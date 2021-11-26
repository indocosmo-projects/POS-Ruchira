/**
 * 
 */
package com.indocosmo.pos.common.beans.settings.printing;

/**
 * @author sandhya
 *
 */
public class BeanPrintDayEndReportSettings {

	public final static String PS_DAY_END_REPORT_TYPE="dayend_report.report_type";
	
	private int mDayEndReportType;

	/**
	 * @return the mDayEndReportType
	 */
	public int getDayEndReportType() {
		return mDayEndReportType;
	}

	/**
	 * @param mDayEndReportType the mDayEndReportType to set
	 */
	public void setDayEndReportType(int mDayEndReportType) {
		this.mDayEndReportType = mDayEndReportType;
	}
	
	
}
