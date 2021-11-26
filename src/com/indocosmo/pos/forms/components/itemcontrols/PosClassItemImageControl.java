/**
 * 
 */
package com.indocosmo.pos.forms.components.itemcontrols;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

/**
 * @author anand
 *
 */
@SuppressWarnings("serial")
public class PosClassItemImageControl extends JPanel {
 private BufferedImage mImage;
 
	public boolean drawClassImage(BufferedImage Image){
		mImage=Image;
		if(mImage==null){
			setVisible(false);
			return false;
		}
		else{
		repaint();
		setVisible(true);
		return true;
		}
	}
	protected void paintComponent(Graphics g){
		super.paintComponent(g);
		if(mImage!=null)
			g.drawImage(mImage,0,0,this.getWidth(),this.getHeight(),0,0,mImage.getWidth(),mImage.getHeight(),null);
	}
}
