/**
 * 
 */
package com.indocosmo.pos.reports.shift;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;

import com.indocosmo.pos.common.PosEnvSettings;
import com.indocosmo.pos.common.PosLog;
import com.indocosmo.pos.common.enums.device.PrinterType;
import com.indocosmo.pos.common.utilities.PosCurrencyUtil;
import com.indocosmo.pos.common.utilities.PosDateUtil;
import com.indocosmo.pos.common.utilities.PosNumberUtil;
import com.indocosmo.pos.data.beans.BeanShiftSummary;
import com.indocosmo.pos.data.beans.BeanPayModeBase.PaymentMode;
import com.indocosmo.pos.data.providers.terminaldb.PosDevReceiptPrinterConfigProvider;
import com.indocosmo.pos.reports.base.PosPrintableReportBase;
import com.indocosmo.pos.reports.base.PosPrintableBase.TextAlign;
import com.indocosmo.pos.terminal.devices.PosDeviceManager;
import com.indocosmo.pos.terminal.devices.PosPrintingDevice;

/**
 * @author sandhya
 */
public class PosShiftSummaryReport extends PosPrintableReportBase{

	private static final int DESC_FIELD_WIDTH = 90;
	private static final int QTY_FIELD_WIDTH = 30;
	private static final int RATE_FIELD_WIDTH = 50;
	protected PosPrintingDevice mBillOutDevice = null;
	private int mColumnCount=40;
	private int mLeftMargin=0;

	protected BeanShiftSummary mShiftSummaryReport;
	protected Font mFontReceipt;
	protected boolean mUseAltLanguge;
	protected String decimalFormat;

	/**
	 * 
	 */
	public PosShiftSummaryReport(BeanShiftSummary ShiftSummaryReport) {

		mShiftSummaryReport=ShiftSummaryReport;
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
	 * @param mBillOutDevice2
	 * @throws Exception 
	 * Print the header details of the report here.
	 */
	protected void printHeaders(PosPrintingDevice billBufferWriter) throws Exception {

		final String hdrLine0 = "Shift Summary Report";
//		final String hdrLine1 = PosEnvSettings.getInstance().getShop().getName();
		final String hdrLine2 = PosEnvSettings.getInstance().getStation().getName();
		final String hdrLine3 ="";
		final String hdrLine4 = PosDateUtil.formatLocal(PosDateUtil.DATE_FORMAT_YYYYMMDD , PosDateUtil.getDateTime()) + "(" + PosDateUtil.getDayOfWeek() + ") " +
				PosDateUtil.format(PosDateUtil.SHORT_TIME_FORMAT_12, PosDateUtil.getDateTime());

		
		setFont(mFontReceipt);
		setFontStyle(Font.BOLD);
		setFontSize(12.0f);
		printText(TextAlign.CENTER, PosEnvSettings.getInstance().getShop().getName());

		
		setFontSize(9.0f); 
		printText(TextAlign.CENTER, hdrLine0);

		setFont(mFontReceipt);
//		printText(TextAlign.CENTER, hdrLine1);
		printText(TextAlign.CENTER, hdrLine2);
		printText(TextAlign.CENTER, hdrLine3);
		advanceLine(3);
		printText(TextAlign.CENTER, hdrLine4);

		advanceLine(6);

		if(mShiftSummaryReport.getShiftCode()!=null)
			printText(TextAlign.LEFT, "SHIFT: "+mShiftSummaryReport.getShiftCode() );
		printText(TextAlign.LEFT, "POS DATE: "+ PosDateUtil.formatLocal(PosDateUtil.DATE_FORMAT_YYYYMMDD ,PosEnvSettings.getInstance().getCashierShiftInfo().getOpeningDate()));

		final int invoice_no_title_length=60;
		final int invoice_no_data_length=	 getPosReportPageFormat().getImageableWidthInPixcel()-invoice_no_title_length;
		
		if(mShiftSummaryReport.getOrderCount()>0){
			printTextBlock(0, invoice_no_title_length, "INVOICE NO : ", TextAlign.LEFT, false);
			printTextBlock(invoice_no_title_length, invoice_no_data_length, mShiftSummaryReport.getOpeningInvoiceNo(), TextAlign.LEFT, true);
			printTextBlock(invoice_no_title_length, invoice_no_data_length, mShiftSummaryReport.getClosingInvoiceNo(), TextAlign.LEFT, true);
//			printText(TextAlign.LEFT, "INVOICE NO : "+  mShiftSummaryReport.getOpeningInvoiceNo() + " - " + mShiftSummaryReport.getClosingInvoiceNo());
		}
 
		
	}


	/**
	 * @param title
	 * @param value
	 */
	private void printLine(String title ){
		
		printTextBlock(5, DESC_FIELD_WIDTH+QTY_FIELD_WIDTH+ RATE_FIELD_WIDTH,title, TextAlign.LEFT, true);
	}
	/**
	 * @param title
	 * @param value
	 */
	private void printLine(String title, String value){
		
		printTextBlock(5, DESC_FIELD_WIDTH+QTY_FIELD_WIDTH,title, TextAlign.LEFT, false);
		printTextBlock(7+DESC_FIELD_WIDTH+QTY_FIELD_WIDTH, RATE_FIELD_WIDTH,value, TextAlign.RIGHT, true);
	}


	/**
	 * @param title
	 * @param value
	 */
	private void printIndentedLine(String title, String value ){
		
		if(PosNumberUtil.parseDoubleSafely(value)!=0){
			printTextBlock(20, DESC_FIELD_WIDTH+QTY_FIELD_WIDTH,title, TextAlign.LEFT, false);
			printTextBlock(7+DESC_FIELD_WIDTH+QTY_FIELD_WIDTH, RATE_FIELD_WIDTH,value, TextAlign.RIGHT, true);
		}
	}
 
	/**
	 * @param billBufferWriter
	 * @return
	 * @throws Exception
	 * Print cash summary here. 
	 */
	protected void printSummary(PosPrintingDevice billBufferWriter) throws Exception{

		advanceLine(6);
		printLine("Opening Cash :",PosCurrencyUtil.format(PosEnvSettings.getInstance().getTillOpenCashierShiftInfo().getOpeningFloat()));
		printLine("Total Bills :", String.valueOf( mShiftSummaryReport.getOrderCount()));
		
		if (mShiftSummaryReport.getCashReceipts() + mShiftSummaryReport.getCardReceipts() +
				mShiftSummaryReport.getVoucherReceipts() + mShiftSummaryReport.getAccountsReceivable()  +
				mShiftSummaryReport.getOnlineReceipts() + mShiftSummaryReport.getClosedOrderAdvance() >0){
				
			printLine("SALES");
			printIndentedLine(PaymentMode.Cash.getDisplayText() +  " :", PosCurrencyUtil.format(mShiftSummaryReport.getCashReceipts()));
			printIndentedLine(PaymentMode.Card.getDisplayText() +  " :", PosCurrencyUtil.format(mShiftSummaryReport.getCardReceipts()));
			printIndentedLine(PaymentMode.Coupon.getDisplayText() +  " :", PosCurrencyUtil.format(mShiftSummaryReport.getVoucherReceipts()));
			printIndentedLine(PaymentMode.Company.getDisplayText() +  " :", PosCurrencyUtil.format(mShiftSummaryReport.getAccountsReceivable()));
			printIndentedLine(PaymentMode.Online.getDisplayText() +  " :", PosCurrencyUtil.format(mShiftSummaryReport.getOnlineReceipts()));
			printIndentedLine("Previous Advance :", PosCurrencyUtil.format(mShiftSummaryReport.getClosedOrderAdvance()));
		}
		if ((mShiftSummaryReport.getCashRefund() + mShiftSummaryReport.getCardRefund() + 
				mShiftSummaryReport.getOnlineRefund() + mShiftSummaryReport.getVoucherRefund() +
				mShiftSummaryReport.getAccountsRefund())>0){
			
			printLine("REFUND");
			printIndentedLine(PaymentMode.Cash.getDisplayText() +  " :", PosCurrencyUtil.format(mShiftSummaryReport.getCashRefund()));
			printIndentedLine(PaymentMode.Card.getDisplayText() +  " :", PosCurrencyUtil.format(mShiftSummaryReport.getCardRefund()));
			printIndentedLine(PaymentMode.Coupon.getDisplayText() +  " :", PosCurrencyUtil.format(mShiftSummaryReport.getVoucherRefund()));
			printIndentedLine(PaymentMode.Company.getDisplayText() +  " :", PosCurrencyUtil.format(mShiftSummaryReport.getAccountsRefund()));
			printIndentedLine(PaymentMode.Online.getDisplayText() +  " :", PosCurrencyUtil.format(mShiftSummaryReport.getOnlineRefund()));
		}
	
		if (mShiftSummaryReport.getCashOut() + mShiftSummaryReport.getVoucherBalance()>0){
			
			printLine("BALANCE");
			printIndentedLine("Cash Out (Card) :", PosCurrencyUtil.format(mShiftSummaryReport.getCashOut() ));
			printIndentedLine(PaymentMode.Coupon.getDisplayText() +  " Balance :", PosCurrencyUtil.format(mShiftSummaryReport.getVoucherBalance() ));
			
		}
		
		setFontStyle(Font.BOLD);
		printLine("Net Sale :", PosCurrencyUtil.format(mShiftSummaryReport.getNetSale()));
		setFont(mFontReceipt); 

		advanceLine(3);
		
		if (mShiftSummaryReport.getCashAdvance() + mShiftSummaryReport.getCardAdvance() +
			mShiftSummaryReport.getVoucherAdvance()   +
			mShiftSummaryReport.getOnlineAdvance() >0){
			
			printLine("ADVANCE COLLECTED TODAY");
			printIndentedLine(PaymentMode.Cash.getDisplayText() +  " :", PosCurrencyUtil.format(mShiftSummaryReport.getCashAdvance()));
			printIndentedLine(PaymentMode.Card.getDisplayText() +  " :", PosCurrencyUtil.format(mShiftSummaryReport.getCardAdvance()));
			printIndentedLine(PaymentMode.Coupon.getDisplayText() +  " :", PosCurrencyUtil.format(mShiftSummaryReport.getVoucherAdvance()));
			printIndentedLine(PaymentMode.Online.getDisplayText() +  " :", PosCurrencyUtil.format(mShiftSummaryReport.getOnlineAdvance()));
		}
		printLine("Expense :", PosCurrencyUtil.format(mShiftSummaryReport.getExpense()));

		setFontStyle(Font.BOLD);
		printLine("Net Cash :", PosCurrencyUtil.format(mShiftSummaryReport.getNetCash()));
		setFont(mFontReceipt); 

		printLine("Discount :", PosCurrencyUtil.format(mShiftSummaryReport.getTotalDiscount() ));
		printLine("Tax :", PosCurrencyUtil.format(mShiftSummaryReport.getTotalTax()));
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
	
		printText(TextAlign.CENTER, "********* End of Report *********");
		
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.pos.reports.base.PosPrintableReportBase#printPageDetails(int)
	 */
	@Override
	protected void printPageDetails(int pageIndex) throws Exception {
		
		printSummary(mBillOutDevice); 
		
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.pos.reports.base.PosPrintableReportBase#printPageHeader(int)
	 */
	@Override
	protected void printPageHeader(int pageIndex) throws Exception {
		
		printHeaders(mBillOutDevice);
		
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
