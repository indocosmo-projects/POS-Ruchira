package com.indocosmo.pos.process.sync;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import com.indocosmo.pos.common.PosEnvSettings;
import com.indocosmo.pos.common.PosLog;
import com.indocosmo.pos.common.enums.PosProcessMessageType;
import com.indocosmo.pos.common.utilities.PosFileUtils;
import com.indocosmo.pos.data.providers.shopdb.PosFlashMessageProvider;
import com.indocosmo.pos.data.providers.shopdb.PosShopProvider;
import com.indocosmo.pos.data.providers.terminaldb.PosSynchronizeProvider;
import com.indocosmo.pos.forms.PosMainMenuForm;

/*
 * This class creates individual threads for 
 * each row and send it to server to synchronize.
 *  
 * @author Ramesh S
 * @since 07th Sept 2012
 */

public class SyncDataFromServer extends ImplDataSynchronization {

	// Constructor to synch multiple rows
	public SyncDataFromServer() {

		// Calling parent constructor
		super();

		try {
			PosLog.debug("Initialising SyncDataFromServer Thread:"
					+ this.toString());
		}

		catch (Exception err) {
			PosLog.debug("Failed initialisation of SyncDataFromServer thread# "
					+ this.toString());
			PosLog.write(this, "SyncDataFromServer()", err);
		}
	}

	public void run() {
		Calendar syncStartTime = null;
		SimpleDateFormat dateFormat = null;
		
		try {
			SynchronizeFromServer.registerThread(this);
			syncStartTime = Calendar.getInstance();
			dateFormat = new SimpleDateFormat("HH:mm:ss:SS");

			// Synchronization of data from server
			startSyncDataFromServer();

			PosLog.debug("Terminating SyncDataFromServer Thread:"
					+ this.toString() + " Start time:"
					+ dateFormat.format(syncStartTime.getTime()) + " End time:"
					+ dateFormat.format(Calendar.getInstance().getTime()));

		} catch (Exception err) {
			PosLog.write(this, "SyncDataFromServer.run()", err);
			PosMainMenuForm.writeProcessLog(
					"Error occurred while synchronizing with server. Please check log file."
							+ "-[ERROR]", PosProcessMessageType.ERROR);

		} finally {
			PosLog.debug("Terminating SyncDataFromServer Thread:"
					+ this.toString() + " Start time:"
					+ dateFormat.format(syncStartTime.getTime()) + " End time:"
					+ dateFormat.format(Calendar.getInstance().getTime()));

			SynchronizeFromServer.unRegisterThread(this);
		}
	}

	
	// Synchronize data for a single order
	private void startSyncDataFromServer() throws Exception {

		AccessURL urlAccess = new AccessURL();
		urlAccess.setUrl(PosEnvSettings.getInstance().getWebArchiveURL());
		int maxRetryCount = PosEnvSettings.getInstance()
				.getMaxThreadRetryCount();

		// Exit from thread if maximum retry is complete.
		for (this.retryCount = 0; this.retryCount < maxRetryCount; this.retryCount++) {

//			// Start Synch using jsonData
			URL from = new URL(PosEnvSettings.getInstance().getWebArchiveURL() + "?"
					+ PosSynchronizeProvider.getInstance()
							.getSynchronizeRequest());
			
			File to = new File(PosEnvSettings.getInstance().getDownloadFolder() + "/"
					+ PosEnvSettings.getInstance().getTmpDownloadedArchive());
			
			// Getting Response from server
			if (urlAccess.getResponseFile(from, to)) {
				//If empty file, no data to synchronize
			    if (to.length() == 0) {
			    	to.delete();
					PosMainMenuForm.writeProcessLog("No data to synchronize.", PosProcessMessageType.WARNNING);
			    	break;
			    }
				processZipFile(to);
			        PosFlashMessageProvider msgProvider=new PosFlashMessageProvider();
				msgProvider.updateSyncNotificationMessageStatus();
			
				break;
			}
		}
		return;
	}

	
	// Unzip downloaded sql file and executes it in database
	private void processZipFile(File downloadedFile) throws Exception {
		// Refer link:
		// http://stackoverflow.com/questions/981578/how-to-unzip-files-recursively-in-java
		final int BUFFER = 1024;
		ZipFile zip = null;
		File destFile = null;
		List<String> sqlFileList = new ArrayList<String>();

		try {
			zip = new ZipFile(downloadedFile);
			String newPath = PosEnvSettings.getInstance().getDownloadFolder();

			PosSynchronizeProvider.getInstance().beginTrans();
			Enumeration zipFileEntries = zip.entries();
			// Process each entry
			while (zipFileEntries.hasMoreElements()) {
				// grab a zip file entry
				ZipEntry entry = (ZipEntry) zipFileEntries.nextElement();
				if (entry.isDirectory()) {
					throw new Exception("A folder is not expected in archive.");
				}

				BufferedInputStream is = new BufferedInputStream(
						zip.getInputStream(entry));
				int currentByte;
				// establish buffer for writing file
				byte data[] = new byte[BUFFER];

				destFile = new File(newPath, entry.getName());

				// write the current file to disk
				FileOutputStream fos = new FileOutputStream(destFile);
				BufferedOutputStream dest = new BufferedOutputStream(fos,
						BUFFER);

				// read and write until last byte is encountered
				while ((currentByte = is.read(data, 0, BUFFER)) != -1) {
					dest.write(data, 0, currentByte);
				}
				dest.flush();
				dest.close();
				is.close();

				sqlFileList.add(destFile.getPath());
			}
			// Executing SQLs in sequence, according to the date created.
			Collections.sort(sqlFileList);
			for (String fileName : sqlFileList) {
				executeSQL(new File(fileName));
			}

			deleteZipFile(downloadedFile);
			PosSynchronizeProvider.getInstance().commitTrans();

		} catch (Exception err) {
			
			PosLog.write(this, "processZipFile", err);
			try {
				PosSynchronizeProvider.getInstance().rollBack();
			} catch (Exception er) {
				PosLog.write(this, "processZipFile", er);
			}
			throw err;
			
		} finally {
			try {
				zip.close();
			
			} catch (Exception err) {
				PosLog.write(this, "processZipFile", err);
			}
		}
	}

	
	// Open the given file and executes all SQL statements.
	private void executeSQL(File destFile) throws Exception {

		// Nothing to execute in empty file
		if (destFile.length() == 0) {
			return;
		}

		PosLog.debug("Processing File : " + destFile.getName());
		FileInputStream fstream = new FileInputStream(destFile.getPath());
		// Get the object of DataInputStream
//		BufferedReader in = new BufferedReader(
//				   new InputStreamReader(
//		                      new FileInputStream(fileDir), "UTF8"));
		DataInputStream in = new DataInputStream(fstream);
		BufferedReader br = new BufferedReader(new InputStreamReader(in,"UTF8"));
		String strLine;
		PosShopProvider shopDB = new PosShopProvider();
		PosSynchronizeProvider termimalDB = PosSynchronizeProvider
				.getInstance();
		StringBuffer sqlStatements = new StringBuffer();

		// Read file Line By Line
		while ((strLine = br.readLine()) != null) {
			
			sqlStatements.append(strLine);
//			PosLog.write(this,"executeSQL",strLine);
			PosLog.debug("strLine:" + strLine);
		}

		// Execute SQLs in whole file as a single statement.
		shopDB.executeNonQuery(sqlStatements.toString());
		termimalDB.updateSyncStatus(destFile.getName(), true);

		// Print the content on the console
		PosLog.debug(strLine);
		// Close the input stream
		br.close();
		in.close();
	}

	
	/*
	 * Deletes archive file; and its extracted files, if any. Moves all files
	 * out from temporary folder before deleting.
	 */
	private void deleteZipFile(File zipFile) throws Exception {
		ZipFile zip = new ZipFile(zipFile);
		Enumeration zipFileEntries = zip.entries();

		// Process each entry
		while (zipFileEntries.hasMoreElements()) {
			// grab a zip file entry
			ZipEntry entry = (ZipEntry) zipFileEntries.nextElement();
			String currentEntry = entry.getName();
			File file = new File(currentEntry);
			if (file.isDirectory()) {
				throw new Exception(file.getName()
						+ " folder found. Folder is not expected in this archive.");

			} else {
				File destFile = new File(PosEnvSettings.getInstance()
						.getPublishedDBFolder() + "/" + file.getName());
				File srcFile = new File(PosEnvSettings.getInstance()
						.getDownloadFolder() + "/" + file.getName());
				
				if (srcFile.length() > 0) {
					PosFileUtils.getInstance().moveFile(srcFile, destFile);
				} else {
					srcFile.delete();
				}
			}
		}
		// Removes zipped file
		zip.close();
		zipFile.delete();
	}
}
