package com.indocosmo.pos.data.beans;

import com.indocosmo.pos.common.PosLog;

public final class BeanSaleItemAttribute implements Cloneable {
	
	private int mIndex;
	private int mSelectedOptionIndex=0;
	private String mName;
	private String[] mOptions;

	public BeanSaleItemAttribute(int index,String name, String[] options){
		mIndex=index;
		mName=name;
		mOptions=options;
	}
	
	public BeanSaleItemAttribute(int index,String name, String options){
		mIndex=index;
		mName=name;
		mOptions=options.split(",");
	}
	
	public String getArtributeName(){
		return mName;
	}
	
	public String getSelectedOption(){
		return mOptions[mSelectedOptionIndex];
	}
	
	public int getAttributeIndex(){
		return mIndex;
	}
	
	public void setSelectedOptionIndex(int index){
		mSelectedOptionIndex=index;
	}
	
	public int getSelectedOptionIndex(){
		return mSelectedOptionIndex;
	}
	
	public String[] getAttributeOptions() {

		return mOptions;
	}
	@Override
	public BeanSaleItemAttribute clone() throws CloneNotSupportedException {
		BeanSaleItemAttribute cloneObject=null;
		try {
			cloneObject=(BeanSaleItemAttribute) super.clone();
		} catch (CloneNotSupportedException e) {
			PosLog.write(this,"clone",e);
		}
		return cloneObject;
	}
}
