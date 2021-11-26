
/**
 * This class represents the tax item
 * @author jojesh
 *
 */
package com.indocosmo.pos.data.beans;

import com.indocosmo.pos.common.PosEnvSettings;
import com.indocosmo.pos.data.providers.shopdb.PosTaxAmountObject;

public final class BeanTax implements Cloneable {

	private int mId;					
	private String mCode;					
	private String mName;					
//	private String mDescription;
	
	private String mTax_1_name;
	private boolean mIs_tax_1_applicable;	
	private double mTax_1_percentage;
	private double mTax1_refund_rate;
	
	private String mTax_2_name;
	private boolean mIs_tax_2_applicable;
	private double mTax_2_percentage;
	private double mTax2_refund_rate;
	
	private String mTax_3_name;
	private boolean mIs_tax_3_applicable;
	private double mTax_3_percentage;
	private double mTax3_refund_rate;
	
	private String mService_Tax_name;
	private boolean mIs_service_tax_applicable;
	private double mService_tax_percentage;	
	private double mService_tax_refund_rate;
	
	private String  mTax_gst_name;
	private boolean mHas_gst;
	private double  mGst_percentage;
	private double mGst_refund_rate;
	
	private boolean mInclude_tax1_in_gst;
	private boolean mInclude_tax2_in_gst;
	private boolean mInclude_tax3_in_gst;
	private boolean mInclude_service_tax_in_gst;
	
	private boolean mCalculate_tax_b4_discount;
	
//	private TaxCalculationMethod mTaxCalculationMethod;
	
//	private boolean mAppyToBill;					
//	private boolean mAppyToItem;

	protected PosTaxAmountObject mTaxAmount;
	
	public BeanTax() {
		mCalculate_tax_b4_discount=PosEnvSettings.getInstance().getTaxParam().isCalculateTaxBeforeDiscount();
	}

	/**
	 * @return the mId
	 */
	public int getId() {
		return mId;
	}

	/**
	 * @param mId the mId to set
	 */
	public void setId(int id) {
		this.mId = id;
	}

	/**
	 * @return the mCode
	 */
	public String getCode() {
		return mCode;
	}

	/**
	 * @param code the mCode to set
	 */
	public void setCode(String code) {
		this.mCode = code;
	}

	/**
	 * @return the mName
	 */
	public String getName() {
		return mName;
	}

	/**
	 * @param name the mName to set
	 */
	public void setName(String name) {
		this.mName = name;
	}

//	/**
//	 * @return the mDescription
//	 */
//	public String getDescription() {
//		return mDescription;
//	}
//
//	/**
//	 * @param description the mDescription to set
//	 */
//	public void setDescription(String description) {
//		this.mDescription = description;
//	}
	
	/**
	 * @return the mIs_tax_1_applicable
	 */
	public boolean isTaxOneApplicable() {
		return mIs_tax_1_applicable;
	}

	/**
	 * @param isapplicable the mIs_tax_1_applicable to set
	 */
	public void setTaxOneApplicable(boolean isapplicable) {
		this.mIs_tax_1_applicable = isapplicable;
	}

	/**
	 * @return the mTax_1_Percentage
	 */
	public double getTaxOnePercentage() {
		return mTax_1_percentage;
	}
	

	/**
	 * @param tax_1_percentage the mTax_1_Percentage to set
	 */
	public void setTaxOnePercentage(double percentage) {
		this.mTax_1_percentage = percentage;
	}
	
	/**
	 * @return the mIs_tax_2_applicable
	 */
	public boolean isTaxTwoApplicable() {
		return mIs_tax_2_applicable;
	}

	/**
	 * @param isapplicable the mIs_tax_2_applicable to set
	 */
	public void setTaxTwoApplicable(boolean isapplicable) {
		this.mIs_tax_2_applicable = isapplicable;
	}

	/**
	 * @return the mTax_2_Percentage
	 */
	public double getTaxTwoPercentage() {
		return mTax_2_percentage;
	}

	/**
	 * @param tax_2_percentage the mTax_2_Percentage to set
	 */
	public void setTaxTwoPercentage(double percentage) {
		this.mTax_2_percentage = percentage;
	}
	
	/**
	 * @return the mIs_tax_3_applicable
	 */
	public boolean isTaxThreeApplicable() {
		return mIs_tax_3_applicable;
	}

	/**
	 * @param isapplicable the mIs_tax_3_applicable to set
	 */
	public void setTaxThreeApplicable(boolean isapplicable) {
		this.mIs_tax_3_applicable = isapplicable;
	}

	/**
	 * @return the mTax_3_Percentage
	 */
	public double getTaxThreePercentage() {
		return mTax_3_percentage;
	}

	/**
	 * @param tax_3_percentage the mTax_3_Percentage to set
	 */
	public void setTaxThreePercentage(double percentage) {
		this.mTax_3_percentage = percentage;
	}


	/**
	 * @return the mIs_service_tax_applicable
	 */
	public boolean isServiceTaxApplicable() {
		return mIs_service_tax_applicable;
	}

	/**
	 * @param mIs_service_tax_applicable the mIs_service_tax_applicable to set
	 */
	public void setServiceTaxApplicable(boolean isapplicable) {
		this.mIs_service_tax_applicable = isapplicable;
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

//	/**
//	 * @return the mAppyToBill
//	 */
//	public boolean isAppyToBill() {
//		return mAppyToBill;
//	}
//
//	/**
//	 * @param appyToBill the mAppyToBill to set
//	 */
//	public void setAppyToBill(boolean appyToBill) {
//		this.mAppyToBill = appyToBill;
//	}
//
//	/**
//	 * @return the mAppyToItem
//	 */
//	public boolean isAppyToItem() {
//		return mAppyToItem;
//	}
//
//	/**
//	 * @param appyToItem the mAppyToItem to set
//	 */
//	public void setAppyToItem(boolean appyToItem) {
//		this.mAppyToItem = appyToItem;
//	}

	/**
	 * @return the mHas_gst
	 */
	public boolean isGSTDefined() {
		return mHas_gst;
	}

	/**
	 * @param gstdefined the mHas_gst to set
	 */
	public void setGSTDefined(boolean gstdefined) {
		this.mHas_gst = gstdefined;
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
	 * @return the mInclude_tax1_in_gst
	 */
	public boolean isTaxOneIncludeInGST() {
		return mInclude_tax1_in_gst;
	}

	/**
	 * @param included the mInclude_tax1_in_gst to set
	 */
	public void setTaxOneIncludeInGST(boolean included) {
		this.mInclude_tax1_in_gst = included;
	}

	/**
	 * @return the mInclude_tax2_in_gst
	 */
	public boolean isTaxTwoIncludeInGST() {
		return mInclude_tax2_in_gst;
	}

	/**
	 * @param included the mInclude_tax2_in_gst to set
	 */
	public void setTaxTwoIncludeInGST(boolean included) {
		this.mInclude_tax2_in_gst = included;
	}

	/**
	 * @return the mInclude_tax3_in_gst
	 */
	public boolean isTaxThreeIncludeInGST() {
		return mInclude_tax3_in_gst;
	}

	/**
	 * @param included the mInclude_tax3_in_gst to set
	 */
	public void setTaxThreeIncludeInGST(boolean included) {
		this.mInclude_tax3_in_gst = included;
	}

	/**
	 * @return the mInclude_service_tax_in_gst
	 */
	public boolean isServiceTaxIncludeInGST() {
		return mInclude_service_tax_in_gst;
	}

	/**
	 * @param included the mInclude_service_tax_in_gst to set
	 */
	public void setServiceTaxIncludeInGST(boolean included) {
		this.mInclude_service_tax_in_gst = included;
	}
	
	public PosTaxAmountObject getTaxAmount(){
		return mTaxAmount;
	}
	
	public void setTaxAmount(PosTaxAmountObject taxamount){
		mTaxAmount=taxamount;
	}

	/**
	 * @return the mTax_1_name
	 */
	public String getTaxOneName() {
		return mTax_1_name;
	}

	/**
	 * @param mTax_1_name the mTax_1_name to set
	 */
	public void setTaxOneName(String tax_1_name) {
		this.mTax_1_name = tax_1_name;
	}

	/**
	 * @return the mTax_2_name
	 */
	public String getTaxTwoName() {
		return mTax_2_name;
	}

	/**
	 * @param mTax_2_name the mTax_2_name to set
	 */
	public void setTaxTwoName(String tax_2_name) {
		this.mTax_2_name = tax_2_name;
	}

	/**
	 * @return the mTax_3_name
	 */
	public String getTaxThreeName() {
		return mTax_3_name;
	}

	/**
	 * @param mTax_3_name the mTax_3_name to set
	 */
	public void setTaxThreeName(String tax_3_name) {
		this.mTax_3_name = tax_3_name;
	}

	/**
	 * @return the mTax_gst_name
	 */
	public String getGSTName() {
		return mTax_gst_name;
	}

	/**
	 * @param mTax_gst_name the mTax_gst_name to set
	 */
	public void setGSTName(String tax_gst_name) {
		this.mTax_gst_name = tax_gst_name;
	}

	/**
	 * @return the mClaculate_tax_b4_discount
	 */
	public boolean isClaculateTaxBeforDiscount() {
		return mCalculate_tax_b4_discount;
	}

	/**
	 * @param calculate the  to set
	 */
	public void setCalculateTaxBeforDiscount(boolean calculate) {
		this.mCalculate_tax_b4_discount = calculate;
	}

	/**
	 * @return the mTax_service_name
	 */
	public String getServiceTaxName() {
		return mService_Tax_name;
	}

	/**
	 * @param name the mTax_service_name to set
	 */
	public void setServiceTaxName(String name) {
		this.mService_Tax_name = name;
	}
	
	@Override
	public BeanTax clone() throws CloneNotSupportedException {
		
		return (BeanTax) super.clone();
	}

	/**
	 * @return the mTax1_refund_rate
	 */
	public double getTax1_refund_rate() {
		return mTax1_refund_rate;
	}

	/**
	 * @param mTax1_refund_rate the mTax1_refund_rate to set
	 */
	public void setTax1_refund_rate(double mTax1_refund_rate) {
		this.mTax1_refund_rate = mTax1_refund_rate;
	}

	/**
	 * @return the mTax2_refund_rate
	 */
	public double getTax2_refund_rate() {
		return mTax2_refund_rate;
	}

	/**
	 * @param mTax2_refund_rate the mTax2_refund_rate to set
	 */
	public void setTax2_refund_rate(double mTax2_refund_rate) {
		this.mTax2_refund_rate = mTax2_refund_rate;
	}

	/**
	 * @return the mTax3_refund_rate
	 */
	public double getTax3_refund_rate() {
		return mTax3_refund_rate;
	}

	/**
	 * @param mTax3_refund_rate the mTax3_refund_rate to set
	 */
	public void setTax3_refund_rate(double mTax3_refund_rate) {
		this.mTax3_refund_rate = mTax3_refund_rate;
	}

	/**
	 * @return the mService_tax_refund_rate
	 */
	public double getService_tax_refund_rate() {
		return mService_tax_refund_rate;
	}

	/**
	 * @param mService_tax_refund_rate the mService_tax_refund_rate to set
	 */
	public void setService_tax_refund_rate(double mService_tax_refund_rate) {
		this.mService_tax_refund_rate = mService_tax_refund_rate;
	}

	/**
	 * @return the mGst_refund_rate
	 */
	public double getGst_refund_rate() {
		return mGst_refund_rate;
	}

	/**
	 * @param mGst_refund_rate the mGst_refund_rate to set
	 */
	public void setGst_refund_rate(double mGst_refund_rate) {
		this.mGst_refund_rate = mGst_refund_rate;
	}
	
//	/**
//	 * @return
//	 */
//	public TaxCalculationMethod getTaxCalculationMethod() {
//		return mTaxCalculationMethod;
//	}
//
//	/**
//	 * @param itemTaxCalculationMethod
//	 */
//	public void setTaxCalculationMethod(TaxCalculationMethod itemTaxCalculationMethod) {
//		this.mTaxCalculationMethod = itemTaxCalculationMethod;
//	}

}
