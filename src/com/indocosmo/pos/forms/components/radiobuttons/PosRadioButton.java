/**
 * 
 */
package com.indocosmo.pos.forms.components.radiobuttons;

import javax.swing.Icon;
import javax.swing.JRadioButton;

import com.indocosmo.pos.common.utilities.PosResUtil;

/**
 * @author anand
 *
 */
@SuppressWarnings("serial")
public class PosRadioButton extends JRadioButton {
	private Object mTag=null;
	private static Icon normalIcon =PosResUtil.getImageIconFromResource("radiobutton_normal.png");
	private static Icon selectedIcon = PosResUtil.getImageIconFromResource("radiobutton_selected.png");
	public PosRadioButton(){
		super();
		setDefaultIcons();
	}
	public PosRadioButton(String name){
		super(name);
		setDefaultIcons();

	}
	private void setDefaultIcons() {
		if(normalIcon!=null&&selectedIcon!=null){
			setIcon(normalIcon);
			setSelectedIcon(selectedIcon);
		}
	}
	public Object getTag(){
		return mTag;
	}
	public void setTag(Object tag){
		mTag=tag;
	}


}
