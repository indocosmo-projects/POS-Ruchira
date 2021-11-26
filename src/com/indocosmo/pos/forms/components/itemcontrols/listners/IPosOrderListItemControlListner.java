package com.indocosmo.pos.forms.components.itemcontrols.listners;

import com.indocosmo.pos.forms.components.itemcontrols.PosOrderListItemControl;

public interface IPosOrderListItemControlListner {
	public void onSelected(PosOrderListItemControl gridItem);
	public void onDoubleClick(PosOrderListItemControl gridItem);
}
