/**
 * 
 */
package com.indocosmo.pos.common.beans.settings.ui.orderentry;

/**
 * @author joe.12.3
 *
 */
public class BeanUIMiscOpionsPanelSetting {
	
	public final static String PS_SHOW_WEIGHING_BUTTON="order_entry.miscellaneous_panel.weighing_button";
	public final static String PS_SHOW_VOID_BUTTON="order_entry.miscellaneous_panel.show_void_button";
	

	private boolean weighingButtonVisible;

	private boolean showVoidButton;
	
	/**
	 * @return the showWeighingButton
	 */
	public boolean isWeighingButtonVisible() {
		return weighingButtonVisible;
	}

	/**
	 * @param showWeighingButton the showWeighingButton to set
	 */
	public void setWeighingButtonVisible(boolean showWeighingButton) {
		this.weighingButtonVisible = showWeighingButton;
	}

	  /**
		 * @return the showVoidButton
		 */
		public boolean isShowVoidButton() {
			return showVoidButton;
		}
		/**
		 * @param showVoidButton the showVoidButton to set
		 */
		public void setShowVoidButton(boolean showVoidButton) {
			this.showVoidButton = showVoidButton;
		} 
}
