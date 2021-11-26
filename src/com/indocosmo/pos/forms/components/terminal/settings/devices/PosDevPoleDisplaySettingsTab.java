package com.indocosmo.pos.forms.components.terminal.settings.devices;

import java.awt.Color;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.border.EmptyBorder;

import com.indocosmo.pos.common.enums.device.PortParity;
import com.indocosmo.pos.common.utilities.PosFormUtil;
import com.indocosmo.pos.data.beans.terminal.device.BeanDevPoleDisplayConfig;
import com.indocosmo.pos.data.providers.terminaldb.PosDevPoleDisplayConfigProvider;
import com.indocosmo.pos.data.providers.terminaldb.PosDevSettingProvider.PosDevices;
import com.indocosmo.pos.forms.components.textfields.PosItemBrowsableField;
import com.indocosmo.pos.forms.components.textfields.PosTouchableFieldBase;
import com.indocosmo.pos.forms.components.textfields.PosTouchableNumberField;
import com.indocosmo.pos.forms.components.textfields.PosTouchableTextField;
import com.indocosmo.pos.forms.components.textfields.listners.PosTouchableFieldAdapter;

@SuppressWarnings("serial")
public final class PosDevPoleDisplaySettingsTab extends PosDeviceSettingsTab{

	private static final int LABEL_L_WIDTH=200;
	private static final int LABEL_R_WIDTH=131;
	private static final int LABEL_HEIGHT=PosTouchableFieldBase.LAYOUT_DEF_HEIGHT;
	
	private static final int TEXT_FIELD_SMALL_WIDTH=225;
	private static final int TEXT_FIELD_WIDE_WIDTH=TEXT_FIELD_SMALL_WIDTH*2+LABEL_R_WIDTH-10+PANEL_CONTENT_H_GAP*2;
	
	private PosTouchableTextField mTxtPort;
	private PosTouchableNumberField mTxtRowCount;
	private PosTouchableNumberField mTxtColumnCount;
	private PosTouchableTextField mTxtCmdMove;
	private PosTouchableTextField mTxtCmdClear;
	private PosTouchableTextField mTxtCmdClearLine;
	
	private PosTouchableNumberField mTxtPortBaudRate;
	private PosTouchableNumberField mTxtPortDataBit;
	private PosItemBrowsableField mTxtPortParity;
	private PosTouchableNumberField mTxtPortStopBit;
	
	private PosTouchableTextField mTxtMsgStartUp;
	private PosTouchableTextField mTxtMsgNewBill;
	private PosTouchableTextField mTxtMsgClosed;
	private PosDevPoleDisplayConfigProvider mDisplayConfigProvider;
	
	private BeanDevPoleDisplayConfig mDisplayConfig;
	
	private PortParity mselectedPortParity;
	
	public PosDevPoleDisplaySettingsTab(JDialog parent){
		super(parent,"Pole",PosDevices.POLEDISPLAY,true,LAYOUT_WIDTH,LAYOUT_HEIGHT);
		setMnemonicChar('l');
	}

	@Override
	protected void initControls() {
		
		setRows();
		
		setColumns();
		setBaudRate();
		setCommandMove();
		setDataBits();
		setCommandClear();
		setParity();
		setCommandClearLine();
		setStopBits();
		setPort();
		setMsgStartup();
		setMsgNewBill();
		setMsgClosed();
		setPoleDisplayDetails();
	}
	
	private void setPoleDisplayDetails(){
		
		mDisplayConfigProvider=new PosDevPoleDisplayConfigProvider();
		mDisplayConfig=mDisplayConfigProvider.getDeviceConfiguration();
		if(mDisplayConfig!=null){
			mTxtCmdClear.setText(mDisplayConfig.getCommandClear());
			mTxtCmdClearLine.setText(mDisplayConfig.getCommandClearLine());
			mTxtCmdMove.setText(mDisplayConfig.getCommandMove());
			mTxtColumnCount.setText(String.valueOf(mDisplayConfig.getColumnCount()));
			mTxtRowCount.setText(String.valueOf(mDisplayConfig.getRowCount()));
			mTxtMsgClosed.setText(mDisplayConfig.getMessageClosed());
			mTxtMsgNewBill.setText(mDisplayConfig.getMessageNewBill());
			mTxtMsgStartUp.setText(mDisplayConfig.getMessageStartup());
			mTxtPort.setText(mDisplayConfig.getPort());
			mTxtPortBaudRate.setText(String.valueOf(mDisplayConfig.getPortBaudRate()));
			mTxtPortDataBit.setText(String.valueOf(mDisplayConfig.getPortDataBits()));
			mselectedPortParity=mDisplayConfig.getPortParity();
			mTxtPortParity.setText(String.valueOf(mselectedPortParity.getDisplayText()));
			mTxtPortStopBit.setText(String.valueOf(mDisplayConfig.getPortStopBits()));
		}
	}
	
	private void setRows(){
		int left=PANEL_CONTENT_H_GAP/4+1;
		final int top=mChkHasDevice.getY()+mChkHasDevice.getHeight()+PANEL_CONTENT_V_GAP;
		final JLabel label=new JLabel(PosFormUtil.getMnemonicString("Row", 'w'));
		label.setOpaque(true);
		label.setBackground(Color.LIGHT_GRAY);
		label.setBorder(new EmptyBorder(2, 2, 2, 2));
		label.setBounds(left, top, LABEL_L_WIDTH, LABEL_HEIGHT);
		setLabelFont(label);
		add(label);
		
		left=label.getX()+LABEL_L_WIDTH+PANEL_CONTENT_H_GAP/8;
		mTxtRowCount=new PosTouchableNumberField(mParent,TEXT_FIELD_SMALL_WIDTH);
		mTxtRowCount.setTitle("Row count ?");
		mTxtRowCount.setLocation(left, top);
		mTxtRowCount.setMnemonic('w');
		add(mTxtRowCount);
	}
	
	private void setColumns(){
		int left=PANEL_CONTENT_H_GAP/4+1;
		final int top=mTxtRowCount.getY()+LABEL_HEIGHT+PANEL_CONTENT_V_GAP/5+1;
		final JLabel label=new JLabel(PosFormUtil.getMnemonicString("Columns :",'u'));
		label.setOpaque(true);
		label.setBackground(Color.LIGHT_GRAY);
		label.setBorder(new EmptyBorder(2, 2, 2, 2));
		label.setBounds(left, top, LABEL_L_WIDTH, LABEL_HEIGHT);
		setLabelFont(label);
		add(label);
		
		left=label.getX()+LABEL_L_WIDTH+PANEL_CONTENT_H_GAP/8;
		mTxtColumnCount=new PosTouchableNumberField(mParent,TEXT_FIELD_SMALL_WIDTH);
		mTxtColumnCount.setTitle("Column count ?");
		mTxtColumnCount.setLocation(left, top);
		mTxtColumnCount.setMnemonic('u');
		add(mTxtColumnCount);
	}
	
	private void setBaudRate(){
		int left=mTxtColumnCount.getX()+mTxtColumnCount.getWidth()+PANEL_CONTENT_H_GAP/2+1;
		final int top=mTxtColumnCount.getY();
		final JLabel label=new JLabel(PosFormUtil.getMnemonicString("Baud Rate :",'B'));
		label.setOpaque(true);
		label.setBackground(Color.LIGHT_GRAY);
		label.setBorder(new EmptyBorder(2, 2, 2, 2));
		label.setBounds(left, top, LABEL_R_WIDTH, LABEL_HEIGHT);
		setLabelFont(label);
		add(label);
		
		left=label.getX()+LABEL_R_WIDTH+PANEL_CONTENT_H_GAP/8;
		mTxtPortBaudRate=new PosTouchableNumberField(mParent,TEXT_FIELD_SMALL_WIDTH);
		mTxtPortBaudRate.setTitle("Baud Rate ?");
		mTxtPortBaudRate.setLocation(left, top);
		mTxtPortBaudRate.setMnemonic('B');
		add(mTxtPortBaudRate);
	}
	
	private void setCommandMove(){
		int left=PANEL_CONTENT_H_GAP/4+1;
		final int top=mTxtColumnCount.getY()+LABEL_HEIGHT+PANEL_CONTENT_V_GAP/5+1;
		final JLabel label=new JLabel(PosFormUtil.getMnemonicString("Move command :",'v'));
		label.setOpaque(true);
		label.setBackground(Color.LIGHT_GRAY);
		label.setBorder(new EmptyBorder(2, 2, 2, 2));
		label.setBounds(left, top, LABEL_L_WIDTH, LABEL_HEIGHT);
		setLabelFont(label);
		add(label);
		
		left=label.getX()+LABEL_L_WIDTH+PANEL_CONTENT_H_GAP/8;
		mTxtCmdMove=new PosTouchableTextField(mParent,TEXT_FIELD_SMALL_WIDTH);
		mTxtCmdMove.setTitle("Move command ?");
		mTxtCmdMove.setLocation(left, top);
		mTxtCmdMove.setMnemonic('v');
		add(mTxtCmdMove);
	}
	
	private void setDataBits(){
		int left=mTxtCmdMove.getX()+mTxtCmdMove.getWidth()+PANEL_CONTENT_H_GAP/2+1;
		final int top=mTxtCmdMove.getY();
		final JLabel label=new JLabel(PosFormUtil.getMnemonicString("Data Bits :",'D'));
		label.setOpaque(true);
		label.setBackground(Color.LIGHT_GRAY);
		label.setBorder(new EmptyBorder(2, 2, 2, 2));
		label.setBounds(left, top, LABEL_R_WIDTH, LABEL_HEIGHT);
		setLabelFont(label);
		add(label);
		
		left=label.getX()+LABEL_R_WIDTH+PANEL_CONTENT_H_GAP/8;
		mTxtPortDataBit=new PosTouchableNumberField(mParent,TEXT_FIELD_SMALL_WIDTH);
		mTxtPortDataBit.setTitle("Data Bits ?");
		mTxtPortDataBit.setLocation(left, top);
		mTxtPortDataBit.setMnemonic('D');
		add(mTxtPortDataBit);
	}
	
	private void setCommandClear(){
		int left=PANEL_CONTENT_H_GAP/4+1;
		final int top=mTxtCmdMove.getY()+LABEL_HEIGHT+PANEL_CONTENT_V_GAP/5+1;
		final JLabel label=new JLabel(PosFormUtil.getMnemonicString("Clear command :",'a'));
		label.setOpaque(true);
		label.setBackground(Color.LIGHT_GRAY);
		label.setBorder(new EmptyBorder(2, 2, 2, 2));
		label.setBounds(left, top, LABEL_L_WIDTH, LABEL_HEIGHT);
		setLabelFont(label);
		add(label);
		
		left=label.getX()+LABEL_L_WIDTH+PANEL_CONTENT_H_GAP/8;
		mTxtCmdClear=new PosTouchableTextField(mParent,TEXT_FIELD_SMALL_WIDTH);
		mTxtCmdClear.setTitle("Clear command ?");
		mTxtCmdClear.setLocation(left, top);
		mTxtCmdClear.setMnemonic('a');
		add(mTxtCmdClear);
	}
	
	private void setParity(){
		int left=mTxtCmdClear.getX()+mTxtCmdClear.getWidth()+PANEL_CONTENT_H_GAP/2+1;
		final int top=mTxtCmdClear.getY();
		final JLabel label=new JLabel(PosFormUtil.getMnemonicString("Parity :",'y'));
		label.setOpaque(true);
		label.setBackground(Color.LIGHT_GRAY);
		label.setBorder(new EmptyBorder(2, 2, 2, 2));
		label.setBounds(left, top, LABEL_R_WIDTH, LABEL_HEIGHT);
		setLabelFont(label);
		add(label);
		
		left=label.getX()+LABEL_R_WIDTH+PANEL_CONTENT_H_GAP/8;
		mTxtPortParity=new PosItemBrowsableField(mParent,TEXT_FIELD_SMALL_WIDTH);
		mTxtPortParity.setBrowseItemList(PortParity.values());
		mTxtPortParity.setTitle("Parity ?");
		mTxtPortParity.setLocation(left, top);
		mTxtPortParity.setMnemonic('y');
		mTxtPortParity.setListner(new PosTouchableFieldAdapter(){
			@Override
			public void onValueSelected(Object item) {
				mselectedPortParity=(PortParity)item;
			}
			@Override
			public void onReset() {
				mselectedPortParity=null;
			}
		});
		add(mTxtPortParity);
	}
	
	private void setCommandClearLine(){
		int left=PANEL_CONTENT_H_GAP/4+1;
		final int top=mTxtCmdClear.getY()+LABEL_HEIGHT+PANEL_CONTENT_V_GAP/5+1;
		final JLabel label=new JLabel(PosFormUtil.getMnemonicString("Clear line command :",'i'));
		label.setOpaque(true);
		label.setBackground(Color.LIGHT_GRAY);
		label.setBorder(new EmptyBorder(2, 2, 2, 2));
		label.setBounds(left, top, LABEL_L_WIDTH, LABEL_HEIGHT);
		setLabelFont(label);
		add(label);
		
		left=label.getX()+LABEL_L_WIDTH+PANEL_CONTENT_H_GAP/8;
		mTxtCmdClearLine=new PosTouchableTextField(mParent,TEXT_FIELD_SMALL_WIDTH);
		mTxtCmdClearLine.setTitle("Clear line command ?");
		mTxtCmdClearLine.setLocation(left, top);
		mTxtCmdClearLine.setMnemonic('i');
		add(mTxtCmdClearLine);
	}
	
	private void setStopBits(){
		int left=mTxtCmdClearLine.getX()+mTxtCmdClear.getWidth()+PANEL_CONTENT_H_GAP/2+1;
		final int top=mTxtCmdClearLine.getY();
		final JLabel label=new JLabel(PosFormUtil.getMnemonicString("Stop Bits :",'S'));
		label.setOpaque(true);
		label.setBackground(Color.LIGHT_GRAY);
		label.setBorder(new EmptyBorder(2, 2, 2, 2));
		label.setBounds(left, top, LABEL_R_WIDTH, LABEL_HEIGHT);
		setLabelFont(label);
		add(label);
		
		left=label.getX()+LABEL_R_WIDTH+PANEL_CONTENT_H_GAP/8;
		mTxtPortStopBit=new PosTouchableNumberField(mParent,TEXT_FIELD_SMALL_WIDTH);
		mTxtPortStopBit.setTitle("Stop Bits ?");
		mTxtPortStopBit.setLocation(left, top);
		mTxtPortStopBit.setMnemonic('S');
		add(mTxtPortStopBit);
	}
	
	private void setPort(){
		int left=PANEL_CONTENT_H_GAP/4+1;
		final int top=mTxtCmdClearLine.getY()+mTxtCmdClearLine.getHeight()+PANEL_CONTENT_V_GAP/5+1;
		final JLabel label=new JLabel(PosFormUtil.getMnemonicString("Port :",'r'));
		label.setOpaque(true);
		label.setBackground(Color.LIGHT_GRAY);
		label.setBorder(new EmptyBorder(2, 2, 2, 2));
		label.setBounds(left, top, LABEL_L_WIDTH, LABEL_HEIGHT);
		setLabelFont(label);
		add(label);
		
		left=label.getX()+LABEL_L_WIDTH+PANEL_CONTENT_H_GAP/8;
		mTxtPort=new PosTouchableTextField(mParent,TEXT_FIELD_WIDE_WIDTH);
		mTxtPort.setTitle("Port");
		mTxtPort.setMnemonic('r');
		mTxtPort.setLocation(left, top);
		add(mTxtPort);
	}
	
	private void setMsgStartup(){
		int left=PANEL_CONTENT_H_GAP/4+1;
		final int top=mTxtPort.getY()+LABEL_HEIGHT+PANEL_CONTENT_V_GAP/5+1;
		final JLabel label=new JLabel(PosFormUtil.getMnemonicString("Startup message :",'m'));
		label.setOpaque(true);
		label.setBackground(Color.LIGHT_GRAY);
		label.setBorder(new EmptyBorder(2, 2, 2, 2));
		label.setBounds(left, top, LABEL_L_WIDTH, LABEL_HEIGHT);
		setLabelFont(label);
		add(label);
		
		left=label.getX()+LABEL_L_WIDTH+PANEL_CONTENT_H_GAP/8;
		mTxtMsgStartUp=new PosTouchableTextField(mParent,TEXT_FIELD_WIDE_WIDTH);
		mTxtMsgStartUp.setTitle("Startup message ?");
		mTxtMsgStartUp.setLocation(left, top);
		mTxtMsgStartUp.setMnemonic('m');
		add(mTxtMsgStartUp);
	}
	
	private void setMsgNewBill(){
		int left=PANEL_CONTENT_H_GAP/4+1;
		final int top=mTxtMsgStartUp.getY()+LABEL_HEIGHT+PANEL_CONTENT_V_GAP/5+1;
		final JLabel label=new JLabel(PosFormUtil.getMnemonicString("New Bill message :",'N'));
		label.setOpaque(true);
		label.setBackground(Color.LIGHT_GRAY);
		label.setBorder(new EmptyBorder(2, 2, 2, 2));
		label.setBounds(left, top, LABEL_L_WIDTH, LABEL_HEIGHT);
		setLabelFont(label);
		add(label);
		
		left=label.getX()+LABEL_L_WIDTH+PANEL_CONTENT_H_GAP/8;
		mTxtMsgNewBill=new PosTouchableTextField(mParent,TEXT_FIELD_WIDE_WIDTH);
		mTxtMsgNewBill.setTitle("New Bill message ?");
		mTxtMsgNewBill.setLocation(left, top);
		mTxtMsgNewBill.setMnemonic('N');
		add(mTxtMsgNewBill);
	}
	
	private void setMsgClosed(){
		int left=PANEL_CONTENT_H_GAP/4+1;
		final int top=mTxtMsgNewBill.getY()+LABEL_HEIGHT+PANEL_CONTENT_V_GAP/5+1;
		final JLabel label=new JLabel(PosFormUtil.getMnemonicString("Closed message :",'g'));
		label.setOpaque(true);
		label.setBackground(Color.LIGHT_GRAY);
		label.setBorder(new EmptyBorder(2, 2, 2, 2));
		label.setBounds(left, top, LABEL_L_WIDTH, LABEL_HEIGHT);
		setLabelFont(label);
		add(label);
		
		left=label.getX()+LABEL_L_WIDTH+PANEL_CONTENT_H_GAP/8;
		mTxtMsgClosed=new PosTouchableTextField(mParent,TEXT_FIELD_WIDE_WIDTH);
		mTxtMsgClosed.setTitle("Closed message ?");
		mTxtMsgClosed.setLocation(left, top);
		mTxtMsgClosed.setMnemonic('g');
		add(mTxtMsgClosed);
	}

	@Override
	protected void setDeviceEnabled(boolean enable) {
		mTxtPort.setEditable(enable);
		mTxtRowCount.setEditable(enable);
		mTxtColumnCount.setEditable(enable);
		mTxtCmdMove.setEditable(enable);
		mTxtCmdClear.setEditable(enable);
		mTxtCmdClearLine.setEditable(enable);
		mTxtMsgStartUp.setEditable(enable);
		mTxtMsgNewBill.setEditable(enable);
		mTxtMsgClosed.setEditable(enable);
		
		mTxtPortBaudRate.setEditable(enable);
		mTxtPortDataBit.setEditable(enable);
		mTxtPortParity.setEditable(enable);
		mTxtPortStopBit.setEditable(enable);


	}
	
	@Override
	public boolean onValidating() {
		if(!hasDevice()) return true;
		if(mTxtPort.getText().trim().length()<=0
				||mTxtRowCount.getText().trim().length()<=0
				||mTxtColumnCount.getText().trim().length()<=0
				||mTxtCmdMove.getText().trim().length()<=0
				||mTxtCmdClear.getText().trim().length()<=0
				||mTxtCmdClearLine.getText().trim().length()<=0
				||mTxtPortBaudRate.getText().trim().length()<=0
				||mTxtPortDataBit.getText().trim().length()<=0
				||mTxtPortParity.getText().trim().length()<=0
				||mTxtPortStopBit.getText().trim().length()<=0){
			PosFormUtil.showErrorMessageBox(mParent, "The pole display settings are not valid. Please enter valid setting.");
			return false;
		}
		else
			return true;
	}

	@Override
	public boolean onSave() {
		if(mDisplayConfig==null) mDisplayConfig=new BeanDevPoleDisplayConfig();
		mDisplayConfig.setColumnCount(Integer.parseInt(mTxtColumnCount.getText()));
		mDisplayConfig.setRowCount(Integer.parseInt(mTxtRowCount.getText()));
		mDisplayConfig.setPort(mTxtPort.getText());
		mDisplayConfig.setPortBaudRate(Integer.parseInt(mTxtPortBaudRate.getText()));
		mDisplayConfig.setPortDataBits(Integer.parseInt(mTxtPortDataBit.getText()));
		mDisplayConfig.setPortParity(PortParity.get(mselectedPortParity.getValue()));
		mDisplayConfig.setPortStopBits((Integer.parseInt(mTxtPortStopBit.getText())));
		mDisplayConfig.setCommandClear(mTxtCmdClear.getText());
		mDisplayConfig.setCommandClearLine(mTxtCmdClearLine.getText());
		mDisplayConfig.setCommandMove(mTxtCmdMove.getText());
		mDisplayConfig.setMessageClosed(mTxtMsgClosed.getText());
		mDisplayConfig.setMessageNewBill(mTxtMsgNewBill.getText());
		mDisplayConfig.setMessageStartup(mTxtMsgStartUp.getText());
		
		mDisplayConfigProvider.setSettings(mDisplayConfig);
		mDeviceSettingProvider.setDeviceAttached(PosDevices.POLEDISPLAY, mChkHasDevice.isSelected());
		return true;
	}
	
	@Override
	public void onGotFocus() {
		mTxtRowCount.requestFocus();
	}

}
