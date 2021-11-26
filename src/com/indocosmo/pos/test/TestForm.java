package com.indocosmo.pos.test;

import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;

import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.KeyEventPostProcessor;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.swing.JDialog;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;

import com.indocosmo.pos.forms.PosObjectBrowserForm;
import com.indocosmo.pos.forms.components.objectbrowser.IPosBrowsableItem;

public class TestForm extends JDialog {

	/**
	 * @param args  
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TestForm frame = new TestForm();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}
	JTextArea text;
	JTextArea textComand;
	public TestForm(){
		setBounds(100, 100, 350, 450);
		setLayout(new FlowLayout());
//		text=new JTextArea();
//		text.setPreferredSize(new Dimension(300, 200));
//		add(text);
//		
//		textComand=new JTextArea();
//		textComand.setPreferredSize(new Dimension(300, 80));
//		add(textComand);
//		
////		ImageIcon icon =new ImageIcon(getClass().getResource("/accept.png"));
//		JButton btnText=new JButton("Click ME");
////		btnText.setIcon(icon);
//		btnText.setSize(new Dimension(100,100));
//		btnText.addActionListener(new ActionListener() {
//			
//			@Override
//			public void actionPerformed(ActionEvent e) {
////				portCheck(text.getText());
//				portCheckText(text.getText());
//				
//			}
//		});
//		add(btnText)	;
//		
//		JButton btnCommand=new JButton("Send Command");
//		btnCommand.setSize(new Dimension(100,100));
//		btnCommand.addActionListener(new ActionListener() {
//			
//			@Override
//			public void actionPerformed(ActionEvent e) {
////				portCheck(text.getText());
//				portCheckCommand(textComand.getText());
//				
//			}
//		});
//		add(btnCommand);
//		
//		JButton clrbtn=new JButton("Clear");
//		clrbtn.setSize(new Dimension(100,100));
//		clrbtn.addActionListener(new ActionListener() {
//			
//			@Override
//			public void actionPerformed(ActionEvent e) {
////				portCheck(text.getText());
//				clear();
//				
//			}
//		});
//		add(clrbtn);
//		PosCodeScanner scanner=new PosCodeScanner();
//		scanner.setOnCodeScanListner(new IPosCodeScanListner() {
//			
//			@Override
//			public void onCodeScanned(String code) {
//				PosFormUtil.showInformationMessageBox(null,code);
//			}
//		});
//		manager.addKeyEventPostProcessor(scanner);
		
		
	}
	
	class EnterKeyListener implements KeyEventPostProcessor {

	    @Override
	    public boolean postProcessKeyEvent(KeyEvent e) {
	        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
	            //Activate the refresh button:
//	            fireSearch();
	            return true;    //halt further processing
	        }
	        return false;
	    }
	}
	
	
	 KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
	    

	private void portCheck(String message){
		
			BufferedWriter bw=null;
			try {
				bw =new BufferedWriter(new FileWriter("/dev/ttyUSB0",true));
				bw.append(message);
				bw.newLine();
			} catch (IOException e) {
				e.printStackTrace();
			}finally{
				try {
					bw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
	}
	
	private void  portCheck3(){
		String wantedPortName = "/dev/ttyUSB0";
        Enumeration portIdentifiers = CommPortIdentifier.getPortIdentifiers();
        CommPortIdentifier portId = null;  // will be set if port found
        while (portIdentifiers.hasMoreElements())
        {
            CommPortIdentifier pid = (CommPortIdentifier) portIdentifiers.nextElement();
            if(pid.getPortType() == CommPortIdentifier.PORT_SERIAL &&
               pid.getName().equals(wantedPortName)) 
            {
                portId = pid;
                break;
            }
        }
        if(portId == null)
        {
            System.err.println("Could not find serial port " + wantedPortName);
            System.exit(1);
        } 
	}
	
	private void portCheckCommand(String message){
		SerialPort serialPort=null;
		try
	    {
			CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier("/dev/ttyUSB0");
			if (portIdentifier.isCurrentlyOwned())
			{
				System.out.println("Port in use!");
			}
			else {
				System.out.println(portIdentifier.getName());

				serialPort = (SerialPort) portIdentifier.open("ListPortClass", 300);
//				int b = serialPort.getBaudRate();
//				System.out.println(Integer.toString(b));
				serialPort.setSerialPortParams(9600, SerialPort.DATABITS_8, 
					    SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);

				OutputStream mOutputToPort = serialPort.getOutputStream();
//				InputStream mInputFromPort = serialPort.getInputStream();
//				String mValue = "AT\r";
//				System.out.println("beginning to Write . \r\n");
				byte[] bt=new byte[]{0x0C};
//				mOutputToPort.write(message.getBytes());

				mOutputToPort.write(hexStringToByteArray(message));
//				System.out.println("AT Command Written to Port. \r\n");
//				mOutputToPort.flush();
//				System.out.println("Waiting for Reply \r\n");
//				Thread.sleep(500);
//				byte mBytesIn [] = new byte[20];
//				mInputFromPort.read(mBytesIn);
//				mInputFromPort.read(mBytesIn);
//				String value = new String(mBytesIn);
//				System.out.println("Response from Serial Device: "+value);
				mOutputToPort.close();
//				mInputFromPort.close();
				serialPort.close();
			}
	    }
		catch (Exception ex)
		{
			System.out.println("Exception : " + ex.getMessage());
			serialPort.close();
		}

	}
	
	private void clear(){
		try
	    {
			CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier("/dev/ttyUSB0");
			if (portIdentifier.isCurrentlyOwned())
			{
				System.out.println("Port in use!");
			}
			else {
				System.out.println(portIdentifier.getName());

				SerialPort serialPort = (SerialPort) portIdentifier.open("ListPortClass", 300);
				serialPort.setSerialPortParams(9600, SerialPort.DATABITS_8, 
					    SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);

				OutputStream mOutputToPort = serialPort.getOutputStream();
				mOutputToPort.write(new byte[]{0x0C});
				mOutputToPort.flush();
				mOutputToPort.close();
//				mInputFromPort.close();
				serialPort.close();
			}
	    }
		catch (Exception ex)
		{
			System.out.println("Exception : " + ex.getMessage());
		}

	}
	
	private void portCheckText(String message){
		SerialPort serialPort=null;
		try
	    {
			CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier("/dev/ttyUSB0");
			if (portIdentifier.isCurrentlyOwned())
			{
				System.out.println("Port in use!");
			}
			else {
				System.out.println(portIdentifier.getName());

				serialPort = (SerialPort) portIdentifier.open("ListPortClass", 300);
//				int b = serialPort.getBaudRate();
//				System.out.println(Integer.toString(b));
				serialPort.setSerialPortParams(9600, SerialPort.DATABITS_8, 
					    SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);

				OutputStream mOutputToPort = serialPort.getOutputStream();
//				InputStream mInputFromPort = serialPort.getInputStream();
//				String mValue = "AT\r";
//				System.out.println("beginning to Write . \r\n");
				byte[] bt=new byte[]{0x0C};
				mOutputToPort.write(message.getBytes());

//				mOutputToPort.write(hexStringToByteArray(message));
//				System.out.println("AT Command Written to Port. \r\n");
				mOutputToPort.flush();
//				System.out.println("Waiting for Reply \r\n");
//				Thread.sleep(500);
//				byte mBytesIn [] = new byte[20];
//				mInputFromPort.read(mBytesIn);
//				mInputFromPort.read(mBytesIn);
//				String value = new String(mBytesIn);
//				System.out.println("Response from Serial Device: "+value);
				mOutputToPort.close();
//				mInputFromPort.close();
				serialPort.close();
			}
	    }
		catch (Exception ex)
		{
			System.out.println("Exception : " + ex.getMessage());
			serialPort.close();
		}

	}
	
	public static byte[] hexStringToByteArray(String s) {
	    int len = s.length();
	    byte[] data = new byte[len / 2];
	    for (int i = 0; i < len; i += 2) {
	        data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
	                             + Character.digit(s.charAt(i+1), 16));
	    }
	    return data;
	}
	
	public void test(){
		
		ArrayList<IPosBrowsableItem> list=new ArrayList<IPosBrowsableItem>();
		
		final String cardNumber="123456";
		
		list.add(new IPosBrowsableItem() {
			
			@Override
			public boolean isVisibleInUI() {
				// TODO Auto-generated method stub
				return true;
			}
			
			@Override
			public KeyStroke getKeyStroke() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Object getItemCode() {
				// TODO Auto-generated method stub
				return cardNumber;
			}
			
			@Override
			public String getDisplayText() {
				// TODO Auto-generated method stub
				return cardNumber;
			}
		});
		
			
		 
	}

}
