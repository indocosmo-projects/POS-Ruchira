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
public enum BillSplitAdvacedType implements IPosBrowsableItem{
	
	/*
	 * Should start with 2.
	 * 0 -> Percentage based SimpleType
	 * 1 -> Amount based SimpleType
	 * 2 -> Count 
	 */
	Custom(3,"Custom"), 
	Seat(4,"Seat");
	
	private static final Map<Integer, BillSplitAdvacedType> mLookup = new HashMap<Integer, BillSplitAdvacedType>();

	static {
		for (BillSplitAdvacedType item : EnumSet.allOf(BillSplitAdvacedType.class))
			mLookup.put(item.getCode(), item);
	}

	private int mCode;
	private String mTitle;
	private BillSplitAdvacedType(int code, String title) {
		mCode = code;
		mTitle=title;
	}

	public int getCode() {
		return mCode;
	}

	public static BillSplitAdvacedType get(int code) {
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
