/**
 * 
 */
package com.indocosmo.pos.data.beans;

/**
 * @author jojesh
 * with table usage info with open orders
 */
public class BeanServingTableExt extends BeanServingTable {
	
	private int mOrderCount;
	
	public boolean isUsed(){
		return mOrderCount>0;
	}
	

	/**
	 * The number of active orders in this table
	 * @return the OrderCount
	 */
	public int getOrderCount() {
		return mOrderCount;
	}

	/**
	 * @param OrderCount the OrderCount to set
	 */
	public void setOrderCount(int orderCount) {
		this.mOrderCount = orderCount;
	}
	

	public String getDisplayText(boolean showCount) {
		String countString=(mOrderCount>0)?" ("+mOrderCount+")":"";
		String displayText=getName()+((showCount)?countString:"");
		return displayText;
	}
	
	public static String[] SEARCH_FIELD_TITLE_LIST={"Code","Name","Seats","Orders"};
	public static String[] SEARCH_FIELD_LIST={"getCode","getName","getSeatCount","getOrderCount"};
	public static int[] SEARCH_FIELD_WIDTH_LIST={100,440,50};
	
	/* (non-Javadoc)
	 * @see com.indocosmo.pos.components.search.IPosSearchableItem#getFiledList()
	 */
	@Override
	public String[] getFieldList() {
		return SEARCH_FIELD_LIST;
	}
	/* (non-Javadoc)
	 * @see com.indocosmo.pos.components.search.IPosSearchableItem#getFiledTitleList()
	 */
	@Override
	public String[] getFieldTitleList() {

		return SEARCH_FIELD_TITLE_LIST;
	}
	/* (non-Javadoc)
	 * @see com.indocosmo.pos.components.search.IPosSearchableItem#getFiledWidthList()
	 */
	@Override
	public int[] getFieldWidthList() {
		return SEARCH_FIELD_WIDTH_LIST;
	}
}
