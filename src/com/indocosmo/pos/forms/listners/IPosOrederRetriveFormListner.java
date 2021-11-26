package com.indocosmo.pos.forms.listners;

import com.indocosmo.pos.data.beans.BeanOrderHeader;
import com.indocosmo.pos.data.beans.BeanOrderQHeader;

public interface IPosOrederRetriveFormListner {
	public void  onItemSelected(Object sender, int queueNo,BeanOrderQHeader item);
	public void  onItemSelected(Object sender,BeanOrderHeader item);
}
