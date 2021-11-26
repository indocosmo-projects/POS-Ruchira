/**
 * 
 */
package com.indocosmo.pos.data.beans;

import com.indocosmo.pos.common.enums.PosOrderServiceTypes;
import com.indocosmo.pos.common.utilities.PosUomUtil;
import com.indocosmo.pos.data.beans.BeanOrderHeader.PosOrderStatus;
import com.indocosmo.pos.data.beans.BeanPayModeBase.PaymentMode;
import com.indocosmo.pos.data.providers.shopdb.PosDiscountItemProvider;

/**
 * @author sandhya
 *
 */
public class BeanSalesOrderReport {

 
	private String mOrderId;
	private String mQueueNo;
	private String mInvoiceNo; 
	private String mDueDateTime;
	private double mAdvanceAmount;
	private PaymentMode mAdvancePaymentMode;
	private String mCustomerName;
	private String mCustomerPhoneNo;
	private String mItemName;
	private double mQuantity;
	private String mItemRemarks;
	private double mBalanceReceived;
	private PaymentMode mBalancePaymentMode;
	private double mDueAmount;
	private double mTotalAmount;
	private double mRefundAmount;
	private PosOrderServiceTypes mDeliveryType;
	private PosOrderStatus mStatus=PosOrderStatus.Open;
	/**
	 * @return the mOrderId
	 */
	public String getOrderId() {
		return mOrderId;// PosOrderUtil.getFormatedOrderId(mStationCode,
		// mOrderNo);
	}

	/**
	 * @param OrderId
	 *            the mOrderId to set
	 */
	public void setOrderId(String orderId) {
		this.mOrderId = orderId;
	}
	
	/**
	 * @return the mQueueNo
	 */
	public String getQueueNo() {
		return mQueueNo;
	}

	/**
	 * @param QueueNo
	 *            the mQueueNo to set
	 */
	public void setQueueNo(String QueueNo) {
		this.mQueueNo  = QueueNo;
	}
	/**
	 * @return the mInvoiceNo
	 */
	public String getInvoiceNo() {
		return mInvoiceNo;// PosOrderUtil.getFormatedOrderId(mStationCode,
		// mOrderNo);
	}

	/**
	 * @param InvoiceNo
	 *            the mInvoiceNo to set
	 */
	public void setInvoiceNo(String InvoiceNo) {
		this.mInvoiceNo  = InvoiceNo;
	}
	/**
	 * @return the mDueDateTime
	 */
	public String getDueDateTime() {
		return mDueDateTime;
	}

	/**
	 * @param mDueDateTime the mDueDateTime to set
	 */
	public void setDueDateTime(String mDueDateTime) {
		this.mDueDateTime = mDueDateTime;
	}
	
	/**
	 * @return the mAdvancePaymentMode
	 */
	public PaymentMode getAdvancePaymentMode() {
		return mAdvancePaymentMode;
	}

	/**
	 * @param mAdvancePaymentMode the mAdvancePaymentMode to set
	 */
	public void setAdvancePaymentMode(PaymentMode mAdvancePaymentMode) {
		this.mAdvancePaymentMode = mAdvancePaymentMode;
	}

	
	/**
	 * @return the mAdvanceAmount
	 */
	public double getAdvanceAmount() {
		return mAdvanceAmount;
	}

	/**
	 * @param mAdvanceAmount the mAdvanceAmount to set
	 */
	public void setAdvanceAmount(double mAdvanceAmount) {
		this.mAdvanceAmount = mAdvanceAmount;
	}
	
	/**
	 * @return the mAdvancePaymentMode
	 */
	public PaymentMode getBalancePaymentMode() {
		return mBalancePaymentMode;
	}

	/**
	 * @param mAdvancePaymentMode the mAdvancePaymentMode to set
	 */
	public void setBalancePaymentMode(PaymentMode mBalancePaymentMode) {
		this.mBalancePaymentMode = mBalancePaymentMode;
	}

	
	/**
	 * @return the mAdvanceAmount
	 */
	public double getBalanceReceived() {
		return mBalanceReceived;
	}

	/**
	 * @param mAdvanceAmount the mAdvanceAmount to set
	 */
	public void setBalanceReceived(double balanceReceived) {
		this.mBalanceReceived = balanceReceived;
	}
	
	
	/**
	 * @return the mName
	 */
	public String getCustomerName() {
		return mCustomerName;
	}
	/**
	 * @param mName the mName to set
	 */
	public void setCustomerName(String mName) {
		this.mCustomerName = mName;
	}
	/**
	 * @return the mPhoneNo
	 */
	public String getPhoneNumber() {
		return mCustomerPhoneNo;
	}
	/**
	 * @param mPhoneNo the mPhoneNo to set
	 */
	public void setCustomerPhoneNumber(String mPhoneNo) {
		this.mCustomerPhoneNo = mPhoneNo;
	}
	public String getItemName() {
		return (mItemName);
	}

	public void setItemName(String itemName) {
		this.mItemName = itemName;
	}
	/**
	 * @param quantity
	 */
	public void setQuantity(double quantity) {
		this.mQuantity =  quantity ;
	}

	/**
	 * @return
	 */
	public double getQuantity() {
		return this.mQuantity;
	}

	/**
	 * @return the mItemRemarks
	 */
	public String getItemRemarks() {
		return mItemRemarks;
	}

	/**
	 * @param mItemRemarks the mItemRemarks to set
	 */
	public void setItemRemarks(String mItemRemarks) {
		this.mItemRemarks = mItemRemarks;
	}
	/**
	 * @return the mDeliveryType
	 */
	public PosOrderServiceTypes getDeliveryType() {
		return mDeliveryType;
	}

	/**
	 * @param mDeliveryType the mDeliveryType to set
	 */
	public void setDeliveryType(PosOrderServiceTypes mDeliveryType) {
		this.mDeliveryType = mDeliveryType;
	}
	
	/**
	 * @return the mTatus
	 */
	public PosOrderStatus getStatus() {
		return mStatus;
	}

	/**
	 * @param Tatus
	 *            the mTatus to set
	 */
	public void setStatus(PosOrderStatus status) {
		this.mStatus = status;
	}

	/**
	 * @return the mDueAmount
	 */
	public double getDueAmount() {
		return mDueAmount;
	}

	/**
	 * @param mDueAmount the mDueAmount to set
	 */
	public void setDueAmount(double mDueAmount) {
		this.mDueAmount = mDueAmount;
	}

	/**
	 * @return the mTotalAmount
	 */
	public double getTotalAmount() {
		return mTotalAmount;
	}

	/**
	 * @param mTotalAmount the mTotalAmount to set
	 */
	public void setTotalAmount(double mTotalAmount) {
		this.mTotalAmount = mTotalAmount;
	}

	/**
	 * @return the mRefundAmount
	 */
	public double getRefundAmount() {
		return mRefundAmount;
	}

	/**
	 * @param mRefundAmount the mRefundAmount to set
	 */
	public void setRefundAmount(double mRefundAmount) {
		this.mRefundAmount = mRefundAmount;
	}

	
}
