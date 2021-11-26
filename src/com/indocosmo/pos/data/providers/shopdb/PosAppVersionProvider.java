/**
 * 
 */
package com.indocosmo.pos.data.providers.shopdb;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.rowset.CachedRowSet;

import com.indocosmo.pos.common.PosConstants;
import com.indocosmo.pos.common.PosLog;
import com.indocosmo.pos.common.utilities.PosFileUtils;
import com.indocosmo.pos.common.utilities.PosNumberUtil;
import com.indocosmo.pos.data.beans.BeanAppVersion;

/**
 * @author 	Sandhya
 * @since 21st Feb 2018
 */
public class PosAppVersionProvider extends PosShopDBProviderBase {

	/**
	 * 
	 */
	public PosAppVersionProvider() {
		super("app_version");
	}
	
	/*
	 * 
	 */
	public BeanAppVersion getAppVerion() throws Exception{
		
		final BeanAppVersion appVersion = new BeanAppVersion() ;
		CachedRowSet crs = null;

		try {
			crs =  getData();
			if (crs.next()) {
				
				appVersion.setMajor(crs.getInt("major"));
				appVersion.setMinor(crs.getInt("minor"));
				appVersion.setPatch(crs.getInt("patch")); 
				appVersion.setBuildNo(crs.getString("build_no"));
				appVersion.setBuildDate(crs.getString("build_date"));
			}
			crs.close();
			
		} catch (SQLException e) {
			PosLog.write(this,"getAppVerion",e);
			throw new Exception("Failed get version information. Please check the log for more details.");
		}
		return appVersion;
	} 
	
	/*
	 * 
	 */
	public void updateAppVersionFromFile() throws Exception{

		Properties mPrintProperties;
		mPrintProperties = PosFileUtils.loadPropertyFile(PosConstants.VERSION_INFO_FILE);
		if (mPrintProperties!=null){
			
			BeanAppVersion appVersion = new BeanAppVersion() ;
			appVersion.setMajor(
					PosNumberUtil.parseIntegerSafely(PosConstants.APP_VERSION_MAJOR));
			appVersion.setMinor(
					PosNumberUtil.parseIntegerSafely(PosConstants.APP_VERSION_MINOR));
			appVersion.setPatch(
					PosNumberUtil.parseIntegerSafely(PosConstants.APP_VERSION_PATCH));
			appVersion.setBuildNo(mPrintProperties.getProperty("build_id","1"));
			appVersion.setBuildDate(
					mPrintProperties.getProperty("build_date","2000-01-01")) ;

			save(appVersion);

		}
	}
	
	/**
	 * @return 
	 * @throws SQLException
	 */
	private PreparedStatement getInsPreparedStatment() throws SQLException{

		final String insert_sql="INSERT INTO "+mTablename+" (`major`, `minor`, `patch`, `build_no`, `build_date` ) VALUES (?,?,?,?,?)";
		return getConnection().prepareStatement(insert_sql);
	}

	/**
	 * @throws SQLException
	 */
	private PreparedStatement getUpdPreparedStatment() throws SQLException {

		final String update_sql = "update "+mTablename+" set " +
				"major=?, minor=?, patch=?, build_no=?, build_date=? ";

		return getConnection().prepareStatement(update_sql);

	}
    
    /**
     * @param item
     * @throws SQLException
     */
    public void save(BeanAppVersion appVersion) throws Exception{
    	
    	PreparedStatement ps=null;
    	final String where=" 1=1";
    	    	
    	if(isExist(where))
    		ps=getUpdPreparedStatment();
    	else    		
    		ps=getInsPreparedStatment();
    	
    	
    	ps.setInt(1, appVersion.getMajor());
    	ps.setInt(2, appVersion.getMinor());
    	ps.setInt(3, appVersion.getPatch());
    	ps.setString(4, appVersion.getBuildNo());
    	ps.setString(5, appVersion.getBuildDate());
    	
    	ps.execute();
    	
    }

}
