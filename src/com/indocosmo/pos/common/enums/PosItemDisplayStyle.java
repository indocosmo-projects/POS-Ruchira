/**
 * 
 */
package com.indocosmo.pos.common.enums;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * @author anand
 *
 */
public enum PosItemDisplayStyle {
	IMAGE_ONLY("image_only"),
	TEXT_ONLY("text_only"),
	BOTH("both");

	private static final Map<String,PosItemDisplayStyle> mLookup = new HashMap<String,PosItemDisplayStyle>();

	static{
		for(PosItemDisplayStyle is:EnumSet.allOf(PosItemDisplayStyle.class)){
			mLookup.put(is.getValue(),is);
		}
	}

	private String style;

	private PosItemDisplayStyle(String style){
		this.style=style;
	}

	private String getValue(){
		return this.style;
	}

	public static PosItemDisplayStyle get(String style){
		return mLookup.get(style);
	}
}

