/**
 * 
 */
package com.indocosmo.pos.common.utilities.codescanner.bean;

/**
 * @author jojesh
 *
 */
public class BeanWeighingBarcodeDtl {
	
	/**
	 * @author jojesh
	 *
	 */
	public enum CodeType{
		
		BarCode,
		ItemCode,
		Any
	}
	
	/**
	 * @author jojesh
	 *
	 */
	public enum MeasuringType{
		
		Count,
		Weighing
	}
	
	private MeasuringType measuringType=MeasuringType.Weighing;
	private CodeType codeType=CodeType.Any;
	private String code;
	private double value;
	
	/**
	 * @return the productCode
	 */
	public String getCode() {
		return code;
	}
	/**
	 * @param productCode the productCode to set
	 */
	public void setCode(String productCode) {
		this.code = productCode;
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
	 * @return the codeType
	 */
	public CodeType getCodeType() {
		return codeType;
	}
	/**
	 * @param codeType the codeType to set
	 */
	public void setCodeType(CodeType codeType) {
		this.codeType = codeType;
	}
	/**
	 * @return the measuringType
	 */
	public MeasuringType getMeasuringType() {
		return measuringType;
	}
	/**
	 * @param measuringType the measuringType to set
	 */
	public void setMeasuringType(MeasuringType measuringType) {
		this.measuringType = measuringType;
	}

}
