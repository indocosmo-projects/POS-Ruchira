package com.indocosmo.pos.data.beans;

import com.indocosmo.pos.common.enums.CardTypes;

public final class BeanCardType{

//	private String Name;
//	private String Description;
	private CardTypes mCardType;
	private String mCharPrefix;
	private String mCharSuffix;
//	/**
//	 * @return the name
//	 */
//	public String getName() {
//		return Name;
//	}
//	/**
//	 * @param name the name to set
//	 */
//	public void setName(String name) {
//		Name = name;
//	}
//	/**
//	 * @return the description
//	 */
//	public String getDescription() {
//		return Description;
//	}
//	/**
//	 * @param description the description to set
//	 */
//	public void setDescription(String description) {
//		Description = description;
//	}
	/**
	 * @return the cardType
	 */
	public CardTypes getCardType() {
		return mCardType;
	}
	/**
	 * @param cardType the cardType to set
	 */
	public void setCardType(CardTypes cardType) {
		mCardType = cardType;
	}
	/**
	 * @return the charPrefix
	 */
	public String getCharPrefix() {
		return mCharPrefix;
	}
	/**
	 * @param charPrefix the charPrefix to set
	 */
	public void setCharPrefix(String charPrefix) {
		mCharPrefix = charPrefix;
	}
	/**
	 * @return the charSuffix
	 */
	public String getCharSuffix() {
		return mCharSuffix;
	}
	/**
	 * @param charSuffix the charSuffix to set
	 */
	public void setCharSuffix(String charSuffix) {
		mCharSuffix = charSuffix;
	}
	
}
