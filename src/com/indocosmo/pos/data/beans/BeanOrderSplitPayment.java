/**
 * 
 */
package com.indocosmo.pos.data.beans;

/**
 * @author jojesh-13.2
 *
 */
public class BeanOrderSplitPayment implements Cloneable{
	
	private String id;
	private String headerID;
	private String transactionID;
	
	
	/**
	 * 
	 */
	public BeanOrderSplitPayment() {
		// TODO Auto-generated constructor stub
	}
	

	/**
	 * @return the paymentID
	 */
	public String getTransactionID() {
		return transactionID;
	}

	/**
	 * @param paymentID the paymentID to set
	 */
	public void setTransactionID(String transacionID) {
		this.transactionID = transacionID;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the headerID
	 */
	public String getHeaderID() {
		return headerID;
	}

	/**
	 * @param headerID the headerID to set
	 */
	public void setHeaderID(String headerID) {
		this.headerID = headerID;
	}

	@Override
	public BeanOrderSplitPayment clone() throws CloneNotSupportedException {
		
		return (BeanOrderSplitPayment)super.clone();
	}

}
