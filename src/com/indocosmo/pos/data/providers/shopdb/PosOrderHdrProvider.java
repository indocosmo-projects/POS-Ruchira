/**
 * 
 */
package com.indocosmo.pos.data.providers.shopdb;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

import javax.sql.rowset.CachedRowSet;

import com.indocosmo.pos.common.PosEnvSettings;
import com.indocosmo.pos.common.PosEnvSettings.ApplicationType;
import com.indocosmo.pos.common.PosLog;
import com.indocosmo.pos.common.enums.PosOrderMedium;
import com.indocosmo.pos.common.enums.PosOrderServiceTypes;
import com.indocosmo.pos.common.utilities.PosDateUtil;
import com.indocosmo.pos.common.utilities.PosOrderSplitUtil;
import com.indocosmo.pos.common.utilities.PosOrderUtil;
import com.indocosmo.pos.data.beans.BeanOrderDetail;
import com.indocosmo.pos.data.beans.BeanOrderDiscount;
import com.indocosmo.pos.data.beans.BeanOrderHeader;
import com.indocosmo.pos.data.beans.BeanOrderHeader.PaymentProcessStatus;
import com.indocosmo.pos.data.beans.BeanOrderHeader.PosOrderStatus;
import com.indocosmo.pos.data.beans.BeanOrderCustomer;
import com.indocosmo.pos.data.beans.BeanOrderPayment;
import com.indocosmo.pos.data.beans.BeanOrderPaymentHeader;
import com.indocosmo.pos.data.beans.BeanOrderServingTable;
import com.indocosmo.pos.data.beans.BeanOrderSplit;

/**
 * @author jojesh
 *
 */
public class PosOrderHdrProvider extends PosShopDBProviderBase {

	private PosOrderDtlProvider mOrderDtlProvider;
	private PosOrderPaymentsProvider mOrderPaymentsProvider;
	private PosOrderPaymentHdrProvider mOrderPaymentHdrProvider;
	private PosOrderDiscountProvider mOrderDiscountProvider;
	private PosOrderPreDiscountProvider mOrderPreDiscountProvider;
	private PosCounterProvider mCounterProvider;
//	private PosServiceTableProvider mServiceTableprovider;
	private PosWaiterProvider mWaiterProvider;
	private PosUsersProvider mUserProvider;
	private PosOrderSplitProvider mOrderSplitProvider;
	private PosOrderServingTableProvider mServingTableProvider;
	private PosOrderServingSeatProvider mServingSeatProvider;
	private PosInvoiceProvider mInvoiceProvider;
	private PosOrderQueueProvider mOrderQueueProvider;
	private PosOrderCustomerProvider mOrderCustomerProvider;
	private PosOrderRefundProvider mOrderRefundProvider;
	
	private PreparedStatement mHeaderItemPs;

	/**
	 * 
	 */
	public PosOrderHdrProvider() {
		super("order_id","v_order_hdrs");

		mOrderDtlProvider=new PosOrderDtlProvider();
		mOrderPaymentsProvider=new PosOrderPaymentsProvider();
		mOrderPaymentHdrProvider=new PosOrderPaymentHdrProvider();
		mOrderDiscountProvider = new PosOrderDiscountProvider();
		mOrderPreDiscountProvider = new PosOrderPreDiscountProvider();
		mOrderSplitProvider=new PosOrderSplitProvider();
		mCounterProvider= new PosCounterProvider();
		mWaiterProvider=new PosWaiterProvider();
		mUserProvider=new PosUsersProvider();
		mServingTableProvider=new PosOrderServingTableProvider();
		mServingSeatProvider=new PosOrderServingSeatProvider();
		mInvoiceProvider=new PosInvoiceProvider();
		mOrderQueueProvider=new PosOrderQueueProvider();
		mOrderCustomerProvider=new PosOrderCustomerProvider();
		mOrderRefundProvider=new PosOrderRefundProvider();
		
	}

	/**
	 * @param mOrderHeaderActual
	 * @param b
	 * @param c
	 * @param d
	 * @param e
	 * @return
	 * @throws Exception 
	 */
	public boolean saveOrder(BeanOrderHeader orderHdrItem) throws Exception  {

		return saveOrder(orderHdrItem, true, true, true, true);
	}

	/**
	 * @param orderHdrItem
	 * @param isHeader
	 * @param isDetails
	 * @param isPayments
	 * @param isSplits
	 * @param isOrderTables
	 * @return
	 * @throws Exception
	 */
	public boolean saveOrder(BeanOrderHeader orderHdrItem, boolean isHeader, boolean isDetails, boolean isPayments, boolean isSplits) throws Exception  {
		
		PosLog.debug("Starting to save order ...... ");
		
		boolean status=false;

		try {

			mConnection.setAutoCommit(false);

			/**
			 * Order header saving
			 */
			if(isHeader)
				this.save(orderHdrItem);

//			if (orderHdrItem.getStatus()==PosOrderStatus.Void)
//				mOrderDtlProvider.voidOrderDetails(orderHdrItem.getOrderId());
			
			/**
			 * Order details saving
			 */
			if(isDetails){
				
//				if(orderHdrItem.getOrderServiceType()==PosOrderServiceTypes.SALES_ORDER)
				mOrderCustomerProvider.save(orderHdrItem);
				
				mOrderDtlProvider.save(orderHdrItem);
				mServingTableProvider.save(orderHdrItem);
			}

			/**
			 * Order Payments saving
			 */
			if(isPayments && orderHdrItem.getOrderPaymentHeaders()!=null){
				String paymentHdrId="";
				mOrderPaymentHdrProvider.save(orderHdrItem);
			
				for (BeanOrderPaymentHeader ordPayHdr: orderHdrItem.getOrderPaymentHeaders()){
					
					if (ordPayHdr.isNew()) 
						paymentHdrId=ordPayHdr.getId();
					 
				}
				
				for (BeanOrderPayment ordPay: orderHdrItem.getOrderPaymentItems()){

					if (ordPay.isNew()) 
						ordPay.setOrderPaymentHdrId(paymentHdrId);
					 
				}
				
				if(orderHdrItem.getBillDiscounts()!=null && orderHdrItem.getBillDiscounts().size()>0)
					for (BeanOrderDiscount ordDisc: orderHdrItem.getBillDiscounts()){
						
						if (ordDisc.isNew()) 
							ordDisc.setOrderPaymentHdrId(paymentHdrId);
						 
					}
				
				mOrderPaymentsProvider.save(orderHdrItem);
				mOrderDiscountProvider.save(orderHdrItem);
				
				for (BeanOrderPaymentHeader ordPayHdr: orderHdrItem.getOrderPaymentHeaders())
					ordPayHdr.setNew(false);
				for (BeanOrderPayment ordPay: orderHdrItem.getOrderPaymentItems())
					 ordPay.setNew(false);
 
				if(orderHdrItem.getBillDiscounts()!=null){
					
					for (BeanOrderDiscount ordDisc: orderHdrItem.getBillDiscounts())
						ordDisc.setNew(false);
				}
			 
			}
			
			/**
			 * Order Splits savings
			 */
			if(isSplits)
				mOrderSplitProvider.save(orderHdrItem);

			mConnection.commit(); 
			mConnection.setAutoCommit(true);

			if(orderHdrItem.isNewOrder()){
				
				mCounterProvider.updateOrderBillNumber();
				
				final String queueNo=new PosOrderQueueProvider().getOrderQueueNo(orderHdrItem);
				orderHdrItem.setQueueNo(queueNo);
			}
				

			status= true; 
			orderHdrItem.setNewOrder(false);
			
			PosLog.debug("Finished save order ...... ");
			
		} catch (Exception e) {
			
			try {
				
				mConnection.rollback();
			} catch (Exception e1) {
				
				PosLog.write(this,"saveOrder ("+orderHdrItem.getOrderNo()+")",e1);
			}
			
			PosLog.write(this,"saveOrder ("+orderHdrItem.getOrderNo()+")",e);
			status= false;
			throw new Exception("Failed to save the order. Please check the log for details");
		}finally{

		}
		return status;
	}

	/**
	 * @throws SQLException
	 * Execute the prepared statement.
	 */
	private void executePS() throws SQLException{

		mHeaderItemPs.execute();
	}

	/**
	 * @throws SQLException
	 */
	private void createInsPreparedStatment() throws SQLException{

		final String insert_sql="insert into order_hdrs  " +
				"(order_id,order_no,station_code,user_id,order_date,order_time,shift_id,customer_id,detail_total," +
				"total_tax1,total_tax2,total_tax3,total_gst,total_sc,total_detail_discount," +
				"final_round_amount,total_amount,total_amount_paid,total_balance," +
				"actual_balance_paid,status,total_print_count,created_by,created_at,updated_by," +
				"updated_at,remarks,sync_status,shop_code,closing_date,closing_time,is_ar_customer," +
				"bill_less_tax_amount,bill_discount_amount,cash_out,refund_total_tax1,refund_total_tax2," +
				"refund_total_tax3,refund_total_gst,refund_total_sc,refund_amount,serving_table_id,covers," +
				"served_by,service_type,payment_process_status,is_locked,locked_station_code,alias_text,driver_name,vehicle_number," +
				"bill_discount_percentage,due_datetime,order_medium,delivery_type,extra_charges,invoice_prefix,"+
				" extra_charge_tax_id, extra_charge_tax_code, extra_charge_tax_name, " + 
				" extra_charge_tax1_name, extra_charge_tax1_pc, extra_charge_tax1_amount,"+
				 "extra_charge_tax2_name, extra_charge_tax2_pc, extra_charge_tax2_amount,"+
				 "extra_charge_tax3_name, extra_charge_tax3_pc, extra_charge_tax3_amount,"+
				 "extra_charge_sc_name,extra_charge_sc_pc,extra_charge_sc_amount,"+
				 "extra_charge_gst_name, extra_charge_gst_pc, extra_charge_gst_amount, extra_charge_remarks,closed_by )" +
				" values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,"+
				 "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"; 
		mHeaderItemPs=getConnection().prepareStatement(insert_sql);
	}

	/**
	 * @throws SQLException
	 */
	private void createUpdPreparedStatment() throws SQLException {

		final String update_sql = "update order_hdrs set " +
				"order_id=?,order_no=?,station_code=?,user_id=?,order_date=?,order_time=?,shift_id=?,customer_id=?,detail_total=?," +
				"total_tax1=?,total_tax2=?,total_tax3=?,total_gst=?,total_sc=?,total_detail_discount=?," +
				"final_round_amount=?,total_amount=?,total_amount_paid=?,total_balance=?," +
				"actual_balance_paid=?,status=?,total_print_count=?,created_by=?,created_at=?,updated_by=?," +
				"updated_at=?,remarks=?,sync_status=?,shop_code=?,closing_date=?,closing_time=?,is_ar_customer=?," +
				"bill_less_tax_amount=?,bill_discount_amount=?,cash_out=?,refund_total_tax1=?,refund_total_tax2=?," +
				"refund_total_tax3=?,refund_total_gst=?,refund_total_sc=?,refund_amount=?,serving_table_id=?,covers=?," +
				"served_by=?,service_type=?,payment_process_status=?,is_locked=?,locked_station_code=?, alias_text=?," + 
				"driver_name=?,vehicle_number=?,bill_discount_percentage=?," + 
				"due_datetime=?,order_medium=?,delivery_type=?,extra_charges=?,invoice_prefix=?,"+
				" extra_charge_tax_id=?, extra_charge_tax_code=?, extra_charge_tax_name=?, " + 
				" extra_charge_tax1_name=?, extra_charge_tax1_pc=?, extra_charge_tax1_amount=?,"+
				 "extra_charge_tax2_name=?, extra_charge_tax2_pc=?, extra_charge_tax2_amount=?,"+
				 "extra_charge_tax3_name=?, extra_charge_tax3_pc=?, extra_charge_tax3_amount=?,"+
				 "extra_charge_sc_name=?,extra_charge_sc_pc=?,extra_charge_sc_amount=?,"+
				 "extra_charge_gst_name=?, extra_charge_gst_pc=?, extra_charge_gst_amount=?, extra_charge_remarks=?," +
				 "closed_by=?,void_by=?,void_at=? " +
				" where order_id=?";

		mHeaderItemPs=getConnection().prepareStatement(update_sql);

	}

	/**
	 * @param orderHdrItem
	 * @throws SQLException
	 */
	private void save(BeanOrderHeader orderHdrItem) throws SQLException {	
		
		PosLog.debug("Satrting to save order hdr...... ");

		final boolean isExist = isExist("order_id='"+orderHdrItem.getOrderId()+"'");

		if(isExist)
			createUpdPreparedStatment();
		else{
			orderHdrItem.setInvoicePrefix(PosOrderUtil.getFormatedInvoicePreifx());
			createInsPreparedStatment();
		}
		 if(orderHdrItem.getCreatedAt()==null || orderHdrItem.getCreatedAt().trim().equals(""))
			 orderHdrItem.setCreatedAt(	PosDateUtil.getDateTime());
					
		mHeaderItemPs.setString(1, orderHdrItem.getOrderId());
		mHeaderItemPs.setInt(2, orderHdrItem.getOrderNo());
		mHeaderItemPs.setString(3, orderHdrItem.getStationCode());
		mHeaderItemPs.setInt(4, orderHdrItem.getUser().getId());
		mHeaderItemPs.setString(5, orderHdrItem.getOrderDate());
		mHeaderItemPs.setString(6, orderHdrItem.getOrderTime());
		mHeaderItemPs.setInt(7, PosEnvSettings.getInstance().getCashierShiftInfo().getShiftItem().getId());
		mHeaderItemPs.setInt(8, orderHdrItem.getCustomer().getId());
		mHeaderItemPs.setDouble(9, orderHdrItem.getDetailTotal());
		mHeaderItemPs.setDouble(10, orderHdrItem.getTotalTax1());
		mHeaderItemPs.setDouble(11, orderHdrItem.getTotalTax2());
		mHeaderItemPs.setDouble(12, orderHdrItem.getTotalTax3());
		mHeaderItemPs.setDouble(13, orderHdrItem.getTotalGST());
		mHeaderItemPs.setDouble(14, orderHdrItem.getTotalServiceTax());
		mHeaderItemPs.setDouble(15, orderHdrItem.getTotalDetailDiscount());
		mHeaderItemPs.setDouble(16, orderHdrItem.getRoundAdjustmentAmount());
		mHeaderItemPs.setDouble(17, orderHdrItem.getTotalAmount());
		mHeaderItemPs.setDouble(18, orderHdrItem.getTotalAmountPaid());
		mHeaderItemPs.setDouble(19, orderHdrItem.getChangeAmount());
		mHeaderItemPs.setDouble(20, orderHdrItem.getActualBalancePaid());
		mHeaderItemPs.setInt(21, orderHdrItem.getStatus().getCode());
		mHeaderItemPs.setInt(22, orderHdrItem.getTotalPrintCount());
		mHeaderItemPs.setInt(23, PosEnvSettings.getInstance().getCashierShiftInfo().getCashierInfo().getId());
		mHeaderItemPs.setString(24, orderHdrItem.getCreatedAt());
		mHeaderItemPs.setInt(25, PosEnvSettings.getInstance().getCashierShiftInfo().getCashierInfo().getId());
		mHeaderItemPs.setString(26, PosDateUtil.getDateTime());
		mHeaderItemPs.setString(27, orderHdrItem.getRemarks());
		mHeaderItemPs.setInt(28, 0);
		mHeaderItemPs.setString(29, orderHdrItem.getShopeCode());
		if(orderHdrItem.getStatus()==PosOrderStatus.Closed || orderHdrItem.getStatus()==PosOrderStatus.Refunded || orderHdrItem.getStatus()==PosOrderStatus.Void ){
			mHeaderItemPs.setString(30, orderHdrItem.getClosingDate());
			mHeaderItemPs.setString(31, orderHdrItem.getClosingTime());
			mHeaderItemPs.setInt(77, orderHdrItem.getClosedBy().getId());
		}else{
			mHeaderItemPs.setObject(30, null);
			mHeaderItemPs.setObject(31, null);
			mHeaderItemPs.setObject(77, null);
		}

		mHeaderItemPs.setBoolean(32,  orderHdrItem.getCustomer().isIsArCompany());
		mHeaderItemPs.setDouble(33, orderHdrItem.getBillTaxAmount());

		final double billDiscountAmount=orderHdrItem.getBillDiscountAmount();
		mHeaderItemPs.setDouble(35,orderHdrItem.getCashOut());
		mHeaderItemPs.setDouble(34,  billDiscountAmount);
		mHeaderItemPs.setDouble(36, orderHdrItem.getRefundTotalAmountTax1());
		mHeaderItemPs.setDouble(37, orderHdrItem.getRefundTotalAmountTax2());
		mHeaderItemPs.setDouble(38, orderHdrItem.getRefundTotalAmountTax3());
		mHeaderItemPs.setDouble(39, orderHdrItem.getRefundTotalAmountGST());
		mHeaderItemPs.setDouble(40, orderHdrItem.getRefundTotalAmountServiceTax());
		mHeaderItemPs.setDouble(41, orderHdrItem.getRefundAmount());
		if(orderHdrItem.getServiceTable()!=null){
			mHeaderItemPs.setInt(42, orderHdrItem.getServiceTable().getId());
			mHeaderItemPs.setInt(43, orderHdrItem.getCovers());
		}else{
			mHeaderItemPs.setObject(42, null);
			mHeaderItemPs.setObject(43, null);
		}
		if(orderHdrItem.getServedBy()!=null)
			mHeaderItemPs.setInt(44, orderHdrItem.getServedBy().getId());
		else
			mHeaderItemPs.setObject(44, null);
		mHeaderItemPs.setInt(45, orderHdrItem.getOrderServiceType().getCode());
		mHeaderItemPs.setInt(46, orderHdrItem.getPaymentProcessStatus().getCode());
		mHeaderItemPs.setBoolean(47, orderHdrItem.isOrderLocked());
		mHeaderItemPs.setString(48, orderHdrItem.getLockedStation());
		mHeaderItemPs.setString(49, orderHdrItem.getAliasText());
		mHeaderItemPs.setString(50, orderHdrItem.getDriverName());
		mHeaderItemPs.setString(51, orderHdrItem.getVehicleNumber());
		mHeaderItemPs.setDouble(52,orderHdrItem.getBillDiscountPercentage());
		mHeaderItemPs.setString(53, orderHdrItem.getDueDateTime());
		mHeaderItemPs.setInt(54, orderHdrItem.getOrderByMedium()==null?0:orderHdrItem.getOrderByMedium().getCode());
		mHeaderItemPs.setInt(55, orderHdrItem.getDeliveryType()==null?0:orderHdrItem.getDeliveryType().getCode());
		mHeaderItemPs.setDouble(56, orderHdrItem.getExtraCharges());
		mHeaderItemPs.setString(57, orderHdrItem.getInvoicePrefix());
		
		if(orderHdrItem.getExtraCharges()>0){
			
			mHeaderItemPs.setInt(58, orderHdrItem.getExtraChargeTaxId());
			mHeaderItemPs.setString(59, orderHdrItem.getExtraChargeTaxCode());
			mHeaderItemPs.setString(60, orderHdrItem.getExtraChargeTaxName());
			
			mHeaderItemPs.setString(61, orderHdrItem.getExtraChargeTaxOneName());
			mHeaderItemPs.setDouble(62, orderHdrItem.getExtraChargeTaxOnePercentage());
			mHeaderItemPs.setDouble(63, orderHdrItem.getExtraChargeTaxOneAmount());
			
			mHeaderItemPs.setString(64, orderHdrItem.getExtraChargeTaxTwoName());
			mHeaderItemPs.setDouble(65, orderHdrItem.getExtraChargeTaxTwoPercentage());
			mHeaderItemPs.setDouble(66, orderHdrItem.getExtraChargeTaxTwoAmount());
			
			mHeaderItemPs.setString(67, orderHdrItem.getExtraChargeTaxThreeName());
			mHeaderItemPs.setDouble(68, orderHdrItem.getExtraChargeTaxThreePercentage());
			mHeaderItemPs.setDouble(69, orderHdrItem.getExtraChargeTaxThreeAmount());
			
			mHeaderItemPs.setString(70, orderHdrItem.getExtraChargeSCName());
			mHeaderItemPs.setDouble(71, orderHdrItem.getExtraChargeSCPercentage());
			mHeaderItemPs.setDouble(72, orderHdrItem.getExtraChargeSCAmount());
			
			mHeaderItemPs.setString(73, orderHdrItem.getExtraChargeGSTName());
			mHeaderItemPs.setDouble(74, orderHdrItem.getExtraChargeGSTPercentage());
			mHeaderItemPs.setDouble(75, orderHdrItem.getExtraChargeGSTAmount());
			
			mHeaderItemPs.setString(76, orderHdrItem.getExtraChargeRemarks());
			}else{
				mHeaderItemPs.setNull(58, java.sql.Types.INTEGER);
				mHeaderItemPs.setNull(59, java.sql.Types.VARCHAR);
				mHeaderItemPs.setNull(60, java.sql.Types.VARCHAR);
				
				mHeaderItemPs.setNull(61, java.sql.Types.VARCHAR);
				mHeaderItemPs.setNull(62, java.sql.Types.DOUBLE);
				mHeaderItemPs.setNull(63, java.sql.Types.DOUBLE);
				
				mHeaderItemPs.setNull(64, java.sql.Types.VARCHAR);
				mHeaderItemPs.setNull(65, java.sql.Types.DOUBLE);
				mHeaderItemPs.setNull(66, java.sql.Types.DOUBLE);
				
				mHeaderItemPs.setNull(67, java.sql.Types.VARCHAR);
				mHeaderItemPs.setNull(68, java.sql.Types.DOUBLE);
				mHeaderItemPs.setNull(69, java.sql.Types.DOUBLE);
				
				mHeaderItemPs.setNull(70, java.sql.Types.VARCHAR);
				mHeaderItemPs.setNull(71, java.sql.Types.DOUBLE);
				mHeaderItemPs.setNull(72, java.sql.Types.DOUBLE);
				
				mHeaderItemPs.setNull(73, java.sql.Types.VARCHAR);
				mHeaderItemPs.setNull(74, java.sql.Types.DOUBLE);
				mHeaderItemPs.setNull(75, java.sql.Types.DOUBLE);
				
				mHeaderItemPs.setString(76, orderHdrItem.getExtraChargeRemarks());
			}
		
		if (isExist) {
			if (orderHdrItem.getStatus()==PosOrderStatus.Void){
				mHeaderItemPs.setInt(78, orderHdrItem.getVoidBy().getId());
				mHeaderItemPs.setString(79, orderHdrItem.getVoidAt());
			}
			else{
				mHeaderItemPs.setNull(78, java.sql.Types.INTEGER);
				mHeaderItemPs.setNull(79, java.sql.Types.DATE);
       	 	}
			 
			mHeaderItemPs.setString(80, orderHdrItem.getOrderId());
		} 

		executePS();
		
		
		
		PosLog.debug("Finished save order hdr...... ");
	}

	/**
	 * @param order
	 * @throws SQLException 
	 */
	public void deleteOrderData(BeanOrderHeader order) throws SQLException{

		deleteOrderData(order,true,true,true,true);
	}

	/**
	 * @param order
	 * @param isHeader
	 * @param isDetails
	 * @param isPayments
	 * @param isSplits 
	 * @throws SQLException
	 */
	public void deleteOrderData(BeanOrderHeader order, boolean isHeader, boolean isDetails, boolean isPayments, boolean isSplits) throws SQLException {

		final String where= " order_id='"+order.getOrderId()+"'";
		if(isHeader)
			deleteData("order_hdrs",where);
		
		if(isDetails){
			mOrderDtlProvider.deleteData(where);
			mServingTableProvider.deleteData(where);
		}

		if(isSplits)
			mOrderSplitProvider.deleteData(where);
		
		if(isPayments){
			mOrderPaymentsProvider.deleteData(where);
			mOrderDiscountProvider.deleteData(where);
		}
	} 

 
	
	

	
	public void deleteOrderData(String orderId) throws SQLException {
		final String where= " order_id='"+orderId+"'";
		try {	
			mConnection.setAutoCommit(false);
			
			deleteData("order_hdrs",where);
			mOrderDtlProvider.deleteData(where);
//			mOrderCustomerProvider.deleteData(where);
			mOrderPaymentHdrProvider.deleteData(where);
			mOrderPaymentsProvider.deleteData(where);
			mOrderDiscountProvider.deleteData(where);
			mOrderSplitProvider.deleteData(where);
			mServingTableProvider.deleteData(where);
			mOrderSplitProvider.deleteData(where);
			mServingSeatProvider.deleteData(where);
//			mInvoiceProvider.deleteData(where); // To avoid making holes int invoice numbers Chances are there to re-start from 1
			mOrderQueueProvider.deleteData(where);
			mOrderPreDiscountProvider.deleteData(where);
			mOrderRefundProvider.deleteData(where);
			mConnection.commit();
			mConnection.setAutoCommit(true);
		} catch (Exception e) {
			
			mConnection.rollback();
			
			throw e;
		} 
	} 

	public void resetAdvancePayment(String orderId) throws SQLException {
		
		final String where= " order_id='"+orderId+"'";
		try {	
			mConnection.setAutoCommit(false);
			
			mOrderPaymentsProvider.deleteData(where);
			mOrderPaymentHdrProvider.deleteData(where);
			mOrderSplitProvider.deleteData(where);
			
//			final String sql="UPDATE order_hdrs SET total_amount_paid=0,total_balance=0,actual_balance_paid=0,cash_out=0 WHERE " +
//							 where ;
//			executeNonQuery(sql);
			
			mConnection.commit();
			mConnection.setAutoCommit(true);
		} catch (Exception e) {
			
			mConnection.rollback();
			
			throw e;
		} 
	} 
	public void updatePrintCounter(String orderID){
		String sql="update order_hdrs set total_print_count=total_print_count+1 where order_id='"+orderID+"'";
		try {
			executeNonQuery(sql);
		} catch (SQLException e) {
			PosLog.write(this,"updatePrintCounter", e);
		}
	}

	public BeanOrderHeader getOrderData(String orderID) throws Exception{
		
		BeanOrderHeader orderHeader=null;
		String where="order_id='"+orderID+"'";
		CachedRowSet crs=getData(where);
		try {
			if(crs!=null && crs.next())
				orderHeader=createOrderFromRecordSet(crs);
		} catch (SQLException e) {
			PosLog.write(this, "getOrderData", e);
			throw(new Exception("Failed to retrieve order data. Please Check log for details."));
		}finally{
			if(crs!=null )
				try {
					crs.close();
				} catch (SQLException e) {
					PosLog.write(this, "getOrderData", e);
				}
		}
		return orderHeader;	
	}
	
	/**
	 * Return the list of orders matching the order id or part of it 
	 * @param orderID
	 * @return
	 * @throws Exception
	 */
	public BeanOrderHeader getProcessingOrder(){
		BeanOrderHeader orderHeader=null;
		/**
		 *
		 *		Since changing the status of the order to process can cause loosing the actual status, moved to separate filed 
		 * 
		 * 		String where="status='"+PosOrderStatus.Processing.getCode()+"'";
		 */

		String where="payment_process_status='"+PaymentProcessStatus.PROCESSING.getCode()+"'";

		CachedRowSet crs=getData(where);
		try {
			if(crs!=null && crs.next())
				try {
					orderHeader=createOrderFromRecordSet(crs);
				} catch (Exception e) {
					PosLog.write(this, "getProcessingOrder", e);
				}
		} catch (SQLException e) {
			PosLog.write(this, "getProcessingOrder", e);
		}finally{
			if(crs!=null )
				try {
					crs.close();
				} catch (SQLException e) {
					PosLog.write(this, "getProcessingOrder", e);
				}
		}

		return orderHeader;
	}

	/**
	 * @param crs
	 * @return
	 * @throws Exception
	 */
	private BeanOrderHeader createOrderFromRecordSet(CachedRowSet crs) throws Exception{
		return createOrderFromRecordSet(crs, false);
	}

	/**
	 * @param crs
	 * @param isHeaderOnly
	 * @return
	 * @throws Exception
	 */
	private BeanOrderHeader createOrderFromRecordSet(CachedRowSet crs, boolean isHeaderOnly) throws Exception{

		BeanOrderHeader orderObject=new BeanOrderHeader();
		orderObject.setNewOrder(false);
		
		orderObject.setOrderId(crs.getString("order_id"));
		orderObject.setInvoicePrefix(crs.getString("invoice_prefix"));
		orderObject.setQueueNo(Integer.toString(crs.getInt("queue_no") ));	
		orderObject.setInvoiceNo(PosOrderUtil.getFormatedInvoiceNumber( crs.getInt("invoice_no")) );	
		orderObject.setUser(mUserProvider.getUserByID(crs.getInt("user_id")));
		orderObject.setOrderDate(crs.getString("order_date"));
		orderObject.setOrderTime(crs.getString("order_time"));
		orderObject.setShiftId(crs.getInt("shift_id"));
		final boolean is_ar=crs.getBoolean("is_ar_customer");
		orderObject.setCustomer((is_ar)?new PosCompanyItemProvider().getById(crs.getInt("customer_id")):
			new PosCustomerProvider().getById(crs.getInt("customer_id")));
		orderObject.setDetailTotal(crs.getDouble("detail_total"));
		orderObject.setTotalTax1(crs.getDouble("total_tax1"));
		orderObject.setTotalTax2(crs.getDouble("total_tax2"));
		orderObject.setTotalTax3(crs.getDouble("total_tax3"));
		orderObject.setTotalGST(crs.getDouble("total_gst"));
		orderObject.setTotalServiceTax(crs.getDouble("total_sc"));
		orderObject.setTotalDetailDiscount(crs.getDouble("total_detail_discount"));
		orderObject.setRoundAdjustmentAmount(crs.getDouble("final_round_amount"));
		orderObject.setTotalAmount(crs.getDouble("total_amount"));
		orderObject.setTotalAmountPaid(crs.getDouble("total_amount_paid"));
		orderObject.setChangeAmount(crs.getDouble("total_balance"));
		orderObject.setActualBalancePaid(crs.getDouble("actual_balance_paid"));
		orderObject.setCashOut(crs.getDouble("cash_out"));
		orderObject.setStatus(PosOrderStatus.get(crs.getInt("status")));
		orderObject.setRemarks(crs.getString("remarks"));
		orderObject.setTotalPrintCount(crs.getInt("total_print_count"));	
		orderObject.setClosingDate(crs.getString("closing_date"));
		orderObject.setClosingTime(crs.getString("closing_time"));
		orderObject.setClosedBy(mUserProvider.getUserByID(crs.getInt("closed_by")));
		orderObject.setDetailItemCount(crs.getInt("item_count"));
		orderObject.setDetailItemQuatity(crs.getDouble("total_qty"));
		orderObject.setBillDiscountAmount(crs.getDouble("bill_discount_amount"));
		orderObject.setBillDiscountPercentage(crs.getDouble("bill_discount_percentage"));
		orderObject.setBillTaxAmount(crs.getDouble("bill_less_tax_amount"));
		orderObject.setServiceTable(mServingTableProvider.getTableByID(crs.getInt("serving_table_id")));
		orderObject.setCovers(crs.getInt("covers"));
		/*
		 * Make sure that the waiter table does not contain any records havign id=0;
		 */
		orderObject.setServedBy(mWaiterProvider.getWaiterById(crs.getInt("served_by")));
		orderObject.setOrderServiceType(PosOrderServiceTypes.get(crs.getInt("service_type")));
		
		//set pre bill discount
		orderObject.setPreBillDiscount(mOrderPreDiscountProvider.getPreBillDiscount(orderObject.getOrderId()));
	
		
		orderObject.setDueDateTime(crs.getString("due_datetime"));
		orderObject.setOrderByMedium(PosOrderMedium.get(crs.getInt("order_medium")));
		orderObject.setDeliveryType(PosOrderServiceTypes.get(crs.getInt("delivery_type")));
		orderObject.setExtraCharges(crs.getDouble("extra_charges"));
		
		
		//Refund Amount 
		orderObject.setRefundAmount(crs.getDouble("refund_amount"));
		orderObject.setRefundTotalAmountTax1(crs.getDouble("refund_total_tax1"));
		orderObject.setRefundTotalAmountTax2(crs.getDouble("refund_total_tax2"));
		orderObject.setRefundTotalAmountTax3(crs.getDouble("refund_total_tax3"));
		orderObject.setRefundTotalAmountGST(crs.getDouble("refund_total_gst"));
		orderObject.setRefundTotalAmountServiceTax(crs.getDouble("refund_total_sc"));
		
		final BeanOrderCustomer orderCustomer=mOrderCustomerProvider.getOrderCustomer(orderObject.getOrderId());
		
		orderCustomer.setId(orderObject.getCustomer().getId());
		orderObject.setOrderCustomer(orderCustomer);
		System.out.println(orderObject.getCustomer().getName());
		if(!isHeaderOnly){
			
			final Map<String, BeanOrderServingTable> servingableList=mServingTableProvider.getOrderTables(orderObject.getOrderId());
			orderObject.setOrderTableList(servingableList);
			
			final ArrayList<BeanOrderDetail> orderDetailList=mOrderDtlProvider.getOrderDetailData(orderObject);
			orderObject.setOrderDetailItems(orderDetailList);
			orderObject.setBillDiscounts(mOrderDiscountProvider.getOrderDiscounts(orderObject));
			
			final ArrayList<BeanOrderPaymentHeader> paymentHdrList=mOrderPaymentHdrProvider.getOrderPaymentHeaders(orderObject);
			orderObject.setOrderPaymentHeaders(paymentHdrList); 
			
			final ArrayList<BeanOrderPayment> paymentList=mOrderPaymentsProvider.getOrderPayments(orderObject);
			orderObject.setOrderPaymentItems(paymentList);
			
			if(PosOrderUtil.isDeliveryService(orderObject)  && 
					orderObject.getStatus()==PosOrderStatus.Partial && 
							paymentList!=null && paymentList.size()>0){
				orderObject.setAdvanceAmount(paymentList.get(0).getPaidAmount());
				orderObject.setAdvancePaymentMode(paymentList.get(0).getPaymentMode());
			}
			
		 	final ArrayList<BeanOrderSplit> orderSplits=mOrderSplitProvider.getSplits(orderObject);
			orderObject.setOrderSplits(orderSplits);
			/**
			 * Flatten the splits for checking at order entry level
			 */
			if(orderObject.getOrderSplits()!=null){

				orderObject.setSplitItemIDList(PosOrderSplitUtil.createSplitItemIDList(orderObject.getOrderSplits()));
				/**
				 * TO DO
				 * If needed set the detail order item to the split details object.
				 * Only id is set now.
				 * order detail item is used for printing receipt for splits now
				 * so reloading may not needed.
				 */
				orderObject.setSplitPartRecieved(PosOrderSplitUtil.getSplitPartAmountRecieved(orderObject));
				orderObject.setSplitPartUsed(PosOrderSplitUtil.getSplitPartAmountUsed(orderObject));
			}

		}
		orderObject.setPaymentProcessStatus(PaymentProcessStatus.get(crs.getInt("payment_process_status")));
		orderObject.setOrderLocked(crs.getBoolean("is_locked"));
		orderObject.setLockedStation(crs.getString("locked_station_code"));
		orderObject.setAliasText(crs.getString("alias_text"));
		orderObject.setDriverName(crs.getString("driver_name"));
		orderObject.setVehicleNumber(crs.getString("vehicle_number"));
		
		orderObject.setExtraChargeTaxId(crs.getInt("extra_charge_tax_id"));
		orderObject.setExtraChargeTaxCode(crs.getString("extra_charge_tax_code"));
		orderObject.setExtraChargeTaxName(crs.getString("extra_charge_tax_name"));
		
		orderObject.setExtraChargeTaxOneName(crs.getString("extra_charge_tax1_name"));
		orderObject.setExtraChargeTaxOnePercentage(crs.getDouble("extra_charge_tax1_pc"));
		orderObject.setExtraChargeTaxOneAmount(crs.getDouble("extra_charge_tax1_amount"));
		
		orderObject.setExtraChargeTaxTwoName(crs.getString("extra_charge_tax2_name"));
		orderObject.setExtraChargeTaxTwoPercentage(crs.getDouble("extra_charge_tax2_pc"));
		orderObject.setExtraChargeTaxTwoAmount(crs.getDouble("extra_charge_tax2_amount"));
		
		orderObject.setExtraChargeTaxThreeName(crs.getString("extra_charge_tax3_name"));
		orderObject.setExtraChargeTaxThreePercentage(crs.getDouble("extra_charge_tax3_pc"));
		orderObject.setExtraChargeTaxThreeAmount(crs.getDouble("extra_charge_tax3_amount"));
		
		orderObject.setExtraChargeSCName(crs.getString("extra_charge_sc_name"));
		orderObject.setExtraChargeSCPercentage(crs.getDouble("extra_charge_sc_pc"));
		orderObject.setExtraChargeSCAmount(crs.getDouble("extra_charge_sc_amount"));
		
		orderObject.setExtraChargeGSTName(crs.getString("extra_charge_gst_name"));
		orderObject.setExtraChargeGSTPercentage(crs.getDouble("extra_charge_gst_pc"));
		orderObject.setExtraChargeGSTAmount(crs.getDouble("extra_charge_gst_amount"));
		
		orderObject.setExtraChargeRemarks(crs.getString("extra_charge_remarks"));
		orderObject.setCreatedAt(crs.getString("created_at"));
 		 
		if(orderObject.getStatus()==PosOrderStatus.Void){
			orderObject.setVoidBy(mUserProvider.getUserByID(crs.getInt("void_by")));
			orderObject.setVoidAt(crs.getString("void_at"));
		}
		return orderObject;
	}
	/**
	 * @param crs
	 * @param isHeaderOnly
	 * @return
	 * @throws Exception
	 */
	private BeanOrderHeader createOrderViewListFromRecordSet(CachedRowSet crs) throws Exception{

		BeanOrderHeader orderObject=new BeanOrderHeader();
		 System.out.println(new PosCustomerProvider().getById(crs.getInt("customer_id")));
		orderObject.setOrderId(crs.getString("order_id"));
		orderObject.setInvoicePrefix(crs.getString("invoice_prefix"));
		orderObject.setQueueNo(Integer.toString(crs.getInt("queue_no") ));	
		orderObject.setInvoiceNo(PosOrderUtil.getFormatedInvoiceNumber( crs.getInt("invoice_no")) );	
		orderObject.setUser(mUserProvider.getUserByID(crs.getInt("user_id")));
		orderObject.setOrderDate(crs.getString("order_date"));
		orderObject.setOrderTime(crs.getString("order_time"));
		final boolean is_ar=crs.getBoolean("is_ar_customer");
		orderObject.setCustomer((is_ar)?new PosCompanyItemProvider().getById(crs.getInt("customer_id")):
			new PosCustomerProvider().getById(crs.getInt("customer_id")));
		orderObject.setRoundAdjustmentAmount(crs.getDouble("final_round_amount"));
		orderObject.setTotalAmount(crs.getDouble("total_amount")); // update by aslam on 27-01-2021
//		orderObject.setTotalAmount(crs.getDouble("total_amount") - crs.getDouble("total_detail_discount"));
		 
		orderObject.setStatus(PosOrderStatus.get(crs.getInt("status")));
		orderObject.setBillDiscountAmount(crs.getDouble("bill_discount_amount"));
		orderObject.setServiceTable(mServingTableProvider.getTableByID(crs.getInt("serving_table_id")));
		orderObject.setServedBy(mWaiterProvider.getWaiterById(crs.getInt("served_by")));
		orderObject.setOrderServiceType(PosOrderServiceTypes.get(crs.getInt("service_type")));
		
		final BeanOrderCustomer orderCustomer=new BeanOrderCustomer();
		
		orderCustomer.setId(orderObject.getCustomer().getId());
		orderCustomer.setName(crs.getString("name"));
		orderCustomer.setPhoneNumber(crs.getString("phone"));
		orderObject.setOrderCustomer(orderCustomer);

		orderObject.setLockedStation(crs.getString("locked_station_code"));
		orderObject.setOrderLocked(crs.getBoolean("is_locked"));
		
		orderObject.setAliasText(crs.getString("alias_text"));
		return orderObject;
	}

	/**
	 * @param orderHeader
	 * @throws Exception
	 */
	public void setOrderIncomplete(BeanOrderHeader orderHeader) throws Exception{
		
		updateStatus(orderHeader,PosOrderStatus.Void);
	}

	/**
	 * @param orderHeader
	 * @param status
	 * @throws Exception
	 */
	public void updateStatus(BeanOrderHeader orderHeader, PosOrderStatus status) throws Exception {
		
		updateStatus(orderHeader.getOrderId(),status);
		orderHeader.setStatus(status);
	}

	/**
	 * @param orderId
	 * @param status
	 * @throws Exception
	 */
	public void updateStatus(String orderId, PosOrderStatus status) throws Exception {
		final String sql="update order_hdrs set status="+status.getCode()+ " where order_id='"+orderId+"'";
		try {
			executeNonQuery(sql);
		} catch (SQLException e) {
			PosLog.write(this, "updateStatus", e);
			throw new Exception("Failed to update the order status. Please check the log for details");
		}
	}
	/**
	 * @param where
	 * @param isHederOnly
	 * @return
	 * @throws Exception
	 */
	public ArrayList<BeanOrderHeader> getOrderHeaderViewList(String where) throws Exception{
		ArrayList<BeanOrderHeader> orderList=null;
		//		final String where=((status==null)?null:"status="+status.getCode());
		final String orderBy="serving_table_id, order_date desc, order_time desc";
		
		where=where.replace("order_id", "hdr.order_id");
		
		final String sql=" SELECT hdr.invoice_prefix  AS  invoice_prefix , hdr.invoice_no  AS  invoice_no ,	 order_queue.id  AS  queue_no ,"+
				 " hdr.order_id  AS  order_id ,hdr.user_id as user_id, hdr.order_date  AS  order_date , hdr.order_time  AS  order_time ,"+
				 " hdr.customer_id  AS  customer_id ,	 hdr.is_ar_customer  AS  is_ar_customer ,"+ 	 
				 " hdr.final_round_amount  AS  final_round_amount , hdr.total_amount  AS  total_amount ,"+
				 " hdr.status  AS  status , 	 hdr.bill_discount_amount  AS  bill_discount_amount ,"+
				 " hdr.serving_table_id  AS  serving_table_id ,	 hdr.served_by  AS  served_by ,"+
				 " hdr.service_type  AS  service_type , "+
				 " order_customers.name  AS  name , 	 order_customers.phone  AS  phone," + 
				 " hdr.is_locked ,hdr.locked_station_code,hdr.alias_text as alias_text,hdr.total_detail_discount "+ 
				 " FROM  order_hdrs   hdr "+
				 " LEFT JOIN  order_queue  ON hdr.order_id  =  order_queue.order_id "+
				 " JOIN  order_customers  ON hdr.order_id  =  order_customers.order_id where " + where + " order by " + orderBy;
		CachedRowSet crs=executeQuery(sql);
		if(crs!=null){
			orderList=new ArrayList<BeanOrderHeader>();
			try {
				while(crs.next()){
					BeanOrderHeader orderObject=createOrderViewListFromRecordSet(crs);
					orderList.add(orderObject);
				}
			} catch (Exception e) {
				PosLog.write(this, "getOrderHeaders", e);
				throw new Exception("Failed get order headers. Please check log for details.");
			}finally{
				try {
					crs.close();
				} catch (SQLException e) {
					PosLog.write(this, "getOrderHeaders", e);
					throw new Exception("Failed get order headers. Please check log for details.");
				}
			}
		}
		return orderList;
	}

	/**
	 * @param where
	 * @param isHederOnly
	 * @return
	 * @throws Exception
	 */
	public ArrayList<BeanOrderHeader> getOrderDetails(String where, boolean isHederOnly) throws Exception{
		ArrayList<BeanOrderHeader> orderList=null;
		//		final String where=((status==null)?null:"status="+status.getCode());
		final String orderBy="serving_table_id, order_date desc, order_time desc";
		CachedRowSet crs=getData(where,orderBy);
		if(crs!=null){
			orderList=new ArrayList<BeanOrderHeader>();
			try {
				while(crs.next()){
					BeanOrderHeader orderObject=createOrderFromRecordSet(crs,isHederOnly);
					orderList.add(orderObject);
				}
			} catch (Exception e) {
				PosLog.write(this, "getOrderHeaders", e);
				throw new Exception("Failed get order headers. Please check log for details.");
			}finally{
				try {
					crs.close();
				} catch (SQLException e) {
					PosLog.write(this, "getOrderHeaders", e);
					throw new Exception("Failed get order headers. Please check log for details.");
				}
			}
		}
		return orderList;
	}

	/**
	 * @return
	 * @throws Exception
	 */
	public ArrayList<BeanOrderHeader> getOrderDetails() throws Exception{
		return getOrderDetails(null,false);
	}

	/**
	 * @param where
	 * @return
	 * @throws Exception
	 */
	public ArrayList<BeanOrderHeader> getOrderDetails(String where) throws Exception{
		return getOrderDetails(where,false);
	}

	/**
	 * @param status
	 * @return
	 * @throws Exception
	 */
	public ArrayList<BeanOrderHeader> getOrderDetails(PosOrderStatus status) throws Exception{
		final String where=((status==null)?null:"status="+status.getCode());
		return getOrderDetails(where,false);
	}
	

	/**
	 * @param orderID
	 * @return
	 * @throws Exception
	 */
	public BeanOrderHeader getOpenOrderById(String orderID) throws Exception {
		
		BeanOrderHeader orderHeader=null;
		final String where ="order_id='"+orderID+"' and status in ("+PosOrderStatus.Open.getCode()+", "+PosOrderStatus.Partial.getCode()+")";
		final ArrayList<BeanOrderHeader> orders=getOrderDetails(where,false);
		if(orders!=null && orders.size()==1)
			orderHeader=orders.get(0);
		
		return orderHeader;
	}

	/**
	 * @return
	 * @throws Exception
	 */
	public ArrayList<BeanOrderHeader> getOrderHeaders() throws Exception{
		return getOrderDetails(null,true);
	}

	/**
	 * @param where
	 * @return
	 * @throws Exception
	 */
	public ArrayList<BeanOrderHeader> getOrderHeaders(String where) throws Exception{
		return getOrderDetails(where,true);
	}
	
	/**
	 * @param where
	 * @return
	 * @throws Exception
	 */
	public double getTotalAdditionalCharges(String where) throws Exception{
		
		double totalExtraCharges=0;
		
		final String sql="Select sum(hdr.extra_charges  + IFNULL(hdr.extra_charge_tax1_amount,0) +  " +
						" IFNULL(hdr.extra_charge_tax2_amount,0)  + IFNULL(hdr.extra_charge_tax3_amount,0) + " +
						" IFNULL(hdr.extra_charge_sc_amount,0)) + IFNULL(hdr.extra_charge_gst_amount,0)) as total_extra_charges  FROM v_order_hdrs hdr WHERE " + where ;
		CachedRowSet crs=executeQuery(sql);
		if(crs!=null){
			try {
				if(crs.next()){

					totalExtraCharges=crs.getDouble("total_extra_charges") ;
				}
					
			} catch (Exception e) {
				PosLog.write(this, "getTotalAdditionalCharges", e);
				throw new Exception("Failed to get order header details.");
			}finally{
				try {
					crs.close();
				} catch (SQLException e) {
					PosLog.write(this, "getTotalAdditionalCharges", e);
					throw new Exception("Failed to get order header details.");
				}
			}
		}
		return totalExtraCharges;
	}
	
	/**
	 * @param where
	 * @return
	 * @throws Exception
	 */
	public int getOrderCount(String where) throws Exception{
		
		int orderCount=0;
		
		final String sql="Select count(invoice_no) as order_count  FROM v_order_hdrs WHERE " + where ;
		CachedRowSet crs=executeQuery(sql);
		if(crs!=null){
			try {
				if(crs.next()){

					orderCount=crs.getInt("order_count");
				}
					
			} catch (Exception e) {
				PosLog.write(this, "getOrderCount", e);
				throw new Exception("Failed to get order header details.");
			}finally{
				try {
					crs.close();
				} catch (SQLException e) {
					PosLog.write(this, "getOrderCount", e);
					throw new Exception("Failed to get order header details.");
				}
			}
		}
		return orderCount;
	}
	
	/**
	 * @param where
	 * @return
	 * @throws Exception
	 */
	public ArrayList<BeanOrderHeader> getLockedOrderHeaders(String stationCode,String where) throws Exception{
		
		where+=((where.isEmpty())?"":" and ")+"is_locked= 1 and locked_station_code='"+stationCode+"'";
		return getOrderDetails(where,true);
	}
	
	/**
	 * @param where
	 * @return
	 * @throws Exception
	 */
	public ArrayList<BeanOrderHeader> getLockedOrderHeaders(String stationCode) throws Exception{
		
		return getLockedOrderHeaders(stationCode,"");
	}
	/**
	 * @param where
	 * @return
	 * @throws Exception
	 */
	public String getOrderIds(String where) throws Exception{
		String inString="";
		ArrayList<BeanOrderHeader> orderIds = getOrderHeaders(where);
		for(int count = 0; count < orderIds.size(); count++){

			inString += "'"+orderIds.get(count).getOrderId()+"',";

		}

		return (inString.trim().length()!=0)?inString.substring(0,
				inString.length() - 1) : inString;
	}

	/**
	 * @param where
	 * @return
	 * @throws Exception
	 */
	public double getTotalRoundinAdjustments(String where) throws Exception{

		double roundingAdj=0;
		ArrayList<BeanOrderHeader> orderIds = getOrderHeaders(where);
		for(int count = 0; count < orderIds.size(); count++){

			roundingAdj += orderIds.get(count).getRoundAdjustmentAmount();

		}

		return roundingAdj;
	}

	/**
	 * @param where
	 * @return
	 * @throws Exception
	 */
	public double getTotalBillTax(String where) throws Exception {

		double totalTax = 0;
		ArrayList<BeanOrderHeader> orderhdrs = getOrderHeaders(where);
		for (int count = 0; count < orderhdrs.size(); count++) {

			totalTax += orderhdrs.get(count).getTotalTax1()+
					orderhdrs.get(count).getTotalTax2()+
					orderhdrs.get(count).getTotalTax3()+
					orderhdrs.get(count).getTotalServiceTax()+
					orderhdrs.get(count).getTotalGST()-
					orderhdrs.get(count).getBillTaxAmount();
		}

		return totalTax;
	}

	/**
	 * @param status
	 * @return
	 * @throws Exception
	 */
	public ArrayList<BeanOrderHeader> getOrderHeaders(PosOrderStatus status) throws Exception{
		final String where=((status==null)?null:"status="+status.getCode());
		return getOrderDetails(where,true);
	}

	/**
	 * @param orderID
	 * @return
	 * @throws Exception
	 */
	public BeanOrderHeader getOrderHeader(String orderID) throws Exception {
		BeanOrderHeader orderHeader=null;
		String where ="order_id='"+orderID+"'";
		CachedRowSet crs= getData(where);
		if(crs!=null){
			try {
				if(crs.next()){
					orderHeader=createOrderFromRecordSet(crs,true);
				}
			} catch (SQLException e) {
				PosLog.write(this, "getOrderHeader", e);
				throw new Exception("Failed to get order details.");
			}finally{
				try {
					crs.close();
				} catch (SQLException e) {
					PosLog.write(this, "getOrderHeader", e);
					throw new Exception("Failed to get order details.");
				}
			}
		}
		return orderHeader;
	}

	/**
	 * @param where
	 * @return
	 * @throws Exception
	 * Gets the last saved order
	 */
	public BeanOrderHeader getLatestOrderHeader(String where) throws Exception {
		BeanOrderHeader orderHeader=null;
		String SQL ="select * from v_order_hdrs where "+where+" order by order_time desc limit 1";
		//		String orderBy = "order_time desc";
		CachedRowSet crs= executeQuery(SQL);
		if(crs!=null){
			try {
				if(crs.next()){
					orderHeader=createOrderFromRecordSet(crs,false);
				}
			} catch (SQLException e) {
				PosLog.write(this, "getOrderHeader", e);
				throw new Exception("Failed to get order details.");
			}finally{
				try {
					crs.close();
				} catch (SQLException e) {
					PosLog.write(this, "getOrderHeader", e);
					throw new Exception("Failed to get order details.");
				}
			}
		}
		return orderHeader;
	}

	/**
	 * @param mNextPosDate
	 */
	public void clearTransactionData(String mNextPosDate){
		 
		ArrayList<String> mOrderHeaderList=null;
		String posDate = mNextPosDate;
		int RefundDays = (PosEnvSettings.getInstance().getNumberOfDaysToKeepTXNData()<=3)?3:PosEnvSettings.getInstance().getNumberOfDaysToKeepTXNData();
		String where =PosEnvSettings.getInstance().getApplicationType().equals(ApplicationType.StandAlone)?"":
				"not(status in ("+PosOrderStatus.Closed.getCode()+","+PosOrderStatus.Refunded.getCode()+") and hdr.sync_status in(0,2,3))and ";
		
	
		where = where + 
				" CASE WHEN hdr.status=" + PosOrderStatus.Refunded.getCode() + " THEN refund_date ELSE closing_date END  <= '"
				+PosDateUtil.getNextDate(posDate, -RefundDays)+"'";
		try {
			mOrderHeaderList = getOrderIDsToPurge(where);

			if(mOrderHeaderList!=null){
				for(String orderId:mOrderHeaderList){
					deleteOrderData(orderId);
				}
			}
			
			mOrderCustomerProvider.deleteDuplicatedCustomers();
		} catch (Exception e) {
			PosLog.write(this, "clearTransactionData", e);
		}
	}
	/**
	 * @param date
	 */
	public StringBuffer addTransactionDataToHistoryTable(String date){
		
		StringBuffer orderSqlStatements = new StringBuffer();

		 
//		String posDate = mNextPosDate;
//		int RefundDays = (PosEnvSettings.getInstance().getNumberOfDaysToKeepTXNData()<=3)?3:PosEnvSettings.getInstance().getNumberOfDaysToKeepTXNData();
//		String where ="    closing_date <= '"+PosDateUtil.getNextDate(posDate, -RefundDays)+"'";
		
		String where ="  CASE WHEN hdr.status=" + PosOrderStatus.Refunded.getCode() + " THEN refund_date ELSE closing_date END  <= '"+  date +"'";

		try {
			final String orderIds = getOrderIDStringToPurge(where);

			if(!orderIds.trim().equals("")){
				
				where =" order_id in (" + orderIds + ")";
				orderSqlStatements.append(addToHistory("order_hdrs", where));
				orderSqlStatements.append(mOrderDtlProvider.addToHistory("order_dtls" , where));
				orderSqlStatements.append(mOrderCustomerProvider.addToHistory(where));
				orderSqlStatements.append(mOrderPaymentHdrProvider.addToHistory("order_payment_hdr",where));
				orderSqlStatements.append(mOrderPaymentsProvider.addToHistory("order_payments",where));
				orderSqlStatements.append(mOrderDiscountProvider.addToHistory(where));
				orderSqlStatements.append(mServingTableProvider.addToHistory("order_serving_tables", where));
				orderSqlStatements.append(mServingSeatProvider.addToHistory("order_serving_seats",where));
				
				orderSqlStatements.append(mOrderSplitProvider.addToHistory("order_splits",where));
				orderSqlStatements.append(mOrderQueueProvider.addToHistory(where));
				orderSqlStatements.append(mOrderPreDiscountProvider.addToHistory(where));
				orderSqlStatements.append(mOrderRefundProvider.addToHistory(where));
			}
		} catch (Exception e) {
			PosLog.write(this, "addTransactionDataToHistoryTable", e);
		}
		return orderSqlStatements;
	}
	
//	
//	private void copyToHistory(String orderId) throws SQLException {
//		final String where= " order_id='"+orderId+"'";
//		try {	
//			
//			if(isExist("order_hdrs_hist",where))
//				return;
//			
//			mConnection.setAutoCommit(false);
//			
//			addToHistory("order_hdrs", where);
//			mOrderDtlProvider.addToHistory("order_dtls" , where);
//			mOrderCustomerProvider.addToHistory(where);
//			mOrderPaymentHdrProvider.addToHistory(where);
//			mOrderPaymentsProvider.addToHistory(where);
//			mOrderDiscountProvider.addToHistory(where);
//			mServingTableProvider.addToHistory( where);
//			mServingSeatProvider.addToHistory(where);
//			
//			mOrderSplitProvider.addToHistory(where);
//			mOrderQueueProvider.addToHistory(where);
//			mOrderPreDiscountProvider.addToHistory(where);
//			mOrderRefundProvider.addToHistory(where);
//			
//			
//			mConnection.commit();
//			mConnection.setAutoCommit(true);
//		} catch (Exception e) {
//			
//			mConnection.rollback();
//			
//			throw e;
//		} 
//	} 
	/**
	 * @param where
	 * @return
	 */
	private ArrayList<String> getOrderIDs(String where) {
		ArrayList<String> orderList=null;
		CachedRowSet crs=executeQuery("select order_id from order_hdrs where "+where);
		if(crs!=null){
			orderList=new ArrayList<String>();
			try {
				while(crs.next()){
					String orderId=crs.getString("order_id");
					orderList.add(orderId);
				}
			} catch (Exception e) {
				PosLog.write(this, "getOrders", e);
			}finally{
				try {
					crs.close();
				} catch (SQLException e) {
					PosLog.write(this, "getOrders", e);
				}
			}
		}
		return orderList;
	}

	/**
	 * @param where
	 * @return
	 */
	private String getOrderIDStringToPurge(String where) {
		
		
		final String sql="SELECT DISTINCT hdr.order_id as order_id " +
						 " FROM order_hdrs hdr " +  
						 " LEFT JOIN order_refunds ref on hdr.order_id=ref.order_id " + 
						 " where "+where ; 
		
		CachedRowSet crs=executeQuery(sql);
		
		String orderIds="";
		if(crs!=null){
			try {
				while(crs.next()){
					
					orderIds= orderIds+ (orderIds.trim().equals("")?"":",");
					orderIds= orderIds+ "'" +  crs.getString("order_id") + "'";
				}
			} catch (Exception e) {
				PosLog.write(this, "getOrderIDsToPurge", e);
			}finally{
				try {
					crs.close();
				} catch (SQLException e) {
					PosLog.write(this, "getOrderIDsToPurge", e);
				}
			}
		}
		return orderIds;
	}
	/**
	 * @param where
	 * @return
	 */
	private ArrayList<String> getOrderIDsToPurge(String where) {
		
		ArrayList<String> orderList=null;
		
		final String sql="SELECT DISTINCT hdr.order_id as order_id " +
						 " FROM order_hdrs hdr " +  
						 " LEFT JOIN order_refunds ref on hdr.order_id=ref.order_id " + 
						 " where "+where ; 
		
		CachedRowSet crs=executeQuery(sql);
		if(crs!=null){
			orderList=new ArrayList<String>();
			try {
				while(crs.next()){
					String orderId=crs.getString("order_id");
					orderList.add(orderId);
				}
			} catch (Exception e) {
				PosLog.write(this, "getOrderIDsToPurge", e);
			}finally{
				try {
					crs.close();
				} catch (SQLException e) {
					PosLog.write(this, "getOrderIDsToPurge", e);
				}
			}
		}
		return orderList;
	}

	/**
	 * Returns the payment entries for the given order. 
	 * @param orderObject
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<BeanOrderPayment> getOrderPayments(BeanOrderHeader orderObject) throws SQLException{

		return getOrderPayments(orderObject.getOrderId());
	}

	/**
	 * @param orderID
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<BeanOrderPayment> getOrderPayments(String orderID) throws SQLException{

		return 	mOrderPaymentsProvider.getOrderPayments(orderID);
	}

	/**
	 * @param orderObject
	 * @return
	 * @throws Exception 
	 */
	public ArrayList<BeanOrderSplit> getOrderSPlits(BeanOrderHeader orderObject) throws Exception{

		return getOrderSPlits(orderObject.getOrderId());
	}

	/**
	 * @param orderID
	 * @return
	 * @throws Exception 
	 */
	public ArrayList<BeanOrderSplit> getOrderSPlits(String orderID) throws Exception{

		return mOrderSplitProvider.getSplits(orderID);
	}

	/**
	 * @param mOrderItem
	 * @throws SQLException 
	 */
	public void finishOrder(BeanOrderHeader mOrderItem) throws SQLException {
		
		finishOrder(mOrderItem.getOrderId());
	}
	
	/**
	 * @param mOrderItem
	 * @throws SQLException 
	 */
	public void finishOrder(String orderId ) throws SQLException {
		
		final String sql="update order_hdrs set is_locked=0 where order_id='"+orderId+"'";
		executeNonQuery(sql);
	}


	/**
	 * @param orderID
	 * @return
	 * @throws SQLException 
	 */
	public boolean  getLock(String orderID,String shopCode) throws SQLException{
		
		boolean hasLockGained=false;
		final String sql="update order_hdrs set is_locked=1, locked_station_code='"+ shopCode +"' where order_id='" + orderID + "' and (is_locked=0 OR (is_locked =1 and locked_station_code='"+ shopCode +"')) ";
		final int updatedCount=executeNonQuery(sql);

		if(updatedCount>0)
			hasLockGained=true;

		return hasLockGained;
	}


	/**
	 * @param orderID
	 * @return
	 * @throws SQLException 
	 */
	public void  saveRefundAmount(BeanOrderHeader orderHeader) throws SQLException{
 
		final String sql="update order_hdrs set  status="  + orderHeader.getStatus().getCode() +  ", " +
				" refund_amount= "  + orderHeader.getRefundAmount() + "," +
				" refund_total_tax1= "  + orderHeader.getRefundTotalAmountTax1() + "," + 
				" refund_total_tax2= "  + orderHeader.getRefundTotalAmountTax2() + "," + 
				" refund_total_tax3= "  + orderHeader.getRefundTotalAmountTax3() + "," + 
				" refund_total_gst= "  + orderHeader.getRefundTotalAmountGST() + "," + 
				" refund_total_sc= "  + orderHeader.getRefundTotalAmountServiceTax() + "," + 
				" sync_status=0 where order_id='" + orderHeader.getOrderId() + "'";
		executeNonQuery(sql);
	 
	}
	

	/*
	 * 		
	 */
	public String getOpeningInvoiceNo(String where) throws Exception{
		
		int startingInvNo=0;
		final String sql="SELECT MIN(invoice_no) as opening_invno  FROM order_hdrs  where " + where;
		CachedRowSet crs=executeQuery(sql);
 
		if(crs!=null){
			try {
				if(crs.next())
					startingInvNo=crs.getInt("opening_invno");
			} catch (Exception e) {
				PosLog.write(this, "getOpeningInvoiceNo", e);
				throw new Exception("Failed to get opening invoice no");
			}finally{
				try {
					crs.close();
				} catch (SQLException e) {
					PosLog.write(this, "getOpeningInvoiceNo", e);
					throw new Exception("Failed to get opening invoice no");
				}
			}
		}
		return  PosOrderUtil.getFormatedInvoiceNumber(startingInvNo);
	}
	
	/*
	 * 		
	 */
	public String getClosingInvoiceNo(String where) throws Exception{
		
		int closingInvNo=0;
		final String sql="SELECT MAX(invoice_no) as closing_invno FROM order_hdrs  where " + where;
		CachedRowSet crs=executeQuery(sql);
 
		if(crs!=null){
			try {
				if(crs.next())
					closingInvNo=crs.getInt("closing_invno");
			} catch (Exception e) {
				PosLog.write(this, "getClosingInvoiceNo", e);
				throw new Exception("Failed to get closing invoice no");
			}finally{
				try {
					crs.close();
				} catch (SQLException e) {
					PosLog.write(this, "getClosingInvoiceNo", e);
					throw new Exception("Failed to get closing invoice no");
				}
			}
		}
		return  PosOrderUtil.getFormatedInvoiceNumber(closingInvNo);
	}
}
