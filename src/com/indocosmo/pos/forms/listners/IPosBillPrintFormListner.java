package com.indocosmo.pos.forms.listners;

import com.indocosmo.pos.data.beans.BeanOrderHeader;

public interface IPosBillPrintFormListner {
	public void onRePrintClicked(Object sender,BeanOrderHeader oh);
	public void onRePrintKitchenReceiptClicked(Object sender,BeanOrderHeader oh);
	public void onRePrintBarcodeClicked(Object sender,BeanOrderHeader oh);
	public void onRePrintReshitoClicked(Object sender,BeanOrderHeader oh);
	public void onRePrintItemLabelClicked(Object sender,BeanOrderHeader oh);
	public void onDoneClicked();
	/**
	 * 
	 */
	public void onwholeBillPrint(Object sender);
	public void onRePrintClicked(Object sender);
	public void onRePrintKitchenReceiptClicked(Object sender);
	public void onRePrintBarcodeClicked(Object sender);
	public void onRePrintItemLabelClicked(Object sender);
	public void onRePrintReshitoClicked(Object sender);
	
}
