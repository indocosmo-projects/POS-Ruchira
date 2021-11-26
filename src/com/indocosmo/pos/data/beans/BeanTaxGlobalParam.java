/**
 * This class will store the tax param settings
 */
package com.indocosmo.pos.data.beans;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import com.indocosmo.pos.data.providers.shopdb.PosTaxItemProvider.TaxCalculationMethod;
 
/**
 * @author jojesh
 *
 */
public final class BeanTaxGlobalParam {
	
	private String mTax1Name;
	private String mTax2Name;
	private String mTax3Name;
	private String mServiceTaxName;
	private String mGSTName;
	private TaxCalculationMethod mTaxCalcMethod;
	private boolean mCalculateTaxBeforeDiscount;
	

	public enum POSTaxationBasedOn {

		SaleItem(0),
		ServiceType(1);


		private static final Map<Integer,POSTaxationBasedOn> mLookup 
		= new HashMap<Integer,POSTaxationBasedOn>();

		static {
			for(POSTaxationBasedOn rc : EnumSet.allOf(POSTaxationBasedOn.class))
				mLookup.put(rc.getValue(), rc);
		}

		private int mValue;
		
		private POSTaxationBasedOn(int value) {
			this.mValue = value;
		}

		public int getValue() { return mValue; }
		
		public static POSTaxationBasedOn get(int value) { 
			return mLookup.get(value); 
		}

		 
	}
	
	/**
	 * 
	 */
	public BeanTaxGlobalParam() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the mTax1Name
	 */
	public String getTax1Name() {
		return mTax1Name;
	}

	/**
	 * @param mTax1Name the mTax1Name to set
	 */
	public void setTax1Name(String name) {
		this.mTax1Name = name;
	}

	/**
	 * @return the mTax2Name
	 */
	public String getTax2Name() {
		return mTax2Name;
	}

	/**
	 * @param mTax2Name the mTax2Name to set
	 */
	public void setTax2Name(String name) {
		this.mTax2Name = name;
	}

	/**
	 * @return the mTax3Name
	 */
	public String getTax3Name() {
		return mTax3Name;
	}

	/**
	 * @param mTax3Name the mTax3Name to set
	 */
	public void setTax3Name(String name) {
		this.mTax3Name = name;
	}

	/**
	 * @return the mServiceTaxName
	 */
	public String getServiceTaxName() {
		return mServiceTaxName;
	}

	/**
	 * @param mServiceTaxName the mServiceTaxName to set
	 */
	public void setServiceTaxName(String name) {
		this.mServiceTaxName = name;
	}

	/**
	 * @return the mSCName
	 */
	public String getGSTName() {
		return mGSTName;
	}

	/**
	 * @param mSCName the mSCName to set
	 */
	public void setGSTName(String name) {
		this.mGSTName = name;
	}

	/**
	 * @return the mTaxCalcMethod
	 */
	public TaxCalculationMethod getTaxCalcMethod() {
		return mTaxCalcMethod;
	}

	/**
	 * @param mTaxCalcMethod the mTaxCalcMethod to set
	 */
	public void setTaxCalcMethod(TaxCalculationMethod taxCalcMethod) {
		this.mTaxCalcMethod = taxCalcMethod;
	}
	
	/**
	 * @param taxCalcMethod the mTaxCalcMethod to set
	 */
	public void setTaxCalcMethod(int taxCalcMethod) {
		this.mTaxCalcMethod = TaxCalculationMethod.get(taxCalcMethod);
	}

	/**
	 * @return the mCalculateTaxBeforeDiscount
	 */
	public boolean isCalculateTaxBeforeDiscount() {
		return mCalculateTaxBeforeDiscount;
	}

	/**
	 * @param mCalculateTaxBeforeDiscount the mCalculateTaxBeforeDiscount to set
	 */
	public void setCalculateTaxBeforeDiscount(boolean calculateTaxBeforeDiscount) {
		this.mCalculateTaxBeforeDiscount = calculateTaxBeforeDiscount;
	}
	
}
