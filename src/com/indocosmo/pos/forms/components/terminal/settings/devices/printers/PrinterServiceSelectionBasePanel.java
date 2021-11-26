/**
 * 
 */
package com.indocosmo.pos.forms.components.terminal.settings.devices.printers;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.RootPaneContainer;
import javax.swing.SortOrder;
import javax.swing.border.EmptyBorder;

import com.indocosmo.pos.common.enums.PosPrinterType;
import com.indocosmo.pos.common.utilities.PosFormUtil;
import com.indocosmo.pos.common.utilities.PosResUtil;
import com.indocosmo.pos.data.beans.terminal.device.BeanDevPrinterConfig;
import com.indocosmo.pos.data.beans.terminal.device.BeanPrinterServiceInfo;
import com.indocosmo.pos.forms.components.buttons.PosButton;
import com.indocosmo.pos.forms.components.buttons.listners.IPosButtonListner;
import com.indocosmo.pos.forms.components.checkboxes.PosCheckBox;
import com.indocosmo.pos.forms.components.terminal.settings.devices.PosDevPrinterSettingTab;
import com.indocosmo.pos.forms.components.textfields.PosItemSearchableField;
import com.indocosmo.pos.forms.components.textfields.PosTouchableFieldBase;
import com.indocosmo.pos.forms.components.textfields.PosTouchableNumberField;
import com.indocosmo.pos.forms.components.textfields.PosTouchableTextField;
import com.indocosmo.pos.forms.components.textfields.listners.IPosTouchableFieldListner;
import com.indocosmo.pos.forms.messageboxes.PosMessageBoxForm.MessageBoxButtonTypes;
import com.indocosmo.pos.terminal.devices.printer.PosDevicePrinter;

/**
 * @author anand
 *
 */
@SuppressWarnings("serial")
public abstract class PrinterServiceSelectionBasePanel<T extends BeanDevPrinterConfig> extends JPanel{

	protected static final int PANEL_CONTENT_V_GAP = PosDevPrinterSettingTab.PANEL_CONTENT_V_GAP/2;
	protected static final int PANEL_CONTENT_H_GAP = PosDevPrinterSettingTab.PANEL_CONTENT_H_GAP;
	protected static final int LABEL_WIDTH = 100;
	protected static final int CHECK_BOX_WIDTH=150;
	protected static final int LABEL_HEIGHT = PosTouchableFieldBase.LAYOUT_DEF_HEIGHT-2;

	protected static final int CHK_BOX_HEIGHT = 25;
	protected static final int CHK_BOX_WIDTH = 300;

	protected static final int LAYOUT_HEIGHT = PosTouchableFieldBase.LAYOUT_DEF_HEIGHT*3
			+ CHK_BOX_HEIGHT + PANEL_CONTENT_V_GAP*3+40;

	protected static final int PRINT_TEST_BUTTON_WIDTH = 101;
	protected static final int PRINT_TEST_BUTTON_HEIGHT = 40;

	protected static final String PRINT_TEST_BUTTON_NORMAL = "test_print.png";
	protected static final String PRINT_TEST_BUTTON_TOUCHED = "test_print_touch.png";
	protected static final String PRINT_DELETE_BUTTON_NORMAL="delete_print.png";
	protected static final String PRINT_DELETE_BUTTON_TOUCHED="delete_print_touch.png";

	protected JLabel mLabelPrinter;
	protected PosItemSearchableField mTxtPrinter;
	protected JLabel mLabelCopies;
	protected PosTouchableNumberField mTxtNoCopies;
	protected JLabel mLabelPaperCut;
	protected PosTouchableTextField mTxtPaperCut;
	protected JLabel mLabelCashBox;
	protected PosTouchableTextField mTxtCashBox;

	protected RootPaneContainer mParent;
	protected PosCheckBox mChkHasPrinter;
	protected PosCheckBox mChkAllowPaperCut;
	protected PosCheckBox mchkIsActive;
	protected PosButton mBtnTestPrinter;
	protected PosCheckBox mChkUseAlternativeLangaugeToPrint;
	protected int mWidth;
	protected int mHeight;
	protected boolean mAllowMultipleCopies=true;

	protected BeanPrinterServiceInfo mPrinterInfo;
	protected PosPrinterType mPrinterType;
	protected T mPrinterConfig;
	protected ArrayList<T> printerConfigList;
	
	protected JPanel mTitleOptionPanel;
	

	/**
	 * @param parent
	 * @param type
	 * @param width
	 * @throws Exception 
	 */
	public PrinterServiceSelectionBasePanel(RootPaneContainer parent,
			PosPrinterType type, int width) throws Exception {
		mWidth = width;
		mParent = parent;
		mPrinterType = type;
		mHeight=LAYOUT_HEIGHT;
		initControls();

	}
	
	
	
	/**
	 * @param parent
	 * @param type
	 * @param width
	 * @param height
	 * @throws Exception 
	 */
	public PrinterServiceSelectionBasePanel(RootPaneContainer parent,
			PosPrinterType type, int width,int height) throws Exception {
		mWidth = width;
		mParent = parent;
		mPrinterType = type;
		mHeight=height;
		
		initControls();

	}


	/**
	 * @throws Exception 
	 * 
	 */
	protected  void initControls() throws Exception{
	
		setLayout(new FlowLayout(FlowLayout.LEFT,1,1));
		setSize(mWidth, mHeight);

		createTitleOptionPanel();
		createHasPrinterCheckBox();
		


	}



	protected void createTitleOptionPanel(){
		mTitleOptionPanel=new JPanel();
		mTitleOptionPanel.setPreferredSize(new Dimension( getWidth(), CHK_BOX_HEIGHT));
		mTitleOptionPanel.setBounds(0, 0, getWidth(), CHK_BOX_HEIGHT);
		mTitleOptionPanel.setBackground(PosFormUtil.SECTION_HEADING_PANEL_BG_COLOR);
		mTitleOptionPanel.setOpaque(true);
		mTitleOptionPanel.setLayout(null);
		add(mTitleOptionPanel);
	}

	/**
	 * Creates the has printer check box
	 */
	protected void createHasPrinterCheckBox() {

		mChkHasPrinter = new PosCheckBox("Has "
				+ ((mPrinterType == PosPrinterType.RECEIPT) ? "Receipt"
						: "Kitchen") + " Printer");
//		mChkHasPrinter.setMnemonic(KeyEvent.VK_C);
		mChkHasPrinter.setMnemonic((mPrinterType == PosPrinterType.RECEIPT) ? 'R':'K');
//		mChkHasPrinter.setSelected(false);
		mChkHasPrinter.setBounds(0, 0,250, CHK_BOX_HEIGHT);
		mChkHasPrinter.setFocusable(false);
		mChkHasPrinter.setOpaque(false);
		mChkHasPrinter.setForeground(PosFormUtil.SECTION_HEADING_PANEL_FG_COLOR);
		mChkHasPrinter.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {

//				if(!mChkHasPrinter.isSelected()){
//					mChkAllowPaperCut.setSelected(false);
//					mChkHasCashBox.setSelected(false);
//					mChkAllowOpenCashBox.setSelected(false);
//				}

				setEnableControls();
			}
		});
		PosFormUtil.setCheckboxDefaultFont(mChkHasPrinter);
		mTitleOptionPanel.add(mChkHasPrinter);
	}

	protected void setLabelFont(JLabel label) {
//		Font newLabelFont = new Font(label.getFont().getName(), label.getFont()
//				.getStyle(), 16);
//		label.setFont(newLabelFont);
		label.setFont( PosFormUtil.getLabelFont());
	}
	/**
	 * 
	 */
	protected void createIsActiveCheckBox() {
		
		final JLabel mCheckBoxLabel = new JLabel("Active :");
		mCheckBoxLabel.setBackground(Color.LIGHT_GRAY);
		mCheckBoxLabel.setOpaque(true);
		mCheckBoxLabel.setBorder(new EmptyBorder(2,2,2,2));
		mCheckBoxLabel.setPreferredSize(new Dimension(LABEL_WIDTH,LABEL_HEIGHT));
		setLabelFont(mCheckBoxLabel);
		add(mCheckBoxLabel);

		mchkIsActive = new PosCheckBox();
		mchkIsActive.setMnemonic(KeyEvent.VK_C);
		mchkIsActive.setIcon(PosResUtil.getImageIconFromResource("checkbox_normal.png"));
		mchkIsActive.setSelectedIcon(PosResUtil.getImageIconFromResource("checkbox_selected.png"));
		mchkIsActive.setPreferredSize(new Dimension(45,40));
		mchkIsActive.setFocusable(false);
		mchkIsActive.setEnabled(false);
		mchkIsActive.setSelected(true);
		mchkIsActive.setOpaque(true);
		mchkIsActive.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				setEnableControls();
			}
		});
		PosFormUtil.setCheckboxDefaultFont(mchkIsActive);
		add(mchkIsActive);
	}
	/**
	 * @return the kitchenPrinterConfig
	 */
	public ArrayList<T> getPrinterConfigList() {
		return printerConfigList;
	}

	/**
	 * @param kitchenPrinterConfig the kitchenPrinterConfig to set
	 */
	public void setPrinterConfigList(
			ArrayList<T> kitchenPrinterConfig) {
		this.printerConfigList = kitchenPrinterConfig;
	}

	
	/**
	 * Creates the has paper cut check box
	 */
	protected void createAllowPaperCutCheckBox() {

		final JLabel mCheckBoxLabel = new JLabel("Cut :");
		mCheckBoxLabel.setBackground(Color.LIGHT_GRAY);
		mCheckBoxLabel.setOpaque(true);
		mCheckBoxLabel.setBorder(new EmptyBorder(2,2,2,2));
		mCheckBoxLabel.setPreferredSize(new Dimension(LABEL_WIDTH,LABEL_HEIGHT));
		setLabelFont(mCheckBoxLabel);
		add(mCheckBoxLabel);
		
		mChkAllowPaperCut= new PosCheckBox();
		mChkAllowPaperCut.setMnemonic(KeyEvent.VK_P);
		mChkAllowPaperCut.setIcon(PosResUtil.getImageIconFromResource("checkbox_normal.png"));
		mChkAllowPaperCut.setSelectedIcon(PosResUtil.getImageIconFromResource("checkbox_selected.png"));
		mChkAllowPaperCut.setSelected(false);
		mChkAllowPaperCut.setFocusable(false);
		mChkAllowPaperCut.setBounds(mChkHasPrinter.getX()
				+mChkHasPrinter.getWidth(), 0, 160, CHK_BOX_HEIGHT);
		mChkAllowPaperCut.setPreferredSize(new Dimension(45, LABEL_HEIGHT));
		mChkAllowPaperCut.setOpaque(true);
		mChkAllowPaperCut.setEnabled(false);
		mChkAllowPaperCut.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				setEnableControls();
			}
		});
		PosFormUtil.setCheckboxDefaultFont(mChkAllowPaperCut);
		add(mChkAllowPaperCut);
	}

	/**
	 * Creates the has CashBox attached check box
	 */

	


	/**
	 * Create the printer info field
	 */
	protected void createPrinterInfoField() {

		mLabelPrinter = new JLabel(
				"<html>Printer<font color=#FF0000>*</font> :</html>");
		mLabelPrinter.setOpaque(true);
		mLabelPrinter.setBackground(Color.LIGHT_GRAY);
		mLabelPrinter.setBorder(new EmptyBorder(2, 2, 2, 2));
		mLabelPrinter.setPreferredSize(new Dimension( LABEL_WIDTH, LABEL_HEIGHT));

		setLabelFont(mLabelPrinter);
		add(mLabelPrinter);

		mTxtPrinter = new PosItemSearchableField(mParent, getWidth()
				- mLabelPrinter.getWidth()-109-PRINT_TEST_BUTTON_WIDTH-1,
				PosDevicePrinter.getAllPrinters());
		mTxtPrinter.setTitle("Select Receipt Printer.");
		mTxtPrinter.setEditable(false);
		mTxtPrinter.setEnabled(false);
		mTxtPrinter.setSorting(0, SortOrder.ASCENDING);
		mTxtPrinter.setListner(new IPosTouchableFieldListner() {

			@Override
			public void onValueSelected(Object value) {
				mPrinterInfo = (BeanPrinterServiceInfo) value;

			}

			@Override
			public void onReset() {
				mPrinterInfo = null;
			}
		});
		add(mTxtPrinter);
	}

	/**
	 * Enable/disable controls
	 */
	protected void setEnableControls(){

		mTxtPrinter.setEnabled(mChkHasPrinter.isSelected());
		mBtnTestPrinter.setEnabled(mChkHasPrinter.isSelected());
		mTxtNoCopies.setEnabled(mChkHasPrinter.isSelected()&&mAllowMultipleCopies);

		mChkAllowPaperCut.setEnabled(mChkHasPrinter.isSelected());
		mTxtPaperCut.setEnabled(mChkHasPrinter.isSelected()&&mChkAllowPaperCut.isSelected());
	
		mChkUseAlternativeLangaugeToPrint.setEnabled(mChkHasPrinter.isSelected());
//		mchkIsActive.setEnabled(mChkHasPrinter.isSelected());
	}

	
	
	/**
	 * Creates the printer test button
	 */
	protected void createPrinterTestButton() {

		mBtnTestPrinter = new PosButton("Test");
		mBtnTestPrinter.setImage(PRINT_TEST_BUTTON_NORMAL);
		mBtnTestPrinter.setTouchedImage(PRINT_TEST_BUTTON_TOUCHED);
		mBtnTestPrinter.setSize(PRINT_TEST_BUTTON_WIDTH,
				PRINT_TEST_BUTTON_HEIGHT);
		mBtnTestPrinter.setEnabled(false);
		mBtnTestPrinter.setOnClickListner(new IPosButtonListner() {

			@Override
			public void onClicked(PosButton button) {
				try {
					if (mPrinterInfo == null) {
						PosFormUtil
						.showErrorMessageBox(mParent,
								"No printer selected!!!. Please select a printer.");
						return;
					}
					PosDevicePrinter.testDevice(getPrinterConfig(),mPrinterType);
					PosFormUtil
					.showQuestionMessageBox(
							mParent,
							MessageBoxButtonTypes.YesNo,
							"A document has been sent to the selected printer. Is it printed?",
							null);
				} catch (Exception e) {
					e.printStackTrace();
					// PosFormUtil.showErrorMessageBox(mParent,
					// "Failed to print the test document. Please check your printer!!!");
				}
			}
		});
		add(mBtnTestPrinter);
	}
	
	/**
	 * creates the No. Copies filed
	 */
	protected void createNoCopies() {

		mLabelCopies = new JLabel("Copies :");
		mLabelCopies.setOpaque(true);
		mLabelCopies.setBackground(Color.LIGHT_GRAY);
		mLabelCopies.setBorder(new EmptyBorder(2, 2, 2, 2));
		mLabelCopies.setPreferredSize(new Dimension( LABEL_WIDTH, LABEL_HEIGHT));
		setLabelFont(mLabelCopies);
		add(mLabelCopies);

		mTxtNoCopies = new PosTouchableNumberField(mParent, 155);
		mTxtNoCopies.setTitle("No Copies ?");
		mTxtNoCopies.setMinValue(1);
		mTxtNoCopies.setEnabled(false);

		add(mTxtNoCopies);
		setAllowMultipleCopies(false);
	}

	/**
	 * creates the paper cut
	 */
	protected void createPaperCut() {

		mLabelPaperCut = new JLabel("Code :");
		mLabelPaperCut.setOpaque(true);
		mLabelPaperCut.setBackground(Color.LIGHT_GRAY);
		mLabelPaperCut.setBorder(new EmptyBorder(2, 2, 2, 2));
		mLabelPaperCut.setPreferredSize(new Dimension( LABEL_WIDTH-18, LABEL_HEIGHT));
		setLabelFont(mLabelPaperCut);
		add(mLabelPaperCut);

		mTxtPaperCut = new PosTouchableTextField(mParent, 306);
		mTxtPaperCut.setTitle("Paper Cut Code?");
		mTxtPaperCut.setEnabled(false);
		add(mTxtPaperCut);
	}

	/**
	 * creates the alternative langauge to print checkbox
	 */
	protected void createPrintInAlternativeLangaugeCheckBox() {
		
		final JLabel mCheckBoxLabel = new JLabel("Use Alternate Languge :");
		mCheckBoxLabel.setBackground(Color.LIGHT_GRAY);
		mCheckBoxLabel.setOpaque(true);
		mCheckBoxLabel.setBorder(new EmptyBorder(2,2,2,2));
		mCheckBoxLabel.setPreferredSize(new Dimension(229,LABEL_HEIGHT));
		setLabelFont(mCheckBoxLabel);
		add(mCheckBoxLabel);
		
		mChkUseAlternativeLangaugeToPrint= new PosCheckBox();
		mChkUseAlternativeLangaugeToPrint.setMnemonic(KeyEvent.VK_P);
		mChkUseAlternativeLangaugeToPrint.setIcon(PosResUtil.getImageIconFromResource("checkbox_normal.png"));
		mChkUseAlternativeLangaugeToPrint.setSelectedIcon(PosResUtil.getImageIconFromResource("checkbox_selected.png"));
		mChkUseAlternativeLangaugeToPrint.setPreferredSize(new Dimension(45,40));
		mChkUseAlternativeLangaugeToPrint.setFocusable(false);
		mChkUseAlternativeLangaugeToPrint.setOpaque(false);
		mChkUseAlternativeLangaugeToPrint.setEnabled(false);
		mChkUseAlternativeLangaugeToPrint.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				setEnableControls();
			}
		});
		PosFormUtil.setCheckboxDefaultFont(mChkUseAlternativeLangaugeToPrint);
		add(mChkUseAlternativeLangaugeToPrint);
	}

	/**
	 * @return the mPrinterInfo
	 */
	public T getPrinterConfig() {

//		if (mPrinterConfig == null)
//			mPrinterConfig = new BeanDevPrinterConfig();
//		mPrinterConfig.setPrinterType(mPrinterType);
		mPrinterConfig.setPrinterInfo(mPrinterInfo);
		mPrinterConfig.setNoCopies(Integer.parseInt(mTxtNoCopies.getText()));
		mPrinterConfig.setPaperCutOn(mChkAllowPaperCut.isSelected());
		mPrinterConfig.setPaperCutCode(mTxtPaperCut.getText());
		mPrinterConfig.setUseAltLangugeToPrint(mChkUseAlternativeLangaugeToPrint.isSelected());
//		mPrinterConfig.setActive(mchkIsActive.isSelected());
//		mPrinterConfig.setActive(mchkIsActive.isSelected());
		
		
//		if(mChkHasCashBox!=null){
//		mPrinterConfig.setCashBoxAttached(mChkHasCashBox.isSelected());
//		mPrinterConfig.setAllowManualCashBoxOpen(mChkAllowOpenCashBox.isSelected());
//		mPrinterConfig.setCashBoxOpenCode(mTxtCashBox.getText());
//		}
		return mPrinterConfig;
	}

	/**
	 * @param mPrinterInfo
	 *            the mPrinterInfo to set
	 */
	public void setPrinterConfig() {

//		this.mPrinterConfig = printerConfig;
		if (mPrinterConfig != null) {
			
			mPrinterInfo =mPrinterConfig.getPrinterInfo();
			mTxtPrinter.setSelectedItem(mPrinterInfo);
			mTxtNoCopies.setText(String.valueOf(mPrinterConfig.getNoCopies()));
			mTxtPaperCut.setText(mPrinterConfig.getPaperCutCode());
			mChkAllowPaperCut.setSelected(mPrinterConfig.isPaperCutOn());
//			mchkIsActive.setSelected(mPrinterConfig.isActive());
			mChkUseAlternativeLangaugeToPrint.setSelected(mPrinterConfig.isUseAltLangugeToPrint());

			
		}

	}

	/**
	 * Printer attached property
	 * 
	 * @return
	 */
	public boolean hasPrinter() {
		return mChkHasPrinter.isSelected();
	}

	/**
	 * Printer attached property
	 * 
	 * @return
	 */
	public void setHasPrinter(boolean attached) {
		mChkHasPrinter.setSelected(attached);
	}
	/**
	 * validate printer
	 * @return
	 */
	public boolean validatePrinter() {
		boolean result=true;
		if(mChkHasPrinter.isSelected()){
//			if(mPrinterInfo == null){
//				PosFormUtil
//				.showErrorMessageBox(mParent,
//						"Kitchen printer is not selected!!! Please select a printer.");
//				result=false;
//			}else 
			if(!mTxtNoCopies.doValidate()){
				result=false;
			}else if(!validatePaperCutOptions()){
				result=false;
			}
		}
		return result;
	}

	/**
	 * Validate the paper cut options
	 * @return
	 */
	protected boolean validatePaperCutOptions(){
		boolean result=true;
		if(mChkAllowPaperCut.isSelected()){
			if(mTxtPaperCut.getText().equals("")){
				PosFormUtil
				.showErrorMessageBox(mParent,
						"Paper cut option selected!!! Please enter the paper cut code.");
				result=false;
			}
		}
		return result;
	}

	/**
	 * @return the mAllowMultipleCopies
	 */
	public boolean isAllowMultipleCopies() {
		return mAllowMultipleCopies;
	}

	/**
	 * @param mAllowMultipleCopies the mAllowMultipleCopies to set
	 */
	public void setAllowMultipleCopies(boolean allowMultipleCopies) {
		this.mAllowMultipleCopies = allowMultipleCopies;
		mTxtNoCopies.setEnabled(mChkHasPrinter.isSelected()&&mAllowMultipleCopies);
	}
	public void setprinterDetails() {
		
	}
}
