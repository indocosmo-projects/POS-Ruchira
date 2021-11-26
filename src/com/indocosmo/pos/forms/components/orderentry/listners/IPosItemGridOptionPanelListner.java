package com.indocosmo.pos.forms.components.orderentry.listners;

public interface IPosItemGridOptionPanelListner {
	public void onUpClicked();
	public void onDownClicked();
	public void onNexClicked();
	public void onPreviousClicked();
	public void onAddClicked();
	public void onEditClicked();
	public void onDeleteClicked();
	public void onQuantityChanged(double value);
	public void onSearchClicked(String itemCode);
}
