package com.indocosmo.pos.forms.listners;

import com.indocosmo.pos.data.beans.BeanSalesMode;

public interface IPosSalesModeFormListner {
	public void onSalesModeSelected(BeanSalesMode salesModeItem);
	public void onCancel();
}
