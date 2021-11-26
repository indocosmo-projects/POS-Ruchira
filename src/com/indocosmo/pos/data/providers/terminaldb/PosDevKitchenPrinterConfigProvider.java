/**
 * 
 */
package com.indocosmo.pos.data.providers.terminaldb;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.sql.rowset.CachedRowSet;

import com.indocosmo.pos.common.PosLog;
import com.indocosmo.pos.data.beans.terminal.device.BeanDevKitchenPrinterConfig;
import com.indocosmo.pos.terminal.devices.printer.PosDevicePrinter;

/**
 * @author anand
 *
 */
public class PosDevKitchenPrinterConfigProvider extends
		PosTerminalDBProviderBase {

	/**
	 * @param mTable
	 */
	public PosDevKitchenPrinterConfigProvider() {
		super("kitchen_printers");
	}

	public ArrayList<BeanDevKitchenPrinterConfig> getPrinters() throws Exception {
		return loadData();
	}
	/**
	 * Return the printer configuration for selected type
	 * 
	 * @param type
	 * @return
	 * @throws Exception
	 */
	public ArrayList<BeanDevKitchenPrinterConfig> getPrinterConfig()throws Exception{
		
		final ArrayList<BeanDevKitchenPrinterConfig> printers = loadData();
		return printers;
	}
	

	/**
	 * Returns the printer configuration for selected kitchen
	 * 
	 * @param type
	 * @param printers
	 * @return
	 * @throws Exception
	 * 
	 */
	public BeanDevKitchenPrinterConfig getPrinterConfig(int kitchenId,
			ArrayList<BeanDevKitchenPrinterConfig> printers) throws Exception {

		if (printers != null) {
			for (BeanDevKitchenPrinterConfig printer : printers) {
				if (printer.getKitchenId() == kitchenId)
					return printer;
			}
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.pos.data.providers.terminaldb.PosDevPrinterConfigProvider#loadData()
	 */
	private ArrayList<BeanDevKitchenPrinterConfig> loadData()
			throws Exception {
		ArrayList<BeanDevKitchenPrinterConfig> printers = null;
		CachedRowSet crs = getData();
		if (crs != null) {
			try {
				printers = new ArrayList<BeanDevKitchenPrinterConfig>();
				while (crs.next()) {
					BeanDevKitchenPrinterConfig printer = new BeanDevKitchenPrinterConfig();
					printer.setPrinterInfo(PosDevicePrinter.getServiceinforFromName(crs.getString("name")));
//					printer.setPrinterType(PosPrinterType.get(crs
//							.getInt("type")));
					printer.setNoCopies(crs.getInt("no_copies"));
					printer.setPaperCutOn(crs.getBoolean("is_paper_cut_on"));
					printer.setPaperCutCode(crs.getString("paper_cut_code"));
					printer.setKitchenId(crs.getInt("kitchen_id"));
					printer.setUseAltLangugeToPrint(crs.getBoolean("use_alt_language"));
					printer.setActive(crs.getBoolean("is_active"));
					printer.setAsMaster(crs.getBoolean("is_master"));
					printers.add(printer);
				}
			} catch (Exception err) {
				printers = null;
				throw err;
			} finally {
				try {
					crs.close();
				} catch (SQLException e1) {
					PosLog.write(this, "loadData", e1);
				}
			}
		}
		return printers;
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.pos.data.providers.terminaldb.PosDevPrinterConfigProvider#saveSettings(java.util.ArrayList)
	 */
	public void saveSettings(
			ArrayList<BeanDevKitchenPrinterConfig> printerConfigs) throws Exception {
		try {
			beginTrans();
			deleteData();
			PreparedStatement prep;

			final String sql = "insert into " + mTablename + " ("
					+ "name,"
//					+ "type,"
					+ "no_copies,"
					+ "is_paper_cut_on,"
					+ "paper_cut_code,"
					+ "is_master,"
					+ "kitchen_id,"
					+ "use_alt_language,"
					+ "is_active) "
					+ "values" + "(?,?,?,?,?,?,?,?)";

			prep = mConnection.prepareStatement(sql);
			for(BeanDevKitchenPrinterConfig config:printerConfigs){
				
				prep.setString(1, config.getName());
//				prep.setInt(2,config.getType().getValue());
				prep.setInt(2,config.getNoCopies());
				prep.setBoolean(3,config.isPaperCutOn());
				prep.setString(4,config.getPaperCutCode());
				prep.setBoolean(5,config.isMaster());
				prep.setInt(6,config.getKitchenId());
				prep.setBoolean(7,config.isUseAltLangugeToPrint());
				prep.setBoolean(8, config.isActive());
				prep.addBatch();
			}
			prep.executeBatch();
			commitTrans();

		} catch (Exception e) {
			
			PosLog.write(this, "saveSettings", e);
			try {
				rollBack();
			} catch (SQLException e1) {
				PosLog.write(this, "saveSettings", e);
			}
			
			throw new Exception("Failed to save setting.");
		}
		
	}
}
