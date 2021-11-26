package com.indocosmo.pos.test;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;

public class HTMLMailingTestForm extends JDialog {

	/**
	 * @param args  
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					HTMLMailingTestForm frame = new HTMLMailingTestForm();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}
	JTextArea mailBodycodeText;
	JButton btnPrint;
	public HTMLMailingTestForm(){
		
		setBounds(100, 40, 350, 450);
		setLayout(new FlowLayout());
		
		mailBodycodeText=new JTextArea();
		mailBodycodeText.setPreferredSize(new Dimension(300, 350));
		mailBodycodeText.setBorder(new EtchedBorder());
		add(mailBodycodeText);
	
		btnPrint=new JButton();
		btnPrint.setPreferredSize(new Dimension(100, 40));
		btnPrint.setBorder(new EtchedBorder());
		btnPrint.setText("Print");
		btnPrint.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if(mailBodycodeText.getText()!=null || !mailBodycodeText.getText().trim().isEmpty()){
			
					    try {
					    	
					    	Session mailSession = Session.getInstance( new Properties() );
					    	Transport transport = mailSession.getTransport();

					    	String html = mailBodycodeText.getText();

					    	MimeMessage message = new MimeMessage( mailSession );
					    	Multipart multipart = new MimeMultipart( "alternative" );

					    	MimeBodyPart htmlPart = new MimeBodyPart();
					    	htmlPart.setContent( html, "text/html; charset=utf-8" );

					    	multipart.addBodyPart( htmlPart );
					    	
					    	
					    	message.setFrom(new InternetAddress("jojesh@gmail.com"));
							message.setReplyTo(new InternetAddress[]{new InternetAddress("jojesh@gmail.com"),new InternetAddress("mohandas@indocosmo.com")});
							message.setRecipients(Message.RecipientType.TO,
									InternetAddress.parse("jojesh@gmail.com"));
							message.setRecipients(Message.RecipientType.CC,
									InternetAddress.parse("jojesh@gmail.com"));
							message.setRecipients(Message.RecipientType.BCC,
									InternetAddress.parse("jojesh@gmail.com"));
							message.setSubject("[ DAILY SALES SUMARY ]");
					    	message.setContent( multipart );
					    	
					    	Transport.send(message);
					    	
						} catch (MessagingException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					
				}
			}
		});
		add(btnPrint);
		
	}
	

}
