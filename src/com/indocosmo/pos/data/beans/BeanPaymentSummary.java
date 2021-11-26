/**
 * 
 */
package com.indocosmo.pos.data.beans;

/**
 * @author jojesh
 *
 */
public class BeanPaymentSummary {
	private double mCashTotal;
	private double mCardTotal;
	private double mVoucherTotal;
	private double mCompanyTotal;
	private double mBillDiscount;
	private double mTotalRepayment;
	private double mTotalBalance;
	private double mRoundAdjustment;
	/**
	 * @return the cashTotal
	 */
	public double getCashTotal() {
		return mCashTotal;
	}

	/**
	 * @param mCashTotal the mCashTotal to set
	 */
	public void setCashTotal(double cashTotal) {
		this.mCashTotal = cashTotal;
	}

	/**
	 * @return the mCardTotal
	 */
	public double getCardTotal() {
		return mCardTotal;
	}

	/**
	 * @param mCardTotal the mCardTotal to set
	 */
	public void setCardTotal(double cardTotal) {
		this.mCardTotal = cardTotal;
	}

	/**
	 * @return the VoucherTotal
	 */
	public double getVoucherTotal() {
		return mVoucherTotal;
	}

	/**
	 * @param voucherTotal
	 */
	public void setVoucherTotal(double voucherTotal) {
		this.mVoucherTotal = voucherTotal;
	}

	/**
	 * @return themCompanyTotal
	 */
	public double getCompanyTotal() {
		return mCompanyTotal;
	}

	/**
	 * @param CompanyTotal 
	 */
	public void setCompanyTotal(double companyTotal) {
		this.mCompanyTotal = companyTotal;
	}

	/**
	 * @return the BillDiscount
	 */
	public double getBillDiscount() {
		return mBillDiscount;
	}

	/**
	 * @param Bill Discount
	 */
	public void setBillDiscount(double billDiscount) {
		this.mBillDiscount = billDiscount;
	}

	/**
	 * @return the totalRepayment
	 */
	public double getTotalRepayment() {
		return mTotalRepayment;
	}

	/**
	 * @param totalRepayment the totalRepayment to set
	 */
	public void setTotalRepayment(double totalRepayment) {
		mTotalRepayment = totalRepayment;
	}

	/**
	 * @return the toalBalance
	 */
	public double getTotalBalance() {
		return mTotalBalance;
	}

	/**
	 * @param toalBalance the toalBalance to set
	 */
	public void setTotalBalance(double totalBalance) {
		mTotalBalance = totalBalance;
	}

	/**
	 * @param roundAdjustmentAmount
	 */
	public void setRoundAdjustmentAmount(double roundAdjustmentAmount) {
		mRoundAdjustment = roundAdjustmentAmount;
		
	}
	/**
	 * 
	 * @return
	 */
	public double getRoundAdjustmentAmount(){
		return mRoundAdjustment; 
	}

}
