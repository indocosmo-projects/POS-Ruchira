/*
 * This class is used to POST data onto URL and
 * receive response from it. Request encoding
 * including encryption is taken care.
 */
package com.indocosmo.pos.process.sync;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;

import com.indocosmo.pos.common.PosEnvSettings;
import com.indocosmo.pos.common.PosLog;
import com.indocosmo.pos.common.utilities.PosFileUtils;

/*
 * @author Ramesh S
 * @since 10th Sept 2012
 */
public class AccessURL {

	protected String url = null;
	protected String request = null;

	// Creates a connection to web server
	private HttpURLConnection getHTTPConnection() throws Exception {

		if (this.url.trim().length() == 0) {
			throw new Exception("URL is empty.");
		}

		URL url = new URL(this.url);
		HttpURLConnection httpConnection = (HttpURLConnection) url
				.openConnection();
		httpConnection.setDoInput(true);
		httpConnection.setDoOutput(true);
		httpConnection.setUseCaches(false);
		httpConnection.setRequestMethod("POST");
		httpConnection.setRequestProperty("Content-Type",
				"application/x-www-form-urlencoded");

		return httpConnection;
	}

	// Send request to URL and get response in text
	public String getResponseText() throws Exception {

		if (this.url == null || this.url.trim().length() == 0) {
			throw new Exception("URL string is empty.");
		}

		if (this.request == null || this.request.trim().length() == 0) {
			throw new Exception("Request string is empty.");
		}

		HttpURLConnection httpConn = getHTTPConnection();

		StringBuffer sbfResponse = new StringBuffer();
		BufferedReader urlResponse = null;

		try {
			DataOutputStream urlRequest = new DataOutputStream(
					httpConn.getOutputStream());

			// This is the POST
			urlRequest.writeBytes(this.request);
			urlRequest.flush();
			urlRequest.close();

			// Receiving Response from server
			urlResponse = new BufferedReader(new InputStreamReader(
					httpConn.getInputStream()));

			// Read the response
			String strRead = null;

			while (null != ((strRead = urlResponse.readLine()))) {
				sbfResponse.append(strRead);
			}

		} catch (Exception ex) {
			throw ex;

		} finally {
			try {
				urlResponse.close();
			} catch (Exception err) {
				PosLog.write(this, "requestData", err);
			}
		}
		return URLDecoder.decode(sbfResponse.toString(), "UTF-8");
	}

	
	// Send request to URL and get response in binary
	public boolean getResponseFile(URL fromURL, File toFile) throws Exception {

		// Create download folder, if not existing.
		PosFileUtils.getInstance().createFolder(
				new File(PosEnvSettings.getInstance().getDownloadFolder()));

		if (toFile.exists()) {
			toFile.delete();
		}

		InputStream is = fromURL.openStream();
		FileOutputStream fos = new FileOutputStream(toFile);

		int c;
		while ((c = is.read()) != -1) {
			fos.write(c);
		}

		is.close();
		fos.close();

		return true;
	}

	// Setting request by encoding URL.
	public void setRequest(String strRequest) throws Exception {
		//CryptUtil crypting = CryptUtil.getInstance();
		// this.request = crypting.getEncryptedURL(strRequest);

		this.request = strRequest;
	}

	public String getRequest() {
		return request;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUrl() {
		return url;
	}
}
