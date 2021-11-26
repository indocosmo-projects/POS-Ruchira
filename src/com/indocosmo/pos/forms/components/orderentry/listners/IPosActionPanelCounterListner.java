/**
 * 
 */
package com.indocosmo.pos.forms.components.orderentry.listners;

/**
 * @author jojesh
 *
 */
public interface IPosActionPanelCounterListner extends
		IPosActionPanelListnerBase {
	
	public void onNewClicked();
	public void onSaveClicked();
	public void onRetrieveClicked();
}
