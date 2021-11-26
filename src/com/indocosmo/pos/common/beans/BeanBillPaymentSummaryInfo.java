/**
 * 
 */
package com.indocosmo.pos.common.beans;


/**
 * @author jojesh-13.2
 *
 */
public class BeanBillPaymentSummaryInfo {
	
	private String number;
	private double amount;
	private double roundAdjustment;
	private double tenderedAmount;
	private double balanceToPay;
	private double partAmount; 
	private boolean isPartialPayment;
	private double discountedAmount;
	/**
	 * 
	 * This is the amount which will be adjusted in the payment form.
	 * 
	 */
	private double partPayAdjustment;
	/**
	 * 
	 * This is the amount which will be adjusted in the split selection screen
	 * 
	 */
	private double splitPayAdjustment;
	private double unPaidAmount;

	/**
	 * 
	 */
	public BeanBillPaymentSummaryInfo() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the number
	 */
	public String getNumber() {
		return number;
	}

	/**
	 * @param number the number to set
	 */
	public void setNumber(String number) {
		this.number = number;
	}

	/**
	 * @return the amount
	 */
	public double getAmount() {
		return amount;
	}

	/**
	 * @param amount the amount to set
	 */
	public void setAmount(double amount) {
		this.amount = amount;
	}

	/**
	 * @return the roundAdjustment
	 */
	public double getRoundAdjustment() {
		return roundAdjustment;
	}

	/**
	 * @param roundAdjustment the roundAdjustment to set
	 */
	public void setRoundAdjustment(double roundAdjustment) {
		this.roundAdjustment = roundAdjustment;
	}

	/**
	 * @return the balanceToPay
	 */
	public double getChangeAmount() {
		return balanceToPay;
	}

	/**
	 * @param balanceToPay the balanceToPay to set
	 */
	public void setChangeAmount(double balanceToPay) {
		this.balanceToPay = balanceToPay;
	}

	/**
	 * @return the tenderedAmount
	 */
	public double getTenderedAmount() {
		return tenderedAmount;
	}

	/**
	 * @param tenderedAmount the tenderedAmount to set
	 */
	public void setTenderedAmount(double tenderedAmount) {
		this.tenderedAmount = tenderedAmount;
	}

	/**
	 * @return the billBalance
	 */
	public double getPartAmount() {
		return partAmount;
	}

	/**
	 * @param billBalance the billBalance to set
	 */
	public void setPartAmount(double amount) {
		this.partAmount = amount;
	}

	/**
	 * @return the isPartialPayment
	 */
	public boolean isPartialPayment() {
		return isPartialPayment;
	}

	/**
	 * @param isPartialPayment the isPartialPayment to set
	 */
	public void setPartialPayment(boolean isPartialPayment) {
		this.isPartialPayment = isPartialPayment;
	}

//	/**
//	 * @return the paymentItems
//	 */
//	public ArrayList<BeanOrderPayment> getPaymentItems() {
//		return paymentItems;
//	}
//
//	/**
//	 * @param paymentItems the paymentItems to set
//	 */
//	public void setPaymentItems1(ArrayList<BeanOrderPayment> paymentItems) {
//		this.paymentItems = paymentItems;
//	}

	/**
	 * @return
	 */
	public double getDiscountedAmount() {
		
		return this.discountedAmount;
	}

	/**
	 * @param discountedAmount the discountedAmount to set
	 */
	public void setDiscountedAmount(double discountedAmount) {
		
		this.discountedAmount = discountedAmount;
	}

	/**
	 * 
	 * This is the amount which will be adjusted in the payment form.
	 * 
	 * @param mSplitPartPayAdj
	 */
	public void setPartPayAdjustment(double partPayAdj) {

		this.partPayAdjustment=partPayAdj;
	}


	/**
	 * 
	 * This is the amount which will be adjusted in the payment form.
	 * 
	 * @return the partPayAdjustment
	 */
	public double getPartPayAdjustment() {
		return partPayAdjustment;
	}

	/**
	 * @return
	 */
	public double getUnPaidAmount() {
		// TODO Auto-generated method stub
		return this.unPaidAmount;
	}

	/**
	 * @param unPaidAmount the unPaidAmount to set
	 */
	public void setUnPaidAmount(double unPaidAmount) {
		this.unPaidAmount = unPaidAmount;
	}

	/**
	 * @return the splitPayAdjustment
	 */
	public double getSplitPayAdjustment() {
		return splitPayAdjustment;
	}

	/**
	 * @param splitPayAdjustment the splitPayAdjustment to set
	 */
	public void setSplitPayAdjustment(double splitPayAdjustment) {
		this.splitPayAdjustment = splitPayAdjustment;
	}

	 

}
