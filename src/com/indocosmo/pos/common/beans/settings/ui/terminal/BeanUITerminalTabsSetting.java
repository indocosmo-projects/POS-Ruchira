/**
 * 
 */
package com.indocosmo.pos.common.beans.settings.ui.terminal;

/**
 * @author Deepak E
 *
 */
public class BeanUITerminalTabsSetting {
	
	public final static String TS_SHOW_CASHBOX_SETTINGS_TAB="terminal_settings.tabs.show_cashbox_settings_tab";
	public final static String TS_SHOW_EFT_SETTINGS_TAB="terminal_settings.tabs.show_eft_settings_tab";
	public final static String TS_SHOW_POLE_SETTINGS_TAB="terminal_settings.tabs.show_pole_settings_tab";
	public final static String TS_SHOW_WEIGHING_SETTINGS_TAB="terminal_settings.tabs.show_weighing_settings_tab";
	
	private boolean showCashboxSettingsTab;
	private boolean showPoleSettingsTab;
	private boolean showEFTSettingsTab;
	private boolean showWeighingSettingsTab;
	

	/**
	 * @return showCashboxsettingsTab
	 */
	public boolean isShowCashboxSettingsTab() {
		return showCashboxSettingsTab;
	}

	/**
	 * @param showCashboxsettingsTab
	 */
	public void setShowCashboxSettingsTab(boolean showCashboxsettingsTab) {
		this.showCashboxSettingsTab = showCashboxsettingsTab;
	}

	/**
	 * @return the showPoleSettingsTab
	 */
	public boolean isShowPoleSettingsTab() {
		return showPoleSettingsTab;
	}

	/**
	 * @param showPoleSettingsTab the showPoleSettingsTab to set
	 */
	public void setShowPoleSettingsTab(boolean showPoleSettingsTab) {
		this.showPoleSettingsTab = showPoleSettingsTab;
	}

	/**
	 * @return the showEFTSettingsTab
	 */
	public boolean isShowEFTSettingsTab() {
		return showEFTSettingsTab;
	}

	/**
	 * @param showEFTSettingsTab the showEFTSettingsTab to set
	 */
	public void setShowEFTSettingsTab(boolean showEFTSettingsTab) {
		this.showEFTSettingsTab = showEFTSettingsTab;
	}

	/**
	 * @return the showWeighingSettingsTab
	 */
	public boolean isShowWeighingSettingsTab() {
		return showWeighingSettingsTab;
	}

	/**
	 * @param showWeighingSettingsTab the showWeighingSettingsTab to set
	 */
	public void setShowWeighingSettingsTab(boolean showWeighingSettingsTab) {
		this.showWeighingSettingsTab = showWeighingSettingsTab;
	}

}
