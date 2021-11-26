/**
 * 
 */
package com.indocosmo.pos.terminal.devices.weighingmachine;

import gnu.io.SerialPortEvent;

import java.util.ArrayList;

import com.indocosmo.pos.common.PosLog;
import com.indocosmo.pos.common.utilities.PosNumberUtil;
import com.indocosmo.pos.data.beans.terminal.device.BeanDevWMConfig;
import com.indocosmo.pos.data.beans.terminal.device.BeanDevWMConfig.OperationMode;
import com.indocosmo.pos.data.providers.terminaldb.PosDevWMConfigProvider;
import com.indocosmo.pos.terminal.devices.PosDeviceUtil;
import com.indocosmo.pos.terminal.devices.PosSerailPortDevice;

/**
 * @author jojesh-13.2
 *
 */
public class PosDeviceWM extends PosSerailPortDevice {

	private static PosDeviceWM mInstance;
	private BeanDevWMConfig wmConfig;
	private ArrayList<IPosDevWMListener> listeners;


	/**
	 * @return
	 */
	public static PosDeviceWM getInstance(){
		if(mInstance==null)
			mInstance=new PosDeviceWM();
		return mInstance;
	}

	private PosDeviceWM(){

		listeners=new ArrayList<IPosDevWMListener>();
	}



	/**
	 * @param config
	 * @return
	 * @throws Exception
	 */
	public static String testDevice(BeanDevWMConfig config) throws Exception{

		String value="NO DATA RECEIVED!!!";

		PosDeviceWM device=new PosDeviceWM();

		if(device.initialize(config)){

			final double res=device.requestForWeight();

			if(res!=-1){

				value="DATA RECEIVED ["+res+"]";
			}

			device.shutdown();

		}

		return value;
	}



	/**
	 * @throws Exception 
	 * 
	 */
	public double requestForWeight() throws Exception{

		double weight=-1;

		initialize();
		open();
		
		write(PosDeviceUtil.stringToByteArr((wmConfig.getCmdRequestValue())));

		if(wmConfig.getOperationMode()==OperationMode.AUTO){

			Thread.sleep(200);
			final String value = readLine();
			weight=PosNumberUtil.parseDoubleSafely(value);
		}

		shutdown();
		return weight;
	}


	/* (non-Javadoc)
	 * @see com.indocosmo.pos.terminal.devices.PosPortDevice#initialize()
	 */
	@Override
	public boolean initialize() throws Exception {

		if(isDeviceInitialized()) shutdown();
		
		final PosDevWMConfigProvider provider=new PosDevWMConfigProvider();
		final BeanDevWMConfig config=provider.getWMConfig();

		return initialize(config);
	}

	/**
	 * @param config
	 * @return
	 * @throws Exception
	 */
	public boolean initialize(BeanDevWMConfig config) throws Exception {

		if(config==null)
			throw new Exception("Invalid configuration!");

		wmConfig=config;
		setPortinfo(wmConfig.getPortInfo());
		setNotifyOnData(config.getOperationMode()==OperationMode.MANUAL);
		return super.initialize();

	}

	/* (non-Javadoc)
	 * @see com.indocosmo.pos.terminal.devices.PosPortDevice#onInitCompleted()
	 */
	@Override
	protected void onInitCompleted() throws Exception {

//		open();
	}

	/**
	 * @author jojesh-13.2
	 *
	 */
	public interface IPosDevWMListener{

		public boolean onWeightRecived(double weight);
	}

	/**
	 * @param listener
	 */
	public void addListener(IPosDevWMListener listener){

		listeners.add(listener);
	}

	/**
	 * @param listener
	 */
	public void removeListener(IPosDevWMListener listener){

		listeners.remove(listener);
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.pos.terminal.devices.PosSerailPortDevice#onDataReceived(gnu.io.SerialPortEvent)
	 */
	@Override
	protected void onDataReceived(SerialPortEvent event) {

		try {

			final String value = readLine();

			PosLog.debug("WM:onDataReceived => " + value);

			if(value!=null && value.trim().length()>0){

				double weight=PosNumberUtil.parseDoubleSafely(value);

				if(listeners!=null && listeners.size()>0){

					for(IPosDevWMListener listner:listeners){

						listner.onWeightRecived(weight);
					}
				}
			}

		} catch (Exception e) {

			PosLog.write(this, "onDataReceived", e);
		}

	}


}
