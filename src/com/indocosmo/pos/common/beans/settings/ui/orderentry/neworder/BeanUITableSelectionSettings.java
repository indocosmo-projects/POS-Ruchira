/**
 * 
 */
package com.indocosmo.pos.common.beans.settings.ui.orderentry.neworder;

/**
 * @author jojesh-13.2
 *
 */
public class BeanUITableSelectionSettings {
	
	public final static int TABLE_WAITER_SELECTION_MODE_NORMAL=0;
	public final static int TABLE_WAITER_SELECTION_MODE_WAITER_ONLY=1;
	public final static int TABLE_WAITER_SELECTION_MODE_SILENT=2;
	
	public final static String PS_TABLE_WAITER_SELECTION_MODE="order_entry.new_order.table_waiter_selection.mode";
	public final static String PS_ALLOW_MULTIPLE_ORDERS="order_entry.new_order.table_selection.allow_multiple_orders";
	public final static String PS_ENABLE_SINGLE_TOUCH_SELECTION="order_entry.new_order.table_selection.enable_single_touch_selection";
	public final static String PS_SHOW_ORDER_COUNT="order_entry.new_order.table_selection.table_conrol.show_order_count";
	public final static String PS_SHOW_TABLE_CODE="order_entry.new_order.table_selection.table_conrol.show_table_code";
	public final static String PS_SHOW_SEAT_COUNT="order_entry.new_order.table_selection.table_conrol.show_seat_count";
	public final static String PS_INFO_COLOR="order_entry.new_order.table_selection.table_conrol.info_color";
	
	public final static String PS_LAYOUT_WIDTH="order_entry.new_order.table_selection.layout_width";
	public final static String PS_LAYOUT_HEIGHT="order_entry.new_order.table_selection.layout_height";
	
	private boolean allowMultipleOrders;
	private boolean enableSingleTouchSelection;
	private boolean orderCountVisible;
	private boolean tableCodeVisible;
	private boolean seatCountVisible;
	private int layoutWidth;
	private int layoutHeight;
	private String infoFontColor="#FFFFFF";
	private int tableWaiterSelectionMode=0;

	/**
	 * @return the allowMultipleOrders
	 */
	public boolean allowMultipleOrders() {
		
		return allowMultipleOrders;
	}

	/**
	 * @param allowMultipleOrders the allowMultipleOrders to set
	 */
	public void setAllowMultipleOrders(boolean allowMultipleOrders) {
		
		this.allowMultipleOrders = allowMultipleOrders;
	}

	/**
	 * @return the allowWaiterAutoSElection
	 */
	public boolean isSingleTouchSelectionEnabled() {
		return enableSingleTouchSelection;
	}

	/**
	 * @param allowWaiterAutoSelection the allowWaiterAutoSElection to set
	 */
	public void setSingleTouchSelectionEnabled(boolean isEnabled) {
		this.enableSingleTouchSelection = isEnabled;
	}

	/**
	 * @return the orderCountVisible
	 */
	public boolean isOrderCountVisible() {
		return orderCountVisible;
	}

	/**
	 * @param orderCountVisible the orderCountVisible to set
	 */
	public void setOrderCountVisible(boolean orderCountVisible) {
		this.orderCountVisible = orderCountVisible;
	}

	/**
	 * @return the maxWidth
	 */
	public int getLayoutWidth() {
		return layoutWidth;
	}

	/**
	 * @param maxWidth the maxWidth to set
	 */
	public void setLayoutWidth(int maxWidth) {
		this.layoutWidth = maxWidth;
	}

	/**
	 * @return the maxHeight
	 */
	public int getLayoutHeight() {
		return layoutHeight;
	}

	/**
	 * @param maxHeight the maxHeight to set
	 */
	public void setLayoutHeight(int maxHeight) {
		this.layoutHeight = maxHeight;
	}

	/**
	 * @return the tableCodeVisible
	 */
	public boolean isTableCodeVisible() {
		return tableCodeVisible;
	}

	/**
	 * @param tableCodeVisible the tableCodeVisible to set
	 */
	public void setTableCodeVisible(boolean tableCodeVisible) {
		this.tableCodeVisible = tableCodeVisible;
	}

	/**
	 * @return the seaCountVisible
	 */
	public boolean isSeatCountVisible() {
		return seatCountVisible;
	}

	/**
	 * @param seaCountVisible the seaCountVisible to set
	 */
	public void setSeaCountVisible(boolean seaCountVisible) {
		this.seatCountVisible = seaCountVisible;
	}

	/**
	 * @return the infoFontColor
	 */
	public String getInfoFontColor() {
		return infoFontColor;
	}

	/**
	 * @param infoFontColor the infoFontColor to set
	 */
	public void setInfoFontColor(String infoFontColor) {
		this.infoFontColor = infoFontColor;
	}

	/**
	 * @return the tableWaiterSelectionMode
	 */
	public int getTableWaiterSelectionMode() {
		return tableWaiterSelectionMode;
	}

	/**
	 * @param tableWaiterSelectionMode the tableWaiterSelectionMode to set
	 */
	public void setTableWaiterSelectionMode(int tableWaiterSelectionMode) {
		this.tableWaiterSelectionMode = tableWaiterSelectionMode;
	}

}
