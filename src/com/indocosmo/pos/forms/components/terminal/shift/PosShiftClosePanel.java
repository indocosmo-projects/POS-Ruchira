/**
 * 
 */
package com.indocosmo.pos.forms.components.terminal.shift;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Map;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.indocosmo.pos.common.PosEnvSettings;
import com.indocosmo.pos.common.PosLog;
import com.indocosmo.pos.common.enums.EnablePrintingOption;
import com.indocosmo.pos.common.utilities.PosAccessPermissionsUtil;
import com.indocosmo.pos.common.utilities.PosCurrencyUtil;
import com.indocosmo.pos.common.utilities.PosDateUtil;
import com.indocosmo.pos.common.utilities.PosFormUtil;
import com.indocosmo.pos.common.utilities.PosNumberUtil;
import com.indocosmo.pos.data.beans.BeanCashierShift;
import com.indocosmo.pos.data.beans.BeanPayModeBase.PaymentMode;
import com.indocosmo.pos.data.beans.BeanShiftCloseSummary;
import com.indocosmo.pos.data.providers.shopdb.PosCashOutProvider;
import com.indocosmo.pos.data.providers.shopdb.PosOrderPaymentsProvider;
import com.indocosmo.pos.data.providers.shopdb.PosShiftSummaryProvider;
import com.indocosmo.pos.forms.PosShiftClosingForm;
import com.indocosmo.pos.forms.components.textfields.PosTouchableDigitalField;
import com.indocosmo.pos.forms.components.textfields.PosTouchableNumericField;
import com.indocosmo.pos.forms.components.textfields.PosTouchableTextField;
import com.indocosmo.pos.forms.components.textfields.listners.IPosTouchableFieldListner;
import com.indocosmo.pos.forms.messageboxes.PosMessageBoxForm.MessageBoxButtonTypes;
import com.indocosmo.pos.forms.messageboxes.PosMessageBoxForm.MessageBoxResults;
import com.indocosmo.pos.reports.shift.PosShiftClosingReport;
import com.indocosmo.pos.terminal.devices.PosDeviceManager;
import com.indocosmo.pos.terminal.devices.printer.PosDeviceReceiptPrinter;
import com.indocosmo.pos.terminal.devices.printer.printerexceptions.PrinterException;

/**
 * @author deepak
 * 
 */
@SuppressWarnings("serial")
public final class PosShiftClosePanel extends PosShiftBasePanel {

	private static final int INNER_PANEL_HEIGHT_DET = 577;
	
	private static final int INNER_PANEL_HEIGHT_SUMMARY = 160;

	
	public static  final int LAYOUT_HEIGHT_SUMMARY = 450;
	public static final int LAYOUT_WIDTH_SUMMARY = 494;	
	
	public static  final int LAYOUT_HEIGHT_DET = 585;
	public static final int LAYOUT_WIDTH_DET = 983;
	
	
	private static final int TXT_FIELD_WIDTH = 250;
	private static final int LABEL_WIDTH = 230;
	private static final int SUMMARY_TEXT_WIDTH = 158; 
	private static final int SUMMARY_LABEL_WIDTH = 158; 
	private static final int SUMMARY_LABEL_HEIGHT= LABEL_HEIGHT-10; 
	private static final int scrollHeight = 20;

	private static final int TXT_AREA_FIELD_HEIGHT_DET = 65;
	private static final int TXT_AREA_FIELD_HEIGHT_SUMMARY = 100;
	private JPanel mShiftDetailPanel;
	private JPanel mSummaryDetailPanel;

	private JTextField mTxtCashier;
	//	private JTextField mTxtShiftOpen;
	private JTextField mTxtShiftName;
	private JTextField mTxtCashReceipts;
	private JTextField mTxtCashReturned;
	private JTextField mTxtCardReceipts;
	private JTextField mTxtOnlineReceipts;
	private JTextField mTxtCashOut;
	private JTextField mTxtVoucherReceipts;
	private JTextField mTxtVoucherBalance;
	private JTextField mTxtAccountsReceivable;
	private JTextField mTxtTotalRefund;
	private JTextField mTxtSales;
	private JTextField mTxtOpeningCash;
	private JTextField mTxtNetCashRecieved;
	private JTextField mTxtSummaryCashOut;
	private JTextField mTxtVoucherBalanceReturned;
	private JTextField mTxtSummaryCashRefund;
	private JTextField mTxtClosingCash;
	private PosTouchableDigitalField mTxtActualCash;
	private JTextField mTxtCashVariance;
	private JTextField mTxtPreviousAdvance; 
	private JTextField mTxtSummaryExpense;
	private JTextField mTxtAdvanceCash;
	private JTextField mTxtAdvanceCard;
	private JTextField mTxtAdvanceOnline;
	
	//	private String ShiftStartDate;
	private String ShiftCloseDate;
	private String ShiftCloseTime;
	private JTextArea mTxtReason;
	private double mRefundByCash;
	private double mRefundByCard;
	private double mRefundByCompany;
	private double mRefundByCoupon;
	private double mRefundByOnline;
	private PosTouchableDigitalField mTxtCashDeposit;
	private PosTouchableTextField mTxtReferenceNumber;
	

	private int panelHeight;
	/**
	 * @param parent
	 * @param shiftAction
	 */
	public PosShiftClosePanel(JDialog parent) {
		super(parent, ShiftActions.Shift_Close);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.indocosmo.pos.components.terminal.shift.PosShiftBasePanel#initControls
	 * ()
	 */
	@Override
	protected void initControls() {

//		mCashierShiftInfo = PosEnvSettings.getInstance().getCashierShiftInfo();
		mCashierShiftInfo = PosEnvSettings.getInstance().getTillOpenCashierShiftInfo();
		mShopShiftInfo = mCashierShiftInfo.getShiftItem();
		mCashierInfo = mCashierShiftInfo.getCashierInfo();
		
		panelHeight=(PosEnvSettings.getInstance().getUISetting().getMainMenuUISettings().getTillFormSettings().isShowSummaryOnly()?
				   INNER_PANEL_HEIGHT_SUMMARY:INNER_PANEL_HEIGHT_DET);
		
		setLayout(new FlowLayout(FlowLayout.LEFT,1,1));
		final int width=(PosEnvSettings.getInstance().getUISetting().getMainMenuUISettings().getTillFormSettings().isShowSummaryOnly()?
				  LAYOUT_WIDTH_SUMMARY  :LAYOUT_WIDTH_DET);
	
		final int height=(PosEnvSettings.getInstance().getUISetting().getMainMenuUISettings().getTillFormSettings().isShowSummaryOnly()?
				  LAYOUT_HEIGHT_SUMMARY  :LAYOUT_HEIGHT_DET);
		setSize(width, height);
//		setBorder(BorderFactory.createLineBorder(Color.red));
		createShiftDetailPanel();
		setShiftDetailpanel();
		createSummaryDetailPanel();
		setSummaryDetailpanel();
		setFieldValues();
		setCashSummaryFieldValues();
	}
	
	/* (non-Javadoc)
	 * @see com.indocosmo.pos.forms.components.terminal.shift.PosShiftBasePanel#setDefaultFocus()
	 */
	@Override
	public void setDefaultFocus() {
	
		mTxtCashDeposit.requestFocus();
	}
	
	


	/**
	 * Setting the field values.
	 */
	private void setFieldValues() {
		
		mTxtCashier.setText(mCashierInfo.getName());
		mTxtCashier.setCaretPosition(1);
		mTxtShiftStartDate = new JTextField();
		mTxtShiftStartDate.setText(mCashierShiftInfo.getOpeningDate());
		mTxtShiftStartTime.setText(PosDateUtil.format(mCashierShiftInfo.getOpeningTime()));
		mTxtShiftName.setText(mShopShiftInfo.getName());
		mIsOpenTill=mCashierShiftInfo.IsOpenTill();
		ShiftCloseDate = PosEnvSettings.getPosEnvSettings().getPosDate();
		ShiftCloseTime = PosDateUtil.getDateTime();
		mTxtCashReceipts.setText(PosCurrencyUtil.format(0));
		mTxtCashReturned.setText(PosCurrencyUtil.format(0));
		mTxtCardReceipts.setText(PosCurrencyUtil.format(0));
		mTxtOnlineReceipts.setText(PosCurrencyUtil.format(0));
		mTxtAdvanceCash.setText(PosCurrencyUtil.format(0));
		mTxtAdvanceCard.setText(PosCurrencyUtil.format(0));
		mTxtAdvanceOnline.setText(PosCurrencyUtil.format(0));
		mTxtCashOut.setText(PosCurrencyUtil.format(0));
		mTxtVoucherReceipts.setText(PosCurrencyUtil.format(0));
		mTxtVoucherBalance.setText(PosCurrencyUtil.format(0));
		mTxtAccountsReceivable.setText(PosCurrencyUtil.format(0));
		mTxtTotalRefund.setText(PosCurrencyUtil.format(0));
		mTxtOpeningFloatAmount.setText(PosCurrencyUtil.format(mCashierShiftInfo.getOpeningFloat()));
		mTxtOpeningCash.setText(PosCurrencyUtil.format(mCashierShiftInfo.getOpeningFloat()));
		mTxtNetCashRecieved.setText(PosCurrencyUtil.format(0));
		mTxtSummaryCashOut.setText(PosCurrencyUtil.format(0));
		mTxtVoucherBalanceReturned.setText(PosCurrencyUtil.format(0));
		mTxtSummaryCashRefund.setText(PosCurrencyUtil.format(0));
		mTxtPreviousAdvance.setText(PosCurrencyUtil.format(0));
		mTxtClosingCash.setText(PosCurrencyUtil.format(0));
		mTxtActualCash.setText(" ");
		mTxtCashVariance.setText(PosCurrencyUtil.format(0));
		loadShiftTxnAmounts();
	}

	/**
	 * Load the payment details in this shift here. 
	 * Set the payments in the fields.
	 * 
	 */
	private void loadShiftTxnAmounts() {
		
		PosOrderPaymentsProvider paymentProvider=new PosOrderPaymentsProvider();
		double totalAmount=0.0;
		double getTotalVoucherBalanceReturned = 0;	
		double totalRefund = 0;
		double totalPreviousAdvance=0;
		Map<PaymentMode, Double> payments=null;
		Map<PaymentMode, Double> refundPayments=null;
		Map<PaymentMode, Double> advances=null;

		try {


			payments=paymentProvider.getShiftPayments(mCashierShiftInfo,null,  PosEnvSettings.getInstance().getPosDate(),
					mTxtShiftStartDate.getText(), mTxtShiftStartTime.getText(), PosDateUtil.getDate(), PosDateUtil.getDateTime(),false,false);

			advances=paymentProvider.getShiftPayments(mCashierShiftInfo,null,  PosEnvSettings.getInstance().getPosDate(),
					mTxtShiftStartDate.getText(), mTxtShiftStartTime.getText(), PosDateUtil.getDate(), PosDateUtil.getDateTime(),false,true);

//			totalAmount=paymentProvider.getNetSale(mCashierShiftInfo,null,  PosEnvSettings.getInstance().getPosDate());

			getTotalVoucherBalanceReturned = paymentProvider.getTotalVoucherBalanceReturned(mCashierShiftInfo,null,  PosEnvSettings.getInstance().getPosDate(),
					mTxtShiftStartDate.getText(), mTxtShiftStartTime.getText(), PosDateUtil.getDate(),  PosDateUtil.getDateTime());
			mTxtVoucherBalanceReturned.setText(PosCurrencyUtil.format(getTotalVoucherBalanceReturned));	

			totalAmount -= getTotalVoucherBalanceReturned;
			mTxtVoucherBalance.setText(PosCurrencyUtil.format(getTotalVoucherBalanceReturned));
			
			totalPreviousAdvance=paymentProvider.getAdvanceAmtOfClosedSaledsOrder(mCashierShiftInfo, PosEnvSettings.getInstance().getPosDate(), mTxtShiftStartDate.getText());
			totalAmount+=totalPreviousAdvance;
			
			if(payments!=null){
				if(payments.containsKey(PaymentMode.Card)){
					totalAmount+=payments.get(PaymentMode.Card);
					mTxtCardReceipts.setText(PosCurrencyUtil.format(payments.get(PaymentMode.Card)));
				}
				if(payments.containsKey(PaymentMode.Cash)){
					totalAmount+=payments.get(PaymentMode.Cash);
					mTxtCashReceipts.setText(PosCurrencyUtil.format(payments.get(PaymentMode.Cash)));
				}
				if(payments.containsKey(PaymentMode.Company)){
					totalAmount+=payments.get(PaymentMode.Company);
					mTxtAccountsReceivable.setText(PosCurrencyUtil.format(payments.get(PaymentMode.Company)));
				}
				if(payments.containsKey(PaymentMode.Coupon)){
					totalAmount+=payments.get(PaymentMode.Coupon);
					mTxtVoucherReceipts.setText(PosCurrencyUtil.format(payments.get(PaymentMode.Coupon)));
				}
				if(payments.containsKey(PaymentMode.Online)){
					totalAmount+=payments.get(PaymentMode.Online);
					mTxtOnlineReceipts.setText(PosCurrencyUtil.format(payments.get(PaymentMode.Online)));
				}
				if(payments.containsKey(PaymentMode.Balance)){
					totalAmount -= payments.get(PaymentMode.Balance);
					mTxtCashReturned.setText(PosCurrencyUtil.format(payments.get(PaymentMode.Balance)));
				}

//				if(payments.containsKey(PaymentMode.CouponBalance)){
//					totalAmount -= payments.get(PaymentMode.CouponBalance);
//					mTxtVoucherBalance.setText(PosCurrencyUtil.format(payments.get(PaymentMode.CouponBalance)));
//				}
				if(payments.containsKey(PaymentMode.CashOut)){
					totalAmount-= payments.get(PaymentMode.CashOut);
					mTxtCashOut.setText(PosCurrencyUtil.format(payments.get(PaymentMode.CashOut)));
				}
			}
			if(advances!=null){
				if(advances.containsKey(PaymentMode.Card)){
					mTxtAdvanceCard.setText(PosCurrencyUtil.format(advances.get(PaymentMode.Card)));
				}
				if(advances.containsKey(PaymentMode.Cash)){
					mTxtAdvanceCash.setText(PosCurrencyUtil.format(advances.get(PaymentMode.Cash)));
				}
				if(advances.containsKey(PaymentMode.Online)){
					mTxtAdvanceOnline.setText(PosCurrencyUtil.format(advances.get(PaymentMode.Online)));
				}
			}
//			totalRefund = paymentProvider.getRefund(mCashierShiftInfo,null,   PosEnvSettings.getInstance().getPosDate(),
//					mTxtShiftStartDate.getText(), mTxtShiftStartTime.getText(), ShiftCloseDate, ShiftCloseTime);
			
//			totalRefund = paymentProvider.getRefund(mCashierShiftInfo,null,   PosEnvSettings.getInstance().getPosDate(),
//					mTxtShiftStartDate.getText(), mTxtShiftStartTime.getText(), PosDateUtil.getDate(),  PosDateUtil.getDateTime());
//			mTxtTotalRefund.setText(PosCurrencyUtil.format(totalRefund));	
//
//			totalAmount-=totalRefund;
//
//			mTxtSales.setText(PosCurrencyUtil.format(totalAmount));


//			refundPayments = paymentProvider.getShiftPayments(mCashierShiftInfo,null,  PosEnvSettings.getInstance().getPosDate(),
//					mTxtShiftStartDate.getText(), mTxtShiftStartTime.getText(), ShiftCloseDate, ShiftCloseTime,true);
			
			refundPayments = paymentProvider.getShiftPayments(mCashierShiftInfo,null,  PosEnvSettings.getInstance().getPosDate(),
					mTxtShiftStartDate.getText(), mTxtShiftStartTime.getText(), PosDateUtil.getDate(),  PosDateUtil.getDateTime(),true,false);
			if(refundPayments!=null){
				if(refundPayments.containsKey(PaymentMode.Card)){
					mRefundByCard = refundPayments.get(PaymentMode.Card);
				}
				if(refundPayments.containsKey(PaymentMode.Cash)){
					mRefundByCash = refundPayments.get(PaymentMode.Cash);
				}
				if(refundPayments.containsKey(PaymentMode.Company)){
					mRefundByCompany = refundPayments.get(PaymentMode.Company);
				}
				if(refundPayments.containsKey(PaymentMode.Coupon)){
					mRefundByCoupon = refundPayments.get(PaymentMode.Coupon);
				}
				if(refundPayments.containsKey(PaymentMode.Online)){
					mRefundByOnline = refundPayments.get(PaymentMode.Online);
				}
			}
			mTxtSummaryCashRefund.setText(PosCurrencyUtil.format(mRefundByCash));
			mTxtPreviousAdvance.setText(PosCurrencyUtil.format(totalPreviousAdvance));
	
			totalRefund=mRefundByCash+mRefundByCard+mRefundByCompany+mRefundByCoupon+mRefundByOnline;
			
			mTxtTotalRefund.setText(PosCurrencyUtil.format(totalRefund));	

			totalAmount-=totalRefund;

			mTxtSales.setText(PosCurrencyUtil.format(totalAmount));
			final double totalExpense=new PosCashOutProvider().getTotalCashOut(PosEnvSettings.getInstance().getPosDate(),mCashierShiftInfo.getShiftItem().getId() );
			mTxtSummaryExpense.setText(PosCurrencyUtil.format(totalExpense));
			
		} catch (Exception e) {
			PosLog.write(this, "loadShiftTxnAmounts", e);
			PosFormUtil.showSystemError(mParent);
		}
	}


	/**
	 * Set the cash summary details here.
	 */
	private void setCashSummaryFieldValues() {
		final double openingFloat = PosNumberUtil.parseDoubleSafely(mTxtOpeningCash.getText());
		
		final double netCashRecieved =  PosNumberUtil.parseDoubleSafely(mTxtCashReceipts.getText())+ 
				  PosNumberUtil.parseDoubleSafely(mTxtAdvanceCash.getText()) -
				PosNumberUtil.parseDoubleSafely(mTxtCashReturned.getText());
		mTxtNetCashRecieved.setText(PosCurrencyUtil.format(netCashRecieved));
		
		final double totalCashOut = PosNumberUtil.parseDoubleSafely(mTxtCashOut.getText());
		mTxtSummaryCashOut.setText(mTxtCashOut.getText());
		
		final double voucherBalanceReturned = PosNumberUtil.parseDoubleSafely(mTxtVoucherBalanceReturned.getText());
		final double cashRefunds = PosNumberUtil.parseDoubleSafely(mTxtSummaryCashRefund.getText());
		final double expense = PosNumberUtil.parseDoubleSafely(mTxtSummaryExpense.getText());
		final double closingCash = openingFloat+netCashRecieved-totalCashOut-voucherBalanceReturned-cashRefunds-expense;
		mTxtClosingCash.setText(PosCurrencyUtil.format(closingCash));
		mTxtActualCash.setText(" ");
		mTxtActualCash.selectAll();
		mTxtCashVariance.setText(PosCurrencyUtil.format(0));
	}

	/**
	 * Create the panel for shift details.
	 * 
	 */
	private void createShiftDetailPanel() {

		final int panelWidth= LABEL_WIDTH + TXT_FIELD_WIDTH + PANEL_CONTENT_H_GAP ;

		mShiftDetailPanel = new JPanel();
		mShiftDetailPanel.setSize(panelWidth, panelHeight);
		mShiftDetailPanel.setPreferredSize(new Dimension(panelWidth, panelHeight));
		mShiftDetailPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 1,
				PANEL_CONTENT_V_GAP));
		mShiftDetailPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
		add(mShiftDetailPanel);

		JLabel labelMessage=new JLabel();
		labelMessage.setText("Shift details");
		labelMessage.setHorizontalAlignment(SwingConstants.CENTER);		
		labelMessage.setPreferredSize(new Dimension(mShiftDetailPanel.getWidth()-7, MESSAGE_PANEL_HEIGHT-10));				
		labelMessage.setOpaque(true);
		labelMessage.setBackground(LABEL_BG_COLOR);
		labelMessage.setForeground(Color.WHITE);
		labelMessage.setFont(PosFormUtil.getSubHeadingFont());
		mShiftDetailPanel.add(labelMessage);

	}

	/**
	 * 
	 */
	private void setShiftDetailpanel() {

		setCashierName();
		setShiftOpenDateTime();
		setShiftName();
		setCollectionDetails();
	}

	/**
	 * Create field for cashier name.
	 */
	private void setCashierName() {

		JLabel label = new JLabel("Name :");
		label.setOpaque(true);
		label.setBorder(new EmptyBorder(2, 2, 2, 2)); 
		label.setBackground(Color.LIGHT_GRAY);
		label.setPreferredSize(new Dimension(LABEL_WIDTH, LABEL_HEIGHT));
		setLabelFont(label);
		mShiftDetailPanel.add(label);

		mTxtCashier = new JTextField();
		mTxtCashier.setFont(PosFormUtil.getTextFieldFont());
		mTxtCashier.setPreferredSize(new Dimension(TXT_FIELD_WIDTH,
				TEXT_FIELD_HEIGHT));
		mTxtCashier.setEditable(false);
		mTxtCashier.setFocusable(false);
		mShiftDetailPanel.add(mTxtCashier);

	}

	/**
	 * Create field for shift open Date time.
	 */
	private void setShiftOpenDateTime() {

		JLabel label = new JLabel("Shift Open :");
		label.setOpaque(true);
		label.setBorder(new EmptyBorder(2, 2, 2, 2));
		label.setBackground(Color.LIGHT_GRAY);
		label.setPreferredSize(new Dimension(LABEL_WIDTH, LABEL_HEIGHT));
		setLabelFont(label);
		mShiftDetailPanel.add(label);

		mTxtShiftStartTime = new JTextField();
		mTxtShiftStartTime.setFont(PosFormUtil.getTextFieldFont());
		mTxtShiftStartTime.setPreferredSize(new Dimension(TXT_FIELD_WIDTH,
				TEXT_FIELD_HEIGHT));
		mTxtShiftStartTime.setEditable(false);
		mTxtShiftStartTime.setFocusable(false);
		mShiftDetailPanel.add(mTxtShiftStartTime);
	}

	/**
	 * Create field for shift name.
	 */
	private void setShiftName() {

		JLabel label = new JLabel("Shift :");
		label.setOpaque(true);
		label.setBorder(new EmptyBorder(2, 2, 2, 2));
		label.setBackground(Color.LIGHT_GRAY);
		label.setPreferredSize(new Dimension(LABEL_WIDTH, LABEL_HEIGHT));
		setLabelFont(label);
		mShiftDetailPanel.add(label);

		mTxtShiftName = new JTextField();
		mTxtShiftName.setFont(PosFormUtil.getTextFieldFont());
		mTxtShiftName.setPreferredSize(new Dimension(TXT_FIELD_WIDTH,
				TEXT_FIELD_HEIGHT));
		mTxtShiftName.setEditable(false);
		mTxtShiftName.setFocusable(false);
		mShiftDetailPanel.add(mTxtShiftName);

	}

	/**
	 * 
	 */
	private void setCollectionDetails() {
		
		setReceiptControls();
		setCashReturnControls();
		
		 
		setSaleNRefundControls();

	}
	
	 
		/**
		 * Create field for cash receipts and returned.
		 */
		private void setReceiptControls() {
			
			final int width=SUMMARY_TEXT_WIDTH *3 + PANEL_CONTENT_H_GAP ;
			final int height=(LABEL_HEIGHT +SUMMARY_LABEL_HEIGHT) *2 + MESSAGE_PANEL_HEIGHT + PANEL_CONTENT_H_GAP *2 ;

			JPanel receiptPanel=new JPanel();
			receiptPanel.setLayout(new FlowLayout(FlowLayout.LEFT,1,3));
			receiptPanel.setBorder(new EtchedBorder());
			receiptPanel.setPreferredSize(new Dimension(width,height));  
			
			JLabel labelMessage=new JLabel();
			labelMessage.setText("Receipts");
			labelMessage.setHorizontalAlignment(SwingConstants.CENTER);		
			labelMessage.setPreferredSize(new Dimension(SUMMARY_TEXT_WIDTH *3 , MESSAGE_PANEL_HEIGHT-10));				
			labelMessage.setOpaque(true);
			labelMessage.setBackground(LABEL_BG_COLOR);
			labelMessage.setForeground(Color.WHITE);
			labelMessage.setFont(PosFormUtil.getSubHeadingFont());
			receiptPanel.add(labelMessage);

			

			JLabel labelCashReceipts = new JLabel(PaymentMode.Cash.getDisplayText());
			labelCashReceipts.setOpaque(true);
			labelCashReceipts.setBorder(new EmptyBorder(2, 2, 2, 2));
			labelCashReceipts.setBackground(Color.LIGHT_GRAY);
			labelCashReceipts.setPreferredSize(new Dimension(SUMMARY_LABEL_WIDTH, SUMMARY_LABEL_HEIGHT));
			setLabelFont(labelCashReceipts);
			
			JLabel CardReceipts = new JLabel(PaymentMode.Card.getDisplayText());
			CardReceipts.setOpaque(true);
			CardReceipts.setBorder(new EmptyBorder(2, 2, 2, 2));
			CardReceipts.setBackground(Color.LIGHT_GRAY);
			CardReceipts.setPreferredSize(new Dimension(SUMMARY_LABEL_WIDTH, SUMMARY_LABEL_HEIGHT));
			setLabelFont(CardReceipts);
			

			JLabel labelVoucherReceipts = new JLabel(PaymentMode.Coupon.getDisplayText());
			labelVoucherReceipts.setOpaque(true);
			labelVoucherReceipts.setBorder(new EmptyBorder(2, 2, 2, 2));
			labelVoucherReceipts.setBackground(Color.LIGHT_GRAY);
			labelVoucherReceipts.setPreferredSize(new Dimension(SUMMARY_LABEL_WIDTH, SUMMARY_LABEL_HEIGHT));
			setLabelFont(labelVoucherReceipts);
			
			mTxtCashReceipts = new JTextField();
			mTxtCashReceipts.setFont(PosFormUtil.getTextFieldFont());
			mTxtCashReceipts.setPreferredSize(new Dimension(SUMMARY_TEXT_WIDTH,
					TEXT_FIELD_HEIGHT));
			mTxtCashReceipts.setEditable(false);
			mTxtCashReceipts.setFocusable(false);
			mTxtCashReceipts.setHorizontalAlignment(JTextField.RIGHT);
			
			

			mTxtCardReceipts = new JTextField();
			mTxtCardReceipts.setFont(PosFormUtil.getTextFieldFont());
			mTxtCardReceipts.setPreferredSize(new Dimension(SUMMARY_TEXT_WIDTH,
					TEXT_FIELD_HEIGHT));
			mTxtCardReceipts.setEditable(false);
			mTxtCardReceipts.setFocusable(false);
			mTxtCardReceipts.setHorizontalAlignment(JTextField.RIGHT);
		 		
			
			
			mTxtVoucherReceipts = new JTextField();
			mTxtVoucherReceipts.setFont(PosFormUtil.getTextFieldFont());
			mTxtVoucherReceipts.setPreferredSize(new Dimension(SUMMARY_TEXT_WIDTH,
					TEXT_FIELD_HEIGHT));
			mTxtVoucherReceipts.setEditable(false);
			mTxtVoucherReceipts.setFocusable(false);
			mTxtVoucherReceipts.setHorizontalAlignment(JTextField.RIGHT);
			
			
			JLabel labelAccountsReceivable = new JLabel(PaymentMode.Company.getDisplayText());
			labelAccountsReceivable.setOpaque(true);
			labelAccountsReceivable.setBorder(new EmptyBorder(2, 2, 2, 2));
			labelAccountsReceivable.setBackground(Color.LIGHT_GRAY);
			labelAccountsReceivable.setPreferredSize(new Dimension(SUMMARY_LABEL_WIDTH, SUMMARY_LABEL_HEIGHT));
			setLabelFont(labelAccountsReceivable);
			
			JLabel labelAdvanceCash = new JLabel("Advance("+ PaymentMode.Cash.getDisplayText() + ")");
			labelAdvanceCash.setOpaque(true);
			labelAdvanceCash.setBorder(new EmptyBorder(2, 2, 2, 2));
			labelAdvanceCash.setBackground(Color.LIGHT_GRAY);
			labelAdvanceCash.setPreferredSize(new Dimension(SUMMARY_LABEL_WIDTH, SUMMARY_LABEL_HEIGHT));
			setLabelFont(labelAdvanceCash);
			 
			JLabel labelAdvanceCard = new JLabel("Advance("+ PaymentMode.Card.getDisplayText() + ")");
			labelAdvanceCard.setOpaque(true);
			labelAdvanceCard.setBorder(new EmptyBorder(2, 2, 2, 2));
			labelAdvanceCard.setBackground(Color.LIGHT_GRAY);
			labelAdvanceCard.setPreferredSize(new Dimension(SUMMARY_LABEL_WIDTH, SUMMARY_LABEL_HEIGHT));
			setLabelFont(labelAdvanceCard);
			 
			mTxtAccountsReceivable = new JTextField();
			mTxtAccountsReceivable.setFont(PosFormUtil.getTextFieldFont());
			mTxtAccountsReceivable.setPreferredSize(new Dimension(SUMMARY_TEXT_WIDTH,
					TEXT_FIELD_HEIGHT));
			mTxtAccountsReceivable.setEditable(false);
			mTxtAccountsReceivable.setFocusable(false);
			mTxtAccountsReceivable.setHorizontalAlignment(JTextField.RIGHT);
			
			
			mTxtAdvanceCash = new JTextField();
			mTxtAdvanceCash.setFont(PosFormUtil.getTextFieldFont());
			mTxtAdvanceCash.setPreferredSize(new Dimension(SUMMARY_TEXT_WIDTH,
					TEXT_FIELD_HEIGHT));
			mTxtAdvanceCash.setEditable(false);
			mTxtAdvanceCash.setFocusable(false);
			mTxtAdvanceCash.setHorizontalAlignment(JTextField.RIGHT);
			
			
			mTxtAdvanceCard = new JTextField();
			mTxtAdvanceCard.setFont(PosFormUtil.getTextFieldFont());
			mTxtAdvanceCard.setPreferredSize(new Dimension(SUMMARY_TEXT_WIDTH,
					TEXT_FIELD_HEIGHT));
			mTxtAdvanceCard.setEditable(false);
			mTxtAdvanceCard.setFocusable(false);
			mTxtAdvanceCard.setHorizontalAlignment(JTextField.RIGHT);
			
			
			mTxtAdvanceOnline = new JTextField();
			mTxtAdvanceOnline.setFont(PosFormUtil.getTextFieldFont());
			mTxtAdvanceOnline.setPreferredSize(new Dimension(SUMMARY_TEXT_WIDTH,
					TEXT_FIELD_HEIGHT));
			mTxtAdvanceOnline.setEditable(false);
			mTxtAdvanceOnline.setFocusable(false);
			mTxtAdvanceOnline.setHorizontalAlignment(JTextField.RIGHT);
			
			if(!PosEnvSettings.getInstance().getUISetting().getMainMenuUISettings().getTillFormSettings().isShowSummaryOnly()){
				
				mShiftDetailPanel.add(receiptPanel);
				
			}
			
			
			mTxtOnlineReceipts  = new JTextField();
			mTxtOnlineReceipts.setFont(PosFormUtil.getTextFieldFont());
			mTxtOnlineReceipts.setPreferredSize(new Dimension(SUMMARY_TEXT_WIDTH,
					TEXT_FIELD_HEIGHT));
			mTxtOnlineReceipts.setEditable(false);
			mTxtOnlineReceipts.setFocusable(false);
			mTxtOnlineReceipts.setHorizontalAlignment(JTextField.RIGHT);
		 		
			
			receiptPanel.add(labelCashReceipts);
			receiptPanel.add(CardReceipts);
			receiptPanel.add(labelVoucherReceipts);
			receiptPanel.add(mTxtCashReceipts);
			receiptPanel.add(mTxtCardReceipts);
			receiptPanel.add(mTxtVoucherReceipts);
			receiptPanel.add(labelAccountsReceivable);
			receiptPanel.add(labelAdvanceCash);
			receiptPanel.add(labelAdvanceCard);
			receiptPanel.add(mTxtAccountsReceivable);	
			receiptPanel.add(mTxtAdvanceCash);	
			receiptPanel.add(mTxtAdvanceCard);	
 
		}
	 
		
		/**
		 * Create field for cash receipts and returned.
		 */
		private void setCashReturnControls() {

			final int width=SUMMARY_TEXT_WIDTH *3 + PANEL_CONTENT_H_GAP ;
			final int height=LABEL_HEIGHT +SUMMARY_LABEL_HEIGHT  + MESSAGE_PANEL_HEIGHT + PANEL_CONTENT_H_GAP ;
			JPanel cashReturnPanel=new JPanel();
			cashReturnPanel.setLayout(new FlowLayout(FlowLayout.LEFT,1,3));
			cashReturnPanel.setBorder(new EtchedBorder());
			cashReturnPanel.setPreferredSize(new Dimension(width,height));  
			
			JLabel labelMessage=new JLabel();
			labelMessage.setText("Returns");
			labelMessage.setHorizontalAlignment(SwingConstants.CENTER);		
			labelMessage.setPreferredSize(new Dimension(SUMMARY_TEXT_WIDTH *3 , MESSAGE_PANEL_HEIGHT-10));				
			labelMessage.setOpaque(true);
			labelMessage.setBackground(LABEL_BG_COLOR);
			labelMessage.setForeground(Color.WHITE);
			labelMessage.setFont(PosFormUtil.getSubHeadingFont());
			cashReturnPanel.add(labelMessage);
			
			JLabel labelCashReturned = new JLabel(PaymentMode.Cash.getDisplayText() + " Returned ");
			labelCashReturned.setOpaque(true);
			labelCashReturned.setBorder(new EmptyBorder(2, 2, 2, 2));
			labelCashReturned.setBackground(Color.LIGHT_GRAY);		
			labelCashReturned.setPreferredSize(new Dimension(SUMMARY_LABEL_WIDTH, SUMMARY_LABEL_HEIGHT));
			setLabelFont(labelCashReturned);
			
			JLabel labelCashOut = new JLabel("Cash Out (Card)");
			labelCashOut.setOpaque(true);
			labelCashOut.setBorder(new EmptyBorder(2, 2, 2, 2));
			labelCashOut.setBackground(Color.LIGHT_GRAY);
			labelCashOut.setPreferredSize(new Dimension(SUMMARY_LABEL_WIDTH, SUMMARY_LABEL_HEIGHT));
			setLabelFont(labelCashOut);
			 	
			JLabel labelVoucherBalance = new JLabel(PaymentMode.Coupon.getDisplayText());
			labelVoucherBalance.setBorder(new EmptyBorder(2, 2, 2, 2));
			labelVoucherBalance.setOpaque(true);
			labelVoucherBalance.setBackground(Color.LIGHT_GRAY);
			labelVoucherBalance.setPreferredSize(new Dimension(SUMMARY_LABEL_WIDTH, SUMMARY_LABEL_HEIGHT));
			setLabelFont(labelVoucherBalance);
			
			mTxtCashReturned = new JTextField();
			mTxtCashReturned.setFont(PosFormUtil.getTextFieldFont());
			mTxtCashReturned.setPreferredSize(new Dimension(SUMMARY_TEXT_WIDTH,
					TEXT_FIELD_HEIGHT));
			mTxtCashReturned.setEditable(false);
			mTxtCashReturned.setFocusable(false);
			mTxtCashReturned.setHorizontalAlignment(JTextField.RIGHT);
		 
			mTxtCashOut = new JTextField();
			mTxtCashOut.setFont(PosFormUtil.getTextFieldFont());
			mTxtCashOut.setPreferredSize(new Dimension(SUMMARY_TEXT_WIDTH,
					TEXT_FIELD_HEIGHT));
			mTxtCashOut.setEditable(false);
			mTxtCashOut.setFocusable(false);
			mTxtCashOut.setHorizontalAlignment(JTextField.RIGHT);
			
			mTxtVoucherBalance = new JTextField();
			mTxtVoucherBalance.setFont(PosFormUtil.getTextFieldFont());
			mTxtVoucherBalance.setPreferredSize(new Dimension(SUMMARY_TEXT_WIDTH,
					TEXT_FIELD_HEIGHT));
			mTxtVoucherBalance.setEditable(false);
			mTxtVoucherBalance.setFocusable(false);
			mTxtVoucherBalance.setHorizontalAlignment(JTextField.RIGHT);
  
			if(!PosEnvSettings.getInstance().getUISetting().getMainMenuUISettings().getTillFormSettings().isShowSummaryOnly()){
				
				mShiftDetailPanel.add(cashReturnPanel);
				
			}
			cashReturnPanel.add(labelCashReturned);
			cashReturnPanel.add(labelCashOut);
			cashReturnPanel.add(labelVoucherBalance);
			cashReturnPanel.add(mTxtCashReturned);
			cashReturnPanel.add(mTxtCashOut);
			cashReturnPanel.add(mTxtVoucherBalance);
		 
		}
	 
		/**
		 * Create field for cash receipts and returned.
		 */
		private void setSaleNRefundControls() {

			final int width=SUMMARY_TEXT_WIDTH *3 + PANEL_CONTENT_H_GAP ;
			final int height=LABEL_HEIGHT +SUMMARY_LABEL_HEIGHT  + MESSAGE_PANEL_HEIGHT + PANEL_CONTENT_H_GAP ;
			JPanel salesPanel=new JPanel();
			salesPanel.setLayout(new FlowLayout(FlowLayout.LEFT,1,3));
			salesPanel.setBorder(new EtchedBorder());
			salesPanel.setPreferredSize(new Dimension(width,height));  
			
			JLabel labelMessage=new JLabel();
			labelMessage.setText("Sales");
			labelMessage.setHorizontalAlignment(SwingConstants.CENTER);		
			labelMessage.setPreferredSize(new Dimension(SUMMARY_TEXT_WIDTH *3 , MESSAGE_PANEL_HEIGHT-10));				
			labelMessage.setOpaque(true);
			labelMessage.setBackground(LABEL_BG_COLOR);
			labelMessage.setForeground(Color.WHITE);
			labelMessage.setFont(PosFormUtil.getSubHeadingFont());
			salesPanel.add(labelMessage);
				
			JLabel labelPreviousAdvance = new JLabel("Previous Advance");
			labelPreviousAdvance.setOpaque(true);
			labelPreviousAdvance.setBackground(Color.LIGHT_GRAY);
			labelPreviousAdvance.setBorder(new EmptyBorder(2, 2, 2,2));
			labelPreviousAdvance.setPreferredSize(new Dimension(SUMMARY_LABEL_WIDTH, SUMMARY_LABEL_HEIGHT));
			setLabelFont(labelPreviousAdvance);
			
			JLabel labelTotalRefund = new JLabel("Refund ");
			labelTotalRefund.setOpaque(true);
			labelTotalRefund.setBorder(new EmptyBorder(2, 2, 2, 2));
			labelTotalRefund.setBackground(Color.LIGHT_GRAY);
			labelTotalRefund.setPreferredSize(new Dimension(SUMMARY_LABEL_WIDTH, SUMMARY_LABEL_HEIGHT));
			setLabelFont(labelTotalRefund);
			
			JLabel labelSales = new JLabel("Gross Sale ");
			labelSales.setOpaque(true);
			labelSales.setBorder(new EmptyBorder(2, 2, 2, 2));
			labelSales.setBackground(Color.LIGHT_GRAY);
			labelSales.setPreferredSize(new Dimension(SUMMARY_LABEL_WIDTH, SUMMARY_LABEL_HEIGHT));
			setLabelFont(labelSales);
		
			mTxtPreviousAdvance= new JTextField();
			mTxtPreviousAdvance.setFont(PosFormUtil.getTextFieldFont());
			mTxtPreviousAdvance.setPreferredSize(new Dimension(SUMMARY_TEXT_WIDTH,TEXT_FIELD_HEIGHT));
			mTxtPreviousAdvance.setEditable(false);
			mTxtPreviousAdvance.setFocusable(false);
			mTxtPreviousAdvance.setHorizontalAlignment(JTextField.RIGHT);
			
			mTxtTotalRefund = new JTextField();
			mTxtTotalRefund.setFont(PosFormUtil.getTextFieldFont());
			mTxtTotalRefund.setPreferredSize(new Dimension(SUMMARY_TEXT_WIDTH,
					TEXT_FIELD_HEIGHT));
			mTxtTotalRefund.setEditable(false);
			mTxtTotalRefund.setFocusable(false);
			mTxtTotalRefund.setHorizontalAlignment(JTextField.RIGHT);
  
			mTxtSales = new JTextField();
			mTxtSales.setFont(PosFormUtil.getTextFieldFont());
			mTxtSales.setPreferredSize(new Dimension(SUMMARY_TEXT_WIDTH,
					TEXT_FIELD_HEIGHT));
			mTxtSales.setEditable(false);
			mTxtSales.setFocusable(false);
			mTxtSales.setHorizontalAlignment(JTextField.RIGHT);

			
			if(!PosEnvSettings.getInstance().getUISetting().getMainMenuUISettings().getTillFormSettings().isShowSummaryOnly()){
				
				mShiftDetailPanel.add(salesPanel);
				
			}
			salesPanel.add(labelPreviousAdvance);
			salesPanel.add(labelTotalRefund);
			salesPanel.add(labelSales);
			salesPanel.add(mTxtPreviousAdvance);
			salesPanel.add(mTxtTotalRefund);
			salesPanel.add(mTxtSales);
			
		}
	 
	/**
	 * Create summary detail panel. 
	 */
	private void createSummaryDetailPanel() {

		final int panelWidth=LABEL_WIDTH + TXT_FIELD_WIDTH  + PANEL_CONTENT_H_GAP;
		mSummaryDetailPanel = new JPanel();
		 
		mSummaryDetailPanel.setSize(panelWidth, panelHeight);
		mSummaryDetailPanel.setPreferredSize(new Dimension(panelWidth, panelHeight));
		mSummaryDetailPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 1,
				PANEL_CONTENT_V_GAP));
		mSummaryDetailPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
		mSummaryDetailPanel.setBounds(mShiftDetailPanel.getX()+mShiftDetailPanel.getWidth(),mShiftDetailPanel.getY() , panelWidth, panelHeight);
		add(mSummaryDetailPanel);

		JLabel labelMessage=new JLabel();
		labelMessage.setText("Cash Summary");
		labelMessage.setHorizontalAlignment(SwingConstants.CENTER);		
		labelMessage.setPreferredSize(new Dimension(mSummaryDetailPanel.getWidth()-7, MESSAGE_PANEL_HEIGHT-10));				
		labelMessage.setOpaque(true);
		labelMessage.setBackground(LABEL_BG_COLOR);
		labelMessage.setForeground(Color.WHITE);
		labelMessage.setFont(PosFormUtil.getSubHeadingFont());
		mSummaryDetailPanel.add(labelMessage);

	}

	/**
	 * 
	 */
	private void setSummaryDetailpanel() {

		setOpeningCash();
		setNetCashRecieved();
		setSummaryCashOut();
		setVoucherBalanceReturned();
		setSummaryCashRefund();
		mTxtSummaryExpense=setExpence(mSummaryDetailPanel);
		setClosingCash();
		setActualCash();
		setCashVariance();
		setCashDeposit();
		setReferenceNumber();
		setReason();

	}



	/**
	 * Create field for Opening Cash.
	 */
	private void setOpeningCash() {
		JLabel labelOpeningCash = new JLabel("Opening Cash :");
		labelOpeningCash.setOpaque(true);
		labelOpeningCash.setBackground(Color.LIGHT_GRAY);
		labelOpeningCash.setBorder(new EmptyBorder(2, 2, 2,2));
		labelOpeningCash.setPreferredSize(new Dimension(LABEL_WIDTH, LABEL_HEIGHT));
		setLabelFont(labelOpeningCash);
		

		mTxtOpeningFloatAmount = new PosTouchableNumericField(mParent);

		mTxtOpeningCash = new JTextField();
		mTxtOpeningCash.setFont(PosFormUtil.getTextFieldFont());
		mTxtOpeningCash.setPreferredSize(new Dimension(TXT_FIELD_WIDTH,
				TEXT_FIELD_HEIGHT));
		mTxtOpeningCash.setEditable(false);
		mTxtOpeningCash.setFocusable(false);
		mTxtOpeningCash.setHorizontalAlignment(JTextField.RIGHT);
		
		if(!PosEnvSettings.getInstance().getUISetting().getMainMenuUISettings().getTillFormSettings().isShowSummaryOnly()){
			mSummaryDetailPanel.add(labelOpeningCash);
			mSummaryDetailPanel.add(mTxtOpeningCash);
		}
	}

	/**
	 * Create field for Net Cash Recieved.
	 */
	private void setNetCashRecieved() {
		JLabel labelNetCashRecieved = new JLabel("Net Cash Recieved :");
		labelNetCashRecieved.setOpaque(true);
		labelNetCashRecieved.setBackground(Color.LIGHT_GRAY);
		labelNetCashRecieved.setBorder(new EmptyBorder(2, 2, 2,2));
		labelNetCashRecieved.setPreferredSize(new Dimension(LABEL_WIDTH, LABEL_HEIGHT));
		setLabelFont(labelNetCashRecieved);
		

		mTxtNetCashRecieved = new JTextField();
		mTxtNetCashRecieved.setFont(PosFormUtil.getTextFieldFont());
		mTxtNetCashRecieved.setPreferredSize(new Dimension(TXT_FIELD_WIDTH,
				TEXT_FIELD_HEIGHT));
		mTxtNetCashRecieved.setEditable(false);
		mTxtNetCashRecieved.setFocusable(false);
		mTxtNetCashRecieved.setHorizontalAlignment(JTextField.RIGHT);
		
		if(!PosEnvSettings.getInstance().getUISetting().getMainMenuUISettings().getTillFormSettings().isShowSummaryOnly()){
			mSummaryDetailPanel.add(labelNetCashRecieved);
			mSummaryDetailPanel.add(mTxtNetCashRecieved);
		}
	}

	/**
	 * Create field for Cash Out.
	 */
	private void setSummaryCashOut() {
		JLabel labelSummaryCashOut = new JLabel("Cash Out (Card) :");
		labelSummaryCashOut.setOpaque(true);
		labelSummaryCashOut.setBackground(Color.LIGHT_GRAY);
		labelSummaryCashOut.setBorder(new EmptyBorder(2, 2, 2,2));
		labelSummaryCashOut.setPreferredSize(new Dimension(LABEL_WIDTH, LABEL_HEIGHT));
		setLabelFont(labelSummaryCashOut);
		

		mTxtSummaryCashOut = new JTextField();
		mTxtSummaryCashOut.setFont(PosFormUtil.getTextFieldFont());
		mTxtSummaryCashOut.setPreferredSize(new Dimension(TXT_FIELD_WIDTH,
				TEXT_FIELD_HEIGHT));
		mTxtSummaryCashOut.setEditable(false);
		mTxtSummaryCashOut.setFocusable(false);
		mTxtSummaryCashOut.setHorizontalAlignment(JTextField.RIGHT);
		
		if(!PosEnvSettings.getInstance().getUISetting().getMainMenuUISettings().getTillFormSettings().isShowSummaryOnly()){
			mSummaryDetailPanel.add(labelSummaryCashOut);
			mSummaryDetailPanel.add(mTxtSummaryCashOut);
		}
			
	}

	/**
	 * Create field for Voucher Bal. Returned.
	 */
	private void setVoucherBalanceReturned() {
		JLabel labelVoucherBalanceReturned = new JLabel("Voucher Bal. Returned :");
		labelVoucherBalanceReturned.setOpaque(true);
		labelVoucherBalanceReturned.setBackground(Color.LIGHT_GRAY);
		labelVoucherBalanceReturned.setBorder(new EmptyBorder(2, 2, 2,2));
		labelVoucherBalanceReturned.setPreferredSize(new Dimension(LABEL_WIDTH, LABEL_HEIGHT));
		setLabelFont(labelVoucherBalanceReturned);
		

		mTxtVoucherBalanceReturned = new JTextField();
		mTxtVoucherBalanceReturned.setFont(PosFormUtil.getTextFieldFont());
		mTxtVoucherBalanceReturned.setPreferredSize(new Dimension(TXT_FIELD_WIDTH,
				TEXT_FIELD_HEIGHT));
		mTxtVoucherBalanceReturned.setEditable(false);
		mTxtVoucherBalanceReturned.setFocusable(false);
		mTxtVoucherBalanceReturned.setHorizontalAlignment(JTextField.RIGHT);
		
		if(!PosEnvSettings.getInstance().getUISetting().getMainMenuUISettings().getTillFormSettings().isShowSummaryOnly()){
			mSummaryDetailPanel.add(labelVoucherBalanceReturned);
			mSummaryDetailPanel.add(mTxtVoucherBalanceReturned);
		}

	}

	/**
	 * Create field for Cash Refund.
	 */
	private void setSummaryCashRefund() {
		JLabel labelSummaryCashRefund = new JLabel("Cash Refund :");
		labelSummaryCashRefund.setOpaque(true);
		labelSummaryCashRefund.setBackground(Color.LIGHT_GRAY);
		labelSummaryCashRefund.setBorder(new EmptyBorder(2, 2, 2,2));
		labelSummaryCashRefund.setPreferredSize(new Dimension(LABEL_WIDTH, LABEL_HEIGHT));
		setLabelFont(labelSummaryCashRefund);
		

		mTxtSummaryCashRefund = new JTextField();
		mTxtSummaryCashRefund.setFont(PosFormUtil.getTextFieldFont());
		mTxtSummaryCashRefund.setPreferredSize(new Dimension(TXT_FIELD_WIDTH,
				TEXT_FIELD_HEIGHT));
		mTxtSummaryCashRefund.setEditable(false);
		mTxtSummaryCashRefund.setFocusable(false);
		mTxtSummaryCashRefund.setHorizontalAlignment(JTextField.RIGHT);
		
		if(!PosEnvSettings.getInstance().getUISetting().getMainMenuUISettings().getTillFormSettings().isShowSummaryOnly()){
			mSummaryDetailPanel.add(labelSummaryCashRefund);
			mSummaryDetailPanel.add(mTxtSummaryCashRefund);
		}
	}

	/**
	 * Create field for Cash Refund.
	 * @return 
	 */
	private JTextField setExpence(JPanel rootPanel) {

		JLabel label = new JLabel("Expense :");
		label.setOpaque(true);
		label.setBackground(Color.LIGHT_GRAY);
		label.setBorder(new EmptyBorder(2, 2, 2,2));
		label.setPreferredSize(new Dimension(LABEL_WIDTH, LABEL_HEIGHT));
		setLabelFont(label);
		

		final JTextField txtExpense = new JTextField();
		txtExpense.setFont(PosFormUtil.getTextFieldFont());
		txtExpense.setPreferredSize(new Dimension(TXT_FIELD_WIDTH,TEXT_FIELD_HEIGHT));
		txtExpense.setEditable(false);
		txtExpense.setFocusable(false);
		txtExpense.setHorizontalAlignment(JTextField.RIGHT);
		
		if(!PosEnvSettings.getInstance().getUISetting().getMainMenuUISettings().getTillFormSettings().isShowSummaryOnly()){
			rootPanel.add(label);
			rootPanel.add(txtExpense);
			

		}
		return txtExpense;
	}

	/**
	 * Create field for Closing Cash.
	 */
	private void setClosingCash() {
		
		JLabel labelClosingCash = new JLabel("Closing Cash :");
		labelClosingCash.setOpaque(true);
		labelClosingCash.setBackground(Color.LIGHT_GRAY);
		labelClosingCash.setBorder(new EmptyBorder(2, 2, 2,2));
		labelClosingCash.setPreferredSize(new Dimension(LABEL_WIDTH, LABEL_HEIGHT));
		setLabelFont(labelClosingCash);
		

		mTxtClosingCash = new JTextField();
		mTxtClosingCash.setFont(PosFormUtil.getTextFieldFont());
		mTxtClosingCash.setPreferredSize(new Dimension(TXT_FIELD_WIDTH,
				TEXT_FIELD_HEIGHT));
		mTxtClosingCash.setEditable(false);
		mTxtClosingCash.setFocusable(false);
		mTxtClosingCash.setHorizontalAlignment(JTextField.RIGHT);
		
		if(!PosEnvSettings.getInstance().getUISetting().getMainMenuUISettings().getTillFormSettings().isShowSummaryOnly()){
			mSummaryDetailPanel.add(labelClosingCash);
			mSummaryDetailPanel.add(mTxtClosingCash);
		}

	}

	/**
	 * Create field for Actual Cash.
	 */
	private void setActualCash() {
		JLabel labelActualCash = new JLabel(PosFormUtil.getMnemonicString("Actual Cash :", 'A'));
		labelActualCash.setOpaque(true);
		labelActualCash.setBackground(Color.LIGHT_GRAY);
		labelActualCash.setBorder(new EmptyBorder(2, 2, 2,2));
		labelActualCash.setPreferredSize(new Dimension(LABEL_WIDTH, LABEL_HEIGHT));
		setLabelFont(labelActualCash);
		mSummaryDetailPanel.add(labelActualCash);

		mTxtActualCash = new PosTouchableDigitalField(mParent,TXT_FIELD_WIDTH);
		mTxtActualCash.setTitle("Actual Cash ");
		mTxtActualCash.setMnemonic('A');
		mTxtActualCash.setFont(PosFormUtil.getTextFieldFont());
		mTxtActualCash.getTexFieldComponent().setDocument(PosNumberUtil.createDecimalDocument());
		mTxtActualCash.getTexFieldComponent().getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void removeUpdate(DocumentEvent e) {
				calculateVariance();
				mTxtActualCash.requestFocus();
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				calculateVariance();
				mTxtActualCash.requestFocus();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				calculateVariance();
				mTxtActualCash.requestFocus();
			}
		});
		mTxtActualCash.setListner(new IPosTouchableFieldListner() {
			
			@Override
			public void onValueSelected(Object value) {

				mTxtActualCash.setText( PosCurrencyUtil.format(PosNumberUtil.parseDoubleSafely(mTxtActualCash.getText())));
				mTxtCashDeposit.requestFocus();
				
			}
			
			@Override
			public void onReset() {
				// TODO Auto-generated method stub
				
			}
		});
		mSummaryDetailPanel.add(mTxtActualCash);

	}

	/**
	 * Calculate the variance here.
	 */
	private void calculateVariance() {
		final double variance = PosNumberUtil.parseDoubleSafely(mTxtActualCash.getText())-PosNumberUtil.parseDoubleSafely(mTxtClosingCash.getText());
		mTxtCashVariance.setText(PosCurrencyUtil.format(variance)); 
	}

	/**
	 * Create field for Cash Variance.
	 */
	private void setCashVariance() {
		JLabel labelCashVariance = new JLabel("Cash Variance :");
		labelCashVariance.setOpaque(true);
		labelCashVariance.setBackground(Color.LIGHT_GRAY);
		labelCashVariance.setBorder(new EmptyBorder(2, 2, 2,2));
		labelCashVariance.setPreferredSize(new Dimension(LABEL_WIDTH, LABEL_HEIGHT));
		setLabelFont(labelCashVariance);
		

		mTxtCashVariance = new JTextField();
		mTxtCashVariance.setFont(PosFormUtil.getTextFieldFont());
		mTxtCashVariance.setPreferredSize(new Dimension(TXT_FIELD_WIDTH,
				TEXT_FIELD_HEIGHT));
		mTxtCashVariance.setEditable(false);
		mTxtCashVariance.setHorizontalAlignment(JTextField.RIGHT);
		
		if(!PosEnvSettings.getInstance().getUISetting().getMainMenuUISettings().getTillFormSettings().isShowSummaryOnly()){
			mSummaryDetailPanel.add(labelCashVariance);
			mSummaryDetailPanel.add(mTxtCashVariance);
		}

	}

	/**
	 * 
	 */

	private void setCashDeposit() {
		
		JLabel labelCashDeposit = new JLabel(PosFormUtil.getMnemonicString("Amount Deposited :", 'D'));
		labelCashDeposit.setOpaque(true);
		labelCashDeposit.setBackground(Color.LIGHT_GRAY);
		labelCashDeposit.setBorder(new EmptyBorder(2, 2, 2,2));
		labelCashDeposit.setPreferredSize(new Dimension(LABEL_WIDTH, LABEL_HEIGHT));
		setLabelFont(labelCashDeposit);
		mSummaryDetailPanel.add(labelCashDeposit);

		mTxtCashDeposit = new PosTouchableDigitalField(mParent, TXT_FIELD_WIDTH);
		mTxtCashDeposit.setFont(PosFormUtil.getTextFieldFont());
		mTxtCashDeposit.setTitle("Amount Deposited");
		mTxtCashDeposit.setMnemonic('D');
		mTxtCashDeposit.getTexFieldComponent().setDocument(PosNumberUtil.createDecimalDocument());
		mTxtCashDeposit.setSelectedValue("");
		mTxtCashDeposit.setListner(new IPosTouchableFieldListner() {
			
			@Override
			public void onValueSelected(Object value) {

			
				mTxtCashDeposit.setText( PosCurrencyUtil.format(PosNumberUtil.parseDoubleSafely(mTxtCashDeposit.getText())));
				mTxtReferenceNumber.requestFocus();
				
			}
			
			@Override
			public void onReset() {
				// TODO Auto-generated method stub
				
			}
		});
		mSummaryDetailPanel.add(mTxtCashDeposit);
		
	 
	}
	//	private IPosTouchableFieldListner mFieldChangeListner=new PosTouchableFieldAdapter() {
	//		@Override
	//		public void onValueSelected(Object value) {
	//			doCashDepositFieldValidation(String.valueOf(value));
	//			
	//		}
	//	};	
	/**
	 * 
	 */
	private void setReferenceNumber() {
		JLabel labelReferenceNumber = new JLabel(PosFormUtil.getMnemonicString("Bank Deposit Slip :", 'B'));
		labelReferenceNumber.setOpaque(true);
		labelReferenceNumber.setBackground(Color.LIGHT_GRAY);
		labelReferenceNumber.setBorder(new EmptyBorder(2, 2, 2,2));
		labelReferenceNumber.setPreferredSize(new Dimension(LABEL_WIDTH, LABEL_HEIGHT));
		setLabelFont(labelReferenceNumber);
		mSummaryDetailPanel.add(labelReferenceNumber);

		mTxtReferenceNumber = new PosTouchableTextField(mParent, TXT_FIELD_WIDTH);
		mTxtReferenceNumber.setTitle("Bank deposit slip");
		mTxtReferenceNumber.setMnemonic('B');
		mTxtReferenceNumber.setFont(PosFormUtil.getTextFieldFont());
		mTxtReferenceNumber.setListner(new IPosTouchableFieldListner() {
			
			@Override
			public void onValueSelected(Object value) {

				mTxtReason.requestFocus();
			}
			
			@Override
			public void onReset() {
				// TODO Auto-generated method stub
				
			}
		});
		mSummaryDetailPanel.add(mTxtReferenceNumber);
	}


	/**
	 * @param valueOf
	 */
	protected boolean doCashDepositFieldValidation() {
		boolean valid = false;
		if(PosNumberUtil.parseDoubleSafely(mTxtCashDeposit.getText())>PosNumberUtil.parseDoubleSafely(mTxtActualCash.getText())){
			PosFormUtil.showInformationMessageBox(mParent, "You can not enter an amount greater than actual amount");
			mTxtCashDeposit.setText("");
			valid = true;
		}
		return valid; 

	}

	/**
	 * Create field for Reason.
	 */
	private void setReason() {
		
		final int width=(PosEnvSettings.getInstance().getUISetting().getMainMenuUISettings().getTillFormSettings().isShowSummaryOnly()? LAYOUT_WIDTH_SUMMARY-7: mSummaryDetailPanel.getWidth()-7) ;
		JLabel labelMessage=new JLabel();
		labelMessage.setText("Remarks.");
		labelMessage.setHorizontalAlignment(SwingConstants.CENTER);		
		labelMessage.setPreferredSize(new Dimension(width, MESSAGE_PANEL_HEIGHT-10));				
		labelMessage.setOpaque(true);
		labelMessage.setBackground(LABEL_BG_COLOR);
		labelMessage.setForeground(Color.WHITE);
		labelMessage.setFont(PosFormUtil.getLabelFontSmall());
		

		mTxtReason = new JTextArea(2,21);
		//		mTxtReason.setPreferredSize(new Dimension(mSummaryDetailPanel.getWidth()-24,62));
		mTxtReason.setLineWrap(true);
		mTxtReason.setWrapStyleWord(false);
		mTxtReason.setFont(PosFormUtil.getTextFieldFont());
		mTxtReason.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseClicked(MouseEvent e) {
				PosFormUtil.showSoftKeyPad(mParent,mTxtReason, null);
			}
		});

		JScrollPane scrollPane=new JScrollPane(mTxtReason);
		scrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(15,0));
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
//		scrollPane.setSize(INNER_PANEL_WIDTH,
//				scrollHeight);
		scrollPane.setBorder(new LineBorder(new Color(134, 184, 232)));
		
		if(PosEnvSettings.getInstance().getUISetting().getMainMenuUISettings().getTillFormSettings().isShowSummaryOnly()){
			
//			labelMessage.setBounds(mShiftDetailPanel.getX() +PANEL_CONTENT_V_GAP*2, 
//					mShiftDetailPanel.getY() +mShiftDetailPanel.getHeight() +PANEL_CONTENT_V_GAP ,
//					width, MESSAGE_PANEL_HEIGHT-10);
			
			add(labelMessage);
			scrollPane.setPreferredSize(new Dimension(width,TXT_AREA_FIELD_HEIGHT_SUMMARY));
			
//			scrollPane.setBorder(null);
//			scrollPane.setBounds(mShiftDetailPanel.getX() +PANEL_CONTENT_V_GAP*2, 
//					labelMessage.getY() +labelMessage.getHeight() +PANEL_CONTENT_V_GAP ,
//					width, TXT_AREA_FIELD_HEIGHT_DET);
			add(scrollPane);
		}else{
			mSummaryDetailPanel.add(labelMessage);
			scrollPane.setPreferredSize(new Dimension(width,TXT_AREA_FIELD_HEIGHT_DET));
			
			mSummaryDetailPanel.add(scrollPane);
		}

	}



	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.indocosmo.pos.components.terminal.shift.PosShiftBasePanel#validateShift
	 * ()
	 */
	/* (non-Javadoc)
	 * @see com.indocosmo.pos.components.terminal.shift.PosShiftBasePanel#onGotFocus()
	 */
	@Override
	public void onGotFocus() {
		mTxtCashDeposit.requestFocus();
	}

	@Override
	protected boolean validateShift() {
		// TODO Auto-generated method stub
		return true;
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.pos.components.terminal.shift.PosShiftBasePanel#getCashierShiftDetails()
	 */
	@Override
	public BeanCashierShift getCashierShiftDetails() {
		// TODO Auto-generated method stub
		return super.getCashierShiftDetails();
	}

	/**
	 * @return
	 * Closing the shift here, 
	 * First close all the joint cashier shifts 
	 * And last close the actual open shift(Open till)
	 */
	public boolean closeShift(){
		
		PosShiftSummaryProvider shiftSummaryProvider = new PosShiftSummaryProvider();
		boolean isSuccess = false;
		Map<String, BeanCashierShift> mOpenCashierShifts = mCashierShiftProvider.getAllOpenCashierShifts();
		for(BeanCashierShift shift: mOpenCashierShifts.values()){
			isSuccess =  mCashierShiftProvider.closeJoinedShift(shift);
		}
		if(isSuccess){
			BeanShiftCloseSummary shiftSummary = setValuesToShiftSummary();
			isSuccess = shiftSummaryProvider.insertShiftSummary(shiftSummary);
		}
		return isSuccess;
	}
	
	/**
	 * 
	 */
	public BeanShiftCloseSummary setValuesToShiftSummary(){
		BeanShiftCloseSummary shiftSummary = new BeanShiftCloseSummary();
		
		shiftSummary.setShiftItem(mShopShiftInfo);
		shiftSummary.setCashierInfo(mCashierInfo);
		shiftSummary.setOpeningDate(mTxtShiftStartDate.getText());
		shiftSummary.setOpeningTime(mTxtShiftStartTime.getText());
		shiftSummary.setClosingDate(ShiftCloseDate);
		shiftSummary.setClosingTime(ShiftCloseTime);

		shiftSummary.setOpeningFloat(Double.parseDouble(mTxtOpeningFloatAmount.getText()));
		shiftSummary.setCashReceipts(Double.parseDouble(mTxtCashReceipts.getText()));
		shiftSummary.setCardReceipts(Double.parseDouble(mTxtCardReceipts.getText()));
		shiftSummary.setOnlineReceipts(Double.parseDouble(mTxtOnlineReceipts.getText()));
		
		shiftSummary.setCashAdvance(Double.parseDouble(mTxtAdvanceCash.getText()));
		shiftSummary.setCardAdvance(Double.parseDouble(mTxtAdvanceCard.getText()));
		shiftSummary.setOnlineAdvance(Double.parseDouble(mTxtAdvanceOnline.getText()));
		
		shiftSummary.setVoucherReceipts(Double.parseDouble(mTxtVoucherReceipts.getText()));
		shiftSummary.setAccountsReceivable(Double.parseDouble(mTxtAccountsReceivable.getText()));

		shiftSummary.setCashReturned(Double.parseDouble(mTxtCashReturned.getText()));
		shiftSummary.setVoucherBalance(Double.parseDouble(mTxtVoucherBalance.getText()));
		shiftSummary.setCashOut(Double.parseDouble(mTxtCashOut.getText()));
		shiftSummary.setCashRefund(mRefundByCash);
		shiftSummary.setCardRefund(mRefundByCard);
		shiftSummary.setVoucherRefund(mRefundByCoupon);
		shiftSummary.setAccountsRefund(mRefundByCompany);
		shiftSummary.setOnlineRefund(mRefundByOnline);
		shiftSummary.setTotalRefund(Double.parseDouble(mTxtTotalRefund.getText()));
		shiftSummary.setNetSale(Double.parseDouble(mTxtSales.getText()));
		shiftSummary.setClosedOrderAdvance(Double.parseDouble(mTxtPreviousAdvance.getText()));
		shiftSummary.setExpense(Double.parseDouble(mTxtSummaryExpense.getText()));
		
		shiftSummary.setNetCash(Double.parseDouble(mTxtNetCashRecieved.getText()));
		shiftSummary.setVoucherBalanceReturned(Double.parseDouble(mTxtVoucherBalanceReturned.getText()));
		shiftSummary.setClosingCash(Double.parseDouble(mTxtClosingCash.getText()));
		shiftSummary.setActualCash(PosNumberUtil.parseDoubleSafely(mTxtActualCash.getText()));
		shiftSummary.setVariance(PosNumberUtil.parseDoubleSafely(mTxtCashVariance.getText()));
		shiftSummary.setCashDeposit(PosNumberUtil.parseDoubleSafely(mTxtCashDeposit.getText()));
		shiftSummary.setReferenceSlipNumber(mTxtReferenceNumber.getText());
		double cashRenmaining = PosNumberUtil.parseDoubleSafely(mTxtActualCash.getText())-PosNumberUtil.parseDoubleSafely(mTxtCashDeposit.getText());
		shiftSummary.setCashRemaining(cashRenmaining);

		return shiftSummary;

	}
	
	/**
	 * @return
	 */
	private boolean validateActualCashField() {
		boolean valid = true;
		if(mTxtActualCash.getText().trim().equalsIgnoreCase("")||mTxtActualCash.getText()==null){
			valid = false;
			PosFormUtil.showErrorMessageBox(mParent, "Please Enter Actual cash.");
		}else if(PosEnvSettings.getInstance().getUISetting().getMainMenuUISettings().getTillFormSettings().isConfirmBankDetails()){ 
			
			if(mTxtCashDeposit.getText().trim().equalsIgnoreCase("")||mTxtCashDeposit.getText()==null){
				valid = false;
				PosFormUtil.showErrorMessageBox(mParent, "Please Enter Bank deposit amount.");
			}else if(mTxtReferenceNumber.getText().trim().equalsIgnoreCase("")||mTxtReferenceNumber.getText()==null){
				valid = PosFormUtil.showQuestionMessageBox(mParent, MessageBoxButtonTypes.YesNo,"Bank slip number is blank. Do you want to continue?",null)==MessageBoxResults.Yes;
				//			PosFormUtil.showErrorMessageBox(null, "Please Enter Bank slip number.");
			}
		}else if(doCashDepositFieldValidation()){
			valid = false;
		}
		return valid;
	}
	/* (non-Javadoc)
	 * @see com.indocosmo.pos.components.terminal.shift.PosShiftBasePanel#onSave()
	 */
	@Override
	public boolean onSave() {
		
		boolean res=false;
		
		if(closeShift()){

		
			if (PosAccessPermissionsUtil.validateAccess(mParent, ((PosShiftClosingForm)mParent).getActiveUser().getUserGroupId(),"till_summary_report",true) ){
				printCloshShiftSummary();
			}
			res= super.onSave();
		}
		return res;
	}
	
	/**
	 * @return
	 */
	private void printCloshShiftSummary(){

		try {

			final PosDeviceReceiptPrinter printer=PosDeviceManager.getInstance().getReceiptPrinter();

			if(printer!=null && printer.isDeviceInitialized() && PosEnvSettings.getInstance().getPrintSettings().getPrintSummaryAtShitClosing()!=EnablePrintingOption.NO){

				boolean doPrint=true;			

				if(PosEnvSettings.getInstance().getPrintSettings().getPrintSummaryAtShitClosing()==EnablePrintingOption.ASK)
					doPrint=(PosFormUtil.showQuestionMessageBox(mParent ,MessageBoxButtonTypes.YesNo,"Do you want to print the shift summary?",	null)==MessageBoxResults.Yes);

				if(doPrint)
					printSummaryReport(true);
			}
			
		} catch (Exception e) {

			PosLog.write(this, "printCloshShiftSummary", e);
			PosFormUtil.showErrorMessageBox(mParent, "Failed to print the shift summary");
		}
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.pos.forms.components.terminal.shift.PosShiftBasePanel#onValidate()
	 */
	@Override
	public boolean onValidate() {

		if(validateActualCashField())
			return super.onValidate();
		return false;
	}
	/* (non-Javadoc)
	 * @see com.indocosmo.pos.components.terminal.shift.PosShiftBasePanel#printSummaryReport()
	 */ 
	public void printSummaryReport(boolean isClosed) {
		
			BeanShiftCloseSummary shiftSummary=setValuesToShiftSummary();
			doPrintReport(shiftSummary,isClosed);
		 
		
	}


	/*
	 * @param BeanShiftSummaryReport
	 */
	public  void doPrintReport(final BeanShiftCloseSummary shiftSummary ,final Boolean isClosed ) {

		SwingWorker<Boolean, String> swt = new SwingWorker<Boolean, String>() {
			@Override
			protected Boolean doInBackground() throws Exception {
				
				try {
					final PosDeviceReceiptPrinter printer=PosDeviceManager.getInstance().getReceiptPrinter();
 
					if(!PosDeviceManager.getInstance().hasReceiptPrinter()) 
						throw new PrinterException("No Receipt printer configured. Please configure printer.");
						
					if(printer==null) 
						throw new PrinterException("Receipt printer not available. Please check printer");
					
					final PosShiftClosingReport report =new PosShiftClosingReport( shiftSummary);
					report.initPrint();
					report.setIsShiftClosed(isClosed);
					printer.print(report,false);
					
				} catch (Exception e) {
					
					PosLog.write(PosShiftClosePanel.this, "doPrintReport", e);
					PosFormUtil.closeBusyWindow();
					PosFormUtil.showErrorMessageBox(null, e.getMessage());
				}
				return true;
			}
			
			@Override
			protected void done() {
				PosFormUtil.closeBusyWindow();
			}
		};
		
		swt.execute();
		//PosFormUtil.showBusyWindow(super.this,"Please wait ...");
	}


}
