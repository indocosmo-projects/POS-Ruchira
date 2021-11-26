/**
 * 
 */
package com.indocosmo.pos.forms.components.itemcontrols;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.indocosmo.pos.common.enums.PosItemDisplayStyle;
import com.indocosmo.pos.data.beans.BeanSaleItem;

/**
 *@author anand
 *
 */
@SuppressWarnings("serial")
public class PosItemImageControlPanel extends PosItemDetailControlPanel{
	
	private BufferedImage image;
	/**
	 * 
	 */
	public PosItemImageControlPanel(){
		super();
		setOpaque(false);
		designPanel();
	}
	
	/**
	 * @param width
	 * @param height
	 */
	private void setDimension(int width,int height){
		setSize(width, height);
		setLocation(PANEL_CONTENT_H_GAP, PANEL_CONTENT_V_GAP);
	}
	
	/* (non-Javadoc)
	 * @see com.indocosmo.pos.forms.components.itemcontrols.PosItemDetailControlPanel#setItem(com.indocosmo.pos.data.beans.BeanSaleItem)
	 */
	@Override
	public void setItem(BeanSaleItem item){
		this.posSaleItem=item;
		if(displayMode!=PosItemDisplayStyle.TEXT_ONLY)
			drawImage(posSaleItem.getSaleItemImage());
	}
	
	/* (non-Javadoc)
	 * @see com.indocosmo.pos.forms.components.itemcontrols.PosItemDetailControlPanel#designPanel()
	 */
	@Override
	protected void designPanel(){		
		switch(displayMode){
		case IMAGE_ONLY:
			setDimension(parentCntrlWidth-PANEL_CONTENT_H_GAP*2,parentCntrlHeight-PANEL_CONTENT_V_GAP*2);
			break;
		case TEXT_ONLY:
			setVisible(false);		
			break;
		case BOTH:
			setDimension(parentCntrlHeight-PANEL_CONTENT_V_GAP*2,parentCntrlHeight-PANEL_CONTENT_V_GAP*2);
			break;
		}
	}
	/**
	 * @param img
	 */
	private void drawImage(BufferedImage img){
		image=img;
		if(image==null){
			setVisible(false);
			return;
		}
		try{
			setVisible(true);
			repaint();
		}catch(IllegalArgumentException i){
			i.printStackTrace();
		}
	}
	/* (non-Javadoc)
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	@Override
	protected void paintComponent(Graphics g){
		super.paintComponent(g);
		if(image!=null )
			g.drawImage(image,0,0,this.getWidth(),this.getHeight(),0,0,image.getWidth(),image.getHeight(),null);
	}
}
