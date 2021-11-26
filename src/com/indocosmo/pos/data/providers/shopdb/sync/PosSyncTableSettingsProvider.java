/**
 * Need to complete this class
 * @author Ramesh S
 * @since 19th July 2012
 */
package com.indocosmo.pos.data.providers.shopdb.sync;

import java.sql.SQLException;

import javax.sql.rowset.CachedRowSet;

import com.indocosmo.pos.common.PosLog;
import com.indocosmo.pos.data.beans.BeanSyncTableSettings;
import com.indocosmo.pos.data.providers.shopdb.PosShopDBProviderBase;

/*
 * Since SQLite works on a single thread, at a time
 * only one thread can access data. So this Singleton
 * class is used to update records to SQLite database.
 *  
 * @author Ramesh S
 * @since 23rd October 2012
 */
public class PosSyncTableSettingsProvider extends PosShopDBProviderBase {

	private static PosSyncTableSettingsProvider instance = null;

	private PosSyncTableSettingsProvider() {
		super("v_sync_settings_terminal_to_server");
	}

	public static PosSyncTableSettingsProvider getInstance() {

		if (instance == null) {
			instance = new PosSyncTableSettingsProvider();
		}
		return instance;
	}

	// Retrieving all rows eligible for synchronization
	public CachedRowSet getAllRowsToSynch() {
		CachedRowSet crsTableSettings = null;

		crsTableSettings = getData("sync_order != ''", "sync_order");
		return crsTableSettings;
	}

	// Get CachedRowSet details by mentioning table id
	public synchronized CachedRowSet getCachedRowSetById(int tableId)
			throws Exception {
		CachedRowSet crs = null;

		try {
			crs = getData(" table_id = " + tableId + " order by sync_order");

		} catch (Exception err) {
			throw err;

		} finally {
			try {
				crs.close();

			} catch (SQLException e) {
				crs = null;
				PosLog.write(this, "getTableDetailsById", e);
			}
		}

		return crs;
	}

	// Get table details by mentioning table id
	public synchronized BeanSyncTableSettings getTableDetailsByName(String tableName)
				throws Exception {

		CachedRowSet crs = null;
		BeanSyncTableSettings syncTableSettings = new BeanSyncTableSettings();

		try {
			crs = getData(" table_name = '" + tableName+"'");

			if (crs != null && crs.next()) {
				syncTableSettings.setId(crs.getInt("id"));
				syncTableSettings.setSyncOrder(crs.getInt("sync_order"));
				syncTableSettings.setTableId(crs.getInt("table_id"));
				syncTableSettings.setTableName(crs.getString("table_name"));
				syncTableSettings.setParentTableId(crs
						.getInt("parent_table_id"));
				syncTableSettings.setTableCriteria(crs
						.getString("table_criteria"));
				syncTableSettings.setOrderBy(crs.getString("order_by"));
				syncTableSettings.setColumnToExclude(crs
						.getString("column_to_exclude"));
				syncTableSettings.setWebParamValue(crs
						.getString("web_param_value"));
				syncTableSettings.setRemarks(crs.getString("remarks"));
			} else {
				throw (new Exception(
						"No table with table name "
								+ tableName
								+ " exists.\nPlease check values in 'sync_table_settings' table."));
			}

		} finally {
			try {
				crs.close();

			} catch (SQLException e) {
				crs = null;
				PosLog.write(this, "getTableDetailsById", e);
			}
		}

		return syncTableSettings;
	}
	
	/**
	 * This function will reset the sync_status to 1 for all records with status 2 and 3;
	 * 
	 */
	public void initSyncTableRecords(){
		
		final String sql="SELECT table_name from v_sync_settings_terminal_to_server where parent_table_id=0";
		CachedRowSet crs =null;
		
		try {
			
			crs = executeQuery(sql);
			while(crs.next()){
				
				final String updSql="UPDATE "+ crs.getString("table_name") +" set sync_status=0 where sync_status in (2,3)";
				executeNonQuery(updSql);
			}
			crs.close();
			
		} catch (SQLException e) {
			
			
			PosLog.write(this, "initSyncTableRecords", e);
			
			try {
				if(crs!=null)
					crs.close();

			} catch (SQLException e1) {
				
				crs = null;
				PosLog.write(this, "getTableDetailsById", e1);
			}
		}
		
		
	}
}