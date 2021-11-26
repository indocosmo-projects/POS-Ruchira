/**
 * 
 */
package com.indocosmo.pos.common.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @author jojesh-13.2
 *
 */
public class PosZipUtil {


	public static void zip(String inputFolder,String targetZippedFolder)  throws IOException {

		FileOutputStream fileOutputStream = null;
		fileOutputStream = new FileOutputStream(targetZippedFolder);
		ZipOutputStream zipOutputStream = new ZipOutputStream(fileOutputStream);

		File inputFile = new File(inputFolder);
		if (inputFile.isFile())
			zipFile(inputFile,"",zipOutputStream);
		else if (inputFile.isDirectory())
			zipFolder(zipOutputStream,inputFile,"");
		zipOutputStream.close();

	}
	
	/**
	 * @author deepak
	 * @param srcFiles
	 * @param targetZippedFolder
	 * @throws IOException 
	 */
	public static void zip(String[] srcFiles, String targetZippedFolder) throws IOException {
		FileOutputStream fileOutputStream = null;
		fileOutputStream = new FileOutputStream(targetZippedFolder);
		ZipOutputStream zipOutputStream = new ZipOutputStream(fileOutputStream);

		for(String inputFolder:srcFiles){
			File inputFile = new File(inputFolder);
			if (inputFile.isFile())
				zipFile(inputFile,"",zipOutputStream);
			else if (inputFile.isDirectory())
				zipFolder(zipOutputStream,inputFile,"");
		}
		zipOutputStream.close();
	}
	
	private static void zipFolder(ZipOutputStream zipOutputStream,File inputFolder, String parentName)  throws IOException {
		
		String fileName = parentName +inputFolder.getName()+"\\";
		ZipEntry folderZipEntry = new ZipEntry(fileName);
		zipOutputStream.putNextEntry(folderZipEntry);
		File[] contents = inputFolder.listFiles();
		for (File f : contents){

			if (f.isFile())
				zipFile(f,fileName,zipOutputStream);
			else if(f.isDirectory())

				zipFolder(zipOutputStream,f, fileName);
		}

		zipOutputStream.closeEntry();

	}



	private static void zipFile(File inputFile,String parentName,ZipOutputStream zipOutputStream) throws IOException{


		ZipEntry zipEntry = new ZipEntry(parentName+inputFile.getName());

		zipOutputStream.putNextEntry(zipEntry);
		FileInputStream fileInputStream = new FileInputStream(inputFile);
		byte[] buf = new byte[1024];

		int bytesRead;

		while ((bytesRead = fileInputStream.read(buf)) > 0) {
			zipOutputStream.write(buf, 0, bytesRead);
		}

		zipOutputStream.closeEntry();
		fileInputStream.close();

		System.out.println("Regular file :" + parentName+inputFile.getName() +" is zipped to archive. ");

	}
}
