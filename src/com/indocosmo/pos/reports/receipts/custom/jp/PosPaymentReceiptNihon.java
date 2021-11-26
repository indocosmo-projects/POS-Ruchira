/**
 * 
 */
package com.indocosmo.pos.reports.receipts.custom.jp;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.ImageIO;

import com.indocosmo.pos.common.PosEnvSettings;
import com.indocosmo.pos.common.beans.BeanBillPaymentSummaryInfo;
import com.indocosmo.pos.common.beans.BeanReceiptTaxSummary;
import com.indocosmo.pos.common.enums.device.PrinterType;
import com.indocosmo.pos.common.utilities.PosCurrencyUtil;
import com.indocosmo.pos.common.utilities.PosDateUtil;
import com.indocosmo.pos.common.utilities.PosNumberUtil;
import com.indocosmo.pos.common.utilities.PosOrderUtil;
import com.indocosmo.pos.common.utilities.PosPrintingUtil;
import com.indocosmo.pos.common.utilities.PosResUtil.PosResourceType;
import com.indocosmo.pos.common.utilities.PosSaleItemUtil;
import com.indocosmo.pos.common.utilities.PosTaxUtil;
import com.indocosmo.pos.data.beans.BeanBillParam;
import com.indocosmo.pos.data.beans.BeanDiscount;
import com.indocosmo.pos.data.beans.BeanOrderDetail;
import com.indocosmo.pos.data.beans.BeanOrderHeader;
import com.indocosmo.pos.data.beans.BeanTax;
import com.indocosmo.pos.data.beans.BeanOrderHeader.PosOrderStatus;
import com.indocosmo.pos.data.beans.BeanOrderPayment;
import com.indocosmo.pos.data.beans.BeanSaleItem;
import com.indocosmo.pos.data.beans.BeanServingTable;
import com.indocosmo.pos.data.providers.shopdb.PosDiscountItemProvider;
import com.indocosmo.pos.data.providers.shopdb.PosPromotionItemProvider;
import com.indocosmo.pos.data.providers.shopdb.PosTaxItemProvider;
import com.indocosmo.pos.data.providers.shopdb.PosTaxItemProvider.TaxCalculationMethod;
import com.indocosmo.pos.data.providers.terminaldb.PosDevReceiptPrinterConfigProvider;
import com.indocosmo.pos.reports.base.PosPaymentReceiptBase;
import com.indocosmo.pos.reports.base.PosPrintableBase.TextAlign;
import com.indocosmo.pos.terminal.devices.PosDeviceManager;

/**
 * @author deepak
 * 
 */
public class PosPaymentReceiptNihon extends PosPaymentReceiptBase {



	/**
	 * Serial Number column width
	 */
	private static final int SLNO_FIELD_WIDTH = 0;
	/**
	 * Item level indicator
	 */
	private static final int ITEM_LEVEL_FIELD_WITDH =8;
	/**
	 * Description column width
	 */
	//	private static final int DESC_FIELD_WIDTH = 60;
	private     int DESC_FIELD_WIDTH = 92;

	private static final int QTY_FIELD_WIDTH = 25;
	private static final int RATE_FIELD_WIDTH = 0;
	private static final int TOTAL_FIELD_WIDTH = 40;
	private static final int TAX_LABEL_WIDTH = 8;
	

	private String mCurrency;
	private String decimalFormat="#,###";
	private double totalOfExclusiveTax = 0.0;
	private double totalOfInclusiveTax = 0.0;
	private double subTotal = 0.0;
	private double exclusiveTaxableAmount = 0.0;
	private double inclusiveTaxableAmount = 0.0;
	private double totalItemQuanty = 0.0;
	private HashMap<Integer, BeanReceiptTaxSummary> itemTaxList;

	/**
	 * 
	 */
	public PosPaymentReceiptNihon(BeanOrderHeader order) {
		super();
		setOrder(order);
		mFontReceipt = new Font("Ariel", Font.PLAIN, 8);
		mCurrency = PosEnvSettings.getInstance().getCurrencySymbol();
//		setOverridePrinterSettings(true);
		preparePaymentInfo(getOrder().getOrderPaymentItems());
		try {
			mUseAltLanguge = PosDeviceManager.getInstance().canUseAltLanguage(getPrinterType());
		} catch (Exception e) {

			e.printStackTrace();
		}
	}




	/**
	 * 
	 */
	public PosPaymentReceiptNihon() {
		mFontReceipt = new Font("Ariel", Font.PLAIN, 8);
		mCurrency = PosEnvSettings.getInstance().getCurrencySymbol();
//		setOverridePrinterSettings(true);
		try {
			mUseAltLanguge = PosDeviceManager.getInstance().canUseAltLanguage(getPrinterType());
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.indocosmo.pos.reports.PosPrintableReportBase#onInitPrint(java.awt
	 * .Graphics2D, java.awt.print.PageFormat)
	 */
	@Override
	protected void onInitialized(Graphics2D g2d, PageFormat pf) throws Exception {
		setFont(mFontReceipt);
		
		DESC_FIELD_WIDTH=getPosReportPageFormat().getImageableWidthInPixcel()-(
				
				SLNO_FIELD_WIDTH+1+
				ITEM_LEVEL_FIELD_WITDH+1+
				QTY_FIELD_WIDTH+1+
				RATE_FIELD_WIDTH+1+
				TOTAL_FIELD_WIDTH + 1 + 
				TAX_LABEL_WIDTH
				);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.indocosmo.pos.reports.receipts.PosPrintableReceiptBase#printHeader
	 * (com.indocosmo.pos.data.beans.BeanOrderHeader,
	 * com.indocosmo.pos.data.beans.BeanBillParam)
	 */
	@Override
	protected void onPrintHeader(BeanOrderHeader order, BeanBillParam param)
			throws Exception {

		/*
		 * Prints the Title and headers
		 */
		final String hdrLine1 = param.getHeaderLine1();
		final String hdrLine2 = param.getHeaderLine2();
		final String hdrLine3 = param.getHeaderLine3();
		final String hdrLine4 = param.getHeaderLine4();
		try {
			BufferedImage bi =ImageIO.read(new File("logos/receipt_header.png"));
			printImage(bi, 0, 0, 180, 56);
		} catch (IOException e) {
			//				e.printStackTrace();
		}

		setFontStyle(Font.BOLD);
		setFontSize(10.0f);
		printText(TextAlign.CENTER, hdrLine1);

		setFont(mFontReceipt);
		printText(TextAlign.CENTER, hdrLine2);
		printText(TextAlign.CENTER, hdrLine3);
		printText(TextAlign.CENTER, hdrLine4);

		advanceLine();
		/*Print Bill number, Date And service details.*/
		
		setFont(mFontReceipt);
		
		String billNo ="";
		
		if (isBillPrinting)
			billNo = "No.  " + PosOrderUtil.getShortOrderIDFromOrderID(order.getOrderId());
		else
			billNo="No.  " + order.getInvoiceNo();
		
		String billDate =  (mUseAltLanguge?"日付 ":"Dt :" )  + PosDateUtil.formatLocal(isBillPrinting?order.getOrderDate():
			(order.getClosingDate()==null?PosDateUtil.getDateTime():order.getClosingDate()));
		billDate+= " ";
		billDate+=PosDateUtil.format(PosDateUtil.getLocalTimeFormat(), isBillPrinting?order.getOrderTime(): 
					(order.getClosingTime()==null?PosDateUtil.getDateTime():order.getClosingTime()));  

//		final String printedAt = "で印刷 : "+ PosEnvSettings.getInstance().getStation().getName();
 
		printText(TextAlign.LEFT, billNo );
		printText(TextAlign.LEFT, billDate);
//		printText(TextAlign.LEFT, printedAt);


		String footerLine_ServiceType = mUseAltLanguge?"区分 ":"Service Type :";
		String footerLine_Covers="";
		String footerLine_ServedBy = mUseAltLanguge?""
				+ "担当  ":"Served By :";
		footerLine_ServiceType += order.getOrderServiceType()
				.getDisplayText();

		switch (order.getOrderServiceType()) {
		case HOME_DELIVERY:
			footerLine_ServedBy += (order.getServedBy() != null) ? order
					.getServedBy().getName() : "";
					break;
		case TABLE_SERVICE:
			ArrayList<BeanServingTable> tables = PosOrderUtil
			.getAllServingTables(order);
			String tableCodes = " [";
			for (BeanServingTable tbl : tables) {
				tableCodes += tbl.getName() + ",";
			}
			tableCodes = tableCodes.substring(0, tableCodes.length() - 1) + "]";
			footerLine_ServiceType += tableCodes;
			footerLine_Covers = mUseAltLanguge?"カバー        : ":"covers :"+order.getCovers();
			footerLine_ServedBy += (order.getServedBy() != null) ? order
					.getServedBy().getName() : "";
					break;
		case TAKE_AWAY:
			footerLine_ServedBy += order.getUser().getName();
			break;
		}

		/** Serving information **/


		if (footerLine_ServiceType.trim().length() > 0) {

			printText(TextAlign.LEFT, footerLine_ServiceType);
		}
		
		final int servedByWidth=DESC_FIELD_WIDTH + QTY_FIELD_WIDTH +5 ;
		final int coversWidth=TOTAL_FIELD_WIDTH + ITEM_LEVEL_FIELD_WITDH;
		if (footerLine_ServedBy.trim().length() > 0) {
			printTextBlock(0, servedByWidth, footerLine_ServedBy, TextAlign.LEFT, false);
		}
		printTextBlock(servedByWidth,coversWidth, footerLine_Covers, TextAlign.RIGHT, true,true);
		
		
		//		if(footerLine_Covers.trim().length() > 0){
		//			printText(TextAlign.LEFT, footerLine_Covers);
		//		}
//		final String orderStatus = "注文状況   : " + order.getStatus().getDisplayText();
//		printText(TextAlign.LEFT, orderStatus);
//		drawLine(getDefaultHeaderSeparatorStyle());
		advanceLine(3);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.indocosmo.pos.reports.receipts.PosPrintableReceiptBase#printDetails
	 * (com.indocosmo.pos.data.beans.BeanOrderHeader)
	 */
	@Override
	protected void onPrintDetails(BeanOrderHeader order) throws Exception {

		itemTaxList=new HashMap<Integer, BeanReceiptTaxSummary>();

		totalOfExclusiveTax = 0.0;
		subTotal = 0.0;
		exclusiveTaxableAmount = 0.0;
		totalItemQuanty = 0.0;
		totalOfInclusiveTax=0.0;
		inclusiveTaxableAmount=0;
		
		if(order.getOrderDetailItems()==null || order.getOrderDetailItems().size()<=0) return;

//		printDetailsHeader();

		int slNo = 1;
		for (BeanOrderDetail dtl : order.getOrderDetailItems()) {

			if(dtl.isVoid()) continue;
			slNo=printDetailItem(slNo, dtl,true);

		}
		advanceLine();
//		drawLine(getDefaultDetailSeparatorStyle());
	}

	private int printDetailItem(int slNo,BeanOrderDetail dtlItem, boolean printSub){

		/**
		 * Print the order item 
		 */
		printDetailItem(slNo++, dtlItem);

		/**Check ordered item has sub items**/
		if(dtlItem.hasSubItems()){
			/**If has combo contents print them**/
			if(dtlItem.isComboContentsSelected()){
				for(ArrayList<BeanOrderDetail> subItemList:dtlItem.getComboSubstitutes().values()){
					for(BeanOrderDetail item:subItemList){
						printDetailItem(slNo++, item);
					}
				}
			}
			/**If has extras print them**/
			if(dtlItem.isExtraItemsSelected()){
				for(ArrayList<BeanOrderDetail> subItemList:dtlItem.getExtraItemList().values()){
					for(BeanOrderDetail item:subItemList){
						printDetailItem(slNo++, item);
					}
				}
			}
		}

		return slNo;
	}

	/**
	 * Prints the header for details
	 */
	protected void printDetailsHeader() {

		final int gap = 1;
		int left = 0;

		/** The Serial number field **/
		printTextBlock(left, SLNO_FIELD_WIDTH, "SL.", TextAlign.CENTER, false);

		/** The item level indicator **/
		left += SLNO_FIELD_WIDTH + gap;
		printTextBlock(left, ITEM_LEVEL_FIELD_WITDH, "", TextAlign.CENTER, false);

		/** The Quantity field **/
		left += ITEM_LEVEL_FIELD_WITDH + gap;
		printTextBlock(left, QTY_FIELD_WIDTH, "量", TextAlign.LEFT, false);

		/** The Description field **/
		left += QTY_FIELD_WIDTH + gap;
		printTextBlock(left, DESC_FIELD_WIDTH, "説明", TextAlign.CENTER,
				false);

		/** The Rate field **/
		left += DESC_FIELD_WIDTH + gap;
		printTextBlock(left, RATE_FIELD_WIDTH, "レート", TextAlign.CENTER, false);

		/** The Total Amount field **/
		left += RATE_FIELD_WIDTH + gap;
		printTextBlock(left, TOTAL_FIELD_WIDTH, "合計", TextAlign.CENTER, true);
		printSingleLine();
	}

	/**
	 * Prints the details part
	 */
	protected void printDetailItem(int srNo, BeanOrderDetail dtl) {

		final BeanSaleItem saleItem = dtl.getSaleItem();
		final int gap = 1;

		int left = 0;
		/** The Serial number field **/
		printTextBlock(left, SLNO_FIELD_WIDTH, String.valueOf(srNo),
				TextAlign.RIGHT, false);

		/** The Item Level field **/
		left += SLNO_FIELD_WIDTH + gap;
		printTextBlock(left, ITEM_LEVEL_FIELD_WITDH,
				PosPrintingUtil.getItemLevelIndicator(dtl), TextAlign.CENTER, false);

		/** The Quantity field **/
		totalItemQuanty = totalItemQuanty + PosOrderUtil.getItemQuantity(dtl);
		left += ITEM_LEVEL_FIELD_WITDH + gap;
		printTextBlock(left, QTY_FIELD_WIDTH,
				PosNumberUtil.format(PosOrderUtil.getItemQuantity(dtl)), TextAlign.LEFT, false);

		/** The Description field **/
		left += QTY_FIELD_WIDTH + gap;
		String itemName=  PosSaleItemUtil.getItemNameToPrint(dtl.getSaleItem(), mUseAltLanguge) ;
		if ( saleItem.getTax().getTaxOnePercentage()==8)
			itemName="※" + itemName ;
		
		printTextBlock(left, DESC_FIELD_WIDTH,itemName, TextAlign.LEFT, false);

		/** The Rate field **/
		left += DESC_FIELD_WIDTH + gap;
		String taxLabel = "";
		 if(!saleItem.getTax().getCode().equalsIgnoreCase("NOTAX")){
			if(saleItem.getTaxCalculationMethod() == TaxCalculationMethod.ExclusiveOfTax){
				taxLabel = "外";
				totalOfExclusiveTax = totalOfExclusiveTax + PosCurrencyUtil.roundTo(saleItem.getT1TaxAmount()+
						saleItem.getT2TaxAmount()+
						saleItem.getT3TaxAmount()+
						saleItem.getServiceTaxAmount()+
						saleItem.getGSTAmount());
				exclusiveTaxableAmount = exclusiveTaxableAmount + PosOrderUtil.getTotalTaxableAmount(dtl);
			}
			else
			{
				totalOfInclusiveTax = totalOfInclusiveTax + PosCurrencyUtil.roundTo(saleItem.getT1TaxAmount()+
						saleItem.getT2TaxAmount()+
						saleItem.getT3TaxAmount()+
						saleItem.getServiceTaxAmount()+
						saleItem.getGSTAmount());
				inclusiveTaxableAmount = inclusiveTaxableAmount + PosOrderUtil.getTotalTaxableAmount(dtl);
			}
		}
		printTextBlock(left, RATE_FIELD_WIDTH,
				PosNumberUtil.format(PosOrderUtil
						.getItemFixedPrice(dtl)), TextAlign.RIGHT, false);

		/** The Total Amount field **/
		left += RATE_FIELD_WIDTH + gap;
		subTotal = subTotal + PosOrderUtil.getTotalItemPrice(dtl);
		printTextBlock(left, TOTAL_FIELD_WIDTH,mCurrency+
				PosNumberUtil.format(PosOrderUtil
						.getTotalItemPrice(dtl),decimalFormat), TextAlign.RIGHT, taxLabel.equals("")?true:false);
		/** The tax label field **/
		left += TOTAL_FIELD_WIDTH + gap;
		printTextBlock(left, TAX_LABEL_WIDTH,taxLabel, TextAlign.RIGHT, true);
		final BeanDiscount discount=saleItem.getDiscount();
		if(discount!=null && 
				!discount.getCode().equals(PosDiscountItemProvider.NONE_DISCOUNT_CODE) && 
				!discount.getCode().equals(PosPromotionItemProvider.DEF_PROMO_CODE)){
			String discountAmount="-"+PosNumberUtil.format(PosOrderUtil.getTotalDiscountAmount(dtl),decimalFormat);
			String discountName=" LESS "+ discount.getName();
			int atX=SLNO_FIELD_WIDTH+gap+ITEM_LEVEL_FIELD_WITDH+gap;
			final int discDescWidth=QTY_FIELD_WIDTH+gap+DESC_FIELD_WIDTH;
			printTextBlock(atX, discDescWidth,discountName, TextAlign.LEFT, false);
			atX+=discDescWidth+gap;
			printTextBlock(atX,TOTAL_FIELD_WIDTH,discountAmount,TextAlign.RIGHT,true);
		}
		
		setTaxSummary(dtl);
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.indocosmo.pos.reports.receipts.PosPrintableReceiptBase#onPrintBillSummary
	 * (com.indocosmo.pos.data.beans.BeanOrderHeader)
	 */
	@Override
	protected void onPrintBillSummary(BeanOrderHeader order) throws Exception {

		final int blockLeft = 10;
		final int blockWidth = 90;
		final int amtLeft = SLNO_FIELD_WIDTH+ITEM_LEVEL_FIELD_WITDH+QTY_FIELD_WIDTH+DESC_FIELD_WIDTH+RATE_FIELD_WIDTH+5;

		printTextBlock(blockLeft, blockWidth, mUseAltLanguge?"小　計:":"Sub total :", TextAlign.RIGHT, false);
		printTextBlock(amtLeft, TOTAL_FIELD_WIDTH,mCurrency+PosNumberUtil.format(subTotal,decimalFormat), TextAlign.RIGHT, true);
		
// 		printTextBlock(blockLeft, blockWidth, mUseAltLanguge?"（うち消費税":"Tax    amount :", TextAlign.RIGHT, false);
// 		final String taxAmount=mCurrency+PosNumberUtil.format(totalOfExclusiveTax + totalOfInclusiveTax,decimalFormat);
//		printTextBlock(amtLeft, TOTAL_FIELD_WIDTH,taxAmount + (mUseAltLanguge?")":"") , TextAlign.RIGHT, true);
		
		if (order.getBillDiscountAmount() > 0) {
			final String totalDiscAmtText = PosNumberUtil.format(order
					.getBillDiscountAmount(),decimalFormat);
			/*Bill discount  = 割引手形*/
			printTextBlock(blockLeft, blockWidth, mUseAltLanguge?"割引手形 :":"Bill discount ",
					TextAlign.RIGHT, false);
//			printText(TextAlign.RIGHT, mCurrency+totalDiscAmtText);
			printTextBlock(amtLeft, TOTAL_FIELD_WIDTH,mCurrency+totalDiscAmtText, TextAlign.RIGHT, true);

		}
		double roundingAdjustment =0;
		roundingAdjustment=Double.parseDouble(PosNumberUtil.format(order.getRoundAdjustmentAmount()));
//		if(totalSplitPartAdjPayment!=0){ 
//			/**Split adjustment = 裂けた調整**/
//			printTextBlock(blockLeft, blockWidth, "裂けた調整 :", TextAlign.RIGHT,
//					false);
//			printText(TextAlign.RIGHT, PosNumberUtil.formatNumber(totalSplitPartAdjPayment));
//		}

		final double netPayble=PosNumberUtil.roundTo(order.getTotalAmount())
				+roundingAdjustment
				-order.getBillDiscountAmount()
				-totalSplitPartAdjPayment;

		
		advanceTextLine(1);
		String totalAmtText = PosNumberUtil.format(netPayble,decimalFormat);
		/**Net payment = 純支払い**/
		printTextBlock(blockLeft, blockWidth, mUseAltLanguge?"合計：":"Total :", TextAlign.RIGHT, false);
		setFontStyle(Font.BOLD);
		setFontSize(10.0f);
//		printText(TextAlign.RIGHT, mCurrency + totalAmtText);
		printTextBlock(amtLeft, TOTAL_FIELD_WIDTH,mCurrency + totalAmtText, TextAlign.RIGHT, true);

		setFont(mFontReceipt);

		double GstTotal=order.getTotalTax1()+
				order.getTotalTax2()+
				order.getTotalTax3()+
				order.getTotalServiceTax()+
				order.getTotalGST()-
				order.getBillTaxAmount();
		if(GstTotal>0){

				if(PosEnvSettings.getInstance().getBillParams().getTaxSummaryDisplayType()==BeanBillParam.SHOW_TAX_TABLE ){

					printTaxSummary();

				}
		}
	 
		
		advanceTextLine(1);
		
//		printTextBlock(blockLeft, blockWidth, mUseAltLanguge?"点数  ":"No. of items :", TextAlign.RIGHT, false);
		printTextBlock(amtLeft, TOTAL_FIELD_WIDTH,String.valueOf((int)totalItemQuanty), TextAlign.RIGHT, true);

		advanceLine();

	}



	/**
	 * 
	 */
	//	private void printPartialPaymentInfo() {
	//		
	//		
	//	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indocosmo.pos.reports.receipts.PosPrintableReceiptBase#
	 * printPaymentSummary(com.indocosmo.pos.data.beans.BeanOrderHeader)
	 */
	@Override
	protected void onPrintPaymentSummary(BeanOrderHeader order)
			throws Exception {

		//		if(!mPrePaymentReceipt){
		printPaymentSummary(order);
		//		}else
		//			printPaymentSummaryForPartial(order);
	}


	/**
	 * For normal Payments
	 * @param order
	 */
	private void printPaymentSummary(BeanOrderHeader order){


		double balance = 0 ;
		double totalPayed=0;

		if(order.getStatus().equals(PosOrderStatus.Void)){
			
			/**VOID BILL空洞法案**/
			printText(TextAlign.CENTER, mUseAltLanguge?"VOID BILL":"空洞法案");
			printDashedLine();
		}


		totalPayed=totalCashPayment+totalCardPayment+totalCompanyPayment+totalCouponPayment;
		balance=totalBalancePayment;

		/**CASH = 現金 **/
		printPaymentInfo(mUseAltLanguge?"お預かり（現金）":"CASH :",totalCashPayment );
		/** CARD = カード **/
		printPaymentInfo(mUseAltLanguge?"お預かり（カード ) ":" CARD :", totalCardPayment );
		/**COMAPANY = 会社**/
		printPaymentInfo(mUseAltLanguge?"お預かり（会社) ":"COMPANY :", totalCompanyPayment);
		/** VOUCHER = バウチャー **/
		printPaymentInfo(mUseAltLanguge?"お預かり（バウチャー) ":"VOUCHER :", totalCouponPayment);


//		printSingleLine();
		

		
		/** CHANGE = 変化 **Deepak**/
		if(totalPayed>0){
//			printTextBlock(blockLeft, blockWidth, mUseAltLanguge?"お釣り  :":"CHANGE :", TextAlign.LEFT,
//					false);
//			printTextBlock(blockLeft, blockWidth, mCurrency+PosNumberUtil.format(balance,decimalFormat), TextAlign.RIGHT,
//					true);
			
			printPaymentInfo( mUseAltLanguge?"お釣り  ":"CHANGE " ,balance);
			 
		}
 
		if (mUseAltLanguge){

			if(order.getStatus().equals(PosOrderStatus.Refunded)){
	
				refundDetails(order);
	
			}
		}
		advanceLine(4);
		final int blockLeft = 0;
		final int blockWidth = SLNO_FIELD_WIDTH+ITEM_LEVEL_FIELD_WITDH+QTY_FIELD_WIDTH+DESC_FIELD_WIDTH+RATE_FIELD_WIDTH+5 + TOTAL_FIELD_WIDTH;

			
		printTextBlock(blockLeft, blockWidth,  "※印は軽減税率(8%)適用商品", TextAlign.LEFT,
				true);
	}

	private void printPaymentInfo(String paymenTitle, double amount ){

		if(amount!=0){

			final int blockLeft = 0;
			final int blockWidth = 90;
			final int amtLeft = SLNO_FIELD_WIDTH+ITEM_LEVEL_FIELD_WITDH+QTY_FIELD_WIDTH+DESC_FIELD_WIDTH+RATE_FIELD_WIDTH+5;
			
			printTextBlock(blockLeft, blockWidth, paymenTitle, TextAlign.LEFT,
					false);
			printTextBlock(amtLeft, TOTAL_FIELD_WIDTH, mCurrency+PosNumberUtil.format(amount,decimalFormat), TextAlign.RIGHT,
					true);
			 
		
		}

	}

	private void preparePaymentInfo(ArrayList<BeanOrderPayment> paymentDetails){

		totalCashPayment=0.0;
		totalCardPayment=0.0;
		totalCashOutPayment=0.0;
		totalCompanyPayment=0.0;
		totalCouponPayment=0.0;
		totalBalancePayment=0.0;
		totalCouponBalancePayment=0.0;
		//		totalSplitPartAdjPayment=0.0;


		for (BeanOrderPayment payment : paymentDetails) {
			switch (payment.getPaymentMode()) {
			case Cash:
				if (payment.getPaidAmount() > 0){
					totalCashPayment+=payment.getPaidAmount();
				}
				break;
			case Card:
				if (payment.getPaidAmount() > 0){
					totalCardPayment+=payment.getPaidAmount();
				}
				break;
			case CashOut:
				if(payment.getPaidAmount() > 0)
					totalCashOutPayment+=payment.getPaidAmount();
				break;	
			case Company:
				if (payment.getPaidAmount() > 0){
					totalCompanyPayment+=payment.getPaidAmount();
				}
				break;
			case Coupon:
				if (payment.getVoucherCount()>0){
					totalCouponPayment+=payment.getPaidAmount();
				}
				break;
			case Balance:
				if (payment.getPaidAmount()>0){
					totalBalancePayment+=payment.getPaidAmount();
				}
				break;
			case CouponBalance:
				if (payment.getPaidAmount()>0){
					totalCouponBalancePayment+=payment.getPaidAmount();
					if(payment.isVoucherBalanceReturned()){
						totalBalancePayment+=payment.getPaidAmount();
					}
				}
				break;
			case SplitAdjust:
				/**
				 * This is not needed as it is not saved to db.
				 * Use payment summary info to get totalSplitPartAdjPayment
				 */
				//				if (payment.getPaidAmount()>0){
				//					totalSplitPartAdjPayment+=payment.getPaidAmount();
				//				}
				break;
			default:
				break;
			}

		}

	}

	
	/**
	 * @param order
	 */
	private void refundDetails(BeanOrderHeader order) {
		final int blockLeft = 0;
		final int blockWidth = 100;
		ArrayList<BeanOrderPayment> paymentDetails  =order.getOrderPaymentItems();
		printText(TextAlign.CENTER, "REFUND DETAILS");
		for (BeanOrderPayment payment : paymentDetails) {
			switch (payment.getPaymentMode()) {
			case Cash:
				if (payment.getPaidAmount() > 0&&payment.isRepayment()){
					/**CASH = 現金 **/
					printTextBlock(blockLeft, blockWidth, mUseAltLanguge?"現金 :":"CASH :", TextAlign.LEFT,
							false);
					printTextBlock(blockLeft, blockWidth, mCurrency+PosNumberUtil.format(payment.getPaidAmount(),decimalFormat), TextAlign.RIGHT,
							true);
				}
				break;
			case Card:
				if (payment.getPaidAmount() > 0&&payment.isRepayment())
					
					/** CARD = カード **/
					printTextBlock(blockLeft, blockWidth,mUseAltLanguge?"カード :":"CARD :", TextAlign.LEFT,
							false);
				printTextBlock(blockLeft, blockWidth,mCurrency+PosNumberUtil.format(payment.getPaidAmount(),decimalFormat), TextAlign.RIGHT,
						true);	
				break;
			case CashOut:
				if(payment.getPaidAmount() > 0&&payment.isRepayment())

					break;	
			case Company:
				if (payment.getPaidAmount() > 0&&payment.isRepayment())
					/**COMAPANY = 会社**/
					printTextBlock(blockLeft, blockWidth,mUseAltLanguge?"会社 :":"COMPANY :", TextAlign.LEFT,
							false);
				printTextBlock(blockLeft, blockWidth, PosNumberUtil.format(payment.getPaidAmount(),decimalFormat), TextAlign.RIGHT,
						true);
				break;
			case Coupon:
				if (payment.getVoucherCount()>0&&payment.isRepayment()){

					/** VOUCHER = バウチャー **/
					printTextBlock(blockLeft, blockWidth,mUseAltLanguge?"バウチャー :":"VOUCHER :", TextAlign.LEFT,
							false);
					printTextBlock(blockLeft, blockWidth, PosNumberUtil.format(payment.getPaidAmount(),decimalFormat), TextAlign.RIGHT,
							true);
				}
				break;
				default:
				break;
			}

		}
		drawLine(getDefaultPaymentSummarySeparatorStyle());

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.indocosmo.pos.reports.receipts.PosPrintableReceiptBase#printFooter
	 * (com.indocosmo.pos.data.beans.BeanBillParam,
	 * com.indocosmo.pos.data.beans.BeanOrderHeader)
	 */
	@Override
	protected void onPrintFooter(BeanBillParam param, BeanOrderHeader order)
			throws Exception {



		/** footer information **/
		final String footerLine_1 = param.getFooterLine1();
		final String footerLine_2 = param.getFooterLine2();
		final String footerLine_3 = param.getFooterLine3();
		final String footerLine_4 = param.getFooterLine4();

		if (footerLine_1 != null && footerLine_1.trim().length() > 0) {
			printText(TextAlign.CENTER, footerLine_1);
		}
		if (footerLine_2 != null && footerLine_2.trim().length() > 0) {
			printText(TextAlign.CENTER, footerLine_2);
		}
		if (footerLine_3 != null && footerLine_3.trim().length() > 0) {
			printText(TextAlign.CENTER, footerLine_3);
		}
		if (footerLine_4 != null && footerLine_4.trim().length() > 0) {
			printText(TextAlign.CENTER, footerLine_4);
		}
		//		}
		//			advanceLine(20);
//		printText(TextAlign.CENTER, ".");
//		printText(TextAlign.CENTER, ".");
//		printText(TextAlign.CENTER, ".");
//		printText(TextAlign.CENTER, ".");

	}


	/**
	 * @param mPrePaymentReceipt the mPrePaymentReceipt to set
	 */
	public void setPrePaymentReceipt(boolean prePaymentReceipt) {
		this.mPrePaymentReceipt = prePaymentReceipt;
	}

	/**
	 * @param currentBillInfo
	 */
	public void setPreBillPaymentInfo(BeanBillPaymentSummaryInfo currentBillInfo) {

		this.currentBillInfo=currentBillInfo;

		setPrePaymentReceipt((currentBillInfo!=null && 
				currentBillInfo.isPartialPayment()));
		totalSplitPartAdjPayment=currentBillInfo.getPartPayAdjustment();


	}

	/* (non-Javadoc)
	 * @see com.indocosmo.pos.reports.receipts.PosPrintableReceiptBase#setOrder(com.indocosmo.pos.data.beans.BeanOrderHeader)
	 */
	@Override
	public void setOrder(BeanOrderHeader order) {
		super.setOrder(order);

		preparePaymentInfo(getOrder().getOrderPaymentItems());
	}

	/**
	 * Returns theUnpaidAmount.
	 *  
	 * @return
	 */
	private double getetUnpaidAmount(){

		double totalUnpaidAmount=0.0;

		if(mPrePaymentReceipt)
			/**
			 * In case o partial payments get it from bill info
			 */
			totalUnpaidAmount=currentBillInfo.getUnPaidAmount();
		else
			/**
			 * In case of normal payment calculate it from the payments
			 */
			totalUnpaidAmount=PosOrderUtil.getTotalBalanceOnBill(mOrder);

		return totalUnpaidAmount;
	}
	
	
	/**
	 * 
	 */
	protected void printTaxSummary(){
 
		double gstTotal=0.0;
		advanceTextLine(1);
		 
		final int blockLeft = 10;
		final int blockWidth = 90;
		final int amtLeft = SLNO_FIELD_WIDTH+ITEM_LEVEL_FIELD_WITDH+QTY_FIELD_WIDTH+DESC_FIELD_WIDTH+RATE_FIELD_WIDTH+5;

		
		 for(Integer taxId: itemTaxList.keySet()){

			if(itemTaxList.get(taxId).getTaxAmount()>0){

				final BeanTax tax=PosTaxItemProvider.getInstance().getTaxItem(taxId);

				final double taxAmount=   itemTaxList.get(taxId).getTaxAmount() -itemTaxList.get(taxId).getTaxAmount()*mOrder.getBillDiscountPercentage()/100;
				
				final String taxAmountText=mCurrency + PosCurrencyUtil.format(taxAmount);
				final String taxDescr=  "(" + String.valueOf(tax.getTaxOnePercentage()) + "%対象"; 
				 printTextBlock(blockLeft, blockWidth, mUseAltLanguge?taxDescr:tax.getName(), TextAlign.RIGHT, false);
				printTextBlock(amtLeft, TOTAL_FIELD_WIDTH,taxAmountText + (mUseAltLanguge?")":""), TextAlign.RIGHT, true);
				 
			 
			
			 	gstTotal+=itemTaxList.get(taxId).getTaxAmount();
			 	}

		}

//		if(gstTotal>0){
//
//			printTextBlock(0, 100, "Total TAX :", TextAlign.RIGHT,false);
//			printText(TextAlign.RIGHT, PosCurrencyUtil.format(gstTotal));
//		}

	}
	
	/**
	 * @param item
	 */
	protected void setTaxSummary(BeanOrderDetail item){

		if(item.getSaleItem().getTax()==null) return;

		final BeanTax itemTax=item.getSaleItem().getTax();
		BeanReceiptTaxSummary taxSummary=null;

		if(itemTaxList.containsKey(itemTax.getId()))

			taxSummary=itemTaxList.get(itemTax.getId());

		else{

			taxSummary=new BeanReceiptTaxSummary();
			itemTaxList.put(itemTax.getId(), taxSummary);
		}

		double totalTaxAmo=taxSummary.getTaxAmount();
		double taxableAmo=taxSummary.getTaxableAmount();

		totalTaxAmo+= PosCurrencyUtil.roundTo(item.getSaleItem().getTax().getTaxAmount().getTotalTaxAmount());
		taxSummary.setTaxAmount(totalTaxAmo);
		taxableAmo+=item.getSaleItem().getTax().getTaxAmount().getTaxableAmount();
		taxSummary.setTaxableAmount(taxableAmo);

	}


}
