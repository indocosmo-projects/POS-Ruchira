package com.indocosmo.pos.terminal.devices.printer;

import java.util.ArrayList;

import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.DocAttributeSet;
import javax.print.attribute.HashDocAttributeSet;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Copies;
import javax.print.attribute.standard.MediaPrintableArea;
import javax.print.attribute.standard.MediaSizeName;
import javax.print.attribute.standard.MultipleDocumentHandling;
import javax.print.attribute.standard.PrinterState;
import javax.print.attribute.standard.SheetCollate;
import javax.print.event.PrintJobAdapter;
import javax.print.event.PrintJobEvent;
import javax.print.event.PrintJobListener;

import com.indocosmo.pos.common.PosLog;
import com.indocosmo.pos.common.enums.PosPrinterType;
import com.indocosmo.pos.common.exception.PosException;
import com.indocosmo.pos.common.utilities.PosFormUtil;
import com.indocosmo.pos.data.beans.terminal.device.BeanDevPrinterConfig;
import com.indocosmo.pos.data.beans.terminal.device.BeanPrinterServiceInfo;
import com.indocosmo.pos.reports.base.IPosPrintable;
import com.indocosmo.pos.terminal.devices.PosDeviceBase;
import com.indocosmo.pos.terminal.devices.PosDeviceUtil;

/**
 * @author Ramesh S.
 * @since 14th Aug 2012
 */

public abstract class PosDevicePrinter<T extends BeanDevPrinterConfig> extends PosDeviceBase {

	protected PrintService mPrintService;
	protected T mPrinterConfig;
	protected BeanPrinterServiceInfo mPrinterServiceInfo;
	protected PosPrinterType mPrinterType;
	protected boolean isActive;
	protected boolean useAltLanguage;
	protected int mNoCopies = 1;

	public static void testDevice(BeanDevPrinterConfig config,
			PosPrinterType type) throws Exception {
		if (type == PosPrinterType.KITCHEN)
			PosDeviceKitchenPrinter.testDevice(config);
		else
			PosDeviceReceiptPrinter.testDevice(config);
	}

	public PosDevicePrinter(PosPrinterType type) {
		mPrinterType = type;
	}

	/**
	 * @return the mPrinterType
	 */
	public PosPrinterType getPrinterType() {
		return mPrinterType;
	}

	/**
	 * Capture all printers installed in the system.
	 * 
	 * @return
	 */
	public static ArrayList<BeanPrinterServiceInfo> getAllPrinters() {
		ArrayList<BeanPrinterServiceInfo> list = null;
		PrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();
		DocFlavor flavor = DocFlavor.INPUT_STREAM.AUTOSENSE;
		PrintService printService[] = PrintServiceLookup.lookupPrintServices(
				flavor, pras);
		if (printService.length > 0) {
			list = new ArrayList<BeanPrinterServiceInfo>();

			for (int index = 0; index < printService.length; index++) {
				BeanPrinterServiceInfo printerInfo = new BeanPrinterServiceInfo(
						printService[index]);
				printerInfo.setId(index + 1);
				list.add(printerInfo);
			}
		}

		return list;
	}

	public static BeanPrinterServiceInfo getServiceinforFromName(String name) {
		BeanPrinterServiceInfo printerInfo = null;
		PrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();
		DocFlavor flavor = DocFlavor.INPUT_STREAM.AUTOSENSE;
		PrintService printService[] = PrintServiceLookup.lookupPrintServices(
				flavor, pras);
		if (printService.length > 0) {
			for (int index = 0; index < printService.length; index++) {
				if (printService[index].getName().equals(name)) {
					printerInfo = new BeanPrinterServiceInfo(
							printService[index]);
					printerInfo.setId(index + 1);
					break;
				}
			}
		}
		return printerInfo;
	}
	
	public void getStatus(){
		
	}

	/**
	 * @param printable
	 * @return
	 * @throws Exception
	 */
	public boolean print(IPosPrintable printable) throws Exception {
		
		if (!isDeviceInitialized() || !printable.isPrintable())
			return false;
				
		try {
			DocFlavor flavor = DocFlavor.SERVICE_FORMATTED.PRINTABLE;
			if (mPrintService != null) {
				
				PrinterState st =mPrintService.getAttribute(PrinterState.class); 
							

				DocAttributeSet docAttrSet = new HashDocAttributeSet();
				
				Doc doc = new SimpleDoc(printable, flavor, docAttrSet);
				PrintRequestAttributeSet printAttrSet = new HashPrintRequestAttributeSet();
				
				if(printable.getPosReportPageFormat().getMediaSizeName()!=null)
					printAttrSet.add(printable.getPosReportPageFormat().getMediaSizeName());
				else
					printAttrSet.add(MediaSizeName.ISO_B0);
				printAttrSet.add(printable.getPrintingOrientation());
				printAttrSet.add(new MediaPrintableArea(
						printable.getPosReportPageFormat().getMarginLefft(), 
						printable.getPosReportPageFormat().getMarginTop(), 
						printable.getPosReportPageFormat().getWidth()-(printable.getPosReportPageFormat().getMarginLefft()+printable.getPosReportPageFormat().getMarginRight()), 
						printable.getPosReportPageFormat().getHeight()-(printable.getPosReportPageFormat().getMarginTop()+printable.getPosReportPageFormat().getMarginBottom()), 
						printable.getPosReportPageFormat().getUnit()));

 
				PrintJobListener pjlistener = new PrintJobAdapter() {
					
					public void printDataTransferCompleted(PrintJobEvent e) {
						
						cutPaper();
					}

					/* (non-Javadoc)
					 * @see javax.print.event.PrintJobAdapter#printJobCompleted(javax.print.event.PrintJobEvent)
					 */
					@Override
					public void printJobCompleted(PrintJobEvent pje) {
						
						super.printJobCompleted(pje);
					}
					
					/* (non-Javadoc)
					 * @see javax.print.event.PrintJobAdapter#printJobFailed(javax.print.event.PrintJobEvent)
					 */
					@Override
					public void printJobFailed(PrintJobEvent pje) {
					
						PosFormUtil.showErrorMessageBox(null, "Faild to print.");
					}
					
					
				};
				final int printCount=(printable.canOverridePrinterSettings())?printable.getCopies():mNoCopies;
				for (int pCount = 0; pCount < printCount; pCount++) {
					DocPrintJob job = mPrintService.createPrintJob();
					
					job.addPrintJobListener(pjlistener);
					job.print(doc, printAttrSet);
				}
			}
		} catch (Exception e) {
			PosLog.write(this, "print", e);
			throw e;
		}

		return true;
	}
//	/**
//	 * Initializes the printer with the given configuration
//	 * 
//	 * @param config
//	 * @return
//	 * @throws Exception
//	 */
	public  boolean initialize(T config) throws Exception {
		

		if (config == null)
			return false;

		mNoCopies = config.getNoCopies();
		isActive = config.isActive();
		useAltLanguage = config.isUseAltLangugeToPrint();
		mPrinterServiceInfo = getServiceinforFromName(config.getName());
		if (mPrinterServiceInfo != null) {
			mPrintService = mPrinterServiceInfo.getPrintService();
			setDeviceInitialized(true);
		} else {
			PosLog.write(this, "initialize",
					"Printer service is null. Printer not found.");
			throw new PosException("Failed to initilize the printer");
		}
		return true;
	}
//	
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
//	
	/**
	 * @return the useAltLanguage
	 */
	public boolean isUseAltLanguage() {
		return useAltLanguage;
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indocosmo.pos.terminal.devices.IPosDeviceBase#shutdown()
	 */
	@Override
	public void shutdown() throws Exception {
		// TODO Auto-generated method stub

	}
	public boolean isActive(){
		return isActive;
	}

	/**
	 * @return the mPrinterServiceInfo
	 */
	public BeanPrinterServiceInfo getPrinterServiceInfo() {
		return mPrinterServiceInfo;
	}

	/**
	 * @return the mPrinterConfig
	 */
	public T getPrinterConfig() {
		return mPrinterConfig;
	}


}
