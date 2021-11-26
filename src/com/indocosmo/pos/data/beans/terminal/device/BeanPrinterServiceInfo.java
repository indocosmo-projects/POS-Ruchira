/**
 * 
 */
package com.indocosmo.pos.data.beans.terminal.device;

import javax.print.PrintService;
import javax.swing.KeyStroke;

import com.indocosmo.pos.data.beans.BeanMasterBase;

/**
 * @author jojesh
 * System printer information
 */
public class BeanPrinterServiceInfo extends BeanMasterBase {

	private PrintService mPrintService;

	/**
	 * @param service
	 */
	public BeanPrinterServiceInfo(PrintService service) {
		this.setPrintService(service);
	}

	/**
	 * @return the PrintService
	 */
	public PrintService getPrintService() {
		return mPrintService;
	}

	/**
	 * @param PrintService the PrintService to set
	 */
	public void setPrintService(PrintService printService) {
		this.mPrintService = printService;
		setName((printService!=null)?printService.getName():"");
	}
	
	public static String[] SEARCH_FIELD_TITLE_LIST={"#","Printer Name"};
	public static String[] SEARCH_FIELD_LIST={"getId","getName"};
	public static int[] SEARCH_FIELD_WIDTH_LIST={50};
	/* (non-Javadoc)
	 * @see com.indocosmo.pos.components.search.IPosSearchableItem#getFiledList()
	 */
	@Override
	public String[] getFieldList() {
		return SEARCH_FIELD_LIST;
	}
	/* (non-Javadoc)
	 * @see com.indocosmo.pos.components.search.IPosSearchableItem#getFiledTitleList()
	 */
	@Override
	public String[] getFieldTitleList() {

		return SEARCH_FIELD_TITLE_LIST;
	}
	/* (non-Javadoc)
	 * @see com.indocosmo.pos.components.search.IPosSearchableItem#getFiledWidthList()
	 */
	@Override
	public int[] getFieldWidthList() {
		return SEARCH_FIELD_WIDTH_LIST;
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.pos.forms.components.objectbrowser.IPosBrowsableItem#getKeyStroke()
	 */
	@Override
	public KeyStroke getKeyStroke() {
		// TODO Auto-generated method stub
		return null;
	}
}
