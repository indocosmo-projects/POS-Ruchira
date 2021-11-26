package com.indocosmo.pos.forms;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.RootPaneContainer;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.MatteBorder;

import com.indocosmo.pos.common.PosEnvSettings;
import com.indocosmo.pos.common.PosEnvSettings.ApplicationType;
import com.indocosmo.pos.common.PosLog;
import com.indocosmo.pos.common.beans.BeanBillPaymentSummaryInfo;
import com.indocosmo.pos.common.enums.EnablePrintingOption;
import com.indocosmo.pos.common.enums.PosOrderServiceTypes;
import com.indocosmo.pos.common.enums.split.SplitBasedOn;
import com.indocosmo.pos.common.utilities.HmsUtil;
import com.indocosmo.pos.common.utilities.PosCurrencyUtil;
import com.indocosmo.pos.common.utilities.PosDateUtil;
import com.indocosmo.pos.common.utilities.PosFormUtil;
import com.indocosmo.pos.common.utilities.PosNumberUtil;
import com.indocosmo.pos.common.utilities.PosOrderSplitUtil;
import com.indocosmo.pos.common.utilities.PosOrderUtil;
import com.indocosmo.pos.common.utilities.PosResUtil;
import com.indocosmo.pos.common.utilities.PosStringUtil;
import com.indocosmo.pos.data.beans.BeanDiscount;
import com.indocosmo.pos.data.beans.BeanOrderDiscount;
import com.indocosmo.pos.data.beans.BeanOrderHeader;
import com.indocosmo.pos.data.beans.BeanOrderHeader.PaymentProcessStatus;
import com.indocosmo.pos.data.beans.BeanOrderHeader.PosOrderStatus;
import com.indocosmo.pos.data.beans.BeanOrderPayment;
import com.indocosmo.pos.data.beans.BeanOrderPaymentHeader;
import com.indocosmo.pos.data.beans.BeanOrderSplit;
import com.indocosmo.pos.data.beans.BeanPayModeBase.PaymentMode;
import com.indocosmo.pos.data.beans.BeanPaymentModes;
import com.indocosmo.pos.data.beans.BeanTax;
import com.indocosmo.pos.data.providers.shopdb.PosCustomerTypeProvider;
import com.indocosmo.pos.data.providers.shopdb.PosDiscountItemProvider;
import com.indocosmo.pos.data.providers.shopdb.PosInvoiceProvider;
import com.indocosmo.pos.data.providers.shopdb.PosOrderHdrProvider;
import com.indocosmo.pos.data.providers.shopdb.PosOrderSplitProvider;
import com.indocosmo.pos.data.providers.shopdb.PosPaymentModesProvider;
import com.indocosmo.pos.data.providers.shopdb.PosStockInHdrProvider;
import com.indocosmo.pos.forms.components.buttons.PosButton;
import com.indocosmo.pos.forms.components.buttons.listners.IPosButtonListner;
import com.indocosmo.pos.forms.components.buttons.listners.PosButtonListnerAdapter;
import com.indocosmo.pos.forms.components.payment.PosCardPaymentPanel;
import com.indocosmo.pos.forms.components.payment.PosCardPaymentPanel.CardPaymentException;
import com.indocosmo.pos.forms.components.payment.PosCashPaymentPanel;
import com.indocosmo.pos.forms.components.payment.PosCouponPaymentPanel;
import com.indocosmo.pos.forms.components.payment.PosCreditCustomerPaymentPanel;
import com.indocosmo.pos.forms.components.payment.PosDiscountPaymentPanel;
import com.indocosmo.pos.forms.components.payment.PosPaymentBasePanel;
import com.indocosmo.pos.forms.components.payment.PosSplitPartPaymentAdjustPanel;
import com.indocosmo.pos.forms.components.payment.listners.IPosPaymentPanelListner;
import com.indocosmo.pos.forms.components.tab.PosTab;
import com.indocosmo.pos.forms.components.tab.PosTabControl;
import com.indocosmo.pos.forms.components.tab.listner.IPosTabControlListner;
import com.indocosmo.pos.forms.listners.IPosBillPrintFormListner;
import com.indocosmo.pos.forms.listners.IPosFormEventsListner;
import com.indocosmo.pos.forms.listners.IPosPaymentFormListner;
import com.indocosmo.pos.forms.listners.adapters.PosBillPrintFormAdapter;
import com.indocosmo.pos.forms.messageboxes.PosMessageBoxForm.MessageBoxButtonTypes;
import com.indocosmo.pos.forms.messageboxes.PosMessageBoxForm.MessageBoxResults;
import com.indocosmo.pos.forms.split.PosOrderSplitForm;
import com.indocosmo.pos.process.sync.SynchronizeToServer;
import com.indocosmo.pos.reports.receipts.PosReceipts;
import com.indocosmo.pos.terminal.devices.PosDeviceManager;
import com.indocosmo.pos.terminal.devices.poledisplay.PosDevicePoleDisplay;
import com.indocosmo.pos.terminal.devices.printer.PosDeviceKitchenPrinter;
import com.indocosmo.pos.terminal.devices.printer.printerexceptions.PrinterException;

@SuppressWarnings("serial")
public class PosPaymentForm extends JDialog {

	private static final int PANEL_CONTENT_H_GAP = 8;
	private static final int PANEL_CONTENT_V_GAP = 4;
	private static final int TITLE_PANEL_HEIGHT = 60;
	private static final int CONTENT_PANEL_HEIGHT = 453;
	private static final int BOTTOM_PANEL_HEIGHT = 80;
	private static final int IMAGE_BUTTON_WIDTH = 150;
	private static final int IMAGE_BUTTON_HEIGHT = 60;
	private static final int LABEL_VALUE_WIDTH = 85;
	private static final int LABEL_VALUE_HEIGHT = 40;
	private static final int LABEL_TITLE_WIDTH = 100;
	private static final int LABEL_TITLE_HEIGHT = LABEL_VALUE_HEIGHT;
	private static final int BILL_DETAIL_PANEL_HEIGHT = (LABEL_VALUE_HEIGHT+PANEL_CONTENT_V_GAP)*3;
	
	private static final int FORM_HEIGHT = TITLE_PANEL_HEIGHT
			+ BILL_DETAIL_PANEL_HEIGHT + CONTENT_PANEL_HEIGHT
			+ BOTTOM_PANEL_HEIGHT + PANEL_CONTENT_V_GAP * 6;
	private static final int FORM_WIDTH =(LABEL_TITLE_WIDTH+LABEL_VALUE_WIDTH )*4;
	private static final String MAKE_PAYMENT_IMAGE_ITEM_BUTTON = "make_pay.png";
	private static final String MAKE_PAYMENT_IMAGE_ITEM_BUTTON_TOUCH = "make_pay_hover.png";

	private static final String REMARKS_IMAGE_ITEM_BUTTON = "dlg_remarks.png";
	private static final String REMARKS_IMAGE_ITEM_BUTTON_TOUCH = "dlg_remarks_touch.png";

	private static final String IMAGE_BUTTON_CANCEL = "item_edit_cancel.png";
	private static final String IMAGE_BUTTON_CANCEL_TOUCH = "item_edit_cancel_touch.png";

	private static final Color PANEL_BG_COLOR = new Color(78, 128, 188);
	private static final Color LABEL_BG_COLOR = new Color(78, 128, 188);
	private static final Color LABEL_FG_COLOR = Color.WHITE;
	private static final Color PANEL_BILL_DETAIL_BG_COLOR = PosOrderEntryForm.PANEL_BG_COLOR;

	private JPanel mContentPane;
	private JPanel mBottomPanel;

	private JPanel mBillDetailsPanel;
	private JPanel mTitlePanel;

	private PosTabControl mTabControl;
	private ArrayList<PosTab> mTabs;
	private ArrayList<PosTab> mTabsForPayment;

	private JLabel mlabelTitle;
	private JLabel mBillTotal;
	private JLabel mTenderedAmount;
	private JLabel mBillBalanceAmount;
	private JLabel mCashValue;
	private JLabel mCardValue;
	private JLabel mCouponValue;
	private JLabel mCompanyValue;
	private JLabel mDiscountValue;
	private JLabel mExtraCharges;
	private JLabel mAdvanceAmount;
	
	private PosButton mButtonMakePayment;
	private PosButton mButtonCacel;
	private PosButton mButtonReset;
	private PosButton mButtonRemarks;

	private IPosPaymentFormListner mListner;

	private PosCashPaymentPanel mCashPaymentPanel;
	private PosSplitPartPaymentAdjustPanel mSplitAdjustmentPanel;
	private PosCardPaymentPanel mCardPaymentPanel;
	private PosCreditCustomerPaymentPanel mCompanyPaymentPanel;
	private PosCouponPaymentPanel mCouponPaymentPanel;
	private PosPaymentBasePanel mActievePayment;
	private PosDiscountPaymentPanel mDiscountPaymentPanel;

	private PaymentMode mSelectedPaymentMode = PaymentMode.Cash;
	private PaymentMode mActualPaymentMode = PaymentMode.Cash;
	
	private JDialog mParent;

	private PosOrderHdrProvider mOrderBillProvider;
	private PosDevicePoleDisplay mPosPoleDisplay;

	private ArrayList<BeanOrderSplit> mSelectedSplitItems;
	private BeanOrderSplit mSplitForPayment;

	private BeanBillPaymentSummaryInfo mPaymentSummary;
	private BeanOrderDiscount mBillDiscount;

	private BeanOrderHeader mOrderHeaderForPayment;
	private BeanOrderHeader mOrderHeaderActual;

//	private boolean mPrintRecipt = false;
	private boolean mIsPartialPayment=false;
	private double mTaxBeforeBillDiscount;
	private double mCompletedPayment;
	private double mBillTotalAmount=0;
	private double mRoundAdjustMentAmount = 0;
	private boolean isOrderSaved=false;
	private String mRemarks="";
	
	private final static Color PAYMENT_DETAIL_TITLE_BG_COLOR = Color.PINK;
	private final static Color PAYMENT_DETAIL_VALUE_BG_COLOR = Color.GREEN;
	private final static Color PAYMENT_DETAIL_BALANCE_BG_COLOR = Color.GREEN;

	public PosPaymentForm(final JDialog parent, PaymentMode paymode) {
		
		setActualPaymentMode(paymode);
		
		mParent = parent;
		mOrderBillProvider = new PosOrderHdrProvider();
		mPosPoleDisplay = PosDevicePoleDisplay.getInstance();
		
		initControls();
		this.addWindowListener(new WindowAdapter() {

			/* (non-Javadoc)
			 * @see java.awt.event.WindowAdapter#windowActivated(java.awt.event.WindowEvent)
			 */
			@Override
			public void windowActivated(WindowEvent e) {

				setPoleDisplay(getFinalBillTotal(), 0);
				super.windowActivated(e);
			}
		});
	}


	/**
	 * 
	 */
	private void buildPaymentSummaryInfo(double billDue){

		mPaymentSummary=new BeanBillPaymentSummaryInfo();
		mPaymentSummary.setNumber(mOrderHeaderForPayment.getOrderId());
		mPaymentSummary.setAmount(PosOrderUtil.isDeliveryService(mOrderHeaderActual)?
				mOrderHeaderActual.getTotalAmount(): billDue);
		mPaymentSummary.setPartAmount(mCompletedPayment);
		mPaymentSummary.setPartialPayment(mIsPartialPayment);

		/**
		 * 
		 * In case of split and there is adjustment, need to print it.
		 * Set the adjustment amount to show in print
		 * 
		 */
		if(mSplitForPayment!=null )
			mPaymentSummary.setSplitPayAdjustment(mSplitForPayment.getAdjustAmount());

	}


	/**
	 * Builds the order header with summary.
	 */
	private void setBillTaxAmounts(BeanOrderHeader order) {
		
			double billTax1 = 0;
			double billTax2 = 0;
			double billTax3 = 0;
			double billGST = 0;
			double billServiceTax = 0;
			
			if (PosEnvSettings.getInstance().getBillParams().getTax() != null) {
				BeanTax billTax = PosEnvSettings.getInstance().getBillParams()
						.getTax();
				double totalBill = getBillTotalAmount();
				if (billTax.isTaxOneApplicable())
					billTax1 = (totalBill * billTax.getTaxOnePercentage()) / 100;
				if (billTax.isTaxTwoApplicable())
					billTax2 = (totalBill * billTax.getTaxTwoPercentage()) / 100;
				if (billTax.isTaxThreeApplicable())
					billTax3 = (totalBill * billTax.getTaxThreePercentage()) / 100;
				if (billTax.isGSTDefined())
					billGST = (totalBill * billTax.getGSTPercentage()) / 100;
				if (billTax.isServiceTaxApplicable())
					billServiceTax = (totalBill * billTax
							.getServiceTaxPercentage()) / 100;
			}

//			mOrderHeader.setDetailTotal(mOrderBill.getBillItemTotal());
			
//			mOrderHeader.setTotalTax1(mOrderBill.getBillTax1() + billTax1);
			order.setTotalTax1(order.getTotalTax1() + billTax1);
			
//			mOrderHeader.setTotalTax2(mOrderBill.getBillTax2() + billTax2);
			order.setTotalTax2(order.getTotalTax2() + billTax2);
			
//			mOrderHeader.setTotalTax3(mOrderBill.getBillTax3() + billTax3);
			order.setTotalTax3(order.getTotalTax3() + billTax3);
			
//			mOrderHeader.setTotalGST(mOrderBill.getBillGST() + billGST);
			order.setTotalGST(order.getTotalGST() + billGST);
			
//			mOrderHeader.setTotalServiceTax(mOrderBill.getBillServiceTax()+ billServiceTax);
			order.setTotalServiceTax(order.getTotalServiceTax() + billServiceTax);
			
//			mOrderHeader.setTotalDetailDiscount(mOrderBill.getBillDiscount());
			
//			mOrderHeader.setTotalAmount(mOrderBill.getBillTotal() + billTax1
//					+ billTax2 + billTax3 + billGST + billServiceTax);
			order.setTotalAmount(order.getTotalAmount() + billTax1
					+ billTax2 + billTax3 + billGST + billServiceTax);

//			if (mOrderHeaderForPayment != null
//					&& mOrderHeaderForPayment.getOrderPaymentItems() != null ) {
//				mCompletedPayment = PosOrderUtil
//						.getTotalPaidAmount(mOrderHeaderForPayment)
//						+ mOrderHeaderForPayment.getBillDiscountAmount();
//			}

//		} catch (Exception e) {
//			
//			PosLog.write(this, "buildOrderHeaders", e);
//			PosFormUtil.showErrorMessageBox(mParent,
//					"Failed create payment. Please check log for details");
//		}
	}
	
	/**
	 * @param isPartial
	 */
	private void setPartialPayment(boolean isPartial){
		
		this.mIsPartialPayment=isPartial;
	}
	
	/**
	 * Sets the payment details for the order if there is any payment were already done.
	 * @param orderObject
	 * @throws SQLException 
	 */
	private void setAlreadyPayedPayments(BeanOrderHeader orderObject) throws SQLException{
	
		orderObject.setOrderPaymentItems(null);
		orderObject.setOrderPaymentItems(mOrderBillProvider.getOrderPayments(orderObject));
	}
	

	/**
	 * @param amount
	 */
	public void setSelectedAmount(int amount) {
		if (mSelectedPaymentMode != PaymentMode.Cash )
			return;
		// mSelectedAmount=amount;
		mCashPaymentPanel.setTenderAmount(amount);
	}

	/**
	 * 
	 */
	private void initControls() {

		this.setSize(FORM_WIDTH, FORM_HEIGHT);

		mContentPane = new JPanel();
		mContentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		mContentPane.setLayout(null);
		this.setContentPane(mContentPane);

		mTitlePanel = new JPanel();
		mTitlePanel.setBounds(PANEL_CONTENT_H_GAP, PANEL_CONTENT_V_GAP*2,
				FORM_WIDTH - PANEL_CONTENT_H_GAP * 2, TITLE_PANEL_HEIGHT);
		mTitlePanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
		mTitlePanel.setBackground(PANEL_BG_COLOR);

		this.add(mTitlePanel);

		mBillDetailsPanel = new JPanel();
		mBillDetailsPanel.setBounds(PANEL_CONTENT_H_GAP, mTitlePanel.getY()
				+ mTitlePanel.getHeight() + PANEL_CONTENT_V_GAP, FORM_WIDTH
				- PANEL_CONTENT_H_GAP * 2,  BILL_DETAIL_PANEL_HEIGHT);
		mBillDetailsPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
		mBillDetailsPanel.setBackground(PANEL_BILL_DETAIL_BG_COLOR);
		mBillDetailsPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
		mBillDetailsPanel.setLayout(null);
		this.add(mBillDetailsPanel);

		mTabControl = new PosTabControl(FORM_WIDTH - PANEL_CONTENT_H_GAP * 2,
				CONTENT_PANEL_HEIGHT);
		;
		mTabControl.setLocation(PANEL_CONTENT_H_GAP, mBillDetailsPanel.getY()
				+ mBillDetailsPanel.getHeight() + PANEL_CONTENT_V_GAP);
		mTabControl.setLocation(PANEL_CONTENT_H_GAP, mBillDetailsPanel.getY()
				+ mBillDetailsPanel.getHeight() + PANEL_CONTENT_V_GAP);
		mTabControl.setListner(new IPosTabControlListner() {

			@Override
			public void onTabSelected(int index) {
				mActievePayment = (PosPaymentBasePanel) mTabs.get(index);
				// mlabelTitle.setText("Pay By " +
				// mActievePayment.getTabCaption());
			}
		});
		this.add(mTabControl);

		mBottomPanel = new JPanel();
		mBottomPanel.setBounds(PANEL_CONTENT_H_GAP, mTabControl.getY()
				+ mTabControl.getHeight(),
				FORM_WIDTH - PANEL_CONTENT_H_GAP * 2, BOTTOM_PANEL_HEIGHT);
		mBottomPanel.setLayout(new FlowLayout());
		mBottomPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
		mBottomPanel.setOpaque(false);
		this.add(mBottomPanel);

		setTitlePanelContent();
		setBillDetailsPanel();
		setTabs(mSelectedPaymentMode);
		setBottomPanel();
	}
	
	/**
	 * @param paymentMode
	 */
	public PaymentMode getActualPaymentMode() {
		
		return mActualPaymentMode;
	}
	
	/**
	 * @param paymentMode
	 */
	private void setActualPaymentMode(PaymentMode paymentMode) {
		
		mActualPaymentMode=paymentMode;
		
		switch(paymentMode){
		case  Cash:
		case Cash10:
		case Cash20:
		case QuickCash:
			mSelectedPaymentMode=PaymentMode.Cash;
			break;
		case QuickCard:
			mSelectedPaymentMode=paymentMode.Card;
			break;
		case QuickCredit:
			mSelectedPaymentMode=PaymentMode.Company;
			break;default:
			mSelectedPaymentMode=paymentMode;
			break;
		}
	}

	/**
	 * @param paymentMode
	 */
	public void setPaymentMode(PaymentMode paymentMode) {
		
		setActualPaymentMode(paymentMode);
		mTabControl.setSelectedTab(PaymentMode.Cash.getValue());
		
		switch (mSelectedPaymentMode) {
//		case Partial:
//			mTabControl.setSelectedTab(PaymentMode.Cash.getValue());
//			break;
		case Online:
//			mTabControl.setSelectedTab(PaymentMode.Cash.getValue());
			break;
		case Company:
			mTabControl.setSelectedTab(mSelectedPaymentMode.getValue());
			break;
		default:
			mTabControl.setSelectedTab(mSelectedPaymentMode.getValue());
			break;
		}
		mlabelTitle.setText("Bill Payment");
		if (paymentMode == PaymentMode.Cash10)
			setSelectedAmount(10);
		else if (paymentMode == PaymentMode.Cash20)
			setSelectedAmount(20);
	}

	/**
	 * 
	 */
	private void setTitlePanelContent() {
		
		setTitle();
	}

	/**
	 * 
	 */
	private void setTitle() {

		mlabelTitle = new JLabel();
		mlabelTitle.setHorizontalAlignment(SwingConstants.CENTER);
		mlabelTitle.setPreferredSize(new Dimension(mTitlePanel.getWidth()
				- PANEL_CONTENT_H_GAP * 2, mTitlePanel.getHeight()
				- PANEL_CONTENT_V_GAP * 2));
		mlabelTitle.setFont(PosFormUtil.getHeadingFont());
		mlabelTitle.setOpaque(true);
		mlabelTitle.setBackground(LABEL_BG_COLOR);
		mlabelTitle.setForeground(LABEL_FG_COLOR);
		mlabelTitle.setText("Bill Payment");
		mTitlePanel.add(mlabelTitle);
	}

	/**
	 * 
	 */
	private void setBottomPanel() {
		
		createMakePayment();
	}

	/**
	 * @return
	 */
	public double getBillTotalAmount() {
		
			return mBillTotalAmount; 
	}
	
	public double getFinalBillTotal() {
		
		return getBillTotalAmount()+mRoundAdjustMentAmount-mDiscountPaymentPanel
				.getTenderAmount(); 
	}

	/**
	 * @return
	 */
	public double getTaxBeforeBillDiscount() {
	
		double tax = 0;
//		if (mOrderBill != null) {
//			tax = mOrderBill.getBillTax();
//		}
		
		tax=mTaxBeforeBillDiscount;

		return tax;
	}

	/**
	 * @return
	 */
	public double getRoundingAdjustment() {
		return mRoundAdjustMentAmount;
	}

	/**
	 * @param adjAmount
	 */
	public void setRoundingAdjustment(double adjAmount) {
		this.mRoundAdjustMentAmount = adjAmount;
		setAmounts();
	}

	// public double getActualTotal(){
	// return mActualTotal;
	// }
	private void setBillDetailsPanel() {
		
		final Font valueFont = PosFormUtil.getTextFieldFont().deriveFont(18f);
		final MatteBorder borderTitle = new MatteBorder(1, 1, 1, 0, Color.GRAY);
		final MatteBorder borderValue = new MatteBorder(1, 0, 1, 1, Color.GRAY);
		// .deriveFont(Font.BOLD);
		/** Cash **/
		int top = PANEL_CONTENT_V_GAP;
		int left = PANEL_CONTENT_H_GAP;
		JLabel cashTitle = new JLabel();
		cashTitle.setText(PaymentMode.Cash.getDisplayText() + " :");
		cashTitle.setOpaque(true);
		cashTitle.setBackground(Color.LIGHT_GRAY);
		cashTitle.setHorizontalAlignment(SwingConstants.LEFT);
		cashTitle.setBounds(left, top, LABEL_TITLE_WIDTH - 20,
				LABEL_TITLE_HEIGHT);
		cashTitle.setFont(valueFont);
		cashTitle.setBorder(borderTitle);
		cashTitle.setOpaque(true);
		cashTitle.setBackground(PAYMENT_DETAIL_TITLE_BG_COLOR);
		mBillDetailsPanel.add(cashTitle);

		left = cashTitle.getX() + cashTitle.getWidth();

		mCashValue = new JLabel();
		mCashValue.setHorizontalAlignment(JTextField.RIGHT);
		mCashValue.setBounds(left, top, LABEL_VALUE_WIDTH, LABEL_VALUE_HEIGHT);
		mCashValue.setFont(valueFont);
		mCashValue.setBorder(borderValue);
		mCashValue.setOpaque(true);
		mCashValue.setBackground(PAYMENT_DETAIL_VALUE_BG_COLOR);
		mCashValue.setText("00.00");
		mBillDetailsPanel.add(mCashValue);

		/** Card **/
		top = mCashValue.getY() + mCashValue.getHeight() + PANEL_CONTENT_V_GAP
				/ 2;
		left = PANEL_CONTENT_H_GAP;
		JLabel cardTitle = new JLabel();
		cardTitle.setText(PaymentMode.Card.getDisplayText() + " :");
		cardTitle.setHorizontalAlignment(SwingConstants.LEFT);
		cardTitle.setBounds(left, top, LABEL_TITLE_WIDTH - 20,
				LABEL_TITLE_HEIGHT);
		cardTitle.setFont(valueFont);
		cardTitle.setBorder(borderTitle);
		cardTitle.setOpaque(true);
		cardTitle.setBackground(PAYMENT_DETAIL_TITLE_BG_COLOR);
		mBillDetailsPanel.add(cardTitle);

		left = cardTitle.getX() + cardTitle.getWidth();
		mCardValue = new JLabel();
		mCardValue.setHorizontalAlignment(JTextField.RIGHT);
		mCardValue.setBounds(left, top, LABEL_VALUE_WIDTH, LABEL_VALUE_HEIGHT);
		mCardValue.setFont(valueFont);
		mCardValue.setBorder(borderValue);
		mCardValue.setOpaque(true);
		mCardValue.setBackground(PAYMENT_DETAIL_VALUE_BG_COLOR);
		mCardValue.setText("00.00");
		mBillDetailsPanel.add(mCardValue);

		
		/** Advance **/
		top = mCardValue.getY() + mCardValue.getHeight() + PANEL_CONTENT_V_GAP
				/ 2;
		left = PANEL_CONTENT_H_GAP;

		JLabel advanceTitle = new JLabel();
		advanceTitle.setText("Advance:");
		advanceTitle.setHorizontalAlignment(SwingConstants.LEFT);
		advanceTitle.setBounds(left, top, LABEL_TITLE_WIDTH - 20,
				LABEL_TITLE_HEIGHT);
		advanceTitle.setFont(valueFont);
		advanceTitle.setBorder(borderTitle);
		advanceTitle.setOpaque(true);
		advanceTitle.setBackground(PAYMENT_DETAIL_TITLE_BG_COLOR);
		mBillDetailsPanel.add(advanceTitle);

		left = advanceTitle.getX() + advanceTitle.getWidth();

		mAdvanceAmount = new JLabel();
		mAdvanceAmount.setHorizontalAlignment(JTextField.RIGHT);
		mAdvanceAmount.setBounds(left, top, LABEL_VALUE_WIDTH,
				LABEL_VALUE_HEIGHT);
		mAdvanceAmount.setFont(valueFont);
		mAdvanceAmount.setBorder(borderValue);
		mAdvanceAmount.setOpaque(true);
		mAdvanceAmount.setBackground(PAYMENT_DETAIL_VALUE_BG_COLOR);
		mAdvanceAmount.setText("00.00");
		mBillDetailsPanel.add(mAdvanceAmount);
		
	
		
		/** Coupon **/
		top = PANEL_CONTENT_V_GAP;
		left = mCardValue.getX() + mCardValue.getWidth() + PANEL_CONTENT_H_GAP
				/ 2;
		JLabel couponTitle = new JLabel();
		couponTitle.setText(PaymentMode.Coupon.getDisplayText() + " :");
		couponTitle.setHorizontalAlignment(SwingConstants.LEFT);
		couponTitle.setBounds(left, top, LABEL_TITLE_WIDTH - 5,
				LABEL_TITLE_HEIGHT);
		couponTitle.setFont(valueFont);
		couponTitle.setBorder(borderTitle);
		couponTitle.setOpaque(true);
		couponTitle.setBackground(PAYMENT_DETAIL_TITLE_BG_COLOR);
		mBillDetailsPanel.add(couponTitle);

		left = couponTitle.getX() + couponTitle.getWidth();

		mCouponValue = new JLabel();
		mCouponValue.setHorizontalAlignment(JTextField.RIGHT);
		mCouponValue
				.setBounds(left, top, LABEL_VALUE_WIDTH, LABEL_VALUE_HEIGHT);
		mCouponValue.setFont(valueFont);
		mCouponValue.setBorder(borderValue);
		mCouponValue.setOpaque(true);
		mCouponValue.setBackground(PAYMENT_DETAIL_VALUE_BG_COLOR);
		mCouponValue.setText("00.00");
		mBillDetailsPanel.add(mCouponValue);

		/** Company **/
		top = couponTitle.getY() + couponTitle.getHeight()
				+ PANEL_CONTENT_V_GAP / 2;
		left = couponTitle.getX();
		JLabel companyTitle = new JLabel();
		companyTitle.setText(PaymentMode.Company.getDisplayText() + " :");
		companyTitle.setHorizontalAlignment(SwingConstants.LEFT);
		companyTitle.setBounds(left, top, LABEL_TITLE_WIDTH - 5,
				LABEL_TITLE_HEIGHT);
		companyTitle.setFont(valueFont);
		companyTitle.setBorder(borderTitle);
		companyTitle.setOpaque(true);
		companyTitle.setBackground(PAYMENT_DETAIL_TITLE_BG_COLOR);
		mBillDetailsPanel.add(companyTitle);

		left = companyTitle.getX() + companyTitle.getWidth();

		mCompanyValue = new JLabel();
		mCompanyValue.setHorizontalAlignment(JTextField.RIGHT);
		mCompanyValue.setBounds(left, top, LABEL_VALUE_WIDTH,
				LABEL_VALUE_HEIGHT);
		mCompanyValue.setFont(valueFont);
		mCompanyValue.setBorder(borderValue);
		mCompanyValue.setOpaque(true);
		mCompanyValue.setBackground(PAYMENT_DETAIL_VALUE_BG_COLOR);
		mCompanyValue.setText("00.00");
		mBillDetailsPanel.add(mCompanyValue);

		 
		top = companyTitle.getY() + companyTitle.getHeight()
				+ PANEL_CONTENT_V_GAP / 2;
		left = companyTitle.getX();
		
		JLabel discTitle = new JLabel();
		discTitle.setText("Discount:");
		discTitle.setHorizontalAlignment(SwingConstants.LEFT);
		discTitle.setBounds(left, top, LABEL_TITLE_WIDTH -5,
				LABEL_TITLE_HEIGHT);
		discTitle.setFont(valueFont);
		discTitle.setBorder(borderTitle);
		discTitle.setOpaque(true);
		discTitle.setBackground(PAYMENT_DETAIL_TITLE_BG_COLOR);
		mBillDetailsPanel.add(discTitle);

		left = discTitle.getX() + discTitle.getWidth();

		mDiscountValue = new JLabel();
		mDiscountValue.setHorizontalAlignment(JTextField.RIGHT);
		mDiscountValue.setBounds(left, top, LABEL_VALUE_WIDTH,
				LABEL_VALUE_HEIGHT);
		mDiscountValue.setFont(valueFont);
		mDiscountValue.setBorder(borderValue);
		mDiscountValue.setOpaque(true);
		mDiscountValue.setBackground(PAYMENT_DETAIL_VALUE_BG_COLOR);
		mDiscountValue.setText("00.00");
		mBillDetailsPanel.add(mDiscountValue);
		/** Extra Charge  **/
//		JLabel extraChargeTitle = new JLabel();
//		extraChargeTitle.setText("Extra:");
//		extraChargeTitle.setHorizontalAlignment(SwingConstants.LEFT);
//		extraChargeTitle.setBounds(left, top, LABEL_TITLE_WIDTH - 5,
//				LABEL_TITLE_HEIGHT);
//		extraChargeTitle.setFont(valueFont);
//		extraChargeTitle.setBorder(borderTitle);
//		extraChargeTitle.setOpaque(true);
//		extraChargeTitle.setBackground(PAYMENT_DETAIL_TITLE_BG_COLOR);
//		mBillDetailsPanel.add(extraChargeTitle);
//
//		left = extraChargeTitle.getX() + extraChargeTitle.getWidth();
//		mExtraCharges = new JLabel();
//		mExtraCharges.setHorizontalAlignment(JTextField.RIGHT);
//		mExtraCharges.setBounds(left, top, LABEL_VALUE_WIDTH, LABEL_VALUE_HEIGHT);
//		mExtraCharges.setFont(valueFont);
//		mExtraCharges.setBorder(borderValue);
//		mExtraCharges.setForeground(Color.RED);
//		mExtraCharges.setOpaque(true);
//		mExtraCharges.setBackground(PAYMENT_DETAIL_VALUE_BG_COLOR);
//		mBillDetailsPanel.add(mExtraCharges); 	

		 
		
		/** Total **/
		top = mCouponValue.getY();
		left = mCouponValue.getX() + mCouponValue.getWidth()
				+ PANEL_CONTENT_H_GAP / 2;
		JLabel billTotal = new JLabel();
		billTotal.setText("Bill Total:");
		billTotal.setHorizontalAlignment(SwingConstants.LEFT);
		billTotal.setBounds(left, top, LABEL_TITLE_WIDTH - 10,
				LABEL_TITLE_HEIGHT);
		billTotal.setFont(valueFont);
		billTotal.setBorder(borderTitle);
		billTotal.setOpaque(true);
		billTotal.setBackground(PAYMENT_DETAIL_TITLE_BG_COLOR);
		mBillDetailsPanel.add(billTotal);

		left = billTotal.getX() + billTotal.getWidth();
		mBillTotal = new JLabel();
		mBillTotal.setHorizontalAlignment(JTextField.RIGHT);
		mBillTotal.setBounds(left, top, LABEL_VALUE_WIDTH, LABEL_VALUE_HEIGHT);
		mBillTotal.setFont(valueFont);
		mBillTotal.setBorder(borderValue);
		mBillTotal.setForeground(Color.RED);
		mBillTotal.setOpaque(true);
		mBillTotal.setBackground(PAYMENT_DETAIL_VALUE_BG_COLOR);
		mBillDetailsPanel.add(mBillTotal);

		/** discount **/
		top = billTotal.getY() + billTotal.getHeight() + PANEL_CONTENT_V_GAP
				/ 2;
		left = billTotal.getX()  ;
		

		/** Extra Charge  **/
		JLabel extraChargeTitle = new JLabel();
		extraChargeTitle.setText("Extra:");
		extraChargeTitle.setHorizontalAlignment(SwingConstants.LEFT);
		extraChargeTitle.setBounds(left, top, LABEL_TITLE_WIDTH - 10,
				LABEL_TITLE_HEIGHT);
		extraChargeTitle.setFont(valueFont);
		extraChargeTitle.setBorder(borderTitle);
		extraChargeTitle.setOpaque(true);
		extraChargeTitle.setBackground(PAYMENT_DETAIL_TITLE_BG_COLOR);
		mBillDetailsPanel.add(extraChargeTitle);

		left = extraChargeTitle.getX() + extraChargeTitle.getWidth();
		mExtraCharges = new JLabel();
		mExtraCharges.setHorizontalAlignment(JTextField.RIGHT);
		mExtraCharges.setBounds(left, top, LABEL_VALUE_WIDTH, LABEL_VALUE_HEIGHT);
		mExtraCharges.setFont(valueFont);
		mExtraCharges.setBorder(borderValue);
		mExtraCharges.setForeground(Color.RED);
		mExtraCharges.setOpaque(true);
		mExtraCharges.setBackground(PAYMENT_DETAIL_VALUE_BG_COLOR);
		mBillDetailsPanel.add(mExtraCharges); 	
		
		/** Dummy  **/
		left = billTotal.getX()  ;
		top = extraChargeTitle.getY() + extraChargeTitle.getHeight() + PANEL_CONTENT_V_GAP
				/ 2;
		JLabel dummyTitle = new JLabel();
//		dummyTitle.setText("Dummy:");
		dummyTitle.setHorizontalAlignment(SwingConstants.LEFT);
		dummyTitle.setBounds(left, top, LABEL_TITLE_WIDTH - 10,
				LABEL_TITLE_HEIGHT);
		dummyTitle.setFont(valueFont);
		dummyTitle.setBorder(borderTitle);
		dummyTitle.setOpaque(true);
		dummyTitle.setBackground(PAYMENT_DETAIL_TITLE_BG_COLOR);
		mBillDetailsPanel.add(dummyTitle);

		left = dummyTitle.getX() + dummyTitle.getWidth();
		JLabel dummyValue = new JLabel();
		dummyValue.setHorizontalAlignment(JTextField.RIGHT);
		dummyValue.setBounds(left, top, LABEL_VALUE_WIDTH, LABEL_VALUE_HEIGHT);
		dummyValue.setFont(valueFont);
		dummyValue.setBorder(borderValue);
		dummyValue.setForeground(Color.RED);
		dummyValue.setOpaque(true);
		dummyValue.setBackground(Color.GRAY);
		mBillDetailsPanel.add(dummyValue); 
		
//		JLabel discTitle = new JLabel();
//		discTitle.setText("Discount:");
//		discTitle.setHorizontalAlignment(SwingConstants.LEFT);
//		discTitle.setBounds(left, top, LABEL_TITLE_WIDTH - 10,
//				LABEL_TITLE_HEIGHT);
//		discTitle.setFont(valueFont);
//		discTitle.setBorder(borderTitle);
//		discTitle.setOpaque(true);
//		discTitle.setBackground(PAYMENT_DETAIL_TITLE_BG_COLOR);
//		mBillDetailsPanel.add(discTitle);
//
//		left = discTitle.getX() + discTitle.getWidth();
//
//		mDiscountValue = new JLabel();
//		mDiscountValue.setHorizontalAlignment(JTextField.RIGHT);
//		mDiscountValue.setBounds(left, top, LABEL_VALUE_WIDTH,
//				LABEL_VALUE_HEIGHT);
//		mDiscountValue.setFont(valueFont);
//		mDiscountValue.setBorder(borderValue);
//		mDiscountValue.setOpaque(true);
//		mDiscountValue.setBackground(PAYMENT_DETAIL_VALUE_BG_COLOR);
//		mDiscountValue.setText("00.00");
//		mBillDetailsPanel.add(mDiscountValue);
		
		
		left = mBillTotal.getX() + mBillTotal.getWidth() + PANEL_CONTENT_H_GAP
				/ 2;
		top = mBillTotal.getY();
		mBillBalanceAmount = new JLabel();
		mBillBalanceAmount
				.setText(PosCurrencyUtil.format(0));
		mBillBalanceAmount.setHorizontalAlignment(JTextField.RIGHT);
		mBillBalanceAmount.setSize(LABEL_VALUE_WIDTH + 93, LABEL_VALUE_HEIGHT * 3
				+ PANEL_CONTENT_V_GAP );
		mBillBalanceAmount.setLocation(left, top);
		mBillBalanceAmount.setFont(PosFormUtil.getTextFieldBoldFont()
				.deriveFont(30f));
		mBillBalanceAmount.setOpaque(true);
		mBillBalanceAmount.setBorder(new MatteBorder(1, 1, 1, 1, Color.GRAY));
		mBillBalanceAmount.setForeground(Color.RED);
		mBillBalanceAmount.setBackground(PAYMENT_DETAIL_BALANCE_BG_COLOR);
		mBillDetailsPanel.add(mBillBalanceAmount);

		left = mBillTotal.getX() + mBillTotal.getWidth() + PANEL_CONTENT_H_GAP;
		JLabel billBalance = new JLabel();
		billBalance.setText("Tendered:");
		billBalance.setHorizontalAlignment(SwingConstants.LEFT);
		billBalance.setBounds(left, top, LABEL_TITLE_WIDTH + 30,
				LABEL_TITLE_HEIGHT);
		billBalance.setFont(PosFormUtil.getLabelFont().deriveFont(20f));
		billBalance.setOpaque(true);
		billBalance.setBackground(PAYMENT_DETAIL_TITLE_BG_COLOR);
		// mBillDetailsPanel.add(billBalance);

		left = billBalance.getX() + billBalance.getWidth();
		mTenderedAmount = new JLabel();
		mTenderedAmount.setText(PosCurrencyUtil.format(0));
		mTenderedAmount.setHorizontalAlignment(JTextField.RIGHT);
		mTenderedAmount.setBounds(left, top, LABEL_VALUE_WIDTH,
				LABEL_VALUE_HEIGHT);
		mTenderedAmount.setFont(PosFormUtil.getTextFieldBoldFont().deriveFont(
				30f));
		mTenderedAmount.setOpaque(true);
		mTenderedAmount.setBackground(PAYMENT_DETAIL_TITLE_BG_COLOR);
		// mBillDetailsPanel.add(mTenderedAmount);

		left = mTenderedAmount.getX() + mTenderedAmount.getWidth()
				+ PANEL_CONTENT_H_GAP;
		JLabel billChange = new JLabel();
		billChange.setText("Balance:");
		billChange.setHorizontalAlignment(SwingConstants.LEFT);
		billChange.setBounds(left, top, LABEL_TITLE_WIDTH + 15,
				LABEL_TITLE_HEIGHT);
		billChange.setFont(PosFormUtil.getLabelFont());
		billChange.setOpaque(true);
		billChange.setBackground(PAYMENT_DETAIL_TITLE_BG_COLOR);
		// mBillDetailsPanel.add(billChange);

	}

	/**
	 * @param amount
	 */
	public void setTenderedAmount(double amount) {
		mTenderedAmount.setText(PosCurrencyUtil.format(amount));
	}

	/**
	 * @return
	 */
	public double getTenderedAmount() {
		setAmounts();
		return Double.parseDouble(mTenderedAmount.getText());
	}

	/**
	 * @param amount
	 */
	public void setBalanceAmount(double amount) {
		
		String amountText = PosCurrencyUtil.format(amount);
		mBillBalanceAmount
				.setText(amountText.equalsIgnoreCase("-0.00") ? "0.00"
						: amountText);
		// System.out.println(amount);
		// System.out.println(PosNumberUtil.formatNumber(amount));
	}

	/**
	 * Return the balance amount
	 * @return
	 */
	public double getBalanceAmount() {
		
		setAmounts();
		return Double.parseDouble(mBillBalanceAmount.getText());
	}

	public void doQuickPayment(){
		
		if (!initPayment())
			return;
		
		switch(mSelectedPaymentMode){
		case Cash:
			
			if(mOrderHeaderActual!=null && mOrderHeaderActual.getCustomer().isIsArCompany())
				mCompanyPaymentPanel.clear();
			
			mCashPaymentPanel.onGotFocus();
			mCashPaymentPanel.setExactAmount();
			break;
		case Card:
			
			if(mOrderHeaderActual!=null && mOrderHeaderActual.getCustomer().isIsArCompany())
				mCompanyPaymentPanel.clear();
			
			mCardPaymentPanel.onGotFocus();
			break;
		case Company:
			
			if(mOrderHeaderActual!=null && !mOrderHeaderActual.getCustomer().isIsArCompany()){
				PosFormUtil.showErrorMessageBox(this, "Can't make this payment, This feature is available only for credit customers.");
				return;
			}
				
			mCompanyPaymentPanel.onGotFocus();
			break;
		}
		makePaymentButtonListner.onClicked(mButtonMakePayment);
	}

	/**
	 * Sets the various amounts
	 */
	private void setAmounts() {
		
		double tendered = 0;
		// double paid=0;
		double balance = 0;
		for (PosTab tab : mTabs) {
			// if(((PosPaymentBasePanel)
			// tab).getPaymentMode()!=PaymentMode.Discount){
			tendered += ((PosPaymentBasePanel) tab).getTenderAmount();
			// }
		}
	
		mCashValue.setText(PosCurrencyUtil.format(mCashPaymentPanel
				.getTenderAmount()));
		markAsUsed(mCashValue, mCashPaymentPanel.getTenderAmount() > 0);
		
		mCardValue.setText(PosCurrencyUtil.format(mCardPaymentPanel
				.getTenderAmount()));
		markAsUsed(mCardValue, mCardPaymentPanel.getTenderAmount() > 0);
		
		mCouponValue.setText(PosCurrencyUtil.format(mCouponPaymentPanel
				.getTenderAmount()));
		markAsUsed(mCouponValue, mCouponPaymentPanel.getTenderAmount() > 0);
		
		mCompanyValue.setText(PosCurrencyUtil.format(mCompanyPaymentPanel
				.getTenderAmount()));
		markAsUsed(mCompanyValue, mCompanyPaymentPanel.getTenderAmount() > 0);
		
		if(mOrderHeaderActual!=null && 
				!PosOrderUtil.isDeliveryService(mOrderHeaderActual)){			
			mDiscountValue.setText(PosCurrencyUtil.format(mDiscountPaymentPanel
					.getTenderAmount()+mSplitAdjustmentPanel.getTenderAmount()));
			markAsUsed(mDiscountValue, (mDiscountPaymentPanel.getTenderAmount() > 0 || mSplitAdjustmentPanel.getTenderAmount() > 0));
			
		}else
			markAsUsed(mDiscountValue, (PosNumberUtil.parseDoubleSafely(mDiscountValue.getText()) > 0));
		
		markAsUsed(mAdvanceAmount, (PosNumberUtil.parseDoubleSafely(mAdvanceAmount.getText()) > 0));
		markAsUsed(mExtraCharges, (PosNumberUtil.parseDoubleSafely(mExtraCharges.getText()) > 0));
		
		setTenderedAmount(tendered);
		
//		double billTotal = ((mOrderHeader != null) ? PosNumberUtil
//				.roundTo(mOrderHeader.getTotalAmount())
//		// - mOrderHeader.getBillDiscountAmount())
//				: getBillTotal());
		
//		final double billTotal = getBillTotalAmount();
		


//		if (mOrderHeaderForPayment != null)
//			mOrderHeaderForPayment.setRoundAdjustmentAmount(mOrderHeaderOrg.getRoundAdjustmentAmount()+ mRoundAdjustMentAmount)
		// balance = tendered+mCompletedPayment - (billTotal +
		// mRoundAdjustMentAmount)-((mDiscountPaymentPanel.getTenderAmount() >
		// 0)?mOrderHeader.getBillDiscountAmount():0);
//		if(mSelectedPaymentMode==PaymentMode.Partial)
//			billTotal=getPartialPaymentAmount();
//		
//		balance = tendered + ((mIsPartialPayment)?0:mCompletedPayment)
//				- (billTotal + mRoundAdjustMentAmount);
		balance = tendered 	- (mBillTotalAmount + mRoundAdjustMentAmount);
		
		// POS online payment mode issue. The below line of code written for return the balance amount is zero in online payment mode. 
		
		if(mSelectedPaymentMode.getTitle().equals("Online")) {
			 balance=0;	
		}
		
		setBalanceAmount(balance);

	}

//	/**
//	 * @return
//	 */
//	private double getPartialPaymentAmount() {
//		
//		double amount=0;
//		
//		if(mSplitForPayment!=null){
//				
//				amount=mSplitForPayment.getActualAmount();
//		}
//		
//		return amount;
//	}


	/**
	 * @param component
	 * @param used
	 */
	private void markAsUsed(JLabel component, boolean used) {

		component.setBackground(((used) ? PAYMENT_DETAIL_VALUE_BG_COLOR
				: Color.GRAY));

	}

	/**
	 * 
	 */
	private void createMakePayment() {
		
		int left = mBottomPanel.getX() + PANEL_CONTENT_H_GAP * 10;

		mButtonMakePayment = new PosButton();
		mButtonMakePayment.setDefaultButton(true);
		mButtonMakePayment.setText("Make Payment");
		mButtonMakePayment.setMnemonic('y');
		mButtonMakePayment.registerKeyStroke(KeyEvent.VK_ENTER,KeyEvent.CTRL_DOWN_MASK);
		mButtonMakePayment.setImage(PosResUtil
				.getImageIconFromResource(MAKE_PAYMENT_IMAGE_ITEM_BUTTON));
		mButtonMakePayment
				.setTouchedImage(PosResUtil
						.getImageIconFromResource(MAKE_PAYMENT_IMAGE_ITEM_BUTTON_TOUCH));
		mButtonMakePayment.setHorizontalAlignment(SwingConstants.CENTER);
		mButtonMakePayment.setBounds(left, PANEL_CONTENT_V_GAP * 5,
				IMAGE_BUTTON_WIDTH, IMAGE_BUTTON_HEIGHT);
		mButtonMakePayment.setOnClickListner(makePaymentButtonListner);
		
		mBottomPanel.add(mButtonMakePayment);

		left = mButtonMakePayment.getX() + mButtonMakePayment.getWidth()
				+ PANEL_CONTENT_V_GAP * 2;
		mButtonRemarks = new PosButton();
		mButtonRemarks.setText("Remarks");
		mButtonRemarks.setMnemonic('m');
//		mButtonRemarks.registerKeyStroke(KeyEvent.VK_F2);
		mButtonRemarks.setImage(PosResUtil
				.getImageIconFromResource(REMARKS_IMAGE_ITEM_BUTTON));
		mButtonRemarks.setTouchedImage(PosResUtil
				.getImageIconFromResource(REMARKS_IMAGE_ITEM_BUTTON_TOUCH));
		mButtonRemarks.setHorizontalAlignment(SwingConstants.CENTER);
		mButtonRemarks.setBounds(left, PANEL_CONTENT_V_GAP * 5,
				IMAGE_BUTTON_WIDTH, IMAGE_BUTTON_HEIGHT);
		mButtonRemarks.setOnClickListner(imgRemarksButtonListner);
		mBottomPanel.add(mButtonRemarks);

		left = mButtonRemarks.getX() + mButtonRemarks.getWidth()
				+ PANEL_CONTENT_V_GAP * 2;
		mButtonReset = new PosButton();
		mButtonReset.setText("Reset");
		mButtonReset.setMnemonic('R');
//		mButtonReset.registerKeyStroke(KeyEvent.VK_F3);
		mButtonReset.setImage(PosResUtil
				.getImageIconFromResource(IMAGE_BUTTON_CANCEL));
		mButtonReset.setTouchedImage(PosResUtil
				.getImageIconFromResource(IMAGE_BUTTON_CANCEL_TOUCH));
		mButtonReset.setHorizontalAlignment(SwingConstants.CENTER);
		mButtonReset.setBounds(left, PANEL_CONTENT_V_GAP * 5,
				IMAGE_BUTTON_WIDTH, IMAGE_BUTTON_HEIGHT);
		mButtonReset.setOnClickListner(imgResetButtonListner);
		mBottomPanel.add(mButtonReset);

		left = mButtonReset.getX() + mButtonReset.getWidth()
				+ PANEL_CONTENT_V_GAP * 2;

		mButtonCacel = new PosButton();
		mButtonCacel.setText("Close");
		mButtonCacel.setMnemonic('C');
//		mButtonCacel.registerKeyStroke(KeyEvent.VK_F4);
		mButtonCacel.setCancel(true);
		mButtonCacel.setImage(PosResUtil
				.getImageIconFromResource(IMAGE_BUTTON_CANCEL));
		mButtonCacel.setTouchedImage(PosResUtil
				.getImageIconFromResource(IMAGE_BUTTON_CANCEL_TOUCH));
		mButtonCacel.setHorizontalAlignment(SwingConstants.CENTER);
		mButtonCacel.setBounds(left, PANEL_CONTENT_V_GAP, IMAGE_BUTTON_WIDTH,
				IMAGE_BUTTON_HEIGHT);
		mButtonCacel.setOnClickListner(imgCancelButtonListner);
		mBottomPanel.add(mButtonCacel);
	}

	/**
	 * @param orderHeader
	 * @return
	 */
	private BeanOrderPayment getCashPayment(BeanOrderHeader orderHeader) {
		
		PosCashPaymentPanel posPayment = (PosCashPaymentPanel) mCashPaymentPanel;
		BeanOrderPayment orderPaymentCash = posPayment.getPayment(orderHeader);
		setCashierData(orderPaymentCash);
		
		return orderPaymentCash;
	}
	
	/**
	 * @param orderHeader
	 * @return
	 */
	private BeanOrderPayment getBalancePayment(BeanOrderHeader orderHeader) {
		
		BeanOrderPayment orderPaymentBalance = new BeanOrderPayment();
		orderPaymentBalance.setOrderId(orderHeader.getOrderId());
		orderPaymentBalance.setPaymentMode(PaymentMode.Balance);
		orderPaymentBalance.setPaidAmount(getBalanceAmount());
		setCashierData(orderPaymentBalance);
		
		return orderPaymentBalance;
	}

	/**
	 * @param orderHeader
	 * @return
	 */
	private BeanOrderPayment getBalanceByVoucher(BeanOrderHeader orderHeader) {
		
		BeanOrderPayment orderPaymentBalanceByVoucher = new BeanOrderPayment();
		orderPaymentBalanceByVoucher.setOrderId(orderHeader.getOrderId());
		orderPaymentBalanceByVoucher.setPaymentMode(PaymentMode.CouponBalance);
		orderPaymentBalanceByVoucher.setPaidAmount(getBalanceAmount());
		orderPaymentBalanceByVoucher
				.setVoucherBalanceReturned(mCouponPaymentPanel
						.isVoucherBalanceReturned());
		setCashierData(orderPaymentBalanceByVoucher);
		
		return orderPaymentBalanceByVoucher;
	}

	/**
	 * @param orderHeader
	 * @return
	 */
	private ArrayList<BeanOrderPayment> getCouponPayment(
			BeanOrderHeader orderHeader) {
		PosCouponPaymentPanel posCouponPayment = mCouponPaymentPanel;
		ArrayList<BeanOrderPayment> posOrderCouponPaymentItems = posCouponPayment
				.getPayment(orderHeader);

		for (BeanOrderPayment paymentObj : posOrderCouponPaymentItems)
			setCashierData(paymentObj);

		return posOrderCouponPaymentItems;
	}

	/**
	 * @param payment
	 */
	private void setCashierData(BeanOrderPayment payment) {
		
		payment.setCashierID(PosEnvSettings.getInstance().getCashierShiftInfo()
				.getCashierInfo().getId());
		payment.setPaymentTime(PosDateUtil.getDateTime());
		payment.setPaymentDate(PosEnvSettings.getPosEnvSettings().getPosDate());
		payment.setShiftId(PosEnvSettings.getInstance().getCashierShiftInfo().getShiftItem().getId());
		payment.setNew(true);
	}

	/**
	 * @param orderHeader
	 * @return
	 * @throws Exception
	 */
	private BeanOrderPayment getCardPayment(BeanOrderHeader orderHeader)
			throws Exception {
		
		PosCardPaymentPanel posCardPayment = (PosCardPaymentPanel) mCardPaymentPanel;
		BeanOrderPayment orderPayment = posCardPayment.getPayment(orderHeader);
		if (orderPayment != null)
			setCashierData(orderPayment);
		
		return orderPayment;
	}

	/**
	 * @param orderHeader
	 * @return
	 * @throws Exception
	 */
	private BeanOrderPayment getCashOutPayment(BeanOrderHeader orderHeader)
			throws Exception {
		
		PosCardPaymentPanel posCardPayment = (PosCardPaymentPanel) mCardPaymentPanel;
		BeanOrderPayment orderPayment = posCardPayment
				.getCashOutPayment(orderHeader);
		
		if (orderPayment != null)
			setCashierData(orderPayment);
		return orderPayment;
	}

	/**
	 * @param orderHeader
	 * @return
	 */
	private BeanOrderPayment getCompanyPayment(BeanOrderHeader orderHeader) {
		
		PosCreditCustomerPaymentPanel posCompanyPayment = (PosCreditCustomerPaymentPanel) mCompanyPaymentPanel;
		BeanOrderPayment orderPayment = posCompanyPayment
				.getPayment(orderHeader);
		setCashierData(orderPayment);
		
		return orderPayment;
	}

	/**
	 * Partial paymetns contains multiple bill discounts
	 * @param orderHeader
	 * @throws Exception 
	 */
	private void setDiscountPayment(BeanOrderHeader orderHeader) throws Exception {
		
		mBillDiscount = new BeanOrderDiscount(mDiscountPaymentPanel.getDiscount());
		
		
//		if (mDiscountPaymentPanel.getTenderAmount() <= 0) return;
		
//		orderHeader.setBillDiscount(mDiscountPaymentPanel.getDiscount());
		ArrayList<BeanOrderDiscount> billDiscounts=((orderHeader.getBillDiscounts()==null)?
				new ArrayList<BeanOrderDiscount>():
					orderHeader.getBillDiscounts());
		
		if(mDiscountPaymentPanel.getTenderAmount() > 0){

			mBillDiscount.setNew(true);
			mBillDiscount.setDiscountedAmount(mDiscountPaymentPanel.getTenderAmount());
			billDiscounts.add(mBillDiscount);
		}

		orderHeader.setBillDiscounts(billDiscounts);
		orderHeader.setBillDiscountAmount(orderHeader.getBillDiscountAmount()+
				mDiscountPaymentPanel.getTenderAmount());
		
		final double totalAmt= PosOrderUtil.getTotalAmount(orderHeader);
		
		if(billDiscounts.size()==1 && mBillDiscount.isPercentage())
			orderHeader.setBillDiscountPercentage(mBillDiscount.getPrice());
		else{
			
	//		final double discPercentage=(totalAmt==0?0:PosCurrencyUtil.roundTo(orderHeader.getBillDiscountAmount()*100/ totalAmt));
			final double discPercentage=(totalAmt==0?0:orderHeader.getBillDiscountAmount()*100/ totalAmt);
			orderHeader.setBillDiscountPercentage(discPercentage);
		}
 	}

	/**
	 * @param oh
	 * @return
	 */
	private boolean canOpenCashBox(BeanOrderHeader oh){

		boolean res=true;
		if(oh.getOrderPaymentItems().size()==1 && oh.getOrderPaymentItems().get(0).getPaymentMode()==PaymentMode.Card)
			res=PosEnvSettings.getInstance().getEFTConfiguration().isOpenCashBox();
		return res;
	}
	
 	private boolean validateKitchenPrinter(){
		 
		 boolean result=false;
		 
			if(PosDeviceManager.getInstance().hasKichenPrinter()){
				for(PosDeviceKitchenPrinter kp: PosDeviceManager.getInstance().getKitchenPrinters()){

					if(kp.isDeviceInitialized() && kp.isActive() && PosOrderUtil.hasPrintableItems(mOrderHeaderActual, true)){
					
						if(PosEnvSettings.getInstance().getPrintSettings().getPrintToKitchenAtPayment()==EnablePrintingOption.ASK)
							result=(PosFormUtil.showQuestionMessageBox(getParentForm(), MessageBoxButtonTypes.YesNo, "Do you want to print to kitchen?", null)==MessageBoxResults.Yes);
						else if(PosEnvSettings.getInstance().getPrintSettings().getPrintToKitchenAtPayment()==EnablePrintingOption.FORCE)
							result=true;
						break;
					}
				}
					
			}
		 return result;
	 }


	 /**
	  * Returns the parent based on the payment method
	 * @return
	 */
	private Object getParentForm(){
		 
		 return (mActualPaymentMode==PaymentMode.QuickCash || 
				 mActualPaymentMode==PaymentMode.QuickCard || 
				 mActualPaymentMode==PaymentMode.Online)?mParent:this;
	 }
	/**
	 * @return
	 */
	private boolean checkWholeSaleInfo(BeanOrderHeader oh ){
		
		boolean isOK=false;
		 
		if(	(oh.getVehicleNumber()==null ||oh.getVehicleNumber().trim()=="") || (oh.getDriverName()==null || oh.getDriverName().trim()=="" )){
			
			
			final PosWholeSaleInfoForm form=new PosWholeSaleInfoForm();
			form.setOrderHeader(oh);
			form.setCustomoerInfoReadOnly(true);
			PosFormUtil.showLightBoxModal(this,form);
			if(!form.isCancelled()){
				
				isOK=true;
				if (oh != null){
					
					oh.setVehicleNumber(form.getVehicleNumber());
					oh.setDriverName(form.getDriverName());
				}
			}
			
		}else
			isOK=true;
		
		
		return isOK;
	}

	private IPosButtonListner makePaymentButtonListner = new PosButtonListnerAdapter() {


		@Override
		public void onClicked(PosButton button) {

			if (!validatePaymentOptions()) 	return;
			

			if(mOrderHeaderForPayment.getOrderServiceType()==PosOrderServiceTypes.WHOLE_SALE){
				
				if(!checkWholeSaleInfo(mOrderHeaderForPayment)) return;
			}
				

			try {

				onOrderPaymentStatusChanged(PaymentProcessStatus.INIT);
				
				if(PosEnvSettings.getInstance().getPrintSettings().getPrintToKitchenAtPayment()!=EnablePrintingOption.NO 
						&& mOrderHeaderForPayment.getOrderDetailItems()!=null 
						&& mOrderHeaderForPayment.getOrderDetailItems().size()>0){
					
					if(validateKitchenPrinter())
						PosReceipts.printReceiptToKitchen(mOrderHeaderActual, false);
						
				}
				mOrderHeaderForPayment.setNewOrder(false);
				/**
				 * SPLIT TO DO: setting the orderentry forms satatus
				 * Paymetn form is dtaching from order entry form
				 *
				 * PosOrderEntryForm orderEntry = (PosOrderEntryForm) mParent;
				 */
				BeanOrderHeader orderToSave=mOrderHeaderForPayment.clone();
				/**
				 * Sets the payment items null for this object
				 * This is needed bcose every payment is individual
				 * 
				 */
				orderToSave.setOrderPaymentItems(null);

				/**
				 * update the split payment info if needed
				 * This is needed to know the actual amount paid.
				 * Actual amount paid can be different depending on rouding tax discount etc.. 
				 */
				updateSplitForPayment();
				/**
				 * Updates the order header amounts and split details
				 */
				updateOrderHeaderForPayment(orderToSave);
				/**
				 * Sets the bill discount to the order header
				 * In case of partial payments there can be multiple discounts
				 */
				setDiscountPayment(orderToSave);

				/**
				 * SPLIT TO DO: setting the orderentry forms status
				 * Payment form is detaching from order entry form
				 * 				
				 *
				 * if (!orderEntry.isFreshOrder()) {
				 *  	mOrderHeader.setNewOrder(false);
				 *	}
				 */
				/**
				 * sets the payment status of the order
				 */
				orderToSave.setPaymentProcessStatus(PaymentProcessStatus.PROCESSING);
				onOrderPaymentStatusChanged(orderToSave.getPaymentProcessStatus());

				/**
				 * SPLIT TO DO: setting the orderentry forms satatus
				 * Paymetn form is dtaching from order entry form
				 * 				
				 * orderEntry.setFreshOrder(false);
				 * 
				 **/

				ArrayList<BeanOrderPayment> tmpOrderPaymentItems = new ArrayList<BeanOrderPayment>();

				if (mSelectedPaymentMode==PaymentMode.Online && getFinalBillTotal()>0){
					
						BeanOrderPayment paymentObject = new BeanOrderPayment();
						paymentObject.setOrderId(mOrderHeaderForPayment.getOrderId());
						paymentObject.setPaymentMode(PaymentMode.Online);
						paymentObject.setPaidAmount(getFinalBillTotal());
						setCashierData(paymentObject);
						tmpOrderPaymentItems.add(paymentObject);
					
				}else{
					for (PosTab tab : mTabsForPayment) {
	
						PosPaymentBasePanel paymentPanel = (PosPaymentBasePanel) tab;
						BeanOrderPayment paymentObject = null;
	
						if (paymentPanel.getTenderAmount() != 0) {
	
							switch (paymentPanel.getPaymentMode()) {
	
							case Cash:
	
								paymentObject = getCashPayment(orderToSave);
								if (paymentObject != null) {
	
									tmpOrderPaymentItems.add(paymentObject);
								}
								break;
	
							case Card:
	
								paymentObject = getCardPayment(orderToSave);
								if (paymentObject != null) {
	
									tmpOrderPaymentItems.add(paymentObject);
									paymentObject = getCashOutPayment(orderToSave);
									if (paymentObject != null) {
	
										orderToSave.setCashOut(paymentObject
												.getPaidAmount());
										tmpOrderPaymentItems.add(paymentObject);
									}
								}
								break;
	
							case Coupon:
	
								ArrayList<BeanOrderPayment> posOrderCouponPaymentItems = getCouponPayment(orderToSave);
								if (posOrderCouponPaymentItems != null)
									tmpOrderPaymentItems
									.addAll(posOrderCouponPaymentItems);
								break;
	
							case Company:
	
								tmpOrderPaymentItems
								.add(getCompanyPayment(orderToSave));
								break;
							case SplitAdjust:
								/***
								 * 
								 * This is already received payment so no need to add again
								 * 
								 */
								//							paymentObject = getSplitParAdjPayment(orderToSave);
								//							if (paymentObject != null) {
								//
								//								tmpOrderPaymentItems.add(paymentObject);
								//							}
								break;
							default:
								break;
							}
						}
					}

					/** 
					 * Adding balance as payment
					 *  
					 **/
	
					if (getBalanceAmount() > 0) {
	
						if (mCouponPaymentPanel.getTenderAmount()>0 && mCouponPaymentPanel.getTenderAmount() >= (getBillTotalAmount() - mDiscountPaymentPanel
								.getTenderAmount())) {
	
							tmpOrderPaymentItems.add(getBalanceByVoucher(orderToSave));
							if (mCouponPaymentPanel.isVoucherBalanceReturned()) {
	
								orderToSave.setActualBalancePaid(getBalanceByVoucher(orderToSave).getPaidAmount());
							}
	
						} else {
	
							tmpOrderPaymentItems.add(getBalancePayment(orderToSave));
							orderToSave.setActualBalancePaid(getBalancePayment(orderToSave).getPaidAmount());
						}
					 }

				}
//				for(BeanOrderPayment orderPayItem : tmpOrderPaymentItems){
//					orderPayItem.setRemarks(mRemarks);
//				}
			 	orderToSave.setOrderPaymentItems(tmpOrderPaymentItems);
				orderToSave.setPaymentProcessStatus(PaymentProcessStatus.COMPLETED);

				/**
				 * 
				 * Update the original header object with the values
				 * This is required for partial payments.
				 * 
				 */
				updateActualHeader(orderToSave);
				updateActualSplitsStatus();

				final boolean result=onOrderPaymentStatusChanged(orderToSave.getPaymentProcessStatus());
				/**
				 * 
				 * Sets the order status of the temporary object from actualOrder object 
				 * 
				 */
				orderToSave.setStatus(mOrderHeaderActual.getStatus());
				orderToSave.setInvoiceNo(mOrderHeaderActual.getInvoiceNo());
				orderToSave.setOrderPaymentHeaders(mOrderHeaderActual.getOrderPaymentHeaders());
				orderToSave.setClosingDate(mOrderHeaderActual.getClosingDate());
				orderToSave.setClosingTime(mOrderHeaderActual.getClosingTime());
				orderToSave.setClosedBy(mOrderHeaderActual.getClosedBy());
				if (PosOrderUtil.isDeliveryService(mOrderHeaderActual))
					orderToSave.setBillDiscountAmount(mOrderHeaderActual.getBillDiscountAmount());
				if (result) {
					/**
					 * SPLIT TO DO: setting the orderentry forms status
					 * Payment form is detaching from order entry form
					 * 					
					 * orderEntry.setFreshOrder(true);
					 */
					if (!mOrderHeaderActual.getStatus().equals(PosOrderStatus.Partial)) {

						if (PosEnvSettings.getInstance().isEnabledHMSIntegration()  &&
							 mOrderHeaderActual.getCustomer().getCustType().getCode().equals(PosCustomerTypeProvider.ROOM_TYPE_CODE) && 
							 mSelectedPaymentMode.equals(PaymentMode.Company)){
							HmsUtil.saveOrderDetais(mOrderHeaderActual);
						
						}
						
						SynchronizeToServer.synchronizeTable(
								SynchronizeToServer.SyncTable.ORDER_HDRS
								.getCode(), "order_hdrs.order_id='"
										+ mOrderHeaderActual.getOrderId() + "'");
						
						
						
						
					}

					/**
					 * Sets the payment summary info object for showing the summary form.
					 */
					if(mIsPartialPayment ){

						/**
						 * Use actual payment header for printing and showing summary.
						 */
						updatePaymentSummaryInfo(orderToSave);
						
						if (orderToSave.getOrderSplits().size()>0){
						
							String splitNo=Integer.toString(mSplitForPayment.getSplitNo());
							splitNo=   PosStringUtil.paddLeft( splitNo, PosOrderSplitProvider.SPLIT_NO_WIDTH , '0');
							orderToSave.setInvoiceNo(orderToSave.getInvoiceNo() + "/" + splitNo  );
						}
						
						if(PosEnvSettings.getInstance().getPrintSettings().getReceiptPrintingAtPayment()!=EnablePrintingOption.NO){
							
							if(!PosOrderUtil.isDeliveryService(mOrderHeaderActual))
								printBill(getParentForm(),orderToSave, canOpenCashBox(orderToSave),mPaymentSummary,false,false);
							else if  (orderToSave.getStatus()==PosOrderStatus.Closed){
								printBill(getParentForm(),mOrderHeaderActual, canOpenCashBox(orderToSave),mPaymentSummary,false,false);
								if(mOrderHeaderActual.getOrderServiceType()==PosOrderServiceTypes.SALES_ORDER)
									PosFormUtil.showSOInvoicePrintConfirmMessage((RootPaneContainer)getParentForm(),false, mOrderHeaderActual,  canOpenCashBox(orderToSave) );
										
							}
							
						}else if(canOpenCashBox(orderToSave))
							PosDeviceManager.getInstance().getReceiptPrinter().openCashBox();

							printReshito(orderToSave);
							
							showPaymentSummary(PosOrderUtil.isDeliveryService(mOrderHeaderActual)? 
									mOrderHeaderActual:orderToSave,mPaymentSummary);

					}else{

						/**
						 * Use actual payment header for printing and showing summary.
						 */
						updatePaymentSummaryInfo(mOrderHeaderActual);
						
						if(PosEnvSettings.getInstance().getPrintSettings().getReceiptPrintingAtPayment()!=EnablePrintingOption.NO){
							
							printBill(getParentForm(),mOrderHeaderActual, canOpenCashBox(mOrderHeaderActual),mPaymentSummary,false,true);
							
						}else if(canOpenCashBox(mOrderHeaderActual) && PosDeviceManager.getInstance().hasCashBox() )
							PosDeviceManager.getInstance().openCashBox();
						
						printReshito(mOrderHeaderActual);
						showPaymentSummary(mOrderHeaderActual,mPaymentSummary);
					}

				}

			}catch(PrinterException prException){
				
				PosLog.write("PosPaymentForm", "makePaymentButtonListner", prException);
				PosFormUtil
				.showErrorMessageBox(getParentForm(),
						prException.getMessage());
			}catch (CardPaymentException e) {

				PosLog.write("PosPaymentForm", "makePaymentButtonListner", e);
				PosFormUtil
				.showErrorMessageBox(getParentForm(),
						e.getMessage());
			} catch (Exception e) {

				PosLog.write("PosPaymentForm", "makePaymentButtonListner", e);
				PosFormUtil.showErrorMessageBox(getParentForm(),
						"Failed to save order. Please contact administrator.");
			}
		}

	};

	/**
	 * Updates/Inserts the order
	 * @param status
	 * @throws Exception
	 */
	private boolean onOrderPaymentStatusChanged(PaymentProcessStatus status) throws Exception {

		boolean result=true;

		mOrderHeaderActual.setPaymentProcessStatus(status);

		switch (status) {

		case INIT:

			result=mOrderBillProvider.saveOrder(mOrderHeaderActual, true, true, false, false);
			mOrderHeaderActual.setNewOrder(false);
			isOrderSaved=true;
			break;

		case PROCESSING:

			result=mOrderBillProvider.saveOrder(mOrderHeaderActual, true, false, false, false);
			break;

		case COMPLETED:
			
			String invoiceNo; 
			updateOrderStatus();
			if (PosOrderUtil.isDeliveryService(mOrderHeaderActual) && 
					mOrderHeaderActual.getStatus()!=PosOrderStatus.Closed)
				invoiceNo="";
			else
				invoiceNo=new PosInvoiceProvider().getInvoiceNo(mOrderHeaderActual.getOrderId(), true);
			
			mOrderHeaderActual.setInvoiceNo(invoiceNo);
			result=mOrderBillProvider.saveOrder(mOrderHeaderActual, true, false, true, true);
			break;

		default:

			break;
		}

		return result;
	}

	/**
	 * Updates the splits info for saving
	 */
	private void updateSplitForPayment() {

		if(!mIsPartialPayment) return;
		/**
		 * 
		 * Sets the actual pay amount to the split.
		 * 
		 */
		
		final double finalPayamount=getBillTotalAmount()
				-mDiscountPaymentPanel.getTenderAmount()
				+mRoundAdjustMentAmount;
		
		mSplitForPayment.setPartPayAdjustment(mSplitAdjustmentPanel.getTenderAmount());
		mSplitForPayment.setRoundingAdjustment(mRoundAdjustMentAmount);
		mSplitForPayment.setDiscount(mDiscountPaymentPanel.getTenderAmount());
		mSplitForPayment.setPayedAmount(finalPayamount);
		mSplitForPayment.setPayed(true);

	}

	/**
	 * @param orderToSave 
	 * @param orderPaymentItems
	 * @return
	 */
	private void updatePaymentSummaryInfo(BeanOrderHeader orderToSave){

		mPaymentSummary.setChangeAmount(orderToSave.getChangeAmount() + orderToSave.getCashOut());
		mPaymentSummary.setRoundAdjustment(orderToSave.getRoundAdjustmentAmount());
		mPaymentSummary.setDiscountedAmount(orderToSave.getBillDiscountAmount());
		mPaymentSummary.setPartPayAdjustment(mSplitAdjustmentPanel.getTenderAmount());
		mPaymentSummary.setTenderedAmount((mBillTotalAmount<=0)?0:orderToSave.getTotalAmountPaid() + orderToSave.getCashOut());
		mPaymentSummary.setUnPaidAmount(PosOrderUtil.getTotalBalanceOnBill(mOrderHeaderActual));
		//		final double balance=mOrderHeaderActual.getTotalAmount() - PosOrderSplitUtil.getSplitTotalPaidAmount(mOrderHeaderActual);
		//		mPaymentSummary.setUnPaidAmount(balance);

	}

	/**
	 * @param paymentSummary
	 */
	private void showPaymentSummary(BeanOrderHeader order,BeanBillPaymentSummaryInfo paymentSummary){

		PosBillPaymentInfoForm billForm = new PosBillPaymentInfoForm(
				order,paymentSummary);
		billForm.setListner(mBillPrintFormListner);
		PosFormUtil
		.showLightBoxModal(getParentForm(), billForm);

	}

	/**
	 * Update the status of selected splits to payed.
	 */
	private void updateActualSplitsStatus(){

		if(mIsPartialPayment && mSelectedSplitItems!=null)
			for(BeanOrderSplit split:mSelectedSplitItems)
				split.setPayed(true);
	}
	/*
	 * update the balance tax values in payment header object 
	 */
	private BeanOrderPaymentHeader updateTaxesToPaymentHeader(BeanOrderHeader order) {
		int a=0;
		BeanOrderPaymentHeader orderPaymentHdr=null;
		
		double detailTotal=0;
		double totalTax1=0;
		double totalTax2=0;
		double totalTax3=0;
		double totalGST=0;
		double totalSC=0;
		
		for(BeanOrderPaymentHeader payHdr : order.getOrderPaymentHeaders()){
			
			if(payHdr.isNew()) 
				orderPaymentHdr=payHdr;
			else{
			
				totalTax1+=payHdr.getTotalTax1();
				totalTax2+=payHdr.getTotalTax2();
				totalTax3+=payHdr.getTotalTax3();
				totalGST+=payHdr.getTotalGST();
				totalSC+=payHdr.getTotalServiceTax();
				detailTotal+=payHdr.getDetailTotal();
			}
			
		}
		

		double orderDetailTotal=order.getDetailTotal();
		double orderTotalTax1=order.getTotalTax1();
		double orderTotalTax2=order.getTotalTax2();
		double orderTotalTax3=order.getTotalTax3();
		double orderTotalGST=order.getTotalGST();
		double orderTotalSC=order.getTotalServiceTax();
	
		
		if (order.getExtraCharges()>0){
			
			 orderDetailTotal+=order.getExtraCharges();
			 orderTotalTax1+=order.getExtraChargeTaxOneAmount();
			 orderTotalTax2+=order.getExtraChargeTaxTwoAmount();
			 orderTotalTax3+=order.getExtraChargeTaxThreeAmount();
			 orderTotalGST+=order.getExtraChargeGSTAmount();
			 orderTotalSC+=order.getExtraChargeSCAmount();
		
		}
		if (orderDetailTotal>detailTotal   ||
				orderTotalTax1>totalTax1  || 
				orderTotalTax2>totalTax2 ||
				orderTotalTax3>totalTax3 ||
				orderTotalGST>totalGST || 
				orderTotalSC>totalSC){
			
			detailTotal= (orderDetailTotal-detailTotal);
			totalTax1= (orderTotalTax1-totalTax1);
			totalTax2=  (orderTotalTax2-totalTax2);
			totalTax3=  (orderTotalTax3-totalTax3);
			totalGST=  (orderTotalGST-totalGST);
			totalSC=  (orderTotalSC-totalSC);
			
			orderPaymentHdr.setDetailTotal(PosCurrencyUtil.roundTo(detailTotal));
			orderPaymentHdr.setTotalTax1(PosCurrencyUtil.roundTo(totalTax1));
			orderPaymentHdr.setTotalTax2(PosCurrencyUtil.roundTo(totalTax2));
			orderPaymentHdr.setTotalTax3(PosCurrencyUtil.roundTo(totalTax3));
			orderPaymentHdr.setTotalGST(PosCurrencyUtil.roundTo(totalGST));
			orderPaymentHdr.setTotalServiceTax(PosCurrencyUtil.roundTo( totalSC));
		}
		orderPaymentHdr.setFinal(true);
			
		return orderPaymentHdr;
		
		
	}
		
	/*
	 * update the balance tax values in payment header object 
	 */
	private void createDiscountForClosedOrders(BeanOrderHeader order) throws Exception {

		BeanOrderDiscount orderDiscount=null;
		if(mOrderHeaderActual.getPreBillDiscount()!=null && mOrderHeaderActual.getPreBillDiscount() !=new PosDiscountItemProvider().getNoneDiscount()){
			
			BeanDiscount discount=mOrderHeaderActual.getPreBillDiscount().clone();
			
			double totalDiscount= 0;
			if (order.getPreBillDiscount()!=null  && order.getPreBillDiscount()!=new PosDiscountItemProvider().getNoneDiscount()){
				
				totalDiscount = ((order.getPreBillDiscount().isPercentage()) ? 
						PosOrderUtil.getTotalAmount(order)
						* order.getPreBillDiscount().getPrice() / 100 : 
						order.getPreBillDiscount().getPrice());
			}
			
			totalDiscount=totalDiscount-mOrderHeaderActual.getBillDiscountAmount();
			if(totalDiscount>0){
				
				orderDiscount=new BeanOrderDiscount(discount);
				orderDiscount.setDiscountedAmount(totalDiscount);
				orderDiscount.setNew(true);
				
				if(order.getBillDiscounts()==null)
					order.setBillDiscounts(new ArrayList<BeanOrderDiscount>());
				order.getBillDiscounts().add(orderDiscount);
				order.setBillDiscountAmount(order.getBillDiscountAmount()+totalDiscount);
				order.setBillDiscountPercentage(order.getBillDiscountAmount()*100/PosOrderUtil.getTotalAmount(order));
				
			}
			 
		} 
		
	}
	
	

	/**
	 * 
	 *  Update the original header object with the values
		This is required for partial payments.
	 */
	private void updateActualHeader(BeanOrderHeader order) {

		final ArrayList<BeanOrderPaymentHeader> orderPayHdrList=new ArrayList<BeanOrderPaymentHeader>();
		orderPayHdrList.add(getOrderPaymentHeader(order));

		if(mOrderHeaderActual.getOrderPaymentHeaders()==null)
			mOrderHeaderActual.setOrderPaymentHeaders(orderPayHdrList);
		else 
			mOrderHeaderActual.getOrderPaymentHeaders().addAll(orderPayHdrList);
		
		
		mOrderHeaderActual.setTotalAmountPaid(order.getTotalAmountPaid()+
				(mIsPartialPayment?mOrderHeaderActual.getTotalAmountPaid():0));
		mOrderHeaderActual.setBillTaxAmount(order.getBillTaxAmount()+
				(mIsPartialPayment?mOrderHeaderActual.getBillTaxAmount():0));
		mOrderHeaderActual.setChangeAmount(order.getChangeAmount()+
				(mIsPartialPayment?mOrderHeaderActual.getChangeAmount():0));
		mOrderHeaderActual.setCashOut(order.getCashOut() +
				(mIsPartialPayment?mOrderHeaderActual.getCashOut():0));
		mOrderHeaderActual.setStatus(order.getStatus());
		mOrderHeaderActual.setClosingDate(order.getClosingDate());
		mOrderHeaderActual.setClosingTime(order.getClosingTime());
		mOrderHeaderActual.setClosedBy(order.getClosedBy());
		mOrderHeaderActual.setNewOrder(order.isNewOrder());
		/**
		 * For split payments
		 */
		if(mIsPartialPayment || mOrderHeaderActual.hasPartialPayments()){

			if(mOrderHeaderActual.getOrderPaymentItems()==null)
				mOrderHeaderActual.setOrderPaymentItems(order.getOrderPaymentItems());
			else if(order.getOrderPaymentItems()!=null)
				mOrderHeaderActual.getOrderPaymentItems().addAll(order.getOrderPaymentItems());

			if(mOrderHeaderActual.getOrderSplits()==null)
				mOrderHeaderActual.setOrderSplits(order.getOrderSplits());
			else if(order.getOrderSplits()!=null)
				mOrderHeaderActual.getOrderSplits().addAll(order.getOrderSplits());

			if(mOrderHeaderActual.getBillDiscounts()==null)
				mOrderHeaderActual.setBillDiscounts(order.getBillDiscounts());
			else if(order.getBillDiscounts()!=null)
				mOrderHeaderActual.getBillDiscounts().addAll(order.getBillDiscounts());

			mOrderHeaderActual.setSplitPartRecieved(PosOrderSplitUtil.getSplitPartAmountRecieved(mOrderHeaderActual));
			mOrderHeaderActual.setSplitPartUsed(PosOrderSplitUtil.getSplitPartAmountUsed(mOrderHeaderActual));

		}else{

			mOrderHeaderActual.setOrderPaymentItems(order.getOrderPaymentItems());
			mOrderHeaderActual.setBillDiscounts(order.getBillDiscounts());
		}

		mOrderHeaderActual.setPaymentProcessStatus(order.getPaymentProcessStatus());
		mOrderHeaderActual.setBillDiscountAmount(order.getBillDiscountAmount()+
				(mIsPartialPayment?mOrderHeaderActual.getBillDiscountAmount():0));
		mOrderHeaderActual.setRoundAdjustmentAmount(order.getRoundAdjustmentAmount()+
				(mIsPartialPayment?mOrderHeaderActual.getRoundAdjustmentAmount():0));

		if (order.getBillDiscountPercentage()>0)
			mOrderHeaderActual.setBillDiscountPercentage(order.getBillDiscountPercentage()); 

		mOrderHeaderActual.setRemarks(order.getRemarks());
		mOrderHeaderActual.setDriverName(order.getDriverName());
		mOrderHeaderActual.setVehicleNumber(order.getVehicleNumber());

		
	}
	/**
	 * 
	 *  Update the original payment header object with the values
	 */
	private BeanOrderPaymentHeader getOrderPaymentHeader(BeanOrderHeader order) {
		
		BeanOrderPaymentHeader orderPaymentHdr=new BeanOrderPaymentHeader();
		
		orderPaymentHdr.setOrderId(order.getOrderId());
		orderPaymentHdr.setTotalAmount(order.getTotalAmount());
		orderPaymentHdr.setDetailTotal(order.getDetailTotal());
		orderPaymentHdr.setTotalTax1(order.getTotalTax1());
		orderPaymentHdr.setTotalTax2(order.getTotalTax2());
		orderPaymentHdr.setTotalTax3(order.getTotalTax3());
		orderPaymentHdr.setTotalGST(order.getTotalGST());
		orderPaymentHdr.setTotalServiceTax(order.getTotalServiceTax());
		orderPaymentHdr.setTotalDetailDiscount(order.getTotalDetailDiscount());
		orderPaymentHdr.setBillDiscountAmount(order.getBillDiscountAmount() );
		orderPaymentHdr.setBillTaxAmount(order.getBillTaxAmount() );

	if (order.getExtraCharges()>0 && !mIsPartialPayment){
		orderPaymentHdr.setDetailTotal(orderPaymentHdr.getDetailTotal()+order.getExtraCharges());
		orderPaymentHdr.setTotalTax1(orderPaymentHdr.getTotalTax1()+order.getExtraChargeTaxOneAmount());
		orderPaymentHdr.setTotalTax2(orderPaymentHdr.getTotalTax2()+order.getExtraChargeTaxTwoAmount());
		orderPaymentHdr.setTotalTax3(orderPaymentHdr.getTotalTax3()+order.getExtraChargeTaxThreeAmount());
		orderPaymentHdr.setTotalGST(orderPaymentHdr.getTotalGST() + order.getExtraChargeGSTAmount());
		orderPaymentHdr.setTotalServiceTax(orderPaymentHdr.getTotalServiceTax() +order.getExtraChargeSCAmount());
 
		orderPaymentHdr.setTotalAmount(order.getTotalAmount()+order.getExtraCharges() +order.getExtraChargeTaxOneAmount()+ 
				order.getExtraChargeTaxTwoAmount()+order.getExtraChargeTaxThreeAmount()+
				order.getExtraChargeSCAmount() +order.getExtraChargeGSTAmount()
				) ;
		} 
		if(mBillTotalAmount!=0){
			orderPaymentHdr.setTotalAmountPaid(order.getTotalAmountPaid());
			orderPaymentHdr.setChangeAmount(order.getChangeAmount());
			orderPaymentHdr.setCashOut(order.getCashOut());
			orderPaymentHdr.setRoundAdjustmentAmount(order.getRoundAdjustmentAmount());
		}else{
			orderPaymentHdr.setTotalAmountPaid(0);
			orderPaymentHdr.setChangeAmount(0);
			orderPaymentHdr.setCashOut(0);
			orderPaymentHdr.setRoundAdjustmentAmount(0);
		}
		orderPaymentHdr.setRemarks(order.getRemarks());
		orderPaymentHdr.setRefund(false);
		
		

		orderPaymentHdr.setShiftId(PosEnvSettings.getInstance().getCashierShiftInfo().getShiftItem().getId());
		orderPaymentHdr.setPaymentTime(PosDateUtil.getDateTime());
		orderPaymentHdr.setPaymentDate(PosEnvSettings.getPosEnvSettings().getPosDate());
		orderPaymentHdr.setRemarks(mRemarks);
		orderPaymentHdr.setAdvance(false);
		orderPaymentHdr.setNew(true);
		
		return orderPaymentHdr;
	}

	/**
	 * 
	 */
	private IPosBillPrintFormListner mBillPrintFormListner = new PosBillPrintFormAdapter() {

		@Override
		public void onDoneClicked() {

			closeForm();
			if (mListner != null)
				mListner.onPaymentDone(PosPaymentForm.this);
		}

		@Override
		public void onRePrintClicked(Object sender,BeanOrderHeader oh) {

//			mPrintRecipt =true;
			printBill(sender,oh, false, mPaymentSummary,false,true);
		}

		@Override
		public void onwholeBillPrint(Object sender) {

			printBill(sender,mOrderHeaderActual, false, null , mOrderHeaderActual.getStatus()==PosOrderStatus.Closed? false:true,false);

		}
		@Override
		public void onRePrintKitchenReceiptClicked(Object sender,
				BeanOrderHeader oh) {
			try {
				
				if( oh.getOrderDetailItems()!=null && oh.getOrderDetailItems().size()>0){
					
					 if(validateKitchenPrinter())
						 PosReceipts.printReceiptToKitchen(mOrderHeaderActual, true);
				}
			} catch (Exception e) {
				PosLog.write("Kitchen Print", "onRePrintKitchenReceiptClicked", e);
				PosFormUtil.showErrorMessageBox(getParentForm(),
						"Failed to print. Please contact administrator.");
			
			}
		}

		@Override
		public void onRePrintReshitoClicked(Object sender, BeanOrderHeader oh) {
			printReshito(oh);
		}
		 
	};

	/**
	 * Sets the order status
	 * @throws Exception 
	 */
	private void updateOrderStatus() throws Exception{

		PosStockInHdrProvider stockInHdrProvider;
		mOrderHeaderActual.setStatus(((mIsPartialPayment)?PosOrderStatus.Partial:PosOrderStatus.Open));

		if(mOrderHeaderActual.getOrderSplits()!=null &&  mOrderHeaderActual.getOrderSplits().size()>0){

			if(!mIsPartialPayment ){

				mOrderHeaderActual.setStatus(PosOrderStatus.Closed);

			}else if(PosCurrencyUtil.roundTo(PosOrderUtil.getTotalAmount(mOrderHeaderActual)  -
					PosOrderSplitUtil.getSplitTotalPaidAmountLessDiscount(mOrderHeaderActual)-
					PosOrderUtil.getBillDiscount(mOrderHeaderActual)) ==0){

				if(PosOrderUtil.isDeliveryService(mOrderHeaderActual))
					mOrderHeaderActual.setStatus(PosOrderStatus.Closed);
				else if(PosFormUtil.showQuestionMessageBox(getParentForm(), MessageBoxButtonTypes.YesNo, 
						"Whole bill has been paid. Do you want to close this order?", null)==MessageBoxResults.Yes){

					mOrderHeaderActual.setStatus(PosOrderStatus.Closed);
					
				}

			}else if(PosOrderUtil.getTotalAmount(mOrderHeaderActual) - PosOrderSplitUtil.getSplitTotalPaidAmount(mOrderHeaderActual) <0){

				//				To DO Ask and paye the balance

				//				final double balanceAmount=PosNumberUtil.roundTo(mOrderHeaderActual.getTotalAmount() - PosOrderSplitUtil.getSplitTotalPaidAmount(mOrderHeaderActual));
				//
				//				if(PosFormUtil.showQuestionMessageBox(this, MessageBoxButtonTypes.YesNo, 
				//						"This oder has balance amount of (" 
				//								+ PosEnvSettings.getInstance().getCurrencySymbol()  
				//								+ String.valueOf(Math.abs(PosNumberUtil.roundTo(balanceAmount)))
				//								+"). Do you want to close this bill by paying balance?", 
				//								null)==MessageBoxResults.Yes){
				//					
				//
				//					//				makePaymentButtonListner.onClicked(mButtonMakePayment);
				//
				//				}
			}

		}else{

			mOrderHeaderActual.setStatus(PosOrderStatus.Closed);
			 
		}
		/**
		 * Updates the closing date and time
		 * 
		 */
		if(mOrderHeaderActual.getStatus()==PosOrderStatus.Closed){

			updateTaxesToPaymentHeader(mOrderHeaderActual);
			createDiscountForClosedOrders(mOrderHeaderActual);
			mOrderHeaderActual.setClosingDate(PosEnvSettings.getPosEnvSettings().getPosDate());
			mOrderHeaderActual.setClosingTime(PosDateUtil.getDateTime());
			mOrderHeaderActual.setClosedBy(PosEnvSettings.getInstance().getCashierShiftInfo()
					.getCashierInfo());
			if(PosEnvSettings.getInstance().getApplicationType()==ApplicationType.StandAlone){
				stockInHdrProvider=new PosStockInHdrProvider();
				stockInHdrProvider.save(mOrderHeaderActual);
			}
		}

	}

	/**
	 * @param orderHeader
	 * @param openCashDrawer
	 * @param paymentSummary
	 */
	private void printBill(Object sender,BeanOrderHeader orderHeader, boolean openCashDrawer, BeanBillPaymentSummaryInfo paymentSummary,boolean isBillPrinting,boolean SOInvoice) {
		
		try {

			PosFormUtil.showPrintConfirmMessage((RootPaneContainer)sender, (PosEnvSettings.getInstance().getPrintSettings().getReceiptPrintingAtPayment()==EnablePrintingOption.FORCE) , orderHeader, openCashDrawer, paymentSummary, isBillPrinting);
				
		}catch (PrinterException err) {

			PosLog.write(this, "printBill", err);
			PosFormUtil.showErrorMessageBox(this, err.getMessage());
		} catch (Exception err) {

			PosLog.write(this, "printBill", err);
			PosFormUtil.showErrorMessageBox(this, "Error in printing!!");
		}
	}
	
	/**
	 * @param oh
	 */
	private void printReshito(BeanOrderHeader oh){
		
		if(PosEnvSettings.getInstance().getPrintSettings().getPrintReshitoAtPayment()!=EnablePrintingOption.NO &&
				PosDeviceManager.getInstance().hasReceiptPrinter() &&
				PosDeviceManager.getInstance().getReceiptPrinter()!=null && PosDeviceManager.getInstance().getReceiptPrinter().isDeviceInitialized()){

			boolean doPrint=true;

			if(PosEnvSettings.getInstance().getPrintSettings().getPrintReshitoAtPayment()==EnablePrintingOption.ASK)
				doPrint=(PosFormUtil.showQuestionMessageBox(this,
						MessageBoxButtonTypes.YesNo,
						"Do you want to print Reshito?",null)==MessageBoxResults.Yes);

			if(doPrint){
				try {

					PosReceipts.printBillReshito(oh,false);

				} catch (Exception e) {

					PosLog.write("PosPaymentForm", "printReshito", e);
					PosFormUtil.showErrorMessageBox(this, "Failed to print reshito. Please check printer connection or contact administrator.");
				}

			}
		}
		
	}

	/**
	 * 
	 */
	private void closeForm() {

		setVisible(false);
		dispose();
	}

	/**
	 * Build the OrderHeader object
	 * 
	 * @return
	 */
	private void  updateOrderHeaderForPayment(BeanOrderHeader order) {


		final double amountPaid= mSelectedPaymentMode==PaymentMode.Online?
				(mBillTotalAmount<=0?0:mBillTotalAmount):
				order.getTotalAmountPaid()
				+ getTenderedAmount()
				- mDiscountPaymentPanel.getTenderAmount()
				-mSplitAdjustmentPanel.getTenderAmount();
		
		order.setTotalAmountPaid(amountPaid);
		/**
		 * SPLIT TO DO
		 * Need to check how the tax was calculated
		 * 
		 * order.setBillTaxAmount(order.getBillTaxAmount());
		 * 
		 */

		final double balanceAmount = getBalanceAmount();
		order.setChangeAmount(order.getChangeAmount()+balanceAmount);
		order.setRoundAdjustmentAmount(order.getRoundAdjustmentAmount()+ mRoundAdjustMentAmount);
		
		if(mIsPartialPayment){

			if(order.getOrderSplits()==null)
				order.setOrderSplits(new ArrayList<BeanOrderSplit>());

			order.getOrderSplits().add(mSplitForPayment);
		}
	}

	/**
	 * Validates the tendered amounts
	 * @return
	 */
	private boolean validatePaymentOptions() {

		if(mSelectedPaymentMode==PaymentMode.Online)
			return true;
		
		if (getBalanceAmount() < 0){ //&& !isPartialPayment) {

			PosFormUtil.showInformationMessageBox(PosPaymentForm.this,
					"The tendered amount does not match the bill amount.");
			return false;
		}
		for (PosTab tab : mTabs) {
			PosPaymentBasePanel paymentPanel = (PosPaymentBasePanel) tab;
			if (paymentPanel.getTenderAmount() != 0)
				if (!((PosPaymentBasePanel) tab).onValidating()){
					mTabControl.setSelectedTab(tab);
					return false;
				}
		}
		return true;
	}

	/**
	 * 
	 */
	private IPosButtonListner imgCancelButtonListner = new PosButtonListnerAdapter() {
		@Override
		public void onClicked(PosButton button) {
			if (mListner != null)
				mListner.onPaymentCancelled(getParentForm());
			setVisible(false);
			mPosPoleDisplay.disPlayBillTotal(getFinalBillTotal());
			dispose();
		}
	};

	/**
	 * 
	 */
	private IPosButtonListner imgResetButtonListner = new PosButtonListnerAdapter() {
		@Override
		public void onClicked(PosButton button) {
			mActievePayment.reset();
		}
	};

 
	/*
	 * 
	 */
	
	private IPosButtonListner imgRemarksButtonListner=new IPosButtonListner() {

		@Override
		public void onClicked(PosButton button) {
				final PosRemarksEditForm remarksform=new PosRemarksEditForm();
				remarksform.setRemarks(mRemarks);
				
				remarksform.setListner(new IPosFormEventsListner() {
					
					@Override
					public void onResetButtonClicked() {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public boolean onOkButtonClicked() {
						mRemarks=remarksform.getRemarks();
						return true;
					}
					
					@Override
					public boolean onCancelButtonClicked() {
						// TODO Auto-generated method stub
						return false;
					}
				});
				
				
				PosFormUtil.showLightBoxModal(getParentForm(), remarksform);
			 
		}
	};

	
	/**
	 * Reset all variables
	 * Same instance of the form is used for multiple payments
	 */
	private void setForNewOrder(){
		
		isOrderSaved=false;
//		mPrintRecipt = false;
		mIsPartialPayment=false;
		mTaxBeforeBillDiscount=0.0;
		mCompletedPayment=0.0;
		mBillTotalAmount=0;
		mRoundAdjustMentAmount = 0;
		mOrderHeaderForPayment=null;
		mOrderHeaderActual=null;
		mSelectedSplitItems=null;
		mSplitForPayment=null;
		mPaymentSummary=null;
		mBillDiscount=null;

		resetAllTabs();
	}

	/**
	 * @return the isOrderSaved
	 */
	public boolean isOrderSaved() {
		return isOrderSaved;
	}


	/**
	 * Reset All tabs
	 * 
	 */
	private void resetAllTabs() {

		for (PosTab paymentTabs : mTabs)
			((PosPaymentBasePanel) paymentTabs).reset();

	}

	/**
	 * @param paymode
	 */
	private void setTabs(PaymentMode paymode) {

		mTabs = createTabs();
		mTabControl.setTabs(mTabs);
		//		mTabControl.setTabVisibility(3, false);
		BeanPaymentModes supportedPaymentMethods=PosPaymentModesProvider.getInstance().getPaymentModes();
		mTabControl.setTabVisibility(0, supportedPaymentMethods.isCanPayByCash());
		mTabControl.setTabVisibility(1, supportedPaymentMethods.isCanPayByCard());
		mTabControl.setTabVisibility(2, supportedPaymentMethods.isCanPayByVouchers());
		mTabControl.setTabVisibility(3, supportedPaymentMethods.isCanPayByCompany());
		/**
		 * Set the visibility of the discount panel on if the bill amount us > 0
		 * This is needed in partial paymenent mode
		 * Refer setOrderHeader()
		 */
		mTabControl.setTabVisibility(mDiscountPaymentPanel, false);
		/**
		 * Set the visibilty of the split tab false
		 * This is needed on when payment by split
		 * Refer setOrderWithSplit()
		 */
		mTabControl.setTabVisibility(mSplitAdjustmentPanel, false);



		setPaymentMode(paymode);
	}

	/**
	 * @return
	 */
	private ArrayList<PosTab> createTabs() {

		final int height = mTabControl.getHeight()
				- PosTabControl.BUTTON_HEIGHT;

		final int width=mTabControl.getWidth();//-(LABEL_TITLE_WIDTH+LABEL_VALUE_WIDTH); 
		ArrayList<PosTab> paymentTabs = new ArrayList<PosTab>();
		mTabsForPayment = new ArrayList<PosTab>();
 
		mCashPaymentPanel = new PosCashPaymentPanel(PosPaymentForm.this,
				width, height);
		mCashPaymentPanel.setListner(mPaymentAmountListner);
		paymentTabs.add(mCashPaymentPanel);

		mCardPaymentPanel = new PosCardPaymentPanel(PosPaymentForm.this,
				width, height);
		mCardPaymentPanel.setListner(mPaymentAmountListner);
		paymentTabs.add(mCardPaymentPanel);

		mCouponPaymentPanel = new PosCouponPaymentPanel(PosPaymentForm.this,
				mTabControl.getWidth(), height);
		mCouponPaymentPanel.setListner(mPaymentAmountListner);
		paymentTabs.add(mCouponPaymentPanel);

		mCompanyPaymentPanel = new PosCreditCustomerPaymentPanel(PosPaymentForm.this,
				mTabControl.getWidth(), height);
		mCompanyPaymentPanel.setListner(mPaymentAmountListner);
		paymentTabs.add(mCompanyPaymentPanel);

		mDiscountPaymentPanel = new PosDiscountPaymentPanel(
				PosPaymentForm.this, mTabControl.getWidth(), height);
		mDiscountPaymentPanel.setListner(mPaymentAmountListner);
		paymentTabs.add(mDiscountPaymentPanel);

		mSplitAdjustmentPanel = new PosSplitPartPaymentAdjustPanel(PosPaymentForm.this,
				mTabControl.getWidth(), height);
		mSplitAdjustmentPanel.setListner(mPaymentAmountListner);
		paymentTabs.add(mSplitAdjustmentPanel);

		mTabsForPayment.add(mCashPaymentPanel);
		mTabsForPayment.add(mCompanyPaymentPanel);
		mTabsForPayment.add(mCouponPaymentPanel);
		mTabsForPayment.add(mCardPaymentPanel);
		mTabsForPayment.add(mDiscountPaymentPanel);
		mTabsForPayment.add(mSplitAdjustmentPanel);

		return paymentTabs;

	}


	/**
	 * Sets the visibilit if the tabs
	 */
	public void resetTabVisibility(){

		mTabControl.setTabVisibility(mCashPaymentPanel, mBillTotalAmount>0);
		mTabControl.setTabVisibility(mSplitAdjustmentPanel, canShowSplitAdjTab());
		mTabControl.setTabVisibility(mDiscountPaymentPanel, mBillTotalAmount>0);
		mTabControl.setTabVisibility(mCouponPaymentPanel, mBillTotalAmount>0);
		mTabControl.setTabVisibility(mCardPaymentPanel, mBillTotalAmount>0);

	}

	/**
	 * Determine whther the split tab can be shown
	 * @return
	 */
	public boolean canShowSplitAdjTab(){

		final boolean isSplitPartAdjTabVisible=(mIsPartialPayment 
				&& (mSplitMethod==SplitBasedOn.Custom)
				&& mOrderHeaderActual.getSplitPartAmountLeft()>0
				);
		return isSplitPartAdjTabVisible;

	}


	/**
	 * @return
	 */
	public PosCardPaymentPanel getCardPaymentPanel() {
		return mCardPaymentPanel;
	}


	/**
	 * @return
	 */
	public PosCreditCustomerPaymentPanel  getCompanyPaymentPanel() {
		return mCompanyPaymentPanel;
	}

	/**
	 * 
	 */
	private IPosPaymentPanelListner mPaymentAmountListner = new IPosPaymentPanelListner() {

		@Override
		public void onTenderAmountChanged(double amount) {
			setAmounts();
		}

		@Override
		public void onAccept() {
			makePaymentButtonListner.onClicked(null);
			
		}
	};
	private SplitBasedOn mSplitMethod;


	/**
	 * @param mListner
	 *            the mListner to set
	 */
	public void setListner(IPosPaymentFormListner listner) {
		this.mListner = listner;
	}
	
	
	public boolean initPayment(){
		
		mRemarks="";
		/**
		 * Show the actual payment/balance in the pole display.                        
		 */

		final double totalBillAmount=getBillTotalAmount();

		/**
		 * Set the total bill amount even if it is partial for split
		 */
		if(PosOrderUtil.isDeliveryService(mOrderHeaderActual)){
			mTabControl.setTabVisibility(mDiscountPaymentPanel, false);
			mBillTotal.setText(PosCurrencyUtil.format(mOrderHeaderActual.getTotalAmount()));
			mExtraCharges.setText(PosCurrencyUtil.format(mOrderHeaderActual.getExtraCharges()+ PosOrderUtil.getExtraChargeTotalTaxAmount(mOrderHeaderActual)));
			mAdvanceAmount.setText(PosCurrencyUtil.format(mOrderHeaderActual.getAdvanceAmount()));
			mDiscountValue.setText(PosCurrencyUtil.format(PosOrderUtil.getBillDiscount(mOrderHeaderActual)));
		}else{
			mBillTotal.setText(PosCurrencyUtil.format(totalBillAmount));
			mExtraCharges.setText(PosCurrencyUtil.format(0));
			mAdvanceAmount.setText(PosCurrencyUtil.format(0));
		}
		

		/***
		 * If there is only balance to pay then Prompt the user
		 * 
		 */
		if(mOrderHeaderActual.getTotalAmount()>0 && mBillTotalAmount<=0 ){  

			if(mBillTotalAmount<0){
				if(PosFormUtil.showQuestionMessageBox(getParentForm(), MessageBoxButtonTypes.YesNo, 
						"This order has balance amount of (" 
								+ PosEnvSettings.getInstance().getCurrencySymbol()  
								+ String.valueOf(Math.abs(PosCurrencyUtil.roundTo(mBillTotalAmount)))
								+"). Do you want to close this bill by paying balance?", 
								null)==MessageBoxResults.Yes){

					makePaymentButtonListner.onClicked(mButtonMakePayment);

				}
			}else{

				
				if( PosFormUtil.showQuestionMessageBox(getParentForm(), MessageBoxButtonTypes.YesNo, 
						"Whole bill has been paid. Do you want to close this order?", null)==MessageBoxResults.Yes){
					makePaymentButtonListner.onClicked(mButtonMakePayment);
				}

			}

			closeForm();
			return false;
		}

		/**
		 * Do not show the ui if it is in the silent mode
		 */
		//			if(isSilentMode){
		//				b=false;
		//			}

		if(mOrderHeaderActual.getPreBillDiscount()!=null && 
				mOrderHeaderActual.getPreBillDiscount() !=new PosDiscountItemProvider().getNoneDiscount() && 
				(!mIsPartialPayment || mSplitMethod==SplitBasedOn.Custom || mSplitMethod==SplitBasedOn.Seat)){
				
			BeanDiscount discount=mOrderHeaderActual.getPreBillDiscount().clone();
			if(!discount.isPercentage()){
				final double amtPayPercentage=(totalBillAmount/PosOrderUtil.getTotalAmount(mOrderHeaderActual))*100;
				discount.setPrice(discount.getPrice()*amtPayPercentage/100);
			}
				
			mDiscountPaymentPanel.setPreBillDiscount(discount);
		}else
			mDiscountPaymentPanel.setPreBillDiscount(null);
		
		mDiscountPaymentPanel.setEnabled(!mIsPartialPayment);
		if(mSelectedPaymentMode.equals(PaymentMode.Company))	
			mCompanyPaymentPanel.setExactAmount();
		return true;
	}

	/* (non-Javadoc)
	 * @see java.awt.Dialog#setVisible(boolean)
	 */
	@Override
	public void setVisible(boolean b) {

		if (b) {

			if (!initPayment())
				return;
			}
		super.setVisible(b);
	}

	/**
	 * @return the mOrderHeader
	 */
	public BeanOrderHeader getOrderHeader() {
		return mOrderHeaderForPayment;
	}

	public BeanOrderHeader getActualOrderHeader(){

		return mOrderHeaderActual;
	}

	/**
	 * @param mOrderHeaderForPayment
	 *            the mOrderHeader to set
	 * @throws SQLException 
	 * @throws CloneNotSupportedException
	 */
	public void setOrderHeaderWithOutSplit(BeanOrderHeader orderHeader) throws SQLException {

		/**
		 * Reset all variables
		 * Same instance of the form is used for multiple payments
		 */
		setForNewOrder();

		this.mOrderHeaderActual = orderHeader;
		/**
		 * Sets the payments done so far. 
		 * This is required to calculate the balance. Also to rewrite to db.
		 */
		setAlreadyPayedPayments(mOrderHeaderActual);
		this.mOrderHeaderForPayment = orderHeader.clone();
		mTaxBeforeBillDiscount=orderHeader.getBillTaxAmount();

		setPartialPayment(false);

		setBillTaxAmounts(mOrderHeaderForPayment);

		mCompletedPayment = PosOrderUtil
				.getTotalPaidAmount(mOrderHeaderForPayment);
		//				+ mOrderHeaderForPayment.getBillDiscountAmount();

		mBillTotalAmount=PosOrderUtil.getTotalBalanceOnBill(mOrderHeaderForPayment) + 
				(mOrderHeaderForPayment.getStatus()==PosOrderStatus.Open? PosOrderUtil.getBillDiscount(mOrderHeaderForPayment):0);
		
		//		if(mBillTotalAmount<0){
		final double itemTotal =PosOrderUtil.getTotalItemAmount(mOrderHeaderForPayment);
		buildPaymentSummaryInfo(itemTotal);
		//		}else
		//			buildPaymentSummaryInfo(mBillTotalAmount);

		resetAllTabs();
		setAmounts();
		resetTabVisibility();
	}


	/**
	 * @param mOrderHeaderForPayment the mOrderHeader to set
	 * @throws Exception 
	 * @throws CloneNotSupportedException
	 */
	public void setOrderHeader(BeanOrderHeader orderHeader) throws Exception {

		/**
		 * 
		 * If the order has partial payments, then 
		 * 		balance amount is > 0 then proceed to pay the rest as another partial payment.
		 * 		balance amount is < 0 process like ordinary order and and pay the balance.
		 * If the order has no partial payments, then process like ordinary order.
		 *  
		 */
		  
		
		if( PosEnvSettings.getInstance().isEnabledHMSIntegration()  && 
				orderHeader!=null && 
				orderHeader.getCustomer().getCustType().getCode().equals(PosCustomerTypeProvider.ROOM_TYPE_CODE)){
			
				mTabControl.setTabCaption(3, "Room");
		}else{
			mTabControl.setTabCaption(3, PaymentMode.Company.getDisplayText());
		}
		final double balanceOnBill=PosOrderUtil.getTotalBalanceOnBill(orderHeader);

		if(orderHeader.hasPartialPayments()  ){

			final int totalBillCount= orderHeader.getOrderSplits()!=null?orderHeader.getOrderSplits().size()+1:1;
			final String billName=PosOrderSplitForm.DEF_BILL_PREFIX+PosStringUtil.paddLeft(String.valueOf(totalBillCount), 2, '0' );

			BeanOrderSplit split=new BeanOrderSplit();
			split.setAmount(balanceOnBill);
			split.setBasedOn(SplitBasedOn.Amount);
			split.setOrderID(orderHeader.getOrderId());
			split.setDescription(billName);
			setOrderWithSplit(orderHeader, split);

		}else{

			setOrderHeaderWithOutSplit(orderHeader);

		}
		if(PosOrderUtil.isDeliveryService(orderHeader))
			mTabControl.setTabVisibility(mDiscountPaymentPanel, false);
		

	}



	/**
	 * @param orderHeader
	 * @param selectedSplitItems
	 * @throws Exception 
	 */
	public void setOrderWithSplit(BeanOrderHeader orderHeader,ArrayList<BeanOrderSplit> selectedSplitItems) throws Exception {

		/**
		 * Reset all variables
		 * Same instance of the form is used for multiple payments
		 */
		setForNewOrder();

		this.mOrderHeaderActual = orderHeader;
		this.mSelectedSplitItems=selectedSplitItems;
		this.mSplitForPayment=PosOrderSplitUtil.prepareSplitsForPayment(selectedSplitItems);
		this.mOrderHeaderForPayment = PosOrderSplitUtil.convertSplitToOrder(orderHeader, mSplitForPayment);
		this.mTaxBeforeBillDiscount=orderHeader.getBillTaxAmount();
		this.mCompletedPayment = 0;
		this.mSplitMethod=selectedSplitItems.get(0).getBasedOn();

		setPartialPayment(true);
		setBillTaxAmounts(mOrderHeaderForPayment);
		mBillTotalAmount=PosOrderUtil.getTotalBalanceOnBill(mOrderHeaderForPayment);
		mBillTotalAmount+=mSplitForPayment.getAdjustAmount();
		
		double totalAmount=mBillTotalAmount ;
		if(PosOrderUtil.isDeliveryService(orderHeader)){
			totalAmount+=orderHeader.getAdvanceAmount();
			totalAmount-=orderHeader.getExtraCharges();
			
		}
		buildPaymentSummaryInfo(totalAmount);
		resetAllTabs();
		setAmounts();
		resetTabVisibility();

		final boolean isSplitPartAdjTabVisible=canShowSplitAdjTab();
		if(isSplitPartAdjTabVisible){

			mTabControl.setTabVisibility(mSplitAdjustmentPanel, isSplitPartAdjTabVisible);
			if(PosFormUtil.showQuestionMessageBox(mParent, MessageBoxButtonTypes.YesNo, 
					"There is part amount ("
							+ PosEnvSettings.getInstance().getCurrencySymbol()
							+String.valueOf(PosCurrencyUtil.format(mOrderHeaderActual.getSplitPartAmountLeft()))
							+") available for this order. Do you want to adjust?", 
							null)==MessageBoxResults.Yes){
				mTabControl.setSelectedTab(mSplitAdjustmentPanel);
			}else{
//				setRoundingAdjustment(0);
//				mCashPaymentPanel.onGotFocus();;
				mTabControl.setSelectedTab(mCashPaymentPanel);
			}
		}

	}

	private double totalForDisplay=00.00;
	private double balanceForDisplay=00.00;
	/**
	 * @param balance 
	 * @param billTotal 
	 * 
	 */
	private void setPoleDisplay(double billTotal, double balance){

		if(totalForDisplay!=billTotal || balanceForDisplay!=balance){

			totalForDisplay=billTotal;
			balanceForDisplay=balance;

			mPosPoleDisplay.disPlayBillSettlement(totalForDisplay,balanceForDisplay);
		}


	}

	/**
	 * @param orderHeader
	 * @param beanOrderSplit
	 * @throws Exception 
	 */
	public void setOrderWithSplit(BeanOrderHeader orderHeader,	BeanOrderSplit beanOrderSplit) throws Exception {

		ArrayList<BeanOrderSplit> selectedSplitItems=new ArrayList<BeanOrderSplit>();
		selectedSplitItems.add(beanOrderSplit);
		setOrderWithSplit(orderHeader, selectedSplitItems);

	}

	/**
	 * Sets the account tab visibility
	 * @param visible
	 */
	public void setCashTabVisible(boolean visible) {
		mTabControl.setTabVisibility(mCashPaymentPanel, visible);
	}

	/**
	 * Sets the account tab visibility
	 * @param visible
	 */
	public void setCardTabVisible(boolean visible) {
		mTabControl.setTabVisibility(mCardPaymentPanel, visible);
	}

	/**
	 * Sets the account tab visibility
	 * @param visible
	 */
	public void setCouponTabVisible(boolean visible) {
		mTabControl.setTabVisibility(mCouponPaymentPanel, visible);
	}

	/**
	 * Sets the tab visibility
	 * @param visible
	 */
	public void setCompanyTabVisible(boolean visible) {
		mTabControl.setTabVisibility(mCompanyPaymentPanel, visible);
	}

	/**
	 * Sets the account tab visibility
	 * @param visible
	 */
	public void setDiscountTabVisible(boolean visible) {
		mTabControl.setTabVisibility(mDiscountPaymentPanel, visible);
	}


	/**
	 * @return the mCantApplyDiscount
	 */
	public boolean canApplyDiscount() {

		boolean canApplyDiscount = true;

		if (mCashPaymentPanel.getTenderAmount() > 0
				|| mCardPaymentPanel.getTenderAmount() > 0
				|| mCouponPaymentPanel.getTenderAmount() > 0
				|| mSplitAdjustmentPanel.getTenderAmount() > 0
				|| (mCompanyPaymentPanel.getCompanyItem() != null && mCompanyPaymentPanel
				.getTenderAmount() > 0)) {
			canApplyDiscount = false;
		}

		return canApplyDiscount;
	}

	//	/**
	//	 * @return the isSilentMode
	//	 */
	//	public boolean isSilentMode() {
	//		return isSilentMode;
	//	}
	//
	//
	//	/**
	//	 * @param isSilentMode the isSilentMode to set
	//	 */
	//	public void setSilentMode(boolean isSilentMode) {
	//		this.isSilentMode = isSilentMode;
	//	}




}
