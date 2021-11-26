package com.indocosmo.pos.forms.components.payment;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import com.indocosmo.pos.common.utilities.PosFormUtil;
import com.indocosmo.pos.data.beans.BeanCoupon;
import com.indocosmo.pos.data.providers.shopdb.PosCouponItemProvider;
import com.indocosmo.pos.forms.PosOrderEntryForm;
import com.indocosmo.pos.forms.components.listners.IPosCoupenContainerListner;
import com.indocosmo.pos.forms.components.payment.listners.IPosCouponPaymentControlListner;

@SuppressWarnings("serial")
public final class PosCouponsContainerPanel extends JPanel{
	
	private JPanel mHeaderPanel;
	private JPanel mCouponPayItemListPanel;	
	private JLabel mlabelCoupon;	
	private JLabel mLabelCouponAmt;	
	private JLabel mLabelCouponCount;	
	private JLabel mLabelTotal;
		
	private static final int PANEL_CONTENT_H_GAP=PosOrderEntryForm.PANEL_CONTENT_H_GAP;
	private static final int PANEL_CONTENT_V_GAP=5;

	private static final int MAX_COUPON_COUNT=5;
	
	private static final  int COUPON_SELECT_CTRL_PANAL_WIDTH=PosCouponPaymentControl.LAYOUT_WIDTH+PANEL_CONTENT_H_GAP*2;
	private static final  int COUPON_SELECT_CTRL_PANAL_HEIGHT=(PosCouponPaymentControl.LAYOUT_HEIGHT*MAX_COUPON_COUNT)+PANEL_CONTENT_V_GAP*(MAX_COUPON_COUNT+1);
		
	private static final  int HEADER_PANAL_WIDTH=COUPON_SELECT_CTRL_PANAL_WIDTH;
	private static final  int HEADER_PANAL_HEIGHT=30;
	
	public static int LAYOUT_WIDTH=COUPON_SELECT_CTRL_PANAL_WIDTH+PANEL_CONTENT_H_GAP*2;
	public static int LAYOUT_HEIGHT=COUPON_SELECT_CTRL_PANAL_HEIGHT+HEADER_PANAL_HEIGHT;
	
	private static final Color PANEL_BG_COLOR=new Color(78,128,188);
	
	public static final Color GRID_ITEM_BORDER_COLOR=Color.LIGHT_GRAY;	
	private ArrayList<BeanCoupon> mCouponItems;	
	private ArrayList<PosCouponPaymentControl> mPosCouponPaymentControlList;
	private ArrayList<BeanCoupon> mCouponlist;
	private IPosCoupenContainerListner mCoupenContainerListner;
	
	private int mLeft, mTop;

	private JDialog mParent;
	
	public PosCouponsContainerPanel( JDialog parent,int left, int top) {
		mParent=parent;
		mLeft=left;
		mTop=top;
		mCouponItems=new ArrayList<BeanCoupon>();
		mCouponlist= new PosCouponItemProvider().getCouponBrowseItemList();
//		PosCouponItemProvider couponItemProvider=new PosCouponItemProvider();
//		mCouponlist=couponItemProvider.getCouponBrowseItemList();
		initComponent();
	}

	public void initComponent() {
		int width=LAYOUT_WIDTH;
		int height=LAYOUT_HEIGHT;
		setBounds(mLeft, mTop, width, height);
//		setBackground(Color.YELLOW);
//		setBorder(new EtchedBorder(EtchedBorder.LOWERED));
		setLayout(null);
		setHeaderDetails();
		createCouponnPaymentPanels();
		addCouponPaymentItem();	
//		setGrantTotalAmt();
	}
	
	private void createCouponnPaymentPanels(){
		final int top=mHeaderPanel.getY()+mHeaderPanel.getHeight();
		mCouponPayItemListPanel=new JPanel();
		mCouponPayItemListPanel.setLocation(PANEL_CONTENT_H_GAP,top);
		mCouponPayItemListPanel.setSize(COUPON_SELECT_CTRL_PANAL_WIDTH,COUPON_SELECT_CTRL_PANAL_HEIGHT);
//		mCouponPayItemListPanel.setBackground(Color.RED);
		mCouponPayItemListPanel.setLayout(new FlowLayout(FlowLayout.CENTER,0,2));		
		add(mCouponPayItemListPanel);	
	}	
	
	private void setHeaderDetails(){
		int left=PANEL_CONTENT_H_GAP;
		int top=0;
		
		mHeaderPanel=new JPanel();
		mHeaderPanel.setSize(new Dimension(HEADER_PANAL_WIDTH, HEADER_PANAL_HEIGHT));
		mHeaderPanel.setLocation(left,top);
		mHeaderPanel.setLayout(new FlowLayout(FlowLayout.LEFT,1,0));
//		mHeaderPanel.setBackground(PANEL_BG_COLOR);
		mHeaderPanel.setOpaque(false);
//		mHeaderPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
		add(mHeaderPanel);
		
		mlabelCoupon=new JLabel();
		mlabelCoupon.setText("Voucher");
		mlabelCoupon.setHorizontalAlignment(SwingConstants.CENTER);		
		mlabelCoupon.setPreferredSize(new Dimension(PosCouponPaymentControl.COUPON_SELECT_FIELD_WIDTH+10, HEADER_PANAL_HEIGHT));		
		mlabelCoupon.setFont(PosFormUtil.getLabelFont());
		mlabelCoupon.setFocusable(true);
		mlabelCoupon.setOpaque(true);
		mlabelCoupon.setBackground(Color.LIGHT_GRAY);
		mHeaderPanel.add(mlabelCoupon);	
		
//		left=mlabelCoupon.getX()+mlabelCoupon.getWidth()+PANEL_CONTENT_H_GAP;
	
		mLabelCouponAmt=new JLabel();
		mLabelCouponAmt.setText("Value");
		mLabelCouponAmt.setHorizontalAlignment(SwingConstants.CENTER);		
		mLabelCouponAmt.setPreferredSize(new Dimension(PosCouponPaymentControl.COUPON_VALUE_FIELD_WIDTH+2,HEADER_PANAL_HEIGHT));		
		mLabelCouponAmt.setFont(PosFormUtil.getLabelFont());
		mLabelCouponAmt.setOpaque(true);
		mLabelCouponAmt.setBackground(Color.LIGHT_GRAY);
		mHeaderPanel.add(mLabelCouponAmt);
		
//		left=mLabelCouponAmt.getX()+mLabelCouponAmt.getWidth()+PANEL_CONTENT_H_GAP;
		
		mLabelCouponCount=new JLabel();
		mLabelCouponCount.setText("Count");
		mLabelCouponCount.setHorizontalAlignment(SwingConstants.CENTER);		
		mLabelCouponCount.setPreferredSize(new Dimension( PosCouponPaymentControl.COUPON_COUNT_FIELD_WIDTH+1, HEADER_PANAL_HEIGHT));		
		mLabelCouponCount.setFont(PosFormUtil.getLabelFont());
		mLabelCouponCount.setOpaque(true);
		mLabelCouponCount.setBackground(Color.LIGHT_GRAY);
		mHeaderPanel.add(mLabelCouponCount);	
		
//		left=mLabelCouponCount.getX()+mLabelCouponCount.getWidth()+PANEL_CONTENT_H_GAP;
		
		mLabelTotal=new JLabel();
		mLabelTotal.setText("Amount");
		mLabelTotal.setHorizontalAlignment(SwingConstants.CENTER);		
		mLabelTotal.setPreferredSize(new Dimension( PosCouponPaymentControl.COUPON_TOTAL_AMT_FIELD_WIDTH+10, HEADER_PANAL_HEIGHT));		
		mLabelTotal.setFont(PosFormUtil.getLabelFont());
		mLabelTotal.setOpaque(true);
		mLabelTotal.setBackground(Color.LIGHT_GRAY);
		mHeaderPanel.add(mLabelTotal);	
	}		 
	
	
	
	public void addCouponPaymentItem(){		
		int totalItems=MAX_COUPON_COUNT;
		int left=PANEL_CONTENT_H_GAP;
		int top=0;
		mPosCouponPaymentControlList=new ArrayList<PosCouponPaymentControl>();
		for(int index=0;index<totalItems;index++){
			top=index*(PosCouponPaymentControl.LAYOUT_HEIGHT);
			PosCouponPaymentControl couponPayItemCtrl=null;
			
			if(index<mCouponlist.size())
				couponPayItemCtrl=new PosCouponPaymentControl(mCouponlist.get(index),mParent);
			else
				couponPayItemCtrl=new PosCouponPaymentControl(mParent);
			
			couponPayItemCtrl.setLocation(left, top);	
			couponPayItemCtrl.setListner(couponControlListner);
			couponPayItemCtrl.setIndex(index);
			
			mPosCouponPaymentControlList.add(couponPayItemCtrl);
			mCouponPayItemListPanel.add(couponPayItemCtrl);
			mCouponItems.add(couponPayItemCtrl.getCouponItem());
		}
		
//		mCouponPayItemListPanel.setSize(width,height);
//		mCouponPayItemListPanel.setBackground(Color.RED);
		mCouponPayItemListPanel.revalidate();
		mCouponPayItemListPanel.repaint();
	}	
	
	public ArrayList<BeanCoupon> getCoupons(){
		return mCouponItems;
	}
	
	public double getCouponTotalAmount(){
		double total=0;
		for(BeanCoupon coupon:  mCouponItems)
			if(coupon!=null)
				total+=coupon.getValue()*coupon.getCount();
		return total;
	}
	
	public Integer getCouponCount(){
		return 1;		
	}
	
	public void setListner(IPosCoupenContainerListner listner){
		mCoupenContainerListner=listner;
	}
	
	private IPosCouponPaymentControlListner couponControlListner=new IPosCouponPaymentControlListner() {
		@Override
		public void onChange(BeanCoupon item, int index) {
			mCouponItems.set(index, item);
			if(mCoupenContainerListner!=null)
				mCoupenContainerListner.onCouponChanged();
//			mLabelGrantTotalvalue.setText(String.valueOf(getCouponTotalAmount()));
		}
	};
	

	public void reset(){
		for(PosCouponPaymentControl payCtrl:mPosCouponPaymentControlList)
			payCtrl.reset();
	}

}
