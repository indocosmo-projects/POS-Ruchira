package com.indocosmo.pos.forms.components.textfields;

import javax.swing.JTextField;

@SuppressWarnings("serial")
public class PosTextField extends JTextField {

	private Object mTag=null;
	
	public void setTag(Object tag){
		this.mTag=tag;
	}
	
	public Object getTag(){
		return this.mTag;
	}
}
