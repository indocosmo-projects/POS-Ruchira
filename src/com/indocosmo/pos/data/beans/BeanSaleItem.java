/**
 * @author jojesh
 *This class will act as a structure from pos items.
 *Add required properties and functionalities for each pos items
 */

package com.indocosmo.pos.data.beans;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.KeyStroke;

import com.indocosmo.pos.common.PosLog;
import com.indocosmo.pos.common.utilities.PosCurrencyUtil;
import com.indocosmo.pos.common.utilities.PosSaleItemUtil;
import com.indocosmo.pos.common.utilities.PosUomUtil;
import com.indocosmo.pos.data.beans.BeanTaxGlobalParam.POSTaxationBasedOn;
import com.indocosmo.pos.data.providers.shopdb.PosDiscountItemProvider;
import com.indocosmo.pos.data.providers.shopdb.PosSaleItemProvider;
import com.indocosmo.pos.data.providers.shopdb.PosTaxItemProvider.TaxCalculationMethod;

public class BeanSaleItem extends BeanMasterBase implements Cloneable {

	public static final int ITEM_ATTRIBUTE_COUNT = 5;
	/**  options from table + system defined option "None" **/
	public static final int ITEM_ATTRIBUTE_OPTION_MAX_COUNT = 7 + 1;

	private int mStockItemId;
//	protected String mCode;
	protected String mBarCode;
//	protected String mName;
	protected String mAlternativeName;
	protected String mNameToPrint;
	protected String mAlternativeNameToPrint;
//	protected String mDescription;
	protected String mFgColor;
	protected String mBgColor;
	protected BeanSubClass mSubClass;
	protected int mSubClassId;
	protected Boolean mIsValid;
	protected int mKitchenId;
	protected Boolean mIsOpen;
	protected BeanUOM mUom;
//	protected double mUnitPrice;
	protected double mItemFixedPrice;
	protected double mItemTotal;
	//	protected double mTaxedPrice;
	protected BeanTax mTaxItem;
	protected BeanTax mTaxHomeService;
	protected BeanTax mTaxTableService;
	protected BeanTax mTaxTakeAwayService;
	private POSTaxationBasedOn  mTaxationBasedOn=POSTaxationBasedOn.SaleItem;
	protected TaxCalculationMethod mTaxCalculationMethod=null;//TaxCalculationMethod.None;
	protected Boolean mRequireWeight;
	protected double mQuantity = 1;
	protected BeanDiscount mDiscount;
	protected int mGroupID;
//	protected String mRemarks;
	protected ArrayList<BeanSaleItemAttribute> mSaleItemAttributes;
	protected boolean mIsGroup;
	protected boolean mHasChoices;
	protected ArrayList<BeanSaleItem> mSubItemList;
	protected ArrayList<String> mSaleItemPromotionList;
	protected boolean mIsComboItem;
	protected double mCustomerPrice=0;
//	protected Double mUnitPrice=null;
	protected BufferedImage item_image;
	protected double mItemRetailPrice;
	protected double mItemWholesalePrice;
	protected boolean mIsPercetangeWholesalePrice;
	protected String mHSNCode;
	protected boolean mIsPrintableToKitchen;
	
	
	private HashMap<Integer, ArrayList<BeanSaleItem>> mExtras;

	public BeanSaleItem() {
		//Comments added by Udhayan for DayStart module problem On 23-Jul-2021
		//PosLog.debug("BeanSaleItem==========inside==========>");
		mSaleItemAttributes = new ArrayList<BeanSaleItemAttribute>();
		//PosLog.debug("mSaleItemAttributes==========after==========>");
		mExtras=new HashMap<Integer, ArrayList<BeanSaleItem>>();
		//PosLog.debug("mExtras==========after==========>");
		mSubClass = new BeanSubClass();
	//	PosLog.debug("mSubClass==========after==========>");
	}

//	public String getName() {
//		return mName;
//	}
//
//	public void setName(String itemName) {
//		this.mName = itemName;
//	}
//
//	public String getCode() {
//		return mCode;
//	}
//
//	public void setCode(String itemCode) {
//		this.mCode = itemCode;
//	}
//
//	public String getDescription() {
//		return mDescription;
//	}
//
//	public void setDescription(String itemDescription) {
//		this.mDescription = itemDescription;
//	}

	public String getSubClassCode() {
		return mSubClass.getCode();
	}

	 public void setSubClass(BeanSubClass itemSubClass) {
	 this.mSubClass = itemSubClass;
	 }
	
	 public BeanSubClass getSubClass() {
	 return mSubClass;
	 }

	public int getSubClassID() {
		return mSubClassId;
	}

	public void setSubClassId(int subClassID) {
		this.mSubClassId = subClassID;
	}

	public Boolean isValid() {
		return mIsValid;
	}

	public void setValid(Boolean itemIsValid) {
		this.mIsValid = itemIsValid;
	}

	public String getAlternativeName() {
		return mAlternativeName;
	}

	public void setAlternativeName(String itemAlternativeName) {
		this.mAlternativeName = itemAlternativeName;
	}

	public String getNameToPrint() {
//		return (mNameToPrint==null || mNameToPrint.trim().equals(""))?mName:mNameToPrint;
		return (mNameToPrint);
	}

	public void setNameToPrint(String itemNameToPrint) {
		this.mNameToPrint = itemNameToPrint;
	}

	/**
	 * @return
	 */
	public String getAlternativeNameToPrint() {
		return mAlternativeNameToPrint;
	}

	/**
	 * @param itemAlternativeNameToPrint
	 */
	public void setAlternativeNameToPrint(String itemAlternativeNameToPrint) {
		this.mAlternativeNameToPrint = itemAlternativeNameToPrint;
	}

	/**
	 * @return
	 */
	public String getBarCode() {
		return mBarCode;
	}

	/**
	 * @param itemBarCode
	 */
	public void setBarCode(String itemBarCode) {
		this.mBarCode = itemBarCode;
	}

	/**
	 * @return
	 */
	public int getKitchenId() {
		return mKitchenId;
	}

	/**
	 * @param itemKitchenId
	 */
	public void setKitchenId(int itemKitchenId) {
		this.mKitchenId = itemKitchenId;
	}

	/**
	 * @return
	 */
	public Boolean isOpen() {
		return mIsOpen;
	}

	/**
	 * @param itemIsOpen
	 */
	public void setOpen(Boolean itemIsOpen) {
		this.mIsOpen = itemIsOpen;
	}

	/**
	 * @return
	 */
	public BeanUOM getUom() {
		return mUom;
	}

	/**
	 * @param itemUomId
	 */
	public void setUom(BeanUOM uom) {
		this.mUom = uom;
	}

	/**
	 * @return
	 */
	public Boolean isRequireWeighing() {
		return mRequireWeight;
	}

	/**
	 * @param itemRequireWeight
	 */
	public void setRequireWeighing(Boolean itemRequireWeight) {
		this.mRequireWeight = itemRequireWeight;
	}

	/**
	 * @param quantity
	 */
	public void setQuantity(double quantity) {
		if(mDiscount!=null &&  quantity<mDiscount.getRequiredQuantity())
			setDiscount(new PosDiscountItemProvider().getNoneDiscount());
		this.mQuantity = PosUomUtil.roundTo(quantity,getUom());
	}

	/**
	 * @return
	 */
	public double getQuantity() {
		return this.mQuantity;
//		return this.mQuantity;
	}

//	/**
//	 * This function will return the sale item  price (which is exclusive of tax)
//	 * 
//	 * @return
//	 */
//	public double getPrice() {
//		return PosNumberUtil.roundTo((mTaxCalculationMethod==TaxCalculationMethod.None || mTaxCalculationMethod==TaxCalculationMethod.ExclusiveOfTax)?
//				getTotalFixedPrice():getTotalFixedPrice()-getTaxAmount());
//	}
	
//	/**
//	 * This function will return the sale item  price (which is exclusive of tax).
//	 * 
//	 * @return
//	 */
//	public double getPrice() {
//		return PosNumberUtil.roundTo((mTaxCalculationMethod==TaxCalculationMethod.None || mTaxCalculationMethod==TaxCalculationMethod.ExclusiveOfTax)?
//				getTotalFixedPrice():getTotalFixedPrice()-getTaxAmount());
//	}
//	
//	public double getUnitPrice(){
//		return getUnitPrice(getTaxCalculationMethod()==TaxCalculationMethod.ExclusiveOfTax);
//	}
	
	/**
	 * Unit price which is exclusive of tax.
	 * 
	 * @return
	 */
//	public double getUnitPrice(boolean isTaxExcl){
////			return PosNumberUtil.roundTo(getTotalPrice()/getQuantity());
////		return getTotalPrice()/getQuantity();
////		return PosNumberUtil.roundTo((mTaxCalculationMethod==TaxCalculationMethod.None || mTaxCalculationMethod==TaxCalculationMethod.ExclusiveOfTax)?
////				getFixedPrice():getFixedPrice()-getTaxAmount()/getQuantity());
//		return getFixedPrice();
//		
////		return (isTaxExcl)?
////				mUnitPrice:mItemFixedPrice;
//		
//		
////		return PosNumberUtil.roundTo(mItemFixedPrice);
//		
//	}
	
	
	/**
	 * 
	 * @returns the items fixed price
	 */
	public double getDisplayPrice() {
		
		final  double displayPrice=PosSaleItemUtil.getGrandTotalBasedOnService(this);
		
		return displayPrice==0?mItemFixedPrice:displayPrice;
	}
	
	/**
	 * This function will return the sale item fixed  price (which is exclusive/inclusive of tax).
	 * 
	 * @return
	 */
//	public double getFixedPrice() {
//		return mItemFixedPrice;
//	}
	
	/**
	 * 
	 * @returns the items fixed price
	 */
	public double getFixedPrice() {
		return mItemFixedPrice;
	}

	
	/**
	 * This function will return the total sale item fixed  price (which is exclusive/inclusive of tax).
	 * 
	 * @return
	 */
//	public double getTotalPrice() {
//		// return PosNumberUtil.roundTo(getUnitPrice(true)*mQuantity);
//		return (mItemFixedPrice*mQuantity)
//				- ((mTaxCalculationMethod == TaxCalculationMethod.ExclusiveOfTax) ? getTotalTaxAmount()
//						: 0);
//	}

//	/**
//	 * This function will return the sale item  price * quantity (which is exclusive of tax)
//	 * 
//	 * @return
//	 */
//	public double getTotalPrice() {
////		return (mTaxCalculationMethod==TaxCalculationMethod.None || mTaxCalculationMethod==TaxCalculationMethod.ExclusiveOfTax)?
////				getTotalFixedPrice():getTotalFixedPrice()-getTaxAmount();
//		return getTotalFixedPrice();
//				
//	}

	/**
	 * Sets the price for the item which can be exclusive or inclusive of tax. 
	 * Set the tax calculation method and tax item before setting the item price.
	 * @param price
	 */
	public void setFixedPrice(double price) {
		if(mTaxCalculationMethod==null || (mTaxCalculationMethod!=TaxCalculationMethod.None && mTaxItem==null && mTaxationBasedOn==POSTaxationBasedOn.SaleItem))
			PosLog.debug( new Exception("Tax item has not been set for ->  " + getCode() + " "+ getDisplayText()));
//		else{
			this.mItemFixedPrice=price;
//			this.mUnitPrice = price;
//		}
	}
	
//	public void setUnitPrice(double price){
//		this.mUnitPrice = price;
////		calculateTaxes(true);
//	}

	/**
	 * @param discount
	 */
	public void setDiscount(BeanDiscount discount) {
		this.mDiscount = discount;
	}

	/**
	 * @return
	 */
	public BeanDiscount getDiscount() {
		return mDiscount;
	}

	/**
	 * Return the item discount amount;
	 * 
	 * @return
	 */
//	public double getDiscountAmount() {
//		if (mDiscount == null)
//			return 0;
//////		double discAmount = (mDiscount.isPercentage()) ? 
//////				((mDiscount.getRequiredQuantity()==0)?getTotalPrice(): getPrice()) * mDiscount.getPrice() / 100 : mDiscount.getPrice();
//////		discAmount=((mDiscount.getRequiredQuantity()>=1) ? (discAmount - mDiscount.getVariants()) : discAmount);
////		double discAmount = (mDiscount.isPercentage()) ?getTotalPrice() * mDiscount.getPrice() / 100 : mDiscount.getPrice();
////		return PosNumberUtil.roundTo(discAmount); 
//		
//
//		double discAmount = (mDiscount.isPercentage()) ?getDiscountableAmount() * mDiscount.getPrice() / 100 : mDiscount.getPrice();
//		return PosNumberUtil.roundTo(discAmount); 
//		
//	}
//	
//	public double getTotalTaxableAmount(){
//		double taxableAmount=0;
////		if(this.getTax().isClaculateTaxBeforDiscount() || this.getDiscount().isAdjustmentDiscount())
//		if(this.getTax().isClaculateTaxBeforDiscount()||getDiscount().getEditType()!=EditTypes.None)
//			taxableAmount=getFixedPrice()*getQuantity(); //Should use the actual price for calculating tax
//		else
//			taxableAmount=(getFixedPrice()*getQuantity())-this.getDiscountAmount();
//		return PosNumberUtil.roundTo(taxableAmount);
//	}
	
//	private double getDiscountableAmount(){
//		double discount=0;
//		if(PosEnvSettings.getInstance().getTaxParam().isCalculateTaxBeforeDiscount()){
//			discount=getTotalPrice()+getTotalTaxAmount();
//		}else{
//			discount=getTotalPrice();
//		}
//		return PosNumberUtil.roundTo(discount);
//	}
	
	
//	/**
//	 * Return the total discount amount with variants;
//	 * @return
//	 */
//	public double getTotalDiscountAmount() {
//		if (mDiscount == null)
//			return 0;
////		double discAmount = (mDiscount.isPercentage()) ? getPrice()
////				* mDiscount.getPrice() / 100 : mDiscount.getPrice();
////				return PosNumberUtil.roundTo((mDiscount.isAdjustmentDiscount()) ? discAmount
////						- mDiscount.getVariants() : discAmount*mQuantity); 
//
////		final double amount=getActualDiscountQuantity()*getDiscountAmount();
//		final double amount=getDiscountAmount();
//		return  PosNumberUtil.roundTo(amount+mDiscount.getVariants());
//	}
	
	/**
	 * Calculate the actual quantity on which the discount can be applied
	 * @return
	 */
//	private double getActualDiscountQuantity(){
//		int actualQuantity=0;
//		if(mDiscount.getRequiredQuantity()==0)
//			actualQuantity=1;
//		else
//			actualQuantity=(int)(mQuantity/mDiscount.getRequiredQuantity());
//		return actualQuantity;
//	}

	/**
	 * Item price after discount and before adding tax
	 * 
	 * @return
	 */
//	public double getDiscountedPrice() {
//		return PosNumberUtil.roundTo(getTotalPrice() - getDiscountAmount());
//	}
	
//	/**
//	 * Total Item price after discount and before adding tax
//	 * 
//	 * @return
//	 */
//	public double getTotalDiscountedPrice() {
//		return PosNumberUtil.roundTo(getTotalPrice() - getTotalDiscountAmount());
//	}

	/**
	 * Returns the  quantity * item price (inclusive of tax)
	 * 
	 * @return
	 */
//	public double getTotalPrice() {
//		double total = mQuantity * ((mTaxCalculationMethod==TaxCalculationMethod.InclusiveOfTax)?mItemPrice:
//			mItemPrice+getTaxAmount());
//		return total;
//	}

	/**
	 * Return the total item price + tax - discount
	 * 
	 * @return
	 */
//	public double getGrandTotal() {
//		double total = getTotalPrice()+((mTaxCalculationMethod==TaxCalculationMethod.ExclusiveOfTax)?getTotalTaxAmount():0);
//		if (mDiscount != null)
//			total -= getTotalDiscountAmount();
//		return PosNumberUtil.roundTo(total);
//	}

	public boolean isGroupItem() {
		return mIsGroup;
	}

	public void setGroupItem(boolean isGroup) {
		this.mIsGroup = isGroup;
	}
	
	public boolean hasChoices() {
		return mHasChoices;
	}

	public void hasChoices(boolean hasChoices) {
		this.mHasChoices = hasChoices;
	}

	public int getGroupID() {
		return mGroupID;
	}

	public void setGroupID(int groupID) {
		this.mGroupID = groupID;
	}

	public ArrayList<BeanSaleItem> getSubItemList() {
		return mSubItemList;
	}

	public void setSubItemList(ArrayList<BeanSaleItem> subItemList) {
		this.mSubItemList = subItemList;
	}

//	public int getId() {
//		return mId;
//	}

//	/**
//	 * @param id
//	 */
//	public void setId(int id) {
//		this.mId = id;
//	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.ics.pos.terminal.components.objectbrowser.IPosBrowsableItem#getCaption
	 * ()
	 */
//	@Override
//	public String getDisplayText() {
//		return mName;
//	}

	/**
	 * @param index
	 * @return
	 */
	public String getAttributeName(int index) {
		if (mSaleItemAttributes != null && index >= 0
				&& index < mSaleItemAttributes.size())
			return mSaleItemAttributes.get(index).getArtributeName();
		else
			return "";
	}

	/**
	 * @param name
	 * @param options
	 */
	public void addAttribute(String name, String options) {
		BeanSaleItemAttribute attrib = new BeanSaleItemAttribute(
				mSaleItemAttributes.size(), name, options);
		mSaleItemAttributes.add(attrib);
	}


	/**
	 * @param attributeName
	 * @param string
	 * @param selectedAttribute
	 */
	public void addAttribute(String name, String options,
			String selectedAttribute) {
		BeanSaleItemAttribute attrib = new BeanSaleItemAttribute(
				mSaleItemAttributes.size(), name, options);
		for(int i=0;i<attrib.getAttributeOptions().length;i++){
			if(attrib.getAttributeOptions()[i].trim().equalsIgnoreCase(selectedAttribute)){
				attrib.setSelectedOptionIndex(i);
			}
		}
		mSaleItemAttributes.add(attrib);
	}
	/**
	 * @param index
	 * @return
	 */
	public String[] getAttributeOptions(int index) {
		String[] emptyArray = { "" };
		if (mSaleItemAttributes != null && index >= 0
				&& index < mSaleItemAttributes.size())
			return mSaleItemAttributes.get(index).getAttributeOptions();
		else
			return emptyArray;

	}

	/**
	 * @return
	 */
	public ArrayList<BeanSaleItemAttribute> getAtrributeList() {
		return mSaleItemAttributes;
	}

	public void setAttributeList(ArrayList<BeanSaleItemAttribute> atribList) {
		mSaleItemAttributes = atribList;
	}

	/**
	 * @param index
	 * @return
	 */
	public BeanSaleItemAttribute getAtrribute(int index) {
		return (mSaleItemAttributes != null && mSaleItemAttributes.size() > 0 && index < mSaleItemAttributes
				.size()) ? mSaleItemAttributes.get(index) : null;
	}

	public String getAttribSelectedOption(int index) {
		String selectedOption = "";
		if (mSaleItemAttributes != null && index >= 0
				&& index < mSaleItemAttributes.size()) {
			selectedOption = (getAtrribute(index) != null) ? getAtrribute(index)
					.getSelectedOption() : "";
		}
		return selectedOption;
	}
	
	/**
	 * @return
	 */
	public boolean hasSelectedAttributes(){
		
		boolean result=false;
		
		if(mSaleItemAttributes!=null && mSaleItemAttributes.size()>0){
			
			for(int index=0; index<mSaleItemAttributes.size();index++){
				
				final String attrOpt=getAttribSelectedOption(index);
				if(!attrOpt.trim().isEmpty() && !attrOpt.equals(PosSaleItemProvider.DEFAULT_ATTRIBUTE_OPTION)){
					
					result=true;
					break;
				}
					
			}
		}
		
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#clone()
	 */
	public BeanSaleItem clone() {
		BeanSaleItem cloneObject = null;
		try {
			cloneObject = (BeanSaleItem) super.clone();
			/** clone attributes since it is editable **/
			if (mSaleItemAttributes != null) {
				ArrayList<BeanSaleItemAttribute> newSaleItemAttributes = new ArrayList<BeanSaleItemAttribute>();
				for (BeanSaleItemAttribute attribute : mSaleItemAttributes)
					newSaleItemAttributes.add(attribute.clone());
				cloneObject.setAttributeList(newSaleItemAttributes);
				if(mExtras!=null)
					cloneObject.setExtras(cloneExtras());
			}
			/** clone discounts since it is editable **/
			if (mDiscount != null)
				cloneObject.setDiscount(mDiscount.clone());
			if (mTaxItem != null)
				cloneObject.setTax(mTaxItem.clone());

		} catch (Exception e) {
			PosLog.write(this, "clone", e);
		}
		return cloneObject;
	}
	
	private HashMap<Integer, ArrayList<BeanSaleItem>> cloneExtras() throws CloneNotSupportedException {
		HashMap<Integer, ArrayList<BeanSaleItem>> newExtars=new HashMap<Integer, ArrayList<BeanSaleItem>>();
		for(int key:mExtras.keySet()){
			ArrayList<BeanSaleItem> saleItemList=new ArrayList<BeanSaleItem>();
			try {
				for(BeanSaleItem item:mExtras.get(key)){
					saleItemList.add((BeanSaleItem)item.clone());
				}
			} catch (Exception e) {
				PosLog.write(this, "cloneExtras", e);
				throw e;
			}
			newExtars.put(key, saleItemList);
		}
		return newExtars;
	}

	public String getForeground() {
		return mFgColor;
	}

	public void setForeground(String color) {
		mFgColor = color;
	}

	public String getBackground() {
		return mBgColor;
	}

	public void setBackground(String color) {
		mBgColor = color;
	}

	/**
	 * @return
	 */
	public boolean isComboItem() {
		return mIsComboItem;
	}

	/**
	 * @param isComboItem
	 */
	public void setComboItem(boolean isComboItem) {
		mIsComboItem = isComboItem;
	}

	/**
	 * Calculates the TAX1 amount (For single quantity)
	 * @return TAX1 Amount
	 */ 
	public double getT1TaxAmount() {
		if (mTaxItem != null && mTaxItem.getTaxAmount() != null)
			return mTaxItem.getTaxAmount().getTaxOneAmount();
		else
			return 0;
	}
	
	/**
	 * Calculates the Total TAX1 amount.
	 * @return TAX1 Amount
	 */ 
//	public double getTotalT1TaxAmount() {
//			return getT1TaxAmount();
//	}

	/**
	 * Calculates the TAX2 amount (For single quantity)
	 * @return TAX2 Amount
	 */ 
	public double getT2TaxAmount() {
		if (mTaxItem != null && mTaxItem.getTaxAmount() != null)
			return mTaxItem.getTaxAmount().getTaxTwoAmount();
		else
			return 0;
	}
	
	
	/**
	 * Calculates the Total TAX2 amount.
	 * @return TAX2 Amount
	 */ 
//	public double getTotalT2TaxAmount() {
//			return getT2TaxAmount();//*getQuantity();
//	}


	/**
	 * Calculates the TAX3 amount (For single quantity)
	 * @return TAX3 Amount
	 */ 
	public double getT3TaxAmount() {
		if (mTaxItem != null && mTaxItem.getTaxAmount() != null)
			return mTaxItem.getTaxAmount().getTaxThreeAmount();
		else
			return 0;
	}
	
	/**
	 * Calculates the Total TAX3 amount.
	 * @return TAX3 Amount
	 */ 
//	public double getTotalT3TaxAmount() {
//			return getT3TaxAmount();//*getQuantity();
//	}
	
	/**
	 * Calculates the Service TAX amount (For single quantity)
	 * @return service tax Amount
	 */ 
	public double getServiceTaxAmount() {
		if (mTaxItem != null && mTaxItem.getTaxAmount() != null)
			return mTaxItem.getTaxAmount().getServiceTaxAmount();
		else
			return 0;
	}
	
	/**
	 * Calculates the Total Service TAX amount.
	 * @return Service Tax amount
	 */ 
//	public double getTotalServiceTaxAmount() {
//			return getServiceTaxAmount();//*getQuantity();
//	}
	
	/**
	 * Calculates the GST amount (For single quantity)
	 * @return GST Amount
	 */ 
	public double getGSTAmount() {
		if (mTaxItem != null && mTaxItem.getTaxAmount() != null)
			return mTaxItem.getTaxAmount().getGSTAmount();
		else
			return 0;
	}
	
	/**
	 * Calculates the GST amount.
	 * @return GST amount
	 */ 
//	public double getTotalGSTAmount() {
//			return getGSTAmount();//*getQuantity();
//	}

	/**
	 * Returns the tax calculated tax amount for single quantity
	 * 
	 * @return the tax amount
	 */
//	private double getTaxAmount() {
//		return getT1TaxAmount() + getT2TaxAmount() + getT3TaxAmount()
//				+ getGSTAmount() + getServiceTaxAmount();
//	}
	
	/**
	 * Returns the total tax calculated tax amount for item quantity
	 * 
	 * @return the tax amount
	 */
//	public double getTotalTaxAmount() {
////		return PosNumberUtil.roundTo(getTaxAmount()*getQuantity());
//		return PosNumberUtil.roundTo(getTaxAmount());
//	}

	/**
	 * @return
	 */
	public TaxCalculationMethod getTaxCalculationMethod() {
		return mTaxCalculationMethod;
	}

	/**
	 * @param itemTaxCalculationMethod
	 */
	public void setTaxCalculationMethod(
			TaxCalculationMethod itemTaxCalculationMethod) {
		mTaxCalculationMethod = itemTaxCalculationMethod;
//		calculateTaxes();
	}

	/**
	 * Set the tax item and calculates the tax
	 * Set the tax calculation method before setting tax
	 * @param mtaxItem
	 */
	public void setTax(BeanTax taxItem) {
		if(mTaxCalculationMethod==TaxCalculationMethod.None)
			PosLog.debug(new Exception("Tax calculation method has not been set for -> " + getCode() + " "+ getDisplayText()));
		else
			this.mTaxItem = taxItem;
	}

	/**
	 * Returns the tax item
	 * `
	 * @return
	 */
	public BeanTax getTax() {
		return mTaxItem;
	}

//	private double calculateTaxes(boolean forceExclusive) {
////		if (mTaxItem == null)
////			return 0;
////		PosTaxAmountObject taxAmount = ((forceExclusive)?PosTaxUtil.calculateExclusiveTax(this):
////			PosTaxUtil.calculateTaxAmounts(this));
////		this.mTaxItem.setTaxAmount(taxAmount);
//		return 0;// taxAmount.getTotalTaxAmount();
//	}

	/**
	 * return the taxable amount based on parameter settings
	 * 
	 * @return
	 */
//	public double getTaxableAmount(){
//		double taxableAmount=0;
////		if(this.getTax().isClaculateTaxBeforDiscount() || this.getDiscount().isAdjustmentDiscount())
//		if(this.getTax().isClaculateTaxBeforDiscount())
//			taxableAmount=mItemFixedPrice; //Should use the actual price for calculating tax
//		else
//			taxableAmount=(mItemFixedPrice)-this.getDiscountAmount();
//		return PosNumberUtil.roundTo(taxableAmount);
//	}
	

	
//	public double getTaxableAmount() {
//		double taxableAmount = 0;
//		PosTaxGlobalParamObject taxParam = PosEnvSettings.getInstance()
//				.getTaxParam();
//		if (taxParam.isCalculateTaxBeforeDiscount())
//			taxableAmount = getPrice();
//		else
//			taxableAmount = getDiscountedPrice() - mDiscount.getVariants();
//		return taxableAmount;
//	}

//	public String getRemarks() {
//		return mRemarks;
//	}
//
//	public void setRemarks(String remarks) {
//		mRemarks = remarks;
//	}

	/**
	 * @return the Sale Item Promotion List
	 */
	public ArrayList<String> getSaleItemPromotionList() {
		return mSaleItemPromotionList;
	}

	/**
	 * @param sale Item Promotion List to set
	 */
	public void setPromotionList(ArrayList<String> saleItemPromotionList) {
		this.mSaleItemPromotionList = saleItemPromotionList;
	}
	
	public void setPromotion(BeanDiscount promotion){
		if(mSaleItemPromotionList.contains(promotion.getCode())){
			setDiscount(promotion.clone());
		}
	}

	/**
	 * @return the mCustomerPrice
	 */
	public double getCustomerPrice() {
		return mCustomerPrice;
	}

	/**
	 * @param mCustomerPrice the mCustomerPrice to set
	 */
	public void setCustomerPrice(double customerPrice) {
		this.mCustomerPrice = customerPrice;
	}

	/**
	 * @return the mItemTotal
	 */
	public double getItemTotal() {
		return mItemTotal;
	}

	/**
	 * @param mItemTotal the mItemTotal to set
	 */
	public void setItemTotal(double mItemTotal) {
		this.mItemTotal = mItemTotal;
	}
	
	/**
	 * @return the extras
	 */
	public HashMap<Integer, ArrayList<BeanSaleItem>> getExtras() {
		return mExtras;
	}

	/**
	 * @param extras the extras to set
	 */
	public void setExtras(HashMap<Integer, ArrayList<BeanSaleItem>> extras) {
		this.mExtras = extras;
	}

	public static final String[] FILED_VALUE_LIST={"getCode","getName","getBarCode","getDisplayPrice"};
	public static final String[] FILED_TITLE_LIST={"Code","Name","","Price"};
	public static final int[] FILED_WIDTH_LIST={100,450,0};
	public static final String[] FILED_FORMAT_LIST={"","","",PosCurrencyUtil.getCurrencyFormatString()};
	/* (non-Javadoc)
	 * @see com.indocosmo.pos.components.search.IPosSearchableItem#getFiledList()
	 */
	@Override
	public String[] getFieldList() {
		return FILED_VALUE_LIST;
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.pos.components.search.IPosSearchableItem#getFiledTitleList()
	 */
	@Override
	public String[] getFieldTitleList() {
		return FILED_TITLE_LIST;
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.pos.components.search.IPosSearchableItem#getFiledWidthList()
	 */
	@Override
	public int[] getFieldWidthList() {
		return FILED_WIDTH_LIST;
	}

	public void setSaleItemImage(BufferedImage image){
		this.item_image=image;
	}
	
	public BufferedImage getSaleItemImage(){
		return this.item_image;
	}
	
	/**
	 * @return the TaxHomeDeliveryService
	 */
	public BeanTax getTaxHomeService() {
		return mTaxHomeService;
	}

	/**
	 * @param mTaxHomeDeliveryService the mTaxHomeDeliveryService to set
	 */
	public void setTaxHomeService(BeanTax mTaxHomeService) {
		this.mTaxHomeService = mTaxHomeService;
	}

	/**
	 * @return the mTaxTableService
	 */
	public BeanTax getTaxTableService() {
		return mTaxTableService;
	}

	/**
	 * @param mTaxTableService the mTaxTableService to set
	 */
	public void setTaxTableService(BeanTax mTaxTableService) {
		this.mTaxTableService = mTaxTableService;
	}

	/**
	 * @return the mTaxTakeAwayService
	 */
	public BeanTax getTaxTakeAwayService() {
		return mTaxTakeAwayService;
	}

	/**
	 * @param mTaxTakeAwayService the mTaxTakeAwayService to set
	 */
	public void setTaxTakeAwayService(BeanTax mTaxTakeAwayService) {
		this.mTaxTakeAwayService = mTaxTakeAwayService;
	}

	/**
	 * @return the mTaxationBasedOn
	 */
	public POSTaxationBasedOn getTaxationBasedOn() {
		return mTaxationBasedOn;
	}

	/**
	 * @param mTaxationBasedOn the mTaxationBasedOn to set
	 */
	public void setTaxationBasedOn(POSTaxationBasedOn mTaxationBasedOn) {
		this.mTaxationBasedOn = mTaxationBasedOn;
	}
	
	
	

	/**
	 * @return the mItemWholesalePrice
	 */
	public double getWholesalePrice() {
		return mItemWholesalePrice;
	}

	/**
	 * @param mItemWholesalePrice the mItemWholesalePrice to set
	 */
	public void setWholesalePrice(double mItemWholesalePrice) {
		this.mItemWholesalePrice = mItemWholesalePrice;
	}

	/**
	 * @return the mIsPercetangeWholesalePrice
	 */
	public boolean isPercetangeWholesalePrice() {
		return mIsPercetangeWholesalePrice;
	}

	/**
	 * @param mIsPercetangeWholesalePrice the mIsPercetangeWholesalePrice to set
	 */
	public void setIsPercetangeWholesalePrice(boolean mIsPercetangeWholesalePrice) {
		this.mIsPercetangeWholesalePrice = mIsPercetangeWholesalePrice;
	}

	
	 
	/**
	 * @return the mItemRetailPrice
	 */
	public double getRetailPrice() {
		return mItemRetailPrice;
	}

	/**
	 * @param mItemRetailPrice the mItemRetailPrice to set
	 */
	public void setRetailPrice(double mItemRetailPrice) {
		this.mItemRetailPrice = mItemRetailPrice;
	}

	
	
	/**
	 * @return the mHSNCode
	 */
	public String getHSNCode() {
		return mHSNCode;
	}

	/**
	 * @param mHSNCode the mHSNCode to set
	 */
	public void setHSNCode(String mHSNCode) {
		this.mHSNCode = mHSNCode;
	}
	
	
	
	/**
	 * @return the mIsPrintableToKitchen
	 */
	public boolean isPrintableToKitchen() {
		return mIsPrintableToKitchen;
	}

	/**
	 * @param mIsPrintableToKitchen the mIsPrintableToKitchen to set
	 */
	public void setIsPrintableToKitchen(boolean mIsPrintableToKitchen) {
		this.mIsPrintableToKitchen = mIsPrintableToKitchen;
	}

	/**
	 * @return the mStockItemId
	 */
	public int getStockItemId() {
		return mStockItemId;
	}

	/**
	 * @param mStockItemId the mStockItemId to set
	 */
	public void setStockItemId(int mStockItemId) {
		this.mStockItemId = mStockItemId;
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.pos.data.beans.BeanMasterBase#getFieldFormatList()
	 */
	@Override
	public String[] getFieldFormatList() {
		// TODO Auto-generated method stub
		return FILED_FORMAT_LIST;
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
	 * @param mUnitPrice the mUnitPrice to set
	 */
//	public void setUnitPrice(double mUnitPrice) {
//		this.mUnitPrice = mUnitPrice;
//	}

	/**
	 * @return the mUnitPrice
	 */
//	public Double getUnitPrice() {
////		return ((mUnitPrice==null)?mItemFixedPrice:mUnitPrice);
//		return mItemFixedPrice;
//	}



}
