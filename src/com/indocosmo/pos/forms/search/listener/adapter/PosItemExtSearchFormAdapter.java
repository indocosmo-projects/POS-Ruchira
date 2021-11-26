/**
 * 
 */
package com.indocosmo.pos.forms.search.listener.adapter;

import com.indocosmo.pos.forms.components.search.IPosSearchableItem;
import com.indocosmo.pos.forms.search.listener.IPosItemExtSearchFormListener;

/**
 * @author jojesh-13.2
 *
 */
public class PosItemExtSearchFormAdapter implements
		IPosItemExtSearchFormListener {

	/**
	 * 
	 */
	public PosItemExtSearchFormAdapter() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.pos.forms.search.listener.IPosItemExtSearchFormListener#onAccepted(com.indocosmo.pos.forms.components.search.IPosSearchableItem)
	 */
	@Override
	public boolean onAccepted(Object sender,IPosSearchableItem item) {
		// TODO Auto-generated method stub
		return true;
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.pos.forms.search.listener.IPosItemExtSearchFormListener#onCancel()
	 */
	@Override
	public boolean onCancel(Object sender) {
		// TODO Auto-generated method stub
		return true;
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.pos.forms.search.listener.IPosItemExtSearchFormListener#onDetailClicked(java.lang.Object, com.indocosmo.pos.forms.components.search.IPosSearchableItem)
	 */
	@Override
	public void onDetailClicked(Object sender, IPosSearchableItem item) {
		// TODO Auto-generated method stub
		return  ;
	}

}
