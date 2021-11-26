package com.indocosmo.pos.forms.components.payment;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
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
public final class PosSplitPartPaymentAdjustPanel extends PosPaymentBasePanel{	

	private JLabel mLabelTendered;
	private JTextField mTxtTendered;

	private JLabel mLabelPartRecevied;
	private JTextField mTxtPartRecevied;
	private JLabel mLabelPartUsed;
	private JTextField mTxtPartUsed;
	private JLabel mLabelPartRemaining;
	private JTextField mTxtPartRemaining;

	private static final int TEXT_FIELD_WIDTH=200;
	private static final int TEXT_FIELD_HEIGHT=35;

	private static final int LABEL_WIDTH=110;
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
	private static final int QUK_PAY_OPTNS_NO = 6;

	private JPanel mCashPanel;
	private JPanel mQuickCashPanel;
//	private PosButton mButtonExact;
	private JLabel mLabelActualAmountGiven;
	//	private JTextField mTxtActualAmountGiven;
	private PosNumKeypad mPosNumKeypad;
	private JTextField mCurrentCtrl;

	private double mPartAmountReceieved=0.0;
	private double mPartAmountUsed=0.0;

	/**
	 * @param parent
	 * @param width
	 * @param height
	 */
	public PosSplitPartPaymentAdjustPanel(PosPaymentForm parent, int width, int height) {
		super(parent,"Split Adj.",PaymentMode.SplitAdjust);
//		setKeyStroke(KeyStroke.getKeyStroke(KeyEvent.VK_F10, 0));
		setMnemonicChar('A');
		setBounds(0,0,width,height);
		setLayout(null);
		setOpaque(true);
		setCashDetails();
		createKeypadControl();
		reset();

	}	

	/**
	 * Sets the part amounts 
	 */
	private void setAmounts() {

		BeanOrderHeader order= mParent.getActualOrderHeader();
		mPartAmountReceieved=order.getSplitPartRecieved();
		mTxtPartRecevied.setText(PosCurrencyUtil.format(mPartAmountReceieved));

		mPartAmountUsed=order.getSplitPartUsed();
		mTxtPartUsed.setText(PosCurrencyUtil.format(mPartAmountUsed));

		mTxtPartRemaining.setText(PosCurrencyUtil.format(mPartAmountReceieved- mPartAmountUsed));

	}

	/**
	 * 
	 */
	private void setCashDetails(){	

		int left=0;		
		int top=0;
		mCashPanel=new JPanel();
		mCashPanel.setBounds(left, top, 320,getHeight());
		mCashPanel.setBorder(new MatteBorder(0, 0, 0, 1,  Color.LIGHT_GRAY));
		mCashPanel.setLayout(null);
		add(mCashPanel);
		createDetails();
		createQuickEntryPanel(mQuickCash);
	}

	/**
	 * 
	 */
	private void createDetails(){	

		int top=PANEL_CONTENT_V_GAP/4+1;

		mLabelPartRecevied=new JLabel();
		mLabelPartRecevied.setText("Received :");
		mLabelPartRecevied.setBorder(new EmptyBorder(2, 2, 2, 2));
		mLabelPartRecevied.setOpaque(true);
		mLabelPartRecevied.setBackground(Color.LIGHT_GRAY);
		mLabelPartRecevied.setHorizontalAlignment(SwingConstants.LEFT);		
		mLabelPartRecevied.setBounds(PANEL_CONTENT_H_GAP/4+1, top, LABEL_WIDTH, LABEL_HEIGHT);		
		mLabelPartRecevied.setFont(PosFormUtil.getLabelFont());
		mCashPanel.add(mLabelPartRecevied);

		int left=mLabelPartRecevied.getX()+mLabelPartRecevied.getWidth()+PANEL_CONTENT_H_GAP/8;
		mTxtPartRecevied=new JTextField();	
		mTxtPartRecevied.setHorizontalAlignment(JTextField.RIGHT);
		mTxtPartRecevied.setBounds(left, top, TEXT_FIELD_WIDTH, TEXT_FIELD_HEIGHT);	
		mTxtPartRecevied.setFont(PosFormUtil.getTextFieldFont());
		mTxtPartRecevied.setEditable(false);
		mCashPanel.add(mTxtPartRecevied);

		top=mTxtPartRecevied.getY()+mTxtPartRecevied.getHeight()+ PANEL_CONTENT_V_GAP/4;
		mLabelPartUsed=new JLabel();
		mLabelPartUsed.setText("Used :");
		mLabelPartUsed.setBorder(new EmptyBorder(2, 2, 2, 2));
		mLabelPartUsed.setOpaque(true);
		mLabelPartUsed.setBackground(Color.LIGHT_GRAY);
		mLabelPartUsed.setHorizontalAlignment(SwingConstants.LEFT);		
		mLabelPartUsed.setBounds(PANEL_CONTENT_H_GAP/4+1, top, LABEL_WIDTH, LABEL_HEIGHT);		
		mLabelPartUsed.setFont(PosFormUtil.getLabelFont());
		mCashPanel.add(mLabelPartUsed);

		left=mLabelPartUsed.getX()+mLabelPartUsed.getWidth()+PANEL_CONTENT_H_GAP/8;
		mTxtPartUsed=new JTextField();	
		mTxtPartUsed.setHorizontalAlignment(JTextField.RIGHT);
		mTxtPartUsed.setBounds(left, top, TEXT_FIELD_WIDTH, TEXT_FIELD_HEIGHT);	
		mTxtPartUsed.setFont(PosFormUtil.getTextFieldFont());
		mTxtPartUsed.setEditable(false);
		mCashPanel.add(mTxtPartUsed);

		top=mTxtPartUsed.getY()+mTxtPartUsed.getHeight()+ PANEL_CONTENT_V_GAP/4;
		mLabelPartRemaining=new JLabel();
		mLabelPartRemaining.setText("Left :");
		mLabelPartRemaining.setBorder(new EmptyBorder(2, 2, 2, 2));
		mLabelPartRemaining.setOpaque(true);
		mLabelPartRemaining.setBackground(Color.LIGHT_GRAY);
		mLabelPartRemaining.setHorizontalAlignment(SwingConstants.LEFT);		
		mLabelPartRemaining.setBounds(PANEL_CONTENT_H_GAP/4+1, top, LABEL_WIDTH, LABEL_HEIGHT);		
		mLabelPartRemaining.setFont(PosFormUtil.getLabelFont());
		mCashPanel.add(mLabelPartRemaining);

		left=mLabelPartRemaining.getX()+mLabelPartRemaining.getWidth()+PANEL_CONTENT_H_GAP/8;
		mTxtPartRemaining=new JTextField();	
		mTxtPartRemaining.setHorizontalAlignment(JTextField.RIGHT);
		mTxtPartRemaining.setBounds(left, top, TEXT_FIELD_WIDTH, TEXT_FIELD_HEIGHT);	
		mTxtPartRemaining.setFont(PosFormUtil.getTextFieldFont());
		mTxtPartRemaining.setEditable(false);
		mCashPanel.add(mTxtPartRemaining);

		top=this.getHeight()-56*3-PANEL_CONTENT_V_GAP*5-LABEL_HEIGHT-1-(PANEL_CONTENT_V_GAP/4+1);
		mLabelTendered=new JLabel();
		mLabelTendered.setText("Tendered :");
		mLabelTendered.setBorder(new EmptyBorder(2, 2, 2, 2));
		mLabelTendered.setOpaque(true);
		mLabelTendered.setBackground(Color.LIGHT_GRAY);
		mLabelTendered.setHorizontalAlignment(SwingConstants.LEFT);		
		mLabelTendered.setBounds(PANEL_CONTENT_H_GAP/4+1, top, LABEL_WIDTH+1, LABEL_HEIGHT);		
		mLabelTendered.setFont(PosFormUtil.getLabelFont());
		mCashPanel.add(mLabelTendered);

		left=mLabelTendered.getX()+mLabelTendered.getWidth()+PANEL_CONTENT_H_GAP/8;
		mTxtTendered=new JTextField();	
		mTxtTendered.setHorizontalAlignment(JTextField.RIGHT);
		mTxtTendered.setBounds(left, top, TEXT_FIELD_WIDTH, TEXT_FIELD_HEIGHT);	
		mTxtTendered.setFont(PosFormUtil.getTextFieldFont());
		mTxtTendered.addFocusListener(focusListener);
		mCashPanel.add(mTxtTendered);

		top=mLabelTendered.getX()+mLabelTendered.getHeight()+PANEL_CONTENT_V_GAP/4;
		mLabelActualAmountGiven=new JLabel();
		mLabelActualAmountGiven.setText("Tendered :");
		mLabelActualAmountGiven.setBorder(new EmptyBorder(2, 2, 2, 2));
		mLabelActualAmountGiven.setOpaque(true);
		mLabelActualAmountGiven.setBackground(Color.LIGHT_GRAY);
		mLabelActualAmountGiven.setHorizontalAlignment(SwingConstants.LEFT);		
		mLabelActualAmountGiven.setBounds(PANEL_CONTENT_H_GAP/4+1, top, LABEL_WIDTH, LABEL_HEIGHT);		
		mLabelActualAmountGiven.setFont(PosFormUtil.getLabelFont());
		mLabelActualAmountGiven.setFocusable(true);
		mLabelActualAmountGiven.setVisible(false);
		mCashPanel.add(mLabelActualAmountGiven);


	}

	/**
	 * 
	 */
	private void createKeypadControl(){

		mPosNumKeypad=new PosNumKeypad(mTxtTendered);
		int left=getWidth()-PosNumKeypad.LAYOUT_WIDTH ;
		mPosNumKeypad.setBorder(null);
		mPosNumKeypad.setLocation(left, PANEL_CONTENT_V_GAP);
		mPosNumKeypad.setCancelActions(CancelActions.Clear);
		add(mPosNumKeypad);
	}


	/**
	 * 
	 */
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
		}
	};

	/**
	 * @param items
	 */
	private void createQuickEntryPanel(String[] items){

		final int height=PosSoftKey.KEY_HEIGHT*3+PANEL_CONTENT_V_GAP*4;//getHeight()-PANEL_CASH_HEIGHT;
		mQuickCashPanel=new JPanel();
		mQuickCashPanel.setBounds(0, getHeight()-PANEL_CONTENT_V_GAP-height,
				PANEL_CASH_WIDTH-1,height);
		mQuickCashPanel.setLayout(new FlowLayout(FlowLayout.CENTER,PANEL_CONTENT_H_GAP,PANEL_CONTENT_V_GAP));
		mQuickCashPanel.setBorder(new MatteBorder(1, 0, 0, 0,  Color.LIGHT_GRAY));
		//		mQuickCashPanel.setBackground(Color.CYAN);
		final String currencySymbol=PosEnvSettings.getInstance().getCurrencySymbol();
		createExactAmountButton();
		for(int index=0; index<QUK_PAY_OPTNS_NO;index++){
			PosButton quickPayButton=new PosButton();
			quickPayButton.setPreferredSize(new Dimension(90,56));
			quickPayButton.setAutoMnemonicEnabled(false);
			quickPayButton.registerKeyStroke(KeyEvent.VK_F1+index);
			quickPayButton.setImage(QUICK_KEY_IMAGE);
			quickPayButton.setTouchedImage(QUICK_KEY_IMAGE_TOUCH);
			if(index>=items.length){
				quickPayButton.setEnabled(false);
			}
			else{
			quickPayButton.setTag(items[index]);
			quickPayButton.setText(currencySymbol+items[index]);
			quickPayButton.setOnClickListner(mQuickCashListner);
			}
			mQuickCashPanel.add(quickPayButton);
		}
		mCashPanel.add(mQuickCashPanel);
	}

	/**
	 * 
	 */
	private void createExactAmountButton(){

		PosButton button=new PosButton("Exact");
		button.setMnemonic('x');
		button.setPreferredSize(new Dimension(mQuickCashPanel.getWidth()-PANEL_CONTENT_H_GAP*2,60));
		button.setImage(EXACT_KEY_IMAGE);
		button.setTouchedImage(EXACT_KEY_IMAGE_TOUCH);
		button.setOnClickListner(mExactAmountButton);
		mQuickCashPanel.add(button);
	}

	private IPosButtonListner mExactAmountButton=new IPosButtonListner() {
		/* (non-Javadoc)
		 * @see com.indocosmo.pos.forms.components.buttons.listners.IPosButtonListner#onClicked(com.indocosmo.pos.forms.components.buttons.PosButton)
		 */
		@Override
		public void onClicked(PosButton button) {
			mParent.setRoundingAdjustment(0);
			mTxtTendered.setText(PosCurrencyUtil.format(getExactAmount()));
			mTxtTendered.selectAll();
			mTxtTendered.requestFocus();
		}
	};

	/**
	 * @return
	 */
	private double getExactAmount(){

		return mPartAmountReceieved-mPartAmountUsed;
	}

	/**
	 * 
	 */
	private IPosButtonListner mQuickCashListner=new IPosButtonListner() {
		@Override
		public void onClicked(PosButton button) {

			mCurrentCtrl.setText(String.valueOf(button.getTag()));
			mCurrentCtrl.selectAll();
			mCurrentCtrl.requestFocus();
		}
	};


	/* (non-Javadoc)
	 * @see com.indocosmo.pos.forms.components.payment.PosPaymentBasePanel#getTenderAmount()
	 */
	@Override
	public double getTenderAmount() {

		double amount=(!mTxtTendered.getText().equals(""))?Double.parseDouble(mTxtTendered.getText()):0;
		return	amount;
	}

	/**
	 * @param amount
	 */
	public void setTenderAmount(double amount){

		mTxtTendered.setText(PosCurrencyUtil.format(amount));
		mTxtTendered.selectAll();
		mTxtTendered.requestFocus();
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.pos.forms.components.payment.PosPaymentBasePanel#reset()
	 */
	@Override
	public void reset() {

		mTxtTendered.setText("0.00");
		mTxtTendered.selectAll();
		mTxtTendered.requestFocus(true);
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.pos.forms.components.tab.PosTab#onGotFocus()
	 */
	@Override
	public void onGotFocus() {

		setAmounts();
		mParent.setRoundingAdjustment(0);
		mTxtTendered.requestFocus();
		mTxtTendered.selectAll();
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.pos.forms.components.tab.PosTab#onValidating()
	 */
	@Override
	public boolean onValidating() {

		boolean status=true;
//		if(PosCurrencyUtil.roundTo(getExactAmount())<getTenderAmount()){
		if(getExactAmount()<getTenderAmount()){
			PosFormUtil.showErrorMessageBox(mParent, "You cant pay more than left amount.");
			reset();
			status=false;
		}

		return status;
	}

	/**
	 * @param orderHeader
	 * @return
	 */
	public BeanOrderPayment getPayment(
			BeanOrderHeader orderHeader) {
		BeanOrderPayment orderPayment = new BeanOrderPayment();
		orderPayment.setOrderId(orderHeader.getOrderId());
		orderPayment.setPaymentMode(PaymentMode.SplitAdjust);
		orderPayment.setPaidAmount(this.getTenderAmount());
		return orderPayment;
	}
}
