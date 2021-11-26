/**
 * 
 */
package com.indocosmo.pos.data.providers.terminaldb;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.sql.rowset.CachedRowSet;

import com.indocosmo.pos.common.PosLog;
import com.indocosmo.pos.common.enums.device.PrinterType;
import com.indocosmo.pos.data.beans.terminal.device.BeanDevReceiptPrinterConfig;
import com.indocosmo.pos.terminal.devices.printer.PosDevicePrinter;

/**
 * @author anand
 *
 */
public class PosDevReceiptPrinterConfigProvider extends PosTerminalDBProviderBase {

	private static  ArrayList<BeanDevReceiptPrinterConfig> printers;
	/**
	 * @param mTable
	 */
	public PosDevReceiptPrinterConfigProvider() {
		super("receipt_printers");
	}

	public ArrayList<BeanDevReceiptPrinterConfig> getPrinters() throws Exception {
		return loadData();
	}
	/**
	 * @param type
	 * @return
	 * @throws Exception
	 */
	public BeanDevReceiptPrinterConfig getPrinterConfigByType(PrinterType type)throws Exception{
		
		BeanDevReceiptPrinterConfig pintConfig=null;
		getPrinterConfig();
		if(printers!=null && printers.size()>0){
			for(BeanDevReceiptPrinterConfig pc:printers){
				if(pc.getPrinterType()==type){
					pintConfig=pc;
					break;
				}
			}
		}
		
		return pintConfig;
	}
	/**
	 * Return the printer configuration for selected type
	 * 
	 * @param type
	 * @return
	 * @throws Exception
	 */
	public ArrayList<BeanDevReceiptPrinterConfig> getPrinterConfig()throws Exception{
		
		if(printers==null)
			printers = loadData();

		return printers;

	}
	/* (non-Javadoc)
	 * @see com.indocosmo.pos.data.providers.terminaldb.PosDevPrinterConfigProvider#loadData()
	 */
	private ArrayList<BeanDevReceiptPrinterConfig> loadData()
			throws Exception {
		ArrayList<BeanDevReceiptPrinterConfig> printers = null;
		CachedRowSet crs = getData();
		if (crs != null) {
			try {
				printers = new ArrayList<BeanDevReceiptPrinterConfig>();
				while (crs.next()) {
					BeanDevReceiptPrinterConfig printer = new BeanDevReceiptPrinterConfig();
					printer.setPrinterInfo(PosDevicePrinter.getServiceinforFromName(crs.getString("name")));
//					printer.setPrinterType(PosPrinterType.get(crs
//							.getInt("type")));
					printer.setNoCopies(crs.getInt("no_copies"));
					printer.setPaperCutOn(crs.getBoolean("is_paper_cut_on"));
					printer.setPaperCutCode(crs.getString("paper_cut_code"));
					printer.setCashBoxAttached(crs.getBoolean("has_cash_box"));
					printer.setAllowManualCashBoxOpen(crs.getBoolean("allow_manual_open"));
					printer.setCashBoxOpenCode(crs.getString("cash_box_open_code"));
					printer.setUseAltLangugeToPrint(crs.getBoolean("use_alt_language"));
					printer.setActive(crs.getBoolean("is_active"));
					printer.setPrinterType(PrinterType.get(crs.getInt("printer_type")));
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
			ArrayList<BeanDevReceiptPrinterConfig> printerConfigs) throws Exception {
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
					+ "has_cash_box,"
					+ "cash_box_open_code,"
					+ "allow_manual_open,"
					+ "use_alt_language,"
					+ "is_active,"
					+ "printer_type) "
					+ "values" + "(?,?,?,?,?,?,?,?,?,?)";

			prep = mConnection.prepareStatement(sql);
			for(BeanDevReceiptPrinterConfig config:printerConfigs){
				
				prep.setString(1, config.getName());
//				prep.setInt(2,config.getType().getValue());
				prep.setInt(2,config.getNoCopies());
				prep.setBoolean(3,config.isPaperCutOn());
				prep.setString(4,config.getPaperCutCode());
				prep.setBoolean(5,config.isCashBoxAttached());
				prep.setString(6,config.getCashBoxOpenCode());
				prep.setBoolean(7, config.isAllowManualCashBoxOpen());
				prep.setBoolean(8,config.isUseAltLangugeToPrint());
				prep.setBoolean(9, config.isActive());
				prep.setInt(10,config.getPrinterType().getValue());
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
	
//	/**
//	 * Returns the printer configuration for selected printer type
//	 * 
//	 * @param type
//	 * @param printers
//	 * @return
//	 * @throws Exception
//	 */
//	public BeanDevPrinterConfig getPrinterConfig(PosPrinterType type,
//			ArrayList<BeanDevPrinterConfig> printers) throws Exception {
//
//		if (printers != null) {
//			for (BeanDevPrinterConfig printer : printers) {
//				if (printer.getType() == type)
//					return printer;
//			}
//		}
//		return null;
//	}
}

