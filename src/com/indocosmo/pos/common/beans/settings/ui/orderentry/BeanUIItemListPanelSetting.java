/**
 * 
 */
package com.indocosmo.pos.common.beans.settings.ui.orderentry;

import com.indocosmo.pos.common.enums.PosItemDisplayStyle;

/**
 * @author joe.12.3
 *
 */
public class BeanUIItemListPanelSetting {

	public final static String PS_USE_COLORED_ITEM_BUTTON="order_entry.item_list_panel.use_colored_item_button";
	public final static String PS_USE_ALTERNATIVE_TITLE="order_entry.item_list_panel.use_alternative_title";
	public final static String PS_SHOW_ITEM_CODE="order_entry.item_list_panel.show_item_code";
	public final static String PS_SHOW_ITEM_PRICE="order_entry.item_list_panel.show_item_price";
	public static final String PS_SALE_ITEM_BUTTON_STYLE="order_entry.item_list_panel.item_button_style";
	public static final String PS_SALE_ITEM_BUTTON_WIDTH="order_entry.item_list_panel.item_button_width";
	public static final String PS_SALE_ITEM_BUTTON_HEIGHT="order_entry.item_list_panel.item_button_height";
	public static final String PS_SALE_ITEM_DETAIL_FONT_SIZE="order_entry.item_list_panel.item_detail_font_size";
	public static final String PS_SALE_ITEM_NAME_FONT_SIZE="order_entry.item_list_panel.item_name_font_size";

	private boolean useColoredButton;
	private boolean useAlternativeTitle;
	private boolean showItemCode;
	private boolean showItemPrice;
	private PosItemDisplayStyle itmDisplayStyle;
	private int itemControlWidth;
	private int itemControlHeight;
	private int itemDetailFontSize;
	private int itemNameFontSize;
	/**
	 * @return the itemNameFontSize
	 */
	public int getItemNameFontSize() {
		return itemNameFontSize;
	}

	/**
	 * @param itemNameFontSize the itemNameFontSize to set
	 */
	public void setItemNameFontSize(int itemNameFontSize) {
		this.itemNameFontSize = itemNameFontSize;
	}

	/**
	 * @return the itemDetailFontSize
	 */
	public int getItemDetailFontSize() {
		return itemDetailFontSize;
	}

	/**
	 * @param itemDetailFontSize the itemDetailFontSize to set
	 */
	public void setItemDetailFontSize(int itemDetailFontSize) {
		this.itemDetailFontSize = itemDetailFontSize;
	}

	
	/**
	 * @return the itemCntrolHeight
	 */
	public int getItemControlHeight() {
		return itemControlHeight;
	}

	/**
	 * @param itemCntrolHeight the itemCntrolHeight to set
	 */
	public void setItemControlHeight(int itemCntrolHeight) {
		this.itemControlHeight = itemCntrolHeight;
	}

	/**
	 * @return the itemCntrolWidth
	 */
	public int getItemControlWidth() {
		return itemControlWidth;
	}

	/**
	 * @param itemCntrolWidth the itemCntrolWidth to set
	 */
	public void setItemControlWidth(int itemCntrolWidth) {
		this.itemControlWidth = itemCntrolWidth;
	}

	
	/**
	 * 
	 */
	public BeanUIItemListPanelSetting() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the useColoredButton
	 */
	public boolean useColoredButton() {
		return useColoredButton;
	}

	/**
	 * @param useColoredButton the useColoredButton to set
	 */
	public void setColoredButton(boolean useColoredButton) {
		this.useColoredButton = useColoredButton;
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

	public void setButtonType(String type){
		this.itmDisplayStyle=PosItemDisplayStyle.get(type);
	}

	public PosItemDisplayStyle getButtonType(){
		return this.itmDisplayStyle;
	}

	/**
	 * @return the showItemCode
	 */
	public boolean showItemCode() {
		return showItemCode;
	}

	/**
	 * @param showItemCode the showItemCode to set
	 */
	public void setShowItemCode(boolean showItemCode) {
		this.showItemCode = showItemCode;
	}

	/**
	 * @return the showItemPrice
	 */
	public boolean showItemPrice() {
		return showItemPrice;
	}

	/**
	 * @param showItemPrice the showItemPrice to set
	 */
	public void setShowItemPrice(boolean showItemPrice) {
		this.showItemPrice = showItemPrice;
	}
}
