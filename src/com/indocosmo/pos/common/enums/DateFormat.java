package com.indocosmo.pos.common.enums;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum DateFormat {
	DDMMYYYY(0,"dd-MM-yyyy"),
	MMDDYYYY(1,"MM-dd-yyyy"),
	YYYYMMDD(2,"yyyy-MM-dd");
	
	private static final Map<Integer,DateFormat> mLookup 
	= new HashMap<Integer,DateFormat>();

	static {
		for(DateFormat rc : EnumSet.allOf(DateFormat.class))
			mLookup.put(rc.getValue(), rc);
	}

 
	private int mValue;
	private String mFormat;
	
	private DateFormat(int value,String format) {
		this.mValue = value;
		this.mFormat=format;
	}
	public int getValue() { return mValue; }
	public String getFormat() { return mFormat; }
	
	public static DateFormat get(int value) { 
		return mLookup.get(value); 
	}
}
