/**
 * 
 */
package com.indocosmo.pos.common.utilities;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;

import com.indocosmo.pos.common.PosLog;
import com.indocosmo.pos.common.utilities.CryptUtil;

/**
 * 
 * @author anand
 *
 */
public class PosPropertyFileUtil {

	/***
	 * this function recieve's a file path and encrypts the content of the file
	 * 
	 * @param prprtyFilePath
	 * @throws IOException
	 */
	public static void encryptPropertyFile(String prprtyFilePath){
		try{
			
			FileReader prptyFileReader = new FileReader(prprtyFilePath);
			BufferedReader br = new BufferedReader(prptyFileReader);
			String line = br.readLine();
			StringBuilder sb = new StringBuilder();
			while(line!=null){
				sb.append(line);
				sb.append("\n");
				line=br.readLine();
			}
			if(br!=null)
				br.close();
			final String prprtyFile = sb.toString();

			encryptPropertyFile(prprtyFilePath,prprtyFile);
			
		}catch(Exception e){
				PosLog.write(PosPropertyFileUtil.class, "encryptPropertyFile("+prprtyFilePath+")", e);
		}
	}

	/**
	 * this function recieve's a file path and a text,and encrypts the received text and write's it to the file at the given path
	 * 
	 * @param prprtyFilePath
	 * @param prprtyFile
	 * @throws IOException 
	 */
	public static void encryptPropertyFile(String prprtyFilePath,String prprtyFile){
		try{

			final byte[] encryptedText = CryptUtil.getInstance().getEcryptedString(prprtyFile).getBytes();
			File encryptedfile = new File(prprtyFilePath);
			FileOutputStream fout = new FileOutputStream(encryptedfile);
			fout.write(encryptedText);
			fout.close();
		
		}catch(Exception e)
		{
			PosLog.write(PosPropertyFileUtil.class, " encryptPropertyFile("+ prprtyFilePath+","+ prprtyFile+")", e);
		}

	}
	/**
	 * this function recieve's a file path and decrypt's the content of the file, and returns it as an InputStream.
	 * 
	 * @param FilePath
	 */
	public static InputStream decryptPropertyFile(String FilePath)throws Exception{
		
		ByteArrayInputStream is=null;
	
			FileReader prptyFileReader = new FileReader(FilePath);
			BufferedReader br = new BufferedReader(prptyFileReader);
			String line = br.readLine();
			StringBuilder sb = new StringBuilder();
			while(line!=null){
				sb.append(line);
				sb.append("\n");
				line=br.readLine();
			}
			
			if(br!=null)
				br.close();
			
			final String encrytedPrprtyFile = sb.toString();
			final byte[] decryptedPrprtyFile = CryptUtil.getInstance().getDecryptedString(encrytedPrprtyFile).getBytes();
			is= new ByteArrayInputStream(decryptedPrprtyFile);


		return  is;
	}

}
