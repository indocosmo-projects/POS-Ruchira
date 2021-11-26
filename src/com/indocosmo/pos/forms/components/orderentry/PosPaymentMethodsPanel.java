/**
 * 
 */
package com.indocosmo.pos.forms.components.orderentry;


import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.RootPaneContainer;

import com.indocosmo.pos.common.PosEnvSettings;
import com.indocosmo.pos.common.PosLog;
import com.indocosmo.pos.common.enums.PosOrderServiceTypes;
import com.indocosmo.pos.common.enums.PosPaymentOption;
import com.indocosmo.pos.common.enums.split.SplitBasedOn;
import com.indocosmo.pos.common.utilities.PosFormUtil;
import com.indocosmo.pos.common.utilities.PosOrderUtil;
import com.indocosmo.pos.common.utilities.PosResUtil;
import com.indocosmo.pos.common.utilities.PosStringUtil;
import com.indocosmo.pos.data.beans.BeanOrderHeader;
import com.indocosmo.pos.data.beans.BeanOrderHeader.PosOrderStatus;
import com.indocosmo.pos.data.beans.BeanOrderSplit;
import com.indocosmo.pos.data.beans.BeanPayModeBase.PaymentMode;
import com.indocosmo.pos.data.providers.shopdb.PosOrderHdrProvider;
import com.indocosmo.pos.data.providers.shopdb.PosPaymentModesProvider;
import com.indocosmo.pos.forms.PosObjectBrowserForm;
import com.indocosmo.pos.forms.PosObjectBrowserForm.ItemSize;
import com.indocosmo.pos.forms.PosOrderEntryForm;
import com.indocosmo.pos.forms.PosOrderPreBillDiscountForm;
import com.indocosmo.pos.forms.PosPaymentForm;
import com.indocosmo.pos.forms.PosSalesOrderInfoForm;
import com.indocosmo.pos.forms.components.buttons.PosButton;
import com.indocosmo.pos.forms.components.buttons.listners.IPosButtonListner;
import com.indocosmo.pos.forms.components.objectbrowser.IPosBrowsableItem;
import com.indocosmo.pos.forms.components.orderentry.listners.IPosPaymentPanelListner;
import com.indocosmo.pos.forms.listners.IPosObjectBrowserListner;
import com.indocosmo.pos.forms.listners.IPosPaymentFormListner;
import com.indocosmo.pos.forms.messageboxes.listners.PosMessageBoxFormListnerAdapter;
import com.indocosmo.pos.forms.split.PosOrderSplitForm;


/**
 * @author jojesh
 * 
 * This class will handle the POS Payments options
 */
@SuppressWarnings("serial")
public final class PosPaymentMethodsPanel extends PosOrderEntryBottomMenuPanelBase{

	private static final String IMAGE_PAY_BY_CASH_PART_NORMAL="pay_by_cash_multi.png";
	private static final String IMAGE_PAY_BY_CASH_PART_TOUCH="pay_by_cash_multi_touch.png";
	private static final String IMAGE_PAY_BY_CARD_NORMAL="pay_by_card_normal.png";
	private static final String IMAGE_PAY_BY_CARD_TOUCH="pay_by_card_touch.png";
	private static final String IMAGE_SPLIT_PAY_NORMAL="split_pay_normal.png";
	private static final String IMAGE_SPLIT_PAY_TOUCH="split_pay_touch.png";
	private static final String IMAGE_PAY_BY_CASH_NORMAL="pay_by_cash_single.png";
	private static final String IMAGE_PAY_BY_CASH_TOUCH="pay_by_cash_single_touch.png";
	private static final String IMAGE_QUICK_PAY_NORMAL="qpay_normal.png";
	private static final String IMAGE_QUICK_PAY_TOUCH="qpay_touch.png";
	private static final String IMAGE_PAY_BY_COMPANY_NORMAL="pay_by_company_normal.png";
	private static final String IMAGE_PAY_BY_COMPANY_TOUCH="pay_by_company_touch.png";
	private static final String IMAGE_PAY_BY_COUPON_NORMAL="pay_by_coupon_normal.png";
	private static final String IMAGE_PAY_BY_COUPON_TOUCH="pay_by_coupon_touch.png";
	private static final String IMAGE_DISCOUNT_NORMAL="bill-discount-normal.png";
	private static final String IMAGE_DISCOUNT_TOUCH="bill-discount-touch.png";
	private static final String IMAGE_ONLINE_PAY_NORMAL="online_pay_normal.png";
	private static final String IMAGE_ONLINE_PAY_TOUCH="online_pay_touch.png";
	private PosPaymentForm mPaymentForm;

	private PaymentMode mSelectedPaymentMode=PaymentMode.Cash;

	private PosOrderEntryForm mOrderEntryForm;

	private PosPaymentModesProvider paymentMethodesProvider=null; 

	PosButton payCashButton;
	PosButton paySplitButton;
	/**
	 * @param posOrderEntryForm
	 */
	public PosPaymentMethodsPanel(RootPaneContainer parent) {
		super(parent);
		paymentMethodesProvider=PosPaymentModesProvider.getInstance(); 
		mOrderEntryForm=(PosOrderEntryForm)parent;
		createButtons();
	}

	private void initPaymentForm(){

		if(mPaymentForm !=null) return;

		mPaymentForm =new PosPaymentForm(mOrderEntryForm,mSelectedPaymentMode);
		mPaymentForm.setListner(mPaymentFormListner);

	}

	private void createButtons(){
		
		createQuickPayButton();
		createPayCashButton();
		createPayCardButton();
		createPayCouponButton();
		createPayCompanyButton();
		createOnlinePayButton();
		createSplitPayButton();
		createDiscountButton();
	}

	private void createPayCashButton(){
		
		payCashButton=new PosButton();
		payCashButton.registerKeyStroke(KeyEvent.VK_F9);
		payCashButton.setTag(PaymentMode.Cash);
		payCashButton.setBounds(0, 0, BUTTON_WIDTH, BUTTON_HEIGHT);
		payCashButton.setImage(PosResUtil.getImageIconFromResource(IMAGE_PAY_BY_CASH_NORMAL));
		payCashButton.setTouchedImage(PosResUtil.getImageIconFromResource(IMAGE_PAY_BY_CASH_TOUCH));
		payCashButton.setOnClickListner(pay_by_button_listner);
		if(PosEnvSettings.getInstance().getUISetting().getOrderEntryUISettings().getPaymentPanelSetting().isCashPayButtonVisibile() && 
				paymentMethodesProvider.getPaymentModes().isCanPayByCash())
			addButtonToPanel(payCashButton);
	}
	
	/**
	 * 
	 */
	private void createQuickPayButton(){
		
		PosButton payQuickPayButton;
		payQuickPayButton=new PosButton();
		payQuickPayButton.registerKeyStroke(KeyEvent.VK_F8);
		payQuickPayButton.setTag(PaymentMode.QuickCash);
		payQuickPayButton.setBounds(0, 0, BUTTON_WIDTH, BUTTON_HEIGHT);
		payQuickPayButton.setImage(PosResUtil.getImageIconFromResource(IMAGE_QUICK_PAY_NORMAL));
		payQuickPayButton.setTouchedImage(PosResUtil.getImageIconFromResource(IMAGE_QUICK_PAY_TOUCH));
		payQuickPayButton.setOnClickListner(quick_pay_by_button_listner);
		if(PosEnvSettings.getInstance().getUISetting().getQuickPayMode()!=null && PosEnvSettings.getInstance().getUISetting().getQuickPayMode().length>0)
			addButtonToPanel(payQuickPayButton);
	}
	/**
	 * 
	 */
	private void createOnlinePayButton(){
		
		PosButton payOnlinePayButton;
		payOnlinePayButton=new PosButton();
		payOnlinePayButton.registerKeyStroke(KeyEvent.VK_F8);
		payOnlinePayButton.setTag(PaymentMode.Online);
		payOnlinePayButton.setBounds(0, 0, BUTTON_WIDTH, BUTTON_HEIGHT);
		payOnlinePayButton.setImage(PosResUtil.getImageIconFromResource(IMAGE_ONLINE_PAY_NORMAL));
		payOnlinePayButton.setTouchedImage(PosResUtil.getImageIconFromResource(IMAGE_ONLINE_PAY_TOUCH));
		payOnlinePayButton.setOnClickListner(pay_by_button_listner);
		if(PosEnvSettings.getInstance().getUISetting().getOrderEntryUISettings().getPaymentPanelSetting().isOnlinePayButtonVisible()&& 
				paymentMethodesProvider.getPaymentModes().isCanPayOnline())
			addButtonToPanel(payOnlinePayButton);
	}
	private void createPayCardButton(){
		PosButton payButton=new PosButton();
		payButton.registerKeyStroke(KeyEvent.VK_F10);
		payButton.setTag(PaymentMode.Card);
		payButton.setBounds(0, 0, BUTTON_WIDTH, BUTTON_HEIGHT);
		payButton.setImage(PosResUtil.getImageIconFromResource(IMAGE_PAY_BY_CARD_NORMAL));
		payButton.setTouchedImage(PosResUtil.getImageIconFromResource(IMAGE_PAY_BY_CARD_TOUCH));
		payButton.setOnClickListner(pay_by_button_listner);
		if(PosEnvSettings.getInstance().getUISetting().getOrderEntryUISettings().getPaymentPanelSetting().isCardPayButtonVisibile()&& 
				paymentMethodesProvider.getPaymentModes().isCanPayByCard())
			addButtonToPanel(payButton);
	}

	private void createPayCompanyButton(){
		PosButton payButton=new PosButton();
		payButton.setTag(PaymentMode.Company);
		payButton.setBounds(0, 0, BUTTON_WIDTH, BUTTON_HEIGHT);
		payButton.setImage(PosResUtil.getImageIconFromResource(IMAGE_PAY_BY_COMPANY_NORMAL));
		payButton.setTouchedImage(PosResUtil.getImageIconFromResource(IMAGE_PAY_BY_COMPANY_TOUCH));
		payButton.setOnClickListner(pay_by_button_listner);
		/** Company pay is very rare so no need to show in quick pay  option **/
//		if(PosEnvSettings.getInstance().getUISetting().getOrderEntryUISettings().getPaymentPanelSetting().isCompanyPayButtonVisibile()&& 
//				paymentMethodesProvider.getPaymentModes().isCanPayByCompany())
//			addButtonToPanel(payButton);
	}

	private void createPayCouponButton(){
		PosButton payButton=new PosButton();
		payButton.registerKeyStroke(KeyEvent.VK_F12);
		payButton.setTag(PaymentMode.Coupon);
		payButton.setBounds(0, 0, BUTTON_WIDTH, BUTTON_HEIGHT);
		payButton.setImage(PosResUtil.getImageIconFromResource(IMAGE_PAY_BY_COUPON_NORMAL));
		payButton.setTouchedImage(PosResUtil.getImageIconFromResource(IMAGE_PAY_BY_COUPON_TOUCH));
		payButton.setOnClickListner(pay_by_button_listner);
		if(PosEnvSettings.getInstance().getUISetting().getOrderEntryUISettings().getPaymentPanelSetting().isVoucherPayButtonVisibile()&& 
				paymentMethodesProvider.getPaymentModes().isCanPayByVouchers())
			addButtonToPanel(payButton);
	}

	private void createSplitPayButton(){
		paySplitButton=new PosButton();
		paySplitButton.registerKeyStroke(KeyEvent.VK_F12);
		paySplitButton.setBounds(0, 0, BUTTON_WIDTH, BUTTON_HEIGHT);
		paySplitButton.setImage(PosResUtil.getImageIconFromResource(IMAGE_SPLIT_PAY_NORMAL));
		paySplitButton.setTouchedImage(PosResUtil.getImageIconFromResource(IMAGE_SPLIT_PAY_TOUCH));
		paySplitButton.setOnClickListner(split_pay_button_listner);
		if(PosEnvSettings.getInstance().getUISetting().getOrderEntryUISettings().getPaymentPanelSetting().isSplitPayButtonVisibile())
			addButtonToPanel(paySplitButton);
	}
	
	private void createDiscountButton(){
		
		PosButton discountButton=new PosButton();
//		discountButton.registerKeyStroke(KeyEvent.VK_F9);
		discountButton.setTag(PaymentMode.Cash);
		discountButton.setBounds(0, 0, BUTTON_WIDTH, BUTTON_HEIGHT);
		discountButton.setImage(PosResUtil.getImageIconFromResource(IMAGE_DISCOUNT_NORMAL));
		discountButton.setTouchedImage(PosResUtil.getImageIconFromResource(IMAGE_DISCOUNT_TOUCH));
		discountButton.setOnClickListner(discount_button_listner);
		if(PosEnvSettings.getInstance().getUISetting().getOrderEntryUISettings().getPaymentPanelSetting().isPreBillDiscountButtonVisible())
			addButtonToPanel(discountButton);
	}
	private IPosButtonListner discount_button_listner=new IPosButtonListner() {
		@Override
		public void onClicked(PosButton button) {
			
			if(!mOrderEntryForm.validateNewOrder()) return;
			
//			if(mOrderEntryForm.getBillGrid().getItemList().size()<=0) {
//				PosFormUtil.showErrorMessageBox(mOrderEntryForm,"There are no items in the order.");
//				return ;
//			}

			try {
				//BeanOrderHeader order=mOrderEntryForm.doSaveOrder(false);
				PosOrderPreBillDiscountForm form=new PosOrderPreBillDiscountForm(mOrderEntryForm );
				PosFormUtil.showLightBoxModal(mOrderEntryForm,form);
				if(form.isDirty()){
					
					if(mOperationsListener!=null)
						((IPosPaymentPanelListner)mOperationsListener).onPreBillDiscountChanged();
				}
				 
			} catch (Exception e) {
				PosLog.write(PosPaymentMethodsPanel.this, "IPosButtonListner.onClicked", e);
				PosFormUtil.showSystemError(mOrderEntryForm);
			}
		}
	};
	

	private IPosButtonListner split_pay_button_listner=new IPosButtonListner() {

		@Override
		public void onClicked(PosButton button) {

			if(!mOrderEntryForm.checkForActiveOrder(false) || 
					mOrderEntryForm.getBillGrid().getItemList().size()<=0 ||
					mOrderEntryForm.getBillGrid().getBillItemCount()==0) {
				PosFormUtil.showErrorMessageBox(mOrderEntryForm,"There is no active order or no item in the order.");
				return ;
			} 
			final BeanOrderHeader orderHdr = PosOrderUtil
					.buildOrderHeaders(false, mOrderEntryForm.getBillGrid());
			if(orderHdr.getOrderServiceType()==PosOrderServiceTypes.SALES_ORDER){
				PosFormUtil.showErrorMessageBox(mOrderEntryForm,"Split Payment feature is not supported for Sales Order Service Type");
				return ;
			}
			
			mSelectedPaymentMode=PaymentMode.Cash;
			BeanOrderHeader order=mOrderEntryForm.doSaveOrder(false);
			PosOrderSplitForm form=new PosOrderSplitForm(order.getOrderServiceType());
			form.setParent(mOrderEntryForm);
			
			form.setOrderHeader(order);
			PosFormUtil.showLightBoxModal(mOrderEntryForm,form);
			if(order.getStatus()==PosOrderStatus.Closed)
				mPaymentFormListner.onPaymentDone(this);
			else
				mOrderEntryForm.retrieveOrder(order.getOrderId());

		}
	};


	private IPosButtonListner quick_pay_by_button_listner=new IPosButtonListner() {
		
		@Override
		public void onClicked(PosButton button) {
			

			ArrayList<IPosBrowsableItem> quickPayModes =new ArrayList<IPosBrowsableItem>();
			
			for(String payMode :PosEnvSettings.getInstance().getUISetting().getQuickPayMode()){

				quickPayModes.add( Integer.parseInt(payMode)==1?PaymentMode.QuickCash: (Integer.parseInt(payMode)==2?PaymentMode.QuickCard:PaymentMode.QuickCredit));
			}
			
			
			
			if(quickPayModes.size()==1){
				button.setTag(quickPayModes.get(0));
				pay_by_button_listner.onClicked(button);
			}else{

				showQuickPaymodes(quickPayModes, button);
			}
			
		}
	};
	
	private void showQuickPaymodes(ArrayList<IPosBrowsableItem> quickPayModes,final PosButton button ){
		
 
		final PosObjectBrowserForm form=new PosObjectBrowserForm("Quick Payment Mode", quickPayModes , ItemSize.Wider,true);
		form.setListner(new IPosObjectBrowserListner() {

			@Override
			public void onItemSelected(IPosBrowsableItem item) {
				button.setTag((PaymentMode)item);
				pay_by_button_listner.onClicked(button);
			}

			@Override
			public void onCancel() {
				// TODO Auto-generated method stub

			}
		});
		PosFormUtil.showLightBoxModal(mOrderEntryForm,form);
		
	}
	
	private void onClickStandardPayment(PaymentMode payMode){
		
		if(!mOrderEntryForm.checkForActiveOrder(true)) return;
		if(mOrderEntryForm.getBillGrid().getItemList().size()<=0) {
			PosFormUtil.showErrorMessageBox(mOrderEntryForm,"There are no items in the order.");
			return ;
		}else if(mOrderEntryForm.getBillGrid().getBillItemCount()==0) {
			PosFormUtil.showErrorMessageBox(mOrderEntryForm,"There are no items in the order.");
			return ;
		}

		try {
			
			if(!mOrderEntryForm.saveOrderBeforePayment())
				return;
			BeanOrderHeader order =PosOrderUtil.buildOrderHeaders(mOrderEntryForm.getBillGrid());
			mSelectedPaymentMode=payMode;
			
			mOrderEntryForm.setExtraChargeTax();
		 
			initPaymentForm();
			
			mPaymentForm.setOrderHeader(order);

			
			if (mSelectedPaymentMode==PaymentMode.QuickCash || mSelectedPaymentMode==PaymentMode.QuickCard 
					 || mSelectedPaymentMode==PaymentMode.QuickCredit 
					 || mSelectedPaymentMode==PaymentMode.Online){
				
				mPaymentForm.setPaymentMode(mSelectedPaymentMode);
				mPaymentForm.doQuickPayment();
				
			}else{
				
				mSelectedPaymentMode=(order.getCustomer()!=null && order.getCustomer().isIsArCompany())?PaymentMode.Company:payMode;
				mPaymentForm.setPaymentMode(mSelectedPaymentMode);
				PosFormUtil.showLightBoxModal(mOrderEntryForm, mPaymentForm);
			}
			
		} catch (Exception e) {
			PosLog.write(PosPaymentMethodsPanel.this, "IPosButtonListner.onClicked", e);
			PosFormUtil.showSystemError(mOrderEntryForm);
		}
	} 
	private IPosButtonListner pay_by_button_listner=new IPosButtonListner() {
		@Override
		public void onClicked(PosButton button) {
			onClickStandardPayment((PaymentMode) button.getTag());
		}
	};

	private IPosPaymentFormListner mPaymentFormListner=new IPosPaymentFormListner() {
		@Override
		public void onPaymentDone(Object sender) {
			if(mOperationsListener!=null)
				((IPosPaymentPanelListner)mOperationsListener).onPaymentDone(mSelectedPaymentMode);
		}

		@Override
		public void onPaymentCancelled(Object sender) {
		//	 mPaymentForm.getActualOrderHeader()
			if(mOperationsListener!=null)
				((IPosPaymentPanelListner)mOperationsListener).onPaymentCancelled(sender);
		}

		@Override
		public void onPaymentStatusChanged(Object sender) {
			// TODO Auto-generated method stub
			
		}
	};

	
	public void doPayment(PosPaymentOption paymentOption){
		
		switch(paymentOption){
		case STANDARD:
			pay_by_button_listner.onClicked(payCashButton);
			break;
		case SPLIT:
			split_pay_button_listner.onClicked(paySplitButton);
			break;
		case QUICKCASH:
			onClickStandardPayment(PaymentMode.QuickCash);
			break;
		case QUICKCARD:
			onClickStandardPayment(PaymentMode.QuickCard);
			break;
		default:
				break;
		}
	}

	  
}
