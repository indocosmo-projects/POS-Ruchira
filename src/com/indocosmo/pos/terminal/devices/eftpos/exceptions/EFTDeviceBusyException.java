/**
 * 
 */
package com.indocosmo.pos.terminal.devices.eftpos.exceptions;

/**
 * @author jojesh-13.2
 *
 */
public class EFTDeviceBusyException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EFTDeviceBusyException(String message){
		super(message);
	}
}
