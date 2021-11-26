package com.indocosmo.pos.forms.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;

import com.indocosmo.pos.common.PosEnvSettings;
import com.indocosmo.pos.common.PosLog;
import com.indocosmo.pos.common.enums.PosOrderServiceTypes;
import com.indocosmo.pos.common.enums.PosTerminalServiceType;
import com.indocosmo.pos.common.utilities.PosCurrencyUtil;
import com.indocosmo.pos.common.utilities.PosDateUtil;
import com.indocosmo.pos.common.utilities.PosFormUtil;
import com.indocosmo.pos.common.utilities.PosNumberUtil;
import com.indocosmo.pos.common.utilities.PosOrderUtil;
import com.indocosmo.pos.data.beans.BeanCashierShift;
import com.indocosmo.pos.data.beans.BeanOrderHeader;
import com.indocosmo.pos.data.beans.BeanOrderHeader.PosOrderStatus;
import com.indocosmo.pos.data.providers.shopdb.PosDiscountItemProvider;
import com.indocosmo.pos.data.providers.shopdb.PosOrderHdrProvider;
import com.indocosmo.pos.forms.PosOrderBillAmountInfoForm;
import com.indocosmo.pos.forms.PosOrderEntryForm;
import com.indocosmo.pos.forms.PosOrderRemarksView;
import com.indocosmo.pos.forms.PosOrderRetrieveForm;
import com.indocosmo.pos.forms.components.buttons.PosButton;
import com.indocosmo.pos.forms.components.buttons.PosButton.ButtonStyle;
import com.indocosmo.pos.forms.components.buttons.listners.IPosButtonListner;
import com.indocosmo.pos.forms.components.keypads.PosSoftKeyPad;
import com.indocosmo.pos.forms.components.listners.IPosOrderRetriveListner;
import com.indocosmo.pos.forms.components.search.IPosSearchableItem;
import com.indocosmo.pos.forms.components.tab.PosTab;
import com.indocosmo.pos.forms.search.PosExtSearchForm;
import com.indocosmo.pos.forms.search.PosOrderSerachForm;
import com.indocosmo.pos.forms.search.listener.adapter.PosItemExtSearchFormAdapter;

@SuppressWarnings("serial")
public class PosOrderItemRetrievePanel extends PosTab {

	private static final int PANEL_CONTENT_H_GAP=1;
	private static final int PANEL_CONTENT_V_GAP=2;
	public static final int PANEL_WIDTH=PosOrderEntryForm.LEFT_PANEL_WIDTH;
	
	private static final int RESET_BUTTON_DEF_HEIGHT=40;
	private static final int RESET_BUTTON_DEF_WIDTH=50;
	
	private static final int RETRIVE_BUTTON_DEF_HEIGHT=RESET_BUTTON_DEF_HEIGHT-5;
	private static final int RETRIVE_BUTTON_DEF_WIDTH=160;

	private static final int TXT_ORDER_NO_WIDTH=PosSoftKeyPad.LAYOUT_WIDTH-RETRIVE_BUTTON_DEF_WIDTH-PANEL_CONTENT_H_GAP*2-RESET_BUTTON_DEF_WIDTH;
	private static final int TXT_ORDER_NO_HEIGHT=RESET_BUTTON_DEF_HEIGHT;

	private static final Color PANEL_BG_COLOR=new Color(78,128,188);

	private static final String IMAGE_VIEW_REMARKS_NORMAL="oreder_view_remarks.png";
	private static final String IMAGE_VIEW_REMARKS_TOUCH="oreder_view_remarks_touch.png";

	private static final String RESET_BUTTON_NORMAL="ctrl_reset.png";
	private static final String RESET_BUTTON_TOUCHED="ctrl_reset_touch.png";
	

	private final int labelWidth=145;
	private final int labelHeight=35;
	
	private PosButton mResetButton;

	private PosSoftKeyPad mSoftKeyPad;
	private JTextArea mTextArea;

	private JLabel mLabelOrderNumber;
	private JTextField mTextOrderNumber;

	private JLabel mLabelOrderDateTime;
	private JTextField mTextOrderDateTime;

	private JLabel mLabelTotalItems;
	private JTextField mTextTotalItems;

	private JLabel mLabelTotalAmount;
	private JTextField mTextTotalAmount;

	private JLabel mLabelDueAmount;
	private JTextField mTextDueAmount;

	private JLabel mLabelOrderService;
	private JTextField mTextService;

	private JLabel mLabelHeading;
	private JLabel mLabelOrderSelectHeading;
	
	private JLabel mLabelOrderUser;
	private JTextField mTextUser;

	private JLabel mLabelViewRemarks;
	private PosButton mButtomRemarks;

	private PosButton mButtomRetrive;

	private int mLeft, mTop;
	private int mHeight, mWidth;

	private PosOrderHdrProvider moderHdrProvider;
	private PosOrderRetrieveForm mParent;
	private BeanCashierShift shift;

	public PosOrderItemRetrievePanel(PosOrderRetrieveForm parent,int left,int top, int width, int height) {
		super(parent,"Order Items");
		mParent=parent;
		mLeft=left;
		mTop=top;
		mHeight=height;
		mWidth=width;
		initComponent();
		moderHdrProvider=new PosOrderHdrProvider();
	}

	private void initComponent() {
		setBounds(mLeft,mTop,mWidth, mHeight);
		setLayout(null);
		setHeading();
		setOrderNumber();
		setOrderDateTime();
		setOrderAmount();
		setOrderDueAmount();
		setOrderItemCount();
		setOrderServiceType();
		setOrderUser();
		setViewRemarksButton();
		setOrderSelectHeader();
		initKeypad();	
		setBorder(new EtchedBorder(EtchedBorder.LOWERED));
	}

	private void setHeading(){
		mLabelHeading=new JLabel();
		mLabelHeading.setText("Order Details");
		mLabelHeading.setHorizontalAlignment(SwingConstants.CENTER);
		mLabelHeading.setVerticalAlignment(SwingConstants.CENTER);
		mLabelHeading.setBounds(0, 0, getWidth(), 25);
		mLabelHeading.setFont(PosFormUtil.getSubHeadingFont());
		mLabelHeading.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
		mLabelHeading.setBackground(PANEL_BG_COLOR);
		mLabelHeading.setForeground(Color.WHITE);
		mLabelHeading.setOpaque(true);
		add(mLabelHeading);
	}

	private void setOrderNumber(){
		int top=mLabelHeading.getY()+mLabelHeading.getHeight()+PANEL_CONTENT_V_GAP;
		int left=PANEL_CONTENT_H_GAP*4;

		mLabelOrderNumber=new JLabel("Number :");
		mLabelOrderNumber.setBorder(new EmptyBorder(2, 2, 2, 2));
		mLabelOrderNumber.setOpaque(true);
		mLabelOrderNumber.setBackground(Color.LIGHT_GRAY);
		mLabelOrderNumber.setSize(labelWidth+13, labelHeight);
		mLabelOrderNumber.setLocation(left, top);
		mLabelOrderNumber.setFont(PosFormUtil.getLabelFont());
		add(mLabelOrderNumber);

		left+=mLabelOrderNumber.getWidth()+PANEL_CONTENT_H_GAP;
		mTextOrderNumber=new JTextField("");
		mTextOrderNumber.setSize(160, labelHeight);
		mTextOrderNumber.setLocation(left, top);
		mTextOrderNumber.setFont(PosFormUtil.getLabelFont());
		mTextOrderNumber.setEditable(false);
		add(mTextOrderNumber);
	}

	private void setOrderDateTime(){
		int top=mTextOrderNumber.getY();
		int left=mTextOrderNumber.getX()+mTextOrderNumber.getWidth()+PANEL_CONTENT_H_GAP*2;

		mLabelOrderDateTime=new JLabel("Date & Time :");
		mLabelOrderDateTime.setBorder(new EmptyBorder(2, 2, 2, 2));
		mLabelOrderDateTime.setOpaque(true);
		mLabelOrderDateTime.setBackground(Color.LIGHT_GRAY);
		mLabelOrderDateTime.setSize(labelWidth, labelHeight);
		mLabelOrderDateTime.setLocation(left, top);
		mLabelOrderDateTime.setFont(PosFormUtil.getLabelFont());
		add(mLabelOrderDateTime);

		left+=mLabelOrderDateTime.getWidth()+PANEL_CONTENT_H_GAP;
		mTextOrderDateTime=new JTextField("");
		mTextOrderDateTime.setSize(200, labelHeight);
		mTextOrderDateTime.setLocation(left, top);
		mTextOrderDateTime.setFont(PosFormUtil.getLabelFont());
		mTextOrderDateTime.setEditable(false);
		add(mTextOrderDateTime);
	}

	private void setOrderAmount(){
		int top=mTextOrderDateTime.getY()+mTextOrderDateTime.getHeight()+PANEL_CONTENT_V_GAP;
		int left=PANEL_CONTENT_H_GAP*4;

		mLabelTotalAmount=new JLabel("Total Amount :");
		mLabelTotalAmount.setBorder(new EmptyBorder(2, 2, 2, 2));
		mLabelTotalAmount.setOpaque(true);
		mLabelTotalAmount.setBackground(Color.LIGHT_GRAY);
		mLabelTotalAmount.setSize(labelWidth+13, labelHeight);
		mLabelTotalAmount.setLocation(left, top);
		mLabelTotalAmount.setFont(PosFormUtil.getLabelFont());
		add(mLabelTotalAmount);

		left+=mLabelTotalAmount.getWidth()+PANEL_CONTENT_H_GAP;
		mTextTotalAmount=new JTextField("");
		mTextTotalAmount.setSize(160, labelHeight);
		mTextTotalAmount.setLocation(left, top);
		mTextTotalAmount.setFont(PosFormUtil.getLabelFont());
		mTextTotalAmount.setEditable(false);
		mTextTotalAmount.setHorizontalAlignment(SwingConstants.RIGHT);
		add(mTextTotalAmount);
	}

	private void setOrderDueAmount(){
		int top=mTextTotalAmount.getY();//+mTextOrderNumber.getHeight()+PANEL_CONTENT_V_GAP;
		int left=mTextTotalAmount.getX()+mTextOrderNumber.getWidth()+PANEL_CONTENT_H_GAP*2;

		mLabelDueAmount=new JLabel("Due Amount :");
		mLabelDueAmount.setBorder(new EmptyBorder(2, 2, 2, 2));
		mLabelDueAmount.setBackground(Color.LIGHT_GRAY);
		mLabelDueAmount.setOpaque(true);
		mLabelDueAmount.setSize(labelWidth, labelHeight);
		mLabelDueAmount.setLocation(left, top);
		mLabelDueAmount.setFont(PosFormUtil.getLabelFont());
		add(mLabelDueAmount);

		left+=mLabelDueAmount.getWidth()+PANEL_CONTENT_H_GAP;
		mTextDueAmount=new JTextField("");
		mTextDueAmount.setSize(150, labelHeight);
		mTextDueAmount.setLocation(left, top);
		mTextDueAmount.setFont(PosFormUtil.getLabelFont());
		mTextDueAmount.setEditable(false);
		mTextDueAmount.setHorizontalAlignment(SwingConstants.RIGHT);
		add(mTextDueAmount);
		
		left+=mTextDueAmount.getWidth()+PANEL_CONTENT_H_GAP;
		final PosButton btnRetrieve=new PosButton("...");
		btnRetrieve.setMnemonic('e');
		btnRetrieve.setSize(new Dimension(50 ,labelHeight));
		btnRetrieve.setLocation(left, top);
		btnRetrieve.setButtonStyle(ButtonStyle.IMAGE);
		btnRetrieve.setImage("cashout_search.png", "cashout_search_touch.png");
		btnRetrieve.setOnClickListner(new IPosButtonListner() {
			
			@Override
			public void onClicked(PosButton button) {
			
				try {
					
					if (mParent.getOrder()==null) return;
					PosOrderBillAmountInfoForm form=new PosOrderBillAmountInfoForm(mParent.getOrder());
					PosFormUtil.showLightBoxModal(mParent , form);
					 
				} catch (Exception e) {
					
					PosLog.write(PosOrderItemRetrievePanel.this, "btnRetrieve.onClicked", e);
					PosFormUtil.showErrorMessageBox(PosOrderItemRetrievePanel.this, "Failed to load details. Please check log.");
				}
				
			}
		});
		add(btnRetrieve);
	}

	private void setOrderItemCount(){
		int top=mTextDueAmount.getY()+mTextDueAmount.getHeight()+PANEL_CONTENT_V_GAP;
		int left=PANEL_CONTENT_H_GAP*4;

//		mLabelTotalItems=new JLabel("Total Quantity :");
		mLabelTotalItems=new JLabel("Order Status :");
		mLabelTotalItems.setBorder(new EmptyBorder(2, 2, 2, 2));
		mLabelTotalItems.setOpaque(true);
		mLabelTotalItems.setBackground(Color.LIGHT_GRAY);
		mLabelTotalItems.setSize(labelWidth+13, labelHeight);
		mLabelTotalItems.setLocation(left, top);
		mLabelTotalItems.setFont(PosFormUtil.getLabelFont());
		add(mLabelTotalItems);

		left+=mLabelTotalItems.getWidth()+PANEL_CONTENT_H_GAP;
		mTextTotalItems=new JTextField("");
		mTextTotalItems.setSize(160, labelHeight);
		mTextTotalItems.setLocation(left, top);
		mTextTotalItems.setFont(PosFormUtil.getButtonBoldFont());
		mTextTotalItems.setEditable(false);
		mTextTotalItems.setForeground(new Color(255, 0, 0));
//		mTextTotalItems.setHorizontalAlignment(SwingConstants.RIGHT);
		add(mTextTotalItems);
	}

	private void setOrderServiceType(){
		int top=mTextTotalItems.getY();//+mTextOrderNumber.getHeight()+PANEL_CONTENT_V_GAP;
		int left=mTextTotalItems.getX()+mTextTotalItems.getWidth()+PANEL_CONTENT_H_GAP*2;

		mLabelOrderService=new JLabel("Table/Service :");
		mLabelOrderService.setBorder(new EmptyBorder(2, 2, 2, 2));
		mLabelOrderService.setOpaque(true);
		mLabelOrderService.setBackground(Color.LIGHT_GRAY);
		mLabelOrderService.setSize(labelWidth, labelHeight);
		mLabelOrderService.setLocation(left, top);
		mLabelOrderService.setFont(PosFormUtil.getLabelFont());
		add(mLabelOrderService);

		left+=mLabelOrderService.getWidth()+PANEL_CONTENT_H_GAP;
		mTextService=new JTextField("");
		mTextService.setSize(200, labelHeight);
		mTextService.setLocation(left, top);
		mTextService.setFont(PosFormUtil.getLabelFont());
		mTextService.setEditable(false);
		add(mTextService);
	}
	
	private void setOrderUser(){
		int top=mLabelOrderService.getY()+mLabelOrderService.getHeight()+PANEL_CONTENT_V_GAP;
		int left=mLabelOrderService.getX();

		mLabelOrderUser=new JLabel("Served By :");
		mLabelOrderUser.setBorder(new EmptyBorder(2, 2, 2, 2));
		mLabelOrderUser.setOpaque(true);
		mLabelOrderUser.setBackground(Color.LIGHT_GRAY);
		mLabelOrderUser.setSize(labelWidth, labelHeight);
		mLabelOrderUser.setLocation(left, top);
		mLabelOrderUser.setFont(PosFormUtil.getLabelFont());
		add(mLabelOrderUser);

		left+=mLabelOrderUser.getWidth()+PANEL_CONTENT_H_GAP;
		mTextUser=new JTextField("");
		mTextUser.setSize(200, labelHeight);
		mTextUser.setLocation(left, top);
		mTextUser.setFont(PosFormUtil.getLabelFont());
		mTextUser.setEditable(false);
		add(mTextUser);
	}

	private void setViewRemarksButton(){
		int top=mTextService.getY()+mTextService.getHeight()+PANEL_CONTENT_V_GAP;
		int left=PANEL_CONTENT_H_GAP*4;

		mLabelViewRemarks=new JLabel("Remarks :");
		mLabelViewRemarks.setBorder(new EmptyBorder(2, 2, 2, 2));
		mLabelViewRemarks.setOpaque(true);
		mLabelViewRemarks.setBackground(Color.LIGHT_GRAY);
		mLabelViewRemarks.setSize(labelWidth+13, labelHeight);
		mLabelViewRemarks.setLocation(left, top);
		mLabelViewRemarks.setFont(PosFormUtil.getLabelFont());
		add(mLabelViewRemarks);

		left+=mLabelViewRemarks.getWidth()+PANEL_CONTENT_H_GAP;
		mButtomRemarks=new PosButton("View");
		mButtomRemarks.setMnemonic('w');
		mButtomRemarks.setSize(RETRIVE_BUTTON_DEF_WIDTH, RETRIVE_BUTTON_DEF_HEIGHT);
		mButtomRemarks.setImage(IMAGE_VIEW_REMARKS_NORMAL);
		mButtomRemarks.setTouchedImage(IMAGE_VIEW_REMARKS_TOUCH);
		mButtomRemarks.setOnClickListner(new IPosButtonListner() {

			@Override
			public void onClicked(PosButton button) {
				if(mOrderHeaderItem==null) return;
				PosOrderRemarksView view=new PosOrderRemarksView();
				view.setOrderHeaderItem(mOrderHeaderItem);
				PosFormUtil.showLightBoxModal(mParent,view);
			}
		});
		mButtomRemarks.setLocation(left, top);
		add(mButtomRemarks);
	}

	private void setOrderSelectHeader(){
		int top=mButtomRemarks.getY()+mButtomRemarks.getHeight()+PANEL_CONTENT_V_GAP;
		int left=0;

		mLabelOrderSelectHeading=new JLabel();
		mLabelOrderSelectHeading.setText("Select Order");
		mLabelOrderSelectHeading.setHorizontalAlignment(SwingConstants.CENTER);
		mLabelOrderSelectHeading.setVerticalAlignment(SwingConstants.CENTER);
		mLabelOrderSelectHeading.setBounds(0, 0, getWidth(), 25);
		mLabelOrderSelectHeading.setFont(PosFormUtil.getSubHeadingFont());
		mLabelOrderSelectHeading.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
		mLabelOrderSelectHeading.setBackground(PANEL_BG_COLOR);
		mLabelOrderSelectHeading.setLocation(left, top);
		mLabelOrderSelectHeading.setOpaque(true);
		mLabelOrderSelectHeading.setForeground(Color.WHITE);
		add(mLabelOrderSelectHeading);
	}

	private void initKeypad(){
		int top=mLabelOrderSelectHeading.getY()+mLabelOrderSelectHeading.getHeight()+PANEL_CONTENT_V_GAP;
		int left=PANEL_CONTENT_H_GAP*4;

		mTextArea=new JTextArea(1,30);
		mTextArea.getActionMap().put(mTextArea.getInputMap().get(KeyStroke.getKeyStroke("ENTER")),new AbstractAction() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				mRetrieveButtonListner.onClicked(mButtomRetrive);
				
			}
		});
		mTextArea.setLineWrap(false);
		mTextArea.setFont(PosFormUtil.getTextFieldBoldFont());
		mTextArea.setSize(TXT_ORDER_NO_WIDTH, TXT_ORDER_NO_HEIGHT);
		mTextArea.setLocation(left, top);
		mTextArea.setBorder(new EtchedBorder());
		add(mTextArea);

		
		
		left=mTextArea.getX()+mTextArea.getWidth()+PANEL_CONTENT_H_GAP;
		mButtomRetrive=new PosButton("Retrieve");
		mButtomRetrive.setMnemonic('R');
		mButtomRetrive.setSize(RETRIVE_BUTTON_DEF_WIDTH, RESET_BUTTON_DEF_HEIGHT);
		mButtomRetrive.setImage(IMAGE_VIEW_REMARKS_NORMAL);
		mButtomRetrive.setTouchedImage(IMAGE_VIEW_REMARKS_TOUCH);
		mButtomRetrive.setLocation(left, top);
		mButtomRetrive.setOnClickListner(mRetrieveButtonListner);
		add(mButtomRetrive);
		
		
		
		left=mButtomRetrive.getX()+mButtomRetrive.getWidth()+PANEL_CONTENT_H_GAP;
		mResetButton=new PosButton();
		mResetButton.setSize(RESET_BUTTON_DEF_WIDTH, RESET_BUTTON_DEF_HEIGHT);
//		mResetButton.setPreferredSize(new Dimension(RESET_BUTTON_DEF_WIDTH, RESET_BUTTON_DEF_HEIGHT));
		mResetButton.setOnClickListner(new IPosButtonListner() {
			@Override
			public void onClicked(PosButton button) {
				onResetButtonClicked();
			}
		});
		mResetButton.setLocation(left, top);
		mResetButton.setImage(RESET_BUTTON_NORMAL);
		mResetButton.setTouchedImage(RESET_BUTTON_TOUCHED);	
		add(mResetButton);

		left=mTextArea.getX();
		top=mTextArea.getY()+mTextArea.getHeight()+PANEL_CONTENT_V_GAP;
		mSoftKeyPad=new PosSoftKeyPad(mTextArea,false);
		mSoftKeyPad.setActionButtonsDisabled(true);
		mSoftKeyPad.setLocation(left, top);
		add(mSoftKeyPad);
	}
	
	private void onResetButtonClicked(){
		 reset();
	}
	
	public void reset(){
		mTextArea.setText("");
		mTextDueAmount.setText("");
		mTextOrderDateTime.setText("");
		mTextOrderNumber.setText("");
		mTextTotalAmount.setText("");
		mTextTotalItems.setText("");
		mTextService.setText("");
		mTextUser.setText("");
		mOrderHeaderItem=null;
		if(mOrderRetrieveListner!=null )
			mOrderRetrieveListner.onReset();
	}
	
	private BeanOrderHeader mOrderHeaderItem;
	private IPosButtonListner mRetrieveButtonListner=new IPosButtonListner() {	
		@Override
		public void onClicked(PosButton button) {

			if(mTextArea.getText().length()>0)
				doSearch();
			else
				showOrderListSearch();
		}
	};
	
    private boolean mOrderSerachFormCanceld=false;
	/**
	 * @return the mOrderSerachFormCanceld
	 */
	public boolean isOrderSerachFormCanceld() {
		return mOrderSerachFormCanceld;
	}
	
	/**
	 * 
	 */
	public void showOrderListSearch(){
		
		ArrayList<BeanOrderHeader> orderHeaders;
		shift=PosEnvSettings.getInstance().getCashierShiftInfo();
//		String userinfo =PosEnvSettings.getInstance().getStation().getServiceType().equals(PosTerminalServiceType.Counter)?" and user_id="+shift.getCashierInfo().getId():"";
		String userinfo ="";
		try {


//			orderHeaders = moderHdrProvider.getOrderHeaders("status in("+(PosOrderStatus.Open).getCode()+","+(PosOrderStatus.Partial).getCode()+")" + ((userinfo.trim().length()>0)? userinfo:""));
			orderHeaders = moderHdrProvider.getOrderHeaderViewList("status in("+(PosOrderStatus.Open).getCode()+","+(PosOrderStatus.Partial).getCode()+")" + ((userinfo.trim().length()>0)? userinfo:""));
	
			showOrderListSearch(orderHeaders);
			
		} catch (Exception e) {
			
			PosLog.write(this, "showOrderListSearch", e);
			PosFormUtil.showErrorMessageBox(mParent,"Failed get order list. Please check the log for details");
		}
			
	}

	public void showOrderListSearch(ArrayList<BeanOrderHeader> orderHeaders){
		
		try {
			
			final PosExtSearchForm serachForm=new PosOrderSerachForm(orderHeaders);//PosItemSerachForm(true,"Order ID",true,"Order Status",orderHeaders);
			serachForm.setWindowTitle("Select order");
			serachForm.setSorting(1);
			serachForm.setListner(new PosItemExtSearchFormAdapter() {

				@Override
				public boolean onAccepted(Object sender, IPosSearchableItem item) {
					
					try {

						final BeanOrderHeader oh=(BeanOrderHeader)item;
//						final BeanOrderHeader oh=moderHdrProvider.getOrderHeader(((BeanOrderHeader)item).getOrderId());
						final boolean isValid=validateOrderForEditing(oh, sender);
						
						mOrderSerachFormCanceld=true;
						
						if(isValid){

							if(PosOrderUtil.getLock(serachForm,oh)){
								
								mTextArea.setText(PosOrderUtil.getShortOrderIDFromOrderID(oh.getOrderId()));
								mOrderHeaderItem= moderHdrProvider.getOrderData(oh.getOrderId());
								loadOrderDetails();
								mOrderSerachFormCanceld=false;
							}
						}
						
					} catch (Exception e) {
						
						PosLog.write(this, "showOrderListSearch", e);
						PosFormUtil.showErrorMessageBox(mParent,"Failed get order. Please check the log for details");
					}
					
					return !mOrderSerachFormCanceld;
				}
				
				/* (non-Javadoc)
				 * @see com.indocosmo.pos.forms.search.listener.adapter.PosItemExtSearchFormAdapter#onCancel(java.lang.Object)
				 */
				@Override
				public boolean onCancel(Object sender) {
					
					mOrderSerachFormCanceld=true;
					return super.onCancel(sender);
				}
			});
			
			PosFormUtil.showLightBoxModal(mParent,serachForm);
			
		} catch (Exception e) {
			
			PosLog.write(this, "showOrderListSearch", e);
			PosFormUtil.showErrorMessageBox(mParent,"Failed get order list. Please check the log for details");
		}

	}
	
	/**
	 * @param oh
	 * @param caller
	 * @return
	 */
	public boolean validateOrderForEditing(BeanOrderHeader oh, Object caller){
		
		boolean isValid=false;
		String msg="";
		
		if(oh==null || !(oh.getStatus()==PosOrderStatus.Open || oh.getStatus()==PosOrderStatus.Partial))
			msg = "The order is not available now. It is either closed or cancelled. Please check order number.";
		

		else
			isValid=true;

		if(!isValid)
			PosFormUtil.showErrorMessageBox(caller,msg);
		
		return isValid;
	}
	
	public void setOrderHeaderInfo(BeanOrderHeader orderHeader){
		
		mOrderHeaderItem=orderHeader;
		loadOrderDetails();
		
	}
	
	
	/**
	 * @return
	 */
	public double getTotalAmount(){
		
		return totalItemAmount;
	}
	
	/**
	 * @return
	 */
	public double getDueAmount(){
		
		return dueAmount;
	}
	
	private double dueAmount;
	private double totalItemAmount;
	
	
	private void doSearch(){
		
		try {
			
//			if(doSearch){

//				ArrayList<BeanOrderHeader> orderHeaders=moderHdrProvider.getOrderHeaders("order_id like '%"+mTextArea.getText()+"%'");
			String criteria="(order_id like '%"+mTextArea.getText()+"%' "; 
			if (PosNumberUtil.isInteger(mTextArea.getText()) )
				criteria += " or queue_no=" + Integer.parseInt(mTextArea.getText() );
			criteria += " ) " + 
						" and status in("+PosOrderStatus.Open.getCode() +"," + PosOrderStatus.Partial.getCode()+")";
			ArrayList<BeanOrderHeader> orderHeaders=moderHdrProvider.getOrderHeaderViewList(criteria);
			
				if(orderHeaders.size()==1){
					

					//final BeanOrderHeader oh= moderHdrProvider.getOrderData(PosOrderUtil.getQualifiedOrderID(mTextArea.getText()));
					final BeanOrderHeader oh= moderHdrProvider.getOrderData( orderHeaders.get(0).getOrderId());
					final boolean isValid=validateOrderForEditing(oh, mParent);
					
					if(isValid){
						
						if(PosOrderUtil.getLock(mParent,oh)){

							mOrderHeaderItem=oh;
							loadOrderDetails();
						}
					}

				}else if(orderHeaders.size()>1)

					showOrderListSearch(orderHeaders);
				else
					PosFormUtil.showErrorMessageBox(mParent,"The order does not exist. Please enter a valid order number.");
						
//			}else
//				loadOrderDetails();
				
		} catch (Exception e) {
			PosLog.write(this, "doSearch", e);
			PosFormUtil.showErrorMessageBox(mParent,"Failed perform search. Please check the log for details");
		}
	}
	
	public void refresh(){
		
		loadOrderDetails();
	}
		
	/**
	 * Loads and display the details.
	 */
	private void loadOrderDetails(){

		try {

			mTextOrderNumber.setText(PosOrderUtil.getFormattedReferenceNo(mOrderHeaderItem));
			mTextOrderDateTime.setText(PosDateUtil.format(mOrderHeaderItem.getOrderTime()));
			mTextTotalItems.setText(mOrderHeaderItem.getStatus().getDisplayText());
			totalItemAmount=PosOrderUtil.getTotalItemAmount(mOrderHeaderItem);
			double amount=PosOrderUtil.getTotalAmount(mOrderHeaderItem);
			 if (mOrderHeaderItem.getPreBillDiscount()!=null ){
				 amount=amount-(mOrderHeaderItem.getPreBillDiscount().isPercentage()?amount*mOrderHeaderItem.getPreBillDiscount().getPrice()/100:mOrderHeaderItem.getPreBillDiscount().getPrice());
			 }
			 mTextTotalAmount.setText(PosCurrencyUtil.format(PosCurrencyUtil.roundTo(amount)));
 
			dueAmount=PosOrderUtil.getTotalBalanceOnBill(mOrderHeaderItem);
			mTextDueAmount.setText(PosCurrencyUtil.format(dueAmount));
			String serviceLabelCaption=(mOrderHeaderItem.getOrderServiceType()==PosOrderServiceTypes.TABLE_SERVICE)?"Table :":"Service :";
			mLabelOrderService.setText(serviceLabelCaption);
			mTextService.setText(mOrderHeaderItem.getServingTableName());
			String orderBy="";
			if(mOrderHeaderItem.getServedBy()!=null)
				orderBy=mOrderHeaderItem.getServedBy().getFirstName();
			else
				orderBy=mOrderHeaderItem.getUser().getName();
			if(orderBy!=null && orderBy.length()>0){
				mTextUser.setText(orderBy);
				mTextUser.setCaretPosition(1);
			}
			if(mOrderRetrieveListner!=null )
				mOrderRetrieveListner.onOrderRetrieved(mOrderHeaderItem);
			
		} catch (Exception e) {
			
			PosLog.write(this, "doSearch", e);
			PosFormUtil.showErrorMessageBox(mParent,"Failed perform search. Please check the log for details");
		}
	}
		

	private IPosOrderRetriveListner mOrderRetrieveListner;
	public void setListner(IPosOrderRetriveListner listner){
		mOrderRetrieveListner=listner;
	}
	
	

}
