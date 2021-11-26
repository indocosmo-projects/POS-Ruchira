/**
 * 
 */
package com.indocosmo.pos.forms.components.checkboxes;

import javax.swing.JCheckBox;

/**
 * @author deepak
 *
 */
public class PosCheckBox extends JCheckBox {
private Object mTag=null;
	
	/**
 * @param string
 */
	public PosCheckBox(String string) {
		super(string);
	}

	/**
	 * 
	 */
	public PosCheckBox() {
		// TODO Auto-generated constructor stub
	}

	public void setTag(Object tag){
		this.mTag=tag;
	}
	
	public Object getTag(){
		return this.mTag;
	}

}
