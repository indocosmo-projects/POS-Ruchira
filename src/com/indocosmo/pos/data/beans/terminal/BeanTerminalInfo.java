/**
 * 
 */
package com.indocosmo.pos.data.beans.terminal;

import javax.swing.KeyStroke;

import com.indocosmo.pos.common.enums.PosTerminalServiceType;
import com.indocosmo.pos.common.enums.PosTerminalOperationalMode;
import com.indocosmo.pos.data.beans.BeanMasterBase;
import com.indocosmo.pos.forms.components.objectbrowser.IPosBrowsableItem;

/**
 * @author jojesh
 *
 */
public class BeanTerminalInfo extends BeanMasterBase implements IPosBrowsableItem{

//	public enum  PosTerminalServiceType implements IPosBrowsableItem{
//
//		Counter(1,"Counter"),
//		Restaurant(2,"Restaurant Counter");
////		KOTStation(3,"KOT Station");
//
//		private static final Map<Integer, PosTerminalServiceType> mLookup 
//		= new HashMap<Integer, PosTerminalServiceType>();
//
//		static {
//			for( PosTerminalServiceType rc : EnumSet.allOf( PosTerminalServiceType.class))
//				mLookup.put(rc.getValue(), rc);
//		}
//
//		private int mValue;
//		private String mCaption;
//
//		private  PosTerminalServiceType(int value, String caption) {
//			this.mValue = value;
//			this.mCaption=caption;
//		}
//
//		public int getValue() { return mValue; }
//		public String getDisplayText() { return mCaption; }
//
//		public static  PosTerminalServiceType get(int value) { 
//			return mLookup.get(value); 
//		}
//
//		/* (non-Javadoc)
//		 * @see com.indocosmo.pos.components.objectbrowser.IPosBrowsableItem#getItemCode()
//		 */
//		@Override
//		public Object getItemCode() {
//			return getValue();
//		}
//		
//		/* (non-Javadoc)
//		 * @see com.indocosmo.pos.forms.components.objectbrowser.IPosBrowsableItem#isVisibleInUI()
//		 */
//		@Override
//		public boolean isVisibleInUI() {
//			// TODO Auto-generated method stub
//			return true;
//		}
//	}
	
	private PosTerminalServiceType mServiceType;
	private PosTerminalOperationalMode mType; 

	@Override
	public String getDisplayText() {
		return getName();
	}
	
	/**
	 * @return the Type
	 */
	public PosTerminalServiceType getServiceType() {
		return mServiceType;
	}
	
	/**
	 * @param Type to set
	 */
	public void setServiceType(PosTerminalServiceType type) {
		this.mServiceType = type;
	}

	/**
	 * @return the type
	 */
	public PosTerminalOperationalMode getOperationalMode() {
		return mType;
	}

	/**
	 * @param type the type to set
	 */
	public void setOperationalMode(PosTerminalOperationalMode type) {
		this.mType = type;
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
