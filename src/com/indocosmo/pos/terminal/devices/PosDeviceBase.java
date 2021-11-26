/**
 * 
 */
package com.indocosmo.pos.terminal.devices;

/**
 * @author jojesh
 *
 */
public abstract class PosDeviceBase {
	
	private boolean mDeviceInitialized = false;

	/**
	 * 
	 */
	public PosDeviceBase() {
		
	}
	
	public boolean initialize() throws Exception{
		mDeviceInitialized=true;
		return mDeviceInitialized;
	}
	
	/**
	 * @return the deviceInitialized
	 */
	public boolean isDeviceInitialized() {
		
		return mDeviceInitialized;
	}
	
	public void setDeviceInitialized(boolean b){
		
		mDeviceInitialized=b;
	}
	
	public void shutdown() throws Exception{
		
		setDeviceInitialized(false);
	};

}
