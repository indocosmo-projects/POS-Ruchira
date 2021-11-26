/**
 * 
 */
package com.indocosmo.pos.terminal.devices.printer;

import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.SimpleDoc;

import com.indocosmo.pos.common.PosLog;
import com.indocosmo.pos.common.enums.PosPrinterType;
import com.indocosmo.pos.data.beans.terminal.device.BeanDevPrinterConfig;
import com.indocosmo.pos.data.beans.terminal.device.BeanDevReceiptPrinterConfig;
import com.indocosmo.pos.reports.base.IPosPrintable;
import com.indocosmo.pos.reports.base.PosPrintableBase;
import com.indocosmo.pos.reports.printertest.PosPrinterTest;
import com.indocosmo.pos.terminal.devices.PosDeviceUtil;

/**
 * @author jojesh
 * 
 */
public final class PosDeviceReceiptPrinter extends PosDevicePrinter<BeanDevReceiptPrinterConfig> {

	
	private boolean hasCashBox;
	private String cashBoxOpenCode;
	private boolean allowManualOpen;
	private static PosDeviceReceiptPrinter mInstance;

//	public static PosDeviceReceiptPrinter getInstance() {
//		if (mInstance == null) {
//			mInstance = new PosDeviceReceiptPrinter();
//		}
//		return mInstance;
//	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.indocosmo.pos.terminal.devices.printer.PosDevicePrinter#print(java
	 * .io.InputStream, javax.print.PrintService)
	 */
//	@Override
//	public boolean print(InputStream is, PrintService service) throws Exception {
//		openCashBox();
//		return super.print(is, service);
//	}
//	
//	public boolean print(InputStream is, PrintService service, boolean openCashbox) throws Exception {
//		if(openCashbox)
//			openCashBox();
//		return super.print(is, service);
//	}
	
	/* (non-Javadoc)
	 * @see com.indocosmo.pos.terminal.devices.printer.PosDevicePrinter#print(java.awt.print.Printable)
	 */
	@Override
	public boolean print(IPosPrintable printable) throws Exception {
//		openCashBox();
		return super.print(printable);
	}
	
	public boolean print(PosPrintableBase printable, boolean openCashbox) throws Exception {
		if(openCashbox)
			openCashBox();
		return super.print(printable);
	}

	/**
	 * 
	 */
	public PosDeviceReceiptPrinter() {
		super(PosPrinterType.RECEIPT);
	}

	public void openCashBox() {
		if (mPrinterConfig!=null && mPrinterConfig.isCashBoxAttached()) {
			try {
				DocPrintJob job = mPrinterServiceInfo.getPrintService()
						.createPrintJob();
				byte[] bytes = PosDeviceUtil.stringToByteArr(mPrinterConfig
						.getCashBoxOpenCode());
				DocFlavor flavor = DocFlavor.BYTE_ARRAY.AUTOSENSE;
				Doc doc = new SimpleDoc(bytes, flavor, null);
				job.print(doc, null);
			} catch (Exception e) {
				PosLog.write(this, "openCashBox", e);
			}
		}
	}

	public void openCashBoxManually() {
		if (mPrinterConfig!=null && mPrinterConfig.isAllowManualCashBoxOpen())
			openCashBox();
	}

	public boolean hasCashBox() {
		return (mPrinterConfig!=null && mPrinterConfig.isCashBoxAttached());
	}

	public boolean canOpenManually() {
		return (mPrinterConfig!=null && mPrinterConfig.isAllowManualCashBoxOpen());
	}
	
	public static void testDevice(BeanDevPrinterConfig config) throws Exception {
		PosDeviceReceiptPrinter printer = new PosDeviceReceiptPrinter();
		if(printer==null) return;
		final PosPrinterTest receipt=new PosPrinterTest();
		printer.initialize((BeanDevReceiptPrinterConfig)config);
		printer.print(receipt);
	}
	
	/**
	 * Initializes the printer with the given configuration
	 * 
	 * @param config
	 * @return
	 * @throws Exception
	 */
	public boolean initialize(BeanDevReceiptPrinterConfig config) throws Exception {
		
		mPrinterConfig = config;
		hasCashBox=mPrinterConfig.isCashBoxAttached();
		cashBoxOpenCode=mPrinterConfig.getCashBoxOpenCode();
		allowManualOpen =mPrinterConfig.isAllowManualCashBoxOpen();
		return super.initialize(config);
	}
	
	public void cutPaper(){
		if(mPrinterConfig.isPaperCutOn()){
			try{
			DocPrintJob job = mPrinterServiceInfo.getPrintService().createPrintJob();  
			byte[] bytes = PosDeviceUtil.stringToByteArr(mPrinterConfig.getPaperCutCode());
			DocFlavor flavor = DocFlavor.BYTE_ARRAY.AUTOSENSE;
			Doc doc = new SimpleDoc(bytes, flavor, null);
			job.print(doc, null);
			}catch (Exception e){
				PosLog.write(this, "cutPaper", e);
			}
		}
	}

	/**
	 * @return the hasCashBox
	 */
	public boolean isCashBoxAttched() {
		return hasCashBox;
	}

	/**
	 * @return the cashBoxOpenCode
	 */
	public String getCashBoxOpenCode() {
		return cashBoxOpenCode;
	}

	/**
	 * @return the allowManualOpen
	 */
	public boolean isCashBoxOpenedManualy() {
		return allowManualOpen;
	}

}
