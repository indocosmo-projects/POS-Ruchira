/**
 * 
 */
package com.indocosmo.pos.data.beans;

 
public final class BeanAbout extends BeanMasterBase{
	
 
	private String mContents;
	private String mCopyright;
	/**
	 * @return the contents
	 */
	public String getContents() {
		return mContents;
	}
	/**
	 * @param contents the contents to set
	 */
	public void setContents(String contents) {
		this.mContents = contents;
	}
	/**
	 * @return the copyright
	 */
	public String getCopyright() {
		return mCopyright;
	}
	/**
	 * @param copyright the copyright to set
	 */
	public void setCopyright(String copyright) {
		this.mCopyright = copyright;
	}
	 	
}
