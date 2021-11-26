/**
 * Use this class to handle the log writing.
 */
package com.indocosmo.pos.common;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

import com.indocosmo.pos.common.utilities.PosDateUtil;
import com.indocosmo.pos.common.utilities.PosFormUtil;

public final class PosLog {
	
	private static PosLog mPosLog=null;
	private String mPosLogPath;  
	private static final String mLogFileName
			= PosDateUtil.getDate("yyyyMMdd")+ "-" + PosEnvSettings.getInstance().getLogfilename();

	/**
	 * Singleton model to avoid multiple instance of this class.
	 * use getInstance instead.
	 * DO NOT ALTER THE access modifier. Do the necessary initialization.
	 */
	private PosLog() {
		mPosLogPath=PosEnvSettings.getInstance().getLogPath()+"/";
	}
	
	/**
	 * Singleton method to return the object of this class.
	 *  DO NOT ALTER THE CODE.
	 * @return: returns the same object always.
	 */
	public static synchronized PosLog getInstance(){
		if(mPosLog==null)
			mPosLog=new PosLog();
		return mPosLog;
	}
	
	/**
	 * Static method to avoid calling getInstance() to write log.
	 * @param message : Message to write.
	 */
	public static void write(Object module,String method,String message){
		PosLog.getInstance().writeLog(module.getClass().getName(),method,message);
	}
	
	public static void write(Object module,String method,Exception e){

		final String message=e.getMessage()+"\n"+getStackTrace(e);
		PosLog.getInstance().writeLog(module.getClass().getName(),method,message);
	}
	
	public static void debug(String message){
		debug(message, false);
	}
	
	public static void debug(String message, boolean showInUI){
		
		if (PosEnvSettings.getInstance().isLogDebugMode() ) {
			if(showInUI) PosFormUtil.showInformationMessageBox(null,message);
			 write("","","DEBUG INFO : " +message);
		}
	}
	
	public static void debug(Exception e){
		if (PosEnvSettings.getInstance().isLogDebugMode()){
			e.printStackTrace();
			final String message="DEBUG INFO : " +getStackTrace(e);
			 write("","",message);
		}
	}
	
	public static void write(String module,String method,String message){
		PosLog.getInstance().writeLog(module,method,message);
	}
	
	public static void write(String module,String method,Exception e){
		final String message=e.getMessage()+"\n"+getStackTrace(e);
		PosLog.getInstance().writeLog(module,method,message);
	}
	
	/* 
	 * This is a singleton class. so we must avoid cloning the objects.
	 *  DO NOT ALTER THE CODE.
	 */
	@Override
	protected Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException();
	}
	
	public synchronized void writeLog(String module, 
			String method, String message){
		if (PosEnvSettings.getInstance().isLogDebugMode()){
			System.out.println(message);
		}
		final String dateInfo="Date :" + PosDateUtil.getDate() + "\nTime :"+PosDateUtil.getTime();
		message=dateInfo+"\nModule : "+ module+ "\nMethod : "+method+"\nMessage : "+message;
		BufferedWriter bw=null;
		try {
			bw =new BufferedWriter(new FileWriter(mPosLogPath+mLogFileName,true));
			bw.append(message);
			bw.newLine();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				if (bw != null)
					bw.close();
			} catch (IOException e) {
//				e.printStackTrace();
			}
		}
	}
	
	public static void writeProcessLog(String message){
		PosLog.getInstance().ProcessLog(message);
	}
	
	public static void writeProcessLog(String message,boolean isSaveAsHTML){
		PosLog.getInstance().ProcessLog(message,isSaveAsHTML);
	}
	
	public void ProcessLog(String message){
		ProcessLog(message,false);
	}
	public void ProcessLog(String message,boolean isSaveAsHTML){
		
		try {
			FileWriter plogFile = new FileWriter(mPosLogPath+"ProcessLog"+PosDateUtil.getDateTime().replaceAll("[^\\d]", "")+((isSaveAsHTML)?".html":".txt"));
			 BufferedWriter out = new BufferedWriter(plogFile);
			 out.write(message);
			 out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
		}
		
	}
	private static String getStackTrace(Throwable aThrowable) {
	    final Writer result = new StringWriter();
	    final PrintWriter printWriter = new PrintWriter(result);
	    aThrowable.printStackTrace(printWriter);
	    return result.toString();
	  }

}
