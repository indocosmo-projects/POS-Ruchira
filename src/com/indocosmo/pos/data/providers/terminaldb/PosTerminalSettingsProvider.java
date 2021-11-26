package com.indocosmo.pos.data.providers.terminaldb;

import java.sql.SQLException;

import javax.sql.rowset.CachedRowSet;

import com.indocosmo.pos.common.PosLog;
import com.indocosmo.pos.data.beans.terminal.BeanTerminalInfo;
import com.indocosmo.pos.data.beans.terminal.BeanTerminalSetting;
import com.indocosmo.pos.data.providers.shopdb.PosStationProvider;


public  class PosTerminalSettingsProvider extends PosTerminalDBProviderBase{
	

	private static BeanTerminalSetting  mTerminalSettings;

	/**
	 * 
	 */
	public PosTerminalSettingsProvider() {
		super("terminal");
		if(mTerminalSettings==null)
			loadData();
	}

	/**
	 * 
	 */
	private void loadData(){
		
		CachedRowSet crs=getData();
		if(crs!=null){
			try {
				if(crs.next()){
					
					mTerminalSettings=new BeanTerminalSetting();
					mTerminalSettings.setTerminalInfo(getTerminalInfo(crs.getString("terminal_code")));
					mTerminalSettings.setShopCode(crs.getString("shop_code"));
//					mTerminalSettings.setPrintKitchenReceiptWithPayment(crs.getBoolean("kitchen_receipt_at_payment"));
				}
			}catch (SQLException e) {
				mTerminalSettings=null;
				PosLog.write(this, "loadData", e);
			}finally{
				try {
					crs.close();
				} catch (SQLException e) {
					PosLog.write(this, "loadData", e);
				}
			}
		}
	}
	
	/**
	 * @param code
	 * @return
	 */
	private BeanTerminalInfo getTerminalInfo(String code){
		
		PosStationProvider pvdr=new PosStationProvider();
		final BeanTerminalInfo tInfo=pvdr.getStation(code);
		
		return tInfo;
	}

	/**
	 * @return
	 */
	public BeanTerminalSetting getTerminalSetting(){
		
		return mTerminalSettings;
	}

	/**
	 * @param terminalSettings
	 * @throws Exception
	 */
	public void setTerminalSettings(BeanTerminalSetting terminalSettings) throws Exception{
		
		try {
			
			beginTrans();
			deleteData();
//			final String sql="insert into terminal (shop_code,terminal_code,kitchen_receipt_at_payment) values('"+terminalSettings.getShopCode()+"','"
//					+terminalSettings.getCode()+"', "+ (terminalSettings.isPrintKitchenReceiptWithPayment()?1:0)+");";
			
			final String sql="insert into terminal (shop_code,terminal_code) values('"+terminalSettings.getShopCode()+"','"
					+terminalSettings.getCode()+"');";
			executeNonQuery(sql);
			commitTrans();
			mTerminalSettings=terminalSettings;
			
		} catch (Exception e) {
			
			PosLog.write(this, "setTerminalSettings", e);
			try {
				rollBack();
			} catch (SQLException e1) {
				PosLog.write(this, "setTerminalSettings", e);
			}
			
			throw new Exception("Failed save terminal setings. Please contact Administrator.");
		}

	}
	
	/**
	 * @return
	 */
	public boolean hasTerminalInfo(){
		
		return isExist("terminal_code is not null");
	}
	
//	/**
//	 * @param isEnabled
//	 * @throws Exception
//	 */
//	public void setKitchenReceiptAtPayment(boolean isEnabled) throws Exception{
//		
//		try {
//			
//			beginTrans();
//			final String sql="update "+ mTablename + " set kitchen_receipt_at_payment="+((isEnabled)?1:0);
//
//			executeNonQuery(sql);
//			commitTrans();
//
//		} catch (Exception e) {
//
//			PosLog.write(this, "setKitchenReceiptAtPayment", e);
//			try {
//				rollBack();
//			} catch (SQLException e1) {
//				PosLog.write(this, "setKitchenReceiptAtPayment", e);
//			}
//
//			throw new Exception("Failed save terminal setings. Please contact Administrator.");
//		}
//
//	}

}
