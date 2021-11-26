/**
 * 
 */
package com.indocosmo.pos.data.beans;

import javax.swing.KeyStroke;

import com.indocosmo.pos.common.utilities.PosCurrencyUtil;
import com.indocosmo.pos.forms.components.search.IPosSearchableItem;

/**
 * @author jojesh
 *
 */
public class BeanCashoutRecentItem implements IPosSearchableItem{
	
	
	private String remarks;
	private Integer mId;
	/**
	 * @return the remarks
	 */
	public String getRemarks() {
		return remarks;
	}
	/**
	 * @param remarks the remarks to set
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	/**
	 * @return the Id
	 */
	public int getId() {
		return mId;
	}
	/**
	 * @param Id to set
	 */
	public void setId(int mId) {
		this.mId = mId;
	}
	
	/**
	 * 
	 */
	public static String[] SEARCH_FIELD_LIST = {"getId","getRemarks"};

	/**
	 * 
	 */
	public static String[] SEARCH_COLUMN_NAMES = {"#","Remarks"};

	/**
	 * 
	 */
	public static int[] SEARCH_FIELD_WIDTH = {50};
	
	/**
	 * 
	 */
	public static String[] SEARCH_FIELD_FORMAT = {"",""};
	
	/* (non-Javadoc)
	 * @see com.indocosmo.pos.forms.components.objectbrowser.IPosBrowsableItem#getItemCode()
	 */
	@Override
	public Object getItemCode() {

		return getRemarks();
	}
	/* (non-Javadoc)
	 * @see com.indocosmo.pos.forms.components.objectbrowser.IPosBrowsableItem#getDisplayText()
	 */
	@Override
	public String getDisplayText() {

		return getRemarks();
	}
	/* (non-Javadoc)
	 * @see com.indocosmo.pos.forms.components.objectbrowser.IPosBrowsableItem#isVisibleInUI()
	 */
	@Override
	public boolean isVisibleInUI() {

		return true;
	}
	/* (non-Javadoc)
	 * @see com.indocosmo.pos.forms.components.search.IPosSearchableItem#getFiledList()
	 */
	@Override
	public String[] getFieldList() {

		return SEARCH_FIELD_LIST;
	}
	/* (non-Javadoc)
	 * @see com.indocosmo.pos.forms.components.search.IPosSearchableItem#getFiledTitleList()
	 */
	@Override
	public String[] getFieldTitleList() {
		
		return SEARCH_COLUMN_NAMES;
	}
	/* (non-Javadoc)
	 * @see com.indocosmo.pos.forms.components.search.IPosSearchableItem#getFiledWidthList()
	 */
	@Override
	public int[] getFieldWidthList() {

		return SEARCH_FIELD_WIDTH;
	}
	/* (non-Javadoc)
	 * @see com.indocosmo.pos.forms.components.search.IPosSearchableItem#getFiledFormatList()
	 */
	@Override
	public String[] getFieldFormatList() {
		// TODO Auto-generated method stub
		return SEARCH_FIELD_FORMAT;
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
