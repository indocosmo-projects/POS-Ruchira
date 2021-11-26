/**
 * 
 */
package com.indocosmo.pos.reports.base;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.PrinterException;

import com.google.zxing.WriterException;
import com.indocosmo.barcode.common.codeprinter.CodePrintingUtil;
import com.indocosmo.barcode.common.codeprinter.bean.BeanBarCodePrintingSettings;
import com.indocosmo.pos.data.beans.BeanBillParam;
import com.indocosmo.pos.data.beans.BeanOrderHeader;
import com.indocosmo.pos.data.providers.shopdb.PosBillParamProvider;

/**
 * @author jojesh
 * 
 */
public abstract class PosPrintableReceiptBase extends PosPrintableReportBase {

	protected BeanOrderHeader mOrder;
	protected BeanBillParam mBillParam;
	protected Font mFontReceipt;
	/**
	 * 
	 */
	public PosPrintableReceiptBase() {
		
		super();
		mFontReceipt = new Font("Arial", Font.PLAIN, 8);
		
		
	}

	/**
	 * Set the order object to be printed
	 * 
	 * @param order
	 */
	public void setOrder(BeanOrderHeader order) {
		mOrder = order;
		
	}
	
	public BeanOrderHeader getOrder(){
		
		return mOrder;
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

	/* (non-Javadoc)
	 * @see com.indocosmo.pos.reports.base.PosPrintableReportBase#isPageExist(int)
	 */
	@Override
	protected boolean isPageExist(int pageIndex) throws Exception {
	
		return pageIndex==0;
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

		if (mBillParam == null)
			loadBillParams();
	}
	
	/* (non-Javadoc)
	 * @see com.indocosmo.pos.reports.base.PosPrintableReportBase#printReportHeader(int)
	 */
	@Override
	protected void printReportHeader(int pageIndex) throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	/* (non-Javadoc)
	 * @see com.indocosmo.pos.reports.base.PosPrintableReportBase#printReportHeader(int)
	 */
	@Override
	protected void printPageHeader(int pageIndex) throws Exception {
		
		onPrintHeader(mOrder, mBillParam);
		
	}
	
	/* (non-Javadoc)
	 * @see com.indocosmo.pos.reports.base.PosPrintableReportBase#printPageDetails(int)
	 */
	@Override
	protected void printPageDetails(int pageIndex) throws Exception {
	
		onPrintDetails(mOrder);
		
	}
	
	/* (non-Javadoc)
	 * @see com.indocosmo.pos.reports.base.PosPrintableReportBase#printPageFooter(int)
	 */
	@Override
	protected void printPageFooter(int pageIndex) throws Exception {
		
		  onPrintBillSummary(mOrder);
	      onPrintPaymentSummary(mOrder);
	      onPrintFooter(mBillParam, mOrder);
	}
	
	/* (non-Javadoc)
	 * @see com.indocosmo.pos.reports.base.PosPrintableReportBase#onPageChanged(int, int)
	 */
	@Override
	protected void onPageChanged(int oldPage, int newPage) throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	/* (non-Javadoc)
	 * @see com.indocosmo.pos.reports.base.PosPrintableReportBase#printReportFooter(int)
	 */
	@Override
	protected void printReportFooter(int pageIndex) throws Exception {
		
		if(mListner!=null)
			mListner.onPrintingCompleted();
	}
	
	/* (non-Javadoc)
	 * @see com.indocosmo.pos.reports.base.PosPrintableReportBase#hasMoreData()
	 */
	@Override
	protected boolean hasMoreData() throws Exception {
 
		return false;
	}

	/**
	 * Default header detail separator line style. Override this method to
	 * change the line style in custom formats
	 * 
	 * @return
	 */
	protected LineStyles getDefaultHeaderSeparatorStyle() {
		return LineStyles.SINGLE;
	}

	/**
	 * Default detail payment separator line style. Override this method to
	 * change the line style in custom formats
	 * 
	 * @return
	 */
	protected LineStyles getDefaultDetailSeparatorStyle() {
		return LineStyles.SINGLE;
	}
	
	/**
	 * Default bill summary separator line style Override this method to
	 * change the line style in custom formats
	 * 
	 * @return
	 */
	protected LineStyles getDefaultBillSummarySeparatorStyle() {
		return LineStyles.SINGLE;
	}

	/**
	 * Default payment summary separator line style Override this method to
	 * change the line style in custom formats
	 * 
	 * @return
	 */
	protected LineStyles getDefaultPaymentSummarySeparatorStyle() {
		return LineStyles.SINGLE;
	}

	/**
	 * Prints the header of the receipt
	 * 
	 * @param Order
	 *            object
	 * @throws Exception
	 */
	protected abstract void onPrintHeader(BeanOrderHeader order,
			BeanBillParam param) throws Exception;

	/**
	 * Prints the order details part
	 * 
	 * @param order
	 * @throws Exception
	 */
	protected abstract void onPrintDetails(BeanOrderHeader order)
			throws Exception;

	/**
	 * Prints the order bill summary info.
	 * 
	 * @param order
	 * @throws Exception
	 */
	protected abstract void onPrintBillSummary(BeanOrderHeader order)
			throws Exception;

	/***
	 * Prints the payments summary part
	 * 
	 * @param order
	 * @throws Exception
	 */
	protected abstract void onPrintPaymentSummary(BeanOrderHeader order)
			throws Exception;

	/**
	 * Prints the footer of the receipt
	 * 
	 * @param Order
	 *            object
	 * @throws Exception
	 */
	protected abstract void onPrintFooter(BeanBillParam param,
			BeanOrderHeader order) throws Exception;

	/* (non-Javadoc)
	 * @see com.indocosmo.pos.reports.PosPrintableReportBase#print(java.awt.Graphics, java.awt.print.PageFormat, int)
	 */
	@Override
	public int print(Graphics g, PageFormat pf, int pageIndex)
			throws PrinterException {
		
		Graphics2D mGPrinter = (Graphics2D) g;
		mGPrinter.setFont(mFontReceipt);
		return super.print(g, pf, pageIndex);
	}
	
	/**
	 * @throws WriterException 
	 * 
	 */
	protected void printOrderIDBarcode() throws WriterException {
	
		final BeanBarCodePrintingSettings settings=CodePrintingUtil.getDefaultBarCodePrintingSettings();
		settings.setCode(mOrder.getOrderId());
		printBarcode(settings);
		
	}
	
	/**
	 * @throws WriterException 
	 * 
	 */
//	protected void printOrderIDBarcode(BeanBarCodePrintingSettings settings) throws WriterException {
//	
//		settings.setCode(mOrder.getOrderId());
//		
//		BufferedImage barCode=CodePrintingUtil.createBarCode(settings);
//		printImage(barCode);
//		
//	}
}
