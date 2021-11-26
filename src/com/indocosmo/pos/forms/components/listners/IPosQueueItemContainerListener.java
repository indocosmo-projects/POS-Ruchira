package com.indocosmo.pos.forms.components.listners;

import com.indocosmo.pos.forms.components.itemcontrols.PosOrderQueueItemControl;

public interface IPosQueueItemContainerListener {
	public void onItemSelected(PosOrderQueueItemControl item);
	public void onPageChanged(int pageNo);
	public void onItemDeleted();
}
