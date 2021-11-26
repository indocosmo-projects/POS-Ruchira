/**
 * 
 */
package com.indocosmo.pos.data.providers.terminaldb;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.sql.rowset.CachedRowSet;

import com.indocosmo.pos.common.PosLog;
import com.indocosmo.pos.common.enums.device.PortParity;
import com.indocosmo.pos.data.beans.terminal.device.BeanDevCashBoxConfig;
import com.indocosmo.pos.data.beans.terminal.device.ports.BeanParallelPortInfo;
import com.indocosmo.pos.data.beans.terminal.device.ports.BeanPortBase;
import com.indocosmo.pos.data.beans.terminal.device.ports.BeanSerialPortInfo;
import com.indocosmo.pos.data.beans.terminal.device.ports.BeanUSBPortInfo;
import com.indocosmo.pos.terminal.devices.PosPortDevice.PortType;

/**
 * @author Ramesh S.
 * @since 08th Aug 2012
 */
public final class PosDevCashBoxConfigProvider extends PosTerminalDBProviderBase {

	
	/**
	 * 
	 */
	public PosDevCashBoxConfigProvider()  {
		super("device_cash_box");
		
	}
	
	public BeanDevCashBoxConfig getCashBoxConfig() throws Exception{
		return loadData();
	}
	
	private BeanPortBase getPortInfoFromCrs(CachedRowSet crs) throws SQLException{
		BeanPortBase portInfo=null;
		PortType type=PortType.get(crs.getInt("port_type"));
		switch (type) {
		case PARALLEL:
			portInfo=new BeanParallelPortInfo();
			break;
		case SERIAL:
			portInfo=getSerailPortinfoFromCrs(crs);
			break;
		case USB:
			portInfo=new BeanUSBPortInfo();
			break;
		default:
			break;
		}

		portInfo.setName(crs.getString("port"));

		return portInfo;
	}
	
	private BeanPortBase getSerailPortinfoFromCrs(CachedRowSet crs) throws SQLException{ 
		BeanSerialPortInfo portInfo=new BeanSerialPortInfo();
		portInfo.setPortBaudRate(crs.getInt("port_baud_rate"));
		portInfo.setPortDataBits(crs.getInt("port_data_bits"));
		portInfo.setPortParity(PortParity.get(crs.getInt("port_parity")));
		portInfo.setPortStopBits(crs.getInt("port_stop_bits"));
		return portInfo;
	}

	private BeanDevCashBoxConfig loadData() throws Exception {
		BeanDevCashBoxConfig cashBox =null;
		CachedRowSet crs = getData();
		if (crs != null) {
			try {
				
				if (crs.next()) {
					cashBox = new BeanDevCashBoxConfig();
					cashBox.setAllowOpen(crs.getBoolean("is_allow_open"));
					cashBox.setCmdOpen(crs.getString("cmd_open"));
					cashBox.setPortInfo(getPortInfoFromCrs(crs));
				}
			} catch (Exception err) {
				cashBox = null;
				throw err;
			} finally {
				try {
					crs.close();
				} catch (SQLException e1) {
					PosLog.write(this, "loadData", e1);
				}
			}
		}
		return cashBox;
	}


	public void saveSettings(BeanDevCashBoxConfig cashBoxSettings) {
		try {
			beginTrans();
			deleteData();
			PreparedStatement prep;
			final String sql = "insert into "
					+ mTablename
					+ " (is_allow_open, cmd_open, port, port_type, port_baud_rate, port_data_bits, port_parity, port_stop_bits) values" 
					+"(?,?,?,?,?,?,?,?)";
				
					prep = mConnection.prepareStatement(sql);
					prep.setBoolean(1, cashBoxSettings.isAllowOpen());
					prep.setString(2, cashBoxSettings.getCmdOpen());
					setPortinfo(cashBoxSettings.getPortInfo(),prep);
					prep.execute();
					commitTrans();

		} catch (Exception e) {
			PosLog.write(this, "saveSettings", e);
			try {
				rollBack();
			} catch (SQLException e1) {
				PosLog.write(this, "saveSettings", e);
			}
		}
	}
	
	private void setPortinfo(BeanPortBase portInfo, PreparedStatement prep) throws SQLException{
		
		prep.setString(3, portInfo.getName());
		prep.setInt(4, portInfo.getType().getValue());
		
		switch (portInfo.getType()) {
		case PARALLEL:
			break;
		case SERIAL:
			setSerailPortInfo(prep,(BeanSerialPortInfo)portInfo);
			break;
		case USB:
			break;
		default:
			break;
		}
	}

	/**
	 * @param crs
	 * @throws SQLException 
	 */
	private void setSerailPortInfo(PreparedStatement prep,BeanSerialPortInfo portInfo) throws SQLException {
		prep.setInt(5, portInfo.getPortBaudRate());
		prep.setInt(6, portInfo.getPortDataBits());
		prep.setInt(7, portInfo.getPortParity().getValue());
		prep.setInt(8, portInfo.getPortStopBits());
	}
}
