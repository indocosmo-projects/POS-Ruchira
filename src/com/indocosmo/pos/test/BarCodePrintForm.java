package com.indocosmo.pos.test;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;

import com.indocosmo.pos.common.PosEnvSettings;
import com.indocosmo.pos.terminal.devices.PosDeviceManager;
import com.indocosmo.pos.terminal.devices.printer.PosDeviceReceiptPrinter;

public class BarCodePrintForm extends JDialog {

	/**
	 * @param args  
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BarCodePrintForm frame = new BarCodePrintForm();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}
	JTextField barcodeText;
	JButton btnPrint;
	JTextField displayText;
	public BarCodePrintForm(){
		
		setBounds(100, 40, 350, 450);
		setLayout(new FlowLayout());
		
		barcodeText=new JTextField();
		barcodeText.setPreferredSize(new Dimension(300, 50));
		barcodeText.setBorder(new EtchedBorder());
		add(barcodeText);
		
		displayText=new JTextField();
		displayText.setPreferredSize(new Dimension(300, 50));
		displayText.setBorder(new EtchedBorder());
		add(displayText);
	
		btnPrint=new JButton();
		btnPrint.setPreferredSize(new Dimension(100, 40));
		btnPrint.setBorder(new EtchedBorder());
		btnPrint.setText("Print");
		btnPrint.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if(barcodeText.getText()!=null || !barcodeText.getText().trim().isEmpty()){

					com.indocosmo.barcode.reports.BarCodePrinting bp=new com.indocosmo.barcode.reports.BarCodePrinting();
					bp.setBarCodeText(barcodeText.getText());
					bp.setDisplayText(displayText.getText());
					
					try {
						
						PosEnvSettings.getInstance().init();
						PosEnvSettings.getInstance().loadEnvSettings_StageTwo();
						PosDeviceManager.getInstance().startupDevices(false,false,true,false);
						final PosDeviceReceiptPrinter printer=PosDeviceManager.getInstance().getReceiptPrinter();
						
						printer.print(bp,false);
						
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		add(btnPrint);
		
	}
	

}
