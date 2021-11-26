/**
 * 
 */
package com.indocosmo.pos.data.providers.shopdb;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.sql.rowset.CachedRowSet;

import com.indocosmo.pos.common.PosLog;
import com.indocosmo.pos.data.beans.BeanCustomerType;

/**
 * @author jojesh
 *
 */
public final class PosCustomerTypeProvider extends PosShopDBProviderBase {
	
	private static BeanCustomerType mDefaultCustomerType;
	private static String DEF_TYPE_CODE="WALKIN";
	public static String ROOM_TYPE_CODE="ROOM";

	private static ArrayList<BeanCustomerType> mPosCustomerTypeList;
	
	public PosCustomerTypeProvider(){
		mTablename="v_customer_types";	
	}
	
	private void loadCustomerTypes(){
		ArrayList<BeanCustomerType> customerTypeItemList=null;
		CachedRowSet res=getData();
		customerTypeItemList=new ArrayList<BeanCustomerType>();
		try {
			if(res.next())
				do{
					customerTypeItemList.add(createCustomerTypeFromCRS(res)); 
				}while (res.next()) ;
			res.close();
		} catch (SQLException e) {
			PosLog.write(this,"loadCustomerTypes",e);
		}
		mPosCustomerTypeList= customerTypeItemList;
	}
	
	private BeanCustomerType createCustomerTypeFromCRS(CachedRowSet crs) throws SQLException{

		BeanCustomerType customerType= new BeanCustomerType(); 
		customerType.setId(crs.getInt("id"));
		customerType.setName(crs.getString("name"));
		customerType.setCode(crs.getString("code"));	 
		customerType.setDefPriceVariance(crs.getDouble("default_price_variance_pc"));
		return customerType;
	}
	
	public ArrayList<BeanCustomerType> getCustomerTypeList(){
		if(mPosCustomerTypeList==null)
			loadCustomerTypes();
		return mPosCustomerTypeList;
	}
	
	public BeanCustomerType getCustomerTypeByCode(String code){
		String where="code='"+code+"'";
		return getCustomerType(where);
	}
	
	public BeanCustomerType getCustomerTypeByID(int id){
		String where="id="+id;
		return getCustomerType(where);
	}
    private  BeanCustomerType getCustomerType(String where){ 
//    	String where="code='"+code+"'";
    	CachedRowSet res=getData(where);
    	BeanCustomerType customerType=null;
    	try {
    		if(res.next())
    			customerType=createCustomerTypeFromCRS(res);
    		res.close();
    	} catch (SQLException e) {
			PosLog.write(this,"getCustomerType",e);
		}
    	return  customerType;
    }
    
    public BeanCustomerType getDefaultCustomerType(){
    	
    	if(mDefaultCustomerType==null){
    		String sql="SELECT * FROM customer_types WHERE code='"+DEF_TYPE_CODE+"' and is_system=1";

        	CachedRowSet res=executeQuery(sql);
        	try {
        		if(res.next())
        			mDefaultCustomerType=createCustomerTypeFromCRS(res);
        		res.close();
        	} catch (SQLException e) {
    			PosLog.write(this,"getDefaultCustomerType",e);
    		}
    	}
    	return  mDefaultCustomerType;
    }
    
    public boolean isRoomTypeCustomer(int id){
    	
    	boolean result=false;
    	
		String sql="SELECT id FROM customer_types WHERE code='"+ ROOM_TYPE_CODE+"' and id=" + id;
		CachedRowSet res=executeQuery(sql);
    	try {
    		if(res.next())
    			result=true;
    		res.close();
    	} catch (SQLException e) {
			PosLog.write(this,"isRoomTypeCustomer",e);
		}
    	return result;
    }
} 
