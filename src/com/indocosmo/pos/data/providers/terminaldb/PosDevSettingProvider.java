/**
 * 
 */
package com.indocosmo.pos.data.providers.terminaldb;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.sql.rowset.CachedRowSet;

import com.indocosmo.pos.common.PosLog;
import com.indocosmo.pos.data.beans.terminal.device.BeanDeviceSetting;

/**
 * @author jojesh
 *
 */
public final class PosDevSettingProvider extends PosTerminalDBProviderBase {

	public enum PosDevices{
		POLEDISPLAY,
		KITCHENPRINTER,
		CASHBOX,
		RECEIPTPRINTER,
		POSDEVICE,
		WEIGHINGMACHINE
	}

	private BeanDeviceSetting mDeviceSettings;
	private Map<PosDevices, String> mFiledLists;
	/**
	 * 
	 */
	public PosDevSettingProvider() {
		super("devices");
		mDeviceSettings=new BeanDeviceSetting();
		initlizefiledList();
		loadData();
	}

	private void initlizefiledList(){
		mFiledLists=new HashMap<PosDevSettingProvider.PosDevices, String>();
		mFiledLists.put(PosDevices.POLEDISPLAY, "has_pole_display");
		mFiledLists.put(PosDevices.KITCHENPRINTER, "has_kitchen_printer");
		mFiledLists.put(PosDevices.CASHBOX, "has_cash_box");
		mFiledLists.put(PosDevices.RECEIPTPRINTER, "has_receipt_printer");
		mFiledLists.put(PosDevices.POSDEVICE, "has_pos_device");
		mFiledLists.put(PosDevices.WEIGHINGMACHINE, "has_weighing");
	}

	private void loadData(){
		CachedRowSet crs=getData();
		if(crs!=null){
			try {
				if(crs.next()){
					mDeviceSettings.setAttachedCashDrawer(crs.getBoolean("has_cash_box"));
					mDeviceSettings.setAttachedKitchenPrinter(crs.getBoolean("has_kitchen_printer"));
					mDeviceSettings.setAttachedPoleDisplay(crs.getBoolean("has_pole_display"));
					mDeviceSettings.setAttachedReceiptPrinter(crs.getBoolean("has_receipt_printer"));
					mDeviceSettings.setAttachedPosDevice(crs.getBoolean("has_pos_device"));
					mDeviceSettings.setAttacheddWeighing(crs.getBoolean("has_weighing"));
				}
			}catch (SQLException e) {
				mDeviceSettings=null;
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

	public BeanDeviceSetting getDeviceSettings(){
		return mDeviceSettings;
	}

	public void setDevicesListSetting(BeanDeviceSetting deviceSettings){
		try {
			beginTrans();
			deleteData();
			final String sql="insert into "+mTablename+" values("+deviceSettings.isAttachedPoleDisplay() +","
					+deviceSettings.isAttachedReceiptPrinter() +","
					+deviceSettings.isAttachedKitchenPrinter()  +","
					+deviceSettings.isAttachedCashDrawer() +","
					+deviceSettings.isAttachedPosDevice() +","
					+deviceSettings.isAttachedWeighing() +","
					+");";
			executeNonQuery(sql);
			commitTrans();
			mDeviceSettings=deviceSettings;
		} catch (Exception e) {
			PosLog.write(this, "setDeviceSettings", e);
			try {
				rollBack();
			} catch (SQLException e1) {
				PosLog.write(this, "setDeviceSettings", e1);
			}
		}
	}

	public boolean isDeviceAttached(PosDevices device){
		boolean isAttached=false;
		switch (device) {
		case POLEDISPLAY:
			isAttached=mDeviceSettings.isAttachedPoleDisplay();
			break;
		case CASHBOX:
			isAttached=mDeviceSettings.isAttachedCashDrawer();
			break;
		case KITCHENPRINTER:
			isAttached=mDeviceSettings.isAttachedKitchenPrinter();
			break;
		case RECEIPTPRINTER:
			isAttached=mDeviceSettings.isAttachedReceiptPrinter();
			break;
		case POSDEVICE:
			isAttached=mDeviceSettings.isAttachedPosDevice();
			break;
		case WEIGHINGMACHINE:
			isAttached=mDeviceSettings.isAttachedWeighing();
			break;
		default:
			break;
		}
		return isAttached;
	}

	public void setDeviceAttached(PosDevices device, boolean isAttached){
		String sql=	"update "+mTablename+" set "+ mFiledLists.get(device) +"="+((isAttached)?1:0);
		try{
			beginTrans();
			executeNonQuery(sql);
			commitTrans();
			loadData();
		}catch (Exception ex){
			PosLog.write(this, "setDevice", ex);
			try {
				rollBack();
			} catch (SQLException e1) {
				PosLog.write(this, "setDevice", e1);
			}
		}

	}


}
