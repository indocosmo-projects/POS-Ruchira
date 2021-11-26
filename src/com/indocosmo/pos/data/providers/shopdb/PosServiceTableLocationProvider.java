/**
 * 
 */
package com.indocosmo.pos.data.providers.shopdb;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.sql.rowset.CachedRowSet;

import com.indocosmo.pos.common.PosLog;
import com.indocosmo.pos.common.utilities.PosImageUtils;
import com.indocosmo.pos.data.beans.BeanServingTableLocation;
import com.indocosmo.pos.data.beans.BeanTax;

/**
 * @author jojesh
 *
 */
public  class PosServiceTableLocationProvider extends PosShopDBProviderBase {

	
	private static ArrayList<BeanServingTableLocation> serviceTableLocationList=null;
	private static ArrayList<BeanServingTableLocation> sysServiceTableLocationList=null;
	private static ArrayList<BeanServingTableLocation> allServiceTableLocationList=null;
	
	private PosTaxItemProvider mTaxItemProvider;
	
	
	/**
	 * @return the serviceTableList
	 * @throws Exception 
	 */

	public ArrayList<BeanServingTableLocation> getServiceTableLocationList() throws Exception {
		
		if(serviceTableLocationList==null)
			loadItems();
		return serviceTableLocationList;
	}
	
	/**
	 * @param code
	 * @return
	 * @throws Exception
	 */
	public BeanServingTableLocation getTableLocationByCode(String code) throws Exception{

		if(allServiceTableLocationList==null)
			loadItems();
		BeanServingTableLocation tableLocationObject=null;
		tableLocationObject= getTableLocationByCode(allServiceTableLocationList, code);
		return tableLocationObject;
	}
	
	/**
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public BeanServingTableLocation getTableLocationByID(int id) throws Exception{

		if(allServiceTableLocationList==null)
			loadItems();
		BeanServingTableLocation tableLocationObject=null;
		tableLocationObject= getTableLocationByID(allServiceTableLocationList, id);
		
		return tableLocationObject;
	}
	
	
	/**
	 * @param list
	 * @param id
	 * @return
	 * @throws Exception
	 */
	private  BeanServingTableLocation getTableLocationByID(ArrayList<BeanServingTableLocation> list ,int id) throws Exception{

		BeanServingTableLocation tableLocationObject=null;
		if(list!=null)
			for(BeanServingTableLocation tableLocation:list)
				if(tableLocation.getId()==id){

					tableLocationObject=tableLocation;
					break;
				}
		return tableLocationObject;
	}
	
	/**
	 * @param list
	 * @param code
	 * @return
	 * @throws Exception
	 */
	private BeanServingTableLocation getTableLocationByCode(ArrayList<BeanServingTableLocation> list ,String code) throws Exception{
		
		BeanServingTableLocation tableLocationObject=null;
		if(list!=null)
			for(BeanServingTableLocation tableLocation:list)
				if(tableLocation.getCode().equals(code)){

					tableLocationObject=tableLocation;
					break;
				}
		return tableLocationObject;
	}
	
	
	/**
	 * 
	 */
	public PosServiceTableLocationProvider() {
		
		super("v_serving_table_locations");
	}

	
	/**
	 * @throws Exception
	 */
	private void loadItems() throws Exception{

		ArrayList<BeanServingTableLocation> itemList=null;
		ArrayList<BeanServingTableLocation> sysItemList=null;
		
		CachedRowSet res=getData();
		try {
			itemList=new ArrayList<BeanServingTableLocation>();
			sysItemList=new ArrayList<BeanServingTableLocation>();
			while (res.next()) {

				BeanServingTableLocation item = createItemFromCrs(res);
				if(item.isSystem())
					sysItemList.add(item); 
				else
					itemList.add(item); 
			}
			res.close();
		} catch (SQLException e) {
			PosLog.write(this,"loadItems",e.getMessage());
			itemList=null;
			throw e;
		}
		sysServiceTableLocationList=sysItemList;
		serviceTableLocationList= itemList;
		
		allServiceTableLocationList=new ArrayList<BeanServingTableLocation>();
		if(sysServiceTableLocationList!=null)
			allServiceTableLocationList.addAll(sysServiceTableLocationList);
		if(serviceTableLocationList!=null)
			allServiceTableLocationList.addAll(serviceTableLocationList);
		
	}
	
	/**
	 * @param crs
	 * @return
	 * @throws SQLException
	 */
	protected BeanServingTableLocation createItemFromCrs(CachedRowSet crs) throws SQLException{
		
		BeanServingTableLocation item= new BeanServingTableLocation();
		setItemFromCrs(crs,item);
		return item;
	}
	
	/**
	 * @param crs
	 * @param item
	 * @throws SQLException
	 */
	protected void setItemFromCrs(CachedRowSet crs,BeanServingTableLocation item) throws SQLException{
		
		mTaxItemProvider=PosTaxItemProvider.getInstance();
				
		item.setId(crs.getInt("id"));
		item.setName(crs.getString("name"));
		item.setCode(crs.getString("code"));		
		item.setAutoLayout(crs.getBoolean("is_auto_layout"));
		item.setApplyServiceCharges(crs.getBoolean("apply_service_charge"));
		item.setAmount(crs.getDouble("sc_amount"));
		item.setPercentage(crs.getBoolean("is_sc_percentage"));
		item.setBasedOn(crs.getInt("sc_based_on"));
		item.setSystem(crs.getBoolean("is_system"));
		
		item.setApplyServiceTax(crs.getBoolean("apply_service_tax"));
		item.setQueueNoPrefix(crs.getString("que_no_prefix"));
		
		if(item.isApplyServiceTax()){ 
			
			final BeanTax serviceTax=mTaxItemProvider.getTaxItem(crs.getInt("service_tax_id"));
			item.setServiceTax(serviceTax);
		}
		
		try {
			
			final String image=crs.getString("bg_image");
//			final String image=PosStringUtil.getLargerString2(crs, "bg_image");
			if(image!=null && !image.isEmpty())
				item.setImage(PosImageUtils.decodeFromBase64(image));
			
		} catch (Exception e) {
			
			PosLog.write(this, "setItemFromCrs", e);
		}
		
	}

	/**
	 * @return the sysServiceTableLocationList
	 */
	public static ArrayList<BeanServingTableLocation> getSysServiceTableLocationList() {
		return sysServiceTableLocationList;
	}

}
