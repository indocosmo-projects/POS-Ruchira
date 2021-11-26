/**
 * 
 */
package com.indocosmo.pos.reports.receipts;

import java.awt.Graphics2D;
import java.awt.print.PageFormat;

import com.indocosmo.pos.reports.base.PosPrintableReportBase;
import com.indocosmo.pos.reports.base.PosReportPageFormat;

/**
 * @author jojesh-13.2
 *
 */
public class PosMultiPageTest extends PosPrintableReportBase {

	int maxRows=1000;
	int max_lines_page=45;
	int curRow=0;
	/* (non-Javadoc)
	 * @see com.indocosmo.pos.reports.base.PosPrintableBase#onInitPrint(java.awt.Graphics2D, java.awt.print.PageFormat)
	 */
	@Override
	protected void onInitialized(Graphics2D g2d, PageFormat pf) throws Exception {
		
	}
	
	/* (non-Javadoc)
	 * @see com.indocosmo.pos.reports.base.PosPrintableReportBase#onReportInit()
	 */
	@Override
	protected void initReport() {
		// TODO Auto-generated method stub
		
	}
	
	/* (non-Javadoc)
	 * @see com.indocosmo.pos.reports.base.PosPrintableReportBase#pageInit()
	 */
	@Override
	protected void initPage(int pageIndex) {
		
		curRow=max_lines_page*pageIndex;
	}

	
	/* (non-Javadoc)
	 * @see com.indocosmo.pos.reports.base.PosPrintableReportBase#printReportHeader(int)
	 */
	@Override
	protected void printReportHeader(int pageIndex) {
		
		setFontSize(15f);
		printTextBlock(0, getWidth(), "THIS IS REPORT HEADER", TextAlign.CENTER, true);
		
		printLine(0, getNextLineStartY(), getWidth(), getNextLineStartY());
		
	}
	
	/* (non-Javadoc)
	 * @see com.indocosmo.pos.reports.base.PosPrintableReportBase#printPageHeader(int)
	 */
	@Override
	protected void printPageHeader(int pageIndex) {
		
		setFontSize(12f);
		printTextBlock(0, getWidth(), "THIS IS PAGE HEADER", TextAlign.CENTER, true);
		
		printLine(0, getNextLineStartY(), getWidth(), getNextLineStartY());
	}
	
	/* (non-Javadoc)
	 * @see com.indocosmo.pos.reports.base.PosPrintableReportBase#printDetails(int)
	 */
	@Override
	protected void printPageDetails(int pageIndex) {
		
		int line_no=0;
		for(;curRow<=maxRows;curRow++){
			
			printText(TextAlign.LEFT, "This is Row No : " + curRow);
			line_no++;
			if(line_no>=max_lines_page) break;
			
		}
			
	}
	
	/* (non-Javadoc)
	 * @see com.indocosmo.pos.reports.base.PosPrintableReportBase#printPageFooter(int)
	 */
	@Override
	protected void printPageFooter(int pageIndex) {
		
		printLine(0, getNextLineStartY(), getWidth(), getNextLineStartY());
		
		setFontSize(12f);
		printTextBlock(0, getWidth(), "THIS IS PAGE FOOTER", TextAlign.CENTER, true);
		
		
	}
	
	/* (non-Javadoc)
	 * @see com.indocosmo.pos.reports.base.PosPrintableReportBase#printReportFooter(int)
	 */
	@Override
	protected void printReportFooter(int pageIndex) {
		
		printLine(0, getNextLineStartY(), getWidth(), getNextLineStartY());
		setFontSize(15f);
		printTextBlock(0, getWidth(), "THIS IS REPORT FOOTER", TextAlign.CENTER, true);
	}


	/* (non-Javadoc)
	 * @see com.indocosmo.pos.reports.base.PosPrintableReportBase#hasMoreData(int)
	 */
	@Override
	protected boolean isPageExist(int pageIndex) {
		
		return max_lines_page*pageIndex<maxRows;
	}
	
	/* (non-Javadoc)
	 * @see com.indocosmo.pos.reports.base.PosPrintableBase#getPageFormat()
	 */
	/* (non-Javadoc)
	 * @see com.indocosmo.pos.reports.base.PosPrintableReportBase#getPosReportPageFormat()
	 */
	@Override
	public PosReportPageFormat getPosReportPageFormat() {

		return PosReportPageFormat.PAGE_A4;
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.pos.reports.base.PosPrintableReportBase#onPageChanged(int, int)
	 */
	@Override
	protected void onPageChanged(int oldPage, int newPage) throws Exception {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.pos.reports.base.PosPrintableReportBase#hasMoreData()
	 */
	@Override
	protected boolean hasMoreData() throws Exception {
		// TODO Auto-generated method stub
		return false;
	}


}
