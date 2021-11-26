package com.indocosmo.pos.data.providers.terminaldb;

import javax.sql.rowset.CachedRowSet;

import net.sf.json.JSONObject;

import com.indocosmo.pos.common.PosEnvSettings;
import com.indocosmo.pos.common.PosLog;
import com.indocosmo.pos.data.providers.shopdb.PosShopDBProviderBase;


public class PosSynchronizeProvider extends PosShopDBProviderBase {

	private static PosSynchronizeProvider instance = null;
	private CachedRowSet crsSync = null;
	private final String webParamValue = "synchronize=";
	private String latestSyncFile = null;

	//Implementing Singleton pattern
	private PosSynchronizeProvider() {
		super("terminal_sync_status");
	}
	
	
	public static PosSynchronizeProvider getInstance() {

		if (instance == null) {
			instance = new PosSynchronizeProvider();
		}
		return instance;
	}

	
	//Generates JSON data according to the condition parameter
	public String getSynchronizeRequest() throws Exception {
		JSONObject jsonObj = new JSONObject();
		StringBuffer strBffrJSON = new StringBuffer();
		strBffrJSON.append(webParamValue);
		
		jsonObj.clear();
		String fileName = getLatestSyncFile();
		if (fileName == null) {
			
			fileName = PosEnvSettings.getInstance().getShop().getCode().toUpperCase().trim() + "-190001010101.sql";
		}
			
		jsonObj.put("file_name", fileName);
		strBffrJSON.append(jsonObj.toString());

		PosLog.debug("Request string:" + strBffrJSON);
		return strBffrJSON.toString();
	}
	
	
	//Retreives latest synchronized file name.
	public String getLatestSyncFile() throws Exception {
		
//		if (latestSyncFile != null) {
//			return latestSyncFile;
//		}
		
		String sql = "SELECT file_name FROM "+mTablename+" order by sync_date desc, file_name desc limit 1";
		
		// Retrieving rows to be synchronized
		crsSync = executeQuery(sql);
		
		if (crsSync.next())
			latestSyncFile = crsSync.getString("file_name");

		return latestSyncFile;
	}

	
	@Override
	protected Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException();
	}

	
	public void updateSyncStatus(String fileName, boolean syncStatus)
			throws Exception {
		final String sql = "insert into "+mTablename+" (file_name, sync_status) values ('"
				+ fileName + "'," + (syncStatus?1:0) + ")";

		executeNonQuery(sql);
	}
}
