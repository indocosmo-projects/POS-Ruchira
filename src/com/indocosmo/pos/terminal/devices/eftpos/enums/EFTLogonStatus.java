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
public enum EFTLogonStatus{
	ACCEPTED("00","ACCEPTED"),
	DECLINED("01","DECLINED");

	private static final Map<String,EFTLogonStatus> mLookup 
	= new HashMap<String,EFTLogonStatus>();

	static {
		for(EFTLogonStatus rc : EnumSet.allOf(EFTLogonStatus.class))
			mLookup.put(rc.getValue(), rc);
	}

	private String mValue;
	private String mCaption;

	private EFTLogonStatus(String value, String caption) {
		this.mValue = value;
		this.mCaption=caption;
	}

	public String getValue() { return mValue; }
	public String getCaption() { return mCaption; }

	public static EFTLogonStatus get(String value) { 
		return mLookup.get(value); 
	}
}
