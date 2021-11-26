/**
 * 
 */
package com.indocosmo.pos.data.beans;

/**
 * @author Deepak
 *
 */
public final class BeanAccessLog {

	private String mFunctionName;
	private String mUserName;
	private String mAccessTime;
	/**
	 * @return the mFunctionName
	 */
	public String getFunctionName() {
		return mFunctionName;
	}
	/**
	 * @param mFunctionName the mFunctionName to set
	 */
	public void setFunctionName(String mFunctionName) {
		this.mFunctionName = mFunctionName;
	}
	/**
	 * @return the mUserName
	 */
	public String getUserName() {
		return mUserName;
	}
	/**
	 * @param mUserName the mUserName to set
	 */
	public void setUserName(String mUserName) {
		this.mUserName = mUserName;
	}
	/**
	 * @return the mAccessTime
	 */
	public String getAccessTime() {
		return mAccessTime;
	}
	/**
	 * @param mAccessTime the mAccessTime to set
	 */
	public void setAccessTime(String mAccessTime) {
		this.mAccessTime = mAccessTime;
	}
	
}
