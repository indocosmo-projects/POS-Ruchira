/**
 * 
 */
package com.indocosmo.pos.data.beans;

/**
 * @author jojesh
 *
 */
public final class BeanCustomerType {
	
	public BeanCustomerType(int id,String code,String name){
		mID=id;
		mCode=code;
		mName=name;
	}
	
	public BeanCustomerType(){
		
	}
	
	private int mID;
	private String mCode;
	private String mName;
	private double mDefPriceVariance;
	
	public int getId() {
		return mID;
	}
	public void setId(int Id) {
		this.mID = Id;
	}
	public String getCode() {
		return mCode;
	}
	public void setCode(String code) {
		this.mCode = code;
	}
	public String getName() {
		return mName;
	}
	public void setName(String name) {
		this.mName = name;
	}

	/**
	 * @return the mDefPriceVariance
	 */
	public double getDefPriceVariance() {
		return mDefPriceVariance;
	}

	/**
	 * @param Def Price Variance the Def Price Variance to set
	 */
	public void setDefPriceVariance(double defPriceVariance) {
		this.mDefPriceVariance = defPriceVariance;
	}
	

}
