package com.indocosmo.pos.forms.listners;

import com.indocosmo.pos.common.enums.PosPaymentOption;
import com.indocosmo.pos.data.beans.BeanOrderHeader;
import com.indocosmo.pos.data.beans.BeanOrderQHeader;

public interface IPosOrderInfoFormListner {
	
	public void  onItemSaved(Object sender,BeanOrderHeader item);
	public void  onOkClicked(Object sender,BeanOrderHeader item);
	public void  onPaymentButtonClicked(Object sender,PosPaymentOption paymentOption, BeanOrderHeader item);
	
}
