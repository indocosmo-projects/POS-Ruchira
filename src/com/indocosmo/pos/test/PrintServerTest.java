/**
 * 
 */
package com.indocosmo.pos.test;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import com.indocosmo.pos.server.core.EezDineServer;
import com.indocosmo.pos.server.interfaces.IEezDineServerActionListener;
import com.indocosmo.pos.terminal.printserver.PrintServer;

public class PrintServerTest implements IEezDineServerActionListener
{

	private static final int FORM_WIDTH=500;
	private static final int FORM_HEIGHT=300;
	private PrintServer server;

	JTextArea txtRequest;
	JTextArea txtResponse;


	public static void main(String[] args)
	{
		PrintServerTest serverTest=new PrintServerTest();
	}

	PrintServerTest(){
		server=PrintServer.getInstance();
		initScreen();
	}

	private void initScreen() {
		JLabel lblRequest= new JLabel("Request ");;
		lblRequest.setBounds(10, 10, 100, 50);

		txtRequest=new JTextArea();
		txtRequest.setBounds(lblRequest.getX()+lblRequest.getWidth()+10, lblRequest.getY(), 350, 50);

		JLabel lblResponse= new JLabel("Response ");;
		lblResponse.setBounds(10, 80, 100, 50);

		txtResponse=new JTextArea();
		txtResponse.setBounds(lblResponse.getX()+lblResponse.getWidth()+10, lblResponse.getY(), 350, 50);

		JPanel pnlControls=new JPanel();
		pnlControls.setBounds(10,lblResponse.getY()+lblResponse.getHeight()+10 , FORM_WIDTH-30, 60);
		pnlControls.setPreferredSize(pnlControls.getSize());
		pnlControls.setLayout(new FlowLayout());
		pnlControls.setBackground(Color.RED);

		JButton btnStart =createButton("Start");
		JButton btnStop =createButton("Stop");
		JButton btnSend =createButton("Send");

		pnlControls.add(btnStart);
		pnlControls.add(btnStop);
		pnlControls.add(btnSend);

		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(null);
		frame.setBounds(10, 80, FORM_WIDTH, FORM_HEIGHT);

		frame.add(lblRequest);
		frame.add(txtRequest);
		frame.add(lblResponse);
		frame.add(txtResponse);

		frame.add(pnlControls);
		frame.setVisible(true);
	}

	private  JButton createButton(String caption) {

		JButton btn =new JButton(caption);
		btn.setPreferredSize(new Dimension(100,50));
		btn.addActionListener(btnListener);

		return btn;

	}

	private  ActionListener btnListener=new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {

			switch (((JButton)e.getSource()).getText()) {
			case "Start":
				try {
					server.start();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				break;
			case "Stop":
				server.stop();
				break;
			case "Send":
				send(txtRequest.getText());
				break;
			default:
				break;
			}

		}
	};

	/**
	 * @param msg
	 */
	private  void send(String msg){

		try {
			int destPort= 4300;
			Socket socket=new Socket("127.0.0.1",destPort);
			PrintWriter out=new PrintWriter(socket.getOutputStream(),true);
			out.println(msg);
			BufferedReader reader=new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String resp=reader.readLine();
			txtResponse.setText(resp);
			socket.close();

		}catch(Exception e){
			e.printStackTrace();
		}finally{

		}
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.pos.common.utilities.server.printing.RequestHandler.IPrinterServerRequestHandler#processRequest(com.indocosmo.pos.common.utilities.server.printing.RequestHandler, java.lang.String)
	 */
	@Override
	public String processRequest(String message) {
		
		System.out.println("From Form "+message);
		return "We have prcessed your message...." + message;
	}


}