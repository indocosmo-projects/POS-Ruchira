package com.indocosmo.pos.common.utilities;

import com.indocosmo.pos.common.PosEnvSettings;
import com.indocosmo.pos.common.enums.CardTypes;
import com.indocosmo.pos.data.beans.BeanCardType;

public final class PosCardUtil {

	public static String getPosCardNumber(CardTypes cardType, String cardNumber){
		String actualCardNumber=null;
		BeanCardType crdDtl=PosEnvSettings.getInstance().getCardTypes().get(cardType);
		if(checkForValidPosCard( cardType,  cardNumber))
			actualCardNumber=cardNumber.substring(crdDtl.getCharPrefix().length(), cardNumber.length()-crdDtl.getCharSuffix().length());
		else
			actualCardNumber=cardNumber;
		return actualCardNumber;
	}
	
	public static boolean checkForValidPosCard(CardTypes cardType, String cardNumber){
		BeanCardType crdDtl=PosEnvSettings.getInstance().getCardTypes().get(cardType);
		return (cardNumber.startsWith(crdDtl.getCharPrefix()) && cardNumber.endsWith(crdDtl.getCharSuffix()));
	}
	
	public static CardTypes getCardTypeFromCardNumber(String cardNumber){
		CardTypes cardType=null;
		for(CardTypes type:CardTypes.values()){
			if(checkForValidPosCard(type,cardNumber)){
				cardType=type;
				break;
			}
		}
		return cardType;
	}
	
}
