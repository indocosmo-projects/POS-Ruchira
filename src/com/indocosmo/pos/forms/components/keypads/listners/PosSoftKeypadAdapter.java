package com.indocosmo.pos.forms.components.keypads.listners;

import java.awt.event.KeyEvent;

import com.indocosmo.pos.forms.components.keypads.PosSoftKeyPad.IPosSoftKeypadListner;


public class PosSoftKeypadAdapter implements IPosSoftKeypadListner {

	@Override
	public void onAccepted(String text) {		
	}

	@Override
	public void onCancel() {
	}

	@Override
	public void onTextChanged(String text) {
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.pos.forms.components.keypads.PosSoftKeyPad.IPosSoftKeypadListner#onAccepted(int)
	 */
	@Override
	public void onAccepted(int index) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.pos.forms.components.keypads.PosSoftKeyPad.IPosSoftKeypadListner#onKeyPressed(java.awt.event.KeyEvent)
	 */
	@Override
	public void onKeyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

//	@Override
//	public void onAccepted(int index) {
//		
//	}

}
