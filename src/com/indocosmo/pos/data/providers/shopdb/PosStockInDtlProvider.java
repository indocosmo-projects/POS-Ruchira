/**
 * 
 */
package com.indocosmo.pos.data.providers.shopdb;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import com.indocosmo.pos.common.PosLog;
import com.indocosmo.pos.common.utilities.PosDBUtil;
import com.indocosmo.pos.data.beans.BeanStockInDetail;
import com.indocosmo.pos.data.beans.BeanStockInHeader;

/**
 * @author sandhya
 *
 */
public class PosStockInDtlProvider extends PosShopDBProviderBase {
	
	   private PreparedStatement mInsertDetailItemPs;
 

	public PosStockInDtlProvider() {
		super("");
		
	}
	
	/**
	 * @param orderDetlList 
	 * @return
	 * @throws SQLException
	 */
	public void save(BeanStockInHeader stockInHdr) throws Exception{
		
		PosLog.debug("Satrting to save Stock in dtl...... ");

		final ArrayList<BeanStockInDetail> detlList=stockInHdr.getDetails();
		
		initPreparedStatment();

		for(BeanStockInDetail stockInDtl:detlList){
			addPreparedStatement(stockInHdr.getId(), stockInDtl);
		}
		executePS();
		
		PosLog.debug("Finished save order dtl...... ");
	} 
	
	/**
	 * Initializes the prepared statements
	 * @throws SQLException
	 */
 
	private void initPreparedStatment() throws SQLException {

		if (mInsertDetailItemPs != null && PosDBUtil.getInstance().isValidConnection(mInsertDetailItemPs.getConnection())) 
			mInsertDetailItemPs.clearParameters();
		 else
			mInsertDetailItemPs = getConnection().prepareStatement(getInsertStatement());
 

	}
 

	   private String getInsertStatement() {
	      String insert_sql="insert into  stock_in_dtl ("    +
	            "stock_in_hdr_id, "+
	            "stock_item_id, "+
	            "description ,"+
	            "qty_received "+
	            ") "+
	            " values (?,?,?,?)";
	      return insert_sql;
	   }
	   
 
	/*
	 * 
	 */
	private void addPreparedStatement(int hdrId,BeanStockInDetail stockInDtl)throws SQLException{
		
		PreparedStatement prep;
		prep=mInsertDetailItemPs;
		 
		prep.setInt(1, hdrId);
		prep.setInt(2, stockInDtl.getItemId());
		prep.setString(3, stockInDtl.getDescription());
		prep.setDouble(4,stockInDtl.getQuantity());
		prep.addBatch();
		
	} 
	/**
	 * @throws SQLException
	 */
	private void executePS() throws SQLException{
		
		if (mInsertDetailItemPs != null) 
			mInsertDetailItemPs.executeBatch();
	}
	
 
	
}
