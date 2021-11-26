/**
 * 
 */
package com.indocosmo.pos.data.providers.shopdb;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.sql.rowset.CachedRowSet;

import com.indocosmo.pos.common.PosLog;
import com.indocosmo.pos.data.beans.BeanAccessLog;

/**
 * @author Deepak 
 *
 */
public class PosAccessLogProvider extends PosShopDBProviderBase {

	/**
	 * 
	 */
	public PosAccessLogProvider() {
		super("access_log");
	}
	
	public ArrayList<BeanAccessLog> getAccessLog(){
		
		ArrayList<BeanAccessLog> accessLogList = null;
		CachedRowSet crs = getData();
		if(crs!=null){
			accessLogList = new ArrayList<BeanAccessLog>();
			try {
				while(crs.next()){
					BeanAccessLog accessLogObject = createAccessLogFromRecordSet(crs);
					accessLogList.add(accessLogObject);
				}
			} catch (SQLException e) {
				accessLogList = null;
				PosLog.write(this, "getAccessLog", e);
			}finally{
				try {
					crs.close();
					
				} catch (SQLException e) {
					accessLogList = null;
					PosLog.write(this, "getAccessLog", e);
				}
			}
		}
		return accessLogList;
	}

	/**
	 * @param crs
	 * @return
	 * @throws SQLException 
	 */
	private BeanAccessLog createAccessLogFromRecordSet(CachedRowSet crs) throws SQLException {
		BeanAccessLog accessLog = new BeanAccessLog();
		accessLog.setFunctionName(crs.getString("function_name"));
		accessLog.setUserName(crs.getString("user_name"));
		accessLog.setAccessTime(crs.getString("access_time"));
		return accessLog;
	}
	
	public void updateAccessLog(BeanAccessLog accessLogObject){
		
		String insertSQL = "insert into access_log (function_name,user_name,access_time) values(?,?,?)";
		PreparedStatement prepStatment = null;
		try {
			beginTrans();
			prepStatment = getPreparedStatement(insertSQL);
			prepStatment.setString(1,accessLogObject.getFunctionName());
			prepStatment.setString(2, accessLogObject.getUserName());
			prepStatment.setString(3, accessLogObject.getAccessTime());
			prepStatment.executeUpdate();
			commitTrans();
		} catch (SQLException e) {
			PosLog.write(this, "updateAccessLog", e);
			try {
				rollBack();
			} catch (SQLException e1) {
				PosLog.write(this, "updateAccessLog", e1);
			}
		}finally{
			try {
				prepStatment.close();
			} catch (SQLException e) {
				PosLog.write(this, "updateAccessLog", e);
			}
		}
		
	}
	@Override
	public int deleteData(String where) throws SQLException {
	
		final String sql= "delete from access_log where "+where;
		
		return executeNonQuery(sql);
		
	}
	
	@Override
	public int purgeData(String dateTo) throws SQLException{
		
		final String where="access_time <='" + dateTo + "'";
		return deleteData(where);
	}
	
}
