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
 * @author deepak
 *
 */
public enum BankAccountType implements IPosBrowsableItem{
	
	CHQ(1,"CHQ"),
	SAV(2,"SAV"),
	CRD(3,"CRD");
	
	private static final Map<Integer, BankAccountType> mLookup = new HashMap<Integer, BankAccountType>();

	static {
		for (BankAccountType item : EnumSet.allOf(BankAccountType.class))
			mLookup.put(item.getCode(), item);
	}

	private int mCode;
	private String mTitle;
	private BankAccountType(int code, String title) {
		mCode = code;
		mTitle=title;
	}

	public int getCode() {
		return mCode;
	}

	public static BankAccountType get(int code) {
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
	 * @see com.indocosmo.pos.components.objectbrowser.IPosBrowsableItem#getItemCode()
	 */
	@Override
	public Object getItemCode() {
		// TODO Auto-generated method stub
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
