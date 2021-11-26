package com.indocosmo.pos.terminal.devices.eftpos;

import java.math.BigInteger;
import java.util.Arrays;

import com.indocosmo.pos.common.PosEnvSettings;
import com.indocosmo.pos.common.PosLog;
import com.indocosmo.pos.common.utilities.PosDateUtil;
import com.indocosmo.pos.terminal.devices.eftpos.enums.EFTLogonStatus;
import com.indocosmo.pos.terminal.devices.eftpos.enums.EFTMessageType;
import com.indocosmo.pos.terminal.devices.eftpos.enums.EFTPolStatus;
import com.indocosmo.pos.terminal.devices.eftpos.enums.EFTPurchaseStatus;
import com.indocosmo.pos.terminal.devices.eftpos.messageobjects.EFTMessageBase;
import com.indocosmo.pos.terminal.devices.eftpos.messageobjects.EFTResponseMessageDSP;
import com.indocosmo.pos.terminal.devices.eftpos.messageobjects.EFTResponseMessageERR;
import com.indocosmo.pos.terminal.devices.eftpos.messageobjects.EFTResponseMessageLOG;
import com.indocosmo.pos.terminal.devices.eftpos.messageobjects.EFTResponseMessageMAN;
import com.indocosmo.pos.terminal.devices.eftpos.messageobjects.EFTResponseMessagePOL;
import com.indocosmo.pos.terminal.devices.eftpos.messageobjects.EFTResponseMessagePUR;
import com.indocosmo.pos.terminal.devices.eftpos.messageobjects.EFTResponseMessageSIG;

/**
 * @author jojesh
 *
 */
public final class PosDeviceEFTUtil {
	
	
	
	public static byte calculateLRC(byte[] data,int len) {
		byte lrc=0;
		for (int i = 0; i < len; i++) {
			lrc ^=data[i];
		}
		lrc^=PosDeviceEFTConstants.ETX;
		return lrc;
	}
	
	private static int RemoveControlChars(byte[] baSrc, int inIdx, int iLen, byte[] baDst)
	{
		int inRetLen = 0;
		boolean bLastCharDLE = false;
		for (int i = inIdx; i < iLen; i++)
		{
			if((baSrc[i] == PosDeviceEFTConstants.DLE) && (!bLastCharDLE))
			{
				// Control DLE... Dump It!
				bLastCharDLE = true;
				continue;
			}
			if(bLastCharDLE)
			{
				//
				// Last char was DLE, so copy over the next char regardless and reset DLE indicator
				//
				// Accomodates:
				// - DLE DLE, remove first DLE
				// - DLE FS, remove DLE
				// - DLE STX/ETX/ACK/NAK, remove DLE
				//
				baDst[inRetLen++] = baSrc[i];
				bLastCharDLE = false;
				continue;
			}
			else if(baSrc[i] == PosDeviceEFTConstants.FS)
			{
				//
				// FS all by it's lonesome... must really be a COMMA
				//
				// Accomodates:
				// - FS, replace with COMMA
				//
				baDst[inRetLen++] = PosDeviceEFTConstants.COMMA;
				continue;
			}
			else
			{
				baDst[inRetLen++] = baSrc[i];
				continue;
			}
		}
		return inRetLen;
	}

	private static int InsertControlChars(byte[] baSrc, int iLen,  byte[] baDst, int inDstIdx)
	{
		int inRetLen = 0;
		for (int i = 0; i < iLen; i++)
		{
			if ((baSrc[i] == PosDeviceEFTConstants.DLE) || (baSrc[i] == PosDeviceEFTConstants.FS) || (baSrc[i] == PosDeviceEFTConstants.STX) || (baSrc[i] == PosDeviceEFTConstants.ETX) || (baSrc[i] == PosDeviceEFTConstants.ACK) ||
					(baSrc[i] == PosDeviceEFTConstants.NAK) || (baSrc[i] == PosDeviceEFTConstants.ENQ))
			{
				// Insert DLE
				baDst[inDstIdx + inRetLen++] = PosDeviceEFTConstants.DLE;
				baDst[inDstIdx + inRetLen++] = baSrc[i];
				continue;
			}
			if(baSrc[i] == PosDeviceEFTConstants.COMMA)
			{
				// Replace COMMA with FS so Terminal interprets correctly
				baDst[inDstIdx + inRetLen++] =PosDeviceEFTConstants.COMMA;// FS;
				continue;
			}
			else
			{
				// Copy over "normal" byte
				baDst[inDstIdx + inRetLen++] = baSrc[i];
				continue;
			}
		}
		return inRetLen;
	}
	
	public static String decodeMessage(byte[] message){
		PosLog.debug("EFTPOS -:-> Decoding Message.....");
		byte[] recMessage=new byte[message.length-3];
		System.arraycopy(message, 1, recMessage, 0, recMessage.length);
		byte[] command_tmp=new byte[1024*10];
		int newLen=PosDeviceEFTUtil.RemoveControlChars(recMessage,0,recMessage.length,command_tmp);
		byte[] command=new byte[newLen];
		System.arraycopy(command_tmp, 0, command, 0, newLen);
		PosLog.debug("EFTPOS -:-> Decoded Message");
		PosLog.debug("EFTPOS -:-> Byte: "+Arrays.toString(command));
		PosLog.debug("EFTPOS -:-> Hex: "+ new BigInteger(1, command).toString(16) );
		PosLog.debug("EFTPOS -:-> "+new String(command));
		return new String(command);
	}
	
	public static byte[] encodeMessage(String message){
		PosLog.debug("EFTPOS -:-> Encoding Message.....");
		int index=0;
		byte[] buffer=new byte[message.length()+3];
		buffer[index++]=PosDeviceEFTConstants.STX;
		for(byte bt:message.getBytes())
			buffer[index++]=bt;
		buffer[index++]=PosDeviceEFTConstants.ETX;
		buffer[index++]=calculateLRC(message.getBytes(),message.length());
		PosLog.debug("EFTPOS -:-> Encoded Message ");
		PosLog.debug("EFTPOS -:-> Byte: "+Arrays.toString(buffer));
		PosLog.debug("EFTPOS -:-> Hex: "+ new BigInteger(1, buffer).toString(16).toUpperCase());
		String sss = decodeMessage(buffer);
		return buffer;
	}
	
	public static String getEFTUID(){
		final String terminalCode=PosEnvSettings.getInstance().getStation().getCode();
		final String timeStamp=PosDateUtil.getDate("ddhhmmss");
		final String EftUid=terminalCode+timeStamp;
		return EftUid.toUpperCase();
	}
	
	public static EFTMessageBase getEFTResponseMessageObject(String rspMsg){
		
		final String[] msgParts=rspMsg.split(",",100);
		final EFTMessageType msgType=EFTMessageType.get(msgParts[PosDeviceEFTConstants.EFT_MSG_TYPE_PART]);
		EFTMessageBase eftMsg=null;
		switch (msgType) {
		case PUR:
			EFTResponseMessagePUR msgPur=new EFTResponseMessagePUR();
			msgPur.setAccountType(msgParts[PosDeviceEFTConstants.PUR_ACC_PART]);
			msgPur.setAuthCode(msgParts[PosDeviceEFTConstants.PUR_AUTH_PART]);
			msgPur.setCardAmount(msgParts[PosDeviceEFTConstants.PUR_CARD_AMO_PART]);
			msgPur.setCardNumber(msgParts[PosDeviceEFTConstants.PUR_CARD_NO_PART]);
			msgPur.setCashAmount(msgParts[PosDeviceEFTConstants.PUR_CASH_AMO_PART]);
			msgPur.setDisplayMessage(msgParts[PosDeviceEFTConstants.PUR_DISP_MSG_PART]);
//			msgPur.setReceiptDataPart1(msgParts[PosDeviceEFTConstants.PUR_OPT_DATAT_1_PART]);
//			msgPur.setReceiptDataPart2(msgParts[PosDeviceEFTConstants.PUR_OPT_DATAT_2_PART]);
			msgPur.setStatus(EFTPurchaseStatus.get(msgParts[PosDeviceEFTConstants.PUR_STAT_PART]));
			msgPur.setTxnTraceNumber(msgParts[PosDeviceEFTConstants.PUR_TXN_REF_NO_PART]);
			eftMsg=msgPur;
			break;
		case MAN:
			EFTResponseMessageMAN msgMan=new EFTResponseMessageMAN();
			msgMan.setAccountType(msgParts[PosDeviceEFTConstants.MAN_ACC_PART]);
			msgMan.setBankReferance(msgParts[PosDeviceEFTConstants.MAN_BANK_REF_PART]);
			msgMan.setCardAmount(msgParts[PosDeviceEFTConstants.MAN_CARD_AMO_PART]);
			msgMan.setCardNumber(msgParts[PosDeviceEFTConstants.MAN_CARD_NO_PART]);
			msgMan.setDisplayMessage(msgParts[PosDeviceEFTConstants.MAN_DISP_MSG_PART]);
//			msgMan.setReceiptDataPart1(msgParts[PosDeviceEFTConstants.MAN_OPT_DATAT_1_PART]);
//			msgMan.setReceiptDataPart2(msgParts[PosDeviceEFTConstants.MAN_OPT_DATAT_2_PART]);
			msgMan.setStatus(EFTPurchaseStatus.get(msgParts[PosDeviceEFTConstants.MAN_STAT_PART]));
			msgMan.setTxnTraceNumber(msgParts[PosDeviceEFTConstants.MAN_TXN_REF_NO_PART]);
			eftMsg=msgMan;
			break;
		case LOG:
			EFTResponseMessageLOG msgLog=new EFTResponseMessageLOG();
			msgLog.setStatus(EFTLogonStatus.get(msgParts[PosDeviceEFTConstants.LOGON_STAT_PART]));
			msgLog.setDisplayMessage(msgParts[PosDeviceEFTConstants.LOGON_DISP_MSG_PART]);
//			msgLog.setReceiptDataPart1(msgParts[PosDeviceEFTConstants.LOGON_OPT_DATAT_1_PART]);
			eftMsg=msgLog;
			break;
		case POL:
			EFTResponseMessagePOL msgPol=new EFTResponseMessagePOL();
//			msgPol.setDisplayMessage(msgParts[PosDeviceEFTConstants.POL_DISP_MSG_PART]);
			msgPol.setStatus(EFTPolStatus.get(msgParts[PosDeviceEFTConstants.POL_STAT_PART]));
//			msgPol.setSerialNumber(msgParts[PosDeviceEFTConstants.POL_SRNO_PART]);
			eftMsg=msgPol;
			break;
		case DSP:
			EFTResponseMessageDSP dspMsg=new EFTResponseMessageDSP();
			dspMsg.setDisplayMessage(msgParts[PosDeviceEFTConstants.DSP_DISP_MSG_PART]);
			eftMsg=dspMsg;
			break;
		case SIG:
			EFTResponseMessageSIG sigMsg=new EFTResponseMessageSIG();
			sigMsg.setDisplayMessage(msgParts[PosDeviceEFTConstants.SIG_DISP_MSG_PART]);
			eftMsg=sigMsg;
			break;
		case ERR:
			EFTResponseMessageERR errMsg=new EFTResponseMessageERR();
			errMsg.setDisplayMessage(msgParts[PosDeviceEFTConstants.ERR_DISP_MSG_PART]);
			eftMsg=errMsg;
			break;
		case CAN:
//			EFTResponseMessageCAN canMsg=new EFTResponseMessageCAN();
//			canMsg.setDisplayMessage(msgParts[PosDeviceEFTConstants.]);
//			eftMsg=canMsg;
			break;
		default:
			break;
		}
		if(eftMsg!=null){
			eftMsg.setMerchantID(msgParts[PosDeviceEFTConstants.EFT_MERCHANT_ID_PART]);
			eftMsg.setMessageId(msgParts[PosDeviceEFTConstants.EFT_MSG_ID_PART]);
			eftMsg.setMessageType(msgType);
		}
		return eftMsg;
	}

}
