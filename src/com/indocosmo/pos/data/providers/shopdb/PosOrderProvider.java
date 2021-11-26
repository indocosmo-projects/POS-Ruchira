/**
 * 
 */
package com.indocosmo.pos.data.providers.shopdb;

import javax.sql.rowset.CachedRowSet;

import com.indocosmo.pos.common.PosLog;

/**
 * @author jojesh
 *
 */
public final class PosOrderProvider extends PosShopDBProviderBase {

	/**
	 * 
	 */
	public PosOrderProvider() {
		
	}
	/***
	 * returns the number of tables used by this order
	 * @param id
	 * @return count
	 * @throws Exception 
	 */
	public int getTableCount(String id) throws Exception{
		String sql="select count(distinct serving_table_id) as table_count from order_dtls where order_id='"+id+"'";
		int count=0;
		try{
			CachedRowSet crs=executeQuery(sql);
			if(crs!=null){
				if(crs.next()){
					count=crs.getInt("table_count");
				}
			}
		}catch(Exception e){
			PosLog.write(this, "getTableCount", e);
			throw e;
		}
		return count; 
	}
	
	/***
	 * returns the of waiters used by this order
	 * @param id
	 * @return count 
	 * @throws Exception 
	 */
	public int getServedByCount(String id) throws Exception{
		String sql="select count(distinct serving_table_id) as table_count from order_dtls where served_id='"+id+"'";
		int count=0;
		try{
			CachedRowSet crs=executeQuery(sql);
			if(crs!=null){
				if(crs.next()){
					count=crs.getInt("table_count");
				}
			}
		}catch(Exception e){
			PosLog.write(this, "getTableCount", e);
			throw e;
		}
		return count; 
	}
}
