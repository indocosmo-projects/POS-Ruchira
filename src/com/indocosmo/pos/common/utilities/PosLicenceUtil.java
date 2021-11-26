/**
 * 
 */
package com.indocosmo.pos.common.utilities;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import com.indocosmo.pos.common.PosEnvSettings;
import com.indocosmo.pos.common.PosLog;
import com.indocosmo.pos.data.beans.BeanBillParam;
import com.indocosmo.pos.data.beans.BeanShop;
import com.indocosmo.pos.data.beans.terminal.BeanTerminalSetting;
import com.indocosmo.pos.data.providers.shopdb.PosBillParamProvider;
import com.indocosmo.pos.data.providers.shopdb.PosDayProcessProvider;
import com.indocosmo.pos.data.providers.shopdb.PosFlashMessageProvider;
import com.indocosmo.pos.data.providers.shopdb.PosShopProvider;
import com.indocosmo.pos.data.providers.terminaldb.PosTerminalSettingsProvider;

/**
 * @author anand
 *
 */
public class PosLicenceUtil{
	
	private static final String encrKey="@%#$&356767$%@$";

	public static  String generateKey() throws Exception{

		final PosShopProvider shopProvider = new PosShopProvider();
		final PosTerminalSettingsProvider terminalSettings = new PosTerminalSettingsProvider();
		final PosBillParamProvider billParamProvider=PosBillParamProvider.getInstance();
		
		final BeanShop shop = shopProvider.getShop();
		final BeanBillParam billParam=billParamProvider.getBillParam();
		
		String key=shop.getName()+
				shop.getCode()+
				terminalSettings.getTerminalSetting().getName()+
				terminalSettings.getTerminalSetting().getCode()+
				((shop.getAddress()==null)?"":shop.getAddress())+
				((shop.getCity()==null)?"":shop.getCity())+
				((shop.getCompanyLicenseNo()==null)?"":shop.getCompanyLicenseNo())+
				shop.getAreaId()+
				((billParam.getHeaderLine1()==null)?"":billParam.getHeaderLine1())+
				((billParam.getHeaderLine2()==null)?"":billParam.getHeaderLine2())+
				((billParam.getHeaderLine3()==null)?"":billParam.getHeaderLine3())+
				((billParam.getHeaderLine4()==null)?"":billParam.getHeaderLine4());

		return CryptUtil.getInstance().getEcryptedString(key,encrKey).replace("\r","").replace("\n","");
	}
	
	/**
	 * @return
	 * @throws Exception 
	 */
	public static InputStream decryptLicence() throws Exception {
		
		return decryptLicence(generateKey());
	}

	public static InputStream decryptLicence(String key)throws Exception{

		ByteArrayInputStream is=null;
//		key=generateKey();
		if(key!=null)
		{
			
			FileReader licenceFileReader = new FileReader(PosEnvSettings.LICENCE_FILE);
			BufferedReader br = new BufferedReader(licenceFileReader);
			String line = br.readLine();
			StringBuilder sb = new StringBuilder();
			while(line!=null){
				sb.append(line);
				sb.append("\n");
				line=br.readLine();
			}

			if(br!=null)
				br.close();

			final String encrytedLicenceFile = sb.toString();
			final byte[] decryptedLicenceFile = CryptUtil.getInstance().getDecryptedString(encrytedLicenceFile,key).getBytes();
			is= new ByteArrayInputStream(decryptedLicenceFile);
		}
		return  is;
	}

	/**
	 * @return
	 */
	private static boolean isSystemReadyForUse(){

		final PosTerminalSettingsProvider terminalSettingsProvider = new PosTerminalSettingsProvider();
		final BeanTerminalSetting terminalSettings=terminalSettingsProvider.getTerminalSetting();

		return (terminalSettings!=null 
				&& terminalSettings.getCode()!=null && !terminalSettings.getCode().equals("")  
				&& terminalSettings.getShopCode()!=null && !terminalSettings.getShopCode().equals(""));
	}

	public static boolean checkLicence(){

		if (!isSystemReadyForUse()) return true;


		boolean isValid = false;
		try{

			final String genKey=PosLicenceUtil.generateKey();
			
			final InputStream iS = decryptLicence(genKey);
			Properties subscription = new Properties();
			subscription.load(iS);

			final String licKey=subscription.getProperty("lic_key","");
			
			if(!genKey.equals(licKey))
				throw new Exception("Failed to validate key. Invalid licence key.");
			
			final String keyValue=subscription.getProperty("key","");
			
//			if(!keyValue.equals("")){
//
//				isValid=true;
//
//			}else{

				PosEnvSettings.getInstance().setDemoVersion(true);

				final SimpleDateFormat sdf=new SimpleDateFormat(PosDateUtil.DATE_FORMAT_YYYYMMDD);
				final Date endDate=sdf.parse(subscription.getProperty("valid_till"));
				final PosDayProcessProvider dPprovider = new PosDayProcessProvider();
				final String curPosDate = dPprovider.getCurrentPosDate();
				final Date posDate = sdf.parse(curPosDate!=null&&!curPosDate.equals("")?curPosDate:PosDateUtil.getDate());
				final Date sysDate = sdf.parse(PosDateUtil.getDate());
				PosEnvSettings.getInstance().setValidTillDate(endDate);
				
				// add notification to flash message table
				int diffInPosDays = (int) ((endDate.getTime() - posDate.getTime()) / (1000 * 60 * 60 * 24));
				int diffInSysDays = (int) ((endDate.getTime() - sysDate.getTime()) / (1000 * 60 * 60 * 24));
				int difDays=(diffInSysDays<diffInPosDays)?diffInSysDays:diffInPosDays;
				if(difDays<=10){
					
					PosFlashMessageProvider flasMsgProvider=new PosFlashMessageProvider();
					flasMsgProvider.addLicenseNotificationMessage(endDate);
					 

				}
				
				if(posDate.compareTo(endDate)<=0 && sysDate.compareTo(endDate)<=0){

					isValid=true;

				}else{

					PosFormUtil.showInformationMessageBox(null, "Your subscription has been expired. Please contact administrator.");
					isValid=false;
				}


		}catch(Exception f){

			PosLog.write(PosLicenceUtil.class, "checkLicence", f);
			PosFormUtil.showInformationMessageBox(null,"Cannot ensure subscription validity. Please contact administrator.");
			isValid=false;
		}

		return isValid;
	}


	/**
	 * this function recieve's a file path and a text,and encrypts the received text and write's it to the file at the given path
	 * 
	 * @param prprtyFilePath
	 * @param prprtyFile
	 * @throws IOException 
	 */
	public static void encryptLicenceFile(String licKey, String prprtyFilePath,String prprtyFile){
		try{

			final byte[] encryptedText = CryptUtil.getInstance().getEcryptedString(prprtyFile,licKey).getBytes();
			File encryptedfile = new File(prprtyFilePath);
			FileOutputStream fout = new FileOutputStream(encryptedfile);
			fout.write(encryptedText);
			fout.close();

		}catch(Exception e)
		{
			PosLog.write(PosPropertyFileUtil.class, " encryptPropertyFile("+ prprtyFilePath+","+ prprtyFile+")", e);
		}

	}

	
}
