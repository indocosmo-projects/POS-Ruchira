package com.indocosmo.pos.forms.components.terminal;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

@SuppressWarnings("serial")
public final class PosSystemInfoDisplayPanel extends JPanel{

	private static final int PANEL_CONTENT_H_GAP=5;
	private static final int PANEL_CONTENT_V_GAP=5;
	private static final int MESSAGE_PANEL_HEIGHT=30;
	private static final Color LABEL_BG_COLOR=new Color(78,128,188);
	
	private JTextArea mTxtSystemInfoArea;
	private int mHeight;
	private int mWidth;
	
	public PosSystemInfoDisplayPanel(int width, int height){
		mHeight=height;
		mWidth=width;
		initControls();
	}
	
	private void initControls(){
		this.setSize(mWidth,mHeight );
		this.setPreferredSize(new Dimension(mWidth,mHeight ));
		this.setLayout(new FlowLayout(FlowLayout.CENTER,PANEL_CONTENT_H_GAP,PANEL_CONTENT_V_GAP));
		this.setOpaque(false);
		setShortMessagePanel("POS System Information");
		setDisplayArea();
	}
	
	private void setDisplayArea(){
		final int width=getWidth()-PANEL_CONTENT_H_GAP*2;
		final int height=getHeight()-MESSAGE_PANEL_HEIGHT-PANEL_CONTENT_V_GAP*3;
		mTxtSystemInfoArea=new JTextArea();
		mTxtSystemInfoArea.setSize(width,height );
		mTxtSystemInfoArea.setPreferredSize(new Dimension(width,height ));
		mTxtSystemInfoArea.setEditable(false);
		mTxtSystemInfoArea.setFocusable(false);
		add(mTxtSystemInfoArea);
	}
	
	private void setShortMessagePanel(String message){
		JLabel labelMessage=new JLabel();
		labelMessage.setText(message);
		labelMessage.setHorizontalAlignment(SwingConstants.CENTER);		
		labelMessage.setPreferredSize(new Dimension(getWidth()-4, MESSAGE_PANEL_HEIGHT));				
		labelMessage.setOpaque(true);
		labelMessage.setBackground(LABEL_BG_COLOR);
		labelMessage.setForeground(Color.WHITE);
		add(labelMessage);
	}
	
}
