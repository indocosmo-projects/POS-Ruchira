/**
 * 
 */
package com.indocosmo.pos.data.beans;

import javax.swing.KeyStroke;

/**
 * @author jojesh
 *
 */
public class BeanCurrency extends BeanMasterBase{

	private String currencySymbol;
	private String fraction;
	private String fractionSymbol;
	private int decimalPlaces;
	private float exchangeRate;
	private boolean isBaseCurrency=false;
	private BeanRounding rounding;
	
	/**
	 * @return the rounding
	 */
	public BeanRounding getRounding() {
		return rounding;
	}
	/**
	 * @param rounding the rounding to set
	 */
	public void setRounding(BeanRounding rounding) {
		this.rounding = rounding;
	}
	/**
	 * @return the currencySymbol
	 */
	public String getCurrencySymbol() {
		return currencySymbol;
	}
	/**
	 * @param currencySymbol the currencySymbol to set
	 */
	public void setCurrencySymbol(String currencySymbol) {
		this.currencySymbol = currencySymbol;
	}
	/**
	 * @return the fraction
	 */
	public String getFractionName() {
		return fraction;
	}
	/**
	 * @param fraction the fraction to set
	 */
	public void setFractionName(String fraction) {
		this.fraction = fraction;
	}
	/**
	 * @return the fractionSymbol
	 */
	public String getFractionSymbol() {
		return fractionSymbol;
	}
	/**
	 * @param fractionSymbol the fractionSymbol to set
	 */
	public void setFractionSymbol(String fractionSymbol) {
		this.fractionSymbol = fractionSymbol;
	}
	/**
	 * @return the decimalPlaces
	 */
	public int getDecimalPlaces() {
		return decimalPlaces;
	}
	/**
	 * @param decimalPlaces the decimalPlaces to set
	 */
	public void setDecimalPlaces(int decimalPlaces) {
		this.decimalPlaces = decimalPlaces;
	}
	/**
	 * @return the exchangeRate
	 */
	public float getExchangeRate() {
		return exchangeRate;
	}
	/**
	 * @param exchangeRate the exchangeRate to set
	 */
	public void setExchangeRate(float exchangeRate) {
		this.exchangeRate = exchangeRate;
	}
	/**
	 * @return the isBaseCurrency
	 */
	public boolean isBaseCurrency() {
		return isBaseCurrency;
	}
	/**
	 * @param isBaseCurrency the isBaseCurrency to set
	 */
	public void setBaseCurrency(boolean isBaseCurrency) {
		this.isBaseCurrency = isBaseCurrency;
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
