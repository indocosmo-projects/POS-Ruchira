/**
 * 
 */
package com.indocosmo.pos.data.beans;

import javax.swing.KeyStroke;

import com.indocosmo.pos.common.utilities.PosDateUtil;
import com.indocosmo.pos.forms.components.search.IPosSearchableItem;

/**
 * @author jojesh-13.2
 *
 */
public class BeanLoginSessions implements IPosSearchableItem{
	
	private int id;
	private int posId;
	private int cashierShiftId;
	private int loginUserId;
	private String startAt;
	private String endAt;
	private String userName;
	private String cardNo;
	private String stationName;
	private String stationCode;
	private int openingTillId;
	private boolean isSelected=true;

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
	 * @return the pos_id
	 */
	public int getPosId() {
		return posId;
	}
	/**
	 * @param pos_id the pos_id to set
	 */
	public void setPosId(int posId) {
		this.posId = posId;
	}
	/**
	 * @return the cashier_shift_id
	 */
	public int getCashierShiftId() {
		return cashierShiftId;
	}
	/**
	 * @param cashier_shift_id the cashier_shift_id to set
	 */
	public void setCashierShiftId(int cashierShiftId) {
		this.cashierShiftId = cashierShiftId;
	}
	/**
	 * @return the login_user_id
	 */
	public int getLoginUserId() {
		return loginUserId;
	}
	/**
	 * @param login_user_id the login_user_id to set
	 */
	public void setLoginUserId(int loginUserId) {
		this.loginUserId = loginUserId;
	}
	/**
	 * @return the start_at
	 */
	public String getStartAt() {
		return PosDateUtil.format(startAt);
	}
	/**
	 * @param start_at the start_at to set
	 */
	public void setStartAt(String startAt) {
		this.startAt = startAt;
	}
	/**
	 * @return the end_at
	 */
	public String getEndAt() {
		return PosDateUtil.format(endAt);
	}
	/**
	 * @param end_at the end_at to set
	 */
	public void setEndAt(String endAt) {
		this.endAt = endAt;
	}
	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}
	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	/**
	 * @return the cardNo
	 */
	public String getCardNo() {
		return cardNo;
	}
	/**
	 * @param cardNo the cardNo to set
	 */
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	/**
	 * @return the stationName
	 */
	public String getStationName() {
		return stationName;
	}
	/**
	 * @param stationName the stationName to set
	 */
	public void setStationName(String stationName) {
		this.stationName = stationName;
	}
	/**
	 * @return the stationCode
	 */
	public String getStationCode() {
		return stationCode;
	}
	/**
	 * @param stationCode the stationCode to set
	 */
	public void setStationCode(String stationCode) {
		this.stationCode = stationCode;
	}
	/* (non-Javadoc)
	 * @see com.indocosmo.pos.forms.components.objectbrowser.IPosBrowsableItem#getItemCode()
	 */
	@Override
	public Object getItemCode() {

		return getId();
	}
	/* (non-Javadoc)
	 * @see com.indocosmo.pos.forms.components.objectbrowser.IPosBrowsableItem#getDisplayText()
	 */
	@Override
	public String getDisplayText() {

		return getUserName();
	}
	/* (non-Javadoc)
	 * @see com.indocosmo.pos.forms.components.objectbrowser.IPosBrowsableItem#isVisibleInUI()
	 */
	@Override
	public boolean isVisibleInUI() {
		// TODO Auto-generated method stub
		return true;
	}
	
	public static String[] SEARCH_FIELD_LIST = {"getCardNo",
		"getUserName", "getStationName","getStartAt"};

	public static String[]SEARCH_COLUMN_NAMES =  {"Card No",
		"User Name", "Station Name","Started At"};

	public static int[] SEARCH_FIELD_WIDTH = { 110,195,130};
	
	/* (non-Javadoc)
	 * @see com.indocosmo.pos.forms.components.search.IPosSearchableItem#getFiledList()
	 */
	@Override
	public String[] getFieldList() {
		// TODO Auto-generated method stub
		return SEARCH_FIELD_LIST;
	}	
	
	/* (non-Javadoc)
	 * @see com.indocosmo.pos.forms.components.search.IPosSearchableItem#getFiledTitleList()
	 */
	@Override
	public String[] getFieldTitleList() {
		
		return SEARCH_COLUMN_NAMES;
	}
	/* (non-Javadoc)
	 * @see com.indocosmo.pos.forms.components.search.IPosSearchableItem#getFiledWidthList()
	 */
	@Override
	public int[] getFieldWidthList() {
		
		return SEARCH_FIELD_WIDTH;
	}
	/**
	 * @param int1
	 */
	public void setOpeningTillId(int tillId) {
		
		this.openingTillId=tillId;
		
	}
	/**
	 * @return the openingTillId
	 */
	public int getOpeningTillId() {
		return openingTillId;
	}
	
	/**
	 * @return the isSelected
	 */
	public boolean isSelected() {
		return isSelected;
	}

	/**

	 * @param isSelected the isSelected to set
	 */
	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}
	/* (non-Javadoc)
	 * @see com.indocosmo.pos.forms.components.search.IPosSearchableItem#getFiledFormatList()
	 */
	@Override
	public String[] getFieldFormatList() {
		// TODO Auto-generated method stub
		return null;
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
