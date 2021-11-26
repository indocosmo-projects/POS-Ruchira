/**
 * 
 */
package com.indocosmo.pos.process.sync.listner;

/**
 * @author jojesh
 *
 */
public interface IPosSyncListenerExt extends IPosSyncListener{
	
	/**
	 * @param isActieve
	 */
	public void setToSyncActieve(boolean isActieve);
	
	/**
	 * @param isActieve
	 */
	public void setFromSyncActieve(boolean isActieve);
}