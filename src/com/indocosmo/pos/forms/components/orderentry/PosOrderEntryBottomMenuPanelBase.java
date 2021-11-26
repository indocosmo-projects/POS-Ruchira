/**
 * 
 */
package com.indocosmo.pos.forms.components.orderentry;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.RootPaneContainer;

import com.indocosmo.pos.forms.components.buttons.PosButton;
import com.indocosmo.pos.forms.components.orderentry.listners.IPosOrderEntryBottomPanelListener;


/**
 * @author jojesh
 * This abstract class will handle the various menu bars at the bottom of the order entry form.
 */
@SuppressWarnings("serial")
public abstract class PosOrderEntryBottomMenuPanelBase extends JPanel{
	
	protected static final int PANEL_CONTENT_V_GAP=3;
	protected static final int PANEL_CONTENT_H_GAP=3;
	
	protected static final int BUTTON_HEIGHT=PosBottomToolbarPanel.BUTTON_HEIGHT;
	protected static final int BUTTON_WIDTH=PosBottomToolbarPanel.BUTTON_WIDTH;
	
	public static final int LAYOUT_HEIGHT=BUTTON_HEIGHT+PANEL_CONTENT_V_GAP*2;
	
	protected static final Color PANEL_BG_COLOR=PosBottomToolbarPanel.PANEL_BG_COLOR;
	
	protected IPosOrderEntryBottomPanelListener mOperationsListener;
	protected RootPaneContainer mParent;
	private ArrayList<PosButton> optionList;
	/**
	 * 
	 */
	public PosOrderEntryBottomMenuPanelBase(RootPaneContainer parent) {
		mParent=parent;
		initComponent();
	}

	protected void initComponent() {
		setBackground(PANEL_BG_COLOR);
//		setBackground(new Color(46, 139, 87));
		setOpaque(true);
		setLayout(createLayout());
		optionList=new ArrayList<PosButton>();
	}
	
	protected void addButtonsToPanel(ArrayList<PosButton> buttonList){
		final int noButtons=buttonList.size();
		this.removeAll();
		if(noButtons>0){
			final int width=BUTTON_WIDTH*noButtons+PANEL_CONTENT_H_GAP*(noButtons+1);
			final int height=LAYOUT_HEIGHT;
			this.setSize(new Dimension(width,height));
			this.setPreferredSize(new Dimension(width,height));
			for(PosButton button:buttonList){
				this.add(button);
			}
		}
	}
	
	protected void addButtonToPanel(PosButton button){
		optionList.add(button);
		addButtonsToPanel(optionList);
	}
	
	private FlowLayout createLayout(){
		FlowLayout flowLayout=new FlowLayout();
		flowLayout.setVgap(PANEL_CONTENT_V_GAP);
		flowLayout.setHgap(PANEL_CONTENT_H_GAP);
		flowLayout.setAlignment(FlowLayout.CENTER);
		return flowLayout;
	}

	
	public void setListener(IPosOrderEntryBottomPanelListener operationsListener){
		mOperationsListener=operationsListener;
	}

	/**
	 * @return the optionList
	 */
	public ArrayList<PosButton> getOptionList() {
		return optionList;
	}
	
	
	

	
}
