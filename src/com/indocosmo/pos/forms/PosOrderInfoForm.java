/**
 * 
 */
package com.indocosmo.pos.forms;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.RootPaneContainer;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import com.indocosmo.pos.common.PosEnvSettings;
import com.indocosmo.pos.common.PosLog;
import com.indocosmo.pos.common.beans.settings.ui.orderentry.BeanUIOrderEntrySetting;
import com.indocosmo.pos.common.enums.PosOrderServiceTypes;
import com.indocosmo.pos.common.enums.PosPaymentOption;
import com.indocosmo.pos.common.enums.PosTerminalServiceType;
import com.indocosmo.pos.common.utilities.PosAccessPermissionsUtil;
import com.indocosmo.pos.common.utilities.PosCurrencyUtil;
import com.indocosmo.pos.common.utilities.PosFormUtil;
import com.indocosmo.pos.common.utilities.PosNumberUtil;
import com.indocosmo.pos.common.utilities.PosOrderUtil;
import com.indocosmo.pos.common.utilities.PosResUtil;
import com.indocosmo.pos.common.utilities.PosSaleItemUtil;
import com.indocosmo.pos.common.utilities.PosUomUtil;
import com.indocosmo.pos.data.beans.BeanCustomer;
import com.indocosmo.pos.data.beans.BeanEmployees;
import com.indocosmo.pos.data.beans.BeanOrderCustomer;
import com.indocosmo.pos.data.beans.BeanOrderDetail;
import com.indocosmo.pos.data.beans.BeanOrderHeader;
import com.indocosmo.pos.data.beans.BeanOrderServingTable;
import com.indocosmo.pos.data.beans.BeanPayModeBase.PaymentMode;
import com.indocosmo.pos.data.beans.BeanSaleItem;
import com.indocosmo.pos.data.beans.BeanServingTable;
import com.indocosmo.pos.data.providers.shopdb.PosCompanyItemProvider;
import com.indocosmo.pos.data.providers.shopdb.PosCustomerProvider;
import com.indocosmo.pos.data.providers.shopdb.PosDiscountItemProvider;
import com.indocosmo.pos.data.providers.shopdb.PosServiceTableProvider;
import com.indocosmo.pos.data.providers.shopdb.PosUOMProvider;
import com.indocosmo.pos.data.providers.shopdb.PosWaiterProvider;
import com.indocosmo.pos.forms.PosObjectBrowserForm.ItemSize;
import com.indocosmo.pos.forms.components.PosOrderInfoDetTablePanel;
import com.indocosmo.pos.forms.components.buttons.PosButton;
import com.indocosmo.pos.forms.components.buttons.PosButton.ButtonStyle;
import com.indocosmo.pos.forms.components.buttons.listners.IPosButtonListner;
import com.indocosmo.pos.forms.components.objectbrowser.IPosBrowsableItem;
import com.indocosmo.pos.forms.components.search.IPosSearchableItem;
import com.indocosmo.pos.forms.components.textfields.PosItemBrowsableField;
import com.indocosmo.pos.forms.components.textfields.PosItemSearchableField;
import com.indocosmo.pos.forms.components.textfields.PosTouchableDigitalField;
import com.indocosmo.pos.forms.components.textfields.PosTouchableFieldBase;
import com.indocosmo.pos.forms.components.textfields.listners.IPosTouchableFieldListner;
import com.indocosmo.pos.forms.components.textfields.listners.PosTouchableFieldAdapter;
import com.indocosmo.pos.forms.listners.IPosObjectBrowserListner;
import com.indocosmo.pos.forms.listners.IPosOrderInfoFormListner;
import com.indocosmo.pos.forms.listners.IPosPaymentMetodsFormListner;
import com.indocosmo.pos.forms.listners.IPosSelectCustomerFormListner;
import com.indocosmo.pos.forms.messageboxes.PosMessageBoxForm.MessageBoxButtonTypes;
import com.indocosmo.pos.forms.messageboxes.PosMessageBoxForm.MessageBoxResults;
import com.indocosmo.pos.forms.restaurant.tablelayout.PosServiceTableSelectionForm;
import com.indocosmo.pos.terminal.devices.PosDeviceManager;
import com.indocosmo.pos.terminal.devices.printer.PosDeviceKitchenPrinter;

/**
 * @author jojesh
 * 
 */
@SuppressWarnings("serial")
public final class PosOrderInfoForm extends PosBaseForm {

	protected static final int MAIN_PANEL_CONTENT_H_GAP = 1;
	protected static final int MAIN_PANEL_CONTENT_V_GAP = 1;
	
	protected static final int PANEL_CONTENT_H_GAP = 2;
	protected static final int PANEL_CONTENT_V_GAP = 2;
	
	private static final int ITEM_PANEL_H_GAP = 1;

	private static final int ITEM_ROWS = 5;
	private static final int ITEM_COLS = 2;

	private static final int ITEM_TITEL_WIDTH =150;
	private static final int ITEM_TEXT_WIDTH = 320;
	private static final int ITEM_HEIGHT = PosTouchableFieldBase.LAYOUT_DEF_HEIGHT;

	private static final int  BROWSE_BUTTON_WIDTH=50;
	
	private static final int ITEM_PANEL_HIEGHT = ITEM_HEIGHT;
	private static final int ITEM_PANEL_WIDTH = (ITEM_TITEL_WIDTH + ITEM_TEXT_WIDTH)
			+ PANEL_CONTENT_H_GAP * 3;
	private static final int DTL_LIST_PANEL_HIEGHT = 352;
	
	private static final int LAYOUT_HEIGHT = ITEM_PANEL_HIEGHT * ITEM_ROWS
			+ PANEL_CONTENT_V_GAP * (ITEM_ROWS + 1) + PANEL_BORDER_WIDTH * 2;
	private static final int LAYOUT_WIDTH = ITEM_PANEL_WIDTH * ITEM_COLS
			+ PANEL_CONTENT_H_GAP * (ITEM_COLS + 1) + PANEL_BORDER_WIDTH * 2;
	
	private static final String IMAGE_BUTTON_PAYMENT="dlg_payment.png";
	private static final String IMAGE_BUTTON_PAYMENT_TOUCH="dlg_payment_touch.png";
	private static final String IMAGE_BUTTON_PARK="dlg_info_park_btn.png";
	private static final String IMAGE_BUTTON_PARK_TOUCH="dlg_info_park_btn_touch.png";

	private JTextField mTextOrderNumber;
	private JTextField mTextOrderDateTime;
	private JTextField mTextTotalItems;
	private JTextField mTextTotalAmount;
	private JTextField mTextDueAmount;

	private JTextField mTextCustomer;
	private PosItemBrowsableField mFieldServedBy;
	private PosItemBrowsableField mFieldService;
	//	private PosItemBrowsableField mFieldTable;
	private JTextField mFieldTable;
	private PosTouchableDigitalField mFieldCovers;
	private JPanel mServiceTablePanel;
	private JPanel mTableCoversPanel;
	private JPanel mServicePanel;
	private JPanel mServedByPanel;
	
	private JPanel mHdrPanel;

	private PosCustomerProvider mPosCustomerProvider;
	private PosCompanyItemProvider mPosCompanyProvider;
	private PosWaiterProvider mPosWaiterProvider;
	private PosServiceTableProvider mServiceTableProvider;

	private ArrayList<BeanCustomer> mPosCustomerList;
	private ArrayList<BeanEmployees> mPosWaiterList;

	private BeanOrderHeader mPosOrderHObject;

	private PosOrderServiceTypes mSelectedService=PosOrderServiceTypes.TAKE_AWAY;
	private BeanCustomer mSelectedCustomer;
	private BeanOrderCustomer mOrderCustomer;
	private BeanServingTable mSelectedTable;
	private IPosSearchableItem mSelectedUser;
	private String mOriginalRemarks;
	private int mSelectedCovers;

	private JPanel mContentPanel;

	private boolean isDirty = false;

	private int mServedByCount = 0;
	private int mTableCount = 0;
	private PosOrderEntryForm mParent;
	private Map<String, BeanOrderServingTable> mOrderServiceTables;

	private PosOrderInfoDetTablePanel orderDetTablePanel;
	private IPosOrderInfoFormListner mOrderInfoListner;

	/**
	 * @param title
	 * @param cPanelwidth
	 * @param cPanelHeight
	 */
	public PosOrderInfoForm(PosOrderEntryForm parent,BeanOrderHeader orderHObject) {
	
		super("Order Details", LAYOUT_WIDTH, LAYOUT_HEIGHT+DTL_LIST_PANEL_HIEGHT + PANEL_CONTENT_V_GAP*3);
		mPosOrderHObject = orderHObject;
		mParent = parent;
		try {
			
			mPosCustomerProvider = new PosCustomerProvider();
			mPosCompanyProvider=new PosCompanyItemProvider();
			mPosWaiterProvider = new PosWaiterProvider();
			mServiceTableProvider = new PosServiceTableProvider();

			mPosCustomerList = new ArrayList<BeanCustomer>();
			if(mPosCustomerProvider.getItemList()!=null)
				mPosCustomerList.addAll(mPosCustomerProvider.getItemList());
			if(mPosCompanyProvider.getItemList()!=null)
				mPosCustomerList.addAll(mPosCompanyProvider.getItemList());
			mPosWaiterList = mPosWaiterProvider.getWaiterList();
			mServedByCount = PosOrderUtil.getCountOfServedBy(orderHObject);
			mTableCount = PosOrderUtil.getCountOfTables(orderHObject);

			createUI();
			
			loadUI();
			
			setVars();
		
//			if (PosEnvSettings.getInstance().getStation().getServiceType() == PosTerminalServiceType.RWS){
//				
//				mServicePanel.setVisible(false) ;
//				mServedByPanel.setVisible(false);
//			}
				
			
		} catch (Exception e) {
			PosLog.write(this, "PosOrderInfoForm", e);
			PosFormUtil.showSystemError(this);
		}
		
		setResetButtonVisible(true);
		mOriginalRemarks = mPosOrderHObject.getRemarks();
	}

	/**
	 * @throws CloneNotSupportedException 
	 * 
	 */
	private void setVars() throws CloneNotSupportedException {

		mOrderServiceTables=new HashMap<String, BeanOrderServingTable>();
		for(String key:mPosOrderHObject.getOrderTableList().keySet()){
			
			mOrderServiceTables.put(key,(BeanOrderServingTable)mPosOrderHObject.getOrderTableList().get(key).clone());
		}
		
		mSelectedTable=mPosOrderHObject.getServiceTable();
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

	
	private void createDetailTableUI(){
		
		int orderDtlTableHeight=DTL_LIST_PANEL_HIEGHT-PANEL_CONTENT_V_GAP *2;
		orderDetTablePanel  =new PosOrderInfoDetTablePanel(mContentPanel ,LAYOUT_WIDTH-PANEL_CONTENT_H_GAP*4,orderDtlTableHeight  );
		orderDetTablePanel.SetOrderDetailList(mPosOrderHObject.getOrderDetailItems() );
		mContentPanel.add(orderDetTablePanel);

	}
	/***
	 * 
	 */
	private void createUI() {
		
		mHdrPanel =new JPanel();
		mHdrPanel.setLayout(new FlowLayout(FlowLayout.LEFT,PANEL_CONTENT_H_GAP,PANEL_CONTENT_V_GAP));
		mHdrPanel.setPreferredSize(new Dimension(LAYOUT_WIDTH-PANEL_CONTENT_H_GAP*3, LAYOUT_HEIGHT));
		mContentPanel.add( mHdrPanel);
		
		createDetailTableUI();
		
		mTextOrderNumber = crateField(mHdrPanel, "Number :");
		mTextOrderDateTime = crateField(mHdrPanel, "Order Date :");
		mTextTotalAmount = crateField(mHdrPanel, "Total Amount :");
		mTextDueAmount = createDueAmtField(mHdrPanel, "Due Amount :");
		mTextTotalItems = crateField(mHdrPanel, "Quantity :");
		mTextCustomer = createCustomerField(mHdrPanel);
		mFieldService = createServiceField(mHdrPanel);
		mFieldServedBy = createServedByField(mHdrPanel);
		
		mFieldTable = createTableField(mHdrPanel);
		mFieldCovers = createCoversField(mHdrPanel);
		mFieldCovers.setTextReadOnly(true);
		
		createButtons(mHdrPanel);

	}

	/**
	 * 
	 */
	private IPosButtonListner parkButtonListner =new IPosButtonListner() {
		
		@Override
		public void onClicked(PosButton button) {

			if(mOrderInfoListner!=null){
				
				MessageBoxResults result=MessageBoxResults.Yes;
						
				if (isDirty){
					result=PosAccessPermissionsUtil.checkEditAuthenticationOfBilledOrder(
							(RootPaneContainer)PosOrderInfoForm.this, mPosOrderHObject,true);
				}
				
				if(result==MessageBoxResults.Yes){
					if(updatePosOrder()){
						closeWindow();
						mOrderInfoListner.onItemSaved(mParent , mPosOrderHObject);
					}
				}	
					
//				}else
//					closeWindow();
				 
			}
 		}
	};
	
	
	/**
	 * 
	 */
	private IPosButtonListner paymentButtonListner =new IPosButtonListner() {
		
		@Override
		public void onClicked(PosButton button) {
			if(mOrderInfoListner!=null){
			 		
				if (isDirty) 
					if (!updatePosOrder()) return;
				
				
				
				
				PosFormUtil.showPaymentOptions(PosOrderInfoForm.this, payMethodListner,mPosOrderHObject);
				
//				final  BeanUIOrderEntrySetting uiOrderEtrySettings=
//						  PosEnvSettings.getInstance().getUISetting().getOrderEntryUISettings();
//				
				
				
				
//				if(!PosEnvSettings.getInstance().getUISetting().getOrderEntryUISettings().getPaymentPanelSetting().isSplitPayButtonVisibile()){
//					
//					payMethodListner.onClick(PosPaymentOption.STANDARD );
//					
//				}else {
//					
//					switch (uiOrderEtrySettings.getPaymentOption()){
//					
//					case STANDARD:
//						payMethodListner.onClick(PosPaymentOption.STANDARD );
//						break;
//					case SPLIT:
//						payMethodListner.onClick(PosPaymentOption.SPLIT);
//						break;
//					case ASK:
//						showPaymentOptions();
//				} 
			}
				
		 
		}
	};
	
//	/*
//	 *  
//	 * 
//	 */
//	private void  showPaymentOptions(){
//		 
//			
//		PosObjectBrowserForm form=new PosObjectBrowserForm("Payment Options", PosPaymentOption.values(),ItemSize.Wider,1,2);
//		form.setListner(new IPosObjectBrowserListner() {
//			
//			@Override
//			public void onItemSelected(IPosBrowsableItem item) {
//				
//				payMethodListner.onClick((PosPaymentOption)item);
//			}
//			
//			@Override
//			public void onCancel() {
//				// TODO Auto-generated method stub
//				
//			}
//		});
//		PosFormUtil.showLightBoxModal(this,form);
// 
//	}
	IPosPaymentMetodsFormListner payMethodListner=new IPosPaymentMetodsFormListner() {
		
		@Override
		public void onSelected(PaymentMode paymentMode) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onClick(PosPaymentOption payOption) {
			closeWindow();
			mOrderInfoListner.onPaymentButtonClicked(mParent,payOption, mPosOrderHObject);
		}
	};
	 
	 
	/*
	 * 		
	 */
	public void setListner(IPosOrderInfoFormListner orderInfoListner){
		mOrderInfoListner=orderInfoListner;
	}
	/**
	 * @param mContentPanel2
	 * @return
	 */
	private PosTouchableDigitalField createCoversField(JPanel panel) {
		mTableCoversPanel = creatFieldPanelWithTitle(PosFormUtil.getMnemonicString("Covers :", 'o'));
		panel.add(mTableCoversPanel);

		PosTouchableDigitalField field = new PosTouchableDigitalField(this,
				ITEM_TEXT_WIDTH);
		field.setHorizontalTextAlignment(SwingConstants.LEFT);
		field.setFont(PosFormUtil.getTextFieldBoldFont());
		field.setTitle("Covers");
		field.setMnemonic('o');
		field.hideResetButton(true);
		field.setListner(new IPosTouchableFieldListner() {

			@Override
			public void onValueSelected(Object value) {
				mSelectedCovers = Integer.valueOf(value.toString());
				isDirty = true;
			}

			@Override
			public void onReset() {
				// TODO Auto-generated method stub

			}
		});

		mTableCoversPanel.add(field);
		return field;
	}

		/***
	 * 
	 * @param panel
	 * @return
	 */
	private PosItemBrowsableField createServedByField(JPanel panel) {

		  mServedByPanel = creatFieldPanelWithTitle(PosFormUtil.getMnemonicString("Server By :", 'B'));
		panel.add(mServedByPanel);


		int columnCount=3;
		int rowCount=3;
		
		if (mPosWaiterList.size()>=20) {
			columnCount=5;
			rowCount=5;
		}else if (mPosWaiterList.size()>=16) {
			columnCount=5;
			rowCount=4;
		}else if (mPosWaiterList.size()>=12) {
			columnCount=4;
			rowCount=4;
		}else if (mPosWaiterList.size()>=9) {
			columnCount=4;
			rowCount=3;
		}
		
		final PosItemBrowsableField field = new PosItemBrowsableField(this,
				ITEM_TEXT_WIDTH);
		field.setBrowseItemList(mPosWaiterList);
		field.setMnemonic('B');
		field.setBrowseWindowSize(columnCount, rowCount);
		field.setHorizontalTextAlignment(SwingConstants.LEFT);
		field.setFont(PosFormUtil.getTextFieldBoldFont());
		field.setTitle("Served By");
		field.hideResetButton(true);
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
				super.onReset();
				mSelectedUser = null;
				isDirty = true;
			}
		});

		mServedByPanel.add(field);
		return field;
	}

	/***
	 * 
	 * @param panel
	 * @return
	 */
	private PosItemBrowsableField createServiceField(JPanel panel) {

		mServicePanel = creatFieldPanelWithTitle(PosFormUtil.getMnemonicString("Service :", 'S'));
		panel.add(mServicePanel);

		final PosItemBrowsableField field = new PosItemBrowsableField(this,
				ITEM_TEXT_WIDTH);
		
		ArrayList<IPosBrowsableItem> orderServiceTypes =new ArrayList<IPosBrowsableItem>();
		for(PosOrderServiceTypes serviceType: PosOrderServiceTypes.values()){
			if(serviceType.isVisibleInUI() && serviceType!=PosOrderServiceTypes.SALES_ORDER){
				orderServiceTypes.add(serviceType);
			}
		}
		field.setBrowseItemList(orderServiceTypes);
		field.setTitle("Services");
		field.setMnemonic('S');
		field.hideResetButton(true);
		field.setFont(PosFormUtil.getTextFieldFont());
		field.setListner(new PosTouchableFieldAdapter() {

			@Override
			public void onValueSelected(Object value) {
				if (mSelectedService == (PosOrderServiceTypes) value)
					return;
				mSelectedService = (PosOrderServiceTypes) value;
				mFieldTable.setText("");
				mFieldServedBy.reset();
				try {
					switch (mSelectedService) {
					case HOME_DELIVERY:
						mSelectedTable = mServiceTableProvider
						.getTableByServiceType(mSelectedService);
						break;
					case TABLE_SERVICE:
						mSelectedTable = null;
						break;
					case TAKE_AWAY:
					case WHOLE_SALE:
						mSelectedTable = mServiceTableProvider
						.getTableByServiceType(mSelectedService);
						mSelectedUser = mPosOrderHObject.getUser();
						break;
					default:
						break;
					}
					setupUI();
				} catch (Exception e) {
					
					PosLog.write(this, "createServiceField.onValueSelected", e);
					PosFormUtil.showSystemError(PosOrderInfoForm.this);
				}
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
				mSelectedService = null;
				isDirty = true;
			}
		});
		mServicePanel.add(field);
		return field;
	}

	/***
	 * 
	 * @param panel
	 * @return
	 */
	private JTextField createTableField(JPanel panel) {

		final int btnHeight=PosTouchableFieldBase.LAYOUT_DEF_HEIGHT;
		final int btnWidth=50;
		mServiceTablePanel = creatFieldPanelWithTitle(PosFormUtil.getMnemonicString("Table :", 'T'));
		panel.add(mServiceTablePanel);

		final JTextField field = new JTextField();
		field.setPreferredSize(new Dimension(ITEM_TEXT_WIDTH-btnWidth-ITEM_PANEL_H_GAP*2,btnHeight));
		field.setEditable(false);
		field.setFont(PosFormUtil.getTextFieldFont());
		mServiceTablePanel.add(field);

		final PosButton btnSelectTable=new PosButton();
		btnSelectTable.setText("...");
		btnSelectTable.setMnemonic('T');
		btnSelectTable.setPreferredSize(new Dimension(btnWidth,btnHeight));
		btnSelectTable.setButtonStyle(ButtonStyle.IMAGE);
		btnSelectTable.setBackgroundColor(Color.PINK, Color.PINK.darker());
		btnSelectTable.setBorderPainted(true);
		btnSelectTable.setImage(PosTouchableFieldBase.CLICK_BUTTON_NORMAL, PosTouchableFieldBase.CLICK_BUTTON_TOUCHED);
		btnSelectTable.setOnClickListner(new IPosButtonListner() {

			@Override
			public void onClicked(PosButton button) {

				try {

					showRestaurantOrderForm();
				} catch (Exception e) {
					
					PosLog.write(this, "createTableField.onClicked", e);
					PosFormUtil.showSystemError(PosOrderInfoForm.this);
				}
			}
		});
		mServiceTablePanel.add(btnSelectTable);

		return field;
	}

	private boolean showRestaurantOrderForm() throws Exception {

		boolean isCancelled = true;
		final PosServiceTableSelectionForm form = new PosServiceTableSelectionForm(true);
		form.setOrderTables(mOrderServiceTables);
		form.setSelectedTableLocation((mSelectedTable!=null)?mSelectedTable.getLocation().getId():-1);
		form.setSelectedTable((mSelectedTable!=null)?mSelectedTable.getCode():null); 
		form.setOrderHeader(mPosOrderHObject);
		if(mSelectedUser!=null)
			form.setSelectedWaiter(((BeanEmployees)mSelectedUser).getCardNumber());
		PosFormUtil.showLightBoxModal(this, form);

		if (!form.isCancelled()) {

			this.mOrderServiceTables=PosOrderUtil.updateOrderTables(mOrderServiceTables,form.getOrderTables());

			final String selTableCode=form.getSelectedServiceTableCode();

			if(selTableCode!=null && mOrderServiceTables.containsKey(selTableCode)){
				mSelectedTable = mOrderServiceTables.get(selTableCode);
				mFieldTable.setText(mSelectedTable.getCode());
			}
			isDirty = true;
			isCancelled = false;
		}
				
		form.dispose();
		return isCancelled;
	}

	/***
	 * 
	 * @param panel
	 */
	private void createButtons(JPanel panel) {

		final PosButton buttonRemarks = this.addButtonsToBottomPanel("Remarks",null,0);
		buttonRemarks.setMnemonic('m');
		buttonRemarks.setOnClickListner(new IPosButtonListner() {

			@Override
			public void onClicked(PosButton button) {
				PosOrderRemarksEntryForm remarks = new PosOrderRemarksEntryForm();
				remarks.setOrderHeaderItem(mPosOrderHObject);
				PosFormUtil.showLightBoxModal(PosOrderInfoForm.this,remarks);
				if (remarks.IsEdited()) {
					isDirty = true;
				}
			}
		});
		buttonRemarks.setEnabled(!mParent.IsPartiallyPaidOrder());

		final PosButton btnPark= addButtonsToBottomPanel("Park", parkButtonListner,1);
		btnPark.setImage(IMAGE_BUTTON_PARK, IMAGE_BUTTON_PARK_TOUCH);
		
		if(PosEnvSettings.getInstance().getUISetting().getOrderEntryUISettings().getOrderInfoFormSetting().isPaymentButtonVisible()){
		
			final PosButton btnPayment= addButtonsToBottomPanel("Payment", paymentButtonListner,1);
			btnPayment.setMnemonic('y');
			btnPayment.setImage(IMAGE_BUTTON_PAYMENT, IMAGE_BUTTON_PAYMENT_TOUCH);
		}

	}

	/***
	 * 
	 * @param title
	 * @return
	 */
	private JPanel creatFieldPanelWithTitle(String title) {
		JPanel itemPanel = new JPanel();
		itemPanel
		.setLayout(new FlowLayout(FlowLayout.LEFT, ITEM_PANEL_H_GAP, 0));
		itemPanel.setPreferredSize(new Dimension(ITEM_PANEL_WIDTH,
				ITEM_PANEL_HIEGHT));

		JLabel lable = new JLabel(title);
		lable.setPreferredSize(new Dimension(ITEM_TITEL_WIDTH, ITEM_HEIGHT));
		lable.setBorder(new EmptyBorder(2, 2, 2, 2));
		lable.setFont(PosFormUtil.getLabelFont());
		lable.setOpaque(true);
		lable.setBackground(Color.LIGHT_GRAY);
		itemPanel.add(lable);

		return itemPanel;
	}

	/**
	 * 
	 * @param panel
	 * @param title
	 * @return
	 */
	private JTextField crateField(JPanel panel, String title) {
		JPanel itemPanel = creatFieldPanelWithTitle(title);
		panel.add(itemPanel);

		JTextField text = new JTextField("");
		text.setPreferredSize(new Dimension(ITEM_TEXT_WIDTH, ITEM_HEIGHT));
		text.setFont(PosFormUtil.getLabelFont());
		text.setEditable(false);
		itemPanel.add(text);

		return text;
	}

	/**
	 * 
	 * @param panel
	 * @param title
	 * @return
	 */
	private JTextField createDueAmtField(JPanel panel, String title) {
		
		JPanel itemPanel = creatFieldPanelWithTitle(title);
		panel.add(itemPanel);

		JTextField text = new JTextField("");
		text.setPreferredSize(new Dimension(ITEM_TEXT_WIDTH-BROWSE_BUTTON_WIDTH , ITEM_HEIGHT));
		text.setFont(PosFormUtil.getLabelFont());
		text.setEditable(false);
		itemPanel.add(text);

		final PosButton btnRetrieve=new PosButton("...");
		btnRetrieve.setMnemonic('e');
		btnRetrieve.setPreferredSize(new Dimension(BROWSE_BUTTON_WIDTH ,ITEM_HEIGHT));
		btnRetrieve.setButtonStyle(ButtonStyle.IMAGE);
		btnRetrieve.setImage("cashout_search.png", "cashout_search_touch.png");
		btnRetrieve.setOnClickListner(new IPosButtonListner() {
			
			@Override
			public void onClicked(PosButton button) {
			
				try {
					PosOrderBillAmountInfoForm form=new PosOrderBillAmountInfoForm(mPosOrderHObject);
					PosFormUtil.showLightBoxModal(PosOrderInfoForm.this, form);
					 
				} catch (Exception e) {
					
					PosLog.write(PosOrderInfoForm.this, "btnRetrieve.onClicked", e);
					PosFormUtil.showErrorMessageBox(PosOrderInfoForm.this, "Failed to load details. Please check log.");
				}
				
			}
		});
		itemPanel.add(btnRetrieve);
		return text;
	}
	
	 
	
	/**
	 * 
	 * @param panel
	 * @param title
	 * @return
	 */
 
	private JTextField createCustomerField(JPanel panel) {
		
		JPanel itemPanel = creatFieldPanelWithTitle(PosFormUtil.getMnemonicString("Customer :", 'u'));
		panel.add(itemPanel);
		 
		JTextField text = new JTextField("");
		text.setPreferredSize(new Dimension(ITEM_TEXT_WIDTH-BROWSE_BUTTON_WIDTH , ITEM_HEIGHT));
		text.setFont(PosFormUtil.getLabelFont());
		text.setEditable(false);
		itemPanel.add(text);

		final PosButton btnCustomerRetrieve=new PosButton("...");
		btnCustomerRetrieve.setMnemonic('u');
		btnCustomerRetrieve.setPreferredSize(new Dimension(BROWSE_BUTTON_WIDTH ,ITEM_HEIGHT));
		btnCustomerRetrieve.setButtonStyle(ButtonStyle.IMAGE);
		btnCustomerRetrieve.setImage("cashout_search.png", "cashout_search_touch.png");
		btnCustomerRetrieve.setOnClickListner(new IPosButtonListner() {
			
			@Override
			public void onClicked(PosButton button) {
			
				onCustomerInfoClicked();
				
			}
		});
		itemPanel.add(btnCustomerRetrieve);
		return text;
	}
	
	

	/**
	 * @param custCardNo
	 */
	private void onCustomerInfoClicked() {

		if (PosAccessPermissionsUtil.validateAccess(this, PosEnvSettings
				.getInstance().getCashierShiftInfo().getCashierInfo()
				.getUserGroupId(), "change_customer") ) {
			PosOrderCustomerForm custForm = new PosOrderCustomerForm(mParent,mSelectedCustomer, mOrderCustomer);
			custForm.setListner(new IPosSelectCustomerFormListner() {
				
				@Override
				public void onOkClicked(BeanCustomer posCustomerItem,
						BeanOrderCustomer orderCustomer) {
					mSelectedCustomer = posCustomerItem;
					mOrderCustomer=orderCustomer;
					mTextCustomer.setText(mOrderCustomer.getName());
					isDirty = true;
					// TODO Auto-generated method stub
					
				}
			});
			PosFormUtil.showLightBoxModal(this, custForm);
		}
	}
	/***
	 * popualte the data from object
	 */
	private void loadUI() {

		
		mTextOrderNumber.setText(PosOrderUtil
				.getFormattedReferenceNo(mPosOrderHObject ));
		mTextOrderDateTime.setText(mPosOrderHObject.getOrderTime());
		mTextTotalItems.setText(PosUomUtil.format(mPosOrderHObject.getDetailQuatity(),PosUOMProvider.getInstance().getMaxDecUom()));
//		final double totalItemAmount = PosOrderUtil
//				.getTotalItemAmount(mPosOrderHObject);
		mTextTotalAmount.setText(PosCurrencyUtil.format(PosCurrencyUtil
				.roundTo(PosOrderUtil.getTotalAmount(mPosOrderHObject))));
		 

		final double totalAmtPaid= mPosOrderHObject.getTotalAmountPaid() -
				(mPosOrderHObject.getChangeAmount()+mPosOrderHObject.getCashOut())-
				mPosOrderHObject.getRoundAdjustmentAmount()  ;
		final double totalDiscount=PosOrderUtil.getBillDiscount(mPosOrderHObject);
	
 		final double  dueAmount=  PosOrderUtil.getTotalAmount(mPosOrderHObject)-(totalAmtPaid+totalDiscount);
		 mTextDueAmount
		.setText(PosCurrencyUtil.format(dueAmount));
		mSelectedCustomer = mPosOrderHObject.getCustomer();
		mOrderCustomer=mPosOrderHObject.getOrderCustomer();
		mSelectedService = mPosOrderHObject.getOrderServiceType();
		mSelectedTable = mPosOrderHObject.getServiceTable();
		mSelectedCovers = (mSelectedService == PosOrderServiceTypes.TABLE_SERVICE) ?mPosOrderHObject.getCovers():0;
		mSelectedUser = (mSelectedService == PosOrderServiceTypes.TAKE_AWAY   ) ? 
				mPosOrderHObject.getUser() : mPosOrderHObject.getServedBy();
		setupUI();
		isDirty = false;
	}

	/***
	 * set the control with data
	 */
	private void setupUI() {
		
		mTextCustomer.setText(mOrderCustomer.getName());
		mFieldServedBy.setSelectedItem(mSelectedUser);
		mFieldCovers.setSelectedValue(String.valueOf(mSelectedCovers));
		mFieldService.setSelectedItem(mSelectedService);

		mFieldTable.setText((mSelectedTable!=null)?mSelectedTable.getCode():"");
		
		mFieldTable.setEnabled(false);
//		mFieldCovers.setEnabled(false);
		mFieldServedBy.setEnabled(false);

		int orderDtlTableHeight=DTL_LIST_PANEL_HIEGHT-PANEL_CONTENT_V_GAP *2;
		int hdrPanelHeight=LAYOUT_HEIGHT;
		
		switch (mSelectedService) {
		case HOME_DELIVERY:
			mServiceTablePanel.setVisible(false);
			mTableCoversPanel.setVisible(false);
			mFieldServedBy.setEnabled(true);
			mFieldTable.setEnabled(true);
//			mFieldCovers.setEnabled(true);
			orderDtlTableHeight+=ITEM_HEIGHT ;
			hdrPanelHeight -=ITEM_HEIGHT;
			break;
		case TABLE_SERVICE:
			mServiceTablePanel.setVisible(true);
			mTableCoversPanel.setVisible(true);
			mFieldServedBy.setEnabled(mServedByCount <= 1);
			mFieldTable.setEnabled(mTableCount <= 1);
//			mFieldCovers.setEnabled(mTableCount <= 1);
			break;
		case TAKE_AWAY:
		case WHOLE_SALE:
			mServiceTablePanel.setVisible(false);
			mTableCoversPanel.setVisible(false);
			mFieldServedBy.setEnabled(false);
			
			orderDtlTableHeight+=ITEM_HEIGHT ;
			hdrPanelHeight -=ITEM_HEIGHT;
			break;
		default:
			break;
		}
		
//		if (PosEnvSettings.getInstance().getStation().getServiceType() == PosTerminalServiceType.RWS){
//			orderDtlTableHeight+=ITEM_HEIGHT  ;
//			hdrPanelHeight -=ITEM_HEIGHT ;
//		}
	
		mHdrPanel.setPreferredSize(new Dimension(LAYOUT_WIDTH-PANEL_CONTENT_H_GAP*3,  hdrPanelHeight));
		orderDetTablePanel.resizePanel(new Dimension(LAYOUT_WIDTH-PANEL_CONTENT_H_GAP*4,orderDtlTableHeight));
		
		if(mPosOrderHObject.hasPartialPayments()){
			mTextCustomer.setEnabled(false);
			mFieldService.setEnabled(false);
			mFieldServedBy.setEnabled(false);
			mFieldTable.setEnabled(false);
//		mFieldCovers.setEnabled(false);
		}
		
		mHdrPanel.invalidate();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indocosmo.pos.forms.PosBaseForm#onResetButtonClicked()
	 */
	@Override
	public void onResetButtonClicked() {
		super.onResetButtonClicked();
		loadUI();
		mPosOrderHObject.setRemarks(mOriginalRemarks);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indocosmo.pos.forms.PosBaseForm#onCancelButtonClicked()
	 */
	@Override
	public boolean onCancelButtonClicked() {
		mPosOrderHObject.setRemarks(mOriginalRemarks);
		isDirty=false;
		return super.onCancelButtonClicked();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indocosmo.pos.forms.PosBaseForm#onOkButtonClicked()
	 */
	@Override
	public boolean onOkButtonClicked() {
		boolean isCancelled = false;
		if (isDirty) {
			MessageBoxResults res = PosFormUtil
					.showQuestionMessageBox(
							this,
							MessageBoxButtonTypes.YesNoCancel,
							"The order information has been edited. Do you want to update now?",
							null);

			switch (res) {
			case Yes:
				MessageBoxResults result=PosAccessPermissionsUtil.checkEditAuthenticationOfBilledOrder(
						(RootPaneContainer)PosOrderInfoForm.this, mPosOrderHObject,true);
				if(result==MessageBoxResults.Yes)
					isCancelled = !updatePosOrder();
				else
					isCancelled = true;
				break;
			case No:
				cancelUpdates();
				break;
			case Cancel:
				isCancelled = true;
				break;
			default:
				break;

			}
		}
		if(mOrderInfoListner!=null && !isCancelled){
			mOrderInfoListner.onOkClicked(PosOrderInfoForm.this , mPosOrderHObject);
			
			 
		}
		return !isCancelled;
	}

	private void cancelUpdates() {
		mPosOrderHObject.setRemarks(mOriginalRemarks);
		isDirty = false;
	}

		
	private boolean updatePosOrder() {
		
		if (!isValidated())
			return false;
		
		boolean updateDetails = (mPosOrderHObject.getOrderServiceType() != mSelectedService);
				//|| mSelectedService == PosOrderServiceTypes.TAKE_AWAY
				//|| mSelectedService == PosOrderServiceTypes.HOME_DELIVERY;
		
 		// boolean isServiceChanged =mPosOrderHObject.getOrderServiceType() !=
		// mSelectedService;
		mPosOrderHObject.setOrderServiceType(mSelectedService);
		mPosOrderHObject.setCovers(0);
		if(mSelectedService==PosOrderServiceTypes.TABLE_SERVICE )
			if(mFieldCovers.getText()!=null && mFieldCovers.getText().trim().length()>0)
				mPosOrderHObject.setCovers(Integer.valueOf(mFieldCovers.getText()));
		mPosOrderHObject.setServiceTable(mSelectedTable);
		mPosOrderHObject.setCustomer(mSelectedCustomer);
		mPosOrderHObject.setOrderCustomer(mOrderCustomer);
		final Map<String, BeanOrderServingTable> updatedTablList=PosOrderUtil.updateOrderTables(mPosOrderHObject.getOrderTableList(), mOrderServiceTables);
		mPosOrderHObject.setOrderTableList(updatedTablList);
		BeanEmployees waiter = (mSelectedService != PosOrderServiceTypes.TAKE_AWAY  ) ? (BeanEmployees) mSelectedUser
				: null;
		mPosOrderHObject.setServedBy(waiter);
		
		 if (updateDetails) {
			 for (BeanOrderDetail dtl : mPosOrderHObject.getOrderDetailItems()) {
				 //			if (updateDetails || mTableCount == 1)
				 dtl.setServingTable(mSelectedTable);
				 //			if (updateDetails || mServedByCount == 1)
//				 dtl.setServedBy(waiter);
				 dtl.setServiceType(mSelectedService);
				 try{
				 PosSaleItemUtil.resetSaleItemTax(dtl, mSelectedService);	
				 }catch(Exception ex){
					 //PosLog.write(module, method, e);
				 }
				 
			 }
		 }
		 
	
		return true;
		 
	}

	public boolean isEdited() {
		return isDirty;
	}

	private boolean isValidated() {
		boolean isValid = true;
		String errFiled = "";
		if (mSelectedService == null) {
			isValid = false;
			errFiled = "Service";
		} else if (mSelectedCustomer == null) {
			isValid = false;
			errFiled = "Customer";
		} else {
			switch (mSelectedService) {
			case HOME_DELIVERY:

				break;
			case TABLE_SERVICE:
				if (mSelectedUser == null) {
					isValid = false;
					errFiled = "Served By";
				} else if (mSelectedTable == null) {
					isValid = false;
					errFiled = "Table";
				}
				break;
			case TAKE_AWAY:
				break;
			default:
				break;
			}
		}
		if (!isValid)
			PosFormUtil.showErrorMessageBox(this, errFiled
					+ " field is empty. Please select.");
		return isValid;
	}

}
