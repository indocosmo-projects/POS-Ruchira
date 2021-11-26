/**
 * 
 */
package com.indocosmo.pos.data.beans;

import javax.swing.KeyStroke;

import com.indocosmo.pos.common.PosEnvSettings;
import com.indocosmo.pos.common.PosLog;
import com.indocosmo.pos.data.providers.shopdb.PosCustomerProvider;


/**
 * @author sandhya
 * 
 */
public class BeanOrderCustomer  extends BeanMasterBase implements Cloneable {
	
	private String orderID;
	private String mAddress;
	private String mAddress2;
	private String mAddress3;
	private String mAddress4;
	private String mCity;
	private String mState;
	private String mStateCode;
	private String mCountry;
	private String mPhoneNo;
	private String mPhoneNo_two;
	private String mTinNo;
	private BeanGstRegisterType mGstRegisterType;
	private BeanGstPartyType mGstPartyType;
	private int mCutomerTypeId;
	 
	@Override
	public BeanOrderCustomer clone(){
		BeanOrderCustomer cloneObject = null;
		try {
			cloneObject = (BeanOrderCustomer) super.clone();
		} catch (CloneNotSupportedException e) {
			PosLog.write(this,"clone",e);
		}
		return cloneObject;
	}
	public BeanOrderCustomer(){
	}
	public BeanOrderCustomer(BeanCustomer customer){
		
		   this.mId = customer.getId();
		   this.mCode=customer.getCode();
		    this.mName = customer.getName();
		    this.mAddress = customer.getAddress();
		    this.mCity = customer.getCity();
		    if(new PosCustomerProvider().getDefaultCustomer().equals(customer)){
			    this.mState =PosEnvSettings.getInstance().getShop().getState();
			    this.mStateCode = PosEnvSettings.getInstance().getShop().getStateCode();
			    this.mCountry = PosEnvSettings.getInstance().getShop().getCountry();
		    }else{
		    	this.mState = customer.getState();
			    this.mStateCode = customer.getStateCode();
			    this.mCountry = customer.getCountry();
		    }
		    this.mPhoneNo = customer.getPhoneNumber();
		    this.mTinNo = customer.getTinNo();
		    this.mGstRegisterType=customer.getGstRegisterType();
		    this.mGstPartyType=customer.getGstPartyType();
	}
	/**
	 * @return the orderID
	 */
	public String getOrderID() {
		return orderID;
	}
	/**
	 * @param orderID the orderID to set
	 */
	public void setOrderID(String orderID) {
		this.orderID = orderID;
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
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "BeanOrderCustomer [orderID=" + orderID + ", mAddress=" + mAddress + ", mAddress2=" + mAddress2
				+ ", mAddress3=" + mAddress3 + ", mAddress4=" + mAddress4 + ", mCity=" + mCity + ", mState=" + mState
				+ ", mStateCode=" + mStateCode + ", mCountry=" + mCountry + ", mPhoneNo=" + mPhoneNo + ", mPhoneNo_two="
				+ mPhoneNo_two + ", mTinNo=" + mTinNo + ", mGstRegisterType=" + mGstRegisterType + ", mGstPartyType="
				+ mGstPartyType + ", mCutomerTypeId=" + mCutomerTypeId + "]";
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
	 * @return the mPhoneNo
	 */
	public String getPhoneNumber() {
		return mPhoneNo;
	}
	/**
	 * @param mPhoneNo the mPhoneNo to set
	 */
	public void setPhoneNumber(String mPhoneNo) {
		this.mPhoneNo = mPhoneNo;
	}
	/**
	 * @return the mTinNo
	 */
	public String getTinNo() {
		return mTinNo;
	}
	/**
	 * @param mTinNo the mTinNo to set
	 */
	public void setTinNo(String mTinNo) {
		this.mTinNo = mTinNo;
	}
	
	/**
	 * @return the gstRegisterType
	 */
	public BeanGstRegisterType getGstRegisterType() {
		return mGstRegisterType;
	}
	/**
	 * @param gstRegisterType the gstRegisterType to set
	 */
	public void setGstRegisterType(BeanGstRegisterType gstRegisterType) {
		this.mGstRegisterType = gstRegisterType;
	}
	/**
	 * @return the gstPartyType
	 */
	public BeanGstPartyType getGstPartyType() {
		return mGstPartyType;
	}
	/**
	 * @param gstPartyType the gstPartyType to set
	 */
	public void setGstPartyType(BeanGstPartyType gstPartyType) {
		this.mGstPartyType = gstPartyType;
	}
 
	/**
	 * @return the cutomerTypeId
	 */
	public int getCutomerTypeId() {
		return mCutomerTypeId;
	}
	/**
	 * @param cutomerTypeId the cutomerTypeId to set
	 */
	public void setCutomerTypeId(int cutomerTypeId) {
		this.mCutomerTypeId = cutomerTypeId;
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
	
	/**
	 * @return the mPhoneNo_two
	 */
	public String getContactNumbers() {
		  String nos= (mPhoneNo==null || mPhoneNo.trim().equals(""))?"":mPhoneNo   ;
		  nos =nos +   ((mPhoneNo_two==null|| mPhoneNo_two.trim().equals(""))?"":((nos.trim().equals("")?"":"/")+mPhoneNo_two)); 
		return nos;
	}
	/***
	 * Below functions are used to get data for displaying in the search window
	 */
	
	public static String[] SEARCH_FIELD_LIST = {"getName","getContactNumbers","getAddress"};
	public static String[] SEARCH_COLUMN_NAMES = {"Name" ,"Phone","Address"};
	public static int[] SEARCH_FIELD_WIDTH = {170 , 250,300};
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
	 * @see com.indocosmo.pos.formSelectedCustomerms.components.objectbrowser.IPosBrowsableItem#getKeyStroke()
	 */
	@Override
	public KeyStroke getKeyStroke() {
		// TODO Auto-generated method stub
		return null;
	}


 
}
