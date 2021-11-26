/**
 * @author jojesh
 *This class will act as a structure from pos items super class .
 *Add required additional properties and functionalities for superclass.
 */

package com.indocosmo.pos.data.beans;

import java.util.ArrayList;

/**
 * @author jojesh
 *
 */
public final class BeanSubClass extends BeanItemClassBase {
	
	private ArrayList<BeanSaleItem> mItemList;
	private int mPrintingOrder;
	
	public int getPrintingOrder() {
		return mPrintingOrder;
	}

	/**
	 * @param mPrintingOrder the mPrintingOrder to set
	 */
	public void setPrintingOrder(int mPrintingOrder) {
		this.mPrintingOrder = mPrintingOrder;
	}
	/**
	 * 
	 */
	
	public BeanSubClass() {
		// TODO Auto-generated constructor stub
	}
	

	public ArrayList<BeanSaleItem> getItemList() {
		return mItemList;
	}

	public void setPosItemList(ArrayList<BeanSaleItem> itemList) {
		this.mItemList = itemList;
	}


}
