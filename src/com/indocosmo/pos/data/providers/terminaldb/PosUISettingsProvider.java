/**
 * 
 */
package com.indocosmo.pos.data.providers.terminaldb;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.sql.rowset.CachedRowSet;

import com.indocosmo.pos.common.PosLog;
import com.indocosmo.pos.data.beans.terminal.BeanUISettings;

/**
 * @author deepak  
 *
 */
public class PosUISettingsProvider extends PosTerminalDBProviderBase {
	
	BeanUISettings mUISetting;
	
	public enum PosUISettingFields{
		SHOW_LIGHT_BOX("show_lightbox"),
		USE_COLORED_BUTTONS("use_coloreditem_buttons"),
		SHOW_ALTER_NAMES("show_alternative_titles"),
		SHOW_SALEITEM_DETAILS("show_saleitem_details"),
		USE_SINGLE_ROW_GRID("use_singleline_billgrid"),
		SHOW_DETAILS_IN_BILL_SUMMARY("show_details_in_bill_summary"),
		HIDE_CLASS_PANEL("hide_class_panel");
		
		String mCode;
		PosUISettingFields(String code){
			mCode=code;
		}
		
		public String getCode(){
			return mCode;
		}
	}
	
	/**
	 * @throws SQLException 
	 * 
	 */
	public PosUISettingsProvider() throws SQLException {
		super("ui_settings");
		loadSettings();
	}
		
	
	
	/**
	 * @throws SQLException 
	 * 
	 */
	private void loadSettings() throws SQLException {
		CachedRowSet crs=getData();
		if (crs != null && crs.next()) {
			Map<PosUISettingFields, Boolean> uiSettingMap = new HashMap<PosUISettingFields, Boolean>();
			for (PosUISettingFields field : PosUISettingFields.values()){
				uiSettingMap.put(field, crs.getBoolean(field.getCode()));
			}
			mUISetting = new BeanUISettings();
			mUISetting.setSettingMaps(uiSettingMap);
		}
		
	}
	
	public BeanUISettings getUISettings(){
		return mUISetting;
		
	}
	
	public void saveSettings(BeanUISettings uisettings){
		try {
			beginTrans();
			deleteData();
			PreparedStatement prep;
			final String SQL="insert into "+
							mTablename+
							"(show_lightbox,use_coloreditem_buttons," +
							"show_alternative_titles,show_saleitem_details," +
							"use_singleline_billgrid,show_details_in_bill_summary,hide_class_panel)values" +
							"(?,?,?,?,?,?,?)";
			prep = mConnection.prepareStatement(SQL);
			prep.setBoolean(1, uisettings.getSettingMaps().get(PosUISettingFields.SHOW_LIGHT_BOX));
			prep.setBoolean(2, uisettings.getSettingMaps().get(PosUISettingFields.USE_COLORED_BUTTONS));
			prep.setBoolean(3, uisettings.getSettingMaps().get(PosUISettingFields.SHOW_ALTER_NAMES));
			prep.setBoolean(4, uisettings.getSettingMaps().get(PosUISettingFields.SHOW_SALEITEM_DETAILS));
			prep.setBoolean(5, uisettings.getSettingMaps().get(PosUISettingFields.USE_SINGLE_ROW_GRID));
			prep.setBoolean(6, uisettings.getSettingMaps().get(PosUISettingFields.SHOW_DETAILS_IN_BILL_SUMMARY));
			prep.setBoolean(7, uisettings.getSettingMaps().get(PosUISettingFields.HIDE_CLASS_PANEL));
			prep.execute();
			commitTrans();
					
		} catch (SQLException e) {
			PosLog.write(this,"saveSettings", e);
			try {
				rollBack();
			} catch (SQLException e1) {
				PosLog.write(this, "saveSettings", e1);
			}
		}
		
	}

}
