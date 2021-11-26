/**
 * 
 */
package com.indocosmo.pos.test;

import java.io.FileInputStream;
import java.io.IOException;

import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Copies;
import javax.print.attribute.standard.Finishings;
import javax.print.attribute.standard.MediaSizeName;
import javax.print.attribute.standard.Sides;

public class PrintTest {

	private static String filePath = "/home/jojesh/WorkShop/bills/S026-PBY2-000134_TAKE_AWAY.txt";

	public static void main(String args[]) {
		PrintTest ps = new PrintTest();
	}

	public PrintTest() {
		/*
		 * Construct the print request specification. The print data is
		 * Postscript which will be supplied as a stream. The media size
		 * required is A4, and 2 copies are to be printed
		 */
		DocFlavor flavor = DocFlavor.INPUT_STREAM.TEXT_PLAIN_HOST;
		PrintRequestAttributeSet aset = new HashPrintRequestAttributeSet();
		aset.add(MediaSizeName.ISO_A4);
		aset.add(new Copies(2));
		aset.add(Sides.TWO_SIDED_LONG_EDGE);
		aset.add(Finishings.STAPLE);

		/* locate a print service that can handle it */
		PrintService pservices = PrintServiceLookup.lookupDefaultPrintService();
//		if (pservices.length > 0) 
		{
//			System.out.println("selected printer " + pservices[0].getName());

			/* create a print job for the chosen service */
			DocPrintJob pj = pservices.createPrintJob();
			try {
				/*
				 * Create a Doc object to hold the print data. Since the data is
				 * postscript located in a disk file, an input stream needs to
				 * be obtained BasicDoc is a useful implementation that will if
				 * requested close the stream when printing is completed.
				 */
				FileInputStream fis = new FileInputStream(filePath);
				Doc doc = new SimpleDoc(fis, flavor, null);

				/* print the doc as specified */
				pj.print(doc, aset);

				/*
				 * Do not explicitly call System.exit() when print returns.
				 * Printing can be asynchronous so may be executing in a
				 * separate thread. If you want to explicitly exit the VM, use a
				 * print job listener to be notified when it is safe to do so.
				 */

			} catch (IOException ie) {
				System.err.println(ie);
			} catch (PrintException e) {
				System.err.println(e);
			}
		}
	}

}
