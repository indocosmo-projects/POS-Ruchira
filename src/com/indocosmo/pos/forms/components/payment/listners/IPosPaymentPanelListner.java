package com.indocosmo.pos.forms.components.payment.listners;

public interface IPosPaymentPanelListner {
	
	public void onTenderAmountChanged(double amount);
	public void onAccept();
}
