package com.indocosmo.pos.forms.components.terminal.settings.devices;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

import com.indocosmo.pos.common.utilities.PosFormUtil;
import com.indocosmo.pos.data.beans.terminal.device.BeanDevPrinterConfig;
import com.indocosmo.pos.data.providers.terminaldb.PosDevPrinterConfigProvider;
import com.indocosmo.pos.data.providers.terminaldb.PosDevSettingProvider;
import com.indocosmo.pos.data.providers.terminaldb.PosDevSettingProvider.PosDevices;
import com.indocosmo.pos.forms.components.checkboxes.PosCheckBox;
import com.indocosmo.pos.forms.components.textfields.PosTouchableFieldBase;
import com.indocosmo.pos.forms.components.textfields.PosTouchableTextField;

/**
 * @author Ramesh S
 * @since 08th Aug 2012
 **/

@SuppressWarnings("serial")
public final class PosPrinterPanelOld extends PosDeviceSettingsTab{


	/* (non-Javadoc)
	 * @see com.indocosmo.pos.forms.components.terminal.settings.PosTermnalSettingsBase#onSave()
	 */
	@Override
	public boolean onSave() {
		// TODO Auto-generated method stub
		return false;
	}

	private static final int LABEL_L_WIDTH=150;
	private static final int LABEL_R_WIDTH=160;
	private static final int LABEL_HEIGHT=PosTouchableFieldBase.LAYOUT_DEF_HEIGHT;

	private static final int PANEL_CONTENT_RH_GAP = PANEL_CONTENT_H_GAP + 10;
	
	private static final int TEXT_FIELD_SMALL_WIDTH=210;
	private static final int TEXT_FIELD_WIDE_WIDTH=TEXT_FIELD_SMALL_WIDTH*2+LABEL_R_WIDTH+PANEL_CONTENT_H_GAP*2;
	
	private PosTouchableTextField mTxtPort;
	private PosTouchableTextField mTxtInit;
	private PosTouchableTextField mTxtOpenDrawer;
	private PosTouchableTextField mTxtLineFeed;
	private PosTouchableTextField mTxtSmallFont;
	private PosTouchableTextField mTxtBigFont;
	private PosTouchableTextField mTxtUnderLine;
	private PosTouchableTextField mTxtUnderlineReset;
	private PosTouchableTextField mTxtLineSpacing;
	private PosTouchableTextField mTxtLineSpacingReset;
	private PosTouchableTextField mTxtPrinterReset;
	private PosTouchableTextField mTxtCutFull;
	private PosTouchableTextField mTxtCutPartial;
	private PosTouchableTextField mTxtJustifyLeft;
	private PosTouchableTextField mTxtJustifyRight;
	private PosTouchableTextField mTxtJustifyCenter;
	private PosTouchableTextField mTxtMarginLeft;
	private PosTouchableTextField mTxtPrintWidth;

	private PosDevPrinterConfigProvider mPrinterConfigProvider;
	private PosDevSettingProvider mDeviceSettingProvider;
	private BeanDevPrinterConfig mPrinterConfig;
	private PosCheckBox mOpenDrawer;
	private PosCheckBox mOpenDrawerOnly;
	
	public PosPrinterPanelOld(JDialog parent){
		super(parent,"Printer",PosDevices.RECEIPTPRINTER,true,LAYOUT_WIDTH,LAYOUT_HEIGHT);
	}

	@Override
	protected void initControls() {
		setPort();
		setLineFeed();
		setOpenDrawer();
		setPartialCut();
		setJustifyLeft();
		setJustifyCenter();
		setSmallFont();
		setUnderline();
		setLineSpacing();
		setOpenDrawerSelectionControl();
		setOpenDrawerOnly();
		setInitialize();
		setPrinterReset();
		setPrintWidth();
		setFullCut();
		setJustifyRight();
		setLeftMargin();
		setBigFont();
		setUnderlineReset();
		setLineSpacingReset();
//		setPrinterDetails();
	}	

	private void setPort(){
		int left=PANEL_CONTENT_H_GAP;
		final int top=mChkHasDevice.getY()+mChkHasDevice.getHeight()+PANEL_CONTENT_V_GAP;
		final JLabel label=new JLabel("<html>Port<font color=#FF0000>*</font> :</html>");
		label.setBounds(left, top, LABEL_L_WIDTH, LABEL_HEIGHT);
		setLabelFont(label);
		add(label);
		
		left=label.getX()+LABEL_L_WIDTH+PANEL_CONTENT_H_GAP;
		mTxtPort=new PosTouchableTextField(mParent,TEXT_FIELD_SMALL_WIDTH);
		mTxtPort.setTitle("Enter Port (eg:-'/dev/lp0' or 'LPT1').");
		mTxtPort.setLocation(left, top);
		add(mTxtPort);
	}

	private void setLineFeed(){
		int left=PANEL_CONTENT_H_GAP;
		final int top=mTxtPort.getY()+mTxtPort.getHeight()+PANEL_CONTENT_V_GAP;
		final JLabel label=new JLabel("<html>Line Feed<font color=#FF0000>*</font> :</html>");
		label.setBounds(left, top, LABEL_L_WIDTH, LABEL_HEIGHT);
		setLabelFont(label);
		add(label);
		
		left=label.getX()+LABEL_L_WIDTH+PANEL_CONTENT_H_GAP;
		mTxtLineFeed=new PosTouchableTextField(mParent,TEXT_FIELD_SMALL_WIDTH);
		mTxtLineFeed.setTitle("Hexadecimal code for Line Feed.");
		mTxtLineFeed.setLocation(left, top);
		add(mTxtLineFeed);
	}

	private void setOpenDrawer(){
		int left=PANEL_CONTENT_H_GAP;
		final int top=mTxtLineFeed.getY()+mTxtLineFeed.getHeight()+PANEL_CONTENT_V_GAP;
		final JLabel label=new JLabel("Open Drawer :");
		label.setBounds(left, top, LABEL_L_WIDTH, LABEL_HEIGHT);
		setLabelFont(label);
		add(label);
		
		left=label.getX()+LABEL_L_WIDTH+PANEL_CONTENT_H_GAP;
		mTxtOpenDrawer=new PosTouchableTextField(mParent,TEXT_FIELD_SMALL_WIDTH);
		mTxtOpenDrawer.setTitle("Hexadecimal code to Open Cash Drawer.");
		mTxtOpenDrawer.setLocation(left, top);
		add(mTxtOpenDrawer);
	}

	private void setPartialCut(){
		int left=PANEL_CONTENT_H_GAP;
		final int top=mTxtOpenDrawer.getY()+mTxtOpenDrawer.getHeight()+PANEL_CONTENT_V_GAP;
		final JLabel label=new JLabel("<html>Partial Cut<font color=#FF0000>*</font> :</html>");
		label.setBounds(left, top, LABEL_L_WIDTH, LABEL_HEIGHT);
		setLabelFont(label);
		add(label);
		
		left=label.getX()+LABEL_L_WIDTH+PANEL_CONTENT_H_GAP;
		mTxtCutPartial=new PosTouchableTextField(mParent,TEXT_FIELD_SMALL_WIDTH);
		mTxtCutPartial.setTitle("Hexadecimal code for Paper Partial Cut.");
		mTxtCutPartial.setLocation(left, top);
		add(mTxtCutPartial);
	}

	private void setJustifyLeft(){
		int left=PANEL_CONTENT_H_GAP;
		final int top=mTxtCutPartial.getY()+mTxtCutPartial.getHeight()+PANEL_CONTENT_V_GAP;
		final JLabel label=new JLabel("Justify Left :");
		label.setBounds(left, top, LABEL_L_WIDTH, LABEL_HEIGHT);
		setLabelFont(label);
		add(label);
		
		left=label.getX()+LABEL_L_WIDTH+PANEL_CONTENT_H_GAP;
		mTxtJustifyLeft=new PosTouchableTextField(mParent,TEXT_FIELD_SMALL_WIDTH);
		mTxtJustifyLeft.setTitle("Hexadecimal code for Left Justify.");
		mTxtJustifyLeft.setLocation(left, top);
		add(mTxtJustifyLeft);
	}
	
	private void setJustifyCenter(){
		int left=PANEL_CONTENT_H_GAP;
		final int top=mTxtJustifyLeft.getY()+mTxtJustifyLeft.getHeight()+PANEL_CONTENT_V_GAP;
		final JLabel label=new JLabel("Justify Center :");
		label.setBounds(left, top, LABEL_L_WIDTH, LABEL_HEIGHT);
		setLabelFont(label);
		add(label);
		
		left=label.getX()+LABEL_L_WIDTH+PANEL_CONTENT_H_GAP;
		mTxtJustifyCenter=new PosTouchableTextField(mParent,TEXT_FIELD_SMALL_WIDTH);
		mTxtJustifyCenter.setTitle("Hexadecimal code for Center Justify.");
		mTxtJustifyCenter.setLocation(left, top);
		add(mTxtJustifyCenter);
	}

	private void setSmallFont(){
		int left=PANEL_CONTENT_H_GAP;
		final int top=mTxtJustifyCenter.getY()+mTxtJustifyCenter.getHeight()+PANEL_CONTENT_V_GAP;
		final JLabel label=new JLabel("Small Font :");
		label.setBounds(left, top, LABEL_L_WIDTH, LABEL_HEIGHT);
		setLabelFont(label);
		add(label);
		
		left=label.getX()+LABEL_L_WIDTH+PANEL_CONTENT_H_GAP;
		mTxtSmallFont=new PosTouchableTextField(mParent,TEXT_FIELD_SMALL_WIDTH);
		mTxtSmallFont.setTitle("Hexadecimal code for Small Font.");
		mTxtSmallFont.setLocation(left, top);
		add(mTxtSmallFont);
	}

	private void setUnderline(){
		int left=PANEL_CONTENT_H_GAP;
		final int top=mTxtSmallFont.getY()+mTxtSmallFont.getHeight()+PANEL_CONTENT_V_GAP;
		final JLabel label=new JLabel("Underline :");
		label.setBounds(left, top, LABEL_L_WIDTH, LABEL_HEIGHT);
		setLabelFont(label);
		add(label);
		
		left=label.getX()+LABEL_L_WIDTH+PANEL_CONTENT_H_GAP;
		mTxtUnderLine=new PosTouchableTextField(mParent,TEXT_FIELD_SMALL_WIDTH);
		mTxtUnderLine.setTitle("Hexadecimal code for Underline.");
		mTxtUnderLine.setLocation(left, top);
		add(mTxtUnderLine);
	}

	private void setLineSpacing(){
		int left=PANEL_CONTENT_H_GAP;
		final int top=mTxtUnderLine.getY()+mTxtUnderLine.getHeight()+PANEL_CONTENT_V_GAP;
		final JLabel label=new JLabel("Line Spacing :");
		label.setBounds(left, top, LABEL_L_WIDTH, LABEL_HEIGHT);
		setLabelFont(label);
		add(label);
		
		left=label.getX()+LABEL_L_WIDTH+PANEL_CONTENT_H_GAP;
		mTxtLineSpacing=new PosTouchableTextField(mParent,TEXT_FIELD_SMALL_WIDTH);
		mTxtLineSpacing.setTitle("Hexadecimal code for Line Spacing.");
		mTxtLineSpacing.setLocation(left, top);
		
		add(mTxtLineSpacing);
	}
	
	private void setOpenDrawerSelectionControl(){
		int left = PANEL_CONTENT_H_GAP;
		final int top = mTxtLineSpacing.getY() + mTxtLineSpacing.getHeight()
				+ PANEL_CONTENT_V_GAP;

		mOpenDrawer = new PosCheckBox();
		mOpenDrawer.setTag("Open File when created");
		mOpenDrawer.setHorizontalAlignment(JTextField.LEFT);
		mOpenDrawer.setBounds(left, top, 300, 20);
		mOpenDrawer.setFont(PosFormUtil.getTextFieldBoldFont());
		mOpenDrawer.setText("Open Cash Drawer");
		PosFormUtil.setCheckboxDefaultFont(mOpenDrawer);
		add(mOpenDrawer);
	}
	
	/**
	 * 
	 */
	private void setOpenDrawerOnly() {
		
		final int top=mChkHasDevice.getY();
		int left=mTxtPort.getX()+mTxtPort.getWidth()+PANEL_CONTENT_RH_GAP;
		
		mOpenDrawerOnly = new PosCheckBox();
		mOpenDrawerOnly.setTag("Allow Open Cash Drawer Only");
		mOpenDrawerOnly.setHorizontalAlignment(JTextField.LEFT);
		mOpenDrawerOnly.setBounds(left, top, 350, 20);
		mOpenDrawerOnly.setFont(PosFormUtil.getTextFieldBoldFont());
		mOpenDrawerOnly.setText("Open Cash Drawer without sale");
		PosFormUtil.setCheckboxDefaultFont(mOpenDrawerOnly);
		add(mOpenDrawerOnly);
	}

	

	private void setInitialize(){
		final int top=mTxtPort.getY();
		int left=mTxtPort.getX()+mTxtPort.getWidth()+PANEL_CONTENT_RH_GAP;
		final JLabel label=new JLabel("<html>Initialize<font color=#FF0000>*</font> :</html>");
		label.setBounds(left, top, LABEL_L_WIDTH, LABEL_HEIGHT);
		setLabelFont(label);
		add(label);
		
		left=label.getX()+LABEL_R_WIDTH+PANEL_CONTENT_H_GAP+PANEL_CONTENT_RH_GAP;
		mTxtInit=new PosTouchableTextField(mParent,TEXT_FIELD_SMALL_WIDTH);
		mTxtInit.setTitle("Hexadecimal code to Initialize Printer.");
		mTxtInit.setLocation(left, top);
		add(mTxtInit);
	}

	private void setPrinterReset(){
		final int top=mTxtInit.getY()+mTxtInit.getHeight()+PANEL_CONTENT_V_GAP;
		int left=mTxtPort.getX()+mTxtPort.getWidth()+PANEL_CONTENT_RH_GAP;
		final JLabel label=new JLabel("<html>Printer Reset<font color=#FF0000>*</font> :</html>");
		label.setBounds(left, top, LABEL_R_WIDTH, LABEL_HEIGHT);
		setLabelFont(label);
		add(label);
		
		left=label.getX()+LABEL_R_WIDTH+PANEL_CONTENT_H_GAP+PANEL_CONTENT_RH_GAP;
		mTxtPrinterReset=new PosTouchableTextField(mParent,TEXT_FIELD_SMALL_WIDTH);
		mTxtPrinterReset.setTitle("Hexadecimal code for Resetting Printer.");
		mTxtPrinterReset.setLocation(left, top);
		add(mTxtPrinterReset);
	}
	
	private void setPrintWidth(){
		final int top=mTxtPrinterReset.getY()+mTxtPrinterReset.getHeight()+PANEL_CONTENT_V_GAP;
		int left=mTxtPort.getX()+mTxtPort.getWidth()+PANEL_CONTENT_RH_GAP;
		final JLabel label=new JLabel("<html>Print width<font color=#FF0000>*</font> :</html>");
		label.setBounds(left, top, LABEL_R_WIDTH, LABEL_HEIGHT);
		setLabelFont(label);
		add(label);
		
		left=label.getX()+LABEL_R_WIDTH+PANEL_CONTENT_H_GAP+PANEL_CONTENT_RH_GAP;
		mTxtPrintWidth=new PosTouchableTextField(mParent,TEXT_FIELD_SMALL_WIDTH);
		mTxtPrintWidth.setTitle("Hexadecimal code for Print Width.");
		mTxtPrintWidth.setLocation(left, top);
		add(mTxtPrintWidth);
	}

	private void setFullCut(){
		final int top=mTxtPrintWidth.getY()+mTxtPrintWidth.getHeight()+PANEL_CONTENT_V_GAP;
		int left=mTxtPort.getX()+mTxtPort.getWidth()+PANEL_CONTENT_RH_GAP;
		final JLabel label=new JLabel("Full Cut :");
		label.setBounds(left, top, LABEL_R_WIDTH, LABEL_HEIGHT);
		setLabelFont(label);
		add(label);
		
		left=label.getX()+LABEL_R_WIDTH+PANEL_CONTENT_H_GAP+PANEL_CONTENT_RH_GAP;
		mTxtCutFull=new PosTouchableTextField(mParent,TEXT_FIELD_SMALL_WIDTH);
		mTxtCutFull.setTitle("Hexadecimal code for Paper Full Cut.");
		mTxtCutFull.setLocation(left, top);
		add(mTxtCutFull);
	}

	private void setJustifyRight(){
		final int top=mTxtCutFull.getY()+mTxtCutFull.getHeight()+PANEL_CONTENT_V_GAP;
		int left=mTxtPort.getX()+mTxtPort.getWidth()+PANEL_CONTENT_RH_GAP;
		final JLabel label=new JLabel("Justify Right :");
		label.setBounds(left, top, LABEL_R_WIDTH, LABEL_HEIGHT);
		setLabelFont(label);
		add(label);
		
		left=label.getX()+LABEL_R_WIDTH+PANEL_CONTENT_H_GAP+PANEL_CONTENT_RH_GAP;
		mTxtJustifyRight=new PosTouchableTextField(mParent,TEXT_FIELD_SMALL_WIDTH);
		mTxtJustifyRight.setTitle("Hexadecimal code for Right Justify.");
		mTxtJustifyRight.setLocation(left, top);
		add(mTxtJustifyRight);
	}

	private void setLeftMargin(){
		final int top=mTxtJustifyRight.getY()+mTxtJustifyRight.getHeight()+PANEL_CONTENT_V_GAP;
		int left=mTxtPort.getX()+mTxtPort.getWidth()+PANEL_CONTENT_RH_GAP;
		final JLabel label=new JLabel("Left Margin :");
		label.setBounds(left, top, LABEL_R_WIDTH, LABEL_HEIGHT);
		setLabelFont(label);
		add(label);
		
		left=label.getX()+LABEL_R_WIDTH+PANEL_CONTENT_H_GAP+PANEL_CONTENT_RH_GAP;
		mTxtMarginLeft=new PosTouchableTextField(mParent,TEXT_FIELD_SMALL_WIDTH);
		mTxtMarginLeft.setTitle("Hexadecimal code for Left Margin.");
		mTxtMarginLeft.setLocation(left, top);
		add(mTxtMarginLeft);
	}

	private void setBigFont(){
		final int top=mTxtMarginLeft.getY()+mTxtMarginLeft.getHeight()+PANEL_CONTENT_V_GAP;
		int left=mTxtPort.getX()+mTxtPort.getWidth()+PANEL_CONTENT_RH_GAP;
		final JLabel label=new JLabel("Big Font :");
		label.setBounds(left, top, LABEL_R_WIDTH, LABEL_HEIGHT);
		setLabelFont(label);
		add(label);
		
		left=label.getX()+LABEL_R_WIDTH+PANEL_CONTENT_H_GAP+PANEL_CONTENT_RH_GAP;
		mTxtBigFont=new PosTouchableTextField(mParent,TEXT_FIELD_SMALL_WIDTH);
		mTxtBigFont.setTitle("Hexadecimal code for Big Font.");
		mTxtBigFont.setLocation(left, top);
		add(mTxtBigFont);
	}

	private void setUnderlineReset(){
		final int top=mTxtBigFont.getY()+mTxtBigFont.getHeight()+PANEL_CONTENT_V_GAP;
		int left=mTxtPort.getX()+mTxtPort.getWidth()+PANEL_CONTENT_RH_GAP;
		final JLabel label=new JLabel("Reset Underline :");
		label.setBounds(left, top, LABEL_R_WIDTH, LABEL_HEIGHT);
		setLabelFont(label);
		add(label);
		
		left=label.getX()+LABEL_R_WIDTH+PANEL_CONTENT_H_GAP+PANEL_CONTENT_RH_GAP;
		mTxtUnderlineReset=new PosTouchableTextField(mParent,TEXT_FIELD_SMALL_WIDTH);
		mTxtUnderlineReset.setTitle("Hexadecimal code for Resetting Underline.");
		mTxtUnderlineReset.setLocation(left, top);
		add(mTxtUnderlineReset);
	}
	
	private void setLineSpacingReset(){
		final int top=mTxtUnderlineReset.getY()+mTxtUnderlineReset.getHeight()+PANEL_CONTENT_V_GAP;
		int left=mTxtPort.getX()+mTxtPort.getWidth()+PANEL_CONTENT_RH_GAP;
		final JLabel label=new JLabel("Reset LineSpace :");
		label.setBounds(left, top, LABEL_R_WIDTH, LABEL_HEIGHT);
		setLabelFont(label);
		add(label);
		
		left=label.getX()+LABEL_R_WIDTH+PANEL_CONTENT_H_GAP+PANEL_CONTENT_RH_GAP;
		mTxtLineSpacingReset=new PosTouchableTextField(mParent,TEXT_FIELD_SMALL_WIDTH);
		mTxtLineSpacingReset.setTitle("Hexadecimal code for Resetting Line Space.");
		mTxtLineSpacingReset.setLocation(left, top);
		add(mTxtLineSpacingReset);
	}

//	private void setPrinterDetails() {
//		try {
//			mDeviceSettingProvider = new PosDevSettingProvider();
//			mChkHasDevice.setSelected(mDeviceSettingProvider
//					.isDeviceAttached(PosDevices.RecieptPrinter));
//			mPrinterConfigProvider = new PosDevPrinterConfigProvider();
//			mPrinterConfig = mPrinterConfigProvider.getDeviceConfiguration();
//
//			if (mPrinterConfig != null) {
//				mTxtPort.setText(mPrinterConfig.getPort());
//				mTxtInit.setText(mPrinterConfig.getCmdInit());
//				mTxtOpenDrawer.setText(mPrinterConfig.getCmdOpenDrawer());
//				mTxtLineFeed.setText(mPrinterConfig.getCmdLineFeed());
//				mTxtSmallFont.setText(mPrinterConfig.getCmdSmallFont());
//				mTxtBigFont.setText(mPrinterConfig.getCmdBigFont());
//				mTxtUnderLine.setText(mPrinterConfig.getCmdUnderLine());
//				mTxtUnderlineReset.setText(mPrinterConfig
//						.getCmdUnderlineReset());
//				mTxtLineSpacing.setText(mPrinterConfig.getCmdLineSpacing());
//				mTxtLineSpacingReset.setText(mPrinterConfig
//						.getCmdLineSpacingReset());
//				mTxtPrinterReset.setText(mPrinterConfig.getCmdPrinterReset());
//				mTxtCutFull.setText(mPrinterConfig.getCmdCutFull());
//				mTxtCutPartial.setText(mPrinterConfig
//						.getCmdCutPartial());
//				mTxtJustifyLeft.setText(mPrinterConfig.getCmdJustifyLeft());
//				mTxtJustifyRight.setText(mPrinterConfig.getCmdJustifyRight());
//				mTxtJustifyCenter.setText(mPrinterConfig.getCmdJustifyCenter());
//				mTxtMarginLeft.setText(mPrinterConfig.getCmdMarginLeft());
//				mTxtPrintWidth.setText(mPrinterConfig.getCmdPrintWidth());
//				mOpenDrawer.setSelected(mPrinterConfig.isOpenCashDrawer());
//				mOpenDrawerOnly.setSelected(mPrinterConfig.isOpenCashDrawerWithoutSale());
//			}
//		} catch (Exception err) {
//			PosLog.write(this, "setPrinterDetails", err);
//			PosFormUtil.showErrorMessageBox(mParent, "Error in printer settings. Please contact Administrator.");
//		}
//	}
	

	@Override
	protected void setDeviceEnabled(boolean enable) {
		
		mTxtPort.setEditable(enable);
		mTxtInit.setEditable(enable);
		mTxtOpenDrawer.setEditable(enable);
		mOpenDrawerOnly.setEnabled(enable);
		mTxtLineFeed.setEditable(enable);
		mTxtSmallFont.setEditable(enable);
		mTxtBigFont.setEditable(enable);
		mTxtUnderLine.setEditable(enable);
		mTxtUnderlineReset.setEditable(enable);
		mTxtLineSpacing.setEditable(enable);
		mTxtLineSpacingReset.setEditable(enable);
		mTxtPrinterReset.setEditable(enable);
		mTxtCutFull.setEditable(enable);
		mTxtCutPartial.setEditable(enable);
		mTxtJustifyLeft.setEditable(enable);
		mTxtJustifyRight.setEditable(enable);
		mTxtJustifyCenter.setEditable(enable);
		mTxtMarginLeft.setEditable(enable);
		mTxtPrintWidth.setEditable(enable);
	}
//	
//	
//	@Override
//	public boolean onValidating() {
//		if(!hasDevice()) return true;
//		if (mChkHasDevice.isSelected()
//				&& (mTxtPort.getText().trim().length() == 0
//				|| mTxtInit.getText().trim().length() == 0
//				|| mTxtLineFeed.getText().trim().length() == 0
//				|| mTxtPrinterReset.getText().trim().length() == 0
//				|| mTxtPrintWidth.getText().trim().length() == 0
//				|| mTxtCutPartial.getText().trim().length() == 0)) {
//			PosFormUtil.showInformationMessageBox(mParent, "Incomplete entry. Values marked with '*' is mandatory.");
//			return false;
//		}
//		else
//			return true;
//	}
//
//	@Override
//	public boolean onSave() {
//		if (mPrinterConfig == null) {
//			mPrinterConfig=new BeanDevPrinterConfig();
//		}
//		
//		mPrinterConfig.setPort(mTxtPort.getText().trim());
//		mPrinterConfig.setCmdInit(mTxtInit.getText().trim());
//		mPrinterConfig.setCmdOpenDrawer(mTxtOpenDrawer.getText().trim());
//		mPrinterConfig.setCmdLineFeed(mTxtLineFeed.getText().trim());
//		mPrinterConfig.setCmdSmallFont(mTxtSmallFont.getText().trim());
//		mPrinterConfig.setCmdBigFont(mTxtBigFont.getText().trim());
//		mPrinterConfig.setCmdUnderLine(mTxtUnderLine.getText().trim());
//		mPrinterConfig.setCmdUnderlineReset(mTxtUnderlineReset.getText().trim());
//		mPrinterConfig.setCmdLineSpacing(mTxtLineSpacing.getText().trim());
//		mPrinterConfig.setCmdLineSpacingReset(mTxtLineSpacingReset.getText().trim());
//		mPrinterConfig.setCmdPrinterReset(mTxtPrinterReset.getText().trim());
//		mPrinterConfig.setCmdCutFull(mTxtCutFull.getText().trim());
//		mPrinterConfig.setCmdCutPartial(mTxtCutPartial.getText().trim());
//		mPrinterConfig.setCmdJustifyLeft(mTxtJustifyLeft.getText().trim());
//		mPrinterConfig.setCmdJustifyRight(mTxtJustifyRight.getText().trim());
//		mPrinterConfig.setCmdJustifyCenter(mTxtJustifyCenter.getText().trim());
//		mPrinterConfig.setCmdMarginLeft(mTxtMarginLeft.getText().trim());
//		mPrinterConfig.setCmdPrintWidth(mTxtPrintWidth.getText().trim());
//		mPrinterConfig.setOpenCashDrawer(mOpenDrawer.isSelected());
//		mPrinterConfig.setOpenCashDrawerWithoutSale(mOpenDrawerOnly.isSelected());
//		
//		mPrinterConfigProvider.saveSettings(mPrinterConfig);
//		mDeviceSettingProvider.setDeviceAttached(PosDevices.RecieptPrinter, mChkHasDevice.isSelected());
//		return true;
//	}
//
//	@Override
//	public void onGotFocus() {
//		mTxtPort.requestFocus();
//	}

}
