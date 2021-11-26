package com.indocosmo.pos.common.enums;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum TimeFormat {
	HHmmss(0 , "HH:mm:ss"),
	Hmmss(1,"H:mm:ss"),
	HHmm(2, "HH:mm"),
	HHhmm(3, "HH'h'mm");
	private static final Map<Integer,TimeFormat> mLookup 
	= new HashMap<Integer,TimeFormat>();

	static {
		for(TimeFormat rc : EnumSet.allOf(TimeFormat.class))
			mLookup.put(rc.getValue(), rc);
	}

	private int mValue;
	private String mFormat;
	
	private TimeFormat(int value,String format) {
		this.mValue = value;
		this.mFormat=format;
	}
	public int getValue() { return mValue; }
	public String getFormat() { return mFormat; }
	
	public static TimeFormat get(int value) { 
		return mLookup.get(value); 
	}

}
