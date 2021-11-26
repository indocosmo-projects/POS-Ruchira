package com.indocosmo.pos.test;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Arrays;


public class eft_pos_test2
{
	static final String POL_MESSAGE="323526,POL,1";
	private OutputStream out=null;
	private Socket sc=null;
	private InputStream in=null;
	
	public static void main(String[] args) throws IOException
	{
		final eft_pos_test2 eftPos=new eft_pos_test2();
		eftPos.connectToServer();
		new Thread(new Runnable() {
			@Override
			public void run() {
				eftPos.listen(); 
			}
		}).start();
		eftPos.promptMenu();
		
	}	
	
	private static void procMessage(String message) throws IOException{
		String[] msgParts=message.split(",");
		if(msgParts[2].equals("POL")){
			System.out.println("CLIENT -:-> POL Status received");
			System.out.println("CLINET -:-> Status : "+msgParts[4]);
		}
	}
	
	private void promptMenu() throws IOException{
		String ans="";
		while(true){
			System.out.println("Options ");
			System.out.println("1 -> POL");
			System.out.println("0 -> Exit");
			ans=System.console().readLine("Enter value:");
			if(ans=="1"){
				out.write(EftPosUtil.encodeMessage(POL_MESSAGE));
				out.flush();
			}else if(ans=="0"){
				closeConnection();
				System.exit(0);
			}
		}
	}
	
	private void listen(){
		byte[] inmsg=new byte[EftPosUtil.MAX_BYTE_ARRAY_LENGTH];
		int len=0;
		try {
			do{
				len = in.read(inmsg);
				if(len<=0) continue;
				System.out.println("CLIENT -:-> Data received of length :"+len);
				System.out.println("CLIENT -:-> Dumping data "+Arrays.toString(inmsg));
				if(inmsg[0]==EftPosUtil.ACK){

				}else if(inmsg[0]==EftPosUtil.NAK){

				}else{
					String message=EftPosUtil.decodeMessage(Arrays.copyOf(inmsg, len));
					procMessage(message);
				}

			}while(true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	private void connectToServer() throws IOException{
		System.out.println("CLIENT -:-> Connecting...");
		sc=new Socket("192.168.1.133", 4444);
		System.out.println("CLIENT -:-> Connected!!!");
		out=sc.getOutputStream();
		in=sc.getInputStream();
	}
	
	private void closeConnection() throws IOException{
		if(sc!=null && sc.isConnected()){
			sc.close();
			in.close();
			out.close();
		}
	}

}

