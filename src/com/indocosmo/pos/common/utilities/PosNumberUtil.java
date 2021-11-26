package com.indocosmo.pos.common.utilities;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import javax.swing.JTextField;
import javax.swing.text.Document;
import javax.swing.text.DocumentFilter;
import javax.swing.text.PlainDocument;

import com.indocosmo.pos.common.PosEnvSettings;

public final class PosNumberUtil {

	private static NumberFormat mNumberFormat;

	public static double parseDoubleSafely(String str) {
		double result = 0;
		try {
			result = Double.parseDouble(str);
		} catch (Exception e) {

		}
		return result;
	}

	public static int parseIntegerSafely(String str) {
		int result = 0;
		try {
			result = Integer.valueOf(str.trim());
		} catch (Exception e) {

		}
		return result;
	}
	public static String format(double number) {
		return format(String.valueOf(number));
	}

	public static String format(String number) {
		final String format = getDecimalFormatString();
		return format(number, format);
	}

	public static String format(String number, String format) {
		mNumberFormat = new DecimalFormat(format);
		String formatValue = mNumberFormat.format(Double.parseDouble(number));
		return formatValue;
	}
	
	public static String format(double number, String format) {
		mNumberFormat = new DecimalFormat(format);
		String formatValue = mNumberFormat.format(number);
		return formatValue;
	}
	
	public static double roundTo(double value, int prec) {
		
		return roundTo( value,  prec,RoundingMode.HALF_UP);
		
	}

	public static double roundTo(double value, int prec,RoundingMode rmode) {

		final String decimalFormat = getDecimalFormatString(prec);
		final DecimalFormat df=new DecimalFormat(decimalFormat);
		if(rmode!=RoundingMode.UNNECESSARY)
			df.setRoundingMode(rmode);
	System.out.println("value============================>"+value);
	//System.out.println("doblvalue============================>"+Double.valueOf(df.format(value)));
	
		return Double.valueOf(df.format(value));
	}

	public static double roundTo(double value) {
		final int prec = PosEnvSettings.getInstance().getDecimalRounding();
		return roundTo(value, prec);
	}

	public static String getDecimalFormatString() {
		final int prec = PosEnvSettings.getInstance().getDecimalRounding();
		return getDecimalFormatString(prec);
	}

	public static String getDecimalFormatString(int prec) {
		String actualPrec = String.valueOf(prec + ((prec>0)?2:1));
		String decimalFormat = String.format("%-" + actualPrec + "s", (prec>0)?"0.":"0")
				.replace(' ', '0');
		return decimalFormat;
	}

	
	public static double nRound(double value, double nearest) {
		double result = roundTo(value / nearest, 0,RoundingMode.UNNECESSARY) * nearest;
		return result;
	}

	public static double nRound(double value) {
		double nearest = PosEnvSettings.getInstance().getRoundingRule();
		return nRound(value, nearest);
	}

	public static boolean isInteger(String value) {

		boolean res=false;
		Integer val=null;
		try{
			
			val=Integer.parseInt(value);
			res= (val!=null);
			
		}catch(NumberFormatException e){
			
			res= false;
		}
	
		return res;
	}

	public static boolean isDouble(String value) {

		boolean res=false;
		Double val=null;
		try{
			
			val=Double.parseDouble(value);
			res= (val!=null);
			
		}catch(NumberFormatException e){
			
			res= false;
		}
	
		return res;
	}

	public static double getValueFormUIComponenet(JTextField inputField) {
		return getValueFormUIComponenet(inputField, true);
	}

	public static double getValueFormUIComponenet(JTextField inputField,
			boolean showMessage) {
		double value = 0;
		try {
			value = Double.parseDouble(inputField.getText());
		} catch (Exception e) {
			if (showMessage)
				PosFormUtil
						.showErrorMessageBox(null,
								"The entered value is not a valid entry. Please correct it.");
			inputField.requestFocus();
		}
		return value;
	}

	// Number Document.
	private static final java.util.Locale LOCALE = java.util.Locale.US;
	private static final java.text.DecimalFormatSymbols SYMBOLS = new java.text.DecimalFormatSymbols(
			LOCALE);

	private static boolean containsDecimal(Document doc, int off, int len)
			throws javax.swing.text.BadLocationException {
		final char decimalSeparator = '.';// SYMBOLS.getDecimalSeparator();

		final String text = doc.getText(off, len);
		return text.indexOf(decimalSeparator) != -1;
	}

	public static Document createNumericDocument() {
		return createNumericDocument(true);
	}

	public static Document createNumericDocument(boolean allowMinus) {
		Document doc = null;
		if (allowMinus) {
			doc = createNumberDocument(true, true);
		} else {
			doc = createNumberDocument(true, false);
		}
		return doc;
	}

	public static Document createDecimalDocument() {
		return createDecimalDocument(true);
	}

	public static Document createDecimalDocument(boolean allowMinus) {
		Document doc = null;
		if (allowMinus) {
			doc = createNumberDocument(false, true);
		} else {
			doc = createNumberDocument(false, false);
		}
		return doc;
	}
	

	public static Document createDigitalDocument(){
		return createNumberDocument(true, false);
	}
	
		
	private static Document createNumberDocument(final boolean filterDecimal,
			final boolean allowMinus) {
		javax.swing.text.AbstractDocument doc = new PlainDocument();
		doc.setDocumentFilter(new DocumentFilter() {
			public void insertString(DocumentFilter.FilterBypass bypass,
					int off, String string, javax.swing.text.AttributeSet attr)
					throws javax.swing.text.BadLocationException {
				final Document bypassDoc = bypass.getDocument();
				final int bypassDocLen = bypassDoc.getLength();
				final boolean containsDecimal = filterDecimal
						|| containsDecimal(bypassDoc, 0, bypassDocLen);
				if (!(string.equals("-") || string.equals("+"))) {
					bypass.insertString(
							off,
							filterPositiveNumber(string, !containsDecimal,
									allowMinus), attr);
				} else if (allowMinus) {
					if ((bypassDoc.getText(0, 1).equals("+") || bypassDoc
							.getText(0, 1).equals("-")))
						bypass.remove(0, 1);
					bypass.replace(0, 1, string, attr);
				}

			}

			public void remove(DocumentFilter.FilterBypass bypass, int offset,
					int length) throws javax.swing.text.BadLocationException {
				bypass.remove(offset, length);
			}

			public void replace(DocumentFilter.FilterBypass bypass, int off,
					int len, String str, javax.swing.text.AttributeSet attr)
					throws javax.swing.text.BadLocationException {
				final Document bypassDoc = bypass.getDocument();
				final int bypassDocLen = bypassDoc.getLength();
				final boolean containsDecimal = filterDecimal
						|| containsDecimal(bypassDoc, 0, off)
						|| containsDecimal(bypassDoc, off + len, bypassDocLen
								- (off + len));
				if (!(str.equals("-") || str.equals("+"))) {
					bypass.replace(
							off,
							len,
							filterPositiveNumber(str, !containsDecimal,
									allowMinus), attr);
				} else if (allowMinus) {
					if (bypassDoc.getText(0, 1).equals("+")
							|| bypassDoc.getText(0, 1).equals("-"))
						bypass.remove(0, 1);
					if (str.equals("-"))
						bypass.replace(0, len, str, attr);
				}
			}
		});
		return doc;
	}

	private static String filterPositiveNumber(final String string,
			final boolean allowDecimal, final boolean allowMinus) {
		// final char groupSeparator = SYMBOLS.getGroupingSeparator();
		final char decimalSeparator = SYMBOLS.getDecimalSeparator();
		final StringBuilder buff = new StringBuilder();
		boolean moreDecimal = allowDecimal;
		for (final char c : string.toCharArray()) {
			if (Character.isDigit(c) || c == '-' && allowMinus || c == '+') {
				buff.append(c);
			} else if (c == decimalSeparator && moreDecimal) {
				moreDecimal = false;
				buff.append(c);
			} else {
				// Cut.
			}
		}
		return buff.toString();
	}
/** Number to word functions**/
	
	private static final String[] TENS_WORDS = { "", " ten", " twenty",
			" thirty", " forty", " fifty", " sixty", " seventy", " eighty",
			" ninety" };

	private static final String[] NUME_WORDS = { "", " one", " two", " three",
			" four", " five", " six", " seven", " eight", " nine", " ten",
			" eleven", " twelve", " thirteen", " fourteen", " fifteen",
			" sixteen", " seventeen", " eighteen", " nineteen" };

	private static String convertLessThanOneThousand(int number) {
		String soFar;

		if (number % 100 < 20) {
			soFar = NUME_WORDS[number % 100];
			number /= 100;
		} else {
			soFar = NUME_WORDS[number % 10];
			number /= 10;

			soFar = TENS_WORDS[number % 10] + soFar;
			number /= 10;
		}
		if (number == 0)
			return soFar;
		return NUME_WORDS[number] + " hundred" + soFar;
	}

	public static String toWords(long number) {
		// 0 to 999 999 999 999
		if (number == 0) {
			return "zero";
		}

		String snumber = Long.toString(number);

		// pad with "0"
		String mask = "000000000000";
		DecimalFormat df = new DecimalFormat(mask);
		snumber = df.format(number);

		// XXXnnnnnnnnn
		int billions = Integer.parseInt(snumber.substring(0, 3));
		// nnnXXXnnnnnn
		int millions = Integer.parseInt(snumber.substring(3, 6));
		// nnnnnnXXXnnn
		int hundredThousands = Integer.parseInt(snumber.substring(6, 9));
		// nnnnnnnnnXXX
		int thousands = Integer.parseInt(snumber.substring(9, 12));

		String tradBillions;
		switch (billions) {
		case 0:
			tradBillions = "";
			break;
		case 1:
			tradBillions = convertLessThanOneThousand(billions) + " billion ";
			break;
		default:
			tradBillions = convertLessThanOneThousand(billions) + " billion ";
		}
		String result = tradBillions;

		String tradMillions;
		switch (millions) {
		case 0:
			tradMillions = "";
			break;
		case 1:
			tradMillions = convertLessThanOneThousand(millions) + " million ";
			break;
		default:
			tradMillions = convertLessThanOneThousand(millions) + " million ";
		}
		result = result + tradMillions;

		String tradHundredThousands;
		switch (hundredThousands) {
		case 0:
			tradHundredThousands = "";
			break;
		case 1:
			tradHundredThousands = "one thousand ";
			break;
		default:
			tradHundredThousands = convertLessThanOneThousand(hundredThousands)
					+ " thousand ";
		}
		result = result + tradHundredThousands;

		String tradThousand;
		tradThousand = convertLessThanOneThousand(thousands);
		result = result + tradThousand;

		// remove extra spaces!
		return result.replaceAll("^\\s+", "").replaceAll("\\b\\s{2,}\\b", " ");
	}

}
