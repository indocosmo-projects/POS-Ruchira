package com.indocosmo.pos.data.beans;

public final class BeanMainMenu {

	private String mCode;
	private String mName;

	public String getCode() {
		return mCode;
	}

	public void setCode(String code) {
		this.mCode = code;
	}

	public String getName() {
		return mName;
	}

	public void setName(String name) {
		this.mName = name;
	}

	public BeanMainMenu() {

	}
	
	public BeanMainMenu(String code, String name) {
		this.mCode = code;
		this.mName = name;
	}
	
	
}
