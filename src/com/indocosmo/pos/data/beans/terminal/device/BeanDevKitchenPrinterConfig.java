/**
 * 
 */
package com.indocosmo.pos.data.beans.terminal.device;

/**
 * @author anand
 *
 */
public class BeanDevKitchenPrinterConfig extends BeanDevPrinterConfig {
	
	private boolean isMaster;
	private int kitchenId;

	/**
	 * @return the isMaster
	 */
	public boolean isMaster() {
		return isMaster;
	}

	/**
	 * @param isMaster the isMaster to set
	 */
	public void setAsMaster(boolean isMaster) {
		this.isMaster = isMaster;
	}

	/**
	 * @return the kitchenId
	 */
	public int getKitchenId() {
		return kitchenId;
	}

	/**
	 * @param kitchenId the kitchenId to set
	 */
	public void setKitchenId(int kitchenId) {
		this.kitchenId = kitchenId;
	}

}
