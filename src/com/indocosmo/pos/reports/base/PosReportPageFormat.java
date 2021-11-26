/**
 * 
 */
package com.indocosmo.pos.reports.base;

import javax.print.attribute.standard.MediaPrintableArea;
import javax.print.attribute.standard.MediaSize;
import javax.print.attribute.standard.MediaSizeName;

/**
 * @author jojesh-13.2
 *
 */
public class PosReportPageFormat {


	public static final PosReportPageFormat PAGE_A4 = new PosReportPageFormat(MediaSizeName.ISO_A4,MediaSize.ISO.A4.getX(MediaPrintableArea.MM), MediaSize.ISO.A4.getY(MediaPrintableArea.MM),5, 5,5,5);
	public static final PosReportPageFormat PAGE_80MM = new PosReportPageFormat(72, 2000, 1, 1, 1, 1);

	private MediaSizeName mediaSizeName;
	private float height=2000;
	private float width=70;
	private float marginLefft=1;
	private float marginTop=1;
	private float marginRight=1;
	private float marginBottom=1;
	private int unit=MediaPrintableArea.MM;

	/**
	 * 
	 */
	public PosReportPageFormat(){
	}
	
	/**
	 * @param mediaSizeName
	 * @param width
	 * @param height
	 * @param marginLefft
	 * @param marginTop
	 * @param marginRight
	 * @param marginBottom
	 */
	public PosReportPageFormat(MediaSizeName mediaSizeName,float width, float height, float marginLefft, float marginTop, float marginRight, float marginBottom){

		this.height=height;
		this.width=width;
		this.marginLefft=marginLefft;
		this.marginTop=marginTop;
		this.marginRight=marginRight;
		this.marginBottom=marginBottom;
		this.mediaSizeName=mediaSizeName;
		
	}

	/**
	 * @param width
	 * @param height
	 * @param marginLefft
	 * @param marginTop
	 * @param marginRight
	 * @param marginBottom
	 */
	public PosReportPageFormat(float width, float height, float marginLefft, float marginTop, float marginRight, float marginBottom){

		this.height=height;
		this.width=width;
		this.marginLefft=marginLefft;
		this.marginTop=marginTop;
		this.marginRight=marginRight;
		this.marginBottom=marginBottom;

	}

	/**
	 * @param width
	 * @param height
	 * @param marginLefft
	 * @param marginTop
	 * @param marginRight
	 * @param marginBottom
	 * @param unit
	 */
	public PosReportPageFormat(float width, float height, float marginLefft, float marginTop, float marginRight, float marginBottom, int unit){

		this.height=height;
		this.width=width;
		this.marginLefft=marginLefft;
		this.marginTop=marginTop;
		this.marginRight=marginRight;
		this.marginBottom=marginBottom;
		this.unit=unit;

	}
	/**
	 * @return the height
	 */
	public float getHeight() {

		return height;
	}
	/**
	 * @param height the height to set
	 */
	public void setHeight(float height) {

		this.height = height;
	}
	/**
	 * @return the width
	 */
	public float getWidth() {

		return width;
	}
	/**
	 * @param width the width to set
	 */
	public void setWidth(float width) {

		this.width = width;
	}
	/**
	 * @return the marginLefft
	 */
	public float getMarginLefft() {

		return marginLefft;
	}
	/**
	 * @param marginLefft the marginLefft to set
	 */
	public void setMarginLefft(float marginLefft) {

		this.marginLefft = marginLefft;
	}
	/**
	 * @return the marginTop
	 */
	public float getMarginTop() {

		return marginTop;
	}
	/**
	 * @param marginTop the marginTop to set
	 */
	public void setMarginTop(float marginTop) {

		this.marginTop = marginTop;
	}
	/**
	 * @return the marginRight
	 */
	public float getMarginRight() {

		return marginRight;
	}
	/**
	 * @param marginRight the marginRight to set
	 */
	public void setMarginRight(float marginRight) {

		this.marginRight = marginRight;
	}
	/**
	 * @return the marginBottom
	 */
	public float getMarginBottom() {

		return marginBottom;
	}
	/**
	 * @param marginBottom the marginBottom to set
	 */
	public void setMarginBottom(float marginBottom) {

		this.marginBottom = marginBottom;
	}
	/**
	 * @return the unit
	 */
	public int getUnit() {

		return unit;
	}
	/**
	 * @param unit the unit to set
	 */
	public void setUnit(int unit) {

		this.unit = unit;
	}

	/**
	 * @return
	 */
	public float getImageableWidth(){

		return getWidth()-getMarginLefft()-getMarginRight();
	}
	
	/**
	 * @return
	 */
	public float getImageableHeight(){

		return getHeight()-getMarginTop()-getMarginBottom();
	}

	/**
	 * @return
	 */
	public int getImageableWidthInPixcel(){

		final int imagableWidth=toPixcel(getImageableWidth());

		return imagableWidth;
	}
	
	/**
	 * @return
	 */
	public int getImageableHeightInPixcel(){

		final int imagableHeight=toPixcel(getImageableHeight());

		return imagableHeight;
	}

	/**
	 * @param value
	 * @return
	 */
	private int toPixcel(float value){
		
		final float pixcelValue=((value*getInchDivisionFactor())*72f);
		
		return (int) pixcelValue;
	}
	
	/**
	 * @return
	 */
	private float getInchDivisionFactor(){
		
		final float divisionFactorToInch=0.0393700787401575f;
		
		return divisionFactorToInch;
	}

	/**
	 * @return the mediaSizeName
	 */
	public MediaSizeName getMediaSizeName() {
		return mediaSizeName;
	}

	/**
	 * @param mediaSizeName the mediaSizeName to set
	 */
	public void setMediaSizeName(MediaSizeName mediaSizeName) {
		this.mediaSizeName = mediaSizeName;
	}
}
