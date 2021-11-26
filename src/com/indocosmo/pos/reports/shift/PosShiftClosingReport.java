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
import com.indocosmo.pos.data.beans.BeanShiftCloseSummary;
import com.indocosmo.pos.data.beans.BeanPayModeBase.PaymentMode;
import com.indocosmo.pos.data.providers.terminaldb.PosDevReceiptPrinterConfigProvider;
import com.indocosmo.pos.reports.base.PosPrintableBase;
import com.indocosmo.pos.reports.base.PosPrintableBase.TextAlign;
import com.indocosmo.pos.terminal.devices.PosDeviceManager;
import com.indocosmo.pos.terminal.devices.PosPrintingDevice;

/**
 * @author sandhya
 */
public class PosShiftClosingReport extends PosPrintableBase{

	private static final int DESC_FIELD_WIDTH = 90;
	private static final int QTY_FIELD_WIDTH = 30;
	private static final int RATE_FIELD_WIDTH = 70;
	protected PosPrintingDevice mBillOutDevice = null;
	private int mColumnCount=40;
	private int mLeftMargin=0;

	protected BeanShiftCloseSummary  mShiftSummary;
	protected Font mFontReceipt;
	protected boolean mUseAltLanguge;
	protected String decimalFormat;
	private boolean isShiftClosed =false;

	/**
	 * 
	 */
	public PosShiftClosingReport(BeanShiftCloseSummary shiftSummary) {

		mShiftSummary=shiftSummary;
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
	public void setIsShiftClosed(boolean isClosed){
		isShiftClosed=isClosed;
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

		final String hdrLine0 = "SHIFT CLOSE SUMMARY";
//		final String hdrLine1 = PosEnvSettings.getInstance().getShop().getName();
		final String hdrLine2 = PosEnvSettings.getInstance().getStation().getName();
		final String hdrLine3 ="";
		final String hdrLine4 = PosDateUtil.formatLocal(PosDateUtil.getDate()) + "(" + PosDateUtil.getDayOfWeek() + ") " +
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
		
	 	printText(TextAlign.LEFT, "Shift: "+ mShiftSummary.getShiftItem().getDisplayText() ,false);
		printText(TextAlign.RIGHT, "Pos Date: "+  PosDateUtil.formatLocal(mShiftSummary.getOpeningDate()));
		
		String tillDateRange="Till : [" +
					PosDateUtil.format(PosDateUtil.SHORT_TIME_FORMAT_12,mShiftSummary.getOpeningTime()) + "]" ;
		if(isShiftClosed)
			tillDateRange += " - [" +  
					PosDateUtil.format(PosDateUtil.SHORT_TIME_FORMAT_12,mShiftSummary.getClosingTime()) + "]";
		
		printText(TextAlign.LEFT,tillDateRange);
		printText(TextAlign.LEFT, "Opened By : "+  mShiftSummary.getCashierInfo().getName());
 
		 
	}



	/**
	 * @param title
	 * @param value
	 */
	private void printLine(String title, String value,TextAlign align){
		
		printTextBlock(5, DESC_FIELD_WIDTH,title, TextAlign.LEFT, false);
		printTextBlock(7+DESC_FIELD_WIDTH, QTY_FIELD_WIDTH + RATE_FIELD_WIDTH,value, align, true);
	}
	
	/**
	 * @param title
	 * @param value
	 */
	private void printLine(String title, String value){
		printLine(title,value,false); 
	}
	
	/**
	 * @param title
	 * @param value
	 */
	private void printLine(String title, String value,boolean printZeroValue){
		
		printLine(title,value,printZeroValue,true); 
		 
	}
	/**
	 * @param title
	 * @param value
	 */
	private void printLine(String title, String value,boolean printZeroValue,boolean advanceLine){
		
		if (PosNumberUtil.parseDoubleSafely(value)!=0 || printZeroValue ) {
			printTextBlock(5, DESC_FIELD_WIDTH+QTY_FIELD_WIDTH,title, TextAlign.LEFT,false );
			printTextBlock(7+DESC_FIELD_WIDTH+QTY_FIELD_WIDTH, RATE_FIELD_WIDTH,value, TextAlign.RIGHT, advanceLine);
		}
	}/**
	 * @param billBufferWriter
	 * @return
	 * @throws Exception
	 * Print cash summary here. 
	 */
	protected void printShiftSummary(PosPrintingDevice billBufferWriter) throws Exception{

		advanceLine(3);
		setFontStyle(Font.BOLD);
		printText(TextAlign.LEFT, "SHIFT DETAILS");
		advanceLine(2);
		setFont(mFontReceipt);
		printLine(PaymentMode.Cash.getDisplayText() + " Receipts :", PosCurrencyUtil.format(mShiftSummary.getCashReceipts()-mShiftSummary.getCashReturned()),true);
		printLine(PaymentMode.Card.getDisplayText() + " Receipts :", PosCurrencyUtil.format(mShiftSummary.getCardReceipts()));
		printLine("Cash Out (Card):", PosCurrencyUtil.format(mShiftSummary.getCashOut()));
		printLine( PaymentMode.Coupon.getDisplayText() + " Receipts :", PosCurrencyUtil.format(mShiftSummary.getVoucherReceipts()));
		printLine(PaymentMode.Coupon.getDisplayText() + " Balance :", PosCurrencyUtil.format(mShiftSummary.getVoucherBalance()));
		printLine(PaymentMode.Company.getDisplayText() + " :", PosCurrencyUtil.format(mShiftSummary.getAccountsReceivable()));
		printLine(PaymentMode.Online.getDisplayText() + " Receipts :", PosCurrencyUtil.format(mShiftSummary.getOnlineReceipts()));
		printLine("Less Refund :", PosCurrencyUtil.format(-1 * mShiftSummary.getTotalRefund()),false,true);

		printLine("Previous Advance :", PosCurrencyUtil.format(-1 * mShiftSummary.getClosedOrderAdvance()),false,true);
		printText(TextAlign.RIGHT, "----------------  ");
//		printTextBlock(7+DESC_FIELD_WIDTH+QTY_FIELD_WIDTH, RATE_FIELD_WIDTH,"_____________", TextAlign.RIGHT, true);
//		advanceLine();
		printLine("Net Sale :", PosCurrencyUtil.format(mShiftSummary.getNetSale()),true);
	
		advanceLine(3);
		
		printLine("Advance(" + PaymentMode.Cash.getDisplayText() + ") :", PosCurrencyUtil.format(mShiftSummary.getCashAdvance()));
		printLine("Advance(" + PaymentMode.Card.getDisplayText() + ") :", PosCurrencyUtil.format(mShiftSummary.getCardAdvance()));
		printLine("Advance(" + PaymentMode.Online.getDisplayText() + ") :", PosCurrencyUtil.format(mShiftSummary.getOnlineAdvance()));
		printLine("Advance(" + PaymentMode.Coupon.getDisplayText() + ") :", PosCurrencyUtil.format(mShiftSummary.getVoucherAdvance()));
		
	}

	
	/**
	 * @param billBufferWriter
	 * @return
	 * @throws Exception
	 * Print cash summary here. 
	 */
	protected void printCashSummary(PosPrintingDevice billBufferWriter) throws Exception{

		advanceLine(3);
		setFontStyle(Font.BOLD);
		printText(TextAlign.LEFT, "CASH SUMMARY");
		advanceLine(2);
		setFont(mFontReceipt);
		
		printLine("Opening Cash :",PosCurrencyUtil.format(mShiftSummary.getOpeningFloat()),true);
		printLine("Cash Recieved :", PosCurrencyUtil.format(mShiftSummary.getNetCash()),true);
		printLine("Cash Out (Card) :", PosCurrencyUtil.format(mShiftSummary.getCashOut()));
		printLine("Voucher Bal. Returned :", PosCurrencyUtil.format(mShiftSummary.getVoucherBalanceReturned()));
		printLine("Cash Refund :", PosCurrencyUtil.format(-1 * mShiftSummary.getCashRefund()));
		printLine("Expense :", PosCurrencyUtil.format(-1*mShiftSummary.getExpense() ),false,true);
		printText(TextAlign.RIGHT, "----------------  ");
//		printTextBlock(7+DESC_FIELD_WIDTH+QTY_FIELD_WIDTH, RATE_FIELD_WIDTH,"_____________", TextAlign.RIGHT, true);
		
		printLine("Net Cash :", PosCurrencyUtil.format(mShiftSummary.getClosingCash()),true);
		printLine("Actual Cash :", PosCurrencyUtil.format(mShiftSummary.getActualCash()),true,true);
		 
 
		if(mShiftSummary.getVariance()!=0)
			printText(TextAlign.RIGHT, "----------------  ");
		printLine("Cash Variance :" ,PosCurrencyUtil.format(mShiftSummary.getVariance()));
		printLine("Amount Deposited :", PosCurrencyUtil.format(mShiftSummary.getCashDeposit()));
		
		printLine("Bank Deposit Slip  :", mShiftSummary.getReferenceSlipNumber(),TextAlign.RIGHT);
		
		//printLine("Reason :", PosNumberUtil.formatNumber(mShiftSummary.getExpense() ));
	//	advanceLine(2);
		
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
	 * @see com.indocosmo.pos.reports.PosPrintableReportBase#printReport()
	 */
	@Override
	protected int printReport(int pageIndex) throws Exception {
		
		setDecimalFormat();
		printHeaders(mBillOutDevice);
		printShiftSummary(mBillOutDevice);
		printCashSummary(mBillOutDevice); 
		advanceTextLine(1);
		printText(TextAlign.CENTER, "********* End of Report *********");
		
		return (pageIndex>0?NO_SUCH_PAGE:PAGE_EXISTS);
	}

	public void initPrint()throws Exception{

	}
	/* (non-Javadoc)
	 * @see com.indocosmo.pos.reports.PosPrintableReportBase#onInitPrint(java.awt.Graphics2D, java.awt.print.PageFormat)
	 */
	@Override
	protected void onInitialized(Graphics2D g2d, PageFormat pf) throws Exception {
		setFont(mFontReceipt);
	}


}
