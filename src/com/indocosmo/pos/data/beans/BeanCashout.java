/**
 * 
 */
package com.indocosmo.pos.data.beans;

import javax.swing.KeyStroke;

import com.indocosmo.pos.common.utilities.PosCurrencyUtil;
import com.indocosmo.pos.common.utilities.PosDateUtil;
import com.indocosmo.pos.common.utilities.PosUomUtil;
import com.indocosmo.pos.data.beans.terminal.BeanTerminalInfo;
import com.indocosmo.pos.data.providers.shopdb.PosUOMProvider;
import com.indocosmo.pos.forms.components.search.IPosSearchableItem;

/**
 * @author jojesh
 *
 */
public class BeanCashout implements IPosSearchableItem{
	
	private int id;
	private String posDate;
	private BeanUser user;
	private int shiftId;
	private double amount;
	private String title;
	private String remarks;
	private BeanTerminalInfo station;
	private String cashOutTime;
	private int createdBy;
//	private String createdTime;
	private int updatedBy;
//	private String updatedAt;
//	
	/**
	 * @return the posDate
	 */
	public String getPosDate() {
		return posDate;
	}
	/**
	 * @param posDate the posDate to set
	 */
	public void setCashOutDate(String posDate) {
		this.posDate = posDate;
	}
	/**
	 * @return the user
	 */
	public BeanUser getUser() {
		return user;
	}
	/**
	 * @param user the user to set
	 */
	public void setUser(BeanUser user) {
		this.user = user;
	}
	/**
	 * @return the amount
	 */
	public double getAmount() {
		return amount;
	}
	/**
	 * @param amount the amount to set
	 */
	public void setAmount(double amount) {
		this.amount = amount;
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
	 * @param string
	 */
	public void setStation(BeanTerminalInfo station) {
		
		this.station=station;
	}
	/**
	 * @return the stationCode
	 */
	public BeanTerminalInfo getStation() {
		return station;
	}
	/**
	 * @param string
	 */
	public void setCashOutTime(String time) {

		this.cashOutTime=time;
	}
	/**
	 * @return the cashOutTime
	 */
	public String getCashOutTime() {
		
		return cashOutTime;
	}
	
	/**
	 * @return the cashOutTime
	 */
	public String getCashOutDisplayTime() {
		
//		SimpleDateFormat sd =new SimpleDateFormat("yyyy-MM-dd HH:mm");
//		return sd.format(PosDateUtil.parse("yyyy-MM-dd HH:mm:ss", cashOutTime));
		return PosDateUtil.formatShortDateTime(cashOutTime);
	}
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
	 * @return
	 */
	public String getUserName(){
		
		return user.getName();
	}
	
	
	/**
	 * @return
	 */
	public String getStationName(){
		
		return station.getName();
	}
	
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * 
	 */
	public static String[] SEARCH_FIELD_LIST = {"getCashOutDisplayTime","getUserName",	"getAmount","getTitle","getRemarks"};

	/**
	 * 
	 */
	public static String[] SEARCH_COLUMN_NAMES = {"Date Time","User", "Amount","Title","Remarks"};

	/**
	 * 
	 */
	public static int[] SEARCH_FIELD_WIDTH = { 175,125,100,120,170};
	
	/* (non-Javadoc)
	 * @see com.indocosmo.pos.forms.components.objectbrowser.IPosBrowsableItem#getItemCode()
	 */
	@Override
	public Object getItemCode() {

		return getAmount();
	}
	/* (non-Javadoc)
	 * @see com.indocosmo.pos.forms.components.objectbrowser.IPosBrowsableItem#getDisplayText()
	 */
	@Override
	public String getDisplayText() {

		return String.valueOf(getAmount());
	}
	/* (non-Javadoc)
	 * @see com.indocosmo.pos.forms.components.objectbrowser.IPosBrowsableItem#isVisibleInUI()
	 */
	@Override
	public boolean isVisibleInUI() {

		return true;
	}
	/* (non-Javadoc)
	 * @see com.indocosmo.pos.forms.components.search.IPosSearchableItem#getFiledList()
	 */
	@Override
	public String[] getFieldList() {

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
	 * @return the createdBy
	 */
	public int getCreatedBy() {
		return createdBy;
	}
	/**
	 * @param createdBy the createdBy to set
	 */
	public void setCreatedBy(int createdBy) {
		this.createdBy = createdBy;
	}
	/**
	 * @return the updatedBy
	 */
	public int getUpdatedBy() {
		return updatedBy;
	}
	/**
	 * @param updatedBy the updatedBy to set
	 */
	public void setUpdatedBy(int updatedBy) {
		this.updatedBy = updatedBy;
	}
	/**
	 * @return the shiftId
	 */
	public int getShiftId() {
		return shiftId;
	}
	/**
	 * @param shiftId the shiftId to set
	 */
	public void setShiftId(int shiftId) {
		this.shiftId = shiftId;
	}
	/* (non-Javadoc)
	 * @see com.indocosmo.pos.forms.components.search.IPosSearchableItem#getFiledFormatList()
	 */
	@Override
	public String[] getFieldFormatList() {
		// TODO Auto-generated method stub
		return  null;
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
