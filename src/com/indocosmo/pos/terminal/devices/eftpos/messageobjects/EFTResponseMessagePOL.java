/**
 * 
 */
package com.indocosmo.pos.terminal.devices.eftpos.messageobjects;

import com.indocosmo.pos.terminal.devices.eftpos.enums.EFTPolStatus;

/**
 * @author jojesh
 *
 */
public class EFTResponseMessagePOL extends EFTMessageBase {

	private EFTPolStatus mStatus;
//	private String mDisplayMessage;
//	private String mSerialNumber;
	/**
	 * @return the status
	 */
	public EFTPolStatus getStatus() {
		return mStatus;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(EFTPolStatus status) {
		mStatus = status;
	}
//	/**
//	 * @return the displayMessage
//	 */
//	public String getDisplayMessage() {
//		return mDisplayMessage;
//	}
//	/**
//	 * @param displayMessage the displayMessage to set
//	 */
//	public void setDisplayMessage(String displayMessage) {
//		mDisplayMessage = displayMessage;
//	}
//	/**
//	 * @return the serialNumber
//	 */
//	public String getSerialNumber() {
//		return mSerialNumber;
//	}
//	/**
//	 * @param serialNumber the serialNumber to set
//	 */
//	public void setSerialNumber(String serialNumber) {
//		mSerialNumber = serialNumber;
//	}
}
