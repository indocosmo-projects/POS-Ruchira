package com.indocosmo.pos.forms.components.payment;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.indocosmo.pos.common.PosEnvSettings;
import com.indocosmo.pos.common.enums.BankAccountType;
import com.indocosmo.pos.common.utilities.PosCurrencyUtil;
import com.indocosmo.pos.common.utilities.PosFormUtil;
import com.indocosmo.pos.common.utilities.PosNumberUtil;
import com.indocosmo.pos.common.utilities.PosResUtil;
import com.indocosmo.pos.data.beans.BeanBankCard;
import com.indocosmo.pos.data.beans.BeanOrderHeader;
import com.indocosmo.pos.data.beans.BeanOrderPayment;
import com.indocosmo.pos.data.beans.BeanPayModeBase.PaymentMode;
import com.indocosmo.pos.data.providers.shopdb.PosBankCardTypesProvider;
import com.indocosmo.pos.data.providers.shopdb.PosCardPaymentRecoveryProvider;
import com.indocosmo.pos.forms.PosOrderEntryForm;
import com.indocosmo.pos.forms.PosPaymentForm;
import com.indocosmo.pos.forms.components.buttons.PosButton;
import com.indocosmo.pos.forms.components.buttons.listners.IPosButtonListner;
import com.indocosmo.pos.forms.components.textfields.PosItemBrowsableField;
import com.indocosmo.pos.forms.components.textfields.PosTouchableDigitalField;
import com.indocosmo.pos.forms.components.textfields.PosTouchableNumberField;
import com.indocosmo.pos.forms.components.textfields.PosTouchableTextField;
import com.indocosmo.pos.forms.components.textfields.listners.IPosTouchableFieldListner;
import com.indocosmo.pos.forms.components.textfields.listners.PosTouchableFieldAdapter;
import com.indocosmo.pos.forms.listners.IPosMessageBoxFormCancelListener;
import com.indocosmo.pos.forms.messageboxes.PosMessageBoxForm;
import com.indocosmo.pos.terminal.devices.PosDeviceManager;
import com.indocosmo.pos.terminal.devices.eftpos.EFTCardUtils;
import com.indocosmo.pos.terminal.devices.eftpos.PosDeviceEFT;
import com.indocosmo.pos.terminal.devices.eftpos.dataobjects.EFTCreditCardObject;
import com.indocosmo.pos.terminal.devices.eftpos.enums.EFTMessageType;
import com.indocosmo.pos.terminal.devices.eftpos.enums.EFTPurchaseStatus;
import com.indocosmo.pos.terminal.devices.eftpos.messageobjects.EFTMessageMAN;
import com.indocosmo.pos.terminal.devices.eftpos.messageobjects.EFTResponseMessageMAN;
import com.indocosmo.pos.terminal.devices.eftpos.messageobjects.EFTResponseMessagePUR;


@SuppressWarnings("serial")
public final class PosCardPaymentPanel extends PosPaymentBasePanel{	

	private JLabel mlabelMessage;
	
//	private JLabel mlabelCardName;
//	private PosTouchableNumericField mTxtCardName;
	
	private JLabel mLabelTotalAmount;
	private PosTouchableTextField mTxtTotalAmount;

	private JLabel mlabelCardNumber;
	private PosTouchableDigitalField mTxtCardNumber;

	private JLabel mlabelCardType;
//	private PosTouchableTextField mTxtCardType;
	private PosItemBrowsableField mTxtCardType;

	private JLabel mLabelCardHldrName;
	private PosTouchableTextField mTxtCardHldrName;

//	private JLabel mLabelExpMonth;
//	private JLabel mLabelExpYear;
	
	private JLabel mLabelExpDate;
	private PosTouchableDigitalField mTxtExpMonth;
	private PosTouchableDigitalField mTxtExpYear;

	private JLabel mLabelPurchaseAmount;
	private PosTouchableNumberField mTxtPurchaseAmount;

	private JLabel mLabelCashOutAmount;  
	private PosTouchableNumberField mTxtCashOutAmount;

	private JLabel mLabelAuthcode;  
	private PosTouchableTextField mTxtAuthcode;

	private static final int TEXT_FIELD_WIDTH=394;
	private static final int TEXT_FIELD_DATE_WIDTH=197;
	
	private static final int LABEL_WIDTH=198;
	private static final int LABEL_HEIGHT=40;	

	private static final int PANEL_CONTENT_H_GAP=PosOrderEntryForm.PANEL_CONTENT_H_GAP;
	private static final int PANEL_CONTENT_V_GAP=2;

	private JPanel mMessagePanel;
	public static final int MESSAGE_PANEL_HEIGHT=30;
	private static final Color PANEL_BG_COLOR=new Color(6,38,76);
	private static final Color LABEL_BG_COLOR=new Color(6,38,76);


	private int mCardTrackReadCount=0;
	private String mCardAccountType ="";
	private boolean purchaseCanceled = false;
	private PosCardPaymentRecoveryProvider mCardPaymentRecoveryProvider;

	private JLabel mLabelCardAccountType;

	private PosItemBrowsableField mTxtAccountType;
	private JPanel mDetailPanel;
	public PosCardPaymentPanel(PosPaymentForm parent,int width, int height) {
		
		super(parent,PaymentMode.Card.getDisplayText(),PaymentMode.Card);	
//		setKeyStroke(KeyStroke.getKeyStroke(KeyEvent.VK_F10, 0));
		setMnemonicChar('d');
		mParent=parent;
		setBounds(0,0,width,height);
		setLayout(null);
		setOpaque(true);
		setCardDetails();
		reset();
	}	

	/**
	 * 
	 */
	public PosCardPaymentPanel() {
		super(null,"Card",PaymentMode.Card);	
	}
	private void setCardDetails(){	
		createMessagePanel();	
		
		setTransactionSummary();
	}

	private void createMessagePanel(){
		int top=0;
		int left=0;
		mMessagePanel=new JPanel();
		int width=getWidth();
		int height=MESSAGE_PANEL_HEIGHT;
		mMessagePanel.setSize(width, height);
		mMessagePanel.setBounds(left, top, width, height);
		mMessagePanel.setBackground(PANEL_BG_COLOR);
		mMessagePanel.setLayout(null);		
		add(mMessagePanel);

		left=PANEL_CONTENT_H_GAP*2;
		top=0;
		mlabelMessage=new JLabel();
		mlabelMessage.setText("Please fill the card information.");
		mlabelMessage.setHorizontalAlignment(SwingConstants.LEFT);		
		mlabelMessage.setBounds(left, top, mMessagePanel.getWidth(), mMessagePanel.getHeight());				
		mlabelMessage.setOpaque(true);
		mlabelMessage.setBackground(LABEL_BG_COLOR);
		mlabelMessage.setForeground(Color.WHITE);
		mlabelMessage.setFont(PosFormUtil.getTextFieldBoldFont().deriveFont(15.0f));
		mMessagePanel.add(mlabelMessage);

	}

	private void setTransactionSummary(){
		
		mDetailPanel=new JPanel();
		int width=LABEL_WIDTH + TEXT_FIELD_WIDTH  + PANEL_CONTENT_H_GAP +PANEL_CONTENT_H_GAP*2 ;
		
		int height=(LABEL_HEIGHT + PANEL_CONTENT_H_GAP) * 8;
		int left=(getWidth()- width )/2 ;		
		int top=mMessagePanel.getY()+mMessagePanel.getHeight()+PANEL_CONTENT_V_GAP*4;
	
		 
		mDetailPanel.setSize(width, height);
		mDetailPanel.setBounds(left, top, width, height);
		mDetailPanel.setLayout(null);		
		add(mDetailPanel);
		
		setNumber();
		setCardType();
//		setCardName();
		
//		setExpYear();
//		setExpMonth();
		setExpDate();
//		setCardHldrName();
		setAccountType();
		setPurchaseAmount();
		setCashOutAmount();
		setTotalAmount();
		setAuthCode();
	}

	
	
	

	private void setCardType(){
//		int left=PANEL_CONTENT_H_GAP*8;		
//		int top=mMessagePanel.getY()+mMessagePanel.getHeight()+PANEL_CONTENT_V_GAP;
		int left= mlabelCardNumber.getX();		
		int top=mTxtCardNumber.getY()+mTxtCardNumber.getHeight()+PANEL_CONTENT_V_GAP;
		
		mlabelCardType=new JLabel();
		mlabelCardType.setText(PosFormUtil.getMnemonicString("Card Type :",'T'));
		mlabelCardType.setBorder(new EmptyBorder(2, 2, 2, 2));
		mlabelCardType.setOpaque(true);
		mlabelCardType.setBackground(Color.LIGHT_GRAY);
		mlabelCardType.setHorizontalAlignment(SwingConstants.LEFT);		
		mlabelCardType.setBounds(left, top, LABEL_WIDTH, LABEL_HEIGHT);
//		mlabelCardType.setPreferredSize(new Dimension( LABEL_WIDTH, LABEL_HEIGHT));	
		mlabelCardType.setFont(PosFormUtil.getLabelFont());
		mlabelCardType.setFocusable(true);
		mDetailPanel.add(mlabelCardType);

		left=mlabelCardType.getX()+mlabelCardType.getWidth()+PANEL_CONTENT_H_GAP/4;
		PosBankCardTypesProvider bankProvider = new PosBankCardTypesProvider();
		Map<Integer, BeanBankCard> bankCards = bankProvider.getBankCards();
		
		mTxtCardType=new PosItemBrowsableField(mParent,TEXT_FIELD_WIDTH);
		mTxtCardType.setBrowseItemList(new ArrayList<BeanBankCard>(bankCards.values()));
		mTxtCardType.setMnemonic('T');
		mTxtCardType.setBrowseWindowSize(3, 3);
		mTxtCardType.setTitle("Card type ?");
//		mTxtCardType.setEditable(false);
		mTxtCardType.setLocation(left, top);
		mTxtCardType.setListner(new IPosTouchableFieldListner() {
			
			@Override
			public void onValueSelected(Object value) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onReset() {
				// TODO Auto-generated method stub
				
			}
		});
		mDetailPanel.add(mTxtCardType);
		
			
	}

	/**
	 * 
	 */
//	private void setCardName() {
//		int left=PANEL_CONTENT_H_GAP*2;		
//		int top=mMessagePanel.getY()+mMessagePanel.getHeight()+PANEL_CONTENT_V_GAP;
//		
//		mlabelCardName = new JLabel();
//		mlabelCardName.setText("Card Name :");
//		mlabelCardName.setHorizontalAlignment(SwingConstants.LEFT);
//		mlabelCardName.setBounds(left, top, LABEL_WIDTH, LABEL_HEIGHT);
//		mlabelCardName.setFont(PosFormUtil.getLabelFont());
//		mlabelCardName.setFocusable(true);
//		add(mlabelCardName);
//		
//		left = mlabelCardName.getX()+mlabelCardName.getWidth()+PANEL_CONTENT_H_GAP*2;
//		mTxtCardName = new PosTouchableNumericField(mParent,TEXT_FIELD_WIDTH);
//		mTxtCardName.setTitle("Bank Name ?");
//		mTxtCardName.setLocation(left, top);
//		mTxtCardName.setHorizontalTextAlignment(JTextField.LEFT);
//		mTxtCardName.setTextFont(PosFormUtil.getTextFieldFont());	
//		add(mTxtCardName);
//		
//	}

	/**
	 * 
	 */
	

	private void setNumber(){

		int left=PANEL_CONTENT_H_GAP ;		
		int top=PANEL_CONTENT_V_GAP;// mMessagePanel.getY()+mMessagePanel.getHeight()+PANEL_CONTENT_V_GAP*4;
		
		mlabelCardNumber=new JLabel();
		mlabelCardNumber.setText(PosFormUtil.getMnemonicString("Card Number :",'N'));
		mlabelCardNumber.setBorder(new EmptyBorder(2, 2, 2, 2));
		mlabelCardNumber.setOpaque(true);
		mlabelCardNumber.setBackground(Color.LIGHT_GRAY);
		mlabelCardNumber.setHorizontalAlignment(SwingConstants.LEFT);		
//		mlabelCardNumber.setPreferredSize(new Dimension( LABEL_WIDTH, LABEL_HEIGHT));		
		mlabelCardNumber.setBounds(left, top, LABEL_WIDTH, LABEL_HEIGHT);
		mlabelCardNumber.setFont(PosFormUtil.getLabelFont());
		mlabelCardNumber.setFocusable(true);
		mDetailPanel.add(mlabelCardNumber);

		left=mlabelCardNumber.getX()+mlabelCardNumber.getWidth()+PANEL_CONTENT_H_GAP/4;
		mTxtCardNumber=new PosTouchableDigitalField(mParent,TEXT_FIELD_WIDTH);
		mTxtCardNumber.setMnemonic('N');
		mTxtCardNumber.setTitle("Card number ?");
		mTxtCardNumber.setLocation(left, top);
		mTxtCardNumber.setHorizontalTextAlignment(JTextField.LEFT);
		
		mTxtCardNumber.setFont(PosFormUtil.getTextFieldFont());		
		mTxtCardNumber.setListner(new PosTouchableFieldAdapter(){
			@Override
			public void onValueSelected(Object value) {
				EFTCreditCardObject ccObject=null;
				if(mTxtCardNumber.getText().trim()!=""){
					mTxtCashOutAmount.setText("");
					mTxtCashOutAmount.setEditable(false);
				}
				mCardTrackReadCount++;
				if(mCardTrackReadCount<=1)
					ccObject=EFTCardUtils.getCreditCardDetails(value.toString());
				if(!ccObject.getCreditCardNumber().equalsIgnoreCase(mTxtCardNumber.getText())){
					mTxtCardHldrName.setText(ccObject.getHolderName());
					mTxtCardNumber.setText(ccObject.getCreditCardNumber());
					mTxtExpMonth.setText(ccObject.getExpMM());
					mTxtExpYear.setText(ccObject.getExpYY());
				}
				mTxtPurchaseAmount.setText(PosCurrencyUtil.format(getExactAmount()));
				mTxtCardType.setTextReadOnly(true);
				mTxtCardNumber.setSelectedAll();
//				mTxtCardNumber.setEditable(false);
			}
		});
		mDetailPanel.add(mTxtCardNumber);
	}

	/**
	 * 
	 */
	private void setExpDate() {
		int left=mlabelCardType.getX();		
		int top=mTxtCardType.getY()+mTxtCardType.getHeight()+PANEL_CONTENT_V_GAP;
		mLabelExpDate=new JLabel();
		mLabelExpDate.setText(PosFormUtil.getMnemonicString( "Expiry Date(MM/YY) :",'i') );
		mLabelExpDate.setBorder(new EmptyBorder(2, 2, 2, 2));
		mLabelExpDate.setOpaque(true);
		mLabelExpDate.setBackground(Color.LIGHT_GRAY);
		mLabelExpDate.setHorizontalAlignment(SwingConstants.LEFT);		
		mLabelExpDate.setBounds(left, top, LABEL_WIDTH, LABEL_HEIGHT);
//		mLabelExpDate.setPreferredSize(new Dimension( LABEL_WIDTH, LABEL_HEIGHT));	
		mLabelExpDate.setFont(PosFormUtil.getLabelFont());
		mLabelExpDate.setFocusable(true);
		mDetailPanel.add(mLabelExpDate);
		
		left=mLabelExpDate.getX()+mLabelExpDate.getWidth()+PANEL_CONTENT_H_GAP/4;
		mTxtExpMonth=new PosTouchableDigitalField(mParent,TEXT_FIELD_DATE_WIDTH);
		mTxtExpMonth.setMnemonic('i');
		mTxtExpMonth.setTitle("Card expiry month ?");
		mTxtExpMonth.setHorizontalTextAlignment(JTextField.LEFT);
		mTxtExpMonth.setLocation(left, top);
		mTxtExpMonth.setTextFont(PosFormUtil.getTextFieldFont());	
		mTxtExpMonth.hideResetButton(true);
		mTxtExpMonth.getTexFieldComponent().setDocument(PosNumberUtil.createDecimalDocument());
		mDetailPanel.add(mTxtExpMonth);
		
		left=mTxtExpMonth.getX()+mTxtExpMonth.getWidth();
		mTxtExpYear=new PosTouchableDigitalField(mParent,TEXT_FIELD_DATE_WIDTH);
		mTxtExpYear.setTitle("Card expiry year ?");
		mTxtExpYear.setHorizontalTextAlignment(JTextField.LEFT);
		mTxtExpYear.setLocation(left, top);
		mTxtExpYear.setTextFont(PosFormUtil.getTextFieldFont());
		mTxtExpYear.getTexFieldComponent().setDocument(PosNumberUtil.createDecimalDocument());
		mTxtExpYear.setListner(new IPosTouchableFieldListner() {
			
			@Override
			public void onValueSelected(Object value) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onReset() {
				mTxtExpMonth.reset();
			}
		});
		mDetailPanel.add(mTxtExpYear);
		
	}

	
	
	private void setCashOutAmount(){
		int left=mLabelPurchaseAmount.getX();		
		int top=mTxtPurchaseAmount.getY()+mTxtPurchaseAmount.getHeight()+PANEL_CONTENT_V_GAP;
		
		mLabelCashOutAmount=new JLabel();
		mLabelCashOutAmount.setText(PosFormUtil.getMnemonicString("Cash Out :",'q'));
		mLabelCashOutAmount.setBorder(new EmptyBorder(2, 2, 2, 2));
		mLabelCashOutAmount.setOpaque(true);
		mLabelCashOutAmount.setBackground(Color.LIGHT_GRAY);
		mLabelCashOutAmount.setHorizontalAlignment(SwingConstants.LEFT);		
		mLabelCashOutAmount.setBounds(left, top, LABEL_WIDTH, LABEL_HEIGHT);
//		mLabelCashOutAmount.setPreferredSize(new Dimension( LABEL_WIDTH, LABEL_HEIGHT));	
		mLabelCashOutAmount.setFont(PosFormUtil.getLabelFont());
		mLabelCashOutAmount.setFocusable(true);
		mDetailPanel.add(mLabelCashOutAmount);

		left=mLabelCashOutAmount.getX()+mLabelCashOutAmount.getWidth()+PANEL_CONTENT_H_GAP/4;
		//		mTxtAuthcode=new JTextField("12xz0123ybd2");	

		//		mTxtAuthcode.setBounds(left, top, TEXT_FIELD_WIDTH, TEXT_FIELD_HEIGHT);	
		mTxtCashOutAmount=new PosTouchableDigitalField(mParent,TEXT_FIELD_WIDTH);
		mTxtCashOutAmount.setMnemonic('q');
		mTxtCashOutAmount.setTitle("Cash Out ?");
		mTxtCashOutAmount.setHorizontalTextAlignment(JTextField.RIGHT);
		mTxtCashOutAmount.setLocation(left, top);
		mTxtCashOutAmount.setTextFont(PosFormUtil.getTextFieldFont());	
		mTxtCashOutAmount.getTexFieldComponent().setDocument(PosNumberUtil.createDecimalDocument());
		mTxtCashOutAmount.getTexFieldComponent().getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void removeUpdate(DocumentEvent e) {
				updateTotalAmount();
				mTxtCashOutAmount.requestFocus();
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				updateTotalAmount();
				mTxtCashOutAmount.requestFocus();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				updateTotalAmount();
				mTxtCashOutAmount.requestFocus();
			}
		});
		mDetailPanel.add(mTxtCashOutAmount);
	}
	
	private void updateTotalAmount(){
		final double cashOutAmount=PosNumberUtil.getValueFormUIComponenet(mTxtCashOutAmount.getTexFieldComponent(),false);
		final double purchaseAmount=PosNumberUtil.getValueFormUIComponenet(mTxtPurchaseAmount.getTexFieldComponent(),false);
		final double totalAmount=cashOutAmount+purchaseAmount;
		mTxtTotalAmount.setText(PosCurrencyUtil.format(totalAmount));
		
	}
	
	private void setTotalAmount() {
//		int left= mlabelCardName.getX();		
//		int top=mTxtCardName.getY()+mTxtCardName.getHeight()+PANEL_CONTENT_V_GAP*2;
		
		int left=mLabelCashOutAmount.getX();		
		int top=mTxtCashOutAmount.getY()+mTxtCashOutAmount.getHeight()+PANEL_CONTENT_V_GAP;
		
		mLabelTotalAmount = new JLabel();
		mLabelTotalAmount.setText( "Total Amount :");
		mLabelTotalAmount.setBorder(new EmptyBorder(2, 2, 2, 2));
		mLabelTotalAmount.setOpaque(true);
		mLabelTotalAmount.setBackground(Color.LIGHT_GRAY);
		mLabelTotalAmount.setHorizontalAlignment(SwingConstants.LEFT);
		mLabelTotalAmount.setBounds(left, top, LABEL_WIDTH, LABEL_HEIGHT);
//		mLabelTotalAmount.setPreferredSize(new Dimension( LABEL_WIDTH, LABEL_HEIGHT));	
		mLabelTotalAmount.setFont(PosFormUtil.getLabelFont());
		mLabelTotalAmount.setFocusable(true);
		mDetailPanel.add(mLabelTotalAmount);
		
		left = mLabelTotalAmount.getX()+mLabelTotalAmount.getWidth()+PANEL_CONTENT_H_GAP/4;
		mTxtTotalAmount = new PosTouchableTextField(mParent,TEXT_FIELD_WIDTH);
		mTxtTotalAmount.setTitle("Total Amount ?");
		mTxtTotalAmount.setLocation(left, top);
		mTxtTotalAmount.setHorizontalTextAlignment(JTextField.RIGHT);
		mTxtTotalAmount.setTextFont(PosFormUtil.getTextFieldFont());	
		mTxtTotalAmount.setEditable(false);
		mTxtTotalAmount.getTexFieldComponent().getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void removeUpdate(DocumentEvent e) {
				onTenderAmountChanged(mTxtTotalAmount.getText());
				
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				onTenderAmountChanged(mTxtTotalAmount.getText());
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				onTenderAmountChanged(mTxtTotalAmount.getText());
			}
		});
		mDetailPanel.add(mTxtTotalAmount);
	}
	
	private void setCardHldrName(){
		int left=mLabelExpDate.getX();		
		int top=mTxtExpMonth.getY()+mTxtExpMonth.getHeight()+PANEL_CONTENT_V_GAP;
		mLabelCardHldrName=new JLabel();
		mLabelCardHldrName.setText("Holder Name :");
		mLabelCardHldrName.setBorder(new EmptyBorder(2, 2, 2, 2));
		mLabelCardHldrName.setHorizontalAlignment(SwingConstants.LEFT);		
		mLabelCardHldrName.setBounds(left, top, LABEL_WIDTH, LABEL_HEIGHT);
//		mLabelCardHldrName.setPreferredSize(new Dimension( LABEL_WIDTH, LABEL_HEIGHT));	
		mLabelCardHldrName.setFont(PosFormUtil.getLabelFont());
		mLabelCardHldrName.setFocusable(true);
		mDetailPanel.add(mLabelCardHldrName);

		left=mLabelCardHldrName.getX()+mLabelCardHldrName.getWidth()+PANEL_CONTENT_H_GAP*2;
		mTxtCardHldrName=new PosTouchableTextField(mParent,TEXT_FIELD_WIDTH);
		mTxtCardHldrName.setTitle("Holder's name ?");
		mTxtCardHldrName.setHorizontalTextAlignment(JTextField.LEFT);
		mTxtCardHldrName.setLocation(left, top);
		mTxtCardHldrName.setTextFont(PosFormUtil.getTextFieldFont());	
		mDetailPanel.add(mTxtCardHldrName);
	}

	/**
	 * 
	 */
	private void setAccountType() {

		int left=mLabelExpDate.getX();		
		int top=mTxtExpMonth.getY()+mTxtExpMonth.getHeight()+PANEL_CONTENT_V_GAP;
		mLabelCardAccountType=new JLabel();
		mLabelCardAccountType.setText(PosFormUtil.getMnemonicString("Account Type :",'A'));
		mLabelCardAccountType.setBorder(new EmptyBorder(2, 2, 2, 2));
		mLabelCardAccountType.setOpaque(true);
		mLabelCardAccountType.setBackground(Color.LIGHT_GRAY);
		mLabelCardAccountType.setHorizontalAlignment(SwingConstants.LEFT);		
		mLabelCardAccountType.setBounds(left, top, LABEL_WIDTH, LABEL_HEIGHT);
//		mLabelCardAccountType.setPreferredSize(new Dimension( LABEL_WIDTH, LABEL_HEIGHT));	
		mLabelCardAccountType.setFont(PosFormUtil.getLabelFont());
		mLabelCardAccountType.setFocusable(true);
		mDetailPanel.add(mLabelCardAccountType);

		left=mLabelCardAccountType.getX()+mLabelCardAccountType.getWidth()+PANEL_CONTENT_H_GAP/4;
		mTxtAccountType = new PosItemBrowsableField(mParent,TEXT_FIELD_WIDTH);
		mTxtAccountType.setMnemonic('A');
		mTxtAccountType.setBrowseItemList(BankAccountType.values());
		mTxtAccountType.setTitle("Account Type ?");
		mTxtAccountType.setLocation(left, top);
		mTxtAccountType.setHorizontalTextAlignment(JTextField.RIGHT);
		mTxtAccountType.setTextFont(PosFormUtil.getTextFieldFont());
		mTxtAccountType.setListner(new PosTouchableFieldAdapter() {

			/*
			 * (non-Javadoc)
			 * 
			 * @see com.indocosmo.pos.components.textfields.listners.
			 * PosTouchableFieldAdapter#onValueSelected(java.lang.Object)
			 */
			@Override
			public void onValueSelected(Object value) {
				// TODO Auto-generated method stub
				mCardAccountType = ((BankAccountType) value).getDisplayText();
			}
		});
		mDetailPanel.add(mTxtAccountType);
		
		
	}
	
	private void setPurchaseAmount(){
//		int left=mLabelCardHldrName.getX();		
//		int top=mTxtCardHldrName.getY()+mTxtCardHldrName.getHeight()+PANEL_CONTENT_V_GAP;
		int left=mLabelCardAccountType.getX();		
		int top=mTxtAccountType.getY()+mTxtAccountType.getHeight()+PANEL_CONTENT_V_GAP;
		mLabelPurchaseAmount=new JLabel();
		mLabelPurchaseAmount.setText(PosFormUtil.getMnemonicString( "Purchase Amount :",'o'));
		mLabelPurchaseAmount.setBorder(new EmptyBorder(2, 2, 2, 2));
		mLabelPurchaseAmount.setOpaque(true);
		mLabelPurchaseAmount.setBackground(Color.LIGHT_GRAY);
		mLabelPurchaseAmount.setHorizontalAlignment(SwingConstants.LEFT);		
		mLabelPurchaseAmount.setBounds(left, top, LABEL_WIDTH, LABEL_HEIGHT);
//		mLabelPurchaseAmount.setPreferredSize(new Dimension( LABEL_WIDTH, LABEL_HEIGHT));	
		mLabelPurchaseAmount.setFont(PosFormUtil.getLabelFont());
		mDetailPanel.add(mLabelPurchaseAmount);

		left=mLabelPurchaseAmount.getX()+mLabelPurchaseAmount.getWidth()+PANEL_CONTENT_H_GAP/4;
		mTxtPurchaseAmount=new PosTouchableNumberField(mParent,TEXT_FIELD_WIDTH-102);
		mTxtPurchaseAmount.setMnemonic('o');
		mTxtPurchaseAmount.setTitle("Purchase Amount ?");
		mTxtPurchaseAmount.setHorizontalTextAlignment(JTextField.RIGHT);
		mTxtPurchaseAmount.setLocation(left, top);
		mTxtPurchaseAmount.setTextFont(PosFormUtil.getTextFieldFont());
		mTxtPurchaseAmount.setDefaultValue("0.00");
		mTxtPurchaseAmount.setListner(mFieldChangeListner);
		mTxtPurchaseAmount.getTexFieldComponent().setDocument(PosNumberUtil.createDecimalDocument());
		mTxtPurchaseAmount.getTexFieldComponent().getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void removeUpdate(DocumentEvent e) {
				updateTotalAmount();
				mTxtPurchaseAmount.requestFocus();
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				updateTotalAmount();
				mTxtPurchaseAmount.requestFocus();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				updateTotalAmount();
				mTxtPurchaseAmount.requestFocus();
			}
		});
		mDetailPanel.add(mTxtPurchaseAmount);
		createExactButton();
	}
	
	private IPosTouchableFieldListner mFieldChangeListner=new PosTouchableFieldAdapter() {
		@Override
		public void onValueSelected(Object value) {
			double amount = Double.parseDouble(String.valueOf(value));
			if(mParent.getBalanceAmount()>0){
				PosFormUtil.showInformationMessageBox(mParent, "You can not enter amount more than exact amount");
				mTxtPurchaseAmount.reset();
			}
		}
	};	
	private static final String SELECT_BUTTON_NORMAL="payment_coupon_ctrl_select_button.png";
	private static ImageIcon mImageSelectNormal;
//	private static final String SELECT_BUTTON_TOUCH="payment_coupon_ctrl_select_button.png";
//	private static ImageIcon mImageSelectNormal;
	private void createExactButton(){
		
		PosButton button=new PosButton("Exact");
		button.setMnemonic('x');
		button.setBounds(mTxtPurchaseAmount.getX()+mTxtPurchaseAmount.getWidth(), mTxtPurchaseAmount.getY(), 102, mTxtPurchaseAmount.getHeight());
		if(mImageSelectNormal==null)
			mImageSelectNormal=PosResUtil.getImageIconFromResource(SELECT_BUTTON_NORMAL);
		button.setImage(mImageSelectNormal);
		button.setTag(getBillTotal());
		mDetailPanel.add(button);
		button.setOnClickListner(new IPosButtonListner() {
			
			@Override
			public void onClicked(PosButton button) {
				mTxtPurchaseAmount.setText(PosCurrencyUtil.format(getExactAmount()));
			}
		});
	}
	@Override
	protected void onTenderAmountChanged(String amount) {
//		setControlsEnabled(true);
		super.onTenderAmountChanged(amount);
	}

	private void setAuthCode(){
		int left=mLabelTotalAmount.getX();		
		int top=mTxtTotalAmount.getY()+mTxtTotalAmount.getHeight()+PANEL_CONTENT_V_GAP;
		
		mLabelAuthcode=new JLabel(); 
		mLabelAuthcode.setText(PosFormUtil.getMnemonicString("Auth. Code :",'e'));
		mLabelAuthcode.setBorder(new EmptyBorder(2, 2, 2, 2));
		mLabelAuthcode.setOpaque(true);
		mLabelAuthcode.setBackground(Color.LIGHT_GRAY);
		mLabelAuthcode.setHorizontalAlignment(SwingConstants.LEFT);		
		mLabelAuthcode.setBounds(left, top, LABEL_WIDTH, LABEL_HEIGHT);
//		mLabelAuthcode.setPreferredSize(new Dimension( LABEL_WIDTH, LABEL_HEIGHT));	
		mLabelAuthcode.setFont(PosFormUtil.getLabelFont());
		mLabelAuthcode.setFocusable(true);
		mDetailPanel.add(mLabelAuthcode);

		left=mLabelAuthcode.getX()+mLabelAuthcode.getWidth()+PANEL_CONTENT_H_GAP/4;
		//		mTxtAuthcode=new JTextField("12xz0123ybd2");	

		//		mTxtAuthcode.setBounds(left, top, TEXT_FIELD_WIDTH, TEXT_FIELD_HEIGHT);	
		mTxtAuthcode=new PosTouchableTextField(mParent,TEXT_FIELD_WIDTH);
		mTxtAuthcode.setMnemonic('e');
		mTxtAuthcode.setTitle("Auth. Code ?");
		mTxtAuthcode.setHorizontalTextAlignment(JTextField.LEFT);
		mTxtAuthcode.setLocation(left, top);
		mTxtAuthcode.setTextFont(PosFormUtil.getTextFieldFont());		
		//		mTxtAuthcode.setEditable(false);
		mDetailPanel.add(mTxtAuthcode);
	}

	
	//	 public void actionPerformed(ActionEvent evt) {
	//		 JOptionPane.showMessageDialog(null, "Pressed");	 
	//	}

	//	 private void setCardItems(){
	//		 mCardItems=new PosCardItem();
	//		 mCardItems.setCardNumber(getCardNumber());
	//		 mCardItems.setCardName(getCardName());
	//		 mCardItems.setCardType(getCardType());
	//		 mCardItems.setCardExpiryMonth(getCardExpiryMonth());
	//		 mCardItems.setCardExpirYear(getCardExpirYear());
	//		 mCardItems.setCardApprovalCode(getCardApprovalCode());
	//	 }

	/**
	 * @return the mCardNumber
	 */
	public final String getCardNumber() {
		return mTxtCardNumber.getText();
	}


	//		/**
	//		 * @param mCardNumber the mCardNumber to set
	//		 */
	//		public final void setCardNumber(String cardNumber) {
	//			this.mCardNumber = cardNumber;
	//		}


	/**
	 * @return the mCardName
	 */
//	public final String getCardName() {
//		return mTxtCardName.getText();
//	}


	/**
	 * @param mCardName the mCardName to set
	 */
	//		public final void setCardName(String cardName) {
	//			this.mCardName = cardName;
	//		}


	/**
	 * @return the mCardType
	 */
	public final String getCardType() {
		return mTxtCardType.getText();
	}


	/**
	 * @param mCardType the mCardType to set
	 */
	//		public final void setCardType(String cardType) {
	//			this.mCardType = cardType;
	//		}


	/**
	 * @return the mCardExpiryMonth
	 */
	public final Integer getCardExpiryMonth() {
		Integer expMonth=0;
		try{
			expMonth= Integer.parseInt(mTxtExpMonth.getText());
		}catch (Exception e) {
			// TODO: handle exception
		}
		return expMonth;
	}


	/**
	 * @param mCardExpiryMonth the mCardExpiryMonth to set
	 */
	//		public final void setCardExpiryMonth(Integer cardExpiryMonth) {
	//			this.mCardExpiryMonth = cardExpiryMonth;
	//		}


	/**
	 * @return the mCardExpirYear
	 */
	public final Integer getCardExpiryYear() {
//		return Integer.parseInt(mTxtExpYear.getText());
		Integer expYear=0;
		try{
			expYear= Integer.parseInt(mTxtExpYear.getText());
		}catch (Exception e) {
			// TODO: handle exception
		}
		return expYear;
	}


	/**
	 * @param mCardExpirYear the mCardExpirYear to set
	 */
	//		public final void setCardExpirYear(Integer cardExpirYear) {
	//			this.mCardExpirYear = cardExpirYear;
	//		}
	

	/**
	 * @return the mCardApprovalCode
	 */
	public final String getCardApprovalCode() {
		return mTxtAuthcode.getText();
	}

	/**
	 * @return the mCardApprovalCode
	 */
	public final String getNameOnCard() {
		return mTxtCardHldrName.getText();
	}


	/**
	 * @param mCardApprovalCode the mCardApprovalCode to set
	 */
	//		public final void setCardApprovalCode(String cardApprovalCode) {
	//			this.mCardApprovalCode = cardApprovalCode;
	//		}	

	//		public PosCard getCardItems(){
	//			return mCardItems;
	//		}

	@Override
	public boolean onValidating() {
		if(mParent.getBalanceAmount()>0){
			PosFormUtil.showInformationMessageBox(mParent, "You can not enter amount more than exact amount.");
			mTxtPurchaseAmount.reset();
			return false;
		}else if(!PosDeviceManager.getInstance().hasPosDevice()){
			
			if (PosEnvSettings.getInstance().getUISetting().getOrderEntryUISettings().getPaymentPanelSetting().isCreditCardValidationRequired()
					&& mParent.getActualPaymentMode()!=PaymentMode.QuickCard  ){

				if(mTxtCardNumber.getText().trim().equals("") || mTxtCardNumber.getText().trim().length()<4){
				
					PosFormUtil.showErrorMessageBox(mParent, "Please enter last 4 digits of card number.");
					return false;
				}
			}
//			else if(mTxtCardType.getText().trim().equals("")){
//				PosFormUtil.showErrorMessageBox(mParent, "Please select card type.");
//				return false;
//			}else 
//				if(mTxtAccountType.getText().trim().equals("")){
//				PosFormUtil.showErrorMessageBox(mParent, "Please select account type.");
//				return false;
//			}else if(mTxtAuthcode.getText().trim().equals("")){
//				PosFormUtil.showErrorMessageBox(mParent, "Please enter auth.code.");
//				return false;
//			}else{
				return true;
//			}
		}else if(!mTxtCardNumber.getText().trim().equals("")
//				||mTxtAuthcode.getText().trim().equals("")
//				||mTxtCardHldrName.getText().trim().equals("")
				&& (mTxtCardNumber.getText().trim().equals("")
//				||mTxtCardType.getText().trim().equals("")
				||mTxtExpMonth.getText().trim().equals("") 
				||!PosNumberUtil.isInteger(mTxtExpMonth.getText())
				||mTxtExpYear.getText().trim().equals("") 
				||!PosNumberUtil.isInteger(mTxtExpYear.getText()))
				){
			PosFormUtil.showErrorMessageBox(mParent, "Credit card details are not valid.");
			return false;
		}
		return true;
	}

	@Override
	public double getTenderAmount() {
//		return (mTxtTotalAmount.getText().trim().equals(""))?0:Double.parseDouble(mTxtTotalAmount.getText());
//		return PosNumberUtil.getValueFormUIComponenet(mTxtTotalAmount.getTexFieldComponent(),false);
		return PosNumberUtil.getValueFormUIComponenet(mTxtPurchaseAmount.getTexFieldComponent(),false);
	}
	
	public double getTotalAmount() {
		return PosNumberUtil.getValueFormUIComponenet(mTxtTotalAmount.getTexFieldComponent(),false);
	}
	
	public double getPruchaseAmount(){
		return PosNumberUtil.getValueFormUIComponenet(mTxtPurchaseAmount.getTexFieldComponent(),false);
	}
	
	public double getCashOutAmount(){
		return PosNumberUtil.getValueFormUIComponenet(mTxtCashOutAmount.getTexFieldComponent(),false);
	}
	
	private double getExactAmount(){
		mTxtPurchaseAmount.reset();
		return (mParent.getBalanceAmount()<0)?(mParent.getBalanceAmount()*-1):0;
	}

	@Override
	public void reset() {
//		mTxtCardName.setText("");
		mTxtTotalAmount.setText("");
		mTxtCardNumber.setText("");
		mTxtCardType.setText("");
//		mTxtCardHldrName.setText("");
		mTxtAccountType.setText("");
		mTxtExpYear.setText("");
		mTxtExpMonth.setText("");
		mTxtPurchaseAmount.setText("0.00");
		mTxtAuthcode.setText("");
		mTxtCashOutAmount.setText("");
		mTxtCardNumber.requestFocus();
		setControlsEnabled(true);
		mCardTrackReadCount=0;
		mCardAccountType = "";
//		mParent.mCantApplyDiscount=false;
	}
	
	public void setControlsEnabled(boolean enabled){
		mTxtCardNumber.setEditable(true);
//		mTxtCardName.setEditable(enabled);
//		mTxtTotalAmount.setEditable(enabled);
//		mTxtCardType.setEditable(enabled);
//		mTxtCardType.setTextReadOnly(enabled);
//		mTxtCardHldrName.setEditable(enabled);
		mTxtExpYear.setEditable(enabled);
		mTxtExpMonth.setEditable(enabled);
		mTxtPurchaseAmount.setEditable(enabled);
		mTxtAuthcode.setEditable(enabled);
		mTxtCashOutAmount.setEditable(enabled);
	}

	@Override
	public void onGotFocus() {
//		mTxtCardNumber.selectAll();
		
		double roundedAmount=0;
		double roundingAdjustment ;
		mTxtPurchaseAmount.reset();
		if(mParent.getBalanceAmount()<0){
//			mParent.setRoundingAdjustment(0);
			
			if (PosFormUtil.canRound(PaymentMode.Card)) {
			
				roundedAmount = PosCurrencyUtil.nRound(mParent.getBalanceAmount()+mParent.getRoundingAdjustment());
				roundingAdjustment = roundedAmount - (mParent.getBalanceAmount()+mParent.getRoundingAdjustment());
			}else{
				roundedAmount = mParent.getBalanceAmount()-(-1 * mParent.getRoundingAdjustment());
				
				
				roundingAdjustment =0;
			}
			mParent.setRoundingAdjustment((roundingAdjustment==0)?0:-roundingAdjustment);
		}
//		mTxtPurchaseAmount.setText(PosCurrencyUtil.format(getExactAmount()));
		mTxtPurchaseAmount.setText(PosCurrencyUtil.format(roundedAmount<0?(roundedAmount*-1):0));
		mTxtCardNumber.requestFocus();
	}
	
	public BeanOrderPayment getPayment(
			
			BeanOrderHeader orderHeader) throws Exception {
		if(getTenderAmount()==0) return null;
		if(PosDeviceManager.getInstance().hasPosDevice()){
			if(!doCardPayment()) return null;
		}
		PosCardPaymentPanel posCardPayment = this;
		BeanOrderPayment orderPayment = new BeanOrderPayment();
		orderPayment.setOrderId(orderHeader.getOrderId());
		orderPayment.setPaymentMode(PaymentMode.Card);
		orderPayment.setPaidAmount(posCardPayment.getTotalAmount());
		// orderPayment.setCardName(posCardPayment.getCardName());
		orderPayment.setCardType("Other");
		orderPayment.setCardNo(posCardPayment.getCardNumber());
		
		PosBankCardTypesProvider bankProvider = new PosBankCardTypesProvider();
		Map<Integer, BeanBankCard> bankCards = bankProvider.getBankCards();
		int carNumber = 000000;
		if(!mTxtCardNumber.getText().trim().equals("")){
			carNumber = Integer.valueOf((posCardPayment.getCardNumber().length()>5)?posCardPayment.getCardNumber().substring(0,6):posCardPayment.getCardNumber());
		}
		for(BeanBankCard cardObject:bankCards.values()){
			if(isBetween(carNumber,cardObject.getIinPrefixRangeFrom(),cardObject.getIinPrefixRangeTo())){
				orderPayment.setCardType(cardObject.getName());
				break;
			}
		}
		
//		orderPayment.setNameOnCard(posCardPayment.getNameOnCard());
		orderPayment.setCardExpiryMonth(posCardPayment.getCardExpiryMonth());
		orderPayment.setCardExpiryYear(posCardPayment.getCardExpiryYear());
		orderPayment.setCardApprovalCode(posCardPayment.getCardApprovalCode());
		orderPayment.setAccount(mCardAccountType);
		
		return orderPayment;
	}
	
	
	public BeanOrderPayment getCashOutPayment(
			BeanOrderHeader orderHeader) throws Exception {
		if(getCashOutAmount()==0) return null;
		PosCardPaymentPanel posCardPayment = this;
		BeanOrderPayment orderPayment = new BeanOrderPayment();
		orderPayment.setOrderId(orderHeader.getOrderId());
		orderPayment.setPaymentMode(PaymentMode.CashOut);
		orderPayment.setPaidAmount(posCardPayment.getCashOutAmount());
		// orderPayment.setCardName(posCardPayment.getCardName());
		orderPayment.setCardType(posCardPayment.getCardType());
		orderPayment.setCardNo(posCardPayment.getCardNumber());
//		orderPayment.setNameOnCard(posCardPayment.getNameOnCard());
		orderPayment.setCardExpiryMonth(posCardPayment.getCardExpiryMonth());
		orderPayment.setCardExpiryYear(posCardPayment.getCardExpiryYear());
		orderPayment.setCardApprovalCode(posCardPayment.getCardApprovalCode());
		orderPayment.setAccount(mCardAccountType);
		
		return orderPayment;
	}
	
	public boolean isBetween(int x, int lower, int upper) {
		  return lower <= x && x <= upper;
		}
	
	private boolean doCardPayment() throws Exception{
//		EFTMessageBase purchaseMessage;
		
		boolean paiedSatus=false;
		mCardPaymentRecoveryProvider = new PosCardPaymentRecoveryProvider();
		final PosMessageBoxForm msgForm =PosFormUtil.showCancelOnlyMessageBox(mParent, "Communicating with EFTPOS. Please wait...", null);
		msgForm.setCancelOnlyListner(new IPosMessageBoxFormCancelListener() {
			
			@Override
			public boolean onCancelButtonPressed() {
				boolean status =false;
				if(PosDeviceEFT.getInstance().cancelTxn()){
					purchaseCanceled = true;
					mCardPaymentRecoveryProvider.deleteEftPurchaseMessage();
					status =true;
					msgForm.setVisible(false);
					msgForm.dispose();
				}
				return status;	
			}
		});
		
		if(!getCardNumber().equals("")){
			
			EFTMessageMAN manMsg=new EFTMessageMAN();
			manMsg.setPurchaseAmount(String.valueOf(getPruchaseAmount()));
			manMsg.setCashOutAmount(String.valueOf(getCashOutAmount()));
			manMsg.setCardNumber(getCardNumber());
			manMsg.setMessageType(EFTMessageType.MAN);
			manMsg.setCardExpDate(PosNumberUtil.format(String.valueOf(getCardExpiryMonth()), "00") +
					PosNumberUtil.format(String.valueOf(getCardExpiryYear()), "00"));
			setManMsg(manMsg);
			
			try{
				/*
				 * Use swing worker for threading .
				 */
				SwingWorker<Boolean, String> swt = new SwingWorker<Boolean, String>() {
					@Override
					protected Boolean doInBackground() throws Exception {
						try {
							EFTResponseMessageMAN purchaseMessage=(EFTResponseMessageMAN)PosDeviceEFT.doPayment(mParent, getManMsg(),msgForm);
							setPurchaseMessageMAN(purchaseMessage);
						} catch (Exception e) {
							msgForm.setVisible(false);
							msgForm.dispose();
							throw new Exception(
									"Problem in communication. Please check the log for details.");
						}
						return true;
					}
					@Override
					protected void done() {
						msgForm.setVisible(false);
						msgForm.dispose();
					}
				};
				swt.execute();
				
				PosFormUtil.showLightBoxModal(mParent, msgForm);
				
				EFTResponseMessageMAN purchaseMessage=getPurchaseMessageMAN();
				setPurchaseMessageMAN(null);
				if (purchaseMessage == null) {
//					reset();
					PosDeviceEFT.getInstance().close();
					throw new CardPaymentException(
							"Connection to EFT failed, Please check your device.");
				} else if (purchaseMessage.getStatus()==EFTPurchaseStatus.ACCEPTED){
					mTxtCardNumber.setText(purchaseMessage.getCardNumber());
					mTxtAuthcode.setText(purchaseMessage.getBankReferance());
					mCardAccountType = purchaseMessage.getAccountType();
					paiedSatus=true;
				}else{
					throw new CardPaymentException("The card payment has been "+purchaseMessage.getStatus()+". Reason "+purchaseMessage.getDisplayMessage());
				}
			}catch (Exception e) {
				
				throw new CardPaymentException(e.getMessage());
			}
			
		}else{
			try {
				SwingWorker<Boolean, String> swt = new SwingWorker<Boolean, String>() {
					@Override
					protected Boolean doInBackground() throws Exception {
						try {
							EFTResponseMessagePUR purchaseMessage = (EFTResponseMessagePUR) PosDeviceEFT
									.doPayment(mParent, getPruchaseAmount(),
											getCashOutAmount(),msgForm); 
							setPurchaseMessagePUR(purchaseMessage);
						} catch (Exception e) {
							msgForm.setVisible(false);
							msgForm.dispose();
							throw new Exception(
									"Problem in communication. Please check the log for details.");
						}
						return true;
					}
					@Override
					protected void done() {
						msgForm.setVisible(false);
						msgForm.dispose();
					}
				};
				swt.execute();
				
				
				PosFormUtil.showLightBoxModal(mParent, msgForm);
				
				EFTResponseMessagePUR purchaseMessage=getPurchaseMessagePUR();
				setPurchaseMessagePUR(null);
				if (purchaseMessage == null) {
//					reset();
					if(purchaseCanceled){
						purchaseCanceled = false;
						throw new CardPaymentException(
								"The card payment has been CANCELED. Reason TRANS. CANCELLED");
					}else{
						
						PosDeviceEFT.getInstance().close();
						throw new CardPaymentException(
								"Connection to EFT failed, Please check your device.");
					}
				} else if (purchaseMessage.getStatus() == EFTPurchaseStatus.ACCEPTED) {
					mTxtCardNumber.setText(purchaseMessage.getCardNumber());
					mTxtAuthcode.setText(purchaseMessage.getAuthCode());
					mCardAccountType = purchaseMessage.getAccountType();
					paiedSatus = true;
				} else {
					throw new CardPaymentException("The card payment has been "
							+ purchaseMessage.getStatus() + ". Reason "
							+ purchaseMessage.getDisplayMessage());
				}
			} catch (Exception e) {
				throw new CardPaymentException(e.getMessage());
			}
			
			
		}
		return paiedSatus;
	}
	
    /**
	 * @return
	 * 
	 * 
	 */
	private EFTMessageMAN mEFTMessageMAN =null;
	
	protected EFTMessageMAN getManMsg() {
		// TODO Auto-generated method stub
		return mEFTMessageMAN;
	}

	private void setManMsg(EFTMessageMAN ManMSG){
		mEFTMessageMAN =ManMSG;
	}
	
	
	private EFTResponseMessagePUR mPurchaseMessagePUR = null; 
	
	private void setPurchaseMessagePUR(EFTResponseMessagePUR purchaseMessage){
		mPurchaseMessagePUR = purchaseMessage;
	}
	
	private EFTResponseMessagePUR getPurchaseMessagePUR(){
		return mPurchaseMessagePUR;
	}
	
    private EFTResponseMessageMAN mPurchaseMessageMAN = null; 
	
	private void setPurchaseMessageMAN(EFTResponseMessageMAN purchaseMessage){
		mPurchaseMessageMAN = purchaseMessage;
	}
	
	private EFTResponseMessageMAN getPurchaseMessageMAN(){
		return mPurchaseMessageMAN;
	}
	
	
	public class CardPaymentException extends Exception{
		
		public CardPaymentException(String message){
			super(message);
		}
	}

}
