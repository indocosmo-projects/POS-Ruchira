/**
 * 
 */
package com.indocosmo.pos.forms.restaurant.components;

import javax.swing.RootPaneContainer;

import com.indocosmo.pos.forms.restaurant.components.itemcontrols.PosServiceTableItemControlExt;
import com.indocosmo.pos.forms.restaurant.components.itemcontrols.PosServiceTabletemControl;

/**
 * @author jojesh
 *
 */
public final class PosServiceTableSelectionPanelExt extends
		PosServiceTableSelectionPanel {

	/**
	 * @param parent
	 */
	public PosServiceTableSelectionPanelExt(RootPaneContainer parent) {
		super(parent);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.pos.forms.restaurant.components.PosServiceTableSelectionPanel#createItemControl()
	 */
	@Override
	protected PosServiceTabletemControl createItemControl() {
		PosServiceTableItemControlExt ctrl=new  PosServiceTableItemControlExt();
		return ctrl;
	}

}
