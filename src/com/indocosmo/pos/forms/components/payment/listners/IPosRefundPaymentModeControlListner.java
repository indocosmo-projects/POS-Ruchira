package com.indocosmo.pos.forms.components.payment.listners;

import com.indocosmo.pos.data.beans.BeanOrderPayment;

public interface IPosRefundPaymentModeControlListner {
	public void onChange(BeanOrderPayment item, int index);
}
