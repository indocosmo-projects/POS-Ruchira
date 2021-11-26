package com.indocosmo.pos.data.beans;


public class BeanItemDiscount extends BeanDiscount implements
		Cloneable {

	public BeanItemDiscount() {
		setIsPromotion(false);
	}

	public BeanItemDiscount clone() {
		BeanItemDiscount cloneObject = null;
		// try {
		cloneObject = (BeanItemDiscount) super.clone();
		// } catch (CloneNotSupportedException e) {
		// PosLog.write(this,"clone",e);
		// }
		return cloneObject;
	}

}
