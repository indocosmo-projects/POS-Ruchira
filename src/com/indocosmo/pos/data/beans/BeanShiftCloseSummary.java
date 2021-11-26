/**
 * 
 */
package com.indocosmo.pos.data.beans;

/**
 * @author deepak
 *
 */
public class BeanShiftCloseSummary  extends BeanShiftSummary{
 
	private BeanUser mCashierInfo;
	private double mOpeningFloat; 
	private BeanShopShift mShiftItem;
	private double mVoucherBalanceReturned;
	private double mCashReturned; 
	private double mClosingCash;
	private double mActualCash;
	private double mVariance;
	private double mCashDeposit;
	private double mCashRemaining;
	private String mReferenceSlipNumber;
	private String mStationCode;
 
	
	 
	/**
	 * @return the mCashierInfo
	 */
	public BeanUser getCashierInfo() {
		return mCashierInfo;
	}
	/**
	 * @param mCashierInfo the mCashierInfo to set
	 */
	public void setCashierInfo(BeanUser mCashierInfo) {
		this.mCashierInfo = mCashierInfo;
	}
	/**
	 * @return the mOpeningFloat
	 */
	public double getOpeningFloat() {
		return mOpeningFloat;
	}
	/**
	 * @param mOpeningFloat the mOpeningFloat to set
	 */
	public void setOpeningFloat(double mOpeningFloat) {
		this.mOpeningFloat = mOpeningFloat;
	}
	
	/**
	 * @return the mShiftItem
	 */
	public BeanShopShift getShiftItem() {
		return mShiftItem;
	}
	/**
	 * @param mShiftItem the mShiftItem to set
	 */
	public void setShiftItem(BeanShopShift mShiftItem) {
		this.mShiftItem = mShiftItem;
	}
	/**
	 * @return the mVoucherBalanceReturned
	 */
	public double getVoucherBalanceReturned() {
		return mVoucherBalanceReturned;
	}
	/**
	 * @param mVoucherBalanceReturned the mVoucherBalanceReturned to set
	 */
	public void setVoucherBalanceReturned(double mVoucherBalanceReturned) {
		this.mVoucherBalanceReturned = mVoucherBalanceReturned;
	}
	/**
	 * @return the mCashReturned
	 */
	public double getCashReturned() {
		return mCashReturned;
	}
	/**
	 * @param mCashReturned the mCashReturned to set
	 */
	public void setCashReturned(double mCashReturned) {
		this.mCashReturned = mCashReturned;
	}
	/**
	 * @return the mClosingCash
	 */
	public double getClosingCash() {
		return mClosingCash;
	}
	/**
	 * @param mClosingCash the mClosingCash to set
	 */
	public void setClosingCash(double mClosingCash) {
		this.mClosingCash = mClosingCash;
	}
	/**
	 * @return the mActualCash
	 */
	public double getActualCash() {
		return mActualCash;
	}
	/**
	 * @param mActualCash the mActualCash to set
	 */
	public void setActualCash(double mActualCash) {
		this.mActualCash = mActualCash;
	}
	/**
	 * @return the mVariance
	 */
	public double getVariance() {
		return mVariance;
	}
	/**
	 * @param mVariance the mVariance to set
	 */
	public void setVariance(double mVariance) {
		this.mVariance = mVariance;
	}
	/**
	 * @return the mStationCode
	 */
	public String getStationCode() {
		return mStationCode;
	}
	/**
	 * @param mStationCode the mStationCode to set
	 */
	public void setStationCode(String mStationCode) {
		this.mStationCode = mStationCode;
	}
	 
	/**
	 * @return the mCashDeposit
	 */
	public double getCashDeposit() {
		return mCashDeposit;
	}
	/**
	 * @param mCashDeposit the mCashDeposit to set
	 */
	public void setCashDeposit(double mCashDeposit) {
		this.mCashDeposit = mCashDeposit;
	}
	/**
	 * @return the mReferenceSlipNumber
	 */
	public String getReferenceSlipNumber() {
		return mReferenceSlipNumber;
	}
	/**
	 * @param mReferenceSlipNumber the mReferenceSlipNumber to set
	 */
	public void setReferenceSlipNumber(String mReferenceSlipNumber) {
		this.mReferenceSlipNumber = mReferenceSlipNumber;
	}
	/**
	 * @return the mCashRemaining
	 */
	public double getCashRemaining() {
		return mCashRemaining;
	}
	/**
	 * @param mCashRemaining the mCashRemaining to set
	 */
	public void setCashRemaining(double mCashRemaining) {
		this.mCashRemaining = mCashRemaining;
	}
	  
	
}
