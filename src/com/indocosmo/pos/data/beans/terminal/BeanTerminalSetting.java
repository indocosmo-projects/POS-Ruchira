package com.indocosmo.pos.data.beans.terminal;

import com.indocosmo.pos.common.enums.PosTerminalServiceType;

public final class BeanTerminalSetting {

	private String mShopCode;
	
	private BeanTerminalInfo mTerminalInfo;
	
//	private boolean printKitchenReceiptWithPayment; 
	
	/**
	 * @return the mTerminalCode
	 */
	public String getCode() {
		return mTerminalInfo.getCode();
	}

	/**
	 * @return the mTerminalName
	 */
	public String getName() {
		return mTerminalInfo.getName();
	}

	/**
	 * @return the mTerminalType
	 */
	public PosTerminalServiceType getType() {
		return mTerminalInfo.getServiceType();
	}

	/**
	 * @return the mShopCode
	 */
	public String getShopCode() {
		return mShopCode;
	}

	/**
	 * @param mShopCode the mShopCode to set
	 */
	public void setShopCode(String shopCode) {
		this.mShopCode = shopCode;
	}

	/**
	 * @return the mTerminalInfo
	 */
	public BeanTerminalInfo getTerminalInfo() {
		return mTerminalInfo;
	}

	/**
	 * @param mTerminalInfo the mTerminalInfo to set
	 */
	public void setTerminalInfo(BeanTerminalInfo terminalInfo) {
		this.mTerminalInfo = terminalInfo;
	}

//	/**
//	 * @return the printKitchenReceiptWithPayment
//	 */
//	public boolean isPrintKitchenReceiptWithPayment() {
//		return printKitchenReceiptWithPayment;
//	}
//
//	/**
//	 * @param printKitchenReceiptWithPayment the printKitchenReceiptWithPayment to set
//	 */
//	public void setPrintKitchenReceiptWithPayment(
//			boolean printKitchenReceiptWithPayment) {
//		this.printKitchenReceiptWithPayment = printKitchenReceiptWithPayment;
//	}
	
}
