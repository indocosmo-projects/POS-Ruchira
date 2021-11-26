/**
 * 
 */
package com.indocosmo.pos.data.beans;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import com.indocosmo.pos.common.PosLog;
import com.indocosmo.pos.common.utilities.PosUomUtil;
import com.indocosmo.pos.data.beans.BeanPayModeBase.PaymentMode;
import com.indocosmo.pos.data.beans.BeanStockInHeader.StockInType;
import com.indocosmo.pos.data.providers.shopdb.PosDiscountItemProvider;

/**
 * @author jojesh
 *
 */
public final class BeanOrderRefund implements Cloneable{

	private int mId;
	private String mOrderId;
	private BeanOrderDetail mOrderDetail;
	private String mOrderPaymentId;
	private double mPaidAmount;
	private double tax1Amount;
	private double tax2Amount;
	private double tax3Amount;
	private double taxSCAmount;
	private double taxGSTAmount; 
	private double mQuantity = 1;
	private String mRefundDate;
	private String mRefundTime;
	private BeanUser mRefundedBy;
	
@Override
	public BeanOrderRefund clone(){
		BeanOrderRefund cloneObject = null;
		try {
			cloneObject = (BeanOrderRefund) super.clone();
		} catch (CloneNotSupportedException e) {
			PosLog.write(this,"clone",e);
		}
		return cloneObject;
	}
	
	/**
	 * @return the mId
	 */
	public int getId() {
		return mId;
	}
	/**
	 * @param Id the mId to set
	 */
	public void setId(int id) {
		this.mId = id;
	}

	/**
	 * @return the mOrderId
	 */
	public String getOrderId() {
		return mOrderId;
	}
	
	/**
	 * @param OrderId the mOrderId to set
	 */
	public void setOrderId(String orderId) {
		this.mOrderId = orderId;
	}
	/**
	 * @return the mOrderdetId
	 */
	public BeanOrderDetail getOrderDetail() {
		return mOrderDetail;
	}

	/**
	 * @param mOrderdetId the mOrderdetId to set
	 */
	public void setOrderDetail(BeanOrderDetail orderDetail) {
		this.mOrderDetail = orderDetail;
		
	}

	/**
	 * @return the mOrderPayId
	 */
	public String getOrderPaymentId() {
		return mOrderPaymentId;
	}

	/**
	 * @param mOrderPayId the mOrderPayId to set
	 */
	public void setOrderPaymentId(String mOrderPayId) {
		this.mOrderPaymentId = mOrderPayId;
	}


	/**
	 * @return the mPaidAmount
	 */
	public double getPaidAmount() {
		return mPaidAmount;
	}

	/**
	 * @param mPaidAmount the mPaidAmount to set
	 */
	public void setPaidAmount(double mPaidAmount) {
		this.mPaidAmount = mPaidAmount;
	}

	/**
	 * @return the tax1Amount
	 */
	public double getTax1Amount() {
		return tax1Amount;
	}
	/**
	 * @param tax1Amount the tax1Amount to set
	 */
	public void setTax1Amount(double tax1Amount) {
		this.tax1Amount = tax1Amount;
	}
	/**
	 * @return the tax2Amount
	 */
	public double getTax2Amount() {
		return tax2Amount;
	}
	/**
	 * @param tax2Amount the tax2Amount to set
	 */
	public void setTax2Amount(double tax2Amount) {
		this.tax2Amount = tax2Amount;
	}
	/**
	 * @return the tax3Amount
	 */
	public double getTax3Amount() {
		return tax3Amount;
	}
	/**
	 * @param tax3Amount the tax3Amount to set
	 */
	public void setTax3Amount(double tax3Amount) {
		this.tax3Amount = tax3Amount;
	}
	/**
	 * @return the taxSCAmount
	 */
	public double getTaxSCAmount() {
		return taxSCAmount;
	}
	/**
	 * @param taxSCAmount the taxSCAmount to set
	 */
	public void setTaxSCAmount(double taxSCAmount) {
		this.taxSCAmount = taxSCAmount;
	}
	/**
	 * @return the taxGSTAmount
	 */
	public double getTaxGSTAmount() {
		return taxGSTAmount;
	}
	/**
	 * @param taxGSTAmount the taxGSTAmount to set
	 */
	public void setTaxGSTAmount(double taxGSTAmount) {
		this.taxGSTAmount = taxGSTAmount;
	}
	
	/**
	 * @param quantity
	 */
	public void setQuantity(double quantity) {
		this.mQuantity = quantity ;
	}

	/**
	 * @return
	 */
	public double getQuantity() {
		return this.mQuantity;
	}

	/**
	 * @return the refundDate
	 */
	public String getRefundDate() {
		return mRefundDate;
	}

	/**
	 * @param refundDate the refundDate to set
	 */
	public void setRefundDate(String refundDate) {
		this.mRefundDate = refundDate;
	}

	/**
	 * @return the refundTime
	 */
	public String getRefundTime() {
		return mRefundTime;
	}

	/**
	 * @param refundTime the refundTime to set
	 */
	public void setRefundTime(String refundTime) {
		this.mRefundTime = refundTime;
	}

	/**
	 * @return the mRefundedBy
	 */
	public BeanUser getRefundedBy() {
		return mRefundedBy;
	}

	/**
	 * @param mRefundedBy the mRefundedBy to set
	 */
	public void setRefundedBy(BeanUser mRefundedBy) {
		this.mRefundedBy = mRefundedBy;
	}
	 
	
	//1 -> Stock In , 2 -> Adjuxtment, 3-> Disposal,4-> Sales
	public enum RefundType{
		Partial (1),
		Full(2);
		
		private static final Map<Integer,RefundType> mLookup 
		= new HashMap<Integer,RefundType>();

		static {
			for(RefundType rc : EnumSet.allOf(RefundType.class))
				mLookup.put(rc.getValue(), rc);
		}

		private int mValue;
		
		private RefundType(int value) {
			this.mValue = value;
		}

		public int getValue() { return mValue; }
		
		public static RefundType get(int value) { 
			return mLookup.get(value); 
		}

	}
		
}
