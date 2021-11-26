/**
 * 
 */
package com.indocosmo.pos.forms;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.RootPaneContainer;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import com.indocosmo.barcode.reports.BarCodeLabelPrint;
import com.indocosmo.pos.common.PosEnvSettings;
import com.indocosmo.pos.common.PosLog;
import com.indocosmo.pos.common.enums.PosOrderServiceTypes;
import com.indocosmo.pos.common.utilities.PosCurrencyUtil;
import com.indocosmo.pos.common.utilities.PosDateUtil;
import com.indocosmo.pos.common.utilities.PosFormUtil;
import com.indocosmo.pos.common.utilities.PosOrderUtil;
import com.indocosmo.pos.common.utilities.PosResUtil;
import com.indocosmo.pos.data.beans.BeanOrderHeader;
import com.indocosmo.pos.data.beans.BeanOrderHeader.PosOrderStatus;
import com.indocosmo.pos.data.beans.BeanOrderPayment;
import com.indocosmo.pos.data.beans.BeanPaymentSummary;
import com.indocosmo.pos.data.providers.shopdb.PosOrderHdrProvider;
import com.indocosmo.pos.data.providers.shopdb.PosOrderPaymentsProvider;
import com.indocosmo.pos.forms.components.buttons.PosButton;
import com.indocosmo.pos.forms.components.buttons.listners.IPosButtonListner;
import com.indocosmo.pos.forms.components.itemcontrols.PosOrderListItemControl;
import com.indocosmo.pos.forms.components.orderentry.PosOrderListOptionPanel;
import com.indocosmo.pos.forms.components.orderentry.PosOrderListPanel;
import com.indocosmo.pos.forms.components.orderentry.listners.PosOrderListPanelAdapter;
import com.indocosmo.pos.forms.components.payment.PosRefundPaymentModeContainerPanel;
import com.indocosmo.pos.forms.messageboxes.PosMessageBoxForm.MessageBoxButtonTypes;
import com.indocosmo.pos.forms.messageboxes.PosMessageBoxForm.MessageBoxResults;
import com.indocosmo.pos.reports.receipts.PosReceipts;
import com.indocosmo.pos.terminal.devices.PosDeviceManager;
import com.indocosmo.pos.terminal.devices.printer.PosDeviceKitchenPrinter;

/**
 * @author jojesh
 * 
 */
@SuppressWarnings("serial")
public class PosOrderDetailsForm extends PosBaseForm {

	private static final int PANEL_WIDTH = 1000;
	private static final int PANEL_HEIGHT = 575;
	private static final int PANEL_CONTENT_V_GAP = 2;
	private static final int PANEL_CONTENT_H_GAP = 2;


	private static final String REMARKS_IMAGE_ITEM_BUTTON = "dlg_remarks.png";
	private static final String REMARKS_IMAGE_ITEM_BUTTON_TOUCH = "dlg_remarks_touch.png";
	private static final int MESSAGE_PANEL_HEIGHT = 20;
	private static final int FIELD_VERTICAL_GAP = 2;
	private static final int FIELD_HORIZONTAL_GAP = 1;
	private static final int TITLE_FIELD_WIDTH=114;
	private static final int VALUE_FIELD_WIDTH=217 ;
	private static final int AMOUNT_FIELD_WIDTH=106;
	private static final int FIELD_HEIGHT=40;
	
	private static final Border LABEL_PADDING = new EmptyBorder(2, 2, 2, 2);
	
	private PosOrderListPanel mOrderListPanel;
	private PosOrderListOptionPanel mOrderListOptionPanel;
	private JPanel mContianerPanel;

	private PosRefundPaymentModeContainerPanel mRefundPaymentModeContainerPanel;
	private JPanel mPanelBillSummary;
	private JPanel mPanelPaymentSummary;
	private JPanel mPanelRefundSummary;
	private JPanel mPanelServiceSummary;


	private PosOrderHdrProvider mOderHdrProvider;
	private ArrayList<BeanOrderPayment> mRefundList;
	private PosButton mButtonPrintReceipt;
	private PosButton mButtonPrintReshito;
	private PosButton mButtonPrintKitchenReceipt;
	private PosButton mButtonPrintBarcode;
	private PosButton mButtonPrintSO;
	private PosButton mButtonRemarks;

	private JPanel mPanelOrderSummary;
	private JLabel mLabelOrderNoValue;
	private JLabel mLabelTimeValue;
	private JLabel mLabelDateValue;
	private JLabel mLabelCustomerValue;
	private JLabel mLabelCashierValue;
	private JLabel mLabelItemTotalValue;
	private JLabel mLabelOrderStatus;


	/**
	 * @param title
	 * @param cPanelwidth
	 * @param cPanelHeight
	 */
	public PosOrderDetailsForm(String orderId) {
		super("Order Details", PANEL_WIDTH, PANEL_HEIGHT);
		setOkButtonCaption("Make Payment");
		setCancelButtonCaption("Close");
		createRePrintButton();
//		createVoidButton();
		createRemarksButton();
		createOrderSummaryPanel();
		createBillSummaryPanel();
		createPaymentSummaryPanel();
		createRefundSummaryPanel();
		createServiceSummaryPanel();
		mOderHdrProvider = new PosOrderHdrProvider();
		resetFields();
		resetButtons();
		setOkButtonVisible(false);
//		mButtonVoid.setVisible(false);
		populateOrderDetails(orderId);
		
		
	}
	
	/**
	 * @param title
	 * @param cPanelwidth
	 * @param cPanelHeight
	 */
	public PosOrderDetailsForm(   ) {
		super("Order Details", PANEL_WIDTH, PANEL_HEIGHT);
		setOkButtonCaption("Make Payment");
		setCancelButtonCaption("Close");
		createRePrintButton();
//		createVoidButton();
		createRemarksButton();
		 createOrderSummaryPanel();
		 createBillSummaryPanel();
		createPaymentSummaryPanel();
		createRefundSummaryPanel();
		createServiceSummaryPanel();
		mOderHdrProvider = new PosOrderHdrProvider();
		resetFields();
		resetButtons();
		setOkButtonVisible(false);
		
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.indocosmo.pos.forms.PosBaseForm#setContentPanel(javax.swing.JPanel)
	 */
	@Override
	protected void setContentPanel(JPanel panel) {

		mContianerPanel = panel;
		mContianerPanel.setLayout(null);
		setItemGridPanel();
	}

	private void setItemGridPanel() {
		int left = 0;
		int top = 0;
		mOrderListOptionPanel = new PosOrderListOptionPanel(this);
		mOrderListOptionPanel.setLocation(left, top);
		mOrderListOptionPanel.setOpaque(false);
		mOrderListOptionPanel.setReadOnly(true);
		
		mContianerPanel.add(mOrderListOptionPanel);
		
		top += PosOrderListOptionPanel.PANEL_HEIGHT - 1;
		mOrderListPanel = new PosOrderListPanel(PANEL_HEIGHT
				- PosOrderListOptionPanel.PANEL_HEIGHT - PANEL_CONTENT_V_GAP,true);
		mOrderListPanel.setLocation(left, top);
		mOrderListPanel.setReadOnly(true);
		mOrderListPanel.setListner(new PosOrderListPanelAdapter() {
			@Override
			public void onItemDeleted(PosOrderListItemControl item) {
				populateRefundPaymentModeContainerPanel(mSelectedOrderDetails);
			}
		});
		mContianerPanel.add(mOrderListPanel);
		mOrderListPanel.setItemGridControlPanel(mOrderListOptionPanel);
		
	}

	/**
	 * 
	 */
	private void createRemarksButton() {
	
		mButtonRemarks = new PosButton();
		mButtonRemarks.setText("Remarks");
		mButtonRemarks.setImage(PosResUtil
				.getImageIconFromResource(REMARKS_IMAGE_ITEM_BUTTON));
		mButtonRemarks.setTouchedImage(PosResUtil
				.getImageIconFromResource(REMARKS_IMAGE_ITEM_BUTTON_TOUCH));
		mButtonRemarks.setHorizontalAlignment(SwingConstants.CENTER);
		mButtonRemarks.setSize(IMAGE_BUTTON_WIDTH, IMAGE_BUTTON_HEIGHT);
		mButtonRemarks.setOnClickListner(new IPosButtonListner() {

			@Override
			public void onClicked(PosButton button) {
				editRemarks();
			}
		});
		mButtonRemarks.setEnabled(false);
		addButtonsToBottomPanel(mButtonRemarks, 4);
		mButtonRemarks.setVisible(false);

	}

	/**
	 * 
	 */
	private void editRemarks() {
		
		if (mSelectedOrderDetails != null) {
			PosOrderRemarksEntryForm remarksform = new PosOrderRemarksEntryForm();
			remarksform.setOrderHeaderItem(mSelectedOrderDetails);
			PosFormUtil.showLightBoxModal(this, remarksform);
		}
	}

	 
	/**
	 * 
	 */
	private void createRePrintButton() {
		
		mButtonPrintReceipt = new PosButton();
		mButtonPrintReceipt.setText("Print");
		mButtonPrintReceipt.setImage(PosResUtil
				.getImageIconFromResource(IMAGE_BUTTON_OK));
		mButtonPrintReceipt.setTouchedImage(PosResUtil
				.getImageIconFromResource(IMAGE_BUTTON_OK_TOUCH));
		mButtonPrintReceipt.setHorizontalAlignment(SwingConstants.CENTER);
		mButtonPrintReceipt.setSize(IMAGE_BUTTON_WIDTH, IMAGE_BUTTON_HEIGHT);
		mButtonPrintReceipt.setOnClickListner(new IPosButtonListner() {

			@Override
			public void onClicked(PosButton button) {
				if (mSelectedOrderDetails != null) {
					printBill(PosOrderDetailsForm.this, mSelectedOrderDetails);
				}
			}
		});
		mButtonPrintReceipt.setEnabled(false);
		addButtonsToBottomPanel(mButtonPrintReceipt, 2);
		

		mButtonPrintSO = new PosButton();
		mButtonPrintSO.setText("Sales Order");
		mButtonPrintSO.setImage(PosResUtil
				.getImageIconFromResource(IMAGE_BUTTON_OK));
		mButtonPrintSO.setTouchedImage(PosResUtil
				.getImageIconFromResource(IMAGE_BUTTON_OK_TOUCH));
		mButtonPrintSO.setHorizontalAlignment(SwingConstants.CENTER);
		mButtonPrintSO.setSize(IMAGE_BUTTON_WIDTH, IMAGE_BUTTON_HEIGHT);
		mButtonPrintSO.setOnClickListner(new IPosButtonListner() {

			@Override
			public void onClicked(PosButton button) {
				if (mSelectedOrderDetails != null) {
					
					 try {
						PosFormUtil.showSalesOrderPrintConfirmMessage(PosOrderDetailsForm.this, true, mSelectedOrderDetails, false);
					 		
						} catch (Exception err) {
		
							PosLog.write(this, "printsalesorder", err);
							PosFormUtil.showErrorMessageBox(this, "Error in printing!!");
						
						}
				}
			}
		});
		mButtonPrintSO.setEnabled(false);
		mButtonPrintSO.setVisible(false);
		addButtonsToBottomPanel(mButtonPrintSO, 2);
		
		mButtonPrintReshito = new PosButton();
		mButtonPrintReshito.setText("Print Reshito");
		mButtonPrintReshito.setImage(PosResUtil
				.getImageIconFromResource(IMAGE_BUTTON_OK));
		mButtonPrintReshito.setTouchedImage(PosResUtil
				.getImageIconFromResource(IMAGE_BUTTON_OK_TOUCH));
		mButtonPrintReshito.setHorizontalAlignment(SwingConstants.CENTER);
		mButtonPrintReshito.setSize(IMAGE_BUTTON_WIDTH, IMAGE_BUTTON_HEIGHT);
		mButtonPrintReshito.setEnabled(false);
		mButtonPrintReshito.setOnClickListner(new IPosButtonListner() {

			@Override
			public void onClicked(PosButton button) {
				if (mSelectedOrderDetails != null) {
					printBillReshito(mSelectedOrderDetails);
				}
			}
		});
		mButtonPrintReshito.setEnabled(false);
		if(PosEnvSettings.getInstance().getUISetting().getOrderDetailSettings().isPrintReshitoButtonVisible())
			addButtonsToBottomPanel(mButtonPrintReshito, 2);

		mButtonPrintKitchenReceipt= new PosButton();
		mButtonPrintKitchenReceipt.setText("Kitchen Print");
		mButtonPrintKitchenReceipt.setImage(PosResUtil
				.getImageIconFromResource(IMAGE_BUTTON_OK));
		mButtonPrintKitchenReceipt.setTouchedImage(PosResUtil
				.getImageIconFromResource(IMAGE_BUTTON_OK_TOUCH));
		mButtonPrintKitchenReceipt.setHorizontalAlignment(SwingConstants.CENTER);
		mButtonPrintKitchenReceipt.setSize(IMAGE_BUTTON_WIDTH, IMAGE_BUTTON_HEIGHT);
		mButtonPrintKitchenReceipt.setOnClickListner(new IPosButtonListner() {

			@Override
			public void onClicked(PosButton button) {
				if (mSelectedOrderDetails != null) {
					printKitchenReceipt(mSelectedOrderDetails);
				}
			}
		});
		
		if(PosDeviceManager.getInstance().hasKichenPrinter() ){
					for(PosDeviceKitchenPrinter kp: PosDeviceManager.getInstance().getKitchenPrinters()){

						if(kp.isDeviceInitialized() && kp.isActive()){
							addButtonsToBottomPanel(mButtonPrintKitchenReceipt, 2);
							break;
						}
					}
				}
		
		 
		mButtonPrintBarcode = new PosButton();
		mButtonPrintBarcode.setText("Print Barcode");
		mButtonPrintBarcode.setImage(PosResUtil
				.getImageIconFromResource(IMAGE_BUTTON_OK));
		mButtonPrintBarcode.setTouchedImage(PosResUtil
				.getImageIconFromResource(IMAGE_BUTTON_OK_TOUCH));
		mButtonPrintBarcode.setHorizontalAlignment(SwingConstants.CENTER);
		mButtonPrintBarcode.setSize(IMAGE_BUTTON_WIDTH, IMAGE_BUTTON_HEIGHT);
		mButtonPrintBarcode.setOnClickListner(new IPosButtonListner() {

			@Override
			public void onClicked(PosButton button) {
				if (mSelectedOrderDetails != null) {
					printBarcode(mSelectedOrderDetails);
				}
			}
		});
		if(PosEnvSettings.getInstance().getPrintSettings().isBarCodePrintingEnabled())
			addButtonsToBottomPanel(mButtonPrintBarcode, 2);

	}
	/*
	 * 
	 */
	private boolean printBarcode(BeanOrderHeader orderHdr) {
		
		try{
			
			final BarCodeLabelPrint bcLabelPrint=new BarCodeLabelPrint();
			bcLabelPrint.print(orderHdr);

		 } catch (Exception e) {
				PosLog.write(this, "Print Barcode", e);
				PosFormUtil.showErrorMessageBox(this,"Failed to print. Please contact administrator.");
				return false;
		}
		return true;
	}
/*
 * 
 */
	private void printKitchenReceipt(BeanOrderHeader orderHeader){
		try {
			
			if( orderHeader.getOrderDetailItems()==null || orderHeader.getOrderDetailItems().size()==0)
				return;

			try {

				if (orderHeader.getStatus()==PosOrderStatus.Open || orderHeader.getStatus()==PosOrderStatus.Partial){
					
					final MessageBoxResults msgBoxResult=PosFormUtil.showQuestionMessageBox(this, MessageBoxButtonTypes.YesNoCancel,"Print new/modified item[s] only?", null);
					if(msgBoxResult!=MessageBoxResults.Cancel){
						
						if(msgBoxResult==MessageBoxResults.Yes && !PosOrderUtil.hasNotPrintedToKitchenItems(orderHeader)){
							
							PosFormUtil.showInformationMessageBox(this, "Already printed to kitchen(s)." );
						
						}else{
							
							final PosOrderHdrProvider pvdr=new PosOrderHdrProvider();
							PosReceipts.printReceiptToKitchen(orderHeader, msgBoxResult==MessageBoxResults.No);
							PosOrderUtil.setAsPrintedToKitchen(orderHeader);
							pvdr.saveOrder(orderHeader);
							 
						}
					}
				}else
					PosReceipts.printReceiptToKitchen(orderHeader, true);
					
			} catch (Exception e) {
				PosLog.write(this, "printKitchen Recipt", e);
				PosFormUtil.showErrorMessageBox(this, "Error in printing!!");
			}
					
		} catch (Exception e) {
			PosLog.write("OrderDetails", "Kitchen Print", e);
			PosFormUtil.showErrorMessageBox(PosOrderDetailsForm.this,
					"Failed to print. Please contact administrator.");
		
		}
	}
	/**
	 * @param orderHeader
	 */
	private void printBill(Object sender,BeanOrderHeader orderHeader) {
		
		try {
//			PosReceiptBase.printReceipt(orderHeader, false, null, false, false);
//			PosReceiptBase.printReceipt(orderHeader, false, null, false, false,true);
			if (mSelectedOrderDetails.getStatus().equals(PosOrderStatus.Refunded) || mSelectedOrderDetails.getStatus().equals(PosOrderStatus.Closed)) {
			
				if ( PosFormUtil.showPrintConfirmMessage((RootPaneContainer)sender, true, orderHeader, false, null,false)
						&& 	mSelectedOrderDetails.getStatus().equals(PosOrderStatus.Refunded) )
					
					PosOrderUtil.printRefundReceipt(this,mSelectedOrderDetails,false);
			}
			else{
				PosFormUtil.showPrintConfirmMessage((RootPaneContainer)sender, true, orderHeader, false, null, true);
			}
				
//			if (orderHeader.getStatus().equals(PosOrderStatus.Refunded))
//				PosOrderUtil.printRefundReceipt(orderHeader,useAltLang);
		} catch (Exception err) {
			PosLog.write(this, "printBill", err);
			PosFormUtil.showErrorMessageBox(this, "Error in printing!!");
		}
	}
	
	/**
	 * @param orderHeader
	 */
	private void printBillReshito(BeanOrderHeader orderHeader) {
		
		try {
			PosReceipts.printBillReshito(orderHeader,false);
		} catch (Exception err) {
			PosLog.write(this, "printBillReshito", err);
			PosFormUtil.showErrorMessageBox(this, "Error in printing!!");
		}
	}

	

	private BeanOrderHeader mSelectedOrderDetails;
	private JLabel mLabelItemDiscountValue;
	private JLabel mLabelItemSubTotalValue;

	/**
	 * @param orderID
	 */
	public void populateOrderDetails(String orderID) {
		try {
			final String orderHeaderID = orderID;
			mSelectedOrderDetails = mOderHdrProvider
					.getOrderData(orderHeaderID);
			if (mSelectedOrderDetails == null) {
				PosFormUtil
						.showInformationMessageBox(this,
								"No such order exist. Please make sure that the order number is correct.");
				mOrderListPanel.setPosOrderEntryItem(null);
				resetFields();
				resetButtons();
				return;
			}

			mOrderListPanel.setPosOrderEntryItem(mSelectedOrderDetails);
			mButtonPrintSO.setVisible(mSelectedOrderDetails.getOrderServiceType()==PosOrderServiceTypes.SALES_ORDER);
			resetFields();
			populateOrderSummaryPanel(mSelectedOrderDetails);
			populateBillSummaryPanel(mSelectedOrderDetails);

			if (mSelectedOrderDetails.getStatus().equals(PosOrderStatus.Refunded) || mSelectedOrderDetails.getStatus().equals(PosOrderStatus.Closed)) {
				mButtonPrintReshito.setVisible(true);
			}
			
			if (mSelectedOrderDetails.getStatus().equals(PosOrderStatus.Refunded)) {
				mPanelRefundSummary.setVisible(true);
				populatePaymentSummaryPanel(mSelectedOrderDetails);
				populateRefundSummaryPanel(mSelectedOrderDetails);
				if(mSelectedOrderDetails.getOrderServiceType().equals(PosOrderServiceTypes.TABLE_SERVICE)){
					populateServiceSummaryPanel(mSelectedOrderDetails);
				}
				setOkEnabled(false);
				mButtonRemarks.setEnabled(false);
			}else {
				mPanelRefundSummary.setVisible(false);
				populatePaymentSummaryPanel(mSelectedOrderDetails);
				if(mSelectedOrderDetails.getOrderServiceType().equals(PosOrderServiceTypes.TABLE_SERVICE)){
					populateServiceSummaryPanel(mSelectedOrderDetails);
				}
				setOkEnabled(true);
				mButtonRemarks.setEnabled(true);
//				mButtonVoid.setEnabled(true);
			}
			
			if(PosDeviceManager.getInstance().hasReceiptPrinter()){
				if(PosDeviceManager.getInstance().getReceiptPrinter() !=null 
						&& PosDeviceManager.getInstance().getReceiptPrinter().isDeviceInitialized()){
					
					mButtonPrintReceipt.setEnabled(true);
					mButtonPrintReshito.setEnabled(true);
					mButtonPrintSO.setEnabled(true);
				}
			}
		 
		} catch (Exception e) {
			PosLog.write(this, "populateOrderDetails", e);
			PosFormUtil.showErrorMessageBox(this,"Failed to load the order details.");
		}
	}

	/**
	 * @param mSelectedOrderDetails2
	 */
	private void populateServiceSummaryPanel(BeanOrderHeader mSelectedOrderDetails) {
		
		if (mSelectedOrderDetails.getStatus().equals(PosOrderStatus.Refunded)){
			int left=mOrderListPanel.getX()+mOrderListPanel.getWidth()+PANEL_CONTENT_H_GAP;
			int top=mPanelRefundSummary.getY()+mPanelRefundSummary.getHeight()+PANEL_CONTENT_V_GAP;
			
			mPanelServiceSummary.setLocation(left,top);
		}
			
		mLabelServiceTypeValue.setText(mSelectedOrderDetails.getServingTableName());
		mLabelCoversNoValue.setText(String.valueOf(mSelectedOrderDetails.getCovers()));
		mLabelServedByValue.setText(mSelectedOrderDetails.getServedBy().getName());
		mLabelServedDeviceValue.setText(mSelectedOrderDetails.getStationCode());
		
		mPanelServiceSummary.setVisible(true);
		
		
	}

 
	
	/**
	 * 
	 */
	private void createOrderSummaryPanel(){
		int left = mOrderListPanel.getX() + mOrderListPanel.getWidth()
				+ PANEL_CONTENT_H_GAP+1;
		int top = PANEL_CONTENT_V_GAP+1;
		int width = PANEL_WIDTH - mOrderListPanel.getWidth()
				- PANEL_CONTENT_H_GAP * 3;
		int height = FIELD_HEIGHT*4+FIELD_VERTICAL_GAP*7+MESSAGE_PANEL_HEIGHT;
		
		mPanelOrderSummary=createBoxedPanel("Order Summary", width, height);
		mPanelOrderSummary.setLocation(left,top);
		mContianerPanel.add(mPanelOrderSummary);
		
		Font font=PosFormUtil.getLabelFont();// mItemTitleLableFont.deriveFont(Font.BOLD);
		 
		mLabelOrderNoValue=createTextValueField("Ref. No. :", mPanelOrderSummary);
		mLabelOrderStatus=createTextValueField("Status :", mPanelOrderSummary);
		
		createOrderDateTime();
	

		mLabelItemSubTotalValue=createAmountField("Item Total :", mPanelOrderSummary,VALUE_FIELD_WIDTH);
		
		mLabelCashierValue=createTextValueField("Cashier :", mPanelOrderSummary);
		mLabelItemDiscountValue=createAmountField("Discount :", mPanelOrderSummary,VALUE_FIELD_WIDTH);
		
		mLabelCustomerValue=createTextValueField("Customer :", mPanelOrderSummary);
		mLabelItemTotalValue=createAmountField("Item Net :", mPanelOrderSummary,VALUE_FIELD_WIDTH);
		
		
	}


	/*
	 * create title and value lable
	 */
	private void createOrderDateTime(){
		
		Font font=PosFormUtil.getLabelFont();
		

		
		JLabel labelOrderDateTitle=new JLabel("Date Time:");
		labelOrderDateTitle.setBorder(LABEL_PADDING);
		labelOrderDateTitle.setBackground(Color.LIGHT_GRAY);
		labelOrderDateTitle.setOpaque(true);
		labelOrderDateTitle.setFont(font);
		labelOrderDateTitle.setPreferredSize(new Dimension(TITLE_FIELD_WIDTH, FIELD_HEIGHT));
		mPanelOrderSummary.add(labelOrderDateTitle);
		
		 
 
		mLabelDateValue=new JLabel();
		mLabelDateValue.setText("2012-10-12");
		mLabelDateValue.setPreferredSize(new Dimension( VALUE_FIELD_WIDTH/2 , FIELD_HEIGHT));
		mLabelDateValue.setBorder(new LineBorder(Color.decode("#b8cfe5")));
		mLabelDateValue.setFont(font);
		mPanelOrderSummary.add(mLabelDateValue);
		
		mLabelTimeValue=new JLabel();
		mLabelTimeValue.setText("00:00");
		mLabelTimeValue.setPreferredSize(new Dimension( (VALUE_FIELD_WIDTH/2) -1, FIELD_HEIGHT));
		mLabelTimeValue.setBorder(new LineBorder(Color.decode("#b8cfe5")));
		mLabelTimeValue.setFont(font);
		mPanelOrderSummary.add(mLabelTimeValue);
	}
	

	/*
	 * create title and value lable
	 */
	private JLabel createTextValueField( String Title,JPanel panel){
		
		Font font=PosFormUtil.getLabelFont();
		
		JLabel labelTitle=new JLabel(Title);
		labelTitle.setBorder(LABEL_PADDING);
		labelTitle.setBackground(Color.LIGHT_GRAY);
		labelTitle.setOpaque(true);
		labelTitle.setFont(font);
		labelTitle.setPreferredSize(new Dimension(TITLE_FIELD_WIDTH, FIELD_HEIGHT));
		panel.add(labelTitle);
		
		JLabel labelValue=new JLabel();
		labelValue.setText("");
		labelValue.setBorder(new LineBorder(Color.decode("#b8cfe5")));
		labelValue.setFont(font);
		labelValue.setHorizontalAlignment(SwingConstants.LEFT);
		labelValue.setPreferredSize(new Dimension(VALUE_FIELD_WIDTH, FIELD_HEIGHT));
		panel.add(labelValue);
		return labelValue; 
	}
	
	
	/*
	 * create title and value lable
	 */
	private JLabel createAmountField( String Title,JPanel panel){
		
		return createAmountField(Title, panel, AMOUNT_FIELD_WIDTH);
	}
	

	/*
	 * create title and value lable
	 */
	private JLabel createAmountField( String Title,JPanel panel,int width){
		
		Font font=PosFormUtil.getLabelFont();
		
		JLabel labelTitle=new JLabel(Title);
		labelTitle.setBorder(LABEL_PADDING);
		labelTitle.setBackground(Color.LIGHT_GRAY);
		labelTitle.setOpaque(true);
		labelTitle.setFont(font);
		labelTitle.setPreferredSize(new Dimension(TITLE_FIELD_WIDTH, FIELD_HEIGHT));
		panel.add(labelTitle);
		
		JLabel labelValue=new JLabel();
		labelValue.setText("00.00");
		labelValue.setBorder(new LineBorder(Color.decode("#b8cfe5")));
		labelValue.setFont(font);
		labelValue.setHorizontalAlignment(SwingConstants.RIGHT);
		labelValue.setPreferredSize(new Dimension(width, FIELD_HEIGHT));
		panel.add(labelValue);
		return labelValue;
	}
	
	

	private JLabel mLabelCashValue;
	private JLabel mLabelCompanyValue;
	private JLabel mLabelCardValue;
	private JLabel mLabelVoucherValue;
//	private JLabel mLabelDiscountValue;
	private JLabel mLabelBalanceValue;
	private JLabel mLabelBillDueAmtValue;
	private JLabel mLabelDiscountValue;
	
	/**
	 * 
	 */
	private void createPaymentSummaryPanel(){
		
		int left=mOrderListPanel.getX()+mOrderListPanel.getWidth()+PANEL_CONTENT_H_GAP;
		int top=mPanelBillSummary.getY()+mPanelBillSummary.getHeight()+PANEL_CONTENT_V_GAP;
		int width=PANEL_WIDTH-mOrderListPanel.getWidth()-PANEL_CONTENT_H_GAP*3;
		int height=FIELD_HEIGHT*3+FIELD_VERTICAL_GAP*6+MESSAGE_PANEL_HEIGHT;
		
		mPanelPaymentSummary=createBoxedPanel("Payment Summary", width, height);
		mPanelPaymentSummary.setLocation(left,top);
		mContianerPanel.add(mPanelPaymentSummary);
		mPanelPaymentSummary.setVisible(true);

		mLabelCashValue=createAmountField("Cash :",mPanelPaymentSummary);
		mLabelCardValue=createAmountField("Card :",mPanelPaymentSummary);
		mLabelVoucherValue=createAmountField("Voucher :",mPanelPaymentSummary,AMOUNT_FIELD_WIDTH-PANEL_CONTENT_H_GAP);
		mLabelCompanyValue=createAmountField("Company :",mPanelPaymentSummary);
		mLabelBalanceValue=createAmountField("Balance :",mPanelPaymentSummary);
		mLabelDiscountValue=createAmountField("Discount :",mPanelPaymentSummary,AMOUNT_FIELD_WIDTH-PANEL_CONTENT_H_GAP);
		mLabelBillRoundingValue=createAmountField("Rounding :",mPanelPaymentSummary);
		
	   
	}
 

	private JLabel mLabelBillSubTotalValue;
	private JLabel mLabelBillRoundingValue;
	private JLabel mLabelBillTotalDiscValue;
	private JLabel mLabelBillPartPayValue;
 	
	/**
	 * 
	 */
	private void createBillSummaryPanel(){
		
		int left=mOrderListPanel.getX()+mOrderListPanel.getWidth()+PANEL_CONTENT_H_GAP;
		int top=mPanelOrderSummary.getY()+mPanelOrderSummary.getHeight()+PANEL_CONTENT_V_GAP;
		int width=PANEL_WIDTH-mOrderListPanel.getWidth()-PANEL_CONTENT_H_GAP*3;
		int height=FIELD_HEIGHT*1+FIELD_VERTICAL_GAP*4+MESSAGE_PANEL_HEIGHT;
		
		mPanelBillSummary=createBoxedPanel("Bill Summary", width, height);
		mPanelBillSummary.setLocation(left,top);
		mContianerPanel.add(mPanelBillSummary);
		mPanelBillSummary.setVisible(true);

		mLabelBillPartPayValue=createAmountField("Part. Pay. :",mPanelBillSummary);
		mLabelBillTotalDiscValue=createAmountField("Discount :",mPanelBillSummary);
		mLabelBillDueAmtValue=createAmountField("Due :",mPanelBillSummary,AMOUNT_FIELD_WIDTH-PANEL_CONTENT_H_GAP);
		
	}

 
	private JLabel mLabelRefundCashValue;
	private JLabel mLabelRefundCardValue;
	private JLabel mLabelRefundCompanyValue;
	private JLabel mLabelRefundVoucherValue;
	
	/**
	 * 
	 */
	private void createRefundSummaryPanel(){
		
		int left=mOrderListPanel.getX()+mOrderListPanel.getWidth()+PANEL_CONTENT_H_GAP;
		int top=mPanelPaymentSummary.getY()+mPanelPaymentSummary.getHeight()+PANEL_CONTENT_V_GAP;
		int width=PANEL_WIDTH-mOrderListPanel.getWidth()-PANEL_CONTENT_H_GAP*3;
		int height=FIELD_HEIGHT*2+FIELD_VERTICAL_GAP*5+MESSAGE_PANEL_HEIGHT;
		
		mPanelRefundSummary=createBoxedPanel("Refund Summary", width, height);
		mPanelRefundSummary.setLocation(left,top);
		mContianerPanel.add(mPanelRefundSummary);
		mPanelRefundSummary.setVisible(false);

		mLabelRefundCashValue=createAmountField("Cash :",mPanelRefundSummary);
		mLabelRefundCardValue=createAmountField("Card :",mPanelRefundSummary);
		mLabelRefundVoucherValue=createAmountField("Voucher :",mPanelRefundSummary,AMOUNT_FIELD_WIDTH-PANEL_CONTENT_H_GAP);
		mLabelRefundCompanyValue=createAmountField("Company :",mPanelRefundSummary);
  
	}

	private JLabel mLabelServiceTypeValue;
	private JLabel mLabelServedByValue;
	private JLabel mLabelCoversNoValue;
	private JLabel mLabelServedDeviceValue;
	
	/**
	 * 
	 */
	private void createServiceSummaryPanel(){
		
		int left=mOrderListPanel.getX()+mOrderListPanel.getWidth()+PANEL_CONTENT_H_GAP;
		int top=mPanelPaymentSummary.getY()+mPanelPaymentSummary.getHeight()+PANEL_CONTENT_V_GAP;
		int width=PANEL_WIDTH-mOrderListPanel.getWidth()-PANEL_CONTENT_H_GAP*3;
		int height=FIELD_HEIGHT*2+FIELD_VERTICAL_GAP*3+MESSAGE_PANEL_HEIGHT;
		
		mPanelServiceSummary=createBoxedPanel("Service Summary", width, height);
		mPanelServiceSummary.setLocation(left,top);
		mContianerPanel.add(mPanelServiceSummary);
		mPanelServiceSummary.setVisible(false);
		
		mLabelServiceTypeValue=createAmountField("Table :",mPanelServiceSummary);
		mLabelServedByValue=createAmountField("Served by :",mPanelServiceSummary);
		mLabelCoversNoValue=createAmountField("Covers :",mPanelServiceSummary);
		mLabelServedDeviceValue=createAmountField("Station :",mPanelServiceSummary);
   
		
	}

	 
	/**
	 * @param title
	 * @param width
	 * @param height
	 * @return
	 */
	private JPanel createBoxedPanel(String title, int width, int height ) {

		JPanel boxPanel = new JPanel();
		Dimension size = new Dimension(width, height);
		boxPanel.setBackground(Color.white);
		boxPanel.setPreferredSize(size);
		boxPanel.setSize(size);
		boxPanel.setBorder(new LineBorder(Color.LIGHT_GRAY));
		boxPanel.setLayout(new FlowLayout(FlowLayout.LEFT,2,2));
		

		JLabel labelTitle = new JLabel();
		labelTitle.setText(title);
		labelTitle.setHorizontalAlignment(SwingConstants.CENTER);
		labelTitle.setPreferredSize(new Dimension(width, MESSAGE_PANEL_HEIGHT));
		labelTitle.setSize(new Dimension(width, MESSAGE_PANEL_HEIGHT));
		labelTitle.setOpaque(true);
		labelTitle.setBackground(LABEL_BG_COLOR);
		labelTitle.setForeground(Color.WHITE);
		labelTitle.setFont(PosFormUtil.getSubHeadingFont());
		boxPanel.add(labelTitle);
		return boxPanel;
	}
	 


	/**
	 * 
	 */
	private void resetFields() {
		
		mLabelOrderNoValue.setText("");
		mLabelOrderStatus.setText("");
		mLabelDateValue.setText("");
		mLabelTimeValue.setText("");
		mLabelItemDiscountValue.setText("");
		mLabelCustomerValue.setText("");
		mLabelCashierValue.setText("");
		mLabelItemSubTotalValue.setText("");
		mLabelItemTotalValue.setText("00.00");
		resetServiceSummaryPanel();
	}

	/**
	 * 
	 */
	private void resetServiceSummaryPanel() {

		mLabelServiceTypeValue.setText("");
		mLabelCoversNoValue.setText("0");
		mLabelServedByValue.setText("");
		mLabelServedDeviceValue.setText("");
		
		mPanelServiceSummary.setVisible(false);
		
	}

	/**
	 * 
	 */
	private void resetButtons() {
		//mButtonRetrive.setEnabled(true);
		setOkEnabled(false);
//		mButtonVoid.setEnabled(false);
		mButtonRemarks.setEnabled(false);
		mButtonPrintReceipt.setEnabled(false);
		mButtonPrintReshito.setEnabled(false);
		mButtonPrintSO.setEnabled(false);
	}

	/**
	 * @param order
	 */
	private void populateOrderSummaryPanel(BeanOrderHeader order) {
		
		mLabelOrderNoValue.setText(PosOrderUtil.getShortOrderIDFromOrderID(order.getOrderId())); 
//		mLabelOrderNoValue.setText(PosEnvSettings.getInstance().getUISetting().useOrderQueueNo()?
//				 PosOrderUtil.getFormatedOrderQueueNo(Integer.valueOf(order.getQueueNo()), order.getOrderServiceType(), order.getServiceTable()):
//						PosOrderUtil.getShortOrderIDFromOrderID(order.getOrderId())); //+" ("+order.getStatus().name()+")");
		mLabelOrderStatus.setText(order.getStatus().name());
		Color statusForColor=PosOrderUtil.getOrderStatusColor(order.getStatus());
		mLabelOrderStatus.setForeground(statusForColor);
		if (order.getStatus()== PosOrderStatus.Open || order.getStatus() == PosOrderStatus.Partial){
			mLabelDateValue.setText(order.getOrderDate());
			mLabelTimeValue.setText(PosDateUtil.format(PosDateUtil.TIME_FORMAT_24, 
					PosDateUtil.parse(PosDateUtil.DATE_FORMAT_NOW_24,order.getOrderTime())));
		}else{
			mLabelDateValue.setText(order.getClosingDate());
			mLabelTimeValue.setText(PosDateUtil.format(PosDateUtil.TIME_FORMAT_24, 
					PosDateUtil.parse(PosDateUtil.DATE_FORMAT_NOW_24,order.getClosingTime())));
		}
		
		mLabelCustomerValue.setText(((order.getOrderCustomer()!=null)?order.getOrderCustomer().getName(): order.getCustomer().getName()));
		try {
			mLabelCashierValue.setText(order.getUser().getName());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		final double itemTotalAmt=PosOrderUtil.getTotalItemAmount( order);
		
//		mLabelItemSubTotalValue.setText(PosCurrencyUtil.format(order.getDetailTotal() + order.getTotalTax1() + 
//				order.getTotalTax2()+ order.getTotalTax3() + + order.getTotalServiceTax() + order.getTotalGST() ));
		mLabelItemSubTotalValue.setText(PosCurrencyUtil.format(order.getTotalAmount() +order.getTotalDetailDiscount() ));
		mLabelItemDiscountValue.setText(PosCurrencyUtil.format(order.getTotalDetailDiscount()));
		mLabelItemTotalValue.setText(PosCurrencyUtil.format(order.getTotalAmount()));
		
		 
	}
	

	/**
	 * @param order
	 */
	private void populateBillSummaryPanel(BeanOrderHeader order) {

	 	try {
		 
	 		
	 		final double totalAmtPaid= order.getTotalAmountPaid() -
					(order.getChangeAmount()+order.getCashOut())-
					order.getRoundAdjustmentAmount()  ;
			final double totalDisc= PosOrderUtil.getBillDiscount(order) ;
			final double dueAmount= PosOrderUtil.getTotalAmount(order) -totalAmtPaid -totalDisc;
			
			mLabelBillPartPayValue.setText(PosCurrencyUtil.format(totalAmtPaid));
			 
			mLabelBillTotalDiscValue.setText(PosCurrencyUtil.format(totalDisc));
			mLabelBillDueAmtValue.setText(PosCurrencyUtil.format(dueAmount));
			
			mLabelBillRoundingValue.setText(PosCurrencyUtil.format(order.getRoundAdjustmentAmount()));
	
		} catch (Exception e) {
			PosLog.write(this, "populatePaymentSummaryPanel", e);
			PosFormUtil.showErrorMessageBox(this,
					"Failed get payment information.");
		}
	}

	/**
	 * @param order
	 */
	private void populatePaymentSummaryPanel(BeanOrderHeader order) {

		PosOrderPaymentsProvider paymentProvider = new PosOrderPaymentsProvider();
		try {
			BeanPaymentSummary pymentSummary = paymentProvider
					.getPaymentSummary(order.getOrderId());
			mLabelCashValue.setText(PosCurrencyUtil.format(pymentSummary
					.getCashTotal()));
			mLabelCardValue.setText(PosCurrencyUtil.format(pymentSummary
					.getCardTotal()));
			mLabelCompanyValue.setText(PosCurrencyUtil.format(pymentSummary
					.getCompanyTotal()));
			mLabelVoucherValue.setText(PosCurrencyUtil.format(pymentSummary
					.getVoucherTotal()));
			
			mLabelDiscountValue.setText(PosCurrencyUtil.format(order
					.getBillDiscountAmount()));
			
			mLabelBalanceValue.setText(PosCurrencyUtil.format(order
					.getChangeAmount()));
		} catch (Exception e) {
			PosLog.write(this, "populatePaymentSummaryPanel", e);
			PosFormUtil.showErrorMessageBox(this,
					"Failed get payment information.");
		}
		 
	}

	/**
	 * @param order
	 */
	private void populateRefundSummaryPanel(BeanOrderHeader order) {

		PosOrderPaymentsProvider paymentProvider = new PosOrderPaymentsProvider();
		try {
			BeanPaymentSummary pymentSummary = paymentProvider
					.getRefundSummary(order.getOrderId());
			mLabelRefundCashValue.setText(PosCurrencyUtil
					.format(pymentSummary.getCashTotal()));
			mLabelRefundCardValue.setText(PosCurrencyUtil
					.format(pymentSummary.getCardTotal()));
			mLabelRefundCompanyValue.setText(PosCurrencyUtil
					.format(pymentSummary.getCompanyTotal()));
			mLabelRefundVoucherValue.setText(PosCurrencyUtil
					.format(pymentSummary.getVoucherTotal()));
		} catch (Exception e) {
			PosLog.write(this, "populatePaymentSummaryPanel", e);
			PosFormUtil.showErrorMessageBox(this,
					"Failed get payment information.");
		}
	}

	private void populateRefundPaymentModeContainerPanel(BeanOrderHeader order) {
//		mRefundPaymentModeContainerPanel.setOrder(order);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indocosmo.pos.forms.PosBaseForm#onOkButtonClicked()
	 */
	@Override
	public boolean onOkButtonClicked() {
	 
		return true;// super.onOkButtonClicked();
	}
	
	/* (non-Javadoc)
	 * @see java.awt.Dialog#setVisible(boolean)
	 */
	@Override
	public void setVisible(boolean b) {

		if(b){
			//mRetrieveButtonListner.onClicked(mButtonRetrive);
			super.setVisible(b);
		}else
			super.setVisible(b);
	}

}
