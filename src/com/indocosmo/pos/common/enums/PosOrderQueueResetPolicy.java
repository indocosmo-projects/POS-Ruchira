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
public enum PosOrderQueueResetPolicy {

	NORESET(0),
	DAILY(1);
	
	private static final HashMap<Integer,PosOrderQueueResetPolicy> mLookUp = new HashMap<Integer, PosOrderQueueResetPolicy>();
	
	static{
		for(PosOrderQueueResetPolicy item: EnumSet.allOf(PosOrderQueueResetPolicy.class))
			mLookUp.put(item.getCode(),item);
	}
	
	private int mCode;
	
	private PosOrderQueueResetPolicy(int code){
		this.mCode=code;
	}

	public int getCode() {
		
		return mCode;
	}
	
	public static PosOrderQueueResetPolicy get(int code){
		return mLookUp.get(code);
	}
	
}
