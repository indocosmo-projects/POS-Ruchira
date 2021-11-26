/**
 * 
 */
package com.indocosmo.pos.common.utilities;

import com.indocosmo.pos.common.PosEnvSettings;
import com.indocosmo.pos.common.utilities.converters.MoneyToWordsConvertor;
import com.indocosmo.pos.data.beans.BeanCurrency;
import com.indocosmo.pos.data.beans.BeanRounding;

/**
 * @author jojesh
 *
 */
public class PosCurrencyUtil {
	
	/**
	 * @param value
	 * @param prec
	 * @return
	 */
	public static double roundTo(double value, int prec) {
	
		return PosNumberUtil.roundTo(value, prec);
	}

	/**
	 * @param value
	 * @return
	 */
	public static double roundTo(double value) {
		
		BeanCurrency currnecy= PosEnvSettings.getInstance().getCurrency();
		final int prec = currnecy.getDecimalPlaces(); 
		
		return roundTo(value, prec);
	}

	/**
	 * @param amount
	 * @return
	 */
	public static double nRound(double amount){
		
		BeanCurrency currnecy= PosEnvSettings.getInstance().getCurrency();
		BeanRounding rounding=currnecy.getRounding();
		amount=PosNumberUtil.nRound(amount, rounding.getRoundTo());
		
		return amount; 
	}
	
	/**
	 * @param amount
	 * @return
	 */
	public static String format(double amount){
		
		return format(amount, false,false);
	}
	
	/**
	 * @param amount
	 * @param round
	 * @return
	 */
	public static String format(double amount,boolean round){
		
		return format(amount, round,false);
	}
	
	/**
	 * @param amount
	 * @param round
	 * @param appendSymbol
	 * @return
	 */
	public static String format(double amount, boolean round, boolean appendSymbol){
		
		
		final BeanCurrency currnecy= PosEnvSettings.getInstance().getCurrency();

		if(round)
			amount=roundTo(amount);
		
		final String formatedValue= ((appendSymbol)?currnecy.getCurrencySymbol():"")+ PosNumberUtil.format(amount,getCurrencyFormatString());
		
		return formatedValue;
	}
	
	/**
	 * @param prec
	 * @return
	 */
	public static String  getCurrencyFormatString(){
		
		final BeanCurrency currnecy= PosEnvSettings.getInstance().getCurrency();
		final int decimal=currnecy.getDecimalPlaces();
		
		return  PosNumberUtil.getDecimalFormatString(decimal);
	}
	
	/**
	 * @param money
	 * @return
	 */
	public static String toWords(double money){
		
		return MoneyToWordsConvertor.toWords(money);
	}
	
	/**
	 * @param money
	 * @return
	 */
	public static String toWords(String money){
		
		return MoneyToWordsConvertor.toWords(money);
	}
}
