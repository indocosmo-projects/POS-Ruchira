/**
 * 
 */
package com.indocosmo.pos.common.utilities;

import java.util.ArrayList;

import com.indocosmo.pos.common.PosEnvSettings;
import com.indocosmo.pos.common.enums.PosOrderServiceTypes;
import com.indocosmo.pos.data.beans.BeanCustomerType;
import com.indocosmo.pos.data.beans.BeanDiscount;
import com.indocosmo.pos.data.beans.BeanItemClassBase;
import com.indocosmo.pos.data.beans.BeanOrderDetail;
import com.indocosmo.pos.data.beans.BeanOrderDetail.OrderDetailItemType;
import com.indocosmo.pos.data.beans.BeanSaleItem;
import com.indocosmo.pos.data.beans.BeanSaleItemComboContentSubstitute;
import com.indocosmo.pos.data.beans.BeanServingTableLocation;
import com.indocosmo.pos.data.beans.BeanTax;
import com.indocosmo.pos.data.beans.BeanTaxGlobalParam.POSTaxationBasedOn;
import com.indocosmo.pos.data.providers.shopdb.PosDiscountItemProvider;
import com.indocosmo.pos.data.providers.shopdb.PosPromotionItemProvider;
import com.indocosmo.pos.data.providers.shopdb.PosSaleItemPromotionProvider;
import com.indocosmo.pos.data.providers.shopdb.PosSaleItemProvider;
import com.indocosmo.pos.data.providers.shopdb.PosTaxAmountObject;
import com.indocosmo.pos.data.providers.shopdb.PosTaxItemProvider.TaxCalculationMethod;
import com.indocosmo.pos.forms.PosOrderEntryForm;

/**
 * @author jojesh
 *
 */
public final class PosSaleItemUtil {

	/**
	 * Return the item price per unit
	 * It may include tax depend on the tax calculation method
	 * @param item
	 * @return
	 */
	public static double getItemFixedPrice(BeanSaleItem item){
		
		return  ((item.getCustomerPrice()>0)?getActualCustomerPrice(item):item.getFixedPrice());
	}

	/**
	 * @param item
	 * @return
	 */
	private static double getActualCustomerPrice(BeanSaleItem item){
		
		return item.getFixedPrice() -item.getFixedPrice()*(item.getCustomerPrice()/100);
	}

//	private static double getSubItemTotalPrice(BeanSaleItem item){
//		return getExtrasAmount(item);
//	}

	/**
	 * Returns the total item price exclusive/inclusive of tax
	 * @param item
	 * @return
	 */
	public static double getTotalItemPrice(BeanSaleItem item) {
		
		return (getItemFixedPrice(item) *getItemQuantity(item));
	}

	/**
	 * Return the item quantity amount
	 * @param item
	 * @return
	 */
	public static double getItemQuantity(BeanSaleItem item){
		
		return item.getQuantity();
	}

	/**
	 * Return the discountable amount
	 * @param item
	 * @return
	 */
	private static double getDiscountableAmount(BeanSaleItem item){
		
		double discount=0;
		discount=getItemFixedPrice(item);
		
		return discount;
	}

	/**
	 * Calculate the actual quantity on which the discount can be applied
	 * @return
	 */
	public static double getActualDiscountQuantity(BeanSaleItem item){
		
		double actualQuantity=0;
		if(item.getDiscount().getRequiredQuantity()==0)
			actualQuantity=(!item.getDiscount().isPercentage()?1:item.getQuantity());
		else{
			actualQuantity=(int)(item.getQuantity()/item.getDiscount().getRequiredQuantity());
			actualQuantity*=((item.getDiscount().isPercentage())?item.getDiscount().getRequiredQuantity():1);
		}
		
		return actualQuantity;
	}

	/**
	 * Return the discount amount
	 * @param item
	 * @return
	 */
	private static double getDiscountAmount(BeanSaleItem item) {
		
		if (item.getDiscount() == null)
			return 0;	
		
		final BeanDiscount discount =item.getDiscount();
		final double discountableAmount=getDiscountableAmount(item);
		double discAmount = (discount.isPercentage()) ? discountableAmount* discount.getPrice() / 100 : discount.getPrice();
		
		return discAmount; 
	}

	/**
	 * Return the total discount amount with variants;
	 * @return
	 */
	public static double getTotalDiscountAmount(BeanSaleItem item){
		
		return getTotalDiscountAmount(item,true);
	}

	/**
	 * @param item
	 * @param includeVariants
	 * @return
	 */
	public static double getTotalDiscountAmount(BeanSaleItem item,boolean includeVariants ) {
		
		if (item.getDiscount() == null)
			return 0;
		
		double amount=getActualDiscountQuantity(item)*getDiscountAmount(item);
		amount+=((includeVariants)?item.getDiscount().getVariants()*item.getQuantity():0);
		
		return (amount>getTotalItemPrice(item))?getTotalItemPrice(item):amount; 
	}

	/**
	 * Return the taxable amount
	 * @param item
	 * @return
	 */
	public static double getTotalTaxableAmount(BeanSaleItem item){
		
		double taxableAmount=0;
		
		taxableAmount=getItemFixedPrice(item)*getItemQuantity(item)-getTotalDiscountAmount(item);
		
		return taxableAmount;
	}

//	/**
//	 * 
//	 * @returns items total price which is inclusive of sub items too. 
//	 */
//	public static double getExtrasAmount(BeanSaleItem saleItem){
//		double total=0;
//		if (saleItem.getExtras()!=null){
//			for(int choiceCode:saleItem.getExtras().keySet()){
//				for(BeanSaleItem item:saleItem.getExtras().get(choiceCode)){
//					total+=item.getItemPriceDiff();
//				}
//			}
//		}
//		return total;
//	}

	/**
	 * Return the total tax amount
	 * @param item
	 * @return
	 */
	public static double getTotalTaxAmount(BeanSaleItem item,boolean inclTaxRound){
		
		final double totalTax=  getTotalT1Amount(item) + getTotalT2Amount(item) + getTotalT3Amount(item)
				+ getTotalGSTAmount(item) + getTotalServiceTaxAmount(item)+((inclTaxRound)?getTaxRoundAdjustment(item):0);
		
		return totalTax;
	}

	/**
	 * @param item
	 * @return
	 */
	public static double getTaxRoundAdjustment(BeanSaleItem item){
		
		if(item.getTax()==null) return 0;
		PosTaxAmountObject taxAmount=item.getTax().getTaxAmount();
		
		return ((taxAmount==null)?0:taxAmount.getTaxRoundAdjustment());
	}

	/**
	 * Return the total tax1 amount
	 * @param item
	 * @return
	 */
	public static double getTotalT1Amount(BeanSaleItem item){
		
		return item.getT1TaxAmount();
	}

	/**
	 * Return the total tax2 amount
	 * @param item
	 * @return
	 */
	public static double getTotalT2Amount(BeanSaleItem item){
		
		return item.getT2TaxAmount();
	}

	/**
	 * Return the total tax3 amount
	 * @param item
	 * @return
	 */
	public static double getTotalT3Amount(BeanSaleItem item){
		
		return item.getT3TaxAmount();
	}

	/**
	 * Return the total GST amount
	 * @param item
	 * @return
	 */
	public static double getTotalGSTAmount(BeanSaleItem item){
		
		return item.getGSTAmount();
	}

	/**
	 * Return the total service tax amount
	 * @param item
	 * @return
	 */
	public static double getTotalServiceTaxAmount(BeanSaleItem item){
		
		return item.getServiceTaxAmount();
	}

	public static double getGrandTotal(BeanSaleItem item) {
		
		return getGrandTotal(item,true);
	}
	
	/**
	 * Returns the grant item total
	 * @param item
	 * @return
	 */
	public static double getGrandTotalBasedOnService(BeanSaleItem item ) {
		
		PosOrderServiceTypes serviceType=( PosOrderEntryForm.getInstance()==null?PosOrderServiceTypes.TAKE_AWAY:PosOrderEntryForm.getInstance().getSelectedServiceType());
		//=PosOrderServiceTypes.TAKE_AWAY;
		//PosOrderEntryForm.getInstance().getSelectedServiceType()
		//( PosOrderEntryForm.getInstance()==null?PosOrderServiceTypes.TAKE_AWAY:PosOrderEntryForm.getInstance().getSelectedServiceType());
		final double taxableAmt = getTotalItemPrice(item,serviceType) 
				-getTotalDiscountAmount(item,false);
		
		final double total=taxableAmt+((item.getTaxCalculationMethod().equals(TaxCalculationMethod.ExclusiveOfTax))?getExcluciveTaxAmount(item.getTax(), taxableAmt):0);
		
		return total;
	}
	
	/**
	 * Returns the tax total
	 * @param taxable amount 
	 * @param taz
	 * @return
	 */
	private static double getExcluciveTaxAmount(BeanTax tax, double taxableAmount) {
		
		if(tax==null)
			return 0;
		
		PosTaxAmountObject taxAmount=PosTaxUtil.calculateTaxes(tax, taxableAmount);
		
		return taxAmount.getTaxOneAmount()+ taxAmount.getTaxTwoAmount() + taxAmount.getTaxThreeAmount() + taxAmount.getGSTAmount() + taxAmount.getServiceTaxAmount();
	}
	
	/**
	 * Returns the total item price exclusive/inclusive of tax
	 * @param item
	 * @return
	 */
	public static double getTotalItemPrice(BeanSaleItem item,PosOrderServiceTypes serviceType) {
		
		double itemPrice;
		if(serviceType==PosOrderServiceTypes.WHOLE_SALE ){
			itemPrice=item.isPercetangeWholesalePrice()?item.getRetailPrice()*item.getWholesalePrice()/100:item.getWholesalePrice();
		}else
			itemPrice=item.getRetailPrice();
		
		
		return (itemPrice *getItemQuantity(item));
	}

	
	/**
	 * Returns the grant item total
	 * @param item
	 * @return
	 */
	public static double getGrandTotal(BeanSaleItem item,boolean withDiscount) {
		
		double total = getTotalItemPrice(item)
				-((withDiscount)?getTotalDiscountAmount(item,false):0)+
				((item.getTaxCalculationMethod().equals(TaxCalculationMethod.ExclusiveOfTax))?+ PosCurrencyUtil.roundTo(getTotalTaxAmount(item, false)):0);
		
		return total;
	}

	/**
	 * @param item
	 * @param custType
	 */
	/**
	 * @param orderDetail
	 * @param custType
	 */
	public static void resetItemPrice4CustType(BeanOrderDetail orderDetail, BeanCustomerType custType){
		
		BeanSaleItem item=orderDetail.getSaleItem();
//		double cTypePrice = new PosSaleItemProvider().getItemPriceByCustType(custType, item);
		
//		if(!PosEnvSettings.getInstance().getUISetting().getOrderEntryUISettings().isCustomerPriceAsDiscount()){
//			item.setCustomerPrice(cTypePrice);	
//		}else	{
		  
		ArrayList<BeanSaleItem> availablePositems = PosOrderEntryForm.getInstance().getAvailablePositems();
		final int index = findIndexoOf(item.getItemCode().toString(), availablePositems, 0);
		if (index >= 0) {
			
			if (orderDetail.getItemType()==OrderDetailItemType.SALE_ITEM)
				item.setFixedPrice(availablePositems.get(index).getFixedPrice());
			else if(item.getFixedPrice()!=0){
			
				if (orderDetail.getItemType()==OrderDetailItemType.COMBO_CONTENT_ITEM ){
					
					for(BeanSaleItemComboContentSubstitute comboContent:orderDetail.getComboContentItem().getContentItems()){
						
						if(comboContent.getSaleItem().equals(item))
							item.setFixedPrice(comboContent.getPriceDifferance());
//						PosCurrencyUtil.format(PosSaleItemUtil.getGrandTotal(item))
					}
//					orderDetail.getComboContentItem().getContentItems().get(index)
//					item.setFixedPrice(price);
		
				}else if (orderDetail.getItemType()==OrderDetailItemType.EXTRA_ITEM){
					item.setFixedPrice(availablePositems.get(index).getFixedPrice());
				}
			} 
			 	
		}
		
		if(item.getDiscount()==null || item.getDiscount().getCode().equals(PosDiscountItemProvider.NONE_DISCOUNT_CODE) ||
				 item.getDiscount().getCode().equals(PosDiscountItemProvider.CUSTOMER_DISCOUNT)){
		 	
			item.setCustomerPrice(0);
		 	
		 	final BeanDiscount customeDiscount= new PosSaleItemProvider().getItemPriceAsDiscountByCustType(custType, item);
			item.setDiscount( (customeDiscount!=null && customeDiscount.getPrice()!=0) ?customeDiscount:(new PosDiscountItemProvider().getNoneDiscount()));
		}
			
//		}
		PosTaxUtil.calculateTax(item);
	}
	
	
 

	/**
	 * @param saleItem
	 * @throws Exception
	 */
	public static void setPromotionToItem(BeanSaleItem saleItem) throws Exception{
		
		if(saleItem.getDiscount() !=null && 
				saleItem.getDiscount().getCode().equals(PosDiscountItemProvider.NONE_DISCOUNT_CODE)){
			
			BeanDiscount curPromo=PosOrderEntryForm.getInstance().getCurrentPromotion();
			// if default promotion do not add
			if(curPromo!=null && !curPromo.getCode().equals(PosPromotionItemProvider.DEF_PROMO_CODE)){
				
				if(saleItem.getSaleItemPromotionList().contains(curPromo.getCode())){
					
					if(curPromo.isItemSpecific()){
						
						BeanDiscount promo;
						try {
							
							promo = new PosSaleItemPromotionProvider().getPromotionItem(saleItem.getId(),curPromo.getId());
							
							if(promo!=null)
								saleItem.setPromotion(promo);
							
						} catch (Exception e) {
							
							throw new Exception("Failed to load promotion. Please contact administrator");
						}
					}else{
						
						saleItem.setPromotion(curPromo);
					}
				}
			}
		}
	}

	/**
	 * @param item
	 * @return
	 */
	public static double getDiscountVariants(BeanSaleItem item){
		
		final BeanDiscount discount=item.getDiscount();
		final double variance=((discount!=null)?discount.getVariants():0);
		
		return variance;
	}
	

	/**
	 * @param item
	 * @param isAlterNatieve
	 * @return
	 */
	public static String getItemNameToPrint(BeanSaleItem item, boolean isAlterNatieve){
		
		String name=((item.getAlternativeNameToPrint()!=null && item.getAlternativeNameToPrint().trim().length()>0 && isAlterNatieve)
					?
							item.getAlternativeNameToPrint()
					:
							item.getNameToPrint());

		name=((name!=null && name.trim().length()>0)
						?
							name
						:
							getItemNameToDisplay(item,isAlterNatieve));
										
		return name;
	}
	

	/**
	 * @param item
	 * @param isAlterNatieve
	 * @return
	 */
	public static String getItemNameToDisplay(BeanSaleItem item,boolean isAlterNatieve){

		String name="";

		name=(isAlterNatieve)
				?
						((item.getAlternativeName()!=null && item.getAlternativeName().trim().length()>0)
								?
										item.getAlternativeName()
								:
										item.getName())
				:
						item.getName();

		return name;
	}
	
	/**
	 * @param item
	 * @param isAlterNatieve
	 * @return
	 */
	public static String getItemClassNameToDisplay(BeanItemClassBase item,boolean isAlterNatieve){

		String name="";

		name=(isAlterNatieve)
				?
						((item.getAlternativeName()!=null && item.getAlternativeName().trim().length()>0)
								?
										item.getAlternativeName()
								:
										item.getName())
				:
						item.getName();

		return name;
	}

	/**
	 * @param item
	 * @param selectedServiceType
	 */
	public static void resetSaleItemPriceForService(BeanSaleItem saleItem,
			PosOrderServiceTypes serviceType) {
		
		if(serviceType==PosOrderServiceTypes.WHOLE_SALE ){
			saleItem.setFixedPrice(saleItem.isPercetangeWholesalePrice()?saleItem.getRetailPrice()*saleItem.getWholesalePrice()/100:saleItem.getWholesalePrice());
		}else
			saleItem.setFixedPrice(saleItem.getRetailPrice());
		
		PosTaxUtil.calculateTax(saleItem);
	}
 
	/**
	 * @param item
	 * @param selectedServiceType
	 * @throws CloneNotSupportedException 
	 */
	public static void resetSaleItemTax(BeanOrderDetail orderItem,
			PosOrderServiceTypes serviceType) throws CloneNotSupportedException {

		BeanSaleItem saleItem=orderItem.getSaleItem();
		if(!setTaxBasedLocation (orderItem ))
			
			if (saleItem.getTaxationBasedOn()== POSTaxationBasedOn.ServiceType){

				if (serviceType==PosOrderServiceTypes.HOME_DELIVERY){
					saleItem.setTax( saleItem.getTaxHomeService());
				}else if (serviceType==PosOrderServiceTypes.TABLE_SERVICE){
					saleItem.setTax(saleItem.getTaxTableService());

				}else{
					saleItem.setTax( saleItem.getTaxTakeAwayService());
				}
		}else{
			saleItem.setTax(getSalesItemDefaultTax(saleItem));
		}
		PosTaxUtil.calculateTax(saleItem);
	}
	
	/**
	 * @param orderItem
	 * @throws CloneNotSupportedException 
	 */
	private static boolean setTaxBasedLocation(BeanOrderDetail orderItem) throws CloneNotSupportedException{
		
		boolean result=false;
		
		if(orderItem.getServingTable()!=null){
			
			BeanServingTableLocation servingTableLoc=orderItem.getServingTable().getLocation();
			if(servingTableLoc.isApplyServiceTax()){
				orderItem.getSaleItem().setTax( servingTableLoc.getServiceTax().clone());
				result=true;
			}
		}
		return result;
	}

	private static BeanTax getSalesItemDefaultTax(BeanSaleItem salesItem) throws CloneNotSupportedException{
		
		BeanTax tax=null;
		ArrayList<BeanSaleItem> availablePositems = PosOrderEntryForm.getInstance().getAvailablePositems();
		final int index = findIndexoOf(salesItem.getItemCode().toString(), availablePositems, 0);
		if (index >= 0) {
			BeanSaleItem item = availablePositems.get(index);
			tax=item.getTax().clone();
		}
		return tax;
	}
	private static int findIndexoOf(String code,
			ArrayList<BeanSaleItem> posSaleItems, int searchStartIndex) {
		boolean isFound = false;
		for (int index = 0; index < posSaleItems.size(); index++, searchStartIndex++) {
			searchStartIndex = (searchStartIndex < posSaleItems.size()) ? searchStartIndex
					: 0;
			if (posSaleItems.get(searchStartIndex).getCode().equals(code)) {
				isFound = true;
				break;
			}
		}
		return ((isFound) ? searchStartIndex : -1);
	}
	}
