package com.indocosmo.pos.forms.components.textfields.listners;


public interface IPosClickableTextFieldListner {
	public void onValueSelected(Object value);
//	public void onValueSelected(int value);
//	public void onValueSelected(IPosBrowsableItem item);
//	public void onValueSelected(IPosSearchableItem item);
	public void onReset();
}
