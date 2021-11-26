/**
 * 
 */
package com.indocosmo.pos.data.providers.shopdb.sync;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import javax.sql.rowset.CachedRowSet;

import com.indocosmo.pos.common.PosEnvSettings;
import com.indocosmo.pos.common.PosLog;
import com.indocosmo.pos.data.beans.BeanSyncTableSettings;
import com.indocosmo.pos.data.beans.BeanOrderHeader.PosOrderStatus;
import com.indocosmo.pos.data.providers.shopdb.PosShopDBProviderBase;

/*
 * Since SQLite works on a single thread, at a time
 * only one thread can access data. So this Singleton
 * class is used to update records to SQLite database.
 *  
 * @author Ramesh S
 * @since 23rd October 2012
 */
public class PosSyncTableProvider extends PosShopDBProviderBase {

	private BeanSyncTableSettings tableSettingsObj = null;
	
	// Implementing Singleton pattern
	public PosSyncTableProvider(BeanSyncTableSettings tableSettingsObj)
			throws Exception {
		super(tableSettingsObj.getTableName());
		this.tableSettingsObj = tableSettingsObj;
	}

	public BeanSyncTableSettings getTableSettings() {
		return tableSettingsObj;
	}

	public void setTableSettings(BeanSyncTableSettings tableSettingsObj) {
		this.tableSettingsObj = tableSettingsObj;
	}

	//Retrieve data for creating JSON
	public synchronized CachedRowSet getDataForJSON(
					BeanSyncTableSettings tableSettings) throws Exception {
		
		StringBuffer sql = new StringBuffer();
		
		sql.append("select " + createIdentifierColumn(tableSettings) 
					+ " as _identifier," + getColumns(tableSettings));

		
		//Capturing table names to be used in SQL. 
		if (tableSettings.getTableList() != null
				&& tableSettings.getTableList().trim().length() > 0) {
			
			sql.append(" from " + tableSettings.getTableList());
			
		} else {
			sql.append(" from " + tableSettings.getTableName());
		}

		//Capturing SQL criteria
		if (tableSettings.getTableCriteria() != null
				&& tableSettings.getTableCriteria().trim().length() > 0) {
			sql.append(" where " + tableSettings.getTableCriteria());
		}
		
		//Capturing order of SQL
		if (tableSettings.getOrderBy() != null
				&& tableSettings.getOrderBy().trim().length() > 0) {
			
			sql.append(" order by " + tableSettings.getOrderBy());
		}
		
		PosLog.debug("getData()=" + sql.toString());

		return executeQuery(sql.toString());
	}
	
	//Retrieve data for creating JSON
	public synchronized CachedRowSet getDataIdentifiers(
					BeanSyncTableSettings tableSettings) throws Exception {
		
		StringBuffer sql = new StringBuffer();
		String identifier = createIdentifierColumn(tableSettings);
		
		sql.append("select " +  identifier + " as _identifier, sync_status");

		//Capturing table names to be used in SQL. 
		if (tableSettings.getTableList() != null
				&& tableSettings.getTableList().trim().length() > 0) {
			sql.append(" from " + tableSettings.getTableList());
			
		} else {
			sql.append(" from " + tableSettings.getTableName());
		}

		//Capturing SQL criteria
		sql.append(" where sync_status in (" 
				+ PosEnvSettings.getInstance().getSyncToStart() + "," 
				+ PosEnvSettings.getInstance().getSyncFailed() + ")");
		
		if(tableSettings.getTableName().equalsIgnoreCase("order_hdrs")){
			
			sql.append(" and status in("+PosOrderStatus.Closed.getCode()+","+PosOrderStatus.Refunded.getCode()+")");
		}
		if (tableSettings.getTableCriteria() != null
				&& tableSettings.getTableCriteria().trim().length() > 0) {
			
			sql.append(" and " + tableSettings.getTableCriteria());
		}
		
		//Capturing order of SQL
		if (tableSettings.getOrderBy() != null
				&& tableSettings.getOrderBy().trim().length() > 0) {
			
			sql.append(" order by " + tableSettings.getOrderBy());
		}
		
		PosLog.debug("getDataIdentifiers()=" + sql.toString());
		return executeQuery(sql.toString());
	}

	//Creates row identifier condition using primary key
	private synchronized String createIdentifierColumn(
						BeanSyncTableSettings tableSettingsObject) throws Exception {
		StringBuffer identifier = null;
		String columnName = null;
		DatabaseMetaData meta = mConnection.getMetaData();
		ResultSet rs = meta.getPrimaryKeys(null, null, tableSettingsObject.getTableName());
		
		while (rs.next()) {
			columnName = rs.getString("COLUMN_NAME");	//Retrieving primary key
			if (identifier == null) {
				identifier = new StringBuffer();
//				identifier.append("(\"" + tableSettingsObject.getTableName() + "." + columnName 
//						+ "='\" || " + tableSettingsObject.getTableName() + "." + columnName + " || \"'\"");
				
				identifier.append("concat(\"" + tableSettingsObject.getTableName() + "." + columnName 
				+ "='\" , " + tableSettingsObject.getTableName() + "." + columnName + " , \"'\"");
				
				
			} else {
				identifier.append(", \" and \",\"" + tableSettingsObject.getTableName() + "." + columnName 
						+ "='\" , " + tableSettingsObject.getTableName() + "." + columnName + " , \"'\"");
			}
	    }
		if (identifier != null) {
			identifier.append(")");
		}
		return identifier.toString(); 
	}
		
	//Retrieving required columns for forming SQL statement
	private synchronized String getColumns(BeanSyncTableSettings tableSettingsObject) throws Exception {
		StringBuffer stbColumns = new StringBuffer();
		List<String> listColToExclude = null;
		CachedRowSet crsTableDataProvider = executeQuery("select * from "+ tableSettingsObject.getTableName() + " where (1>1)");
		
		if (tableSettingsObject.getColumnToExclude() != null) {
			listColToExclude = Arrays.asList(tableSettingsObject.getColumnToExclude()
								.replaceAll("\\s", "").toLowerCase().split(","));
		}
		
		ResultSetMetaData metaData = crsTableDataProvider.getMetaData();
		int numOfCols = metaData.getColumnCount();

		for (int ctr = 1; ctr <= numOfCols; ctr++) {
			if (listColToExclude == null || !listColToExclude.contains(metaData.getColumnName(ctr).toLowerCase())) {
				if (ctr > 1) {
					stbColumns.append(",");
				}
				stbColumns.append(tableSettingsObject.getTableName() 
								+ "." + metaData.getColumnName(ctr));
			}
		}
		return stbColumns.toString();
	}
		
	
	public synchronized int updateSyncStatus(CachedRowSet crsHeader, String status) throws SQLException {
		return updateSyncStatus(crsHeader, status, null);
	}
	
	public synchronized int updateSyncStatus(CachedRowSet crsHeader, String status, String message) throws SQLException {
		String sql = null;
		if (message != null) {
			sql = "update " + tableSettingsObj.getTableName() + " set sync_status = \""
						+ status + "\", sync_message = \"" + message + "\""
						+ " where " + crsHeader.getString("_identifier") + ";";
		} else {
			sql = "update " + tableSettingsObj.getTableName() + " set sync_status = \""
						+ status + "\""
						+ " where " + crsHeader.getString("_identifier") + ";";
		}
				
		return executeNonQuery(sql);
	}
}