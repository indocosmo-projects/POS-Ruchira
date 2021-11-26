package com.indocosmo.pos.forms.components.keypads;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;

import com.indocosmo.pos.common.utilities.PosFormUtil;
import com.indocosmo.pos.forms.components.keypads.PosSoftKeyPad.IPosSoftKeypadListner;

@SuppressWarnings("serial")
public abstract class PosSoftKeyPadInputBase extends JPanel{
	
	protected final static int PANEL_CONTENT_H_GAP=1;
	protected final static int PANEL_CONTENT_V_GAP=1;
	
	protected final static int TEXT_AREA_WIDTH=PosSoftKeyPad.LAYOUT_WIDTH;
	protected final static int TEXT_AREA_HEIGHT_SINGLE=30;
	
	protected JTextArea mTextArea;
	protected PosSoftKeyPad mPosSoftKeyPad;
	
	private int mWidth, mHeight;
	private boolean mIsMultiLine;
	
	
	public PosSoftKeyPadInputBase(int row) {
		mIsMultiLine=(row>1);
		mWidth=PosSoftKeyPad.LAYOUT_WIDTH+PANEL_CONTENT_H_GAP*2;
		mHeight=row*TEXT_AREA_HEIGHT_SINGLE+PosSoftKeyPad.LAYOUT_HEIGHT+PANEL_CONTENT_V_GAP*3;
		initControls();
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.JComponent#setEnabled(boolean)
	 */
	@Override
	public void setEnabled(boolean enabled) {
		mTextArea.setEditable(enabled);
		mPosSoftKeyPad.setEnabled(enabled);
		super.setEnabled(enabled);
	}

	
	private void initControls(){
		initLayout();
		initTextArea();
		initKeyPad();
	}
	
	protected void initLayout(){
		this.setLayout(new FlowLayout(FlowLayout.CENTER,PANEL_CONTENT_H_GAP, PANEL_CONTENT_V_GAP));
		this.setSize(mWidth,mHeight);
		this.setPreferredSize(new Dimension(mWidth,mHeight));
	}
	
	protected void initTextArea(){
		mTextArea=new JTextArea();
		if(mTextArea!=null){
			mTextArea.addKeyListener(new KeyListener() {
				
				@Override
				public void keyTyped(KeyEvent e) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void keyReleased(KeyEvent e) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void keyPressed(KeyEvent e) {
					
					if(!mIsMultiLine && e.getKeyCode()==KeyEvent.VK_ENTER){

						e.consume();
						if(listener!=null)
							listener.onAccepted(mTextArea.getText());
						
					}else{
						
						if(listener!=null)
							listener.onKeyPressed(e);
					}
				
				}
			});
		}
		mTextArea.setBorder(new LineBorder(new Color(134, 184, 232)));
		mTextArea.setFont(PosFormUtil.getTextFieldBoldFont());
		add(mTextArea);
	}
	
	protected void initKeyPad(){
		mPosSoftKeyPad=new PosSoftKeyPad(mTextArea,mIsMultiLine);
		add(mPosSoftKeyPad);
	}
	
	private IPosSoftKeypadListner listener;
	public void setListner(IPosSoftKeypadListner listner){
		listener=listner;
		mPosSoftKeyPad.setKeypadListner(listner);
	}
	
	public void setText(String text){
		mTextArea.setText(text);
		setTextSeleted();
		setFocus();
	}
	
	@Override
	public int getWidth() {
		return mWidth;
	}
	
	@Override
	public int getHeight() {
		return mHeight;
	}

	public void setFocus(){
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				mTextArea.requestFocus();

			}
		});


	}

	public void setTextSeleted(){
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
//				mTextArea.selectAll();

			}
		});

	}


	
	public void showActionActrols(boolean show){
//		mPosSoftKeyPad.setActionButtonsDisabled(show);
	}

	/**
	 * 
	 */
	public void reset() {

		if(mTextArea!=null){
			mTextArea.setText("");
		}
	}

	/**
	 * @return
	 */
	public Component getTextComponent() {
		// TODO Auto-generated method stub
		return mTextArea;
	}

}
