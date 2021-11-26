/**
 * 
 */
package com.indocosmo.pos.forms.components.orderentry.listners;

import com.indocosmo.pos.forms.components.itemcontrols.PosOrderListItemControl;

/**
 * @author jojesh
 *
 */

public interface IPosOrderListPanelListner {
	public void onItemEdited(PosOrderListItemControl item);
	public void onItemAdded(PosOrderListItemControl item);
	public void onItemDeleted(PosOrderListItemControl item);
	public void onItemSelected(PosOrderListItemControl item);
}


