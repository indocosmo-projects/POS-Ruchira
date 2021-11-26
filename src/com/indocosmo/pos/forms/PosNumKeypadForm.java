package com.indocosmo.pos.forms;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.WindowEvent;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.Document;

import com.indocosmo.pos.common.utilities.PosFormUtil;
import com.indocosmo.pos.common.utilities.PosNumberUtil;
import com.indocosmo.pos.forms.components.keypads.PosNumKeypad;
import com.indocosmo.pos.forms.components.keypads.PosNumKeypad.KeypadTypes;
import com.indocosmo.pos.forms.components.keypads.listners.PosNumkeypadListnerAdapter;
import com.indocosmo.pos.forms.listners.IPosNumKeyPadFormListner;
import com.indocosmo.pos.forms.listners.adapters.PosWindowListenerAdapter;

@SuppressWarnings("serial")
public final class PosNumKeypadForm extends JDialog {
	

	private JPanel  mContentPane;
	private PosNumKeypad mPosNumKeypad;
	private JTextField mTitleLabel;
	private IPosNumKeyPadFormListner mValueChangedListner;
	
	private static final int PANEL_CONTENT_H_GAP=5;
	private static final int PANEL_CONTENT_V_GAP=1;
	
	private static final int BORDER_WIDTH=5;
	private static final int TITLE_HEIGHT=30;
	private static final int LAYOUT_WIDTH=PosNumKeypad.LAYOUT_WIDTH+PANEL_CONTENT_H_GAP*2;
	private static final int LAYOUT_HEIGHT=PosNumKeypad.LAYOUT_HEIGHT+PANEL_CONTENT_V_GAP*3+TITLE_HEIGHT+BORDER_WIDTH*2;
	private Double mMinValue=null, mMaxValue=null;
	private KeypadTypes mKeyPadType = KeypadTypes.Numeric;
	
	public PosNumKeypadForm(){
		
		initControls();
	}
	public PosNumKeypadForm(KeypadTypes keyType){
		this.mKeyPadType =keyType;
		initControls();
	}
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PosNumKeypadForm frame = new PosNumKeypadForm();
					
					frame.setFiledDocument(PosNumberUtil.createNumericDocument(false));
					PosFormUtil.showModal(frame);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public void setCancelEnabled(boolean canCancel){
		mPosNumKeypad.setCancelEnabled(canCancel);
	}
	
	private String mMinValidationMessage;
	public void setMinValue(Double minValue, String validationMessage){
		mMinValidationMessage=validationMessage;
		mMinValue=minValue;
	}
	
	private String mMaxValidationMessage;
	public void setMaxValue(Double maxValue,String validationMessage){
		mMaxValidationMessage=validationMessage;
		mMaxValue=maxValue;
	}
	
	private void initControls(){
//		setSize(PosNumKeypad.LAYOUT_WIDTH+PANEL_CONTENT_H_GAP*2,PosNumKeypad.LAYOUT_HEIGHT+TITLE_HEIGHT+PANEL_CONTENT_V_GAP*3);
		setSize(LAYOUT_WIDTH, LAYOUT_HEIGHT);
		setLayout(new FlowLayout(FlowLayout.CENTER,PANEL_CONTENT_H_GAP,PANEL_CONTENT_V_GAP));
		setPreferredSize(new Dimension(LAYOUT_WIDTH, LAYOUT_HEIGHT));
		mTitleLabel=new JTextField("Soft Key Pad");
		mTitleLabel.setHorizontalAlignment(JTextField.CENTER);
		mTitleLabel.setPreferredSize(new Dimension(LAYOUT_WIDTH-PANEL_CONTENT_H_GAP*2,TITLE_HEIGHT));
		mTitleLabel.setEditable(false);
		mTitleLabel.setFont(PosFormUtil.getTextFieldBoldFont());
		add(mTitleLabel);
//		if(mIsPassword){
		mPosNumKeypad=new PosNumKeypad(mKeyPadType);
//		mPosNumKeypad=new PosNumKeypad();
//		}else{
//			mPosNumKeypad=new PosNumKeypad();
//		}
		mPosNumKeypad.setOnCancelListner(new PosNumkeypadListnerAdapter() {
			@Override
			public void onCancelButton() {
				dispose();
				setVisible(false);
			}
		});
		
		mPosNumKeypad.setOnAcceptListner(new PosNumkeypadListnerAdapter(){
			@Override
			public void onAcceptButton(String value) {
				if(mKeyPadType == KeypadTypes.Numeric)
					minmaxValidation(value);
				else{
					setVisible(false);
					if(mValueChangedListner!=null)
						mValueChangedListner.onValueChanged(value);
					dispose();
				}
			}

			private void minmaxValidation(String value) {
				final double inValue=PosNumberUtil.parseDoubleSafely(value);
				if((mMinValue!=null && inValue<mMinValue))
					PosFormUtil.showErrorMessageBox(null,mMinValidationMessage);
				else if((mMaxValue!=null && inValue>=mMaxValue))
					PosFormUtil.showErrorMessageBox(null,mMaxValidationMessage);
				else{
					setVisible(false);
					if(mValueChangedListner!=null)
						mValueChangedListner.onValueChanged(value);
					dispose();
				}
			}
		});
		add(mPosNumKeypad);
		
		addWindowListener(new PosWindowListenerAdapter() {
		/* (non-Javadoc)
		 * @see com.indocosmo.pos.forms.listners.PosWindowListenerAdapter#windowActivated(java.awt.event.WindowEvent)
		 */
		@Override
		public void windowActivated(WindowEvent e) {
			// TODO Auto-generated method stub
			super.windowActivated(e);
			mPosNumKeypad.requestFocus();
		}
		});
	}
	
	public void setOnValueChanged(IPosNumKeyPadFormListner listner){
		mValueChangedListner=listner;
	}
	
	public void setValue(String value){
		mPosNumKeypad.setValue(value);
	}
	
	private String mTitle;
	public void setTitle(String title){
		mTitle=title;
		mTitleLabel.setText(mTitle);
	}
	
	private Document mFieldDocument;
	public void setFiledDocument(Document document){
		mFieldDocument=document;
		mPosNumKeypad.setFiledDocument(mFieldDocument);
	}
	
	/**
	 * @param value
	 */
	public void setDefaltValue(String value){
		
		mPosNumKeypad.setDefaultValue(value);
	}
	
}
