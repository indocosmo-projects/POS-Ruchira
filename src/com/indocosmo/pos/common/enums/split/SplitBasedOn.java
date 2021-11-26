/**
 * 
 */
package com.indocosmo.pos.common.enums.split;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import javax.swing.KeyStroke;

import com.indocosmo.pos.common.PosEnvSettings;
import com.indocosmo.pos.forms.components.objectbrowser.IPosBrowsableItem;

/**
 * @author jojesh-13.2
 *
 */
public enum SplitBasedOn  implements IPosBrowsableItem{

	Custom(3,"Item","Item",BillSplitMethod.Advance),
	Count(2,"Person","123",BillSplitMethod.Simple),
	Amount(0,"Amount",PosEnvSettings.getInstance().getCurrencySymbol(),BillSplitMethod.Simple), 
	Percentage(1,"Amount(%)","%",BillSplitMethod.Simple),
	Seat(4,"Table/Seat","Table",BillSplitMethod.Advance);

	private static final Map<Integer, SplitBasedOn> mLookup = new HashMap<Integer, SplitBasedOn>();
	static {
		for (SplitBasedOn item : EnumSet.allOf(SplitBasedOn.class))
			mLookup.put(item.getCode(), item);
	}

	private int mCode;
	private String mTitle;
	private String mShortTitle;
	private BillSplitMethod mSplitMethod;
	private SplitBasedOn(int code, String title,String shortTitle,BillSplitMethod splitMethod) {
		mCode = code;
		mTitle=title;
		mShortTitle=shortTitle;
		mSplitMethod=splitMethod;
	}

	public int getCode() {
		return mCode;
	}

	public static SplitBasedOn get(int code) {
		return mLookup.get(code);
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.pos.components.objectbrowser.IPosBrowsableItem#getCaption()
	 */
	public String getDisplayText() {
		// TODO Auto-generated method stub
		return mTitle;
	}
	
	/**
	 * @return
	 */
	public String getShortText() {
		// TODO Auto-generated method stub
		return mShortTitle;
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.pos.forms.components.objectbrowser.IPosBrowsableItem#getItemCode()
	 */
	@Override
	public Object getItemCode() {

		return mCode;
	}
	
	public BillSplitMethod getBillSplitMethod(){
		
		return mSplitMethod;
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
