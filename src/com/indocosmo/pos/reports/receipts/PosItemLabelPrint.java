/**
 * 
 */
package com.indocosmo.pos.reports.receipts;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;

import com.indocosmo.pos.common.PosEnvSettings;
import com.indocosmo.pos.common.PosLog;
import com.indocosmo.pos.common.enums.device.PrinterType;
import com.indocosmo.pos.common.utilities.PosCurrencyUtil;
import com.indocosmo.pos.common.utilities.PosDateUtil;
import com.indocosmo.pos.common.utilities.PosOrderUtil;
import com.indocosmo.pos.data.beans.BeanOrderDetail;
import com.indocosmo.pos.data.beans.BeanOrderHeader;
import com.indocosmo.pos.data.beans.BeanShiftSummary;
import com.indocosmo.pos.data.providers.terminaldb.PosDevReceiptPrinterConfigProvider;
import com.indocosmo.pos.reports.base.PosPrintableReportBase;
import com.indocosmo.pos.reports.base.PosPrintableBase.TextAlign;
import com.indocosmo.pos.terminal.devices.PosDeviceManager;
import com.indocosmo.pos.terminal.devices.PosPrintingDevice;

/**
 * @author sandhya
 */
public class PosItemLabelPrint extends PosPrintableReportBase{

	private static final int DESC_FIELD_WIDTH = 55;
	private static final int H_GAP = 10;
	private static int VALUE_FIELD_WIDTH = 100;
	protected PosPrintingDevice mBillOutDevice = null;
	private int mColumnCount=40;
	private int mLeftMargin=0;

	protected Font mFontReceipt;
	protected boolean mUseAltLanguge;
	protected String decimalFormat;
	BeanOrderDetail mOrderDetail;
	BeanOrderHeader mOrderHdr;	
	
	/**
	 * 
	 */
	public PosItemLabelPrint() {

		mOrderHdr=null;
		mOrderDetail=null;
		mFontReceipt = new Font("Ariel", Font.PLAIN, 8);
		try {
			
			mUseAltLanguge = PosDeviceManager.getInstance().canUseAltLanguage(getPrinterType());
		} catch (Exception e) {
			
			 PosLog.write(this, "PosShiftSummaryReport", e);
		}
	}
	
	/**
	 * 
	 */
	public PosItemLabelPrint(BeanOrderHeader orderHdr, BeanOrderDetail orderDetail) {

		mOrderHdr=orderHdr;
		mOrderDetail=orderDetail;
		mFontReceipt = new Font("Ariel", Font.PLAIN, 8);
		try {
			
			mUseAltLanguge = PosDeviceManager.getInstance().canUseAltLanguage(getPrinterType());
		} catch (Exception e) {
			
			 PosLog.write(this, "PosShiftSummaryReport", e);
		}
	}

	/**
	 * 
	 */
	private void setDecimalFormat() {
		
		decimalFormat="#,###.##";
	}

 

	/**
	 * @param title
	 * @param value
	 */
	private void printLine(String title, String value){
		
		printTextBlock(5, DESC_FIELD_WIDTH ,title, TextAlign.LEFT, false);
		printTextBlock(DESC_FIELD_WIDTH, H_GAP ," : ", TextAlign.CENTER, false);
		printTextBlock(DESC_FIELD_WIDTH +H_GAP , VALUE_FIELD_WIDTH,value, TextAlign.LEFT, true,true);
	}


	/**
	 * @param title
	 * @param remarks
	 */
	private void printItemRemarks(String title, String remarks){
		
		printTextBlock(5, DESC_FIELD_WIDTH ,title, TextAlign.LEFT, false);
		printTextBlock(DESC_FIELD_WIDTH, H_GAP ," : ", TextAlign.CENTER, false);
		if(remarks!=null && remarks.trim().length()>0){

			final String[] remarksToPrint=remarks.split("\\r\\n|\\n|\\r");
			for(String remark:remarksToPrint){
				String[] tokens = remark.split("(?<=\\G.{" + 30 + "})");
				for(String line : tokens) {
					printTextBlock(DESC_FIELD_WIDTH +H_GAP , VALUE_FIELD_WIDTH,line, TextAlign.LEFT, true,true);
					
				}
			}
			 
		}else
			printTextBlock(DESC_FIELD_WIDTH +H_GAP , VALUE_FIELD_WIDTH,"", TextAlign.LEFT, true,true);
	}
	
	/**
	 * @param billBufferWriter
	 * @return
	 * @throws Exception
	 * Print cash summary here. 
	 */
	protected void printItemDetails(PosPrintingDevice billBufferWriter) throws Exception{

		setFont(mFontReceipt);
		advanceLine(2);
//		printLine("ORDER NO ", PosEnvSettings.getInstance().getUISetting().useOrderQueueNo()?PosOrderUtil.getFormatedOrderQueueNo(mOrderHdr):
//			PosOrderUtil.getShortOrderIDFromOrderID(mOrderHdr.getOrderId()));
		printLine("ORDER NO ", 	PosOrderUtil.getShortOrderIDFromOrderID(mOrderHdr.getOrderId()));
		printLine("CUST. NAME ",mOrderHdr.getOrderCustomer().getName());
		printLine("MOBILE NO ",mOrderHdr.getOrderCustomer().getPhoneNumber());
		printLine("ITEM ",mOrderDetail.getSaleItem().getName());
		printItemRemarks("MESSAGE ",mOrderDetail.getRemarks());
		
		
		printLine("DUE DATE ",PosDateUtil.formatLocal(mOrderHdr.getDueDateTime()));
		printLine("TIME ",PosDateUtil.format(PosDateUtil.SHORT_TIME_FORMAT_12, mOrderHdr.getDueDateTime()));
		printLine("BARCODE ",mOrderDetail.getSaleItem().getBarCode());

		advanceLine(3);

	}

	/**
	 * @return
	 */
	final protected int getColumnCount() {
		
		return mColumnCount;
	}

	/**
	 * @return the LeftMargin
	 */
	public int getLeftMargin() {
		
		return mLeftMargin;
	}


	/* (non-Javadoc)
	 * @see com.indocosmo.pos.reports.PosPrintableReportBase#onInitPrint(java.awt.Graphics2D, java.awt.print.PageFormat)
	 */
	@Override
	protected void onInitialized(Graphics2D g2d, PageFormat pf) throws Exception {
		
		setFont(mFontReceipt);
		setDecimalFormat();
		
		VALUE_FIELD_WIDTH=getPosReportPageFormat().getImageableWidthInPixcel()-(DESC_FIELD_WIDTH+H_GAP);
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.pos.reports.base.PosPrintableReportBase#onReportInit()
	 */
	@Override
	protected void initReport() {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.pos.reports.base.PosPrintableReportBase#onPageInit(int)
	 */
	@Override
	protected void initPage(int pageIndex) throws Exception {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.pos.reports.base.PosPrintableReportBase#printReportFooter(int)
	 */
	@Override
	protected void printReportFooter(int pageIndex) throws Exception {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.pos.reports.base.PosPrintableReportBase#printPageFooter(int)
	 */
	@Override
	protected void printPageFooter(int pageIndex) throws Exception {
	
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.pos.reports.base.PosPrintableReportBase#printPageDetails(int)
	 */
	@Override
	protected void printPageDetails(int pageIndex) throws Exception {
		
		printItemDetails(mBillOutDevice); 
		
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.pos.reports.base.PosPrintableReportBase#printPageHeader(int)
	 */
	@Override
	protected void printPageHeader(int pageIndex) throws Exception {
		
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.pos.reports.base.PosPrintableReportBase#printReportHeader(int)
	 */
	@Override
	protected void printReportHeader(int pageIndex) throws Exception {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.pos.reports.base.PosPrintableReportBase#isPageExist(int)
	 */
	@Override
	protected boolean isPageExist(int pageIndex) throws Exception {
		
		
		return (pageIndex==0);
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.pos.reports.base.PosPrintableReportBase#onPageChanged(int, int)
	 */
	@Override
	protected void onPageChanged(int oldPage, int newPage) throws Exception {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.pos.reports.base.PosPrintableReportBase#hasMoreData()
	 */
	@Override
	protected boolean hasMoreData() throws Exception {
		// TODO Auto-generated method stub
		return false;
	}


}
