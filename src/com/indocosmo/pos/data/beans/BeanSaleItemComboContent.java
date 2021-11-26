/**
 * 
 */
package com.indocosmo.pos.data.beans;

import java.util.ArrayList;

import javax.swing.KeyStroke;

/**
 * @author joe.12.3
 *
 */
public class BeanSaleItemComboContent extends BeanMasterBase {

	private int comboContentId;
	private int saleItemId;
	private double maxItems;
	private BeanUOM uom;
	private ArrayList<BeanSaleItemComboContentSubstitute> contentItems;

	/**
	 * @return the quantity
	 */
	public double getMaxItems() {
		return maxItems;
	}

	/**
	 * @param count the quantity to set
	 */
	public void setMaxItems(double count) {
		this.maxItems = count;
	}

	/**
	 * @return the uom
	 */
	public BeanUOM getUom() {
		return uom;
	}

	/**
	 * @param uom the uom to set
	 */
	public void setUom(BeanUOM uom) {
		this.uom = uom;
	}

	/**
	 * @return the contentItems
	 */
	public ArrayList<BeanSaleItemComboContentSubstitute> getContentItems() {
		return contentItems;
	}

	/**
	 * @param contentItems the contentItems to set
	 */
	public void setContentSubstitutes(ArrayList<BeanSaleItemComboContentSubstitute> contentItems) {
		this.contentItems = contentItems;
	}

	/**
	 * 
	 */
	public BeanSaleItemComboContent() {
		// TODO Auto-generated constructor stub
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

	/**
	 * @return the saleItemId
	 */
	public int getSaleItemId() {
		return saleItemId;
	}

	/**
	 * @param saleItemId the saleItemId to set
	 */
	public void setSaleItemId(int saleItemId) {
		this.saleItemId = saleItemId;
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
