package com.indocosmo.pos.forms.messageboxes.listners;

import com.indocosmo.pos.forms.listners.IPosMessageBoxFormListner;

public abstract class PosMessageBoxFormListnerAdapter implements IPosMessageBoxFormListner {

	@Override
	public void onOkButtonPressed() {

	}

	@Override
	public void onCancelButtonPressed() {

	}

	@Override
	public void onYesButtonPressed() {

	}

	@Override
	public void onNoButtonPressed() {

	}
	public boolean onCancelClicked(){
		return true;
		
	}

}
