/**
 * 
 */
package com.indocosmo.pos.reports.salesorder;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.indocosmo.pos.common.PosEnvSettings;
import com.indocosmo.pos.common.beans.BeanReceiptTaxSummary;
import com.indocosmo.pos.common.enums.PosOrderServiceTypes;
import com.indocosmo.pos.common.enums.device.PrinterType;
import com.indocosmo.pos.common.exception.printing.HasNoItemsToPrintException;
import com.indocosmo.pos.common.utilities.PosCurrencyUtil;
import com.indocosmo.pos.common.utilities.PosDateUtil;
import com.indocosmo.pos.common.utilities.PosNumberUtil;
import com.indocosmo.pos.common.utilities.PosOrderUtil;
import com.indocosmo.pos.common.utilities.PosPrintingUtil;
import com.indocosmo.pos.common.utilities.PosSaleItemUtil;
import com.indocosmo.pos.common.utilities.PosStringUtil;
import com.indocosmo.pos.common.utilities.PosTaxUtil;
import com.indocosmo.pos.common.utilities.PosUomUtil;
import com.indocosmo.pos.common.utilities.converters.MoneyToWordsConvertor;
import com.indocosmo.pos.data.beans.BeanBillParam;
import com.indocosmo.pos.data.beans.BeanDiscount;
import com.indocosmo.pos.data.beans.BeanOrderDetail;
import com.indocosmo.pos.data.beans.BeanOrderHeader;
import com.indocosmo.pos.data.beans.BeanOrderPayment;
import com.indocosmo.pos.data.beans.BeanSalesOrderReport;
import com.indocosmo.pos.data.beans.BeanPayModeBase.PaymentMode;
import com.indocosmo.pos.data.beans.BeanSaleItem;
import com.indocosmo.pos.data.beans.BeanTax;
import com.indocosmo.pos.data.beans.BeanOrderHeader.PosOrderStatus;
import com.indocosmo.pos.data.beans.BeanShop;
import com.indocosmo.pos.data.providers.shopdb.PosBillParamProvider;
import com.indocosmo.pos.data.providers.shopdb.PosDiscountItemProvider;
import com.indocosmo.pos.data.providers.shopdb.PosPromotionItemProvider;
import com.indocosmo.pos.data.providers.shopdb.PosSaleItemProvider;
import com.indocosmo.pos.data.providers.shopdb.PosSalesOrderReportProvider;
import com.indocosmo.pos.data.providers.shopdb.PosTaxItemProvider;
import com.indocosmo.pos.data.providers.shopdb.PosTaxItemProvider.TaxCalculationMethod;
import com.indocosmo.pos.data.providers.shopdb.PosUOMProvider;
import com.indocosmo.pos.forms.components.objectbrowser.IPosBrowsableItem;
import com.indocosmo.pos.reports.base.PosPrintableReportBase;
import com.indocosmo.pos.reports.base.PosReportPageFormat;
import com.indocosmo.pos.reports.base.PosPrintableBase.TextAlign;
import com.sun.org.apache.xpath.internal.FoundIndex;

/**
 * @author sandhya
 * 
 */
public class PosSalesOrderItemWiseDetReport  extends PosSalesOrderReportBase {
	
	private static final String PROPERTY_FILE="pos-terminal-receipt-pymt-prd.properties"; 

	/**
	 * Serial Number column width
	 */
	
	private static final int REPORT_HEADER_HEIGHT=150;
	private int REPORT_HEIGHT;
	private int REPORT_WIDTH;
	private static final int REPORT_FOOTER_HEIGHT=30;
	protected static final int SL_NO_FIELD_WIDTH =38;
	protected static final int ORDER_NO_FIELD_WIDTH =  80;
	protected static final int CUSTOMER_NAME_FIELD_WIDTH =  90;
	protected static final int CUSTOMER_PHONE_FIELD_WIDTH =  60;
	protected static final int DELIVERY_TYPE_FIELD_WIDTH =  70;
	protected static final int DELIVERY_TIME_FIELD_WIDTH =  50;
	protected static final int MESSAGE_FIELD_WIDTH =  120;
	protected static final int QTY_FIELD_WIDTH = 90;
//	protected static final int ADVANCE_AMT_FIELD_WIDTH = 0;
	protected static final int BALANCE_AMT_FIELD_WIDTH =90;
	
	  
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
 
	private double report_totalQty=0;
 
	
	private int mPageHdrStartY=0;
	private int mPageHdrEndY=0;
	
	private String page_lastPrintedID="";
	private String report_lastPrintedID="";
	protected ArrayList<BeanSalesOrderReport> salesOrderDetails;
	
	/**
	 * @throws IOException 
	 * 
	 */
	public PosSalesOrderItemWiseDetReport() throws IOException {
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
		REPORT_WIDTH=getHeight()-50;
		REPORT_HEIGHT=600;//getWidth();
		DESC_FIELD_WIDTH=getPosReportPageFormat().getImageableHeightInPixcel()-(
				
				SL_NO_FIELD_WIDTH+1+
				ORDER_NO_FIELD_WIDTH+1+
				CUSTOMER_NAME_FIELD_WIDTH+1+
				CUSTOMER_PHONE_FIELD_WIDTH+1+
				DELIVERY_TYPE_FIELD_WIDTH+1+
				DELIVERY_TIME_FIELD_WIDTH +1 +
				MESSAGE_FIELD_WIDTH + 1+
				QTY_FIELD_WIDTH+1+
				BALANCE_AMT_FIELD_WIDTH 
				) ; 

		loadBillParams();	
	
		PosSalesOrderReportProvider reportProvider=new PosSalesOrderReportProvider();
		salesOrderDetails=reportProvider.getItemWiseSalesOrderDetailedReport(reportDate,mSelectedServices);
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
	 * @see com.indocosmo.pos.reports.base.PosPrintableBase#getOrientation()
	 */
	@Override
	public int getOrientation() {
		
		return PageFormat.LANDSCAPE;
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
		final String hdrLine0 = "STATEMENT OF ITEMWISE SALES ORDER FOR SETTING AS ON " + PosDateUtil.formatLocal(reportDate);
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
			++page_slNo; 
			printDetailItem(page_slNo, dtl);
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
		printTextBlock(left+H_GAP, CUSTOMER_PHONE_FIELD_WIDTH-H_GAP-LINE_GAP, "PHONE NO.", TextAlign.LEFT, 
				false);
		
		left+=CUSTOMER_PHONE_FIELD_WIDTH;
		printTextBlock(left+H_GAP, DELIVERY_TYPE_FIELD_WIDTH-H_GAP-LINE_GAP, "DEL. TYPE", TextAlign.LEFT, 
				false);
		
		left+=DELIVERY_TYPE_FIELD_WIDTH;
		printTextBlock(left+H_GAP, DELIVERY_TIME_FIELD_WIDTH-H_GAP-LINE_GAP, "DEL. TIME", TextAlign.LEFT, 
				false);
		
		left+=DELIVERY_TIME_FIELD_WIDTH;
		printTextBlock(left+H_GAP, DESC_FIELD_WIDTH-H_GAP-LINE_GAP, "ITEM", TextAlign.LEFT, 
				false);
		
		left+=DESC_FIELD_WIDTH;
		printTextBlock(left+H_GAP, MESSAGE_FIELD_WIDTH-H_GAP-LINE_GAP, "MESSAGE", TextAlign.LEFT, 
				false);
		
		left+=MESSAGE_FIELD_WIDTH;
		printTextBlock(left+H_GAP, QTY_FIELD_WIDTH-H_GAP-LINE_GAP, "QTY", TextAlign.RIGHT, 
				false);
		 
//		left+=QTY_FIELD_WIDTH;
//		printTextBlock(left+H_GAP, ADVANCE_AMT_FIELD_WIDTH-H_GAP-LINE_GAP, "ADV. RECEIVED", TextAlign.RIGHT, 
//				false);
		
		left+=QTY_FIELD_WIDTH;
		printTextBlock(left+H_GAP, BALANCE_AMT_FIELD_WIDTH-H_GAP-LINE_GAP, "AMT. RECEIVED", TextAlign.RIGHT, 
				true);
	}
		
 

	/**
	 * Prints the details part
	 */
	private void printDetailItem(int srNo, BeanSalesOrderReport soDetail) {

		int left = 0;
		
		printTextBlock(left+H_GAP, SL_NO_FIELD_WIDTH -H_GAP-LINE_GAP,String.valueOf(srNo), TextAlign.LEFT, 
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
		printTextBlock(left+H_GAP, CUSTOMER_PHONE_FIELD_WIDTH -H_GAP-LINE_GAP, 
				soDetail.getOrderId().equals(page_lastPrintedID)?" ": soDetail.getPhoneNumber(), TextAlign.LEFT, 
				false,true); 

		left+=CUSTOMER_PHONE_FIELD_WIDTH;
		printTextBlock(left+H_GAP, DELIVERY_TYPE_FIELD_WIDTH -H_GAP-LINE_GAP, 
				soDetail.getOrderId().equals(page_lastPrintedID)?" ": soDetail.getDeliveryType().getDisplayText(), TextAlign.LEFT, 
				false,true); 
		
		left+=DELIVERY_TYPE_FIELD_WIDTH;
		final String delTime=soDetail.getOrderId().equals(page_lastPrintedID)?" ":
			PosDateUtil.format(PosDateUtil.SHORT_TIME_FORMAT_12,PosDateUtil.parse(PosDateUtil.DATE_FORMAT_NOW_24,soDetail.getDueDateTime()));
		printTextBlock(left+H_GAP, DELIVERY_TIME_FIELD_WIDTH -H_GAP-LINE_GAP,  
				delTime, TextAlign.LEFT, 
				false,true); 
		
		left+=DELIVERY_TIME_FIELD_WIDTH;
		printTextBlock(left+H_GAP, DESC_FIELD_WIDTH -H_GAP-LINE_GAP,  soDetail.getItemName(), TextAlign.LEFT, 
				false,true); 
		 
		left+=DESC_FIELD_WIDTH;
		printTextBlock(left+H_GAP, MESSAGE_FIELD_WIDTH -H_GAP-LINE_GAP,  soDetail.getItemRemarks(), TextAlign.LEFT, 
				false,true); 
		 
		left+=MESSAGE_FIELD_WIDTH;
		printTextBlock(left+H_GAP, QTY_FIELD_WIDTH -H_GAP-LINE_GAP, PosUomUtil.format(soDetail.getQuantity(),PosUOMProvider.getInstance().getMaxDecUom())  , TextAlign.RIGHT, 
				false,true);
		
		left+=QTY_FIELD_WIDTH;
//		final String advanceAmtText=soDetail.getOrderId().equals(lastPrintedID)?" ":
//				(soDetail.getAdvanceAmount()==0?"":PosCurrencyUtil.format(soDetail.getAdvanceAmount()));
//		printTextBlock(left+H_GAP, ADVANCE_AMT_FIELD_WIDTH -H_GAP-LINE_GAP,  
//				advanceAmtText, TextAlign.LEFT, false,true); 
//		
//		left+=ADVANCE_AMT_FIELD_WIDTH;
		final double balanceAmt=soDetail.getBalanceReceived() + soDetail.getAdvanceAmount();
		final String balanceAmtText=soDetail.getOrderId().equals(page_lastPrintedID)?" ":
				(balanceAmt==0?"":PosCurrencyUtil.format(balanceAmt));
		printTextBlock(left+H_GAP, BALANCE_AMT_FIELD_WIDTH -H_GAP-LINE_GAP,  
				balanceAmtText , TextAlign.RIGHT, 
				true,true); 
		 
	}
 
 
	/**
	 * @throws Exception
	 */
	private void onPrintReportFooter() throws Exception {
		
		
		 
 	}
 
}
