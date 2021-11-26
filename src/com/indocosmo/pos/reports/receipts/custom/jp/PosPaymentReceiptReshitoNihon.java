/**
 * 
 */
package com.indocosmo.pos.reports.receipts.custom.jp;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.indocosmo.pos.common.PosEnvSettings;
import com.indocosmo.pos.common.enums.device.PrinterType;
import com.indocosmo.pos.common.utilities.PosCurrencyUtil;
import com.indocosmo.pos.common.utilities.PosDateUtil;
import com.indocosmo.pos.common.utilities.PosNumberUtil;
import com.indocosmo.pos.data.beans.BeanBillParam;
import com.indocosmo.pos.data.beans.BeanOrderDetail;
import com.indocosmo.pos.data.beans.BeanOrderHeader;
import com.indocosmo.pos.data.providers.terminaldb.PosDevReceiptPrinterConfigProvider;
import com.indocosmo.pos.reports.base.PosPrintableReceiptBase;
import com.indocosmo.pos.terminal.devices.PosDeviceManager;

/**
 * @author deepak
 * 
 */
public class PosPaymentReceiptReshitoNihon extends PosPrintableReceiptBase {



	private static final int ITEM_LEVEL_FIELD_WITDH =8;
	/**
	 * Description column width
	 */
	//	private static final int DESC_FIELD_WIDTH = 60;
	private static final int DESC_FIELD_WIDTH = 100;

	private static final int QTY_FIELD_WIDTH = 25;
	private static final int RATE_FIELD_WIDTH = 0;
	private static final int TOTAL_FIELD_WIDTH = 40;

	private Font mFontReceipt;
	private boolean mUseAltLanguge;
	private String mCurrency;
	private String decimalFormat="#,###";

	/**
	 * 
	 */
	public PosPaymentReceiptReshitoNihon(BeanOrderHeader order) {
		super();
		setOrder(order);
		mFontReceipt = new Font("Ariel", Font.PLAIN, 8);
		mCurrency = PosEnvSettings.getInstance().getCurrencySymbol();
		try {
			mUseAltLanguge = PosDeviceManager.getInstance().canUseAltLanguage(getPrinterType());
		} catch (Exception e) {

			e.printStackTrace();
		}
	}




	/**
	 * 
	 */
	public PosPaymentReceiptReshitoNihon() {
		mFontReceipt = new Font("Ariel", Font.PLAIN, 8);
		mCurrency = PosEnvSettings.getInstance().getCurrencySymbol();
		try {
			mUseAltLanguge = PosDeviceManager.getInstance().canUseAltLanguage(getPrinterType());
		} catch (Exception e) {

			e.printStackTrace();
		}
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

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.indocosmo.pos.reports.receipts.PosPrintableReceiptBase#printHeader
	 * (com.indocosmo.pos.data.beans.BeanOrderHeader,
	 * com.indocosmo.pos.data.beans.BeanBillParam)
	 */
	@Override
	protected void onPrintHeader(BeanOrderHeader order, BeanBillParam param)
			throws Exception {

		
		/*Print Bill number, Date And service details.*/
		
		setFontStyle(Font.BOLD);
		setFontSize(20.0f);
		printText(TextAlign.CENTER, "領 収 書");

		setFont(mFontReceipt);
		final String billNo = "領収No" + order.getOrderNoPart();
		final String billDate = PosDateUtil.getDateInJapaneaseFormat();

		printText(TextAlign.LEFT, billNo, false);
		printText(TextAlign.RIGHT, billDate);

//		drawLine(getDefaultHeaderSeparatorStyle());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.indocosmo.pos.reports.receipts.PosPrintableReceiptBase#printDetails
	 * (com.indocosmo.pos.data.beans.BeanOrderHeader)
	 */
	@Override
	protected void onPrintDetails(BeanOrderHeader order) throws Exception {

		String amountStoperSymbol="-";
		setFontStyle(Font.BOLD);
		setFontSize(20.0f);
		final String titleText ="様";
		printText(TextAlign.RIGHT, titleText);
		drawLine(getDefaultDetailSeparatorStyle());
		
		final double netPayble=PosNumberUtil.roundTo(order.getTotalAmount())
				+order.getRoundAdjustmentAmount()
				-order.getBillDiscountAmount();

		String totalAmtText = PosNumberUtil.format(netPayble,decimalFormat);
		String currencyAmoText = mCurrency + totalAmtText+amountStoperSymbol;
		printText(TextAlign.RIGHT, currencyAmoText);
		drawLine(getDefaultDetailSeparatorStyle());
		
		if(PosEnvSettings.getInstance().getBillParams().getTax()==null){

			double gstTotal= order.getTotalTax1()+
					order.getTotalTax2()+
					order.getTotalTax3()+
					order.getTotalServiceTax()+
					order.getTotalGST()-
					order.getBillTaxAmount() ;
			
			  gstTotal=PosCurrencyUtil.roundTo(gstTotal-gstTotal*mOrder.getBillDiscountPercentage()/100);
			 
			if(gstTotal>0){
				setFontStyle(Font.PLAIN);
				setFontSize(10.0f);
				printText(TextAlign.RIGHT, "税抜金額 "+mCurrency+PosNumberUtil.format(netPayble-gstTotal,decimalFormat)+amountStoperSymbol);
				printText(TextAlign.RIGHT, "消費税等 "+mCurrency+PosNumberUtil.format(gstTotal,decimalFormat)+amountStoperSymbol);
			}
		}
		String thePriceExcludingTax = "税抜金額";
		String taxAmount ="消費税等";
		
		setFontSize(10.0f);
		String receivedStatementLine1= "(但し　　　		        	         		として ";
		String receivedStatementLine2= "  　　          正に領収致しました)";
		printText(TextAlign.LEFT, receivedStatementLine1);
		printText(TextAlign.RIGHT, receivedStatementLine2);
		
//		setFont(mFontReceipt);
		advanceLine();
		printTextInRectangle(10, 40, 40, "印",false);
		printTextInRectangle(120,50, 50, "収入印紙",true);
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.indocosmo.pos.reports.receipts.PosPrintableReceiptBase#printFooter
	 * (com.indocosmo.pos.data.beans.BeanBillParam,
	 * com.indocosmo.pos.data.beans.BeanOrderHeader)
	 */
	@Override
	protected void onPrintFooter(BeanBillParam param, BeanOrderHeader order)
			throws Exception {

		/** footer information **/
		try {
			BufferedImage bi =ImageIO.read(new File("logos/reshito_logo.png"));
			printImage(bi, 0,getNextLineStartY(), 180, 56);
		} catch (IOException e) {
			//				e.printStackTrace();
		}
		
		final String hdrLine1 = param.getHeaderLine1();
		final String hdrLine2 = param.getHeaderLine2();
		final String hdrLine3 = param.getHeaderLine3();
		final String hdrLine4 = param.getHeaderLine4();
		final String foldInfo ="印刷面を内側に折って保管願います";

//		setFontStyle(Font.BOLD);
		setFontSize(10.0f);
//		setFont(mFontReceipt);
		printText(TextAlign.CENTER, hdrLine1);
		printText(TextAlign.CENTER, hdrLine2);
		printText(TextAlign.CENTER, hdrLine3);
		printText(TextAlign.CENTER, hdrLine4);
		printText(TextAlign.CENTER, foldInfo);


	}


	/* (non-Javadoc)
	 * @see com.indocosmo.pos.reports.receipts.PosPrintableReceiptBase#setOrder(com.indocosmo.pos.data.beans.BeanOrderHeader)
	 */
	@Override
	public void setOrder(BeanOrderHeader order) {
		super.setOrder(order);
	}




	/* (non-Javadoc)
	 * @see com.indocosmo.pos.reports.receipts.PosPrintableReceiptBase#onPrintBillSummary(com.indocosmo.pos.data.beans.BeanOrderHeader)
	 */
	@Override
	protected void onPrintBillSummary(BeanOrderHeader order) throws Exception {
		// TODO Auto-generated method stub
		
	}




	/* (non-Javadoc)
	 * @see com.indocosmo.pos.reports.receipts.PosPrintableReceiptBase#onPrintPaymentSummary(com.indocosmo.pos.data.beans.BeanOrderHeader)
	 */
	@Override
	protected void onPrintPaymentSummary(BeanOrderHeader order)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

}
