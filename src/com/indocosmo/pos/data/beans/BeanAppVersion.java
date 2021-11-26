/**
 * 
 */
package com.indocosmo.pos.data.beans;

 
public final class BeanAppVersion extends BeanMasterBase{
	
	private int mPatch;
	private int mMinor;
	private int mMajor;
	private String mBuildNo;
	private String mBuildDate;
	/**
	 * @return the version
	 */
	public int getPatch() {
		return mPatch;
	}
	/**
	 * @param version the version to set
	 */
	public void setPatch(int version) {
		this.mPatch = version;
	}
	/**
	 * @return the minor
	 */
	public int getMinor() {
		return mMinor;
	}
	/**
	 * @param minor the minor to set
	 */
	public void setMinor(int minor) {
		this.mMinor = minor;
	}
	/**
	 * @return the major
	 */
	public int getMajor() {
		return mMajor;
	}
	/**
	 * @param major the major to set
	 */
	public void setMajor(int major) {
		this.mMajor = major;
	}
	/**
	 * @return the buildNo
	 */
	public String getBuildNo() {
		return mBuildNo;
	}
	/**
	 * @param buildNo the buildNo to set
	 */
	public void setBuildNo(String buildNo) {
		this.mBuildNo = buildNo;
	}
	/**
	 * @return the buildDate
	 */
	public String getBuildDate() {
		return mBuildDate;
	}
	/**
	 * @param buildDate the buildDate to set
	 */
	public void setBuildDate(String buildDate) {
		this.mBuildDate = buildDate;
	} 
	
}
