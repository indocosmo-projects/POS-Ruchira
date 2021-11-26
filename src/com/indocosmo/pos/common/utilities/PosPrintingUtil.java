/**
 * 
 */
package com.indocosmo.pos.common.utilities;

import java.util.ArrayList;

import javax.swing.RootPaneContainer;

import com.indocosmo.barcode.common.BarcodeEnvSettings;
import com.indocosmo.pos.common.PosEnvSettings;
import com.indocosmo.pos.common.enums.EnablePrintingOption;
import com.indocosmo.pos.common.enums.PosOrderServiceTypes;
import com.indocosmo.pos.common.enums.PosPrintOption;
import com.indocosmo.pos.data.beans.BeanOrderDetail;
import com.indocosmo.pos.data.beans.BeanOrderDetail.OrderDetailItemType;
import com.indocosmo.pos.data.beans.BeanOrderHeader;
import com.indocosmo.pos.data.beans.BeanSaleItem;
import com.indocosmo.pos.forms.PosObjectBrowserForm;
import com.indocosmo.pos.forms.PosObjectBrowserForm.ItemSize;
import com.indocosmo.pos.forms.components.objectbrowser.IPosBrowsableItem;
import com.indocosmo.pos.forms.listners.IPosBillPrintFormListner;
import com.indocosmo.pos.forms.listners.IPosObjectBrowserListner;
import com.indocosmo.pos.terminal.devices.PosDeviceManager;
import com.indocosmo.pos.terminal.devices.printer.PosDeviceKitchenPrinter;

/**
 * @author joe.12.3
 *
 */
public final class PosPrintingUtil {
	

	private static String PARENT_ITEM_INDICATOR="+";
	private static String SUB_ITEM_INDICATOR="|";
	private static String NORMAL_ITEM_INDICATOR="-";
	
	public static String getItemLevelIndicator(BeanOrderDetail item){
		
		String indiChar="";
		
		if(item.getItemType()==OrderDetailItemType.SALE_ITEM )
			indiChar=(item.hasSubItems()?PARENT_ITEM_INDICATOR:NORMAL_ITEM_INDICATOR);
		else
			indiChar=SUB_ITEM_INDICATOR;
		
		return indiChar;
	}
	
	public static String getNameToPrint(BeanSaleItem saleItem){
		String nameToPrint="";
		
		return nameToPrint;
	}
	
	/**
	 * 
	 */
	public static ArrayList<IPosBrowsableItem>  buildPrintOptions(){

		ArrayList<IPosBrowsableItem>  printButtonList =new ArrayList<IPosBrowsableItem>();

		if(PosDeviceManager.getInstance().getReceiptPrinter()!=null && PosDeviceManager.getInstance().getReceiptPrinter().isDeviceInitialized() )
			printButtonList.add(PosPrintOption.PAYMENTRECEIPT);

		if(PosDeviceManager.getInstance().hasKichenPrinter()){
			for(PosDeviceKitchenPrinter kp: PosDeviceManager.getInstance().getKitchenPrinters()){

				if(kp.isDeviceInitialized() && kp.isActive()){
					printButtonList.add(PosPrintOption.KITCHENRECEIPT);
					break;
				}
			}
		}

		if(PosEnvSettings.getInstance().getPrintSettings().isBarCodePrintingEnabled())
			printButtonList.add(PosPrintOption.BARCODELABEL);
		
		if(PosDeviceManager.getInstance().getReceiptPrinter()!=null && PosDeviceManager.getInstance().getReceiptPrinter().isDeviceInitialized() &&
				BarcodeEnvSettings.getInstance().isItemLabelPrinting()  )
			printButtonList.add(PosPrintOption.ITEMLABEL);
		
		if(PosEnvSettings.getInstance().getPrintSettings().isPrintingReshitoEnabled() &&
				PosDeviceManager.getInstance().hasReceiptPrinter() &&
				PosDeviceManager.getInstance().getReceiptPrinter()!=null && PosDeviceManager.getInstance().getReceiptPrinter().isDeviceInitialized()){
		
			printButtonList.add(PosPrintOption.RESHITO);
		}

		return printButtonList;
 
	}
 
	/**
	 * 
	 */
	public static ArrayList<IPosBrowsableItem>  buildPrintOptions(BeanOrderHeader order){

		ArrayList<IPosBrowsableItem>  printButtonList =buildPrintOptions(); 
		
		if(printButtonList!=null && order.getOrderServiceType()==PosOrderServiceTypes.SALES_ORDER)
			printButtonList.add(PosPrintOption.SALESORDER);

		return printButtonList;
 
	}
	/**
	 * 
	 */
	public static ArrayList<IPosBrowsableItem>  buildPrintOptionsAtPayment(){

		ArrayList<IPosBrowsableItem>  printButtonList =new ArrayList<IPosBrowsableItem>();

		if(PosDeviceManager.getInstance().getReceiptPrinter()!=null && PosDeviceManager.getInstance().getReceiptPrinter().isDeviceInitialized() )
			printButtonList.add(PosPrintOption.PAYMENTRECEIPT);

		if(PosDeviceManager.getInstance().hasKichenPrinter()&& 
				PosEnvSettings.getInstance().getPrintSettings().getPrintToKitchenAtPayment()!=EnablePrintingOption.NO ){
			for(PosDeviceKitchenPrinter kp: PosDeviceManager.getInstance().getKitchenPrinters()){

				if(kp.isDeviceInitialized() && kp.isActive()){
					printButtonList.add(PosPrintOption.KITCHENRECEIPT);
					break;
				}
			}
		}

		if(PosEnvSettings.getInstance().getPrintSettings().isBarCodePrintingEnabled())
			printButtonList.add(PosPrintOption.BARCODELABEL);
		
		if(PosEnvSettings.getInstance().getPrintSettings().getPrintReshitoAtPayment()!=EnablePrintingOption.NO &&
				PosDeviceManager.getInstance().hasReceiptPrinter() &&
				PosDeviceManager.getInstance().getReceiptPrinter()!=null && PosDeviceManager.getInstance().getReceiptPrinter().isDeviceInitialized()){
		
			printButtonList.add(PosPrintOption.RESHITO);
		}

		return printButtonList;
 
	}
/*
 * 
 */
	public static void showPrintOptions(final RootPaneContainer parent,
			final IPosBillPrintFormListner billPrintListner,
			ArrayList<IPosBrowsableItem> printButtonList) {

		if(printButtonList.size()==1){
			doRePrint(parent,billPrintListner,printButtonList.get(0));
			
		}else{
			
			final PosObjectBrowserForm form=new PosObjectBrowserForm("Print Menu", printButtonList , ItemSize.Wider,true);
			form.setListner(new IPosObjectBrowserListner() {
	
				@Override
				public void onItemSelected(IPosBrowsableItem item) {
					doRePrint(parent,billPrintListner, item);
				}
	
				@Override
				public void onCancel() {
					// TODO Auto-generated method stub
	
				}
			});
			PosFormUtil.showLightBoxModal(parent,form);
		}

	}

	/**
	 * @param item
	 */
	private static void doRePrint(RootPaneContainer parent,IPosBillPrintFormListner billPrintListner,IPosBrowsableItem item) {
 
			switch ((PosPrintOption)item) {
			case PAYMENTRECEIPT:
				if(billPrintListner!=null)
					billPrintListner.onRePrintClicked(parent);
				break;
			case KITCHENRECEIPT:
				if(billPrintListner!=null)
					billPrintListner.onRePrintKitchenReceiptClicked(parent);
				break;
			case BARCODELABEL:
				if(billPrintListner!=null)
					billPrintListner.onRePrintBarcodeClicked(parent);
				break;
			case ITEMLABEL:
				if(billPrintListner!=null)
					billPrintListner.onRePrintItemLabelClicked(parent);
				break;
			case RESHITO:
				if(billPrintListner!=null)
					billPrintListner.onRePrintReshitoClicked(parent);
				break;

			}

		 
	}

	

}
