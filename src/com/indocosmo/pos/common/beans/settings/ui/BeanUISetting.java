/**
 * 
 */
package com.indocosmo.pos.common.beans.settings.ui;

import com.indocosmo.pos.common.beans.settings.ui.mainmenu.BeanUIMainMenuSetting;
import com.indocosmo.pos.common.beans.settings.ui.orderdetails.BeanUIOrderDetailSetting;
import com.indocosmo.pos.common.beans.settings.ui.orderentry.BeanUIOrderEntrySetting;
import com.indocosmo.pos.common.beans.settings.ui.orderlist.BeanUIOrderListSetting;
import com.indocosmo.pos.common.beans.settings.ui.terminal.BeanUITerminalTabsSetting;
import com.indocosmo.pos.common.enums.PosOrderServiceTypes;

/**
 * @author joe.12.3
 *
 */
public class BeanUISetting {
	
	public final static String PS_SHOW_LIGHT_BOX="show_light_box";
	public final static String PS_SHOW_KEY_STROKE_CHAR_ON_BUTTONS="show_key_stroke_char_on_buttons";

	public final static String PS_APPLY_WINDOW_RENDERING_HACK="apply_window_rendering_hack";
	public final static String PS_ORDER_SERVICE_SELECTION_CAPTION_TAKEAWAY="order.service.selection.caption.takeaway";
	public final static String PS_ORDER_SERVICE_SELECTION_CAPTION_WHOLESALE="order.service.selection.caption.wholesale";
	public final static String PS_ORDER_SERVICE_SELECTION_CAPTION_HOMEDELIVERY="order.service.selection.caption.homedelivery";
	public final static String PS_ORDER_SERVICE_SELECTION_CAPTION_TABLE="order.service.selection.caption.table";
	public final static String PS_ORDER_SERVICE_SELECTION_CAPTION_SALESORDER="order.service.selection.caption.salesorder";
	public final static String PS_ORDER_ENABLED_SERVICES="order.enabled_services";

	
	public final static String PS_AUTHENTICATE_BILLED_ORDER_EDIT="authenticate_billed_order_edit";
	public final static String PS_AUTHENTICATE_PARKED_ORDER_EDIT="authenticate_parked_order_edit";
	public final static String PS_USE_QUEUE_NO="use_queue_no";
	public final static String PS_LABEL_FONT="label_font";
	public final static String PS_WINDOW_TITLE_FONT="window_title_font";
	public final static String PS_TEXT_AREA_FONT="text_area_font";
	public final static String PS_QUICK_PAY_MODES="quick_pay_modes";

	public final static String PS_QUEUE_NO_PREFIX="queue_no_prefix";
	public final static String PS_INVOICE_NO_PREFIX="invoice_no_prefix";
	
	private BeanUIOrderEntrySetting orderEntryUISettings;
	private BeanUITerminalTabsSetting terminalUISettings;
	private BeanUIOrderDetailSetting orderDetailSettings;
	private BeanUIOrderListSetting orderListSettings;
	private BeanUIMainMenuSetting mainMenuUISettings;
	
	private boolean billedOrderEditAuthenticationRequired=false;
	private boolean parkedOrderEditAuthenticationRequired=false;
	
	private boolean useOrderQueueNo=true;
	private  String[] enabledServices;
	private boolean showKeyStrokeCharOnButtons=false;
	private  String[] quickPayMode;

	private String labelFont;
	private String windowTitleFont;
	private String textAreaFont	;
	private String queueNoPrefix;
	private String invoiceNoPrefix;

	
	/**
	 * @return the mainMenuUISettings
	 */
	public BeanUIMainMenuSetting getMainMenuUISettings() {
		return mainMenuUISettings;
	}

	/**
	 * @param mainMenuUISettings the mainMenuUISettings to set
	 */
	public void setMainMenuUISettings(BeanUIMainMenuSetting mainMenuUISettings) {
		this.mainMenuUISettings = mainMenuUISettings;
	}

	private boolean showLightBox;
	private boolean applyWindowRenderingHack;;
	private String  serviceTakeAwayTitle;
	private String  serviceHomeDelTitle;
	private String  serviceTableTitle;
	private String 	wholeSaleTitle;
	private String 	serviceSalesOrderTitle;
	/**
	 * @return the showLightBox
	 */
	public boolean showLightBox() {
		return showLightBox;
	}

	/**
	 * @param showLightBox the showLightBox to set
	 */
	public void setLightBox(boolean showLightBox) {
		this.showLightBox = showLightBox;
	}



	/**
	 * @return the orderEntryUISettings
	 */
	public BeanUIOrderEntrySetting getOrderEntryUISettings() {
		return orderEntryUISettings;
	}

	/**
	 * @param orderEntryUISettings the orderEntryUISettings to set
	 */
	public void setOrderEntryUISettings(BeanUIOrderEntrySetting orderEntryUISettings) {
		this.orderEntryUISettings = orderEntryUISettings;
	}

	/**
	 * @return the terminalUISettings
	 */
	public BeanUITerminalTabsSetting getTerminalUISettings() {
		return terminalUISettings;
	}

	/**
	 * @param terminalUISettings
	 */
	public void setTerminalUISettings(BeanUITerminalTabsSetting terminalUISettings) {
		this.terminalUISettings = terminalUISettings;
	}

	/**
	 * @param b
	 */
	public void setApplyWindowRenderingHack(boolean use) {

		this.applyWindowRenderingHack=use;
		
	}

	/**
	 * @return the useWindowRenderingHack
	 */
	public boolean isApplyWindowRenderingHack() {
		
		return applyWindowRenderingHack;
	}

	/**
	 * @return the orderDetailSettings
	 */
	public BeanUIOrderDetailSetting getOrderDetailSettings() {
		return orderDetailSettings;
	}

	/**
	 * @param orderDetailSettings the orderDetailSettings to set
	 */
	public void setOrderDetailSettings(BeanUIOrderDetailSetting orderDetailSettings) {
		this.orderDetailSettings = orderDetailSettings;
	}

	
	/**
	 * @return the orderDetailSettings
	 */
	public BeanUIOrderListSetting getOrderListSettings() {
		return orderListSettings ;
	}

	/**
	 * @param orderDetailSettings the orderDetailSettings to set
	 */
	public void setOrderListSettings(BeanUIOrderListSetting orderListSettings) {
		this.orderListSettings  = orderListSettings;
	}
	
	/**
	 * @return the serviceTakeAwayTitle
	 */
	public String getServiceTakeAwayTitle() {
		return serviceTakeAwayTitle;
	}

	/**
	 * @param serviceTakeAwayTitle the serviceTakeAwayTitle to set
	 */
	public void setServiceTakeAwayTitle(String serviceTakeAwayTitle) {
		this.serviceTakeAwayTitle = serviceTakeAwayTitle;
	}

	/**
	 * @return the serviceHomeDelTitle
	 */
	public String getServiceHomeDelTitle() {
		return serviceHomeDelTitle;
	}

	/**
	 * @param serviceHomeDelTitle the serviceHomeDelTitle to set
	 */
	public void setServiceHomeDelTitle(String serviceHomeDelTitle) {
		this.serviceHomeDelTitle = serviceHomeDelTitle;
	}

	/**
	 * @return the serviceTableTitle
	 */
	public String getServiceTableTitle() {
		return serviceTableTitle;
	}
	
	/**
	 * @return the serviceTableTitle
	 */
	public String getServiceWholeSaleTitle() {
		return wholeSaleTitle;
	}
	
	/**
	 * @return the serviceTableTitle
	 */
	public void setServiceWholeSaleTitle(String wholeSaleTitle) {
		this.wholeSaleTitle=wholeSaleTitle;
	}

	/**
	 * @param serviceTableTitle the serviceTableTitle to set
	 */
	public void setServiceTableTitle(String serviceTableTitle) {
		this.serviceTableTitle = serviceTableTitle;
	}
	
	
	/**
	 * @return the salesOrderTitle
	 */
	public String getServiceSalesOrderTitle() {
		return serviceSalesOrderTitle;
	}

	/**
	 * @param salesOrderTitle the salesOrderTitle to set
	 */
	public void setServiceSalesOrderTitle(String salesOrderTitle) {
		this.serviceSalesOrderTitle = salesOrderTitle;
	}

	/**
	 * @param serviceTableTitle the serviceTableTitle to set
	 */
	public void setEnabledOrderServices(String enabledServices) {
		
		this.enabledServices=enabledServices.trim().split(",");
		
	}
	
	/**
	 * @return
	 */
	public String[] getEnabledOrderServices(){
		
		return this.enabledServices;
	}
	
	/**
	 * @return the useOrderQueueNo
	 */
	public boolean useOrderQueueNo() {
		
		return useOrderQueueNo;
	}
	/**
	 * @param useOrderQueueNo  
	 */
	public void setUseOrderQueueNo(boolean useOrderQueueNo) {
		
		this.useOrderQueueNo = useOrderQueueNo;
	}

	/**
	 * @return the authenticationRequired
	 */
	public boolean isBilledItemEditAuthenticationRequired() {
		
		return billedOrderEditAuthenticationRequired;
	}
	/**
	 * @param authenticationRequired the authenticationRequired to set
	 */
	public void setBilledOrderEditAuthenticationRequired(boolean authenticationRequired) {
		
		this.billedOrderEditAuthenticationRequired = authenticationRequired;
	}
	
	
	/**
	 * @return the parked Order Edit Authentication Required
	 */
	public boolean isParkedItemEditAuthenticationRequired() {
		
		return parkedOrderEditAuthenticationRequired;
	}
	/**
	 * @param authenticationRequired the authenticationRequired to set
	 */
	public void setParkedOrderEditAuthenticationRequired(boolean authenticationRequired) {
		
		this.parkedOrderEditAuthenticationRequired = authenticationRequired;
	}
	
	/**
	 * @return the lableFont
	 */
	public String getLabelFont() {
		return labelFont;
	}

	/**
	 * @param lableFont the lableFont to set
	 */
	public void setLabelFont(String lableFont) {
		this.labelFont = lableFont;
	}

	/**
	 * @return the windowTitleFont
	 */
	public String getWindowTitleFont() {
		return windowTitleFont;
	}

	/**
	 * @param windowTitleFont the windowTitleFont to set
	 */
	public void setWindowTitleFont(String windowTitleFont) {
		this.windowTitleFont = windowTitleFont;
	}

	/**
	 * @return the textAreaFont
	 */
	public String getTextAreaFont() {
		return textAreaFont;
	}

	/**
	 * @param textAreaFont the textAreaFont to set
	 */
	public void setTextAreaFont(String textAreaFont) {
		this.textAreaFont = textAreaFont;
	}

	/**
	 * @return the queueNoPrefix
	 */
	public String getQueueNoPrefix() {
		return queueNoPrefix;
	}

	/**
	 * @param queueNoPrefix the queueNoPrefix to set
	 */
	public void setQueueNoPrefix(String queueNoPrefix) {
		this.queueNoPrefix = queueNoPrefix;
	}

	/**
	 * @return the invoiceNoPrefix
	 */
	public String getInvoiceNoPrefix() {
		return invoiceNoPrefix;
	}

	/**
	 * @param invoiceNoPrefix the invoiceNoPrefix to set
	 */
	public void setInvoiceNoPrefix(String invoiceNoPrefix) {
		this.invoiceNoPrefix = invoiceNoPrefix;
	}


	
/**
	 * @return the showKeyStrokeCharOnButtons
	 */
	public boolean showKeyStrokeCharOnButtons() {
		return showKeyStrokeCharOnButtons;
	}

	/**
	 * @param showKeyStrokeCharOnButtons the showKeyStrokeCharOnButtons to set
	 */
	public void setShowKeyStrokeCharOnButtons(boolean showKeyStrokeCharOnButtons) {
		this.showKeyStrokeCharOnButtons = showKeyStrokeCharOnButtons;
	}

	/**
	 * @return the quickPayMode
	 */
	public String[] getQuickPayMode() {
		return quickPayMode;
	}

	/**
	 * @param quickPayMode the quickPayMode to set
	 */
	public void setQuickPayMode(String quickPayMode) {
		
		this.quickPayMode =(quickPayMode==null ||quickPayMode.trim().equals(""))?null:quickPayMode.trim().split(",");
		
	}
 
	
}
