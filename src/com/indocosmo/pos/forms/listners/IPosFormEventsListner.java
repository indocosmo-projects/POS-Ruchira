package com.indocosmo.pos.forms.listners;

public interface IPosFormEventsListner {
	public boolean onOkButtonClicked();
	public boolean onCancelButtonClicked();
	public void onResetButtonClicked();
}
