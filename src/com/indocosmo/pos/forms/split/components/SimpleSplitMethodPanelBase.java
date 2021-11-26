/**
 * 
 */
package com.indocosmo.pos.forms.split.components;

import java.awt.Dimension;

import javax.swing.RootPaneContainer;

/**
 * @author jojesh
 *
 */
public abstract class SimpleSplitMethodPanelBase extends SplitMethodPanel{

	private static final long serialVersionUID = 1L;

	public SimpleSplitMethodPanelBase(RootPaneContainer parent ,int width,int height){
		super(parent);

		setLayout(null);
		setSize(width, height);
		setPreferredSize(new Dimension(width, height));
		setVisible(true);
		setFocusable(true);
		createContents();
	}
	
	protected abstract void createContents();
	
	public abstract double getSplitValue();
}
