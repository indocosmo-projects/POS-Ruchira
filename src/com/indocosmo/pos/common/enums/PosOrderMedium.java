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
 * @author sandhya-13.2
 *
 */
public enum PosOrderMedium  implements IPosBrowsableItem{

	PHONE(1,"Phone"),
	DIRECT(2, "Direct"),
	MAIL(3, "Mail") ,
	EBS(4, "EBS") ;
	
	private static final Map<Integer, PosOrderMedium> mLookup = new HashMap<Integer, PosOrderMedium>();
	

	static {
		for (PosOrderMedium item : EnumSet.allOf(PosOrderMedium.class))
			mLookup.put(item.getCode(), item);
	}

	private int mCode;
	private String mTitle;
 
	private PosOrderMedium(int code, String title ) {
		mCode = code;
		mTitle=title;
	}
	
	public int getCode() {
		return mCode;
	}
	
	public static PosOrderMedium get(int code) {
		return mLookup.get(code);
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.pos.forms.components.objectbrowser.IPosBrowsableItem#getItemCode()
	 */
	@Override
	public Object getItemCode() {
		// TODO Auto-generated method stub
		return mCode;
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.pos.forms.components.objectbrowser.IPosBrowsableItem#getDisplayText()
	 */
	@Override
	public String getDisplayText() {
		// TODO Auto-generated method stub
		return mTitle;
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
