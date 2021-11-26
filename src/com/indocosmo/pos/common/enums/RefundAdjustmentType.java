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
 * @author sandhya
 *
 */
public enum RefundAdjustmentType implements IPosBrowsableItem{
	
	NONE(0, "None",false),
	QUANTITY(1, "Quantity",true),
	AMOUNT(2, "Amount",true);

	private static final Map<Integer, RefundAdjustmentType> mLookup = new HashMap<Integer, RefundAdjustmentType>();

	static {
		for (RefundAdjustmentType item : EnumSet.allOf(RefundAdjustmentType.class))
			mLookup.put(item.getCode(), item);
	}

	private int mCode;
	private String mTitle;
	private boolean mIsVisible=true;
	
	private RefundAdjustmentType(int code, String title,boolean isVisible) {
		
		mCode = code;
		mTitle=title;
		mIsVisible=isVisible;
	}

	public int getCode() {
		return mCode;
	}

	public static RefundAdjustmentType get(int code) {
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
		return mIsVisible;
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
