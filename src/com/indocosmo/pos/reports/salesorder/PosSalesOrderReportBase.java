/**
 * 
 */
package com.indocosmo.pos.reports.salesorder;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.io.IOException;
import java.util.ArrayList;

import com.indocosmo.pos.common.PosEnvSettings;
import com.indocosmo.pos.common.enums.PosOrderServiceTypes;
import com.indocosmo.pos.common.enums.device.PrinterType;
import com.indocosmo.pos.common.exception.printing.HasNoItemsToPrintException;
import com.indocosmo.pos.common.utilities.PosDateUtil;
import com.indocosmo.pos.common.utilities.PosOrderUtil;
import com.indocosmo.pos.common.utilities.PosUomUtil;
import com.indocosmo.pos.data.beans.BeanBillParam;
import com.indocosmo.pos.data.beans.BeanSalesOrderReport;
import com.indocosmo.pos.data.providers.shopdb.PosBillParamProvider;
import com.indocosmo.pos.data.providers.shopdb.PosSalesOrderReportProvider;
import com.indocosmo.pos.data.providers.shopdb.PosUOMProvider;
import com.indocosmo.pos.forms.components.objectbrowser.IPosBrowsableItem;
import com.indocosmo.pos.reports.base.PosPrintableReportBase;
import com.indocosmo.pos.reports.base.PosReportPageFormat;

/**
 * @author sandhya
 * 
 */
public class PosSalesOrderReportBase extends PosPrintableReportBase {
	
	private static final String PROPERTY_FILE="pos-terminal-receipt-pymt-prd.properties"; 

	/**
	 * Serial Number column width
	 */
  
	public boolean isEndOfReport=false; 
	protected String reportDate;
	protected ArrayList<PosOrderServiceTypes> mSelectedServices;
	
	/**
	 * @throws IOException 
	 * 
	 */
	public PosSalesOrderReportBase() throws IOException {
		super();
	}
 

	/**
	 * @param reportDate
	 */
	public void setReportDate(String reportDate) {
		this.reportDate = reportDate;
	}

	/**
	 * @param mSelectePayMode the mSelectePayMode to set
	 */
	public void setSelectedServices(ArrayList<PosOrderServiceTypes> mSelectedServices) {
		this.mSelectedServices = mSelectedServices;
	}


	/* (non-Javadoc)
	 * @see com.indocosmo.pos.reports.base.PosPrintableReportBase#hasMoreData()
	 */
	@Override
	protected boolean hasMoreData() throws Exception {
		// TODO Auto-generated method stub
		return false;
	}


	/* (non-Javadoc)
	 * @see com.indocosmo.pos.reports.base.PosPrintableReportBase#onPageChanged(int, int)
	 */
	@Override
	protected void onPageChanged(int oldPage, int newPage) throws Exception {
		// TODO Auto-generated method stub
		
	}


	/* (non-Javadoc)
	 * @see com.indocosmo.pos.reports.base.PosPrintableReportBase#initReport()
	 */
	@Override
	protected void initReport() {
		// TODO Auto-generated method stub
		
	}


	/* (non-Javadoc)
	 * @see com.indocosmo.pos.reports.base.PosPrintableReportBase#initPage(int)
	 */
	@Override
	protected void initPage(int pageIndex) throws Exception {
		// TODO Auto-generated method stub
		
	}


	/* (non-Javadoc)
	 * @see com.indocosmo.pos.reports.base.PosPrintableReportBase#printReportFooter(int)
	 */
	@Override
	protected void printReportFooter(int pageIndex) throws Exception {
		// TODO Auto-generated method stub
		
	}


	/* (non-Javadoc)
	 * @see com.indocosmo.pos.reports.base.PosPrintableReportBase#printPageFooter(int)
	 */
	@Override
	protected void printPageFooter(int pageIndex) throws Exception {
		// TODO Auto-generated method stub
		
	}


	/* (non-Javadoc)
	 * @see com.indocosmo.pos.reports.base.PosPrintableReportBase#printPageDetails(int)
	 */
	@Override
	protected void printPageDetails(int pageIndex) throws Exception {
		// TODO Auto-generated method stub
		
	}


	/* (non-Javadoc)
	 * @see com.indocosmo.pos.reports.base.PosPrintableReportBase#printPageHeader(int)
	 */
	@Override
	protected void printPageHeader(int pageIndex) throws Exception {
		// TODO Auto-generated method stub
		
	}


	/* (non-Javadoc)
	 * @see com.indocosmo.pos.reports.base.PosPrintableReportBase#printReportHeader(int)
	 */
	@Override
	protected void printReportHeader(int pageIndex) throws Exception {
		// TODO Auto-generated method stub
		
	}


	/* (non-Javadoc)
	 * @see com.indocosmo.pos.reports.base.PosPrintableReportBase#isPageExist(int)
	 */
	@Override
	protected boolean isPageExist(int pageIndex) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}


	/* (non-Javadoc)
	 * @see com.indocosmo.pos.reports.base.PosPrintableBase#onInitialized(java.awt.Graphics2D, java.awt.print.PageFormat)
	 */
	@Override
	protected void onInitialized(Graphics2D g2d, PageFormat pf)
			throws Exception {
		// TODO Auto-generated method stub
		
	}   
	
}
