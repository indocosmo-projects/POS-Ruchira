/**
 * 
 */
package com.indocosmo.pos.common.beans.settings.ui.orderentry;

/**
 * @author joe.12.3
 *
 */
public class BeanUIPrintPanelSetting {
	
	public final static String PS_SHOW_RECEIPT_PRINT_BUTTON="order_entry.print_panel.show_receipt_print_button";
	public final static String PS_SHOW_KITCHEN_PRINT_BUTTON="order_entry.print_panel.show_kitchen_print_button";
	public final static String PS_SHOW_CASH_BOX_BUTTON="order_entry.print_panel.show_cash_box_button";

	private boolean showReceiptPrintButton;
	private boolean showKitchenPrintButton;
	private boolean showCashBoxButton;
	/**
	 * @return the showReceiptPrintButton
	 */
	public boolean isShowReceiptPrintButton() {
		return showReceiptPrintButton;
	}
	/**
	 * @param showReceiptPrintButton the showReceiptPrintButton to set
	 */
	public void setShowReceiptPrintButton(boolean showReceiptPrintButton) {
		this.showReceiptPrintButton = showReceiptPrintButton;
	}
	/**
	 * @return the showKitchenPrintButton
	 */
	public boolean isShowKitchenPrintButton() {
		return showKitchenPrintButton;
	}
	/**
	 * @param showKitchenPrintButton the showKitchenPrintButton to set
	 */
	public void setShowKitchenPrintButton(boolean showKitchenPrintButton) {
		this.showKitchenPrintButton = showKitchenPrintButton;
	}
	/**
	 * @return the showCashBoxButton
	 */
	public boolean isShowCashBoxButton() {
		return showCashBoxButton;
	}
	/**
	 * @param showCashBoxButton the showCashBoxButton to set
	 */
	public void setShowCashBoxButton(boolean showCashBoxButton) {
		this.showCashBoxButton = showCashBoxButton;
	}
}
