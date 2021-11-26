package com.indocosmo.pos.forms.listners;


public interface IPosPaymentFormListner {
	public void onPaymentDone(Object sender);
	public void onPaymentCancelled(Object sender);
	public void onPaymentStatusChanged(Object sender);
}
