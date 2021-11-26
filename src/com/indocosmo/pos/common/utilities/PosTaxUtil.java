package com.indocosmo.pos.common.utilities;

import com.indocosmo.pos.data.beans.BeanSaleItem;
import com.indocosmo.pos.data.beans.BeanTax;
import com.indocosmo.pos.data.providers.shopdb.PosTaxAmountObject;
import com.indocosmo.pos.data.providers.shopdb.PosTaxItemProvider.TaxCalculationMethod;

public final class PosTaxUtil {
	//TAXDISC130203
	/**
	 * @param item
	 */
	public static void calculateTax(BeanSaleItem item) {
		if(item.getTax()!=null)
			recalcTax(item);
	}
	
	/**
	 * @param tax
	 * @param amount
	 * @return
	 */
	private static double getUnitPriceFromTotal(BeanTax tax, double amount){
		double itemUnitPrice = 0;
		double itemPrice = amount;
		final double gstAmount = getGSTAmount(tax,itemPrice);
		itemPrice -= gstAmount;

		final double totalTaxRate = getTotalTaxRate(tax);
		itemUnitPrice = itemPrice / (1 + (totalTaxRate / 100));
		return itemUnitPrice;
	}
	
	/**
	 * @param tax
	 * @param amount
	 * @return
	 */
	public static double getTaxAmountFromTotal(BeanTax tax, double amount){
		final double unitPrice=getUnitPriceFromTotal(tax,amount);
		final double taxAmount=amount-unitPrice;
		return taxAmount;
	}

	/**
	 * @param tax
	 * @param fixedPrice
	 * @return
	 */
	public static double getGSTAmount(BeanTax tax,double fixedPrice) {
		double gstAmount = 0;
		if (tax.isGSTDefined()) {
			final double itemPrice = fixedPrice;
			final double amountWithOutGst = itemPrice
					/ (1 + tax.getGSTPercentage() / 100);
			gstAmount = itemPrice - amountWithOutGst;
		}
		return gstAmount;
	}

	/**
	 * @param tax
	 * @return
	 */
	public static double getTotalTaxRate(BeanTax tax) {
		double totalTaxRate = 0;
		if (tax.isTaxOneApplicable())
			totalTaxRate += tax.getTaxOnePercentage();
		if (tax.isTaxTwoApplicable())
			totalTaxRate += tax.getTaxTwoPercentage();
		if (tax.isTaxThreeApplicable())
			totalTaxRate += tax.getTaxThreePercentage();
		if (tax.isServiceTaxApplicable())
			totalTaxRate += tax.getServiceTaxPercentage();
		return totalTaxRate;
	}
//
//	/**
//	 * @param tax
//	 * @param taxableAmount
//	 * @return
//	 */
//	public static PosTaxAmountObject calculateTaxes(BeanTax tax,
//			double taxableAmount) {
//		
//		PosTaxAmountObject taxAmount = new PosTaxAmountObject();
//		taxAmount.setTaxableAmount(taxableAmount);
//		if(tax==null ) return taxAmount;
//		if (tax.isTaxOneApplicable())
//			taxAmount.setTaxOneAmount(PosCurrencyUtil.roundTo(taxableAmount * tax.getTaxOnePercentage()
//					/ 100));
//		if (tax.isTaxTwoApplicable())
//			taxAmount.setTaxTwoAmount(PosCurrencyUtil.roundTo(taxableAmount * tax.getTaxTwoPercentage()
//					/ 100));
//		if (tax.isTaxThreeApplicable())
//			taxAmount.setTaxThreeAmount(PosCurrencyUtil.roundTo(taxableAmount
//					* tax.getTaxThreePercentage() / 100));
//		if (tax.isServiceTaxApplicable())
//			taxAmount.setServiceTaxAmount(PosCurrencyUtil.roundTo(taxableAmount
//					* tax.getServiceTaxPercentage() / 100));
//		if (tax.isGSTDefined())
//			taxAmount.setGSTAmount(calculateGST(tax, taxAmount, taxableAmount));
//		
//		return taxAmount;
//
//	}
	/**
	 * @param tax
	 * @param taxableAmount
	 * @return
	 */
	public static PosTaxAmountObject calculateTaxes(BeanTax tax,
			double taxableAmount) {
		PosTaxAmountObject taxAmount = new PosTaxAmountObject();
		taxAmount.setTaxableAmount(taxableAmount);
		if(tax==null ) return taxAmount;
		if (tax.isTaxOneApplicable())
			taxAmount.setTaxOneAmount(PosCurrencyUtil.roundTo(taxableAmount * tax.getTaxOnePercentage()
					/ 100));
		if (tax.isTaxTwoApplicable())
			taxAmount.setTaxTwoAmount(PosCurrencyUtil.roundTo(taxableAmount * tax.getTaxTwoPercentage()
					/ 100));
		if (tax.isTaxThreeApplicable())
			taxAmount.setTaxThreeAmount(PosCurrencyUtil.roundTo(taxableAmount
					* tax.getTaxThreePercentage() / 100));
		if (tax.isServiceTaxApplicable())
			taxAmount.setServiceTaxAmount(PosCurrencyUtil.roundTo(taxableAmount
					* tax.getServiceTaxPercentage() / 100));
		if (tax.isGSTDefined())
			taxAmount.setGSTAmount((calculateGST(tax, taxAmount, taxableAmount)));
		return taxAmount;

	}

	/**
	 * @param tax
	 * @param taxamounts
	 * @param totalTaxableAmount
	 * @return
	 */
	private static double calculateGST(BeanTax tax,
			PosTaxAmountObject taxamounts, double totalTaxableAmount) {
		double gstAmount = 0;
		totalTaxableAmount += ((tax.isTaxOneIncludeInGST()) ? taxamounts
				.getTaxOneAmount() : 0)
				+ ((tax.isTaxTwoIncludeInGST()) ? taxamounts.getTaxTwoAmount()
						: 0)
				+ ((tax.isTaxThreeIncludeInGST()) ? taxamounts
						.getTaxThreeAmount() : 0)
				+ ((tax.isServiceTaxIncludeInGST()) ? taxamounts
						.getServiceTaxAmount() : 0);
		gstAmount = totalTaxableAmount * tax.getGSTPercentage() / 100;
		return gstAmount;
	}
	
	/**
	 * @param saleItem
	 */
	public static void recalcTax(BeanSaleItem saleItem){
		if(saleItem.getTax()==null) return;
		saleItem.getTax().setTaxAmount(null);
		PosTaxAmountObject taxAmo=((saleItem.getTaxCalculationMethod().equals(TaxCalculationMethod.InclusiveOfTax))?
				calcTaxInclusive(saleItem):
					calcTaxExclusive(saleItem));
		saleItem.getTax().setTaxAmount(taxAmo);
	}
	
	/**
	 * @param item
	 * @return
	 */
	private static PosTaxAmountObject calcTaxInclusive(BeanSaleItem item){
	
		PosTaxAmountObject itemTaxAmount=null;
		
		final double itemTotalPrice=PosSaleItemUtil.getItemFixedPrice(item)*item.getQuantity();
		/*
		 * When tax is calculated on items with discount, 
		 * first deduct the discount amount from the total item price and 
		 * then calculate tax. This is needed to avoid confusion in customers.
		 */
		final double discAmount=PosSaleItemUtil.getTotalDiscountAmount(item);
		final double itemTaxableAmount=itemTotalPrice-discAmount;
		
		final double gstAmount = getGSTAmount(item.getTax(),itemTaxableAmount);
		final double itemPriceWOGst =itemTaxableAmount- gstAmount;

		final double totalTaxRate = getTotalTaxRate(item.getTax());
		final double itemPriceExclTax = itemPriceWOGst / (1 + (totalTaxRate / 100));
		
		itemTaxAmount=calculateTaxes(item.getTax(),itemPriceExclTax);
		
		return itemTaxAmount;
	}
	
	
//	/**
//	 * @param item
//	 * @return
//	 */
//	private static PosTaxAmountObject calcTaxInclusive(BeanSaleItem item){
//	
//		PosTaxAmountObject itemTaxAmount=null;
//		
//		final double itemTotalPrice=PosSaleItemUtil.getItemFixedPrice(item)*item.getQuantity();
//		/*
//		 * When tax is calculated on items with discount, 
//		 * first deduct the discount amount from the total item price and 
//		 * then calculate tax. This is needed to avoid confusion in customers.
//		 */
//		final double discAmount=PosSaleItemUtil.getTotalDiscountAmount(item);
//		final double itemTaxableAmount=itemTotalPrice-discAmount;
//		
//		final double gstAmount = getGSTAmount(item.getTax(),itemTaxableAmount);
//		final double itemPriceWOGst =itemTaxableAmount- gstAmount;
//
//		final double totalTaxRate = getTotalTaxRate(item.getTax());
//		final double itemPriceExclTax = itemPriceWOGst / (1 + (totalTaxRate / 100));
//		
//		itemTaxAmount=calculateTaxes(item.getTax(),itemPriceExclTax );
//		itemTaxAmount.setTaxableAmount(PosCurrencyUtil.roundTo(itemTotalPrice-discAmount -itemTaxAmount.getTaxOneAmount()-itemTaxAmount.getTaxTwoAmount()-itemTaxAmount.getTaxThreeAmount()));
////		itemTaxAmount.setTaxableAmount(itemTaxAmount.getTaxableAmount() *item.getQuantity()));
////		itemTaxAmount.setTaxOneAmount(PosCurrencyUtil.roundTo(itemTaxAmount.getTaxOneAmount() *item.getQuantity()));
////		itemTaxAmount.setTaxTwoAmount(PosCurrencyUtil.roundTo(itemTaxAmount.getTaxTwoAmount() *item.getQuantity()));
////		itemTaxAmount.setTaxThreeAmount(PosCurrencyUtil.roundTo(itemTaxAmount.getTaxThreeAmount() *item.getQuantity()));
//		return itemTaxAmount;
//	}
	/**
	 * @param item
	 * @return
	 */
	private static PosTaxAmountObject calcTaxExclusive(BeanSaleItem item){
		
		PosTaxAmountObject itemTaxAmount=null;
		
		final double itemTotalPrice=PosSaleItemUtil.getItemFixedPrice(item)*item.getQuantity();
		/*
		 * When tax is calculated on items with discount, 
		 * first deduct the discount amount from the total item price and 
		 * then calculate tax. This is needed to avoid confusion in customers.
		 */
		final double discAmount= (PosSaleItemUtil.getTotalDiscountAmount(item));
		final double itemTaxableAmount= (itemTotalPrice-discAmount);
				
		itemTaxAmount=calculateTaxes(item.getTax(),itemTaxableAmount);
		
		return itemTaxAmount;
	}

}
