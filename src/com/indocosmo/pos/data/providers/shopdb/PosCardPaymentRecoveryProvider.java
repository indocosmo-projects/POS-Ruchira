/**
 * 
 */
package com.indocosmo.pos.data.providers.shopdb;

import java.sql.SQLException;

import javax.sql.rowset.CachedRowSet;

import com.indocosmo.pos.common.PosLog;

/**
 * @author deepak
 *
 */
public class PosCardPaymentRecoveryProvider extends PosShopDBProviderBase{
	
	/**
	 * 
	 */
	public PosCardPaymentRecoveryProvider() {
		super("card_payment_recovery");
	}
	
	/**
	 * @param eftPurchaseMesdsage
	 * @return
	 * For the recovery process keep the purchase message in the database. 
	 */
	public boolean insertEftPurchaseMessage(String eftPurchaseMesdsage){
		boolean status = false;
		String insertSQL ="insert into card_payment_recovery (eft_purchase_message) values('"+eftPurchaseMesdsage+"')";
		try {
			beginTrans();
			executeNonQuery(insertSQL);
			commitTrans();
			status = true;
		} catch (SQLException e) {
			PosLog.write(this, "insertEftPurchaseMessage", e);
			try {
				rollBack();
			} catch (SQLException e1) {
				PosLog.write(this, "insertEftPurchaseMessage", e1);
			}
			status = false;
		}
		
		
		return status;
		
	}
	
	/**
	 * @return the purchase message that saved.
	 * 
	 */
	public String getEftPurchaseMessage(){
		String EftPurchaseMessage = null;
		CachedRowSet crs =getData();
		if(crs!=null){
			try {
				if(crs.next()){
					EftPurchaseMessage = crs.getString("eft_purchase_message");
				}
			} catch (Exception e) {
				PosLog.write(this, "getEftPurchaseMessage", e);
			}
		}
		return EftPurchaseMessage;
	}
	
	public void deleteEftPurchaseMessage(){
		
		try {
			deleteData();
		} catch (SQLException e) {
			PosLog.write(this, "deleteEftPurchaseMessage", e);
		}
	}

}
