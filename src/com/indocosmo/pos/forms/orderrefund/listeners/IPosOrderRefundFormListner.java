package com.indocosmo.pos.forms.orderrefund.listeners;

import com.indocosmo.pos.data.beans.BeanOrderHeader;
import com.indocosmo.pos.data.beans.BeanOrderQHeader;

public interface IPosOrderRefundFormListner {
	public void  onRefundDone(Object sender,BeanOrderHeader item);
}
