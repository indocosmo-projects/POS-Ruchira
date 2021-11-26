package com.indocosmo.pos.forms.listners;

import com.indocosmo.pos.data.beans.BeanCustomer;
import com.indocosmo.pos.data.beans.BeanOrderCustomer;

public interface IPosSelectCustomerFormListner {
	public void onOkClicked(BeanCustomer posCustomerItem, BeanOrderCustomer orderCustomer);
	//		public void onCancelClicked();
}
