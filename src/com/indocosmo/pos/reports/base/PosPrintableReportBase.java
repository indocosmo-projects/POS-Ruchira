/**
 * 
 */
package com.indocosmo.pos.reports.base;

import java.awt.Font;

/**
 * @author jojesh
 * 
 */
public abstract class PosPrintableReportBase extends PosPrintableBase {

	protected Font mFontReceipt;
	protected boolean mUseAltLanguge=false;
	private int currentPageIndex=-1;
	/**
	 * 
	 */
	public PosPrintableReportBase() {

		super();
		mFontReceipt = new Font("Ariel", Font.PLAIN, 8);
	}
	
	int pageStatus=0;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.indocosmo.pos.reports.PosPrintableReportBase#printReport(java.awt
	 * .Graphics, java.awt.print.PageFormat)
	 */
	@Override
	protected int printReport(int pageIndex) throws Exception {
		
		if(currentPageIndex!=pageIndex){

			onPageChanged(currentPageIndex,pageIndex);
			pageStatus=(isPageExist(pageIndex)?PAGE_EXISTS:NO_SUCH_PAGE);
			if(pageStatus==NO_SUCH_PAGE) {
				currentPageIndex=-1;
				return NO_SUCH_PAGE;
			}
			currentPageIndex=pageIndex;
		}

		initPage(pageIndex);
		
		if(pageIndex==0){
			initReport();
			printReportHeader(pageIndex);
		}

		printPageHeader(pageIndex);
		printPageDetails(pageIndex);
		printPageFooter(pageIndex);

		if(!hasMoreData())
			printReportFooter(pageIndex);

		return pageStatus;
	}


	/**
	 * @return
	 */
	protected abstract boolean hasMoreData() throws Exception ;


	/**
	 * @param currentPageIndex2
	 * @param pageIndex
	 */
	protected abstract void onPageChanged(int oldPage, int newPage) throws Exception ;

	/**isInitialized
	 * 
	 */
	protected abstract void initReport() ;

	/**
	 * Initialize all page variable
	 * 
	 */
	protected abstract void initPage(int pageIndex) throws Exception ;


	/**
	 * @param pageIndex
	 */
	protected abstract void printReportFooter(int pageIndex) throws Exception ;


	/**
	 * @param pageIndex
	 */
	protected abstract void printPageFooter(int pageIndex)  throws Exception ;

	/**
	 * @param pageIndex
	 */
	protected abstract void printPageDetails(int pageIndex)  throws Exception ;

	/**
	 * @param pageIndex
	 */
	protected abstract void printPageHeader(int pageIndex)  throws Exception ;

	/**
	 * @param pageIndex
	 */
	protected abstract void printReportHeader(int pageIndex)  throws Exception ;

	/**
	 * @param pageIndex
	 * @return
	 */
	protected abstract boolean isPageExist(int pageIndex)  throws Exception ;

	

	/**
	 * @return the mUseAltLanguge
	 */
	public boolean isUseAltLanguge() {
		return mUseAltLanguge;
	}

	/**
	 * @param mUseAltLanguge the mUseAltLanguge to set
	 */
	public void setUseAltLanguge(boolean mUseAltLanguge) {
		this.mUseAltLanguge = mUseAltLanguge;
	}


}
