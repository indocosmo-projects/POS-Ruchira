package com.indocosmo.pos.data.beans.terminal.device;



/**
 * @author Jojesh S
 * @since 08th Aug 2012
 **/

public class BeanDevPrinterConfig {

	private BeanPrinterServiceInfo mPrinterInfo;
	private int mNoCopies=1;
	private String mPaperCutCode;
	private boolean mIsPaperCutOn;
	private boolean mUseAltLangugeToPrint;
	private boolean mIsActive=true;
	
	/**
	 * @return the printerName
	 */
	public String getName() {
		return ((mPrinterInfo!=null)?mPrinterInfo.getName():"");
	}
	
	/**
	 * sets the printer info
	 * @param pritnerInfo
	 */
	public void setPrinterInfo(BeanPrinterServiceInfo pritnerInfo){
		mPrinterInfo=pritnerInfo;
	}
	
	public BeanPrinterServiceInfo getPrinterInfo(){
		return mPrinterInfo;
	}


	/**
	 * @return the mNoCopies
	 */
	public int getNoCopies() {
		return mNoCopies;
	}

	/**
	 * @param mNoCopies the mNoCopies to set
	 */
	public void setNoCopies(int noCopies) {
		this.mNoCopies = noCopies;
	}

	/**
	 * @return the mIsPaperCutOn
	 */
	public boolean isPaperCutOn() {
		return mIsPaperCutOn;
	}

	/**
	 * @param Is Paper Cut On
	 */
	public void setPaperCutOn(boolean isPaperCutOn) {
		this.mIsPaperCutOn = isPaperCutOn;
	}
	
	/**
	 * @return the Paper Cut Code
	 */
	public String getPaperCutCode() {
		return mPaperCutCode;
	}
	
	/**
	 * @param Paper Cut Code to set
	 */
	public void setPaperCutCode(String paperCutCode) {
		this.mPaperCutCode = paperCutCode;
	}
	/**
	 * @return the mUseAltLangugeToPrint
	 */
	public boolean isUseAltLangugeToPrint() {
		return mUseAltLangugeToPrint;
	}

	/**
	 * @param mUseAltLangugeToPrint the mUseAltLangugeToPrint to set
	 */
	public void setUseAltLangugeToPrint(boolean mUseAltLangugeToPrint) {
		this.mUseAltLangugeToPrint = mUseAltLangugeToPrint;
	}

	/**
	 * @return the mIsActive
	 */
	public boolean isActive() {
		return mIsActive;
	}

	/**
	 * @param mIsActive the mIsActive to set
	 */
	public void setActive(boolean mIsActive) {
		this.mIsActive = mIsActive;
	}


}
