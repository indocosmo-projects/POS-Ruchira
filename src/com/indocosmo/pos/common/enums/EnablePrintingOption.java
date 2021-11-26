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

public enum EnablePrintingOption{
	
	NO(0), 
	ASK(1),
	FORCE(2);

	private static final Map<Integer,EnablePrintingOption> mLookup 
	= new HashMap<Integer,EnablePrintingOption>();

	static {
		for(EnablePrintingOption pt : EnumSet.allOf(EnablePrintingOption.class))
			mLookup.put(pt.getValue(), pt);
	}

	private int mValue;

	private EnablePrintingOption(int value) {
		this.mValue = value;
	}

	public int getValue() { return mValue; }

	public static EnablePrintingOption get(int value) { 
		return mLookup.get(value); 
	}
}