/**
 * 
 */
package com.indocosmo.pos.common.utilities;

import java.net.URL;
import java.sql.ResultSetMetaData;

import javax.sql.rowset.CachedRowSet;

import org.apache.poi.util.SystemOutLogger;

import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import com.indocosmo.pos.common.PosConstants;
import com.indocosmo.pos.common.PosEnvSettings;
import com.indocosmo.pos.common.PosLog;
import com.indocosmo.pos.common.enums.PosProcessMessageType;
import com.indocosmo.pos.data.providers.shopdb.PosDayProcessProvider;
import com.indocosmo.pos.data.providers.shopdb.PosFlashMessageProvider;
import com.indocosmo.pos.data.providers.shopdb.PosDayProcessProvider.PosDayProcess;
import com.indocosmo.pos.data.providers.shopdb.sync.PosSyncTableSettingsProvider;
import com.indocosmo.pos.data.providers.terminaldb.PosSynchronizeProvider;
import com.indocosmo.pos.forms.PosMainMenuForm;
import com.indocosmo.pos.process.sync.AccessURL;
import com.indocosmo.pos.process.sync.SynchronizeFromServer;
import com.indocosmo.pos.process.sync.SynchronizeToServer;
import com.indocosmo.pos.process.sync.listner.IPosSyncListener;

/**
 * @author jojesh
 *
 */
public final class PosSyncUtil {

	public static void sync(boolean downSync, boolean upSync){

		if (upSync){
			PosSyncUtil.initSyncRecords();
			SynchronizeToServer.synchronizeAllTables();
			
			HmsUtil.postPendingPaymentToPMS();
		}
		if (downSync)  
			SynchronizeFromServer.syncFromWeb();
			
		 

	}
	
	/**
	 * 
	 */
	public static void initSyncRecords(){
		
		final PosSyncTableSettingsProvider pvdr=PosSyncTableSettingsProvider.getInstance();
		pvdr.initSyncTableRecords();
		
	}
	
	public static String getServerURL() throws Exception {

		String requestUrl = PosEnvSettings.getInstance().getWebArchiveURL();

		if (requestUrl == null || requestUrl.trim().length() <= 0) {
			requestUrl = PosEnvSettings.getInstance().getWebDataURL();
		}

		if (requestUrl == null || requestUrl.trim().length() <= 0) {
			throw new Exception(
					"Failed to get web url. Make sure that the sync urls are set.");
		}

		return requestUrl.substring(0, requestUrl.indexOf("/includes"));

	}
		
	
 /*
  * check for new updates from server,
  * add to flash message if there is any pending updates
  * 
  */
	public static void checkForSynUpdates() throws Exception {
		
		boolean result=true;
		try {
			String serverRequestUrl = PosSyncUtil.getServerURL() + "/includes/check_for_data_updates.php";

			AccessURL urlAccess = new AccessURL();
			urlAccess.setUrl(serverRequestUrl);
			 

			urlAccess.setRequest(PosSynchronizeProvider.getInstance()
					.getSynchronizeRequest());
			String jsonConfig = null;
			try {
				
				//System.out.println("nambi================>"+urlAccess.getResponseText());
				
				jsonConfig = urlAccess.getResponseText();
				
				if (jsonConfig == null || jsonConfig.trim().length() == 0) {
					throw new Exception(
							"Failed to get Sync Status. Contact administrator.");
				}
				
				JSONObject responseJSON = (JSONObject) JSONSerializer
						.toJSON(jsonConfig);
				
		 
				 result=(PosNumberUtil.parseIntegerSafely(String.valueOf(responseJSON.get("update_available")))==1);//.getBoolean("update_available");//(responseText!=null && responseText.trim().toLowerCase().equals("true"))?true:false;
				 
			} catch (Exception e) {
				jsonConfig = null;
				PosLog.write("Sync Update", "checkForSynUpdate",e);
				throw new Exception(
						"Support service has been halted. Please contact vendor.");
			}
		} catch (Exception e) {
			PosLog.write("Sync Update", "checkForSynUpdate",e);
			throw new Exception(
					"Support service has been halted. Please contact vendor.");
		}
		
		if (result){
			 PosFlashMessageProvider msgProvider=new PosFlashMessageProvider();
			 msgProvider.addSyncNotificationMessage();
		 }
	}
 
	
	
 
	 

 
}
