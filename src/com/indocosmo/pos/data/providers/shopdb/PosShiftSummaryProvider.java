/**
 * 
 */
package com.indocosmo.pos.data.providers.shopdb;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.sql.rowset.CachedRowSet;

import com.indocosmo.pos.common.PosEnvSettings;
import com.indocosmo.pos.common.PosLog;
import com.indocosmo.pos.common.PosEnvSettings.ApplicationType;
import com.indocosmo.pos.data.beans.BeanCashierShift;
import com.indocosmo.pos.data.beans.BeanShiftCloseSummary;
import com.indocosmo.pos.data.beans.BeanShiftSummary;
import com.indocosmo.pos.data.beans.BeanUser;
import com.indocosmo.pos.data.beans.BeanOrderHeader.PosOrderStatus;
import com.indocosmo.pos.data.beans.BeanPayModeBase.PaymentMode;
import com.indocosmo.pos.forms.messageboxes.PosMessageBoxForm;

/**
 * @author deepak
 *
 */
public class PosShiftSummaryProvider extends PosShopDBProviderBase {
	
	private static Map<String, BeanShiftCloseSummary> mShiftsSummary;
	private PosShopShiftProvider mPosShopShiftProvider;
//	private PosUsersProvider mPosUsersProvider;
	
	public PosShiftSummaryProvider() {
		super("shift_summary");
		mPosShopShiftProvider=new PosShopShiftProvider();
//		loadData();
	}

	private void loadData(){
		mShiftsSummary=null;
		mShiftsSummary=getShiftsSummary(null);
	}

	private  Map<String, BeanShiftCloseSummary> getShiftsSummary(String where){
		Map<String, BeanShiftCloseSummary> shifts=null;
		CachedRowSet crs =((where==null)?getData():getData(where));
		if(crs!=null){
			shifts=new HashMap<String, BeanShiftCloseSummary>();
			try {
				while(crs.next()){
					BeanShiftCloseSummary shift=createShiftSummaryFromRecordset(crs);
					shifts.put(shift.getCashierInfo().getCode(), shift);
				} 
			}catch (Exception e) {
				shifts=null;
				PosLog.write(this, "getShiftsSummary", e);
			}finally{
				try {
					crs.close();
				} catch (SQLException e) {
					shifts=null;
					PosLog.write(this, "getShiftsSummary", e);
				}
			}
		}
		return shifts;
	}

 
	private BeanShiftCloseSummary createShiftSummaryFromRecordset(CachedRowSet crs) throws Exception{
		PosCashierProvider cashierProvider=new PosCashierProvider();
		BeanShiftCloseSummary shiftSummary=new BeanShiftCloseSummary();
		
		shiftSummary.setID(crs.getInt("auto_id"));
		shiftSummary.setStationCode("station_code");
		shiftSummary.setShiftItem(mPosShopShiftProvider.getShift(crs.getInt("shift_id")));
		shiftSummary.setCashierInfo(cashierProvider.getCashierDetails(crs.getInt("shift_by")));

		shiftSummary.setOpeningDate(crs.getString("opening_date"));
		shiftSummary.setOpeningTime(crs.getString("opening_time"));
		shiftSummary.setClosingDate(crs.getString("closing_date"));
		shiftSummary.setClosingTime(crs.getString("closing_time"));
		
		shiftSummary.setOpeningFloat(crs.getDouble("opening_float"));
		shiftSummary.setCashReceipts(crs.getDouble("cash_receipts"));
		shiftSummary.setCardReceipts(crs.getDouble("card_receipts"));
		shiftSummary.setVoucherReceipts(crs.getDouble("voucher_receipts"));
		shiftSummary.setAccountsReceivable(crs.getDouble("accounts_receivable"));
		shiftSummary.setOnlineReceipts(crs.getDouble("online_receipts"));
		
		shiftSummary.setCashAdvance(crs.getDouble("cash_receipts_advance"));
		shiftSummary.setCardAdvance(crs.getDouble("card_receipts_advance"));
		shiftSummary.setOnlineAdvance(crs.getDouble("online_receipts_advance"));
		shiftSummary.setVoucherAdvance(crs.getDouble("voucher_receipts_advance"));
		
		shiftSummary.setCashReturned(crs.getDouble("cash_returned"));
		shiftSummary.setVoucherBalance(crs.getDouble("voucher_balance"));
		shiftSummary.setCashOut(crs.getDouble("cash_out"));
		shiftSummary.setCashRefund(crs.getDouble("cash_refund"));
		shiftSummary.setCardRefund(crs.getDouble("card_refund"));
		shiftSummary.setVoucherRefund(crs.getDouble("voucher_refund"));
		shiftSummary.setAccountsRefund(crs.getDouble("accounts_refund"));
		shiftSummary.setOnlineRefund(crs.getDouble("online_refund"));
		
		shiftSummary.setTotalRefund(crs.getDouble("total_refund"));
		shiftSummary.setNetSale(crs.getDouble("sales"));
		shiftSummary.setExpense(crs.getDouble("daily_cashout"));
		shiftSummary.setNetCash(crs.getDouble("net_cash_received"));
		shiftSummary.setVoucherBalanceReturned(crs.getDouble("voucher_balance_returned"));
		
		shiftSummary.setClosingCash(crs.getDouble("closing_cash"));
		shiftSummary.setActualCash(crs.getDouble("actual_cash"));
		shiftSummary.setVariance(crs.getDouble("cash_variance"));
		shiftSummary.setCashDeposit(crs.getDouble("cash_deposit"));
		shiftSummary.setCashRemaining(crs.getDouble("cash_remaining"));
		shiftSummary.setReferenceSlipNumber(crs.getString("referance_number"));
		return shiftSummary;
	}
	
	public Map<String, BeanShiftCloseSummary>  getShiftSummary(){
		loadData();
		return mShiftsSummary;
	}
	
	public BeanShiftCloseSummary getShiftSummary(String Where){
		
		BeanShiftCloseSummary shiftSummary = null;
		CachedRowSet crs =getData(Where);
		if(crs!=null){
			try {
				if(crs.next()){
					shiftSummary=createShiftSummaryFromRecordset(crs);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return shiftSummary;
		
	}
	
	private BeanShiftCloseSummary addOpeningDetToDayEnd( String where  ) throws Exception{
		
		BeanShiftCloseSummary shiftSummary=null;
		String sql=" SELECT opening_date,opening_time,opening_float  FROM shift_summary where " + where + " ORDER BY opening_time";
		try{
			CachedRowSet crs =executeQuery(sql);
			
			if(crs!=null){
				if(crs.next()){
					shiftSummary=new BeanShiftCloseSummary();
					
					shiftSummary.setOpeningDate(crs.getString("opening_date"));
					shiftSummary.setOpeningTime(crs.getString("opening_time"));
//					shiftSummary.setOpeningFloat(crs.getDouble("opening_float"));
					
				}
				
			}
			return shiftSummary;
		}catch(Exception ex) {
			throw ex;
		}
	}
	private void addClosingDetToDayEnd(BeanShiftCloseSummary shiftSummary,String where  ) throws Exception{
		
		String sql=" SELECT  closing_date,closing_time,cash_remaining,cash_variance,cash_deposit,closing_cash,actual_cash  FROM shift_summary where " + where + " ORDER BY  closing_time DESC";
		try{
			CachedRowSet crs =executeQuery(sql);
			
			if(crs!=null){
				if(crs.next()){
				
					shiftSummary.setClosingDate(crs.getString("closing_date"));
					shiftSummary.setClosingTime(crs.getString("closing_time"));
					
//					shiftSummary.setClosingCash(crs.getDouble("closing_cash"));
//					shiftSummary.setActualCash(crs.getDouble("actual_cash"));
//					shiftSummary.setVariance(crs.getDouble("cash_variance"));
//					shiftSummary.setCashDeposit(crs.getDouble("cash_deposit"));
//					shiftSummary.setCashRemaining(crs.getDouble("cash_remaining"));

				}
				
			}
		}catch(Exception ex) {
			throw ex;
		}
	}
	
private void addSummaryToDayEnd(BeanShiftCloseSummary shiftSummary,String where  ) throws Exception{
		
 
	
		String sql=" SELECT opening_date " + 
					" ,sum(cash_receipts) 'cash_receipts' " + 
					" ,sum(card_receipts) 'card_receipts' " + 
					" ,sum(voucher_receipts) 'voucher_receipts' " +
					" ,sum(online_receipts) 'online_receipts' " +
					" ,sum(accounts_receivable) 'accounts_receivable'" + 
					" ,sum(cash_receipts_advance) 'cash_receipts_advance' " + 
					" ,sum(card_receipts_advance) 'card_receipts_advance' " + 
					" ,sum(online_receipts_advance) 'online_receipts_advance' " + 
					" ,sum(voucher_receipts_advance) 'voucher_receipts_advance' " + 
					" ,sum(cash_returned) 'cash_returned' " + 
					" ,sum(voucher_balance) 'voucher_balance' " + 
					" ,sum(cash_out) 'cash_out' " + 
					" ,sum(cash_refund) 'cash_refund' " +
					" ,sum(card_refund) 'card_refund' " + 
					" ,sum(voucher_refund) 'voucher_refund' " + 
					" ,sum(accounts_refund) 'accounts_refund' " +
					" ,sum(online_refund) 'online_refund' " +
					" ,sum(total_refund) 'total_refund'" + 
					" ,sum(sales) 'sales'" + 
					" ,sum(previous_advance) 'previous_advance' " + 
					" ,sum(daily_cashout) 'daily_cashout' " + 
					" ,sum(net_cash_received) 'net_cash_received' " + 
					" ,sum(voucher_balance_returned) 'voucher_balance_returned' " + 
					" ,sum(cash_variance) 'cash_variance' " + 
					" ,sum(cash_deposit) 'cash_deposit' " +
					" ,sum(opening_float) 'opening_float' " + 
					" ,sum(closing_cash) 'closing_cash' " + 
					" ,sum(actual_cash) 'actual_cash' " + 
					" ,sum(cash_variance) 'cash_variance' " + 
					" ,sum(cash_deposit) 'cash_deposit' " + 
					" ,sum(cash_remaining) 'cash_remaining' " + 
					" FROM shift_summary " + 
					" where " + where  +
					"  GROUP BY opening_date";
		
		try{
			CachedRowSet crs =executeQuery(sql);
			
			if(crs!=null){
				if(crs.next()){
				
					shiftSummary.setCashReceipts(crs.getDouble("cash_receipts"));
					shiftSummary.setCardReceipts(crs.getDouble("card_receipts"));
					shiftSummary.setVoucherReceipts(crs.getDouble("voucher_receipts"));
					shiftSummary.setAccountsReceivable(crs.getDouble("accounts_receivable"));
					shiftSummary.setOnlineReceipts(crs.getDouble("online_receipts"));
					
					shiftSummary.setCashAdvance(crs.getDouble("cash_receipts_advance"));
					shiftSummary.setCardAdvance(crs.getDouble("card_receipts_advance"));
					shiftSummary.setOnlineAdvance(crs.getDouble("online_receipts_advance"));
					shiftSummary.setVoucherAdvance(crs.getDouble("voucher_receipts_advance"));
					
					shiftSummary.setCashReturned(crs.getDouble("cash_returned"));
					shiftSummary.setVoucherBalance(crs.getDouble("voucher_balance"));
					shiftSummary.setCashOut(crs.getDouble("cash_out"));
					shiftSummary.setCashRefund(crs.getDouble("cash_refund"));
					shiftSummary.setCardRefund(crs.getDouble("card_refund"));
					shiftSummary.setVoucherRefund(crs.getDouble("voucher_refund"));
					shiftSummary.setAccountsRefund(crs.getDouble("accounts_refund"));
					shiftSummary.setOnlineRefund(crs.getDouble("online_refund"));
					
					shiftSummary.setTotalRefund(crs.getDouble("total_refund"));
					shiftSummary.setNetSale(crs.getDouble("sales"));
					shiftSummary.setClosedOrderAdvance(crs.getDouble("previous_advance"));
					shiftSummary.setExpense(crs.getDouble("daily_cashout"));
					shiftSummary.setNetCash(crs.getDouble("net_cash_received"));
					shiftSummary.setVoucherBalanceReturned(crs.getDouble("voucher_balance_returned"));
					
					shiftSummary.setOpeningFloat(crs.getDouble("opening_float"));
					shiftSummary.setClosingCash(crs.getDouble("closing_cash"));
					shiftSummary.setActualCash(crs.getDouble("actual_cash"));
					shiftSummary.setVariance(crs.getDouble("cash_variance"));
					shiftSummary.setCashDeposit(crs.getDouble("cash_deposit"));
					shiftSummary.setCashRemaining(crs.getDouble("cash_remaining"));
				 
					
				}
				
			}
		}catch(Exception ex) {
			throw ex;
		}
	}
	
	public BeanShiftCloseSummary getDayEndShiftSummary(String where){
		
		BeanShiftCloseSummary shiftSummary =null;
		
		try{
			shiftSummary=addOpeningDetToDayEnd( where);
		 	addSummaryToDayEnd(shiftSummary, where);
			addClosingDetToDayEnd(shiftSummary, where);
			
			
		}catch(Exception ex) {
			PosLog.write(this,  "Day End - Shift Summary", ex);
			
			shiftSummary=null;
		}
		
		return shiftSummary;	 
		
	}
	public boolean insertShiftSummary(BeanShiftCloseSummary shiftSummary){
		
//		String SQL = "insert into shift_summary (shift_id,shift_by,opening_date,opening_time,closing_date,closing_time)" +
//				
//				" values(?,?,?,?,?,?)";
		String SQL = "insert into shift_summary (station_code,shift_id,shift_by,opening_date,opening_time,closing_date,closing_time," +
				"opening_float,cash_receipts,card_receipts,voucher_receipts,accounts_receivable," +
				"cash_returned,voucher_balance,cash_out,cash_refund,card_refund,voucher_refund,accounts_refund,total_refund,sales,net_cash_received,voucher_balance_returned," +
				"closing_cash,actual_cash,cash_variance,cash_deposit,cash_remaining,referance_number,daily_cashout,"+
				"cash_receipts_advance,card_receipts_advance,previous_advance,voucher_receipts_advance," +
				"online_receipts,online_receipts_advance,online_refund) values " + 
				" (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		PreparedStatement prepStmt = null;
		
		try {
			beginTrans();
			prepStmt = getPreparedStatement(SQL);
			prepStmt.setString(1, PosEnvSettings.getPosEnvSettings().getStation().getCode());
			prepStmt.setInt(2, shiftSummary.getShiftItem().getId());
			prepStmt.setInt(3, shiftSummary.getCashierInfo().getId());
			prepStmt.setString(4, shiftSummary.getOpeningDate());
			prepStmt.setString(5, shiftSummary.getOpeningTime());
			prepStmt.setString(6, shiftSummary.getClosingDate());
			prepStmt.setString(7, shiftSummary.getClosingTime());
			
			prepStmt.setDouble(8, shiftSummary.getOpeningFloat());
			prepStmt.setDouble(9, shiftSummary.getCashReceipts());
			prepStmt.setDouble(10, shiftSummary.getCardReceipts());
			prepStmt.setDouble(11, shiftSummary.getVoucherReceipts());
			prepStmt.setDouble(12, shiftSummary.getAccountsReceivable());
			
			prepStmt.setDouble(13, shiftSummary.getCashReturned());
			prepStmt.setDouble(14, shiftSummary.getVoucherBalance());
			prepStmt.setDouble(15, shiftSummary.getCashOut());
			prepStmt.setDouble(16, shiftSummary.getCashRefund());
			prepStmt.setDouble(17, shiftSummary.getCardRefund());
			prepStmt.setDouble(18, shiftSummary.getVoucherRefund());
			prepStmt.setDouble(19, shiftSummary.getAccountsRefund());
			prepStmt.setDouble(20, shiftSummary.getTotalRefund());
			prepStmt.setDouble(21, shiftSummary.getNetSale());
			prepStmt.setDouble(22, shiftSummary.getNetCash());
			prepStmt.setDouble(23, shiftSummary.getVoucherBalanceReturned());
			
			prepStmt.setDouble(24, shiftSummary.getClosingCash());
			prepStmt.setDouble(25, shiftSummary.getActualCash());
			prepStmt.setDouble(26, shiftSummary.getVariance());
			prepStmt.setDouble(27, shiftSummary.getCashDeposit());
			prepStmt.setDouble(28, shiftSummary.getCashRemaining());
			prepStmt.setString(29, shiftSummary.getReferenceSlipNumber());
			prepStmt.setDouble(30, shiftSummary.getExpense());
			prepStmt.setDouble(31, shiftSummary.getCashAdvance());
			prepStmt.setDouble(32, shiftSummary.getCardAdvance());
			prepStmt.setDouble(33, shiftSummary.getClosedOrderAdvance());
			
			prepStmt.setDouble(34, shiftSummary.getVoucherAdvance());
			prepStmt.setDouble(35, shiftSummary.getOnlineReceipts());
			prepStmt.setDouble(36, shiftSummary.getOnlineAdvance());
			prepStmt.setDouble(37, shiftSummary.getOnlineRefund());
			prepStmt.executeUpdate();
			commitTrans();
			return true;
		} catch (SQLException e) {
			PosLog.write(this, "insertShiftSummary", e);
			try {
				rollBack();
			} catch (SQLException e1) {
				PosLog.write(this, "insertShiftSummary", e1);
			}
		}finally{
			try {
				prepStmt.close();
			} catch (SQLException e) {
				PosLog.write(this, "insertShiftSummary", e);
			}
		}
		
		return false;
		
	}
	
	public double getLatestCashRemainingForDefaultFloat(){
		
		String SQL= "select cash_remaining from shift_summary where closing_time is not null order by closing_time desc LIMIT 1";
		
		
		
		double cashRemaining  = 0.0;
		try {
			final CachedRowSet res=executeQuery(SQL);
			if(res!=null){
				while (res.next()) {
					cashRemaining=res.getDouble("cash_remaining");
				}
				res.close();
			}
		} catch (SQLException e) {
			PosLog.write(this,"getLatestCashRemainingForDefaultFloat",e);
		}
		return cashRemaining;
		
	}
	
	public boolean isValidShift(int shiftId){
		boolean valid = true;
		/***
		 *Changed for mysql porting 
		 *String sql = "select * from shift_summary where opening_date is '"+PosEnvSettings.getInstance().getPosDate()+"' and shift_id is "+shiftId;
		 */
		String sql = "select * from shift_summary where opening_date = '"+PosEnvSettings.getInstance().getPosDate()+"' and shift_id = "+shiftId;
		
		try {
			final CachedRowSet res=executeQuery(sql);
			if(res!=null){
				while (res.next()) {
					valid = false;
					res.close();
				}
				
			}
		} catch (SQLException e) {
			PosLog.write(this,"isValidShift",e);
		}
		return valid;
		
	}

	
	public BeanShiftSummary getShiftSummaryReport(BeanCashierShift shopShift, BeanUser mCashierInfo, String posDate,  String dateFrom, String timeFrom, String dateTo, String timeTo, Boolean isRepayment) throws Exception{
		
		final PosOrderPaymentsProvider paymentProvider=new PosOrderPaymentsProvider();
		final PosCashOutProvider expnceProvider=new PosCashOutProvider();
		final PosOrderHdrProvider orderHdrProvider=new PosOrderHdrProvider();
		final PosOrderDiscountProvider discProvider=new PosOrderDiscountProvider();
		final PosOrderPaymentHdrProvider payHdrProvider=new PosOrderPaymentHdrProvider();

		double totalClosedOrderAdvance=0;
		double netSale=0;
		double netCash=0;
		int Numberoforders=0;
		
		Map<PaymentMode, Double> payments=null;
		Map<PaymentMode, Double> advances=null;
		Map<PaymentMode, Double> refunds=null;
		BeanShiftSummary shiftSummaryReport= new BeanShiftSummary();
		
		try{
			shiftSummaryReport.setShiftCode(shopShift.getShiftItem().getCode()); 
			 
			payments=paymentProvider.getShiftPayments(shopShift,null,  posDate , dateFrom,  timeFrom  ,dateTo ,timeTo,false,false);
			advances=paymentProvider.getShiftPayments(shopShift,null,  posDate , dateFrom,  timeFrom  ,dateTo ,timeTo,false,true);
			refunds=paymentProvider.getShiftPayments(shopShift,null,  posDate , dateFrom,  timeFrom  ,dateTo ,timeTo,true,false);
			netSale=0; 
			netCash=PosEnvSettings.getInstance().getTillOpenCashierShiftInfo().getOpeningFloat();
			if(payments!=null){
				
				if(payments.containsKey(PaymentMode.Cash)){
					double CashReturned =0;
					if(payments.containsKey(PaymentMode.Balance)){
						CashReturned = payments.get(PaymentMode.Balance);
					}
					shiftSummaryReport.setCashReceipts(payments.get(PaymentMode.Cash)-CashReturned);
					netSale+=shiftSummaryReport.getCashReceipts();
					netCash+=shiftSummaryReport.getCashReceipts();
				}
				if(payments.containsKey(PaymentMode.Card)){
					shiftSummaryReport.setCardReceipts(payments.get(PaymentMode.Card));
					netSale+=shiftSummaryReport.getCardReceipts();
				}
				if(payments.containsKey(PaymentMode.Company)){
					shiftSummaryReport.setAccountsReceivable(payments.get(PaymentMode.Company));
					netSale+=shiftSummaryReport.getAccountsReceivable();
				}
				if(payments.containsKey(PaymentMode.Coupon)){
					if(payments.containsKey(PaymentMode.CouponBalance)){
						shiftSummaryReport.setVoucherBalance( payments.get(PaymentMode.CouponBalance));
					}
					shiftSummaryReport.setVoucherReceipts( payments.get(PaymentMode.Coupon));
					netSale+=shiftSummaryReport.getVoucherReceipts();
					netSale-=shiftSummaryReport.getVoucherBalance();
					netCash=netCash-shiftSummaryReport.getVoucherBalance();
				}
	
				if(payments.containsKey(PaymentMode.CashOut)){
					shiftSummaryReport.setCashOut(payments.get(PaymentMode.CashOut));
					netSale=netSale-shiftSummaryReport.getCashOut();
					netCash=netCash-shiftSummaryReport.getCashOut();;
				}
	
				if(payments.containsKey(PaymentMode.Online)){
					shiftSummaryReport.setOnlineReceipts(payments.get(PaymentMode.Online));
					netSale+=shiftSummaryReport.getOnlineReceipts();
				}
	
			}
			
			if(advances!=null){
				
				if(advances.containsKey(PaymentMode.Cash)){
					shiftSummaryReport.setCashAdvance(advances.get(PaymentMode.Cash));
					netCash+=shiftSummaryReport.getCashAdvance();
				}
				if(advances.containsKey(PaymentMode.Card)){
					shiftSummaryReport.setCardAdvance(advances.get(PaymentMode.Card));
					
				}
				 	if(advances.containsKey(PaymentMode.Online)){
					shiftSummaryReport.setOnlineAdvance( advances.get(PaymentMode.Online) );
				}
				if(advances.containsKey(PaymentMode.Coupon)){
					shiftSummaryReport.setVoucherAdvance( advances.get(PaymentMode.Coupon) );
				}
			}
			
			
			if(refunds!=null){
				if(refunds.containsKey(PaymentMode.Card)){
					shiftSummaryReport.setCardRefund(refunds.get(PaymentMode.Card));
					netSale-=shiftSummaryReport.getCardRefund();
				}
				if(refunds.containsKey(PaymentMode.Cash)){
					shiftSummaryReport.setCashRefund(refunds.get(PaymentMode.Cash));
					netSale-=shiftSummaryReport.getCashRefund();
					netCash=netCash-shiftSummaryReport.getCashRefund();
				}
				
				if(refunds.containsKey(PaymentMode.Company)){
					shiftSummaryReport.setAccountsRefund(refunds.get(PaymentMode.Company));
					netSale-=shiftSummaryReport.getAccountsRefund();
				}
				if(refunds.containsKey(PaymentMode.Coupon)){
					shiftSummaryReport.setVoucherRefund( refunds.get(PaymentMode.Coupon) );
					netSale-=shiftSummaryReport.getVoucherRefund();
				}
				if(refunds.containsKey(PaymentMode.Online)){
					shiftSummaryReport.setOnlineRefund(refunds.get(PaymentMode.Online) );
					netSale-=shiftSummaryReport.getOnlineRefund();
				}
 			}
			
			totalClosedOrderAdvance=paymentProvider.getAdvanceAmtOfClosedSaledsOrder(shopShift, posDate, dateFrom);
			netSale+=totalClosedOrderAdvance;
			
			shiftSummaryReport.setClosedOrderAdvance(totalClosedOrderAdvance);
			shiftSummaryReport.setNetSale(netSale);

			
					
		} catch (Exception e) {
			PosLog.write(this, "getShiftSummaryReport", e);
			throw new Exception("Failed get  shift summary report. Please check log for details.");
		}
		
		try{
		
			final double totalExpense=expnceProvider.getTotalCashOut(posDate, shopShift.getShiftItem().getId(),
					dateFrom,timeFrom,dateTo,timeTo);
			netCash=netCash-totalExpense;
			shiftSummaryReport.setExpense(totalExpense);
			shiftSummaryReport.setNetCash(netCash);
			shiftSummaryReport.setTotalDiscount(discProvider.getTotalDiscountForShiftSummaryReport(posDate, shopShift.getShiftItem().getId()));
			
			PosOrderDtlProvider orderDtlPvdr=new PosOrderDtlProvider();
			shiftSummaryReport.setTotalTax(orderDtlPvdr.getTotalBillTax(" closing_date ='" + posDate + "' ", shopShift.getShiftItem().getId()));
			
			String sqlCriteria=" 	closing_date = '" + posDate + "' ";
			sqlCriteria = sqlCriteria+" and status in "
					+ "("+PosOrderStatus.Closed.getCode()+","+PosOrderStatus.Refunded.getCode()+") ";
			sqlCriteria = sqlCriteria+" and shift_id=" + shopShift.getShiftItem().getId(); 
			
			shiftSummaryReport.setOpeningInvoiceNo(orderHdrProvider.getOpeningInvoiceNo(sqlCriteria));
			shiftSummaryReport.setClosingInvoiceNo(orderHdrProvider.getClosingInvoiceNo(sqlCriteria));

		} catch (Exception e) {
			PosLog.write(this, "getShiftSummaryReport", e);
			throw new Exception("Failed get  shift summary report. Please check log for details.");
		}
		
//		String where=" (order_date='" +posDate+ "' and order_time between concat(date('"+ dateFrom + "'),' ', time('" + timeFrom +"'))"+ 
//				" and concat(date('" + dateTo + "'),' ' ,time('" + timeTo +"')) OR " ;
//		
//		where+=" closing_date='" +posDate+ "' and closing_time between concat(date('"+ dateFrom + "'),' ', time('" + timeFrom +"'))"+ 
//				" and concat(date('" + dateTo + "'),' ' ,time('" + timeTo +"'))) and "+" status in ("+PosOrderStatus.Closed.getCode()+","+PosOrderStatus.Partial.getCode()+","+PosOrderStatus.Refunded.getCode()+")";

		String where=" closing_date='" +posDate+ "'  and "+" status in ("+PosOrderStatus.Closed.getCode()+","+PosOrderStatus.Partial.getCode()+","+PosOrderStatus.Refunded.getCode()+")";

		if(shopShift!=null)
			where+=" and shift_id="+shopShift.getShiftItem().getId();

		PosOrderHdrProvider orderhdrProvider = new PosOrderHdrProvider();
		try {
			Numberoforders = orderhdrProvider.getOrderHeaders(where).size();
		} catch (Exception e) {
			PosLog.write(this, "getShiftSummaryReport", e);
			throw new Exception("Failed get  shift summary report. Please check log for details.");
		}
		shiftSummaryReport.setOrderCount( Numberoforders); 
		
		return shiftSummaryReport;
	}
	
	@Override
	public int purgeData(String dateTo) throws SQLException{
 

		final String where=" closing_date<='" + dateTo + "'" + 
				(PosEnvSettings.getInstance().getApplicationType().equals(ApplicationType.Standard)?" AND sync_status=1  ":"");
		return deleteData(where);
	}
	
	
	@Override
	public int deleteData(String where) throws SQLException {
	
		final String sql= "delete from shift_summary where "+where;
		
		return executeNonQuery(sql);
		
	}
	
	/* (non-Javadoc)
	 * @see com.indocosmo.pos.data.providers.PosDatabaseProvider#addToHistory(java.lang.String)
	 */
	@Override
	public StringBuffer addToHistory(String date) throws SQLException {
	 
		String where =" closing_date<='" + date + "'";
		 
		return super.addToHistory(where); 
	}
}
