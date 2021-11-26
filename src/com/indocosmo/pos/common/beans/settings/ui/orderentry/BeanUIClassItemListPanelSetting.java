/**
 * 
 */
package com.indocosmo.pos.common.beans.settings.ui.orderentry;

import com.indocosmo.pos.common.enums.ImageButtonTextPosition;
import com.indocosmo.pos.common.enums.PosItemDisplayStyle;




/**
 * @author joe.12.3
 *
 */
public class BeanUIClassItemListPanelSetting {


	public final static String PS_USE_ALTERNATIVE_TITLE="order_entry.class_item_list_panel.use_alternative_title";
	public final static String PS_SHOW_MAIN_CLASS_PANEL="order_entry.class_item_list_panel.show_main_class_panel";
	public static final String PS_CLASS_ITEM_BUTTON_HEIGHT="order_entry.class_item_list_panel.item_button_height";
	public static final String PS_CLASS_ITEM_BUTTON_WIDTH="order_entry.class_item_list_panel.item_button_width";
	public static final String PS_CLASS_ITEM_BUTTON_STYLE="order_entry.class_item_list_panel.item_button_style";
	public static final String PS_CLASS_ITEM_BUTTON_TEXT_POSITION="order_entry.class_item_list_panel.item_button_text_position";
	public static final String PS_CLASS_ITEM_BUTTON_FONT_SIZE="order_entry.class_item_list_panel.item_button_font_size";
	
	private PosItemDisplayStyle classItemDisplayStyle;
	private int buttonHeight;
	private int buttonWidth;	
	private boolean useAlternativeTitle;
	private ImageButtonTextPosition classItemButtonTextPosition;
	private int buttonTextFontSize;


	/**
	 * @return the buttonTextFontSize
	 */
	public int getClassButtonTextFontSize() {
		return buttonTextFontSize;
	}
	/**
	 * @param buttonTextFontSize the buttonTextFontSize to set
	 */
	public void setClassButtonTextFontSize(int buttonTextFontSize) {
		this.buttonTextFontSize = buttonTextFontSize;
	}
	/**
	 * @return the classDetailsAlignment
	 */
	public ImageButtonTextPosition getClassItemButtonTextPosition() {
		return classItemButtonTextPosition;
	}
	/**
	 * @param textPosition the classDetailsAlignment to set
	 */
	public void setClassItemButtonTextPosition(String textPosition) {
		this.classItemButtonTextPosition = ImageButtonTextPosition.get(textPosition);
	}
	/**
	 * @return the useAlternativeTitle
	 */
	public boolean useAlternativeTitle() {
		return useAlternativeTitle;
	}
	/**
	 * @param useAlternativeTitle the useAlternativeTitle to set
	 */
	public void setAlternativeTitle(boolean useAlternativeTitle) {
		this.useAlternativeTitle = useAlternativeTitle;
	}
	private boolean showMainClassPanel;
	/**
	 * @return the showMainClassPanel
	 */
	public boolean showMainClassPanel() {
		return showMainClassPanel;
	}
	/**
	 * @param showMainClassPanel the showMainClassPanel to set
	 */
	public void setMainClassPanel(boolean showMainClassPanel) {
		this.showMainClassPanel = showMainClassPanel;
	}


	public void setButtonHeight(int hght){
		buttonHeight=hght;
	}
	public int getButtonHeight(){
		return this.buttonHeight;
	}

	public void setButtonWidth(int wdth){
		buttonWidth=wdth;
	}
	public int getButtonWidth(){
		return this.buttonWidth;
	}

	public void setButtonType(String type){
		this.classItemDisplayStyle=PosItemDisplayStyle.get(type);
	}
	public PosItemDisplayStyle getButtonType(){
		return this.classItemDisplayStyle;
	}
}
