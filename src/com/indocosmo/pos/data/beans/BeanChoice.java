/**
 * 
 */
package com.indocosmo.pos.data.beans;

import java.util.ArrayList;

import javax.swing.KeyStroke;


/**
 * @author joe.12.3
 * Bean for choice
 * created on 01/07/2014
 */
public class BeanChoice extends BeanMasterBase implements Cloneable{
	
	private ArrayList<BeanSaleItem> subItems;
	private boolean isExtra=false;
	/**
	 * @return the isExtra
	 */
	public boolean isGlobal() {
		return isExtra;
	}

	/**
	 * @param isExtra the isExtra to set
	 */
	public void setExtra(boolean isExtra) {
		this.isExtra = isExtra;
	}

	/**
	 * @return the saleItems
	 */
	public ArrayList<BeanSaleItem> getSaleItems() {
		return subItems;
	}

	/**
	 * @param saleItems the saleItems to set
	 */
	public void setSaleItems(ArrayList<BeanSaleItem> subItems) {
		this.subItems = subItems;
	}
	
	public BeanChoice clone() throws CloneNotSupportedException {
		BeanChoice cloneObject = null;
		
		cloneObject = (BeanChoice) super.clone();
		if (subItems != null) {
			ArrayList<BeanSaleItem> newSubItems = new ArrayList<BeanSaleItem>();
			for (BeanSaleItem item : subItems)
				newSubItems.add(item.clone());
			cloneObject.setSaleItems(newSubItems);
		}
		return cloneObject;
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
