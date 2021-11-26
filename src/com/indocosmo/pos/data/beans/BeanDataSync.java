/**
 * 
 */
package com.indocosmo.pos.data.beans;

/**
 * @author LAP-L530
 *
 */
public class BeanDataSync {
	String tableName;
	String description;
	String remarks;
	/**
	 * @return the remarks
	 */
	public String getRemarks() {
		return remarks;
	}
	/**
	 * @param remarks the remarks to set
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	int errorRecords;
	int pendingRecords;
	
	/**
	 * @return the tableName
	 */
	public String getTableName() {
		return tableName;
	}
	/**
	 * @param tableName the tableName to set
	 */
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @return the errorRecords
	 */
	public int getErrorRecords() {
		return errorRecords;
	}
	/**
	 * @param errorRecords the errorRecords to set
	 */
	public void setErrorRecords(int errorRecords) {
		this.errorRecords = errorRecords;
	}
	/**
	 * @return the pendingRecords
	 */
	public int getPendingRecords() {
		return pendingRecords;
	}
	/**
	 * @param pendingRecords the pendingRecords to set
	 */
	public void setPendingRecords(int pendingRecords) {
		this.pendingRecords = pendingRecords;
	}
	

}
