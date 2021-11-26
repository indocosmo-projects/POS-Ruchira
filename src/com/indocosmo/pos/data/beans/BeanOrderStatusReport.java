/**
 * 
 */
package com.indocosmo.pos.data.beans;

import com.indocosmo.pos.common.PosLog;
import com.indocosmo.pos.common.enums.PosOrderServiceTypes;
import com.indocosmo.pos.common.utilities.PosCurrencyUtil;
import com.indocosmo.pos.common.utilities.PosDateUtil;
import com.indocosmo.pos.common.utilities.PosOrderUtil;
import com.indocosmo.pos.data.beans.BeanOrderHeader.PosOrderStatus;
import com.indocosmo.pos.data.beans.BeanPayModeBase.PaymentMode;
import com.indocosmo.pos.data.providers.shopdb.PosOrderServingTableProvider;

/**
 * @author jojesh
 * 
 */
public final class BeanOrderStatusReport implements Cloneable  {

	private String mOrderId;
	private String mOrderDate;
	private String mOrderTime;
	private double mTotalAmount;
	private int mDetailItemCount;
	private double mDetailQuantity;
	private String mServedBy;
	private PosOrderStatus mStatus=PosOrderStatus.Open;
	private boolean isSelected=true;

	private String mInvoiceNo;
	private String mQueueNo;

	private String mClosingDate;
	private String mClosingTime;
	private String mAliasText;

	private double mTotalAmountPaid;
	private double mBalanceAmount;
	private double mCashOut;
	private double mBillDiscountAmount;
	private BeanServingTable mOrderServiceTable;
	private PosOrderServiceTypes mOrderServiceType;
	private String mCustomerName;
	private String mCustomerPhoneNo;
	
	private double mExtraCharges; 
	private double mExtraChargeTaxOneAmount;
	private double mExtraChargeTaxTwoAmount;
	private double mExtraChargeTaxThreeAmount;
	private double mExtraChargeGstAmount;
	private double mBillDiscountPercentage;
	
	private BeanDiscount mPreBillDiscount;
	
		@Override
	public BeanOrderStatusReport clone() {

		BeanOrderStatusReport cloneObject = null;

		try {
		
			cloneObject = (BeanOrderStatusReport) super.clone();
	
		} catch (CloneNotSupportedException e) {

			PosLog.write(this, "clone", e);
		}

		return cloneObject;
	}



	/**
	 * 
	 */
	public BeanOrderStatusReport() {

	}

	/**
	 * @return the mQueueNo
	 */
	public String getQueueNo() {
		return mQueueNo;
	}



	/**
	 * @param QueueNo
	 *            the mQueueNo to set
	 */
	public void setQueueNo(String QueueNo) {
		this.mQueueNo  = QueueNo;
	}


	
	

	/**
	 * @return the mInvoiceNo
	 */
	public String getInvoiceNo() {
		return mInvoiceNo;// PosOrderUtil.getFormatedOrderId(mStationCode,
		// mOrderNo);
	}



	/**
	 * @param InvoiceNo
	 *            the mInvoiceNo to set
	 */
	public void setInvoiceNo(String InvoiceNo) {
		this.mInvoiceNo  = InvoiceNo;
	}

	/**
	 * @return the mOrderId
	 */
	public String getOrderId() {
		return mOrderId;// PosOrderUtil.getFormatedOrderId(mStationCode,
		// mOrderNo);
	}

	/**
	 * @param OrderId
	 *            the mOrderId to set
	 */
	public void setOrderId(String orderId) {
		this.mOrderId = orderId;
	}


	/**
	 * @return the mOrderNo
	 */
	public int getOrderNo() {
		// return mOrderNo;
		return Integer.parseInt(PosOrderUtil.getOrderNoFromOrderID(mOrderId));
	}

	/**
	 * @return the mOrderDate
	 */
	public String getOrderDate() {
		return mOrderDate ;
	}
	/**
	 * @param orderDate
	 *            the mOrderDate to set
	 */
	public void setOrderDate(String orderDate) {
		this.mOrderDate = orderDate;
	}

	/**
	 * @return the mOrderTime
	 */
	public String getOrderTime() {
		return mOrderTime;
	}

	/**
	 * @param orderTime
	 *            the mOrderTime to set
	 */
	public void setOrderTime(String orderTime) {
		this.mOrderTime = orderTime;
	}

	/**

	/**
	 * @return the mTotalAmount
	 */
	public double getTotalAmount() {
		return mTotalAmount;
	}
	/**
	 * @param total
	 *            the mTotalAmount to set
	 */
	public void setTotalAmount(double total) {
		this.mTotalAmount = total;
	}

	public String getCode() {
		return PosOrderUtil.getShortOrderIDFromOrderID(mOrderId);
	}


	public int getDetailItemCount(){
		return mDetailItemCount;
	}
	public void setDetailItemCount(int count){
		mDetailItemCount=count;
	}



	public double getDetailItemQuatity(){
		return mDetailQuantity;
	}
	

	public void setDetailItemQuatity(double qty){
		mDetailQuantity=qty;
	}

	/**
	 * @return the Waiter
	 */
	public String getServedBy() {
		return mServedBy;
	}

	/**
	 * @param waiter the Waiter to set
	 */
	public void setServedBy(String firstName) {
		this.mServedBy = firstName;
	}



	public static String[] ORDER_RETRIEVE_SEARCH_FIELD_LIST = {"getShortOrderID",
		"getOrderDateSearchField", "getTotalAmountSearchField","getDetailQuatity","getServingTableName"};

	public static String[] ORDER_RETRIEVE_SEARCH_COLUMN_NAMES = {"Number/Alias",
		"Order Date", "Amount","Quantity","Table/Service" };
 
    

	/**
	 * @return the isSelected
	 */
	public boolean isSelected() {
		return isSelected;
	}

	/**

	 * @param isSelected the isSelected to set
	 */
	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}



	/**
	 * @return the aliasText
	 */
	public String getAliasText() {
		return mAliasText;
	}



	/**
	 * @param aliasText the aliasText to set
	 */
	public void setAliasText(String aliasText) {
		this.mAliasText = aliasText;
	}
	


	/**
	 * @return the mTatus
	 */
	public String getStatusText() {
		return mStatus.getDisplayText();
	}
	/**
	 * @return the mTatus
	 */
	public PosOrderStatus getStatus() {
		return mStatus;
	}

	/**
	 * @param Tatus
	 *            the mTatus to set
	 */
	public void setStatus(PosOrderStatus status) {
		this.mStatus = status;
	}

	/**
	 * @return the Closing Date
	 */
	public String getClosingDate() {
		return mClosingDate;
	}

	/**
	 * @param  Closing Date to set
	 */
	public void setClosingDate(String date) {
		this.mClosingDate = date;
	}

	/**
	 * @return the Closing Date
	 */
	public String getClosingTime() {
		return mClosingTime;
	}

	/**
	 * @param Closing Time to set
	 */
	public void setClosingTime(String time) {
		this.mClosingTime = time;
	}

	/**
	 * @return the mTotalAmountPaid
	 */
	public double getTotalAmountPaid() {
		return mTotalAmountPaid;
	}

	/**
	 * @param total
	 *            the mTotalAmount to set
	 */
	public void setTotalAmountPaid(double total) {
		this.mTotalAmountPaid = total;
	}

	/**
	 * @return the mBalanceAmount
	 */
	public double getChangeAmount() {
		return mBalanceAmount;
	}

	/**
	 * @param mBalanceAmount
	 *            the mBalanceAmount to set
	 */
	public void setChangeAmount(double balanceAmount) {
		this.mBalanceAmount = balanceAmount;
	}
	
	/**
	 * @return the mCashOut
	 */
	public double getCashOut() {
		return mCashOut;
	}

	/**
	 * @param mCashOut the mCashOut to set
	 */
	public void setCashOut(double mCashOut) {
		this.mCashOut = mCashOut;
	}

	/**
	 * @return the BillDiscountAmount
	 */
	public double getBillDiscountAmount() {
		return mBillDiscountAmount;
	}

	/**
	 * @param the BillDiscountAmount to set
	 */
	public void setBillDiscountAmount(double billDiscountAmount) {
		this.mBillDiscountAmount = billDiscountAmount;
	}
	/**
	 * @return the OrderServiceTable
	 */
	public BeanServingTable getServiceTable() {
		
		return mOrderServiceTable;
	}

	/**
	 * @param OrderServiceTable the OrderServiceTable to set
	 */
 
	public void setServiceTable(BeanServingTable orderServiceTable) {
		this.mOrderServiceTable = orderServiceTable;
	}
	
	/**
	 * @return the OrderServiceType
	 */
	public PosOrderServiceTypes getOrderServiceType() {
		return mOrderServiceType;
	}

	/**
	 * @param mOrderServiceType the mOrderServiceType to set
	 */
	public void setOrderServiceType(PosOrderServiceTypes orderServiceType) {
		this.mOrderServiceType = orderServiceType;
	}
	
	public String getServingTableName(){
		String tableName=mOrderServiceType.getDisplayText();
		if(mOrderServiceTable!=null && !mOrderServiceTable.getCode().equals(PosOrderServingTableProvider.CODE_SYS_TABLE_NA))
			tableName=mOrderServiceTable.getName();
		return tableName;
	}



	/**
	 * @return the mCustomerName
	 */
	public String getCustomerName() {
		return mCustomerName;
	}



	/**
	 * @param mCustomerName the mCustomerName to set
	 */
	public void setCustomerName(String mCustomerName) {
		this.mCustomerName = mCustomerName;
	}



	/**
	 * @return the mCustomerPhoneNo
	 */
	public String getCustomerPhoneNo() {
		return mCustomerPhoneNo;
	}



	/**
	 * @param mCustomerPhoneNo the mCustomerPhoneNo to set
	 */
	public void setCustomerPhoneNo(String mCustomerPhoneNo) {
		this.mCustomerPhoneNo = mCustomerPhoneNo;
	}
	/**
	 * @return the mExtraCharges
	 */
	public double getExtraCharges() {
		return mExtraCharges;
	}

	/**
	 * @param mExtraCharges the mExtraCharges to set
	 */
	public void setExtraCharges(double mExtraCharges) {
		this.mExtraCharges = mExtraCharges;
	}
	
	/**
	 * @return the mExtraChargeTaxOneAmount
	 */
	public double getExtraChargeTaxOneAmount() {
		return mExtraChargeTaxOneAmount;
	}
	
	/**
	 * @param mExtraChargeTaxOneAmount the mExtraChargeTaxOneAmount to set
	 */
	public void setExtraChargeTaxOneAmount(double mExtraChargeTaxOneAmount) {
		this.mExtraChargeTaxOneAmount = mExtraChargeTaxOneAmount;
	}


	/**
	 * @return the mExtraChargeTaxTwoAmount
	 */
	public double getExtraChargeTaxTwoAmount() {
		return mExtraChargeTaxTwoAmount;
	}

	/**
	 * @param mExtraChargeTaxTwoAmount the mExtraChargeTaxTwoAmount to set
	 */
	public void setExtraChargeTaxTwoAmount(double mExtraChargeTaxTwoAmount) {
		this.mExtraChargeTaxTwoAmount = mExtraChargeTaxTwoAmount;
	}

	/**
	 * @return the mExtraChargeTaxThreeAmount
	 */
	public double getExtraChargeTaxThreeAmount() {
		return mExtraChargeTaxThreeAmount;
	}

	/**
	 * @param mExtraChargeTaxThreeAmount the mExtraChargeTaxThreeAmount to set
	 */
	public void setExtraChargeTaxThreeAmount(double mExtraChargeTaxThreeAmount) {
		this.mExtraChargeTaxThreeAmount = mExtraChargeTaxThreeAmount;
	}

	/**
	 * @return the mExtraChargeGstAmount
	 */
	public double getExtraChargeGSTAmount() {
		return mExtraChargeGstAmount;
	}

	/**
	 * @param mExtraChargeGstAmount the mExtraChargeGstAmount to set
	 */
	public void setExtraChargeGSTAmount(double mExtraChargeGstAmount) {
		this.mExtraChargeGstAmount = mExtraChargeGstAmount;
	}
	
	/**
	 * @return the BillDiscountAmount
	 */
	public double getBillDiscountPercentage() {
		return mBillDiscountPercentage;
	}

	/**
	 * @param the BillDiscountAmount to set
	 */
	public void setBillDiscountPercentage(double billDiscountPercentage) {
		this.mBillDiscountPercentage = billDiscountPercentage;
	}
	
	/**
	 * @return the mPreBillDiscount
	 */
	public BeanDiscount getPreBillDiscount() {
		return mPreBillDiscount;
	}

	/**
	 * @param mPreBillDiscount the mPreBillDiscount to set
	 */
	public void setPreBillDiscount(BeanDiscount mPreBillDiscount) {
		this.mPreBillDiscount = mPreBillDiscount;
	}
	public String getShortOrderID(){
		
		return  PosOrderUtil.getShortOrderIDFromOrderID(mOrderId);
	}
	
public String getDisplayNo( ){
		
		String refNo="";
		
		if (getStatus()==PosOrderStatus.Closed  || getStatus()==PosOrderStatus.Refunded ) 
			refNo=getInvoiceNo();
		else{
			final String queueNo= "[" + PosOrderUtil.getFormatedOrderQueueNo(Integer.valueOf(getQueueNo()), getOrderServiceType(), getServiceTable())+ "]";
			if(!(getOrderServiceType()==PosOrderServiceTypes.HOME_DELIVERY || 
					getOrderServiceType()==PosOrderServiceTypes.SALES_ORDER) && getStatus()==PosOrderStatus.Partial)
				refNo=getInvoiceNo() + " " + queueNo ;
			else
				refNo=queueNo;
			
		}
		
		return refNo;
	
	}
	
	public String getDisplayDate( ){
		
		String refDate="";
		
		if (getStatus()==PosOrderStatus.Closed  || getStatus()==PosOrderStatus.Refunded ) 
			refDate= getClosingDate() + " " + PosDateUtil.format(PosDateUtil.TIME_FORMAT_24 , getClosingTime())  ;
		else 
			refDate= getOrderDate() + " " + PosDateUtil.format(PosDateUtil.TIME_FORMAT_24 , getOrderTime())  ;
		
		refDate=PosDateUtil.formatLocalDateTime(refDate);			
		return refDate;
	
	}

	public double getDisplayAmount( ){
		
		double amount=0;
		
		if (getStatus()==PosOrderStatus.Closed  || getStatus()==PosOrderStatus.Refunded ) 
			amount= PosCurrencyUtil.roundTo(getTotalAmountPaid()-getChangeAmount()-getCashOut()) ;
		else {
				amount= getTotalAmount()+ getExtraCharges() + getExtraChargeTaxOneAmount() + 
						getExtraChargeTaxTwoAmount() + getExtraChargeTaxThreeAmount() + 
						getExtraChargeGSTAmount()  ;
				 if (getPreBillDiscount()!=null ){
					 amount=amount-(getPreBillDiscount().isPercentage()?amount*getPreBillDiscount().getPrice()/100:getPreBillDiscount().getPrice());
				 }
		}
				
		amount=PosCurrencyUtil.roundTo(amount);
		return  amount;
	
	}
	
 

}
