package com.indocosmo.pos.data.providers.shopdb;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.sql.rowset.CachedRowSet;

import com.indocosmo.pos.common.PosLog;
import com.indocosmo.pos.data.beans.BeanDiscount;
import com.indocosmo.pos.data.beans.BeanDiscount.PermittedLevel;

public class PosSaleItemDiscountProvider extends PosDiscountItemProvider {

	public PosSaleItemDiscountProvider() {
		super("v_sale_item_discounts");
	}

	/**
	 * This function returns all the discount codes that are applicable to the given sale_item_id
	 * Only discounts that are Permitted to given PermittedLevel will be returned.
	 * @param saleItemId
	 * @param pl Permitted level ITEM,BOTH,BILL
	 * @return Arraylist of PosDiscountItem
	 */
	public ArrayList<BeanDiscount> getDiscountItemList(Integer saleItemId,PermittedLevel pl ){
		String sqlWhere="permitted_for="+String.valueOf(pl.getCode())+" and sale_item_id=" +String.valueOf(saleItemId);
		loadDiscountItems(sqlWhere);
		return mPosDiscountItemList;
	}

	/**
	 * This function returns all the discount codes that are applicable to the given sale_item_id
	 * Only discount items that are permitted to PermittedLevel.ITEM and PermittedLevel.BOTH will be returned
	 * @param saleItemId
	 * @return Arraylist of PosDiscountItem
	 */
	public ArrayList<BeanDiscount> getSaleItemDiscountList(Integer saleItemId){
		String sqlWhere="permitted_for in ("+
				String.valueOf(PermittedLevel.ITEM.getCode())+","+
				String.valueOf(PermittedLevel.BOTH.getCode())+
				") and sale_item_id=" +String.valueOf(saleItemId);
		loadDiscountItems(sqlWhere);
		return mPosDiscountItemList;
	}

	/**
	 * This function will return all the discount item codes which are applicable to the given sale item id.
	 * This function is used for fast checking while applying the discount from BILL Discounts.
	 * These discounts are item specific.
	 * @param saleItemId
	 * @return Arraylist of discount codes
	 */
	public ArrayList<String> getSaleItemDiscountCodes(Integer saleItemId){
		ArrayList<String> discounts=new ArrayList<String>();
		CachedRowSet res=getData("sale_item_id="+String.valueOf(saleItemId));
		try {
			while (res.next()) {
				discounts.add(String.valueOf(res.getString("code")));
			}
			res.close();
		} catch (SQLException e) {
			PosLog.write(this,"getSaleItemDiscountCodes",e);
		}
		return discounts; 
	}


}
