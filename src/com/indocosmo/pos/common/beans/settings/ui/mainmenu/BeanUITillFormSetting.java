/**
 * 
 */
package com.indocosmo.pos.common.beans.settings.ui.mainmenu;

/**
 * @author sandhya
 *
 */
public class BeanUITillFormSetting {
	
	public static final String SHOW_SUMMARY_ONLY="main_menu.close_till.show_summary_only";
	public static final String CONFIRM_BANK_DETAILS="main_menu.close_till.confirm_bank_deposit_details";
	public static final String SET_OPENINGFLOAT="main_menu.open_till.set_opening_float";
	public static final String OPEN_CASH_BOX="main_menu.close_till.open_cash_box";
	
	private boolean showSummaryOnly;
	private boolean confirmBankDetails;
	private boolean setOpeningFloat;
	private boolean openCashBox;

	/**
	 * @return the openCashBox
	 */
	public boolean openCashBox() {
		return openCashBox;
	}

	/**
	 * @param openCashBox the openCashBox to set
	 */
	public void setOpenCashBox(boolean openCashBox) {
		this.openCashBox = openCashBox;
	}

	/**
	 * @return the showSummaryOnly
	 */
	public boolean isShowSummaryOnly() {
		return showSummaryOnly;
	}

	/**
	 * @param showSummaryOnly the showSummaryOnly to set
	 */
	public void setShowSummaryOnly(boolean showSummaryOnly) {
		this.showSummaryOnly = showSummaryOnly;
	}

	/**
	 * @return the confirmBankDetails
	 */
	public boolean isConfirmBankDetails() {
		return confirmBankDetails;
	}

	/**
	 * @param confirmBankDetails the confirmBankDetails to set
	 */
	public void setConfirmBankDetails(boolean confirmBankDetails) {
		this.confirmBankDetails = confirmBankDetails;
	}

	/**
	 * @return the setOpeningFloat
	 */
	public boolean isSetOpeningFloat() {
		return setOpeningFloat;
	}

	/**
	 * @param setOpeningFloat the setOpeningFloat to set
	 */
	public void setOpeningFloat(boolean setOpeningFloat) {
		this.setOpeningFloat = setOpeningFloat;
	}

	 
	
	
}
