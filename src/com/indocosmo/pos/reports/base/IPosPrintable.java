/**
 * 
 */
package com.indocosmo.pos.reports.base;

import java.awt.print.Printable;

import javax.print.attribute.standard.OrientationRequested;

import com.indocosmo.pos.common.enums.device.PrinterType;

/**
 * @author jojesh-13.2
 * 
 * Interface to define Printer Type ( Receipt Printer for 80mm/ normal for A4  ) , 	
 * 		Paper size , Margins ,No. of copies and Page Orientation
 * 
 *
 */
public interface IPosPrintable extends Printable {

	/**
	 * @return PosReportPageFormat(class stores paper size and margins)
	 */
	public PosReportPageFormat getPosReportPageFormat();
	
	/**
	 * @return Enumerator PrinterType (80mm / Normal)
	 */
	public PrinterType getPrinterType();
	
	/**
	 * @return true / false , print based on this flag
	 */
	public boolean isPrintable();
	
	/**
	 * @return true /false ,allow multiple copies if it is true 
	 */
	public boolean canOverridePrinterSettings();
	
	/**
	 * @return no. of copies
	 */
	public int getCopies();
	
	
	/**
	 * @return OrientationRequested.LANDSCAPE / OrientationRequested.PORTRAIT
	 */
	public OrientationRequested getPrintingOrientation() ;
	
}
