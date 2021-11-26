/**
 * 
 */
package com.indocosmo.pos.data.providers.terminaldb;

import java.sql.SQLException;

import javax.sql.rowset.CachedRowSet;

import com.indocosmo.pos.common.PosLog;
import com.indocosmo.pos.data.beans.terminal.device.BeanDevEFTConfig;

/**
 * @author deepak
 *
 */
public class PosDevEFTConfigProvider extends PosTerminalDBProviderBase {

	private BeanDevEFTConfig mPosEFTConfigSettings;
	
	public PosDevEFTConfigProvider(){
		super("device_eft");
		loadData();
	}

	/**
	 * 
	 */
	private void loadData() {

		CachedRowSet crs=getData();
		if(crs!=null){
			try {
				if(crs.next()){
					mPosEFTConfigSettings=new BeanDevEFTConfig();
					mPosEFTConfigSettings.setEFTHostIP(crs.getString("eft_host_ip"));
					mPosEFTConfigSettings.setEFTHostPort(crs.getInt("eft_host_port"));
					mPosEFTConfigSettings.setSocketTimeOut(crs.getInt("socket_timeout"));
					mPosEFTConfigSettings.setOpenCashBox(crs.getBoolean("open_cashbox"));
				}
			} catch (SQLException e) {
				PosLog.write(this, "loadData", e);
				mPosEFTConfigSettings=null;
			}
			finally{
				try {
					crs.close();
				} catch (SQLException e1) {
					PosLog.write(this, "loadData", e1);
				}
			}
		}
	}
	
	public  BeanDevEFTConfig getDeviceConfiguration(){
		return mPosEFTConfigSettings;
	}

	public  void setSettings(BeanDevEFTConfig EFTConfigSettings){
		try {
			beginTrans();
			deleteData();
			final String sql="insert into "+mTablename+" values('"+EFTConfigSettings.getEFTHostIP()+"','"
					+EFTConfigSettings.getEFTHostPort()+"','"+EFTConfigSettings.getSocketTimeOut()+"'," 
					+ ( EFTConfigSettings.isOpenCashBox()?"1":"0" )	+ ");";
			executeNonQuery(sql);
			commitTrans();
			mPosEFTConfigSettings=EFTConfigSettings;
		} catch (Exception e) {
			PosLog.write(this, "setSettings", e);
			try {
				rollBack();
			} catch (SQLException e1) {
				PosLog.write(this, "setSettings", e);
			}

		}

	}
	
}
