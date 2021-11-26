/**
 * 
 */
package com.indocosmo.pos.data.beans;


/**
 * @author joe.12.3
 * created on 01/07/2014
 * Bean for sale_item_choices
 */
public class BeanSaleItemChoice  {
	
	private int id;
	private int saleItemId;
	private String saleItemCode;
	private double freeItemCount=0;
	private double maxItems=-1;
	private BeanChoice choice;
	
	/**
	 * @return the maxItems
	 */
	public double getMaxItems() {
		return maxItems;
	}

	/**
	 * @param maxItems the maxItems to set
	 */
	public void setMaxItems(double maxItems) {
		this.maxItems = maxItems;
	}
		
	/**
	 * @return the saleItemCode
	 */
	public String getSaleItemCode() {
		return saleItemCode;
	}

	/**
	 * @param saleItemCode the saleItemCode to set
	 */
	public void setSaleItemCode(String saleItemCode) {
		this.saleItemCode = saleItemCode;
	}

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
	 * @return the saleItemId
	 */
	public int getSaleItemId() {
		return saleItemId;
	}

	/**
	 * @param saleItemId the saleItemId to set
	 */
	public void setSaleItemId(int saleItemId) {
		this.saleItemId = saleItemId;
	}

	/**
	 * @return the choiceId
	 */
	public int getChoiceId() {
		return choice.getId();
	}

	/**
	 * @return the freeItemCount
	 */
	public double getFreeItemCount() {
		return freeItemCount;
	}

	/**
	 * @param freeItemCount the freeItemCount to set
	 */
	public void setFreeItemCount(double freeItemCount) {
		this.freeItemCount = freeItemCount;
	}

	/**
	 * @return the choice
	 */
	public BeanChoice getChoice() {
		return choice;
	}

	/**
	 * @param choice the choice to set
	 */
	public void setChoice(BeanChoice choice) {
		this.choice = choice;
	}

	

}
