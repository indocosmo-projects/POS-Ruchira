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
public enum EFTMessageType{
	POL("POL","POLL"),
	LOG("LOG","LOGON"),
	DSP("DSP","DISPLAY"),
	SIG("SIG","SIGNATURE"),
	PUR("PUR","PURCHASE"),
	MAN("MAN","MAN PURCHASE"),
	CAN("CAN","CANCEL"),
	ERR("ERR","ERROR");
	
	private static final Map<String,EFTMessageType> mLookup 
	= new HashMap<String,EFTMessageType>();

	static {
		for(EFTMessageType rc : EnumSet.allOf(EFTMessageType.class))
			mLookup.put(rc.getValue(), rc);
	}

	private String mValue;
	private String mCaption;

	private EFTMessageType(String value, String caption) {
		this.mValue = value;
		this.mCaption=caption;
	}

	public String getValue() { return mValue; }
	public String getCaption() { return mCaption; }

	public static EFTMessageType get(String value) { 
		return mLookup.get(value); 
	}
}