/**
 * 
 */
package com.indocosmo.pos.forms.components.orderentry;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JPanel;

import com.indocosmo.pos.common.utilities.PosResUtil;
import com.indocosmo.pos.forms.components.buttons.PosButton;


/**
 * @author jojesh
 * This is a filler panel. Which is used to fill the gap.
 * DO NOT USE THIS FOR ANY PURPOSE
 */
@SuppressWarnings("serial")
public final class PosEmptyPanel extends JPanel{
	
	private static final int BUTTON_HEIGHT=PosBottomToolbarPanel.BUTTON_HEIGHT;
	private static final int BUTTON_WIDTH=PosBottomToolbarPanel.BUTTON_WIDTH;
	
	private static final int PANEL_CONTENT_V_GAP=PosBottomToolbarPanel.PANEL_CONTENT_V_GAP;
	private static final int PANEL_CONTENT_H_GAP=PosBottomToolbarPanel.PANEL_CONTENT_H_GAP;
	public static final Color PANEL_BG_COLOR=PosBottomToolbarPanel.PANEL_BG_COLOR;
	
	public static final int LAYOUT_HEIGHT=PosBottomToolbarPanel.LAYOUT_HEIGHT;
	
	public static final String IMAGE_EMPTY_NORMAL="empty_button.png";
	public static final String IMAGE_EMPTY_TOUCH="empty_button.png";
	
	private int  mWidth;
	

	
	/**
	 * 
	 */
	public PosEmptyPanel(int width) {
		mWidth=width;
		initComponent();
	}

	private int mNoOptions=0;
	/**
	 * 
	 */
	private void initComponent() {
		mNoOptions=(int)(mWidth/BUTTON_WIDTH);
		final int height=BUTTON_HEIGHT+PANEL_CONTENT_H_GAP;
		setPreferredSize(new Dimension(mWidth, height));
		setBackground(PANEL_BG_COLOR);
//		setBackground(new Color(46, 139, 87));
		
		setLayout(createLayout());
		createButtons();
	}
	
	private void createButtons(){
		
		for(int index=0;index<mNoOptions;index++){
			PosButton button=createEmptyButton(); 
			add(button);
		}
	}
	/**
	 * 
	 */
	public static PosButton  createEmptyButton(){
		PosButton button=new PosButton();
		button.setSize(BUTTON_WIDTH, BUTTON_HEIGHT);
		button.setImage(PosResUtil.getImageIconFromResource(IMAGE_EMPTY_NORMAL));
		button.setTouchedImage(PosResUtil.getImageIconFromResource(IMAGE_EMPTY_TOUCH));
		button.setEnabled(false);
		return button;
	}
	/**
	 * 
	 * @return
	 */
	private FlowLayout createLayout(){
		FlowLayout flowLayout=new FlowLayout();
		flowLayout.setVgap(PANEL_CONTENT_V_GAP);
		flowLayout.setAlignment(FlowLayout.CENTER);
		return flowLayout;
	}


}
