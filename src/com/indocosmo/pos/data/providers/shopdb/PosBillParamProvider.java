/**
 * 
 */
package com.indocosmo.pos.data.providers.shopdb;

import java.sql.SQLException;

import javax.sql.rowset.CachedRowSet;

import com.indocosmo.pos.common.PosLog;
import com.indocosmo.pos.data.beans.BeanBillParam;

/**
 * @author jojesh
 *
 */
public class PosBillParamProvider extends PosShopDBProviderBase {

	private BeanBillParam mBillParam;
	private static PosBillParamProvider mSingleInstance;
	/**
	 *
	 */
	private PosBillParamProvider() {
		super("bill_params");
	}
	
	public static PosBillParamProvider getInstance(){
		if(mSingleInstance==null)
			mSingleInstance=new PosBillParamProvider();
		return mSingleInstance;
	}
	
	private void loadData() throws Exception{
		CachedRowSet res=getData();
		if(res!=null){
			try {
				res.next();
				mBillParam=createBillParamFromResultSet(res);
			} catch (Exception e) {
				PosLog.write(this,"getBillParam",e);
				try {
					res.close();
				} catch (SQLException e1) {
					PosLog.write(this,"getBillParam",e1);
				}
				throw new Exception("Failed to load bill param");
			}finally {
				try {
					res.close();
				} catch (SQLException e1) {
					PosLog.write(this,"getBillParam",e1);
				}
			}
		}
	}

	/**
	 * Creates and return the PosBillParams
	 * @return
	 * @throws Exception 
	 */
	public BeanBillParam getBillParam() throws Exception {
		if(mBillParam==null)
			loadData();
		return mBillParam;
	}

	/**
	 * Creates the PosBillParams item from the given resultset
	 * @param res
	 * @return
	 * @throws Exception 
	 */
	private BeanBillParam createBillParamFromResultSet(CachedRowSet res) throws Exception{
		BeanBillParam billParam=null;
		try {
			
			billParam=new BeanBillParam();
			billParam.setFooterLine1(res.getString("bill_footer1"));
			billParam.setFooterLine2(res.getString("bill_footer2"));
			billParam.setFooterLine3(res.getString("bill_footer3"));
			billParam.setFooterLine4(res.getString("bill_footer4"));
			billParam.setFooterLine5(res.getString("bill_footer5"));
			billParam.setFooterLine6(res.getString("bill_footer6"));
			billParam.setFooterLine7(res.getString("bill_footer7"));
			billParam.setFooterLine8(res.getString("bill_footer8"));
			billParam.setFooterLine9(res.getString("bill_footer9"));
			billParam.setFooterLine10(res.getString("bill_footer10"));
			
			billParam.setHeaderLine1(res.getString("bill_hdr1"));
			billParam.setHeaderLine2(res.getString("bill_hdr2"));
			billParam.setHeaderLine3(res.getString("bill_hdr3"));
			billParam.setHeaderLine4(res.getString("bill_hdr4"));
			billParam.setHeaderLine5(res.getString("bill_hdr5")); 
			billParam.setHeaderLine6(res.getString("bill_hdr6"));
			billParam.setHeaderLine7(res.getString("bill_hdr7"));
			billParam.setHeaderLine8(res.getString("bill_hdr8"));
			billParam.setHeaderLine9(res.getString("bill_hdr9"));
			billParam.setHeaderLine10(res.getString("bill_hdr10"));
			
			
			billParam.setShowDiscountSummary(res.getBoolean("show_discount_summary"));
			billParam.setTaxSummaryDisplayType(res.getInt("show_tax_summary"));
			billParam.setShowItemDiscount(res.getBoolean("show_item_discount"));
			billParam.setShowItemTax(res.getBoolean("show_item_tax"));
			final int billTaxId=res.getInt("bill_tax_id");
			billParam.setTax(PosTaxItemProvider.getInstance().getTaxItem(billTaxId));
			final int billRoundingId=res.getInt("rounding_id");
			billParam.setRounding(new PosRoundingProvider().getRounding(billRoundingId));
		} catch (Exception e) {
			PosLog.write(this,"createBillParamFromResultSet",e)	;
			throw new Exception("Failed to load bill params");
		}
		return billParam; 
	}
}
