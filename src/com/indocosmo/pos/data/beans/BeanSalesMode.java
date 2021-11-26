package com.indocosmo.pos.data.beans;


public final class BeanSalesMode extends BeanMasterBase{

//	private int mId;
//	private String mCode;
//	private String mName;

//	public String getCode() {
//		return mCode;
//	}
//
//	public void setCode(String code) {
//		this.mCode = code;
//	}
//
//	public String getName() {
//		return mName;
//	}
//
//	public void setName(String name) {
//		this.mName = name;
//	}
//	
//	public int getId() {
//		return mId;
//	}
//
//	public void setId(int id) {
//		this.mId =id;
//	}

	public BeanSalesMode() {

	}
	
	public BeanSalesMode(int id,String code, String name) {
		this.setId(id);
		this.setCode(code);
		this.setName(name);
		}
	
}
