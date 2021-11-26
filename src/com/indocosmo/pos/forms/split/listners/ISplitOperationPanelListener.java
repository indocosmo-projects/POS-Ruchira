/**
 * 
 */
package com.indocosmo.pos.forms.split.listners;

import com.indocosmo.pos.common.enums.split.SplitBasedOn;

/**
 * @author jojesh
 *
 */
public interface ISplitOperationPanelListener {

	public boolean onSplitMethodChanged(SplitBasedOn basaedOn);
	public double onExactAmountRequested();
	public void onResetRequested();
	public boolean hasSplit();
	
}
