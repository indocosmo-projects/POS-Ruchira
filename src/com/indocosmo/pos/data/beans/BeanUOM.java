package com.indocosmo.pos.data.beans;

public final class BeanUOM {

	private int mID;
	private String mCode;
	private String mName;
	private String mSymbol;
	private int decimalPlaces;
	
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
	 * @return the mID
	 */
	public int getID() {
		return mID;
	}
	/**
	 * @param mID the mID to set
	 */
	public void setID(int ID) {
		this.mID = ID;
	}
	/**
	 * @return the mCode
	 */
	public String getCode() {
		return mCode;
	}
	/**
	 * @param mCode the mCode to set
	 */
	public void setCode(String Code) {
		this.mCode = Code;
	}
	/**
	 * @return the mName
	 */
	public String getName() {
		return mName;
	}
	/**
	 * @param mName the mName to set
	 */
	public void setName(String Name) {
		this.mName = Name;
	}
	
	/**
	 * @return the Symbol
	 */
	public String getSymbol() {
		return mSymbol;
	}
	/**
	 * @param Symbol to set
	 */
	public void setSymbol(String Symbol) {
		this.mSymbol = Symbol;
	}
	
	
}
