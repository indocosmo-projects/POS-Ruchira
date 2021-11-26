package com.indocosmo.pos.forms.components.terminal.settings.devices;

import java.util.ArrayList;

import javax.swing.JDialog;

import com.indocosmo.pos.common.PosLog;
import com.indocosmo.pos.common.enums.PosPrinterType;
import com.indocosmo.pos.common.utilities.PosFormUtil;
import com.indocosmo.pos.data.beans.terminal.BeanTerminalSetting;
import com.indocosmo.pos.data.beans.terminal.device.BeanDevKitchenPrinterConfig;
import com.indocosmo.pos.data.beans.terminal.device.BeanDevReceiptPrinterConfig;
import com.indocosmo.pos.data.providers.shopdb.PosKitchenProvider;
import com.indocosmo.pos.data.providers.terminaldb.PosDevKitchenPrinterConfigProvider;
import com.indocosmo.pos.data.providers.terminaldb.PosDevReceiptPrinterConfigProvider;
import com.indocosmo.pos.data.providers.terminaldb.PosDevSettingProvider.PosDevices;
import com.indocosmo.pos.data.providers.terminaldb.PosTerminalSettingsProvider;
import com.indocosmo.pos.forms.components.terminal.settings.devices.printers.PosKitchenPrinterServiceSelectionPanel;
import com.indocosmo.pos.forms.components.terminal.settings.devices.printers.PosReceiptPrinterServiceSelectionPanel;

/**
 * @author Jojesh Jose
 * @since 08th Aug 2012
 **/

@SuppressWarnings("serial")
public final class PosDevPrinterSettingTab extends PosDeviceSettingsTab {

	/**
	 * The Kitchen Printer config bean object
	 */
//	private BeanDevKitchenPrinterConfig mKitchenPrinterConfig;

	/**
	 * Printer configuration provider
	 **/
	private PosDevKitchenPrinterConfigProvider mKitchenPrinterConfigProvider;

	/**
	 * Kitchen Provider
	 **/
	private PosKitchenProvider mKitchenProvider;


	/**
	 * The Receipt Printer config bean object
	 */
//	private BeanDevReceiptPrinterConfig mReceiptPrinterConfig;

	/**
	 * Printer configuration provider
	 **/
	private PosDevReceiptPrinterConfigProvider mReceiptPrinterConfigProvider;
	/**
	 * The receipt printer panel object
	 */
	private PosReceiptPrinterServiceSelectionPanel mReceiptPrinterPanel;
	
	
	private PosTerminalSettingsProvider mterminalSettingProvider;
	
	/**
	 * The kitchen printer panel;
	 * 
	 * @param parent
	 */
	private PosKitchenPrinterServiceSelectionPanel mKitchenPrinterPanel;

//	private BeanTerminalSetting terminalSettings;

	/**
	 * The class constructor
	 * 
	 * @param parent
	 */
	public PosDevPrinterSettingTab(JDialog parent) {
		super(parent, "Printer", PosDevices.RECEIPTPRINTER, false,
				LAYOUT_WIDTH, LAYOUT_HEIGHT);
	}

	/**
	 * Initilizes the controls
	 */
	@Override
	protected void initControls() {

		//		mPrinterConfigProvider = new PosDevPrinterConfigProvider();
		initProviders();
		createFieldReceiptPrinter();
		createFieldKitchenPrinter();
		setPrinterDetails();
		//		createInfoPanel();
	}

	/**
	 * 
	 */
	private void initProviders() {

		mReceiptPrinterConfigProvider = new PosDevReceiptPrinterConfigProvider();
		mKitchenPrinterConfigProvider = new PosDevKitchenPrinterConfigProvider();
		mterminalSettingProvider=new PosTerminalSettingsProvider();
		mKitchenProvider = PosKitchenProvider.getInstance();
	}

	/**
	 * Creates the receipt printer panel
	 */
	private void createFieldReceiptPrinter() {
		try{
			mReceiptPrinterPanel = new PosReceiptPrinterServiceSelectionPanel(mParent,
					PosPrinterType.RECEIPT, getWidth());
			mReceiptPrinterPanel.setLocation(0,0);
			mReceiptPrinterPanel.setAllowCashBoxOperations(true);
			mReceiptPrinterPanel.setAllowMultipleCopies(false);
			mReceiptPrinterPanel.setPrinterConfigList(mReceiptPrinterConfigProvider.getPrinters());
			mReceiptPrinterPanel.setprinterDetails();
			add(mReceiptPrinterPanel);
		}catch(Exception e){
			PosLog.write(this, "CreateFieldReceiptPrinter()", e);
		}
	}

	/**
	 * Creates the Kitchen printer panel
	 */
	private void createFieldKitchenPrinter() {
		try{
			mKitchenPrinterPanel = new PosKitchenPrinterServiceSelectionPanel(mParent,
					PosPrinterType.KITCHEN, getWidth());
			mKitchenPrinterPanel.setAllowMultipleCopies(true);
			mKitchenPrinterPanel.setLocation(0,
					mReceiptPrinterPanel.getY() + mReceiptPrinterPanel.getHeight()
					+ PANEL_CONTENT_V_GAP);
			mKitchenPrinterPanel.setPrinterConfigList(mKitchenPrinterConfigProvider.getPrinters());
			mKitchenPrinterPanel.setKitchenList(mKitchenProvider.getKitchens());
			mKitchenPrinterPanel.setprinterDetails();
			add(mKitchenPrinterPanel);
		}catch(Exception e){
			PosLog.write(this, "CreateFieldKitchenPrinter", e);
		}
	}

	/**
	 * set the printer fields
	 */
	private void setPrinterDetails() {

		try {

			mReceiptPrinterPanel.setHasPrinter(mDeviceSettingProvider
					.isDeviceAttached(PosDevices.RECEIPTPRINTER));
			mKitchenPrinterPanel.setHasPrinter(mDeviceSettingProvider
					.isDeviceAttached(PosDevices.KITCHENPRINTER));

		} catch (Exception err) {
			PosLog.write(this, "setPrinterDetails", err);
			PosFormUtil.showErrorMessageBox(mParent,
					"Error in printer settings. Please contact Administrator.");
		}
	}

	/**
	 * Creates the info panel
	 */
	private void createInfoPanel() {
		
		String info = "<html>Select the receipt/kitchen printer. Once you selected it, "
				+ "test the printers using the printer button at the right side. It will "
				+ "send some text to the printer. <font color=#FF0000>Please make sure that "
				+ "the printer is on and paper is loaded</font>.</html>";
		setInfoPanel(info);
	}

	@Override
	protected void setDeviceEnabled(boolean enable) {

		// mTxtReceiptPrinter.setEditable(enable);

	}

	@Override
	public boolean onValidating() {
		
		if (!mKitchenPrinterPanel.validatePrinter()) {
			return false;
		}
		if (!mReceiptPrinterPanel.validatePrinter()) {
			return false;
		}
		return true;
	}

	/**
	 * Saves the configuration to the database
	 */
	@Override
	public boolean onSave() {

		final ArrayList<BeanDevReceiptPrinterConfig> receiptPrinterConfigurations = new ArrayList<BeanDevReceiptPrinterConfig>();
		final ArrayList<BeanDevKitchenPrinterConfig> kitchenPrinterConfigurations = new ArrayList<BeanDevKitchenPrinterConfig>();

		final ArrayList<BeanDevReceiptPrinterConfig> receptPrinterConfig = mReceiptPrinterPanel.getPrinterConfigList();
		for (BeanDevReceiptPrinterConfig printerConfig:receptPrinterConfig){
			if (printerConfig.getPrinterInfo() != null) 
				receiptPrinterConfigurations.add(printerConfig);

		}

		final ArrayList<BeanDevKitchenPrinterConfig> kitchenPrinterConfig = mKitchenPrinterPanel.getPrinterConfigList();
		for (BeanDevKitchenPrinterConfig printerConfig:kitchenPrinterConfig){

			if(printerConfig.getPrinterInfo()!=null)
				kitchenPrinterConfigurations.add(printerConfig);
		}
		
		try{

		mReceiptPrinterConfigProvider.saveSettings(receiptPrinterConfigurations);
		mKitchenPrinterConfigProvider.saveSettings(kitchenPrinterConfigurations);
//		mterminalSettingProvider.setKitchenReceiptAtPayment(mKitchenPrinterPanel.isPrintWithReceipt());
		mDeviceSettingProvider.setDeviceAttached(PosDevices.KITCHENPRINTER,
				mKitchenPrinterPanel.hasPrinter());
		mDeviceSettingProvider.setDeviceAttached(PosDevices.RECEIPTPRINTER,
				mReceiptPrinterPanel.hasPrinter());

		return true;
		
		}catch(Exception e){
			
			PosLog.write(this, "onSave", e);
			
			PosFormUtil.showErrorMessageBox(mParent, "Failed to save settings. Please contact administrator.");
			

			return false;
		}
	}

	@Override
	public void onGotFocus() {

	}

	/**
	 * @param terminalSettings
	 */
	public void setTerminalSettings(BeanTerminalSetting terminalSettings) {
		
		super.setTerminalSettings(terminalSettings);
		
		// Enbale the print to kitchen at payment only if the service type is Counter
//		if(terminalSettings.getTerminalInfo()!=null)
//			mKitchenPrinterPanel.setPrintWithReceiptEnable(terminalSettings.getTerminalInfo().getType()==PosTerminalServiceType.Counter);
//		mKitchenPrinterPanel.setPrintWithReceipt(terminalSettings.isPrintKitchenReceiptWithPayment());
	}
}
