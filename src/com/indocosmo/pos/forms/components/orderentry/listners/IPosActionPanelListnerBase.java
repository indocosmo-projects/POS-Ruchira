package com.indocosmo.pos.forms.components.orderentry.listners;

import com.indocosmo.pos.forms.components.orderentry.PosActionPanelBase.OrderEntryMenuActions;

public interface IPosActionPanelListnerBase {
	
	public void onPosActionButtonClicked(OrderEntryMenuActions menuAction, Object data);
}
