/**
 * 
 */
package com.indocosmo.pos.terminal.devices;

import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;

import com.indocosmo.pos.common.PosLog;
import com.indocosmo.pos.data.beans.terminal.device.ports.BeanSerialPortInfo;

/**
 * @author jojesh-13.2
 *
 */
public abstract class PosSerailPortDevice extends PosPortDevice implements SerialPortEventListener{

	private boolean notifyOnData=false;
	/**
	 * 
	 */
	public PosSerailPortDevice() {
		
		super(PortType.SERIAL);
	}

	
	/**
	 * @param b
	 */
	protected void setNotifyOnData(boolean notify) {
		
		notifyOnData=notify;
	}
	
	/* (non-Javadoc)
	 * @see com.indocosmo.pos.terminal.devices.PosPortDevice#setupPort()
	 */
	protected void setupPort() throws Exception {

		final SerialPort mSerialPort = (SerialPort) mCommPort;
		final BeanSerialPortInfo serialPortInfo = (BeanSerialPortInfo) mPortInfo;
		mSerialPort.setSerialPortParams(serialPortInfo.getPortBaudRate(),
				serialPortInfo.getPortDataBits(), serialPortInfo
				.getPortStopBits(), serialPortInfo.getPortParity()
				.getValue());
		mSerialPort.setFlowControlMode(SerialPort.FLOWCONTROL_NONE);
		mSerialPort.notifyOnDataAvailable(notifyOnData);
		mSerialPort.notifyOnBreakInterrupt(true);
		mSerialPort.addEventListener(this);
		
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.pos.terminal.devices.PosPortDevice#setSystemParams()
	 */
	@Override
	protected void setSystemParams() {

		System.setProperty("gnu.io.rxtx.SerialPorts", mPortInfo.getName());
	}


	/* (non-Javadoc)
	 * @see gnu.io.SerialPortEventListener#serialEvent(gnu.io.SerialPortEvent)
	 */
	@Override
	public void serialEvent(SerialPortEvent event) {
		
		try{

			switch(event.getEventType()) {
			case SerialPortEvent.BI:
				close();
				break;
			case SerialPortEvent.OE:
			case SerialPortEvent.FE:
			case SerialPortEvent.PE:
			case SerialPortEvent.CD:
			case SerialPortEvent.CTS:
			case SerialPortEvent.DSR:
			case SerialPortEvent.RI:
			case SerialPortEvent.OUTPUT_BUFFER_EMPTY:
				break;
			case SerialPortEvent.DATA_AVAILABLE:

				onDataReceived(event);

			}
		
		}catch (Exception ex){
			
			PosLog.write(this, "serialEvent", ex);
		}
		
	}

	/**
	 * @param event
	 */
	protected void onDataReceived(SerialPortEvent event){};
	
	/**
	 * @throws Exception 
	 * 
	 */
	protected void onBreakInterrupt() throws Exception{
		
		close();
	}
}
