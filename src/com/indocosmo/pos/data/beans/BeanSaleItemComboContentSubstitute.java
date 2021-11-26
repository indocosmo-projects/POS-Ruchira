/**
 * 
 */
package com.indocosmo.pos.data.beans;

import com.indocosmo.pos.common.PosLog;

/**
 * @author joe.12.3
 *
 */
public class BeanSaleItemComboContentSubstitute implements Cloneable{

	private int id;
	private int comboContentId;
	private BeanSaleItem saleItem;
	private double priceDifferance;
	private boolean isDefault;
	private double quantity;

	/**
	 * @return the quantity
	 */
	public double getQuantity() {
		return quantity;
	}

	/**
	 * @param quantity the quantity to set
	 */
	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the saleItem
	 */
	public BeanSaleItem getSaleItem() {
		return saleItem;
	}

	/**
	 * @param saleItem the saleItem to set
	 */
	public void setSaleItem(BeanSaleItem saleItem) {
		this.saleItem = saleItem;
	}

	/**
	 * @return the priceDifferance
	 */
	public double getPriceDifferance() {
		return priceDifferance;
	}

	/**
	 * @param priceDifferance the priceDifferance to set
	 */
	public void setPriceDifferance(double priceDifferance) {
		this.priceDifferance = priceDifferance;
	}

	/**
	 * @return the isDefault
	 */
	public boolean isDefault() {
		return isDefault;
	}

	/**
	 * @param isDefault the isDefault to set
	 */
	public void setDefault(boolean isDefault) {
		this.isDefault = isDefault;
	}

	/**
	 * 
	 */
	public BeanSaleItemComboContentSubstitute() {
		isDefault=false;
	}
	
	public BeanSaleItemComboContentSubstitute clone() {
		BeanSaleItemComboContentSubstitute cloneObject = null;
		try {
			cloneObject = (BeanSaleItemComboContentSubstitute) super.clone();
			cloneObject.setSaleItem(saleItem.clone());
		} catch (Exception e) {
			PosLog.write(this, "clone", e);
		}
		return cloneObject;
	}

	/**
	 * @return the comboContentId
	 */
	public int getComboContentId() {
		return comboContentId;
	}

	/**
	 * @param comboContentId the comboContentId to set
	 */
	public void setComboContentId(int comboContentId) {
		this.comboContentId = comboContentId;
	}
	

}
