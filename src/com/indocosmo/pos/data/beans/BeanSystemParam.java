/**
 * 
 */
package com.indocosmo.pos.data.beans;

import com.indocosmo.pos.common.enums.DateFormat;
import com.indocosmo.pos.common.enums.TimeFormat;
import com.indocosmo.pos.common.utilities.PosNumberUtil;

/**
 * @author jojesh
 *
 */
public class BeanSystemParam {
	
	private int id;
	private DateFormat dateFormat;
	private String dateSeparator;
	private TimeFormat timeFormat;
	private String timeZone;
	private int decimalPlaces;
	private BeanCustomerType defaultCustomerType;
	
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
	 * @return the dateFormat
	 */
	public DateFormat getDateFormat() {
		return dateFormat;
	}
	/**
	 * @param dateFormat the dateFormat to set
	 */
	public void setDateFormat(String dateFormat) {
		this.dateFormat = DateFormat.get(PosNumberUtil.parseIntegerSafely(dateFormat)) ;
	}
	/**
	 * @return the dateSeparator
	 */
	public String getDateSeparator() {
		return dateSeparator;
	}
	/**
	 * @param dateSeparator the dateSeparator to set
	 */
	public void setDateSeparator(String dateSeparator) {
		this.dateSeparator = dateSeparator;
	}
	/**
	 * @return the timeFormat
	 */
	public TimeFormat getTimeFormat() {
		return timeFormat;
	}
	/**
	 * @param timeFormat the timeFormat to set
	 */
	public void setTimeFormat(String timeFormat) {
		
		
		this.timeFormat = TimeFormat.get(PosNumberUtil.parseIntegerSafely(timeFormat)) ;
	}
	/**
	 * @return the timeZone
	 */
	public String getTimeZone() {
		return timeZone;
	}
	/**
	 * @param timeZone the timeZone to set
	 */
	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}
	/**
	 * @return the decimalPlaces
	 */
	public int getDecimalPlaces() {
		return decimalPlaces;
	}
	/**
	 * @param decimalPlaces the decimalPlaces to set
	 */
	public void setDecimalPlaces(int decimalPlaces) {
		this.decimalPlaces = decimalPlaces;
	}
	/**
	 * @return the defaultCustomerType
	 */
	public BeanCustomerType getDefaultCustomerType() {
		return defaultCustomerType;
	}
	/**
	 * @param defaultCustomerType the defaultCustomerType to set
	 */
	public void setDefaultCustomerType(BeanCustomerType defaultCustomerType) {
		this.defaultCustomerType = defaultCustomerType;
	}

}
