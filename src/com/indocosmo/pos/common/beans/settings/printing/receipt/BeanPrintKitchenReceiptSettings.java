/**
 * 
 */
package com.indocosmo.pos.common.beans.settings.printing.receipt;

import java.awt.Font;
import java.util.ArrayList;

import com.indocosmo.pos.common.enums.PosOrderServiceTypes;
import com.indocosmo.pos.common.utilities.PosNumberUtil;
import com.indocosmo.pos.common.utilities.PosOrderUtil;

/**
 * @author jojesh-13.2
 *
 */
public class BeanPrintKitchenReceiptSettings {
	
	public final static String PS_FONT_NAME="kitchen_receipt.font_name";
	public final static String PS_FONT_STYLE="kitchen_receipt.font_style";
	public final static String PS_FONT_SIZE="kitchen_receipt.font_size";
	
	
	public final static String PS_ITEM_DETAIL_FONT_NAME="kitchen_receipt.item_detail_font_name";
	public final static String PS_ITEM_DETAIL_FONT_STYLE="kitchen_receipt.item_detail_font_style";
	public final static String PS_ITEM_DETAIL_FONT_SIZE="kitchen_receipt.item_detail_font_size";
	
	public final static String PS_NO_BLANK_LINES_HDR="kitchen_receipt.no_blank_lines_hdr";
	public final static String PS_NO_BLANK_LINES_FOOTER="kitchen_receipt.no_blank_lines_footer";
	public final static String PS_SHOW_RELATED_KITCHEN="kitchen_receipt.show_related_kitchen";
	public final static String PS_SHOW_BARCODE="kitchen_receipt.show_barcode";
	public final static String PS_CUSTOMER_PRINTING_FOR_SERVICES="customer_info_in_kot_receipt_services";
	public final static String PS_SHOW_KITCHEN_QUEUE_PREFIX="kitchen_receipt.show_queue_no_prefix";
	
	private String fontName="";
	private int fontStyle=0;
	private int fontSize=8;
	
	private String itemDetailFontName="";
	private int itemDetailFontStyle=0;
	private int itemDetailFontSize=8;
	private int mNoOfBlankLinesHdr=0;
	private int mNoOfBlankLinesFooter=0;
	private boolean mShowRelatedKitchen=true;
	private boolean showBarcode;
	private String[] customerPrintingForServices;
	private boolean showKitchenQueuePrefix=true;
	/**
	 * @return the mNoOfBlankLinesHdr
	 */
	public int getNoOfBlankLinesHdr() {
		return mNoOfBlankLinesHdr;
	}

	/**
	 * @param mNoOfBlankLinesHdr the mNoOfBlankLinesHdr to set
	 */
	public void setNoOfBlankLinesHdr(int noOfBlankLinesHdr) {
		this.mNoOfBlankLinesHdr = noOfBlankLinesHdr;
	}

	/**
	 * @return the mNoOfBlankLinesFooter
	 */
	public int getNoOfBlankLinesFooter() {
		return mNoOfBlankLinesFooter;
	}

	/**
	 * @param mNoOfBlankLinesFooter the mNoOfBlankLinesFooter to set
	 */
	public void setNoOfBlankLinesFooter(int noOfBlankLinesFooter) {
		this.mNoOfBlankLinesFooter = noOfBlankLinesFooter;
	}

	/**
	 * @return the mShowRelatedKitchen
	 */
	public boolean showRelatedKitchens() {
		return mShowRelatedKitchen;
	}

	/**
	 * @param mShowRelatedKitchen the mShowRelatedKitchen to set
	 */
	public void setShowRelatedKitchen(boolean showRelatedKitchen) {
		this.mShowRelatedKitchen = showRelatedKitchen;
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
	 * @return
	 */
	public Font getFont(){
		
		return new Font(fontName,fontStyle,fontSize);
	}
	/**
	 * @return the printBarcode
	 */
	public boolean showBarcode() {
		return showBarcode;
	}

	/**
	 * @param printBarcode the printBarcode to set
	 */
	public void setShowBarcode(boolean showBarcode) {
		this.showBarcode = showBarcode;
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
	 * @return the fontName
	 */
	public String getFontName() {
		return fontName;
	}

	/**
	 * @param fontName the fontName to set
	 */
	public void setFontName(String fontName) {
		this.fontName = fontName;
	}

	/**
	 * @return the fontStyle
	 */
	public int getFontStyle() {
		return fontStyle;
	}

	/**
	 * @param fontStyle the fontStyle to set
	 */
	public void setFontStyle(int fontStyle) {
		this.fontStyle = fontStyle;
	}

	/**
	 * @return the fontSize
	 */
	public int getFontSize() {
		return fontSize;
	}

	/**
	 * @param fontSize the fontSize to set
	 */
	public void setFontSize(int fontSize) {
		this.fontSize = fontSize;
	}

	/**
	 * @return the showKitchenQueuePrefix
	 */
	public boolean isShowKitchenQueuePrefix() {
		return showKitchenQueuePrefix;
	}

	/**
	 * @param showKitchenQueuePrefix the showKitchenQueuePrefix to set
	 */
	public void setShowKitchenQueuePrefix(boolean showKitchenQueuePrefix) {
		this.showKitchenQueuePrefix = showKitchenQueuePrefix;
	}

	
}
