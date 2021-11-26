package com.indocosmo.pos.common.enums;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum PaymentReceiptItemGrouping {
	None(0),
	GroupByParentClass(1),
	GroupBySubClass(2);
	
	private static final Map<Integer,PaymentReceiptItemGrouping> mLookup 
	= new HashMap<Integer,PaymentReceiptItemGrouping>();

	static {
		for(PaymentReceiptItemGrouping rc : EnumSet.allOf(PaymentReceiptItemGrouping.class))
			mLookup.put(rc.getValue(), rc);
	}

	private int mValue;
	
	private PaymentReceiptItemGrouping(int value) {
		this.mValue = value;
	}

	public int getValue() { return mValue; }
	
	public static PaymentReceiptItemGrouping get(int value) { 
		return mLookup.get(value); 
	}

}
