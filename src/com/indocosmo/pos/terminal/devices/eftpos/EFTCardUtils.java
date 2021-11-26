/**
 * 
 */
package com.indocosmo.pos.terminal.devices.eftpos;

import com.indocosmo.pos.terminal.devices.eftpos.dataobjects.EFTCreditCardObject;

/**
 * @author jojesh
 * 
 */
public final class EFTCardUtils {

	public static EFTCreditCardObject getCreditCardDetails(String rawData) {

		EFTCreditCardObject eftCCObj = new EFTCreditCardObject();
		boolean CaretPresent = false;
		boolean EqualPresent = false;

		CaretPresent = rawData.contains("^");
		EqualPresent = rawData.contains("=");

		if (CaretPresent) {
			String[] CardData1 = rawData.split("\\^");
			// B1234123412341234^CardUser/John^030510100000019301000000877000000?
			eftCCObj.setHolderName(FormatName(CardData1[1]));
			eftCCObj.setCreditCardNumber(FormatCardNumber(CardData1[0]));
			eftCCObj.setExpMM(CardData1[2].substring(2, 4));
			eftCCObj.setExpYY(CardData1[2].substring(0, 2));
		} else if (EqualPresent) {
			String[] CardData2 = rawData.split("=");
			// B1234123412341234^CardUser/John^030510100000019301000000877000000?
			eftCCObj.setCreditCardNumber(FormatCardNumber(CardData2[0]));
			eftCCObj.setExpMM(CardData2[1].substring(2, 4));
			eftCCObj.setExpYY(CardData2[1].substring(0, 2));
		}else
			eftCCObj.setCreditCardNumber(rawData);
		return eftCCObj;
	}

	private static String FormatCardNumber(String number) {
		String result = "";
		result = number.replaceAll("[^0-9]", "");
		return result;
	}

	private static String FormatName(String name) {
		String result = "";
		if (name.contains("/")) {
			String[] NameSplit = name.split("/",2);
			result = NameSplit[1] + " " + NameSplit[0];
		} else {
			result = name;
		}
		return result;
	}
}
