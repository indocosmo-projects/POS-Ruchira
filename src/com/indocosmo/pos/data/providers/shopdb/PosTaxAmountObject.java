package com.indocosmo.pos.data.providers.shopdb;

public final class PosTaxAmountObject {
	
	private double mTaxable_amount;
	private double mTax_1_amount;
	private double mTax_2_amount;
	private double mTax_3_amount;
	private double mService_tax_amount;
	private double mGST_amount;
	private double mTaxRoundAdjustment;
	
	public PosTaxAmountObject(){
		mTaxable_amount=0;
		mTax_1_amount=0;
		mTax_2_amount=0;
		mTax_3_amount=0;
		mService_tax_amount=0;
		mGST_amount=0;
	}
	/**
	 * @return the mTax_1_amount
	 */
	public double getTaxOneAmount() {
		return mTax_1_amount;
	}
	/**
	 * @param mTax_1_amount the mTax_1_amount to set
	 */
	public void setTaxOneAmount(double amount) {
		this.mTax_1_amount = amount;
	}
	/**
	 * @return the mTax_2_amount
	 */
	public double getTaxTwoAmount() {
		return mTax_2_amount;
	}
	/**
	 * @param mTax_2_amount the mTax_2_amount to set
	 */
	public void setTaxTwoAmount(double amount) {
		this.mTax_2_amount = amount;
	}
	/**
	 * @return the mTax_3_amount
	 */
	public double getTaxThreeAmount() {
		return mTax_3_amount;
	}
	/**
	 * @param mTax_3_amount the mTax_3_amount to set
	 */
	public void setTaxThreeAmount(double amount) {
		this.mTax_3_amount = amount;
	}
	/**
	 * @return the mService_tax_amount
	 */
	public double getServiceTaxAmount() {
		return mService_tax_amount;
	}
	/**
	 * @param mService_tax_amount the mService_tax_amount to set
	 */
	public void setServiceTaxAmount(double amount) {
		this.mService_tax_amount = amount;
	}
	/**
	 * @return the mGST_amount
	 */
	public double getGSTAmount() {
		return mGST_amount;
	}
	/**
	 * @param mGST_amount the mGST_amount to set
	 */
	public void setGSTAmount(double mGST_amount) {
		this.mGST_amount = mGST_amount;
	}
	/**
	 * @return the mTaxable_amount
	 */
	public double getTaxableAmount() {
		return mTaxable_amount;
	}
	/**
	 * @param mTaxable_amount the mTaxable_amount to set
	 */
	public void setTaxableAmount(double mTaxable_amount) {
		this.mTaxable_amount = mTaxable_amount;
	}
	
	public double getTotalTaxAmount(){
		return 	mTax_1_amount+
				mTax_2_amount+
				mTax_3_amount+
				mService_tax_amount+
				mGST_amount;
	}
	/**
	 * @return the Tax Round Adjustment
	 */
	public double getTaxRoundAdjustment() {
		return mTaxRoundAdjustment;
	}
	/**
	 * @param Tax Round Adjustment 
	 */
	public void setTaxRoundAdjustment(double taxRoundAdjustment) {
		this.mTaxRoundAdjustment = taxRoundAdjustment;
	}
	
}
