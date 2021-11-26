/*
 * This class creates separate threads for 
 * each row and send it to server to synchronize.
 *  
 * @author Ramesh S
 * @since 10th July 2012, 14nd November 2012
 */

package com.indocosmo.pos.process.sync;

import java.net.URLEncoder;
import java.sql.ResultSetMetaData;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.sql.rowset.CachedRowSet;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import com.indocosmo.pos.common.PosEnvSettings;
import com.indocosmo.pos.common.PosLog;
import com.indocosmo.pos.common.enums.PosProcessMessageType;
import com.indocosmo.pos.data.beans.BeanSyncTableSettings;
import com.indocosmo.pos.data.providers.shopdb.sync.PosSyncTableProvider;
import com.indocosmo.pos.data.providers.shopdb.sync.PosSyncTableSettingsProvider;
import com.indocosmo.pos.forms.PosMainMenuForm;

public class DataToServerSyncThread extends ImplDataSynchronization {

	private PosSyncTableProvider syncTableProvider = null;

	public DataToServerSyncThread(){
		
	}
	public DataToServerSyncThread(BeanSyncTableSettings tableSettingsObject) {

		super(); // Calling parent constructor

		try {
			PosLog.debug("Initialising thread# " + this.toString());
			this.syncTableProvider = new PosSyncTableProvider(tableSettingsObject);

		} catch (Exception err) {
			PosLog.debug("Failed initialisation of thread# " + this.toString());
			PosLog.write(this, "DataToServerSyncThread(tableSettingsObject)",
					err);
		}
	}

	public void run() {
		Calendar syncStartTime = null;
		SimpleDateFormat dateFormat = null;

		try {
			SynchronizeToServer.registerThread(this);

			syncStartTime = Calendar.getInstance();
			dateFormat = new SimpleDateFormat("HH:mm:ss:SS");

			CachedRowSet crsTable = syncTableProvider
					.getDataIdentifiers(syncTableProvider.getTableSettings());
			while (crsTable.next()) {
				BeanSyncTableSettings tableSettingsObj = syncTableProvider
						.getTableSettings().clone();

				if (tableSettingsObj.getTableCriteria().trim().length() == 0) {
					tableSettingsObj.setTableCriteria(crsTable
							.getString("_identifier"));

				} else {
					tableSettingsObj.setTableCriteria(tableSettingsObj
							.getTableCriteria()
							+ " and "
							+ crsTable.getString("_identifier"));
				}
				CachedRowSet crsRealTime = syncTableProvider
						.getDataIdentifiers(tableSettingsObj);

				// If row is being processed by another thread, skip it.
				if (crsRealTime.next()
						&& !crsRealTime.getString("sync_status").equals(
								PosEnvSettings.getInstance().getSyncToStart())
						&& !crsRealTime.getString("sync_status").equals(
								PosEnvSettings.getInstance().getSyncFailed())) {

					tableSettingsObj = null;
					continue;
				}

				processTableRow(crsTable, tableSettingsObj);
			}

		} catch (Exception err) {
			PosLog.write(this, "DataToServerSyncThread.run()", err);
			PosMainMenuForm.writeProcessLog(
					"Error occurred while synchronizing. Please check log file."
							+ "-[ERROR]", PosProcessMessageType.ERROR);

		} finally {
			SynchronizeToServer.unRegisterThread(this);

			PosLog.debug("Terminating Thread:" + this.toString()
					+ " Start time:"
					+ dateFormat.format(syncStartTime.getTime()) + " End time:"
					+ dateFormat.format(Calendar.getInstance().getTime()));
		}
		return;
	}

	
	private JSONObject createJSONRequest(BeanSyncTableSettings tableSettingsObj)
			throws Exception {
		
		JSONObject jsonMain = new JSONObject();
		jsonMain.put(tableSettingsObj.getTableName(),
				generateJSON(tableSettingsObj));
		jsonMain.put("shop_id", PosEnvSettings.getInstance()
				.getShop().getId());
		jsonMain.put("station_id", PosEnvSettings.getInstance()
				.getStation().getId());

		PosLog.debug("JSON String:" + jsonMain.toString());
		return jsonMain;
	}

	//Creating JSON request using Recursion
	//This is the core part of this Sync program
	private JSONArray generateJSON(
			BeanSyncTableSettings parentTableSettings) throws Exception {

		String Identifier = "Identifier";
		String IdentCheck = "IdentCheck";
		JSONObject jsonObject = null;
		JSONArray jsonArray = new JSONArray();
		BeanSyncTableSettings childTableSettings = null;

		CachedRowSet crsHeader = syncTableProvider.getDataForJSON(parentTableSettings);
		while (crsHeader.next()) {
			jsonObject = getAsJSON(crsHeader);

			if (parentTableSettings.getTableCriteria() != null
					&& parentTableSettings.getTableCriteria().trim().length() > 0) {

				if(crsHeader.getString("_identifier").startsWith(IdentCheck)){
					parentTableSettings.setTableCriteria(parentTableSettings.getTableCriteria().replace(Identifier, crsHeader.getString("_identifier")));
					Identifier = crsHeader.getString("_identifier");
				}else{
					parentTableSettings.setTableCriteria(crsHeader.getString("_identifier") + 
							" and " + parentTableSettings.getTableCriteria());
					Identifier = crsHeader.getString("_identifier");
					IdentCheck = crsHeader.getString("_identifier").substring(0, 10);
				}
			}
			
			CachedRowSet crsDetail = PosSyncTableSettingsProvider.getInstance()
					.getData("parent_table_id = " + parentTableSettings.getTableId());
			while (crsDetail.next()) {

				childTableSettings = getChildTableSettings(parentTableSettings,
										crsHeader, crsDetail);
				jsonObject.put(childTableSettings.getTableName(),
						generateJSON(childTableSettings));
			}
			jsonArray.add(jsonObject);
		}

		return jsonArray;
	}
	
	
	// Retrieve "child table settings" bean info
	private BeanSyncTableSettings getChildTableSettings(
			BeanSyncTableSettings parentTableSettings,
			CachedRowSet crsHeader, CachedRowSet crsDetail) throws Exception {

		BeanSyncTableSettings childTableSettings = PosSyncTableSettingsProvider
				.getInstance()
				.getTableDetailsByName(crsDetail.getString("table_name"));

		// Collect all the table names in the query
		// If no tables in the list, then start collecting table names.
		if (parentTableSettings.getTableList() != null
				&& parentTableSettings.getTableList().trim().length() > 0) {

			childTableSettings.setTableList(parentTableSettings.getTableList()
					+ ", " + childTableSettings.getTableName());

		} else {
			childTableSettings.setTableList(parentTableSettings.getTableName()
					+ ", " + childTableSettings.getTableName());
		}

		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append(" 1=1 ");
		if (parentTableSettings.getTableCriteria() != null
				&& parentTableSettings.getTableCriteria().trim().length() > 0) {

			stringBuffer.append(" and " + parentTableSettings.getTableCriteria());
		}
		if (childTableSettings.getTableCriteria() != null
				&& childTableSettings.getTableCriteria().trim().length() > 0) {

			stringBuffer.append(" and " + childTableSettings.getTableCriteria());
		}

		childTableSettings.setTableCriteria(stringBuffer.toString());

		return childTableSettings;
	}
	 
	
	// Creates and Adds table column values to a JSON object
	private synchronized JSONObject getAsJSON(CachedRowSet crsTable)
			throws Exception {

		JSONObject jsonObj = new JSONObject();
		ResultSetMetaData metaData = crsTable.getMetaData();
		int numOfCols = metaData.getColumnCount();

		for (int ctr = 1; ctr <= numOfCols; ctr++) {
			// Excluding "_identifier" column, all columns are added to JSON
			if (!metaData.getColumnName(ctr).equalsIgnoreCase("_identifier")) {
				jsonObj.put(metaData.getColumnName(ctr), crsTable
						.getString(crsTable.getMetaData().getColumnName(ctr)));
			}
		}
		return jsonObj;
	}
	
	
	//Processing request
	private String sendReceiveRequest(String string,String request) throws Exception {
		JSONObject responseJSON = null;
		String syncStatus = "Failed"; // To hold Synched status

		try {
			PosLog.debug("Final Request:" + request);
			int maxRetryCount = PosEnvSettings.getInstance()
					.getMaxThreadRetryCount();
			String errMessage = null;
			AccessURL urlAccess = new AccessURL();
			urlAccess.setUrl(PosEnvSettings.getInstance().getWebDataURL());
			// Exit from thread if maximum retry is complete.
			for (this.retryCount = 0; this.retryCount < maxRetryCount; this.retryCount++) {
				// Start Synch using jsonData
				urlAccess.setRequest(request);
			 					
				responseJSON = (JSONObject) JSONSerializer.toJSON(urlAccess
						.getResponseText());
				 
				
				if (responseJSON == null) {
					throw new Exception("Failed to get response from server.");
				}

				
				PosLog.debug("responseJSON :" +  responseJSON.toString());
				// If successful response
				if (responseJSON.getInt("status") == 0) {
					syncStatus = responseJSON.getString("message");
					break;

				} else {
					syncStatus = "Failed";
					errMessage = responseJSON.getString("message");
					throw new Exception(errMessage);
				}
			}

		} finally {
			// Writing the message to console of POS
			if (!syncStatus.equalsIgnoreCase("Failed")) {

				PosMainMenuForm.writeProcessLog("Sent Successful for "
						+string, PosProcessMessageType.SUCCEESS);

			} else {
				PosMainMenuForm.writeProcessLog("Sent Failed for "
						+string + "-[ERROR]", PosProcessMessageType.ERROR);
			}
		}
		return syncStatus;
	}
	
	//Process single current record of the table passed
	private void processTableRow(CachedRowSet crsHeader, BeanSyncTableSettings tableSettingsObj) {
		try{
			//Updating status in "sync_status" column
			syncTableProvider.updateSyncStatus(crsHeader, PosEnvSettings.getInstance().getSyncInProgress());

			// Generating Request JSON
			JSONObject jsonRequest = null;
			jsonRequest = createJSONRequest(tableSettingsObj);
	
			PosLog.debug("Request before encoding:" + jsonRequest.toString());
			
			// Send request and receive response from Server
			String message = sendReceiveRequest(crsHeader.getString("_identifier"),tableSettingsObj.getWebParamValue() 
								+ "=" + URLEncoder.encode(jsonRequest.toString(), "UTF-8"));

			//Updating status in "sync_status" column
			syncTableProvider.updateSyncStatus(crsHeader,
						PosEnvSettings.getInstance().getSyncSuccessful(), message);

		} catch (Exception err) {
			try {
				//Updating status in "sync_status" column
				syncTableProvider.updateSyncStatus(crsHeader, 
						PosEnvSettings.getInstance().getSyncFailed(),
						(err.getMessage() == null ? null : err.getMessage()));
				PosLog.write(this, "processTableRow", (err.getMessage() == null ? null : err.getMessage()));
			} catch (Exception error) {
				PosLog.write(this, "processTableRow", error);
			}
		}
	}
	
	public JSONObject createOrderJSONForHMS() {
		
		JSONObject jsonRequest = null;
		
	 	
		try {

			CachedRowSet crsTable = syncTableProvider
					.getDataIdentifiers(syncTableProvider.getTableSettings());
			while (crsTable.next()) {
				BeanSyncTableSettings tableSettingsObj = syncTableProvider
						.getTableSettings().clone();

				if (tableSettingsObj.getTableCriteria().trim().length() == 0) {
					tableSettingsObj.setTableCriteria(crsTable
							.getString("_identifier"));

				} else {
					tableSettingsObj.setTableCriteria(tableSettingsObj
							.getTableCriteria()
							+ " and "
							+ crsTable.getString("_identifier"));
				}
				CachedRowSet crsRealTime = syncTableProvider
						.getDataIdentifiers(tableSettingsObj);

				// If row is being processed by another thread, skip it.
				if (crsRealTime.next()
						&& !crsRealTime.getString("sync_status").equals(
								PosEnvSettings.getInstance().getSyncToStart())
						&& !crsRealTime.getString("sync_status").equals(
								PosEnvSettings.getInstance().getSyncFailed())) {

					tableSettingsObj = null;
					continue;
				}
				// Generating Request JSON
			
				jsonRequest = createJSONRequest(tableSettingsObj);
			 
				PosLog.debug("Request before encoding:" + jsonRequest.toString());
			}

		} catch (Exception err) {
			PosLog.write(this, "DataToServerSyncThread.run()", err);
			PosMainMenuForm.writeProcessLog(
					"Error occurred while synchronizing. Please check log file."
							+ "-[ERROR]", PosProcessMessageType.ERROR);

 		}
		return jsonRequest;
	} 
	 
	
	
//	public   JSONObject createRefundJSONForHMS(CachedRowSet crs) {
//		
//		JSONObject jsonRequest = null;
//	 	
//		try {
//
//			JSONObject jsonObject = getAsJSON(crs);
//
//			JSONObject jsonMain = new JSONObject();
//			jsonMain.put("order_refund",
//					jsonObject);
//			jsonMain.put("shop_id", PosEnvSettings.getInstance()
//					.getShop().getId());
//			jsonMain.put("station_id", PosEnvSettings.getInstance()
//					.getStation().getId());
//
//			PosLog.debug("JSON String:" + jsonMain.toString());
//			PosLog.debug("Request before encoding:" + jsonRequest.toString());
//			
//			return jsonMain;
//				
//
//		} catch (Exception err) {
//			PosLog.write(this, "RefundToHMS", err);
//			PosMainMenuForm.writeProcessLog(
//					"Error occurred while synchronizing. Please check log file."
//							+ "-[ERROR]", PosProcessMessageType.ERROR);
//
// 		}
//		return jsonRequest;
//	} 
}
