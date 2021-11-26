/**
 * 
 */
package com.indocosmo.pos.data.providers.shopdb;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.sql.rowset.CachedRowSet;

import com.indocosmo.pos.common.PosEnvSettings;
import com.indocosmo.pos.common.PosLog;
import com.indocosmo.pos.common.PosEnvSettings.ApplicationType;
import com.indocosmo.pos.common.utilities.PosDateUtil;
import com.indocosmo.pos.common.utilities.PosOrderUtil;
import com.indocosmo.pos.data.beans.BeanCashout;
import com.indocosmo.pos.data.beans.BeanCashoutRecentItem;
import com.indocosmo.pos.data.beans.BeanDayProcess;
import com.indocosmo.pos.process.sync.SynchronizeToServer;

/**
 * @author Ramesh S.
 * @since 30th July 2012
 * 
 */
public class PosCashOutProvider extends PosShopDBProviderBase {
	
	private PosUsersProvider userProvider;
	private PosStationProvider stationProvider;
	/**
	 * 
	 */
	public PosCashOutProvider() {
		super("txn_cashouts");
		userProvider=new PosUsersProvider();
		stationProvider=new PosStationProvider();
	}
	
	/**
	 * @return
	 * @throws Exception
	 */
	public ArrayList<BeanCashout> getCashOutList(String date, int shiftId, int userId) throws Exception{
		
		final String where="cashout_date='"+date+"' and shift_id="+shiftId +" and user_id="+userId;
		
		return getCashOuts(where);
	}
	
	/**
	 * @return
	 * @throws Exception
	 */
	public ArrayList<BeanCashout> getCashOutList(String date, int shiftId) throws Exception{
		
		final String where="cashout_date='"+date+"' and shift_id="+shiftId;
		
		return getCashOuts(where);
	}
			
			
	/**
	 * @param where
	 * @return
	 * @throws Exception
	 */
	private ArrayList<BeanCashout> getCashOuts(String where) throws Exception {
		
		CachedRowSet crs = null;
		ArrayList<BeanCashout> cashoutList=null;
		try {

			final String sql="select * from v_txn_cashouts where "+where;
			crs = executeQuery(sql);
			if (crs != null ) {

				cashoutList=new ArrayList<BeanCashout>();
				while(crs.next()){
					BeanCashout cashout = createCashoutFromRecord(crs);
					cashoutList.add(cashout);
				}
			}
		} catch (Exception err) {
			throw err;
		} finally {
			try {
				crs.close();
			} catch (SQLException e) {
				crs = null;
				PosLog.write(this, "getCashOutList", e);
			}
		}
		return cashoutList;
	}

	/**
	 * @param crs
	 * @return
	 * @throws Exception 
	 */
	private BeanCashout createCashoutFromRecord(CachedRowSet crs)
			throws Exception {
		
		BeanCashout item=new  BeanCashout();
		item.setId(crs.getInt("id"));
		item.setShiftId(crs.getInt("shift_id"));
		item.setAmount(crs.getDouble("amount"));
		item.setCashOutDate(crs.getString("cashout_date"));
		item.setStation(stationProvider.getStation(crs.getInt("station_id")));
		item.setTitle(crs.getString("title"));
		item.setRemarks(crs.getString("remarks"));
		item.setUser(userProvider.getUserByID(crs.getInt("user_id")));
		item.setCashOutTime(crs.getString("cashout_time"));
		return item;
	}
	
    /**
     * @param item
     * @throws SQLException
     */
    public void deleteData(BeanCashout item) throws SQLException{
    	
    	final String where="id="+item.getId()+" and station_id="+item.getStation().getId();
    	
//    	markAsDeleted(where,item.getUpdatedBy());
    	
    	
		final String sql="update "+mTablename+ " set is_deleted=1, updated_by="+ item.getUpdatedBy() +"," +
				" updated_at='"+ PosDateUtil.getDateTime()+"',sync_status=0 " +
				" where "+where;
    	
    	executeNonQuery(sql);
    	
    	SynchronizeToServer.synchronizeTable(SynchronizeToServer.SyncTable.DAILY_CASHOUTS.getCode());
    }
    
	/**
	 * @return 
	 * @throws SQLException
	 */
	private PreparedStatement getInsPreparedStatment() throws SQLException{

		final String insert_sql="INSERT INTO "+mTablename+" (`station_id`, `user_id`, `shift_id`, `cashout_date`, `cashout_time`, `amount`, `title`, `remarks`,`created_by`,`created_at`,`sync_status`) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
		return getConnection().prepareStatement(insert_sql);
	}

	/**
	 * @throws SQLException
	 */
	private PreparedStatement getUpdPreparedStatment() throws SQLException {

		final String update_sql = "update "+mTablename+" set " +
				"station_id=?, user_id=?, shift_id=?, cashout_date=?, cashout_time=?, amount=?, title=?, remarks=?, updated_by=?, updated_at=?, sync_status=? where id=?";

		return getConnection().prepareStatement(update_sql);

	}
    
    /**
     * @param item
     * @throws SQLException
     */
    public void save(BeanCashout item) throws Exception{
    	
    	PreparedStatement ps=null;
    	final String where="id="+item.getId()+" and station_id="+item.getStation().getId();
    	    	
    	if(isExist(where)){
    		
    		ps=getUpdPreparedStatment();
    		ps.setInt(12, item.getId());
    		ps.setInt(9, item.getUpdatedBy());
    	}else{
    		
    		ps=getInsPreparedStatment();
    		ps.setInt(9, item.getCreatedBy());
    	}
    	
    	ps.setInt(1, item.getStation().getId());
    	ps.setInt(2, item.getUser().getId());
    	ps.setInt(3, item.getShiftId());
    	ps.setString(4, item.getPosDate());
    	ps.setString(5, item.getCashOutTime());
    	ps.setDouble(6, item.getAmount());
    	ps.setString(7, item.getTitle());
    	ps.setString(8, item.getRemarks());
    	ps.setString(10, PosDateUtil.getDateTime());
    	ps.setInt(11, 0);
    	
    	ps.execute();
    	
    	SynchronizeToServer.synchronizeTable(SynchronizeToServer.SyncTable.DAILY_CASHOUTS.getCode());
    	
    }

	/**
	 * @param posDate
	 * @return
	 * @throws SQLException 
	 */
    public double getTotalCashOut(String posDate, int shiftId) throws SQLException {
		
		double totalCashOut=0; 
		
		final String sql="select sum(amount) as totalcashout from "+mTablename+" where shift_id='"+shiftId+"' and cashout_date='"+posDate+"' and is_deleted=0";
		
		final CachedRowSet crs=executeQuery(sql);
		if(crs!=null && crs.next()){
			
			totalCashOut=crs.getDouble("totalcashout");
		}
		
		
		return totalCashOut;
	}

    
	/**
	 * @param posDate
	 * @return
	 * @throws SQLException 
	 */
    	public double getTotalCashOut(String posDate, int shiftId, String dateFrom, String timeFrom, String dateTo, String timeTo) throws SQLException {
		
		double totalCashOut=0; 
		String where="" ;
		
		final String timeCriteria=PosOrderUtil.getTimeCriteria("cashout_time",timeFrom,timeTo);
		
		
		if( shiftId>0)
			where+="shift_id='"+shiftId+"' and ";
		
//		where+=" cashout_date='" + posDate + "' and cashout_time between concat(date('"+ dateFrom + "'),' ', time('" + timeFrom + "'))"
//						+" and concat(date('" + dateTo + "'),' ', time('" +timeTo +"')) and is_deleted=0";
		
		where+=" cashout_date='" + posDate + "' and is_deleted=0  " + (timeCriteria.trim()!=""? " AND " + timeCriteria:"");
		
		final String sql="select sum(amount) as totalcashout from "+mTablename+" where " + where;
				
		
		final CachedRowSet crs=executeQuery(sql);
		if(crs!=null && crs.next()){
			
			totalCashOut=crs.getDouble("totalcashout");
		}
		
		
		return totalCashOut;
	}

	@Override
	public int purgeData(String dateTo) throws SQLException{
		

		final String where=" cashout_date<='" + dateTo + "'" + 
				(PosEnvSettings.getInstance().getApplicationType().equals(ApplicationType.Standard)?" AND sync_status=1  ":"");
		return deleteData(where);
	}
	
	
	@Override
	public int deleteData(String where) throws SQLException {
	
		final String sql= "delete from txn_cashouts where "+where;
		
		return executeNonQuery(sql);
		
	}
	
	/* (non-Javadoc)
	 * @see com.indocosmo.pos.data.providers.PosDatabaseProvider#addToHistory(java.lang.String)
	 */
	@Override
	public StringBuffer addToHistory(String date) throws SQLException {
	 
		
		String where =" cashout_date<='" + date + "'";
		return super.addToHistory(where);
		 
		 
	}
	
	/**
	 * @return
	 * @throws Exception
	 */
	public ArrayList<BeanCashoutRecentItem> getRecentCashoutRemarks() throws Exception{
		
		ArrayList<BeanCashoutRecentItem> remarks=null;
		CachedRowSet crs=null;
		
		try {

			final String sql="SELECT DISTINCT remarks FROM 	txn_cashouts ORDER BY remarks ";
			crs=executeQuery(sql);
			
			if (crs != null ) {

				Integer index=0;
				remarks=new ArrayList<BeanCashoutRecentItem>();
				while(crs.next()){
					
					BeanCashoutRecentItem item = new BeanCashoutRecentItem();
					item.setId(++index);
					item.setRemarks(crs.getString("remarks"));
					remarks.add(item);
				}
			}
		} catch (Exception err) {
			
			PosLog.write(this, "getRecentCashoutRemarks", err);
			throw err;
			
		} finally {
			
			try {
				
				crs.close();
			} catch (SQLException e) {
				
				crs = null;
				PosLog.write(this, "getRecentCashoutRemarks", e);
			}
		}
		
		return remarks;
	}

	
	/**
	 * @return
	 * @throws Exception
	 */
	public ArrayList<BeanCashoutRecentItem> getRecentCashoutTitle() throws Exception{
		
		ArrayList<BeanCashoutRecentItem> remarks=null;
		CachedRowSet crs=null;
		
		try {

			final String sql="SELECT DISTINCT title FROM txn_cashouts WHERE  is_deleted=0 AND  title IS NOT NULL ORDER BY 1 ASC";
			crs=executeQuery(sql);
			
			if (crs != null ) {

				remarks=new ArrayList<BeanCashoutRecentItem>();
				Integer index=0;
				while(crs.next()){
					
					BeanCashoutRecentItem item = new BeanCashoutRecentItem();
					item.setId(++index);
					item.setRemarks(crs.getString("title"));
					remarks.add(item);
				}
			}
		} catch (Exception err) {
			
			PosLog.write(this, "getRecentCashoutTitle", err);
			throw err;
			
		} finally {
			
			try {
				
				crs.close();
			} catch (SQLException e) {
				
				crs = null;
				PosLog.write(this, "getRecentCashoutTitle", e);
			}
		}
		
		return remarks;
	}

}
