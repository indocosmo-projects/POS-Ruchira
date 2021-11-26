/**
 * 
 */
package com.indocosmo.pos.common.enums.device;

import gnu.io.SerialPort;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import javax.swing.KeyStroke;

import com.indocosmo.pos.forms.components.objectbrowser.IPosBrowsableItem;

/**
 * @author jojesh
 *
 */
public enum PortParity implements IPosBrowsableItem{

	None(SerialPort.PARITY_NONE, "None"),
	Even(SerialPort.PARITY_EVEN , "Even"),
	Odd(SerialPort.PARITY_ODD, "Odd"),
	Mark(SerialPort.PARITY_MARK, "Mark"),
	Space(SerialPort.PARITY_SPACE, "Space");

	private static final Map<Integer,PortParity> mLookup 
	= new HashMap<Integer,PortParity>();

	static {
		for(PortParity rc : EnumSet.allOf(PortParity.class))
			mLookup.put(rc.getValue(), rc);
	}

	private int mValue;
	private String mCaption;

	private PortParity(int value, String caption) {
		this.mValue = value;
		this.mCaption=caption;
	}

	public int getValue() { return mValue; }
	public String getDisplayText() { return mCaption; }

	public static PortParity get(int value) { 
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