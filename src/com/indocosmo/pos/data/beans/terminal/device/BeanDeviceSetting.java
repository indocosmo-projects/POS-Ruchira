package com.indocosmo.pos.data.beans.terminal.device;



public final class BeanDeviceSetting {	
	
	private boolean mAttachedReceiptPrinter;	
	private boolean mAttachedKitchenPrinter;	
	private boolean mAttachedCashDrawer;		
	private boolean mAttachedPoleDisplay;
	private boolean mAttachedPosDevice;
	private boolean mAttachedWeighing;

	/**
	 * @return the mAttachedReceiptPrinter
	 */
	public final boolean isAttachedReceiptPrinter() {
		return mAttachedReceiptPrinter;
	}

	/**
	 * @param mAttachedReceiptPrinter the mAttachedReceiptPrinter to set
	 */
	public final void setAttachedReceiptPrinter(boolean mAttachedReceiptPrinter) {
		this.mAttachedReceiptPrinter = mAttachedReceiptPrinter;
	}

	/**
	 * @return the mAttachedKitchenPrinter
	 */
	public final boolean isAttachedKitchenPrinter() {
		return mAttachedKitchenPrinter;
	}

	/**
	 * @param mAttachedKitchenPrinter the mAttachedKitchenPrinter to set
	 */
	public final void setAttachedKitchenPrinter(boolean mAttachedKitchenPrinter) {
		this.mAttachedKitchenPrinter = mAttachedKitchenPrinter;
	}

	/**
	 * @return the mAttachedCashDrawer
	 */
	public final boolean isAttachedCashDrawer() {
		return mAttachedCashDrawer;
	}

	/**
	 * @param mAttachedCashDrawer the mAttachedCashDrawer to set
	 */
	public final void setAttachedCashDrawer(boolean mAttachedCashDrawer) {
		this.mAttachedCashDrawer = mAttachedCashDrawer;
	}

	/**
	 * @return the mAttachedPoleDisplay
	 */
	public final boolean isAttachedPoleDisplay() {
		return mAttachedPoleDisplay;
	}

	/**
	 * @param mAttachedPoleDisplay the mAttachedPoleDisplay to set
	 */
	public final void setAttachedPoleDisplay(boolean mAttachedPoleDisplay) {
		this.mAttachedPoleDisplay = mAttachedPoleDisplay;
	}

	/**
	 * @return the mAttachedPosDevice
	 */
	public boolean isAttachedPosDevice() {
		return mAttachedPosDevice;
	}

	/**
	 * @param mAttachedPosDevice the mAttachedPosDevice to set
	 */
	public void setAttachedPosDevice(boolean mAttachedPosDevice) {
		this.mAttachedPosDevice = mAttachedPosDevice;
	}
	
	/**
	 * @return the mAttachedCashDrawer
	 */
	public final boolean isAttachedWeighing() {
		return mAttachedWeighing;
	}

	/**
	 * @param mAttachedCashDrawer the mAttachedCashDrawer to set
	 */
	public final void setAttacheddWeighing(boolean mAttached) {
		this.mAttachedWeighing = mAttached;
	}

}
