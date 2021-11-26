/**
 * 
 */
package com.indocosmo.pos.data.beans.terminal.device;

import com.indocosmo.pos.common.enums.device.PrinterType;

/**
 * @author anand
 *
 */
public class BeanDevReceiptPrinterConfig extends BeanDevPrinterConfig {
	
	private PrinterType printerType; 

	private String mCashBoxOpenCode;
	private boolean mIsCashBoxAttached;
	private boolean mAllowManualCashBoxOpen;
	
	/**
	 * @return Has Cash Box Attached
	 */
	public boolean isCashBoxAttached() {
		return mIsCashBoxAttached;
	}

	/**
	 * @param Has Cash Box Attached 
	 */
	public void setCashBoxAttached(boolean hasCashBoxAttached) {
		this.mIsCashBoxAttached = hasCashBoxAttached;
	}

	/**
	 * @return the Allow Manual Cash Box Open
	 */
	public boolean isAllowManualCashBoxOpen() {
		return mAllowManualCashBoxOpen;
	}

	/**
	 * @param Allow Manual CashBox Open 
	 */
	public void setAllowManualCashBoxOpen(boolean allowManualCashBoxOpen) {
		this.mAllowManualCashBoxOpen = allowManualCashBoxOpen;
	}

	/**
	 * @return the Cash Box Open Code
	 */
	public String getCashBoxOpenCode() {
		return mCashBoxOpenCode;
	}

	/**
	 * @param Cash Box Open Code 
	 */
	public void setCashBoxOpenCode(String cashBoxOpenCode) {
		this.mCashBoxOpenCode = cashBoxOpenCode;
	}

	/**
	 * @return the printerType
	 */
	public PrinterType getPrinterType() {
		return printerType;
	}

	/**
	 * @param printerType the printerType to set
	 */
	public void setPrinterType(PrinterType printerType) {
		this.printerType = printerType;
	}
	
	

}
