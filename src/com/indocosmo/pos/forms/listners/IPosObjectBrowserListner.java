package com.indocosmo.pos.forms.listners;

import com.indocosmo.pos.forms.components.objectbrowser.IPosBrowsableItem;

public interface IPosObjectBrowserListner {
	public void onItemSelected(IPosBrowsableItem item);
	public void onCancel();
}
