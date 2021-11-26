package com.indocosmo.pos.forms.components.listners;

import com.indocosmo.pos.data.beans.BeanSaleItem;
import com.indocosmo.pos.forms.components.buttons.PosButton;
import com.indocosmo.pos.forms.components.itemcontrols.listners.IPosItemControlListner;

public abstract class PosItemControlListnerAdapter implements
		IPosItemControlListner {

	@Override
	public void onClicked(PosButton button) {
	}

	@Override
	public void onClicked(BeanSaleItem item) {
	}

}
