/**
 * 
 */
package com.indocosmo.pos.common.beans.settings.printing;

import java.util.HashMap;
import java.util.Map;

import com.indocosmo.pos.common.beans.settings.printing.receipt.BeanPrintKitchenReceiptSettings;
import com.indocosmo.pos.common.beans.settings.printing.receipt.BeanPrintPaymentReceiptSettings;
import com.indocosmo.pos.common.beans.settings.printing.receipt.BeanPrintRefundReceiptSettings;
import com.indocosmo.pos.common.enums.EnablePrintingOption;
import com.indocosmo.pos.common.enums.PosInvoicePrintFormat;
import com.indocosmo.pos.common.enums.PosOrderServiceTypes;
import com.indocosmo.pos.common.utilities.PosOrderUtil;

/**
 * @author jojesh-13.2
 *
 */
 
public class BeanPrintSettings {
	
	public final static String PS_RECEIPT_PRINTING_AT_PAYMENT="receipt_printing_at_payment";
	public final static String PS_RECEIPT_PRINTING_AT_REFUND="receipt_printing_at_refund";
	public final static String PS_KITCHEN_PRINTING_AT_PAYMENT="kitchen_printing_at_payment";
	public final static String PS_KITCHEN_PRINTING_AT_PARKING="kitchen_printing_at_parking";
	public final static String PS_BILL_PRINTING_AT_PARKING="bill_printing_at_parking";
	public final static String PS_KITCHEN_PRINTING_TO_COUNTER="kitchen_printing_to_counter";
	public final static String PS_SUMMARY_PRINTING_AT_SHIFT_CLOSING="summary_printing_at_shift_closing";
	public final static String PS_DAYEND_REPORT_PRINTING_AT_DAYEND="dayend_report_printing_at_dayend";
	public final static String PS_RESHITO_PRINTING_AT_PAYMENT="reshito_printing_at_payment";
	public final static String PS_ENABLE_RESHITO_PRINTING="enable_reshito_printing";
	public final static String PS_ALLOW_LANGUAGE_SWITCHING="allow_language_switching";
	public final static String PS_PRINTING_FORMAT="prining_format";
	public final static String PS_BILL_PRINTING_AT_PARKING_FOR_SERVICES="bill_printing_at_parking_services";
	public final static String PS_INVOICE_PRINTING_FORMAT="invoice_printing_format";
	
	public final static String PS_INVOICE_PRINTING_FORMAT_TAKEAWAY="invoice_printing_format_takeaway";
	public final static String PS_INVOICE_PRINTING_FORMAT_HOMEDELIVERY="invoice_printing_format_homedelivery";
	public final static String PS_INVOICE_PRINTING_FORMAT_TABLE="invoice_printing_format_tableservice";
	public final static String PS_INVOICE_PRINTING_FORMAT_WHOLESALE="invoice_printing_format_wholesale";
	public final static String PS_INVOICE_PRINTING_FORMAT_SALESORDER="invoice_printing_format_salesorder";
	public final static String PS_BARCODE_PRINTING="barcode_printing";
	
	private EnablePrintingOption receiptPrintingAtPayment;
	private EnablePrintingOption receiptPrintingAtRefund;
	private EnablePrintingOption printToKitchenAtPayment;
	private EnablePrintingOption printToKitchenAtParking;
	private EnablePrintingOption billPrintingAtParking;
	private boolean kitchenPrintingToCounter;
	private EnablePrintingOption printSummaryAtShitClosing;
	private EnablePrintingOption printReshitoAtPayment;
	private EnablePrintingOption printDayEndReportAtDayEnd;
	
//	private PosInvoicePrintFormat invoicePrintFormat;
//	private PosInvoicePrintFormat invoicePrintFormatTakeAway;
//	private PosInvoicePrintFormat invoicePrintFormatHomeDelivery;
//	private PosInvoicePrintFormat invoicePrintFormatTableService;
//	private PosInvoicePrintFormat invoicePrintFormatWholesale;
//	private PosInvoicePrintFormat invoicePrintFormatSalesOrder;
	
	private boolean enableReshitoPrinting=false;
	private boolean languageSwitchingAllowed=false;
	private String printingFormat="STD";
	private boolean mBarcodePrinting;
	
	
	private  String[] billPrintAtParkingForServices;

	private BeanPrintPaymentReceiptSettings paymentReceiptSettings;
	private BeanPrintRefundReceiptSettings refundReceiptSettings;
	private BeanPrintKitchenReceiptSettings kitchenReceiptSettings;
	
	private BeanPrintDayEndReportSettings dayEndReportSettings;
	private BeanPrintShiftReportSettings shiftReportSettings;
	
	private Map<Integer, PosInvoicePrintFormat> invoiceFormats;



	/**
	 * @return the reundReceiptSettings
	 */
	public BeanPrintRefundReceiptSettings getRefundReceiptSettings() {
		return refundReceiptSettings;
	}

	/**
	 * @param paymentReceiptSettings the paymentReceiptSettings to set
	 */
	public void setRefundReceiptSettings(
			BeanPrintRefundReceiptSettings refundReceiptSettings) {
		this.refundReceiptSettings = refundReceiptSettings;
	}
	
		
	
	/**
	 * @return the paymentReceiptSettings
	 */
	public BeanPrintPaymentReceiptSettings getPaymentReceiptSettings() {
		return paymentReceiptSettings;
	}

	/**
	 * @param paymentReceiptSettings the paymentReceiptSettings to set
	 */
	public void setPaymentReceiptSettings(
			BeanPrintPaymentReceiptSettings paymentReceiptSettings) {
		this.paymentReceiptSettings = paymentReceiptSettings;
	}
	
	/**
	 * @param kitchenSetting
	 */
	public void setKitchenReceiptSettings(
			BeanPrintKitchenReceiptSettings kitchenSetting) {
		this.kitchenReceiptSettings = kitchenSetting;
		
	}

	/**
	 * @return the kitchenReceiptSettings
	 */
	public BeanPrintKitchenReceiptSettings getKitchenReceiptSettings() {
		return kitchenReceiptSettings;
	}

	/**
	 * @return the receiptPrinting
	 */
	public EnablePrintingOption getReceiptPrintingAtPayment() {
		return receiptPrintingAtPayment;
	}

	/**
	 * @param receiptPrinting the receiptPrinting to set
	 */
	public void setReceiptPrintingAtPayment(EnablePrintingOption receiptPrinting) {
		this.receiptPrintingAtPayment = receiptPrinting;
	}

	/**
	 * @return the printToKitchenAtPayment
	 */
	public EnablePrintingOption getPrintToKitchenAtPayment() {
		return printToKitchenAtPayment;
	}

	/**
	 * @param printToKitchenAtPayment the printToKitchenAtPayment to set
	 */
	public void setPrintToKitchenAtPayment(
			EnablePrintingOption printToKitchenAtPayment) {
		this.printToKitchenAtPayment = printToKitchenAtPayment;
	}

	/**
	 * @return the printToKitchenAtParking
	 */
	public EnablePrintingOption getPrintToKitchenAtParking() {
		return printToKitchenAtParking;
	}

	/**
	 * @param printToKitchenAtParking the printToKitchenAtParking to set
	 */
	public void setPrintToKitchenAtParking(
			EnablePrintingOption printToKitchenAtParking) {
		this.printToKitchenAtParking = printToKitchenAtParking;
	}

	/**
	 * @return the printSummaryAtShitClosing
	 */
	public EnablePrintingOption getPrintSummaryAtShitClosing() {
		return printSummaryAtShitClosing;
	}

	/**
	 * @param printSummaryAtShitClosing the printSummaryAtShitClosing to set
	 */
	public void setPrintSummaryAtShitClosing(
			EnablePrintingOption printSummaryAtShitClosing) {
		this.printSummaryAtShitClosing = printSummaryAtShitClosing;
	}

	/**
	 * @return the printReshitoAtPayment
	 */
	public EnablePrintingOption getPrintReshitoAtPayment() {
		return ((enableReshitoPrinting)?printReshitoAtPayment:EnablePrintingOption.NO);
	}

	/**
	 * @param printReshitoAtPayment the printReshitoAtPayment to set
	 */
	public void setPrintReshitoAtPayment(EnablePrintingOption printReshitoAtPayment) {
		this.printReshitoAtPayment = printReshitoAtPayment;
	}

	/**
	 * @return the enableReshitoPrinting
	 */
	public boolean isPrintingReshitoEnabled() {
		return enableReshitoPrinting;
	}

	/**
	 * @param enableReshitoPrinting the enableReshitoPrinting to set
	 */
	public void setPrintingReshitoEnabled(boolean enableReshitoPrinting) {
		this.enableReshitoPrinting = enableReshitoPrinting;
	}

	/**
	 * @return the languageSwitchingAllowed
	 */
	public boolean isLanguageSwitchingAllowed() {
		return languageSwitchingAllowed;
	}

	/**
	 * @param languageSwitchingAllowed the languageSwitchingAllowed to set
	 */
	public void setLanguageSwitchingAllowed(boolean languageSwitchingAllowed) {
		this.languageSwitchingAllowed = languageSwitchingAllowed;
	}

	/**
	 * @return the printDayEndReportAtDayEnd
	 */
	public EnablePrintingOption getPrintDayEndReportAtDayEnd() {
		return printDayEndReportAtDayEnd;
	}

	/**
	 * @param printDayEndReportAtDayEnd the printDayEndReportAtDayEnd to set
	 */
	public void setPrintDayEndReportAtDayEnd(
			EnablePrintingOption printDayEndReportAtDayEnd) {
		this.printDayEndReportAtDayEnd = printDayEndReportAtDayEnd;
	}

	/**
	 * @return the receiptPrintingAtRefund
	 */
	public EnablePrintingOption getReceiptPrintingAtRefund() {
		return receiptPrintingAtRefund;
	}

	/**
	 * @param receiptPrintingAtRefund the receiptPrintingAtRefund to set
	 */
	public void setReceiptPrintingAtRefund(
			EnablePrintingOption receiptPrintingAtRefund) {
		this.receiptPrintingAtRefund = receiptPrintingAtRefund;
	}

	/**
	 * @return the printingFormat
	 */
	public String getPrintingFormat() {
		return printingFormat;
	}

	/**
	 * @param printingFormat the printingFormat to set
	 */
	public void setPrintingFormat(String printingFormat) {
		this.printingFormat = printingFormat;
	}

	/**
	 * @return the kitchenPrintingToCounter
	 */
	public boolean isKitchenPrintingToCounter() {
		return kitchenPrintingToCounter;
	}

	/**
	 * @param kitchenPrintingToCounter the kitchenPrintingToCounter to set
	 */
	public void setKitchenPrintingToCounter(boolean kitchenPrintingToCounter) {
		this.kitchenPrintingToCounter = kitchenPrintingToCounter;
	}

	/**
	 * @return the dayEndReportSettings
	 */
	public BeanPrintDayEndReportSettings getDayEndReportSettings() {
		return dayEndReportSettings;
	}

	/**
	 * @param dayEndReportSettings the dayEndReportSettings to set
	 */
	public void setDayEndReportSettings(
			BeanPrintDayEndReportSettings dayEndReportSettings) {
		this.dayEndReportSettings = dayEndReportSettings;
	}

	/**
	 * @return the shiftReportSettings
	 */
	public BeanPrintShiftReportSettings getShiftReportSettings() {
		return shiftReportSettings;
	}

	/**
	 * @param shiftReportSettings the shiftReportSettings to set
	 */
	public void setShiftReportSettings(
			BeanPrintShiftReportSettings shiftReportSettings) {
		this.shiftReportSettings = shiftReportSettings;
	}

	/**
	 * @return the receiptPrintingAtParking
	 */
	public EnablePrintingOption getBillPrintingAtParking() {
		return billPrintingAtParking;
	}

	/**
	 * @param receiptPrintingAtParking the receiptPrintingAtParking to set
	 */
	public void setBillPrintingAtParking(
			EnablePrintingOption receiptPrintingAtParking) {
		this.billPrintingAtParking = receiptPrintingAtParking;
	}

	/**
	 * @return the billPrintAtParkingForServices
	 */
	public String[] getBillPrintAtParkingForServices() {
		return billPrintAtParkingForServices;
	}

	public boolean isEnabledBillPrintAtParking(int currentService){
		
		return PosOrderUtil.isServiceEnabled(billPrintAtParkingForServices, currentService);
	}
	/**
	 * @param billPrintAtParkingForServices the billPrintAtParkingForServices to set
	 */
	public void setBillPrintAtParkingForServices(String servcices) {
		
		this.billPrintAtParkingForServices =servcices.trim().equals("")?null:servcices.trim().split(",");
		
	}
 

//	/**
//	 * @return the invoiceFormat
//	 */
//	public PosInvoicePrintFormat getInvoicePrintFormat() {
//		return invoicePrintFormat;
//	}

//	/**
//	 * @param invoiceFormat the invoiceFormat to set
//	 */
//	public void setInvoicePrintFormat(PosInvoicePrintFormat invoiceFormat) {
//		this.invoicePrintFormat = invoiceFormat;
//	}
//
//	/**
//	 * @return the invoicePrintFormatTakeAway
//	 */
//	public PosInvoicePrintFormat getInvoicePrintFormatTakeAway() {
//		return invoicePrintFormatTakeAway;
//	}

//	/**
//	 * @param invoicePrintFormatTakeAway the invoicePrintFormatTakeAway to set
//	 */
//	public void setInvoicePrintFormatTakeAway(
//			PosInvoicePrintFormat invoicePrintFormatTakeAway) {
//		this.invoicePrintFormatTakeAway = invoicePrintFormatTakeAway;
//	}
//
//	/**
//	 * @return the invoicePrintFormatHomeDelivery
//	 */
//	public PosInvoicePrintFormat getInvoicePrintFormatHomeDelivery() {
//		return invoicePrintFormatHomeDelivery;
//	}
//
//	/**
//	 * @param invoicePrintFormatHomeDelivery the invoicePrintFormatHomeDelivery to set
//	 */
//	public void setInvoicePrintFormatHomeDelivery(
//			PosInvoicePrintFormat invoicePrintFormatHomeDelivery) {
//		this.invoicePrintFormatHomeDelivery = invoicePrintFormatHomeDelivery;
//	}
//
//	/**
//	 * @return the invoicePrintFormatTableService
//	 */
//	public PosInvoicePrintFormat getInvoicePrintFormatTableService() {
//		return invoicePrintFormatTableService;
//	}
//
//	/**
//	 * @param invoicePrintFormatTableService the invoicePrintFormatTableService to set
//	 */
//	public void setInvoicePrintFormatTableService(
//			PosInvoicePrintFormat invoicePrintFormatTableService) {
//		this.invoicePrintFormatTableService = invoicePrintFormatTableService;
//	}
//
//	/**
//	 * @return the invoicePrintFormatWholesale
//	 */
//	public PosInvoicePrintFormat getInvoicePrintFormatWholesale() {
//		return invoicePrintFormatWholesale;
//	}
//
//	/**
//	 * @param invoicePrintFormatWholesale the invoicePrintFormatWholesale to set
//	 */
//	public void setInvoicePrintFormatWholesale(
//			PosInvoicePrintFormat invoicePrintFormatWholesale) {
//		this.invoicePrintFormatWholesale = invoicePrintFormatWholesale;
//	}
//
//	/**
//	 * @return the invoicePrintFormatSalesOrder
//	 */
//	public PosInvoicePrintFormat getInvoicePrintFormatSalesOrder() {
//		return invoicePrintFormatSalesOrder;
//	}
//
//	/**
//	 * @param invoicePrintFormatSalesOrder the invoicePrintFormatSalesOrder to set
//	 */
//	public void setInvoicePrintFormatSalesOrder(
//			PosInvoicePrintFormat invoicePrintFormatSalesOrder) {
//		this.invoicePrintFormatSalesOrder = invoicePrintFormatSalesOrder;
//		
//	}



	/**
	 * @return the invoiceFormat 
	 */
	public PosInvoicePrintFormat getInvoiceFormat(PosOrderServiceTypes serviceType) {
		return invoiceFormats.get(serviceType.getCode());
	}
	/**
	 * @return the invoiceFormats
	 */
	public Map<Integer, PosInvoicePrintFormat> getInvoiceFormats() {
		return invoiceFormats;
	}

	/**
	 * @param invoiceFormats the invoiceFormats to set
	 */
	public void setInvoiceFormats(PosOrderServiceTypes serviceType, PosInvoicePrintFormat invoiceFormat) {
		if (invoiceFormats==null)
			invoiceFormats=new HashMap<Integer, PosInvoicePrintFormat>();
		
		this.invoiceFormats.put(serviceType.getCode(), invoiceFormat);
	}
	/**
	 * @return the mBarcodePrintting
	 */
	public boolean isBarCodePrintingEnabled() {
		return mBarcodePrinting;
	}
	/**
	 * @param mBarcodePrintting the mBarcodePrintting to set
	 */
	public void setBarcodePrintingEnabled(boolean mBarcodePrinting) {
		this.mBarcodePrinting = mBarcodePrinting;
	}
}
