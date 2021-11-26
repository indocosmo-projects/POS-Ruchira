/**
 * 
 */
package com.indocosmo.pos.data.beans;

import javax.swing.KeyStroke;

import com.indocosmo.pos.common.PosLog;
import com.indocosmo.pos.data.beans.BeanPayModeBase.PaymentMode;
import com.indocosmo.pos.forms.components.objectbrowser.IPosBrowsableItem;

/**
 * @author jojesh
 *
 */
public final class BeanOrderPayment implements Cloneable,IPosBrowsableItem{

	private String mId;
//	private int mAutoId;					
	private String mOrderId;
	private String mOrderPaymentHdrId;
	private String mInvoiceNo;
	
	private PaymentMode mPaymentMode;					
	private double mPaidAmount;	
	private double mPartialBalance;
//	private double mBalanceAmount;
	
	private String mCardName;					
	private String mCardType;					
	private String mCardNo;					
	private String mNameOnCard;					
	private int mCardExpiryMonth;					
	private int mCardExpiryYear;					
	private String mCardApprovalCode;
	private String mAccount;
	
	private int mCompanyId;					
	private String mCompanyName;
	
	private int mVoucherId;					
	private String mVoucherName;					
	private double mVoucherValue;					
	private int mVoucherCount;	
	
	private BeanDiscount mDiscount;
	
	private int mCashierID;
	private String mPaymentDate;
	private String mPaymentTime;
	private int mShiftId;
	private boolean mIsRepayment;
	private boolean mIsNew=false;
	private boolean mIsAdvance;
	private String mQueueNo;
	private boolean mIsVoucherBalanceReturned;
	
	private String mCreatedAt;
	private int mCreatedBy;
	private String mUpdatedAt;
	private int mUpdatedBy;
	@Override
	public BeanOrderPayment clone(){
		BeanOrderPayment cloneObject = null;
		try {
			cloneObject = (BeanOrderPayment) super.clone();
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
//	/**
//	 * @return the mAutoId
//	 */
//	public int getAutoId() {
//		return mAutoId;
//	}
//	/**
//	 * @param AutoId the mAutoId to set
//	 */
//	public void setAutoId(int autoId) {
//		this.mAutoId = autoId;
//	}
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
	 * @return the mBalanceAmount
	 */
//	public double getBalanceAmount() {
//		return mBalanceAmount;
//	}
//	/**
//	 * @param BalanceAmount the mBalanceAmount to set
//	 */
//	public void setBalanceAmount(double cashBalance) {
//		this.mBalanceAmount = cashBalance;
//	}
	/**
	 * @return the mCardName
	 */
	public String getCardName() {
		return mCardName;
	}
	/**
	 * @param CardName the mCardName to set
	 */
	public void setCardName(String cardName) {
		this.mCardName = cardName;
	}
	/**
	 * @return the mCardType
	 */
	public String getCardType() {
		return mCardType;
	}
	/**
	 * @param CardType the mCardType to set
	 */
	public void setCardType(String cardType) {
		this.mCardType = cardType;
	}
	/**
	 * @return the mCardNo
	 */
	public String getCardNo() {
		return mCardNo;
	}
	/**
	 * @param CardNo the mCardNo to set
	 */
	public void setCardNo(String cardNo) {
		this.mCardNo = cardNo;
	}
	/**
	 * @return the mNameOnCard
	 */
	public String getNameOnCard() {
		return mNameOnCard;
	}
	/**
	 * @param NameOnCard the mNameOnCard to set
	 */
	public void setNameOnCard(String nameOnCard) {
		this.mNameOnCard = nameOnCard;
	}
	/**
	 * @return the mCardExpiryMonth
	 */
	public int getCardExpiryMonth() {
		return mCardExpiryMonth;
	}
	/**
	 * @param CardExpiryMonth the mCardExpiryMonth to set
	 */
	public void setCardExpiryMonth(int cardExpiryMonth) {
		this.mCardExpiryMonth = cardExpiryMonth;
	}
	/**
	 * @return the mCardExpiryYear
	 */
	public int getCardExpiryYear() {
		return mCardExpiryYear;
	}
	/**
	 * @param CardExpiryYear the mCardExpiryYear to set
	 */
	public void setCardExpiryYear(int cardExpiryYear) {
		this.mCardExpiryYear = cardExpiryYear;
	}
	/**
	 * @return the mCardApprovalCode
	 */
	public String getCardApprovalCode() {
		return mCardApprovalCode;
	}
	/**
	 * @param CardApprovalCode the mCardApprovalCode to set
	 */
	public void setCardApprovalCode(String cardApprovalCode) {
		this.mCardApprovalCode = cardApprovalCode;
	}
	/**
	 * @return the mCompanyId
	 */
	public int getCompanyId() {
		return mCompanyId;
	}
	/**
	 * @param CompanyId the mCompanyId to set
	 */
	public void setCompanyId(int companyId) {
		this.mCompanyId = companyId;
	}
	/**
	 * @return the mCompanyName
	 */
	public String getCompanyName() {
		return mCompanyName;
	}
	/**
	 * @param CompanyName the mCompanyName to set
	 */
	public void setCompanyName(String companyName) {
		this.mCompanyName = companyName;
	}
	/**
	 * @return the mVoucherId
	 */
	public int getVoucherId() {
		return mVoucherId;
	}
	/**
	 * @param VoucherId the mVoucherId to set
	 */
	public void setVoucherId(int voucherId) {
		this.mVoucherId = voucherId;
	}
	/**
	 * @return the mVoucherName
	 */
	public String getVoucherName() {
		return mVoucherName;
	}
	/**
	 * @param VoucherName the mVoucherName to set
	 */
	public void setVoucherName(String voucherName) {
		this.mVoucherName = voucherName;
	}
	/**
	 * @return the mVoucherValue
	 */
	public double getVoucherValue() {
		return mVoucherValue;
	}
	/**
	 * @param VoucherValue the mVoucherValue to set
	 */
	public void setVoucherValue(double voucherValue) {
		this.mVoucherValue = voucherValue;
	}
	/**
	 * @return the mVoucherCount
	 */
	public int getVoucherCount() {
		return mVoucherCount;
	}
	/**
	 * @param VoucherCount the mVoucherCount to set
	 */
	public void setVoucherCount(int voucherCount) {
		this.mVoucherCount = voucherCount;
	}
	/**
	 * @return the mVoucherBalance
	 */
//	public double getVoucherBalance() {
//		return mVoucherBalance;
//	}
//	/**
//	 * @param VoucherBalance the mVoucherBalance to set
//	 */
//	public void setVoucherBalance(double voucherBalance) {
//		this.mVoucherBalance = voucherBalance;
//	}					
	/**
	 * @return the cashierID
	 */
	public int getCashierID() {
		return mCashierID;
	}
	/**
	 * @param cashierID the cashierID to set
	 */
	public void setCashierID(int cashierID) {
		mCashierID = cashierID;
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
	 * @return the mDiscount
	 */
	public BeanDiscount getDiscount() {
		return mDiscount;
	}

	/**
	 * @param mDiscount the mDiscount to set
	 */
	public void setDiscount(BeanDiscount discount) {
		this.mDiscount = discount;
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
	 * @return the mIsVoucherBalanceREturned
	 */
	public boolean isVoucherBalanceReturned() {
		return mIsVoucherBalanceReturned;
	}

	/**
	 * @param mIsVoucherBalanceREturned the mIsVoucherBalanceREturned to set
	 */
	public void setVoucherBalanceReturned(boolean mIsVoucherBalanceREturned) {
		this.mIsVoucherBalanceReturned = mIsVoucherBalanceREturned;
	}

	/**
	 * @return the mAccount
	 */
	public String getAccount() {
		return mAccount;
	}

	/**
	 * @param mAccount the mAccount to set
	 */
	public void setAccount(String mAccount) {
		this.mAccount = mAccount;
	}

	/**
	 * @return the mPartialBalance
	 */
	public double getPartialBalance() {
		return mPartialBalance;
	}

	/**
	 * @param mPartialBalance the mPartialBalance to set
	 */
	public void setPartialBalance(double mPartialBalance) {
		this.mPartialBalance = mPartialBalance;
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

	/* (non-Javadoc)
	 * @see com.indocosmo.pos.forms.components.objectbrowser.IPosBrowsableItem#getItemCode()
	 */
	@Override
	public Object getItemCode() {
		
		String displayCode="";
		
		switch (getPaymentMode()) {
		case Card:
			displayCode=getCardNo();
		default:
			break;
		}
		return displayCode;
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.pos.forms.components.objectbrowser.IPosBrowsableItem#getDisplayText()
	 */
	@Override
	public String getDisplayText() {
		
		String displayText="";
		
		switch (getPaymentMode()) {
		case Card:
			displayText=getCardNo();
		default:
			break;
		}
		
		return displayText;
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.pos.forms.components.objectbrowser.IPosBrowsableItem#isVisibleInUI()
	 */
	@Override
	public boolean isVisibleInUI() {
		// TODO Auto-generated method stub
		return true;
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.pos.forms.components.objectbrowser.IPosBrowsableItem#getKeyStroke()
	 */
	@Override
	public KeyStroke getKeyStroke() {
		// TODO Auto-generated method stub
		return null;
	}
}
