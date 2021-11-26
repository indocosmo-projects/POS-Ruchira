/**
 * 
 */
package com.indocosmo.pos.data.beans;

import javax.swing.KeyStroke;


/**
 * @author deepak
 *
 */
public class BeanBankCard extends BeanMasterBase {
	
	private boolean mIsValid;
    private boolean mIsRefundable;
	private boolean mAlternativeRefundMethod;
	private boolean mIsDeleted;
//	private String mIinPrefix;
	private int mIinPrefixRangeFrom;
	private int mIinPrefixRangeTo;

	/**
	 * @return the mIsValid
	 */
	public boolean isValid() {
		return mIsValid;
	}

	/**
	 * @param mIsValid the mIsValid to set
	 */
	public void setIsValid(boolean mIsValid) {
		this.mIsValid = mIsValid;
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.pos.components.objectbrowser.IPosBrowsableItem#getCaption()
	 */
	@Override
	public String getDisplayText() {
		// TODO Auto-generated method stub
		return getName();
	}

	/**
	 * @return the mIsRefundable
	 */
	public boolean isIsRefundable() {
		return mIsRefundable;
	}

	/**
	 * @param mIsRefundable the mIsRefundable to set
	 */
	public void setIsRefundable(boolean mIsRefundable) {
		this.mIsRefundable = mIsRefundable;
	}

	/**
	 * @return the mAlternativeRefundMethod
	 */
	public boolean isAlternativeRefundMethod() {
		return mAlternativeRefundMethod;
	}

	/**
	 * @param mAlternativeRefundMethod the mAlternativeRefundMethod to set
	 */
	public void setAlternativeRefundMethod(boolean mAlternativeRefundMethod) {
		this.mAlternativeRefundMethod = mAlternativeRefundMethod;
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
	 * @return the mIinPrefixRangeFrom
	 */
	public int getIinPrefixRangeFrom() {
		return mIinPrefixRangeFrom;
	}

	/**
	 * @param mIinPrefixRangeFrom the mIinPrefixRangeFrom to set
	 */
	public void setIinPrefixRangeFrom(int mIinPrefixRangeFrom) {
		this.mIinPrefixRangeFrom = mIinPrefixRangeFrom;
	}

	/**
	 * @return the mIinPrefixRangeTo
	 */
	public int getIinPrefixRangeTo() {
		return mIinPrefixRangeTo;
	}

	/**
	 * @param mIinPrefixRangeTo the mIinPrefixRangeTo to set
	 */
	public void setIinPrefixRangeTo(int mIinPrefixRangeTo) {
		this.mIinPrefixRangeTo = mIinPrefixRangeTo;
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.pos.components.objectbrowser.IPosBrowsableItem#getItemCode()
	 */
	@Override
	public Object getItemCode() {
		return getCode();
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.pos.forms.components.objectbrowser.IPosBrowsableItem#getKeyStroke()
	 */
	@Override
	public KeyStroke getKeyStroke() {
		// TODO Auto-generated method stub
		return null;
	}

//	/**
//	 * @return the mIinPrefix
//	 */
//	public String getIinPrefix() {
//		return mIinPrefix;
//	}
//
//	/**
//	 * @param mIinPrefix the mIinPrefix to set
//	 */
//	public void setIinPrefix(String mIinPrefix) {
//		this.mIinPrefix = mIinPrefix;
//	}
	

}
