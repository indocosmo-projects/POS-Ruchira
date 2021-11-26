package com.indocosmo.pos.forms.components.terminal.settings.devices;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;

import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;

import com.indocosmo.pos.common.utilities.PosFormUtil;
import com.indocosmo.pos.data.providers.terminaldb.PosDevSettingProvider;
import com.indocosmo.pos.data.providers.terminaldb.PosDevSettingProvider.PosDevices;
import com.indocosmo.pos.forms.components.terminal.settings.PosTermnalSettingsBase;

@SuppressWarnings("serial")
public abstract class PosDeviceSettingsTab extends PosTermnalSettingsBase {

	private static final int CHK_WIDTH = 200;
	private static final int CHK_HEIGHT = 25;

	protected JCheckBox mChkHasDevice;
	protected String mDeviceName;
	protected PosDevSettingProvider mDeviceSettingProvider;

	private int mHeight;
	private int mWidth;
	protected PosDevices mPosDevice;
	private boolean mShowDeviceCheckBox = true;
	protected JDialog mParent;

	public PosDeviceSettingsTab(JDialog parent, String deviceName,
			PosDevices device, boolean showDeviceChkBox, int width, int height) {
		super(parent,deviceName);
		mParent = parent;
		mHeight = height;
		mWidth = width;
		mDeviceName = deviceName;
		mPosDevice = device;
		mShowDeviceCheckBox = showDeviceChkBox;
		mDeviceSettingProvider = new PosDevSettingProvider();
		initialize();
	}

	private void initialize() {
		setLayout(null);
		setSize(mWidth, mHeight);
		setPreferredSize(new Dimension(mWidth, mHeight));
		if (mShowDeviceCheckBox)
			setCheckBox();
		initControls();
		if (mShowDeviceCheckBox) {
			mChkHasDevice.setSelected(mDeviceSettingProvider
					.isDeviceAttached(mPosDevice));
		}
	}

	protected abstract void initControls();

	protected abstract void setDeviceEnabled(boolean enable);

	protected void setLabelFont(JLabel label) {
//		Font newLabelFont = new Font(label.getFont().getName(), label.getFont()
//				.getStyle(), 16);
//		label.setFont(newLabelFont);
		label.setFont(PosFormUtil.getLabelFont());
	}

	private void setCheckBox() {

		int left = 0;
		int top = 0;
		mChkHasDevice = new JCheckBox("Has " + mDeviceName);
		mChkHasDevice.setMnemonic(KeyEvent.VK_C);
		mChkHasDevice.setSelected(true);
		mChkHasDevice.setBounds(left, top, getWidth(), CHK_HEIGHT);
		mChkHasDevice.setBackground(PosFormUtil.SECTION_HEADING_PANEL_BG_COLOR);
		mChkHasDevice.setForeground(PosFormUtil.SECTION_HEADING_PANEL_FG_COLOR);
		mChkHasDevice.setLayout(null);
		PosFormUtil.setCheckboxDefaultFont(mChkHasDevice);
		mChkHasDevice.addItemListener(mHasDeviceSelectedListner);
		add(mChkHasDevice);
	}

	protected boolean hasDevice() {
		return mChkHasDevice.isSelected();
	}

	protected ItemListener mHasDeviceSelectedListner = new ItemListener() {
		public void itemStateChanged(ItemEvent itemEvent) {
			int state = itemEvent.getStateChange();
			if (state == ItemEvent.SELECTED)
				setDeviceEnabled(true);
			else
				setDeviceEnabled(false);
		}
	};

}
