package com.indocosmo.pos.data.providers.shopdb;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import com.indocosmo.pos.data.beans.BeanSaleItem;


public final class PosHotItemProvider extends PosSaleItemProvider{
	
	public enum HotItems{
		HOT_ITEM_1(1),
		HOT_ITEM_2(2),
		HOT_ITEM_3(3);
		
		private static final Map<Integer,HotItems> mLookup=new HashMap<Integer,HotItems>();

		static {
			for(HotItems hi:EnumSet.allOf(HotItems.class))
				mLookup.put(hi.getCode(), hi);
		}

		private Integer mCode;
		private HotItems(Integer code){
			mCode=code;
		}

		public int getCode(){
			return mCode;
		}

		public static HotItems get(Integer code){
			return mLookup.get(code);
		}
	}
	
	public PosHotItemProvider(){
		super("v_hot_items");
	}
	private String mHotItemDisplayField;
	/* (non-Javadoc)
	 * @see com.indocosmo.pos.dataproviders.shopdb.PosSaleItemProvider#getDisplayOrderField()
	 */
	@Override
	protected String getDisplayOrderField() {
		// TODO Auto-generated method stub
		return "ifnull("+mHotItemDisplayField+",100000),"+mHotItemDisplayField;
	}
	
	public Map<Integer, ArrayList<BeanSaleItem>> getHotItemLists(){
		Map<Integer, ArrayList<BeanSaleItem>> mHotItemLists=new HashMap<Integer, ArrayList<BeanSaleItem>>(); 
		for(HotItems hi:EnumSet.allOf(HotItems.class)){
			String sqlWhere="is_hot_item_"+String.valueOf(hi.getCode());
			mHotItemDisplayField="hot_item_"+String.valueOf(hi.getCode()+"_display_order");
			ArrayList<BeanSaleItem> hotItemList=getList(sqlWhere);
			mHotItemLists.put(hi.getCode(), hotItemList);
		}
		return mHotItemLists;
	}
		
}
