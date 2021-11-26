/**
 * 
 */
package com.indocosmo.pos.data.providers.shopdb;

import java.sql.SQLException;

import javax.sql.rowset.CachedRowSet;

import com.indocosmo.pos.common.PosLog;

/**
 * @author jojesh
 *
 */
public class PosCounterProvider extends PosShopDBProviderBase {

	public static final String COUNTER_MODULE_NAME="POST";
	public static final String ORDER_BILL_NO_KEY_NAME="order_bill_no";
	public static final String ORDER_QUEUE_NO_KEY_NAME="order_queue_no";
	public static final String ORDER_DTL_ID_KEYNAME="order_dtl_id";
	public static final String STOCK_IN_NO_KEY_NAME="stock_in_no";
	/**
	 * 
	 */
	public PosCounterProvider() {
		super();
		mTablename="counter";
	}

	public int getNextCounter(String module_name, String key_name){
		String where="module='"+ module_name + "' and key_name='"+ key_name + "'";
		int counter=1;
		try {
			CachedRowSet res=getData(where);
			if(res!=null){
				if(res.next())
					counter=res.getInt("key_value")+1;
				else
					addToCounter(module_name,key_name);
				res.close();
			}
		} catch (SQLException e) {
			PosLog.write(this,"getNextCounter",e);
		}
		return counter;
	}

	private void addToCounter(String module_name, String key_name){
		
		final String sql="insert into counter (module,key_name,key_value) values ('"+ module_name +"', '"+ key_name + "',0)";
		try {
			
			executeNonQuery(sql);
			
		} catch (SQLException e) {
			
			PosLog.write(this,"addToCounter", e);
		}
	}

	public int updateCounter(String module_name, String key_name,int key_value){
		
		final String sql= "update "+ mTablename +" set key_value="+ String.valueOf(key_value) +
				" where module='"+ module_name + "' and key_name='"+ key_name + "'";
		try {
			executeNonQuery(sql);
		} catch (SQLException e) {
			
			PosLog.write(this,"updateCounter", e);
		}
		return key_value;
	}

	public int updateCounter(String module_name, String key_name){
		final int value = getNextCounter(module_name,key_name);
		updateCounter(module_name, key_name,value);
		return value;
	}
	

	public int getNextOrderBillNumber(){
		return getNextCounter(COUNTER_MODULE_NAME,ORDER_BILL_NO_KEY_NAME);
	}

	public int updateOrderBillNumber(){
		return updateCounter(COUNTER_MODULE_NAME,ORDER_BILL_NO_KEY_NAME);
	}

	public int getNextOrderQueueSNO(){
		return getNextCounter(COUNTER_MODULE_NAME,ORDER_QUEUE_NO_KEY_NAME);
	}

	public int updateOrderQueueSNO(){
		return updateCounter(COUNTER_MODULE_NAME,ORDER_QUEUE_NO_KEY_NAME);
	}
	
	public int getNextOrderDtlID(){
		return getNextCounter(COUNTER_MODULE_NAME,ORDER_DTL_ID_KEYNAME);
	}

//	public int updateOrderDtlID(int incrementBy){
//		final int value = getNextCounter(COUNTER_MODULE_NAME,ORDER_DTL_ID_KEYNAME)+incrementBy-1;
//		updateCounter(COUNTER_MODULE_NAME, ORDER_DTL_ID_KEYNAME,value);
//		return value;
//	}
	public int getNextStockInHdrNumber(){
		return getNextCounter(COUNTER_MODULE_NAME,STOCK_IN_NO_KEY_NAME);
	}

	public int updateStockInHdrNumber(){
		return updateCounter(COUNTER_MODULE_NAME,STOCK_IN_NO_KEY_NAME);
	}
	
}
