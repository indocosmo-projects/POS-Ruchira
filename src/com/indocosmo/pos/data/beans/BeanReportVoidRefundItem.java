/**
 * 
 */
package com.indocosmo.pos.data.beans;

import com.indocosmo.pos.common.enums.PosOrderServiceTypes;
import com.indocosmo.pos.data.beans.BeanOrderHeader.PosOrderStatus;

/**
 * @author jojesh
 * 
 */
public class BeanReportVoidRefundItem {
	
	 
	private String mId;
	private String mOrderId;
	private String mInvoicePrefix;
	private String mInvoiceNo;
	private String mQueueNo;
    private boolean mIsVoid;
 	private String mOrderDate;
	private String mOrderTime;
	
	private String mItemCode;
	private String mItemName;
	private double mQuantity = 1;
	private BeanUOM mUom;
	private double mItemFixedPrice;
	private double mItemTotal;
	
	private boolean mRefundStatus=false;
	private double mRefundAmount;
	private double mRefundQuantity;
	private BeanUser mVoidBy;
	private String mVoidAt;
	private BeanUser mRefundedBy;
	private String mRefundedAt;
	
	private double mTax1Amount;
	private double mTax2Amount;
	private double mTax3Amount;
	private double mServiceTaxAmount;
	private double mGSTAmount;
	private double mRefundExtraCharge;
	private PosOrderStatus mStatus=PosOrderStatus.Open;
	private BeanServingTable mOrderServiceTable;
	private PosOrderServiceTypes mServiceType;
	 
	private double mTotalRefundAmt ;
	private double mTotalRoundingAdjustment;

	/** New additional objects to handle retrieving of order data as sale objects **/


	/**
	 * @return the mId
	 */
	public String getId() {
		return mId;
	}

	/**
	 * @param id
	 *            the mId to set
	 */
	public void setId(String id) {
		this.mId = id;
	}

	/**
	 * @return the mOrderId
	 */
	public String getOrderId() {
		return mOrderId;
	}

	/**
	 * @param OrderId
	 *            the mOrderId to set
	 */
	public void setOrderId(String orderId) {
		this.mOrderId = orderId;
	}
	/**
	 * @return the mInvoicePrefix
	 */
	public String getInvoicePrefix() {
		return mInvoicePrefix;
	}

	/**
	 * @param mInvoicePrefix the mInvoicePrefix to set
	 */
	public void setInvoicePrefix(String mInvoicePrefix) {
		this.mInvoicePrefix = mInvoicePrefix;
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
	 

	/**
	 * @return the mIsVoid
	 */
	public boolean isVoid() {
		return mIsVoid;
	}

	/**
	 * @param mIsVoid the mIsVoid to set
	 */
	public void setVoid(boolean isVoid) {
		this.mIsVoid = isVoid;
	}

	 
	/**
	 * @return the mOrderDate
	 */
	public String getOrderDate() {
		return mOrderDate;
	}

	/**
	 * @param OrderDate to set
	 */
	public void setOrderDate(String date) {
		this.mOrderDate = date;
	}

	/**
	 * @return the mOrderTime
	 */
	public String getOrderTime() {
		return mOrderTime;
	}

	/**
	 * @param OrderTime to set
	 */
	public void setOrderTime(String time) {
		this.mOrderTime = time;
	}
 
	/**
	 * @return the ServiceType
	 */
	public PosOrderServiceTypes getServiceType() {
		return mServiceType;
	}

	/**
	 * @param ServiceType the ServiceType to set
	 */
	public void setServiceType(PosOrderServiceTypes serviceType) {
		this.mServiceType = serviceType;
	}
	     
	/**
	 * @return the refundStatus
	 */
	public boolean isRefunded() {
		return mRefundStatus;
	}

	/**
	 * @param refundStatus the refundStatus to set
	 */
	public void setRefundStatus(boolean refundStatus) {
		this.mRefundStatus = refundStatus;
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
	 
	/**
	 * @return the mVoidBy
	 */
	public BeanUser getVoidBy() {
		return mVoidBy;
	}

	/**
	 * @param mVoidBy the mVoidBy to set
	 */
	public void setVoidBy(BeanUser mVoidBy) {
		this.mVoidBy = mVoidBy;
	}

	/**
	 * @return the mVoidAt
	 */
	public String getVoidAt() {
		return mVoidAt;
	}

	/**
	 * @param mVoidAt the mVoidAt to set
	 */
	public void setVoidAt(String mVoidAt) {
		this.mVoidAt = mVoidAt;
	}
	 
	/**
	 * @return the mRefundQuantity
	 */
	public double getRefundQuantity() {
		return mRefundQuantity;
	}

	/**
	 * @param mRefundQuantity the mRefundQuantity to set
	 */
	public void setRefundQuantity(double mRefundQuantity) {
		this.mRefundQuantity = mRefundQuantity;
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

	/**
	 * @return the mRefundAt
	 */
	public String getRefundedAt() {
		return mRefundedAt;
	}

	/**
	 * @param mRefundAt the mRefundAt to set
	 */
	public void setRefundedAt(String mRefundAt) {
		this.mRefundedAt = mRefundAt;
	}

	/**
	 * @return the mItemCode
	 */
	public String getItemCode() {
		return mItemCode;
	}

	/**
	 * @param mItemCode the mItemCode to set
	 */
	public void setItemCode(String mItemCode) {
		this.mItemCode = mItemCode;
	}

	/**
	 * @return the mItemName
	 */
	public String getItemName() {
		return mItemName;
	}

	/**
	 * @param mItemName the mItemName to set
	 */
	public void setItemName(String mItemName) {
		this.mItemName = mItemName;
	}

	/**
	 * @return the mQuantity
	 */
	public double getQuantity() {
		return mQuantity;
	}

	/**
	 * @param mQuantity the mQuantity to set
	 */
	public void setQuantity(double mQuantity) {
		this.mQuantity = mQuantity;
	}

	/**
	 * @return the mUom
	 */
	public BeanUOM getUom() {
		return mUom;
	}

	/**
	 * @param mUom the mUom to set
	 */
	public void setUom(BeanUOM mUom) {
		this.mUom = mUom;
	}

	/**
	 * @return the mItemFixedPrice
	 */
	public double getItemFixedPrice() {
		return mItemFixedPrice;
	}

	/**
	 * @param mItemFixedPrice the mItemFixedPrice to set
	 */
	public void setItemFixedPrice(double mItemFixedPrice) {
		this.mItemFixedPrice = mItemFixedPrice;
	}

	/**
	 * @return the mItemTotal
	 */
	public double getItemTotal() {
		return mItemTotal;
	}

	/**
	 * @param mItemTotal the mItemTotal to set
	 */
	public void setItemTotal(double mItemTotal) {
		this.mItemTotal = mItemTotal;
	}

	/**
	 * @return the mTax1Amount
	 */
	public double getTax1Amount() {
		return mTax1Amount;
	}

	/**
	 * @param mTax1Amount the mTax1Amount to set
	 */
	public void setTax1Amount(double mTax1Amount) {
		this.mTax1Amount = mTax1Amount;
	}

	/**
	 * @return the mTax2Amount
	 */
	public double getTax2Amount() {
		return mTax2Amount;
	}

	/**
	 * @param mTax2Amount the mTax2Amount to set
	 */
	public void setTax2Amount(double mTax2Amount) {
		this.mTax2Amount = mTax2Amount;
	}

	/**
	 * @return the mTax3Amount
	 */
	public double getTax3Amount() {
		return mTax3Amount;
	}

	/**
	 * @param mTax3Amount the mTax3Amount to set
	 */
	public void setTax3Amount(double mTax3Amount) {
		this.mTax3Amount = mTax3Amount;
	}

	/**
	 * @return the mServiceTaxAmount
	 */
	public double getServiceTaxAmount() {
		return mServiceTaxAmount;
	}

	/**
	 * @param mServiceTaxAmount the mServiceTaxAmount to set
	 */
	public void setServiceTaxAmount(double mServiceTaxAmount) {
		this.mServiceTaxAmount = mServiceTaxAmount;
	}

	/**
	 * @return the mGSTAmount
	 */
	public double getGSTAmount() {
		return mGSTAmount;
	}

	/**
	 * @param mGSTAmount the mGSTAmount to set
	 */
	public void setGSTAmount(double mGSTAmount) {
		this.mGSTAmount = mGSTAmount;
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
	 * @return the OrderServiceTable
	 */
	public BeanServingTable getServiceTable() {
		return mOrderServiceTable;
	}

	/**
	 * @param OrderServiceTable the OrderServiceTable to set
	 */
	public void setServiceTable(BeanServingTable orderServiceTable) {
		this.mOrderServiceTable = orderServiceTable;
	}

	/**
	 * @return the mRefundExtraCharge
	 */
	public double getRefundExtraCharge() {
		return mRefundExtraCharge;
	}

	/**
	 * @param mRefundExtraCharge the mRefundExtraCharge to set
	 */
	public void setRefundExtraCharge(double mRefundExtraCharge) {
		this.mRefundExtraCharge = mRefundExtraCharge;
	}

	/**
	 * @return the mTotalRefundAmt
	 */
	public double getTotalRefundAmt() {
		return mTotalRefundAmt;
	}

	/**
	 * @param mTotalRefundAmt the mTotalRefundAmt to set
	 */
	public void setTotalRefundAmt(double mTotalRefundAmt) {
		this.mTotalRefundAmt = mTotalRefundAmt;
	}

	/**
	 * @return the mTotalRoundingAdjustment
	 */
	public double getTotalRoundingAdjustment() {
		return mTotalRoundingAdjustment;
	}

	/**
	 * @param mTotalRoundingAdjustment the mTotalRoundingAdjustment to set
	 */
	public void setTotalRoundingAdjustment(double mTotalRoundingAdjustment) {
		this.mTotalRoundingAdjustment = mTotalRoundingAdjustment;
	}
	
	
}
