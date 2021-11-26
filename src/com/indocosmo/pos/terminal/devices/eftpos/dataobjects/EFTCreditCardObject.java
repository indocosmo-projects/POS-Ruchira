/**
 * 
 */
package com.indocosmo.pos.terminal.devices.eftpos.dataobjects;

/**
 * @author jojesh
 *
 */
public final class EFTCreditCardObject {
	
	private String mCreditCardNumber;
	private String mExpYY;
	private String mExpMM;
	private String mCVV;
	private String mHolderName;
	/**
	 * @return the creditCardNumber
	 */
	public String getCreditCardNumber() {
		return mCreditCardNumber;
	}
	/**
	 * @param creditCardNumber the creditCardNumber to set
	 */
	public void setCreditCardNumber(String creditCardNumber) {
		mCreditCardNumber = creditCardNumber;
	}
	
	/**
	 * @return the expDate
	 */
	public String getExpMM() {
		return mExpMM;
	}
	/**
	 * @param expDate the expDate to set
	 */
	public void setExpMM(String expMM) {
		mExpMM = expMM;
	}
	/**
	 * @return the expDate
	 */
	public String getExpYY() {
		return mExpYY;
	}
	/**
	 * @param expDate the expDate to set
	 */
	public void setExpYY(String expYY) {
		mExpYY = expYY;
	}
	/**
	 * @return the cVV
	 */
	public String getCVV() {
		return mCVV;
	}
	/**
	 * @param cVV the cVV to set
	 */
	public void setCVV(String cVV) {
		mCVV = cVV;
	}
	/**
	 * @return the holderName
	 */
	public String getHolderName() {
		return mHolderName;
	}
	/**
	 * @param holderName the holderName to set
	 */
	public void setHolderName(String holderName) {
		mHolderName = holderName;
	}
	

}
