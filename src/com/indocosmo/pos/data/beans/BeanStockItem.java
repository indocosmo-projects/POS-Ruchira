/**
 * 
 */
package com.indocosmo.pos.data.beans;

import java.util.HashMap;

import com.indocosmo.pos.data.beans.BeanStockInHeader.StockInType;


 
public final class BeanStockItem {
	
 
	private int id;
	private String name;
	private double openingStock;
	private double currentStock;
	private double salesQty;
	private double adjustmentQty;
	private double disposalQty;
	private double stockInQty;
	
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
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the openingStock
	 */
	public double getOpeningStock() {
		return openingStock;
	}
	/**
	 * @param openingStock the openingStock to set
	 */
	public void setOpeningStock(double openingStock) {
		this.openingStock = openingStock;
	}
	/**
	 * @return the currentStock
	 */
	public double getCurrentStock() {
		return currentStock;
	}
	/**
	 * @param currentStock the currentStock to set
	 */
	public void setCurrentStock(double currentStock) {
		this.currentStock = currentStock;
	}
	/**
	 * @return the salesQty
	 */
	public double getSalesQty() {
		return salesQty;
	}
	/**
	 * @param salesQty the salesQty to set
	 */
	public void setSalesQty(double salesQty) {
		this.salesQty =  (salesQty<0?-1:1)* salesQty;
	}
	/**
	 * @return the adjustmentQty
	 */
	public double getAdjustmentQty() {
		return adjustmentQty;
	}
	/**
	 * @param adjustmentQty the adjustmentQty to set
	 */
	public void setAdjustmentQty(double adjustmentQty) {
		this.adjustmentQty = adjustmentQty;
	}
	/**
	 * @return the disposalQty
	 */
	public double getDisposalQty() {
		return disposalQty;
	}
	/**
	 * @param disposalQty the disposalQty to set
	 */
	public void setDisposalQty(double disposalQty) {
		this.disposalQty = disposalQty;
	}
	/**
	 * @return the stockInQty
	 */
	public double getStockInQty() {
		return stockInQty;
	}
	/**
	 * @param stockInQty the stockInQty to set
	 */
	public void setStockInQty(double stockInQty) {
		this.stockInQty = stockInQty;
	}
	 
	
}


 
