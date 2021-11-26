/**
 * 
 */
package com.indocosmo.pos.common.utilities.mail;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import com.indocosmo.pos.common.PosEnvSettings;
import com.indocosmo.pos.common.PosLog;
import com.indocosmo.pos.common.utilities.CryptUtil;
import com.indocosmo.pos.common.utilities.PosFileUtils;
import com.indocosmo.pos.common.utilities.PosSyncUtil;
import com.indocosmo.pos.common.utilities.mail.bean.PosMail;
import com.indocosmo.pos.common.utilities.mail.bean.PosMailConfiguration;
import com.indocosmo.pos.process.sync.AccessURL;
import com.indocosmo.pos.terminal.PosOsInfo;

/**
 * @author jojesh-13.2
 *
 */
public class PosSendMail {

	private static final String MAIL_INFO_FILE = "mail-info";
	private static final String WEB_PARAM_VALUE = "mailconfig";
	private static boolean configFromCache;

	public enum AuthMethod {

		TLS("tls"), SSL("ssl");

		private static final Map<String, AuthMethod> mLookup = new HashMap<String, AuthMethod>();

		static {
			for (AuthMethod item : EnumSet.allOf(AuthMethod.class))
				mLookup.put(item.getCode(), item);
		}

		private String mCode;

		private AuthMethod(String code) {
			mCode = code;
		}

		public String getCode() {
			return mCode;
		}

		public static AuthMethod get(String code) {
			return mLookup.get(code);
		}

	}


	public static boolean send(PosMail mail) throws Exception {

		final PosMailConfiguration mailConfig = getMailConfiguration(AuthMethod.TLS);

		Properties props = mailConfig.getPorperty();
		Session session = Session.getDefaultInstance(props,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(mailConfig.getUser(),
								mailConfig.getPassword());
					}
				});

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(mailConfig.getFromAddress()));
			message.setReplyTo(new InternetAddress[]{new InternetAddress(mailConfig.getReplyToAddress())});
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(mailConfig.getToAddress()));
			message.setRecipients(Message.RecipientType.CC,
					InternetAddress.parse(mailConfig.getCcAddress()));
			message.setRecipients(Message.RecipientType.BCC,
					InternetAddress.parse(mailConfig.getBccAdress()));
			message.setSubject("[" + mailConfig.getClientId() + "]"
					+ mail.getSubject());

			BodyPart msgBodyPart = new MimeBodyPart();
			msgBodyPart.setText(mail.getBody()+(configFromCache? "\n\n\n\n[Mail configuration from cache.]":"\n\n\n\n[Mail configuration from server.]"));

			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(msgBodyPart);

			if (mail.hasAttachment()) {
				for (String filename : mail.getAttachement()) {
					BodyPart attachmentPart = new MimeBodyPart();
					DataSource source = new FileDataSource(filename);
					attachmentPart.setDataHandler(new DataHandler(source));
					attachmentPart.setFileName(attachmentPart.getDataHandler()
							.getDataSource().getName());
					multipart.addBodyPart(attachmentPart);
				}
			}
			message.setContent(multipart);
			Transport.send(message);

			return true;

		} catch (MessagingException e) {
			PosLog.write("PosSendMail", "send", e);
			throw new Exception("Sending mail failed!!!");
		}
	}



	/**
	 * @param authMethod
	 * @return
	 * @throws Exception
	 */
	public static JSONObject getMailConfigurationJson(
			AuthMethod authMethod) throws Exception {

		String serverRequestUrl = PosSyncUtil.getServerURL() + "/includes/support.php";
		
		JSONObject jsonRequest = null;
		jsonRequest = createJSONRequest();
		AccessURL urlAccess = new AccessURL();
		urlAccess.setUrl(serverRequestUrl);
		urlAccess.setRequest(WEB_PARAM_VALUE+ "=" + jsonRequest.toString());
		String jsonConfig = null;
		try {
			jsonConfig = urlAccess.getResponseText();
		} catch (Exception e) {
			jsonConfig = null;
			PosLog.write("PosSendMail", "getMailConfiguration",e);
		}

		if (jsonConfig == null || jsonConfig.trim().length() == 0) {

			jsonConfig = CryptUtil.getInstance().getDecryptedString(
					getMailConfigFromCache());
			configFromCache =true;

		} else {
			configFromCache =false;
			updateMailInfoCache(CryptUtil.getInstance().getEcryptedString(
					jsonConfig));
		}

		if (jsonConfig == null || jsonConfig.trim().length() == 0) {
			throw new Exception(
					"Failed to get mail configurations. Contact administrator.");
		}
		
		JSONObject responseJSON = (JSONObject) JSONSerializer
				.toJSON(jsonConfig);
		
		if(!responseJSON.getBoolean("valid")){
			PosLog.write("PosSendMail", "getMailConfiguration","valid=false");
			throw new Exception(
					"Support service has been halted. Please contact vendor.");
		}
		
		return responseJSON;
	}
	
	/**
	 * @param authMethod
	 * @return
	 * @throws Exception
	 */
	public static PosMailConfiguration getMailConfiguration(AuthMethod authMethod) throws Exception{

		
		JSONObject responseJSON =getMailConfigurationJson(authMethod);

		PosMailConfiguration mailConfig = new PosMailConfiguration();

		Properties props = new Properties();

		if (responseJSON.getString("smtp_auth_method").toLowerCase()
				.equals(AuthMethod.TLS.getCode())) {
			props.put("mail.smtp.starttls.enable", "true");
		}

		props.put("mail.smtp.auth",
				responseJSON.getString("smtp_auth_required"));
		props.put("mail.smtp.host", responseJSON.getString("smtp_server"));
		props.put("mail.smtp.port", responseJSON.getString("smtp_server_port"));
		props.put("mail.smtp.timeout",
				responseJSON.getString("smtp_server_timeout"));
		props.put("mail.smtp.connectiontimeout",
				responseJSON.getString("smtp_server_timeout"));

		mailConfig.setPorperty(props);
		mailConfig.setClientId(responseJSON.getString("client_id"));
		
		mailConfig.setFromAddress(responseJSON.getString("mail_from"));
		mailConfig.setReplyToAddress(responseJSON.getString("mail_reply_to"));
		mailConfig.setToAddress(responseJSON.getString("mail_to_user"));
		mailConfig.setCcAddress(responseJSON.getString("mail_cc_users"));
		mailConfig.setBccAdress(responseJSON.getString("mail_bcc_users"));
		mailConfig.setUser(responseJSON.getString("smtp_server_user"));
		mailConfig.setPassword(responseJSON.getString("smtp_server_user_pw"));

		return mailConfig;
	}
	
	private static JSONObject createJSONRequest()
			throws Exception {
		
		JSONObject jsonMain = new JSONObject();
		jsonMain.put("json_ver", PosEnvSettings.getInstance()
				.getJsonVersion());
		jsonMain.put("app_ver", PosEnvSettings.getInstance()
				.getApplicationVersion());
		jsonMain.put("request_type","get_mail_config");
		jsonMain.put("shop_code", PosEnvSettings.getInstance()
				.getShop().getCode());
		jsonMain.put("terminal_code", PosEnvSettings.getInstance()
				.getStation().getCode());
		jsonMain.put("os", PosOsInfo.getName());

		PosLog.debug("JSON String:" + jsonMain.toString());
		return jsonMain;
	}
	

	/**
	 * 
	 * 
	 * 
	 *  json_ver:"x.y.z",
  shop_code:"",
  terminal_code:"",
  os:"", -->WINDOWS/LINUX/ANDROID/IOS
	 * @return
	 * @throws IOException
	 */
	private static String getMailConfigFromCache() throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(PosEnvSettings
				.getInstance().getCacheFolder() + "/" + MAIL_INFO_FILE));
		try {
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();

			while (line != null) {
				sb.append(line);
				sb.append("\n");
				line = br.readLine();
			}
			return sb.toString();

		} finally {
			br.close();
		}
	}

	/**
	 * @param jsonConfig
	 * @throws IOException
	 */
	private static void updateMailInfoCache(String jsonConfig)
			throws IOException {

		PosFileUtils.getInstance().createFolder(
				new File(PosEnvSettings.getInstance().getCacheFolder()));

		File mailInfoFile = new File(PosEnvSettings.getInstance()
				.getCacheFolder() + "/" + MAIL_INFO_FILE);
		FileWriter mailInfoWriter = new FileWriter(mailInfoFile, false);
		mailInfoWriter.write(jsonConfig);
		mailInfoWriter.close();
	}

}
