/**
 * Use this class to define the common utility functions
 */
package com.indocosmo.pos.common.utilities;

import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.Rectangle;

import javax.swing.RootPaneContainer;

import com.indocosmo.pos.common.PosEnvSettings;
import com.indocosmo.pos.forms.PosDateMismatchWarningForm;

public final class PosUtil {

	private static Dimension defScreenSize=null;
	private static GraphicsDevice defScreen;
	private static GraphicsDevice[] screens;

	private  PosUtil() {}
	
	/**
	 * Return the available screens;
	 * @return
	 */
	public static GraphicsDevice[] getScreens(){
		
		if(screens==null){
			
			
			 GraphicsEnvironment ge = GraphicsEnvironment
				        .getLocalGraphicsEnvironment();
			 screens = ge.getScreenDevices();
			 
			 defScreen=screens[PosEnvSettings.getInstance().getPrimaryScreen()];
		}
		return screens;
	}

//	/**
//	 * @return
//	 */
//	public static Dimension getScreenSize(){
//		
//		getScreens();
//		
//		if(defScreenSize==null){
//			
//			defScreenSize=new Dimension(defScreen.getDisplayMode().getWidth(),defScreen.getDisplayMode().getHeight());
//			
//		}
//		
//		return defScreenSize;
//	}
	
	/**
	 * @param screen
	 * @return
	 */
	public static Dimension getScreenSize(int screen){
		
		getScreens();
		
		Dimension screenSize=null;
		
		if( screen > -1 && screen < screens.length )
	    {
			
			screenSize=new Dimension(screens[screen].getDisplayMode().getWidth(),screens[screen].getDisplayMode().getHeight());
	    }
	    else if( screens.length > 0 )
	    {
	    	screenSize=new Dimension(screens[0].getDisplayMode().getWidth(),screens[0].getDisplayMode().getHeight());
	    	
	    }
		
		return screenSize;
	}

//	public static int getDefScreenWidth(){
//		
//		getScreens();
//		
//		return (int)getScreenSize().getWidth();
//	}

//	public static int getDefScreenHeight(){
//		
//		getScreens();
//		
//		return (int)getScreenSize().getHeight();
//	}

//	/**
//	 * @return the defScreen
//	 */
//	public static GraphicsDevice getDefScreen() {
//		
//		getScreens();
//		
//		return defScreen;
//	}
	
	/**
	 * @param screen
	 * @return
	 */
	public static Rectangle getScreenCords(int screen){
		
		if(screens==null)
			getScreens();
		
		final Rectangle rect=new Rectangle();
		rect.x= screens[screen].getDefaultConfiguration().getBounds().getLocation().x;
		rect.y= screens[screen].getDefaultConfiguration().getBounds().getLocation().y;
		rect.height=screens[screen].getDisplayMode().getHeight();
		rect.width=screens[screen].getDisplayMode().getWidth();
		
		return rect;		
	}
	
	/**
	 * @param p
	 * @param screen
	 * @return
	 */
	public static Point transformScreenPoints(Point p, int screen){
		
		final Point tp=new Point();
		final Rectangle screenRect=getScreenCords(screen);
		tp.x=screenRect.x+p.x;
		tp.y=screenRect.y+p.y;
		
		return tp;
	}
	
	public static boolean checkPosDate(RootPaneContainer parent){
		
		boolean isProceed=true;
		if(PosEnvSettings.getInstance().getPosDate()!=null && !PosEnvSettings.getInstance().getPosDate().equals(PosDateUtil.getDate())){
			
			PosDateMismatchWarningForm form=new PosDateMismatchWarningForm();
			PosFormUtil.showLightBoxModal(parent,form);
			isProceed=!form.isCancelled();
			form.dispose();
			
		}
		
		return isProceed;
	}

	/**
	 * @param primaryScreen
	 * @return
	 */
	public static int validatePrimaryScreen(int primaryScreen) {
		
		if(primaryScreen>getScreens().length-1)
			primaryScreen=0;
		
		return primaryScreen;
	}

	/**
	 * @return
	 */
	public static String getCmdExtension() {
		// TODO Auto-generated method stub
		return null;
	}

}
