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
public class PosUserGroupFunctionsProvider extends PosShopDBProviderBase{

	/**
	 * 
	 */
	public PosUserGroupFunctionsProvider() {
		super("pos_user_group_functions");
	}
	
	public boolean isValid(int userGroupId,int systemFunctionId){
		boolean isValid=false;
		String where = "user_group_id="+userGroupId+" and pos_system_function_id="+systemFunctionId;
		CachedRowSet res=null;
		res=executeQuery("select can_execute from pos_user_group_functions where "+where);
		try {
			while (res.next()) {
				isValid=res.getBoolean("can_execute");
			}
			res.close();
		} catch (SQLException e) {
			PosLog.write(this,"isValid",e);
		}
		return isValid;
	}
	
}
