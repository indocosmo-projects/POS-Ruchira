package com.indocosmo.pos.forms;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;

import com.indocosmo.pos.common.PosEnvSettings;
import com.indocosmo.pos.common.PosLog;
import com.indocosmo.pos.common.utilities.PosCurrencyUtil;
import com.indocosmo.pos.common.utilities.PosDateUtil;
import com.indocosmo.pos.common.utilities.PosFormUtil;
import com.indocosmo.pos.common.utilities.PosOrderUtil;
import com.indocosmo.pos.common.utilities.PosResUtil;
import com.indocosmo.pos.data.beans.BeanOrderHeader;
import com.indocosmo.pos.data.beans.BeanOrderHeader.PosOrderStatus;
import com.indocosmo.pos.data.beans.terminal.BeanTerminalInfo;
import com.indocosmo.pos.data.providers.shopdb.PosDiscountItemProvider;
import com.indocosmo.pos.forms.components.buttons.PosButton;
import com.indocosmo.pos.forms.components.buttons.PosButton.ButtonStyle;
import com.indocosmo.pos.forms.components.buttons.listners.IPosButtonListner;
import com.indocosmo.pos.forms.components.buttons.listners.PosButtonListnerAdapter;
import com.indocosmo.pos.forms.components.textfields.PosTouchableFieldBase;
import com.indocosmo.pos.forms.components.textfields.PosTouchableTextField;
import com.indocosmo.pos.forms.components.textfields.listners.IPosTouchableFieldListner;
import com.indocosmo.pos.forms.listners.IPosHoldOrderFormFormListner;

@SuppressWarnings("serial")
public final class PosOrderBillAmountInfoForm extends PosBaseForm {

	

	protected static final int PANEL_CONTENT_H_GAP = 2;
	protected static final int PANEL_CONTENT_V_GAP = 2;
	
	private static final int ITEM_PANEL_H_GAP = 1;

	private static final int ITEM_ROWS = 5;
	private static final int ITEM_COLS = 2;

	private static final int ITEM_TITEL_WIDTH =170;
	private static final int ITEM_TEXT_WIDTH = 220;
	private static final int ITEM_HEIGHT = PosTouchableFieldBase.LAYOUT_DEF_HEIGHT;

	private static final int ITEM_PANEL_HIEGHT = ITEM_HEIGHT;
	private static final int ITEM_PANEL_WIDTH = (ITEM_TITEL_WIDTH + ITEM_TEXT_WIDTH)
			+ PANEL_CONTENT_H_GAP * 3;
		 
	private static final int FORM_HEIGHT =   (ITEM_PANEL_HIEGHT+ PANEL_CONTENT_V_GAP *2)*6 ;
	private static final int FORM_WIDTH = ITEM_PANEL_WIDTH+ PANEL_CONTENT_H_GAP *3 ;
	private BeanOrderHeader mOrderHeader;
	
	private JTextField mTextItemTotal;
	private JTextField mTextExtraCharge;
	private JTextField mTextTotalAmount;
	private JTextField mTextTotalPaid;
	private JTextField mTextTotalDiscount;
	private JTextField mTextDueAmount;
	private JPanel mContentPanel;
	
	public PosOrderBillAmountInfoForm(BeanOrderHeader orderHeader ){
		
		super("Amount Details", FORM_WIDTH,FORM_HEIGHT);	
		mOrderHeader=orderHeader;
		setCancelButtonVisible(false);
		createUI();
		loadUI();
	}
 
	/***
	 * 
	 */
	private void createUI() {
		mContentPanel.setLayout(new FlowLayout(FlowLayout.LEFT,PANEL_CONTENT_H_GAP,PANEL_CONTENT_V_GAP));
		mTextItemTotal = crateField(mContentPanel, "Item Total :");
		mTextExtraCharge = crateField(mContentPanel, "Extra Charge :");
		mTextTotalDiscount = crateField(mContentPanel, "Discount :");
		mTextTotalAmount = crateTotalField(mContentPanel, "Total Amount :");
		
		mTextTotalPaid = crateField(mContentPanel, "Paid Amount:");
	 	mTextDueAmount = crateField(mContentPanel, "Due Amount :");

	}
	/*
	 * 
	 */
	private void loadUI(){

		
		final double totalAmtPaid= mOrderHeader.getTotalAmountPaid() -
				(mOrderHeader.getChangeAmount()+mOrderHeader.getCashOut())-
				mOrderHeader.getRoundAdjustmentAmount()  ;
		final double totalDiscount=PosOrderUtil.getBillDiscount(mOrderHeader);
		mTextItemTotal.setText(PosCurrencyUtil.format(mOrderHeader.getTotalAmount()));
		mTextExtraCharge.setText(PosCurrencyUtil.format(mOrderHeader.getExtraCharges() + 
				mOrderHeader.getExtraChargeTaxOneAmount() + mOrderHeader.getExtraChargeTaxTwoAmount() +
				mOrderHeader.getExtraChargeTaxThreeAmount() + mOrderHeader.getExtraChargeGSTAmount()));
		mTextTotalDiscount.setText(PosCurrencyUtil.format(totalDiscount));
		mTextTotalAmount.setText(PosCurrencyUtil.format(PosOrderUtil.getTotalAmount(mOrderHeader)-totalDiscount));
		mTextTotalPaid.setText(PosCurrencyUtil.format(totalAmtPaid));
		mTextDueAmount.setText(PosCurrencyUtil.format(PosOrderUtil.getTotalAmount(mOrderHeader)-totalAmtPaid-totalDiscount));
		
	}
	
	/* (non-Javadoc)
	 * @see com.indocosmo.pos.forms.PosBaseForm#setContentPanel(javax.swing.JPanel)
	 */
	@Override
	protected void setContentPanel(JPanel panel) {
		// TODO Auto-generated method stub
		mContentPanel=panel;
		
	}

	/**
	 * 
	 * @param panel
	 * @param title
	 * @return
	 */
	private JTextField crateField(JPanel panel, String title) {
		JPanel itemPanel = creatFieldPanelWithTitle(title);
		panel.add(itemPanel);

		JTextField text = new JTextField("");
		text.setPreferredSize(new Dimension(ITEM_TEXT_WIDTH, ITEM_HEIGHT));
		text.setFont(PosFormUtil.getLabelFont());
		text.setEditable(false);
		text.setHorizontalAlignment(SwingConstants.RIGHT);
		itemPanel.add(text);

		return text;
	}

	/**
	 * 
	 * @param panel
	 * @param title
	 * @return
	 */
	private JTextField crateTotalField(JPanel panel, String title) {
 
		
		JPanel itemPanel = new JPanel();
		itemPanel
		.setLayout(new FlowLayout(FlowLayout.LEFT, ITEM_PANEL_H_GAP, 0));
		itemPanel.setPreferredSize(new Dimension(ITEM_PANEL_WIDTH,
				ITEM_PANEL_HIEGHT));
		panel.add(itemPanel);
		
		JLabel lable = new JLabel(title);
		lable.setPreferredSize(new Dimension(ITEM_TITEL_WIDTH, ITEM_HEIGHT));
		lable.setBorder(new EmptyBorder(2, 2, 2, 2));
		lable.setFont(PosFormUtil.getTextFieldBoldFont());
		lable.setOpaque(true);
		lable.setBackground(Color.LIGHT_GRAY);
		itemPanel.add(lable);

		JTextField text = new JTextField("");
		text.setPreferredSize(new Dimension(ITEM_TEXT_WIDTH, ITEM_HEIGHT));
		text.setFont(PosFormUtil.getTextFieldBoldFont());
		text.setEditable(false);
		text.setHorizontalAlignment(SwingConstants.RIGHT);
		itemPanel.add(text);

		return text;
	}
	/***
	 * 
	 * @param title
	 * @return
	 */
	private JPanel creatFieldPanelWithTitle(String title) {
		JPanel itemPanel = new JPanel();
		itemPanel
		.setLayout(new FlowLayout(FlowLayout.LEFT, ITEM_PANEL_H_GAP, 0));
		itemPanel.setPreferredSize(new Dimension(ITEM_PANEL_WIDTH,
				ITEM_PANEL_HIEGHT));

		JLabel lable = new JLabel(title);
		lable.setPreferredSize(new Dimension(ITEM_TITEL_WIDTH, ITEM_HEIGHT));
		lable.setBorder(new EmptyBorder(2, 2, 2, 2));
		lable.setFont(PosFormUtil.getLabelFont());
		lable.setOpaque(true);
		lable.setBackground(Color.LIGHT_GRAY);
		itemPanel.add(lable);

		return itemPanel;
	}

}
