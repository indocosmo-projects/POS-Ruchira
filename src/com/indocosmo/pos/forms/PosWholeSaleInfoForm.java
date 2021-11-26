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
import com.indocosmo.pos.data.beans.BeanOrderHeader;
import com.indocosmo.pos.data.providers.shopdb.PosCustomerProvider;
import com.indocosmo.pos.forms.components.buttons.PosButton;
import com.indocosmo.pos.forms.components.buttons.listners.IPosButtonListner;
import com.indocosmo.pos.forms.components.buttons.listners.PosButtonListnerAdapter;
import com.indocosmo.pos.forms.components.search.IPosSearchableItem;
import com.indocosmo.pos.forms.components.textfields.PosCardReaderField;
import com.indocosmo.pos.forms.components.textfields.PosTouchableFieldBase;
import com.indocosmo.pos.forms.components.textfields.PosTouchableTextField;
import com.indocosmo.pos.forms.components.textfields.listners.PosTouchableFieldAdapter;
import com.indocosmo.pos.forms.search.PosExtSearchForm;
import com.indocosmo.pos.forms.search.listener.adapter.PosItemExtSearchFormAdapter;

/**
 * @author jojesh-13.2
 *
 */
@SuppressWarnings("serial")
public final class PosWholeSaleInfoForm extends JDialog {

	public final static int RETRIEVE_KEY_HEIGHT=PosTouchableFieldBase.RESET_BUTTON_DEF_HEIGHT;
	public final static int RETRIEVE_KEY_WIDTH=PosTouchableFieldBase.RESET_BUTTON_DEF_WIDTH-1;

	private static final int TITLE_PANEL_HEIGHT=30;	
	private static final int CONTENT_PANEL_HEIGHT=220; 
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
	private PosButton mButtonReset;
	private PosButton mButtonCancel;

	private JLabel mLabelCustomerName;
	private JTextField mTxtCustomerName;

	private JLabel mLabelCustomerType;
	private JTextField mTxtCustomerType;
	private PosTouchableTextField mTxtVehicleNumber;
	private JLabel mLabelCardNumber;
	private PosCardReaderField mTxtCardNumber;
	private static final String IMAGE_BUTTON_OK="dlg_ok.png";
	private static final String IMAGE_BUTTON_OK_TOUCH="dlg_ok_touch.png";

	private static final String IMAGE_BUTTON_CANCEL="dlg_cancel.png";
	private static final String IMAGE_BUTTON_CANCEL_TOUCH="dlg_cancel_touch.png";	

	private final static String CARD_NO_CLEAR_IMAGE="customer_select_ctrl_reset.png";
	private final static String CARD_NO_CLEAR_IMAGE_TOUCH="customer_select_ctrl_reset_touch.png";

	private final static String CUST_SEARCH_IMAGE="cust_search.png";
	private final static String CUST_SEARCH_IMAGE_TOUCH="cust_search_touch.png";

	private static final Color PANEL_BG_COLOR=new Color(78,128,188);
	private static final Color LABEL_FG_COLOR=Color.WHITE;	

	private PosCustomerProvider mPosCustomerProvider;
	private BeanCustomer mPosCustomer;
	private ArrayList<BeanCustomer> mPosCustomerList;
	private Map<String, BeanCustomer> mPosCustomerMap;
	private BeanCustomer mDefualtCustomer;
	private PosTouchableTextField mTxtDriverName;
	private PosButton mBtnSearchCustomer;
	private PosButton mBtnClearhCustomer;
	private boolean isFormCancelled=false;
	
	public PosWholeSaleInfoForm(){
		loadCustomers();
		initControls();	
	}

	public PosWholeSaleInfoForm(String cardNo){
		loadCustomers();
		initControls();
		if(cardNo!=null && cardNo.trim().length()>0)
			onCardNumberEntered(cardNo);
	}

	private void loadCustomers(){
		
		mPosCustomerProvider=new PosCustomerProvider();
		mPosCustomerMap=new HashMap<String, BeanCustomer>();
		if(mPosCustomerProvider.getItemMap()!=null)
			mPosCustomerMap.putAll(mPosCustomerProvider.getItemMap());
		
		mPosCustomerList=new ArrayList<BeanCustomer>();
		if(mPosCustomerProvider.getItemList()!=null)
			mPosCustomerList.addAll(mPosCustomerProvider.getItemList());
 
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
		setCardNumber();
		setCustomerName();
		setCustomerType();
		setVehicleInfo();
		setBottomPanel();
//		reset();
		setFocus();
	}	

	public void setFocus(){
		this.addWindowListener( new WindowAdapter() {
			public void windowOpened( WindowEvent e ){
				
				if(mTxtCardNumber.isEditable())
					mTxtCardNumber.requestFocus();
				else
					mTxtDriverName.requestFocus();
			}
		}); 
	}

	/**
	 * 
	 */
	private void setTitle(){
		
		mlabelTitle=new JLabel();
		mlabelTitle.setText("Whole Sale Info");
		mlabelTitle.setHorizontalAlignment(SwingConstants.CENTER);		
		mlabelTitle.setBounds(0, 0, mTitlePanel.getWidth(), mTitlePanel.getHeight());		
		mlabelTitle.setFont(PosFormUtil.getHeadingFont());
		mlabelTitle.setForeground(LABEL_FG_COLOR);
		mTitlePanel.add(mlabelTitle);		
	}

	/**
	 * 
	 */
	private void setCardNumber(){

		int left=PANEL_CONTENT_H_GAP/2;		
		int top=PANEL_CONTENT_V_GAP/2;
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
		mTxtCardNumber=new PosCardReaderField(this,CARD_FIELD_WIDTH-PosTouchableFieldBase.RESET_BUTTON_DEF_WIDTH);
		mTxtCardNumber.setPosCardType(CardTypes.Member);
		mTxtCardNumber.hideResetButton(true);
		mTxtCardNumber.setHorizontalTextAlignment(JTextField.LEFT);
		mTxtCardNumber.setLocation(left, top);
		mTxtCardNumber.setFont(PosFormUtil.getTextFieldBoldFont());	
		mTxtCardNumber.setTitle("Card Number");
		mTxtCardNumber.setMnemonic('N');
		mTxtCardNumber.setListner(new PosTouchableFieldAdapter() {

			@Override
			public void onValueSelected(Object value) {
				onCardNumberEntered(String.valueOf(value));
			}
		});
		mTxtCardNumber.setEditable(false);
		mContentPanel.add(mTxtCardNumber);

		left=mTxtCardNumber.getX()+mTxtCardNumber.getWidth();
		top=(top+mTxtCardNumber.getHeight()/2) - RETRIEVE_KEY_HEIGHT/2;
		mBtnSearchCustomer=new PosButton();
		mBtnSearchCustomer.setBounds(left, top, RETRIEVE_KEY_WIDTH, RETRIEVE_KEY_HEIGHT);
		mBtnSearchCustomer.setImage(CUST_SEARCH_IMAGE);
		mBtnSearchCustomer.setTouchedImage(CUST_SEARCH_IMAGE_TOUCH);
		mBtnSearchCustomer.setOnClickListner(new PosButtonListnerAdapter() {
			@Override
			public void onClicked(PosButton button) {

				PosExtSearchForm mSearchForm=new PosExtSearchForm(mPosCustomerList);
				mSearchForm.setShowHiddenItems(true);
				mSearchForm.setListner(new PosItemExtSearchFormAdapter() {

					@Override
					public boolean onAccepted(Object sender, IPosSearchableItem item) {
						mPosCustomer=(BeanCustomer)item;
						loadUserData(mPosCustomer);
						return true;
					}
				});
				mSearchForm.setWindowTitle("Customer");
				PosFormUtil.showLightBoxModal(PosWholeSaleInfoForm.this,mSearchForm);

			}
		});
		mContentPanel.add(mBtnSearchCustomer);

		left=mBtnSearchCustomer.getX()+mBtnSearchCustomer.getWidth()+1;
		top=mBtnSearchCustomer.getY();
		
		mBtnClearhCustomer=new PosButton();
		mBtnClearhCustomer.setBounds(left, top, RETRIEVE_KEY_WIDTH, RETRIEVE_KEY_HEIGHT);
		mBtnClearhCustomer.setImage(CARD_NO_CLEAR_IMAGE);
		mBtnClearhCustomer.setTouchedImage(CARD_NO_CLEAR_IMAGE_TOUCH);
		mBtnClearhCustomer.setOnClickListner(new PosButtonListnerAdapter() {
			@Override
			public void onClicked(PosButton button) {
				loadUserData(mDefualtCustomer);
			}
		});
//		mContentPanel.add(mBtnClearhCustomer);
	}

	/**
	 * @param cardNo
	 */
	private void onCardNumberEntered(String cardNo){
		mPosCustomer=mPosCustomerMap.get(cardNo);
		loadUserData(mPosCustomer);
	}
	
	/**
	 * 
	 */
	private void reset(){
		
		clearData();
//		mPosCustomer=mDefualtCustomer;
//		loadUserData(mPosCustomer);
//		mButtonOk.setEnabled(false);
	}

	/**
	 * 
	 */
	private void clearData(){
		
//		mTxtCardNumber.setText("");
//		mTxtCustomerName.setText("");
//		mTxtCustomerType.setText("");
		mTxtDriverName.setText("");
		mTxtVehicleNumber.setText("");
	}

	/**
	 * 
	 */
	private void setVehicleInfo(){

		int left=mLabelCustomerType.getX();		
		int top=mLabelCustomerType.getY()+mLabelCustomerType.getHeight()+V_GAP_BTWN_CMPNTS;

		final JLabel labelDriverName=new JLabel();
		labelDriverName.setOpaque(true);
		labelDriverName.setBackground(Color.lightGray);
		labelDriverName.setText(PosFormUtil.getMnemonicString("Driver Name :", 'D'));
		labelDriverName.setBorder(new EmptyBorder(2, 2, 2, 2));
		labelDriverName.setHorizontalAlignment(SwingConstants.LEFT);		
		labelDriverName.setBounds(left, top, LABEL_WIDTH, LABEL_HEIGHT);		
		labelDriverName.setFont(PosFormUtil.getLabelFont());
		labelDriverName.setFocusable(true);
		mContentPanel.add(labelDriverName);

		left=mLabelCardNumber.getX()+mLabelCardNumber.getWidth()+H_GAP_BTWN_CMPNTS;
		mTxtDriverName=new PosTouchableTextField(this,CARD_FIELD_WIDTH);
		mTxtDriverName.setHorizontalTextAlignment(JTextField.LEFT);
		mTxtDriverName.setLocation(left, top);
		mTxtDriverName.setFont(PosFormUtil.getTextFieldBoldFont());	
		mTxtDriverName.setTitle("Driver Name :");
		mTxtDriverName.setMnemonic('D');
		mContentPanel.add(mTxtDriverName);

		left=labelDriverName.getX();		
		top=labelDriverName.getY()+labelDriverName.getHeight()+V_GAP_BTWN_CMPNTS;

		final JLabel labelVehicleNumber=new JLabel();
		labelVehicleNumber.setOpaque(true);
		labelVehicleNumber.setBackground(Color.lightGray);
		labelVehicleNumber.setText(PosFormUtil.getMnemonicString("Vehicle Number :", 'V'));
		labelVehicleNumber.setBorder(new EmptyBorder(2, 2, 2, 2));
		labelVehicleNumber.setHorizontalAlignment(SwingConstants.LEFT);		
		labelVehicleNumber.setBounds(left, top, LABEL_WIDTH, LABEL_HEIGHT);		
		labelVehicleNumber.setFont(PosFormUtil.getLabelFont());
		labelVehicleNumber.setFocusable(true);
		mContentPanel.add(labelVehicleNumber);

		left=mLabelCardNumber.getX()+mLabelCardNumber.getWidth()+H_GAP_BTWN_CMPNTS;
		mTxtVehicleNumber =new PosTouchableTextField(this,CARD_FIELD_WIDTH);
		mTxtVehicleNumber.setHorizontalTextAlignment(JTextField.LEFT);
		mTxtVehicleNumber.setLocation(left, top);
		mTxtVehicleNumber.setFont(PosFormUtil.getTextFieldBoldFont());	
		mTxtVehicleNumber.setTitle("Vehicle Number :");
		mTxtVehicleNumber.setMnemonic('V');
		mContentPanel.add(mTxtVehicleNumber);
	}

	/**
	 * 
	 */
	private void setCustomerName(){
		
		int left=mLabelCardNumber.getX();		
		int top=mLabelCardNumber.getY()+mLabelCardNumber.getHeight()+V_GAP_BTWN_CMPNTS;
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
		mContentPanel.add(mTxtCustomerName);
	}


	/**
	 * 
	 */
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

	/**
	 * 
	 */
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

		mButtonReset=new PosButton();		
		mButtonReset.setText("Reset");
		mButtonReset.setImage(PosResUtil.getImageIconFromResource(IMAGE_BUTTON_CANCEL));
		mButtonReset.setTouchedImage(PosResUtil.getImageIconFromResource(IMAGE_BUTTON_CANCEL_TOUCH));	
		mButtonReset.setHorizontalAlignment(SwingConstants.CENTER);		
		mButtonReset.setSize(IMAGE_BUTTON_WIDTH, IMAGE_BUTTON_HEIGHT );	
		mButtonReset.setOnClickListner(resetbuttonListener);
		mBottomPanel.add(mButtonReset);

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

	private IPosButtonListner okButtonListner=new PosButtonListnerAdapter() {
		@Override
		public void onClicked(PosButton button) {
			
			setVisible(false);
			dispose();	
		}
	};

	private IPosButtonListner resetbuttonListener=new PosButtonListnerAdapter() {
		@Override
		public void onClicked(PosButton button) {
			reset();
			
		}
	};

	private  IPosButtonListner mCancelButtonListner= new PosButtonListnerAdapter() {
		@Override
		public void onClicked(PosButton button) {	
			
			isFormCancelled=true;
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
			mTxtCustomerName.setText(mCustItem.getName());
			mTxtCustomerName.setCaretPosition(0);
			mTxtCustomerType.setText(mCustItem.getCustType().getName());
			mButtonOk.setEnabled(true);
		}else{
			mTxtCardNumber.setText("");
			mTxtCustomerName.setText("");
			mTxtCustomerType.setText("");
			mButtonOk.setEnabled(false);
		}
		mTxtCardNumber.requestFocus();
		mTxtCardNumber.setSelectedAll();
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
	
	/**
	 * @return
	 */
	public BeanCustomer getCustomer(){
		
		return (mPosCustomer==null)?mDefualtCustomer:mPosCustomer;
	}
	
	/**
	 * @return
	 */
	public String getVehicleNumber(){
		
		return mTxtVehicleNumber.getText();
	}

	/**
	 * @return
	 */
	public String getDriverName(){
		
		return mTxtDriverName.getText();
	}
	

	/**
	 * @return
	 */
	public boolean isCancelled(){
		
		return isFormCancelled;
	}

	/**
	 * @param b
	 */
	public void setCustomoerInfoReadOnly(boolean readOnly) {
		
		mTxtCardNumber.setEditable(false);
		mBtnSearchCustomer.setEnabled(!readOnly);
		mBtnClearhCustomer.setEnabled(!readOnly);
	}

	/**
	 * @param oh
	 */
	public void setOrderHeader(BeanOrderHeader oh) {
		
		loadUserData(oh.getCustomer());
		if(oh.getDriverName()!=null)
			mTxtDriverName.setText(oh.getDriverName());
		if(oh.getVehicleNumber()!=null)
			mTxtVehicleNumber.setText(oh.getVehicleNumber());
		
	}

}
