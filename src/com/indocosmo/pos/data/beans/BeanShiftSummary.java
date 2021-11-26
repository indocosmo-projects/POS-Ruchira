/**
 * 
 */
package com.indocosmo.pos.data.beans;

/**
 * @author deepak
 *
 */
public class BeanShiftSummary {

	private int mID;
	private String mShiftCode;
	private String mOpeningDate;
	private String mOpeningTime;
	private String mClosingDate;
	private String mClosingTime;
	
	private int mOrderCount;
	
	private double mCashReceipts;
	private double mCardReceipts;
	private double mVoucherReceipts;
	private double mOnlineReceipts;
	private double mAccountsReceivable;
	
	private double mVoucherBalance;
	private double mCashOut;

	private double mNetSale;
	private double mExpense;
	
	private double mCashAdvance;
	private double mCardAdvance;
	private double mVoucherAdvance;
	private double mOnlineAdvance;

	private double mNetCash;
	
	private double mCashRefund;
	private double mCardRefund;
	private double mVoucherRefund;
	private double mAccountsRefund;
	private double mOnlineRefund;
	private double mTotalRefund;
	
	private double mClosedOrderAdvance;
	private double mTotalDiscount;
	private double mTotalTax;
	private String mOpeningInvoiceNo;
	private String mClosingInvoiceNo;
	
	/**
	 * @return the mNoOfOrders
	 */
	public int getOrderCount() {
		return mOrderCount;
	}
	/**
	 * @param mNoOfOrders the mNoOfOrders to set
	 */
	public void setOrderCount(int orderCount) {
		this.mOrderCount = orderCount;
	}
	/**
	 * @return the mID
	 */
	public int getID() {
		return mID;
	}
	/**
	 * @param mID the mID to set
	 */
	public void setID(int mID) {
		this.mID = mID;
	}
 
	/**
	 * @return the mCashierInfo
	 */
	public String getShiftCode() {
		return mShiftCode;
	}
	/**
	 * @param mCashierInfo the mCashierInfo to set
	 */
	public void setShiftCode(String mShiftCode) {
		this.mShiftCode = mShiftCode;
	}
	/**
	 * @return the mOpeningDate
	 */
	public String getOpeningDate() {
		return mOpeningDate;
	}
	/**
	 * @param mOpeningDate the mOpeningDate to set
	 */
	public void setOpeningDate(String mOpeningDate) {
		this.mOpeningDate = mOpeningDate;
	}
	/**
	 * @return the mOpeningTime
	 */
	public String getOpeningTime() {
		return mOpeningTime;
	}
	/**
	 * @param mOpeningTime the mOpeningTime to set
	 */
	public void setOpeningTime(String mOpeningTime) {
		this.mOpeningTime = mOpeningTime;
	}
	/**
	 * @return the mClosingDate
	 */
	public String getClosingDate() {
		return mClosingDate;
	}
	/**
	 * @param mClosingDate the mClosingDate to set
	 */
	public void setClosingDate(String mClosingDate) {
		this.mClosingDate = mClosingDate;
	}
	/**
	 * @return the mClosingTime
	 */
	public String getClosingTime() {
		return mClosingTime;
	}
	/**
	 * @param mClosingTime the mClosingTime to set
	 */
	public void setClosingTime(String mClosingTime) {
		this.mClosingTime = mClosingTime;
	}
 	/**
	 * @return the mCashReceipts
	 */
	public double getCashReceipts() {
		return mCashReceipts;
	}
	/**
	 * @param mCashReceipts the mCashReceipts to set
	 */
	public void setCashReceipts(double mCashReceipts) {
		this.mCashReceipts = mCashReceipts;
	}
	/**
	 * @return the mCardReceipts
	 */
	public double getCardReceipts() {
		return mCardReceipts;
	}
	/**
	 * @param mCardReceipts the mCardReceipts to set
	 */
	public void setCardReceipts(double mCardReceipts) {
		this.mCardReceipts = mCardReceipts;
	}
	/**
	 * @return the mVoucherReceipts
	 */
	public double getVoucherReceipts() {
		return mVoucherReceipts;
	}
	/**
	 * @param mVoucherReceipts the mVoucherReceipts to set
	 */
	public void setVoucherReceipts(double mVoucherReceipts) {
		this.mVoucherReceipts = mVoucherReceipts;
	}
	/**
	 * @return the mAccountsReceivable
	 */
	public double getAccountsReceivable() {
		return mAccountsReceivable;
	}
	/**
	 * @param mAccountsReceivable the mAccountsReceivable to set
	 */
	public void setAccountsReceivable(double mAccountsReceivable) {
		this.mAccountsReceivable = mAccountsReceivable;
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
	 * @return the mSales
	 */
	public double getNetSale() {
		return mNetSale;
	}
	/**
	 * @param mSales the mSales to set
	 */
	public void setNetSale(double mSales) {
		this.mNetSale= mSales;
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
	 * @return the expense
	 */
	public double getExpense() {
		return mExpense;
	}
	/**
	 * @param expense the expense to set
	 */
	public void setExpense(double expense) {
		this.mExpense = expense;
	}
	/**
	 * @return the mAdvanceCash
	 */
	public double getCashAdvance() {
		return mCashAdvance;
	}
	/**
	 * @param mAdvanceCash the mAdvanceCash to set
	 */
	public void setCashAdvance(double mAdvanceCash) {
		this.mCashAdvance = mAdvanceCash;
	}
	/**
	 * @return the mAdvanceCard
	 */
	public double getCardAdvance() {
		return mCardAdvance;
	}
	/**
	 * @param mAdvanceCard the mAdvanceCard to set
	 */
	public void setCardAdvance(double mAdvanceCard) {
		this.mCardAdvance = mAdvanceCard;
	}
	/**
	 * @return the mPreviousAdvance
	 */
	public double getClosedOrderAdvance() {
		return mClosedOrderAdvance;
	}
	/**
	 * @param mPreviousAdvance the mPreviousAdvance to set
	 */
	public void setClosedOrderAdvance(double mPreviousAdvance) {
		this.mClosedOrderAdvance = mPreviousAdvance;
	}
	/**
	 * @return the mOpeningInvoiceNo
	 */
	public String getOpeningInvoiceNo() {
		return mOpeningInvoiceNo;
	}
	/**
	 * @param mOpeningInvoiceNo the mOpeningInvoiceNo to set
	 */
	public void setOpeningInvoiceNo(String mOpeningInvoiceNo) {
		this.mOpeningInvoiceNo = mOpeningInvoiceNo;
	}
	/**
	 * @return the mClosingInvoiceNo
	 */
	public String getClosingInvoiceNo() {
		return mClosingInvoiceNo;
	}
	/**
	 * @param mClosingInvoiceNo the mClosingInvoiceNo to set
	 */
	public void setClosingInvoiceNo(String mClosingInvoiceNo) {
		this.mClosingInvoiceNo = mClosingInvoiceNo;
	}
	/**
	 * @return the mTotalDiscount
	 */
	public double getTotalDiscount() {
		return mTotalDiscount;
	}
	/**
	 * @param mTotalDiscount the mTotalDiscount to set
	 */
	public void setTotalDiscount(double mTotalDiscount) {
		this.mTotalDiscount = mTotalDiscount;
	}
	/**
	 * @return the mTotalTax
	 */
	public double getTotalTax() {
		return mTotalTax;
	}
	/**
	 * @param mTotalTax the mTotalTax to set
	 */
	public void setTotalTax(double mTotalTax) {
		this.mTotalTax = mTotalTax;
	}
	/**
	 * @return the mOnlineReceipts
	 */
	public double getOnlineReceipts() {
		return mOnlineReceipts;
	}
	/**
	 * @param mOnlineReceipts the mOnlineReceipts to set
	 */
	public void setOnlineReceipts(double mOnlineReceipts) {
		this.mOnlineReceipts = mOnlineReceipts;
	}
	/**
	 * @return the mVoucherAdvance
	 */
	public double getVoucherAdvance() {
		return mVoucherAdvance;
	}
	/**
	 * @param mVoucherAdvance the mVoucherAdvance to set
	 */
	public void setVoucherAdvance(double mVoucherAdvance) {
		this.mVoucherAdvance = mVoucherAdvance;
	}
 
	/**
	 * @return the mOnlineAdvance
	 */
	public double getOnlineAdvance() {
		return mOnlineAdvance;
	}
	/**
	 * @param mOnlineAdvance the mOnlineAdvance to set
	 */
	public void setOnlineAdvance(double mOnlineAdvance) {
		this.mOnlineAdvance = mOnlineAdvance;
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
	 * @return the mNetCash
	 */
	public double getNetCash() {
		return mNetCash;
	}
	/**
	 * @param mNetCash the mNetCash to set
	 */
	public void setNetCash(double mNetCash) {
		this.mNetCash = mNetCash;
	}

	/**
	 * @return the mVoucherBalance
	 */
	public double getVoucherBalance() {
		return mVoucherBalance;
	}
	/**
	 * @param mVoucherBalance the mVoucherBalance to set
	 */
	public void setVoucherBalance(double mVoucherBalance) {
		this.mVoucherBalance = mVoucherBalance;
	}
	/**
	 * @return the voucherRefund
	 */
	public double getVoucherRefund() {
		return mVoucherRefund;
	}
	/**
	 * @param voucherRefund the voucherRefund to set
	 */
	public void setVoucherRefund(double voucherRefund) {
		this.mVoucherRefund = voucherRefund;
	}
	/**
	 * @return the accountsReceivableRefund
	 */
	public double getAccountsRefund() {
		return mAccountsRefund;
	}
	/**
	 * @param accountsReceivableRefund the accountsReceivableRefund to set
	 */
	public void setAccountsRefund(double accountsReceivableRefund) {
		this.mAccountsRefund = accountsReceivableRefund;
	}
	/**
	 * @return the OnlineRefund
	 */
	public double getOnlineRefund() {
		return mOnlineRefund;
	}
	/**
	 * @param OnlineRefund the OnlineRefund to set
	 */
	public void setOnlineRefund(double onlineRefund) {
		mOnlineRefund = onlineRefund;
	}
	
}
