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
public class PosSystemFunctionsProvider extends PosShopDBProviderBase{

	/**
	 * 
	 */
	public PosSystemFunctionsProvider() {
		super("pos_system_functions");
	}
	
	public int getSystemFunctionIdByCode(String code){
		int id = 0;
		String where = "code = '"+code+"'";
		CachedRowSet res=null;
		res=executeQuery("select id from pos_system_functions where "+where);
		try {
			while (res.next()) {
				id=res.getInt("id");
			}
			res.close();
		} catch (SQLException e) {
			PosLog.write(this,"getSystemFunctionIdByCode",e);
		}
		return id;
	}
}
