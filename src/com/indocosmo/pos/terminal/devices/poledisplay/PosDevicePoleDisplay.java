package com.indocosmo.pos.terminal.devices.poledisplay;

import gnu.io.CommPortIdentifier;
import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.UnsupportedCommOperationException;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

import com.indocosmo.pos.common.PosLog;
import com.indocosmo.pos.common.utilities.PosCurrencyUtil;
import com.indocosmo.pos.common.utilities.PosSaleItemUtil;
import com.indocosmo.pos.data.beans.BeanSaleItem;
import com.indocosmo.pos.data.beans.terminal.device.BeanDevPoleDisplayConfig;
import com.indocosmo.pos.data.providers.terminaldb.PosDevPoleDisplayConfigProvider;
import com.indocosmo.pos.terminal.devices.PosDeviceBase;
import com.indocosmo.pos.terminal.devices.PosDeviceUtil;


public final class PosDevicePoleDisplay extends PosDeviceBase {

	private static final int AMOUNT_COL_MAX_LENGTH=8;


	private int mTitleMaxLength=0;

	private static PosDevicePoleDisplay mClassInstance;
	private OutputStream mPDStream;
	private SerialPort mSerialPort;
	private boolean mIsDeviceInitilized=false;
	private boolean mPaused=false;

	private int mDisplayColumns;
	private int mDisplayRows;
	private String mPoleDisplayPort="";
	private Map<PoleDisplayCommands, Byte> mPDCommands;
	private Map<PoleDisplayMessages, String> mPDMessages;

	public enum PoleDisplayCommands{
		Move,
		Clear,
		Clear_Line;
	}

	public enum PoleDisplayMessages{
		Startup,
		NewBill,
		Closed;
	}

	private PosDevicePoleDisplay() {

	}

	public static PosDevicePoleDisplay getInstance(){
		if(mClassInstance==null)
			mClassInstance=new PosDevicePoleDisplay();
		return mClassInstance;
	}

	private boolean loadPoleDisplaySettings(){
		PosDevPoleDisplayConfigProvider posPDConfigProvider=new PosDevPoleDisplayConfigProvider();
		BeanDevPoleDisplayConfig pdConfig=posPDConfigProvider.getDeviceConfiguration();
		if(pdConfig!=null){
			mPoleDisplayPort=pdConfig.getPort();
			mDisplayColumns=pdConfig.getColumnCount();
			mDisplayRows=pdConfig.getRowCount();
			mTitleMaxLength=mDisplayColumns-AMOUNT_COL_MAX_LENGTH;
			mPDCommands=pdConfig.getCommands();
			mPDMessages=pdConfig.getMessages();
			return true;
		}else
			return false;
	}

	private boolean initPoleDisplay() throws Exception{
		if(!loadPoleDisplaySettings()) return false;
		try
		{
			CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier(mPoleDisplayPort);

			if (portIdentifier.isCurrentlyOwned())
				PosLog.write(this,"initPoleDisplay","Port in use!");
			else {
				mSerialPort = (SerialPort) portIdentifier.open("ListPortClass", 300);
				mSerialPort.setSerialPortParams(9600, SerialPort.DATABITS_8, 
						SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
				mPDStream = mSerialPort.getOutputStream();
				mIsDeviceInitilized=true;
				clear(); 
				displayMessageCenterd(mPDMessages.get(PoleDisplayMessages.Startup));
				mPDStream.flush();
			}
		} catch (NoSuchPortException e){
			PosLog.write(this,"initPoleDisplay",e);
			throw new Exception("Failed to initialize pole display. Please check log for more details.");
		} catch (IOException e) {
			PosLog.write(this,"initPoleDisplay",e);
			throw new Exception("Failed to initialize pole display. Please check log for more details.");
		} catch (PortInUseException e){
			PosLog.write(this,"initPoleDisplay",e);
			throw new Exception("Failed to initialize pole display. Please check log for more details.");
		} catch (UnsupportedCommOperationException e) {
			PosLog.write(this,"initPoleDisplay",e);
			throw new Exception("Failed to initialize pole display. Please check log for more details.");
		} catch (UnsatisfiedLinkError e) {
//			PosLog.write(this,"initPoleDisplay","rxtx driver not found");
//			return false;
		}
		return mIsDeviceInitilized;
	}

	public void close(){
		if(!mIsDeviceInitilized) return ;
		displayMessageCenterd(mPDMessages.get(PoleDisplayMessages.Closed));
		return ;
	}


	public void clear(){
		if(!mIsDeviceInitilized) return;
		writeTo(mPDCommands.get(PoleDisplayCommands.Clear));
	}

	public void disPlayBillTotal(double total){
		disPlayBillTotal(total,2);
	}

	/**
	 * @param total
	 * @param line
	 * Display bill total here..
	 */
	public void disPlayBillTotal(double total,int line){
		if(!mIsDeviceInitilized) return;
		
		final String totalTitle=String.format("%-"+mTitleMaxLength+"s","Total:");
		byte[] command=new byte[]{mPDCommands.get(PoleDisplayCommands.Move),PosDeviceUtil.getHexValue(mDisplayColumns*(line-1))};
		writeTo(command);
		writeTo(totalTitle.getBytes()); 

		final String totalAmount=String.format("%"+AMOUNT_COL_MAX_LENGTH+"s",PosCurrencyUtil.format(total));
		command=new byte[]{mPDCommands.get(PoleDisplayCommands.Move),PosDeviceUtil.getHexValue((mDisplayColumns*(line-1))+mTitleMaxLength)};
		writeTo(command);
		writeTo(totalAmount.getBytes());

	}

	/**
	 * @param billTotal
	 * @param balance
	 * Do the bill settlement here, 
	 */
	public void disPlayBillSettlement(double billTotal, double balance){
		if(!mIsDeviceInitilized) return;
		clear();
		
		final String totalTitle=String.format("%-"+mTitleMaxLength+"s","Total:");
		byte[] command=new byte[]{mPDCommands.get(PoleDisplayCommands.Move),PosDeviceUtil.getHexValue(0)};
		writeTo(command);
		writeTo(totalTitle.getBytes()); 

		final String totalAmount=String.format("%"+AMOUNT_COL_MAX_LENGTH+"s",PosCurrencyUtil.format(billTotal));
		command=new byte[]{mPDCommands.get(PoleDisplayCommands.Move),PosDeviceUtil.getHexValue(mTitleMaxLength)};
		writeTo(command);
		writeTo(totalAmount.getBytes());

		final String balanceTitle=String.format("%-"+mTitleMaxLength+"s","Change:");
		command=new byte[]{mPDCommands.get(PoleDisplayCommands.Move),PosDeviceUtil.getHexValue(mDisplayColumns)};
		writeTo(command);
		writeTo(balanceTitle.getBytes()); 

		final String changeAmount=String.format("%"+AMOUNT_COL_MAX_LENGTH+"s",PosCurrencyUtil.format(balance));
		command=new byte[]{mPDCommands.get(PoleDisplayCommands.Move),PosDeviceUtil.getHexValue(mDisplayColumns+mTitleMaxLength)};
		writeTo(command);
		writeTo(changeAmount.getBytes());

	}
	
	/**
	 * @param billTotal
	 * @param balance
	 * Do the bill settlement here, 
	 */
	public void disPlayPartialBillSettlement(double billTotal, double splitAmount){
		if(!mIsDeviceInitilized) return;
		clear();
		
		final String totalTitle=String.format("%-"+mTitleMaxLength+"s","Bill Total:");
		byte[] command=new byte[]{mPDCommands.get(PoleDisplayCommands.Move),PosDeviceUtil.getHexValue(0)};
		writeTo(command);
		writeTo(totalTitle.getBytes()); 

		final String totalAmount=String.format("%"+AMOUNT_COL_MAX_LENGTH+"s",PosCurrencyUtil.format(billTotal));
		command=new byte[]{mPDCommands.get(PoleDisplayCommands.Move),PosDeviceUtil.getHexValue(mTitleMaxLength)};
		writeTo(command);
		writeTo(totalAmount.getBytes());

		final String balanceTitle=String.format("%-"+mTitleMaxLength+"s","Part. Pay:");
		command=new byte[]{mPDCommands.get(PoleDisplayCommands.Move),PosDeviceUtil.getHexValue(mDisplayColumns)};
		writeTo(command);
		writeTo(balanceTitle.getBytes()); 

		final String changeAmount=String.format("%"+AMOUNT_COL_MAX_LENGTH+"s",PosCurrencyUtil.format(splitAmount));
		command=new byte[]{mPDCommands.get(PoleDisplayCommands.Move),PosDeviceUtil.getHexValue(mDisplayColumns+mTitleMaxLength)};
		writeTo(command);
		writeTo(changeAmount.getBytes());

	}

	/**
	 * @param saleItem
	 * Display the item detail here..
	 * 
	 */
	public void disPlayItemDtl(BeanSaleItem saleItem){
		if(!mIsDeviceInitilized) return;
		clear();
		String itemTitle=saleItem.getName();
		itemTitle=(itemTitle.length()>mTitleMaxLength)?itemTitle.substring(0, mTitleMaxLength-1):itemTitle;
		itemTitle=String.format("%-"+mTitleMaxLength+"s",itemTitle);

		byte[] command=new byte[]{mPDCommands.get(PoleDisplayCommands.Move),0x00};
		writeTo(command);
		writeTo(itemTitle.getBytes());

		final String totalAmount=String.format("%"+AMOUNT_COL_MAX_LENGTH+"s",PosCurrencyUtil.format(PosSaleItemUtil.getGrandTotal(saleItem)));
		command=new byte[]{mPDCommands.get(PoleDisplayCommands.Move),PosDeviceUtil.getHexValue(mTitleMaxLength)};
		writeTo(command);
		writeTo(totalAmount.getBytes());

	}

	public void displayNewBillMessage(){
		if(!mIsDeviceInitilized) return;
		
		displayMessageCenterd(mPDMessages.get(PoleDisplayMessages.NewBill));
	}

	public void displayMessageCenterd(String message){
		if(message.length()<mDisplayColumns)
			message=String.format("%"+(mDisplayColumns/2-message.length()/2)+"s", "")+message;
		displayMessage(1,1,message);
	}

	public void displayMessage(String message){
		displayMessage(1,1,message);
	}

	public void displayMessage(int line, int col, String message){ 
		if(!mIsDeviceInitilized) return;
		
		clear();
		final int startCol =(line-1)*mDisplayColumns+(col-1);
		byte[] command=new byte[]{mPDCommands.get(PoleDisplayCommands.Move),PosDeviceUtil.getHexValue(startCol)};
		writeTo(command);
		writeTo(mPDCommands.get(PoleDisplayCommands.Clear_Line));
		writeTo(message.getBytes());
	}

	private void writeTo(byte[] text){
		if(!mIsDeviceInitilized) return;
		try {
			mPDStream.write(text);
			mPDStream.flush();
		} catch (IOException e) {
			PosLog.write(this,"writeTo",e);
		}
	}

	private void writeTo(byte text){ 
		writeTo(new byte[]{text});
	}

	public int getLineLength(){
		return mDisplayColumns;
	}

	public void setPaused(boolean paused){
		mPaused=paused;
	}

	@Override
	public boolean initialize() throws Exception{
		try {
			return initPoleDisplay();
		} catch (Exception e) {
			PosLog.write(this, "initialize", e);
			throw e;
		}
	}

	@Override
	public void shutdown() throws Exception{
		if(mIsDeviceInitilized) {
			try {
//				displayMessageCenterd(mPDMessages.get(PoleDisplayMessages.Closed));
				clear();
				mPDStream.close();
				mSerialPort.close();
				mClassInstance=null;
				mIsDeviceInitilized=false;
			} catch (IOException e) {
				PosLog.write(this,"close",e);
			}
		}
	}

	

}
