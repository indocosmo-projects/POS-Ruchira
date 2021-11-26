/**
 * 
 */
package com.indocosmo.pos.data.providers.shopdb;

import java.sql.SQLException;

import javax.sql.rowset.CachedRowSet;

import com.indocosmo.pos.common.PosLog;
import com.indocosmo.pos.data.beans.BeanSystemParam;



/**
 * @author jojesh
 *
 */
public class PosSystemParamProvider extends PosShopDBProviderBase {

	private static PosSystemParamProvider instance;
	private BeanSystemParam systemParam;
	/**
	 * @return
	 */
	public static PosSystemParamProvider getInstance(){
		
		if(instance==null)
			instance=new PosSystemParamProvider();

		return instance;
	}
	
	/**
	 * 
	 */
	private PosSystemParamProvider(){
		super("system_params");
	}
	
	/**
	 * @throws Exception
	 */
	private void loadSystemParam() throws Exception{
		
		CachedRowSet res=getData();
		if(res!=null){
			try {
				res.next();
				systemParam=createSystemParamFromResultSet(res);
			} catch (SQLException e) {
				PosLog.write(this,"loadSystemParam",e);
				try {
					res.close();
				} catch (SQLException e1) {
					PosLog.write(this,"loadSystemParam",e1);
				}
				throw new Exception("Failed to load system params");
			}finally {
				try {
					res.close();
				} catch (SQLException e1) {
					PosLog.write(this,"loadSystemParam",e1);
				}
			}
		}
	}
	
	/**
	 * @param res
	 * @return
	 * @throws Exception
	 */
	private BeanSystemParam createSystemParamFromResultSet(CachedRowSet res) throws Exception{
		
		final PosCustomerTypeProvider custTypePvdr=new PosCustomerTypeProvider();
		BeanSystemParam sysParam=null;
		
		try {

			sysParam=new BeanSystemParam();
			sysParam.setId(res.getInt("id"));
			sysParam.setDateFormat(res.getString("date_format"));
			sysParam.setDateSeparator(res.getString("date_separator"));
			sysParam.setTimeFormat(res.getString("time_format"));
			sysParam.setTimeZone(res.getString("time_zone"));
			sysParam.setDecimalPlaces(res.getInt("decimal_places"));
//			sysParam.setDefaultCustomerType(custTypePvdr.getCustomerTypeByID(res.getInt("default_customer_type")));
			
		} catch (Exception e) {
			PosLog.write(this,"createSystemParamFromResultSet",e);
			throw new Exception("Failed to get system params.");
		}

		return sysParam; 
	}
	
	/**
	 * @return
	 * @throws Exception 
	 */
	public BeanSystemParam getSystemParam() throws Exception{
		
		if(systemParam==null)
			loadSystemParam();
		
		return systemParam;
	}
	
}
