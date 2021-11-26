package com.indocosmo.pos.data.providers.shopdb;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.sql.rowset.CachedRowSet;

import com.indocosmo.pos.common.PosEnvSettings;
import com.indocosmo.pos.common.PosLog;
import com.indocosmo.pos.common.enums.PosOrderServiceTypes;
import com.indocosmo.pos.data.beans.BeanCustomer;
import com.indocosmo.pos.data.beans.BeanCustomerType;
import com.indocosmo.pos.data.beans.BeanGstPartyType;
import com.indocosmo.pos.data.beans.BeanGstRegisterType;
 
public class PosCustomerProvider extends PosShopDBProviderBase {

	public final static String DEF_CUST_CODE="WALKIN";
	private static BeanCustomer mDefaultCustomer;
	private PosCustomerTypeProvider mPosCustomerTypeProvider;
	private static ArrayList<BeanCustomer> mItemList;
	private static Map<String, BeanCustomer> mItemCardNoMap;
	private static Map<String, BeanCustomer> mItemCodeMap;
	
	private PosGstRegisterTypeProvider mGstRegisterTypeProvider;
	private PosGstPartyTypeProvider mGstPartyTypeProvider;
	
 	
	
	public PosCustomerProvider(){
		mTablename="v_customers";	
		mPosCustomerTypeProvider=new PosCustomerTypeProvider();
		mGstRegisterTypeProvider=new PosGstRegisterTypeProvider();
		mGstPartyTypeProvider=new PosGstPartyTypeProvider();
	}

	private void addToMap(BeanCustomer customer){
		
		addToCardNoMap(customer);
		addToCodeMap(customer);
		
	}
	private void addToCardNoMap(BeanCustomer customer){
		
		if(mItemCardNoMap==null)
			mItemCardNoMap=new HashMap<String, BeanCustomer>();
		
		if(!mItemCardNoMap.containsKey(customer.getCardNumber()))
			mItemCardNoMap.put(customer.getCardNumber(), customer);
	}
	
	private void addToCodeMap(BeanCustomer customer){
		
		if(mItemCodeMap==null)
			mItemCodeMap=new HashMap<String, BeanCustomer>();
		
		if(!mItemCodeMap.containsKey(customer.getCode()))
			mItemCodeMap.put(customer.getCode(), customer);
	}
	private void loadItems(){
		
		CachedRowSet res=getData();
		ArrayList<BeanCustomer> customerTypeItemList=new ArrayList<BeanCustomer>();
		try {
			if(res.next())
				do{
					BeanCustomer item=createItemFromCRS(res);
					customerTypeItemList.add(item); 
					addToMap(item);
				}while (res.next()) ;
			res.close();
		} catch (SQLException e) {
			PosLog.write(this,"loadCustomers",e);
		}
		
		mItemList= customerTypeItemList;
	}
	
	private BeanCustomer createItemFromCRS(CachedRowSet crs) throws SQLException{
		BeanCustomer item=new BeanCustomer();
		return createItemFromCRS( item,  crs);
	}

	private BeanCustomer createItemFromCRS(BeanCustomer item, CachedRowSet crs) throws SQLException{
		
		item.setId(crs.getInt("id"));
		item.setName(crs.getString("name"));
		item.setCategory(crs.getString("category"));
		item.setCode(crs.getString("code"));	
		item.setCardNumber(crs.getString("card_no"));	
		item.setIsArCompany(crs.getBoolean("is_ar"));
		item.setVisibleInUI(!crs.getBoolean("is_system"));
		final int custTypeId=crs.getInt("customer_type");
		final BeanCustomerType custType=mPosCustomerTypeProvider.getCustomerTypeByID(custTypeId);
		item.setCustType(custType);
		item.setPhoneNumber(crs.getString("phone"));
		item.setAddress(crs.getString("address"));	
		item.setCity(crs.getString("city"));	
		item.setState(crs.getString("state"));	
		item.setStateCode(crs.getString("state_code"));	
		item.setCountry(crs.getString("country"));
		item.setCSTNo(crs.getString("cst_no"));
		item.setTinNo(crs.getString("tin"));
	    
		item.setBankName(crs.getString("bank_name"));
		item.setBankBranch(crs.getString("bank_branch"));
		item.setBankAddress(crs.getString("bank_address"));
		item.setBankBrancIFSCCode(crs.getString("bank_ifsc_code"));
		item.setBankMICRCode(crs.getString("bank_micr_code"));
		item.setBankAccountNo(crs.getString("bank_account_no"));
		
		final BeanGstRegisterType gstRegisterType=mGstRegisterTypeProvider.getRegisterType(crs.getInt("gst_reg_type"));
		item.setGstRegisterType(gstRegisterType);
		final BeanGstPartyType gstPartyType=mGstPartyTypeProvider.getPartyType(crs.getInt("gst_party_type"));
		item.setGstPartyType(gstPartyType);
		
		
		item.setAddress2(crs.getString("address2"));
		item.setAddress3(crs.getString("address3"));
		item.setAddress4(crs.getString("address4"));
		
		item.setPhoneNumber2(crs.getString("phone2"));
		return item;
	}

	public ArrayList<? extends BeanCustomer> getItemList(){
		if(mItemList==null)
			loadItems();
		return mItemList;
	}
	
	public Map<String, BeanCustomer> getItemMap(){
		if(mItemCardNoMap==null)
			loadItems();
		return mItemCardNoMap;
	}

	public BeanCustomer getItem(String where){
		CachedRowSet res=getData(where);
		BeanCustomer customerType=null;
		try {
			if(res.next())
				customerType=createItemFromCRS(res);
		} catch (SQLException e) {
			PosLog.write(this,"getCustomer",e);
		}
		return  customerType;
	}

	public BeanCustomer getByCode(String code){
	
		BeanCustomer cust=null;
		
		if(mItemCodeMap!=null && mItemCodeMap.containsKey(code))
			cust=mItemCodeMap.get(code);  
		else{
			
			final String where="code='"+code+"'";
			cust=getItem(where);
			if(cust!=null)
				addToMap(cust);
		}
		return  cust;
	}

	public BeanCustomer getByCard(String cardno){
		
		BeanCustomer cust=null;
		
		if( mItemCardNoMap!=null && mItemCardNoMap.containsKey(cardno))
			cust=mItemCardNoMap.get(cardno);  
		else{
		
			final String where="card_no='"+cardno+"'";
			cust=getItem(where);
			addToMap(cust);
		}
		return  cust;
	}
	
	public BeanCustomer getById(int id){
		
		if(mDefaultCustomer==null)
			getDefaultCustomer();
		
		BeanCustomer cust=null;
		
		if(mDefaultCustomer.getId()==id)
			cust=mDefaultCustomer;
		else{
			final String where="id="+id;
			cust=getItem(where);
			addToMap(cust);
		}
		
		return cust;
	}

	public BeanCustomer getDefaultCustomer(){
		if(mDefaultCustomer==null){
			String sql="SELECT * FROM customers WHERE code='"+DEF_CUST_CODE+"' and is_system=1";
			try {
				CachedRowSet rs=executeQuery(sql);
				if(rs!=null && rs.next())
					mDefaultCustomer=createItemFromCRS(rs);
			} catch (SQLException e) {
				PosLog.write(this,"getDefaultCustomer",e);
			}
//			if(mDefaultCustomer!=null)
//				mDefaultCustomer.setCustType(mPosCustomerTypeProvider.getDefaultCustomerType());
		}
		return  mDefaultCustomer;
	}
	
	
	public BeanCustomer getDefaultCustomer(PosOrderServiceTypes serviceType){
		
		String custCode=serviceType.getDefualtCustomerCode();
 
		custCode=custCode.trim().equals("")?DEF_CUST_CODE:custCode;
		BeanCustomer customer=getByCode(custCode);
		if(customer==null)
			customer=getDefaultCustomer();
		return  customer;
	}
 
	
	/*
	 * 
	 */
	public BeanCustomer getAllCustomerById(int id){
		
		if(mDefaultCustomer==null)
			getDefaultCustomer();
		
		BeanCustomer cust=null;
		
		if(mDefaultCustomer.getId()==id)
			cust=mDefaultCustomer;
		else{
			CachedRowSet res=executeQuery("SELECT * FROM customers where id=" +id);
			BeanCustomer customer=null;
			try {
				if(res.next()){
					final String code=res.getString("code");
					final boolean isARCustomer=res.getBoolean("is_ar");
					
					if(isARCustomer){
						PosCompanyItemProvider companyProvider=new PosCompanyItemProvider();
						customer=(BeanCustomer)companyProvider.getByCode(code);
					}else
						customer=createItemFromCRS(res);
					
				}
			} catch (SQLException e) {
				PosLog.write(this,"getAllCustomerById",e);
			}
			return  customer;
		}
		
		return cust;
	}
	
//	public BeanCustomer getAllCustomerById(int id){
//		
//		if(mDefaultCustomer==null)
//			getDefaultCustomer();
//		
//		BeanCustomer cust=null;
//		
//		if(mDefaultCustomer.getId()==id)
//			cust=mDefaultCustomer;
//		else{
//			CachedRowSet res=executeQuery("SELECT * FROM customers where id=" +id);
//			BeanCustomer customer=null;
//			try {
//				if(res.next())
//					customer=createItemFromCRS(res);
//			} catch (SQLException e) {
//				PosLog.write(this,"getAllCustomerById",e);
//			}
//			return  customer;
//		}
//		
//		return cust;
//	}
}
