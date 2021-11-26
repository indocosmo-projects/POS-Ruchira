package com.indocosmo.pos.forms.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.RootPaneContainer;
import javax.swing.SwingConstants;

import com.indocosmo.pos.common.PosEnvSettings;
import com.indocosmo.pos.common.utilities.PosFormUtil;
import com.indocosmo.pos.common.utilities.PosOrderUtil;
import com.indocosmo.pos.common.utilities.PosStringUtil;
import com.indocosmo.pos.common.utilities.PosUtil;
import com.indocosmo.pos.data.beans.BeanCashierShift;
import com.indocosmo.pos.data.beans.BeanCustomer;
import com.indocosmo.pos.data.beans.BeanItemPromotion;
import com.indocosmo.pos.data.beans.BeanOrderCustomer;
import com.indocosmo.pos.data.beans.BeanOrderHeader;
import com.indocosmo.pos.forms.PosOrderEntryForm;
import com.indocosmo.pos.forms.PosOrderEntryForm.PosOrderEntryMode;
import com.indocosmo.pos.forms.components.labels.PosMarqueeLabel;
import com.indocosmo.pos.forms.listners.IPosMessageListner;

/**
 * @author jojesh Class to handle the Message display panel
 */
public final class PosScrollMessagePanel extends JPanel  {

	/**
	 * 
	 */
	private static final Dimension SCREEN_SIZE=PosUtil.getScreenSize(PosEnvSettings.getInstance().getPrimaryScreen());
	private static final long serialVersionUID = 1L;
	public static final int PANEL_HEIGHT = 25;
	// private static final Color PANEL_BG_COLOR=new Color(100,100,162);
	private static final Color PANEL_BG_COLOR = PosOrderEntryForm.PANEL_BG_COLOR;

	private static final int PANEL_CONTENT_H_GAP = 1;// PosOrderEntryForm.PANEL_CONTENT_H_GAP;
	private static final int PANEL_CONTENT_V_GAP = 1;// PosOrderEntryForm.PANEL_CONTENT_V_GAP;
  
	private int mLeft, mTop,mWidth,mHeight;
 
	private PosMarqueeLabel mLabelScrollMessage;
 
 
	
	public PosScrollMessagePanel(int left, int top,int width) {
		mLeft = left;
		mTop = top;
		mWidth=width;
		initComponent();
		 
	}

	public PosScrollMessagePanel(int left, int top) {
		mLeft = left;
		mTop = top;
		mWidth = (int) SCREEN_SIZE.getWidth() - PosOrderEntryForm.PANEL_H_GAP
				* 4;
		initComponent();
		 
	}
	private void initComponent() {
		
	
		mHeight = PANEL_HEIGHT;
		setSize(mWidth, mHeight);
		setPreferredSize(new Dimension(mWidth, mHeight));
		setBounds(mLeft, mTop, mWidth, mHeight);
		setBackground (Color.decode("#2B4564"));
		setLayout(new FlowLayout(FlowLayout.LEFT, PANEL_CONTENT_H_GAP,
				PANEL_CONTENT_V_GAP));
		
		createScrollMessageLabel();
	}
 
	private void createScrollMessageLabel() {
		
		mLabelScrollMessage = new PosMarqueeLabel();
		mLabelScrollMessage.setVerticalAlignment(SwingConstants.CENTER);
		mLabelScrollMessage.setBounds(PANEL_CONTENT_H_GAP, PANEL_CONTENT_V_GAP, getWidth()-PANEL_CONTENT_H_GAP*5, this.getHeight());
		mLabelScrollMessage.setPreferredSize(mLabelScrollMessage.getSize());
//		mLabelScrollMessage.setBackground(Color.white);
//		mLabelScrollMessage.setOpaque(true);
		add(mLabelScrollMessage);

	}
	
	/*
	 * 
	 */
	public void showMessage(String message) {
		
		setVisible(true);
		mLabelScrollMessage.setText(message);
		int width=   PosStringUtil.getStringLength(message, mLabelScrollMessage.getGraphics());
		width=width<(getWidth()-PANEL_CONTENT_H_GAP*5)?getWidth()-PANEL_CONTENT_H_GAP*5:width;
		mLabelScrollMessage.setPreferredSize(new Dimension(width, mLabelScrollMessage.getHeight()));
		mLabelScrollMessage.setSize(new Dimension(width, mLabelScrollMessage.getHeight()));
		
		mLabelScrollMessage.invalidate();
//	       validate();getWidth()-PANEL_CONTENT_H_GAP*5
		
	}
}
