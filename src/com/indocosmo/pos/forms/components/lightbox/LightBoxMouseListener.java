package com.indocosmo.pos.forms.components.lightbox;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


public class LightBoxMouseListener implements MouseListener {
	
	private static LightBoxMouseListener lightBoxMouseListener = new LightBoxMouseListener(); 
	
	private LightBoxMouseListener(){
	}
	
	public static LightBoxMouseListener getInstance(){
		if(lightBoxMouseListener == null)
			lightBoxMouseListener = new LightBoxMouseListener();
		return lightBoxMouseListener;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {}
	@Override
	public void mouseEntered(MouseEvent e) {}
	@Override
	public void mouseExited(MouseEvent e) {}
	@Override
	public void mousePressed(MouseEvent e) {}
	@Override
	public void mouseReleased(MouseEvent e) {}
}
