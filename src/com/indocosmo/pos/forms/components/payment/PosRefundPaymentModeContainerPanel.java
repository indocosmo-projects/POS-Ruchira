package com.indocosmo.pos.forms.components.payment;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;

import com.indocosmo.pos.common.PosLog;
import com.indocosmo.pos.common.utilities.PosFormUtil;
import com.indocosmo.pos.data.beans.BeanOrderHeader;
import com.indocosmo.pos.data.beans.BeanOrderPayment;
import com.indocosmo.pos.data.beans.BeanPayModeBase.PaymentMode;
import com.indocosmo.pos.data.providers.shopdb.PosCouponItemProvider;
import com.indocosmo.pos.data.providers.shopdb.PosOrderPaymentsProvider;
import com.indocosmo.pos.forms.components.listners.IPosCoupenContainerListner;
import com.indocosmo.pos.forms.components.payment.listners.IPosRefundPaymentModeControlListner;

@SuppressWarnings("serial")
public final class PosRefundPaymentModeContainerPanel extends JPanel{
	
	private JPanel mHeaderPanel;
	private JPanel mRefundPaymentPanel;	
	private JLabel mlabelMode;	
	private JLabel mLabelAmount;	
	private JLabel mLabelRefundable;	
	private JLabel mLabelModeRefund;
	private JLabel mLabelAmountRe;
		
	private static final int PANEL_CONTENT_H_GAP=2;
	private static final int PANEL_CONTENT_V_GAP=2;

	private static final int MAX_COUPON_COUNT=7;
	
		
	private static final  int LBL_WIDTH=130;
	private static final  int LBL_HEIGHT=30;
	
	
	private static final Color PANEL_BG_COLOR=new Color(78,128,188);
	
	public static final Color GRID_ITEM_BORDER_COLOR=Color.LIGHT_GRAY;	
	private ArrayList<PosCouponPaymentControl> mPosCouponPaymentControlList;
	private IPosCoupenContainerListner mCoupenContainerListner;
	private ArrayList<BeanOrderPayment> mOrderPaymentList = null;
	private ArrayList<BeanOrderPayment> mRefundList;
	
	private int mLeft, mTop, mWidth,mHeight;

	private JDialog mParent;
	private ArrayList<PosRefundPaymentModeControl> mRefundPaymentControlList;
	private ArrayList<BeanOrderPayment> mOrderBalance;
	private PosCouponItemProvider mCouponItemProvider = new PosCouponItemProvider();
	private double mBalanceInCash = 0;
	private double mBalanceInVoucher = 0;
	
	public PosRefundPaymentModeContainerPanel( JDialog parent,int left, int top, int width,int height,BeanOrderHeader order) {
		mParent=parent;
		mLeft=left;
		mTop=top;
		mWidth=width;
		mHeight=height;
		mRefundList = new ArrayList<BeanOrderPayment>();
		loadOrderPayments(order);
		initComponent();
//		reset();
	}

	
	public PosRefundPaymentModeContainerPanel( JDialog parent,int left, int top,int width,int height) {
		mParent=parent;
		mLeft=left;
		mTop=top;
		mWidth=width;
		mHeight=height;
		mRefundList = new ArrayList<BeanOrderPayment>();
		initComponent();
	}
	/**
	 * @param order 
	 * 
	 */
	private void loadOrderPayments(BeanOrderHeader order) {
		PosOrderPaymentsProvider paymentProvider=new PosOrderPaymentsProvider();
		
		String sqlPayments = "order_id='"+order.getOrderId()+"' and payment_mode in(0,1,2,3)";
		String sqlBalance ="order_id='"+order.getOrderId()+"' and payment_mode in(5,6)";
		try {
			mOrderPaymentList = paymentProvider.getOrderPaymentsByCond(sqlPayments);
			mOrderBalance = paymentProvider.getOrderPaymentsByCond(sqlBalance);
		} catch (Exception e) {
			PosLog.write(this, "loadOrderPayments", e);
			PosFormUtil.showErrorMessageBox(null, "Failed get payment information.");
		} 
		if(mOrderBalance!=null&&!mOrderBalance.isEmpty()){
			if(mOrderBalance.get(0).getPaymentMode().getValue()==PaymentMode.Balance.getValue()){
				mBalanceInCash = mOrderBalance.get(0).getPaidAmount();
			}else{
				mBalanceInVoucher = mOrderBalance.get(0).getPaidAmount();
			}
		}
		
	}

	public void initComponent() {
		int width=mWidth;
		int height=mHeight;
		setBounds(mLeft, mTop, width, height);
		setLayout(null);
		setBorder(new EtchedBorder());
		setBackground(PANEL_BG_COLOR);
		setHeaderDetails();
		createRefundPaymentPanel();
		addRefundPaymentItem();	
	}
	
	private void createRefundPaymentPanel(){
		final int top=mHeaderPanel.getY()+mHeaderPanel.getHeight();
		final int width=mHeaderPanel.getWidth()-PANEL_CONTENT_H_GAP*2;
		final int height=getHeight()-top-PANEL_CONTENT_H_GAP;
		mRefundPaymentPanel=new JPanel();
		mRefundPaymentPanel.setLocation(PANEL_CONTENT_H_GAP,top);
		mRefundPaymentPanel.setSize(width,height);
//		mRefundPaymentPanel.setOpaque(false);
		mRefundPaymentPanel.setLayout(new FlowLayout(FlowLayout.CENTER));		
		add(mRefundPaymentPanel);	
	}	
	
	private void setHeaderDetails(){
		int left=0;
		int top=0;
		
		mHeaderPanel=new JPanel();
		mHeaderPanel.setSize(new Dimension(getWidth(), LBL_HEIGHT));
		mHeaderPanel.setLocation(left,top);
		mHeaderPanel.setLayout(new FlowLayout(FlowLayout.LEFT,1,0));
		mHeaderPanel.setBackground(PANEL_BG_COLOR);
		mHeaderPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
		add(mHeaderPanel);
		
		mlabelMode=new JLabel();
		mlabelMode.setText("         Mode");
		mlabelMode.setHorizontalAlignment(SwingConstants.CENTER);		
		mlabelMode.setPreferredSize(new Dimension(LBL_WIDTH-20, LBL_HEIGHT));		
		mlabelMode.setFont(PosFormUtil.getLabelFont());
		mlabelMode.setFocusable(true);
		mlabelMode.setOpaque(false);
		mHeaderPanel.add(mlabelMode);	
	
		mLabelAmount=new JLabel();
		mLabelAmount.setText("Amount");
		mLabelAmount.setHorizontalAlignment(SwingConstants.CENTER);		
		mLabelAmount.setPreferredSize(new Dimension(LBL_WIDTH-20, LBL_HEIGHT));		
		mLabelAmount.setFont(PosFormUtil.getLabelFont());
		mLabelAmount.setOpaque(false);
		mHeaderPanel.add(mLabelAmount);
		
		mLabelRefundable=new JLabel();
		mLabelRefundable.setText("Refundable?");
		mLabelRefundable.setHorizontalAlignment(SwingConstants.CENTER);		
		mLabelRefundable.setPreferredSize(new Dimension( LBL_WIDTH-20, LBL_HEIGHT));		
		mLabelRefundable.setFont(PosFormUtil.getLabelFont());
		mLabelRefundable.setOpaque(false);
		mHeaderPanel.add(mLabelRefundable);	
		
		mLabelModeRefund=new JLabel();
		mLabelModeRefund.setText("Mode");
		mLabelModeRefund.setHorizontalAlignment(SwingConstants.CENTER);		
		mLabelModeRefund.setPreferredSize(new Dimension( LBL_WIDTH+40, LBL_HEIGHT));		
		mLabelModeRefund.setFont(PosFormUtil.getLabelFont());
		mLabelModeRefund.setOpaque(false);
		mHeaderPanel.add(mLabelModeRefund);	
		
		mLabelAmountRe=new JLabel();
		mLabelAmountRe.setText("Amount");
		mLabelAmountRe.setHorizontalAlignment(SwingConstants.CENTER);		
		mLabelAmountRe.setPreferredSize(new Dimension( LBL_WIDTH+20, LBL_HEIGHT));		
		mLabelAmountRe.setFont(PosFormUtil.getLabelFont());
		mLabelAmountRe.setOpaque(false);
		mHeaderPanel.add(mLabelAmountRe);	
	}		 
	
	
	
	public void addRefundPaymentItem(){		
		int totalItems=MAX_COUPON_COUNT;
		int left=PANEL_CONTENT_H_GAP;
		int top=0;
//		int width=mRefundPaymentPanel.getWidth()-PANEL_CONTENT_H_GAP
		mRefundPaymentControlList =  new ArrayList<PosRefundPaymentModeControl>();
		mRefundPaymentControlList.clear();
		mRefundPaymentPanel.removeAll();
		for(int index=0;index<totalItems;index++){
			top=index*(PosRefundPaymentModeControl.LAYOUT_HEIGHT);
			PosRefundPaymentModeControl refundPaymentModeControl = null;
			
			if(mOrderPaymentList!=null&&index<mOrderPaymentList.size()){
				if(mOrderPaymentList.get(index).getPaymentMode()==PaymentMode.Cash){
					refundPaymentModeControl = new PosRefundPaymentModeControl(mOrderPaymentList.get(index), mParent,mBalanceInCash);
					mBalanceInCash = 0;
				}else if(mOrderPaymentList.get(index).getPaymentMode()==PaymentMode.Coupon && mOrderPaymentList.get(index).getPaidAmount()>mBalanceInVoucher){
					if((mOrderBalance!=null&&mOrderBalance.size()>0)&&mOrderBalance.get(0).isVoucherBalanceReturned()){
						if(mCouponItemProvider.getCouponById(mOrderPaymentList.get(index).getVoucherId()).isChangePayable()){
							refundPaymentModeControl = new PosRefundPaymentModeControl(mOrderPaymentList.get(index), mParent,mBalanceInVoucher);
							mBalanceInVoucher =0;
						}else{
							refundPaymentModeControl = new PosRefundPaymentModeControl(mOrderPaymentList.get(index), mParent,mBalanceInVoucher);
						}
						
					}else{
						refundPaymentModeControl = new PosRefundPaymentModeControl(mOrderPaymentList.get(index), mParent,mBalanceInVoucher);
						mBalanceInVoucher =0;
					}
				}else{
					refundPaymentModeControl = new PosRefundPaymentModeControl(mOrderPaymentList.get(index), mParent,0);
				}
			}
			else
				refundPaymentModeControl = new PosRefundPaymentModeControl(mParent);
			refundPaymentModeControl.setLocation(left, top);	
			refundPaymentModeControl.setListner(refundPaymentModeControlListner);
			refundPaymentModeControl.setIndex(index);
			
			mRefundPaymentControlList.add(refundPaymentModeControl);
			mRefundPaymentPanel.add(refundPaymentModeControl);
			mRefundList.add(refundPaymentModeControl.getRefundItem());
		}
		
		mRefundPaymentPanel.revalidate();
		mRefundPaymentPanel.repaint();
	}	
	
	public ArrayList<BeanOrderPayment> getReFundItems(){
		return mRefundList;
	}
	
	public void setOrder(BeanOrderHeader order){
		mRefundList.clear();
		reset();
		if(order!=null){
			loadOrderPayments(order);
			addRefundPaymentItem();
		}
	}

	private IPosRefundPaymentModeControlListner refundPaymentModeControlListner=new IPosRefundPaymentModeControlListner() {
		@Override
		public void onChange(BeanOrderPayment item, int index) {
			mRefundList.set(index, item);
		}
	};
	
	public boolean validateRefundAmount(){
		boolean valid = true;
		for(PosRefundPaymentModeControl refundCtrl:mRefundPaymentControlList){
			valid = refundCtrl.validateRefundAmount();
			if(!valid)	
				break;
		}
		return valid;
	}
	
	public void reset(){
		for(PosRefundPaymentModeControl refundCtrl:mRefundPaymentControlList)
			refundCtrl.reset();
	}

}
