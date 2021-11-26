/**
 * 
 */
package com.indocosmo.pos.data.beans;

import com.indocosmo.pos.data.providers.shopdb.PosTaxItemProvider.TaxCalculationMethod;

/**
 * @author jojesh-13.2
 *
 */
public class BeanTallyExportItem {
	
	private int invoiceNo;
	private String invoiceDate;
	private String voucherType;
	private String custName;
	private String custAddress;
	private String custStreet;
	private String custCity;
	private String custState;
	private String custGstNo;
	private String custStateCode;
	private String custCountry;
	private String supplyDate;
	private String vehicleNumber;
	private String orderId;
	private String saleItemName;
	private String subClassName;
	private String description;
	private String saleIemHSNCode;
	private double qty;
	private String uomName;
	private String uomSymbol;
	private TaxCalculationMethod taxCalculationMethod;
	private double fixedPrice;
	private double rate;
	private double customerPriceVariance;
	private String taxName;
	private double tax1Pc;
	private String tax1Name;
	private double tax2Pc;
	private String tax2Name;
	private double tax1Amount;
	private double tax2Amount;
	private String itemDiscountName;
	private double itemDiscountAmount;
	private boolean itemDiscountIsPercentage;
	private double itemDiscountPrice;
	private double taxableValue;
	private double netValue;
	private double roundOff;
	private double value;
	private double billDiscountPercentage;
	/**
	 * @return the invoiceNo
	 */
	public int getInvoiceNo() {
		return invoiceNo;
	}
	/**
	 * @param invoiceNo the invoiceNo to set
	 */
	public void setInvoiceNo(int invoiceNo) {
		this.invoiceNo = invoiceNo;
	}
	/**
	 * @return the invoiceDate
	 */
	public String getInvoiceDate() {
		return invoiceDate;
	}
	/**
	 * @param invoiceDate the invoiceDate to set
	 */
	public void setInvoiceDate(String invoiceDate) {
		this.invoiceDate = invoiceDate;
	}
	/**
	 * @return the voucherType
	 */
	public String getVoucherType() {
		return voucherType;
	}
	/**
	 * @param voucherType the voucherType to set
	 */
	public void setVoucherType(String voucherType) {
		this.voucherType = voucherType;
	}
	/**
	 * @return the custName
	 */
	public String getCustName() {
		return custName;
	}
	/**
	 * @param custName the custName to set
	 */
	public void setCustName(String custName) {
		this.custName = custName;
	}
	/**
	 * @return the custAddress
	 */
	public String getCustAddress() {
		return custAddress;
	}
	/**
	 * @param custAddress the custAddress to set
	 */
	public void setCustAddress(String custAddress) {
		this.custAddress = custAddress;
	}
	/**
	 * @return the custStreet
	 */
	public String getCustStreet() {
		return custStreet;
	}
	/**
	 * @param custStreet the custStreet to set
	 */
	public void setCustStreet(String custStreet) {
		this.custStreet = custStreet;
	}
	/**
	 * @return the custCity
	 */
	public String getCustCity() {
		return custCity;
	}
	/**
	 * @param custCity the custCity to set
	 */
	public void setCustCity(String custCity) {
		this.custCity = custCity;
	}
	/**
	 * @return the custState
	 */
	public String getCustState() {
		return custState;
	}
	/**
	 * @param custState the custState to set
	 */
	public void setCustState(String custState) {
		this.custState = custState;
	}
	/**
	 * @return the custGstNo
	 */
	public String getCustGstNo() {
		return custGstNo;
	}
	/**
	 * @param custGstNo the custGstNo to set
	 */
	public void setCustGstNo(String custGstNo) {
		this.custGstNo = custGstNo;
	}
	/**
	 * @return the custStateCode
	 */
	public String getCustStateCode() {
		return custStateCode;
	}
	/**
	 * @param custStateCode the custStateCode to set
	 */
	public void setCustStateCode(String custStateCode) {
		this.custStateCode = custStateCode;
	}
	/**
	 * @return the custCountry
	 */
	public String getCustCountry() {
		return custCountry;
	}
	/**
	 * @param custCountry the custCountry to set
	 */
	public void setCustCountry(String custCountry) {
		this.custCountry = custCountry;
	}
	/**
	 * @return the supplyDate
	 */
	public String getSupplyDate() {
		return supplyDate;
	}
	/**
	 * @param supplyDate the supplyDate to set
	 */
	public void setSupplyDate(String supplyDate) {
		this.supplyDate = supplyDate;
	}
	/**
	 * @return the vehicleNumber
	 */
	public String getVehicleNumber() {
		return vehicleNumber;
	}
	/**
	 * @param vehicleNumber the vehicleNumber to set
	 */
	public void setVehicleNumber(String vehicleNumber) {
		this.vehicleNumber = vehicleNumber;
	}
	/**
	 * @return the orderId
	 */
	public String getOrderId() {
		return orderId;
	}
	/**
	 * @param orderId the orderId to set
	 */
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	/**
	 * @return the saleItemName
	 */
	public String getSaleItemName() {
		return saleItemName;
	}
	/**
	 * @param saleItemName the saleItemName to set
	 */
	public void setSaleItemName(String saleItemName) {
		this.saleItemName = saleItemName;
	}
	/**
	 * @return the subClassName
	 */
	public String getSubClassName() {
		return subClassName;
	}
	/**
	 * @param subClassName the subClassName to set
	 */
	public void setSubClassName(String subClassName) {
		this.subClassName = subClassName;
	}
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @return the saleIemHSNCode
	 */
	public String getSaleIemHSNCode() {
		return saleIemHSNCode;
	}
	/**
	 * @param saleIemHSNCode the saleIemHSNCode to set
	 */
	public void setSaleIemHSNCode(String saleIemHSNCode) {
		this.saleIemHSNCode = saleIemHSNCode;
	}
	/**
	 * @return the qty
	 */
	public double getQty() {
		return qty;
	}
	/**
	 * @param qty the qty to set
	 */
	public void setQty(double qty) {
		this.qty = qty;
	}
	/**
	 * @return the uomName
	 */
	public String getUomName() {
		return uomName;
	}
	/**
	 * @param uomName the uomName to set
	 */
	public void setUomName(String uomName) {
		this.uomName = uomName;
	}
	
	/**
	 * @return the uomSymbol
	 */
	public String getUomSymbol() {
		return uomSymbol;
	}
	/**
	 * @param Symbol the Symbol to set
	 */
	public void setUomSymbol(String uomSymbol) {
		this.uomSymbol = uomSymbol;
	}
	/**
	 * @return the taxCalculationMethod
	 */
	public TaxCalculationMethod getTaxCalculationMethod() {
		return taxCalculationMethod;
	}
	/**
	 * @param taxCalculationMethod the taxCalculationMethod to set
	 */
	public void setTaxCalculationMethod(TaxCalculationMethod taxCalculationMethod) {
		this.taxCalculationMethod = taxCalculationMethod;
	}
	/**
	 * @return the fixedPrice
	 */
	public double getFixedPrice() {
		return fixedPrice;
	}
	/**
	 * @param fixedPrice the fixedPrice to set
	 */
	public void setFixedPrice(double fixedPrice) {
		this.fixedPrice = fixedPrice;
	}
	/**
	 * @return the rate
	 */
	public double getRate() {
		return rate;
	}
	/**
	 * @param rate the rate to set
	 */
	public void setRate(double rate) {
		this.rate = rate;
	}
	/**
	 * @return the customerPriceVariance
	 */
	public double getCustomerPriceVariance() {
		return customerPriceVariance;
	}
	/**
	 * @param customerPriceVariance the customerPriceVariance to set
	 */
	public void setCustomerPriceVariance(double customerPriceVariance) {
		this.customerPriceVariance = customerPriceVariance;
	}
	/**
	 * @return the taxName
	 */
	public String getTaxName() {
		return taxName;
	}
	/**
	 * @param taxName the taxName to set
	 */
	public void setTaxName(String taxName) {
		this.taxName = taxName;
	}
	/**
	 * @return the tax1Pc
	 */
	public double getTax1Pc() {
		return tax1Pc;
	}
	/**
	 * @param tax1Pc the tax1Pc to set
	 */
	public void setTax1Pc(double tax1Pc) {
		this.tax1Pc = tax1Pc;
	}
	/**
	 * @return the tax1Name
	 */
	public String getTax1Name() {
		return tax1Name;
	}
	/**
	 * @param tax1Name the tax1Name to set
	 */
	public void setTax1Name(String tax1Name) {
		this.tax1Name = tax1Name;
	}
	/**
	 * @return the tax2Pc
	 */
	public double getTax2Pc() {
		return tax2Pc;
	}
	/**
	 * @param tax2Pc the tax2Pc to set
	 */
	public void setTax2Pc(double tax2Pc) {
		this.tax2Pc = tax2Pc;
	}
	/**
	 * @return the tax2Name
	 */
	public String getTax2Name() {
		return tax2Name;
	}
	/**
	 * @param tax2Name the tax2Name to set
	 */
	public void setTax2Name(String tax2Name) {
		this.tax2Name = tax2Name;
	}
	/**
	 * @return the tax1Amount
	 */
	public double getTax1Amount() {
		return tax1Amount;
	}
	/**
	 * @param tax1Amount the tax1Amount to set
	 */
	public void setTax1Amount(double tax1Amount) {
		this.tax1Amount = tax1Amount;
	}
	/**
	 * @return the tax2Amount
	 */
	public double getTax2Amount() {
		return tax2Amount;
	}
	/**
	 * @param tax2Amount the tax2Amount to set
	 */
	public void setTax2Amount(double tax2Amount) {
		this.tax2Amount = tax2Amount;
	}
	 
	/**
	 * @return the taxableValue
	 */
	public double getTaxableValue() {
		return taxableValue;
	}
	/**
	 * @param taxableValue the taxableValue to set
	 */
	public void setTaxableValue(double taxableValue) {
		this.taxableValue = taxableValue;
	}
	/**
	 * @return the netValue
	 */
	public double getNetValue() {
		return netValue;
	}
	/**
	 * @param netValue the netValue to set
	 */
	public void setNetValue(double netValue) {
		this.netValue = netValue;
	}
	/**
	 * @return the roundOff
	 */
	public double getRoundOff() {
		return roundOff;
	}
	/**
	 * @param roundOff the roundOff to set
	 */
	public void setRoundOff(double roundOff) {
		this.roundOff = roundOff;
	}
	/**
	 * @return the value
	 */
	public double getValue() {
		return value;
	}
	/**
	 * @param value the value to set
	 */
	public void setValue(double value) {
		this.value = value;
	}
	/**
	 * @return the itemDiscountName
	 */
	public String getItemDiscountName() {
		return itemDiscountName;
	}
	/**
	 * @param itemDiscountName the itemDiscountName to set
	 */
	public void setItemDiscountName(String itemDiscountName) {
		this.itemDiscountName = itemDiscountName;
	}
	/**
	 * @return the itemDiscountAmount
	 */
	public double getItemDiscountAmount() {
		return itemDiscountAmount;
	}
	/**
	 * @param itemDiscountAmount the itemDiscountAmount to set
	 */
	public void setItemDiscountAmount(double itemDiscountAmount) {
		this.itemDiscountAmount = itemDiscountAmount;
	}
	/**
	 * @return the itemDiscountIsPercentage
	 */
	public boolean isItemDiscountPercentage() {
		return itemDiscountIsPercentage;
	}
	/**
	 * @param itemDiscountIsPercentage the itemDiscountIsPercentage to set
	 */
	public void setItemDiscountIsPercentage(boolean itemDiscountIsPercentage) {
		this.itemDiscountIsPercentage = itemDiscountIsPercentage;
	}
	/**
	 * @return the itemDiscountPrice
	 */
	public double getItemDiscountPrice() {
		return itemDiscountPrice;
	}
	/**
	 * @param itemDiscountPrice the itemDiscountPrice to set
	 */
	public void setItemDiscountPrice(double itemDiscountPrice) {
		this.itemDiscountPrice = itemDiscountPrice;
	}
	/**
	 * @return the billDiscountPercentage
	 */
	public double getBillDiscountPercentage() {
		return billDiscountPercentage;
	}
	/**
	 * @param billDiscountPercentage the billDiscountPercentage to set
	 */
	public void setBillDiscountPercentage(double billDiscountPercentage) {
		this.billDiscountPercentage = billDiscountPercentage;
	}
	

}
