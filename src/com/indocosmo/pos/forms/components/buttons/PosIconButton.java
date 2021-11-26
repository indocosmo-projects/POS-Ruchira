/**
 * 
 */
package com.indocosmo.pos.forms.components.buttons;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import com.indocosmo.pos.common.utilities.PosFormUtil;
/**
 * @author jojesh
 *
 */
@SuppressWarnings("serial")
public class PosIconButton extends PosButton {

	private JLabel mTextLabel;
	private static final int PANEL_CONTENT_V_GAP=2;
	private static final int PANEL_CONTENT_H_GAP=2;
	
	/**
	 * @param text
	 */
	public PosIconButton(String text) {
		super();
		initControls();
		setText(text);
	}

	/**
	 * @param image
	 */
	public PosIconButton(Icon image) {
		super(image);
		initControls();
	}

	/**
	 * 
	 */
	public PosIconButton() {
		super();
		initControls();
	}
	
	private void initControls(){
//		setLayout(new FlowLayout(FlowLayout.CENTER,PANEL_CONTENT_H_GAP,PANEL_CONTENT_V_GAP));
		setLayout(null);
		mTextLabel=new JLabel("",SwingConstants.CENTER);
		mTextLabel.setFont(PosFormUtil.getButtonFont());
		mTextLabel.setLocation(1,1);
//		mTextLabel.setOpaque(true);
		mTextLabel.setForeground(Color.WHITE);
		add(mTextLabel);
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.AbstractButton#setHorizontalTextPosition(int)
	 */
	@Override
	public void setHorizontalTextPosition(int textPosition) {
		if(mTextLabel!=null)
			mTextLabel.setHorizontalAlignment(textPosition);
	}
	
	public void setTextIcon(ImageIcon icon){
		mTextLabel.setIcon(icon);
	}
	
	@Override
	public void setText(String text) {	
//		mTextLabel.setText("<html><center>"+text+"</center></html>");
		this.text=text;
		mTextLabel.setText(getHTMLFormatedString(text));
	}
	
	@Override
	public void setPreferredSize(Dimension preferredSize) {
		// TODO Auto-generated method stub
		super.setPreferredSize(preferredSize);
		setLableSize();
	}
	
	@Override
	public void setSize(Dimension d) {
		// TODO Auto-generated method stub
		super.setSize(d);
		mTextLabel.setPreferredSize(new Dimension(getWidth(),getHeight()));
		setLableSize();
	}
	
	@Override
	public void setSize(int width, int height) {
		// TODO Auto-generated method stub
		super.setSize(width, height);
		setLableSize();
	}
	
	private void setLableSize(){
		final int width=getWidth()-PANEL_CONTENT_H_GAP;
		final int height=getHeight()-PANEL_CONTENT_V_GAP;
		mTextLabel.setSize(new Dimension(width,height));
	}

	/* (non-Javadoc)
	 * @see javax.swing.AbstractButton#setEnabled(boolean)
	 */
	@Override
	public void setEnabled(boolean b) {
		super.setEnabled(b);
		mTextLabel.setForeground((b)?Color.WHITE:Color.GRAY);
	}

}
