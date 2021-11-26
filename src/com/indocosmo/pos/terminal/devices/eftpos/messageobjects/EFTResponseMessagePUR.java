/**
 * 
 */
package com.indocosmo.pos.terminal.devices.eftpos.messageobjects;

import com.indocosmo.pos.terminal.devices.eftpos.enums.EFTPurchaseStatus;

/**
 * @author jojesh
 *
 */
public final class EFTResponseMessagePUR extends EFTMessageBase {
	
	private String mCardAmount;
	private String mCashAmount;
	private EFTPurchaseStatus mStatus;
	private String mDisplayMessage;
	private String mAuthCode;
	private String mTxnTraceNumber;
	private String mCardNumber;
	private String mAccountType;
	private String mReceiptDataPart1;
	private String mReceiptDataPart2;
	/**
	 * @return the cardAmount
	 */
	public String getCardAmount() {
		return mCardAmount;
	}
	/**
	 * @param cardAmount the cardAmount to set
	 */
	public void setCardAmount(String cardAmount) {
		mCardAmount = cardAmount;
	}
	/**
	 * @return the cashAmount
	 */
	public String getCashAmount() {
		return mCashAmount;
	}
	/**
	 * @param cashAmount the cashAmount to set
	 */
	public void setCashAmount(String cashAmount) {
		mCashAmount = cashAmount;
	}
	/**
	 * @return the status
	 */
	public EFTPurchaseStatus getStatus() {
		return mStatus;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(EFTPurchaseStatus status) {
		mStatus = status;
	}
	/**
	 * @return the displayMessage
	 */
	public String getDisplayMessage() {
		return mDisplayMessage;
	}
	/**
	 * @param displayMessage the displayMessage to set
	 */
	public void setDisplayMessage(String displayMessage) {
		mDisplayMessage = displayMessage;
	}
	/**
	 * @return the bankReferance
	 */
	public String getAuthCode() {
		return mAuthCode;
	}
	/**
	 * @param bankReferance the bankReferance to set
	 */
	public void setAuthCode(String authCode) {
		mAuthCode = authCode;
	}
	/**
	 * @return the txnTraceNumber
	 */
	public String getTxnTraceNumber() {
		return mTxnTraceNumber;
	}
	/**
	 * @param txnTraceNumber the txnTraceNumber to set
	 */
	public void setTxnTraceNumber(String txnTraceNumber) {
		mTxnTraceNumber = txnTraceNumber;
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
	 * @return the accountType
	 */
	public String getAccountType() {
		return mAccountType;
	}
	/**
	 * @param accountType the accountType to set
	 */
	public void setAccountType(String accountType) {
		mAccountType = accountType;
	}
	/**
	 * @return the receiptDataPart1
	 */
	public String getReceiptDataPart1() {
		return mReceiptDataPart1;
	}
	/**
	 * @param receiptDataPart1 the receiptDataPart1 to set
	 */
	public void setReceiptDataPart1(String receiptDataPart1) {
		mReceiptDataPart1 = receiptDataPart1;
	}
	/**
	 * @return the receiptDataPart2
	 */
	public String getReceiptDataPart2() {
		return mReceiptDataPart2;
	}
	/**
	 * @param receiptDataPart2 the receiptDataPart2 to set
	 */
	public void setReceiptDataPart2(String receiptDataPart2) {
		mReceiptDataPart2 = receiptDataPart2;
	}

}
