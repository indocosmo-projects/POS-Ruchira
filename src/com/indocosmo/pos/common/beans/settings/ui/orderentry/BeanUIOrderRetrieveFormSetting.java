/**
 * 
 */
package com.indocosmo.pos.common.beans.settings.ui.orderentry;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * @author jojesh-13.2
 *
 */
public class BeanUIOrderRetrieveFormSetting {
	
	public final static String PS_SHOW_PAYMENT_BUTTON="order_entry.order_retrieve_form.show_payment_button";
	public final static String PS_SHOW_CLOSEBILL_BUTTON="order_entry.order_retrieve_form.show_closebill_button";
	public final static String PS_ORDER_SEARCH_GRID_COLUMNS="order_entry.order_retrieve_form.order_search_grid_columns";
	
	public final static String PS_ORDER_SEARCH_GRID_DEF_COLUMNS="{\"columns\":[{\"function\":\"getShortOrderID\", \"title\":\"Order No\",\"format\":\"\",\"width\":\"160\"},{\"function\":\"getReferenceText\",\"title\":\"Number/Alias\",\"format\":\"\",\"width\":\"140\"},{\"function\":\"getOrderDateSearchField\", \"title\":\"Order Date\",\"format\":\"\",\"width\":\"195\"},{\"function\":\"getCustomerName\",\"title\":\"Name\",\"format\":\"\",\"width\":\"150\"},{\"function\":\"getCustomerPhoneNo\",\"title\":\"Phone\",\"format\":\"\",\"width\":\"130\"},{\"function\":\"getServingTableName\",\"title\":\"Table/Service\",\"format\":\"\",\"width\":\"120\"},{\"function\":\"getTotalAmountSearchField\",\"title\": \"Amount\",\"format\": \"0.00\",\"width\":\"100\"},{\"function\":\"getDetailQuatity\",\"title\":\"Quantity\",\"format\":\"0.00\",\"width\":\"100\"}]}";
	
//	public final static String PS_ORDER_SEARCH_GRID_DEF_COLUMNS="{\"columns\":[{\"function\":\"getReferenceText\",\"title\":\"Number/Alias\",\"format\":\"\",\"width\":\"140\"},{\"function\":\"getOrderDateSearchField\", \"title\":\"Order Date\",\"format\":\"\",\"width\":\"195\"},{\"function\":\"getCustomerName\",\"title\":\"Name\",\"format\":\"\",\"width\":\"150\"},{\"function\":\"getCustomerPhoneNo\",\"title\":\"Phone\",\"format\":\"\",\"width\":\"130\"},{\"function\":\"getServingTableName\",\"title\":\"Table/Service\",\"format\":\"\",\"width\":\"120\"},{\"function\":\"getTotalAmountSearchField\",\"title\": \"Amount\",\"format\": \"0.00\",\"width\":\"100\"},{\"function\":\"getDetailQuatity\",\"title\":\"Quantity\",\"format\":\"0.00\",\"width\":\"100\"}]}";
	
	private boolean paymentButtonVisible;
	private boolean closeBillButtonVisible;
	
	
	private String  orderSearchGridColumns=PS_ORDER_SEARCH_GRID_DEF_COLUMNS;
	private String[]  orderRetrieveSearchFieldList;
	private String[]  orderRetrieveSearchColumnNames;
	private String[]  orderRetrieveSearchFieldFormats;
	private int[]  orderRetrieveSearchFieldWidth;
	/**
	 * @return the paymentButtonVisible
	 */
	public boolean isPaymentButtonVisible() {
		return paymentButtonVisible;
	}
	/**
	 * @param paymentButtonVisible the paymentButtonVisible to set
	 */
	public void setPaymentButtonVisible(boolean paymentButtonVisible) {
		this.paymentButtonVisible = paymentButtonVisible;
	}
	/**
	 * @return the closeBillButtonVisible
	 */
	public boolean isCloseBillButtonVisible() {
		return closeBillButtonVisible;
	}
	/**
	 * @param closeBillButtonVisible the closeBillButtonVisible to set
	 */
	public void setCloseBillButtonVisible(boolean closeBillButtonVisible) {
		this.closeBillButtonVisible = closeBillButtonVisible;
		
	}

	/**
	 * @param orderSearchGridColumns the orderSearchGridColumns to set
	 */
	public void setOrderSearchGridColumns(String orderSearchGridColumns) {
		this.orderSearchGridColumns = orderSearchGridColumns;
		updateOrderRetriveColumsSettings();
	}
	/**
	 * @return the orderRetrieveSearchFieldList
	 */
	public String[] getOrderRetrieveSearchFieldList() {
		return orderRetrieveSearchFieldList;
	}
	/**
	 * @return the orderRetrieveSearchColumnNames
	 */
	public String[] getOrderRetrieveSearchColumnNames() {
		return orderRetrieveSearchColumnNames;
	}
	/**
	 * @return the orderRetrieveSearchFieldFormats
	 */
	public String[] getOrderRetrieveSearchFieldFormats() {
		return orderRetrieveSearchFieldFormats;
	}
	/**
	 * @return the orderRetrieveSearchFieldWidth
	 */
	public int[] getOrderRetrieveSearchFieldWidth() {
		return orderRetrieveSearchFieldWidth;
	}
	
	private void updateOrderRetriveColumsSettings(){
	  
	JSONArray columnList=JSONObject.fromObject(orderSearchGridColumns).getJSONArray("columns");
	int columnCount=columnList.size();
	
	orderRetrieveSearchFieldList= new String[columnCount];
	orderRetrieveSearchColumnNames= new String[columnCount];
	orderRetrieveSearchFieldFormats= new String[columnCount];
	orderRetrieveSearchFieldWidth= new int[columnCount];
	
	int columnCounter=0;
	for (Object obj : columnList) {
		
		JSONObject column=(JSONObject)obj;
		
		orderRetrieveSearchFieldList[columnCounter]=column.getString("function");
		orderRetrieveSearchColumnNames[columnCounter]=column.getString("title");
		orderRetrieveSearchFieldFormats[columnCounter]=column.getString("format");
		if(column.getString("width").trim().length()>0)
			orderRetrieveSearchFieldWidth[columnCounter]= Integer.valueOf(column.getString("width"));
		else
				orderRetrieveSearchFieldWidth[columnCounter]= -1;
		columnCounter++;
	}
	   
   }
}
