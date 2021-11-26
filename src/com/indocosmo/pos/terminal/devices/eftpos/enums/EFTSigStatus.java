/**
 * 
 */
package com.indocosmo.pos.terminal.devices.eftpos.enums;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * @author jojesh
 *
 */
public enum EFTSigStatus{
	YES("YES","YES"),
	NO("NO","NO");

	private static final Map<String,EFTSigStatus> mLookup 
	= new HashMap<String,EFTSigStatus>();

	static {
		for(EFTSigStatus rc : EnumSet.allOf(EFTSigStatus.class))
			mLookup.put(rc.getValue(), rc);
	}

	private String mValue;
	private String mCaption;

	private EFTSigStatus(String value, String caption) {
		this.mValue = value;
		this.mCaption=caption;
	}

	public String getValue() { return mValue; }
	public String getCaption() { return mCaption; }

	public static EFTSigStatus get(String value) { 
		return mLookup.get(value); 
	}
}
