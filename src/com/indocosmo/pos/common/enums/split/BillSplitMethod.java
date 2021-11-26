/**
 * 
 */
package com.indocosmo.pos.common.enums.split;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import javax.swing.KeyStroke;

import com.indocosmo.pos.forms.components.objectbrowser.IPosBrowsableItem;

/**
 * @author jojesh-13.2
 *
 */
public enum BillSplitMethod  implements IPosBrowsableItem{

	Simple(0,"Simple"),
	Advance(1,"Advanced");
	
	private static final Map<Integer, BillSplitMethod> mLookup = new HashMap<Integer, BillSplitMethod>();

	static {
		for (BillSplitMethod item : EnumSet.allOf(BillSplitMethod.class))
			mLookup.put(item.getCode(), item);
	}

	private int mCode;
	private String mTitle;
	private BillSplitMethod(int code, String title) {
		mCode = code;
		mTitle=title;
	}

	public int getCode() {
		return mCode;
	}

	public static BillSplitMethod get(int code) {
		return mLookup.get(code);
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.pos.components.objectbrowser.IPosBrowsableItem#getCaption()
	 */
	public String getDisplayText() {
		// TODO Auto-generated method stub
		return mTitle;
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.pos.forms.components.objectbrowser.IPosBrowsableItem#getItemCode()
	 */
	@Override
	public Object getItemCode() {

		return mCode;
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
