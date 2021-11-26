/**
 * 
 */
package com.indocosmo.pos.forms;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.RootPaneContainer;
import javax.swing.SwingWorker;
import javax.swing.border.EtchedBorder;

import com.indocosmo.pos.common.PosEnvSettings;
import com.indocosmo.pos.common.PosLog;
import com.indocosmo.pos.common.utilities.PosCurrencyUtil;
import com.indocosmo.pos.common.utilities.PosDateUtil;
import com.indocosmo.pos.common.utilities.PosFormUtil;
import com.indocosmo.pos.common.utilities.PosOrderUtil;
import com.indocosmo.pos.data.beans.BeanOrderHeader;
import com.indocosmo.pos.data.beans.BeanOrderHeader.PosOrderStatus;
import com.indocosmo.pos.data.beans.BeanOrderPayment;
import com.indocosmo.pos.data.beans.BeanPayModeBase.PaymentMode;
import com.indocosmo.pos.data.providers.shopdb.PosCardPaymentRecoveryProvider;
import com.indocosmo.pos.data.providers.shopdb.PosOrderHdrProvider;
import com.indocosmo.pos.forms.components.textfields.PosTouchableFieldBase;
import com.indocosmo.pos.forms.listners.IPosBillPrintFormListner;
import com.indocosmo.pos.forms.listners.IPosCardTxnDetailsFormListner;
import com.indocosmo.pos.forms.listners.IPosMessageBoxFormCancelListener;
import com.indocosmo.pos.forms.listners.adapters.PosBillPrintFormAdapter;
import com.indocosmo.pos.forms.messageboxes.PosMessageBoxForm;
import com.indocosmo.pos.forms.messageboxes.PosMessageBoxForm.MessageBoxButtonTypes;
import com.indocosmo.pos.forms.messageboxes.listners.PosMessageBoxFormListnerAdapter;
import com.indocosmo.pos.process.sync.SynchronizeToServer;
import com.indocosmo.pos.terminal.devices.PosDeviceManager;
import com.indocosmo.pos.terminal.devices.eftpos.PosDeviceEFT;
import com.indocosmo.pos.terminal.devices.eftpos.PosDeviceEFTConstants;
import com.indocosmo.pos.terminal.devices.eftpos.enums.EFTPurchaseStatus;
import com.indocosmo.pos.terminal.devices.eftpos.messageobjects.EFTResponseMessagePUR;
import com.indocosmo.pos.terminal.devices.poledisplay.PosDevicePoleDisplay;
import com.indocosmo.pos.terminal.devices.printer.PosDeviceReceiptPrinter;

/**
 * @author deepak
 *
 */
public class PosCardPaymentRecoveryForm extends PosBaseForm{
	
	private static final int PANEL_HEIGHT = 200;
	private static final int PANEL_WIDTH = 550;
	
	private static final int PANEL_CONTENT_H_GAP=2;
	private static final int PANEL_CONTENT_V_GAP=2;
	
	protected static final int TEXT_FIELD_WIDTH=330;
	protected static final int TEXT_FIELD_HEIGHT=PosTouchableFieldBase.LAYOUT_DEF_HEIGHT;
	protected static final int LABEL_WIDTH=200;
	protected static final int LABEL_HEIGHT=PosTouchableFieldBase.LAYOUT_DEF_HEIGHT;
	protected static final Color LABEL_BG_COLOR=new Color(78,128,188);
	protected static final int MESSAGE_PANEL_HEIGHT=30;
	
	private JPanel mContentPanel;
	private JTextField mTxtOrderNo;
	private JTextField mTxtOrderDate;
	private JTextField mTxtTotalAmount;
	private PosOrderHdrProvider mOrderHdrProvider; 
	private BeanOrderHeader mOrderHeaderObject;
	private PosCardPaymentRecoveryProvider mCardPaymentRecoveryProvider;
	private boolean reTry;
//	private Boolean mPrintRecipt = false;
	private EFTResponseMessagePUR purchaseMessageOnManualEntry = null;
	private JTextField mTxtCashOutAmount;
	private PosDevicePoleDisplay mPosPoleDisplay;
	private boolean mStatus;
	private String strPurchaseAmount="0";
	private String strCashAmount="0";
	private boolean purchaseCanceled = false;
	/**
	 * 
	 */
	public PosCardPaymentRecoveryForm() {
         super("Card Payment Recovery", PANEL_WIDTH, PANEL_HEIGHT);
         setCancelButtonVisible(false);
         initRecoveryUIControls();
         setValues();
         mPosPoleDisplay = PosDevicePoleDisplay.getInstance();
	}
	/**
	 * Set the field values from the database.
	 * 
	 */
	private void setValues() {
		 mOrderHdrProvider = new PosOrderHdrProvider();
		 mOrderHeaderObject = mOrderHdrProvider.getProcessingOrder();
		 
		 if(mOrderHeaderObject!=null){
			 mTxtOrderNo.setText(mOrderHeaderObject.getOrderId());
			 mTxtOrderDate.setText(mOrderHeaderObject.getOrderDate());
			 mTxtTotalAmount.setText(PosCurrencyUtil.format(PosOrderUtil.getTotalAmount(mOrderHeaderObject)));
		 }
	}
	/**
	 * 
	 */
	private void initRecoveryUIControls() {
		
		setOrderNo();
		setOrderDate();
		TotalAmount();
	}
	
	private void setOrderNo() {
		JLabel label = new JLabel(" Order No :");
		label.setPreferredSize(new Dimension(LABEL_WIDTH, LABEL_HEIGHT));
		setLabelFont(label);
		mContentPanel.add(label);

		mTxtOrderNo = new JTextField();
		mTxtOrderNo.setFont(PosFormUtil.getTextFieldFont());
		mTxtOrderNo.setPreferredSize(new Dimension(TEXT_FIELD_WIDTH,
				TEXT_FIELD_HEIGHT));
		mTxtOrderNo.setEditable(false);
		mContentPanel.add(mTxtOrderNo);

	}

	/**
	 * 
	 */
	private void setOrderDate() {
		JLabel label = new JLabel(" Order Date :");
		label.setPreferredSize(new Dimension(LABEL_WIDTH, LABEL_HEIGHT));
		setLabelFont(label);
		mContentPanel.add(label);

		mTxtOrderDate = new JTextField();
		mTxtOrderDate.setFont(PosFormUtil.getTextFieldFont());
		mTxtOrderDate.setPreferredSize(new Dimension(TEXT_FIELD_WIDTH,
				TEXT_FIELD_HEIGHT));
		mTxtOrderDate.setEditable(false);
		mContentPanel.add(mTxtOrderDate);
	}

	/**
	 * 
	 */
	private void TotalAmount() {
		JLabel label = new JLabel(" Bill Amount:");
		label.setPreferredSize(new Dimension(LABEL_WIDTH, LABEL_HEIGHT));
		setLabelFont(label);
		mContentPanel.add(label);

		mTxtTotalAmount = new JTextField();
		mTxtTotalAmount.setFont(PosFormUtil.getTextFieldFont());
		mTxtTotalAmount.setPreferredSize(new Dimension(TEXT_FIELD_WIDTH,
				TEXT_FIELD_HEIGHT));
		mTxtTotalAmount.setEditable(false);
		mContentPanel.add(mTxtTotalAmount);

	}
	
	private void CashOutAmount() {
		JLabel label = new JLabel(" CashOut Amount:");
		label.setPreferredSize(new Dimension(LABEL_WIDTH, LABEL_HEIGHT));
		setLabelFont(label);
		mContentPanel.add(label);

		mTxtCashOutAmount = new JTextField();
		mTxtCashOutAmount.setFont(PosFormUtil.getTextFieldFont());
		mTxtCashOutAmount.setPreferredSize(new Dimension(TEXT_FIELD_WIDTH,
				TEXT_FIELD_HEIGHT));
		mTxtCashOutAmount.setEditable(false);
		mContentPanel.add(mTxtCashOutAmount);

	}
	
	protected void setLabelFont(JLabel label){
		Font newLabelFont=new Font(label.getFont().getName(),label.getFont().getStyle(),16);  
		label.setFont(newLabelFont);  		
	}
	/* (non-Javadoc)
	 * @see com.indocosmo.pos.forms.PosBaseForm#setContentPanel(javax.swing.JPanel)
	 */
	@Override
	protected void setContentPanel(JPanel panel) {

		mContentPanel = panel;
		mContentPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 0,
				PANEL_CONTENT_V_GAP));
		mContentPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
		
	}
	
	private  void setPurchaseAmounts(String eftPurchaseMsg) {
		final String[] msgParts=eftPurchaseMsg.split(",",100);
		strPurchaseAmount = msgParts[PosDeviceEFTConstants.PUR_CARD_AMO_PART];
		strCashAmount = msgParts[PosDeviceEFTConstants.PUR_CASH_AMO_PART];
	}
	
	/* (non-Javadoc)
	 * @see com.indocosmo.pos.forms.PosBaseForm#onOkButtonClicked()
	 */
	@Override
	public boolean onOkButtonClicked() {
		
		boolean status = false;
		final PosMessageBoxForm msgForm =PosFormUtil.showCancelOnlyMessageBox(this, "Communicating with EFTPOS. Please wait...", null);
		msgForm.setCancelOnlyListner(new IPosMessageBoxFormCancelListener() {
			
			@Override
			public boolean onCancelButtonPressed() {
				boolean status =false;
				if(PosDeviceEFT.getInstance().cancelTxn()){
					purchaseCanceled = true;
					msgForm.setVisible(false);
					msgForm.dispose();
					status =true;
				}
				return status;	
			}
		});
		mCardPaymentRecoveryProvider = new PosCardPaymentRecoveryProvider();
		final String eftPurchaseMessage = mCardPaymentRecoveryProvider.getEftPurchaseMessage();
		setPurchaseAmounts(eftPurchaseMessage);
		try {
			
			
			SwingWorker<Boolean, String> swt = new SwingWorker<Boolean, String>() {
				@Override
				protected Boolean doInBackground() throws Exception {
					try {
						EFTResponseMessagePUR purchaseMessage = doReTryPayment(eftPurchaseMessage,msgForm);
						setPurchaseMessage(purchaseMessage);
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
			PosFormUtil.showLightBoxModal(this, msgForm);
			
			EFTResponseMessagePUR purchaseMessage = getPurchaseMessage();
			setPurchaseMessage(null);
			if (purchaseMessage == null) {
				
				if(purchaseMessageOnManualEntry!=null){
					updatePayment(purchaseMessageOnManualEntry);
					purchaseMessageOnManualEntry = null;
					mCardPaymentRecoveryProvider.deleteEftPurchaseMessage();
					status = true;
				}else if(purchaseCanceled){
					removePayment();
					mCardPaymentRecoveryProvider.deleteEftPurchaseMessage();
					status = true;
					purchaseCanceled = false;
					throw new reTryPaymentException(
							"The card payment has been CANCELED. Reason TRANS. CANCELLED");
				}else{
					removePayment();
					mCardPaymentRecoveryProvider.deleteEftPurchaseMessage();
					status = true;
					throw new reTryPaymentException("The card payment has been CANCELED. Reason TRANS. CANCELLED");
				}
			} else if (purchaseMessage.getStatus() == EFTPurchaseStatus.ACCEPTED) {
				updatePayment(purchaseMessage);
				status = true;
			} else {
				removePayment();
				throw new reTryPaymentException("The card payment has been "
						+ purchaseMessage.getStatus() + ". Reason "
						+ purchaseMessage.getDisplayMessage());
			}
		} catch (reTryPaymentException e) {
			status = true;
			PosFormUtil.showErrorMessageBox(this, e.getMessage()+", Please do the payment again");
		} catch (Exception e) {
			status = false;
			PosFormUtil.showErrorMessageBox(this, e.getMessage());
		}
		return status;
	}
	
	
	private EFTResponseMessagePUR mPurchaseMessage = null; 
	
	private void setPurchaseMessage(EFTResponseMessagePUR purchaseMessage){
		mPurchaseMessage = purchaseMessage;
	}
	
	private EFTResponseMessagePUR getPurchaseMessage(){
		return mPurchaseMessage;
	}
	
	/**
	 * @throws Exception 
	 * Remove the payments against the order.
	 * 
	 */
	private void removePayment() throws Exception {
		mOrderHeaderObject.setStatus(PosOrderStatus.Open);
		mOrderHeaderObject.getOrderPaymentItems().clear();
		mOrderHdrProvider.saveOrder(mOrderHeaderObject);		
	}
	/**
	 * @param purchaseMessage
	 * @throws Exception 
	 * Setting the payment object using the response message received
	 * Update the order payment table against the order.
	 * Ask for printing the bill.
	 * Doing the synchronization of the order with the order payment. 
	 */
	private void updatePayment(EFTResponseMessagePUR purchaseMessage) throws Exception {
		BeanOrderPayment orderCardPayment = new BeanOrderPayment();
		orderCardPayment.setOrderId(mOrderHeaderObject.getOrderId());
		orderCardPayment.setPaymentMode(PaymentMode.Card);
		orderCardPayment.setPaidAmount(Double.parseDouble(purchaseMessage.getCardAmount()));
		orderCardPayment.setCardNo(purchaseMessage.getCardNumber());
		orderCardPayment.setCardApprovalCode(purchaseMessage.getAuthCode());
		orderCardPayment.setAccount(purchaseMessage.getAccountType());
		
		orderCardPayment.setCashierID(PosEnvSettings.getInstance().getCashierShiftInfo()
				.getCashierInfo().getId());
		orderCardPayment.setPaymentTime(PosDateUtil.getDateTime());
		orderCardPayment.setPaymentDate(PosEnvSettings.getPosEnvSettings().getPosDate());
		
		mOrderHeaderObject.getOrderPaymentItems().add(orderCardPayment);
		
		if(purchaseMessage.getCashAmount()!=null&&!purchaseMessage.getCashAmount().isEmpty() && Double.parseDouble(purchaseMessage.getCashAmount())>0){
			
			BeanOrderPayment orderCashOutPayment = new BeanOrderPayment();
			orderCashOutPayment.setOrderId(mOrderHeaderObject.getOrderId());
			orderCashOutPayment.setPaymentMode(PaymentMode.Card);
			orderCashOutPayment.setPaidAmount(Double.parseDouble(purchaseMessage.getCardAmount()));
			orderCashOutPayment.setCardNo(purchaseMessage.getCardNumber());
			orderCashOutPayment.setCardApprovalCode(purchaseMessage.getAuthCode());
			orderCashOutPayment.setAccount(purchaseMessage.getAccountType());
			
			orderCashOutPayment.setCashierID(PosEnvSettings.getInstance().getCashierShiftInfo()
					.getCashierInfo().getId());
			orderCashOutPayment.setPaymentTime(PosDateUtil.getDateTime());
			orderCashOutPayment.setPaymentDate(PosEnvSettings.getPosEnvSettings().getPosDate());
			
			mOrderHeaderObject.getOrderPaymentItems().add(orderCashOutPayment);
		}
		
		mPosPoleDisplay.disPlayBillSettlement(PosOrderUtil.getTotalAmount(mOrderHeaderObject),
				mOrderHeaderObject.getChangeAmount());
		mOrderHeaderObject.setStatus(PosOrderStatus.Closed);
		mOrderHeaderObject.setClosingDate(PosEnvSettings.getPosEnvSettings()
				.getPosDate());
		mOrderHeaderObject.setClosingTime(PosDateUtil.getDateTime());
		if (mOrderHdrProvider.saveOrder(mOrderHeaderObject)) {
			SynchronizeToServer.synchronizeTable(
					SynchronizeToServer.SyncTable.ORDER_HDRS.getCode(),
					"order_hdrs.order_id='" + mOrderHeaderObject.getOrderId()
							+ "'");
//			PosFormUtil.showQuestionMessageBox(this,
//					MessageBoxButtonTypes.YesNo,
//					"Do you want to print recipt?",
//					new PosMessageBoxFormListnerAdapter() {
//						@Override
//						public void onYesButtonPressed() {
//							mPrintRecipt = true;
//						}
//
//						public void onNoButtonPressed() {
//							mPrintRecipt = false;
//						};
//					});
			printBill(PosCardPaymentRecoveryForm.this, mOrderHeaderObject, true);
			PosBillPaymentInfoForm billForm = new PosBillPaymentInfoForm(
					mOrderHeaderObject);
			billForm.setListner(mBillPrintFormListner);
			PosFormUtil
					.showLightBoxModal(this, billForm);
		}
		
	}
	
	private IPosBillPrintFormListner mBillPrintFormListner = new PosBillPrintFormAdapter() {
		@Override
		public void onDoneClicked() {
			try {
				// PosDevicePrinter.getInstance().shutdown();
			} catch (Exception err) {
				PosLog.write(this, "onDoneClicked", err);
			}
			closeForm();
//			if (mListner != null)
//				mListner.onPaymentDone();
		}

		@Override
		public void onRePrintClicked(Object sender, BeanOrderHeader oh) {
//			mPrintRecipt =true;
			printBill(sender,oh, false);
			mOrderHdrProvider.updatePrintCounter(oh.getOrderId());
		}

		@Override
		public void onwholeBillPrint(Object sender) {
			// TODO Auto-generated method stub
			
		}
	};
	
	
	private void closeForm() {
		setVisible(false);
		dispose();
	}
	
	/**
	 * @param orderHeader
	 * @param openCashDrawer
	 * Print the bill and open the cash drawer also.
	 */
	private void printBill(Object sender,  BeanOrderHeader orderHeader,
			boolean openCashDrawer) {
		try {
//			if(mPrintRecipt){
//				PosReceiptBase.printBill(orderHeader,openCashDrawer,);
//				PosReceiptBase.printReceipt( orderHeader, openCashDrawer, null, false, false);
				if(!PosFormUtil.showPrintConfirmMessage((RootPaneContainer)sender, false, orderHeader, openCashDrawer, null, false))
					PosDeviceManager.getInstance().getReceiptPrinter().openCashBox();
//			} else if(openCashDrawer){
//				PosDeviceReceiptPrinter.getInstance().openCashBox();
//			}
		} catch (Exception err) {
			PosLog.write(this, "printBill", err);
			PosFormUtil.showErrorMessageBox(this, "Error in printing!!");
		}
	}
	/**
	 * @param eftPurchaseMessage
	 * @param msgForm 
	 * @throws Exception 
	 */
	private EFTResponseMessagePUR doReTryPayment(String eftPurchaseMessage, PosMessageBoxForm msgForm) throws Exception {

		reTry = false;
		EFTResponseMessagePUR purchaseMessage = null;
		purchaseMessage = doPurchase(eftPurchaseMessage,msgForm);
				
		if (purchaseMessage == null) {
			showEFTerrorMessage(new PosMessageBoxFormListnerAdapter() {
				@Override
				public void onNoButtonPressed() {
					return;
				}

				@Override
				public void onYesButtonPressed() {
					setTxnDetails();
				}

				/*
				 * (non-Javadoc)
				 * 
				 * @see com.indocosmo.pos.forms.messageboxes.listners.
				 * PosMessageBoxFormListnerAdapter#onCancelButtonPressed()
				 */
				@Override
				public void onCancelButtonPressed() {
					// setTxnStatus();
					reTry = true;
				}

			});
			if (reTry ) {
				purchaseMessage = doReTryPayment(eftPurchaseMessage,msgForm);
			}

		}
		return purchaseMessage;
			
	}
	
	
	
	/**
	 * @param eftPurchaseMessage 
	 * @param msgForm 
	 * @return
	 */
	private EFTResponseMessagePUR doPurchase(String eftPurchaseMessage, PosMessageBoxForm msgForm) {
		EFTResponseMessagePUR purchaseMessage = null;
		try {
			purchaseMessage = (EFTResponseMessagePUR) PosDeviceEFT.retryPayment(
					this, eftPurchaseMessage,msgForm);
		} catch (Exception e) {
			purchaseMessage = null;
		}
		return purchaseMessage;
	}
	private void showEFTerrorMessage(PosMessageBoxFormListnerAdapter listner) {
		PosFormUtil.showQuestionMessageBox(null, MessageBoxButtonTypes.YesNoCancel,
				"Communication with EFTPOS device lost. Check terminal, EFTPOS txn approved ?", listner,"Retry");
	}
	
	/**
	 * Setting the transaction details by showing a new form for entering the txn details.
	 */
	private void setTxnDetails(){
		purchaseMessageOnManualEntry = null;
		PosCardTxnDetailsForm cardTxnDetailsForm = new PosCardTxnDetailsForm();
		cardTxnDetailsForm.setCancelButtonVisible(false);
		cardTxnDetailsForm.setListner(cardTxnDetailsFormListner);
		cardTxnDetailsForm.setCardPurchaseAmount(strPurchaseAmount);
		cardTxnDetailsForm.setCashAmount(strCashAmount);
		PosFormUtil.showLightBoxModal(null, cardTxnDetailsForm);
	}
	
private IPosCardTxnDetailsFormListner cardTxnDetailsFormListner = new IPosCardTxnDetailsFormListner() {
		
		@Override
		public void onTxnCompleted(EFTResponseMessagePUR purchaseMessage) {
			purchaseMessageOnManualEntry =  purchaseMessage;
			purchaseMessageOnManualEntry.setStatus(EFTPurchaseStatus.ACCEPTED);
		}
	};
	
    /**
     * @author deepak
     * new exception class.
     *
     */
    public class reTryPaymentException extends Exception{
		
		public reTryPaymentException(String message){
			super(message);
		}
	}

}
