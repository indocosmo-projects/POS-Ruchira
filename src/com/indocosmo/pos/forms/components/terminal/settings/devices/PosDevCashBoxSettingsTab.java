package com.indocosmo.pos.forms.components.terminal.settings.devices;

import java.awt.Color;
import java.awt.event.KeyEvent;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.RootPaneContainer;
import javax.swing.border.LineBorder;

import com.indocosmo.pos.common.PosLog;
import com.indocosmo.pos.common.enums.device.PortParity;
import com.indocosmo.pos.common.utilities.PosFormUtil;
import com.indocosmo.pos.common.utilities.PosResUtil;
import com.indocosmo.pos.data.beans.terminal.device.BeanDevCashBoxConfig;
import com.indocosmo.pos.data.beans.terminal.device.ports.BeanParallelPortInfo;
import com.indocosmo.pos.data.beans.terminal.device.ports.BeanPortBase;
import com.indocosmo.pos.data.beans.terminal.device.ports.BeanSerialPortInfo;
import com.indocosmo.pos.data.beans.terminal.device.ports.BeanUSBPortInfo;
import com.indocosmo.pos.data.providers.terminaldb.PosDevCashBoxConfigProvider;
import com.indocosmo.pos.data.providers.terminaldb.PosDevSettingProvider.PosDevices;
import com.indocosmo.pos.forms.components.buttons.PosButton;
import com.indocosmo.pos.forms.components.buttons.listners.IPosButtonListner;
import com.indocosmo.pos.forms.components.checkboxes.PosCheckBox;
import com.indocosmo.pos.forms.components.textfields.PosItemBrowsableField;
import com.indocosmo.pos.forms.components.textfields.PosTouchableFieldBase;
import com.indocosmo.pos.forms.components.textfields.PosTouchableNumberField;
import com.indocosmo.pos.forms.components.textfields.PosTouchableTextField;
import com.indocosmo.pos.forms.components.textfields.listners.IPosTouchableFieldListner;
import com.indocosmo.pos.forms.components.textfields.listners.PosTouchableFieldAdapter;
import com.indocosmo.pos.terminal.devices.PosPortDevice.PortType;
import com.indocosmo.pos.terminal.devices.cashbox.PosDeviceCashBox;

/**
 * @author Ramesh S
 * @since 08th Aug 2012
 **/

@SuppressWarnings("serial")
public final class PosDevCashBoxSettingsTab extends PosDeviceSettingsTab {

	private static final int LABEL_L_WIDTH = 120;

	private static final int LABEL_HEIGHT = PosTouchableFieldBase.LAYOUT_DEF_HEIGHT;
	private static final int IMAGE_BUTTON_WIDTH = 130;
	private static final int IMAGE_BUTTON_HEIGHT = 87;
	private static final String IMAGE_BUTTON_RED_NORMAL = "main_menu_red.png";
	private static final String IMAGE_BUTTON_RED_TOUCHED = "main_menu_red_touch.png";

	private PosItemBrowsableField mTxtCashBoxPortType;
	private PosTouchableTextField mTxtCashDrawerOpenCode;
	private PosTouchableTextField mTxtCashDrawerPort;
	private PosCheckBox mChkOpenDrawerManually;
	private PosButton mBtnTest;

	private SerialPortInfoPanel mSerialPortInfoPanel;
	private PosDevCashBoxConfigProvider mCashBoxConfigProvider;

	public PosDevCashBoxSettingsTab(JDialog parent) {
		super(parent, "Cash Box", PosDevices.CASHBOX, true, LAYOUT_WIDTH,
				LAYOUT_HEIGHT);
	}

	@Override
	protected void initControls() {
		createCashBoxheck();
		createFieldCashBoxOpenCode();
		createFiedlCashBoxPort();
		createFieldCashBoxPorType();
		createSerialPortInfo();
		createPanelInfo();
		createTestButton();
		setCashBoxDetails();
	}

	private void createCashBoxheck() {

		final int cashBoxLeft = mChkHasDevice.getX() + 200;
//				+ PANEL_CONTENT_H_GAP;
		final int cashBoxTop = mChkHasDevice.getY();

		mChkOpenDrawerManually = new PosCheckBox("Allow open manually");
		mChkOpenDrawerManually.setMnemonic(KeyEvent.VK_C);
		mChkOpenDrawerManually.setOpaque(false);
		mChkOpenDrawerManually.setSelected(true);
		mChkOpenDrawerManually.setBounds(cashBoxLeft, cashBoxTop, 300, 20);
		mChkOpenDrawerManually.setForeground(PosFormUtil.SECTION_HEADING_PANEL_FG_COLOR);
		PosFormUtil.setCheckboxDefaultFont(mChkOpenDrawerManually);
		mChkHasDevice.add(mChkOpenDrawerManually);

	}

	private void createFieldCashBoxOpenCode() {
		final int labelCashBoxLeft = PANEL_CONTENT_H_GAP;
		final int labelCashBoxTop = mChkHasDevice.getY()
				+ mChkHasDevice.getHeight() + PANEL_CONTENT_V_GAP;
		final JLabel labelCashBox = new JLabel(
				"<html>Code<font color=#FF0000>*</font> :</html>");
		labelCashBox.setBounds(labelCashBoxLeft, labelCashBoxTop,
				LABEL_L_WIDTH, LABEL_HEIGHT);
		setLabelFont(labelCashBox);
		add(labelCashBox);

		final int labelCashBoxCodeLeft = labelCashBox.getX()
				+ labelCashBox.getWidth() + PANEL_CONTENT_H_GAP;
		final int labelCashBoxCodeTop = labelCashBoxTop;

		mTxtCashDrawerOpenCode = new PosTouchableTextField(mParent, 210);
		mTxtCashDrawerOpenCode.setTitle("Cash Box control code.");
		mTxtCashDrawerOpenCode.setLocation(labelCashBoxCodeLeft,
				labelCashBoxCodeTop);
		add(mTxtCashDrawerOpenCode);
	}

	private void createFiedlCashBoxPort() {
		final int labelCashBoxPortLeft = mTxtCashDrawerOpenCode.getX()
				+ mTxtCashDrawerOpenCode.getWidth() + PANEL_CONTENT_H_GAP;
		// final int
		// labelCashBoxPortTop=mTxtCashDrawerOpenCode.getY()+mTxtCashDrawerOpenCode.getHeight()+PANEL_CONTENT_V_GAP;
		final int labelCashBoxPortTop = mTxtCashDrawerOpenCode.getY();
		final JLabel labelCashBoxPort = new JLabel(
				"<html>Port<font color=#FF0000>*</font> :</html>");
		labelCashBoxPort.setBounds(labelCashBoxPortLeft, labelCashBoxPortTop,
				75, LABEL_HEIGHT);
		setLabelFont(labelCashBoxPort);
		add(labelCashBoxPort);

		final int fieldCashBoxPortLeft = labelCashBoxPort.getX()
				+ labelCashBoxPort.getWidth() + PANEL_CONTENT_H_GAP;
		final int fieldCashBoxPortTop = labelCashBoxPortTop;
		final int fieldCashBoxWidth = getWidth() - fieldCashBoxPortLeft
				- PANEL_CONTENT_H_GAP;

		mTxtCashDrawerPort = new PosTouchableTextField(mParent,
				fieldCashBoxWidth);
		mTxtCashDrawerPort.setTitle("Cash Box Port.");
		mTxtCashDrawerPort.setLocation(fieldCashBoxPortLeft,
				fieldCashBoxPortTop);
		add(mTxtCashDrawerPort);
	}

	private void createFieldCashBoxPorType() {
		final int labelCashBoxPortTypeLeft = PANEL_CONTENT_H_GAP;
		final int labelCashBoxPortTypeTop = mTxtCashDrawerOpenCode.getY()
				+ mTxtCashDrawerOpenCode.getHeight() + PANEL_CONTENT_V_GAP;
		final JLabel labelCashBoxPortType = new JLabel(
				"<html>Port Type<font color=#FF0000>*</font> :</html>");
		labelCashBoxPortType.setBounds(labelCashBoxPortTypeLeft,
				labelCashBoxPortTypeTop, LABEL_L_WIDTH, LABEL_HEIGHT);
		setLabelFont(labelCashBoxPortType);
		add(labelCashBoxPortType);

		final int filedCashBoxPortTypeLeft = labelCashBoxPortType.getX()
				+ labelCashBoxPortType.getWidth() + PANEL_CONTENT_H_GAP;
		final int fieldCashBoxPortTypeTop = labelCashBoxPortType.getY();

		mTxtCashBoxPortType = new PosItemBrowsableField(mParent, 210);
		mTxtCashBoxPortType.setTitle("Port Type.");
		mTxtCashBoxPortType.setBrowseItemList(PortType.values());
		mTxtCashBoxPortType.setLocation(filedCashBoxPortTypeLeft,
				fieldCashBoxPortTypeTop);
		mTxtCashBoxPortType.setListner(new IPosTouchableFieldListner() {

			@Override
			public void onValueSelected(Object value) {
				PortType portType = (PortType) value;
				mSerialPortInfoPanel.setEnabled(mChkHasDevice.isSelected() &&  portType == PortType.SERIAL);
			}

			@Override
			public void onReset() {
				// TODO Auto-generated method stub

			}
		});
		add(mTxtCashBoxPortType);
	}

	private void createSerialPortInfo() {

		final int infoPanelLeft = mTxtCashDrawerPort.getX();
		final int infoPanelTop = mTxtCashBoxPortType.getY();
		mSerialPortInfoPanel = creatSerialPortInfoPanel();
		mSerialPortInfoPanel.setLocation(infoPanelLeft, infoPanelTop);
		mSerialPortInfoPanel.setEnabled(false);
		add(mSerialPortInfoPanel);

	}

	private void createTestButton() {
		final int top = mSerialPortInfoPanel.getY()
				+ mSerialPortInfoPanel.getHeight() - IMAGE_BUTTON_HEIGHT;
		final int left = (mSerialPortInfoPanel.getX() / 2) - IMAGE_BUTTON_WIDTH
				/ 2;

		mBtnTest = new PosButton();
		mBtnTest.setBounds(left, top, IMAGE_BUTTON_WIDTH, IMAGE_BUTTON_HEIGHT);
		mBtnTest.setImage(PosResUtil
				.getImageIconFromResource(IMAGE_BUTTON_RED_NORMAL));
		mBtnTest.setTouchedImage(PosResUtil
				.getImageIconFromResource(IMAGE_BUTTON_RED_TOUCHED));
		mBtnTest.setText("Test It!!!");
		mBtnTest.setOnClickListner(new IPosButtonListner() {
			
			@Override
			public void onClicked(PosButton button) {
				try{
					PosDeviceCashBox.testDevice(buildConfig());
					PosFormUtil.showInformationMessageBox(mParent, "System has tried to open the CashBox. If it did'nt open please check the settings and device");
				}catch(Exception e){
					PosFormUtil.showErrorMessageBox(mParent, "Failed to test the device. Please check the settings and device. ");
					PosLog.write(PosDevCashBoxSettingsTab.this, "Test CashBox", e);
				}
			}
		});
		add(mBtnTest);

	}

	/**
	 * creates the info panel
	 */
	private void createPanelInfo() {

		String infText = "<html>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Please make sure that you are "
				+ "entering the correct port. If the <font color=#FF0000>Cash Box</font> is connected to the "
				+ " <font color=#FF0000>Printer</font> enter the  <font color=#FF0000>Printer  &nbsp;Port</font>. "
				+ "If the  <font color=#FF0000>Cash  &nbsp;Box</font> is connected to the  "
				+ "<font color=#FF0000>Pos  &nbsp;Terminal</font> enter the connected <font color=#FF0000>Terminal &nbsp;Port</font>. "
				+ "After entering the details, test the cash box using the <font color=#FF0000>Test It!!!</font> button. </html>>";

		final int lblInfoHeight = 70;
		setInfoPanel(infText, lblInfoHeight);
	}

	/**
	 * creates the serial port info panel
	 * 
	 * @return
	 */
	private SerialPortInfoPanel creatSerialPortInfoPanel() {
		SerialPortInfoPanel serialInfoPanel = new SerialPortInfoPanel(mParent,
				getWidth() - mTxtCashDrawerPort.getX(),
				mTxtCashDrawerPort.getHeight() * 4 + PANEL_CONTENT_V_GAP * 5);
		return serialInfoPanel;
	}

	/**
	 * load the printer details to the various controls
	 */
	private void setCashBoxDetails() {
		try {

			mCashBoxConfigProvider = new PosDevCashBoxConfigProvider();
			final BeanDevCashBoxConfig config = mCashBoxConfigProvider
					.getCashBoxConfig();

			if (config != null) {
				mTxtCashDrawerOpenCode.setText(config.getCmdOpen());
				mTxtCashDrawerPort.setText(config.getPortInfo().getName());
				mTxtCashBoxPortType.setSelectedItem(config.getPortInfo().getType());

				if (config.getPortInfo().getType() == PortType.SERIAL) {
					mSerialPortInfoPanel.setEnabled(true);
					mSerialPortInfoPanel
							.setPortinfo((BeanSerialPortInfo) config
									.getPortInfo());
				} else {
					mSerialPortInfoPanel.setEnabled(false);
					mSerialPortInfoPanel.reset();
				}
				
				mChkHasDevice.setSelected(mDeviceSettingProvider
						.isDeviceAttached(PosDevices.CASHBOX));
			}

		} catch (Exception err) {
			PosLog.write(this, "setPrinterDetails", err);
			PosFormUtil.showErrorMessageBox(mParent,
					"Error in printer settings. Please contact Administrator.");
		}
	}

	/**
	 * Enables or disable the various controls when selected/deselected the has
	 * device checkbox
	 */
	@Override
	protected void setDeviceEnabled(boolean enable) {
		mTxtCashDrawerOpenCode.setEnabled(enable);
		mTxtCashDrawerPort.setEnabled(enable);
		mTxtCashBoxPortType.setEnabled(enable);
		mSerialPortInfoPanel.setEnabled(enable && (mTxtCashBoxPortType
				.getSelectedValue() == PortType.SERIAL));
		mChkOpenDrawerManually.setEnabled(enable);
		mBtnTest.setEnabled(enable);
	}

	/**
	 * Validate the inputs
	 */
	@Override
	public boolean onValidating() {
		boolean isValid = true;
		String errField = "";

		if (hasDevice()) {
			if (mTxtCashDrawerOpenCode.getText().equals("")) {
				isValid = false;
				errField = "port Open Command";
			} else if (mTxtCashBoxPortType.getSelectedValue() == null) {
				isValid = false;
				errField = "Port Type";
			} else if (mTxtCashDrawerPort.getText().equals("")) {
				isValid = false;
				errField = "Port";
			}
		}

		if (!isValid) {
			PosFormUtil.showErrorMessageBox(mParent, "The " + errField
					+ " is empty!!! Please enter value.");
		} else if (mTxtCashBoxPortType.getSelectedValue() == PortType.SERIAL) {
			isValid = mSerialPortInfoPanel.validateFields();
		}

		return isValid;
	}

	/**
	 * Saves the data to db
	 */
	@Override
	public boolean onSave() {

		try {
			BeanDevCashBoxConfig config=buildConfig();
			mCashBoxConfigProvider.saveSettings(config);
			mDeviceSettingProvider.setDeviceAttached(mPosDevice,
					mChkHasDevice.isSelected());
		} catch (Exception e) {
			PosLog.write(this, "save", e);
			PosFormUtil
					.showErrorMessageBox(mParent,
							"Failed to save the cash box settings. Contact system addmin.");
			return false;
		}
		return true;
	}
	
	private BeanDevCashBoxConfig buildConfig() throws Exception{
		
		BeanDevCashBoxConfig config = new BeanDevCashBoxConfig();
		config.setAllowOpen(mChkOpenDrawerManually.isSelected());
		config.setCmdOpen(mTxtCashDrawerOpenCode.getText());
		final PortType type=(PortType)mTxtCashBoxPortType
				.getSelectedValue();
		
		BeanPortBase portInfo=null;
		switch (type) {
		case PARALLEL:
			portInfo=new BeanParallelPortInfo();
			break;
		case SERIAL:
			portInfo=mSerialPortInfoPanel.getPortInfo();
			break;
		case USB:
			portInfo=new BeanUSBPortInfo();
			break;
		default:
			break;
		}
		portInfo.setName(mTxtCashDrawerPort.getText());
		
		config.setPortInfo(portInfo);
		
		return config;
	}

	@Override
	public void onGotFocus() {
		// mTxtReceiptPrinter.requestFocus();
	}

	/**
	 * The class that creates the serial port info controls
	 * 
	 * @author jojesh
	 * 
	 */
	private class SerialPortInfoPanel extends JPanel {

		private final int LABEL_WIDTH = 110;
		/**
		 * the baud rate filed control
		 */
		private PosTouchableNumberField mTxtPortBaudRate;
		/**
		 * the data bit filed control
		 */
		private PosTouchableNumberField mTxtPortDataBit;
		/**
		 * the parity filed control
		 */
		private PosItemBrowsableField mTxtPortParity;
		/**
		 * the stop bits filed control
		 */
		private PosTouchableNumberField mTxtPortStopBit;
		/**
		 * the layout width
		 */
		private int mWidth;
		/**
		 * the layout height
		 */
		private int mHeight;
		/**
		 * the parent form to which this panel will be added
		 */
		private RootPaneContainer mParent;
		/**
		 * the default text filed width
		 */
		private int mTextWidth;

		/**
		 * The class constructor
		 * 
		 * @param parent
		 * @param width
		 * @param height
		 */
		public SerialPortInfoPanel(RootPaneContainer parent, int width,
				int height) {

			this.mParent = parent;
			this.mWidth = width;
			this.mHeight = height;
			mTextWidth = mWidth - LABEL_WIDTH - PANEL_CONTENT_H_GAP * 3;
			intPanel();
			createControls();
		}

		/**
		 * Initializes the panel
		 */
		private void intPanel() {
			this.setSize(mWidth, mHeight);
			this.setBackground(Color.LIGHT_GRAY);
			this.setBorder(new LineBorder(Color.GRAY));
			this.setOpaque(true);
			this.setLayout(null);
		}

		/**
		 * creates the port info controls
		 */
		private void createControls() {
			setBaudRate(PANEL_CONTENT_V_GAP);
			setDataBits(mTxtPortBaudRate.getY() + mTxtPortBaudRate.getHeight()
					+ PANEL_CONTENT_V_GAP);
			setParity(mTxtPortDataBit.getY() + mTxtPortDataBit.getHeight()
					+ PANEL_CONTENT_V_GAP);
			setStopBits(mTxtPortParity.getY() + mTxtPortParity.getHeight()
					+ PANEL_CONTENT_V_GAP);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see javax.swing.JComponent#setEnabled(boolean)
		 */
		@Override
		public void setEnabled(boolean enabled) {
			mTxtPortBaudRate.setEnabled(enabled);
			mTxtPortDataBit.setEnabled(enabled);
			mTxtPortParity.setEnabled(enabled);
			mTxtPortStopBit.setEnabled(enabled);
			super.setEnabled(enabled);
		}

		/**
		 * creates the baud rate filed
		 */
		private void setBaudRate(int top) {
			int left = PANEL_CONTENT_H_GAP;
			final JLabel label = new JLabel("Baud Rate :");
			label.setBounds(left, top, LABEL_WIDTH, LABEL_HEIGHT);
			setLabelFont(label);
			add(label);

			left = label.getX() + LABEL_WIDTH + PANEL_CONTENT_H_GAP;
			mTxtPortBaudRate = new PosTouchableNumberField(mParent, mTextWidth);
			mTxtPortBaudRate.setTitle("Baud Rate ?");
			mTxtPortBaudRate.setLocation(left, top);
			add(mTxtPortBaudRate);
		}

		/**
		 * creates the data bits filed
		 * 
		 * @param top
		 */
		private void setDataBits(int top) {
			int left = PANEL_CONTENT_H_GAP;
			final JLabel label = new JLabel("Data Bits :");
			label.setBounds(left, top, LABEL_WIDTH, LABEL_HEIGHT);
			setLabelFont(label);
			add(label);

			left = label.getX() + LABEL_WIDTH + PANEL_CONTENT_H_GAP;
			mTxtPortDataBit = new PosTouchableNumberField(mParent, mTextWidth);
			mTxtPortDataBit.setTitle("Data Bits ?");
			mTxtPortDataBit.setLocation(left, top);
			add(mTxtPortDataBit);
		}

		/**
		 * creates the party filed
		 * 
		 * @param top
		 */
		private void setParity(int top) {
			int left = PANEL_CONTENT_H_GAP;

			final JLabel label = new JLabel("Parity :");
			label.setBounds(left, top, LABEL_WIDTH, LABEL_HEIGHT);
			setLabelFont(label);
			add(label);

			left = label.getX() + LABEL_WIDTH + PANEL_CONTENT_H_GAP;
			mTxtPortParity = new PosItemBrowsableField(mParent, mTextWidth);
			mTxtPortParity.setBrowseItemList(PortParity.values());
			mTxtPortParity.setTitle("Parity ?");
			mTxtPortParity.setLocation(left, top);
			mTxtPortParity.setListner(new PosTouchableFieldAdapter() {
				@Override
				public void onValueSelected(Object item) {
					// mselectedPortParity = (PortParity) item;
				}

				@Override
				public void onReset() {
					// mselectedPortParity = null;
				}
			});
			add(mTxtPortParity);
		}

		/**
		 * Creates the stop bits field
		 * 
		 * @param top
		 */
		private void setStopBits(int top) {
			int left = PANEL_CONTENT_H_GAP;
			final JLabel label = new JLabel("Stop Bits :");
			label.setBounds(left, top, LABEL_WIDTH, LABEL_HEIGHT);
			setLabelFont(label);
			add(label);

			left = label.getX() + LABEL_WIDTH + PANEL_CONTENT_H_GAP;
			mTxtPortStopBit = new PosTouchableNumberField(mParent, mTextWidth);
			mTxtPortStopBit.setTitle("Stop Bits ?");
			mTxtPortStopBit.setLocation(left, top);
			add(mTxtPortStopBit);
		}

		/**
		 * Creates and return the serial port info bean
		 * 
		 * @return
		 * @throws Exception
		 */
		public BeanSerialPortInfo getPortInfo() throws Exception {
			BeanSerialPortInfo portInfo = null;
			try {
				portInfo = new BeanSerialPortInfo();
				portInfo.setPortBaudRate(Integer.parseInt(this.mTxtPortBaudRate
						.getSelectedValue()));
				portInfo.setPortDataBits(Integer.parseInt(this.mTxtPortDataBit
						.getSelectedValue()));
				portInfo.setPortParity((PortParity) this.mTxtPortParity
						.getSelectedValue());
				portInfo.setPortStopBits(Integer.parseInt(this.mTxtPortStopBit
						.getSelectedValue()));
			} catch (Exception e) {
				PosLog.write(this, "getPortInfo", e);
				throw e;
			}
			return portInfo;
		}

		/**
		 * set the control value from port info bean
		 * 
		 * @param portInfo
		 */
		public void setPortinfo(BeanSerialPortInfo portInfo) {
			if (portInfo != null) {
				this.mTxtPortBaudRate.setText(String.valueOf(portInfo
						.getPortBaudRate()));
				this.mTxtPortDataBit.setText(String.valueOf(portInfo
						.getPortDataBits()));
				this.mTxtPortParity.setSelectedItem(portInfo.getPortParity());
				this.mTxtPortStopBit.setText(String.valueOf(portInfo
						.getPortStopBits()));
			}
		}

		/**
		 * Reset all the controls
		 */
		public void reset() {
			this.mTxtPortBaudRate.reset();
			this.mTxtPortDataBit.reset();
			this.mTxtPortParity.reset();
			this.mTxtPortStopBit.reset();
		}

		/**
		 * Validates the inputs
		 * 
		 * @return
		 */
		public boolean validateFields() {

			String errField = "";
			boolean isValid = true;
			if (this.mTxtPortBaudRate.getText().equals("")) {
				errField = "Baud Rate";
				isValid = false;
			} else if (this.mTxtPortDataBit.getText().equals("")) {
				errField = "Data Bits";
				isValid = false;
			} else if (this.mTxtPortParity.getText().equals("")) {
				errField = "Parity";
				isValid = false;
			} else if (this.mTxtPortStopBit.getText().equals("")) {
				errField = "Stop Bits";
				isValid = false;
			}

			if (!isValid)
				PosFormUtil.showErrorMessageBox(mParent, "The " + errField
						+ " is empty!!! Please enter " + errField);
			if (isValid) {
				try {
					getPortInfo();
				} catch (Exception e) {
					PosFormUtil
							.showErrorMessageBox(mParent,
									"There is some problem with the port details. Please check the values");
					isValid = false;
				}
			}

			return isValid;
		}
	}

}
