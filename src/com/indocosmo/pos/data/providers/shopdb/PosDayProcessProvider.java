/**
 * 
 */
package com.indocosmo.pos.data.providers.shopdb;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import javax.sql.rowset.CachedRowSet;

import com.indocosmo.pos.common.PosEnvSettings;
import com.indocosmo.pos.common.PosLog;
import com.indocosmo.pos.common.utilities.PosDateUtil;
import com.indocosmo.pos.data.beans.BeanDayProcess;

/**
 * @author deepak
 * @since 16.10.2012
 */
public class PosDayProcessProvider extends PosShopDBProviderBase{
	
	/**
	 * Enum for day process state 
	 * @author deepak
	 *
	 */
	public enum PosDayProcess{
		DAY_START(1), DAY_END(2);
		private static final Map<Integer,PosDayProcess> mLookup 
		= new HashMap<Integer,PosDayProcess>();

		static {
			for(PosDayProcess posdayprocess : EnumSet.allOf(PosDayProcess.class))
				mLookup.put(posdayprocess.getValue(), posdayprocess);
		}

		private int mValue;
		
		private PosDayProcess(int value) {
			this.mValue = value;
		}

		public int getValue() { return mValue; }
		
		public static PosDayProcess get(int value) { 
			return mLookup.get(value); 
		}
	}
	public PosDayProcessProvider(){
		super("day_process");
	}
	public BeanDayProcess getDayProcess(String date) throws Exception{
		return getDayProcess(date, PosDayProcess.DAY_START);
	}
	
	public BeanDayProcess getDayProcess(String date,PosDayProcess process) throws Exception{
		 BeanDayProcess dp=null;
		 final String getDateTime = "SELECT * FROM day_process WHERE pos_date='"+date+"'AND day_process_type="+process.getValue();
		 final CachedRowSet crsDateTime = executeQuery(getDateTime);
		 if(crsDateTime!=null){
			 try{
				 if(crsDateTime.next()){
					 dp= new BeanDayProcess();
					 dp.setShopId(crsDateTime.getInt("shop_id"));
					 dp.setStationId(crsDateTime.getInt("station_id"));
					 dp.setPosDate(crsDateTime.getString("pos_date"));
					 dp.setStatus(PosDayProcess.get(crsDateTime.getInt("day_process_type")));
					 dp.setSynchUp(crsDateTime.getBoolean("synch_up"));
					 dp.setSynchDown(crsDateTime.getBoolean("synch_down"));
					 dp.setDoneBy(crsDateTime.getInt("done_by"));
					 dp.setDoneAt(crsDateTime.getString("done_at"));
				 }
			 }catch (SQLException e){
				 PosLog.write(this, "getDayProcess", e);
				 throw new Exception("Failed to get Day process information");
			 }
		 }
		 return dp;
	}
	/**
	 * Setting the open Pos date
	 * @return mOpenPosDate
	 * @throws Exception
	 */
	public String getOpenPosDate() throws Exception{
		String mOpenPosDate = null;
//		String opendate_sql = "SELECT  pos_date open_date FROM day_process where " +
//				"pos_date > ( select max(pos_date) from day_process where day_process_type ="+PosDayProcess.DAY_END.getValue()+")";
		String opendate_sql = "SELECT  pos_date open_date FROM day_process dp where 1 > (select count(pos_date) from day_process where day_process_type ="+PosDayProcess.DAY_END.getValue()+
				" and pos_date=dp.pos_date)";
		CachedRowSet crsLastDate = executeQuery(opendate_sql);
		if(crsLastDate!=null){
			try {
				if(crsLastDate.next()){
					mOpenPosDate = crsLastDate.getString("open_date");
				}
			} catch (SQLException e) {
				PosLog.write(this, "getOpenPosDate", e);
				throw new Exception("Failed to get Day process information. Please contact Administrator.");
			}
		}
		return mOpenPosDate;
	}
	
	/**
	 * Setting the Previous date
	 * @return
	 * @throws Exception
	 */
	public String getPreviousPosDate() throws Exception{

		String mLastPosDate = null;
		String lastdate_sql = "SELECT  max(pos_date) max_date FROM day_process where" +
				" shop_id = "+PosEnvSettings.getInstance().getShop().getId()+
//				" and station_id = "+PosEnvSettings.getInstance().getStation().getId()+
				" and day_process_type = "+PosDayProcess.DAY_END.getValue();
		CachedRowSet crsLastDate= null;
		crsLastDate = executeQuery(lastdate_sql);
		if(crsLastDate!=null){
			try {
				if(crsLastDate.next()){
					mLastPosDate = crsLastDate.getString("max_date");
				}
			} catch (SQLException e) {
				PosLog.write(this, "getPreviousPosDate", e);
				throw new Exception("Failed to get Day process information. Please contact Administrator.");
			}
		}
		return mLastPosDate;
		
	}
	
	public String getDefaultCurrentPosDate() throws Exception {
		String mLastPosDate = null;
		String mDefaultCurrentDate = null;
		mLastPosDate = getPreviousPosDate();
		Calendar c = Calendar.getInstance();
		if (mLastPosDate!= null) {
			try {
				c.setTime(PosDateUtil.parse(mLastPosDate));
				c.add(Calendar.DATE, 1);
				mDefaultCurrentDate = PosDateUtil.format(
						PosDateUtil.DATE_FORMAT_YYYYMMDD, c.getTime());
			} catch (Exception e) {
				PosLog.write(this, "getPreviousPosDate", e);
				throw new Exception(
						"Failed to get Day process information. Please contact Administrator.");
			}
		}else{
			mDefaultCurrentDate = PosDateUtil.getDate();
		}
		
		return mDefaultCurrentDate;

	}
	
	/**
	 * Setting the Current Pos date
	 * @returnmOpenPosDate
	 * @throws Exception
	 */
	public String getCurrentPosDate() throws Exception{
		
		String mLastPosDate = null;
		String lastdate_sql = "SELECT  max(pos_date) max_date FROM day_process where" +
				" shop_id = "+PosEnvSettings.getInstance().getShop().getId()+
//				" and station_id = "+PosEnvSettings.getInstance().getStation().getId()+
				" and day_process_type = "+PosDayProcess.DAY_START.getValue();
		
		CachedRowSet crsLastDate= null;
		crsLastDate = executeQuery(lastdate_sql);
		if(crsLastDate!=null){
			try {
				if(crsLastDate.next()){
					mLastPosDate = crsLastDate.getString("max_date");
				}
			} catch (SQLException e) {
				PosLog.write(this, "getCurrentPosDate", e);
				throw new Exception("Failed to get Day process information. Please contact Administrator.");
			}
		}
		return mLastPosDate;
	}
	
	/**
	 * Method for creating prepared statement.
	 * @param dayProcess
	 * @param status
	 * @return prep
	 * @throws Exception
	 */
	private PreparedStatement getInsertDayStartPreparedStatement(BeanDayProcess dayProcess, int status) throws Exception {
		
		PreparedStatement prep;
		String insert_sql="insert into "+" day_process"+" ("+
							"shop_id, "+
							"station_id, "+
							"pos_date, "+
							"day_process_type, "+
							"synch_up, "+
							"synch_down, "+
							"done_by, "+
							"done_at )"+
							" values (?,?,?,?,?,?,?,?)";
		try {
			prep = mConnection.prepareStatement(insert_sql);
			prep.setInt(1, dayProcess.getShopId());
			prep.setInt(2, dayProcess.getStationId());
			prep.setString(3, dayProcess.getPosDate());
			prep.setInt(4,status);
			prep.setBoolean(5, dayProcess.ismSynchUp());
			prep.setBoolean(6, dayProcess.isSynchDown());
			prep.setInt(7, dayProcess.getDoneBy());
			prep.setString(8, dayProcess.getDoneAt());
		} catch (SQLException e) {
			PosLog.write(this, "getInsertDayStartPreparedStatement", e);
			throw new Exception("Failed Insert day process. Please contact Administrator.");
		}
		
		return prep;
	}
	
	/**
	 * Method for Updating day process, calling the getInsertDayStartPreparedStatement 
	 * according to DAY_START or DAY_END.
	 * @param dayProcess
	 * @param dayStatus 
	 * @throws Exception
	 */
	public void updateDayProcess(BeanDayProcess dayProcess, PosDayProcess dayStatus) throws Exception{
	
		if(dayProcess.getStatus()== PosDayProcess.DAY_START){
			getInsertDayStartPreparedStatement(dayProcess,dayStatus.getValue()).executeUpdate();
		}else {
			getInsertDayStartPreparedStatement(dayProcess,dayStatus.getValue()).executeUpdate();
		}
		
	}
	/*
	 * 
	 */
	public boolean isDayProcessed(String date ) throws Exception{
		
		boolean result=false;
		final String sql = "SELECT * FROM day_process WHERE pos_date='"+date+"' AND day_process_type="+  PosDayProcess.DAY_END.getValue() ;
		 
		 try{
			 final CachedRowSet crs = executeQuery(sql);
			 if(crs!=null){
				 if(crs.next()){
					 result=true;
				 }
			 }
		 } catch (Exception e) {
				PosLog.write(this, "isDayProcessed", e);
		}
		return result;
	}
	
	/*
	 * function to clear all active tab sessions
	 * Author - ASLAM
	 * Date :- 01-02-2020 */
	public void removeTabSessions() {
		
		executeQuery("update users set logged_in_from = null");
		
	}

}
