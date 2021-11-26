package com.indocosmo.pos.test;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;


public class eft_pos_test
{

	static final int FS = 0x1C;
	static final int DLE = 0x10;
	static final int STX = 0x02;
	static final int ETX = 0x03;
	static final int ENQ = 0x05;
	static final int ACK = 0x06;
	static final int NAK = 0x15;
	static final int COMMA = 0x2C;
	static final int MAX_SIZE=10*1024;
	static final int MIN_SIZE=10*1024;
	static final String POL_MESSAGE="323526,POL,1,2.1";
	static final int MAX_RETRY_COUNT=3;
	static final int MAX_BYTE_ARRAY_LENGHT=10*1024;


	private static byte[] getMessageFrame(String message){
		int index=0;
		byte [] btMsg=new byte[MAX_BYTE_ARRAY_LENGHT];
		final int inRetLen=InsertControlChars(message.getBytes(),message.length(),btMsg,0);
		byte[] buffer=new byte[inRetLen+3];
		buffer[index++]=STX;
		for(int i=0;i<inRetLen;i++)
			buffer[index++]=btMsg[i];
		buffer[index++]=ETX;
		buffer[index++]=calculateLRC(btMsg);
		return buffer;
	}

	private static SocketChannel openConnection(String host, int port) throws IOException{
		SocketChannel sc=SocketChannel.open();
		sc=SocketChannel.open();
		sc.configureBlocking(false);
		sc.connect(new InetSocketAddress(host, port));
		System.out.println("Connecting....");
		while (!sc.finishConnect()) {
			try { 
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("Connected!");
		return sc;
	}
	
	private static String getPrinteTestMessage(){
		String message="432234,PRT,1,POS 1,YN,CORYS FISHING \r\n test prinitng \r\n";
		return message;
		}

	public static void main(String[] args) throws IOException
	{
		try{
			SocketChannel sc=openConnection("192.168.1.19", 4444);
			if(sc!=null){
//				byte[] bytToSend=getMessageFrame(POL_MESSAGE);
//				sendMessageFrame(sc,bytToSend);
//				final byte[] resp=sendMessageFrame(sc,bytToSend);
				testPrint(sc);
				int i=1+14;
			}
		}catch (Exception e) {
			// TODO: handle exception
		}
	}	
	
//	private String getResponse(String queryID){
//		String response=null;
//		String sigString=""
//		
//	}

	private static byte[] sendMessageFrame(SocketChannel sc, byte[] msgFrm) throws IOException{
		ByteBuffer recBuffer= ByteBuffer.allocate(10*1024);
		
		boolean exitStatus=false;
		int retryCount=0;
		int len=0;
		while(!exitStatus){
			recBuffer.clear();
			sc.write(ByteBuffer.wrap(msgFrm));
			retryCount++;
			len=sc.read(recBuffer);
			exitStatus=(len>0 && recBuffer.array()[0]!=NAK);
			if(retryCount>=MAX_RETRY_COUNT && !exitStatus){
				len=0;
				exitStatus=true;
			}
		}
		return copyBytes(recBuffer.array(), 0, len);
	}

	private static byte[] copyBytes(byte[] srcByteArray, int offset, int length){
		byte[] dstByte=new byte[length];
		for(int index=0; index<length; index++)
			dstByte[index]=srcByteArray[index];
		return dstByte;
	}

	private static byte calculateLRC(byte[] data) {
		byte lrc=0;
		for (int i = 0; i < data.length; i++) {
			lrc ^=data[i];
		}
		lrc^=ETX;
		return lrc;
	}
	
	private static void testPrint(SocketChannel sc) throws IOException{
		String printMessage="432234,PRT,1,POS 1,YY,CORYS FISHING";
		byte[] polResp=sendMessageFrame(sc, getMessageFrame(POL_MESSAGE));
		byte[] resp=sendMessageFrame(sc, getMessageFrame(getPrinteTestMessage()))	;
		System.out.println("printing......");
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


}

