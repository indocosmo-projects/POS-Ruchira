/**
 * 
 */
package com.indocosmo.pos.data.beans;

import com.indocosmo.pos.common.PosLog;

/**
 * @author jojesh
 *
 */
public final class BeanOrderKitchenQueue implements Cloneable ,Comparable{


	private int mKitchenId;
	private String mKitchenCode;
	private int mKitchenQueueNo;
	private String mPrintedTime;
	
	@Override
	public BeanOrderKitchenQueue clone(){
		BeanOrderKitchenQueue cloneObject = null;
		try {
			cloneObject = (BeanOrderKitchenQueue) super.clone();
		} catch (CloneNotSupportedException e) {
			PosLog.write(this,"clone",e);
		}
		return cloneObject;
	}
	
 
	/**
	 * @return the mKitchenId
	 */
	public int getKitchenId() {
		return mKitchenId;
	}


	/**
	 * @param mKitchenId the mKitchenId to set
	 */
	public void setKitchenId(int mKitchenId) {
		this.mKitchenId = mKitchenId;
	}


	/**
	 * @return the mKitchenQueueNo
	 */
	public int getKitchenQueueNo() {
		return mKitchenQueueNo;
	}


	/**
	 * @param mKitchenQueueNo the mKitchenQueueNo to set
	 */
	public void setKitchenQueueNo(int mKitchenQueueNo) {
		this.mKitchenQueueNo = mKitchenQueueNo;
	}


	/**
	 * @return the mPrintedTime
	 */
	public String getPrintedTime() {
		return mPrintedTime;
	}


	/**
	 * @param mPrintedTime the mPrintedTime to set
	 */
	public void setPrintedTime(String mPrintedTime) {
		this.mPrintedTime = mPrintedTime;
	}


	/**
	 * @return the mKitchenCode
	 */
	public String getKitchenCode() {
		return mKitchenCode;
	}


	/**
	 * @param mKitchenCode the mKitchenCode to set
	 */
	public void setKitchenCode(String mKitchenCode) {
		this.mKitchenCode = mKitchenCode;
	}


	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(Object o) {
		
		BeanOrderKitchenQueue kitchenQueue=(BeanOrderKitchenQueue)o;
		int kitchenCodeComapre=  (int) (this.getKitchenCode().compareTo(kitchenQueue.getKitchenCode()));
		int queueNoCompare=this.getKitchenQueueNo()-kitchenQueue.getKitchenQueueNo();
		return (kitchenCodeComapre==0?queueNoCompare:kitchenCodeComapre);
	}
	 
	
}
