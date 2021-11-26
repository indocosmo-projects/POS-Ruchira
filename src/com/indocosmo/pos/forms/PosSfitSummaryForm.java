/**
 * 
 */
package com.indocosmo.pos.forms;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;

import com.indocosmo.pos.common.PosEnvSettings;
import com.indocosmo.pos.common.PosLog;
import com.indocosmo.pos.common.utilities.PosCurrencyUtil;
import com.indocosmo.pos.common.utilities.PosDateUtil;
import com.indocosmo.pos.common.utilities.PosFormUtil;
import com.indocosmo.pos.common.utilities.PosNumberUtil;
import com.indocosmo.pos.data.beans.BeanCashierShift;
import com.indocosmo.pos.data.beans.BeanPayModeBase.PaymentMode;
import com.indocosmo.pos.data.beans.BeanShiftSummary;
import com.indocosmo.pos.data.providers.shopdb.PosCashierShiftProvider;
import com.indocosmo.pos.data.providers.shopdb.PosShiftSummaryProvider;
import com.indocosmo.pos.forms.components.buttons.PosButton;
import com.indocosmo.pos.forms.components.buttons.listners.IPosButtonListner;
import com.indocosmo.pos.forms.components.textfields.PosItemBrowsableField;
import com.indocosmo.pos.forms.components.textfields.PosTouchableDigitalField;
import com.indocosmo.pos.forms.components.textfields.listners.IPosTouchableFieldListner;
import com.indocosmo.pos.reports.shift.PosShiftSummaryReport;
import com.indocosmo.pos.terminal.devices.PosDeviceManager;
import com.indocosmo.pos.terminal.devices.printer.PosDevicePrinter;

/**
 * @author jojesh
 *
 */
public class PosSfitSummaryForm extends PosBaseForm{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final int PANEL_CONTENT_V_GAP = 2;
	private static final int PANEL_CONTENT_H_GAP = 3;
	
	private static final int LABEL_WIDTH = 170;
	private static final int LABEL_HEIGHT = 35;
		protected static final int TEXT_FIELD_WIDTH=200;
	protected static final int TEXT_FIELD_HEIGHT=35;
	private static final int TEXT_FIELD_WIDTH_HOUR = 125;
	private static final int TEXT_FIELD_WIDTH_MINUTE = 165;
	private static final int MESSAGE_PANEL_HEIGHT = 20;
	
	private static final int SHIFT_DETAIL_PANEL_HEIGHT =0;// 88;
	private static final int SHIFT_COLLECTION_DETAIL_PANEL_HEIGHT = (TEXT_FIELD_HEIGHT* 13 + MESSAGE_PANEL_HEIGHT *5  +PANEL_CONTENT_V_GAP* 18) ;//440;

	private static final int INNER_PANEL_WIDTH = (LABEL_WIDTH + TEXT_FIELD_WIDTH)*2 + PANEL_CONTENT_H_GAP*5;
	
	private static final int PANEL_WIDTH = INNER_PANEL_WIDTH+ PANEL_CONTENT_H_GAP*2;// 700;
	private static final int PANEL_HEIGHT = SHIFT_DETAIL_PANEL_HEIGHT+SHIFT_COLLECTION_DETAIL_PANEL_HEIGHT+PANEL_CONTENT_V_GAP;
	protected static final int INVOICE_NO_FIELD_WIDTH=(INNER_PANEL_WIDTH -LABEL_WIDTH-PANEL_CONTENT_H_GAP*8)/2+1;

	private static final Border labelPadding = new EmptyBorder(2, 2, 2, 2);
	private JPanel mShiftSummaryPanel;
	private JTextField mTxtOpeningFloat;
	private JTextField mTxtCashReceipts;
	private JTextField mTxtCardReceipts;
	private JTextField mTxtVoucherReceipts;
	private JTextField mTxtAccountsReceivable;
	private JTextField mTxtSales;
	private JTextField mTxtCashRefund;
	private JTextField mTxtCashOut;
	private JTextField mTxtVoucherBalance;
	private JTextField mTxtExpenses;
	private JTextField mTxtTotalInvoice;
	private JTextField mTxtAdvanceCash;
	private JTextField mTxtAdvanceCard;
	private JTextField mTxtClosedOrderAdvance;
	private JTextField mTxtTotalDiscount;
	private JTextField mTxtTotalTax;
	private JTextField mTxtOpeningInvoiceNo;
	private JTextField mTxtClosingInvoiceNo;
	JTextField mTxtAdvanceVoucher;
	JTextField mTxtAdvanceEBS;
	JTextField mTxtCardRefund;
	JTextField mTxtEBSReceipts;
	JTextField mTxtNetCash;
	
	private PosTouchableDigitalField mTxtHourFrom;
	private PosTouchableDigitalField mTxtMinuteFrom;
	private PosTouchableDigitalField mTxtHourTo;
	private PosTouchableDigitalField mTxtMinuteTo;
	private JPanel mShiftDetailPanel;
	private JPanel mShiftCollectionDetailPanel;
 
	 
	private BeanCashierShift mCashierShiftInfo;
	 
	private BeanShiftSummary mShiftSummaryReport; 
	public PosSfitSummaryForm() {
		super("Shift Summary", PANEL_WIDTH, PANEL_HEIGHT);

		createShiftDetailControls();
		setOkButtonCaption("Print");
		setCancelButtonCaption("Close");
		setFieldValues();
	 	loadShiftTxnAmounts();	
	 	
	 	setOkButtonEnabled((PosDeviceManager.getInstance().getReceiptPrinter()!=null 
	 			&& PosDeviceManager.getInstance().getReceiptPrinter().isDeviceInitialized() ));
		 
	}
	/**
	 * getl
	 */
	private void setFieldValues() {
//		mCashierShiftInfo = (new PosCashierShiftProvider()).getShiftDetails(PosEnvSettings.getInstance().getStation());
		
		mCashierShiftInfo=PosEnvSettings.getInstance().getCashierShiftInfo();
	 		resetFields();
	}
	

	private void resetFields(){
		
		mTxtTotalInvoice.setText("0");
		mTxtOpeningInvoiceNo.setText("");
		mTxtClosingInvoiceNo.setText("");
		mTxtOpeningFloat.setText("0.00");
		mTxtCashReceipts.setText("0.00");
//		mTxtCashReturned.setText("0.00");
		mTxtCardReceipts.setText("0.00");
		mTxtCashOut.setText("0.00");
		mTxtVoucherBalance.setText("0.00");
		mTxtExpenses.setText("0.00");
		mTxtVoucherReceipts.setText("0.00");
//		mTxtVoucherBalance.setText("0.00");
		mTxtAccountsReceivable.setText("0.00");
		mTxtCashRefund.setText("0.00");
		mTxtAdvanceCash.setText("0.00");
		mTxtAdvanceCard.setText("0.00");
		mTxtClosedOrderAdvance.setText("0.00");
		mTxtTotalDiscount.setText("0.00");
		mTxtTotalTax.setText("0.00");
		
		mTxtAdvanceVoucher.setText("0.00");
		mTxtAdvanceEBS.setText("0.00");
		mTxtCardRefund.setText("0.00");
		mTxtEBSReceipts.setText("0.00");
		mTxtNetCash.setText("0.00");
 
		
	}
	/**
	 * Load the payment details in this shift here. 
	 * Set the payments in the fields.
	 * @throws Exception 
	 * 
	 */
	private void loadShiftTxnAmounts()   {

		final PosShiftSummaryProvider summaryProvider=new PosShiftSummaryProvider();
		
		String orderTimeFrom;
		String orderTimeTo;
		 
		try {
			
		    mTxtOpeningFloat.setText( PosCurrencyUtil.format(PosEnvSettings.getInstance().getTillOpenCashierShiftInfo().getOpeningFloat()));

			orderTimeFrom = (mTxtMinuteFrom.getText().trim().length()!=0 || mTxtHourFrom.getText().trim().length()!=0)?PosDateUtil.buildStringTime(mTxtHourFrom.getText().trim(), mTxtMinuteFrom.getText().trim(), "00",PosDateUtil.TIME_SEPERATOR):null;
			orderTimeTo = (mTxtMinuteTo.getText().trim().length()!=0 || mTxtHourTo.getText().trim().length()!=0)?PosDateUtil.buildStringTime(mTxtHourTo.getText().trim(), mTxtMinuteTo.getText().trim(), "00", PosDateUtil.TIME_SEPERATOR):null;

			if(validateSearchCriteria(orderTimeFrom,orderTimeTo)){
				
				mShiftSummaryReport= summaryProvider.getShiftSummaryReport (mCashierShiftInfo,null,  PosEnvSettings.getInstance().getPosDate(),
						mCashierShiftInfo.getOpeningDate(), (orderTimeFrom!=null)?(mCashierShiftInfo.getOpeningDate()+" "+orderTimeFrom):mCashierShiftInfo.getOpeningTime(), PosDateUtil.getDate(),(orderTimeTo!=null)?(PosDateUtil.getDate()+" "+orderTimeTo):PosDateUtil.getDateTime(),false);

				mTxtCardReceipts.setText(PosCurrencyUtil.format(mShiftSummaryReport.getCardReceipts()));
				mTxtCashReceipts.setText(PosCurrencyUtil.format(mShiftSummaryReport.getCashReceipts()));
				mTxtAccountsReceivable.setText(PosCurrencyUtil.format(mShiftSummaryReport.getAccountsReceivable()));
				mTxtVoucherReceipts.setText(PosCurrencyUtil.format(mShiftSummaryReport.getVoucherReceipts()));
				mTxtEBSReceipts.setText(PosCurrencyUtil.format(mShiftSummaryReport.getOnlineReceipts()));
				mTxtClosedOrderAdvance.setText(PosCurrencyUtil.format(mShiftSummaryReport.getClosedOrderAdvance()));
				
				mTxtCashRefund.setText(PosCurrencyUtil.format(mShiftSummaryReport.getCashRefund()));	
				mTxtCardRefund.setText(PosCurrencyUtil.format(mShiftSummaryReport.getCardRefund()));	
				
				mTxtCashOut.setText(PosCurrencyUtil.format(mShiftSummaryReport.getCashOut()));
				mTxtVoucherBalance.setText(PosCurrencyUtil.format(mShiftSummaryReport.getVoucherBalance()));
				mTxtSales.setText(PosCurrencyUtil.format(mShiftSummaryReport.getNetSale()));
				
				mTxtAdvanceCash.setText(PosCurrencyUtil.format(mShiftSummaryReport.getCashAdvance()));
				mTxtAdvanceCard.setText(PosCurrencyUtil.format(mShiftSummaryReport.getCardAdvance()));
				mTxtAdvanceVoucher.setText(PosCurrencyUtil.format(mShiftSummaryReport.getVoucherAdvance()));
				mTxtAdvanceEBS.setText(PosCurrencyUtil.format(mShiftSummaryReport.getOnlineAdvance()));

				mTxtExpenses.setText(PosCurrencyUtil.format(mShiftSummaryReport.getExpense()));
				mTxtNetCash.setText(PosCurrencyUtil.format(mShiftSummaryReport.getNetCash()));
				
				mTxtTotalDiscount.setText(PosCurrencyUtil.format(mShiftSummaryReport.getTotalDiscount()));
				mTxtTotalTax.setText(PosCurrencyUtil.format(mShiftSummaryReport.getTotalTax()));
				
				mTxtTotalInvoice.setText(String.valueOf(mShiftSummaryReport.getOrderCount()));
				if(mShiftSummaryReport.getOrderCount()>0){
					mTxtOpeningInvoiceNo.setText(mShiftSummaryReport.getOpeningInvoiceNo());
					mTxtClosingInvoiceNo.setText(mShiftSummaryReport.getClosingInvoiceNo());
				}
			}
		} catch (Exception e) {
			
			PosLog.write(this,"loadShiftTxnAmounts" , e);
			PosFormUtil.showErrorMessageBox (this,"Failed get  shift summary report. Please check log for details.");
		}
	}
	
	/**
	 * @param orderTimeFrom
	 * @param orderTimeTo
	 */
	private boolean validateSearchCriteria(String orderTimeFrom, String orderTimeTo) {
		boolean valid = true;
		if ((mTxtHourFrom.getText().trim().length() != 0)
				&& (mTxtHourFrom.getText().trim().length() != 2)) {
			PosFormUtil.showErrorMessageBox(this, "Invalid Hour.");
			valid = false;
			mTxtHourFrom.requestFocus();
		} else if ((mTxtMinuteFrom.getText().trim().length() != 0)
				&& (mTxtMinuteFrom.getText().trim().length() != 2)) {
			PosFormUtil.showErrorMessageBox(this, "Invalid Minute.");
			valid = false;
			mTxtMinuteFrom.requestFocus();
		} 
		else if (orderTimeFrom !=null && (!PosDateUtil.validateTime(orderTimeFrom))) {
			PosFormUtil.showErrorMessageBox(this, "Time is not in proper format.");
			valid = false;
			mTxtHourFrom.requestFocus();
		}
		else if ((mTxtHourTo.getText().trim().length() != 0)
				&& (mTxtHourTo.getText().trim().length() != 2)) {
			PosFormUtil.showErrorMessageBox(this, "Invalid Hour.");
			valid = false;
			mTxtHourTo.requestFocus();
		} else if ((mTxtMinuteTo.getText().trim().length() != 0)
				&& (mTxtMinuteTo.getText().trim().length() != 2)) {
			PosFormUtil.showErrorMessageBox(this, "Invalid Minute.");
			valid = false;
			mTxtMinuteTo.requestFocus();
		} 
		else if (orderTimeTo != null && (!PosDateUtil.validateTime(orderTimeTo))) {
			PosFormUtil.showErrorMessageBox(this, "Time is not in proper format.");
			valid = false;
			mTxtHourTo.requestFocus();
		}
		return valid;
	}
	/**
	 * 
	 */
	private void createShiftDetailControls() {
		createCriteriaFields();
		setCollectionDetails();
	}
	/**
	 * 
	 */
	private void createCriteriaFields() {

		mShiftDetailPanel = new JPanel();
		mShiftDetailPanel.setSize(PANEL_WIDTH, SHIFT_DETAIL_PANEL_HEIGHT);
		mShiftDetailPanel.setLayout(null);
		mShiftDetailPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
		mShiftDetailPanel.setLocation(0, 0);
//		mShiftSummaryPanel.add(mShiftDetailPanel);
		JLabel labelMessage=new JLabel();
		labelMessage.setText("Shift details.");
		labelMessage.setHorizontalAlignment(SwingConstants.CENTER);		
		labelMessage.setPreferredSize(new Dimension(mShiftDetailPanel.getWidth()-(PANEL_CONTENT_H_GAP/2+1), 40));				
		labelMessage.setOpaque(true);
		labelMessage.setBackground(LABEL_BG_COLOR);
		labelMessage.setForeground(Color.WHITE);
		labelMessage.setFont(PosFormUtil.getLabelFont());
		//		mShiftDetailPanel.add(labelMessage);
		createTimeSelectionControl();
//		createSummaryButton();
		//		createCashierShiftSelectionControl();
	}

	/**
	 * 
	 */
	private void createTimeSelectionControl() {
		
		JLabel mlabelTimeFrom = new JLabel("Time From(hh:mm) :");
		mlabelTimeFrom.setOpaque(true);
		mlabelTimeFrom.setBackground(Color.LIGHT_GRAY);
		mlabelTimeFrom.setBorder(labelPadding);
		//		mlabelTimeFrom.setPreferredSize(new Dimension(LABEL_WIDTH, LABEL_HEIGHT));
		setLabelFont(mlabelTimeFrom);
		mlabelTimeFrom.setBounds(PANEL_CONTENT_H_GAP, PANEL_CONTENT_V_GAP, LABEL_WIDTH, LABEL_HEIGHT+5);
		mShiftDetailPanel.add(mlabelTimeFrom);

		int left = mlabelTimeFrom.getX()+LABEL_WIDTH;
		int top = PANEL_CONTENT_V_GAP;
		mTxtHourFrom = new PosTouchableDigitalField(this,
				TEXT_FIELD_WIDTH_HOUR);
		mTxtHourFrom.setTitle("Hour");
		mTxtHourFrom.hideResetButton(true);
		mTxtHourFrom.setLocation(left, top);
		mTxtHourFrom.getTexFieldComponent().setDocument(PosNumberUtil.createDecimalDocument());
		mShiftDetailPanel.add(mTxtHourFrom);

		left = mTxtHourFrom.getX() + mTxtHourFrom.getWidth();

		mTxtMinuteFrom = new PosTouchableDigitalField(this,
				TEXT_FIELD_WIDTH_MINUTE);
		mTxtMinuteFrom.setTitle("Minute");
		//		mTxtMinuteFrom.hideResetButton(true);
		mTxtMinuteFrom.setLocation(left, top);
		mTxtMinuteFrom.getTexFieldComponent().setDocument(PosNumberUtil.createDecimalDocument());
		mTxtMinuteFrom.setListner(new IPosTouchableFieldListner() {

			@Override
			public void onValueSelected(Object value) {
				// TODO A0.00uto-generated method stub

			}

			@Override
			public void onReset() {
				mTxtHourFrom.reset();
			}
		});
		mShiftDetailPanel.add(mTxtMinuteFrom);


		left =PANEL_CONTENT_H_GAP;
		top =  mlabelTimeFrom.getY()+mlabelTimeFrom.getHeight()+PANEL_CONTENT_V_GAP;
		JLabel mlabelTimeTo = new JLabel("Time To(hh:mm) :");
		mlabelTimeTo.setOpaque(true);
		mlabelTimeTo.setBackground(Color.LIGHT_GRAY);
		mlabelTimeTo.setBorder(labelPadding);
		setLabelFont(mlabelTimeTo);
		mlabelTimeTo.setBounds(left, top,LABEL_WIDTH, LABEL_HEIGHT+5);
		mShiftDetailPanel.add(mlabelTimeTo);

		left = mlabelTimeTo.getX()+LABEL_WIDTH;

		mTxtHourTo = new PosTouchableDigitalField(this,
				TEXT_FIELD_WIDTH_HOUR);
		mTxtHourTo.setTitle("Hour");
		mTxtHourTo.hideResetButton(true);
		mTxtHourTo.setLocation(left, top);
		mTxtHourTo.getTexFieldComponent().setDocument(PosNumberUtil.createDecimalDocument());
		mShiftDetailPanel.add(mTxtHourTo);

		left = mTxtHourTo.getX() + mTxtHourTo.getWidth();

		mTxtMinuteTo = new PosTouchableDigitalField(this,
				TEXT_FIELD_WIDTH_MINUTE);
		mTxtMinuteTo.setTitle("Minute");
		//		mTxtMinuteTo.hideResetButton(true);
		mTxtMinuteTo.setLocation(left, top);
		mTxtMinuteTo.getTexFieldComponent().setDocument(PosNumberUtil.createDecimalDocument());
		mTxtMinuteTo.setListner(new IPosTouchableFieldListner() {

			@Override
			public void onValueSelected(Object value) {
				// TODO A0.00uto-generated method stub

			}

			@Override
			public void onReset() {
				mTxtHourTo.reset();
			}
		});
		mShiftDetailPanel.add(mTxtMinuteTo);

	}
	
	/**
	 * @param title
	 * @param width
	 * @param height
	 * @return
	 */
	private JPanel createBoxedPanel(String title, int width, int height ) {

		JPanel boxPanel = new JPanel();
		Dimension size = new Dimension(width, height);
		boxPanel.setBackground(Color.white);
		boxPanel.setPreferredSize(size);
		boxPanel.setSize(size);
		boxPanel.setBorder(new LineBorder(Color.LIGHT_GRAY));
		boxPanel.setLayout(new FlowLayout(FlowLayout.LEFT,2,2));
		

		JLabel labelTitle = new JLabel();
		labelTitle.setText(title);
		labelTitle.setHorizontalAlignment(SwingConstants.CENTER);
		labelTitle.setPreferredSize(new Dimension(width, MESSAGE_PANEL_HEIGHT));
		labelTitle.setSize(new Dimension(width, MESSAGE_PANEL_HEIGHT));
		labelTitle.setOpaque(true);
		labelTitle.setBackground(LABEL_BG_COLOR);
		labelTitle.setForeground(Color.WHITE);
		labelTitle.setFont(PosFormUtil.getSubHeadingFont());
		boxPanel.add(labelTitle);
		return boxPanel;
	}
	private void setCollectionDetails() {
		
		setCollectionDetailsPanel();
		setInvoiceNoRange();
		setTotalNumberOfOrders();
		setOpeningFloat();
		createSalesControls();
		createAdvanceNNetCashControls();
	}
	 
	/*
	 * 
	 */
	private void createSalesControls(){
		
		final int salesPanelHeight=MESSAGE_PANEL_HEIGHT +TEXT_FIELD_HEIGHT *3 + PANEL_CONTENT_V_GAP*5;
		final int refundPanelHeight=MESSAGE_PANEL_HEIGHT +TEXT_FIELD_HEIGHT *1+ PANEL_CONTENT_V_GAP*3;
		final int netSalePanelHeight=MESSAGE_PANEL_HEIGHT +TEXT_FIELD_HEIGHT *2+ PANEL_CONTENT_V_GAP*5;

	
		JPanel salesPanel=createBoxedPanel("Sales", PANEL_WIDTH-10, salesPanelHeight);
		mShiftCollectionDetailPanel.add(salesPanel);
		mTxtCashReceipts =createCollectionDetailField(PaymentMode.Cash.getDisplayText() +  " :",salesPanel);
		mTxtCardReceipts = createCollectionDetailField(PaymentMode.Card.getDisplayText() +  " :",salesPanel);
		mTxtVoucherReceipts = createCollectionDetailField(PaymentMode.Coupon.getDisplayText() +  " :",salesPanel);
		mTxtAccountsReceivable =createCollectionDetailField(PaymentMode.Company.getDisplayText() +  " :",salesPanel);;
		mTxtEBSReceipts = createCollectionDetailField(PaymentMode.Online.getDisplayText() +  " :",salesPanel);
		mTxtClosedOrderAdvance = createCollectionDetailField("Pre. Advance :",salesPanel);
	
		JPanel refundPanel=createBoxedPanel("Refund", PANEL_WIDTH-10, refundPanelHeight);
		mShiftCollectionDetailPanel.add(refundPanel);
		mTxtCashRefund = createCollectionDetailField(PaymentMode.Cash.getDisplayText() +  " :",refundPanel);
		 mTxtCardRefund = createCollectionDetailField(PaymentMode.Card.getDisplayText() +  " :",refundPanel);
			
		JPanel netSalePanel=createBoxedPanel("Balance", PANEL_WIDTH-10, netSalePanelHeight);
		mShiftCollectionDetailPanel.add(netSalePanel);
		mTxtCashOut = createCollectionDetailField("Cash Out (Card) :",netSalePanel);
		mTxtVoucherBalance = createCollectionDetailField(PaymentMode.Coupon.getDisplayText() +  " Balance :",netSalePanel);
		mTxtSales = createCollectionTotalField("Net Sale :",netSalePanel);

	}
	
	/*
	 * 
	 */
	private void createAdvanceNNetCashControls(){
		
		final int advancePanelHeight=MESSAGE_PANEL_HEIGHT +TEXT_FIELD_HEIGHT *2 + PANEL_CONTENT_V_GAP*5;
		final int netCashPanelHeight=MESSAGE_PANEL_HEIGHT +TEXT_FIELD_HEIGHT *2+ PANEL_CONTENT_V_GAP*5;
		
 
		
			JPanel advancePanel=createBoxedPanel("Advance", PANEL_WIDTH-10, advancePanelHeight);
			mShiftCollectionDetailPanel.add(advancePanel);
			
			mTxtAdvanceCash =createCollectionDetailField(PaymentMode.Cash.getDisplayText() +  " :",advancePanel);
			mTxtAdvanceCard =createCollectionDetailField(PaymentMode.Card.getDisplayText() +  " :",advancePanel);
			mTxtAdvanceVoucher = createCollectionDetailField(PaymentMode.Coupon.getDisplayText() +  " :",advancePanel);
			mTxtAdvanceEBS =createCollectionDetailField(PaymentMode.Online.getDisplayText() +  " :",advancePanel);
			
			JPanel netCashPanel=createBoxedPanel("Miscellaneous", PANEL_WIDTH-10, netCashPanelHeight);
			mShiftCollectionDetailPanel.add(netCashPanel);

			mTxtExpenses = createCollectionDetailField("Expense :",netCashPanel);
			mTxtTotalTax= createCollectionDetailField("Tax :",netCashPanel);
			mTxtTotalDiscount= createCollectionDetailField("Discount :",netCashPanel);
			mTxtNetCash = createCollectionTotalField("Net Cash :",netCashPanel);
			
	}
	/**
	 * 
	 */
	private void setCollectionDetailsPanel() {
		
		mShiftCollectionDetailPanel = new JPanel();
		mShiftCollectionDetailPanel.setSize(PANEL_WIDTH, SHIFT_COLLECTION_DETAIL_PANEL_HEIGHT);
		mShiftCollectionDetailPanel.setLayout(new FlowLayout(FlowLayout.LEFT,2,
				PANEL_CONTENT_V_GAP));
		mShiftCollectionDetailPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
		mShiftCollectionDetailPanel.setLocation(0, PANEL_CONTENT_V_GAP+mShiftDetailPanel.getHeight());
		mShiftSummaryPanel.add(mShiftCollectionDetailPanel);

	}
	/**
	 * 
	 */
	private JTextField createCollectionDetailField(String title, JPanel rootPanel) {
		
		JLabel titleLabel = new JLabel(title);
		titleLabel.setBorder(labelPadding);
		titleLabel.setPreferredSize(new Dimension(LABEL_WIDTH, LABEL_HEIGHT));
		titleLabel.setOpaque(true);
		titleLabel.setBackground(Color.LIGHT_GRAY);
		setLabelFont(titleLabel);
		rootPanel.add(titleLabel);

		JTextField txtFiled = new JTextField();
		txtFiled.setFont(PosFormUtil.getTextFieldFont());
		txtFiled.setPreferredSize(new Dimension(TEXT_FIELD_WIDTH,
				TEXT_FIELD_HEIGHT));
		txtFiled.setEditable(false);
		txtFiled.setHorizontalAlignment(SwingConstants.RIGHT);
		rootPanel.add(txtFiled);
		return txtFiled;
		
	}
	/**
	 * 
	 */
	private JTextField createCollectionTotalField(String title, JPanel rootPanel) {
		
		JLabel titleLabel = new JLabel(title);
		titleLabel.setBorder(labelPadding);
		titleLabel.setPreferredSize(new Dimension(LABEL_WIDTH, LABEL_HEIGHT));
		titleLabel.setOpaque(true);
		titleLabel.setBackground(Color.LIGHT_GRAY);
		titleLabel.setFont(PosFormUtil.getHeadingFont());
		rootPanel.add(titleLabel);

		JTextField txtFiled = new JTextField();
		txtFiled.setFont(PosFormUtil.getTextFieldFont());
		txtFiled.setPreferredSize(new Dimension(TEXT_FIELD_WIDTH,
				TEXT_FIELD_HEIGHT));
		txtFiled.setEditable(false);
		txtFiled.setHorizontalAlignment(SwingConstants.RIGHT);
		txtFiled.setFont(PosFormUtil.getHeadingFont());
		rootPanel.add(txtFiled);
		return txtFiled;
		
	}
	/* (non-Javadoc)
	 * @see com.indocosmo.pos.forms.PosBaseForm#onOkButtonClicked()
	 */
	@Override
	public boolean onOkButtonClicked() {
		
		if (mShiftSummaryReport!=null)
			doPrintReport(mShiftSummaryReport);
		
		return false;
	}
	/**
	 * 
	 */
	private void setTotalNumberOfOrders() {

		 	
		JLabel titleLabel = new JLabel("Total Bills :");
		titleLabel.setBorder(labelPadding);
		titleLabel.setPreferredSize(new Dimension(LABEL_WIDTH+ 1, LABEL_HEIGHT));
		titleLabel.setOpaque(true);
		titleLabel.setBackground(Color.LIGHT_GRAY);
		setLabelFont(titleLabel);
		mShiftCollectionDetailPanel.add(titleLabel);

		mTxtTotalInvoice = new JTextField();
		mTxtTotalInvoice.setFont(PosFormUtil.getTextFieldFont());
		mTxtTotalInvoice.setPreferredSize(new Dimension(TEXT_FIELD_WIDTH+ 1 ,
				TEXT_FIELD_HEIGHT));
		mTxtTotalInvoice.setEditable(false);
		mTxtTotalInvoice.setHorizontalAlignment(SwingConstants.RIGHT);
		mShiftCollectionDetailPanel.add(mTxtTotalInvoice);
	 
	}
	
	/**
	 * 
	 */
	private void setInvoiceNoRange() {

		
		JLabel titleLabel = new JLabel("Invoice No :");
		titleLabel.setBorder(labelPadding);
		titleLabel.setPreferredSize(new Dimension(LABEL_WIDTH+1, LABEL_HEIGHT));
		titleLabel.setOpaque(true);
		titleLabel.setBackground(Color.LIGHT_GRAY);
		setLabelFont(titleLabel);
		mShiftCollectionDetailPanel.add(titleLabel);

		mTxtOpeningInvoiceNo = new JTextField();
		mTxtOpeningInvoiceNo.setFont(PosFormUtil.getTextFieldFont());
		mTxtOpeningInvoiceNo.setPreferredSize(new Dimension(INVOICE_NO_FIELD_WIDTH,
				TEXT_FIELD_HEIGHT));
		mTxtOpeningInvoiceNo.setEditable(false);
		mTxtOpeningInvoiceNo.setHorizontalAlignment(SwingConstants.RIGHT);
		mShiftCollectionDetailPanel.add(mTxtOpeningInvoiceNo);
		
		JLabel separatorLabel = new JLabel("-");
		separatorLabel.setBorder(labelPadding);
		separatorLabel.setPreferredSize(new Dimension(PANEL_CONTENT_H_GAP*4, LABEL_HEIGHT));
		separatorLabel.setOpaque(true);
//		separatorLabel.setBackground(Color.LIGHT_GRAY);
		setLabelFont(separatorLabel);
		mShiftCollectionDetailPanel.add(separatorLabel);
		
		
		mTxtClosingInvoiceNo = new JTextField();
		mTxtClosingInvoiceNo.setFont(PosFormUtil.getTextFieldFont());
		mTxtClosingInvoiceNo.setPreferredSize(new Dimension(INVOICE_NO_FIELD_WIDTH,
				TEXT_FIELD_HEIGHT));
		mTxtClosingInvoiceNo.setEditable(false);
		mTxtClosingInvoiceNo.setHorizontalAlignment(SwingConstants.RIGHT);
		mShiftCollectionDetailPanel.add(mTxtClosingInvoiceNo);
	}
	/**
	 * Create field for Opening Float
	 */
	private void setOpeningFloat() {
		
		JLabel titleLabel = new JLabel("Opening Cash :");
		titleLabel.setBorder(labelPadding);
		titleLabel.setPreferredSize(new Dimension(LABEL_WIDTH+ 1, LABEL_HEIGHT));
		titleLabel.setOpaque(true);
		titleLabel.setBackground(Color.LIGHT_GRAY);
		setLabelFont(titleLabel);
		mShiftCollectionDetailPanel.add(titleLabel);

		mTxtOpeningFloat = new JTextField();
		mTxtOpeningFloat.setFont(PosFormUtil.getTextFieldFont());
		mTxtOpeningFloat.setPreferredSize(new Dimension(TEXT_FIELD_WIDTH+ 2,
				TEXT_FIELD_HEIGHT));
		mTxtOpeningFloat.setEditable(false);
		mTxtOpeningFloat.setHorizontalAlignment(SwingConstants.RIGHT);
		mShiftCollectionDetailPanel.add(mTxtOpeningFloat);
	 
	}
	 
	 

 
 
	private void setLabelFont(JLabel label){

		label.setFont(PosFormUtil.getLabelFont());
	}
	/* (non-Javadoc)
	 * @see com.indocosmo.pos.forms.PosBaseForm#setContentPanel(javax.swing.JPanel)
	 */
	@Override
	protected void setContentPanel(JPanel panel) {

		mShiftSummaryPanel = panel;
		mShiftSummaryPanel.setLayout(null);
		mShiftSummaryPanel.setBorder(null);
	}
	
	/*
	 * @param BeanShiftSummaryReport
	 */
	private void doPrintReport(final BeanShiftSummary shiftSummaryReport ) {

		SwingWorker<Boolean, String> swt = new SwingWorker<Boolean, String>() {
			@Override
			protected Boolean doInBackground() throws Exception {
				
				try {
					final PosDevicePrinter printer=PosDeviceManager.getInstance().getReceiptPrinter();
					
					if(printer!=null){
						
						final PosShiftSummaryReport report =new PosShiftSummaryReport( shiftSummaryReport);
//						report.setOverridePrinterSettings(allowMultipleCopies);
//						report.initPrint();
						printer.print(report);
						PosFormUtil.showInformationMessageBox(null, "Shift Summary Report has been printed.");
						
					}else{
						
						PosFormUtil.showInformationMessageBox(null, "Receipt Printer not configured.");
					}
				} catch (Exception e) {
					
					PosLog.write(PosSfitSummaryForm.this, "doPrintReport", e);
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
		PosFormUtil.showBusyWindow(this,"Please wait ...");
	}

}
