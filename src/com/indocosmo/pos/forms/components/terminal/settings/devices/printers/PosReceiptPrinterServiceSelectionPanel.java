/**
 * 
 */
package com.indocosmo.pos.forms.components.terminal.settings.devices.printers;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;

import javax.swing.JLabel;
import javax.swing.RootPaneContainer;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import com.indocosmo.pos.common.enums.PosPrinterType;
import com.indocosmo.pos.common.enums.device.PrinterType;
import com.indocosmo.pos.common.utilities.PosFormUtil;
import com.indocosmo.pos.common.utilities.PosResUtil;
import com.indocosmo.pos.data.beans.terminal.device.BeanDevKitchenPrinterConfig;
import com.indocosmo.pos.data.beans.terminal.device.BeanDevReceiptPrinterConfig;
import com.indocosmo.pos.data.beans.terminal.device.BeanKitchen;
import com.indocosmo.pos.forms.components.buttons.PosButton;
import com.indocosmo.pos.forms.components.buttons.listners.IPosButtonListner;
import com.indocosmo.pos.forms.components.checkboxes.PosCheckBox;
import com.indocosmo.pos.forms.components.textfields.PosItemBrowsableField;
import com.indocosmo.pos.forms.components.textfields.PosTouchableTextField;
import com.indocosmo.pos.forms.components.textfields.listners.IPosTouchableFieldListner;
import com.indocosmo.pos.forms.messageboxes.PosMessageBoxForm.MessageBoxButtonTypes;
import com.indocosmo.pos.forms.messageboxes.PosMessageBoxForm.MessageBoxResults;

/**
 * @author anand
 *
 */
public class PosReceiptPrinterServiceSelectionPanel extends PrinterServiceSelectionBasePanel<BeanDevReceiptPrinterConfig> {

	private PosCheckBox mChkHasCashBox;
	private PosCheckBox mChkAllowOpenCashBox;
	private PosItemBrowsableField mTxtType; 
	private PosButton mBtnDeletePrinter ;
	private PrinterType currentPrinterType;

	private boolean mEnableCashBoxOptions=false;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param parent
	 * @param type
	 * @param width
	 * @throws Exception 
	 */
	public PosReceiptPrinterServiceSelectionPanel(RootPaneContainer parent,
			PosPrinterType type, int width) throws Exception {
		super(parent, type, width,237);

	}
	public PosReceiptPrinterServiceSelectionPanel(RootPaneContainer parent,
			PosPrinterType type, int width,int height) throws Exception {
		super(parent, type, width,height);

	}
	/**
	 * @throws Exception 
	 * 
	 */
	@Override
	protected void initControls() throws Exception {

		super.initControls();

		createPrinterTypeField();
		createPrinterDeleteButton();
		createPrinterInfoField();
		createPrinterTestButton();

		createAllowPaperCutCheckBox();
		createPaperCut();
		createNoCopies();
		createCashBoxAttachedCheckBox();
		createCashBox();

		createCashBoxManualCheckBox();	
		createPrintInAlternativeLangaugeCheckBox();

		mChkHasPrinter.setSelected(false);

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
									"You are about to delete the printer Configurations of "+mTxtType.getText()+". Want to proceed?",null);
					if(mResult==MessageBoxResults.Yes){
						getPrinterConfig(mTxtType.getSelectedValue());
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
	 * @return
	 */
	private boolean onDelete(){
		
		clearBeanData(mPrinterConfig);

		return true;
	}
	/**
	 * @param printerConfig
	 */
	private void clearBeanData(BeanDevReceiptPrinterConfig printerConfig) {

		printerConfig.setPrinterInfo(null);
		printerConfig.setPaperCutOn(false);
		printerConfig.setPaperCutCode(null);
		printerConfig.setNoCopies(1);
		printerConfig.setUseAltLangugeToPrint(false);
		printerConfig.setActive(false);
		printerConfig.setPrinterType(null);
		setPrinterConfig();
	}

	private void createTypeNameField() {

		final JLabel mLabelKitchenName = new JLabel();
		mLabelKitchenName.setPreferredSize(new Dimension(406,LABEL_HEIGHT));
		mLabelKitchenName.setOpaque(false);
		mLabelKitchenName.setBorder(new LineBorder(Color.decode("#b8cfe5")));
		mLabelKitchenName.setBackground(Color.BLUE);
		mLabelKitchenName.setFont(PosFormUtil.getTextFieldFont());
		add(mLabelKitchenName);

	}

	protected void createPrinterTypeField() {

		final JLabel mLabelType = new JLabel("<html>Type<font color=#FF0000>*</font> :</html>");
		mLabelType.setOpaque(true);
		mLabelType.setBackground(Color.LIGHT_GRAY);
		mLabelType.setBorder(new EmptyBorder(2, 2, 2, 2));
		mLabelType.setPreferredSize(new Dimension( LABEL_WIDTH, LABEL_HEIGHT));
		setLabelFont(mLabelType);
		add(mLabelType);

		mTxtType = new PosItemBrowsableField(mParent, 589);
		mTxtType.setTitle("Printer Type?");
		mTxtType.setEnabled(false);
		mTxtType.setBrowseItemList(PrinterType.values());
		mTxtType.setListner(new IPosTouchableFieldListner() {

			@Override
			public void onValueSelected(Object value) {

				if(getCurrentPrinterType()!=null)
					saveCurrentPrinter();
				
				getPrinterConfig(value);
				setPrinterConfig();
				setCurrentPrinterType((PrinterType)value);

			}

			@Override
			public void onReset() {

			}
		});

		add(mTxtType);

	}

	public void saveCurrentPrinter(){

		getPrinterConfig();
		mPrinterConfig.setPrinterType(currentPrinterType);

	}


	@Override
	public void setprinterDetails() {
		
		final BeanDevReceiptPrinterConfig receiptPrinterConfig = (printerConfigList==null||printerConfigList.size()==0)?null: printerConfigList.get(0);
		if(receiptPrinterConfig!=null){
			mTxtType.setSelectedItem(receiptPrinterConfig.getPrinterType());
			mPrinterConfig=receiptPrinterConfig;
			currentPrinterType=receiptPrinterConfig.getPrinterType();
			setPrinterConfig();	
		}

	}
	/* (non-Javadoc)
	 * @see com.indocosmo.pos.forms.components.terminal.settings.devices.printers.PrinterServiceSelectionPanel#setEnableControls()
	 */
	@Override
	protected void setEnableControls() {

		super.setEnableControls();
		mChkHasCashBox.setEnabled(mChkHasPrinter.isSelected()&&mEnableCashBoxOptions);
		mChkAllowOpenCashBox.setEnabled(mChkHasPrinter.isSelected()&&mChkHasCashBox.isSelected()&&mEnableCashBoxOptions);
		mTxtCashBox.setEnabled(mChkHasPrinter.isSelected()&&mChkHasCashBox.isSelected()&&mEnableCashBoxOptions);
		mTxtType.setEnabled(mChkHasPrinter.isSelected());
		mBtnDeletePrinter.setEnabled(mChkHasPrinter.isSelected());

	}
	
	/**
	 * Validate the cash box options
	 * @return
	 */
	protected boolean validateCashBoxOptions(){
		boolean result=true;
		if(!mEnableCashBoxOptions) 
			result= true;
		else if(mChkHasCashBox.isSelected()){
			if(mTxtCashBox.getText().equals("")){
				PosFormUtil
				.showErrorMessageBox(mParent,
						"Cash Box is enabled!!! Please enter Cash Box open code.");
				mTxtCashBox.requestFocus();
				result=false;
			}
		}
		return result;
	}
	
	/* (non-Javadoc)
	 * @see com.indocosmo.pos.forms.components.terminal.settings.devices.printers.PrinterServiceSelectionBasePanel#validatePrinter()
	 */
	@Override
	public boolean validatePrinter() {

		if(getCurrentPrinterType()!=null)
			saveCurrentPrinter();
		
		boolean result=true;
		
		
		if(mChkHasPrinter.isSelected()){
			int counter=0;
			for(BeanDevReceiptPrinterConfig printerConfig:printerConfigList){

				if(printerConfig.getPrinterInfo()!=null)
					++counter;
//				if(printerConfig.getPrinterInfo()==null){
//					PosFormUtil
//					.showErrorMessageBox(mParent,
//							"Receipt printer " + printerConfig.getPrinterType().getDisplayText() + " is not selected!!! Please select a printer.");
//					result=false;
//					break;

//				}else 
				if(super.validatePrinter()){

					if(validateCashBoxOptions())
						result= true;
				}
			}
			if(counter==0){
				PosFormUtil
				.showErrorMessageBox(mParent,
						"Receipt printer is not selected!!! Please select a printer.");
				result=false;
			}
		}
		return result;
	}
	
	/**
	 * 
	 */
	private void createCashBoxAttachedCheckBox() {

		final JLabel mCheckBoxLabel = new JLabel("Cash Box :");
		mCheckBoxLabel.setBackground(Color.LIGHT_GRAY);
		mCheckBoxLabel.setOpaque(true);
		mCheckBoxLabel.setBorder(new EmptyBorder(2,2,2,2));
		mCheckBoxLabel.setPreferredSize(new Dimension(LABEL_WIDTH,LABEL_HEIGHT));
		setLabelFont(mCheckBoxLabel);
		add(mCheckBoxLabel);

		mChkHasCashBox= new PosCheckBox();
		mChkHasCashBox.setMnemonic(KeyEvent.VK_P);
		mChkHasCashBox.setIcon(PosResUtil.getImageIconFromResource("checkbox_normal.png"));
		mChkHasCashBox.setSelectedIcon(PosResUtil.getImageIconFromResource("checkbox_selected.png"));
		mChkHasCashBox.setSelected(false);
		mChkHasCashBox.setBounds(mChkAllowPaperCut.getX()
				+mChkAllowPaperCut.getWidth(), 0, 200, CHK_BOX_HEIGHT);
		mChkHasCashBox.setPreferredSize(new Dimension(45,40));
		mChkHasCashBox.setOpaque(true);
		mChkHasCashBox.setFocusable(false);
		mChkHasCashBox.setEnabled(false);
		mChkHasCashBox.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {

				if(!mChkHasCashBox.isSelected()){
					mChkAllowOpenCashBox.setSelected(false);
				}

				setEnableControls();
			}
		});
		PosFormUtil.setCheckboxDefaultFont(mChkHasCashBox);
		add(mChkHasCashBox);
	}
	
	/**
	 * Creates the has CashBox manual operation
	 */
	private void createCashBoxManualCheckBox() {

		final JLabel mCheckBoxLabel = new JLabel("Open Manually :");
		mCheckBoxLabel.setBackground(Color.LIGHT_GRAY);
		mCheckBoxLabel.setOpaque(true);
		mCheckBoxLabel.setBorder(new EmptyBorder(2,2,2,2));
		mCheckBoxLabel.setPreferredSize(new Dimension(LABEL_WIDTH+53,LABEL_HEIGHT));
		setLabelFont(mCheckBoxLabel);
		add(mCheckBoxLabel);

		mChkAllowOpenCashBox= new PosCheckBox();
		mChkAllowOpenCashBox.setMnemonic(KeyEvent.VK_P);
		mChkAllowOpenCashBox.setIcon(PosResUtil.getImageIconFromResource("checkbox_normal.png"));
		mChkAllowOpenCashBox.setSelectedIcon(PosResUtil.getImageIconFromResource("checkbox_selected.png"));
		mChkAllowOpenCashBox.setSelected(false);
		mChkAllowOpenCashBox.setPreferredSize(new Dimension( 45,40));
		mChkAllowOpenCashBox.setBounds(mChkHasCashBox.getX()
				+mChkHasCashBox.getWidth(), 0, 200, CHK_BOX_HEIGHT);
		mChkAllowOpenCashBox.setOpaque(false);
		mChkAllowOpenCashBox.setFocusable(false);
		mChkAllowOpenCashBox.setEnabled(false);
		mChkAllowOpenCashBox.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				setEnableControls();
			}
		});
		PosFormUtil.setCheckboxDefaultFont(mChkAllowOpenCashBox);
		add(mChkAllowOpenCashBox);
	}
	
	/* (non-Javadoc)
	 * @see com.indocosmo.pos.forms.components.terminal.settings.devices.printers.PrinterServiceSelectionBasePanel#getPrinterConfig()
	 */
	@Override
	public BeanDevReceiptPrinterConfig getPrinterConfig() {

		if(mPrinterConfig==null)
			mPrinterConfig= new BeanDevReceiptPrinterConfig();

		mPrinterConfig.setPrinterType(getCurrentPrinterType());
		mPrinterConfig.setCashBoxAttached(mChkHasCashBox.isSelected());
		mPrinterConfig.setAllowManualCashBoxOpen(mChkAllowOpenCashBox.isSelected());
		mPrinterConfig.setCashBoxOpenCode(mTxtCashBox.getText());

		return super.getPrinterConfig();
	}

	/**
	 * @param value
	 */
	private void getPrinterConfig(Object value) {

		if(value!=null){

			final PrinterType type = (PrinterType)value;
			BeanDevReceiptPrinterConfig recPrinterConfig = null;
			int i=0;
			for(;i<printerConfigList.size();i++){
				if(printerConfigList.get(i).getPrinterType()==type){
					recPrinterConfig=printerConfigList.get(i);
					break;
				}
			}
			if(recPrinterConfig==null){
				recPrinterConfig=new BeanDevReceiptPrinterConfig();
				recPrinterConfig.setPrinterType(type);
				printerConfigList.add(recPrinterConfig);
			}

			mPrinterConfig=recPrinterConfig;
		}else
			mPrinterConfig=null;

	}


	/* (non-Javadoc)
	 * @see com.indocosmo.pos.forms.components.terminal.settings.devices.printers.PrinterServiceSelectionBasePanel#setPrinterConfig(com.indocosmo.pos.data.beans.terminal.device.BeanDevPrinterConfig)
	 */
	@Override
	public void setPrinterConfig() {

		super.setPrinterConfig();
		if(mChkHasCashBox!=null&&mPrinterConfig!=null){

			mTxtType.setText(mPrinterConfig.getPrinterType().getDisplayText());
			mChkAllowOpenCashBox.setSelected(mPrinterConfig.isAllowManualCashBoxOpen());
			mChkHasCashBox.setSelected(mPrinterConfig.isCashBoxAttached());
			mTxtCashBox.setText(mPrinterConfig.getCashBoxOpenCode());
			
			setEnableControls();
			
			if(mPrinterConfig.getPrinterType()==PrinterType.Normal){
				
				mChkAllowOpenCashBox.setEnabled(false);
				mChkAllowPaperCut.setEnabled(false);
				mChkHasCashBox.setEnabled(false);
				mTxtCashBox.setEnabled(false);
			}

		}
	}

	/**
	 * creates the Cash Box filed
	 */
	private void createCashBox() {

		mLabelCashBox = new JLabel("Code :");
		mLabelCashBox.setOpaque(true);
		mLabelCashBox.setBackground(Color.LIGHT_GRAY);
		mLabelCashBox.setBorder(new EmptyBorder(2, 2, 2, 2));
		mLabelCashBox.setPreferredSize(new Dimension( LABEL_WIDTH-18, LABEL_HEIGHT));
		setLabelFont(mLabelCashBox);
		add(mLabelCashBox);

		mTxtCashBox = new PosTouchableTextField(mParent, 306);
		mTxtCashBox.setTitle("Cash Box Open Code ?");
		mTxtCashBox.setEnabled(false);
		add(mTxtCashBox);

	}
	/* (non-Javadoc)
	 * @see com.indocosmo.pos.forms.components.terminal.settings.devices.printers.PrinterServiceSelectionBasePanel#setAllowCashBoxOperations(boolean)
	 */
	public void setAllowCashBoxOperations(boolean allow) {

		mEnableCashBoxOptions=allow;
		mLabelCashBox.setVisible(allow);
		mTxtCashBox.setVisible(allow);
		mChkHasCashBox.setVisible(allow);
		mChkHasCashBox.setSelected(false);
		mChkAllowOpenCashBox.setVisible(allow);
		mChkAllowOpenCashBox.setSelected(false);
	}
	public boolean isCashBoxOperationsAllowed(){
		return mEnableCashBoxOptions;
	}
	/**
	 * @return the currentPrinterType
	 */
	public PrinterType getCurrentPrinterType() {
		return currentPrinterType;
	}
	/**
	 * @param currentPrinterType the currentPrinterType to set
	 */
	public void setCurrentPrinterType(PrinterType currentPrinterType) {
		this.currentPrinterType = currentPrinterType;
	}
}
