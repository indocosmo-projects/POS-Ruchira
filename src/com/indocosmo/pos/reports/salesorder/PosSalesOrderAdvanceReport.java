/**
 * 
 */
package com.indocosmo.pos.reports.salesorder;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.io.IOException;
import java.util.ArrayList;

import com.indocosmo.pos.common.PosEnvSettings;
import com.indocosmo.pos.common.enums.PosOrderServiceTypes;
import com.indocosmo.pos.common.enums.device.PrinterType;
import com.indocosmo.pos.common.exception.printing.HasNoItemsToPrintException;
import com.indocosmo.pos.common.utilities.PosCurrencyUtil;
import com.indocosmo.pos.common.utilities.PosDateUtil;
import com.indocosmo.pos.common.utilities.PosOrderUtil;
import com.indocosmo.pos.data.beans.BeanBillParam;
import com.indocosmo.pos.data.beans.BeanPayModeBase.PaymentMode;
import com.indocosmo.pos.data.beans.BeanSalesOrderReport;
import com.indocosmo.pos.data.providers.shopdb.PosBillParamProvider;
import com.indocosmo.pos.data.providers.shopdb.PosSalesOrderReportProvider;
import com.indocosmo.pos.forms.components.objectbrowser.IPosBrowsableItem;
import com.indocosmo.pos.reports.base.PosPrintableReportBase;
import com.indocosmo.pos.reports.base.PosReportPageFormat;

/**
 * @author sandhya
 * 
 */
public class PosSalesOrderAdvanceReport extends PosSalesOrderReportBase {
	
	private static final String PROPERTY_FILE="pos-terminal-receipt-pymt-prd.properties"; 

	/**
	 * Serial Number column width
	 */
	
	private static final int REPORT_HEADER_HEIGHT=150;
	private int REPORT_HEIGHT;
	private static final int REPORT_FOOTER_HEIGHT=30;
	protected static final int SL_NO_FIELD_WIDTH =40;
	protected static final int ORDER_NO_FIELD_WIDTH =  100;
	protected static final int CUSTOMER_NAME_FIELD_WIDTH =  90;
	protected static final int QTY_FIELD_WIDTH =  0;
	protected static final int ADVANCE_CASH_FIELD_WIDTH =  70;
	protected static final int ADVANCE_CARD_FIELD_WIDTH =  70;
	protected static final int EBS_FIELD_WIDTH = 60; 
	  
	private static final int H_GAP =4;
	private static final int LINE_GAP=8;

	private static final int START_INDEX=4; // GAP B/W Line & First character 
	private static final int END_INDEX=4;
	
	private int DESC_FIELD_WIDTH=0;
	private int PAGE_HDR_DATA_WIDTH; 
	
	private BeanBillParam mBillParam;

	private int report_itemIndex=0;
	private int report_slNo = 0;

	private int page_itemIndex=-1;
	private int page_slNo = 0;

	private double page_totalQty=0;
	private double page_totalAdvanceCash=0;
	private double page_totalAdvanceCard=0;
	private double page_totalEBS=0;
	 
	private double report_totalQty=0;
	private double report_totalAdvanceCash=0;
	private double report_totalAdvanceCard=0;
	private double report_totalEBS=0;
	
	private String page_lastPrintedID="";
	private String report_lastPrintedID="";
	protected ArrayList<BeanSalesOrderReport> salesOrderDetails;
	/**
	 * @throws IOException 
	 * 
	 */
	public PosSalesOrderAdvanceReport() throws IOException {
		
		super();
	}

	 
	/***
	 * Loads the bill params from db
	 * 
	 * @throws Exception
	 */
	private void loadBillParams() throws Exception {

		PosBillParamProvider provider = PosBillParamProvider.getInstance();
		mBillParam = provider.getBillParam();
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

//		loadPaymentReceiptSettings();
		DESC_FIELD_WIDTH=getPosReportPageFormat().getImageableWidthInPixcel()-(
				
				SL_NO_FIELD_WIDTH+1+
				ORDER_NO_FIELD_WIDTH+1+
				CUSTOMER_NAME_FIELD_WIDTH+1+
				ADVANCE_CASH_FIELD_WIDTH+1+
				ADVANCE_CARD_FIELD_WIDTH+1+
				EBS_FIELD_WIDTH
				); 

		loadBillParams();		
		REPORT_HEIGHT=getHeight();
		PosSalesOrderReportProvider reportProvider=new PosSalesOrderReportProvider();
		salesOrderDetails=reportProvider.getDailySalesOrderAdvance(reportDate,mSelectedServices);
		if(salesOrderDetails ==null || salesOrderDetails.size()==0)
			throw new HasNoItemsToPrintException("No data to print ");
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.pos.reports.base.PosPrintableReportBase#onPageChanged(int, int)
	 */
	@Override
	protected void onPageChanged(int oldPage, int newPage) throws Exception {

		report_itemIndex=page_itemIndex;
		report_slNo=page_slNo;
		report_totalQty=page_totalQty;
		report_totalAdvanceCash=page_totalAdvanceCash;
		report_totalAdvanceCard=page_totalAdvanceCard;
		report_totalEBS=page_totalEBS;
		report_lastPrintedID=page_lastPrintedID;
	 
		
	}


	/* (non-Javadoc)PosEnvSettings.getInstance().getPrintSettings().getPRDPaymentReceiptSettings().getHsnCodeWidth()
	 * @see com.indocosmo.pos.reports.base.PosPrintableReportBase#onPageInit(int)
	 */
	@Override
	protected void initPage(int pageIndex) throws Exception {

		page_itemIndex=report_itemIndex+1;
		page_slNo=report_slNo;
		page_totalQty=report_totalQty;
		page_totalAdvanceCash=report_totalAdvanceCash;
		page_totalAdvanceCard=report_totalAdvanceCard;
		page_totalEBS=report_totalEBS;		
		isEndOfReport=false;
		page_lastPrintedID=report_lastPrintedID;
		

	}
 
	 
	/* (non-Javadoc)
	 * @see com.indocosmo.pos.reports.base.PosPrintableReportBase#initReport()
	 */
	@Override
	protected void initReport() {
		
		
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.pos.reports.base.PosPrintableReportBase#printReportHeader(int)
	 */
	@Override
	protected void printReportHeader(int pageIndex) throws Exception {
		
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.pos.reports.base.PosPrintableReportBase#printPageHeader(int)
	 */
	@Override
	protected void printPageHeader(int pageIndex) throws Exception {
		
		printHeader(mBillParam);
		
		printSingleLine();
		printDetailsHeader();
		advanceLine(2);
		printSingleLine();
 
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.pos.reports.base.PosPrintableReportBase#printPageDetails(int)
	 */
	@Override
	protected void printPageDetails(int pageIndex) throws Exception {

		printDetails();

	}

	/* (non-Javadoc)
	 * @see com.indocosmo.pos.reports.base.PosPrintableReportBase#printPageFooter(int)
	 */
	@Override
	protected void printPageFooter(int pageIndex) throws Exception {
		
	}


 
	
	/* (non-Javadoc)
	 * @see com.indocosmo.pos.reports.base.PosPrintableReportBase#printReportFooter(int)
	 */
	@Override
	protected void printReportFooter(int pageIndex) throws Exception {

		if ((getHeight()-REPORT_FOOTER_HEIGHT)>=getLastPrintedLineStartY()){
			
			 
			onPrintReportFooter();
			isEndOfReport=true;
		}

	}

	/* (non-Javadoc)
	 * @see com.indocosmo.pos.reports.base.PosPrintableReportBase#isPageExist(int)
	 */
	@Override
	protected boolean isPageExist(int pageIndex) throws Exception {

		return !isEndOfReport;
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.pos.reports.base.PosPrintableReportBase#getPosReportPageFormat()
	 */
	@Override
	public PosReportPageFormat getPosReportPageFormat() {

		return PosReportPageFormat.PAGE_A4;
	}
	/* (non-Javadoc)
	 * @see com.indocosmo.pos.reports.base.PosPrintableBase#getPrinterType()
	 */
	@Override
	public PrinterType getPrinterType() {
		
		return PrinterType.Normal;
	}
	/* (non-Javadoc)
	 * @see com.indocosmo.pos.reports.base.PosPrintableReportBase#hasMoreData()
	 */
	@Override
	protected boolean hasMoreData() throws Exception {

	
		return !isEndOfReport && page_itemIndex<salesOrderDetails.size()-1;
	}

	/**
	 * @param order
	 * @param param
	 * @throws Exception
	 */
	private void printHeader(BeanBillParam param)
			throws Exception {

		setFontSize(14.0f);
		
		advanceLine();
		final String hdrLine0 = "DAILY ADVANCE AS ON " + PosDateUtil.formatLocal(reportDate);
		final String hdrLine1 = PosEnvSettings.getInstance().getShop().getName();
		final String hdrLine2 = PosEnvSettings.getInstance().getStation().getName();
		final String hdrLine3 ="";
//		final String hdrLine4 = PosDateUtil.getDate()
//				+ "(" + getDayOfWeek() + ") " + PosDateUtil.getTime();
		
		setFontStyle(Font.BOLD);
		setFontSize(10.0f);
		printText(TextAlign.CENTER, hdrLine0);

		setFont(mFontReceipt);
		printText(TextAlign.CENTER, hdrLine1);
		printText(TextAlign.CENTER, hdrLine2);
		printText(TextAlign.CENTER, hdrLine3);
//		advanceLine(3);
//		printText(TextAlign.CENTER, hdrLine4);
		
		advanceLine(6);

	
	}

	/**
	 * @param order
	 * @throws Exception
	 */
	private void printDetails() throws Exception {

		if(salesOrderDetails ==null || salesOrderDetails.size()<=0) return ;

		 
		final Font dtlsFont=PosEnvSettings.getInstance().getPrintSettings().getPaymentReceiptSettings().getItemDetailFont();
		setFont(dtlsFont);
		
		for(;page_itemIndex<salesOrderDetails.size();page_itemIndex++){

			final BeanSalesOrderReport dtl=salesOrderDetails.get(page_itemIndex);
//			++page_slNo; 
			printDetailItem(  dtl);
			page_lastPrintedID=dtl.getOrderId();
			if(getNextLineStartY()+getLineHeight()>=(REPORT_HEIGHT-REPORT_FOOTER_HEIGHT))
				break;

		}
//		page_lastPrintedID=""; 

	}

 
 

	/**
	 * Prints the header for details
	 */
	private void printDetailsHeader() {

		setFont(mFontReceipt);
		int left =1;
		printTextBlock(left+H_GAP, SL_NO_FIELD_WIDTH-H_GAP-LINE_GAP, "SL. NO", TextAlign.CENTER, 
				false);
		left+=SL_NO_FIELD_WIDTH;
		printTextBlock(left+H_GAP, ORDER_NO_FIELD_WIDTH-H_GAP-LINE_GAP, "REF. NO", TextAlign.LEFT, 
				false);
		
		left+=ORDER_NO_FIELD_WIDTH;
		printTextBlock(left+H_GAP, CUSTOMER_NAME_FIELD_WIDTH-H_GAP-LINE_GAP, "CUSTOMER NAME", TextAlign.LEFT, 
				false);

		left+=CUSTOMER_NAME_FIELD_WIDTH;
		printTextBlock(left+H_GAP, DESC_FIELD_WIDTH-H_GAP-LINE_GAP, "ITEM", TextAlign.LEFT, 
				false);
		
		left+=DESC_FIELD_WIDTH;
		printTextBlock(left+H_GAP, ADVANCE_CASH_FIELD_WIDTH-H_GAP-LINE_GAP,PaymentMode.Cash.getDisplayText(), TextAlign.RIGHT, 
				false);
		
		left+=ADVANCE_CASH_FIELD_WIDTH;
		printTextBlock(left+H_GAP, ADVANCE_CARD_FIELD_WIDTH-H_GAP-LINE_GAP, PaymentMode.Card.getDisplayText(), TextAlign.RIGHT, 
				false);
		
		left+=ADVANCE_CARD_FIELD_WIDTH;
		printTextBlock(left+H_GAP, EBS_FIELD_WIDTH-H_GAP-LINE_GAP, PaymentMode.Online.getDisplayText(), TextAlign.RIGHT, 
				true);

	}

	/**
	 * Prints the details part
	 */
	private void printDetailItem(  BeanSalesOrderReport soDetail) {

		int left = 0;
		double advanceCash=0;
		double advanceCard=0;
		double ebs=0;
		if(!soDetail.getOrderId().equals(page_lastPrintedID))
			++page_slNo;
		printTextBlock(left+H_GAP, SL_NO_FIELD_WIDTH -H_GAP-LINE_GAP, soDetail.getOrderId().equals(page_lastPrintedID)?" ":String.valueOf(page_slNo), TextAlign.LEFT, 
				false);
		left+=SL_NO_FIELD_WIDTH;
		printTextBlock(left+H_GAP, ORDER_NO_FIELD_WIDTH -H_GAP-LINE_GAP, soDetail.getOrderId().equals(page_lastPrintedID)?" ": PosOrderUtil.getShortOrderIDFromOrderID(soDetail.getOrderId()), TextAlign.LEFT, 
				false,true);
		
		left+=ORDER_NO_FIELD_WIDTH;
		printTextBlock(left+H_GAP, CUSTOMER_NAME_FIELD_WIDTH -H_GAP-LINE_GAP, soDetail.getOrderId().equals(page_lastPrintedID)?" ": soDetail.getCustomerName(), TextAlign.LEFT, 
				false,true); 

		left+=CUSTOMER_NAME_FIELD_WIDTH;
		printTextBlock(left+H_GAP, DESC_FIELD_WIDTH -H_GAP-LINE_GAP,  soDetail.getItemName(), TextAlign.LEFT, 
				false,true); 
		if (!soDetail.getOrderId().equals(page_lastPrintedID)){ 	
			switch(soDetail.getAdvancePaymentMode()){
				case Cash:
					advanceCash=soDetail.getAdvanceAmount();
					advanceCard=0;
					ebs=0;
					break;
				case Card:
					advanceCash=0;
					advanceCard=soDetail.getAdvanceAmount();
					ebs=0;
					break;
				case Online:
					advanceCash=0;
					advanceCard=0;
					ebs=soDetail.getAdvanceAmount();
					break;
				default:
						advanceCash=soDetail.getAdvanceAmount();
						advanceCard=0;
						ebs=0;
			}
		}
		
		page_totalAdvanceCash+=advanceCash;
		page_totalAdvanceCard+=advanceCard;
		page_totalEBS+=ebs;
		
		left+=DESC_FIELD_WIDTH;
		printTextBlock(left+H_GAP, ADVANCE_CASH_FIELD_WIDTH -H_GAP-LINE_GAP,  advanceCash>0?PosCurrencyUtil.format(advanceCash):" ", TextAlign.RIGHT, 
				false,true);
		
		left+=ADVANCE_CASH_FIELD_WIDTH;
		printTextBlock(left+H_GAP, ADVANCE_CARD_FIELD_WIDTH -H_GAP-LINE_GAP, advanceCard>0?PosCurrencyUtil.format(advanceCard):" ", TextAlign.RIGHT, 
				false,true);
		
		left+=ADVANCE_CARD_FIELD_WIDTH;
		printTextBlock(left+H_GAP, EBS_FIELD_WIDTH -H_GAP-LINE_GAP,  ebs>0?PosCurrencyUtil.format(ebs):" ", TextAlign.RIGHT, 
				true,true);
		
		 
	}
 
 
	/**
	 * @throws Exception
	 */
	private void onPrintReportFooter() throws Exception {
		
		advanceLine(5);
		printSingleLine();
		int left = SL_NO_FIELD_WIDTH + ORDER_NO_FIELD_WIDTH  + CUSTOMER_NAME_FIELD_WIDTH;
		printTextBlock(left+H_GAP, DESC_FIELD_WIDTH -H_GAP-LINE_GAP, "TOTAL :", TextAlign.RIGHT, 
				false,true);  
		
		left+=DESC_FIELD_WIDTH;
		printTextBlock(left+H_GAP, ADVANCE_CASH_FIELD_WIDTH -H_GAP-LINE_GAP,  page_totalAdvanceCash>0?PosCurrencyUtil.format(page_totalAdvanceCash):" ", TextAlign.RIGHT, 
				false,true);
		
		left+=ADVANCE_CASH_FIELD_WIDTH;
		printTextBlock(left+H_GAP, ADVANCE_CARD_FIELD_WIDTH -H_GAP-LINE_GAP, page_totalAdvanceCard>0?PosCurrencyUtil.format(page_totalAdvanceCard):" ", TextAlign.RIGHT, 
				false,true);
		
		left+=ADVANCE_CARD_FIELD_WIDTH;
		printTextBlock(left+H_GAP, EBS_FIELD_WIDTH -H_GAP-LINE_GAP,  page_totalEBS>0?PosCurrencyUtil.format(page_totalEBS):" ", TextAlign.RIGHT, 
				true,true);
		advanceLine(2);
		printDoubleLine();
		 
 	}
	     
	
}
