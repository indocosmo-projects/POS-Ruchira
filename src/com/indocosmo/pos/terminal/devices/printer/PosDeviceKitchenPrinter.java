/**
 * 
 */
package com.indocosmo.pos.terminal.devices.printer;

import com.indocosmo.pos.common.enums.PosPrinterType;
import com.indocosmo.pos.data.beans.terminal.device.BeanDevKitchenPrinterConfig;
import com.indocosmo.pos.data.beans.terminal.device.BeanDevPrinterConfig;
import com.indocosmo.pos.reports.printertest.PosPrinterTest;

/**
 * @author jojesh
 * 
 */
public class PosDeviceKitchenPrinter extends PosDevicePrinter<BeanDevKitchenPrinterConfig> {

	private boolean isMaster;
	private int kitchenId;

//	public static PosDeviceKitchenPrinter getInstance() {
//		if (mInstance == null) {
//			mInstance = new PosDeviceKitchenPrinter();
//		}
//		return mInstance;
//	}
	
	
	public static void testDevice(BeanDevPrinterConfig config) throws Exception{
		
		 PosDeviceKitchenPrinter printer=new PosDeviceKitchenPrinter();
		 final PosPrinterTest receipt=new PosPrinterTest();
		 printer.initialize((BeanDevKitchenPrinterConfig)config);
		 printer.print(receipt);
	}
	
//	private PosDeviceKitchenPrinter() {
	public PosDeviceKitchenPrinter() {
		super(PosPrinterType.KITCHEN);
	}
	
	public boolean isMaster(){
		return isMaster;
	}
	
	public int getKitchenId(){
		return kitchenId;
	}
	
//	public boolean initialize(BeanDevKitchenPrinterConfig printerConfig) throws Exception {
//		try {
//			PosDevPrinterConfigProvider provider = new PosDevPrinterConfigProvider();
//			BeanDevPrinterConfig config = provider
//					.getPrinterConfig(mPrinterType);
//			if (initialize(config))
//				return super.initialize();
//			else
//				return false;
//		} catch (Exception e) {
//			PosLog.write(this, "initialize", e);
//			throw e;
//		}
//	}
	
	/**
	 * Initializes the printer with the given configuration
	 * 
	 * @param config
	 * @return
	 * @throws Exception
	 */
	public boolean initialize(BeanDevKitchenPrinterConfig config) throws Exception {

		if (config == null)
			return false;
		mPrinterConfig = config;
		kitchenId= mPrinterConfig.getKitchenId();
		isMaster = mPrinterConfig.isMaster();
		return super.initialize(mPrinterConfig);
		
	}
	
	


}
