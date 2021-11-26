/**
 * 
 */
package com.indocosmo.pos.data.providers.shopdb;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.sql.rowset.CachedRowSet;

import com.indocosmo.pos.common.PosLog;
import com.indocosmo.pos.data.beans.BeanSaleItemComboContent;

/**
 * @author joe.12.3
 *
 */
public class PosSaleItemComboContentProvider extends PosShopDBProviderBase {

	private static PosSaleItemComboContentProvider mInstance;
	/**
	 * cached list for combo contents
	 * <saleitemId,arraylist>
	 * */
	private HashMap<Integer, ArrayList<BeanSaleItemComboContent>> cachedList;
	/**
	 * sale item id code mappings
	 * */
	private HashMap<String, Integer> idCodeMappings;
	private PosUOMProvider mUOMProvider;
	
	public static PosSaleItemComboContentProvider getInstance(){
		if(mInstance==null)
			mInstance=new PosSaleItemComboContentProvider();
		return mInstance;
	}
	/**
	 * 
	 */
	private  PosSaleItemComboContentProvider() {
		super("v_sale_item_combo_contents");
		cachedList=new HashMap<Integer, ArrayList<BeanSaleItemComboContent>>();
		idCodeMappings=new HashMap<String, Integer>();
		mUOMProvider= PosUOMProvider.getInstance();
	}
	
	public ArrayList<BeanSaleItemComboContent> getComboContentList(int saleItemId) throws SQLException{
		ArrayList<BeanSaleItemComboContent> itemList=getFromCache(saleItemId);
		if(itemList==null){
			String where="combo_sale_item_id="+saleItemId;
			itemList= createItemList(where);
		}
		return itemList;
	}
	
	public ArrayList<BeanSaleItemComboContent> getComboContentList(String saleItemCode) throws SQLException{
		
		ArrayList<BeanSaleItemComboContent> itemList=getFromCache(saleItemCode);
		if(itemList==null){
			String where="combo_sale_item_code='"+saleItemCode+"'";
			itemList= createItemList(where);
		}
		return itemList;
	}
	
	private ArrayList<BeanSaleItemComboContent> getFromCache(String saleItemCode){
		ArrayList<BeanSaleItemComboContent> itemList=null;
		if(idCodeMappings.containsKey(saleItemCode)){
			itemList=getFromCache(idCodeMappings.get(saleItemCode));
		}
		return itemList;
	}
	
	private ArrayList<BeanSaleItemComboContent> getFromCache(int id){
		ArrayList<BeanSaleItemComboContent> itemList=null;
		if(cachedList.containsKey(id)){
			itemList=cachedList.get(id);
		}
		return itemList;
	}
	
	/**
	 * @param where
	 * @return
	 * @throws SQLException 
	 */
	private ArrayList<BeanSaleItemComboContent> createItemList(String where) throws SQLException {
		ArrayList<BeanSaleItemComboContent> list=null;
		CachedRowSet res=getData(where);
		if(res!=null){
			try {
				list=new ArrayList<BeanSaleItemComboContent>();
				BeanSaleItemComboContent item=null;
				Integer saleItemId=null;
				String saleItemCode=null;
				while(res.next()) {
					item = createItemFromCrs(res);
					list.add(item);
					saleItemId=res.getInt("combo_sale_item_id");
					saleItemCode=res.getString("combo_sale_item_code");
				}
				if(saleItemId!=null){
					cachedList.put(saleItemId, list);
					idCodeMappings.put(saleItemCode, saleItemId);
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
	
	public BeanSaleItemComboContent createItemFromCrs(CachedRowSet res) throws SQLException {
		return createItemFromCrs(res,true);
	}
	
	/**
	 * @param res
	 * @return
	 * @throws SQLException 
	 */
	public BeanSaleItemComboContent createItemFromCrs(CachedRowSet res, boolean loadSubList) throws SQLException {
		BeanSaleItemComboContent comboContentItem=new BeanSaleItemComboContent();
		comboContentItem.setId(res.getInt("id"));
//		comboContentItem.setComboContentId(res.getInt("combo_content_item_id"));
		comboContentItem.setCode(res.getString("code"));
		comboContentItem.setName(res.getString("name"));
		comboContentItem.setDescription(res.getString("description"));
		comboContentItem.setMaxItems(res.getDouble("max_items"));
		comboContentItem.setUom(mUOMProvider.getUom(res.getInt("uom_id")));
//		comboContentItem.setSaleItemId(res.getInt("combo_sale_item_id"));
		if(loadSubList){
			comboContentItem.setContentSubstitutes(PosSaleItemComboContentSubstitutesProvider.getInstance().getSubstitutes(comboContentItem.getId()));
		}
		return comboContentItem;
	}


	

}
