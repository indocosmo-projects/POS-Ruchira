/**
 * 
 */
package com.indocosmo.pos.test;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextPane;
import javax.swing.KeyStroke;

/**
 * @author jojesh-13.2
 *
 */
public class KeyFocusTest {

	
	
	public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(new KeyBindings());
        frame.pack();
        frame.setVisible(true);
    }
}

 class KeyBindings extends Box{
	 
		
	    public KeyBindings(){
	        super(BoxLayout.Y_AXIS);
	        final JTextPane textArea = new JTextPane();
	        textArea.insertComponent(new JLabel("Text"));
	        add(textArea);

	        Action action = new AbstractAction() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                textArea.setText("New Text");
	            }};
	         String keyStrokeAndKey = "control SPACE";
	         KeyStroke keyStroke = KeyStroke.getKeyStroke(keyStrokeAndKey);
	         textArea.getInputMap().put(keyStroke, keyStrokeAndKey);
	         textArea.getActionMap().put(keyStrokeAndKey, action);
	    }
	
}
