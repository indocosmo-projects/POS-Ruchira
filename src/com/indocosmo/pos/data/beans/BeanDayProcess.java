/**
 * 
 */
package com.indocosmo.pos.data.beans;

import com.indocosmo.pos.data.providers.shopdb.PosDayProcessProvider.PosDayProcess;

/**
 * @author deepak
 * @since 16.10.2012
 */
public class BeanDayProcess {
	
	private int mShop_Id;
	private int mStation_Id;
	private String mPos_Date;
//	private int mDay_Process_Type;
	private boolean mSynch_Up;
	private boolean mSynch_Down;
	private int mDone_By;
	private String mDone_At;
	PosDayProcess status;
	
	/**
	 * @return the status
	 */
	public PosDayProcess getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(PosDayProcess status) {
		this.status = status;
	}
	/**
	 * @return the mShop_Id
	 */
	public int getShopId() {
		return mShop_Id;
	}
	/**
	 * @param mShop_Id the mShop_Id to set
	 */
	public void setShopId(int mShop_Id) {
		this.mShop_Id = mShop_Id;
	}
	/**
	 * @return the mStation_Id
	 */
	public int getStationId() {
		return mStation_Id;
	}
	/**
	 * @param mStation_Id the mStation_Id to set
	 */
	public void setStationId(int mStation_Id) {
		this.mStation_Id = mStation_Id;
	}
	/**
	 * @return the mPos_Date
	 */
	public String getPosDate() {
		return mPos_Date;
	}
	/**
	 * @param mPos_Date the mPos_Date to set
	 */
	public void setPosDate(String mPos_Date) {
		this.mPos_Date = mPos_Date;
	}
	/**
	 * @return the mSynch_Up
	 */
	public boolean ismSynchUp() {
		return mSynch_Up;
	}
	/**
	 * @param mSynch_Up the mSynch_Up to set
	 */
	public void setSynchUp(boolean mSynch_Up) {
		this.mSynch_Up = mSynch_Up;
	}
	/**
	 * @return the mSynch_Down
	 */
	public boolean isSynchDown() {
		return mSynch_Down;
	}
	/**
	 * @param mSynch_Down the mSynch_Down to set
	 */
	public void setSynchDown(boolean mSynch_Down) {
		this.mSynch_Down = mSynch_Down;
	}
	/**
	 * @return the mDone_By
	 */
	public int getDoneBy() {
		return mDone_By;
	}
	/**
	 * @param mDone_By the mDone_By to set
	 */
	public void setDoneBy(int mDone_By) {
		this.mDone_By = mDone_By;
	}
	/**
	 * @return the mDone_At
	 */
	public String getDoneAt() {
		return mDone_At;
	}
	/**
	 * @param mDone_At the mDone_At to set
	 */
	public void setDoneAt(String mDone_At) {
		this.mDone_At = mDone_At;
	}

}
