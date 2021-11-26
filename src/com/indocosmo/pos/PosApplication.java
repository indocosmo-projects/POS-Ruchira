/** 
 * The main class which starts the application.
 */
package com.indocosmo.pos;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.indocosmo.pos.common.PosEnvSettings;
import com.indocosmo.pos.common.PosLog;
import com.indocosmo.pos.forms.PosMainMenuForm;
import com.indocosmo.pos.terminal.printserver.PrintServer;

/**
 * @author jojesh
 * 
 */
public final class PosApplication {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
	
		
		try {
			UIManager.setLookAndFeel(
					UIManager.getCrossPlatformLookAndFeelClassName());
			startPos(args);
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e) {
			PosLog.write("PosApplication", "main", e);
		}
		
	}
	
	private static PosMainMenuForm posMain;
	
	private static void startPos(final String[] args) throws Exception{

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				PosEnvSettings.getInstance(args).init();
					posMain=PosMainMenuForm.getInstance();
					posMain.setAlwaysOnTop(true);
					posMain.setVisible(true);
			}
		});
	}
	
}
