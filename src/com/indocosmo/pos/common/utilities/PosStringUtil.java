package com.indocosmo.pos.common.utilities;

import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.io.InputStream;
import java.io.Reader;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

import javax.sql.rowset.CachedRowSet;

public final class PosStringUtil {

	public enum PaddingType{
		RIGHT,
		LEFT;
	}
	public static String truncateString(String string, int length){
		return (string.length()>length)?string.substring(0, length):string;
	}
	/**
	 * 
	 * @param value
	 * @return
	 */
	public static String getCommaSeperatedList(String value[]) {
		String csl="";
		if(value!=null && value.length>0 ){
			csl=Arrays.toString(value);
			csl=csl.substring(1, csl.length()-1);
		}
		return csl;
	}
	/**
	 * 
	 * @param text
	 * @param length
	 * @param withChar
	 * @return
	 */
	public static String paddRight(String text, int length, char withChar){
		return String.format("%1$-"+(length)+"s", text).replace(' ', withChar);
	}
	/**
	 * 
	 * @param text
	 * @param length
	 * @param withChar
	 * @return
	 */
	public static String paddLeft(String text, int length, char withChar){
		return String.format("%1$"+length+"s", text).replace(' ', withChar);
	}
	/**
	 * Creates fixed length string field from the text passed;
	 * @param text
	 * @param length
	 * @param pType
	 * @return
	 */
	public static String setToFixedLengthString(String text,int length,PaddingType pType){
		if(text.length()>length)
			text=text.substring(0,length);
		return (pType==PaddingType.LEFT)?
				paddLeft(text, length, ' '):
					paddRight(text, length, ' ');
	}
	
	public static String[] getWrappedLines(String input, int maxLineLength) {
	    StringTokenizer tok = new StringTokenizer(input, " ");
	    StringBuilder output = new StringBuilder(input.length());
	    int lineLen = 0;
	    while (tok.hasMoreTokens()) {
	        String word = tok.nextToken() + " ";

	        if (lineLen + word.length() > maxLineLength) {
	            output.append("\n");
	            lineLen = 0;
	        }
	        output.append(word);
	        lineLen += word.length();
	    }
	    return output.toString().split("\n");
	}
	
	/**
	 * @param input
	 * @param g
	 * @param maxLineLength
	 * @return
	 */
	public static String[] getWrappedLines(String input,Graphics g, int maxLineLength) {
		
	    StringTokenizer tok = new StringTokenizer(input, " ");
	    StringBuilder output = new StringBuilder(input.length());
	    FontMetrics fm=g.getFontMetrics();
	    
	    int lineLen = 0;
	    while (tok.hasMoreTokens()) {
	        String word = tok.nextToken() + " ";

	        if ((lineLen + fm.stringWidth(word) )> (maxLineLength)) {
	            output.append("\n");
	            lineLen = 0;
	        }
	        output.append(word);
	        lineLen += fm.stringWidth(word);
	    }
	    return output.toString().split("\n");
	}
	
	/**
	 * @param g
	 * @param text
	 * @param rect
	 */
	public static void drawTextCentered(Graphics g, String text, Rectangle rect) {
	
		final FontMetrics metrics = g.getFontMetrics();
		final int x = (rect.width - metrics.stringWidth(text)) / 2;
		final int y = ((rect.height - metrics.getHeight()) / 2) + metrics.getAscent();
		
	    g.drawString(text, rect.x+x, rect.y+y);
	}
	
	/**
	 * @param rs
	 * @param columnIndex
	 * @return
	 * @throws SQLException
	 */
	public static String getLargerString(CachedRowSet rs, String columnIndex) throws SQLException {

	    InputStream in = null;
	    int BUFFER_SIZE = 1024;
	    try {
	      in = rs.getAsciiStream(columnIndex);
	      if (in == null) {
	        return "";
	      }

	      byte[] arr = new byte[BUFFER_SIZE];
	      StringBuffer buffer = new StringBuffer();
	      int numRead = in.read(arr);
	      while (numRead != -1) {
	        buffer.append(new String(arr, 0, numRead));
	        numRead = in.read(arr);
	      }
	      return buffer.toString();
	    } catch (Exception e) {
	      e.printStackTrace();
	      throw new SQLException(e.getMessage());
	    }
	  }
	
	/**
	 * @param rs
	 * @param columnIndex
	 * @return
	 * @throws SQLException
	 */
	public static String getLargerString2(CachedRowSet rs, String columnIndex) throws Exception {

	
	StringBuilder sb = new StringBuilder();
	Reader in = rs.getCharacterStream(columnIndex);
	int buf = -1;
	while((buf = in.read()) > -1) {
	      sb.append((char)buf);
	}
	in.close();
	String text = sb.toString();
	return text;
	}
	
	/**
	 * @param str
	 * @return
	 */
	public static String escapeSpecialRegexChars(String str) {

		Pattern SPECIAL_REGEX_CHARS = Pattern.compile("[{}()\\[\\].+*?^$\\\\|]");
	    return SPECIAL_REGEX_CHARS.matcher(str).replaceAll("\\\\$0");
	}
	/**
	 * @param input
	 * @param g
	 * @param maxLineLength
	 * @return
	 */
	public static  int getStringLength(String input,Graphics g ) {
		
	    StringTokenizer tok = new StringTokenizer(input, " ");
	    FontMetrics fm=g.getFontMetrics();
	    
	    int lineLen = 0;
	    while (tok.hasMoreTokens()) {
	       
	    	String word = tok.nextToken().replaceAll("&nbsp;", "").replace("html", "").replace("body", "").replace("font", "");  
	        lineLen += fm.stringWidth(word);
	    }
	    return lineLen;
	}
}

