/**
 * 
 */
package com.indocosmo.pos.data.providers.shopdb;

import java.sql.SQLException;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import javax.sql.rowset.CachedRowSet;

import com.indocosmo.pos.common.PosLog;
import com.indocosmo.pos.data.beans.BeanEmployees;

/**
 * @author Ramesh S.
 * @since 30th July 2012
 * 
 */
public class PosEmployeeProvider extends PosShopDBProviderBase {

	
	
	public enum EmployeeStatus {
		Active(1),
		Resigned(2);
		
		private static final Map<Integer,EmployeeStatus> mLookup 
		= new HashMap<Integer,EmployeeStatus>();

		static {
			for(EmployeeStatus rc : EnumSet.allOf(EmployeeStatus.class))
				mLookup.put(rc.getValue(), rc);
		}

		private int mValue;
		
		private EmployeeStatus(int value) {
			this.mValue = value;
		}

		public int getValue() { return mValue; }
		
		public static EmployeeStatus get(int value) { 
			return mLookup.get(value); 
		}

	}
	/**
	 * 
	 */
	public PosEmployeeProvider() {
		super("v_employees");
	}

	public BeanEmployees getEmployeeByCard(String cardNo) throws Exception {
		final String where = "card_no='" + cardNo + "'";
		return getEmployeeDetail(where);	
	}
	
	public BeanEmployees getEmployeeByCode(String code) throws Exception {
		if (code == null)
			throw (new Exception("Invalid employee code."));
		final String where = "code = '" + code + "'";
		return getEmployeeDetail(where);	
	}
	
	public BeanEmployees getEmployeeByID(int id) throws Exception {
		
		final String where = "id =" + id;
		return getEmployeeDetail(where);	
	}

	private BeanEmployees getEmployeeDetail(String where) throws Exception {
		CachedRowSet crs = null;
		BeanEmployees employee = null;
		try {
			crs = getData(where);
			if (crs != null && crs.next()) {
				employee = createEmployeeFromRecord(crs);
			}
		} catch (Exception err) {
			throw err;
		} finally {
			try {
				crs.close();
			} catch (SQLException e) {
				crs = null;
				PosLog.write(this, "getEmployeeByCode", e);
			}
		}
		return employee;
	}

	private BeanEmployees createEmployeeFromRecord(CachedRowSet crs)
			throws SQLException {
		BeanEmployees employee = null;
		employee = new BeanEmployees();
		employee.setId(crs.getInt("id"));
		employee.setCode(crs.getString("code"));
		employee.setFirstName(crs.getString("f_name"));
		employee.setMiddleName(crs.getString("m_name"));
		employee.setLastName(crs.getString("l_name"));
		employee.setCardNumber(crs.getString("card_no"));
		employee.setStatus(EmployeeStatus.get(crs.getInt("status")));
		return employee;
	}


}
