/**
 * 
 */
package com.indocosmo.pos.reports.receipts;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

import com.indocosmo.pos.common.PosEnvSettings;
import com.indocosmo.pos.common.beans.BeanReceiptTaxSummary;
import com.indocosmo.pos.common.enums.device.PrinterType;
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
import com.indocosmo.pos.common.utilities.converters.MoneyToWordsConvertor;
import com.indocosmo.pos.data.beans.BeanBillParam;
import com.indocosmo.pos.data.beans.BeanOrderDetail;
import com.indocosmo.pos.data.beans.BeanOrderHeader;
import com.indocosmo.pos.data.beans.BeanOrderHeader.PosOrderStatus;
import com.indocosmo.pos.data.beans.BeanOrderPayment;
import com.indocosmo.pos.data.beans.BeanSaleItem;
import com.indocosmo.pos.data.beans.BeanShop;
import com.indocosmo.pos.data.beans.BeanTax;
import com.indocosmo.pos.data.providers.shopdb.PosBillParamProvider;
import com.indocosmo.pos.data.providers.shopdb.PosDiscountItemProvider;
import com.indocosmo.pos.data.providers.shopdb.PosSaleItemProvider;
import com.indocosmo.pos.data.providers.shopdb.PosTaxAmountObject;
import com.indocosmo.pos.data.providers.shopdb.PosTaxItemProvider;
import com.indocosmo.pos.data.providers.shopdb.PosTaxItemProvider.TaxCalculationMethod;
import com.indocosmo.pos.data.providers.shopdb.PosUOMProvider;
import com.indocosmo.pos.reports.base.PosPrintableReportBase;
import com.indocosmo.pos.reports.base.PosReportPageFormat;

/**
 * @author sandhya
 * 
 */
public class PosSalesOrderInvoice extends PosPrintableReportBase {
	
	private static final String PROPERTY_FILE="pos-terminal-receipt-pymt-prd.properties"; 

	/**
	 * Serial Number column width
	 */
	
	private static final int REPORT_HEADER_HEIGHT=150;

	private static final int REPORT_FOOTER_HEIGHT=225;
	private static final int PAGE_FOOTER_HEIGHT=17;
	private int REPORT_HEIGHT;
	private int SLNO_FIELD_WIDTH=20; 
	private int HSN_CODE_WIDTH=50; 
	private int QTY_FIELD_WIDTH=50;
	private int UOM_FIELD_WIDTH=40;
	private int RATE_FIELD_WIDTH=50;
	private int CASH_DISC_FIELD_WIDTH=50;
	private int AMOUNT_FIELD_WIDTH=80;

	private int TAX_RATE_FIELD_WIDTH=40;
	private int TAX_AMOUNT_FIELD_WIDTH=50;
	private static final int REPORT_FOOTER_AMT_FIELD_WIDTH=160;
	private static final int REPORT_FOOTER_AMT_DATA_WIDTH=100;
	 
	private static final int HDR_GAP =6;
	private static final int H_GAP =4;
	private static final int LINE_GAP=8;

	private static final int START_INDEX=4; // GAP B/W Line & First character 
	private static final int END_INDEX=4;
	
	private int DESC_FIELD_WIDTH=0;
	private int PAGE_HDR_DATA_WIDTH; 
	

	private BeanOrderHeader mOrder;
	private BeanBillParam mBillParam;

	private int report_itemIndex=0;
	private int report_slNo = 0;

	private int page_itemIndex=-1;
	private int page_slNo = 1;

	private double page_totalQty=0;
	private double page_totalAmt=0;
	 
	private double report_totalQty=0;
	private double report_totalAmt=0;
	 
	private int mPageHdrStartY=0;
	private int mPageHdrEndY=0;
	

	public boolean isEndOfReport=false;
	private HashMap<Integer, BeanReceiptTaxSummary> itemTaxList;
	private HashMap<String, BeanReceiptTaxSummary> itemHSNTaxList;
	protected double totalCashPayment=0.0;
	protected double totalCardPayment=0.0;
	protected double totalCashOutPayment=0.0;
	protected double totalCompanyPayment=0.0;
	protected double totalCouponPayment=0.0;
	protected double totalBalancePayment=0.0;
	protected double totalCouponBalancePayment=0.0;
	private  boolean isPrintDetailSummary=false;
	/**
	 * @throws IOException 
	 * 
	 */
	public PosSalesOrderInvoice(BeanOrderHeader order) throws IOException {
		super();

		setOrder(order);
		
	}

	/**
	 * 
	 */
	public PosSalesOrderInvoice() {

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

				SLNO_FIELD_WIDTH+
				HSN_CODE_WIDTH +
			 	QTY_FIELD_WIDTH+
				RATE_FIELD_WIDTH+
				UOM_FIELD_WIDTH + 
				CASH_DISC_FIELD_WIDTH +
				AMOUNT_FIELD_WIDTH
				) ;

		loadBillParams();		
		REPORT_HEIGHT=getHeight();
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
		
		isEndOfReport=false;
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.pos.reports.base.PosPrintableReportBase#initReport()
	 */
	@Override
	protected void initReport() {
		itemTaxList=new HashMap<Integer, BeanReceiptTaxSummary>();
		itemHSNTaxList=new HashMap<String, BeanReceiptTaxSummary>();
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
		
		printHeader(mOrder, mBillParam);
//		printSingleLine();
		printDetailsHeader();
		advanceLine(2);
		printSingleLine();
 
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.pos.reports.base.PosPrintableReportBase#printPageDetails(int)
	 */
	@Override
	protected void printPageDetails(int pageIndex) throws Exception {

		printDetails(mOrder);

	}

	/* (non-Javadoc)
	 * @see com.indocosmo.pos.reports.base.PosPrintableReportBase#printPageFooter(int)
	 */
	@Override
	protected void printPageFooter(int pageIndex) throws Exception {
		
		
		int foorterStartY=getNextLineStartY();
		 

			final int newFtrStartY=getHeight()-(REPORT_FOOTER_HEIGHT+PAGE_FOOTER_HEIGHT);
			// will print only page footer if not enough space for report footer section 
			foorterStartY=(newFtrStartY>=getNextLineStartY())?newFtrStartY:getHeight()-PAGE_FOOTER_HEIGHT;
 		

//		printLine(1, foorterStartY, getWidth(), foorterStartY);
		setNextLineStartY(foorterStartY+getLineSpacing());
		printPageSummary(mOrder);
//		printLine(1, foorterStartY+PAGE_FOOTER_HEIGHT, getWidth(), foorterStartY+PAGE_FOOTER_HEIGHT);
//		printDetailLayout(REPORT_HEADER_HEIGHT+PAGE_HEADER_HEIGHT-PAGE_DETAIL_HEADER_HEIGHT,foorterStartY+PAGE_FOOTER_HEIGHT);
		printDetailLayout(foorterStartY+PAGE_FOOTER_HEIGHT);

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

	
		return !isEndOfReport && page_itemIndex<mOrder.getOrderDetailItems().size()-1;
	}

	/**
	 * @param order
	 * @param param
	 * @throws Exception
	 */
	private void printHeader(BeanOrderHeader order, BeanBillParam param)
			throws Exception {

		setFontSize(14.0f);
		
		advanceLine();
		printText(TextAlign.CENTER, (order.getStatus()==PosOrderStatus.Closed ||order.getStatus()==PosOrderStatus.Refunded)?"TAX INVOICE":"ESTIMATE");
 
		mPageHdrStartY=getNextLineStartY();
//		printSingleLine();
		 
		setFont(mFontReceipt); 
		BeanShop shop=PosEnvSettings.getInstance().getShop();
		
		PAGE_HDR_DATA_WIDTH=getWidth()/2;

		advanceLine(2);
		mPageHdrStartY=getNextLineStartY();
		final int shopCustomerColumnWidth=PAGE_HDR_DATA_WIDTH-START_INDEX-HDR_GAP;
		
		final String phNo= "Ph:" +  ((shop.getPhoneNumber() !=null && !shop.getPhoneNumber().trim().equals(""))?  shop.getPhoneNumber():"");
		final String email= "E-Mail:" + ((shop.getEmail() !=null && !shop.getEmail().trim().equals(""))?  shop.getEmail():"");
		final String gsTin= "GSTIN/UIN: " + ((shop.getCompanyTaxNo() !=null && !shop.getCompanyTaxNo().trim().equals(""))?  shop.getCompanyTaxNo():"");
		setFontStyle(Font.BOLD);
		printTextBlock(START_INDEX, shopCustomerColumnWidth, shop.getName() , TextAlign.LEFT,true,true);
		setFont(mFontReceipt);
		
		printTextBlock(START_INDEX, shopCustomerColumnWidth, shop.getAddress() , TextAlign.LEFT,true,true);
		printTextBlock(START_INDEX, shopCustomerColumnWidth, shop.getCity(), TextAlign.LEFT,true,true);
		printTextBlock(START_INDEX, shopCustomerColumnWidth, shop.getState(), TextAlign.LEFT,true,true);
		printTextBlock(START_INDEX, shopCustomerColumnWidth, phNo, TextAlign.LEFT,true,true);
		printTextBlock(START_INDEX, shopCustomerColumnWidth, email, TextAlign.LEFT,true,true);
		printTextBlock(START_INDEX, shopCustomerColumnWidth, gsTin, TextAlign.LEFT,true,true);
		
		//horizontal line - shop and customer separations
		printLine(0, getNextLineStartY(), PAGE_HDR_DATA_WIDTH ,  getNextLineStartY(), false);
		printTextBlock(START_INDEX, shopCustomerColumnWidth, "Buyer " , TextAlign.LEFT,true,true);
		advanceLine();
		setFontStyle(Font.BOLD);
		printTextBlock(START_INDEX, shopCustomerColumnWidth, mOrder.getOrderCustomer().getName() , TextAlign.LEFT,true,true);
		setFont(mFontReceipt);
		printTextBlock(START_INDEX, shopCustomerColumnWidth, mOrder.getOrderCustomer().getAddress() , TextAlign.LEFT,true,false);
		printTextBlock(START_INDEX, shopCustomerColumnWidth, mOrder.getOrderCustomer().getCity() , TextAlign.LEFT,true,false);
		printTextBlock(START_INDEX, shopCustomerColumnWidth,"Ph: " +  mOrder.getOrderCustomer().getPhoneNumber() , TextAlign.LEFT,true,false);
		advanceLine();
		final String state="State Name :   " +  (mOrder.getOrderCustomer().getState() ==null?"   ":mOrder.getOrderCustomer().getState()) + 
							",     Code:  " + (mOrder.getOrderCustomer().getStateCode() ==null?"":mOrder.getOrderCustomer().getStateCode());
		printTextBlock(START_INDEX, shopCustomerColumnWidth,state , TextAlign.LEFT,true,true);
		final String customerTin= "GSTIN/UIN: " + ((mOrder.getOrderCustomer().getTinNo() !=null && !mOrder.getOrderCustomer().getTinNo().trim().equals(""))?  mOrder.getOrderCustomer().getTinNo():"");
		printTextBlock(START_INDEX, shopCustomerColumnWidth,customerTin , TextAlign.LEFT,true,true);
		
		
		final int orderFieldWidth=PAGE_HDR_DATA_WIDTH/2 -HDR_GAP ;
		final int orderColumn1Left=PAGE_HDR_DATA_WIDTH + HDR_GAP ;
		final int orderColumn2Left=PAGE_HDR_DATA_WIDTH + orderFieldWidth + HDR_GAP *2;
		
		String payMode=totalCashPayment>0?"Cash":"";
		payMode+=  totalCardPayment>0?(payMode.trim().length()>0?",":"") + "Card":"";
		payMode+=  totalCompanyPayment>0?(payMode.trim().length()>0?",":"") + "Company":"";
		payMode+=  totalCouponPayment>0?(payMode.trim().length()>0?",":"") + "Voucher":"";
		
		
		setNextLineStartY(mPageHdrStartY);
		printTextBlock(orderColumn1Left,orderFieldWidth, "Invoice No. ", TextAlign.LEFT,false,true);
		printTextBlock(orderColumn2Left,orderFieldWidth, "Dated ", TextAlign.LEFT,true,true);
//		advanceLine();
		printTextBlock(orderColumn1Left,orderFieldWidth,
				(order.getStatus()==PosOrderStatus.Closed || order.getStatus()==PosOrderStatus.Refunded)?mOrder.getInvoiceNo():"", TextAlign.LEFT,false,true);
//		Date closingDate=PosDateUtil.parse(PosDateUtil.DATE_FORMAT , order.getClosingDate());
		final String dateText=PosDateUtil.formatLocal(
						(order.getStatus()==PosOrderStatus.Closed || order.getStatus()==PosOrderStatus.Refunded)? order.getClosingDate():order.getOrderDate());
		printTextBlock(orderColumn2Left,orderFieldWidth,dateText , TextAlign.LEFT,true,true);
		
		printLine(PAGE_HDR_DATA_WIDTH, getNextLineStartY(), getWidth(), getNextLineStartY());
		
		printTextBlock(orderColumn1Left,orderFieldWidth,"Delivery Note ", TextAlign.LEFT,false,true);
		printTextBlock(orderColumn2Left,orderFieldWidth,"Mode/Terms of Payment", TextAlign.LEFT,true,true);
//		advanceLine();
		printTextBlock(orderColumn1Left,orderFieldWidth," ", TextAlign.LEFT,false,true);
		printTextBlock(orderColumn2Left,orderFieldWidth,payMode, TextAlign.LEFT,true,true);

		printLine(PAGE_HDR_DATA_WIDTH, getNextLineStartY(), getWidth(), getNextLineStartY());
		
		printTextBlock(orderColumn1Left,orderFieldWidth, "Supplier's Ref. ", TextAlign.LEFT,false,true);
		printTextBlock(orderColumn2Left,orderFieldWidth, "Other Reference(s) ", TextAlign.LEFT,true,true);
//		advanceLine();
		printTextBlock(orderColumn1Left,orderFieldWidth, mOrder.getInvoiceNo(), TextAlign.LEFT,false,true);
		printTextBlock(orderColumn2Left,orderFieldWidth, " ", TextAlign.LEFT,true,true);
//		advanceLine();
		printLine(PAGE_HDR_DATA_WIDTH, getNextLineStartY(), getWidth(), getNextLineStartY());
		
		
		printTextBlock(orderColumn1Left,orderFieldWidth, "Buyer's Order No. ", TextAlign.LEFT,false,true);
		printTextBlock(orderColumn2Left,orderFieldWidth, "Dated ", TextAlign.LEFT,true,true);
//		advanceLine();
//		final String refNo=(PosEnvSettings.getInstance().getUISetting().useOrderQueueNo()?PosOrderUtil.getFormatedOrderQueueNo(order):
//			PosOrderUtil.getShortOrderIDFromOrderID(order.getOrderId()));
		final String refNo= PosOrderUtil.getShortOrderIDFromOrderID(order.getOrderId());
		printTextBlock(orderColumn1Left,orderFieldWidth, refNo, TextAlign.LEFT,false,true);
		printTextBlock(orderColumn2Left,orderFieldWidth,  PosDateUtil.formatLocal(order.getOrderDate()), TextAlign.LEFT,true,true);
		printLine(PAGE_HDR_DATA_WIDTH, getNextLineStartY(), getWidth(), getNextLineStartY());
		
		
		printTextBlock(orderColumn1Left,orderFieldWidth, "Despatch Document No. ", TextAlign.LEFT,false,true);
		printTextBlock(orderColumn2Left,orderFieldWidth, "Delivery Note Date  ", TextAlign.LEFT,true,true);
//		advanceLine();
		printTextBlock(orderColumn1Left,orderFieldWidth, "", TextAlign.LEFT,false,true);
		printTextBlock(orderColumn2Left,orderFieldWidth, "", TextAlign.LEFT,true,true);
		printLine(PAGE_HDR_DATA_WIDTH, getNextLineStartY(), getWidth(), getNextLineStartY());
		
		
		printTextBlock(orderColumn1Left,orderFieldWidth, "Despatched through ", TextAlign.LEFT,false,true);
		printTextBlock(orderColumn2Left,orderFieldWidth, "Destination ", TextAlign.LEFT,true,true);
//		advanceLine();
		printTextBlock(orderColumn1Left,orderFieldWidth, "", TextAlign.LEFT,false,true);
		printTextBlock(orderColumn2Left,orderFieldWidth, "", TextAlign.LEFT,true,true);
		final int termsOfDeliveryTop=getNextLineStartY();
		printLine(PAGE_HDR_DATA_WIDTH, getNextLineStartY(), getWidth(), getNextLineStartY());
		
		
		
		printTextBlock(orderColumn1Left,orderFieldWidth*2, "Terms of Delivery ", TextAlign.LEFT,false,true);
		
		printTextBlock(orderColumn1Left,orderFieldWidth*2, "", TextAlign.LEFT,false,true);
		 
		advanceTextLine(5); 
		mPageHdrEndY=getNextLineStartY();
		printSingleLine();
		//mPageHdrEndY=mPageHdrStartY + REPORT_HEADER_HEIGHT;
		
		// 1st vertical line
		printLine(PAGE_HDR_DATA_WIDTH, mPageHdrStartY, PAGE_HDR_DATA_WIDTH  , mPageHdrEndY ,false);
		
		// 2nd vertical line
		printLine(PAGE_HDR_DATA_WIDTH +orderFieldWidth+HDR_GAP  , mPageHdrStartY, PAGE_HDR_DATA_WIDTH +orderFieldWidth+HDR_GAP   , termsOfDeliveryTop ,false);
				
		//setNextLineStartY(mPageHdrEndY);

	
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

			if(getNextLineStartY()+getLineHeight()>=(REPORT_HEIGHT-PAGE_FOOTER_HEIGHT))
				break;

		}
		PosOrderUtil.setExtraChargeTaxSummary(order,itemTaxList);
		setExtraChargeHSNTaxSummary();
		if(!isPrintDetailSummary)
			printDetailSummary(mOrder);

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


	private void printDetailLayout(int endY) throws Exception{

		int lineLeft=0;
		
//		final int endY=REPORT_HEIGHT-REPORT_FOOTER_HEIGHT + getLineHeight();
		// left border 
		printLine(0 , mPageHdrStartY, 0 , REPORT_HEIGHT,false);
		
		// right border 
		printLine(getWidth() , mPageHdrStartY, getWidth() , REPORT_HEIGHT,false);
		
		//top border
		printLine(0 , mPageHdrStartY, getWidth() , mPageHdrStartY ,false);
		
		//bottom border
		printLine(0 , REPORT_HEIGHT, getWidth() , REPORT_HEIGHT ,false);
			
		
		lineLeft= SLNO_FIELD_WIDTH;
		printLine(lineLeft, mPageHdrEndY, lineLeft, endY,false);

		lineLeft += DESC_FIELD_WIDTH;
		printLine(lineLeft, mPageHdrEndY, lineLeft, endY,false);


		lineLeft += HSN_CODE_WIDTH;
		if(HSN_CODE_WIDTH>0)
			printLine(lineLeft, mPageHdrEndY, lineLeft, endY,false);

		lineLeft += QTY_FIELD_WIDTH;
		if(QTY_FIELD_WIDTH>0)
			printLine(lineLeft, mPageHdrEndY, lineLeft, endY,false);

		lineLeft += RATE_FIELD_WIDTH;
		if(RATE_FIELD_WIDTH>0)
			printLine(lineLeft, mPageHdrEndY, lineLeft, endY,false);

		lineLeft += UOM_FIELD_WIDTH;
		if(UOM_FIELD_WIDTH>0)
			printLine(lineLeft, mPageHdrEndY, lineLeft, endY,false);
		
		lineLeft += CASH_DISC_FIELD_WIDTH;
		if(CASH_DISC_FIELD_WIDTH>0)
			printLine(lineLeft, mPageHdrEndY, lineLeft, endY,false);
//		lineLeft += AMOUNT_FIELD_WIDTH + H_GAP;
//		printLine(lineLeft-LINE_GAP , mPageHdrEndY, lineLeft-LINE_GAP, endY,false);
//		
		
		//page footer total field  -- start  horizontal line 
		printLine(0 , endY-PAGE_FOOTER_HEIGHT, getWidth() ,endY-PAGE_FOOTER_HEIGHT,false);
		
		//page  footer total field  -- end  horizontal line 
		printLine(0 , endY, getWidth() , endY ,false);
	 
	}


	/**
	 * Prints the header for details
	 */
	private void printDetailsHeader() {

		setFont(mFontReceipt);
		int left = 0;
 	
		left=1;
		printTextBlock(left+H_GAP, SLNO_FIELD_WIDTH-H_GAP-LINE_GAP, "Sl ", TextAlign.CENTER, false);

		/** The description field **/
		left += SLNO_FIELD_WIDTH;
		printTextBlock(left+H_GAP, DESC_FIELD_WIDTH-H_GAP-LINE_GAP, "Description of Goods", TextAlign.CENTER,	false);

		/** The HSN field **/
		left += DESC_FIELD_WIDTH;
		printTextBlock(left+H_GAP, HSN_CODE_WIDTH-H_GAP-LINE_GAP, "HSN/SAC", TextAlign.CENTER,	false);
		
		/** The Quantity field **/
		left += HSN_CODE_WIDTH;
		printTextBlock(left+H_GAP,QTY_FIELD_WIDTH-H_GAP-LINE_GAP , "Quantity ", TextAlign.CENTER, false);

		
		/** The Rate field **/
		left += QTY_FIELD_WIDTH;
		printTextBlock(left+H_GAP, RATE_FIELD_WIDTH-H_GAP-LINE_GAP, "Rate", TextAlign.CENTER, false);

		/** The uom field **/
		left += RATE_FIELD_WIDTH;
		printTextBlock(left+H_GAP, UOM_FIELD_WIDTH-H_GAP-LINE_GAP, "Per", TextAlign.CENTER,	false);
		
		/** The Disc field **/
		left += UOM_FIELD_WIDTH;
		printTextBlock(left+H_GAP, CASH_DISC_FIELD_WIDTH-H_GAP-LINE_GAP, "Disc", TextAlign.CENTER,	false);
		
		/** The Amount field **/		
		left += CASH_DISC_FIELD_WIDTH;
		printTextBlock(left+H_GAP, AMOUNT_FIELD_WIDTH-H_GAP-LINE_GAP, "Amount ", TextAlign.CENTER, true);


	}

	/**
	 * Prints the details part
	 */
	private void printDetailItem(int srNo, BeanOrderDetail dtl) {

		int left = 0;

		/** The Serial number field **/
		printTextBlock(left+H_GAP, SLNO_FIELD_WIDTH -H_GAP-LINE_GAP, String.valueOf(srNo),
				TextAlign.CENTER, false);

		/** The description field **/
		left += SLNO_FIELD_WIDTH;
		printTextBlock(left+H_GAP, DESC_FIELD_WIDTH-H_GAP-LINE_GAP,PosSaleItemUtil.getItemNameToPrint(dtl.getSaleItem(), mUseAltLanguge) , TextAlign.LEFT, false);

		/** The HSN field **/
		left += DESC_FIELD_WIDTH;
		printTextBlock(left+H_GAP, HSN_CODE_WIDTH-H_GAP-LINE_GAP, dtl.getSaleItem().getHSNCode(), TextAlign.CENTER, false);

		/** The Quantity field **/
		final double qty=PosOrderUtil.getItemQuantity(dtl);
		left += HSN_CODE_WIDTH;
		printTextBlock(left+H_GAP, QTY_FIELD_WIDTH-H_GAP-LINE_GAP,
				PosUomUtil.format(qty,PosUOMProvider.getInstance().getMaxDecUom()) , TextAlign.RIGHT, false);

		page_totalQty += qty;
		/** The Rate field **/
		left += QTY_FIELD_WIDTH;
		

		
		double rate;
		double taxableAmt=0;

		rate=PosOrderUtil.getItemFixedPrice(dtl);
		if(dtl.getSaleItem().getTaxCalculationMethod()==TaxCalculationMethod.InclusiveOfTax){
			
			final double taxRate=PosTaxUtil.getTotalTaxRate(dtl.getSaleItem().getTax());
			taxableAmt=(PosOrderUtil.getItemFixedPrice(dtl)*dtl.getSaleItem().getQuantity())/(1+taxRate/100);
			rate=PosOrderUtil.getItemFixedPrice(dtl)/(1+taxRate/100);
			
		} 
		 printTextBlock(left+H_GAP, RATE_FIELD_WIDTH-H_GAP-LINE_GAP,
				PosCurrencyUtil.format(rate), TextAlign.RIGHT, false);
		
		
		/** The UOM field **/
		left += RATE_FIELD_WIDTH;
		printTextBlock(left+H_GAP, UOM_FIELD_WIDTH-H_GAP-LINE_GAP, dtl.getSaleItem().getUom().getSymbol(), TextAlign.CENTER, false);
		
		/** The DISC % field **/
		left += UOM_FIELD_WIDTH;
		double itemDiscAmt;
		if( dtl.getSaleItem().getTaxCalculationMethod()==TaxCalculationMethod.InclusiveOfTax)
			itemDiscAmt=	PosOrderUtil.getTotalDiscountAmountForExclusiveRate(dtl,rate);
		else
			itemDiscAmt=PosOrderUtil.getTotalDiscountAmount(dtl);
		printTextBlock(left+H_GAP, CASH_DISC_FIELD_WIDTH-H_GAP-LINE_GAP, PosCurrencyUtil.format(itemDiscAmt), TextAlign.CENTER, false);

		/** The total field **/
		left += CASH_DISC_FIELD_WIDTH;
		final double amt= (dtl.getSaleItem().getTaxCalculationMethod()==TaxCalculationMethod.InclusiveOfTax)?(taxableAmt-itemDiscAmt):
			(rate*dtl.getSaleItem().getQuantity()-itemDiscAmt);
		
		printTextBlock(left+H_GAP, AMOUNT_FIELD_WIDTH-H_GAP-LINE_GAP,
				PosCurrencyUtil.format( amt ), TextAlign.RIGHT, true);
		page_totalAmt += PosCurrencyUtil.roundTo(amt);
		
		/** Print modifiers */
		if(PosEnvSettings.getInstance().getPrintSettings().getPaymentReceiptSettings().isModifiersVisible())
			printAttributes(dtl.getSaleItem());
		
		//item remarks 
		left = SLNO_FIELD_WIDTH;
		if(PosEnvSettings.getInstance().getPrintSettings().getPaymentReceiptSettings().showItemRemarks())
			printTextBlock(left+H_GAP,DESC_FIELD_WIDTH-H_GAP-LINE_GAP,dtl.getRemarks(),TextAlign.LEFT,true);
		 
		setTaxSummary(dtl);
		setHSNTaxSummary(dtl);
	}

	/**
	 * @param beanSaleItem
	 */
	private void printAttributes(BeanSaleItem beanSaleItem) {
		
		for(int i=0;i<5;i++){
			if(beanSaleItem.getAttribSelectedOption(i)!=null&&beanSaleItem.getAttribSelectedOption(i).trim().length()!=0&&!beanSaleItem.getAttribSelectedOption(i).trim().equalsIgnoreCase(PosSaleItemProvider.DEFAULT_ATTRIBUTE_OPTION)){
				 printTextBlock(SLNO_FIELD_WIDTH +RATE_FIELD_WIDTH+H_GAP, DESC_FIELD_WIDTH-H_GAP-LINE_GAP,beanSaleItem.getAttributeName(i)+" :- "+beanSaleItem.getAttribSelectedOption(i).trim(), TextAlign.LEFT, true);
			}
		}
	}
	
	
	/**
	 * 
	 */
	protected boolean printTax(){

	    String taxName;	
	    
	    final int textLeft = SLNO_FIELD_WIDTH ;
		final int valueLeft = textLeft + DESC_FIELD_WIDTH + HSN_CODE_WIDTH + 
				QTY_FIELD_WIDTH +RATE_FIELD_WIDTH  +UOM_FIELD_WIDTH + CASH_DISC_FIELD_WIDTH ;
		
		double tax1Amt,tax2Amt,tax3Amt;
		
//	    advanceTextLine(2);
		for(Integer taxId: itemTaxList.keySet()){

			if(itemTaxList.get(taxId).getTaxAmount()>0){

				final BeanTax tax=PosTaxItemProvider.getInstance().getTaxItem(taxId);
				
				taxName=tax.getName() + " "; //+ PosCurrencyUtil.format(PosTaxUtil.getTotalTaxRate(tax));
				
				if(itemTaxList.get(taxId).getTax1Amount()>0){
					
					tax1Amt=itemTaxList.get(taxId).getTax1Amount()-
							PosOrderUtil.getDiscountForPaymentReceipt(mOrder, itemTaxList.get(taxId).getTax1Amount());
							
					
					printTextBlock(textLeft+H_GAP, DESC_FIELD_WIDTH-H_GAP-LINE_GAP, 
							 tax.getTaxOneName() + " " + tax.getTaxOnePercentage()+"%", TextAlign.RIGHT, false);
					 
					printTextBlock(valueLeft+H_GAP, AMOUNT_FIELD_WIDTH-H_GAP-LINE_GAP,
							PosCurrencyUtil.format(tax1Amt), TextAlign.RIGHT, true);
					page_totalAmt+=PosCurrencyUtil.roundTo(tax1Amt);
				}
				
				if(itemTaxList.get(taxId).getTax2Amount()>0){
					
					tax2Amt=itemTaxList.get(taxId).getTax2Amount()-
							PosOrderUtil.getDiscountForPaymentReceipt(mOrder, itemTaxList.get(taxId).getTax2Amount());
					
					printTextBlock(textLeft+H_GAP, DESC_FIELD_WIDTH-H_GAP-LINE_GAP, 
							tax.getTaxTwoName() + " " + tax.getTaxTwoPercentage()+"%", TextAlign.RIGHT, false);
					
					printTextBlock(valueLeft+H_GAP, AMOUNT_FIELD_WIDTH-H_GAP-LINE_GAP,
							PosCurrencyUtil.format(tax2Amt), TextAlign.RIGHT, true);
					page_totalAmt+=PosCurrencyUtil.roundTo(tax2Amt);
			
				} 
				
				if(itemTaxList.get(taxId).getTax3Amount()>0){
					
					tax3Amt=itemTaxList.get(taxId).getTax3Amount()-
							PosOrderUtil.getDiscountForPaymentReceipt(mOrder, itemTaxList.get(taxId).getTax3Amount());
					
					printTextBlock(textLeft+H_GAP, DESC_FIELD_WIDTH-H_GAP-LINE_GAP, 
							tax.getTaxThreeName() + " " + tax.getTaxThreePercentage()+"%", TextAlign.RIGHT, false);
					
					printTextBlock(valueLeft+H_GAP, AMOUNT_FIELD_WIDTH-H_GAP-LINE_GAP,
							PosCurrencyUtil.format(tax3Amt), TextAlign.RIGHT, true);
			
					page_totalAmt+=PosCurrencyUtil.roundTo(tax3Amt);
				}  
					
			}

		}
 
		return true;
	}
	
	/**
	 * @param order
	 * @throws Exception
	 */
	private void printDetailSummary(BeanOrderHeader order) throws Exception {

		final int textLeft = SLNO_FIELD_WIDTH;
		final int valueLeft = textLeft + DESC_FIELD_WIDTH + HSN_CODE_WIDTH +RATE_FIELD_WIDTH + QTY_FIELD_WIDTH +
				UOM_FIELD_WIDTH + CASH_DISC_FIELD_WIDTH ;


		if(!hasMoreData()) {

		    int lineCount=4;
		    
			page_totalAmt+=mOrder.getExtraCharges();
			lineCount+=mOrder.getExtraCharges()>0?1:0;
			
			final double totalDiscAmt=PosOrderUtil.getDiscountForPaymentReceipt(mOrder, page_totalAmt);
			lineCount+=totalDiscAmt>0?4:0;
			
		    for(Integer taxId: itemTaxList.keySet()){

		    	lineCount+=itemTaxList.get(taxId).getTax1Amount()>0?1:0;
		    	lineCount+=itemTaxList.get(taxId).getTax2Amount()>0?1:0;
		    	lineCount+=itemTaxList.get(taxId).getTax3Amount()>0?1:0;
			}
			
		    
//				lineCount+=Double.parseDouble(roundingAdjustmentText)!=0?1:0;
		   
		    if(getNextLineStartY()+getLineHeight()*lineCount>=(REPORT_HEIGHT-PAGE_FOOTER_HEIGHT))
		    	return  ;
			
		    advanceTextLine(2);
		    if (mOrder.getExtraCharges()>0){
				
				printTextBlock(textLeft+H_GAP, DESC_FIELD_WIDTH-H_GAP-LINE_GAP, 
						"Extra Charges", TextAlign.RIGHT, false);
				
				printTextBlock(valueLeft+H_GAP, AMOUNT_FIELD_WIDTH-H_GAP-LINE_GAP,
						PosCurrencyUtil.format(mOrder.getExtraCharges()), TextAlign.RIGHT, true);
			}	
		    if (totalDiscAmt>0 ){
				advanceLine(2);
				printTextBlock(textLeft+H_GAP, DESC_FIELD_WIDTH-H_GAP-LINE_GAP, 
						"Subtotal", TextAlign.RIGHT, false);
				
				printTextBlock(valueLeft+H_GAP, AMOUNT_FIELD_WIDTH-H_GAP-LINE_GAP,
						PosCurrencyUtil.format(page_totalAmt), TextAlign.RIGHT, true);
		
			}
			
			
		    page_totalAmt-=PosCurrencyUtil.roundTo(totalDiscAmt);
			
			
		    
			if (totalDiscAmt>0){
			
				printTextBlock(textLeft+H_GAP, DESC_FIELD_WIDTH-H_GAP-LINE_GAP, 
						PosOrderUtil.getDiscountName(mOrder), TextAlign.RIGHT, false);
				
				printTextBlock(valueLeft+H_GAP, AMOUNT_FIELD_WIDTH-H_GAP-LINE_GAP,
						PosCurrencyUtil.format(totalDiscAmt), TextAlign.RIGHT, true);
		
			}
			printTax();
		    
			
			final double netAmount	 =PosCurrencyUtil.roundTo(order.getTotalAmount()) + 
					mOrder.getExtraCharges()+ PosOrderUtil.getExtraChargeTotalTaxAmount(order)
					+mOrder.getRoundAdjustmentAmount()
					-order.getBillDiscountAmount();
			page_totalAmt += mOrder.getRoundAdjustmentAmount() ;// roundingAdjustment;
			
			String roundingAdjustmentText;
			
			if (netAmount!= page_totalAmt){
				roundingAdjustmentText= PosCurrencyUtil.format(mOrder.getRoundAdjustmentAmount() + (netAmount- page_totalAmt));
				page_totalAmt =netAmount;
			}else
				roundingAdjustmentText= PosCurrencyUtil.format(mOrder.getRoundAdjustmentAmount());
			
			
			if(Double.parseDouble(roundingAdjustmentText)!=0){ 
					
					printTextBlock(textLeft+H_GAP, DESC_FIELD_WIDTH-H_GAP-LINE_GAP, 
							"Round Off", TextAlign.RIGHT, false);
					
					printTextBlock(valueLeft+H_GAP, AMOUNT_FIELD_WIDTH-H_GAP-LINE_GAP,
							roundingAdjustmentText, TextAlign.RIGHT, true);
			}
		
			isPrintDetailSummary=true;
			
		}
			 
			 
	}
	/**
	 * @param order
	 * @throws Exception
	 */
	private void printPageSummary(BeanOrderHeader order) throws Exception {
		  int left;
	 	 
		left =SLNO_FIELD_WIDTH  ;
		 
		printTextBlock(left+H_GAP,  DESC_FIELD_WIDTH-H_GAP-LINE_GAP, "Total : " , TextAlign.RIGHT, false);
		
		left +=DESC_FIELD_WIDTH + HSN_CODE_WIDTH; // RATE_FIELD_WIDTH + DESC_FIELD_WIDTH + QTY_FIELD_WIDTH;
		printTextBlock(left+H_GAP, QTY_FIELD_WIDTH-H_GAP-LINE_GAP,
				PosUomUtil.format(page_totalQty,PosUOMProvider.getInstance().getMaxDecUom()), TextAlign.RIGHT, false);  

		left +=QTY_FIELD_WIDTH + RATE_FIELD_WIDTH + UOM_FIELD_WIDTH + CASH_DISC_FIELD_WIDTH ;
		printTextBlock(left+H_GAP, AMOUNT_FIELD_WIDTH-H_GAP-LINE_GAP,
				PosCurrencyUtil.format( page_totalAmt ), TextAlign.RIGHT, true);  
		advanceLine(1);
 
	}
 
	/**
	 * @throws Exception
	 */
	private void onPrintReportFooter() throws Exception {

 	}
	 
	/* (non-Javadoc)
	 * @see com.indocosmo.pos.reports.receipts.PosPrintableReceiptBase#setOrder(com.indocosmo.pos.data.beans.BeanOrderHeader)
	 */
	public void setOrder(BeanOrderHeader order) {

		this.mOrder=order;
		preparePaymentInfo(mOrder.getOrderPaymentItems());
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

		 
		double tax1Amount=taxSummary.getTax1Amount();
		double tax2Amount=taxSummary.getTax2Amount();
		double tax3Amount=taxSummary.getTax3Amount();
		double taxSCAmount=taxSummary.getTaxSCAmount();
		double taxGSTAmount=taxSummary.getTaxGSTAmount();
		double totalTaxAmo=taxSummary.getTaxAmount();
		double taxableAmo=taxSummary.getTaxableAmount();

		tax1Amount+=item.getSaleItem().getTax().getTaxAmount().getTaxOneAmount();
		taxSummary.setTax1Amount(tax1Amount);
		tax2Amount+=item.getSaleItem().getTax().getTaxAmount().getTaxTwoAmount();
		taxSummary.setTax2Amount(tax2Amount);
		tax3Amount+=item.getSaleItem().getTax().getTaxAmount().getTaxThreeAmount();
		taxSummary.setTax3Amount(tax3Amount);
		taxSCAmount+=item.getSaleItem().getTax().getTaxAmount().getServiceTaxAmount();
		taxSummary.setTaxSCAmount(taxSCAmount);
		taxGSTAmount+=item.getSaleItem().getTax().getTaxAmount().getGSTAmount();
		taxSummary.setTaxGSTAmount(taxGSTAmount);
		
		totalTaxAmo+=PosCurrencyUtil.roundTo(item.getSaleItem().getTax().getTaxAmount().getTotalTaxAmount());
		taxSummary.setTaxAmount(totalTaxAmo);
		taxableAmo+=item.getSaleItem().getTax().getTaxAmount().getTaxableAmount();
		taxSummary.setTaxableAmount(taxableAmo);

	}
	/**
	 * @param item
	 */
	protected void setHSNTaxSummary(BeanOrderDetail item){

		if(item.getSaleItem().getTax()==null) return;

//		if(item.getSaleItem().getTaxCalculationMethod() == TaxCalculationMethod.InclusiveOfTax) return;

		final BeanTax itemTax=item.getSaleItem().getTax();
		BeanReceiptTaxSummary taxSummary=null;
  
		final String key=(item.getSaleItem().getHSNCode()==null?"":item.getSaleItem().getHSNCode()) + "/" + itemTax.getCode();
		
		if(itemHSNTaxList.containsKey(key))

			taxSummary=itemHSNTaxList.get(key);

		else{

			taxSummary=new BeanReceiptTaxSummary();
			itemHSNTaxList.put(key, taxSummary);
			taxSummary.setTaxOnePercentage(itemTax.getTaxOnePercentage());
			taxSummary.setTaxTwoPercentage(itemTax.getTaxTwoPercentage());
			taxSummary.setTaxThreePercentage(itemTax.getTaxThreePercentage());
			taxSummary.setServiceTaxPercentage(itemTax.getServiceTaxPercentage()); 
			taxSummary.setGSTPercentage(itemTax.getGSTPercentage());
			
		}
		double tax1Amount=taxSummary.getTax1Amount();
		double tax2Amount=taxSummary.getTax2Amount();
		double tax3Amount=taxSummary.getTax3Amount();
		double taxSCAmount=taxSummary.getTaxSCAmount();
		double taxGSTAmount=taxSummary.getTaxGSTAmount();
		double totalTaxAmo=taxSummary.getTaxAmount();
		double taxableAmo=taxSummary.getTaxableAmount();

		tax1Amount+=item.getSaleItem().getTax().getTaxAmount().getTaxOneAmount();
		taxSummary.setTax1Amount(tax1Amount);
		tax2Amount+=item.getSaleItem().getTax().getTaxAmount().getTaxTwoAmount();
		taxSummary.setTax2Amount(tax2Amount);
		tax3Amount+=item.getSaleItem().getTax().getTaxAmount().getTaxThreeAmount();
		taxSummary.setTax3Amount(tax3Amount);
		taxSCAmount+=item.getSaleItem().getTax().getTaxAmount().getServiceTaxAmount();
		taxSummary.setTaxSCAmount(taxSCAmount);
		taxGSTAmount+=item.getSaleItem().getTax().getTaxAmount().getGSTAmount();
		taxSummary.setTaxGSTAmount(taxGSTAmount);
		
		totalTaxAmo+=PosCurrencyUtil.roundTo(item.getSaleItem().getTax().getTaxAmount().getTotalTaxAmount());
		taxSummary.setTaxAmount(totalTaxAmo);
		taxableAmo+=item.getSaleItem().getTax().getTaxAmount().getTaxableAmount();
		taxSummary.setTaxableAmount(taxableAmo);

	}
	 
	
	/**
	 * @param item
	 */
	protected void setExtraChargeHSNTaxSummary(){

		
		if(mOrder.getExtraChargeTaxCode()==null || mOrder.getExtraChargeTaxCode().equals(PosTaxItemProvider.NO_TAXCODE) 
				|| mOrder.getExtraChargeTaxCode().equals(""))
			return;
		
		if(mOrder.getExtraCharges()<=0)
			return;
		
	 	BeanReceiptTaxSummary taxSummary=null;
  
		final String key="" + "/" + mOrder.getExtraChargeTaxCode();
		
		if(itemHSNTaxList.containsKey(key))

			taxSummary=itemHSNTaxList.get(key);

		else{

			taxSummary=new BeanReceiptTaxSummary();
			itemHSNTaxList.put(key, taxSummary);
			taxSummary.setTaxOnePercentage(mOrder.getExtraChargeTaxOnePercentage());
			taxSummary.setTaxTwoPercentage(mOrder.getExtraChargeTaxTwoPercentage());
			taxSummary.setTaxThreePercentage(mOrder.getExtraChargeTaxThreePercentage());
			taxSummary.setGSTPercentage(mOrder.getExtraChargeGSTPercentage());
			
		}
		
		
		double tax1Amount=taxSummary.getTax1Amount();
		double tax2Amount=taxSummary.getTax2Amount();
		double tax3Amount=taxSummary.getTax3Amount();
		double taxGSTAmount=taxSummary.getTaxGSTAmount();
		double totalTaxAmo=taxSummary.getTaxAmount();
		double taxableAmo=taxSummary.getTaxableAmount();
		
		tax1Amount+=mOrder.getExtraChargeTaxOneAmount(); 
		taxSummary.setTax1Amount(tax1Amount);
		tax2Amount+=mOrder.getExtraChargeTaxTwoAmount(); 
		taxSummary.setTax2Amount(tax2Amount);
		tax3Amount+=mOrder.getExtraChargeTaxThreeAmount();
		taxSummary.setTax3Amount(tax3Amount);
		taxGSTAmount+=mOrder.getExtraChargeGSTAmount();  
		taxSummary.setTaxGSTAmount(taxGSTAmount);
		
		 
		totalTaxAmo+=PosOrderUtil.getExtraChargeTotalTaxAmount(mOrder); 
		taxSummary.setTaxAmount(totalTaxAmo);
		taxableAmo+=mOrder.getExtraCharges(); 
		taxSummary.setTaxableAmount(taxableAmo);
 

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
			
			if(!payment.isRepayment()){
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

	}

	/**
	 * 
	 */
	
	private void printReportSummary(){
 
		printTextBlock(START_INDEX, getWidth(), "Amount Chargeable (in words)" , TextAlign.LEFT,false,true);
		printText(TextAlign.RIGHT, "E. & O.E ",true,true);
		setFontStyle(Font.BOLD);
		printMoneyInText(PosCurrencyUtil.format(page_totalAmt));
		setFont(mFontReceipt);
//		printSingleLine();
		printTaxSummary();
		
		advanceTextLine(3);
		final int width=getWidth()/2;
		
		final int signatoryTop=getNextLineStartY();
		
		final String panNo= "Company's PAN        :" + (PosEnvSettings.getInstance().getShop().getCompanyTaxNo()==null?"": PosEnvSettings.getInstance().getShop().getCompanyTaxNo());
		printTextBlock(START_INDEX, getWidth(), panNo  , TextAlign.LEFT,true,true);
		
		printTextBlock(START_INDEX, width, "Declaration" , TextAlign.LEFT,false,true);
		printTextBlock(width, width-LINE_GAP , "For " + PosEnvSettings.getInstance().getShop().getName() , TextAlign.RIGHT,true,true);
		
		printTextBlock(START_INDEX, width, "We declare that this invoice shows the actual price of the " , TextAlign.LEFT,true,true);
		printTextBlock(START_INDEX, width, "goods described and that all particulars are true and correct." , TextAlign.LEFT,true,true);
		printTextBlock(START_INDEX, getWidth()-LINE_GAP, "Authorised Signatory" , TextAlign.RIGHT,true,true);
		
		printLine(width, signatoryTop, getWidth(), signatoryTop);
		printLine(width, signatoryTop, width, getHeight());
		
		isPrintDetailSummary=false;
	}
	
	/* (non-Javadoc)
	 * @see com.indocosmo.pos.reports.base.PosPrintableBase#printMoneyInText(double)
	 */
	@Override
	protected void printMoneyInText(String amt){

		String amoInWords = MoneyToWordsConvertor.toWords(amt);
		String[] amolines = PosStringUtil.getWrappedLines(amoInWords,getGraphics(),getPosReportPageFormat().getImageableWidthInPixcel());

		for (String amoLine : amolines) {
			printTextBlock(START_INDEX, getWidth(), amoLine , TextAlign.LEFT,true,true);
		}
	}
	 
	/**
	 * 
	 */
	protected boolean printTaxSummary(){
		
		setFont(mFontReceipt);
		int left = 0;
 	
		left=START_INDEX;
		final int startY=getNextLineStartY();
		final int taxWidth=TAX_RATE_FIELD_WIDTH*3 + TAX_AMOUNT_FIELD_WIDTH*3;
		final int hsnFieldWidth=getPosReportPageFormat().getImageableWidthInPixcel()-AMOUNT_FIELD_WIDTH*2-taxWidth;
		printTextBlock(left+H_GAP, hsnFieldWidth-H_GAP-LINE_GAP, "HSN/SAC ", TextAlign.CENTER, false);

		
		left +=hsnFieldWidth ;
		printTextBlock(left+H_GAP, AMOUNT_FIELD_WIDTH-H_GAP-LINE_GAP, "Taxable", TextAlign.CENTER,	false);

		left += AMOUNT_FIELD_WIDTH;
		printTextBlock(left+H_GAP, taxWidth-H_GAP-LINE_GAP, "Integrated Tax", TextAlign.CENTER,	false);

		left +=taxWidth;
		printTextBlock(left+H_GAP, AMOUNT_FIELD_WIDTH-H_GAP-LINE_GAP, "Total", TextAlign.CENTER,	true);

		final int startSubTitleY=getNextLineStartY();
		
		
		left=1;
		
		left += hsnFieldWidth;
		printTextBlock(left+H_GAP, AMOUNT_FIELD_WIDTH-H_GAP-LINE_GAP, "Value", TextAlign.CENTER,	false);

		left += AMOUNT_FIELD_WIDTH;
		printTextBlock(left+H_GAP, TAX_RATE_FIELD_WIDTH-H_GAP-LINE_GAP, "Rate", TextAlign.CENTER,	false);

		left += TAX_RATE_FIELD_WIDTH;
		printTextBlock(left+H_GAP, TAX_AMOUNT_FIELD_WIDTH-H_GAP-LINE_GAP, "Amount", TextAlign.CENTER,	false);

		left += TAX_AMOUNT_FIELD_WIDTH;
		printTextBlock(left+H_GAP, TAX_RATE_FIELD_WIDTH-H_GAP-LINE_GAP, "Rate", TextAlign.CENTER,	false);

		left += TAX_RATE_FIELD_WIDTH;
		printTextBlock(left+H_GAP, TAX_AMOUNT_FIELD_WIDTH-H_GAP-LINE_GAP, "Amount", TextAlign.CENTER,	false);

		left += TAX_AMOUNT_FIELD_WIDTH;
		printTextBlock(left+H_GAP, TAX_RATE_FIELD_WIDTH-H_GAP-LINE_GAP, "Rate", TextAlign.CENTER,	false);

		left += TAX_RATE_FIELD_WIDTH;
		printTextBlock(left+H_GAP, TAX_AMOUNT_FIELD_WIDTH-H_GAP-LINE_GAP, "Amount", TextAlign.CENTER,	false);

		
		left += TAX_AMOUNT_FIELD_WIDTH;
		printTextBlock(left+H_GAP, AMOUNT_FIELD_WIDTH-H_GAP-LINE_GAP, "Tax Amount", TextAlign.CENTER,	true);
  
	   printSingleLine();
	   
		double billDiscountPercentage=mOrder.getBillDiscountPercentage();
		
		if(billDiscountPercentage==0 && 
				(mOrder.getStatus()!=PosOrderStatus.Closed && mOrder.getStatus()!=PosOrderStatus.Refunded) && 
				mOrder.getPreBillDiscount()!=null &&
						mOrder.getPreBillDiscount()!=new PosDiscountItemProvider().getNoneDiscount()){
		
			if(!mOrder.getPreBillDiscount().isPercentage()){
				billDiscountPercentage=mOrder.getPreBillDiscount().getPrice() *100 /PosOrderUtil.getTotalAmount(mOrder);
			}else
				billDiscountPercentage=mOrder.getPreBillDiscount().getPrice();
		}
		
		
	   int lineCount=0;
	   double totalTaxableAmt=0;
	   double totalTax1Amt=0;
	   double totalTax2Amt=0;
	   double totalTax3Amt=0;
	   double totalTaxAmt=0;
	   
	   double taxableAmt=0;
	   double tax1Amt=0;
	   double tax2Amt=0;
	   double tax3Amt=0;
	   double taxAmt=0;
		for(String key: itemHSNTaxList.keySet()){
			BeanReceiptTaxSummary taxSummary=itemHSNTaxList.get(key);
			if(taxSummary.getTaxAmount()>0){

				left=START_INDEX;
				
				taxableAmt=taxSummary.getTaxableAmount()-taxSummary.getTaxableAmount()*billDiscountPercentage/100;
				tax1Amt=taxSummary.getTax1Amount()-taxSummary.getTax1Amount()*billDiscountPercentage/100;
				tax2Amt=taxSummary.getTax2Amount()-taxSummary.getTax2Amount()*billDiscountPercentage/100;
				tax3Amt=taxSummary.getTax3Amount()-taxSummary.getTax3Amount()*billDiscountPercentage/100;
				taxAmt=taxSummary.getTaxAmount()-taxSummary.getTaxAmount()*billDiscountPercentage/100;
				
				final String splits[]=key.split("/");
				final String hsnCode=splits.length>1?splits[0]:"";
				printTextBlock(left+H_GAP,hsnFieldWidth-H_GAP-LINE_GAP, hsnCode , TextAlign.LEFT, false);
				
				
				left += hsnFieldWidth ;
				printTextBlock(left+H_GAP, AMOUNT_FIELD_WIDTH-H_GAP-LINE_GAP,PosCurrencyUtil.format(taxableAmt), TextAlign.RIGHT,	false);

				left += AMOUNT_FIELD_WIDTH;
				printTextBlock(left+H_GAP, TAX_RATE_FIELD_WIDTH-H_GAP-LINE_GAP,PosNumberUtil.format(taxSummary.getTaxOnePercentage())  , TextAlign.RIGHT,	false);

				left += TAX_RATE_FIELD_WIDTH;
				printTextBlock(left+H_GAP, TAX_AMOUNT_FIELD_WIDTH-H_GAP-LINE_GAP,PosCurrencyUtil.format(tax1Amt)  , TextAlign.RIGHT,	false);

				left += TAX_AMOUNT_FIELD_WIDTH;
				printTextBlock(left+H_GAP, TAX_RATE_FIELD_WIDTH-H_GAP-LINE_GAP,PosNumberUtil.format(taxSummary.getTaxTwoPercentage())  , TextAlign.RIGHT,	false);

				left += TAX_RATE_FIELD_WIDTH;
				printTextBlock(left+H_GAP, TAX_AMOUNT_FIELD_WIDTH-H_GAP-LINE_GAP,PosCurrencyUtil.format(tax2Amt)  , TextAlign.RIGHT,	false);

				left += TAX_AMOUNT_FIELD_WIDTH;
				printTextBlock(left+H_GAP, TAX_RATE_FIELD_WIDTH-H_GAP-LINE_GAP,PosNumberUtil.format(taxSummary.getTaxThreePercentage())  , TextAlign.RIGHT,	false);

				left += TAX_RATE_FIELD_WIDTH;
				printTextBlock(left+H_GAP, TAX_AMOUNT_FIELD_WIDTH-H_GAP-LINE_GAP,PosCurrencyUtil.format(tax3Amt)  , TextAlign.RIGHT,	false);

				
				left += TAX_AMOUNT_FIELD_WIDTH;
				printTextBlock(left+H_GAP, AMOUNT_FIELD_WIDTH-H_GAP-LINE_GAP,PosCurrencyUtil.format(taxAmt)  , TextAlign.RIGHT,	true);

					
				totalTaxableAmt+=PosCurrencyUtil.roundTo(taxableAmt);
				totalTax1Amt+=PosCurrencyUtil.roundTo(tax1Amt); 
				totalTax2Amt+=PosCurrencyUtil.roundTo(tax2Amt);
				totalTax3Amt+=PosCurrencyUtil.roundTo(tax3Amt); 
				totalTaxAmt+=PosCurrencyUtil.roundTo(taxAmt); 
				lineCount++;
			}

		}
		if(lineCount==0)
			advanceTextLine(1);
			
		advanceLine(2);
		left=START_INDEX;
		printSingleLine();
		printTextBlock(left+H_GAP,hsnFieldWidth-H_GAP-LINE_GAP, "Total" , TextAlign.LEFT, false);

		
		left += hsnFieldWidth ;
		printTextBlock(left+H_GAP, AMOUNT_FIELD_WIDTH-H_GAP-LINE_GAP,PosCurrencyUtil.format(totalTaxableAmt), TextAlign.RIGHT,	false);

		left += AMOUNT_FIELD_WIDTH;
		left += TAX_RATE_FIELD_WIDTH;
		printTextBlock(left+H_GAP, TAX_AMOUNT_FIELD_WIDTH-H_GAP-LINE_GAP,PosCurrencyUtil.format(totalTax1Amt)  , TextAlign.RIGHT,	false);

		left += TAX_AMOUNT_FIELD_WIDTH;
		left += TAX_RATE_FIELD_WIDTH;
		printTextBlock(left+H_GAP, TAX_AMOUNT_FIELD_WIDTH-H_GAP-LINE_GAP,PosCurrencyUtil.format(totalTax2Amt)  , TextAlign.RIGHT,	false);

		left += TAX_AMOUNT_FIELD_WIDTH;
		left += TAX_RATE_FIELD_WIDTH;
		printTextBlock(left+H_GAP, TAX_AMOUNT_FIELD_WIDTH-H_GAP-LINE_GAP,PosCurrencyUtil.format(totalTax3Amt)  , TextAlign.RIGHT,	false);

		left += TAX_AMOUNT_FIELD_WIDTH;
		printTextBlock(left+H_GAP, AMOUNT_FIELD_WIDTH-H_GAP-LINE_GAP,PosCurrencyUtil.format(totalTaxAmt)  , TextAlign.RIGHT,	true);

		
		final int endY=getNextLineStartY();
		
		//horizontal lines 
		printLine(0, startY, getWidth(), startY);
		printLine(0, endY, getWidth(), endY);
		printLine(hsnFieldWidth, startY, hsnFieldWidth, endY);
		left=hsnFieldWidth+AMOUNT_FIELD_WIDTH;
		printLine(left, startY, left, endY);
		left+=taxWidth;
		printLine(left, startY, left, endY);
		
		//vertical lines 
		left=hsnFieldWidth+AMOUNT_FIELD_WIDTH;
		printLine(left, startSubTitleY,left+taxWidth, startSubTitleY);
		left+=TAX_RATE_FIELD_WIDTH ;
		printLine(left, startSubTitleY, left, endY);
		left+=TAX_AMOUNT_FIELD_WIDTH ;
		printLine(left, startSubTitleY, left, endY);
		left+=TAX_RATE_FIELD_WIDTH ;
		printLine(left, startSubTitleY, left, endY);
		left+=TAX_AMOUNT_FIELD_WIDTH ;
		printLine(left, startSubTitleY, left, endY);
		left+=TAX_RATE_FIELD_WIDTH ;
		printLine(left, startSubTitleY, left, endY);
		
		setNextLineStartY(endY);
		printTextBlock(START_INDEX, getWidth(), "Tax Amount (in words)" , TextAlign.LEFT,true,true);
		setFontStyle(Font.BOLD);
		printMoneyInText(PosCurrencyUtil.format(totalTaxAmt));
		setFont(mFontReceipt);
		
		return true;
	}
	
	 
	
}
