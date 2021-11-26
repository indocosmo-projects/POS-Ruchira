/**
 * 
 */
package com.indocosmo.pos.reports.receipts;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

import javax.imageio.ImageIO;

import com.indocosmo.pos.common.PosEnvSettings;
import com.indocosmo.pos.common.beans.BeanBillPaymentSummaryInfo;
import com.indocosmo.pos.common.beans.BeanReceiptTaxSummary;
import com.indocosmo.pos.common.beans.settings.ui.BeanUISetting;
import com.indocosmo.pos.common.enums.PaymentReceiptType;
import com.indocosmo.pos.common.utilities.PosCurrencyUtil;
import com.indocosmo.pos.common.utilities.PosDateUtil;
import com.indocosmo.pos.common.utilities.PosFileUtils;
import com.indocosmo.pos.common.utilities.PosNumberUtil;
import com.indocosmo.pos.common.utilities.PosOrderUtil;
import com.indocosmo.pos.common.utilities.PosPrintingUtil;
import com.indocosmo.pos.common.utilities.PosSaleItemUtil;
import com.indocosmo.pos.common.utilities.PosStringUtil;
import com.indocosmo.pos.common.utilities.PosTaxUtil;
import com.indocosmo.pos.common.utilities.PosUomUtil;
import com.indocosmo.pos.data.beans.BeanBillParam;
import com.indocosmo.pos.data.beans.BeanDiscount;
import com.indocosmo.pos.data.beans.BeanOrderDetail;
import com.indocosmo.pos.data.beans.BeanOrderHeader;
import com.indocosmo.pos.data.beans.BeanOrderHeader.PosOrderStatus;
import com.indocosmo.pos.data.beans.BeanOrderPayment;
import com.indocosmo.pos.data.beans.BeanSaleItem;
import com.indocosmo.pos.data.beans.BeanServingTable;
import com.indocosmo.pos.data.beans.BeanTax;
import com.indocosmo.pos.data.providers.shopdb.PosCustomerTypeProvider;
import com.indocosmo.pos.data.providers.shopdb.PosDiscountItemProvider;
import com.indocosmo.pos.data.providers.shopdb.PosPromotionItemProvider;
import com.indocosmo.pos.data.providers.shopdb.PosSaleItemProvider;
import com.indocosmo.pos.data.providers.shopdb.PosTaxAmountObject;
import com.indocosmo.pos.data.providers.shopdb.PosTaxItemProvider;
import com.indocosmo.pos.data.providers.shopdb.PosTaxItemProvider.TaxCalculationMethod;
import com.indocosmo.pos.reports.base.PosPaymentReceiptBase;
import com.indocosmo.pos.reports.base.PosReportPageFormat;
import com.indocosmo.pos.reports.base.PosPrintableBase.TextAlign;
import com.indocosmo.pos.terminal.devices.PosDeviceManager;

/**
 * @author jojesh
 * 
 */
public class PosDefaultPaymentReceipt extends PosPaymentReceiptBase {
	
	private static final String PROPERTY_FILE="pos-terminal-receipt-pymt-default.properties"; 

	/**
	 * Serial Number column width
	 */
	private int SLNO_FIELD_WIDTH ;
	/**
	 * Item level indicator
	 */
	private int ITEM_LEVEL_FIELD_WITDH;
	private int QTY_FIELD_WIDTH;
	private int RATE_FIELD_WIDTH ;
	private int TOTAL_FIELD_WIDTH;
	
	private boolean showPaymentSummary=true;
	private boolean showBarcode=true;
	private boolean showAmountInWords=true;
	private boolean showQueueNo=false;

	private static final int RECEIPT_SERVEDBY_WIDTH = 110;
	
	

	private static final String REPORT_HEADER_LOGO= "logos/receipt_header.png";
	private HashMap<Integer, BeanReceiptTaxSummary> itemTaxList;
	
	private int DESC_FIELD_WIDTH=0;
	private String EXCLUSIVE_TAX_SYMBOL="";
	

	private double totalExclusiveTaxAmt = 0.0;
	private double totalInclusiveTaxAmt = 0.0;
	private double subTotal = 0.0; 
	final static  String devModeText="DEVELOPMENT MODE, INDOCOSMO SYSTEMS";
	
	/**
	 * 
	 */
	public PosDefaultPaymentReceipt(BeanOrderHeader order) throws IOException {
		super();
		setOrder(order);
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
	public PosDefaultPaymentReceipt() {
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

		loadDefaultPaymentReceiptSettings();
		DESC_FIELD_WIDTH=getPosReportPageFormat().getImageableWidthInPixcel()-(
				
				SLNO_FIELD_WIDTH+1+
				ITEM_LEVEL_FIELD_WITDH+1+
				QTY_FIELD_WIDTH+1+
				RATE_FIELD_WIDTH+1+
				TOTAL_FIELD_WIDTH
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
		final String hdrLine5 = param.getHeaderLine5();
		final String hdrLine6 = param.getHeaderLine6();
		final String hdrLine7 = param.getHeaderLine7();
		final String hdrLine8 = param.getHeaderLine8();
		final String hdrLine9 = param.getHeaderLine9();
		final String hdrLine10 = param.getHeaderLine10();

		
		String hdrLine11 = ((isBillPrinting)? "BILL" :"RECEIPT");
		if  (isBillPrinting && PosEnvSettings.getInstance().getUISetting().useOrderQueueNo()){
			
			String  queueNo;
			if (order.getStatus()==PosOrderStatus.Void )
				queueNo=(order.getQueueNo()==null || order.getQueueNo()=="") ?"0" :PosOrderUtil.getFormatedOrderQueueNo(order);
			else
				queueNo=PosOrderUtil.getFormatedOrderQueueNo(order);
			hdrLine11  += (queueNo =="0")? "" : 
				 " [" + queueNo  + "]";
		}
	
		
		try {
			BufferedImage bi =ImageIO.read(new File(REPORT_HEADER_LOGO));
			printImage(bi);
		} catch (IOException e) {
			//				e.printStackTrace();
		}

		setFontStyle(Font.BOLD);
		setFontSize(12f);
		
		if(PosEnvSettings.getInstance().isDevelopmentMode()) {
			
			printText(TextAlign.CENTER, "DEVELOPMENT MODE");
			printText(TextAlign.CENTER, "DEVELOPMENT MODE");
			printText(TextAlign.CENTER, "DEVELOPMENT MODE");
			
		}
		
		printText(TextAlign.CENTER, hdrLine1);
		setFont(mFontReceipt);
		printText(TextAlign.CENTER, hdrLine2);
		printText(TextAlign.CENTER, hdrLine3);
		printText(TextAlign.CENTER, hdrLine4);
		printText(TextAlign.CENTER, hdrLine5);
		printText(TextAlign.CENTER, hdrLine6);
		printText(TextAlign.CENTER, hdrLine7);
		printText(TextAlign.CENTER, hdrLine8);
		printText(TextAlign.CENTER, hdrLine9);
		printText(TextAlign.CENTER, hdrLine10);

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
		String refBillNo="";
		if (isBillPrinting){
			billNo="Bill. "  + PosOrderUtil.getShortOrderIDFromOrderID(order.getOrderId());
//					(PosEnvSettings.getInstance().getUISetting().useOrderQueueNo()?PosOrderUtil.getFormatedOrderQueueNo(order):
//				PosOrderUtil.getShortOrderIDFromOrderID(order.getOrderId()));
		}else{
			billNo="Invoice No. "  + order.getInvoiceNo();
			refBillNo="Ord. "  +PosOrderUtil.getShortOrderIDFromOrderID(order.getOrderId());
//			(PosEnvSettings.getInstance().getUISetting().useOrderQueueNo()?PosOrderUtil.getFormatedOrderQueueNo(order):
//				PosOrderUtil.getShortOrderIDFromOrderID(order.getOrderId()));
		}
//		final String billNo = "Bill. " + PosOrderUtil.getShortOrderIDFromOrderID(order.getOrderId());
//		String billDate = "Dt. " + PosDateUtil.formatLocal(isBillPrinting?order.getOrderDate():
//			(order.getClosingDate()==null?PosDateUtil.getDateTime():order.getClosingDate()));
//		billDate+= " ";
//		billDate+=PosDateUtil.format(PosDateUtil.SHORT_TIME_FORMAT_12, isBillPrinting?order.getOrderTime(): 
//					(order.getClosingTime()==null?PosDateUtil.getDateTime():order.getClosingTime()));  
		
		
		String billDate = "Date : " + PosDateUtil.formatLocal(isBillPrinting?order.getOrderDate():
			(order.getClosingDate()==null?PosDateUtil.getDateTime():order.getClosingDate()));
	 
		String billTime= "Time : " + PosDateUtil.format(PosDateUtil.SHORT_TIME_FORMAT_12, isBillPrinting?order.getOrderTime(): 
					(order.getClosingTime()==null?PosDateUtil.getDateTime():order.getClosingTime()));  
	
		
		final String printedAt = "Printed at: "+ PosEnvSettings.getInstance().getStation().getName();
		final String orderStatus = "Status : " + order.getStatus().getDisplayText();

		
		printText(TextAlign.LEFT, billNo, false);
		printText(TextAlign.RIGHT, billDate);
		printText(TextAlign.LEFT, printedAt,false);
		printText(TextAlign.RIGHT, billTime);
		
		
		
//		printText(TextAlign.LEFT, billNo, false);
//		printText(TextAlign.RIGHT, printedAt);
//
// 
//		printText(TextAlign.LEFT,billDate, false,true);
//		printText(TextAlign.RIGHT, refBillNo,true,true);
		
		String footerLine_ServiceType = "Service Type : ";
		String footerLine_Covers="";
		final String footerLine_ServedBy = 
						((isBillPrinting || order.getClosedBy()==null)?
						"Served By : "+ order.getUser().getName():
						"Closed By : "+ order.getClosedBy().getName());
		
		footerLine_ServiceType += order.getOrderServiceType()
				.getDisplayText();

	
		switch (order.getOrderServiceType()) {
		case HOME_DELIVERY:
//			footerLine_ServedBy += (order.getServedBy() != null) ? order
//					.getServedBy().getFirstName() : "";
					break;
		case TABLE_SERVICE:
		 
			footerLine_ServiceType += PosOrderUtil.getAllServingTableName(order);
			if(order.getCovers()>0)
				footerLine_Covers = "Covers : "+order.getCovers();
//			footerLine_ServedBy += (order.getServedBy() != null) ? order
//					.getServedBy().getFirstName() : "";
					break;
		case TAKE_AWAY:
//			footerLine_ServedBy += order.getUser().getName();
			break;
		default:
//			footerLine_ServedBy += order.getUser().getName();
			break;
		}

		if (footerLine_ServiceType.trim().length() > 0) {

			printText(TextAlign.LEFT, footerLine_ServiceType,(footerLine_Covers.trim().length()>0)?false:true);
			printText(TextAlign.RIGHT, footerLine_Covers);
		}
 	
		

		if (footerLine_ServedBy.trim().length() > 0) {
			
			if (isBillPrinting)
				printText(TextAlign.LEFT, footerLine_ServedBy,true);
			else
				printTextBlock(0, RECEIPT_SERVEDBY_WIDTH, footerLine_ServedBy, TextAlign.LEFT, false);
			
		}
		
		if (!isBillPrinting)
			printText(TextAlign.RIGHT, refBillNo,true);
		
	 
		if(!order.getStatus().equals(PosOrderStatus.Closed))
			printText(TextAlign.LEFT, orderStatus);
		
		 
		
		if(PosEnvSettings.getInstance().isDevelopmentMode()) {
			
			printText(TextAlign.CENTER, devModeText);
			printText(TextAlign.CENTER, devModeText);
			printText(TextAlign.CENTER, devModeText);
			
		}
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

		totalExclusiveTaxAmt=0;
		totalInclusiveTaxAmt=0;
		subTotal=0;
		
		itemTaxList=new HashMap<Integer, BeanReceiptTaxSummary>();

		if(order.getOrderDetailItems()==null || order.getOrderDetailItems().size()<=0) return;

		printDetailsHeader();
		
		final Font dtlsFont=PosEnvSettings.getInstance().getPrintSettings().getPaymentReceiptSettings().getItemDetailFont();
		setFont(dtlsFont);

		int slNo = 1;
		for (BeanOrderDetail dtl : order.getOrderDetailItems()) {

			if (dtl.isVoid())	continue;
			slNo=printDetailItem(slNo, dtl,true);

		}
		
		advanceLine(1);
		drawLine(getDefaultDetailSeparatorStyle());
		
		setFont(mFontReceipt);
		PosOrderUtil.setExtraChargeTaxSummary(mOrder,itemTaxList);
		
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
		printTextBlock(left, RATE_FIELD_WIDTH, "Rate", TextAlign.CENTER, false);

		/** The Total Amount field **/
		left += RATE_FIELD_WIDTH + gap;
		printTextBlock(left, TOTAL_FIELD_WIDTH, "Total", TextAlign.CENTER, true);
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
		left += ITEM_LEVEL_FIELD_WITDH + gap;
		printTextBlock(left, QTY_FIELD_WIDTH,
				PosUomUtil.format(PosOrderUtil.getItemQuantity(dtl),dtl.getSaleItem().getUom()), TextAlign.LEFT, false);

		/** The Description field **/
		String taxSymbol="";
		if(PosEnvSettings.getInstance().getPrintSettings().getPaymentReceiptSettings().getReceiptType()==PaymentReceiptType.Normal)
			taxSymbol=(!saleItem.getTax().getCode().equalsIgnoreCase("NOTAX"))?
					(saleItem.getTaxCalculationMethod() == TaxCalculationMethod.ExclusiveOfTax?EXCLUSIVE_TAX_SYMBOL:""):"";
	
		left += QTY_FIELD_WIDTH + gap;
		printTextBlock(left, DESC_FIELD_WIDTH, PosSaleItemUtil.getItemNameToPrint(dtl.getSaleItem(), mUseAltLanguge) , TextAlign.LEFT, false);

		double rate ;
		PosTaxAmountObject taxAmount;
		double taxableAmt=0;
		double exclusivePriceVariant=0;
		rate=PosOrderUtil.getItemFixedPrice(dtl);
		taxableAmt=rate;
		switch(PosEnvSettings.getInstance().getPrintSettings().getPaymentReceiptSettings().getReceiptType()){
			
		case Inclusive:
			
			 if(!saleItem.getTax().getCode().equalsIgnoreCase("NOTAX")){
				if(dtl.getSaleItem().getTaxCalculationMethod()==TaxCalculationMethod.ExclusiveOfTax){
					  taxAmount=PosTaxUtil.calculateTaxes(dtl.getSaleItem().getTax(), rate); 
					rate= PosCurrencyUtil.roundTo(rate + taxAmount.getTaxOneAmount() +
							taxAmount.getTaxTwoAmount() + taxAmount.getTaxThreeAmount() + taxAmount.getServiceTaxAmount());
				} 
			 }
			 totalInclusiveTaxAmt+=	PosCurrencyUtil.roundTo(PosOrderUtil.getTotalTaxAmount(dtl));
			break;
		case Exclusive:
			
			 if(!saleItem.getTax().getCode().equalsIgnoreCase("NOTAX")){
				if(dtl.getSaleItem().getTaxCalculationMethod()==TaxCalculationMethod.InclusiveOfTax){
					final double taxRate=PosTaxUtil.getTotalTaxRate(dtl.getSaleItem().getTax());
					taxableAmt=(PosOrderUtil.getItemFixedPrice(dtl)*dtl.getSaleItem().getQuantity())/(1+taxRate/100);
					rate=PosOrderUtil.getItemFixedPrice(dtl)/(1+taxRate/100);
						
//					  taxAmount=PosTaxUtil.calculateTaxes(dtl.getSaleItem().getTax(), taxableAmt); 
//					  final double tempTaxableAmt=PosCurrencyUtil.roundTo(rate*dtl.getSaleItem().getQuantity()) -(
//							  PosCurrencyUtil.roundTo(taxAmount.getTaxOneAmount()*dtl.getItemSplitShare())+
//							    PosCurrencyUtil.roundTo(taxAmount.getTaxTwoAmount()*dtl.getItemSplitShare())+
//							    PosCurrencyUtil.roundTo(taxAmount.getTaxThreeAmount()*dtl.getItemSplitShare()));
//					  exclusivePriceVariant=PosCurrencyUtil.roundTo(tempTaxableAmt)-PosCurrencyUtil.roundTo(taxableAmt);
					 
				} 
			 }
			 totalExclusiveTaxAmt+=	PosCurrencyUtil.roundTo(PosOrderUtil.getTotalTaxAmount(dtl));
			break;
//			rate=PosOrderUtil.getItemFixedPrice(dtl);
//			 if(!saleItem.getTax().getCode().equalsIgnoreCase("NOTAX")){
//				 
//				if(dtl.getSaleItem().getTaxCalculationMethod()==TaxCalculationMethod.InclusiveOfTax){
//					final double taxRate=PosTaxUtil.getTotalTaxRate(dtl.getSaleItem().getTax());
//					  taxableAmt=(PosOrderUtil.getItemFixedPrice(dtl)*dtl.getSaleItem().getQuantity())/(1+taxRate/100);
//					  taxAmount=PosTaxUtil.calculateTaxes(dtl.getSaleItem().getTax(), taxableAmt); 
//					  
//					  taxableAmt=PosCurrencyUtil.roundTo(rate*dtl.getSaleItem().getQuantity()  -taxAmount.getTaxOneAmount()-taxAmount.getTaxTwoAmount()-taxAmount.getTaxThreeAmount());
//
//					rate= PosCurrencyUtil.roundTo(taxableAmt/dtl.getSaleItem().getQuantity());
//					
//					exclusivePriceVariant=taxableAmt-rate*dtl.getSaleItem().getQuantity();
//					
//				} 
//			 }
//			 totalExclusiveTaxAmt+=	PosCurrencyUtil.roundTo(PosOrderUtil.getTotalTaxAmount(dtl));
//			break;
		default:
			
			 if(!saleItem.getTax().getCode().equalsIgnoreCase("NOTAX")){
					if(saleItem.getTaxCalculationMethod() == TaxCalculationMethod.ExclusiveOfTax)
						totalExclusiveTaxAmt+=	PosCurrencyUtil.roundTo(PosOrderUtil.getTotalTaxAmount(dtl));
					else
						totalInclusiveTaxAmt+=	PosCurrencyUtil.roundTo(PosOrderUtil.getTotalTaxAmount(dtl));
			 }
			
			break;
		
		}
		/** The Rate field **/
		left += DESC_FIELD_WIDTH + gap;
		printTextBlock(left, RATE_FIELD_WIDTH,
				taxSymbol+PosCurrencyUtil.format(rate), TextAlign.RIGHT, false);
		
	
		/** The Total Amount field **/
		left += RATE_FIELD_WIDTH + gap;
		final double amount= (PosEnvSettings.getInstance().getPrintSettings().getPaymentReceiptSettings().getReceiptType()==PaymentReceiptType.Exclusive && 
				dtl.getSaleItem().getTaxCalculationMethod()==TaxCalculationMethod.InclusiveOfTax &&
				!saleItem.getTax().getCode().equalsIgnoreCase("NOTAX"))?
						taxableAmt:rate*dtl.getSaleItem().getQuantity();
		printTextBlock(left, TOTAL_FIELD_WIDTH, 
				PosCurrencyUtil.format(amount) ,
						TextAlign.RIGHT, true);
		subTotal = subTotal + PosCurrencyUtil.roundTo(amount) ;
		/** Print modifiers */
		if(PosEnvSettings.getInstance().getPrintSettings().getPaymentReceiptSettings().isModifiersVisible())
			printAttributes(dtl.getSaleItem());
		
		
		left = SLNO_FIELD_WIDTH + gap +  ITEM_LEVEL_FIELD_WITDH + gap +QTY_FIELD_WIDTH + gap;
		
		//item remarks 
		if(PosEnvSettings.getInstance().getPrintSettings().getPaymentReceiptSettings().showItemRemarks())
			printTextBlock(left,DESC_FIELD_WIDTH +gap ,dtl.getRemarks(),TextAlign.LEFT,true);
 
		final BeanDiscount discount=saleItem.getDiscount();
		if(discount!=null && 
				!discount.getCode().equals(PosDiscountItemProvider.NONE_DISCOUNT_CODE) && 
				!discount.getCode().equals(PosPromotionItemProvider.DEF_PROMO_CODE)){
			
			double discAmt;
			if(PosEnvSettings.getInstance().getPrintSettings().getPaymentReceiptSettings().getReceiptType()==PaymentReceiptType.Exclusive && 
					dtl.getSaleItem().getTaxCalculationMethod()==TaxCalculationMethod.InclusiveOfTax){
				 discAmt=	PosOrderUtil.getTotalDiscountAmountForExclusiveRate(dtl,rate  );
				 
				 exclusivePriceVariant=PosOrderUtil.getGrandTotal(dtl)-(PosCurrencyUtil.roundTo(amount)+PosCurrencyUtil.roundTo(PosOrderUtil.getTotalTaxAmount(dtl))-PosCurrencyUtil.roundTo(discAmt)  );
				 discAmt-= exclusivePriceVariant;
			}else if(PosEnvSettings.getInstance().getPrintSettings().getPaymentReceiptSettings().getReceiptType()==PaymentReceiptType.Inclusive && 
					dtl.getSaleItem().getTaxCalculationMethod()==TaxCalculationMethod.ExclusiveOfTax){
				 discAmt=	PosOrderUtil.getTotalDiscountAmountForExclusiveRate(dtl,rate  );
			}else
			   discAmt=PosOrderUtil.getTotalDiscountAmount(dtl);
			
			subTotal = subTotal - PosCurrencyUtil.roundTo(discAmt);
			String discountAmount="-"+PosCurrencyUtil.format(discAmt);
			String discountName=" LESS "+ discount.getName();
			int atX=SLNO_FIELD_WIDTH+gap+ITEM_LEVEL_FIELD_WITDH+gap;
			final int discDescWidth=QTY_FIELD_WIDTH+gap+DESC_FIELD_WIDTH;
			printTextBlock(atX, discDescWidth,discountName, TextAlign.LEFT, false);
			atX+=discDescWidth+gap+RATE_FIELD_WIDTH;
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
		final int blockWidth = 105;

		double totalGst =0;
		totalGst=order.getTotalTax1()+
				order.getTotalTax2()+
				order.getTotalTax3()+
				order.getTotalServiceTax()+
				order.getTotalGST()+
				PosOrderUtil.getExtraChargeTotalTaxAmount(order) -
				order.getBillTaxAmount();
		
		final String totalDtlAmtText = PosCurrencyUtil.format(subTotal);
		
		if(order.getOrderDetailItems() !=null && order.getOrderDetailItems().size()>0){

			printTextBlock(blockLeft, blockWidth, "Total :", TextAlign.RIGHT, false);
			printText(TextAlign.RIGHT, totalDtlAmtText);
		}else if(order.getRoundAdjustmentAmount()!=0){ 
			
			printTextBlock(blockLeft, blockWidth, "Total :", TextAlign.RIGHT, false);
			printText(TextAlign.RIGHT, PosCurrencyUtil.format(order.getTotalAmount()));
		}

		
		final double extraCharge=mOrder.getExtraCharges() + 
					(PosEnvSettings.getInstance().getPrintSettings().getPaymentReceiptSettings().getReceiptType()==PaymentReceiptType.Inclusive ?
							PosOrderUtil.getExtraChargeTotalTaxAmount(mOrder):0);		
		if(extraCharge>0){ 
			printTextBlock(blockLeft, blockWidth, "Extra Charges :", TextAlign.RIGHT,
					false);
			printText(TextAlign.RIGHT, PosCurrencyUtil.format(extraCharge));
		}
		
		 
		final double totalDiscAmt=PosOrderUtil.getDiscountForPaymentReceipt(order, subTotal+extraCharge);

		if(order.getExtraCharges()>0 && 
				PosEnvSettings.getInstance().getPrintSettings().getPaymentReceiptSettings().getReceiptType()!=PaymentReceiptType.Inclusive)
			totalExclusiveTaxAmt+= PosOrderUtil.getExtraChargeTotalTaxAmount(mOrder);
		
		if(totalDiscAmt>0 ){
			advanceLine(1);
			printTextBlock(blockLeft, blockWidth, PosOrderUtil.getDiscountName(order), TextAlign.RIGHT,	false);
			printText(TextAlign.RIGHT, PosCurrencyUtil.format(totalDiscAmt));
			
			totalExclusiveTaxAmt=totalExclusiveTaxAmt-PosOrderUtil.getDiscountForPaymentReceipt(order, totalExclusiveTaxAmt);
		}
		
		final String taxSymbol= (PosEnvSettings.getInstance().getPrintSettings().getPaymentReceiptSettings().getReceiptType()==
				PaymentReceiptType.Normal) ?EXCLUSIVE_TAX_SYMBOL:"";
		
		
		final String totalExclusiveTaxAmtText = PosCurrencyUtil.format(totalExclusiveTaxAmt);
		if (Double.parseDouble(totalExclusiveTaxAmtText)  > 0) {
			
			printTextBlock(blockLeft, blockWidth, taxSymbol + " Tax Amount :",
					TextAlign.RIGHT, false);
			printText(TextAlign.RIGHT, totalExclusiveTaxAmtText);
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

		
	
		final double totPaidAmt= order.getTotalAmountPaid() -
				(order.getChangeAmount()+order.getCashOut())-  
				order.getRoundAdjustmentAmount();
		
		if(isBillPrinting && totPaidAmt>0 && !PosOrderUtil.isDeliveryService(mOrder) ){
			advanceLine(1);
			printTextBlock(blockLeft, blockWidth, "Part. Payments :", TextAlign.RIGHT,	false);
			printText(TextAlign.RIGHT, PosCurrencyUtil.format(totPaidAmt));
		 }
		
		
		
		if(mOrder.getAdvanceAmount()>0 && isBillPrinting){ 

			printTextBlock(blockLeft, blockWidth, "Advance Paid :", TextAlign.RIGHT,
					false);
			printText(TextAlign.RIGHT, PosCurrencyUtil.format(mOrder.getAdvanceAmount()));
			
		}
		
		  double netAmount=PosCurrencyUtil.roundTo(order.getTotalAmount()) + 
					mOrder.getExtraCharges() + PosOrderUtil.getExtraChargeTotalTaxAmount(mOrder)
					- PosCurrencyUtil.roundTo(PosOrderUtil.getBillDiscount(order));
		 
		  
//		  double netPayble=PosCurrencyUtil.roundTo(order.getTotalAmount()) + 
//				mOrder.getExtraCharges() + PosOrderUtil.getExtraChargeTotalTaxAmount(mOrder)
//				+totalSplitAdjustment-PosCurrencyUtil.roundTo(
//				+order.getBillDiscountAmount()
//				+totalSplitPartAdjPayment
//				+mOrder.getAdvanceAmount()) ;
		  
		  double unpaidAmt =PosCurrencyUtil.roundTo(subTotal) + extraCharge  +
				  PosCurrencyUtil.roundTo(totalExclusiveTaxAmt)- (PosCurrencyUtil.roundTo(totPaidAmt) + PosCurrencyUtil.roundTo(totalDiscAmt )) ;
		  double  roundingAdjustment=0;
		  if(isBillPrinting){
			  roundingAdjustment= PosCurrencyUtil.nRound(unpaidAmt) - unpaidAmt;
			  netAmount=PosCurrencyUtil.nRound(netAmount);
		  }else{
			  roundingAdjustment=mOrder.getRoundAdjustmentAmount();
			  netAmount=(roundingAdjustment!=0)?PosCurrencyUtil.nRound(netAmount):netAmount;
		  }
//			  roundingAdjustment= PosCurrencyUtil.nRound(netPayble) - netPayble;
		  
		final String roundingAdjustmentText= PosCurrencyUtil.format(roundingAdjustment);
	
		if(Double.parseDouble(roundingAdjustmentText)!=0){ 

			printTextBlock(blockLeft, blockWidth, "Rounding adjustment :", TextAlign.RIGHT,
					false);
			printText(TextAlign.RIGHT, roundingAdjustmentText);
		}
		
//		netPayble= PosCurrencyUtil.nRound(netPayble);
		unpaidAmt=PosCurrencyUtil.nRound(unpaidAmt);
		
	
		setFontStyle(Font.BOLD);
		setFontSize(9f);

		/* netamount calculation contains an irregularity. To fix it immediately, commenting this portion for 
		 * printing netamount By Aslam ( 2021 - 01 -09 12:08:14 ) */
		
		if(netAmount!=unpaidAmt || netAmount==0 ){
			printTextBlock(blockLeft, blockWidth,"Net Amount :", TextAlign.RIGHT,	false);
			printText(TextAlign.RIGHT,  PosCurrencyUtil.format(netAmount));
		}
		
		if(isBillPrinting && unpaidAmt!=0){
			printTextBlock(blockLeft, blockWidth, unpaidAmt>0?"Amount Due :": "Balance(Refund) :"  , TextAlign.RIGHT,	false);
			printText(TextAlign.RIGHT,   PosCurrencyUtil.format(unpaidAmt));
				 
		}
		

		setFont(mFontReceipt);

		final String netAmtText=PosCurrencyUtil.format(netAmount);  //(netAmount<0)?-1*netAmount:netAmount
		final String unpaidAmtText=PosCurrencyUtil.format((unpaidAmt<0)?-1*unpaidAmt:unpaidAmt);
		
		if(totalGst>0){

			if(PosEnvSettings.getInstance().getBillParams().getTaxSummaryDisplayType()==BeanBillParam.SHOW_TAX_TABLE){

				printMoneyInText((isBillPrinting && unpaidAmt!=0)? unpaidAmtText: netAmtText);
				printTaxSummary();

			}else if(PosEnvSettings.getInstance().getBillParams().getTaxSummaryDisplayType()==BeanBillParam.SHOW_TAX_SUMMARY){

				printTextBlock(blockLeft, blockWidth, "Total includes TAX of :", TextAlign.RIGHT,false);
				printText(TextAlign.RIGHT, PosCurrencyUtil.format(totalGst));
				printMoneyInText((isBillPrinting && unpaidAmt!=0)? unpaidAmtText: netAmtText);
			}else
				printMoneyInText((isBillPrinting && unpaidAmt!=0)? unpaidAmtText: netAmtText);

			drawLine(getDefaultBillSummarySeparatorStyle());
		}else
			printMoneyInText((isBillPrinting && unpaidAmt!=0)? unpaidAmtText: netAmtText);
		

	}
	/* (non-Javadoc)
	 * @see com.indocosmo.pos.reports.base.PosPrintableBase#printMoneyInText(java.lang.String)
	 */
	@Override
	protected void printMoneyInText(String amt) {
		
		if (showAmountInWords && PosNumberUtil.parseDoubleSafely(amt)!=0)
			super.printMoneyInText(amt);
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
		
	 
//			printText(TextAlign.CENTER, " ",true,true);
		
	}


	/**
	 * @param item
	 */
	protected void setTaxSummary(BeanOrderDetail item){

		if(item.getSaleItem().getTax()==null) 
			return;
		
		if(item.getSaleItem().getTax().getCode().equals(PosTaxItemProvider.NO_TAXCODE)) return;
		final BeanTax itemTax=item.getSaleItem().getTax();
		if(PosTaxUtil.getTotalTaxRate(itemTax)==0)
			return;
		
		BeanReceiptTaxSummary taxSummary=null;

		
		if(itemTaxList.containsKey(itemTax.getId()))

			taxSummary=itemTaxList.get(itemTax.getId());

		else{

			taxSummary=new BeanReceiptTaxSummary();
			itemTaxList.put(itemTax.getId(), taxSummary);
			
//			taxSummary.setTaxOneName(item.getSaleItem().getTax().getTaxOneName());
//			taxSummary.setTaxTwoName(item.getSaleItem().getTax().getTaxTwoName());
//			taxSummary.setTaxThreeName(item.getSaleItem().getTax().getTaxThreeName());
//			taxSummary.setSCName(item.getSaleItem().getTax().getServiceTaxName());
//			
//			taxSummary.setTaxOnePercentage(item.getSaleItem().getTax().getTaxOnePercentage());
//			taxSummary.setTaxTwoPercentage(item.getSaleItem().getTax().getTaxTwoPercentage());
//			taxSummary.setTaxThreePercentage(item.getSaleItem().getTax().getTaxThreePercentage());
//			taxSummary.setSCName(item.getSaleItem().getTax().getServiceTaxName());
		}

		double billDiscountPercentage=getOrder().getBillDiscountPercentage();
		
		if(billDiscountPercentage==0 && 
				(getOrder().getStatus()!=PosOrderStatus.Closed && getOrder().getStatus()!=PosOrderStatus.Refunded) && 
				getOrder().getPreBillDiscount()!=null &&
				!getOrder().getPreBillDiscount().equals(new PosDiscountItemProvider().getNoneDiscount())){
		
			if(!getOrder().getPreBillDiscount().isPercentage()){
				billDiscountPercentage=getOrder().getPreBillDiscount().getPrice() *100 /PosOrderUtil.getTotalAmount(getOrder());
			}else
				billDiscountPercentage=getOrder().getPreBillDiscount().getPrice();
		}
		
		double totalTax1Amount=taxSummary.getTax1Amount();
		double totalTax2Amount=taxSummary.getTax2Amount();
		double totalTax3Amount=taxSummary.getTax3Amount();
		double totalTaxSCAmount=taxSummary.getTaxSCAmount();
		double totalTaxGSTAmount=taxSummary.getTaxGSTAmount();
		double totalTaxAmo=taxSummary.getTaxAmount();
		double totalTaxableAmo=taxSummary.getTaxableAmount();
		
		double t1Amt=PosOrderUtil.getTotalT1Amount(item);
		double t2Amt=PosOrderUtil.getTotalT2Amount(item);
		double t3Amt=PosOrderUtil.getTotalT3Amount(item);
		double scAmt=PosOrderUtil.getTotalServiceTaxAmount(item);
		double taxableAmt=PosOrderUtil.getTotalTaxableAmount(item);
		
		t1Amt-=PosCurrencyUtil.roundTo(t1Amt*billDiscountPercentage/100);
		t2Amt-=PosCurrencyUtil.roundTo(t2Amt*billDiscountPercentage/100);
		t3Amt-=PosCurrencyUtil.roundTo(t3Amt*billDiscountPercentage/100);
		scAmt-=PosCurrencyUtil.roundTo(scAmt*billDiscountPercentage/100);
		taxableAmt-=PosCurrencyUtil.roundTo(taxableAmt*billDiscountPercentage/100);
		
		totalTax1Amount+=t1Amt;//item.getSaleItem().getTax().getTaxAmount().getTaxOneAmount();
		totalTax2Amount+=t2Amt; //item.getSaleItem().getTax().getTaxAmount().getTaxTwoAmount();
		totalTax3Amount+=t3Amt;//item.getSaleItem().getTax().getTaxAmount().getTaxThreeAmount();
		totalTaxSCAmount+=scAmt;//item.getSaleItem().getTax().getTaxAmount().getServiceTaxAmount();
		
		taxSummary.setTax1Amount(totalTax1Amount);
		taxSummary.setTax2Amount(totalTax2Amount);
		taxSummary.setTax3Amount(totalTax3Amount);
		taxSummary.setTaxSCAmount(totalTaxSCAmount);
		
		totalTaxGSTAmount+=PosCurrencyUtil.roundTo(PosOrderUtil.getTotalGSTAmount(item));//item.getSaleItem().getTax().getTaxAmount().getGSTAmount();
		taxSummary.setTaxGSTAmount(totalTaxGSTAmount);
		
		totalTaxAmo+=PosCurrencyUtil.roundTo(t1Amt+t2Amt+t3Amt+scAmt);//item.getSaleItem().getTax().getTaxAmount().getTotalTaxAmount();
		taxSummary.setTaxAmount(totalTaxAmo);
		totalTaxableAmo+=taxableAmt;//item.getSaleItem().getTax().getTaxAmount().getTaxableAmount();
		taxSummary.setTaxableAmount(totalTaxableAmo);

	}
	
	/**
	 * 
	 */
	protected void printTaxSummary(){

		final int TAX_NAME_WIDTH=90;
		final int TAX_PER_WIDTH=25;
		final int TAX_APLICABLE_AMO_WIDTH=45;
		final int TAX_TOTAL_TAX_WIDTH=35;

		printTextBlock(0, 100, " ", TextAlign.LEFT,true);
		printTextBlock(0, getWidth(), "TAX SUMMARY (Net Amount is inclusive of tax.)", TextAlign.LEFT,true);

		printSingleLine();

		final int gap = 1;
		int left = 0;
		double gstTotal=0.0;
		double totalWithoutTax=0.0;

		/** The Serial number field **/
		printTextBlock(left, TAX_NAME_WIDTH, "Tax. Name", TextAlign.CENTER, false);

		/** The Quantity field **/
		left += TAX_NAME_WIDTH + gap;
		printTextBlock(left, TAX_PER_WIDTH, "%", TextAlign.CENTER, false);

		/** The Description field **/
		left += TAX_PER_WIDTH + gap;
		printTextBlock(left, TAX_APLICABLE_AMO_WIDTH, "Amount", TextAlign.CENTER,
				false);

		/** The Rate field **/
		left += TAX_APLICABLE_AMO_WIDTH + gap;
		printTextBlock(left, TAX_TOTAL_TAX_WIDTH, "Tax", TextAlign.CENTER, true);

		printSingleLine();

//		double billDiscountPercentage=getOrder().getBillDiscountPercentage();
//		
//		if(billDiscountPercentage==0 && 
//				(getOrder().getStatus()!=PosOrderStatus.Closed && getOrder().getStatus()!=PosOrderStatus.Refunded) && 
//				getOrder().getPreBillDiscount()!=null &&
//				!getOrder().getPreBillDiscount().equals(new PosDiscountItemProvider().getNoneDiscount())){
//		
//			if(!getOrder().getPreBillDiscount().isPercentage()){
//				billDiscountPercentage=getOrder().getPreBillDiscount().getPrice() *100 /PosOrderUtil.getTotalAmount(getOrder());
//			}else
//				billDiscountPercentage=getOrder().getPreBillDiscount().getPrice();
//		}
		
		for(Integer taxId: itemTaxList.keySet()){

//			if(itemTaxList.get(taxId).getTaxAmount()>0){

				final BeanTax tax=PosTaxItemProvider.getInstance().getTaxItem(taxId);

				/** The tax name field **/
				left=0;
				printTextBlock(left, TAX_NAME_WIDTH, tax.getName(), TextAlign.LEFT, false);

				/** The tax rate field **/
				final String totalTaxRate=PosCurrencyUtil.format(PosTaxUtil.getTotalTaxRate(tax));
				left += TAX_NAME_WIDTH + gap;
				printTextBlock(left, TAX_PER_WIDTH,totalTaxRate, TextAlign.RIGHT, false);

					
				String taxSplitting="";
				if(itemTaxList.get(taxId).getTax1Amount()>0){
					taxSplitting=tax.getTaxOneName() + "(" + tax.getTaxOnePercentage()+"%) :" + 
							PosCurrencyUtil.format(itemTaxList.get(taxId).getTax1Amount());
				
				}
					
				if(itemTaxList.get(taxId).getTax2Amount()>0){
				
					taxSplitting+=((taxSplitting!="")?", ":" " )+tax.getTaxTwoName() + "(" + tax.getTaxTwoPercentage()+"%) :"+ 
							PosCurrencyUtil.format(itemTaxList.get(taxId).getTax2Amount());
				
				}
				
				
				if(itemTaxList.get(taxId).getTax3Amount()>0){
					
					taxSplitting+=((taxSplitting!="")?", ":" ") +tax.getTaxThreeName() + "(" + tax.getTaxThreePercentage()+"%) :" + 
					PosCurrencyUtil.format(itemTaxList.get(taxId).getTax3Amount());

				}	
				
				if(itemTaxList.get(taxId).getTaxSCAmount()>0){
					
					taxSplitting+=((taxSplitting!="")?", ":" ") +tax.getServiceTaxName() + "(" + tax.getServiceTaxPercentage()+"%) :" + 
					PosCurrencyUtil.format(itemTaxList.get(taxId).getTaxSCAmount());

				}
				
				/** The Taxable amount field **/
				double taxableAmount=PosCurrencyUtil.roundTo(itemTaxList.get(taxId).getTaxableAmount());
				taxableAmount= (taxableAmount==0?0:taxableAmount);
		
				left += TAX_PER_WIDTH + gap;
				printTextBlock(left, TAX_APLICABLE_AMO_WIDTH, PosCurrencyUtil.format(taxableAmount), TextAlign.RIGHT,false);

				
				
				/** The Tax Amount field **/
				double taxAmount=PosCurrencyUtil.roundTo(itemTaxList.get(taxId).getTaxAmount());
				taxAmount= (taxAmount==0?0:taxAmount);
//				final double taxAmount=tax1Amt+tax2Amt+tax3Amt;
				gstTotal+=taxAmount;
				totalWithoutTax+=taxableAmount;
				
				left += TAX_APLICABLE_AMO_WIDTH + gap;
				printTextBlock(left, TAX_TOTAL_TAX_WIDTH, PosCurrencyUtil.format(taxAmount), TextAlign.RIGHT, true);

				
				if(taxSplitting!=""){
				
					setFontStyle(Font.ITALIC);
					
					taxSplitting="" + taxSplitting +".";
					printTextBlock(5, TAX_NAME_WIDTH+TAX_APLICABLE_AMO_WIDTH+TAX_PER_WIDTH+TAX_TOTAL_TAX_WIDTH, taxSplitting, TextAlign.LEFT, true);
					
					setFont(mFontReceipt);
					
				}

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
		
		 if(showPaymentSummary)
			 printPaymentSummary(order);
	}


	/**
	 * For normal Payments
	 * @param order
	 */
	private void printPaymentSummary(BeanOrderHeader order){

		final int blockLeft = 0;
		final int blockWidth = 110;
		double balance = 0 ;
		double totalPayed=0;

		totalPayed=totalCashPayment+totalCardPayment+totalCompanyPayment+totalCouponPayment+totalAdvanceCash+totalAdvanceCard +
				totalOnlinePayment + totalAdvanceOnline;
		balance=totalBalancePayment+totalCouponBalancePayment + totalCashOutPayment;

		if(totalPayed>0){

			printTextBlock(0, 100, " ", TextAlign.LEFT,true);
			printTextBlock(0, 100, "PAYMENT SUMMARY", TextAlign.LEFT,true);
			drawLine(getDefaultPaymentSummarySeparatorStyle());

		

			printPaymentInfo("CASH :", totalCashPayment, blockLeft, blockWidth);
			printPaymentInfo("CARD :", totalCardPayment, blockLeft, blockWidth);
			printPaymentInfo("COMPANY :", totalCompanyPayment, blockLeft, blockWidth);
			printPaymentInfo("VOUCHER :", totalCouponPayment, blockLeft, blockWidth);
			printPaymentInfo("ONLINE :", totalOnlinePayment, blockLeft, blockWidth);

			printPaymentInfo("ADVANCE(CASH) :", totalAdvanceCash, blockLeft, blockWidth);
			printPaymentInfo("ADVANCE(CARD) :", totalAdvanceCard, blockLeft, blockWidth);
			printPaymentInfo("ADVANCE(ONLINE) :", totalAdvanceOnline, blockLeft, blockWidth);
			
			if(totalCashPayment>0 || totalCardPayment>0 || totalCompanyPayment>0 || totalCouponPayment>0 ||totalOnlinePayment>0)
				printSingleLine();

			printTextBlock(blockLeft, blockWidth, "Total Paid :", TextAlign.LEFT,false);
			printTextBlock(blockLeft, blockWidth, PosCurrencyUtil.format(totalPayed), TextAlign.RIGHT,true);
			if(balance!=0){
				printTextBlock(blockLeft, blockWidth, "Change :", TextAlign.LEFT,false);
				printTextBlock(blockLeft, blockWidth, PosCurrencyUtil.format(balance), TextAlign.RIGHT,	true);
			}
			 

			drawLine(getDefaultPaymentSummarySeparatorStyle());

			if(order.getStatus().equals(PosOrderStatus.Refunded)){

//				refundDetails(order);

				setFontStyle(Font.BOLD);
				setFontSize(12f);
				printText(TextAlign.CENTER, "REFUNDED");
				setFont(mFontReceipt);
				advanceTextLine(1);
 
				
				

			}
		}else
			drawLine(getDefaultPaymentSummarySeparatorStyle());

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

			printTextBlock(blockLeft, blockWidth, paymenTitle, TextAlign.LEFT,
					false);
			printTextBlock(blockLeft, blockWidth, PosCurrencyUtil.format(amount), TextAlign.RIGHT,
					true);
			isPrinted=true;
		}

		return isPrinted;
	}

	/**
	 * @param paymentDetails
	 */
	private void preparePaymentInfo(ArrayList<BeanOrderPayment> paymentDetails){

		totalAdvanceCash=0;
		totalAdvanceCard=0;
		totalAdvanceOnline=0;
		totalCashPayment=0.0;
		totalCardPayment=0.0;
		totalOnlinePayment=0;
		totalCashOutPayment=0.0;
		totalCompanyPayment=0.0;
		totalCouponPayment=0.0;
		totalBalancePayment=0.0;
		totalCouponBalancePayment=0.0;
		//		totalSplitPartAdjPayment=0.0;

		if(paymentDetails==null || paymentDetails.size()==0)
			return;
		for (BeanOrderPayment payment : paymentDetails) {
			
			if(!payment.isRepayment()){
				switch (payment.getPaymentMode()) {
				case Cash:
					if (payment.getPaidAmount() > 0 && !payment.isAdvance()){
						totalCashPayment+=payment.getPaidAmount();
					}else if (payment.getPaidAmount() > 0 && payment.isAdvance()){
						totalAdvanceCash+=payment.getPaidAmount();
					}
					break;
				case Card:
					if (payment.getPaidAmount() > 0 && !payment.isAdvance()){
						totalCardPayment+=payment.getPaidAmount();
					}else if (payment.getPaidAmount() > 0 && payment.isAdvance()){
						totalAdvanceCard+=payment.getPaidAmount();
					}
					break;
				case Online:
					if (payment.getPaidAmount() > 0 && !payment.isAdvance()){
						totalOnlinePayment+=payment.getPaidAmount();
					}else if (payment.getPaidAmount() > 0 && payment.isAdvance()){
						totalAdvanceOnline+=payment.getPaidAmount();
					}
					break;	case CashOut:
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

	}

 

	/*
	 * 
	 */
	private void printCustomerInfo(BeanOrderHeader order){
		
		
		if(mOrder.getCustomer().getCustType().getCode().equals(PosCustomerTypeProvider.ROOM_TYPE_CODE)){
			printText(TextAlign.LEFT, "Room No :" + mOrder.getCustomer().getName());
		}
		if(PosEnvSettings.getInstance().getPrintSettings().getPaymentReceiptSettings().isEnabledCustomerInfoPrinting(getOrder().getOrderServiceType().getCode())){
			setFontStyle(Font.BOLD);
			setFontSize(9f);
			
			printText(TextAlign.LEFT, "Customer Info:");
			
			setFont(mFontReceipt);
			printText(TextAlign.LEFT, order.getOrderCustomer().getName());
			printText(TextAlign.LEFT, order.getOrderCustomer().getAddress());
			printText(TextAlign.LEFT, order.getOrderCustomer().getAddress2());
			printText(TextAlign.LEFT, order.getOrderCustomer().getAddress3());
			printText(TextAlign.LEFT, order.getOrderCustomer().getAddress4());
			printText(TextAlign.LEFT, order.getOrderCustomer().getCity());
			printText(TextAlign.LEFT, order.getOrderCustomer().getState());
			
			if(order.getOrderCustomer().getPhoneNumber()!=null && 
					order.getOrderCustomer().getPhoneNumber().trim().length()>1)
				printText(TextAlign.LEFT,"Tel:"+ order.getOrderCustomer().getPhoneNumber());
			
			if(order.getOrderCustomer().getPhoneNumber2()!=null && 
					order.getOrderCustomer().getPhoneNumber2().trim().length()>1)
				printText(TextAlign.LEFT,"Mob:"+ order.getOrderCustomer().getPhoneNumber2());
			
			if(order.getOrderCustomer().getTinNo()!=null  &&  !order.getOrderCustomer().getTinNo().trim().equals(""))
				printText(TextAlign.LEFT,"Tin No:" + order.getOrderCustomer().getTinNo());
		
		}
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
		

		if(order.getStatus().equals(PosOrderStatus.Void)){

			setFontStyle(Font.BOLD);
			setFontSize(10f);
			printText(TextAlign.CENTER, "VOID BILL");
			setFont(mFontReceipt);
		}
		
		printRemarks();
		
		printCustomerInfo(getOrder());

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

		printText(TextAlign.CENTER, footerLine_1,true,false);
		printText(TextAlign.CENTER, footerLine_2,true,false);
		printText(TextAlign.CENTER, footerLine_3,true,false);
		printText(TextAlign.CENTER, footerLine_4,true,false);
		printText(TextAlign.CENTER, footerLine_5,true,false);
		printText(TextAlign.CENTER, footerLine_6,true,false);
		printText(TextAlign.CENTER, footerLine_7,true,false);
		printText(TextAlign.CENTER, footerLine_8,true,false);
		printText(TextAlign.CENTER, footerLine_9,true,false);
		printText(TextAlign.CENTER, footerLine_10,true,false);

		if(!isBillPrinting &&  showQueueNo){
			setFontStyle(Font.BOLD);
			setFontSize(10f);
			printText(TextAlign.CENTER, PosOrderUtil.getFormatedOrderQueueNo(order),true,false);
			setFont(mFontReceipt);
		}
		if(showBarcode)
			printOrderIDBarcode();

		
		if(PosEnvSettings.getInstance().isDevelopmentMode()) {
			
			printText(TextAlign.CENTER, devModeText);
			printText(TextAlign.CENTER, devModeText);
			printText(TextAlign.CENTER, devModeText);
			
		}
		printText(TextAlign.CENTER, ".",true,true);

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

	 

	/* (non-Javadoc)
	 * @see com.indocosmo.pos.reports.base.PosPrintableReportBase#getPosReportPageFormat()
	 */
	@Override
	public PosReportPageFormat getPosReportPageFormat() {

		return PosReportPageFormat.PAGE_80MM;
	}

	/**
	 * @return
	 * @throws IOException 
	 */
	
	private void loadDefaultPaymentReceiptSettings() throws IOException{
		
		Properties mPrintProperties =PosFileUtils.loadPropertyFile(PROPERTY_FILE);
		
	 
		SLNO_FIELD_WIDTH=
				PosNumberUtil.parseIntegerSafely(mPrintProperties.getProperty("tax_invoice.column_settings.sl_no.width","0"));
		
		ITEM_LEVEL_FIELD_WITDH=
				PosNumberUtil.parseIntegerSafely(mPrintProperties.getProperty("tax_invoice.column_settings.item_level_field.width","8"));
		 
		
		QTY_FIELD_WIDTH=
				PosNumberUtil.parseIntegerSafely(mPrintProperties.getProperty("tax_invoice.column_settings.quantity.width","25"));
		
		RATE_FIELD_WIDTH=
				PosNumberUtil.parseIntegerSafely(mPrintProperties.getProperty("tax_invoice.column_settings.rate.width","28"));
	 	
		TOTAL_FIELD_WIDTH=
				PosNumberUtil.parseIntegerSafely(mPrintProperties.getProperty("tax_invoice.column_settings.total_amount.width","40"));
		
		EXCLUSIVE_TAX_SYMBOL=
				mPrintProperties.getProperty("tax_invoice.exclusive_tax_symbol","");
		
		showPaymentSummary= mPrintProperties.getProperty("tax_invoice.show_payment_summary","true")
							.trim().equalsIgnoreCase("true")?true:false;
		
		showBarcode= mPrintProperties.getProperty("tax_invoice.show_barcode","true")
				.trim().equalsIgnoreCase("true")?true:false;

		showAmountInWords= mPrintProperties.getProperty("tax_invoice.show_amount_in_words","true")
				.trim().equalsIgnoreCase("true")?true:false;
		
		
		showQueueNo=PosEnvSettings.getInstance().getUISetting().useOrderQueueNo() &&
				(mPrintProperties.getProperty("tax_invoice.show_queue_no","false")
				.trim().equalsIgnoreCase("true")?true:false);
		
		
		}
	
	
	

//	/**
//	 * @param item
//	 */
//	protected void setTaxSummary(BeanOrderDetail item){
//
//		if(item.getSaleItem().getTax()==null) 
//			return;
//		
//		if(item.getSaleItem().getTax().getCode().equals(PosTaxItemProvider.NO_TAXCODE)) return;
//		final BeanTax itemTax=item.getSaleItem().getTax();
//		if(PosTaxUtil.getTotalTaxRate(itemTax)==0)
//			return;
//		
//		BeanReceiptTaxSummary taxSummary=null;
//
//		
//		if(itemTaxList.containsKey(itemTax.getId()))
//
//			taxSummary=itemTaxList.get(itemTax.getId());
//
//		else{
//
//			taxSummary=new BeanReceiptTaxSummary();
//			itemTaxList.put(itemTax.getId(), taxSummary);
//		}
//
//		
//		double tax1Amount=taxSummary.getTax1Amount();
//		double tax2Amount=taxSummary.getTax2Amount();
//		double tax3Amount=taxSummary.getTax3Amount();
//		double taxSCAmount=taxSummary.getTaxSCAmount();
//		double taxGSTAmount=taxSummary.getTaxGSTAmount();
//		double totalTaxAmo=taxSummary.getTaxAmount();
//		double taxableAmo=taxSummary.getTaxableAmount();
//		
//		tax1Amount+=PosCurrencyUtil.roundTo(PosOrderUtil.getTotalT1Amount(item));//item.getSaleItem().getTax().getTaxAmount().getTaxOneAmount();
//		taxSummary.setTax1Amount(tax1Amount);
//		tax2Amount+=PosCurrencyUtil.roundTo(PosOrderUtil.getTotalT2Amount(item)); //item.getSaleItem().getTax().getTaxAmount().getTaxTwoAmount();
//		taxSummary.setTax2Amount(tax2Amount);
//		tax3Amount+=PosCurrencyUtil.roundTo(PosOrderUtil.getTotalT3Amount(item));//item.getSaleItem().getTax().getTaxAmount().getTaxThreeAmount();
//		taxSummary.setTax3Amount(tax3Amount);
//		taxSCAmount+=PosCurrencyUtil.roundTo(PosOrderUtil.getTotalServiceTaxAmount(item));//item.getSaleItem().getTax().getTaxAmount().getServiceTaxAmount();
//		taxSummary.setTaxSCAmount(taxSCAmount);
//		taxGSTAmount+=PosCurrencyUtil.roundTo(PosOrderUtil.getTotalGSTAmount(item));//item.getSaleItem().getTax().getTaxAmount().getGSTAmount();
//		taxSummary.setTaxGSTAmount(taxGSTAmount);
//		
//
//		totalTaxAmo+=PosCurrencyUtil.roundTo(PosOrderUtil.getTotalTaxAmount(item));//item.getSaleItem().getTax().getTaxAmount().getTotalTaxAmount();
//		taxSummary.setTaxAmount(totalTaxAmo);
//		taxableAmo+=PosCurrencyUtil.roundTo(PosOrderUtil.getTotalTaxableAmount(item));//item.getSaleItem().getTax().getTaxAmount().getTaxableAmount();
//		taxSummary.setTaxableAmount(taxableAmo);
//
//	}
//	
//	/**
//	 * 
//	 */
//	protected void printTaxSummary(){
//
//		final int TAX_NAME_WIDTH=90;
//		final int TAX_PER_WIDTH=25;
//		final int TAX_APLICABLE_AMO_WIDTH=45;
//		final int TAX_TOTAL_TAX_WIDTH=35;
//
//		printTextBlock(0, 100, " ", TextAlign.LEFT,true);
//		printTextBlock(0, getWidth(), "TAX SUMMARY (Net Amount is inclusive of tax.)", TextAlign.LEFT,true);
//
//		printSingleLine();
//
//		final int gap = 1;
//		int left = 0;
//		double gstTotal=0.0;
//		double totalWithoutTax=0.0;
//
//		/** The Serial number field **/
//		printTextBlock(left, TAX_NAME_WIDTH, "Tax. Name", TextAlign.CENTER, false);
//
//		/** The Quantity field **/
//		left += TAX_NAME_WIDTH + gap;
//		printTextBlock(left, TAX_PER_WIDTH, "%", TextAlign.CENTER, false);
//
//		/** The Description field **/
//		left += TAX_PER_WIDTH + gap;
//		printTextBlock(left, TAX_APLICABLE_AMO_WIDTH, "Amount", TextAlign.CENTER,
//				false);
//
//		/** The Rate field **/
//		left += TAX_APLICABLE_AMO_WIDTH + gap;
//		printTextBlock(left, TAX_TOTAL_TAX_WIDTH, "Tax", TextAlign.CENTER, true);
//
//		printSingleLine();
//
//		double billDiscountPercentage=getOrder().getBillDiscountPercentage();
//		
//		if(billDiscountPercentage==0 && 
//				(getOrder().getStatus()!=PosOrderStatus.Closed && getOrder().getStatus()!=PosOrderStatus.Refunded) && 
//				getOrder().getPreBillDiscount()!=null &&
//				!getOrder().getPreBillDiscount().equals(new PosDiscountItemProvider().getNoneDiscount())){
//		
//			if(!getOrder().getPreBillDiscount().isPercentage()){
//				billDiscountPercentage=getOrder().getPreBillDiscount().getPrice() *100 /PosOrderUtil.getTotalAmount(getOrder());
//			}else
//				billDiscountPercentage=getOrder().getPreBillDiscount().getPrice();
//		}
//		
//		for(Integer taxId: itemTaxList.keySet()){
//
////			if(itemTaxList.get(taxId).getTaxAmount()>0){
//
//				final BeanTax tax=PosTaxItemProvider.getInstance().getTaxItem(taxId);
//
//				/** The tax name field **/
//				left=0;
//				printTextBlock(left, TAX_NAME_WIDTH, tax.getName(), TextAlign.LEFT, false);
//
//				/** The tax rate field **/
//				final String totalTaxRate=PosCurrencyUtil.format(PosTaxUtil.getTotalTaxRate(tax));
//				left += TAX_NAME_WIDTH + gap;
//				printTextBlock(left, TAX_PER_WIDTH,totalTaxRate, TextAlign.RIGHT, false);
//
//					
//				String taxSplitting="";
//				if(itemTaxList.get(taxId).getTax1Amount()>0){
//					taxSplitting=tax.getTaxOneName() + "(" + tax.getTaxOnePercentage()+"%) :" + 
//							PosCurrencyUtil.format(itemTaxList.get(taxId).getTax1Amount()- 
//									itemTaxList.get(taxId).getTax1Amount()* billDiscountPercentage/100);
//				
//				}
//					
//				if(itemTaxList.get(taxId).getTax2Amount()>0){
//				
//					taxSplitting+=((taxSplitting!="")?", ":" " )+tax.getTaxTwoName() + "(" + tax.getTaxTwoPercentage()+"%) :"+ 
//							PosCurrencyUtil.format(itemTaxList.get(taxId).getTax2Amount() - 
//									itemTaxList.get(taxId).getTax2Amount()* billDiscountPercentage/100);
//				
//				}
//				
//				
//				if(itemTaxList.get(taxId).getTax3Amount()>0){
//					
//					taxSplitting+=((taxSplitting!="")?", ":" ") +tax.getTaxThreeName() + "(" + tax.getTaxThreePercentage()+"%) :" + 
//					PosCurrencyUtil.format(itemTaxList.get(taxId).getTax3Amount()- 
//							itemTaxList.get(taxId).getTax3Amount()* billDiscountPercentage/100);
//
//				}	
//				
//				if(itemTaxList.get(taxId).getTaxSCAmount()>0){
//					
//					taxSplitting+=((taxSplitting!="")?", ":" ") +tax.getServiceTaxName() + "(" + tax.getServiceTaxPercentage()+"%) :" + 
//					PosCurrencyUtil.format(itemTaxList.get(taxId).getTaxSCAmount()- 
//							itemTaxList.get(taxId).getTaxSCAmount()* billDiscountPercentage/100);
//
//				}
//				
//				/** The Taxable amount field **/
//				double taxableAmount=PosCurrencyUtil.roundTo(itemTaxList.get(taxId).getTaxableAmount()- itemTaxList.get(taxId).getTaxableAmount() * billDiscountPercentage/100);
//				taxableAmount= (taxableAmount==0?0:taxableAmount);
//		
//				left += TAX_PER_WIDTH + gap;
//				printTextBlock(left, TAX_APLICABLE_AMO_WIDTH, PosCurrencyUtil.format(taxableAmount), TextAlign.RIGHT,false);
//
//				
//				
//				/** The Tax Amount field **/
//				double taxAmount=PosCurrencyUtil.roundTo(itemTaxList.get(taxId).getTaxAmount()-itemTaxList.get(taxId).getTaxAmount() * billDiscountPercentage/100);
//				taxAmount= (taxAmount==0?0:taxAmount);
////				final double taxAmount=tax1Amt+tax2Amt+tax3Amt;
//				gstTotal+=taxAmount;
//				totalWithoutTax+=taxableAmount;
//				
//				left += TAX_APLICABLE_AMO_WIDTH + gap;
//				printTextBlock(left, TAX_TOTAL_TAX_WIDTH, PosCurrencyUtil.format(taxAmount), TextAlign.RIGHT, true);
//
//				
//				if(taxSplitting!=""){
//				
//					setFontStyle(Font.ITALIC);
//					
//					taxSplitting="" + taxSplitting +".";
//					printTextBlock(5, TAX_NAME_WIDTH+TAX_APLICABLE_AMO_WIDTH+TAX_PER_WIDTH+TAX_TOTAL_TAX_WIDTH, taxSplitting, TextAlign.LEFT, true);
//					
//					setFont(mFontReceipt);
//					
//				}
//
//		}
//
// 
//
//	}
	
}
