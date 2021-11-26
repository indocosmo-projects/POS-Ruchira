package com.indocosmo.pos.forms.components.orderentry;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import com.indocosmo.pos.common.PosEnvSettings;
import com.indocosmo.pos.common.utilities.PosFormUtil;
import com.indocosmo.pos.common.utilities.PosOrderUtil;
import com.indocosmo.pos.common.utilities.PosUtil;
import com.indocosmo.pos.data.beans.BeanCashierShift;
import com.indocosmo.pos.data.beans.BeanCustomer;
import com.indocosmo.pos.data.beans.BeanItemPromotion;
import com.indocosmo.pos.data.beans.BeanOrderCustomer;
import com.indocosmo.pos.data.beans.BeanOrderHeader;
import com.indocosmo.pos.forms.PosOrderEntryForm;
import com.indocosmo.pos.forms.PosOrderEntryForm.PosOrderEntryMode;

/**
 * @author jojesh Class to handle the Message display panel
 */
public final class PosMessagePanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final int PANEL_HEIGHT = 25;
	// private static final Color PANEL_BG_COLOR=new Color(100,100,162);
	private static final Color PANEL_BG_COLOR = PosOrderEntryForm.PANEL_BG_COLOR;

	private static final int PANEL_CONTENT_H_GAP = 1;// PosOrderEntryForm.PANEL_CONTENT_H_GAP;
	private static final int PANEL_CONTENT_V_GAP = 1;// PosOrderEntryForm.PANEL_CONTENT_V_GAP;

//	private static final Color LABEL_BG_COLOR = new Color(6, 38, 76);
	private static final Color LABEL_FG_COLOR = Color.WHITE;
	private static final Dimension SCREEN_SIZE=PosUtil.getScreenSize(PosEnvSettings.getInstance().getPrimaryScreen());
	private static final int LABEL_STAFF_NAME_WIDTH =(int) ((int)  SCREEN_SIZE.getWidth()*(115f/1024f));//  115;	
	private static final int LABEL_SHIFT_WIDTH = (int) ((int)  SCREEN_SIZE.getWidth()*(110f/1024f)); //110;			
	private static final int LABEL_CUSTOMER_WIDTH =(int) ((int)  SCREEN_SIZE.getWidth()*(150f/1024f)); // 150;
	private static final int LABEL_MENU_WIDTH =(int) ((int)  SCREEN_SIZE.getWidth()*(180f/1024f)); // 180;
	private static final int LABEL_ORDER_NO_WIDTH =(int) ((int)  SCREEN_SIZE.getWidth()*(150f/1024f)); // 150;
	private static final int LABEL_ORDER_TYPE_WIDTH =(int) ((int)  SCREEN_SIZE.getWidth()*(115f/1024f)); // 115;
	private static final int LABEL_SALES_PLAN_WIDTH =(int) ((int)  SCREEN_SIZE.getWidth()*(150f/1024f)); // 150;

	private static final Color LABEL_STAFF_NAME_COLOR = new Color(139, 0, 139);
	private static final Color LABEL_SHIFT_COLOR = new Color(208, 32, 144);
	private static final Color LABEL_CUSTOMER_COLOR = new Color(205, 91, 69);
	private static final Color LABEL_MENU_COLOR = new Color(205, 205, 0);
	private static final Color LABEL_ORDER_NO_COLOR = new Color(46, 139, 87);
	private static final Color LABEL_ORDER_TYPE_COLOR = new Color(0, 139, 139);
	private static final Color LABEL_SALES_PLAN_COLOR = new Color(125, 38, 205);
//	private static final Color LABEL_MISC_COLOR = new Color(205, 133, 0);

	private static final String ORDER_ENTRY_MODE_PREFIX = " ";// " Mode: ";
	private static final String STAFF_PREFIX = " ";// " Staff: ";
	private static final String CUSTOMER_PREFIX = " ";// " Cust: ";
	private static final String SHIFT_PREFIX = " ";// " Shift: ";
	private static final String SALES_PLAN_PREFIX = " ";// " Plan: ";
	private static final String ORDER_NO_PREFIX = " ";// " Order No: ";

	private static final int LABEL_HEIGHT = PANEL_HEIGHT - PANEL_CONTENT_V_GAP
			* 2;

	private int mLeft, mTop;

	private JLabel mLabelStaffName;
	private JLabel mLabelShift;
	private JLabel mLabelCustomer;
	private JLabel mLabelPromotion;
//	private JLabel mLabelMisc;
	private JLabel mLabelOrderEntryMode;
	private JLabel mLabelOrderNo;
	private JLabel mLabelMenu;

	public PosMessagePanel(int left, int top) {
		mLeft = left;
		mTop = top;
		initComponent();
		 
	}

	private void initComponent() {
		
		int width = (int) SCREEN_SIZE.getWidth() - PosOrderEntryForm.PANEL_H_GAP
				* 2;
		int height = PANEL_HEIGHT;
		setSize(width, height);
		setPreferredSize(new Dimension(width, height));
		setBounds(mLeft, mTop, width, height);
		setBackground(PANEL_BG_COLOR);
		setLayout(new FlowLayout(FlowLayout.LEFT, PANEL_CONTENT_H_GAP,
				PANEL_CONTENT_V_GAP));
		createControls();
	}

	private void createControls() {

		createStaffPanel();
		createShiftPanel();
		createCustomerPanel();
		createMenuPanel();
		createOrderEntryPanel();
		createOrderNoPanel();
		createPromotionPanel();
//		createMiscPanel();
	}

	private void createStaffPanel() {
		mLabelStaffName = new JLabel(STAFF_PREFIX);
		// mLabelStaffName.setBounds(0, PANEL_CONTENT_V_GAP,
		// LABEL_STAFF_NAME_WIDTH, LABEL_HEIGHT);
		mLabelStaffName.setPreferredSize(new Dimension(LABEL_STAFF_NAME_WIDTH,
				LABEL_HEIGHT));
		mLabelStaffName.setOpaque(true);
		mLabelStaffName.setBackground(LABEL_STAFF_NAME_COLOR);
		mLabelStaffName.setForeground(LABEL_FG_COLOR);
		mLabelStaffName.setFont(PosFormUtil.getSubHeadingFont());
		add(mLabelStaffName);
	}

	private void createShiftPanel() {
		mLabelShift = new JLabel(SHIFT_PREFIX);
		mLabelShift.setPreferredSize(new Dimension(LABEL_SHIFT_WIDTH,
				LABEL_HEIGHT));
		mLabelShift.setOpaque(true);
		mLabelShift.setBackground(LABEL_SHIFT_COLOR);
		mLabelShift.setForeground(LABEL_FG_COLOR);
		mLabelShift.setFont(PosFormUtil.getSubHeadingFont());
		add(mLabelShift);
	}

	private void createCustomerPanel() {
		mLabelCustomer = new JLabel(CUSTOMER_PREFIX);
		mLabelCustomer.setPreferredSize(new Dimension(LABEL_CUSTOMER_WIDTH,
				LABEL_HEIGHT));
		mLabelCustomer.setOpaque(true);
		mLabelCustomer.setBackground(LABEL_CUSTOMER_COLOR);
		mLabelCustomer.setForeground(LABEL_FG_COLOR);
		mLabelCustomer.setFont(PosFormUtil.getSubHeadingFont());
		add(mLabelCustomer);
	}

	private void createMenuPanel() {
		mLabelMenu = new JLabel();
		mLabelMenu.setPreferredSize(new Dimension(LABEL_MENU_WIDTH,
				LABEL_HEIGHT));
		mLabelMenu.setOpaque(true);
		mLabelMenu.setBackground(LABEL_MENU_COLOR);
		mLabelMenu.setForeground(LABEL_FG_COLOR);
		mLabelMenu.setFont(PosFormUtil.getSubHeadingFont());
		add(mLabelMenu);
	}


	private void createOrderEntryPanel() {
		mLabelOrderEntryMode = new JLabel(ORDER_ENTRY_MODE_PREFIX);
		mLabelOrderEntryMode.setPreferredSize(new Dimension(
				LABEL_ORDER_TYPE_WIDTH, LABEL_HEIGHT));
		mLabelOrderEntryMode.setOpaque(true);
		mLabelOrderEntryMode.setBackground(LABEL_ORDER_TYPE_COLOR);
		mLabelOrderEntryMode.setForeground(LABEL_FG_COLOR);
		mLabelOrderEntryMode.setFont(PosFormUtil.getSubHeadingFont());
		add(mLabelOrderEntryMode);
	}

	private void createOrderNoPanel() {
		mLabelOrderNo = new JLabel(ORDER_NO_PREFIX);
		mLabelOrderNo.setPreferredSize(new Dimension(LABEL_ORDER_NO_WIDTH,
				LABEL_HEIGHT));
		mLabelOrderNo.setOpaque(true);
		mLabelOrderNo.setBackground(LABEL_ORDER_NO_COLOR);
		mLabelOrderNo.setForeground(LABEL_FG_COLOR);
		mLabelOrderNo.setFont(PosFormUtil.getSubHeadingFont());
		
		add(mLabelOrderNo);
	}
	

	private void createPromotionPanel() {
		
		final int width = LABEL_CUSTOMER_WIDTH + LABEL_MENU_WIDTH
				+ LABEL_ORDER_NO_WIDTH + LABEL_ORDER_TYPE_WIDTH
				+ LABEL_SALES_PLAN_WIDTH + LABEL_SHIFT_WIDTH
				+ LABEL_STAFF_NAME_WIDTH+PANEL_CONTENT_H_GAP*8;
		
		final int fillWidth=getWidth()-width;
		
		mLabelPromotion = new JLabel(SALES_PLAN_PREFIX);
		mLabelPromotion.setPreferredSize(new Dimension(LABEL_SALES_PLAN_WIDTH+fillWidth,
				LABEL_HEIGHT));
		mLabelPromotion.setOpaque(true);
		mLabelPromotion.setBackground(LABEL_SALES_PLAN_COLOR);
		mLabelPromotion.setForeground(LABEL_FG_COLOR);
		mLabelPromotion.setFont(PosFormUtil.getSubHeadingFont());
		
		add(mLabelPromotion);
	}

//	private void createMiscPanel() {
//		
//		final int width = LABEL_CUSTOMER_WIDTH + LABEL_MENU_WIDTH
//				+ LABEL_ORDER_NO_WIDTH + LABEL_ORDER_TYPE_WIDTH
//				+ LABEL_SALES_PLAN_WIDTH + LABEL_SHIFT_WIDTH
//				+ LABEL_STAFF_NAME_WIDTH+PANEL_CONTENT_H_GAP*8;
//		
//		mLabelMisc = new JLabel();
//		mLabelMisc.setPreferredSize(new Dimension(getWidth()-width, LABEL_HEIGHT));
//		mLabelMisc.setOpaque(true);
//		mLabelMisc.setBackground(LABEL_MISC_COLOR);
//		mLabelMisc.setForeground(LABEL_FG_COLOR);
//		mLabelMisc.setText("MISC");
//		add(mLabelMisc);
//	}

	public void setStaff(String name) {
		mLabelStaffName.setText(" " + name);
	}

	public void setShift(String shift) {
		mLabelShift.setText(" " + shift);
	}

	public void setMenu(String menu) {
		mLabelMenu.setText(" " + menu);
	}

	public void setCustomer(BeanOrderCustomer customer) {
		if (customer != null)
			mLabelCustomer.setText(CUSTOMER_PREFIX + customer.getName());
		else
			mLabelCustomer.setText(CUSTOMER_PREFIX);
	}

	public void setShiftInfo(BeanCashierShift shift) {
		if (shift != null)
			setStaff(shift.getCashierInfo().getName());
		if (shift != null && shift.getShiftItem() != null)
			setShift(shift.getShiftItem().getName());
	}

	public void setPromotion(BeanItemPromotion promotion) {
		String promotionTitle = "";
		if (promotion != null)
			promotionTitle = promotion.getName();
		mLabelPromotion.setText(SALES_PLAN_PREFIX + promotionTitle);
	}

	public void setOrderEntryMode(PosOrderEntryMode mode) {
		mLabelOrderEntryMode.setText(ORDER_ENTRY_MODE_PREFIX + mode.toString());
	}

	public void setOrderNumber(BeanOrderHeader orderEntryItem) {
		
		String orderNumber = "";
		if (orderEntryItem != null)
		{
			orderNumber = PosOrderUtil
					.getShortOrderIDFromOrderID(orderEntryItem.getOrderId());
		
			if  (PosEnvSettings.getInstance().getUISetting().useOrderQueueNo())
			{
				if (!orderEntryItem.isNewOrder())
					mLabelOrderNo.setText(ORDER_NO_PREFIX + 
							PosOrderUtil.getFormatedOrderQueueNo(orderEntryItem));
				
			}else{
				
				mLabelOrderNo.setText(ORDER_NO_PREFIX + orderNumber);	
			}
		}
		else
			mLabelOrderNo.setText("");
		
		
	}

}
