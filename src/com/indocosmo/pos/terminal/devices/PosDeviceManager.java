package com.indocosmo.pos.terminal.devices;

import java.util.ArrayList;

import com.indocosmo.pos.common.PosEnvSettings;
import com.indocosmo.pos.common.PosLog;
import com.indocosmo.pos.common.enums.device.PrinterType;
import com.indocosmo.pos.common.utilities.PosFormUtil;
import com.indocosmo.pos.data.beans.terminal.device.BeanDevKitchenPrinterConfig;
import com.indocosmo.pos.data.beans.terminal.device.BeanDevReceiptPrinterConfig;
import com.indocosmo.pos.data.beans.terminal.device.BeanDeviceSetting;
import com.indocosmo.pos.data.providers.shopdb.PosKitchenProvider;
import com.indocosmo.pos.data.providers.terminaldb.PosDevKitchenPrinterConfigProvider;
import com.indocosmo.pos.data.providers.terminaldb.PosDevReceiptPrinterConfigProvider;
import com.indocosmo.pos.data.providers.terminaldb.PosDevSettingProvider;
import com.indocosmo.pos.data.providers.terminaldb.PosDevSettingProvider.PosDevices;
import com.indocosmo.pos.terminal.devices.cashbox.PosDeviceCashBox;
import com.indocosmo.pos.terminal.devices.poledisplay.PosDevicePoleDisplay;
import com.indocosmo.pos.terminal.devices.printer.PosDeviceKitchenPrinter;
import com.indocosmo.pos.terminal.devices.printer.PosDeviceReceiptPrinter;
import com.indocosmo.pos.terminal.devices.weighingmachine.PosDeviceWM;

public final class PosDeviceManager {

	private static PosDeviceManager mClassInstance;

	private ArrayList<PosDeviceBase> mDeviceList;

	private PosDevicePoleDisplay mPosDevPoleDisplay;
	private boolean mIsOkPosDevicePoleDisplay = false;

	private ArrayList<PosDeviceReceiptPrinter> mPosDevReceiptPrinter;
	private PosDevReceiptPrinterConfigProvider mReceiptPrinterConfigProvider;
	private boolean mIsOkPosDeviceReceiptPrinter = false;

//	private PosDeviceKitchenPrinter mPosDevKitchenPrinter;
	private ArrayList<PosDeviceKitchenPrinter> mPosDevKitchenPrinters;
	private PosDevKitchenPrinterConfigProvider mKitchenPrinterConfigProvider;

	private boolean mIsOkPosDeviceKitchenPrinter = false;

	private PosDeviceCashBox mPosDevCashBox;
	private boolean mIsOkPosDeviceCashBox = false;
	
	private PosDeviceWM mPosDevWeighing;
	private boolean mIsOkPosDeviceWeighing = false;

	private BeanDeviceSetting mDeviceSettings;

	private PosDeviceManager() {
		
		mDeviceList = new ArrayList<PosDeviceBase>();
		mPosDevReceiptPrinter=new ArrayList<PosDeviceReceiptPrinter>();
		mPosDevKitchenPrinters=new ArrayList<PosDeviceKitchenPrinter>();
		
		mReceiptPrinterConfigProvider=new PosDevReceiptPrinterConfigProvider();
		mKitchenPrinterConfigProvider=new PosDevKitchenPrinterConfigProvider();
	}

	/**
	 * @return
	 * @throws Exception
	 */
	public boolean startupDevices()throws Exception{
		
		return startupDevices(true, true, true,true);
	}
	
	/**
	 * @param initPole
	 * @param initCashDrawer
	 * @param initPrinters
	 * @param initWM
	 * @return
	 * @throws Exception
	 */
	public boolean startupDevices(boolean initPole,boolean initCashDrawer,boolean initPrinters,boolean initWM) throws Exception {
	
		try {
			
			PosDevSettingProvider deviceSettingProvider = new PosDevSettingProvider();
			mDeviceSettings = deviceSettingProvider.getDeviceSettings();
			deviceSettingProvider = null;

			if (mListener != null) {
				mListener.onMessage("Initializing devices...");
			}

			if (mDeviceSettings.isAttachedPoleDisplay()&&initPole) {
				initPoleDisplay();
			}

			if(initPrinters)
				initPrinters();

			if (mDeviceSettings.isAttachedCashDrawer()&&initCashDrawer) {
				initCashBox();
			}
			
			if (mDeviceSettings.isAttachedWeighing()&&initWM) {
				initWM();
			}

			if (mListener != null) {
				mListener.onMessage("Device initialization completed.");
			}

		} catch (Exception e) {
			PosLog.write(this, "startupDevices", e);
			return false;
		}
		return true;
	}

	/**
	 * @return
	 */
	public static PosDeviceManager getInstance() {
		
		if (mClassInstance == null)
			mClassInstance = new PosDeviceManager();
		return mClassInstance;
	}

	/**
	 * @return
	 */
	public boolean initPoleDisplay() {

		mIsOkPosDevicePoleDisplay = false;

		if (mListener != null) {
			mListener.onInitStarted(PosDevices.POLEDISPLAY,
					"Initializing Pole Display...");
		}

		if (mDeviceSettings.isAttachedPoleDisplay()) {
			mPosDevPoleDisplay = PosDevicePoleDisplay.getInstance();
			mDeviceList.add(mPosDevPoleDisplay);
		}

		if (mPosDevPoleDisplay != null) {
			try {
				mIsOkPosDevicePoleDisplay = mPosDevPoleDisplay.initialize();
			} catch (Exception e) {
				PosLog.write(this, "initPoleDisplay", e);
			}
		}

		if (mIsOkPosDevicePoleDisplay) {
			if (mListener != null) {
				mListener.onInitSuccess(PosDevices.POLEDISPLAY,
						"Pole Display initialized successfully.");
			}
		} else {
			if (mListener != null) {
				mListener.onInitFailed(PosDevices.POLEDISPLAY,
						"Failed to initialize Pole Display.");
			}
		}

		return mIsOkPosDevicePoleDisplay;
	}

	/**
	 * @return
	 * @throws Exception
	 */
	public boolean initPrinters() throws Exception {

		if (mDeviceSettings.isAttachedKitchenPrinter()) {
			initKitchenPrinter();
		}

		if (mDeviceSettings.isAttachedReceiptPrinter()) {
			initReceiptPrinter();
		}

		return (mDeviceSettings.isAttachedKitchenPrinter() && mIsOkPosDeviceKitchenPrinter)
				&& (mDeviceSettings.isAttachedReceiptPrinter() && mIsOkPosDeviceReceiptPrinter);
	}

	/**
	 * @return
	 */
//	public boolean _initReceiptPrinter() {
//
//		if (mListener != null) {
//			mListener.onInitStarted(PosDevices.RECEIPTPRINTER,
//					"Initializing Receipt Printer...");
//		}
//
//		if (mDeviceSettings.isAttachedReceiptPrinter()) {
//			try {
//				//since only a single receipt printer will be there
//				mPosDevReceiptPrinter.initialize(mReceiptPrinterConfigProvider.getPrinterConfig());
//			} catch (Exception e) {
//				PosLog.write(this, "initReceiptPrinter", e);
//				e.printStackTrace();
//			}
//			mDeviceList.add(mPosDevReceiptPrinter);
//		}
//		
//		if (mPosDevReceiptPrinter != null) {
//				
//			mIsOkPosDeviceReceiptPrinter = mPosDevReceiptPrinter.isDeviceInitialized();
//				
//		}
//
//		if (mIsOkPosDeviceReceiptPrinter) {
//			if (mListener != null) {
//				mListener.onInitSuccess(PosDevices.RECEIPTPRINTER,
//						"Receipt printer '"
//						+ mPosDevReceiptPrinter.getPrinterServiceInfo().getName()
//						+ "' initialized successfully.");
//			}
//		} else {
//			if (mListener != null) {
//				mListener.onInitFailed(PosDevices.RECEIPTPRINTER,
//						"Failed to initialize '"
//						+ mPosDevReceiptPrinter.getPrinterServiceInfo().getName()
//						+ "' receipt printer.");
//			}
//		}
//
//		return mIsOkPosDeviceReceiptPrinter;
//	}
	
	/**
	 * @return
	 * @throws Exception
	 */
	public boolean initReceiptPrinter() throws Exception {

		if (mListener != null) {
			mListener.onInitStarted(PosDevices.RECEIPTPRINTER,
					"Initializing Receipt Printer...");
		}

		mPosDevReceiptPrinter.clear();

		if (mDeviceSettings.isAttachedReceiptPrinter()) {

			final ArrayList<BeanDevReceiptPrinterConfig> receiptPrinterConfigList = mReceiptPrinterConfigProvider.getPrinterConfig();
			for(BeanDevReceiptPrinterConfig printerConfig:receiptPrinterConfigList){

				try {

					PosDeviceReceiptPrinter receiptPrinter=new PosDeviceReceiptPrinter();
					receiptPrinter.initialize(printerConfig);

					if(receiptPrinter.isDeviceInitialized()){

						mPosDevReceiptPrinter.add(receiptPrinter);

						if (mListener != null){ 

							mListener.onInitSuccess(PosDevices.RECEIPTPRINTER,
									"Receipt printer '"
											+receiptPrinter.getPrinterServiceInfo().getName()
											+" has been initialized successfully.");

						}

					}else {

						if(mListener != null){

							mListener.onInitFailed(PosDevices.RECEIPTPRINTER,
									"Failed to initialize Receipt printer '"
											+ receiptPrinter.getPrinterServiceInfo().getName()
											+".");
						}
					}
				}catch (Exception e) {

					PosLog.write(this, "initReceiptPrinter", e);
				}
			}
			mDeviceList.addAll(mPosDevReceiptPrinter);
		}

		if (mPosDevReceiptPrinter.size()>0)
			mIsOkPosDeviceKitchenPrinter = true;

		return mIsOkPosDeviceKitchenPrinter;
	}

	/**
	 * @return
	 * @throws Exception
	 */
	public boolean initKitchenPrinter() throws Exception {

		if (mListener != null) {
			mListener.onInitStarted(PosDevices.KITCHENPRINTER,
					"Initializing Kitchen Printer...");
		}

		mPosDevKitchenPrinters.clear();

		if (mDeviceSettings.isAttachedKitchenPrinter()) {

			final ArrayList<BeanDevKitchenPrinterConfig> kitchenPrinterConfigList = mKitchenPrinterConfigProvider.getPrinters();
			for(BeanDevKitchenPrinterConfig printerConfig:kitchenPrinterConfigList){

				try {

					PosDeviceKitchenPrinter kitchenPrinter=new PosDeviceKitchenPrinter();
					kitchenPrinter.initialize(printerConfig);

					if(kitchenPrinter.isDeviceInitialized()){

						mPosDevKitchenPrinters.add(kitchenPrinter);

						if (mListener != null){ 

							mListener.onInitSuccess(PosDevices.KITCHENPRINTER,
									"Kitchen printer '"
											+kitchenPrinter.getPrinterServiceInfo().getName()
											+"' for kitchen '"+ PosKitchenProvider.getInstance().getKitchenName(kitchenPrinter.getKitchenId()) 
											+"' has been initialized successfully.");

						}

					}else {

						if(mListener != null){

							mListener.onInitFailed(PosDevices.KITCHENPRINTER,
									"Failed to initialize kitchen printer '"
											+ kitchenPrinter.getPrinterServiceInfo().getName()
											+" for kitchen '"+ PosKitchenProvider.getInstance().getKitchenName(kitchenPrinter.getKitchenId()) 
											+"'.");
						}
					}

				}catch (Exception e) {

					e.printStackTrace();
				}
			}
			mDeviceList.addAll(mPosDevKitchenPrinters);
		}

		if (mPosDevKitchenPrinters.size()>0)
			mIsOkPosDeviceKitchenPrinter = true;

		//		if (mIsOkPosDeviceKitchenPrinter) {
		//			if (mListener != null) {
		//				mListener.onInitSuccess(PosDevices.KITCHENPRINTER,
		//						"Kitchen Printer initialized successfully.");
		//			}
		//		} else {
		//			if (mListener != null) {
		//				mListener.onInitFailed(PosDevices.KITCHENPRINTER,
		//						"Failed to initialize Kitchen Printer.");
		//			}
		//		}

		return mIsOkPosDeviceKitchenPrinter;
	}

	/**
	 * @return
	 */
	public boolean initCashBox() {

		if (mListener != null) {
			mListener.onInitStarted(PosDevices.CASHBOX,
					"Initializing Cash Box...");
		}

		if (mDeviceSettings.isAttachedCashDrawer()) {
			mPosDevCashBox = PosDeviceCashBox.getInstance();
			mDeviceList.add(mPosDevCashBox);
		}

		if (mPosDevCashBox != null) {
			try {
				mIsOkPosDeviceCashBox = mPosDevCashBox.initialize();
			} catch (Exception e) {
				PosLog.write(this, "initCashBox", e);
			}
		}

		if (mIsOkPosDeviceCashBox) {
			if (mListener != null) {
				mListener.onInitSuccess(PosDevices.CASHBOX,
						"Cash Box initialized successfully.");
			}
		} else {
			if (mListener != null) {
				mListener.onInitFailed(PosDevices.CASHBOX,
						"Failed to initialize Cash Box.");
			}
		}

		return mIsOkPosDeviceCashBox;
	}
	
	/**
	 * @return
	 */
	public boolean initWM() {

		if (mListener != null) {
			mListener.onInitStarted(PosDevices.WEIGHINGMACHINE,
					"Initializing Weighing Machine...");
		}

		if (mDeviceSettings.isAttachedWeighing()) {
			mPosDevWeighing = PosDeviceWM.getInstance();
			mDeviceList.add(mPosDevWeighing);
		}

		if (mPosDevWeighing != null) {
			try {
				mIsOkPosDeviceWeighing = mPosDevWeighing.initialize();
			} catch (Exception e) {
				PosLog.write(this, "initWM", e);
			}
		}

		if (mIsOkPosDeviceWeighing) {
			if (mListener != null) {
				mListener.onInitSuccess(PosDevices.WEIGHINGMACHINE,
						"Weighing Machine initialized successfully.");
			}
		} else {
			if (mListener != null) {
				mListener.onInitFailed(PosDevices.WEIGHINGMACHINE,
						"Failed to initialize Weighing Machine.");
			}
		}

		return mIsOkPosDeviceWeighing;
	}

	/**
	 * 
	 */
	public void shutdownDevices() {
		
		for (PosDeviceBase dev : mDeviceList) {
			try {
				dev.shutdown();
			} catch (Exception ex) {
				PosFormUtil
				.showErrorMessageBox(
						null,
						"There is an error while shuting down the device. Please check log for more info");
				PosLog.write(this, "shutdown", ex);
			}
		}

	}


	public ArrayList<PosDeviceKitchenPrinter> getKitchenPrinters(){
		return mPosDevKitchenPrinters;
	}
	
	

	/**
	 * @return
	 */
	/**
	 * @return
	 */
	public PosDeviceReceiptPrinter getReceiptPrinter() {
		
		
		return getPrinter(PrinterType.Receipt80);
	}
	
	/**
	 * @param 
	 * @return
	 */
	public boolean canUseAltLanguage(PrinterType printerType) {
				
		boolean useAltLangToPrint=false;
		if(PosDeviceManager.getInstance().getPrinter(printerType)!=null  && PosEnvSettings.getInstance().getPrintSettings().isLanguageSwitchingAllowed())			
			useAltLangToPrint = PosDeviceManager.getInstance().getPrinter(printerType).isUseAltLanguage();
			
		return useAltLangToPrint;
	}
	
	
	/**
	 * @param 
	 * @return
	 */
	public PosDeviceReceiptPrinter getPrinter(PrinterType printerType) {
	
		PosDeviceReceiptPrinter devRecptPrinter=null;
		if(mPosDevReceiptPrinter!=null && mPosDevReceiptPrinter.size()>0){
			
			for(PosDeviceReceiptPrinter printer: mPosDevReceiptPrinter){
				
				if(printer.getPrinterConfig().getPrinterType()==printerType){
					
					devRecptPrinter=printer;
					break;
				}
			}
		}
		return devRecptPrinter;
	}
	public PosDeviceCashBox getCashBox() {
		return mPosDevCashBox;
	}
	
	public PosDeviceWM getWeighingMachine() {
		return mPosDevWeighing;
	}


	public PosDevicePoleDisplay getPoleDisplay() {
		return mPosDevPoleDisplay;
	}

	/**
	 * @return the mCanUsePoleDisplay
	 */
	public boolean hasPoleDisplay() {
		return mDeviceSettings.isAttachedPoleDisplay();
	}

	public boolean hasReceiptPrinter() {
		return mDeviceSettings.isAttachedReceiptPrinter();
	}

	public boolean hasKichenPrinter() {
		return mDeviceSettings.isAttachedKitchenPrinter();
	}

	public boolean hasCashBox() {
		return (mDeviceSettings.isAttachedCashDrawer() || (getReceiptPrinter()!=null && getReceiptPrinter().hasCashBox()));
	}

	public boolean hasWeighingMachine() {
		return mDeviceSettings.isAttachedWeighing();
	}

	public boolean hasPosDevice() {
		return mDeviceSettings.isAttachedPosDevice();
	}

	private IPosDeviceManagerListener mListener;

	public void setListener(IPosDeviceManagerListener listener) {
		mListener = listener;
	}

	/**
	 * @author jojesh
	 *
	 */
	public interface IPosDeviceManagerListener {

		public abstract void onInitStarted(PosDevices device, String message);

		public abstract void onInitFailed(PosDevices device, String message);

		public abstract void onInitSuccess(PosDevices device, String message);

		public abstract void onMessage(String message);
	}
	
	/**
	 * 
	 */
	public void openCashBox() {

		try {
			if(getReceiptPrinter()!=null && getReceiptPrinter().hasCashBox())
				getReceiptPrinter().openCashBox();
			else if(hasCashBox() && mPosDevCashBox!=null) {
				mPosDevCashBox.openCashBox();
			}
		} catch (Exception e) {
			PosLog.write(this, "openCashBox", e);
		}

	}

}
