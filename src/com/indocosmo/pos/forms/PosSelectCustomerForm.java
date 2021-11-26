package com.indocosmo.pos.forms;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;

import com.indocosmo.pos.common.enums.CardTypes;
import com.indocosmo.pos.common.utilities.PosFormUtil;
import com.indocosmo.pos.common.utilities.PosResUtil;
import com.indocosmo.pos.data.beans.BeanCustomer;
import com.indocosmo.pos.data.providers.shopdb.PosCompanyItemProvider;
import com.indocosmo.pos.data.providers.shopdb.PosCustomerProvider;
import com.indocosmo.pos.forms.components.buttons.PosButton;
import com.indocosmo.pos.forms.components.buttons.listners.IPosButtonListner;
import com.indocosmo.pos.forms.components.buttons.listners.PosButtonListnerAdapter;
import com.indocosmo.pos.forms.components.textfields.PosCardReaderField;
import com.indocosmo.pos.forms.components.textfields.PosItemSearchableField;
import com.indocosmo.pos.forms.components.textfields.PosTouchableNumberField;
import com.indocosmo.pos.forms.components.textfields.listners.PosTouchableFieldAdapter;
import com.indocosmo.pos.forms.listners.IPosSelectCustomerFormListner;

@SuppressWarnings("serial")
public final class PosSelectCustomerForm extends JDialog {

	public final static int RETRIEVE_KEY_HEIGHT=40;
	public final static int RETRIEVE_KEY_WIDTH=62;

	private static final int TITLE_PANEL_HEIGHT=30;	
	private static final int CONTENT_PANEL_HEIGHT=217; 
	private static final int BOTTOM_PANEL_HEIGHT=76; 

	private static final int IMAGE_BUTTON_WIDTH=150;
	private static final int IMAGE_BUTTON_HEIGHT=60;

	private static final int IMAGE_BUTTON_RETRIEVE_WIDTH=125;
	private static final int IMAGE_BUTTON_RETRIEVE_HEIGHT=60;

	private static final int TEXT_FIELD_WIDTH=360;
	private static final int TEXT_FIELD_HEIGHT=40;
	private static final int CARD_FIELD_WIDTH=TEXT_FIELD_WIDTH;

	private static final int LABEL_WIDTH=170;
	private static final int LABEL_HEIGHT=40;	

	private static final int PANEL_CONTENT_H_GAP=PosOrderEntryForm.PANEL_CONTENT_H_GAP;
	private static final int PANEL_CONTENT_V_GAP=PosOrderEntryForm.PANEL_CONTENT_V_GAP;
	
	private static final int H_GAP_BTWN_CMPNTS=1;
	private static final int V_GAP_BTWN_CMPNTS=2;

	private static final int FORM_HEIGHT=TITLE_PANEL_HEIGHT+CONTENT_PANEL_HEIGHT+BOTTOM_PANEL_HEIGHT+PANEL_CONTENT_V_GAP*8;
	private static final int FORM_WIDTH=556;

	private JPanel mContentPane;
	private JPanel mTitlePanel;
	private JPanel mContentPanel;
	private JPanel mBottomPanel;

	private JLabel mlabelTitle;	

	private PosButton mButtonOk;
	private PosButton mButtonDefault;
	private PosButton mButtonCancel;

	private PosButton mClearButton;

	private JLabel mlabelCustomerID;
	//	private JTextField mTxtCustomerID;
	private PosItemSearchableField mTxtCustomerID;
	
	private JLabel mLabelPhoneNumber;
	private PosTouchableNumberField mTxtPhoneNumber;

	private JLabel mLabelCustomerName;
	private JTextField mTxtCustomerName;
	//	private PosClickableTextField mTxtCustomerName;

	private JLabel mLabelCustomerType;
	private JTextField mTxtCustomerType;

	private JLabel mLabelCardNumber;
	//	private JTextField mTxtCardNumber;	
//	private PosTouchableNumericField mTxtCardNumber;
	private PosCardReaderField mTxtCardNumber;
	private static final String IMAGE_BUTTON_OK="dlg_ok.png";
	private static final String IMAGE_BUTTON_OK_TOUCH="dlg_ok_touch.png";

	private static final String IMAGE_BUTTON_CANCEL="dlg_cancel.png";
	private static final String IMAGE_BUTTON_CANCEL_TOUCH="dlg_cancel_touch.png";	

	private static final String IMAGE_BUTTON_RETRIEVE="cust_retrieve.png";
	private static final String IMAGE_BUTTON_RETRIEVE_TOUCH="cust_retrieve_touch.png";

	private final static String CARD_NO_CLEAR_IMAGE="customer_select_ctrl_reset.png";
	private final static String CARD_NO_CLEAR_IMAGE_TOUCH="customer_select_ctrl_reset_touch.png";

	private static final Color PANEL_BG_COLOR=new Color(78,128,188);
	private static final Color LABEL_FG_COLOR=Color.WHITE;	
	
	private IPosSelectCustomerFormListner mPosSelectCustomerFormListner;

	private PosCustomerProvider mPosCustomerProvider;
	private PosCompanyItemProvider mPosCompanyProvider;
	private BeanCustomer mPosCustomer;
	private ArrayList<BeanCustomer> mPosCustomerList;
	private Map<String, BeanCustomer> mPosCustomerMap;
	private BeanCustomer mDefualtCustomer;
	
	public PosSelectCustomerForm(){
		loadCustomers();
		initControls();	
	}
	
	public PosSelectCustomerForm(String cardNo){
		loadCustomers();
		initControls();
		if(cardNo!=null && cardNo.trim().length()>0)
			onCardNumberEntered(cardNo);
	}
	
	private void loadCustomers(){
		
		mPosCustomerProvider=new PosCustomerProvider();
		mPosCompanyProvider=new PosCompanyItemProvider();
		
		mPosCustomerMap=new HashMap<String, BeanCustomer>();
		if(mPosCustomerProvider.getItemMap()!=null)
			mPosCustomerMap.putAll(mPosCustomerProvider.getItemMap());
		if(mPosCompanyProvider.getItemMap()!=null)
			mPosCustomerMap.putAll(mPosCompanyProvider.getItemMap());
		
		mPosCustomerList=new ArrayList<BeanCustomer>();
		if(mPosCustomerProvider.getItemList()!=null)
			mPosCustomerList.addAll(mPosCustomerProvider.getItemList());
		if(mPosCompanyProvider.getItemList()!=null)
			mPosCustomerList.addAll(mPosCompanyProvider.getItemList());
		
		mDefualtCustomer=mPosCustomerProvider.getDefaultCustomer();
	}

	private void initControls(){
		
		setSize(FORM_WIDTH, FORM_HEIGHT);
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
				FORM_WIDTH-PANEL_CONTENT_H_GAP*2, CONTENT_PANEL_HEIGHT);
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
		setCustomerId();
		setCardNumber();
		setPhoneNumber();
		setCustomerName();
		setCustomerType();
		setBottomPanel();
		setFocus();
	}	

	public void setFocus(){
		this.addWindowListener( new WindowAdapter() {
			public void windowOpened( WindowEvent e ){
				mTxtCardNumber.requestFocus();
			}
		}); 
	}

	private void setTitle(){
		mlabelTitle=new JLabel();
		mlabelTitle.setText("Customer Info.");
		mlabelTitle.setHorizontalAlignment(SwingConstants.CENTER);		
		mlabelTitle.setBounds(0, 0, mTitlePanel.getWidth(), mTitlePanel.getHeight());		
		mlabelTitle.setFont(PosFormUtil.getHeadingFont());
		mlabelTitle.setForeground(LABEL_FG_COLOR);
		mTitlePanel.add(mlabelTitle);		
	}

	private void setCardNumber(){
		

		int left=mlabelCustomerID.getX();		
		int top=mlabelCustomerID.getY()+mlabelCustomerID.getHeight()+V_GAP_BTWN_CMPNTS;
		
		mLabelCardNumber=new JLabel();
		mLabelCardNumber.setOpaque(true);
		mLabelCardNumber.setBackground(Color.lightGray);
		mLabelCardNumber.setText(PosFormUtil.getMnemonicString("Card Number :", 'N'));
		mLabelCardNumber.setBorder(new EmptyBorder(2, 2, 2, 2));
		mLabelCardNumber.setHorizontalAlignment(SwingConstants.LEFT);		
		mLabelCardNumber.setBounds(left, top, LABEL_WIDTH, LABEL_HEIGHT);		
		mLabelCardNumber.setFont(PosFormUtil.getLabelFont());
		mLabelCardNumber.setFocusable(true);
		mContentPanel.add(mLabelCardNumber);

		left=mLabelCardNumber.getX()+mLabelCardNumber.getWidth()+H_GAP_BTWN_CMPNTS;
		mTxtCardNumber=new PosCardReaderField(this,CARD_FIELD_WIDTH);
		mTxtCardNumber.setPosCardType(CardTypes.Member);
		mTxtCardNumber.setHorizontalTextAlignment(JTextField.LEFT);
		//		mTxtCardNumber.setBounds(left, top, CARD_FIELD_WIDTH, TEXT_FIELD_HEIGHT);	
		mTxtCardNumber.setLocation(left, top);
		mTxtCardNumber.setFont(PosFormUtil.getTextFieldBoldFont());	
		mTxtCardNumber.setTitle("Card Number");
		mTxtCardNumber.setMnemonic('N');
		mTxtCardNumber.setListner(new PosTouchableFieldAdapter() {

			@Override
			public void onValueSelected(Object value) {
				onCardNumberEntered(String.valueOf(value));
			}
			
			/* (non-Javadoc)
			 * @see com.indocosmo.pos.forms.components.textfields.listners.PosTouchableFieldAdapter#onReset()
			 */
			@Override
			public void onReset() {
			
				super.onReset();
				reset();
			}

		});
		mContentPanel.add(mTxtCardNumber);

		left=mTxtCardNumber.getX()+mTxtCardNumber.getWidth()+H_GAP_BTWN_CMPNTS;
		top=(top+mTxtCardNumber.getHeight()/2) - RETRIEVE_KEY_HEIGHT/2;
		mClearButton=new PosButton();
		mClearButton.setBounds(left, top, RETRIEVE_KEY_WIDTH, RETRIEVE_KEY_HEIGHT);
		mClearButton.setImage(CARD_NO_CLEAR_IMAGE);
		mClearButton.setTouchedImage(CARD_NO_CLEAR_IMAGE_TOUCH);
		mClearButton.setOnClickListner(new PosButtonListnerAdapter() {
			@Override
			public void onClicked(PosButton button) {
				clearData();
			}
		});
		//		mContentPanel.add(mClearButton);
	}
	
	/**
	 * 
	 */
	private void reset() {
		
		mPosCustomer=null;
		loadUserData(mPosCustomer);
		
	}
	
	private void setPhoneNumber(){
		

		int left=mLabelCardNumber.getX();		
		int top=mLabelCardNumber.getY()+mLabelCardNumber.getHeight()+V_GAP_BTWN_CMPNTS;
		
		mLabelPhoneNumber=new JLabel();
		mLabelPhoneNumber.setOpaque(true);
		mLabelPhoneNumber.setBackground(Color.lightGray);
		mLabelPhoneNumber.setText(PosFormUtil.getMnemonicString("Phone Number :", 'P'));
		mLabelPhoneNumber.setBorder(new EmptyBorder(2, 2, 2, 2));
		mLabelPhoneNumber.setHorizontalAlignment(SwingConstants.LEFT);		
		mLabelPhoneNumber.setBounds(left, top, LABEL_WIDTH, LABEL_HEIGHT);		
		mLabelPhoneNumber.setFont(PosFormUtil.getLabelFont());
		mLabelPhoneNumber.setFocusable(true);
		mContentPanel.add(mLabelPhoneNumber);

		left=mLabelPhoneNumber.getX()+mLabelPhoneNumber.getWidth()+H_GAP_BTWN_CMPNTS;
		mTxtPhoneNumber=new PosTouchableNumberField(this,CARD_FIELD_WIDTH);
		mTxtPhoneNumber.setHorizontalTextAlignment(JTextField.LEFT);
		//		mTxtCardNumber.setBounds(left, top, CARD_FIELD_WIDTH, TEXT_FIELD_HEIGHT);	
		mTxtPhoneNumber.setLocation(left, top);
		mTxtPhoneNumber.setFont(PosFormUtil.getTextFieldBoldFont());	
		mTxtPhoneNumber.setTitle("Phone Number");
		mTxtPhoneNumber.setMnemonic('P');
		mTxtPhoneNumber.setListner(new PosTouchableFieldAdapter() {

			@Override
			public void onValueSelected(Object value) {
				onPhoneNumberEntered(String.valueOf(value));
			}
			
			/* (non-Javadoc)
			 * @see com.indocosmo.pos.forms.components.textfields.listners.PosTouchableFieldAdapter#onReset()
			 */
			@Override
			public void onReset() {
			
				reset();
			}
			
		});
		mContentPanel.add(mTxtPhoneNumber);

//		left=mTxtPhoneNumber.getX()+mTxtPhoneNumber.getWidth()+H_GAP_BTWN_CMPNTS;
//		top=(top+mTxtPhoneNumber.getHeight()/2) - RETRIEVE_KEY_HEIGHT/2;
//		mClearButton=new PosButton();
//		mClearButton.setBounds(left, top, RETRIEVE_KEY_WIDTH, RETRIEVE_KEY_HEIGHT);
//		mClearButton.setImage(CARD_NO_CLEAR_IMAGE);
//		mClearButton.setTouchedImage(CARD_NO_CLEAR_IMAGE_TOUCH);
//		mClearButton.setOnClickListner(new PosButtonListnerAdapter() {
//			@Override
//			public void onClicked(PosButton button) {
//				clearData();
//			}
//		});
		//		mContentPanel.add(mClearButton);
	}
	
	/**
	 * @param phNo
	 */
	private void onPhoneNumberEntered(String phNo){
		
		for(BeanCustomer cust:mPosCustomerList){
			
			if(cust.getPhoneNumber()!=null && cust.getPhoneNumber().contains(phNo)){
				mPosCustomer=cust;
				break;
			}
		}
		
		loadUserData(mPosCustomer);
	}
	
	private void onCardNumberEntered(String cardNo){
		mPosCustomer=mPosCustomerMap.get(cardNo);
		loadUserData(mPosCustomer);
	}

	/**
	 * 
	 */
	private void clearData(){
		
		mTxtCardNumber.setText("");
		mTxtCustomerID.setText("");
		mTxtCustomerName.setText("");
		mTxtCustomerType.setText("");
	}

	/**
	 * 
	 */
	private void setCustomerId(){
		
		int left=PANEL_CONTENT_H_GAP/2;		
		int top=PANEL_CONTENT_V_GAP/2;
		mlabelCustomerID=new JLabel();
		mlabelCustomerID.setOpaque(true);
		mlabelCustomerID.setBackground(Color.LIGHT_GRAY);
		mlabelCustomerID.setText(PosFormUtil.getMnemonicString("Customer :", 'C'));
		mlabelCustomerID.setBorder(new EmptyBorder(2, 2, 2, 2));
		mlabelCustomerID.setHorizontalAlignment(SwingConstants.LEFT);		
		mlabelCustomerID.setBounds(left, top, LABEL_WIDTH, LABEL_HEIGHT);		
		mlabelCustomerID.setFont(PosFormUtil.getLabelFont());
		mlabelCustomerID.setFocusable(true);
		mContentPanel.add(mlabelCustomerID);

		left=mlabelCustomerID.getX()+mlabelCustomerID.getWidth()+H_GAP_BTWN_CMPNTS;
		mTxtCustomerID=new PosItemSearchableField(this,TEXT_FIELD_WIDTH,mPosCustomerList);
		mTxtCustomerID.setMnemonic('C');
		mTxtCustomerID.setHorizontalTextAlignment(JTextField.LEFT);
		mTxtCustomerID.setLocation(left, top);
		mTxtCustomerID.setFont(PosFormUtil.getTextFieldBoldFont());		
		mTxtCustomerID.setTitle("Select customer");
		mTxtCustomerID.setTextReadOnly(true);
		mTxtCustomerID.setListner(new PosTouchableFieldAdapter() {

			
			@Override
			public void onValueSelected(Object value) {
				
				mPosCustomer=(BeanCustomer)value;
				loadUserData(mPosCustomer);
			}
			
			/* (non-Javadoc)
			 * @see com.indocosmo.pos.forms.components.textfields.listners.PosTouchableFieldAdapter#onReset()
			 */
			@Override
			public void onReset() {

				reset();
				
			}
		});
		mContentPanel.add(mTxtCustomerID);
	}

	/**
	 * 
	 */
	private void setCustomerName(){
		
		int left=mLabelPhoneNumber.getX();		
		int top=mLabelPhoneNumber.getY()+mLabelPhoneNumber.getHeight()+V_GAP_BTWN_CMPNTS;
		mLabelCustomerName=new JLabel();
		mLabelCustomerName.setOpaque(true);
		mLabelCustomerName.setBackground(Color.LIGHT_GRAY);
		mLabelCustomerName.setText("Customer Name :");
		mLabelCustomerName.setBorder(new EmptyBorder(2, 2, 2, 2));
		mLabelCustomerName.setHorizontalAlignment(SwingConstants.LEFT);		
		mLabelCustomerName.setBounds(left, top, LABEL_WIDTH, LABEL_HEIGHT);		
		mLabelCustomerName.setFont(PosFormUtil.getLabelFont());
		mLabelCustomerName.setFocusable(true);
		mContentPanel.add(mLabelCustomerName);

		left=mLabelCustomerName.getX()+mLabelCustomerName.getWidth()+H_GAP_BTWN_CMPNTS;
		mTxtCustomerName=new JTextField();	
		mTxtCustomerName.setHorizontalAlignment(JTextField.LEFT);
		mTxtCustomerName.setBounds(left, top, TEXT_FIELD_WIDTH, TEXT_FIELD_HEIGHT);	
		mTxtCustomerName.setFont(PosFormUtil.getTextFieldBoldFont());		 
		mTxtCustomerName.setEditable(false);
		mTxtCustomerName.setCaretPosition(0);
		mContentPanel.add(mTxtCustomerName);
	}


	private void setCustomerType(){
		int left=mLabelCustomerName.getX();		
		int top=mLabelCustomerName.getY()+mLabelCustomerName.getHeight()+V_GAP_BTWN_CMPNTS;
		mLabelCustomerType=new JLabel();
		mLabelCustomerType.setOpaque(true);
		mLabelCustomerType.setBackground(Color.lightGray);
		mLabelCustomerType.setText("Customer Type :");
		mLabelCustomerType.setBorder(new EmptyBorder(2, 2, 2, 2));
		mLabelCustomerType.setHorizontalAlignment(SwingConstants.LEFT);		
		mLabelCustomerType.setBounds(left, top, LABEL_WIDTH, LABEL_HEIGHT);		
		mLabelCustomerType.setFont(PosFormUtil.getLabelFont());
		mLabelCustomerType.setFocusable(true);
		mContentPanel.add(mLabelCustomerType);

		left=mLabelCustomerType.getX()+mLabelCustomerType.getWidth()+H_GAP_BTWN_CMPNTS;
		mTxtCustomerType=new JTextField();	
		mTxtCustomerType.setHorizontalAlignment(JTextField.LEFT);
		mTxtCustomerType.setBounds(left, top, TEXT_FIELD_WIDTH, TEXT_FIELD_HEIGHT);	
		mTxtCustomerType.setFont(PosFormUtil.getTextFieldBoldFont());	
		mTxtCustomerType.setEditable(false);
		mContentPanel.add(mTxtCustomerType);
	}

	private void setBottomPanel(){
		
		mButtonOk=new PosButton();		
		mButtonOk.setText("OK");
		mButtonOk.setImage(PosResUtil.getImageIconFromResource(IMAGE_BUTTON_OK));
		mButtonOk.setTouchedImage(PosResUtil.getImageIconFromResource(IMAGE_BUTTON_OK_TOUCH));	
		mButtonOk.setHorizontalAlignment(SwingConstants.CENTER);		
		mButtonOk.setSize(IMAGE_BUTTON_WIDTH, IMAGE_BUTTON_HEIGHT );	
		mButtonOk.setOnClickListner(okButtonListner);
		mButtonOk.setEnabled(false);
		mButtonOk.setDefaultButton(true);
		mButtonOk.registerKeyStroke(KeyEvent.VK_ENTER,KeyEvent.CTRL_DOWN_MASK);
		mBottomPanel.add(mButtonOk);

		mButtonDefault=new PosButton();		
		mButtonDefault.setText("Default");
		mButtonDefault.setImage(PosResUtil.getImageIconFromResource(IMAGE_BUTTON_RETRIEVE));
		mButtonDefault.setTouchedImage(PosResUtil.getImageIconFromResource(IMAGE_BUTTON_RETRIEVE_TOUCH));	
		mButtonDefault.setHorizontalAlignment(SwingConstants.CENTER);		
		mButtonDefault.setSize(IMAGE_BUTTON_RETRIEVE_WIDTH, IMAGE_BUTTON_RETRIEVE_HEIGHT );	
		mButtonDefault.setOnClickListner(mDefaultButtonListner);
		mBottomPanel.add(mButtonDefault);

		mButtonCancel=new PosButton();		
		mButtonCancel.setText("Cancel");
		mButtonCancel.setCancel(true);
		mButtonCancel.setImage(PosResUtil.getImageIconFromResource(IMAGE_BUTTON_CANCEL));
		mButtonCancel.setTouchedImage(PosResUtil.getImageIconFromResource(IMAGE_BUTTON_CANCEL_TOUCH));	
		mButtonCancel.setHorizontalAlignment(SwingConstants.CENTER);		
		mButtonCancel.setSize( IMAGE_BUTTON_WIDTH, IMAGE_BUTTON_HEIGHT);		
		mButtonCancel.setOnClickListner(mCancelButtonListner);
		mBottomPanel.add(mButtonCancel);	

	}

	/**
	 * 
	 */
	private IPosButtonListner okButtonListner=new PosButtonListnerAdapter() {
		
		@Override
		public void onClicked(PosButton button) {
			if(mPosCustomer==null) return;
			setVisible(false);
			if(mPosSelectCustomerFormListner!=null){
//				mPosSelectCustomerFormListner.onOkClicked(mPosCustomer);
			}
			dispose();	
		}
	};

	/**
	 * 
	 */
	private IPosButtonListner mDefaultButtonListner=new PosButtonListnerAdapter() {
		@Override
		public void onClicked(PosButton button) {
			mPosCustomer=mDefualtCustomer;
			loadUserData(mPosCustomer);
		}
	};

	private  IPosButtonListner mCancelButtonListner= new PosButtonListnerAdapter() {
		@Override
		public void onClicked(PosButton button) {			
			setVisible(false);
			dispose();			
		}
	};	

	
	/**
	 * @param mCustItem
	 */
	private void loadUserData(BeanCustomer mCustItem){
		
		if(mCustItem!=null){
			mTxtCardNumber.setText(mCustItem.getCardNumber());
			mTxtCustomerID.setText(mCustItem.getCode());
			mTxtCustomerName.setText(mCustItem.getName());
			mTxtCustomerName.setCaretPosition(0);
			mTxtCustomerType.setText(mCustItem.getCustType().getName());
			mTxtPhoneNumber.setText(mCustItem.getPhoneNumber());
			mButtonOk.setEnabled(true);
			
		}else{
			
			mTxtCardNumber.setText("");
			mTxtCustomerID.setText("");
			mTxtCustomerName.setText("");
			mTxtCustomerType.setText("");
			mTxtPhoneNumber.setText("");
			mButtonOk.setEnabled(false);
		}
		mTxtCardNumber.requestFocus();
		mTxtCardNumber.setSelectedAll();
	}

	/**
	 * @param listner
	 */
	public void setListner(IPosSelectCustomerFormListner listner) {	
		mPosSelectCustomerFormListner=listner;
	}

	/**
	 * @return the mPosCustomerList
	 */
	public  ArrayList<BeanCustomer> getPosCustomerList() {
		return mPosCustomerList;
	}

	/**
	 * @param mPosCustomerList the mPosCustomerList to set
	 */
	public  void setPosCustomerList(
			ArrayList<BeanCustomer> posCustomerList) {
		mPosCustomerList = posCustomerList;
	}

	/**
	 * @return the mPosCustomerMap
	 */
	public Map<String, BeanCustomer> getPosCustomerMap() {
		return mPosCustomerMap;
	}

	/**
	 * @param mPosCustomerMap the mPosCustomerMap to set
	 */
	public void setmPosCustomerMap(Map<String, BeanCustomer> posCustomerMap) {
		this.mPosCustomerMap = posCustomerMap;
	}


}
