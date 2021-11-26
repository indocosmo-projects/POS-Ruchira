/**
 * 
 */
package com.indocosmo.pos.forms.components.orderentry;


import java.awt.event.KeyEvent;

import javax.swing.RootPaneContainer;

import com.indocosmo.pos.common.PosEnvSettings;
import com.indocosmo.pos.common.PosLog;
import com.indocosmo.pos.common.utilities.PosAccessPermissionsUtil;
import com.indocosmo.pos.common.utilities.PosDateUtil;
import com.indocosmo.pos.common.utilities.PosFormUtil;
import com.indocosmo.pos.common.utilities.PosResUtil;
import com.indocosmo.pos.data.beans.BeanAccessLog;
import com.indocosmo.pos.data.beans.BeanUser;
import com.indocosmo.pos.data.providers.shopdb.PosAccessLogProvider;
import com.indocosmo.pos.forms.PosUserAuthenticateForm;
import com.indocosmo.pos.forms.components.buttons.PosButton;
import com.indocosmo.pos.forms.components.buttons.listners.IPosButtonListner;
import com.indocosmo.pos.forms.components.buttons.listners.PosButtonListnerAdapter;
import com.indocosmo.pos.forms.components.orderentry.listners.IPosPrintOperationsListener;
import com.indocosmo.pos.terminal.devices.PosDeviceManager;
import com.indocosmo.pos.terminal.devices.printer.PosDeviceReceiptPrinter;


/**
 * @author jojesh
 * This class will handle the POS operations like Payments, Print and other Misc operations.
 */
@SuppressWarnings("serial")
public final class PosPrintOptionPanel extends PosOrderEntryBottomMenuPanelBase{
	
	
	private static final String IMAGE_PRINT_NORMAL="print.png";
	private static final String IMAGE_PRINT_TOUCH="print_touch.png";
	private static final String IMAGE_KITCHEN_PRINT_NORMAL="kitchen_printer.png";
	private static final String IMAGE_KITCHEN_PRINT_TOUCH="kitchen_printer_touch.png";
	private static final String IMAGE_CASH_BOX_NORMAL="cash_box_normal.png";
	private static final String IMAGE_CASH_BOX_TOUCH="cash_box_touch.png";
	
	/**
	 * 
	 */
	public PosPrintOptionPanel(RootPaneContainer parent) {
		super(parent);
		createButtons();
	}

	
	private void createButtons(){
		
		createPrintButton();
		createKitechenPrintButton();
		createOpenCashBoxButton();
	}
	
	/**
	 * 
	 */
	private void createKitechenPrintButton() {
		PosButton imgButton=new PosButton();
		imgButton.registerKeyStroke(KeyEvent.VK_K,KeyEvent.CTRL_DOWN_MASK);
		imgButton.setBounds(0, 0, BUTTON_WIDTH, BUTTON_HEIGHT);
		imgButton.setImage(PosResUtil.getImageIconFromResource(IMAGE_KITCHEN_PRINT_NORMAL));
		imgButton.setTouchedImage(PosResUtil.getImageIconFromResource(IMAGE_KITCHEN_PRINT_TOUCH));		
		imgButton.setOnClickListner(new PosButtonListnerAdapter() {
			@Override
			public void onClicked(PosButton button) {					
				if(mOperationsListener!=null)
					((IPosPrintOperationsListener)mOperationsListener).onKitchenPrintClicked();
			}
		});
		imgButton.setEnabled(PosDeviceManager.getInstance().hasKichenPrinter());
		
		if(PosEnvSettings.getInstance().getUISetting().getOrderEntryUISettings().getPrintePanelSettings().isShowKitchenPrintButton())
			addButtonToPanel(imgButton);
	}


	private void createPrintButton(){
		PosButton imgButton=new PosButton();
		imgButton.registerKeyStroke(KeyEvent.VK_P,KeyEvent.CTRL_DOWN_MASK);
		imgButton.setBounds(0, 0, BUTTON_WIDTH, BUTTON_HEIGHT);
		imgButton.setImage(PosResUtil.getImageIconFromResource(IMAGE_PRINT_NORMAL));
		imgButton.setTouchedImage(PosResUtil.getImageIconFromResource(IMAGE_PRINT_TOUCH));		
		imgButton.setOnClickListner(new PosButtonListnerAdapter() {
			@Override
			public void onClicked(PosButton button) {					
				if(mOperationsListener!=null)
					((IPosPrintOperationsListener)mOperationsListener).onPrintClicked();
			}
		});
		imgButton.setEnabled(PosDeviceManager.getInstance().hasReceiptPrinter());
		
		if(PosEnvSettings.getInstance().getUISetting().getOrderEntryUISettings().getPrintePanelSettings().isShowReceiptPrintButton())
			addButtonToPanel(imgButton);
	}
	
	public void validateComponent() {
		// TODO Auto-generated method stub
		
	}
	
	private void createOpenCashBoxButton() {
		PosButton openCashBoxbutton;
		openCashBoxbutton=new PosButton();
		openCashBoxbutton.registerKeyStroke(KeyEvent.VK_B,KeyEvent.CTRL_DOWN_MASK);
		openCashBoxbutton.setSize(BUTTON_WIDTH, BUTTON_HEIGHT);
		openCashBoxbutton.setImage(PosResUtil.getImageIconFromResource(IMAGE_CASH_BOX_NORMAL));
		openCashBoxbutton.setTouchedImage(PosResUtil.getImageIconFromResource(IMAGE_CASH_BOX_TOUCH));
		openCashBoxbutton.setOnClickListner(openCashBoxButtonListener);
		openCashBoxbutton.setEnabled(false);
		

		if(PosDeviceManager.getInstance().hasReceiptPrinter() && PosDeviceManager.getInstance().getReceiptPrinter()!=null && PosDeviceManager.getInstance().getReceiptPrinter().hasCashBox()){
			if(PosDeviceManager.getInstance().getReceiptPrinter().canOpenManually()){
				openCashBoxbutton.setEnabled(true);				
			}
		}else if (PosDeviceManager.getInstance().hasCashBox() && PosDeviceManager.getInstance().getCashBox().canOpenManually()){
			openCashBoxbutton.setEnabled(true);		
		}
		
		if(PosEnvSettings.getInstance().getUISetting().getOrderEntryUISettings().getPrintePanelSettings().isShowCashBoxButton())
			addButtonToPanel(openCashBoxbutton);
	}
	
	private IPosButtonListner openCashBoxButtonListener = new IPosButtonListner() {
		
		private PosAccessLogProvider accesslogProvider;

		@Override
		public void onClicked(PosButton button) {
			PosUserAuthenticateForm loginForm=new PosUserAuthenticateForm("Open cash drawer? ");
			PosFormUtil.showLightBoxModal(mParent,loginForm);
			BeanUser user=loginForm.getUser();
			if(user!=null&&PosAccessPermissionsUtil.validateAccess(mParent,user.getUserGroupId(), "open_cash_drawer")){
				BeanAccessLog accessLog= new BeanAccessLog();
				accessLog.setFunctionName("open_cash_drawer");
				accessLog.setUserName(user.getName());
				accessLog.setAccessTime(PosDateUtil.getDateTime());
				accesslogProvider = new PosAccessLogProvider();
				accesslogProvider.updateAccessLog(accessLog);
				
				try {
					PosDeviceManager.getInstance().getReceiptPrinter().openCashBoxManually();
				} catch (Exception e) {
					PosLog.write(this, "openCashBoxButtonListener", e);
				}
			}
			
		}
	};
	
}
