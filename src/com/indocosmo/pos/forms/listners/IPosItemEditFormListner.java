package com.indocosmo.pos.forms.listners;

import com.indocosmo.pos.data.beans.BeanOrderDetail;
import com.indocosmo.pos.data.beans.BeanSaleItem;

public interface IPosItemEditFormListner {
	public void onItemEdited(BeanSaleItem newItem);
	public void onItemEdited(BeanOrderDetail newItem, BeanOrderDetail oldItem);
//	public void onItemEdited(BeanSaleItem newSaleItem, BeanSaleItem oldSaleItem);
//	public void onComboContentItemEdited(BeanSaleItem newSaleItem, ArrayList<BeanSaleItem> newSubstitutes);
}
