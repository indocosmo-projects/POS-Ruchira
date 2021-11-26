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
public enum PosPaymentOption  implements IPosBrowsableItem{

	ASK(0,"ASK",false),
	STANDARD(1, "STANDARD"),
	SPLIT(2, "SPLIT") ,
	QUICKCASH(3, "QUICK CASH") ,
	QUICKCARD(4, "QUICK CARD") ,
	QUICKCREDIT(5, "QUICK CREDIT") ,
	ONLINE(6, "ONLINE") ;
	
	private static final Map<Integer, PosPaymentOption> mLookup = new HashMap<Integer, PosPaymentOption>();
	

	static {
		for (PosPaymentOption item : EnumSet.allOf(PosPaymentOption.class))
			mLookup.put(item.getCode(), item);
	}

	private int mCode;
	private String mTitle;
	private boolean showInUI=true;
	
	private PosPaymentOption(int code, String title ) {
		mCode = code;
		mTitle=title;
	}
	
	private PosPaymentOption(int code, String title, boolean showInUI ) {
		mCode = code;
		mTitle=title;
		this.showInUI =showInUI;
	}

	public int getCode() {
		return mCode;
	}
	
	

	public static PosPaymentOption get(int code) {
		return mLookup.get(code);
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.pos.components.objectbrowser.IPosBrowsableItem#getCaption()
	 */
	@Override
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
		
		return showInUI;
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
