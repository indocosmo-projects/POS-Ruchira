/**
 * 
 */
package com.indocosmo.pos.terminal.devices;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import javax.swing.KeyStroke;

import com.indocosmo.pos.common.exception.PosException;
import com.indocosmo.pos.data.beans.terminal.device.ports.BeanPortBase;
import com.indocosmo.pos.forms.components.objectbrowser.IPosBrowsableItem;

/**
 * @author jojesh
 * 
 */
public abstract class PosPortDevice extends PosIODevice {
	
	private static final int TIME_OUT=2000;

	public enum PortType implements IPosBrowsableItem {

		SERIAL(1, "Serial"), PARALLEL(2, "Parallel"), USB(3, "USB");

		private static final Map<Integer, PortType> mLookup = new HashMap<Integer, PortType>();

		static {
			for (PortType rc : EnumSet.allOf(PortType.class))
				mLookup.put(rc.getValue(), rc);
		}

		private int mValue;
		private String mCaption;

		private PortType(int value, String caption) {
			this.mValue = value;
			this.mCaption = caption;
		}

		public int getValue() {
			return mValue;
		}

		public String getDisplayText() {
			return mCaption;
		}

		public static PortType get(int value) {
			return mLookup.get(value);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * com.indocosmo.pos.components.objectbrowser.IPosBrowsableItem#getItemCode
		 * ()
		 */
		@Override
		public Object getItemCode() {
			// TODO Auto-generated method stub
			return mValue;
		}
		/* (non-Javadoc)
		 * @see com.indocosmo.pos.forms.components.objectbrowser.IPosBrowsableItem#isVisibleInUI()
		 */
		@Override
		public boolean isVisibleInUI() {
			// TODO Auto-generated method stub
			return true;
		}

		/* (non-Javadoc)
		 * @see com.indocosmo.pos.forms.components.objectbrowser.IPosBrowsableItem#getKeyStroke()
		 */
		@Override
		public KeyStroke getKeyStroke() {
			// TODO Auto-generated method stub
			return null;
		}
	}

	protected CommPortIdentifier mCommPortIdentifier;
	protected CommPort mCommPort;
	protected String mCommPortName;
	protected boolean mIsPortOpend = false;
	protected PortType mPortType;
	protected BeanPortBase mPortInfo;

	/**
	 * 
	 */
	public PosPortDevice() {
	}
	
	/**
	 * @param type
	 */
	public PosPortDevice(PortType type) {
		
		mPortType=type;
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indocosmo.pos.terminal.devices.PosOutputDevice#initialize()
	 */
	@Override
	public boolean initialize() throws Exception {
		
		if(isDeviceInitialized()) return true;
		
		if (!checkPortInfo()) return false;
		
		setSystemParams();
		
		mCommPortName = mPortInfo.getName();
		mPortType = mPortInfo.getType();
		
//		switch (mPortType) {
//		case PARALLEL:
//			System.setProperty("gnu.io.rxtx.ParallelPorts", mCommPortName);
//			break;
//		case SERIAL:
//			System.setProperty("gnu.io.rxtx.SerialPorts", mCommPortName);
//			break;
//		case USB:
//			break;
//		}

		
		mCommPortIdentifier = CommPortIdentifier
				.getPortIdentifier(mCommPortName);
		
		if(mCommPortIdentifier==null)
			throw new PosException("Invalid port!");
		
		if (mCommPortIdentifier.isCurrentlyOwned())
			throw new PosException("Port in use!");
		
		setDeviceInitialized(true);
		
		onInitCompleted();
		
		return true;
	}
	
	/**
	 * @throws Exception
	 */
	protected void onInitCompleted() throws Exception{};
	
	/**
	 * 
	 */
	protected abstract void setSystemParams();
	
	/**
	 * @return
	 * @throws Exception
	 */
	protected boolean checkPortInfo() throws Exception {
		
		if (mPortInfo == null)
			throw new PosException("Port not set.");
		
		if (mPortInfo.getName() == null || mPortInfo.getName().equals(""))
			throw new PosException("Port name not set.");
		
		return true;
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indocosmo.pos.terminal.devices.PosOutputDevice#open()
	 */
	@Override
	public void open() throws Exception {
		
		if (mIsPortOpend)
			return;

		mCommPort=mCommPortIdentifier.open(mCommPortName, TIME_OUT);
		setupPort();
		setOutputStream(mCommPort.getOutputStream());
		setInputStream(mCommPort.getInputStream());
		mIsPortOpend = true;
		
	}

	/**
	 * @param port
	 * @param portinfo
	 * @throws Exception
	 */
	protected abstract void setupPort() throws Exception ;
		
//		switch (portinfo.getType()) {
//		case PARALLEL:
//			break;
//		case SERIAL:
//			SerialPort mSerialPort = (SerialPort) port;
//			BeanSerialPortInfo serialPortInfo = (BeanSerialPortInfo) portinfo;
//			mSerialPort.setSerialPortParams(serialPortInfo.getPortBaudRate(),
//					serialPortInfo.getPortDataBits(), serialPortInfo
//							.getPortStopBits(), serialPortInfo.getPortParity()
//							.getValue());
//			break;
//		case USB:
//			break;
//		default:
//			break;
//
//		}
//	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indocosmo.pos.terminal.devices.PosOutputDevice#close()
	 */
	@Override
	public void close() throws Exception {
		
		if (!mIsPortOpend)
			return;
		
		super.close();
		mCommPort.close();
		mIsPortOpend = false;
		
		return;
	}

	/**
	 * @param portinfo
	 */
	public void setPortinfo(BeanPortBase portinfo) {
		
		this.mPortInfo = portinfo;
	}

	/**
	 * @return
	 */
	public BeanPortBase getPortInfo() {
		
		return mPortInfo;
	}
	
	/**
	 * @return
	 */
	public boolean IsOpen(){
		
		return mIsPortOpend;
	}

}
