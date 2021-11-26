/**
 * 
 */
package com.indocosmo.pos.test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

public class VerySimpleServer {
	
	private int serverPort = 4444;
	private ServerSocket serverSock = null;

	public VerySimpleServer(int serverPort) {
		this.serverPort = serverPort;

		try {
			serverSock = new ServerSocket(serverPort);
		}
		catch (IOException e){
			e.printStackTrace(System.err);
		}
	}

	public void handleConnection(InputStream sockInput, OutputStream sockOutput) {
		while(true) {
			byte[] buf=new byte[1024];
			int bytes_read = 0;
			try {
				bytes_read = sockInput.read(buf);

				if(bytes_read < 0) {
					System.err.println("Tried to read from socket, read() returned < 0,  Closing socket.");
					return;
				}
				byte[] msg_buff=new byte[bytes_read];
				System.arraycopy(buf, 0, msg_buff, 0, bytes_read);
				System.out.println("SERVER -:-> Received Data from clinet");
				System.out.println("SERVER -:-> Dumping Data");
				System.out.println("SERVER -:-> "+Arrays.toString(msg_buff));
				System.out.println("SERVER -:-> Sending ACK [06] back to client");
				sockOutput.write(new byte[]{6});
				sockOutput.flush();
				String dCodedMsg=EftPosUtil.decodeMessage(msg_buff);
				procMessage(sockOutput,dCodedMsg);
			}catch (Exception e){
				System.err.println("Exception reading from/writing to socket, e="+e);
				e.printStackTrace(System.err);
				return;
			}
		}
	}
	
	private void procMessage(OutputStream sockOutput,String message) throws IOException{
		String[] msgParts=message.split(",");
		if(msgParts[1].equals("POL")){
			System.out.println("SERVER -:-> POL request received");
			System.out.println("SERVER -:-> Sending back status");
			procPolMessage(sockOutput,msgParts[0]);
		}
	}
	
	private byte[] getStatusMessage(String messageId){
		String statusString=messageId+",POL,1,80,READY,12345678";
		byte[] msgFrame=EftPosUtil.encodeMessage(statusString);
		return msgFrame;
	}
	
	private void procPolMessage(OutputStream sockOutput, String messageId) throws IOException{
		
		byte[] statusMsg=getStatusMessage( messageId);
		sockOutput.write(statusMsg);
	}
	

	public void waitForConnections() {
		Socket sock = null;
		InputStream sockInput = null;
		OutputStream sockOutput = null;
		while (true) {
			try {
				sock = serverSock.accept();
				System.err.println("SERVER -:-> Have accepted new socket.");
				sockInput = sock.getInputStream();
				sockOutput = sock.getOutputStream();
			}
			catch (IOException e){
				e.printStackTrace(System.err);
			}
			handleConnection(sockInput, sockOutput);
			try {
				System.err.println("SEVER -:-> Closing socket.");
				sock.close();
			}
			catch (Exception e){
				System.err.println("SERVER -:->   Exception while closing socket.");
				e.printStackTrace(System.err);
			}

			System.err.println("SERVER -:->   Finished with socket, waiting for next connection.");
		}
	}

	public static void main(String argv[]) {
		int port = 4444;
		VerySimpleServer server = new VerySimpleServer(port);
		server.waitForConnections();
	}
}