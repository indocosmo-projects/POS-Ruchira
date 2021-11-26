/**
 * This class will handle the as database interaction functions for TaxItem
 */
package com.indocosmo.pos.data.providers.shopdb;

import java.sql.SQLException;

import javax.sql.rowset.CachedRowSet;

import com.indocosmo.pos.common.PosLog;
import com.indocosmo.pos.data.beans.BeanTaxGlobalParam;

/**
 * @author jojesh
 *
 */
public class PosTaxParamProvider extends PosShopDBProviderBase {

	private BeanTaxGlobalParam mTaxParam;
	private static PosTaxParamProvider mSingleInstance=null;
	/**
	 * 
	 */
	private PosTaxParamProvider() {
		super("tax_param");
	}
	
	public static PosTaxParamProvider getInstance(){
		if(mSingleInstance==null)
			mSingleInstance=new PosTaxParamProvider();
		return mSingleInstance;
	}
	
	private void loadData() throws Exception{
		CachedRowSet res=getData();
		if(res!=null){
			try {
				res.next();
				mTaxParam=createTaxParamFromResultSet(res);
			} catch (SQLException e) {
				PosLog.write(this,"getTaxParam",e);
				try {
					res.close();
				} catch (SQLException e1) {
					PosLog.write(this,"getTaxParam",e1);
				}
				throw new Exception("Failed to load tax params");
			}finally {
				try {
					res.close();
				} catch (SQLException e1) {
					PosLog.write(this,"getTaxParam",e1);
				}
			}
		}
	}

	/**
	 * Creates and return the PosTaxParam
	 * @return
	 * @throws Exception 
	 */
	public BeanTaxGlobalParam getTaxParam() throws Exception{
		if(mTaxParam==null)
			loadData();
		return mTaxParam;
	}

	/**
	 * Creates the PosTaxParam item from the given resultset
	 * @param res
	 * @return
	 * @throws Exception 
	 */
	private BeanTaxGlobalParam createTaxParamFromResultSet(CachedRowSet res) throws Exception{
		BeanTaxGlobalParam taxParam=null;
		try {
			taxParam=new BeanTaxGlobalParam();
			taxParam.setTax1Name(res.getString("tax1_name"));
			taxParam.setTax2Name(res.getString("tax2_name"));
			taxParam.setTax3Name(res.getString("tax3_name"));
			taxParam.setGSTName(res.getString("gst_name"));
			taxParam.setServiceTaxName(res.getString("sc_name"));
			taxParam.setCalculateTaxBeforeDiscount(res.getBoolean("claculate_tax_before_discount"));
			taxParam.setTaxCalcMethod(res.getInt("default_taxation_method"));
			
		} catch (Exception e) {
			PosLog.write(this,"createTaxParamFromResultSet",e);
			throw new Exception("failed to get tax params");
		}
		return taxParam; 
	}
}
