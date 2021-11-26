/**
 * 
 */
package com.indocosmo.pos.common.enums;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import javax.swing.KeyStroke;

import com.indocosmo.pos.forms.components.objectbrowser.IPosBrowsableItem;

/**
 * @author sandhya
 *
 */
public enum PosInvoicePrintFormat  implements IPosBrowsableItem{
		
		EIGHTY_MM(1,"80MM"), 
		A4(2,"A4"),
		BOTH(3,"BOTH");

		private static final Map<Integer,PosInvoicePrintFormat> mLookup 
		= new HashMap<Integer,PosInvoicePrintFormat>();

		static {
			for(PosInvoicePrintFormat pt : EnumSet.allOf(PosInvoicePrintFormat.class))
				mLookup.put(pt.getValue(), pt);
		}

		private int mCode;
		private String mTitle;
		private boolean mIsVisible=true;
	   private PosInvoicePrintFormat(int code, String title ) {
			mCode = code;
			mTitle=title;
			 
		}

		public int getValue() { return mCode; }

		public static PosInvoicePrintFormat get(int value) { 
			return mLookup.get(value); 
		}

		/* (non-Javadoc)
		 * @see com.indocosmo.pos.forms.components.objectbrowser.IPosBrowsableItem#getItemCode()
		 */
		@Override
		public Object getItemCode() {
			// TODO Auto-generated method stub
			return mCode;
		}

		/* (non-Javadoc)
		 * @see com.indocosmo.pos.forms.components.objectbrowser.IPosBrowsableItem#getDisplayText()
		 */
		@Override
		public String getDisplayText() {
			// TODO Auto-generated method stub
			return mTitle;
		}

		/* (non-Javadoc)
		 * @see com.indocosmo.pos.forms.components.objectbrowser.IPosBrowsableItem#isVisibleInUI()
		 */
		@Override
		public boolean isVisibleInUI() {
			// TODO Auto-generated method stub
			return mIsVisible;
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
