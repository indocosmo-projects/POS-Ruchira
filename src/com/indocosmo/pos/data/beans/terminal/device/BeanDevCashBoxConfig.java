package com.indocosmo.pos.data.beans.terminal.device;

import com.indocosmo.pos.data.beans.terminal.device.ports.BeanPortBase;

/**
 * @author Jojesh S
 * @since 08th Aug 2012
 **/

public final class BeanDevCashBoxConfig {

	private String mCmdOpen;
	private BeanPortBase mPortInfo;
	
	 
	private boolean mIsAllowOpen=true;

	/**
	 * @return the AllowOpen
	 */
	public boolean isAllowOpen() {
		return mIsAllowOpen;
	}

	/**
	 * @param AllowOpen the mIsOpen to set
	 */
	public void setAllowOpen(boolean mIsOpen) {
		this.mIsAllowOpen = mIsOpen;
	}

	/**
	 * @return the cmdOpen
	 */
	public String getCmdOpen() {
		return mCmdOpen;
	}

	/**
	 * @param cmdOpen
	 *            the cmdOpen to set
	 */
	public void setCmdOpen(String cmdOpen) {
		this.mCmdOpen = cmdOpen;
	}

	/**
	 * @return the PortInfo
	 */
	public BeanPortBase getPortInfo() {
		return mPortInfo;
	}

	/**
	 * @param PortInfo the PortInfo to set
	 */
	public void setPortInfo(BeanPortBase portInfo) {
		this.mPortInfo = portInfo;
	}



}
