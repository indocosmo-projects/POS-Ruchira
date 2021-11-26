/**
 * 
 */
package com.indocosmo.pos.forms.split.components;

import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.RootPaneContainer;

import com.indocosmo.pos.common.enums.split.SplitBasedOn;
import com.indocosmo.pos.forms.split.listners.ISplitMethodSelectionListener;

/**
 * @author jojesh
 *
 */
public abstract class SplitMethodSelectionPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected static final SplitBasedOn DEFAULT_SPLIT_BASED_ON=SplitBasedOn.Amount;
	
	protected ISplitMethodSelectionListener listener;
	protected RootPaneContainer mParent;
	protected SplitBasedOn splitBasedOn;
	private int mHeight;
	private int mWidth;


	/**
	 * @param parent 
	 * 
	 */
	public SplitMethodSelectionPanel(RootPaneContainer parent, int width, int height) {
		
		this.mParent=parent;
		this.mHeight=height;
		this.mWidth=width;
		initComponent();

	}
	
	/**
	 * 
	 */
	protected void initComponent(){
		
		setLayout(null);
		setSize(new Dimension(mWidth,mHeight));
		setPreferredSize(new Dimension(mWidth,mHeight));
		
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
//		this.splitBasedOn = splitBasedOn;
		setSplitMethod(splitBasedOn);
	}
	
	/**
	 * @param splitBasedOn
	 */
	protected abstract void setSplitMethod(SplitBasedOn splitBasedOn);
	
	/**
	 * @param value
	 */
	protected boolean onSPlitMethodChanged(SplitBasedOn value) {
		
		return	listener.onSplitMethodSelected(value);
	}

	/**
	 * @param listener the listener to set
	 */
	public void setListener(ISplitMethodSelectionListener listener) {
		this.listener = listener;
	}
	
	/**
	 * @param basedOn
	 * @param enabled
	 */
	public abstract void setSplitMethodEnabled(SplitBasedOn basedOn, boolean enabled);

}
