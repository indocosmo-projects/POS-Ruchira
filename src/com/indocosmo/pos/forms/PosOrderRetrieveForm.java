package com.indocosmo.pos.forms;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.RootPaneContainer;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;

import com.indocosmo.barcode.reports.BarCodeLabelPrint;
import com.indocosmo.pos.common.PosEnvSettings;
import com.indocosmo.pos.common.PosLog;
import com.indocosmo.pos.common.beans.settings.ui.orderentry.BeanUIOrderRetrieveFormSetting;
import com.indocosmo.pos.common.enums.EnablePrintingOption;
import com.indocosmo.pos.common.enums.PosOrderServiceTypes;
import com.indocosmo.pos.common.enums.PosPaymentOption;
import com.indocosmo.pos.common.utilities.PosAccessPermissionsUtil;
import com.indocosmo.pos.common.utilities.PosCurrencyUtil;
import com.indocosmo.pos.common.utilities.PosDateUtil;
import com.indocosmo.pos.common.utilities.PosFormUtil;
import com.indocosmo.pos.common.utilities.PosOrderUtil;
import com.indocosmo.pos.common.utilities.PosPrintingUtil;
import com.indocosmo.pos.common.utilities.PosResUtil;
import com.indocosmo.pos.data.beans.BeanOrderHeader;
import com.indocosmo.pos.data.beans.BeanOrderHeader.PosOrderStatus;
import com.indocosmo.pos.data.beans.BeanPayModeBase.PaymentMode;
import com.indocosmo.pos.data.providers.shopdb.PosInvoiceProvider;
import com.indocosmo.pos.data.providers.shopdb.PosOrderHdrProvider;
import com.indocosmo.pos.forms.components.PosOrderItemRetrievePanel;
import com.indocosmo.pos.forms.components.buttons.PosButton;
import com.indocosmo.pos.forms.components.buttons.listners.IPosButtonListner;
import com.indocosmo.pos.forms.components.buttons.listners.PosButtonListnerAdapter;
import com.indocosmo.pos.forms.components.keypads.PosSoftKeyPadSingleLineInput;
import com.indocosmo.pos.forms.components.listners.IPosOrderRetriveListner;
import com.indocosmo.pos.forms.components.objectbrowser.IPosBrowsableItem;
import com.indocosmo.pos.forms.components.orderentry.PosOrderListPanel;
import com.indocosmo.pos.forms.listners.IPosBillPrintFormListner;
import com.indocosmo.pos.forms.listners.IPosOrederRetriveFormListner;
import com.indocosmo.pos.forms.listners.IPosPaymentFormListner;
import com.indocosmo.pos.forms.listners.IPosPaymentMetodsFormListner;
import com.indocosmo.pos.forms.listners.adapters.PosBillPrintFormAdapter;
import com.indocosmo.pos.forms.messageboxes.PosMessageBoxForm.MessageBoxButtonTypes;
import com.indocosmo.pos.forms.messageboxes.PosMessageBoxForm.MessageBoxResults;
import com.indocosmo.pos.forms.messageboxes.listners.PosMessageBoxFormListnerAdapter;
import com.indocosmo.pos.forms.split.PosOrderSplitForm;
import com.indocosmo.pos.reports.receipts.PosReceipts;



@SuppressWarnings("serial")
public class PosOrderRetrieveForm extends JDialog {


	private ArrayList<IPosBrowsableItem> moreButtonList;
	 
	private static final int PANEL_CONTENT_H_GAP=PosOrderEntryForm.PANEL_CONTENT_H_GAP;
	private static final int PANEL_CONTENT_V_GAP=PosOrderEntryForm.PANEL_CONTENT_V_GAP;

	private static final int IMAGE_BUTTON_WIDTH=150;
	private static final int IMAGE_BUTTON_HEIGHT=60;

	private static final int QUEUE_CONTAINER_PANEL_WIDTH=PosSoftKeyPadSingleLineInput.LAYOUT_WIDTH+PANEL_CONTENT_H_GAP;

	private static final int ORDER_ITEM_LIST_PANEL_HEIGHT=497; //QUEUE_CONTAINER_PANEL_HEIGHT-PANEL_CONTENT_V_GAP*3;


	private static final int TITLE_PANEL_HEIGHT=60;	


	private static final int BOTTOM_PANEL_HEIGHT=75;

	private static final int FORM_HEIGHT=657;
	private static final int FORM_WIDTH=QUEUE_CONTAINER_PANEL_WIDTH+PANEL_CONTENT_H_GAP*3+PosOrderListPanel.LAYOUT_WIDTH;
	private static final int TITLE_PANEL_WIDTH=FORM_WIDTH-PANEL_CONTENT_H_GAP*2;
	private static final int BOTTOM_PANEL_WIDTH= FORM_WIDTH-PANEL_CONTENT_H_GAP*2;

	private JPanel mContentPane;
	private JPanel mTitlePanel;
	private JPanel mBottomPanel;
	private JLabel mlabelTitle;	

	private PosButton mButtonPayment;
	private PosButton mButtonOk;
	private PosButton mButtonCacel;
	private PosButton mButtonDelete;
	private PosButton mButtonCloseOrder;
	private PosButton mButtonPrint; 
	
	private static final String IMAGE_BUTTON_OK="dlg_ok.png";
	private static final String IMAGE_BUTTON_OK_TOUCH="dlg_ok_touch.png";

	private static final String IMAGE_BUTTON_PAYMENT="dlg_payment.png";
	private static final String IMAGE_BUTTON_PAYMENT_TOUCH="dlg_payment_touch.png";

	private static final String IMAGE_BUTTON_CANCEL="dlg_cancel.png";
	private static final String IMAGE_BUTTON_CANCEL_TOUCH="dlg_cancel_touch.png";

	private static final Color PANEL_BG_COLOR=new Color(78,128,188);
	private static final Color LABEL_FG_COLOR=Color.WHITE;	

	private PosOrderItemRetrievePanel mOrderItemContainer;
	private PosOrderListPanel mItemGridPanel;
	private BeanOrderHeader selectedOrderItem;

	public enum ViewMode{
		ViewOnlyMode,
		EditMode
	}

	private ViewMode mViewMode=ViewMode.ViewOnlyMode;
	private boolean mOpenAsReadOnly=false;

	/**
	 * @param viewMode
	 */
	public PosOrderRetrieveForm(ViewMode viewMode){

		this.mViewMode=viewMode;
		initControls();	
		mOrderItemContainer.showOrderListSearch();
		
	}


	/**
	 * 
	 */
	public PosOrderRetrieveForm(){

		mViewMode=ViewMode.EditMode;
		initControls();	
		
		moreButtonList=PosPrintingUtil.buildPrintOptions();
		mButtonPrint.setEnabled(moreButtonList.size()>0);
		
	}

	/* (non-Javadoc)
	 * @see java.awt.Dialog#setVisible(boolean)
	 */
	@Override
	public void setVisible(boolean b) {

		if(b){
			
			if(selectedOrderItem==null){
				
				mOrderItemContainer.showOrderListSearch();
				super.setVisible(!mOrderItemContainer.isOrderSerachFormCanceld());
			}else
				
				super.setVisible(b);
		}else
			super.setVisible(b);

	}

	/**
	 * 
	 */
	private void initControls(){

			mContentPane = new JPanel();
		mContentPane.setLayout(null);
		mContentPane.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
		setContentPane(mContentPane);


		//		final int form_height=mItemGridPanel.getHeight()+ TITLE_PANEL_HEIGHT+PANEL_CONTENT_V_GAP*3;
		setSize(FORM_WIDTH, FORM_HEIGHT);

		mTitlePanel = new JPanel();
		mTitlePanel.setBounds(PANEL_CONTENT_H_GAP, 
				PANEL_CONTENT_V_GAP, 
				TITLE_PANEL_WIDTH, TITLE_PANEL_HEIGHT);
		mTitlePanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
		mTitlePanel.setBackground(PANEL_BG_COLOR);
		mTitlePanel.setLayout(null);		
		add(mTitlePanel);
		setTitle();

		int top=getHeight() -PANEL_CONTENT_V_GAP - BOTTOM_PANEL_HEIGHT;
		mBottomPanel = new JPanel();
		mBottomPanel.setBounds(PANEL_CONTENT_H_GAP, 
				top, 
				BOTTOM_PANEL_WIDTH, 
				BOTTOM_PANEL_HEIGHT);
		mBottomPanel.setLayout(new FlowLayout());
		mBottomPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
		add(mBottomPanel);
		setBottomPanel();

		top=mTitlePanel.getY()+mTitlePanel.getHeight()+PANEL_CONTENT_V_GAP;
		setOrederItemListPanel();

		setItemGridPanel();

	}	

	/**
	 * 
	 */
	private void setTitle(){

		mlabelTitle=new JLabel();
		mlabelTitle.setText((mViewMode==ViewMode.EditMode)?"Order Retrieve":"Order Details");
		mlabelTitle.setHorizontalAlignment(SwingConstants.CENTER);		
		mlabelTitle.setBounds(0, 0, mTitlePanel.getWidth(), mTitlePanel.getHeight());		
		mlabelTitle.setFont(PosFormUtil.getHeadingFont());
		mlabelTitle.setForeground(LABEL_FG_COLOR);
		mTitlePanel.add(mlabelTitle);		
	}

	/**getBillGrid
	 * 
	 */
	private void setOrederItemListPanel(){

		int top=mTitlePanel.getY()+mTitlePanel.getHeight()+PANEL_CONTENT_V_GAP;
		int height=(mBottomPanel.getY()-PANEL_CONTENT_V_GAP)-top;
		int left=PANEL_CONTENT_H_GAP;		
		int width=QUEUE_CONTAINER_PANEL_WIDTH;

		mOrderItemContainer=new PosOrderItemRetrievePanel(this,left,top,width,height);
		mOrderItemContainer.setListner(new IPosOrderRetriveListner() {

			@Override
			public void onOrderRetrieved(BeanOrderHeader order) {
							
				if(selectedOrderItem!=null && !selectedOrderItem.getOrderId().equals(order.getOrderId()))
					reset();

				selectedOrderItem=order;
				mItemGridPanel.setPosOrderEntryItem(order);
				mButtonOk.setEnabled(true);
				mButtonPayment.setEnabled(true);
				mButtonDelete.setEnabled(canVoid(order));
				mButtonCloseOrder.setEnabled(order.getOrderServiceType()!=PosOrderServiceTypes.SALES_ORDER && PosCurrencyUtil.roundTo(mOrderItemContainer.getDueAmount())<=0 && order.getStatus()==PosOrderStatus.Partial);
				if (moreButtonList.size()>0) mButtonPrint.setEnabled(true);
			}

			@Override
			public void onReset() {
				
				reset();
			}
		});
		add(mOrderItemContainer);
	}

	/**
	 * 
	 */
	private void reset(){
		
		if(selectedOrderItem!=null)
			PosOrderUtil.releaseOrder(selectedOrderItem.getOrderId());

		selectedOrderItem=null;
		mItemGridPanel.setPosOrderEntryItem(null);
		mButtonOk.setEnabled(false);
		mButtonPayment.setEnabled(false);
		mButtonDelete.setEnabled(false);
		mButtonCloseOrder.setEnabled(false);
		mButtonPrint.setEnabled(false);
		
	}
	 

	/**
	 * 
	 */
	private void setBottomPanel(){

		final BeanUIOrderRetrieveFormSetting uiSettings =
				  PosEnvSettings.getInstance().getUISetting().getOrderEntryUISettings().getOrderRetrieveFormSetting();
	
		int left=mBottomPanel.getX()+PANEL_CONTENT_H_GAP*10;
		mButtonOk=new PosButton();		
		mButtonOk.setText("Open");
		mButtonOk.setDefaultButton(true);
		mButtonOk.registerKeyStroke(KeyEvent.VK_ENTER,KeyEvent.CTRL_DOWN_MASK);
		mButtonOk.setImage(PosResUtil.getImageIconFromResource(IMAGE_BUTTON_OK));
		mButtonOk.setTouchedImage(PosResUtil.getImageIconFromResource(IMAGE_BUTTON_OK_TOUCH));	
		mButtonOk.setHorizontalAlignment(SwingConstants.CENTER);		
		mButtonOk.setBounds(left, PANEL_CONTENT_V_GAP*5, IMAGE_BUTTON_WIDTH, IMAGE_BUTTON_HEIGHT );	
		mButtonOk.setOnClickListner(okButtonListner);
		mButtonOk.setEnabled(false);
		if(mViewMode==ViewMode.EditMode)
			mBottomPanel.add(mButtonOk);

		left=mButtonOk.getX()+mButtonOk.getWidth()+PANEL_CONTENT_V_GAP*2;
		mButtonPayment =new PosButton();		
		mButtonPayment.setText("Payment");
		mButtonPayment.setMnemonic('y');
		mButtonPayment.setImage(PosResUtil.getImageIconFromResource(IMAGE_BUTTON_PAYMENT));
		mButtonPayment.setTouchedImage(PosResUtil.getImageIconFromResource(IMAGE_BUTTON_PAYMENT_TOUCH));	
		mButtonPayment.setHorizontalAlignment(SwingConstants.CENTER);		
		mButtonPayment.setBounds(left, PANEL_CONTENT_V_GAP*5, IMAGE_BUTTON_WIDTH, IMAGE_BUTTON_HEIGHT );	
		mButtonPayment.setOnClickListner(paymentButtonListner);
		mButtonPayment.setEnabled(false);
		if(mViewMode==ViewMode.EditMode && uiSettings.isPaymentButtonVisible())
			mBottomPanel.add(mButtonPayment);

		left=mButtonPayment.getX()+mButtonPayment.getWidth()+PANEL_CONTENT_V_GAP*2;
		mButtonDelete=new PosButton();		
		mButtonDelete.setText("Void Bill");
		mButtonDelete.setImage(PosResUtil.getImageIconFromResource(IMAGE_BUTTON_CANCEL));
		mButtonDelete.setTouchedImage(PosResUtil.getImageIconFromResource(IMAGE_BUTTON_CANCEL_TOUCH));	
		mButtonDelete.setHorizontalAlignment(SwingConstants.CENTER);		
		mButtonDelete.setBounds(left, PANEL_CONTENT_V_GAP, IMAGE_BUTTON_WIDTH, IMAGE_BUTTON_HEIGHT);		
		mButtonDelete.setOnClickListner(deleteButtonListner);
		mButtonDelete.setEnabled(false);
		if(mViewMode==ViewMode.EditMode)
			mBottomPanel.add(mButtonDelete);


		left=mButtonOk.getX()+mButtonOk.getWidth()+PANEL_CONTENT_V_GAP*2;
		mButtonCloseOrder=new PosButton();		
		mButtonCloseOrder.setText("Close Bill");
		mButtonCloseOrder.setMnemonic('B');
		mButtonCloseOrder.setImage(PosResUtil.getImageIconFromResource(IMAGE_BUTTON_CANCEL));
		mButtonCloseOrder.setTouchedImage(PosResUtil.getImageIconFromResource(IMAGE_BUTTON_CANCEL_TOUCH));	
		mButtonCloseOrder.setHorizontalAlignment(SwingConstants.CENTER);		
		mButtonCloseOrder.setBounds(left, PANEL_CONTENT_V_GAP, IMAGE_BUTTON_WIDTH, IMAGE_BUTTON_HEIGHT);		
		mButtonCloseOrder.setOnClickListner(closeOrderButtonListner);
		mButtonCloseOrder.setEnabled(false);
		if(mViewMode==ViewMode.EditMode && uiSettings.isCloseBillButtonVisible())
			mBottomPanel.add(mButtonCloseOrder);

		left=mButtonCloseOrder.getX()+mButtonCloseOrder.getWidth()+PANEL_CONTENT_V_GAP*2;
		mButtonCacel=new PosButton();		
		mButtonCacel.setText("Cancel");
		mButtonCacel.setCancel(true);
		mButtonCacel.setImage(PosResUtil.getImageIconFromResource(IMAGE_BUTTON_CANCEL));
		mButtonCacel.setTouchedImage(PosResUtil.getImageIconFromResource(IMAGE_BUTTON_CANCEL_TOUCH));	
		mButtonCacel.setHorizontalAlignment(SwingConstants.CENTER);		
		mButtonCacel.setBounds(left, PANEL_CONTENT_V_GAP, IMAGE_BUTTON_WIDTH, IMAGE_BUTTON_HEIGHT);		
		mButtonCacel.setOnClickListner(cancelButtonListner);
		mBottomPanel.add(mButtonCacel);
		
		left=mButtonCacel.getX()+mButtonCacel.getWidth()+PANEL_CONTENT_V_GAP*2;
		mButtonPrint=new PosButton();		
		mButtonPrint.setText("Print");
		mButtonPrint.setImage(PosResUtil.getImageIconFromResource(IMAGE_BUTTON_OK));
		mButtonPrint.setTouchedImage(PosResUtil.getImageIconFromResource(IMAGE_BUTTON_OK_TOUCH));	
		mButtonPrint.setHorizontalAlignment(SwingConstants.CENTER);		
		mButtonPrint.setBounds(left, PANEL_CONTENT_V_GAP, IMAGE_BUTTON_WIDTH, IMAGE_BUTTON_HEIGHT);		
		mButtonPrint.setOnClickListner(moreButtonListner);
		mButtonPrint.setEnabled(true);
		mBottomPanel.add(mButtonPrint);

	}

	/**
	 * 
	 */
	private void setItemGridPanel(){

		final int left= QUEUE_CONTAINER_PANEL_WIDTH+PANEL_CONTENT_H_GAP*2;
		final int top=mTitlePanel.getY()+mTitlePanel.getHeight();// TITLE_PANEL_HEIGHT+PANEL_CONTENT_V_GAP*2;
		mItemGridPanel=new PosOrderListPanel(ORDER_ITEM_LIST_PANEL_HEIGHT,true);
		mItemGridPanel.setLocation(left, top);
		mItemGridPanel.setReadOnly(true);
		add(mItemGridPanel);
	}

	/**
	 * @return the mOpenAsReadOnly
	 */
	public boolean OpenAsReadOnly() {
		return mOpenAsReadOnly;
	}
	/**
	 * 
	 */
	private IPosButtonListner okButtonListner=new PosButtonListnerAdapter() {

		@Override
		public void onClicked(PosButton button) {
		
 
			MessageBoxResults result=PosAccessPermissionsUtil.checkEditAuthenticationOfBilledOrder(
					(RootPaneContainer)PosOrderRetrieveForm.this, selectedOrderItem,true);
			if(result==MessageBoxResults.Yes){
				mOpenAsReadOnly=false;
				setItemSelectedListener();
			}
	

			
		}
	};

	/**
	 * returns selected order 
	 */
	
	public BeanOrderHeader getOrder(){
		return selectedOrderItem;
	}


	/**
	 * 
	 */
	private void setItemSelectedListener(){
		
		setVisible(false);
		if(mOnSelectedListner!=null){
			//checkAuthentication();	
			mOnSelectedListner.onItemSelected(PosOrderRetrieveForm.this, selectedOrderItem);
		}
		dispose();	
	}
	
	
	/**
	 * 
	 */
	private IPosButtonListner moreButtonListner=new PosButtonListnerAdapter() {
		@Override
		public void onClicked(PosButton button) {
		 	 
			try {
				
				PosPrintingUtil.showPrintOptions(PosOrderRetrieveForm.this, billPrintListner, moreButtonList);

			} catch (Exception err) {
				PosLog.write(this, "printBill", err);
				PosFormUtil.showErrorMessageBox(this, "Error in printing!!");
			}
			 
		}
	
	};
	


	/*
	 * 
	 */
	private IPosBillPrintFormListner billPrintListner =new PosBillPrintFormAdapter(){
		
		@Override
		public void onRePrintClicked(Object sender) {
			printBill();
		}
		
		@Override
		public void onRePrintKitchenReceiptClicked(Object sender){
			printToKitchen();
			 
		}
		
		@Override
		public void onRePrintBarcodeClicked(Object sender) {
			 
			try {
			 
				final BarCodeLabelPrint bcLabelPrint=new BarCodeLabelPrint();
				bcLabelPrint.print(selectedOrderItem);

			}  catch (Exception err) {
				PosLog.write(this, "printBillBarcode", err);
				PosFormUtil.showErrorMessageBox(PosOrderRetrieveForm.this, "Error in printing!!");
			}
			 
		}
		
		@Override
		public void onRePrintReshitoClicked(Object sender) {
			try {
				PosReceipts.printBillReshito(selectedOrderItem,false);
			} catch (Exception err) {
				PosLog.write(this, "printBillReshito", err);
				PosFormUtil.showErrorMessageBox(PosOrderRetrieveForm.this, "Error in printing!!");
			}
		}
		
		
		@Override
		public void onRePrintItemLabelClicked(Object sender) {
			try{
				if(selectedOrderItem.getOrderServiceType()!=PosOrderServiceTypes.SALES_ORDER) {
					
					PosFormUtil.showErrorMessageBox(PosOrderRetrieveForm.this , "This feature is available for only sales order service.");
				}else
					PosReceipts.printItemLabels(selectedOrderItem);

			 } catch (Exception e) {
					PosLog.write(this, "Print Item Label", e);
					PosFormUtil.showErrorMessageBox(PosOrderRetrieveForm.this,"Failed to print. Please contact administrator.");
			}
		}
	 
	};
 
	 
	/**
	 * 
	 */
	private IPosButtonListner paymentButtonListner=new PosButtonListnerAdapter() {

		@Override
		public void onClicked(PosButton button) {
			
//			if(!getLock(mOrderItem)) return;
			
			if(PosOrderEntryForm.getInstance().getOrderObject()!=null && 
					selectedOrderItem.getOrderId().equals(PosOrderEntryForm.getInstance().getOrderObject().getOrderId())){

				PosFormUtil.showErrorMessageBox(PosOrderRetrieveForm.this,"This order is already opend for order entry.");

			}else{

				PosFormUtil.showPaymentOptions(PosOrderRetrieveForm.this, payMethodListner,selectedOrderItem);

			}
		}
		
	};

	/**
	 * @throws Exception 
	 * 
	 */
	private void showStandardPaymentForm(PaymentMode paymentMode) throws Exception {
		
		final PaymentMode payMode=(selectedOrderItem.getCustomer()!=null && selectedOrderItem.getCustomer().isIsArCompany() )?PaymentMode.Company:paymentMode;
		PosPaymentForm mPaymentForm=new PosPaymentForm(PosOrderRetrieveForm.this,payMode);
		mPaymentForm.setOrderHeader(selectedOrderItem);
		mPaymentForm.setListner(paymentFormListener);
		mPaymentForm.setPaymentMode(payMode);
		if(paymentMode==PaymentMode.QuickCash || 
				paymentMode==PaymentMode.QuickCard ||
				paymentMode==PaymentMode.Online){
			mPaymentForm.doQuickPayment();
		}else{
			
			PosFormUtil.showLightBoxModal(PosOrderRetrieveForm.this, mPaymentForm);
		}
	}

/*
 * 
 */
	IPosPaymentFormListner paymentFormListener=new IPosPaymentFormListner() {
		
		@Override
		public void onPaymentStatusChanged(Object sender) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onPaymentDone(Object sender) {
			
			mOrderItemContainer.reset();
			
		}
		
		@Override
		public void onPaymentCancelled(Object sender) {
				
				mOrderItemContainer.reset();
				
		}
	};
	/**
	 * @param order
	 * @return
	 */
	private boolean canVoid(BeanOrderHeader order){

		boolean canVoid=false;

		if(order!=null && order.getStatus()==PosOrderStatus.Open && (order.getOrderPaymentItems()==null || order.getOrderPaymentItems().size()<=0))
			canVoid=true;

		return canVoid;
	}

 
 
	/**
	 * 
	 */
	private IPosButtonListner deleteButtonListner=new PosButtonListnerAdapter() {
		@Override
		public void onClicked(PosButton button) {

			if(PosOrderEntryForm.getInstance().getOrderObject()!=null && 
					selectedOrderItem.getOrderId().equals(PosOrderEntryForm.getInstance().getOrderObject().getOrderId())){

				PosFormUtil.showErrorMessageBox(PosOrderRetrieveForm.this,"This order is already opend for order entry.");
				return;
				
			}else 
				if(PosOrderUtil.voidOrder(PosOrderRetrieveForm.this, selectedOrderItem))
					mOrderItemContainer.reset();
		}
		};

	 
	 

	/**
	 * 
	 */
	private IPosButtonListner closeOrderButtonListner=new PosButtonListnerAdapter() {

		@Override
		public void onClicked(PosButton button) {
			
			if(PosOrderEntryForm.getInstance().getOrderObject()!=null && 
					selectedOrderItem.getOrderId().equals(PosOrderEntryForm.getInstance().getOrderObject().getOrderId())){

				PosFormUtil.showErrorMessageBox(PosOrderRetrieveForm.this,"This order is already opend for order entry. Please use payment button to close this order.");

			}else if(mOrderItemContainer.getDueAmount()<0){

				try {

					final String orderId=selectedOrderItem.getOrderId();
					closeOrder();
					PosOrderUtil.releaseOrder(orderId);
					
				} catch (Exception e) {
					
					PosLog.write("PosOrderRetrieveForm", "closeOrderButtonListner", e);
					PosFormUtil.showErrorMessageBox(null,"Unable to close the order. Please contact administrator.");

				}


			}else

				PosFormUtil.showQuestionMessageBox(PosOrderRetrieveForm.this,MessageBoxButtonTypes.YesNo, 
						"Do you want to close this bill?", 
						new PosMessageBoxFormListnerAdapter() {
					@Override
					public void onNoButtonPressed() {

					}
					@Override
					public void onYesButtonPressed() {

						try {
							PosOrderHdrProvider pvdr=new PosOrderHdrProvider();
							if(selectedOrderItem.getOrderServiceType()==PosOrderServiceTypes.SALES_ORDER){
								final String invoiceNo=new PosInvoiceProvider().getInvoiceNo(selectedOrderItem.getOrderId(), true);
								selectedOrderItem.setInvoiceNo(invoiceNo);
							}
							selectedOrderItem.setStatus(PosOrderStatus.Closed);
							selectedOrderItem.setClosingDate(PosEnvSettings.getInstance().getPosDate());
							selectedOrderItem.setClosingTime(PosDateUtil.getDateTime());
							selectedOrderItem.setClosedBy(PosEnvSettings.getInstance().getCashierShiftInfo()
									.getCashierInfo());
							pvdr.saveOrder(selectedOrderItem, true, false, false, false);
							mOrderItemContainer.reset();
						} catch (Exception e) {
							PosLog.write("PosOrderRetrieveForm", "closeOrderButtonListner", e);
							PosFormUtil.showErrorMessageBox(null,"Unable to close the order. Please contact administrator.");

						}
					}
				});

		}
	};


	/**
	 * @throws SQLException 
	 * 
	 */
	private void closeOrder() throws Exception{

		PosPaymentForm form=new PosPaymentForm(this, PaymentMode.Cash);
		form.setOrderHeader(selectedOrderItem);
		form.setListner(new IPosPaymentFormListner() {

			@Override
			public void onPaymentStatusChanged( Object sender) {

			}

			@Override
			public void onPaymentDone(Object sender) {
				mOrderItemContainer.reset();					
			}

			@Override
			public void onPaymentCancelled( Object sender) {
				// TODO Auto-generated method stub

			}
		});
		PosFormUtil.showLightBoxModal(this,form);

	}

	/**
	 * @param orderHeader
	 */
	public void setOrderHeader(BeanOrderHeader orderHeader){

		if(orderHeader!=null){
			
			selectedOrderItem=orderHeader;
			mOrderItemContainer.setOrderHeaderInfo(selectedOrderItem);
		}
	}

	/**
	 * 
	 */
	private  IPosButtonListner cancelButtonListner= new PosButtonListnerAdapter() {
		@Override
		public void onClicked(PosButton button) {	
			
			if(selectedOrderItem!=null)
				PosOrderUtil.releaseOrder(selectedOrderItem.getOrderId());
			
			setVisible(false);
			dispose();			
		}
	};	


	private IPosOrederRetriveFormListner mOnSelectedListner;

	/**
	 * @param listner
	 */
	public void setListner(IPosOrederRetriveFormListner listner){

		mOnSelectedListner=listner;
	}
	
	

	
	
	IPosPaymentMetodsFormListner payMethodListner=new IPosPaymentMetodsFormListner() {
		
		@Override
		public void onSelected(PaymentMode paymentMode) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onClick(PosPaymentOption payOption) throws Exception {
			 
			switch(payOption){
			case STANDARD:
				showStandardPaymentForm(PaymentMode.Cash);
				break;
			case SPLIT:
				showSplitPaymentForm();
				break;
			case QUICKCASH:
				showStandardPaymentForm(PaymentMode.QuickCash);
				break;
			case QUICKCARD:
				showStandardPaymentForm(PaymentMode.QuickCard);
				break;
			case ONLINE:
				showStandardPaymentForm(PaymentMode.Online);
				break;
			default:
				break;
				
			}
		}
	};
	 
	/**
	 * @throws SQLException 
	 * 
	 */
	private void showSplitPaymentForm() throws SQLException {
		
		PosOrderSplitForm form=new PosOrderSplitForm(selectedOrderItem.getOrderServiceType());
		form.setParent(this);
		
		form.setOrderHeader(selectedOrderItem);
		PosFormUtil.showLightBoxModal(this,form);
		
		if(selectedOrderItem.getStatus()==PosOrderStatus.Closed)
			mOrderItemContainer.reset();
		else
			mOrderItemContainer.refresh();
	
	}

	/**
	 *  
	 */
	private void printBill() {
		
		try {
			
			final boolean forceToPrint=(PosEnvSettings.getInstance().getPrintSettings().getReceiptPrintingAtPayment()!=EnablePrintingOption.ASK);
			PosFormUtil.showPrintConfirmMessage((RootPaneContainer)this, forceToPrint, selectedOrderItem, false, null, true);
		} catch (Exception err) {
			PosLog.write(this, "printBill", err);
			PosFormUtil.showErrorMessageBox(this, "Error in printing!!");
		}
	}
	

	/**
	 * 
	 */
	private void  printToKitchen() {

		try {

			final MessageBoxResults msgBoxResult=PosFormUtil.showQuestionMessageBox(this, MessageBoxButtonTypes.YesNoCancel,"Print new/modified item[s] only?", null);
			if(msgBoxResult!=MessageBoxResults.Cancel){
				
				if(msgBoxResult==MessageBoxResults.Yes && !PosOrderUtil.hasNotPrintedToKitchenItems(selectedOrderItem)){
					
					PosFormUtil.showInformationMessageBox(this, "Already printed to kitchen(s)." );
				
				}else if (!PosOrderUtil.hasPrintableItems(selectedOrderItem,  msgBoxResult==MessageBoxResults.No)){
					
					PosFormUtil.showInformationMessageBox(this, "No Items to print." );
				}else{
					
					final PosOrderHdrProvider pvdr=new PosOrderHdrProvider();
					PosReceipts.printReceiptToKitchen(selectedOrderItem, msgBoxResult==MessageBoxResults.No);
					PosOrderUtil.setAsPrintedToKitchen(selectedOrderItem);
					pvdr.saveOrder(selectedOrderItem);
					mItemGridPanel.setPosOrderEntryItem(selectedOrderItem);
				}
			}
			
		} catch (Exception e) {
			PosLog.write(this, "printBill", e);
			PosFormUtil.showErrorMessageBox(this, "Error in printing!!");
		}
		
		 
	}



}
