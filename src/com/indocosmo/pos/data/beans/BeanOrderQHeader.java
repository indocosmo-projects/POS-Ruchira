package com.indocosmo.pos.data.beans;

import java.util.ArrayList;

public final class BeanOrderQHeader {

	private int mId;
	private BeanCustomer mCustomerId;
	private int mOriginstationId;
	private Integer mOrderQueueNo;
	ArrayList<BeanSaleItem> mOrderQueueDtlItemList;
	/**
	 * @return the mId
	 */
	public  int getId() {
		return mId;
	}
	/**
	 * @param mId the mId to set
	 */
	public  void setId(int id) {
		this.mId = id;
	}
	/**
	 * @return the mOrigin_station_id
	 */
	public  int getOriginstationId() {
		return mOriginstationId;
	}
	/**
	 * @param mOrigin_station_id the mOrigin_station_id to set
	 */
	public  void setOriginstationId(int originStationId) {
		this.mOriginstationId = originStationId;
	}
	/**
	 * @return the mOrderQueueNo
	 */
	public  Integer getOrderQueueNo() {
		
		return mOrderQueueNo;
	}
	/**
	 * @param mOrderQueueNo the mOrderQueueNo to set
	 */
	public  void setOrderQueuNo(Integer orderQueueNo) {
		this.mOrderQueueNo = orderQueueNo;
	}
	
	public  BeanCustomer getCustomer() {
		return mCustomerId;
	}

	/**
	 * @param mCustomerId the mCustomerId to set
	 */
	public  void setCustomer(BeanCustomer customer) {
		this.mCustomerId = customer;
	}	
		
	public ArrayList<BeanSaleItem> getOrderQueDtlItemList(){
		return mOrderQueueDtlItemList;
	}
	
	public void setOrderQueueDtlItemList(ArrayList<BeanSaleItem> orderQueueDtlItemList){
		mOrderQueueDtlItemList=orderQueueDtlItemList;
	}
	
}
