package com.indocosmo.pos.forms.components.terminal.settings.devices;

import java.awt.Color;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.RootPaneContainer;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import com.indocosmo.pos.common.PosLog;
import com.indocosmo.pos.common.enums.device.PortParity;
import com.indocosmo.pos.common.utilities.PosFormUtil;
import com.indocosmo.pos.common.utilities.PosResUtil;
import com.indocosmo.pos.data.beans.terminal.device.BeanDevWMConfig;
import com.indocosmo.pos.data.beans.terminal.device.BeanDevWMConfig.OperationMode;
import com.indocosmo.pos.data.beans.terminal.device.ports.BeanPortBase;
import com.indocosmo.pos.data.beans.terminal.device.ports.BeanSerialPortInfo;
import com.indocosmo.pos.data.providers.terminaldb.PosDevSettingProvider.PosDevices;
import com.indocosmo.pos.data.providers.terminaldb.PosDevWMConfigProvider;
import com.indocosmo.pos.forms.components.buttons.PosButton;
import com.indocosmo.pos.forms.components.buttons.listners.IPosButtonListner;
import com.indocosmo.pos.forms.components.textfields.PosItemBrowsableField;
import com.indocosmo.pos.forms.components.textfields.PosTouchableFieldBase;
import com.indocosmo.pos.forms.components.textfields.PosTouchableNumberField;
import com.indocosmo.pos.forms.components.textfields.PosTouchableTextField;
import com.indocosmo.pos.forms.components.textfields.listners.IPosTouchableFieldListner;
import com.indocosmo.pos.forms.components.textfields.listners.PosTouchableFieldAdapter;
import com.indocosmo.pos.terminal.devices.PosPortDevice.PortType;
import com.indocosmo.pos.terminal.devices.weighingmachine.PosDeviceWM;

/**
 * @author Ramesh S
 * @since 08th Aug 2012
 **/

@SuppressWarnings("serial")
public final class PosDevWeighingMachineSettingsTab extends PosDeviceSettingsTab {
	

	private static final int LABEL_L_WIDTH = 160;

	private static final int LABEL_HEIGHT = PosTouchableFieldBase.LAYOUT_DEF_HEIGHT;
	private static final int IMAGE_BUTTON_WIDTH = 130;
	private static final int IMAGE_BUTTON_HEIGHT = 87;
	private static final String IMAGE_BUTTON_RED_NORMAL = "main_menu_red.png";
	private static final String IMAGE_BUTTON_RED_TOUCHED = "main_menu_red_touch.png";
	
	protected static final int CHK_BOX_HEIGHT = 25;
	protected static final int CHK_BOX_WIDTH = 300;

	private PosItemBrowsableField mTxtWMPortType;
	private PosTouchableTextField mTxtWMDataRequestCode;
	private PosTouchableTextField mTxtWMPort;
	private PosItemBrowsableField mTxtWMOperationMode;
	private PosButton mBtnTest;

	private SerialPortInfoPanel mSerialPortInfoPanel;
	private PosDevWMConfigProvider mWMConfigProvider;

	public PosDevWeighingMachineSettingsTab(JDialog parent) {
		super(parent, "Weighing", PosDevices.WEIGHINGMACHINE, true, LAYOUT_WIDTH,
				LAYOUT_HEIGHT);
	}

	@Override
	protected void initControls() {
		
		createWMOperationMode();
		createFieldWMDataRequestCode();
		createFiedlWMPort();
		createFieldWPorType();
		createSerialPortInfo();
		createPanelInfo();
		createTestButton();
		setWMDetails();
	}

	private void createWMOperationMode() {

		final int wmTop= mChkHasDevice.getY()+mChkHasDevice.getHeight()+PANEL_CONTENT_V_GAP;
		final int wmLeft  = mChkHasDevice.getX();

		final JLabel labelOperationMode = new JLabel(
				"<html>Oper. Mode<font color=#FF0000>*</font> :</html>");
		labelOperationMode.setBackground(Color.LIGHT_GRAY);
		labelOperationMode.setOpaque(true);
		labelOperationMode.setBorder(new EmptyBorder(2,2,2,2));
		labelOperationMode.setBounds(wmLeft,wmTop,LABEL_L_WIDTH,LABEL_HEIGHT);
		setLabelFont(labelOperationMode);
		add(labelOperationMode);
		
		mTxtWMOperationMode= new PosItemBrowsableField(mParent, 210);
		mTxtWMOperationMode.setTitle("Operation Mode?");
		mTxtWMOperationMode.setMnemonic('M');
		mTxtWMOperationMode.setBrowseItemList(OperationMode.values());
		mTxtWMOperationMode.setFocusable(false);
		mTxtWMOperationMode.setLocation(labelOperationMode.getX()	+labelOperationMode.getWidth()+PANEL_CONTENT_H_GAP, labelOperationMode.getY());
		mTxtWMOperationMode.setEnabled(false);
		mTxtWMOperationMode.setListner(new IPosTouchableFieldListner() {
			
			@Override
			public void onValueSelected(Object value) {

				OperationMode mode=(OperationMode)value;
				mTxtWMDataRequestCode.setEnabled(mode==OperationMode.AUTO);	
				
			}
			
			@Override
			public void onReset() {
				// TODO Auto-generated method stub
				
			}
		});
		add(mTxtWMOperationMode);

	}
	

	private void createFieldWMDataRequestCode() {
		
		final int labelRequestCodeLeft = mChkHasDevice.getX();
		final int labelRequestCodeTop = mTxtWMOperationMode.getY()
				+ mTxtWMOperationMode.getHeight() + PANEL_CONTENT_V_GAP;
		
		final JLabel labelRequestCode = new JLabel(
				"<html>Request Code<font color=#FF0000>*</font> :</html>");
		labelRequestCode.setBounds(labelRequestCodeLeft, labelRequestCodeTop,
				LABEL_L_WIDTH, LABEL_HEIGHT);
		labelRequestCode.setOpaque(true);
		labelRequestCode.setBackground(Color.LIGHT_GRAY);
		setLabelFont(labelRequestCode);
		add(labelRequestCode);

		final int txtRequestCodeLeft = labelRequestCode.getX()
				+ labelRequestCode.getWidth() + PANEL_CONTENT_H_GAP;
		final int txtRequestCodeTop = labelRequestCodeTop;

		mTxtWMDataRequestCode = new PosTouchableTextField(mParent, 210);
		mTxtWMDataRequestCode.setTitle("Weighing Machine Data Request Code.");
		mTxtWMDataRequestCode.setLocation(txtRequestCodeLeft,
				txtRequestCodeTop);
		add(mTxtWMDataRequestCode);
	}

	/**
	 * 
	 */
	private void createFieldWPorType() {
		
		final int labelWMPortTypeLeft = mTxtWMOperationMode.getX()+mTxtWMOperationMode.getWidth()+PANEL_CONTENT_H_GAP;
		final int labelWMPortTypeTop = mTxtWMOperationMode.getY();
		
		final JLabel labelWMPortType = new JLabel(
				"<html>Port Type<font color=#FF0000>*</font> :</html>");
		labelWMPortType.setBounds(labelWMPortTypeLeft,
				labelWMPortTypeTop, LABEL_L_WIDTH, LABEL_HEIGHT);
		setLabelFont(labelWMPortType);
		labelWMPortType.setOpaque(true);
		labelWMPortType.setBackground(Color.LIGHT_GRAY);
		add(labelWMPortType);

		final int filedWMPortTypeLeft = labelWMPortType.getX()
				+ labelWMPortType.getWidth() + PANEL_CONTENT_H_GAP;
		final int fieldWMPortTypeTop = labelWMPortType.getY();

		mTxtWMPortType = new PosItemBrowsableField(mParent, 240);
		mTxtWMPortType.setTitle("Port Type.");
		mTxtWMPortType.setBrowseItemList(PortType.values());
		mTxtWMPortType.setLocation(filedWMPortTypeLeft,
				fieldWMPortTypeTop);
		mTxtWMPortType.setEnabled(false);
		mTxtWMPortType.setListner(new IPosTouchableFieldListner() {

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
		add(mTxtWMPortType);
	}

	/**
	 * 
	 */
	private void createFiedlWMPort() {
		
		final int labelWMPortLeft = mTxtWMDataRequestCode.getX()
				+ mTxtWMDataRequestCode.getWidth() + PANEL_CONTENT_H_GAP;
		final int labelWMPortTop = mTxtWMDataRequestCode.getY();
		
		final JLabel labelWPort = new JLabel(
				"<html>Port<font color=#FF0000>*</font> :</html>");
		labelWPort.setBounds(labelWMPortLeft, labelWMPortTop,75, LABEL_HEIGHT);
		setLabelFont(labelWPort);
		labelWPort.setOpaque(true);
		labelWPort.setBackground(Color.LIGHT_GRAY);
		add(labelWPort);

		final int fieldWMPortLeft = labelWPort.getX()
				+ labelWPort.getWidth() + PANEL_CONTENT_H_GAP;
		final int fieldWMPortTop = labelWMPortTop;
		final int fieldWMWidth = getWidth() - fieldWMPortLeft
				- PANEL_CONTENT_H_GAP;

		mTxtWMPort = new PosTouchableTextField(mParent,
				fieldWMWidth);
		mTxtWMPort.setTitle("Weighing Machine Port.");
		mTxtWMPort.setLocation(fieldWMPortLeft,
				fieldWMPortTop);
		add(mTxtWMPort);
	}

	
	/**
	 * 
	 */
	private void createSerialPortInfo() {

		final int infoPanelLeft = mTxtWMDataRequestCode.getX()+mTxtWMDataRequestCode.getWidth()+PANEL_CONTENT_H_GAP;
		final int infoPanelTop = mTxtWMPort.getY()+mTxtWMPort.getHeight()+PANEL_CONTENT_V_GAP;
		mSerialPortInfoPanel = creatSerialPortInfoPanel();
		mSerialPortInfoPanel.setLocation(infoPanelLeft, infoPanelTop);
		mSerialPortInfoPanel.setEnabled(false);
		add(mSerialPortInfoPanel);

	}

	/**
	 * 
	 */
	private void createTestButton() {
		final int top = mSerialPortInfoPanel.getY() 
				+ mSerialPortInfoPanel.getHeight()/2 - IMAGE_BUTTON_HEIGHT/2;
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

					String testResult=PosDeviceWM.testDevice(buildConfig());
					PosFormUtil.showInformationMessageBox(mParent, "Test Result :-> " + testResult +"");
					
				}catch(Exception e){
					
					PosFormUtil.showErrorMessageBox(mParent, "Failed to test the device. Please check the settings and device. ");
					PosLog.write(PosDevWeighingMachineSettingsTab.this, "Test WM", e);
				}
			}
		});
		add(mBtnTest);
		
	}

	/**
	 * creates the info panel
	 */
	private void createPanelInfo() {

//		String infText = "<html>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Please make sure that you are "
//				+ "entering the correct port. If the <font color=#FF0000>Cash Box</font> is connected to the "
//				+ " <font color=#FF0000>Printer</font> enter the  <font color=#FF0000>Printer  &nbsp;Port</font>. "
//				+ "If the  <font color=#FF0000>Cash  &nbsp;Box</font> is connected to the  "
//				+ "<font color=#FF0000>Pos  &nbsp;Terminal</font> enter the connected <font color=#FF0000>Terminal &nbsp;Port</font>. "
//				+ "After entering the details, test the cash box using the <font color=#FF0000>Test It!!!</font> button. </html>>";

		final int lblInfoHeight = 70;
		setInfoPanel("", lblInfoHeight);
	}

	/**
	 * creates the serial port info panel
	 * 
	 * @return
	 */
	private SerialPortInfoPanel creatSerialPortInfoPanel() {
		
		SerialPortInfoPanel serialInfoPanel = new SerialPortInfoPanel(mParent,
				getWidth() - (mTxtWMDataRequestCode.getX() + mTxtWMDataRequestCode.getWidth()+ PANEL_CONTENT_H_GAP),
				mTxtWMPort.getHeight() * 4 + PANEL_CONTENT_V_GAP * 5);
		return serialInfoPanel;
	}

	/**
	 * load the printer details to the various controls
	 */
	private void setWMDetails() {
		try {

			mWMConfigProvider = new PosDevWMConfigProvider();
			final BeanDevWMConfig config = mWMConfigProvider
					.getWMConfig();

			if (config != null) {
				
				mTxtWMOperationMode.setSelectedItem(config.getOperationMode());
				mTxtWMDataRequestCode.setText(config.getCmdRequestValue());
				mTxtWMPort.setText(config.getPortInfo().getName());
				mTxtWMPortType.setSelectedItem(config.getPortInfo().getType());

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
						.isDeviceAttached(PosDevices.WEIGHINGMACHINE));
			}

		} catch (Exception err) {
			
			PosLog.write(this, "setWMDetails", err);
			PosFormUtil.showErrorMessageBox(mParent,
					"Error in  settings. Please contact Administrator.");
		}
	}

	/**
	 * Enables or disable the various controls when selected/deselected the has
	 * device checkbox
	 */
	@Override
	protected void setDeviceEnabled(boolean enable) {
		
		mTxtWMDataRequestCode.setEnabled(enable && ((OperationMode)mTxtWMOperationMode.getSelectedValue())==OperationMode.AUTO);
		mTxtWMPort.setEnabled(enable);
//		mTxtWMPortType.setEnabled(enable);
		mSerialPortInfoPanel.setEnabled(enable && (mTxtWMPortType
				.getSelectedValue() == PortType.SERIAL));
//		mTxtWMOperationMode.setEnabled(enable);
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
			if (mTxtWMDataRequestCode.getText().equals("")) {
				isValid = false;
				errField = "port Open Command";
			} else if (mTxtWMPortType.getSelectedValue() == null) {
				isValid = false;
				errField = "Port Type";
			} else if (mTxtWMPort.getText().equals("")) {
				isValid = false;
				errField = "Port";
			}
		}

		if (!isValid) {
			PosFormUtil.showErrorMessageBox(mParent, "The " + errField
					+ " is empty!!! Please enter value.");
		} else if (mTxtWMPortType.getSelectedValue() == PortType.SERIAL) {
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
			final BeanDevWMConfig config=buildConfig();
			mWMConfigProvider.saveSettings(config);
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
	
	/**
	 * @return
	 * @throws Exception
	 */
	private BeanDevWMConfig buildConfig() throws Exception{

		final BeanDevWMConfig config = new BeanDevWMConfig();
		config.setOperationMode((OperationMode)mTxtWMOperationMode.getSelectedValue());
		config.setCmdRequestValue(mTxtWMDataRequestCode.getText());

//		final PortType type=(PortType)mTxtWMPortType
//				.getSelectedValue();

		BeanPortBase portInfo=null;
		
//		switch (type) {
//		case PARALLEL:
//			portInfo=new BeanParallelPortInfo();
//			break;
//		case SERIAL:
			portInfo=mSerialPortInfoPanel.getPortInfo();
//			break;
//		case USB:
//			portInfo=new BeanUSBPortInfo();
//			break;
//		default:
//			break;
//		}
		
		portInfo.setName(mTxtWMPort.getText());
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
