/**
 * 
 */
package com.indocosmo.pos.data.beans;

import javax.swing.KeyStroke;

import com.indocosmo.pos.common.PosLog;
import com.indocosmo.pos.data.beans.BeanOrderHeader.PosOrderStatus;
import com.indocosmo.pos.data.beans.BeanPayModeBase.PaymentMode;
import com.indocosmo.pos.forms.components.objectbrowser.IPosBrowsableItem;

/**
 * @author jojesh
 *
 */
public final class BeanOrderPaymentReport  {

	private String mId;
	private String mOrderId;
	private String mOrderPaymentHdrId;
	private String mInvoiceNo;
	
	private PaymentMode mPaymentMode;					
	private double mPaidAmount;	 
	private boolean mIsAdvance;
	private boolean mIsRepayment;
	private String mQueueNo;
	private PosOrderStatus mStatus=PosOrderStatus.Open;
	@Override
	public BeanOrderPaymentReport clone(){
		BeanOrderPaymentReport cloneObject = null;
		try {
			cloneObject = (BeanOrderPaymentReport) super.clone();
		} catch (CloneNotSupportedException e) {
			PosLog.write(this,"clone",e);
		}
		return cloneObject;
	}
	
	/**
	 * @return the mId
	 */
	public String getId() {
		return mId;
	}
	/**
	 * @param Id the mId to set
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
	 * @param OrderId the mOrderId to set
	 */
	public void setOrderId(String orderId) {
		this.mOrderId = orderId;
	}
	

	/**
	 * @return the mOrderPaymentHdrId
	 */
	public String getOrderPaymentHdrId() {
		return mOrderPaymentHdrId;
	}
	/**
	 * @param mOrderPaymentHdrId the mOrderPaymentHdrId to set
	 */
	public void setOrderPaymentHdrId(String orderPaymentHdrId) {
		this.mOrderPaymentHdrId = orderPaymentHdrId;
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
	 * @return the mPaymentMode
	 */
	public PaymentMode getPaymentMode() {
		return mPaymentMode;
	}
	/**
	 * @param PaymentMode the mPaymentMode to set
	 */
	public void setPaymentMode(PaymentMode paymentMode) {
		this.mPaymentMode = paymentMode;
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
	 * @return the mIsAdvance
	 */
	public boolean isAdvance() {
		return mIsAdvance;
	}

	/**
	 * @param mIsAdvance the mIsAdvance to set
	 */
	public void setAdvance(boolean mIsAdvance) {
		this.mIsAdvance = mIsAdvance;
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

}
