package com.indocosmo.pos.data.beans;


public final class BeanPayModeCash extends BeanPayModeBase {	

	private double mPayCash;
	private double mPayChange;
	
	public BeanPayModeCash() {
		super();		
	}

	/**
	 * @return the mPayCash
	 */
	public final double getCash() {
		return mPayCash;
	}

	/**
	 * @param mPayCash the mPayCash to set
	 */
	public final void setCash(double cash) {
		this.mPayCash = cash;
	}

	/**
	 * @return the mPayChange
	 */
	public final double getChangeValue() {
		return mPayChange;
	}

	/**
	 * @param mPayChange the mPayChange to set
	 */
	public final void setChangeValue(double change) {
		this.mPayChange = change;
	}	
	
}
