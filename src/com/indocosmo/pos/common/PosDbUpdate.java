/**
 * 
 */
package com.indocosmo.pos.common;

import com.indocosmo.pos.data.providers.shopdb.PosShopDbUpdate;
import com.indocosmo.pos.data.providers.terminaldb.PosTerminalDbUpdate;
import com.indocosmo.pos.terminal.devices.PosDeviceManager;

/**
 * @author deepak
 * 
 */
public class PosDbUpdate {

	PosTerminalDbUpdate terminalDbUpdate = new PosTerminalDbUpdate();
	PosShopDbUpdate shopDbUpdate = new PosShopDbUpdate();

	/**
	 * 
	 */
	public void checkAndUpdateDbs() {
		boolean changed = false;
		int terminalDbVersion = terminalDbUpdate.getTerminalDbVersion();
		int shopDbVersion = shopDbUpdate.getShopDbVersion();
		switch (terminalDbVersion) {
		case 0:
			terminalDbUpdate.terminalUpdate("CREATE TABLE terminal_db_version ('version' INTEGER)");
			terminalDbUpdate.updateVersion(1);
			changed = true;
			break;
		case 1:
			terminalDbUpdate.updateVersion(0);
		default:
			break;
		}

		switch (shopDbVersion) {
		case 0:
			shopDbUpdate.shopUpdate("CREATE TABLE shop_db_version ('version' INTEGER)");
			shopDbUpdate.updateVersion(1);
			changed = true;
			break;

		default:
			break;
		}
		if(changed){
			doExit();
		}
	}
	private void doExit()  {
		if (PosDeviceManager.getInstance() != null)
			try {
				PosDeviceManager.getInstance().shutdownDevices();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		System.exit(0);
	}
}
