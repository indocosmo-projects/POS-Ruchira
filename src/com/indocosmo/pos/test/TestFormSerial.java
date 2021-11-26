package com.indocosmo.pos.test;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JTextArea;

import com.indocosmo.pos.common.PosLog;
import com.indocosmo.pos.terminal.devices.PosDeviceUtil;

public class TestFormSerial extends JDialog {

	boolean isPortOpen;

	SerialPort parallelPort=null;
	/**
	 * @param args  
	 */
	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TestFormSerial frame = new TestFormSerial();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}

	JTextArea text;

	public TestFormSerial(){

		setBounds(100, 100, 350, 450);
		setLayout(new FlowLayout());
		text=new JTextArea();
		text.setPreferredSize(new Dimension(300, 200));
		add(text);


		JButton btnNormalText=new JButton("Test");
		btnNormalText.setSize(new Dimension(100,200));
		btnNormalText.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				try {
					connect("/dev/ttyUSB0");
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		add(btnNormalText);

	}

	InputStream in;
	OutputStream out ;
	SerialPort serialPort;
	CommPort commPort ;
	void connect ( String portName ) throws Exception
	{
		if(!isPortOpen) {
			
			System.setProperty("gnu.io.rxtx.SerialPorts", portName);

			Enumeration<CommPortIdentifier> commPortIdentifier=CommPortIdentifier.getPortIdentifiers();
			CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier(portName);
			if ( portIdentifier.isCurrentlyOwned() ) {

				System.out.println("Error: Port is currently in use");
				return;

			}else{
				commPort = portIdentifier.open(this.getClass().getName(),2000);
				isPortOpen=true;
			}


			if ( commPort instanceof SerialPort )
			{
				serialPort = (SerialPort) commPort;
				serialPort.setSerialPortParams(9600,SerialPort.DATABITS_8,SerialPort.STOPBITS_1,SerialPort.PARITY_NONE);

				in = serialPort.getInputStream();
				out = serialPort.getOutputStream();

				final  SerialReader serialReader=new SerialReader(in);
				serialReader.setListener(new ISerialEventListener() {

					@Override
					public void onIntrupt() {

						close();

					}

					@Override
					public void onDataRecieved(String data) {

						System.out.println(data);
					}
				});
				
				serialPort.setFlowControlMode(SerialPort.FLOWCONTROL_NONE);

				
		        
				serialPort.notifyOnBreakInterrupt(true);
				serialPort.notifyOnDataAvailable(true);
				
				serialPort.addEventListener(serialReader);
		        
//		        serialPort.notifyOnCarrierDetect(true);
//		        serialPort.notifyOnCTS(true);
//		        serialPort.notifyOnDSR(true);
//		        serialPort.notifyOnFramingError(true);
//		        serialPort.notifyOnOutputEmpty(true);
//		        serialPort.notifyOnOverrunError(true);
//		        serialPort.notifyOnParityError(true);
//		        serialPort.notifyOnRingIndicator(true);


			}
			else
			{
				System.out.println("Error: Only serial ports are handled by this example.");
			}

		}
		
		if(isPortOpen)
			(new Thread(new SerialWriter(out))).start();

	}

	private void close(){

		try {
			
			serialPort.removeEventListener();
			serialPort.close();
			
			in.close();
			out.close();
			
			isPortOpen=false;

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Handles the input coming from the serial port. A new line character
	 * is treated as the end of a block in this example. 
	 */
	public  class SerialReader implements SerialPortEventListener 
	{
		private InputStream in;
//		private byte[] buffer = new byte[1024];

		public SerialReader ( InputStream in )
		{
			this.in = in;
		}

		public void serialEvent(SerialPortEvent event) {
			
			 switch(event.getEventType())
		        {
		            case SerialPortEvent.BI:
		                System.out.println("SerialPortEvent.BI occurred");
		            case SerialPortEvent.OE:
		                System.out.println("SerialPortEvent.OE occurred");
		            case SerialPortEvent.FE:
		                System.out.println("SerialPortEvent.FE occurred");
		            case SerialPortEvent.PE:
		                System.out.println("SerialPortEvent.PE occurred");
		            case SerialPortEvent.CD:
		                System.out.println("SerialPortEvent.CD occurred");
		            case SerialPortEvent.CTS:
		                System.out.println("SerialPortEvent.CTS occurred");
		            case SerialPortEvent.DSR:
		                System.out.println("SerialPortEvent.DSR occurred");
		            case SerialPortEvent.RI:
		                System.out.println("SerialPortEvent.RI occurred");
		            case SerialPortEvent.OUTPUT_BUFFER_EMPTY:
		                System.out.println("SerialPortEvent.OUTPUT_BUFFER_EMPTY occurred");
		                break;
		            case SerialPortEvent.DATA_AVAILABLE:
		                System.out.println("SerialPortEvent.DATA_AVAILABLE occurred");
		               

		                try
		                {
		                	byte[] buffer=new byte[1024];
		                	int len = 0;
		                	int data;
		                    while ( ( data = in.read()) > -1 )
		                    {
		                        if ( data == '\n' ) {
		                            break;
		                        }
		                        buffer[len++] = (byte) data;
		                    }
		                   
		                    if(listener!=null && len>0)
		                    	listener.onDataRecieved( new String(buffer,0,len));
		                    
		                 }catch (IOException ioe)
		                {
		                    System.out.println("Exception " + ioe);
		                }
		                break;
		        }

		}



		private ISerialEventListener listener;

		public void setListener(ISerialEventListener listener){

			this.listener=listener;
		}
	}

	/** */
	public class SerialWriter implements Runnable 
	{
		OutputStream out;

		public SerialWriter ( OutputStream out )
		{
			this.out = out;
		}

		public void run ()
		{
			try
			{                
				//                int c = 0;
				//                while ( ( c = System.in.read()) > -1 )
				//                {
				this.out.write(PosDeviceUtil.stringToByteArr("05"));;
				this.out.flush();
				//                }                
			}
			catch (  Exception e )
			{
				e.printStackTrace();
//				close();
//				System.exit(-1);
			}            
		}
	}

	public interface ISerialEventListener{

		public void onDataRecieved(String data);

		public void onIntrupt();
	}

}
