package com.indocosmo.pos.forms.components.orderentry.listners;

import com.indocosmo.pos.data.beans.BeanPayModeBase.PaymentMode;

public interface IPosPaymentPanelListner extends IPosOrderEntryBottomPanelListener{
	public void onPaymentDone(PaymentMode paymode);
	public void onPaymentCancelled(Object sender);
	public void onPreBillDiscountChanged();
}
