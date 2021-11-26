/**
 * 
 */
package com.indocosmo.pos.data.beans;

import com.indocosmo.pos.common.PosLog;

/**
 * @author jojesh
 *
 */
public final class BeanPMSOrderPayment {

	private String mPaymentId;
	private String mOrderId; 
	private String mInvoiceNo;
	 					
	private double mPaidAmount;	 
	private boolean mIsRepayment; 
	private String mCustomerCode;
	private String mCustomerName;
	@Override
	public BeanPMSOrderPayment clone(){
		BeanPMSOrderPayment cloneObject = null;
		try {
			cloneObject = (BeanPMSOrderPayment) super.clone();
		} catch (CloneNotSupportedException e) {
			PosLog.write(this,"clone",e);
		}
		return cloneObject;
	}
	
	/**
	 * @return the mId
	 */
	public String getPaymentId() {
		return mPaymentId;
	}
	/**
	 * @param Id the mId to set
	 */
	public void setPaymentId(String id) {
		this.mPaymentId = id;
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
	 * @return the mPaidAmount
	 */
	public double getPaidAmount() {
		return mPaidAmount;
	}
	/**
	 * @param PaidAmount the mPaidAmount to set
	 */
	public void setPaidAmount(double cashPaid) {
		this.mPaidAmount = cashPaid;
	}
	 

	/**
	 * @return the isRepayment
	 */
	public boolean isRepayment() {
		return mIsRepayment;
	}

	/**
	 * @param isRepayment the isRepayment to set
	 */
	public void setRepayment(boolean isRepayment) {
		this.mIsRepayment = isRepayment;
	}

	/**
	 * @return the mCustomerName
	 */
	public String getCustomerName() {
		return mCustomerName;
	}

	/**
	 * @param mCustomerName the mCustomerName to set
	 */
	public void setCustomerName(String mCustomerName) {
		this.mCustomerName = mCustomerName;
	}

	/**
	 * @return the mCustomerCode
	 */
	public String getCustomerCode() {
		return mCustomerCode;
	}

	/**
	 * @param mCustomerCode the mCustomerCode to set
	 */
	public void setCustomerCode(String mCustomerCode) {
		this.mCustomerCode = mCustomerCode;
	}

 
}
