package com.indocosmo.pos.forms.components;

@Deprecated
public abstract class PosFormContainerPanel {
	
	public boolean onOkButtonClicked(){
		return true;
	};
	
	public void onCancelButtonClicked(){};
	
	public void onResetButtonClicked(){};
	
}
