/**
 * 
 */
package com.indocosmo.pos.reports.printertest;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;

import com.indocosmo.pos.common.utilities.PosStringUtil;
import com.indocosmo.pos.reports.base.PosPrintableBase;

/**
 * @author deepak
 *
 */
public class PosPrinterTest extends PosPrintableBase{

	private Font mFontReceipt;

	/* (non-Javadoc)
	 * @see com.indocosmo.pos.reports.PosPrintableReportBase#printReport()
	 */
	@Override
	protected int printReport(int pageIndex) throws Exception {

		String line_0 = "POS Ver.4.0.0";
		String line_1 = "Printer Test utility";
		
		String line = "If you can read this statement, it is proven that you have configured the printer successfully.";
		
		setFontStyle(Font.BOLD);
		setFontSize(10.0f);
		printText(TextAlign.CENTER,line_0);
		printText(TextAlign.CENTER,line_1);
		advanceLine(5);
		setFont(mFontReceipt);
		printThickLine();
		advanceLine(3);
		printDoubleLine();
		advanceLine(3);
		printSingleLine();
		advanceLine(3);
		printDashedLine();
		advanceLine(5);

		String[] splitedLines=PosStringUtil.getWrappedLines(line,getGraphics(),getPosReportPageFormat().getImageableWidthInPixcel()); 
		for(int index=0;index<splitedLines.length;index++)
			printText(TextAlign.LEFT,splitedLines[index]);

		advanceLine(10);
		printDashedLine();
		advanceLine(3);
		printSingleLine();
		advanceLine(3);
		printDoubleLine();
		advanceLine(3);
		printThickLine();
		advanceLine(20);
		printText(TextAlign.CENTER, ".");
		
		return (pageIndex>0)?NO_SUCH_PAGE:PAGE_EXISTS;
		
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.pos.reports.base.PosPrintableBase#onInitialized(java.awt.Graphics2D, java.awt.print.PageFormat)
	 */
	@Override
	protected void onInitialized(Graphics2D g2d, PageFormat pf)
			throws Exception {
		mFontReceipt = new Font("Ariel", Font.PLAIN, 9);
		
	}

}
