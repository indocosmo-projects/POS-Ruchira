/**
 * 
 */
package com.indocosmo.pos.common.utilities;

import java.util.ArrayList;

import com.indocosmo.pos.common.PosEnvSettings;
import com.indocosmo.pos.common.PosLog;
import com.indocosmo.pos.common.enums.PosOrderServiceTypes;
import com.indocosmo.pos.common.enums.split.BillSplitMethod;
import com.indocosmo.pos.data.beans.BeanOrderDetail;
import com.indocosmo.pos.data.beans.BeanOrderHeader;
import com.indocosmo.pos.data.beans.BeanOrderHeader.PosOrderStatus;
import com.indocosmo.pos.data.beans.BeanOrderPayment;
import com.indocosmo.pos.data.beans.BeanOrderPaymentHeader;
import com.indocosmo.pos.data.beans.BeanOrderSplit;
import com.indocosmo.pos.data.beans.BeanOrderSplitDetail;
import com.indocosmo.pos.data.beans.BeanSaleItem;
import com.indocosmo.pos.data.beans.BeanOrderHeader.PaymentProcessStatus;
import com.indocosmo.pos.data.beans.BeanPayModeBase.PaymentMode;
import com.indocosmo.pos.data.providers.shopdb.PosOrderHdrProvider;
import com.indocosmo.pos.forms.PosOrderEntryForm;
import com.indocosmo.pos.forms.components.orderentry.PosPaymentMethodsPanel;
import com.indocosmo.pos.forms.messageboxes.PosMessageBoxForm.MessageBoxButtonTypes;
import com.indocosmo.pos.forms.messageboxes.PosMessageBoxForm.MessageBoxResults;

/**
 * @author jojesh
 * 
 */
public final class PosPaymentUtil {
 
	public static void doAdvancePaymentForSalesOrder(PosOrderEntryForm mOrderEntryForm) throws Exception{
		
		if(!mOrderEntryForm.checkForActiveOrder(true)) return;
		if(mOrderEntryForm.getBillGrid().getItemList().size()<=0) {
			PosFormUtil.showErrorMessageBox(mOrderEntryForm,"There are no items in the order.");
			return ;
		}

		try {

			BeanOrderHeader order =PosOrderUtil.buildOrderHeaders(false,mOrderEntryForm.getBillGrid());

			if (order.getOrderPaymentItems()!=null && order.getOrderPaymentItems().size()>0){
				
				BeanOrderPayment payment=order.getOrderPaymentItems().get(0);
				if(!PosEnvSettings.getPosEnvSettings().getPosDate().equals(payment.getPaymentDate()) ||
						PosEnvSettings.getInstance().getCashierShiftInfo().getShiftItem().getId()!=payment.getShiftId())
					return;
			}
			
			ArrayList<BeanOrderSplit> orderSplits=PosOrderSplitUtil.doSimpleSplitForAdvancePayment(order);
			order.setTotalAmountPaid(order.getAdvanceAmount());
			order.setChangeAmount(0);
			order.setRoundAdjustmentAmount(0);
			
			order.setOrderSplits(orderSplits);

			ArrayList<BeanOrderPayment> tmpOrderPaymentItems = new ArrayList<BeanOrderPayment>();
			
			BeanOrderPayment orderPayment ;
			if (order.getOrderPaymentItems()==null || order.getOrderPaymentItems().size()==0){

				orderPayment= new BeanOrderPayment();
				orderPayment.setOrderId(order.getOrderId());
				setCashierData(orderPayment);
				
			}else{
				
				orderPayment=order.getOrderPaymentItems().get(0);
				orderPayment.setNew(false);
				orderPayment.setPaymentTime(PosDateUtil.getDateTime());
			}
			orderPayment.setPaidAmount(order.getAdvanceAmount());
			
			switch(order.getAdvancePaymentMode()){
			case Card:
				orderPayment.setPaymentMode(PaymentMode.Card);
				orderPayment.setCardType("Other");
				orderPayment.setCardNo("");
				orderPayment.setCardExpiryMonth(0);
				orderPayment.setCardExpiryYear(0);
				orderPayment.setCardApprovalCode("");
				orderPayment.setAccount("");
				break;
			default:
				orderPayment.setPaymentMode(order.getAdvancePaymentMode());
				
				 
			}
	 
			tmpOrderPaymentItems.add(orderPayment);
		 	order.setOrderPaymentItems(tmpOrderPaymentItems);
		 	
			final ArrayList<BeanOrderPaymentHeader> orderPayHdrList=new ArrayList<BeanOrderPaymentHeader>();
			orderPayHdrList.add(PosPaymentUtil.getOrderPaymentHeaderForAdvancePayment(order));
			
			order.setOrderPaymentHeaders(orderPayHdrList);

			order.setStatus(PosOrderStatus.Partial);
			order.setSplitPartRecieved(order.getAdvanceAmount());
			order.setSplitPartUsed(0);
			order.setPaymentProcessStatus(PaymentProcessStatus.COMPLETED);
			PosOrderHdrProvider orderHdrProvider=new PosOrderHdrProvider();
			orderHdrProvider.saveOrder(order, true, false, true, true);
			
		} catch (Exception e) {
			PosLog.write(mOrderEntryForm, "IPosButtonListner.onClicked", e);
			throw new Exception(e.getMessage());
		}
	} 
	
	public static void doOnlinePayment(PosOrderEntryForm mOrderEntryForm,double billAmount) throws Exception{
		
		if(!mOrderEntryForm.checkForActiveOrder(true)) return;
		if(mOrderEntryForm.getBillGrid().getItemList().size()<=0) {
			PosFormUtil.showErrorMessageBox(mOrderEntryForm,"There are no items in the order.");
			return ;
		}

		try {

			BeanOrderHeader order =PosOrderUtil.buildOrderHeaders(false,mOrderEntryForm.getBillGrid());

			if (order.getOrderPaymentItems()!=null && order.getOrderPaymentItems().size()>0){
				
				BeanOrderPayment payment=order.getOrderPaymentItems().get(0);
				if(!PosEnvSettings.getPosEnvSettings().getPosDate().equals(payment.getPaymentDate()) ||
						PosEnvSettings.getInstance().getCashierShiftInfo().getShiftItem().getId()!=payment.getShiftId())
					return;
			}
			
			ArrayList<BeanOrderSplit> orderSplits=null;//PosOrderSplitUtil.doSimpleSplitForAdvancePayment(order);
			order.setTotalAmountPaid(order.getTotalAmountPaid() + billAmount);
			order.setChangeAmount(0);
			order.setRoundAdjustmentAmount(0);
			
			order.setOrderSplits(orderSplits);

			ArrayList<BeanOrderPayment> tmpOrderPaymentItems = new ArrayList<BeanOrderPayment>();
			
			BeanOrderPayment orderPayment ;
			if (order.getOrderPaymentItems()==null || order.getOrderPaymentItems().size()==0){

				orderPayment= new BeanOrderPayment();
				orderPayment.setOrderId(order.getOrderId());
				setCashierData(orderPayment);
				
			}else{
				
				orderPayment=order.getOrderPaymentItems().get(0);
				orderPayment.setNew(false);
				orderPayment.setPaymentTime(PosDateUtil.getDateTime());
			}
			orderPayment.setPaidAmount(billAmount);
			orderPayment.setPaymentMode(PaymentMode.Online);
			tmpOrderPaymentItems.add(orderPayment);
		 	order.setOrderPaymentItems(tmpOrderPaymentItems);
		 	
			final ArrayList<BeanOrderPaymentHeader> orderPayHdrList=new ArrayList<BeanOrderPaymentHeader>();
			orderPayHdrList.add(PosPaymentUtil.getOrderPaymentHeaderForAdvancePayment(order));
			
			order.setOrderPaymentHeaders(orderPayHdrList);

			order.setStatus(PosOrderStatus.Closed);
			order.setSplitPartRecieved(order.getAdvanceAmount());
			order.setSplitPartUsed(0);
			order.setPaymentProcessStatus(PaymentProcessStatus.COMPLETED);
			PosOrderHdrProvider orderHdrProvider=new PosOrderHdrProvider();
			orderHdrProvider.saveOrder(order, true, false, true, true);
			
		} catch (Exception e) {
			PosLog.write(mOrderEntryForm, "IPosButtonListner.onClicked", e);
			throw new Exception(e.getMessage());
		}
	} 
	/*
	 * 
	 */
	/**
	 * @param payment
	 */
	public static void setCashierData(BeanOrderPayment payment) {
		
		payment.setCashierID(PosEnvSettings.getInstance().getCashierShiftInfo()
				.getCashierInfo().getId());
		payment.setPaymentTime(PosDateUtil.getDateTime());
		payment.setPaymentDate(PosEnvSettings.getPosEnvSettings().getPosDate());
		payment.setShiftId(PosEnvSettings.getInstance().getCashierShiftInfo().getShiftItem().getId());
		payment.setNew(true);
	}
	
	/**
	 * 
	 *  Update the original payment header object with the values
	 */
	public static BeanOrderPaymentHeader getOrderPaymentHeaderForAdvancePayment(BeanOrderHeader order ) {
		
		BeanOrderPaymentHeader orderPaymentHdr;
		if (order.getOrderPaymentHeaders()==null || order.getOrderPaymentHeaders().size()==0){
			orderPaymentHdr=new BeanOrderPaymentHeader();
			orderPaymentHdr.setOrderId(order.getOrderId());
			orderPaymentHdr.setNew(true);
			orderPaymentHdr.setShiftId(PosEnvSettings.getInstance().getCashierShiftInfo().getShiftItem().getId());
			
			orderPaymentHdr.setPaymentTime(PosDateUtil.getDateTime());
			orderPaymentHdr.setPaymentDate(PosEnvSettings.getPosEnvSettings().getPosDate());
			
		}else{
			orderPaymentHdr=order.getOrderPaymentHeaders().get(0);
			orderPaymentHdr.setPaymentTime(PosDateUtil.getDateTime());
			orderPaymentHdr.setNew(false);
		}
		
		
		orderPaymentHdr.setTotalAmount(order.getTotalAmountPaid());
		orderPaymentHdr.setDetailTotal(0);
		orderPaymentHdr.setTotalTax1(0);
		orderPaymentHdr.setTotalTax2(0);
		orderPaymentHdr.setTotalTax3(0);
		orderPaymentHdr.setTotalGST(0);
		orderPaymentHdr.setTotalServiceTax(0);
		orderPaymentHdr.setTotalDetailDiscount(0);
		
		
		orderPaymentHdr.setTotalAmountPaid(order.getTotalAmountPaid() );
		orderPaymentHdr.setBillTaxAmount(0);
		orderPaymentHdr.setChangeAmount(0);
		orderPaymentHdr.setCashOut(0);
		 
		orderPaymentHdr.setBillDiscountAmount(0);
		orderPaymentHdr.setRoundAdjustmentAmount(0);
		orderPaymentHdr.setRefund(false);
		
		
		orderPaymentHdr.setAdvance(true);
		
		return orderPaymentHdr;
	}
	
	 
	
	
	/**
	 * @param orderHeader
	 * @param split
	 * @return
	 * @throws CloneNotSupportedException
	 */
	public static BeanOrderHeader convertSplitToOrder(BeanOrderHeader orderHeader,BeanOrderSplit split
			) throws CloneNotSupportedException {

		final ArrayList<BeanOrderSplitDetail> splitDetails=split.getSplitDetails();
		final ArrayList<BeanOrderDetail> orderDtlItemList=new ArrayList<BeanOrderDetail>();
		final BeanOrderHeader newOrder=new BeanOrderHeader();

		if(split.getBasedOn().getBillSplitMethod()==BillSplitMethod.Simple){

			final double billNetTotal=split.getAmount();
			newOrder.setTotalAmount(billNetTotal);
			
		}  

		newOrder.setNewOrder(orderHeader.isNewOrder());
		newOrder.setOrderDate(orderHeader.getOrderDate());
		newOrder.setCustomer(orderHeader.getCustomer());
		newOrder.setOrderId(orderHeader.getOrderId());
		newOrder.setOrderServiceType(orderHeader.getOrderServiceType());
		newOrder.setOrderTime(orderHeader.getOrderTime());
		newOrder.setRemarks(orderHeader.getRemarks());
		newOrder.setServedBy(orderHeader.getServedBy());
		newOrder.setServiceTable(orderHeader.getServiceTable());
		newOrder.setCovers(orderHeader.getCovers());
		newOrder.setShiftId(orderHeader.getShiftId());
		newOrder.setStatus(orderHeader.getStatus());
		newOrder.setUser(orderHeader.getUser());
		newOrder.setOrderCustomer(orderHeader.getOrderCustomer());
		return newOrder;
	}
	
	
	
//	/**
//	 * @param orderHeader
//	 * @param selectedSplitItems
//	 * @throws Exception 
//	 */
//	public void setOrderWithSplit(BeanOrderHeader orderHeader,ArrayList<BeanOrderSplit> selectedSplitItems) throws Exception {
//
//		/**
//		 * Reset all variables
//		 * Same instance of the form is used for multiple payments
//		 */
//		setForNewOrder();
//
//		this.mOrderHeaderActual = orderHeader;
//		this.mSelectedSplitItems=selectedSplitItems;
//		this.mSplitForPayment=PosOrderSplitUtil.prepareSplitsForPayment(selectedSplitItems);
//		this.mOrderHeaderForPayment = PosOrderSplitUtil.convertSplitToOrder(orderHeader, mSplitForPayment);
//		this.mTaxBeforeBillDiscount=orderHeader.getBillTaxAmount();
//		this.mCompletedPayment = 0;
//		this.mSplitMethod=selectedSplitItems.get(0).getBasedOn();
//
//		setPartialPayment(true);
//		setBillTaxAmounts(mOrderHeaderForPayment);
//		mBillTotalAmount=PosOrderUtil.getTotalBalanceOnBill(mOrderHeaderForPayment);
//		mBillTotalAmount+=mSplitForPayment.getAdjustAmount();
//		
//		double totalAmount=mBillTotalAmount ;
//		if(orderHeader.getOrderServiceType()==PosOrderServiceTypes.SALES_ORDER){
//			totalAmount+=orderHeader.getAdvanceAmount();
//			totalAmount-=orderHeader.getExtraCharges();
//		}
//		buildPaymentSummaryInfo(totalAmount);
//		resetAllTabs();
//		setAmounts();
//		resetTabVisibility();
//
//		final boolean isSplitPartAdjTabVisible=canShowSplitAdjTab();
//		if(isSplitPartAdjTabVisible){
//
//			mTabControl.setTabVisibility(mSplitAdjustmentPanel, isSplitPartAdjTabVisible);
//			if(PosFormUtil.showQuestionMessageBox(mParent, MessageBoxButtonTypes.YesNo, 
//					"There is part amount ("
//							+ PosEnvSettings.getInstance().getCurrencySymbol()
//							+String.valueOf(PosCurrencyUtil.format(mOrderHeaderActual.getSplitPartAmountLeft()))
//							+") available for this order. Do you want to adjust?", 
//							null)==MessageBoxResults.Yes){
//				mTabControl.setSelectedTab(mSplitAdjustmentPanel);
//			}else{
////				setRoundingAdjustment(0);
////				mCashPaymentPanel.onGotFocus();;
//				mTabControl.setSelectedTab(mCashPaymentPanel);
//			}
//		}
//
//	}
}
