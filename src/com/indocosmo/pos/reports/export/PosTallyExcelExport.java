/**
 * 
 */
package com.indocosmo.pos.reports.export;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.JDialog;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Font;
import org.ibex.nestedvm.util.InodeCache;

import com.indocosmo.pos.common.PosEnvSettings;
import com.indocosmo.pos.common.PosLog;
import com.indocosmo.pos.common.utilities.PosCurrencyUtil;
import com.indocosmo.pos.common.utilities.PosDateUtil;
import com.indocosmo.pos.common.utilities.PosNumberUtil;
import com.indocosmo.pos.common.utilities.PosOrderUtil;
import com.indocosmo.pos.common.utilities.PosUomUtil;
import com.indocosmo.pos.data.beans.BeanOrderDetail;
import com.indocosmo.pos.data.beans.BeanOrderHeader;
import com.indocosmo.pos.data.beans.BeanOrderPayment;
import com.indocosmo.pos.data.beans.BeanTallyExportItem;
import com.indocosmo.pos.data.providers.shopdb.PosCouponItemProvider;
import com.indocosmo.pos.data.providers.shopdb.PosOrderHdrProvider;
import com.indocosmo.pos.data.providers.shopdb.PosShopShiftProvider;
import com.indocosmo.pos.data.providers.shopdb.PosTallyInvoiceExportProvider;
import com.indocosmo.pos.data.providers.shopdb.PosUOMProvider;
import com.indocosmo.pos.data.providers.shopdb.PosUsersProvider;

/**
 * @author sandhya
 * 
 */
public class PosTallyExcelExport {

	private HSSFWorkbook myWorkBook;// = new HSSFWorkbook();
	private HSSFSheet mySheet;// = myWorkBook.createSheet();
	private String excelFileName;
	private String mBillExportPath;
	private HSSFCellStyle cellStyle;// = myWorkBook.createCellStyle();
	private HSSFCellStyle cellStyleNumeric;// = myWorkBook.createCellStyle();

	/**
	 * Here using the appache poi external jar for creating and manipulating the excel file.
	 * 
	 */
	public PosTallyExcelExport() {

		mBillExportPath=PosEnvSettings.getInstance().getExportPath();

	}

	/**
	 * 
	 */
	private void initWorkBook(){

		excelFileName = mBillExportPath+"/ToTally" + PosDateUtil.getDateTime("yyyyMMddHHmm") +".xls";

		myWorkBook = new HSSFWorkbook();
		mySheet = myWorkBook.createSheet();
		cellStyle = myWorkBook.createCellStyle();
		cellStyleNumeric = myWorkBook.createCellStyle();


		mySheet.setDefaultColumnWidth(15);
		Font font = myWorkBook.createFont();
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		font.setFontHeightInPoints((short) 11);
		cellStyle.setFont(font);
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		cellStyleNumeric.setFont(font);
		cellStyleNumeric.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
		cellStyleNumeric.setDataFormat((short) 2);


	}

	/**
	 * @param row
	 * @param col
	 * @param string
	 * Creating and manipulating excel file.
	 */
	private  void excelLog(int row, int col, String string) {

		HSSFRow myRow = mySheet.getRow(row);

		if (myRow == null)
			myRow = mySheet.createRow(row);


		HSSFCell myCell = myRow.createCell(col);
		myCell.setCellValue(string); 
		myCell.setCellStyle(cellStyle);
	}
	/**
	 * @param row
	 * @param col
	 * @param string
	 * Creating and manipulating excel file.
	 */
	private  void excelLog(int row, int col, double value){

		HSSFRow myRow = mySheet.getRow(row);

		if (myRow == null)
			myRow = mySheet.createRow(row);


		HSSFCell myCell = myRow.createCell(col);
		myCell.setCellValue(value); 
		myCell.setCellStyle(cellStyleNumeric);
	}
	/*
	 * 
	 */
	public boolean exportInvoiceListDateWise(String dateFrom, String dateTo, boolean openFile) throws Exception {


		PosTallyInvoiceExportProvider invExportProvider=new PosTallyInvoiceExportProvider();

		final String where ="closing_date>='" + dateFrom + "' AND closing_date<='" + dateTo + "'";
		ArrayList<BeanTallyExportItem> invoiceList= invExportProvider.getInvoiceList(where);
		return exportInvoiceList(invoiceList,openFile);
	 }
	/*
	 * 
	 */
	public boolean exportInvoiceListInvoiceNoWise(String invoiceNoFrom, String invoiceNoTo, boolean openFile) throws Exception {


		PosTallyInvoiceExportProvider invExportProvider=new PosTallyInvoiceExportProvider();

		final String where ="invoice_no>=" + invoiceNoFrom + " AND invoice_no<=" + invoiceNoTo + "";
		ArrayList<BeanTallyExportItem> invoiceList= invExportProvider.getInvoiceList(where);
		return exportInvoiceList(invoiceList,openFile);
	 }
	
	
	/**
	 * @param mParent 
	 * 
	 * Create the excel file here. 
	 * 
	 */
	private boolean exportInvoiceList(ArrayList<BeanTallyExportItem> invoiceList, boolean openFile) throws Exception {


		initWorkBook();
	

		if(invoiceList==null || invoiceList.size()==0)
			throw new Exception("No data to export");

		try {

			excelLog(0, 0, "INVOICE NO");
			excelLog(0, 1, "INVOICE DATE");
			excelLog(0, 2, "VOUCHER TYPE");
			excelLog(0, 3, "CUSTOMER NAME");
			excelLog(0, 4, "ADDRESS LINE 1");
			excelLog(0, 5, "ADDRESS LINE 2");
			excelLog(0, 6, "GST NUMBER");
			excelLog(0, 7, "STATE");
			excelLog(0, 8, "BANK NAME");
			excelLog(0, 9, "BANK ACC NO");
			excelLog(0, 10, "BANK IFSC");
			excelLog(0, 11, "DATE OF SUPPLY");
			excelLog(0, 12, "VECHICLE NO");
			excelLog(0, 13, "DRIVER NAME");
			excelLog(0, 14, "ITEM NAME");
			excelLog(0, 15, "GROUP");
			excelLog(0, 16, "DESCRIPTION");
			excelLog(0, 17, "HSN CODE");
			excelLog(0, 18, "QUANTITY");
			excelLog(0, 19, "UNIT");
			excelLog(0, 20, "RATE");
			excelLog(0, 21, "VALUE");
			excelLog(0, 22, "DISCOUNT");
			excelLog(0, 23, "TAXABLE");
			excelLog(0, 24, "TAX");
			excelLog(0, 25, "CGST");
			excelLog(0, 26, "SGST");
			excelLog(0, 27, "IGST");
			excelLog(0, 28, "ROUND OFF");
			excelLog(0, 29, "NET VALUE");
			int i = 1;

			String invoiceNo;
			for (BeanTallyExportItem invoiceObject:invoiceList) {
				invoiceNo=  PosOrderUtil.getFormatedInvoiceNumberForTally(invoiceObject.getInvoiceDate(), invoiceObject.getInvoiceNo()) ;
				excelLog(i, 0, invoiceNo);
				excelLog(i, 1,PosDateUtil.formatLocal("yyyy-MM-dd", invoiceObject.getInvoiceDate()));
				excelLog(i, 2,invoiceObject.getVoucherType());
				excelLog(i, 3,invoiceObject.getCustName());
				excelLog(i, 4,invoiceObject.getCustAddress());
				excelLog(i, 5,invoiceObject.getCustCity());
				excelLog(i, 6,invoiceObject.getCustGstNo());
				excelLog(i, 7,invoiceObject.getCustState());
				excelLog(i, 8,"");
				excelLog(i, 9,"");
				excelLog(i, 10,"");
				excelLog(i, 11,PosDateUtil.formatLocal("yyyy-MM-dd", invoiceObject.getSupplyDate()));
				excelLog(i, 12,invoiceObject.getVehicleNumber());
				excelLog(i, 13,"");
				excelLog(i, 14,invoiceObject.getSaleItemName());
				excelLog(i, 15,invoiceObject.getSubClassName());
				excelLog(i, 16,invoiceObject.getDescription());
				excelLog(i, 17,invoiceObject.getSaleIemHSNCode());
				excelLog(i, 18, PosUomUtil.roundTo(invoiceObject.getQty(), PosUOMProvider.getInstance().getMaxDecUom()));
//				excelLog(i, 19,invoiceObject.getUomSymbol());
				excelLog(i, 19,"Nos");
				excelLog(i, 20,PosCurrencyUtil.roundTo(invoiceObject.getRate()));
				excelLog(i, 21,PosCurrencyUtil.roundTo(invoiceObject.getValue()));
				excelLog(i, 22,PosCurrencyUtil.roundTo(invoiceObject.getItemDiscountAmount()));
				excelLog(i, 23,PosCurrencyUtil.roundTo(invoiceObject.getTaxableValue()));
				excelLog(i, 24,PosCurrencyUtil.roundTo(invoiceObject.getTax1Pc()+invoiceObject.getTax2Pc()));
				excelLog(i, 25,PosCurrencyUtil.roundTo(invoiceObject.getTax1Amount()));
				excelLog(i, 26,PosCurrencyUtil.roundTo(invoiceObject.getTax2Amount()));
				excelLog(i, 27,"");
				excelLog(i, 28,PosCurrencyUtil.roundTo(invoiceObject.getRoundOff()));
				excelLog(i, 29, PosCurrencyUtil.roundTo(invoiceObject.getNetValue()));
				i++;
			}


			//			excelFileName = mBillExportPath+"/ToTally" + String.valueOf(startingInvoiceNo) + "-" + String.valueOf(endingInvoiceNo) +".xls";
			FileOutputStream out = new FileOutputStream(excelFileName );
			myWorkBook.write(out);
			out.close();
			System.out.println("Your excel file has been generated!");
			if(openFile){
				//				openFileCreated(excelFileName);

			}
			return true;
		} catch (Exception e) {
			PosLog.write(this,"exportInvoiceList",e);
			throw new Exception("Problem while Creating File. Please contact Administrator.");
		}
	}

}
