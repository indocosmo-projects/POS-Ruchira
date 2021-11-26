/**
 * 
 */
package com.indocosmo.pos.common.beans.settings.ui.orderentry;

/**
 * @author jojesh-13.2
 *
 */
public class BeanUIOrderInfoFormSettings {
	
	public final static String PS_SHOW_PAYMENT_BUTTON="order_entry.order_info_form.show_payment_button";
	
	private boolean paymentButtonVisible;
	
	
	/**
	 * @return the paymentButtonVisible
	 */
	public boolean isPaymentButtonVisible() {
		return paymentButtonVisible;
	}
	/**
	 * @param paymentButtonVisible the paymentButtonVisible to set
	 */
	public void setPaymentButtonVisible(boolean paymentButtonVisible) {
		this.paymentButtonVisible = paymentButtonVisible;
	}
}
