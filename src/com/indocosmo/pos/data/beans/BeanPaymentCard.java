package com.indocosmo.pos.data.beans;

public final class BeanPaymentCard {

	private String mCardNumber;
	private String mCardName;
	private String mCardType;
	private Integer mCardExpiryMonth;
	private Integer mCardExpirYear;
	private String mCardApprovalCode;
	
//	public PosCardItem(String cardNumber, String cardName) {
//		this.mCardNumber = cardNumber;
//		this.mCardName = cardName;
//	}

	/**
	 * @return the mCardNumber
	 */
	public final String getCardNumber() {
		return mCardNumber;
	}


	/**
	 * @param mCardNumber the mCardNumber to set
	 */
	public final void setCardNumber(String cardNumber) {
		this.mCardNumber = cardNumber;
	}


	/**
	 * @return the mCardName
	 */
	public final String getCardName() {
		return mCardName;
	}


	/**
	 * @param mCardName the mCardName to set
	 */
	public final void setCardName(String cardName) {
		this.mCardName = cardName;
	}


	/**
	 * @return the mCardType
	 */
	public final String getCardType() {
		return mCardType;
	}


	/**
	 * @param mCardType the mCardType to set
	 */
	public final void setCardType(String cardType) {
		this.mCardType = cardType;
	}


	/**
	 * @return the mCardExpiryMonth
	 */
	public final Integer getCardExpiryMonth() {
		return mCardExpiryMonth;
	}


	/**
	 * @param mCardExpiryMonth the mCardExpiryMonth to set
	 */
	public final void setCardExpiryMonth(Integer cardExpiryMonth) {
		this.mCardExpiryMonth = cardExpiryMonth;
	}


	/**
	 * @return the mCardExpirYear
	 */
	public final Integer getCardExpirYear() {
		return mCardExpirYear;
	}


	/**
	 * @param mCardExpirYear the mCardExpirYear to set
	 */
	public final void setCardExpirYear(Integer cardExpirYear) {
		this.mCardExpirYear = cardExpirYear;
	}


	/**
	 * @return the mCardApprovalCode
	 */
	public final String getCardApprovalCode() {
		return mCardApprovalCode;
	}


	/**
	 * @param mCardApprovalCode the mCardApprovalCode to set
	 */
	public final void setCardApprovalCode(String cardApprovalCode) {
		this.mCardApprovalCode = cardApprovalCode;
	}	
	
	
	
}
