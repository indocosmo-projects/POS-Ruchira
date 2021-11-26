/**
 * 
 */
package com.indocosmo.pos.terminal.devices.eftpos;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.text.DecimalFormat;

import javax.swing.JDialog;

import com.indocosmo.pos.common.PosEnvSettings;
import com.indocosmo.pos.common.PosLog;
import com.indocosmo.pos.common.utilities.PosFormUtil;
import com.indocosmo.pos.data.beans.terminal.device.BeanDevEFTConfig;
import com.indocosmo.pos.data.providers.shopdb.PosCardPaymentRecoveryProvider;
import com.indocosmo.pos.forms.PosCardTxnDetailsForm;
import com.indocosmo.pos.forms.listners.IPosCardTxnDetailsFormListner;
import com.indocosmo.pos.forms.messageboxes.PosMessageBoxForm;
import com.indocosmo.pos.forms.messageboxes.PosMessageBoxForm.MessageBoxButtonTypes;
import com.indocosmo.pos.forms.messageboxes.listners.PosMessageBoxFormListnerAdapter;
import com.indocosmo.pos.terminal.devices.PosIODevice;
import com.indocosmo.pos.terminal.devices.eftpos.adapters.IPosDeviceEFTListner;
import com.indocosmo.pos.terminal.devices.eftpos.enums.EFTLogonStatus;
import com.indocosmo.pos.terminal.devices.eftpos.enums.EFTMessageType;
import com.indocosmo.pos.terminal.devices.eftpos.enums.EFTPolStatus;
import com.indocosmo.pos.terminal.devices.eftpos.enums.EFTPurchaseStatus;
import com.indocosmo.pos.terminal.devices.eftpos.enums.EFTResponseType;
import com.indocosmo.pos.terminal.devices.eftpos.enums.EFTSigStatus;
import com.indocosmo.pos.terminal.devices.eftpos.exceptions.EFTDeviceBusyException;
import com.indocosmo.pos.terminal.devices.eftpos.messageobjects.EFTMessageBase;
import com.indocosmo.pos.terminal.devices.eftpos.messageobjects.EFTMessageMAN;
import com.indocosmo.pos.terminal.devices.eftpos.messageobjects.EFTResponseMessageDSP;
import com.indocosmo.pos.terminal.devices.eftpos.messageobjects.EFTResponseMessageERR;
import com.indocosmo.pos.terminal.devices.eftpos.messageobjects.EFTResponseMessageLOG;
import com.indocosmo.pos.terminal.devices.eftpos.messageobjects.EFTResponseMessageMAN;
import com.indocosmo.pos.terminal.devices.eftpos.messageobjects.EFTResponseMessagePOL;
import com.indocosmo.pos.terminal.devices.eftpos.messageobjects.EFTResponseMessagePUR;
import com.indocosmo.pos.terminal.devices.eftpos.messageobjects.EFTResponseMessageSIG;

/**
 * @author jojesh && deepak.
 * 
 */
public final class PosDeviceEFT extends PosIODevice {

	private static final String MERCHANT_ID = "1";
	private static final String EFT_POS_VERSION = "2.1";

	private static PosDeviceEFT mSingeInstance;
	private static JDialog mParent;
	private PosMessageBoxForm eftMsgForm;
	private OutputStream mOutStream = null;
	private Socket mSocket = null;
	private InputStream mInStream = null;
	private String mHostIP;
	private int mHostPort;
	private int mSocketCreationTimeOut;
	private DecimalFormat myFormatter = new DecimalFormat("000000.00");
	
	private BeanDevEFTConfig mPosEFTConfigObject;
	private PosCardPaymentRecoveryProvider mCardPaymentRecoveryProvider;
	private boolean mTxnApproved = false;
	private boolean reTry = false;
	private boolean TxnCompleated = false;
	private EFTResponseMessagePUR purchaseMessageOnManualEntry = null;
	private String strPurchaseAmount="0";
	private String strCashAmount="0";
	private String strRefferenceId = null;
	private int mSocketTimeOut;

	private PosDeviceEFT() {
		loadEFTSettings();
	}

	/**
	 * @return the singleton instance of the class PosDeviceEFT.
	 */
	public static PosDeviceEFT getInstance() {
		if (mSingeInstance == null)
			mSingeInstance = new PosDeviceEFT();
		return mSingeInstance;
	}

	/**
	 * @return
	 * Load the POS device settings from the database. 
	 */
	private boolean loadEFTSettings(){
		
		mPosEFTConfigObject = PosEnvSettings.getInstance().getEFTConfiguration();
		
		mSocketCreationTimeOut = PosEnvSettings.getInstance().getSocketCreationTimeOut();
		mHostIP = mPosEFTConfigObject.getEFTHostIP();
		mHostPort = mPosEFTConfigObject.getEFTHostPort();
		mSocketTimeOut = mPosEFTConfigObject.getSocketTimeOut();
		
		PosLog.debug("mHostIP = " + mPosEFTConfigObject.getEFTHostIP());
		PosLog.debug("mHostPort = " + mPosEFTConfigObject.getEFTHostPort());
		PosLog.debug("mSocketTimeOut = " + mPosEFTConfigObject.getSocketTimeOut());
		
		return true;
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indocosmo.pos.terminal.devices.IPosDeviceBase#initialize()
	 */
	@Override
	public boolean initialize() throws Exception {
//		if(!loadEFTSettings()) return false;
//		open();
		openPort();
		close();
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indocosmo.pos.terminal.devices.IPosDeviceBase#shutdown()
	 */
	@Override
	public void shutdown() throws Exception {
		close();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indocosmo.pos.terminal.devices.IPosDeviceBase#open()
	 */
	@Override
	public void open() throws Exception {
		
		PosLog.debug("EFTPOS -:-> Connecting to terminal...");
		PosLog.debug("EFTPOS -:-> Using IP " + mHostIP);
		PosLog.debug("EFTPOS -:-> Using Port " + mHostPort);

		mSocket = new Socket(mHostIP, mHostPort);
		mSocket.setSoTimeout(mSocketTimeOut);
		mOutStream = mSocket.getOutputStream();
		mInStream = mSocket.getInputStream();
		PosLog.debug("EFTPOS -:-> Connected to terminal!!!");
		mOutStream = mSocket.getOutputStream();
	}

	/**
	 * @return status
	 * @throws Exception
	 * 
	 * Opening the socket 
	 * returns true if success else false.
	 */
	public boolean openPort() throws Exception {
		boolean status = false;
		
		PosLog.debug("EFTPOS -:-> Connecting to terminal...");
		PosLog.debug("EFTPOS -:-> Using IP " + mHostIP);
		PosLog.debug("EFTPOS -:-> Using Port " + mHostPort);

		try{
			
			mSocket = new Socket();
			mSocket.connect(new InetSocketAddress(mHostIP, mHostPort),mSocketCreationTimeOut);
			mSocket.setSoTimeout(mSocketTimeOut);
			mOutStream = mSocket.getOutputStream();
			mInStream = mSocket.getInputStream();
			PosLog.debug("EFTPOS -:-> Connected to terminal!!!");
			mOutStream = mSocket.getOutputStream();
			status = true;
		}catch (Exception e) {
			
			PosLog.write(this, "openPort", e);
			status = false;
		}
		return status;
		
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.pos.terminal.devices.IPosDeviceBase#close()
	 * 
	 * Closes the currently open socket.
	 */
	@Override
	public void close() throws Exception {
		if (mSocket != null && mSocket.isConnected()) {
			mSocket.close();
			mOutStream.close();
			mInStream.close();
		}
		mSocket = null;
		mOutStream = null;
		mInStream = null;
	}

	/**
	 * @return the status of the socket.
	 * @throws IOException
	 * @throws InterruptedException
	 * 
	 * Sending pole message to the POS devise 
	 * Getting response , and from the response message get the current status of the device.
	 */
	public EFTPolStatus getDeviceStatus() throws IOException,
	InterruptedException {
		final String polMsg = getPOLMessage();
		EFTPolStatus status = EFTPolStatus.BUSY;
		if (sendMessage(polMsg)) {
			byte[] resp = getResponse();
			mOutStream.write(EFTResponseType.ACK.getValue());
			String respMsg = PosDeviceEFTUtil.decodeMessage(resp);
			EFTResponseMessagePOL polMsgObject=(EFTResponseMessagePOL)PosDeviceEFTUtil.getEFTResponseMessageObject(respMsg);
			status = polMsgObject.getStatus();
		}
		return status;
	}

	public EFTLogonStatus doLogon() throws IOException, InterruptedException {
		PosLog.debug("EFTPOS -:-> Trying to LOGON...");
		final String logonMsg = PosDeviceEFTUtil.getEFTUID() + ","
				+ EFTMessageType.LOG + "," + MERCHANT_ID + ",POS" + ",1";
		EFTLogonStatus status = EFTLogonStatus.DECLINED;
		EFTMessageType rspMsgType = EFTMessageType.DSP;
		if (sendMessage(logonMsg)) {
			do {
				byte[] resp = getResponse();
				mOutStream.write(EFTResponseType.ACK.getValue());
				String respMsg = PosDeviceEFTUtil.decodeMessage(resp);
				EFTMessageBase eftMsgObject=PosDeviceEFTUtil.getEFTResponseMessageObject(respMsg);
				rspMsgType =eftMsgObject.getMessageType();
				switch (rspMsgType) {
				case DSP:
					PosLog.debug("EFTPOS -:-> LOGON Processing...");
					continue;
				case LOG:
					status=((EFTResponseMessageLOG)eftMsgObject).getStatus();
					break;
				case ERR:
					if(mListner!=null)
						mListner.onError("Unknow error has been occured");
					status=EFTLogonStatus.DECLINED;
					break;
				}
				PosLog.debug("EFTPOS -:-> LOGON " + status);
				break;
			} while (true);
		}
		return status;
	}

	/**
	 * Main Method In The Communication.
	 * 
	 * @param message
	 * @return
	 * @throws IOException
	 * @throws InterruptedException
	 * 
	 * Send the purchase message build to the  pos device and communicating with the devise 
	 * 
	 */
	private EFTMessageBase doPurchase(String message) throws IOException, InterruptedException{
		if(mListner!=null)
			mListner.onPurchaseRequestReceived();
		EFTPurchaseStatus status = EFTPurchaseStatus.DECLINED;
		EFTMessageBase returnMsg=null;
		mCardPaymentRecoveryProvider = new PosCardPaymentRecoveryProvider();
		if (sendMessage(message)) {
			mCardPaymentRecoveryProvider.insertEftPurchaseMessage(message);
			do {
//				byte[] resp = getResponse();
				byte[] resp = getPurchaseResponse(message);
				if(resp==null){
					if(purchaseMessageOnManualEntry!=null){
						returnMsg = purchaseMessageOnManualEntry;
						purchaseMessageOnManualEntry = null;
						status = EFTPurchaseStatus.ACCEPTED;
						mCardPaymentRecoveryProvider.deleteEftPurchaseMessage();
						break;
					}else{
						mCardPaymentRecoveryProvider.deleteEftPurchaseMessage();
						break;
					}
				}else{
					
					mOutStream.write(EFTResponseType.ACK.getValue());
					String respMsg = PosDeviceEFTUtil.decodeMessage(resp);
					EFTMessageBase eftMsgObject= PosDeviceEFTUtil.getEFTResponseMessageObject(respMsg);
					switch (eftMsgObject.getMessageType()) {
					case DSP:
						EFTResponseMessageDSP dspMsgObject=(EFTResponseMessageDSP)eftMsgObject;
						procDspRequest(dspMsgObject);
						continue;
					case SIG:
						EFTResponseMessageSIG dspSigObject=(EFTResponseMessageSIG)eftMsgObject;
						procSigRequest(dspSigObject);
						continue;
					case PUR:
						EFTResponseMessagePUR purMsg=(EFTResponseMessagePUR)eftMsgObject;
						status = purMsg.getStatus();
						returnMsg=purMsg;
						break;
					case MAN:
						EFTResponseMessageMAN manMsg=(EFTResponseMessageMAN)eftMsgObject;
						status = manMsg.getStatus();
						returnMsg=manMsg;
						break;
					case ERR:
						EFTResponseMessageERR errMsg=(EFTResponseMessageERR)eftMsgObject;
						PosLog.write(PosDeviceEFT.this, "doPurchase", errMsg.getDisplayMessage());
						if(mListner!=null)
							mListner.onError("Unknown error has been occured. Pleaase contact administrator.");
						status=EFTPurchaseStatus.DECLINED;
						break;
					}
					
				}
				
				PosLog.debug("EFTPOS -:-> PURCHASE " + status);
				mCardPaymentRecoveryProvider.deleteEftPurchaseMessage();
				break;
			} while (true);
		}
//		sendMessage(message);
//		byte[] resp = getResponse();
//		mOutStream.write(EFTResponseType.ACK.getValue());
//		String respMsg = PosDeviceEFTUtil.decodeMessage(resp);
		if(mListner!=null)
			mListner.onPurchaseCompleted(null, null);
		return returnMsg;
	}

	private boolean onYes = false;
	
	/**
	 * @param message
	 * @return Response message from the POS device.
	 * @throws IOException
	 * @throws InterruptedException
	 * 
	 * Getting response message and if fails (Connection loss or any interruption ) give us 
	 * a dialog to ask about the communication status and the chance to retry also.
	 */
	private byte[] getPurchaseResponse(final String message) throws IOException, InterruptedException{
		reTry = false;
		onYes = false;
		byte[] response = null;
		response = getPurResponse();
		if(response==null){
			EFTPolStatus devStatus = checkDeviseStatus();
			if(devStatus == EFTPolStatus.IDLE||devStatus == EFTPolStatus.BUSY) {
				sendMessage(message);
				response = getPurResponse();
				
			}else{
				showEFTerrorMessage(new PosMessageBoxFormListnerAdapter() {
					@Override
					public void onNoButtonPressed() {
						purchaseMessageOnManualEntry = null;
						return;
					}

					@Override
					public void onYesButtonPressed() {
						TxnCompleated = false;
						onYes = true;
						setTxnDetails();
					}

					/* (non-Javadoc)
					 * @see com.indocosmo.pos.forms.messageboxes.listners.PosMessageBoxFormListnerAdapter#onCancelButtonPressed()
					 */
					@Override
					public void onCancelButtonPressed() {
						reTry = true;
 					}
					
					
				});
				if(reTry){
					response = getPurchaseResponse(message);
				}
				if(onYes){
					int i= 0;
					do{
						i++;
						PosLog.debug("EFTPOS -:-> WAITING " + i);
						if(getTxnSts()){
							break;
						}
					}while(true);
				}
				
			}
		}
		
		return response;
	}
	
	private boolean getTxnSts(){
		return TxnCompleated;
	}
	
	/**
	 * @return
	 * Show the form(PosCardTxnDetailsForm) for entering the transaction details manually. 
	 */
	private boolean setTxnDetails(){
		purchaseMessageOnManualEntry = null;
		PosCardTxnDetailsForm cardTxnDetailsForm = new PosCardTxnDetailsForm();
		cardTxnDetailsForm.setCancelButtonVisible(false);
		cardTxnDetailsForm.setListner(cardTxnDetailsFormListner);
		cardTxnDetailsForm.setCardPurchaseAmount(strPurchaseAmount);
		cardTxnDetailsForm.setCashAmount(strCashAmount);
		PosFormUtil.showLightBoxModal(null, cardTxnDetailsForm);
		return true;
	}
	/**
	 * PosCardTxnDetailsForm listener.
	 */
	private IPosCardTxnDetailsFormListner cardTxnDetailsFormListner = new IPosCardTxnDetailsFormListner() {
		
		@Override
		public void onTxnCompleted(EFTResponseMessagePUR purchaseMessage) {
			purchaseMessageOnManualEntry =  purchaseMessage;
			purchaseMessageOnManualEntry.setStatus(EFTPurchaseStatus.ACCEPTED);
			TxnCompleated = true;
		}
	};
	
	private boolean getTxnStatus(){
		return mTxnApproved;
	}
	
	
	/**
	 * @return
	 * Check the POS device status.
	 */
	private EFTPolStatus checkDeviseStatus(){
		EFTPolStatus devStatus = null;
		try {
			PosDeviceEFT eft=getInstance();
			if (eft.openPort()) {
				devStatus = eft.getDeviceStatus();
				}
		} catch (IOException e) {
			devStatus = null;
		} catch (InterruptedException e) {
			devStatus = null;
		} catch (Exception e) {
			devStatus = null;
		}
		return devStatus;
	}
	
	/**
	 * @param listner
	 * Dialog form asking about POS device transaction status, and recover option(Retry).
	 */
	private void showEFTerrorMessage(PosMessageBoxFormListnerAdapter listner) {
		PosFormUtil.showQuestionMessageBox(null, MessageBoxButtonTypes.YesNoCancel,
				"Communication with EFTPOS device lost. Check terminal, EFTPOS txn approved ?", listner,"Retry");
	}
	private byte[] getPurResponse(){
		byte[] response = null;
		try {
			response = getResponse();
		} catch (IOException e) {
			response = null;
		}
		return response;
	}
	
	
	
	/**
	 * @param cardAmount
	 * @param cashAmount
	 * @param msgForm
	 * @return
	 * @throws IOException
	 * @throws InterruptedException
	 * Build the Purchase message that to send to the POS device , from the purchase amount and the cash-out amount. 
	 * 
	 */
	public EFTMessageBase doPurchase(double purchaseAmount, double cashAmount, PosMessageBoxForm msgForm)
			throws IOException, InterruptedException {
		
		eftMsgForm = msgForm;
		PosLog.debug("EFTPOS -:-> Trying to PURCHASE...");
		strRefferenceId = null;
		strPurchaseAmount = String.valueOf(purchaseAmount);
		strCashAmount = String.valueOf(cashAmount);
		
		String strPurchase;
		String strCashOut;
		strPurchase = myFormatter.format(purchaseAmount);
		strCashOut = myFormatter.format(cashAmount); 
	    final String terminalCode = PosEnvSettings.getInstance().getStation()
				.getCode();
		final String msgUUID = PosDeviceEFTUtil.getEFTUID();
		strRefferenceId =msgUUID;
		final String purchMsg = msgUUID + "," + EFTMessageType.PUR + ","
				+ MERCHANT_ID + ","+strPurchase+","+ strCashOut + ","
				+ terminalCode + ",NY";
		return doPurchase(purchMsg);

	}
	
	

	/**
	 * @param purchaseReqMsg
	 * @param msgForm
	 * @return
	 * @throws IOException
	 * @throws InterruptedException
	 * 
	 * Build the purchase message from the Manual purchase object from the card payment panel.
	 * send the purchase message for communication with the POS device.
	 */
	public EFTMessageBase doPurchase(EFTMessageMAN purchaseReqMsg, PosMessageBoxForm msgForm) throws IOException, InterruptedException{
		eftMsgForm = msgForm;
		PosLog.debug("EFTPOS -:-> Trying to MAN PURCHASE...");
		strRefferenceId = null;
		final String terminalCode = PosEnvSettings.getInstance().getStation()
				.getCode();
		final String msgUUID = PosDeviceEFTUtil.getEFTUID();
		strRefferenceId =msgUUID;
		strPurchaseAmount = purchaseReqMsg.getPurchaseAmount();
		strCashAmount = purchaseReqMsg.getCashOutAmount();
		String strPurchase;
		String strCashOut;
		strPurchase = myFormatter.format(Double.parseDouble(purchaseReqMsg.getPurchaseAmount()));
		strCashOut = myFormatter.format(Double.parseDouble(purchaseReqMsg.getCashOutAmount())); 
		final String purchMsg = msgUUID + "," + EFTMessageType.MAN + ","
				+ MERCHANT_ID + "," + strPurchase + "," +purchaseReqMsg.getCardExpDate() +
				"," + purchaseReqMsg.getCardNumber() + "," 
				+ terminalCode + ",N,Y";
		return doPurchase(purchMsg);
	}

	/**
	 * @param dspMessage
	 * Process the display message here, and also set the process message in the dialog message.
	 */
	private void procDspRequest(EFTResponseMessageDSP dspMessage) {
		PosLog.debug("EFTPOS -:-> Processing DSP Request...");
		eftMsgForm.setMessage(dspMessage.getDisplayMessage());
		if(dspMessage.getDisplayMessage().equalsIgnoreCase("AWAITING PIN")){
			strRefferenceId = null;
		}
		if (mListner != null)
			mListner.onDisplayMessageReceived(dspMessage.getDisplayMessage());
	}

	private String getSIGRspMessage(String messageUUID, EFTSigStatus status) {
		final String rspSigMessage = messageUUID + "," + EFTMessageType.SIG
				+ "," + MERCHANT_ID + "," + status;
		return rspSigMessage;
	}

	/**
	 * @param sigMessage
	 * @return
	 * @throws IOException
	 * @throws InterruptedException
	 * 
	 * Process the Signature request here , Showing a dialog to select the signature status, Yes or No.
	 */
	private EFTSigStatus procSigRequest(EFTResponseMessageSIG sigMessage) throws IOException,
	InterruptedException {
		PosLog.debug("EFTPOS -:-> Signature Required...");
		PosLog.debug("EFTPOS -:-> Processing Signature...");
		EFTSigStatus status = ((mListner != null && mListner
				.onSignatureVerificationRequest(sigMessage.getDisplayMessage())) ? EFTSigStatus.YES
						: EFTSigStatus.NO);
		String rspMsg = getSIGRspMessage(sigMessage.getMessageId(), status);
		sendSIGResponseMessage(rspMsg);
		return status;
	}
	
	private boolean sendSIGResponseMessage(String rspMsg){
		boolean status = false;
		try {
			status = sendMessage(rspMsg);
		} catch (IOException | InterruptedException e) {
			status = false;
		}
		return status;
	}
	
	/**
	 * @return
	 * Creating POL message .
	 */
	private String getPOLMessage() {
		final String polMsg = PosDeviceEFTUtil.getEFTUID() + ","
				+ EFTMessageType.POL + "," + MERCHANT_ID + ","
				+ EFT_POS_VERSION;
		return polMsg.toUpperCase();
	}

	/**
	 * @param message
	 * @return
	 * @throws IOException
	 * @throws InterruptedException
	 * 
	 * Sending message to POS device via socket connection.
	 */
	private boolean sendMessage(String message) throws IOException,
	InterruptedException {
		PosLog.debug("EFTPOS -:-> Sending Message... ");
		PosLog.debug("EFTPOS -:-> Message: " + message);
		boolean isAckRecieved = false;
		byte[] ecMsg = PosDeviceEFTUtil.encodeMessage(message);
		String sss = PosDeviceEFTUtil.decodeMessage(ecMsg);
		mOutStream.write(ecMsg);
		mOutStream.flush();
		int retry = 0;
		do {
			byte[] resp = getResponse();
//			String res = PosDeviceEFTUtil.decodeMessage(resp);
			if (!(resp.length > 0 && resp[0] == PosDeviceEFTConstants.ACK)) {
				mOutStream.write(ecMsg);
				continue;
			} else {
				isAckRecieved = true;
				break;
			}
		} while (retry < PosDeviceEFTConstants.MAX_RETRY_COUNT);
		PosLog.debug("EFTPOS -:-> Message sending: "
				+ ((isAckRecieved) ? "Success" : "Failed") + "!!!");
		return isAckRecieved;
	}

	/**
	 * @return
	 * @throws IOException
	 * Getting response message.
	 */
	private byte[] getResponse() throws IOException {
		byte[] buff = new byte[PosDeviceEFTConstants.MAX_BYTE_ARRAY_LENGTH];
		final int len = mInStream.read(buff);
		byte[] inMsg = new byte[len];
		System.arraycopy(buff, 0, inMsg, 0, len);
		return inMsg;
	}


	private IPosDeviceEFTListner mListner;

	public void setListner(IPosDeviceEFTListner listner) {
		mListner = listner;
	}

	/**
	 * @param parent
	 * @param purchaseAmount
	 * @param cashOutAmount
	 * @param msgForm
	 * @return
	 * @throws Exception
	 * 
	 * This method using in case of purchase + cash out.
	 * Get the instance of PosDeviceEFT and opening the socket and send the purchase details for processing .
	 * 
	 */
	public static EFTMessageBase doPayment(JDialog parent, double purchaseAmount, double cashOutAmount, PosMessageBoxForm msgForm) throws Exception{
		mParent = parent;
		EFTMessageBase purMessage=null;
		PosDeviceEFT eft=getInstance();
//		eft.open();
		if (eft.openPort()) {
			EFTPolStatus devStatus = eft.getDeviceStatus();
			if (devStatus == EFTPolStatus.IDLE) {
				mProcessListner.setParent(parent);
				eft.setListner(mProcessListner);
				purMessage = eft.doPurchase(purchaseAmount, cashOutAmount,msgForm);
				eft.close();
			}else
				throw new EFTDeviceBusyException("Device busy. Please try again later.");
		}
		return purMessage;
	}
	
	/**
	 * @param parent
	 * @param purchaseReqMsg
	 * @param msgForm
	 * @return
	 * @throws Exception
	 * 
	 * This method using in case of Manual PAN entry.
	 * Get the instance of PosDeviceEFT and opening the socket and send the purchase details for processing .
	 */
	public static EFTMessageBase doPayment(JDialog parent, EFTMessageMAN purchaseReqMsg, PosMessageBoxForm msgForm) throws Exception{
		
		EFTMessageBase purMessage=null;
		PosDeviceEFT eft=getInstance();
//		eft.open();
		if (eft.openPort()) {
			EFTPolStatus devStatus = eft.getDeviceStatus();
			if (devStatus == EFTPolStatus.IDLE) {
				mProcessListner.setParent(parent);
				eft.setListner(mProcessListner);
				purMessage = eft.doPurchase(purchaseReqMsg,msgForm);
				eft.close();
			}else
				throw new EFTDeviceBusyException("Device busy. Please try again later.");
		}
		return purMessage;
	}
	
	/**
	 * @param parent
	 * @param eftPurchaseMsg
	 * @param msgForm
	 * @return
	 * @throws Exception
	 * 
	 * This method using in case of Recovery Mode .
	 * Get the instance of PosDeviceEFT and opening the socket and send the purchase details for processing .
	 * 
	 */
	public static EFTMessageBase retryPayment(JDialog parent ,String eftPurchaseMsg, PosMessageBoxForm msgForm) throws Exception{
		EFTMessageBase purMessage=null;
		PosDeviceEFT eft=getInstance();
		eft.setPurchaseAmounts(eftPurchaseMsg);
//		eft.open();
		if (eft.openPort()) {
			EFTPolStatus devStatus = eft.getDeviceStatus();
			if (devStatus == EFTPolStatus.IDLE||devStatus == EFTPolStatus.BUSY) {
				mProcessListner.setParent(parent);
				eft.setListner(mProcessListner);
				purMessage = eft.doRetryPurchase(eftPurchaseMsg,msgForm);
				eft.close();
			}
		}
		return purMessage;
	}
	
	
	/**
	 * @param eftPurchaseMsg
	 * @param msgForm 
	 * @return
	 * @throws InterruptedException 
	 * @throws IOException 
	 */
	private EFTMessageBase doRetryPurchase(String eftPurchaseMsg, PosMessageBoxForm msgForm) throws IOException, InterruptedException {
		eftMsgForm = msgForm;
		return doPurchase(eftPurchaseMsg);
	}

	/**
	 * @return status as true if the cancel process success else false. 
	 * Cancel the transaction at any point before the pin entry time.
	 */
	public boolean cancelTxn(){
		boolean  status = false;
		if(strRefferenceId!=null){
			final String cancelMsg = strRefferenceId + "," + EFTMessageType.CAN + ","
						+ MERCHANT_ID;
			try {
				byte[] ecMsg = PosDeviceEFTUtil.encodeMessage(cancelMsg);
				String sss = PosDeviceEFTUtil.decodeMessage(ecMsg);
				mOutStream.write(ecMsg);
				mOutStream.flush();
				strRefferenceId = null;
				byte[] response = null;
				response = getPurResponse();
				status = true;
				getInstance().close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}
		
		return status; 
	}

	/**
	 * @param eftPurchaseMsg
	 * Set the purchase amount and the cash out amount in the field variable, strPurchaseAmount and strCashAmount.
	 */
	private  void setPurchaseAmounts(String eftPurchaseMsg) {
		final String[] msgParts=eftPurchaseMsg.split(",",100);
		strPurchaseAmount = msgParts[PosDeviceEFTConstants.PUR_CARD_AMO_PART];
		strCashAmount = msgParts[PosDeviceEFTConstants.PUR_CASH_AMO_PART];
	}

	private static IPosDeviceEFTListner mProcessListner=new IPosDeviceEFTListner() {
		boolean isSIGAccepted=false; 
		JDialog mParent;
		@Override
		public boolean onSignatureVerificationRequest(String message) {

			PosFormUtil.showQuestionMessageBox(mParent, MessageBoxButtonTypes.YesNo, message, 
					new PosMessageBoxFormListnerAdapter() {
				@Override
				public void onYesButtonPressed() {
					isSIGAccepted=true;
					super.onYesButtonPressed();
				}
				@Override
				public void onNoButtonPressed() {
					isSIGAccepted=false;
					super.onNoButtonPressed();
				}
			});
			return isSIGAccepted;

		}

		@Override
		public void onPurchaseRequestReceived() {
//			PosFormUtil.showBusyWindow(mParent, "Please waite while processing the payment.");
		}

		@Override
		public void onPurchaseCompleted(EFTPurchaseStatus status, String message) {
//			PosFormUtil.closeBusyWindow();

		}

		@Override
		public void onError(String errorMsg) {
			

		}

		@Override
		public void onDisplayMessageReceived(String message) {
			// TODO Auto-generated method stub

		}

		@Override
		public void setParent(JDialog parent) {
			mParent=parent;
			
		}
	};

}