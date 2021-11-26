/**
 * 
 */
package com.indocosmo.pos.common.beans.settings.printing.receipt;
 

/**
 * @author jojesh-13.2
 *
 */
public class BeanPrintRefundReceiptSettings {
	
	public final static String PS_NO_COPIES="refund_receipt.no_copies";
	
	private int mNoCopies=1;
	
	/**
	 * @return the mNoCopies
	 */
	public int getNoCopies() {
		return mNoCopies;
	}

	/**
	 * @param mNoCopies the mNoCopies to set
	 */
	public void setNoCopies(int noCopies) {
		this.mNoCopies = noCopies;
	}

}
