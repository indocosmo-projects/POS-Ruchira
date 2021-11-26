/**
 * 
 */
package com.indocosmo.pos.common.utilities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import com.indocosmo.pos.common.enums.PosOrderServiceTypes;
import com.indocosmo.pos.common.enums.split.BillSplitMethod;
import com.indocosmo.pos.common.enums.split.SplitBasedOn;
import com.indocosmo.pos.data.beans.BeanOrderDetail;
import com.indocosmo.pos.data.beans.BeanOrderHeader;
import com.indocosmo.pos.data.beans.BeanOrderSplit;
import com.indocosmo.pos.data.beans.BeanOrderSplitDetail;
import com.indocosmo.pos.data.beans.BeanSaleItem;
import com.indocosmo.pos.forms.split.PosOrderSplitForm;

/**
 * @author jojesh
 *
 */
public final class PosOrderSplitUtil {


	/**
	 * Return the splits part amount.
	 * @param order
	 * @return
	 */
	public static double getSplitPartAmountUsed(BeanOrderHeader order){

		double amount=0.0;
		ArrayList<BeanOrderSplit> splits=order.getOrderSplits();
		if(splits!=null && splits.size()>0){

			for(BeanOrderSplit split:splits)
				amount+=split.getPartPayAdjustment();
		}

		return amount;
	}

	/**
	 * Return the splits part amount.
	 * @param order
	 * @return
	 */
	public static double getSplitPartAmountRecieved(BeanOrderHeader order){

		double amount=0.0;

		ArrayList<BeanOrderSplit> splits=order.getOrderSplits();

		if(splits!=null && splits.size()>0){

			for(BeanOrderSplit split:splits)
				amount+=((split.getBasedOn().getBillSplitMethod()==BillSplitMethod.Simple)?split.getActualAmount():0.0);

		}

		return amount;
	}

	/**
	 * Return the splits part amount.
	 * @param order
	 * @return
	 */
	public static double getSplitTotalPaidAmount(BeanOrderHeader order){

		double amount=0.0;

		ArrayList<BeanOrderSplit> splits=order.getOrderSplits();

		if(splits!=null && splits.size()>0){

			for(BeanOrderSplit split:splits)
				amount+=split.getActualAmount()-split.getPartPayAdjustment();

		}

		return amount;
	}

	/**
	 * Return the splits part amount.
	 * @param order
	 * @return
	 */
	public static double getSplitTotalPaidAmountLessDiscount(BeanOrderHeader order){

		double amount=0.0;

		ArrayList<BeanOrderSplit> splits=order.getOrderSplits();

		if(splits!=null && splits.size()>0){

			for(BeanOrderSplit split:splits)
				amount+=(split.getActualAmount()-split.getDiscountAmount());

		}

		return amount;
	}
	
	/**
	 * @param splits
	 * @return
	 * @throws CloneNotSupportedException
	 */
	public static BeanOrderSplit prepareSplitsForPayment(ArrayList<BeanOrderSplit> splits) throws Exception{

		BeanOrderSplit split=null;
		String splitName="";
		ArrayList<BeanOrderSplitDetail> splitDetails=null;

		if(splits!=null && splits.size()>0){

			split=new BeanOrderSplit();
			for(BeanOrderSplit splitItem:splits){

				split.setAdjustAmount(split.getAdjustAmount()+splitItem.getAdjustAmount());
				split.setAmount(split.getAmount()+splitItem.getAmount());
				split.setBasedOn(splitItem.getBasedOn());
				/**
				 * Combine the names
				 */
				splitName+=((splitName.equals(""))?"": " & ")+splitItem.getDescription();
				split.setDescription(splitName);
				split.setOrderID(splitItem.getOrderID());
				split.setPayed(false);
				/**
				 * Sets the details
				 */
				if(splitItem.getSplitDetails()!=null && splitItem.getSplitDetails().size()>0){

					if(splitDetails==null)
						splitDetails=new ArrayList<BeanOrderSplitDetail>();

					for(BeanOrderSplitDetail spliteDetailItem:splitItem.getSplitDetails()){

						//						if(spliteDetailItem.getOrderDetailtem().getParentDtlId()==null || spliteDetailItem.getOrderDetailtem().getParentDtlId()=="")
						splitDetails.add(spliteDetailItem.clone());
						if(spliteDetailItem.getOrderDetailtem().hasSubItems()){

							final BeanOrderDetail orderDtrlItem=spliteDetailItem.getOrderDetailtem();
							/**
							 * appends the extra items to the split 
							 */
							if(orderDtrlItem.isExtraItemsSelected())
								appendSubItemsToSplitArray(splitDetails,spliteDetailItem.getOrderDetailtem().getExtraItemList().values());
							/**
							 * Appends the combo items to the split list 
							 */
							if(orderDtrlItem.isComboContentsSelected())
								appendSubItemsToSplitArray(splitDetails,spliteDetailItem.getOrderDetailtem().getComboSubstitutes() .values());
						}

					}

				}
				split.setSplitDetails(splitDetails);
				split.setValue(splitItem.getValue());
			}

		}

		return split;
	}

	/**
	 * Appends the sub items to the given split array for saving 
	 * @param splitDetails
	 * @param orderDetailtemListCollection
	 * @throws CloneNotSupportedException 
	 */
	private static void appendSubItemsToSplitArray(ArrayList<BeanOrderSplitDetail> splitDetails, Collection<ArrayList<BeanOrderDetail>> orderDetailtemListCollection) throws CloneNotSupportedException {

		for(ArrayList<BeanOrderDetail> orderDetailtems :orderDetailtemListCollection )
			appendSubItemsToSplitArray(splitDetails,orderDetailtems);
	}

	/**
	 * Appends the sub items to the given split array for saving
	 * @param splitDetails
	 * @param orderDetailtem
	 * @throws CloneNotSupportedException 
	 */
	private static void appendSubItemsToSplitArray(ArrayList<BeanOrderSplitDetail> splitDetails, ArrayList<BeanOrderDetail> orderDetailtems) throws CloneNotSupportedException {

		for(BeanOrderDetail orderDetailtem :orderDetailtems){

			BeanOrderSplitDetail spliteDetailItem=getSplitDetailItem(orderDetailtem,1,orderDetailtem.getSaleItem().getQuantity());
			splitDetails.add(spliteDetailItem);
		}

	}

	/**
	 * @param dtlItem
	 * @param orderDetailubID
	 * @param splitItemQty
	 * @return
	 * @throws CloneNotSupportedException
	 */
	public static BeanOrderSplitDetail getSplitDetailItem(BeanOrderDetail dtlItem,double splitItemQty) throws CloneNotSupportedException{

		return getSplitDetailItem( dtlItem,1, splitItemQty);
	}

	/**
	 * @param dtlItem
	 * @param orderDetailubID
	 * @param splitItemQty
	 * @return
	 * @throws CloneNotSupportedException
	 */
	public static BeanOrderSplitDetail getSplitDetailItem(BeanOrderDetail dtlItem,int subID,double splitItemQty) throws CloneNotSupportedException{

		final String orderDetailubID=PosOrderUtil.appendToId(dtlItem.getId(), subID);
		return getSplitDetailItem( dtlItem, orderDetailubID, splitItemQty);

	}

	/**
	 * @param dtlItem
	 * @param orderDetailubID
	 * @param splitItemQty
	 * @return
	 * @throws CloneNotSupportedException
	 */
	public static BeanOrderSplitDetail getSplitDetailItem(BeanOrderDetail dtlItem,String orderDetailubID,double splitItemQty) throws CloneNotSupportedException{

		BeanOrderSplitDetail splitDetailItem=null;

		final BeanOrderDetail splitOrderDetail=dtlItem.clone(); 
		splitDetailItem=new BeanOrderSplitDetail();
		splitDetailItem.setOrderDetailItem(splitOrderDetail);

		updateSplitItemQuantity(splitDetailItem,splitItemQty);

		splitDetailItem.setOrderDetailSubID(orderDetailubID);
		/**
		 * Sets the split quantity
		 */
		splitDetailItem.setOrderId(splitOrderDetail.getOrderId());

		return splitDetailItem;
	}

	/**
	 * Updates and recalculate the amounts
	 * @param splitDetailItem
	 * @param splitItemQty
	 */
	public static void updateSplitItemQuantity(BeanOrderSplitDetail splitDetailItem, double splitItemQty){

		final BeanOrderDetail splitOrderDetail =splitDetailItem.getOrderDetailtem();
		final BeanSaleItem saleItem=splitOrderDetail.getSaleItem();
		final double splitItemPrice=(PosSaleItemUtil.getGrandTotal(saleItem));
		final double subItemTotal=PosOrderUtil.getSubItemTotalAmount(splitOrderDetail);

		splitDetailItem.setQuantity(splitItemQty);
		final double splitTotal=(subItemTotal+splitItemPrice)*splitDetailItem.getSplitShare();
		splitDetailItem.setPrice(splitTotal);

	}

	/**
	 * Set the split quantity
	 * @param splitDetailItem
	 * @param splitItemQty
	 */
	public static void setSplitItemQuantity(BeanOrderSplitDetail splitDetailItem, double splitItemQty){

		BeanOrderDetail orderDetailItem=splitDetailItem.getOrderDetailtem();

		if(orderDetailItem==null) return ;

		orderDetailItem.setItemSplitShareQty(splitItemQty);

		if(orderDetailItem.hasSubItems()){

			if(orderDetailItem.isComboContentsSelected()){

				setSplitItemQuantity(orderDetailItem.getComboSubstitutes().values(),splitItemQty);

			}

			if(orderDetailItem.isExtraItemsSelected()){

				setSplitItemQuantity(orderDetailItem.getExtraItemList().values(),splitItemQty);

			}
		}

	}


	/**
	 * Set the split quantity
	 * @param itemArrayList
	 * @param splitItemQty
	 */
	private static void setSplitItemQuantity(Collection<ArrayList<BeanOrderDetail>> itemArrayList, double splitItemQty){

		for(ArrayList<BeanOrderDetail> subItemList:itemArrayList){

			for(BeanOrderDetail subItem:subItemList){

				subItem.setItemSplitShareQty(splitItemQty);
			}
		}

	}

	/**
	 * Groups the splittings and put under the order details id
	 * @param splitList
	 * @returns Hash map with order detail item id as key and splitdetail item as value
	 */
	public static HashMap<String, ArrayList<BeanOrderSplitDetail>> createSplitItemIDList(	ArrayList<BeanOrderSplit> splitList) {

		HashMap<String, ArrayList<BeanOrderSplitDetail>> idList=null;

		for(BeanOrderSplit splitItem: splitList){

			if(splitItem.getSplitDetails()!=null){

				if(idList==null) 
					idList=new HashMap<String, ArrayList<BeanOrderSplitDetail>>();

				for(BeanOrderSplitDetail item:splitItem.getSplitDetails()){

					ArrayList<BeanOrderSplitDetail> itemList=null;

					if(idList.containsKey(item.getOrderDetailItemID()))

						itemList=idList.get(item.getOrderDetailItemID());
					else{

						itemList=new ArrayList<BeanOrderSplitDetail>();
						idList.put(item.getOrderDetailItemID(),itemList);
					}

					itemList.add(item);
				}

			}
		}

		return idList;
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
			
		}else{

			newOrder.setDetailItemCount(splitDetails.size());
			newOrder.setOrderDetailItems(orderDtlItemList);

			for(BeanOrderSplitDetail spltDtlItem:splitDetails){

				/***
				 * 
				 * Only parent items will be added to the order
				 * Don not add the subitems directly to the order.
				 * 
				 */
				if(spltDtlItem.getOrderDetailtem().getParentDtlId()==null || spltDtlItem.getOrderDetailtem().getParentDtlId().trim()==""){

					BeanOrderDetail orderDtlItem=spltDtlItem.getOrderDetailtem().clone();
					final BeanSaleItem saleItem=orderDtlItem.getSaleItem();

					updateOrderHeaderAmounts(newOrder,orderDtlItem);

					/**
					 * Add sub item amounts too.
					 * 
					 */
					if(orderDtlItem.hasSubItems()){
						/***
						 * Combo items  
						 * 
						 */
						if(orderDtlItem.isComboContentsSelected()){
							for(ArrayList<BeanOrderDetail>  subDtlItemList: orderDtlItem.getComboSubstitutes().values()){
								for(BeanOrderDetail  subDtlItem: subDtlItemList){
									updateOrderHeaderAmounts(newOrder,subDtlItem);
								}
							}
						}
						/***
						 * 
						 * Extras
						 */
						if(orderDtlItem.isExtraItemsSelected()){
							for(ArrayList<BeanOrderDetail>  subDtlItemList: orderDtlItem.getExtraItemList().values()){
								for(BeanOrderDetail  subDtlItem: subDtlItemList){
									updateOrderHeaderAmounts(newOrder,subDtlItem);
								}
							}
						}
					}

					orderDtlItemList.add(orderDtlItem);
				}
			}
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
//		newOrder.setOrderByMedium(orderHeader.getOrderByMedium());
//		newOrder.setDeliveryType(orderHeader.getDeliveryType());
//		newOrder.setExtraCharges(orderHeader.getExtraCharges());
//		newOrder.setAdvanceAmount(orderHeader.getAdvanceAmount());
//		newOrder.setAdvancePaymentMode(orderHeader.getAdvancePaymentMode());

		/***
		 * If any adjustment is there update the order total amount.
		 * 
		 */
		//		if(split.getAdjustAmount()!=0){
		//			
		//			double total=newOrder.getTotalAmount();
		//			total+=split.getAdjustAmount();
		//			newOrder.setTotalAmount(total);
		//		}

		return newOrder;
	}

	/**
	 * @param ohItem
	 * @param odItem
	 */
	private static void updateOrderHeaderAmounts(BeanOrderHeader ohItem, BeanOrderDetail odItem){

		if(odItem.isVoid()) return;
		
		double billNetTotal=0.0;
		double detailTotal=0.0;
		double detailItemQty=0.0;
		double detailTotalDiscount=0.0;
		double detailTotalGST=0.0;
		double detailTotalServiceTax=0.0;
		double detailTotalTax1=0.0;
		double detailTotalTax2=0.0;
		double detailTotalTax3=0.0;

		detailTotal = PosCurrencyUtil.roundTo(PosOrderUtil.getTotalItemPrice(odItem));
		detailTotalDiscount = PosCurrencyUtil.roundTo(PosOrderUtil.getTotalDiscountAmount(odItem));
		billNetTotal=PosCurrencyUtil.roundTo(PosOrderUtil.getGrandTotal(odItem));
		//			mBillTaxAmount += PosNumberUtil.roundTo(PosSaleItemUtil.getTotalTaxAmount(saleItem,true));
		detailItemQty = PosUomUtil.roundTo(PosOrderUtil.getItemQuantity(odItem),odItem.getSaleItem().getUom());
		detailTotalTax1 = PosCurrencyUtil.roundTo(PosOrderUtil.getTotalT1Amount(odItem));
		detailTotalTax2 = PosCurrencyUtil.roundTo(PosOrderUtil.getTotalT2Amount(odItem));
		detailTotalTax3 = PosCurrencyUtil.roundTo(PosOrderUtil.getTotalT3Amount(odItem));
		detailTotalGST = PosCurrencyUtil.roundTo(PosOrderUtil.getTotalGSTAmount(odItem));
		detailTotalServiceTax = PosCurrencyUtil.roundTo(PosOrderUtil.getTotalServiceTaxAmount(odItem));

		ohItem.setDetailItemQuatity(ohItem.getDetailItemQuatity()+ detailItemQty);
		ohItem.setTotalAmount(ohItem.getTotalAmount()+billNetTotal);
		ohItem.setDetailTotal(ohItem.getDetailTotal()+detailTotal);
		//		newOrder.setTotalAmountPaid(total);
		ohItem.setTotalDetailDiscount(ohItem.getTotalDetailDiscount()+detailTotalDiscount);
		ohItem.setTotalGST(ohItem.getTotalGST()+detailTotalGST);
		//		newOrder.setTotalPrintCount(totalPrintCount);
		ohItem.setTotalServiceTax(ohItem.getTotalServiceTax()+detailTotalServiceTax);
		ohItem.setTotalTax1(ohItem.getTotalTax1()+detailTotalTax1);
		ohItem.setTotalTax2(ohItem.getTotalTax2()+detailTotalTax2);
		ohItem.setTotalTax3(ohItem.getTotalTax3()+detailTotalTax3);

	}
	

	/**
	 * 
	 */
	public static ArrayList<BeanOrderSplit> doSimpleSplitForAdvancePayment(BeanOrderHeader order) {

		double value=0.0;
 
		value=order.getAdvanceAmount();

		final ArrayList<BeanOrderSplit> splitItems=new ArrayList<BeanOrderSplit>();
		final String billName=PosOrderSplitForm.DEF_BILL_PREFIX+PosStringUtil.paddLeft(String.valueOf(1), 2, '0' );
		
		BeanOrderSplit orderSplit;
		if(order.getOrderSplits()==null || order.getOrderSplits().size()==0)
			orderSplit=new BeanOrderSplit();
		else
			orderSplit=order.getOrderSplits().get(0);
		
		orderSplit.setDescription(billName);
		orderSplit.setOrderID(order.getOrderId());
		orderSplit.setAmount(value);
		orderSplit.setBasedOn(SplitBasedOn.Amount);
		
		
		orderSplit.setPartPayAdjustment(0);
		orderSplit.setRoundingAdjustment(0);
		orderSplit.setDiscount(0);
		orderSplit.setPayedAmount(value);
		orderSplit.setPayed(true);
		splitItems.add(orderSplit);

		return splitItems;
	}
}
