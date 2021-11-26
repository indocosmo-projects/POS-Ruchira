package com.indocosmo.pos.forms.listners;

import com.indocosmo.pos.data.beans.BeanOrderHeader;

public interface IPosHoldOrderFormFormListner {
	public void onOkClicked(BeanOrderHeader posOrderItem);
	public void onCancelClicked();
}
