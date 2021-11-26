/**
 * 
 */
package com.indocosmo.pos.forms.components.textfields;

import javax.swing.RootPaneContainer;
import javax.swing.SwingConstants;
import javax.swing.text.JTextComponent;

import com.indocosmo.pos.common.utilities.PosFormUtil;
import com.indocosmo.pos.common.utilities.PosNumberUtil;
import com.indocosmo.pos.forms.PosNumKeypadForm;
import com.indocosmo.pos.forms.components.keypads.PosNumKeypad.KeypadTypes;
import com.indocosmo.pos.forms.listners.IPosNumKeyPadFormListner;

/**
 * @author jojesh
 *
 */
@SuppressWarnings("serial")
public class PosTouchableNumberField extends PosTouchableFieldBase {

	private PosNumKeypadForm mNumKeyPadForm;
	protected Object mMaxValue=null;
	protected Object mMinValue=null;
	
	public PosTouchableNumberField(RootPaneContainer parent, int width, int height) {
		super(parent, width, height);
		// TODO Auto-generated constructor stub
	}

	public PosTouchableNumberField(RootPaneContainer parent, int width) {
		super(parent, width);
		// TODO Auto-generated constructor stub
	}

	public PosTouchableNumberField(RootPaneContainer parent) {
		super(parent);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected void initComponent() {
		super.initComponent();
		mTextFiled.setHorizontalAlignment(SwingConstants.RIGHT);
	}
	/* (non-Javadoc)
	 * @see com.indocosmo.pos.forms.components.textfields.PosTouchableFieldBase#setDocType()
	 */
	@Override
	protected void setDocType() {
		mTextFiled.setDocument(PosNumberUtil.createNumericDocument());
	}
	

	@Override
	protected void showBroswerForm() {
		showNumKeypadForm(KeypadTypes.Numeric);
	}
		
	/**
	 * 
	 */
	protected void showNumKeypadForm(KeypadTypes keypadType){
		if(mNumKeyPadForm==null){
			mNumKeyPadForm=new PosNumKeypadForm(keypadType);
			mNumKeyPadForm.setOnValueChanged(new IPosNumKeyPadFormListner() {
				@Override
				public void onValueChanged(String value) {
					onValueSelected(value);
				}
				@Override
				public void onValueChanged(JTextComponent target, String oldValue) {}
			});
		}
		mNumKeyPadForm.setValue(mTextFiled.getText());
		mNumKeyPadForm.setTitle(mTitle);
		if(mFieldDocument!=null) mNumKeyPadForm.setFiledDocument(mFieldDocument);
		PosFormUtil.showLightBoxModal(mParent,mNumKeyPadForm);
	}
	
	@Override
	protected void onValueSelected(Object value) {
		
		String numValue=String.valueOf(value);
//		mTextFiled.setText(numValue);
		setText(numValue);
		super.onValueSelected(value);
	}
	
	/* (non-Javadoc)
	 * @see com.indocosmo.pos.forms.components.textfields.PosTouchableFieldBase#setText(java.lang.String)
	 */
	@Override
	public void setText(String text) {
		
		super.setText(text);
		if(text!=null && text.trim().length()>0)
			mTextFiled.setCaretPosition(text.trim().length()-1);
	}

	public String getSelectedValue() {
		return getText();
	}

	public void setSelectedValue(String value) {
		setText(value);
	}
	
	@Override
	public void reset() {
		super.reset();
		if(mMinValue!=null){
			setSelectedValue(String.valueOf(mMinValue));
		}
	}
	
	public void setMaxValue(Object max){
		mMaxValue=max;
		reset();
	}
	
	public Object getMaxValue(){
		return mMaxValue;
	}
	
	public Object getMinValue(){
		return mMinValue;
	}
	
	public void setMinValue(Object min){
		mMinValue=min;
		reset();
	}
	
	public boolean doValidate(){
		
		try{
			if(mMaxValue!=null){
				final int value=Integer.parseInt(mTextFiled.getText());
				final int maxValue=(int)mMaxValue; 
				if(value>maxValue){
					PosFormUtil.showErrorMessageBox(mParent, "The value entered should be less than or equal to "+String.valueOf(mMaxValue));
					mTextFiled.requestFocus();
					return false;
				}
			}
			if(mMinValue!=null){
				final int value=Integer.parseInt(mTextFiled.getText());
				final int minValue=(int)mMinValue; 
				if(value<minValue){
					PosFormUtil.showErrorMessageBox(mParent, "The value entered should be greater than or equal to "+String.valueOf(mMinValue));
					mTextFiled.requestFocus();
					return false;
				}
			}
		}catch (Exception e){
			PosFormUtil.showErrorMessageBox(mParent, "Please enter a valid input.");
			mTextFiled.requestFocus();
			return false;
		}
		return true;
	}
	
	
	

}
