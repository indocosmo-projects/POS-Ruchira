/**
 * 
 */
package com.indocosmo.pos.test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author jojesh
 *
 */
public class RegexMatches {


	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// String to be scanned to find the pattern.
	      String line = "$123456%";
//	      String pattern = "^222(\\d{4})(\\d{1})(\\d{4})$";
//	      String pattern = "^\\#(\\d+)%$";
	      String pattern = "^\\$(\\d+)%$";

	      // Create a Pattern object
	      Pattern r = Pattern.compile(pattern);

	      // Now create matcher object.
	      Matcher m = r.matcher(line);
	      if (m.find( )) {
	    	  for(int i=0; i<=m.groupCount(); i++)
	         System.out.println("Found value: " + m.group(i) );
	         
	      }else {
	         System.out.println("NO MATCH");
	      }

	}

}
