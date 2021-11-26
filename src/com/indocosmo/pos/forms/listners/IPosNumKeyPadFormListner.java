package com.indocosmo.pos.forms.listners;

import javax.swing.text.JTextComponent;

public interface IPosNumKeyPadFormListner {
	public void  onValueChanged(JTextComponent target, String oldValue);
	public void  onValueChanged(String value);
}
