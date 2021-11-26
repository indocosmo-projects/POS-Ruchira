/**
 * 
 */
package com.indocosmo.pos.reports.receipts;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.indocosmo.pos.common.PosEnvSettings;
import com.indocosmo.pos.common.enums.device.PrinterType;
import com.indocosmo.pos.common.utilities.PosCurrencyUtil;
import com.indocosmo.pos.common.utilities.PosDateUtil;
import com.indocosmo.pos.common.utilities.PosFileUtils;
import com.indocosmo.pos.common.utilities.PosNumberUtil;
import com.indocosmo.pos.common.utilities.PosOrderUtil;
import com.indocosmo.pos.common.utilities.PosPrintingUtil;
import com.indocosmo.pos.common.utilities.PosSaleItemUtil;
import com.indocosmo.pos.common.utilities.PosStringUtil;
import com.indocosmo.pos.common.utilities.PosUomUtil;
import com.indocosmo.pos.common.utilities.converters.MoneyToWordsConvertor;
import com.indocosmo.pos.data.beans.BeanBillParam;
import com.indocosmo.pos.data.beans.BeanOrderDetail;
import com.indocosmo.pos.data.beans.BeanOrderHeader;
import com.indocosmo.pos.data.beans.BeanOrderHeader.PosOrderStatus;
import com.indocosmo.pos.data.beans.BeanShop;
import com.indocosmo.pos.data.providers.shopdb.PosBillParamProvider;
import com.indocosmo.pos.data.providers.shopdb.PosTaxItemProvider.TaxCalculationMethod;
import com.indocosmo.pos.data.providers.shopdb.PosUOMProvider;
import com.indocosmo.pos.reports.base.PosPrintableReportBase;
import com.indocosmo.pos.reports.base.PosReportPageFormat;
import com.indocosmo.pos.reports.base.PosPrintableBase.TextAlign;

/**
 * @author sandhya
 * 
 */
public class PosPRDPaymentReceipt extends PosPrintableReportBase {
	
	private static final String PROPERTY_FILE="pos-terminal-receipt-pymt-prd.properties"; 

	/**
	 * Serial Number column width
	 */
	
	private int REPORT_HEADER_HEIGHT=0; // This will very depends on the Bill params
	private static final int REPORT_FOOTER_HEIGHT=235;
	private int PAGE_HEADER_HEIGHT=0;
	private static final int PAGE_DETAIL_HEADER_HEIGHT=28;
	private int PAGE_DETAIL_HEIGHT=0;  
	private final int DET_HEADING_HEIGHT=15;
	private static final int PAGE_FOOTER_HEIGHT=22;

	
	private int SLNO_FIELD_WIDTH; 
	private int HSN_CODE_WIDTH; 
	private int UOM_FIELD_WIDTH;
	private int QTY_FIELD_WIDTH;
	private int RATE_FIELD_WIDTH;
	private int AMOUNT_FIELD_WIDTH;
	private int CASH_DISC_FIELD_WIDTH;
	private int TAXABLE_AMT_FIELD_WIDTH; 
	private int TOTAL_FIELD_WIDTH; 
	private int CGST_RATE_FIELD_WIDTH; 
	private int CGST_AMOUNT_FIELD_WIDTH; 
	private int SGST_RATE_FIELD_WIDTH; 
	private int SGST_AMOUNT_FIELD_WIDTH; 
	private int IGST_RATE_FIELD_WIDTH;
	private int IGST_AMOUNT_FIELD_WIDTH;
	
//	private static final int FREE_QTY_WIDTH = 25;

	private int PG_HDR_TRANSPORT_TITLE_WIDTH = 100;
	private int PG_HDR_TRANSPORT_DATA_WIDTH = 150;
	
	private int PG_HDR_INVOICE_TITLE_WIDTH = 100;
	private int PG_HDR_INVOICE_DATA_WIDTH = 150;

	private int PG_HDR_STATECODE_WIDTH = 100;	

	private static final int REPORT_FOOTER_AMT_FIELD_WIDTH=180;
	private static final int REPORT_FOOTER_AMT_DATA_WIDTH=80;

	private static final int REPORT_FOOTER_BANK_FIELD_WIDTH=80;
	 
	private static final int HDR_GAP =6;
	private static final int H_GAP =4;
	private static final int START_INDEX=4; // GAP B/W Line & First character 
	private static final int END_INDEX=4;
	
	private int DESC_FIELD_WIDTH=0;

	private BeanOrderHeader mOrder;
	private BeanBillParam mBillParam;

	private int report_itemIndex=0;
	private int report_slNo = 0;

	private int page_itemIndex=-1;
	private int page_slNo = 1;

	private double page_totalQty=0;
	private double page_totalAmt=0;
	private double page_totalCashDisc=0;
	private double page_totalTaxableAmt=0;
	private double page_totalCGST=0;
	private double page_totalSGST=0;
	private double page_totalIGST=0;
	private double page_subTotal=0;
	
	private double report_totalQty=0;
	private double report_totalAmt=0;
	private double report_totalCashDisc=0;
	private double report_totalTaxableAmt=0;
	private double report_totalCGST=0;
	private double report_totalSGST=0;
	private double report_totalIGST=0;
	private double report_subTotal=0;
	
	private int mPageHdrStartY=0;
	private int mCustomerStartY=0;
	private int mBillHeadingStartY=0;
	private int mBillFooterEndY=0;
	final int rptFooterBankDetStartY=0;

	public boolean isEndOfReport=false;

	/**
	 * @throws IOException 
	 * 
	 */
	public PosPRDPaymentReceipt(BeanOrderHeader order) throws IOException {
		super();

		setOrder(order);
		
	}

	/**
	 * 
	 */
	public PosPRDPaymentReceipt() {

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

		loadPRDPaymentReceiptSettings();
		DESC_FIELD_WIDTH=getPosReportPageFormat().getImageableWidthInPixcel()-(

				SLNO_FIELD_WIDTH+H_GAP+
				HSN_CODE_WIDTH +H_GAP +
				UOM_FIELD_WIDTH + H_GAP+
				QTY_FIELD_WIDTH+H_GAP+
				RATE_FIELD_WIDTH+H_GAP+
				AMOUNT_FIELD_WIDTH +H_GAP + 
				CASH_DISC_FIELD_WIDTH +H_GAP + 
				TAXABLE_AMT_FIELD_WIDTH +H_GAP +
				CGST_RATE_FIELD_WIDTH +H_GAP +
				CGST_AMOUNT_FIELD_WIDTH +H_GAP +
				SGST_RATE_FIELD_WIDTH +H_GAP +
				SGST_AMOUNT_FIELD_WIDTH +H_GAP +
				IGST_RATE_FIELD_WIDTH +H_GAP +
				IGST_AMOUNT_FIELD_WIDTH +H_GAP +
				TOTAL_FIELD_WIDTH +H_GAP +END_INDEX
				) ;

		loadBillParams();		
	 
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.pos.reports.base.PosPrintableReportBase#onPageChanged(int, int)
	 */
	@Override
	protected void onPageChanged(int oldPage, int newPage) throws Exception {

		report_itemIndex=page_itemIndex;
		report_slNo=page_slNo;
		report_totalQty=page_totalQty;
		report_totalAmt=page_totalAmt;
		report_totalCashDisc=page_totalCashDisc;
		report_totalTaxableAmt=page_totalTaxableAmt;
		report_totalCGST=page_totalCGST;
		report_totalSGST=page_totalSGST;
		report_totalIGST=page_totalIGST;
		report_subTotal=page_subTotal;
		
	}


	/* (non-Javadoc)PosEnvSettings.getInstance().getPrintSettings().getPRDPaymentReceiptSettings().getHsnCodeWidth()
	 * @see com.indocosmo.pos.reports.base.PosPrintableReportBase#onPageInit(int)
	 */
	@Override
	protected void initPage(int pageIndex) throws Exception {

		page_itemIndex=report_itemIndex+1;
		page_slNo=report_slNo;
		page_totalQty=report_totalQty;
		page_totalAmt=report_totalAmt;
		page_totalCashDisc =report_totalCashDisc;
		page_totalTaxableAmt =report_totalTaxableAmt;
		page_totalCGST=report_totalCGST;
		page_totalSGST=report_totalSGST;
		page_totalIGST=report_totalIGST;
		page_subTotal =report_subTotal;
		
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

		
		REPORT_HEADER_HEIGHT=getNextLineStartY();
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.pos.reports.base.PosPrintableReportBase#printPageHeader(int)
	 */
	@Override
	protected void printPageHeader(int pageIndex) throws Exception {
		
		printHeader(mOrder, mBillParam);
		printSingleLine();
		printDetailsHeader();
		printSingleLine();

		PAGE_HEADER_HEIGHT=getNextLineStartY()-REPORT_HEADER_HEIGHT;
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.pos.reports.base.PosPrintableReportBase#printPageDetails(int)
	 */
	@Override
	protected void printPageDetails(int pageIndex) throws Exception {

		printDetails(mOrder);
		PAGE_DETAIL_HEIGHT=getNextLineStartY()-PAGE_HEADER_HEIGHT-REPORT_HEADER_HEIGHT;

	}

	/* (non-Javadoc)
	 * @see com.indocosmo.pos.reports.base.PosPrintableReportBase#printPageFooter(int)
	 */
	@Override
	protected void printPageFooter(int pageIndex) throws Exception {

		int foorterStartY=getNextLineStartY();
		if(!hasMoreData()){

			final int newFtrStartY=getHeight()-(REPORT_FOOTER_HEIGHT+PAGE_FOOTER_HEIGHT);
			// will print only page footer if not enough space for report footer section 
			foorterStartY=(newFtrStartY>=getNextLineStartY())?newFtrStartY:getHeight()-PAGE_FOOTER_HEIGHT;

		}

		printLine(1, foorterStartY, getWidth(), foorterStartY);
		setNextLineStartY(foorterStartY+getLineSpacing());
		printPageSummary(mOrder);
		printLine(1, foorterStartY+PAGE_FOOTER_HEIGHT, getWidth(), foorterStartY+PAGE_FOOTER_HEIGHT);
		//printLine(1, foorterStartY+PAGE_FOOTER_HEIGHT-getLineSpacing(), getWidth(), foorterStartY+PAGE_FOOTER_HEIGHT-getLineSpacing());
		printDetailLayout(REPORT_HEADER_HEIGHT+PAGE_HEADER_HEIGHT-PAGE_DETAIL_HEADER_HEIGHT,foorterStartY+PAGE_FOOTER_HEIGHT);

	}


	/* (non-Javadoc)
	 * @see com.indocosmo.pos.reports.base.PosPrintableReportBase#printReportFooter(int)
	 */
	@Override
	protected void printReportFooter(int pageIndex) throws Exception {

		if ((getHeight()-REPORT_FOOTER_HEIGHT)>=getLastPrintedLineStartY()){
		
			printReportSummary();
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

		boolean result=false;
		
		if(mOrder.getOrderDetailItems()!=null)
			result=  page_itemIndex<mOrder.getOrderDetailItems().size()-1;
		return result;
	}

	/**
	 * @param order
	 * @param param
	 * @throws Exception
	 */
	private void printHeader(BeanOrderHeader order, BeanBillParam param)
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
		
		BeanShop shop=PosEnvSettings.getInstance().getShop();

		String hdrLineGSTinNo=(shop.getCompanyTaxNo()!=null && shop.getCompanyTaxNo().trim()!="")?
				"GSTIN: " +  shop.getCompanyTaxNo():"";
		hdrLineGSTinNo=hdrLineGSTinNo +(hdrLineGSTinNo!=""?", ":  "") +
				((shop.getCompanyLicenseNo()!=null && shop.getCompanyLicenseNo().trim()!="")? 
						"CST NO : " +  shop.getCompanyLicenseNo():"");
		
		final String hdrLine11 = (order.getStatus()==PosOrderStatus.Closed ||order.getStatus()==PosOrderStatus.Refunded)?"INVOICE":"ESTIMATE";

		 
		setFontStyle(Font.BOLD);
		setFontSize(12f);
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
//		printText(TextAlign.CENTER, hdrLineGSTinNo);

		setFontSize(14.0f);
		
		advanceLine(2);
		mBillHeadingStartY=getNextLineStartY();
		printSingleLine();
		printText(TextAlign.CENTER, hdrLine11);

		advanceLine( );
		
		mPageHdrStartY=getNextLineStartY();
		printSingleLine();
		 
		setFont(mFontReceipt);
		
		/*
		 * Prints the sub headersSUB_HDR_FIELD_WIDTH
		 */
		
		PG_HDR_INVOICE_DATA_WIDTH = getWidth()-PG_HDR_INVOICE_TITLE_WIDTH-PG_HDR_TRANSPORT_TITLE_WIDTH-PG_HDR_TRANSPORT_DATA_WIDTH;

		final int invTitleLeft=START_INDEX;
		final int invSeparatorLeft= PG_HDR_INVOICE_TITLE_WIDTH;
		final int invDataLeft=PG_HDR_INVOICE_TITLE_WIDTH+HDR_GAP;
		
		PG_HDR_INVOICE_TITLE_WIDTH=PG_HDR_INVOICE_TITLE_WIDTH-START_INDEX;
		PG_HDR_INVOICE_DATA_WIDTH=PG_HDR_INVOICE_DATA_WIDTH-HDR_GAP;
		
		final int transportTitleLeft=PG_HDR_INVOICE_TITLE_WIDTH+PG_HDR_INVOICE_DATA_WIDTH + START_INDEX ;
		final int transportSeparatorLeft= transportTitleLeft +PG_HDR_TRANSPORT_TITLE_WIDTH;
		final int transportDataLeft=transportSeparatorLeft+HDR_GAP ;
	
		PG_HDR_TRANSPORT_TITLE_WIDTH=PG_HDR_TRANSPORT_TITLE_WIDTH-START_INDEX;
		PG_HDR_TRANSPORT_DATA_WIDTH=PG_HDR_TRANSPORT_DATA_WIDTH-HDR_GAP;
	
		printTextBlock(  invTitleLeft, PG_HDR_INVOICE_TITLE_WIDTH, "Reverse Charge ", TextAlign.LEFT,false);
		printTextBlock( invSeparatorLeft,HDR_GAP, ":  ", TextAlign.LEFT,false);
		printTextBlock(  invDataLeft, PG_HDR_INVOICE_DATA_WIDTH, " ", TextAlign.LEFT,false,true);
		
		printTextBlock(transportTitleLeft  , PG_HDR_TRANSPORT_TITLE_WIDTH, "Transportation Mode  ", TextAlign.LEFT,false);
		printTextBlock(transportSeparatorLeft, HDR_GAP, ":  ", TextAlign.LEFT,false);
		printTextBlock(transportDataLeft,PG_HDR_TRANSPORT_DATA_WIDTH ,  " ", TextAlign.LEFT,true,true);
		
 
		String invoiceNoData;
		String invoiceNoTitle;
		String invoiceDateData;
		String invoiceDateTitle;
		
		if(order.getStatus()==PosOrderStatus.Closed ||order.getStatus()==PosOrderStatus.Refunded){
			invoiceNoTitle="Invoice No ";
			invoiceNoData=mOrder.getInvoiceNo();
			invoiceDateTitle="Invoice Date";
			invoiceDateData=PosDateUtil.formatLocal(order.getClosingDate());
		}else{
			invoiceNoTitle="Ref. No ";
			invoiceNoData=PosOrderUtil.getShortOrderIDFromOrderID(order.getOrderId());
			invoiceDateTitle="Ref. Date";
			invoiceDateData=PosDateUtil.formatLocal(order.getOrderDate());
		}
 

		printTextBlock(invTitleLeft, PG_HDR_INVOICE_TITLE_WIDTH, invoiceNoTitle, TextAlign.LEFT,false);
		printTextBlock(invSeparatorLeft,HDR_GAP, ":  ", TextAlign.LEFT,false,true);
		printTextBlock(invDataLeft,PG_HDR_INVOICE_DATA_WIDTH , invoiceNoData, TextAlign.LEFT,false,true);
		
		printTextBlock(transportTitleLeft, PG_HDR_TRANSPORT_TITLE_WIDTH, "Driver Name", TextAlign.LEFT,false);
		printTextBlock(transportSeparatorLeft , HDR_GAP, ":  ", TextAlign.LEFT,false);
		printTextBlock(transportDataLeft,PG_HDR_TRANSPORT_DATA_WIDTH ,  order.getDriverName()==null?" ":order.getDriverName(), TextAlign.LEFT,true,true);
		
	 
		printTextBlock(invTitleLeft, PG_HDR_INVOICE_TITLE_WIDTH, invoiceDateTitle, TextAlign.LEFT,false);
		printTextBlock(invSeparatorLeft,HDR_GAP, ":  ", TextAlign.LEFT,false);
		printTextBlock(invDataLeft,PG_HDR_INVOICE_DATA_WIDTH , invoiceDateData, 
						TextAlign.LEFT,false);
		
		printTextBlock(transportTitleLeft,PG_HDR_TRANSPORT_TITLE_WIDTH, "Vehicle Number  ", TextAlign.LEFT,false);
		printTextBlock(transportSeparatorLeft , HDR_GAP, ":  ", TextAlign.LEFT,false);
		printTextBlock(transportDataLeft,PG_HDR_TRANSPORT_DATA_WIDTH ,  order.getVehicleNumber()==null?" ":order.getVehicleNumber(), TextAlign.LEFT,true,true);
		
		
		final int stateWidth=PG_HDR_INVOICE_DATA_WIDTH-PG_HDR_STATECODE_WIDTH;
		
		printTextBlock(invTitleLeft, PG_HDR_INVOICE_TITLE_WIDTH, "State  ", TextAlign.LEFT,false);
		printTextBlock(invSeparatorLeft,H_GAP, ":  ", TextAlign.LEFT,false);
		printTextBlock(invDataLeft,stateWidth  , shop.getState(), TextAlign.LEFT,false);
		printTextBlock( invDataLeft + stateWidth ,PG_HDR_STATECODE_WIDTH,"State Code  : "  +  (shop.getStateCode()==null?"    ":shop.getStateCode())  , TextAlign.LEFT,false,true);

		printTextBlock(transportTitleLeft, PG_HDR_TRANSPORT_TITLE_WIDTH, "Date of Supply ", TextAlign.LEFT,false);
		printTextBlock(transportSeparatorLeft, HDR_GAP, ":  ", TextAlign.LEFT,false);
		printTextBlock(transportDataLeft,PG_HDR_TRANSPORT_DATA_WIDTH , 
				(order.getStatus()==PosOrderStatus.Closed ||order.getStatus()==PosOrderStatus.Refunded? 
						PosDateUtil.formatLocalDateTime(order.getClosingTime()):" "), TextAlign.LEFT,true,true);
		advanceLine(2);
		mCustomerStartY=getNextLineStartY();
		// Vertical Line in page header -- b/w Invoice Det and Transportation Det
		printLine(transportTitleLeft-START_INDEX , mPageHdrStartY, transportTitleLeft-START_INDEX  , mCustomerStartY,false);

		printSingleLine();
		setFontStyle(Font.BOLD);
		printTextBlock(invTitleLeft, PG_HDR_INVOICE_TITLE_WIDTH +PG_HDR_INVOICE_DATA_WIDTH, "Details of Receiver | Billed to:", TextAlign.LEFT,true);
		printSingleLine();
		

		setFont(mFontReceipt);
		final int customerWidth=PG_HDR_INVOICE_DATA_WIDTH + PG_HDR_TRANSPORT_TITLE_WIDTH + PG_HDR_TRANSPORT_DATA_WIDTH;
		printTextBlock(invTitleLeft, PG_HDR_INVOICE_TITLE_WIDTH, "Name  ", TextAlign.LEFT,false);
		printTextBlock( invSeparatorLeft,H_GAP, ":  ", TextAlign.LEFT,false);
		printTextBlock( invDataLeft,customerWidth , order.getOrderCustomer().getName(), TextAlign.LEFT,true,true);
	
		printTextBlock(invTitleLeft, PG_HDR_INVOICE_TITLE_WIDTH, "Address     ", TextAlign.LEFT,false);
		printTextBlock(invSeparatorLeft,H_GAP, ":  ", TextAlign.LEFT,false);
		printTextBlock(invDataLeft ,customerWidth , order.getOrderCustomer().getAddress(), TextAlign.LEFT,true,true);
	
		printTextBlock(invTitleLeft, PG_HDR_INVOICE_TITLE_WIDTH, "GSTIN    ", TextAlign.LEFT,false);
		printTextBlock(invSeparatorLeft,H_GAP, ":  ", TextAlign.LEFT,false);
		printTextBlock(invDataLeft  ,customerWidth , order.getOrderCustomer().getTinNo(), TextAlign.LEFT,true,true);
	
		printTextBlock(invTitleLeft, PG_HDR_INVOICE_TITLE_WIDTH, "CST Number   ", TextAlign.LEFT,false);
		printTextBlock(invSeparatorLeft,H_GAP, ":  ", TextAlign.LEFT,false);
		printTextBlock(invDataLeft  ,customerWidth ,(order.getCustomer().getCSTNo()==null?" ":order.getCustomer().getCSTNo()) , TextAlign.LEFT,true,true);
	
		printTextBlock(invTitleLeft, PG_HDR_INVOICE_TITLE_WIDTH, "State   ", TextAlign.LEFT,false);
		printTextBlock(invSeparatorLeft,H_GAP, ":  ", TextAlign.LEFT,false);
		printTextBlock(invDataLeft,stateWidth  , order.getOrderCustomer().getState(), TextAlign.LEFT,false,true);
		printTextBlock(invDataLeft + stateWidth ,PG_HDR_STATECODE_WIDTH,"State Code  : " + (order.getOrderCustomer().getStateCode()) , TextAlign.LEFT,true,true);

		printSingleLine();
		advanceTextLine(1);

	}

	/**
	 * @param order
	 * @throws Exception
	 */
	private void printDetails(BeanOrderHeader order) throws Exception {

		if(mOrder.getOrderDetailItems()==null || mOrder.getOrderDetailItems().size()<=0) return ;

		final Font dtlsFont=PosEnvSettings.getInstance().getPrintSettings().getPaymentReceiptSettings().getItemDetailFont();
		setFont(dtlsFont);

		for(;page_itemIndex<mOrder.getOrderDetailItems().size();page_itemIndex++){

			final BeanOrderDetail dtl=mOrder.getOrderDetailItems().get(page_itemIndex);
			if(dtl.isVoid()) continue;
			page_slNo=printDetailItem(page_slNo, dtl,true);

			if(getNextLineStartY()+getLineHeight()>=(getHeight()-PAGE_FOOTER_HEIGHT))
				break;

		}

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
		return slNo;
	}


	private void printDetailLayout(int startY , int endY) throws Exception{

		int lineLeft=1;
		int lineGap=2;

		printLine(lineLeft , mBillHeadingStartY, lineLeft , hasMoreData()?endY:getHeight() ,false);

		lineLeft += SLNO_FIELD_WIDTH + H_GAP;
		printLine(lineLeft-lineGap , startY, lineLeft-lineGap, endY,false);

		lineLeft += DESC_FIELD_WIDTH + H_GAP ;
		printLine(lineLeft-lineGap , startY, lineLeft-lineGap, endY,false);

		lineLeft += HSN_CODE_WIDTH + H_GAP ;
		if(HSN_CODE_WIDTH>0)
			printLine(lineLeft-lineGap , startY, lineLeft-lineGap, endY,false);


		lineLeft += UOM_FIELD_WIDTH + H_GAP ;
		if(UOM_FIELD_WIDTH>0)
			printLine(lineLeft-lineGap , startY, lineLeft-lineGap, endY,false);


		lineLeft += QTY_FIELD_WIDTH  + H_GAP;
		if(QTY_FIELD_WIDTH>0)
			printLine(lineLeft-lineGap , startY, lineLeft-lineGap, endY,false);

		lineLeft += RATE_FIELD_WIDTH + H_GAP;
		if(RATE_FIELD_WIDTH>0)
			printLine(lineLeft-lineGap , startY, lineLeft-lineGap, endY,false);

		lineLeft += AMOUNT_FIELD_WIDTH + H_GAP;
		if(AMOUNT_FIELD_WIDTH>0)
			printLine(lineLeft-lineGap , startY, lineLeft-lineGap, endY,false);

		lineLeft += CASH_DISC_FIELD_WIDTH + H_GAP ;
		if(CASH_DISC_FIELD_WIDTH>0)
			printLine(lineLeft-lineGap , startY, lineLeft-lineGap, endY,false);

		lineLeft += TAXABLE_AMT_FIELD_WIDTH + H_GAP ;
		if(TAXABLE_AMT_FIELD_WIDTH>0)
			printLine(lineLeft-lineGap , startY, lineLeft-lineGap, endY,false);
		
		final int gstHeadingStart=lineLeft-lineGap ;
		int gstHeadingEnd=lineLeft-lineGap ;
		
		lineLeft += CGST_RATE_FIELD_WIDTH +   H_GAP ;
		if(CGST_RATE_FIELD_WIDTH>0){
			printLine(lineLeft-lineGap , startY+DET_HEADING_HEIGHT, lineLeft-lineGap, endY,false);
			gstHeadingEnd=lineLeft-lineGap ;
		}
		
		lineLeft += CGST_AMOUNT_FIELD_WIDTH +   H_GAP ;
		if(CGST_AMOUNT_FIELD_WIDTH>0){
			printLine(lineLeft-lineGap , startY, lineLeft-lineGap, endY,false);
			gstHeadingEnd=lineLeft-lineGap ;
		}

		lineLeft += SGST_RATE_FIELD_WIDTH +   H_GAP ;
		if(SGST_RATE_FIELD_WIDTH>0){
			printLine(lineLeft-lineGap , startY+DET_HEADING_HEIGHT, lineLeft-lineGap, endY,false);
			gstHeadingEnd=lineLeft-lineGap ;
		}
		
		lineLeft += SGST_AMOUNT_FIELD_WIDTH +   H_GAP ;
		if(SGST_AMOUNT_FIELD_WIDTH>0){
			printLine(lineLeft-lineGap , startY, lineLeft-lineGap, endY,false);
			gstHeadingEnd=lineLeft-lineGap ;
		}

		lineLeft += IGST_RATE_FIELD_WIDTH +   H_GAP ;
		if(IGST_RATE_FIELD_WIDTH>0){
			printLine(lineLeft-lineGap , startY+DET_HEADING_HEIGHT, lineLeft-lineGap, endY,false);
			gstHeadingEnd=lineLeft-lineGap ;
		}
		
		lineLeft += IGST_AMOUNT_FIELD_WIDTH +   H_GAP ;
		if(IGST_AMOUNT_FIELD_WIDTH>0){
			printLine(lineLeft-lineGap , startY, lineLeft-lineGap, endY,false);
			gstHeadingEnd=lineLeft-lineGap ;
		}
 
	
//		lineLeft += TOTAL_FIELD_WIDTH + H_GAP ;
//		printLine(lineLeft-lineGap , startY, lineLeft-lineGap, endY,false);

		lineLeft =getWidth();
		printLine(lineLeft, mBillHeadingStartY, lineLeft,  hasMoreData()?endY:getHeight()  ,false);

		//tax headning separator
		
	
//		final int gstHeadingWidth=(CGST_RATE_FIELD_WIDTH +  H_GAP + CGST_AMOUNT_FIELD_WIDTH + H_GAP +
//				SGST_RATE_FIELD_WIDTH + H_GAP + SGST_AMOUNT_FIELD_WIDTH + H_GAP +
//				IGST_RATE_FIELD_WIDTH +  H_GAP + IGST_AMOUNT_FIELD_WIDTH + H_GAP ) -lineGap;
		final int gstHeadingWidth=
				(CGST_RATE_FIELD_WIDTH>0?(CGST_RATE_FIELD_WIDTH +  H_GAP ) : 0 )+
				(CGST_AMOUNT_FIELD_WIDTH>0?(CGST_AMOUNT_FIELD_WIDTH +  H_GAP ) : 0 )+
				(SGST_RATE_FIELD_WIDTH>0?(SGST_RATE_FIELD_WIDTH +  H_GAP ) : 0) +
				(SGST_AMOUNT_FIELD_WIDTH>0?(SGST_AMOUNT_FIELD_WIDTH +  H_GAP ) : 0 )+
				(IGST_RATE_FIELD_WIDTH>0?(IGST_RATE_FIELD_WIDTH +  H_GAP ) : 0 )+
				(IGST_AMOUNT_FIELD_WIDTH>0?(IGST_AMOUNT_FIELD_WIDTH +  H_GAP ) : 0 );
										
 
		
		//printLine(gstHeadingStart, startY+DET_HEADING_HEIGHT,gstHeadingStart+gstHeadingWidth,  startY+DET_HEADING_HEIGHT,false);
		printLine(gstHeadingStart, startY+DET_HEADING_HEIGHT,gstHeadingEnd,  startY+DET_HEADING_HEIGHT,false);

	}


	/**
	 * Prints the header for details
	 */
	private void printDetailsHeader() {

		setFont(mFontReceipt);
		int left = 0;

		final int cgstFieldWidth=(CGST_RATE_FIELD_WIDTH + CGST_AMOUNT_FIELD_WIDTH)>0?CGST_RATE_FIELD_WIDTH + CGST_AMOUNT_FIELD_WIDTH +H_GAP:0;
		final int sgstFieldWidth=(SGST_RATE_FIELD_WIDTH + SGST_AMOUNT_FIELD_WIDTH)>0?SGST_RATE_FIELD_WIDTH + SGST_AMOUNT_FIELD_WIDTH +H_GAP:0;
		final int igstFieldWidth=(IGST_RATE_FIELD_WIDTH + IGST_AMOUNT_FIELD_WIDTH)>0?IGST_RATE_FIELD_WIDTH + IGST_AMOUNT_FIELD_WIDTH +H_GAP:0;
		
		
		left=1;
		printTextBlock(left, SLNO_FIELD_WIDTH, "Sl ", TextAlign.CENTER, false);

		left += SLNO_FIELD_WIDTH + H_GAP;
		printTextBlock(left, DESC_FIELD_WIDTH, "Commodity", TextAlign.LEFT,	false);

		left += DESC_FIELD_WIDTH + H_GAP;
		printTextBlock(left, HSN_CODE_WIDTH,  "HSN ", TextAlign.CENTER,	false);

		 
		/** The Rate field **/
		left += HSN_CODE_WIDTH + H_GAP;
		printTextBlock(left, UOM_FIELD_WIDTH, "UOM ", TextAlign.LEFT, false);

		/** The Rate field **/
		left += UOM_FIELD_WIDTH + H_GAP;
		printTextBlock(left,QTY_FIELD_WIDTH , "Qty ", TextAlign.CENTER, false);

		/** The Quantity field **/
		left += QTY_FIELD_WIDTH + H_GAP;
		printTextBlock(left, RATE_FIELD_WIDTH, "Rate", TextAlign.CENTER, false);

		left += RATE_FIELD_WIDTH + H_GAP;
		printTextBlock(left, AMOUNT_FIELD_WIDTH, "Amount ", TextAlign.CENTER, false);

		left += AMOUNT_FIELD_WIDTH + H_GAP;
		printTextBlock(left, CASH_DISC_FIELD_WIDTH, "Less:  ", TextAlign.CENTER, false);

		left += CASH_DISC_FIELD_WIDTH + H_GAP;
		printTextBlock(left, TAXABLE_AMT_FIELD_WIDTH, "Taxable ", TextAlign.CENTER, false);

		left += TAXABLE_AMT_FIELD_WIDTH + H_GAP;
		printTextBlock(left, cgstFieldWidth , "CGST", TextAlign.CENTER, false);

		left += cgstFieldWidth + H_GAP;
		printTextBlock(left, sgstFieldWidth, "SGST", TextAlign.CENTER, false);

		left += sgstFieldWidth + H_GAP;
		printTextBlock(left, igstFieldWidth , "IGST", TextAlign.CENTER, false);

		/** The Total Amount field **/
		left += igstFieldWidth + H_GAP;
		printTextBlock(left, TOTAL_FIELD_WIDTH, "Total ", TextAlign.CENTER, true);
		// Second Line
		setFontSize(6f);
		left=0;
		printTextBlock(left, SLNO_FIELD_WIDTH, "No", TextAlign.CENTER, false);

		left += SLNO_FIELD_WIDTH + H_GAP;
		printTextBlock(left, DESC_FIELD_WIDTH, " ", TextAlign.LEFT,	false,true);

		left += DESC_FIELD_WIDTH + H_GAP;
		printTextBlock(left, HSN_CODE_WIDTH,  "Code ", TextAlign.CENTER,	false);

		/** The Rate field **/

		left += HSN_CODE_WIDTH + H_GAP;
		printTextBlock(left, UOM_FIELD_WIDTH, " ", TextAlign.CENTER, false,true);

		/** The Rate field **/
		left += UOM_FIELD_WIDTH + H_GAP;
		printTextBlock(left, QTY_FIELD_WIDTH, "", TextAlign.CENTER, false,true);

		/** The Quantity field **/
		left += QTY_FIELD_WIDTH + H_GAP;
		printTextBlock(left, RATE_FIELD_WIDTH, "", TextAlign.CENTER, false);

		left += RATE_FIELD_WIDTH + H_GAP;
		printTextBlock(left, AMOUNT_FIELD_WIDTH, "", TextAlign.CENTER, false);

		left += AMOUNT_FIELD_WIDTH + H_GAP;
		printTextBlock(left, CASH_DISC_FIELD_WIDTH, "Disc", TextAlign.CENTER, false);

		left += CASH_DISC_FIELD_WIDTH + H_GAP; 
		printTextBlock(left, TAXABLE_AMT_FIELD_WIDTH, "Value", TextAlign.CENTER, false);

		left += TAXABLE_AMT_FIELD_WIDTH + H_GAP;
		printTextBlock(left, CGST_RATE_FIELD_WIDTH, "Rate", TextAlign.CENTER, false);

		left += CGST_RATE_FIELD_WIDTH + H_GAP;
		printTextBlock(left, CGST_AMOUNT_FIELD_WIDTH, "Amount", TextAlign.CENTER, false);


		left += CGST_AMOUNT_FIELD_WIDTH + H_GAP;
		printTextBlock(left, SGST_RATE_FIELD_WIDTH, "Rate", TextAlign.CENTER, false);

		left += SGST_RATE_FIELD_WIDTH + H_GAP;
		printTextBlock(left, SGST_AMOUNT_FIELD_WIDTH, "Amount", TextAlign.CENTER, false);
		
 		left += SGST_AMOUNT_FIELD_WIDTH + H_GAP;
		printTextBlock(left, IGST_RATE_FIELD_WIDTH, "Rate", TextAlign.CENTER, false);

		left += IGST_RATE_FIELD_WIDTH + H_GAP;
		printTextBlock(left, IGST_AMOUNT_FIELD_WIDTH, "Amount", TextAlign.CENTER, false);

		/** The Total Amount field **/
		left += IGST_AMOUNT_FIELD_WIDTH + H_GAP;
		printTextBlock(left, TOTAL_FIELD_WIDTH, "", TextAlign.CENTER, true,true);



	}

	/**
	 * Prints the details part
	 */
	private void printDetailItem(int srNo, BeanOrderDetail dtl) {

		
		int left = 0;

		/** The Serial number field **/
		printTextBlock(left, SLNO_FIELD_WIDTH, String.valueOf(srNo),
				TextAlign.CENTER, false);

		left += SLNO_FIELD_WIDTH   + H_GAP;
		printTextBlock(left, DESC_FIELD_WIDTH,PosSaleItemUtil.getItemNameToPrint(dtl.getSaleItem(), mUseAltLanguge) , TextAlign.LEFT, false);

		left += DESC_FIELD_WIDTH   + H_GAP;
		printTextBlock(left, HSN_CODE_WIDTH,( dtl.getSaleItem().getHSNCode()==null?dtl.getSaleItem().getHSNCode():dtl.getSaleItem().getHSNCode()) , TextAlign.LEFT, false,true);

		left += HSN_CODE_WIDTH   + H_GAP;
		printTextBlock(left, UOM_FIELD_WIDTH, dtl.getSaleItem().getUom().getCode(), 
				TextAlign.CENTER, false);
		
		/** The Quantity field **/
		left += UOM_FIELD_WIDTH + H_GAP;
		printTextBlock(left, QTY_FIELD_WIDTH,
				PosUomUtil.format(PosOrderUtil.getItemQuantity(dtl),PosUOMProvider.getInstance().getMaxDecUom()) , TextAlign.RIGHT, false);

		/** The Rate field **/
		left += QTY_FIELD_WIDTH + H_GAP;
		printTextBlock(left, RATE_FIELD_WIDTH,
				PosCurrencyUtil.format(dtl.getSaleItem().getFixedPrice()), TextAlign.RIGHT, false);

		/** The Total Amount field **/
		final double amt =PosOrderUtil.getItemQuantity(dtl) *dtl.getSaleItem().getFixedPrice();
		left += RATE_FIELD_WIDTH + H_GAP;
		printTextBlock(left, AMOUNT_FIELD_WIDTH,
				PosCurrencyUtil.format( amt ), TextAlign.RIGHT, false);

		left += AMOUNT_FIELD_WIDTH   + H_GAP;
		printTextBlock(left, CASH_DISC_FIELD_WIDTH,PosCurrencyUtil.format( PosSaleItemUtil.getTotalDiscountAmount(dtl.getSaleItem()) ), TextAlign.RIGHT, false);

		
//		final double taxableAmt=amt - (dtl.getSaleItem().getDiscount().isPercentage()? (amt *dtl.getSaleItem().getDiscount().getPrice() /100):dtl.getSaleItem().getDiscount().getPrice());
		final double taxableAmt=getTaxableAmount(dtl);
		left += CASH_DISC_FIELD_WIDTH   + H_GAP;
		printTextBlock(left, TAXABLE_AMT_FIELD_WIDTH,PosCurrencyUtil.format(taxableAmt) , TextAlign.RIGHT, false);

		left += TAXABLE_AMT_FIELD_WIDTH   + H_GAP;
		printTextBlock(left, CGST_RATE_FIELD_WIDTH, PosCurrencyUtil.format(dtl.getSaleItem().getTax().getTaxOnePercentage()), TextAlign.RIGHT, false);
		
		left += CGST_RATE_FIELD_WIDTH   + H_GAP;
		printTextBlock(left, CGST_AMOUNT_FIELD_WIDTH, PosCurrencyUtil.format(dtl.getSaleItem().getT1TaxAmount()), TextAlign.RIGHT, false);
		
		left += CGST_AMOUNT_FIELD_WIDTH   + H_GAP;
		printTextBlock(left, SGST_RATE_FIELD_WIDTH, PosCurrencyUtil.format(dtl.getSaleItem().getTax().getTaxTwoPercentage()), TextAlign.RIGHT, false);
		
		left += SGST_RATE_FIELD_WIDTH   + H_GAP;
		printTextBlock(left, SGST_AMOUNT_FIELD_WIDTH, PosCurrencyUtil.format(dtl.getSaleItem().getT2TaxAmount()), TextAlign.RIGHT, false);
		
		left += SGST_AMOUNT_FIELD_WIDTH   + H_GAP;
		printTextBlock(left, IGST_RATE_FIELD_WIDTH, PosCurrencyUtil.format(dtl.getSaleItem().getTax().getTaxThreePercentage()), TextAlign.RIGHT, false);
		
		left += IGST_RATE_FIELD_WIDTH   + H_GAP;
		printTextBlock(left, IGST_AMOUNT_FIELD_WIDTH, PosCurrencyUtil.format(dtl.getSaleItem().getT3TaxAmount()), TextAlign.RIGHT, false);
		
		final double itemTotal=PosSaleItemUtil.getGrandTotal(dtl.getSaleItem());
		left += IGST_AMOUNT_FIELD_WIDTH   + H_GAP;
		printTextBlock(left, TOTAL_FIELD_WIDTH, PosCurrencyUtil.format(itemTotal), TextAlign.RIGHT, true);
	 
		//item remarks 
		left=SLNO_FIELD_WIDTH   + H_GAP;
		
		if(PosEnvSettings.getInstance().getPrintSettings().getPaymentReceiptSettings().showItemRemarks())
			printTextBlock(left,DESC_FIELD_WIDTH,dtl.getRemarks(),TextAlign.LEFT,true );
			
		page_totalQty += PosOrderUtil.getItemQuantity(dtl);

		page_totalAmt += PosCurrencyUtil.roundTo(amt);
		page_totalCashDisc += PosCurrencyUtil.roundTo(PosSaleItemUtil.getTotalDiscountAmount(dtl.getSaleItem()));
		page_totalTaxableAmt += PosCurrencyUtil.roundTo(taxableAmt);
		page_totalCGST +=PosCurrencyUtil.roundTo(dtl.getSaleItem().getT1TaxAmount());
		page_totalSGST +=PosCurrencyUtil.roundTo(dtl.getSaleItem().getT2TaxAmount());
		page_totalIGST +=PosCurrencyUtil.roundTo(dtl.getSaleItem().getT3TaxAmount());
		page_subTotal +=PosCurrencyUtil.roundTo(itemTotal);

	}

	/**
	 * @param order
	 * @throws Exception
	 */
	private void printPageSummary(BeanOrderHeader order) throws Exception {

		int left =SLNO_FIELD_WIDTH + H_GAP;
		left +=DESC_FIELD_WIDTH + H_GAP;
		left +=HSN_CODE_WIDTH + H_GAP;
		left +=UOM_FIELD_WIDTH + H_GAP;


		left += QTY_FIELD_WIDTH + H_GAP;
		left += RATE_FIELD_WIDTH + H_GAP;
		printTextBlock(left, AMOUNT_FIELD_WIDTH,
				PosCurrencyUtil.format( page_totalAmt ), TextAlign.RIGHT, false);

		left += AMOUNT_FIELD_WIDTH   + H_GAP;
		printTextBlock(left, CASH_DISC_FIELD_WIDTH,PosCurrencyUtil.format(page_totalCashDisc), TextAlign.RIGHT, false);

		left += CASH_DISC_FIELD_WIDTH   + H_GAP;
		printTextBlock(left, TAXABLE_AMT_FIELD_WIDTH,PosCurrencyUtil.format(page_totalTaxableAmt) , TextAlign.RIGHT, false);

		left += TAXABLE_AMT_FIELD_WIDTH   + H_GAP;
		left += CGST_RATE_FIELD_WIDTH   + H_GAP;
		printTextBlock(left, CGST_AMOUNT_FIELD_WIDTH, PosCurrencyUtil.format(page_totalCGST), TextAlign.RIGHT, false);

		
		left += CGST_AMOUNT_FIELD_WIDTH   + H_GAP;
		left += SGST_RATE_FIELD_WIDTH   + H_GAP;
		printTextBlock(left, SGST_AMOUNT_FIELD_WIDTH, PosCurrencyUtil.format(page_totalSGST), TextAlign.RIGHT, false);

		left += SGST_AMOUNT_FIELD_WIDTH   + H_GAP;
		left += IGST_RATE_FIELD_WIDTH   + H_GAP;
		printTextBlock(left, IGST_AMOUNT_FIELD_WIDTH, PosCurrencyUtil.format(page_totalIGST), TextAlign.RIGHT, false);

				
		left += IGST_AMOUNT_FIELD_WIDTH   + H_GAP;
		printTextBlock(left, TOTAL_FIELD_WIDTH,PosCurrencyUtil.format(page_subTotal), TextAlign.RIGHT, true);
		advanceTextLine(1);

		mBillFooterEndY=getNextLineStartY();
	}

	
	/*
	 * 
	 */
	private void printReportSummary(){

//		advanceTextLine(1);
		advanceLine();
		
		final int rptFooterStartY=getNextLineStartY();
		printSingleLine();
		
		final int rptFooterSection1Width=getWidth()-REPORT_FOOTER_AMT_FIELD_WIDTH-REPORT_FOOTER_AMT_DATA_WIDTH-H_GAP;
		

		final Double  roundingAdjustment=mOrder.getRoundAdjustmentAmount();
		final String roundingAdjustmentText= PosCurrencyUtil.format(roundingAdjustment);

		final double netPayble=PosCurrencyUtil.roundTo(mOrder.getTotalAmount())
				+roundingAdjustment;
				//-mOrder.getBillDiscountAmount();
		final String netAmtText =  PosCurrencyUtil.format(netPayble);
 		
		int left=0;

		left=rptFooterSection1Width ;
 		final int rptFtrStartIndex=getNextLineStartY();
 		final String[] amolines = PosStringUtil.getWrappedLines(  MoneyToWordsConvertor.toWords(netAmtText),getGraphics(),getPosReportPageFormat().getImageableWidthInPixcel());
	 
 		//*******Starting **** Left section in report footer 
 		
		int amtTextIndex=0;
		amtTextIndex=printMoneyInText(amolines,amtTextIndex,rptFooterSection1Width);
		amtTextIndex=printMoneyInText(amolines,amtTextIndex,rptFooterSection1Width);
		amtTextIndex=printMoneyInText(amolines,amtTextIndex,rptFooterSection1Width);
		
		printLine(0, getNextLineStartY(), rptFooterSection1Width, getNextLineStartY(), false); //bank detail separation
		
		final int rptFooterBankDataWidth=rptFooterSection1Width-REPORT_FOOTER_BANK_FIELD_WIDTH;

		setFontStyle(Font.BOLD);
		printTextBlock(START_INDEX, rptFooterSection1Width, ":Bank Details :", TextAlign.CENTER,true);
		advanceLine(3);
		setFont(mFontReceipt);

		printTextBlock(START_INDEX, REPORT_FOOTER_BANK_FIELD_WIDTH -H_GAP*2," Bank Name ", TextAlign.LEFT,false);
		printTextBlock(START_INDEX +REPORT_FOOTER_BANK_FIELD_WIDTH-H_GAP*2, H_GAP*2,":  ", TextAlign.LEFT,false);
		printTextBlock(START_INDEX +REPORT_FOOTER_BANK_FIELD_WIDTH, rptFooterBankDataWidth, PosEnvSettings.getInstance().getShop().getBankName() , TextAlign.LEFT,true,true);
		advanceTextLine(1);
		
		printTextBlock(START_INDEX, REPORT_FOOTER_BANK_FIELD_WIDTH -H_GAP*2," Account Number ", TextAlign.LEFT,false);
		printTextBlock(START_INDEX +REPORT_FOOTER_BANK_FIELD_WIDTH-H_GAP*2, H_GAP*2,":  ", TextAlign.LEFT,false);
		printTextBlock(START_INDEX +REPORT_FOOTER_BANK_FIELD_WIDTH, rptFooterBankDataWidth, PosEnvSettings.getInstance().getShop().getBankAccountNo() , TextAlign.LEFT,true,true);
		advanceTextLine(1);
		
		printTextBlock(START_INDEX, REPORT_FOOTER_BANK_FIELD_WIDTH -H_GAP*2," Branch IFSC", TextAlign.LEFT,false);
		printTextBlock(START_INDEX +REPORT_FOOTER_BANK_FIELD_WIDTH-H_GAP*2, H_GAP*2,":  ", TextAlign.LEFT,false);
		printTextBlock(START_INDEX +REPORT_FOOTER_BANK_FIELD_WIDTH, rptFooterBankDataWidth, PosEnvSettings.getInstance().getShop().getBankIFSCCode() , TextAlign.LEFT,true,true);
		advanceTextLine(1);
			
		printLine(0, getNextLineStartY(), rptFooterSection1Width  , getNextLineStartY(), false);
			
 
		final int rptFooterBankDetStartY=getNextLineStartY();
	 
    	final int commonSealWidth=rptFooterSection1Width/3;
	
//		printTextBlock(START_INDEX, commonSealWidth*2 ," ", TextAlign.CENTER,true,true);
//		advanceLine(2);
		
    	advanceLine();
		setFontStyle(Font.BOLD);
		printTextBlock(START_INDEX,  commonSealWidth*2 ,":Terms and Conditions :", TextAlign.CENTER,true);
		setFont(mFontReceipt);
		
		advanceTextLine(5);
		setFontSize(6f);
		printTextBlock( commonSealWidth*2  , commonSealWidth ,
				"(Common Seal)", TextAlign.CENTER,true);
		
		//*******Ending ---- Left section in report footer 
	
		
		//*******Starting ---- right section in report footer 
		setFont(mFontReceipt);
		setNextLineStartY(rptFtrStartIndex);
		printAmountSummary(left,"Total Amount Before Tax", page_totalTaxableAmt); 
		
		advanceLine(2);
		final double totalDiscAmt=PosOrderUtil.getDiscountForPaymentReceipt(mOrder, page_totalTaxableAmt);
		if(totalDiscAmt>0)
			printAmountSummary(left,"Discount", totalDiscAmt); 
		else
			printTextBlock(left , H_GAP," ", TextAlign.LEFT,true,true);
		
		advanceLine(3);		
		
		
		if(totalDiscAmt>0){
			
			page_totalCGST=page_totalCGST-
				PosOrderUtil.getDiscountForPaymentReceipt(mOrder, page_totalCGST);
			
			page_totalSGST=page_totalSGST-
					PosOrderUtil.getDiscountForPaymentReceipt(mOrder, page_totalSGST);
			
			page_totalIGST=page_totalIGST-
					PosOrderUtil.getDiscountForPaymentReceipt(mOrder, page_totalIGST);
					
		}
		printAmountSummary(left,"Add : CGST", page_totalCGST); 
		printAmountSummary(left,"Add : SGST", page_totalSGST); 
		printAmountSummary(left,"Add : IGST", page_totalIGST); 

		advanceLine(3);		
		 
		printAmountSummary(left,"Tax Amount : GST", page_totalCGST+page_totalSGST+page_totalIGST); 
		
		advanceLine(3);				
		if(Double.parseDouble(roundingAdjustmentText)!=0)
			printAmountSummary(left,"Round Off ",  Double.parseDouble(roundingAdjustmentText)); 
		else
			printTextBlock(left , H_GAP," ", TextAlign.LEFT,true,true);
 
		
		advanceLine(3);		
		setFontStyle(Font.BOLD);
		printAmountSummary(left,"Net Amount",  netPayble); 
		
		
		advanceLine(3);
		printAmountSummary(left,"GST Payable on Reverse Charge",  0); 
		advanceLine(4);
		setFont(mFontReceipt);
		printLine(left, getNextLineStartY(), getWidth(), getNextLineStartY(), false);
		printTextBlock(left + START_INDEX, REPORT_FOOTER_AMT_FIELD_WIDTH +REPORT_FOOTER_AMT_DATA_WIDTH+H_GAP ,
				"Certified that the particulars given above are true and correct", TextAlign.LEFT,true);
			
		advanceLine(1);
		setFontStyle(Font.BOLD);
		printTextBlock(left, REPORT_FOOTER_AMT_FIELD_WIDTH +REPORT_FOOTER_AMT_DATA_WIDTH+H_GAP ,
				"For " + PosEnvSettings.getInstance().getShop().getName(), TextAlign.CENTER,true);
		advanceTextLine(2);
		
		 
		 
	
		setFont(mFontReceipt);
		printTextBlock(left, REPORT_FOOTER_AMT_FIELD_WIDTH +REPORT_FOOTER_AMT_DATA_WIDTH+H_GAP ,
				"Authorised Signatory", TextAlign.CENTER,true);
		
		printText(TextAlign.RIGHT, "[E&OE] ");
		mBillFooterEndY=getNextLineStartY();
		printSingleLine();
		//report footer amount separator vertical line 
		printLine(left, rptFooterStartY, left, mBillFooterEndY, false);
		//report footer bank  det vertical line 
		printLine( commonSealWidth*2 , rptFooterBankDetStartY, commonSealWidth*2, mBillFooterEndY, false);
		
		printText(TextAlign.CENTER, ".",true,true);
	}
	
	private void  printAmountSummary(int left,String amountTitle,double amount ){
		
		printTextBlock(left + START_INDEX , REPORT_FOOTER_AMT_FIELD_WIDTH-START_INDEX,amountTitle, TextAlign.LEFT,false,false);
		printTextBlock(left + REPORT_FOOTER_AMT_FIELD_WIDTH, H_GAP, ":  ", TextAlign.LEFT,false,false);
		printTextBlock(left + REPORT_FOOTER_AMT_FIELD_WIDTH + H_GAP, REPORT_FOOTER_AMT_DATA_WIDTH-END_INDEX,amount==0?" ":PosCurrencyUtil.format(amount), TextAlign.RIGHT, true,true);
		
	} 
	private int printMoneyInText(String[] amolines,int amtTextIndex,int width){


		if (amtTextIndex==0){
			final String netAmtInWordsTitle=  "Total Invoice Amount in Words : ";
			printTextBlock(START_INDEX, width,netAmtInWordsTitle, TextAlign.CENTER,true,true);
			++amtTextIndex;
		}else if(amtTextIndex<=amolines.length){
			printTextBlock(START_INDEX, width,amolines[amtTextIndex-1], TextAlign.CENTER,true,true);
			++amtTextIndex;
		}else
			printTextBlock(START_INDEX, width,"", TextAlign.CENTER,true,true);
			 return amtTextIndex;
	}
 
	public static ArrayList<String> splitString(String msg, int lineSize) {
		ArrayList<String> res = new ArrayList<>();

        Pattern p = Pattern.compile("\\b.{1," + (lineSize-1) + "}\\b\\W?");
        Matcher m = p.matcher(msg);
        
	while(m.find()) {
                res.add(m.group());
        }
        return res;
    }
	
	/*
	 * 
	 */

	/**
	 * @param item
	 * @return
	 */
	private double getTaxableAmount(BeanOrderDetail ordDet){
	
	double amt =(PosOrderUtil.getItemQuantity(ordDet) *ordDet.getSaleItem().getFixedPrice()) ;
	
	amt=amt-
			(ordDet.getSaleItem().getDiscount().isPercentage()? (amt *ordDet.getSaleItem().getDiscount().getPrice() /100):ordDet.getSaleItem().getDiscount().getPrice());
	
	final double taxableAmt=amt- 
				(ordDet.getSaleItem().getTaxCalculationMethod().equals(TaxCalculationMethod.InclusiveOfTax)?
				(ordDet.getSaleItem().getT1TaxAmount()+ordDet.getSaleItem().getT2TaxAmount()+
						ordDet.getSaleItem().getT3TaxAmount() +ordDet.getSaleItem().getGSTAmount() +
						ordDet.getSaleItem().getServiceTaxAmount()): 0);
				
				return taxableAmt;
	}
	/**
	 * @throws Exception
	 */
	private void onPrintReportFooter() throws Exception {

//		final String footerLine_1="DECLARATION";
//		final String footerLine_2="Certified that all the particulars shown in the above tax invoice are true and " +
//				" correct and that my/our Registration under KVAT Act 2003 is valid as on the date of this Bill."; 
//		advanceTextLine(1);
//		setFontStyle(Font.BOLD);
//		printText(TextAlign.CENTER, footerLine_1,true,true);
		setFont(mFontReceipt);

//
//		final String[] footerLines = PosStringUtil.getWrappedLines(footerLine_2,getGraphics(),getPosReportPageFormat().getImageableWidthInPixcel());
//
//		for (String ftLine : footerLines) {
//			
//			printText(TextAlign.LEFT, ftLine);
//		}
//
//		advanceTextLine(2);
		

	}

	 

	/* (non-Javadoc)
	 * @see com.indocosmo.pos.reports.receipts.PosPrintableReceiptBase#setOrder(com.indocosmo.pos.data.beans.BeanOrderHeader)
	 */
	public void setOrder(BeanOrderHeader order) {

		this.mOrder=order;

	}


	/**
	 * @return
	 * @throws IOException 
	 */
	
	private void loadPRDPaymentReceiptSettings() throws IOException{
		
		
		Properties mPrintProperties =PosFileUtils.loadPropertyFile(PROPERTY_FILE);
		
		SLNO_FIELD_WIDTH=
				PosNumberUtil.parseIntegerSafely(mPrintProperties.getProperty("production.tax_invoice.column_settings.sl_no.width","14"));
		
		HSN_CODE_WIDTH=
				PosNumberUtil.parseIntegerSafely(mPrintProperties.getProperty("production.tax_invoice.column_settings.hsn_code.width","20"));
		
		UOM_FIELD_WIDTH=
				PosNumberUtil.parseIntegerSafely(mPrintProperties.getProperty("production.tax_invoice.column_settings.uom.width","22"));
		
		QTY_FIELD_WIDTH=
				PosNumberUtil.parseIntegerSafely(mPrintProperties.getProperty("production.tax_invoice.column_settings.quantity.width","25"));
		
		RATE_FIELD_WIDTH=
				PosNumberUtil.parseIntegerSafely(mPrintProperties.getProperty("production.tax_invoice.column_settings.rate.width","28"));
		
		AMOUNT_FIELD_WIDTH=
				PosNumberUtil.parseIntegerSafely(mPrintProperties.getProperty("production.tax_invoice.column_settings.amount.width","39"));
		
		CASH_DISC_FIELD_WIDTH=
				PosNumberUtil.parseIntegerSafely(mPrintProperties.getProperty("production.tax_invoice.column_settings.discount.width","30"));
		
		TAXABLE_AMT_FIELD_WIDTH=
				PosNumberUtil.parseIntegerSafely(mPrintProperties.getProperty("production.tax_invoice.column_settings.taxable_amount.width","39"));
		
		CGST_RATE_FIELD_WIDTH=
				PosNumberUtil.parseIntegerSafely(mPrintProperties.getProperty("production.tax_invoice.column_settings.cgst_rate.width","20"));
		
		CGST_AMOUNT_FIELD_WIDTH=
				PosNumberUtil.parseIntegerSafely(mPrintProperties.getProperty("production.tax_invoice.column_settings.cgst_amount.width","28"));
		
		SGST_RATE_FIELD_WIDTH=
				PosNumberUtil.parseIntegerSafely(mPrintProperties.getProperty("production.tax_invoice.column_settings.sgst_rate.width","20"));
		
		SGST_AMOUNT_FIELD_WIDTH=
				PosNumberUtil.parseIntegerSafely(mPrintProperties.getProperty("production.tax_invoice.column_settings.sgst_amount.width","28"));
		
		IGST_RATE_FIELD_WIDTH=
				PosNumberUtil.parseIntegerSafely(mPrintProperties.getProperty("production.tax_invoice.column_settings.igst_rate.width","20"));
		
		IGST_AMOUNT_FIELD_WIDTH=
				PosNumberUtil.parseIntegerSafely(mPrintProperties.getProperty("production.tax_invoice.column_settings.igst_amount.width","28"));
		
		TOTAL_FIELD_WIDTH=
				PosNumberUtil.parseIntegerSafely(mPrintProperties.getProperty("production.tax_invoice.column_settings.total_amount.width","40"));
	}
	
	
	
}
