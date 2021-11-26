package com.indocosmo.pos.data.beans;

import javax.swing.KeyStroke;

import com.indocosmo.pos.common.PosLog;

public final class BeanCoupon extends BeanMasterBase implements Cloneable{
				
	private double mValue;
	private int mCount;
	private int mVoucherTypes;
	private boolean mOverridable;
	private boolean mChangePayable;
	private String mAccountCode;
	


	public BeanCoupon() {

	}
	
	public BeanCoupon(String code, String name) {
		this.setCode(code);
		this.setName(name);
	}

	/**
	 * @return the mValue
	 */
	public final double getValue() {
		return mValue;
	}

	/**
	 * @param mValue the mValue to set
	 */
	public final void setValue(double value) {
		this.mValue = value;
	}

	/**
	 * @return the mCount
	 */
	public  int getCount() {
		return mCount;
	}

	/**
	 * @param mCount the mCount to set
	 */
	public  void setCount(int count) {
		this.mCount = count;
	}

	/**
	 * @return the mVoucherTypes
	 */
	public int getVoucherTypes() {
		return mVoucherTypes;
	}

	/**
	 * @param mVoucherTypes the mVoucherTypes to set
	 */
	public void setVoucherTypes(int mVoucherTypes) {
		this.mVoucherTypes = mVoucherTypes;
	}

	/**
	 * @return the mOverridable
	 */
	public boolean isOverridable() {
		return mOverridable;
	}

	/**
	 * @param mOverridable the mOverridable to set
	 */
	public void setOverridable(boolean mOverridable) {
		this.mOverridable = mOverridable;
	}

	/**
	 * @return the mChangePayable
	 */
	public boolean isChangePayable() {
		return mChangePayable;
	}

	/**
	 * @param mChangePayable the mChangePayable to set
	 */
	public void setChangePayable(boolean mChangePayable) {
		this.mChangePayable = mChangePayable;
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
	
	
	public double getTotalValue(){
		return mCount*mValue;
	}
	
	public BeanCoupon clone() {
		BeanCoupon cloneObject=null;
		try {
			cloneObject=(BeanCoupon) super.clone();
		} catch (CloneNotSupportedException e) {
			PosLog.write(this,"clone",e);
		}
		return cloneObject;
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
