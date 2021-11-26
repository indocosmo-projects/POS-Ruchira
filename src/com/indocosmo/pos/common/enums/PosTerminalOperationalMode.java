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
public enum PosTerminalOperationalMode implements IPosBrowsableItem {
	
	Master(0,"Master"),
	Slave(1,"Slave"),
	KOTSlave(2,"KOT Slave");

	private static final Map<Integer, PosTerminalOperationalMode> mLookup 	= new HashMap<Integer, PosTerminalOperationalMode>();

	static {
		for( PosTerminalOperationalMode rc : EnumSet.allOf( PosTerminalOperationalMode.class))
			mLookup.put(rc.getValue(), rc);
	}

	private int mValue;
	private String mCaption;

	private  PosTerminalOperationalMode(int value, String caption) {
		this.mValue = value;
		this.mCaption=caption;
	}

	public int getValue() { 
		
		return mValue; 
	}
	
	public String getDisplayText() { 
		
		return mCaption; 
	}

	public static  PosTerminalOperationalMode get(int value) {
		
		return mLookup.get(value); 
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.pos.forms.components.objectbrowser.IPosBrowsableItem#getItemCode()
	 */
	@Override
	public Object getItemCode() {
		// TODO Auto-generated method stub
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
