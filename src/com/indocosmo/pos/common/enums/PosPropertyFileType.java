/**
 * 
 */
package com.indocosmo.pos.common.enums;

import java.util.EnumSet;
import java.util.HashMap;

/**
 * @author jojesh-13.2
 *
 */
public enum PosPropertyFileType {

	APP("App Properties","pos-terminal.properties"),
	UI("UI Properties","pos-terminal-ui.properties"),
	PRINT("Printitng Properties","pos-terminal-print.properties");

	private static final HashMap<String,PosPropertyFileType> mLookUp = new HashMap<String, PosPropertyFileType>();

	static{
		for(PosPropertyFileType item: EnumSet.allOf(PosPropertyFileType.class))
			mLookUp.put(item.getTitle(),item);
	}

	private String titile;
	private String fileName;

	private PosPropertyFileType(String titile,String fileName){

		this.titile=titile;
		this.fileName=fileName;
	}

	public String getTitle() {

		return titile;
	}

	public String getFileName() {

		return fileName;
	}

}
