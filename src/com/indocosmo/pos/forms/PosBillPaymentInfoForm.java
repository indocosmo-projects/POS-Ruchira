package com.indocosmo.pos.forms;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;

import com.indocosmo.barcode.reports.BarCodeLabelPrint;
import com.indocosmo.pos.common.PosEnvSettings;
import com.indocosmo.pos.common.PosLog;
import com.indocosmo.pos.common.beans.BeanBillPaymentSummaryInfo;
import com.indocosmo.pos.common.utilities.PosCurrencyUtil;
import com.indocosmo.pos.common.utilities.PosFormUtil;
import com.indocosmo.pos.common.utilities.PosOrderUtil;
import com.indocosmo.pos.common.utilities.PosPrintingUtil;
import com.indocosmo.pos.common.utilities.PosResUtil;
import com.indocosmo.pos.data.beans.BeanOrderHeader;
import com.indocosmo.pos.forms.components.buttons.PosButton;
import com.indocosmo.pos.forms.components.buttons.listners.IPosButtonListner;
import com.indocosmo.pos.forms.components.buttons.listners.PosButtonListnerAdapter;
import com.indocosmo.pos.forms.components.objectbrowser.IPosBrowsableItem;
import com.indocosmo.pos.forms.listners.IPosBillPrintFormListner;
import com.indocosmo.pos.forms.listners.adapters.PosBillPrintFormAdapter;
import com.indocosmo.pos.terminal.devices.PosDeviceManager;
import com.indocosmo.pos.terminal.devices.poledisplay.PosDevicePoleDisplay;

@SuppressWarnings("serial")
public final class PosBillPaymentInfoForm extends JDialog {

	//	public final static int RETRIEVE_KEY_HEIGHT=40;
	//	public final static int RETRIEVE_KEY_WIDTH=62;

	private static final int PANEL_CONTENT_H_GAP=PosOrderEntryForm.PANEL_CONTENT_H_GAP;
	private static final int PANEL_CONTENT_V_GAP=PosOrderEntryForm.PANEL_CONTENT_V_GAP;

	private static final int H_GAP_BTWN_CMPNTS=1;
	private static final int V_GAP_BTWN_CMPNTS=2;

	private static final int TITLE_PANEL_HEIGHT=30;	

	private static final int BOTTOM_PANEL_HEIGHT=75; 

	private static final int IMAGE_BUTTON_WIDTH=135;
	private static final int IMAGE_BUTTON_HEIGHT=60;

	//	private static final int IMAGE_BUTTON_RETRIEVE_WIDTH=125;
	//	private static final int IMAGE_BUTTON_RETRIEVE_HEIGHT=60;

	private static final int TEXT_FIELD_WIDTH=260;
	private static final int TEXT_FIELD_HEIGHT=30;
	private static final int BALANCE_FIELD_HEIGHT=50;
	//	private static final int CARD_FIELD_WIDTH=210;
	private static final int CONTENT_PANEL_HEIGHT=BALANCE_FIELD_HEIGHT+TEXT_FIELD_HEIGHT*6+V_GAP_BTWN_CMPNTS*6+9;
	private static final int LABEL_WIDTH=167;
	private static final int LABEL_HEIGHT=30;	

	private static final int FORM_HEIGHT=TITLE_PANEL_HEIGHT+CONTENT_PANEL_HEIGHT+BOTTOM_PANEL_HEIGHT+PANEL_CONTENT_V_GAP*8-2;
	private static final int FORM_WIDTH=452;

	private JPanel mContentPane;
	private JPanel mTitlePanel;
	private JPanel mContentPanel;
	private JPanel mBottomPanel;
	private BeanOrderHeader mOrderHeader;
	private BeanBillPaymentSummaryInfo mPaymentSummary;
	private JLabel mlabelTitle;	

	private PosButton mButtonPrint;
	private PosButton mButtonPrintWholeBill;
	private PosButton mButtonDone;

	//	private PosButton mClearButton;

	private JLabel mlabelBillTotal;
	private JTextField mTxtBillTotal;

	private JLabel mlabelDiscountAmount;
	private JTextField mTxtDiscountAmount;

	private JLabel mlabelRoundAdjustmentAmount;
	private JTextField mTxtRoundAdjustmentAmount;

	private JLabel mLabelNetAmount;
	private JTextField mTxtNetAmount;

	private JLabel mLabelTenderedAmount;
	private JTextField mTxtTenderedAmount;

	private JLabel mLabelBillChange;
	private JTextField mTxtBillChange;

	private JLabel mLabelBillBalance;
	private JTextField mTxtBillBalance;

	private JLabel mLabelExtraCharges;
	private JTextField mTxtExtraCharges;

	private JLabel mLabelAdvancePay;
	private JTextField mTxtAdvancePay;
	
	private JLabel mLabelBillNumber;
	private JTextField mTxtBillNumber;	

	private static final String IMAGE_BUTTON_PRINT="dlg_payment_info_ok.png";
	private static final String IMAGE_BUTTON_PRINT_TOUCH="dlg_payment_info_ok_touch.png";

	private static final String IMAGE_BUTTON_CANCEL="dlg_payment_info_cancel.png";
	private static final String IMAGE_BUTTON_CANCEL_TOUCH="dlg_payment_info_cancel_touch.png";	

	private static final Color PANEL_BG_COLOR=new Color(78,128,188);
	private static final Color LABEL_FG_COLOR=Color.WHITE;	

	private IPosBillPrintFormListner mListner;
	private JLabel mLabelPartCashPaid;
	private JTextField mTxtPartCashPaid;
	private boolean mHasPartialPay=false;
	private  boolean isDeliveryService;
	

	private ArrayList<IPosBrowsableItem> printButtonList;

	public PosBillPaymentInfoForm(BeanOrderHeader oh, BeanBillPaymentSummaryInfo paymentSummary){

		mOrderHeader=oh;
		mPaymentSummary=paymentSummary;
		mHasPartialPay=(paymentSummary.getPartAmount()>0 && !paymentSummary.isPartialPayment() && !PosOrderUtil.isDeliveryService(oh));
		initControls();	
	}

	public PosBillPaymentInfoForm(BeanOrderHeader oh){

		mOrderHeader=oh;
		mPaymentSummary=new BeanBillPaymentSummaryInfo();
		mPaymentSummary.setNumber(mOrderHeader.getOrderId());
		mPaymentSummary.setAmount(mOrderHeader.getTotalAmount());
		mPaymentSummary.setChangeAmount(mOrderHeader.getActualBalancePaid());
		mPaymentSummary.setRoundAdjustment(mOrderHeader.getRoundAdjustmentAmount());
		mPaymentSummary.setTenderedAmount(mOrderHeader.getTotalAmountPaid());

		initControls();	
	}

	private void initControls(){
		isDeliveryService=PosOrderUtil.isDeliveryService(mOrderHeader) ;
		final int PARTIAL_HEIGHT=(mHasPartialPay ||isDeliveryService?(TEXT_FIELD_HEIGHT+V_GAP_BTWN_CMPNTS)*2:0);

		setSize(FORM_WIDTH, FORM_HEIGHT+PARTIAL_HEIGHT);
		mContentPane = new JPanel();
		mContentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		mContentPane.setLayout(null);
		setContentPane(mContentPane);

		mTitlePanel = new JPanel();
		mTitlePanel.setBounds(PANEL_CONTENT_H_GAP, 
				PANEL_CONTENT_V_GAP, 
				FORM_WIDTH-PANEL_CONTENT_H_GAP*2, TITLE_PANEL_HEIGHT*2);
		mTitlePanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
		mTitlePanel.setBackground(PANEL_BG_COLOR);
		mTitlePanel.setLayout(null);		
		add(mTitlePanel);

		mContentPanel = new JPanel();
		mContentPanel.setBounds(PANEL_CONTENT_H_GAP, 
				mTitlePanel.getY()+mTitlePanel.getHeight()+PANEL_CONTENT_V_GAP, 
				FORM_WIDTH-PANEL_CONTENT_H_GAP*2, CONTENT_PANEL_HEIGHT+PARTIAL_HEIGHT);
		mContentPanel.setLayout(null);
		mContentPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
		add(mContentPanel);

		mBottomPanel = new JPanel();
		mBottomPanel.setBounds(PANEL_CONTENT_H_GAP, 
				mContentPanel.getY()+ mContentPanel.getHeight()+PANEL_CONTENT_V_GAP, 
				FORM_WIDTH-PANEL_CONTENT_H_GAP*2, BOTTOM_PANEL_HEIGHT);
		mBottomPanel.setLayout(new FlowLayout());
		mBottomPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
		add(mBottomPanel);

		setTitle();
		setBillNumber();
		setBillAmount(mLabelBillNumber);
		setExtraCharges(mlabelBillTotal);
		setDiscountAmount(isDeliveryService?mLabelExtraCharges:mlabelBillTotal);
		setAdvancePayment(mlabelDiscountAmount);
		setRoundAdjustmentAmount(isDeliveryService?mLabelAdvancePay:mlabelDiscountAmount);
		setNetAmount(mlabelRoundAdjustmentAmount);
		setPartCashPaid(mLabelNetAmount);

		setBillBalance(mLabelPartCashPaid);
		setTenderedAmount(mHasPartialPay?mLabelBillBalance:mLabelNetAmount);
		setBillChange(mLabelTenderedAmount);
		setBottomPanel();

		loadBillData();

		this.addWindowListener(new WindowAdapter() {

			/* (non-Javadoc)
			 * @see com.indocosmo.pos.forms.listners.adapters.PosWindowListenerAdapter#windowOpened(java.awt.event.WindowEvent)
			 */
			@Override
			public void windowOpened(WindowEvent e) {

				super.windowOpened(e);
				doBarcodePrinting();
			}
		});

		printButtonList=PosPrintingUtil.buildPrintOptionsAtPayment();
		mButtonPrint.setEnabled((printButtonList.size()>0));
	}	

	/**
	 * 
	 */
	private void doBarcodePrinting(){

		try {

			if(PosEnvSettings.getInstance().getPrintSettings().isBarCodePrintingEnabled()){

				final BarCodeLabelPrint bcLabelPrint=new BarCodeLabelPrint();
				bcLabelPrint.print(mOrderHeader);
			}

		} catch (Exception e1) {

			PosLog.write(PosBillPaymentInfoForm.this, "WinodowOpened", e1);
			PosFormUtil.showErrorMessageBox(PosBillPaymentInfoForm.this, "Failed to print barcode. Please contact Administrator.");
		}

	}

	/**
	 * 
	 */
	public void setFocus(){
		
		this.addWindowListener( new WindowAdapter() {
			public void windowOpened( WindowEvent e ){
				mTxtBillNumber.requestFocus();
			}
		}); 
	}

	private void setTitle(){

		mlabelTitle=new JLabel();
		mlabelTitle.setText("Balance Payable");
		mlabelTitle.setHorizontalAlignment(SwingConstants.CENTER);		
		mlabelTitle.setBounds(0, 0, mTitlePanel.getWidth(), mTitlePanel.getHeight());		
		mlabelTitle.setFont(PosFormUtil.getHeadingFont());
		mlabelTitle.setForeground(LABEL_FG_COLOR);
		mTitlePanel.add(mlabelTitle);		
	}

	private void setBillNumber(){

		int left=PANEL_CONTENT_H_GAP/2;		
		int top=PANEL_CONTENT_V_GAP/2;
		mLabelBillNumber=new JLabel();
		mLabelBillNumber.setText("Invoice Number :");
		mLabelBillNumber.setBorder(new EmptyBorder(2, 2, 2, 2));
		mLabelBillNumber.setOpaque(true);
		mLabelBillNumber.setBackground(Color.LIGHT_GRAY);
		mLabelBillNumber.setHorizontalAlignment(SwingConstants.LEFT);		
		mLabelBillNumber.setBounds(left, top, LABEL_WIDTH, LABEL_HEIGHT);		
		mLabelBillNumber.setFont(PosFormUtil.getLabelFont());

		mContentPanel.add(mLabelBillNumber);

		left=mLabelBillNumber.getX()+mLabelBillNumber.getWidth()+H_GAP_BTWN_CMPNTS;
		mTxtBillNumber=new JTextField();	
		mTxtBillNumber.setHorizontalAlignment(JTextField.LEFT);
		mTxtBillNumber.setBounds(left, top, TEXT_FIELD_WIDTH, TEXT_FIELD_HEIGHT);	
		mTxtBillNumber.setFont(PosFormUtil.getTextFieldBoldFont());	
		mTxtBillNumber.setEditable(false);
		mContentPanel.add(mTxtBillNumber);
	}

	private void setBillAmount(JLabel previousComponent){

		int left=previousComponent.getX();		
		int top=previousComponent.getY()+previousComponent.getHeight()+V_GAP_BTWN_CMPNTS;
		mlabelBillTotal=new JLabel();
		mlabelBillTotal.setText("Bill Total :");
		mlabelBillTotal.setBorder(new EmptyBorder(2, 2, 2, 2));
		mlabelBillTotal.setOpaque(true);
		mlabelBillTotal.setBackground(Color.LIGHT_GRAY);
		mlabelBillTotal.setHorizontalAlignment(SwingConstants.LEFT);		
		mlabelBillTotal.setBounds(left, top, LABEL_WIDTH, LABEL_HEIGHT);		
		mlabelBillTotal.setFont(PosFormUtil.getLabelFont());
		mlabelBillTotal.setFocusable(true);
		mContentPanel.add(mlabelBillTotal);

		left=mlabelBillTotal.getX()+mlabelBillTotal.getWidth()+H_GAP_BTWN_CMPNTS;
		mTxtBillTotal=new JTextField();	
		mTxtBillTotal.setHorizontalAlignment(JTextField.RIGHT);
		mTxtBillTotal.setBounds(left, top, TEXT_FIELD_WIDTH, TEXT_FIELD_HEIGHT);	
		mTxtBillTotal.setFont(PosFormUtil.getTextFieldBoldFont());		
		mTxtBillTotal.setEditable(false);
		//		mTxtBillTotal.setText(PosNumberUtil.formatNumber(mOrderHeader.getTotalAmount()));
		mContentPanel.add(mTxtBillTotal);


	}

	private void setDiscountAmount(JLabel previousComponent){

		int left=previousComponent.getX();		
		int top=previousComponent.getY()+previousComponent.getHeight()+V_GAP_BTWN_CMPNTS;
		mlabelDiscountAmount = new JLabel();
		mlabelDiscountAmount.setText("Discount :");
		mlabelDiscountAmount.setBorder(new EmptyBorder(2, 2, 2, 2));
		mlabelDiscountAmount.setOpaque(true);
		mlabelDiscountAmount.setBackground(Color.LIGHT_GRAY);
		mlabelDiscountAmount.setHorizontalAlignment(SwingConstants.LEFT);
		mlabelDiscountAmount.setBounds(left, top, LABEL_WIDTH,LABEL_HEIGHT);
		mlabelDiscountAmount.setFont(PosFormUtil.getLabelFont());
		mlabelDiscountAmount.setFocusable(true);
		mContentPanel.add(mlabelDiscountAmount);

		left = mlabelDiscountAmount.getX()+mlabelDiscountAmount.getWidth()+H_GAP_BTWN_CMPNTS;
		mTxtDiscountAmount = new JTextField();
		mTxtDiscountAmount.setHorizontalAlignment(JTextField.RIGHT);
		mTxtDiscountAmount.setBounds(left, top, TEXT_FIELD_WIDTH, TEXT_FIELD_HEIGHT);
		mTxtDiscountAmount.setFont(PosFormUtil.getTextFieldBoldFont());
		mTxtDiscountAmount.setEditable(false);
		mContentPanel.add(mTxtDiscountAmount);
	}

	private void setRoundAdjustmentAmount(JLabel previousComponent){

		int left=previousComponent.getX();		
		int top=previousComponent.getY()+previousComponent.getHeight()+V_GAP_BTWN_CMPNTS;
		mlabelRoundAdjustmentAmount = new JLabel();
		mlabelRoundAdjustmentAmount.setText("Adjustment :");
		mlabelRoundAdjustmentAmount.setBorder(new EmptyBorder(2, 2, 2, 2));
		mlabelRoundAdjustmentAmount.setOpaque(true);
		mlabelRoundAdjustmentAmount.setBackground(Color.LIGHT_GRAY);
		mlabelRoundAdjustmentAmount.setHorizontalAlignment(SwingConstants.LEFT);
		mlabelRoundAdjustmentAmount.setBounds(left, top, LABEL_WIDTH,LABEL_HEIGHT);
		mlabelRoundAdjustmentAmount.setFont(PosFormUtil.getLabelFont());
		mlabelRoundAdjustmentAmount.setFocusable(true);
		mContentPanel.add(mlabelRoundAdjustmentAmount);

		left = mlabelRoundAdjustmentAmount.getX()+mlabelRoundAdjustmentAmount.getWidth()+H_GAP_BTWN_CMPNTS;
		mTxtRoundAdjustmentAmount = new JTextField();
		mTxtRoundAdjustmentAmount.setHorizontalAlignment(JTextField.RIGHT);
		mTxtRoundAdjustmentAmount.setBounds(left, top, TEXT_FIELD_WIDTH, TEXT_FIELD_HEIGHT);
		mTxtRoundAdjustmentAmount.setFont(PosFormUtil.getTextFieldBoldFont());
		mTxtRoundAdjustmentAmount.setEditable(false);
		mContentPanel.add(mTxtRoundAdjustmentAmount);
	}

	private void setPartCashPaid(JLabel previousComponent){

		int left=previousComponent.getX();		
		int top=previousComponent.getY()+previousComponent.getHeight()+V_GAP_BTWN_CMPNTS;
		mLabelPartCashPaid=new JLabel();
		mLabelPartCashPaid.setText("Part.Cash Paid:");
		mLabelPartCashPaid.setBorder(new EmptyBorder(2, 2, 2, 2));
		mLabelPartCashPaid.setOpaque(true);
		mLabelPartCashPaid.setBackground(Color.LIGHT_GRAY);
		mLabelPartCashPaid.setHorizontalAlignment(SwingConstants.LEFT);		
		mLabelPartCashPaid.setBounds(left, top, LABEL_WIDTH, LABEL_HEIGHT);		
		mLabelPartCashPaid.setFont(PosFormUtil.getLabelFont());
		mLabelPartCashPaid.setForeground(Color.RED);
		mLabelPartCashPaid.setFocusable(true);
		//		mLabelPartCashPaid.setVisible(false);
		if(mHasPartialPay)
			mContentPanel.add(mLabelPartCashPaid);

		left=mLabelPartCashPaid.getX()+mLabelPartCashPaid.getWidth()+H_GAP_BTWN_CMPNTS;
		mTxtPartCashPaid=new JTextField();	
		mTxtPartCashPaid.setHorizontalAlignment(JTextField.RIGHT);
		mTxtPartCashPaid.setBounds(left, top, TEXT_FIELD_WIDTH, TEXT_FIELD_HEIGHT);	
		mTxtPartCashPaid.setFont(PosFormUtil.getTextFieldBoldFont());	
		mTxtPartCashPaid.setForeground(Color.RED);
		mTxtPartCashPaid.setEditable(false);
		//		mTxtPartCashPaid.setVisible(false);
		//		mTxtTenderedAmount.setText(PosNumberUtil.formatNumber(mOrderHeader.getTotalAmountPaid()));
		if(mHasPartialPay)
			mContentPanel.add(mTxtPartCashPaid);
	}

	private void setBillBalance(JLabel previousComponent){

		int left=previousComponent.getX();		
		int top=previousComponent.getY()+previousComponent.getHeight()+V_GAP_BTWN_CMPNTS;
		mLabelBillBalance=new JLabel();
		mLabelBillBalance.setText("Bill Balance:");
		mLabelBillBalance.setBorder(new EmptyBorder(2, 2, 2, 2));
		mLabelBillBalance.setOpaque(true);
		mLabelBillBalance.setBackground(Color.LIGHT_GRAY);
		mLabelBillBalance.setHorizontalAlignment(SwingConstants.LEFT);		
		mLabelBillBalance.setBounds(left, top, LABEL_WIDTH, LABEL_HEIGHT);		
		mLabelBillBalance.setFont(PosFormUtil.getLabelFont());
		mLabelBillBalance.setForeground(Color.RED);
		mLabelBillBalance.setFocusable(true);
		//		mLabelPartCashPaid.setVisible(false);
		if(mHasPartialPay)
			mContentPanel.add(mLabelBillBalance);

		left=mLabelBillBalance.getX()+mLabelBillBalance.getWidth()+H_GAP_BTWN_CMPNTS;
		mTxtBillBalance=new JTextField();	
		mTxtBillBalance.setHorizontalAlignment(JTextField.RIGHT);
		mTxtBillBalance.setBounds(left, top, TEXT_FIELD_WIDTH, TEXT_FIELD_HEIGHT);	
		mTxtBillBalance.setFont(PosFormUtil.getTextFieldBoldFont());	
		mTxtBillBalance.setForeground(Color.RED);
		mTxtBillBalance.setEditable(false);
		//		mTxtPartCashPaid.setVisible(false);
		//		mTxtTenderedAmount.setText(PosNumberUtil.formatNumber(mOrderHeader.getTotalAmountPaid()));
		if(mHasPartialPay)
			mContentPanel.add(mTxtBillBalance);
	}

	
	

	private void setExtraCharges(JLabel previousComponent){

		int left=previousComponent.getX();		
		int top=previousComponent.getY()+previousComponent.getHeight()+V_GAP_BTWN_CMPNTS;
		mLabelExtraCharges=new JLabel();
		mLabelExtraCharges.setText("Extra Charges :");
		mLabelExtraCharges.setBorder(new EmptyBorder(2, 2, 2, 2));
		mLabelExtraCharges.setOpaque(true);
		mLabelExtraCharges.setBackground(Color.LIGHT_GRAY);
		mLabelExtraCharges.setHorizontalAlignment(SwingConstants.LEFT);		
		mLabelExtraCharges.setBounds(left, top, LABEL_WIDTH, LABEL_HEIGHT);		
		mLabelExtraCharges.setFont(PosFormUtil.getLabelFont());
		mLabelExtraCharges.setForeground(Color.RED);
		mLabelExtraCharges.setFocusable(true);
		//		mLabelPartCashPaid.setVisible(false);
		if(isDeliveryService)
			mContentPanel.add(mLabelExtraCharges);

		left=mLabelExtraCharges.getX()+mLabelExtraCharges.getWidth()+H_GAP_BTWN_CMPNTS;
		mTxtExtraCharges=new JTextField();	
		mTxtExtraCharges.setHorizontalAlignment(JTextField.RIGHT);
		mTxtExtraCharges.setBounds(left, top, TEXT_FIELD_WIDTH, TEXT_FIELD_HEIGHT);	
		mTxtExtraCharges.setFont(PosFormUtil.getTextFieldBoldFont());	
		mTxtExtraCharges.setForeground(Color.RED);
		mTxtExtraCharges.setEditable(false);
		//		mTxtPartCashPaid.setVisible(false);
		//		mTxtTenderedAmount.setText(PosNumberUtil.formatNumber(mOrderHeader.getTotalAmountPaid()));
		if(isDeliveryService)
			mContentPanel.add(mTxtExtraCharges);
	}

	private void setAdvancePayment(JLabel previousComponent){

		int left=previousComponent.getX();		
		int top=previousComponent.getY()+previousComponent.getHeight()+V_GAP_BTWN_CMPNTS;
		mLabelAdvancePay=new JLabel();
		mLabelAdvancePay.setText("Advance Paid:");
		mLabelAdvancePay.setBorder(new EmptyBorder(2, 2, 2, 2));
		mLabelAdvancePay.setOpaque(true);
		mLabelAdvancePay.setBackground(Color.LIGHT_GRAY);
		mLabelAdvancePay.setHorizontalAlignment(SwingConstants.LEFT);		
		mLabelAdvancePay.setBounds(left, top, LABEL_WIDTH, LABEL_HEIGHT);		
		mLabelAdvancePay.setFont(PosFormUtil.getLabelFont());
		mLabelAdvancePay.setForeground(Color.RED);
		mLabelAdvancePay.setFocusable(true);
		//		mLabelPartCashPaid.setVisible(false);
		if(isDeliveryService)
			mContentPanel.add(mLabelAdvancePay);

		left=mLabelAdvancePay.getX()+mLabelAdvancePay.getWidth()+H_GAP_BTWN_CMPNTS;
		mTxtAdvancePay=new JTextField();	
		mTxtAdvancePay.setHorizontalAlignment(JTextField.RIGHT);
		mTxtAdvancePay.setBounds(left, top, TEXT_FIELD_WIDTH, TEXT_FIELD_HEIGHT);	
		mTxtAdvancePay.setFont(PosFormUtil.getTextFieldBoldFont());	
		mTxtAdvancePay.setForeground(Color.RED);
		mTxtAdvancePay.setEditable(false);
		//		mTxtPartCashPaid.setVisible(false);
		//		mTxtTenderedAmount.setText(PosNumberUtil.formatNumber(mOrderHeader.getTotalAmountPaid()));
		if(isDeliveryService)
			mContentPanel.add(mTxtAdvancePay);
	}
	private void setNetAmount(JLabel previousComponent){

		int left=previousComponent.getX();		
		int top=previousComponent.getY()+previousComponent.getHeight()+V_GAP_BTWN_CMPNTS;
		mLabelNetAmount=new JLabel();
		mLabelNetAmount.setText("Net Pay :");
		mLabelNetAmount.setBorder(new EmptyBorder(2, 2, 2, 2));
		mLabelNetAmount.setOpaque(true);
		mLabelNetAmount.setBackground(Color.LIGHT_GRAY);
		mLabelNetAmount.setHorizontalAlignment(SwingConstants.LEFT);		
		mLabelNetAmount.setBounds(left, top, LABEL_WIDTH, LABEL_HEIGHT);		
		mLabelNetAmount.setFont(PosFormUtil.getLabelFont());
		mLabelNetAmount.setFocusable(true);
		mContentPanel.add(mLabelNetAmount);

		left=mLabelNetAmount.getX()+mLabelNetAmount.getWidth()+H_GAP_BTWN_CMPNTS;
		mTxtNetAmount=new JTextField();	
		mTxtNetAmount.setHorizontalAlignment(JTextField.RIGHT);
		mTxtNetAmount.setBounds(left, top, TEXT_FIELD_WIDTH, TEXT_FIELD_HEIGHT);	
		mTxtNetAmount.setFont(PosFormUtil.getTextFieldBoldFont());		 
		mTxtNetAmount.setEditable(false);
		//		mTxtTenderedAmount.setText(PosNumberUtil.formatNumber(mOrderHeader.getTotalAmountPaid()));
		mContentPanel.add(mTxtNetAmount);
	}


	private void setTenderedAmount(JLabel previousComponent){

		int left=previousComponent.getX();		
		int top=previousComponent.getY()+previousComponent.getHeight()+V_GAP_BTWN_CMPNTS;
		mLabelTenderedAmount=new JLabel();
		mLabelTenderedAmount.setText("Total Tendered :");
		mLabelTenderedAmount.setBorder(new EmptyBorder(2, 2, 2, 2));
		mLabelTenderedAmount.setOpaque(true);
		mLabelTenderedAmount.setBackground(Color.LIGHT_GRAY);
		mLabelTenderedAmount.setHorizontalAlignment(SwingConstants.LEFT);		
		mLabelTenderedAmount.setBounds(left, top, LABEL_WIDTH, LABEL_HEIGHT);		
		mLabelTenderedAmount.setFont(PosFormUtil.getLabelFont());
		mLabelTenderedAmount.setFocusable(true);
		mContentPanel.add(mLabelTenderedAmount);

		left=mLabelTenderedAmount.getX()+mLabelTenderedAmount.getWidth()+H_GAP_BTWN_CMPNTS;
		mTxtTenderedAmount=new JTextField();	
		mTxtTenderedAmount.setHorizontalAlignment(JTextField.RIGHT);
		mTxtTenderedAmount.setBounds(left, top, TEXT_FIELD_WIDTH, TEXT_FIELD_HEIGHT);	
		mTxtTenderedAmount.setFont(PosFormUtil.getTextFieldBoldFont());		 
		mTxtTenderedAmount.setEditable(false);
		//		mTxtTenderedAmount.setText(PosNumberUtil.formatNumber(mOrderHeader.getTotalAmountPaid()));
		mContentPanel.add(mTxtTenderedAmount);
	}

	private void setBillChange(JLabel previousComponent){

		int left=previousComponent.getX();		
		int top=previousComponent.getY()+previousComponent.getHeight()+V_GAP_BTWN_CMPNTS;
		mLabelBillChange=new JLabel();
		mLabelBillChange.setText("Change :");
		mLabelBillChange.setBorder(new EmptyBorder(2, 2, 2, 2));
		mLabelBillChange.setOpaque(true);
		mLabelBillChange.setBackground(Color.LIGHT_GRAY);
		mLabelBillChange.setHorizontalAlignment(SwingConstants.LEFT);
		mLabelBillChange.setVerticalTextPosition(JLabel.TOP);
		mLabelBillChange.setBounds(left, top, LABEL_WIDTH, LABEL_HEIGHT+20);		
		mLabelBillChange.setFont(PosFormUtil.getLabelFont());
		mLabelBillChange.setFocusable(true);
		mContentPanel.add(mLabelBillChange);

		left=mLabelBillChange.getX()+mLabelBillChange.getWidth()+H_GAP_BTWN_CMPNTS;
		//		left=PANEL_CONTENT_H_GAP;
		top=mLabelTenderedAmount.getY()+mLabelTenderedAmount.getHeight()+V_GAP_BTWN_CMPNTS;
		mTxtBillChange=new JTextField();	
		mTxtBillChange.setHorizontalAlignment(JTextField.RIGHT);
		mTxtBillChange.setBounds(left, top, TEXT_FIELD_WIDTH, BALANCE_FIELD_HEIGHT);	
		mTxtBillChange.setFont(PosFormUtil.getTextFieldBoldFont().deriveFont(35f));	
		mTxtBillChange.setEditable(false);
		mTxtBillChange.setForeground(Color.RED);
		mContentPanel.add(mTxtBillChange);
	}

	private void setBottomPanel(){

		
		printButtonList=PosPrintingUtil.buildPrintOptions();
		
		mButtonPrint=new PosButton();		
		mButtonPrint.setText("Re-Print");
		mButtonPrint.setImage(PosResUtil.getImageIconFromResource(IMAGE_BUTTON_PRINT));
		mButtonPrint.setTouchedImage(PosResUtil.getImageIconFromResource(IMAGE_BUTTON_PRINT_TOUCH));	
		mButtonPrint.setHorizontalAlignment(SwingConstants.CENTER);		
		mButtonPrint.setSize(IMAGE_BUTTON_WIDTH, IMAGE_BUTTON_HEIGHT );	
		mButtonPrint.setOnClickListner(printButtonListner);
		mBottomPanel.add(mButtonPrint);

		mButtonPrintWholeBill=new PosButton();		
		mButtonPrintWholeBill.setText("Whole Bill");
		mButtonPrintWholeBill.setImage(PosResUtil.getImageIconFromResource(IMAGE_BUTTON_PRINT));
		mButtonPrintWholeBill.setTouchedImage(PosResUtil.getImageIconFromResource(IMAGE_BUTTON_PRINT_TOUCH));	
		mButtonPrintWholeBill.setHorizontalAlignment(SwingConstants.CENTER);		
		mButtonPrintWholeBill.setSize(IMAGE_BUTTON_WIDTH, IMAGE_BUTTON_HEIGHT );	
		mButtonPrintWholeBill.setOnClickListner(wholeBillPrintButtonListner);
		if(mPaymentSummary.isPartialPayment() || mHasPartialPay)
			mBottomPanel.add(mButtonPrintWholeBill);

		mButtonPrint.setEnabled(false);
		mButtonPrintWholeBill.setEnabled(false);
		if(PosDeviceManager.getInstance().hasReceiptPrinter()){
			if(PosDeviceManager.getInstance().getReceiptPrinter()!=null ){
				mButtonPrint.setEnabled(true);
				mButtonPrintWholeBill.setEnabled(true);
			}
		}
		mButtonDone=new PosButton();
		mButtonDone.setDefaultButton(true);
		mButtonDone.setText("Done");
		mButtonDone.setImage(PosResUtil.getImageIconFromResource(IMAGE_BUTTON_CANCEL));
		mButtonDone.setTouchedImage(PosResUtil.getImageIconFromResource(IMAGE_BUTTON_CANCEL_TOUCH));	
		mButtonDone.setHorizontalAlignment(SwingConstants.CENTER);		
		mButtonDone.setSize( IMAGE_BUTTON_WIDTH, IMAGE_BUTTON_HEIGHT);		
		mButtonDone.setOnClickListner(mDoneButtonListner);
		mBottomPanel.add(mButtonDone);	
	}


	/**
	 * 
	 */
	private IPosButtonListner printButtonListner=new PosButtonListnerAdapter() {
		@Override
		public void onClicked(PosButton button) {

//			if(printButtonList.size()==1)
//				doRePrint(printButtonList.get(0));
//			else
				PosPrintingUtil.showPrintOptions(PosBillPaymentInfoForm.this, billPrintListner, printButtonList);
			
			

		}

	};

	 

	/*
	 * 
	 */
	private IPosBillPrintFormListner billPrintListner =new PosBillPrintFormAdapter(){
		
		@Override
		public void onRePrintClicked(Object sender) {
			if(mListner!=null)
				mListner.onRePrintClicked(PosBillPaymentInfoForm.this, mOrderHeader);
			 

		}
		
		@Override
		public void onRePrintKitchenReceiptClicked(Object sender){
			if(mListner!=null)
				mListner.onRePrintKitchenReceiptClicked(PosBillPaymentInfoForm.this, mOrderHeader);
			 
		}
		
		@Override
		public void onRePrintBarcodeClicked(Object sender) {
			doBarcodePrinting();
			 
		}
		
		@Override
		public void onRePrintReshitoClicked(Object sender) {
			if(mListner!=null)
				mListner.onRePrintReshitoClicked(PosBillPaymentInfoForm.this, mOrderHeader);
		}
		
	};


	private IPosButtonListner wholeBillPrintButtonListner=new PosButtonListnerAdapter() {
		@Override
		public void onClicked(PosButton button) {
			if(mListner!=null)
				mListner.onwholeBillPrint(PosBillPaymentInfoForm.this);
		}
	};

	private  IPosButtonListner mDoneButtonListner= new PosButtonListnerAdapter() {
		@Override
		public void onClicked(PosButton button) {	
			setVisible(false);
			dispose();	
			if(mListner!=null)
				mListner.onDoneClicked();
		}
	};	

	/**
	 * 
	 */
	private void loadBillData(){ 

		//mTxtBillNumber.setText(PosOrderUtil.getShortOrderIDFromOrderID(mPaymentSummary.getNumber()));
		mTxtBillNumber.setText(mOrderHeader.getInvoiceNo());
		mTxtBillTotal.setText(PosCurrencyUtil.format(mPaymentSummary.getAmount()));
		mTxtDiscountAmount.setText(PosCurrencyUtil.format(mPaymentSummary.getDiscountedAmount()));
		mTxtRoundAdjustmentAmount.setText(PosCurrencyUtil.format(mPaymentSummary.getRoundAdjustment()
				+mPaymentSummary.getPartPayAdjustment()));

		mTxtExtraCharges.setText(PosCurrencyUtil.format(mOrderHeader.getExtraCharges()+ PosOrderUtil.getExtraChargeTotalTaxAmount(mOrderHeader)));
		mTxtAdvancePay.setText(PosCurrencyUtil.format(mOrderHeader.getAdvanceAmount()));
		
		final double netAmo=mPaymentSummary.getAmount()
				-mPaymentSummary.getDiscountedAmount()
				+mPaymentSummary.getRoundAdjustment()
				-mPaymentSummary.getPartPayAdjustment() 
				+ mOrderHeader.getExtraCharges()+ PosOrderUtil.getExtraChargeTotalTaxAmount(mOrderHeader)
				- mOrderHeader.getAdvanceAmount() ;

		mTxtNetAmount.setText(PosCurrencyUtil.format(netAmo));
		mTxtPartCashPaid.setText(PosCurrencyUtil.format(mPaymentSummary.getPartAmount()));

		final double mBalanceOnBill=netAmo-mPaymentSummary.getPartAmount();
		mTxtBillBalance.setText(PosCurrencyUtil.format(mBalanceOnBill));

		mTxtTenderedAmount.setText(PosCurrencyUtil.format(mPaymentSummary.getTenderedAmount()));
		mTxtBillChange.setText(PosCurrencyUtil.format(mPaymentSummary.getChangeAmount()));

		setPoleDisplay(mBalanceOnBill,mPaymentSummary.getChangeAmount());

	}

	/**
	 * @param listner
	 */
	public void setListner(IPosBillPrintFormListner listner) {	
		mListner=listner;
	}

	/**
	 * @param billTotal
	 * @param balance
	 */
	private void setPoleDisplay(double billTotal, double balance){

		final PosDevicePoleDisplay poleDisplay=PosDevicePoleDisplay.getInstance();
		poleDisplay.disPlayBillSettlement(billTotal,balance);


	}

 

}
