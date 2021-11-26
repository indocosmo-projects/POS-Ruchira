/**
 * 
 */
package com.indocosmo.pos.reports.receipts.custom.nz;
 
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.text.StyledEditorKit.BoldAction;

import com.indocosmo.pos.common.PosEnvSettings;
import com.indocosmo.pos.common.enums.device.PrinterType;
import com.indocosmo.pos.common.utilities.PosCurrencyUtil;
import com.indocosmo.pos.common.utilities.PosDateUtil;
import com.indocosmo.pos.common.utilities.PosNumberUtil;
import com.indocosmo.pos.common.utilities.PosOrderUtil;
import com.indocosmo.pos.common.utilities.PosSaleItemUtil;
import com.indocosmo.pos.common.utilities.PosStringUtil;
import com.indocosmo.pos.data.beans.BeanBillParam;
import com.indocosmo.pos.data.beans.BeanOrderDetail;
import com.indocosmo.pos.data.beans.BeanOrderHeader;
import com.indocosmo.pos.data.beans.BeanOrderPayment;
import com.indocosmo.pos.data.beans.BeanOrderPaymentHeader;
import com.indocosmo.pos.data.providers.terminaldb.PosDevReceiptPrinterConfigProvider;
import com.indocosmo.pos.reports.base.PosPaymentReceiptBase;
import com.indocosmo.pos.reports.base.PosPrintableBase.TextAlign;
import com.indocosmo.pos.terminal.devices.PosDeviceManager;
/**
 * @author jojesh
 * 
 */
public class PosefundReceiptNZ extends PosPaymentReceiptBase {

 

	/**
	 * Serial Number column width
	 */
	private static final int SLNO_FIELD_WIDTH = 0;
	/**
	 * Item level indicator
	 */
	private static final int ITEM_LEVEL_FIELD_WITDH =0;
	/**
	 * Description column width
	 */
	//	private static final int DESC_FIELD_WIDTH = 60;
	private static final int DESC_FIELD_WIDTH = 93;

	private static final int QTY_FIELD_WIDTH = 25;
	private static final int RATE_FIELD_WIDTH = 35;
	private static final int TOTAL_FIELD_WIDTH = 40;

	private static final String REPORT_HEADER_LOGO= "logos/receipt_header.png";
 	private boolean mWholeBillRefunded=false;
	private double mRefundedItemTotal;
	private String mCardNo;
	/**
	 * 
	 */
	public PosefundReceiptNZ(BeanOrderHeader order) {
		super();
		setOrder(order);
//		mFontReceipt = new Font("Ariel", Font.PLAIN, 8);
//		preparePaymentInfo(getOrder().getOrderPaymentItems());
		try {
			mUseAltLanguge = PosDeviceManager.getInstance().canUseAltLanguage(getPrinterType());
			setOverridePrinterSettings(true);
			setCopies(PosEnvSettings.getInstance().getPrintSettings().getRefundReceiptSettings().getNoCopies());
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	/**
	 * 
	 */
	public PosefundReceiptNZ() {
//		mFontReceipt = new Font("Ariel", Font.PLAIN, 8);
		try {
			mUseAltLanguge = PosDeviceManager.getInstance().canUseAltLanguage(getPrinterType());
			setOverridePrinterSettings(true);
			setCopies(PosEnvSettings.getInstance().getPrintSettings().getRefundReceiptSettings().getNoCopies());
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
//		setFont(mFontReceipt);

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

		try {
			BufferedImage bi =ImageIO.read(new File(REPORT_HEADER_LOGO));
			printImage(bi);
		} catch (IOException e) {
			//				e.printStackTrace();
		}

		setFontStyle(Font.BOLD);
		setFontSize(8.5f);
		printText(TextAlign.CENTER, hdrLine1);

		setFont(mFontReceipt);
		printText(TextAlign.CENTER, hdrLine2,true,true);
		printText(TextAlign.CENTER, hdrLine3);
		printText(TextAlign.CENTER, hdrLine4);
		printText(TextAlign.CENTER, hdrLine5);
		printText(TextAlign.CENTER, hdrLine6);
		printText(TextAlign.CENTER, hdrLine7);
		printText(TextAlign.CENTER, hdrLine8);
		printText(TextAlign.CENTER, hdrLine9);
		printText(TextAlign.CENTER, hdrLine10);

		 	final String hdrGST ="GST: " + PosEnvSettings.getInstance().getShop().getCompanyTaxNo();
			printText (TextAlign.CENTER, hdrGST);
	 
			setFontStyle(Font.BOLD);
		setFontSize(10.0f);
		String hdrLine11 = "Credit Note";
	 	
		printText(TextAlign.CENTER, hdrLine11);
		 
		setFont(mFontReceipt);
			
		final String billNo = "Inv: "  + order.getInvoiceNo();
		
		final String billDate = "Date: " + PosDateUtil.getDateTime();
		final String salesName="Sales: " + ( order.getUser()!=null? order.getUser().getName() :order.getServedBy().getName() );
		
		printText(TextAlign.LEFT , salesName);
		 
		printText(TextAlign.LEFT , billNo);
		printText(TextAlign.LEFT , billDate);
		 
		drawLine(getDefaultHeaderSeparatorStyle());
		preparePaymentInfo(mOrder.getOrderPaymentItems());
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

		if(order.getOrderDetailItems()==null || order.getOrderDetailItems().size()<=0) return;
	 	mRefundedItemTotal=0;
	 	
	 	mWholeBillRefunded=checkWholeBillRefunded(order);
	 	
//	 	printDetailsHeader();
		
		final Font dtlsFont=PosEnvSettings.getInstance().getPrintSettings().getPaymentReceiptSettings().getItemDetailFont();
		
		setFont(dtlsFont);
 		
		int slNo = 1;
		for (BeanOrderDetail dtl : order.getOrderDetailItems()) {

			if(dtl.isVoid()) continue;

			printDetailItem(slNo, dtl);	
		}
	
		advanceLine(1);
		printDashedLine();
		
		printText(TextAlign.LEFT , String.valueOf( order.getOrderDetailItems().size()) + ((order.getOrderDetailItems().size()>1)?" Items.":" Item." ));
		
		advanceLine(1);
		printDashedLine();
		
		
		setFont(mFontReceipt);
		
	}
 
	/**
	 * Prints the header for details
	 */
	protected void printDetailsHeader() {

		final int gap = 1;
		int left = 0;

		/** The Serial number field **/
		printTextBlock(left, SLNO_FIELD_WIDTH, "SL.", TextAlign.CENTER, false);

		/** The item level indicator **/
		left += SLNO_FIELD_WIDTH + gap;
		printTextBlock(left, ITEM_LEVEL_FIELD_WITDH, "", TextAlign.CENTER, false);

		/** The Quantity field **/
		left += ITEM_LEVEL_FIELD_WITDH + gap;
		printTextBlock(left, QTY_FIELD_WIDTH, "Qty", TextAlign.LEFT, false);

		/** The Description field **/
		left += QTY_FIELD_WIDTH + gap;
		printTextBlock(left, DESC_FIELD_WIDTH, "Description", TextAlign.CENTER,
				false);

		/** The Rate field **/
		left += DESC_FIELD_WIDTH + gap;
		printTextBlock(left, RATE_FIELD_WIDTH, " ", TextAlign.CENTER, false);

		/** The Total Amount field **/
		left += RATE_FIELD_WIDTH + gap;
		printTextBlock(left, TOTAL_FIELD_WIDTH, "Total", TextAlign.CENTER, true);
		printDashedLine();
	}

	/**
	 * Prints the details part
	 */
	protected void printDetailItem(int srNo, BeanOrderDetail dtl) {

	 	final int gap = 1;

		int left = 0;
		/** The Serial number field **/
		printTextBlock(left, SLNO_FIELD_WIDTH, String.valueOf(srNo),
				TextAlign.RIGHT, false);

		/** The Description field **/
		left += SLNO_FIELD_WIDTH + gap;
		printTextBlock(left, DESC_FIELD_WIDTH,    PosSaleItemUtil.getItemNameToPrint(dtl.getSaleItem(), mUseAltLanguge) , TextAlign.LEFT, true);

		//in next line qty , rate and price
		/** The Quantity field **/
		left += SLNO_FIELD_WIDTH + gap;

		String  indiChar="-";
	 	printTextBlock(left, QTY_FIELD_WIDTH,
	 			indiChar + PosNumberUtil.format(PosOrderUtil.getItemQuantity(dtl)), TextAlign.LEFT, false);
	 
	  
		/** The Rate field **/
		left += QTY_FIELD_WIDTH      + gap;
		printTextBlock(left,DESC_FIELD_WIDTH+  RATE_FIELD_WIDTH,"@ " + 
				PosEnvSettings.getInstance().getCurrencySymbol() +
				PosCurrencyUtil.format(PosOrderUtil
						.getItemFixedPrice(dtl)), TextAlign.LEFT, false);
		
		/** The Total Amount field **/
		left += DESC_FIELD_WIDTH+  RATE_FIELD_WIDTH + gap;
		printTextBlock(left, TOTAL_FIELD_WIDTH,
				indiChar +PosEnvSettings.getInstance()
				.getCurrencySymbol()  +   
				PosCurrencyUtil.format(mWholeBillRefunded? dtl.getSaleItemPrice(): dtl.getRefundAmount()),
				TextAlign.RIGHT, true);
		
		mRefundedItemTotal+=mWholeBillRefunded? dtl.getSaleItemPrice(): dtl.getRefundAmount();
	 
	}
 
	 

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.indocosmo.pos.reports.receipts.PosPrintableReceiptBase#onPrintBillSummary
	 * (com.indocosmo.pos.data.beans.BeanOrderHeader)
	 */
	@Override
	protected void onPrintBillSummary(BeanOrderHeader order) throws Exception {
 

		final String indiChar="-" + PosEnvSettings.getInstance().getCurrencySymbol() ;
		final double totalRefundPaid=totalCashPayment + totalCardPayment + totalCompanyPayment + totalCouponPayment;
		String roundingAmtText="0";
	
		roundingAmtText= PosCurrencyUtil.format(totalRefundPaid- mRefundedItemTotal);
		 
		if(PosNumberUtil.parseDoubleSafely(roundingAmtText) !=0   ){
			printText(TextAlign.LEFT, "Rounding adjustment :", false);
			printText(TextAlign.RIGHT,     roundingAmtText);
	
		}
			
		printText(TextAlign.LEFT, "REFUNDED TOTAL: ",false);
		printText(TextAlign.RIGHT, indiChar +  PosCurrencyUtil.format(totalRefundPaid));
		
		printPaymentInfo("CASH :", totalCashPayment );
 		printPaymentInfo("CARD " + mCardNo + " :", totalCardPayment );
		printPaymentInfo("COMPANY :", totalCompanyPayment );
		printPaymentInfo("VOUCHER :", totalCouponPayment );
		
		final double totalTax=order.getTotalTax1() +order.getTotalTax2() +order.getTotalTax3() + order.getTotalGST();
	 	
	 	if (totalTax>0){
	 		
		 	printText(TextAlign.LEFT, "INCLUDE TAX:",false);
			printText(TextAlign.RIGHT,  indiChar +  PosCurrencyUtil.format(totalTax));
	 	}
 
			
		 	
		advanceLine();
		drawLine(getDefaultHeaderSeparatorStyle());

		printRemarks();
		
		
		printText(TextAlign.LEFT ,"Customer Name :________________________________",true);
		printText(TextAlign.LEFT ,"Contact Ph.No. :________________________________________",true);
		printText(TextAlign.LEFT ,"Customer Signature :_____________________________",true);
		
		advanceLine();
		printDashedLine();
	}
	/**
	 * @param paymenTitle
	 * @param amount
	 * @param blockLeft
	 * @param blockWidth
	 */
	private boolean printPaymentInfo(String paymenTitle, double amount){

		boolean isPrinted=false;
		final String indiChar="-" + PosEnvSettings.getInstance().getCurrencySymbol() ;
		if(amount!=0){

			printText(TextAlign.LEFT, paymenTitle,false);
			printText(TextAlign.RIGHT,  indiChar +  PosCurrencyUtil.format(amount));
			
			 
			isPrinted=true;
		}
 
		return isPrinted;
	}
	/**
	 * 
	 */
	private void printRemarks(){
		
		if (mOrder.getOrderPaymentHeaders()==null) return;
		  
		String remarks="";
		for(BeanOrderPaymentHeader ordPaymentHdr:  mOrder.getOrderPaymentHeaders()){
			
			if (ordPaymentHdr.getRemarks()!=null && ordPaymentHdr.getRemarks().length()>0)
				remarks=remarks + " " + ordPaymentHdr.getRemarks();
		}
 
		
		if(remarks!=null && remarks.trim().length()>0){
			
			setFontStyle(Font.BOLD);
			setFontSize(9f);
			
			printText(TextAlign.LEFT, "Remarks:");
			
			setFont(mFontReceipt);
			
			final String[] remarksToPrint=PosStringUtil.getWrappedLines(remarks, 52);
			
			for(String remark:remarksToPrint){
				
				printText(TextAlign.LEFT, remark);
			}
			
			printText(TextAlign.CENTER, " ",true,true);
			
			advanceLine();
			printDashedLine();
			advanceLine();
		}
	}

		/*
	 * (non-Javadoc)
	 * 
	 * @see com.indocosmo.pos.reports.receipts.PosPrintableReceiptBase#
	 * printPaymentSummary(com.indocosmo.pos.data.beans.BeanOrderHeader)
	 */
	@Override
	protected void onPrintPaymentSummary(BeanOrderHeader order)
			throws Exception {
 
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
		
		printText(TextAlign.LEFT, "Please Retain This Docket For Exchange Purpose",true);
		printText(TextAlign.LEFT, "Within 7 Days",true);
		advanceLine(3);
		if (isBillPrinting)
		{
			final String footerLine_bill = "--THANK YOU--";
			printText(TextAlign.CENTER, footerLine_bill,true);
			return;
			
		}
		/** footer information **/
		final String footerLine_1 = param.getFooterLine1();
		final String footerLine_2 = param.getFooterLine2();
		final String footerLine_3 = param.getFooterLine3();
		final String footerLine_4 = param.getFooterLine4();
		final String footerLine_5 = param.getFooterLine5();
		final String footerLine_6 = param.getFooterLine6();
		final String footerLine_7 = param.getFooterLine7();
		final String footerLine_8 = param.getFooterLine8();
		final String footerLine_9 = param.getFooterLine9();
		final String footerLine_10 = param.getFooterLine10();

		printText(TextAlign.CENTER, footerLine_1,true,true);
		printText(TextAlign.CENTER, footerLine_2,true,true);
		printText(TextAlign.CENTER, footerLine_3,true,true);
		printText(TextAlign.CENTER, footerLine_4,true,true);
		printText(TextAlign.CENTER, footerLine_5,true,true);
		printText(TextAlign.CENTER, footerLine_6,true,true);
		printText(TextAlign.CENTER, footerLine_7,true,true);
		printText(TextAlign.CENTER, footerLine_8,true,true);
		printText(TextAlign.CENTER, footerLine_9,true,true);
		printText(TextAlign.CENTER, footerLine_10,true,true);


		printText(TextAlign.CENTER, ".",true,true);
		//		printText(TextAlign.CENTER, ".");
		//		printText(TextAlign.CENTER, ".");

	}

 
 
	/* (non-Javadoc)
	 * @see com.indocosmo.pos.reports.receipts.PosPrintableReceiptBase#setOrder(com.indocosmo.pos.data.beans.BeanOrderHeader)
	 */
	@Override
	public void setOrder(BeanOrderHeader order) {
		super.setOrder(order);

//		preparePaymentInfo(getOrder().getOrderPaymentItems());
	}
	/**
	 * @param paymentDetails
	 */
	private void preparePaymentInfo(ArrayList<BeanOrderPayment> paymentDetails){

		totalCashPayment=0.0;
		totalCardPayment=0.0;
		totalCompanyPayment=0.0;
		totalCouponPayment=0.0;
 
 
		mCardNo="";
		 for (BeanOrderPayment payment : paymentDetails) {
			
			if(payment.isRepayment()){
			
				
				
				switch (payment.getPaymentMode()) {
				case Cash:
					if (payment.getPaidAmount() > 0){
						totalCashPayment+=payment.getPaidAmount();
					}
					break;
				case Card:
					if (payment.getPaidAmount() > 0){
						totalCardPayment+=payment.getPaidAmount();
						
						if (payment.getCardNo()!=null  && payment.getCardNo().trim()!=""){
							mCardNo +=( ( mCardNo.trim()!="")?",":"") + "****";
							mCardNo += (payment.getCardNo().trim().length()<=4? 
									payment.getCardNo(): 
									payment.getCardNo().trim().substring(payment.getCardNo().trim().length()-4));
							
						}
						
					}
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
				
				default:
					break;
				}
			}
		}
		
		mCardNo = ( mCardNo.trim()!="")?"(" + mCardNo + ")":"";
		

	}
	 

	/*
	 * 
	 */
	private boolean  checkWholeBillRefunded(BeanOrderHeader order){
		
		boolean result=false;
		final double totalBillAmount= mOrder.getTotalAmountPaid()-mOrder.getChangeAmount();//  PosOrderUtil.getTotalPaidAmount(order);
		
		final double roundingAmt=( totalCashPayment + totalCardPayment + totalCompanyPayment + totalCouponPayment)  - totalBillAmount;
				 
		if(PosCurrencyUtil.nRound(roundingAmt)==0){
			order.setRoundAdjustmentAmount(roundingAmt);
			result=true;
		}
	 	return result;
	 	
	 
	}
}
