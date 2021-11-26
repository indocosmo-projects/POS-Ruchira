/**
 * 
 */
package com.indocosmo.pos.common.beans.settings.ui.orderentry;

/**
 * @author jojesh-13.2
 *
 */
public class BeanUISplitSettings {
	
	public final static String PS_SHOW_ITEM_MODIFIERS="order_entry.split_form.item_list.show_item_modifiers";
	public final static String PS_SHOW_ITEM_CHOICES="order_entry.split_form.item_list.show_item_choices";
	
	private boolean itemMoidifiersVisible=false;
	private boolean itemChoicesVisible=false;
	
	/**
	 * @return the itemMoidifiersVisible
	 */
	public boolean isItemMoidifiersVisible() {
		return itemMoidifiersVisible;
	}
	/**
	 * @param itemMoidifiersVisible the itemMoidifiersVisible to set
	 */
	public void setItemMoidifiersVisible(boolean itemMoidifiersVisible) {
		this.itemMoidifiersVisible = itemMoidifiersVisible;
	}
	/**
	 * @return the itemChoicesVisible
	 */
	public boolean isItemChoicesVisible() {
		return itemChoicesVisible;
	}
	/**
	 * @param itemChoicesVisible the itemChoicesVisible to set
	 */
	public void setItemChoicesVisible(boolean itemChoicesVisible) {
		this.itemChoicesVisible = itemChoicesVisible;
	}


}
