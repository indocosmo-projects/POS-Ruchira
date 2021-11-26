package com.indocosmo.pos.forms.components.lightbox;

import java.awt.Toolkit;

import javax.swing.JFrame;

/**
 * Light box utility class that provides utility method used by LightBox class.
 * 
 */
public final class LightBoxUtil {
	
	/**
	 * 
	 */
	protected LightBoxUtil() {
	}
	
	/**
	 * Give buzz effect to frame.
	 * 
	 */
	protected void buzzFrame(JFrame frame) {
		try { 
			final int frameX = frame.getLocationOnScreen().x, 
					frameY = frame.getLocationOnScreen().y; 
			for(int i = 0; i < 20; i++) { 
				Thread.sleep(10); 
				frame.setLocation(frameX, frameY + 5); 
				Thread.sleep(10); 
				frame.setLocation(frameX, frameY - 5);
				Thread.sleep(10); 
				frame.setLocation(frameX + 5, frameY);
				Thread.sleep(10); 
				frame.setLocation(frameX, frameY); 
			}
		} 
		catch (Exception e) { 
			e.printStackTrace();
		}
	}

	/**
	 * Blinks frame's task bar entry for attention.
	 * @param frame Frame object.
	 * 
	 */
	protected void blinkFrameOnTaskbar(JFrame frame){
		if(!frame.hasFocus() || frame.getExtendedState() == JFrame.ICONIFIED){
			frame.setVisible(true);
		}
	}
	
	/**
	 * Makes a beep sound.
	 */
	protected void makeBeepSound() {
		Toolkit.getDefaultToolkit().beep();
	}
}