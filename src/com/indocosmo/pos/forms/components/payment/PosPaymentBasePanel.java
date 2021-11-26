package com.indocosmo.pos.forms.components.payment;

import com.indocosmo.pos.common.PosLog;
import com.indocosmo.pos.common.utilities.PosCurrencyUtil;
import com.indocosmo.pos.common.utilities.PosNumberUtil;
import com.indocosmo.pos.data.beans.BeanPayModeBase.PaymentMode;
import com.indocosmo.pos.forms.PosPaymentForm;
import com.indocosmo.pos.forms.components.payment.listners.IPosPaymentPanelListner;
import com.indocosmo.pos.forms.components.tab.PosTab;

@SuppressWarnings("serial")
public abstract class PosPaymentBasePanel extends PosTab {
	
	private boolean mIsDirty=false;
	private PaymentMode mPaymentMode;
	protected PosPaymentForm mParent;
	
	public PosPaymentBasePanel(PosPaymentForm parent, String caption,PaymentMode mode) {
		super(parent,caption);
		mPaymentMode=mode;
		mParent=parent;
	}
	
	public String getBillTotal(){
		return PosCurrencyUtil.format(mParent.getBillTotalAmount());
	}
	
	private IPosPaymentPanelListner mPaymentListner;
	
	/**
	 * 
	 */
	public abstract double getTenderAmount();
	public abstract void reset();
	
	
	public PaymentMode getPaymentMode(){
		return mPaymentMode;
	}
	/**
	 * @param amount
	 */
	protected void onTenderAmountChanged(String amount){
		try{
			final double tender=((!amount.equals("") && PosNumberUtil.isDouble(amount))?Double.parseDouble(amount):0);
			if(mPaymentListner!=null)
				mPaymentListner.onTenderAmountChanged(tender);
			mIsDirty=(tender!=0);
		}catch(Exception ex){
			PosLog.write(this,"onTenderAmountChanged", ex);
		}
	}
	
	/**
	 * @param listner
	 */
	public void setListner(IPosPaymentPanelListner listner){
		mPaymentListner=listner;
	}
	
	protected void setIsDirty(boolean isdirty){
		mIsDirty=isdirty;
	}
	
	protected boolean getIsDirty(){
		return mIsDirty;
	}
	
	public boolean isUsed(){
		return mIsDirty;
	}
	
	/**
	 * 
	 */
	protected void onValueAccepted(){
		
		if(mPaymentListner!=null)
			mPaymentListner.onAccept();
	}

}
