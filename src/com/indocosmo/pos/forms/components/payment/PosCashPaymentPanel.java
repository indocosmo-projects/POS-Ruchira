package com.indocosmo.pos.forms.components.payment;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.indocosmo.pos.common.PosEnvSettings;
import com.indocosmo.pos.common.utilities.PosCurrencyUtil;
import com.indocosmo.pos.common.utilities.PosFormUtil;
import com.indocosmo.pos.data.beans.BeanOrderHeader;
import com.indocosmo.pos.data.beans.BeanOrderPayment;
import com.indocosmo.pos.data.beans.BeanPayModeBase.PaymentMode;
import com.indocosmo.pos.forms.PosOrderEntryForm;
import com.indocosmo.pos.forms.PosPaymentForm;
import com.indocosmo.pos.forms.components.buttons.PosButton;
import com.indocosmo.pos.forms.components.buttons.listners.IPosButtonListner;
import com.indocosmo.pos.forms.components.keypads.PosNumKeypad;
import com.indocosmo.pos.forms.components.keypads.PosNumKeypad.CancelActions;
import com.indocosmo.pos.forms.components.keypads.PosSoftKey;


@SuppressWarnings("serial")
public final class PosCashPaymentPanel extends PosPaymentBasePanel{	
	
	private JLabel mLabelTendered;
	private JTextField mTxtTendered;

	private static final int TEXT_FIELD_WIDTH=200;
	private static final int TEXT_FIELD_HEIGHT=35;

	private static final int LABEL_WIDTH=100;
	private static final int LABEL_HEIGHT=35;	

	private static final int PANEL_CONTENT_H_GAP=PosOrderEntryForm.PANEL_CONTENT_H_GAP;
	private static final int PANEL_CONTENT_V_GAP=PosOrderEntryForm.PANEL_CONTENT_V_GAP;

//	private static final int PANEL_CASH_HEIGHT=200;
	private static final int PANEL_CASH_WIDTH=320;
	
	private final static String QUICK_KEY_IMAGE="deno_key.png";
	private final static String QUICK_KEY_IMAGE_TOUCH="deno_key_touch.png";
	
	private final static String EXACT_KEY_IMAGE="exact_pay.png";
	private final static String EXACT_KEY_IMAGE_TOUCH="exact_pay_touch.png";

	private static final String[] mQuickCash=PosEnvSettings.getInstance().getUISetting().getOrderEntryUISettings().getPaymentPanelSetting().getQuickPaymentOptions();
	private static final int QUK_PAY_OPTNS_NO =6;

	private JPanel mCashPanel;
	private JPanel mQuickCashPanel;
//	private PosButton mButtonExact;
	private JLabel mLabelActualAmountGiven;
//	private JTextField mTxtActualAmountGiven;
	private PosNumKeypad mPosNumKeypad;
	private JTextField mCurrentCtrl;
	private boolean isPartialPayment;
	private JPanel mDetailPanel;
	
	public PosCashPaymentPanel(PosPaymentForm parent, int width, int height) {
		super(parent,PaymentMode.Cash.getDisplayText(),PaymentMode.Cash);
//		setKeyStroke(KeyStroke.getKeyStroke(KeyEvent.VK_F9, 0));
		setMnemonicChar('h');
		setBounds(0,0,width,height);
		setLayout(null);
		setOpaque(true);
		setCashDetails();
		createKeypadControl();
		reset();
	}	

	private void setCashDetails(){	
		int left=(getWidth()-PANEL_CASH_WIDTH-PosNumKeypad.LAYOUT_WIDTH -PANEL_CONTENT_H_GAP)/2;		
		int top=0;
		
//		
//		mDetailPanel=new JPanel();
//		int width=LABEL_WIDTH + TEXT_FIELD_WIDTH  + PANEL_CONTENT_H_GAP +PANEL_CONTENT_H_GAP*2 ;
//		int height=500;
//	
//		 
//		mDetailPanel.setSize(320, height);
//		mDetailPanel.setBounds(left, top, width, height);
//		mDetailPanel.setLayout(null);		
//		add(mDetailPanel);
		
		
		mCashPanel=new JPanel();
		mCashPanel.setBounds(left, top, PANEL_CASH_WIDTH,getHeight());
		mCashPanel.setBorder(new MatteBorder(1, 1, 1, 1,  Color.LIGHT_GRAY));
		mCashPanel.setLayout(null);
		add(mCashPanel);
		createDetails();
		createQuickEntryPanel(mQuickCash);
	}

	private void createDetails(){	
		int top=PANEL_CONTENT_V_GAP/4+1;
		mLabelTendered=new JLabel();
		mLabelTendered.setText("To Pay :");
		mLabelTendered.setBorder(new EmptyBorder(2, 2, 2, 2));
		mLabelTendered.setOpaque(true);
		mLabelTendered.setBackground(Color.LIGHT_GRAY);
		mLabelTendered.setHorizontalAlignment(SwingConstants.LEFT);		
		mLabelTendered.setBounds(PANEL_CONTENT_H_GAP/4+5, top, LABEL_WIDTH, LABEL_HEIGHT);		
		mLabelTendered.setFont(PosFormUtil.getLabelFont());
		mLabelTendered.setFocusable(true);
		mCashPanel.add(mLabelTendered);

		int left=mLabelTendered.getX()+mLabelTendered.getWidth()+PANEL_CONTENT_H_GAP/8;
		mTxtTendered=new JTextField();	
		mTxtTendered.setHorizontalAlignment(JTextField.RIGHT);
		mTxtTendered.setBounds(left, top, TEXT_FIELD_WIDTH, TEXT_FIELD_HEIGHT);	
		mTxtTendered.setFont(PosFormUtil.getTextFieldFont());
		mTxtTendered.addFocusListener(focusListener);
		mTxtTendered.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
					
				onValueAccepted();
			}
		});
		
//		mTxtTendered.getDocument().addDocumentListener(new DocumentListener() {
//			@Override
//			public void removeUpdate(DocumentEvent e) {}
//			@Override
//			public void insertUpdate(DocumentEvent e) {
//				onTenderAmountChanged(mTxtTendered.getText());
//			}
//			
//			@Override
//			public void changedUpdate(DocumentEvent e) {}
//		});
		mCashPanel.add(mTxtTendered);
		
		top=mLabelTendered.getX()+mLabelTendered.getHeight()+PANEL_CONTENT_V_GAP;
		
		mLabelActualAmountGiven=new JLabel();
		mLabelActualAmountGiven.setText("Tendered :");
		mLabelActualAmountGiven.setHorizontalAlignment(SwingConstants.LEFT);		
		mLabelActualAmountGiven.setBounds(PANEL_CONTENT_H_GAP, top, LABEL_WIDTH, LABEL_HEIGHT);		
		mLabelActualAmountGiven.setFont(PosFormUtil.getLabelFont());
		mLabelActualAmountGiven.setFocusable(true);
		mLabelActualAmountGiven.setVisible(false);
		mCashPanel.add(mLabelActualAmountGiven);

//		left=mLabelActualAmountGiven.getX()+mLabelActualAmountGiven.getWidth()+PANEL_CONTENT_H_GAP;
//		mTxtActualAmountGiven=new JTextField();	
//		mTxtActualAmountGiven.setHorizontalAlignment(JTextField.RIGHT);
//		mTxtActualAmountGiven.setBounds(left, top, TEXT_FIELD_WIDTH, TEXT_FIELD_HEIGHT);	
//		mTxtActualAmountGiven.setFont(PosFormUtil.getTextFieldFont());
//		mTxtActualAmountGiven.setDocument(PosNumberUtil.createDecimalDocument());
//		mTxtActualAmountGiven.addFocusListener(focusListener);
//		mTxtActualAmountGiven.setVisible(false);
//		mCashPanel.add(mTxtActualAmountGiven);
		
	}

	private void createKeypadControl(){
		mPosNumKeypad=new PosNumKeypad(mTxtTendered);
//		int left=getWidth()-PosNumKeypad.LAYOUT_WIDTH ;
		int left=mCashPanel.getX() + mCashPanel.getWidth() + PANEL_CONTENT_V_GAP ;
		mPosNumKeypad.setBorder(null);
		mPosNumKeypad.setResetOnAccept(false);
		mPosNumKeypad.setLocation(left, PANEL_CONTENT_V_GAP);
		mPosNumKeypad.setCancelActions(CancelActions.Clear);
//		mTxtTendered.getDocument().addDocumentListener(new DocumentListener() {
//			@Override
//			public void removeUpdate(DocumentEvent e) {}
//			@Override
//			public void insertUpdate(DocumentEvent e) {
//				onTenderAmountChanged(mTxtTendered.getText());
//			}
//			
//			@Override
//			public void changedUpdate(DocumentEvent e) {}
//		});
		add(mPosNumKeypad);
	}
	
	
	private FocusListener focusListener = new FocusAdapter() {
		@Override
		public void focusGained(FocusEvent e) {
			JTextField jtf = (JTextField) e.getSource();
			if(!mPosNumKeypad.getDisplayTxtField().equals(jtf))
				mPosNumKeypad.setDisplayTextField(jtf);
			if (mCurrentCtrl != null)
				mCurrentCtrl.setBackground(Color.WHITE);
			mCurrentCtrl = jtf;
			mCurrentCtrl.setBackground(Color.GREEN);
			mTxtTendered.getDocument().addDocumentListener(new DocumentListener() {
				@Override
				public void removeUpdate(DocumentEvent e) {
					onTenderAmountChanged(mTxtTendered.getText());
				}
				@Override
				public void insertUpdate(DocumentEvent e) {
					onTenderAmountChanged(mTxtTendered.getText());
				}
				
				@Override
				public void changedUpdate(DocumentEvent e) {}
			});
//			mTxtActualAmountGiven.getDocument().addDocumentListener(new DocumentListener() {
//				@Override
//				public void removeUpdate(DocumentEvent e) {}
//				@Override
//				public void insertUpdate(DocumentEvent e) {
//					mTxtTendered.setText(mTxtActualAmountGiven.getText());
//				}
//				
//				@Override
//				public void changedUpdate(DocumentEvent e) {}
//			});
		}
	};

	private void createQuickEntryPanel(String[] items){
		final int height=PosSoftKey.KEY_HEIGHT*3+PANEL_CONTENT_V_GAP*4;//getHeight()-PANEL_CASH_HEIGHT;
		mQuickCashPanel=new JPanel();
		mQuickCashPanel.setBounds(0, getHeight()-PANEL_CONTENT_V_GAP-height,
				PANEL_CASH_WIDTH-1,height);
		mQuickCashPanel.setLayout(new FlowLayout(FlowLayout.CENTER,PANEL_CONTENT_H_GAP,PANEL_CONTENT_V_GAP));
		mQuickCashPanel.setBorder(new MatteBorder(1, 1, 0, 0,  Color.LIGHT_GRAY));
		//		mQuickCashPanel.setBackground(Color.CYAN);
		final String currencySymbol=PosEnvSettings.getInstance().getCurrencySymbol();
		createExactAmountButton();
		for(int index=0; index<QUK_PAY_OPTNS_NO;index++){
			PosButton quickButton=new PosButton();
			quickButton.registerKeyStroke(KeyEvent.VK_F1+index);
			quickButton.setAutoMnemonicEnabled(false);
			quickButton.setPreferredSize(new Dimension(90,56));
			quickButton.setImage(QUICK_KEY_IMAGE);
			quickButton.setTouchedImage(QUICK_KEY_IMAGE_TOUCH);
			quickButton.setOnClickListner(mQuickCashListner);
			if(index>=items.length){
				quickButton.setEnabled(false);
			}else{
				quickButton.setText(currencySymbol+items[index]);
				quickButton.setTag(items[index]);
			}
			mQuickCashPanel.add(quickButton);
		}
		mCashPanel.add(mQuickCashPanel);
	}
	
	private void createExactAmountButton(){
		PosButton button=new PosButton("Exact");
		button.setMnemonic('x');
//		button.registerKeyStroke(KeyEvent.VK_E, KeyEvent.CTRL_DOWN_MASK);
		button.setPreferredSize(new Dimension(mQuickCashPanel.getWidth()-PANEL_CONTENT_H_GAP*2,60));
		button.setImage(EXACT_KEY_IMAGE);
		button.setTouchedImage(EXACT_KEY_IMAGE_TOUCH);
//		button.setTag(getBillTotal());
		button.setOnClickListner(mExactAmountButton);
		mQuickCashPanel.add(button);
	}
	
	private IPosButtonListner mExactAmountButton=new IPosButtonListner() {
		@Override
		public void onClicked(PosButton button) {
			setExactAmount();
		}
	};
	
	/*
	 * 
	 */
	public void setExactAmount(){
		mTxtTendered.setText(PosCurrencyUtil.format(getExactAmount()));
//		mTxtActualAmountGiven.setText(PosNumberUtil.formatNumber(getExactAmount()));
		mTxtTendered.selectAll();
		mParent.requestFocus();
//		mTxtTendered.requestFocus();
	}

	private double getExactAmount(){
//		return mParent.getBillTotal() -mParent.getTenderedAmount()+getTenderAmount();
		reset();
		final double roudedAmount = PosCurrencyUtil.nRound(mParent.getBalanceAmount());
		return (mParent.getBalanceAmount()<0)?(roudedAmount*-1):0;
	}

	private IPosButtonListner mQuickCashListner=new IPosButtonListner() {
		@Override
		public void onClicked(PosButton button) {
			mCurrentCtrl.setText(String.valueOf(button.getTag()));
			mCurrentCtrl.selectAll();
			mCurrentCtrl.requestFocus();
		}
	};


	@Override
	public double getTenderAmount() {
		double amount=(!mTxtTendered.getText().equals(""))?Double.parseDouble(mTxtTendered.getText()):0;
		return	amount;
	}
	
//	public double getPartialBalance() {
//		double amount=(!mTxtActualAmountGiven.getText().equals(""))?Double.parseDouble(mTxtActualAmountGiven.getText()):0;
//		return	amount-getTenderAmount();
//	}
	
	public void setTenderAmount(double amount){
		mTxtTendered.setText(PosCurrencyUtil.format(amount));
		mTxtTendered.selectAll();
		mTxtTendered.requestFocus();
	}

//	public void setPartialPayment(boolean status){
//		isPartialPayment =status;
//		showAmountToPayAttributes(isPartialPayment);
//	}
	
//	private void showAmountToPayAttributes(boolean status){
//		mLabelActualAmountGiven.setVisible(status);
//		mTxtActualAmountGiven.setVisible(status);
//	}
	@Override
	public void reset() {
		mTxtTendered.setText(PosCurrencyUtil.format(0));
		mTxtTendered.selectAll();
		mTxtTendered.requestFocus(true);
	}

	@Override
	public void onGotFocus() {
		mParent.getCardPaymentPanel().reset();
		
		final double roudedAmount;
		final double roundingAdjustment ;
		
		if (PosFormUtil.canRound(PaymentMode.Cash)) {
		
			roudedAmount = PosCurrencyUtil.nRound(mParent.getBalanceAmount()+mParent.getRoundingAdjustment());
			roundingAdjustment = roudedAmount - (mParent.getBalanceAmount()+mParent.getRoundingAdjustment());
		}else{
			roudedAmount = mParent.getBalanceAmount()-mParent.getRoundingAdjustment();
			roundingAdjustment =0;
		}
		mParent.setRoundingAdjustment((roundingAdjustment==0)?0:-roundingAdjustment);
//		if(isPartialPayment){
//			mTxtActualAmountGiven.requestFocus();
//			mTxtActualAmountGiven.selectAll();
//		}else{
			mTxtTendered.requestFocus();
			mTxtTendered.selectAll();
//		}
	}
	
	public BeanOrderPayment getPayment(
			BeanOrderHeader orderHeader) {
//		if(isPartialPayment){
//			mTxtActualAmountGiven.requestFocus();
//			mTxtActualAmountGiven.selectAll();
//		}
		PosCashPaymentPanel posPayment = this;
		BeanOrderPayment orderPaymentCash = new BeanOrderPayment();
		orderPaymentCash.setOrderId(orderHeader.getOrderId());
		orderPaymentCash.setPaymentMode(PaymentMode.Cash);
		orderPaymentCash.setPaidAmount(posPayment.getTenderAmount());
//		orderPaymentCash.setPartialBalance(posPayment.getPartialBalance());
		return orderPaymentCash;
	}
	/* (non-Javadoc)
	 * @see com.indocosmo.pos.forms.components.tab.PosTab#onValidating()
	 */
	@Override
	public boolean onValidating() {
		boolean status=true;
//		if(isPartialPayment&&getPartialBalance()<0){
//			PosFormUtil.showErrorMessageBox(mParent, "You cant pay more than tendered amount.");
//			reset();
//			status=false;
//		}
		return status;
	}
	
	
}
