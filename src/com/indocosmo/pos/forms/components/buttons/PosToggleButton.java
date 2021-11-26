package com.indocosmo.pos.forms.components.buttons;

import java.awt.Color;

import javax.swing.Icon;

import com.indocosmo.pos.forms.components.buttons.listners.IPosToggleButtonListner;

@SuppressWarnings("serial")
public class PosToggleButton extends PosSelectButton {
	
	private IPosToggleButtonListner mOnToggleListner;

	public PosToggleButton(String text) {
		super(text);
		// TODO Auto-generated constructor stub
	}

	public PosToggleButton(Icon image) {
		super(image);
		// TODO Auto-generated constructor stub
	}

	public PosToggleButton() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected void onSelected(){
			setForeground(Color.WHITE);
			if(mOnToggleListner!=null)
				mOnToggleListner.onToggle(this,isSelected());
		}
	
	@Override
	protected void onActionPerformed(){
		setSelected(!isSelected());
	}
	
	public void setOnToggleListner(IPosToggleButtonListner listner){
		mOnToggleListner=listner;
	}
	

}
