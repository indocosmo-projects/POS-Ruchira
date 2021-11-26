/**
 * 
 */
package com.indocosmo.pos.reports.shift;

import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.swing.SwingWorker;

import org.apache.poi.xwpf.usermodel.LineSpacingRule;

import com.indocosmo.pos.common.PosEnvSettings;
import com.indocosmo.pos.common.beans.BeanReceiptTaxSummary;
import com.indocosmo.pos.common.enums.PosQueueNoResetPolicy;
import com.indocosmo.pos.common.enums.PosOrderServiceTypes;
import com.indocosmo.pos.common.utilities.PosCurrencyUtil;
import com.indocosmo.pos.common.utilities.PosDateUtil;
import com.indocosmo.pos.common.utilities.PosFormUtil;
import com.indocosmo.pos.common.utilities.PosNumberUtil;
import com.indocosmo.pos.common.utilities.PosOrderUtil;
import com.indocosmo.pos.common.utilities.PosSaleItemUtil;
import com.indocosmo.pos.common.utilities.PosUomUtil;
import com.indocosmo.pos.data.beans.BeanDiscountSummary;
import com.indocosmo.pos.data.beans.BeanOrderDetail;
import com.indocosmo.pos.data.beans.BeanOrderHeader;
import com.indocosmo.pos.data.beans.BeanOrderHeader.PosOrderStatus;
import com.indocosmo.pos.data.beans.BeanOrderPayment;
import com.indocosmo.pos.data.beans.BeanOrderPaymentReport;
import com.indocosmo.pos.data.beans.BeanPayModeBase;
import com.indocosmo.pos.data.beans.BeanPayModeBase.PaymentMode;
import com.indocosmo.pos.data.beans.BeanSaleItem;
import com.indocosmo.pos.data.beans.BeanShiftCloseSummary;
import com.indocosmo.pos.data.beans.BeanShopShift;
import com.indocosmo.pos.data.beans.BeanTax;
import com.indocosmo.pos.data.beans.BeanUOM;
import com.indocosmo.pos.data.providers.shopdb.PosCashOutProvider;
import com.indocosmo.pos.data.providers.shopdb.PosOrderDiscountProvider;
import com.indocosmo.pos.data.providers.shopdb.PosOrderDtlProvider;
import com.indocosmo.pos.data.providers.shopdb.PosOrderHdrProvider;
import com.indocosmo.pos.data.providers.shopdb.PosOrderPaymentHdrProvider;
import com.indocosmo.pos.data.providers.shopdb.PosOrderPaymentsProvider;
import com.indocosmo.pos.data.providers.shopdb.PosShiftReportProvider;
import com.indocosmo.pos.data.providers.shopdb.PosShiftSummaryProvider;
import com.indocosmo.pos.data.providers.shopdb.PosTaxItemProvider;
import com.indocosmo.pos.data.providers.shopdb.PosUOMProvider;
import com.indocosmo.pos.forms.components.objectbrowser.IPosBrowsableItem;
import com.indocosmo.pos.reports.base.PosPrintableBase;
import com.indocosmo.pos.reports.base.PosPrintableBase.TextAlign;
import com.indocosmo.pos.terminal.devices.PosDeviceManager;
import com.indocosmo.pos.terminal.devices.PosPrintingDevice;
import com.indocosmo.pos.terminal.devices.printer.PosDevicePrinter;
import com.sun.org.apache.xml.internal.serialize.LineSeparator;

/**
 * @author deepak
 * class for day end and mid day shift report
 */
public class PosShiftReport extends PosPrintableBase{
	
 
	protected static final int TAX_FIELD_WIDTH = 40;
	protected    int DESC_FIELD_WIDTH =  115;
	protected static final int QTY_FIELD_WIDTH =  30;
	protected static final int RATE_FIELD_WIDTH = 50;
	protected static final int INVNO_FIELD_WIDTH =  90;
	protected static final int PAYMODE_FIELD_WIDTH =  55;
	
	protected PosPrintingDevice mBillOutDevice = null;
	private int mColumnCount=40;
	private int mLeftMargin=0;
	
	protected  ArrayList<BeanOrderHeader> voidOrderList;
	protected  ArrayList<BeanOrderHeader> voidItemList;
	protected ArrayList<BeanOrderDetail> orderDetailList;
	protected	ArrayList<BeanOrderPaymentReport> orderPaymentReportList;
    protected ArrayList<BeanOrderPayment> orderPaymentSummary;
	private PosOrderHdrProvider moderHdrProvider;
	private PosOrderDtlProvider mOrderDtlProvider;
	private PosShiftReportProvider mShiftReportProvider;
	private PosOrderDiscountProvider mOrderDiscountProvider;
	protected BeanShiftCloseSummary mShiftSummary;
	private PosShiftSummaryProvider mShiftSummaryProvider;
	protected double totalDiscount=0;
	protected double totalGrossSale=0;
	private String orderIdsString="";
	protected BeanShopShift mShift;
	private PosOrderPaymentsProvider mOrderPaymentProvider;
	private PosOrderPaymentHdrProvider mOrderPaymentHdrProvider;
	private String mReportPath;
	protected double mTotalRoundAdjustMent;
	protected double mTotalRefundRoundAdjustMent;
	protected double mTotalSalesRoundAdjustMent;
	protected ArrayList<BeanDiscountSummary> orderItemDiscountSummary;
	protected int orderCount;
	protected String openingInvoiceNo;
	protected String closingInvoiceNo;
	protected int paymentCount;
	protected double totalRefund;
	protected ArrayList<BeanDiscountSummary> billDiscountSummary;
	private String mFileName;
	protected Font mFontReceipt;
	private String mCriteria;
	protected boolean summaryOnly;
	protected boolean printPaymentDtls;
	protected double mTotalbillTax;
	protected boolean midReport;
	protected boolean mUseAltLanguge;
	protected String decimalFormat;
	protected String timeFrom;
	protected String timeTo;
	protected String posDate;
	protected HashMap<Integer, BeanReceiptTaxSummary> itemTaxSummaryList;
	protected boolean isDayEndReport=false;
	protected double advanceCash;
	protected double advanceCard;
	protected double advanceEBS;
	private double totalAdvanceOfClosedSO;
	private double totalExtraCharges;
	private double netSale;
	private ArrayList<IPosBrowsableItem> mSelectedServices;
	
	/**
	 * Constructor to initialize font from  print property file 
	 * 			check alternative language option of the receipt printer
	 */
	public PosShiftReport() {
		
		mFontReceipt = new Font("Ariel", Font.PLAIN, 8);
		try {
			mUseAltLanguge = PosDeviceManager.getInstance().canUseAltLanguage(getPrinterType());		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		setDecimalFormat();
	}
	
	/**
	 * 
	 */
	private void setDecimalFormat() {
		decimalFormat="#,###.00";
	}

	/**
	 * @param mBillOutDevice2
	 * @throws Exception 
	 * Print header details of the report here.
	 */
	protected void printHeaders(PosPrintingDevice billBufferWriter) throws Exception {
		 
		final String hdrLine0 =(midReport? "Posella Mid Day Report":"Posella Day End Report");
		final String hdrLine1 = PosEnvSettings.getInstance().getShop().getName();
		final String hdrLine2 = PosEnvSettings.getInstance().getStation().getName();
		final String hdrLine3 ="";
		final String hdrLine4 =PosDateUtil.formatLocal(PosDateUtil.getDate()) + "(" + PosDateUtil.getDayOfWeek() + ") " +
				PosDateUtil.format(PosDateUtil.SHORT_TIME_FORMAT_12, PosDateUtil.getDateTime());  
		
		setFont(mFontReceipt);
		setFontStyle(Font.BOLD);
		setFontSize(12.0f);
		printText(TextAlign.CENTER, PosEnvSettings.getInstance().getShop().getName());

		
		setFontSize(9.0f);
		printText(TextAlign.CENTER, hdrLine0);

		setFont(mFontReceipt);
		printText(TextAlign.CENTER, hdrLine1);
		printText(TextAlign.CENTER, hdrLine2);
		printText(TextAlign.CENTER, hdrLine3);
		advanceLine(3);
		printText(TextAlign.CENTER, hdrLine4);
		
		advanceLine(6);
		
		if(mShift!=null)
			printText(TextAlign.LEFT, "SHIFT: "+ mShift.getDisplayText());
		
		if(mShiftSummary!=null){
			printText(TextAlign.LEFT, "POS DATE: "+ PosDateUtil.formatLocal(mShiftSummary.getOpeningDate()));
			printText(TextAlign.LEFT, "TILL : ["+ PosDateUtil.format(PosDateUtil.SHORT_TIME_FORMAT_12,mShiftSummary.getOpeningTime()) + "]-[" + 
					PosDateUtil.format(PosDateUtil.SHORT_TIME_FORMAT_12,mShiftSummary.getClosingTime()) + "]");
		} else{
			printText(TextAlign.LEFT, "POS DATE: "+  PosDateUtil.formatLocal(posDate) );
			printText(TextAlign.LEFT, "TILL : ["+  
					PosDateUtil.format(PosDateUtil.SHORT_TIME_FORMAT_12,timeFrom)  + "]-["  + 
					PosDateUtil.format(PosDateUtil.SHORT_TIME_FORMAT_12,timeTo)+ "]");
		}
		
		final int invoice_no_title_length=60;
		final int invoice_no_data_length=	 getPosReportPageFormat().getImageableWidthInPixcel()-invoice_no_title_length;
		
		if(orderCount>0){
			printTextBlock(0, invoice_no_title_length, "INVOICE NO : ", TextAlign.LEFT, false);
			printTextBlock(invoice_no_title_length, invoice_no_data_length, openingInvoiceNo, TextAlign.LEFT, true);
			printTextBlock(invoice_no_title_length, invoice_no_data_length, closingInvoiceNo, TextAlign.LEFT, true);
//			printText(TextAlign.LEFT, "Invoice No : "+ openingInvoiceNo + " - " + closingInvoiceNo);
		}
 
	}
	
 
	final protected String getDayOfWeek() {
		String[] day_name = { "Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat" };
		final int day = Calendar.getInstance().get(Calendar.DAY_OF_WEEK) - 1;
		return day_name[day];
	}

	
	/**
	 * @param amount
	 * @return
	 * Format the value to currency format
	 */
	protected String prepareAmountString(double amount){
		final String curSymbol=PosEnvSettings.getInstance().getCurrencySymbol();
		final String valueSymbol=((amount>=0)?"":"-");
		amount=Math.abs(amount);
		String amountString=valueSymbol+curSymbol+PosCurrencyUtil.format(amount);
		return amountString;
	}
	
 
	/**
	 * @param title
	 * @param quantity
	 * @param amount
	 * @return 
	 * Print title, quantity and amount in 3 columns
	 */
	protected void printLineQuantityAmo(String title,String quantity, double amount){
		int left =0;//5;
		int gap = 1;
		printTextBlock(left, DESC_FIELD_WIDTH,title, TextAlign.LEFT, false);
		left += DESC_FIELD_WIDTH + gap;
//		printTextBlock(left, QTY_FIELD_WIDTH,PosNumberUtil.format(quantity), TextAlign.RIGHT, false);
		printTextBlock(left, QTY_FIELD_WIDTH, String.valueOf(quantity), TextAlign.RIGHT, false);
		left += QTY_FIELD_WIDTH + gap;
//		printTextBlock(left, RATE_FIELD_WIDTH,prepareAmountString(amount), TextAlign.RIGHT, true);
		printText( TextAlign.RIGHT,PosCurrencyUtil.format(amount));
	}
	
	/**
	 * @param title
	 * @param mode
	 * @param amount
	 * @return 
	 * Print Invoice No, Payment Mode  and amount in 3 columns
	 */
	protected void printPaymentLine(String title,String mode, double amount){
		int left =0;//5;
		int gap = 1;
		printTextBlock(left, INVNO_FIELD_WIDTH,title, TextAlign.LEFT, false);
		left += INVNO_FIELD_WIDTH + gap;
		printTextBlock(left, PAYMODE_FIELD_WIDTH,mode, TextAlign.LEFT, false);
		left += PAYMODE_FIELD_WIDTH + gap;
		printTextBlock(left, RATE_FIELD_WIDTH,prepareAmountString(amount), TextAlign.RIGHT, true);
	}
	
	/**
	 * Print title/caption in left side and amount in right end  
	 * @param title
	 * @param amount
	 * @param printZeroValue ,if true,print the value whether it is zero or not. if false, not print zero value  
	 * @return 
	 */
	protected void printLine(String title, double amount,boolean printZeroValue){
		
		if(printZeroValue || amount!=0 ){
			printText(TextAlign.LEFT,title,false);
			printText(TextAlign.RIGHT,PosCurrencyUtil.format(amount));
		}
		
	}
	/**
	 * Print title/caption in left side and amount with prefix (-)  in right end  
	 * @param title
	 * @param amount
	 * @param printZeroValue ,if true,print the value whether it is zero or not. if false, not print zero value  
	 * @return 
	 */
	
  protected void printNegativeLine(String title, double amount,boolean printZeroValue){
		
		if(printZeroValue || amount!=0 ){
			printText(TextAlign.LEFT,title,false);
			printText(TextAlign.RIGHT,"(-)" + PosCurrencyUtil.format(amount));
		}
		
	}
	protected void printLine(String title, double amount){
		
		printLine(title,amount,false);
	
	}


	protected void printAmountTextLine(String title, double value,boolean printZeroValue){
		
		if(printZeroValue ||  value!=0 ){
			
			printText(TextAlign.LEFT,title,false);
			printText(TextAlign.RIGHT,prepareAmountString(value));
		}
	}

	protected void printAmountTextLine(String title, double value){
		
		printAmountTextLine(title, value,false);
	}
	
	protected void printLine(String title, String value ){
		
			
			printText(TextAlign.LEFT,title,false);
			printText(TextAlign.RIGHT,value);
	}

		/**
	 * @param mBillOutDevice2
	 * @throws Exception 
	 * The Details of the bill print here.
	 */
	protected void printDetails(PosPrintingDevice billBufferWriter, boolean summaryOnly) throws Exception {

		String subClassName = "";
		String itemName = "";
		double itemQuantity = 0;
		double itemTotal = 0;
		double subTotal = 0;
		int index = 0;
		totalGrossSale =0;
		if (!summaryOnly) {
			printDetailsHeader();
		}

		BeanSaleItem saleItem = orderDetailList.get(index).getSaleItem();
		BeanUOM uom;
		do {
			itemName = saleItem.getName();
			itemQuantity += saleItem.getQuantity();
			itemTotal += PosSaleItemUtil.getGrandTotal(saleItem);
			uom=saleItem.getUom();
			if (!subClassName.equals(saleItem.getSubClass().getName())) {
				if (!subClassName.equals("") && !summaryOnly) {

					advanceLine(1);
					printText(TextAlign.RIGHT, "------------------");
					printLine("Sub Total(" + subClassName + ")", subTotal);
					advanceLine(2);
				}
				subClassName = saleItem.getSubClass().getName();
				if (!summaryOnly) {
					advanceLine(4);
					printText(TextAlign.LEFT, subClassName);
				}
				totalGrossSale += subTotal;
				subTotal = 0;

			}
			subTotal += PosSaleItemUtil.getGrandTotal(saleItem);
			index++;
			if (index < orderDetailList.size())
				saleItem = orderDetailList.get(index).getSaleItem();
			else {
				saleItem = null;
			}
			if (saleItem == null
					|| (!itemName.equalsIgnoreCase(saleItem.getName()) && !itemName
							.equals(""))) {
				if (!summaryOnly) {
					printLineQuantityAmo(itemName, PosUomUtil.format(itemQuantity, uom), itemTotal);
				}
				if ((saleItem == null) && !subClassName.equals("")) {
					if (!summaryOnly) {
						advanceLine(1);
						printText(TextAlign.RIGHT, "------------------");
						printLine("Sub Total(" + subClassName + ")", subTotal);
					}
					totalGrossSale += subTotal;
					subTotal = 0;
				}
				itemName = "";
				itemTotal = 0;
				itemQuantity = 0;
			}
		} while (saleItem != null);

		if(totalExtraCharges>0){
			advanceLine(2);
			totalGrossSale+=totalExtraCharges;
			printLine("Total Additional Charges", totalExtraCharges);
		}
		if(!midReport){
//				printLine("TOTAL BILL TAX", mTotalbillTax);
			advanceLine(3);
			printLine("TOTAL Number Of Bills ", String.valueOf(orderCount));
		
		}
		advanceLine(3);
	}
	/**
	 * 
	 */
	protected void printPaymentdetails() {
		
		final int gap = 1;
		int left = 3;
		
		String payMode,invoiceNo;
		advanceLine(6);
		printText(TextAlign.LEFT, "PAYMENT DETAILS");
 
		printTextBlock(left, INVNO_FIELD_WIDTH, "Inv./Ref. Number", TextAlign.CENTER,
				false);
		/** The Quantity field **/
		left += INVNO_FIELD_WIDTH + gap;
		printTextBlock(left, PAYMODE_FIELD_WIDTH, "Mode", TextAlign.LEFT, false);
		/** The Rate field **/
		left += PAYMODE_FIELD_WIDTH + gap;
		printTextBlock(left, RATE_FIELD_WIDTH, "Amount", TextAlign.CENTER, true);
		for(BeanOrderPaymentReport payment :orderPaymentReportList){
			//String orderId =PosOrderUtil.getShortOrderIDFromOrderID(payment.getOrderId());
			if(payment.isRepayment()){
				printPaymentLine(payment.getInvoiceNo(),"Re-"+payment.getPaymentMode().getDisplayText(),payment.getPaidAmount());
			}else{
				payMode=payment.isAdvance()?"Adv. (" + payment.getPaymentMode().getDisplayText() +")": payment.getPaymentMode().getDisplayText();
				if (!payment.isAdvance() || (payment.getStatus()==PosOrderStatus.Closed || payment.getStatus() == PosOrderStatus.Refunded))
					invoiceNo=payment.getInvoiceNo();
				else
					invoiceNo= PosEnvSettings.getInstance().getUISetting().useOrderQueueNo()?
							PosOrderUtil.getFormatedOrderQueueNo(PosNumberUtil.parseIntegerSafely(payment.getQueueNo()),null,null):
							PosOrderUtil.getShortOrderIDFromOrderID(payment.getOrderId());
					
				printPaymentLine(invoiceNo,payMode,payment.getPaidAmount());
				
			}
		}
	
	}
	

	/**
	 * 
	 */
	protected void printPaymentSummary() {
		
		printText(TextAlign.LEFT, "PAYMENT SUMMARY");
		printSingleLine();
		for(BeanOrderPayment payment :orderPaymentSummary){
			if(payment.getPaymentMode().equals(PaymentMode.CouponBalance))
				printLine(PaymentMode.Coupon.getDisplayText() + " Balance ",payment.getPaidAmount());
			else
				printLine(payment.getPaymentMode().getDisplayText(),payment.getPaidAmount());
		}
	
		if(advanceCash!=0){
			printLine(PaymentMode.Cash.getDisplayText() +  " (Advance):",advanceCash);
		}
		if(advanceCard!=0){
			printLine(PaymentMode.Card.getDisplayText() + " (Advance):",advanceCard);
		}
		if(advanceEBS!=0){
			printLine(PaymentMode.Online.getDisplayText() + " (Advance):",advanceEBS);
		}
		if(totalRefund!=0){
			printLine("Refund Amount:",totalRefund);
		}
		advanceLine(2);
	}

	/**
		 * 
		 */
	private void printDetailsHeader() {
		
		final int gap = 1;
		int left = 3;
		printTextBlock(left, DESC_FIELD_WIDTH, "Description", TextAlign.CENTER,
				false);
		/** The Quantity field **/
		left += DESC_FIELD_WIDTH + gap;
		printTextBlock(left, QTY_FIELD_WIDTH, "Qty", TextAlign.CENTER, false);
		/** The Rate field **/
		left += QTY_FIELD_WIDTH + gap;
		printTextBlock(left, RATE_FIELD_WIDTH, "Total", TextAlign.CENTER, true);
		printSingleLine();
	}

	/**
	 * @param mBillOutDevice2
	 * @throws Exception
	 *             Sales summary.
	 */
	protected void printSalesSummary(PosPrintingDevice billBufferWriter) throws Exception {
		
		advanceLine(6);
		printText(TextAlign.LEFT, "SALES SUMMARY");
		printSingleLine();
		totalGrossSale=PosCurrencyUtil.roundTo(totalGrossSale);
		totalDiscount=PosCurrencyUtil.roundTo(totalDiscount);
		mTotalbillTax=PosCurrencyUtil.roundTo(mTotalbillTax);
		
		printLine("Total  Sale", totalGrossSale,true);
		printNegativeLine("Bill Discounts",  totalDiscount,true);
		
		final double totalRefund=PosCurrencyUtil.roundTo(mShiftSummary.getTotalRefund())-mTotalRefundRoundAdjustMent;
		printNegativeLine("Refund",totalRefund ,true);
		advanceLine(3);
		
		if(mTotalSalesRoundAdjustMent!=0)
			printLine("Rounding Adj.(Sales)", mTotalSalesRoundAdjustMent);
		if(mTotalRefundRoundAdjustMent!=0)
			printLine("Rounding Adj.(Refund)", -1* mTotalRefundRoundAdjustMent);
		
		 netSale=totalGrossSale -
				totalDiscount-totalRefund  + mTotalRoundAdjustMent  ;
		printText(TextAlign.RIGHT, "------------------");
		printAmountTextLine("Net Sale", netSale,true);
		printAmountTextLine("Total Tax", mTotalbillTax,true);
		
		advanceLine(3);
		
	}
	
	/**
	 * @throws Exception 
	 * 
	 */
	protected void printItemDiscountsSummary(PosPrintingDevice billBufferWriter) throws Exception {

		advanceLine(6);
		printText(TextAlign.LEFT, "ITEM DISCOUNTS APPLIED");
		printSingleLine();
		final int leftIndent=5;
		
		if(orderItemDiscountSummary!=null&&orderItemDiscountSummary.size()>0){
			for(int i=0;i<orderItemDiscountSummary.size();i++){
				int left =leftIndent;
				int gap = 1;
//				printLine(orderItemDiscountSummary.get(i).getName(), orderItemDiscountSummary.get(i).getQuantity(), orderItemDiscountSummary.get(i).getAmount());
				printTextBlock(left, DESC_FIELD_WIDTH-leftIndent,orderItemDiscountSummary.get(i).getName(), TextAlign.LEFT, false);
				left += DESC_FIELD_WIDTH -leftIndent + gap;
				printTextBlock(left, QTY_FIELD_WIDTH,PosUomUtil.format(orderItemDiscountSummary.get(i).getQuantity(),PosUOMProvider.getInstance().getMaxDecUom()), TextAlign.RIGHT, false);
				left += QTY_FIELD_WIDTH + gap;
				printTextBlock(left, RATE_FIELD_WIDTH,PosCurrencyUtil.format(orderItemDiscountSummary.get(i).getAmount()), TextAlign.RIGHT, true);
			}
		}else{
			printText(TextAlign.CENTER, "None...");
		}
		
	}
 
	/**
	 * @throws Exception 
	 * 
	 */
	protected void printExpense(PosPrintingDevice billBufferWriter,String orderFrom,String orderTo) throws Exception {

		 
		advanceLine(2);
		
		PosCashOutProvider cashOutProvider=new PosCashOutProvider();
		double totalCashOut= cashOutProvider.getTotalCashOut(posDate, mShift.getId(), orderFrom, orderFrom, orderTo, orderTo);
		
		if (totalCashOut!=0){
			 
		
//			printText(TextAlign.LEFT,"Expense ",false);
//			printText(TextAlign.RIGHT,prepareAmountString(totalCashOut));
			printAmountTextLine("Expense",  totalCashOut );
			
		}
		advanceLine(3);
		
	}
	
	/**
	 * @param mBillOutDevice2
	 * @throws Exception 
	 */
	protected void printBillDiscountSummary(PosPrintingDevice billBufferWriter) throws Exception {
		advanceLine(6);
		printText(TextAlign.LEFT, "BILL DISCOUNTS APPLIED ");
		printSingleLine();
		final int leftIndent=5;
		if(billDiscountSummary!=null&&billDiscountSummary.size()>0){
			for(int i=0;i<billDiscountSummary.size();i++){
				int left =leftIndent;
				int gap = 1;
				printTextBlock(left, DESC_FIELD_WIDTH-leftIndent,billDiscountSummary.get(i).getName(), TextAlign.LEFT, false);
				left += DESC_FIELD_WIDTH -leftIndent+ gap;
				printTextBlock(left, QTY_FIELD_WIDTH,String.valueOf(billDiscountSummary.get(i).getQuantity()), TextAlign.RIGHT, false);
				left += QTY_FIELD_WIDTH + gap;
				printTextBlock(left, RATE_FIELD_WIDTH,PosCurrencyUtil.format(billDiscountSummary.get(i).getAmount()), TextAlign.RIGHT, true);
			}
		}else{
			printText(TextAlign.CENTER, "None...");
		}
	}
	
	
	/**
	 * @param mBillOutDevice2
	 * @throws Exception 
	 */
	protected void printTaxSummary(PosPrintingDevice billBufferWriter) throws Exception {
	
		int count=1;
		
		double tax1Total=0,tax2Total=0,tax3Total=0,scTotal=0;
		
		boolean isTax2Applicable=( PosEnvSettings.getInstance().getTaxParam().getTax2Name()!=null && 
				  !PosEnvSettings.getInstance().getTaxParam().getTax2Name().trim().equals(""));
		

		boolean isTax3Applicable=( PosEnvSettings.getInstance().getTaxParam().getTax3Name()!=null && 
				  !PosEnvSettings.getInstance().getTaxParam().getTax3Name().trim().equals(""));
		

		boolean isServiceChargeApplicable=( PosEnvSettings.getInstance().getTaxParam().getServiceTaxName()!=null && 
				  !PosEnvSettings.getInstance().getTaxParam().getServiceTaxName().trim().equals(""));
		
		count +=(isTax2Applicable?1:0);
		count +=(isTax3Applicable?1:0);
		count +=(isServiceChargeApplicable?1:0);
		
	 	
				
		int taxNameFieldSize =getPosReportPageFormat().getImageableWidthInPixcel()-(
				TAX_FIELD_WIDTH+1)*count;
		advanceLine(6);
		printText(TextAlign.LEFT, "TAX SUMMARY");
		printSingleLine();
		
		
		int left; 
		
		left=0;
		printTextBlock(left, taxNameFieldSize ," ", TextAlign.LEFT, false);
		left=left + taxNameFieldSize+1 ;
		printTextBlock(left,TAX_FIELD_WIDTH ,PosEnvSettings.getInstance().getTaxParam().getTax1Name() , TextAlign.RIGHT, false);
		left=left + TAX_FIELD_WIDTH +1;		 
		
		if(isTax2Applicable){
			printTextBlock(left,TAX_FIELD_WIDTH ,PosEnvSettings.getInstance().getTaxParam().getTax2Name() ,
					TextAlign.RIGHT, !(isTax3Applicable||isServiceChargeApplicable));
			left=left + TAX_FIELD_WIDTH +1;	
		}
		if(isTax3Applicable){
			printTextBlock(left,TAX_FIELD_WIDTH ,PosEnvSettings.getInstance().getTaxParam().getTax3Name() , TextAlign.RIGHT,
					 !isServiceChargeApplicable);
			left=left + TAX_FIELD_WIDTH +1;	
		}	 
				
		if(isServiceChargeApplicable){
			printTextBlock(left,TAX_FIELD_WIDTH ,PosEnvSettings.getInstance().getTaxParam().getServiceTaxName()  ,
					TextAlign.RIGHT, true);
		}
		for(BeanReceiptTaxSummary taxSummary: itemTaxSummaryList.values()){

			if(PosNumberUtil.roundTo(taxSummary.getTaxAmount())==0)
				continue;
			
			left=0;
			printTextBlock(left, taxNameFieldSize ,taxSummary.getTaxName(), TextAlign.LEFT, false);
			left=left + taxNameFieldSize+1 ;
			printTextBlock(left,TAX_FIELD_WIDTH ,PosCurrencyUtil.format(taxSummary.getTax1Amount()) , TextAlign.RIGHT, false);
			left=left + TAX_FIELD_WIDTH +1;		 
			
			if(isTax2Applicable){
				printTextBlock(left,TAX_FIELD_WIDTH ,PosCurrencyUtil.format(taxSummary.getTax2Amount()) ,
						TextAlign.RIGHT,  !(isTax3Applicable||isServiceChargeApplicable));
				left=left + TAX_FIELD_WIDTH +1;	
			}
			if(isTax3Applicable){
				printTextBlock(left,TAX_FIELD_WIDTH ,PosCurrencyUtil.format(taxSummary.getTax3Amount()) , 
						TextAlign.RIGHT,  !isServiceChargeApplicable);
				left=left + TAX_FIELD_WIDTH +1;	
			}	 
					
			if(isServiceChargeApplicable){
				printTextBlock(left,TAX_FIELD_WIDTH ,PosCurrencyUtil.format(taxSummary.getTaxSCAmount()) ,
						TextAlign.RIGHT, true);
			}
			tax1Total+=taxSummary.getTax1Amount();
			tax2Total+=taxSummary.getTax2Amount();
			tax3Total+=taxSummary.getTax3Amount();
			scTotal+=taxSummary.getTaxSCAmount();
		} 
		
		String separator="-------------";
		String lineSeparator=separator;
		if(isTax2Applicable)
			lineSeparator =lineSeparator + separator;
		if(isTax3Applicable)
			lineSeparator =lineSeparator + separator;
		if(isServiceChargeApplicable)
			lineSeparator =lineSeparator + separator;
		
		printText(TextAlign.RIGHT, lineSeparator);
		left=0;
		printTextBlock(left, taxNameFieldSize ,"Total :", TextAlign.LEFT, false);
		left=left + taxNameFieldSize+1;
		printTextBlock(left,TAX_FIELD_WIDTH ,PosCurrencyUtil.format(tax1Total) , TextAlign.RIGHT, false);
		left=left + TAX_FIELD_WIDTH +1;		 
		
		if(isTax2Applicable){
			printTextBlock(left,TAX_FIELD_WIDTH ,PosCurrencyUtil.format(tax2Total) ,
					TextAlign.RIGHT,  !(isTax3Applicable||isServiceChargeApplicable));
			left=left + TAX_FIELD_WIDTH +1;	
		}
		if(isTax3Applicable){
			printTextBlock(left,TAX_FIELD_WIDTH ,PosCurrencyUtil.format(tax3Total) , 
					TextAlign.RIGHT,  !isServiceChargeApplicable);
			left=left + TAX_FIELD_WIDTH +1;	
		}	 
				
		if(isServiceChargeApplicable){
			printTextBlock(left,TAX_FIELD_WIDTH ,PosCurrencyUtil.format(scTotal) ,
					TextAlign.RIGHT, true);
		}
		
		printText(TextAlign.RIGHT, "------------------");
		printAmountTextLine("Total Tax ", tax1Total + tax2Total +tax3Total +scTotal  ,true );
	}
	
	/**
	 * @param mBillOutDevice2
	 * @throws Exception 
	 * Pyment summary here.
	 */
	protected void printPaymentSummary(PosPrintingDevice billBufferWriter) throws Exception {
		
		advanceLine(6);
		printText(TextAlign.LEFT, "PAYMENT/TRANSACTION SUMMARY");
		printSingleLine();
//		printLine("Total Cash Tendered", mShiftSummary.getCashReceipts());
//		printLine("Total Change Given", mShiftSummary.getCashReturned());
		printLine(PaymentMode.Cash.getDisplayText() + " Receipts", mShiftSummary.getCashReceipts()-mShiftSummary.getCashReturned());
		printLine(PaymentMode.Card.getDisplayText() + " Receipts", mShiftSummary.getCardReceipts());
	
		if (mShiftSummary.getOnlineReceipts()>0)
			printLine(PaymentMode.Online.getDisplayText() + " Receipts", mShiftSummary.getOnlineReceipts());
		
		
		if (mShiftSummary.getCashOut()>0)
			printLine("Cash Out", mShiftSummary.getCashOut());
		
		if( mShiftSummary.getVoucherReceipts()>0)
			printLine(PaymentMode.Coupon.getDisplayText() + " Receipts", mShiftSummary.getVoucherReceipts());
		
		if(mShiftSummary.getVoucherBalance()>0)
			printLine(PaymentMode.Coupon.getDisplayText() + " Balance", mShiftSummary.getVoucherBalance());
		
		if(mShiftSummary.getAccountsReceivable()>0)
			printLine(PaymentMode.Company.getDisplayText() , mShiftSummary.getAccountsReceivable());
		
		if( mShiftSummary.getTotalRefund()>0)
			printNegativeLine("Total Refunds", mShiftSummary.getTotalRefund(),false);
		
		if(totalAdvanceOfClosedSO>0){
			printText(TextAlign.LEFT, "Previous Advance ",false);
			printText(TextAlign.RIGHT,PosCurrencyUtil.format(totalAdvanceOfClosedSO));
		}
		printText(TextAlign.RIGHT, "------------------");
		printAmountTextLine("Total Receipts ", mShiftSummary.getNetSale(),true );
		
		advanceLine(2);
		if(advanceCash!=0)
			printLine(PaymentMode.Cash.getDisplayText() + " (Advance):",advanceCash);
		if(advanceCard!=0)
			printLine(PaymentMode.Card.getDisplayText() + " (Advance):",advanceCard);
		if(advanceEBS!=0)
			printLine(PaymentMode.Online.getDisplayText() + " (Advance):",advanceEBS);
		
 
	}
	
	/**
	 * @param billBufferWriter
	 * @return
	 * @throws Exception
	 * Print cash summary here. 
	 */
	protected void printCashSummary(PosPrintingDevice billBufferWriter) throws Exception{
		
		advanceLine(6);
		printText(TextAlign.LEFT, "CASH SUMMARY");
		printSingleLine();
		printLine("Opening Cash", mShiftSummary.getOpeningFloat(),true);
		printLine("Net Cash Received", mShiftSummary.getNetCash(),true);
		printLine("Cash Out", mShiftSummary.getCashOut());
		printLine("Voucher Bal. Returned", mShiftSummary.getVoucherBalanceReturned());
		printNegativeLine("Cash Refunds", mShiftSummary.getCashRefund(),false);
		printLine("Expense ", mShiftSummary.getExpense());
		printLine("Actual Cash", mShiftSummary.getActualCash(),true);
		printLine("Cash Variance", mShiftSummary.getVariance(),true);
		advanceLine(3);
		printLine("Bank Deposit Amount", mShiftSummary.getCashDeposit());
		printText(TextAlign.RIGHT, "------------------");
		printAmountTextLine("Balance Cash", mShiftSummary.getCashRemaining(),true);
		advanceLine(3);
	}
	
	final protected int getColumnCount() {
		return mColumnCount;
	}
	
	/**
	 * @return the LeftMargin
	 */
	public int getLeftMargin() {
		return mLeftMargin;
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.pos.reports.PosPrintableReportBase#printReport()
	 */
	@Override
	protected int printReport(int pageIndex) throws Exception {
			setDecimalFormat();
			
			if (orderCount==0 && !midReport){
				
				printHeaders(mBillOutDevice);
				printCashSummary(mBillOutDevice);
					
			}else {
				printHeaders(mBillOutDevice);
				
				if(!printPaymentDtls){
					
					if (isDayEndReport)
						summaryOnly= (PosEnvSettings.getInstance().getPrintSettings().getDayEndReportSettings().getDayEndReportType()==1);
						
					if (orderCount>0 )
						printDetails(mBillOutDevice,summaryOnly);

					
					
					if(mShiftSummary!=null&&!midReport){
						printSalesSummary(mBillOutDevice);
						printItemDiscountsSummary(mBillOutDevice);
						printBillDiscountSummary(mBillOutDevice);
						printTaxSummary(mBillOutDevice);
						printPaymentSummary(mBillOutDevice);
						printCashSummary(mBillOutDevice);
						
					}else{
//						printPaymentSummary();
						printMidSalesSummary();
					}
					
					if(PosEnvSettings.getInstance().getPrintSettings().getShiftReportSettings().isPrintVoidItemsInPaymentSummary()){
						printVoidItemdetails(true);
						printVoidItemdetails(false);
					}
					
					
					advanceLine(2);
					printLine("Report Finalised by ", "................");
				}else{
					printPaymentdetails();
				}
			}
			printText(TextAlign.CENTER, "********* End of Report *********");
			
			return (pageIndex>0?NO_SUCH_PAGE:PAGE_EXISTS);
	}

	/**
	 * @throws Exception 
	 * 
	 */
	protected void printMidSalesSummary() throws Exception {

		double totalAmount=0.0;
		Map<PaymentMode, Double> payments=new HashMap<BeanPayModeBase.PaymentMode, Double>();
		printText(TextAlign.LEFT, "TRANSACTION SUMMARY");
		printSingleLine();
		
		for(BeanOrderPayment payment :orderPaymentSummary){
			payments.put(payment.getPaymentMode(), payment.getPaidAmount());
		}
		
		if(payments!=null){
			
			if(payments.containsKey(PaymentMode.Cash)){
				double CashReturned =0;
				totalAmount+=payments.get(PaymentMode.Cash);
				if(payments.containsKey(PaymentMode.Balance)){
					CashReturned = payments.get(PaymentMode.Balance);
				}
				
				printLine(PaymentMode.Cash.getDisplayText(), payments.get(PaymentMode.Cash)-CashReturned);
				
			}
			
			if(payments.containsKey(PaymentMode.Card)){
				
				totalAmount+=payments.get(PaymentMode.Card);
				double CashOut =0;
				if(payments.containsKey(PaymentMode.CashOut)){
					CashOut = payments.get(PaymentMode.CashOut);
				}
				
				printLine(PaymentMode.Card.getDisplayText(),payments.get(PaymentMode.Card)-CashOut);
			}
			
			if(payments.containsKey(PaymentMode.Online)){
				
				totalAmount+=payments.get(PaymentMode.Online);
				printLine(PaymentMode.Online.getDisplayText(),payments.get(PaymentMode.Online));
			}
		
			if(payments.containsKey(PaymentMode.Company)){
				totalAmount+=payments.get(PaymentMode.Company);
				printLine(PaymentMode.Company.getDisplayText(),payments.get(PaymentMode.Company));
			}
			if(payments.containsKey(PaymentMode.Coupon)){
				double VoucherBalance =0;
				totalAmount+=payments.get(PaymentMode.Coupon);
				if(payments.containsKey(PaymentMode.CouponBalance)){
					VoucherBalance = payments.get(PaymentMode.CouponBalance);
				}
				printLine(PaymentMode.Coupon.getDisplayText(),payments.get(PaymentMode.Coupon)-VoucherBalance);
			}
			if(payments.containsKey(PaymentMode.Balance)){
				totalAmount -= payments.get(PaymentMode.Balance);
			}
			
			if(payments.containsKey(PaymentMode.CouponBalance)){
				totalAmount -= payments.get(PaymentMode.CouponBalance);
			}
			if(payments.containsKey(PaymentMode.CashOut)){
				totalAmount-= payments.get(PaymentMode.CashOut);
			}
			
		}
		if(totalRefund>0){
			printNegativeLine("Refund",totalRefund,false);
			totalAmount-=totalRefund;
		}
		if(totalAdvanceOfClosedSO>0){
			printAmountTextLine("Previous Advance  ",totalAdvanceOfClosedSO);
			totalAmount+=totalAdvanceOfClosedSO;
		}
		printText(TextAlign.RIGHT, "------------------");
		printLine("Net Sales including tax", (totalAmount>0?totalAmount:0));
		
		advanceLine(1);
		printExpense(mBillOutDevice, timeFrom, timeTo);
		advanceLine(2);
		printLine("Tax",mTotalbillTax);
		 
		if(totalDiscount>0){
			printLine( "Bill Discounts ",totalDiscount);
		}
		
//		if(mTotalRoundAdjustMent!=0){
//			printLine("Rounding Adj. ",mTotalRoundAdjustMent);
//		} 
		 
		if(mTotalSalesRoundAdjustMent!=0)
			printLine("Rounding Adj.(Sales)", mTotalSalesRoundAdjustMent);
		if(mTotalRefundRoundAdjustMent!=0)
			printLine("Rounding Adj.(Refund)", mTotalRefundRoundAdjustMent);
		printLine("No of Bills", String.valueOf(orderCount));
 
		
		
		printTaxSummary(mBillOutDevice);
	}
	
	
	
	
	

	/**
	 * 
	 */
	protected void printVoidItemdetails(boolean voidOrdersonly) {
		
		final int inv_no_caption_width = 33;
		final int inv_no_data_width =DESC_FIELD_WIDTH- inv_no_caption_width ;
		final int void_by_caption_width =QTY_FIELD_WIDTH+ 10;
		final int void_by_data_width =RATE_FIELD_WIDTH-10;
		final int item_dtls_indentation=10;
		final int void_by_indentation=20;
		ArrayList<BeanOrderHeader> voidHdrList=voidOrdersonly?voidOrderList:voidItemList;
		
		 if(voidHdrList==null || voidHdrList.size()==0) 
			 return;
		 
		final int gap = 1;
		int left = 0;
		
		advanceLine(6);
		printText(TextAlign.LEFT, voidOrdersonly?"VOID ORDERS":"VOID ITEMS (PAID)");
		 
		for(BeanOrderHeader ordHdr:voidHdrList){
			
			advanceLine(2);
			left=3;
			if(ordHdr.getStatus()==PosOrderStatus.Closed || ordHdr.getStatus()==PosOrderStatus.Refunded){
				
				printTextBlock(left, inv_no_caption_width, "Inv No :", TextAlign.LEFT,
						false);
				printTextBlock(left +inv_no_caption_width,inv_no_data_width ,  ordHdr.getInvoiceNo(), TextAlign.LEFT,
						true);
			}else{
				
				printTextBlock(left, inv_no_caption_width, "Bill No :", TextAlign.LEFT,
						false);
				
			 	final String refNo=PosEnvSettings.getInstance().getUISetting().useOrderQueueNo() &&
			 					PosEnvSettings.getInstance().getOrderQueueResetPolicy()==PosQueueNoResetPolicy.NORESET ? ordHdr.getQueueNo():
					 PosOrderUtil.getShortOrderIDFromOrderID(ordHdr.getOrderId());
				printTextBlock(left +=inv_no_caption_width,inv_no_data_width ,refNo, TextAlign.LEFT,
						(ordHdr.getStatus()==PosOrderStatus.Void?false:true));
				
				if(ordHdr.getStatus()==PosOrderStatus.Void)	{
					
					printTextBlock(left+=inv_no_data_width , void_by_caption_width, "Void By: ", TextAlign.RIGHT,false);
					printTextBlock(left+=void_by_caption_width, void_by_data_width, ordHdr.getUser().getName(), TextAlign.LEFT,true);
					
				}
			}
			
			 
			for(BeanOrderDetail ordDtl:ordHdr.getOrderDetailItems()){
				
				left=item_dtls_indentation;
				
				printTextBlock(left, DESC_FIELD_WIDTH-item_dtls_indentation, ordDtl.getSaleItemName(), TextAlign.LEFT,false);
				left= DESC_FIELD_WIDTH + gap;
				printTextBlock(left, QTY_FIELD_WIDTH, PosUomUtil.format(ordDtl.getSaleItem().getQuantity(),  PosUOMProvider.getInstance().getMaxDecUom( )), TextAlign.RIGHT,false);
				left += QTY_FIELD_WIDTH + gap;
				printTextBlock(left, RATE_FIELD_WIDTH, PosCurrencyUtil.format(ordDtl.getSaleItem().getItemTotal()), TextAlign.RIGHT,true);
								
				if(ordHdr.getStatus()!=PosOrderStatus.Void)	{
					left=void_by_indentation;
					printTextBlock(left, DESC_FIELD_WIDTH,"Void By : " + ordDtl.getVoidBy().getName(), TextAlign.LEFT,true);
					advanceLine(1);
				}
//				printTextBlock(left+DESC_FIELD_WIDTH, QTY_FIELD_WIDTH+RATE_FIELD_WIDTH, PosDateUtil.formatShortDateTime( ordDtl.getVoidAt()), TextAlign.RIGHT,true);
				
			}
			
		}
		
	}
	
 
	
	public void initPrint()throws Exception{
		
		String where1;
	    totalDiscount=0;
		totalGrossSale=0;
		mTotalRoundAdjustMent=0;
		mTotalbillTax = 0;
		moderHdrProvider = new PosOrderHdrProvider();
		mShiftSummaryProvider = new PosShiftSummaryProvider();
		advanceCash=0;
		advanceCard=0;
		advanceEBS=0;
		totalAdvanceOfClosedSO=0;
		totalExtraCharges=0;
		String orderHdrCriteria ="";
		String previousAdvanceCriteria="";
		boolean isSOSelected=false;
		
		
		final String shiftCriteria= ((mShift!=null &&  mShift.getId()!=0)? " and shift_id="+ mShift.getId() : "");
		
		
		if(mSelectedServices==null || mSelectedServices.size()==0)
			isSOSelected=true;
		else{
			for(IPosBrowsableItem service:mSelectedServices){
				if((PosOrderServiceTypes)service==PosOrderServiceTypes.SALES_ORDER){
					isSOSelected=true;
					break;
				}
			}
		}
		
		orderHdrCriteria=mCriteria;
		
		previousAdvanceCriteria=" payment_date ='" + posDate + "' ";
		final String paymentTimeCriteria=" payment_time>='" + timeFrom + "' AND payment_time<='" + timeTo + "'" ;
		if(midReport){
			orderHdrCriteria = orderHdrCriteria + " AND closing_time>='" + timeFrom + "' AND closing_time<='" + timeTo + "'" ;
			
			previousAdvanceCriteria = previousAdvanceCriteria + " AND " + paymentTimeCriteria;
			
 		}

		orderHdrCriteria =orderHdrCriteria + shiftCriteria;
		
		final String voidItemsCriteria=orderHdrCriteria;
		
	
		
		orderHdrCriteria = orderHdrCriteria+" and status in "
				+ "("+PosOrderStatus.Closed.getCode()+","+PosOrderStatus.Refunded.getCode()+")";
//		previousAdvanceCriteria = previousAdvanceCriteria +" and status in "
//				+ "("+PosOrderStatus.Closed.getCode()+","+PosOrderStatus.Refunded.getCode()+")"; 
		
		previousAdvanceCriteria = previousAdvanceCriteria  + shiftCriteria;
		orderIdsString = moderHdrProvider.getOrderIds(orderHdrCriteria);
		openingInvoiceNo=moderHdrProvider.getOpeningInvoiceNo(orderHdrCriteria);
		closingInvoiceNo=moderHdrProvider.getClosingInvoiceNo(orderHdrCriteria);
		
		mShiftReportProvider=new PosShiftReportProvider();
		voidOrderList =mShiftReportProvider.getVoidItemList(voidItemsCriteria,true);
		voidItemList =mShiftReportProvider.getVoidItemList(voidItemsCriteria,false);
		
		String paymentCriteria= mCriteria.replace("closing_date", "payment_date" );
		
		if(!midReport){
			
			mShiftSummary = mShiftSummaryProvider.getDayEndShiftSummary(mCriteria.replace("closing_date", "opening_date") + shiftCriteria);
			
		} else{
			
			if (paymentTimeCriteria.trim().length()>0)
				paymentCriteria = paymentCriteria + " AND " + paymentTimeCriteria;
		}
		
		
		final String  	paymentHdrCriteria=paymentCriteria ;
		Integer shiftId=0;
		
		
		String orderCountCriteria=paymentCriteria + shiftCriteria; 
		if(mShift!=null &&  mShift.getId()!=0){
			paymentCriteria+=" and cashier_shift_id="+ mShift.getId();
			previousAdvanceCriteria = previousAdvanceCriteria + " and cashier_shift_id="+ mShift.getId();
			shiftId=mShift.getId();
		}
		
		mOrderPaymentProvider = new PosOrderPaymentsProvider();
		mOrderPaymentHdrProvider=new PosOrderPaymentHdrProvider();
		
		if (isSOSelected){
//			previousAdvanceCriteria=previousAdvanceCriteria + " and service_type =" + PosOrderServiceTypes.SALES_ORDER.getCode();
			totalAdvanceOfClosedSO=mOrderPaymentProvider.getAdvanceAmtOfClosedSaledsOrder(previousAdvanceCriteria);
			totalExtraCharges=moderHdrProvider.getTotalAdditionalCharges(orderHdrCriteria);
		}
		
		orderPaymentSummary = mOrderPaymentProvider.getPaymentSummaryForShiftReports(paymentCriteria);
		totalRefund = mOrderPaymentProvider.getGrandTotalRepayment(paymentCriteria);
//		netSale = mOrderPaymentProvider.getNetSale(paymentCriteria);
//		netSale-=totalRefund;
		advanceCash=mOrderPaymentProvider.getTotalAdvanceReceived(paymentCriteria,PaymentMode.Cash);
		advanceCard=mOrderPaymentProvider.getTotalAdvanceReceived(paymentCriteria,PaymentMode.Card);
		advanceEBS=mOrderPaymentProvider.getTotalAdvanceReceived(paymentCriteria,PaymentMode.Online);
		paymentCount=orderPaymentSummary.size();
		paymentCount+= totalRefund>0?1:0;
		
		if(printPaymentDtls){
			orderPaymentReportList = mOrderPaymentProvider.getOrderPaymentsForShiftReport(paymentCriteria);
		}
		orderCount = mOrderPaymentProvider.getOrderCount(orderCountCriteria);
		
		//paymentCriteria=paymentCriteria.replace("cashier_shift_id", "shift_id");
		 
		mOrderDiscountProvider = new PosOrderDiscountProvider();
		
		totalDiscount = mOrderDiscountProvider.getTotalDiscount(paymentHdrCriteria,shiftId); 
		billDiscountSummary = mOrderDiscountProvider.getDiscountSummary(paymentHdrCriteria,shiftId);
		
		mTotalSalesRoundAdjustMent = mOrderPaymentHdrProvider.getTotalRoundinAdjustments(paymentHdrCriteria,shiftId,false); 
		mTotalRefundRoundAdjustMent = mOrderPaymentHdrProvider.getTotalRoundinAdjustments(paymentHdrCriteria,shiftId,true);
		mTotalRoundAdjustMent=mTotalSalesRoundAdjustMent-mTotalRefundRoundAdjustMent;
		
		mOrderDtlProvider = new PosOrderDtlProvider();
		mTotalbillTax = mOrderDtlProvider.getTotalBillTax(paymentHdrCriteria,shiftId);
//		+mOrderPaymentHdrProvider.getTotalExtraChargeTax(orderHdrCriteria, shiftId); 
		
		
		if (orderIdsString.trim().length() > 0) {
			
			where1 = "order_id  in (" + orderIdsString + ")";
			
		
			
			orderDetailList = mOrderDtlProvider.getOrderDetailData(where1);
			itemTaxSummaryList=mOrderDtlProvider.getItemTaxSummaryList(paymentHdrCriteria,shiftId);
			orderItemDiscountSummary = mOrderDtlProvider.getDiscountSummary(where1);
			
		}
 
	}
	/* (non-Javadoc)
	 * @see com.indocosmo.pos.reports.PosPrintableReportBase#onInitPrint(java.awt.Graphics2D, java.awt.print.PageFormat)
	 */
	@Override
	protected void onInitialized(Graphics2D g2d, PageFormat pf) throws Exception {
		setFont(mFontReceipt);
		
		DESC_FIELD_WIDTH=getPosReportPageFormat().getImageableWidthInPixcel()-(
			 	QTY_FIELD_WIDTH+1+
				RATE_FIELD_WIDTH 
				);
	}

	/**
	 * @param mCashierShift the mCashierShift to set
	 */
	public void setShift(BeanShopShift mShift) {
		this.mShift = mShift;
	}

	/**
	 * @param mCriteria the mCriteria to set
	 */
	public void setCriteria(String mCriteria) {
		this.mCriteria = mCriteria;
	}

	/**
	 * @param summaryOnly the summaryOnly to set
	 */
	public void setSummaryOnly(boolean summaryOnly) {
		this.summaryOnly = summaryOnly;
	}

	/**
	 * @param printPaymentDtls the printPaymentDtls to set
	 */
	public void setPrintPaymentDtls(boolean printPaymentDtls) {
		this.printPaymentDtls = printPaymentDtls;
	}

	/**
	 * @param selected
	 */
	public void setSalesReportOnly(boolean salesRptOnly) {
		this.midReport = salesRptOnly;
	}

	/**
	 * @param orderFrom
	 * @param orderTo
	 */
	public void setTimeCriteria(String orderFrom, String orderTo) {
		this.timeFrom = orderFrom;
		this.timeTo = orderTo;
	}

	/**
	 * @param openingDate
	 */
	public void setPosDate(String openingDate) {
		this.posDate = openingDate;
	}
	
	/**
	 * @param isDayEndReport the isDayEndReport to set
	 */
	public void setIsDayEndReport(boolean isDayEndReport) {
		this.isDayEndReport = isDayEndReport;
	}
 
	/**
	 * @param where
	 * @param timeCriteria 
	 * @param orderTo 
	 * @param selected
	 * @param text
	 * @param salesReportOnly 
	 */
	public static  void doPrintDayEndReport( final Container parent, final PosShiftReport report) throws Exception {

		SwingWorker<Boolean, String> swt = new SwingWorker<Boolean, String>() {
			@Override
			protected Boolean doInBackground() throws Exception {
				
				try {
					
					final PosDevicePrinter printer=PosDeviceManager.getInstance().getReceiptPrinter();
					
					if(printer!=null){
						 
						report.initPrint();
						if(!report.midReport && report.mShiftSummary==null)
							PosFormUtil.showErrorMessageBox(parent, "No data to print");
						else if (report.midReport && report.orderCount==0 && report.paymentCount==0 && 
								report.advanceCard==0 && report.advanceCash==0
								&& report.advanceEBS==0 ){
							PosFormUtil.showErrorMessageBox(parent, "No Order Details.");
						}else
							printer.print(report);
						
					}else{
						PosFormUtil.showInformationMessageBox(parent, "Receipt Printer not configured.");
					}
				} catch (Exception e) {
					
					PosFormUtil.closeBusyWindow();
				 
						PosFormUtil.showErrorMessageBox(parent, e.getMessage());
				}
				return true;
			}
			@Override
			protected void done() {
				PosFormUtil.closeBusyWindow();
			}
		};
		swt.execute();
		PosFormUtil.showBusyWindow(parent,
				"Please wait ...");
	}
 

	/**
	 * @param mSelectePayMode the mSelectePayMode to set
	 */
	public void setSelectedServices(ArrayList<IPosBrowsableItem> mSelectedServices) {
		this.mSelectedServices = mSelectedServices;
	}
	

}
