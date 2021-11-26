/**
 * 
 */
package com.indocosmo.pos.forms.split.listners.adapters;

import java.util.ArrayList;

import com.indocosmo.pos.data.beans.BeanOrderSplit;
import com.indocosmo.pos.forms.split.listners.IPosSplitSelectFormListener;

/**
 * @author jojesh
 *
 */
public class PosOrderSplitSelectionFormAdapter implements IPosSplitSelectFormListener {

	/* (non-Javadoc)
	 * @see com.indocosmo.pos.forms.listners.IPosFormEventsListner#onOkButtonClicked()
	 */
	@Override
	public boolean onOkButtonClicked() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.pos.forms.listners.IPosFormEventsListner#onCancelButtonClicked()
	 */
	@Override
	public boolean onCancelButtonClicked() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.pos.forms.listners.IPosFormEventsListner#onResetButtonClicked()
	 */
	@Override
	public void onResetButtonClicked() {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.pos.forms.split.listners.IPosSplitSelectFormListener#onPaymentDone()
	 */
	@Override
	public void onPaymentDone(ArrayList<BeanOrderSplit> splits) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.pos.forms.split.listners.IPosSplitSelectFormListener#onPaymentCompleted()
	 */
	@Override
	public void onPaymentCompleted() {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.pos.forms.split.listners.IPosSplitSelectFormListener#onCancelled()
	 */
	@Override
	public void onCancelled() {
		// TODO Auto-generated method stub
		
	}

}
