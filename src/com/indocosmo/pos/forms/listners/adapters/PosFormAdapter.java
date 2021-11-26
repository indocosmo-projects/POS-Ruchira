package com.indocosmo.pos.forms.listners.adapters;

import com.indocosmo.pos.forms.listners.IPosFormEventsListner;

public class PosFormAdapter implements IPosFormEventsListner {

	@Override
	public boolean onOkButtonClicked() {
		return true;
	}

	@Override
	public boolean onCancelButtonClicked() {
		return true;

	}

	@Override
	public void onResetButtonClicked() {
	}

}
