/**
 * 
 */
package com.indocosmo.pos.data.providers.shopdb;

import java.sql.SQLException;

import javax.sql.rowset.CachedRowSet;

import com.indocosmo.pos.common.PosLog;
import com.indocosmo.pos.common.utilities.PosOrderUtil;
import com.indocosmo.pos.common.utilities.PosStringUtil;

/**
 * @author sandhya
 *
 */
public final class PosInvoiceProvider extends PosShopDBProviderBase {

	public static int INVOICE_NO_WIDTH=7;
	/**
	 * 
	 */
	public PosInvoiceProvider() {
		
	}
	/***
	 * returns Invoice No
	 * @param Orderid
	 * @throws Exception 
	 */
	public String getInvoiceNo(String orderid,boolean addNewNo) throws Exception{
		
		String sql="select getInvoiceNo('" + orderid + "'," +  (addNewNo?1:0) +  ") as InvoiceNo";
		int id=0;
		try{
			
//			executeNonQuery("LOCK TABLES pos_invoice WRITE;");
			CachedRowSet crs=executeQuery(sql);
			if(crs!=null){
				
				if(crs.next()){
					
					id=crs.getInt("InvoiceNo");
				}
			}else{
				throw new Exception("Failed to get Invoice number");
			}
			
		}catch(Exception e){
			
			PosLog.write(this, "GetInoiveNo ("+orderid+","+addNewNo+")", e);
			throw new Exception("Failed to get Invoice number");
			
		}finally{
			
//			executeNonQuery("UNLOCK TABLES;");
		}
		
		return id==0?"": PosOrderUtil.getFormatedInvoiceNumber(id) ;
	}
	
	/* (non-Javadoc)
	 * @see com.indocosmo.pos.data.providers.PosDatabaseProvider#deleteData(java.lang.String)
	 */
	@Override
	public int deleteData(String where) throws SQLException {
	
		final String sql= "delete from pos_invoice where "+where;
		
		return executeNonQuery(sql);
		
	}
	 
}
