/**
 * 
 */
package com.indocosmo.pos.data.providers.shopdb;

import java.sql.SQLException;

import javax.sql.rowset.CachedRowSet;

import com.indocosmo.pos.common.PosLog;
import com.indocosmo.pos.data.beans.BeanAbout;
import com.indocosmo.pos.data.beans.BeanAppVersion;
import com.indocosmo.pos.data.beans.BeanShop;

/**
 * @author 	Sandhya
 * @since 21st Feb 2018
 */
public class PosAboutProvider extends PosShopDBProviderBase {

//	private ArrayList<PosStation> mPosPosStationItemList;
	/**
	 * 
	 */
	public PosAboutProvider() {
		super("about");
	}
	
	
	public BeanAbout getAboutDetails() throws Exception{
		
		final BeanAbout about = new BeanAbout() ;
		CachedRowSet crs = null;

		try {
			crs =  getData();
			if (crs.next()) {
				about.setContents(crs.getString("contents"));
				about.setCopyright(crs.getString("copyright"));
			}
			crs.close();
			
		} catch (SQLException e) {
			PosLog.write(this,"getAboutDetails",e);
			throw new Exception("Failed get about information. Please check the log for more details.");
		}
		return about;
	} 
}
