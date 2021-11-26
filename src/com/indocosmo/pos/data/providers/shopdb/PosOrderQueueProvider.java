/**
 * 
 */
package com.indocosmo.pos.data.providers.shopdb;

import java.sql.SQLException;

import javax.sql.rowset.CachedRowSet;

import com.indocosmo.pos.common.PosLog;
import com.indocosmo.pos.common.utilities.PosDBUtil;
import com.indocosmo.pos.common.utilities.PosOrderUtil;
import com.indocosmo.pos.common.utilities.PosStringUtil;
import com.indocosmo.pos.data.beans.BeanOrderHeader;

/**
 * @author sandhya
 *
 */
public final class PosOrderQueueProvider extends PosShopDBProviderBase {

	public static int QUEUE_NO_WIDTH=3;
	/**
	 * 
	 */
	public PosOrderQueueProvider() {
		super("order_queue");
		
	}
	/***
	 * returns Invoice No
	 * @param Orderid
	 * @throws Exception 
	 */
	public String getOrderQueueNo(BeanOrderHeader orderHdr ) throws Exception{
		
		if (orderHdr.getQueueNo()!=null){
			if (orderHdr.getQueueNo().trim()!=""){
				
				return orderHdr.getQueueNo();
			}
		}
		String sql="select getOrderQueueNo('" + orderHdr.getOrderId() + "') as QueueNo";
		int id=0;
		try{
			
			CachedRowSet crs=executeQuery(sql);
			if(crs!=null){
				
				if(crs.next()){
					
					id=crs.getInt("QueueNo");
				}
			}else{
				throw new Exception("Failed to get  Order Queue Number");
			}
			
		}catch(Exception e){
			
			PosLog.write(this, "getOrderQueueNo ("+ orderHdr.getOrderId() +")", e);
			throw new Exception("Failed to get Order Queue Number");
			
		}finally{
			
		}
		
		return id==0?"": Integer.toString(id); 
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
