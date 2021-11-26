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
import com.indocosmo.pos.data.beans.BeanOrderPayment;
import com.indocosmo.pos.data.beans.BeanOrderPaymentHeader;
import com.indocosmo.pos.data.beans.BeanOrderRefund;
import com.indocosmo.pos.data.beans.BeanPayModeBase.PaymentMode;
import com.indocosmo.pos.data.beans.BeanPaymentSummary;
import com.indocosmo.pos.data.beans.BeanStockInDetail;
import com.indocosmo.pos.data.beans.BeanStockInHeader;
import com.indocosmo.pos.data.beans.BeanStockInHeader.StockInType;
import com.indocosmo.pos.data.beans.BeanStockItem;
import com.indocosmo.pos.data.beans.BeanUser;

/**
 * @author sandhya
 *
 */
public class PosStockInHdrProvider extends PosShopDBProviderBase {
	
	   
	private PreparedStatement mInsertDetailItemPs;
	private PosStockInDtlProvider mStockInDtlProvider;

	public PosStockInHdrProvider() {
		
		super("");
		mStockInDtlProvider=new PosStockInDtlProvider();
		
	}
	
	  

	private void initPreparedStatment() throws SQLException {

		if (mInsertDetailItemPs != null && PosDBUtil.getInstance().isValidConnection(mInsertDetailItemPs.getConnection())) 
			mInsertDetailItemPs.clearParameters();
		 else
			mInsertDetailItemPs = getConnection().prepareStatement(getInsertStatement());
	}
 
	   private String getInsertStatement() {
	    
		   String insert_sql="insert into  stock_in_hdr ("    +
	            "id, "+
	            "date, "+
	            "type ,"+
	            "created_by, " +
	            "created_at," +
	            "updated_by," + 
	            "updated_at "+
	            ") "+
	            " values (?,?,?,?,?,?,?)";
	      return insert_sql;
	   }
	   
	   /**
		 * @param BeanOrderRefund
		 * @throws Exception 
		 */
		public  void save(ArrayList<BeanOrderRefund> ordRefundItemList) throws Exception{
			
			
			if (ordRefundItemList==null || ordRefundItemList.size()==0)
				return;
			
			BeanStockInHeader stockInHdr=new BeanStockInHeader();
			BeanStockInDetail stockInDtl;
			ArrayList<BeanStockInDetail> detailList=new ArrayList<BeanStockInDetail>();		
			
			stockInHdr.setDate(PosEnvSettings.getPosEnvSettings().getPosDate());
			stockInHdr.setType(StockInType.Stock_In);
			for(BeanOrderRefund ordRefund:ordRefundItemList){
				 
					stockInDtl=new BeanStockInDetail();
					stockInDtl.setItemId(ordRefund.getOrderDetail().getSaleItem().getStockItemId());
					stockInDtl.setQuantity(ordRefund.getQuantity());
					stockInDtl.setDescription("Refund");
					detailList.add(stockInDtl);
				 
				
			}
			stockInHdr.setDetails(detailList);
					
			save(stockInHdr);
		}
		  
		
   /**
	 * @param orderHeader
	 * @throws Exception 
	 */
	public  void save(BeanOrderHeader orderHdr ) throws Exception{
		
		BeanStockInHeader stockInHdr=new BeanStockInHeader();
		BeanStockInDetail stockInDtl;
		ArrayList<BeanStockInDetail> detailList=new ArrayList<BeanStockInDetail>();		
		
		stockInHdr.setDate(orderHdr.getClosingDate());
		stockInHdr.setType(StockInType.Sales);
		
		for(BeanOrderDetail orderDtl:orderHdr.getOrderDetailItems()){
			if(!orderDtl.isVoid()){
				
				stockInDtl=new BeanStockInDetail();
				stockInDtl.setItemId(orderDtl.getSaleItem().getStockItemId());
				stockInDtl.setQuantity(orderDtl.getSaleItem().getQuantity());
				stockInDtl.setDescription("Sales");
				detailList.add(stockInDtl);
			}
			
		}
		stockInHdr.setDetails(detailList);
				
		save(stockInHdr);
	}
	  
	/**
	 * @param stockInHdr
	 * @throws Exception 
	 */
	private  void save(BeanStockInHeader stockInHdr ) throws Exception{
		
		PosLog.debug("Satrting to save Stock In ...... ");
		try{
			mConnection.setAutoCommit(false);
	
			saveHdr(stockInHdr);
			mStockInDtlProvider.save(stockInHdr);
			mConnection.commit();
			mConnection.setAutoCommit(true);
			
			PosLog.debug("Finished save Stock In...... ");
		
		} catch (Exception e) {
				
				try {
					
					mConnection.rollback();
				} catch (Exception e1) {
					
					PosLog.write(this," saveStockHdr ",e1);
				}
				
				PosLog.write(this," saveStockHdr ",e);
				throw new Exception("Failed to save Stock In . Please check the log for details");
			}finally{
	
			}
	} 
	private void saveHdr(BeanStockInHeader stockInHdr ) throws SQLException{
	
		initPreparedStatment();
		addPreparedStatement(stockInHdr);
		executePS();
	}
	/*
	 * 
	 */
	private void addPreparedStatement(BeanStockInHeader stockInHdr)throws SQLException{
		
		 
		PosCounterProvider pc = new PosCounterProvider();
		
		stockInHdr.setId(pc.getNextStockInHdrNumber());
		
		mInsertDetailItemPs.setInt(1, stockInHdr.getId());
		mInsertDetailItemPs.setString(2, stockInHdr.getDate());
		mInsertDetailItemPs.setInt(3, stockInHdr.getType().getValue()); 
		mInsertDetailItemPs.setInt(4, PosEnvSettings.getInstance().getCashierShiftInfo().getCashierInfo().getId());
		mInsertDetailItemPs.setString(5, PosDateUtil.getDateTime());
		mInsertDetailItemPs.setInt(6, PosEnvSettings.getInstance().getCashierShiftInfo().getCashierInfo().getId());
		mInsertDetailItemPs.setString(7, PosDateUtil.getDateTime());
		pc.updateStockInHdrNumber();
		
	} 
	/**
	 * @throws SQLException
	 */
	private void executePS() throws SQLException{
		
		if (mInsertDetailItemPs != null) 
			mInsertDetailItemPs.execute();
	}
	
 public ArrayList<BeanStockItem> getStockItems() throws SQLException{
	 
	 ArrayList<BeanStockItem> stockItems=null;
	 final String posDate=PosEnvSettings.getInstance().getPosDate();
	 final String sql="SELECT v_stock.stock_item_id,stock_items.name as item_name,"+
			 " SUM(IF(v_stock.date<'" + posDate + "',qty_received,0)) AS opening_stock," +
			 " SUM(IF(v_stock.date='" + posDate + "' AND type=" + StockInType.Sales.getValue() + ",qty_received,0)) AS sales," +
			 " SUM(IF(v_stock.date='" + posDate + "' AND type=" + StockInType.Adjustment.getValue() + ",qty_received,0)) AS adjustment," +
			 " SUM(IF(v_stock.date='" + posDate + "' AND type=" + StockInType.Disposal.getValue() + ",qty_received,0)) AS disposal," + 
			 " SUM(IF(v_stock.date='" + posDate + "' AND type=" + StockInType.Stock_In.getValue() + ",qty_received,0)) AS stock_in, " + 
			 " SUM(qty_received) AS current_stock " +
			 " FROM v_stock_in_out_preview v_stock " +
			 " LEFT JOIN stock_items ON v_stock.stock_item_id = stock_items.id " +
			 " GROUP BY v_stock.stock_item_id ";
	 
		CachedRowSet crs=executeQuery(sql);
		if(crs!=null){
			stockItems=new ArrayList<BeanStockItem>();
			while(crs.next()){
				BeanStockItem stockItem=creatStockItemsFromRecordset(crs);
				stockItems.add(stockItem);
			}
			crs.close();
		}
	 return stockItems;
 }
 
 private BeanStockItem creatStockItemsFromRecordset(CachedRowSet crs) throws SQLException {
 
	 BeanStockItem stockItem=new BeanStockItem();
	 stockItem.setId(crs.getInt("stock_item_id"));
	 stockItem.setName(crs.getString("item_name"));
	 stockItem.setOpeningStock(crs.getDouble("opening_stock"));
	 stockItem.setCurrentStock(crs.getDouble("current_stock"));
	 
	 stockItem.setSalesQty(crs.getDouble("sales"));
	 stockItem.setAdjustmentQty(crs.getDouble("adjustment"));
	 stockItem.setDisposalQty(crs.getDouble("disposal"));
	 stockItem.setStockInQty(crs.getDouble("stock_in"));
	 return stockItem;
 }
		

  
	
}
