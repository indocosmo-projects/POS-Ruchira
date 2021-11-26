/**
 * 
 */
package com.indocosmo.pos.common.beans.settings.ui.orderentry;

/**
 * @author joe.12.3
 *
 */
public class BeanUIOrderItemListPanelSetting {
	
	
	public final static String PS_USE_ALTERNATIVE_TITLE="order_entry.order_item_list_panel.use_alternative_title";
	public final static String PS_USE_SINGLE_LINE_UI="order_entry.order_item_list_panel.use_single_line_ui";
	public final static String PS_ITEM_NAME_FONT_SIZE="order_entry.order_item_list_panel.order_item_name_font_size";
	public final static String PS_ITEM_DTLS_FONT_SIZE="order_entry.order_item_list_panel.order_item_details_font_size";
	public final static String PS_ITEM_DTLS_SHOW_QTY="order_entry.order_item_list_panel.order_item_details_show_quantity";
	public final static String PS_ITEM_DTLS_SHOW_DISC="order_entry.order_item_list_panel.order_item_details_show_discount";
	public final static String PS_ITEM_DTLS_SHOW_PRICE="order_entry.order_item_list_panel.order_item_details_show_price";
	public final static String PS_ITEM_DTLS_SHOW_TAX="order_entry.order_item_list_panel.order_item_details_show_tax";
	public final static String PS_CONFIRM_ITEM_DELETION="order_entry.order_item_list_panel.confirm_item_deletion";
	public final static String PS_GROUP_SAME_ITEMS="order_entry.order_item_list_panel.group_same_items";
	public final static String PS_CONFIRM_DUPLICATE_ITEMS="order_entry.order_item_list_panel.confirm_duplicate_item";
	
	private boolean useAlternativeTitle;
	private boolean useSingleLineUI;
	private float itemNameFontSize;
	private float itemDetailsFontSize;
	private boolean showQuantity;
	private boolean showPrice;
	private boolean showDiscount;
	private boolean showTax;
	private boolean confirmItemDeletionRequired;
	private boolean groupSameItems;
	private boolean confirmDuplicateItems;
	
	
	/**
	 * @return the confirmItemDeletionRequired
	 */
	public boolean isConfirmItemDeletionRequired() {
		return confirmItemDeletionRequired;
	}
	/**
	 * @param confirmItemDeletionRequired the confirmItemDeletionRequired to set
	 */
	public void setConfirmItemDeletionRequired(boolean confirmItemDeletionRequired) {
		this.confirmItemDeletionRequired = confirmItemDeletionRequired;
	}
	/**
	 * @return the useSingleLineUI
	 */
	public boolean useSingleLineUI() {
		return useSingleLineUI;
	}
	/**
	 * @param useSingleLineUI the useSingleLineUI to set
	 */
	public void setSingleLineUI(boolean useSingleLineUI) {
		this.useSingleLineUI = useSingleLineUI;
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
	
	public float getItemNameFontSize(){
		return itemNameFontSize;
	}
	public void setItemNameFontSize(int size){
		this.itemNameFontSize=size;
	}

	public float getItemDetailsFontSize(){
		return itemDetailsFontSize;
	}
	public void setItemDetailsFontSize(int size){
		this.itemDetailsFontSize=size;
	}
	/**
	 * @return the showQuantity
	 */
	public boolean canShowQuantity() {
		return showQuantity;
	}
	/**
	 * @param showQuantity the showQuantity to set
	 */
	public void setShowQuantity(boolean showQuantity) {
		this.showQuantity = showQuantity;
	}
	/**
	 * @return the showPrice
	 */
	public boolean canShowPrice() {
		return showPrice;
	}
	/**
	 * @param showPrice the showPrice to set
	 */
	public void setShowPrice(boolean showPrice) {
		this.showPrice = showPrice;
	}
	/**
	 * @return the showDiscount
	 */
	public boolean canShowDiscount() {
		return showDiscount;
	}
	/**
	 * @param showDiscount the showDiscount to set
	 */
	public void setShowDiscount(boolean showDiscount) {
		this.showDiscount = showDiscount;
	}
	/**
	 * @return the showTax
	 */
	public boolean canShowTax() {
		return showTax;
	}
	/**
	 * @param showTax the showTax to set
	 */
	public void setShowTax(boolean showTax) {
		this.showTax = showTax;
	}
	/**
	 * @return the groupSameItems
	 */
	public boolean isGroupSameItems() {
		return groupSameItems;
	}
	/**
	 * @param groupSameItems the groupSameItems to set
	 */
	public void setGroupSameItems(boolean groupSameItems) {
		this.groupSameItems = groupSameItems;
	}
	/**
	 * @return the confirmDuplicateItems
	 */
	public boolean isConfirmDuplicateItems() {
		return confirmDuplicateItems;
	}
	/**
	 * @param confirmDuplicateItems the confirmDuplicateItems to set
	 */
	public void setConfirmDuplicateItems(boolean confirmDuplicateItems) {
		this.confirmDuplicateItems = confirmDuplicateItems;
	}
}
