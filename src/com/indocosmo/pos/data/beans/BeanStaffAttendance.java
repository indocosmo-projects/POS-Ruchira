/**
 * 
 */
package com.indocosmo.pos.data.beans;

/**
 * @author Ramesh S.
 *@since 18th July 2012
 */
public final class BeanStaffAttendance {

	private String id;
	private int employee_id;
	private int shift_no;
	private int shift_id;
	private String shift_start_date;			
	private String shift_start_time;			
	private String shift_end_date;			
	private String shift_end_time;
	private int is_processed;
	private int sync_status;
	private String sync_message;

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the employee_id
	 */
	public int getEmployee_id() {
		return employee_id;
	}
	/**
	 * @param employee_id the employee_id to set
	 */
	public void setEmployee_id(int employee_id) {
		this.employee_id = employee_id;
	}
	/**
	 * @return the shift_no
	 */
	public int getShift_no() {
		return shift_no;
	}
	/**
	 * @param shift_no the shift_no to set
	 */
	public void setShift_no(int shift_no) {
		this.shift_no = shift_no;
	}
	/**
	 * @return the shift_id
	 */
	public int getShift_id() {
		return shift_id;
	}
	/**
	 * @param shift_id the shift_id to set
	 */
	public void setShift_id(int shift_id) {
		this.shift_id = shift_id;
	}
	/**
	 * @return the shift_start_date
	 */
	public String getShift_start_date() {
		return shift_start_date;
	}
	/**
	 * @param shift_start_date the shift_start_date to set
	 */
	public void setShift_start_date(String shift_start_date) {
		this.shift_start_date = shift_start_date;
	}
	/**
	 * @return the shift_start_time
	 */
	public String getShift_start_time() {
		return shift_start_time;
	}
	/**
	 * @param shift_start_time the shift_start_time to set
	 */
	public void setShift_start_time(String shift_start_time) {
		this.shift_start_time = shift_start_time;
	}
	/**
	 * @return the shift_end_date
	 */
	public String getShift_end_date() {
		return shift_end_date;
	}
	/**
	 * @param shift_end_date the shift_end_date to set
	 */
	public void setShift_end_date(String shift_end_date) {
		this.shift_end_date = shift_end_date;
	}
	/**
	 * @return the shift_end_time
	 */
	public String getShift_end_time() {
		return shift_end_time;
	}
	/**
	 * @param shift_end_time the shift_end_time to set
	 */
	public void setShift_end_time(String shift_end_time) {
		this.shift_end_time = shift_end_time;
	}
	/**
	 * @return the is_processed
	 */
	public int getIs_processed() {
		return is_processed;
	}
	/**
	 * @param is_processed the is_processed to set
	 */
	public void setIs_processed(int is_processed) {
		this.is_processed = is_processed;
	}
	/**
	 * @return the sync_status
	 */
	public int getSync_status() {
		return sync_status;
	}
	/**
	 * @param sync_status the sync_status to set
	 */
	public void setSync_status(int sync_status) {
		this.sync_status = sync_status;
	}
	/**
	 * @return the sync_message
	 */
	public String getSync_message() {
		return sync_message;
	}
	/**
	 * @param sync_message the sync_message to set
	 */
	public void setSync_message(String sync_message) {
		this.sync_message = sync_message;
	}

	
}