/**
 * 
 */
package com.indocosmo.pos.data.providers.shopdb;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.sql.rowset.CachedRowSet;

import com.indocosmo.pos.common.PosLog;
import com.indocosmo.pos.data.beans.BeanEmployees;
import com.indocosmo.pos.data.providers.shopdb.PosEmployeeProvider.EmployeeStatus;

/**
 * @author jojesh
 *
 */
public final class PosWaiterProvider extends PosShopDBProviderBase {
	
	public static final String DEF_SYSTEM_WAITER_CDOE="SYSWTR";
	private static BeanEmployees defSystemWaiter; 
	
	private static ArrayList<BeanEmployees> waiterList=null;
	/**
	 * @return the waiterList
	 * @throws SQLException 
	 */
	public ArrayList<BeanEmployees> getWaiterList() throws SQLException {

		if(waiterList==null)
			loadItems();

		return waiterList;
	}
	
	public BeanEmployees getWaiterByCardNo(String cardNo) throws SQLException{

		ArrayList<BeanEmployees> list=getWaiterList();
		BeanEmployees waiter=null;
		if(list!=null){

			for(BeanEmployees empl:list){

				if(empl.getCardNumber().equals(cardNo)){

					waiter=empl;
				}
			}
		}

		return waiter;
	}
	
	public BeanEmployees getWaiterByCode(String code) throws SQLException{
		
		ArrayList<BeanEmployees> list=getWaiterList();
		BeanEmployees waiter=null;
		if(list!=null){

			for(BeanEmployees empl:list){

				if(empl.getCode().equals(code)){

					waiter=empl;
				}
			}
		}
		return waiter;
	}
	
	public BeanEmployees getWaiterById(int id) throws SQLException{

		ArrayList<BeanEmployees> list=getWaiterList();
		BeanEmployees waiter=null;
		if(list!=null){

			for(BeanEmployees empl:list){

				if(empl.getId()==id){

					waiter=empl;
				}
			}
		}
		return waiter;
	}

	/**
	 * 
	 */
	public PosWaiterProvider() {

		super("v_waiters");
	}

	private void loadItems() throws SQLException{

		ArrayList<BeanEmployees> itemList=null;
		CachedRowSet res=getData();
		itemList=new ArrayList<BeanEmployees>();
		try {
			while (res.next()) {

				BeanEmployees item = createItemFromCrs(res);
				itemList.add(item); 
			}
			res.close();
		} catch (SQLException e) {

			PosLog.write(this,"loadItems",e.getMessage());
			throw e;
		}
		waiterList= itemList;
	}
	
	private BeanEmployees createItemFromCrs(CachedRowSet crs) throws SQLException{
		
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
	
	/**
	 * @return
	 * @throws SQLException
	 */
	public BeanEmployees getDefaultSystemWaiter() throws SQLException{
		
		if(defSystemWaiter==null){

			defSystemWaiter=getWaiterByCode(DEF_SYSTEM_WAITER_CDOE);
			defSystemWaiter.setVisibleInUI(false);
		}

		return defSystemWaiter;
	}

}
