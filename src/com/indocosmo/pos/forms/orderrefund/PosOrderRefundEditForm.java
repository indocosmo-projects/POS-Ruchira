/**
 * 
 */
package com.indocosmo.pos.forms.orderrefund;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.RootPaneContainer;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.text.JTextComponent;

import com.indocosmo.pos.common.PosEnvSettings;
import com.indocosmo.pos.common.PosLog;
import com.indocosmo.pos.common.beans.settings.ui.orderlist.BeanUIOrderListSetting;
import com.indocosmo.pos.common.beans.settings.ui.orderlist.BeanUIOrderRefundSetting;
import com.indocosmo.pos.common.enums.EnablePrintingOption;
import com.indocosmo.pos.common.enums.RefundAdjustmentType;
import com.indocosmo.pos.common.utilities.HmsUtil;
import com.indocosmo.pos.common.utilities.PosCurrencyUtil;
import com.indocosmo.pos.common.utilities.PosDateUtil;
import com.indocosmo.pos.common.utilities.PosFormUtil;
import com.indocosmo.pos.common.utilities.PosNumberUtil;
import com.indocosmo.pos.common.utilities.PosOrderUtil;
import com.indocosmo.pos.common.utilities.PosSaleItemUtil;
import com.indocosmo.pos.common.utilities.PosTaxUtil;
import com.indocosmo.pos.common.utilities.PosUomUtil;
import com.indocosmo.pos.data.beans.BeanOrderDetail;
import com.indocosmo.pos.data.beans.BeanOrderHeader;
import com.indocosmo.pos.data.beans.BeanOrderHeader.PosOrderStatus;
import com.indocosmo.pos.data.beans.BeanOrderPayment;
import com.indocosmo.pos.data.beans.BeanOrderPaymentHeader;
import com.indocosmo.pos.data.beans.BeanOrderRefund;
import com.indocosmo.pos.data.beans.BeanOrderRefund.RefundType;
import com.indocosmo.pos.data.beans.BeanPayModeBase.PaymentMode;
import com.indocosmo.pos.data.beans.BeanPaymentModes;
import com.indocosmo.pos.data.beans.BeanSaleItem;
import com.indocosmo.pos.data.providers.shopdb.PosCustomerTypeProvider;
import com.indocosmo.pos.data.providers.shopdb.PosOrderHdrProvider;
import com.indocosmo.pos.data.providers.shopdb.PosOrderPaymentHdrProvider;
import com.indocosmo.pos.data.providers.shopdb.PosOrderPaymentsProvider;
import com.indocosmo.pos.data.providers.shopdb.PosOrderRefundProvider;
import com.indocosmo.pos.data.providers.shopdb.PosPaymentModesProvider;
import com.indocosmo.pos.data.providers.shopdb.PosTaxAmountObject;
import com.indocosmo.pos.data.providers.shopdb.PosUOMProvider;
import com.indocosmo.pos.forms.PosBaseForm;
import com.indocosmo.pos.forms.PosNumKeypadForm;
import com.indocosmo.pos.forms.PosObjectBrowserForm;
import com.indocosmo.pos.forms.PosObjectBrowserForm.ItemSize;
import com.indocosmo.pos.forms.PosRemarksEditForm;
import com.indocosmo.pos.forms.components.buttons.PosButton;
import com.indocosmo.pos.forms.components.buttons.PosSelectButton;
import com.indocosmo.pos.forms.components.buttons.listners.IPosButtonListner;
import com.indocosmo.pos.forms.components.buttons.listners.IPosSelectButtonListner;
import com.indocosmo.pos.forms.components.objectbrowser.IPosBrowsableItem;
import com.indocosmo.pos.forms.listners.IPosFormEventsListner;
import com.indocosmo.pos.forms.listners.IPosNumKeyPadFormListner;
import com.indocosmo.pos.forms.listners.IPosObjectBrowserListner;
import com.indocosmo.pos.forms.messageboxes.PosMessageBoxForm.MessageBoxButtonTypes;
import com.indocosmo.pos.forms.messageboxes.PosMessageBoxForm.MessageBoxResults;
import com.indocosmo.pos.forms.orderrefund.components.RefundItemListTablePanel;
import com.indocosmo.pos.forms.orderrefund.components.listeners.IRefundItemListTablePanelListener;
import com.indocosmo.pos.forms.orderrefund.listeners.IPosOrderRefundFormListner;
import com.indocosmo.pos.process.sync.SynchronizeToServer;

/**
 * @author sandhya
 *
 */
public class PosOrderRefundEditForm extends PosBaseForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	
	protected static final int PANEL_CONTENT_V_GAP=6;
	protected static final int PANEL_CONTENT_H_GAP=6; 
	
	private static final int BUTTON_WIDTH =85;
	
	private static final int ITEM_TITLE_WIDTH =150;
	private static final int ITEM_TEXT_WIDTH = 300;
	private static final int ITEM_HEIGHT = 34; //PosTouchableFieldBase.LAYOUT_DEF_HEIGHT;
 	
	private static final int ORD_HDR_ROWS=6;
	private static final int ORD_HDR_PANEL_HEIGHT =  ( ITEM_HEIGHT *ORD_HDR_ROWS ) 
			+ PANEL_CONTENT_V_GAP * ( ORD_HDR_ROWS-2  )  ;
	private static final int ORD_HDR_PANEL_WIDTH = ITEM_TITLE_WIDTH+ITEM_TEXT_WIDTH +PANEL_CONTENT_H_GAP *2 ;

	private static final int REFUND_PANEL_HEIGHT=  ORD_HDR_PANEL_HEIGHT  ;
	private static final int REFUND_PANEL_WIDTH = 450; //PANEL_WIDTH - ORD_HDR_PANEL_WIDTH - PANEL_CONTENT_H_GAP *2;

	private static final int PANEL_WIDTH=REFUND_PANEL_WIDTH + ORD_HDR_PANEL_WIDTH + PANEL_CONTENT_H_GAP *2;// 830;
	private static final int PANEL_HEIGHT=530;
	
	private static final int ITEM_LIST_PANEL_HEIGHT = PANEL_HEIGHT- ORD_HDR_PANEL_HEIGHT-PANEL_CONTENT_V_GAP*3;
	private static final int ITEM_LIST_PANEL_WIDTH = PANEL_WIDTH-PANEL_CONTENT_H_GAP*2;
	
	private RefundItemListTablePanel refundItemListPanel;
	private JPanel mContentPanel;
	
	private JTextField mTxtNumber;
	private JTextField mTxtDateTime;
	private JTextField mTxtCashier;
	private JTextField mTxtTotalAmount;
	private JTextField mTxtRefundableAmount;
	private JTextField mTxtExtraCharges;
	PosOrderHdrProvider mOderHdrProvider;
	BeanOrderHeader mPosOrderHObject;
	private PaymentMode mSelectedPayMode;
	HashMap<PaymentMode, PosSelectButton> paymentButtons;
	JLabel mTxtRefundTotCount;
	JLabel mTxtRefundTotAmount;
	Double mTotalRefundAmt;
	double mRoundingAdjAmt=0;
	private IPosOrderRefundFormListner mRefundListner;
	private PosOrderRefundProvider ordRefundProvider;
	String mRemarks;
 
	/**
	 * @param title
	 * @param cPanelwidth
	 * @param cPanelHeight
	 */
	public PosOrderRefundEditForm(String orderId) {
		super("Order Refund", PANEL_WIDTH, PANEL_HEIGHT);
	
		ordRefundProvider=new PosOrderRefundProvider();
		populateOrderDetails(orderId);
		
		setComponents();
		loadUIData();
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.pos.forms.PosBaseForm#setContentPanel(javax.swing.JPanel)
	 */
	@Override
	
	protected void setContentPanel(JPanel panel) {
		
		mContentPanel=panel;
		mContentPanel.setLayout(null);
	}
	/*
	 * 
	 */
	private void createButtons(){
		
		setOkButtonCaption("Refund",'f');
		
		PosButton buttonReset= addButtonsToBottomPanel("Reset",resetBtnListener,2);
		buttonReset.setMnemonic('R');
		
		final PosButton buttonRemarks = this.addButtonsToBottomPanel("Remarks",null,0);
		buttonRemarks.setMnemonic('m');
		buttonRemarks.setOnClickListner(remarksButtonListener);
		
		BeanUIOrderRefundSetting refundSettings=PosEnvSettings.getInstance().getUISetting().getOrderListSettings().getRefundSetting();
		
		if(refundSettings.getRefundType()==RefundType.Partial &&  
				!refundSettings.getRefundAdjustmentMethods().get(0).equals(RefundAdjustmentType.NONE) )
			addButtonsToBottomPanel("Adjust", adjustButtonListener,1);
	}
	
	/*
	 * 
	 */
	
	private IPosButtonListner remarksButtonListener=new IPosButtonListner() {

		@Override
		public void onClicked(PosButton button) {
				final PosRemarksEditForm remarksform=new PosRemarksEditForm();
				remarksform.setRemarks(mRemarks);
				
				remarksform.setListner(new IPosFormEventsListner() {
					
					@Override
					public void onResetButtonClicked() {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public boolean onOkButtonClicked() {
						mRemarks=remarksform.getRemarks();
						return true;
					}
					
					@Override
					public boolean onCancelButtonClicked() {
						// TODO Auto-generated method stub
						return false;
					}
				});
				
				
				PosFormUtil.showLightBoxModal(PosOrderRefundEditForm.this, remarksform);
			 
		}
	};
	
	/*
	 * 
	 */
	private IPosButtonListner adjustButtonListener=new IPosButtonListner() {
		
		@Override
		public void onClicked(PosButton button) {
			
			if(!isSelectionValidForAdjustment()) return;
			
			
			BeanUIOrderListSetting orderListSettings=PosEnvSettings.getInstance().getUISetting().getOrderListSettings();
			
			if (orderListSettings.getRefundSetting().getRefundAdjustmentMethods().size()==1){
				setAdjustmentValue(orderListSettings.getRefundSetting().getRefundAdjustmentMethods().get(0));
			}else{
			
				final PosObjectBrowserForm form=new PosObjectBrowserForm("Adjustment Method", RefundAdjustmentType.values() , ItemSize.Wider,1,1);
				form.setListner(new IPosObjectBrowserListner() {
		
					@Override
					public void onItemSelected(IPosBrowsableItem item) {
						setAdjustmentValue((RefundAdjustmentType)item);
						
					}
		
					@Override
					public void onCancel() {
						// TODO Auto-generated method stub
		
					}
				});
				PosFormUtil.showLightBoxModal(PosOrderRefundEditForm.this,form);
			}
		}
	};

	private void setAdjustmentValue(final RefundAdjustmentType adjustmentType){
			
		PosNumKeypadForm numForm=new PosNumKeypadForm();
		numForm.setTitle("Adjusted " + ( adjustmentType.equals(RefundAdjustmentType.QUANTITY) ? " Quantity":"Amount")); 
		final double adjustedValue=(adjustmentType==RefundAdjustmentType.AMOUNT)?refundItemListPanel.getSelectedItem().getRefundAmount():refundItemListPanel.getSelectedItem().getRefundQuantity();
		
		numForm.setValue(PosCurrencyUtil.format(adjustedValue));
		numForm.setOnValueChanged(new IPosNumKeyPadFormListner() {
			
			@Override
			public void onValueChanged(String value) {
				if(adjustmentType==RefundAdjustmentType.AMOUNT)
			  	 refundItemListPanel.setSelectedItemAdjustmentAmount(Double.valueOf(value));
				else
					refundItemListPanel.setSelectedItemAdjustmentQty(Double.valueOf(value));
			}
			
			@Override
			public void onValueChanged(JTextComponent target, String oldValue) {
				// TODO Auto-generated method stub
				
			}
		});
		PosFormUtil.showLightBoxModal(PosOrderRefundEditForm.this,numForm);
		
	}
	
	/**
	 * @return
	 */
	private boolean isSelectionValidForAdjustment(){
		
		boolean isValid=true;
		
		if(refundItemListPanel.getSelectedItem()==null){
			
			PosFormUtil.showInformationMessageBox(PosOrderRefundEditForm.this, "Please select an item.");
			isValid=false;
		}else if (!refundItemListPanel.getSelectedItem().isDirty()){
			
			PosFormUtil.showInformationMessageBox(PosOrderRefundEditForm.this, "Please select an item.");
			isValid=false;
		}
		
		return isValid;
	}
	

	/**
	 * @param listner
	 */
	public void setListner(IPosOrderRefundFormListner listner){

		mRefundListner=listner;
	}
	/*
	 * 
	 */
	IPosButtonListner resetBtnListener=new IPosButtonListner() {
		
		@Override
		public void onClicked(PosButton button) {
			
			refundItemListPanel.clearSelections();
			mTxtRefundTotAmount.setText(PosCurrencyUtil.format(0));
			mTxtRefundTotCount.setText(PosNumberUtil.format(0));
			mRemarks="";
		}
	};
	
	/*
	 * 
	 */
	private void loadUIData(){
		
		refundItemListPanel.SetOrderItemList(mPosOrderHObject);
		mTxtNumber.setText(mPosOrderHObject.getInvoiceNo());
	  	mTxtDateTime.setText(PosDateUtil.formatShortDateTime( (mPosOrderHObject.getClosingTime()!=null?mPosOrderHObject.getClosingTime():mPosOrderHObject.getOrderTime())));
	 	
	 	mTxtCashier.setText(mPosOrderHObject.getUser().getDisplayText());
	 	mTxtExtraCharges.setText(PosCurrencyUtil.format(mPosOrderHObject.getExtraCharges()));	  	
	 	PosOrderRefundProvider orderRefundProvider=new PosOrderRefundProvider();

	 	final double totalPaidAmt=PosOrderUtil.getTotalPaidAmount(mPosOrderHObject);
	 	mTxtTotalAmount.setText(PosCurrencyUtil.format(totalPaidAmt));
	 	
	 	mTotalRefundAmt=orderRefundProvider.getTotalRefundAmount(mPosOrderHObject.getOrderId());
	 	if(totalPaidAmt - mTotalRefundAmt>=0)
	 		mTxtRefundableAmount.setText(PosCurrencyUtil.format(PosCurrencyUtil
				.roundTo(totalPaidAmt - mTotalRefundAmt)));
	 	else
	 		mTxtRefundableAmount.setText(PosCurrencyUtil.format(0));
	 	
	 	refundItemListPanel.setEnabled(mPosOrderHObject.getStatus().equals(PosOrderStatus.Partial)?false:true);
	
	 	BeanUIOrderRefundSetting refundSettings=PosEnvSettings.getInstance().getUISetting().getOrderListSettings().getRefundSetting();
		if(refundSettings.getRefundType()==RefundType.Full) 
			refundItemListPanel.setEnabled(false);
	}
	/**
	 * 
	 */
	private void setComponents() {

		createItemListTable();
		createOrderHdrPanel();
		createRefundPanel();
		createButtons();
	}
	
	
	/*
	 * 
	 */
	private void createOrderHdrPanel(){
		 
		JPanel hdrPanel=new JPanel();
		hdrPanel.setLayout(new FlowLayout(FlowLayout.LEFT,1,3));
		hdrPanel.setBorder(new EtchedBorder());
		hdrPanel.setBounds(PANEL_CONTENT_H_GAP, ITEM_LIST_PANEL_HEIGHT + PANEL_CONTENT_V_GAP *2,ORD_HDR_PANEL_WIDTH,ORD_HDR_PANEL_HEIGHT);
	
	 	mContentPanel.add( hdrPanel);
	
		mTxtNumber = createField(hdrPanel, "Number :");
		mTxtDateTime= createField(hdrPanel, "Date :");
		mTxtCashier = createField(hdrPanel, "Cashier :");
		mTxtExtraCharges = createField(hdrPanel, "Extra Charges :");
		mTxtTotalAmount = createField(hdrPanel, "Paid Amount :");
		mTxtRefundableAmount = createField(hdrPanel, "Refundable :");
		
		mTxtExtraCharges.setHorizontalAlignment(SwingConstants.RIGHT);
		mTxtTotalAmount.setHorizontalAlignment(SwingConstants.RIGHT);
		mTxtRefundableAmount.setHorizontalAlignment(SwingConstants.RIGHT);
		
		
	}
	/**
	 * 
	 * @param panel
	 * @param title
	 * @return
	 */
	private JTextField createField(JPanel panel, String title) {
		JPanel itemPanel = creatFieldPanelWithTitle(title);
		panel.add(itemPanel);

		JTextField text = new JTextField("");
		text.setPreferredSize(new Dimension(ITEM_TEXT_WIDTH, ITEM_HEIGHT));
		text.setFont(PosFormUtil.getLabelFont());
		text.setEditable(false);
		itemPanel.add(text);

		return text;
	}
	 
	/***
	 * 
	 * @param title
	 * @return
	 */
	private JPanel creatFieldPanelWithTitle(String title) {
		
		final int width= (ITEM_TITLE_WIDTH + ITEM_TEXT_WIDTH) + PANEL_CONTENT_H_GAP * 1 ;
				

		JPanel itemPanel = new JPanel();
		itemPanel
		.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
		itemPanel.setPreferredSize(new Dimension(width,	ITEM_HEIGHT));

		JLabel lable = new JLabel(title);
		lable.setPreferredSize(new Dimension(ITEM_TITLE_WIDTH, ITEM_HEIGHT));
		lable.setBorder(new EmptyBorder(2, 2, 2, 2));
		lable.setFont(PosFormUtil.getLabelFont());
		lable.setOpaque(true);
		lable.setBackground(Color.LIGHT_GRAY);
		itemPanel.add(lable);

		return itemPanel;
	}
	/**
	 * 
	 */
	private void createItemListTable() {

		refundItemListPanel=new  RefundItemListTablePanel(this, ITEM_LIST_PANEL_WIDTH, ITEM_LIST_PANEL_HEIGHT);
		refundItemListPanel.setRowsSelectable(true);
		refundItemListPanel.setLocation(PANEL_CONTENT_H_GAP, PANEL_CONTENT_V_GAP);
		refundItemListPanel.setListener(new IRefundItemListTablePanelListener() {
			
			@Override
			public void onSelectionChanged(int index) {
				calculateRefundSummary();
				
			}
		});
		mContentPanel.add(refundItemListPanel);
	}
	
	/**
	 * 
	 */
	private void calculateRefundSummary(){
		

		ArrayList<BeanOrderDetail> orderDetItems;
		double totalRefAmt=0;
		double totalRefItemCnt=0;
		
		orderDetItems=refundItemListPanel.getSelectedItemList();
		if (orderDetItems!=null){
			for(BeanOrderDetail odet :orderDetItems){
				totalRefAmt+=odet.getRefundAmount();
				totalRefItemCnt+=odet.getRefundQuantity();
				
			}
		}
		
		final double roundedAmt= (mSelectedPayMode!=null && PosFormUtil.canRound(mSelectedPayMode)) ? PosCurrencyUtil.nRound(totalRefAmt):totalRefAmt;
		 
		mRoundingAdjAmt=roundedAmt-totalRefAmt;
		mTxtRefundTotAmount.setText(PosCurrencyUtil.format(roundedAmt));
		mTxtRefundTotCount.setText(PosUomUtil.format(totalRefItemCnt, PosUOMProvider.getInstance().getMaxDecUom()));
		
	}
	
	
	/*
	 * 
	 */
	private void populateOrderDetails(String orderID){
		
		try {
			
			final String orderHeaderID=orderID;
			mOderHdrProvider=new PosOrderHdrProvider();
			mPosOrderHObject = mOderHdrProvider.getOrderData(orderHeaderID);
			
			
		} catch (Exception e) {
			PosLog.write(this, "populateOrderDetails", e);
			PosFormUtil.showErrorMessageBox(this, "Failed to load the order details.");
		}
	}
	 

	/*
	 * 
	 */
	private void createRefundPanel(){
		
		JPanel refundPanel=new JPanel();
		refundPanel.setBounds(ORD_HDR_PANEL_WIDTH+ PANEL_CONTENT_H_GAP*2, ITEM_LIST_PANEL_HEIGHT + PANEL_CONTENT_V_GAP *2, REFUND_PANEL_WIDTH,REFUND_PANEL_HEIGHT);
		refundPanel.setLayout( null);
		mContentPanel.add( refundPanel);
		refundPanel.setBorder(new EtchedBorder());

		createRefundBtnList(refundPanel);
		createRefundAmtPanel(refundPanel);
	} 
	
	/**
	 * 
	 */
	private void createRefundBtnList( JPanel refundPanel){
		
		JPanel refundMethodPanel=new JPanel();
		refundMethodPanel.setLayout( new FlowLayout(FlowLayout.LEFT,2,0 ));
		refundMethodPanel.setBounds(PANEL_CONTENT_H_GAP, PANEL_CONTENT_V_GAP,REFUND_PANEL_WIDTH -PANEL_CONTENT_H_GAP*2 ,ITEM_HEIGHT + PANEL_CONTENT_V_GAP);
		refundPanel.add( refundMethodPanel);
		  
		paymentButtons=new HashMap<PaymentMode, PosSelectButton>();
		
		final PosPaymentModesProvider posPaymentModesProvider = PosPaymentModesProvider.getInstance();
		BeanPaymentModes paymentModes = posPaymentModesProvider.getPaymentModes();
		
		 
		PosSelectButton btnCash=createRefundButton(refundMethodPanel, PaymentMode.Cash,null,'h');
		paymentButtons.put(PaymentMode.Cash, btnCash);
		
		PosSelectButton btnCard=createRefundButton(refundMethodPanel, PaymentMode.Card,  btnCash,'d');
		paymentButtons.put(PaymentMode.Card, btnCard);
		
		PosSelectButton btnCompany=createRefundButton(refundMethodPanel, PaymentMode.Company, btnCard,'p');
		paymentButtons.put(PaymentMode.Company, btnCompany);
		
		PosSelectButton btnVoucher=createRefundButton(refundMethodPanel, PaymentMode.Coupon, btnCompany,'o');
		paymentButtons.put(PaymentMode.Coupon, btnVoucher);
		
		PosSelectButton btnOnline=createRefundButton(refundMethodPanel, PaymentMode.Online, btnCompany,'n');
		paymentButtons.put(PaymentMode.Online, btnOnline);
		
		if(paymentModes.isCanCashRefundable()) 
			btnCash.setEnabled(true);
		
		if(paymentModes.isCanCardRefundable()) {
			
			if (isExistPayMode(PaymentMode.Card))
				btnCard.setEnabled(true);
			
		} 
		
		if(paymentModes.isCanCompanyRefundable()){
			
			if (isExistPayMode(PaymentMode.Company))
				btnCompany.setEnabled(true);
			
		}  
		
		if(paymentModes.isCanVoucherRefundable()) {
			
			if (isExistPayMode(PaymentMode.Coupon))
				btnCard.setEnabled(true);
			
		}
		if(paymentModes.isCanOnlineRefund()) {
			
			if (isExistPayMode(PaymentMode.Online))
				btnOnline.setEnabled(true);
			
		}
		PaymentMode payMode=  mPosOrderHObject.getOrderPaymentItems().get(0).getPaymentMode();
		
		if (paymentButtons.get(payMode)!=null && paymentButtons.get(payMode).isEnabled())
			paymentButtons.get(payMode).setSelected(true);
		else if(btnCash.isEnabled())
			btnCash.setSelected(true);
		 else
			setOkButtonEnabled(false);
		
 
	}
	
	/*
	 * 
	 */
	private PosSelectButton createRefundButton(JPanel paymentPanel, PaymentMode payMode,PosSelectButton prevButton,char mnemonic ){
	
		
		PosSelectButton button=new PosSelectButton();
		button.setSize(new Dimension(BUTTON_WIDTH,ITEM_HEIGHT));
		button.setPreferredSize(button.getSize());
		button.setText(payMode.getDisplayText());
		button.setTag(payMode);
		button.setImage("split_basedon_button.png");
		button.setTouchedImage("split_basedon_button_selected.png");
		button.setOnSelectListner(selectBtnListner);
		button.setEnabled(false);
		button.setMnemonic(mnemonic);
		paymentPanel.add(button);
		return button;
	}
	
	/*
	 * 
	 */
	private IPosSelectButtonListner selectBtnListner=new IPosSelectButtonListner() {
		
		@Override
		public void onSelected(PosSelectButton button) {
			
			PaymentMode newPayMode=(PaymentMode) button.getTag();
			if(mSelectedPayMode !=newPayMode){
				
				if(mSelectedPayMode!=null){
					paymentButtons.get(mSelectedPayMode).setSelected(false);
					
					if (PosFormUtil.canRound(mSelectedPayMode) != PosFormUtil.canRound(newPayMode)){ 
					
						mSelectedPayMode=newPayMode;
						calculateRefundSummary();	
					}else
						mSelectedPayMode=newPayMode;

					
				}else 
					mSelectedPayMode=newPayMode;
				 
			}
			
		}
	};
	
	/*
	 * 
	 */
	private boolean isExistPayMode(PaymentMode payMode){
		
		boolean result=false;
		if (mPosOrderHObject!=null){
			
			for(BeanOrderPayment ordPayment: mPosOrderHObject.getOrderPaymentItems()){
					if (ordPayment.getPaymentMode().equals(payMode)) {
						result=true;
						break;
					}
			}
		}
		
		return result;
	} 
	
	/**
	 * @param refundMethodPanel
	 */
	private void createRefundAmtPanel( JPanel refundMethodPanel) {

		final int panelWitdth=REFUND_PANEL_WIDTH-PANEL_CONTENT_H_GAP *3;
		final int panelHeight=ORD_HDR_PANEL_HEIGHT-ITEM_HEIGHT-PANEL_CONTENT_V_GAP*3;
		
		JPanel refundAmtPanel=new JPanel();
//		refundAmtPanel.setPreferredSize(new Dimension( panelWitdth,panelHeight));
		refundAmtPanel.setLayout(null);
//		refundAmtPanel.setLocation(20, ITEM_HEIGHT + PANEL_CONTENT_V_GAP);
		refundAmtPanel.setBounds(PANEL_CONTENT_H_GAP +2, ITEM_HEIGHT + PANEL_CONTENT_V_GAP*2,panelWitdth,panelHeight);
		
		refundMethodPanel.add(refundAmtPanel);
		
		
		JLabel lblRefundSummary;
	 	JLabel lblRefundTotCnt;
	 	JLabel lblRefundTotAmt;
	 	final Font fnt=PosFormUtil.getLabelFont().deriveFont(Font.BOLD,35.0f);
		
	 	lblRefundSummary=PosFormUtil.setHeading("Refund Summary",panelWitdth,ITEM_HEIGHT);
		lblRefundSummary.setLocation(0, 2);
		refundAmtPanel.add(lblRefundSummary);
	
		final int summary_panel_gap=4;
		final int refundSummaryHeight=panelHeight-ITEM_HEIGHT-summary_panel_gap*2;
		final int refundSummaryCntWidth=ITEM_TITLE_WIDTH;
		final int refundSummaryAmtWidth=panelWitdth-ITEM_TITLE_WIDTH-summary_panel_gap;
		
		JPanel refundTotCntPanel=new JPanel();
		refundTotCntPanel.setLayout(null);
		refundTotCntPanel.setBounds(0, ITEM_HEIGHT+summary_panel_gap,refundSummaryCntWidth,refundSummaryHeight);
		refundAmtPanel.add(refundTotCntPanel);
		
		JPanel refundTotAmtPanel=new JPanel();
		refundTotAmtPanel.setLayout(null);
		refundTotAmtPanel.setBounds(refundSummaryCntWidth+summary_panel_gap, ITEM_HEIGHT+summary_panel_gap,refundSummaryAmtWidth,refundSummaryHeight);
		refundAmtPanel.add(refundTotAmtPanel);
		
		final int refundSummaryHdrHeight=ITEM_HEIGHT-5;
		
		lblRefundTotCnt=new JLabel("Quantity");
		lblRefundTotCnt.setFont(PosFormUtil.getLabelFont());
		lblRefundTotCnt.setBounds(0, 0,refundSummaryCntWidth ,refundSummaryHdrHeight);
		lblRefundTotCnt.setOpaque(true);
		lblRefundTotCnt.setBackground(Color.LIGHT_GRAY);
		lblRefundTotCnt.setHorizontalAlignment(JLabel.CENTER);
		refundTotCntPanel.add(lblRefundTotCnt);
		
		mTxtRefundTotCount=new JLabel();
		mTxtRefundTotCount.setHorizontalAlignment(JLabel.CENTER);
		mTxtRefundTotCount.setFont(fnt);
		mTxtRefundTotCount.setBorder(new LineBorder(new Color(78,128,188), 1));
		mTxtRefundTotCount.setBounds(0, refundSummaryHdrHeight, refundSummaryCntWidth, refundSummaryHeight-refundSummaryHdrHeight);
		mTxtRefundTotCount.setForeground(Color.RED);
		mTxtRefundTotCount.setText(PosNumberUtil.format(0));
		refundTotCntPanel.add(mTxtRefundTotCount);
		 
		lblRefundTotAmt=new JLabel("To Pay");
		lblRefundTotAmt.setFont(PosFormUtil.getLabelFont());
		lblRefundTotAmt.setBounds(0, 0,refundSummaryAmtWidth ,refundSummaryHdrHeight);  
		lblRefundTotAmt.setOpaque(true);
		lblRefundTotAmt.setBackground(Color.LIGHT_GRAY);
		lblRefundTotAmt.setHorizontalAlignment(JLabel.CENTER);
		refundTotAmtPanel.add(lblRefundTotAmt);
		
		mTxtRefundTotAmount=new JLabel();
		mTxtRefundTotAmount.setHorizontalAlignment(JLabel.CENTER);
		mTxtRefundTotAmount.setFont(fnt);
		mTxtRefundTotAmount.setBorder(new LineBorder(new Color(78,128,188), 1));
		mTxtRefundTotAmount.setBounds(0, refundSummaryHdrHeight, refundSummaryAmtWidth,refundSummaryHeight-refundSummaryHdrHeight);
		mTxtRefundTotAmount.setForeground(Color.RED);
		mTxtRefundTotAmount.setText(PosCurrencyUtil.format(0));
//		
//		mTxtRefundTotAmount.setEnabled(false);
		refundTotAmtPanel.add(mTxtRefundTotAmount);
		 
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indocosmo.pos.forms.PosBaseForm#onOkButtonClicked()
	 */
	@Override
	public boolean onOkButtonClicked() {
		
//		if(!validateRefundAmount()) return false;

		boolean refundWholeBill=false; 
		if( PosCurrencyUtil.nRound(PosNumberUtil.parseDoubleSafely(mTxtRefundableAmount.getText()))==0){
			
			final String msg=mPosOrderHObject.getStatus().equals(PosOrderStatus.Refunded)?
					 "This order has been already refunded.":
					 "This bill is not refundable,can't refund zero amount";		 
			PosFormUtil.showInformationMessageBox(this,msg);
			return false;

		}
		final int selectedItemCount=refundItemListPanel.getSelectedItemList()!=null ?refundItemListPanel.getSelectedItemList().size():0;
		
		if(selectedItemCount==0 &&  PosNumberUtil.parseDoubleSafely(mTxtRefundTotAmount.getText())==0  && 
				PosNumberUtil.parseDoubleSafely(mTxtRefundableAmount.getText())==PosNumberUtil.parseDoubleSafely(mTxtTotalAmount.getText())){
			
			if(PosFormUtil.showQuestionMessageBox(this,
					MessageBoxButtonTypes.YesNo ,
					"Do you want to refund whole bill?",null)==MessageBoxResults.No)
				return false;
			else
				refundWholeBill=true;
			
		}else if(selectedItemCount==0 && PosNumberUtil.parseDoubleSafely(mTxtRefundTotAmount.getText()) ==0){
			
//			PosFormUtil.showInformationMessageBox(this, "No items selected. Please select item(s). ");
		 	PosFormUtil.showInformationMessageBox(this, "Nothing to refund, refund amount is 0");
			return false;
			
		}else if(PosNumberUtil.parseDoubleSafely(mTxtRefundTotAmount.getText())>PosNumberUtil.parseDoubleSafely(mTxtRefundableAmount.getText())){
			
			PosFormUtil.showInformationMessageBox(this, "You can not enter an amount greater than refundable amount");
			return false;
		}
//else if( PosNumberUtil.parseDoubleSafely(mTxtRefundTotAmount.getText()) ==0){
//			
////			PosFormUtil.showInformationMessageBox(this, "No items selected. Please select item(s). ");
//		 	PosFormUtil.showInformationMessageBox(this, "Nothing to refund, refund amount is 0");
//			return false;
//			
//		}
		
		 try{
				 
				 //save  to  order payment table  
				 BeanOrderPayment orderPayment= getPaymentDetails(refundWholeBill);
				 

				if (!showCreditCards(orderPayment)) return false;
				 
				 final int counter =mPosOrderHObject.getOrderPaymentItems().size(); 
				 final String paymentId=PosOrderUtil.appendToId(mPosOrderHObject.getOrderId(), counter);
				 orderPayment.setId(paymentId);
				  
				 // save to order_refund
				 ArrayList<BeanOrderRefund> refundDetList=getOrderRefundDetails(paymentId,refundWholeBill);
				 
				 updateRefund(orderPayment, refundDetList, refundWholeBill);
				
				 if (PosEnvSettings.getInstance().isEnabledHMSIntegration()  &&
						 orderPayment.getPaymentMode().equals(PaymentMode.Company) &&
						 mPosOrderHObject.getCustomer().getCustType().getCode().equals(PosCustomerTypeProvider.ROOM_TYPE_CODE)){
					  
				 		 HmsUtil.saveRefundDetais( orderPayment.getId(), 
				 				mPosOrderHObject.getCustomer().getName(),
				 				 PosNumberUtil.parseDoubleSafely(mTxtRefundTotAmount.getText()) 
				 				  );
						
					}
				 
				 SynchronizeToServer.synchronizeTable(
						 SynchronizeToServer.SyncTable.ORDER_HDRS.getCode(),
						 "order_hdrs.order_id='" + mPosOrderHObject.getOrderId()
						 + "'");
				 
				 SynchronizeToServer.synchronizeTable(
						 SynchronizeToServer.SyncTable.ORDER_REFUNDS.getCode(),
						 "order_hdrs.order_id='" + mPosOrderHObject.getOrderId()
						 + "'");

				 printRefundReceipt(this, true,refundWholeBill);

					if(mRefundListner!=null){
						mRefundListner.onRefundDone(this, mPosOrderHObject);
					}

				 
		 }catch(Exception ex){
			 
			 PosLog.write(this, "onOkButtonClicked", ex);
			 PosFormUtil.showErrorMessageBox(this, "Failed to save refund. Please contact administrator.");
		 }

		 return true;
	}
	 
	/*
	 * 
	 */
	private BeanOrderPayment getPaymentDetails(boolean refundWholeBill){
		
		BeanOrderPayment orderPayment=new BeanOrderPayment();;
		
		//  select existing order payment record
		if (mSelectedPayMode!=PaymentMode.Cash){
				
			for(BeanOrderPayment ordPayment : mPosOrderHObject.getOrderPaymentItems()){
				
				if (ordPayment.getPaymentMode()== mSelectedPayMode ){
					
					orderPayment=ordPayment.clone();
					break;
				}
			}
		}
//		else
//			 orderPayment=new BeanOrderPayment();
			
		//to set payment id 
		final int counter =mPosOrderHObject.getOrderPaymentItems().size(); 
		final String id=PosOrderUtil.appendToId(mPosOrderHObject.getOrderId(), counter );

		orderPayment.setOrderId(mPosOrderHObject.getOrderId());
		orderPayment.setId(id);
		orderPayment.setPaymentMode(mSelectedPayMode);
		
		if(refundWholeBill){
			
			final double  refundableAmt=PosNumberUtil.parseDoubleSafely(mTxtRefundableAmount.getText());
			final double roundedAmt=(mSelectedPayMode==PaymentMode.Card?refundableAmt:PosCurrencyUtil.nRound(refundableAmt));
			mRoundingAdjAmt=roundedAmt-refundableAmt;
			
			orderPayment.setPaidAmount(roundedAmt);
				
		}else
			orderPayment.setPaidAmount(PosNumberUtil.parseDoubleSafely(mTxtRefundTotAmount.getText()));
			
		
		orderPayment.setRepayment(true);
		orderPayment.setCashierID(PosEnvSettings.getInstance().getCashierShiftInfo()
					.getCashierInfo().getId());
		orderPayment.setPaymentTime(PosDateUtil.getDateTime());
		orderPayment.setPaymentDate(PosEnvSettings.getPosEnvSettings().getPosDate());
		orderPayment.setNew(true);
		 return orderPayment;
	}
	
	/*
	 * 
	 */
	private ArrayList<BeanOrderRefund> getOrderRefundDetails(String paymentId,boolean refundWholeBill){
		  
		ArrayList<BeanOrderRefund> refundDet=new ArrayList<BeanOrderRefund>();
		 
		final ArrayList<BeanOrderDetail> orderDetItems=refundItemListPanel.getSelectedItemList();
		
		if (orderDetItems!=null && orderDetItems.size()>0){
			
			for(BeanOrderDetail odet :orderDetItems){
				
				BeanOrderRefund ordRefund=new BeanOrderRefund();
				ordRefund.setOrderId( mPosOrderHObject.getOrderId());
				ordRefund.setOrderPaymentId(paymentId);
				ordRefund.setOrderDetail(odet);
				ordRefund.setPaidAmount(odet.getRefundAmount());
				ordRefund.setQuantity(odet.getRefundQuantity());
				ordRefund.setTax1Amount(odet.getSaleItem().getT1TaxAmount());
				ordRefund.setTax2Amount(odet.getSaleItem().getT2TaxAmount());
				ordRefund.setTax3Amount(odet.getSaleItem().getT3TaxAmount());
				ordRefund.setTaxSCAmount(odet.getSaleItem().getServiceTaxAmount());
				ordRefund.setTaxGSTAmount(odet.getSaleItem().getGSTAmount());
				ordRefund.setRefundedBy(getActiveUser());
				refundDet.add(ordRefund);
			}
		}else if(refundWholeBill && !mPosOrderHObject.getStatus().equals(PosOrderStatus.Partial)){
			
			final ArrayList<BeanOrderDetail> pendingRefunbleDetItems=refundItemListPanel.getRefundableItemList();
			
			if (pendingRefunbleDetItems!=null && pendingRefunbleDetItems.size()>0){
				
				for(BeanOrderDetail odet :pendingRefunbleDetItems){
					
					BeanOrderRefund ordRefund=new BeanOrderRefund();
					ordRefund.setOrderId(mPosOrderHObject.getOrderId());
					ordRefund.setOrderPaymentId(paymentId);
					ordRefund.setOrderDetail(odet);
					ordRefund.setPaidAmount(odet.getRefundAmount());
					ordRefund.setQuantity(odet.getRefundQuantity());
					ordRefund.setTax1Amount(odet.getSaleItem().getT1TaxAmount());
					ordRefund.setTax2Amount(odet.getSaleItem().getT2TaxAmount());
					ordRefund.setTax3Amount(odet.getSaleItem().getT3TaxAmount());
					ordRefund.setTaxSCAmount(odet.getSaleItem().getServiceTaxAmount());
					ordRefund.setTaxGSTAmount(odet.getSaleItem().getGSTAmount());
					ordRefund.setRefundedBy(getActiveUser());
					refundDet.add(ordRefund);
				}
			}
	
		}
		if(refundDet.size()==0){
			
			BeanOrderRefund ordRefund=new BeanOrderRefund();
			ordRefund.setOrderId( mPosOrderHObject.getOrderId());
			ordRefund.setOrderPaymentId(paymentId);
			ordRefund.setPaidAmount(0);
			refundDet.add(ordRefund);
		}
		return refundDet;
		
	}
	
	 
	
	/*
	 * 
	 */
	private boolean showCreditCards(final BeanOrderPayment orderPayment){
		
		boolean result=true;
		if (mSelectedPayMode!=PaymentMode.Card) 
			return result;
	
		ArrayList<String> creditCardList=new ArrayList<String>();
		ArrayList<IPosBrowsableItem> itemList=new ArrayList<IPosBrowsableItem>();
		
		for(BeanOrderPayment oPay : mPosOrderHObject.getOrderPaymentItems()){
			if (oPay.getPaymentMode()==PaymentMode.Card && !creditCardList.contains(oPay.getCardNo())){
				
				final String cardNo=oPay.getCardNo();
				
				if(cardNo!=null && cardNo.trim()!=""){
					
					creditCardList.add(cardNo);
					itemList.add(oPay);
				}
			}
		}
		
		if (itemList.size()<=1)
			return result;
		
		final PosObjectBrowserForm form=new PosObjectBrowserForm("Select Credit Card",itemList,ItemSize.Wider,true);
		form.setListner(new IPosObjectBrowserListner() {
			
			@Override
			public void onItemSelected(IPosBrowsableItem item) {
				
				final BeanOrderPayment oPay=(BeanOrderPayment) item;
				orderPayment.setCardName(oPay.getCardName());
				orderPayment.setCardType(oPay.getCardType());
				orderPayment.setCardNo(oPay.getCardNo());
				orderPayment.setCardApprovalCode(oPay.getCardApprovalCode());
				orderPayment.setCardExpiryMonth(oPay.getCardExpiryMonth());
				orderPayment.setCardExpiryYear(oPay.getCardExpiryYear());
					
			}
			
			@Override
			public void onCancel() {
				// TODO Auto-generated method stub
				
			}
		});
		PosFormUtil.showLightBoxModal(this,form);
		return !form.isCancelled();
	}
	
	private BeanOrderDetail getOrderDetilObjectFromList(BeanOrderRefund ordRefItem ){
		
		
			BeanOrderDetail orderDetail=null;
			
			for(BeanOrderDetail dtlItem: mPosOrderHObject.getOrderDetailItems()){

				if((ordRefItem.getOrderDetail().getParentDtlId()==null || ordRefItem.getOrderDetail().getParentDtlId().equals("")) && 
						ordRefItem.getOrderDetail().getId().equals(dtlItem.getId())){
					
					orderDetail=dtlItem;
					break;
				}else if(ordRefItem.getOrderDetail().getParentDtlId()!=null && dtlItem.getId().equals(ordRefItem.getOrderDetail().getParentDtlId())){
					

					/**If has combo contents print them**/
					if(dtlItem.isComboContentsSelected())

						for(ArrayList<BeanOrderDetail> subItemList:dtlItem.getComboSubstitutes().values())

							for(BeanOrderDetail item:subItemList)

								if(ordRefItem.getOrderDetail().getId().equals(item.getId())){

									orderDetail=item;
									break;
								}
							
						
					

					/**If has extras print them**/
					if(dtlItem.isExtraItemsSelected())

						for(ArrayList<BeanOrderDetail> subItemList:dtlItem.getExtraItemList().values())

							for(BeanOrderDetail item:subItemList)

								if(ordRefItem.getOrderDetail().getId().equals(item.getId())){

									orderDetail=item;
									break;
								}
							
						
					
				 
				}
			}
			return orderDetail;
	}
	
	private void updateRefund(BeanOrderPayment orderPayment,
			ArrayList<BeanOrderRefund>   refundDetList ,
			boolean refundWholeBill) throws Exception{
		
		double totalTax1=0;
		double totalTax2=0;
		double totalTax3=0;
		double totalGST=0;
		double totalSC=0;
		double detailRefundTotal=0;
		double detailItemTotal=0;

		ArrayList<BeanOrderDetail> ordDetItems=new ArrayList<BeanOrderDetail>();
		BeanOrderPaymentHeader orderPaymentHdr=new BeanOrderPaymentHeader();

		 
		 // Calculating detail item total and tax total
		
		for(BeanOrderRefund ordRefItem : refundDetList){

			BeanOrderDetail ordDet =getOrderDetilObjectFromList(ordRefItem);
			 
				if(ordDet!=null &&  ordRefItem.getOrderDetail().getId().equals(ordDet.getId()) ){

				
					if (ordDet.getSaleItem().getItemTotal()!=ordRefItem.getPaidAmount()){

						ordDet.getSaleItem().getTax().setTaxAmount(calcTaxInclusive(ordDet.getSaleItem(),ordRefItem.getPaidAmount()));
						ordRefItem.setTax1Amount(ordDet.getSaleItem().getT1TaxAmount());
						ordRefItem.setTax2Amount(ordDet.getSaleItem().getT2TaxAmount());
						ordRefItem.setTax3Amount(ordDet.getSaleItem().getT3TaxAmount());
						ordRefItem.setTaxSCAmount(ordDet.getSaleItem().getServiceTaxAmount());
						ordRefItem.setTaxGSTAmount(ordDet.getSaleItem().getGSTAmount());
					}
					totalTax1 +=ordDet.getSaleItem().getT1TaxAmount();
					totalTax2 +=ordDet.getSaleItem().getT2TaxAmount();
					totalTax3 += ordDet.getSaleItem().getT3TaxAmount();
					totalGST += ordDet.getSaleItem().getGSTAmount();
					totalSC +=ordDet.getSaleItem().getServiceTaxAmount();
					
					detailRefundTotal+=ordRefItem.getPaidAmount();
					
					ordDetItems.add(ordDet);
					
				}
		}
		
		if(refundWholeBill && mPosOrderHObject.getExtraCharges()>0 ){
			
			totalTax1 +=(mPosOrderHObject.getExtraChargeTaxOneAmount()-
					mPosOrderHObject.getExtraChargeTaxOneAmount()*mPosOrderHObject.getBillDiscountPercentage()/100);
			totalTax2 +=(mPosOrderHObject.getExtraChargeTaxTwoAmount()-
					mPosOrderHObject.getExtraChargeTaxTwoAmount()*mPosOrderHObject.getBillDiscountPercentage()/100);
			totalTax3 +=(mPosOrderHObject.getExtraChargeTaxThreeAmount()-
					mPosOrderHObject.getExtraChargeTaxThreeAmount()*mPosOrderHObject.getBillDiscountPercentage()/100);
			totalSC +=(mPosOrderHObject.getExtraChargeSCAmount()-
					mPosOrderHObject.getExtraChargeSCAmount()*mPosOrderHObject.getBillDiscountPercentage()/100);
			
			totalGST +=(mPosOrderHObject.getExtraChargeGSTAmount()-
					mPosOrderHObject.getExtraChargeGSTAmount()*mPosOrderHObject.getBillDiscountPercentage()/100);
			
			final double extraCharge=mPosOrderHObject.getExtraCharges() +  PosOrderUtil.getExtraChargeTotalTaxAmount(mPosOrderHObject);
			detailRefundTotal+=(extraCharge -extraCharge*mPosOrderHObject.getBillDiscountPercentage()/100);
		}
	 

		
		//create new  payment hdr id 
		final int counter =mPosOrderHObject.getOrderPaymentHeaders().size(); 
		final String paymentHdrId=PosOrderUtil.appendToId(mPosOrderHObject.getOrderId(), counter );
		
		//assign payment hdr values to order payment object 
		 orderPayment.setOrderPaymentHdrId(paymentHdrId);
	
		 
		// load values into payment header object
		orderPaymentHdr.setId(paymentHdrId);
		orderPaymentHdr.setOrderId(mPosOrderHObject.getOrderId());
		orderPaymentHdr.setTotalAmount(orderPayment.getPaidAmount());
		
		if(!mPosOrderHObject.getStatus().equals(PosOrderStatus.Partial)){
//			orderPaymentHdr.setTotalAmount(refundWholeBill ?mPosOrderHObject.getTotalAmount():detailRefundTotal);
			orderPaymentHdr.setTotalAmount(detailRefundTotal);
//			orderPaymentHdr.setDetailTotal(detailRefundTotal-(totalTax1+totalTax2+totalTax3 +totalGST +totalSC ));
			orderPaymentHdr.setDetailTotal(PosCurrencyUtil.roundTo(detailRefundTotal));
			orderPaymentHdr.setTotalTax1(PosCurrencyUtil.roundTo(totalTax1));
			orderPaymentHdr.setTotalTax2(PosCurrencyUtil.roundTo(totalTax2));
			orderPaymentHdr.setTotalTax3(PosCurrencyUtil.roundTo(totalTax3));
			orderPaymentHdr.setTotalGST(PosCurrencyUtil.roundTo(totalGST));
			orderPaymentHdr.setTotalServiceTax(PosCurrencyUtil.roundTo(totalSC));
//			orderPaymentHdr.setTotalDetailDiscount(refundWholeBill ? mPosOrderHObject.getTotalDetailDiscount():0 );
//			orderPaymentHdr.setTotalDetailDiscount(PosCurrencyUtil.roundTo(detailItemTotal-detailRefundTotal));
			orderPaymentHdr.setTotalDetailDiscount(0);
		}  
		
		orderPaymentHdr.setTotalAmountPaid(orderPayment.getPaidAmount());
		orderPaymentHdr.setBillTaxAmount(0);
		orderPaymentHdr.setChangeAmount(0);
		orderPaymentHdr.setCashOut(0 );
		orderPaymentHdr.setBillDiscountAmount(0);// refundWholeBill ? mPosOrderHObject.getBillDiscountAmount():0 );
		orderPaymentHdr.setRoundAdjustmentAmount(refundWholeBill ? mPosOrderHObject.getRoundAdjustmentAmount():mRoundingAdjAmt );
		orderPaymentHdr.setRemarks(mRemarks);
		orderPaymentHdr.setRefund(true);
		
		orderPaymentHdr.setShiftId(PosEnvSettings.getInstance().getCashierShiftInfo().getShiftItem().getId());
		orderPaymentHdr.setPaymentTime(PosDateUtil.getDateTime());
		orderPaymentHdr.setPaymentDate(PosEnvSettings.getPosEnvSettings().getPosDate());
		
		orderPaymentHdr.setNew(true);

		//Saving values into database
	 
		PosOrderPaymentsProvider ordPaymentProvider=new PosOrderPaymentsProvider();
		PosOrderHdrProvider ordHdrProvider=new PosOrderHdrProvider();
		PosOrderPaymentHdrProvider ordPaymentHdrProvider=new PosOrderPaymentHdrProvider();
		
		ordPaymentHdrProvider.save(orderPaymentHdr);
		
		ordPaymentProvider.save(orderPayment);
		
		 ordRefundProvider.save(refundDetList);
		
		 //save to order_hdr
		 if(mPosOrderHObject.getStatus().equals(PosOrderStatus.Partial)){
			 
			 mPosOrderHObject.setStatus(PosOrderStatus.Partial);
			 mPosOrderHObject.setRefundAmount(PosCurrencyUtil.roundTo(mPosOrderHObject.getRefundAmount() +  orderPaymentHdr.getTotalAmountPaid()));
			 mPosOrderHObject.setRefundTotalAmountTax1(0);
			 mPosOrderHObject.setRefundTotalAmountTax2(0);
			 mPosOrderHObject.setRefundTotalAmountTax3(0);
			 mPosOrderHObject.setRefundTotalAmountServiceTax(0);
			 mPosOrderHObject.setRefundTotalAmountGST(0);
		 }else{
			 
			 mPosOrderHObject.setStatus(PosOrderStatus.Refunded);
			 
			 final double tax1Amt=mPosOrderHObject.getRefundTotalAmountTax1() + orderPaymentHdr.getTotalTax1();
			 final double tax2Amt=mPosOrderHObject.getRefundTotalAmountTax2() + orderPaymentHdr.getTotalTax2();
			 final double tax3Amt=mPosOrderHObject.getRefundTotalAmountTax3() + orderPaymentHdr.getTotalTax3();
			 final double serviceTaxAmt=mPosOrderHObject.getRefundTotalAmountServiceTax() + orderPaymentHdr.getTotalServiceTax();
			 final double gstAmt=mPosOrderHObject.getRefundTotalAmountGST()+ orderPaymentHdr.getTotalGST();
			
			
			 mPosOrderHObject.setRefundAmount(PosCurrencyUtil.roundTo(mPosOrderHObject.getRefundAmount() +  orderPaymentHdr.getTotalAmountPaid()));
			 mPosOrderHObject.setRefundTotalAmountTax1(PosCurrencyUtil.roundTo(tax1Amt));
			 mPosOrderHObject.setRefundTotalAmountTax2(PosCurrencyUtil.roundTo(tax2Amt));
			 mPosOrderHObject.setRefundTotalAmountTax3(PosCurrencyUtil.roundTo(tax3Amt));
			 mPosOrderHObject.setRefundTotalAmountServiceTax(PosCurrencyUtil.roundTo(serviceTaxAmt));
			 mPosOrderHObject.setRefundTotalAmountGST(PosCurrencyUtil.roundTo(gstAmt));
		 }
 		 ordHdrProvider.saveRefundAmount(mPosOrderHObject);
		 
	
		//Assigning values for refund receipt printing 
 
 		 mPosOrderHObject.setRefundTotalAmountTax1(totalTax1);
		 mPosOrderHObject.setRefundTotalAmountTax2(totalTax2);
		 mPosOrderHObject.setRefundTotalAmountTax3(totalTax3);
		 mPosOrderHObject.setRefundTotalAmountGST(totalGST);
		 mPosOrderHObject.setRefundTotalAmountServiceTax(totalSC);
		 mPosOrderHObject.setTotalRefundAmount(orderPayment.getPaidAmount());
		 mPosOrderHObject.setRoundAdjustmentAmount(mRoundingAdjAmt);
		
		 ArrayList<BeanOrderPaymentHeader> ordPaymentHdrList=new ArrayList<BeanOrderPaymentHeader>();
		 ArrayList<BeanOrderPayment> ordPaymentList=new ArrayList<BeanOrderPayment>();
		 
		 ordPaymentHdrList.add(orderPaymentHdr);
		 ordPaymentList.add(orderPayment);

		 mPosOrderHObject.setOrderDetailItems(ordDetItems);
		mPosOrderHObject.setOrderPaymentItems(ordPaymentList);
		 mPosOrderHObject.setOrderPaymentHeaders(ordPaymentHdrList);
		
//		return refundHdr;
	}
	
	/*
	 * 
	 */
	
	private void printRefundReceipt(Object sender ,boolean openCashBox,boolean refundWholeBill) {
		
		if(PosEnvSettings.getInstance().getPrintSettings().getReceiptPrintingAtRefund()!=EnablePrintingOption.NO){
			
			final boolean forcePrint=(PosEnvSettings.getInstance().getPrintSettings().getReceiptPrintingAtRefund()==EnablePrintingOption.FORCE);
			try {

				PosFormUtil.showRefundPrintConfirmMessage((RootPaneContainer)sender, forcePrint, mPosOrderHObject, openCashBox);
				
			} catch (Exception err) {
				PosLog.write(this, "printrefundReceipt", err);
				PosFormUtil.showErrorMessageBox(this, "Error in printing!!");
			}
		}
	}
	 
	
 
	/**
	 * @param item
	 * @return
	 */
	private   PosTaxAmountObject calcTaxInclusive(BeanSaleItem item,double adjustedAmount){
	
		PosTaxAmountObject itemTaxAmount=null;
		
		final double itemTotalPrice =adjustedAmount;// PosSaleItemUtil.getItemFixedPrice(item)*item.getQuantity();
		/*
		 * When tax is calculated on items with discount, 
		 * first deduct the discount amount from the total item price and 
		 * then calculate tax. This is needed to avoid confusion in customers.
		 */
		final double discAmount=PosSaleItemUtil.getTotalDiscountAmount(item);
		final double itemTaxableAmount=itemTotalPrice;//-discAmount;
		
		final double gstAmount = PosTaxUtil.getGSTAmount(item.getTax(),itemTaxableAmount);
		final double itemPriceWOGst =itemTaxableAmount- gstAmount;

		final double totalTaxRate = PosTaxUtil.getTotalTaxRate(item.getTax());
		final double itemPriceExclTax = itemPriceWOGst / (1 + (totalTaxRate / 100));
		
		itemTaxAmount= PosTaxUtil.calculateTaxes(item.getTax(),itemPriceExclTax);
		
		return itemTaxAmount;
	}
	
 

}

  
