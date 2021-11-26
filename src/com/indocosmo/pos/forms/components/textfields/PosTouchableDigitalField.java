/**
 * 
 */
package com.indocosmo.pos.forms.components.textfields;

import javax.swing.RootPaneContainer;

import com.indocosmo.pos.common.utilities.PosNumberUtil;
import com.indocosmo.pos.forms.components.keypads.PosNumKeypad.KeypadTypes;

/**
 * @author deepak
 *
 */
public class PosTouchableDigitalField extends PosTouchableNumberField{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public PosTouchableDigitalField(RootPaneContainer parent, int width, int height) {
		super(parent, width, height);
		// TODO Auto-generated constructor stub
	}

	public PosTouchableDigitalField(RootPaneContainer parent, int width) {
		super(parent, width);
		// TODO Auto-generated constructor stub
	}

	
	/**
	 * @param parent
	 */
	public PosTouchableDigitalField(RootPaneContainer parent) {
		super(parent);
		
	}
	
	/* (non-Javadoc)
	 * @see com.indocosmo.pos.forms.components.textfields.PosTouchableFieldBase#setDocType()
	 */
	@Override
	protected void setDocType() {
		mTextFiled.setDocument(PosNumberUtil.createDigitalDocument());
	}
	/* (non-Javadoc)
	 * @see com.indocosmo.pos.components.textfields.PosTouchableNumericField#showBroswerForm()
	 */
	@Override
	protected void showBroswerForm() {
		showNumKeypadForm(KeypadTypes.Digital);
	}
	

}
