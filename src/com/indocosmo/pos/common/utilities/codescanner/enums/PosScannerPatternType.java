package com.indocosmo.pos.common.utilities.codescanner.enums;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum PosScannerPatternType {
	USER(1),
	MEMBER(2),
	WEIGHING(3),
	ORDER(4),
	TRAY(5),
	ITEMTRAY(6),
	OTHER(0);
	
	private static final Map<Integer,PosScannerPatternType> mLookup 
	= new HashMap<Integer,PosScannerPatternType>();

	static {
		for(PosScannerPatternType rc : EnumSet.allOf(PosScannerPatternType.class))
			mLookup.put(rc.getValue(), rc);
	}

	private int mValue;
	
	private PosScannerPatternType(int value) {
		this.mValue = value;
	}

	public int getValue() { return mValue; }
	
	public static PosScannerPatternType get(int value) { 
		return mLookup.get(value); 
	}

}
