package com.indocosmo.pos.data.providers.shopdb;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.sql.rowset.CachedRowSet;

import com.indocosmo.pos.common.PosLog;
import com.indocosmo.pos.data.beans.BeanItemPromotion;

public class PosSaleItemPromotionProvider extends PosPromotionItemProvider {

	private PosPromotionItemProvider mPromotionItemProvider;
	public PosSaleItemPromotionProvider() {
		super("v_sale_item_promotions");
		mPromotionItemProvider=new PosPromotionItemProvider();
	}

	/**
	 * This function will return all the promotion item codes which are applicable to the given sale item id.
	 * This function is used for fast checking while applying the promotion from BILL Discounts.
	 * These promotions are item specific.
	 * @param saleItemId
	 * @return Arraylist of promotion codes
	 */
	public ArrayList<String> getItemSpecificPromotionCodes(Integer saleItemId){
		ArrayList<String> promotions=new ArrayList<String>();
		CachedRowSet res=getData("sale_item_id="+String.valueOf(saleItemId));
		try {
			while (res.next()) {
				promotions.add(String.valueOf(res.getString("code")));
			}
			res.close();
		} catch (SQLException e) {
			PosLog.write(this,"getSaleItemDiscountCodes",e);
		}
		return promotions; 
	}
	
	public ArrayList<String> getPromotionCodes(Integer saleItemId){
		ArrayList<String> promotions=getItemSpecificPromotionCodes(saleItemId);
		promotions.addAll(mPromotionItemProvider.getNonItemSpecificPromotionCodes());
		return promotions;
	}

	public BeanItemPromotion getPromotionItem(int saleItemId, int promotionItemId) throws Exception {
		final String where="id="+promotionItemId +" and sale_item_id="+saleItemId;
		final ArrayList<BeanItemPromotion> promotions= loadPromotionItems(where);
		BeanItemPromotion promo=null;
		if(promotions!=null && promotions.size()>0){
			promo=promotions.get(0);
		}
		return promo;
	}


}
