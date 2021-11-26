/**
 * 
 */
package com.indocosmo.pos.forms.components.textfields;

import javax.swing.JDialog;
import javax.swing.JPasswordField;

import com.indocosmo.pos.forms.components.keypads.PosNumKeypad.KeypadTypes;

/**
 * @author deepak
 *
 */
@SuppressWarnings("serial")
public class PosTouchableNumericPasswordField extends PosTouchableNumberField{
	
	public PosTouchableNumericPasswordField(JDialog parent, int width, int height) {
		super(parent, width, height);
		// TODO Auto-generated constructor stub
	}

	public PosTouchableNumericPasswordField(JDialog parent, int width) {
		super(parent, width);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * @param parent
	 */
	public PosTouchableNumericPasswordField(JDialog parent) {
		super(parent);
		// TODO Auto-generated constructor stub
	}
	
	/* (non-Javadoc)
	 * @see com.indocosmo.pos.components.textfields.PosTouchableFieldBase#createTextFild()
	 */
	@Override
	protected void createTextField() {
			mTextFiled=new JPasswordField();
	}
	
	/* (non-Javadoc)
	 * @see com.indocosmo.pos.components.textfields.PosTouchableFieldBase#getText()
	 */
	@Override
	public String getText() {
		return getPassword();
	}
	
	public String getPassword(){
		return new String(((JPasswordField)mTextFiled).getPassword());
	}
			
	/* (non-Javadoc)
	 * @see com.indocosmo.pos.components.textfields.PosTouchableNumericField#showBroswerForm()
	 */
	@Override
	protected void showBroswerForm() {
		showNumKeypadForm(KeypadTypes.Digital);
	}
	
}
