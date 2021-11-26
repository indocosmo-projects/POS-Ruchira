/**
 * 
 */
package com.indocosmo.pos.data.providers.shopdb;

import com.indocosmo.pos.common.utilities.PosPasswordUtil;
import com.indocosmo.pos.data.beans.BeanUser;

/**
 * @author jojesh
 *
 */
public final class PosCashierProvider extends PosUsersProvider {

	public PosCashierProvider() {
		super("v_cashiers");
	}
	
	public BeanUser getCashierDetails(int id) throws Exception{
		return (BeanUser) getUserByID(id);
	}

	public BeanUser getCashierDetails(String code) throws Exception{
			return (BeanUser) getUserByCode(code);
	}

	public BeanUser getCashierDetails(String code, String password) throws Exception{
		String encPassword=PosPasswordUtil.encrypt(password);
		String where="code='"+code+"' and password='"+encPassword+"'";
		return (BeanUser) getUser(where);
	}

}
