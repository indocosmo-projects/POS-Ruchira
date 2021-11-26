/**
 * 
 */
package com.indocosmo.pos.common.beans.settings.ui.orderlist;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.indocosmo.pos.data.beans.BeanOrderHeader.PosOrderStatus;
/**
 * @author sandhya
 *
 */
public class BeanUIOrderListSetting {
 
	
	public static final String  SHOW_RESHITO_BUTTON="order_list_form.show_reshito_button";
	public static final String  DEFAULT_ORDER_STATUS="order_list_form.default_order_status";
 	public final static String PS_ORDER_SEARCH_GRID_COLUMNS="order_List_form.grid_columns";
	
public final static String PS_ORDER_SEARCH_GRID_DEF_COLUMNS=	"{\"columns\":["
		+ "{\"function\":\"getShortOrderID\", \"title\":\"Ref. No.\",\"format\":\"\",\"width\":\"150\"},"
		+ "{\"function\":\"getDisplayNo\",\"title\":\"Inv./[Que. No.]\",\"format\":\"\",\"width\":\"170\"},"
		+ "{\"function\":\"getDisplayDate\", \"title\":\"Date\",\"format\":\"\",\"width\":\"190\"},"
		+ "{\"function\":\"getCustomerName\",\"title\":\"Customer\",\"format\":\"\",\"width\":\"150\"},"
		+ "{\"function\":\"getCustomerPhoneNo\",\"title\":\"Phone\",\"format\":\"\",\"width\":\"130\"},"
		+ "{\"function\":\"getDetailItemQuatity\",\"title\":\"Item #\",\"format\":\"0.00\",\"width\":\"70\"},"
		+ "{\"function\":\"getServedBy\",\"title\":\"By\",\"format\":\"\",\"width\":\"140\"}," 
		+ "{\"function\":\"getServingTableName\",\"title\":\"Table/Service\",\"format\":\"\",\"width\":\"125\"}," 
		+ "{\"function\":\"getTotalAmountSearchField\",\"title\": \"Amount\",\"format\": \"0.00\",\"width\":\"85\"}]}";

private String  orderSearchGridColumns=PS_ORDER_SEARCH_GRID_DEF_COLUMNS;
private String[] orderListSearchFieldList;
private String[] orderListSearchColumnNames;
private String[] orderListSearchFieldFormats;
private int[]  orderListSearchFieldWidth;
 
	private boolean mShowReshitoButton;
	private PosOrderStatus mDefaultOrderStatus;
	private BeanUIOrderRefundSetting mRefundSetting;
 	
	/**
	 * @return the mShowPrintReshitoButton
	 */
	public boolean isPrintReshitoButtonVisible() {
		return mShowReshitoButton;
	}


	/**
	 * @param mShowPrintReshitoButton the mShowPrintReshitoButton to set
	 */
	public void setPrintReshitoButtonVisible(boolean mShowPrintReshitoButton) {
		
		this.mShowReshitoButton = mShowPrintReshitoButton;
	}

	/**
	 * @return the mTatus
	 */
	public PosOrderStatus getDefaultOrderStatus() {
		return mDefaultOrderStatus;
	}

	/**
	 * @param Tatus
	 *            the mTatus to set
	 */
	public void setDefaultOrderStatus(PosOrderStatus status) {
		this.mDefaultOrderStatus = status;
	}


	/**
	 * @return the refundSetting
	 */
	public BeanUIOrderRefundSetting getRefundSetting() {
		return mRefundSetting;
	}


	/**
	 * @param refundSetting the refundSetting to set
	 */
	public void setRefundSetting(BeanUIOrderRefundSetting refundSetting) {
		this.mRefundSetting = refundSetting;
	}


	/**
	 * @param orderSearchGridColumns the orderSearchGridColumns to set
	 */
	public void setOrderListGridColumns(String orderSearchGridColumns) {
		this.orderSearchGridColumns = orderSearchGridColumns;
		updateOrderListColumsSettings();
	}
	/**
	 * @return the orderListSearchFieldList
	 */
	public String[] getOrderListSearchFieldList() {
		return orderListSearchFieldList;
	}
	/**
	 * @return the orderListSearchColumnNames
	 */
	public String[] getOrderListSearchColumnNames() {
		return orderListSearchColumnNames;
	}
	/**
	 * @return the orderListSearchFieldFormats
	 */
	public String[] getOrderListSearchFieldFormats() {
		return orderListSearchFieldFormats;
	}
	/**
	 * @return the orderListSearchFieldWidth
	 */
	public int[] getOrderListSearchFieldWidth() {
		return orderListSearchFieldWidth;
	}
	
	private void updateOrderListColumsSettings(){
	  
	JSONArray columnList=JSONObject.fromObject(orderSearchGridColumns).getJSONArray("columns");
	int columnCount=columnList.size();
	
	orderListSearchFieldList= new String[columnCount];
	orderListSearchColumnNames= new String[columnCount];
	orderListSearchFieldFormats= new String[columnCount];
	orderListSearchFieldWidth= new int[columnCount];
	
	int columnCounter=0;
	for (Object obj : columnList) {
		
		JSONObject column=(JSONObject)obj;
		
		orderListSearchFieldList[columnCounter]=column.getString("function");
		orderListSearchColumnNames[columnCounter]=column.getString("title");
		orderListSearchFieldFormats[columnCounter]=column.getString("format");
		if(column.getString("width").trim().length()>0)
			orderListSearchFieldWidth[columnCounter]= Integer.valueOf(column.getString("width"));
		else
				orderListSearchFieldWidth[columnCounter]= -1;
		columnCounter++;
	}
	   
   }

 
	
}
