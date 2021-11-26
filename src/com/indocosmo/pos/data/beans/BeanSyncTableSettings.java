package com.indocosmo.pos.data.beans;


public final class BeanSyncTableSettings implements Cloneable {

	private int id;
	private int syncOrder;
	private int tableId;
	private String tableName;
	private int parentTableId;
	private String tableCriteria;
	private String orderBy;
	private String columnToExclude;
	private String webParamValue;
	private String remarks;

	//To hold the list of tables for SQL.
	//(Used for processing logic and
	//hence not present in data table).
	private String tableList;
	
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the syncOrder
	 */
	public int getSyncOrder() {
		return syncOrder;
	}

	/**
	 * @param syncOrder the syncOrder to set
	 */
	public void setSyncOrder(int syncOrder) {
		this.syncOrder = syncOrder;
	}

	/**
	 * @return the tableId
	 */
	public int getTableId() {
		return tableId;
	}

	/**
	 * @param tableId the tableId to set
	 */
	public void setTableId(int tableId) {
		this.tableId = tableId;
	}

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
	 * @return the parentTableId
	 */
	public int getParentTableId() {
		return parentTableId;
	}

	/**
	 * @param parentTableId the parentTableId to set
	 */
	public void setParentTableId(int parentTableId) {
		this.parentTableId = parentTableId;
	}

	/**
	 * @return the tableCriteria
	 */
	public String getTableCriteria() {
		return tableCriteria;
	}

	/**
	 * @param tableCriteria the tableCriteria to set
	 */
	public void setTableCriteria(String tableCriteria) {
		this.tableCriteria = tableCriteria;
	}

	/**
	 * @return the orderBy
	 */
	public String getOrderBy() {
		return orderBy;
	}

	/**
	 * @param orderBy the orderBy to set
	 */
	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	/**
	 * @return the columnToExclude
	 */
	public String getColumnToExclude() {
		return columnToExclude;
	}

	/**
	 * @param columnToExclude the columnToExclude to set
	 */
	public void setColumnToExclude(String columnToExclude) {
		this.columnToExclude = columnToExclude;
	}

	/**
	 * @return the webParamValue
	 */
	public String getWebParamValue() {
		return webParamValue;
	}

	/**
	 * @param webParamValue the webParamValue to set
	 */
	public void setWebParamValue(String webParamValue) {
		this.webParamValue = webParamValue;
	}

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

	/**
	 * @return the tableList
	 */
	public String getTableList() {
		return tableList;
	}

	/**
	 * @param tableList the tableList to set
	 */
	public void setTableList(String tableList) {
		this.tableList = tableList;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	@Override
	public BeanSyncTableSettings clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return (BeanSyncTableSettings) super.clone();
	}
}