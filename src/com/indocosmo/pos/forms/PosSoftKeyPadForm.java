package com.indocosmo.pos.forms;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;

import javax.swing.JDialog;
import javax.swing.JTextField;
import javax.swing.text.JTextComponent;

import com.indocosmo.pos.common.utilities.PosFormUtil;
import com.indocosmo.pos.forms.components.keypads.PosSoftKeyPad.IPosSoftKeypadListner;
import com.indocosmo.pos.forms.components.keypads.PosSoftKeyPadInputBase;
import com.indocosmo.pos.forms.components.keypads.PosSoftKeyPadMultiLineInput;
import com.indocosmo.pos.forms.components.keypads.PosSoftKeyPadSingleLineInput;
import com.indocosmo.pos.forms.components.keypads.listners.PosSoftKeypadAdapter;


@SuppressWarnings("serial")
public class PosSoftKeyPadForm extends JDialog{

	private static final int PANEL_CONTENT_H_GAP=5;
	private static final int PANEL_CONTENT_V_GAP=1;
	
	private static final int BORDER_WIDTH=5;
	
	private static final int TITLE_HEIGHT=30;
	private IPosSoftKeypadListner mListner;
	private PosSoftKeyPadInputBase mSoftKeyPad;
	private JTextComponent mTarget;
	private JTextField mTitleLabel;
	private boolean mIsMultiLine=false;
//	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PosSoftKeyPadForm frame = new PosSoftKeyPadForm();
					PosFormUtil.showModal(frame);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public PosSoftKeyPadForm(JTextComponent target){
		mTarget=target;
		initControls();
	}
	
	public PosSoftKeyPadForm(boolean isMultiLine,JTextComponent target){
		mIsMultiLine=isMultiLine;
		mTarget=target;
		initControls();
	}
	
	public PosSoftKeyPadForm(boolean isMultiLine){
		mIsMultiLine=isMultiLine;
		initControls();
	}
	
	
	public PosSoftKeyPadForm(){
		initControls();
	}
	
	private void initControls(){
		int width=0;
		int height=0;
		
		
		if(mIsMultiLine){
			width=PosSoftKeyPadMultiLineInput.LAYOUT_WIDTH;
			height=PosSoftKeyPadMultiLineInput.LAYOUT_HEIGHT;
			mSoftKeyPad=new PosSoftKeyPadMultiLineInput();
		}else{
			width=PosSoftKeyPadSingleLineInput.LAYOUT_WIDTH;
			height=PosSoftKeyPadSingleLineInput.LAYOUT_HEIGHT;
			mSoftKeyPad=new PosSoftKeyPadSingleLineInput();
		}
			
		
		width+=PANEL_CONTENT_H_GAP*2;
		height+=PANEL_CONTENT_V_GAP*3+TITLE_HEIGHT+BORDER_WIDTH*2;
		
		this.setSize(width, height);
		this.setLayout(new FlowLayout(FlowLayout.CENTER,PANEL_CONTENT_H_GAP,PANEL_CONTENT_V_GAP));
				
		mTitleLabel=new JTextField("Soft Key Pad");
		mTitleLabel.setHorizontalAlignment(JTextField.CENTER);
		mTitleLabel.setPreferredSize(new Dimension(width-PANEL_CONTENT_H_GAP*2,TITLE_HEIGHT));
		mTitleLabel.setEditable(false);
		mTitleLabel.setFont(PosFormUtil.getTextFieldBoldFont());
		add(mTitleLabel);
		
		add(mSoftKeyPad);
		
		if(mTarget!=null){
			mSoftKeyPad.setText(mTarget.getText());
			mSoftKeyPad.setTextSeleted();
//			mSoftKeyPad.setFocus();
		}
			
	}
	
	public void setTitle(String title){
		mTitleLabel.setText(title);
	}
	
	public void setListner(IPosSoftKeypadListner listner) {
		mListner=listner;
		mSoftKeyPad.setListner(new PosSoftKeypadAdapter() {
			@Override
			public void onCancel() {
				setVisible(false);
				if(mListner!=null)
					mListner.onCancel();
				dispose();
			}
			
			@Override
			public void onAccepted(String text) {
				setVisible(false);
				if(mTarget!=null)
					mTarget.setText(text);
				if(mListner!=null)
					mListner.onAccepted(text);
				dispose();
			}
			@Override
			public void onTextChanged(String text) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	public void setText(String text){
		mSoftKeyPad.setText(text);
	}
	
}
