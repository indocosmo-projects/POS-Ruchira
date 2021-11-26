
/**
 * @author jojesh
 *This class will act as a structure from pos items super class .
 *Add required additional properties and functionalities for superclass.
 */

package com.indocosmo.pos.data.beans;

import java.util.ArrayList;


public final class BeanItemClass extends BeanItemClassBase{
	
	private ArrayList<BeanItemClassBase> mSubClassList;
	
	public BeanItemClass(){
		
	}
	
	public ArrayList<BeanItemClassBase> getSubList() {
		return mSubClassList;
	}

	public void setSubClassList(ArrayList<BeanItemClassBase> subClassList) {
		this.mSubClassList = subClassList;
	}


}
  