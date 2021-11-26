/**
 * 
 */
package com.indocosmo.pos.data.providers.shopdb;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.sql.rowset.CachedRowSet;

import com.indocosmo.pos.common.PosLog;
import com.indocosmo.pos.data.beans.BeanUser;

/**
 * @author Ramesh S.
 * @since 19th July 2012
 * 
 */
public class PosUsersProvider extends PosShopDBProviderBase {
	
	private static Map<String, BeanUser> userCodeMap;
	private static Map<String, BeanUser> userCardMap;
	private static Map<Integer, BeanUser> userIdMap;

	/**
	 * 
	 */
	public PosUsersProvider() {
		super("v_users");
	}
	
	public PosUsersProvider(String table) {
		super(table);
	}

	public BeanUser getUserByCard(String cardNo) throws Exception {
		if (cardNo == null)
			throw (new Exception("Invalid card No."));
		
		if(userCardMap==null)
			userCardMap=new HashMap<String, BeanUser>();
		
		BeanUser user =null;
		
		if(userCardMap.containsKey(cardNo))
			
			user=userCardMap.get(cardNo);
		
		else{
			
			final String where = "card_no='" + cardNo + "'";
			user = getUser(where);
			userCardMap.put(cardNo,user);
		}
		
		return user;
	}

	public BeanUser getUserByCode(String code) throws Exception {

		if (code == null)
			throw (new Exception("Invalid user code."));
		
		if(userCodeMap==null)
			userCodeMap=new HashMap<String, BeanUser>();
		
		BeanUser user =null;
		
		if(userCodeMap.containsKey(code))
			
			user=userCodeMap.get(code);
		
		else{

			String where = "code = '" + code + "'";
			user=getUser(where);
			userCodeMap.put(code,user);
		}
		
		return user;
	}
	
	public BeanUser getUserByID(int id) throws Exception {
		
		if(userIdMap==null)
			userIdMap=new HashMap<Integer, BeanUser>();
		
		BeanUser user =null;
		
		if(userIdMap.containsKey(id))
			
			user=userIdMap.get(id);
		
		else{
			String where = "id = " + id ;
			user=getUser(where);
			userIdMap.put(id,user);
		}
		
		return user;
	}

	protected BeanUser getUser(String where) throws Exception {
		
		BeanUser user = null;
		CachedRowSet crs = null;
		try {
			crs = getData(where);
			if (crs != null && crs.next()) {
				user = createUser(crs);
			}
		} catch (Exception err) {
			throw err;
		} finally {
			try {
				crs.close();
			} catch (SQLException e) {
				crs = null;
				PosLog.write(this, "getUserByCode", e);
			}
		}
		return user;
	}

	private BeanUser createUser(CachedRowSet crs) throws SQLException {
		BeanUser user = new BeanUser();
		user.setId(crs.getInt("id"));
		user.setCode(crs.getString("code"));
		user.setName(crs.getString("name"));
//		user.setDisplayName(crs.getString("display_name"));
		user.setUserGroupId(crs.getInt("user_group_id"));
		user.setEmployeeId((Integer)crs.getObject("employee_id"));
		user.setPassword(crs.getString("password"));
		user.setEmail(crs.getString("email"));
		user.setLastloginDate(crs.getString("lastlogin_date"));
		user.setValidFrom(crs.getString("valid_from"));
		user.setValidTo(crs.getString("valid_to"));
		user.setActive(crs.getInt("is_active"));
		user.setCardNumber(crs.getString("card_no"));
//		user.setIsOpenTill(crs.getBoolean("is_open_till"));
		return user;
	}

}
