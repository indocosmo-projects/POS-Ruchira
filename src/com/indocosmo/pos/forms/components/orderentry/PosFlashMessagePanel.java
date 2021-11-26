/**
 * 
 */
package com.indocosmo.pos.forms.components.orderentry;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;

import com.indocosmo.pos.data.providers.shopdb.PosFlashMessageProvider;

/**
 * @author Deepak
 * 
 */
public class PosFlashMessagePanel extends JPanel implements Runnable {

	private JLabel mFlashMessagelabel;
	private String mMessage ;
	private PosFlashMessageProvider mPosFlashMessageProvider;

	public PosFlashMessagePanel(String Message) {
		super();
		this.mMessage = Message;
		setFlashMessagePanel();
	}

	public PosFlashMessagePanel() {
		super();
		mPosFlashMessageProvider = new PosFlashMessageProvider();
		this.mMessage = mPosFlashMessageProvider.getLatestFlashMessage();
		setFlashMessagePanel();
	}
	/**
	 * 
	 */
	private void setFlashMessagePanel() {
		setBackground(new Color(6,38,76));
		setMessageLabel();
		Thread t = new Thread(this);
		t.start();
		
	}

	/**
	 * Create the message label with message.
	 */
	private void setMessageLabel() {
		mFlashMessagelabel = new JLabel(mMessage);
		mFlashMessagelabel.setForeground(new Color(28,143,2));
		mFlashMessagelabel.setFont(new Font("Serif", Font.PLAIN, 14));
		add(mFlashMessagelabel);
	}

	public void run() {
		while (true) {
			char c = mMessage.charAt(0);
			String rest = mMessage.substring(1);
			mMessage = rest + c;
			mFlashMessagelabel.setText(mMessage);
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
			}
		}
	}
}
