package com.indocosmo.pos.common;

import java.sql.SQLException;

import com.indocosmo.pos.common.utilities.PosFormUtil;
import com.indocosmo.pos.data.beans.terminal.BeanUISettings;
import com.indocosmo.pos.data.providers.terminaldb.PosUISettingsProvider;
import com.indocosmo.pos.data.providers.terminaldb.PosUISettingsProvider.PosUISettingFields;

/**
 * This class handles all UI related settings. 
 * @author jojesh
 *
 */
public final class PosUISettings {
	
	private static PosUISettings mPosUISettings;
	
	private BeanUISettings mPosUISettingsObject;

	/**
	 * private constructor to make the class singleton model
	 */
	private PosUISettings(){
		
		try {
			mPosUISettingsObject=new  PosUISettingsProvider().getUISettings();
		} catch (SQLException e) {
			PosLog.write(this, "setUISettings", e);
			PosFormUtil.showErrorMessageBox(null,"Error in UI settings. Please contact Administrator.");
		}
	}
	
	/**
	 * Returns the single instance of this class.
	 * @return
	 */
	public static PosUISettings getInstance(){
		if(mPosUISettings==null)
			mPosUISettings=new PosUISettings();
		return mPosUISettings;
	}
	
	/* 
	 * This is a singleton class. so we must avoid cloning the objects.
	 *  DO NOT ALTER THE CODE.
	 */
	@Override
	protected Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException();
	}
	
	/**
	 * returns whether the class panel should be hidden or not.
	 * @return
	 */
//	public boolean isClassPanelHidden(){
////		return false; //make it true. class panel is deprecated 
//		return mPosUISettingsObject.getSettingMaps().get(PosUISettingFields.HIDE_CLASS_PANEL);
//	}
	
//	public int getNumberOfSubclassPerRow(){
//		return 4;
//	}
//	
	/**
	 * Whether to show the details of sale item in the item controls
	 * @return true/false
	 */
//	public boolean showSaleItemDetails(){
//		return mPosUISettingsObject.getSettingMaps().get(PosUISettingFields.SHOW_SALEITEM_DETAILS);
//	}
	
//	public void setShowSaleItemDetails(boolean show){
//		mShowSaleItemDetails=show;
//	}
	
	/**
	 * Whether to show the light box
	 * @return
	 */
//	public boolean showLightBox(){
//		return mPosUISettingsObject.getSettingMaps().get(PosUISettingFields.SHOW_LIGHT_BOX);
//	}
	
//	public void setShowLightBox(boolean show){
//		mShowLightBox=show;
//	}
	
	/**
	 * whether to use colored item buttons
	 * @return
	 */
//	public boolean useColoredItemButtons(){
//		return mPosUISettingsObject.getSettingMaps().get(PosUISettingFields.USE_COLORED_BUTTONS);
//	}
	
//	public void setUseColoredItemButtons(boolean use){
//		mUseColoredItemButtons=use;
//	}
	
	/**
	 * Whether to show alternative titles on the
	 * @return
	 */
//	public boolean showAlternativeTitles(){
//		return mPosUISettingsObject.getSettingMaps().get(PosUISettingFields.SHOW_ALTER_NAMES);
//	}
	
//	public void setShowAlternativeTitles(boolean show){
//		mShowAlternativeTitles=show;
//	}

	/**
	 * @return the mUseSingleLineBillGrid
	 */
//	public boolean useSingleLineBillGrid() {
//		return mPosUISettingsObject.getSettingMaps().get(PosUISettingFields.USE_SINGLE_ROW_GRID);
//	}
	
	public boolean showDetailsInBillSummary(){
		return true;//mPosUISettingsObject.getSettingMaps().get(PosUISettingFields.SHOW_DETAILS_IN_BILL_SUMMARY);
	}

}
