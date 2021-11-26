/**
 * 
 */
package com.indocosmo.pos.data.providers;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.sql.rowset.CachedRowSet;

import com.indocosmo.pos.data.beans.BeanLoginSessions;
import com.indocosmo.pos.data.providers.shopdb.PosShopDBProviderBase;


/**
 * @author anand
 *
 */
public class PosLoginSessionsProvider extends PosShopDBProviderBase {
	
	
	/**
	 * 
	 */
	public PosLoginSessionsProvider(){

		super("v_login_sessions");
	}

	/**
	 * @param open_till_id
	 * @return
	 * @throws Exception
	 */
	public int getActiveSessionCount(int open_till_id)throws Exception{

		int sessionCount=0;

		final String sqlString ="SELECT COUNT(login_sessions_id) AS `session_count` FROM v_login_sessions where `end_at` IS NULL AND opening_till_id="+open_till_id; 
		final CachedRowSet crs = executeQuery(sqlString);

		if(crs!=null && crs.size()>0){

			crs.next();
			sessionCount=crs.getInt("session_count");
		}

		return sessionCount;
	}

	/**
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<BeanLoginSessions> getAllOpenSessions() throws SQLException{

		return getAllOpenSessions(null);
	}

	/**
	 * @param open_till_id
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<BeanLoginSessions> getAllOpenSessions(Integer open_till_id) throws SQLException{

		String sqlString ="SELECT *  FROM v_login_sessions where `end_at` IS NULL";

		if(open_till_id!=null)
			sqlString +=" AND opening_till_id="+open_till_id;

		final CachedRowSet crs = executeQuery(sqlString);

		return getAllSessions(crs);
	}

	/**
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<BeanLoginSessions> getAllSessions() throws SQLException{

		String sqlString ="SELECT *  FROM v_login_sessions";
		final CachedRowSet crs = executeQuery(sqlString);

		return getAllSessions(crs);
	}
	
	/**
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<BeanLoginSessions> getAllSessions(Integer open_till_id) throws SQLException{

		String sqlString ="SELECT *  FROM v_login_sessions";
		if(open_till_id!=null)
			sqlString +="AND opening_till_id="+open_till_id;
		
		final CachedRowSet crs = executeQuery(sqlString);

		return getAllSessions(crs);
	}

	/**
	 * @param crs
	 * @return
	 * @throws SQLException
	 */
	private ArrayList<BeanLoginSessions> getAllSessions(CachedRowSet crs) throws SQLException{

		ArrayList<BeanLoginSessions> loginSessions=null;
		if(crs!=null && crs.size()>0){

			loginSessions=new ArrayList<BeanLoginSessions>();
			while(crs.next()){

				BeanLoginSessions session=createLoginSessionFromCRS(crs);
				loginSessions.add(session);
			}
		}

		return loginSessions;
	}

	/**
	 * @param crs
	 * @return
	 * @throws SQLException 
	 */
	private BeanLoginSessions createLoginSessionFromCRS(CachedRowSet crs) throws SQLException {

		BeanLoginSessions session=new BeanLoginSessions();
		
		session.setCashierShiftId(crs.getInt("cashier_shift_id"));
		session.setId(crs.getInt("login_sessions_id"));
		session.setPosId(crs.getInt("pos_id"));
		session.setLoginUserId(crs.getInt("login_user_id"));
		session.setStartAt(crs.getString("start_at"));
		session.setEndAt(crs.getString("end_at"));
		session.setOpeningTillId(crs.getInt("opening_till_id"));
		session.setUserName(crs.getString("user_name"));
		session.setCardNo(crs.getString("card_no"));
		session.setStationCode(crs.getString("station_code"));
		session.setStationName(crs.getString("station_name"));
		
		return session;
	}

	/**
	 * @param loginSession
	 * @throws SQLException 
	 */
	public void openNewSession(BeanLoginSessions loginSession) throws SQLException {
		
		closeOpenSession(loginSession.getPosId());
			
		final PreparedStatement psIns=getConnection().prepareStatement("INSERT INTO `login_sessions` (`pos_id`, `cashier_shift_id`, `login_user_id`, `start_at`) VALUES ( ?, ?, ?, ?);");
		
		psIns.setInt(1, loginSession.getPosId());
		psIns.setInt(2, loginSession.getCashierShiftId());
		psIns.setInt(3, loginSession.getLoginUserId());
		psIns.setString(4, loginSession.getStartAt());
		
		psIns.execute();
		
	}
	
	/**
	 * @param pos_id
	 * @throws SQLException
	 */
	public void closeOpenSession(int pos_id) throws SQLException{
		
		final String sql="delete from login_sessions where pos_id="+pos_id;
		executeNonQuery(sql);
	}


	@Override
	public int purgeData(String dateTo) throws SQLException{
		
		final String where="  end_at<='" + dateTo + "'";
		return deleteData(where);
	}
	
	
	@Override
	public int deleteData(String where) throws SQLException {
	
		final String sql= "delete from login_sessions where "+where;
		
		return executeNonQuery(sql);
		
	}
}
