/**
 * 
 */
package com.indocosmo.pos.data.beans;

/**
 * @author deepak
 *
 */
public class BeanPaymentModes {
	
	private int id;
	private boolean mCanPayByCash;
	private boolean mCanPayByCard;
	private boolean mCanPayByCompany;
	private boolean mCanPayByVouchers;
	private boolean mCanPayOnline;
	private int mMaxVoucherType;
	private int mCreatedBy;
	private String mCreatedAt;
	private int mUpdatedBy;
	private String mUpdatedAt;
	private boolean mPublishStatus;
	private boolean mIsDeleted;
	private boolean mIsSynchable;
	private boolean mCanCashRefundable;
	private boolean mCanCardRefundable;
	private boolean mCanCompanyRefundable;
	private boolean mCanVoucherRefundable;
	private boolean mCanOnlineRefund;
	private String mCashRefundAccountNo;
	private boolean mAlternativeRefundMethodForVoucher;
	private boolean mCanCashRound;
	private boolean mCanCardRound;
	private boolean mCanCompanyRound;
	private boolean mCanVoucherRound;
	private boolean mCanOnlineRound;
	
	private String mTitleCash;
	private String mTitleCard;
	private String mTitleCompany;
	private String mTitleVoucher;
	private String mTitleOnline;
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return the mCanPayByCash
	 */
	public boolean isCanPayByCash() {
		return mCanPayByCash;
	}
	/**
	 * @param mCanPayByCash the mCanPayByCash to set
	 */
	public void setCanPayByCash(boolean mCanPayByCash) {
		this.mCanPayByCash = mCanPayByCash;
	}
	/**
	 * @return the mCanPayByCard
	 */
	public boolean isCanPayByCard() {
		return mCanPayByCard;
	}
	/**
	 * @param mCanPayByCard the mCanPayByCard to set
	 */
	public void setCanPayByCard(boolean mCanPayByCard) {
		this.mCanPayByCard = mCanPayByCard;
	}
	/**
	 * @return the mCanPayByCompany
	 */
	public boolean isCanPayByCompany() {
		return mCanPayByCompany;
	}
	/**
	 * @param mCanPayByCompany the mCanPayByCompany to set
	 */
	public void setCanPayByCompany(boolean mCanPayByCompany) {
		this.mCanPayByCompany = mCanPayByCompany;
	}
	/**
	 * @return the mCanPayByVouchers
	 */
	public boolean isCanPayByVouchers() {
		return mCanPayByVouchers;
	}
	/**
	 * @param mCanPayByVouchers the mCanPayByVouchers to set
	 */
	public void setCanPayByVouchers(boolean mCanPayByVouchers) {
		this.mCanPayByVouchers = mCanPayByVouchers;
	}
	/**
	 * @return the mMaxVoucherType
	 */
	public int getMaxVoucherType() {
		return mMaxVoucherType;
	}
	/**
	 * @param mMaxVoucherType the mMaxVoucherType to set
	 */
	public void setMaxVoucherType(int mMaxVoucherType) {
		this.mMaxVoucherType = mMaxVoucherType;
	}
	/**
	 * @return the mCreatedBy
	 */
	public int getCreatedBy() {
		return mCreatedBy;
	}
	/**
	 * @param mCreatedBy the mCreatedBy to set
	 */
	public void setCreatedBy(int mCreatedBy) {
		this.mCreatedBy = mCreatedBy;
	}
	/**
	 * @return the mCreatedAt
	 */
	public String getCreatedAt() {
		return mCreatedAt;
	}
	/**
	 * @param mCreatedAt the mCreatedAt to set
	 */
	public void setCreatedAt(String mCreatedAt) {
		this.mCreatedAt = mCreatedAt;
	}
	/**
	 * @return the mUpdatedBy
	 */
	public int getUpdatedBy() {
		return mUpdatedBy;
	}
	/**
	 * @param mUpdatedBy the mUpdatedBy to set
	 */
	public void setUpdatedBy(int mUpdatedBy) {
		this.mUpdatedBy = mUpdatedBy;
	}
	/**
	 * @return the mUpdatedAt
	 */
	public String getUpdatedAt() {
		return mUpdatedAt;
	}
	/**
	 * @param mUpdatedAt the mUpdatedAt to set
	 */
	public void setUpdatedAt(String mUpdatedAt) {
		this.mUpdatedAt = mUpdatedAt;
	}
	/**
	 * @return the mPublishStatus
	 */
	public boolean isPublishStatus() {
		return mPublishStatus;
	}
	/**
	 * @param mPublishStatus the mPublishStatus to set
	 */
	public void setPublishStatus(boolean mPublishStatus) {
		this.mPublishStatus = mPublishStatus;
	}
	/**
	 * @return the mIsDeleted
	 */
	public boolean isIsDeleted() {
		return mIsDeleted;
	}
	/**
	 * @param mIsDeleted the mIsDeleted to set
	 */
	public void setIsDeleted(boolean mIsDeleted) {
		this.mIsDeleted = mIsDeleted;
	}
	/**
	 * @return the mIsSynchable
	 */
	public boolean isIsSynchable() {
		return mIsSynchable;
	}
	/**
	 * @param mIsSynchable the mIsSynchable to set
	 */
	public void setIsSynchable(boolean mIsSynchable) {
		this.mIsSynchable = mIsSynchable;
	}
	/**
	 * @return the mCanCashRefundable
	 */
	public boolean isCanCashRefundable() {
		return mCanCashRefundable;
	}
	/**
	 * @param mCanCashRefundable the mCanCashRefundable to set
	 */
	public void setCanCashRefundable(boolean mCanCashRefundable) {
		this.mCanCashRefundable = mCanCashRefundable;
	}
	
	/**
	 * @return the mCanCardRefundable
	 */
	public boolean isCanCardRefundable() {
		return mCanCardRefundable;
	}
	/**
	 * @param mCanCardRefundable the mCanCardRefundable to set
	 */
	public void setCanCardRefundable(boolean mCanCardRefundable) {
		this.mCanCardRefundable = mCanCardRefundable;
	}
	
	/**
	 * @return the mCanCompanyRefundable
	 */
	public boolean isCanCompanyRefundable() {
		return mCanCompanyRefundable;
	}
	/**
	 * @param mCanCompanyRefundable the mCanCompanyRefundable to set
	 */
	public void setCanCompanyRefundable(boolean mCanCompanyRefundable) {
		this.mCanCompanyRefundable = mCanCompanyRefundable;
	}
	/**
	 * @return the mCanVoucherRefundable
	 */
	public boolean isCanVoucherRefundable() {
		return mCanVoucherRefundable;
	}
	/**
	 * @param mCanVoucherRefundable the mCanVoucherRefundable to set
	 */
	public void setCanVoucherRefundable(boolean mCanVoucherRefundable) {
		this.mCanVoucherRefundable = mCanVoucherRefundable;
	}
	/**
	 * @return the mCashRefundAccountNo
	 */
	public String getCashRefundAccountNo() {
		return mCashRefundAccountNo;
	}
	/**
	 * @param mCashRefundAccountNo the mCashRefundAccountNo to set
	 */
	public void setCashRefundAccountNo(String mCashRefundAccountNo) {
		this.mCashRefundAccountNo = mCashRefundAccountNo;
	}
	/**
	 * @return the mAlternativeRefundMethodForVoucher
	 */
	public boolean isAlternativeRefundMethodForVoucher() {
		return mAlternativeRefundMethodForVoucher;
	}
	/**
	 * @param mAlternativeRefundMethodForVoucher the mAlternativeRefundMethodForVoucher to set
	 */
	public void setAlternativeRefundMethodForVoucher(
			boolean mAlternativeRefundMethodForVoucher) {
		this.mAlternativeRefundMethodForVoucher = mAlternativeRefundMethodForVoucher;
	}
	/**
	 * @return the mCanCashRound
	 */
	public boolean isCanCashRound() {
		return mCanCashRound;
	}
	/**
	 * @param mCanCashRound the mCanCashRound to set
	 */
	public void setCanCashRound(boolean mCanCashRound) {
		this.mCanCashRound = mCanCashRound;
	}
	/**
	 * @return the mCanCardRound
	 */
	public boolean isCanCardRound() {
		return mCanCardRound;
	}
	/**
	 * @param mCanCardRound the mCanCardRound to set
	 */
	public void setCanCardRound(boolean mCanCardRound) {
		this.mCanCardRound = mCanCardRound;
	}
	/**
	 * @return the mCanCompanyRound
	 */
	public boolean isCanCompanyRound() {
		return mCanCompanyRound;
	}
	/**
	 * @param mCanCompanyRound the mCanCompanyRound to set
	 */
	public void setCanCompanyRound(boolean mCanCompanyRound) {
		this.mCanCompanyRound = mCanCompanyRound;
	}
	/**
	 * @return the mCanVoucherRound
	 */
	public boolean isCanVoucherRound() {
		return mCanVoucherRound;
	}
	/**
	 * @param mCanVoucherRound the mCanVoucherRound to set
	 */
	public void setCanVoucherRound(boolean mCanVoucherRound) {
		this.mCanVoucherRound = mCanVoucherRound;
	}
	/**
	 * @return the mCanPayOnline
	 */
	public boolean isCanPayOnline() {
		return mCanPayOnline;
	}
	/**
	 * @param mCanPayOnline the mCanPayOnline to set
	 */
	public void setCanPayOnline(boolean mCanPayOnline) {
		this.mCanPayOnline = mCanPayOnline;
	}
	/**
	 * @return the mCanOnlineRefund
	 */
	public boolean isCanOnlineRefund() {
		return mCanOnlineRefund;
	}
	/**
	 * @param mCanOnlineRefund the mCanOnlineRefund to set
	 */
	public void setCanOnlineRefund(boolean mCanOnlineRefund) {
		this.mCanOnlineRefund = mCanOnlineRefund;
	}
	/**
	 * @return the mCanOnlineRound
	 */
	public boolean isCanOnlineRound() {
		return mCanOnlineRound;
	}
	/**
	 * @param mCanOnlineRound the mCanOnlineRound to set
	 */
	public void setCanOnlineRound(boolean mCanOnlineRound) {
		this.mCanOnlineRound = mCanOnlineRound;
	}
	/**
	 * @return the titleCash
	 */
	public String getTitleCash() {
		return mTitleCash;
	}
	/**
	 * @param titleCash the titleCash to set
	 */
	public void setTitleCash(String titleCash) {
		this.mTitleCash = titleCash;
	}
	/**
	 * @return the titleCard
	 */
	public String getTitleCard() {
		return mTitleCard;
	}
	/**
	 * @param titleCard the titleCard to set
	 */
	public void setTitleCard(String titleCard) {
		this.mTitleCard = titleCard;
	}
	/**
	 * @return the titleCompany
	 */
	public String getTitleCompany() {
		return mTitleCompany;
	}
	/**
	 * @param titleCompany the titleCompany to set
	 */
	public void setTitleCompany(String titleCompany) {
		this.mTitleCompany = titleCompany;
	}
	/**
	 * @return the titleOnline
	 */
	public String getTitleOnline() {
		return mTitleOnline;
	}
	/**
	 * @param titleOnline the titleOnline to set
	 */
	public void setTitleOnline(String titleOnline) {
		this.mTitleOnline = titleOnline;
	}
	/**
	 * @return the titleVoucher
	 */
	public String getTitleVoucher() {
		return mTitleVoucher;
	}
	/**
	 * @param titleVoucher the titleVoucher to set
	 */
	public void setTitleVoucher(String titleVoucher) {
		this.mTitleVoucher = titleVoucher;
	}
	
	
	

}
