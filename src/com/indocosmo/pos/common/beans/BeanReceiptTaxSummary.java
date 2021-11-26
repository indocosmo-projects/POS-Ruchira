/**
 * 
 */
package com.indocosmo.pos.common.beans;

/**
 * @author jojesh-13.2
 *
 */
public class BeanReceiptTaxSummary {

	private String tax_name;
	private double taxableAmount;
	private double taxAmount;
	private double tax1Amount;
	private double tax2Amount;
	private double tax3Amount;
	private double taxSCAmount;
	private double taxGSTAmount; /** Not actual GST. **/
	private double mTax_percentage;		
	private double mTax1_percentage;	
	private double mTax2_percentage;	
	private double mTax3_percentage;	
	private double mService_tax_percentage;
	private double mGst_percentage;
	
	private String mTaxOneName;
	private String mTaxTwoName;
	private String mTaxThreeName;
	private String mSCName;
	
		
	/**
	 * @return the tax_name
	 */
	public String getTaxName() {
		return tax_name;
	}
	/**
	 * @param tax_name the tax_name to set
	 */
	public void setTaxName(String tax_name) {
		this.tax_name = tax_name;
	}
	/**
	 * @return the taxableAmount
	 */
	public double getTaxableAmount() {
		return taxableAmount;
	}
	/**
	 * @param taxableAmount the taxableAmount to set
	 */
	public void setTaxableAmount(double taxableAmount) {
		this.taxableAmount = taxableAmount;
	}
	/**
	 * @return the taxAmount
	 */
	public double getTaxAmount() {
		return taxAmount;
	}
	/**
	 * @param taxAmount the taxAmount to set
	 */
	public void setTaxAmount(double taxAmount) {
		this.taxAmount = taxAmount;
	}
	
	/**
	 * @return the mTax_Percentage
	 */
	public double getTaxPercentage() {
		return mTax_percentage;
	}
	

	/**
	 * @param tax_1_percentage the mTax_1_Percentage to set
	 */
	public void setTaxPercentage(double percentage) {
		this.mTax_percentage = percentage;
	}
	/**
	 * @return the tax1Amount
	 */
	public double getTax1Amount() {
		return tax1Amount;
	}
	/**
	 * @param tax1Amount the tax1Amount to set
	 */
	public void setTax1Amount(double tax1Amount) {
		this.tax1Amount = tax1Amount;
	}
	/**
	 * @return the tax2Amount
	 */
	public double getTax2Amount() {
		return tax2Amount;
	}
	/**
	 * @param tax2Amount the tax2Amount to set
	 */
	public void setTax2Amount(double tax2Amount) {
		this.tax2Amount = tax2Amount;
	}
	/**
	 * @return the tax3Amount
	 */
	public double getTax3Amount() {
		return tax3Amount;
	}
	/**
	 * @param tax3Amount the tax3Amount to set
	 */
	public void setTax3Amount(double tax3Amount) {
		this.tax3Amount = tax3Amount;
	}
	/**
	 * @return the taxSCAmount
	 */
	public double getTaxSCAmount() {
		return taxSCAmount;
	}
	/**
	 * @param taxSCAmount the taxSCAmount to set
	 */
	public void setTaxSCAmount(double taxSCAmount) {
		this.taxSCAmount = taxSCAmount;
	}
	/**
	 * @return the taxGSTAmount
	 */
	public double getTaxGSTAmount() {
		return taxGSTAmount;
	}
	/**
	 * @param taxGSTAmount the taxGSTAmount to set
	 */
	public void setTaxGSTAmount(double taxGSTAmount) {
		this.taxGSTAmount = taxGSTAmount;
	}
	
	
	/**
	 * @return the mTax_Percentage
	 */
	public double getTaxOnePercentage() {
		return mTax1_percentage;
	}
	

	/**
	 * @param tax_1_percentage the mTax_1_Percentage to set
	 */
	public void setTaxOnePercentage(double percentage) {
		this.mTax1_percentage = percentage;
	}
	
	/**
	 * @return the mTax_Percentage
	 */
	public double getTaxTwoPercentage() {
		return mTax2_percentage;
	}
	

	/**
	 * @param tax_1_percentage the mTax_1_Percentage to set
	 */
	public void setTaxTwoPercentage(double percentage) {
		this.mTax2_percentage = percentage;
	}
	
	/**
	 * @return the mTax_Percentage
	 */
	public double getTaxThreePercentage() {
		return mTax3_percentage;
	}
	

	/**
	 * @param tax_1_percentage the mTax_1_Percentage to set
	 */
	public void setTaxThreePercentage(double percentage) {
		this.mTax3_percentage = percentage;
	}
	/**
	 * @return the mServiceCharge_Percentage
	 */
	public double getServiceTaxPercentage() {
		return mService_tax_percentage;
	}

	/**
	 * @param sc_percentage the mServiceCharge_Percentage to set
	 */
	public void setServiceTaxPercentage(double percentage) {
		this.mService_tax_percentage = percentage;
	}
	
	/**
	 * @return the mGst_percentage
	 */
	public double getGSTPercentage() {
		return mGst_percentage;
	}

	/**
	 * @param mGst_percentage the mGst_percentage to set
	 */
	public void setGSTPercentage(double percentage) {
		this.mGst_percentage = percentage;
	}
	/**
	 * @return the taxOneName
	 */
	public String getTaxOneName() {
		return mTaxOneName;
	}
	/**
	 * @param taxOneName the taxOneName to set
	 */
	public void setTaxOneName(String taxOneName) {
		this.mTaxOneName = taxOneName;
	}
	/**
	 * @return the taxTwoName
	 */
	public String getTaxTwoName() {
		return mTaxTwoName;
	}
	/**
	 * @param taxTwoName the taxTwoName to set
	 */
	public void setTaxTwoName(String taxTwoName) {
		this.mTaxTwoName = taxTwoName;
	}
	/**
	 * @return the taxThreeName
	 */
	public String getTaxThreeName() {
		return mTaxThreeName;
	}
	/**
	 * @param taxThreeName the taxThreeName to set
	 */
	public void setTaxThreeName(String taxThreeName) {
		this.mTaxThreeName = taxThreeName;
	}
	/**
	 * @return the taxSCName
	 */
	public String getSCName() {
		return mSCName;
	}
	/**
	 * @param taxSCName the taxSCName to set
	 */
	public void setSCName(String taxSCName) {
		this.mSCName = taxSCName;
	}

}
