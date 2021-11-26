/**
 * 
 */
package com.indocosmo.pos.forms;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.RootPaneContainer;
import javax.swing.SortOrder;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;

import com.indocosmo.pos.common.PosEnvSettings;
import com.indocosmo.pos.common.PosLog;
import com.indocosmo.pos.common.enums.PosOrderMedium;
import com.indocosmo.pos.common.enums.PosOrderServiceTypes;
import com.indocosmo.pos.common.enums.PosPaymentOption;
import com.indocosmo.pos.common.utilities.PosCurrencyUtil;
import com.indocosmo.pos.common.utilities.PosDateUtil;
import com.indocosmo.pos.common.utilities.PosFormUtil;
import com.indocosmo.pos.common.utilities.PosNumberUtil;
import com.indocosmo.pos.common.utilities.PosResUtil;
import com.indocosmo.pos.data.beans.BeanCashout;
import com.indocosmo.pos.data.beans.BeanCashoutRecentItem;
import com.indocosmo.pos.data.beans.BeanCustomer;
import com.indocosmo.pos.data.beans.BeanEmployees;
import com.indocosmo.pos.data.beans.BeanGstPartyType;
import com.indocosmo.pos.data.beans.BeanGstRegisterType;
import com.indocosmo.pos.data.beans.BeanOrderCustomer;
import com.indocosmo.pos.data.beans.BeanOrderHeader;
import com.indocosmo.pos.data.beans.BeanPayModeBase.PaymentMode;
import com.indocosmo.pos.data.providers.shopdb.PosCompanyItemProvider;
import com.indocosmo.pos.data.providers.shopdb.PosCustomerProvider;
import com.indocosmo.pos.data.providers.shopdb.PosGstPartyTypeProvider;
import com.indocosmo.pos.data.providers.shopdb.PosGstRegisterTypeProvider;
import com.indocosmo.pos.data.providers.shopdb.PosOrderCustomerProvider;
import com.indocosmo.pos.data.providers.shopdb.PosOrderPaymentsProvider;
import com.indocosmo.pos.data.providers.shopdb.PosServiceTableProvider;
import com.indocosmo.pos.data.providers.shopdb.PosWaiterProvider;
import com.indocosmo.pos.forms.components.PosOrderCustomerPanel;
import com.indocosmo.pos.forms.components.buttons.PosButton;
import com.indocosmo.pos.forms.components.buttons.PosButton.ButtonStyle;
import com.indocosmo.pos.forms.components.buttons.listners.IPosButtonListner;
import com.indocosmo.pos.forms.components.objectbrowser.IPosBrowsableItem;
import com.indocosmo.pos.forms.components.search.IPosSearchableItem;
import com.indocosmo.pos.forms.components.textfields.PosItemBrowsableField;
import com.indocosmo.pos.forms.components.textfields.PosTouchableDigitalField;
import com.indocosmo.pos.forms.components.textfields.PosTouchableFieldBase;
import com.indocosmo.pos.forms.components.textfields.PosTouchableNumberField;
import com.indocosmo.pos.forms.components.textfields.PosTouchableNumericField;
import com.indocosmo.pos.forms.components.textfields.PosTouchableTextField;
import com.indocosmo.pos.forms.components.textfields.listners.IPosTouchableFieldListner;
import com.indocosmo.pos.forms.components.textfields.listners.PosTouchableFieldAdapter;
import com.indocosmo.pos.forms.listners.IPosFormEventsListner;
import com.indocosmo.pos.forms.messageboxes.PosMessageBoxForm.MessageBoxButtonTypes;
import com.indocosmo.pos.forms.messageboxes.PosMessageBoxForm.MessageBoxResults;
import com.indocosmo.pos.forms.search.PosExtSearchForm;
import com.indocosmo.pos.forms.search.listener.IPosItemExtSearchFormListener;
import com.indocosmo.pos.forms.search.listener.adapter.PosItemExtSearchFormAdapter;

/**
 * @author sandhya
 * 
 */
@SuppressWarnings("serial")
public final class PosSalesOrderInfoForm extends PosBaseForm {

	protected static final int MAIN_PANEL_CONTENT_H_GAP = 1;
	protected static final int MAIN_PANEL_CONTENT_V_GAP = 1;
	
	protected static final int PANEL_CONTENT_H_GAP = 2;
	protected static final int PANEL_CONTENT_V_GAP = 1;
	
 
	private static final int ITEM_HEIGHT = PosTouchableFieldBase.LAYOUT_DEF_HEIGHT;
	private static final int TITLE_WIDTH =107;
	private static final int DELIVERY_DET_TEXT_WIDTH = 340;
	private static final int CUSTOMER_PANEL_HEIGHT =  PosOrderCustomerPanel.CUSTOMER_PANEL_HEIGHT;
	private static final int CUSTOMER_PANEL_WIDTH =PosOrderCustomerPanel.CUSTOMER_PANEL_WIDTH  ;
		//	+ PANEL_CONTENT_H_GAP *8 ;

	
	private static final int DELIVERY_DET_PANEL_WIDTH = (TITLE_WIDTH + DELIVERY_DET_TEXT_WIDTH)
			+ PANEL_CONTENT_H_GAP * 4;
	
	private static final int DELIVERY_DET_PANEL_HEIGHT =CUSTOMER_PANEL_HEIGHT + PANEL_CONTENT_V_GAP *4;
	
	private static final int LAYOUT_HEIGHT = CUSTOMER_PANEL_HEIGHT + PANEL_CONTENT_V_GAP *10  ;
	
//	private static final int CUSTOMER_PANEL_WIDTH = CUSTOMER_PANEL_WIDTH   + PANEL_CONTENT_H_GAP * 2 + PANEL_BORDER_WIDTH *2;
					
	private static final int LAYOUT_WIDTH = CUSTOMER_PANEL_WIDTH +DELIVERY_DET_PANEL_WIDTH 
			+ PANEL_CONTENT_H_GAP * 4 + PANEL_BORDER_WIDTH * 2;
	private static final boolean showGstDetails=PosEnvSettings.getInstance().getUISetting().getOrderEntryUISettings().isShowCustomerGSTDetails();
	

	private static final int TEXT_FIELD_WIDTH_DAYMONTH = 90;
	private static final int TEXT_FIELD_WIDTH_YEAR = 160;
	private static final int TEXT_FIELD_WIDTH_MINUTE = 140;
	private static final int TXT_AREA_FIELD_HEIGHT_DET =(showGstDetails?212:75);
	private static final int MESSAGE_PANEL_HEIGHT=25;
	
	private PosTouchableDigitalField mTxtDay;
	private PosTouchableDigitalField mTxtMonth;
	private PosTouchableDigitalField mTxtYear;
	private PosTouchableDigitalField mTxtHour;
	private PosTouchableDigitalField mTxtMinute;

	private PosTouchableNumericField mTxtExtraCharges;
	private PosTouchableNumericField mTxtAdvance;
	
	private PosItemBrowsableField mFieldOrderByMedium;
	private PosItemBrowsableField mFieldDeliveryType;
	private PosItemBrowsableField mFieldPayMode;
	private PosItemBrowsableField mFieldServedBy;
	
	private JTextArea mTxtRemarks;
	
	private JPanel mDeliveryDetPanel;
	private PosWaiterProvider mPosWaiterProvider;
	private ArrayList<BeanEmployees> mPosWaiterList; 
 	private BeanOrderHeader mPosOrderHObject;

 	private JPanel mContentPanel;

	private boolean isDirty = false;
 
 	private PosOrderEntryForm mParent;
 	private IPosSearchableItem mSelectedUser;
	
 	private ArrayList<BeanOrderCustomer> mPosCustomerList;
 	private PosOrderCustomerProvider mPosOrderCustomerProvider;
	private PosOrderCustomerPanel orderCustomerPanel;
	private PosCustomerProvider mPosCustomerProvider;
	
	
	private String dueDatetime;
	private boolean mIsDirty=false;
	private PosButton mButtonPrint;
	private JPanel mPanelDeliveryType;
	private JPanel mPanelServedBy;
	PosButton mButtonRemarksEdit;
	  PosButton mButtonRemarksReset;
	/**
	 * @param title
	 * @param cPanelwidth
	 * @param cPanelHeight
	 */
	public PosSalesOrderInfoForm(PosOrderEntryForm parent,BeanOrderHeader orderHObject) {

		super("Delivery Details", LAYOUT_WIDTH, LAYOUT_HEIGHT +PANEL_CONTENT_V_GAP*3);
		mPosOrderHObject = orderHObject;
		mParent = parent;
		init();
 
	}
	
	/**
	 * @param title
	 * @param cPanelwidth
	 * @param cPanelHeight
	 */
	public PosSalesOrderInfoForm(PosOrderEntryForm parent,BeanOrderHeader orderHObject,String phone) {

		super("Delivery Details", LAYOUT_WIDTH, LAYOUT_HEIGHT +PANEL_CONTENT_V_GAP*3);
		mPosOrderHObject = orderHObject;
		mParent = parent;
		init();
		orderCustomerPanel.loadCustomerInfoByPhone(phone);
 
	}
	private void init() {
		mPosWaiterProvider = new PosWaiterProvider();
		try{
		
			mPosWaiterList = mPosWaiterProvider.getWaiterList();
			createUI();
			loadCustomers();
			if(mPosOrderHObject.getOrderCustomer()!=null && !mPosOrderHObject.isNewOrder() ) 
				populateDeliveryDetails();
 
		} catch (Exception e) {
			PosLog.write(this, "PosOrderInfoForm", e);
			PosFormUtil.showSystemError(this);
		}
		
	}
 
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.indocosmo.pos.forms.PosBaseForm#setContentPanel(javax.swing.JPanel)
	 */
	@Override
	protected void setContentPanel(JPanel panel) {
		panel.setLayout(new FlowLayout(FlowLayout.LEFT,MAIN_PANEL_CONTENT_H_GAP ,
				MAIN_PANEL_CONTENT_V_GAP ));
		mContentPanel = panel;
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.pos.forms.PosBaseForm#onOkButtonClicked()
	 */
	@Override
	public boolean onOkButtonClicked() {
		
		if(! validateInputs())
			return false;
		
		updateSalesOrderDet(mPosOrderHObject);
		
		mIsDirty=true;
		return true;
		}
	
 
	
	/*
	 * 
	 */
	private void updateSalesOrderDet(BeanOrderHeader orderHdr){
		
	 			
		final BeanOrderCustomer orderCustomer= orderCustomerPanel.getSelectedOrderCustomer();
		orderHdr.setOrderCustomer(orderCustomer);
		
		if(orderCustomer!=null)
			orderHdr.setCustomer(mPosCustomerProvider.getAllCustomerById(orderCustomer.getId()));

		orderHdr.setDueDateTime(dueDatetime);
		orderHdr.setOrderByMedium((PosOrderMedium)mFieldOrderByMedium.getSelectedValue());
		orderHdr.setDeliveryType((PosOrderServiceTypes)mFieldDeliveryType.getSelectedValue());
		orderHdr.setAdvancePaymentMode((PaymentMode)mFieldPayMode.getSelectedValue());
		orderHdr.setAdvanceAmount(PosNumberUtil.parseDoubleSafely(mTxtAdvance.getText()));
		orderHdr.setExtraCharges(PosNumberUtil.parseDoubleSafely(mTxtExtraCharges.getText()));
		orderHdr.setExtraChargeRemarks(mTxtRemarks.getText());
		orderHdr.setServedBy(orderHdr.getOrderServiceType()==PosOrderServiceTypes.HOME_DELIVERY?((BeanEmployees)mSelectedUser):null);
		
	}
	/* (non-Javadoc)
	 * @see com.indocosmo.pos.forms.PosBaseForm#onCancelButtonClicked()
	 */
	@Override
	public boolean onCancelButtonClicked() {

		mIsDirty=false;
		return super.onCancelButtonClicked();
	}
	 
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indocosmo.pos.forms.PosBaseForm#onResetButtonClicked()
	 */
	@Override
	public void onResetButtonClicked() {
		super.onResetButtonClicked();
		
		reset();
		if(mPosOrderHObject.getOrderCustomer()!=null && !mPosOrderHObject.isNewOrder() ) 
			populateDeliveryDetails();
		
		orderCustomerPanel.reset();
		
 
	}
	private void reset(){
		
		setDefaultDueDate();
		setDefaultDueTime();
		mFieldOrderByMedium.setSelectedItem(PosOrderMedium.DIRECT);
		mFieldDeliveryType.setSelectedItem(PosOrderServiceTypes.TAKE_AWAY);
		mFieldPayMode.setSelectedItem(PaymentMode.Cash);
		mTxtExtraCharges.setText(PosCurrencyUtil.format(0));
		mTxtAdvance.setText(PosCurrencyUtil.format(0));
		
		mTxtRemarks.setText("");
//		mTxtRemarks.setEditable(false);
//		mButtonRemarksReset.setEnabled(false);
//		mButtonRemarksEdit.setEnabled(false);
		
	}
	public boolean isDirty(){
		
		return mIsDirty;
	}
	/**
	 * @throws SQLException *
	 * 
	 */
	private void createUI() throws SQLException {
		
		setResetButtonVisible(true);
 
		orderCustomerPanel=new PosOrderCustomerPanel(PosSalesOrderInfoForm.this,  mPosOrderHObject.getCustomer(),mPosOrderHObject.getOrderCustomer());
		orderCustomerPanel.setBorder(new EtchedBorder());
		mContentPanel.add( orderCustomerPanel);
		
		mDeliveryDetPanel =new JPanel();
		mDeliveryDetPanel.setLayout(new FlowLayout(FlowLayout.LEFT,PANEL_CONTENT_H_GAP,PANEL_CONTENT_V_GAP));
		mDeliveryDetPanel.setPreferredSize(new Dimension(DELIVERY_DET_PANEL_WIDTH , DELIVERY_DET_PANEL_HEIGHT));
		mDeliveryDetPanel.setBorder(new EtchedBorder());
		mContentPanel.add( mDeliveryDetPanel);
		  
		//Delivery detail panel
		createDateSelectionControl();
		createTimeFromSelectionControl();
		mFieldOrderByMedium =createOrderByMediumField(mDeliveryDetPanel);
		mFieldDeliveryType =createDeliveryTypeField(mDeliveryDetPanel);
		mFieldServedBy=createServedByField(mDeliveryDetPanel);
		mTxtAdvance = createNumericField(mDeliveryDetPanel, "Advance:",'v',TITLE_WIDTH,DELIVERY_DET_TEXT_WIDTH);
		
		mFieldPayMode=createPaymentModeField(mDeliveryDetPanel);
		mTxtExtraCharges = createNumericField(mDeliveryDetPanel, "Ad.Charge:",'A',TITLE_WIDTH,DELIVERY_DET_TEXT_WIDTH);
		
		createRemarksField(mDeliveryDetPanel);

		mTxtExtraCharges.setListner(new IPosTouchableFieldListner() {
			
			@Override
			public void onValueSelected(Object value) {
				
//				resetRemarksField();
			}
			
			@Override
			public void onReset() {

//				 mTxtRemarks.setEditable(false);
//				 mButtonRemarksReset.setEnabled(false);
//				 mButtonRemarksEdit.setEnabled(false);

			}
		}); 
		 
		setFocusListner(mTxtDay,mTxtMonth);
		setFocusListner(mTxtMonth,mTxtYear);
		setFocusListner(mTxtHour,mTxtMinute);
		
		setFocusListner(mTxtAdvance,mTxtExtraCharges);
		
		reset();
		createPrintButton();
		
		mSelectedUser=null;
		mPanelDeliveryType.setVisible(mPosOrderHObject.getOrderServiceType()==PosOrderServiceTypes.SALES_ORDER); 
		mPanelServedBy.setVisible(mPosOrderHObject.getOrderServiceType()==PosOrderServiceTypes.HOME_DELIVERY);
		if (mPosOrderHObject.getOrderServiceType()==PosOrderServiceTypes.HOME_DELIVERY)
			mFieldDeliveryType.setSelectedItem(PosOrderServiceTypes.HOME_DELIVERY);
	}
//	/*
//	 * 
//	 */
//	private void resetRemarksField(){
//		
//		if(PosNumberUtil.parseDoubleSafely(String.valueOf(mTxtExtraCharges.getText()))>0){
//			
//			 mTxtRemarks.setEditable(true);
//			 mButtonRemarksReset.setEnabled(true);
//			 mButtonRemarksEdit.setEnabled(true);
//		 }else{
//			 
//			 mTxtRemarks.setText("");
//			 mTxtRemarks.setEditable(false);
//			 mButtonRemarksReset.setEnabled(false);
//			 mButtonRemarksEdit.setEnabled(false);
//		 }
//	}
	 
	private void createDateSelectionControl() {
		 
		JPanel itemPanel = creatFieldPanelWithTitle("Due Date: ",TITLE_WIDTH,DELIVERY_DET_TEXT_WIDTH);
		mDeliveryDetPanel.add(itemPanel);
		
		
		mTxtDay=new PosTouchableDigitalField(this,TEXT_FIELD_WIDTH_DAYMONTH);
		mTxtDay.setTitle("Day");
		mTxtDay.setMnemonic('d');
		mTxtDay.setFont(PosFormUtil.getTextFieldFont());
		mTxtDay.hideResetButton(true);
		itemPanel.add(mTxtDay);
		
		mTxtMonth=new PosTouchableDigitalField(this,TEXT_FIELD_WIDTH_DAYMONTH);
		mTxtMonth.setTitle("Month");
		mTxtMonth.setMnemonic('m');
		mTxtMonth.setFont(PosFormUtil.getTextFieldFont());  
		mTxtMonth.hideResetButton(true);
		itemPanel.add(mTxtMonth);
		
		
		mTxtYear=new PosTouchableDigitalField(this,TEXT_FIELD_WIDTH_YEAR);
		mTxtYear.setTitle("Year");
		mTxtYear.setMnemonic('y');
		mTxtYear.setFont(PosFormUtil.getTextFieldFont());
		itemPanel.add(mTxtYear);
		
		mTxtYear.setListner(new IPosTouchableFieldListner() {
			
			@Override
			public void onValueSelected(Object value) {
				mTxtHour.requestFocus();
				mTxtHour.setSelectedAll();
			}
			
			@Override
			public void onReset() {
				setDefaultDueDate();
				mTxtDay.requestFocus();
				
			}
		});
		
	}


	/**
	 * 
	 */
	private void createTimeFromSelectionControl() {
		
		JPanel itemPanel = creatFieldPanelWithTitle("Time: ",TITLE_WIDTH,DELIVERY_DET_TEXT_WIDTH);
		mDeliveryDetPanel.add(itemPanel);
				
		mTxtHour=new PosTouchableDigitalField(this,TEXT_FIELD_WIDTH_DAYMONTH);
		mTxtHour.setTitle("Hour");
		mTxtHour.setMnemonic('h');
		mTxtHour.setFont(PosFormUtil.getTextFieldFont());
		mTxtHour.hideResetButton(true);
		itemPanel.add(mTxtHour);
		
		mTxtMinute=new PosTouchableDigitalField(this,TEXT_FIELD_WIDTH_MINUTE);
		mTxtMinute.setTitle("Minute");
		mTxtMinute.setMnemonic('t');
		mTxtMinute.setFont(PosFormUtil.getTextFieldFont());
		itemPanel.add(mTxtMinute);
		mTxtMinute.setListner(new IPosTouchableFieldListner() {
			
			@Override
			public void onValueSelected(Object value) {
				mTxtExtraCharges.requestFocus();
				mTxtExtraCharges.setSelectedAll();
			}
			
			@Override
			public void onReset() {
				setDefaultDueTime();
				mTxtHour.requestFocus();
			}
		});
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


	
	
	/**
	 * 
	 * @param panel
	 * @param title
	 * @return
	 */
	private PosTouchableNumericField createNumericField(JPanel panel, String title,char mnemonic,int titleWidth,int textWidth) {
		JPanel itemPanel = creatFieldPanelWithTitle(PosFormUtil.getMnemonicString(title,mnemonic),titleWidth,textWidth);
		panel.add(itemPanel);
		
		
		PosTouchableNumericField text=new PosTouchableNumericField(this,textWidth);
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
	 */
	private PosItemBrowsableField createOrderByMediumField(JPanel panel) {

		JPanel itemPanel  = creatFieldPanelWithTitle(PosFormUtil.getMnemonicString("Order By:", 'B'),TITLE_WIDTH,DELIVERY_DET_TEXT_WIDTH);
		panel.add(itemPanel);

		final PosItemBrowsableField field = new PosItemBrowsableField(this,
				DELIVERY_DET_TEXT_WIDTH);
		field.setBrowseWindowSize(2,2);
		field.setBrowseItemList(PosOrderMedium.values());
		field.setTitle("Order By Medium");
		field.setMnemonic('B');
		field.hideResetButton(false);
		field.setFont(PosFormUtil.getTextFieldFont());
		field.setListner(new PosTouchableFieldAdapter() {

			@Override
			public void onValueSelected(Object value) {
				
				if((PosOrderMedium)value==PosOrderMedium.EBS)
					mFieldPayMode.setSelectedItem(PaymentMode.Online);
				if(mPosOrderHObject.getOrderServiceType()==PosOrderServiceTypes.SALES_ORDER)
					mFieldDeliveryType.requestFocus();
				else
					mFieldServedBy.requestFocus();
			}

			/*
			 * (non-Javadoc)
			 * 
			 * @see com.indocosmo.pos.components.textfields.listners.
			 * PosTouchableFieldAdapter#onReset()
			 */
			@Override
			public void onReset() {
				mFieldOrderByMedium.setSelectedItem(PosOrderMedium.DIRECT);
			}
		});
		itemPanel.add(field);
		return field;
	}
	/***
	 * 
	 * @param panel
	 * @return
	 */
	private PosItemBrowsableField createServedByField(JPanel panel) {

		mPanelServedBy  = creatFieldPanelWithTitle(PosFormUtil.getMnemonicString("Served By:", 'B'),TITLE_WIDTH,DELIVERY_DET_TEXT_WIDTH);
		panel.add(mPanelServedBy);

		final PosItemBrowsableField field = new PosItemBrowsableField(this,
				DELIVERY_DET_TEXT_WIDTH);
		field.setBrowseWindowSize(2,2);
		field.setBrowseItemList(mPosWaiterList);
		field.setTitle("Served By");
		field.setMnemonic('B');
		field.hideResetButton(false);
		field.setFont(PosFormUtil.getTextFieldFont());
		field.setListner(new PosTouchableFieldAdapter() {

			@Override
			public void onValueSelected(Object value) {
				mSelectedUser = (IPosSearchableItem) value;
				isDirty = true;
			}

			/*
			 * (non-Javadoc)
			 * 
			 * @see com.indocosmo.pos.components.textfields.listners.
			 * PosTouchableFieldAdapter#onReset()
			 */
			@Override
			public void onReset() {
				mFieldOrderByMedium.setSelectedItem(PosOrderMedium.DIRECT);
			}
		});
		mPanelServedBy.add(field);
		return field;
	}
	
	/***
	 * 
	 * @param panel
	 * @return
	 */
	private PosItemBrowsableField createDeliveryTypeField(JPanel panel) {

		ArrayList<IPosBrowsableItem> orderServiceTypes =new ArrayList<IPosBrowsableItem>();
		
		orderServiceTypes.add(PosOrderServiceTypes.TAKE_AWAY);
		orderServiceTypes.add(PosOrderServiceTypes.HOME_DELIVERY);
		
		
		mPanelDeliveryType  = creatFieldPanelWithTitle(PosFormUtil.getMnemonicString("Delivery:", 'l'),TITLE_WIDTH,DELIVERY_DET_TEXT_WIDTH);
		panel.add(mPanelDeliveryType);
		
		final PosItemBrowsableField field = new PosItemBrowsableField(this,
				DELIVERY_DET_TEXT_WIDTH);
		field.setBrowseItemList(orderServiceTypes);
		field.setTitle("Delivery");
		field.setMnemonic('l');
		field.hideResetButton(false);
		field.setFont(PosFormUtil.getTextFieldFont());
		field.setListner(new PosTouchableFieldAdapter() {

			@Override
			public void onValueSelected(Object value) {
				 
				mTxtExtraCharges.requestFocus();
				mTxtExtraCharges.setSelectedAll();
			}

			/*
			 * (non-Javadoc)
			 * 
			 * @see com.indocosmo.pos.components.textfields.listners.
			 * PosTouchableFieldAdapter#onReset()
			 */
			@Override
			public void onReset() {
				mFieldDeliveryType.setSelectedItem(PosOrderServiceTypes.TAKE_AWAY);
			}
		});
		mPanelDeliveryType.add(field);
		return field;
	}
	
	/***
	 * 
	 * @param panel
	 * @return
	 */
	private PosItemBrowsableField createPaymentModeField(JPanel panel) {

		JPanel itemPanel  = creatFieldPanelWithTitle(PosFormUtil.getMnemonicString("Pay. Mode:", 'P'),TITLE_WIDTH,DELIVERY_DET_TEXT_WIDTH);
		panel.add(itemPanel);
		
		ArrayList<IPosBrowsableItem> orderPayModes =new ArrayList<IPosBrowsableItem>();
		
		orderPayModes.add(PaymentMode.Cash);
		orderPayModes.add(PaymentMode.Card);
		orderPayModes.add(PaymentMode.Online);

		final PosItemBrowsableField field = new PosItemBrowsableField(this,
				DELIVERY_DET_TEXT_WIDTH);
		field.setBrowseItemList(orderPayModes);
		field.setTitle("Payment Mode");
		field.setMnemonic('P');
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
				mFieldPayMode.setSelectedItem(PaymentMode.Cash);
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
		itemPanel.add(label);

		return itemPanel;
	}
	
	/**
	 * Create field for Reason.
	 */
	private void createRemarksField(JPanel panel) {
		
		JPanel itemPanel = new JPanel();
		itemPanel
		.setLayout(null);
//		itemPanel.setBackground(Color.DARK_GRAY);
		itemPanel.setPreferredSize(new Dimension(TITLE_WIDTH+DELIVERY_DET_TEXT_WIDTH  ,
				MESSAGE_PANEL_HEIGHT+TXT_AREA_FIELD_HEIGHT_DET )); 
		panel.add(itemPanel);
		
	    int width=TITLE_WIDTH+DELIVERY_DET_TEXT_WIDTH ;
		JLabel labelRemarks=new JLabel();
		labelRemarks.setText("Remarks(Additional Charge)");
		labelRemarks.setHorizontalAlignment(SwingConstants.CENTER);		
		labelRemarks.setBounds(0, 0, width, 20); 			
		labelRemarks.setOpaque(true);
		labelRemarks.setBackground(LABEL_BG_COLOR);
		labelRemarks.setForeground(Color.WHITE);
		labelRemarks.setFont(PosFormUtil.getLabelFontSmall());
		itemPanel.add(labelRemarks);
		
		width-=PosTouchableFieldBase.RESET_BUTTON_DEF_WIDTH;

		mTxtRemarks = new JTextArea(2,21);
		mTxtRemarks.setLineWrap(true);
		mTxtRemarks.setWrapStyleWord(false);
		mTxtRemarks.setFont(PosFormUtil.getTextFieldFont()); 

		JScrollPane scrollPane=new JScrollPane(mTxtRemarks);
		scrollPane.getVerticalScrollBar().setSize(new Dimension(15,0));
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setBorder(new LineBorder(new Color(134, 184, 232)));
		scrollPane.setBounds(0,labelRemarks.getHeight(),  width, TXT_AREA_FIELD_HEIGHT_DET); 			
		
		itemPanel.add(scrollPane);
	 
		final int buttonHeight=TXT_AREA_FIELD_HEIGHT_DET/2;
			
		  mButtonRemarksEdit=new PosButton("...");
		mButtonRemarksEdit.setButtonStyle(ButtonStyle.IMAGE);
		mButtonRemarksEdit.setMnemonic('m');
		mButtonRemarksEdit.setBounds(width, labelRemarks.getHeight(), PosTouchableFieldBase.BROWSE_BUTTON_WIDTH,buttonHeight -1);
		mButtonRemarksEdit.setImage(PosTouchableFieldBase.CLICK_BUTTON_NORMAL, PosTouchableFieldBase.CLICK_BUTTON_TOUCHED);
		mButtonRemarksEdit.setOnClickListner(new IPosButtonListner() {
			
			@Override
			public void onClicked(PosButton button) {
				
				PosFormUtil.showSoftKeyPad(PosSalesOrderInfoForm.this,mTxtRemarks, null);
			}
		});
		itemPanel.add(mButtonRemarksEdit);
		 
		mButtonRemarksReset=new PosButton();
		mButtonRemarksReset.setButtonStyle(ButtonStyle.IMAGE);
		mButtonRemarksReset.setBounds(width,labelRemarks.getHeight()+buttonHeight ,PosTouchableFieldBase.BROWSE_BUTTON_WIDTH,buttonHeight);
		mButtonRemarksReset.setImage(PosTouchableFieldBase.RESET_BUTTON_NORMAL, PosTouchableFieldBase.RESET_BUTTON_TOUCHED);
		mButtonRemarksReset.setOnClickListner(new IPosButtonListner() {
			
			@Override
			public void onClicked(PosButton button) {
				 
				mTxtRemarks.setText("");
				 
			}
		});
		itemPanel.add(mButtonRemarksReset);

	}
	
	private void loadCustomers(){
		
		mPosCustomerProvider=new PosCustomerProvider();
		mPosOrderCustomerProvider = new PosOrderCustomerProvider();
		mPosCustomerList = new ArrayList<BeanOrderCustomer>();
		mPosCustomerList.addAll(mPosOrderCustomerProvider.getItemList());

	}
 
	
	/*
	 * 
	 */
	private void setDefaultDueDate(){
		
		final java.util.Date dueDateTime=PosDateUtil.parse(PosDateUtil.DATE_FORMAT_NOW_24,PosDateUtil.getDateTime());
		Calendar cal = Calendar.getInstance();
		cal.setTime(dueDateTime);
		 
		mTxtDay.setText(PosNumberUtil.format(String.valueOf(cal.get(Calendar.DAY_OF_MONTH)),"00"));	
		mTxtMonth.setText(PosNumberUtil.format(String.valueOf(cal.get(Calendar.MONTH)+1),"00"));	  
		mTxtYear.setText(PosNumberUtil.format(String.valueOf(cal.get(Calendar.YEAR)),"0000"));	  	
	}
	
	/*
	 * 
	 */
	private void setDefaultDueTime(){
		
		final java.util.Date dueDateTime=PosDateUtil.parse(PosDateUtil.DATE_FORMAT_NOW_24,PosDateUtil.getDateTime());
		Calendar cal = Calendar.getInstance();
		cal.setTime(dueDateTime);
		 
		mTxtHour.setText(PosNumberUtil.format(String.valueOf(cal.get(Calendar.HOUR_OF_DAY)),"00"));	
		mTxtMinute.setText(PosNumberUtil.format(String.valueOf(cal.get(Calendar.MINUTE)),"00"));	
	}
	
	/*
	 * 
	 */
 
	private void populateDeliveryDetails(){
		
	 
		final java.util.Date dueDateTime=PosDateUtil.parse(PosDateUtil.DATE_FORMAT_NOW_24,mPosOrderHObject.getDueDateTime());
		Calendar cal = Calendar.getInstance();
		cal.setTime(dueDateTime);
		mTxtDay.setText(PosNumberUtil.format(String.valueOf(cal.get(Calendar.DAY_OF_MONTH)),"00"));	
		mTxtMonth.setText(PosNumberUtil.format(String.valueOf(cal.get(Calendar.MONTH)+1),"00"));	  
		mTxtYear.setText(PosNumberUtil.format(String.valueOf(cal.get(Calendar.YEAR)),"0000"));	  	
		mTxtHour.setText(PosNumberUtil.format(String.valueOf(cal.get(Calendar.HOUR_OF_DAY)),"00"));	
		mTxtMinute.setText(PosNumberUtil.format(String.valueOf(cal.get(Calendar.MINUTE)),"00"));	
		
		mFieldOrderByMedium.setSelectedItem(mPosOrderHObject.getOrderByMedium()); 
		mFieldDeliveryType.setSelectedItem(mPosOrderHObject.getDeliveryType());
		if(mPosOrderHObject.getOrderServiceType()==PosOrderServiceTypes.HOME_DELIVERY)
			mFieldServedBy.setSelectedItem(mPosOrderHObject.getServedBy());
		mTxtExtraCharges.setText(PosCurrencyUtil.format(mPosOrderHObject.getExtraCharges()));
		mTxtRemarks.setText(mPosOrderHObject.getExtraChargeRemarks());
		
		boolean isEditable=true;
		if (mPosOrderHObject.getAdvanceAmount()>0 && 
				mPosOrderHObject.getOrderPaymentHeaders()!=null  &&
				mPosOrderHObject.getOrderPaymentHeaders().size()>0){
			
			isEditable= (PosEnvSettings.getInstance().getPosDate().equals(mPosOrderHObject.getOrderPaymentHeaders().get(0).getPaymentDate()) &&
					    PosEnvSettings.getInstance().getCashierShiftInfo().getShiftItem().getId()== 
					    mPosOrderHObject.getOrderPaymentHeaders().get(0).getShiftId());
		}
		mTxtAdvance.setEditable(isEditable);
		mFieldPayMode.setEditable(isEditable);
		mTxtAdvance.setText(PosCurrencyUtil.format(mPosOrderHObject.getAdvanceAmount())); 
		if(mPosOrderHObject.getAdvancePaymentMode()!=null) mFieldPayMode.setSelectedItem(mPosOrderHObject.getAdvancePaymentMode());
		
//		resetRemarksField();
//		mTxtCustName.setEnabled(orderCustomer.getId()== mDefaultCustomer.getId());
//		mTxtCustName.getResetButton().setEnabled(true);
		 
		
	}
 
	private boolean validateInputs() {
		
		boolean valid = true;
		
//		else if (mTxtCustPhone.getText().equals("")){
//			PosFormUtil.showErrorMessageBox(PosSalesOrderInfoForm.this, "Customer's Phone Number field is empty, Please enter a valid input");
//			mTxtCustPhone.requestFocus();
//			valid=false;
//		}
		
//		if (mTxtCustName.getText().trim().equals("")){
//			PosFormUtil.showErrorMessageBox(PosSalesOrderInfoForm.this, "Customer's Name is empty, Please enter a valid input");
//			mTxtCustName.requestFocus();
//			
//			valid=false;
//		}else 
		if (!orderCustomerPanel.validateInputs())
			valid=false;
		else if (mFieldOrderByMedium.getSelectedValue() ==null){
			PosFormUtil.showErrorMessageBox(PosSalesOrderInfoForm.this, "Order Medium field is empty, Please enter a valid input");
			valid=false;
		}else if (mPosOrderHObject.getOrderServiceType()==PosOrderServiceTypes.SALES_ORDER && mFieldDeliveryType.getSelectedValue() ==null){
			PosFormUtil.showErrorMessageBox(PosSalesOrderInfoForm.this, "Delivery Type field is empty, Please enter a valid input");
			valid=false;
		} else if (PosNumberUtil.parseDoubleSafely(mTxtExtraCharges.getText())< 0  || PosNumberUtil.parseDoubleSafely(mTxtExtraCharges.getText())>9999999){
			PosFormUtil.showErrorMessageBox(PosSalesOrderInfoForm.this, "Amount entered in Additional charge field is not valid, Please enter a valid input");
			mTxtExtraCharges.requestFocus();
			mTxtExtraCharges.setSelectedAll();
			valid=false;
		} else if (PosNumberUtil.parseDoubleSafely(mTxtAdvance.getText())< 0 || PosNumberUtil.parseDoubleSafely(mTxtAdvance.getText())>999999){
			PosFormUtil.showErrorMessageBox(PosSalesOrderInfoForm.this, "Amount entered in Advance field is not valid, Please enter a valid input");
			mTxtAdvance.requestFocus();
			mTxtAdvance.setSelectedAll();
			valid=false;
		} else if (PosNumberUtil.parseDoubleSafely(mTxtAdvance.getText())> 0  && mFieldPayMode.getSelectedValue()==null ){
			PosFormUtil.showErrorMessageBox(PosSalesOrderInfoForm.this, "Payment mode field is empty, Please enter a valid input");
			mFieldPayMode.requestFocus();
			valid=false;
		} else
			valid=validateDueDateTime();
		
//		else if(mPosOrderHObject.getOrderServiceType()==PosOrderServiceTypes.HOME_DELIVERY && mSelectedUser==null){
//			PosFormUtil.showErrorMessageBox(PosSalesOrderInfoForm.this, "Served by field is empty, Please select waiter");
//			mFieldServedBy.requestFocus();
//			valid=false;
//		}
//		 else if (PosNumberUtil.parseDoubleSafely(mTxtAdvance.getText())> mPosOrderHObject.getTotalAmount()){
//				PosFormUtil.showErrorMessageBox(PosSalesOrderInfoForm.this, "Advance amount is  Please enter a valid input");
//				mTxtAdvance.requestFocus();
//				valid=false;
//			}
		return valid;
	}
	/**
	 *  
	 * @return
	 */
	private boolean validateDueDateTime() {
		boolean valid = true;
		 
		if ((mTxtDay.getText().trim().length() == 0)
				|| (mTxtDay.getText().trim().length() > 2)) {
			PosFormUtil.showErrorMessageBox(this,"Invalid day.");
			mTxtDay.requestFocus();
			mTxtDay.setSelectedAll();
			valid = false;
		} else if ((mTxtMonth.getText().trim().length() == 0)
				|| (mTxtMonth.getText().trim().length() > 2)) {
			PosFormUtil.showErrorMessageBox(this,"Invalid month.");
			mTxtMonth.requestFocus();
			mTxtMonth.setSelectedAll();
			valid = false;
		} else if ((mTxtYear.getText().trim().length()) != 4) {
			PosFormUtil.showErrorMessageBox(this,"Invalid year.");
			mTxtYear.requestFocus();
			mTxtYear.setSelectedAll();
			valid = false;
		}else if (mTxtHour.getText().trim().length() == 0
				|| mTxtHour.getText().trim().length() > 2) {
			PosFormUtil.showErrorMessageBox(this, "Invalid Hour.");
			valid = false;
			mTxtHour.requestFocus();
			mTxtHour.setSelectedAll();
		} else if (mTxtMinute.getText().trim().length() == 0
				|| mTxtMinute.getText().trim().length() > 2) {
			PosFormUtil.showErrorMessageBox(this, "Invalid Minute.");
			valid = false;
			mTxtMinute.requestFocus();
			mTxtMinute.setSelectedAll();
		} 
		 
		
		final String deliveryDate= PosDateUtil
		.buildStringDate(mTxtYear.getText()
				.trim(), mTxtMonth.getText().trim(), mTxtDay.getText()
				.trim(), PosDateUtil.DATE_SEPERATOR);
		
		final String orderTime=PosDateUtil
				.buildStringTime(mTxtHour.getText()
				.trim(), mTxtMinute.getText().trim(), "00",
				PosDateUtil.TIME_SEPERATOR);
		
		if (valid && !PosDateUtil.validateDate(deliveryDate)) {
			PosFormUtil.showErrorMessageBox(this, "Date is not in proper format.");
			valid = false;
			mTxtDay.requestFocus();
			mTxtDay.setSelectedAll();
		} else if (valid && !PosDateUtil.validateTime(orderTime)) {
			PosFormUtil.showErrorMessageBox(this, "Time is not in proper format.");
			valid = false;
			mTxtHour.requestFocus();
			mTxtHour.setSelectedAll();
		}
		
		dueDatetime =deliveryDate +" "+ orderTime;
		
		
		final Date dDueDate=PosDateUtil.parse(  deliveryDate);
		final Date posDate =PosDateUtil.parse(mPosOrderHObject.getOrderDate());//   PosEnvSettings.getInstance().getPosDate());
		if (dDueDate.before(posDate)){
			PosFormUtil.showErrorMessageBox(this, "Delivery date should not be less than the current date.");
			valid = false;
			mTxtDay.requestFocus();
			mTxtDay.setSelectedAll();
		}
		 
		return valid;
	} 

	/**
	 * 
	 */
	private void createPrintButton() {
		
		mButtonPrint = new PosButton();
		mButtonPrint.setText("Print");
		mButtonPrint.setImage(PosResUtil
				.getImageIconFromResource(IMAGE_BUTTON_OK));
		mButtonPrint.setTouchedImage(PosResUtil
				.getImageIconFromResource(IMAGE_BUTTON_OK_TOUCH));
		mButtonPrint.setHorizontalAlignment(SwingConstants.CENTER);
		mButtonPrint.setSize(IMAGE_BUTTON_WIDTH, IMAGE_BUTTON_HEIGHT);
		mButtonPrint.setOnClickListner(new IPosButtonListner() {

			@Override
			public void onClicked(PosButton button) {
				try {
					
					if(validateInputs()){
					
						updateSalesOrderDet(mPosOrderHObject);
						mParent.doSaveOrder(false);
						PosFormUtil.showSalesOrderPrintConfirmMessage(PosSalesOrderInfoForm.this, true, mPosOrderHObject, false);
						
					}
				} catch (Exception e) {
					PosFormUtil
					.showErrorMessageBox(PosSalesOrderInfoForm.this,
							"Failed to print Sales Order. Please check the log for details.");
					PosLog.write(this, "printSalesOrder", e);
				}
				 
			}
		});
		if(!mPosOrderHObject.isNewOrder() && mPosOrderHObject.getOrderServiceType()==PosOrderServiceTypes.SALES_ORDER)
			addButtonsToBottomPanel(mButtonPrint, 2);
		 
	}
}
