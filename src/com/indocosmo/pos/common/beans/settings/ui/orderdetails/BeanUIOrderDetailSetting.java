/**
 * 
 */
package com.indocosmo.pos.common.beans.settings.ui.orderdetails;

/**
 * @author anand
 *
 */
public class BeanUIOrderDetailSetting {

	public static final String  SHOW_RESHITO_BUTTON="order_detail_form.show_reshito_button";
	
	private boolean mShowReshitoButton;
	
	/**
	 * @return the mShowPrintReshitoButton
	 */
	public boolean isPrintReshitoButtonVisible() {
		return mShowReshitoButton;
	}


	/**
	 * @param mShowPrintReshitoButton the mShowPrintReshitoButton to set
	 */
	public void setPrintReshitoButtonVisible(boolean mShowPrintReshitoButton) {
		
		this.mShowReshitoButton = mShowPrintReshitoButton;
	}

}
