/**
 * 
 */
package com.indocosmo.pos.reports.base;

import java.awt.BasicStroke;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.PrinterException;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import javax.print.attribute.standard.OrientationRequested;

import com.google.zxing.WriterException;
import com.indocosmo.barcode.common.codeprinter.CodePrintingUtil;
import com.indocosmo.barcode.common.codeprinter.bean.BeanBarCodePrintingSettings;
import com.indocosmo.pos.common.PosLog;
import com.indocosmo.pos.common.enums.device.PrinterType;
import com.indocosmo.pos.common.utilities.PosStringUtil;
import com.indocosmo.pos.common.utilities.converters.MoneyToWordsConvertor;

/**
 * @author jojesh
 * 
 * abstract class which implements IPosPrintable interface 
 * 		defines  Printer Type ( Receipt Printer for 80mm/ normal for A4  ) , 	
 * 		Paper size , Margins ,No. of copies , Page Orientation
 *		Various Line styles and print functions
 */
public abstract class PosPrintableBase implements IPosPrintable, Cloneable {


	/**
	 * Enumerator for various line styles 
	 * 
	 */
	public enum LineStyles {
		BLANK(0),SINGLE(1), DOUBLE(1), DOTED(1), THICK(4);

		private static final Map<Integer, LineStyles> mLookup = new HashMap<Integer, LineStyles>();

		static {
			for (LineStyles rc : EnumSet.allOf(LineStyles.class))
				mLookup.put(rc.getHeight(), rc);
		}

		private int mHeight;

		private LineStyles(int height) {
			this.mHeight = height;
		}

		public int getHeight() {
			return mHeight;
		}

		public static LineStyles get(int value) {
			return mLookup.get(value);
		}

	}

	/**
	 *  Enumerator for Text Alignment
	 * 
	 * 
	 */
	public enum TextAlign {
		LEFT, CENTER, RIGHT;
	}

	/**
	 * The Dashed pattern used to draw the dashed line
	 */
	private final static BasicStroke DASHED_LINE_PATTERN = new BasicStroke(1,
			BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[] {
			4.0f, 2.0f }, 0);
	/**
	 * The default stroke;
	 */
	private Stroke mDefStroke;
	
	/**
	 * The default line spacing
	 */
	protected final static int LINE_SPACING = 2;
	
	/**
	 * The graphics object of printer to which it is printed
	 */
	private Graphics2D mGPrinter;
	
	/**
	 * instance of {java.awt.print.PageFormat}
	 */
	protected PageFormat mPageFormat;
	
	/**
	 * Next line printed at
	 */
	private int mNextLineStartY;

	/**
	 * The line spacing
	 */
	private int mLineSpacing = LINE_SPACING;
	/**
	 * printable area width;
	 */
	private int mWidth;
	/**
	 * printable area height;
	 */
	private int mHeight;

	/**
	 * The font used to draw text
	 */
	private Font mFont;
	/**
	 * 
	 */
	private FontMetrics mCurFontMatrics;
	/**
	 * The default clipping area;
	 */
	private Shape mDefClipArea;

	private boolean isInitialized;

	private boolean overridePrinterSettings=false;

	private int copies=1;

	private boolean drawLines=true;

	/**
	 * 
	 */
	public PosPrintableBase() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Returns the graphics surface where it draw
	 * 
	 * @return
	 */
	protected Graphics2D getGraphics() {
		return mGPrinter;
	}

	/**
	 * Returns the page format used.
	 * 
	 * @return
	 */
	protected PageFormat getPageFormat() {
		return mPageFormat;
	}

	/*
	 * print the functi
	 * 
	 * @see java.awt.print.Printable#print(java.awt.Graphics,
	 * java.awt.print.PageFormat, int)
	 */
	@Override
	public int print(Graphics g, PageFormat pf, int pageIndex)
			throws PrinterException {

		int pageStatus=0;

		try {

			mGPrinter = (Graphics2D) g;
			mGPrinter.translate(0, 0);
			mNextLineStartY = 0;
			mPageFormat = pf;
			mPageFormat.setOrientation(getOrientation());
			if(!isInitialized){

				mWidth = (int) pf.getImageableWidth();
				mHeight = (int) pf.getImageableHeight();

				onInitialized(mGPrinter, mPageFormat);
				isInitialized=true;
			}

			mGPrinter.translate(mPageFormat.getImageableX(),
					mPageFormat.getImageableY());

			mFont = mGPrinter.getFont();
			mCurFontMatrics = mGPrinter.getFontMetrics(mFont);
			mDefClipArea = mGPrinter.getClip();
			mDefStroke = mGPrinter.getStroke();

			/** Do the actual printing **/
			pageStatus=printReport(pageIndex);

		}catch (Exception e) {
			PosLog.write(this, "print", e);
			 throw new PrinterException(e.getMessage());
		}

		return pageStatus;
	}


	/**
	 * @return
	 */
	protected int getOrientation() {
		
		return PageFormat.PORTRAIT;
	}
	
	/**
	 * @return
	 */
	public OrientationRequested getPrintingOrientation(){
		
		return (getOrientation()==PageFormat.LANDSCAPE)?OrientationRequested.LANDSCAPE:OrientationRequested.PORTRAIT;
	}

	/**
	 * Override this method to print the report
	 * 
	 * @param Graphics
	 *            object
	 * @param PageFormat
	 *            object
	 * @throws Exception
	 */
	protected abstract int printReport(int pageIndex) throws Exception;

	/**
	 * Override this method to initialize Set the default font stroke etc.
	 * 
	 * @param g2d
	 * @param pf
	 * @throws Exception
	 */
	protected abstract void onInitialized(Graphics2D g2d, PageFormat pf)
			throws Exception;

	/**
	 * Print the text to the printer
	 * 
	 * @param line
	 */
	protected void printTextLine(String line) {
		printText(TextAlign.LEFT, line);
	}

	/**
	 * Print the text to the printer
	 * 
	 * @param align
	 * @param line
	 */
	protected void printText(TextAlign align, String line) {
		printText(align, line, true);
	}

	/**
	 * Print the text to the printer
	 * 
	 * @param align
	 * @param line
	 * @param font
	 * @param advanceLine
	 *            advances to next line
	 */
	protected void printText(TextAlign align, String line, boolean advanceLine) {
		printText(0, mWidth, align, line, advanceLine);
	}

	/**
	 * @param align
	 * @param line
	 * @param advanceLine
	 * @param trim
	 */
	protected void printText(TextAlign align, String line, boolean advanceLine,boolean allowBlank) {
		printText(0, mWidth, align, line, advanceLine,allowBlank);
	}


	/**
	 * Prints the given text.
	 * @param left
	 * @param width
	 * @param align
	 * @param text
	 * @param advanceLine
	 */
	protected void printText(int left, int width, TextAlign align, String text,
			boolean advanceLine) {

		printText(left, width, align, text,
				advanceLine,false);
	}

	
	/**
	 * Prints the given text.
	 * 
	 * @param left
	 * @param width
	 * @param align
	 * @param text
	 * @param advanceLine
	 */
	protected void printText(int left, int width, TextAlign align, String text,
			boolean advanceLine,boolean allowBlank) {

//		if (text == null || ((allowBlank) ? (text.length() == 0):(text.trim().length() == 0)))
//			return;
		
		if (((text == null || text.trim().length() == 0) && allowBlank) ? false:(text == null || text.trim().length() == 0))
			return;

		if(text!=null){
			int textLeft = left;
			switch (align) {
			case CENTER: {
				final int textWidth = mCurFontMatrics.stringWidth(text);
				textLeft += (width - textWidth) / 2;
				break;
			}
			case LEFT: {
				textLeft = left;
				break;
			}
			case RIGHT: {
				final int textWidth = mCurFontMatrics.stringWidth(text);
				textLeft += ((int) width - textWidth);
				break;
			}

			}
			
			mGPrinter.drawString(text, textLeft,
					mNextLineStartY + mCurFontMatrics.getHeight());
		}
		if (advanceLine)
			mNextLineStartY += mCurFontMatrics.getHeight() + getLineSpacing();
	}

	/**
	 * @param text
	 * @return width of parameter text 
	 */
	protected int getTextWidth(String text){

		return  mCurFontMatrics.stringWidth(text);
	}

	/**
	 *  prints image 
	 * @param image 
	 */
	protected void printImage(BufferedImage image){

		final double width=mWidth;

		int imgWidth=image.getWidth();
		int imgHeight=image.getHeight();

		final double aspectRatio=(float)imgHeight/(float)imgWidth;

		if(imgWidth>mWidth){
			imgWidth=mWidth;
			imgHeight=(int)(imgWidth*aspectRatio);

		}

		final int x=((int)width/2) -imgWidth/2;
		final int y=mNextLineStartY;


		printImage(image,x,y,imgWidth,imgHeight);		

	}

	/**
	 * prints image at a specified position 
	 * @param image
	 * @param left
	 * @param right
	 */
	protected void printImage(BufferedImage image,int left,int right){

		printImage(image,left,right,image.getWidth(),image.getHeight());
	}

	/**
	 * prints image at a specified position and with specified size
	 * @param image
	 * @param left
	 * @param right
	 * @param width
	 * @param height
	 */
	protected void printImage(BufferedImage image,int left,int right,int width,int height){

		mGPrinter.drawImage(image,left,right,width,height, null);
		mNextLineStartY += mCurFontMatrics.getHeight() + height;
	}
	
	/**
	 * print text inside a rectangle
	 * @param x
	 * @param width
	 * @param height
	 * @param text
	 * @param advanceLine
	 */

	protected void printTextInRectangle(int x,int width,int height,String text,boolean advanceLine) {

		int yPosition = mNextLineStartY;
		mNextLineStartY += height/2-mCurFontMatrics.getHeight()/2;
		printTextBlock(x, width, text, TextAlign.CENTER, false);
		mNextLineStartY = yPosition;
		setStroke(DASHED_LINE_PATTERN);
		mGPrinter.drawRect(x, mNextLineStartY, width, height);
		resetStroke();
		if (advanceLine)
			mNextLineStartY += mCurFontMatrics.getHeight()+height;
	}

	/**
	 * print block of text at specified x position, 
	 * won't print if the text is blank 
	 * @param blockLleft
	 * @param blockWidth
	 * @param text
	 * @param align
	 * @param advanceLine - point to the next line if true
	 */
	protected void printTextBlock(int blockLleft, int blockWidth, String text,
			TextAlign align, boolean advanceLine){

		printTextBlock( blockLleft,  blockWidth,  text,
				align,  advanceLine,false);
	}

	/**
	 * Print at specified x position, print blank line if empty text
	 *  
	 * @param x
	 * @param text
	 * @param advanceLine  
	 * @param allowBlank , if true, print blank line for empty text 
	 */
	protected void printTextBlock(int blockLleft, int blockWidth, String text,
			TextAlign align, boolean advanceLine,boolean allowBlank) {

		if (((text == null || text.trim().length() == 0) && allowBlank) ? false:(text ==null || text.trim().length() == 0))
			return;
		
		if(blockWidth==0)
			return;
		
		if (text == null || text.trim().length() == 0){ 
//			mGPrinter.clipRect(blockLleft, mNextLineStartY, blockWidth,
//					mCurFontMatrics.getHeight()*2);
			printText(blockLleft, blockWidth, align, text, advanceLine,allowBlank);
//			mGPrinter.setClip(mDefClipArea);
		}else{
			
			final String[] textLines=text.split("\\r\\n|\\n|\\r");
			for (int lineIndex=0; lineIndex<textLines.length; lineIndex++) {
			
				final String[] wrappedTextLines = PosStringUtil.getWrappedLines(textLines[lineIndex],getGraphics(),blockWidth);
		
				for (int wrappedLineIndex=0; wrappedLineIndex<wrappedTextLines.length; wrappedLineIndex++) {
		
					mGPrinter.clipRect(blockLleft, mNextLineStartY, blockWidth,
							mCurFontMatrics.getHeight()*2);
					//commented by Udhay for space between items on 25 Nov 2021
					printText(blockLleft, blockWidth, align,"\n", 
						      (wrappedLineIndex<wrappedTextLines.length-1 || lineIndex<textLines.length-1)?true:advanceLine,allowBlank);
					printText(blockLleft, blockWidth, align, wrappedTextLines[wrappedLineIndex], 
							(wrappedLineIndex<wrappedTextLines.length-1 || lineIndex<textLines.length-1)?true:advanceLine,allowBlank);
					//commented by Udhay for space between items on 25 Nov 2021
					printText(blockLleft, blockWidth, align,"\n", 
						      (wrappedLineIndex<wrappedTextLines.length-1 || lineIndex<textLines.length-1)?true:advanceLine,allowBlank);
					mGPrinter.setClip(mDefClipArea);
				
				}
			}
		}
	}

	/**
	 * Advances by one line
	 */
	protected void advanceLine() {
		advanceLine(1);
	}

	/**
	 * Advances by lineCount
	 * 
	 * @param lineCount
	 */
	protected void advanceLine(int lineCount) {
		mNextLineStartY += getLineSpacing()* lineCount;
	}

	/**
	 * Advances by lineCount
	 * 
	 * @param lineCount
	 */
	protected void advanceTextLine(int lineCount) {

		mNextLineStartY += (mCurFontMatrics.getHeight()+getLineSpacing())*lineCount;
	}

	/**
	 * Print a line
	 */
	protected void printLine(int startX, int startY, int endX, int endY ) {

		printLine( startX,  startY,  endX,  endY, true);
	}

	/**
	 * Print a line
	 */
	protected void printLine(int startX, int startY, int endX, int endY, boolean advanceLine) {

		if(!drawLines) return;

		final int lineHeight = Math.abs(endY-startY);
		mGPrinter.drawLine(startX, startY, endX, endY);
		if(advanceLine)
			mNextLineStartY += getLineSpacing() + lineHeight;
	}


	/**
	 * Print a line
	 */
	protected void printDottedLine(int startX, int startY, int endX, int endY, boolean advanceLine) {

		if(!drawLines) return;

		final int lineHeight = Math.abs(endY-startY);
		setStroke(DASHED_LINE_PATTERN);
		mGPrinter.drawLine(startX, startY, endX, endY);
		if(advanceLine)
			mNextLineStartY += getLineSpacing() + lineHeight;
		resetStroke();
	}

	/**
	 * Print a single line
	 */
	protected void printSingleLine() {

		if(!drawLines) return;

		final int lineHeight = LineStyles.SINGLE.getHeight();
		mGPrinter.drawLine(1, mNextLineStartY, mWidth, mNextLineStartY);
		mNextLineStartY += getLineSpacing() + lineHeight;
	}

	 

	/**
	 * Print a double line
	 */
	protected void printDoubleLine() {

		if(!drawLines) return;

		final int lineHeight = LineStyles.SINGLE.getHeight();
		mGPrinter.drawLine(1, mNextLineStartY, mWidth, mNextLineStartY);
		mNextLineStartY += lineHeight * 2;
		mGPrinter.drawLine(1, mNextLineStartY, mWidth, mNextLineStartY);
		mNextLineStartY += getLineSpacing() + lineHeight;
	}

	/**
	 * Print a doted line
	 */
	protected void printDashedLine() {

		if(!drawLines) return;
		printDashedLine(DASHED_LINE_PATTERN);
	}

	/**
	 * Print a doted line
	 */
	protected void printDashedLine(Stroke pattern) {

		if(!drawLines) return;

		setStroke(pattern);
		printSingleLine();
		resetStroke();
	}

	/**
	 * Print a thick line with default thickness
	 */
	protected void printThickLine() {

		if(!drawLines) return;
		printThickLine(LineStyles.THICK.getHeight());
	}

	/**
	 * Print a thick line with specified thickness
	 */
	protected void printThickLine(int thickness) {

		if(!drawLines) return;
		Stroke stroke = new BasicStroke(thickness);
		setStroke(stroke);
		mGPrinter.drawLine(1, mNextLineStartY+thickness/2, mWidth,mNextLineStartY+thickness/2);
		mNextLineStartY += getLineSpacing()+thickness/2 ;
		resetStroke();
	}

	/**
	 * Print lines based on the style specified.
	 * 
	 * @param style
	 */
	protected void drawLine(LineStyles style) {
		
		switch (style) {
		case DOTED:
			printDashedLine();
			break;
		case DOUBLE:
			printDoubleLine();
			break;
		case SINGLE:
			printSingleLine();
			break;
		case THICK:
			printThickLine();
			break;
		default:
			break;
		}
	}

	/**
	 * Return the current line spacing;
	 * 
	 * @return
	 */
	protected int getLineSpacing() {
		return mLineSpacing;
	}

	/**
	 * Sets the line spacing
	 * 
	 * @param spacing
	 */
	protected void setLineSpacing(int spacing) {
		mLineSpacing = spacing;
	}

	/**
	 * Sets the stroke pattern
	 * 
	 * @param stroke
	 */
	protected void setStroke(Stroke stroke) {
		mGPrinter.setStroke(stroke);
	}

	/**
	 * Resets the stroke pattern to default.
	 * 
	 * @param stroke
	 */
	protected void resetStroke() {
		mGPrinter.setStroke(mDefStroke);
	}

	/**
	 * Sets the font to draw text
	 * 
	 * @param font
	 */
	protected void setFont(Font font) {
		mFont = font;
		mGPrinter.setFont(font);
		mCurFontMatrics = mGPrinter.getFontMetrics(mFont);
	}

	/**
	 * Sets the font size
	 * 
	 * @param size
	 */
	protected void setFontSize(float size) {
		Font font = mGPrinter.getFont().deriveFont(size);
		setFont(font);
	}

	/**
	 * sets the font style BOLD, PLAIN, ITALIC
	 * 
	 * @param style
	 */
	protected void setFontStyle(int style) {
		Font font = mGPrinter.getFont().deriveFont(style);
		setFont(font);
	}

	/**
	 * Returns the font which is used to draw the text
	 * 
	 * @return
	 */
	protected Font getFont() {
		return mFont;
	}

	/**
	 * print amount in words 
	 * @param amt 
	 */
	protected void printMoneyInText(double amt){

		printMoneyInText(amt);
	}

	/**
	 * print amount in words 
	 * @param amt
	 */
	protected void printMoneyInText(String amt){

		String amoInWords = MoneyToWordsConvertor.toWords(amt);
		String[] amolines = PosStringUtil.getWrappedLines(amoInWords,getGraphics(),getPosReportPageFormat().getImageableWidthInPixcel());

		for (String amoLine : amolines) {
			printText(TextAlign.LEFT, amoLine);
		}
	}

	/**
	 * The listener object
	 */
	protected IPosPrintableListener mListner;
	/**
	 * Set the listener
	 * @param listener
	 */
	public void setListener(IPosPrintableListener listener){
		mListner=listener;
	}
	/**
	 * Receipt interface listener
	 * @author jojesh
	 *
	 */
	public interface IPosPrintableListener{
		public void onPrintingCompleted();
	}

	/**
	 * @return the mNextLineStartY
	 */
	protected int getNextLineStartY() {
		return mNextLineStartY;
	}
	
	/**
	 * @return y position of last printed line 
	 */
	protected int getLastPrintedLineStartY() {
		return mNextLineStartY-getLineHeight()-getLineSpacing();
	}
 
	/**
	 * @return y position of next line to be printed 
	 */
	protected void setNextLineStartY(int y){

		this.mNextLineStartY=y;

	}
	
	/**
	 * @return line height
	 */
	protected int getLineHeight(){
		
		return mCurFontMatrics.getHeight()+getLineSpacing();
	}

	/***
	 * Override this function to make it printable or not.
	 * @return
	 */
	public boolean isPrintable(){

		return true;
	}

	/**
	 * @return  the allowMultipleCopies
	 */
	public boolean canOverridePrinterSettings() {
		return overridePrinterSettings;
	}

	/**
	 * @param allowMultipleCopies, allow multiple copies if true
	 */
	public void setOverridePrinterSettings(boolean allowMultipleCopies) {
		this.overridePrinterSettings = allowMultipleCopies;
	}

	/**
	 * @return the drawLines
	 */
	public boolean isDrawLines() {
		return drawLines;
	}

	/**
	 * @param drawLines the drawLines to set
	 */
	public void setDrawLines(boolean drawLines) {
		this.drawLines = drawLines;
	}

	/**
	 * @return copies- return no. of copies 
	 */
	public int getCopies() {
		return copies;
	}

	/**
	 * @param copies - set the no. of copies 
	 */
	public void setCopies(int copies) {
		this.copies = copies;
	}

	/**
	 * @return width mWidth
	 */
	public int getWidth(){

		return mWidth;
	}

	/**
	 * @return height mHeight
	 */
	public int getHeight() {
		return mHeight;
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.pos.reports.base.IPosPrintable#getPosReportPageFormat()
	 */
	@Override
	public PosReportPageFormat getPosReportPageFormat() {

		return PosReportPageFormat.PAGE_80MM;
	}
	
	/* 
	 * 
	 * (non-Javadoc)
	 * @see com.indocosmo.pos.reports.base.IPosPrintable#getPrinterType()
	 */
	@Override
	public PrinterType getPrinterType() {
		
		return PrinterType.Receipt80;
	}

	
	/* (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	@Override
	public Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}
	
	/**
	 * 
	 * prints Barcode
	 * @param settings
	 * @throws WriterException
	 */
	protected void printBarcode(BeanBarCodePrintingSettings settings) throws WriterException {
				
		BufferedImage barCode=com.indocosmo.barcode.common.codeprinter.CodePrintingUtil.createBarCode(settings);
		printImage(barCode);
		
	}
	
	
}
