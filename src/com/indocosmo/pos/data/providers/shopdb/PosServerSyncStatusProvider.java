/**
 * 
 */
package com.indocosmo.pos.data.providers.shopdb;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import javax.sql.rowset.CachedRowSet;

import org.apache.commons.collections.map.HashedMap;

import com.indocosmo.pos.common.utilities.PosDBUtil;
import com.indocosmo.pos.data.beans.BeanDataSync;
import com.indocosmo.pos.data.providers.PosDatabaseProvider;
import com.mysql.jdbc.util.ResultSetUtil;

/**
 * @author LAP-L530
 *
 */

/*
 * retrieve data from sync_terminal_table_settings 
  */
public class PosServerSyncStatusProvider extends PosShopDBProviderBase  {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indocosmo.pos.data.providers.PosDatabaseProvider#initProvider()
	 */
	protected static Connection mConnection;
	public ArrayList<String> syncTables;
	public ArrayList<BeanDataSync> syncStatus;
	public HashMap<String,String> tableRemarks;
	
	

	/**
	 * @param table
	 */
	public PosServerSyncStatusProvider(String table) {
		super("terminal_sync_table_settings");
	}
	
	@Override
	protected void initProvider() {
		mConnection = getConnection();//PosDBUtil.getInstance().getShopDBConnection();
	}

	
	public void getSyncTables() {
		syncTables = new ArrayList<>();
		tableRemarks=new HashMap<>();
		try {
			//String sql = "SELECT table_name,remarks FROM terminal_sync_table_settings WHERE is_sync_terminal_to_server=1 AND parent_table_id=0";
			CachedRowSet cachedRowSet = getData("is_sync_terminal_to_server=1 AND parent_table_id=0");//executeQuery(sql);
			while (cachedRowSet.next()) {
				syncTables.add(cachedRowSet.getString("table_name"));
				tableRemarks.put(cachedRowSet.getString("table_name"),cachedRowSet.getString("remarks"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public PosServerSyncStatusProvider() {
		super();
	}


	public ArrayList<BeanDataSync> getSyncRows(){
		getSyncTables();// get table list
		syncStatus = new ArrayList<>();
	
		/*
		 * 
		 * syncToStart=0
		   syncSuccessful=1
		   syncInProgress=2
           syncFailed=3
		 * 
		 * */
		
		for (int i = 0; i < syncTables.size(); i++) {
			BeanDataSync beanDataSync = new BeanDataSync();
			try {
							
				String sql = "SELECT (SELECT COUNT(*) AS count FROM " + syncTables.get(i);
				
				if(syncTables.get(i).equals("order_hdrs")) {
					sql += " WHERE sync_status=3 AND status IN (3,4)) AS error, (SELECT COUNT(*) AS count FROM " + syncTables.get(i) + 
							" WHERE sync_status=0 AND status IN (3,4) ) AS pending FROM " + syncTables.get(i);
				}
				else
				{
					sql += " WHERE sync_status=3) AS error, (SELECT COUNT(*) AS count FROM " + syncTables.get(i) + 
							" WHERE sync_status=0) AS pending FROM " + syncTables.get(i);
				}
					
				
				beanDataSync.setRemarks(tableRemarks.get(syncTables.get(i)));
				Statement statement_combined = mConnection.createStatement();
				ResultSet resultSet_combined = statement_combined.executeQuery(sql);
				while(resultSet_combined.next())
				{
					beanDataSync.setTableName(syncTables.get(i));
					beanDataSync.setDescription("");
					beanDataSync.setErrorRecords(resultSet_combined.getInt("error"));
					//beanDataSync.setRemarks(tableRemarks.get(syncTables.get(i)));
					beanDataSync.setPendingRecords(resultSet_combined.getInt("pending"));
				}
						
				syncStatus.add(beanDataSync);
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
		//mConnection.close();
		return syncStatus;

	}

}
