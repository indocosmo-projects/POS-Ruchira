/**
 * 
 */
package com.indocosmo.pos.common.enums;

import java.util.EnumSet;
import java.util.HashMap;

/**
 * @author jojesh-13.2
 *
 */
public enum PosDbType {

	MYSQL("mysql"),
	MARIADB("mariadb"),
	SQLITE("sqlite");
	
	private static final HashMap<String,PosDbType> mLookUp = new HashMap<String, PosDbType>();
	
	static{
		for(PosDbType item: EnumSet.allOf(PosDbType.class))
			mLookUp.put(item.getCode(),item);
	}
	
	private String mCode;
	
	private PosDbType(String code){
		this.mCode=code;
	}

	public String getCode() {
		
		return mCode;
	}
	
	public static PosDbType get(String code){
		return mLookUp.get(code);
	}
	
}
