/**
 * 
 */
package com.indocosmo.pos.forms.split.listners;

import java.util.ArrayList;

import com.indocosmo.pos.data.beans.BeanOrderSplit;
import com.indocosmo.pos.forms.listners.IPosFormEventsListner;

/**
 * @author jojesh
 *
 */
public interface IPosSplitSelectFormListener extends IPosFormEventsListner {

	public void onPaymentDone(ArrayList<BeanOrderSplit> splits);
	public void onPaymentCompleted();
	public void onCancelled();
	
}
