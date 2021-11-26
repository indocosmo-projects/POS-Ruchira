/**
 * 
 */
package com.indocosmo.pos.terminal.devices.eftpos.adapters;

import com.indocosmo.pos.terminal.devices.eftpos.enums.EFTPurchaseStatus;

/**
 * @author jojesh
 *
 */
public abstract class PosDeviceEFTAdapter implements IPosDeviceEFTListner{

	/* (non-Javadoc)
	 * @see com.indocosmo.pos.terminal.devices.eftpos.adapters.IPosDeviceEFTListner#onSignatureVerificationRequest(java.lang.String)
	 */
	@Override
	public boolean onSignatureVerificationRequest(String message) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.pos.terminal.devices.eftpos.adapters.IPosDeviceEFTListner#onDisplayMessageReceived(java.lang.String)
	 */
	@Override
	public void onDisplayMessageReceived(String message) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.pos.terminal.devices.eftpos.adapters.IPosDeviceEFTListner#onPurchaseRequestReceived()
	 */
	@Override
	public void onPurchaseRequestReceived() {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.pos.terminal.devices.eftpos.adapters.IPosDeviceEFTListner#onPurchaseCompleted(com.indocosmo.pos.terminal.devices.eftpos.enums.EFTPurchaseStatus, java.lang.String)
	 */
	@Override
	public void onPurchaseCompleted(EFTPurchaseStatus status, String message) {
		// TODO Auto-generated method stub
		
	}

}
