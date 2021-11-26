/**
 * 
 */
package com.indocosmo.pos.common.enums;

import java.util.EnumSet;
import java.util.HashMap;
/**
 * @author anand
 *
 */
public enum ImageButtonTextPosition {

		ALONGSIDE_IMAGE("alongside_image"),
		UNDER_IMAGE("under_image");
		
		private static final HashMap<String,ImageButtonTextPosition> mLookUp = new HashMap<String, ImageButtonTextPosition>();
		
		static{
			for(ImageButtonTextPosition item: EnumSet.allOf(ImageButtonTextPosition.class))
				mLookUp.put(item.getCode(),item);
		}
		
		private String mCode;
		
		private ImageButtonTextPosition(String code){
			this.mCode=code;
		}

		public String getCode() {
			
			return mCode;
		}
		
		public static ImageButtonTextPosition get(String code){
			return mLookUp.get(code);
		}

	}

