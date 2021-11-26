/**
 * 
 */
package com.indocosmo.pos.forms.components.terminal.settings.devices;

import java.awt.Color;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import com.google.zxing.Dimension;
import com.indocosmo.pos.common.utilities.PosFormUtil;
import com.indocosmo.pos.common.utilities.PosResUtil;
import com.indocosmo.pos.data.beans.terminal.device.BeanDevEFTConfig;
import com.indocosmo.pos.data.providers.terminaldb.PosDevEFTConfigProvider;
import com.indocosmo.pos.data.providers.terminaldb.PosDevSettingProvider.PosDevices;
import com.indocosmo.pos.forms.PosOrderEntryForm;
import com.indocosmo.pos.forms.components.checkboxes.PosCheckBox;
import com.indocosmo.pos.forms.components.textfields.PosTouchableFieldBase;
import com.indocosmo.pos.forms.components.textfields.PosTouchableTextField;

/**
 * @author deepak
 *
 */
public class PosDevEFTSettingsTab extends PosDeviceSettingsTab{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3480686110344088866L;

	private static final int TEXT_FIELD_WIDTH=300;

	private static final int LABEL_WIDTH=150;
	private static final int LABEL_HEIGHT=PosTouchableFieldBase.LAYOUT_DEF_HEIGHT;

	private static final int PANEL_CONTENT_H_GAP=PosOrderEntryForm.PANEL_CONTENT_H_GAP;
	private static final int PANEL_CONTENT_V_GAP=PosOrderEntryForm.PANEL_CONTENT_V_GAP;	

	private JDialog mParent;
	
	private JLabel mLabelHostIP;
	private PosTouchableTextField mTxtHostIP;

	private JLabel mLabelHostPort;
	private PosTouchableTextField mTxtHostPort;

	private PosCheckBox mChkOpenCashBox;
	
	private BeanDevEFTConfig mPosEFTConfigObject;
	
	private PosDevEFTConfigProvider mPosDeviceEFTConfigProvider;

	private JLabel mLabelTimeOut;

	private PosTouchableTextField mTxtSocketTimeOut;
	
	/**
	 * 
	 */
	public PosDevEFTSettingsTab(JDialog parent) {
		super(parent, "EFT",PosDevices.POSDEVICE,true, LAYOUT_WIDTH, LAYOUT_HEIGHT);

	}

	/**
	 * 
	 */
	private void setEFTSettings() {
		setEFTHostIP();
		setEFTHostPort();
		setSocketTimeOut();
		setOpenCashBox();
		setEFTDetails();
	}

	

	/**
	 * 
	 */
	private void setEFTHostIP() {
		
		int left=PANEL_CONTENT_H_GAP/4+1;
		final int top=mChkHasDevice.getY()+mChkHasDevice.getHeight()+PANEL_CONTENT_V_GAP/4+1;
//		int left=PANEL_CONTENT_V_GAP;
//		int top=PANEL_CONTENT_V_GAP;
		mLabelHostIP=new JLabel();
		mLabelHostIP.setText("EFT Host IP :");
		mLabelHostIP.setOpaque(true);
		mLabelHostIP.setBackground(Color.LIGHT_GRAY);
		mLabelHostIP.setBorder(new EmptyBorder(2, 2, 2, 2));
		mLabelHostIP.setHorizontalAlignment(SwingConstants.LEFT);		
		mLabelHostIP.setBounds(left, top, LABEL_WIDTH, LABEL_HEIGHT);		
		mLabelHostIP.setFont(PosFormUtil.getLabelFont());
		mLabelHostIP.setFocusable(true);
		add(mLabelHostIP);

		left=mLabelHostIP.getX()+mLabelHostIP.getWidth()+PANEL_CONTENT_H_GAP/8;
		mTxtHostIP=new PosTouchableTextField(mParent,TEXT_FIELD_WIDTH);
		mTxtHostIP.setTitle("Enter Host IP.");
		mTxtHostIP.setLocation(left, top);	
		add(mTxtHostIP);
	}

	/**
	 * 
	 */
	private void setEFTHostPort() {
		
		int left=mLabelHostIP.getX();
		int top=mLabelHostIP.getY()+mLabelHostIP.getHeight()+ PANEL_CONTENT_V_GAP/4;
		
		mLabelHostPort=new JLabel();
		mLabelHostPort.setText("EFT Host Port  :");
		mLabelHostPort.setOpaque(true);
		mLabelHostPort.setBackground(Color.LIGHT_GRAY);
		mLabelHostPort.setBorder(new EmptyBorder(2, 2, 2, 2));
		mLabelHostPort.setHorizontalAlignment(SwingConstants.LEFT);		
		mLabelHostPort.setBounds(left, top, LABEL_WIDTH, LABEL_HEIGHT);		
		mLabelHostPort.setFont(PosFormUtil.getLabelFont());
		mLabelHostPort.setFocusable(true);
		add(mLabelHostPort);

		left=mLabelHostPort.getX()+mLabelHostPort.getWidth()+PANEL_CONTENT_H_GAP/8;
		mTxtHostPort=new PosTouchableTextField(mParent,TEXT_FIELD_WIDTH);
		mTxtHostPort.setTitle("Enter Host Port.");
		mTxtHostPort.setLocation(left, top);	
		add(mTxtHostPort);
		
	}

	/**
	 * Create the field to enter the socket time out.
	 * 
	 */
	private void setSocketTimeOut() {

		int left=mLabelHostPort.getX();
		int top=mLabelHostPort.getY()+mLabelHostPort.getHeight()+ PANEL_CONTENT_V_GAP/4;
		
		mLabelTimeOut=new JLabel();
		mLabelTimeOut.setText("Time out :");
		mLabelTimeOut.setOpaque(true);
		mLabelTimeOut.setBackground(Color.LIGHT_GRAY);
		mLabelTimeOut.setBorder(new EmptyBorder(2, 2, 2, 2));
		mLabelTimeOut.setHorizontalAlignment(SwingConstants.LEFT);		
		mLabelTimeOut.setBounds(left, top, LABEL_WIDTH, LABEL_HEIGHT);		
		mLabelTimeOut.setFont(PosFormUtil.getLabelFont());
		mLabelTimeOut.setFocusable(true);
		add(mLabelTimeOut);

		left=mLabelTimeOut.getX()+mLabelTimeOut.getWidth()+PANEL_CONTENT_H_GAP/8;
		mTxtSocketTimeOut=new PosTouchableTextField(mParent,TEXT_FIELD_WIDTH);
		mTxtSocketTimeOut.setTitle("Time out ");
		mTxtSocketTimeOut.setLocation(left, top);	
		add(mTxtSocketTimeOut);
	
	}
	/**
	 * Creates the has paper cut check box
	 */
	protected void setOpenCashBox() {

		  int left=mLabelTimeOut.getX();
		final int top=mLabelTimeOut.getY()+mLabelTimeOut.getHeight()+ PANEL_CONTENT_V_GAP/4;
		
		final JLabel mCheckBoxLabel = new JLabel("Open Cashbox :");
		mCheckBoxLabel.setOpaque(true);
		mCheckBoxLabel.setBackground(Color.LIGHT_GRAY);
		mCheckBoxLabel.setBorder(new EmptyBorder(2, 2, 2, 2));
		mCheckBoxLabel.setHorizontalAlignment(SwingConstants.LEFT);		
		mCheckBoxLabel.setBounds(left, top, LABEL_WIDTH, LABEL_HEIGHT);		
		mCheckBoxLabel.setFont(PosFormUtil.getLabelFont());
		mCheckBoxLabel.setFocusable(true);
		
 
		add(mCheckBoxLabel);
		
		left=mCheckBoxLabel.getX()+mCheckBoxLabel.getWidth()+PANEL_CONTENT_H_GAP/8;
		
		
		mChkOpenCashBox = new PosCheckBox();
		mChkOpenCashBox.setFocusable(false);
		mChkOpenCashBox.setHorizontalAlignment(JTextField.LEFT);
		mChkOpenCashBox.setBounds(left, top, getWidth(), LABEL_HEIGHT);
//		mChkOpenCashBox.setPreferredSize(new Dimension(45, LABEL_HEIGHT));
		mChkOpenCashBox.setIcon(PosResUtil.getImageIconFromResource("checkbox_normal.png"));
		mChkOpenCashBox.setSelectedIcon(PosResUtil.getImageIconFromResource("checkbox_selected.png"));
 		mChkOpenCashBox.setLayout(null);
 		PosFormUtil.setCheckboxDefaultFont(mChkOpenCashBox);
		mChkOpenCashBox.setOpaque(true);
		mChkOpenCashBox.setMnemonic('b');
		add(mChkOpenCashBox);
	
	}
	private void setEFTDetails(){
		
		mPosDeviceEFTConfigProvider = new PosDevEFTConfigProvider();
		mPosEFTConfigObject = mPosDeviceEFTConfigProvider.getDeviceConfiguration();
		
		if(mPosEFTConfigObject!=null){
			mTxtHostIP.setText(mPosEFTConfigObject.getEFTHostIP());
			mTxtHostPort.setText(String.valueOf(mPosEFTConfigObject.getEFTHostPort()));
			mTxtSocketTimeOut.setText(String.valueOf(mPosEFTConfigObject.getSocketTimeOut()));
			mChkOpenCashBox.setSelected(mPosEFTConfigObject.isOpenCashBox());
		}
		
		
	}
	/* (non-Javadoc)
	 * @see com.indocosmo.pos.components.tab.PosTab#onValidating()
	 */
	@Override
	public boolean onValidating() {
		if(!hasDevice()) return true;
		if(mChkHasDevice.isSelected() && (mTxtHostIP.getText().trim().length()<=0
				|| mTxtHostPort.getText().trim().length()<=0 || mTxtSocketTimeOut.getText().trim().length()<=0)){
			PosFormUtil.showErrorMessageBox(mParent, "The EFT settings are not valid. Please enter valid setting.");	
			return false;
		}
		return true;
	}
	/* (non-Javadoc)
	 * @see com.indocosmo.pos.components.terminal.settings.PosTermnalSettingsBase#onSave()
	 */
	@Override
	public boolean onSave() {
		if(mPosEFTConfigObject==null){
			mPosEFTConfigObject = new BeanDevEFTConfig();
		}
		
		mPosEFTConfigObject.setEFTHostIP(mTxtHostIP.getText());
		mPosEFTConfigObject.setEFTHostPort(Integer.parseInt(mTxtHostPort.getText()));
		mPosEFTConfigObject.setSocketTimeOut(Integer.parseInt(mTxtSocketTimeOut.getText()));
		mPosEFTConfigObject.setOpenCashBox(mChkOpenCashBox.isSelected());
		
		if(mPosDeviceEFTConfigProvider==null){
			mPosDeviceEFTConfigProvider = new PosDevEFTConfigProvider();
		}
		mPosDeviceEFTConfigProvider.setSettings(mPosEFTConfigObject);
		mDeviceSettingProvider.setDeviceAttached(PosDevices.POSDEVICE, mChkHasDevice.isSelected());
		return true;
	}
	
	/* (non-Javadoc)
	 * @see com.indocosmo.pos.components.tab.PosTab#onGotFocus()
	 */
	@Override
	public void onGotFocus() {
		
		mTxtHostIP.requestFocus();
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.pos.components.terminal.settings.devices.PosDevicePanel#initControls()
	 */
	@Override
	protected void initControls() {
		setEFTSettings();
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.pos.components.terminal.settings.devices.PosDevicePanel#setDeviceEnabled(boolean)
	 */
	@Override
	protected void setDeviceEnabled(boolean enable) {

		mTxtHostIP.setEditable(enable);
		mTxtHostPort.setEditable(enable);
		mTxtSocketTimeOut.setEditable(enable);
		mChkOpenCashBox.setEnabled(enable);
	}

}
