/**
 * 
 */
package com.indocosmo.pos.forms.components.terminal.settings.devices.printers;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.RootPaneContainer;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import com.indocosmo.pos.common.enums.PosPrinterType;
import com.indocosmo.pos.common.utilities.PosFormUtil;
import com.indocosmo.pos.common.utilities.PosResUtil;
import com.indocosmo.pos.data.beans.terminal.device.BeanDevKitchenPrinterConfig;
import com.indocosmo.pos.data.beans.terminal.device.BeanKitchen;
import com.indocosmo.pos.forms.components.buttons.PosButton;
import com.indocosmo.pos.forms.components.buttons.listners.IPosButtonListner;
import com.indocosmo.pos.forms.components.checkboxes.PosCheckBox;
import com.indocosmo.pos.forms.components.textfields.PosItemBrowsableField;
import com.indocosmo.pos.forms.components.textfields.listners.IPosTouchableFieldListner;
import com.indocosmo.pos.forms.messageboxes.PosMessageBoxForm.MessageBoxButtonTypes;
import com.indocosmo.pos.forms.messageboxes.PosMessageBoxForm.MessageBoxResults;

/**
 * @author anand
 *
 */
@SuppressWarnings("serial")
public class PosKitchenPrinterServiceSelectionPanel extends PrinterServiceSelectionBasePanel<BeanDevKitchenPrinterConfig> {

	private JLabel mLabelKitchenName;
	private ArrayList<BeanKitchen> kitchenList;
	private PosCheckBox mChkIsMaster;
	private PosButton mBtnDeletePrinter;
	private JLabel mLabelKitchenID;
	private BeanKitchen currentKitchen;
	private PosItemBrowsableField mTxtKitchenID;
//	private PosCheckBox mChkPrintWithReceipt;
	private boolean isPrintWithReceiptEnable;

	/**
	 * @param parent
	 * @param type
	 * @param width
	 * @throws Exception 
	 */
	public PosKitchenPrinterServiceSelectionPanel(RootPaneContainer parent,
			PosPrinterType type, int width) throws Exception {
		super(parent, type, width);


	}

	/**
	 * @param parent
	 * @param type
	 * @param width
	 * @param height
	 * @throws Exception 
	 */
	public PosKitchenPrinterServiceSelectionPanel(RootPaneContainer parent,
			PosPrinterType type, int width,int height) throws Exception {

		super(parent, type, width,height);

	}
	/**
	 * @throws Exception 
	 * 
	 */
	@Override
	protected void initControls() throws Exception{

		super.initControls();
		
//		createPrintWithReceipt();
		createKitchenIDField();
		createKitchenNameField();
		createPrinterDeleteButton();
		createPrinterInfoField();
		createPrinterTestButton();

		createAllowPaperCutCheckBox();
		createPaperCut();
		createNoCopies();

		createPrintInAlternativeLangaugeCheckBox();
		createIsActiveCheckBox();
		createIsMasterCheckBox();
	}
	
//	/**
//	 * Creates the has printer check box
//	 */
//	protected void createPrintWithReceipt() {
//
//		final int left=mChkHasPrinter.getX()+mChkHasPrinter.getWidth()+PANEL_CONTENT_H_GAP;
//		
//		mChkPrintWithReceipt=new PosCheckBox("Print With Receipt");
//		mChkPrintWithReceipt.setMnemonic(KeyEvent.VK_R);
//		mChkPrintWithReceipt.setBounds(left, 0,250, CHK_BOX_HEIGHT);
//		mChkPrintWithReceipt.setFocusable(false);
//		mChkPrintWithReceipt.setOpaque(false);
//		mChkPrintWithReceipt.setForeground(PosFormUtil.SECTION_HEADING_PANEL_FG_COLOR);
//		mChkPrintWithReceipt.addItemListener(new ItemListener() {
//
//			@Override
//			public void itemStateChanged(ItemEvent e) {
//
//				setEnableControls();
//			}
//		});
//		
//		mChkPrintWithReceipt.setVisible(false);
//		mChkPrintWithReceipt.setSelected(false);
//		PosFormUtil.setCheckboxDefaultFont(mChkPrintWithReceipt);
//		mTitleOptionPanel.add(mChkPrintWithReceipt);
//	}
//	

	/**
	 * @return the currentKitchen
	 */
	public BeanKitchen getCurrentKitchen() {
		
		return currentKitchen;
	}

	/**
	 * @param currentKitchen the currentKitchen to set
	 */
	public void setCurrentKitchen(BeanKitchen currentKitchen) {
		
		this.currentKitchen = currentKitchen;
	}
	/**
	 * 
	 */
	private void createIsMasterCheckBox() {

		final JLabel mCheckBoxLabel = new JLabel("Master :");
		mCheckBoxLabel.setBackground(Color.LIGHT_GRAY);
		mCheckBoxLabel.setOpaque(true);
		mCheckBoxLabel.setBorder(new EmptyBorder(2,2,2,2));
		mCheckBoxLabel.setPreferredSize(new Dimension(LABEL_WIDTH-18,LABEL_HEIGHT));
		setLabelFont(mCheckBoxLabel);
		add(mCheckBoxLabel);

		mChkIsMaster = new PosCheckBox();
		mChkIsMaster.setMnemonic(KeyEvent.VK_C);
		mChkIsMaster.setIcon(PosResUtil.getImageIconFromResource("checkbox_normal.png"));
		mChkIsMaster.setSelectedIcon(PosResUtil.getImageIconFromResource("checkbox_selected.png"));
		mChkIsMaster.setPreferredSize(new Dimension(45,40));
		mChkIsMaster.setFocusable(false);
		mChkIsMaster.setEnabled(false);
		mChkIsMaster.setOpaque(true);
		mChkIsMaster.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				setEnableControls();
			}
		});
		PosFormUtil.setCheckboxDefaultFont(mChkIsMaster); 
		add(mChkIsMaster);
	}	
	private void createPrinterDeleteButton() {

		mBtnDeletePrinter = new PosButton("Delete");
		mBtnDeletePrinter.setImage(PRINT_DELETE_BUTTON_NORMAL);
		mBtnDeletePrinter.setTouchedImage(PRINT_DELETE_BUTTON_TOUCHED);
		mBtnDeletePrinter.setSize(PRINT_TEST_BUTTON_WIDTH,
				PRINT_TEST_BUTTON_HEIGHT);
		mBtnDeletePrinter.setEnabled(false);
		mBtnDeletePrinter.setOnClickListner(new IPosButtonListner() {

			@Override
			public void onClicked(PosButton button){
				try {
					if (mPrinterInfo == null) {
						PosFormUtil
						.showErrorMessageBox(mParent,
								"No printer selected!!!. Please select a printer.");
						return;
					}
					final MessageBoxResults mResult=PosFormUtil
							.showQuestionMessageBox(
									mParent,
									MessageBoxButtonTypes.YesNo,
									"You are about to delete the printer Configurations of "+mLabelKitchenName.getText()+". Want to proceed?",null);
					if(mResult==MessageBoxResults.Yes){
						getPrinterConfig(mTxtKitchenID.getSelectedValue());
						onDelete();
					}

				} catch (Exception e) {
					e.printStackTrace();
					// PosFormUtil.showErrorMessageBox(mParent,
					// "Failed to print the test document. Please check your printer!!!");
				}
			}
		});
		add(mBtnDeletePrinter);
	}
	/**
	 * @return the kitchenList
	 */
	public ArrayList<BeanKitchen> getKitchenList() {
		
		return kitchenList;
	}

	/**
	 * @param kitchenList the kitchenList to set
	 */
	public void setKitchenList(ArrayList<BeanKitchen> kitchenList) {
		
		this.kitchenList = kitchenList;
		mTxtKitchenID.setBrowseItemList(kitchenList);
	}

	/**
	 * 
	 */
	@Override
	public void setprinterDetails() {
		
		if(kitchenList!=null&&kitchenList.size()>0){
			if(printerConfigList.size()>0){
				//				setPrinterConfig(printerConfigList.get(0));
				for(BeanKitchen kitchen:kitchenList){
					if(kitchen.getId()==printerConfigList.get(0).getKitchenId()){

						mTxtKitchenID.setSelectedItem(kitchen);
						break;

					}
				}
			}
			else
				mTxtKitchenID.setSelectedItem(kitchenList.get(0));
		}
		else{
			mTxtKitchenID.setSelectedItem(null);
			mChkHasPrinter.setEnabled(false);
		}
	}

	/**
	 * 
	 */
	private void createKitchenNameField() {
		
		mLabelKitchenName = new JLabel();
		mLabelKitchenName.setPreferredSize(new Dimension(406,LABEL_HEIGHT));
		mLabelKitchenName.setOpaque(false);
		mLabelKitchenName.setBorder(new LineBorder(Color.decode("#b8cfe5")));
		mLabelKitchenName.setBackground(Color.BLUE);
		mLabelKitchenName.setFont(PosFormUtil.getTextFieldFont());
		add(mLabelKitchenName);

	}
	private boolean onDelete(){
		
		clearBeanData(mPrinterConfig);

		return true;
	}
	/**
	 * @param printerConfig
	 */
	private void clearBeanData(BeanDevKitchenPrinterConfig printerConfig) {

		printerConfig.setPrinterInfo(null);
		printerConfig.setPaperCutOn(false);
		printerConfig.setPaperCutCode(null);
		printerConfig.setNoCopies(1);
		printerConfig.setUseAltLangugeToPrint(false);
		printerConfig.setActive(false);
		printerConfig.setAsMaster(false);
		setPrinterConfig();
	}

	protected void createKitchenIDField() {

		mLabelKitchenID = new JLabel("<html>Kitchen<font color=#FF0000>*</font> :</html>");
		mLabelKitchenID.setOpaque(true);
		mLabelKitchenID.setBackground(Color.LIGHT_GRAY);
		mLabelKitchenID.setBorder(new EmptyBorder(2, 2, 2, 2));
		mLabelKitchenID.setPreferredSize(new Dimension( LABEL_WIDTH, LABEL_HEIGHT));
		setLabelFont(mLabelKitchenID);
		add(mLabelKitchenID);

		mTxtKitchenID = new PosItemBrowsableField(mParent, 182);
		mTxtKitchenID.setTitle("Kitchen ID?");
		mTxtKitchenID.setEnabled(false);
		mTxtKitchenID.setListner(new IPosTouchableFieldListner() {

			@Override
			public void onValueSelected(Object value) {
				
				if(getCurrentKitchen()!=null)
					saveCurrentPrinter();
				getPrinterConfig(value);
				setPrinterConfig();

				setCurrentKitchen((BeanKitchen)value);
			}

			@Override
			public void onReset() {
				mTxtKitchenID.setSelectedItem(kitchenList.get(0));

			}
		});

		add(mTxtKitchenID);

	}

	private void getPrinterConfig(Object value) {
		
		if(value!=null){
			
			final BeanKitchen kitchen = (BeanKitchen)value;
			BeanDevKitchenPrinterConfig kitchenPrinterConfig = null;// = (BeanKitchenPrinterConfig)mPrinterConfig;
			int i=0;
			for(;i<printerConfigList.size();i++){
				if(printerConfigList.get(i).getKitchenId()==kitchen.getId()){
					kitchenPrinterConfig=printerConfigList.get(i);
					break;
				}
			}
			if(kitchenPrinterConfig==null){
				kitchenPrinterConfig=new BeanDevKitchenPrinterConfig();
				kitchenPrinterConfig.setKitchenId(kitchen.getId());
				printerConfigList.add(kitchenPrinterConfig);
			}

			mPrinterConfig=kitchenPrinterConfig;
		}else
			mPrinterConfig=null;

	}

	public void saveCurrentPrinter(){
		
		super.getPrinterConfig();
		mPrinterConfig.setKitchenId(getCurrentKitchen().getId());
		mPrinterConfig.setAsMaster(mChkIsMaster.isSelected());
		mPrinterConfig.setActive(mchkIsActive.isSelected());
	}

	//	private BeanDevKitchenPrinterConfig getCurrentKitchenPrinterConfig(){
	//
	//		
	//		return mPrinterConfig;
	//		
	//	}
	/* (non-Javadoc)
	 * @see com.indocosmo.pos.forms.components.terminal.settings.devices.printers.PrinterServiceSelectionPanel#setPrinterConfig(com.indocosmo.pos.data.beans.terminal.device.BeanDevPrinterConfig)
	 */
	@Override
	public void setPrinterConfig() {
		
		if(mPrinterConfig!=null&&(kitchenList!=null&&kitchenList.size()>0)){
			super.setPrinterConfig();
			mchkIsActive.setSelected(mPrinterConfig.isActive());
			mChkIsMaster.setSelected(mPrinterConfig.isMaster());
			for (int i=0;i<kitchenList.size();i++){

				if(kitchenList.get(i).getId()==mPrinterConfig.getKitchenId()){
					mTxtKitchenID.setText(kitchenList.get(i).getCode());
					mLabelKitchenName.setText(kitchenList.get(i).getName());
					break;
				}
			}
		}
		else{

		}
	}
	/* (non-Javadoc)
	 * @see javax.swing.JComponent#setEnabled(boolean)
	 */
	@Override
	protected void setEnableControls() {
		
		super.setEnableControls();
		if(mTxtKitchenID!=null)
			mTxtKitchenID.setEnabled(mChkHasPrinter.isSelected());
		
		mchkIsActive.setEnabled(mChkHasPrinter.isSelected());
		mChkIsMaster.setEnabled(mChkHasPrinter.isSelected());
		mBtnDeletePrinter.setEnabled(mChkHasPrinter.isSelected());
		mchkIsActive.setEnabled(mChkHasPrinter.isSelected());
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.pos.forms.components.terminal.settings.devices.printers.PrinterServiceSelectionBasePanel#validatePrinter()
	 */
	@Override
	public boolean validatePrinter() {
		
		if(getCurrentKitchen()!=null)
			saveCurrentPrinter();
		if(mChkHasPrinter.isSelected()){
			//			int no=0;
			boolean sucess=true;
			for(BeanDevKitchenPrinterConfig printerConfig:printerConfigList){

				if(printerConfig.getPrinterInfo()!=null){
					//					no++;
					if(!validatePaperCutOptions(printerConfig)){
						sucess=false;
						for(BeanKitchen kitchen:kitchenList){

							if(kitchen.getId()==printerConfig.getKitchenId()){

								PosFormUtil.showErrorMessageBox(mParent,"Paper cut option selected for "+kitchen.getName()+"!!! Please enter the paper cut code.");
								mTxtKitchenID.setSelectedItem(kitchen);
								break;
							}
						}
						break;
					}
					if(!validateNoOfCopies(printerConfig)){
						sucess=false;
						for(BeanKitchen kitchen:kitchenList){

							if(kitchen.getId()==printerConfig.getKitchenId()){

								PosFormUtil.showErrorMessageBox(mParent,"Select a minimum of '1' no of copies for "+kitchen.getName());
								mTxtKitchenID.setSelectedItem(kitchen);
								break;
							}
						}
						break;
					}
				}
			}
			
			return sucess;
		}
		
		return true;
	}

	/**
	 * @return
	 */
	private boolean validateNoOfCopies(BeanDevKitchenPrinterConfig printerConfig) {
		
		boolean result= true;
		if(printerConfig.getNoCopies()<1)
			result=false;
		
		return result;
	}

	/**
	 * @param printerConfig
	 */
	private boolean validatePaperCutOptions(BeanDevKitchenPrinterConfig printerConfig) {
		
		boolean result=true;
		if(printerConfig.isPaperCutOn()){
			if(printerConfig.getPaperCutCode()==null||printerConfig.getPaperCutCode().equals(""))
				result=false;
		}
		
		return result;
	}
	
//	/**
//	 * @param isEnabled
//	 */
//	public void setPrintWithReceipt(boolean isEnabled){
//		
//		if(isPrintWithReceiptEnable)
//			mChkPrintWithReceipt.setSelected(isEnabled);
//		else
//			mChkPrintWithReceipt.setSelected(false);
//	}
	
//	/**
//	 * @return
//	 */
//	public boolean isPrintWithReceipt(){
//		
//		return	mChkPrintWithReceipt.isSelected();
//	}

//	/**
//	 * @param isPrintWithReceiptEnable the isPrintWithReceiptEnable to set
//	 */
//	public void setPrintWithReceiptEnable(boolean isEnable) {
//		
//		this.isPrintWithReceiptEnable = isEnable;
//		
//		mChkPrintWithReceipt.setEnabled(isEnable);
//		mChkPrintWithReceipt.setVisible(isEnable);
//		
//		if(!isEnable)
//			setPrintWithReceipt(false);
//		
//	}
}
