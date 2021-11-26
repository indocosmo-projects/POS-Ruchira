/**
 * 
 */
package com.indocosmo.pos.common.enums;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import javax.swing.KeyStroke;

import com.indocosmo.pos.common.PosEnvSettings;
import com.indocosmo.pos.data.providers.shopdb.PosCustomerProvider;
import com.indocosmo.pos.forms.components.objectbrowser.IPosBrowsableItem;

/**
 * @author jojesh
 *
 */
public enum PosOrderServiceTypes implements IPosBrowsableItem{
	
	TAKE_AWAY(
			1,
			PosEnvSettings.getInstance().getUISetting().getServiceTakeAwayTitle(),
			"TAKEAWAY",
			PosEnvSettings.getInstance().getUISetting().getOrderEntryUISettings().getDefaultCustomerTakeAway()),
	HOME_DELIVERY(
			2,
			PosEnvSettings.getInstance().getUISetting().getServiceHomeDelTitle(),
			"HOMEDEL",
			PosEnvSettings.getInstance().getUISetting().getOrderEntryUISettings().getDefaultCustomerHomeDelivery()),
	TABLE_SERVICE(
			3,
			PosEnvSettings.getInstance().getUISetting().getServiceTableTitle(),
			"TABLESERV",
			PosEnvSettings.getInstance().getUISetting().getOrderEntryUISettings().getDefaultCustomerTableService()),
	WHOLE_SALE(
			4,
			PosEnvSettings.getInstance().getUISetting().getServiceWholeSaleTitle(),
			"WHOLESALE",
			PosEnvSettings.getInstance().getUISetting().getOrderEntryUISettings().getDefaultCustomerWholesale()),
	SALES_ORDER(
					5,
					PosEnvSettings.getInstance().getUISetting().getServiceSalesOrderTitle(),
					"SALESORDER",
					PosEnvSettings.getInstance().getUISetting().getOrderEntryUISettings().getDefaultCustomerSO());
	
	private static final Map<Integer, PosOrderServiceTypes> mLookup = new HashMap<Integer, PosOrderServiceTypes>();
	

	static {
		for (PosOrderServiceTypes item : EnumSet.allOf(PosOrderServiceTypes.class))
			mLookup.put(item.getCode(), item);
	}

	private int mCode;
	private String mTitle;
	private String mSysCode;
	private boolean mIsVisible=false;
	private String defaultCustomerCode=PosCustomerProvider.DEF_CUST_CODE;
	
	private PosOrderServiceTypes(int code, String title,String sysCode,String defCutCode) {
		mCode = code;
		mTitle=title;
		mSysCode=sysCode;
		defaultCustomerCode=defCutCode;
	}
	
	/**
	 * @param custCode
	 */
	public void setDefualtCustomerCode(String custCode){
		
		this.defaultCustomerCode=custCode;
		
	}
	
	/**
	 * @return
	 */
	public String getDefualtCustomerCode(){
		
		return this.defaultCustomerCode;
		
	}

	/**
	 * @return
	 */
	public int getCode() {
		
		return mCode;
	}
	
	/**
	 * @return
	 */
	public String getSysCode() {
		return mSysCode;
	}

	public static PosOrderServiceTypes get(int code) {
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
