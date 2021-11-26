/**
 * 
 */
package com.indocosmo.pos.data.providers.shopdb;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import javax.sql.rowset.CachedRowSet;

import com.indocosmo.pos.common.PosEnvSettings;
import com.indocosmo.pos.common.PosLog;
import com.indocosmo.pos.common.enums.MessageDisplayStatus;
import com.indocosmo.pos.common.utilities.PosDBUtil;
import com.indocosmo.pos.common.utilities.PosDateUtil;
import com.indocosmo.pos.common.utilities.PosOrderUtil;
import com.indocosmo.pos.data.beans.BeanCashierShift;
import com.indocosmo.pos.data.beans.BeanDiscount;
import com.indocosmo.pos.data.beans.BeanOrderHeader;
import com.indocosmo.pos.data.beans.BeanOrderPayment;
import com.indocosmo.pos.data.beans.BeanOrderHeader.PosOrderStatus;
import com.indocosmo.pos.data.beans.BeanOrderPaymentReport;
import com.indocosmo.pos.data.beans.BeanPMSOrderPayment;
import com.indocosmo.pos.data.beans.BeanPayModeBase.PaymentMode;
import com.indocosmo.pos.data.beans.BeanPaymentSummary;
import com.indocosmo.pos.data.beans.BeanUser;

/**
 * @author jojesh
 *
 */
public class PosOrderPaymentsProvider extends PosShopDBProviderBase {
	
	   private PreparedStatement mInsertDetailItemPs;
	   private PreparedStatement mUpateDetailItemPs;

	public PosOrderPaymentsProvider() {
		super("v_order_payments");
		
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

/*
 * 
 */
	   private String getInsertStatement() {
	      String insert_sql="insert into "+"order_payments "+" ("    +
	            "payment_mode, "+
	            "paid_amount, "+
	            "card_name, "+
	            "card_type, "+
	            "card_no, "+
	            "name_on_card, "+
	            "card_expiry_month, "+
	            "card_expiry_year, "+
	            "card_approval_code, "+
	            "card_account_type, "+
	            "company_id, "+
	            "voucher_id, "+
	            "voucher_value, "+
	            "voucher_count, "+
	            "cashier_id, "+
	            "payment_date, " +
	            "payment_time "+
	            ",discount_id" +
	            ",discount_code" +
	            ",discount_name" +
	            ",discount_description" +
	            ",discount_price" +
	            ",discount_is_percentage" +
	            ",discount_is_overridable" +
	            ",discount_amount" +
	            ",is_repayment" +
	            ",is_voucher_balance_returned" +
	            ",partial_balance" +
	            ",cashier_shift_id" +
				",created_by " +
				",created_at" + 
				",updated_by " +
				",updated_at" + 
	            ",order_payment_hdr_id" +
	            ",order_id "+
	            ",id " +
	            ") "+
	            " values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	      return insert_sql;
	      

	   }
	   

/*
 * 
 */
	   private String getUpdateStatement() {
		   
		      String insert_sql="update order_payments  SET "    +
		            "payment_mode=?, "+
		            "paid_amount=?, "+
		            "card_name=?, "+
		            "card_type=?, "+
		            "card_no=?, "+
		            "name_on_card=?, "+
		            "card_expiry_month=?, "+
		            "card_expiry_year=?, "+
		            "card_approval_code=?, "+
		            "card_account_type=?, "+
		            "company_id=?, "+
		            "voucher_id=?, "+
		            "voucher_value=?, "+
		            "voucher_count=?, "+
		            "cashier_id=?, "+
		            "payment_date=?, " +
		            "payment_time=?,"+
		            "discount_id=?," +
		            "discount_code=?," +
		            "discount_name=?," +
		            "discount_description=?," +
		            "discount_price=?," +
		            "discount_is_percentage=?," +
		            "discount_is_overridable=?," +
		            "discount_amount=?," +
		            "is_repayment=?," +
		            "is_voucher_balance_returned=?," +
		            "partial_balance=?," +
		            "cashier_shift_id=?," +
				      "updated_by=?, " +
			            "updated_at=? " +  
		             " WHERE order_payment_hdr_id=? AND " +
		            "order_id=? AND "+
		            "id=? " +
		          
		            " ";
		      return insert_sql;
		      
	          
		   } 

	/**
	 * @param orderHeader
	 * @throws SQLException
	 */
	public void save(BeanOrderHeader orderHeader) throws SQLException{
		
		PosLog.debug("Satrting to save order Payments...... ");
		
		ArrayList<BeanOrderPayment> orderPaymentlList=orderHeader.getOrderPaymentItems();
		
		initPreparedStatment();
					
		int counter=0;
		for (BeanOrderPayment payItem: orderPaymentlList){	

			final String id=PosOrderUtil.appendToId(orderHeader.getOrderId(), counter++);
			if(payItem.isNew()) 
				payItem.setId(id);
			addPreparedStatement(payItem );
			 
		}
		
		executePS();
		
		PosLog.debug("Finished save order payments...... ");
	} 
	
	/**
	 * @param orderHeader
	 * @throws SQLException
	 */
	public void save( BeanOrderPayment ordPayment) throws SQLException{
		
		if (!ordPayment.isNew()) return;
			
			
		PosLog.debug("Satrting to save order payment...... ");
		
	 	initPreparedStatment();
					
		addPreparedStatement(ordPayment );
	 	
		executePS();
		
		PosLog.debug("Finished save order payment...... ");
	} 
	
	/**
	 * @param orderHeader
	 * @throws SQLException
	 */
	public void save( BeanOrderPayment ordPayment, BeanOrderHeader orderHdr) throws Exception{
		

		mConnection.setAutoCommit(false);
			
		PosLog.debug("Starting to save order payment...... ");
		try{
			 
			
			final String sql="delete from order_payments where order_payment_hdr_id='" + ordPayment.getOrderPaymentHdrId() +"'";
			executeNonQuery(sql);
			
		 	initPreparedStatment();	
			addPreparedStatement(ordPayment );
			executePS();
			mConnection.commit(); 
			mConnection.setAutoCommit(true);
		}catch (Exception e) {
			
			try {
				
				mConnection.rollback();
			} catch (Exception e1) {
				
				PosLog.write(this,"EditOrderPayments ("+orderHdr.getOrderNo()+")",e1);
			}
			
			PosLog.write(this,"EditOrderPayments ("+orderHdr.getOrderNo()+")",e);
			throw new Exception("Failed to update payment. Please check the log for details");
		}finally{

		}
		PosLog.debug("Finished save order payment...... ");
	} 
	
	/*
	 * 
	 */
	private void addPreparedStatement(BeanOrderPayment payItem)throws SQLException{
		
		PreparedStatement prep;

		final boolean isExist = isExist("order_id='"+payItem.getOrderId()+"' and id='"+payItem.getId()  +"'");
	 
		if (isExist) {
		            /*We need to update data if exist*/
		    prep=mUpateDetailItemPs;
		 } else {
		            /*We need to insert data if not exist*/
		    prep=mInsertDetailItemPs;
		 }
		
//		 prep=mInsertDetailItemPs;
		prep.setInt(1, payItem.getPaymentMode().getValue());
		prep.setDouble(2, payItem.getPaidAmount());
		if( payItem.getPaymentMode().equals(PaymentMode.Card)){
			prep.setString(3, payItem.getCardName());
			prep.setString(4, payItem.getCardType());
			prep.setString(5, payItem.getCardNo());
			prep.setString(6, payItem.getNameOnCard());
			prep.setInt(7, payItem.getCardExpiryMonth());
			prep.setInt(8, payItem.getCardExpiryYear());
			prep.setString(9, payItem.getCardApprovalCode());
			prep.setString(10, payItem.getAccount());
		}else{
			
 
			prep.setNull( 3, java.sql.Types.NULL);
			prep.setNull(4, java.sql.Types.NULL);
			prep.setNull(5, java.sql.Types.NULL); 
			prep.setNull(6,  java.sql.Types.NULL);
			prep.setNull(7, java.sql.Types.INTEGER);
			prep.setNull(8,java.sql.Types.INTEGER);
			prep.setNull(9,  java.sql.Types.NULL);
			prep.setNull(10,  java.sql.Types.NULL);
		}
		prep.setInt(11, payItem.getCompanyId());
		prep.setInt(12, payItem.getVoucherId());
		prep.setDouble(13, payItem.getVoucherValue());
		prep.setInt(14, payItem.getVoucherCount());
		prep.setInt(15, payItem.getCashierID());
		prep.setString(16, payItem.getPaymentDate());
		prep.setString(17, payItem.getPaymentTime());
		BeanDiscount disc=payItem.getDiscount();
		if(disc!=null){
			prep.setInt(18, disc.getId());
			prep.setString(19, disc.getCode());
			prep.setString(20, disc.getName());
			prep.setString(21, disc.getDescription());
			prep.setDouble(22, disc.getPrice());
			prep.setBoolean(23, disc.isPercentage());
			prep.setBoolean(24, disc.isOverridable());
			prep.setDouble(25, payItem.getPaidAmount());
		}else{
			prep.setObject(18, null);
			prep.setObject(19, null);
			prep.setObject(20, null);
			prep.setObject(21, null);
			prep.setObject(22, null);
			prep.setObject(23, null);
			prep.setObject(24, null);
			prep.setObject(25, null);
	
		}
		prep.setBoolean(26, payItem.isRepayment());
		prep.setBoolean(27, payItem.isVoucherBalanceReturned());
		prep.setDouble(28, payItem.getPartialBalance());
		prep.setInt(29,(payItem.isNew()? 
				PosEnvSettings.getInstance().getCashierShiftInfo().getShiftItem().getId():
					payItem.getShiftId()));
//		prep.setString(30,  payItem.getOrderPaymentHdrId());
//		prep.setString(31, payItem.getOrderId());
//		prep.setString(32, payItem.getId()); //Id generated from order id
		
		if (isExist){
			prep.setInt(30, PosEnvSettings.getInstance().getCashierShiftInfo().getCashierInfo().getId());
			prep.setString(31, PosDateUtil.getDateTime());
			
			prep.setString(32,  payItem.getOrderPaymentHdrId());
			prep.setString(33, payItem.getOrderId());
			prep.setString(34, payItem.getId()); //Id generated from order id
			
		}else{
			
			prep.setInt(30, PosEnvSettings.getInstance().getCashierShiftInfo().getCashierInfo().getId());
			prep.setString(31, PosDateUtil.getDateTime());
			prep.setInt(32, PosEnvSettings.getInstance().getCashierShiftInfo().getCashierInfo().getId());
			prep.setString(33, PosDateUtil.getDateTime());
			
			prep.setString(34,  payItem.getOrderPaymentHdrId());
			prep.setString(35, payItem.getOrderId());
			prep.setString(36, payItem.getId()); //Id generated from order id
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

	public Map<PaymentMode, Double> getShiftPayments(BeanCashierShift shopShift, BeanUser mCashierInfo, String posDate,  
			String dateFrom, String timeFrom, String dateTo, String timeTo, Boolean isRepayment,Boolean isAdvance){
		  
		Map<PaymentMode, Double> shiftPayments=new HashMap<PaymentMode, Double>();
		String where=" where  ";
		//NOT (payment_mode=" + PaymentMode.CouponBalance.getValue()  + " AND NOT is_voucher_balance_returned)  ";
		
		if(mCashierInfo!=null){

			where+="  cashier_id="+mCashierInfo.getId()
					+" and payment_date='" + posDate + "'  and "+(isRepayment?"is_repayment":"not is_repayment");
		}else{

			where+="   payment_date='" + posDate + "' and  "+(isRepayment?"is_repayment":"not is_repayment");
		}
		where+= isAdvance? " and is_advance":" and not is_advance";

		if(shopShift!=null)
			where+=" and cashier_shift_id="+shopShift.getShiftItem().getId();
			
		final String join=" INNER JOIN order_hdrs ON v_order_payments.order_id = order_hdrs.order_id";
		
		final String sql="select payment_mode, sum(paid_amount) as paid_amount from v_order_payments " + join + where + " group by payment_mode";
		CachedRowSet crs=executeQuery(sql);
		if(crs!=null){
			try {
				while(crs.next())
					shiftPayments.put(PaymentMode.get(crs.getInt("payment_mode")), crs.getDouble("paid_amount"));
				crs.close();
			} catch (SQLException e) {
				PosLog.write(this, "getShiftPayments", e);
				try {
					crs.close();
				} catch (SQLException e1) {
					PosLog.write(this, "getShiftPayments", e1);
				}
			}
		}
		
		
 
		return shiftPayments;
	}
	public Map<PaymentMode, Double> getShiftPayments(BeanCashierShift shopShift, BeanUser mCashierInfo, String posDate,  
			String dateFrom, String timeFrom, String dateTo, String timeTo, Boolean isRepayment){
		  
		Map<PaymentMode, Double> shiftPayments=new HashMap<PaymentMode, Double>();
		String where=" WHERE ";
		//NOT (payment_mode=" + PaymentMode.CouponBalance.getValue()  + " AND NOT is_voucher_balance_returned)  ";
		if(mCashierInfo!=null){

			where+="  cashier_id="+mCashierInfo.getId()
					+" and payment_date='" + posDate + "'  and "+(isRepayment?"is_repayment":"not is_repayment");
		}else{

			where+="  payment_date='" + posDate + "' and  "+(isRepayment?"is_repayment":"not is_repayment");
		}

		if(shopShift!=null)
			where+=" and cashier_shift_id="+shopShift.getShiftItem().getId();
			
		final String join=" INNER JOIN order_hdrs ON v_order_payments.order_id = order_hdrs.order_id";
		
		final String sql="select payment_mode, sum(paid_amount) as paid_amount from v_order_payments" + join+ " " + where + " group by payment_mode";
		CachedRowSet crs=executeQuery(sql);
		if(crs!=null){
			try {
				while(crs.next())
					shiftPayments.put(PaymentMode.get(crs.getInt("payment_mode")), crs.getDouble("paid_amount"));
				crs.close();
			} catch (SQLException e) {
				PosLog.write(this, "getShiftPayments", e);
				try {
					crs.close();
				} catch (SQLException e1) {
					PosLog.write(this, "getShiftPayments", e1);
				}
			}
		}
		
		
 
		return shiftPayments;
	}
	
	
	public double getTotalVoucherBalanceNonReturned(BeanCashierShift shopShift, BeanUser mCashierInfo, String posDate,  
			String dateFrom, String timeFrom, String dateTo, String timeTo ){
		  
		double amount=0;
		String where="WHERE ";//  payment_mode=" + PaymentMode.CouponBalance.getValue()  + " AND NOT is_voucher_balance_returned   ";
		if(mCashierInfo!=null){

			where+="  cashier_id="+mCashierInfo.getId()
					+" and payment_date='" + posDate + "' not is_repayment";
		}else{

			where+="  payment_date='" + posDate + "' and  not is_repayment" ;
		}

		if(shopShift!=null)
			where+=" and cashier_shift_id="+shopShift.getShiftItem().getId();
			
		final String join=" INNER JOIN order_hdrs ON v_order_payments.order_id = order_hdrs.order_id";
		
		final String sql="select sum(paid_amount) as paid_amount from v_order_payments" + join + where  ;
		CachedRowSet crs=executeQuery(sql);
		if(crs!=null){
			try {
				if(crs.next())
					amount= crs.getDouble("paid_amount");
				crs.close();
			} catch (SQLException e) {
				PosLog.write(this, "getShiftPayments", e);
				try {
					crs.close();
				} catch (SQLException e1) {
					PosLog.write(this, "getShiftPayments", e1);
				}
			}
		}
		
		return amount;
	}
	
/*
 * 
 */
	public double getNetSale(BeanCashierShift shopShift, BeanUser mCashierInfo, String posDate){
		  

		String where="";
		
		where=" pay.payment_date='" + posDate + "' ";
		where+=(mCashierInfo!=null?" and  pay.cashier_id="+mCashierInfo.getId():"");
		where+=(shopShift!=null?" and  pay.cashier_shift_id="+shopShift.getShiftItem().getId():"");

		return getNetSale(where);
 	}
	
	/*
	 * 
	 */
	public double getNetSale( String criteria ){
			  
 
			double netSale=0;
			String where=" where   not pay.is_repayment " ;
			where+= (criteria.trim().equals("")?"":" and " + criteria);
			
//			//take sum of all payment transactions and sum of advance payments which closed in this same date time range
//			final String advanceCriteria="(pay.is_advance AND hdr.status IN(" + 
//										PosOrderStatus.Closed.getCode() + "," + 
//										PosOrderStatus.Refunded.getCode()  + "))";
//			where+=  " and (not pay.is_advance OR " + advanceCriteria + ")";

			final String sql="select  sum(CASE WHEN pay.payment_mode=" + PaymentMode.Balance.getValue() + 
				 " OR  pay.payment_mode=" + PaymentMode.CashOut.getValue() + 
				  " OR  pay.payment_mode=" + PaymentMode.CouponBalance.getValue() + " THEN -1 ELSE 1 END * " + 
				 " pay.paid_amount) as paid_amount from v_order_payments pay" + 
					" INNER JOIN order_hdrs hdr ON pay.order_id = hdr.order_id " + where ;
			CachedRowSet crs=executeQuery(sql);
			if(crs!=null){
				try {
					if(crs.next())
						netSale=crs.getDouble("paid_amount");
					
					crs.close();
				} catch (SQLException e) {
					PosLog.write(this, "getNetSale", e);
					try {
						crs.close();
					} catch (SQLException e1) {
						PosLog.write(this, "getNetSale", e1);
					}
				}
				
			}
			
			return netSale;
		}
		
		/*
		 * 
		 */
	public double getTotalVoucherBalanceReturned(BeanCashierShift shopShift,BeanUser mCashierInfo,  String posDate,  String startDate, String startTime, String endDate, String endTime){
		
		double TotalVoucherBalanceReturned=0;
		String where="";
		if(mCashierInfo!=null){
//			where=" where cashier_id="+mCashierInfo.getId()+" and (payment_date || ' ' || payment_time  ) between '"+ startDate + " " + startTime + 
//					"' and '" + endDate+ " " +endTime +"' and is_voucher_balance_returned";
			where=" where cashier_id="+mCashierInfo.getId()
					+" and payment_date='" + posDate + "' and payment_time between concat(date('"+ startDate + "'),' ',time('" + startTime + "'))"+ 
					" and concat(date('" + endDate+ "'),' ',time('" +endTime +"')) and is_voucher_balance_returned";
			
		}else{
//			where=" where (payment_date || ' ' || payment_time  ) between '"+ startDate + " " + startTime + 
//					"' and '" + endDate+ " " +endTime +"' and is_voucher_balance_returned";
			where=" where payment_date='" + posDate + "' and payment_time between concat(date('"+ startDate + "'),' ',time('" + startTime +"'))"+ 
					" and concat(date('" + endDate+ "'),' ', time('" +endTime +"')) and is_voucher_balance_returned";
		}
		
		if(shopShift!=null)
			where+=" and cashier_shift_id="+shopShift.getShiftItem().getId();
			
		final String join=" INNER JOIN order_hdrs ON order_payments.order_id = order_hdrs.order_id";

		final String sql="select sum(paid_amount) as paid_amount from order_payments" + join + where;
		CachedRowSet crs=executeQuery(sql);
		
		if(crs!=null){
			try {
				while(crs.next())
					TotalVoucherBalanceReturned = crs.getDouble("paid_amount");
				crs.close();
			} catch (SQLException e) {
				PosLog.write(this, "getTotalVoucherBalanceReturned", e);
				try {
					crs.close();
				} catch (SQLException e1) {
					PosLog.write(this, "getTotalVoucherBalanceReturned", e1);
				}
			}
		}
		return TotalVoucherBalanceReturned;
	}
	
	public double getRefund(BeanCashierShift shopShift,BeanUser mCashierInfo,  String posDate,  String startDate, String startTime, String endDate, String endTime){
		
		double TotalRefund=0;
		String where="";
		if(mCashierInfo!=null){
//			where=" where cashier_id="+mCashierInfo.getId()+" and (payment_date || ' ' || payment_time  ) between '"+ startDate + " " + startTime + 
//					"' and '" + endDate+ " " +endTime +"' and is_repayment";
			where=" where cashier_id="+mCashierInfo.getId()
					+" and payment_date='" + posDate + "' and payment_time between concat(date('"+ startDate + "'), ' ', time('" + startTime +"'))"+ 
					" and concat(date('" + endDate+ "'),' ',time('" +endTime +"')) and is_repayment";
		}else{
//			where=" where (payment_date || ' ' || payment_time  ) between '"+ startDate + " " + startTime + 
//					"' and '" + endDate+ " " +endTime +"' and is_repayment";
			where=" where payment_date='" + posDate + "' and payment_time between concat(date('"+ startDate + "'),' ', time('" + startTime +"'))"+ 
					" and concat(date('" + endDate+ "'),' ',time('" +endTime +"')) and is_repayment";
		}
		
		if(shopShift!=null)
			where+=" and cashier_shift_id="+shopShift.getShiftItem().getId();
			
		final String join=" INNER JOIN order_hdrs ON order_payments.order_id = order_hdrs.order_id";

		final String sql="select sum(paid_amount) as paid_amount from order_payments" + join + where;
		
		CachedRowSet crs=executeQuery(sql);
		if(crs!=null){
			try {
				while(crs.next())
					TotalRefund = crs.getDouble("paid_amount");
				crs.close();
			} catch (SQLException e) {
				PosLog.write(this, "getRefund", e);
				try {
					crs.close();
				} catch (SQLException e1) {
					PosLog.write(this, "getRefund", e1);
				}
			}
		}
		return TotalRefund;
	}
	
	/**
	 * @param order
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<BeanOrderPayment> getOrderPayments(BeanOrderHeader order) throws SQLException{

		return getOrderPayments(order.getOrderId());
	}
	
	/**
	 * @param orderID
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<BeanOrderPayment> getOrderPayments(String orderID) throws SQLException{
		ArrayList<BeanOrderPayment> orderPaymentlList=null;
		final String where="order_id='"+orderID+"'";
		CachedRowSet crs=getData(where);
		if(crs!=null){
			orderPaymentlList=new ArrayList<BeanOrderPayment>();
			while(crs.next()){
				BeanOrderPayment payment=creatPaymentFromRecordset(crs);
				orderPaymentlList.add(payment);
			}
			crs.close();
		}
		return orderPaymentlList;
	}
	/**
	 * @param where
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<BeanOrderPayment> getOrderPaymentsByCond(String where) throws SQLException{
		ArrayList<BeanOrderPayment> orderPaymentlList=null;
//		final String where="order_id='"+order.getOrderId()+"'";
		final String Sql="SELECT opay.* " +
				" FROM v_order_payments opay  JOIN v_order_hdrs  ohdr ON  opay.order_id=ohdr.order_id " + 
				" where "+where ;
		
		CachedRowSet crs=executeQuery(Sql);
		if(crs!=null){
			orderPaymentlList=new ArrayList<BeanOrderPayment>();
			while(crs.next()){
				BeanOrderPayment payment=creatPaymentFromRecordset(crs);
				orderPaymentlList.add(payment);
			}
			crs.close();
		}
		return orderPaymentlList;
	}
	/**
	 * @param where
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<BeanOrderPaymentReport> getOrderPaymentsForShiftReport(String where) throws SQLException{
		ArrayList<BeanOrderPaymentReport> orderPaymentlList=null;
//		final String where="order_id='"+order.getOrderId()+"'";
		where +=" and  NOT (payment_mode=" + PaymentMode.CouponBalance.getValue()  + " AND NOT is_voucher_balance_returned)  ";
		
		final String Sql="SELECT ohdr.order_id as order_id,opay.id as id,opay.order_payment_hdr_id as order_payment_hdr_id," +
				" ohdr.invoice_no as invoice_no,opay.payment_mode as payment_mode,opay.paid_amount as paid_amount," +
				" opay.is_repayment as is_repayment,opay.is_advance as is_advance,ohdr.queue_no as queue_no,ohdr.status as status " +
				" FROM v_order_payments opay  JOIN v_order_hdrs  ohdr ON  opay.order_id=ohdr.order_id " + 
				" where "+where + " AND opay.paid_amount<>0 order by ohdr.invoice_no";
		
		CachedRowSet crs=executeQuery(Sql);
		if(crs!=null){
			orderPaymentlList=new ArrayList<BeanOrderPaymentReport>();
			while(crs.next()){
				BeanOrderPaymentReport payment=creatPaymentReportFromRecordset(crs);
				
				orderPaymentlList.add(payment);
			}
			crs.close();
		}
		return orderPaymentlList;
	}
	
	
	/**
	 * @param where
	 * @return
	 * @throws SQLException
	 */
	public int getOrderCount(String where) throws Exception{
		
		int orderCount=0;
		String sql="SELECT count(ohdr.invoice_no) as order_count " +
				" FROM v_order_payment_hdr opay  JOIN v_order_hdrs  ohdr ON  opay.order_id=ohdr.order_id " + 
				" where "+where + "  and  not opay.is_advance and not opay.is_refund order by ohdr.invoice_no";
		sql=sql.replace("shift_id", "opay.shift_id");
		CachedRowSet crs=executeQuery(sql);
		if(crs!=null){
			try {
				if(crs.next()){

					orderCount=crs.getInt("order_count");
				}
					
			} catch (Exception e) {
				PosLog.write(this, "getOrderCount", e);
				throw new Exception("Failed to get order count details.");
			}finally{
				try {
					crs.close();
				} catch (SQLException e) {
					PosLog.write(this, "getOrderCount", e);
					throw new Exception("Failed to get order count details.");
				}
			}
		}
		return orderCount;
	}
	
	public ArrayList<BeanOrderPayment> getPaymentSummaryForShiftReports(String where) throws SQLException{
		ArrayList<BeanOrderPayment> orderPaymentlList=null;
		
		where +=" and  NOT (payment_mode=" + PaymentMode.CouponBalance.getValue()  + " AND NOT is_voucher_balance_returned)  ";
		
		final String Sql="SELECT  payment_mode, sum(paid_amount) as paid_amount " +
						" FROM v_order_payments opay  JOIN order_hdrs  ohdr ON  opay.order_id=ohdr.order_id " + 
						" where not is_advance AND " + where + " and not is_repayment group by payment_mode ";
		CachedRowSet crs=executeQuery(Sql);
		if(crs!=null){
			orderPaymentlList=new ArrayList<BeanOrderPayment>();
			while(crs.next()){
				BeanOrderPayment payment=new BeanOrderPayment();
				payment.setPaymentMode(PaymentMode.get(crs.getInt("payment_mode")));
				payment.setPaidAmount(crs.getDouble("paid_amount"));
				orderPaymentlList.add(payment);
			}
			crs.close();
		}
		return orderPaymentlList;
	}
	
	/*
	 * return advance collected for closed sales order 
	 */
	public double getAdvanceAmtOfClosedSaledsOrder(BeanCashierShift shopShift , String posDate,  
			String dateFrom ) throws Exception{
		String where="";
		 
			where="  payment_date='" + posDate + "'  " ;
		 

		if(shopShift!=null)
			where+=" and cashier_shift_id="+shopShift.getShiftItem().getId();
		  return getAdvanceAmtOfClosedSaledsOrder(where);
	}
	
	/*
	 * return advance collected for closed sales order 
	 */
	public double getAdvanceAmtOfClosedSaledsOrder(String where) throws Exception{

		double advanceAmt=0;
		final String sql="SELECT sum(paid_amount) as advanceAmount FROM v_order_payments " + 
				" where is_advance  AND" + 
				" order_id in (SELECT ohdr.order_id " +
				" FROM v_order_payment_hdr opay  JOIN order_hdrs  ohdr ON  opay.order_id=ohdr.order_id " + 
				" where not is_advance AND " + where + " and not is_refund AND status in " + 
				"("+PosOrderStatus.Closed.getCode()+","+PosOrderStatus.Refunded.getCode()+")"+" ) ";

 
		
		CachedRowSet crs=executeQuery(sql);
		if(crs!=null){
			try {
				if(crs.next())
					advanceAmt=crs.getDouble("advanceAmount");
			} catch (Exception e) {
				PosLog.write(this, "getAdvanceOfClosedSaledsOrder", e);
				throw new Exception("Failed to get Advance Amount details.");
			}finally{
				try {
					crs.close();
				} catch (SQLException e) {
					PosLog.write(this, "getTotalRepayment", e);
					throw new Exception("Failed to get Advance Amount details.");
				}
			}
		}
		return advanceAmt;
	}
  
	private BeanOrderPayment creatPaymentFromRecordset(CachedRowSet crs) throws SQLException {
		BeanOrderPayment payment=new BeanOrderPayment();
//		PosCouponItemProvider mCouponItemProvider = new PosCouponItemProvider();
		payment.setId(crs.getString("id"));
		payment.setOrderId(crs.getString("order_id"));
		payment.setOrderPaymentHdrId(crs.getString("order_payment_hdr_id"));
		payment.setInvoiceNo(PosOrderUtil.getFormatedInvoiceNumber( crs.getInt("invoice_no")) );	
		payment.setPaymentMode(PaymentMode.get(crs.getInt("payment_mode")));
		payment.setPaidAmount(crs.getDouble("paid_amount"));
		payment.setCardName(crs.getString("card_name"));
		payment.setCardType(crs.getString("card_type"));
		payment.setCardNo(crs.getString("card_no"));
		payment.setNameOnCard(crs.getString("name_on_card"));
		payment.setCardExpiryMonth(crs.getInt("card_expiry_month"));
		payment.setCardExpiryYear(crs.getInt("card_expiry_year"));
		payment.setCardApprovalCode(crs.getString("card_approval_code"));
		payment.setAccount(crs.getString("card_account_type"));
		payment.setCompanyId(crs.getInt("company_id"));
		PosCompanyItemProvider companyProvider = new PosCompanyItemProvider();
		payment.setCompanyName(companyProvider.getCompanyName(crs.getInt("company_id")));
		payment.setVoucherId(crs.getInt("voucher_id"));
		payment.setVoucherCount(crs.getInt("voucher_count"));
		payment.setVoucherValue(crs.getDouble("voucher_value"));
		payment.setCashierID(crs.getInt("cashier_id"));
		payment.setPaymentDate(crs.getString("payment_date"));
		payment.setPaymentTime(crs.getString("payment_time"));
		payment.setRepayment(crs.getBoolean("is_repayment"));
		payment.setVoucherBalanceReturned(crs.getBoolean("is_voucher_balance_returned"));
		payment.setPartialBalance(crs.getDouble("partial_balance"));
		payment.setShiftId(crs.getInt("cashier_shift_id"));
		payment.setAdvance(crs.getBoolean("is_advance"));
 
//		payment.setCreatedAt(crs.getString("created_at"));
//		payment.setCreatedBy(crs.getInt("created_by"));
//		payment.setUpdatedAt(crs.getString("updated_at"));
//		payment.setUpdatedBy(crs.getInt("updated_by"));
//		
		return payment;
	}
	
	/*
	 * 
	 */
	private BeanOrderPaymentReport creatPaymentReportFromRecordset(CachedRowSet crs) throws SQLException {
		
		BeanOrderPaymentReport paymentReport=new BeanOrderPaymentReport();
//		PosCouponItemProvider mCouponItemProvider = new PosCouponItemProvider();
		
		paymentReport.setId(crs.getString("id"));
		paymentReport.setOrderId(crs.getString("order_id"));
		paymentReport.setOrderPaymentHdrId(crs.getString("order_payment_hdr_id"));
		paymentReport.setInvoiceNo(PosOrderUtil.getFormatedInvoiceNumber( crs.getInt("invoice_no")) );	
		paymentReport.setPaymentMode(PaymentMode.get(crs.getInt("payment_mode")));
		paymentReport.setPaidAmount(crs.getDouble("paid_amount"));
		paymentReport.setRepayment(crs.getBoolean("is_repayment"));
		paymentReport.setAdvance(crs.getBoolean("is_advance"));
		paymentReport.setQueueNo(crs.getString("queue_no"));
		paymentReport.setStatus(PosOrderStatus.get(crs.getInt("status")));
 
		return paymentReport;
	}
	
	public BeanPaymentSummary getRefundSummary(String orderID) throws Exception {
		
		BeanPaymentSummary summary=null;
		String sql="select sum(paid_amount) as paid_amount, payment_mode  from v_order_payments where order_id='"+orderID+"' and is_repayment=1 group by (payment_mode)";
		CachedRowSet crs=executeQuery(sql);
		if(crs!=null){
			try {
				summary=createPaymentSumaryFromRecordset(crs);
			} catch (Exception e) {
				PosLog.write(this, "getPaymentSummary", e);
				throw new Exception("Failed to get order payment details.");
			}finally{
				try {
					crs.close();
				} catch (SQLException e) {
					PosLog.write(this, "getPaymentSummary", e);
					throw new Exception("Failed to get order payment details.");
				}
			}
		}
		summary.setTotalRepayment(getTotalRepayment(orderID));
		return summary;
	}

	public BeanPaymentSummary getPaymentSummary(String orderID) throws Exception {
		BeanPaymentSummary summary=null;
		String sql="select sum(paid_amount) as paid_amount, payment_mode  from v_order_payments where order_id='"+orderID+"' and is_repayment=0 group by (payment_mode)";
		CachedRowSet crs=executeQuery(sql);
		if(crs!=null){
			try {
				summary=createPaymentSumaryFromRecordset(crs);
			} catch (Exception e) {
				PosLog.write(this, "getPaymentSummary", e);
				throw new Exception("Failed to get order payment details.");
			}finally{
				try {
					crs.close();
				} catch (SQLException e) {
					PosLog.write(this, "getPaymentSummary", e);
					throw new Exception("Failed to get order payment details.");
				}
			}
		}
		summary.setTotalRepayment(getTotalRepayment(orderID));
		return summary;
	}
	
	private BeanPaymentSummary createPaymentSumaryFromRecordset(CachedRowSet crs) throws Exception{
		BeanPaymentSummary summary=new BeanPaymentSummary();
		while(crs.next()){
			PaymentMode mode=PaymentMode.get(crs.getInt("payment_mode"));
			double amount=crs.getDouble("paid_amount");
			switch (mode) {
			case Card:
				summary.setCardTotal(amount);
				break;
			case Cash:
				summary.setCashTotal(amount);
				break;
			case Company:
				summary.setCompanyTotal(amount);
				break;
			case Coupon:
				summary.setVoucherTotal(amount);
				break;
			case Discount:
				summary.setBillDiscount(amount);
				break;
			case Balance:
				summary.setTotalBalance(amount);
				break;
//			case CouponBalance:
//				summary.setTotalBalance(amount);
//				break;
			default:
				break;
			}
		}
		return summary;
	}
	
	public double getTotalRepayment(String orderID) throws Exception{
		double repayment=0;
		String sql="select sum(paid_amount) as paid_amount from v_order_payments where order_id='"+orderID+"' and is_repayment=1";
		CachedRowSet crs=executeQuery(sql);
		if(crs!=null){
			try {
				if(crs.next())
					repayment=crs.getDouble("paid_amount");
			} catch (Exception e) {
				PosLog.write(this, "getTotalRepayment", e);
				throw new Exception("Failed to get order payment details.");
			}finally{
				try {
					crs.close();
				} catch (SQLException e) {
					PosLog.write(this, "getTotalRepayment", e);
					throw new Exception("Failed to get order payment details.");
				}
			}
		}
		return repayment;
	}
	
	/*
	 *  total refund  For Shift Report 
	 */
	public double getGrandTotalRepayment(String were) throws Exception{
		double repayment=0;
		String sql="select sum(paid_amount) as paid_amount " +
						" FROM order_payments opay  JOIN order_hdrs  ohdr ON  opay.order_id=ohdr.order_id " + 
						" where "+were+" and is_repayment";
		CachedRowSet crs=executeQuery(sql);
		if(crs!=null){
			try {
				if(crs.next())
					repayment=crs.getDouble("paid_amount");
			} catch (Exception e) {
				PosLog.write(this, "getTotalRepayment", e);
				throw new Exception("Failed to get order payment details.");
			}finally{
				try {
					crs.close();
				} catch (SQLException e) {
					PosLog.write(this, "getTotalRepayment", e);
					throw new Exception("Failed to get order payment details.");
				}
			}
		}
		return repayment;
	}

	/*
	 *  total Advance Paid  For Shift Report 
	 */
	public double getTotalAdvanceReceived(String were,PaymentMode mode) throws Exception{
		double repayment=0;
		
		String sql="select sum(paid_amount) as advance_amount " +
				" FROM v_order_payments opay  JOIN order_hdrs  ohdr ON  opay.order_id=ohdr.order_id " + 
				" where  is_advance and  "+were +"  AND payment_mode=" + mode.getValue() ;
		
		CachedRowSet crs=executeQuery(sql);
		if(crs!=null){
			try {
				if(crs.next())
					repayment=crs.getDouble("advance_amount");
			} catch (Exception e) {
				PosLog.write(this, "getTotalAdvanceReceived", e);
				throw new Exception("Failed to get order payment details.");
			}finally{
				try {
					crs.close();
				} catch (SQLException e) {
					PosLog.write(this, "getTotalAdvanceReceived", e);
					throw new Exception("Failed to get order payment details.");
				}
			}
		}
		return repayment;
	}
	
	/* (non-Javadoc)
	 * @see com.indocosmo.pos.data.providers.PosDatabaseProvider#deleteData(java.lang.String)
	 */
	@Override
	public int deleteData(String where) throws SQLException {
		// TODO Auto-generated method stub
		return super.deleteData("order_payments",where);
	}

	/*
	 * 
	 */
	public void updatePmsSyncStatus(String paymentId,PmsSyncStatus syncStatus) {
		
		String sql="update order_payments set pms_sync_status=" + syncStatus.getValue()  + 
		    " where  id='" + paymentId + "'" ;
		try {
			 executeNonQuery(sql);
		} catch (Exception e) {
			PosLog.write(this, "updatePmsSyncStatus", e); 
		}
	}
	/*
	 * 
	 */
	public ArrayList<BeanPMSOrderPayment> getPendingPaymentPostToPMS() throws Exception{
		
		ArrayList<BeanPMSOrderPayment> paymentList=null;
		BeanPMSOrderPayment payment;
		String sql="select opay.id  as payment_id, opay.order_id as order_id," + 
				" opay.paid_amount as paid_amount,opay.is_repayment as is_repayment," +
				" ocust.name as cust_name,ocust.code as cust_code " + 
				" FROM order_payments opay  JOIN order_hdrs  ohdr ON  opay.order_id=ohdr.order_id " +
				" JOIN order_customers ocust ON ocust.order_id=ohdr.order_id " +
				" JOIN customer_types ctype on ocust.customer_type=ctype.id " + 
				" where ctype.code='" + PosCustomerTypeProvider.ROOM_TYPE_CODE +
				"' AND payment_mode=" + PaymentMode.Company.getValue() + 
				" AND opay.pms_sync_status<>" + PmsSyncStatus.Success.getValue() ;
		
		CachedRowSet crs=executeQuery(sql);
		if(crs!=null){
			try {
				paymentList=new ArrayList<BeanPMSOrderPayment>();
				while(crs.next()) {
					
					payment=new BeanPMSOrderPayment();
					
					payment.setPaymentId(crs.getString("payment_id"));
					payment.setOrderId(crs.getString("order_id"));
					payment.setPaidAmount(crs.getDouble("paid_amount"));
					payment.setRepayment(crs.getBoolean("is_repayment"));
					payment.setCustomerName(crs.getString("cust_name"));
					payment.setCustomerCode(crs.getString("cust_code"));
					paymentList.add(payment);
				}
			} catch (Exception e) {
				PosLog.write(this, "getPendingPaymentPostToPMS", e);
				throw new Exception("Failed to get order payment details.");
			}finally{
				try {
					crs.close();
				} catch (SQLException e) {
					PosLog.write(this, "getPendingPaymentPostToPMS", e);
					throw new Exception("Failed to get order payment details.");
				}
			}
		}
		return paymentList;
	}
	
 public enum PmsSyncStatus{
	 	NotStarted(0),
		Success(1),
		SyncInProcess(2),
		Failed(3);
		 
		
		private static final Map<Integer,PmsSyncStatus> mLookup 
		= new HashMap<Integer,PmsSyncStatus>();

		static {
			for(PmsSyncStatus rc : EnumSet.allOf(PmsSyncStatus.class))
				mLookup.put(rc.getValue(), rc);
		}

		private int mValue;
		
		private PmsSyncStatus(int value) {
			this.mValue = value;
		}

		public int getValue() { return mValue; }
		
		public static PmsSyncStatus get(int value) { 
			return mLookup.get(value); 
		}
 }
}
