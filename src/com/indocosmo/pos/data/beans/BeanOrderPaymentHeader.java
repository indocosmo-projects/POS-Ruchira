/**
 * 
 */
package com.indocosmo.pos.data.beans;

import com.indocosmo.pos.common.PosLog;

/**
 * @author sandhya
 * 
 */
public final class BeanOrderPaymentHeader  implements Cloneable{

	private String mId;
	private String mOrderId;
	private String mPaymentDate;
	private String mPaymentTime;
	private int mShiftId;
	
	private double mDetailTotal;
	private double mTotalAmountTax1;
	private double mTotalAmountTax2;
	private double mTotalAmountTax3;
	private double mTotalAmountServiceTax;
	private double mTotalAmountGST;
	private double mTotalAmountDetailDiscount;
	private double mRoundAdjustmentAmount;
	private double mTotalAmount;
	private double mTotalAmountPaid;
	private double mBalanceAmount;
	private double mActualBalancePaid;
	private double mCashOut;
	private double mBillTaxAmount;
	private double mBillDiscountAmount;
	
	private boolean mIsRefund;
	private boolean mIsAdvance;
	private String mRemarks="";
	private boolean mIsNew=false;    
	private boolean mIsFinal=false;
	@Override
	public BeanOrderPaymentHeader clone(){
		BeanOrderPaymentHeader cloneObject = null;
		try {
			cloneObject = (BeanOrderPaymentHeader) super.clone();
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
	 * @return the paymentTime
	 */
	public String getPaymentDate() {
		return mPaymentDate;
	}
	/**
	 * @param paymentTime the paymentTime to set
	 */
	public void setPaymentDate(String paymentDate) {
		mPaymentDate = paymentDate;
	}
	/**
	 * @return the paymentTime
	 */
	public String getPaymentTime() {
		return mPaymentTime;
	}
	/**
	 * @param paymentTime the paymentTime to set
	 */
	public void setPaymentTime(String paymentTime) {
		mPaymentTime = paymentTime;
	}


	/**
	 * @return the mShiftId
	 */
	public int getShiftId() {
		return mShiftId;
	}

	/**
	 * @param shiftId
	 *            the mShiftId to set
	 */
	public void setShiftId(int shiftId) {
		this.mShiftId = shiftId;
	} 
	/**
	 * @return the mDetailTotal
	 */
	public double getDetailTotal() {
		return mDetailTotal;
	}

	/**
	 * @param detailTotal
	 *            the mDetailTotal to set
	 */
	public void setDetailTotal(double detailTotal) {
		this.mDetailTotal = detailTotal;
	}

	/**
	 * @return the mTotalAmountTax1
	 */
	public double getTotalTax1() {
		return mTotalAmountTax1;
	}

	/**
	 * @param totalTax1
	 *            the mTotalAmountTax1 to set
	 */
	public void setTotalTax1(double totalTax1) {
		this.mTotalAmountTax1 = totalTax1;
	}

	/**
	 * @return the mTotalAmountTax2
	 */
	public double getTotalTax2() {
		return mTotalAmountTax2;
	}

	/**
	 * @param totalTax2
	 *            the mTotalAmountTax2 to set
	 */
	public void setTotalTax2(double totalTax2) {
		this.mTotalAmountTax2 = totalTax2;
	}

	/**
	 * @return the mTotalAmountTax3
	 */
	public double getTotalTax3() {
		return mTotalAmountTax3;
	}

	/**
	 * @param totalTax3
	 *            the mTotalAmountTax3 to set
	 */
	public void setTotalTax3(double totalTax3) {
		this.mTotalAmountTax3 = totalTax3;
	}

	/**
	 * @return the mTotalAmountServiceTax
	 */
	public double getTotalServiceTax() {
		return mTotalAmountServiceTax;
	}

	/**
	 * @param totalServiceTax
	 *            the mTotalAmountServiceTax to set
	 */
	public void setTotalServiceTax(double totalServiceTax) {
		this.mTotalAmountServiceTax = totalServiceTax;
	}

	/**
	 * @return the mTotalAmountGST
	 */
	public double getTotalGST() {
		return mTotalAmountGST;
	}

	/**
	 * @param totalSC
	 *            the mTotalAmountGST to set
	 */
	public void setTotalGST(double totalgst) {
		this.mTotalAmountGST = totalgst;
	}

	/**
	 * @return the mTotalAmountDetailDiscount
	 */
	public double getTotalDetailDiscount() {
		return mTotalAmountDetailDiscount;
	}

	/**
	 * @param totalDetailDiscount
	 *            the mTotalAmountDetailDiscount to set
	 */
	public void setTotalDetailDiscount(double totalDetailDiscount) {
		this.mTotalAmountDetailDiscount = totalDetailDiscount;
	}

	/**
	 * @return the RoundAdjustmentAmount
	 */
	public double getRoundAdjustmentAmount() {
		return mRoundAdjustmentAmount;
	}

	/**
	 * @param 
	 *            the RoundAdjustmentAmount to set
	 */
	public void setRoundAdjustmentAmount(double roundAdjustmentAmount) {
		this.mRoundAdjustmentAmount = roundAdjustmentAmount;
	}

	/**
	 * @return the mTotalAmount
	 */
	public double getTotalAmount() {
		return mTotalAmount;
	}

	/**
	 * @param total
	 *            the mTotalAmount to set
	 */
	public void setTotalAmount(double total) {
		this.mTotalAmount = total;
	}

	/**
	 * @return the mTotalAmountPaid
	 */
	public double getTotalAmountPaid() {
		return mTotalAmountPaid;
	}

	/**
	 * @param total
	 *            the mTotalAmount to set
	 */
	public void setTotalAmountPaid(double total) {
		this.mTotalAmountPaid = total;
	}
 
	/**
	 * @return the mBalanceAmount
	 */
	public double getChangeAmount() {
		return mBalanceAmount;
	}

	/**
	 * @param mBalanceAmount
	 *            the mBalanceAmount to set
	 */
	public void setChangeAmount(double balanceAmount) {
		this.mBalanceAmount = balanceAmount;
	}

	/**
	 * @return the mActualBalancePaid
	 */
	public double getActualBalancePaid() {
		return mActualBalancePaid;
	}

	/**
	 * @param mActualBalancePaid
	 *            the mActualBalancePaid to set
	 */
	public void setActualBalancePaid(double actualBalancePaid) {
		this.mActualBalancePaid = actualBalancePaid;
	}
	
	
	/**
	 * @return the mCashOut
	 */
	public double getCashOut() {
		return mCashOut;
	}

	/**
	 * @param mCashOut the mCashOut to set
	 */
	public void setCashOut(double mCashOut) {
		this.mCashOut = mCashOut;
	}
	/**
	 * @return the BillTaxAmount
	 */
	public double getBillTaxAmount() {
		return mBillTaxAmount;
	}

	/**
	 * @param mBillTaxAmount the mBillTaxAmount to set
	 */
	public void setBillTaxAmount(double billTaxAmount) {
		this.mBillTaxAmount = billTaxAmount;
	}

	/**
	 * @return the BillDiscountAmount
	 */
	public double getBillDiscountAmount() {
		return mBillDiscountAmount;
	}

	/**
	 * @param the BillDiscountAmount to set
	 */
	public void setBillDiscountAmount(double billDiscountAmount) {
		this.mBillDiscountAmount = billDiscountAmount;
	}
	

	/**
	 * @return the isRepayment
	 */
	public boolean isRefund() {
		return mIsRefund;
	}

	/**
	 * @param isRepayment the isRepayment to set
	 */
	public void setRefund(boolean isRefund) {
		this.mIsRefund = isRefund;
	}

	/**
	 * @return the Remarks
	 */
	public String getRemarks() {
		return mRemarks;
	}

	/**
	 * @param Remarks
	 */
	public void setRemarks(String remarks) {
		this.mRemarks = remarks;
	}
 
	/**
	 * @return the mIsNew
	 */
	public boolean isNew() {
		return mIsNew;
	}

	/**
	 * @param mIsNew the mIsNew to set
	 */
	public void setNew(boolean isNew) {
		this.mIsNew= isNew;
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
	 * @return the isFinal
	 */
	public boolean isFinal() {
		return mIsFinal;
	}

	/**
	 * @param isFinal the isFinal to set
	 */
	public void setFinal(boolean isFinal) {
		this.mIsFinal = isFinal;
	}

}
