/**
 * 
 */
package com.indocosmo.pos.data.beans;

import com.indocosmo.pos.common.utilities.PosOrderSplitUtil;
import com.indocosmo.pos.common.utilities.PosOrderUtil;
import com.indocosmo.pos.common.utilities.PosStringUtil;


/**
 * @author jojesh-13.2
 *
 */
public class BeanOrderSplitDetail implements Cloneable{

	private String id;
	private String splitID;
	private double orderDetailQty;
	private double orderDetailPrice;
	private String billName;
	private BeanOrderDetail orderDetailItem;
	private String orderId;
	private String orderDetailID;
	private String orderDetailSubID;
	private boolean isSelected=false;
	private boolean isPaid=false;

	/**
	 * @return the saleItem
	 */
	public BeanOrderDetail getOrderDetailtem() {
		return orderDetailItem;
	}

	/**
	 * @param saleItem the saleItem to set
	 */
	public void setOrderDetailItem(BeanOrderDetail item) {

		orderDetailID=item.getId();
		this.orderDetailItem = item;
	}

	/**
	 * @return
	 */
	public String getItemName(){
		return PosOrderUtil.getDescriptiveItemName(orderDetailItem );
	}

	/**
	 * @return
	 */
	public String getTableSeat(){

		final String value=(orderDetailItem.getServingTable()!=null)?
				
				orderDetailItem.getServingTable().getCode() + 
					((orderDetailItem.getServingSeat()>0)?
									"/"+PosStringUtil.paddLeft(String.valueOf(orderDetailItem.getServingSeat()),2,'0')
									:"")
				
				:"";

				return  value;
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
	 * @return the headerID
	 */
	public String getSplitId() {
		return splitID;
	}


	/**
	 * @param headerID the headerID to set
	 */
	public void setSplitID(String headerID) {
		this.splitID = headerID;
	}

	/**
	 * @return the orderDetailID
	 */
	public String getOrderDetailItemID() {

		return orderDetailID;
	}


	/**
	 * @param orderDetailID the orderDetailID to set
	 */
	public void setOrderDetailItemID(String orderDetailID) {

		this.orderDetailID = orderDetailID;
	}


	/**
	 * @return the orderDetailQty
	 */
	public double getQuantity() {

		return orderDetailQty;
	}


	/**
	 * @param orderDetailQty the orderDetailQty to set
	 */
	public void setQuantity(double quantity) {

		this.orderDetailQty = quantity;
		/**
		 * If the split contains order detail item
		 * Set the split/share quantity of the order detail item for further calculation
		 * 
		 */
		if(this.orderDetailItem!=null)
			PosOrderSplitUtil.setSplitItemQuantity(this, quantity);
		//			orderDetailItem.setItemSplitShareQty(quantity);
	}


	/**
	 * @return the orderDetailPrice
	 */
	public double getPrice() {
		return orderDetailPrice;
	}


	/**
	 * @param orderDetailPrice the orderDetailPrice to set
	 */
	public void setPrice(double price) {

		this.orderDetailPrice = price;
	}

	@Override
	public BeanOrderSplitDetail clone() throws CloneNotSupportedException {

		BeanOrderSplitDetail obj=(BeanOrderSplitDetail)super.clone();
		if(this.orderDetailItem!=null)
			obj.setOrderDetailItem(this.orderDetailItem.clone());
		return obj;
	}

	/**
	 * @return the orderId
	 */
	public String getOrderId() {
		return orderId;
	}

	/**
	 * @param orderId the orderId to set
	 */
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	/**
	 * @return the billName
	 */
	public String getSplitName() {
		return billName;
	}

	/**
	 * @param billName the billName to set
	 */
	public void setSplitName(String billName) {
		this.billName = billName;
	}

	/**
	 * @return the isSelected
	 */
	public boolean isSelected() {
		return isSelected;
	}

	/**

	 * @param isSelected the isSelected to set
	 */
	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}

	/**
	 * This is the id for the split item generated from the order detail item.
	 * Same Order detais item can be splitt in to one or more sub items based on the quantity.
	 * @return the orderDetailSubID
	 */
	public String getOrderDetailSubID() {

		return orderDetailSubID;
	}

	/**
	 * This is the id for the split item generated from the order detail item.
	 * Same Order detais item can be splitt in to one or more sub items based on the quantity 
	 * @param orderDetailSubID the order Detail Sub ID to set
	 */
	public void setOrderDetailSubID(String orderDetailSubID) {

		this.orderDetailSubID = orderDetailSubID;
	}

	/**
	 * @return the isPaid
	 */
	public boolean isPaid() {
		return isPaid;
	}

	/**
	 * @param isPaid the isPaid to set
	 */
	public void setPaid(boolean isPaid) {
		this.isPaid = isPaid;
	}

	/**
	 * Return the split/share for this item.
	 * If this item contains an order detail item get it from order detail item else return 1
	 * @return
	 */
	public double getSplitShare() {

		return	(this.orderDetailItem!=null) ?
				orderDetailItem.getItemSplitShare():1;


	}

}
