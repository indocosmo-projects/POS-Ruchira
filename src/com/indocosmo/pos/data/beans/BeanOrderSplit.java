/**
 * 
 */
package com.indocosmo.pos.data.beans;

import java.util.ArrayList;

import javax.swing.KeyStroke;

import com.indocosmo.pos.common.enums.split.SplitBasedOn;
import com.indocosmo.pos.forms.components.search.IPosSearchableItem;

/**
 * @author jojesh-13.2
 *
 */
public class BeanOrderSplit implements Cloneable, IPosSearchableItem {
	
	private String id;
	private  int splitNo;
	private String orderID;
	private SplitBasedOn basedOn;
	private double value;
	private String description;
	private double amount;
	private ArrayList<BeanOrderSplitDetail> splitDetails;
	private ArrayList<BeanOrderPayment> splitPayments;
	private double adjustAmount;
	private boolean isPayed=false;
	private double payedAmount;
	private double discount;
	private double roundingAdj;
	private double partPayAdjustment;

	/**
	 * The actual amount payed by the customer after discount rounding and all
	 * @return the payedAmount
	 */
	public double getPayedAmount() {
		return payedAmount;
	}


	/**
	 * The actual amount payed by the customer after discount rounding and all
	 * @param payedAmount the payedAmount to set
	 */
	public void setPayedAmount(double payedAmount) {
		this.payedAmount = payedAmount;
	}


	/**
	 * 
	 */
	public BeanOrderSplit() {
		// TODO Auto-generated constructor stub
	}


	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}


	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}


	/**
	 * @return the split no
	 */
	public int getSplitNo() {
		return splitNo ;
	}


	/**
	 * @param id the id to set
	 */
	public void setSplitNo(int splitNo) {
		this.splitNo  = splitNo ;
	}

	
	/**
	 * @return the orderID
	 */
	public String getOrderID() {
		return orderID;
	}


	/**
	 * @param orderID the orderID to set
	 */
	public void setOrderID(String orderID) {
		this.orderID = orderID;
	}


	/**
	 * @return the basedOn
	 */
	public SplitBasedOn getBasedOn() {
		return basedOn;
	}


	/**
	 * @param selectedSplitBasedOn the basedOn to set
	 */
	public void setBasedOn(SplitBasedOn selectedSplitBasedOn) {
		this.basedOn = selectedSplitBasedOn;
	}


	/**
	 * The split value based on split is done. 
	 * Could be an amount count percentage etc.
	 * @return the value
	 */
	public double getValue() {
		return value;
	}


	/**
	 * The split value based on split is done. 
	 * Could be an amount count percentage etc.
	 * @param value the value to set
	 */
	public void setValue(double value) {
		this.value = value;
	}


	/**
	 * @return the splitDetails
	 */
	public ArrayList<BeanOrderSplitDetail> getSplitDetails() {
		return splitDetails;
	}


	/**
	 * @param splitDetails the splitDetails to set
	 */
	public void setSplitDetails(ArrayList<BeanOrderSplitDetail> splitDetails) {
		this.splitDetails = splitDetails;
	}


	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}


	/**
	 * @param name the description to set
	 */
	public void setDescription(String name) {
		this.description = name;
	}


	/**
	 * The amount for this split.
	 * But not the actual payed amount.
	 * It can be changed at the payment screen when rounding and bill discounts are applied
	 * @return the amount
	 */
	public double getAmount() {
		return amount;
	}


	/**
	 * The amount for this split.
	 * But not the actual payed amount.
	 * It can be changed at the payment screen when rounding and bill discounts are applied
	 * @param amount the amount to set
	 */
	public void setAmount(double amount) {
		this.amount = amount;
	}

	/**
	 * @return
	 */
	public double getAdjustAmount() {

		return adjustAmount;
	}
	
	/**
	 * @return
	 */
	public void setAdjustAmount(double amount) {

		adjustAmount=amount;
	}


	/**
	 * @return
	 */
	public double getActualAmount() {

		return getAmount()+getAdjustAmount();
	}
	
	@Override
	public BeanOrderSplit clone() {

		BeanOrderSplit cloneObject = null;

		try {
			cloneObject = (BeanOrderSplit) super.clone();
			/**
			 * cloning details items will break parent child relation
			 * 
			 * */
			if (splitDetails != null) {
				ArrayList<BeanOrderSplitDetail> newSplitDetails = new ArrayList<BeanOrderSplitDetail>();
				for (BeanOrderSplitDetail item : splitDetails)
					newSplitDetails.add(item.clone());
				cloneObject.setSplitDetails(newSplitDetails);
			}
		} catch (CloneNotSupportedException e) {

		}

		return cloneObject;
	}


	/**
	 * @return the isPayed
	 */
	public boolean isPayed() {
		return isPayed;
	}


	/**
	 * @param isPayed the isPayed to set
	 */
	public void setPayed(boolean isPayed) {
		this.isPayed = isPayed;
	}


	/**
	 * @return the discount
	 */
	public double getDiscountAmount() {
		return discount;
	}


	/**
	 * @param discount the discount to set
	 */
	public void setDiscount(double discount) {
		this.discount = discount;
	}


	/**
	 * @return the roundingAdj
	 */
	public double getRoundingAdjustment() {
		return roundingAdj;
	}


	/**
	 * @param roundingAdj the roundingAdj to set
	 */
	public void setRoundingAdjustment(double rounding) {
		this.roundingAdj = rounding;
	}

	public static String[] ORDER_RETRIEVE_SEARCH_FIELD_LIST = { "getDescription","getItemCount",
		"getActualAmount"};
	public static String[] ORDER_RETRIEVE_SEARCH_COLUMN_NAMES = { "Name","Item Count",
			"Amount" };
	public static int[] ORDER_RETRIEVE_SEARCH_FIELD_WIDTH = { 300,200,};


	/* (non-Javadoc)
	 * @see com.indocosmo.pos.forms.components.objectbrowser.IPosBrowsableItem#getItemCode()
	 */
	@Override
	public Object getItemCode() {

		return id;
	}


	/* (non-Javadoc)
	 * @see com.indocosmo.pos.forms.components.objectbrowser.IPosBrowsableItem#getDisplayText()
	 */
	@Override
	public String getDisplayText() {

		return description;
	}


	/* (non-Javadoc)
	 * @see com.indocosmo.pos.forms.components.search.IPosSearchableItem#getFiledList()
	 */
	@Override
	public String[] getFieldList() {

		return ORDER_RETRIEVE_SEARCH_FIELD_LIST;
	}


	/* (non-Javadoc)
	 * @see com.indocosmo.pos.forms.components.search.IPosSearchableItem#getFiledTitleList()
	 */
	@Override
	public String[] getFieldTitleList() {

		return ORDER_RETRIEVE_SEARCH_COLUMN_NAMES;
	}


	/* (non-Javadoc)
	 * @see com.indocosmo.pos.forms.components.search.IPosSearchableItem#getFiledWidthList()
	 */
	@Override
	public int[] getFieldWidthList() {

		return ORDER_RETRIEVE_SEARCH_FIELD_WIDTH;
	}
	
	public int getItemCount(){
		
		return (splitDetails!=null)?splitDetails.size():0;
	}


	/**
	 * @return the splitPayments
	 */
	public ArrayList<BeanOrderPayment> getSplitPayments() {
		return splitPayments;
	}


	/**
	 * @param splitPayments the splitPayments to set
	 */
	public void setSplitPayments(ArrayList<BeanOrderPayment> splitPayments) {
		this.splitPayments = splitPayments;
	}
	
	/* (non-Javadoc)
	 * @see com.indocosmo.pos.forms.components.objectbrowser.IPosBrowsableItem#isVisibleInUI()
	 */
	@Override
	public boolean isVisibleInUI() {
		// TODO Auto-generated method stub
		return true;
	}


	/**
	 * The part payment adjustment
	 * @return
	 */
	public double getPartPayAdjustment() {

		return this.partPayAdjustment;
	}


	/**
	 * The part payment adjustment
	 * @param partPayAdjustment the partPayAdjustment to set
	 */
	public void setPartPayAdjustment(double partPayAdjustment) {
		this.partPayAdjustment = partPayAdjustment;
	}


	/* (non-Javadoc)
	 * @see com.indocosmo.pos.forms.components.search.IPosSearchableItem#getFiledFormatList()
	 */
	@Override
	public String[] getFieldFormatList() {
		// TODO Auto-generated method stub
		return null;
	}


	/* (non-Javadoc)
	 * @see com.indocosmo.pos.forms.components.objectbrowser.IPosBrowsableItem#getKeyStroke()
	 */
	@Override
	public KeyStroke getKeyStroke() {
		// TODO Auto-generated method stub
		return null;
	}

}
