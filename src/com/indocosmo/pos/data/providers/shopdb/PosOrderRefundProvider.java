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
import com.indocosmo.pos.data.beans.BeanOrderHeader;
import com.indocosmo.pos.data.beans.BeanOrderPayment;
import com.indocosmo.pos.data.beans.BeanOrderRefund;
import com.indocosmo.pos.data.beans.BeanPayModeBase.PaymentMode;
import com.indocosmo.pos.data.beans.BeanPaymentSummary;
import com.indocosmo.pos.data.beans.BeanUser;

/**
 * @author sandhya
 *
 */
public class PosOrderRefundProvider extends PosShopDBProviderBase {
	
	   private PreparedStatement mInsertDetailItemPs;
 

	public PosOrderRefundProvider() {
		super("order_refunds");
		
	}
	
	
	public double getTotalRefundAmount(String orderId){
		
		double totRefundAmt=0;
		
		final String sql="SELECT  sum(paid_amount) as tot_amount_refunded FROM  order_payments " +
		"   where is_repayment=1 AND order_id='" + orderId+"'";
		
		
	 	CachedRowSet crs=executeQuery(sql);
		
		if(crs!=null){
			try {
				
				if(crs.next())
					totRefundAmt=crs.getDouble("tot_amount_refunded");
					 
				crs.close();
			} catch (SQLException e) {
				PosLog.write(this, "getTotalRefundAmount", e);
				try {
					crs.close();
				} catch (SQLException e1) {
					PosLog.write(this, "getTotalRefundAmount", e1);
				}
			}
		}
		return totRefundAmt;
	}
	
	
	public double getTotalRefundableAmount(String orderId){
		
		double totRefundableAmt=0;
		
//		final String sql="SELECT  sum(item_total) as tot_amount_refunded FROM  order_refunds " +
//		" JOIN order_dtls on order_refunds.order_dtl_id=order_dtls.id  " +
//					" where  order_refunds.order_id='" + orderId+"'";
		
		
		final String sql="SELECT  sum(item_total) as tot_refundable_amount FROM  order_dtls  WHERE order_id='" + orderId+"' AND " +
						" id NOT IN(SELECT order_dtl_id FROM  order_refunds WHERE order_id='" + orderId+"')";
		CachedRowSet crs=executeQuery(sql);
		
		if(crs!=null){
			try {
				
				if(crs.next())
					totRefundableAmt=crs.getDouble("tot_refundable_amount");
					 
				crs.close();
			} catch (SQLException e) {
				PosLog.write(this, "getTotalRefundableAmount", e);
				try {
					crs.close();
				} catch (SQLException e1) {
					PosLog.write(this, "getTotalRefundableAmount", e1);
				}
			}
		}
		return totRefundableAmt;
	}

	private void initPreparedStatment() throws SQLException {

		if (mInsertDetailItemPs != null && PosDBUtil.getInstance().isValidConnection(mInsertDetailItemPs.getConnection())) 
			mInsertDetailItemPs.clearParameters();
		 else
			mInsertDetailItemPs = getConnection().prepareStatement(getInsertStatement());

//		if (mUpateDetailItemPs != null) 
//			mUpateDetailItemPs.clearParameters();
//		else
//			mUpateDetailItemPs = getConnection().prepareStatement(getUpdateStatement());

	}
 

	
//	 private String getUpdateStatement() {
//
//	      String update_sql="update "+"order_refunds set "+
//	            "order_id=?, "+
//	            "order_dtl_id=?, "+
//	            "order_payment_id=? "+
//	            " where  order_id=? and id=?";
//	      return update_sql;
//	   }


	   private String getInsertStatement() {
	      String insert_sql="insert into "+"order_refunds "+" ("    +
	            "order_id, "+
	            "order_dtl_id, "+
	            "order_payment_id ,"+
	            "paid_amount ," +
	            "qty,"+
	            "tax1_amount," +
	            "tax2_amount," +
	            "tax3_amount," +
	            "sc_amount,"+
	            "gst_amount,"+
	            "refund_date,"+
	            "refund_time," +
	            "refunded_by," + 
	            "created_by, " +
	            "created_at," +
	            "updated_by," + 
	            "updated_at "+
	            ") "+
	            " values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	      return insert_sql;
	   }
	   
 
	
	

	/**
	 * @param orderHeader
	 * @throws SQLException
	 */
	public void save(ArrayList<BeanOrderRefund> ordRefundItemList) throws  Exception{
		
		PosLog.debug("Satrting to save order refund details ...... ");
		
		 
		initPreparedStatment();
		 
		for (BeanOrderRefund refundItem: ordRefundItemList){	
			
		 	addPreparedStatement(refundItem );
		}
		
		executePS();
		
		PosLog.debug("Finished save order refund...... ");
		
		PosStockInHdrProvider stockInProvider=new PosStockInHdrProvider();
		stockInProvider.save(ordRefundItemList);
	} 
	/*
	 * 
	 */
	private void addPreparedStatement(BeanOrderRefund refundItem)throws SQLException{
		
		PreparedStatement prep;
		prep=mInsertDetailItemPs;
		 
		prep.setString(1, refundItem.getOrderId());
		prep.setString(2, refundItem.getOrderDetail().getId());
		prep.setString(3, refundItem.getOrderPaymentId());
		prep.setDouble(4, refundItem.getPaidAmount());
		prep.setDouble(5, refundItem.getQuantity());
		prep.setDouble(6, refundItem.getTax1Amount());
		prep.setDouble(7, refundItem.getTax2Amount());
		prep.setDouble(8, refundItem.getTax3Amount());
		prep.setDouble(9, refundItem.getTaxSCAmount());
		prep.setDouble(10, refundItem.getTaxGSTAmount());
		prep.setString(11,PosEnvSettings.getPosEnvSettings().getPosDate() );
		prep.setString(12, PosDateUtil.getDateTime());
		prep.setInt(13,  refundItem.getRefundedBy().getId());
		prep.setInt(14, PosEnvSettings.getInstance().getCashierShiftInfo().getCashierInfo().getId());
		prep.setString(15, PosDateUtil.getDateTime());
		prep.setInt(16, PosEnvSettings.getInstance().getCashierShiftInfo().getCashierInfo().getId());
		prep.setString(17, PosDateUtil.getDateTime());
		prep.addBatch();
		
	} 
	/**
	 * @throws SQLException
	 */
	private void executePS() throws SQLException{
		

		if (mInsertDetailItemPs != null) 
			mInsertDetailItemPs.executeBatch();
		 
//		if (mUpateDetailItemPs != null) 
//			mUpateDetailItemPs.executeBatch();
		
	}

	
	public CachedRowSet getRefundHeaderForHMS(String paymentId){
		
		final String sql="SELECT " +
					" ohdr.invoice_prefix, ohdr.invoice_no,ohdr.order_id, ohdr.order_no, " +
					" opay.payment_date as refund_date , opay.payment_time as refund_time," + 
					" opay.cashier_id as user_id,opay.cashier_shift_id as shift_id,opay.paid_amount as refund_amount " +  
					" FROM order_hdrs ohdr " + 
					" JOIN order_payments opay ON ohdr.order_id=opay.order_id " +  
					" WHERE opay.id='" + paymentId + "'";
		
		CachedRowSet crs=executeQuery(sql);
		return crs;
	}
	
	public CachedRowSet getRefundDetailsForHMS(String paymentId){
		
		final String sql="SELECT odtl.sale_item_code, odtl.`name`,odtl.name_to_print ,"  +
					" odtl.sub_class_code,odtl.sub_class_name,odtl.uom_code,odtl.uom_name,odtl.uom_symbol," + 
					" odtl.tax_code,odtl.tax_name, " + 
					" odtl.is_tax1_applied,odtl.tax1_name,odtl.tax1_pc,oref.tax1_amount, " + 
					" odtl.is_tax2_applied,odtl.tax2_name,odtl.tax2_pc,oref.tax2_amount, " + 
					" odtl.is_tax3_applied,odtl.tax3_name,odtl.tax3_pc,oref.tax3_amount, "+
					" odtl.is_sc_applied,odtl.sc_name,odtl.sc_pc,oref.sc_amount, "+
					" odtl.fixed_price," + 
					" oref.qty,oref.paid_amount as refund_amount, oref.refund_date," +
					" oref.refund_time,oref.refunded_by " +
					" FROM order_refunds oref " +
					" JOIN order_dtls odtl on oref.order_dtl_id =odtl.id " +
					" WHERE oref.order_payment_id='" + paymentId + "'";
		
		CachedRowSet crs=executeQuery(sql);
		return crs;
	}
}
