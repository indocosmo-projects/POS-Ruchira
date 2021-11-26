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
public enum PosPrintOption  implements IPosBrowsableItem{

	PAYMENTRECEIPT(1, "Bill Receipt"),
	KITCHENRECEIPT(2, "Kitchen Tickets"),
	BARCODELABEL(3, "Barcode Labels"), 
	ITEMLABEL(4, "Item Labels"), 
	RESHITO(5, "Reshito"),
	SALESORDER(6, "Sales Order");
	
	private static final Map<Integer, PosPrintOption> mLookup = new HashMap<Integer, PosPrintOption>();
	

	static {
		for (PosPrintOption item : EnumSet.allOf(PosPrintOption.class))
			mLookup.put(item.getCode(), item);
	}

	private int mCode;
	private String mTitle;
	private boolean showInUI=true;
	
	private PosPrintOption(int code, String title ) {
		mCode = code;
		mTitle=title;
	}
	
	private PosPrintOption(int code, String title, boolean showInUI ) {
		mCode = code;
		mTitle=title;
		this.showInUI =showInUI;
	}

	public int getCode() {
		return mCode;
	}
	
	

	public static PosPrintOption get(int code) {
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
