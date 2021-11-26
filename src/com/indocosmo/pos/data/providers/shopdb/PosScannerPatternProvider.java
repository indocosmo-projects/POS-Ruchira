/**
 * 
 */
package com.indocosmo.pos.data.providers.shopdb;

import java.sql.SQLException;
import java.util.HashMap;

import javax.sql.rowset.CachedRowSet;

import com.indocosmo.pos.common.PosLog;
import com.indocosmo.pos.common.utilities.codescanner.enums.PosScannerPatternType;

/**
 * @author jojesh
 *
 */
public class PosScannerPatternProvider extends PosShopDBProviderBase {
	
	private static PosScannerPatternProvider instance;
	private HashMap<PosScannerPatternType, String> patterns;
	
	public static PosScannerPatternProvider getInstance(){
		
		if(instance==null)
			instance=new PosScannerPatternProvider();
		
		return instance;
	}

	/**
	 * 
	 */
	private PosScannerPatternProvider() {
		super("pos_scanner_patterns");
		
	}
	
	/**
	 * @param type
	 * @return
	 */
	public String getPattern(PosScannerPatternType type){
		
		return getPatterns().get(type);
	}

	/**
	 * @return
	 */
	public HashMap<PosScannerPatternType, String> getPatterns(){
		
		if(patterns==null){
			patterns=new HashMap<PosScannerPatternType, String>();
			loadItems();
		}
		
		return patterns;
	}

	/**
	 * 
	 */
	private void loadItems(){
		
		final CachedRowSet res=getData();
		try {
			
			while (res.next()) {
				
				final String pattern=res.getString("pattern");
				final PosScannerPatternType  type=PosScannerPatternType.get(res.getInt("id")); 
				patterns.put(type, pattern);
			}
			res.close();
		} catch (SQLException e) {
			PosLog.write(this,"loadItems",e);
		}
	}

}
