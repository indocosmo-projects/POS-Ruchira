/**
 * 
 */
package com.indocosmo.pos.data.providers.shopdb;

import java.sql.SQLException;

import javax.sql.rowset.CachedRowSet;

import com.indocosmo.pos.common.PosLog;

/**
 * @author deepak
 * 
 */
public class PosShopDbUpdate extends PosShopDBProviderBase {

	/**
	 * 
	 */
	public PosShopDbUpdate() {
		super("shop_db_version");
	}
	public int getShopDbVersion() {

		int versionNo = 0;
		CachedRowSet crs = null;
		try {
			crs = executeQuery("select version from shop_db_version");
			if (crs != null && crs.next()) {
				versionNo = crs.getInt(1);
			}
		} catch (SQLException e) {
			PosLog.write(this, "getShopDbVersion", e);
		}

		return versionNo;

	}

	public boolean shopUpdate(String SQL) {
		boolean status = false;
		try {
			beginTrans();
			executeNonQuery(SQL);
			commitTrans();
			status = true;
		} catch (SQLException e) {
			PosLog.write(this, "shopUpdate", e);
			try {
				rollBack();
			} catch (SQLException e1) {
				PosLog.write(this, "shopUpdate", e);
			}
		}

		return status;

	}

	public boolean updateVersion(int version) {
		boolean status = false;
		try {
			beginTrans();
			deleteData();
			executeNonQuery("insert into shop_db_version values(" + version
					+ ") ");
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
