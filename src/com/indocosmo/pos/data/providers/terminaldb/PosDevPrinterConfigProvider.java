/**
 * 
 */
package com.indocosmo.pos.data.providers.terminaldb;

import java.util.ArrayList;

import com.indocosmo.pos.data.beans.terminal.device.BeanDevPrinterConfig;

/**
 * @author Jojesh Jose.
 * @since 08th Aug 2012
 */
public abstract class PosDevPrinterConfigProvider<T extends BeanDevPrinterConfig> extends
		PosTerminalDBProviderBase {

 
	public PosDevPrinterConfigProvider(String mTable) {
		super(mTable);
	}

	/**
	 * Returns the printer configuration
	 * 
	 * @return
	 * @throws Exception
	 */
	public ArrayList<T> getPrinters() throws Exception {
		return loadData();
	}

	/**
	 * Loads the printer configuration from database;
	 * 
	 * @return
	 * @throws Exception
	 */
	protected abstract ArrayList<T> loadData() throws Exception;

	/**
	 * Saves the printers configuration to database
	 * 
	 * @param printerSettings
	 */
	public abstract void saveSettings(ArrayList<T> printerConfigs);
	
}
