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
public enum BillSplitSimpleType implements IPosBrowsableItem{

	Percentage(0,"Amount(%)"), 
	Amount(1,"Amount"),
	Count(2,"Count");

	private static final Map<Integer, BillSplitSimpleType> mLookup = new HashMap<Integer, BillSplitSimpleType>();

	static {
		for (BillSplitSimpleType item : EnumSet.allOf(BillSplitSimpleType.class))
			mLookup.put(item.getCode(), item);
	}

	private int mCode;
	private String mTitle;
	private BillSplitSimpleType(int code, String title) {
		mCode = code;
		mTitle=title;
	}

	public int getCode() {
		return mCode;
	}

	public static BillSplitSimpleType get(int code) {
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
