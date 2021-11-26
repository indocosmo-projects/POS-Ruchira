/**
 * 
 */
package com.indocosmo.pos.common.enums.device;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import javax.swing.KeyStroke;

import com.indocosmo.pos.forms.components.objectbrowser.IPosBrowsableItem;

/**
 * @author jojesh
 *
 */
public enum PrinterType implements IPosBrowsableItem{

	Receipt80(1, "Receipt 80mm"),
	Normal(2 , "Normal");

	private static final Map<Integer,PrinterType> mLookup 
	= new HashMap<>();

	static {
		for(PrinterType rc : EnumSet.allOf(PrinterType.class))
			mLookup.put(rc.getValue(), rc);
	}

	private int mValue;
	private String mCaption;

	private PrinterType(int value, String caption) {
		this.mValue = value;
		this.mCaption=caption;
	}

	public int getValue() { return mValue; }
	public String getDisplayText() { return mCaption; }

	public static PrinterType get(int value) { 
		return mLookup.get(value); 
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.pos.components.objectbrowser.IPosBrowsableItem#getItemCode()
	 */
	@Override
	public Object getItemCode() {
		return mValue;
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.pos.forms.components.objectbrowser.IPosBrowsableItem#isVisibleInUI()
	 */
	@Override
	public boolean isVisibleInUI() {
		
		return true;
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.pos.forms.components.objectbrowser.IPosBrowsableItem#getKeyStroke()
	 */
	@Override
	public KeyStroke getKeyStroke() {
		
		return null;
	}
}