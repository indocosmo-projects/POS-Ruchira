/**
 * 
 */
package com.indocosmo.pos.common.exception.printing;

import java.awt.print.PrinterException;


/**
 * @author anand
 *
 */
public class HasNoItemsToPrintException extends PrinterException{


	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public HasNoItemsToPrintException(String message){
		super(message);
	}
}
