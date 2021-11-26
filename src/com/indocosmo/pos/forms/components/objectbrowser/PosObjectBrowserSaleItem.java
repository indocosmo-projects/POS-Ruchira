/**
 * 
 */
package com.indocosmo.pos.forms.components.objectbrowser;

import com.indocosmo.pos.data.beans.BeanSaleItem;
import com.indocosmo.pos.forms.components.itemcontrols.PosItemControl;
import com.indocosmo.pos.forms.components.objectbrowser.listners.IPosObjectBrowserItemListner;

/**
 * @author anand
 *
 */

@SuppressWarnings("serial")
public class PosObjectBrowserSaleItem extends PosItemControl implements IPosObjectBrowserControl{

	public static final int LAYOUT_HEIGHT=BUTTON_HEIGHT;
	public static final int LAYOUT_WIDTH=BUTTON_WIDTH;
	 
	protected IPosObjectBrowserItemListner mItemListner;
	
	private BeanSaleItem mItem;
	/* (non-Javadoc)
	 * @see com.indocosmo.pos.forms.components.objectbrowser.IPosObjectBrowserControl#setItem(com.indocosmo.pos.forms.components.objectbrowser.IPosBrowsableItem)
	 */
	@Override
	public void setItem(IPosBrowsableItem item) {

		mItem=(BeanSaleItem) item;
		super.setItem(mItem);
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.pos.forms.components.objectbrowser.IPosObjectBrowserControl#setListner(com.indocosmo.pos.forms.components.objectbrowser.listners.IPosObjectBrowserItemListner)
	 */
	@Override
	public void setListner(IPosObjectBrowserItemListner listener) {

		mItemListner=listener;
	}
	
	/* (non-Javadoc)
	 * @see com.indocosmo.pos.forms.components.itemcontrols.PosItemControl#onClicked()
	 */
	@Override
	protected void onClicked() {

		super.onClicked();
		if(mItemListner!=null)
			mItemListner.onSelected(mItem);
	}

}
