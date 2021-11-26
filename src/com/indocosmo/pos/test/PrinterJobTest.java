/**
 * 
 */
package com.indocosmo.pos.test;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.StreamPrintService;
import javax.print.StreamPrintServiceFactory;
import javax.print.event.PrintJobEvent;
import javax.print.event.PrintJobListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;

/**
 * @author jojesh-13.2
 *
 */
public class PrinterJobTest extends JFrame {
	
	JTextArea resultArea;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		PrinterJobTest testForm=new PrinterJobTest();
		testForm.pack();
		testForm.setVisible(true);

	}
	
	
	/**
	 * 
	 */
	public PrinterJobTest() {
	
		setLayout(null);
		setLocation(100,100);
		setPreferredSize(new Dimension(300,300));
		JButton printAttrTest =new JButton("Job Test");
		printAttrTest.setBounds(10,10,100,50);
		printAttrTest.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				OutputStream fos;
				try {
					fos = new BufferedOutputStream(new FileOutputStream("filename.ps"));
					 DocFlavor flavor = DocFlavor.INPUT_STREAM.GIF;
					    StreamPrintServiceFactory[] factories = StreamPrintServiceFactory
					        .lookupStreamPrintServiceFactories(flavor, DocFlavor.BYTE_ARRAY.POSTSCRIPT.getMimeType());

					    StreamPrintService service = factories[0].getPrintService(fos);

					    DocPrintJob job = service.createPrintJob();
					    job.addPrintJobListener(new MyPrintJobListener());
					    
					    
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			   
				
			}
		});
		this.add(printAttrTest);
		
		resultArea=new JTextArea();
		resultArea.setBounds(10, 70, this.getWidth()-20, this.getHeight()-50);
		this.add(resultArea);
	}
	
	class MyPrintJobListener implements PrintJobListener {
		  public void printDataTransferCompleted(PrintJobEvent pje) {
		    System.out.println("printDataTransferCompleted");
		  }

		  public void printJobCanceled(PrintJobEvent pje) {
		    System.out.println("The print job was cancelled"); 
		  }

		  public void printJobCompleted(PrintJobEvent pje) {
		    System.out.println("The print job was completed"); 
		  }

		  public void printJobFailed(PrintJobEvent pje) {
		    System.out.println("The print job has failed");
		  }

		  public void printJobNoMoreEvents(PrintJobEvent pje) {
		  }

		  public void printJobRequiresAttention(PrintJobEvent pje) {
		  }
		}

}
