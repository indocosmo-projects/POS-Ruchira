/*
 * This class will synchronize data from web server
 */
package com.indocosmo.pos.process.sync;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

import com.indocosmo.pos.common.PosEnvSettings;
import com.indocosmo.pos.common.enums.PosProcessMessageType;
import com.indocosmo.pos.common.utilities.PosFileUtils;
import com.indocosmo.pos.forms.PosMainMenuForm;
import com.indocosmo.pos.process.sync.listner.IPosSyncListener;

/*
 * @author Ramesh S
 * @since 12th Sept 2012
 */

//USAGE: IMPORTANT:- Please do not delete below comments. //
//Commands for synchronizing data from web.
//SynchronizeFromServer.syncFromWeb();


public class SynchronizeFromServer {

	private static HashMap<ImplDataSynchronization, Boolean> hmThreads = new HashMap<ImplDataSynchronization, Boolean>();
	private static Calendar syncStartTime = null;
	private static SimpleDateFormat dateFormat = new SimpleDateFormat(
			"HH:mm:ss:SS");

	private static void initSync() {
		syncStartTime = Calendar.getInstance();
		PosMainMenuForm.writeProcessLog("Synchronizing data from Server...",
				PosProcessMessageType.INFO);
	}

	public static synchronized void registerThread(
			ImplDataSynchronization thread) {
		if (syncStartTime == null) {
			initSync();
		}
		hmThreads.put(thread, true);
	}

	public static synchronized void unRegisterThread(
			ImplDataSynchronization thread) {
		hmThreads.put(thread, false);

		// Get a set of the entries
		Set<Entry<ImplDataSynchronization, Boolean>> set = hmThreads.entrySet();
		Iterator<Entry<ImplDataSynchronization, Boolean>> iThreads = set
				.iterator();

		// Display elements
		Entry<ImplDataSynchronization, Boolean> currThread = null;
		while (iThreads.hasNext()) {
			currThread = iThreads.next();

			// Some threads are still alive
			if (currThread.getValue() == true)
				break;
		}

		// All the threads ceased
		if (currThread.getValue() == false) {
			PosMainMenuForm.writeProcessLog(
					"Synchronization process complete...", PosProcessMessageType.INFO);
			PosMainMenuForm.writeProcessLog(
					"Synchronization started at "
							+ dateFormat.format(syncStartTime.getTime())
							+ " and ended at "
							+ dateFormat.format(Calendar.getInstance()
									.getTime()), PosProcessMessageType.INFO);
			syncStartTime = null;
			if (syncListener != null) {
				syncListener.onCompleted();
			}
		}
	}


	
	private static IPosSyncListener syncListener;
	
	public static void setListener(IPosSyncListener listener) {
		syncListener =  listener;
	}
	
	// Synchronize data from web server
	public static void syncFromWeb() {
		
		if(!PosEnvSettings.getInstance().isDownSyncEnabled())
			return;
		
		// Create publishing DB folder, if not exist.
		PosFileUtils.getInstance().createFolder(
				new File(PosEnvSettings.getInstance().getPublishedDBFolder()));

		SynchronizeFromServer syncInstance = new SynchronizeFromServer();
		syncInstance.deleteOldFiles();

		// Synchronizing Order data
		new Thread(new SyncDataFromServer()).start();
	}

	// Deletes old SQL dump files, older than specified period 
	// (mentioned in property file)
	private void deleteOldFiles() {
		// Directory path here
		String path = PosEnvSettings.getInstance().getPublishedDBFolder();
		int fileRetainDays = PosEnvSettings.getInstance()
				.getPublishDBRetainDays();

		File folder = new File(path);
		File[] listOfFiles = folder.listFiles();

		if (listOfFiles != null) {
			for (int i = 0; i < listOfFiles.length; i++) {
				if (listOfFiles[i].isFile()
						&& PosFileUtils.getInstance().isOlderThan(listOfFiles[i], fileRetainDays)) {
					listOfFiles[i].delete();
				}
			}
		}
	}

}