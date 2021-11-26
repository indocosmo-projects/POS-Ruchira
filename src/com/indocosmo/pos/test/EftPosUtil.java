/**
 * 
 */
package com.indocosmo.pos.test;

import java.util.Arrays;

/**
 * @author jojesh
 *
 */
public final class EftPosUtil {
	
	public static final int MAX_BYTE_ARRAY_LENGTH=10*1024;
	private static final int FS = 0x1C;
	private static final int DLE = 0x10;
	private static final int STX = 0x02;
	private static final int ETX = 0x03;
	private static final int ENQ = 0x05;
	public static final int ACK = 0x06;
	public static final int NAK = 0x15;
	private static final int COMMA = 0x2C;
	public static final int MAX_RETRY_COUNT=3;
	

	public static byte calculateLRC(byte[] data) {
		byte lrc=0;
		for (int i = 0; i < data.length; i++) {
			lrc ^=data[i];
		}
		lrc^=ETX;
		return lrc;
	}
	
	private static int RemoveControlChars(byte[] baSrc, int inIdx, int iLen, byte[] baDst)
	{
		int inRetLen = 0;
		boolean bLastCharDLE = false;
		for (int i = inIdx; i < iLen; i++)
		{
			if((baSrc[i] == DLE) && (!bLastCharDLE))
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
			else if(baSrc[i] == FS)
			{
				//
				// FS all by it's lonesome... must really be a COMMA
				//
				// Accomodates:
				// - FS, replace with COMMA
				//
				baDst[inRetLen++] = COMMA;
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
			if ((baSrc[i] == DLE) || (baSrc[i] == FS) || (baSrc[i] == STX) || (baSrc[i] == ETX) || (baSrc[i] == ACK) ||
					(baSrc[i] == NAK) || (baSrc[i] == ENQ))
			{
				// Insert DLE
				baDst[inDstIdx + inRetLen++] = DLE;
				baDst[inDstIdx + inRetLen++] = baSrc[i];
				continue;
			}
			if(baSrc[i] == COMMA)
			{
				// Replace COMMA with FS so Terminal interprets correctly
				baDst[inDstIdx + inRetLen++] = FS;
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
		System.out.println("-:-> Decoding Message.....");
		byte[] recMessage=new byte[message.length-3];
		System.arraycopy(message, 1, recMessage, 0, recMessage.length);
		byte[] command_tmp=new byte[1024*10];
		int newLen=EftPosUtil.RemoveControlChars(recMessage,0,recMessage.length,command_tmp);
		byte[] command=new byte[newLen];
		System.arraycopy(command_tmp, 0, command, 0, newLen);
		System.out.println("-:-> Decoded Message");
		System.out.println("-:-> "+Arrays.toString(command));
		System.out.println("-:-> "+new String(command));
		return new String(command);
	}
	
	public static byte[] encodeMessage(String message){
		int index=0;
		byte [] btMsg=new byte[MAX_BYTE_ARRAY_LENGTH];
		final int inRetLen=InsertControlChars(message.getBytes(),message.length(),btMsg,0);
		byte[] buffer=new byte[inRetLen+3];
		buffer[index++]=STX;
		for(int i=0;i<inRetLen;i++)
			buffer[index++]=btMsg[i];
		buffer[index++]=ETX;
		buffer[index++]=calculateLRC(btMsg);
		return buffer;
	}

}
