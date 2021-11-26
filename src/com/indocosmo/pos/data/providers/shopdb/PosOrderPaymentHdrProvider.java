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
import com.indocosmo.pos.common.utilities.PosOrderUtil;
import com.indocosmo.pos.data.beans.BeanCashierShift;
import com.indocosmo.pos.data.beans.BeanCurrency;
import com.indocosmo.pos.data.beans.BeanDiscount;
import com.indocosmo.pos.data.beans.BeanOrderHeader;
import com.indocosmo.pos.data.beans.BeanOrderHeader.PosOrderStatus;
import com.indocosmo.pos.data.beans.BeanOrderPayment;
import com.indocosmo.pos.data.beans.BeanOrderPaymentHeader;
import com.indocosmo.pos.data.beans.BeanPayModeBase.PaymentMode;
import com.indocosmo.pos.data.beans.BeanPaymentSummary;
import com.indocosmo.pos.data.beans.BeanUser;

/**
 * @author sandhya
 *
 */
public class PosOrderPaymentHdrProvider extends PosShopDBProviderBase {
	
	   private PreparedStatement mInsertDetailItemPs;
	   private PreparedStatement mUpateDetailItemPs;
	   
	   
	public PosOrderPaymentHdrProvider() {
		super("v_order_payment_hdr");
		
	}
	

	private void initPreparedStatment() throws SQLException {

		if (mInsertDetailItemPs != null && PosDBUtil.getInstance().isValidConnection(mInsertDetailItemPs.getConnection())) 
			mInsertDetailItemPs.clearParameters();
		 else
			mInsertDetailItemPs = getConnection().prepareStatement(getInsertStatement());

		if (mUpateDetailItemPs != null && PosDBUtil.getInstance().isValidConnection(mUpateDetailItemPs.getConnection())) 
			mUpateDetailItemPs.clearParameters();
		 else
			 mUpateDetailItemPs = getConnection().prepareStatement(getUpdateStatement());


	}

	 private String getUpdateStatement() {

		 
		 String update_sql="update " + " order_payment_hdr set " + 
		  "order_id=?," + 
		  "payment_date=?," + 
		  "payment_time=?," + 
		  "shift_id=?," + 
		  "detail_total=?," + 
		  "total_tax1=?," + 
		  "total_tax2=?," + 
		  "total_tax3=?," + 
		  "total_gst=?," + 
		  "total_sc=?," + 
		  "total_detail_discount=?," + 
		  "final_round_amount=?," + 
		  "total_amount=?," + 
		  "total_amount_paid=?," + 
		  "total_balance=?," + 
		  "actual_balance_paid=?," + 
		  "cash_out=?," + 
		  "bill_less_tax_amount=?," + 
		  "bill_discount_amount=?," + 
		  "is_refund=?," + 
		  "remarks=?," + 
		  "created_by=?," + 
		  "created_at=?," + 
		  "updated_by=?," + 
		  "updated_at=? ," + 
		  "is_advance=?, " +
		  "is_final=? " +
		  " where id=? and order_id=?"; 
	      return update_sql;
	   }


	   private String getInsertStatement() {
		   
		   
 
	      String insert_sql="insert into "+"order_payment_hdr "+" ("    +
	    		  	"order_id," + 
					"payment_date," +
					"payment_time," +
					"shift_id," +
					"detail_total," +
					"total_tax1," +
					"total_tax2," +
					"total_tax3," +
					"total_gst," +
					"total_sc," +
					"total_detail_discount," +
					"final_round_amount," +
					"total_amount," +
					"total_amount_paid," +
					"total_balance," +
					"actual_balance_paid," +
					"cash_out," +
					"bill_less_tax_amount," +
					"bill_discount_amount," +
					"is_refund," +
					"remarks," +
					"created_by," +
					"created_at," +
					"updated_by," +
					"updated_at," +
					"is_advance," +
					"is_final," +
					"id" +
					
					")" +
					" values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,? )";
							
	      return insert_sql;
	   }
	   


	/**
	 * @param orderHeader
	 * @throws SQLException
	 */
	public void save(BeanOrderHeader orderHeader) throws SQLException{
		
		PosLog.debug("Satrting to save order Payment header...... ");
		
		ArrayList<BeanOrderPaymentHeader> orderPaymentHdrList=orderHeader.getOrderPaymentHeaders();
		if (orderPaymentHdrList==null) return;
		
		
         
		initPreparedStatment();
		int counter=0;
		for (BeanOrderPaymentHeader payHdr: orderPaymentHdrList){	
			 
//			 final boolean isExist = isExist("id='"+payHdr.getId()+"'");
//	         detailItemPs=((isExist)?mUpateDetailItemPs:mInsertDetailItemPs);

	         final String id=PosOrderUtil.appendToId(orderHeader.getOrderId(), counter++);
			 payHdr.setId(id);
			
 			 addPreparedStatement((payHdr ) );
			 
				
		}
		
		executePS();
		
		PosLog.debug("Finished save order payment header...... ");
	} 
	
	/**
	 * @param orderHeader
	 * @throws SQLException
	 */
	public void save( BeanOrderPaymentHeader orderPaymentHdr) throws SQLException{
		
		if (!orderPaymentHdr.isNew())
			return;
		
		PosLog.debug("Satrting to save order payment header...... ");
		
	 	initPreparedStatment();
					
		addPreparedStatement(orderPaymentHdr );
	 	
		executePS();
		
		PosLog.debug("Finished save order payment header...... ");
	} 
	
	/*
	 * 
	 */
	private void addPreparedStatement(BeanOrderPaymentHeader payHdrItem)throws SQLException{
		
		PreparedStatement prep;
		final boolean isExist = isExist("order_id='"+payHdrItem.getOrderId()+"' and id='"+payHdrItem.getId()  +"'");
		
		if (isExist) {
		            /*We need to update data if exist*/
		    prep=mUpateDetailItemPs;
		 } else {
		            /*We need to insert data if not exist*/
		    prep=mInsertDetailItemPs;
		 }
		
//		prep=mInsertDetailItemPs;
		prep.setString(1, payHdrItem.getOrderId());
		prep.setString(2, payHdrItem.getPaymentDate());
		prep.setString(3, payHdrItem.getPaymentTime());
		prep.setInt(4, payHdrItem.getShiftId());
		prep.setDouble(5, payHdrItem.getDetailTotal());
		prep.setDouble(6, payHdrItem.getTotalTax1());
		prep.setDouble(7, payHdrItem.getTotalTax2());
		prep.setDouble(8, payHdrItem.getTotalTax3());
		prep.setDouble(9, payHdrItem.getTotalGST());
		prep.setDouble(10, payHdrItem.getTotalServiceTax());
		prep.setDouble(11, payHdrItem.getTotalDetailDiscount());
		prep.setDouble(12, payHdrItem.getRoundAdjustmentAmount());
		prep.setDouble(13, payHdrItem.getTotalAmount());
		prep.setDouble(14, payHdrItem.getTotalAmountPaid());
		prep.setDouble(15, payHdrItem.getChangeAmount());
		prep.setDouble(16, payHdrItem.getActualBalancePaid());
		prep.setDouble(17, payHdrItem.getCashOut());
		prep.setDouble(18, payHdrItem.getBillTaxAmount());
		prep.setDouble(19, payHdrItem.getBillDiscountAmount());
		prep.setBoolean(20, payHdrItem.isRefund());
		prep.setString(21, payHdrItem.getRemarks());
		 
		prep.setInt(22, PosEnvSettings.getInstance().getCashierShiftInfo().getCashierInfo().getId());
		prep.setString(23, payHdrItem.getPaymentDate());
		prep.setInt(24, PosEnvSettings.getInstance().getCashierShiftInfo().getCashierInfo().getId());
		prep.setString(25, payHdrItem.getPaymentDate());
		prep.setBoolean(26, payHdrItem.isAdvance());
		prep.setBoolean(27, payHdrItem.isFinal());
		 prep.setString(28, payHdrItem.getId()); 
		 
		 if (isExist) {
	         prep.setString(29, payHdrItem.getOrderId());
		 } 
			
		prep.addBatch();
		
	} 
	
	
	
	/**
	 * @throws SQLException
	 */
	private void executePS() throws SQLException{
		

		if (mInsertDetailItemPs != null) 
			mInsertDetailItemPs.executeBatch();
		 
		if (mUpateDetailItemPs != null) 
			mUpateDetailItemPs.executeBatch();
		
	}

 
	/**
	 * @param order
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<BeanOrderPaymentHeader> getOrderPaymentHeaders(BeanOrderHeader order) throws SQLException{

		return getOrderPaymentHeaders(order.getOrderId());
	}
	
	/**
	 * @param orderID
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<BeanOrderPaymentHeader> getOrderPaymentHeaders(String orderID) throws SQLException{
		ArrayList<BeanOrderPaymentHeader> orderPaymenHdrList=null;
		final String where="order_id='"+orderID+"'";
		CachedRowSet crs=getData(where);
		if(crs!=null){
			orderPaymenHdrList=new ArrayList<BeanOrderPaymentHeader>();
			while(crs.next()){
				BeanOrderPaymentHeader payment=creatPaymentFromRecordset(crs);
				orderPaymenHdrList.add(payment);
			}
			crs.close();
		}
		return orderPaymenHdrList;
	}
	 
	private BeanOrderPaymentHeader creatPaymentFromRecordset(CachedRowSet crs) throws SQLException {
		
		BeanOrderPaymentHeader paymentHdr=new BeanOrderPaymentHeader();
		paymentHdr.setId(crs.getString("id"));
		paymentHdr.setOrderId(crs.getString("order_id"));
		paymentHdr.setPaymentDate(crs.getString("payment_date"));
		paymentHdr.setPaymentTime(crs.getString("payment_time"));
		paymentHdr.setShiftId(crs.getInt("shift_id"));
		paymentHdr.setDetailTotal(crs.getDouble("detail_total"));
		paymentHdr.setTotalTax1(crs.getDouble("total_tax1"));
		paymentHdr.setTotalTax2(crs.getDouble("total_tax2"));
		paymentHdr.setTotalTax3(crs.getDouble("total_tax3"));
		paymentHdr.setTotalGST(crs.getDouble("total_gst"));
		paymentHdr.setTotalServiceTax(crs.getDouble("total_sc"));
		paymentHdr.setTotalDetailDiscount(crs.getDouble("total_detail_discount"));
		paymentHdr.setRoundAdjustmentAmount(crs.getDouble("final_round_amount"));
		paymentHdr.setTotalAmount(crs.getDouble("total_amount"));
		paymentHdr.setTotalAmountPaid(crs.getDouble("total_amount_paid"));
		paymentHdr.setChangeAmount(crs.getDouble("total_balance"));
		paymentHdr.setActualBalancePaid(crs.getDouble("actual_balance_paid"));
		paymentHdr.setCashOut(crs.getDouble("cash_out"));
		paymentHdr.setRefund(crs.getBoolean("is_refund"));
		paymentHdr.setAdvance(crs.getBoolean("is_advance"));
		paymentHdr.setFinal(crs.getBoolean("is_final"));
		paymentHdr.setRemarks(crs.getString("remarks"));
		return paymentHdr;
	}
	
	 	
	/* (non-Javadoc)
	 * @see com.indocosmo.pos.data.providers.PosDatabaseProvider#deleteData(java.lang.String)
	 */
	@Override
	public int deleteData(String where) throws SQLException {
		// TODO Auto-generated method stub
		return super.deleteData("order_payment_hdr",where);
	}
	
	
	/*
	 * 		
	 */
	public double getTotalRoundinAdjustments(String where,Integer shiftId,boolean isRefund) throws Exception{
		double roundTotal=0;
//		String sql="select sum(final_round_amount) as total_round_amount from order_payment_hdr where "+ where  ;
		
		final String sql="select sum(ophdr.final_round_amount) as total_round_amount " +
				" from order_payment_hdr ophdr JOIN order_hdrs ohdr ON ophdr.order_id=ohdr.order_id " +
				" where   ophdr.is_refund=" + (isRefund?1:0) + " AND "+ where  + (shiftId>0? " AND ophdr.shift_id=" + shiftId : "")  ;
		CachedRowSet crs=executeQuery(sql);
		if(crs!=null){
			try {
				if(crs.next())
					roundTotal=crs.getDouble("total_round_amount");
			} catch (Exception e) {
				PosLog.write(this, "getTotalRoundinAdjustments", e);
				throw new Exception("Failed to get order payment header details.");
			}finally{
				try {
					crs.close();
				} catch (SQLException e) {
					PosLog.write(this, "getTotalRoundinAdjustments", e);
					throw new Exception("Failed to get order  payment header details.");
				}
			}
		}
		return roundTotal;
	}
	/*
	 * 		
	 */
//	public double getTotalBillTaxForShiftSummaryReport(String posDate,Integer shiftId) throws Exception{
//
//		String where=" ophdr.payment_date ='" + posDate + "'  ";
////		String whereExtraCharge= " status<>" + PosOrderStatus.Void.getCode() + " AND  closing_date ='" + posDate + "'  ";
//		return getTotalBillTax(where,shiftId);//+ getTotalExtraChargeTax(whereExtraCharge,shiftId);
//	}
	/*
	 * 		
	 */
//	public double getTotalBillTax(String where,Integer shiftId) throws Exception{
//		
//		where = where + " and status in (" + PosOrderStatus.Closed.getCode()+   "," + PosOrderStatus.Refunded.getCode() + ")";
//		where = where.replace("payment_date", "closing_date");
//		where = where.replace("payment_time", "closing_time");
//		
//		double taxTotal=0;
//		BeanCurrency currnecy= PosEnvSettings.getInstance().getCurrency();
//		final int prec = currnecy.getDecimalPlaces(); 
//		String sql="SELECT   " + 
//				" (select sum(round((ophdr.total_tax1 +ophdr.total_tax2 +ophdr.total_tax3 + ophdr.total_gst + ophdr.total_sc)-" +
//				"  	(ophdr.total_tax1 + ophdr.total_tax2 + ophdr.total_tax3 + ophdr.total_gst + ophdr.total_sc)* " + 
//				"	bill_discount_percentage/100.00,"+ prec +  ")) as total_sales_tax " + 
//				"  from order_payment_hdr ophdr JOIN order_hdrs ohdr ON ophdr.order_id=ohdr.order_id  " + 
//				" WHERE is_refund=0 AND " + where  + (shiftId>0? " AND ophdr.shift_id=" + shiftId : "") +  ") total_sales_tax, " +
//				" (select sum(round(ophdr.total_tax1 +ophdr.total_tax2 +ophdr.total_tax3 + ophdr.total_gst + ophdr.total_sc,"+ prec +  ")) as total_refund_tax " + 
//				"  from order_payment_hdr ophdr JOIN order_hdrs ohdr ON ophdr.order_id=ohdr.order_id  " + 
//				" WHERE is_refund=1 AND " + where  + (shiftId>0? " AND ophdr.shift_id=" + shiftId : "") +  ") total_refund_tax"; 
//	 
//
//		CachedRowSet crs=executeQuery(sql);
//		if(crs!=null){
//			try {
//				if(crs.next()){
//
//					taxTotal=crs.getDouble("total_sales_tax") -crs.getDouble("total_refund_tax") ;
//				}
//					
//			} catch (Exception e) {
//				PosLog.write(this, "getTotalBillTax", e);
//				throw new Exception("Failed to get order payment header details.");
//			}finally{
//				try {
//					crs.close();
//				} catch (SQLException e) {
//					PosLog.write(this, "getTotalBillTax", e);
//					throw new Exception("Failed to get order  payment header details.");
//				}
//			}
//		}
//		return taxTotal;
//	}

	
//	/*
//	 * 		
//	 */
//	public double getTotalExtraChargeTax(String where,Integer shiftId) throws Exception{
//		double taxTotal=0;
//		
//		String sql= "select sum((extra_charge_tax1_amount+extra_charge_tax2_amount+extra_charge_tax3_amount + extra_charge_sc_amount + extra_charge_gst_amount) -" +
//				"  (extra_charge_tax1_amount+extra_charge_tax2_amount+extra_charge_tax3_amount + extra_charge_sc_amount + extra_charge_gst_amount)* " + 
//				"	bill_discount_percentage/100.00) as total_ec_tax " + 
//				"  from  order_hdrs    " + 
//				" WHERE   " + where  + (shiftId>0? " AND  shift_id=" + shiftId : "") ;
//		
//
//		CachedRowSet crs=executeQuery(sql);
//		if(crs!=null){
//			try {
//				if(crs.next()){
//
//					taxTotal=crs.getDouble("total_ec_tax")   ;
//				}
//					
//			} catch (Exception e) {
//				PosLog.write(this, "getTotalExtraChargeTax", e);
//				throw new Exception("Failed to get order  header details.");
//			}finally{
//				try {
//					crs.close();
//				} catch (SQLException e) {
//					PosLog.write(this, "getTotalExtraChargeTax", e);
//					throw new Exception("Failed to get order   header details.");
//				}
//			}
//		}
//		return taxTotal;
//	}
	
 
}
