/**
 * 
 */
package com.indocosmo.pos.data.beans;

import java.awt.Color;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import javax.swing.KeyStroke;

import com.indocosmo.pos.common.PosEnvSettings;
import com.indocosmo.pos.common.PosLog;
import com.indocosmo.pos.common.enums.PosOrderMedium;
import com.indocosmo.pos.common.enums.PosOrderServiceTypes;
import com.indocosmo.pos.common.utilities.PosCurrencyUtil;
import com.indocosmo.pos.common.utilities.PosDateUtil;
import com.indocosmo.pos.common.utilities.PosOrderUtil;
import com.indocosmo.pos.common.utilities.PosUomUtil;
import com.indocosmo.pos.data.beans.BeanPayModeBase.PaymentMode;
import com.indocosmo.pos.data.providers.shopdb.PosUOMProvider;
import com.indocosmo.pos.forms.components.objectbrowser.IPosBrowsableItem;
import com.indocosmo.pos.forms.components.search.IPosSearchableItem;

/**
 * @author jojesh
 * 
 */
public final class BeanOrderHeader implements Cloneable,IPosSearchableItem,IPosBrowsableItem {

	private String mOrderId;
	private BeanUser mUser;
	private String mInvoicePrefix;
	private String mInvoiceNo;
	private String mQueueNo;
	private String mOrderDate;
	private String mOrderTime;
	private int mShiftId;
	private BeanCustomer mCustomer;
	private double mDetailTotal;
	private double mTotalAmountTax1;
	private double mTotalAmountTax2;
	private double mTotalAmountTax3;
	private double mTotalAmountServiceTax;
	private double mTotalAmountGST;
	private double mTotalAmountDetailDiscount;
	private double mRoundAdjustmentAmount;
	private double mTotalAmount;
	private double mTotalAmountPaid;
	private double mBalanceAmount;
	private double mCashOut;
	private double mBillDiscountAmount;
	private double mBillDiscountPercentage;
	private double mActualBalancePaid;
	private boolean mNewOrder = true;
	private String mRemarks=" ";
	private PosOrderStatus mStatus=PosOrderStatus.Open;
	private int mTotalAmountPrintCount;
	private String mClosingDate;
	private String mClosingTime;
	private int mDetailItemCount;
	private double mDetailQuantity;
	private ArrayList<BeanOrderDiscount> mBillDiscounts;
	private BeanDiscount mPreBillDiscount;
	private double mBillTaxAmount;
	private ArrayList<BeanOrderDetail> mPosOrderDetailItems;
	private ArrayList<BeanOrderPayment> mPosOrderPaymentItems;
	private ArrayList<BeanOrderSplit> mOrderSplits;
	private ArrayList<BeanOrderPaymentHeader> mPosOrderPaymentHeaders;
	private BeanOrderCustomer mOrderCustomer;
	private double mRefundTotalAmountTax1;
	private double mRefundTotalAmountTax2;
	private double mRefundTotalAmountTax3;
	private double mRefundTotalAmountServiceTax;
	private double mRefundTotalAmountGST;
	private double mRefundAmount;
	private double mTotalRefundAmount;
	private BeanServingTable mOrderServiceTable;
	private int mCovers;
	private BeanEmployees mServedBy;
	private BeanUser mClosedBy;

	private PosOrderServiceTypes mOrderServiceType;
	private PaymentProcessStatus mPaymentProcessStatus=PaymentProcessStatus.NONE;
	private HashMap<String, ArrayList<BeanOrderSplitDetail>> mSplitItemIDList;
	private double mSplitPartRecieved=0.0;
	private double mSplitPartUsed=0.0;
	private boolean mIsOrderLocked=false;
	private String mLockedStation;
	private String mAliasText;
	private boolean mReadOnly=false;
	private String driverName;
	private BeanUser mVoidBy;
	private String mVoidAt;
	private String mDueDateTime;
	private PosOrderMedium mOrderByMedium;
	private PosOrderServiceTypes mDeliveryType;
	private double mExtraCharges;
	private double mAdvanceAmount;
	private PaymentMode mAdvancePaymentMode;
	
	private int mExtraChargeTaxId;					
	private String mExtraChargeTaxCode;					
	private String mExtraChargeTaxName;					
	
	private String mExtraChargeTaxOneName;
	private double mExtraChargeTaxOnePercentage;
	private double mExtraChargeTaxOneAmount;
	
	private String mExtraChargeTaxTwoName;
	private double mExtraChargeTaxTwoPercentage;
	private double mExtraChargeTaxTwoAmount;
	
	private String mExtraChargeTaxThreeName;
	private double mExtraChargeTaxThreePercentage;
	private double mExtraChargeTaxThreeAmount;
	
	private String  mExtraChargeSCName;
	private double  mExtraChargeSCPercentage;
	private double  mExtraChargeSCAmount;
	
	
	private String  mExtraChargeGSTName;
	private double  mExtraChargeGstPercentage;
	private double  mExtraChargeGstAmount;
	
	private String  mExtraChargeRemarks;
	private String mCreatedAt;
	/**
	 * @return the driverName
	 */
	public String getDriverName() {
		return driverName;
	}

	/**
	 * @param driverName the driverName to set
	 */
	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	/**
	 * @return the vehicleNumber
	 */
	public String getVehicleNumber() {
		return vehicleNumber;
	}

	/**
	 * @param vehicleNumber the vehicleNumber to set
	 */
	public void setVehicleNumber(String vehicleNumber) {
		this.vehicleNumber = vehicleNumber;
	}
	private String vehicleNumber;
	
	/**
	 * @return the mReadOnly
	 */
	public boolean isReadOnly() {
		return mReadOnly;
	}



	/**
	 * @param mReadOnly the mReadOnly to set
	 */
	public void setReadOnly(boolean mReadOnly) {
		this.mReadOnly = mReadOnly;
	}
	private Map<String, BeanOrderServingTable> mOrderTableList;


	public enum PaymentProcessStatus implements IPosBrowsableItem{

		NONE(0,"None"),
		INIT(1,"Initialized"),
		PROCESSING(2,"Processing"), 
		COMPLETED(3,"Completed"); 

		private static final Map<Integer, PaymentProcessStatus> mLookup = new HashMap<Integer, PaymentProcessStatus>();

		static {
			for (PaymentProcessStatus item : EnumSet.allOf(PaymentProcessStatus.class))
				mLookup.put(item.getCode(), item);
		}

		private int mCode;
		private String mTitle;

		private PaymentProcessStatus(int code, String title) {
			mCode = code;
			mTitle=title;
		}

		public int getCode() {
			return mCode;
		}

		public static PaymentProcessStatus get(int code) {
			return mLookup.get(code);
		}

		/* (non-Javadoc)
		 * @see com.indocosmo.pos.components.objectbrowser.IPosBrowsableItem#getCaption()
		 */
		@Override
		public String getDisplayText() {
			// TODO Auto-generated method stub
			return mTitle;
		}

		/* (non-Javadoc)
		 * @see com.indocosmo.pos.components.objectbrowser.IPosBrowsableItem#getItemCode()
		 */
		@Override
		public Object getItemCode() {
			// TODO Auto-generated method stub
			return mCode;
		}
		/* (non-Javadoc)
		 * @see com.indocosmo.pos.forms.components.objectbrowser.IPosBrowsableItem#isVisibleInUI()
		 */
		@Override
		public boolean isVisibleInUI() {
			// TODO Auto-generated method stub
			return true;
		}

		/* (non-Javadoc)
		 * @see com.indocosmo.pos.forms.components.objectbrowser.IPosBrowsableItem#getKeyStroke()
		 */
		@Override
		public KeyStroke getKeyStroke() {
			// TODO Auto-generated method stub
			return null;
		}
	}


	public enum PosOrderStatus implements IPosBrowsableItem{

		Any(-1,"Any",Color.BLACK),
		Open(1,"Open",Color.GREEN),
		//		Processing(2,"Processing",Color.GREEN), 
		Closed(3,"Closed",Color.BLUE), 
		Refunded(4,"Refunded",Color.MAGENTA), 
		Void(5,"Void",Color.RED),
//		VoidAfterPayment(0,"Void (Paid)",Color.RED),
		Partial(6,"Partial Pay",Color.CYAN);

		private static final Map<Integer, PosOrderStatus> mLookup = new HashMap<Integer, PosOrderStatus>();

		static {
			for (PosOrderStatus item : EnumSet.allOf(PosOrderStatus.class))
				mLookup.put(item.getCode(), item);
		}

		private int mCode;
		private String mTitle;
		private Color mColor;
		/**
		 * @return the mColor
		 */
		public Color getColor() {
			return mColor;
		}

		private PosOrderStatus(int code, String title,Color color) {
			mCode = code;
			mTitle=title;
			mColor=color;
		}

		public int getCode() {
			return mCode;
		}

		public static PosOrderStatus get(int code) {
			return mLookup.get(code);
		}

		/* (non-Javadoc)
		 * @see com.indocosmo.pos.components.objectbrowser.IPosBrowsableItem#getCaption()
		 */
		@Override
		public String getDisplayText() {
			// TODO Auto-generated method stub
			return mTitle;
		}

		/* (non-Javadoc)
		 * @see com.indocosmo.pos.components.objectbrowser.IPosBrowsableItem#getItemCode()
		 */
		@Override
		public Object getItemCode() {
			// TODO Auto-generated method stub
			return mCode;
		}
		/* (non-Javadoc)
		 * @see com.indocosmo.pos.forms.components.objectbrowser.IPosBrowsableItem#isVisibleInUI()
		 */
		@Override
		public boolean isVisibleInUI() {
			// TODO Auto-generated method stub
			return true;
		}

		/* (non-Javadoc)
		 * @see com.indocosmo.pos.forms.components.objectbrowser.IPosBrowsableItem#getKeyStroke()
		 */
		@Override
		public KeyStroke getKeyStroke() {
			// TODO Auto-generated method stub
			return null;
		}
	}

	@Override
	public BeanOrderHeader clone() {

		BeanOrderHeader cloneObject = null;

		try {
			cloneObject = (BeanOrderHeader) super.clone();
			/**
			 * cloning details items will break parent child relation
			 * 
			 * */
			if (mPosOrderDetailItems != null) {
				ArrayList<BeanOrderDetail> newPosOrderDetailItem = new ArrayList<BeanOrderDetail>();
				for (BeanOrderDetail item : mPosOrderDetailItems)
					newPosOrderDetailItem.add(item.clone());
				cloneObject.setOrderDetailItems(newPosOrderDetailItem);
			}
			if (mPosOrderPaymentHeaders != null) {
				ArrayList<BeanOrderPaymentHeader> newPosOrderPaymentHdrs = new ArrayList<BeanOrderPaymentHeader>();
				for (BeanOrderPaymentHeader item : mPosOrderPaymentHeaders)
					newPosOrderPaymentHdrs.add(item.clone());
				cloneObject.setOrderPaymentHeaders(newPosOrderPaymentHdrs);
			}
			if (mPosOrderPaymentItems != null) {
				ArrayList<BeanOrderPayment> newPosOrderPaymentItems = new ArrayList<BeanOrderPayment>();
				for (BeanOrderPayment item : mPosOrderPaymentItems)
					newPosOrderPaymentItems.add(item.clone());
				cloneObject.setOrderPaymentItems(newPosOrderPaymentItems);
			}
			if(mOrderSplits!=null){
				ArrayList<BeanOrderSplit> splitItems = new ArrayList<BeanOrderSplit>();
				for (BeanOrderSplit item : mOrderSplits)
					splitItems.add(item.clone());
				cloneObject.setOrderSplits(splitItems);
			}
			
			if (mPosOrderPaymentHeaders != null) {
				ArrayList<BeanOrderPaymentHeader> newPosOrderPaymentHeaders = new ArrayList<BeanOrderPaymentHeader>();
				for (BeanOrderPaymentHeader item : mPosOrderPaymentHeaders)
					newPosOrderPaymentHeaders.add(item.clone());
				cloneObject.setOrderPaymentHeaders(newPosOrderPaymentHeaders);
			}
			
			if (mOrderCustomer != null) {
				cloneObject.setOrderCustomer(mOrderCustomer);
			}
		} catch (CloneNotSupportedException e) {

			PosLog.write(this, "clone", e);
		}

		return cloneObject;
	}



	/**
	 * 
	 */
	public BeanOrderHeader() {
//		mOrderPaymentHeader=new BeanOrderPaymentHeader();
	}

	/**
	 * @return the mOrderPaymentHdr
	 */
	public  ArrayList<BeanOrderPaymentHeader>  getOrderPaymentHeaders() {
		return mPosOrderPaymentHeaders ;
	}

	/**
	 * @param mOrderPaymentHdr the mOrderPaymentHdr to set
	 */
	public void setOrderPaymentHeaders( ArrayList<BeanOrderPaymentHeader>  mOrderPaymentHdrs) {
		this.mPosOrderPaymentHeaders = mOrderPaymentHdrs;
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
	 * @return the mOrderNo
	 */
	public int getOrderNo() {
		// return mOrderNo;
		return Integer.parseInt(PosOrderUtil.getOrderNoFromOrderID(mOrderId));
	}

	/**
	 * @param OrderNo
	 *            the mOrderNo to set
	 */
	// public void setOrderNo(int orderNo) {
	// this.mOrderNo = orderNo;
	// }

	/**
	 * @return the mStationId
	 */
	public String getStationCode() {
		return PosOrderUtil.getStationCodeFromOrderID(mOrderId);
	}

	/**
	 * @return the shope's code
	 */
	public String getShopeCode() {
		return PosEnvSettings.getInstance().getShop().getCode();
	}

	/**
	 * @param stationId
	 *            the mStationId to set
	 */
	// public void setStationCode(String stationCode) {
	// this.mStationCode = stationCode;
	// }

	/**
	 * @return the mUserId
	 */
	//	public int getUserId() {
	//		return mUserId;
	//	}

	public BeanUser getUser() {
		return mUser;
	}


	/**
	 * @param userId
	 *            the mUserId to set
	 */
	public void setUser(BeanUser user) {
		this.mUser = user;
	}

	/**
	 * @return the mOrderDate
	 */
	public String getOrderDate() {
		return mOrderDate;
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
	 * @return the mShiftId
	 */
	public int getShiftId() {
		return mShiftId;
	}

	/**
	 * @param shiftId
	 *            the mShiftId to set
	 */
	public void setShiftId(int shiftId) {
		this.mShiftId = shiftId;
	}

	/**
	 * @return the mCustomerTypeId
	 */
	public BeanCustomer getCustomer() {
		return mCustomer;
	}

	/**
	 * @param customerTypeId
	 *            the mCustomerTypeId to set
	 */
	public void setCustomer(BeanCustomer customer) {
		this.mCustomer = customer;
	}

	/**
	 * @return the mDetailTotal
	 */
	public double getDetailTotal() {
		return mDetailTotal;
	}

	/**
	 * @param detailTotal
	 *            the mDetailTotal to set
	 */
	public void setDetailTotal(double detailTotal) {
		this.mDetailTotal = detailTotal;
	}

	/**
	 * @return the mTotalAmountTax1
	 */
	public double getTotalTax1() {
		return mTotalAmountTax1;
	}

	/**
	 * @param totalTax1
	 *            the mTotalAmountTax1 to set
	 */
	public void setTotalTax1(double totalTax1) {
		this.mTotalAmountTax1 = totalTax1;
	}

	/**
	 * @return the mTotalAmountTax2
	 */
	public double getTotalTax2() {
		return mTotalAmountTax2;
	}

	/**
	 * @param totalTax2
	 *            the mTotalAmountTax2 to set
	 */
	public void setTotalTax2(double totalTax2) {
		this.mTotalAmountTax2 = totalTax2;
	}

	/**
	 * @return the mTotalAmountTax3
	 */
	public double getTotalTax3() {
		return mTotalAmountTax3;
	}

	/**
	 * @param totalTax3
	 *            the mTotalAmountTax3 to set
	 */
	public void setTotalTax3(double totalTax3) {
		this.mTotalAmountTax3 = totalTax3;
	}

	/**
	 * @return the mTotalAmountServiceTax
	 */
	public double getTotalServiceTax() {
		return mTotalAmountServiceTax;
	}

	/**
	 * @param totalServiceTax
	 *            the mTotalAmountServiceTax to set
	 */
	public void setTotalServiceTax(double totalServiceTax) {
		this.mTotalAmountServiceTax = totalServiceTax;
	}

	/**
	 * @return the mTotalAmountGST
	 */
	public double getTotalGST() {
		return mTotalAmountGST;
	}

	/**
	 * @param totalSC
	 *            the mTotalAmountGST to set
	 */
	public void setTotalGST(double totalgst) {
		this.mTotalAmountGST = totalgst;
	}

	/**
	 * @return the mTotalAmountDetailDiscount
	 */
	public double getTotalDetailDiscount() {
		return mTotalAmountDetailDiscount;
	}

	/**
	 * @param totalDetailDiscount
	 *            the mTotalAmountDetailDiscount to set
	 */
	public void setTotalDetailDiscount(double totalDetailDiscount) {
		this.mTotalAmountDetailDiscount = totalDetailDiscount;
	}

	/**
	 * @return the RoundAdjustmentAmount
	 */
	public double getRoundAdjustmentAmount() {
		return mRoundAdjustmentAmount;
	}

	/**
	 * @param 
	 *            the RoundAdjustmentAmount to set
	 */
	public void setRoundAdjustmentAmount(double roundAdjustmentAmount) {
		this.mRoundAdjustmentAmount = roundAdjustmentAmount;
	}

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
	 * @return the mTotalAmountPrintCount
	 */
	public int getTotalPrintCount() {
		return mTotalAmountPrintCount;
	}

	/**
	 * @param totalPrintCount
	 *            the mTotalAmountPrintCount to set
	 */
	public void setTotalPrintCount(int totalPrintCount) {
		this.mTotalAmountPrintCount = totalPrintCount;
	}

	/**
	 * @return the mPosOrderDetailItems
	 */
	public ArrayList<BeanOrderDetail> getOrderDetailItems() {
		return mPosOrderDetailItems;
	}

	/**
	 * @param PosOrderDetailItems
	 *            the mPosOrderDetailItems to set
	 */
	public void setOrderDetailItems(
			ArrayList<BeanOrderDetail> posOrderDetailItems) {
		this.mPosOrderDetailItems = posOrderDetailItems;
	}

	/**
	 * @return the mPosOrderPaymentItems
	 */
	public ArrayList<BeanOrderPayment> getOrderPaymentItems() {
		return mPosOrderPaymentItems;
	}

	/**
	 * @param PosOrderPaymentItems
	 *            the mPosOrderPaymentItems to set
	 */
	public void setOrderPaymentItems(
			ArrayList<BeanOrderPayment> posOrderPaymentItems) {
		this.mPosOrderPaymentItems = posOrderPaymentItems;
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
	 * @return the mActualBalancePaid
	 */
	public double getActualBalancePaid() {
		return mActualBalancePaid;
	}

	/**
	 * @param mActualBalancePaid
	 *            the mActualBalancePaid to set
	 */
	public void setActualBalancePaid(double actualBalancePaid) {
		this.mActualBalancePaid = actualBalancePaid;
	}

	/**
	 * @return the Remarks
	 */
	public String getRemarks() {
		return mRemarks;
	}

	/**
	 * @param Remarks
	 */
	public void setRemarks(String remarks) {
		this.mRemarks = remarks;
	}

	public ArrayList<BeanSaleItem> getSaleItems() {
		return buildSaleItemList();
	}

	private ArrayList<BeanSaleItem> buildSaleItemList() {
		ArrayList<BeanSaleItem> saleItems = null;
		if (mPosOrderDetailItems != null) {
			saleItems = new ArrayList<BeanSaleItem>();
			for (BeanOrderDetail dtlObject : mPosOrderDetailItems)
				saleItems.add(dtlObject.getSaleItem());
		}
		return saleItems;
	}

	public double getTotalDetailQty() {
		double qty = 0;
		if (mPosOrderDetailItems != null) {
			for (BeanOrderDetail dtlObject : mPosOrderDetailItems)
				qty += (!(dtlObject.isVoid()) ? dtlObject.getSaleItem()
						.getQuantity() : 0);
		}
		return qty;
	}

	/**
	 * @return NewOrder
	 */
	public boolean isNewOrder() {
		return mNewOrder;
	}

	/**
	 * @param NewOrder
	 */
	public void setNewOrder(boolean newOrder) {
		
		this.mNewOrder = newOrder;
			
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.pos.components.objectbrowser.IPosBrowsableItem#getCaption()
	 */
	@Override
	public String getDisplayText() {
		return String.valueOf(getStatus());
	}


	public String getCode() {
		return PosOrderUtil.getShortOrderIDFromOrderID(mOrderId);
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


	/***
	 * Below functions are used to get data for displaying in the order retrieve search window 
	 */

	/**
	 * Order Id exclusive of shop code;
	 * @return
	 */
	public String getReferenceText(){
		
		return (mAliasText==null || mAliasText.trim().isEmpty())?PosOrderUtil.getFormattedReferenceNo(this):mAliasText;
	}
	/**
	 * Order Id exclusive of shop code;
	 * @return
	 */
	public String getShortOrderID(){
		
		return  PosOrderUtil.getShortOrderID(this);
	}

	/**
	 * Order Id exclusive of shop code;
	 * @return
	 */
	public String getOrderNoPart(){
		
		return PosOrderUtil.getOrderNoFromOrderID(mOrderId);
	}

	/**
	 * Order station;
	 * @return
	 */
	public String getOrderStation(){
		
		return PosOrderUtil.getTerminalCodeFromOrderID(mOrderId);
	}

	public String getOrderDateSearchField(){

		SimpleDateFormat sd =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sd.format(PosDateUtil.parse("yyyy-MM-dd HH:mm:ss", mOrderTime));
		
	}

	public String getOrderTimeSearchField(){
		
		SimpleDateFormat sd =new SimpleDateFormat("HH:mm");
		return sd.format(PosDateUtil.parse("yyyy-MM-dd HH:mm:ss", mOrderTime));
	}

	public String getCustomerName(){
		return mOrderCustomer!=null?mOrderCustomer.getName():"";
	}
	public String getCustomerPhoneNo(){
		return mOrderCustomer!=null?mOrderCustomer.getPhoneNumber():"";
	}
	public double getTotalAmountSearchField(){
		
		 double amount=mTotalAmount+ mExtraCharges + mExtraChargeTaxOneAmount + 
				 	mExtraChargeTaxTwoAmount + mExtraChargeTaxThreeAmount + 
				 	mExtraChargeGstAmount ;
		 if (mPreBillDiscount!=null ){
			 amount=amount-(mPreBillDiscount.isPercentage()?amount*mPreBillDiscount.getPrice()/100:mPreBillDiscount.getPrice());
		 }
		return amount;
	}

	public double getDetailQuatity(){

		return mDetailQuantity;
	}


	/**
	 * @return the BillDiscount
	 */
	public ArrayList<BeanOrderDiscount> getBillDiscounts() {

		return mBillDiscounts;
	}

	/**
	 * @param mBillDiscounts the mBillDiscount to set
	 */
	public void setBillDiscounts(ArrayList<BeanOrderDiscount> billDiscounts) {

		this.mBillDiscounts = billDiscounts;
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
	 * @return the BillTaxAmount
	 */
	public double getBillTaxAmount() {
		return mBillTaxAmount;
	}

	/**
	 * @param mBillTaxAmount the mBillTaxAmount to set
	 */
	public void setBillTaxAmount(double billTaxAmount) {
		this.mBillTaxAmount = billTaxAmount;
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
	 * @return the mRefundTotalAmountTax1
	 */
	public double getRefundTotalAmountTax1() {
		return mRefundTotalAmountTax1;
	}

	/**
	 * @param mRefundTotalAmountTax1 the mRefundTotalAmountTax1 to set
	 */
	public void setRefundTotalAmountTax1(double mRefundTotalAmountTax1) {
		this.mRefundTotalAmountTax1 = mRefundTotalAmountTax1;
	}

	/**
	 * @return the mRefundTotalAmountTax2
	 */
	public double getRefundTotalAmountTax2() {
		return mRefundTotalAmountTax2;
	}

	/**
	 * @param mRefundTotalAmountTax2 the mRefundTotalAmountTax2 to set
	 */
	public void setRefundTotalAmountTax2(double mRefundTotalAmountTax2) {
		this.mRefundTotalAmountTax2 = mRefundTotalAmountTax2;
	}

	/**
	 * @return the mRefundTotalAmountTax3
	 */
	public double getRefundTotalAmountTax3() {
		return mRefundTotalAmountTax3;
	}

	/**
	 * @param mRefundTotalAmountTax3 the mRefundTotalAmountTax3 to set
	 */
	public void setRefundTotalAmountTax3(double mRefundTotalAmountTax3) {
		this.mRefundTotalAmountTax3 = mRefundTotalAmountTax3;
	}

	/**
	 * @return the mRefundTotalAmountServiceTax
	 */
	public double getRefundTotalAmountServiceTax() {
		return mRefundTotalAmountServiceTax;
	}

	/**
	 * @param mRefundTotalAmountServiceTax the mRefundTotalAmountServiceTax to set
	 */
	public void setRefundTotalAmountServiceTax(double mRefundTotalAmountServiceTax) {
		this.mRefundTotalAmountServiceTax = mRefundTotalAmountServiceTax;
	}

	/**
	 * @return the mRefundTotalAmountGST
	 */
	public double getRefundTotalAmountGST() {
		return mRefundTotalAmountGST;
	}

	/**
	 * @param mRefundTotalAmountGST the mRefundTotalAmountGST to set
	 */
	public void setRefundTotalAmountGST(double mRefundTotalAmountGST) {
		this.mRefundTotalAmountGST = mRefundTotalAmountGST;
	}

	/**
	 * @return the mRefundAmount
	 */
	public double getRefundAmount() {
		return mRefundAmount;
	}

	/**
	 * @param mRefundAmount the mRefundAmount to set
	 */
	public void setRefundAmount(double mRefundAmount) {
		this.mRefundAmount = mRefundAmount;
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
	 * @return the Waiter
	 */
	public BeanEmployees getServedBy() {
		return mServedBy;
	}

	/**
	 * @param waiter the Waiter to set
	 */
	public void setServedBy(BeanEmployees waiter) {
		this.mServedBy = waiter;
	}

	/**
	 * @param waiter the Waiter to set
	 */
	public String getWaiterName() {

		return ((this.mServedBy!=null)? this.mServedBy.getName():this.mUser.getName());

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
		if(mOrderServiceTable!=null)
			tableName=mOrderServiceTable.getName();
		return tableName;
	}

//	public static String[] ORDER_RETRIEVE_SEARCH_FIELD_LIST = {"getShortOrderID","getReferenceText","getOrderDateSearchField", 
//		"getCustomerName","getCustomerPhoneNo","getServingTableName",
//		"getTotalAmountSearchField","getDetailQuatity"};
//
//	public static String[] ORDER_RETRIEVE_SEARCH_COLUMN_NAMES = {"Order No","Number/Alias","Order Date",
//		 "Name","Phone","Table/Service",
//		 "Amount","Quantity" };
//	
//	public static String[] ORDER_RETRIEVE_SEARCH_FIELD_FORMATS = {"","", "",
//		"","","",
//		PosUomUtil.getFormatString(PosUOMProvider.getInstance().getMaxDecUom()),
//		PosCurrencyUtil.getCurrencyFormatString()};
//	
//	public static int[] ORDER_RETRIEVE_SEARCH_FIELD_WIDTH = {160,140,195,150,130,120,100};
	
	public static String[] ORDER_RETRIEVE_SEARCH_FIELD_LIST = PosEnvSettings.getInstance().getUISetting().getOrderEntryUISettings().getOrderRetrieveFormSetting().getOrderRetrieveSearchFieldList();

		public static String[] ORDER_RETRIEVE_SEARCH_COLUMN_NAMES = PosEnvSettings.getInstance().getUISetting().getOrderEntryUISettings().getOrderRetrieveFormSetting().getOrderRetrieveSearchColumnNames();
		
		public static String[] ORDER_RETRIEVE_SEARCH_FIELD_FORMATS = PosEnvSettings.getInstance().getUISetting().getOrderEntryUISettings().getOrderRetrieveFormSetting().getOrderRetrieveSearchFieldFormats();

	public static int[] ORDER_RETRIEVE_SEARCH_FIELD_WIDTH = PosEnvSettings.getInstance().getUISetting().getOrderEntryUISettings().getOrderRetrieveFormSetting().getOrderRetrieveSearchFieldWidth();

	/* (non-Javadoc)
	 * @see com.indocosmo.pos.components.search.IPosSearchableItem#getFiledList()
	 */
	@Override
	public String[] getFieldList() {
		return ORDER_RETRIEVE_SEARCH_FIELD_LIST;
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.pos.components.search.IPosSearchableItem#getFiledTitleList()
	 */
	@Override
	public String[] getFieldTitleList() {
		return ORDER_RETRIEVE_SEARCH_COLUMN_NAMES;
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.pos.components.search.IPosSearchableItem#getFiledWidthList()
	 */
	@Override
	public int[] getFieldWidthList() {
		return ORDER_RETRIEVE_SEARCH_FIELD_WIDTH;
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.pos.components.objectbrowser.IPosBrowsableItem#getItemCode()
	 */
	@Override
	public Object getItemCode() {
		// TODO Auto-generated method stub
		return PosOrderUtil.getShortOrderIDFromOrderID(mOrderId);
	}



	/**
	 * @return the orderSplits
	 */
	public ArrayList<BeanOrderSplit> getOrderSplits() {
		return mOrderSplits;
	}


	/**
	 * @param orderSplits the orderSplits to set
	 */
	public void setOrderSplits(ArrayList<BeanOrderSplit> orderSplits) {

		this.mOrderSplits = orderSplits;
	}

	public void setPaymentProcessStatus(PaymentProcessStatus status) {

		this.mPaymentProcessStatus=status;
	}

	public PaymentProcessStatus getPaymentProcessStatus(){

		return this.mPaymentProcessStatus;
	}

	/**
	 * @return the itemSplitList
	 */
	public HashMap<String, ArrayList<BeanOrderSplitDetail>> getSplitItemIDList() {

		return mSplitItemIDList;
	}

	/**
	 * @param itemSplitList the itemSplitList to set
	 */
	public void setSplitItemIDList(HashMap<String, ArrayList<BeanOrderSplitDetail>> hashMap) {

		this.mSplitItemIDList = hashMap;
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.pos.forms.components.objectbrowser.IPosBrowsableItem#isVisibleInUI()
	 */
	@Override
	public boolean isVisibleInUI() {
		// TODO Auto-generated method stub
		return true;
	}



	/**
	 * @return the splitPartRecieved
	 */
	public double getSplitPartRecieved() {
		return mSplitPartRecieved;
	}



	/**
	 * @param splitPartRecieved the splitPartRecieved to set
	 */
	public void setSplitPartRecieved(double splitPartRecieved) {
		this.mSplitPartRecieved = splitPartRecieved;
	}



	/**
	 * @return the splitPartUsed
	 */
	public double getSplitPartUsed() {
		return mSplitPartUsed;
	}

	public double getSplitPartAmountLeft(){

		return getSplitPartRecieved()-getSplitPartUsed();
	}

	/**
	 * @param splitPartUsed the splitPartUsed to set
	 */
	public void setSplitPartUsed(double splitPartUsed) {
		this.mSplitPartUsed = splitPartUsed;
	}

	/**
	 * @return
	 */
	public boolean hasPartialPayments() {

		return getOrderSplits()!=null && getOrderSplits().size()>0; 
	}

	/**
	 * @return the isOrderLocked
	 */
	public boolean isOrderLocked() {
		
		return mIsOrderLocked;
	}

	/**
	 * @param isOrderLocked the isOrderLocked to set
	 */
	public void setOrderLocked(boolean isOrderLocked) {
		this.mIsOrderLocked = isOrderLocked;
	}


	/**
	 * @return the lockedStation
	 */
	public String getLockedStation() {
		return mLockedStation;
	}

	/**
	 * @param lockedStation the lockedStation to set
	 */
	public void setLockedStation(String lockedStation) {
		this.mLockedStation = lockedStation;
	}



	/**
	 * @return the mCovers
	 */
	public int getCovers() {
		return mCovers;
	}



	/**
	 * @param mCovers the mCovers to set
	 */
	public void setCovers(int covers) {
		this.mCovers = covers;
	}



	/**
	 * @return the orderTableList
	 */
	public Map<String, BeanOrderServingTable> getOrderTableList() {
		return mOrderTableList;
	}



	/**
	 * @param orderTableList the orderTableList to set
	 */
	public void setOrderTableList(Map<String, BeanOrderServingTable> orderTableList) {
		this.mOrderTableList = orderTableList;
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
	 * @return the mTotalRefundAmount
	 */
	public double getTotalRefundAmount() {
		return mTotalRefundAmount;
	}

	/**
	 * @param mTotalRefundAmount the mTotalRefundAmount to set
	 */
	public void setTotalRefundAmount(double mTotalRefundAmount) {
		this.mTotalRefundAmount = mTotalRefundAmount;
	}
	
	/**
	 * @return the mVoidBy
	 */
	public BeanUser getVoidBy() {
		return mVoidBy;
	}

	/**
	 * @param mVoidBy the mVoidBy to set
	 */
	public void setVoidBy(BeanUser mVoidBy) {
		this.mVoidBy = mVoidBy;
	}

	/**
	 * @return the mVoidAt
	 */
	public String getVoidAt() {
		return mVoidAt;
	}

	/**
	 * @param mVoidAt the mVoidAt to set
	 */
	public void setVoidAt(String mVoidAt) {
		this.mVoidAt = mVoidAt;
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
	
	/**
	 * @return the mDueDateTime
	 */
	public String getDueDateTime() {
		return mDueDateTime;
	}

	/**
	 * @param mDueDateTime the mDueDateTime to set
	 */
	public void setDueDateTime(String mDueDateTime) {
		this.mDueDateTime = mDueDateTime;
	}



	/**
	 * @return the mOrderByMedium
	 */
	public PosOrderMedium getOrderByMedium() {
		return mOrderByMedium;
	}


	/**
	 * @param mOrderByMedium the mOrderByMedium to set
	 */
	public void setOrderByMedium(PosOrderMedium mOrderByMedium) {
		this.mOrderByMedium = mOrderByMedium;
	}

	/**
	 * @return the mDeliveryType
	 */
	public PosOrderServiceTypes getDeliveryType() {
		return mDeliveryType;
	}

	/**
	 * @param mDeliveryType the mDeliveryType to set
	 */
	public void setDeliveryType(PosOrderServiceTypes mDeliveryType) {
		this.mDeliveryType = mDeliveryType;
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
	 * @return the mOrderCustomer
	 */
	public BeanOrderCustomer getOrderCustomer() {
		return mOrderCustomer;
	}



	/**
	 * @param mOrderCustomer the mOrderCustomer to set
	 */
	public void setOrderCustomer(BeanOrderCustomer mOrderCustomer) {
		this.mOrderCustomer = mOrderCustomer;
	}



	/**
	 * @return the mAdvancePaymentMode
	 */
	public PaymentMode getAdvancePaymentMode() {
		return mAdvancePaymentMode;
	}

	/**
	 * @param mAdvancePaymentMode the mAdvancePaymentMode to set
	 */
	public void setAdvancePaymentMode(PaymentMode mAdvancePaymentMode) {
		this.mAdvancePaymentMode = mAdvancePaymentMode;
	}

	
	/**
	 * @return the mAdvanceAmount
	 */
	public double getAdvanceAmount() {
		return mAdvanceAmount;
	}

	/**
	 * @param mAdvanceAmount the mAdvanceAmount to set
	 */
	public void setAdvanceAmount(double mAdvanceAmount) {
		this.mAdvanceAmount = mAdvanceAmount;
	}

	/**
	 * @return the mInvoicePrefix
	 */
	public String getInvoicePrefix() {
		return mInvoicePrefix;
	}

	/**
	 * @param mInvoicePrefix the mInvoicePrefix to set
	 */
	public void setInvoicePrefix(String mInvoicePrefix) {
		this.mInvoicePrefix = mInvoicePrefix;
	}

	
	
	
	/**
	 * @return the mExtraChargeTaxId
	 */
	public int getExtraChargeTaxId() {
		return mExtraChargeTaxId;
	}

	/**
	 * @param mExtraChargeTaxId the mExtraChargeTaxId to set
	 */
	public void setExtraChargeTaxId(int mExtraChargeTaxId) {
		this.mExtraChargeTaxId = mExtraChargeTaxId;
	}

	/**
	 * @return the mExtraChargeTaxCode
	 */
	public String getExtraChargeTaxCode() {
		return mExtraChargeTaxCode;
	}

	/**
	 * @param mExtraChargeTaxCode the mExtraChargeTaxCode to set
	 */
	public void setExtraChargeTaxCode(String mExtraChargeTaxCode) {
		this.mExtraChargeTaxCode = mExtraChargeTaxCode;
	}

	/**
	 * @return the mExtraChargeTaxName
	 */
	public String getExtraChargeTaxName() {
		return mExtraChargeTaxName;
	}

	/**
	 * @param mExtraChargeTaxName the mExtraChargeTaxName to set
	 */
	public void setExtraChargeTaxName(String mExtraChargeTaxName) {
		this.mExtraChargeTaxName = mExtraChargeTaxName;
	}

	/**
	 * @return the mExtraChargeTaxOneName
	 */
	public String getExtraChargeTaxOneName() {
		return mExtraChargeTaxOneName;
	}

	/**
	 * @param mExtraChargeTaxOneName the mExtraChargeTaxOneName to set
	 */
	public void setExtraChargeTaxOneName(String mExtraChargeTaxOneName) {
		this.mExtraChargeTaxOneName = mExtraChargeTaxOneName;
	}

	/**
	 * @return the mExtraChargeTaxOnePercentage
	 */
	public double getExtraChargeTaxOnePercentage() {
		return mExtraChargeTaxOnePercentage;
	}

	/**
	 * @param mExtraChargeTaxOnePercentage the mExtraChargeTaxOnePercentage to set
	 */
	public void setExtraChargeTaxOnePercentage(double mExtraChargeTaxOnePercentage) {
		this.mExtraChargeTaxOnePercentage = mExtraChargeTaxOnePercentage;
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
	 * @return the mExtraChargeTaxTwoName
	 */
	public String getExtraChargeTaxTwoName() {
		return mExtraChargeTaxTwoName;
	}

	/**
	 * @param mExtraChargeTaxTwoName the mExtraChargeTaxTwoName to set
	 */
	public void setExtraChargeTaxTwoName(String mExtraChargeTaxTwoName) {
		this.mExtraChargeTaxTwoName = mExtraChargeTaxTwoName;
	}

	/**
	 * @return the mExtraChargeTaxTwoPercentage
	 */
	public double getExtraChargeTaxTwoPercentage() {
		return mExtraChargeTaxTwoPercentage;
	}

	/**
	 * @param mExtraChargeTaxTwoPercentage the mExtraChargeTaxTwoPercentage to set
	 */
	public void setExtraChargeTaxTwoPercentage(double mExtraChargeTaxTwoPercentage) {
		this.mExtraChargeTaxTwoPercentage = mExtraChargeTaxTwoPercentage;
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
	 * @return the mExtraChargeTaxThreeName
	 */
	public String getExtraChargeTaxThreeName() {
		return mExtraChargeTaxThreeName;
	}

	/**
	 * @param mExtraChargeTaxThreeName the mExtraChargeTaxThreeName to set
	 */
	public void setExtraChargeTaxThreeName(String mExtraChargeTaxThreeName) {
		this.mExtraChargeTaxThreeName = mExtraChargeTaxThreeName;
	}

	/**
	 * @return the mExtraChargeTaxThreePercentage
	 */
	public double getExtraChargeTaxThreePercentage() {
		return mExtraChargeTaxThreePercentage;
	}

	/**
	 * @param mExtraChargeTaxThreePercentage the mExtraChargeTaxThreePercentage to set
	 */
	public void setExtraChargeTaxThreePercentage(
			double mExtraChargeTaxThreePercentage) {
		this.mExtraChargeTaxThreePercentage = mExtraChargeTaxThreePercentage;
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
	 * @return the mExtraChargeTaxGSTName
	 */
	public String getExtraChargeGSTName() {
		return mExtraChargeGSTName;
	}

	/**
	 * @param mExtraChargeGSTName the mExtraChargeTaxGSTName to set
	 */
	public void setExtraChargeGSTName(String mExtraChargeGSTName) {
		this.mExtraChargeGSTName = mExtraChargeGSTName;
	}

	/**
	 * @return the mExtraChargeGstPercentage
	 */
	public double getExtraChargeGSTPercentage() {
		return mExtraChargeGstPercentage;
	}

	/**
	 * @param mExtraChargeGstPercentage the mExtraChargeGstPercentage to set
	 */
	public void setExtraChargeGSTPercentage(double mExtraChargeGstPercentage) {
		this.mExtraChargeGstPercentage = mExtraChargeGstPercentage;
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
	 * @return the mExtraChargeSCName
	 */
	public String getExtraChargeSCName() {
		return mExtraChargeSCName;
	}

	/**
	 * @param mExtraChargeSCName the mExtraChargeSCName to set
	 */
	public void setExtraChargeSCName(String mExtraChargeSCName) {
		this.mExtraChargeSCName = mExtraChargeSCName;
	}

	/**
	 * @return the mExtraChargeSCPercentage
	 */
	public double getExtraChargeSCPercentage() {
		return mExtraChargeSCPercentage;
	}

	/**
	 * @param mExtraChargeSCPercentage the mExtraChargeSCPercentage to set
	 */
	public void setExtraChargeSCPercentage(double mExtraChargeSCPercentage) {
		this.mExtraChargeSCPercentage = mExtraChargeSCPercentage;
	}

	/**
	 * @return the mExtraChargeSCAmount
	 */
	public double getExtraChargeSCAmount() {
		return mExtraChargeSCAmount;
	}

	/**
	 * @param mExtraChargeSCAmount the mExtraChargeSCAmount to set
	 */
	public void setExtraChargeSCAmount(double mExtraChargeSCAmount) {
		this.mExtraChargeSCAmount = mExtraChargeSCAmount;
	}

	/**
	 * @return the mExtraChargeRemarks
	 */
	public String getExtraChargeRemarks() {
		return mExtraChargeRemarks;
	}

	/**
	 * @param mExtraChargeRemarks the mExtraChargeRemarks to set
	 */
	public void setExtraChargeRemarks(String mExtraChargeRemarks) {
		this.mExtraChargeRemarks = mExtraChargeRemarks;
	}

	/**
	 * @return the mClosedBy
	 */
	public BeanUser getClosedBy() {
		return mClosedBy;
	}

	/**
	 * @param mClosedBy the mClosedBy to set
	 */
	public void setClosedBy(BeanUser mClosedBy) {
		this.mClosedBy = mClosedBy;
	}

	/**
	 * @return the mCreatedAt
	 */
	public String getCreatedAt() {
		return mCreatedAt;
	}

	/**
	 * @param mCreatedAt the mCreatedAt to set
	 */
	public void setCreatedAt(String mCreatedAt) {
		this.mCreatedAt = mCreatedAt;
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.pos.forms.components.search.IPosSearchableItem#getFiledFormatList()
	 */
	@Override
	public String[] getFieldFormatList() {
		// TODO Auto-generated method stub
		return null;
	}



	/* (non-Javadoc)
	 * @see com.indocosmo.pos.forms.components.objectbrowser.IPosBrowsableItem#getKeyStroke()
	 */
	@Override
	public KeyStroke getKeyStroke() {
		// TODO Auto-generated method stub
		return null;
	}


}
