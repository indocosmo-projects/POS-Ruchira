/**
 * 
 */
package com.indocosmo.pos.data.providers.shopdb;

import java.util.ArrayList;

import javax.sql.rowset.CachedRowSet;

import com.indocosmo.pos.common.PosLog;
import com.indocosmo.pos.common.enums.PosOrderServiceTypes;
import com.indocosmo.pos.common.utilities.PosOrderUtil;
import com.indocosmo.pos.data.beans.BeanOrderHeader.PosOrderStatus;
import com.indocosmo.pos.data.beans.BeanPayModeBase.PaymentMode;
import com.indocosmo.pos.data.beans.BeanSalesOrderReport;
import com.indocosmo.pos.forms.components.objectbrowser.IPosBrowsableItem;

/**
 * @author sandhya
 *
 */
public class PosSalesOrderReportProvider  extends PosShopDBProviderBase {
 
	/**
	 * 
	 */
	public PosSalesOrderReportProvider() {
		
		super("order_id","v_order_hdrs");
		
	}
 
	/**
	 * @param crs
	 * @return
	 * @throws Exception
	 */
	public ArrayList<BeanSalesOrderReport> getDailySalesOrderAdvance(String reportDate,
			ArrayList<PosOrderServiceTypes> mSelectedServices) throws Exception{
		 
//		final String where = " due_datetime>='" + deliveryDate + " 00:00' AND  due_datetime<='" + deliveryDate + " 23:59'";
		final String where = " pay.payment_date='" + reportDate + "' " + getOrderServiceCriteria(mSelectedServices);
		final String sql="SELECT hdr.invoice_no,hdr.status, hdr.queue_no,hdr.order_id,cust.name as cust_name,cust.phone,hdr.due_datetime," + 
						" dtl.name AS item_name,dtl.qty," + 
						" pay.paid_amount as advance_amount, pay.payment_mode as advance_mode " + 
						" FROM v_order_hdrs hdr " + 
						" JOIN order_customers cust ON hdr.order_id=cust.order_id " +  
						" JOIN order_dtls dtl ON hdr.order_id=dtl.order_id " + 
						" LEFT JOIN v_order_payments pay " +
						" 		ON hdr.order_id=pay.order_id AND pay.is_advance " +
						" WHERE dtl.is_void <> 1 and "  + where + " ORDER BY hdr.order_id,dtl.name" ;
		
		
		ArrayList<BeanSalesOrderReport> salesOrderList=null;
		CachedRowSet crs= executeQuery(sql);
		
		try{
			if(crs!=null){
				salesOrderList=new ArrayList<BeanSalesOrderReport>();
				while(crs.next()){
					BeanSalesOrderReport orderDetail=getSODailyAdvanceFromRecordSet(crs);
					salesOrderList.add(orderDetail);
				}		
				crs.close();
			}
		}catch (Exception ex){
			PosLog.write(this, "getDailySalesOrderAdvance", ex);
		}
		return salesOrderList;	
	}

	/**
	 * @param crs
	 * @return
	 * @throws Exception
	 */
	public ArrayList<BeanSalesOrderReport> getDailySalesOrderBalance(String reportDate,
			ArrayList<PosOrderServiceTypes> mSelectedServices,boolean isPendingBalance) throws Exception{
		 
		final String where ;
		
		final String sqlTotalAmt=" (hdr.total_amount + hdr.extra_charges + IFNULL(hdr.extra_charge_tax1_amount,0) +  " +
						" IFNULL(hdr.extra_charge_tax2_amount,0)  + IFNULL(hdr.extra_charge_tax3_amount,0) + " +
						" IFNULL(hdr.extra_charge_gst_amount,0))  ";
		if(isPendingBalance)
			where = " due_datetime>='" + reportDate + " 00:00' AND  due_datetime<='" + reportDate + " 23:59' " +
					" AND " + 
					"( " + sqlTotalAmt + " - CASE WHEN  pre_disc.is_percentage IS NULL THEN 0 WHEN pre_disc.is_percentage THEN " +
					    " "+ sqlTotalAmt + "*pre_disc.price/100 " + 
						"	ELSE pre_disc.price END  )  + hdr.final_round_amount  - " +
					 "     IFNULL(adv_pay.paid_amount,0) - ( " +
					 "  IFNULL(payHdr.total_amount_paid,0) -  IFNULL(payHdr.total_balance,0) -  IFNULL(payHdr.cash_out,0))>0 ";
		else
			where = " pay.payment_date='" + reportDate + "' " +
					" AND  payHdr.total_amount_paid- payHdr.total_balance-payHdr.cash_out-ifnull(payHdrRefund.total_amount_paid,0) >0 ";
//		final String 
//		final String sql="SELECT hdr.invoice_no,hdr.status, hdr.queue_no,hdr.order_id,cust.name as cust_name,cust.phone,hdr.due_datetime," + 
//						" dtl.name AS item_name,dtl.qty," + 
//						" payHdr.total_amount_paid- payHdr.total_balance-payHdr.cash_out as balance_amount, pay.payment_mode as balance_pay_mode " + 
//						" FROM v_order_hdrs hdr " + 
//						" JOIN order_customers cust ON hdr.order_id=cust.order_id " +  
//						" JOIN order_dtls dtl ON hdr.order_id=dtl.order_id " + 
//						" INNER JOIN v_order_payment_hdr payHdr ON hdr.order_id=payHdr.order_id AND NOT payHdr.is_advance" + 
//						" INNER JOIN v_order_payments pay " +
//						" 		ON payHdr.id=pay.order_payment_hdr_id  " +
//						" 		AND pay.payment_mode not in (" + PaymentMode.Balance.getValue() + "," + PaymentMode.CashOut.getValue() + ") " +
//						" WHERE  dtl.is_void <> 1 " + 
//						" and hdr.service_type=" + PosOrderServiceTypes.SALES_ORDER.getCode()  + " AND " + where;
		
		
		final String sql="SELECT hdr.invoice_no,hdr.status, hdr.queue_no,hdr.order_id,"+
				" cust.name as cust_name,cust.phone,hdr.due_datetime,delivery_type," + 
				" dtl.name AS item_name,dtl.qty,dtl.remarks," + 
				" payHdr.total_amount_paid- payHdr.total_balance-payHdr.cash_out  as balance_received, pay.payment_mode as balance_pay_mode, " +
				" ifnull(payHdrRefund.total_amount_paid,0) as refund_amount, " + 
				" adv_pay.paid_amount as advance_amount, adv_pay.payment_mode as advance_pay_mode, " + 
				sqlTotalAmt + "  - CASE WHEN  pre_disc.is_percentage IS NULL THEN 0 WHEN  pre_disc.is_percentage THEN " +
				sqlTotalAmt + "*pre_disc.price/100 " + 
				"	ELSE pre_disc.price END  AS total_amount " +
				" FROM v_order_hdrs hdr " + 
				" JOIN order_customers cust ON hdr.order_id=cust.order_id " +  
				" JOIN order_dtls dtl ON hdr.order_id=dtl.order_id " + 
				" LEFT JOIN order_pre_discounts pre_disc ON hdr.order_id=pre_disc.order_id " + 
				" LEFT JOIN v_order_payment_hdr payHdr ON hdr.order_id=payHdr.order_id AND NOT payHdr.is_advance  and  NOT payHdr.is_refund" + 
				" LEFT JOIN v_order_payments pay " +
				" 		ON payHdr.id=pay.order_payment_hdr_id " +
				" 		AND pay.payment_mode not in (" + PaymentMode.Balance.getValue() + "," + PaymentMode.CashOut.getValue() + ") " +
				" LEFT JOIN v_order_payments adv_pay " +
				" 		ON hdr.order_id=adv_pay.order_id AND   adv_pay.is_advance " +
				"  LEFT JOIN (SELECT SUM(total_amount_paid) total_amount_paid,order_id,is_refund  FROM v_order_payment_hdr " + 
				" 	GROUP BY order_id, is_refund  )payHdrRefund ON hdr.order_id = payHdrRefund.order_id " + 
				" 	AND payHdrRefund.is_refund " + 	
				" WHERE  dtl.is_void <> 1 " + getOrderServiceCriteria(mSelectedServices) + 
					" AND " + where  + " ORDER BY hdr.order_id,dtl.name" ;
		
		ArrayList<BeanSalesOrderReport> salesOrderList=null;
		CachedRowSet crs= executeQuery(sql);
		
		try{
			if(crs!=null){
				salesOrderList=new ArrayList<BeanSalesOrderReport>();
				while(crs.next()){
					BeanSalesOrderReport orderDetail=getSODailyBalanceDataFromRecordSet(crs);
					salesOrderList.add(orderDetail);
				}		
				crs.close();
			}
		}catch (Exception ex){
			PosLog.write(this, "getDailySalesOrderBalance", ex);
		}
		return salesOrderList;	
	}
	/**
	 * @param crs
	 * @return
	 * @throws Exception
	 */
	public ArrayList<BeanSalesOrderReport> getItemWiseSalesOrder(String deliveryDate,
			ArrayList<PosOrderServiceTypes> mSelectedServices) throws Exception{
		 
		final String where = " due_datetime>='" + deliveryDate + " 00:00' AND  due_datetime<='" + deliveryDate + " 23:59'";
		final String sql="SELECT hdr.invoice_no,hdr.status, hdr.queue_no,hdr.order_id,cust.name as cust_name,cust.phone,hdr.due_datetime," + 
						" dtl.name AS item_name,dtl.qty,dtl.remarks " + 
						" FROM v_order_hdrs hdr " + 
						" JOIN order_customers cust ON hdr.order_id=cust.order_id " +  
						" JOIN order_dtls dtl ON hdr.order_id=dtl.order_id " + 
						" WHERE  dtl.is_void <> 1 " + getOrderServiceCriteria(mSelectedServices) + " AND " + where +
						   " ORDER BY hdr.due_datetime,hdr.order_id ,dtl.name" ;
		
		
		ArrayList<BeanSalesOrderReport> salesOrderList=null;
		CachedRowSet crs= executeQuery(sql);
		
		try{
			if(crs!=null){
				salesOrderList=new ArrayList<BeanSalesOrderReport>();
				while(crs.next()){
					BeanSalesOrderReport orderDetail=getSOItemWiseDataFromRecordSet(crs);
					salesOrderList.add(orderDetail);
				}		
				crs.close();
			}
		}catch (Exception ex){
			PosLog.write(this, "getItemWiseSalesOrder", ex);
		}
		return salesOrderList;	
	}

 
	/**
	 * @param crs
	 * @return
	 * @throws Exception
	 */
	public ArrayList<BeanSalesOrderReport> getItemWiseSalesOrderDetailedReport(String deliveryDate,
			ArrayList<PosOrderServiceTypes> mSelectedServices) throws Exception{
		 
		final String where = " due_datetime>='" + deliveryDate + " 00:00' AND  due_datetime<='" + deliveryDate + " 23:59'";
		final String sql="SELECT hdr.invoice_no,hdr.status, hdr.queue_no,hdr.order_id,cust.name as cust_name,cust.phone,hdr.due_datetime,delivery_type," + 
						" dtl.name AS item_name,dtl.qty,dtl.remarks," + 
						" payHdr.total_amount_paid- payHdr.total_balance-payHdr.cash_out-ifnull(payHdrRefund.total_amount_paid,0) as balance_received, pay.payment_mode as balance_pay_mode, " + 
						" adv_pay.paid_amount as advance_amount, adv_pay.payment_mode as advance_pay_mode " + 
						" FROM v_order_hdrs hdr " + 
						" JOIN order_customers cust ON hdr.order_id=cust.order_id " +  
						" JOIN order_dtls dtl ON hdr.order_id=dtl.order_id " + 
						" LEFT JOIN v_order_payment_hdr payHdr ON hdr.order_id=payHdr.order_id AND NOT payHdr.is_advance AND NOT payHdr.is_refund" + 
						" LEFT JOIN v_order_payments pay " +
						" 		ON payHdr.id=pay.order_payment_hdr_id " +
						" 		AND pay.payment_mode not in (" + PaymentMode.Balance.getValue() + "," + PaymentMode.CashOut.getValue() + ") " +
						" LEFT JOIN v_order_payments adv_pay " +
						" 		ON hdr.order_id=adv_pay.order_id AND   adv_pay.is_advance " +
						"  LEFT JOIN (SELECT SUM(total_amount_paid) total_amount_paid,order_id,is_refund  FROM v_order_payment_hdr " + 
						"  GROUP BY order_id, is_refund  )payHdrRefund ON hdr.order_id = payHdrRefund.order_id " +
						"  AND payHdrRefund.is_refund " + 
						" WHERE  dtl.is_void <> 1 " + getOrderServiceCriteria(mSelectedServices) + 
							" AND " + where + 
							" ORDER BY hdr.due_datetime ,hdr.order_id ,dtl.name" ;
		
		// and  hdr.service_type=" + PosOrderServiceTypes.SALES_ORDER.getCode()  +
		ArrayList<BeanSalesOrderReport> salesOrderList=null;
		CachedRowSet crs= executeQuery(sql);
		
		try{
			if(crs!=null){
				salesOrderList=new ArrayList<BeanSalesOrderReport>();
				while(crs.next()){
					BeanSalesOrderReport orderDetail=getSOItemWiseDetDataFromRecordSet(crs);
					salesOrderList.add(orderDetail);
				}		
				crs.close();
			}
		}catch (Exception ex){
			PosLog.write(this, "getItemWiseDetailedSalesOrder", ex);
		}
		return salesOrderList;	
	}
	
	private String getOrderServiceCriteria(ArrayList<PosOrderServiceTypes> mSelectedServices){
		
		String result="";
		
		if(mSelectedServices!=null && mSelectedServices.size()>0){
			for(PosOrderServiceTypes serviceType:mSelectedServices){
				result=result + (result.trim().equals("")?"":",") + serviceType.getCode();
			}
			if(!result.trim().equals(""))
				result=" and  hdr.service_type IN (" + result + ")";
		}
		return result;
	}

	/**
	 * @param crs
	 * @return
	 * @throws Exception
	 */
	private BeanSalesOrderReport getSODetailsFromRecordSet(CachedRowSet crs) throws Exception{
		
		BeanSalesOrderReport orderDetail=new BeanSalesOrderReport();
		orderDetail.setOrderId(crs.getString("order_id"));
		orderDetail.setQueueNo(PosOrderUtil.getFormatedOrderQueueNo(crs.getInt("queue_no"), null, null));
		orderDetail.setStatus(PosOrderStatus.get(crs.getInt("status")));
		if(orderDetail.getStatus()==PosOrderStatus.Closed || orderDetail.getStatus()==PosOrderStatus.Refunded)
			orderDetail.setInvoiceNo(PosOrderUtil.getFormatedInvoiceNumber(crs.getInt("invoice_no")));
		orderDetail.setCustomerName(crs.getString("cust_name"));
		orderDetail.setCustomerPhoneNumber(crs.getString("phone"));
		orderDetail.setDueDateTime(crs.getString("due_datetime"));
		orderDetail.setItemName(crs.getString("item_name"));
		orderDetail.setQuantity(crs.getDouble("qty")); 
		return orderDetail;
	}
	/**
	 * @param crs
	 * @return
	 * @throws Exception
	 */
	private BeanSalesOrderReport getSODailyAdvanceFromRecordSet(CachedRowSet crs) throws Exception{
		
		BeanSalesOrderReport orderDetail=getSODetailsFromRecordSet(crs);
		orderDetail.setAdvancePaymentMode(PaymentMode.get(crs.getInt("advance_mode")));
		orderDetail.setAdvanceAmount(crs.getDouble("advance_amount"));
		return orderDetail;
	}
	
	/**
	 * @param crs
	 * @return
	 * @throws Exception
	 */
	private BeanSalesOrderReport getSODailyBalanceDataFromRecordSet(CachedRowSet crs) throws Exception{
		
		BeanSalesOrderReport orderDetail=getSODetailsFromRecordSet(crs);
		orderDetail.setBalancePaymentMode(PaymentMode.get(crs.getInt("balance_pay_mode")));
		orderDetail.setBalanceReceived(crs.getDouble("balance_received"));
		orderDetail.setTotalAmount(crs.getDouble("total_amount"));
		orderDetail.setAdvanceAmount(crs.getDouble("advance_amount"));
		orderDetail.setRefundAmount(crs.getDouble("refund_amount"));
		orderDetail.setDueAmount(orderDetail.getTotalAmount()-orderDetail.getAdvanceAmount()-orderDetail.getBalanceReceived()-orderDetail.getRefundAmount());
		return orderDetail;
	}
	/**
	 * @param crs
	 * @return
	 * @throws Exception
	 */
	private BeanSalesOrderReport getSOItemWiseDataFromRecordSet(CachedRowSet crs) throws Exception{
		
		BeanSalesOrderReport orderDetail=getSODetailsFromRecordSet(crs);
		orderDetail.setItemRemarks(crs.getString("remarks"));
		return orderDetail;
	}
	/**
	 * @param crs
	 * @return
	 * @throws Exception
	 */
	private BeanSalesOrderReport getSOItemWiseDetDataFromRecordSet(CachedRowSet crs) throws Exception{
		
		BeanSalesOrderReport orderDetail=getSODetailsFromRecordSet(crs);
		orderDetail.setItemRemarks(crs.getString("remarks"));
//		orderDetail.setBalancePaymentMode(PaymentMode.get(crs.getInt("balance_pay_mode")));
		orderDetail.setBalanceReceived(crs.getDouble("balance_received"));
//		orderDetail.setAdvancePaymentMode(PaymentMode.get(crs.getInt("advance_mode")));
		orderDetail.setAdvanceAmount(crs.getDouble("advance_amount"));
		orderDetail.setDeliveryType(PosOrderServiceTypes.get(crs.getInt("delivery_type")));
		return orderDetail;
	}

}
