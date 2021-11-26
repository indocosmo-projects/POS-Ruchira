/**
 * 
 */
package com.indocosmo.pos.common.utilities.codescanner.enums;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * @author jojesh-13.2
 *
 */
public enum ScannerCodeType {

	User(1),
	Member(2),
	WeighingMachine(3);
	
	private static final Map<Integer,ScannerCodeType> mLookup 
	= new HashMap<Integer,ScannerCodeType>();

	static {
		for(ScannerCodeType rc : EnumSet.allOf(ScannerCodeType.class))
			mLookup.put(rc.getValue(), rc);
	}

	private int mValue;
	
	private ScannerCodeType(int value) {
		this.mValue = value;
	}

	public int getValue() { return mValue; }
	
	public static ScannerCodeType get(int value) { 
		return mLookup.get(value); 
	}
	
}
