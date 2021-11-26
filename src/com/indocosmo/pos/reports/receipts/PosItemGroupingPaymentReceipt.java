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
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

import javax.imageio.ImageIO;

import com.indocosmo.pos.common.PosEnvSettings;
import com.indocosmo.pos.common.beans.BeanBillPaymentSummaryInfo;
import com.indocosmo.pos.common.beans.BeanReceiptTaxSummary;
import com.indocosmo.pos.common.beans.settings.ui.BeanUISetting;
import com.indocosmo.pos.common.enums.PaymentReceiptItemGrouping;
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
import com.indocosmo.pos.data.beans.BeanItemClassBase;
import com.indocosmo.pos.data.beans.BeanOrderDetail;
import com.indocosmo.pos.data.beans.BeanOrderHeader;
import com.indocosmo.pos.data.beans.BeanOrderKitchenQueue;
import com.indocosmo.pos.data.beans.BeanOrderHeader.PosOrderStatus;
import com.indocosmo.pos.data.beans.BeanOrderPayment;
import com.indocosmo.pos.data.beans.BeanSaleItem;
import com.indocosmo.pos.data.beans.BeanServingTable;
import com.indocosmo.pos.data.beans.BeanTax;
import com.indocosmo.pos.data.providers.shopdb.PosCustomerTypeProvider;
import com.indocosmo.pos.data.providers.shopdb.PosDiscountItemProvider;
import com.indocosmo.pos.data.providers.shopdb.PosOrderKitchenQueueProvider;
import com.indocosmo.pos.data.providers.shopdb.PosPromotionItemProvider;
import com.indocosmo.pos.data.providers.shopdb.PosSaleItemProvider;
import com.indocosmo.pos.data.providers.shopdb.PosSubItemClassProvider;
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
public class PosItemGroupingPaymentReceipt extends PosPaymentReceiptBase {
	
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
//	private HashMap<Integer, BeanReceiptTaxSummary> itemTaxList;
	private LinkedHashMap<String, Double> itemTaxList;
	private LinkedHashMap<String, Double> grpItemTaxList;
	private int DESC_FIELD_WIDTH=0;
	private String EXCLUSIVE_TAX_SYMBOL="";
	

	private double totalExclusiveTaxAmt = 0.0;
	private double totalInclusiveTaxAmt = 0.0;
	private double subTotal = 0.0; 
	private double mGroupTotal=0;
	private double mBillDiscountPercentage;
	private String mBillDiscountName;
	private String mGroupName;
	
	final int blockLeft = 20;
	  int blockWidth = 120;
	
	final static  String devModeText="DEVELOPMENT MODE, INDOCOSMO SYSTEMS";
	
	private static Map<String, ArrayList<BeanOrderDetail>> orderDtlMapList;
	/**
	 * 
	 */
	public PosItemGroupingPaymentReceipt(BeanOrderHeader order) throws IOException {
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
	public PosItemGroupingPaymentReceipt() {
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
		
		blockWidth=DESC_FIELD_WIDTH + SLNO_FIELD_WIDTH+1+
				ITEM_LEVEL_FIELD_WITDH+1+
				QTY_FIELD_WIDTH+1 ;
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
			billNo="Bill : "  + PosOrderUtil.getShortOrderIDFromOrderID(order.getOrderId());
//					(PosEnvSettings.getInstance().getUISetting().useOrderQueueNo()?PosOrderUtil.getFormatedOrderQueueNo(order):
//				PosOrderUtil.getShortOrderIDFromOrderID(order.getOrderId()));
		}else{
			billNo="Invoice No : "  + order.getInvoiceNo();
			refBillNo="Ref# : "  +PosOrderUtil.getShortOrderIDFromOrderID(order.getOrderId());
//			(PosEnvSettings.getInstance().getUISetting().useOrderQueueNo()?PosOrderUtil.getFormatedOrderQueueNo(order):
//				PosOrderUtil.getShortOrderIDFromOrderID(order.getOrderId()));
		}
//		final String billNo = "Bill. " + PosOrderUtil.getShortOrderIDFromOrderID(order.getOrderId());
		String billDate = "Date : " + PosDateUtil.formatLocal(isBillPrinting?order.getOrderDate():
			(order.getClosingDate()==null?PosDateUtil.getDateTime():order.getClosingDate()));
	 
		String billTime= "Time : " + PosDateUtil.format(PosDateUtil.SHORT_TIME_FORMAT_12, isBillPrinting?order.getOrderTime(): 
					(order.getClosingTime()==null?PosDateUtil.getDateTime():order.getClosingTime()));  
	
		final String printedAt = "Station : "+ PosEnvSettings.getInstance().getStation().getName();
		final String orderStatus = "Status : " + order.getStatus().getDisplayText();

		printText(TextAlign.LEFT, billNo, false);
		printText(TextAlign.RIGHT, billDate);
		printText(TextAlign.LEFT, printedAt,false);
		printText(TextAlign.RIGHT, billTime);
	
		String footerLine_ServiceType = "Service Type : ";
		String footerLine_Covers="";
		String footerLine_ClosedBy ="";
						//((isBillPrinting || order.getClosedBy()==null)?
		String footerLine_ServedBy = "Served By : "+ order.getUser().getName() ;
		if((order.getStatus()==PosOrderStatus.Closed || order.getStatus()==PosOrderStatus.Refunded) && 
				order.getClosedBy()!=null)
			footerLine_ClosedBy = "Closed By : "+ order.getClosedBy().getName();
		
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
			footerLine_ServedBy ="Served By : "+((order.getServedBy() != null) ? order
					.getServedBy().getFirstName() : "");
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
			
			if (!isBillPrinting) {
				printText(TextAlign.LEFT, footerLine_ServedBy,false);
				printText(TextAlign.RIGHT, footerLine_ClosedBy,true,true);
			}
			else
				printTextBlock(0, RECEIPT_SERVEDBY_WIDTH, footerLine_ServedBy, TextAlign.LEFT, true);
			
		}
		
//		if (isBillPrinting)
//			printText(TextAlign.RIGHT, orderStatus);
//		else
//			printText(TextAlign.RIGHT, refBillNo);
		if (!isBillPrinting) {
			printText(TextAlign.LEFT, orderStatus,false);
			printText(TextAlign.RIGHT, refBillNo,true);
		}else
			printTextBlock(0, RECEIPT_SERVEDBY_WIDTH, orderStatus , TextAlign.LEFT, true);
		
		printKitchenReceiptDetails();
		
		if(PosEnvSettings.getInstance().isDevelopmentMode()) {
			
			printText(TextAlign.CENTER, devModeText);
			printText(TextAlign.CENTER, devModeText);
			printText(TextAlign.CENTER, devModeText);
			
		}
		drawLine(getDefaultHeaderSeparatorStyle());
	}

	private void  groupItemList(BeanOrderHeader order) {
		
		
		PaymentReceiptItemGrouping groupingMethod=PosEnvSettings.getInstance().getPrintSettings()
				.getPaymentReceiptSettings()
				.getItemGroupingMethod();
		
		String key="";
		
		orderDtlMapList=new HashMap<String, ArrayList<BeanOrderDetail>>();
		
		ArrayList<BeanOrderDetail> orderDtlList;
		for (BeanOrderDetail dtl : order.getOrderDetailItems()) {
			
			if (groupingMethod.equals(PaymentReceiptItemGrouping.GroupBySubClass))
				key=String.valueOf(dtl.getSaleItem().getSubClassID());
			else
				key=String.valueOf(dtl.getSaleItem().getSubClass().getSuperClassID());
			
			if(orderDtlMapList.containsKey(key))
				orderDtlList=orderDtlMapList.get(key);
			else {
				orderDtlList=new ArrayList<BeanOrderDetail>();
				orderDtlMapList.put(key, orderDtlList);
			}
			orderDtlList.add(dtl);
			
		}
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
		
		itemTaxList=new LinkedHashMap<String, Double>();

		if(order.getOrderDetailItems()==null || order.getOrderDetailItems().size()<=0) return;
		
		//group item based on settings and copy into hashmap
		groupItemList(order);
		
		//calculate bill discount percentage
       mBillDiscountPercentage=getOrder().getBillDiscountPercentage();
       mBillDiscountName=PosOrderUtil.getDiscountName(order);
		
		if(mBillDiscountPercentage==0 && 
				(getOrder().getStatus()!=PosOrderStatus.Closed && getOrder().getStatus()!=PosOrderStatus.Refunded) && 
				getOrder().getPreBillDiscount()!=null &&
				!getOrder().getPreBillDiscount().equals(new PosDiscountItemProvider().getNoneDiscount())){
		
			if(!getOrder().getPreBillDiscount().isPercentage()){
				mBillDiscountPercentage=getOrder().getPreBillDiscount().getPrice() *100 /PosOrderUtil.getTotalAmount(getOrder());
			}else
				mBillDiscountPercentage=getOrder().getPreBillDiscount().getPrice();
		}
		
		
		
		printDetailsHeader();
		
		setFontStyle(Font.PLAIN);
		final Font dtlsFont=PosEnvSettings.getInstance().getPrintSettings().getPaymentReceiptSettings().getItemDetailFont();
		setFont(dtlsFont);

		int slNo = 1;

		  mGroupName="";
		
		
		int count=0;
		for (String key : orderDtlMapList.keySet()) {
			grpItemTaxList=new LinkedHashMap<String, Double>();
			++count;
			
			mGroupTotal=0;
			mGroupName=PosSubItemClassProvider.getInstance().getClassItem(PosNumberUtil.parseIntegerSafely(key)).getName();
			for (BeanOrderDetail dtl : orderDtlMapList.get(key)) {
				
				if (dtl.isVoid())	continue;
				slNo=printDetailItem(slNo, dtl,true);
	
			}
			
				advanceLine(1);
				
				printGroupSummary();
				
				// to avoid  dotted separator after last group  
				if (count!=orderDtlMapList.keySet().size()) {
					drawLine(LineStyles.SINGLE);
					advanceLine(2);
				}
		}
		 
		advanceLine(1);
		drawLine(getDefaultDetailSeparatorStyle());
		
		setFont(mFontReceipt);
//		PosOrderUtil.setExtraChargeTaxSummary(mOrder,itemTaxList);
	}

	private void printGroupSummary(){
		
		printText(TextAlign.RIGHT, "------------------");
		if (mBillDiscountPercentage>0) {
			printTextBlock(blockLeft, blockWidth,  mBillDiscountName , TextAlign.RIGHT, false);
			printText(TextAlign.RIGHT, PosCurrencyUtil.format(mGroupTotal*mBillDiscountPercentage/100));
				
		}
		
		if (PosEnvSettings.getInstance().getPrintSettings()
				.getPaymentReceiptSettings().getReceiptType()==PaymentReceiptType.Exclusive) {
 
//			printTextBlock(blockLeft, blockWidth, " Total :", TextAlign.RIGHT, false);
			printText(TextAlign.RIGHT, PosCurrencyUtil.format(mGroupTotal-PosCurrencyUtil.roundTo(mGroupTotal*mBillDiscountPercentage/100)));
//			printText(TextAlign.RIGHT, "------------------");
//			advanceLine(2);
				
			final double taxTotal= printTaxSummary(grpItemTaxList);
			
			final double groupTotal=mGroupTotal-
					PosCurrencyUtil.roundTo(mGroupTotal*mBillDiscountPercentage/100)+
					taxTotal;
					
					
			printTextBlock(blockLeft, blockWidth, mGroupName + " Total :", TextAlign.RIGHT, false);
			printText(TextAlign.RIGHT, PosCurrencyUtil.format(groupTotal));
		 	advanceLine(2);
			
			
		}else{
			 
			printTextBlock(blockLeft, blockWidth, mGroupName + " Total :", TextAlign.RIGHT, false);
			printText(TextAlign.RIGHT, PosCurrencyUtil.format(mGroupTotal-PosCurrencyUtil.roundTo(mGroupTotal*mBillDiscountPercentage/100)));
			printText(TextAlign.RIGHT, "------------------");
			advanceLine(2);

			
			printTaxSummary(grpItemTaxList);
		}
		advanceLine(2);
		
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
		printTextBlock(left, SLNO_FIELD_WIDTH, "No.", TextAlign.CENTER, false);

		/** The item level indicator **/
		left += SLNO_FIELD_WIDTH + gap;
		printTextBlock(left, ITEM_LEVEL_FIELD_WITDH, "", TextAlign.CENTER, false);

		/** The Description field **/
		left += ITEM_LEVEL_FIELD_WITDH + gap;
		printTextBlock(left, DESC_FIELD_WIDTH, "Item Name", TextAlign.CENTER,
				false);

		/** The Quantity field **/
		left += DESC_FIELD_WIDTH + gap;
	 	printTextBlock(left, QTY_FIELD_WIDTH, "Qty", TextAlign.LEFT, false);

	
		/** The Rate field **/
	 	left += QTY_FIELD_WIDTH + gap;
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
	
		
		/** The Description field **/
		left += ITEM_LEVEL_FIELD_WITDH + gap;
		printTextBlock(left, DESC_FIELD_WIDTH, PosSaleItemUtil.getItemNameToPrint(dtl.getSaleItem(), mUseAltLanguge) , TextAlign.LEFT, false);

		
		/** The Quantity field **/
		left += DESC_FIELD_WIDTH + gap;
		printTextBlock(left, QTY_FIELD_WIDTH,
				PosUomUtil.format(PosOrderUtil.getItemQuantity(dtl),dtl.getSaleItem().getUom()), TextAlign.LEFT, false);

		
		String taxSymbol="";
		if(PosEnvSettings.getInstance().getPrintSettings().getPaymentReceiptSettings().getReceiptType()==PaymentReceiptType.Normal)
			taxSymbol=(!saleItem.getTax().getCode().equalsIgnoreCase("NOTAX"))?
					(saleItem.getTaxCalculationMethod() == TaxCalculationMethod.ExclusiveOfTax?EXCLUSIVE_TAX_SYMBOL:""):"";
	
	
		
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
		left += QTY_FIELD_WIDTH + gap;
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
		
		mGroupTotal = mGroupTotal + PosCurrencyUtil.roundTo(amount) ;
		
		/** Print modifiers */
		if(PosEnvSettings.getInstance().getPrintSettings().getPaymentReceiptSettings().isModifiersVisible())
			printAttributes(dtl.getSaleItem());
		//commented by Udhay for space between lines on 25 Nov 2021
		printText(left, TOTAL_FIELD_WIDTH, TextAlign.CENTER, "\n", true, true);
		
		
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
			mGroupTotal = mGroupTotal - PosCurrencyUtil.roundTo(discAmt);
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



		double totalGst =0;
		totalGst=order.getTotalTax1()+
				order.getTotalTax2()+
				order.getTotalTax3()+
				order.getTotalServiceTax()+
				order.getTotalGST()+
				PosOrderUtil.getExtraChargeTotalTaxAmount(order) -
				order.getBillTaxAmount();
		
		final String totalDtlAmtText = PosCurrencyUtil.format(subTotal);
		
		final double extraCharge=mOrder.getExtraCharges() + 
				(PosEnvSettings.getInstance().getPrintSettings().getPaymentReceiptSettings().getReceiptType()==PaymentReceiptType.Inclusive ?
						PosOrderUtil.getExtraChargeTotalTaxAmount(mOrder):0);		

		final double totalDiscAmt=PosOrderUtil.getDiscountForPaymentReceipt(order, subTotal+extraCharge);
		
		if(order.getExtraCharges()>0 && 
				PosEnvSettings.getInstance().getPrintSettings().getPaymentReceiptSettings().getReceiptType()!=PaymentReceiptType.Inclusive)
			totalExclusiveTaxAmt+= PosOrderUtil.getExtraChargeTotalTaxAmount(mOrder);
	
		if(totalDiscAmt>0 ){
		 	totalExclusiveTaxAmt=totalExclusiveTaxAmt-PosOrderUtil.getDiscountForPaymentReceipt(order, totalExclusiveTaxAmt);
		}
		
		if (orderDtlMapList.keySet().size()>1){
		 	
			if(order.getOrderDetailItems() !=null && order.getOrderDetailItems().size()>0){
	
				printTextBlock(blockLeft, blockWidth,  " Total :", TextAlign.RIGHT, false);
				printText(TextAlign.RIGHT, totalDtlAmtText);
			}else if(order.getRoundAdjustmentAmount()!=0){ 
				
				printTextBlock(blockLeft, blockWidth,  " Total :", TextAlign.RIGHT, false);
				printText(TextAlign.RIGHT, PosCurrencyUtil.format(order.getTotalAmount()));
			}
	
			
			if(extraCharge>0){ 
	
				printTextBlock(blockLeft, blockWidth, "Extra Charges :", TextAlign.RIGHT,
						false);
				printText(TextAlign.RIGHT, PosCurrencyUtil.format(extraCharge));
			}
			
			 
				
			if(totalDiscAmt>0 ){
				advanceLine(1);
				printTextBlock(blockLeft, blockWidth,mBillDiscountName  , TextAlign.RIGHT,	false);
				printText(TextAlign.RIGHT, PosCurrencyUtil.format(totalDiscAmt));
				
			}
			
			final String taxSymbol= (PosEnvSettings.getInstance().getPrintSettings().getPaymentReceiptSettings().getReceiptType()==
					PaymentReceiptType.Normal) ?EXCLUSIVE_TAX_SYMBOL:"";
			
			
			final String totalExclusiveTaxAmtText = PosCurrencyUtil.format(totalExclusiveTaxAmt);
			if (Double.parseDouble(totalExclusiveTaxAmtText)  > 0) {
				
				printTextBlock(blockLeft, blockWidth, taxSymbol + " Tax Amount :",
						TextAlign.RIGHT, false);
				printText(TextAlign.RIGHT, totalExclusiveTaxAmtText);
			}
			

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

			if(PosEnvSettings.getInstance().getBillParams().getTaxSummaryDisplayType()==BeanBillParam.SHOW_TAX_TABLE  ){

				printMoneyInText((isBillPrinting && unpaidAmt!=0)? unpaidAmtText: netAmtText);
				advanceLine(2);
				printSingleLine();
				printTaxSummary(itemTaxList);

			}else if(PosEnvSettings.getInstance().getBillParams().getTaxSummaryDisplayType()==BeanBillParam.SHOW_TAX_SUMMARY){

				printTextBlock(blockLeft, blockWidth, "Total includes TAX of :", TextAlign.RIGHT,false);
				printText(TextAlign.RIGHT, PosCurrencyUtil.format(totalGst));
				printMoneyInText((isBillPrinting && unpaidAmt!=0)? unpaidAmtText: netAmtText);
			}else {
				printMoneyInText((isBillPrinting && unpaidAmt!=0)? unpaidAmtText: netAmtText);
				 
				
				
			}
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
		
		
		
		final BeanTax tax=PosTaxItemProvider.getInstance().getTaxItem(itemTax.getId());
		
	
	 
		double t1TotalAmt;
		double t2TotalAmt;
		double t3TotalAmt;
		double scTotalAmt;
		
		double t1GrpTotalAmt;
		double t2GrpTotalAmt;
		double t3GrpTotalAmt;
		double scGrpTotalAmt;
		
		double t1Amt=PosOrderUtil.getTotalT1Amount(item);
		double t2Amt=PosOrderUtil.getTotalT2Amount(item);
		double t3Amt=PosOrderUtil.getTotalT3Amount(item);
		double scAmt=PosOrderUtil.getTotalServiceTaxAmount(item);
		
		t1Amt-=PosCurrencyUtil.roundTo(t1Amt*mBillDiscountPercentage/100);
		t2Amt-=PosCurrencyUtil.roundTo(t2Amt*mBillDiscountPercentage/100);
		t3Amt-=PosCurrencyUtil.roundTo(t3Amt*mBillDiscountPercentage/100);
		scAmt-=PosCurrencyUtil.roundTo(scAmt*mBillDiscountPercentage/100);
		
		
		
		if (t1Amt>0) {
		
			final String taxKey =tax.getTaxOneName() +  "@" + tax.getTaxOnePercentage();;
			
			t1TotalAmt=t1Amt;
			t1GrpTotalAmt=t1Amt;
			
			if (itemTaxList.containsKey(taxKey)) 
				t1TotalAmt+= itemTaxList.get(taxKey);
			itemTaxList.put(taxKey, t1TotalAmt);
			
			
			if (grpItemTaxList.containsKey(taxKey)) 
				t1GrpTotalAmt+= grpItemTaxList.get(taxKey);
			grpItemTaxList.put(taxKey, t1GrpTotalAmt);
			
			
		}
		
		if (t2Amt>0) {
			
			final String taxKey =tax.getTaxTwoName()  + "@" + tax.getTaxTwoPercentage();
			
			t2TotalAmt=t2Amt;
			t2GrpTotalAmt=t2Amt;
			
			if (itemTaxList.containsKey(taxKey)) 
				t2TotalAmt+= itemTaxList.get(taxKey);
			itemTaxList.put(taxKey, t2TotalAmt);
			
			
			if (grpItemTaxList.containsKey(taxKey)) 
				t2GrpTotalAmt+= grpItemTaxList.get(taxKey);
			grpItemTaxList.put(taxKey, t2GrpTotalAmt);
			
		}
		
		if (t3Amt>0) {
			
			final String taxKey =tax.getTaxThreeName()  + "@" + tax.getTaxThreePercentage() ;
			
			t3TotalAmt=t3Amt;
			t3GrpTotalAmt=t3Amt;
			
			if (itemTaxList.containsKey(taxKey)) 
				t3TotalAmt+= itemTaxList.get(taxKey);
			itemTaxList.put(taxKey, t3TotalAmt);
			
			
			if (grpItemTaxList.containsKey(taxKey)) 
				t3GrpTotalAmt+= grpItemTaxList.get(taxKey);
			grpItemTaxList.put(taxKey, t3GrpTotalAmt);
			
		}
		

	if (scAmt>0) {
		
		final String taxKey =tax.getServiceTaxName()  +  "@" + tax.getServiceTaxPercentage() ;
		
		scTotalAmt=scAmt;
		scGrpTotalAmt=scAmt;
		
		if (itemTaxList.containsKey(taxKey)) 
			scTotalAmt+= itemTaxList.get(taxKey);
		itemTaxList.put(taxKey, scTotalAmt);
		
		
		if (grpItemTaxList.containsKey(taxKey)) 
			scGrpTotalAmt+= grpItemTaxList.get(taxKey);
		grpItemTaxList.put(taxKey, scGrpTotalAmt);
		
		
	}

		 
	}
	
 
	
	/**
	 * 
	 */
	protected double printTaxSummary(LinkedHashMap<String, Double> map){
 
		advanceLine(2);
//		blockWidth=F+1+
//				ITEM_LEVEL_FIELD_WITDH+1+
//				QTY_FIELD_WIDTH+1 ;
		
		final int taxNameWidth= DESC_FIELD_WIDTH + SLNO_FIELD_WIDTH+ITEM_LEVEL_FIELD_WITDH;
		final int taxSeparatorWidth=10;
		final int taxPerWidth= QTY_FIELD_WIDTH+10;
		double total=0;
		
		for(String key :map.keySet()) {
			
			String[] arrKey= key.split("@");
			
			
			printTextBlock(0, taxNameWidth,
					arrKey[0], TextAlign.RIGHT,
					false);
			
			printTextBlock(taxNameWidth, taxSeparatorWidth,
					"@", TextAlign.CENTER,
					false);
			
			printTextBlock(taxNameWidth+taxSeparatorWidth, taxPerWidth,
					arrKey[1] + "%", TextAlign.RIGHT,
					false);
			
			printText(TextAlign.RIGHT, PosCurrencyUtil.format(map.get(key) ));
			
			total+=map.get(key);
			
			advanceLine(1);
		}
		
		if (total>0) {
			
		
			printText(TextAlign.RIGHT, "------------------");
			
			printTextBlock(blockLeft, blockWidth, "Tax Total :", TextAlign.RIGHT, false);
			printText(TextAlign.RIGHT, PosCurrencyUtil.format(total));
		}
		return total;

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
	private boolean printPaymentInfo(String paymenTitle, double amount,int left, int width){

		boolean isPrinted=false;

		if(amount!=0){

			printTextBlock(left, width, paymenTitle, TextAlign.LEFT,
					false);
			printTextBlock(left, width, PosCurrencyUtil.format(amount), TextAlign.RIGHT,
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
	
	
	private void printKitchenReceiptDetails() throws SQLException{
		String kitchenCode="";
		String kitchenQueueNos="";
		
		ArrayList<BeanOrderKitchenQueue> kitchenQueueList=PosOrderKitchenQueueProvider.getInstance().getKitchenQueueNos(mOrder.getOrderId());
		 Collections.sort(kitchenQueueList);
		for(BeanOrderKitchenQueue kitchenQueue:kitchenQueueList){
			
			if (!kitchenCode.trim().equals(kitchenQueue.getKitchenCode()) ){
				if (!kitchenCode.trim().equals("") && !kitchenQueueNos.trim().equals("")){
					printText(TextAlign.LEFT, kitchenCode + " : "+kitchenQueueNos );
					kitchenQueueNos="";
				}
				
			}
			kitchenCode=kitchenQueue.getKitchenCode();
			kitchenQueueNos+=((kitchenQueueNos.trim().equals("")?"":",")+kitchenQueue.getKitchenQueueNo());
		}
		if (!kitchenCode.trim().equals("") && !kitchenQueueNos.trim().equals("")){
			printText(TextAlign.LEFT, kitchenCode + " : "+kitchenQueueNos);
		}
		
	}
 
	
}
