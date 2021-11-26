/**
 * 
 */
package com.indocosmo.pos.common.beans.settings.ui.orderentry;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import com.indocosmo.pos.common.beans.settings.ui.orderentry.neworder.BeanUINewOrderSettings;
import com.indocosmo.pos.common.enums.CardTypes;
import com.indocosmo.pos.common.enums.ItemEditMode;
import com.indocosmo.pos.common.enums.PosOrderServiceTypes;
import com.indocosmo.pos.common.enums.PosPaymentOption;

/**
 * @author joe.12.3
 *
 */
public class BeanUIOrderEntrySetting {
	
	public final static String PS_PAYMENT_METHOD="order_entry.payment_method";
	public final static String PS_DEFAULT_ORDER_SERVICE_TYPE="order_entry.service.default";
	
	public final static String PS_SHOW_EDIT_UI_FOR_OPEN_ITEMS_ON_ADD="order_entry.show_edit_ui_for_open_items_on_add";
	public final static String PS_SHOW_EDIT_UI_FOR_ITEMS_WITH_COMBO_ON_ADD="order_entry.show_edit_ui_for_items_with_combo_on_add";
	public final static String PS_SHOW_EDIT_UI_FOR_ITEMS_WITH_CHOICE_ON_ADD="order_entry.show_edit_ui_for_items_with_choice_on_add";
	public final static String PS_SHOW_EDIT_UI_FOR_ITEMS_WITH_MODIFIERS_ON_ADD="order_entry.show_edit_ui_for_items_with_modifiers_on_add";
	
	public final static String PS_CONFIRM_VOID="order_entry.confirm_void";
	public final static String PS_ENABLE_ITEM_DELETE="order_entry.enable_item_delete";
	public final static String PS_CONFIRM_TRAY_WEIGHT_UPDATE="order_entry.tray_weight_update_confirmation";
	
	public final static String PS_TAKE_AWAY_DEFAULT_CUSTOMER="order_entry.order_service.take_away.default_customer";
	public final static String PS_HOME_DELIVERY_DEFAULT_CUSTOMER="order_entry.order_service.home_delivery.default_customer";
	public final static String PS_TABLE_DEFAULT_CUSTOMER="order_entry.order_service.table.default_customer";
	public final static String PS_WHOLESALE_DEFAULT_CUSTOMER="order_entry.order_service.wholesale.default_customer";
	public final static String PS_SO_DEFAULT_CUSTOMER="order_entry.order_service.so.default_customer";
	
	public final static String PS_QUICK_EDIT="order_entry.show_quick_edit";
	public final static String PS_QUICK_EDIT_MODE="order_entry.quick_edit_mode";
	public final static String PS_ALLOW_CUSTOMER_EDIT="order_entry.allow_customer_edit";
	
	public final static String PS_SHOW_SO_DETAILS="order_entry.show_sales_order_details";
	public final static String PS_SHOW_GST_DET_IN_CUSTOMER_INFO="order_entry.customer_ui.show_gst_details";
	
	private BeanUIPaymentPanelSetting paymentPanelSetting;
	private BeanUIMiscOpionsPanelSetting miscOpionsPanelSetting;
	private BeanUIPrintPanelSetting printePanelSettings;
	private BeanUIItemListPanelSetting itemListPanelSetting;
	private BeanUIClassItemListPanelSetting classItemListPanelSetting;
	private BeanUIOrderItemListPanelSetting orderItemListPanelSetting;
	private BeanUINewOrderSettings newOrderUISetting;
	
	private BeanUISplitSettings splitUISettings;
	private BeanUIOrderRetrieveFormSetting orderRetrieveFormSettings;
	private BeanUIOrderInfoFormSettings orderInfoFormSettings;
	private PosPaymentOption paymentOption;
	private PosOrderServiceTypes defaultServiceType;
	private boolean showEditWindowForOpenItemsOnAdd=true;
	private boolean showEditWindowForItemsWithComboOnAdd=true;
	private boolean showEditWindowForItemsWithChoiceOnAdd=true;
	private boolean showEditWindowForItemsWithModifiersOnAdd=true;
	
	private boolean confirmVoid;
	private boolean enableItemDelete;
	private boolean confirmTrayWeightUpdate;
	
	private String defaultCustomerTakeAway;
	private String defaultCustomerHomeDelivery;
	private String defaultCustomerTableService;
	private String defaultCustomerWholesale;
	private String defaultCustomerSO;
	
	private boolean showQuickEdit;
	private boolean allowCustomerEdit;
	private ItemEditMode quickEditMode;
	
	private showSOInfo showSalesOrderDetail;
	private boolean showCustomerGSTDetails;
	
	/**
	 * @return the defaultCustomerSO
	 */
	public String getDefaultCustomerSO() {
		return defaultCustomerSO;
	}
	/**
	 * @param defaultCustomerSO the defaultCustomerSO to set
	 */
	public void setDefaultCustomerSO(String defaultCustomerSO) {
		this.defaultCustomerSO = defaultCustomerSO;
	}
		
	/**
	 * @return the orderItemListPanelSetting
	 */
	public BeanUIOrderItemListPanelSetting getOrderItemListPanelSetting() {

		return orderItemListPanelSetting;
	}
	/**
	 * @param orderItemListPanelSetting the orderItemListPanelSetting to set
	 */
	public void setOrderItemListPanelSetting(
			BeanUIOrderItemListPanelSetting orderItemListPanelSetting) {

		this.orderItemListPanelSetting = orderItemListPanelSetting;
	}
	/**
	 * @return the classItemListPanelSetting
	 */
	public BeanUIClassItemListPanelSetting getClassItemListPanelSetting() {

		return classItemListPanelSetting;
	}
	/**
	 * @param classItemListPanelSetting the classItemListPanelSetting to set
	 */
	public void setClassItemListPanelSetting(
			BeanUIClassItemListPanelSetting classItemListPanelSetting) {

		this.classItemListPanelSetting = classItemListPanelSetting;
	}
	/**
	 * @return the itemListPanelSetting
	 */
	public BeanUIItemListPanelSetting getItemListPanelSetting() {

		return itemListPanelSetting;
	}
	/**
	 * @param itemListPanelSetting the itemListPanelSetting to set
	 */
	public void setItemListPanelSetting(
			BeanUIItemListPanelSetting itemListPanelSetting) {

		this.itemListPanelSetting = itemListPanelSetting;
	}
	/**
	 * @return the paymentPanelSetting
	 */
	public BeanUIPaymentPanelSetting getPaymentPanelSetting() {

		return paymentPanelSetting;
	}
	/**
	 * @param paymentPanelSetting the paymentPanelSetting to set
	 */
	public void setPaymentPanelSetting(BeanUIPaymentPanelSetting paymentPanelSetting) {

		this.paymentPanelSetting = paymentPanelSetting;
	}
	/**
	 * @return the printePanelSettings
	 */
	public BeanUIPrintPanelSetting getPrintePanelSettings() {


		return printePanelSettings;
	}
	/**
	 * @param printePanelSettings the printePanelSettings to set
	 */
	public void setPrintePanelSettings(BeanUIPrintPanelSetting printePanelSettings) {

		this.printePanelSettings = printePanelSettings;
	}
	/**
	 * @return the newOrderUISetting
	 */
	public BeanUINewOrderSettings getNewOrderUISetting() {

		return newOrderUISetting;
	}
	/**
	 * @param newOrderUISetting the newOrderUISetting to set
	 */
	public void setNewOrderUISetting(BeanUINewOrderSettings newOrderUISetting) {

		this.newOrderUISetting = newOrderUISetting;
	}
	/**
	 * @return the splitUISettings
	 */
	public BeanUISplitSettings getSplitUISettings() {
		return splitUISettings;
	}
	/**
	 * @param splitUISettings the splitUISettings to set
	 */
	public void setSplitUISettings(BeanUISplitSettings splitUISettings) {
		this.splitUISettings = splitUISettings;
	}
	/**
	 * @param orderRetrieveFormSettings
	 */
	public void setOrderRetrieveFormSetting(
			BeanUIOrderRetrieveFormSetting orderRetrieveFormSettings) {
		
		this.orderRetrieveFormSettings=orderRetrieveFormSettings;
		
	}
	/**
	 * @return the orderRetrieveFormSettings
	 */
	public BeanUIOrderRetrieveFormSetting getOrderRetrieveFormSetting() {
		return orderRetrieveFormSettings;
	}
	
	/**
	 * @return the defaultServiceType
	 */
	public PosOrderServiceTypes getDefaultServiceType() {
		return defaultServiceType;
	}
	/**
	 * @param defaultServiceType the defaultServiceType to set
	 */
	public void setDefaultServiceType(PosOrderServiceTypes defaultServiceType) {
		this.defaultServiceType = defaultServiceType;
	}

/**
	 * @return the paymentMethod
	 */
	public PosPaymentOption getPaymentOption() {
		return paymentOption;
	}
	/**
	 * @param paymentMethod the paymentMethod to set
	 */
	public void setPaymentOption(PosPaymentOption payOption) {
		this.paymentOption = payOption;
	}
	/**
	 * @return the orderInfoFormSettings
	 */
	public BeanUIOrderInfoFormSettings getOrderInfoFormSetting() {
		return orderInfoFormSettings;
	}
	/**
	 * @param orderInfoFormSettings the orderInfoFormSettings to set
	 */
	public void setOrderInfoFormSettings(
			BeanUIOrderInfoFormSettings orderInfoFormSettings) {
		this.orderInfoFormSettings = orderInfoFormSettings;
	}
	/**
	 * @return the showEditWindowForOppenItemsOnAdd
	 */
	public boolean canShowEditWindowForOpenItemsOnAdd() {
		return showEditWindowForOpenItemsOnAdd;
	}
	/**
	 * @param showEditWindowForOpenItemsOnAdd the showEditWindowForOppenItemsOnAdd to set
	 */
	public void setShowEditWindowForOpenItemsOnAdd(
			boolean showEditWindowForOpenItemsOnAdd) {
		this.showEditWindowForOpenItemsOnAdd = showEditWindowForOpenItemsOnAdd;
	}
	/**
	 * @return the beanUIMiscOpionsPanelSetting
	 */
	public BeanUIMiscOpionsPanelSetting getMiscOptionsPanelSetting() {
		return miscOpionsPanelSetting;
	}
	/**
	 * @param miscOpionsPanelSetting the beanUIMiscOpionsPanelSetting to set
	 */
	public void setMiscOptionsPanelSetting(
			BeanUIMiscOpionsPanelSetting miscOpionsPanelSetting) {
		this.miscOpionsPanelSetting = miscOpionsPanelSetting;
	}
	
	 
	/**
	 * @return the confirmDelete
	 */
	public boolean isConfirmVoid() {
		return confirmVoid;
	}
	/**
	 * @param confirmDelete the confirmDelete to set
	 */
	public void setConfirmVoid(boolean confirmVoid) {
		this.confirmVoid = confirmVoid;
	}
	/**
	 * @return the enableItemDeleteButton
	 */
	public boolean isEnableItemDelete() {
		return enableItemDelete;
	}
	/**
	 * @param enableItemDeleteButton the enableItemDeleteButton to set
	 */
	public void setEnableItemDelete(boolean enableItemDeleteButton) {
		this.enableItemDelete = enableItemDeleteButton;
	}
	/**
	 * @return the confirmTrayCodeScanning
	 */
	public boolean isConfirmTrayWeightUpdate() {
		return confirmTrayWeightUpdate;
	}
	/**
	 * @param confirmTrayCodeScanning the confirmTrayCodeScanning to set
	 */
	public void setConfirmTrayWeightUpdate(boolean confirmTrayWeightUpdate) {
		this.confirmTrayWeightUpdate = confirmTrayWeightUpdate;
	}
	/**
	 * @return the defaultCustomerTakeAway
	 */
	public String getDefaultCustomerTakeAway() {
		return defaultCustomerTakeAway;
	}
	/**
	 * @param defaultCustomerTakeAway the defaultCustomerTakeAway to set
	 */
	public void setDefaultCustomerTakeAway(String defaultCustomerTakeAway) {
		this.defaultCustomerTakeAway = defaultCustomerTakeAway;
	}
	/**
	 * @return the defaultCustomerHomeDelivery
	 */
	public String getDefaultCustomerHomeDelivery() {
		return defaultCustomerHomeDelivery;
	}
	/**
	 * @param defaultCustomerHomeDelivery the defaultCustomerHomeDelivery to set
	 */
	public void setDefaultCustomerHomeDelivery(String defaultCustomerHomeDelivery) {
		this.defaultCustomerHomeDelivery = defaultCustomerHomeDelivery;
	}
	/**
	 * @return the defaultCustomerTableService
	 */
	public String getDefaultCustomerTableService() {
		return defaultCustomerTableService;
	}
	/**
	 * @param defaultCustomerTableService the defaultCustomerTableService to set
	 */
	public void setDefaultCustomerTableService(String defaultCustomerTableService) {
		this.defaultCustomerTableService = defaultCustomerTableService;
	}
	/**
	 * @return the defaultCustomerWholesale
	 */
	public String getDefaultCustomerWholesale() {
		return defaultCustomerWholesale;
	}
	/**
	 * @param defaultCustomerWholesale the defaultCustomerWholesale to set
	 */
	public void setDefaultCustomerWholesale(String defaultCustomerWholesale) {
		this.defaultCustomerWholesale = defaultCustomerWholesale;
	}

	/**
	 * @return the quickEdit
	 */
	public boolean canShowQuickEdit() {
		return showQuickEdit;
	}
	/**
	 * @param quickEdit the quickEdit to set
	 */
	public void setShowQuickEdit(boolean quickEdit) {
		this.showQuickEdit = quickEdit;
	}
	/**
	 * @return the quickEditMode
	 */
	public ItemEditMode getQuickEditMode() {
		return quickEditMode;
	}
	/**
	 * @param quickEditMode the quickEditMode to set
	 */
	public void setQuickEditMode(ItemEditMode quickEditMode) {
		this.quickEditMode = quickEditMode;
	}
	/**
	 * @return the allowCustomerEdit
	 */
	public boolean isAllowCustomerEdit() {
		return allowCustomerEdit;
	}
	/**
	 * @param allowCustomerEdit the allowCustomerEdit to set
	 */
	public void setAllowCustomerEdit(boolean allowCustomerEdit) {
		this.allowCustomerEdit = allowCustomerEdit;
	}
	/**
	 * @return the showEditWindowForItemsWithComboOnAdd
	 */
	public boolean canShowEditWindowForItemsWithComboOnAdd() {
		return showEditWindowForItemsWithComboOnAdd;
	}
	/**
	 * @param showEditWindowForItemsWithComboOnAdd the showEditWindowForItemsWithComboOnAdd to set
	 */
	public void setShowEditWindowForItemsWithComboOnAdd(boolean showEditWindowForItemsWithComboOnAdd) {
		this.showEditWindowForItemsWithComboOnAdd = showEditWindowForItemsWithComboOnAdd;
	}
	/**
	 * @return the showEditWindowForItemsWithChoiceOnAdd
	 */
	public boolean canShowEditWindowForItemsWithChoiceOnAdd() {
		return showEditWindowForItemsWithChoiceOnAdd;
	}
	/**
	 * @param showEditWindowForItemsWithChoiceOnAdd the showEditWindowForItemsWithChoiceOnAdd to set
	 */
	public void setShowEditWindowForItemsWithChoiceOnAdd(boolean showEditWindowForItemsWithChoiceOnAdd) {
		this.showEditWindowForItemsWithChoiceOnAdd = showEditWindowForItemsWithChoiceOnAdd;
	}
	/**
	 * @return the showEditWindowForItemsWithModifiersOnAdd
	 */
	public boolean canShowEditWindowForItemsWithModifiersOnAdd() {
		return showEditWindowForItemsWithModifiersOnAdd;
	}
	/**
	 * @param showEditWindowForItemsWithModifiersOnAdd the showEditWindowForItemsWithModifiersOnAdd to set
	 */
	public void setShowEditWindowForItemsWithModifiersOnAdd(boolean showEditWindowForItemsWithModifiersOnAdd) {
		this.showEditWindowForItemsWithModifiersOnAdd = showEditWindowForItemsWithModifiersOnAdd;
	}
	
	/**
	 * @return the showSalesOrderDetail
	 */
	public showSOInfo getShowSalesOrderDetail() {
		return showSalesOrderDetail;
	}
	/**
	 * @param showSalesOrderDetail the showSalesOrderDetail to set
	 */
	public void setShowSalesOrderDetail(showSOInfo showSalesOrderDetail) {
		this.showSalesOrderDetail = showSalesOrderDetail;
	}
	/**
	 * @return the showCustomerGSTDetails
	 */
	public boolean isShowCustomerGSTDetails() {
		return showCustomerGSTDetails;
	}
	/**
	 * @param showCustomerGSTDetails the showCustomerGSTDetails to set
	 */
	public void setShowCustomerGSTDetails(boolean showCustomerGSTDetails) {
		this.showCustomerGSTDetails = showCustomerGSTDetails;
	}



	/**
	 * 
	 * @author sandhya
	 * 
	 * enumerator to show the window of customer info in order entry screen
	 * user can configure when a new order create or parking  or  can show at both time   
	 *
	 */
	public enum showSOInfo {
		AtNewOrder(1),
		AtParking(2),
		Both(3);
		
		private static final Map<Integer,showSOInfo> mLookup 
		= new HashMap<Integer,showSOInfo>();

		static {
			for(showSOInfo rc : EnumSet.allOf(showSOInfo.class))
				mLookup.put(rc.getValue(), rc);
		}

		private int mValue;
		
		private showSOInfo(int value) {
			this.mValue = value;
		}

		public int getValue() { return mValue; }
		
		public static showSOInfo get(int value) { 
			return mLookup.get(value); 
		}

	}

	
}
