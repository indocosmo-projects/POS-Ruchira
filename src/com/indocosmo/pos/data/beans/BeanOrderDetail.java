/**
 * 
 */
package com.indocosmo.pos.data.beans;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import javax.swing.KeyStroke;

import com.indocosmo.pos.common.enums.PosOrderServiceTypes;
import com.indocosmo.pos.common.utilities.PosSaleItemUtil;
import com.indocosmo.pos.forms.components.search.IPosSearchableItem;

/**
 * @author jojesh
 * 
 */
public class BeanOrderDetail implements Cloneable, IPosSearchableItem,Comparable{
	
	public static enum OrderDetailItemType{
		SALE_ITEM(0),
		EXTRA_ITEM(1),
		COMBO_CONTENT_ITEM(2);
		
		private static final Map<Integer,OrderDetailItemType> mLookup 
		= new HashMap<Integer,OrderDetailItemType>();

		static {
			for(OrderDetailItemType rc : EnumSet.allOf(OrderDetailItemType.class))
				mLookup.put(rc.getValue(), rc);
		}

		private Integer mValue;
		
		private OrderDetailItemType(Integer value) {
			this.mValue = value;
		}

		public Integer getValue() { return mValue; }
		
		public static OrderDetailItemType get(Integer value) { 
			return mLookup.get(value); 
		}
	}

	private String mId;
	private String mOrderId;
	private int mDiscountType;
	private double mRoundAdjustment;
	private boolean mIsVoid;
	private boolean mIsNewItem = true;
	private int mStatus;
	private boolean mIsPrintedToKitchen;
	private String mKitchenPrintedStatus="N";
	private String mOrderDate;
	private String mOrderTime;
	private int mCashierId;
	private String mRemarks;
	private BeanSaleItem mSaleItem;
	private BeanServingTable mServingTable;
	private int mServingSeat;
	private BeanEmployees mServedBy;
	private PosOrderServiceTypes mServiceType;
	private boolean mIsDirty=false;
	private boolean isKitchenDirty=false;
	private ArrayList<BeanOrderSplitDetail> mSplitList;
	private double itemShareInPercentage=1;
	private double itemShareQty;
	private boolean mRefundStatus=false;
	private int mRefundId;
	private double mRefundAmount;
	private double mRefundQuantity;
	private boolean addedToOrder=false;
	private BeanUser mVoidBy;
	private String mVoidAt;
	private String mVoidTime;
	private int mPrintingOrder;
	private String mTrayWeight;
	private String mTrayCode;
	private double mRefundTax1Amount;
	private double mRefundTax2Amount;
	private double mRefundTax3Amount;
	private double mRefundGSTAmount;
	private double mRefundSCAmount;
	
	
	/**
	 * @return the isDirty
	 */
	public boolean isDirty() {
		return mIsDirty;
	}

	/**
	 * @param isDirty the isDirty to set
	 */
	public void setDirty(boolean isDirty) {
		this.mIsDirty = isDirty;
	}

	private OrderDetailItemType mItemType=OrderDetailItemType.SALE_ITEM;
	
	/** Extras -->
	 * */
	//If this item has extra items set to true
//	private boolean mHasExtras=false;
	//If this item has extra items,Keep all the sub items in this list.
	//KeyChoiceCode
	private HashMap<String,ArrayList<BeanOrderDetail>> mExtrasList;
		
	//If this is an extra item then set the parent id
	private String mParentDtlId=null;
	
	//If this is an extra item, set the choice object 
	private BeanSaleItemChoice mSaleItemChoice;
	
	/** Extras  <--
	 * */	
	
	/** Combo ->
	 * */
	
	//If this item has combo items,Keep all the  items in this list.
	// Key combo content code
	private HashMap<String,ArrayList<BeanOrderDetail>> mComboSubstitutes;

	//if this is a combo content item set the combo content item
	private BeanSaleItemComboContent mComboContentItem;
	
	
	/** Combo  <--
	 * */	

	@Override
	public BeanOrderDetail clone() throws CloneNotSupportedException {
		BeanOrderDetail cloneObject = null;
		cloneObject = (BeanOrderDetail) super.clone();
		cloneObject.setSaleItem(mSaleItem.clone());
		if(mExtrasList!=null){
			cloneObject.setExtraItemList(cloneSubList(mExtrasList));
		}
		if(mComboSubstitutes!=null){
			cloneObject.setComboSubstitutes(cloneSubList(mComboSubstitutes));
		}
		return cloneObject;
	}
	
	/**
	 * @return
	 * @throws CloneNotSupportedException 
	 */
	private HashMap<String, ArrayList<BeanOrderDetail>> cloneSubList(HashMap<String,ArrayList<BeanOrderDetail>> subItemList) throws CloneNotSupportedException {
		HashMap<String,ArrayList<BeanOrderDetail>> newList=new HashMap<String,ArrayList<BeanOrderDetail>> ();
		for(String key:subItemList.keySet()){
			ArrayList<BeanOrderDetail> newItemList=new ArrayList<BeanOrderDetail>();
			for(BeanOrderDetail Item:subItemList.get(key)){
				newItemList.add(Item.clone());
			}
			newList.put(key, newItemList);
		}
		return newList;
	}

//	private HashMap<String,ArrayList<BeanOrderDetail>> cloneExtrasList() throws CloneNotSupportedException{
//		HashMap<String,ArrayList<BeanOrderDetail>> newExtrasList=new HashMap<String,ArrayList<BeanOrderDetail>> ();
//		for(String key:mExtrasList.keySet()){
//			ArrayList<BeanOrderDetail> newItemList=new ArrayList<BeanOrderDetail>();
//			for(BeanOrderDetail Item:mExtrasList.get(key)){
//				newItemList.add(Item.clone());
//			}
//			newExtrasList.put(key, newItemList);
//		}
//		return newExtrasList;
//	}

	/** New additional objects to handle retrieving of order data as sale objects **/


	/**
	 * @return the mId
	 */
	public String getId() {
		return mId;
	}

	/**
	 * @param id
	 *            the mId to set
	 */
	public void setId(String id) {
		this.mId = id;
	}

	/**
	 * @return the mOrderId
	 */
	public String getOrderId() {
		return mOrderId;
	}

	/**
	 * @param OrderId
	 *            the mOrderId to set
	 */
	public void setOrderId(String orderId) {
		this.mOrderId = orderId;
	}

	public void setSaleItem(BeanSaleItem item) {
		this.mSaleItem = item;
	}

	public BeanSaleItem getSaleItem() {
		return this.mSaleItem;
	}
	/**
	 * @return the mDiscountType
	 */
	public int getDiscountType() {
		return mDiscountType;
	}

	/**
	 * @param DiscountType
	 *            the mDiscountType to set
	 */
	public void setDiscountType(int discountType) {
		this.mDiscountType = discountType;
	}

	/**
	 * @return the mRoundAdjustment
	 */
	public double getRoundAdjustment() {
		return mRoundAdjustment;
	}

	/**
	 * @param RoundAdjustment
	 *            the mRoundAdjustment to set
	 */
	public void setRoundAdjustment(double roundAdjustment) {
		this.mRoundAdjustment = roundAdjustment;
	}

	/**
	 * @return the mAttrib1 
	 */
	public String getAttrib1() {
		return (mSaleItem.getAtrribute(1) != null) ? mSaleItem.getAtrribute(1)
				.getSelectedOption() : "";
	}

	/**
	 * @return the mStatus
	 */
	public int getStatus() {
		return mStatus;
	}

	/**
	 * @param Status
	 *            the mStatus to set
	 */
	public void setStatus(int status) {
		this.mStatus = status;
	}

	/**
	 * @return the mIsPrintedToKitchen
	 */
	public boolean isPrintedToKitchen() {
		return mIsPrintedToKitchen;
	}

	/**
	 * @param IsPrintedToKitchen
	 *            the mIsPrintedToKitchen to set
	 */
	public void setPrintedToKitchen(boolean isPrintedToKitchen) {
		this.mIsPrintedToKitchen = isPrintedToKitchen;
	}

	/**
	 * @return the mKitchenPrintedStatus
	 */
	public String getKitchenPrintedStatus() {
		return mKitchenPrintedStatus;
	}

	/**
	 * @param mKitchenPrintedStatus the mKitchenPrintedStatus to set
	 */
	public void setKitchenPrintedStatus(String mKitchenPrintedStatus) {
		this.mKitchenPrintedStatus = mKitchenPrintedStatus;
	}

	/**
	 * @return the mIsVoid
	 */
	public boolean isVoid() {
		return mIsVoid;
	}

	/**
	 * @param mIsVoid the mIsVoid to set
	 */
	public void setVoid(boolean isVoid) {
		this.mIsVoid = isVoid;
	}

	/**
	 * @return the mIsNewItem
	 */
	public boolean isNewItem() {
		return mIsNewItem;
	}

	/**
	 * @param mIsNewItem the mIsNewItem to set
	 */
	public void setNewItem(boolean isNewItem) {
		this.mIsNewItem = isNewItem;
	}
	// public void setRemarks(String remarks){
	// mRemarks=remarks;
	// }

	/**
	 * @return the mOrderDate
	 */
	public String getOrderDate() {
		return mOrderDate;
	}

	/**
	 * @param OrderDate to set
	 */
	public void setOrderDate(String date) {
		this.mOrderDate = date;
	}

	/**
	 * @return the mOrderTime
	 */
	public String getOrderTime() {
		return mOrderTime;
	}

	/**
	 * @param OrderTime to set
	 */
	public void setOrderTime(String time) {
		this.mOrderTime = time;
	}

	/**
	 * @return the Cashier
	 */
	public int getCashierId() {
		return mCashierId;
	}

	/**
	 * @param mCashier the mCashier to set
	 */
	public void setCashierId(int id) {
		this.mCashierId = id;
	}

	/**
	 * @return the ServingTable
	 */
	public BeanServingTable getServingTable() {
		return mServingTable;
	}

	/**
	 * @param ServingTable the ServingTable to set
	 */
	public void setServingTable(BeanServingTable servingTable) {
		this.mServingTable = servingTable;
	}

	/**
	 * @return the Waiter
	 */
	public BeanEmployees getServedBy() {
		return mServedBy;
	}

	/**
	 * @param Waiter the Waiter to set
	 */
	public void setServedBy(BeanEmployees waiter) {
		this.mServedBy = waiter;
	}

	/**
	 * @return the ServiceType
	 */
	public PosOrderServiceTypes getServiceType() {
		return mServiceType;
	}

	/**
	 * @param ServiceType the ServiceType to set
	 */
	public void setServiceType(PosOrderServiceTypes serviceType) {
		this.mServiceType = serviceType;
	}
	
//	/**
//	 * @return the mHasExtras
//	 */
//	public boolean hasExtras() {
//		return mHasExtras;
//	}
//
//
//	/**
//	 * @param mHasExtras the mHasExtras to set
//	 */
//	public void setHasExtras(boolean hasExtras) {
//		this.mHasExtras = hasExtras;
//	}


	/**
	 * @return the mSubOrderDtlList
	 */
	public HashMap<String, ArrayList<BeanOrderDetail>> getExtraItemList() {
		return mExtrasList;
	}


	/**
	 * @param mExtrasList the mSubOrderDtlList to set
	 */
	public void setExtraItemList(
			HashMap<String, ArrayList<BeanOrderDetail>> itemList) {
		this.mExtrasList = itemList;
	}

	/**
	 * @return the mParentDtlId
	 */
	public String getParentDtlId() {
		return mParentDtlId;
	}


	/**
	 * @param mParentDtlId the mParentDtlId to set
	 */
	public void setParentDtlId(String parentDtlId) {
		this.mParentDtlId = parentDtlId;
	}


	/**
	 * @return the mChoiceItem
	 */
	public BeanSaleItemChoice getSaleItemChoice() {
		return mSaleItemChoice;
	}


	/**
	 * @param mSaleItemChoice the mChoiceItem to set
	 */
	public void setSaleItemChoice(BeanSaleItemChoice saleItemChoice) {
		this.mSaleItemChoice = saleItemChoice;
	}
	
	/**
	 * @return the mItemType
	 */
	public OrderDetailItemType getItemType() {
		return mItemType;
	}

	/**
	 * @param mItemType the mItemType to set
	 */
	public void setItemType(OrderDetailItemType itemType) {
		this.mItemType = itemType;
	}
	
	/**
	 * @return the mComboContentItem
	 */
	public BeanSaleItemComboContent getComboContentItem() {
		return mComboContentItem;
	}

	/**
	 * @param mComboContentItem the mComboContentItem to set
	 */
	public void setComboContentItem(BeanSaleItemComboContent contentItem) {
		this.mComboContentItem = contentItem;
	}
	
	/**
	 * @return the mComboSubstitutes
	 */
	public HashMap<String, ArrayList<BeanOrderDetail>> getComboSubstitutes() {
		return mComboSubstitutes;
	}

	/**
	 * @param mComboSubstitutes the mComboSubstitutes to set
	 */
	public void setComboSubstitutes(
			HashMap<String, ArrayList<BeanOrderDetail>> comboSubstitutes) {
		this.mComboSubstitutes = comboSubstitutes;
	}
	
	public String getRemarks() {
		return mRemarks;
	}

	public void setRemarks(String remarks) {
		mRemarks = remarks;
	}
	
	
	
	public boolean hasSubItems(){
		return (isComboContentsSelected() || isExtraItemsSelected());
	}
	
	public boolean isComboContentsSelected(){
		return (mComboSubstitutes!=null && mComboSubstitutes.size()>0);
	}
	
	public boolean isExtraItemsSelected(){
		return (mExtrasList!=null && mExtrasList.size()>0);
	}

	public static String[] SEARCH_FIELD_TITLE_LIST={"Code","Name","Price"};
	public static String[] SEARCH_FIELD_LIST={"getSaleItemCode","getSaleItemName","getSaleItemPrice"};
	public static int[] SEARCH_FIELD_WIDTH_LIST={100,400};
	
	public String getSaleItemCode(){
		return mSaleItem.getCode();
	}
	
	public String getSaleItemName(){
		return mSaleItem.getName();
	}
	
	public double getSaleItemPrice(){
		return PosSaleItemUtil.getGrandTotal(mSaleItem);
	}
	
	/* (non-Javadoc)
	 * @see com.indocosmo.pos.components.objectbrowser.IPosBrowsableItem#getItemCode()
	 */
	@Override
	public Object getItemCode() {
		return getSaleItemCode();
	}
	/* (non-Javadoc)
	 * @see com.indocosmo.pos.components.objectbrowser.IPosBrowsableItem#getDisplayText()
	 */
	@Override
	public String getDisplayText() {
		
		return getSaleItemName();
	}
	/* (non-Javadoc)
	 * @see com.indocosmo.pos.components.search.IPosSearchableItem#getFiledList()
	 */
	@Override
	public String[] getFieldList() {
		
		return SEARCH_FIELD_LIST;
	}
	/* (non-Javadoc)
	 * @see com.indocosmo.pos.components.search.IPosSearchableItem#getFiledTitleList()
	 */
	@Override
	public String[] getFieldTitleList() {

		return SEARCH_FIELD_TITLE_LIST;
	}
	/* (non-Javadoc)
	 * @see com.indocosmo.pos.components.search.IPosSearchableItem#getFiledWidthList()
	 */
	@Override
	public int[] getFieldWidthList() {
		return SEARCH_FIELD_WIDTH_LIST;
	}


	/**
	 * @return
	 */
	public int getServingSeat() {

		return mServingSeat;
	}

	/**
	 * @param mServingSeat the mServingSeat to set
	 */
	public void setServingSeat(int servingSeat) {

		this.mServingSeat = servingSeat;
	}

	/**
	 * @return the splitList
	 */
	public ArrayList<BeanOrderSplitDetail> getSplitList() {
		return mSplitList;
	}

	/**
	 * @param splitList the splitList to set
	 */
	public void setSplitList(ArrayList<BeanOrderSplitDetail> splitList) {
		this.mSplitList = splitList;
	}
	
	public boolean hasSplits(){
		
		return (mSplitList!=null && mSplitList.size()>0);
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.pos.forms.components.objectbrowser.IPosBrowsableItem#isVisibleInUI()
	 */
	@Override
	public boolean isVisibleInUI() {

		return true;
	}

	/**
	 * When items are split between people, return the actual share of each person
	 * By default 1
	 * @return the The actual share of this item.
	 */
	public double getItemSplitShare() {
		
		return itemShareInPercentage;
	}

	/**
	 * When items are split between people the actual shared qty of each person is set
	 */
	public double getItemSplitShareQty() {
		
		return itemShareQty;
	}

	/**
	 * When items are split between people the actual shared qty of each person is set
	 * @param itemShareQty the itemShareQty to set
	 */
	public void setItemSplitShareQty(double itemShareQty) {
		
		this.itemShareQty = itemShareQty;
		this.itemShareInPercentage=itemShareQty/mSaleItem.getQuantity();
	}

	/**
	 * @return the mRefundId
	 */
	public int getRefundId() {
		return mRefundId;
	}

	/**
	 * @param mRefundId the mRefundId to set
	 */
	public void setRefundId(int mRefundId) {
		this.mRefundId = mRefundId;
	}

	/**
	 * @return the refundStatus
	 */
	public boolean isRefunded() {
		return mRefundStatus;
	}

	/**
	 * @param refundStatus the refundStatus to set
	 */
	public void setRefundStatus(boolean refundStatus) {
		this.mRefundStatus = refundStatus;
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

	/**
	 * @return the addedToOrderList
	 */
	public boolean isAddedToOrder() {
		return addedToOrder;
	}

	/**
	 * @param addedToOrderList the addedToOrderList to set
	 */
	public void setAddedToOrder(boolean addedToOrderList) {
		this.addedToOrder = addedToOrderList;
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
	 * @return the mPrintingOrder
	 */
	public int getPrintingOrder() {
		return mPrintingOrder;
	}

	/**
	 * @param mPrintingOrder the mPrintingOrder to set
	 */
	public void setPrintingOrder(int mPrintingOrder) {
		this.mPrintingOrder = mPrintingOrder;
	}

	
	
	/**
	 * @return the mTrayWeight
	 */
	public String getTrayWeight() {
		return mTrayWeight;
	}

	/**
	 * @param mTrayWeight the mTrayWeight to set
	 */
	public void setTrayWeight(String mTrayWeight) {
		this.mTrayWeight = mTrayWeight;
	}

	/**
	 * @return the mTrayCode
	 */
	public String getTrayCode() {
		return mTrayCode;
	}

	/**
	 * @param mTrayCode the mTrayCode to set
	 */
	public void setTrayCode(String mTrayCode) {
		this.mTrayCode = mTrayCode;
	}

	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(Object ordDetail) {
		
		 int compareOrder=((BeanOrderDetail)ordDetail).getPrintingOrder();
	        
		 /* For Ascending order*/
	       return this.mPrintingOrder-compareOrder;
	}

	/**
	 * @param dirty
	 */
	public void setKitchenDirty(boolean dirty) {
		
		isKitchenDirty=dirty;
		
	}

	/**
	 * @return the isKitchenDirty
	 */
	public boolean isKitchenDirty() {
		return isKitchenDirty;
	}

	/**
	 * @return the mRefundQuantity
	 */
	public double getRefundQuantity() {
		return mRefundQuantity;
	}

	/**
	 * @param mRefundQuantity the mRefundQuantity to set
	 */
	public void setRefundQuantity(double mRefundQuantity) {
		this.mRefundQuantity = mRefundQuantity;
	}

	/**
	 * @return the mVoidTime
	 */
	public String getVoidTime() {
		return mVoidTime;
	}

	/**
	 * @param mVoidTime the mVoidTime to set
	 */
	public void setVoidTime(String mVoidTime) {
		this.mVoidTime = mVoidTime;
	}

	/**
	 * @return the mRefundTax1Amount
	 */
	public double getRefundTax1Amount() {
		return mRefundTax1Amount;
	}

	/**
	 * @param mRefundTax1Amount the mRefundTax1Amount to set
	 */
	public void setRefundTax1Amount(double mRefundTax1Amount) {
		this.mRefundTax1Amount = mRefundTax1Amount;
	}

	/**
	 * @return the mRefundTax2Amount
	 */
	public double getRefundTax2Amount() {
		return mRefundTax2Amount;
	}

	/**
	 * @param mRefundTax2Amount the mRefundTax2Amount to set
	 */
	public void setRefundTax2Amount(double mRefundTax2Amount) {
		this.mRefundTax2Amount = mRefundTax2Amount;
	}

	/**
	 * @return the mRefundTax3Amount
	 */
	public double getRefundTax3Amount() {
		return mRefundTax3Amount;
	}

	/**
	 * @param mRefundTax3Amount the mRefundTax3Amount to set
	 */
	public void setRefundTax3Amount(double mRefundTax3Amount) {
		this.mRefundTax3Amount = mRefundTax3Amount;
	}

	/**
	 * @return the mRefundGSTAmount
	 */
	public double getRefundGSTAmount() {
		return mRefundGSTAmount;
	}

	/**
	 * @param mRefundGSTAmount the mRefundGSTAmount to set
	 */
	public void setRefundGSTAmount(double mRefundGSTAmount) {
		this.mRefundGSTAmount = mRefundGSTAmount;
	}

	/**
	 * @return the mRefundSCAmount
	 */
	public double getRefundSCAmount() {
		return mRefundSCAmount;
	}

	/**
	 * @param mRefundSCAmount the mRefundSCAmount to set
	 */
	public void setRefundSCAmount(double mRefundSCAmount) {
		this.mRefundSCAmount = mRefundSCAmount;
	}
	
}
