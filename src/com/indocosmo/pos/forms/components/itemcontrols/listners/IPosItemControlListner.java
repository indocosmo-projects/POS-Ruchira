package com.indocosmo.pos.forms.components.itemcontrols.listners;

import com.indocosmo.pos.data.beans.BeanSaleItem;
import com.indocosmo.pos.forms.components.buttons.listners.IPosButtonListner;

public interface IPosItemControlListner extends IPosButtonListner{
	public void onClicked(BeanSaleItem item);
}
