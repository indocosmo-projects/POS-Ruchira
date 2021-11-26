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
public enum EFTPurchaseStatus{
	ACCEPTED("00","ACCEPTED"),
	DECLINED("01","DECLINED"),
	CANCELED("04","CANCELED");

	private static final Map<String,EFTPurchaseStatus> mLookup 
	= new HashMap<String,EFTPurchaseStatus>();

	static {
		for(EFTPurchaseStatus rc : EnumSet.allOf(EFTPurchaseStatus.class))
			mLookup.put(rc.getValue(), rc);
	}

	private String mValue;
	private String mCaption;

	private EFTPurchaseStatus(String value, String caption) {
		this.mValue = value;
		this.mCaption=caption;
	}

	public String getValue() { return mValue; }
	public String getCaption() { return mCaption; }

	public static EFTPurchaseStatus get(String value) { 
		return mLookup.get(value); 
	}
}
