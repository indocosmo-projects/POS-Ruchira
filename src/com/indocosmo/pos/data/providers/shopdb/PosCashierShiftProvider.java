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
import com.indocosmo.pos.common.PosEnvSettings.ApplicationType;
import com.indocosmo.pos.common.PosLog;
import com.indocosmo.pos.common.utilities.PosDateUtil;
import com.indocosmo.pos.data.beans.BeanCashierShift;
import com.indocosmo.pos.data.beans.BeanPayModeBase.PaymentMode;
import com.indocosmo.pos.data.beans.terminal.BeanTerminalInfo;

/**
 * @author jojesh
 *
 */
public  class PosCashierShiftProvider extends PosShopDBProviderBase {

	private static Map<String, BeanCashierShift> mCashierShifts;
	private PosShopShiftProvider mPosShopShiftProvider;
//	private PosUsersProvider mPosUsersProvider;
	
	public PosCashierShiftProvider() {
		super("cashier_shifts");
		mPosShopShiftProvider=new PosShopShiftProvider();
//		loadData();
	}
	
	private void loadData(){
		mCashierShifts=null;
//		String where="closing_date is null and pos_id='"+PosEnvSettings.getInstance().getStation().getId()+"'";
/**
 * To Support KOT slave stations
 */
		String where="closing_date is null ";
		mCashierShifts=getCashierShifts(where);
	}

	private  Map<String, BeanCashierShift> getCashierShifts(String where){
		Map<String, BeanCashierShift> shifts=null;
		CachedRowSet crs =((where==null)?getData():getData(where));
		if(crs!=null){
			shifts=new HashMap<String, BeanCashierShift>();
			try {
				while(crs.next()){
					BeanCashierShift shift=createShiftFromRecordset(crs);
					shifts.put(shift.getCashierInfo().getCode(), shift);
				} 
			}catch (Exception e) {
				shifts=null;
				PosLog.write(this, "getCashierShifts", e);
			}finally{
				try {
					crs.close();
				} catch (SQLException e) {
					shifts=null;
					PosLog.write(this, "getCashierShifts", e);
				}
			}
		}
		return shifts;
	}
	
	private BeanCashierShift getCashierShift(String where){
		
		BeanCashierShift shift= null;
		CachedRowSet crs =getData(where);
		if(crs!=null){
			
			try {
				if(crs.next()){
					shift=createShiftFromRecordset(crs);
				}
			} catch (Exception e) {
				
				PosLog.write(this, "getCashierShift", e);
			}
		}
		
		return shift;
	}
	
	public BeanCashierShift getOpenCashierById(int id){

		final String where="cashier_id="+id+" and closing_date is null";
				
		return getCashierShift(where);
	}
	
	public BeanCashierShift getTillOpenCashierShift(int station_id){

		final String where="pos_id="+ station_id +" and closing_date is null";
				
		return getCashierShift(where);
	}
	
	public BeanCashierShift getTillOpenCashierShift(){

		final String where="closing_date is null and is_open_till";
				
		return getCashierShift(where);
	}

	private BeanCashierShift createShiftFromRecordset(CachedRowSet crs) throws Exception{
		
		PosCashierProvider cashierProvider=new PosCashierProvider();
		BeanCashierShift shift=new BeanCashierShift();
		
		shift.setID(crs.getInt("auto_id"));
		shift.setCashierInfo(cashierProvider.getCashierDetails(crs.getInt("cashier_id")));

		shift.setCardCollection(crs.getDouble("collection_card"));
		shift.setCashCollection(crs.getDouble("collection_cash"));
		shift.setCompanyCollection(crs.getDouble("collection_company"));
		shift.setVoucherCollection(crs.getDouble("collection_voucher"));
		shift.setClosingFloat(crs.getDouble("closing_float"));
		shift.setOpeningFloat(crs.getDouble("opening_float"));

		shift.setShiftItem(mPosShopShiftProvider.getShift(crs.getInt("shift_id")));
		shift.setClosingDate(crs.getString("closing_date"));
		shift.setClosingTime(crs.getString("closing_time"));
		shift.setOpeningDate(crs.getString("opening_date"));
		shift.setOpeningTime(crs.getString("opening_time"));
		shift.setIsOpenTill(crs.getBoolean("is_open_till"));
		
		shift.setBalanceCash(crs.getDouble("balance_cash"));
		shift.setBalanceVoucher(crs.getDouble("balance_voucher"));
		shift.setBalanceVoucherReturned(crs.getDouble("balance_voucher_returned"));
		shift.setCashOut(crs.getDouble("cash_out"));
		shift.setExpence(crs.getDouble("daily_cashout"));
		shift.setCashRefund(crs.getDouble("cash_refund"));
		shift.setCardRefund(crs.getDouble("card_refund"));
		shift.setVoucherRefund(crs.getDouble("voucher_refund"));
		shift.setAccountsRefund(crs.getDouble("company_refund"));
		shift.setTotalRefund(crs.getDouble("total_refund"));
		return shift;
	}

	public Map<String, BeanCashierShift>  getAllOpenCashierShifts(){
		loadData();
		return mCashierShifts;
	}

	public BeanCashierShift getShiftDetails(BeanTerminalInfo terminalInfo){
		BeanCashierShift shiftDetails = null;
		String where ="closing_date is null and is_open_till=1 "+  ((terminalInfo!=null)?  "and pos_id='"+terminalInfo.getId()+"'":"");
		CachedRowSet crs = getData(where);
		if(crs!=null){
			try {
				while(crs.next()){
					shiftDetails=createShiftFromRecordset(crs);
				}
			}catch (Exception e) {
				PosLog.write(this, "getShiftDetails", e);
			}finally{
				try {
					crs.close();
				} catch (SQLException e) {
					PosLog.write(this, "getShiftDetails", e);
				}
			}
			
		}
		return shiftDetails;
	}
	
	public BeanCashierShift getShiftDetails(String posDate,int shiftId){
		BeanCashierShift shiftDetails = null;
		String where ="opening_date = '"+posDate+"' and shift_id = "+shiftId+" and is_open_till";
		CachedRowSet crs = getData(where);
		if(crs!=null){
			try {
				while(crs.next()){
					shiftDetails=createShiftFromRecordset(crs);
				}
			}catch (Exception e) {
				PosLog.write(this, "getShiftDetails", e);
			}finally{
				try {
					crs.close();
				} catch (SQLException e) {
					PosLog.write(this, "getShiftDetails", e);
				}
			}
			
		}
		return shiftDetails;
	}
	
	public boolean openShift(BeanCashierShift cashierShift){
		//Comments added by Udhayan for DayStart module problem On 23-Jul-2021
		//PosLog.debug("openShift============");
		String sql="insert into cashier_shifts (cashier_id,opening_date,opening_time,shift_id,opening_float,is_open_till,pos_id,opening_till_id) values(?,?,?,?,?,?,?,?)";
//		mPosUsersProvider = new PosUsersProvider();
		PreparedStatement prepStatment=null;
		try {
			beginTrans();
			prepStatment=getPreparedStatement(sql);
			prepStatment.setInt(1, cashierShift.getCashierInfo().getId());
			prepStatment.setString(2, cashierShift.getOpeningDate());
			prepStatment.setString(3, cashierShift.getOpeningTime());
			prepStatment.setInt(4, cashierShift.getShiftItem().getId());
			prepStatment.setDouble(5, cashierShift.getOpeningFloat());
			prepStatment.setBoolean(6, cashierShift.IsOpenTill());
			prepStatment.setInt(7, PosEnvSettings.getInstance().getStation().getId());
			BeanCashierShift tillOpenCashierShift=PosEnvSettings.getInstance().getTillOpenCashierShiftInfo();
			prepStatment.setObject(8, ((tillOpenCashierShift!=null)?tillOpenCashierShift.getID():null));
			prepStatment.executeUpdate();
			mCashierShifts.put(cashierShift.getCashierInfo().getCode(), cashierShift);
			commitTrans();
			return true;
		} catch (SQLException e) {
			PosLog.write(this, "OpenShift", e);
			try {
				rollBack();
			} catch (SQLException e1) {
				PosLog.write(this, "OpenShift", e1);
			}
		}finally{
			try {
				prepStatment.close();
			} catch (SQLException e) {
				PosLog.write(this, "OpenShift", e);
			}
		}
		return false;
	}

	public boolean closeShift(BeanCashierShift cashierShift){
		PreparedStatement prepStatment=null;
		try {
			beginTrans();
			String updateSql = "update cashier_shifts set closing_date=?,closing_time=?,collection_cash=?,collection_card=?," +
					"collection_voucher=?,collection_company=?,closing_float=?,sync_status=?,sync_message=?," +
					"balance_cash=?,balance_voucher=?,balance_voucher_returned=?,cash_out=?,cash_refund=?,card_refund=?" +
					",voucher_refund=?,company_refund=?,total_refund=?, daily_cashout=?, " +
					" collection_online=?,online_refund=? " +
					" where cashier_id=? and opening_date=? and opening_time=?";
			prepStatment=getPreparedStatement(updateSql);
			prepStatment.setString(1, cashierShift.getClosingDate());
			prepStatment.setString(2, cashierShift.getClosingTime());
			prepStatment.setDouble(3, cashierShift.getCashCollection());
			prepStatment.setDouble(4, cashierShift.getCardCollection());
			prepStatment.setDouble(5, cashierShift.getVoucherCollection());
			prepStatment.setDouble(6, cashierShift.getCompanyCollection());
			prepStatment.setDouble(7, cashierShift.getClosingFloat());
			prepStatment.setInt(8,0);
			prepStatment.setString(9,null);
			prepStatment.setDouble(10,cashierShift.getBalanceCash());
			prepStatment.setDouble(11,cashierShift.getBalanceVoucher());
			prepStatment.setDouble(12,cashierShift.getBalanceVoucherReturned());
			prepStatment.setDouble(13,cashierShift.getCashOut());
			prepStatment.setDouble(14,cashierShift.getCashRefund());
			prepStatment.setDouble(15,cashierShift.getCardRefund());
			prepStatment.setDouble(16,cashierShift.getVoucherRefund());
			prepStatment.setDouble(17,cashierShift.getAccountsRefund());
			prepStatment.setDouble(18,cashierShift.getTotalRefund());
			
			prepStatment.setDouble(19,cashierShift.getExpence());
			
			prepStatment.setDouble(20,cashierShift.getOnlineCollection());
			prepStatment.setDouble(21,cashierShift.getOnlineRefund());
			
			
			prepStatment.setInt(22, cashierShift.getCashierInfo().getId());
			
			prepStatment.setString(23, cashierShift.getOpeningDate());
			prepStatment.setString(24, cashierShift.getOpeningTime());
			prepStatment.executeUpdate();
			mCashierShifts.remove(cashierShift.getCashierInfo().getId());
			commitTrans();
			return true;
		} catch (SQLException e) {
			PosLog.write(this, "OpenShift", e);
			try {
				rollBack();
			} catch (SQLException e1) {
				PosLog.write(this, "OpenShift", e1);
			}
		}finally{
			try {
				prepStatment.close();
			} catch (SQLException e) {
				PosLog.write(this, "OpenShift", e);
			}
		}
		return false;
	}
	
	/**
	 * @param shift
	 */
	public boolean closeJoinedShift(BeanCashierShift shift) {
		BeanCashierShift closeShift = setShiftCloseDetails(shift);
		return closeShift(closeShift);
	}

	/**
	 * @return 
	 * 
	 */
	private BeanCashierShift setShiftCloseDetails(BeanCashierShift shift) {
		
		
		BeanCashierShift closeShift = null;
		
		try {
			
			closeShift=shift;
			PosOrderPaymentsProvider paymentProvider=new PosOrderPaymentsProvider();
			Map<PaymentMode, Double> payments=null;
			Map<PaymentMode, Double> refundPayments=null;
			payments=paymentProvider.getShiftPayments(shift,shift.getCashierInfo(),  PosEnvSettings.getInstance().getPosDate(),
					shift.getOpeningDate(),shift.getOpeningTime() , PosDateUtil.getDate(), PosDateUtil.getDateTime(),false);

			if(payments!=null){
				closeShift.setCardCollection(payments.containsKey(PaymentMode.Card)?payments.get(PaymentMode.Card):0.00);
				closeShift.setCashCollection(payments.containsKey(PaymentMode.Cash)?payments.get(PaymentMode.Cash):0.00);
				closeShift.setCompanyCollection(payments.containsKey(PaymentMode.Company)?payments.get(PaymentMode.Company):0.00);
				closeShift.setVoucherCollection(payments.containsKey(PaymentMode.Coupon)?payments.get(PaymentMode.Coupon):0.00);
				closeShift.setOnlineCollection(payments.containsKey(PaymentMode.Online)?payments.get(PaymentMode.Online):0.00);
				closeShift.setBalanceCash(payments.containsKey(PaymentMode.Balance)?payments.get(PaymentMode.Balance):0.00);
				closeShift.setBalanceVoucher(payments.containsKey(PaymentMode.CouponBalance)?payments.get(PaymentMode.CouponBalance):0.00);
				closeShift.setCashOut(payments.containsKey(PaymentMode.CashOut)?payments.get(PaymentMode.CashOut):0.00);
				//			closeShift.setCashRefund(payments.containsKey(PaymentMode.Repay)?payments.get(PaymentMode.Repay):0.00);
			}
			double totalBalanceVoucherReturned = paymentProvider.getTotalVoucherBalanceReturned(shift,shift.getCashierInfo(), PosEnvSettings.getInstance().getPosDate(),
					shift.getOpeningDate(),shift.getOpeningTime() ,PosDateUtil.getDate(), PosDateUtil.getDateTime());
			closeShift.setBalanceVoucherReturned(totalBalanceVoucherReturned);
			refundPayments = paymentProvider.getShiftPayments(shift,shift.getCashierInfo(),  PosEnvSettings.getInstance().getPosDate(),
					shift.getOpeningDate(),shift.getOpeningTime() , PosDateUtil.getDate(), PosDateUtil.getDateTime(),true);
			if(refundPayments!=null){
				closeShift.setCashRefund(refundPayments.containsKey(PaymentMode.Cash)?refundPayments.get(PaymentMode.Cash):0.00);
				closeShift.setCardRefund(refundPayments.containsKey(PaymentMode.Card)?refundPayments.get(PaymentMode.Card):0.00);
				closeShift.setVoucherRefund(refundPayments.containsKey(PaymentMode.Coupon)?refundPayments.get(PaymentMode.Coupon):0.00);
				closeShift.setAccountsRefund(refundPayments.containsKey(PaymentMode.Company)?refundPayments.get(PaymentMode.Company):0.00);
				closeShift.setOnlineRefund(refundPayments.containsKey(PaymentMode.Online)?refundPayments.get(PaymentMode.Online):0.00);
			}
			double totalRefund =closeShift.getCashRefund()+closeShift.getCardRefund() + closeShift.getVoucherRefund() + 	
					closeShift.getAccountsRefund() + closeShift.getOnlineRefund();
			
//			double totalRefund = paymentProvider.getRefund(shift,shift.getCashierInfo(), PosEnvSettings.getInstance().getPosDate(),
//					shift.getOpeningDate(),shift.getOpeningTime() , PosDateUtil.getDate(), PosDateUtil.getDateTime());
			closeShift.setTotalRefund(totalRefund);
			closeShift.setClosingFloat(shift.getOpeningFloat());
			closeShift.setClosingDate(PosEnvSettings.getPosEnvSettings().getPosDate());
			closeShift.setClosingTime(PosDateUtil.getDateTime());

			final double totalExpense=new PosCashOutProvider().getTotalCashOut(PosEnvSettings.getInstance().getPosDate(), shift.getShiftItem().getId());
			closeShift.setExpence(totalExpense);

		} catch (SQLException e) {
			
			PosLog.write(this, "setShiftCloseDetails", e);
		}
		
		return closeShift;
		
	}

	
	@Override
	public int purgeData(String dateTo) throws SQLException{
		
		
		final String where=" closing_date<='" + dateTo + "'" + 
				(PosEnvSettings.getInstance().getApplicationType().equals(ApplicationType.Standard)?" AND sync_status=1  ":"");
		return deleteData(where);
	}
	
	
	@Override
	public int deleteData(String where) throws SQLException {
	
		final String sql= "delete from cashier_shifts where "+where;
		
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
