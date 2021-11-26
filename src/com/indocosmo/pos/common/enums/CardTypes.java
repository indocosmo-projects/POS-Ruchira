package com.indocosmo.pos.common.enums;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum CardTypes {
	User(1),
	Member(2);
	
	private static final Map<Integer,CardTypes> mLookup 
	= new HashMap<Integer,CardTypes>();

	static {
		for(CardTypes rc : EnumSet.allOf(CardTypes.class))
			mLookup.put(rc.getValue(), rc);
	}

	private int mValue;
	
	private CardTypes(int value) {
		this.mValue = value;
	}

	public int getValue() { return mValue; }
	
	public static CardTypes get(int value) { 
		return mLookup.get(value); 
	}

}
