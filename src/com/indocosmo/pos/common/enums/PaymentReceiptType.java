package com.indocosmo.pos.common.enums;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum PaymentReceiptType {
	Normal(1),
	Inclusive(2),
	Exclusive(3);
	
	private static final Map<Integer,PaymentReceiptType> mLookup 
	= new HashMap<Integer,PaymentReceiptType>();

	static {
		for(PaymentReceiptType rc : EnumSet.allOf(PaymentReceiptType.class))
			mLookup.put(rc.getValue(), rc);
	}

	private int mValue;
	
	private PaymentReceiptType(int value) {
		this.mValue = value;
	}

	public int getValue() { return mValue; }
	
	public static PaymentReceiptType get(int value) { 
		return mLookup.get(value); 
	}

}
