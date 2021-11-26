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
public enum PosQueueNoResetPolicy {

	NORESET(0),
	DAILY(1);
	
	private static final HashMap<Integer,PosQueueNoResetPolicy> mLookUp = new HashMap<Integer, PosQueueNoResetPolicy>();
	
	static{
		for(PosQueueNoResetPolicy item: EnumSet.allOf(PosQueueNoResetPolicy.class))
			mLookUp.put(item.getCode(),item);
	}
	
	private int mCode;
	
	private PosQueueNoResetPolicy(int code){
		this.mCode=code;
	}

	public int getCode() {
		
		return mCode;
	}
	
	public static PosQueueNoResetPolicy get(int code){
		return mLookUp.get(code);
	}
	
}
