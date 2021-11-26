package com.indocosmo.pos.terminal.devices;

import java.util.StringTokenizer;
import java.util.regex.Pattern;

public final class PosDeviceUtil {

	/**
	 * Converts the given value to hexadecimal formats
	 * 
	 * @param value
	 *            to be converted to hexadecimal
	 * @return the hexadecimal representation of give value
	 */
	public static Byte getHexValue(int value) {
		final String hexString = Integer.toHexString(value);
		String formatedHexValue = "0x"
				+ ((hexString.length() < 2) ? String.format("%2s", hexString)
						.replace(" ", "0") : hexString);
		return Byte.decode(formatedHexValue);
	}

	
	
	// Checks if passed string is Hexadecimal (comma is excluded).
	public static boolean isHexaDecimal(String string) {
		Pattern hex = Pattern.compile("^[,0-9A-Fa-f]+$");
		return hex.matcher(string).matches();
	}
	
	
	
    //Pad hexa symbol '0x' with elements
	private static String getHexaCommand(String hexaCode) throws Exception {

		//Clean spaces and "0x" from the string
    	String strFormat = hexaCode.replaceAll("\\s","");
    	strFormat = strFormat.replaceAll("0x", "");

    	if (!PosDeviceUtil.isHexaDecimal(strFormat)) {
    		throw new Exception("Invalid Hexadecimal format.");
    	}

    	//Pad "0x" character 
    	char[] charArr = strFormat.toCharArray();
        int varLength = charArr.length;
        StringBuffer sbVar = new StringBuffer("0x");

        for (int ctr=0; ctr < varLength; ctr++) {
        	if (charArr [ctr] == ',') {
        			sbVar.append(",0x");	
        	} else {
        		sbVar.append(charArr[ctr]);
        	}
        }
        return sbVar.toString();
    }    
	
	
	
	//Convert hexadecimal to bytes	
	public static byte[] stringToByteArr(String paddedString) throws Exception {
		byte[] byteArr = null;
		StringTokenizer st = new StringTokenizer(getHexaCommand(paddedString), ",");
		int length = st.countTokens();
		if (length > 8) {
			throw new Exception("Unable to handle more than 8 Bytes instruction.");
		}
		
		if (length == 1) {
			byte var1 = Byte.decode(st.nextToken().toString());
			byteArr = new byte[] {var1};
			
		} else if (length == 2) {
			byte var1 = Byte.decode(st.nextToken().toString());
			byte var2 = Byte.decode(st.nextToken().toString());
			byteArr = new byte[] {var1, var2};

		} else if (length == 3) {
			byte var1 = Byte.decode(st.nextToken().toString());
			byte var2 = Byte.decode(st.nextToken().toString());
			byte var3 = Byte.decode(st.nextToken().toString());
			byteArr = new byte[] {var1, var2, var3};

		} else if (length == 4) {
			byte var1 = Byte.decode(st.nextToken().toString());
			byte var2 = Byte.decode(st.nextToken().toString());
			byte var3 = Byte.decode(st.nextToken().toString());
			byte var4 = Byte.decode(st.nextToken().toString());
			byteArr = new byte[] {var1, var2, var3, var4};

		} else if (length == 5) {
			byte var1 = Byte.decode(st.nextToken().toString());
			byte var2 = Byte.decode(st.nextToken().toString());
			byte var3 = Byte.decode(st.nextToken().toString());
			byte var4 = Byte.decode(st.nextToken().toString());
			byte var5 = Byte.decode(st.nextToken().toString());
			byteArr = new byte[] {var1, var2, var3, var4, var5};

		} else if (length == 6) {
			byte var1 = Byte.decode(st.nextToken().toString());
			byte var2 = Byte.decode(st.nextToken().toString());
			byte var3 = Byte.decode(st.nextToken().toString());
			byte var4 = Byte.decode(st.nextToken().toString());
			byte var5 = Byte.decode(st.nextToken().toString());
			byte var6 = Byte.decode(st.nextToken().toString());
			byteArr = new byte[] {var1, var2, var3, var4, var5, var6};

		} else if (length == 7) {
			byte var1 = Byte.decode(st.nextToken().toString());
			byte var2 = Byte.decode(st.nextToken().toString());
			byte var3 = Byte.decode(st.nextToken().toString());
			byte var4 = Byte.decode(st.nextToken().toString());
			byte var5 = Byte.decode(st.nextToken().toString());
			byte var6 = Byte.decode(st.nextToken().toString());
			byte var7 = Byte.decode(st.nextToken().toString());
			byteArr = new byte[] {var1, var2, var3, var4, var5, var6, var7};

		} else if (length == 8) {
			byte var1 = Byte.decode(st.nextToken().toString());
			byte var2 = Byte.decode(st.nextToken().toString());
			byte var3 = Byte.decode(st.nextToken().toString());
			byte var4 = Byte.decode(st.nextToken().toString());
			byte var5 = Byte.decode(st.nextToken().toString());
			byte var6 = Byte.decode(st.nextToken().toString());
			byte var7 = Byte.decode(st.nextToken().toString());
			byte var8 = Byte.decode(st.nextToken().toString());
			byteArr = new byte[] {var1, var2, var3, var4, var5, var6, var7, var8};
		}
		return byteArr;
	}
}
