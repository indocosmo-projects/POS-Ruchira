/**
 * 
 */
package com.indocosmo.pos.terminal.devices.eftpos.messageobjects;

import com.indocosmo.pos.terminal.devices.eftpos.enums.EFTLogonStatus;

/**
 * @author jojesh
 *
 */
public class EFTResponseMessageLOG extends EFTMessageBase {

	private EFTLogonStatus mStatus;
	private String mReceiptDataPart1;
	private String mDisplayMessage;
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
	 * @return the status
	 */
	public EFTLogonStatus getStatus() {
		return mStatus;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(EFTLogonStatus status) {
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
}
