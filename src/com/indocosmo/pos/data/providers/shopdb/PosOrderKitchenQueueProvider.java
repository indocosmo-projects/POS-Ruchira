/**
 * 
 */
package com.indocosmo.pos.data.providers.shopdb;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.sql.rowset.CachedRowSet;

import com.indocosmo.pos.common.PosEnvSettings;
import com.indocosmo.pos.common.PosLog;
import com.indocosmo.pos.common.utilities.PosDBUtil;
import com.indocosmo.pos.common.utilities.PosDateUtil;
import com.indocosmo.pos.common.utilities.PosOrderUtil;
import com.indocosmo.pos.data.beans.BeanCashierShift;
import com.indocosmo.pos.data.beans.BeanDiscount;
import com.indocosmo.pos.data.beans.BeanOrderDetail;
import com.indocosmo.pos.data.beans.BeanOrderHeader;
import com.indocosmo.pos.data.beans.BeanOrderKitchenQueue;
import com.indocosmo.pos.data.beans.BeanOrderPayment;
import com.indocosmo.pos.data.beans.BeanOrderRefund;
import com.indocosmo.pos.data.beans.BeanPayModeBase.PaymentMode;
import com.indocosmo.pos.forms.PosOrderEntryForm;
import com.indocosmo.pos.data.beans.BeanPaymentSummary;
import com.indocosmo.pos.data.beans.BeanUser;

/**
 * @author sandhya
 *
 */
public class PosOrderKitchenQueueProvider extends PosShopDBProviderBase  {
	
 
   /** The single instance object **/
	private static PosOrderKitchenQueueProvider mSingleToneInstace;

	public PosOrderKitchenQueueProvider() {
		super("order_kitchen_queue");
		mSingleToneInstace=this;
		
	}
	 
	public static  PosOrderKitchenQueueProvider getInstance() {
		if (mSingleToneInstace == null)
			new PosOrderKitchenQueueProvider();
		return mSingleToneInstace;
	}
	
	
	public synchronized int getKitchenQueueNo(BeanOrderHeader orderHdr,int kitchenId) throws Exception {
		
		int queueNo=0;
		
		String sql="select getKitchenQueueNo('" + orderHdr.getOrderId() + "',"  + String.valueOf(kitchenId) + ") as QueueNo";
		try{
			
			CachedRowSet crs=executeQuery(sql);
			if(crs!=null){
				
				if(crs.next()){
					
					queueNo=crs.getInt("QueueNo");
				}
			}else{
				throw new Exception("Failed to get  Kitchen Queue Number");
			}
			
		}catch(Exception e){
			
			PosLog.write(this, "getKitchenQueueNo ("+ orderHdr.getOrderId() + "',"  + String.valueOf(kitchenId) + ")", e);
			throw new Exception("Failed to get Kitchen Queue Number");
			
		}
		return queueNo;
	}
 
	public ArrayList<BeanOrderKitchenQueue> getKitchenQueueNos(String orderId) throws SQLException{
		
		ArrayList<BeanOrderKitchenQueue> kitchenQueueList=new ArrayList<BeanOrderKitchenQueue>();
		final String sql="SELECT t1.kitchen_id,t2.code,t1.kitchen_queue_no " + 
						" from order_kitchen_queue t1 "+ 
						" join kitchens t2 on t1.kitchen_id=t2.id where order_id='" + orderId + "'" + 
						" order by t1.kitchen_id" ;
		
		CachedRowSet crs=executeQuery(sql);
		while(crs.next()){
			BeanOrderKitchenQueue kitchenQueue=new BeanOrderKitchenQueue();
			kitchenQueue.setKitchenId(crs.getInt("kitchen_id"));
			kitchenQueue.setKitchenCode(crs.getString("code"));
			kitchenQueue.setKitchenQueueNo(crs.getInt("kitchen_queue_no"));
			kitchenQueueList.add(kitchenQueue);
		}
		return kitchenQueueList;
	}
 
	public void deleteKitchenQueueNo(String orderId,int kitchenId,int queueNo) throws SQLException{
		
		final String sql="delete from order_kitchen_queue where order_id='" + orderId + 
					"' and kitchen_id=" + kitchenId + " and kitchen_queue_no=" + queueNo;
		
		executeNonQuery(sql);
	}
	

	/**
	 * @throws SQLException
	 */
	public void resetQueueNumber() throws SQLException{

		switch (PosDBUtil.getInstance().getDatabaseType()) {
		case SQLITE:
			executeNonQuery("select resetKitchenQueueNumber();");
			break;
		case MARIADB:
		case MYSQL:
			executeNonQuery("call sp_resetKitchenQueueNumber();");
		}
	
		
	}
	 
}
