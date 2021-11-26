/**
 * 
 */
package com.indocosmo.pos.reports.receipts.custom.nz;
 
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
import com.indocosmo.pos.common.utilities.PosSaleItemUtil;
import com.indocosmo.pos.common.utilities.PosStringUtil;
import com.indocosmo.pos.data.beans.BeanBillParam;
import com.indocosmo.pos.data.beans.BeanDiscount;
import com.indocosmo.pos.data.beans.BeanOrderDetail;
import com.indocosmo.pos.data.beans.BeanOrderDetail.OrderDetailItemType;
import com.indocosmo.pos.data.beans.BeanOrderHeader;
import com.indocosmo.pos.data.beans.BeanOrderHeader.PosOrderStatus;
import com.indocosmo.pos.data.beans.BeanOrderPayment;
import com.indocosmo.pos.data.beans.BeanSaleItem;
import com.indocosmo.pos.data.beans.BeanServingTable;
import com.indocosmo.pos.data.beans.BeanTax;
import com.indocosmo.pos.data.providers.shopdb.PosDiscountItemProvider;
import com.indocosmo.pos.data.providers.shopdb.PosPromotionItemProvider;
import com.indocosmo.pos.data.providers.shopdb.PosSaleItemProvider;
import com.indocosmo.pos.data.providers.terminaldb.PosDevReceiptPrinterConfigProvider;
import com.indocosmo.pos.reports.base.PosPaymentReceiptBase;
import com.indocosmo.pos.terminal.devices.PosDeviceManager;
/**
 * @author jojesh
 * 
 */
public class PosPaymentReceiptNZ extends PosPaymentReceiptBase {

	
	/**
	 * User Name column width
	 */
	
	private static final int USER_NAME_FIELD_WITDH =95;
	

	/**
	 * Serial Number column width
	 */
	private static final int SLNO_FIELD_WIDTH = 0;
	/**
	 * Item level indicator
	 */
	private static final int ITEM_LEVEL_FIELD_WITDH =0;
	/**
	 * Description column width
	 */
	//	private static final int DESC_FIELD_WIDTH = 60;
	private static final int DESC_FIELD_WIDTH = 93;

	private static final int QTY_FIELD_WIDTH = 25;
	private static final int RATE_FIELD_WIDTH = 35;
	private static final int TOTAL_FIELD_WIDTH = 40;

	private static final String REPORT_HEADER_LOGO= "logos/receipt_header.png";
	private HashMap<Integer, BeanReceiptTaxSummary> itemTaxList;

	
	/**
	 * 
	 */
	public PosPaymentReceiptNZ(BeanOrderHeader order) {
		super();
		setOrder(order);
//		mFontReceipt = new Font("Ariel", Font.PLAIN, 8);
		preparePaymentInfo(getOrder().getOrderPaymentItems());
		try {
			mUseAltLanguge = PosDeviceManager.getInstance().canUseAltLanguage(getPrinterType());
			//			setOverridePrinterSettings(true);
			
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	/**
	 * 
	 */
	public PosPaymentReceiptNZ() {
//		mFontReceipt = new Font("Ariel", Font.PLAIN, 8);
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
//		setFont(mFontReceipt);
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
		final String hdrLine5 = param.getHeaderLine5();
		final String hdrLine6 = param.getHeaderLine6();
		final String hdrLine7 = param.getHeaderLine7();
		final String hdrLine8 = param.getHeaderLine8();
		final String hdrLine9 = param.getHeaderLine9();
		final String hdrLine10 = param.getHeaderLine10();

		String hdrLine11 = ((isBillPrinting)? "BILL" :"Tax Invoice");
		if  (isBillPrinting && PosEnvSettings.getInstance().getUISetting().useOrderQueueNo()){
			
			String  queueNo;
			if (order.getStatus()==PosOrderStatus.Void )
				queueNo=(order.getQueueNo()==null || order.getQueueNo()=="") ?"0" :order.getQueueNo();
			else
				queueNo=order.getQueueNo();
			hdrLine11  += Integer.parseInt(queueNo) ==0? "" : 
				 " [" + order.getQueueNo()  + "]";
		}
		try {
			BufferedImage bi =ImageIO.read(new File(REPORT_HEADER_LOGO));
			printImage(bi);
		} catch (IOException e) {
			//				e.printStackTrace();
		}

		setFontStyle(Font.BOLD);
		setFontSize(8.5f);
		printText(TextAlign.CENTER, hdrLine1);

		setFont(mFontReceipt);
		printText(TextAlign.CENTER, hdrLine2,true,true);
		printText(TextAlign.CENTER, hdrLine3);
		printText(TextAlign.CENTER, hdrLine4);
		printText(TextAlign.CENTER, hdrLine5);
		printText(TextAlign.CENTER, hdrLine6);
		printText(TextAlign.CENTER, hdrLine7);
		printText(TextAlign.CENTER, hdrLine8);
		printText(TextAlign.CENTER, hdrLine9);
		printText(TextAlign.CENTER, hdrLine10);

		if (!isBillPrinting)
		{
			final String hdrGST ="GST: " + PosEnvSettings.getInstance().getShop().getCompanyTaxNo();
			printText (TextAlign.CENTER, hdrGST);
		}
		setFontStyle(Font.BOLD);
		setFontSize(10.0f);
		printText(TextAlign.CENTER, hdrLine11);
		//		printText(TextAlign.CENTER,"(Partial Payment)");
		
		
		setFont(mFontReceipt);
		/*
		 * Prints the sub headers
		 */
		//		final String hdrSubLine1 = "Loc. "
		//				+ PosEnvSettings.getInstance().getShop().getName();
		//		printTextLine(hdrSubLine1);

		
		String billNo = "" ;
		if (isBillPrinting)
			billNo="Bill# "  + PosOrderUtil.getShortOrderIDFromOrderID(order.getOrderId());
		else
			billNo="Receipt# "  + order.getInvoiceNo();
		
		final String billDate =  PosDateUtil.getDateTime();
		final String orderNo =  "Order# "  + PosOrderUtil.getShortOrderIDFromOrderID(order.getOrderId());
		
//		final String printedAt = "Printed at : "+ PosEnvSettings.getInstance().getStation().getName();
//		final String orderStatus = "Status : " + order.getStatus().getDisplayText();

//		printText(TextAlign.LEFT, printedAt,false);
//		printText(TextAlign.RIGHT, orderStatus);

		String footerLine_ServiceType = "Service Type : ";
		String footerLine_Table = " ";
		String footerLine_Covers="";
		String footerLine_ServedBy = "Served By :";
		footerLine_ServiceType += order.getOrderServiceType()
				.getDisplayText();

		switch (order.getOrderServiceType()) {
		case HOME_DELIVERY:
			footerLine_ServedBy += (order.getServedBy() != null) ? order
					.getServedBy().getFirstName() : "";
					break;
		case TABLE_SERVICE:
			ArrayList<BeanServingTable> tables = PosOrderUtil.getAllServingTables(order);
			footerLine_Table = "TABLE[";
			for (BeanServingTable tbl : tables) {
				footerLine_Table += tbl.getName() + ",";
			}
			footerLine_Table = footerLine_Table.substring(0, footerLine_Table.length() - 1) + "]";
			footerLine_Covers = "Covers : "+order.getCovers();
			footerLine_ServedBy += (order.getServedBy() != null) ? order
					.getServedBy().getFirstName() : "";
					break;
		case TAKE_AWAY:
			footerLine_ServedBy += order.getUser().getName();
			break;
		}


	 	printText(TextAlign.LEFT , footerLine_Table,true,true);
	 

		if (footerLine_ServedBy.trim().length() > 0) {
			printTextBlock(0, USER_NAME_FIELD_WITDH, footerLine_ServedBy,  TextAlign.LEFT, false);
			
//			if (footerLine_ServedBy.trim().length() >25 )
//				printText(TextAlign.LEFT, footerLine_ServedBy.substring(0,24),false);
//			else
//				printText(TextAlign.LEFT, footerLine_ServedBy,false);
		}
		printText(TextAlign.RIGHT , billNo);
		
		if (isBillPrinting){
			printText(TextAlign.LEFT, footerLine_Covers,false);
		}
		else{
			printText(TextAlign.LEFT, orderNo,false);
		}
		printText(TextAlign.RIGHT, billDate);

		drawLine(getDefaultHeaderSeparatorStyle());
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

		if(order.getOrderDetailItems()==null || order.getOrderDetailItems().size()<=0) return;

		printDetailsHeader();
		
		final Font dtlsFont=PosEnvSettings.getInstance().getPrintSettings().getPaymentReceiptSettings().getItemDetailFont();
		
		setFont(dtlsFont);
		
		int slNo = 1;
		for (BeanOrderDetail dtl : order.getOrderDetailItems()) {

			if (order.getStatus()!=PosOrderStatus.Void && dtl.isVoid())	continue;
			slNo=printDetailItem(slNo, dtl,true);

		}
		
		advanceLine(1);
		//drawLine(getDefaultDetailSeparatorStyle());
		printDashedLine();
		
		setFont(mFontReceipt);
		
	}

	/**
	 * @param slNo
	 * @param dtlItem
	 * @param printSub
	 * @return
	 */
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

						if(!item.isVoid())

							printDetailItem(slNo++, item);
					}
				}
			}

			/**If has extras print them**/
			if(dtlItem.isExtraItemsSelected()){

				for(ArrayList<BeanOrderDetail> subItemList:dtlItem.getExtraItemList().values()){

					for(BeanOrderDetail item:subItemList){

						if(!item.isVoid())

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
		printTextBlock(left, QTY_FIELD_WIDTH, "Qty", TextAlign.LEFT, false);

		/** The Description field **/
		left += QTY_FIELD_WIDTH + gap;
		printTextBlock(left, DESC_FIELD_WIDTH, "Description", TextAlign.CENTER,
				false);

		/** The Rate field **/
		left += DESC_FIELD_WIDTH + gap;
		printTextBlock(left, RATE_FIELD_WIDTH, " ", TextAlign.CENTER, false);

		/** The Total Amount field **/
		left += RATE_FIELD_WIDTH + gap;
		printTextBlock(left, TOTAL_FIELD_WIDTH, "Total", TextAlign.CENTER, true);
		printDashedLine();
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
		//left += SLNO_FIELD_WIDTH + gap;
		//printTextBlock(left, ITEM_LEVEL_FIELD_WITDH,
		//		PosPrintingUtil.getItemLevelIndicator(dtl), TextAlign.CENTER, false);

		/** The Quantity field **/
		left += SLNO_FIELD_WIDTH + gap;
		if(dtl.getItemType()==OrderDetailItemType.SALE_ITEM ){ 
			printTextBlock(left, QTY_FIELD_WIDTH,
					PosNumberUtil.format(PosOrderUtil.getItemQuantity(dtl)), TextAlign.LEFT, false);
		}
//		else{
//			printTextBlock(left, QTY_FIELD_WIDTH," ", TextAlign.LEFT, false);
//		}
		
		String indiChar="";
		if(dtl.getItemType()!=OrderDetailItemType.SALE_ITEM ) indiChar="   - ";
		/** The Description field **/
		left += QTY_FIELD_WIDTH + gap;
		printTextBlock(left, DESC_FIELD_WIDTH, indiChar +  PosSaleItemUtil.getItemNameToPrint(dtl.getSaleItem(), mUseAltLanguge) , TextAlign.LEFT, false);

		/** The Rate field **/
		left += DESC_FIELD_WIDTH + gap;
		printTextBlock(left, RATE_FIELD_WIDTH,"@ " +
				PosCurrencyUtil.format(PosSaleItemUtil.getItemFixedPrice(dtl.getSaleItem())), 
				TextAlign.LEFT, false);
		
		/** The Total Amount field **/
		left += RATE_FIELD_WIDTH + gap;
		printTextBlock(left, TOTAL_FIELD_WIDTH,
				PosCurrencyUtil.format(PosOrderUtil
						.getTotalItemPrice(dtl)), TextAlign.RIGHT, true);
		
		/** Print modifiers */
		if(PosEnvSettings.getInstance().getPrintSettings().getPaymentReceiptSettings().isModifiersVisible())
			printAttributes(dtl.getSaleItem());
		
		final BeanDiscount discount=saleItem.getDiscount();
		if(discount!=null && 
				!discount.getCode().equals(PosDiscountItemProvider.NONE_DISCOUNT_CODE) && 
				!discount.getCode().equals(PosPromotionItemProvider.DEF_PROMO_CODE)){
			String discountAmount="-"+PosCurrencyUtil.format(PosOrderUtil.getTotalDiscountAmount(dtl));
			String discountName="      LESS "+ discount.getName() ;
			int atX=SLNO_FIELD_WIDTH+gap+ QTY_FIELD_WIDTH + gap;
			final int discDescWidth=DESC_FIELD_WIDTH +  gap + RATE_FIELD_WIDTH     ;
			printTextBlock(atX,  discDescWidth,discountName, TextAlign.LEFT, false);
			atX+=discDescWidth + gap;
			printTextBlock(atX,TOTAL_FIELD_WIDTH,discountAmount,TextAlign.RIGHT,true);
		}

		setTaxSummary(dtl);
	}
	
	/**
	 * @param beanSaleItem
	 */
	private void printAttributes(BeanSaleItem beanSaleItem) {
		
		for(int i=0;i<5;i++){
			if(beanSaleItem.getAttribSelectedOption(i)!=null&&beanSaleItem.getAttribSelectedOption(i).trim().length()!=0&&!beanSaleItem.getAttribSelectedOption(i).trim().equalsIgnoreCase(PosSaleItemProvider.DEFAULT_ATTRIBUTE_OPTION)){
				printText(SLNO_FIELD_WIDTH+ITEM_LEVEL_FIELD_WITDH+QTY_FIELD_WIDTH+15,DESC_FIELD_WIDTH,TextAlign.LEFT,beanSaleItem.getAttributeName(i)+" :- "+beanSaleItem.getAttribSelectedOption(i).trim(),true);
			}
		}
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

		totalTaxAmo+=item.getSaleItem().getTax().getTaxAmount().getTotalTaxAmount();
		taxSummary.setTaxAmount(totalTaxAmo);
		taxableAmo+=item.getSaleItem().getTax().getTaxAmount().getTaxableAmount();
		taxSummary.setTaxableAmount(taxableAmo);

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

		final int blockLeft = 20;
		final int blockWidth = 100;

		double totalGst =0;
		totalGst=order.getTotalTax1()+
				order.getTotalTax2()+
				order.getTotalTax3()+
				order.getTotalServiceTax()+
				order.getTotalGST()-
				order.getBillTaxAmount();
		final String totalDtlAmtText = PosCurrencyUtil.format(order
				.getTotalAmount());
		printTextBlock(blockLeft, blockWidth, "SUB-TOTAL  ", TextAlign.RIGHT, false);
		printText(TextAlign.RIGHT, totalDtlAmtText);

		if (isBillPrinting){
			advanceLine(1);
			drawLine(getDefaultBillSummarySeparatorStyle());
		}
		
		final String totalDiscAmtText = PosCurrencyUtil.format(order
				.getBillDiscountAmount());

		if (Double.parseDouble(totalDiscAmtText)  > 0) {
			
			printTextBlock(blockLeft, blockWidth, "Bill Discount :",
					TextAlign.RIGHT, false);
			printText(TextAlign.RIGHT, totalDiscAmtText);
		}


		Double  roundingAdjustment=order.getRoundAdjustmentAmount();
		String roundingAdjustmentText= PosCurrencyUtil.format(roundingAdjustment);
		if(Double.parseDouble(roundingAdjustmentText)!=0){ 

			printTextBlock(blockLeft, blockWidth, "Rounding adjustment :", TextAlign.RIGHT,
					false);
			printText(TextAlign.RIGHT, roundingAdjustmentText);
		}

		if(totalSplitPartAdjPayment!=0){ 

			printTextBlock(blockLeft, blockWidth, "Part. Pay adjustment :", TextAlign.RIGHT,
					false);
			printText(TextAlign.RIGHT, PosCurrencyUtil.format(totalSplitPartAdjPayment));
		}

		if(totalSplitAdjustment!=0){ 

			printTextBlock(blockLeft, blockWidth, "Split adjustment :", TextAlign.RIGHT,
					false);
			printText(TextAlign.RIGHT, PosCurrencyUtil.format(totalSplitAdjustment));
		}

		if(totalGst>0){


			advanceLine(2);
			printTextBlock(blockLeft, blockWidth, "GST Included (15%):", TextAlign.RIGHT,false);
			printText(TextAlign.RIGHT, PosCurrencyUtil.format(totalGst));
		
	}

		
		final double netPayble=PosCurrencyUtil.roundTo(order.getTotalAmount())
				+roundingAdjustment
				-order.getBillDiscountAmount()
				-totalSplitPartAdjPayment
				+totalSplitAdjustment;

		String totalAmtText = PosCurrencyUtil.format(netPayble);
		String currencyAmoText = PosEnvSettings.getInstance()
				.getCurrencySymbol() + totalAmtText;

		final String  unpaidAmtText= PosCurrencyUtil.format(getetUnpaidAmount());
		
		setFontStyle(Font.BOLD);
		setFontSize(9f);

		advanceLine(2);
	 	printTextBlock(blockLeft, blockWidth,  ( isBillPrinting?"AMOUNT DUE : ": "Total Amount : "), TextAlign.RIGHT,	false);
		printText(TextAlign.RIGHT,  ( isBillPrinting? unpaidAmtText: totalAmtText));

		advanceLine(2);
		setFont(mFontReceipt); 

		

	}
	
	/**
	 * 
	 */
	private void printRemarks(){
		
		final String orderRemarks=getOrder().getRemarks();
		
		if(orderRemarks!=null && orderRemarks.trim().length()>0){
			
			setFontStyle(Font.BOLD);
			setFontSize(9f);
			
			printText(TextAlign.LEFT, "Remarks:");
			
			setFont(mFontReceipt);
			
			final String[] remarksToPrint=PosStringUtil.getWrappedLines(orderRemarks, 52);
			
			for(String remark:remarksToPrint){
				
				printText(TextAlign.LEFT, remark);
			}
			
			printText(TextAlign.CENTER, " ",true,true);
		}
	}

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

		final int blockLeft = 0;
		final int blockWidth = 100;
		double balance = 0 ;
		double totalPayed=0;

		totalPayed=totalCashPayment+totalCardPayment+totalCompanyPayment+totalCouponPayment;
		balance=totalBalancePayment+totalCouponBalancePayment;
		
		advanceLine(2);
		
		if(totalPayed>0){

			printTextBlock(0, 100, " ", TextAlign.LEFT,true);
			//printTextBlock(0, 100, "PAYMENT SUMMARY", TextAlign.LEFT,true);
			//drawLine(getDefaultPaymentSummarySeparatorStyle());
			printDashedLine();

			if(order.getStatus().equals(PosOrderStatus.Void)){

				setFontStyle(Font.BOLD);
				setFontSize(10f);
				printText(TextAlign.CENTER, "VOID BILL");
				setFont(mFontReceipt);
			}


					
			printPaymentInfo("CASH :", totalCashPayment, blockLeft, blockWidth);
			printPaymentInfo("CARD :", totalCardPayment, blockLeft, blockWidth);
			printPaymentInfo("COMPANY :", totalCompanyPayment, blockLeft, blockWidth);
			printPaymentInfo("VOUCHER :", totalCouponPayment, blockLeft, blockWidth);
			
			int noOfPayModes=0;
			if (totalCashPayment>0) ++noOfPayModes;
			if (totalCardPayment>0) ++noOfPayModes;
			if (totalCompanyPayment>0) ++noOfPayModes;
			if (totalCouponPayment>0) ++noOfPayModes;
			

//			if(totalCashPayment>0 || totalCardPayment>0 || totalCompanyPayment>0 || totalCouponPayment>0)
//				printSingleLine();

			if (noOfPayModes>1){
				printTextBlock(blockLeft, blockWidth, "Total Paid :", TextAlign.RIGHT ,false);
				printText(TextAlign.RIGHT,  PosCurrencyUtil.format(totalPayed) );
			}
			
			if (balance!=0)
			{
				printTextBlock(blockLeft, blockWidth, "Change :", TextAlign.RIGHT,false);
				printText(TextAlign.RIGHT,  PosCurrencyUtil.format(balance) );
			}
			//printTextBlock(blockLeft, blockWidth, PosNumberUtil.formatNumber(balance), TextAlign.RIGHT,	true);
			
//			final String  unpaidAmtText= PosCurrencyUtil.format(unpaidAmt);
//			if(  Double.parseDouble(unpaidAmtText)!=0){
//
//				//drawLine(getDefaultPaymentSummarySeparatorStyle());
//
//				printTextBlock(blockLeft, blockWidth, "Unpaid : ", TextAlign.RIGHT,false);
//				printText(TextAlign.RIGHT,  unpaidAmtText);
//				//printTextBlock(blockLeft, blockWidth, PosNumberUtil.formatNumber(getetUnpaidAmount()), TextAlign.RIGHT,	true);
//			}

		//	drawLine(getDefaultPaymentSummarySeparatorStyle());

			if(order.getStatus().equals(PosOrderStatus.Refunded)){

				refundDetails(order);

			}
		}
		//else
			//drawLine(getDefaultPaymentSummarySeparatorStyle());

	}

	/**
	 * @param paymenTitle
	 * @param amount
	 * @param blockLeft
	 * @param blockWidth
	 */
	private boolean printPaymentInfo(String paymenTitle, double amount,int blockLeft, int blockWidth){

		boolean isPrinted=false;

		if(amount!=0){
			printTextBlock(blockLeft, blockWidth, paymenTitle, TextAlign.RIGHT,false);
			printText(TextAlign.RIGHT,  PosCurrencyUtil.format(amount) );
			
			//printTextBlock(blockLeft, blockWidth, PosNumberUtil.formatNumber(amount), TextAlign.RIGHT,true);
			isPrinted=true;
		}

		return isPrinted;
	}

	/**
	 * @param paymentDetails
	 */
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
			
			if(payment.isRepayment()) continue;
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
					printTextBlock(blockLeft, blockWidth, "CASH :", TextAlign.LEFT,
							false);
					printTextBlock(blockLeft, blockWidth, PosCurrencyUtil.format(payment.getPaidAmount()), TextAlign.RIGHT,
							true);
				}
				break;
			case Card:
				if (payment.getPaidAmount() > 0&&payment.isRepayment()){

					printTextBlock(blockLeft, blockWidth, "CARD :", TextAlign.LEFT,
							false);
				printTextBlock(blockLeft, blockWidth, PosCurrencyUtil.format(payment.getPaidAmount()), TextAlign.RIGHT,
						true);	
				}
				break;
			case CashOut:
				if(payment.getPaidAmount() > 0&&payment.isRepayment())

					break;	
			case Company:
				if (payment.getPaidAmount() > 0&&payment.isRepayment()){

					printTextBlock(blockLeft, blockWidth, "COMPANY :", TextAlign.LEFT,
							false);
				printTextBlock(blockLeft, blockWidth, PosCurrencyUtil.format(payment.getPaidAmount()), TextAlign.RIGHT,
						true);
				}
				break;
			case Coupon:
				if (payment.getVoucherCount()>0&&payment.isRepayment()){

					printTextBlock(blockLeft, blockWidth, "VOUCHER :", TextAlign.LEFT,
							false);
					printTextBlock(blockLeft, blockWidth, PosCurrencyUtil.format(payment.getPaidAmount()), TextAlign.RIGHT,
							true);
				}
				break;
				//			case Balance:
				//				if (payment.getPaidAmount()>0&&payment.isRepayment()){
				//					balance =payment.getPaidAmount();
				//					printTextBlock(blockLeft, blockWidth, "BALANCE :", TextAlign.LEFT,
				//					false);
				//			printTextBlock(blockLeft, blockWidth, PosNumberUtil.formatNumber(payment.getPaidAmount()), TextAlign.RIGHT,
				//					true);
				//				}
				//				break;
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
		
		printRemarks();
		
		advanceLine(2);
		
		if (isBillPrinting)
		{
			final String footerLine_bill = "--THANK YOU--";
			printText(TextAlign.CENTER, footerLine_bill,true);
			return;
			
		}
		/** footer information **/
		final String footerLine_1 = param.getFooterLine1();
		final String footerLine_2 = param.getFooterLine2();
		final String footerLine_3 = param.getFooterLine3();
		final String footerLine_4 = param.getFooterLine4();
		final String footerLine_5 = param.getFooterLine5();
		final String footerLine_6 = param.getFooterLine6();
		final String footerLine_7 = param.getFooterLine7();
		final String footerLine_8 = param.getFooterLine8();
		final String footerLine_9 = param.getFooterLine9();
		final String footerLine_10 = param.getFooterLine10();

		printText(TextAlign.CENTER, footerLine_1,true,true);
		printText(TextAlign.CENTER, footerLine_2,true,true);
		printText(TextAlign.CENTER, footerLine_3,true,true);
		printText(TextAlign.CENTER, footerLine_4,true,true);
		printText(TextAlign.CENTER, footerLine_5,true,true);
		printText(TextAlign.CENTER, footerLine_6,true,true);
		printText(TextAlign.CENTER, footerLine_7,true,true);
		printText(TextAlign.CENTER, footerLine_8,true,true);
		printText(TextAlign.CENTER, footerLine_9,true,true);
		printText(TextAlign.CENTER, footerLine_10,true,true);


		printText(TextAlign.CENTER, ".",true,true);
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
		totalSplitAdjustment=currentBillInfo.getSplitPayAdjustment();

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


		/**
		 * Unpaid will be printed only in whole bill
		 * 
		 * if(mPrePaymentReceipt && mOrder.getStatus()!=PosOrderStatus.Closed)
		 *	totalUnpaidAmount=currentBillInfo.getUnPaidAmount();
		 * 
		 */
		if(mPrePaymentReceipt )

			totalUnpaidAmount=0;
		else
			/**
			 * In case of normal payment calculate it from the payments
			 */
			totalUnpaidAmount=PosOrderUtil.getTotalBalanceOnBill(mOrder);

		return totalUnpaidAmount;
	}


}
