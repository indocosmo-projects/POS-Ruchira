package com.indocosmo.pos.forms;

import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.JPanel;

import com.indocosmo.pos.common.PosConstants;
import com.indocosmo.pos.common.PosEnvSettings;
import com.indocosmo.pos.common.utilities.PosFormUtil;
import com.indocosmo.pos.forms.components.tab.PosTab;
import com.indocosmo.pos.forms.components.tab.PosTabControl;
import com.indocosmo.pos.forms.components.tab.listner.IPosTabControlListner;
import com.indocosmo.pos.forms.components.terminal.settings.PosTerminalInitSynchPanel;
import com.indocosmo.pos.forms.components.terminal.settings.PosTerminalInitSynchPanel.IInitialSyncPanelListner;
import com.indocosmo.pos.forms.components.terminal.settings.PosTerminalSettingsPanel;
import com.indocosmo.pos.forms.components.terminal.settings.PosTermnalSettingsBase;
import com.indocosmo.pos.forms.components.terminal.settings.devices.PosDevCashBoxSettingsTab;
import com.indocosmo.pos.forms.components.terminal.settings.devices.PosDevEFTSettingsTab;
import com.indocosmo.pos.forms.components.terminal.settings.devices.PosDevPoleDisplaySettingsTab;
import com.indocosmo.pos.forms.components.terminal.settings.devices.PosDevPrinterSettingTab;
import com.indocosmo.pos.forms.components.terminal.settings.devices.PosDevWeighingMachineSettingsTab;
import com.indocosmo.pos.forms.listners.IPosTerminalSettingsFormListner;
import com.indocosmo.pos.forms.messageboxes.PosMessageBoxForm.MessageBoxButtonTypes;
import com.indocosmo.pos.forms.messageboxes.listners.PosMessageBoxFormListnerAdapter;
import com.indocosmo.pos.terminal.devices.PosDeviceManager;

public  class PosTerminalSettingsForm extends PosBaseForm{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public enum SettingsModes{
		user,
		terminal,
		device,		
		database
	}

	private static final int CONTENT_PANEL_HEIGHT=PosTermnalSettingsBase.LAYOUT_HEIGHT+PosTabControl.BUTTON_HEIGHT; 
	private static final int CONTENT_PANEL_WIDTH=PosTermnalSettingsBase.LAYOUT_WIDTH;

	private JPanel mContentPanel;

	private PosTerminalSettingsPanel mTerminalSettingsPanel;	
	private PosDevPoleDisplaySettingsTab mPoleDisplayPanel;
	private PosDevPrinterSettingTab mPrinterPanel;
	private PosDevCashBoxSettingsTab mCashBoxPanel;
	private PosDevWeighingMachineSettingsTab mWMPanel;
	private PosTerminalInitSynchPanel mTerminalInitSynchPanel;
	
	private IPosTerminalSettingsFormListner mPosTerminalFormListner;
	private PosTabControl mTerminalSettingsTab;
//	private PosUISettingsPanel mPosUISettingsPanel;
	private ArrayList<PosTab> mTabList;
	private PosDevEFTSettingsTab mPosEFTSettingsPanel;
	
	public PosTerminalSettingsForm(){
		super("Terminal Settings", CONTENT_PANEL_WIDTH, CONTENT_PANEL_HEIGHT);
		setTabContents();
	}	

	private void setTabContents(){
		
		mTabList=new ArrayList<PosTab>();
		if(PosEnvSettings.getInstance().getShop()!=null){
			
			mTerminalSettingsPanel=new PosTerminalSettingsPanel(this);
			mTabList.add((PosTab) mTerminalSettingsPanel);

			
			if(PosEnvSettings.getInstance().getUISetting().getTerminalUISettings().isShowPoleSettingsTab()){
				mPoleDisplayPanel=new PosDevPoleDisplaySettingsTab(this);
				mTabList.add(mPoleDisplayPanel);
			}
			
				mPrinterPanel=new PosDevPrinterSettingTab(this);
				mTabList.add( mPrinterPanel);
			
			mPrinterPanel.setTerminalSettings(mTerminalSettingsPanel.getTerminalSettings());

			if(PosEnvSettings.getInstance().getUISetting().getTerminalUISettings().isShowCashboxSettingsTab()){
				mCashBoxPanel=new PosDevCashBoxSettingsTab(this);
				mTabList.add(mCashBoxPanel);
			}
			
			if(PosEnvSettings.getInstance().getUISetting().getTerminalUISettings().isShowWeighingSettingsTab()){
				mWMPanel=new PosDevWeighingMachineSettingsTab(this);
				mTabList.add(mWMPanel);
			}

			/** Deprecated as the settings moved to property file**/
			//				mPosUISettingsPanel = new PosUISettingsPanel(this);
			//				mTabList.add(mPosUISettingsPanel);

			if(PosEnvSettings.getInstance().getUISetting().getTerminalUISettings().isShowEFTSettingsTab()){
				
				mPosEFTSettingsPanel = new PosDevEFTSettingsTab(this);
				mTabList.add(mPosEFTSettingsPanel);
			}


		}else  {
			mTerminalInitSynchPanel=new PosTerminalInitSynchPanel(this);
			mTerminalInitSynchPanel.setListener(new IInitialSyncPanelListner() {

				@Override
				public void onShopeCodeEntered(String code) {

					onOkButtonClicked();						
				}
			});
			mTabList.add((PosTab) mTerminalInitSynchPanel);
		}
		mTerminalSettingsTab=new PosTabControl(mTabList, mContentPanel.getWidth(), mContentPanel.getHeight(),mTabSelectedListner);
		mContentPanel.add(mTerminalSettingsTab);
	}	

	private IPosTabControlListner mTabSelectedListner=new IPosTabControlListner() {
		@Override
		public void onTabSelected(int index) {
			
			setTitle(mTabList.get(index).getTabCaption()+" Settings");
		}
	};

	private void confirmRestart(){
		PosFormUtil.showQuestionMessageBox(this, MessageBoxButtonTypes.YesNo, 
				"Terminal settings has been changed. Restart the application to reflect the changes. Do you want quit application now?", 
				new PosMessageBoxFormListnerAdapter() {

			@Override
			public void onYesButtonPressed() {
				super.onYesButtonPressed();
				if (PosDeviceManager.getInstance() != null)
					try {
						PosDeviceManager.getInstance().shutdownDevices();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				System.exit(PosConstants.RESTART_EXIT_CODE);
			}
			@Override
			public void onNoButtonPressed() {
				closeWindow();
				super.onNoButtonPressed();
			}
		});
		setVisible(false);
		if(mPosTerminalFormListner!=null)
			mPosTerminalFormListner.onExit();
		dispose();	
	}

	public void closeWindow(){
		PosTerminalSettingsForm.this.setVisible(false);
		dispose();
	}

	public void setListner(IPosTerminalSettingsFormListner listner){
		mPosTerminalFormListner=listner;
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.pos.forms.PosBaseForm#setContentPanel(javax.swing.JPanel)
	 */
	@Override
	protected void setContentPanel(JPanel panel) {
		mContentPanel = panel;
	}
	/* (non-Javadoc)
	 * @see com.indocosmo.pos.forms.PosBaseForm#onOkButtonClicked()
	 */
	@Override
	public boolean onOkButtonClicked() {
		for(PosTab tab: mTabList){
			PosTermnalSettingsBase terminalSettings=(PosTermnalSettingsBase)tab;
			if(!terminalSettings.onValidating()){
				mTerminalSettingsTab.setSelectedTab(tab);
				return false;
			}
		}
		for(PosTab tab: mTabList){
			PosTermnalSettingsBase terminalSettings=(PosTermnalSettingsBase)tab;
			if(!terminalSettings.onSave()) return false;
		}
		
		confirmRestart();
		return true;
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.pos.forms.PosBaseForm#onWindowOpened(java.awt.event.WindowEvent)
	 */
	@Override
	protected void onWindowOpened(WindowEvent e) {

		mTerminalSettingsTab.getSelectedTab().onGotFocus();
	}

	@Override
	public boolean onCancelButtonClicked() {
		setVisible(false);
		if(mPosTerminalFormListner!=null)
			mPosTerminalFormListner.onExit();
		dispose();	
		return true;
	}
}
