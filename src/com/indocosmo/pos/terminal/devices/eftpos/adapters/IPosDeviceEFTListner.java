/**
 * 
 */
package com.indocosmo.pos.terminal.devices.eftpos.adapters;

import javax.swing.JDialog;

import com.indocosmo.pos.terminal.devices.eftpos.enums.EFTPurchaseStatus;

/**
 * @author jojesh
 *
 */
public interface IPosDeviceEFTListner {
	public void setParent(JDialog parent);
	public boolean onSignatureVerificationRequest(String message);
	public void onDisplayMessageReceived(String message);
	public void onPurchaseRequestReceived();
	public void onPurchaseCompleted(EFTPurchaseStatus status, String message);
	public void onError(String errorMsg);
}
