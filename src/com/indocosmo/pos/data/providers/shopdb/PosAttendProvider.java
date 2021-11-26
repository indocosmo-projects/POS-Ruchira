/**
 *
 *
 */
package com.indocosmo.pos.data.providers.shopdb;

import java.sql.SQLException;

import javax.sql.rowset.CachedRowSet;

import com.indocosmo.pos.common.PosEnvSettings;
import com.indocosmo.pos.common.PosLog;
import com.indocosmo.pos.common.utilities.PosDateUtil;
import com.indocosmo.pos.data.beans.BeanStaffAttendance;
import com.indocosmo.pos.process.sync.SynchronizeToServer;

/**
 * @author Ramesh S.
 * @since 19th July 2012
 */

public class PosAttendProvider extends PosShopDBProviderBase {

	public PosAttendProvider() {
		super("id", "txn_staff_attendance");
	}

	// Get latest attendance of employee id, passed as parameter
	public BeanStaffAttendance getRecentAttendanceByEmpID(int empID)
			throws Exception {

		BeanStaffAttendance attendance = new BeanStaffAttendance();
		CachedRowSet crs = null;

		if (empID == 0)
			throw (new Exception("Invalid Employee id."));

		String where = "employee_id = "	+ empID
				+ " and ((concat(shift_start_date ,\" \", shift_start_time))  in (SELECT MAX(CONCAT(shift_start_date ,\" \", shift_start_time)) "
				+ "FROM txn_staff_attendance where employee_id = " + empID
				+ "))";

		try {
			crs = getData(where);

			if (crs != null && crs.next()) {
				attendance.setId(crs.getString("id"));
				attendance.setEmployee_id(crs.getInt("employee_id"));
				attendance.setShift_no(crs.getInt("shift_no"));
				attendance.setShift_id(crs.getInt("shift_id"));
				attendance.setShift_start_date(crs
						.getString("shift_start_date"));
				attendance.setShift_start_time(crs
						.getString("shift_start_time"));
				attendance.setShift_end_date(crs.getString("shift_end_date"));
				attendance.setShift_end_time(crs.getString("shift_end_time"));
				attendance.setIs_processed(crs.getInt("is_processed"));
				attendance.setSync_status(crs.getInt("sync_status"));
				attendance.setSync_message(crs.getString("sync_message"));
			}

		} catch (SQLException err) {
			throw err;

		} finally {
			try {
				crs.close();

			} catch (SQLException e) {
				crs = null;
				PosLog.write(this, "getLatestStaffAttendance", e);
			}
		}
		return attendance;
	}

	public void updateStartTime(int employeeID)
			throws Exception {
		try {
//			Date currSystemDate = Calendar.getInstance().getTime();
//			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
//					PosDateUtil.DATE_FORMAT);
			String formattedCurrDate = PosDateUtil.getDate();
//			SimpleDateFormat simpleTimeFormat = new SimpleDateFormat(
//					PosDateUtil.TIME_FORMAT_24);
			String posDateStart = PosEnvSettings.getPosEnvSettings().getPosDate();
			String formattedCurrTime = PosDateUtil.getTime();

			String insertSQL = "insert into txn_staff_attendance (pos_id,"
					+ "employee_id, shift_no, shift_start_date, "
					+ "shift_start_time, pos_date_start,sync_status) values ("
					+ PosEnvSettings.getInstance().getStation().getId() + ", "
					+  employeeID + ", "
					+  getNextShiftNo() + ", '"
//					+ PosEnvSettings.getInstance().getCashierShiftInfo().getShiftItem().getId()+ "', '" 
					+ formattedCurrDate + "', '" 
					+ formattedCurrTime + "', '"
					+ posDateStart + "', '"
					+ PosEnvSettings.getInstance().getSyncToStart() + "')";

			beginTrans();
			executeNonQuery(insertSQL);
			commitTrans();
			SynchronizeToServer.synchronizeTable(SynchronizeToServer.SyncTable.TXN_STAFF_ATTENDANCE.getCode(),
					"txn_staff_attendance.employee_id=" +employeeID);

		} catch (SQLException err) {
			rollBack();
			throw err;
		}
		return;
	}
	
	/* 
	 * To-do
	 * The logic of getting next shift_no
	 * may change in next changes
	 */
	private int getNextShiftNo(){
		return getMaxId("shift_no", mTablename)+1;
	}

	public void updateEndTime(BeanStaffAttendance attendance)
			throws Exception {
		try {

//			Date currSystemDate = Calendar.getInstance().getTime();
//			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
//					PosDateUtil.DATE_FORMAT);
			String formattedCurrDate = PosDateUtil.getDate();
//			SimpleDateFormat simpleTimeFormat = new SimpleDateFormat(
//					PosDateUtil.TIME_FORMAT_24);
			String formattedCurrTime = PosDateUtil.getTime();
			String posEndDate = PosEnvSettings.getPosEnvSettings().getPosDate();

			String updateSQL = "update txn_staff_attendance set "
					+ "shift_end_date = '" + formattedCurrDate + "', "
					+ "shift_end_time = '" + formattedCurrTime + "', "
					+ "sync_status = '" + PosEnvSettings.getInstance().getSyncToStart() + "', "
					+ "pos_date_end = '" + posEndDate + "' "
					+ " where shift_start_date = '"
					+ attendance.getShift_start_date()
					+ "' and shift_start_time = '"
					+ attendance.getShift_start_time() + "' and employee_id = "
					+ attendance.getEmployee_id();

			beginTrans();
			executeNonQuery(updateSQL);
			commitTrans();
			SynchronizeToServer.synchronizeTable(SynchronizeToServer.SyncTable.TXN_STAFF_ATTENDANCE.getCode(),
												"txn_staff_attendance.employee_id=" + attendance.getEmployee_id());

		} catch (SQLException err) {
			rollBack();
			throw err;
		}
		return;
	}
	
	public boolean isAttendanceOpen(int employeeId){
		boolean status = false;

		String statusSql = "select count(id) as row_count from txn_staff_attendance where" +
				" shift_end_date is null and shift_end_time is null" +
				" and shift_start_date is not null and shift_start_time is not null"+
				" and employee_id="+employeeId;
		
		CachedRowSet crsStatus= null;
		crsStatus = executeQuery(statusSql);
		if(crsStatus!=null){
			try {
				if(crsStatus.next())
					status=(crsStatus.getInt("row_count")>0);
			} catch (SQLException e) {
				PosLog.write(this, "isAttendanceIn", e);
			}
				
		}
		return status;
		
	}
	

	@Override
	public int purgeData(String dateTo) throws SQLException{
		
		final String where="sync_status=1 AND shift_end_date<='" + dateTo + "'";
		return deleteData(where);
	}
	
	
	@Override
	public int deleteData(String where) throws SQLException {
	
		final String sql= "delete from txn_staff_attendance where "+where;
		
		return executeNonQuery(sql);
		
	}

	
}