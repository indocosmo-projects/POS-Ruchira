/**
 * @author jojesh
 *Abstract class for ItemSuperClass and ItemSubClass.
 *Define the common properties and methods here. 
 *Add functionalities which are common to both superclass and subclass here.  
 */

package com.indocosmo.pos.data.beans;

import java.awt.image.BufferedImage;
import java.util.Comparator;

public abstract class BeanItemClassBase implements Comparator<BeanItemClassBase> {
	
	protected int mID;
	protected String mClassName;
	protected String mAltClassName;
	protected String mClassCode;
	protected int mClassDisplayOrder;
	protected String mFgColor;
	protected String mBgColor;
	protected BufferedImage mClassImage;
	protected String mHSNCode;
	protected int mSuperClassID;
	protected String mSuperClassCode;
	
	
	public BeanItemClassBase() {
		// TODO Auto-generated constructor stub
	}
	
	public final int getID() {
		return mID;
	}

	public final void setID(int id) {
		this.mID = id;
	}
	
	public final String getAlternativeName() {
		return mAltClassName;
	}
	
	public final void setAlternativeName(String altClassName) {
		mAltClassName=altClassName;
	}
	
	public final String getName() {
		return mClassName;
	}

	public final void setName(String mClassName) {
		this.mClassName = mClassName;
	}

	public final String getCode() {
		return mClassCode;
	}

	public final void setCode(String mClassCode) {
		this.mClassCode = mClassCode;
	}

	public final int getClassDisplayOrder() {
		return mClassDisplayOrder;
	}

	public final void setClassDisplayOrder(int mClassDisplayOrder) {
		this.mClassDisplayOrder = mClassDisplayOrder;
	}

	/**
	 * @return the mFgColor
	 */
	public String getFgColor() {
		return mFgColor;
	}

	/**
	 * @param mFgColor the mFgColor to set
	 */
	public void setFgColor(String mFgColor) {
		this.mFgColor = mFgColor;
	}

	/**
	 * @return the mBgColor
	 */
	public String getBgColor() {
		return mBgColor;
	}

	/**
	 * @param mBgColor the mBgColor to set
	 */
	public void setBgColor(String mBgColor) {
		this.mBgColor = mBgColor;
	}
	public void setImage(BufferedImage image){
		mClassImage=image;
	}
	public BufferedImage getImage(){
		return this.mClassImage;
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
	 * @return the mSuperClassID
	 */
	public final int getSuperClassID() {
		return mSuperClassID;
	}

	/**
	 * @param mSuperClassID the mSuperClassID to set
	 */
	public final  void setSuperClassID(int mSuperClassID) {
		this.mSuperClassID = mSuperClassID;
	}

	
	/**
	 * @return the mSuperClassCode
	 */
	public final  String getSuperClassCode() {
		return mSuperClassCode;
	}

	/**
	 * @param mSuperClassCode the mSuperClassCode to set
	 */
	public final  void setSuperClassCode(String mSuperClassCode) {
		this.mSuperClassCode = mSuperClassCode;
	}

	/* (non-Javadoc)
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	@Override
	public int compare(BeanItemClassBase o1, BeanItemClassBase o2) {
		if(o1.mClassDisplayOrder>=o2.mClassDisplayOrder)
			return 1;
		else 
			return -1;
	}
		

}
