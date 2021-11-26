/**
 * 
 */
package com.indocosmo.pos.data.beans;


/**
 * @author Ramesh S.
 * @since 18th July 2012
 */
public class BeanUser extends BeanEmployees {

	private int mUserGroupId;
	private Integer mEmployeeId=null;
	private String mPassword;
	private String mEmail;
	private String mLastloginDate;
	private String mValidFrom;
	private String mValidTo;
	private int mIsActive;
	private boolean mIsOpenTill;

	/**
	 * @return the user_group_id
	 */
	public int getUserGroupId() {
		return mUserGroupId;
	}

	/**
	 * @param user_group_id the user_group_id to set
	 */
	public void setUserGroupId(int userGroupId) {
		this.mUserGroupId = userGroupId;
	}

	/**
	 * @return the employee_id
	 */
	public Integer getEmployeeId() {
		return mEmployeeId;
	}

	/**
	 * @param employee_id the employee_id to set
	 */
	public void setEmployeeId(Integer employeeId) {
		this.mEmployeeId = employeeId;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return mPassword;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.mPassword = password;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return mEmail;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.mEmail = email;
	}

	/**
	 * @return the lastlogin_date
	 */
	public String getLastloginDate() {
		return mLastloginDate;
	}

	/**
	 * @param lastlogin_date the lastlogin_date to set
	 */
	public void setLastloginDate(String lastloginDate) {
		this.mLastloginDate = lastloginDate;
	}

	/**
	 * @return the valid_from
	 */
	public String getValidFrom() {
		return mValidFrom;
	}

	/**
	 * @param valid_from the valid_from to set
	 */
	public void setValidFrom(String validFrom) {
		this.mValidFrom = validFrom;
	}

	/**
	 * @return the valid_to
	 */
	public String getValidTo() {
		return mValidTo;
	}

	/**
	 * @param valid_to the valid_to to set
	 */
	public void setValidTo(String validTo) {
		this.mValidTo = validTo;
	}

	/**
	 * @return the is_active
	 */
	public int isActive() {
		return mIsActive;
	}

	/**
	 * @param is_active the is_active to set
	 */
	public void setActive(int isActive) {
		this.mIsActive = isActive;
	}
	
	public boolean isEmployee(){
		return (mEmployeeId!=null);
	}

	/**
	 * @return the mIsOpenTill
	 */
	public boolean IsOpenTill() {
		return mIsOpenTill;
	}

	/**
	 * @param mIsOpenTill the mIsOpenTill to set
	 */
	public void setIsOpenTill(boolean mIsOpenTill) {
		this.mIsOpenTill = mIsOpenTill;
	}

	/**
	 * @return the created_by
	 */

	@Override
	public String getName() {
		return mName;
	}
	

}
