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
public enum MessageDisplayStatus{
	Shop(1),
	POS(1),
	BOTH(2),
	READ(3);
	 
	
	private static final Map<Integer,MessageDisplayStatus> mLookup 
	= new HashMap<Integer,MessageDisplayStatus>();

	static {
		for(MessageDisplayStatus rc : EnumSet.allOf(MessageDisplayStatus.class))
			mLookup.put(rc.getValue(), rc);
	}

	private int mValue;
	
	private MessageDisplayStatus(int value) {
		this.mValue = value;
	}

	public int getValue() { return mValue; }
	
	public static MessageDisplayStatus get(int value) { 
		return mLookup.get(value); 
	}

}