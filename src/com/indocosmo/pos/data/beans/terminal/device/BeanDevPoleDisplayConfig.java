package com.indocosmo.pos.data.beans.terminal.device;

import java.util.HashMap;
import java.util.Map;

import com.indocosmo.pos.common.PosLog;
import com.indocosmo.pos.common.enums.device.PortParity;
import com.indocosmo.pos.terminal.devices.poledisplay.PosDevicePoleDisplay.PoleDisplayCommands;
import com.indocosmo.pos.terminal.devices.poledisplay.PosDevicePoleDisplay.PoleDisplayMessages;

public final class BeanDevPoleDisplayConfig {

	private int mRowCount;
	private int mColumnCount;
	private String mMoveCommand;
	private String mClearCommand;
	private String mClearLineCommand;
	private String mPort;
	private String mStartupMessage;
	private String mNewBillMessage;
	private String mClosedMessage;
	
	private int mPortBaudRate;
	private int mPortDataBits;
	private PortParity mPortParity;
	private int mPortStopBits;
	
	
	/**
	 * @return the rowCount
	 */
	public int getRowCount() {
		return mRowCount;
	}
	/**
	 * @param rowCount the rowCount to set
	 */
	public void setRowCount(int rowCount) {
		mRowCount = rowCount;
	}
	/**
	 * @return the columnCount
	 */
	public int getColumnCount() {
		return mColumnCount;
	}
	/**
	 * @param columnCount the columnCount to set
	 */
	public void setColumnCount(int columnCount) {
		mColumnCount = columnCount;
	}
	
	/**
	 * @return the mCommands
	 */
	public Map<PoleDisplayCommands, Byte> getCommands() {
		Map<PoleDisplayCommands, Byte> pdCommands=null;
		try{
			pdCommands=new HashMap<PoleDisplayCommands, Byte>();
			pdCommands.put(PoleDisplayCommands.Clear, Byte.decode(mClearCommand));
			pdCommands.put(PoleDisplayCommands.Clear_Line, Byte.decode(mClearLineCommand));
			pdCommands.put(PoleDisplayCommands.Move, Byte.decode(mMoveCommand));
		}catch(Exception ex){
			PosLog.write(this, "getCommands", ex);
		}
		return pdCommands;
	}

	/**
	 * @return the mMessages
	 */
	public Map<PoleDisplayMessages, String> getMessages() {
		Map<PoleDisplayMessages, String> pdMessages=new HashMap<PoleDisplayMessages, String>();
		pdMessages.put(PoleDisplayMessages.Startup, mStartupMessage);
		pdMessages.put(PoleDisplayMessages.NewBill, mNewBillMessage);
		pdMessages.put(PoleDisplayMessages.Closed, mClosedMessage);
		return pdMessages;
	}

	
	/**
	 * @return the port
	 */
	public String getPort() {
		return mPort;
	}
	/**
	 * @param port the port to set
	 */
	public void setPort(String port) {
		mPort = port;
	}
	
	/**
	 * @return the moveCommand
	 */
	public String getCommandMove() {
		return mMoveCommand;
	}
	/**
	 * @param moveCommand the moveCommand to set
	 */
	public void setCommandMove(String moveCommand) {
		mMoveCommand = moveCommand;
	}
	/**
	 * @return the clearCommand
	 */
	public String getCommandClear() {
		return mClearCommand;
	}
	/**
	 * @param clearCommand the clearCommand to set
	 */
	public void setCommandClear(String clearCommand) {
		mClearCommand = clearCommand;
	}
	/**
	 * @return the clearLineCommand
	 */
	public String getCommandClearLine() {
		return mClearLineCommand;
	}
	/**
	 * @param clearLineCommand the clearLineCommand to set
	 */
	public void setCommandClearLine(String clearLineCommand) {
		mClearLineCommand = clearLineCommand;
	}
	/**
	 * @return the startupMessage
	 */
	public String getMessageStartup() {
		return mStartupMessage;
	}
	/**
	 * @param startupMessage the startupMessage to set
	 */
	public void setMessageStartup(String startupMessage) {
		mStartupMessage = startupMessage;
	}
	/**
	 * @return the newBillMessage
	 */
	public String getMessageNewBill() {
		return mNewBillMessage;
	}
	/**
	 * @param newBillMessage the newBillMessage to set
	 */
	public void setMessageNewBill(String newBillMessage) {
		mNewBillMessage = newBillMessage;
	}
	/**
	 * @return the closedBillMessage
	 */
	public String getMessageClosed() {
		return mClosedMessage;
	}
	/**
	 * @param closedBillMessage the closedBillMessage to set
	 */
	public void setMessageClosed(String closedBillMessage) {
		mClosedMessage = closedBillMessage;
	}
	/**
	 * @return the portBaudRate
	 */
	public int getPortBaudRate() {
		return mPortBaudRate;
	}
	/**
	 * @param portBaudRate the portBaudRate to set
	 */
	public void setPortBaudRate(int portBaudRate) {
		mPortBaudRate = portBaudRate;
	}
	/**
	 * @return the portDataBits
	 */
	public int getPortDataBits() {
		return mPortDataBits;
	}
	/**
	 * @param portDataBits the portDataBits to set
	 */
	public void setPortDataBits(int portDataBits) {
		mPortDataBits = portDataBits;
	}
	/**
	 * @return the portParity1
	 */
	public PortParity getPortParity() {
		return mPortParity;
	}
	/**
	 * @param portParity1 the portParity1 to set
	 */
	public void setPortParity(PortParity portParity) {
		mPortParity = portParity;
	}
	/**
	 * @return the stopBits
	 */
	public int getPortStopBits() {
		return mPortStopBits;
	}
	/**
	 * @param stopBits the stopBits to set
	 */
	public void setPortStopBits(int portStopBits) {
		mPortStopBits = portStopBits;
	}

}
