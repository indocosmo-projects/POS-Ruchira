/**
 * 
 */
package com.indocosmo.pos.data.beans.terminal.device.ports;

import com.indocosmo.pos.terminal.devices.PosPortDevice.PortType;

/**
 * @author jojesh
 *
 */
public abstract class BeanPortBase {
	
	
	/**
	 * The variable to holed port name 
	 */
	private String mName;
	/**
	 * The variable to hole port type
	 */
	private PortType mType;
	
	
	/**
	 * 
	 */
	public BeanPortBase() {
	 
	}
	/**
	 * 
	 */
	public BeanPortBase(PortType type) {
		mType=type; 
	}

	/**
	 * @return the mName
	 */
	public String getName() {
		return mName;
	}
	/**
	 * @param mName the mName to set
	 */
	public void setName(String name) {
		this.mName = name;
	}
	/**
	 * @return port type
	 */
	public PortType getType() {
		return mType;
	}
	/**
	 * @param port type
	 */
	public void setType(PortType type) {
		this.mType = type;
	}

}
