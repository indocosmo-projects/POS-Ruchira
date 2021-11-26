/**
 * 
 */
package com.indocosmo.pos.reports.miscellaneous;
 
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.util.ArrayList;

import com.indocosmo.pos.common.PosEnvSettings;
import com.indocosmo.pos.common.enums.PosQueueNoResetPolicy;
import com.indocosmo.pos.common.utilities.PosCurrencyUtil;
import com.indocosmo.pos.common.utilities.PosDateUtil;
import com.indocosmo.pos.common.utilities.PosNumberUtil;
import com.indocosmo.pos.common.utilities.PosOrderUtil;
import com.indocosmo.pos.common.utilities.PosUomUtil;
import com.indocosmo.pos.data.beans.BeanOrderHeader.PosOrderStatus;
import com.indocosmo.pos.data.beans.BeanReportVoidRefundItem;
import com.indocosmo.pos.data.providers.shopdb.PosUOMProvider;
import com.indocosmo.pos.reports.base.PosPrintableBase;
import com.indocosmo.pos.terminal.devices.PosDeviceManager;
/**
 * @author jojesh
 * 
 */
public class PosVoidItemListReport extends PosPrintableBase {

	private static final int  H_GAP=1;

	private int desc_field_width = 120;
	private static final int QTY_FIELD_WIDTH = 40;
	private static final int TOTAL_FIELD_WIDTH = 40;

	private ArrayList<BeanReportVoidRefundItem> voidItemList=new ArrayList<BeanReportVoidRefundItem>();
	private String mOrderId=""; 
	private int slNo;
	protected Font mFontReceipt;
	protected boolean mUseAltLanguge;
	private String reportDate;
	
	private double mTotalAmount;
	private double mTotalQty;
	private double mSubTotalAmount;
	private double mSubTotalQty;
	private boolean mIsSummaryOnly=true;
	/**
	 * 
	 */
	public PosVoidItemListReport() {
		
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
	 * @param voidItemList the voidItemList to set
	 */
	public void setVoidItemList(ArrayList<BeanReportVoidRefundItem> voidItemList) {
		this.voidItemList = voidItemList;
	}


	/**
	 * @param reportDate
	 */
	public void setReportDate(String reportDate) {
		this.reportDate = reportDate;
	}
	
	/**
	 * @param mIsSummaryOnly the mIsSummaryOnly to set
	 */
	public void setSummaryOnly(boolean mIsSummaryOnly) {
		this.mIsSummaryOnly = mIsSummaryOnly;
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
		final String hdrLine0 = mIsSummaryOnly?"Void List": "Void Item List";
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
//		advanceLine(3);
//		printText(TextAlign.CENTER, hdrLine4);
		
		advanceLine(6);
		final String printedAt =PosDateUtil.formatLocal(PosDateUtil.getDate())  +
				PosDateUtil.format(PosDateUtil.SHORT_TIME_FORMAT_12, PosDateUtil.getDateTime());  
			
		printText(TextAlign.LEFT, "Void At: "+ PosDateUtil.formatLocal(reportDate),true);
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

	
	protected void printDetailItem(int srNo, BeanReportVoidRefundItem dtl) {

	 	int left = 0;
		final int itemIndicatorWidth=12;
		final String itemIndicator="-";
		
		
		if (!mIsSummaryOnly){
			
			printTextBlock(left, itemIndicatorWidth, itemIndicator, TextAlign.CENTER, false);
		 	
			/** The Item Name field **/
			printTextBlock(left+itemIndicatorWidth, desc_field_width-itemIndicatorWidth, dtl.getItemName() , TextAlign.LEFT, false);
		 	
			
			/** The Item Qty field **/
			left += desc_field_width + H_GAP;
			printTextBlock(left, QTY_FIELD_WIDTH,
					PosUomUtil.format(dtl.getQuantity(),dtl.getUom()), TextAlign.RIGHT, false);
			
		
			/** The Total Amount field **/
			left += QTY_FIELD_WIDTH + H_GAP;
		 	printTextBlock(left, TOTAL_FIELD_WIDTH, 
					PosCurrencyUtil.format(dtl.getItemTotal()) ,TextAlign.RIGHT, true);
		}
	 		
	 	mTotalAmount+=dtl.getItemTotal();
		mTotalQty+=dtl.getQuantity();
		 
	 	mSubTotalAmount+=dtl.getItemTotal();
	 	mSubTotalQty+=dtl.getQuantity();
	 
	} 

	/* (non-Javadoc)
	 * @see com.indocosmo.pos.reports.base.PosPrintableBase#printReport(int)
	 */
	@Override
	protected int printReport(int pageIndex) throws Exception {
		
		if (voidItemList==null || voidItemList.size()==0)
			throw new Exception("No data to print");
		
		printHeader();
		setFont(mFontReceipt);
		
		printDetailsHeader();
		mTotalAmount=0;
		mTotalQty=0;
		String refNo;
		int left=0;
		int subHeaderY=0;
		
		for(BeanReportVoidRefundItem dtl:voidItemList){
			
			if (!mOrderId.equals(dtl.getOrderId())){
			
				printInvoiceSummaryData(subHeaderY);
					
				mSubTotalAmount=0;
				mSubTotalQty=0;
				mOrderId=dtl.getOrderId();
				
				 
				if(dtl.getStatus()==PosOrderStatus.Closed || dtl.getStatus()==PosOrderStatus.Refunded)
					refNo=dtl.getInvoiceNo();
				else if (PosEnvSettings.getInstance().getUISetting().useOrderQueueNo() && PosEnvSettings.getInstance().getOrderQueueResetPolicy()==PosQueueNoResetPolicy.NORESET)
					refNo=PosOrderUtil.getFormatedOrderQueueNo(PosNumberUtil.parseIntegerSafely(dtl.getQueueNo()), dtl.getServiceType(), dtl.getServiceTable());
				else
					refNo=PosOrderUtil.getShortOrderIDFromOrderID(dtl.getOrderId());
				 
			 
				subHeaderY=getNextLineStartY();
				printTextBlock(left, desc_field_width,refNo , TextAlign.LEFT, true);
			
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
			
			if(!mIsSummaryOnly)
				advanceLine(5);
			final int lastLineY=getNextLineStartY();
			int left=0;
			
			
			setNextLineStartY(subHeaderY);
			left+=(desc_field_width+H_GAP);
			printTextBlock(left, QTY_FIELD_WIDTH, PosUomUtil.format(mSubTotalQty, PosUOMProvider.getInstance().getMaxDecUom())  , TextAlign.RIGHT, false);
			left+=(QTY_FIELD_WIDTH+H_GAP);
			printTextBlock(left, TOTAL_FIELD_WIDTH,PosCurrencyUtil.format(mSubTotalAmount) , TextAlign.RIGHT, true,true);
			
			setNextLineStartY(lastLineY);
			
//			mTotalAmount+=mSubTotalAmount;
//			mTotalQty+=mSubTotalQty;
		}
	}
	
	private void printFooter(){
		
		 	int left=0;
			printLine(desc_field_width-QTY_FIELD_WIDTH, getNextLineStartY(), getWidth(), getNextLineStartY());
		 	setFontStyle(Font.BOLD);
		 	printTextBlock(0, desc_field_width,"Total :" , TextAlign.RIGHT, false);
				
		 	left+=(desc_field_width+H_GAP);
			printTextBlock(left, QTY_FIELD_WIDTH,PosUomUtil.format(mTotalQty, PosUOMProvider.getInstance().getMaxDecUom()), TextAlign.RIGHT, false);
			left+=(QTY_FIELD_WIDTH+H_GAP);
			printTextBlock(left, TOTAL_FIELD_WIDTH,PosCurrencyUtil.format(mTotalAmount) , TextAlign.RIGHT, true,true);
			setFont(mFontReceipt);
		 
		}
	}
	 
	 

 
