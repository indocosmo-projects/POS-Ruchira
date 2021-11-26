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
public enum EFTPolStatus {
	IDLE("80", "Idle"),
	BUSY("81" , "Busy");

	private static final Map<String,EFTPolStatus> mLookup 
	= new HashMap<String,EFTPolStatus>();

	static {
		for(EFTPolStatus rc : EnumSet.allOf(EFTPolStatus.class))
			mLookup.put(rc.getValue(), rc);
	}

	private String mValue;
	private String mCaption;

	private EFTPolStatus(String value, String caption) {
		this.mValue = value;
		this.mCaption=caption;
	}

	public String getValue() { return mValue; }
	public String getCaption() { return mCaption; }

	public static EFTPolStatus get(String value) { 
		return mLookup.get(value); 
	}
}
