package com.indocosmo.pos.data.providers.shopdb;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.sql.rowset.CachedRowSet;

import com.indocosmo.pos.common.PosLog;
import com.indocosmo.pos.data.beans.BeanCompany;
import com.indocosmo.pos.data.beans.BeanCustomerType;


public final class PosCompanyItemProvider extends PosShopDBProviderBase {

	private PosCustomerTypeProvider mPosCustomerTypeProvider;
	private static ArrayList<BeanCompany> mItemList;
	private static Map<String, BeanCompany> mItemMap;
	
	public PosCompanyItemProvider(){
		mTablename="v_ar_companies";	
		mPosCustomerTypeProvider=new PosCustomerTypeProvider();
	}

	private void loadItems(){
//		ArrayList<PosCustomerObject> customerTypeItemList=null;
		CachedRowSet res=getData();
		ArrayList<BeanCompany> itemList=new ArrayList<BeanCompany>();
		Map<String, BeanCompany> itemMap=new HashMap<String, BeanCompany>();
		try {
			if(res.next())
				do{
					BeanCompany item=createItemFromCRS(res);
					itemMap.put(item.getCardNumber(), item);
					itemList.add(item); 
				}while (res.next()) ;
			res.close();
		} catch (SQLException e) {
			PosLog.write(this,"loadItems",e);
		}
		mItemMap=itemMap;
		mItemList= itemList;
	}
	
	private BeanCompany createItemFromCRS(CachedRowSet crs) throws SQLException{
		BeanCompany item=new BeanCompany();
		return createItemFromCRS( item,  crs);
	}

	private BeanCompany createItemFromCRS(BeanCompany item, CachedRowSet crs) throws SQLException{
		
		item.setId(crs.getInt("id"));
		item.setName(crs.getString("name"));
		item.setCode(crs.getString("code"));	
		item.setCategory(crs.getString("category"));
		item.setCardNumber(crs.getString("card_no"));	
		item.setIsArCompany(crs.getBoolean("is_ar"));
		final int custTypeId=crs.getInt("customer_type");
		item.setAccountCode(crs.getString("ar_code"));
		item.setPhoneNumber(crs.getString("phone"));
		item.setAddress(crs.getString("address"));	
		item.setState(crs.getString("state"));	
		item.setCity(crs.getString("city"));	
		item.setStateCode(crs.getString("state_code"));	
		item.setCSTNo(crs.getString("cst_no"));
		item.setTinNo(crs.getString("tin"));
		BeanCustomerType custType=mPosCustomerTypeProvider.getCustomerTypeByID(custTypeId);
		item.setCustType(custType);
		return item;
	}

	public ArrayList<? extends BeanCompany> getItemList(){
		if(mItemList==null)
			loadItems();
		return mItemList;
	}
	
	public Map<String, BeanCompany> getItemMap(){
		if(mItemMap==null)
			loadItems();
		return mItemMap;
	}

	public BeanCompany getItem(String where){
		CachedRowSet res=getData(where);
		BeanCompany CompanyType=null;
		try {
			if(res.next())
				CompanyType=createItemFromCRS(res);
		} catch (SQLException e) {
			PosLog.write(this,"getCompany",e);
		}
		return  CompanyType;
	}

	public BeanCompany getByCode(String code){
		String where="code='"+code+"'";
		return  getItem(where);
	}

	public BeanCompany getByCard(String cardno){
		String where="card_no='"+cardno+"'";
		return  getItem(where);
	}
	
	public BeanCompany getById(int id){
		String where="id="+id;
		return  getItem(where);
	}
	
	public String getCompanyName(int id){
		String companyName = null;
		BeanCompany company=null;
		String where="id="+id;
		company = getItem(where);
		if(company!=null){
	    	companyName =company.getName();
		}
	    return companyName;
	}
	
}

