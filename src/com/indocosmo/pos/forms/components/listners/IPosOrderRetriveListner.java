package com.indocosmo.pos.forms.components.listners;

import com.indocosmo.pos.data.beans.BeanOrderHeader;

public interface IPosOrderRetriveListner {
	public void onOrderRetrieved(BeanOrderHeader order);
	public void onReset();
}
