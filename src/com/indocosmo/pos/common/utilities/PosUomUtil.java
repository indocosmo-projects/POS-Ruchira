/**
 * 
 */
package com.indocosmo.pos.common.utilities;

import com.indocosmo.pos.common.utilities.converters.MoneyToWordsConvertor;
import com.indocosmo.pos.data.beans.BeanUOM;

/**
 * @author jojesh
 *
 */
public class PosUomUtil {
	
	/**
	 * @param value
	 * @param prec
	 * @return
	 */
	public static double roundTo(double value, BeanUOM uom) {
	
		
		if(uom!=null)
			value=PosNumberUtil.roundTo(value, uom.getDecimalPlaces());
		else
			value=PosNumberUtil.roundTo(value);
		
		return value;
	}


	/**
	 * @param amount
	 * @return
	 */
	public static String format(double amount, BeanUOM uom){
		
		return format(amount, true,uom);
	}
	
	/**
	 * @param amount
	 * @param round
	 * @return
	 */
	public static String format(double amount,boolean round, BeanUOM uom){
		
		return format(amount, round,false,uom);
	}
	
	/**
	 * @param amount
	 * @param round
	 * @param appendSymbol
	 * @return
	 */
	public static String format(double amount, boolean round, boolean appendSymbol, BeanUOM uom){

		if(round)
			amount=roundTo(amount,uom);
		
		final String formatedValue= ((appendSymbol)?uom.getSymbol():"")+ PosNumberUtil.format(amount,getFormatString(uom));
		
		return formatedValue;
	}
	
	/**
	 * @param prec
	 * @return
	 */
	public static String  getFormatString(BeanUOM uom){
		
		final int decimal=uom.getDecimalPlaces();
		
		return  PosNumberUtil.getDecimalFormatString(decimal);
	}
	
	/**
	 * @param money
	 * @return
	 */
	public static String toWords(double value){
		
		return MoneyToWordsConvertor.toWords(value);
	}
	
	/**
	 * @param money
	 * @return
	 */
	public static String toWords(String value){
		
		return MoneyToWordsConvertor.toWords(value);
	}
	
	
}
