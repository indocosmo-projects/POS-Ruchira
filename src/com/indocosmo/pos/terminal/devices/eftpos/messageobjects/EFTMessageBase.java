/**
 * 
 */
package com.indocosmo.pos.terminal.devices.eftpos.messageobjects;

import com.indocosmo.pos.terminal.devices.eftpos.enums.EFTMessageType;

/**
 * @author jojesh
 *
 */
public abstract class EFTMessageBase {
	
	private String mMessageId;
	private EFTMessageType mMessageType;
	private String mMerchantID;
	/**
	 * @return the messageId
	 */
	public String getMessageId() {
		return mMessageId;
	}
	/**
	 * @param messageId the messageId to set
	 */
	public void setMessageId(String messageId) {
		mMessageId = messageId;
	}
	/**
	 * @return the messageType
	 */
	public EFTMessageType getMessageType() {
		return mMessageType;
	}
	/**
	 * @param messageType the messageType to set
	 */
	public void setMessageType(EFTMessageType messageType) {
		mMessageType = messageType;
	}
	/**
	 * @return the merchantID
	 */
	public String getMerchantID() {
		return mMerchantID;
	}
	/**
	 * @param merchantID the merchantID to set
	 */
	public void setMerchantID(String merchantID) {
		mMerchantID = merchantID;
	}
	

}
