/**
 * 
 */
package com.indocosmo.pos.data.providers.shopdb;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.sql.rowset.CachedRowSet;

import com.indocosmo.pos.common.PosLog;
import com.indocosmo.pos.data.beans.BeanSaleItemComboContentSubstitute;

/**
 * @author joe.12.3
 *
 */
public class PosSaleItemComboContentSubstitutesProvider extends PosShopDBProviderBase {
	
	private PosSaleItemProvider mSaleItemProvider;
	
	private static PosSaleItemComboContentSubstitutesProvider mInstance;
	
	public static  PosSaleItemComboContentSubstitutesProvider getInstance(){
		if(mInstance==null)
			mInstance=new PosSaleItemComboContentSubstitutesProvider();
		
		return mInstance;
	}

	/**
	 * 
	 */
	private  PosSaleItemComboContentSubstitutesProvider() {
		super("v_sale_item_combo_substitutions");
		mSaleItemProvider=new PosSaleItemProvider();
	}

	public ArrayList<BeanSaleItemComboContentSubstitute> getSubstitutes(String code) throws SQLException{
	
		final String where="combo_content_code="+code;
		return createItemList(where);
	}
	public ArrayList<BeanSaleItemComboContentSubstitute> getSubstitutes(int Id) throws SQLException{
	
		final String where="combo_content_id="+Id;
		return createItemList(where);
	}

	/**
	 * @param where
	 * @return
	 * @throws SQLException 
	 */
	private ArrayList<BeanSaleItemComboContentSubstitute> createItemList(String where) throws SQLException {
		ArrayList<BeanSaleItemComboContentSubstitute> list=null;
		CachedRowSet res=getData(where);
		if(res!=null){
			list=new ArrayList<BeanSaleItemComboContentSubstitute>();
			BeanSaleItemComboContentSubstitute item=null;
			int ccc=res.size();
			try {
				while(res.next()) {
					item = createItemFromCrs(res);
					list.add(item);
				}
				res.close();
			} catch (SQLException e) {
				PosLog.write(this,"createItemList",e);
				res.close(); 
				throw e;
			}
		}
		return list;
	}

	/**
	 * @param res
	 * @return
	 * @throws SQLException 
	 */
	private BeanSaleItemComboContentSubstitute createItemFromCrs(CachedRowSet res) throws SQLException {
		BeanSaleItemComboContentSubstitute substituteItem=new BeanSaleItemComboContentSubstitute();
//		item.setId(res.getInt("id"));
		substituteItem.setComboContentId(res.getInt("combo_content_id"));
		substituteItem.setDefault(res.getBoolean("is_default"));
		substituteItem.setPriceDifferance(res.getDouble("price_difference"));
		substituteItem.setSaleItem(mSaleItemProvider.getSaleItemByID(res.getInt("substitution_sale_item_id")));
		substituteItem.setQuantity(res.getDouble("quantity"));
		return substituteItem;
	}

}
