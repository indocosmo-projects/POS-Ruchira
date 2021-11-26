/**
 * 
 */
package com.indocosmo.pos.common.beans.settings.ui.mainmenu;

/**
 * @author anand
 *
 */
public class BeanUIMainMenuSetting {
	
	public static final String MENU_BUTTON_COLUMN_HEIGHT="main_menu.menu_button_columns";
	public static final String BOTTOM_PANEL_HEIGHT="main_menu.bottom_panel_height";

	
	private BeanUIMenuListPanelSettings menuListPanelSettings;
	private BeanUITillFormSetting tillFormSettings;
 
	private int mMenuColumnCount;
	private int mBottomPanelHeight;
	
	
	/**
	 * @return the mMenuColumnCount
	 */
	public int getMenuColumnCount() {
		return mMenuColumnCount;
	}

	/**
	 * @param mMenuColumnCount the mMenuColumnCount to set
	 */
	public void setMenuColumnCount(int mMenuColumnCount) {
		this.mMenuColumnCount = mMenuColumnCount;
	}

	/**
	 * @return the mBottomPanelHeight
	 */
	public int getBottomPanelHeight() {
		return mBottomPanelHeight;
	}

	/**
	 * @param mBottomPanelHeight the mBottomPanelHeight to set
	 */
	public void setBottomPanelHeight(int mBottomPanelHeight) {
		this.mBottomPanelHeight = mBottomPanelHeight;
	}

	/**
	 * @return the menuListPanelSettings
	 */
	public BeanUIMenuListPanelSettings getMenuListPanelSettings() {
		return menuListPanelSettings;
	}

	/**
	 * @param menuListPanelSettings the menuListPanelSettings to set
	 */
	public void setMenuListPanelSettings(
			BeanUIMenuListPanelSettings menuListPanelSettings) {
		this.menuListPanelSettings = menuListPanelSettings;
	}
	/**
	 * @return the tillFormSettings
	 */
	public BeanUITillFormSetting getTillFormSettings() {
		return tillFormSettings;
	}

	/**
	 * @param tillFormSettings the tillFormSettings to set
	 */
	public void setTillFormSettings(BeanUITillFormSetting tillFormSettings) {
		this.tillFormSettings = tillFormSettings;
	}
	
	
	

}
