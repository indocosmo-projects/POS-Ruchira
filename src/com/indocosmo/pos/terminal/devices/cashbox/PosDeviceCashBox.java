/**
 * 
 */
package com.indocosmo.pos.terminal.devices.cashbox;

import gnu.io.SerialPortEvent;

import com.indocosmo.pos.data.beans.terminal.device.BeanDevCashBoxConfig;
import com.indocosmo.pos.data.providers.terminaldb.PosDevCashBoxConfigProvider;
import com.indocosmo.pos.terminal.devices.PosDeviceUtil;
import com.indocosmo.pos.terminal.devices.PosSerailPortDevice;

/**
 * @author jojesh
 *
 */
public final class PosDeviceCashBox extends PosSerailPortDevice {

	private BeanDevCashBoxConfig mCashBoxConfig;
	
	private static PosDeviceCashBox mInstance;
	/**
	 * 
	 */
	private  PosDeviceCashBox() {
		
	}
	
	public static PosDeviceCashBox getInstance(){
		if(mInstance==null)
			mInstance=new PosDeviceCashBox();
		return mInstance;
	}
	
	public static boolean testDevice(BeanDevCashBoxConfig config) throws Exception{
		boolean result=true;
		
			PosDeviceCashBox cashBox=new PosDeviceCashBox();
			if(cashBox.initialize(config)){
				cashBox.openCashBox();
				cashBox.shutdown();
			}else
				result=false;	
		return result;
	}
	
	/* (non-Javadoc)
	 * @see com.indocosmo.pos.terminal.devices.PosPortDevice#initialize()
	 */
	@Override
	public boolean initialize() throws Exception {
		
		if(isDeviceInitialized()) return true;
		
		final PosDevCashBoxConfigProvider provider=new PosDevCashBoxConfigProvider();
		final BeanDevCashBoxConfig config=provider.getCashBoxConfig();
		
		return initialize(config);
	}
	
	/**
	 * @param config
	 * @return
	 * @throws Exception
	 */
	public boolean initialize(BeanDevCashBoxConfig config) throws Exception {

		if(config==null)
			throw new Exception("Invalid configuration!");
		
		mCashBoxConfig=config;
		setPortinfo(mCashBoxConfig.getPortInfo());
		return super.initialize();
		
	}
	
	/**
	 * @return
	 */
	public boolean canOpenManually(){
		
		return mCashBoxConfig.isAllowOpen();
	}
	
	
	/**
	 * @throws Exception
	 */
	public void openCashBox() throws Exception {
		
		if(!isDeviceInitialized()) return;
		open();
		write(PosDeviceUtil.stringToByteArr((mCashBoxConfig.getCmdOpen())));
		close();
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.pos.terminal.devices.IPosDeviceBase#shutdown()
	 */
	@Override
	public void shutdown() throws Exception {
		close();
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.pos.terminal.devices.PosSerailPortDevice#onDataRevieved(gnu.io.SerialPortEvent)
	 */
	@Override
	protected void onDataReceived(SerialPortEvent event) {
		// TODO Auto-generated method stub
		
	}

}
