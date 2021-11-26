/**
 * 
 */
package com.indocosmo.pos.data.providers.shopdb;

import java.sql.SQLException;

import javax.sql.rowset.CachedRowSet;

import com.indocosmo.pos.common.PosLog;
import com.indocosmo.pos.data.beans.BeanShop;

/**
 * @author Ramesh S.
 * @since 31st July 2012
 */
public class PosShopProvider extends PosShopDBProviderBase {

//	private ArrayList<PosStation> mPosPosStationItemList;
	/**
	 * 
	 */
	public PosShopProvider() {
		super("shop");
	}
	
	
	public BeanShop getShop() throws Exception{
		final BeanShop shop = new BeanShop() ;
		CachedRowSet crsShop = null;

		try {
			crsShop =  getData();
			if (crsShop.next()) {
				shop.setId(crsShop.getInt("id"));
			    shop.setCode(crsShop.getString("code"));
			    shop.setName(crsShop.getString("name"));
			    shop.setDescription(crsShop.getString("description"));
			    shop.setAreaId(crsShop.getInt("area_id"));
			    shop.setAddress(crsShop.getString("address"));
			    shop.setState(crsShop.getString("state"));
			    shop.setStateCode(crsShop.getString("state_code"));
			    shop.setCity(crsShop.getString("city"));
			    shop.setCountry(crsShop.getString("country"));
			    shop.setZipCode(crsShop.getString("zip_code"));
			    shop.setCompanyLicenseNo(crsShop.getString("company_license_no"));
			    shop.setCompanyTaxNo(crsShop.getString("company_tax_no"));
			    shop.setPhoneNumber(crsShop.getString("phone"));
			    shop.setEmail(crsShop.getString("email"));
			    
			    shop.setBankName(crsShop.getString("bank_name"));
			    shop.setBankAddress(crsShop.getString("bank_address"));
			    shop.setBankBranch(crsShop.getString("bank_branch"));
			    shop.setBankIFSCCode(crsShop.getString("bank_ifsc_code"));
			    shop.setBankAccountNo(crsShop.getString("bank_account_no"));
			    
//			    shop.setCreatedBy(crsShop.getInt("created_by"));
//			    shop.setCreatedAt(crsShop.getString("created_at"));
//			    shop.setUpdatedBy(crsShop.getInt("updated_by"));
//			    shop.setUpdatedAt(crsShop.getString("updated_at"));
			}
			crsShop.close();
			
		} catch (SQLException e) {
			PosLog.write(this,"getShop",e);
			throw new Exception("Failed get shop information. Please check the log for more details.");
		}
		return shop;
	}
	
	public boolean hasShopInformation() throws Exception {
		boolean isShopDefined=false;
		final String sql="SELECT count(id) rec_count from shop";
		try{
		CachedRowSet crs=executeQuery(sql);
		if(crs!=null){
			if(crs.next()){
				final int count=crs.getInt("rec_count");
				isShopDefined=(count>0);
			}
		}
		}catch (Exception e) {
			PosLog.write(this,"hasShopInformation",e);
			throw(new Exception("Failed get shop information. Please check the log for more details."));
		}
		return isShopDefined;
	}
}
