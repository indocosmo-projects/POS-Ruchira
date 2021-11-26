/**
 * 
 */
package com.indocosmo.pos.data.providers.shopdb;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.sql.rowset.CachedRowSet;

import com.indocosmo.pos.common.PosLog;
import com.indocosmo.pos.data.beans.BeanServingTableExt;

/**
 * @author jojesh
 *
 */
public class PosServiceTableExtProvider extends PosServiceTableProvider {

	private ArrayList<BeanServingTableExt> serviceTableExtList=null;
	private Map<String, BeanServingTableExt> serviceTableExtMapList=null;
	
	public PosServiceTableExtProvider() {
		super("v_serving_tables_ext");
	}
	
	/**
	 * @param table
	 */
	protected PosServiceTableExtProvider(String table) {
		super(table);
	}
	
	/**
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<BeanServingTableExt> getServiceTableExtList() throws Exception{
		
		loadExtItem();
		return serviceTableExtList;
	}
	
	/**
	 * @return
	 * @throws SQLException
	 */
	public Map<String, BeanServingTableExt>  getServiceTableExtMapList() throws Exception{
		
		loadExtItem();
		return serviceTableExtMapList;
	}
	
	/**
	 * @param locId
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<BeanServingTableExt> getServiceTableExtList(int locId) throws Exception{
		
		final String where="serving_table_location_id="+locId;
		loadExtItem(where);
		return serviceTableExtList;
	}
	
	/**
	 * @param locId
	 * @return
	 * @throws SQLException
	 */
	public Map<String, BeanServingTableExt>  getServiceTableExtMapList(int locId) throws Exception{
		
		final String where="serving_table_location_id="+locId;
		loadExtItem(where);
		return serviceTableExtMapList;
	}
	
	/**
	 * @throws SQLException
	 */
	private void loadExtItem() throws Exception{
		
		loadExtItem(null);
	}
	
	/**
	 * @param where
	 * @throws SQLException
	 */
	private void loadExtItem(String where) throws Exception{
		
		ArrayList<BeanServingTableExt> itemList=null;
		Map<String, BeanServingTableExt> itemMapList =null;
		CachedRowSet res=(where==null)?getData():getData(where,"id asc");
		try {
			itemMapList=new HashMap<String, BeanServingTableExt>();
			itemList=new ArrayList<BeanServingTableExt>();
			while (res.next()) {
				BeanServingTableExt item= new BeanServingTableExt();
				setItemFromCrs(res,item);
				if(!res.getBoolean("is_system")){
					itemList.add(item);
					itemMapList.put(item.getCode(), item);
				}
			}
			res.close();
		} catch (SQLException e) {
			PosLog.write(this,"loadItems",e.getMessage());
			itemList=null;
			throw e;
		}
		serviceTableExtList= itemList;
		serviceTableExtMapList=itemMapList;
	}

	
	/**
	 * @param crs
	 * @param item
	 * @throws SQLException
	 */
	public void setItemFromCrs(CachedRowSet crs, BeanServingTableExt item)
			throws Exception {
		super.setItemFromCrs(crs, item);
		item.setOrderCount(crs.getInt("order_count"));
		
	}

	
}
