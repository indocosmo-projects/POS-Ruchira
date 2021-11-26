/**
 * 
 */
package com.indocosmo.pos.forms.components.objectbrowser;

import com.indocosmo.pos.forms.components.objectbrowser.listners.IPosObjectBrowserItemListner;

/**
 * @author anand
 *
 */
public interface IPosObjectBrowserControl {

	public IPosBrowsableItem getItem();
	public void setItem(IPosBrowsableItem item);
	public void setListner(IPosObjectBrowserItemListner listener);
}
