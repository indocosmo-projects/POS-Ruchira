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
import com.indocosmo.pos.data.beans.BeanSalesOrderReport;
import com.indocosmo.pos.data.providers.shopdb.PosBillParamProvider;
import com.indocosmo.pos.data.providers.shopdb.PosSalesOrderReportProvider;
import com.indocosmo.pos.forms.components.objectbrowser.IPosBrowsableItem;
import com.indocosmo.pos.reports.base.PosPrintableReportBase;
import com.indocosmo.pos.reports.base.PosReportPageFormat;
import com.indocosmo.pos.reports.base.PosPrintableBase.TextAlign;

/**
 * @author sandhya
 * 
 */
public class PosSalesOrderBalanceReport extends PosSalesOrderReportBase {
	
	private static final String PROPERTY_FILE="pos-terminal-receipt-pymt-prd.properties"; 

	/**
	 * Serial Number column width
	 */
	
	private static final int REPORT_HEADER_HEIGHT=150;
	private int REPORT_HEIGHT;
	private static final int REPORT_FOOTER_HEIGHT=30;
	protected static final int SL_NO_FIELD_WIDTH =40;
	protected static final int ORDER_NO_FIELD_WIDTH =  100;
	protected static final int CUSTOMER_NAME_FIELD_WIDTH =  150;
	protected static final int QTY_FIELD_WIDTH =  0;
	protected static final int TOTAL_AMT_FIELD_WIDTH =  90;
	protected static final int ADVANCE_AMT_FIELD_WIDTH =  90;
	protected int BALANCE_AMT_FIELD_WIDTH =  120;
	protected int REFUND_AMT_FIELD_WIDTH =  90;
	  
	private static final int H_GAP =4;
	private static final int LINE_GAP=8;

	
	private int DESC_FIELD_WIDTH=0;
	
	private BeanBillParam mBillParam;

	private int report_itemIndex=0;
	private int report_slNo = 0;

	private int page_itemIndex=-1;
	private int page_slNo = 0;

	private double page_totalQty=0;
	private double page_totalCashAmount=0;
	private double page_totalCardAmount=0;
	private double page_totalBalanceAmount=0;
	 
	private double report_totalQty=0;
	private double report_totalBalanceAmount=0;
	private double report_totalCashAmount=0;
	private double report_totalCardAmount=0;
	 	
	private String page_lastPrintedID="";
	private String report_lastPrintedID="";
	protected ArrayList<BeanSalesOrderReport> salesOrderDetails;
	private boolean mIsPendingBalanceReport;
	/**
	 * @throws IOException 
	 * 
	 */
	public PosSalesOrderBalanceReport() throws IOException {
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
		
		if(mIsPendingBalanceReport)
			REFUND_AMT_FIELD_WIDTH=0;
		else
			BALANCE_AMT_FIELD_WIDTH=100;
		
		DESC_FIELD_WIDTH=getPosReportPageFormat().getImageableHeightInPixcel()- 
				(
						
						SL_NO_FIELD_WIDTH+1+
						ORDER_NO_FIELD_WIDTH+1+
						CUSTOMER_NAME_FIELD_WIDTH+1+
						TOTAL_AMT_FIELD_WIDTH+1+
						ADVANCE_AMT_FIELD_WIDTH +1+
						BALANCE_AMT_FIELD_WIDTH +
						REFUND_AMT_FIELD_WIDTH 
						);
		
//				(mIsPendingBalanceReport?(
//								
//							SL_NO_FIELD_WIDTH+1+
//							ORDER_NO_FIELD_WIDTH+1+
//							CUSTOMER_NAME_FIELD_WIDTH+1+
//							TOTAL_AMT_FIELD_WIDTH+1+
//							ADVANCE_AMT_FIELD_WIDTH +1+
//							BALANCE_AMT_FIELD_WIDTH 
//							): 	(
//				
//							SL_NO_FIELD_WIDTH+1+
//							ORDER_NO_FIELD_WIDTH+1+
//							CUSTOMER_NAME_FIELD_WIDTH+1+
//							TOTAL_AMT_FIELD_WIDTH+1+
//							ADVANCE_AMT_FIELD_WIDTH +1+PosSalesOrderReportForm
//							BALANCE_CASH_FIELD_WIDTH+1+
//							BALANCE_CARD_FIELD_WIDTH 
//							)
//					); 

		loadBillParams();		
		REPORT_HEIGHT=600;//getWidth();
		PosSalesOrderReportProvider reportProvider=new PosSalesOrderReportProvider();
		salesOrderDetails=reportProvider.getDailySalesOrderBalance(reportDate,mSelectedServices,mIsPendingBalanceReport);
		if(salesOrderDetails ==null || salesOrderDetails.size()==0){
			throw new HasNoItemsToPrintException("No data to print ");
		}
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.pos.reports.base.PosPrintableReportBase#onPageChanged(int, int)
	 */
	@Override
	protected void onPageChanged(int oldPage, int newPage) throws Exception {

		report_itemIndex=page_itemIndex;
		report_slNo=page_slNo;
		report_totalQty=page_totalQty;
//		report_totalCashAmount=page_totalCashAmount;
//		report_totalCardAmount=page_totalCardAmount;
		report_totalBalanceAmount=page_totalBalanceAmount;
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
//		page_totalCashAmount=report_totalCashAmount;
//		page_totalCardAmount=report_totalCardAmount;
		page_totalBalanceAmount=report_totalBalanceAmount;
		page_lastPrintedID=report_lastPrintedID;
		isEndOfReport=false;
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

		if ((REPORT_HEIGHT-REPORT_FOOTER_HEIGHT)>=getLastPrintedLineStartY()){
			
			 
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
	 * @see com.indocosmo.pos.reports.base.PosPrintableBase#getOrientation()
	 */
	@Override
	public int getOrientation() {
		return PageFormat.LANDSCAPE;
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
		final String hdrLine0 = "BALANCE " + (mIsPendingBalanceReport? "PENDING":"COLLECTED") + " AS ON " + PosDateUtil.formatLocal(reportDate);
		final String hdrLine1 = PosEnvSettings.getInstance().getShop().getName();
		final String hdrLine2 = PosEnvSettings.getInstance().getStation().getName();
		final String hdrLine3 ="";
		
		setFontStyle(Font.BOLD);
		setFontSize(10.0f);
		printText(TextAlign.CENTER, hdrLine0);

		setFont(mFontReceipt);
		printText(TextAlign.CENTER, hdrLine1);
		printText(TextAlign.CENTER, hdrLine2);
		printText(TextAlign.CENTER, hdrLine3);
		
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
			printDetailItem(dtl);
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
		printTextBlock(left+H_GAP, TOTAL_AMT_FIELD_WIDTH-H_GAP-LINE_GAP, "TOTAL AMT.", TextAlign.RIGHT, 
				false);
		
		left+=TOTAL_AMT_FIELD_WIDTH;
		printTextBlock(left+H_GAP, ADVANCE_AMT_FIELD_WIDTH-H_GAP-LINE_GAP, "ADVANCE", TextAlign.RIGHT, 
				false);
		
		left+=ADVANCE_AMT_FIELD_WIDTH;
		printTextBlock(left+H_GAP, BALANCE_AMT_FIELD_WIDTH-H_GAP-LINE_GAP, 
				mIsPendingBalanceReport?" BAL. RECEIVABLE":" BAL. RECEIVED", TextAlign.RIGHT, 
				mIsPendingBalanceReport);
		
		if(!mIsPendingBalanceReport){
			left+=BALANCE_AMT_FIELD_WIDTH;
			printTextBlock(left+H_GAP, REFUND_AMT_FIELD_WIDTH-H_GAP-LINE_GAP, "REFUND", TextAlign.RIGHT, 
					true);
		}
	}

	/**
	 * Prints the details part
	 */
	private void printDetailItem(BeanSalesOrderReport soDetail) {

		int left = 0;
		double balance=0;
		String advanceAmtText="";
		String refundAmtText="";
		
		if(!soDetail.getOrderId().equals(page_lastPrintedID))
			++page_slNo;
		printTextBlock(left+H_GAP, SL_NO_FIELD_WIDTH -H_GAP-LINE_GAP, soDetail.getOrderId().equals(page_lastPrintedID)?" ":String.valueOf(page_slNo), TextAlign.LEFT, 
				false);
	 
		left+=SL_NO_FIELD_WIDTH;
		printTextBlock(left+H_GAP, ORDER_NO_FIELD_WIDTH -H_GAP-LINE_GAP, 
				soDetail.getOrderId().equals(page_lastPrintedID)?" ": PosOrderUtil.getShortOrderIDFromOrderID(soDetail.getOrderId()), TextAlign.LEFT, 
				false,true);
		
		left+=ORDER_NO_FIELD_WIDTH;
		printTextBlock(left+H_GAP, CUSTOMER_NAME_FIELD_WIDTH -H_GAP-LINE_GAP, 
				soDetail.getOrderId().equals(page_lastPrintedID)?" ": soDetail.getCustomerName(), TextAlign.LEFT, 
				false,true); 

		left+=CUSTOMER_NAME_FIELD_WIDTH;
		printTextBlock(left+H_GAP, DESC_FIELD_WIDTH -H_GAP-LINE_GAP,  soDetail.getItemName(), TextAlign.LEFT, 
				false,true); 
		
		left+=DESC_FIELD_WIDTH;
		printTextBlock(left+H_GAP, TOTAL_AMT_FIELD_WIDTH-H_GAP-LINE_GAP, 
				soDetail.getOrderId().equals(page_lastPrintedID)?" ": PosCurrencyUtil.format(soDetail.getTotalAmount()), TextAlign.RIGHT, 
				false,true);
		
		if (!soDetail.getOrderId().equals(page_lastPrintedID)){		
			balance= mIsPendingBalanceReport? (soDetail.getTotalAmount()-soDetail.getAdvanceAmount()):
				soDetail.getBalanceReceived();
			advanceAmtText=soDetail.getAdvanceAmount()>0? PosCurrencyUtil.format(soDetail.getAdvanceAmount()):"";
			refundAmtText=soDetail.getRefundAmount()>0? PosCurrencyUtil.format(soDetail.getRefundAmount()):"";
			
		}
		
		left+=TOTAL_AMT_FIELD_WIDTH;
		printTextBlock(left+H_GAP, ADVANCE_AMT_FIELD_WIDTH-H_GAP-LINE_GAP,  
				advanceAmtText, TextAlign.RIGHT, 
				false,true);
		
		left+=ADVANCE_AMT_FIELD_WIDTH;
		
		
		
		page_totalBalanceAmount+=balance;
		printTextBlock(left+H_GAP, BALANCE_AMT_FIELD_WIDTH -H_GAP-LINE_GAP,  balance>0?PosCurrencyUtil.format(balance):" ", TextAlign.RIGHT, 
				mIsPendingBalanceReport,true);
		
		if(!mIsPendingBalanceReport){
			left+=BALANCE_AMT_FIELD_WIDTH;
			printTextBlock(left+H_GAP, REFUND_AMT_FIELD_WIDTH-H_GAP-LINE_GAP, 
					refundAmtText, TextAlign.RIGHT, 
					true,true);
		}
		
//	 		
//		if(mIsPendingBalanceReport){
//			
//			if (!soDetail.getOrderId().equals(lastPrintedID))				
//				dueAmount= soDetail.getTotalAmount()-soDetail.getAdvanceAmount();
//			
//			page_totalDueAmount+=dueAmount;
//			printTextBlock(left+H_GAP, BALANCE_AMT_FIELD_WIDTH -H_GAP-LINE_GAP,  dueAmount>0?PosCurrencyUtil.format(dueAmount):" ", TextAlign.RIGHT, 
//					true,true);
//			 
//		}else{
//		
//			if (!soDetail.getOrderId().equals(lastPrintedID)){ 	
//				switch(soDetail.getBalancePaymentMode()){
//					case Cash:
//						cashAmount=soDetail.getBalanceReceived();
//						cardAmount=0;
//						break;
//					case Card:
//						cashAmount=0;
//						cardAmount=soDetail.getBalanceReceived();
//						break;
//					}
//			}
//			page_totalCashAmount+=cashAmount;
//			page_totalCardAmount+=cardAmount;
//			
//			printTextBlock(left+H_GAP, BALANCE_CASH_FIELD_WIDTH -H_GAP-LINE_GAP,  cashAmount>0?PosCurrencyUtil.format(cashAmount):" ", TextAlign.RIGHT, 
//					false,true);
//			
//			left+=BALANCE_CASH_FIELD_WIDTH;
//			printTextBlock(left+H_GAP, BALANCE_CARD_FIELD_WIDTH -H_GAP-LINE_GAP, cardAmount>0?PosCurrencyUtil.format(cardAmount):" ", TextAlign.RIGHT, 
//					true,true);	
				
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
		
		left=left+ DESC_FIELD_WIDTH + TOTAL_AMT_FIELD_WIDTH + ADVANCE_AMT_FIELD_WIDTH;
		printTextBlock(left+H_GAP, BALANCE_AMT_FIELD_WIDTH -H_GAP-LINE_GAP, page_totalBalanceAmount>0?PosCurrencyUtil.format(page_totalBalanceAmount):" ", TextAlign.RIGHT, 
				true,true);
		 
		advanceLine(2);
		printDoubleLine();
		 
 	}

	/**
	 * @return the mIsPendingBalance
	 */
	public boolean isPendingBalanceReport() {
		return mIsPendingBalanceReport;
	}

	/**
	 * @param mIsPendingBalance the mIsPendingBalance to set
	 */
	public void setPendingBalanceReport(boolean mIsPendingBalance) {
		this.mIsPendingBalanceReport = mIsPendingBalance;
	}
 
}
