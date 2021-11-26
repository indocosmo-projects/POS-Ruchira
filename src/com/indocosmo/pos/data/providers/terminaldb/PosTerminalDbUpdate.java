/**
 * 
 */
package com.indocosmo.pos.data.providers.terminaldb;

import java.sql.SQLException;

import javax.sql.rowset.CachedRowSet;

import com.indocosmo.pos.common.PosLog;

/**
 * @author deepak
 *
 */
public class PosTerminalDbUpdate extends PosTerminalDBProvider{
	
	
	/**
	 * 
	 */
	public PosTerminalDbUpdate() {
		super("terminal_db_version");
	}
	public int getTerminalDbVersion(){
		
		int versionNo=0;
		CachedRowSet crs = null;
		try {
			crs = executeQuery("select version from terminal_db_version");
			if (crs != null && crs.next()) {
				versionNo = crs.getInt(1);
			}
		} catch (SQLException e) {
			PosLog.write(this, "getTerminalDbVersion", e);
		}
		
		return versionNo;
		
	}
	
	public boolean terminalUpdate(String SQl){
		boolean status = false;
		try {
			beginTrans();
			executeNonQuery(SQl);
			commitTrans();
			status = true;
		} catch (SQLException e) {
			PosLog.write(this, "terminalUpdate", e);
			try {
				rollBack();
			} catch (SQLException e1) {
				PosLog.write(this, "terminalUpdate", e);
			}
		}
		return status;
	}
	
	public boolean updateVersion(int version){
		boolean status = false;
		try {
			beginTrans();
			deleteData();
			executeNonQuery("insert into terminal_db_version values("+version+") ");
			commitTrans();
			status = true;
		} catch (SQLException e) {
			PosLog.write(this, "updateVersion", e);
			try {
				rollBack();
			} catch (SQLException e1) {
				PosLog.write(this, "updateVersion", e);
			}
		}
		return status;
	}

}
