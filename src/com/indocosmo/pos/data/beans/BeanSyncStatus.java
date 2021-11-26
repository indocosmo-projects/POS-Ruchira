package com.indocosmo.pos.data.beans;


public final class BeanSyncStatus {

	private int row_id;
	private String file_name;
	private boolean sync_status;
	private String sync_date;
	/**
	 * @return the file_name
	 */
	public String getFile_name() {
		return file_name;
	}
	/**
	 * @param file_name the file_name to set
	 */
	public void setFile_name(String file_name) {
		this.file_name = file_name;
	}
	/**
	 * @return the sync_status
	 */
	public boolean isSync_status() {
		return sync_status;
	}
	/**
	 * @param sync_status the sync_status to set
	 */
	public void setSync_status(boolean sync_status) {
		this.sync_status = sync_status;
	}
	/**
	 * @return the row_id
	 */
	public int getRow_id() {
		return row_id;
	}
	/**
	 * @return the sync_date
	 */
	public String getSync_date() {
		return sync_date;
	}

	
}
