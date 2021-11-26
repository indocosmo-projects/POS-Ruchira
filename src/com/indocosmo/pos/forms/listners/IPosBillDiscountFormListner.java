package com.indocosmo.pos.forms.listners;

import java.util.ArrayList;

import com.indocosmo.pos.data.beans.BeanOrderDetail;

public interface IPosBillDiscountFormListner {
	public void onOkPressed(ArrayList<BeanOrderDetail> saleItemList);
}
