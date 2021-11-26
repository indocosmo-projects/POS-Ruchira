/**
 * 
 */
package com.indocosmo.pos.test;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.print.DocFlavor;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.attribute.Attribute;
import javax.print.attribute.AttributeSet;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;

/**
 * @author jojesh-13.2
 *
 */
public class PrinterAtrributeTest extends JFrame {
	
	JTextArea resultArea;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		PrinterAtrributeTest testForm=new PrinterAtrributeTest();
		testForm.pack();
		testForm.setVisible(true);

	}
	
	
	/**
	 * 
	 */
	public PrinterAtrributeTest() {
	
		setLayout(null);
		setLocation(100,100);
		setPreferredSize(new Dimension(300,300));
		JButton printAttrTest =new JButton("Attr Test");
		printAttrTest.setBounds(10,10,100,50);
		printAttrTest.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				PrintService[] printServices =
				        PrintServiceLookup.lookupPrintServices(null, null);
//				for(PrintService ps:printServices )
					getAttributes( PrintServiceLookup.lookupDefaultPrintService() );
				
			}
		});
		this.add(printAttrTest);
		
		resultArea=new JTextArea();
		resultArea.setBounds(10, 70, this.getWidth()-20, this.getHeight()-50);
		this.add(resultArea);
	}
	
	public static Set<Attribute> getAttributes(PrintService printer) {
	    Set<Attribute> set = new LinkedHashSet<Attribute>();

	    //get the supported docflavors, categories and attributes
	    @SuppressWarnings("unchecked")
		Class<? extends Attribute>[] categories = (Class<? extends Attribute>[]) printer.getSupportedAttributeCategories();
	    DocFlavor[] flavors = printer.getSupportedDocFlavors();
	    AttributeSet attributes = printer.getAttributes();

	    //get all the avaliable attributes
	    for (Class<? extends Attribute> category : categories) {
	        for (DocFlavor flavor : flavors) {
	            //get the value
	            Object value = printer.getSupportedAttributeValues(category, flavor, attributes);
	            
	            System.out.println(category.getName()+" :"+value);
	            //check if it's something
	            if (value != null) {
	                //if it's a SINGLE attribute...
	                if (value instanceof Attribute)
	                    set.add((Attribute) value); //...then add it

	                //if it's a SET of attributes...
	                else if (value instanceof Attribute[])
	                    set.addAll(Arrays.asList((Attribute[]) value)); //...then add its childs
	            }
	        }
	    }

	    return set;
	}

}
