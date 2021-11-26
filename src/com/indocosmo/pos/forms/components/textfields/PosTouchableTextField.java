/**
 * 
 */
package com.indocosmo.pos.forms.components.textfields;

import javax.swing.RootPaneContainer;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import com.indocosmo.pos.common.utilities.PosFormUtil;
import com.indocosmo.pos.forms.PosSoftKeyPadForm;
import com.indocosmo.pos.forms.components.keypads.listners.PosSoftKeypadAdapter;

/**
 * @author jojesh
 *
 */
@SuppressWarnings("serial")
public class PosTouchableTextField extends PosTouchableFieldBase {
	

	private int maxLength=0;
	
	private PosSoftKeyPadForm mSoftKeyPadForm;

	public PosTouchableTextField(RootPaneContainer parent, int width, int height) {
		super(parent, width, height);
		// TODO Auto-generated constructor stub
	}

	public PosTouchableTextField(RootPaneContainer parent, int width) {
		super(parent, width);
		// TODO Auto-generated constructor stub
	}

	public PosTouchableTextField(RootPaneContainer parent) {
		super(parent);
		// TODO Auto-generated constructor stub
	}
//

	@Override
	protected void showBroswerForm() {
		showSoftKeypadForm();
	}
	

	/* (non-Javadoc)
	 * @see com.indocosmo.pos.forms.components.textfields.PosTouchableFieldBase#setDocType()
	 */
	@Override
	protected void setDocType() {
	
		mTextFiled.setDocument(new PlainDocument(){
			
			/* (non-Javadoc)
			 * @see javax.swing.text.PlainDocument#insertString(int, java.lang.String, javax.swing.text.AttributeSet)
			 */
			@Override
			public void insertString(int offs, String str, AttributeSet a)
					throws BadLocationException {
				if (str == null)
				      return;

				if(maxLength>0){
				    if ((getLength() + str.length()) <= maxLength) {
				      super.insertString(offs, str, a);
				    };
				}else
				      super.insertString(offs, str, a);
				
			}
		});
	}
	
		
	/**
	 * 
	 */
	private void showSoftKeypadForm(){
		if(mSoftKeyPadForm==null){
			mSoftKeyPadForm=new PosSoftKeyPadForm();
			mSoftKeyPadForm.setListner(new PosSoftKeypadAdapter() {
				@Override
				public void onAccepted(String text) {
					onValueSelected(text);
				}
			});
		}
		mSoftKeyPadForm.setText(mTextFiled.getText());
		mSoftKeyPadForm.setTitle(mTitle);
		PosFormUtil.showLightBoxModal(mParent,mSoftKeyPadForm);
	}
	
	@Override
	protected void onValueSelected(Object value) {
		String numValue=String.valueOf(value);
		mTextFiled.setText(numValue);
		super.onValueSelected(value);
	}
	
	@Override
	protected void onResetButtonClicked() {
		setText(String.valueOf(mDiffaultValue));
		super.onResetButtonClicked();
	}
	
	public String getSelectedValue() {
		return getText();
	}

	public void setSelectedValue(String value) {
		setText(value);
	}

	/**
	 * @return the maxLength
	 */
	public int getMaxLength() {
		return maxLength;
	}

	/**
	 * @param maxLength the maxLength to set
	 */
	public void setMaxLength(int maxLength) {
		this.maxLength = maxLength;
	}
}
