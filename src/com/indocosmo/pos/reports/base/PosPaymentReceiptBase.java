/**
 * 
 */
package com.indocosmo.pos.reports.base;

import com.indocosmo.pos.common.beans.BeanBillPaymentSummaryInfo;


/**
 * @author jojesh-13.2
 *
 */
public abstract class PosPaymentReceiptBase extends PosPrintableReceiptBase{
	
	protected boolean isBillPrinting=false;
	protected BeanBillPaymentSummaryInfo currentBillInfo;
	
//	protected Font mFontReceipt;
	protected boolean mPrePaymentReceipt;

	protected double totalAdvanceCash=0.0;
	protected double totalAdvanceCard=0.0;
	protected double totalAdvanceOnline=0.0;
	protected double totalCashPayment=0.0;
	protected double totalOnlinePayment=0.0;
	protected double totalCardPayment=0.0;
	protected double totalCashOutPayment=0.0;
	protected double totalCompanyPayment=0.0;
	protected double totalCouponPayment=0.0;
	protected double totalBalancePayment=0.0;
	protected double totalCouponBalancePayment=0.0;
	protected double totalSplitPartAdjPayment=0.0;
	protected double totalSplitAdjustment=0.0;
	
	public PosPaymentReceiptBase(){
		
		super();
		
	}

	/**
	 * @param isPrintingBill the isPrintingBill to set
	 */
	public void setBillPrinting(boolean isBill) {

		this.isBillPrinting = isBill;
	}
	
	/**
	 * @param currentBillInfo
	 */
	public void setPreBillPaymentInfo(BeanBillPaymentSummaryInfo currentBillInfo) {

		this.currentBillInfo=currentBillInfo;
	}

}
