/**
 * 
 */
package com.indocosmo.pos.data.providers.shopdb;

import java.sql.SQLException;

import javax.sql.rowset.CachedRowSet;

import com.indocosmo.pos.common.PosLog;
import com.indocosmo.pos.data.beans.BeanPayModeBase.PaymentMode;
import com.indocosmo.pos.data.beans.BeanPaymentModes;

/**
 * @author deepak
 *
 */
public class PosPaymentModesProvider extends PosShopDBProviderBase {
	
	private static PosPaymentModesProvider mInstance;
	
	BeanPaymentModes mPosPaymentModesObject = new BeanPaymentModes();
	
	public static PosPaymentModesProvider getInstance(){
		
		if(mInstance==null)
			mInstance=new PosPaymentModesProvider();
		
		return mInstance;
	}
	/**
	 * 
	 */
	private  PosPaymentModesProvider() {
		
		super("payment_modes");
		mPosPaymentModesObject = load();
		
		setPaymentModeTitle();
				
	}
	/**
	 * @return
	 */
	private BeanPaymentModes load() {
		
		BeanPaymentModes PaymentModesObject = null;
		CachedRowSet res=null;
		res=getData();
		try {
			while (res.next()) {
				
				PaymentModesObject = createItem(res); 
			}
			res.close();
		} catch (SQLException e) {
			PosLog.write(this,"load",e);
		}
		return PaymentModesObject;
	}
	/**
	 * @param res
	 * @return
	 * @throws SQLException 
	 */
	private BeanPaymentModes createItem(CachedRowSet res) throws SQLException {

		BeanPaymentModes PaymentModesObject = new BeanPaymentModes();
		
		PaymentModesObject.setId(res.getInt("id"));
		PaymentModesObject.setCanPayByCash(res.getBoolean("can_pay_by_cash"));
		PaymentModesObject.setCanPayByCompany(res.getBoolean("can_pay_by_company"));
		PaymentModesObject.setCanPayByVouchers(res.getBoolean("can_pay_by_vouchers"));
		PaymentModesObject.setCanPayByCard(res.getBoolean("can_pay_by_card"));
		PaymentModesObject.setCanPayOnline(res.getBoolean("can_pay_online"));
		
		PaymentModesObject.setMaxVoucherType(res.getInt("max_voucher_type"));
		PaymentModesObject.setCreatedBy(res.getInt("created_by"));
		PaymentModesObject.setCreatedAt(res.getString("created_at"));
		PaymentModesObject.setUpdatedBy(res.getInt("updated_by"));
		PaymentModesObject.setUpdatedAt(res.getString("updated_at"));
		PaymentModesObject.setPublishStatus(res.getBoolean("publish_status"));
		PaymentModesObject.setIsDeleted(res.getBoolean("is_deleted"));
		PaymentModesObject.setIsSynchable(res.getBoolean("is_synchable"));
		PaymentModesObject.setCanCashRefundable(res.getBoolean("can_cash_refundable"));
		PaymentModesObject.setCanCardRefundable(res.getBoolean("can_card_refundable"));
		PaymentModesObject.setCanCompanyRefundable(res.getBoolean("can_company_refundable"));
		PaymentModesObject.setCanVoucherRefundable(res.getBoolean("can_voucher_refundable"));
		PaymentModesObject.setCanOnlineRefund(res.getBoolean("can_online_refund"));
		
		PaymentModesObject.setCashRefundAccountNo(res.getString("cash_refund_account_no"));
		PaymentModesObject.setCanCashRefundable(res.getBoolean("can_cash_refundable"));
		PaymentModesObject.setAlternativeRefundMethodForVoucher(res.getBoolean("alternative_refund_method"));
		
		PaymentModesObject.setCanCashRound(res.getBoolean("can_cash_round"));
		PaymentModesObject.setCanCardRound(res.getBoolean("can_card_round"));
		PaymentModesObject.setCanCompanyRound(res.getBoolean("can_company_round"));
		PaymentModesObject.setCanVoucherRound(res.getBoolean("can_voucher_round"));
		PaymentModesObject.setCanOnlineRound(res.getBoolean("can_online_round"));
		
		PaymentModesObject.setTitleCash(res.getString("title_cash"));
		PaymentModesObject.setTitleCard(res.getString("title_card"));
		PaymentModesObject.setTitleCompany(res.getString("title_company"));
		PaymentModesObject.setTitleVoucher(res.getString("title_voucher"));
		PaymentModesObject.setTitleOnline(res.getString("title_online"));
		
		return PaymentModesObject;
	}
	
	public BeanPaymentModes getPaymentModes(){
		return mPosPaymentModesObject;
	}
	
	private void setPaymentModeTitle(){
		
		PaymentMode.get(PaymentMode.Cash.getValue()).setTitle(mPosPaymentModesObject.getTitleCash()); 
		PaymentMode.get(PaymentMode.Card.getValue()).setTitle(mPosPaymentModesObject.getTitleCard());
		PaymentMode.get(PaymentMode.Coupon.getValue()).setTitle(mPosPaymentModesObject.getTitleVoucher());
		PaymentMode.get(PaymentMode.Company.getValue()).setTitle(mPosPaymentModesObject.getTitleCompany());
		PaymentMode.get(PaymentMode.Online.getValue()).setTitle(mPosPaymentModesObject.getTitleOnline());
		
	}

}
