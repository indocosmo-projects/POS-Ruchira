/**
 * 
 */
package com.indocosmo.pos.data.beans;

import javax.swing.KeyStroke;

import com.indocosmo.pos.data.providers.shopdb.PosEmployeeProvider.EmployeeStatus;


/**
 * @author Ramesh S.
 * @since 30th July 2012
 */
public  class BeanEmployees extends BeanMasterBase{

	private String mFirstName;
	private String mMiddleName;
	private String mLastName;
	private String mCardNumber;
	private EmployeeStatus mStatus;

	/**
	 * @return the f_name
	 */
	public String getFirstName() {
		return mFirstName;
	}
	/**
	 * @param f_name the f_name to set
	 */
	public void setFirstName(String f_name) {
		this.mFirstName = f_name;
	}
	/**
	 * @return the m_name
	 */
	public String getMiddleName() {
		return mMiddleName;
	}
	/**
	 * @param m_name the m_name to set
	 */
	public void setMiddleName(String m_name) {
		this.mMiddleName = m_name;
	}
	/**
	 * @return the l_name
	 */
	public String getLastName() {
		return mLastName;
	}
	/**
	 * @param l_name the l_name to set
	 */
	public void setLastName(String l_name) {
		this.mLastName = l_name;
	}
	/**
	 * return the full name of the employee
	 * @return
	 */
	
	/* (non-Javadoc)
		 * @see com.indocosmo.pos.dataobjects.PosMasterBaseObject#getName()
		 */
	@Override
	public String getName() {
		 
		return ((mFirstName!=null)?mFirstName:mFirstName) +" "+ 
				((mMiddleName!=null)?mMiddleName:"")+" "+
					((mLastName!=null)?mLastName:"");
	}
	
	/**
	 * @return the mCardNumber
	 */
	public String getCardNumber() {
		return mCardNumber;
	}
	/**
	 * @param mCardNumber the mCardNumber to set
	 */
	public void setCardNumber(String cardNumber) {
		this.mCardNumber = cardNumber;
	}
	/**
	 * @return the mStatus
	 */
	public EmployeeStatus getStatus() {
		return mStatus;
	}
	/**
	 * @param mStatus the mStatus to set
	 */
	public void setStatus(EmployeeStatus mStatus) {
		this.mStatus = mStatus;
	}
	
	public static String[] SERACH_FILED_TITLE={"Code","Name","Card Number"};
	public static String[] SERACH_FILED_VALUE={"getCode", "getName","getCardNumber"};
	public static int[] SERACH_FILED_WIDTH={100,250};
	/* (non-Javadoc)
	 * @see com.indocosmo.pos.components.search.IPosSearchableItem#getFiledList()
	 */
	@Override
	public String[] getFieldList() {
		return SERACH_FILED_VALUE;
	}
	/* (non-Javadoc)
	 * @see com.indocosmo.pos.components.search.IPosSearchableItem#getFiledTitleList()
	 */
	@Override
	public String[] getFieldTitleList() {
		return SERACH_FILED_TITLE;
	}
	/* (non-Javadoc)
	 * @see com.indocosmo.pos.components.search.IPosSearchableItem#getFiledWidthList()
	 */
	@Override
	public int[] getFieldWidthList() {
		return SERACH_FILED_WIDTH;
	}
	/* (non-Javadoc)
	 * @see com.indocosmo.pos.forms.components.objectbrowser.IPosBrowsableItem#getKeyStroke()
	 */
	@Override
	public KeyStroke getKeyStroke() {
		// TODO Auto-generated method stub
		return null;
	}

}
