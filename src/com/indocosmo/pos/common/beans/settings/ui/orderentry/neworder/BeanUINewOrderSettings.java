/**
 * 
 */
package com.indocosmo.pos.common.beans.settings.ui.orderentry.neworder;

/**
 * @author jojesh-13.2
 *
 */
public class BeanUINewOrderSettings {

	public final static String PS_CONFIRM_SERVICE_FOR_NEW_ORDER="order_entry.new_order.confirm_service";
	
	private BeanUITableSelectionSettings tableSelectionUISettings;
	private boolean confirmService=true;

	/**
	 * @return the tableSelectionUISettings
	 */
	public BeanUITableSelectionSettings getTableSelectionUISettings() {
		
		return tableSelectionUISettings;
	}

	/**
	 * @param tableSelectionUISettings the tableSelectionUISettings to set
	 */
	public void setTableSelectionUISettings(
			BeanUITableSelectionSettings tableSelectionUISettings) {
		
		this.tableSelectionUISettings = tableSelectionUISettings;
	}

	/**
	 * @return the confirmService
	 */
	public boolean isConfirmServiceRequired() {
		return confirmService;
	}

	/**
	 * @param confirmService the confirmService to set
	 */
	public void setConfirmServiceRequired(boolean confirmService) {
		this.confirmService = confirmService;
	}
}
