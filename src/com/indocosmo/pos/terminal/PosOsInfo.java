package com.indocosmo.pos.terminal;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;




public final class PosOsInfo {

	private final static String OS_NAME_TAG = "os.name";  
	private final static String OS_VERSION_TAG = "os.version";  
	private final static String OS_ARCH_TAG = "os.arch";
	
	public enum OsType{
		OS_WINDOWS("windows"),
		OS_LINUX("linux"),
		OS_OTHER("other");
		
		private static final Map<String,OsType> mLookup=new HashMap<String,OsType>();

		static {
			for(OsType hi:EnumSet.allOf(OsType.class))
				mLookup.put(hi.getCode(), hi);
		}

		private String mCode;
		private OsType(String code){
			mCode=code;
		}

		public String getCode(){
			return mCode;
		}

		public static OsType get(String code){
			OsType osType=null;
			if(mLookup.containsKey(code))
				osType=mLookup.get(code);
			else
				osType=OS_OTHER;
			return osType;
		}
	}

//	private String mName;  
//	private String mVersion;  
//	private String mArchitecture ;
	/**
	 * @return the nameOS
	 */
	public static String getName() {
//		return mName;
		return System.getProperty(OS_NAME_TAG) ;
	}
//	/**
//	 * @param nameOS the nameOS to set
//	 */
//	public void setName(String name) {
//		this.mName = name;
//	}
	/**
	 * @return the versionOS
	 */
	public static String getVersion() {
//		return mVersion;
		return System.getProperty(OS_VERSION_TAG) ;
	}
//	/**
//	 * @param versionOS the versionOS to set
//	 */
//	public void setVersionOS(String version) {
//		this.mVersion = version;
//	}
	/**
	 * @return the architectureOS
	 */
	public static String getArchitecture() {
//		return mArchitecture;
		return System.getProperty(OS_ARCH_TAG) ;
	}
//	/**
//	 * @param architectureOS the architectureOS to set
//	 */
//	public void setArchitecture(String architecture) {
//		this.mArchitecture = architecture;
//	} 
	
	public static OsType getType(){
		return OsType.get(getName());
	}
}