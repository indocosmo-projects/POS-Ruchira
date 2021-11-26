/**
 * 
 */
package com.indocosmo.pos.reports.receipts;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;

import com.indocosmo.pos.common.PosEnvSettings;
import com.indocosmo.pos.common.beans.BeanReceiptTaxSummary;
import com.indocosmo.pos.common.enums.PaymentReceiptType;
import com.indocosmo.pos.common.enums.device.PrinterType;
import com.indocosmo.pos.common.utilities.PosCurrencyUtil;
import com.indocosmo.pos.common.utilities.PosDateUtil;
import com.indocosmo.pos.common.utilities.PosFormUtil;
import com.indocosmo.pos.common.utilities.PosOrderUtil;
import com.indocosmo.pos.common.utilities.PosSaleItemUtil;
import com.indocosmo.pos.common.utilities.PosTaxUtil;
import com.indocosmo.pos.common.utilities.PosUomUtil;
import com.indocosmo.pos.data.beans.BeanBillParam;
import com.indocosmo.pos.data.beans.BeanDiscount;
import com.indocosmo.pos.data.beans.BeanOrderDetail;
import com.indocosmo.pos.data.beans.BeanOrderHeader;
import com.indocosmo.pos.data.beans.BeanOrderHeader.PosOrderStatus;
import com.indocosmo.pos.data.beans.BeanSaleItem;
import com.indocosmo.pos.data.beans.BeanShop;
import com.indocosmo.pos.data.beans.BeanTax;
import com.indocosmo.pos.data.providers.shopdb.PosBillParamProvider;
import com.indocosmo.pos.data.providers.shopdb.PosDiscountItemProvider;
import com.indocosmo.pos.data.providers.shopdb.PosPromotionItemProvider;
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
public class PosSalesOrderReceipt extends PosPrintableReportBase {
	

	/**
	 * Serial Number column width
	 */
	
	private static final int REPORT_FOOTER_HEIGHT=100;

	private int REPORT_HEIGHT;
	private int SLNO_FIELD_WIDTH=20; 
	private int RATE_FIELD_WIDTH=50;
	private int QTY_FIELD_WIDTH=50;
	private int AMOUNT_FIELD_WIDTH=80;
	private int HSNCODE_FIELD_WIDTH=50;
	private int UOM_FIELD_WIDTH=50;
	
	private int FOOTER_DISCOUNT_TITLE_WIDTH=120; 
	private int FOOTER_DISCOUNT_VALUE_WIDTH=50;
	
	private int FOOTER_DUE_DATE_FIELD_WIDTH=70; 
	private int FOOTER_DUE_DAY_FIELD_WIDTH=60;
	private int FOOTER_DUE_TIME_FIELD_WIDTH=60;
	private int FOOTER_OT_NAME_FIELD_WIDTH=80;
	private int FOOTER_CUSTOMER_SIGN_FIELD_WIDTH=0;
	private int FOOTER_ADVANCE_TEXT_FIELD_WIDTH=80;
	private int FOOTER_ADVANCE_VALUE_FIELD_WIDTH=80;
	
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
	private HashMap<Integer, BeanReceiptTaxSummary> mReportitemTaxList;
	private HashMap<Integer, BeanReceiptTaxSummary> mPageitemTaxList;
	
	
	private double temp=0;
	/**
	 * @throws IOException 
	 * 
	 */
	public PosSalesOrderReceipt(BeanOrderHeader order) throws IOException {
		super();

		setOrder(order);
		
	}

	/**
	 * mOrderHeaderActual
	 */
	public PosSalesOrderReceipt() {

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

		DESC_FIELD_WIDTH=getPosReportPageFormat().getImageableWidthInPixcel()-(

				SLNO_FIELD_WIDTH+
			 	QTY_FIELD_WIDTH+
				RATE_FIELD_WIDTH+
				AMOUNT_FIELD_WIDTH + 
				HSNCODE_FIELD_WIDTH + 
				UOM_FIELD_WIDTH
				) ;

		FOOTER_CUSTOMER_SIGN_FIELD_WIDTH=getPosReportPageFormat().getImageableWidthInPixcel()-(

				FOOTER_DUE_DATE_FIELD_WIDTH+
			 	FOOTER_DUE_TIME_FIELD_WIDTH+
				FOOTER_DUE_DAY_FIELD_WIDTH+
				FOOTER_OT_NAME_FIELD_WIDTH + 
				FOOTER_ADVANCE_TEXT_FIELD_WIDTH +
				FOOTER_ADVANCE_VALUE_FIELD_WIDTH 
				) ;
 		
		loadBillParams();		
		REPORT_HEIGHT=getHeight()/2;
		
		
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
		if(mPageitemTaxList!=null && mPageitemTaxList.size()>0)
			mReportitemTaxList= new HashMap<Integer, BeanReceiptTaxSummary> (mPageitemTaxList);
		else
			mReportitemTaxList=new HashMap<Integer, BeanReceiptTaxSummary> ();
//		mReportitemTaxList=new HashMap<Integer, BeanReceiptTaxSummary> ();
//		if(mPageitemTaxList!=null && mPageitemTaxList.size()>0)
//			mReportitemTaxList.putAll(mPageitemTaxList);
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
		if(mReportitemTaxList!=null && mReportitemTaxList.size()>0)
			mPageitemTaxList=new HashMap<Integer, BeanReceiptTaxSummary>(mReportitemTaxList);
		else
			mPageitemTaxList=new HashMap<Integer, BeanReceiptTaxSummary>();
		
//		mPageitemTaxList=new HashMap<Integer, BeanReceiptTaxSummary>();
//		if(mReportitemTaxList!=null && mReportitemTaxList.size()>0)
//			mPageitemTaxList.putAll(mReportitemTaxList);
		isEndOfReport=false;
		
		temp=0;

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
		
		printHeader(mOrder, mBillParam);
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

		printDetails(mOrder);

	}

	/* (non-Javadoc)
	 * @see com.indocosmo.pos.reports.base.PosPrintableReportBase#printPageFooter(int)
	 */
	@Override
	protected void printPageFooter(int pageIndex) throws Exception {

		int foorterStartY=getNextLineStartY();
		

		printPageSummary(mOrder);
		printDetailLayout();

	}


	/* (non-Javadoc)
	 * @see com.indocosmo.pos.reports.base.PosPrintableReportBase#printReportFooter(int)
	 */
	@Override
	protected void printReportFooter(int pageIndex) throws Exception {


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
		printText(TextAlign.CENTER, "Sales Order");
 
		mPageHdrStartY=getNextLineStartY();
//		printSingleLine();
		 
		setFont(mFontReceipt); 
		BeanShop shop=PosEnvSettings.getInstance().getShop();
		
		PAGE_HDR_DATA_WIDTH=getWidth()/3-HDR_GAP;
		final int orderTitleLeft=START_INDEX + PAGE_HDR_DATA_WIDTH + HDR_GAP ;
		final int orderTitleWidth=75;
		final int orderSeparatorLeft= orderTitleLeft + orderTitleWidth;
		final int orderDataLeft=orderSeparatorLeft+HDR_GAP;
		final int customerTitleLeft=orderTitleLeft + PAGE_HDR_DATA_WIDTH + HDR_GAP;
		
		advanceLine();
		int shopNameStartY=getNextLineStartY();
		
		printTextBlock(START_INDEX, PAGE_HDR_DATA_WIDTH, shop.getName(), TextAlign.LEFT,true,true);
		printTextBlock(START_INDEX, PAGE_HDR_DATA_WIDTH, shop.getAddress(), TextAlign.LEFT,true,true);

		final String phNo=(shop.getPhoneNumber()!=null && !shop.getPhoneNumber().trim().equals(""))?"Ph:" + shop.getPhoneNumber():"";
		printTextBlock(START_INDEX, PAGE_HDR_DATA_WIDTH, phNo, TextAlign.LEFT,true,true);

		final String gsTin= "GSTIN/UIN: " + ((shop.getCompanyTaxNo() !=null && !shop.getCompanyTaxNo().trim().equals(""))?  shop.getCompanyTaxNo():"");
		printTextBlock(START_INDEX, PAGE_HDR_DATA_WIDTH, gsTin, TextAlign.LEFT,true,true);
		advanceLine();
		mPageHdrEndY=getNextLineStartY();
		
		setNextLineStartY(shopNameStartY);
//		final String refNo=(PosEnvSettings.getInstance().getUISetting().useOrderQueueNo()?PosOrderUtil.getFormatedOrderQueueNo(order):
//			PosOrderUtil.getShortOrderIDFromOrderID(order.getOrderId()));
			
		final String refNo= PosOrderUtil.getShortOrderIDFromOrderID(order.getOrderId());
		printTextBlock(orderTitleLeft,orderTitleWidth, "Order No. ", TextAlign.LEFT,false,true);
		printTextBlock(orderSeparatorLeft,HDR_GAP, ":  ", TextAlign.LEFT,false,true);
		printTextBlock(orderDataLeft, PAGE_HDR_DATA_WIDTH-orderTitleWidth -HDR_GAP, refNo, TextAlign.LEFT,true,true);
		
		printTextBlock(orderTitleLeft,orderTitleWidth, "Date ", TextAlign.LEFT,false,true);
		printTextBlock(orderSeparatorLeft,HDR_GAP, ":  ", TextAlign.LEFT,false,true);
		printTextBlock(orderDataLeft, PAGE_HDR_DATA_WIDTH-orderTitleWidth -HDR_GAP,PosDateUtil.formatLocalDateTime(order.getOrderTime()), TextAlign.LEFT,true,true);
		
		
		printTextBlock(orderTitleLeft,orderTitleWidth, "Order By ", TextAlign.LEFT,false,true);
		printTextBlock(orderSeparatorLeft,HDR_GAP, ":  ", TextAlign.LEFT,false,true);
		printTextBlock(orderDataLeft,PAGE_HDR_DATA_WIDTH-orderTitleWidth -HDR_GAP , mOrder.getOrderByMedium().getDisplayText(), TextAlign.LEFT,true,true);
		
		
		printTextBlock(orderTitleLeft,orderTitleWidth, "Delivery ", TextAlign.LEFT,false,true);
		printTextBlock(orderSeparatorLeft,HDR_GAP, ":  ", TextAlign.LEFT,false,true);
		printTextBlock(orderDataLeft,PAGE_HDR_DATA_WIDTH-orderTitleWidth -HDR_GAP , mOrder.getDeliveryType().getDisplayText(), TextAlign.LEFT,true,true);
	
		setNextLineStartY(shopNameStartY);
		printTextBlock(customerTitleLeft, PAGE_HDR_DATA_WIDTH, "Name & Address", TextAlign.LEFT,true,true);
		printTextBlock(customerTitleLeft, PAGE_HDR_DATA_WIDTH,mOrder.getOrderCustomer().getName(), TextAlign.LEFT,true,true);
		printTextBlock(customerTitleLeft, PAGE_HDR_DATA_WIDTH,mOrder.getOrderCustomer().getAddress(), TextAlign.LEFT,true,false);
		
		final String custTelephone= "Ph: " + ((mOrder.getOrderCustomer().getPhoneNumber() !=null && !mOrder.getOrderCustomer().getPhoneNumber().trim().equals(""))?  mOrder.getOrderCustomer().getPhoneNumber():"");

		printTextBlock(customerTitleLeft, PAGE_HDR_DATA_WIDTH,custTelephone, TextAlign.LEFT,true,true);
		
		
		final String customerTin= "GSTIN/UIN: " + ((mOrder.getOrderCustomer().getTinNo() !=null && !mOrder.getOrderCustomer().getTinNo().trim().equals(""))?  mOrder.getOrderCustomer().getTinNo():"");
		printTextBlock(customerTitleLeft, PAGE_HDR_DATA_WIDTH,customerTin, TextAlign.LEFT,true,true);
		
		mPageHdrEndY=mPageHdrEndY<getNextLineStartY()?getNextLineStartY():mPageHdrEndY;
		
	setNextLineStartY(mPageHdrEndY);
	
		 

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

			if(getNextLineStartY()+getLineHeight()>=(REPORT_HEIGHT-REPORT_FOOTER_HEIGHT))
				break;

		}
		PosOrderUtil.setExtraChargeTaxSummary(order,mPageitemTaxList);
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


	private void printDetailLayout() throws Exception{

		int lineLeft=0;
		
		final int endY=REPORT_HEIGHT-REPORT_FOOTER_HEIGHT + getLineHeight();
		// left border 
		printLine(0 , mPageHdrStartY, 0 , endY + getLineHeight()*2,false);
		
		// right border 
		printLine(getWidth() , mPageHdrStartY, getWidth() , endY + getLineHeight()*2,false);
		
		
		//page header horizontal line 
		printLine(0 , mPageHdrStartY, getWidth() , mPageHdrStartY ,false);
//		
//		//page footer horizontal line 
//		printLine(0 , mPageHdrEndY, getWidth() , mPageHdrEndY ,false);
//		
		// page header separation vertical line  
		printLine(PAGE_HDR_DATA_WIDTH + H_GAP , mPageHdrStartY, PAGE_HDR_DATA_WIDTH + H_GAP  , mPageHdrEndY ,false);
		printLine((PAGE_HDR_DATA_WIDTH + H_GAP )*2, mPageHdrStartY,(PAGE_HDR_DATA_WIDTH + H_GAP )*2, mPageHdrEndY ,false);
				
		
		lineLeft= SLNO_FIELD_WIDTH;
		printLine(lineLeft, mPageHdrEndY, lineLeft, endY,false);

		lineLeft += HSNCODE_FIELD_WIDTH;
		if(HSNCODE_FIELD_WIDTH>0)
			printLine(lineLeft, mPageHdrEndY, lineLeft, endY,false);
		
		lineLeft += DESC_FIELD_WIDTH;
		printLine(lineLeft, mPageHdrEndY, lineLeft, endY,false);

		lineLeft += RATE_FIELD_WIDTH;
		printLine(lineLeft, mPageHdrEndY, lineLeft, endY,false);
		
		lineLeft += QTY_FIELD_WIDTH;
		if(QTY_FIELD_WIDTH>0)
			printLine(lineLeft, mPageHdrEndY, lineLeft, endY,false);
		
		
		lineLeft += UOM_FIELD_WIDTH;
		if(UOM_FIELD_WIDTH>0)
			printLine(lineLeft, mPageHdrEndY, lineLeft, endY,false);

//		lineLeft += AMOUNT_FIELD_WIDTH + H_GAP;
//		printLine(lineLeft-LINE_GAP , mPageHdrEndY, lineLeft-LINE_GAP, endY,false);
//		
		
		//footer total field  -- start  horizontal line 
		printLine(0 , REPORT_HEIGHT-REPORT_FOOTER_HEIGHT, getWidth() , REPORT_HEIGHT-REPORT_FOOTER_HEIGHT ,false);
		
		//footer total field  -- end  horizontal line 
		printLine(0 , endY, getWidth() , endY ,false);
		
		//delivery details  -- start  horizontal line 
		printLine(0 , endY + getLineHeight(), getWidth() ,  endY + getLineHeight(),false);
		
		//delivery details  -- end  horizontal line 
		printLine(0 , endY + getLineHeight()*2, getWidth() ,  endY + getLineHeight()*2,false);
						 
//		DELIVERY Detail section
//		========================
//		due date 
		lineLeft=FOOTER_DUE_DATE_FIELD_WIDTH;
		printLine(lineLeft , endY , lineLeft ,  endY + getLineHeight()*2,false);
		 
//		due day
		lineLeft+=FOOTER_DUE_DAY_FIELD_WIDTH;
		printLine(lineLeft , endY , lineLeft ,  endY + getLineHeight()*2,false);

//		due time 
		lineLeft+=FOOTER_DUE_TIME_FIELD_WIDTH;
		printLine(lineLeft , endY , lineLeft ,  endY + getLineHeight()*2,false);

//		ot name 
		lineLeft+=FOOTER_OT_NAME_FIELD_WIDTH;
		printLine(lineLeft , endY , lineLeft ,  endY + getLineHeight()*2,false);

//		customer signature
		lineLeft+=FOOTER_CUSTOMER_SIGN_FIELD_WIDTH;
		printLine(lineLeft , endY , lineLeft ,  endY + getLineHeight()*2,false);

//		advance and balance
		lineLeft+=FOOTER_ADVANCE_TEXT_FIELD_WIDTH;
		printLine(lineLeft , endY , lineLeft ,  endY + getLineHeight()*2,false);
			
	}


	/**
	 * Prints the header for details
	 */
	private void printDetailsHeader() {

		setFont(mFontReceipt);
		int left = 0;
 	
		left=1;
		printTextBlock(left+H_GAP, SLNO_FIELD_WIDTH-H_GAP-LINE_GAP, "Sl ", TextAlign.CENTER, false);

		/** The HSN Code field **/
		left += SLNO_FIELD_WIDTH;
		printTextBlock(left+H_GAP, HSNCODE_FIELD_WIDTH-H_GAP-LINE_GAP, "HSN/SAC", TextAlign.CENTER, false);

		
		/** The Description field **/
		left += HSNCODE_FIELD_WIDTH;
		printTextBlock(left+H_GAP, DESC_FIELD_WIDTH-H_GAP-LINE_GAP, "Description", TextAlign.LEFT,	false);

		/** The Rate field **/
		left += DESC_FIELD_WIDTH;
		printTextBlock(left+H_GAP, RATE_FIELD_WIDTH-H_GAP-LINE_GAP, "Rate", TextAlign.CENTER, false);

		/** The Quantity field **/
		left += RATE_FIELD_WIDTH;		
		printTextBlock(left+H_GAP,QTY_FIELD_WIDTH-H_GAP-LINE_GAP , "Qty ", TextAlign.CENTER, false);

		/** The UOM field **/		
		left += QTY_FIELD_WIDTH;
		printTextBlock(left+H_GAP, UOM_FIELD_WIDTH-H_GAP-LINE_GAP, "Per ", TextAlign.CENTER, false);

		
		/** The Amount field **/		
		left += UOM_FIELD_WIDTH;
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

		/** The HSN Code field **/
		left += SLNO_FIELD_WIDTH;
		printTextBlock(left+H_GAP, HSNCODE_FIELD_WIDTH-H_GAP-LINE_GAP,dtl.getSaleItem().getHSNCode(), TextAlign.CENTER, false);

		/** The Description field **/
		left += HSNCODE_FIELD_WIDTH;
		printTextBlock(left+H_GAP, DESC_FIELD_WIDTH-H_GAP-LINE_GAP,PosSaleItemUtil.getItemNameToPrint(dtl.getSaleItem(), mUseAltLanguge) , TextAlign.LEFT, false);

	
		/** The Rate field **/
		left += DESC_FIELD_WIDTH;
		
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
		
		/** The Quantity field **/
		left += RATE_FIELD_WIDTH;
		printTextBlock(left+H_GAP, QTY_FIELD_WIDTH-H_GAP-LINE_GAP,
				PosUomUtil.format(PosOrderUtil.getItemQuantity(dtl),PosUOMProvider.getInstance().getMaxDecUom()) , TextAlign.RIGHT, false);

		/** The uom field **/
		left += QTY_FIELD_WIDTH;
		printTextBlock(left+H_GAP, UOM_FIELD_WIDTH-H_GAP-LINE_GAP,dtl.getSaleItem().getUom().getSymbol(), TextAlign.CENTER, false);
		
		/** The total field **/
		left += UOM_FIELD_WIDTH ;
		
		final double amt= (dtl.getSaleItem().getTaxCalculationMethod()==TaxCalculationMethod.InclusiveOfTax)?taxableAmt:rate*dtl.getSaleItem().getQuantity();
		
		printTextBlock(left+H_GAP, AMOUNT_FIELD_WIDTH-H_GAP-LINE_GAP,
				PosCurrencyUtil.format(amt), TextAlign.RIGHT, true);
		page_totalAmt += PosCurrencyUtil.roundTo(amt);
		
		/** print Remarks **/
		printTextBlock(SLNO_FIELD_WIDTH +HSNCODE_FIELD_WIDTH+H_GAP*3, DESC_FIELD_WIDTH-H_GAP*3-LINE_GAP,dtl.getRemarks(), TextAlign.LEFT, true);

		
		/** Print modifiers */
		if(PosEnvSettings.getInstance().getPrintSettings().getPaymentReceiptSettings().isModifiersVisible())
			printAttributes(dtl.getSaleItem());
		
		final BeanDiscount discount=dtl.getSaleItem().getDiscount();
		if(discount!=null && 
				!discount.getCode().equals(PosDiscountItemProvider.NONE_DISCOUNT_CODE) && 
				!discount.getCode().equals(PosPromotionItemProvider.DEF_PROMO_CODE)){
			
			double discAmt;
			if(  dtl.getSaleItem().getTaxCalculationMethod()==TaxCalculationMethod.InclusiveOfTax)
				 discAmt=	PosOrderUtil.getTotalDiscountAmountForExclusiveRate(dtl,rate);
			else
			   discAmt=PosOrderUtil.getTotalDiscountAmount(dtl);
			
			
			page_totalAmt-= PosCurrencyUtil.roundTo(discAmt);
			String discountAmount="-"+PosCurrencyUtil.format(discAmt);
			String discountName=" LESS "+ discount.getName();
			int atX=SLNO_FIELD_WIDTH+HSNCODE_FIELD_WIDTH ;
			printTextBlock(atX +H_GAP*3, DESC_FIELD_WIDTH-H_GAP*3-LINE_GAP,discountName, TextAlign.LEFT, false);
			atX+=DESC_FIELD_WIDTH+QTY_FIELD_WIDTH + RATE_FIELD_WIDTH + UOM_FIELD_WIDTH ;
			printTextBlock(atX+H_GAP,AMOUNT_FIELD_WIDTH-H_GAP-LINE_GAP,discountAmount,TextAlign.RIGHT,true);
			
		}
		setTaxSummary(dtl);
	}
 
	/**
	 * @param beanSaleItem
	 */
	private void printAttributes(BeanSaleItem beanSaleItem) {
		
		for(int i=0;i<5;i++){
			if(beanSaleItem.getAttribSelectedOption(i)!=null&&beanSaleItem.getAttribSelectedOption(i).trim().length()!=0&&!beanSaleItem.getAttribSelectedOption(i).trim().equalsIgnoreCase(PosSaleItemProvider.DEFAULT_ATTRIBUTE_OPTION)){
				 printTextBlock(SLNO_FIELD_WIDTH +HSNCODE_FIELD_WIDTH+H_GAP, DESC_FIELD_WIDTH-H_GAP-LINE_GAP,beanSaleItem.getAttributeName(i)+" :- "+beanSaleItem.getAttribSelectedOption(i).trim(), TextAlign.LEFT, true);
			}
		}
	}
	
	
	/**
	 * 
	 */
	protected boolean printTaxSummary(){

	
	    
	    int lineCount=3;
	    for(Integer taxId: mPageitemTaxList.keySet()){

	    	lineCount+=mPageitemTaxList.get(taxId).getTax1Amount()>0?1:0;
	    	lineCount+=mPageitemTaxList.get(taxId).getTax2Amount()>0?1:0;
	    	lineCount+=mPageitemTaxList.get(taxId).getTax3Amount()>0?1:0;
		}
	    final int taxSectionHeight=getNextLineStartY()+getLineHeight()*lineCount;
	    if(taxSectionHeight>=(REPORT_HEIGHT-REPORT_FOOTER_HEIGHT))
	    	return false;

	    advanceTextLine((REPORT_HEIGHT-REPORT_FOOTER_HEIGHT-taxSectionHeight)/5<2?1:2);
	    
		final int textLeft = SLNO_FIELD_WIDTH+ HSNCODE_FIELD_WIDTH;
		final int valueLeft = textLeft + DESC_FIELD_WIDTH +RATE_FIELD_WIDTH + QTY_FIELD_WIDTH +UOM_FIELD_WIDTH ;
		String taxName;	
		
		
	   double tax1Amt=0;
	   double tax2Amt=0;
	   double tax3Amt=0;
		   
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

		
		for(Integer taxId: mPageitemTaxList.keySet()){

			if(mPageitemTaxList.get(taxId).getTaxAmount()>0){

				final BeanTax tax=PosTaxItemProvider.getInstance().getTaxItem(taxId);
				
				taxName=tax.getName() + " "; //+ PosCurrencyUtil.format(PosTaxUtil.getTotalTaxRate(tax));
				
				
				if(mPageitemTaxList.get(taxId).getTax1Amount()>0){
					
					tax1Amt=mPageitemTaxList.get(taxId).getTax1Amount()-
							PosOrderUtil.getDiscountForPaymentReceipt(mOrder, mPageitemTaxList.get(taxId).getTax1Amount());
					printTextBlock(textLeft+H_GAP, DESC_FIELD_WIDTH-H_GAP-LINE_GAP, 
						 tax.getTaxOneName() + " " + tax.getTaxOnePercentage()+"%", TextAlign.RIGHT, false);
					
					printTextBlock(valueLeft+H_GAP, AMOUNT_FIELD_WIDTH-H_GAP-LINE_GAP,
							PosCurrencyUtil.format(tax1Amt), TextAlign.RIGHT, true);
					page_totalAmt+=PosCurrencyUtil.roundTo(tax1Amt);
				}
				
				if(mPageitemTaxList.get(taxId).getTax2Amount()>0){
					
					tax2Amt=mPageitemTaxList.get(taxId).getTax2Amount()-
							PosOrderUtil.getDiscountForPaymentReceipt(mOrder, mPageitemTaxList.get(taxId).getTax2Amount());
					
					printTextBlock(textLeft+H_GAP, DESC_FIELD_WIDTH-H_GAP-LINE_GAP, 
							tax.getTaxTwoName() + " " + tax.getTaxTwoPercentage()+"%", TextAlign.RIGHT, false);
					
					printTextBlock(valueLeft+H_GAP, AMOUNT_FIELD_WIDTH-H_GAP-LINE_GAP,
							PosCurrencyUtil.format(tax2Amt), TextAlign.RIGHT, true);
					page_totalAmt+=PosCurrencyUtil.roundTo(tax2Amt);
			
				} 
				
				if(mPageitemTaxList.get(taxId).getTax3Amount()>0){
					
					tax3Amt=mPageitemTaxList.get(taxId).getTax3Amount()-
							PosOrderUtil.getDiscountForPaymentReceipt(mOrder, mPageitemTaxList.get(taxId).getTax3Amount());
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
	private void printPageSummary(BeanOrderHeader order) throws Exception {
			
		  
		 
	  
		final int textLeft = SLNO_FIELD_WIDTH+ HSNCODE_FIELD_WIDTH;
		final int valueLeft = textLeft + DESC_FIELD_WIDTH +RATE_FIELD_WIDTH + QTY_FIELD_WIDTH +UOM_FIELD_WIDTH ;
		 double totalDiscAmt=0;
		if(!hasMoreData()) {
			
			if(getNextLineStartY()+getLineHeight()<(REPORT_HEIGHT-REPORT_FOOTER_HEIGHT)){
				if(mOrder.getExtraCharges()>0){
					printTextBlock(textLeft+H_GAP, DESC_FIELD_WIDTH-H_GAP-LINE_GAP, 
							"Extra Charges", TextAlign.RIGHT, false);
					
					printTextBlock(valueLeft+H_GAP, AMOUNT_FIELD_WIDTH-H_GAP-LINE_GAP,
							PosCurrencyUtil.format(mOrder.getExtraCharges()), TextAlign.RIGHT, true);
					page_totalAmt+=mOrder.getExtraCharges();
				}
				
				
			}
			totalDiscAmt=PosOrderUtil.getDiscountForPaymentReceipt(mOrder, page_totalAmt);
			
			isEndOfReport= printTaxSummary();
			
			if(isEndOfReport) {
				page_totalAmt-=totalDiscAmt;
				
				final double roundingAdjustment = PosCurrencyUtil.nRound(page_totalAmt) - page_totalAmt;//PosCurrencyUtil.nRound(page_totalAmt) - page_totalAmt;
				final String roundingAdjustmentText= PosCurrencyUtil.format(roundingAdjustment);
				 if(Double.parseDouble(roundingAdjustmentText)!=0){ 
						
						printTextBlock(textLeft+H_GAP, DESC_FIELD_WIDTH-H_GAP-LINE_GAP, 
								"Round Off", TextAlign.RIGHT, false);
						
						printTextBlock(valueLeft+H_GAP, AMOUNT_FIELD_WIDTH-H_GAP-LINE_GAP,
								roundingAdjustmentText, TextAlign.RIGHT, true);
					}
//				 page_totalAmt+=Double.parseDouble(roundingAdjustmentText);
				 page_totalAmt = PosCurrencyUtil.nRound(page_totalAmt) ;
			}
		}
		 
		
		setNextLineStartY(REPORT_HEIGHT-REPORT_FOOTER_HEIGHT);
	 	 
		int left =SLNO_FIELD_WIDTH + HSNCODE_FIELD_WIDTH;
		
		if(!hasMoreData()  && isEndOfReport ) {
			
			printTextBlock(left+H_GAP,FOOTER_DISCOUNT_TITLE_WIDTH-H_GAP, PosOrderUtil.getDiscountName(mOrder) , TextAlign.LEFT, false);
			
			left+=FOOTER_DISCOUNT_TITLE_WIDTH;
			printTextBlock(left+H_GAP,FOOTER_DISCOUNT_VALUE_WIDTH-H_GAP, PosCurrencyUtil.format(totalDiscAmt) , TextAlign.LEFT, false);
			
		}
		
		left+=FOOTER_DISCOUNT_VALUE_WIDTH;
		printTextBlock(left+H_GAP, DESC_FIELD_WIDTH-FOOTER_DISCOUNT_TITLE_WIDTH-FOOTER_DISCOUNT_VALUE_WIDTH-H_GAP, "Total : " , TextAlign.RIGHT, false);
		
		left =SLNO_FIELD_WIDTH + RATE_FIELD_WIDTH + DESC_FIELD_WIDTH + QTY_FIELD_WIDTH + HSNCODE_FIELD_WIDTH + UOM_FIELD_WIDTH;
		printTextBlock(left+H_GAP, AMOUNT_FIELD_WIDTH-H_GAP-LINE_GAP,
				PosCurrencyUtil.format(page_totalAmt), TextAlign.RIGHT, true);  

		advanceTextLine(1);

		printPageFooterDeliveryDetails();
	}

	/**
	 * 
	 */
	
	private void printPageFooterDeliveryDetails(){

		setNextLineStartY(REPORT_HEIGHT-REPORT_FOOTER_HEIGHT + getLineHeight());
		int left = 0;
	 	
		printTextBlock(left+H_GAP, FOOTER_DUE_DATE_FIELD_WIDTH-H_GAP-LINE_GAP, "Due Date", TextAlign.CENTER, false);

		/** The Day field **/
		left += FOOTER_DUE_DATE_FIELD_WIDTH;
		printTextBlock(left+H_GAP, FOOTER_DUE_DAY_FIELD_WIDTH-H_GAP-LINE_GAP, "Day", TextAlign.CENTER, false);

		/** The Time field **/
		left += FOOTER_DUE_DAY_FIELD_WIDTH;
		printTextBlock(left+H_GAP, FOOTER_DUE_TIME_FIELD_WIDTH-H_GAP-LINE_GAP, "Time", TextAlign.LEFT,	false);

		/** The OT Name field **/
		left += FOOTER_DUE_TIME_FIELD_WIDTH;
		printTextBlock(left+H_GAP,FOOTER_OT_NAME_FIELD_WIDTH-H_GAP-LINE_GAP , "OT Name ", TextAlign.CENTER, false);

		/** The Customer Signature field **/		
		left += FOOTER_OT_NAME_FIELD_WIDTH;
		printTextBlock(left+H_GAP, FOOTER_CUSTOMER_SIGN_FIELD_WIDTH-H_GAP-LINE_GAP, "Customer Signature ", TextAlign.CENTER, false);

		/** The Advance field **/		
		left += FOOTER_CUSTOMER_SIGN_FIELD_WIDTH;
		printTextBlock(left+H_GAP, FOOTER_ADVANCE_TEXT_FIELD_WIDTH-H_GAP-LINE_GAP, "Advance ", TextAlign.CENTER, false);
		
		/** The Advance Value field **/
		final String advanceAmountText=mOrder.getAdvanceAmount()>0?PosCurrencyUtil.format(mOrder.getAdvanceAmount()):"";
		left += FOOTER_ADVANCE_TEXT_FIELD_WIDTH;
		printTextBlock(left+H_GAP, FOOTER_ADVANCE_VALUE_FIELD_WIDTH-H_GAP-LINE_GAP, advanceAmountText, TextAlign.RIGHT, true,true);
		
		left = 0;
		printTextBlock(left+H_GAP, FOOTER_DUE_DATE_FIELD_WIDTH-H_GAP-LINE_GAP, PosDateUtil.format(PosDateUtil.DATE_FORMAT_DDMMYY,mOrder.getDueDateTime()), TextAlign.CENTER, false);

		/** The Day field **/
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(PosDateUtil.parse(mOrder.getDueDateTime()));
		
		left += FOOTER_DUE_DATE_FIELD_WIDTH;
		printTextBlock(left+H_GAP, FOOTER_DUE_DAY_FIELD_WIDTH-H_GAP-LINE_GAP, PosDateUtil.getDayOfWeek(mOrder.getDueDateTime()), TextAlign.CENTER, false);

		/** The Time field **/
		left += FOOTER_DUE_DAY_FIELD_WIDTH;
		printTextBlock(left+H_GAP, FOOTER_DUE_TIME_FIELD_WIDTH-H_GAP-LINE_GAP, PosDateUtil.format(PosDateUtil.SHORT_TIME_FORMAT_12,mOrder.getDueDateTime()) , TextAlign.LEFT,	false);

		/** The OT Name field **/
		final String otName=mOrder.getUser()!=null?mOrder.getUser().getName():"";
		left += FOOTER_DUE_TIME_FIELD_WIDTH;
		printTextBlock(left+H_GAP,FOOTER_OT_NAME_FIELD_WIDTH-H_GAP-LINE_GAP , otName, TextAlign.CENTER, false);

		/** The Customer Signature field **/		
		left += FOOTER_OT_NAME_FIELD_WIDTH;
		printTextBlock(left+H_GAP, FOOTER_CUSTOMER_SIGN_FIELD_WIDTH-H_GAP-LINE_GAP, "", TextAlign.CENTER, false);

		/** The balance field **/		
		left += FOOTER_CUSTOMER_SIGN_FIELD_WIDTH;
		printTextBlock(left+H_GAP, FOOTER_ADVANCE_TEXT_FIELD_WIDTH-H_GAP-LINE_GAP, "Balance", TextAlign.CENTER, false);

		left += FOOTER_ADVANCE_TEXT_FIELD_WIDTH;
		printTextBlock(left+H_GAP, FOOTER_ADVANCE_VALUE_FIELD_WIDTH-H_GAP-LINE_GAP, PosCurrencyUtil.format(PosCurrencyUtil.roundTo(page_totalAmt)-mOrder.getAdvanceAmount()), TextAlign.RIGHT, true,true);

		printText(TextAlign.LEFT, " * Please bring this Order Form with you when taking delivery.");
		printText(TextAlign.LEFT, " * Please take Delivery on the Due Date and note that Cake Should be consumed on the same day.");
		printText(TextAlign.LEFT, " * Please note that any Advance made towards an order is Non Refundable.");
	}
	  
 
	 

	/* (non-Javadoc)
	 * @see com.indocosmo.pos.reports.receipts.PosPrintableReceiptBase#setOrder(com.indocosmo.pos.data.beans.BeanOrderHeader)
	 */
	public void setOrder(BeanOrderHeader order) {

		this.mOrder=order;

	}

	/**
	 * @param dtl
	 */
	protected void setTaxSummary( BeanOrderDetail dtl ){
	 
		 	
			if(dtl.getSaleItem().getTax()==null) return;
	
	//		if(item.getSaleItem().getTaxCalculationMethod() == TaxCalculationMethod.InclusiveOfTax) return;
	
			final BeanTax itemTax=dtl.getSaleItem().getTax();
			BeanReceiptTaxSummary taxSummary=null;
	
			if(mPageitemTaxList.containsKey(itemTax.getId()))
	
				taxSummary=mPageitemTaxList.get(itemTax.getId());
	
			else{
	
				taxSummary=new BeanReceiptTaxSummary();
				mPageitemTaxList.put(itemTax.getId(), taxSummary);
			}
 
				
			
			double tax1Amount=taxSummary.getTax1Amount();
			double tax2Amount=taxSummary.getTax2Amount();
			double tax3Amount=taxSummary.getTax3Amount();
			double taxSCAmount=taxSummary.getTaxSCAmount();
			double taxGSTAmount=taxSummary.getTaxGSTAmount();
			double totalTaxAmo=taxSummary.getTaxAmount();
			double taxableAmo=taxSummary.getTaxableAmount();
	
			tax1Amount+=dtl.getSaleItem().getTax().getTaxAmount().getTaxOneAmount();
			taxSummary.setTax1Amount(tax1Amount);
			tax2Amount+=dtl.getSaleItem().getTax().getTaxAmount().getTaxTwoAmount();
			taxSummary.setTax2Amount(tax2Amount);
			tax3Amount+=dtl.getSaleItem().getTax().getTaxAmount().getTaxThreeAmount();
			taxSummary.setTax3Amount(tax3Amount);
			taxSCAmount+=dtl.getSaleItem().getTax().getTaxAmount().getServiceTaxAmount();
			taxSummary.setTaxSCAmount(taxSCAmount);
			taxGSTAmount+=dtl.getSaleItem().getTax().getTaxAmount().getGSTAmount();
			taxSummary.setTaxGSTAmount(taxGSTAmount);
			
			totalTaxAmo+=PosCurrencyUtil.roundTo(dtl.getSaleItem().getTax().getTaxAmount().getTotalTaxAmount());
			taxSummary.setTaxAmount(totalTaxAmo);
			taxableAmo+=dtl.getSaleItem().getTax().getTaxAmount().getTaxableAmount();
			taxSummary.setTaxableAmount(taxableAmo);
 

	}
	  
}
