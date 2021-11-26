/**
 * 
 */
package com.indocosmo.pos.forms.search.listener;

import com.indocosmo.pos.forms.components.search.IPosSearchableItem;

/**
 * @author jojesh-13.2
 *
 */
public interface IPosItemExtSearchFormListener {
	
	public boolean onAccepted(Object sender,IPosSearchableItem item);
	public boolean onCancel(Object sender);
	public void onDetailClicked(Object sender,IPosSearchableItem item);

}
