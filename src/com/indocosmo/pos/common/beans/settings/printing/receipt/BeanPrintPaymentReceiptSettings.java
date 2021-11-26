/**
 * 
 */
package com.indocosmo.pos.common.beans.settings.printing.receipt;

import java.awt.Font;
import java.util.ArrayList;

import com.indocosmo.pos.common.beans.settings.ui.orderentry.BeanUIOrderEntrySetting;
import com.indocosmo.pos.common.enums.PaymentReceiptItemGrouping;
import com.indocosmo.pos.common.enums.PaymentReceiptType;
import com.indocosmo.pos.common.enums.PosOrderServiceTypes;
import com.indocosmo.pos.common.utilities.PosNumberUtil;
import com.indocosmo.pos.common.utilities.PosOrderUtil;

/**
 * @author jojesh-13.2
 *
 */
public class BeanPrintPaymentReceiptSettings {
	
	public final static String PS_SHOW_MODIFIERS="payment_receipt.show_modifiers";
	public final static String PS_ITEM_DETAIL_FONT_NAME="payment_receipt.item_detail_font_name";
	public final static String PS_ITEM_DETAIL_FONT_STYLE="payment_receipt.item_detail_font_style";
	public final static String PS_ITEM_DETAIL_FONT_SIZE="payment_receipt.item_detail_font_size";
	public final static String PS_SHOW_ITEM_REMARKS="payment_receipt.show_item_remarks";
	public final static String PS_CUSTOMER_PRINTING_FOR_SERVICES="customer_info_in_payment_receipt_services";
	public final static String PS_RECEIPT_TYPE="payment_receipt.receipt_type";
	public final static String PS_RECEIPT_ITEM_GROUPING="payment_receipt.item_grouping";

	private boolean modifiersVisible=false;
	private String itemDetailFontName="";
	private int itemDetailFontStyle=0;
	private int itemDetailFontSize=8;
	private boolean showItemRemarks;
	private String[] customerPrintingForServices;
	private PaymentReceiptType receiptType;
	private PaymentReceiptItemGrouping itemGroupingMethod;
	/**
	 * @return the modifiersVisible
	 */
	public boolean isModifiersVisible() {
		return modifiersVisible;
	}

	/**
	 * @param modifiersVisible the modifiersVisible to set
	 */
	public void setModifiersVisible(boolean modifiersVisible) {
		this.modifiersVisible = modifiersVisible;
	}

	/**
	 * @return the itemDetailFontName
	 */
	public String getItemDetailFontName() {
		return itemDetailFontName;
	}

	/**
	 * @param itemDetailFontName the itemDetailFontName to set
	 */
	public void setItemDetailFontName(String itemDetailFontName) {
		this.itemDetailFontName = itemDetailFontName;
	}

	/**
	 * @return the itemDetailFontStyle
	 */
	public int getItemDetailFontStyle() {
		return itemDetailFontStyle;
	}

	/**
	 * @param itemDetailFontStyle the itemDetailFontStyle to set
	 */
	public void setItemDetailFontStyle(int itemDetailFontStyle) {
		this.itemDetailFontStyle = itemDetailFontStyle;
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
	 * @return
	 */
	public Font getItemDetailFont(){
		
		return new Font(itemDetailFontName,itemDetailFontStyle,itemDetailFontSize);
	}

 
	/**
	 * @return the showItemRemarks
	 */
	public boolean showItemRemarks() {
		return showItemRemarks;
	}

	/**
	 * @param showItemRemarks the showItemRemarks to set
	 */
	public void setShowItemRemarks(boolean showItemRemarks) {
		this.showItemRemarks = showItemRemarks;
	}
	/**
	 * @return the customerPrintForServices
	 */
	public String[] getCustomerInfoPrintingServices() {
		
		return customerPrintingForServices;
	}

	/**
	 * @param customerPrintForServices the customerPrintForServices to set
	 */
	public void setCustomerInfoPrintingServices(
			String services) {
		
		if (services==null || services.trim().equals(""))
			this.customerPrintingForServices=null;
		else{
			
			customerPrintingForServices=services.trim().split(",");
		}
	}
	
	/*
	 * 
	 */
	public boolean isEnabledCustomerInfoPrinting(int currentService){
		
		return PosOrderUtil.isServiceEnabled(customerPrintingForServices, currentService);
	}

	/**
	 * @return the receiptType
	 */
	public PaymentReceiptType getReceiptType() {
		return receiptType;
	}

	/**
	 * @param receiptType the receiptType to set
	 */
	public void setReceiptType(int receiptType) {
		if(receiptType==0)
			this.receiptType=PaymentReceiptType.Normal;
		else
			this.receiptType = PaymentReceiptType.get(receiptType);
	}

	/**
	 * @return the itemGroupingMethod
	 */
	public PaymentReceiptItemGrouping getItemGroupingMethod() {
		return itemGroupingMethod;
	}

	/**
	 * @param itemGroupingMethod the itemGroupingMethod to set
	 */
	public void setItemGroupingMethod(int itemGroupingMethod) {
		this.itemGroupingMethod = PaymentReceiptItemGrouping.get(itemGroupingMethod);
	}
 
	
}
