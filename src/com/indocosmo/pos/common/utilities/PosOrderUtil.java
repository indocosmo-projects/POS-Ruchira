/**
 * 
 */
package com.indocosmo.pos.common.utilities;

import java.awt.Color;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.swing.RootPaneContainer;

import com.indocosmo.pos.common.PosEnvSettings;
import com.indocosmo.pos.common.PosLog;
import com.indocosmo.pos.common.beans.BeanReceiptTaxSummary;
import com.indocosmo.pos.common.beans.settings.ui.orderentry.BeanUIOrderEntrySetting;
import com.indocosmo.pos.common.enums.PosOrderServiceTypes;
import com.indocosmo.pos.common.enums.PosPaymentOption;
import com.indocosmo.pos.data.beans.BeanOrderDetail;
import com.indocosmo.pos.data.beans.BeanOrderDetail.OrderDetailItemType;
import com.indocosmo.pos.data.beans.BeanOrderHeader;
import com.indocosmo.pos.data.beans.BeanOrderHeader.PosOrderStatus;
import com.indocosmo.pos.data.beans.BeanDiscount;
import com.indocosmo.pos.data.beans.BeanOrderPayment;
import com.indocosmo.pos.data.beans.BeanOrderPaymentHeader;
import com.indocosmo.pos.data.beans.BeanOrderServingTable;
import com.indocosmo.pos.data.beans.BeanPaymentSummary;
import com.indocosmo.pos.data.beans.BeanSaleItem;
import com.indocosmo.pos.data.beans.BeanSaleItemComboContent;
import com.indocosmo.pos.data.beans.BeanSaleItemComboContentSubstitute;
import com.indocosmo.pos.data.beans.BeanServingTable;
import com.indocosmo.pos.data.beans.BeanServingTableExt;
import com.indocosmo.pos.data.beans.BeanServingTableLocation;
import com.indocosmo.pos.data.beans.terminal.device.BeanKitchen;
import com.indocosmo.pos.data.providers.shopdb.PosCounterProvider;
import com.indocosmo.pos.data.providers.shopdb.PosCustomerProvider;
import com.indocosmo.pos.data.providers.shopdb.PosDiscountItemProvider;
import com.indocosmo.pos.data.providers.shopdb.PosInvoiceProvider;
import com.indocosmo.pos.data.providers.shopdb.PosKitchenProvider;
import com.indocosmo.pos.data.providers.shopdb.PosOrderHdrProvider;
import com.indocosmo.pos.data.providers.shopdb.PosOrderQueueProvider;
import com.indocosmo.pos.data.providers.shopdb.PosSaleItemProvider;
import com.indocosmo.pos.data.providers.shopdb.PosServiceTableProvider;
import com.indocosmo.pos.data.providers.shopdb.PosTaxItemProvider;
import com.indocosmo.pos.forms.PosObjectBrowserForm;
import com.indocosmo.pos.forms.PosObjectBrowserForm.ItemSize;
import com.indocosmo.pos.forms.components.objectbrowser.IPosBrowsableItem;
import com.indocosmo.pos.forms.components.orderentry.PosOrderListPanel;
import com.indocosmo.pos.forms.listners.IPosObjectBrowserListner;
import com.indocosmo.pos.forms.listners.IPosPaymentMetodsFormListner;
import com.indocosmo.pos.forms.messageboxes.PosMessageBoxForm.MessageBoxButtonTypes;
import com.indocosmo.pos.forms.messageboxes.PosMessageBoxForm.MessageBoxResults;
import com.indocosmo.pos.forms.search.PosExtSearchForm;
import com.indocosmo.pos.forms.search.PosOrderSerachForm;
import com.indocosmo.pos.forms.search.listener.adapter.PosItemExtSearchFormAdapter;
import com.indocosmo.pos.reports.receipts.PosReceipts;
import com.indocosmo.pos.terminal.devices.PosDeviceManager;
import com.sun.corba.se.spi.legacy.connection.GetEndPointInfoAgainException;

/**
 * @author jojesh
 * 
 */
public final class PosOrderUtil {

	public static String ORDER_ID_FILED_SEPERATOR = "-";
	public static String TMP_DETAIL_ID_PREFIX="TID_";

	public static Color getOrderStatusColor(PosOrderStatus status){
		return status.getColor();
	}
	
	public static String appendToId(String id, int counter) {
		return id + ORDER_ID_FILED_SEPERATOR + String.format("%05d", counter);
	}

	/**
	 * @param text
	 * @return
	 */
	//	public static String appendShopCode(String value) {
	//		
	//		String shopCode = PosEnvSettings.getInstance().getShop().getCode() + ORDER_ID_FILED_SEPERATOR+value;
	//
	//		return null;
	//	}


	public static String getFormatedOrderNumber(int number) {
		return String.format("%06d", number);
	}

	public static String getFormatedOrderQueueNo(BeanOrderHeader orderHdr){
		
		if(orderHdr.getQueueNo()==null)
			return null;

		return getFormatedOrderQueueNo(Integer.valueOf(orderHdr.getQueueNo()), 
				orderHdr.getOrderServiceType(),orderHdr.getServiceTable());
	}
			
	public static String getFormatedOrderQueueNo(int number, PosOrderServiceTypes serviceType,
			BeanServingTable servingTable) {
		
		String prefix=PosEnvSettings.getInstance().getUISetting().getQueueNoPrefix();
	
		String queueNo=PosStringUtil.paddLeft(Integer.toString(number), PosOrderQueueProvider.QUEUE_NO_WIDTH, '0');
		
		if (serviceType==PosOrderServiceTypes.TABLE_SERVICE && serviceType!=null){
			if(servingTable.getLocation().getQueueNoPrefix()!=null && !servingTable.getLocation().getQueueNoPrefix().trim().equals(""))
				prefix=servingTable.getLocation().getQueueNoPrefix();
		}
		
		queueNo=prefix + queueNo;
		return queueNo;
		
	}
	
	public static String getFormatedInvoicePreifx() {
		return PosEnvSettings.getInstance().getUISetting().getInvoiceNoPrefix();
	}
	
	public static String getFormatedInvoiceNumber(int number) {
		String invoiceNo=PosStringUtil.paddLeft(Integer.toString(number), PosInvoiceProvider.INVOICE_NO_WIDTH , '0');
		invoiceNo=PosEnvSettings.getInstance().getUISetting().getInvoiceNoPrefix() + invoiceNo;
		return invoiceNo;
	}
	
	
	public static String getFormatedInvoiceNumberForTally(String closingDate,int number) {
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(PosDateUtil.parse( PosDateUtil.DATE_FORMAT_YYYYMMDD,closingDate));

		String year= String.valueOf(cal.get(Calendar.YEAR));
		year= year.length()>2?year.substring(year.length()-2):year;
		
		int month=cal.get(Calendar.MONTH);
		if(month>=4)
			year=PosNumberUtil.format(year,"00") + "-" + PosNumberUtil.format(String.valueOf(Integer.parseInt(year) +1),"00"); 
		else
			year=PosNumberUtil.format(String.valueOf(Integer.parseInt(year) -1),"00") + "-" + PosNumberUtil.format(year,"00");
		final String invoiceNoPrefix=PosEnvSettings.getInstance().getShop().getCode() + "/" + year + "/";
 
			return invoiceNoPrefix + PosOrderUtil.getFormatedInvoiceNumber(number);
	}
	
	public static String getFormatedOrderId(int ordernumber) {
		String shopCode = PosEnvSettings.getInstance().getShop().getCode();
		String stationCode = PosEnvSettings.getInstance().getStation()
				.getCode();
		return getFormatedOrderId(shopCode, stationCode, ordernumber);
	}

	public static String getFormatedOrderId(String ordernumber) {
		String shopCode = PosEnvSettings.getInstance().getShop().getCode();
		String stationCode = PosEnvSettings.getInstance().getStation()
				.getCode();
		// try{
		// int orderNo=Integer.parseInt(ordernumber);
		// return getFormatedOrderId(shopCode,stationCode,orderNo);
		// }catch(Exception ex){}
		return getFormatedOrderId(shopCode, stationCode, ordernumber);
	}

	public static String getQualifiedOrderID(String orderID){

		String qualiFiedOrderID=orderID;
		String parts[]=orderID.split(ORDER_ID_FILED_SEPERATOR);

		if(parts.length==1)
			qualiFiedOrderID=getFormatedOrderId(PosEnvSettings.getInstance().getShop().getCode(),PosEnvSettings.getInstance().getStation().getCode(),parts[0]);

		else if(parts.length==2)
			qualiFiedOrderID=getFormatedOrderId(PosEnvSettings.getInstance().getShop().getCode(),parts[0],parts[1]);

		return qualiFiedOrderID;

	}

	public static String getFormatedOrderId(String shopCode,
			String stationCode, String ordernumber) {
		return shopCode + ORDER_ID_FILED_SEPERATOR + stationCode
				+ ORDER_ID_FILED_SEPERATOR + ordernumber;
	}

	public static String getFormatedOrderId(String shopCode,
			String stationCode, int ordernumber) {
		return getFormatedOrderId(shopCode, stationCode,
				getFormatedOrderNumber(ordernumber));
	}

	public static String getFormattedOrderNoFromOrderID(String orderID) {
		return (validateOrderID(orderID)) ? orderID
				.split(ORDER_ID_FILED_SEPERATOR)[2] : "";
	}

	public static String getShortOrderID(BeanOrderHeader ordHdr) {
	
		return (validateOrderID(ordHdr.getOrderId())) ? ordHdr.getOrderId()
				.split(ORDER_ID_FILED_SEPERATOR)[1] + ORDER_ID_FILED_SEPERATOR + ordHdr.getOrderId()
				.split(ORDER_ID_FILED_SEPERATOR)[2] : "";
	
	}
	
	//getShortOrderIDFromOrderID
	public static String getFormattedReferenceNo(BeanOrderHeader ordHdr) {
		// return
		// (validateOrderID(orderID))?orderID.split(ORDER_ID_FILED_SEPERATOR)[1]+ORDER_ID_FILED_SEPERATOR+orderID.split(ORDER_ID_FILED_SEPERATOR)[2]:"";
		if (PosEnvSettings.getInstance().getUISetting().useOrderQueueNo()){
		
			if (ordHdr.getQueueNo()!=null)
			{
				if (ordHdr.getQueueNo().trim()!="")
					return   PosOrderUtil.getFormatedOrderQueueNo(ordHdr);
			}
		}
	
		return (validateOrderID(ordHdr.getOrderId())) ? ordHdr.getOrderId()
				.split(ORDER_ID_FILED_SEPERATOR)[1] + ORDER_ID_FILED_SEPERATOR + ordHdr.getOrderId()
				.split(ORDER_ID_FILED_SEPERATOR)[2] : "";
	
	}
	
	public static String getShortOrderIDFromOrderID(String orderID) {
		// return
		// (validateOrderID(orderID))?orderID.split(ORDER_ID_FILED_SEPERATOR)[1]+ORDER_ID_FILED_SEPERATOR+orderID.split(ORDER_ID_FILED_SEPERATOR)[2]:"";
			return (validateOrderID(orderID)) ? orderID
					.split(ORDER_ID_FILED_SEPERATOR)[1] + ORDER_ID_FILED_SEPERATOR + orderID
					.split(ORDER_ID_FILED_SEPERATOR)[2] : "";
		}
			
		


	public static String getOrderNoFromOrderID(String orderID) {
		return (validateOrderID(orderID)) ? orderID
				.split(ORDER_ID_FILED_SEPERATOR)[2] : orderID;
	}

	public static String getStationCodeFromOrderID(String orderID) {
		return (validateOrderID(orderID)) ? orderID
				.split(ORDER_ID_FILED_SEPERATOR)[1] : "";
	}

	public static String getShopCodeFromOrderID(String orderID) {
		return (validateOrderID(orderID)) ? orderID
				.split(ORDER_ID_FILED_SEPERATOR)[0] : "";
	}

	public static String getTerminalCodeFromOrderID(String orderID) {
		return (validateOrderID(orderID)) ? orderID
				.split(ORDER_ID_FILED_SEPERATOR)[1] : "";
	}

	public static boolean validateOrderID(String orderID) {
		return (orderID != null && orderID != "" && orderID
				.split(ORDER_ID_FILED_SEPERATOR).length == 3);
	}

	public static String getNextOrderId() {
		PosCounterProvider pc = new PosCounterProvider();
		final int orderno = pc.getNextOrderBillNumber();
		final String shopCode = PosEnvSettings.getInstance().getShop()
				.getCode();
		final String terminalCode = PosEnvSettings.getInstance().getStation()
				.getCode();
		final String orderNumber = PosOrderUtil.getFormatedOrderId(shopCode,
				terminalCode, orderno);
		return orderNumber;
	}

	/**
	 * @return
	 * @throws Exception
	 */
	public static BeanOrderHeader createNewPosOrderEntryItem()
			throws Exception {
		BeanOrderHeader order = new BeanOrderHeader();
		order.setOrderDetailItems(new ArrayList<BeanOrderDetail>());
		order.setOrderPaymentItems(new ArrayList<BeanOrderPayment>());
		order.setOrderId(getNextOrderId());
		order.setUser(PosEnvSettings.getInstance().getCashierShiftInfo()
				.getCashierInfo());
		order.setOrderDate(PosEnvSettings.getPosEnvSettings().getPosDate());
		order.setOrderTime(PosDateUtil.getDateTime());
		order.setStatus(PosOrderStatus.Open);
		order.setTotalPrintCount(0);
		order.setShiftId(PosEnvSettings.getInstance().getCashierShiftInfo()
				.getCashierInfo().getId());
		order.setLockedStation(PosEnvSettings.getInstance().getStation().getCode());
		order.setOrderLocked(true);
		return order;
	}

	public static BeanOrderDetail createOrderDetailObject(
			BeanOrderHeader oHdr, BeanSaleItem saleItem) {
		return createOrderDetailObject(oHdr.getOrderId(),saleItem,OrderDetailItemType.SALE_ITEM);
	}

	public static BeanOrderDetail createOrderDetailObject(
			String orderId, BeanSaleItem saleItem,OrderDetailItemType itemType) {
		
		BeanOrderDetail detailItem = new BeanOrderDetail();
		detailItem.setOrderId(orderId);
		detailItem.setOrderDate(PosEnvSettings.getPosEnvSettings()
				.getPosDate());
		detailItem.setOrderTime(PosDateUtil.getDateTime());
		detailItem.setCashierId(PosEnvSettings.getInstance()
				.getCashierShiftInfo().getCashierInfo().getId());
		detailItem.setSaleItem(saleItem.clone());
		detailItem.setItemType(itemType);
		detailItem.setId(getTempDetailId());
		detailItem.setPrintingOrder(saleItem.getSubClass().getPrintingOrder());
		return detailItem;
	}

	public static BeanOrderDetail createOrderDetailSubItem(BeanOrderDetail parentDtlItem, BeanSaleItem saleItem,OrderDetailItemType itemType){
		BeanOrderDetail detailItem =createOrderDetailObject(parentDtlItem.getOrderId(),saleItem,itemType);
		detailItem.setParentDtlId(parentDtlItem.getId());
		detailItem.setServiceType(parentDtlItem.getServiceType());
		detailItem.setServedBy(parentDtlItem.getServedBy());
		detailItem.setServingTable(parentDtlItem.getServingTable());
		return detailItem;
	}

	public static  BeanOrderDetail createOrderDetailItemForCombo(BeanOrderDetail comboItem, BeanSaleItemComboContent ccItem, BeanSaleItemComboContentSubstitute ccsItem){
		BeanSaleItem cItem=null;
		BeanOrderDetail dtlItem=PosOrderUtil.createOrderDetailSubItem(comboItem, ccsItem.getSaleItem(),OrderDetailItemType.COMBO_CONTENT_ITEM);
		cItem =  dtlItem.getSaleItem();
		cItem.setFixedPrice(ccsItem.getPriceDifferance());
		PosTaxUtil.calculateTax(cItem);
		dtlItem.setComboContentItem(ccItem);
		return dtlItem;
	}

	public static String getTempDetailId(){
		Long uuid = UUID.randomUUID().getMostSignificantBits();
		String dtlId=TMP_DETAIL_ID_PREFIX+String.valueOf(uuid);
		return dtlId;
	}

	/**
	 * This is the actual bill amount that is paid
	 * It excludes bill discounts and rounding etc
	 * @param order
	 * @return
	 */
	public static double getTotalPaidAmount(BeanOrderHeader order) {

		double amount = 0;
		if (order.getOrderPaymentItems() != null)

			for (BeanOrderPayment payment : order.getOrderPaymentItems()){

				
				if (payment.isRepayment()) 
					continue;
				
				switch(payment.getPaymentMode()){
				case Balance:
				case CashOut:
				case CouponBalance:
				case Repay:	
					amount -= payment.getPaidAmount();
					break;
				case Card:
				case Cash:
				case Cash10:
				case Cash20:
				case Company:
				case Coupon:
				case Online:
					amount += payment.getPaidAmount();
					break;
				case Discount:
					/** 
					 * 
					 * Discount is not stored in payment tables 
					 * 
					 **/
				case SplitAdjust: 
					/**
					 *  
					 * Split adjustment is internal adjustment no need to do anything here
					 * Another record Split adjustment exist in the payments 
					 * 
					 * **/
					break;
				}
			}

		return amount;
	}

	/**
	 * The actual balance amount in the bill that has to be paid
	 * @param order
	 * @return
	 */
	public static double getTotalBalanceOnBill(BeanOrderHeader order){

		double amount=getTotalPaidAmount(order);
		amount-=order.getRoundAdjustmentAmount();
		amount+=getBillDiscount(order);
//		return   PosCurrencyUtil.roundTo(getTotalAmount(order)) - PosCurrencyUtil.roundTo(amount);
		return   PosCurrencyUtil.roundTo( getTotalAmount(order) -amount);
	}

	/**
	 * The actual   amount in the bill  
	 * @param order
	 * @return
	 */
	public static double getTotalAmount(BeanOrderHeader order){
 		
		return order.getTotalAmount() + order.getExtraCharges() + 
				order.getExtraChargeTaxOneAmount() + order.getExtraChargeTaxTwoAmount() +
				order.getExtraChargeTaxThreeAmount() + order.getExtraChargeSCAmount() + order.getExtraChargeGSTAmount();
	}

	/**
	 * @param order
	 * @return
	 */
	public static double getTotalItemAmount(BeanOrderHeader order) {
		double amount = 0;
		if (order.getOrderDetailItems() != null)
			for (BeanOrderDetail item : order.getOrderDetailItems()){
				amount += getOrderItemAmount(item);
				if(item.hasSubItems()){
					amount +=getSubItemTotalAmount(item);
				}
			}
		// amount+=((!item.isVoid())?item.getSaleItem().getGrandTotal():0);
		return amount;
	}
	

	/**
	 * @param item
	 * @return
	 */
	public static double getOrderItemAmount(BeanOrderDetail item){
		double amount=0;

		amount = ((!item.isVoid()) ? PosSaleItemUtil
				.getGrandTotal(item.getSaleItem()) : 0);

		return amount;
	}

	/**
	 * @param parentItem
	 * @return
	 */
	public static double  getSubItemTotalAmount(BeanOrderDetail parentItem){

		double totalAmount=0;

		if(parentItem.isComboContentsSelected())
			totalAmount+=getSubItemTotal(parentItem.getComboSubstitutes().values());

		if(parentItem.isExtraItemsSelected())
			totalAmount+=getSubItemTotal(parentItem.getExtraItemList().values());

		return totalAmount;
	}
	
	/**
	 * @param OrderItem
	 * @return
	 */
	public static int getSubItemTotalCount(BeanOrderDetail OrderItem){
		
		int subtItemCount=0;

		if(OrderItem.hasSubItems()){

			if(OrderItem.isComboContentsSelected())
				subtItemCount+=getSubItemTotalCount(OrderItem.getComboSubstitutes().values());

			
			if(OrderItem.isExtraItemsSelected())
				subtItemCount+=getSubItemTotalCount(OrderItem.getExtraItemList().values());

		}

		return subtItemCount;
	}
	
	/**
	 * @param subList
	 * @return
	 */
	private static int getSubItemTotalCount(Collection<ArrayList<BeanOrderDetail>> subList){
		
		int subtItemCount=0;
		
		for(ArrayList<BeanOrderDetail> itemList:subList){
			
			for(BeanOrderDetail item:itemList){

				subtItemCount+=PosOrderUtil.getItemQuantity(item);
			}
		}
		
		return subtItemCount;
	}

	public static double getSubItemTotal(Collection<ArrayList<BeanOrderDetail>> subList){
		double totalAmount=0;

		for(ArrayList<BeanOrderDetail> itemList:subList){
			totalAmount+=getSubItemTotal(itemList);
		}

		return totalAmount;
	}

	public static double getSubItemTotal(ArrayList<BeanOrderDetail> subList){
		double totalAmount=0;

		for(BeanOrderDetail item:subList){
			totalAmount+=getOrderItemAmount(item);
		}

		return totalAmount;
	}
	
	public static String getComaSeperatedSubItems(Collection<ArrayList<BeanOrderDetail>> subList){
		
		String names="";
		
		for(ArrayList<BeanOrderDetail> itemList:subList){
			
			for(BeanOrderDetail item:itemList){
			
				if(!item.isVoid())
					names+= PosSaleItemUtil.getItemNameToDisplay(item.getSaleItem(), PosEnvSettings.getInstance().getUISetting().getOrderEntryUISettings().getItemListPanelSetting().useAlternativeTitle()) + ", ";
			}
		}
		
		if(names!="")
			names=names.substring(0, names.length()-2);
		
		return names;
		
	}

	public static BeanOrderHeader buildOrderHeaders(
			PosOrderListPanel billPanel) {

		//		// try {
		//		BeanOrderHeader orderHeader = billPanel.getPosOrderEntryItem()
		//				.clone();
		//		orderHeader.setDetailTotal(billPanel.getBillItemTotal());
		//		orderHeader.setTotalTax1(billPanel.getBillTax1());
		//		orderHeader.setTotalTax2(billPanel.getBillTax2());
		//		orderHeader.setTotalTax3(billPanel.getBillTax3());
		//		orderHeader.setTotalGST(billPanel.getBillGST());
		//		orderHeader.setTotalServiceTax(billPanel.getBillServiceTax());
		//		orderHeader.setTotalDetailDiscount(billPanel.getBillDiscount());
		//		// orderHeader.setRoundAdjustmentAmount(0)
		//		orderHeader.setTotalAmount(billPanel.getBillTotal());
		//		return orderHeader;
		//		// } catch (Exception e) {
		//		// PosLog.write("PosOrderUtil", "buildOrderHeaders", e);
		//		// PosFormUtil.showMessageBox(MessageBoxTypes.OkOnly,
		//		// "Failed create payment. Please check log for details");
		//		// }

		return buildOrderHeaders(true,billPanel);
	}
	
	public static BeanOrderHeader updateOrderHdrFromGrid(BeanOrderHeader orderHeader, PosOrderListPanel billPanel){
				
		orderHeader.setDetailItemQuatity(billPanel.getBillItemCount());
		orderHeader.setDetailTotal(billPanel.getBillItemTotal());
		orderHeader.setTotalTax1(billPanel.getBillTax1());
		orderHeader.setTotalTax2(billPanel.getBillTax2());
		orderHeader.setTotalTax3(billPanel.getBillTax3());
		orderHeader.setTotalGST(billPanel.getBillGST());
		orderHeader.setTotalServiceTax(billPanel.getBillServiceTax());
		orderHeader.setTotalDetailDiscount(billPanel.getBillDiscount());
		orderHeader.setTotalAmount(billPanel.getBillTotal());
		return orderHeader;
		
	}

	public static BeanOrderHeader buildOrderHeaders(boolean isCopy,
			PosOrderListPanel billPanel) {
		// try {
		BeanOrderHeader orderHeader = (isCopy)?billPanel.getPosOrderEntryItem().clone():
			billPanel.getPosOrderEntryItem();
		orderHeader.setDetailTotal(billPanel.getBillItemTotal());
		orderHeader.setTotalTax1(billPanel.getBillTax1());
		orderHeader.setTotalTax2(billPanel.getBillTax2());
		orderHeader.setTotalTax3(billPanel.getBillTax3());
		orderHeader.setTotalGST(billPanel.getBillGST());
		orderHeader.setTotalServiceTax(billPanel.getBillServiceTax());
		orderHeader.setTotalDetailDiscount(billPanel.getBillDiscount());
		// orderHeader.setRoundAdjustmentAmount(0)
		orderHeader.setTotalAmount(billPanel.getBillTotal());
		return orderHeader;
		// } catch (Exception e) {
		// PosLog.write("PosOrderUtil", "buildOrderHeaders", e);
		// PosFormUtil.showMessageBox(MessageBoxTypes.OkOnly,
		// "Failed create payment. Please check log for details");
		// }
	}

	public static boolean check4OnlyVoidOrder(BeanOrderHeader order) {
		boolean isVoidOrder = true;
		for (BeanOrderDetail dtlItem : order.getOrderDetailItems()) {
			if (!dtlItem.isVoid()) {
				isVoidOrder = false;
				break;
			}
		}
		return isVoidOrder;
	}

	/***
	 * Finds the table count used for this order
	 * 
	 * @param order
	 * @return
	 */
	public static int getCountOfTables(BeanOrderHeader order) {
		ArrayList<Integer> ids = new ArrayList<Integer>();
		if (order.getOrderDetailItems() != null) {
			for (BeanOrderDetail dtl : order.getOrderDetailItems()) {
				if (dtl.getServingTable() != null
						&& !ids.contains(dtl.getServingTable().getId()))
					ids.add(dtl.getServingTable().getId());
			}
		}
		return ids.size();
	}

	/***
	 * Finds the server by count in this order
	 * 
	 * @param order
	 * @return
	 */
	public static int getCountOfServedBy(BeanOrderHeader order) {
		ArrayList<Integer> ids = new ArrayList<Integer>();
		if (order.getOrderDetailItems() != null) {
			for (BeanOrderDetail dtl : order.getOrderDetailItems()) {
				if (dtl.getServedBy() != null
						&& !ids.contains(dtl.getServedBy().getId()))
					ids.add(dtl.getServedBy().getId());
			}
		}
		return ids.size();
	}

	/**
	 * returns a list of tables used in this order
	 * 
	 * @param order
	 * @return
	 */
	public static ArrayList<BeanServingTable> getAllServingTables(
			BeanOrderHeader order) {
		ArrayList<BeanServingTable> tables = new ArrayList<BeanServingTable>();

		tables.add(order.getServiceTable());
		if(order.getOrderDetailItems()!=null && order.getOrderDetailItems().size()>0){
			for (BeanOrderDetail dtl : order.getOrderDetailItems()) {
				if (!tables.contains(dtl.getServingTable())  ) {
					tables.add(dtl.getServingTable());
				}
			}
		}

		return tables;
	}
	
	/**
	 * returns a list of tables used in this order
	 * 
	 * @param order
	 * @return
	 */
	public static String getAllServingTableName(
			BeanOrderHeader order) {
		 

		ArrayList<BeanServingTable> tables=getAllServingTables(order);
		String tableCodes = "";
		for (BeanServingTable tbl : tables) {
			tableCodes += (tbl.getCode().equals(PosServiceTableProvider.CODE_SYS_TABLE_NA))?"":(tbl.getName() + ",");
		}
		
		if(!tableCodes.equals(""))
			tableCodes = " [" + tableCodes.substring(0, tableCodes.length() - 1) + "]";
		
		return tableCodes;
	}

	

	/**
	 * returns a list of tables used in this order
	 * 
	 * @param order
	 * @return
	 */
	public static String getServingTableName(
			BeanOrderHeader order) {
		
		final String tableName= (order.getServiceTable().getCode().equals(PosServiceTableProvider.CODE_SYS_TABLE_NA))?"":
			"[" + order.getServiceTable().getName() + "]";
		
		return tableName;
	}
	/**
	 * Marks all the items in the order as printed to kitchen
	 **/
	public static void setAsPrintedToKitchen(BeanOrderHeader order) {
		if (order.getOrderDetailItems() != null)
			for (BeanOrderDetail dtl : order.getOrderDetailItems()){
				if(!dtl.isPrintedToKitchen()){
					dtl.setPrintedToKitchen(true);
					dtl.setNewItem(false);
					dtl.setKitchenPrintedStatus(null);
					if(dtl.isComboContentsSelected()){
						for(ArrayList<BeanOrderDetail> subItemList:dtl.getComboSubstitutes().values())
							setAsPrintedToKitchen(subItemList);
					}
					if(dtl.isExtraItemsSelected()){
						for(ArrayList<BeanOrderDetail> subItemList:dtl.getExtraItemList().values())
							setAsPrintedToKitchen(subItemList);
					}
				}
			}
	}

	private static void setAsPrintedToKitchen(ArrayList<BeanOrderDetail> itemList){
		for(BeanOrderDetail item:itemList){

			item.setPrintedToKitchen(true);
			item.setNewItem(false);
			item.setKitchenPrintedStatus(null);
		}
	}

	/**
	 * Check for new item in the order.
	 **/
	public static boolean hasNotPrintedToKitchenItems(BeanOrderHeader order){
		boolean containsNew=false;
		if (order.getOrderDetailItems() != null)
			for (BeanOrderDetail dtl : order.getOrderDetailItems()){
				if(!dtl.isPrintedToKitchen()){
					containsNew=true;
					break;
				}
			}
		return containsNew;

	}
	

	/**
	 * Check for new item in the order.
	 **/
	public static boolean hasPrintableItems(BeanOrderHeader order, boolean allItem){
		boolean printableItems=false;
		
		if (order.getOrderDetailItems() != null)
			for (BeanOrderDetail dtl : order.getOrderDetailItems()){
				
				if(allItem && dtl.getSaleItem().isPrintableToKitchen()  ){
					printableItems=true;
					break;
				}else if(!allItem  &&  !dtl.isPrintedToKitchen() && dtl.getSaleItem().isPrintableToKitchen()){
					printableItems=true;
					break;
				}
			}
		return printableItems;

	}

	/**
	 * Count of new item in the order.
	 **/
	public static int countOfNewItems(BeanOrderHeader order){
		int i=0;
		if (order.getOrderDetailItems() != null)
			for (BeanOrderDetail dtl : order.getOrderDetailItems()){
				if(!dtl.isPrintedToKitchen()){
					i=i+1;
				}
			}
		return i;

	}

	/**
	 * @param order
	 * @return
	 */
	public static BeanPaymentSummary getPaymentSummaryFromHeader(
			BeanOrderHeader order) {

		ArrayList<BeanOrderPayment> paymentDetails = order
				.getOrderPaymentItems();

		BeanPaymentSummary summary = new BeanPaymentSummary();

		for (BeanOrderPayment payment : paymentDetails) {
			switch (payment.getPaymentMode()) {
			case Cash:
				if (payment.getPaidAmount() > 0 && !payment.isRepayment()) {
					summary.setCashTotal(payment.getPaidAmount());
				}
				break;
			case Card:
				if (payment.getPaidAmount() > 0 && !payment.isRepayment())
					summary.setCardTotal(payment.getPaidAmount());

				break;
			case CashOut:
				// if(payment.getPaidAmount() > 0&&!payment.isRepayment())

				break;
			case Company:
				if (payment.getPaidAmount() > 0 && !payment.isRepayment())
					summary.setCompanyTotal(payment.getPaidAmount());

				break;
			case Coupon:
				if (payment.getVoucherCount() > 0 && !payment.isRepayment()) {
					summary.setVoucherTotal(summary.getVoucherTotal()
							+ payment.getVoucherValue()
							* payment.getVoucherCount());
				}
				break;
			default:
				break;

			}
		}

		summary.setBillDiscount(order.getBillDiscountAmount());
		summary.setTotalBalance(order.getChangeAmount());
		summary.setRoundAdjustmentAmount(order.getRoundAdjustmentAmount());

		return summary;
	}

	// < ------------------------ New calculation method added for split share ------------------


	/**
	 * @param dtlItem
	 * @return
	 */
	public static String getStatusString(BeanOrderDetail dtlItem){

		String statusString="";

		if(dtlItem.getKitchenPrintedStatus()!=null)
			statusString=dtlItem.getKitchenPrintedStatus();

		else{

			if(dtlItem.isPrintedToKitchen()){
				statusString= "P";
			}else if(dtlItem.isNewItem()&&!dtlItem.isPrintedToKitchen()){
				statusString="N";
			}else
				statusString="E";
		}

		if(dtlItem.isVoid())
			statusString="D";

		return statusString;
	}

	/**
	 * @param odItem
	 * @return
	 */
	public static double getTotalItemPrice(BeanOrderDetail odItem) {

		final double totalItemPrice=PosSaleItemUtil.getTotalItemPrice(odItem.getSaleItem())*odItem.getItemSplitShare();
		return totalItemPrice;
	}

	/**
	 * @param odItem
	 * @return
	 */
	public static double getTotalTaxableAmount(BeanOrderDetail odItem) {

		final double totalTaxableAmount=odItem.getSaleItem().getTax().getTaxAmount().getTaxableAmount()*odItem.getItemSplitShare();//  PosSaleItemUtil.getTotalTaxableAmount(odItem.getSaleItem())*odItem.getItemSplitShare();
		return totalTaxableAmount;
	}


	/**
	 * @param odItem
	 * @return
	 */
	public static double getTotalDiscountAmount(BeanOrderDetail odItem) {

		return  PosSaleItemUtil.getTotalDiscountAmount(odItem.getSaleItem())*odItem.getItemSplitShare();
	}

	/**
	 * @param odItem
	 * @return
	 */
	public static double getGrandTotal(BeanOrderDetail odItem) {

		return  PosSaleItemUtil.getGrandTotal(odItem.getSaleItem())*odItem.getItemSplitShare();
	}

	/**
	 * @param odItem
	 * @return
	 */
	public static double getItemQuantity(BeanOrderDetail odItem) {

		return  (odItem.isVoid()) ? 0 : PosSaleItemUtil.getItemQuantity(odItem.getSaleItem())*odItem.getItemSplitShare();
	}
	
	/**
	 * @param odItem
	 * @return
	 */
	public static double getTotalTaxAmount(BeanOrderDetail odItem) {
	
		return getTotalT1Amount(odItem) +getTotalT2Amount(odItem) + getTotalT3Amount(odItem) + getTotalGSTAmount(odItem) + getTotalServiceTaxAmount(odItem);
	}
	/**
 

	/**
	 * @param odItem
	 * @return
	 */
	public static double getTotalT1Amount(BeanOrderDetail odItem) {

		return PosSaleItemUtil.getTotalT1Amount(odItem.getSaleItem())*odItem.getItemSplitShare();
	}

	/**
	 * @param odItem
	 * @return
	 */
	public static double getTotalT2Amount(BeanOrderDetail odItem) {

		return PosSaleItemUtil.getTotalT2Amount(odItem.getSaleItem())*odItem.getItemSplitShare();
	}

	/**
	 * @param odItem
	 * @return
	 */
	public static double getTotalT3Amount(BeanOrderDetail odItem) {

		return PosSaleItemUtil.getTotalT3Amount(odItem.getSaleItem())*odItem.getItemSplitShare();
	}

	/**
	 * @param odItem
	 * @return
	 */
	public static double getTotalGSTAmount(BeanOrderDetail odItem) {

		return PosSaleItemUtil.getTotalGSTAmount(odItem.getSaleItem())*odItem.getItemSplitShare();
	}

	/**
	 * @param odItem
	 * @return
	 */
	public static double getTotalServiceTaxAmount(BeanOrderDetail odItem) {

		return PosSaleItemUtil.getTotalServiceTaxAmount(odItem.getSaleItem())*odItem.getItemSplitShare();
	}

	/**
	 * @param dtl
	 * @return
	 */
	public static double getItemFixedPrice(BeanOrderDetail odItem) {

		return PosSaleItemUtil.getItemFixedPrice(odItem.getSaleItem())*odItem.getItemSplitShare();
	}

	/**
	 * @param orderItem
	 */
	public static void setServiceCharges(BeanOrderDetail orderItem){
		
		if(orderItem.getServingTable()!=null){
			
			BeanServingTableLocation servingTableLoc=orderItem.getServingTable().getLocation();
			if(servingTableLoc.isApplyServiceCharges() && servingTableLoc.getBasedOn()==1){

				double curFixedPrice=orderItem.getSaleItem().getFixedPrice();
				if(servingTableLoc.isPercentage())
					curFixedPrice=PosCurrencyUtil.roundTo(curFixedPrice+(curFixedPrice*servingTableLoc.getAmount()/100));
				else
					curFixedPrice=curFixedPrice+servingTableLoc.getAmount();
				
				orderItem.getSaleItem().setFixedPrice(curFixedPrice);
				PosTaxUtil.calculateTax(orderItem.getSaleItem());

			}
		}
	}
	
	/**
	 * @param orderItem
	 * @param table
	 */
	public static void setServiceTable(BeanOrderDetail orderItem, BeanServingTable table){
		
		if(orderItem.getServingTable()!=null){
			
			BeanServingTableLocation servingTableLoc=orderItem.getServingTable().getLocation();

			if(servingTableLoc.isApplyServiceCharges() && servingTableLoc.getBasedOn()==1){

				double curFixedPrice=orderItem.getSaleItem().getFixedPrice();
				double actualFixedPrice=0;

				if(servingTableLoc.isPercentage())
					actualFixedPrice=PosCurrencyUtil.roundTo(curFixedPrice/(1+servingTableLoc.getAmount()/100));
				else
					actualFixedPrice=curFixedPrice-servingTableLoc.getAmount();

				orderItem.getSaleItem().setFixedPrice(actualFixedPrice);

			}

		}
		
		orderItem.setServingTable(table);
		
		setServiceCharges(orderItem);
		
	}
	/**
	 * @param mOrderServiceTables
	 * @param orderTables
	 */
	public static Map<String, BeanOrderServingTable> updateOrderTables(
			Map<String, BeanOrderServingTable> currentTableList,
			Map<String, BeanOrderServingTable> newTableList) {

		if(newTableList!=null){ 

			if(currentTableList==null){

				currentTableList=newTableList;
			}else{

				for(String code:newTableList.keySet()){

					if(currentTableList.containsKey(code)){

						currentTableList.get(code).setSeatCount(newTableList.get(code).getSeatCount());
						currentTableList.get(code).setVoid(newTableList.get(code).isVoid());

					}else{

						currentTableList.put(code, newTableList.get(code));
					}
				}
			}
		}

		return currentTableList;
	}

	/**
	 * @param selectedServiceTableCode
	 * @param mOrderServiceTables
	 * @return
	 */
	public static BeanServingTable getSelectedServiceTable(
			String selectedServiceTableCode,
			Map<String, BeanOrderServingTable> mOrderServiceTables) {

		BeanOrderServingTable table=null;

		if(mOrderServiceTables!=null && mOrderServiceTables.size()>=1){

			if(mOrderServiceTables.containsKey(selectedServiceTableCode))
				
				table=mOrderServiceTables.get(selectedServiceTableCode);
			else
				table=mOrderServiceTables.values().iterator().next();

			if(table!=null){


				for(BeanOrderServingTable tbl:mOrderServiceTables.values())
					tbl.setSelected(false);

				table.setSelected(true);
			}

		}

		return table;
	}

	/**
	 * @param orderHeader
	 * @param table
	 * @return
	 */
	public static int getItemCountInTable(BeanOrderHeader orderHeader,
			BeanServingTableExt table) {

		int itemCount=0;
		
		if(orderHeader!=null && orderHeader.getOrderDetailItems()!=null && orderHeader.getOrderDetailItems().size()>0){
			
			for(BeanOrderDetail item:orderHeader.getOrderDetailItems()){
				
				if(!item.isVoid()){
					
					if(item.getServingTable()!=null && item.getServingTable().getCode().equals(table.getCode())){
						
						itemCount++;
					}
					
				}
			}
		}
		
		return itemCount;
	}

	/**
	 * @param parent
	 * @param where
	 * @param listener
	 */
	public void showOrderSelecionForm( RootPaneContainer parent, String where,PosItemExtSearchFormAdapter listener){
		
		if(where==null){
			String posDate = PosEnvSettings.getInstance().getPosDate();
			if(posDate!=null){
				
				where="pos_date between '"+posDate+"' and '"+posDate+"'";
			}
		}
		
		try {
			
			ArrayList<BeanOrderHeader> orderHeaderList=new PosOrderHdrProvider().getOrderHeaders(where);
			
			if(orderHeaderList!=null){
				
				
			}
			
			PosExtSearchForm serachForm = new PosOrderSerachForm(orderHeaderList);
			serachForm.setListner(listener);
			PosFormUtil.showLightBoxModal(parent,serachForm);
			
		} catch (Exception e) {
			
			PosLog.write(this, "showOrderListSearch", e);
			PosFormUtil.showErrorMessageBox(parent,
					"Failed get order list. Please check the log for details");
		}
	}
	
	/**
	 * @param orderID
	 * @return
	 * @throws Exception 
	 */
	public static boolean getLock(String orderID) throws Exception{
		
		return getLock(orderID,PosEnvSettings.getInstance().getStation().getCode());
	}
	
	/**
	 * @param orderID
	 * @param shopCode
	 * @return
	 * @throws SQLException 
	 */
	public static boolean getLock(String orderID, String shopCode) throws Exception{
		
		PosOrderHdrProvider pvdr=new PosOrderHdrProvider();
		return pvdr.getLock(orderID,shopCode);
	}

	/**
	 * @param orderId
	 * @throws SQLException 
	 */
	public static boolean releaseOrder(String orderId) {
	
		boolean isSuccess=false;
		try {
			
			PosOrderHdrProvider pvdr=new PosOrderHdrProvider();
			pvdr.finishOrder(orderId);
			isSuccess=true;
			
		} catch (Exception e) {
			
			PosLog.write("PosOrderUtil", "releaseOrder", e);
		}
		
		return isSuccess;
	}

/**
	 * @return item name and sub items  
	 */
	public static String getDescriptiveItemName( BeanOrderDetail orderDetailItem){

		String itemName="<html><body>"+orderDetailItem.getSaleItem().getName();
		final int subItemCount=PosOrderUtil.getSubItemTotalCount(orderDetailItem);
		String modifire="";
		String choices="";
		
		if(subItemCount>0){

			itemName+=" [" + subItemCount + " More items.";

			final double subItemTotal=PosOrderUtil.getSubItemTotalAmount(orderDetailItem);
			//			if(subItemTotal>0){

			itemName+=" Sub total: " + PosCurrencyUtil.format(subItemTotal) ; 
			//			}

			itemName+="]";
		}
		
		final BeanSaleItem beanSaleItem=orderDetailItem.getSaleItem();

		if(beanSaleItem.hasSelectedAttributes()){
			
			for(int i=0;i<5;i++){

				if(beanSaleItem.getAttribSelectedOption(i)!=null && 
						beanSaleItem.getAttribSelectedOption(i).trim().length()!=0 && 
						!beanSaleItem.getAttribSelectedOption(i).trim().equalsIgnoreCase(PosSaleItemProvider.DEFAULT_ATTRIBUTE_OPTION)){
					modifire+="[ "+beanSaleItem.getAttributeName(i)+" : "+beanSaleItem.getAttribSelectedOption(i)+" ], ";
				}
			}
			
			if(!modifire.isEmpty())	
				modifire=modifire.substring(0,modifire.length()-2);
		}

		if(!modifire.isEmpty() && PosEnvSettings.getInstance().getUISetting().getOrderEntryUISettings().getSplitUISettings().isItemMoidifiersVisible())	
			itemName+="<br><font size=\"4\" color=\"red\">Modifiers " +modifire+"</font></br>";
		
		if(orderDetailItem.hasSubItems()){
			
			if(orderDetailItem.isExtraItemsSelected() && PosEnvSettings.getInstance().getUISetting().getOrderEntryUISettings().getSplitUISettings().isItemChoicesVisible()){
				
				choices=PosOrderUtil.getComaSeperatedSubItems(orderDetailItem.getExtraItemList().values());
				
				if(!choices.isEmpty())
					itemName+="<br><font size=\"4\" color=\"blue\">Choices [ " +choices+" ]</font></br>";
			}
		}

		return itemName+"</body></html>";
	}

	/**
	 * @param order
	 * @return
	 */
	public static ArrayList<BeanKitchen> getKitchens(
			BeanOrderHeader order) {
		
		return getKitchens(order,false);
	}
	
	/**
	 * returns a list of tables used in this order
	 * 
	 * @param order
	 * @return
	 */
	public static ArrayList<BeanKitchen> getKitchens(
			BeanOrderHeader order, boolean filter) {
		ArrayList<BeanKitchen> kitchenList = new ArrayList<BeanKitchen>();

		ArrayList<Integer> kitchenIdList = new ArrayList<Integer>();
		if (order.getOrderDetailItems() != null) {
			for (BeanOrderDetail dtl : order.getOrderDetailItems()) {
				if ( !kitchenIdList.contains(dtl.getSaleItem().getKitchenId()) ){
				
					if(!filter || !dtl.isPrintedToKitchen()){
						
						kitchenIdList.add(dtl.getSaleItem().getKitchenId());
						BeanKitchen kitchen=PosKitchenProvider.getInstance().getKitchenById(dtl.getSaleItem().getKitchenId());
						if(kitchen!=null)
							kitchenList.add(kitchen);
					}
				}
			}
		}
	 	 
		return kitchenList;
	}
	
	/**
	 * @param parentForm 
	 * @param oh
	 * @return
	 */
	public static boolean getLock(Object parentForm, BeanOrderHeader oh){
		
		boolean hasLockGained=false;
		try {
			
			if(!PosOrderUtil.getLock(oh.getOrderId())){
				
				final String msg="Order is opened at station "+ oh.getLockedStation() +". Ask the waiter to close the order ";
				
//				final  String msg="Order is opened at station "+ oh.getLockedStation() +". Do you want to release that order and  open it now?";
				MessageBoxResults msgResult= PosFormUtil.showQuestionMessageBox(parentForm, MessageBoxButtonTypes.CancelOnly, msg, null);
				
				if (msgResult==MessageBoxResults.Yes) {
					PosOrderUtil.releaseOrder(oh.getOrderId());
					hasLockGained=PosOrderUtil.getLock(oh.getOrderId());
				}
				
			}else
				hasLockGained=true;
			
			
		} catch (Exception e) {
		
			PosLog.write(parentForm, "getLock("+oh.getOrderId()+")", e);
			PosFormUtil.showErrorMessageBox(parentForm,"Failed to open the order. Please contact administrator.");
		}
		
		oh.setOrderLocked(hasLockGained);
		return hasLockGained;
	}
	
	
	/*
	 * 
	 */
	
	public static void printRefundReceipt(RootPaneContainer parent, BeanOrderHeader orderHdr, boolean useAltLang) {
		
	 	
		try {

//			BeanOrderHeader refundHdr=new BeanOrderHeader();
			ArrayList<BeanOrderDetail> ordDetItems=new ArrayList<BeanOrderDetail>();
//			ArrayList<BeanOrderPayment> ordPaymentItems=new ArrayList<BeanOrderPayment>();
			double totalTax1=0;
			double totalTax2=0;
			double totalTax3=0;
			double totalGST=0;
			double totalSC=0;
			double totalRoundAmt=0;
			double totalAmount=0;
			double totalAmountPaid=0;
			 
 

			for(BeanOrderDetail ordDet: orderHdr.getOrderDetailItems()){
			
					if(ordDet.isRefunded()){
			
						ordDetItems.add(ordDet);
					}
					if(ordDet.hasSubItems()){
						/**If has combo contents print them**/
						if(ordDet.isComboContentsSelected())

							for(ArrayList<BeanOrderDetail> subItemList:ordDet.getComboSubstitutes().values())

								for(BeanOrderDetail item:subItemList)

									if(item.isRefunded()){

										ordDetItems.add(item);
									}
								
							
						

						/**If has extras print them**/
						if(ordDet.isExtraItemsSelected())

							for(ArrayList<BeanOrderDetail> subItemList:ordDet.getExtraItemList().values())

								for(BeanOrderDetail item:subItemList)

									if(item.isRefunded()){

										ordDetItems.add(item);
									}
					}
				}
			
 
			
			for(BeanOrderPaymentHeader ordPaymentHdr:orderHdr.getOrderPaymentHeaders()){
				
				if(ordPaymentHdr.isRefund()) {
					totalTax1 +=ordPaymentHdr.getTotalTax1();
					totalTax2 +=ordPaymentHdr.getTotalTax2();
					totalTax3 += ordPaymentHdr.getTotalTax3();
					totalGST +=ordPaymentHdr.getTotalGST();
					totalSC +=ordPaymentHdr.getTotalServiceTax();
						
					  totalRoundAmt+= ordPaymentHdr.getRoundAdjustmentAmount();
					  totalAmount += ordPaymentHdr.getTotalAmount();
					  totalAmountPaid += ordPaymentHdr.getTotalAmountPaid();
					  
			 
				}

			}
			orderHdr.setTotalTax1(totalTax1);
			orderHdr.setTotalTax2(totalTax2);
			orderHdr.setTotalTax3(totalTax3);
			orderHdr.setTotalGST(totalGST);
			orderHdr.setTotalServiceTax(totalSC);
			orderHdr.setTotalAmount(totalAmount);
			orderHdr.setTotalRefundAmount(totalAmountPaid);
			orderHdr.setRoundAdjustmentAmount(totalRoundAmt);
			 
			orderHdr.setOrderDetailItems(ordDetItems);
			PosReceipts.printRefundReceipt(orderHdr, true, useAltLang);
		} catch (Exception err) {
			PosLog.write(null, "printrefundReceipt", err);
			PosFormUtil.showErrorMessageBox(null, "Error in printing!!");
		}
		 
	}
	
	/*
	 * 
	 */
	public static boolean voidOrder(final RootPaneContainer parent, final BeanOrderHeader selectedOrderItem){
		
		boolean result=false;
		
		if(!(selectedOrderItem!=null && selectedOrderItem.getStatus()==PosOrderStatus.Open && 
				(selectedOrderItem.getOrderPaymentItems()==null || 
				selectedOrderItem.getOrderPaymentItems().size()<=0))){
			
			PosFormUtil.showErrorMessageBox(parent,"Can not void this bill.");
			
			
		}else if(PosEnvSettings.getInstance().getUISetting().isBilledItemEditAuthenticationRequired() 
				&& selectedOrderItem.getTotalPrintCount()>0)
		{
			if(!PosEnvSettings.getInstance().getUISetting().getOrderEntryUISettings().isConfirmVoid() ||
			  PosFormUtil.showQuestionMessageBox(parent,MessageBoxButtonTypes.YesNo, 
					"Bill has been generated for this order. Do you want to void this bill?", null)==
				MessageBoxResults.Yes){
				
				if (PosAccessPermissionsUtil.validateAccess(parent, true, "printed_bill_edit")!=null){
					result= deleteOrder(parent, selectedOrderItem);
				}
			}
	   }else if(PosEnvSettings.getInstance().getUISetting().isParkedItemEditAuthenticationRequired()) {
			if(!PosEnvSettings.getInstance().getUISetting().getOrderEntryUISettings().isConfirmVoid() ||
			  PosFormUtil.showQuestionMessageBox(parent,MessageBoxButtonTypes.YesNo, 
					"Do you want to void this bill?", null)==
				MessageBoxResults.Yes){
				
				if (PosAccessPermissionsUtil.validateAccess(parent, true, "parked_order_edit")!=null){
					result= deleteOrder(parent, selectedOrderItem);
				}
			}
	   }
		else{
			
			if(!PosEnvSettings.getInstance().getUISetting().getOrderEntryUISettings().isConfirmVoid())
				result= deleteOrder(parent, selectedOrderItem);
			else if(PosFormUtil.showQuestionMessageBox(parent,MessageBoxButtonTypes.YesNo, 
					"Do you want to void this bill?", null)==MessageBoxResults.Yes){
				
				result= deleteOrder(parent, selectedOrderItem);
			}	 
		}
		return result;
	}
	/**
	 * 
	 */
	private static  boolean deleteOrder( RootPaneContainer parent,BeanOrderHeader selectedOrderItem ) {
			
		boolean result=false;
		
		try {
			
			PosOrderHdrProvider pvdr=new PosOrderHdrProvider();
			selectedOrderItem.setStatus(PosOrderStatus.Void);
			selectedOrderItem.setClosingDate(PosEnvSettings.getInstance().getPosDate());
			selectedOrderItem.setClosingTime(PosDateUtil.getDateTime());
			selectedOrderItem.setVoidBy(PosEnvSettings.getInstance().getCashierShiftInfo()
					.getCashierInfo());
			selectedOrderItem.setClosedBy(PosEnvSettings.getInstance().getCashierShiftInfo()
					.getCashierInfo());
			selectedOrderItem.setVoidAt(PosDateUtil.getDateTime());
			pvdr.saveOrder(selectedOrderItem,true,true,false,false);
			
			if(PosDeviceManager.getInstance().hasKichenPrinter() && PosOrderUtil.hasPrintableItems(selectedOrderItem, true)){
			
				if(PosFormUtil.showQuestionMessageBox(parent, MessageBoxButtonTypes.YesNo, "Do you want to print to kitchen?", null)==MessageBoxResults.Yes){

					PosReceipts.printReceiptToKitchen(selectedOrderItem, true);
				}

			}
			result=true;

		} catch (Exception e) {
			PosLog.write(parent.getRootPane().getName(), "void action", e);
			PosFormUtil.showErrorMessageBox(null,"Unable to void the order. Please contact administrator");

		}
		return result;
	}
	
	/**
	 * @param orderHdr
	 * @return
	 */
	public static String getOrderDisplayText(BeanOrderHeader orderHdr){
		
		final String displayText=(
				
				(orderHdr.getAliasText()!=null && orderHdr.getAliasText().trim().length()>0)?orderHdr.getAliasText()
						:((orderHdr.getCustomer()!=null && !orderHdr.getCustomer().getCode().equals(PosCustomerProvider.DEF_CUST_CODE))?orderHdr.getCustomer().getName()
								:((orderHdr.getStatus()==PosOrderStatus.Closed || orderHdr.getStatus()==PosOrderStatus.Refunded)?orderHdr.getInvoiceNo()
										:orderHdr.getQueueNo()
										)
								)
				);	
		
		return displayText;
		
	}

/*
	 * 
	 */
	public static String getTimeCriteria(String timeColumnName,String timeFrom,String timeTo) 
	{
	
		String criteria="";
		
		try{
			
			
			final java.util.Date startTime =PosDateUtil.parseToDateTime( timeFrom, PosDateUtil.DATE_FORMAT_NOW_24);
			final java.util.Date endTime =PosDateUtil.parseToDateTime( timeTo, PosDateUtil.DATE_FORMAT_NOW_24);
			
			Calendar cal = Calendar.getInstance();
			cal.setTime(startTime);

			final int hourFrom=cal.get(Calendar.HOUR_OF_DAY);
			final int minuteFrom=cal.get(Calendar.MINUTE);
			
			cal.setTime(endTime);
			
			final int hourTo=cal.get(Calendar.HOUR_OF_DAY);
			final int minuteTo=cal.get(Calendar.MINUTE);
			String timeFromCriteria="";
			String timeToCriteria="";
			
			switch (PosDBUtil.getInstance().getDatabaseType()) {
			case SQLITE:
				timeFromCriteria=" ( CAST(STRFTIME('%H', "+ timeColumnName + ") AS  integer) > " + String.valueOf(hourFrom) + " " 
						+ "OR (" +
						" CAST(STRFTIME('%H', "+ timeColumnName + ") AS  integer) = " + String.valueOf(hourFrom) + " AND " +
						" CAST(STRFTIME('%M', "+ timeColumnName + ") AS  integer) >= " + String.valueOf(minuteFrom) + " "
						+ ")) ";
				
				 timeToCriteria=" (CAST(STRFTIME('%H', "+ timeColumnName + ") AS  integer) < " + String.valueOf(hourTo) + " " 
						+ "OR (" +
						" CAST(STRFTIME('%H', "+ timeColumnName + ") AS  integer) = " + String.valueOf(hourTo) + " AND " +
						" CAST(STRFTIME('%M', "+ timeColumnName + ") AS  integer) <= " + String.valueOf(minuteTo) + " "
						+ ")) ";
				break;
			case MARIADB:
			case MYSQL:
				timeFromCriteria=" (EXTRACT(HOUR FROM "+ timeColumnName + ") > " + String.valueOf(hourFrom) + " " 
						+ "OR (" +
						" EXTRACT(HOUR FROM "+ timeColumnName + ") = " + String.valueOf(hourFrom) + " AND " +
						" EXTRACT(MINUTE FROM "+ timeColumnName + ") >= " + String.valueOf(minuteFrom) + " "
						+ ")) ";
				
				timeToCriteria=" (EXTRACT(HOUR FROM "+ timeColumnName + ") < " + String.valueOf(hourTo) + " " 
						+ "OR (" +
						" EXTRACT(HOUR FROM "+ timeColumnName + ") = " + String.valueOf(hourTo) + " AND " +
						" EXTRACT(MINUTE FROM "+ timeColumnName + ") <= " + String.valueOf(minuteTo) + " "
						+ ")) ";
			}
			 
			
			if (hourTo >= hourFrom)
				criteria="(" + timeFromCriteria + " AND " + timeToCriteria + ")";
			else
				criteria="(" + timeFromCriteria + " OR " + timeToCriteria + ")";

		}catch(Exception e){
			PosLog.write(null, "time criteria", e);
		}
		
		return criteria;
	}
	
	/*
	 * returns total prebill discount if pre discount assigned 
	 * otherwise returns total applied discount 
	 */
	 
	public static double getBillDiscount(BeanOrderHeader order){
		
		double totalDiscAmt=order.getBillDiscountAmount();
		
		if ( !(order.getStatus()==PosOrderStatus.Closed || order.getStatus()==PosOrderStatus.Refunded )
				&& order.getPreBillDiscount()!=null  
				&& order.getPreBillDiscount()!=new PosDiscountItemProvider().getNoneDiscount()
				){
			
			totalDiscAmt = ((order.getPreBillDiscount().isPercentage()) ? 
					(order.getTotalAmount()+order.getExtraCharges()+ getExtraChargeTotalTaxAmount(order))
					* order.getPreBillDiscount().getPrice() / 100 : 
					order.getPreBillDiscount().getPrice());
		}
		return PosCurrencyUtil.roundTo(totalDiscAmt);
	}
	
	/**
	 * @param orderHdr header 
	 * @return double 
	 */
	public static double getExtraChargeTotalTaxAmount(BeanOrderHeader orderHdr){
		
		final double totalExtraChargeTaxAmt=orderHdr.getExtraChargeTaxOneAmount()+ orderHdr.getExtraChargeTaxTwoAmount()+ 
				orderHdr.getExtraChargeTaxThreeAmount() + orderHdr.getExtraChargeSCAmount()+orderHdr.getExtraChargeGSTAmount();
		return totalExtraChargeTaxAmt;
	}

	/*
	 * returns total prebill discount if pre discount assigned 
	 * otherwise returns total applied discount 
	 */
	 
	public static double getDiscountForPaymentReceipt(BeanOrderHeader order, double subtotal){
		
		double totalDiscAmt=0;
		
		if (!(order.getStatus()==PosOrderStatus.Closed || order.getStatus()==PosOrderStatus.Refunded) && order.getPreBillDiscount()!=null  
				&& !order.getPreBillDiscount().equals(new PosDiscountItemProvider().getNoneDiscount())
				){
			if(order.getPreBillDiscount().isPercentage())
				totalDiscAmt=subtotal* order.getPreBillDiscount().getPrice() / 100;
			else{
				final double amtPayPercentage=(subtotal/PosOrderUtil.getTotalAmount(order))*100;
				totalDiscAmt= order.getPreBillDiscount().getPrice()* amtPayPercentage / 100;
			}
		}else
			totalDiscAmt=subtotal*order.getBillDiscountPercentage()/ 100;
		
		return totalDiscAmt;
	}
	
	/*
	 * returns due amt without prebill discount 
	 */
	public static double getBillDueAmount(BeanOrderHeader order){
 
		final double paidAmt= order.getTotalAmountPaid() -
				(order.getChangeAmount()+order.getCashOut())-
				order.getRoundAdjustmentAmount()  ;
		double dueAmount= getTotalAmount(order)-paidAmt-getBillDiscount(order);
		//dueAmount= PosCurrencyUtil.nRound(dueAmount)==0?0 :  dueAmount;
		return dueAmount;
	}
	 
	
	/*
	 * 
	 */
	public static boolean isServiceEnabled(String[]services, int currentService){
		
		boolean result=false;
		
		if(services!=null)
			for(String service : services){
				if (PosNumberUtil.parseIntegerSafely(service)==currentService){
					result=true;
					break;
				}
			}
		
		return result;
	}
	
	/*
	 *
	 */

	 public static String getDiscountName(BeanOrderHeader order){
		
		 String result="Discount :";
		 if(order.getBillDiscounts()!=null && order.getBillDiscounts().size()==1){
		 
			   result= getDiscountName(order.getBillDiscounts().get(0));
		 }else if(order.getPreBillDiscount()!=null)
		   result= getDiscountName(order.getPreBillDiscount());
		return result;
	 }
	 
	 /*
	 *
	 */

	 private static String getDiscountName( BeanDiscount discount){
		
		 String result="Discount :";
		 
		   result= discount.getName() ;
		   result+=discount.isPercentage()?
				   ("(" + String.valueOf(discount.getPrice())+ "%)") : "";
			result+= " :";
		 
		return result;
	 }
	 
	/*
	 * 
	 */
	 public static boolean isDeliveryService(BeanOrderHeader order){
		 
		 boolean result=false;
		 result=(order.getOrderServiceType()==PosOrderServiceTypes.HOME_DELIVERY || order.getOrderServiceType()==PosOrderServiceTypes.SALES_ORDER);
		 return result;
 	 }
			
	 
	 /**
		 * @param item
		 * @param includeVariants
		 * @return
		 */
		public static double getTotalDiscountAmountForExclusiveRate(BeanOrderDetail orderDtl,double exclusiveRate ) {
			
			BeanSaleItem item=orderDtl.getSaleItem();
			if (item.getDiscount() == null || item.getDiscount().getCode().equals((new PosDiscountItemProvider()).NONE_DISCOUNT_CODE))
				return 0;
			final double qty=PosSaleItemUtil.getActualDiscountQuantity(item);
			double amount=qty*getDiscountAmountForExclusiveRate(item,exclusiveRate);
			
//			double amount= getDiscountAmountForExclusiveRate(item,exclusiveRate);
			amount+= item.getDiscount().getVariants()*item.getQuantity();
			
			
			return   (amount>qty*exclusiveRate?qty*exclusiveRate:amount) *orderDtl.getItemSplitShare();
		}
		/**
		 * Return the discount amount
		 * @param item
		 * @return
		 */
		private static double getDiscountAmountForExclusiveRate(BeanSaleItem item,double exclusiveRate) {
			
			if (item.getDiscount() == null)
				return 0;	
			
			final BeanDiscount discount =item.getDiscount();
			double discAmount =0;
			if(discount.isPercentage())
				discAmount=  exclusiveRate* discount.getPrice() / 100 ;
			else if (item.getFixedPrice()!=0){
				final double discPer= discount.getPrice()*100/	item.getFixedPrice();
				discAmount=  exclusiveRate* discPer / 100 ;
			}
			return discAmount; 
		}
		
		/**
		 * @param  
		 */
		public static void setExtraChargeTaxSummary(BeanOrderHeader mOrder, HashMap<Integer, BeanReceiptTaxSummary> itemTaxList){

	 
			if(mOrder.getExtraChargeTaxCode()==null || mOrder.getExtraChargeTaxCode().equals(PosTaxItemProvider.NO_TAXCODE) 
					|| mOrder.getExtraChargeTaxCode().equals(""))
				return;
			
			if(mOrder.getExtraCharges()<=0)
				return;

			
			BeanReceiptTaxSummary taxSummary=null;

			
			if(itemTaxList.containsKey(mOrder.getExtraChargeTaxId()))

				taxSummary=itemTaxList.get(mOrder.getExtraChargeTaxId());

			else{

				taxSummary=new BeanReceiptTaxSummary();
				itemTaxList.put(mOrder.getExtraChargeTaxId(), taxSummary);
			}
			
			double tax1Amount=taxSummary.getTax1Amount();
			double tax2Amount=taxSummary.getTax2Amount();
			double tax3Amount=taxSummary.getTax3Amount();
			double taxSCAmount=taxSummary.getTaxSCAmount();
			double taxGSTAmount=taxSummary.getTaxGSTAmount();
			double totalTaxAmo=taxSummary.getTaxAmount();
			double taxableAmo=taxSummary.getTaxableAmount();
			
			tax1Amount+=mOrder.getExtraChargeTaxOneAmount(); 
			taxSummary.setTax1Amount(tax1Amount);
			tax2Amount+=mOrder.getExtraChargeTaxTwoAmount(); 
			taxSummary.setTax2Amount(tax2Amount);
			tax3Amount+=mOrder.getExtraChargeTaxThreeAmount();
			taxSummary.setTax3Amount(tax3Amount);
			taxSCAmount+=mOrder.getExtraChargeSCAmount();
			taxSummary.setTaxSCAmount(taxSCAmount);
			taxGSTAmount+=mOrder.getExtraChargeGSTAmount();  
			taxSummary.setTaxGSTAmount(taxGSTAmount);
			
			totalTaxAmo+=getExtraChargeTotalTaxAmount(mOrder); 
			taxSummary.setTaxAmount(totalTaxAmo);
			taxableAmo+=mOrder.getExtraCharges(); 
			taxSummary.setTaxableAmount(taxableAmo);
			

		}
		
		public static boolean canShowItemEditUI(BeanOrderDetail orderItem) {
			
			final BeanUIOrderEntrySetting orderEntrySettings =PosEnvSettings.getInstance().getUISetting().getOrderEntryUISettings();
			
			final boolean showItemDetEditScreen= ((orderItem.getSaleItem().isComboItem() && orderEntrySettings.canShowEditWindowForItemsWithComboOnAdd()) 
					|| (orderItem.getSaleItem().hasChoices() && orderEntrySettings.canShowEditWindowForItemsWithChoiceOnAdd()) 
					|| (orderItem.getSaleItem().getAtrributeList()!=null 
							&& orderItem.getSaleItem().getAtrributeList().size()>0 
							&& orderEntrySettings.canShowEditWindowForItemsWithModifiersOnAdd()));
			
			return showItemDetEditScreen;
		}
}
