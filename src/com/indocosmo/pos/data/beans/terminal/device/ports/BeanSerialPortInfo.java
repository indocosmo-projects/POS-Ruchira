/**
 * 
 */
package com.indocosmo.pos.data.beans.terminal.device.ports;

import com.indocosmo.pos.common.enums.device.PortParity;
import com.indocosmo.pos.terminal.devices.PosPortDevice.PortType;

/**
 * @author jojesh
 *
 */
public final class BeanSerialPortInfo extends BeanPortBase{

	private int portBaudRate;
	private int portDataBits;
	private PortParity portParity;
	private int portStopBits;
	/**
	 * @param type
	 */
	public BeanSerialPortInfo() {
		super(PortType.SERIAL);
	}
	/**
	 * @return the portBaudRate
	 */
	public int getPortBaudRate() {
		return portBaudRate;
	}
	/**
	 * @param portBaudRate the portBaudRate to set
	 */
	public void setPortBaudRate(int portBaudRate) {
		this.portBaudRate = portBaudRate;
	}
	
	
	/**
	 * @return the portParity
	 */
	public PortParity getPortParity() {
		return portParity;
	}
	/**
	 * @param portParity the portParity to set
	 */
	public void setPortParity(PortParity portParity) {
		this.portParity = portParity;
	}
	/**
	 * @return the portDataBits
	 */
	public int getPortDataBits() {
		return portDataBits;
	}
	/**
	 * @param portDataBits the portDataBits to set
	 */
	public void setPortDataBits(int portDataBits) {
		this.portDataBits = portDataBits;
	}
	/**
	 * @return the portStopBits
	 */
	public int getPortStopBits() {
		return portStopBits;
	}
	/**
	 * @param portStopBits the portStopBits to set
	 */
	public void setPortStopBits(int portStopBits) {
		this.portStopBits = portStopBits;
	}

}
