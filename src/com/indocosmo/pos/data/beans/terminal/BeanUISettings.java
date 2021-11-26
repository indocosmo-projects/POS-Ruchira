/**
 * 
 */
package com.indocosmo.pos.data.beans.terminal;

import java.util.Map;

import com.indocosmo.pos.data.providers.terminaldb.PosUISettingsProvider.PosUISettingFields;

/**
 * @author deepak
 *
 */
public class BeanUISettings {
	
	private Map<PosUISettingFields, Boolean> mSettingMaps;
	
/**
	 * @return the mSettingMaps
	 */
	public Map<PosUISettingFields, Boolean> getSettingMaps() {
		return mSettingMaps;
	}

	/**
	 * @param mSettingMaps the mSettingMaps to set
	 */
	public void setSettingMaps(Map<PosUISettingFields, Boolean> settingMaps) {
		this.mSettingMaps = settingMaps;
	}

}
