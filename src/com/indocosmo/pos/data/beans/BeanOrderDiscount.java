package com.indocosmo.pos.data.beans;

import com.indocosmo.pos.common.utilities.reflecion.PosBeanUtil;


public class BeanOrderDiscount extends BeanDiscount implements
		Cloneable {

	private boolean mIsNew=false;
	private String mOrderPaymentHdrId;

	
	public BeanOrderDiscount() {
		
	}
	
   public BeanOrderDiscount(BeanDiscount discount) throws Exception {
		
	   PosBeanUtil.copyProperties(discount, this);
	   
	}

	public BeanOrderDiscount clone() {
		BeanOrderDiscount cloneObject = null;
		// try {
		cloneObject = (BeanOrderDiscount) super.clone();
		// } catch (CloneNotSupportedException e) {
		// PosLog.write(this,"clone",e);
		// }
		return cloneObject;
	}

	/**
	 * @return the mIsNew
	 */
	public boolean isNew() {
		return mIsNew;
	}

	/**
	 * @param mIsNew the mIsNew to set
	 */
	public void setNew(boolean isNew) {
		this.mIsNew= isNew;
	}
	
	/**
	 * @return the mOrderPaymentHdrId
	 */
	public String getOrderPaymentHdrId() {
		return mOrderPaymentHdrId;
	}
	/**
	 * @param mOrderPaymentHdrId the mOrderPaymentHdrId to set
	 */
	public void setOrderPaymentHdrId(String orderPaymentHdrId) {
		this.mOrderPaymentHdrId = orderPaymentHdrId;
	}
}
