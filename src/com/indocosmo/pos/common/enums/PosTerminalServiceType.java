/**
 * 
 */
package com.indocosmo.pos.common.enums;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import javax.swing.KeyStroke;

import com.indocosmo.pos.forms.components.objectbrowser.IPosBrowsableItem;

/**
 * @author jojesh-13.2
 *
 */
public enum PosTerminalServiceType implements IPosBrowsableItem {

	RWS(1,"Retail/Wholesale"),
	Restaurant(2,"Restaurant Counter"),
	KOTTab(3,"KOT Tab");

	private static final Map<Integer, PosTerminalServiceType> mLookup 
	= new HashMap<Integer, PosTerminalServiceType>();

	static {
		for( PosTerminalServiceType rc : EnumSet.allOf( PosTerminalServiceType.class))
			mLookup.put(rc.getValue(), rc);
	}

	private int mValue;
	private String mCaption;

	private  PosTerminalServiceType(int value, String caption) {
		this.mValue = value;
		this.mCaption=caption;
	}

	public int getValue() { return mValue; }
	public String getDisplayText() { return mCaption; }

	public static  PosTerminalServiceType get(int value) { 
		return mLookup.get(value); 
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.pos.components.objectbrowser.IPosBrowsableItem#getItemCode()
	 */
	@Override
	public Object getItemCode() {
		return getValue();
	}
	
	/* (non-Javadoc)
	 * @see com.indocosmo.pos.forms.components.objectbrowser.IPosBrowsableItem#isVisibleInUI()
	 */
	@Override
	public boolean isVisibleInUI() {
		// TODO Auto-generated method stub
		return true;
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.pos.forms.components.objectbrowser.IPosBrowsableItem#getKeyStroke()
	 */
	@Override
	public KeyStroke getKeyStroke() {
		// TODO Auto-generated method stub
		return null;
	}
}
