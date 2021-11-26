/**
 * 
 */
package com.indocosmo.pos.data.beans;

/**
 * @author deepak
 *
 */
public class BeanDiscountSummary {
	
	private int mId;
	private String mName;
	private String mCode;
	private String mBasicPrice;
	private double mAmount;
	private double mQuantity;
	/**
	 * @return the mQuantity
	 */
	public double getQuantity() {
		return mQuantity;
	}
	/**
	 * @param mQuantity the mQuantity to set
	 */
	public void setQuantity(double mQuantity) {
		this.mQuantity = mQuantity;
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
	public void setId(int mId) {
		this.mId = mId;
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
	public void setName(String mName) {
		this.mName = mName;
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
	public void setCode(String mCode) {
		this.mCode = mCode;
	}
	/**
	 * @return the mBasicPrice
	 */
	public String getBasicPrice() {
		return mBasicPrice;
	}
	/**
	 * @param mBasicPrice the mBasicPrice to set
	 */
	public void setBasicPrice(String mBasicPrice) {
		this.mBasicPrice = mBasicPrice;
	}
	/**
	 * @return the mAmount
	 */
	public double getAmount() {
		return mAmount;
	}
	/**
	 * @param mAmount the mAmount to set
	 */
	public void setAmount(double mAmount) {
		this.mAmount = mAmount;
	}
	

}
