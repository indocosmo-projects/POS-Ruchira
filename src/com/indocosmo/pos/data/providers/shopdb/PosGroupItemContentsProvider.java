package com.indocosmo.pos.data.providers.shopdb;

import java.util.ArrayList;

import com.indocosmo.pos.data.beans.BeanSaleItem;

public class PosGroupItemContentsProvider extends PosSaleItemProvider {

		public PosGroupItemContentsProvider() {
			super("v_sale_items");

		}

		public ArrayList<BeanSaleItem> getList(BeanSaleItem saleItem) {
			String where="group_item_id="+String.valueOf(saleItem.getId());
			return getList(where);
		}
}
