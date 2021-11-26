package com.indocosmo.pos.forms.components.itemcontrols.listners;

import com.indocosmo.pos.forms.components.itemcontrols.PosOrderQueueItemControl;

public interface IPosOrderQueueItemControlListner {
	public void onSelected(PosOrderQueueItemControl item);
	public void onDoubleClick(PosOrderQueueItemControl item);
}
