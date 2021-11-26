/**
 * 
 */
package com.indocosmo.pos.forms.components.orderentry.listners;

import com.indocosmo.pos.forms.components.orderentry.PosMainActionPanel.RestaurantOrderEntryMenuActions;

/**
 * @author jojesh
 *
 */
public interface IPosActionPanelRestaurantListner extends
		IPosActionPanelListnerBase {

	public void onPosActionButtonClicked(RestaurantOrderEntryMenuActions menuAction,
			Object data);
	
}
