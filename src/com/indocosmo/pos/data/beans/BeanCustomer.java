/**
 * 
 */
package com.indocosmo.pos.data.beans;

import javax.swing.KeyStroke;


/**
 * @author jojesh
 *
 */
public  class BeanCustomer extends BeanMasterBase{

	private String mCategory;
	private String mPhoneNumber;
	private String mCardNumber;
	private String mAccountCode;
	private boolean mIsArCompany;
	private BeanCustomerType mCustType;
	private String mAddress;
	private String mCstNo;
	private String mTinNo;
	private String mStateCode;
	private String mState;
	private String mCity;
	private String mCountry;
	private String mBankName;
	private String mBankBranch;
	private String mBankAddress;
	private String mBankBrancIFSCCode;
	private String mBankMICRCode;
	private String mBankAccountNo;
	private BeanGstRegisterType gstRegisterType;
	private BeanGstPartyType gstPartyType;
	
	private String mAddress2;
	private String mAddress3;
	private String mAddress4;
	private String mPhoneNo_two;
	/**
	 * 
	 */
	public BeanCustomer() {
	
	}
 
	/**
	 * @return the mCategory
	 */
	public String getCategory() {
		return mCategory;
	}

	/**
	 * @param mCategory the mCategory to set
	 */
	public void setCategory(String mCategory) {
		this.mCategory = mCategory;
	}

	public String getCardNumber() {
		return mCardNumber;
	}
	public void setCardNumber(String cardNumber) {
		this.mCardNumber = cardNumber;
	}
	
	public BeanCustomerType getCustType() {
		return mCustType;
	}
	public void setCustType(BeanCustomerType custType) {
		this.mCustType = custType;
	}
	@Override
	public String getDisplayText() {
		return getName();
	}
	/**
	 * @return the mAccountCode
	 */
	public String getAccountCode() {
		return mAccountCode;
	}
	/**
	 * @param mAccountCode the mAccountCode to set
	 */
	public void setAccountCode(String mAccountCode) {
		this.mAccountCode = mAccountCode;
	}
	/**
	 * @return the mIsArCompany
	 */
	public boolean isIsArCompany() {
		return mIsArCompany;
	}
	/**
	 * @param Is ArCompany 
	 */
	public void setIsArCompany(boolean isArCompany) {
		this.mIsArCompany = isArCompany;
	}
	/**
	 * @return the mAddress
	 */
	public String getAddress() {
		return mAddress;
	}
	/**
	 * @param mAddress the mAddress to set
	 */
	public void setAddress(String mAddress) {
		this.mAddress = mAddress;
	}
	/**
	 * @return the mCstNo
	 */
	public String getCSTNo() {
		return mCstNo;
	}
	/**
	 * @param mCstNo the mCstNo to set
	 */
	public void setCSTNo(String mCstNo) {
		this.mCstNo = mCstNo;
	}
	/**
	 * @return the mCompanyTaxNo
	 */
	public String getTinNo() {
		return mTinNo;
	}
	/**
	 * @param mCompanyTaxNo the mCompanyTaxNo to set
	 */
	public void setTinNo(String mTinNo) {
		this.mTinNo = mTinNo;
	}
	/**
	 * @return the mStateCode
	 */
	public String getStateCode() {
		return mStateCode;
	}
	/**
	 * @param mStateCode the mStateCode to set
	 */
	public void setStateCode(String mStateCode) {
		this.mStateCode = mStateCode;
	}
	
	/**
	 * @return the mState
	 */
	public String getState() {
		return mState;
	}
	/**
	 * @param mState the mState to set
	 */
	public void setState(String mState) {
		this.mState = mState;
	}
	/**
	 * @return the mCity
	 */
	public String getCity() {
		return mCity;
	}
	/**
	 * @param mCity the mCity to set
	 */
	public void setCity(String mCity) {
		this.mCity = mCity;
	}
	/***
	 * Below functions are used to get data for displaying in the search window
	 */
	
	public static String[] SEARCH_FIELD_LIST = { "getCode",
			"getName","getCardNumber","getPhoneNumber"};
	public static String[] SEARCH_COLUMN_NAMES = { "Code",
			"Name" ,"Card Number", "Phone"};
	public static int[] SEARCH_FIELD_WIDTH = { 75, 250,150};
	/* (non-Javadoc)
	 * @see com.indocosmo.pos.components.search.IPosSearchableItem#getFiledList()
	 */
	@Override
	public String[] getFieldList() {
		return SEARCH_FIELD_LIST;
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.pos.components.search.IPosSearchableItem#getFiledTitleList()
	 */
	@Override
	public String[] getFieldTitleList() {
		return SEARCH_COLUMN_NAMES;
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.pos.components.search.IPosSearchableItem#getFiledWidthList()
	 */
	@Override
	public int[] getFieldWidthList() {
		return SEARCH_FIELD_WIDTH;
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.pos.forms.components.objectbrowser.IPosBrowsableItem#getKeyStroke()
	 */
	@Override
	public KeyStroke getKeyStroke() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @return the mPhoneNumber
	 */
	public String getPhoneNumber() {
		return mPhoneNumber;
	}

	/**
	 * @param mPhoneNumber the mPhoneNumber to set
	 */
	public void setPhoneNumber(String mPhoneNumber) {
		this.mPhoneNumber = mPhoneNumber;
	}

	/**
	 * @return the bankName
	 */
	public String getBankName() {
		return mBankName;
	}

	/**
	 * @param bankName the bankName to set
	 */
	public void setBankName(String bankName) {
		this.mBankName = bankName;
	}

	/**
	 * @return the bankBranch
	 */
	public String getBankBranch() {
		return mBankBranch;
	}

	/**
	 * @param bankBranch the bankBranch to set
	 */
	public void setBankBranch(String bankBranch) {
		this.mBankBranch = bankBranch;
	}

	/**
	 * @return the bankAddress
	 */
	public String getBankAddress() {
		return mBankAddress;
	}

	/**
	 * @param bankAddress the bankAddress to set
	 */
	public void setBankAddress(String bankAddress) {
		this.mBankAddress = bankAddress;
	}

	/**
	 * @return the bankBrancIFSCCode
	 */
	public String getBankBrancIFSCCode() {
		return mBankBrancIFSCCode;
	}

	/**
	 * @param bankBrancIFSCCode the bankBrancIFSCCode to set
	 */
	public void setBankBrancIFSCCode(String bankBrancIFSCCode) {
		this.mBankBrancIFSCCode = bankBrancIFSCCode;
	}

	/**
	 * @return the bankMICRCode
	 */
	public String getBankMICRCode() {
		return mBankMICRCode;
	}

	/**
	 * @param bankMICRCode the bankMICRCode to set
	 */
	public void setBankMICRCode(String bankMICRCode) {
		this.mBankMICRCode = bankMICRCode;
	}

	/**
	 * @return the bankAccountNo
	 */
	public String getBankAccountNo() {
		return mBankAccountNo;
	}

	/**
	 * @param bankAccountNo the bankAccountNo to set
	 */
	public void setBankAccountNo(String bankAccountNo) {
		this.mBankAccountNo = bankAccountNo;
	}

	/**
	 * @return the mCountry
	 */
	public String getCountry() {
		return mCountry;
	}

	/**
	 * @param mCountry the mCountry to set
	 */
	public void setCountry(String mCountry) {
		this.mCountry = mCountry;
	}
	
	/**
	 * @return the gstRegisterType
	 */
	public BeanGstRegisterType getGstRegisterType() {
		return gstRegisterType;
	}
	/**
	 * @param gstRegisterType the gstRegisterType to set
	 */
	public void setGstRegisterType(BeanGstRegisterType gstRegisterType) {
		this.gstRegisterType = gstRegisterType;
	}
	/**
	 * @return the gstPartyType
	 */
	public BeanGstPartyType getGstPartyType() {
		return gstPartyType;
	}
	/**
	 * @param gstPartyType the gstPartyType to set
	 */
	public void setGstPartyType(BeanGstPartyType gstPartyType) {
		this.gstPartyType = gstPartyType;
	}

	/**
	 * @return the mAddress2
	 */
	public String getAddress2() {
		return mAddress2;
	}
	/**
	 * @param mAddress2 the mAddress2 to set
	 */
	public void setAddress2(String mAddress2) {
		this.mAddress2 = mAddress2;
	}
	/**
	 * @return the mAddress3
	 */
	public String getAddress3() {
		return mAddress3;
	}
	/**
	 * @param mAddress3 the mAddress3 to set
	 */
	public void setAddress3(String mAddress3) {
		this.mAddress3 = mAddress3;
	}
	/**
	 * @return the mAddress4
	 */
	public String getAddress4() {
		return mAddress4;
	}
	/**
	 * @param mAddress4 the mAddress4 to set
	 */
	public void setAddress4(String mAddress4) {
		this.mAddress4 = mAddress4;
	}
	/**
	 * @return the mPhoneNo_two
	 */
	public String getPhoneNumber2() {
		return mPhoneNo_two;
	}
	/**
	 * @param mPhoneNo_two the mPhoneNo_two to set
	 */
	public void setPhoneNumber2(String mPhoneNo_two) {
		this.mPhoneNo_two = mPhoneNo_two;
	}

}
