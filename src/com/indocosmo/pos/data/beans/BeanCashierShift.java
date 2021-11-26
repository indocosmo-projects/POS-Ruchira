/**
 * 
 */
package com.indocosmo.pos.data.beans;

/**
 * @author jojesh
 *
 */
public final class BeanCashierShift {
	
	private int mID;
	private BeanUser mCashierInfo;
	private String mOpeningDate;
	private String mOpeningTime;
	private BeanShopShift mShiftItem;
	private String mClosingDate;
	private String mClosingTime;
	private double mCashCollection;
	private double mCardCollection;
	private double mCompanyCollection;
	private double mVoucherCollection;
	private double mOnlineCollection;
	private double mClosingFloat;
	private double mOpeningFloat;
	private boolean mIsOpenTill;
	private double mBalanceCash;
	private double mBalanceVoucher;
	private double mBalanceVoucherReturned;
	private double mCashOut;
	private double mCashRefund;
	private double mCardRefund;
	private double mVoucherRefund;
	private double mAccountsRefund;
	private double mOnlineRefund;
	private double mTotalRefund;
	private double mExpence;
	
	/**
	 * @return the iD
	 */
	public int getID() {
		return mID;
	}
	/**
	 * @param iD the iD to set
	 */
	public void setID(int id) {  
		mID = id;
	}
	/**
	 * @return the iD
	 */
	public BeanUser getCashierInfo() {
		return mCashierInfo;
	}
	/**
	 * @param iD the iD to set
	 */
	public void setCashierInfo(BeanUser cashierInfo) {  
		mCashierInfo = cashierInfo;
	}


	/**
	 * @return the shiftOpenDate
	 */
	public String getOpeningDate() {
		return mOpeningDate;
	}
	/**
	 * @param shiftOpenDate the shiftOpenDate to set
	 */
	public void setOpeningDate(String shiftOpenDate) {
		mOpeningDate = shiftOpenDate;
	}
	/**
	 * @return the shiftOpenTime
	 */
	public String getOpeningTime() {
		return mOpeningTime;
	}
	/**
	 * @param shiftOpenTime the shiftOpenTime to set
	 */
	public void setOpeningTime(String shiftOpenTime) {
		mOpeningTime = shiftOpenTime;
	}
	/**
	 * @return the shiftItem
	 */
	public BeanShopShift getShiftItem() {
		return mShiftItem;
	}
	/**
	 * @param shiftItem the shiftItem to set
	 */
	public void setShiftItem(BeanShopShift shiftItem) {
		mShiftItem = shiftItem;
	}
	/**
	 * @return the shiftCloseDate
	 */
	public String getClosingDate() {
		return mClosingDate;
	}
	/**
	 * @param shiftCloseDate the shiftCloseDate to set
	 */
	public void setClosingDate(String shiftCloseDate) {
		mClosingDate = shiftCloseDate;
	}
	/**
	 * @return the shiftCloseTime
	 */
	public String getClosingTime() {
		return mClosingTime;
	}
	/**
	 * @param shiftCloseTime the shiftCloseTime to set
	 */
	public void setClosingTime(String shiftCloseTime) {
		mClosingTime = shiftCloseTime;
	}
	/**
	 * @return the cashCollection
	 */
	public double getCashCollection() {
		return mCashCollection;
	}
	/**
	 * @param cashCollection the cashCollection to set
	 */
	public void setCashCollection(double cashCollection) {
		mCashCollection = cashCollection;
	}
	/**
	 * @return the cardCollection
	 */
	public double getCardCollection() {
		return mCardCollection;
	}
	/**
	 * @param cardCollection the cardCollection to set
	 */
	public void setCardCollection(double cardCollection) {
		mCardCollection = cardCollection;
	}
	/**
	 * @return the companyCollection
	 */
	public double getCompanyCollection() {
		return mCompanyCollection;
	}
	/**
	 * @param companyCollection the companyCollection to set
	 */
	public void setCompanyCollection(double companyCollection) {
		mCompanyCollection = companyCollection;
	}
	/**
	 * @return the voucherCollection
	 */
	public double getVoucherCollection() {
		return mVoucherCollection;
	}
	/**
	 * @param voucherCollection the voucherCollection to set
	 */
	public void setVoucherCollection(double voucherCollection) {
		mVoucherCollection = voucherCollection;
	}
	/**
	 * @return the closingFloat
	 */
	public double getClosingFloat() {
		return mClosingFloat;
	}
	/**
	 * @param closingFloat the closingFloat to set
	 */
	public void setClosingFloat(double closingFloat) {
		mClosingFloat = closingFloat;
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
	public void setOpeningFloat(double openingFloat) {
		this.mOpeningFloat = openingFloat;
	}
	/**
	 * @return the mIsOpenTill
	 */
	public boolean IsOpenTill() {
		return mIsOpenTill;
	}
	/**
	 * @param mIsOpenTill the mIsOpenTill to set
	 */
	public void setIsOpenTill(boolean mIsOpenTill) {
		this.mIsOpenTill = mIsOpenTill;
	}
	/**
	 * @return the mBalanceCash
	 */
	public double getBalanceCash() {
		return mBalanceCash;
	}
	/**
	 * @param mBalanceCash the mBalanceCash to set
	 */
	public void setBalanceCash(double mBalanceCash) {
		this.mBalanceCash = mBalanceCash;
	}
	/**
	 * @return the mBalanceVoucher
	 */
	public double getBalanceVoucher() {
		return mBalanceVoucher;
	}
	/**
	 * @param mBalanceVoucher the mBalanceVoucher to set
	 */
	public void setBalanceVoucher(double mBalanceVoucher) {
		this.mBalanceVoucher = mBalanceVoucher;
	}
	/**
	 * @return the mBalanceVoucherReturned
	 */
	public double getBalanceVoucherReturned() {
		return mBalanceVoucherReturned;
	}
	/**
	 * @param mBalanceVoucherReturned the mBalanceVoucherReturned to set
	 */
	public void setBalanceVoucherReturned(double mBalanceVoucherReturned) {
		this.mBalanceVoucherReturned = mBalanceVoucherReturned;
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
	 * @return the mCashRefund
	 */
	public double getCashRefund() {
		return mCashRefund;
	}
	/**
	 * @param mCashRefund the mCashRefund to set
	 */
	public void setCashRefund(double mCashRefund) {
		this.mCashRefund = mCashRefund;
	}
	/**
	 * @return the mCardRefund
	 */
	public double getCardRefund() {
		return mCardRefund;
	}
	/**
	 * @param mCardRefund the mCardRefund to set
	 */
	public void setCardRefund(double mCardRefund) {
		this.mCardRefund = mCardRefund;
	}
	/**
	 * @return the mVoucherRefund
	 */
	public double getVoucherRefund() {
		return mVoucherRefund;
	}
	/**
	 * @param mVoucherRefund the mVoucherRefund to set
	 */
	public void setVoucherRefund(double mVoucherRefund) {
		this.mVoucherRefund = mVoucherRefund;
	}
	/**
	 * @return the mAccountsRefund
	 */
	public double getAccountsRefund() {
		return mAccountsRefund;
	}
	/**
	 * @param mAccountsRefund the mAccountsRefund to set
	 */
	public void setAccountsRefund(double mAccountsRefund) {
		this.mAccountsRefund = mAccountsRefund;
	}
	/**
	 * @return the mTotalRefund
	 */
	public double getTotalRefund() {
		return mTotalRefund;
	}
	/**
	 * @param mTotalRefund the mTotalRefund to set
	 */
	public void setTotalRefund(double mTotalRefund) {
		this.mTotalRefund = mTotalRefund;
	}
	/**
	 * @return the expence
	 */
	public double getExpence() {
		return mExpence;
	}
	/**
	 * @param expence the expence to set
	 */
	public void setExpence(double expence) {
		this.mExpence = expence;
	}
	/**
	 * @return the mOnlineCollection
	 */
	public double getOnlineCollection() {
		return mOnlineCollection;
	}
	/**
	 * @param mOnlineCollection the mOnlineCollection to set
	 */
	public void setOnlineCollection(double mOnlineCollection) {
		this.mOnlineCollection = mOnlineCollection;
	}
	/**
	 * @return the mOnlineRefund
	 */
	public double getOnlineRefund() {
		return mOnlineRefund;
	}
	/**
	 * @param mOnlineRefund the mOnlineRefund to set
	 */
	public void setOnlineRefund(double mOnlineRefund) {
		this.mOnlineRefund = mOnlineRefund;
	}
	

}
