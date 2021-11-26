package com.indocosmo.pos.data.beans;

import java.util.ArrayList;


public final class BeanPayModeCoupon extends BeanPayModeBase {	
	
	private float mTenderedAmount;
	private float mChangeAmount;
	
	private ArrayList<BeanCoupon> mCouponItems;
	
	public BeanPayModeCoupon() {
		super();
		mCouponItems=new ArrayList<BeanCoupon>();
	}	
	
	/**
	 * @return the mTenderedAmount
	 */
	public final float getTenderedAmount() {
		return mTenderedAmount;
	}


	/**
	 * @param mTenderedAmount the mTenderedAmount to set
	 */
	public final void setTenderedAmount(float tenderedAmount) {
		this.mTenderedAmount = tenderedAmount;
	}


	/**
	 * @return the mChangeAmount
	 */
	public final float getChangeAmount() {
		return mChangeAmount;
	}


	/**
	 * @param mChangeAmount the mChangeAmount to set
	 */
	public final void setChangeAmount(float changeAmount) {
		this.mChangeAmount = changeAmount;
	}


	/**
	 * @return the mCouponItems
	 */
	public final ArrayList<BeanCoupon> getCouponItems() {
		return mCouponItems;
	}


	/**
	 * @param mCouponItems the mCouponItems to set
	 */
	public final void setCouponItems(ArrayList<BeanCoupon> couponItems) {
		this.mCouponItems = couponItems;
	}	
	

}
