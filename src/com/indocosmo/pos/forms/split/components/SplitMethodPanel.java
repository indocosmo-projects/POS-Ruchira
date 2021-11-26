/**
 * 
 */
package com.indocosmo.pos.forms.split.components;

import javax.swing.JPanel;
import javax.swing.RootPaneContainer;

import com.indocosmo.pos.common.enums.split.SplitBasedOn;
import com.indocosmo.pos.forms.split.listners.ISplitMethodPanelListener;

/**
 * @author jojesh-13.2
 *
 */
public abstract class SplitMethodPanel extends JPanel {
	
	protected static final int PANEL_CONTENT_V_GAP=8;
	protected static final int PANEL_CONTENT_H_GAP=8;
	private static final long serialVersionUID = 1L;
	
	protected RootPaneContainer mParent;
	protected SplitBasedOn splitBasedOn;
	
	protected ISplitMethodPanelListener listener;

	/**
	 * @param parent 
	 * 
	 */
	public SplitMethodPanel(RootPaneContainer parent) {
		this.mParent=parent;
	}

	/**
	 * @return the splitBasedOn
	 */
	public SplitBasedOn getSplitBasedOn() {
		return splitBasedOn;
	}

	/**
	 * @param splitBasedOn the splitBasedOn to set
	 */
	public void setSplitBasedOn(SplitBasedOn splitBasedOn) {
		this.splitBasedOn = splitBasedOn;
	}
	

	/**
	 * @return
	 */
	public boolean isSplitValid(){
		
		return true;
	}

	/**
	 * @param listener the listener to set
	 */
	public void setListener(ISplitMethodPanelListener listener) {
		this.listener = listener;
	}
	
	/**
	 * 
	 */
	abstract void reset();
	
	
}
