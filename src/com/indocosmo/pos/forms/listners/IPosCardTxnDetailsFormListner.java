/**
 * 
 */
package com.indocosmo.pos.forms.listners;

import com.indocosmo.pos.terminal.devices.eftpos.messageobjects.EFTResponseMessagePUR;

/**
 * @author deepak
 *
 */
public interface IPosCardTxnDetailsFormListner {
	public abstract void onTxnCompleted(EFTResponseMessagePUR purchaseMessage);

}
