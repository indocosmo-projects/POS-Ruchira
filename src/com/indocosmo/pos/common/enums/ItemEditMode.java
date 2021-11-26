/**
 * 
 */
package com.indocosmo.pos.common.enums;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * @author jojesh-13.2
 *
 */
public enum ItemEditMode{
	Simple(1),
	Extended(2);
	 
	
	private static final Map<Integer,ItemEditMode> mLookup 
	= new HashMap<Integer,ItemEditMode>();

	static {
		for(ItemEditMode rc : EnumSet.allOf(ItemEditMode.class))
			mLookup.put(rc.getValue(), rc);
	}

	private int mValue;
	
	private ItemEditMode(int value) {
		this.mValue = value;
	}

	public int getValue() { return mValue; }
	
	public static ItemEditMode get(int value) { 
		return mLookup.get(value); 
	}

}