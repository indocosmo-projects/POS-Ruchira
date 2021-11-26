/**
 * 
 */
package com.indocosmo.pos.data.providers.shopdb;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.sql.rowset.CachedRowSet;

import com.indocosmo.pos.data.beans.BeanDepartment;

/**
 * @author Deepak
 *
 */
public class PosDepartmentProvider extends PosShopDBProviderBase{
	

	private ArrayList<BeanDepartment> mDepartmentList;
	/**
	 * 
	 */
	public PosDepartmentProvider() {
		super("departments");
	}
	
	public ArrayList<BeanDepartment> getDepartments(){
		return loadItems();
	}

	/**
	 * @return
	 */
	private ArrayList<BeanDepartment> loadItems() {
		
		CachedRowSet crs = getData("is_deleted= 0");
		if(crs!=null){
			mDepartmentList = new ArrayList<BeanDepartment>();
			try {
				while (crs.next()) {
					BeanDepartment department = createDepartmentFromCrs(crs);
					mDepartmentList.add(department);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return mDepartmentList;
	}

	/**
	 * @param crs 
	 * @return
	 * @throws SQLException 
	 */
	private BeanDepartment createDepartmentFromCrs(CachedRowSet crs) throws SQLException {
		BeanDepartment department = new BeanDepartment();
		department.setId(crs.getInt("id"));
		department.setCode(crs.getString("code"));
		department.setName(crs.getString("name"));
		return department;
	}
}
