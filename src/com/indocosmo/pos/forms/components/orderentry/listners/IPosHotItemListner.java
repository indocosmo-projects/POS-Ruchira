package com.indocosmo.pos.forms.components.orderentry.listners;

import java.util.ArrayList;

import com.indocosmo.pos.data.beans.BeanSaleItem;

public interface IPosHotItemListner {
	public void onSelected(ArrayList<BeanSaleItem> hotItemList);
}
