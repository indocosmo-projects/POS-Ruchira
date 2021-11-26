/**
 * 
 */
package com.indocosmo.pos.common.enums;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import javax.swing.KeyStroke;

import com.indocosmo.pos.data.providers.shopdb.PosCustomerProvider;
import com.indocosmo.pos.forms.components.objectbrowser.IPosBrowsableItem;

/**
 * @author sandhya
 *
 */
public enum PosReportsType implements IPosBrowsableItem{
	
	DAILYADVANCE(1,"DAILY ADVANCE"),
	DAILYBALANCE(2,"DAILY BALANCE"),
	ITEMWISE_STATEMENT(3,"ITEM WISE SALES ORDER"),
	ITEMWISE_DET_STATEMENT(4,"ITEM WISE DETAILED SALES ORDER "),
	VOID_REPORT(5,"VOID ITEM LIST"),
	REFUND_REPORT(6,"REFUND ITEM LIST");

//	private static final Map<Integer,PosSalesOrderReportType> mLookup 
//	= new HashMap<Integer,PosSalesOrderReportType>();
//
//	static {
//		for(PosSalesOrderReportType pt : EnumSet.allOf(PosSalesOrderReportType.class))
//			mLookup.put(pt.getValue(), pt);
//	}
//
//	private int mValue;
//
//	private PosSalesOrderReportType(int value) {
//		this.mValue = value;
//	}
//
//	public int getValue() { return mValue; }
//
//	public static PosSalesOrderReportType get(int value) { 
//		return mLookup.get(value); 
//	}
private static final Map<Integer, PosReportsType> mLookup = new HashMap<Integer, PosReportsType>();
	

	static {
		for (PosReportsType item : EnumSet.allOf(PosReportsType.class))
			mLookup.put(item.getCode(), item);
	}

	private int mCode;
	private String mTitle;
	private boolean mIsVisible=true;
	
	private PosReportsType(int code, String title) {
		mCode = code;
		mTitle=title;
	}
	
	/**
	 * @return
	 */
	public int getCode() {
		
		return mCode;
	}
	
	 
	public static PosReportsType get(int code) {
		return mLookup.get(code);
	}
 

	/**
	 * @param isVisible the isVisible to set
	 */
	public void setVisible(boolean isVisible) {
		this.mIsVisible = isVisible;
		  
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
