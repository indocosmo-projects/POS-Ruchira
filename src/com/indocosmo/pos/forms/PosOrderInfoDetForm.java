/**
 * 
 */
package com.indocosmo.pos.forms;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import com.indocosmo.barcode.reports.BarCodeLabelPrint;
import com.indocosmo.pos.common.PosEnvSettings;
import com.indocosmo.pos.common.PosLog;
import com.indocosmo.pos.common.enums.EnablePrintingOption;
import com.indocosmo.pos.common.enums.PosOrderServiceTypes;
import com.indocosmo.pos.common.enums.PosPrintOption;
import com.indocosmo.pos.common.utilities.PosCurrencyUtil;
import com.indocosmo.pos.common.utilities.PosDateUtil;
import com.indocosmo.pos.common.utilities.PosFormUtil;
import com.indocosmo.pos.common.utilities.PosOrderUtil;
import com.indocosmo.pos.common.utilities.PosPrintingUtil;
import com.indocosmo.pos.common.utilities.PosResUtil;
import com.indocosmo.pos.common.utilities.PosUomUtil;
import com.indocosmo.pos.data.beans.BeanOrderHeader;
import com.indocosmo.pos.data.beans.BeanOrderHeader.PosOrderStatus;
import com.indocosmo.pos.data.providers.shopdb.PosOrderHdrProvider;
import com.indocosmo.pos.data.providers.shopdb.PosOrderServingTableProvider;
import com.indocosmo.pos.data.providers.shopdb.PosUOMProvider;
import com.indocosmo.pos.forms.components.PosOrderItemDetTaxExclusiveTablePanel;
import com.indocosmo.pos.forms.components.buttons.PosButton;
import com.indocosmo.pos.forms.components.buttons.PosButton.ButtonStyle;
import com.indocosmo.pos.forms.components.buttons.listners.IPosButtonListner;
import com.indocosmo.pos.forms.components.objectbrowser.IPosBrowsableItem;
import com.indocosmo.pos.forms.components.textfields.PosTouchableFieldBase;
import com.indocosmo.pos.forms.listners.IPosBillPrintFormListner;
import com.indocosmo.pos.forms.listners.adapters.PosBillPrintFormAdapter;
import com.indocosmo.pos.forms.messageboxes.PosMessageBoxForm.MessageBoxButtonTypes;
import com.indocosmo.pos.forms.messageboxes.PosMessageBoxForm.MessageBoxResults;
import com.indocosmo.pos.reports.receipts.PosReceipts;
import com.indocosmo.pos.terminal.devices.PosDeviceManager;
import com.indocosmo.pos.terminal.devices.printer.printerexceptions.PrinterException;

/**
 * @author jojesh
 * 
 */
@SuppressWarnings("serial")
public final class PosOrderInfoDetForm extends PosBaseForm {

	protected static final int MAIN_PANEL_CONTENT_H_GAP = 1;
	protected static final int MAIN_PANEL_CONTENT_V_GAP = 1;
	
	protected static final int PANEL_CONTENT_H_GAP = 2;
	protected static final int PANEL_CONTENT_V_GAP = 2;
	
	private static final int ITEM_PANEL_H_GAP = 1;

	private static final int ITEM_ROWS = 4;
	private static final int ITEM_COLS = 3;

	private static final int ITEM_TITLE_WIDTH =120;
	private static final int ITEM_TEXT_WIDTH = 200;
	
	
	private static final int ITEM_TITLE_WIDTH_SMALL =140;
	private static final int ITEM_TEXT_WIDTH_SMALL = 150;
	
	private static final int ITEM_HEIGHT = PosTouchableFieldBase.LAYOUT_DEF_HEIGHT;

	private static final int  BROWSE_BUTTON_WIDTH=40;
	
	private static final int ITEM_PANEL_HIEGHT = ITEM_HEIGHT;
	private static final int ITEM_PANEL_WIDTH = (ITEM_TITLE_WIDTH + ITEM_TEXT_WIDTH)
			+ PANEL_CONTENT_H_GAP * 3;
	
	private static final int ITEM_PANEL_WIDTH_SMALL = (ITEM_TITLE_WIDTH_SMALL + ITEM_TEXT_WIDTH_SMALL)
			+ PANEL_CONTENT_H_GAP * 3;
	private static final int DTL_LIST_PANEL_HIEGHT = 352;
	
	private static final int LAYOUT_HEIGHT = ITEM_PANEL_HIEGHT * ITEM_ROWS
			+ PANEL_CONTENT_V_GAP * (ITEM_ROWS + 1) + PANEL_BORDER_WIDTH * 2;
	private static final int LAYOUT_WIDTH = ITEM_PANEL_WIDTH  + ITEM_PANEL_WIDTH_SMALL * 2
			+ PANEL_CONTENT_H_GAP * (ITEM_COLS + 1) + PANEL_BORDER_WIDTH * 2;
	
 	private JTextField mTextRefNumber;
	private JTextField mTextOrderDateTime;
	
	private JTextField mTextExtraCharge;
	private JTextField mTextTotalItems;
	private JTextField mTextTotalAmount;
	private JTextField mTextDueAmount;
	private JTextField mTextRefundAmount;

	private JTextField mTextCustomer;
	private JTextField mTextServedBy;
	private JTextField mTextService;
		
	private JTextField mTextDiscAmount;
	private JTextField mTextNetSale;
	private JTextField mTextTaxAmount;
	
	private PosButton mButtonPrint;
	private BeanOrderHeader mPosOrderHObject;
	 
	private JPanel mContentPanel;

	private PosOrderItemDetTaxExclusiveTablePanel orderDetTablePanel;
	private ArrayList<IPosBrowsableItem> printButtonList;
	
	/**
	 * @param title
	 * @param cPanelwidth
	 * @param cPanelHeight
	 */
	public PosOrderInfoDetForm( String orderId) {
		
		super("Order Details", LAYOUT_WIDTH, LAYOUT_HEIGHT+DTL_LIST_PANEL_HIEGHT + PANEL_CONTENT_V_GAP*3);
//		mPosOrderHObject = orderHObject;
		try {
			
			buildPrintButton();
			populateOrderDetails(orderId);
			
			createUI();
			loadUI();
				
		} catch (Exception e) {
			PosLog.write(this, "PosOrderInfoForm", e);
			PosFormUtil.showSystemError(this);
		}
		
		setResetButtonVisible(false);
	}
	
	/*
	 * 
	 */
	private void populateOrderDetails(String orderID){
		
		try {
			
			final String orderHeaderID=orderID;
			PosOrderHdrProvider orderHdrProvider=new PosOrderHdrProvider();
			mPosOrderHObject = orderHdrProvider.getOrderData(orderHeaderID);
			
			
		} catch (Exception e) {
			PosLog.write(this, "populateOrderDetails", e);
			PosFormUtil.showErrorMessageBox(this, "Failed to load the order details.");
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

	
	private void createDetailTableUI(){
		
		int orderDtlTableHeight=DTL_LIST_PANEL_HIEGHT-PANEL_CONTENT_V_GAP *2;
		orderDetTablePanel  =new PosOrderItemDetTaxExclusiveTablePanel(mContentPanel ,LAYOUT_WIDTH-PANEL_CONTENT_H_GAP*4,orderDtlTableHeight  );
		orderDetTablePanel.SetOrderDetailList(mPosOrderHObject.getOrderDetailItems());
		mContentPanel.add(orderDetTablePanel);

	}
	/***
	 * 
	 */
	private void createUI() {
		
		JPanel mLeftPanel =new JPanel();
		mLeftPanel.setLayout(new FlowLayout(FlowLayout.LEFT,PANEL_CONTENT_H_GAP,PANEL_CONTENT_V_GAP));
		mLeftPanel.setPreferredSize(new Dimension(ITEM_PANEL_WIDTH, LAYOUT_HEIGHT));
		mContentPanel.add( mLeftPanel);
		
		JPanel mMiddlePanel =new JPanel();
		mMiddlePanel.setLayout(new FlowLayout(FlowLayout.LEFT,PANEL_CONTENT_H_GAP,PANEL_CONTENT_V_GAP));
		mMiddlePanel.setPreferredSize(new Dimension(ITEM_PANEL_WIDTH_SMALL, LAYOUT_HEIGHT));
		mContentPanel.add( mMiddlePanel);
		
		JPanel mRightPanel =new JPanel();
		mRightPanel.setLayout(new FlowLayout(FlowLayout.LEFT,PANEL_CONTENT_H_GAP,PANEL_CONTENT_V_GAP));
		mRightPanel.setPreferredSize(new Dimension(ITEM_PANEL_WIDTH_SMALL, LAYOUT_HEIGHT));
		mContentPanel.add( mRightPanel);
		
		createDetailTableUI();
		
		mTextRefNumber = crateField(mLeftPanel, "Ref# :",ITEM_PANEL_WIDTH,ITEM_TEXT_WIDTH,ITEM_TITLE_WIDTH);
		mTextOrderDateTime = crateField(mLeftPanel, "Date :",ITEM_PANEL_WIDTH,ITEM_TEXT_WIDTH,ITEM_TITLE_WIDTH);
		mTextService =  crateField(mLeftPanel, "Service :",ITEM_PANEL_WIDTH,ITEM_TEXT_WIDTH,ITEM_TITLE_WIDTH); 
		mTextCustomer =  crateField(mLeftPanel, "Customer :",ITEM_PANEL_WIDTH,ITEM_TEXT_WIDTH,ITEM_TITLE_WIDTH);  
		
		
		mTextServedBy = crateField(mMiddlePanel, "Server By :",ITEM_PANEL_WIDTH_SMALL,ITEM_TEXT_WIDTH_SMALL,ITEM_TITLE_WIDTH_SMALL);
		mTextDiscAmount = crateField(mMiddlePanel, "Discount :",ITEM_PANEL_WIDTH_SMALL,ITEM_TEXT_WIDTH_SMALL,ITEM_TITLE_WIDTH_SMALL);
		mTextNetSale = crateField(mMiddlePanel, "Net Sale :",ITEM_PANEL_WIDTH_SMALL,ITEM_TEXT_WIDTH_SMALL,ITEM_TITLE_WIDTH_SMALL); 
		mTextTaxAmount = crateField(mMiddlePanel, "Tax :",ITEM_PANEL_WIDTH_SMALL,ITEM_TEXT_WIDTH_SMALL,ITEM_TITLE_WIDTH_SMALL); 
		
		mTextTotalItems = crateField(mRightPanel, "Quantity :",ITEM_PANEL_WIDTH_SMALL,ITEM_TEXT_WIDTH_SMALL,ITEM_TITLE_WIDTH_SMALL);
		mTextExtraCharge = crateField(mRightPanel, "Add. Charge :",ITEM_PANEL_WIDTH_SMALL,ITEM_TEXT_WIDTH_SMALL,ITEM_TITLE_WIDTH_SMALL);
		mTextTotalAmount = crateField(mRightPanel, "Total Amount :",ITEM_PANEL_WIDTH_SMALL,ITEM_TEXT_WIDTH_SMALL,ITEM_TITLE_WIDTH_SMALL);
		mTextDueAmount = createDueAmtField(mRightPanel, "Due Amount :",ITEM_PANEL_WIDTH_SMALL,ITEM_TEXT_WIDTH_SMALL,ITEM_TITLE_WIDTH_SMALL);
		mTextRefundAmount = crateField(mRightPanel, "Refund :",ITEM_PANEL_WIDTH_SMALL,ITEM_TEXT_WIDTH_SMALL,ITEM_TITLE_WIDTH_SMALL,mPosOrderHObject.getStatus().equals(PosOrderStatus.Refunded));

	 
		mTextDiscAmount.setHorizontalAlignment(SwingConstants.RIGHT);
		mTextNetSale.setHorizontalAlignment(SwingConstants.RIGHT); 
		mTextTaxAmount.setHorizontalAlignment(SwingConstants.RIGHT); 
		mTextTotalItems.setHorizontalAlignment(SwingConstants.RIGHT);
		mTextExtraCharge.setHorizontalAlignment(SwingConstants.RIGHT); 
		mTextTotalAmount.setHorizontalAlignment(SwingConstants.RIGHT); 
		mTextDueAmount.setHorizontalAlignment(SwingConstants.RIGHT); 
		mTextRefundAmount.setHorizontalAlignment(SwingConstants.RIGHT); 
	}
 
 
 

	/***
	 * 
	 * @param title
	 * @return
	 */
	private JPanel creatFieldPanelWithTitle(String title,int panel_width,int title_width) {
		JPanel itemPanel = new JPanel(); 
		itemPanel
		.setLayout(new FlowLayout(FlowLayout.LEFT, ITEM_PANEL_H_GAP, 0));
		itemPanel.setPreferredSize(new Dimension(panel_width,
				ITEM_PANEL_HIEGHT));

		JLabel lable = new JLabel(title);
		lable.setPreferredSize(new Dimension(title_width, ITEM_HEIGHT));
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
	private JTextField crateField(JPanel panel, String title,int panel_width,int text_width,int title_width) {
		
		 

		return crateField(panel,title,panel_width,text_width,title_width,true);
	}
	
	/**
	 * 
	 * @param panel
	 * @param title
	 * @return
	 */
	private JTextField crateField(JPanel panel, String title,int panel_width,int text_width,int title_width, boolean isVisible) {
		
		JPanel itemPanel = creatFieldPanelWithTitle(title,panel_width,title_width);
		if(isVisible)
			panel.add(itemPanel);

		JTextField text = new JTextField("");
		text.setPreferredSize(new Dimension(text_width, ITEM_HEIGHT));
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
	private JTextField createDueAmtField(JPanel panel, String title,int panel_width,int text_width,int title_width) {
		
		JPanel itemPanel = creatFieldPanelWithTitle(title,panel_width,title_width);
		if(!mPosOrderHObject.getStatus().equals(PosOrderStatus.Refunded))
			panel.add(itemPanel);

		JTextField text = new JTextField("");
		text.setPreferredSize(new Dimension(text_width-BROWSE_BUTTON_WIDTH , ITEM_HEIGHT));
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
					PosFormUtil.showLightBoxModal(PosOrderInfoDetForm.this, form);
					 
				} catch (Exception e) {
					
					PosLog.write(PosOrderInfoDetForm.this, "btnRetrieve.onClicked", e);
					PosFormUtil.showErrorMessageBox(PosOrderInfoDetForm.this, "Failed to load details. Please check log.");
				}
				
			}
		});
		itemPanel.add(btnRetrieve);
		return text;
	}
	
	 
	
	/**
	 * 
	 */
	private void  buildPrintButton (){
		
		mButtonPrint = new PosButton();
		mButtonPrint.setText("Print");
		mButtonPrint.setImage(PosResUtil
				.getImageIconFromResource(IMAGE_BUTTON_OK));
		mButtonPrint.setTouchedImage(PosResUtil
				.getImageIconFromResource(IMAGE_BUTTON_OK_TOUCH));
		mButtonPrint.setHorizontalAlignment(SwingConstants.CENTER);
		mButtonPrint.setSize(IMAGE_BUTTON_WIDTH, IMAGE_BUTTON_HEIGHT);
		mButtonPrint.setOnClickListner( mPrintButtonListner); 
		mButtonPrint.setEnabled(false);
		addButtonsToBottomPanel(mButtonPrint,1);
		
		printButtonList=PosPrintingUtil.buildPrintOptions();
		mButtonPrint.setEnabled(printButtonList.size()>0);
	}
	
	
 
	/***
	 * popualte the data from object
	 */
	private void loadUI() {

		
		final String refNo=(mPosOrderHObject.getStatus().equals(PosOrderStatus.Closed) ||
				mPosOrderHObject.getStatus().equals(PosOrderStatus.Refunded))?
					 mPosOrderHObject.getInvoiceNo(): 
				PosOrderUtil.getFormattedReferenceNo(mPosOrderHObject);			
		
		
		final String dateTime=(mPosOrderHObject.getStatus().equals(PosOrderStatus.Closed) ||
				mPosOrderHObject.getStatus().equals(PosOrderStatus.Refunded))?
					 mPosOrderHObject.getClosingTime():
					 mPosOrderHObject.getOrderTime();			
		
		String serviceName=mPosOrderHObject.getOrderServiceType().getDisplayText(); 
		 
		if(mPosOrderHObject.getServiceTable()!=null && 
				!mPosOrderHObject.getServiceTable().getCode().equals(PosOrderServingTableProvider.CODE_SYS_TABLE_NA))
			serviceName= serviceName + "[" + mPosOrderHObject.getServiceTable().getName() + "]";
		
		mTextRefNumber.setText(refNo + "[" + mPosOrderHObject.getStatus().getDisplayText() + "]");
		mTextOrderDateTime.setText(PosDateUtil.format(PosDateUtil.SHORT_DATE_TIME_FORMAT,dateTime));
		mTextService.setText(serviceName);
		mTextCustomer.setText( mPosOrderHObject.getCustomer().getName());


		final String servedBy = (mPosOrderHObject.getClosedBy()==null)?
				mPosOrderHObject.getUser().getName():
				mPosOrderHObject.getClosedBy().getName();

		mTextServedBy.setText(servedBy);
		
		mTextTotalItems.setText(PosUomUtil.format(mPosOrderHObject.getDetailQuatity(),PosUOMProvider.getInstance().getMaxDecUom()));
		
		mTextExtraCharge.setText(PosCurrencyUtil.format(mPosOrderHObject.getExtraCharges() + 
				mPosOrderHObject.getExtraChargeTaxOneAmount() + mPosOrderHObject.getExtraChargeTaxTwoAmount() +
				mPosOrderHObject.getExtraChargeTaxThreeAmount() + mPosOrderHObject.getExtraChargeGSTAmount()));
		
		final double totalAmt=PosOrderUtil.getTotalAmount(mPosOrderHObject);
		final double totalAmtPaid= mPosOrderHObject.getTotalAmountPaid() -
				(mPosOrderHObject.getChangeAmount()+mPosOrderHObject.getCashOut())-
				mPosOrderHObject.getRoundAdjustmentAmount()  ;
		final double totalDiscount=PosOrderUtil.getBillDiscount(mPosOrderHObject);
	
 		final double  dueAmount=  totalAmt-(totalAmtPaid+totalDiscount);
 		
		mTextTotalAmount.setText(PosCurrencyUtil.format(PosCurrencyUtil
				.roundTo(totalAmt + mPosOrderHObject.getRoundAdjustmentAmount()-totalDiscount)));
		 

	
		 mTextDueAmount.setText(PosCurrencyUtil.format(dueAmount));
		 
		 mTextDiscAmount.setText(PosCurrencyUtil.format(totalDiscount));
		 
		 final double billTax =mPosOrderHObject.getTotalTax1() + mPosOrderHObject.getTotalTax2() + 
				 mPosOrderHObject.getTotalTax3() + mPosOrderHObject.getTotalGST() +mPosOrderHObject.getTotalServiceTax() ;
		 final double totalRefundTax=(mPosOrderHObject.getRefundTotalAmountTax1()+mPosOrderHObject.getRefundTotalAmountTax2()+
				 mPosOrderHObject.getRefundTotalAmountTax3() + mPosOrderHObject.getRefundTotalAmountGST());
			 ;
		final double billTotalAfterDisc=totalAmt-PosOrderUtil.getDiscountForPaymentReceipt(mPosOrderHObject, totalAmt);
		final double billTaxAfterDisc=billTax-PosOrderUtil.getDiscountForPaymentReceipt(mPosOrderHObject, billTax);
		
		 final double netSale=(billTotalAfterDisc -billTaxAfterDisc) - (mPosOrderHObject.getRefundAmount()-totalRefundTax) ;
		 final double totalTax=billTaxAfterDisc-totalRefundTax;
		 
		 mTextNetSale.setText(PosCurrencyUtil.format(netSale));
		 mTextTaxAmount.setText(PosCurrencyUtil.format(totalTax));

		 if (mPosOrderHObject.getStatus().equals(PosOrderStatus.Refunded))
			 mTextRefundAmount.setText(PosCurrencyUtil.format(mPosOrderHObject.getRefundAmount()));
		 
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indocosmo.pos.forms.PosBaseForm#onCancelButtonClicked()
	 */
	@Override
	public boolean onCancelButtonClicked() {
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

		return !isCancelled;
	}

	
	/**
	 * 
	 */
	private IPosButtonListner mPrintButtonListner = new IPosButtonListner() {
		@Override
		public void  onClicked(PosButton button) {
			
		 PosPrintingUtil.showPrintOptions(PosOrderInfoDetForm.this, billPrintListner, printButtonList);
		}
	};
	
	 
 
	
	/*
	 * 
	 */
	private boolean printOrder( ) {
		
		try{
			
 
			
			final boolean forceToPrint=(PosEnvSettings.getInstance().getPrintSettings().getReceiptPrintingAtPayment()!=EnablePrintingOption.ASK);
			
			if (mPosOrderHObject.getStatus().equals(PosOrderStatus.Refunded) || mPosOrderHObject.getStatus().equals(PosOrderStatus.Closed)) {
				PosFormUtil.showPrintConfirmMessage(PosOrderInfoDetForm.this, forceToPrint, mPosOrderHObject, false, null, false);
			}else{
//				PosReceipts.printReceipt(PosOrderListQueryForm.this,orderHeader, false, null,true, useAltLang,true);
				PosFormUtil.showPrintConfirmMessage(PosOrderInfoDetForm.this, forceToPrint, mPosOrderHObject, true, null, true);
			}
			
			if (mPosOrderHObject.getStatus().equals(PosOrderStatus.Refunded)){

				boolean useAltLangToPrint=false;
				if(PosDeviceManager.getInstance().hasReceiptPrinter()){

					if(PosDeviceManager.getInstance().getReceiptPrinter()!=null ){

						if(PosDeviceManager.getInstance().getReceiptPrinter().isUseAltLanguage()){
							final MessageBoxResults res= PosFormUtil.showQuestionMessageBox(this,
									 MessageBoxButtonTypes.YesNo ,
									"Do you want to print receipt using alternatieve language?",null);

								switch(res){
			
								case Yes:
									useAltLangToPrint=true;
									break;
								 	case No:
									useAltLangToPrint=false;
									break;
								default:
									break;
			
									}
							}
					}
					
				}
				
				PosOrderUtil.printRefundReceipt(PosOrderInfoDetForm.this, mPosOrderHObject,useAltLangToPrint);
			}
			
		} catch (PrinterException e) {
			
			PosFormUtil.showErrorMessageBox(PosOrderInfoDetForm.this,e.getMessage());
			return false;
		} catch (Exception e) {
			
			PosLog.write(this, "Print ", e);
			PosFormUtil.showErrorMessageBox(this,
					"Failed to print. Please check the log for details");
			return false;
		}
		return true;
	}
 

	/*
	 * 
	 */
	private boolean validatePrint( PosPrintOption printOption){
	 
		if(printOption==PosPrintOption.RESHITO   ) {
		
			
			if (!(mPosOrderHObject.getStatus().equals(PosOrderStatus.Refunded)  ||
					mPosOrderHObject.getStatus().equals(PosOrderStatus.Closed))){
				
				PosFormUtil.showErrorMessageBox(this, "There is no payment done for this order. Reshito can be printed only for  closed orders.");
				return false;
			}
		} else if(printOption==PosPrintOption.ITEMLABEL    && 
				mPosOrderHObject.getOrderServiceType()!=PosOrderServiceTypes.SALES_ORDER) {
				
				PosFormUtil.showErrorMessageBox(this, "This feature is available for only sales order service.");
				return false;
			}
			
		 
		return true;
		
	}
	
	
	
	/*
	 * 
	 */
	private IPosBillPrintFormListner billPrintListner =new PosBillPrintFormAdapter(){
		
		@Override
		public void onRePrintClicked(Object sender) {
			printOrder();
		}
		
		@Override
		public void onRePrintKitchenReceiptClicked(Object sender){
			printKitchenReceipt();
			 
		}
		
		@Override
		public void onRePrintBarcodeClicked(Object sender) {
			printBarcode();
		}
		
		@Override
		public void onRePrintReshitoClicked(Object sender) {
			printBillReshito();
		}
		
		public void onRePrintItemLabelClicked(Object sender) {
			printItemLabel();
		}
		
	};
 
	/*
	 * 
	 */
	private void printBillReshito() {
		
		try{
			if (!validatePrint(PosPrintOption.ITEMLABEL))
				return ;
			if (mPosOrderHObject.getStatus().equals(PosOrderStatus.Refunded) || mPosOrderHObject.getStatus().equals(PosOrderStatus.Closed)){
				
				PosReceipts.printBillReshito(mPosOrderHObject,false);
			}

		} catch (PrinterException e) {
			
			PosFormUtil.showErrorMessageBox(this,e.getMessage());
		} catch (Exception e) {
			
			PosLog.write(this, "Print Reshito", e);
			PosFormUtil.showErrorMessageBox(this, "Failed to print. Please check the log for details");
		}
	}
	/*
	 * 
	 */
	private void printItemLabel() {
		
		try{
			
			if (!validatePrint(PosPrintOption.ITEMLABEL))
				return ;
			PosReceipts.printItemLabels(mPosOrderHObject);

		 } catch (Exception e) {
				PosLog.write(this, "Print Item Label", e);
				PosFormUtil.showErrorMessageBox(this,"Failed to print. Please contact administrator.");
		}
	}
	
	/*
	 * 
	 */
	private void printKitchenReceipt() {
		
		
		try {
				
			if( mPosOrderHObject.getOrderDetailItems()==null || mPosOrderHObject.getOrderDetailItems().size()==0)
					return ;

				if (!PosOrderUtil.hasPrintableItems(mPosOrderHObject,  true)){
					
					PosFormUtil.showInformationMessageBox(this, "No Items to print." );
					return ;
				}
				try {
	
					if (mPosOrderHObject.getStatus()==PosOrderStatus.Open || mPosOrderHObject.getStatus()==PosOrderStatus.Partial){
						
						final MessageBoxResults msgBoxResult=PosFormUtil.showQuestionMessageBox(this, MessageBoxButtonTypes.YesNoCancel,"Print new/modified item[s] only?", null);
						if(msgBoxResult!=MessageBoxResults.Cancel){
							
							if(msgBoxResult==MessageBoxResults.Yes && !PosOrderUtil.hasNotPrintedToKitchenItems(mPosOrderHObject)){
								
								PosFormUtil.showInformationMessageBox(this, "Already printed to kitchen(s)." );
							
							}else{
								
								final PosOrderHdrProvider pvdr=new PosOrderHdrProvider();
								PosReceipts.printReceiptToKitchen(mPosOrderHObject, msgBoxResult==MessageBoxResults.No);
								PosOrderUtil.setAsPrintedToKitchen(mPosOrderHObject);
								pvdr.saveOrder(mPosOrderHObject);
	
							 
							}
						}
					}else
						PosReceipts.printReceiptToKitchen(mPosOrderHObject, true);
						
				} catch (Exception e) {
					PosLog.write(this, "printKitchen Recipt", e);
					PosFormUtil.showErrorMessageBox(this, "Error in printing!!"); 
					return ;
				}
					
		} catch (Exception e) {
			PosLog.write(this, "Kitchen Print", e);
			PosFormUtil.showErrorMessageBox(this,"Failed to print. Please contact administrator.");
		}
	}
	
	/*
	 * 
	 */
	private void printBarcode() {
		
		try{
			
			final BarCodeLabelPrint bcLabelPrint=new BarCodeLabelPrint();
			bcLabelPrint.print(mPosOrderHObject);

		 } catch (Exception e) {
				PosLog.write(this, "Print Barcode", e);
				PosFormUtil.showErrorMessageBox(this,"Failed to print. Please contact administrator.");
		}
	}
	
}
