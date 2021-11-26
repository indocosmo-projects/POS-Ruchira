/**
 * 
 */
package com.indocosmo.pos.data.beans;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;


 
public final class BeanStockInHeader {
	
 
	private int id;
	private String date;
	private StockInType type;
	
	private ArrayList<BeanStockInDetail> details;
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return the date
	 */
	public String getDate() {
		return date;
	}
	/**
	 * @param date the date to set
	 */
	public void setDate(String date) {
		this.date = date;
	}
 
	/**
	 * @return the type
	 */
	public StockInType getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(StockInType type) {
		this.type = type;
	}
	/**
	 * @return the details
	 */
	public ArrayList<BeanStockInDetail> getDetails() {
		return details;
	}
	/**
	 * @param details the details to set
	 */
	public void setDetails(ArrayList<BeanStockInDetail> details) {
		this.details = details;
	}
	
	
	
	
	//1 -> Stock In , 2 -> Adjuxtment, 3-> Disposal,4-> Sales
	public enum StockInType{
		Stock_In (1),
		Adjustment(2),
		Disposal(3),
		Sales(4);
		
		private static final Map<Integer,StockInType> mLookup 
		= new HashMap<Integer,StockInType>();

		static {
			for(StockInType rc : EnumSet.allOf(StockInType.class))
				mLookup.put(rc.getValue(), rc);
		}

		private int mValue;
		
		private StockInType(int value) {
			this.mValue = value;
		}

		public int getValue() { return mValue; }
		
		public static StockInType get(int value) { 
			return mLookup.get(value); 
		}

	}
		
}


 
