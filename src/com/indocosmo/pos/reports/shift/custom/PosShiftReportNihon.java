/**
 * 
 */
package com.indocosmo.pos.reports.shift.custom;

import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import com.indocosmo.pos.common.PosEnvSettings;
import com.indocosmo.pos.common.enums.PosQueueNoResetPolicy;
import com.indocosmo.pos.common.utilities.PosCurrencyUtil;
import com.indocosmo.pos.common.utilities.PosDateUtil;
import com.indocosmo.pos.common.utilities.PosNumberUtil;
import com.indocosmo.pos.common.utilities.PosOrderUtil;
import com.indocosmo.pos.common.utilities.PosSaleItemUtil;
import com.indocosmo.pos.common.utilities.PosUomUtil;
import com.indocosmo.pos.data.beans.BeanOrderDetail;
import com.indocosmo.pos.data.beans.BeanOrderHeader;
import com.indocosmo.pos.data.beans.BeanOrderPayment;
import com.indocosmo.pos.data.beans.BeanOrderPaymentReport;
import com.indocosmo.pos.data.beans.BeanPayModeBase;
import com.indocosmo.pos.data.beans.BeanSaleItem;
import com.indocosmo.pos.data.beans.BeanTax;
import com.indocosmo.pos.data.beans.BeanUOM;
import com.indocosmo.pos.data.beans.BeanOrderHeader.PosOrderStatus;
import com.indocosmo.pos.data.beans.BeanPayModeBase.PaymentMode;
import com.indocosmo.pos.data.providers.shopdb.PosCashOutProvider;
import com.indocosmo.pos.data.providers.shopdb.PosTaxItemProvider;
import com.indocosmo.pos.data.providers.shopdb.PosUOMProvider;
import com.indocosmo.pos.reports.base.PosPrintableBase.TextAlign;
import com.indocosmo.pos.reports.shift.PosShiftReport;
import com.indocosmo.pos.terminal.devices.PosPrintingDevice;
import com.sun.org.apache.bcel.internal.generic.ISUB;

/**
 * @author deepak
 *
 */
public class PosShiftReportNihon extends PosShiftReport {

	/**
	 * 
	 */
	public PosShiftReportNihon() {
		super();
//		mFontReceipt = new Font("Monospace", Font.PLAIN, 10);

	}

	private void setDecimalFormat() {
		decimalFormat = "#,###";
	}

	protected void printHeaders(PosPrintingDevice billBufferWriter)
			throws Exception {

		try {
			BufferedImage bi = ImageIO
					.read(new File("logos/nirvanam_logo.png"));
			printImage(bi, 0, 0, 180, 56);
		} catch (IOException e) {
			// e.printStackTrace();
		}
		final String midDayRptHeading=mUseAltLanguge?"中間レポート":" MID DAY REPORT";
		final String dayEndRptHeading=mUseAltLanguge?"期末レポート":"DAY END REPORT";
		
		final String reportTitle = midReport?midDayRptHeading:dayEndRptHeading;
		
		setFont(mFontReceipt);
//		setFontStyle(Font.BOLD);
		setFontSize(12.0f);
		printText(TextAlign.CENTER, PosEnvSettings.getInstance().getShop().getName());

		
		setFontSize(9.0f);
		printText(TextAlign.CENTER, reportTitle);

		setFont(mFontReceipt);
		
		final String checkingTime = mUseAltLanguge ? "点検   " : "Inspection on " +  PosDateUtil.formatLocalDateTime(PosDateUtil.getDateTime());
		printText(TextAlign.LEFT, checkingTime);
//		printText(TextAlign.RIGHT, PosDateUtil.getDateTime());
		final String details = mUseAltLanguge ? "日経明細" : "Nikkei details";
		printText(TextAlign.LEFT, details);
//		printText(TextAlign.RIGHT, "X");

		advanceLine(2);
		if (mShift != null)
			printText(TextAlign.LEFT, (mUseAltLanguge?"区分: ":"SHIFT: ") + mShift.getDisplayText());
		if (mShiftSummary != null) {
			printText(TextAlign.LEFT,
				 (mUseAltLanguge?"POS 日付:":"POS DATE: ") + PosDateUtil.formatLocal(mShiftSummary.getOpeningDate()));
			
			printText(TextAlign.LEFT,
					(mUseAltLanguge?"開店準備期間 : ": "TILL OPENED: ") + PosDateUtil.formatLocalDateTime(mShiftSummary.getOpeningTime()));
			printText(TextAlign.LEFT,
					(mUseAltLanguge?"閉店準備期間 : ":"TILL CLOSED: ") + PosDateUtil.formatLocalDateTime(mShiftSummary.getClosingTime()));
		} else{
			printText(TextAlign.LEFT,
					(mUseAltLanguge?"POS 日付:":"POS DATE: ")  +  PosDateUtil.formatLocal(posDate));
			printText(TextAlign.LEFT,
					(mUseAltLanguge?"集計開始:":"Order From: ")  + PosDateUtil.formatLocalDateTime(timeFrom));
			printText(TextAlign.LEFT,
					(mUseAltLanguge?"集計終了:":"Order to: ")  + PosDateUtil.formatLocalDateTime(timeTo));
		}

	}

	// @Override
	protected int printReport(int pageIndex) throws Exception {
		setDecimalFormat();
		
		if (orderCount==0 && !midReport){
			
			printHeaders(mBillOutDevice);
			printCashSummary(mBillOutDevice);
				
		}else {
			printHeaders(mBillOutDevice);
			if (!printPaymentDtls) {
					
				if (isDayEndReport)
					summaryOnly= (PosEnvSettings.getInstance().getPrintSettings().getDayEndReportSettings().getDayEndReportType()==1);
			
				if (orderCount>0)
					printDetails(mBillOutDevice,summaryOnly);
				
//				printPaymentSummary();
					
				if(mShiftSummary!=null&&!midReport){
//					printItemDiscountsSummary(mBillOutDevice);
					printBillDiscountSummary(mBillOutDevice);
					printTaxSummary();
					printSalesSummary(mBillOutDevice);
					
					printPaymentSummary(mBillOutDevice);
//					printCashSummary(mBillOutDevice);
				}else{
					
					printMidSalesSummary();
				}
				if(PosEnvSettings.getInstance().getPrintSettings().getShiftReportSettings().isPrintVoidItemsInPaymentSummary()){
					
					if((voidOrderList!=null && voidOrderList.size()>0) || (voidItemList==null || voidItemList.size()>0)){
						advanceLine(4);
							printDashedLine();
					}
					printVoidItemdetails(true);
					printVoidItemdetails(false);
					advanceLine(2);
				}
				
			}else{
					printPaymentdetails();
			}
		}
	 
		printText(TextAlign.CENTER, mUseAltLanguge?"*********  日計集計 *********": "********* End of Report *********");
		return (pageIndex>0?NO_SUCH_PAGE:PAGE_EXISTS);
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
		advanceLine(2);
		if (!summaryOnly) {
			printDetailsHeader();
			advanceLine(2);
		}

		BeanSaleItem saleItem = orderDetailList.get(index).getSaleItem();
		BeanUOM uom;
		final String subTotalCaption=mUseAltLanguge?"小計":"Sub Total";
		do {
			itemName = saleItem.getName();
			itemQuantity += saleItem.getQuantity();
			itemTotal += PosSaleItemUtil.getGrandTotal(saleItem);
			uom=saleItem.getUom();
			if (!subClassName.equals(saleItem.getSubClass().getName())) {
				if (!subClassName.equals("") && !summaryOnly) {

					advanceLine(1);
//					setFontStyle(Font.BOLD);
					printLine( subTotalCaption + "(" + subClassName + ")", subTotal);
					setFont(mFontReceipt);
				}
				subClassName = saleItem.getSubClass().getName();
				if (!summaryOnly) {
					advanceLine(6);
//					setFontStyle(Font.BOLD);
					printText(TextAlign.LEFT, subClassName);
					advanceLine(1);
					setFont(mFontReceipt);
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
					printLineQuantityAmo(itemName, PosUomUtil.format(itemQuantity,uom), itemTotal);
				}
				if ((saleItem == null) && !subClassName.equals("")) {
					if (!summaryOnly) {
						advanceLine(1);
//						setFontStyle(Font.BOLD);
						printLine( subTotalCaption + "(" + subClassName + ")", subTotal);
						setFont(mFontReceipt);
						
					}
					totalGrossSale += subTotal;
					subTotal = 0;
				}
				itemName = "";
				itemTotal = 0;
				itemQuantity = 0;
			}
		} while (saleItem != null);
//		if (summaryOnly) {
//			advanceLine(6);
//			printLine("TOTAL", totalGrossSale);
//		}
		if(!midReport){
			if(PosEnvSettings.getInstance().getBillParams().getTax()!=null){
				printLine(mUseAltLanguge?"内税": "TOTAL BILL TAX", mTotalbillTax);
			}
			advanceLine(6);
			printLine(mUseAltLanguge?"注文数": "TOTAL Number Of Orders", String.valueOf(orderCount));
		}
		
	}
	
	/**
	 * 
	 */
private void printDetailsHeader() {
	final int gap = 1;
	int left = 3;
//	setFontStyle(Font.BOLD);
	printTextBlock(left, DESC_FIELD_WIDTH, mUseAltLanguge?"詳細": "Description", TextAlign.CENTER,
			false);
	/** The Quantity field **/
	left += DESC_FIELD_WIDTH + gap;
	printTextBlock(left, QTY_FIELD_WIDTH, mUseAltLanguge?"個数":"Qty", TextAlign.CENTER, false);
	/** The Rate field **/
	left += QTY_FIELD_WIDTH + gap;
	printTextBlock(left, RATE_FIELD_WIDTH, mUseAltLanguge?"金額":"Total", TextAlign.CENTER, false);
	setFont(mFontReceipt);
}

	protected void printMidSalesSummary()   throws Exception  {

		double totalAmount = 0.0;
		Map<PaymentMode, Double> payments = new HashMap<BeanPayModeBase.PaymentMode, Double>();
 
		printTaxSummary();
		
		advanceLine(4);
//		setFontStyle(Font.BOLD);
		printText(TextAlign.CENTER, mUseAltLanguge?"売上集計": "SALES SUMMARY");
		setFont(mFontReceipt);
		advanceLine(2);
		for (BeanOrderPayment payment : orderPaymentSummary) {
			payments.put(payment.getPaymentMode(), payment.getPaidAmount());
		}

		if (payments != null) {
			if (payments.containsKey(PaymentMode.Card)) {
				totalAmount += payments.get(PaymentMode.Card);
				printText(TextAlign.LEFT, mUseAltLanguge ? "カード  ": PaymentMode.Card.name(), false);
				printText(TextAlign.RIGHT,
						prepareAmountString(payments.get(PaymentMode.Card)));
			}
			if (payments.containsKey(PaymentMode.Cash)) {
				double CashReturned = 0;
				totalAmount += payments.get(PaymentMode.Cash);
				if (payments.containsKey(PaymentMode.Balance)) {
					CashReturned = payments.get(PaymentMode.Balance);
				}
				printText(TextAlign.LEFT, mUseAltLanguge ? "現金在高 ": PaymentMode.Cash.name(), false);
				printText(TextAlign.RIGHT,
						prepareAmountString(payments.get(PaymentMode.Cash)
								- CashReturned));
			}
			if (payments.containsKey(PaymentMode.Company)) {
				totalAmount += payments.get(PaymentMode.Company);
				printText(TextAlign.LEFT, mUseAltLanguge ? "会社 ": PaymentMode.Company.name(), false);
				printText(TextAlign.RIGHT,
						prepareAmountString(payments.get(PaymentMode.Company)));
			}
			if (payments.containsKey(PaymentMode.Coupon)) {
				double VoucherBalance = 0;
				totalAmount += payments.get(PaymentMode.Coupon);
				if (payments.containsKey(PaymentMode.CouponBalance)) {
					VoucherBalance = payments.get(PaymentMode.CouponBalance);
				}
				printText(TextAlign.LEFT, mUseAltLanguge ? "バウチャー ": PaymentMode.Coupon.name(), false);
				printText(TextAlign.RIGHT,
						prepareAmountString(payments.get(PaymentMode.Coupon)
								- VoucherBalance));
			}
			if (payments.containsKey(PaymentMode.Balance)) {
				totalAmount -= payments.get(PaymentMode.Balance);
			}

			if (payments.containsKey(PaymentMode.CouponBalance)) {
				totalAmount -= payments.get(PaymentMode.CouponBalance);
			}
			if (payments.containsKey(PaymentMode.CashOut)) {
				totalAmount -= payments.get(PaymentMode.CashOut);
			}
		}

		if (totalRefund > 0) {
			printText(TextAlign.LEFT, "Refund", false);
			printText(TextAlign.RIGHT, prepareAmountString(totalRefund));
			totalAmount -= totalRefund;
		}
		printText(TextAlign.LEFT, mUseAltLanguge ? "税込みの総売上 "
				: "Total Sales including tax", false);
		printText(TextAlign.RIGHT, prepareAmountString(totalAmount));

		if (PosEnvSettings.getInstance().getBillParams().getTax() != null) {
			printText(TextAlign.LEFT, mUseAltLanguge ? "税 " : "Tax", false);
			printText(TextAlign.RIGHT, prepareAmountString(mTotalbillTax));
		} else {
			printText(TextAlign.LEFT, mUseAltLanguge ? "総税 " : "Tax",
					false);
			printText(TextAlign.RIGHT, prepareAmountString(mTotalbillTax));
		}

//		printTaxSummary();
		printText(TextAlign.LEFT, mUseAltLanguge ? "注文数" : "No of orders",
				false);
		printText(TextAlign.RIGHT, String.valueOf(orderCount));
		 
		if(totalDiscount>0){
			printLine(mUseAltLanguge?"金額割引を減らす":"Less BILL DISCOUNTS", totalDiscount);
		}
		printExpense(mBillOutDevice, timeFrom, timeTo);
		
		
		
	}
	
	/**
	 * 
	 */
	protected void printTaxSummary(){
 
		advanceLine(4);
//		setFontStyle(Font.BOLD);
		printText(TextAlign.CENTER, mUseAltLanguge?"税務サマリー": "TAX SUMMARY");
		setFont(mFontReceipt);
		advanceLine(2);
		 for(Integer taxId: itemTaxSummaryList.keySet()){

			if(itemTaxSummaryList.get(taxId).getTaxAmount()>0){

				final BeanTax tax=PosTaxItemProvider.getInstance().getTaxItem(taxId);
		 		final String taxDescr=tax.getName();
		 		
//		 		itemTaxSummaryList.get(taxId).ge "(" + String.valueOf(itemTaxSummaryList.get(taxId).getTaxPercentage()) + (mUseAltLanguge?"%対象":"% Tax"); 
				  
		 		
		 		printTextBlock(5, DESC_FIELD_WIDTH+QTY_FIELD_WIDTH,taxDescr, TextAlign.LEFT, false);
				printTextBlock(7+DESC_FIELD_WIDTH+QTY_FIELD_WIDTH, RATE_FIELD_WIDTH,prepareAmountString(itemTaxSummaryList.get(taxId).getTaxAmount()) , TextAlign.RIGHT, true);
				 
				 
			 	}
		 }
		 advanceLine(2);
		 printDashedLine();
		}
	
//	/**
//	 * 
//	 */
//	protected void printPaymentdetails() {
//		final int gap = 1;
//		int left = 3;
//		
//		advanceLine(6);
//		printText(TextAlign.LEFT, mUseAltLanguge?"支払明細" : "PAYMENT DETAILS");
//		printTextBlock(left, DESC_FIELD_WIDTH, mUseAltLanguge?"請求書番号":"Invoice Number", TextAlign.CENTER,
//				false);
//		/** The Quantity field **/
//		left += DESC_FIELD_WIDTH + gap;
//		printTextBlock(left, QTY_FIELD_WIDTH, mUseAltLanguge?"種類":"Mode", TextAlign.LEFT, false);
//		/** The Rate field **/
//		left += QTY_FIELD_WIDTH + gap;
//		printTextBlock(left, RATE_FIELD_WIDTH,mUseAltLanguge?"金額": "Amount", TextAlign.CENTER, true);
//		for(BeanOrderPaymentReport payment :orderPaymentReportList){
//			if(payment.isRepayment()){
//				printPaymentLine("-" + payment.getInvoiceNo(),"Re-"+ getPaymentModeAltLang(payment.getPaymentMode()) ,payment.getPaidAmount());
//			}else{
//				printPaymentLine(payment.getInvoiceNo(),getPaymentModeAltLang(payment.getPaymentMode()),payment.getPaidAmount());
//			}
//		}
//		advanceLine(6);
//		printText(TextAlign.LEFT, mUseAltLanguge?"支払い集計":"PAYMENT SUMMARY");
//		 
//		for(BeanOrderPayment payment :orderPaymentSummary){
//		
//			printLine(getPaymentModeAltLang(payment.getPaymentMode()),payment.getPaidAmount());
//		}
//		if(totalRefund!=0){
//			printLine(mUseAltLanguge?"払い戻し  金額":  "Refund Amount:",totalRefund);
//		}
//	}
	
//	private String getPaymentModeAltLang(PaymentMode payment){
//
//		String paymentMode;
//		paymentMode=payment.name();
//		if (mUseAltLanguge) {
//			switch (payment){
//			case Cash:
//				paymentMode="現金";
//				break;
//			case Card:
//				paymentMode="カード ";
//				break;
//			case Company:
//				paymentMode="会社";
//				break;
//			case Coupon:
//				paymentMode="バウチャー";
//				break;
//			}
//		}
//		return paymentMode;
//		
//	}
	
	
	/**
	 * @param mBillOutDevice2
	 * @throws Exception
	 *             Sales summary.
	 */
	protected void printSalesSummary(PosPrintingDevice billBufferWriter) throws Exception {
		advanceLine(4);
//		setFontStyle(Font.BOLD);
		printText(TextAlign.CENTER, mUseAltLanguge?"売上集計": "SALES SUMMARY");
		setFont(mFontReceipt);
		advanceLine(2); 
	
		printLine( mUseAltLanguge ? "税込みの総売上 "
				: "Total Sales including tax", totalGrossSale,true);
		printLine(mUseAltLanguge?"金額割引を減らす":"Less BILL DISCOUNTS", totalDiscount,true);
		printLine(mUseAltLanguge?"払い戻し額を減らす":"Less REFUND", mShiftSummary.getTotalRefund(),true);
		advanceLine(3);
		printLine(mUseAltLanguge?"総売上高":"TOTAL GROSS SALE", totalGrossSale+((PosEnvSettings.getInstance().getBillParams().getTax()!=null)?mTotalbillTax:0)-totalDiscount-mShiftSummary.getTotalRefund(),true);
		advanceLine(2);
		printLine(mUseAltLanguge?"合計税金額":"TOTAL BILL TAX", mTotalbillTax,true);
		printLine(mUseAltLanguge?"合計ラウンドの調整": "TOTAL ROUNDING ADJ.", mTotalRoundAdjustMent);
		advanceLine(2);
		printDashedLine();
	}
	
	/**
	 * @throws Exception 
	 * 
	 */
	protected void printItemDiscountsSummary(PosPrintingDevice billBufferWriter) throws Exception {

		advanceLine(6);
		 		printText(TextAlign.LEFT, mUseAltLanguge?"商品割引の適用":"ITEM DISCOUNTS APPLIED");
		
		if(orderItemDiscountSummary!=null&&orderItemDiscountSummary.size()>0){
			for(int i=0;i<orderItemDiscountSummary.size();i++){
				int left =5;
				int gap = 1;
//				printLine(orderItemDiscountSummary.get(i).getName(), orderItemDiscountSummary.get(i).getQuantity(), orderItemDiscountSummary.get(i).getAmount());
				printTextBlock(left, DESC_FIELD_WIDTH,orderItemDiscountSummary.get(i).getName(), TextAlign.LEFT, false);
				left += DESC_FIELD_WIDTH + gap;
				printTextBlock(left, QTY_FIELD_WIDTH,String.valueOf(orderItemDiscountSummary.get(i).getQuantity()), TextAlign.RIGHT, false);
				left += QTY_FIELD_WIDTH + gap;
				printTextBlock(left, RATE_FIELD_WIDTH,prepareAmountString(orderItemDiscountSummary.get(i).getAmount()), TextAlign.RIGHT, true);
			}
		}else{
			printText(TextAlign.CENTER,mUseAltLanguge?"なし...": "None...");
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
		
	 
			printText(TextAlign.LEFT,mUseAltLanguge?"費用":"Expense ",false);
			printText(TextAlign.RIGHT,prepareAmountString(totalCashOut));
//			printLine("Expense", prepareAmountString(totalCashOut));
			
	 
		 
		
	}
	
	/**
	 * @param mBillOutDevice2
	 * @throws Exception 
	 */
	protected void printBillDiscountSummary(PosPrintingDevice billBufferWriter) throws Exception {
		advanceLine(6);
//		setFontStyle(Font.BOLD);
		printText(TextAlign.CENTER, mUseAltLanguge?"金額割引の適用":"BILL DISCOUNTS APPLIED ");
		setFont(mFontReceipt);
		advanceLine(2);
		if(billDiscountSummary!=null&&billDiscountSummary.size()>0){
			for(int i=0;i<billDiscountSummary.size();i++){
				int left =5;
				int gap = 1;
				printTextBlock(left, DESC_FIELD_WIDTH,billDiscountSummary.get(i).getName(), TextAlign.LEFT, false);
				left += DESC_FIELD_WIDTH + gap;
				printTextBlock(left, QTY_FIELD_WIDTH,String.valueOf(billDiscountSummary.get(i).getQuantity()), TextAlign.RIGHT, false);
				left += QTY_FIELD_WIDTH + gap;
				printTextBlock(left, RATE_FIELD_WIDTH,prepareAmountString(billDiscountSummary.get(i).getAmount()), TextAlign.RIGHT, true);
			}
		}else{
			printText(TextAlign.CENTER, mUseAltLanguge?"なし...":"None...");
		}
		advanceLine(2);
		printDashedLine();
		
	}
	
	/**
	 * @param mBillOutDevice2
	 * @throws Exception 
	 * Pyment summary here.
	 */
		protected void printPaymentSummary(PosPrintingDevice billBufferWriter) throws Exception {

		
		advanceLine(4);
//		setFontStyle(Font.BOLD);
		printText(TextAlign.CENTER,mUseAltLanguge?"概要": "SUMMARY");
		setFont(mFontReceipt);
		advanceLine(2);
		
//		printLine("Total Cash Tendered", mShiftSummary.getCashReceipts());
//		printLine("Total Change Given", mShiftSummary.getCashReturned());
		printLine(mUseAltLanguge?"現金取引の開始":"Opening Cash", mShiftSummary.getOpeningFloat(),true);
		printLine(mUseAltLanguge?"現金領収書":"Cash Receipts", mShiftSummary.getCashReceipts()-mShiftSummary.getCashReturned(),true);
		printLine(mUseAltLanguge?"現金払い戻し":"Cash Refund", mShiftSummary.getCashRefund(),true);
		printLine(mUseAltLanguge?"費用":"Expense ", mShiftSummary.getExpense(),true);
		
		final double netCash= mShiftSummary.getOpeningFloat() + mShiftSummary.getCashReceipts()-
				mShiftSummary.getCashReturned()- mShiftSummary.getCashRefund() -mShiftSummary.getExpense();
		
		printLine(mUseAltLanguge?"総現金":"Net Cash", netCash,true);
		printLine(mUseAltLanguge?"実際の現金":"Actual Cash", mShiftSummary.getActualCash(),true);
		printLine(mUseAltLanguge?"金額の不一致":"Cash Variance", mShiftSummary.getVariance(),true);
		advanceLine(2);
		printLine(mUseAltLanguge?"銀行預金金額":"Bank Deposit Amount", mShiftSummary.getCashDeposit(),true);
		printLine(mUseAltLanguge?"現金を閉じる":"Closing Cash ", mShiftSummary.getCashRemaining(),true);
		
		advanceLine(2);
		printLine(mUseAltLanguge?"カードの領収書":"Card Receipts", mShiftSummary.getCardReceipts(),true);
		printLine(mUseAltLanguge?"カード払い戻し":"Card Refund", mShiftSummary.getCardRefund(),true);
		printLine(mUseAltLanguge?"キャッシュアウト（カード）":"Cash Out", mShiftSummary.getCashOut());
		
		printLine(mUseAltLanguge?"商品割引券の領収書":"Voucher Receipts", mShiftSummary.getVoucherReceipts());
		printLine(mUseAltLanguge?"商品券の差額の返却":"Voucher Balance", mShiftSummary.getVoucherBalance());
		printLine(mUseAltLanguge?"クレジット":"Credit", mShiftSummary.getAccountsReceivable());
//		printLine(mUseAltLanguge?"売掛金":"Accounts Receivable", mShiftSummary.getAccountsReceivable());
		
		 
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
			
			advanceLine(4);
//			setFontStyle(Font.BOLD);
			final String voidOrdersTitle=mUseAltLanguge?"削除された注文":"VOID ORDERS";
			final String voidItemsTitle=mUseAltLanguge?"削除済みアイテム（支払い済み注文）":"VOID ITEMS (PAID)";
			
			printText(TextAlign.CENTER, voidOrdersonly?voidOrdersTitle:voidItemsTitle);
			setFont(mFontReceipt);
			 
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
//					printTextBlock(left+DESC_FIELD_WIDTH, QTY_FIELD_WIDTH+RATE_FIELD_WIDTH, PosDateUtil.formatShortDateTime( ordDtl.getVoidAt()), TextAlign.RIGHT,true);
					
				}
				
			}
			
		}
//	/**
//	 * @param billBufferWriter
//	 * @return
//	 * @throws Exception
//	 * Print cash summary here. 
//	 */
//	protected void printCashSummary(PosPrintingDevice billBufferWriter) throws Exception{
//		
//		advanceLine(6);
//		printText(TextAlign.LEFT,mUseAltLanguge?"現金集計": "CASH SUMMARY");
//		printLine(mUseAltLanguge?"受け取った現金":"Net Cash Received", mShiftSummary.getNetCash());
//		printLine(mUseAltLanguge?"支出":"Cash Out", mShiftSummary.getCashOut());
//		printLine(mUseAltLanguge?"商品券の差額の返却":"Voucher Bal. Returned", mShiftSummary.getVoucherBalanceReturned());
//		printLine(mUseAltLanguge?"現金払い戻し":"Cash Refunds", mShiftSummary.getCashRefund());
//			printLine(mUseAltLanguge?"金額の不一致":"Cash Variance", mShiftSummary.getVariance());
//		advanceLine(3);
//		printLine(mUseAltLanguge?"銀行預金金額":"Bank Deposit Amount", mShiftSummary.getCashDeposit());
//		printLine(mUseAltLanguge?"お釣り":"Balance", mShiftSummary.getCashRemaining());
//		advanceLine(6);
////		printLine("Bank Deposit Slip Number ", mShiftSummary.getReferenceSlipNumber());
////		advanceLine(6);
//		printLine(mUseAltLanguge?"最終報告者":"Report Finalised by ", "................");
//	}
}
