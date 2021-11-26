package com.indocosmo.pos.data.beans.terminal.device;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import javax.swing.KeyStroke;

import com.indocosmo.pos.data.beans.terminal.device.ports.BeanPortBase;
import com.indocosmo.pos.forms.components.objectbrowser.IPosBrowsableItem;

/**
 * @author Jojesh S
 * @since 08th Aug 2012
 **/

public final class BeanDevWMConfig {

	public enum OperationMode implements IPosBrowsableItem {

		AUTO(1, "Auto"), MANUAL(2, "Manual");

		private static final Map<Integer, OperationMode> mLookup = new HashMap<Integer, OperationMode>();

		static {
			for (OperationMode rc : EnumSet.allOf(OperationMode.class))
				mLookup.put(rc.getValue(), rc);
		}

		private int mValue;
		private String mCaption;

		private OperationMode(int value, String caption) {
			this.mValue = value;
			this.mCaption = caption;
		}

		public int getValue() {
			return mValue;
		}

		public String getDisplayText() {
			return mCaption;
		}

		public static OperationMode get(int value) {
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
	
	public static final int VALUE_FETCH_AUTO=0;
	public static final int VALUE_FETCH_MANUAL=0;
	
	private String cmdRequestValue;
	private BeanPortBase mPortInfo;
	private OperationMode operationMode=OperationMode.AUTO;
	
	/**
	 * @return the cmdFetchValue
	 */
	public String getCmdRequestValue() {
		return cmdRequestValue;
	}

	/**
	 * @param cmdRequestValue the cmdRequestValue to set
	 */
	public void setCmdRequestValue(String cmd) {
		this.cmdRequestValue = cmd;
	}

	/**
	 * @return the PortInfo
	 */
	public BeanPortBase getPortInfo() {
		return mPortInfo;
	}

	/**
	 * @param PortInfo the PortInfo to set
	 */
	public void setPortInfo(BeanPortBase portInfo) {
		this.mPortInfo = portInfo;
	}

	/**
	 * @param selected
	 */
	public void setOperationMode(OperationMode operationMode) {
		
		this.operationMode=operationMode;
	}
	
	public OperationMode getOperationMode(){
		
		return operationMode;
	}



}
