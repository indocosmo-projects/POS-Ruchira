/**
 * 
 */
package com.indocosmo.pos.forms.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.RootPaneContainer;
import javax.swing.SortOrder;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import com.indocosmo.pos.common.PosEnvSettings;
import com.indocosmo.pos.common.PosLog;
import com.indocosmo.pos.common.utilities.HmsUtil;
import com.indocosmo.pos.common.utilities.PosFormUtil;
import com.indocosmo.pos.data.beans.BeanCustomer;
import com.indocosmo.pos.data.beans.BeanCustomerType;
import com.indocosmo.pos.data.beans.BeanGstPartyType;
import com.indocosmo.pos.data.beans.BeanGstRegisterType;
import com.indocosmo.pos.data.beans.BeanOrderCustomer;
import com.indocosmo.pos.data.beans.BeanOrderHeader;
import com.indocosmo.pos.data.providers.shopdb.PosCompanyItemProvider;
import com.indocosmo.pos.data.providers.shopdb.PosCustomerProvider;
import com.indocosmo.pos.data.providers.shopdb.PosCustomerTypeProvider;
import com.indocosmo.pos.data.providers.shopdb.PosGstPartyTypeProvider;
import com.indocosmo.pos.data.providers.shopdb.PosGstRegisterTypeProvider;
import com.indocosmo.pos.data.providers.shopdb.PosOrderCustomerProvider;
import com.indocosmo.pos.forms.components.buttons.PosButton;
import com.indocosmo.pos.forms.components.buttons.PosButton.ButtonStyle;
import com.indocosmo.pos.forms.components.buttons.listners.IPosButtonListner;
import com.indocosmo.pos.forms.components.search.IPosSearchableItem;
import com.indocosmo.pos.forms.components.textfields.PosItemBrowsableField;
import com.indocosmo.pos.forms.components.textfields.PosTouchableDigitalField;
import com.indocosmo.pos.forms.components.textfields.PosTouchableFieldBase;
import com.indocosmo.pos.forms.components.textfields.PosTouchableNumericField;
import com.indocosmo.pos.forms.components.textfields.PosTouchableTextField;
import com.indocosmo.pos.forms.components.textfields.listners.IPosTouchableFieldListner;
import com.indocosmo.pos.forms.components.textfields.listners.PosTouchableFieldAdapter;
import com.indocosmo.pos.forms.search.PosExtSearchForm;
import com.indocosmo.pos.forms.search.listener.adapter.PosItemExtSearchFormAdapter;

/**
 * @author sandhya
 * 
 */
@SuppressWarnings("serial")
public class PosOrderCustomerPanel extends JPanel    {

 	protected static final int PANEL_BORDER_WIDTH = 2;

	protected static final int IMAGE_BUTTON_WIDTH = 150;
	protected static final int IMAGE_BUTTON_HEIGHT = 60;

	protected static final int PANEL_CONTENT_H_GAP =1;//PosOrderEntryForm.PANEL_CONTENT_H_GAP;
	protected static final int PANEL_CONTENT_V_GAP =1;// PosOrderEntryForm.PANEL_CONTENT_V_GAP;


	protected static final Color LABEL_BG_COLOR = new Color(78, 128, 188);
	
	private static final boolean showGstDetails=PosEnvSettings.getInstance().getUISetting().getOrderEntryUISettings().isShowCustomerGSTDetails();
	
	protected static final int MAIN_PANEL_CONTENT_H_GAP = 1;
	protected static final int MAIN_PANEL_CONTENT_V_GAP = (showGstDetails?1:2); 
 
	private static final int ITEM_ROWS = (showGstDetails?12:9);
	private static final int ITEM_HEIGHT = PosTouchableFieldBase.LAYOUT_DEF_HEIGHT;
	
	private static final int MESSAGE_PANEL_HEIGHT =(showGstDetails?20:10); 
	
	private static final int TITLE_WIDTH =95;
	private static final int CUSTOMER_TEXT_WIDTH = 450;
	private static final int PHONE_TEXT_WIDTH = 198;
	private static final int STATE_CODE_TITLE_WIDTH =62;
	private static final int STATE_CODE_TEXT_WIDTH =140;
	private static final int SEARCH_BUTTON_WIDTH =100;
	
	public static final int CUSTOMER_PANEL_WIDTH = (TITLE_WIDTH + CUSTOMER_TEXT_WIDTH)
			+ PANEL_CONTENT_H_GAP * 1;
 	
	public static final int CUSTOMER_PANEL_HEIGHT  = ITEM_HEIGHT * ITEM_ROWS
			+ PANEL_CONTENT_V_GAP * (ITEM_ROWS + 1) + PANEL_BORDER_WIDTH * 2 +MESSAGE_PANEL_HEIGHT ;
	
	private PosTouchableTextField mTxtCustName;
	private PosTouchableTextField mTxtCustAddress;
	private PosTouchableTextField mTxtCustAddress2;
	private PosTouchableTextField mTxtCustAddress3;
	private PosTouchableTextField mTxtCustAddress4;
	private PosTouchableTextField mTxtCustCity;
	private PosTouchableTextField mTxtCustState;
	private PosTouchableDigitalField mTxtCustStateCode;
	private PosTouchableTextField mTxtCustCountry;
	private PosTouchableDigitalField mTxtCustPhone;
	private PosTouchableDigitalField mTxtCustPhone2;
	private PosTouchableTextField mTxtCustTin;
	private PosItemBrowsableField mFieldCustGstRegisterType;
	private PosItemBrowsableField mFieldCustGstPartyType;
	
	private PosButton btnCustomerRetrieve;
	private JPanel mCustomerDetPanel;

 	private ArrayList<BeanOrderCustomer> mPosCustomerList;
 	private PosOrderCustomerProvider mPosOrderCustomerProvider;
 	private PosGstRegisterTypeProvider mGstRegisterTypeProvider;
	private PosGstPartyTypeProvider mGstPartyTypeProvider;
	RootPaneContainer mParent;
	private BeanCustomer mSelectedCustomer;
	private BeanOrderCustomer mSelectedOrderCustomer;
	private BeanCustomer mDefaultCustomer;
	
	private BeanCustomer mParentSelectedCustomer;
	private BeanOrderCustomer mParentOrderCustomer;
	
	public PosOrderCustomerPanel(RootPaneContainer parent,BeanCustomer customer, BeanOrderCustomer orderCustomer) {

 		
		mParentSelectedCustomer=customer;
		mParentOrderCustomer=orderCustomer;
		
		mSelectedCustomer=customer;
		mSelectedOrderCustomer=orderCustomer;
		
		mParent=parent;
		setLayout(new FlowLayout(FlowLayout.LEFT,PANEL_CONTENT_H_GAP,PANEL_CONTENT_V_GAP));
		setSize(CUSTOMER_PANEL_WIDTH, CUSTOMER_PANEL_HEIGHT); 
		
		initControls();
	}

	private void initControls() {
		
		mGstRegisterTypeProvider=new PosGstRegisterTypeProvider();
		mGstPartyTypeProvider=new PosGstPartyTypeProvider();
			 		
		try{

			createUI();
			loadCustomerList();
			loadCustomerInfo();
			
		} catch (Exception e) {
			PosLog.write(this, "PosOrderInfoForm", e);
			PosFormUtil.showSystemError(this);
		}
		 
		resetGSTControls();
		if (mTxtCustName.isEnabled()){
			mTxtCustName.requestFocus();
			mTxtCustName.setSelectedAll();
		}	 
	} 
	
	/*
	 * 
	 */
	private void loadCustomerInfo(){
		
		if (mSelectedCustomer==null)
			mSelectedCustomer=mDefaultCustomer;
		
		if(mSelectedOrderCustomer==null)
			mSelectedOrderCustomer=new BeanOrderCustomer(mSelectedCustomer);
		 
		clearCustomerDetails();
		populateOrderCustomerDetails();
		 	 
		
		if (mTxtCustName.isEnabled()){
			mTxtCustName.requestFocus();
			mTxtCustName.setSelectedAll();
		}
		resetGSTControls();
	}
	/*
	 * 
	 */
	public void loadCustomerInfoByPhone(String phone){
		
		try {
			
			BeanOrderCustomer cust= mPosOrderCustomerProvider.loadCustomerByPhoneNo(phone,"");
			populateOrderCustomerDetails(cust);
			
		}catch(Exception ex) {
			PosFormUtil.showErrorMessageBox(mParent, "Could not load the customers, Please contact system adminstrator");
			 
		
		}
	}
	/*
	 * 
	 */
	private void loadCustomerInfoByPhone(){
		
		try {
			BeanOrderCustomer cust= mPosOrderCustomerProvider.loadCustomerByPhoneNo(mTxtCustPhone.getText(),mTxtCustPhone2.getText());
			populateOrderCustomerDetails(cust);
		}catch(Exception ex) {
			PosFormUtil.showErrorMessageBox(mParent, "Could not load the customers, Please contact system adminstrator");
			 
		
		}
	}
/**
 * 
 * @param cust
 */
	private void populateOrderCustomerDetails(BeanOrderCustomer cust){
		
		if(cust!=null) {
			mSelectedOrderCustomer=cust;
		 
			clearCustomerDetails();
			populateOrderCustomerDetails();
			 	 
			
			if (mTxtCustName.isEnabled()){
				mTxtCustName.requestFocus();
				mTxtCustName.setSelectedAll();
			}
			resetGSTControls();
		}
	}
	/*
	 * 
	 */
 
	private void populateOrderCustomerDetails(){
		System.out.println("mSelectedOrderCustomer====================>"+mSelectedOrderCustomer.toString());
		System.out.println("mSelectedOrderCustomer.getStateCode()====================>"+mSelectedOrderCustomer.getStateCode());
		mTxtCustName.setText(mSelectedOrderCustomer.getName());
		mTxtCustAddress.setText(mSelectedOrderCustomer.getAddress());
		mTxtCustAddress2.setText(mSelectedOrderCustomer.getAddress2());
		mTxtCustAddress3.setText(mSelectedOrderCustomer.getAddress3());
		mTxtCustAddress4.setText(mSelectedOrderCustomer.getAddress4());
		mTxtCustCity.setText(mSelectedOrderCustomer.getCity());
		mTxtCustState.setText(mSelectedOrderCustomer.getState());
		mTxtCustStateCode.setText(mSelectedOrderCustomer.getStateCode());
		mTxtCustCountry.setText(mSelectedOrderCustomer.getCountry());
		mTxtCustPhone.setText(mSelectedOrderCustomer.getPhoneNumber()==null?"":mSelectedOrderCustomer.getPhoneNumber());
		mTxtCustPhone2.setText(mSelectedOrderCustomer.getPhoneNumber2()==null?"":mSelectedOrderCustomer.getPhoneNumber2());
		mTxtCustTin.setText(mSelectedOrderCustomer.getTinNo());
		mFieldCustGstRegisterType.setSelectedItem(mSelectedOrderCustomer.getGstRegisterType());
		mFieldCustGstPartyType.setSelectedItem(mSelectedOrderCustomer.getGstPartyType());
		
		mTxtCustName.setEnabled(mSelectedCustomer.getId()== mDefaultCustomer.getId()  && 
				PosEnvSettings.getInstance().getUISetting().getOrderEntryUISettings().isAllowCustomerEdit());
		mTxtCustName.getResetButton().setEnabled(true);
		resetGSTControls();
		
		
	}
	
	
	/*
	 * 
	 */
 
	private void clearCustomerDetails(){
		
		 
		mTxtCustName.setText("");
		mTxtCustAddress.setText("");
		mTxtCustAddress2.setText("");
		mTxtCustAddress3.setText("");
		mTxtCustAddress4.setText("");
		mTxtCustCity.setText("");
		mTxtCustState.setText("");
		mTxtCustStateCode.setText("");
		mTxtCustCountry.setText("");
		mTxtCustPhone.setText("");
		mTxtCustPhone2.setText("");
		mTxtCustTin.setText("");
		 resetGSTControls();
		mTxtCustName.getResetButton().setEnabled(true);
		
	}
 	 
	/**
	 * @throws SQLException *
	 * 
	 */
	private void createUI() throws SQLException {
		
		 	
		mCustomerDetPanel =new JPanel();
		mCustomerDetPanel.setLayout(new FlowLayout(FlowLayout.LEFT,PANEL_CONTENT_H_GAP,PANEL_CONTENT_V_GAP));
		mCustomerDetPanel.setPreferredSize(new Dimension(CUSTOMER_PANEL_WIDTH , CUSTOMER_PANEL_HEIGHT));
		add( mCustomerDetPanel);
		 
		mTxtCustName = createTextField(mCustomerDetPanel, "Name :",'N',TITLE_WIDTH,CUSTOMER_TEXT_WIDTH -SEARCH_BUTTON_WIDTH -PANEL_CONTENT_H_GAP*2,250);
		mTxtCustName.setListner(customerNameListner);
		
		btnCustomerRetrieve=new PosButton("Search...");
		btnCustomerRetrieve.setMnemonic('X');
		btnCustomerRetrieve.setPreferredSize(new Dimension(SEARCH_BUTTON_WIDTH,ITEM_HEIGHT));
		btnCustomerRetrieve.setButtonStyle(ButtonStyle.IMAGE);
		btnCustomerRetrieve.setImage("cashout_search.png", "cashout_search_touch.png");
		btnCustomerRetrieve.setOnClickListner(customerRetrieveButtonListner);
		mCustomerDetPanel.add(btnCustomerRetrieve);
		
		mTxtCustPhone= createDigitalField(mCustomerDetPanel,"Ph/Mob :",'P',TITLE_WIDTH,PHONE_TEXT_WIDTH);
		mTxtCustPhone2= createDigitalField(mCustomerDetPanel,"",' ',0,CUSTOMER_TEXT_WIDTH-PHONE_TEXT_WIDTH-PANEL_CONTENT_H_GAP);
		mTxtCustPhone.hideResetButton(true);
	
		
		mTxtCustAddress = createTextField(mCustomerDetPanel, "Flt/House:",'F',250);
		mTxtCustAddress2 = createTextField(mCustomerDetPanel, "Building:",'B',250);
		mTxtCustAddress3 = createTextField(mCustomerDetPanel, "Road:",'o',250);
		mTxtCustAddress4 = createTextField(mCustomerDetPanel, "Blk/Area:",'l',250);
		mTxtCustCity = createTextField(mCustomerDetPanel, "City:",'i',250);
		mTxtCustState = createTextField(mCustomerDetPanel, "State:",'S',TITLE_WIDTH,CUSTOMER_TEXT_WIDTH-STATE_CODE_TITLE_WIDTH-STATE_CODE_TEXT_WIDTH-PANEL_CONTENT_H_GAP,250);
		mTxtCustStateCode = createDigitalField(mCustomerDetPanel, "Code:",'e',STATE_CODE_TITLE_WIDTH,STATE_CODE_TEXT_WIDTH);
		mTxtCustCountry= createTextField(mCustomerDetPanel,"Country:",'u',250);
		
	    int width=TITLE_WIDTH+CUSTOMER_TEXT_WIDTH ;
	    
		JPanel gstPanel = new JPanel();
		gstPanel
		.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
		gstPanel.setPreferredSize(new Dimension(CUSTOMER_PANEL_WIDTH   ,
				ITEM_HEIGHT*4 + PANEL_CONTENT_V_GAP*5));
		if(showGstDetails)
			mCustomerDetPanel.add(gstPanel);
		
		JLabel labelGST=new JLabel();
		labelGST.setText("GST Details");
		labelGST.setHorizontalAlignment(SwingConstants.CENTER);		
		labelGST.setPreferredSize(new Dimension(width, MESSAGE_PANEL_HEIGHT)); 			
		labelGST.setOpaque(true);
		labelGST.setBackground(LABEL_BG_COLOR);
		labelGST.setForeground(Color.WHITE);
		labelGST.setFont(PosFormUtil.getLabelFontSmall());
		gstPanel.add(labelGST); 
			
		mTxtCustTin = createTextField(gstPanel, "No.:",'G',TITLE_WIDTH, CUSTOMER_TEXT_WIDTH,250 );
		mTxtCustTin.setListner(customerTinNoListner);
		mFieldCustGstRegisterType=createGstRegisterTypeField(gstPanel);
		mFieldCustGstPartyType=createGstPartyTypeField(gstPanel);
	 		
		mTxtCustPhone.setListner(new IPosTouchableFieldListner() {
			
			@Override
			public void onValueSelected(Object value) {
				loadCustomerInfoByPhone();
				mTxtCustPhone2.requestFocus();
			}
			
			@Override
			public void onReset() {
				
			}
		});
	 		
		
		mTxtCustPhone2.setListner(new IPosTouchableFieldListner() {
			
			@Override
			public void onValueSelected(Object value) {
				loadCustomerInfoByPhone();
				mTxtCustAddress.requestFocus();
				mTxtCustAddress.setSelectedAll();
			}
			
			@Override
			public void onReset() {
				mTxtCustPhone.setText("");
				
			}
		});
	 		
	
//		setFocusListner(mTxtCustName,mTxtCustAddress);
		setFocusListner(mTxtCustAddress,mTxtCustAddress2);
		setFocusListner(mTxtCustAddress2,mTxtCustAddress3);
		setFocusListner(mTxtCustAddress3,mTxtCustAddress4);
		setFocusListner(mTxtCustAddress4,mTxtCustCity);
		setFocusListner(mTxtCustCity,mTxtCustState);
		setFocusListner(mTxtCustState,mTxtCustStateCode);
		setFocusListner(mTxtCustStateCode,mTxtCustCountry);
		setFocusListner(mTxtCustCountry,mTxtCustTin);
//		setFocusListner(mTxtCustPhone,mTxtCustTin);
		
		final boolean allowCustomerEdit=PosEnvSettings.getInstance().getUISetting().getOrderEntryUISettings().isAllowCustomerEdit();
		mTxtCustName.setEnabled(allowCustomerEdit);
		mTxtCustName.getResetButton().setEnabled(true);
		mTxtCustAddress.setEditable(allowCustomerEdit);
		mTxtCustCity.setEditable(allowCustomerEdit);
		mTxtCustState.setEditable(allowCustomerEdit);
		mTxtCustStateCode.setEditable(allowCustomerEdit);
		mTxtCustCountry.setEditable(allowCustomerEdit);
		mTxtCustPhone.setEditable(allowCustomerEdit);
		mTxtCustTin.setEditable(allowCustomerEdit);
		mFieldCustGstRegisterType.setEditable(allowCustomerEdit);
		mFieldCustGstPartyType.setEditable(allowCustomerEdit);
		
	}
	
	IPosTouchableFieldListner  customerNameListner= new IPosTouchableFieldListner() {
		
		@Override
		public void onValueSelected(Object value) {
			
			mTxtCustPhone.requestFocus();
			mTxtCustPhone.setSelectedAll();
		}
		
		@Override
		public void onReset() {
			mSelectedOrderCustomer =new BeanOrderCustomer(mDefaultCustomer);
			mTxtCustName.setEnabled(PosEnvSettings.getInstance().getUISetting().getOrderEntryUISettings().isAllowCustomerEdit());
			
			if(!PosEnvSettings.getInstance().getUISetting().getOrderEntryUISettings().isAllowCustomerEdit()){
			 
				populateCustomerDetails(mSelectedOrderCustomer);		
				resetGSTControls();
			}
			
			mTxtCustName.requestFocus();
		}
	};
	

	/*
	 * 
	 */
	private void resetGSTControls(){

		if (mTxtCustTin.getText().trim().equals("")){
			mFieldCustGstRegisterType.setSelectedItem(null);
			mFieldCustGstPartyType.setSelectedItem(null);
			mFieldCustGstRegisterType.setEditable(false);
			mFieldCustGstPartyType.setEditable(false);
		}else{
			mFieldCustGstRegisterType.setEditable(true);
			mFieldCustGstPartyType.setEditable(true);
		}
	}
	IPosTouchableFieldListner  customerTinNoListner= new IPosTouchableFieldListner() {
		
		@Override
		public void onValueSelected(Object value) {
			resetGSTControls();
		 
			
		}
		
		@Override
		public void onReset() {
			resetGSTControls();
			mTxtCustTin.requestFocus();
		}
	}; 
	/**
	 * 
	 * @param panel
	 * @param title
	 * @return
	 */
	private PosTouchableTextField createTextField(JPanel panel, String title,char mnemonic,int maxLength) {
		 
		return createTextField(panel,title,mnemonic,TITLE_WIDTH, CUSTOMER_TEXT_WIDTH,maxLength);
	}
//	/**
//	 * 
//	 * @param panel
//	 * @param title
//	 * @return
//	 */
//	private PosTouchableTextField createTextField(JPanel panel, String title,char mnemonic,int titleWidth,int textWidth,int maxLength) {
//		return createTextField(panel,title,mnemonic,titleWidth, textWidth,maxLength,true);
//
//		 
//	}
	/**
	 * 
	 * @param panel
	 * @param title
	 * @return
	 */
	private PosTouchableTextField createTextField(JPanel panel, String title,
						char mnemonic,int titleWidth,
						int textWidth,int maxLength ) {
		
		JPanel itemPanel = creatFieldPanelWithTitle( PosFormUtil.getMnemonicString(title,mnemonic),titleWidth,textWidth);
		panel.add(itemPanel);
		
		PosTouchableTextField text=new PosTouchableTextField(mParent,textWidth);
		text.setTitle(title);
		text.setMnemonic(mnemonic);
		text.setFont(PosFormUtil.getTextFieldFont());
		text.setMaxLength(maxLength);
		itemPanel.add(text);
		
		return text;
	}
	private void setFocusListner(Object currentControl, final Object nextControl){
		
		IPosTouchableFieldListner touchableFieldListner=new IPosTouchableFieldListner() {
			
			@Override
			public void onValueSelected(Object value) {
				
				if(nextControl.getClass().equals(PosTouchableTextField.class)){
					((PosTouchableTextField)nextControl).requestFocus();
					((PosTouchableTextField)nextControl).setSelectedAll();
				}else if(nextControl.getClass().equals(PosTouchableDigitalField.class)){
					((PosTouchableDigitalField)nextControl).requestFocus();
					((PosTouchableDigitalField)nextControl).setSelectedAll();
				}else if(nextControl.getClass().equals(PosTouchableNumericField.class)){
					((PosTouchableNumericField)nextControl).requestFocus();
					((PosTouchableNumericField)nextControl).setSelectedAll();
				}
			}
			
			@Override
			public void onReset() {
				// TODO Auto-generated method stub
				
			}
		};
		
		
		if(currentControl.getClass().equals(PosTouchableTextField.class))
			((PosTouchableTextField)currentControl).setListner(touchableFieldListner);
		else if(currentControl.getClass().equals(PosTouchableDigitalField.class))
			((PosTouchableDigitalField)currentControl).setListner(touchableFieldListner);
		else if(currentControl.getClass().equals(PosTouchableNumericField.class))
			((PosTouchableNumericField)currentControl).setListner(touchableFieldListner);
		 
	}
	
//	/**
//	 * 
//	 * @param panel
//	 * @param title
//	 * @return
//	 */
//	private PosTouchableDigitalField createDigitalField(JPanel panel, String title,char mnemonic) {
//		 
//		return createDigitalField(panel,title,mnemonic,TITLE_WIDTH, CUSTOMER_TEXT_WIDTH);
//	}
	/**
	 * 
	 * @param panel
	 * @param title
	 * @return
	 */
	private PosTouchableDigitalField createDigitalField(JPanel panel, String title,char mnemonic,int titleWidth,int textWidth) {
		
		JPanel itemPanel = creatFieldPanelWithTitle(PosFormUtil.getMnemonicString(title,mnemonic),titleWidth,textWidth);
		panel.add(itemPanel);
		
		
		PosTouchableDigitalField text=new PosTouchableDigitalField(mParent,textWidth);
		text.setTitle(title);
		text.setMnemonic(mnemonic);
		text.setFont(PosFormUtil.getTextFieldFont());
		itemPanel.add(text);
		
		return text;
	}
	
	 
	
	/***
	 * 
	 * @param panel
	 * @return
	 * @throws SQLException 
	 */
	private PosItemBrowsableField createGstRegisterTypeField(JPanel panel) throws SQLException {

		JPanel itemPanel  = creatFieldPanelWithTitle("Reg.:" ,TITLE_WIDTH,CUSTOMER_TEXT_WIDTH);
		panel.add(itemPanel);

		final PosItemBrowsableField field = new PosItemBrowsableField(mParent,
				CUSTOMER_TEXT_WIDTH);
		field.setBrowseWindowSize(2,2);
		field.setBrowseItemList(mGstRegisterTypeProvider.getRegisterTypeList());
		field.setTitle("GST Register Type ");
//		field.setMnemonic(' ');
		field.hideResetButton(false);
		field.setFont(PosFormUtil.getTextFieldFont());
		field.setListner(new PosTouchableFieldAdapter() {

			@Override
			public void onValueSelected(Object value) {
				 
			}

			/*
			 * (non-Javadoc)
			 * 
			 * @see com.indocosmo.pos.components.textfields.listners.
			 * PosTouchableFieldAdapter#onReset()
			 */
			@Override
			public void onReset() {
				 
			}
		});
		itemPanel.add(field);
		return field;
	}
	
	
	/***
	 * 
	 * @param panel
	 * @return
	 * @throws SQLException 
	 */
	private PosItemBrowsableField createGstPartyTypeField(JPanel panel) throws SQLException {

		JPanel itemPanel  = creatFieldPanelWithTitle("Party:" ,TITLE_WIDTH,CUSTOMER_TEXT_WIDTH);
		panel.add(itemPanel);

		final PosItemBrowsableField field = new PosItemBrowsableField(mParent,
				CUSTOMER_TEXT_WIDTH);
		field.setBrowseWindowSize(2,2);
		field.setBrowseItemList(mGstPartyTypeProvider.getPartyTypeList());
		field.setTitle("GST Party Type ");
//		field.setMnemonic(' ');
		field.hideResetButton(false);
		field.setFont(PosFormUtil.getTextFieldFont());
		field.setListner(new PosTouchableFieldAdapter() {

			@Override
			public void onValueSelected(Object value) {
				 
			}

			/*
			 * (non-Javadoc)
			 * 
			 * @see com.indocosmo.pos.components.textfields.listners.
			 * PosTouchableFieldAdapter#onReset()
			 */
			@Override
			public void onReset() {
				 
			}
		});
		itemPanel.add(field);
		return field;
	}
	 

	/***
	 * 
	 * @param title
	 * @return
	 */
	private JPanel creatFieldPanelWithTitle(String title,int titleWidth, int textWidth) {
		JPanel itemPanel = new JPanel();
		itemPanel
		.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
		itemPanel.setPreferredSize(new Dimension(titleWidth + textWidth  ,
				ITEM_HEIGHT));

		JLabel label = new JLabel(title);
		label.setPreferredSize(new Dimension(titleWidth, ITEM_HEIGHT));
		label.setBorder(new EmptyBorder(2, 2, 2, 2));
		label.setFont(PosFormUtil.getLabelFont());
		label.setOpaque(true);
		label.setBackground(Color.LIGHT_GRAY);
		
		
		if(titleWidth!=0)
			itemPanel.add(label);

		return itemPanel;
	}
	
	private void loadCustomerList(){
		
		mPosOrderCustomerProvider = new PosOrderCustomerProvider();
		mPosCustomerList = new ArrayList<BeanOrderCustomer>();
		mPosCustomerList.addAll(mPosOrderCustomerProvider.getItemList());
 
		mDefaultCustomer=(new PosCustomerProvider()).getDefaultCustomer();
		 
	}
	IPosButtonListner customerRetrieveButtonListner=new IPosButtonListner() {
		
		@Override
		public void onClicked(PosButton button) {
			
			if(mPosCustomerList!=null && mPosCustomerList.size()>0){
				
				final PosExtSearchForm serachForm=new PosExtSearchForm(mPosCustomerList);
				serachForm.setWindowTitle("Select Customer");
				serachForm.setSorting(0, SortOrder.ASCENDING);
				serachForm.setListner(new PosItemExtSearchFormAdapter() {

					@Override
					public boolean onAccepted(Object sender, IPosSearchableItem item) {
						
						
						BeanOrderCustomer customer=((BeanOrderCustomer) item).clone();
						PosCustomerTypeProvider customerTypeProvider=new PosCustomerTypeProvider();
						if (PosEnvSettings.getInstance().isEnabledHMSIntegration() && 
								customerTypeProvider.isRoomTypeCustomer(customer.getCutomerTypeId()) ){
							
							try{
								if (!HmsUtil.getGuestDetails(customer)){
									PosFormUtil.showErrorMessageBox(mParent, "There is no occupancy in the Room  " +  customer.getName() );
									return false;
								}
							}catch(Exception ex){
								PosFormUtil.showErrorMessageBox( mParent,ex.getMessage());
							}
						}
						
						mSelectedOrderCustomer = customer;
						populateCustomerDetails(mSelectedOrderCustomer);		
						resetGSTControls();
						
						return true;
					}
					
				});
				
				PosFormUtil.showLightBoxModal( mParent,serachForm);
			}
		}
	};
	
	 
 
	/*
	 * 
	 */
	private void populateCustomerDetails(BeanOrderCustomer customer){
		
		mTxtCustName.setText(customer.getName());
		mTxtCustAddress.setText(customer.getAddress());
		mTxtCustAddress2.setText(customer.getAddress2());
		mTxtCustAddress3.setText(customer.getAddress3());
		mTxtCustAddress4.setText(customer.getAddress4());
		mTxtCustCity.setText(customer.getCity());
		mTxtCustState.setText(customer.getState()==null?"":customer.getState());
		mTxtCustStateCode.setText(customer.getStateCode()==null?"":customer.getStateCode());
		mTxtCustCountry.setText(customer.getCountry()==null?"":customer.getCountry());
		mTxtCustPhone.setText(customer.getPhoneNumber()==null?"":customer.getPhoneNumber());
		mTxtCustPhone2.setText(customer.getPhoneNumber2()==null?"":customer.getPhoneNumber2());
		mTxtCustTin.setText(customer.getTinNo()==null?"":customer.getTinNo());
		mFieldCustGstRegisterType.setSelectedItem(customer.getGstRegisterType());
		mFieldCustGstPartyType.setSelectedItem(customer.getGstPartyType());
		
		mTxtCustName.setEnabled(customer.getId()== mDefaultCustomer.getId() && 
				PosEnvSettings.getInstance().getUISetting().getOrderEntryUISettings().isAllowCustomerEdit());
		mTxtCustName.getResetButton().setEnabled(true);
	}
	
	public boolean validateInputs() {
		
		boolean valid = true;
		

		
		if (mTxtCustName.getText().trim().equals("")){
			
			PosFormUtil.showErrorMessageBox(mParent, "Customer's Name is empty, Please enter a valid input");
			mTxtCustName.requestFocus();
			
			valid=false;
		}else if (mTxtCustPhone.getText().trim().length()>=50){
		
			PosFormUtil.showErrorMessageBox(mParent, "You have entered wrong data in Customer's Phone Number field, Please enter a valid input");
			mTxtCustPhone.requestFocus();
			valid=false;
		}else if (mTxtCustPhone2.getText().trim().length()>=50){
		
			PosFormUtil.showErrorMessageBox(mParent, "You have entered wrong data in Customer's Mobile Number field, Please enter a valid input");
			mTxtCustPhone2.requestFocus();
			valid=false;
		}else if (mTxtCustStateCode.getText().trim().length()>=10){
		
			PosFormUtil.showErrorMessageBox(mParent, "You have entered wrong data in state code field, Please enter a valid input");
			mTxtCustStateCode.requestFocus();
			valid=false;
		}
		return valid;
	}
	 
/*
 * 
 */
	public BeanOrderCustomer getSelectedOrderCustomer(){
	
		if(! validateInputs())
			return null;
	
		final BeanOrderCustomer orderCustomer=new BeanOrderCustomer();
		
		orderCustomer.setName(mTxtCustName.getText());
		orderCustomer.setAddress(mTxtCustAddress.getText());
		orderCustomer.setAddress2(mTxtCustAddress2.getText());
		orderCustomer.setAddress3(mTxtCustAddress3.getText());
		orderCustomer.setAddress4(mTxtCustAddress4.getText());
		orderCustomer.setCity(mTxtCustCity.getText());
		orderCustomer.setState(mTxtCustState.getText());
		orderCustomer.setStateCode(mTxtCustStateCode.getText());
		orderCustomer.setCountry(mTxtCustCountry.getText());
		orderCustomer.setPhoneNumber(mTxtCustPhone.getText());
		orderCustomer.setPhoneNumber2(mTxtCustPhone2.getText());
		orderCustomer.setTinNo(mTxtCustTin.getText());
		orderCustomer.setGstRegisterType((BeanGstRegisterType)mFieldCustGstRegisterType.getSelectedValue());
		orderCustomer.setGstPartyType((BeanGstPartyType)mFieldCustGstPartyType.getSelectedValue());
		orderCustomer.setCode(mSelectedOrderCustomer.getCode());
		orderCustomer.setId(mSelectedOrderCustomer.getId());
		return orderCustomer;
	
	}
	/*
	 * 
	 */
	public void reset(){
		
		
		mSelectedCustomer=mParentSelectedCustomer;
		mSelectedOrderCustomer=mParentOrderCustomer;
		loadCustomerInfo();
	}
}
