package com.indocosmo.pos.forms.components.itemcontrols;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JLabel;

import com.indocosmo.pos.common.utilities.PosDateUtil;
import com.indocosmo.pos.common.utilities.PosFormUtil;
import com.indocosmo.pos.data.beans.BeanCashierShift;

@SuppressWarnings("serial")
public final class PosCashierShiftControl extends PosItemSelectorBasePanel{

	
	private final static int PANEL_CONTENT_H_GAP=2;
	private final static int PANEL_CONTENT_V_GAP=2;
	
//	private final static int BORDER_WIDTH=3;
	
	private final static int CASHIER_NAME_LABEL_HEIGHT=20;
	private final static int CASHIER_NAME_LABEL_WIDTH=95;
	
	private final static int CASHIER_ID_LABEL_HEIGHT=CASHIER_NAME_LABEL_HEIGHT;
	private final static int CASHIER_ID_LABEL_WIDTH=209;
	
	private final static int OWNER_FLAG_LABEL_WIDTH=25;
	private final static int OWNER_FLAG_LABEL_HEIGHT=CASHIER_NAME_LABEL_HEIGHT;
	
	private final static int SHIFT_NAME_LABEL_HEIGHT=CASHIER_NAME_LABEL_HEIGHT;
	private final static int SHIFT_NAME_LABEL_WIDTH=CASHIER_NAME_LABEL_WIDTH;
	
	private final static int SHIFT_START_LABEL_HEIGHT=CASHIER_NAME_LABEL_HEIGHT;
	private final static int SHIFT_START_LABEL_WIDTH=CASHIER_ID_LABEL_WIDTH;
	
	public final static int LAYOUT_WIDTH=CASHIER_NAME_LABEL_WIDTH+CASHIER_ID_LABEL_WIDTH+PANEL_CONTENT_H_GAP*3+BORDER_WIDTH*2;
	public final static int LAYOUT_HEIGHT=CASHIER_NAME_LABEL_HEIGHT+SHIFT_NAME_LABEL_HEIGHT+PANEL_CONTENT_V_GAP*3+BORDER_WIDTH*2;
	
//	private static final Color SELECTED_ITEM_BG_COLOR=Color.CYAN;// new Color(179, 34, 65);
//	private static final Color SELECTED_ITEM_BORDER_COLOR=Color.ORANGE;
//	private static final Color ITEM_BORDER_COLOR=Color.GRAY;
	
	private JLabel mLabelCashierName;
	private JLabel mLabelCashierNumber;
	private JLabel mLabelShiftName;
	private JLabel mLabelShiftStartAt;
	private JLabel mLabelOwnerFlag;
	
//	private boolean mIsSelected=false;
	private BeanCashierShift mCashierShift;
	/**
	 * Create the dialog.
	 */
	public PosCashierShiftControl() {
		super(LAYOUT_WIDTH,LAYOUT_HEIGHT);
		
//		initComponent();
	}
	
	@Override
	protected  void initComponent() {
		super.initComponent();
		setLayout(new FlowLayout(FlowLayout.LEFT,PANEL_CONTENT_H_GAP,PANEL_CONTENT_V_GAP));
		setCashierName();
		setCashierNumber();
		setOwnerFlag();
		setShiftName();
		setShiftStart();
		
	}
	protected void setLabelFont(JLabel label){
	 	label.setFont(PosFormUtil.getLabelFontSmall());  
	}

	private void setCashierName(){
		mLabelCashierName=new JLabel("jojesh jose");
//		mLabelCashierName.setBackground(Color.CYAN);
//		mLabelCashierName.setOpaque(true);
		mLabelCashierName.setPreferredSize(new Dimension(CASHIER_NAME_LABEL_WIDTH,CASHIER_NAME_LABEL_HEIGHT));
		setLabelFont(mLabelCashierName);
		add(mLabelCashierName);
	}
	
	private void setCashierNumber(){
		mLabelCashierNumber=new JLabel("(2000 0768 9804)");
//		mLabelCashierNumber.setBackground(Color.CYAN);
//		mLabelCashierNumber.setOpaque(true);
		mLabelCashierNumber.setPreferredSize(new Dimension(CASHIER_ID_LABEL_WIDTH-OWNER_FLAG_LABEL_WIDTH-5,CASHIER_ID_LABEL_HEIGHT));
		setLabelFont(mLabelCashierNumber);
		add(mLabelCashierNumber);
	}
	
	private void setOwnerFlag(){
		mLabelOwnerFlag = new JLabel("hhh");
		mLabelOwnerFlag.setPreferredSize(new Dimension(OWNER_FLAG_LABEL_WIDTH,OWNER_FLAG_LABEL_HEIGHT));
		add(mLabelOwnerFlag);
	}
	
	private void setShiftName(){
		mLabelShiftName=new JLabel("Morning Shift");
//		mLabelShiftName.setBackground(Color.CYAN);
//		mLabelShiftName.setOpaque(true);
		mLabelShiftName.setPreferredSize(new Dimension(SHIFT_NAME_LABEL_WIDTH,SHIFT_NAME_LABEL_HEIGHT));
		setLabelFont(mLabelShiftName);
		add(mLabelShiftName);
	}
	
	private void setShiftStart(){
		mLabelShiftStartAt=new JLabel("(02-01-2012 9:30)");
//		mLabelShiftStartAt.setBackground(Color.CYAN);
//		mLabelShiftStartAt.setOpaque(true);
		mLabelShiftStartAt.setPreferredSize(new Dimension(SHIFT_START_LABEL_WIDTH,SHIFT_START_LABEL_HEIGHT));
		setLabelFont(mLabelShiftStartAt);
		add(mLabelShiftStartAt);
	}
	

	public void setCashierShift(BeanCashierShift item){
		mCashierShift=item;
		if(mCashierShift!=null){
			mLabelCashierName.setText(mCashierShift.getCashierInfo().getName());
			mLabelCashierNumber.setText(mCashierShift.getCashierInfo().getCode());
			mLabelOwnerFlag.setText(mCashierShift.IsOpenTill()?"<html>(<font color='red'><b>*</b></font>)</html>":"");
			mLabelShiftName.setText(mCashierShift.getShiftItem().getName());
			mLabelShiftStartAt.setText(PosDateUtil.formatShortDateTime(mCashierShift.getOpeningTime()));
			setVisible(true);
		}
		else
			setVisible(false);
	}
	
	public BeanCashierShift getCashierShift(){
		return mCashierShift;
	}

	
//	private IPosCashierShiftControlListner mListner;
//	
//	public void setListner(IPosCashierShiftControlListner listner){
//		mListner=listner;
//	}
	
}
