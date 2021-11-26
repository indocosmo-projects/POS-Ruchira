/**
 * 
 */
package com.indocosmo.pos.common.beans.settings.ui.orderentry;

/**
 * @author joe.12.3
 *
 */
public class BeanUIPaymentPanelSetting {
	
	public final static String PS_SHOW_SPLIT_PAY_BUTTON="order_entry.payment_panel.show_split_pay_button";
	public final static String PS_SHOW_CASH_PAY_BUTTON="order_entry.payment_panel.show_cash_pay_button";
	public final static String PS_SHOW_CARD_PAY_BUTTON="order_entry.payment_panel.show_card_pay_button";
	public final static String PS_SHOW_COMPANY_PAY_BUTTON="order_entry.payment_panel.show_company_pay_button";
	public final static String PS_SHOW_VOUCHER_PAY_BUTTON="order_entry.payment_panel.show_voucher_pay_button";
	public final static String PS_SHOW_PARTIAL_PAY_BUTTON="order_entry.payment_panel.show_partial_pay_button";
	public final static String PS_SHOW_PRE_BILL_DISCOUNT_BUTTON="order_entry.payment_panel.show_pre_bill_discount_button";
	public final static String PS_QUICK_PAY_OPTIONS="payment.quick_pay_options";
	public final static String PS_SHOW_ONLINE_PAY_BUTTON="order_entry.payment_panel.show_online_pay_button";
	public final static String PS_CREDIT_CARD_VALIDATION="payment.require_credit_card_validation";
	
	private boolean cashPayButtonVisible;
	private boolean cardPayButtonVisible;
	private boolean voucherPayButtonVisible;
	private boolean companyPayButtonVisible;
	private boolean partialPayButtonVisible;
	private boolean preBillDiscountButtonVisible;
	private boolean onlinePayButtonVisible;
	private boolean creditCardValidationRequired;
	
	private String[] quickPaymentOptions;

	/**
	 * @return the quickPaymentOptions
	 */
	public String[] getQuickPaymentOptions() {
		return quickPaymentOptions;
	}
	/**
	 * @param quickPaymentOptions the quickPaymentOptions to set
	 */
	public void setQuickPaymentOptions(String[] quickPaymentOptions) {
		this.quickPaymentOptions = quickPaymentOptions;
	}
	/**
	 * @return the showPartialPayButton
	 */
	public boolean isPartialPayButtonVisibile() {
		return partialPayButtonVisible;
	}
	/**
	 * @param showPartialPayButton the showPartialPayButton to set
	 */
	public void setPartialPayButtonVisibile(boolean showPartialPayButton) {
		this.partialPayButtonVisible = showPartialPayButton;
	}
	/**
	 * @return the showCashPayButton
	 */
	public boolean isCashPayButtonVisibile() {
		return cashPayButtonVisible;
	}
	/**
	 * @param showCashPayButton the showCashPayButton to set
	 */
	public void setCashPayButtonVisibile(boolean showCashPayButton) {
		this.cashPayButtonVisible = showCashPayButton;
	}
	/**
	 * @return the showCardPayButton
	 */
	public boolean isCardPayButtonVisibile() {
		return cardPayButtonVisible;
	}
	/**
	 * @param showCardPayButton the showCardPayButton to set
	 */
	public void setCardPayButtonVisibile(boolean showCardPayButton) {
		this.cardPayButtonVisible = showCardPayButton;
	}
	/**
	 * @return the showVoucherPayButton
	 */
	public boolean isVoucherPayButtonVisibile() {
		return voucherPayButtonVisible;
	}
	/**
	 * @param showVoucherPayButton the showVoucherPayButton to set
	 */
	public void setVoucherPayButtonVisibile(boolean showVoucherPayButton) {
		this.voucherPayButtonVisible = showVoucherPayButton;
	}
	/**
	 * @return the showCompanyPayButton
	 */
	public boolean isCompanyPayButtonVisibile() {
		return companyPayButtonVisible;
	}
	/**
	 * @param showCompanyPayButton the showCompanyPayButton to set
	 */
	public void setCompanyPayButtonVisibile(boolean showCompanyPayButton) {
		this.companyPayButtonVisible = showCompanyPayButton;
	}
	
	private boolean showSplitPayButton;
	/**
	 * @return the showSplitPayButton
	 */
	public boolean isSplitPayButtonVisibile() {
		return showSplitPayButton;
	}
	/**
	 * @param showSplitPayButton the showSplitPayButton to set
	 */
	public void setSplitPayButtonVisibile(boolean showSplitPayButton) {
		this.showSplitPayButton = showSplitPayButton;
	}
	/**
	 * @return the preBillDiscountButtonVisible
	 */
	public boolean isPreBillDiscountButtonVisible() {
		return preBillDiscountButtonVisible;
	}
	/**
	 * @param preBillDiscountButtonVisible the preBillDiscountButtonVisible to set
	 */
	public void setPreBillDiscountButtonVisible(boolean preBillDiscountButtonVisible) {
		this.preBillDiscountButtonVisible = preBillDiscountButtonVisible;
	}
	/**
	 * @return the onlinePayButtonVisible
	 */
	public boolean isOnlinePayButtonVisible() {
		return onlinePayButtonVisible;
	}
	/**
	 * @param onlinePayButtonVisible the onlinePayButtonVisible to set
	 */
	public void setOnlinePayButtonVisible(boolean onlinePayButtonVisible) {
		this.onlinePayButtonVisible = onlinePayButtonVisible;
	}
	/**
	 * @return the creditCardValidationRequired
	 */
	public boolean isCreditCardValidationRequired() {
		return creditCardValidationRequired;
	}
	/**
	 * @param creditCardValidationRequired the creditCardValidationRequired to set
	 */
	public void setCreditCardValidationRequired(boolean creditCardValidationRequired) {
		this.creditCardValidationRequired = creditCardValidationRequired;
	}
 
	
	
	
}
