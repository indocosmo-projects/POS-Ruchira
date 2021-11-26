package com.indocosmo.pos.forms.components.payment.listners;

import com.indocosmo.pos.data.beans.BeanCoupon;

public interface IPosCouponPaymentControlListner {
	public void onChange(BeanCoupon item, int index);
}
