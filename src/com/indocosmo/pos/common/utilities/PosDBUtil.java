package com.indocosmo.pos.common.utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.sqlite.Function;

import com.indocosmo.pos.common.PosLog;
import com.indocosmo.pos.common.enums.PosDbType;
import com.mysql.jdbc.exceptions.jdbc4.CommunicationsException;

public final class PosDBUtil {

//	public enum DatabaseType{
//		mysql,
//		sqlite,
//		mariadb
//	}

	private static Connection mShopDBCon;
	private static Connection mTerminalDBCon;
	private PosDbType mDatabaseType;
	
	private String mMySQLServerPath;
	private String mMySQLServerPort;
	private String mMySQLServerUser;
	private String mMySQLServerPassword;
	private String mMySQLShopDBName;
	
	private String mSqLiteDBFolder;
	private String mSqLiteShopDBFile;
	private String mSqLiteTerminalDBFile;

	private static PosDBUtil posDbUtil;

	private PosDBUtil(){

	}

	public static synchronized PosDBUtil getInstance(){
		if(posDbUtil==null)
			posDbUtil=new PosDBUtil();
		return posDbUtil;
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException();
	}

	public Connection getShopDBConnection() {
		try {
			if(mShopDBCon==null || mShopDBCon.isClosed() || (mDatabaseType == PosDbType.MYSQL && !mShopDBCon.isValid(2))){
				openShopDbConnection();
				initDbConnection(mShopDBCon);
			}
		} catch (SQLException e) {
			
			PosLog.write(this,"getConnection: ",e);
		}
		return mShopDBCon;
	}
	


	public Connection getTerminalDBConnection(){
		
		try {
			if(mTerminalDBCon==null || mTerminalDBCon.isClosed())
				openTerminalDbConnection();
		} catch (SQLException e) {
			PosLog.write(this,"getConnection: ",e);
		}
		return mTerminalDBCon;
	}

	private void openShopDbConnection() {

		try {
			
			if(mDatabaseType==PosDbType.SQLITE){
				final String conString=getSqliteConnString(mSqLiteShopDBFile);
				mShopDBCon = DriverManager.getConnection(conString);
			}else if(mDatabaseType==PosDbType.MYSQL){
				final String conString=getMySqlConnString(mMySQLShopDBName);
				mShopDBCon = DriverManager.getConnection(conString);
			}else{
				final String conString=getMariaDBConnString(mMySQLShopDBName);
				mShopDBCon = DriverManager.getConnection(conString);
			}

		} catch (ClassNotFoundException e) {
			PosLog.write(this,"openShopDbConnection: ",e);
			
		} catch (CommunicationsException e) {
			PosLog.write(this,"openShopDbConnection: ",e);
			
		} catch (SQLException e) {
			PosLog.write(this,"openShopDbConnection: ",e);
		}		
		
	}
	
	private void openTerminalDbConnection() {
		try {
			final String conString=getSqliteConnString(mSqLiteTerminalDBFile);
			mTerminalDBCon = DriverManager.getConnection(conString);
		} catch (ClassNotFoundException e) {
			PosLog.write(this,"openTerminalDbConnection: ",e);
		} catch (SQLException e) {
			PosLog.write(this,"openTerminalDbConnection: ",e);
		}		
	}

	public static void init(Properties properties){
		getInstance().loadDatabaseSettings(properties);
	}

	private String getSqliteConnString(String dbName) throws ClassNotFoundException{
		Class.forName("org.sqlite.JDBC");
		final String pathToDb=mSqLiteDBFolder+"/"+dbName;
		final String conString="jdbc:sqlite:"+pathToDb;
		return conString;
	}

	private String getMySqlConnString(String dbName) throws ClassNotFoundException{
		Class.forName("com.mysql.jdbc.Driver");
		//final String pathToDb=mServerPath+"/"+dbName;
		//final String conString="jdbc:mysql:"+pathToDb;
		
		final String conString="jdbc:mysql://"+mMySQLServerPath+":"+mMySQLServerPort+"/"+mMySQLShopDBName+"?"
				+ "user="+mMySQLServerUser
				+ "&password="+mMySQLServerPassword
				+ "&tinyInt1isBit=false"
				+ "&useOldAliasMetadataBehavior=true"
				+ "&allowMultiQueries=true"
				+ "&useUnicode=true"
				+ "&characterEncoding=utf-8";
		
		return conString;
	}
	
	private String getMariaDBConnString(String dbName) throws ClassNotFoundException{
		Class.forName("org.mariadb.jdbc.Driver");
		//final String pathToDb=mServerPath+"/"+dbName;
		//final String conString="jdbc:mysql:"+pathToDb;
		
		final String conString="jdbc:mariadb://"+mMySQLServerPath+":"+mMySQLServerPort+"/"+mMySQLShopDBName+"?"
				+ "user="+mMySQLServerUser
				+ "&password="+mMySQLServerPassword
				+ "&tinyInt1isBit=false"
				+ "&useOldAliasMetadataBehavior=true"
				+ "&allowMultiQueries=true"
				+ "&useUnicode=true"
				+ "&characterEncoding=utf-8";
		
		return conString;
	}

	
	public void closeShopDbCon() 
	{
		if(mShopDBCon==null) return;
		try {
			mShopDBCon.close();
		} catch (SQLException e) {
			PosLog.write(this,"closeShopDbCon: ",e);
		}
	}

	public static void dispose(){
		getInstance().closeShopDbCon();
	}

	private void loadDatabaseSettings(Properties mProperties){
		mDatabaseType= PosDbType.get(mProperties.getProperty("shopDBType"));// mProperties.getProperty("shopDBType").equals("sqlite")?DatabaseType.sqlite:DatabaseType.mysql;
		mSqLiteDBFolder=mProperties.getProperty("sqliteDBFolder");
		switch (mDatabaseType) {
		case SQLITE:
			mSqLiteShopDBFile=mProperties.getProperty("sqliteShopDB");
			break;
		case MYSQL:
		case MARIADB:
			mMySQLServerPath=mProperties.getProperty("dbServer");
			mMySQLServerPort=mProperties.getProperty("serverPort");
			mMySQLServerUser=mProperties.getProperty("serverUser");
			mMySQLServerPassword=mProperties.getProperty("serverUserPWD");
			mMySQLShopDBName=mProperties.getProperty("serverShopDB"); 
		}
		mSqLiteTerminalDBFile=mProperties.getProperty("sqliteTerminalDB");
	}

	/**
	 * @return
	 */
	public PosDbType getDatabaseType() {
		return mDatabaseType;
	}

	/**
	 * server path: system folder location when the database type is sqlite 
	 * and dbserver address when database type is mysql
	 * @return the database server path.
	 */
	public String getServerPath() {
		return mMySQLServerPath;
	}
	 
	public String getServerPort() {
		return mMySQLServerPort;
	}

	/**
	 * @return the database server user
	 */
	public String getServerUser() {
		return mMySQLServerUser;
	}

	/**
	 * 
	 * @return the database server user password
	 */
	public String getServerPassword() {
		return mMySQLServerPassword;
	}

	/**
	 * @return the database name
	 */
	public String getShopDBName() {
		return mMySQLShopDBName;
	}
	
	/**
	 * @param mShopDBCon2
	 * @throws SQLException 
	 */
	private void initDbConnection(Connection con) throws SQLException {
		switch (mDatabaseType) {
		case SQLITE:
			initSqLiteDatabase(con);
			break;
		case MYSQL:
			break;
		case MARIADB:
			break;
		}
	}
	
	/**
	 * @param con
	 * @throws SQLException
	 */
	public void initSqLiteDatabase(final Connection con ) throws SQLException{
		
		/**
		 * Porting mysql concat function to sqlite
		 */
		Function.create(con, "concat", PosSQLiteDBUtil.concat());
		
		/**
		 * Porting mysql now function to sqlite
		 */
		Function.create(con, "now", PosSQLiteDBUtil.now());
		
		/**
		 * Porting mysql getInvoiceNo function to sqlite
		 */
		Function.create(con, "getInvoiceNo", PosSQLiteDBUtil.getInvoiceNumber(con));
		
		/**
		 * Porting mysql getOrderQueueNo function to sqlite
		 */
		Function.create(con, "getOrderQueueNo", PosSQLiteDBUtil.getOrderQueueNumber(con));
		
		/***
		 * Poritng mysql resetOrderQueueNumber function. 
		 */
		Function.create(con, "resetOrderQueueNumber", PosSQLiteDBUtil.resetOrderQueueNumber(con));
		
		/**
		 * Porting mysql getKitchenQueueNo function to sqlite
		 */
		Function.create(con, "getKitchenQueueNo", PosSQLiteDBUtil.getKitchenQueueNo(con));
		
		
		/**
		 * Porting mysql getKitchenQueueNo function to sqlite
		 */
		Function.create(con, "getKitchenQueueNo", PosSQLiteDBUtil.getKitchenQueueNo(con));
		
		
 
		/**
		 * Porting mysql resetKitchenQueueNumber function to sqlite
		 */
		Function.create(con, "resetKitchenQueueNumber", PosSQLiteDBUtil.resetKitchenQueueNumber(con));
	
		
		
	
	}
	
	
	public boolean isValidConnection(Connection connection) {
		
		boolean result=true;
		try {
			if(connection==null || connection.isClosed() || (mDatabaseType == PosDbType.MYSQL && !connection.isValid(2)))
				result=false;
				
		} catch (SQLException e) {
			
			result=false;
			PosLog.write(this,"isValidConnection: ",e);
		}
		return result;
	}
	

}
