/**
 * 
 */
package com.indocosmo.pos.data.beans.terminal.device;

/**
 * @author deepak
 *
 */
public class BeanDevEFTConfig {
	
	private String mEFTHostIP;
	private int mEFTHostPort;
	private int mSocketTimeOut;
	private boolean mOpenCashBox;
	
	/**
	 * @return the mEFTHostIP
	 */
	public String getEFTHostIP() {
		return mEFTHostIP;
	}
	/**
	 * @param mEFTHostIP the mEFTHostIP to set
	 */
	public void setEFTHostIP(String mEFTHostIP) {
		this.mEFTHostIP = mEFTHostIP;
	}
	/**
	 * @return the mEFThostPort
	 */
	public int getEFTHostPort() {
		return mEFTHostPort;
	}
	/**
	 * @param mEFThostPort the mEFThostPort to set
	 */
	public void setEFTHostPort(int mEFTHostPort) {
		this.mEFTHostPort = mEFTHostPort;
	}
	/**
	 * @return the mSocketTimeOut
	 */
	public int getSocketTimeOut() {
		return mSocketTimeOut;
	}
	/**
	 * @param mSocketTimeOut the mSocketTimeOut to set
	 */
	public void setSocketTimeOut(int mSocketTimeOut) {
		this.mSocketTimeOut = mSocketTimeOut;
	}
	/**
	 * @return the mOpenCashBox
	 */
	public boolean isOpenCashBox() {
		return mOpenCashBox;
	}
	/**
	 * @param mOpenCashBox the mOpenCashBox to set
	 */
	public void setOpenCashBox(boolean mOpenCashBox) {
		this.mOpenCashBox = mOpenCashBox;
	}
	
}
