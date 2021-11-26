/**
 * 
 */
package com.indocosmo.pos.terminal.devices.eftpos.messageobjects;


/**
 * @author jojesh
 *
 */
public class EFTResponseMessageCAN extends EFTMessageBase {

	private String mDisplayMessage;
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
