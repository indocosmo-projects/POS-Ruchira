/**
 * 
 */
package com.indocosmo.pos.terminal.devices.eftpos.enums;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import com.indocosmo.pos.terminal.devices.eftpos.PosDeviceEFTConstants;

/**
 * @author jojesh
 *
 */
public enum EFTResponseType{
	ACK(PosDeviceEFTConstants.ACK,"ACK"),
	NAK(PosDeviceEFTConstants.NAK,"NAK"),
	STX(PosDeviceEFTConstants.STX,"STX");

	private static final Map<Integer,EFTResponseType> mLookup 
	= new HashMap<Integer,EFTResponseType>();

	static {
		for(EFTResponseType rc : EnumSet.allOf(EFTResponseType.class))
			mLookup.put(rc.getValue(), rc);
	}

	private int mValue;
	private String mCaption;

	private EFTResponseType(int value, String caption) {
		this.mValue = value;
		this.mCaption=caption;
	}

	public int getValue() { return mValue; }
	public String getCaption() { return mCaption; }

	public static EFTResponseType get(int value) { 
		return mLookup.get(value); 
	}
}
