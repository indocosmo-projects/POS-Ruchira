/**
 * 
 */
package com.indocosmo.pos.forms;

import javax.swing.JPanel;

/**
 * @author jojesh
 *
 */
public class PosClosedOrderEditFormCopy extends PosBaseForm {

	/**
	 * @param title
	 * @param cPanelwidth
	 * @param cPanelHeight
	 */
	public PosClosedOrderEditFormCopy(String title, int cPanelwidth,
			int cPanelHeight) {
		super(title, cPanelwidth, cPanelHeight);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.pos.forms.PosBaseForm#setContentPanel(javax.swing.JPanel)
	 */
	@Override
	protected void setContentPanel(JPanel panel) {
		// TODO Auto-generated method stub
		
	}

//	private static final int PANEL_WIDTH=1000;
//	private static final int PANEL_HEIGHT=600;
//	private static final int PANEL_CONTENT_V_GAP=2;
//	private static final int PANEL_CONTENT_H_GAP=2;
//	private static final int ORDER_ITEM_LIST_PANEL_HEIGHT= PANEL_HEIGHT-PANEL_CONTENT_V_GAP*2;
//	
//	private static final String REMARKS_IMAGE_ITEM_BUTTON = "dlg_remarks.png";
//	private static final String REMARKS_IMAGE_ITEM_BUTTON_TOUCH = "dlg_remarks_touch.png";
//	
//	private PosOrderListPanel mOrderListPanel;
//	private PosOrderListOptionPanel mOrderListOptionPanel;
//	private JPanel mContianerPanel;
//	
//	protected Font mItemTitleLableFont=new Font("FreeSans", Font.PLAIN, 15);
//	
//	private PosOrderHdrProvider mOderHdrProvider;
//	/**
//	 * @param title
//	 * @param cPanelwidth
//	 * @param cPanelHeight
//	 */
//	public PosClosedOrderEditFormCopy() {
//		super("Closed Bill Editing", PANEL_WIDTH, PANEL_HEIGHT);
//		mOrderHeaderList=null;
//		setOkButtonCaption("Make Payment");
//		setCancelButtonCaption("Close");
//		createRetrieveButton();
//		createRePrintButton();
//		createRemarksButton();
//		createVoidButton();
//		createBillSummaryPanel();
//		createPaymentSummaryPanel();
//		createRefundSummaryPanel();
//		createRemarksSummaryPanel();
//		mOderHdrProvider=new PosOrderHdrProvider();
//		resetFields();
//	}
//
//	/* (non-Javadoc)
//	 * @see com.indocosmo.pos.forms.PosBaseForm#setContentPanel(javax.swing.JPanel)
//	 */
//	@Override
//	protected void setContentPanel(JPanel panel) {
//		mContianerPanel=panel;
//		mContianerPanel.setLayout(null);
//		setItemGridPanel();
//	}
//	
//	private void setItemGridPanel(){
//		int left= 0;
//		int top=0;
//		mOrderListOptionPanel=new PosOrderListOptionPanel(left, top, this);
//		mOrderListOptionPanel.setOpaque(false);
//		mContianerPanel.add(mOrderListOptionPanel);
//		top+=PosOrderListOptionPanel.PANEL_HEIGHT-1;
//		mOrderListPanel=new PosOrderListPanel(left, top,PANEL_HEIGHT-PosOrderListOptionPanel.PANEL_HEIGHT-PANEL_CONTENT_V_GAP);
//		mOrderListPanel.setReadOnly(true);
//		mOrderListPanel.setListner(new PosOrderListPanelAdapter() {
//			@Override
//			public void onItemDeleted(PosOrderListItemControl item) {
//				populateRefundSummaryPanel(mSelectedOrderDetails);
//			}
//		});
//		mContianerPanel.add(mOrderListPanel);
//		mOrderListOptionPanel.setOrderListPanel(mOrderListPanel);
//		mOrderListOptionPanel.setAddButtonEnabled(false);
//		mOrderListOptionPanel.setQtyButtonsEnabled(false);
//		mOrderListOptionPanel.setNumKeyPadButtonEnabled(false);
//	}
//	
//	private void createRemarksButton(){
//		PosButton button = new PosButton();
//		button.setText("Remarks");
//		button.setImage(PosResUtil
//				.getImageIconFromResource(REMARKS_IMAGE_ITEM_BUTTON));
//		button.setTouchedImage(PosResUtil
//				.getImageIconFromResource(REMARKS_IMAGE_ITEM_BUTTON_TOUCH));
//		button.setHorizontalAlignment(SwingConstants.CENTER);
//		button.setSize(IMAGE_BUTTON_WIDTH, IMAGE_BUTTON_HEIGHT);
//		button.setOnClickListner(new IPosButtonListner() {
//			
//			@Override
//			public void onClicked(PosButton button) {
//				editRemarks();
//			}
//		});
//		addButtonsToBottomPanel(button,3);
//		
//	}
//	
//	private void editRemarks(){
//		if(mSelectedOrderDetails!=null){
//			PosOrderRemarksEntryForm remarksform=new PosOrderRemarksEntryForm();
//			remarksform.setOrderHeaderItem(mSelectedOrderDetails);
//			PosFormUtil.showLightBoxModal(this, remarksform);
//			populateBillRemarksPanel(mSelectedOrderDetails);
//		}
//	}
//	
//	private void createVoidButton(){
//		PosButton button = new PosButton();
//		button.setText("Void Bill");
//		button.setImage(PosResUtil
//				.getImageIconFromResource(IMAGE_BUTTON_CANCEL));
//		button.setTouchedImage(PosResUtil
//				.getImageIconFromResource(IMAGE_BUTTON_CANCEL_TOUCH));
//		button.setHorizontalAlignment(SwingConstants.CENTER);
//		button.setSize(IMAGE_BUTTON_WIDTH, IMAGE_BUTTON_HEIGHT);
//		button.setEnabled(false);
////		mButtonRemarks.setOnClickListner(imgRemarksButtonListner);
//		addButtonsToBottomPanel(button,4);
//		
//	}
//	
//	private void createRetrieveButton(){
//		PosButton button = new PosButton();
//		button.setText("Retrieve Order");
//		button.setImage(PosResUtil
//				.getImageIconFromResource(IMAGE_BUTTON_OK));
//		button.setTouchedImage(PosResUtil
//				.getImageIconFromResource(IMAGE_BUTTON_OK_TOUCH));
//		button.setHorizontalAlignment(SwingConstants.CENTER);
//		button.setSize(IMAGE_BUTTON_WIDTH, IMAGE_BUTTON_HEIGHT);
//		button.setOnClickListner(new IPosButtonListner() {
//			
//			@Override
//			public void onClicked(PosButton button) {
//				showOrderListSearch();
//			}
//		});
//		addButtonsToBottomPanel(button,0);
//		
//	}
//	
//	private void createRePrintButton(){
//		PosButton button = new PosButton();
//		button.setText("Print");
//		button.setImage(PosResUtil
//				.getImageIconFromResource(IMAGE_BUTTON_OK));
//		button.setTouchedImage(PosResUtil
//				.getImageIconFromResource(IMAGE_BUTTON_OK_TOUCH));
//		button.setHorizontalAlignment(SwingConstants.CENTER);
//		button.setSize(IMAGE_BUTTON_WIDTH, IMAGE_BUTTON_HEIGHT);
//		button.setOnClickListner(new IPosButtonListner() {
//			
//			@Override
//			public void onClicked(PosButton button) {
//				printBill(mSelectedOrderDetails);
//			}
//		});
//		addButtonsToBottomPanel(button,2);
//		
//	}
//	
//	private void printBill(PosOrderHeaderObject orderHeader) {
//		try {
//			PosReceiptBase.printBill(orderHeader,true);
//		} catch (Exception err) {
//			PosLog.write(this, "printBill", err);
//			PosFormUtil.showErrorMessageBox(this, "Error in printing!!");
//		}
//	}
//	
//	private static ArrayList<PosOrderHeaderObject> mOrderHeaderList=null;
//	private void showOrderListSearch(){
//		try {
//			if(mOrderHeaderList==null)
//				mOrderHeaderList = mOderHdrProvider.getOrderHeaders(PosOrderStatus.Closed);
//			PosItemExtSearchForm serachForm=new PosOrderSerachForm(mOrderHeaderList);
//			serachForm.setListner(new PosSoftKeypadAdapter(){
//				@Override
//				public void onAccepted(String text) {
//					populateOrderDetails(PosOrderUtil.getFormatedOrderId(text));
//				}
//			});
//			PosFormUtil.showLightBoxModal(serachForm);
//		} catch (Exception e) {
//			PosLog.write(this, "showOrderListSearch", e);
//			PosFormUtil.showErrorMessageBox(null,"Failed get order list. Please check the log for details");
//		}
//
//	}
//	
//	private PosOrderHeaderObject mSelectedOrderDetails; 
//	private void populateOrderDetails(String orderID){
//		try {
//			final String orderHeaderID=orderID;
//			mSelectedOrderDetails=mOderHdrProvider.getOrderData(orderHeaderID);
//			if(mSelectedOrderDetails==null){
//				PosFormUtil.showInformationMessageBox(this, "No such order exist. Please make sure that the order number is correct.");
//				return;
//			}
//			mOrderListPanel.setPosOrderEntryItem(mSelectedOrderDetails);
//			resetFields();
//			populateBillSummaryPanel(mSelectedOrderDetails);
//			populateBillRemarksPanel(mSelectedOrderDetails);
//			populatePaymentSummaryPanel(mSelectedOrderDetails);
//			populateRefundSummaryPanel(mSelectedOrderDetails);
//		} catch (Exception e) {
//			PosLog.write(this, "populateOrderDetails", e);
//			PosFormUtil.showErrorMessageBox(this, "Failed to load the order details.");
//		}
//	}
//	
//	private JPanel mPanelBillSummary;
//	private JLabel mLabelBillNoValue;
//	private JLabel mLabelBillTimeValue;
//	private JLabel mLabelBillDateValue;
//	private JLabel mLabelCustomerValue;
//	private JLabel mLabelCashierValue;
//	private JLabel mLabelBillTotalValue;
//	
//	private void createBillSummaryPanel(){
//		final int HORIZONTAL_GAP=10;
//		final int VERTICAL_GAP=15;
//		int left=mOrderListPanel.getX()+mOrderListPanel.getWidth()+PANEL_CONTENT_H_GAP;
//		int top=PANEL_CONTENT_V_GAP;
//		int width=PANEL_WIDTH-mOrderListPanel.getWidth()-PANEL_CONTENT_H_GAP*3;
//		int height=150;
//		mPanelBillSummary=createBoxedPanel("Bill Summary", width, height);
//		mPanelBillSummary.setLocation(left,top);
//		mContianerPanel.add(mPanelBillSummary);
//		
//		Font font=mItemTitleLableFont.deriveFont(Font.BOLD);
//		left=10;
//		top=25;
//		width=100;
//		height=30;
//		
//		JLabel labelBillNoTitle=new JLabel("Bill Number :");
//		labelBillNoTitle.setBounds(left, top, width, height);
//		labelBillNoTitle.setBorder(new LineBorder(Color.LIGHT_GRAY));
//		labelBillNoTitle.setFont(font);
//		mPanelBillSummary.add(labelBillNoTitle);
//		
//		top=labelBillNoTitle.getY()+labelBillNoTitle.getHeight()+VERTICAL_GAP;
//		JLabel labelBillDataTitle=new JLabel("Bill Date :");
//		labelBillDataTitle.setBounds(left, top, width, height);
//		labelBillDataTitle.setBorder(new LineBorder(Color.LIGHT_GRAY));
//		labelBillDataTitle.setFont(font);
//		mPanelBillSummary.add(labelBillDataTitle);
//	
//		top=labelBillDataTitle.getY()+labelBillDataTitle.getHeight()+VERTICAL_GAP;
//		JLabel labelBillTimeTitle=new JLabel("Bill Time :");
//		labelBillTimeTitle.setBounds(left, top, width, height);
//		labelBillTimeTitle.setBorder(new LineBorder(Color.LIGHT_GRAY));
//		labelBillTimeTitle.setFont(font);
//		mPanelBillSummary.add(labelBillTimeTitle);
//		
//		left=labelBillTimeTitle.getX()+labelBillTimeTitle.getWidth()+HORIZONTAL_GAP;
//		top=25;
//		width=210;
//		height=30;
//		mLabelBillNoValue=new JLabel("ACSD01-TA-0000000001");
//		mLabelBillNoValue.setBounds(left, top, width, height);
//		mLabelBillNoValue.setBorder(new LineBorder(Color.LIGHT_GRAY));
//		mLabelBillNoValue.setFont(font);
//		mPanelBillSummary.add(mLabelBillNoValue);
//
//		top=mLabelBillNoValue.getY()+mLabelBillNoValue.getHeight()+VERTICAL_GAP;
//		mLabelBillDateValue=new JLabel("2012-10-12");
//		mLabelBillDateValue.setBounds(left, top, width, height);
//		mLabelBillDateValue.setBorder(new LineBorder(Color.LIGHT_GRAY));
//		mLabelBillDateValue.setFont(font);
//		mPanelBillSummary.add(mLabelBillDateValue);
//
//		top=mLabelBillDateValue.getY()+mLabelBillDateValue.getHeight()+VERTICAL_GAP;
//		mLabelBillTimeValue=new JLabel("00:00");
//		mLabelBillTimeValue.setBounds(left, top, width, height);
//		mLabelBillTimeValue.setBorder(new LineBorder(Color.LIGHT_GRAY));
//		mLabelBillTimeValue.setFont(font);
//		mPanelBillSummary.add(mLabelBillTimeValue);
//		
//		left=mLabelBillTimeValue.getX()+mLabelBillTimeValue.getWidth()+HORIZONTAL_GAP;
//		top=25;
//		width=100;
//		height=30;
//		
//		JLabel labelCustomerTitle=new JLabel("Customer :");
//		labelCustomerTitle.setBounds(left, top, width, height);
//		labelCustomerTitle.setBorder(new LineBorder(Color.LIGHT_GRAY));
//		labelCustomerTitle.setFont(font);
//		mPanelBillSummary.add(labelCustomerTitle);
//		
//		top=labelCustomerTitle.getY()+labelCustomerTitle.getHeight()+VERTICAL_GAP;
//		JLabel labelCashierTitle=new JLabel("Cashier :");
//		labelCashierTitle.setBounds(left, top, width, height);
//		labelCashierTitle.setBorder(new LineBorder(Color.LIGHT_GRAY));
//		labelCashierTitle.setFont(font);
//		mPanelBillSummary.add(labelCashierTitle);
//	
//		top=labelCashierTitle.getY()+labelCashierTitle.getHeight()+VERTICAL_GAP;
//		JLabel labelBillTotalTitle=new JLabel("Bill Total :");
//		labelBillTotalTitle.setBounds(left, top, width, height);
//		labelBillTotalTitle.setBorder(new LineBorder(Color.LIGHT_GRAY));
//		labelBillTotalTitle.setFont(font);
//		mPanelBillSummary.add(labelBillTotalTitle);
//		
//		left=labelBillTotalTitle.getX()+labelBillTotalTitle.getWidth()+HORIZONTAL_GAP;
//		top=25;
//		width=215;
//		height=30;
//		mLabelCustomerValue=new JLabel("Jojesh Jose");
//		mLabelCustomerValue.setBounds(left, top, width, height);
//		mLabelCustomerValue.setBorder(new LineBorder(Color.LIGHT_GRAY));
//		mLabelCustomerValue.setFont(font);
//		mPanelBillSummary.add(mLabelCustomerValue);
//		
//		top=mLabelCustomerValue.getY()+mLabelCustomerValue.getHeight()+VERTICAL_GAP;
//		mLabelCashierValue=new JLabel("Anoop");
//		mLabelCashierValue.setBounds(left, top, width, height);
//		mLabelCashierValue.setBorder(new LineBorder(Color.LIGHT_GRAY));
//		mLabelCashierValue.setFont(font);
//		mPanelBillSummary.add(mLabelCashierValue);
//		
//		top=mLabelCashierValue.getY()+mLabelCashierValue.getHeight()+VERTICAL_GAP;
//		mLabelBillTotalValue=new JLabel("10000.00");
//		mLabelBillTotalValue.setBounds(left, top, width, height);
//		mLabelBillTotalValue.setBorder(new LineBorder(Color.LIGHT_GRAY));
//		mLabelBillTotalValue.setFont(font);
//		mLabelBillTotalValue.setHorizontalAlignment(SwingConstants.RIGHT);
//		mPanelBillSummary.add(mLabelBillTotalValue);
//	
//	}
//	
//	private JPanel mPanelPaymentSummary;
//	private JLabel mLabelCashValue;
//	private JLabel mLabelCompanyValue;
//	private JLabel mLabelCardValue;
//	private JLabel mLabelVoucherValue;
//	private JLabel mLabelDiscountValue;
//	private JLabel mLabelBalanceValue;
//	private void createPaymentSummaryPanel(){
//		final int HORIZONTAL_GAP=10;
//		final int VERTICAL_GAP=15;
//		int left=mOrderListPanel.getX()+mOrderListPanel.getWidth()+PANEL_CONTENT_H_GAP;
//		int top=mPanelBillSummary.getY()+mPanelBillSummary.getHeight()+PANEL_CONTENT_V_GAP;
//		int width=PANEL_WIDTH-mOrderListPanel.getWidth()-PANEL_CONTENT_H_GAP*3;
//		int height=150;
//		mPanelPaymentSummary=createBoxedPanel("Payment Summary", width, height);
//		mPanelPaymentSummary.setLocation(left,top);
//		mContianerPanel.add(mPanelPaymentSummary);
//		
//		Font font=mItemTitleLableFont.deriveFont(Font.BOLD);
//		left=10;
//		top=25;
//		width=100;
//		height=30;
//		
//		JLabel labelCashTitle=new JLabel("Cash :");
//		labelCashTitle.setBounds(left, top, width, height);
//		labelCashTitle.setBorder(new LineBorder(Color.LIGHT_GRAY));
//		labelCashTitle.setFont(font);
//		mPanelPaymentSummary.add(labelCashTitle);
//		
//		top=labelCashTitle.getY()+labelCashTitle.getHeight()+VERTICAL_GAP;
//		JLabel labelCardTitle=new JLabel("Card :");
//		labelCardTitle.setBounds(left, top, width, height);
//		labelCardTitle.setBorder(new LineBorder(Color.LIGHT_GRAY));
//		labelCardTitle.setFont(font);
//		mPanelPaymentSummary.add(labelCardTitle);
//	
//		top=labelCardTitle.getY()+labelCardTitle.getHeight()+VERTICAL_GAP;
//		JLabel labelCompanyTitle=new JLabel("Company :");
//		labelCompanyTitle.setBounds(left, top, width, height);
//		labelCompanyTitle.setBorder(new LineBorder(Color.LIGHT_GRAY));
//		labelCompanyTitle.setFont(font);
//		mPanelPaymentSummary.add(labelCompanyTitle);
//		
//		left=labelCashTitle.getX()+labelCashTitle.getWidth()+HORIZONTAL_GAP;
//		top=25;
//		width=210;
//		height=30;
//		mLabelCashValue=new JLabel("00.00");
//		mLabelCashValue.setBounds(left, top, width, height);
//		mLabelCashValue.setBorder(new LineBorder(Color.LIGHT_GRAY));
//		mLabelCashValue.setFont(font);
//		mLabelCashValue.setHorizontalAlignment(SwingConstants.RIGHT);
//		mPanelPaymentSummary.add(mLabelCashValue);
//
//		top=mLabelCashValue.getY()+mLabelCashValue.getHeight()+VERTICAL_GAP;
//		mLabelCardValue=new JLabel("00.00");
//		mLabelCardValue.setBounds(left, top, width, height);
//		mLabelCardValue.setBorder(new LineBorder(Color.LIGHT_GRAY));
//		mLabelCardValue.setFont(font);
//		mLabelCardValue.setHorizontalAlignment(SwingConstants.RIGHT);
//		mPanelPaymentSummary.add(mLabelCardValue);
//
//		top=mLabelCardValue.getY()+mLabelCardValue.getHeight()+VERTICAL_GAP;
//		mLabelCompanyValue=new JLabel("00:00");
//		mLabelCompanyValue.setBounds(left, top, width, height);
//		mLabelCompanyValue.setBorder(new LineBorder(Color.LIGHT_GRAY));
//		mLabelCompanyValue.setFont(font);
//		mLabelCompanyValue.setHorizontalAlignment(SwingConstants.RIGHT);
//		mPanelPaymentSummary.add(mLabelCompanyValue);
//		
//		left=mLabelCompanyValue.getX()+mLabelCompanyValue.getWidth()+HORIZONTAL_GAP;
//		top=25;
//		width=100;
//		height=30;
//		
//		JLabel labelVoucherTitle=new JLabel("Voucher :");
//		labelVoucherTitle.setBounds(left, top, width, height);
//		labelVoucherTitle.setBorder(new LineBorder(Color.LIGHT_GRAY));
//		labelVoucherTitle.setFont(font);
//		mPanelPaymentSummary.add(labelVoucherTitle);
//		
//		top=labelVoucherTitle.getY()+labelVoucherTitle.getHeight()+VERTICAL_GAP;
//		JLabel labelDiscountTitle=new JLabel("Discount :");
//		labelDiscountTitle.setBounds(left, top, width, height);
//		labelDiscountTitle.setBorder(new LineBorder(Color.LIGHT_GRAY));
//		labelDiscountTitle.setFont(font);
//		mPanelPaymentSummary.add(labelDiscountTitle);
//	
//		top=labelDiscountTitle.getY()+labelDiscountTitle.getHeight()+VERTICAL_GAP;
//		JLabel labelBalanceTitle=new JLabel("Balance :");
//		labelBalanceTitle.setBounds(left, top, width, height);
//		labelBalanceTitle.setBorder(new LineBorder(Color.LIGHT_GRAY));
//		labelBalanceTitle.setFont(font);
//		mPanelPaymentSummary.add(labelBalanceTitle);
//		
//		left=labelDiscountTitle.getX()+labelDiscountTitle.getWidth()+HORIZONTAL_GAP;
//		top=25;
//		width=215;
//		height=30;
//		mLabelVoucherValue=new JLabel("00.00");
//		mLabelVoucherValue.setBounds(left, top, width, height);
//		mLabelVoucherValue.setBorder(new LineBorder(Color.LIGHT_GRAY));
//		mLabelVoucherValue.setFont(font);
//		mLabelVoucherValue.setHorizontalAlignment(SwingConstants.RIGHT);
//		mPanelPaymentSummary.add(mLabelVoucherValue);
//		
//		top=mLabelVoucherValue.getY()+mLabelVoucherValue.getHeight()+VERTICAL_GAP;
//		mLabelDiscountValue=new JLabel("00.00");
//		mLabelDiscountValue.setBounds(left, top, width, height);
//		mLabelDiscountValue.setBorder(new LineBorder(Color.LIGHT_GRAY));
//		mLabelDiscountValue.setFont(font);
//		mLabelDiscountValue.setHorizontalAlignment(SwingConstants.RIGHT);
//		mPanelPaymentSummary.add(mLabelDiscountValue);
//		
//		top=mLabelDiscountValue.getY()+mLabelDiscountValue.getHeight()+VERTICAL_GAP;
//		mLabelBalanceValue=new JLabel("00.00");
//		mLabelBalanceValue.setBounds(left, top, width, height);
//		mLabelBalanceValue.setBorder(new LineBorder(Color.LIGHT_GRAY));
//		mLabelBalanceValue.setFont(font);
//		mLabelBalanceValue.setHorizontalAlignment(SwingConstants.RIGHT);
//		mPanelPaymentSummary.add(mLabelBalanceValue);
//	}
//	
//	private JPanel mPanelRefundSummary;
//	private JLabel mRefundAmount;
//	private void createRefundSummaryPanel(){
//		final int left=mOrderListPanel.getX()+mOrderListPanel.getWidth()+PANEL_CONTENT_H_GAP;
//		final int top=mPanelPaymentSummary.getY()+mPanelPaymentSummary.getHeight()+PANEL_CONTENT_V_GAP;
//		final int width=PANEL_WIDTH-mOrderListPanel.getWidth()-PANEL_CONTENT_H_GAP*3;
//		final int height=138;
//		mPanelRefundSummary=createBoxedPanel("Refund Summary", width, height);
//		mPanelRefundSummary.setLocation(left,top);
//		mContianerPanel.add(mPanelRefundSummary);
//		
//		Font font=mItemTitleLableFont.deriveFont(Font.BOLD).deriveFont(50f);
//		
//		mRefundAmount =new JLabel("00.00");
//		mRefundAmount.setBounds(0, 20, mPanelRefundSummary.getWidth(), mPanelRefundSummary.getHeight()-20);
//		mRefundAmount.setForeground(Color.RED);
//		mRefundAmount.setFont(font);
//		mRefundAmount.setHorizontalAlignment(SwingConstants.CENTER);
//		mPanelRefundSummary.add(mRefundAmount);
//	}
//	
//	private JPanel mPanelRemarksSummary;
//	private JLabel mRemarks;
//	private void createRemarksSummaryPanel(){
//		final int left=mOrderListPanel.getX()+mOrderListPanel.getWidth()+PANEL_CONTENT_H_GAP;
//		final int top=mPanelRefundSummary.getY()+mPanelRefundSummary.getHeight()+PANEL_CONTENT_V_GAP;
//		final int width=PANEL_WIDTH-mOrderListPanel.getWidth()-PANEL_CONTENT_H_GAP*3;
//		final int height=150;
//		mPanelRemarksSummary=createBoxedPanel("Bill Remarks", width, height);
//		mPanelRemarksSummary.setLocation(left,top);
//		mContianerPanel.add(mPanelRemarksSummary);
//		
//		mRemarks =new JLabel("00.00");
//		mRemarks.setBounds(0, 20, mPanelRefundSummary.getWidth(), mPanelRefundSummary.getHeight()-20);
//		mRemarks.setFont(mItemTitleLableFont);
//		
//		mRemarks.setHorizontalAlignment(SwingConstants.LEFT);
//		mRemarks.setVerticalAlignment(JLabel.BOTTOM);
//		mPanelRemarksSummary.add(mRemarks);
//	}
//	
//	private JPanel createBoxedPanel(String title, int width, int height){
//		final int MESSAGE_PANEL_HEIGHT = 20;
//		JPanel boxPanel=new JPanel();
//		Dimension size=new Dimension(width, height);
//		boxPanel.setBackground(Color.white);
//		boxPanel.setPreferredSize(size);
//		boxPanel.setSize(size);
//		boxPanel.setBorder(new LineBorder(Color.LIGHT_GRAY));
//		boxPanel.setLayout(null);
//		
//		JLabel labelTitle = new JLabel();
//		labelTitle.setText(title);
//		labelTitle.setHorizontalAlignment(SwingConstants.CENTER);
//		labelTitle.setPreferredSize(new Dimension(width, MESSAGE_PANEL_HEIGHT));
//		labelTitle.setSize(new Dimension(width, MESSAGE_PANEL_HEIGHT));
//		labelTitle.setOpaque(true);
//		labelTitle.setBackground(LABEL_BG_COLOR);
//		labelTitle.setForeground(Color.WHITE);
//		boxPanel.add(labelTitle);
//		return boxPanel;
//	}
//	
//	private void resetFields(){
//		mLabelBillNoValue.setText("");
//		mLabelBillDateValue.setText("");
//		mLabelBillTimeValue.setText("");
//		mLabelCustomerValue.setText("");
//		mLabelCashierValue.setText("");
//		mLabelBillTotalValue.setText("00.00");
//		mLabelCashValue.setText("00.00");
//		mLabelCardValue.setText("00.00");
//		mLabelCompanyValue.setText("00.00");
//		mLabelVoucherValue.setText("00.00");
//		mLabelDiscountValue.setText("00.00");
//		mLabelBalanceValue.setText("00.00");
//		mRefundAmount.setText("00.00");
//		mRemarks.setText("");
//	}
//	
//	private void populateBillSummaryPanel(PosOrderHeaderObject order){
//		mLabelBillNoValue.setText(order.getOrderId());
//		mLabelBillDateValue.setText(order.getOrderDate());
//		mLabelBillTimeValue.setText(order.getOrderTime());
//		mLabelCustomerValue.setText(order.getCustomer().getName());
//		mLabelCashierValue.setText("");
//		mLabelBillTotalValue.setText(PosNumberUtil.formatNumber(order.getTotalAmount()));
//	}
//	
//	private void populatePaymentSummaryPanel(PosOrderHeaderObject order){
//		PosOrderPaymentsProvider paymentProvider=new PosOrderPaymentsProvider();
//		try {
//			PosPaymentSummaryObject pymentSummary=paymentProvider.getPaymentSummary(order.getOrderId());
//			mLabelCashValue.setText(PosNumberUtil.formatNumber(pymentSummary.getCashTotal()));
//			mLabelCardValue.setText(PosNumberUtil.formatNumber(pymentSummary.getCardTotal()));
//			mLabelCompanyValue.setText(PosNumberUtil.formatNumber(pymentSummary.getCompanyTotal()));
//			mLabelVoucherValue.setText(PosNumberUtil.formatNumber(pymentSummary.getVoucherTotal()));
//			mLabelDiscountValue.setText(PosNumberUtil.formatNumber(pymentSummary.getBillDiscount()));
//			mLabelBalanceValue.setText(PosNumberUtil.formatNumber(order.getBalanceAmount()));
//		} catch (Exception e) {
//			PosLog.write(this, "populatePaymentSummaryPanel", e);
//			PosFormUtil.showErrorMessageBox(this, "Failed get payment information.");
//		} 
//		
//	}
//	
//	private void populateBillRemarksPanel(PosOrderHeaderObject order){
//		mRemarks.setText("<html><body><br>"+order.getRemarks()+"</br></body></hml>");
//	}
//	
//	private void populateRefundSummaryPanel(PosOrderHeaderObject order){
//		final double billTotal=order.getTotalAmount();
//		final double newBillTotal=mOrderListPanel.getBillTotal();
//		mRefundAmount.setText(PosNumberUtil.formatNumber(billTotal-newBillTotal));
//	}
//	
//	/* (non-Javadoc)
//	 * @see com.indocosmo.pos.forms.PosBaseForm#onOkButtonClicked()
//	 */
//	@Override
//	public boolean onOkButtonClicked() {
//		PosPaymentForm paymentForm=new PosPaymentForm(this, PaymentMode.Cash,mOrderListPanel);
//		paymentForm.setRepaymentMode(true);
//		paymentForm.setOrderHeader(mSelectedOrderDetails);
//		PosFormUtil.showLightBoxModal(this, paymentForm);
//		return false;// super.onOkButtonClicked();
//	}

}
