/**
 * 
 */
package com.indocosmo.pos.data.providers.shopdb;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.sql.rowset.CachedRowSet;

import com.indocosmo.pos.common.PosLog;
import com.indocosmo.pos.data.beans.BeanMenuDepartment;
import com.indocosmo.pos.data.beans.BeanSaleMenuItem;

/**
 * @author deepak
 *
 */
public class PosMenuDepartmentProvider extends PosShopDBProviderBase{
	
	public PosMenuDepartmentProvider(){
		super("menu_departments");
	}
	
	private ArrayList<BeanMenuDepartment> loadItems(String where){
		ArrayList<BeanMenuDepartment> itemList=null;
		try {
			final CachedRowSet res=(where==null)?getData():getData(where);
			if(res!=null){
				itemList=new ArrayList<BeanMenuDepartment>();
				while (res.next()) {
					BeanMenuDepartment item=createMenuItemFromResult(res);
					itemList.add(item); 
				}
				res.close();
			}
		} catch (SQLException e) {
			PosLog.write(this,"loadItems",e);
		}
		return itemList;
	}
	
	private BeanMenuDepartment createMenuItemFromResult(final CachedRowSet res){
		BeanMenuDepartment item=new BeanMenuDepartment();
		try {
			item.setId(res.getInt("id"));
			item.setMenuId(res.getInt("menu_id"));
			item.setDepartmentId(res.getInt("department_id"));
		
		} catch (SQLException e) {
			PosLog.write(this,"createMenuItemFromResult",e);
		}
		return item;
	}
	
	public ArrayList<BeanMenuDepartment> getList(){
		return loadItems(null);
	}
	
	public ArrayList<BeanMenuDepartment> getDepartments(BeanSaleMenuItem menu){
		String where="menu_id="+menu.getId()+" and is_deleted=0";
		return loadItems(where);
	}
	
	/**
	 * @param menu
	 * @return the default Menu departments.....
	 */
//	public ArrayList<PosMenuDepartmentObject> getDefaultMenuDepartments(PosSaleMenuObject menu){
//		String where="id="+menu.getId();
//		return loadItems(where);
//	}

}
