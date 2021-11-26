package com.indocosmo.pos.forms.components.payment;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import com.indocosmo.pos.common.utilities.PosCurrencyUtil;
import com.indocosmo.pos.common.utilities.PosFormUtil;
import com.indocosmo.pos.data.beans.BeanCoupon;
import com.indocosmo.pos.data.beans.BeanOrderHeader;
import com.indocosmo.pos.data.beans.BeanOrderPayment;
import com.indocosmo.pos.data.beans.BeanPayModeBase.PaymentMode;
import com.indocosmo.pos.forms.PosOrderEntryForm;
import com.indocosmo.pos.forms.PosPaymentForm;
import com.indocosmo.pos.forms.components.listners.IPosCoupenContainerListner;


@SuppressWarnings("serial")
public final class PosCouponPaymentPanel extends PosPaymentBasePanel{		
	
	private static final int PANEL_CONTENT_H_GAP=PosOrderEntryForm.PANEL_CONTENT_H_GAP;
	private static final int PANEL_CONTENT_V_GAP=PosOrderEntryForm.PANEL_CONTENT_V_GAP;	
	
	public static final int COUPON_GRANT_TOTAL_AMT_FIELD_WIDTH=180;
	public static final int COUPON_GRANT_TOTAL_AMT_FIELD_HEIGHT=40;

	private PosCouponsContainerPanel mCouponPayItemsContainer;
	
	private JLabel mLabelCouponAmt;
	private JTextField mTxtCouponAmtvalue;
	private double mVoucherBalance;
	private boolean isVoucherBalanceReturned = false;
	private JPanel mDetailPanel;
	
	public PosCouponPaymentPanel(PosPaymentForm parent, int width, int height) {
		super(parent,PaymentMode.Coupon.getDisplayText(),PaymentMode.Coupon);
//		setKeyStroke(KeyStroke.getKeyStroke(KeyEvent.VK_F11, 0));
		setMnemonicChar('V');
		setBounds(0,0,width,height);
		setLayout(null);
		setCouponDetails();
		setGrantTotalAmt();
		setGrantTotalAmtValue();
	}	
	
	private void setCouponDetails(){		
		
		mDetailPanel=new JPanel();
		int width=PosCouponsContainerPanel.LAYOUT_WIDTH +PANEL_CONTENT_H_GAP*2 ;
		
		int height=PosCouponsContainerPanel.LAYOUT_HEIGHT +COUPON_GRANT_TOTAL_AMT_FIELD_HEIGHT + PANEL_CONTENT_V_GAP * 4;
		int left=(getWidth()- width )/2 ;		
		int top=0;
	
		 
		mDetailPanel.setSize(width, height);
		mDetailPanel.setBounds(left, top, width, height);
		mDetailPanel.setLayout(null);		
		add(mDetailPanel);
		
		
		
		mCouponPayItemsContainer=new PosCouponsContainerPanel(mParent,0,10);
		mCouponPayItemsContainer.setListner(mCoupenContainerListner); 
		mDetailPanel.add(mCouponPayItemsContainer);
	}
	
	private  void setGrantTotalAmt(){				
		int left=PANEL_CONTENT_H_GAP;
		int top=mCouponPayItemsContainer.getHeight()+PANEL_CONTENT_V_GAP+7;
		mLabelCouponAmt=new JLabel("Total Amount :");
		mLabelCouponAmt.setBorder(new EmptyBorder(2, 2, 2, 2));
		mLabelCouponAmt.setOpaque(true);
		mLabelCouponAmt.setBackground(Color.LIGHT_GRAY);
		mLabelCouponAmt.setHorizontalAlignment(SwingConstants.LEFT);		
		mLabelCouponAmt.setBounds(left+366, top,173,COUPON_GRANT_TOTAL_AMT_FIELD_HEIGHT+7);		
		mLabelCouponAmt.setFont(PosFormUtil.getTextFieldFont());
//		mLabelCouponAmt.setForeground(Color.RED);
		mDetailPanel.add(mLabelCouponAmt);	
	}	
	
	private  void setGrantTotalAmtValue(){				
		int left=mLabelCouponAmt.getX()+mLabelCouponAmt.getWidth()+5;
		int top=mLabelCouponAmt.getY();
		mTxtCouponAmtvalue=new JTextField(PosCurrencyUtil.format(0));		
		mTxtCouponAmtvalue.setHorizontalAlignment(SwingConstants.RIGHT);		
		mTxtCouponAmtvalue.setBounds(left, top,COUPON_GRANT_TOTAL_AMT_FIELD_WIDTH-30,COUPON_GRANT_TOTAL_AMT_FIELD_HEIGHT+7);		
		mTxtCouponAmtvalue.setFont(PosFormUtil.getTextFieldFont().deriveFont(Font.BOLD));
		mTxtCouponAmtvalue.setEditable(false);
		mTxtCouponAmtvalue.setForeground(Color.RED);
		mDetailPanel.add(mTxtCouponAmtvalue);	
	}	
	
	private void updateChangeTextField(){
		double couponAmt=mCouponPayItemsContainer.getCouponTotalAmount();
		mTxtCouponAmtvalue.setText(PosCurrencyUtil.format(couponAmt));
		onTenderAmountChanged(String.valueOf(couponAmt));
	}
	
	private IPosCoupenContainerListner mCoupenContainerListner=new IPosCoupenContainerListner() {
		
		@Override
		public void onCouponChanged() {
			updateChangeTextField();
			
		}
	};	
	
	/**
	 * @return the mVoucherBalance
	 */
	public double getVoucherBalance() {
		return mVoucherBalance;
	}
	
	public boolean isVoucherBalanceReturned(){
		
		return isVoucherBalanceReturned;
	}
	public ArrayList<BeanCoupon> getCoupons() {
		return mCouponPayItemsContainer.getCoupons();
	}
//
//	public float getChangeAmount(){
//		String amount=mTxtChange.getText();
//		return (amount.equals(""))?0f:Float.parseFloat(amount);
//	}
	
//	public float getTenderedAmount(){
//		String amount=mTxtTendered.getText();
//		return (amount.equals(""))?0f:Float.parseFloat(amount);
//	}


	@Override
	public boolean onValidating() {
		return true;
	}

	@Override
	public double getTenderAmount() {
		double amount=(!mTxtCouponAmtvalue.getText().equals(""))?Double.parseDouble(mTxtCouponAmtvalue.getText()):0;
		return	amount;
	}

	@Override
	public void reset() {
		mCouponPayItemsContainer.reset();
		mTxtCouponAmtvalue.setText(PosCurrencyUtil.format(0));
//		mParent.mCantApplyDiscount=false;
	}

	@Override
	public void onGotFocus() {
		mParent.getCardPaymentPanel().reset();
//		if(mParent.getBalanceAmount()<0){
//			mParent.setRoundingAdjustment(0);
//		}
		// TODO Auto-generated method stub
		
		if(mParent.getBalanceAmount()<0){
			 
			double roundedAmount=0;
			double roundingAdjustment ;
			if (PosFormUtil.canRound(PaymentMode.Coupon)) {
			
				roundedAmount = PosCurrencyUtil.nRound(mParent.getBalanceAmount()+mParent.getRoundingAdjustment());
				roundingAdjustment = roundedAmount - (mParent.getBalanceAmount()+mParent.getRoundingAdjustment());
			}else{
				roundedAmount = mParent.getBalanceAmount()-(-1 * mParent.getRoundingAdjustment());
		
				roundingAdjustment =0;
			}
			mParent.setRoundingAdjustment((roundingAdjustment==0)?0:-roundingAdjustment);
		}
		
	}

	public ArrayList<BeanOrderPayment> getPayment(
			BeanOrderHeader orderHeader) {
		ArrayList<BeanOrderPayment> posOrderCouponPaymentItems = new ArrayList<BeanOrderPayment>();
		PosCouponPaymentPanel posCouponPayment = this;
		isVoucherBalanceReturned = false;
		for (BeanCoupon coupon : posCouponPayment.getCoupons()) {
			if (coupon != null //&& coupon.getId() != null
					&& coupon.getCount() > 0) {
				if(coupon.isChangePayable()){
					isVoucherBalanceReturned = true;
				}
				BeanOrderPayment orderPayment = new BeanOrderPayment();
				orderPayment.setOrderId(orderHeader.getOrderId());
				orderPayment.setPaymentMode(PaymentMode.Coupon);
				orderPayment.setVoucherId(coupon.getId());
				orderPayment.setVoucherName(coupon.getName());
				orderPayment.setVoucherValue(coupon.getValue());
				orderPayment.setVoucherCount(coupon.getCount());
				orderPayment.setPaidAmount(coupon.getValue()
						* coupon.getCount());
				posOrderCouponPaymentItems.add(orderPayment);
			}
		}
		return posOrderCouponPaymentItems;
	}
}
