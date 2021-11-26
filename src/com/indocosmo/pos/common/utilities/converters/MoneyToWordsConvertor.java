/**
 * 
 */
package com.indocosmo.pos.common.utilities.converters;

import com.indocosmo.pos.common.PosEnvSettings;

/**
 * @author jojesh
 *
 */
public final class MoneyToWordsConvertor {

	/**
	 * 
	 */
	public MoneyToWordsConvertor() {
		// TODO Auto-generated constructor stub
	}
	
	public static String toWords(double money){
		
		return toWords(String.valueOf(money));
		
	}
	
	
	public static String toWords(String value){
		
		String[] moneyParts=value.split("[.]");
		
		final long moneyPart=Long.valueOf(moneyParts[0]);
		
		final long moneyFraction=(moneyParts.length==2)?Long.valueOf(moneyParts[1]):0;
		
		final String moneyInWord=NumberToWordsConverter.toWords(moneyPart);
		final String moneyFractInWord=(moneyFraction>0)?NumberToWordsConverter.toWords(moneyFraction):"";
		final String moneySybol =PosEnvSettings.getInstance().getCurrency().getName();
		final String moneyFractSybol =PosEnvSettings.getInstance().getCurrency().getFractionName();
		
		return moneyInWord + " " +moneySybol+ ((moneyFraction>0 && !moneyFractInWord.equals("") )? moneyFractInWord +" "+moneyFractSybol:" Only")+".";
	}
	
}
