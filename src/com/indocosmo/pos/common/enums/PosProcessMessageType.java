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
public enum PosProcessMessageType {
	
	WARNNING("#0000FF"),
	ERROR("#FF0000"),
	INFO("#000000"),
	SUCCEESS("#00FF00");
	
	private static final Map<String,PosProcessMessageType> mLookup 
	= new HashMap<String,PosProcessMessageType>();

	static {
		for(PosProcessMessageType rc : EnumSet.allOf(PosProcessMessageType.class))
			mLookup.put(rc.getTextColor(), rc);
	}

	private String mColor;
	
	private PosProcessMessageType(String color) {
		this.mColor = color;
	}

	public String getTextColor() { return mColor; }

}
