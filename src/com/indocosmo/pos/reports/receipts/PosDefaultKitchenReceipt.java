/**
 * 
 */
package com.indocosmo.pos.reports.receipts;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Properties;

import com.indocosmo.pos.common.PosEnvSettings;
import com.indocosmo.pos.common.enums.PosOrderServiceTypes;
import com.indocosmo.pos.common.utilities.PosCurrencyUtil;
import com.indocosmo.pos.common.utilities.PosDateUtil;
import com.indocosmo.pos.common.utilities.PosFileUtils;
import com.indocosmo.pos.common.utilities.PosNumberUtil;
import com.indocosmo.pos.common.utilities.PosOrderUtil;
import com.indocosmo.pos.common.utilities.PosPrintingUtil;
import com.indocosmo.pos.common.utilities.PosSaleItemUtil;
import com.indocosmo.pos.common.utilities.PosStringUtil;
import com.indocosmo.pos.common.utilities.PosUomUtil;
import com.indocosmo.pos.data.beans.BeanBillParam;
import com.indocosmo.pos.data.beans.BeanOrderDetail;
import com.indocosmo.pos.data.beans.BeanOrderHeader;
import com.indocosmo.pos.data.beans.BeanOrderKitchenQueue;
import com.indocosmo.pos.data.beans.BeanOrderHeader.PosOrderStatus;
import com.indocosmo.pos.data.beans.BeanSaleItem;
import com.indocosmo.pos.data.beans.BeanSaleItemCombo;
import com.indocosmo.pos.data.beans.BeanServingTable;
import com.indocosmo.pos.data.beans.terminal.device.BeanKitchen;
import com.indocosmo.pos.data.providers.shopdb.PosCurrencyProvider;
import com.indocosmo.pos.data.providers.shopdb.PosCustomerProvider;
import com.indocosmo.pos.data.providers.shopdb.PosKitchenProvider;
import com.indocosmo.pos.data.providers.shopdb.PosOrderKitchenQueueProvider;
import com.indocosmo.pos.data.providers.shopdb.PosSaleItemProvider;
import com.indocosmo.pos.data.providers.shopdb.PosWaiterProvider;
import com.indocosmo.pos.reports.base.PosPrintableReceiptBase;
import com.indocosmo.pos.reports.base.PosPrintableBase.TextAlign;

/**
 * @author Deepak
 *
 */
public class PosDefaultKitchenReceipt extends PosPrintableReceiptBase{
	
	private static final String PROPERTY_FILE="pos-terminal-receipt-kitchen-default.properties"; 

	/** the size of itemLevelField field **/
	private int ITEM_LEVEL_FIELD_SIZE = 7;

	/** the size of Quantity field **/
	private int QTY_FIELD_SIZE = 40;//13;
	
	/** the size of Quantity field **/
	private int SL_NO_SIZE = 10;

	/** The size of description filed **/
	private int DESC_FIELD_SIZE = 80+13;//40;

	private int PRICE_FIELD_SIZE = 0;

	private int STATUS_FIELD_SIZE = 15;
	
	private int TABLE_SEAT_INFO =32;
	
	private static final int FIELD_GAP=1;

	private boolean mPrintAll=false;
//	private boolean mUseAltLanguge = false;
	private int kitchenId=-1;
	private Font mFontItemDetail;
	private Font mFont;
	private String tableCodes ;
	private boolean printCurrentKitchenItemsOnly=false;
	private int mKitchenQueueNo;
	final static  String devModeText="DEVELOPMENT MODE, INDOCOSMO SYSTEMS";

	/**
	 * @return
	 */
	public int getKitchenId(){

		return kitchenId;
	}

	/**
	 * @param kitchenId
	 */
	public void setKitchenId(int kitchenId){

		this.kitchenId=kitchenId;
	}
	
	/**
	 * @return the printCurrentKitchenItemsOnly
	 */
	public boolean isPrintCurrentKitchenItemsOnly() {
		return printCurrentKitchenItemsOnly;
	}

	/**
	 * @param printCurrentKitchenItemsOnly the printCurrentKitchenItemsOnly to set
	 */
	public void setPrintCurrentKitchenItemsOnly(boolean printCurrentKitchenItemsOnly) {
		this.printCurrentKitchenItemsOnly = printCurrentKitchenItemsOnly;
	}
	
	
 

	/**
	 * @return the mKitchenQueueNo
	 */
	public int getKitchenQueueNo() {
		return mKitchenQueueNo;
	}

	/**
	 * @param mKitchenQueueNo the mKitchenQueueNo to set
	 */
	public void setKitchenQueueNo(int mKitchenQueueNo) {
		this.mKitchenQueueNo = mKitchenQueueNo;
	}

	/**
	 * @throws IOException 
	 * 
	 */
	public PosDefaultKitchenReceipt() throws IOException {
		
		mFontReceipt = new Font("Ariel", Font.PLAIN, 8);
		mFontItemDetail=PosEnvSettings.getInstance().getPrintSettings().getKitchenReceiptSettings().getItemDetailFont();
		mFont=PosEnvSettings.getInstance().getPrintSettings().getKitchenReceiptSettings().getFont();
//		setOverridePrinterSettings(true);
		loadDefaultKitchenReceiptSettings();
	}


	/* (non-Javadoc)
	 * @see com.indocosmo.pos.reports.receipts.PosPrintableReceiptBase#onPrintHeader(com.indocosmo.pos.data.beans.BeanOrderHeader, com.indocosmo.pos.data.beans.BeanBillParam)
	 */
	@Override
	protected void onPrintHeader(BeanOrderHeader order, BeanBillParam param)
			throws Exception {
		
		
		if (PosEnvSettings.getInstance().getPrintSettings().getKitchenReceiptSettings().getNoOfBlankLinesHdr()>0)
			advanceTextLine(PosEnvSettings.getInstance().getPrintSettings().getKitchenReceiptSettings().getNoOfBlankLinesHdr());
		
		
		
		if(PosEnvSettings.getInstance().isDevelopmentMode()) {
			
			setFontStyle(Font.BOLD);
			setFontSize(12.0f);
			
			printText(TextAlign.CENTER, "DEVELOPMENT MODE");
			printText(TextAlign.CENTER, "DEVELOPMENT MODE");
			printText(TextAlign.CENTER, "DEVELOPMENT MODE");
			
		}
	
		if(order.getStatus().equals(PosOrderStatus.Void)){
			setFontStyle(Font.BOLD);
			setFontSize(12.0f);
			
			printText(TextAlign.CENTER, "ORDER CANCELLED");
			
		}else if(mPrintAll){
			
			setFontStyle(Font.BOLD);
			setFontSize(10.0f);
			
			printText(TextAlign.LEFT, "ALL ITEMS..");
		}
		
		setFontStyle(Font.BOLD);
		setFontSize(14.0f);
		
		BeanKitchen currentKitchen=PosKitchenProvider.getInstance().getKitchenById( getKitchenId());
		String queueNoText="";
		if (mKitchenQueueNo!=0){
			queueNoText= (PosEnvSettings.getInstance().getPrintSettings().
							getKitchenReceiptSettings().
							isShowKitchenQueuePrefix()?
							currentKitchen.getCode()+ "-":"") +String.valueOf(mKitchenQueueNo) ;
		}
		
		if (order.getStatus()== PosOrderStatus.Closed || order.getStatus()==PosOrderStatus.Refunded){
		
			printText(TextAlign.CENTER, order.getInvoiceNo());
			advanceLine(3);
			
		}else if (order.getAliasText()!=null && !order.getAliasText().trim().equals("")){
				
			printText(TextAlign.CENTER, order.getAliasText() + "[" + queueNoText + "]");
			advanceLine(3);
			
		}else {
			
			printText(TextAlign.CENTER, queueNoText);
			advanceLine(3);
			
		}
			 
//		 if (PosEnvSettings.getInstance().getUISetting().useOrderQueueNo()){
//				
//				printText(TextAlign.CENTER, (mKitchenQueueNo!=0?String.valueOf(mKitchenQueueNo):""));
////				printText(TextAlign.CENTER, PosOrderUtil.getFormatedOrderQueueNo(order));
//				advanceLine(3);
//				
//			}
		
		 
		String hdrLine0 = order.getOrderServiceType()
				.getDisplayText()
				+ ((order.getOrderServiceType() == PosOrderServiceTypes.TABLE_SERVICE) ?  PosOrderUtil.getServingTableName(order)
						: "");
		
	
		
	 
		final String hdrLin1 = "Loc. : "
				+ ((currentKitchen!=null)?currentKitchen.getName():"");
		final String hdrLine2 = "Ref# : " + PosOrderUtil.getShortOrderIDFromOrderID(order.getOrderId());
		
		final String hdrLine1R ="Dt. : " +PosDateUtil.formatLocal(PosDateUtil.getDate()) +" " +PosDateUtil.format(PosDateUtil.SHORT_TIME_FORMAT_12, PosDateUtil.getDateTime());

		
	
//		setFontSize(10.0f);
//		setFontStyle(Font.BOLD);
//		
		setFont(mFont);
		if (hdrLine0.trim().length() > 0) {
			
			printText(TextAlign.CENTER, hdrLine0);
		}
//		setFont(mFontReceipt);
		if (hdrLine1R.trim().length() > 0) {
			printText(TextAlign.LEFT, hdrLine1R,true);
		}
		if (hdrLin1.trim().length() > 0) {
			printText(TextAlign.LEFT, hdrLin1,true);
		}
			
		
		if (hdrLine2.trim().length() > 0) {
			printText(TextAlign.LEFT, hdrLine2,true);
		}
		

//		String footerLine_ServiceType = "Service Type :";
		String footerLine_Covers="";
		String footerLine_ServedBy = "Served By : ";
//		footerLine_ServiceType += order.getOrderServiceType()
//				.getDisplayText();

		switch (order.getOrderServiceType()) {
		case HOME_DELIVERY:
			footerLine_ServedBy += ((order.getServedBy() != null) ? order
					.getServedBy().getFirstName() : "_____________") ;
					break;
		case TABLE_SERVICE:
			ArrayList<BeanServingTable> tables = PosOrderUtil
			.getAllServingTables(order);
			tableCodes = "";
			for (BeanServingTable tbl : tables) {
				tableCodes += tbl.getName() + ",";
			}
			tableCodes = tableCodes.substring(0, tableCodes.length() - 1) ;
//			footerLine_ServiceType += tableCodes;
			if(order.getCovers()>0)
				footerLine_Covers = "Covers :"+order.getCovers();
			
			if( order.getServedBy().getCode().equals(PosWaiterProvider.DEF_SYSTEM_WAITER_CDOE))
				footerLine_ServedBy += order.getUser().getName();
			else
				footerLine_ServedBy += (order.getServedBy() != null) ? order
						.getServedBy().getFirstName() : "";
			break;
		case TAKE_AWAY:
			footerLine_ServedBy += order.getUser().getName();
			break;
		}
		
		footerLine_ServedBy+= " [" + order.getStationCode() + "]";
		
		
		if(footerLine_Covers.trim().length() > 0){
			
//			final int tableCodeTextWidth=getTextWidth(tableCodes);
//			final int tableCodeTextBlockMaxSize=70;
//			final int textBlockSize=(tableCodeTextWidth>tableCodeTextBlockMaxSize)?tableCodeTextBlockMaxSize:tableCodeTextWidth;
			
//			printTextBlock(0,250,footerLine_ServiceType,TextAlign.LEFT, false);
//			printTextBlock(75,250," [",TextAlign.LEFT, false);
//			printTextBlock(80,textBlockSize,tableCodes,TextAlign.LEFT, false);
//			printTextBlock(80+textBlockSize,30,((tableCodeTextWidth>tableCodeTextBlockMaxSize)?"...":"") + "]",TextAlign.LEFT, false);
			printText(TextAlign.LEFT, footerLine_ServedBy,false);
			printText(TextAlign.RIGHT, footerLine_Covers);
		}else
			printText(TextAlign.LEFT, footerLine_ServedBy);
		
//		footerLine_ServedBy+= " [" + order.getStationCode() + "]";
//		printText(TextAlign.LEFT, footerLine_ServedBy);

		printSingleLine();
	}

	
	/**
	 * 
	 */
	protected void printDetailsHeader() {

		final int gap = FIELD_GAP;
		int left = 0;
		
		/** The SL No field **/
		printTextBlock(left, SL_NO_SIZE, "No. ", TextAlign.LEFT, false);
		
		/** The item level field **/
		left += SL_NO_SIZE + gap;
		printTextBlock(left, ITEM_LEVEL_FIELD_SIZE, " ", TextAlign.LEFT, false);
		
		if(getOrder().getOrderServiceType()==PosOrderServiceTypes.TABLE_SERVICE){

			left += ITEM_LEVEL_FIELD_SIZE + gap;
			printTextBlock(left, DESC_FIELD_SIZE, "Description", TextAlign.CENTER,
					false);

			/** The Tbl/Seat info **/
			left += DESC_FIELD_SIZE + gap;
			printTextBlock(left, TABLE_SEAT_INFO, "Tbl/Seat", TextAlign.CENTER, false);

			/** The Quantity field **/
			left += TABLE_SEAT_INFO + gap;
			printTextBlock(left, QTY_FIELD_SIZE, "QTY", TextAlign.CENTER, false);
		
		}else{
			
			left += ITEM_LEVEL_FIELD_SIZE + gap;
			printTextBlock(left, DESC_FIELD_SIZE + TABLE_SEAT_INFO, "Description", TextAlign.CENTER,
					false);

			/** The Quantity field **/
			left += DESC_FIELD_SIZE + TABLE_SEAT_INFO + gap;
			printTextBlock(left, QTY_FIELD_SIZE, "Q.", TextAlign.CENTER, false);				
			
		}
		
		/** The Description field **/
		left += QTY_FIELD_SIZE + gap;
		//		printTextBlock(left, PRICE_FIELD_SIZE, "Price", TextAlign.CENTER, false);

		/** The Total Amount field **/
//		left += PRICE_FIELD_SIZE + gap;
		printTextBlock(left, STATUS_FIELD_SIZE, "St.", TextAlign.CENTER, true);
		
		printSingleLine();
	}

	/**
	 * @param itemList
	 * @throws CloneNotSupportedException 
	 */
	protected void printDetails(ArrayList<BeanOrderDetail> itemDetList) throws CloneNotSupportedException{
		
		
		ArrayList<BeanOrderDetail> itemList = new ArrayList<BeanOrderDetail>();
		for (BeanOrderDetail item : itemDetList)
			itemList.add(item.clone());
		
		Collections.sort(itemList);
		
		int itemCount = 0;
		for (BeanOrderDetail dtlItem : itemList) {
			itemCount = itemCount+1;
			if ( dtlItem.getSaleItem().isPrintableToKitchen() && (mPrintAll  || !dtlItem.isPrintedToKitchen())) 
				printDetails( dtlItem,itemCount);
		}
		
		
	}

	/**
	 * @param dtlItem
	 * @param itemCount
	 * @throws CloneNotSupportedException 
	 */
	protected void printDetails(BeanOrderDetail dtlItem, int itemCount) throws CloneNotSupportedException{
		
		

		if(!printCurrentKitchenItemsOnly || dtlItem.getSaleItem().getKitchenId()==getKitchenId()){
			
			int left = 0; 
			
			/** The SL No field **/
			String slNo = String.valueOf(itemCount);
			if(dtlItem.getParentDtlId()==null || dtlItem.getParentDtlId().trim().isEmpty())
				printTextBlock(left, SL_NO_SIZE, slNo,TextAlign.LEFT, false);

			/** The Item level field **/
			left += SL_NO_SIZE + FIELD_GAP;
			final String itemLevelChar =PosPrintingUtil.getItemLevelIndicator(dtlItem);
			printTextBlock(left, ITEM_LEVEL_FIELD_SIZE, itemLevelChar,
					TextAlign.CENTER, false);

			/** The Description field **/
			left += ITEM_LEVEL_FIELD_SIZE + FIELD_GAP;
			int descWidth=getDescriptionFieldSize(getOrder().getOrderServiceType());
			if(dtlItem.isVoid()){
				printTextBlock(left, descWidth,"===================", TextAlign.LEFT, false);
			}
			
			
			String desc = PosSaleItemUtil.getItemNameToPrint(dtlItem.getSaleItem(),mUseAltLanguge);
			
			printTextBlock(left, descWidth,desc, TextAlign.LEFT, false);


			/** The Quantity field **/
			setFontStyle(Font.BOLD);
			left += descWidth+ FIELD_GAP;
			int tableFieldInfoWidth=getTableSeatFieldSize(getOrder().getOrderServiceType());
			
			if(getOrder().getOrderServiceType()==PosOrderServiceTypes.TABLE_SERVICE){
				
				String tabSeat= dtlItem.getServingTable().getCode(); 
				if(dtlItem.getServingSeat()>0){
					tabSeat+=  "/" +PosStringUtil.paddLeft(String.valueOf(dtlItem.getServingSeat()),2,'0');
				}
				printTextBlock(left, tableFieldInfoWidth,tabSeat, TextAlign.RIGHT, false);
			}
			
			left += tableFieldInfoWidth + FIELD_GAP;
			String qty =PosUomUtil.format(dtlItem.getSaleItem().getQuantity(),dtlItem.getSaleItem().getUom());
			printTextBlock(left, QTY_FIELD_SIZE, qty,
					TextAlign.RIGHT, false);
			setFont(mFontItemDetail);

		
			left += QTY_FIELD_SIZE + FIELD_GAP;
			/** Status Filed **/
//			left += PRICE_FIELD_SIZE+ FIELD_GAP;
			printTextBlock(left, STATUS_FIELD_SIZE, PosOrderUtil.getStatusString(dtlItem),
					TextAlign.CENTER, true,true);

			printAttributes(dtlItem);
			
			if (dtlItem.getSaleItem().isComboItem()
					&& ((BeanSaleItemCombo) dtlItem.getSaleItem()).hasContentItems()) {
				ArrayList<BeanSaleItem> contenItemList = ((BeanSaleItemCombo) dtlItem.getSaleItem())
						.getComboContentItemList();
				for (BeanSaleItem contentItem : contenItemList) {
					
					printText(QTY_FIELD_SIZE+6,DESC_FIELD_SIZE,TextAlign.LEFT,PosSaleItemUtil.getItemNameToPrint(contentItem, mUseAltLanguge) ,true);

				}
			}
			
			//item remarks 
			printTextBlock(QTY_FIELD_SIZE+6,descWidth+FIELD_GAP+tableFieldInfoWidth+FIELD_GAP,dtlItem.getRemarks(),TextAlign.LEFT,true);

			if(dtlItem.getSaleItem().isOpen() && !dtlItem.isVoid()){
				
				setFontStyle(Font.BOLD);
				printText(QTY_FIELD_SIZE+6,DESC_FIELD_SIZE,TextAlign.LEFT,"Item price is open.",true);
				printText(QTY_FIELD_SIZE+6,DESC_FIELD_SIZE,TextAlign.LEFT,"Current price : " + PosCurrencyUtil.format(dtlItem.getSaleItem().getFixedPrice()),true);
				printText(QTY_FIELD_SIZE+6,DESC_FIELD_SIZE,TextAlign.LEFT,"Updated price ?____________________",true);
				setFont(mFontItemDetail);
				
			}

		}

		if(dtlItem.hasSubItems()){
			if(dtlItem.isComboContentsSelected()){
				for(ArrayList<BeanOrderDetail> ccList:dtlItem.getComboSubstitutes().values()){
					printDetails(ccList);
				}
			}
			if(dtlItem.isExtraItemsSelected()){
				for(ArrayList<BeanOrderDetail> ccList:dtlItem.getExtraItemList().values()){
					printDetails(ccList);
				}
			}
		}
		
		
	}
	
	private int getDescriptionFieldSize(PosOrderServiceTypes type){
		
		final int width=(type==PosOrderServiceTypes.TABLE_SERVICE)?DESC_FIELD_SIZE:DESC_FIELD_SIZE+TABLE_SEAT_INFO;
		
		return width;
			
				
	}
	
	private int getTableSeatFieldSize(PosOrderServiceTypes type){
		
		final int width=(type==PosOrderServiceTypes.TABLE_SERVICE)?TABLE_SEAT_INFO:0;
		
		return width;
			
				
	}


	/* (non-Javadoc)
	 * @see com.indocosmo.pos.reports.receipts.PosPrintableReceiptBase#onPrintDetails(com.indocosmo.pos.data.beans.BeanOrderHeader)
	 */
	@Override
	protected void onPrintDetails(BeanOrderHeader order) throws Exception {

		printDetailsHeader();
		
		setFont(mFontItemDetail);
		
		printDetails(order.getOrderDetailItems());
		
		setFont(mFontReceipt);
		advanceLine(3);
		printText( TextAlign.LEFT,order.getRemarks() );

		advanceLine(3);
		printSingleLine();
	
	}

	/**
	 * @param beanSaleItem 
	 * 
	 */
	private void printAttributes(BeanOrderDetail dtlItem) {
		
		final BeanSaleItem beanSaleItem=dtlItem.getSaleItem();
		
		for(int i=0;i<5;i++){
			if(beanSaleItem.getAttribSelectedOption(i)!=null&&beanSaleItem.getAttribSelectedOption(i).trim().length()!=0&&!beanSaleItem.getAttribSelectedOption(i).trim().equalsIgnoreCase(PosSaleItemProvider.DEFAULT_ATTRIBUTE_OPTION)){
				
				if(dtlItem.isVoid()){
					printTextBlock(QTY_FIELD_SIZE+6,DESC_FIELD_SIZE, "===================", TextAlign.LEFT, false);
				}
				
				printText(QTY_FIELD_SIZE+6,DESC_FIELD_SIZE,TextAlign.LEFT,beanSaleItem.getAttributeName(i).trim()+" :- "+beanSaleItem.getAttribSelectedOption(i).trim(),true);
			}
		}
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.pos.reports.receipts.PosPrintableReceiptBase#onPrintBillSummary(com.indocosmo.pos.data.beans.BeanOrderHeader)
	 */// TODO Auto-generated method stub

	@Override
	protected void onPrintBillSummary(BeanOrderHeader order) throws Exception {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.indocosmo.pos.reports.receipts.PosPrintableReceiptBase#onPrintPaymentSummary(com.indocosmo.pos.data.beans.BeanOrderHeader)
	 */
	@Override
	protected void onPrintPaymentSummary(BeanOrderHeader order)
			throws Exception {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.indocosmo.pos.reports.receipts.PosPrintableReceiptBase#onPrintFooter(com.indocosmo.pos.data.beans.BeanBillParam, com.indocosmo.pos.data.beans.BeanOrderHeader)
	 */
	@Override
	protected void onPrintFooter(BeanBillParam param, BeanOrderHeader order)
			throws Exception {
		
		if(PosEnvSettings.getInstance().isDevelopmentMode()) {
 
			printText(TextAlign.CENTER, devModeText);
			printText(TextAlign.CENTER, devModeText);
			printText(TextAlign.CENTER, devModeText);
			advanceTextLine(1);
			
		}
	
//		if(mPrintAll)
//			printPreviousReceiptDetails();
		
		if (mPrintAll || 
				PosEnvSettings.getInstance().getPrintSettings().getKitchenReceiptSettings().showRelatedKitchens())
 				printPreviousReceiptDetails();
			
//			printRelatedKitchehs();
//			if(mPrintAll)
//				printPreviousReceiptDetails();
//			else
//				printRelatedKitchehs();
			
 
		
		printCustomerInfo(order);
		
		if(order.getStatus().equals(PosOrderStatus.Void)){
			
			setFontStyle(Font.BOLD);
			setFontSize(12.0f);
			
			printText(TextAlign.CENTER, "ORDER CANCELLED");
			
		}
		
		if(PosEnvSettings.getInstance().getPrintSettings().getKitchenReceiptSettings().showBarcode())
			printOrderIDBarcode();
		
		
		if(PosEnvSettings.getInstance().getPrintSettings().getKitchenReceiptSettings().getNoOfBlankLinesFooter()>0)
			advanceTextLine(PosEnvSettings.getInstance().getPrintSettings().getKitchenReceiptSettings().getNoOfBlankLinesFooter());
		
		printText(TextAlign.CENTER, "." , true, true);
		
	}
	
	
	private void printPreviousReceiptDetails() throws SQLException{
		
		ArrayList<BeanOrderKitchenQueue> kitchenQueueList=PosOrderKitchenQueueProvider.getInstance().getKitchenQueueNos(mOrder.getOrderId());
		Collections.sort(kitchenQueueList);
		 
		 
		String kitchenCode="";
		String kitchenQueueNos="";
			
		setFontStyle(Font.BOLD);
		setFontSize(11.0f);
		
		for(BeanOrderKitchenQueue kitchenQueue:kitchenQueueList){
			
				
			if (!kitchenCode.trim().equals(kitchenQueue.getKitchenCode())){
				
				if (!kitchenCode.trim().equals("") && !kitchenQueueNos.trim().equals("")){
					
					printText(TextAlign.LEFT, kitchenCode + " : ["+kitchenQueueNos+"]");
					kitchenQueueNos="";
				}
				
			}
			kitchenCode=kitchenQueue.getKitchenCode();
			if (mKitchenQueueNo!=kitchenQueue.getKitchenQueueNo() || kitchenQueue.getKitchenId()!=getKitchenId())
				kitchenQueueNos+=((kitchenQueueNos.trim().equals("")?"":",")+kitchenQueue.getKitchenQueueNo());
		}
		if (!kitchenCode.trim().equals("") && !kitchenQueueNos.trim().equals("")){
			printText(TextAlign.LEFT, kitchenCode + " : ["+kitchenQueueNos+"]");
		}
		setFont(mFontReceipt);
			
	}
	
	private void printRelatedKitchehs(){

		String footerLineKitchens="";
		
		
			
			ArrayList<BeanKitchen> kitchenList= PosOrderUtil.getKitchens(mOrder,!mPrintAll);
			BeanKitchen currentKitchen=PosKitchenProvider.getInstance().getKitchenById( getKitchenId());
			footerLineKitchens += ((currentKitchen!=null)?currentKitchen.getName():"");
			
			
			if( kitchenList.size()>1){
//				footerLineKitchens += " [ ";
				
//				boolean multipkeKitchens=false;
				for (BeanKitchen kitchen : kitchenList) {
					
					if ( currentKitchen==null || kitchen.getId()!=currentKitchen.getId()){
						
						if(footerLineKitchens.length()>0) 
							footerLineKitchens += ", "; 
						
//						footerLineKitchens += kitchen.getCode() ;
						footerLineKitchens += kitchen.getName() ;
//						multipkeKitchens=true;
					}
					
				}
				
//				footerLineKitchens += " ] ";
					
			} 
			
			if(footerLineKitchens!=null && footerLineKitchens.trim().length()>0)
				printText(TextAlign.LEFT, "Kitchen(s) : ["+footerLineKitchens+"]");
	 
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.pos.reports.PosPrintableReportBase#onInitPrint(java.awt.Graphics2D, java.awt.print.PageFormat)
	 */
	@Override
	protected void onInitialized(Graphics2D g2d, PageFormat pf) throws Exception {
//		setFont(mFontReceipt);
	}

	/**
	 * @param mPrintAll the mPrintAll to set
	 */
	public void setPrintAll(boolean mPrintAll) {
		this.mPrintAll = mPrintAll;
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.pos.reports.PosPrintableReportBase#isPrintable()
	 */
	@Override
	public boolean isPrintable() {

		boolean printable=false;
		for (BeanOrderDetail dtlItem : mOrder.getOrderDetailItems()) {
			if (mPrintAll || !dtlItem.isPrintedToKitchen()) 
				if(canPrintToKitchen(dtlItem)){
					printable=true;
					break;
				}
		}

		return printable;
	}

	/**
	 * @param dtlItems
	 * @return
	 */
	private boolean canPrintToKitchen(ArrayList<BeanOrderDetail> dtlItems){

		boolean printable=false;
		for (BeanOrderDetail dtlItem : dtlItems){

			if(canPrintToKitchen(dtlItem)){
				printable=true;
				break;
			}
		}

		return printable;
	}

	/**
	 * @param dtlItem
	 * @return
	 */
	private boolean canPrintToKitchen(BeanOrderDetail dtlItem){

		boolean printable=false;
		if(!printCurrentKitchenItemsOnly || dtlItem.getSaleItem().getKitchenId()==getKitchenId()){
			printable=true;
		}else if(dtlItem.hasSubItems()){
			if(dtlItem.isComboContentsSelected()){
				for(ArrayList<BeanOrderDetail> ccList:dtlItem.getComboSubstitutes().values()){
					printable=canPrintToKitchen(ccList);
					if(printable) break;
				}
			}
			if(!printable && dtlItem.isExtraItemsSelected()){
				for(ArrayList<BeanOrderDetail> ccList:dtlItem.getExtraItemList().values()){
//					canPrintToKitchen(ccList);
					printable=canPrintToKitchen(ccList);
					if(printable) break;
				}
			}

		}

		return printable;
	}
	/**
	 * @return
	 * @throws IOException 
	 */
	
	private void loadDefaultKitchenReceiptSettings() throws IOException{
		
		Properties mPrintProperties =PosFileUtils.loadPropertyFile(PROPERTY_FILE);
		 	
		SL_NO_SIZE=
				PosNumberUtil.parseIntegerSafely(mPrintProperties.getProperty("kitchen_receipt.column_settings.sl_no.width","10"));
		
		ITEM_LEVEL_FIELD_SIZE=
				PosNumberUtil.parseIntegerSafely(mPrintProperties.getProperty("kitchen_receipt.column_settings.item_level_field.width","7"));
		DESC_FIELD_SIZE=
				PosNumberUtil.parseIntegerSafely(mPrintProperties.getProperty("kitchen_receipt.column_settings.item_name.width","93"));
		
		QTY_FIELD_SIZE=
				PosNumberUtil.parseIntegerSafely(mPrintProperties.getProperty("kitchen_receipt.column_settings.quantity.width","40"));
		
		PRICE_FIELD_SIZE=
				PosNumberUtil.parseIntegerSafely(mPrintProperties.getProperty("kitchen_receipt.column_settings.rate.width","0"));
	 	
		STATUS_FIELD_SIZE=
				PosNumberUtil.parseIntegerSafely(mPrintProperties.getProperty("kitchen_receipt.column_settings.status.width","15"));
		
		TABLE_SEAT_INFO=
				PosNumberUtil.parseIntegerSafely(mPrintProperties.getProperty("kitchen_receipt.column_settings.table_seat.width","32"));
		
 
	}
	/*
	 * 
	 */
	private void printCustomerInfo(BeanOrderHeader order){
		
//		final PosCustomerProvider pvdr=new PosCustomerProvider();
//		if(order.getOrderCustomer()==null || order.getOrderCustomer().getName().equals(pvdr.getDefaultCustomer().getName())) return;
		
		if(PosEnvSettings.getInstance().getPrintSettings().getKitchenReceiptSettings().isEnabledCustomerInfoPrinting(getOrder().getOrderServiceType().getCode())){
			setFontStyle(Font.BOLD);
			setFontSize(9f);
			
			printText(TextAlign.LEFT, "Customer Info:");
			
			setFont(mFontReceipt);
			printText(TextAlign.LEFT, order.getOrderCustomer().getName());
			printText(TextAlign.LEFT, order.getOrderCustomer().getAddress());
			printText(TextAlign.LEFT, order.getOrderCustomer().getCity());
			printText(TextAlign.LEFT, order.getOrderCustomer().getState());
			
			if(order.getOrderCustomer().getPhoneNumber()!=null && 
					order.getOrderCustomer().getPhoneNumber().trim().length()>1)
				printText(TextAlign.LEFT, order.getOrderCustomer().getPhoneNumber());
			
			if(order.getOrderCustomer().getPhoneNumber2()!=null && 
					order.getOrderCustomer().getPhoneNumber2().trim().length()>1)
				printText(TextAlign.LEFT, order.getOrderCustomer().getPhoneNumber2());
			
			if(order.getOrderCustomer().getTinNo()!=null  &&  !order.getOrderCustomer().getTinNo().trim().equals(""))
				printText(TextAlign.LEFT,"Tin No :" + order.getOrderCustomer().getTinNo());
		
		}
	}

}
