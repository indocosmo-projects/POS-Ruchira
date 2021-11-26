/**
 * 
 */
package com.indocosmo.pos.reports.miscellaneous;
 
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.util.ArrayList;

import com.indocosmo.pos.common.PosEnvSettings;
import com.indocosmo.pos.common.utilities.PosCurrencyUtil;
import com.indocosmo.pos.common.utilities.PosDateUtil;
import com.indocosmo.pos.common.utilities.PosUomUtil;
import com.indocosmo.pos.data.beans.BeanReportVoidRefundItem;
import com.indocosmo.pos.data.providers.shopdb.PosUOMProvider;
import com.indocosmo.pos.reports.base.PosPrintableBase;
import com.indocosmo.pos.reports.base.PosPrintableBase.TextAlign;
import com.indocosmo.pos.terminal.devices.PosDeviceManager;
import com.indocosmo.pos.common.utilities.PosNumberUtil;
/**
 * @author jojesh
 * 
 */
public class PosRefundItemListReport extends PosPrintableBase {

	private static final int  H_GAP=1;

	private int desc_field_width = 120;
	private static final int QTY_FIELD_WIDTH = 40;
	private static final int TOTAL_FIELD_WIDTH = 40;
	private static final int ITEM_INDICATOR_WIDTH=12;
	private ArrayList<BeanReportVoidRefundItem> refundItemList=new ArrayList<BeanReportVoidRefundItem>();
	private String mOrderId=""; 
	private String mRefundedBy="";
	private int slNo;
	protected Font mFontReceipt;
	protected boolean mUseAltLanguge;
	private String reportDate;
	
	private double mTotalAmount;
	private double mTotalQty;
	private double mSubTotalAmount;
	private double mSubTotalRoundingAdjustment;
	private double mSubTotalQty;
	private boolean mIsSummaryOnly=true;
	 
	/**
	 * 
	 */
	public PosRefundItemListReport() {
		
		mFontReceipt = new Font("Ariel", Font.PLAIN, 8);
		try {
			mUseAltLanguge = PosDeviceManager.getInstance().canUseAltLanguage(getPrinterType());		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		setDecimalFormat();
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
		setFont(mFontReceipt);
		
	desc_field_width=getPosReportPageFormat().getImageableWidthInPixcel()-(
				
				QTY_FIELD_WIDTH+1+
				TOTAL_FIELD_WIDTH
				);
	}

	/**
	 * @param refundItemList the refundItemList to set
	 */
	public void setRefundItemList(ArrayList<BeanReportVoidRefundItem> refundItemList) {
		this.refundItemList = refundItemList;
	}

	/**
	 * @param mIsSummaryOnly the mIsSummaryOnly to set
	 */
	public void setSummaryOnly(boolean mIsSummaryOnly) {
		this.mIsSummaryOnly = mIsSummaryOnly;
	}

	/**
	 * @param reportDate
	 */
	public void setReportDate(String reportDate) {
		this.reportDate = reportDate;
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.indocosmo.pos.reports.receipts.PosPrintableReceiptBase#printHeader
	 * (com.indocosmo.pos.data.beans.BeanOrderHeader,
	 * com.indocosmo.pos.data.beans.BeanBillParam)
	 */
	private void printHeader()
			throws Exception {
		
		final String hdrLine0 = mIsSummaryOnly?"Refund List": "Refund Item List";
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
		final String printedAt =PosDateUtil.formatLocal(PosDateUtil.getDate())  +
				PosDateUtil.format(PosDateUtil.SHORT_TIME_FORMAT_12, PosDateUtil.getDateTime());  
			
		printText(TextAlign.LEFT, "Refund Date: "+ PosDateUtil.formatLocal(reportDate));
		printText(TextAlign.LEFT, "Printed At: "+ printedAt); 
		
		advanceLine(2);
		mOrderId="";
		slNo=0;
		

		
	}
 
 
	/**
	 * Prints the header for details
	 */
	protected void printDetailsHeader() {

		int left = 0;


		/** The Description field **/
		printTextBlock(left, desc_field_width, "Invoice No", TextAlign.LEFT,
				false);

		/** The Quantity field **/
		left += desc_field_width + H_GAP;
		printTextBlock(left, QTY_FIELD_WIDTH, "Qty", TextAlign.RIGHT, false);

		

		/** The Total Amount field **/
		left += QTY_FIELD_WIDTH + H_GAP;
		printTextBlock(left, TOTAL_FIELD_WIDTH, "Amount", TextAlign.RIGHT, true);
		printSingleLine();
	}

private void printRoundingAdjustment(){
	
	if(PosNumberUtil.roundTo(mSubTotalRoundingAdjustment)!=0){
		/** The Item Name field **/
		printTextBlock(0, desc_field_width +QTY_FIELD_WIDTH, "Rounding Adjustment" , TextAlign.RIGHT, false);
	 
	 
	 	printTextBlock(desc_field_width+ QTY_FIELD_WIDTH + H_GAP, TOTAL_FIELD_WIDTH, 
				PosCurrencyUtil.format(mSubTotalRoundingAdjustment) ,TextAlign.RIGHT, true);
	}
}
	
	protected void printDetailItem(int srNo, BeanReportVoidRefundItem dtl) {

	 	int left = 0;
	
		final String itemIndicator="-";
			
			
		if (!mIsSummaryOnly){
			
			printTextBlock(left, ITEM_INDICATOR_WIDTH, itemIndicator, TextAlign.CENTER, false);
		 	
			/** The Item Name field **/
			printTextBlock(left+ITEM_INDICATOR_WIDTH, desc_field_width-ITEM_INDICATOR_WIDTH, dtl.getItemName() , TextAlign.LEFT, false);
		 	
			
			/** The Item Qty field **/
			left += desc_field_width + H_GAP;
			printTextBlock(left, QTY_FIELD_WIDTH,
					PosUomUtil.format(dtl.getRefundQuantity(),dtl.getUom()), TextAlign.RIGHT, false);
			
		
			/** The Total Amount field **/
			left += QTY_FIELD_WIDTH + H_GAP;
		 	printTextBlock(left, TOTAL_FIELD_WIDTH, 
					PosCurrencyUtil.format(dtl.getRefundAmount()) ,TextAlign.RIGHT, true);
		}
	 		
//	 	mTotalAmount+=dtl.getRefundAmount();
//	 	mSubTotalAmount+=dtl.getRefundAmount();
	 	mTotalQty+=dtl.getRefundQuantity();
	 	mSubTotalQty+=dtl.getRefundQuantity();
		 
	} 

	/* (non-Javadoc)
	 * @see com.indocosmo.pos.reports.base.PosPrintableBase#printReport(int)
	 */
	@Override
	protected int printReport(int pageIndex) throws Exception {
		
		if (refundItemList==null || refundItemList.size()==0)
			throw new Exception("No data to print");
		
		printHeader();
		setFont(mFontReceipt);
		
		printDetailsHeader();
		mTotalAmount=0;
		mTotalQty=0;
		int left=0;
		int subHeaderY=0;
		
		for(BeanReportVoidRefundItem dtl:refundItemList){
			
			if (!mOrderId.equals(dtl.getOrderId())){
				
				printInvoiceSummaryData(subHeaderY);
				
				mSubTotalAmount=0;
				mSubTotalQty=0;
				mOrderId=dtl.getOrderId();
	 
				subHeaderY=getNextLineStartY();
				printTextBlock(left, desc_field_width,dtl.getInvoiceNo() , TextAlign.LEFT, true);
				
				mTotalAmount+=dtl.getRefundExtraCharge();
			 	mSubTotalAmount+=dtl.getRefundExtraCharge();
			 	
			 	
			 	mSubTotalAmount =(dtl.getTotalRefundAmt()+ dtl.getTotalRoundingAdjustment());
			 	mSubTotalRoundingAdjustment =  dtl.getTotalRoundingAdjustment() ;
			 	mTotalAmount+=mSubTotalAmount;
			 	
		 
			}
			printDetailItem(++slNo, dtl);
		} 
		printInvoiceSummaryData(subHeaderY); 
		advanceLine(3);
		printFooter();
		return (pageIndex>0?NO_SUCH_PAGE:PAGE_EXISTS);
	}
  
	private void printInvoiceSummaryData(int subHeaderY){
		
		if (!mOrderId.equals("")){
			
			printRoundingAdjustment();
			
			if(!mIsSummaryOnly)
				advanceLine(5);
			final int lastLineY=getNextLineStartY();
			int left=0;
			
			
			setNextLineStartY(subHeaderY);
			left+=(desc_field_width+H_GAP);
			printTextBlock(left, QTY_FIELD_WIDTH,PosUomUtil.format(mSubTotalQty, PosUOMProvider.getInstance().getMaxDecUom()) , TextAlign.RIGHT, false);
			left+=(QTY_FIELD_WIDTH+H_GAP);
			printTextBlock(left, TOTAL_FIELD_WIDTH,PosCurrencyUtil.format(mSubTotalAmount) , TextAlign.RIGHT, true,true);
			
			setNextLineStartY(lastLineY);
		}
	}
	
	private void printFooter(){
		
		 	int left=0;
			printLine(desc_field_width-QTY_FIELD_WIDTH, getNextLineStartY(), getWidth(), getNextLineStartY());
		 	setFontStyle(Font.BOLD);
		 	printTextBlock(0, desc_field_width,"Total :" , TextAlign.RIGHT, false);
				
		 	left+=(desc_field_width+H_GAP);
			printTextBlock(left, QTY_FIELD_WIDTH,PosUomUtil.format(mTotalQty, PosUOMProvider.getInstance().getMaxDecUom()) , TextAlign.RIGHT, false);
			left+=(QTY_FIELD_WIDTH+H_GAP);
			printTextBlock(left, TOTAL_FIELD_WIDTH,PosCurrencyUtil.format(mTotalAmount) , TextAlign.RIGHT, true,true);
			setFont(mFontReceipt);
		 
		}

}
