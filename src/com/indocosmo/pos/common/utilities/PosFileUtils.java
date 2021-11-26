/**
 * This class handles file handling utilities
 */
package com.indocosmo.pos.common.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

import com.indocosmo.pos.common.PosLog;

/**
 * @author Ramesh S.
 *
 */
public final class PosFileUtils {

	private static PosFileUtils instance = null;
		
	private PosFileUtils() {
		//Since this class handles file,
		//it is designed in Singleton
	}
	
	public static PosFileUtils getInstance() {
		if (instance == null) {
			instance = new PosFileUtils();
		}
		return instance;
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException();
	}
	
	/*
	 * This method moves the specified file
	 * @param Source file and Destination file
	 * 
	 */
	public synchronized void moveFile(File srcFile, File destFile) throws Exception {
		copyFile(srcFile, destFile);
		srcFile.delete();

		PosLog.debug("File is moved successfully.");
	}

	
	/*
	 * This method copy the specified file
	 * @param Source file and Destination file
	 * 
	 */
	public synchronized void copyFile(File srcFile, File destFile) throws Exception {
		InputStream inStream = null;
		OutputStream outStream = null;

		inStream = new FileInputStream(srcFile.getPath());
		outStream = new FileOutputStream(destFile.getPath());

		byte[] buffer = new byte[1024];

		int length;
		// copy the file content in bytes
		while ((length = inStream.read(buffer)) > 0) {
			outStream.write(buffer, 0, length);
		}

		inStream.close();
		outStream.close();
	}
	
	
	/* 
	 * Creates folder and its sub-folders, if not existing.
	 * @param Folder to be created.
	 */
	public synchronized void createFolder(File files) {
		if (!files.exists()) {
			if (files.mkdirs()) {
				PosLog.debug("Created folder " + files.getName() + " successfully.");
			}
		}
	}
	
	
	/*
	 * Checks if file is older than passed number of days
	 * @param number of days
	 */
	public synchronized boolean isOlderThan(File file, int days) {
		Calendar oldDate = Calendar.getInstance();
		oldDate.add(Calendar.DAY_OF_MONTH, (-1 * days));
		
		Calendar fileModifiedDate = Calendar.getInstance();
		fileModifiedDate.setTime(new Date(file.lastModified()));
		
		return (oldDate.getTimeInMillis() > fileModifiedDate.getTimeInMillis());
	}


	/**
	 * @throws IOException
	 */
	public static Properties loadPropertyFile(String propertyFileName) throws IOException{

		Properties properties = null;
		File file = new File(propertyFileName);
		if (file.isFile() && file.canRead()) {
			  properties = new Properties();
			  
			  InputStream is = null;
			  is = new FileInputStream(propertyFileName);
			  properties.load(is);
			  if(is!=null)
				is.close();
		}
		
		

		return properties;
	}
}
