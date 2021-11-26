package com.indocosmo.pos.common.utilities;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.URISyntaxException;
import java.net.URL;

import javax.swing.ImageIcon;

import com.indocosmo.pos.common.PosEnvSettings;
import com.indocosmo.pos.common.PosLog;
import com.indocosmo.pos.common.enums.PosTerminalServiceType;

public final class PosResUtil {

	public enum PosResourceType{
		IMAGE,
		FONT,
		FILE,
	}

	private static final String RES_FOLDER="com/indocosmo/pos/res/";
	private static final String IMAGE_RES_FOLDER="images/";
	private static final String FONT_RES_FOLDER="fonts/";
	private static final String FILE_RES_FOLDER="files/";

	/**
	 * @param filename
	 * @return
	 */
	public static URL getResource(String filename){
		return ClassLoader.getSystemResource(RES_FOLDER+filename);
	}

	/**
	 * @param filename
	 * @return
	 */
	public static URL getImageResource(String filename){
		return ClassLoader.getSystemResource(RES_FOLDER+IMAGE_RES_FOLDER+filename);
	}

	/**
	 * @param filename
	 * @return
	 */
	public static URL getFontResource(String filename){
		return ClassLoader.getSystemResource(RES_FOLDER+FONT_RES_FOLDER+filename);
	}
	
	/**
	 * @param filename
	 * @return
	 */
	public static URL getFileResource(String filename){
		return ClassLoader.getSystemResource(RES_FOLDER+FILE_RES_FOLDER+filename);
	}

	/**
	 * @param filename
	 * @return
	 */
	public static ImageIcon getImageIconFromResource(String filename){
		URL url=getImageResource(filename);
		ImageIcon ico=null;
		if(url!=null)
			ico=new ImageIcon(url);
		return ico;
	}
	
	/**
	 * @param filename
	 * @param type
	 * @return
	 */
	public static InputStream getFileStreamFromResource(String filename, PosResourceType type ){
		InputStream is=null;
		String resFolder=RES_FOLDER;
		switch (type) {
		case FILE:
				resFolder+=FILE_RES_FOLDER;
			break;
		case IMAGE:
			resFolder+=IMAGE_RES_FOLDER;
		break;
		case FONT:
			resFolder+=FONT_RES_FOLDER;
		break;
		
		}
		is=ClassLoader.getSystemResourceAsStream(resFolder+filename);
		return is;

	}

	/**
	 * @param filename
	 * @return
	 */
	public static RandomAccessFile getRandomAccessFile(String filename){
		RandomAccessFile raf = null;
		try {
			raf =new RandomAccessFile("src/"+RES_FOLDER+FILE_RES_FOLDER+filename,"r");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return raf;
		
	}
	/**
	 * @param fontFile
	 * @return
	 */
	public static Font getFontFromResource(String fontFile){
		Font fontDigital=null;
		try {
			File fnFile=new File(getFontResource(fontFile).toURI());
			fontDigital=Font.createFont (Font.TRUETYPE_FONT,fnFile);
		} catch (FontFormatException | IOException e) {
			PosLog.write("PosUtil", "getFontFromResource", e);
		} catch (URISyntaxException e) {
			PosLog.write("PosUtil", "getFontFromResource", e);
		}
		return fontDigital;
	}
	
	/**
	 * @param type
	 * @param logo
	 * @return
	 */
	public static ImageIcon getLogoFromResource(String logo){
		
		String fileName="posella_title.png";
		if(PosEnvSettings.getInstance().getTerminalSettings()!=null 
				&& PosEnvSettings.getInstance().getTerminalSettings().getTerminalInfo()!=null){

			final PosTerminalServiceType type=PosEnvSettings.getInstance().getTerminalSettings().getTerminalInfo().getServiceType();
			fileName=((type==PosTerminalServiceType.RWS)?"rws":"dine")+"_"+logo;

		}
		
		URL url=getImageResource(fileName);
		ImageIcon ico=null;
		if(url!=null)
			ico=new ImageIcon(url);
		else
			ico=new ImageIcon("logos/posella_title.png");
			
		return ico;
	}
	/**
	 * @param type
	 * @param logo
	 * @return
	 */
	public static ImageIcon getAboutImageFromResource(String imageFileName){
		
		String fileName="posella_title.png";
		if(PosEnvSettings.getInstance().getTerminalSettings()!=null 
				&& PosEnvSettings.getInstance().getTerminalSettings().getTerminalInfo()!=null){

			final PosTerminalServiceType type=PosEnvSettings.getInstance().getTerminalSettings().getTerminalInfo().getServiceType();
			fileName=((type==PosTerminalServiceType.RWS)?"rws":"dine")+"_"+imageFileName;

		}
		
		URL url=getImageResource(fileName);
		ImageIcon ico=null;
		if(url!=null)
			ico=new ImageIcon(url);
		else
			ico=new ImageIcon("logos/posella_title.png");
			
		return ico;
	}
	
}
