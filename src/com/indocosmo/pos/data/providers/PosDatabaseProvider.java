/**
 * Use this class to write db related functions.
 */
package com.indocosmo.pos.data.providers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;

import javax.sql.rowset.CachedRowSet;

import com.indocosmo.pos.common.PosEnvSettings;
import com.indocosmo.pos.common.PosLog;
import com.indocosmo.pos.common.utilities.PosDBUtil;
import com.indocosmo.pos.common.utilities.PosDateUtil;
import com.sun.rowset.CachedRowSetImpl;


public abstract class PosDatabaseProvider {
	
	public enum SortOrder{
		Ascending,
		Descending 
	}

	protected PreparedStatement mPrepStatement;
	protected String mTablename;
	protected String mIDField;

	public PosDatabaseProvider(String idField,String table) {
		mIDField=idField;
		mTablename=table;
		initProvider();
	}

	public PosDatabaseProvider(String table) {
		mTablename=table;
		initProvider();
	}

	public PosDatabaseProvider() {
		initProvider();
	}
	
	public CachedRowSet executeQuery(final String sql)
	{	
		CachedRowSet crs=null;
		try {
			PosLog.debug(sql);
			ResultSet rs = null ;
			Statement statement=getConnection().createStatement();
			crs=new CachedRowSetImpl();
			final String startTime=PosDateUtil.getDateTime("yyyy-MM-dd HH:mm:ss.SSSSSS");
			PosLog.debug("Query Started At : " + startTime);
			rs=statement.executeQuery(sql);
			final String endTime=PosDateUtil.getDateTime("yyyy-MM-dd HH:mm:ss.SSSSSS");
			PosLog.debug("Query Ended At : " + endTime);
			if(PosEnvSettings.getInstance().isLogDebugMode()){
				
				try {
					
					final long dif= PosDateUtil.getDiff(startTime,endTime,"yyyy-MM-dd HH:mm:ss.SSSSSS");
					PosLog.debug("Total Time : "+ dif);
					
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			crs.populate(rs);
			rs.close();
			statement.close();
			
		} catch (SQLException e) {
			PosLog.write(this,"executeQuery",e);
			crs=null;
		}
		return crs;	
	}

	/**
	 * Executes queries that do not returns values. eg. INSERT, UPDATE, DELETE
	 * @param sql
	 * @return The number of rows affected.
	 * @throws SQLException 
	 */
	public int executeNonQuery(final String sql) throws SQLException {	
		int update_count=0;
//		try {
			PosLog.debug(sql);
			Statement statement=getConnection().createStatement();
			update_count = statement.executeUpdate(sql);
			statement.close();
			PosLog.debug(update_count + " Rows modified.");
//		} catch (SQLException e) {
//			PosLog.write(this,"executeNonQuery",e);
//		}
		return update_count;
	}

	public CachedRowSet getData(){
		return executeQuery("select * from "+ mTablename);
	}

	public CachedRowSet getData(final String where){
		return executeQuery("select * from "+ mTablename + 
				((where!=null && !where.equals(""))?" where " + where:""));
	}
	
	public CachedRowSet getData(final String where, String orderBy){
		return executeQuery("select * from "+ mTablename + 
				((where!=null && !where.equals(""))?" where " + where:"") + 
				((orderBy!=null && !orderBy.equals(""))?" order by " + orderBy:"") );
	}
	
	public CachedRowSet getSortedData(final String sortField,final SortOrder sortOrder){
		return executeQuery("select * from "+ mTablename + " order by " + 
				sortField + ((sortOrder==SortOrder.Ascending)?" asc":" desc"));
	}

	public CachedRowSet getSortedData(final String sortField){
		return getSortedData(sortField,SortOrder.Ascending);
	}

	public int getMaxId(){
		return getMaxId(mIDField, mTablename);
	}

	public int getMaxId(String uniqueId){
		return getMaxId(uniqueId, mTablename);
	}


	public int getMaxId(String uniqueId,String tablename){
		int maxID = 0;	
		String sql="SELECT MAX("+ uniqueId +") FROM "+ tablename ;	
		CachedRowSet res =executeQuery(sql);
		try {
			if (res.next()) {
				maxID = res.getInt(1);
				res.close();
			}
		} catch (SQLException e) {
			PosLog.write(this,"getMaxId",e);
		}

		return maxID;
	}

	public int getNextAutoId(){
		return getNextAutoId(mTablename);
	}

	public int getNextAutoId(String tableName){
		int nextID = 0;	
		String sql=null;
		switch(PosDBUtil.getInstance().getDatabaseType()){
		case SQLITE:
			sql="SELECT seq FROM SQLITE_SEQUENCE WHERE name='"+tableName+"';";
			break;
		case MYSQL:
			sql="";
			break;
		default:
			sql="";	
		}
		CachedRowSet res =executeQuery(sql);
		try {
			if (res.next()) {
				nextID = res.getInt(1);
				res.close();
			}
		} catch (SQLException e) {
			PosLog.write(this,"getNextAutoId",e);
		}
		return ++nextID;
	}
	
	protected void executeStatement(PreparedStatement prep) throws SQLException{
//			prep.executeBatch();
	}

	public boolean executePreparedStatement(PreparedStatement prep){
		try {
			getConnection().setAutoCommit(false);
			executeStatement(prep);
			getConnection().commit();
			getConnection().setAutoCommit(true);
			return true;
		} catch (SQLException e) {
			try {
				getConnection().rollback();
			} catch (SQLException e1) {
				PosLog.write(this,"executePreparedStatement",e);
			}
			PosLog.write(this,"executePreparedStatement",e);
		}
		return false;
	}

	protected void executeStatement(PreparedStatement prepHdr,PreparedStatement prepDtl) throws SQLException{
			prepHdr.execute();
			prepDtl.executeBatch();
	}
	
	public boolean executePreparedStatement(PreparedStatement prepHdr,PreparedStatement prepDtl){
		try {
			getConnection().setAutoCommit(false);
			executeStatement(prepHdr,prepDtl);
			getConnection().commit();
			getConnection().setAutoCommit(true);
			return true;
		} catch (SQLException e) {
			try {
				getConnection().rollback();
			} catch (SQLException e1) {
				PosLog.write(this,"executePreparedStatement",e1);
			}
			PosLog.write(this,"executePreparedStatement",e);
		}
		return false;
	}
	
	public boolean isExist(String where){
		
		return isExist(mTablename, where);
	}

	public boolean isExist(String tableName, String where){
		
		boolean bExist=false;
		String sql="select 1 from "+ tableName + " where " + where+ " LIMIT 1";
		CachedRowSet res =executeQuery(sql);
		try {
			if (res.next()) {
				PosLog.debug("isExist value "+res.getInt(1));
				bExist = res.getInt(1)>0;
				res.close();
			}
		} catch (SQLException e) {
			PosLog.write(this,"isExist",e);
		}

		return bExist;
	}
	
	

	public int deleteData(final String where) throws SQLException{
		return executeNonQuery("delete from "+ mTablename + " where " + where);
	} 	
	
	public int deleteData(final String tableName,final String where) throws SQLException{
		return executeNonQuery("delete from "+ tableName + " where " + where);
	} 	
	
	 
	public StringBuffer addToHistory(final String where) throws SQLException {
		
		return addToHistory(mTablename,where);
	}
	
	public StringBuffer addToHistory(final String tableName ,final String where) throws SQLException {
 
		StringBuffer sqlStatements=new StringBuffer();
		final String historyTableName=tableName.trim() + "_hist";
		final String deleteSql="delete from  " + historyTableName + "   where " + where +";";
		final String insertSql="insert into " + historyTableName + " select * from " + tableName + " where " + where + ";";
		sqlStatements.append(deleteSql);
		sqlStatements.append(insertSql);
		return sqlStatements;
	}
	public int deleteData() throws SQLException{
		return executeNonQuery("delete from "+ mTablename);
	}

	public int markAsDeleted(final String where, int deletedBy) throws SQLException{
	
		final String sql="update "+mTablename+ " set is_deleted=1, updated_by="+deletedBy+", updated_at='"+ PosDateUtil.getDateTime()+"' where "+where;
    	
    	return executeNonQuery(sql);
	}

	public void beginTrans() throws SQLException{
		getConnection().setAutoCommit(false);
	}

	public void commitTrans() throws SQLException{
		getConnection().commit();
		getConnection().setAutoCommit(true);
	}
	
	public void rollBack() throws SQLException{
		getConnection().rollback();
		getConnection().setAutoCommit(true);
	}
	
	public PreparedStatement getPreparedStatement(String sql) throws SQLException{
		return getConnection().prepareStatement(sql);
	}
	
	protected abstract void initProvider();
	protected abstract Connection getConnection();

}
