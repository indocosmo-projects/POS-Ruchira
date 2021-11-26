/**
 * 
 */
package com.indocosmo.pos.terminal.devices.eftpos.messageobjects;

/**
 * @author jojesh
 *
 */
public final class EFTMessageMAN extends EFTMessageBase {

	private String mPurchaseAmount;
	private String mCashOutAmount;
	private String mCardExpDate;
	private String mCardNumber;
	private String mID;
	private String mTxnOptions;
	/**
	 * @return the amount
	 */
	public String getPurchaseAmount() {
		return mPurchaseAmount;
	}
	/**
	 * @param amount the amount to set
	 */
	public void setPurchaseAmount(String amount) {
		mPurchaseAmount = amount;
	}
	
	public String getCashOutAmount() {
		return mCashOutAmount;
	}
	/**
	 * @param amount the amount to set
	 */
	public void setCashOutAmount(String amount) {
		mCashOutAmount = amount;
	}
	/**
	 * @return the cardExpDate
	 */
	public String getCardExpDate() {
		return mCardExpDate;
	}
	/**
	 * @param cardExpDate the cardExpDate to set
	 */
	public void setCardExpDate(String cardExpDate) {
		mCardExpDate = cardExpDate;
	}
	/**
	 * @return the cardNumber
	 */
	public String getCardNumber() {
		return mCardNumber;
	}
	/**
	 * @param cardNumber the cardNumber to set
	 */
	public void setCardNumber(String cardNumber) {
		mCardNumber = cardNumber;
	}
	/**
	 * @return the iD
	 */
	public String getID() {
		return mID;
	}
	/**
	 * @param iD the iD to set
	 */
	public void setID(String iD) {
		mID = iD;
	}
	/**
	 * @return the txnOptions
	 */
	public String getTxnOptions() {
		return mTxnOptions;
	}
	/**
	 * @param txnOptions the txnOptions to set
	 */
	public void setTxnOptions(String txnOptions) {
		mTxnOptions = txnOptions;
	}
	
}