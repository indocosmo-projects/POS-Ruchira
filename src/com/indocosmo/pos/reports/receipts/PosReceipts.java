/**
 * 
 */
package com.indocosmo.pos.reports.receipts;

import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.RootPaneContainer;

import com.indocosmo.pos.common.PosEnvSettings;
import com.indocosmo.pos.common.beans.BeanBillPaymentSummaryInfo;
import com.indocosmo.pos.common.enums.PaymentReceiptItemGrouping;
import com.indocosmo.pos.common.enums.PosInvoicePrintFormat;
import com.indocosmo.pos.common.enums.PosOrderServiceTypes;
import com.indocosmo.pos.common.enums.PosPrinterType;
import com.indocosmo.pos.common.exception.NoSuchOrderException;
import com.indocosmo.pos.common.exception.printing.HasNoItemsToPrintException;
import com.indocosmo.pos.common.utilities.PosFormUtil;
import com.indocosmo.pos.common.utilities.PosOrderUtil;
import com.indocosmo.pos.data.beans.BeanOrderDetail;
import com.indocosmo.pos.data.beans.BeanOrderHeader;
import com.indocosmo.pos.data.beans.BeanOrderHeader.PosOrderStatus;
import com.indocosmo.pos.data.providers.shopdb.PosOrderHdrProvider;
import com.indocosmo.pos.data.providers.shopdb.PosOrderKitchenQueueProvider;
import com.indocosmo.pos.forms.messageboxes.PosMessageBoxForm.MessageBoxButtonTypes;
import com.indocosmo.pos.forms.messageboxes.PosMessageBoxForm.MessageBoxResults;
import com.indocosmo.pos.reports.base.IPosPrintable;
import com.indocosmo.pos.reports.base.PosPaymentReceiptBase;
import com.indocosmo.pos.reports.base.PosPrintableReportBase;
import com.indocosmo.pos.reports.receipts.custom.jp.PosPaymentReceiptNihon;
import com.indocosmo.pos.reports.receipts.custom.jp.PosPaymentReceiptReshitoNihon;
import com.indocosmo.pos.reports.receipts.custom.nz.PosPaymentReceiptNZ;
import com.indocosmo.pos.reports.receipts.custom.nz.PosefundReceiptNZ;
import com.indocosmo.pos.terminal.devices.PosDeviceManager;
import com.indocosmo.pos.terminal.devices.printer.PosDeviceKitchenPrinter;
import com.indocosmo.pos.terminal.devices.printer.PosDevicePrinter;
import com.indocosmo.pos.terminal.devices.printer.PosDeviceReceiptPrinter;
import com.indocosmo.pos.terminal.devices.printer.printerexceptions.PrinterException;

/**
 * @author jojesh
 * @author Ramesh S.
 * @since 11th Aug 2012
 */

public abstract class PosReceipts {
 

	/**
	 * @param orderHeader
	 * @param openCashBox
	 * @param paymentSummaryInfo
	 * @param isBillPrinting
	 * @param printMultiple
	 * @throws Exception
	 */
	public static void printReceipt(BeanOrderHeader orderHeader,Boolean openCashBox, BeanBillPaymentSummaryInfo paymentSummaryInfo,  boolean isBillPrinting, boolean useAltLangToPrint) throws Exception {
		printReceipt(null,orderHeader, openCashBox, paymentSummaryInfo, isBillPrinting, useAltLangToPrint);
	}

 
	/**
	 * @param orderHeader
	 * @param openCashBox
	 * @param paymentSummaryInfo
	 * @param isBillPrinting
	 * @param printMultiple
	 * @throws Exception
	 */
	public static void printReceipt(RootPaneContainer parent, BeanOrderHeader orderHeader,Boolean openCashBox, BeanBillPaymentSummaryInfo paymentSummaryInfo,  
			boolean isBillPrinting, boolean useAltLangToPrint) throws Exception {
		

		/** Change here for custom print format **/

		if(!PosDeviceManager.getInstance().hasReceiptPrinter()) 
			throw new PrinterException("No Receipt printer configured. Please configure printer.");
		 
		switch(PosEnvSettings.getInstance().getPrintSettings().getInvoiceFormat(orderHeader.getOrderServiceType())){
			case EIGHTY_MM:
				printReceipt80MM(parent,orderHeader,openCashBox,paymentSummaryInfo,isBillPrinting,useAltLangToPrint);
				break;
			case A4:
				printReceiptA4(parent,orderHeader,openCashBox,paymentSummaryInfo,isBillPrinting,useAltLangToPrint);
				break;
			case BOTH:
				printReceipt80MM(parent,orderHeader,openCashBox,paymentSummaryInfo,isBillPrinting,useAltLangToPrint);
				printReceiptA4(parent,orderHeader,openCashBox,paymentSummaryInfo,isBillPrinting,useAltLangToPrint);
				break;
		}
		
	}
	
	/**
	 * @param orderHeader
	 * @param openCashBox
	 * @param paymentSummaryInfo
	 * @param isBillPrinting
	 * @param printMultiple
	 * @throws Exception
	 */
	public static void printReceipt80MM(RootPaneContainer parent, BeanOrderHeader orderHeader,Boolean openCashBox, BeanBillPaymentSummaryInfo paymentSummaryInfo,  
			boolean isBillPrinting, boolean useAltLangToPrint) throws Exception {
		

		/** Change here for custom print format **/

		if(!PosDeviceManager.getInstance().hasReceiptPrinter()) 
			throw new PrinterException("No Receipt printer configured. Please configure printer.");
		
		PosPrintableReportBase recipt=null;
	 
		switch(PosEnvSettings.getInstance().getPrintSettings().getPrintingFormat()){
		
		case "NIHON":
			recipt=new PosPaymentReceiptNihon();
			break;
		case "NZ":
			recipt=new PosPaymentReceiptNZ();
			break;
		default:
			
			if (PosEnvSettings.getInstance().getPrintSettings()
					.getPaymentReceiptSettings()
					.getItemGroupingMethod().equals(PaymentReceiptItemGrouping.None)) {
			
				recipt=new PosDefaultPaymentReceipt();
			}else {
				recipt=new PosItemGroupingPaymentReceipt();
			
			}
				
			break;
		}
				
		((PosPaymentReceiptBase)recipt).setBillPrinting(isBillPrinting);
		((PosPaymentReceiptBase)recipt).setOrder(orderHeader);
		if(paymentSummaryInfo!=null)
			((PosPaymentReceiptBase)recipt).setPreBillPaymentInfo(paymentSummaryInfo);
 
		final PosDeviceReceiptPrinter printer=PosDeviceManager.getInstance().getPrinter(recipt.getPrinterType());

		/**
		 * Check if printer exist 
		 */
		if(printer==null) 
			throw new PrinterException("Receipt printer not available. Please check printer");
		
		recipt.setUseAltLanguge(useAltLangToPrint);
		
		printer.print(recipt,openCashBox);
		
		new PosOrderHdrProvider().updatePrintCounter(orderHeader.getOrderId());
		orderHeader.setTotalPrintCount(orderHeader.getTotalPrintCount()+1);

	}
	
	
	/**
	 * @param orderHeader
	 * @param openCashBox
	 * @param paymentSummaryInfo
	 * @param isBillPrinting
	 * @param printMultiple
	 * @throws Exception
	 */
	public static void printReceiptA4(RootPaneContainer parent, BeanOrderHeader orderHeader,Boolean openCashBox, BeanBillPaymentSummaryInfo paymentSummaryInfo,  
			boolean isBillPrinting, boolean useAltLangToPrint) throws Exception {
		

		/** Change here for custom print format **/

		if(!PosDeviceManager.getInstance().hasReceiptPrinter()) 
			throw new PrinterException("No Receipt printer configured. Please configure printer.");
		
		if (orderHeader.getOrderServiceType()!=PosOrderServiceTypes.SALES_ORDER){
			
			PosPrintableReportBase recipt=new PosPRDPaymentReceipt(); 
					((PosPRDPaymentReceipt)recipt).setOrder(orderHeader);
			
			final PosDeviceReceiptPrinter printer=PosDeviceManager.getInstance().getPrinter(recipt.getPrinterType());
	
			/**
			 * Check if printer exist 
			 */
			if(printer==null) 
				throw new PrinterException("Receipt printer not available. Please check printer");
			
			recipt.setUseAltLanguge(useAltLangToPrint);
			printer.print(recipt,openCashBox);
			
			new PosOrderHdrProvider().updatePrintCounter(orderHeader.getOrderId());
			orderHeader.setTotalPrintCount(orderHeader.getTotalPrintCount()+1);
		}else{
		
//		if (SOInvoice && !isBillPrinting  && orderHeader.getOrderServiceType()==PosOrderServiceTypes.SALES_ORDER && orderHeader.getStatus()==PosOrderStatus.Closed ){
			
		
				printSOInvoice(orderHeader, openCashBox,  useAltLangToPrint);
		}
	}
	
	
	
	/**
	 * @param orderHeader
	 * @param openCashBox
	 * @param paymentSummaryInfo
	 * @param isBillPrinting
	 * @param printMultiple
	 * @throws Exception
	 */
	public static void printSOInvoice(BeanOrderHeader orderHeader,Boolean openCashBox,  boolean useAltLangToPrint) throws Exception {
		
		/** Change here for custom print format **/

		if(!PosDeviceManager.getInstance().hasReceiptPrinter()) 
			throw new PrinterException("No Receipt printer configured. Please configure printer.");
		
		 		
		PosPrintableReportBase receipt= new PosSalesOrderInvoice();
	 
		((PosSalesOrderInvoice)receipt).setOrder(orderHeader);
		receipt.setUseAltLanguge(useAltLangToPrint);
		
		final PosDeviceReceiptPrinter printer=PosDeviceManager.getInstance().getPrinter(receipt.getPrinterType());
		/**
		 * Check if printer exist 
		 */
		if(printer==null)
			throw new PrinterException("Please check printer settings, \"Normal\" printer is not available. ");
		
		printer.print(receipt,openCashBox);
		
		new PosOrderHdrProvider().updatePrintCounter(orderHeader.getOrderId());
		orderHeader.setTotalPrintCount(orderHeader.getTotalPrintCount()+1);
		 
	}
	
	/**
	 * @param orderHeaderID
	 * @param printAll
	 * @param count
	 * @param printRecipt
	 * @throws Exception
	 */
	public synchronized static void printReceiptToKitchen(String orderHeaderID,Boolean printAll, int count,Boolean printRecipt) throws Exception {


		final PosOrderHdrProvider orderHeaderProvider = new PosOrderHdrProvider();
		final BeanOrderHeader orderHeader = orderHeaderProvider.getOrderData(orderHeaderID);
		
		if(orderHeader==null)
			throw new NoSuchOrderException("No such order. Please check order number.");

		
		if(orderHeader.getStatus()!=PosOrderStatus.Void && !printAll && !PosOrderUtil.hasNotPrintedToKitchenItems(orderHeader))
			throw new HasNoItemsToPrintException("All items have already been printed to kitchen.");
 
		printReceiptToKitchen(orderHeader,printAll,count,
				PosEnvSettings.getInstance().getPrintSettings().isKitchenPrintingToCounter());
		PosOrderUtil.setAsPrintedToKitchen(orderHeader);
		orderHeaderProvider.saveOrder(orderHeader, false, true, false, false);

	}

	/**
	 * @param orderHeader
	 * @param openCashBox
	 * @param paymentSummaryInfo
	 * @param isBillPrinting
	 * @param printMultiple
	 * @throws Exception
	 */
	public static void printSOReceipt(BeanOrderHeader orderHeader,Boolean openCashBox, boolean useAltLangToPrint) throws Exception {
		

		/** Change here for custom print format **/

		if(!PosDeviceManager.getInstance().hasReceiptPrinter()) 
			throw new PrinterException("No printer configured. Please configure printer.");

		if (PosOrderUtil.isDeliveryService(orderHeader) && (orderHeader.getStatus()!=PosOrderStatus.Closed && orderHeader.getStatus() != PosOrderStatus.Refunded)){
			PosSalesOrderReceipt recipt=new PosSalesOrderReceipt();
			
			final PosDeviceReceiptPrinter printer=PosDeviceManager.getInstance().getPrinter(recipt.getPrinterType());

			/**
			 * Check if printer exist 
			 */
			if(printer==null) 
				throw new PrinterException("Please check printer settings, \"Normal\" printer is not available. ");
			
			recipt.setOrder(orderHeader);
		 
			recipt.setUseAltLanguge(useAltLangToPrint);
			
			printer.print(recipt,openCashBox);
			
			new PosOrderHdrProvider().updatePrintCounter(orderHeader.getOrderId());
			orderHeader.setTotalPrintCount(orderHeader.getTotalPrintCount()+1);
		}
 	}
	 
	
	/**
	 * For test
	 * @throws Exception
	 */
	public static void testPrint() throws Exception{
		
		final PosDeviceReceiptPrinter printer=PosDeviceManager.getInstance().getReceiptPrinter();
		if(printer!=null)
			PosDevicePrinter.testDevice(printer.getPrinterConfig(), printer.getPrinterType());
		
		ArrayList<PosDeviceKitchenPrinter> kotPrinters=PosDeviceManager.getInstance().getKitchenPrinters();
		if(kotPrinters!=null) {
			for(PosDevicePrinter kotPrinter:kotPrinters) {
				PosDevicePrinter.testDevice(kotPrinter.getPrinterConfig(), kotPrinter.getPrinterType());
			}
		}
	}

	/**
	 * @param orderHeader
	 * @param openCashBox
	 * @throws Exception
	 */
	public static void printBillReshito(BeanOrderHeader orderHeader, boolean openCashBox) throws Exception {
		/** Change here for custom print format **/

		final PosDeviceReceiptPrinter printer=PosDeviceManager.getInstance().getReceiptPrinter();

		/**
		 * Check if printer exist
		 */
		if(printer==null) return;

		final PosPaymentReceiptReshitoNihon recipt=new PosPaymentReceiptReshitoNihon();
		recipt.setOrder(orderHeader);
		printer.print(recipt,openCashBox);
	}

	/**
	 * Print the bill for pre-payment.
	 * @param orderHeader
	 * @throws Exception
	 */
	public static void printPrePaymentBill(BeanOrderHeader orderHeader) throws Exception {

		printReceipt(orderHeader,false,null,true,true);

	}
	/** Kitchen printing functions **/

	/**
	 * @param orderHeader
	 * @param printAll 
	 * @throws Exception
	 */
	public static void printReceiptToKitchen(BeanOrderHeader orderHeader, boolean printAll) throws Exception {
		printReceiptToKitchen(orderHeader,printAll,1,
				PosEnvSettings.getInstance().getPrintSettings().isKitchenPrintingToCounter());
	}
	/**
	 * @param orderHeader
	 * @param count
	 * @throws Exception
	 */
	public static void printReceiptToKitchen(BeanOrderHeader orderHeader,int count) throws Exception {
		printReceiptToKitchen(orderHeader,false,count,
				PosEnvSettings.getInstance().getPrintSettings().isKitchenPrintingToCounter());
	}
	/**
	 * @param orderHeader
	 * @param printAll
	 * @param count
	 * @throws Exception
	 */
	public static void printReceiptToKitchen(BeanOrderHeader orderHeader,Boolean printAll, int count) throws Exception {
		printReceiptToKitchen(orderHeader,printAll,count,
				PosEnvSettings.getInstance().getPrintSettings().isKitchenPrintingToCounter());
	}
	
	
	/**
	 * @param orderHeader
	 * @param printAll
	 * @param count
	 * @param printRecipt
	 * @throws Exception
	 **/
	public static void printReceiptToKitchen(BeanOrderHeader orderHeader,Boolean printAll, int count, Boolean printToCounter) throws Exception {

		
	
		final PosDefaultKitchenReceipt receipt=new PosDefaultKitchenReceipt();
		BeanOrderHeader ordHdr=orderHeader.clone();
		receipt.setOrder(ordHdr);
		receipt.setPrintAll((orderHeader.getStatus()==PosOrderStatus.Void)?true:printAll);
//		final PosDefaultKitchenReceipt receipt=new PosDefaultKitchenReceipt();
		
		
		ArrayList<PosDevicePrinter> printers=new ArrayList<PosDevicePrinter>();
		
		
		printers.addAll(PosDeviceManager.getInstance().getKitchenPrinters());
		
		if(printToCounter)
			printers.add(PosDeviceManager.getInstance().getReceiptPrinter());
	
		
		//(<ArrayList<PosDevicePrinter>)PosDeviceManager.getInstance().getKitchenPrinters();
		/**
		 * Check if printer exist
		 */
		if(printers==null || printers.size()==0) return;

		 
		int queueNo=0;
		int kitchenId;
		HashMap<Integer, Integer> kitchenQueueNoList=new HashMap<Integer, Integer>();
		HashMap<Integer, Boolean> kitchenPrintList=new HashMap<Integer, Boolean>();
		
		// Generate queue nos for all kitchen before printing
		for(PosDevicePrinter printer:printers){

			if(!printer.isActive())
				break;
			kitchenId=((PosDeviceKitchenPrinter) printer).getKitchenId();
			queueNo= PosOrderKitchenQueueProvider.getInstance().getKitchenQueueNo(orderHeader,kitchenId );
			kitchenQueueNoList.put(kitchenId,queueNo);
			kitchenPrintList.put(kitchenId,false);
		}
		
		
		for(PosDevicePrinter printer:printers){

			if(!printer.isActive())
				break;
			
			if(printer.getPrinterType()==PosPrinterType.RECEIPT){
				
				receipt.setPrintCurrentKitchenItemsOnly(false);
				
			}else{
				receipt.setPrintCurrentKitchenItemsOnly(!((PosDeviceKitchenPrinter) printer).isMaster());
			}
			kitchenId=((PosDeviceKitchenPrinter) printer).getKitchenId();
			receipt.setKitchenId(kitchenId);
			
			try{
//				queueNo= PosOrderKitchenQueueProvider.getInstance().getKitchenQueueNo(orderHeader,kitchenId );
				queueNo=kitchenQueueNoList.get(kitchenId);
				
				receipt.setKitchenQueueNo(queueNo);
				receipt.setUseAltLanguge(printer.isUseAltLanguage());
				printer.print((IPosPrintable)receipt.clone());
			}catch(Exception ex){
				
				for(int id:kitchenPrintList.keySet() ) {
					if (kitchenPrintList.get(id)==true) {
						
						queueNo=kitchenQueueNoList.get(id);
						
						PosOrderKitchenQueueProvider.getInstance().deleteKitchenQueueNo(
								orderHeader.getOrderId(), id, queueNo);
					
					
					}
				}
				throw ex;
			}
		}

	}
	
	
//	/**
//	 * @param orderHeader
//	 * @param printAll
//	 * @param count
//	 * @param printRecipt
//	 * @throws Exception
//	 **/
//	public static void printReceiptToKitchen(BeanOrderHeader orderHeader,Boolean printAll, int count, Boolean printToCounter) throws Exception {
//
//		
//	
//		final PosDefaultKitchenReceipt receipt=new PosDefaultKitchenReceipt();
//		BeanOrderHeader ordHdr=orderHeader.clone();
//		receipt.setOrder(ordHdr);
//		receipt.setPrintAll((orderHeader.getStatus()==PosOrderStatus.Void)?true:printAll);
////		final PosDefaultKitchenReceipt receipt=new PosDefaultKitchenReceipt();
//		
//		
//		ArrayList<PosDevicePrinter> printers=new ArrayList<PosDevicePrinter>();
//		
//		
//		printers.addAll(PosDeviceManager.getInstance().getKitchenPrinters());
//		
//		if(printToCounter)
//			printers.add(PosDeviceManager.getInstance().getReceiptPrinter());
//	
//		
//		//(<ArrayList<PosDevicePrinter>)PosDeviceManager.getInstance().getKitchenPrinters();
//		/**
//		 * Check if printer exist
//		 */
//		if(printers==null || printers.size()==0) return;
//
//		 
//		int queueNo=0;
//		int kitchenId;
////		// Generate queue nos for all kitchen before printing
////		for(PosDevicePrinter printer:printers){
////
////			if(!printer.isActive())
////				break;
////			kitchenId=((PosDeviceKitchenPrinter) printer).getKitchenId();
////			queueNo= PosOrderKitchenQueueProvider.getInstance().getKitchenQueueNo(orderHeader,kitchenId );
////			queueNoList.put(kitchenId,queueNo);
////			
////		}
//		
//		for(PosDevicePrinter printer:printers){
//
//			if(!printer.isActive())
//				break;
//			
//			if(printer.getPrinterType()==PosPrinterType.RECEIPT){
//				
//				receipt.setPrintCurrentKitchenItemsOnly(false);
//				
//			}else{
//				receipt.setPrintCurrentKitchenItemsOnly(!((PosDeviceKitchenPrinter) printer).isMaster());
//			}
//			kitchenId=((PosDeviceKitchenPrinter) printer).getKitchenId();
//			receipt.setKitchenId(kitchenId);
//			
//			try{
//				queueNo= PosOrderKitchenQueueProvider.getInstance().getKitchenQueueNo(orderHeader,kitchenId );
//				receipt.setKitchenQueueNo(queueNo);
//				receipt.setUseAltLanguge(printer.isUseAltLanguage());
//				printer.print((IPosPrintable)receipt.clone());
//			}catch(Exception ex){
//				PosOrderKitchenQueueProvider.getInstance().deleteKitchenQueueNo(orderHeader.getOrderId(), kitchenId, queueNo);
//				throw ex;
//			}
//		}
//
//	}
	
	/**
	 * @param orderHeaderID
	 * @param printAll
	 * @param count
	 * @param printRecipt
	 * @throws Exception
	 */
	public synchronized static void printReceiptToKitchen(String orderHeaderID,Boolean printAll, int count) throws Exception {


		final PosOrderHdrProvider orderHeaderProvider = new PosOrderHdrProvider();
		final BeanOrderHeader orderHeader = orderHeaderProvider.getOrderData(orderHeaderID);
		
		if(orderHeader==null)
			throw new NoSuchOrderException("No such order. Please check order number.");

		
		if(orderHeader.getStatus()!=PosOrderStatus.Void && !printAll && !PosOrderUtil.hasNotPrintedToKitchenItems(orderHeader))
			throw new HasNoItemsToPrintException("All items have already been printed to kitchen.");
		
		printReceiptToKitchen(orderHeader, printAll,count,
				PosEnvSettings.getInstance().getPrintSettings().isKitchenPrintingToCounter());
		PosOrderUtil.setAsPrintedToKitchen(orderHeader);
		orderHeaderProvider.saveOrder(orderHeader, false, true, false, false);

	}
	
	/**
	 * @param orderHeaderID
	 * @param printAll
	 * @param count
	 * @param printRecipt
	 * @throws Exception
	 */
	public synchronized static void printBill(String orderHeaderID) throws Exception {

		final PosOrderHdrProvider orderHeaderProvider = new PosOrderHdrProvider();
		final BeanOrderHeader orderHeader = orderHeaderProvider.getOrderData(orderHeaderID);
		
		if(orderHeader==null)
			throw new NoSuchOrderException("No such order. Please check order number.");
		
		printPrePaymentBill(orderHeader);
	}

	 
	/**
	 * @param orderHeader
	 * @param openCashBox
	 * @param paymentSummaryInfo
	 * @param isBillPrinting
	 * @param printMultiple
	 * @throws Exception
	 */
//	public static void printRefundReceipt(BeanOrderHeader orderHeader,Boolean openCashBox,   boolean printMultiple, boolean useAltLangToPrint) throws Exception {
	public static void printRefundReceipt(BeanOrderHeader orderHeader,Boolean openCashBox,   boolean useAltLangToPrint) throws Exception {
		

		/** Change here for custom print format **/

		if(!PosDeviceManager.getInstance().hasReceiptPrinter()) 
			throw new PrinterException("No Receipt printer configured. Please configure printer.");
		
		final PosDeviceReceiptPrinter printer=PosDeviceManager.getInstance().getReceiptPrinter();

		/**
		 * Check if printer exist 
		 */
		if(printer==null) 
			throw new PrinterException("Receipt printer not available. Please check printer");

		
		 
		PosPaymentReceiptBase recipt=null;
		
		switch(PosEnvSettings.getInstance().getPrintSettings().getPrintingFormat()){
		
		case "NIHON":
			recipt=new PosDefaultRefundReceipt();
			break;
		case "NZ":
			recipt=new PosefundReceiptNZ();
			break;
		default:
			recipt=new PosDefaultRefundReceipt();
			break;
			
		}
		
		
		recipt.setUseAltLanguge(useAltLangToPrint);
		recipt.setOrder(orderHeader);
		
//		recipt.setOverridePrinterSettings(printMultiple);
//	 	recipt.setr 
		printer.print(recipt,openCashBox);
		
//		new PosOrderHdrProvider().updatePrintCounter(orderHeader.getOrderId());
//		orderHeader.setTotalPrintCount(orderHeader.getTotalPrintCount()+1);
	}
	
	
	/**
	 * @param orderHeaderID
	 * @param printAll
	 * @param count
	 * @param printItemLabels
	 * @throws Exception
	 */
	public synchronized static void printItemLabels(BeanOrderHeader orderHdr) throws Exception {
 
		PosItemLabelPrint itemPrint=new PosItemLabelPrint();
		
		final PosDeviceReceiptPrinter printer=PosDeviceManager.getInstance().getPrinter(itemPrint.getPrinterType());

		/**
		 * Check if printer exist 
		 */
		if(printer==null) 
			throw new PrinterException("Receipt printer not available. Please check printer");
		
		for(BeanOrderDetail orderDtl:orderHdr.getOrderDetailItems()){
			itemPrint=new PosItemLabelPrint(orderHdr,orderDtl);
			printer.print(itemPrint);
		}
		
	}
}
