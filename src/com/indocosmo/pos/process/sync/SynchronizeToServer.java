/* 
 * This class gives a handle to start Synchronization process.
 * Both batch and single row can be synchronized.
 * @author Ramesh S.
 * @since 10th July 2012, 14nd November 2012
 */

//USAGE: IMPORTANT:- Please do not delete below comments. //

//Commands for synchronizing table "order_hdrs".
//SynchronizeToServer.synchronizeTable(SynchronizeToServer.SyncTable.ORDER_HDRS.getCode());
//SynchronizeToServer.synchronizeTable(SynchronizeToServer.SyncTable.ORDER_HDRS.getCode(),
//									"order_hdrs.order_id='ADIC352-TA-000001'");

// Commands for synchronizing table "txn_staff_attendance".
//SynchronizeToServer.synchronizeTable(SynchronizeToServer.SyncTable.TXN_STAFF_ATTENDANCE.getCode());
//SynchronizeToServer.synchronizeTable(SynchronizeToServer.SyncTable.TXN_STAFF_ATTENDANCE.getCode(),
//									"txn_staff_attendance.id=1");

// Commands for synchronizing table "cashier_shifts".
//	SynchronizeToServer.synchronizeTable(SynchronizeToServer.SyncTable.cashier_shifts.getCode());
//	SynchronizeToServer.synchronizeTable(SynchronizeToServer.SyncTable.cashier_shifts.getCode(),
//									"cashier_shifts.auto_id=1");

package com.indocosmo.pos.process.sync;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

import javax.sql.rowset.CachedRowSet;

import net.sf.json.JSONObject;

import com.indocosmo.pos.common.PosEnvSettings;
import com.indocosmo.pos.common.PosLog;
import com.indocosmo.pos.common.enums.PosProcessMessageType;
import com.indocosmo.pos.data.beans.BeanSyncTableSettings;
import com.indocosmo.pos.data.providers.shopdb.sync.PosSyncTableSettingsProvider;
import com.indocosmo.pos.forms.PosMainMenuForm;
import com.indocosmo.pos.process.sync.listner.IPosSyncListener;

public class SynchronizeToServer {

	private static Calendar syncStartTime = null;
	private static SimpleDateFormat dateFormat 
					= new SimpleDateFormat("HH:mm:ss:SS");
	private static HashMap<ImplDataSynchronization, Boolean> hmThreads 
					= new HashMap<ImplDataSynchronization, Boolean>();

	// Defining table_id from sync_table_settings
	public static enum SyncTable {
		
		ORDER_HDRS("order_hdrs"), TXN_STAFF_ATTENDANCE("txn_staff_attendance"), CASHIER_SHIFT("cashier_shifts"), DAILY_CASHOUTS("txn_cashouts"), ORDER_REFUNDS("order_refunds");

		private String code;

		private SyncTable(String c) {
			code = c;
		}

		public String getCode() {
			return code;
		}
	}

	// Synchronize all data
	public static void synchronizeAllTables() {
		
		try {
			// Synchronizing all tables defined in "sync_table_settings" table
			CachedRowSet crsTableSettings = PosSyncTableSettingsProvider
					.getInstance().getAllRowsToSynch();

			// Synchronizing all tables
			while (crsTableSettings.next()) {
				SynchronizeToServer.synchronizeTable(crsTableSettings
						.getString("table_name"),false);
			}

		} catch (Exception err) {
			PosLog.write("SynchronizeToServer", "synchAll", err);
			PosMainMenuForm.writeProcessLog(
					"Synchronization failed. Contact System Administrator.",
					PosProcessMessageType.ERROR);
		}
	}

	private static void initSync() {
		syncStartTime = Calendar.getInstance();
		PosMainMenuForm.writeProcessLog("Synchronization started...", PosProcessMessageType.INFO);
	}

	// To track active threads, they register themselves.
	public static synchronized void registerThread(
			ImplDataSynchronization thread) {
		if (syncStartTime == null) {
			initSync();
		}
		hmThreads.put(thread, true);
	}

	// To track any active threads, they un-register themselves.
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
			PosMainMenuForm.writeProcessLog("Synchronization over...", PosProcessMessageType.INFO);
			PosMainMenuForm.writeProcessLog(
					"Synchronization Started at "
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
	
	/**
	 * @param tableName
	 * @param criteria
	 */
	public static void synchronizeTable(String tableName, String criteria) {
		
		synchronizeTable( tableName,  criteria,true);;
	}

	// To synchronize data conditionally
	public static void synchronizeTable(String tableName, String criteria, boolean bgProcess) {
		
		if(!PosEnvSettings.getInstance().isUpSyncEnabled())
			return;
		
		try {

			PosSyncTableSettingsProvider tableSettingsProv = PosSyncTableSettingsProvider
					.getInstance();
			BeanSyncTableSettings tableSettingsObj = tableSettingsProv
					.getTableDetailsByName(tableName);

			// Injecting the criteria along with sync_table_settings beans
			if (criteria != null && criteria.trim().length() > 0) {
				if (tableSettingsObj.getTableCriteria() != null
						&& tableSettingsObj.getTableCriteria().trim().length() > 0) {

					tableSettingsObj.setTableCriteria(tableSettingsObj
							.getTableCriteria().trim()
							+ " and "
							+ criteria.trim());
				} else {
					// Setting dummy condition, if empty; 
					// so that flow will be uniform.
					tableSettingsObj.setTableCriteria(" 1=1 ");
				}
			}

			// Initialize multiple threads, as defined in config.
//			for (int numOfThreads = 0; numOfThreads < PosEnvSettings
//					.getInstance().getMaxSyncThreads(); numOfThreads++) {
				// Since no parameter is passed,
				// thread will synch all records one by one.
			DataToServerSyncThread synRunnable=new DataToServerSyncThread(tableSettingsObj);
			if(bgProcess){
				if(PosEnvSettings.getInstance().isAutoSyncEnabled())
					new Thread(synRunnable).start();
			}else
				synRunnable.run();
			
//			}

		} catch (Exception err) {
			PosLog.write("SynchronizeToServerGen", "syncSingleRecord", err);
			PosMainMenuForm.writeProcessLog("Synchronization failed for table "
					+ tableName + ". Contact System Administrator.", PosProcessMessageType.ERROR);
		}
	}

	/**
	 * @param tableName
	 */
	public static void synchronizeTable(String tableName) {
		
		  synchronizeTable( tableName,true);
		
	}
	// To synchronize all rows in the table
	public static void synchronizeTable(String tableName,boolean bgProcess) {
		synchronizeTable(tableName, null,bgProcess);
	}
	
	

	// To synchronize data conditionally
	public static JSONObject synchronizeTableToHMS(String tableName, String criteria ) {
		JSONObject jsonRequest = null;
		
	 	
		try {

			PosSyncTableSettingsProvider tableSettingsProv = PosSyncTableSettingsProvider
					.getInstance();
			BeanSyncTableSettings tableSettingsObj = tableSettingsProv
					.getTableDetailsByName(tableName);

			// Injecting the criteria along with sync_table_settings beans
			if (criteria != null && criteria.trim().length() > 0) {
				if (tableSettingsObj.getTableCriteria() != null
						&& tableSettingsObj.getTableCriteria().trim().length() > 0) {

					tableSettingsObj.setTableCriteria(tableSettingsObj
							.getTableCriteria().trim()
							+ " and "
							+ criteria.trim());
				} else {
					// Setting dummy condition, if empty; 
					// so that flow will be uniform.
					tableSettingsObj.setTableCriteria(  criteria.trim());
				}
			}

			// Initialize multiple threads, as defined in config.
//			for (int numOfThreads = 0; numOfThreads < PosEnvSettings
//					.getInstance().getMaxSyncThreads(); numOfThreads++) {
				// Since no parameter is passed,
				// thread will synch all records one by one.
			DataToServerSyncThread synRunnable=new DataToServerSyncThread(tableSettingsObj);
			jsonRequest=synRunnable.createOrderJSONForHMS(); 

		} catch (Exception err) {
			PosLog.write("SynchronizeToServerGen", "syncSingleRecord", err);
			PosMainMenuForm.writeProcessLog("HMS Synchronization failed for table "
					+ tableName + ". Contact System Administrator.", PosProcessMessageType.ERROR);
		}
		return jsonRequest;
	}

}