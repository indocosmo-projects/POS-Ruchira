package com.indocosmo.pos.test;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.border.EtchedBorder;

import com.indocosmo.barcode.device.BarCodeLabelPrinter;

public class BarCodeLabelPrintForm extends JDialog {

	/**
	 * @param args  
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BarCodeLabelPrintForm frame = new BarCodeLabelPrintForm();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}

	JButton btnPrint;

	public BarCodeLabelPrintForm(){
		
		setBounds(100, 40, 350, 450);
		setLayout(new FlowLayout());
		

	
		btnPrint=new JButton();
		btnPrint.setPreferredSize(new Dimension(100, 40));
		btnPrint.setBorder(new EtchedBorder());
		btnPrint.setText("Print");
		btnPrint.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				try {
					new BarCodeLabelPrinter().printBarCode("/home/11111.prn",false);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		add(btnPrint);
		
	}
	

}
