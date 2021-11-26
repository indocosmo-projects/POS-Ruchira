package com.indocosmo.pos.test;

import gnu.io.CommPortIdentifier;
import gnu.io.ParallelPort;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.OutputStream;
import java.util.StringTokenizer;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JTextArea;

import com.indocosmo.pos.terminal.devices.PosDeviceUtil;

public class TestFormParallel extends JDialog {
	ParallelPort parallelPort=null;
	/**
	 * @param args  
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TestFormParallel frame = new TestFormParallel();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}
	
	JTextArea text;
	
	public TestFormParallel(){
		setBounds(100, 100, 350, 450);
		setLayout(new FlowLayout());
		text=new JTextArea();
		text.setPreferredSize(new Dimension(300, 200));
		add(text);
		
		final JCheckBox chkEmphaSize=new JCheckBox("Emphasize");
		add(chkEmphaSize);
		
		final JCheckBox chkUL=new JCheckBox("Under Line");
		add(chkUL);
		
		final JCheckBox chkCut=new JCheckBox("Cut");
		add(chkCut);
		
		final JCheckBox chkdblStrok=new JCheckBox("Stroke");
		add(chkdblStrok);
		
		final JCheckBox chkdrawer=new JCheckBox("Drawer 1");
		add(chkdrawer);
		
		JButton btnNormalText=new JButton("print ");
		btnNormalText.setSize(new Dimension(100,100));
		btnNormalText.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				portCheckText(text.getText(),chkEmphaSize.isSelected(),chkUL.isSelected()
						,chkCut.isSelected(),chkdblStrok.isSelected(),chkdrawer.isSelected());
			}
		});
		add(btnNormalText);
			
	}
	
	
	private void portCheckText(String message, boolean emphasized,boolean underline,boolean cut,boolean stroke,
			boolean drawer1){
		ParallelPort parlalPort=null;
		try
	    {
			CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier("LPT1");

				//InitPrinter();

				parlalPort = (ParallelPort) portIdentifier.open("ListPortClass", 50);
				
				OutputStream mOutputToPort = parlalPort.getOutputStream();
				
				System.out.println("=============initializing the printer==============");
				
				byte[] command=new byte[]{0x1B,0x40};
				mOutputToPort.write(command);

				command=new byte[]{0x1B,0x4C};
				mOutputToPort.write(command);
				
				if(emphasized){
					System.out.println("printing in emphasized mode");
					command=new byte[]{0x1B,0x45,PosDeviceUtil.getHexValue(1)};
					mOutputToPort.write(command);
				}
				
				if(stroke){ 
					System.out.println("printing in stroke mode");
					command=new byte[]{0x1B,0x47,PosDeviceUtil.getHexValue(49)};
					mOutputToPort.write(command);
				}
				
				if(underline){
					System.out.println("printing  with under line");
					command=new byte[]{0x1B,0x2D,PosDeviceUtil.getHexValue(2)};
					mOutputToPort.write(command);
				}
				
				if (drawer1) {
					System.out.println("Kick out drawer 1");
					String str = "1B,70,0,A,14";
					command = ConvertToByteArr(str);
					mOutputToPort.write(command);
				}
				
				System.out.println("sending data to buffer");
				mOutputToPort.write(message.getBytes());
				mOutputToPort.write(command);
				
				System.out.println("printing  data");
				command=new byte[]{0x0A};
				mOutputToPort.write(command);
				
				if(cut){
					System.out.println("cutting line");
					command=new byte[]{0x1D,0x56,PosDeviceUtil.getHexValue(0)};
					mOutputToPort.write(command);
				}
				
				mOutputToPort.flush();
				mOutputToPort.close();
	    }
		catch (Exception ex)
		{
			System.out.println("Exception : " + ex.getMessage());
		}
		finally{
			parlalPort.close();
		}
	}

	public byte[] ConvertToByteArr(String paddedString) throws Exception {
		byte[] byteArr = null;
		StringTokenizer st = new StringTokenizer(getHexaCommand(paddedString), ",");
		int length = st.countTokens();
		if (length > 8) {
			throw new Exception("Cannot handle more than 8 Bytes instruction.");
		}
		
		if (length == 1) {
			byte var1 = Byte.decode(st.nextToken().toString());
			byteArr = new byte[] {var1};
			
		} else if (length == 2) {
			byte var1 = Byte.decode(st.nextToken().toString());
			byte var2 = Byte.decode(st.nextToken().toString());
			byteArr = new byte[] {var1, var2};

		} else if (length == 3) {
			byte var1 = Byte.decode(st.nextToken().toString());
			byte var2 = Byte.decode(st.nextToken().toString());
			byte var3 = Byte.decode(st.nextToken().toString());
			byteArr = new byte[] {var1, var2, var3};

		} else if (length == 4) {
			byte var1 = Byte.decode(st.nextToken().toString());
			byte var2 = Byte.decode(st.nextToken().toString());
			byte var3 = Byte.decode(st.nextToken().toString());
			byte var4 = Byte.decode(st.nextToken().toString());
			byteArr = new byte[] {var1, var2, var3, var4};

		} else if (length == 5) {
			byte var1 = Byte.decode(st.nextToken().toString());
			byte var2 = Byte.decode(st.nextToken().toString());
			byte var3 = Byte.decode(st.nextToken().toString());
			byte var4 = Byte.decode(st.nextToken().toString());
			byte var5 = Byte.decode(st.nextToken().toString());
			byteArr = new byte[] {var1, var2, var3, var4, var5};

		} else if (length == 6) {
			byte var1 = Byte.decode(st.nextToken().toString());
			byte var2 = Byte.decode(st.nextToken().toString());
			byte var3 = Byte.decode(st.nextToken().toString());
			byte var4 = Byte.decode(st.nextToken().toString());
			byte var5 = Byte.decode(st.nextToken().toString());
			byte var6 = Byte.decode(st.nextToken().toString());
			byteArr = new byte[] {var1, var2, var3, var4, var5, var6};

		} else if (length == 7) {
			byte var1 = Byte.decode(st.nextToken().toString());
			byte var2 = Byte.decode(st.nextToken().toString());
			byte var3 = Byte.decode(st.nextToken().toString());
			byte var4 = Byte.decode(st.nextToken().toString());
			byte var5 = Byte.decode(st.nextToken().toString());
			byte var6 = Byte.decode(st.nextToken().toString());
			byte var7 = Byte.decode(st.nextToken().toString());
			byteArr = new byte[] {var1, var2, var3, var4, var5, var6, var7};

		} else if (length == 8) {
			byte var1 = Byte.decode(st.nextToken().toString());
			byte var2 = Byte.decode(st.nextToken().toString());
			byte var3 = Byte.decode(st.nextToken().toString());
			byte var4 = Byte.decode(st.nextToken().toString());
			byte var5 = Byte.decode(st.nextToken().toString());
			byte var6 = Byte.decode(st.nextToken().toString());
			byte var7 = Byte.decode(st.nextToken().toString());
			byte var8 = Byte.decode(st.nextToken().toString());
			byteArr = new byte[] {var1, var2, var3, var4, var5, var6, var7, var8};
		}

		return byteArr;
	}

	   //Pad hexa symbol '0x' with elements
		private String getHexaCommand(String hexaCode) throws Exception {
	    	//Clean spaces and "0x" from the string
	    	String strFormat = hexaCode.replaceAll("\\s","");
	    	strFormat = strFormat.replaceAll("0x", "");

	    	if (!PosDeviceUtil.isHexaDecimal(strFormat)) {
	    		throw new Exception("Invalid Hexadecimal format.");
	    	}

	    	//Pad "0x" character 
	    	char[] charArr = strFormat.toCharArray();
	        int varLength = charArr.length;
	        StringBuffer sbVar = new StringBuffer("0x");

	        for (int ctr=0; ctr < varLength; ctr++) {
	        	if (charArr [ctr] == ',') {
	        			sbVar.append(",0x");	
	        	} else {
	        		sbVar.append(charArr[ctr]);
	        	}
	        }
	        return sbVar.toString();
	    }    
		
	
	private void ResetPrinter() throws Exception {
		CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier("/dev/lp0");

		parallelPort = (ParallelPort) portIdentifier.open("ListPortClass", 50);
		
		OutputStream mOutputToPort = parallelPort.getOutputStream();
		
		System.out.println("=============initilaizing the printer==============");
		
		byte[] command=new byte[]{0x1B,0x40};
		mOutputToPort.write(command);

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

}
