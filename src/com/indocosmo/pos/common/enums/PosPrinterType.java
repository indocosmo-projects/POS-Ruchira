/**
 * 
 */
package com.indocosmo.pos.common.enums;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * @author jojesh
 *
 */

public enum PosPrinterType{
     
	RECEIPT(1),
	KITCHEN(2);

	private static final Map<Integer,PosPrinterType> mLookup 
	= new HashMap<Integer,PosPrinterType>();

	static {
		for(PosPrinterType pt : EnumSet.allOf(PosPrinterType.class))
			mLookup.put(pt.getValue(), pt);
	}

	private int mValue;

	private PosPrinterType(int value) {
		this.mValue = value;
	}

	public int getValue() { return mValue; }

	public static PosPrinterType get(int value) { 
		return mLookup.get(value); 
	}
}