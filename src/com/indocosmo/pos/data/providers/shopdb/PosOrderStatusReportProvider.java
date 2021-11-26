/**
 * 
 */
package com.indocosmo.pos.data.providers.shopdb;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.sql.rowset.CachedRowSet;

import com.indocosmo.pos.common.PosLog;
import com.indocosmo.pos.common.enums.PosOrderServiceTypes;
import com.indocosmo.pos.common.utilities.PosOrderUtil;
import com.indocosmo.pos.data.beans.BeanEmployees;
import com.indocosmo.pos.data.beans.BeanOrderHeader.PosOrderStatus;
import com.indocosmo.pos.data.beans.BeanOrderStatusReport;

/**
 * @author jojesh
 *
 */
public class PosOrderStatusReportProvider extends PosShopDBProviderBase {

	 private PosWaiterProvider mWaiterProvider;
	 private PosUsersProvider mUserProvider;
	 private PosOrderServingTableProvider mServingTableProvider;	
	 private PosOrderPreDiscountProvider mOrderPreDiscountProvider;
	/**
	 * 
	 */
	public PosOrderStatusReportProvider() {
		super("order_id","v_order_hdrs");

		mWaiterProvider=new PosWaiterProvider();
		mUserProvider=new PosUsersProvider();
		mServingTableProvider=new PosOrderServingTableProvider();	
		mOrderPreDiscountProvider = new PosOrderPreDiscountProvider();
	}


	/**
	 * @param orderID
	 * @return
	 * @throws Exception
	 */
	public BeanOrderStatusReport getOrderData(String orderID) throws Exception{
		BeanOrderStatusReport orderHeader=null;
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
	 * @param crs
	 * @param isHeaderOnly
	 * @return
	 * @throws Exception
	 */
	private BeanOrderStatusReport createOrderFromRecordSet(CachedRowSet crs) throws Exception{

		BeanOrderStatusReport orderObject=new BeanOrderStatusReport();
		orderObject.setSelected(false); 
		orderObject.setOrderId(crs.getString("order_id"));
		orderObject.setQueueNo(String.valueOf(crs.getInt("queue_no")));	
		orderObject.setInvoiceNo(PosOrderUtil.getFormatedInvoiceNumber( crs.getInt("invoice_no")) );	
		orderObject.setOrderDate(crs.getString("order_date"));
		orderObject.setOrderTime(crs.getString("order_time"));
		
		orderObject.setTotalAmount(crs.getDouble("total_amount"));
		
		orderObject.setExtraCharges(crs.getDouble("extra_charges"));
		orderObject.setExtraChargeTaxOneAmount(crs.getDouble("extra_charge_tax1_amount"));
		orderObject.setExtraChargeTaxTwoAmount(crs.getDouble("extra_charge_tax2_amount"));
		orderObject.setExtraChargeTaxThreeAmount(crs.getDouble("extra_charge_tax3_amount"));
		orderObject.setExtraChargeGSTAmount(crs.getDouble("extra_charge_gst_amount"));
		
		orderObject.setDetailItemCount(crs.getInt("item_count"));
		orderObject.setDetailItemQuatity(crs.getDouble("total_qty"));
		orderObject.setAliasText(crs.getString("alias_text"));
		orderObject.setStatus(PosOrderStatus.get(crs.getInt("status")));
		orderObject.setClosingDate(crs.getString("closing_date"));
		orderObject.setClosingTime(crs.getString("closing_time"));
		 
		if (PosOrderServiceTypes.get(crs.getInt("service_type"))==PosOrderServiceTypes.TABLE_SERVICE ||
				PosOrderServiceTypes.get(crs.getInt("service_type"))==PosOrderServiceTypes.HOME_DELIVERY){
			
			final BeanEmployees waiter=mWaiterProvider.getWaiterById(crs.getInt("served_by"));
			orderObject.setServedBy( ( waiter!= null) ? waiter.getFirstName() : "");
		}else{
			orderObject.setServedBy(mUserProvider.getUserByID(crs.getInt("user_id")).getName());
		}
		
		orderObject.setTotalAmountPaid(crs.getDouble("total_amount_paid"));
		orderObject.setChangeAmount(crs.getDouble("total_balance"));
		orderObject.setCashOut(crs.getDouble("cash_out"));
		orderObject.setBillDiscountAmount(crs.getDouble("bill_discount_amount"));
		orderObject.setBillDiscountPercentage(crs.getDouble("bill_discount_percentage"));
		
		orderObject.setServiceTable(mServingTableProvider.getTableByID(crs.getInt("serving_table_id")));
		orderObject.setOrderServiceType(PosOrderServiceTypes.get(crs.getInt("service_type")));
		orderObject.setCustomerName(crs.getString("name"));
		orderObject.setCustomerPhoneNo(crs.getString("phone"));
		
		orderObject.setPreBillDiscount(mOrderPreDiscountProvider.getPreBillDiscount(orderObject.getOrderId()));
		
		
		return orderObject;
	}


	/**
	 * @param where
	 * @param isHederOnly
	 * @return
	 * @throws Exception
	 */
	public ArrayList<BeanOrderStatusReport> getOrderDetails(String where ) throws Exception{
		ArrayList<BeanOrderStatusReport> orderList=null;
		//		final String where=((status==null)?null:"status="+status.getCode());
		final String orderBy="  order_date desc, order_time desc";
		CachedRowSet crs=getData(where,orderBy);
		if(crs!=null){
			orderList=new ArrayList<BeanOrderStatusReport>();
			try {
				while(crs.next()){
					BeanOrderStatusReport orderObject=createOrderFromRecordSet(crs);
					orderList.add(orderObject);
				}
			} catch (SQLException e) {
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
	 * @return
	 * @throws Exception
	 */
	public ArrayList<BeanOrderStatusReport> getOrderHeaders(String where) throws Exception{
		return getOrderDetails(where );
	}
	 
}
