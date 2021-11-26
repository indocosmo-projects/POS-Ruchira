/**
 * 
 */
package com.indocosmo.pos.data.providers.shopdb;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.sql.rowset.CachedRowSet;

import com.indocosmo.pos.common.PosLog;
import com.indocosmo.pos.common.utilities.PosCurrencyUtil;
import com.indocosmo.pos.data.beans.BeanOrderHeader.PosOrderStatus;
import com.indocosmo.pos.data.beans.BeanTallyExportItem;
import com.indocosmo.pos.data.providers.shopdb.PosTaxItemProvider.TaxCalculationMethod;

/**
 * @author jojesh-13.2
 *
 */
public class PosTallyInvoiceExportProvider extends PosShopDBProviderBase{


	/**
	 * 
	 */
	public PosTallyInvoiceExportProvider(){

		super("v_invoice_tally_export");
	}

 

	/**
	 * @param qrySQL
	 * @return
	 * @throws Exception 
	 */
	public ArrayList<BeanTallyExportItem> getInvoiceList(String qrySQL) throws Exception {
		
		
		
		String sql="SELECT " + 
				 " order_hdrs.invoice_no  AS  invoice_no ," + 
				 " order_hdrs.closing_date  AS  invoice_date ," +
				 " CASE WHEN order_hdrs.is_ar_customer THEN  'CREDIT SALES' ELSE '' END AS voucher_type,  "+
			 " order_customers.name  AS  cust_name ,"+
			 " order_customers.address  AS  cust_address ,"+
			 " order_customers.city  AS  cust_city ,"+
			 " order_customers.state  AS  cust_state ,"+
			 " order_customers.tin  AS  cust_gst_no ,"+
			 " order_customers.state_code  AS  cust_state_code ,"+
			 " order_customers.country  AS  cust_country ,"+
			 " order_hdrs.closing_time  AS  supply_date ,"+
			 " order_hdrs.vehicle_number  AS  vehicle_number ,"+
			 " order_hdrs.order_id  AS  order_id ,"+
			 " order_dtls.name  AS  sale_item_name ,"+
			 " order_dtls.sub_class_name  AS  sub_class_name ,"+
			 " order_dtls.description  AS  description ,"+
			 " order_dtls.sale_item_hsn_code  AS  sale_item_hsn_code ,"+
			 " order_dtls.qty  AS  qty ,"+
			 " order_dtls.uom_name  AS  uom_name ,"+
			 " order_dtls.tax_calculation_method  AS  tax_calculation_method ,"+
			 " order_dtls.fixed_price  AS  fixed_price ,"+
			 " order_dtls.customer_price_variance  AS  customer_price_variance ,"+
			 " order_dtls.tax_name  AS  tax_name ,"+
			 " order_dtls.tax1_pc  AS  tax1_pc ,"+
			 " order_dtls.tax2_pc  AS  tax2_pc ,"+
			 " order_dtls.tax1_name  AS  tax1_name ,"+
			 " order_dtls.tax2_name  AS  tax2_name ,"+
			 " order_dtls.tax1_amount  AS  tax1_amount ,"+
			 " order_dtls.tax2_amount  AS  tax2_amount ,"+
			 " order_dtls.discount_name  AS  item_discount_name ,"+
			 " order_dtls.discount_is_percentage  AS  item_discount_is_percentage ,"+
			 " order_dtls.discount_amount  AS item_discount_amount ,"+
			 " order_dtls.discount_price  AS item_discount_price ,"+
			 " order_dtls.item_total  AS  item_total ,"+
			 " order_dtls.uom_symbol AS uom_symbol," + 
			 " order_dtls.uom_name AS uom_name, " +
			 " order_hdrs.bill_discount_percentage  AS  bill_discount_percentage " + 
			 " FROM "+
			 "	( "+
			 "		( "+
			 "			 order_hdrs " +
			 "			JOIN  order_dtls  ON ( "+
			 "				( "+
			 "					 order_hdrs.order_id  =  order_dtls.order_id "+ 
			 "				) "+
			 "			) "+
			 "		) "+
			 "		JOIN  order_customers  ON ( "+
			 "			( "+
			 "				 order_hdrs.order_id  =  order_customers.order_id "+ 
			 "			) "+
			 "		) "+
			 "	) "+
			 " WHERE "+
			 "	( order_dtls.is_void  <> 1 AND order_hdrs.status IN (" + 
			 PosOrderStatus.Closed.getCode() + "," + PosOrderStatus.Refunded.getCode() + " ) ) ";
		
 
		sql+=((qrySQL!=null && qrySQL.trim()!="")? "  AND  " + qrySQL:"") + " ORDER BY invoice_no,order_dtls.id ";

		ArrayList<BeanTallyExportItem> itemList=null;

		try {

			CachedRowSet res=executeQuery(sql);

			if(res!=null){

				itemList=new ArrayList<BeanTallyExportItem>();

				while(res.next()){

					BeanTallyExportItem item=createItemFromResult(res);
					itemList.add(item);
				}
				res.close();
			}
		} catch (SQLException e) {

			PosLog.write(this,"getInvoiceList",e);
		}
		
		return itemList;
	}

	/**
	 * @param res
	 * @return
	 */
	private BeanTallyExportItem createItemFromResult(CachedRowSet res) throws Exception {


		BeanTallyExportItem item=new BeanTallyExportItem();

		item.setInvoiceNo(res.getInt("invoice_no"));
		item.setInvoiceDate(res.getString("invoice_date"));
		item.setVoucherType(res.getString("voucher_type"));
		item.setCustName(res.getString("cust_name"));
		item.setCustAddress(res.getString("cust_address"));
//		item.setCustStreet(res.getString("cust_street"));
		item.setCustCity(res.getString("cust_city"));
		item.setCustState(res.getString("cust_state"));
		item.setCustGstNo(res.getString("cust_gst_no"));
		item.setCustStateCode(res.getString("cust_state_code"));
		item.setCustCountry(res.getString("cust_country"));
		item.setSupplyDate(res.getString("supply_date"));
		item.setVehicleNumber(res.getString("vehicle_number"));
		item.setOrderId(res.getString("order_id"));
		item.setSaleItemName(res.getString("sale_item_name"));
		item.setSubClassName(res.getString("sub_class_name"));
		item.setDescription(res.getString("description"));
		item.setSaleIemHSNCode(res.getString("sale_item_hsn_code"));
		item.setQty(res.getDouble("qty"));
		item.setUomName(res.getString("uom_name"));
		item.setUomSymbol(res.getString("uom_symbol"));
		item.setTaxCalculationMethod(TaxCalculationMethod.get(res.getInt("tax_calculation_method")));
		item.setFixedPrice(res.getDouble("fixed_price"));
		
		item.setCustomerPriceVariance(res.getDouble("customer_price_variance"));
		item.setTaxName(res.getString("tax_name"));
		item.setTax1Pc(res.getDouble("tax1_pc"));
		item.setTax2Pc(res.getDouble("tax2_pc"));
		item.setTax1Name(res.getString("tax1_name"));
		item.setTax2Name(res.getString("tax2_name"));
		item.setItemDiscountName(res.getString("item_discount_name"));
		item.setItemDiscountAmount(res.getDouble("item_discount_amount"));
		item.setItemDiscountIsPercentage(res.getBoolean("item_discount_is_percentage"));
		item.setItemDiscountPrice(res.getDouble("item_discount_price"));
		item.setBillDiscountPercentage(res.getDouble("bill_discount_percentage"));
		if (item.getTaxCalculationMethod()==TaxCalculationMethod.ExclusiveOfTax){
			item.setRate( (item.getFixedPrice()));
		}else{
			
			 
			item.setRate(PosCurrencyUtil.roundTo(item.getFixedPrice()/(1+(item.getTax1Pc() + item.getTax2Pc())/100)));
			if(item.isItemDiscountPercentage()){
				item.setItemDiscountAmount(PosCurrencyUtil.roundTo(item.getRate()*item.getQty()*item.getItemDiscountPrice()/100));
			}else{
				item.setItemDiscountAmount(PosCurrencyUtil.roundTo((item.getItemDiscountPrice()-  (item.getItemDiscountPrice()*(item.getTax1Pc()+item.getTax2Pc())/100))*item.getQty()));
			}
		}
		
		item.setValue(PosCurrencyUtil.roundTo(item.getRate()*item.getQty()));
		final double taxableValue= item.getValue()-item.getItemDiscountAmount();
		if (item.getBillDiscountPercentage()>0)
			item.setItemDiscountAmount(item.getItemDiscountAmount()+ taxableValue*item.getBillDiscountPercentage()/100);
		
		item.setItemDiscountAmount(item.getItemDiscountAmount()>0?-1*item.getItemDiscountAmount():item.getItemDiscountAmount());
		item.setTaxableValue(PosCurrencyUtil.roundTo(item.getValue()+item.getItemDiscountAmount()));
		item.setTax1Amount(PosCurrencyUtil.roundTo(item.getTaxableValue()*item.getTax1Pc()/100));
		item.setTax2Amount(PosCurrencyUtil.roundTo(item.getTaxableValue()*item.getTax2Pc()/100));
		final double netValue= PosCurrencyUtil.roundTo(item.getTaxableValue()+item.getTax1Amount()+item.getTax2Amount());
		final double dbNetValue=res.getDouble("item_total") -res.getDouble("item_total")*item.getBillDiscountPercentage()/100;
		item.setRoundOff(dbNetValue-netValue);
		item.setNetValue(netValue+item.getRoundOff());
		

		
//		if (item.getTaxCalculationMethod()==TaxCalculationMethod.ExclusiveOfTax){
//			item.setRate(PosCurrencyUtil.roundTo(item.getFixedPrice()));
//		}else{
//			
//			final double taxableAmt=PosCurrencyUtil.roundTo(item.getFixedPrice()/(1+(item.getTax1Pc() + item.getTax2Pc())/100));
//			 final double t1Amount=PosCurrencyUtil.roundTo(taxableAmt * item.getTax1Pc()/100);
//			 final double t2Amount=PosCurrencyUtil.roundTo(taxableAmt * item.getTax2Pc()/100);
//			item.setRate(PosCurrencyUtil.roundTo(item.getFixedPrice()-t1Amount-t2Amount));
//		if(item.getDiscountIsPercentage()){
//			item.setDiscountAmount(PosCurrencyUtil.roundTo(item.getRate()*item.getQty()*item.getDiscountPrice()/100));
//		}else{
//			item.setDiscountAmount(PosCurrencyUtil.roundTo((item.getDiscountPrice()-  (item.getDiscountPrice()*(item.getTax1Pc()+item.getTax2Pc())/100))*item.getQty()));
//		}
//		item.setTaxableValue(PosCurrencyUtil.roundTo(item.getRate()*item.getQty()-item.getDiscountAmount()));
//		item.setTax1Amount(PosCurrencyUtil.roundTo(item.getTaxableValue()*item.getTax1Pc()/100));
//		item.setTax2Amount(PosCurrencyUtil.roundTo(item.getTaxableValue()*item.getTax2Pc()/100));
//		item.setNetValue(PosCurrencyUtil.roundTo(item.getTaxableValue()+item.getTax1Amount()+item.getTax2Amount()));

		return item;
	}

}
