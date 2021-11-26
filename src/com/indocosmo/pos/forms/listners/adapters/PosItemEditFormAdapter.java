package com.indocosmo.pos.forms.listners.adapters;

import com.indocosmo.pos.data.beans.BeanOrderDetail;
import com.indocosmo.pos.data.beans.BeanSaleItem;
import com.indocosmo.pos.forms.listners.IPosItemEditFormListner;

public abstract class PosItemEditFormAdapter implements IPosItemEditFormListner {

	@Override
	public void onItemEdited(BeanSaleItem newItem) {
		// TODO Auto-generated method stub

	}

//	@Override
//	public void onItemEdited(BeanSaleItem newSaleItem, BeanSaleItem oldSaleItem) {
//		// TODO Auto-generated method stub
//
//	}
//
//	@Override
//	public void onComboContentItemEdited(BeanSaleItem newSaleItem,
//			ArrayList<BeanSaleItem> newSubstitutes) {
//		// TODO Auto-generated method stub
//
//	}
	
	/* (non-Javadoc)
	 * @see com.indocosmo.pos.forms.listners.IPosItemEditFormListner#onItemEdited(com.indocosmo.pos.data.beans.BeanOrderDetail, com.indocosmo.pos.data.beans.BeanOrderDetail)
	 */
	@Override
	public void onItemEdited(BeanOrderDetail newItem, BeanOrderDetail oldItem) {
		// TODO Auto-generated method stub
		
	}

}
