package com.indocosmo.pos.data.beans;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import javax.swing.KeyStroke;

public class BeanReason extends BeanMasterBase {
	
	public enum ReasonContext{
	     
		DISCOUNT(1),
		REFUND(2);

		private static final Map<Integer,ReasonContext> mLookup 
		= new HashMap<Integer,ReasonContext>();

		static {
			for(ReasonContext rc : EnumSet.allOf(ReasonContext.class))
				mLookup.put(rc.getValue(), rc);
		}

		private int mValue;

		private ReasonContext(int value) {
			this.mValue = value;
		}

		public int getValue() { return mValue; }

		public static ReasonContext get(int value) { 
			return mLookup.get(value); 
		}
	}

//	private int mId;					
//	private String mCode;
//	private String mName;	
//	private String mDescription;					
	private ReasonContext mContext;
	
//	@Override
//	public String getDisplayText() {
//		
//		return mName;
//	}
//
//	public int getId() {
//		return mId;
//	}
//
//	public void setId(int id) {
//		this.mId = id;
//	}
//
//	public String getCode() {
//		return mCode;
//	}
//
//	public void setCode(String code) {
//		this.mCode = code;
//	}
//	
//	public String getName() {
//		return mName;
//	}
//
//	public void setName(String name) {
//		this.mName = name;
//	}

	public ReasonContext getContext() {
		return mContext;
	}

	public void setContext(ReasonContext context) {
		this.mContext = context;
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.pos.forms.components.objectbrowser.IPosBrowsableItem#getKeyStroke()
	 */
	@Override
	public KeyStroke getKeyStroke() {
		// TODO Auto-generated method stub
		return null;
	}

//	public String getDescription() {
//		return mDescription;
//	}
//
//	public void setDescription(String description) {
//		this.mDescription = description;
//	}

}
