package com.indocosmo.pos.data.beans;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import javax.swing.KeyStroke;

import com.indocosmo.pos.forms.components.objectbrowser.IPosBrowsableItem;
 
public abstract class BeanPayModeBase {	
	
	public enum PaymentMode implements IPosBrowsableItem{
		
		Cash(0,"Cash Pay",true),
		Card(1,"Card Pay",true),
		Coupon(2,"Voucher Pay",true),		
		Company(3,"Credit Pay",true),
		Discount(4,"Discount",false),
		Balance(5,"Balance",false),
		CouponBalance(6,"Balance In Coupon",false),
		CashOut(7,"Cash Out",false),
		Repay(8,"Re-Payment",false),
		SplitAdjust(9,"Split Part. Adjustment",false),
		Online(10,"Online",true),
		Cash10(100,"Pay By Cash 10",false),
		Cash20(101,"Pay By Cash 20",false),
		QuickCash(102,"Cash",true),
		QuickCard(103,"Card",true),
		QuickCredit(104,"Credit",true);;
		
//			Partial(1000,"Partial Bill Payment"); --> this is wrong since it is not a payment method

		private static final Map<Integer,PaymentMode> mLookup 
		= new HashMap<Integer,PaymentMode>();

		static {
			for(PaymentMode rc : EnumSet.allOf(PaymentMode.class))
				mLookup.put(rc.getValue(), rc);
		}

		private int mValue;
		private String mTitle;
		private boolean mIsShowInList;
		
		private PaymentMode(int value, String title,boolean isShowInList) {
			this.mValue = value;
			this.mTitle=title;
			this.mIsShowInList=isShowInList;
		}

		public int getValue() { return mValue; }
		
		public String getTitle() {return mTitle;}
		
		public void setTitle(String title) {
			if (title!=null && !title.trim().equals(""))
				this.mTitle=title;
		}
		
		public static PaymentMode get(int value) { 
			return mLookup.get(value); 
		}

		/* (non-Javadoc)
		 * @see com.indocosmo.pos.forms.components.objectbrowser.IPosBrowsableItem#getItemCode()
		 */
		@Override
		public Object getItemCode() {
			// TODO Auto-generated method stub
			return null;
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
			return mIsShowInList;
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

	private Object mPaymentType;

	/**
	 * @return the mPaymentType
	 */
	public final Object getPaymentType() {
		return mPaymentType;
	}

	/**
	 * @param mPaymentType the mPaymentType to set
	 */
	public final void setPaymentType(PaymentMode paymentType) {
		this.mPaymentType = paymentType;
	}
	
	

}
