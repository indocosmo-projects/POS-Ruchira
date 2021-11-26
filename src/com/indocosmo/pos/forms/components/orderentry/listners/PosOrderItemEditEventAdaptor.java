/**
 * 
 */
package com.indocosmo.pos.forms.components.orderentry.listners;

import com.indocosmo.pos.data.beans.BeanOrderDetail;
import com.indocosmo.pos.forms.PosOrderListItemEditForm.IPosOrderItemEditFormEventListner;

/**
 * @author joe.12.3
 *
 */
public class PosOrderItemEditEventAdaptor implements
		IPosOrderItemEditFormEventListner {

	/* (non-Javadoc)
	 * @see com.indocosmo.pos.forms.listners.IPosFormEventsListner#onOkButtonClicked()
	 */
	@Override
	public boolean onOkButtonClicked() {
		// TODO Auto-generated method stub
		return true;
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.pos.forms.listners.IPosFormEventsListner#onCancelButtonClicked()
	 */
	@Override
	public boolean onCancelButtonClicked() {
		// TODO Auto-generated method stub
		return true;
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.pos.forms.listners.IPosFormEventsListner#onResetButtonClicked()
	 */
	@Override
	public void onResetButtonClicked() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.indocosmo.pos.forms.PosOrderListItemEditForm.IPosOrderItemEditEventListner#onResetButtonClicked(com.indocosmo.pos.data.beans.BeanOrderDetail)
	 */
	@Override
	public void onResetButtonClicked(BeanOrderDetail orgItem) {
		// TODO Auto-generated method stub

	}

}
