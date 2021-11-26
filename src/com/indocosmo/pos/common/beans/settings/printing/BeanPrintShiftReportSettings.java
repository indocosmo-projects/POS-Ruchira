/**
 * 
 */
package com.indocosmo.pos.common.beans.settings.printing;

/**
 * @author sandhya
 *
 */
public class BeanPrintShiftReportSettings {
	
	public final static String PS_VOID_ITEMS_IN_PAYMENT_SUMMARY="shift_report.payment_summary.print_void_items";
	
	private boolean mPrintVoidItemsInPaymentSummary;

	/**
	 * @return the mPrintVoidItemsInPaymentSummary
	 */
	public boolean isPrintVoidItemsInPaymentSummary() {
		return mPrintVoidItemsInPaymentSummary;
	}

	/**
	 * @param mPrintVoidItemsInPaymentSummary the mPrintVoidItemsInPaymentSummary to set
	 */
	public void setPrintVoidItemsInPaymentSummary(
			boolean mPrintVoidItemsInPaymentSummary) {
		
		this.mPrintVoidItemsInPaymentSummary = mPrintVoidItemsInPaymentSummary;
	}
	
	 
	
	
	
	
	
	
	
	
}
