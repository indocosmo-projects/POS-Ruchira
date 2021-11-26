/**
 * 
 */
package com.indocosmo.pos.data.beans;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import javax.swing.KeyStroke;

import com.indocosmo.pos.common.PosLog;

/**
 * @author jojesh
 *
 */
public class BeanDiscount extends BeanMasterBase implements Cloneable{
	
	public enum EditTypes{
		None(0),
		UnitPrice(1),
		NetPrice(2);
		
		private static final Map<Integer,EditTypes> mLookup=new HashMap<Integer,EditTypes>();

		static {
			for(EditTypes pl:EnumSet.allOf(EditTypes.class))
				mLookup.put(pl.getCode(), pl);
		}

		private Integer mCode;
		private EditTypes(Integer code){
			mCode=code;
		}

		public int getCode(){
			return mCode;
		}

		public static EditTypes get(Integer code){
			return mLookup.get(code);
		}
	}
	
	public enum PermittedLevel{
		BOTH(0),
		ITEM(1),
		BILL(2);
		
		private static final Map<Integer,PermittedLevel> mLookup=new HashMap<Integer,PermittedLevel>();

		static {
			for(PermittedLevel pl:EnumSet.allOf(PermittedLevel.class))
				mLookup.put(pl.getCode(), pl);
		}

		private Integer mCode;
		private PermittedLevel(Integer code){
			mCode=code;
		}

		public int getCode(){
			return mCode;
		}

		public static PermittedLevel get(Integer code){
			return mLookup.get(code);
		}
	}
	
	public enum AppliedAt{
		ITEM_LEVEL(0),
		BILL_LEVEL(1),
		NOT_APPLICABLE(2);
		
		private static final Map<Integer,AppliedAt> mLookup=new HashMap<Integer,AppliedAt>();

		static {
			for(AppliedAt pl:EnumSet.allOf(AppliedAt.class))
				mLookup.put(pl.getCode(), pl);
		}

		private Integer mCode;
		private AppliedAt(Integer code){
			mCode=code;
		}

		public int getCode(){
			return mCode;
		}

		public static AppliedAt get(Integer code){
			return mLookup.get(code);
		}
	}

    private PermittedLevel mPermittedLevel;
    private AppliedAt mAppliedAt=AppliedAt.NOT_APPLICABLE;
    

//	private int mId=-1;					
//	private String mCode="";					
//	private String mName="";					
//	private String mDescription="";					
	private double mPrice=0;
	private double mVariants=0;
	private boolean mIsPercentage=false;					
	private boolean mIsItemSpecific=false;	
	private boolean mIsOverridable=true;
	private boolean mIsPromotion;
	private double mRequiredQuantity;
	private EditTypes mEditType;
	private String mDiscountPassword;
	private double discountedAmount;

	
//	@Override
//	public String getDisplayText() {
//		return mName;
//	}
//
//	public int getId() {
//		return mId;
//	}
//
//	public void setId(int id) {
//		this.mId = id;
//	}
//
//	public String getCode() {
//		return mCode;
//	}
//
//	public void setCode(String code) {
//		this.mCode = code;
//	}
//
//	public String getName() {
//		return mName;
//	}
//
//	public void setName(String name) {
//		this.mName = name;
//	}
//
//	public String getDescription() {
//		return mDescription;
//	}
//
//	public void setDescription(String description) {
//		this.mDescription = description;
//	}

	/**
	 * Item discount price
	 * @return
	 */
	public double getPrice() {
		return mPrice;
	}

	public void setPrice(double price) {
		this.mPrice = price;
		this.mVariants=0;
	}
	
	public double getVariants() {
		return mVariants;
	}

	/**
	 * The amount which is added to discount for adjusting the prices
	 * For eg. While calculating taxes there will be some variants
	 * @param variants
	 */
	public void setVariants(double variants) {
		this.mVariants = variants;
	}

	public Boolean isPercentage() {
		return mIsPercentage;
	}

	public void setPercentage(Boolean isPercentage) {
		this.mIsPercentage = isPercentage;
	}
	
	public boolean isOverridable() {
		return mIsOverridable;
	}
	
//	public boolean isAdjustmentDiscount() {
//		return mCode.equals(PosDiscountItemProvider.ADJUSTMENT_DISCOUNT_CODE);
//	}

	public void setOverridable(boolean isOverridable) {
		this.mIsOverridable =isOverridable;
	}
	
	public boolean isItemSpecific() {
		return mIsItemSpecific;
	}

	public void setItemSpecific(boolean isItemSpecific) {
		this.mIsItemSpecific =isItemSpecific;
	}
	
	public BeanDiscount clone() {
		BeanDiscount cloneObject=null;
		try {
			cloneObject=(BeanDiscount) super.clone();
		} catch (CloneNotSupportedException e) {
			PosLog.write(this,"clone",e);
		}
		return cloneObject;
	}

	/**
	 * @return Is Promotion
	 */
	public boolean isPromotion() {
		return mIsPromotion;
	}

	/**
	 * @param the Promotion to set
	 */
	public void setIsPromotion(boolean isPromotion) {
		this.mIsPromotion = isPromotion;
	}
	
	public PermittedLevel getPermittedLevel() {
		return mPermittedLevel;
	}

	public void setPermittedLevel(PermittedLevel permittedLevels) {
		this.mPermittedLevel = permittedLevels;
	}
	
	public void setPermittedLevel(int permittedLevels) {
		this.mPermittedLevel = ((!isPromotion())?
				PermittedLevel.get(permittedLevels):
					PermittedLevel.ITEM);
	}

	public AppliedAt getAppliedAt(){
		return mAppliedAt;
	}
	
	public void setAppliedAt(AppliedAt appliedAt) throws Exception{
		if(mPermittedLevel==PermittedLevel.ITEM && appliedAt==AppliedAt.BILL_LEVEL)
			throw new Exception("Discount permitted for item level can not be assigned from BILL editing");
		else if(mPermittedLevel==PermittedLevel.BILL && appliedAt==AppliedAt.ITEM_LEVEL)
			throw new Exception("Discount permmitted for bill level can not be assigned from Item editing");
		mAppliedAt=appliedAt;
	}
	
	public static BeanDiscount CLONE(BeanDiscount object){
		return (object.isPromotion())?
				((BeanItemPromotion)object).clone():
					((BeanItemDiscount)object).clone();
	}

	/**
	 * @return the Required Quantity
	 */
	public double getRequiredQuantity() {
		return mRequiredQuantity;
	}

	/**
	 * @param the Required Quantity to set
	 */
	public void setRequiredQuantity(double requiredQuantity) {
		this.mRequiredQuantity = requiredQuantity;
	}

	/**
	 * @return the Edit Type
	 */
	public EditTypes getEditType() {
		return mEditType;
	}
	

	/**
	 * @param Edit Type to set
	 */
	public void setEditType(EditTypes editType) {
		this.mEditType =  editType;
	}
	
	/**
	 * @return the mDiscountPassword
	 */
	public String getDiscountPassword() {
		return mDiscountPassword;
	}

	/**
	 * @param mDiscountPassword the mDiscountPassword to set
	 */
	public void setDiscountPassword(String mDiscountPassword) {
		this.mDiscountPassword = mDiscountPassword;
	}
//	public static String[] FIELD_TITLE_LIST={"Code","Name"};
//	public static String[] FIELD_VALUE_LIST={"getCode","getName"};
//	public static int[] FIELD_WIDTH_LIST={100};
//
//	/* (non-Javadoc)
//	 * @see com.indocosmo.pos.components.search.IPosSearchableItem#getFiledList()
//	 */
//	@Override
//	public String[] getFiledList() {
//		
//		
//		return FIELD_VALUE_LIST;
//	}
//
//	/* (non-Javadoc)
//	 * @see com.indocosmo.pos.components.search.IPosSearchableItem#getFiledTitleList()
//	 */
//	@Override
//	public String[] getFiledTitleList() {
//		return FIELD_TITLE_LIST;
//	}
//
//	/* (non-Javadoc)
//	 * @see com.indocosmo.pos.components.search.IPosSearchableItem#getFiledWidthList()
//	 */
//	@Override
//	public int[] getFiledWidthList() {
//		// TODO Auto-generated method stub
//		return FIELD_WIDTH_LIST;
//	}

	/**
	 * @return
	 */
	public double getDiscountedAmount() {
		// TODO Auto-generated method stub
		return this.discountedAmount;
	}

	/**
	 * @param discountedAmount the discountedAmount to set
	 */
	public void setDiscountedAmount(double discountedAmount) {
		this.discountedAmount = discountedAmount;
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.pos.forms.components.objectbrowser.IPosBrowsableItem#getKeyStroke()
	 */
	@Override
	public KeyStroke getKeyStroke() {
		// TODO Auto-generated method stub
		return null;
	}

//	/**
//	 * @return the showInUI
//	 */
//	public boolean isShowInUI() {
//		return showInUI;
//	}
//
//	/**
//	 * @param showInUI the showInUI to set
//	 */
//	public void setShowInUI(boolean showInUI) {
//		this.showInUI = showInUI;
//	}
	
}
