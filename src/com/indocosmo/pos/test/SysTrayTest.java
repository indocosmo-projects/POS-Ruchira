/**
 * 
 */
package com.indocosmo.pos.test;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JPanel;
import com.indocosmo.pos.common.utilities.PosFormUtil;
import com.indocosmo.pos.forms.PosBaseForm;
import com.indocosmo.pos.forms.components.buttons.PosButton;
import com.indocosmo.pos.forms.components.buttons.PosButton.ButtonStyle;
import com.indocosmo.pos.forms.components.buttons.listners.IPosButtonListner;

/**
 * @author jojesh
 *
 */
public class SysTrayTest {
	
	private static final String POSELLA_LOGO= "logos/posella_logo.png";
	/**
	 * @param args
	 */
	public static void main(String[] args) {
	
			new SysTrayTest().initSystemTray();
	}
	
	
	private  void initSystemTray() {
		
		  if (SystemTray.isSupported()) {
			 
		    SystemTray tray = SystemTray.getSystemTray();
		    PopupMenu menu = new PopupMenu();
		    Image image = Toolkit.getDefaultToolkit().createImage(POSELLA_LOGO);
		    
			  TrayIcon trayIcon = new TrayIcon(image, "Server");
			    trayIcon.setImageAutoSize(true);
			    
			    trayIcon.addMouseListener(new MouseListener() {
					
					@Override
					public void mouseReleased(MouseEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void mousePressed(MouseEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void mouseExited(MouseEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void mouseEntered(MouseEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void mouseClicked(MouseEvent e) {
						showControlPanel();
						
					}
				});
		       
		    try {
		      tray.add(trayIcon);
		    } catch (AWTException e) {
		    	e.printStackTrace();
		    }
		  }
		}
	
	/**
	 * 
	 */
	private void showControlPanel() {
		
		EezDineServerControlPanel ctrlPanel=new EezDineServerControlPanel();
		PosFormUtil.show(ctrlPanel);
		
	}
	
	/**
	 * @author jojesh
	 *
	 */
	private class EezDineServerControlPanel extends PosBaseForm {
		
		JPanel container;
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		
		/**
		 * 
		 */
		public EezDineServerControlPanel() {
			super("",300,200,false,false);
		}

		@Override
		protected void setContentPanel(JPanel panel) {
			container=panel;
			container.setLayout(new FlowLayout());
			
			PosButton btn =new PosButton("Start");
			btn.setPreferredSize(new Dimension(140,50));
			btn.setButtonStyle(ButtonStyle.COLORED);
			btn.setBackgroundColor(Color.MAGENTA);
			container.add(btn);
			
			btn =new PosButton("Test");
			btn.setPreferredSize(new Dimension(140,50));
			btn.setButtonStyle(ButtonStyle.COLORED);
			btn.setBackgroundColor(Color.GREEN.darker());
			container.add(btn);
			
			btn =new PosButton("Hide");
			btn.setPreferredSize(new Dimension(140,50));
			btn.setButtonStyle(ButtonStyle.COLORED);
			btn.setBackgroundColor(Color.ORANGE.darker());
			btn.setOnClickListner(new IPosButtonListner() {
				@Override
				public void onClicked(PosButton button) {
					closeWindow();
					
				}
			});
			container.add(btn);
			
			btn =new PosButton("Exit");
			btn.setPreferredSize(new Dimension(140,50));
			btn.setButtonStyle(ButtonStyle.COLORED);
			btn.setBackgroundColor(Color.RED);
			btn.setOnClickListner(new IPosButtonListner() {
				
				@Override
				public void onClicked(PosButton button) {
					System.exit(0);
					
				}
			});
			container.add(btn);
		}
		
	};
		
	}
