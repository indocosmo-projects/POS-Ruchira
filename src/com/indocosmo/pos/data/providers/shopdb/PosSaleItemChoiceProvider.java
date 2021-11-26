/**
 * 
 */
package com.indocosmo.pos.data.providers.shopdb;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.sql.rowset.CachedRowSet;

import com.indocosmo.pos.common.PosLog;
import com.indocosmo.pos.data.beans.BeanSaleItemChoice;

/**
 * @author joe.12.3
 *
 */
public class PosSaleItemChoiceProvider extends PosShopDBProviderBase {

	private HashMap<String, ArrayList<BeanSaleItemChoice>> saleItemChoices;
	private PosChoiceItemProvider choiceItemProvider;
	private static PosSaleItemChoiceProvider singleInstance;
	/**
	 * 
	 */
	private PosSaleItemChoiceProvider() {
		super("v_sale_item_choices");
		saleItemChoices=new HashMap<String, ArrayList<BeanSaleItemChoice>>();
		choiceItemProvider=PosChoiceItemProvider.getInstance();
	}

	public static PosSaleItemChoiceProvider getInstance(){

		if(singleInstance==null)
			singleInstance=new PosSaleItemChoiceProvider();

		return singleInstance;
	}

	public ArrayList<BeanSaleItemChoice> getSaleItemChoices(String saleItemCode) throws Exception{

		ArrayList<BeanSaleItemChoice> items=null;

		if(saleItemChoices.containsKey(saleItemCode))
			items=saleItemChoices.get(saleItemCode);
		else{
			items=createSaleItemChoiceList(saleItemCode);
		}

		return items;

	}

	/**
	 * @param saleItemCode
	 * @return
	 * @throws Exception 
	 */
	private ArrayList<BeanSaleItemChoice> createSaleItemChoiceList(
			String saleItemCode) throws Exception {
		
		ArrayList<BeanSaleItemChoice> itemList=null;
		final String cond="sale_item_code='"+saleItemCode+"'";
		CachedRowSet rs=getData(cond);
		
		if (rs!=null){
			itemList=new ArrayList<BeanSaleItemChoice>();
			try {
				while(rs.next()){
					BeanSaleItemChoice item=createSaleItemChoice(rs);
					itemList.add(item);
				}
				saleItemChoices.put(saleItemCode, itemList);
				rs.close();
			} catch (SQLException e) {
				PosLog.write(this,"createSaleItemChoiceList",e.getMessage());
				throw e;
			}
		}
		
		return itemList;
	}
	
	private BeanSaleItemChoice createSaleItemChoice(CachedRowSet rs) throws Exception{
		return createSaleItemChoice(rs,true);
	}

	/**
	 * @param rs
	 * @return
	 * @throws Exception 
	 * @throws SQLException 
	 */
	public BeanSaleItemChoice createSaleItemChoice(CachedRowSet rs, boolean createChoice) throws Exception {

		BeanSaleItemChoice item=new BeanSaleItemChoice();
		item.setId(rs.getInt("id"));
		item.setSaleItemCode(rs.getString("sale_item_code"));
		item.setSaleItemId(rs.getInt("sale_item_id"));
		item.setFreeItemCount(rs.getDouble("free_items"));
		item.setMaxItems(rs.getDouble("max_items"));
		if(createChoice)
			item.setChoice(choiceItemProvider.getChoice(rs.getInt("choice_id")));
		return item;
	}

	public boolean hasChoices(String code){
		final String where="sale_item_code='"+code+"'";
		return isExist(where);
	}
}
