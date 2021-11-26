package com.indocosmo.pos.data.beans;


public class BeanSaleMenuItem extends BeanMasterBase{
	
	private String mColorCode;
	private boolean mIsDeleted;
	private boolean mIsActive;
	private boolean mIsDefaultMenu;
	private boolean mEnableH1Button;
	private boolean mEnableH2Button;
	private boolean mEnableH3Button;
	
	/**
	 * @return the mIsDeleted
	 */
	public boolean isDeleted() {
		return mIsDeleted;
	}
	/**
	 * @param mIsDeleted the mIsDeleted to set
	 */
	public void setDeleted(boolean mIsDeleted) {
		this.mIsDeleted = mIsDeleted;
	}
	/**
	 * @return the mIsActive
	 */
	public boolean isActive() {
		return mIsActive;
	}
	/**
	 * @param mIsActive the mIsActive to set
	 */
	public void setActive(boolean mIsActive) {
		this.mIsActive = mIsActive;
	}
	/**
	 * @return the mIsDefaultMenu
	 */
	public boolean isDefaultMenu() {
		return mIsDefaultMenu;
	}
	/**
	 * @param mIsDefaultMenu the mIsDefaultMenu to set
	 */
	public void setDefaultMenu(boolean mIsDefaultMenu) {
		this.mIsDefaultMenu = mIsDefaultMenu;
	}
	/**
	 * @return the mEnableH1Button
	 */
	public boolean isEnableH1Button() {
		return mEnableH1Button;
	}
	/**
	 * @param mEnableH1Button the mEnableH1Button to set
	 */
	public void setEnableH1Button(boolean mEnableH1Button) {
		this.mEnableH1Button = mEnableH1Button;
	}
	/**
	 * @return the mEnableH2Button
	 */
	public boolean isEnableH2Button() {
		return mEnableH2Button;
	}
	/**
	 * @param mEnableH2Button the mEnableH2Button to set
	 */
	public void setEnableH2Button(boolean mEnableH2Button) {
		this.mEnableH2Button = mEnableH2Button;
	}
	/**
	 * @return the mEnableH3Button
	 */
	public boolean isEnableH3Button() {
		return mEnableH3Button;
	}
	/**
	 * @param mEnableH3Button the mEnableH3Button to set
	 */
	public void setEnableH3Button(boolean mEnableH3Button) {
		this.mEnableH3Button = mEnableH3Button;
	}
	/**
	 * @return the mColorCode
	 */
	public String getColorCode() {
		return mColorCode;
	}
	/**
	 * @param mColorCode the mColorCode to set
	 */
	public void setColorCode(String mColorCode) {
		this.mColorCode = mColorCode;
	}
	
	

}
