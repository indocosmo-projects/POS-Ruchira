/**
 * 
 */
package com.indocosmo.pos.reports.export;

import java.io.FileOutputStream;
import java.util.ArrayList;

import javax.swing.JDialog;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Font;

import com.indocosmo.pos.common.PosEnvSettings;
import com.indocosmo.pos.common.PosLog;
import com.indocosmo.pos.common.utilities.PosCurrencyUtil;
import com.indocosmo.pos.common.utilities.PosNumberUtil;
import com.indocosmo.pos.common.utilities.PosUomUtil;
import com.indocosmo.pos.data.beans.BeanOrderDetail;
import com.indocosmo.pos.data.beans.BeanOrderHeader;
import com.indocosmo.pos.data.beans.BeanOrderPayment;
import com.indocosmo.pos.data.providers.shopdb.PosCouponItemProvider;
import com.indocosmo.pos.data.providers.shopdb.PosOrderHdrProvider;
import com.indocosmo.pos.data.providers.shopdb.PosShopShiftProvider;
import com.indocosmo.pos.data.providers.shopdb.PosUsersProvider;

/**
 * @author deepak
 * 
 */
public class PosBillExcel {

	private  HSSFWorkbook myWorkBook = new HSSFWorkbook();
	private  HSSFSheet mySheet = myWorkBook.createSheet();
	private  PosOrderHdrProvider moderHdrProvider;
	private  PosShopShiftProvider mPosShopShiftProvider;
	private  ArrayList<BeanOrderDetail> mOrderDetailItems;
	private  ArrayList<BeanOrderHeader> orderHeaders;
	private  ArrayList<BeanOrderPayment> mOrderPaymentItems;
	private  boolean header = false;
	private  String excelFileName;
	private  String mBillExportPath;
	private PosUsersProvider mPosUsersProvider;
	private HSSFCellStyle cellStyle = myWorkBook.createCellStyle();
	
	/**
	 * Here using the appache poi external jar for creating and manipulating the excel file.
	 * 
	 */
	public PosBillExcel() {
		mBillExportPath=PosEnvSettings.getInstance().getExportPath();
		mySheet.setDefaultColumnWidth(15);
		Font font = myWorkBook.createFont();
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		font.setFontHeightInPoints((short) 11);
		cellStyle.setFont(font);
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
//		mySheet.autoSizeColumn(2);
//		mySheet.setColumnWidth(2,100);
		}
	
	/**
	 * @param row
	 * @param col
	 * @param string
	 * Creating and manipulating excel file.
	 */
	private  void excelLog(int row, int col, String string) {
//		mySheet.setDefaultColumnWidth(15);
//		if(col!=0){
//		mySheet.autoSizeColumn(col, true);
//		}
		HSSFRow myRow = mySheet.getRow(row);

		if (myRow == null)
			myRow = mySheet.createRow(row);
		

		HSSFCell myCell = myRow.createCell(col);
		myCell.setCellValue(string);
//		HSSFCellStyle cellStyle = myWorkBook.createCellStyle();
		
		
		
//		Font font = myWorkBook.createFont();
//		font.setBoldweight(Font.BOLDWEIGHT_BOLD);
//		font.setFontHeightInPoints((short) 11);
//		cellStyle.setFont(font);
		
		
		
		
//		if(col!=0){
//			cellStyle.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
//		}else{
//			cellStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
//		}
//		if (header) {
//			cellStyle
//					.setFillForegroundColor((row == 2) ? HSSFColor.GREY_25_PERCENT.index
//							: (row == 0)?HSSFColor.LIGHT_ORANGE.index:HSSFColor.LIGHT_TURQUOISE.index);
//			cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
//		}else{
//			cellStyle.setFillForegroundColor(HSSFColor.WHITE.index);
//			
//		}
		myCell.setCellStyle(cellStyle);
	}

	/**
	 * @param mParent
	 * @param where
	 * @param criteria
	 * @param fileName
	 * @param headerOnly
	 * @param openFile
	 * @return
	 * @throws Exception
	 * 
	 * Create the excel file here. 
	 * 
	 */
	public boolean createBill(JDialog mParent, String where,String criteria,String fileName, boolean headerOnly, boolean openFile) throws Exception {
		
		moderHdrProvider = new PosOrderHdrProvider();
		mPosShopShiftProvider = new PosShopShiftProvider();
		mPosUsersProvider = new PosUsersProvider();
		PosCouponItemProvider mCouponItemProvider = new PosCouponItemProvider();
		double Total=0;
		excelFileName = mBillExportPath+"/"+fileName;
		try {

			orderHeaders = (headerOnly) ? moderHdrProvider
					.getOrderHeaders(where) : moderHdrProvider
					.getOrderDetails(where);
			int orderSize = orderHeaders.size();
			header = true;
			// for (int i = 0; i < 9; i++) {
			excelLog(0, 3, "Shop Name:");
			excelLog(0, 4, PosEnvSettings.getPosEnvSettings().getShop().getName());
			excelLog(0, 5, "Station Code:");
			excelLog(0, 6, PosEnvSettings.getPosEnvSettings().getStation().getCode());
			excelLog(1, 0, criteria);
			
			excelLog(2, 0, "Shift Name");
			excelLog(2, 1, "SL No");
			excelLog(2, 2, "Order Id");
			excelLog(2, 3, "User");
			excelLog(2, 4, "Order Time");
			excelLog(2, 5, "Customer");
			excelLog(2, 6, "Discount");
			excelLog(2, 7, "Total Tax");
			excelLog(2, 8, "Rounding Adj.:");
			excelLog(2, 9, "Total Amount");
			excelLog(2, 10, "Order Status");
			excelLog(2, 11, "Remarks");

			// }
			header = false;
			int i = 3;
			String shift =""; 
			for (int count = 0; count < orderSize; count++) {
				if(shift.equalsIgnoreCase(mPosShopShiftProvider.getShift(orderHeaders.get(count).getShiftId()).getName())){
					excelLog(i, 0,"");
				}else{
					shift=mPosShopShiftProvider.getShift(orderHeaders.get(count).getShiftId()).getName();
					excelLog(i, 0, shift);
				}
				excelLog(i, 1, String.valueOf(count + 1));
				excelLog(i, 2, orderHeaders.get(count).getCode());
				
				excelLog(i, 3, orderHeaders.get(count).getUser().getName());
				excelLog(i, 4, orderHeaders.get(count).getOrderTime());
				excelLog(i, 5, orderHeaders.get(count).getCustomer().getName());
				excelLog(i, 6, PosCurrencyUtil.format(orderHeaders.get(count).getBillDiscountAmount()));
				excelLog(i, 7,
						PosCurrencyUtil.format(orderHeaders.get(count).getTotalTax1()
								+orderHeaders.get(count).getTotalTax2()
								+orderHeaders.get(count).getTotalTax3()
								+orderHeaders.get(count).getTotalServiceTax()
								+orderHeaders.get(count).getTotalGST()));
				excelLog(i, 8, PosCurrencyUtil.format(orderHeaders.get(count)
						.getRoundAdjustmentAmount()));
				excelLog(i, 9, PosCurrencyUtil.format(orderHeaders.get(count)
						.getTotalAmountPaid()));
				Total=Total+orderHeaders.get(count)
						.getTotalAmount();
				excelLog(i, 10, orderHeaders.get(count)
						.getStatus().getDisplayText());
				excelLog(i, 11, orderHeaders.get(count)
						.getRemarks().trim());
				System.out.println(orderHeaders.get(count)
						.getRemarks().trim());

				i++;
				if (!headerOnly) {
					header = true;
					excelLog(i, 2, "Item Name");
					excelLog(i, 3, "Quantity");
					excelLog(i, 4, "Price");
					excelLog(i, 5, "Item Discount");
					excelLog(i, 6, "Discount");
					excelLog(i, 7, "Discount Reason");
					excelLog(i, 8, "Item Total");
					excelLog(i, 9, "Order Time");
					excelLog(i, 10, "Remarks");

					header = false;
					i++;
					mOrderDetailItems = orderHeaders.get(count)
							.getOrderDetailItems();
					for (int CountD = 0; CountD < mOrderDetailItems.size(); CountD++) {

						excelLog(i, 2, mOrderDetailItems.get(CountD)
								.getSaleItem().getName());
						excelLog(
								i,
								3,
								PosUomUtil.format(+mOrderDetailItems.get(CountD)
										.getSaleItem().getQuantity(),mOrderDetailItems.get(CountD)
										.getSaleItem().getUom()));
						
						excelLog(i, 4, PosCurrencyUtil.format(+(mOrderDetailItems.get(CountD)
								.getSaleItem().getFixedPrice()-(mOrderDetailItems.get(CountD)
										.getSaleItem().getFixedPrice()*(mOrderDetailItems.get(CountD)
										.getSaleItem().getCustomerPrice()/100)))));
						excelLog(
								i,
								5,
								mOrderDetailItems.get(CountD)
										.getSaleItem().getDiscount().getName());
						excelLog(
								i,
								6,
								PosCurrencyUtil.format(+(mOrderDetailItems.get(CountD)
										.getSaleItem().getDiscount().getPrice())));
						excelLog(
								i,
								7,
								mOrderDetailItems.get(CountD)
										.getSaleItem().getDiscount().getDescription());
						excelLog(
								i,
								8,
								PosCurrencyUtil.format(+(mOrderDetailItems.get(CountD)
										.getSaleItem().getItemTotal())));
						excelLog(i, 9, mOrderDetailItems.get(CountD)
								.getOrderTime());
						excelLog(i, 10, mOrderDetailItems.get(CountD)
								.getRemarks());
						i++;
					}

					i++;
					header = true;
					excelLog(i, 2, "Payment Type");
					excelLog(i, 3, "Name");
					excelLog(i, 4, "Amount");
					excelLog(i, 5, "Time");

					header = false;
					i++;
					mOrderPaymentItems = orderHeaders.get(count)
							.getOrderPaymentItems();
					for (int CountD = 0; CountD < mOrderPaymentItems.size(); CountD++) {

						excelLog(i, 2, String.valueOf((mOrderPaymentItems.get(
								CountD).isRepayment()?"Re-":"")+mOrderPaymentItems.get(
								CountD).getPaymentMode()));
						if(mOrderPaymentItems.get(
								CountD).isRepayment()){
							Total=Total-mOrderPaymentItems.get(
									CountD).getPaidAmount();
						}
						switch (mOrderPaymentItems.get(
								CountD).getPaymentMode()) {
						case Cash:
							excelLog(i, 3,"Cash");
							break;
						case Card:
							excelLog(i, 3,mOrderPaymentItems.get(
									CountD).getAccount());
							break;
						case CashOut:
							excelLog(i, 3,mOrderPaymentItems.get(
									CountD).getAccount());
							break;	
						case Company:
							excelLog(i, 3,mOrderPaymentItems.get(
									CountD).getCompanyName());
							break;
						case Coupon:
							excelLog(i, 3,mCouponItemProvider.getCouponById(mOrderPaymentItems.get(
									CountD).getVoucherId()).getName());
							break;
						default:
							break;
						}
						excelLog(i, 4, String.valueOf(mOrderPaymentItems.get(
								CountD).getPaidAmount()));
						excelLog(i, 5, mOrderPaymentItems.get(CountD)
								.getPaymentTime());
						i++;
					}
					i++;
				}
			}
			excelLog(i, 1,"TOTAL");
			excelLog(i, 8,PosCurrencyUtil.format(+Total));

			FileOutputStream out = new FileOutputStream(excelFileName);
			myWorkBook.write(out);
			out.close();
			System.out.println("Your excel Bill file has been generated!");
			if(openFile){
//				openFileCreated(excelFileName);
				
			}
			return true;
		} catch (Exception e) {
			PosLog.write(this,"createBill",e);
			throw new Exception("Problem while Creating File. Please contact Administrator.");
		}
	}

	/**
	 * @param file 
	 * @throws Exception 
	 * 
	 */
//	private void openFileCreated(String fileName) throws Exception {
//		try{
//			File file = new File(fileName);
//			java.awt.Desktop.getDesktop().open(file);
//			
//		}catch(IOException e){
//			PosLog.write(this,"openFileCreated",e);
//			throw new Exception("Problem while Creating File. Please contact Administrator.");
//		}
//		
//	}
}
