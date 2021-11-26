/**
 * 
 */
package com.indocosmo.pos.forms;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Map;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.indocosmo.pos.common.enums.BankAccountType;
import com.indocosmo.pos.common.utilities.PosFormUtil;
import com.indocosmo.pos.common.utilities.PosNumberUtil;
import com.indocosmo.pos.data.beans.BeanBankCard;
import com.indocosmo.pos.data.providers.shopdb.PosBankCardTypesProvider;
import com.indocosmo.pos.forms.components.textfields.PosItemBrowsableField;
import com.indocosmo.pos.forms.components.textfields.PosTouchableDigitalField;
import com.indocosmo.pos.forms.components.textfields.PosTouchableNumberField;
import com.indocosmo.pos.forms.components.textfields.PosTouchableTextField;
import com.indocosmo.pos.forms.components.textfields.listners.IPosTouchableFieldListner;
import com.indocosmo.pos.forms.components.textfields.listners.PosTouchableFieldAdapter;
import com.indocosmo.pos.forms.listners.IPosCardTxnDetailsFormListner;
import com.indocosmo.pos.terminal.devices.eftpos.messageobjects.EFTResponseMessagePUR;

/**
 * @author deepak
 *
 */
public class PosCardTxnDetailsForm extends PosBaseForm{

	private static final int PANEL_HEIGHT = 420;
	private static final int PANEL_WIDTH = 600;
	private JPanel mContentPanel;
	
	private JLabel mLabelTotalAmount;
	private PosTouchableTextField mTxtTotalAmount;

	private JLabel mlabelCardNumber;
	private PosTouchableDigitalField mTxtCardNumber;

	private JLabel mlabelCardType;
	private PosItemBrowsableField mTxtCardType;

	private JLabel mLabelCardHldrName;
	private PosTouchableTextField mTxtCardHldrName;

	private JLabel mLabelExpDate;
	private PosTouchableDigitalField mTxtExpMonth;
	private PosTouchableDigitalField mTxtExpYear;

	private JLabel mLabelPurchaseAmount;
	private PosTouchableNumberField mTxtPurchaseAmount;

	private JLabel mLabelCashOutAmount;  
	private PosTouchableNumberField mTxtCashOutAmount;

	private JLabel mLabelAuthcode;  
	private PosTouchableTextField mTxtAuthcode;

	private static final int TEXT_FIELD_WIDTH=380;
	private static final int TEXT_FIELD_DATE_WIDTH=190;
	
	private static final int LABEL_WIDTH=198;
	private static final int LABEL_HEIGHT=30;	

	private static final int PANEL_CONTENT_H_GAP=2;
	private static final int PANEL_CONTENT_V_GAP=2;

	private JLabel mMessagePanel;
	private EFTResponseMessagePUR purchaseMessage;
	private JLabel mLabelAccountType;
	private PosItemBrowsableField mTxtAccountType;
	protected BankAccountType mBankAccountType;
	public static final int MESSAGE_PANEL_HEIGHT=30;
	private static final Color LABEL_BG_COLOR=new Color(6,38,76);

	/**
	 * @param title
	 * @param cPanelwidth
	 * @param cPanelHeight
	 */
	public PosCardTxnDetailsForm(String title, int cPanelwidth, int cPanelHeight) {
		super(title, cPanelwidth, cPanelHeight);
		// TODO Auto-generated constructor stub
	}
	public PosCardTxnDetailsForm(){
		super("Card Txn Details", PANEL_WIDTH, PANEL_HEIGHT);
		initTxnDetailsUi();
	}

	/**
	 * 
	 */
	private void initTxnDetailsUi() {

		setNumber();
		setCardType();
		setExpDate();
		setCardHldrName();
		setPurchaseAmount();
		setCashOutAmount();
		setAccountType();
		setAuthCode();
//		reset();
	}
	
	/**
	 * 
	 */
	private void reset() {

		mTxtPurchaseAmount.setText("0.00");
		mTxtCashOutAmount.setText("0.00");
	}
	
	/**
	 * Creating the field for card number entry.
	 */
	private void setNumber(){

		int left=PANEL_CONTENT_H_GAP*2;		
		int top=mMessagePanel.getX()+mMessagePanel.getHeight()+PANEL_CONTENT_V_GAP*4;
		
		mlabelCardNumber=new JLabel();
		mlabelCardNumber.setText("<html>Card Number :<font color='red'><b>*</b></font></html>");
		mlabelCardNumber.setHorizontalAlignment(SwingConstants.LEFT);		
		mlabelCardNumber.setBounds(left, top, LABEL_WIDTH, LABEL_HEIGHT);		
		mlabelCardNumber.setFont(PosFormUtil.getLabelFont());
		mlabelCardNumber.setFocusable(true);
		mContentPanel.add(mlabelCardNumber);

		left=mlabelCardNumber.getX()+mlabelCardNumber.getWidth()+PANEL_CONTENT_H_GAP*2;
		mTxtCardNumber=new PosTouchableDigitalField(this,TEXT_FIELD_WIDTH);
		mTxtCardNumber.setTitle("Card number ?");
		mTxtCardNumber.setLocation(left, top);
		mTxtCardNumber.setHorizontalTextAlignment(JTextField.LEFT);
		
		mTxtCardNumber.setFont(PosFormUtil.getTextFieldFont());		
		
		mContentPanel.add(mTxtCardNumber);
	}
	
	/**
	 * Creating field for Card type.
	 */
	private void setCardType(){
		int left= mlabelCardNumber.getX();		
		int top=mTxtCardNumber.getY()+mTxtCardNumber.getHeight()+PANEL_CONTENT_V_GAP;
		
		mlabelCardType=new JLabel();
		mlabelCardType.setText("Card Type :");
		mlabelCardType.setHorizontalAlignment(SwingConstants.LEFT);		
		mlabelCardType.setBounds(left, top, LABEL_WIDTH, LABEL_HEIGHT);		
		mlabelCardType.setFont(PosFormUtil.getLabelFont());
		mlabelCardType.setFocusable(true);
		mContentPanel.add(mlabelCardType);

		left=mlabelCardType.getX()+mlabelCardType.getWidth()+PANEL_CONTENT_H_GAP*2;
		PosBankCardTypesProvider bankProvider = new PosBankCardTypesProvider();
		Map<Integer, BeanBankCard> bankCards = bankProvider.getBankCards();
		
		mTxtCardType=new PosItemBrowsableField(this,TEXT_FIELD_WIDTH);
		mTxtCardType.setBrowseItemList(new ArrayList<BeanBankCard>(bankCards.values()));
		mTxtCardType.setBrowseWindowSize(3, 3);
		mTxtCardType.setTitle("Card type ?");
		mTxtCardType.setLocation(left, top);
		
		mContentPanel.add(mTxtCardType);
		
			
	}
	
	/**
	 * Creating field for entering the  expiry  date of the card.
	 * 
	 */
	private void setExpDate() {
		int left=mlabelCardType.getX();		
		int top=mTxtCardType.getY()+mTxtCardType.getHeight()+PANEL_CONTENT_V_GAP;
		mLabelExpDate=new JLabel();
		mLabelExpDate.setText("Expiry Date(MM/YY) :");
		mLabelExpDate.setHorizontalAlignment(SwingConstants.LEFT);		
		mLabelExpDate.setBounds(left, top, LABEL_WIDTH, LABEL_HEIGHT);		
		mLabelExpDate.setFont(PosFormUtil.getLabelFont());
		mLabelExpDate.setFocusable(true);
		mContentPanel.add(mLabelExpDate);
		
		left=mLabelExpDate.getX()+mLabelExpDate.getWidth()+PANEL_CONTENT_H_GAP*2;
		mTxtExpMonth=new PosTouchableDigitalField(this,TEXT_FIELD_DATE_WIDTH);
		mTxtExpMonth.setTitle("Card expiry month ?");
		mTxtExpMonth.setHorizontalTextAlignment(JTextField.LEFT);
		mTxtExpMonth.setLocation(left, top);
		mTxtExpMonth.setTextFont(PosFormUtil.getTextFieldFont());	
		mTxtExpMonth.hideResetButton(true);
		mTxtExpMonth.getTexFieldComponent().setDocument(PosNumberUtil.createDecimalDocument());
		mContentPanel.add(mTxtExpMonth);
		
		left=mTxtExpMonth.getX()+mTxtExpMonth.getWidth();
		mTxtExpYear=new PosTouchableDigitalField(this,TEXT_FIELD_DATE_WIDTH);
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
		mContentPanel.add(mTxtExpYear);
		
	}

	
	
	/**
	 * Creating the field for Cash-out amount.
	 */
	private void setCashOutAmount(){
		int left=mLabelPurchaseAmount.getX();		
		int top=mTxtPurchaseAmount.getY()+mTxtPurchaseAmount.getHeight()+PANEL_CONTENT_V_GAP;
		
		mLabelCashOutAmount=new JLabel();
		mLabelCashOutAmount.setText("Cash Out :");
		mLabelCashOutAmount.setHorizontalAlignment(SwingConstants.LEFT);		
		mLabelCashOutAmount.setBounds(left, top, LABEL_WIDTH, LABEL_HEIGHT);		
		mLabelCashOutAmount.setFont(PosFormUtil.getLabelFont());
		mLabelCashOutAmount.setFocusable(true);
		mContentPanel.add(mLabelCashOutAmount);

		left=mLabelCashOutAmount.getX()+mLabelCashOutAmount.getWidth()+PANEL_CONTENT_H_GAP*2;
		mTxtCashOutAmount=new PosTouchableDigitalField(this,TEXT_FIELD_WIDTH);
		mTxtCashOutAmount.setTitle("Cash Out ?");
		mTxtCashOutAmount.setHorizontalTextAlignment(JTextField.RIGHT);
		mTxtCashOutAmount.setLocation(left, top);
		mTxtCashOutAmount.setTextFont(PosFormUtil.getTextFieldFont());	
		mTxtCashOutAmount.getTexFieldComponent().setDocument(PosNumberUtil.createDecimalDocument());
		mContentPanel.add(mTxtCashOutAmount);
	}
	
	
	/**
	 * Creating the field for account type.
	 */
	private void setAccountType() {

		int left=mLabelCashOutAmount.getX();		
		int top=mTxtCashOutAmount.getY()+mTxtCashOutAmount.getHeight()+PANEL_CONTENT_V_GAP;
		
		mLabelAccountType = new JLabel();
		mLabelAccountType.setText("<html>Account Type :<font color='red'><b>*</b></font></html>");
		mLabelAccountType.setHorizontalAlignment(SwingConstants.LEFT);
		mLabelAccountType.setBounds(left, top, LABEL_WIDTH, LABEL_HEIGHT);
		mLabelAccountType.setFont(PosFormUtil.getLabelFont());
		mLabelAccountType.setFocusable(true);
		mContentPanel.add(mLabelAccountType);
		
		left = mLabelAccountType.getX()+mLabelAccountType.getWidth()+PANEL_CONTENT_H_GAP*2;
		mTxtAccountType = new PosItemBrowsableField(this,TEXT_FIELD_WIDTH);
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
				mBankAccountType = (BankAccountType) value;
			}
		});
		mContentPanel.add(mTxtAccountType);
		
	}
	
	/**
	 * Creating the field for card holder name.
	 */
	private void setCardHldrName(){
		int left=mLabelExpDate.getX();		
		int top=mTxtExpMonth.getY()+mTxtExpMonth.getHeight()+PANEL_CONTENT_V_GAP;
		mLabelCardHldrName=new JLabel();
		mLabelCardHldrName.setText("Holder Name :");
		mLabelCardHldrName.setHorizontalAlignment(SwingConstants.LEFT);		
		mLabelCardHldrName.setBounds(left, top, LABEL_WIDTH, LABEL_HEIGHT);		
		mLabelCardHldrName.setFont(PosFormUtil.getLabelFont());
		mLabelCardHldrName.setFocusable(true);
		mContentPanel.add(mLabelCardHldrName);

		left=mLabelCardHldrName.getX()+mLabelCardHldrName.getWidth()+PANEL_CONTENT_H_GAP*2;
		mTxtCardHldrName=new PosTouchableTextField(this,TEXT_FIELD_WIDTH);
		mTxtCardHldrName.setTitle("Holder's name ?");
		mTxtCardHldrName.setHorizontalTextAlignment(JTextField.LEFT);
		mTxtCardHldrName.setLocation(left, top);
		mTxtCardHldrName.setTextFont(PosFormUtil.getTextFieldFont());	
		mContentPanel.add(mTxtCardHldrName);
	}

	/**
	 *  Creating the field setting purchase amount.
	 */
	private void setPurchaseAmount(){
		int left=mLabelCardHldrName.getX();		
		int top=mTxtCardHldrName.getY()+mTxtCardHldrName.getHeight()+PANEL_CONTENT_V_GAP;
		mLabelPurchaseAmount=new JLabel();
		mLabelPurchaseAmount.setText("<html>Purchase Amount :<font color='red'><b>*</b></font></html>");
		mLabelPurchaseAmount.setHorizontalAlignment(SwingConstants.LEFT);		
		mLabelPurchaseAmount.setBounds(left, top, LABEL_WIDTH, LABEL_HEIGHT);		
		mLabelPurchaseAmount.setFont(PosFormUtil.getLabelFont());
		mContentPanel.add(mLabelPurchaseAmount);

		left=mLabelPurchaseAmount.getX()+mLabelPurchaseAmount.getWidth()+PANEL_CONTENT_H_GAP*2;
		mTxtPurchaseAmount=new PosTouchableNumberField(this,TEXT_FIELD_WIDTH);
		mTxtPurchaseAmount.setTitle("Purchase Amount ?");
		mTxtPurchaseAmount.setHorizontalTextAlignment(JTextField.RIGHT);
		mTxtPurchaseAmount.setLocation(left, top);
		mTxtPurchaseAmount.setTextFont(PosFormUtil.getTextFieldFont());
		mTxtPurchaseAmount.setDefaultValue("0.00");
		mTxtPurchaseAmount.getTexFieldComponent().setDocument(PosNumberUtil.createDecimalDocument());
		mContentPanel.add(mTxtPurchaseAmount);
	}
	

	/**
	 *  Creating the field for entering authentication code.
	 */
	private void setAuthCode(){
		int left=mLabelAccountType.getX();		
		int top=mTxtAccountType.getY()+mTxtAccountType.getHeight()+PANEL_CONTENT_V_GAP;
		
		mLabelAuthcode=new JLabel(); 
		mLabelAuthcode.setText("<html>Auth. Code :<font color='red'><b>*</b></font></html>");
		mLabelAuthcode.setHorizontalAlignment(SwingConstants.LEFT);		
		mLabelAuthcode.setBounds(left, top, LABEL_WIDTH, LABEL_HEIGHT);		
		mLabelAuthcode.setFont(PosFormUtil.getLabelFont());
		mLabelAuthcode.setFocusable(true);
		mContentPanel.add(mLabelAuthcode);

		left=mLabelAuthcode.getX()+mLabelAuthcode.getWidth()+PANEL_CONTENT_H_GAP*2;
		mTxtAuthcode=new PosTouchableTextField(this,TEXT_FIELD_WIDTH);
		mTxtAuthcode.setTitle("Auth. Code ?");
		mTxtAuthcode.setHorizontalTextAlignment(JTextField.LEFT);
		mTxtAuthcode.setLocation(left, top);
		mTxtAuthcode.setTextFont(PosFormUtil.getTextFieldFont());		
		mContentPanel.add(mTxtAuthcode);
	}

	/**
	 * @param message
	 * @return
	 * Set the short message panel.
	 */
	protected JLabel getShortMessagePanel(String message) {
		JLabel labelMessage = new JLabel();
		labelMessage.setText(message);
		labelMessage.setHorizontalAlignment(SwingConstants.CENTER);
		labelMessage.setSize(new Dimension(getWidth()
				- (PANEL_CONTENT_H_GAP / 2 + 1), MESSAGE_PANEL_HEIGHT));
		labelMessage.setOpaque(true);
		labelMessage.setBackground(LABEL_BG_COLOR);
		labelMessage.setForeground(Color.WHITE);
		// mContentPanel.add(labelMessage);//
		return labelMessage;
	}
	
	/* (non-Javadoc)
	 * @see com.indocosmo.pos.forms.PosBaseForm#setContentPanel(javax.swing.JPanel)
	 * Set the content panel here.
	 */
	@Override
	protected void setContentPanel(JPanel panel) {
		mContentPanel = panel;
		mContentPanel.setLayout(null);
		mMessagePanel = getShortMessagePanel("Enter card transaction details.");
		mMessagePanel.setLocation(0, 0);
		mContentPanel.add(mMessagePanel);
		
	}
	/* (non-Javadoc)
	 * @see com.indocosmo.pos.forms.PosBaseForm#onOkButtonClicked()
	 * 
	 * Set the entered values to purchase message object.
	 */
	@Override
	public boolean onOkButtonClicked() {
		Boolean valid = true;
	    purchaseMessage = new EFTResponseMessagePUR();
	    if(mTxtPurchaseAmount.getText().trim().length()==0||mTxtCardNumber.getText().trim().length()==0||mTxtAccountType.getText().trim().length()==0||mTxtAuthcode.getText().trim().length()==0){
	    	PosFormUtil.showErrorMessageBox(this,"Please Enter the transanction details.");
	    	valid = false;
	    }else{
	    	purchaseMessage.setCardNumber(mTxtCardNumber.getText());
	    	purchaseMessage.setAccountType(mBankAccountType.getDisplayText());
	    	purchaseMessage.setAuthCode(mTxtAuthcode.getText());
	    	purchaseMessage.setCardAmount(mTxtPurchaseAmount.getText());
	    	purchaseMessage.setCashAmount(mTxtCashOutAmount.getText());
	    	 if(txnListner!=null){
	 	    	txnListner.onTxnCompleted(purchaseMessage);
	 	    	valid = true;
	 	    }
	    }
		return valid;
		
	}
	
	private IPosCardTxnDetailsFormListner txnListner;
	
	public void setListner(IPosCardTxnDetailsFormListner listner){
		txnListner=listner;
	}
	
	public void setCardPurchaseAmount(String amount){
		mTxtPurchaseAmount.setText(amount);
	}
	
	public void setCashAmount(String amount){
		mTxtCashOutAmount.setText(amount);
	}

}
