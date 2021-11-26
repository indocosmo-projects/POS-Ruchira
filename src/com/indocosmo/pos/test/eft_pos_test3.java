package com.indocosmo.pos.test;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Set;


public class eft_pos_test3
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
	static final String POL_MESSAGE="323526,POL,1";
	static final int MAX_RETRY_COUNT=3;
	static final int MAX_BYTE_ARRAY_LENGHT=10*1024;

	private String mHost="192.168.1.19";
	private int mPort=4444;
	private SocketChannel sc=null;
	private Selector mSelector=null;


	public interface IPosEftPosMessaging{
		public void onConnected();
		public void onConnectionClosed();
		public void onMessageReceived(String message);
	}

	private IPosEftPosMessaging mListner;

	public void setListner(IPosEftPosMessaging listner){
		mListner=listner;
	}

	public void open() throws IOException{
			sc=openConnection(mHost, mPort);
			if(sc!=null && sc.isConnected()){
				if(mListner!=null)
					mListner.onConnected();
				mSelector=Selector.open();
				sc.register(mSelector,SelectionKey.OP_CONNECT | SelectionKey.OP_READ);
				new Thread(
						new Runnable() {
							public void run() {

								try {
									listen();
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}

							}}).start();
			}

	}


	private void listen() throws IOException{
		ByteBuffer byteBuffer = ByteBuffer.allocate(32);
		while(true){
			byteBuffer.clear();
			mSelector.select();
			Set<SelectionKey> readyKeys = mSelector.selectedKeys();
			Iterator<SelectionKey> iterator = readyKeys.iterator();
			while (iterator.hasNext()) {
				SelectionKey key = (SelectionKey) iterator.next();
				iterator.remove();
				if (key.isConnectable()) {

				} else if (key.isReadable()) {
					processQueryRequest(key);
				}
			}
		}
	}
	
	private void processQueryRequest(SelectionKey key) {
		SocketChannel channel = (SocketChannel) key.channel();
	    ByteBuffer byteBuffer = ByteBuffer.allocate(32);
	    try {
	        byteBuffer.clear();
	        while(channel.read(byteBuffer) > 0) {
	            byteBuffer.flip();
	            byteBuffer.compact();
	            System.out.println(Arrays.toString(byteBuffer.array()));
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}

	public void close() throws Exception{
		if(sc==null) return; 
		sc.close();
		if(mListner!=null)
			mListner.onConnectionClosed();
	}

	public void sendMessage(String message) throws Exception{
		if(sc==null) throw new Exception("Connection not established");
		sendMessageFrame(sc, getMessageFrame(message));

	}

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
		String message="432234,PRT,1,POS 1,1,CORYS FISHING \r\n test prinitng \r\n";
		return message;
	}

	public static void main(String[] args) throws IOException
	{
		try{
			eft_pos_test3 eftpos=new eft_pos_test3();
			eftpos.open();
			eftpos.sendMessage(POL_MESSAGE);
			eftpos.sendMessage(getPrinteTestMessage());
		}catch (Exception e) {
			// TODO: handle exception
		}
	}	

	private static void sendMessageFrame(SocketChannel sc, byte[] msgFrm) throws IOException{
		ByteBuffer msgBuffer= ByteBuffer.wrap(msgFrm);
//		while(msgBuffer.hasRemaining()){
			sc.write(ByteBuffer.wrap(msgFrm));
//		}
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
		sendMessageFrame(sc, getMessageFrame(POL_MESSAGE));
		sendMessageFrame(sc, getMessageFrame(getPrinteTestMessage()))	;
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

