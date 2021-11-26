/**
 * 
 */
package com.indocosmo.pos.forms.split.components;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.RootPaneContainer;
import javax.swing.border.LineBorder;

import com.indocosmo.pos.common.utilities.PosFormUtil;
import com.indocosmo.pos.forms.components.checkboxes.PosImageCheckBox;
import com.indocosmo.pos.forms.components.textfields.PosTouchableFieldBase;

/**
 * @author jojesh-13.2
 *
 */
public class CustomSplitMethodPanel extends SplitMethodPanel {

	private static final long serialVersionUID = 1L;
	private static final int INFO_BOX_HEIGHT=50;
	
	private PosImageCheckBox chkBoxSplitEquallyType;
	
	/**
	 * 
	 */
	public CustomSplitMethodPanel(RootPaneContainer parent ,int width,int height){
		super(parent);

		setLayout(null);
		setSize(width, height);
		setPreferredSize(new Dimension(width, height));
		setVisible(false);
		createContents();
	}

	/**
	 * 
	 */
	private void createContents(){

		final int itemDefHeight=PosTouchableFieldBase.LAYOUT_DEF_HEIGHT;
		int top =(getHeight()-INFO_BOX_HEIGHT)/2-itemDefHeight/2;
		int left=0;

		JLabel titleLabel=new JLabel("Common items :");
		titleLabel.setFont(PosFormUtil.getLabelFont());
		titleLabel.setBounds(left,top,200,itemDefHeight);
		titleLabel.setHorizontalAlignment(JLabel.RIGHT);
		add(titleLabel);
		
		left+=titleLabel.getWidth()+PANEL_CONTENT_H_GAP;
		chkBoxSplitEquallyType=new PosImageCheckBox("Split Equally", 400);
		chkBoxSplitEquallyType.setLocation(left, top-3);
		chkBoxSplitEquallyType.setSelected(false);
		add(chkBoxSplitEquallyType);

		createInfoBox();
		
	}
	
	private void createInfoBox(){
		
		final int infoHeight=INFO_BOX_HEIGHT;
		final int left=PANEL_CONTENT_H_GAP;
		final int top=getHeight()-PANEL_CONTENT_V_GAP-infoHeight;
		final int width=getWidth()-left-PANEL_CONTENT_H_GAP;
		final String infoText=String.format("<html><div WIDTH=%d>%s</div><html>", width, "Unselected items will be considered as common items. <br>If <font color=#FF0000>“Split Equally” </font> is not selected, common items will be treated as separate bill.");
		
		JLabel infoLabel=new JLabel(infoText);
		infoLabel.setOpaque(true);
		infoLabel.setBackground(Color.LIGHT_GRAY);
		infoLabel.setBorder(new LineBorder(Color.GRAY));
		infoLabel.setBounds(left,top,width,infoHeight);//450,50
		infoLabel.setHorizontalAlignment(JLabel.LEFT);
		infoLabel.setVerticalAlignment(JLabel.CENTER);
		infoLabel.setFont(PosFormUtil.getLabelFontSmall());
		add(infoLabel);
		
	}


	/**
	 * @return
	 */
	public boolean isInputValid(){

		boolean valid=true;

		return valid;
	}
	
	public boolean isSplitEqually(){
		
		return chkBoxSplitEquallyType.isSelected();
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.pos.forms.split.components.SplitMethodPanel#reset()
	 */
	@Override
	void reset() {
		
		chkBoxSplitEquallyType.setSelected(false);
		
	}
}
