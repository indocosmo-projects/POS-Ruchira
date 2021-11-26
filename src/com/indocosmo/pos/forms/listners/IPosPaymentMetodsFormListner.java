package com.indocosmo.pos.forms.listners;

import com.indocosmo.pos.common.enums.PosPaymentOption;
import com.indocosmo.pos.data.beans.BeanPayModeBase.PaymentMode;

public interface IPosPaymentMetodsFormListner {
	public void onSelected(PaymentMode paymentMode);
	public void onClick(PosPaymentOption payOption) throws Exception;
}
