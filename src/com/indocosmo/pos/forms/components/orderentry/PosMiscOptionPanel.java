/**
 * 
 */
package com.indocosmo.pos.forms.components.orderentry;


import javax.swing.RootPaneContainer;

import com.indocosmo.pos.common.PosEnvSettings;
import com.indocosmo.pos.common.utilities.PosResUtil;
import com.indocosmo.pos.forms.components.buttons.PosButton;
import com.indocosmo.pos.forms.components.buttons.listners.IPosButtonListner;
import com.indocosmo.pos.forms.components.buttons.listners.PosButtonListnerAdapter;
import com.indocosmo.pos.forms.components.orderentry.listners.IPosMiscOperationsListener;
import com.indocosmo.pos.terminal.devices.PosDeviceManager;


/**
 * @author jojesh
 * This class will handle the POS additional 
 */
@SuppressWarnings("serial")
public final class PosMiscOptionPanel extends PosOrderEntryBottomMenuPanelBase{
	
	private static final String IMAGE_WM_NORMAL="wm_normal.png";
	private static final String IMAGE_WM_TOUCH="wm_touch.png";
	
	private static final String IMAGE_VOID_BUTTON_NORMAL="order_void_normal.png";
	private static final String IMAGE_VOID_BUTTON_TOUCH="order_void_touch.png";

	
	
	private PosButton wmButton;
	/**
	 * 
	 */
	public PosMiscOptionPanel(RootPaneContainer parent) {
		
		super(parent);
		initComponent();
		createButtons();
	}
	
	private void createButtons(){
		
		wmButton=PosEmptyPanel.createEmptyButton();
		wmButton.setImage(IMAGE_WM_NORMAL, IMAGE_WM_TOUCH);
		wmButton.setEnabled(PosDeviceManager.getInstance().hasWeighingMachine() && PosDeviceManager.getInstance().getWeighingMachine()!=null && PosDeviceManager.getInstance().getWeighingMachine().isDeviceInitialized());
		wmButton.setOnClickListner(new IPosButtonListner() {
			
			@Override
			public void onClicked(PosButton button) {
				
				if(mOperationsListener!=null)
					((IPosMiscOperationsListener)mOperationsListener).onWMButtonClicked();
				
			}
		});
		
		if(PosEnvSettings.getInstance().getUISetting().getOrderEntryUISettings().getMiscOptionsPanelSetting().isWeighingButtonVisible())
		addButtonToPanel(wmButton);
		
		PosButton imgButton=new PosButton();
		imgButton.setBounds(0, 0, BUTTON_WIDTH, BUTTON_HEIGHT);
		imgButton.setImage(PosResUtil.getImageIconFromResource(IMAGE_VOID_BUTTON_NORMAL));
		imgButton.setTouchedImage(PosResUtil.getImageIconFromResource(IMAGE_VOID_BUTTON_TOUCH));		
		imgButton.setOnClickListner(new PosButtonListnerAdapter() {
			@Override
			public void onClicked(PosButton button) {					
				if(mOperationsListener!=null)
					((IPosMiscOperationsListener)mOperationsListener).onVoidButtonClicked();
			}
		});
	 
		if(PosEnvSettings.getInstance().getUISetting().getOrderEntryUISettings().getMiscOptionsPanelSetting().isShowVoidButton())
			addButtonToPanel(imgButton);
		
	}


	public void validateComponent() {
		// TODO Auto-generated method stub
		
	}
	
	public void setListener(IPosMiscOperationsListener operationsListener){
		mOperationsListener=operationsListener;
	}
	
}
