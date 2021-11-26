/**
 * 
 */
package com.indocosmo.pos.data.beans;

/**
 * @author jojesh-13.2
 *
 */
public class BeanTableOperation {

	private boolean isDeleted=false;
	private boolean isAdded=false;
	private boolean isExisting=false;
	private boolean isDeletedonOnReOpen=false;

	/**
	 * @return
	 */
	public boolean isVoid() {
		
		return isDeleted;
	}
	
	/**
	 * @param isDeleted
	 */
	public void setVoid(boolean isDeleted) {
		
		this.isDeleted = isDeleted;
	}
	
	/**
	 * @return
	 */
	public boolean isAdded() {
		
		return isAdded;
	}
	
	/**
	 * @param isAdded
	 */
	public void setAdded(boolean isAdded) {
		
		this.isAdded = isAdded;
	}
	
	/**
	 * @return
	 */
	public boolean isExisting() {
		
		return isExisting;
	}
	/**
	 * @param isExisting
	 */
	public void setExisting(boolean isExisting) {
		
		this.isExisting = isExisting;
	}
	
	/**
	 * @param flag
	 */
	public void setDeletedonOnReOpen(boolean flag) {
		
		this.isDeletedonOnReOpen=flag;
	}

	/**
	 * @return
	 */
	public boolean isDeletedonOnReOpen() {
		
		return this.isDeletedonOnReOpen;
	}	

}
