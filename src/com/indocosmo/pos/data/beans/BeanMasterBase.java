/**
 * 
 */
package com.indocosmo.pos.data.beans;

import javax.swing.KeyStroke;

import com.indocosmo.pos.forms.components.search.IPosSearchableItem;

/**
 * @author jojesh
 *
 */
public abstract class BeanMasterBase extends BeanPosBase implements IPosSearchableItem, Cloneable{
	
	protected int mId;
	protected String mCode;
	protected String mName;
	protected String mDescription;
	protected boolean canShowInUI=true;
	protected boolean isSystem=false;
	
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
	 * @return Code
	 */
	public String getCode() {
		return mCode;
	}
	/**
	 * @param Code to set
	 */
	public void setCode(String mCode) {
		this.mCode = mCode;
	}
	/**
	 * @return the Name
	 */
	public String getName() {
		return mName;
	}
	/**
	 * @param Name to set
	 */
	public void setName(String mName) {
		this.mName = mName;
	}
	/**
	 * @return the Description
	 */
	public String getDescription() {
		return mDescription;
	}
	/**
	 * @param Decription to set
	 */
	public void setDescription(String description) {
		this.mDescription = description;
	}
	
	public static String[] SEARCH_FIELD_TITLE_LIST={"Code","Name"};
	public static String[] SEARCH_FIELD_LIST={"getCode","getName"};
	public static int[] SEARCH_FIELD_WIDTH_LIST={100};
	/* (non-Javadoc)
	 * @see com.indocosmo.pos.components.objectbrowser.IPosBrowsableItem#getItemCode()
	 */
	@Override
	public Object getItemCode() {
		return getCode();
	}
	/* (non-Javadoc)
	 * @see com.indocosmo.pos.components.objectbrowser.IPosBrowsableItem#getDisplayText()
	 */
	@Override
	public String getDisplayText() {
		return getName();
	}
	/* (non-Javadoc)
	 * @see com.indocosmo.pos.components.search.IPosSearchableItem#getFiledList()
	 */
	@Override
	public String[] getFieldList() {
		return SEARCH_FIELD_LIST;
	}
	/* (non-Javadoc)
	 * @see com.indocosmo.pos.components.search.IPosSearchableItem#getFiledTitleList()
	 */
	@Override
	public String[] getFieldTitleList() {

		return SEARCH_FIELD_TITLE_LIST;
	}
	/* (non-Javadoc)
	 * @see com.indocosmo.pos.components.search.IPosSearchableItem#getFiledWidthList()
	 */
	@Override
	public int[] getFieldWidthList() {
		return SEARCH_FIELD_WIDTH_LIST;
	}
	/**
	 * @return the canShowInUI
	 */
	public boolean isVisibleInUI() {
		return canShowInUI;
	}
	/**
	 * @param canShowInUI the canShowInUI to set
	 */
	public void setVisibleInUI(boolean canShowInUI) {
		this.canShowInUI = canShowInUI;
	}
	/* (non-Javadoc)
	 * @see com.indocosmo.pos.forms.components.search.IPosSearchableItem#getFiledFormatList()
	 */
	@Override
	public String[] getFieldFormatList() {
		// TODO Auto-generated method stub
		return null;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	@Override
	protected Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}
	
	/* (non-Javadoc)
	 * @see com.indocosmo.pos.forms.components.objectbrowser.IPosBrowsableItem#getKeyStroke()
	 */
	@Override
	public KeyStroke getKeyStroke() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
	
		BeanMasterBase item=(BeanMasterBase)obj;
		
		return (item.getCode().equals(this.getCode()));
//		return super.equals(obj);
	}
	/**
	 * @return the isSystem
	 */
	public boolean isSystem() {
		return isSystem;
	}
	/**
	 * @param isSystem the isSystem to set
	 */
	public void setSystem(boolean isSystem) {
		this.isSystem = isSystem;
	}
}
