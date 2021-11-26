/**
 * 
 */
package com.indocosmo.pos.data.providers.terminaldb;

import java.sql.SQLException;

import javax.sql.rowset.CachedRowSet;

import com.indocosmo.pos.common.PosLog;
import com.indocosmo.pos.common.enums.device.PortParity;
import com.indocosmo.pos.data.beans.terminal.device.BeanDevPoleDisplayConfig;

/**
 * @author jojesh
 *
 */
public final class PosDevPoleDisplayConfigProvider extends PosTerminalDBProviderBase {

	private BeanDevPoleDisplayConfig mPoleDisplaySettings;

	/**
	 * 
	 */
	public PosDevPoleDisplayConfigProvider() {
		super("device_pole_display");
		loadData();
	}

	private void loadData(){
		CachedRowSet crs=getData();
		if(crs!=null){
			try {
				if(crs.next()){
					mPoleDisplaySettings=new BeanDevPoleDisplayConfig();
					mPoleDisplaySettings.setPort(crs.getString("port"));
					mPoleDisplaySettings.setPortBaudRate(crs.getInt("port_baud_rate"));
					mPoleDisplaySettings.setPortDataBits(crs.getInt("port_data_bits"));
					mPoleDisplaySettings.setPortParity(PortParity.get(crs.getInt("port_parity")));
					mPoleDisplaySettings.setPortStopBits(crs.getInt("port_stop_bits"));
					mPoleDisplaySettings.setColumnCount(crs.getInt("no_columns"));
					mPoleDisplaySettings.setRowCount(crs.getInt("no_rows"));
					mPoleDisplaySettings.setCommandClear(crs.getString("cmd_clear"));
					mPoleDisplaySettings.setCommandClearLine(crs.getString("cmd_clear_line"));
					mPoleDisplaySettings.setCommandMove(crs.getString("cmd_move"));
					mPoleDisplaySettings.setCommandClearLine(crs.getString("cmd_clear_line"));
					mPoleDisplaySettings.setMessageClosed(crs.getString("msg_closed"));
					mPoleDisplaySettings.setMessageNewBill(crs.getString("msg_new_bill"));
					mPoleDisplaySettings.setMessageStartup(crs.getString("msg_startup"));
				}
			} catch (SQLException e) {
				PosLog.write(this, "loadData", e);
				mPoleDisplaySettings=null;
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

	public  BeanDevPoleDisplayConfig getDeviceConfiguration(){
		return mPoleDisplaySettings;
	}

	public  void setSettings(BeanDevPoleDisplayConfig poleDisplaySettings){
		try {
			beginTrans();
			deleteData();
			final String sql="insert into "+mTablename+" values('"+poleDisplaySettings.getPort()+"',"
					+poleDisplaySettings.getPortBaudRate()+","
					+poleDisplaySettings.getPortDataBits()+","
					+poleDisplaySettings.getPortParity().getValue()+","
					+poleDisplaySettings.getPortStopBits()+","
					+poleDisplaySettings.getRowCount()+","
					+poleDisplaySettings.getColumnCount()+",'"
					+poleDisplaySettings.getCommandMove()+"','"
					+poleDisplaySettings.getCommandClear()+"','"
					+poleDisplaySettings.getCommandClearLine()+"','"
					+poleDisplaySettings.getMessageStartup()+"','"
					+poleDisplaySettings.getMessageNewBill()+"','"
					+poleDisplaySettings.getMessageClosed()+"');";
			executeNonQuery(sql);
			commitTrans();
			mPoleDisplaySettings=poleDisplaySettings;
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
