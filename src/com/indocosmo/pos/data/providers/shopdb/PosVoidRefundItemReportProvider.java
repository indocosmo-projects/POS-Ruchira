/**
 * 
 */
package com.indocosmo.pos.data.providers.shopdb;

import java.util.ArrayList;

import javax.sql.rowset.CachedRowSet;

import com.indocosmo.pos.common.PosLog;
import com.indocosmo.pos.common.enums.PosOrderServiceTypes;
import com.indocosmo.pos.common.utilities.PosOrderUtil;
import com.indocosmo.pos.data.beans.BeanReportVoidRefundItem;
import com.indocosmo.pos.data.beans.BeanOrderHeader.PosOrderStatus;

/**
 * @author sandhya
 *
 */
public class PosVoidRefundItemReportProvider extends PosShopDBProviderBase {
	
	
	PosUOMProvider 	mUOMProvider;
	PosUsersProvider mUserProvider;
	PosServiceTableProvider mServingTableProvider;
	
	public PosVoidRefundItemReportProvider() {
		
		super("");
		mUOMProvider=PosUOMProvider.getInstance();
		mUserProvider=new PosUsersProvider();
		mServingTableProvider=new PosServiceTableProvider();
	}
	 
	
	/**
	 * @param orderID
	 * @param parentId
	 * @return
	 * @throws Exception //String refundDate
	 */
	public ArrayList<BeanReportVoidRefundItem> getRefundList (String refundDate) throws Exception{
		
		ArrayList<BeanReportVoidRefundItem> detailList=null;
		 
		final String sql=" SELECT ohdr.order_id,ohdr.invoice_no,ohdr.invoice_prefix,"+  
				" odt.sale_item_code,odt.`name`,odt.uom_code,`oref`.`paid_amount` AS `refund_amount`, "+ 
				" `oref`.`qty` AS `refund_qty`,	`oref`.`tax1_amount` AS `tax1_amount`,`oref`.`tax2_amount` AS `tax2_amount`," + 
				" `oref`.`tax3_amount` AS `tax3_amount`,`oref`.`refunded_by` AS `refunded_by`," +
				" `oref`.`refund_date` AS `refund_date`," +
				" CASE WHEN ohdr.total_amount_paid-ohdr.total_balance-ohdr.cash_out=ohdr.refund_amount THEN " + 
				" extra_charges + extra_charge_tax1_amount + extra_charge_tax2_amount +extra_charge_tax3_amount + " + 
				" extra_charge_gst_amount ELSE 0 END as refund_extra_charge,ophdr.final_round_amount,ophdr.total_amount " + 
				" FROM order_dtls odt " + 
				"  JOIN order_hdrs ohdr on odt.order_id=ohdr.order_id " +
				" JOIN `order_refunds` `oref` ON  `odt`.`order_id` = `oref`.`order_id` " +
				" AND `odt`.`id` = `oref`.`order_dtl_id` " +
				" LEFT JOIN (SELECT order_id,sum(final_round_amount) as final_round_amount,sum(total_amount) as total_amount " + 
				"            from order_payment_hdr where payment_date='"+ refundDate + "' and is_refund=1 GROUP BY order_id) "+
					" ophdr on ohdr.order_id=ophdr.order_id " + 
				" WHERE oref.refund_date='"+ refundDate + "'order by ohdr.order_id" ; 
		 
		CachedRowSet crs=executeQuery(sql);
		try{
			if(crs!=null){
				detailList=new ArrayList<BeanReportVoidRefundItem>();
				while(crs.next()){
					BeanReportVoidRefundItem orderDetail=getOrderDetailsDataFromRecordSet(crs);
					detailList.add(orderDetail);
				}		
				crs.close();
			}
		}catch (Exception ex){
			PosLog.write(this, "getRefundList", ex);
			throw ex;
		}
		return detailList;	
	}

	/**
	 * @param crs
	 * @return
	 * @throws Exception
	 */
	private BeanReportVoidRefundItem getOrderDetailsDataFromRecordSet(CachedRowSet crs) throws Exception{
		
		PosUOMProvider 	UOMProvider=PosUOMProvider.getInstance();
		PosUsersProvider userProvider=new PosUsersProvider();
		
		BeanReportVoidRefundItem orderDetail=new BeanReportVoidRefundItem();
		orderDetail.setOrderId(crs.getString("order_id"));
		orderDetail.setInvoicePrefix(crs.getString("invoice_prefix"));
		orderDetail.setInvoiceNo(PosOrderUtil.getFormatedInvoiceNumber( crs.getInt("invoice_no")) );	
		
		orderDetail.setItemCode(crs.getString("sale_item_code"));
		orderDetail.setItemName(crs.getString("name"));
		
	 	orderDetail.setRefundAmount(crs.getDouble("refund_amount"));
	 	orderDetail.setRefundQuantity(crs.getDouble("refund_qty"));
		orderDetail.setTax1Amount(crs.getDouble("tax1_amount"));
		orderDetail.setTax2Amount(crs.getDouble("tax2_amount"));
		orderDetail.setTax3Amount(crs.getDouble("tax3_amount"));
		orderDetail.setUom(UOMProvider.getUom(crs.getString("uom_code")));
		orderDetail.setRefundedBy(userProvider.getUserByID(crs.getInt("refunded_by")));
		orderDetail.setRefundedAt(crs.getString("refund_date"));
		orderDetail.setRefundExtraCharge(crs.getDouble("refund_extra_charge"));
		
		
		orderDetail.setTotalRefundAmt(crs.getDouble("total_amount"));
		orderDetail.setTotalRoundingAdjustment(crs.getDouble("final_round_amount"));
		return orderDetail;
	}
 

	/**
	 * @param orderID
	 * @param parentId
	 * @return
	 * @throws Exception //String refundDate
	 */
	public ArrayList<BeanReportVoidRefundItem> getVoidItemList (String voidDate) throws Exception{
		
		ArrayList<BeanReportVoidRefundItem> detailList=null;
		String itemTotalColumn="";
		
		 		itemTotalColumn=" odt.net_amount + odt.tax1_amount+odt.tax2_amount+odt.tax3_amount ";
		 
		
//		final String where ="  odt.void_at>='" + voidDate + " 00:00:01' AND  odt.void_at<='" + voidDate + " 23:59:59'";
		final String sql=" SELECT ohdr.order_id,oq.id as queue_no,ohdr.invoice_no,ohdr.status,ohdr.service_type,ohdr.serving_table_id, " + 
				" ohdr.invoice_prefix,odt.sale_item_code,odt.name  , odt.uom_code ,"+
				" odt.qty,odt.item_total,odt.tax1_amount,odt.tax2_amount,odt.tax3_amount,"+
				" CASE WHEN ohdr.`status`="+ PosOrderStatus.Void.getCode() + " THEN ohdr.void_by ELSE odt.void_by END void_by,"+
				" CASE WHEN ohdr.`status`="+ PosOrderStatus.Void.getCode() + " THEN ohdr.void_at ELSE odt.void_at END void_at "+
				" FROM order_dtls odt " +
				" JOIN order_hdrs ohdr ON odt.order_id = ohdr.order_id "+
				" LEFT JOIN order_queue  oq ON ohdr.order_id =oq.order_id " +
				" WHERE ((odt.is_void=1 AND odt.void_at='" + voidDate + "' ) or "  +
				" (ohdr.status="+ PosOrderStatus.Void.getCode() + " AND ohdr.closing_date='" + voidDate + "')) "+
				" ORDER BY 	ohdr.order_id ";
	 
		CachedRowSet crs=executeQuery(sql);
		try{
			if(crs!=null){
				detailList=new ArrayList<BeanReportVoidRefundItem>();
				while(crs.next()){
					BeanReportVoidRefundItem orderDetail=getVoidDataFromRecordSet(crs);
					detailList.add(orderDetail);
				}		
				crs.close();
			}
		}catch (Exception ex){
			PosLog.write(this, "getVoidItemList", ex);
			throw ex;
		}
		return detailList;	
	}

	/**
	 * @param crs
	 * @return
	 * @throws Exception
	 */
	private BeanReportVoidRefundItem getVoidDataFromRecordSet(CachedRowSet crs) throws Exception{
		
		
		BeanReportVoidRefundItem orderDetail=new BeanReportVoidRefundItem();
		orderDetail.setOrderId(crs.getString("order_id"));
		orderDetail.setInvoicePrefix(crs.getString("invoice_prefix"));
		orderDetail.setQueueNo(Integer.toString(crs.getInt("queue_no") ));	
		orderDetail.setInvoiceNo(PosOrderUtil.getFormatedInvoiceNumber( crs.getInt("invoice_no")) );	
//		orderDetail.setOrderDate(crs.getString("order_date"));
		orderDetail.setStatus(PosOrderStatus.get(crs.getInt("status")));
		orderDetail.setItemCode(crs.getString("sale_item_code"));
		orderDetail.setItemName(crs.getString("name"));
		
	 	orderDetail.setItemTotal(crs.getDouble("item_total"));
	 	orderDetail.setQuantity(crs.getDouble("qty"));
		orderDetail.setTax1Amount(crs.getDouble("tax1_amount"));
		orderDetail.setTax2Amount(crs.getDouble("tax2_amount"));
		orderDetail.setTax3Amount(crs.getDouble("tax3_amount"));
		orderDetail.setUom(mUOMProvider.getUom(crs.getString("uom_code")));
		
		orderDetail.setVoidBy(mUserProvider.getUserByID(crs.getInt("void_by")));
		orderDetail.setVoidAt(crs.getString("void_at"));
		orderDetail.setServiceType(PosOrderServiceTypes.get(crs.getInt("service_type")));
		orderDetail.setServiceTable(mServingTableProvider.getTableByID(crs.getInt("serving_table_id")));
		
		return orderDetail;
	}
}
