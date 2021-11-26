/**
 * 
 */
package com.indocosmo.pos.common.beans.settings.ui.mainmenu;

/**
 * @author anand
 *
 */
public class BeanUIMenuListPanelSettings {

	public static final String SHOW_SYNC_BUTTON="main_menu.menu_list_panel.show_sync_button";
	public static final String SHOW_CONTACTUS_BUTTON="main_menu.menu_list_panel.show_contactus_button";
	public static final String SHOW_VIEW_OPENSESSIONS_BUTTON="main_menu.menu_list_panel.show_view_opensession_button";
	
	public static final String SHOW_DAYSTART_BUTTON="main_menu.menu_list_panel.show_daystart_button";
	public static final String SHOW_DAYEND_BUTTON="main_menu.menu_list_panel.show_dayend_button";
	public static final String SHOW_TILL_BUTTON="main_menu.menu_list_panel.show_till_button";
	public static final String SHOW_ATTENDANCE_BUTTON="main_menu.menu_list_panel.show_attendance_button";
	public static final String SHOW_SUMMARY_BUTTON="main_menu.menu_list_panel.show_summary_button"; 
	public static final String SHOW_ORDERENTRY_BUTTON="main_menu.menu_slist_panel.show_orderentry_button";
	public static final String SHOW_SHIFTREPORTS_BUTTON="main_menu.menu_list_panel.show_shiftreports_button";
	public static final String SHOW_ORDERDETAILS_BUTTON="main_menu.menu_list_panel.show_orderdetails_button";
	public static final String SHOW_TERMINAL_BUTTON="main_menu.menu_list_panel.show_terminal_button";
	public static final String SHOW_MAILS_BUTTON="main_menu.menu_list_panel.show_mails_button";
	public static final String SHOW_ORDERREFUND_BUTTON="main_menu.menu_list_panel.show_orderrefund_button";
	public static final String SHOW_CASHOUT_BUTTON="main_menu.menu_list_panel.show_cashout_button";
	public static final String SHOW_TALLYEXPORT_BUTTON="main_menu.menu_list_panel.show_tallyexport_button";
	public static final String SHOW_SOREPORTS_BUTTON="main_menu.menu_list_panel.show_salesorderreport_button";
	public static final String SHOW_ABOUT_BUTTON="main_menu.menu_list_panel.show_about_button";
	
	private boolean syncButtonVisibile;
	private boolean contactUsButtonVisibile;
	private boolean viewOpenSessionsButtonVisibile;
	
	private boolean dayStartButtonVisible;
	private boolean dayEndButtonVisible;
	private boolean tillButtonVisible; 
	private boolean attendanceButtonVisibile;
	private boolean summaryButtonVisibile;  
	private boolean orderEntryButtonVisibile;
	private boolean shiftReportsButtonVisibile;
	private boolean orderDetailsButtonVisibile;
	private boolean terminalButtonVisibile;
	private boolean mailsButtonVisibile;
	private boolean orderRefundButtonVisibile;
	private boolean cashoutButtonVisibile;
	private boolean tallyExportButtonVisible;
	private boolean salesOrderReportButtonVisible;
	private boolean aboutButtonVisible;
	/**
	 * @return the dayStartButtonVisible
	 */
	public boolean isDayStartButtonVisible() {
		return dayStartButtonVisible;
	}

	/**
	 * @param dayStartButtonVisible the dayStartButtonVisible to set
	 */
	public void setDayStartButtonVisible(boolean dayStartButtonVisible) {
		this.dayStartButtonVisible = dayStartButtonVisible;
	}

	/**
	 * @return the dayEndButtonVisible
	 */
	public boolean isDayEndButtonVisible() {
		return dayEndButtonVisible;
	}

	/**
	 * @param dayEndButtonVisible the dayEndButtonVisible to set
	 */
	public void setDayEndButtonVisible(boolean dayEndButtonVisible) {
		this.dayEndButtonVisible = dayEndButtonVisible;
	}

	/**
	 * @return the tillButtonVisible
	 */
	public boolean isTillButtonVisible() {
		return tillButtonVisible;
	}

	/**
	 * @param tillButtonVisible the tillButtonVisible to set
	 */
	public void setTillButtonVisible(boolean tillButtonVisible) {
		this.tillButtonVisible = tillButtonVisible;
	}

	/**
	 * @return the attendanceButtonVisibile
	 */
	public boolean isAttendanceButtonVisible() {
		return attendanceButtonVisibile;
	}

	/**
	 * @param attendanceButtonVisibile the attendanceButtonVisibile to set
	 */
	public void setAttendanceButtonVisible(boolean attendanceButtonVisibile) {
		this.attendanceButtonVisibile = attendanceButtonVisibile;
	}

	/**
	 * @return the summaryButtonVisibile
	 */
	public boolean isSummaryButtonVisible() {
		return summaryButtonVisibile;
	}

	/**
	 * @param summaryButtonVisibile the summaryButtonVisibile to set
	 */
	public void setSummaryButtonVisible(boolean summaryButtonVisibile) {
		this.summaryButtonVisibile = summaryButtonVisibile;
	}

	/**
	 * @return the orderEntryButtonVisibile
	 */
	public boolean isOrderEntryButtonVisible() {
		return orderEntryButtonVisibile;
	}

	/**
	 * @param orderEntryButtonVisibile the orderEntryButtonVisibile to set
	 */
	public void setOrderEntryButtonVisible(boolean orderEntryButtonVisibile) {
		this.orderEntryButtonVisibile = orderEntryButtonVisibile;
	}

	/**
	 * @return the shiftReportsButtonVisibile
	 */
	public boolean isShiftReportsButtonVisible() {
		return shiftReportsButtonVisibile;
	}

	/**
	 * @param shiftReportsButtonVisibile the shiftReportsButtonVisibile to set
	 */
	public void setShiftReportsButtonVisible(boolean shiftReportsButtonVisibile) {
		this.shiftReportsButtonVisibile = shiftReportsButtonVisibile;
	}

	/**
	 * @return the orderDetailsButtonVisibile
	 */
	public boolean isOrderDetailsButtonVisible() {
		return orderDetailsButtonVisibile;
	}

	/**
	 * @param orderDetailsButtonVisibile the orderDetailsButtonVisibile to set
	 */
	public void setOrderDetailsButtonVisible(boolean orderDetailsButtonVisibile) {
		this.orderDetailsButtonVisibile = orderDetailsButtonVisibile;
	}

	/**
	 * @return the terminalButtonVisibile
	 */
	public boolean isTerminalButtonVisible() {
		return terminalButtonVisibile;
	}

	/**
	 * @param terminalButtonVisibile the terminalButtonVisibile to set
	 */
	public void setTerminalButtonVisible(boolean terminalButtonVisibile) {
		this.terminalButtonVisibile = terminalButtonVisibile;
	}

	/**
	 * @return the mailsButtonVisibile
	 */
	public boolean isMailsButtonVisible() {
		return mailsButtonVisibile;
	}

	/**
	 * @param mailsButtonVisibile the mailsButtonVisibile to set
	 */
	public void setMailsButtonVisible(boolean mailsButtonVisibile) {
		this.mailsButtonVisibile = mailsButtonVisibile;
	}
	
	/**
	 * @return the contactUsButtonVisibility
	 */
	public boolean isContactUsButtonVisible() {
		return contactUsButtonVisibile;
	}

	/**
	 * @param contactUsButtonVisibile the contactUsButtonVisibility to set
	 */
	public void setContactUsButtonVisibile(boolean contactUsButtonVisibility) {
		this.contactUsButtonVisibile = contactUsButtonVisibility;
	}

	/**
	 * @return the SyncButtonVisibility
	 */
	public boolean isSyncButtonVisible() {
		return syncButtonVisibile;
	}

	/**
	 * @param setSyncButtonVisible the syncButtonVisibility to set
	 */
	public void setSyncButtonVisible(boolean showSyncButton) {
		this.syncButtonVisibile = showSyncButton;
	}

	/**
	 * @return the viewOpenSessionsButtonVisibility
	 */
	public boolean isViewOpenSessionsButtonVisible() {
		return viewOpenSessionsButtonVisibile;
	}

	/**
	 * @param viewOpenSessionsButtonVisibility the viewOpenSessionsButtonVisibility to set
	 */
	public void setViewOpenSessionsButtonVisible(
			boolean viewOpenSessionsButtonVisibility) {
		this.viewOpenSessionsButtonVisibile = viewOpenSessionsButtonVisibility;
	}

	/**
	 * @return the orderRefundButtonVisibile
	 */
	public boolean isOrderRefundButtonVisible() {
		// TODO Auto-generated method stub
		return orderRefundButtonVisibile;
	}

	/**
	 * @param orderRefundButtonVisibile the orderRefundButtonVisibile to set
	 */
	public void setOrderRefundButtonVisible(boolean orderRefundButtonVisibile) {
		this.orderRefundButtonVisibile = orderRefundButtonVisibile;
	}

	/**
	 * @return the orderCashoutButtonVisibile
	 */
	public boolean isCashoutButtonVisible() {
		return cashoutButtonVisibile;
	}

	/**
	 * @param orderCashoutButtonVisibile the orderCashoutButtonVisibile to set
	 */
	public void setCashoutButtonVisible(boolean cashoutButtonVisibile) {
		this.cashoutButtonVisibile = cashoutButtonVisibile;
	}

	/**
	 * @return the tallyExportButtonVisible
	 */
	public boolean isTallyExportButtonVisible() {
		return tallyExportButtonVisible;
	}

	/**
	 * @param tallyExportButtonVisible the tallyExportButtonVisible to set
	 */
	public void setTallyExportButtonVisible(boolean tallyExportButtonVisible) {
		this.tallyExportButtonVisible = tallyExportButtonVisible;
	}

	/**
	 * @return the salesOrderReportButtonVisible
	 */
	public boolean isSalesOrderReportButtonVisible() {
		return salesOrderReportButtonVisible;
	}

	/**
	 * @param salesOrderReportButtonVisible the salesOrderReportButtonVisible to set
	 */
	public void setSalesOrderReportButtonVisible(
			boolean salesOrderReportButtonVisible) {
		this.salesOrderReportButtonVisible = salesOrderReportButtonVisible;
	}

	/**
	 * @return the aboutButtonVisibile
	 */
	public boolean isAboutButtonVisible() {
		return aboutButtonVisible;
	}

	/**
	 * @param aboutButtonVisible the aboutButtonVisibile to set
	 */
	public void setAboutButtonVisible(boolean aboutButtonVisible) {
		this.aboutButtonVisible = aboutButtonVisible;
	}
	
}
