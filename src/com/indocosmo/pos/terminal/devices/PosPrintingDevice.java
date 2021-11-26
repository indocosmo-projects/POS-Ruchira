/**
 * 
 */
package com.indocosmo.pos.terminal.devices;


/**
 * @author jojesh
 * 
 */
public abstract class PosPrintingDevice extends PosIODevice {

	
	private int mNoCols = 40;
	private int mLeftMargin = 0;


	public abstract void newLine() throws Exception;

	public void write(String line) throws Exception {
		line = ((mLeftMargin > 0) ? String.format("%" + mLeftMargin + "s", "")
				+ line : line);
		write(line.getBytes());
	}

	public void writeCentered(String line) throws Exception {
		final int noSpaces = ((mNoCols - mLeftMargin) / 2 - line.length() / 2);
		final String message = ((noSpaces > 0) ? String.format("%"
				+ ((mNoCols - mLeftMargin) / 2 - line.length() / 2) + "s", "")
				+ line : line);
		write(message);
	}

	/**
	 * @return the mNoCols
	 */
	public int getNoCols() {
		return mNoCols;
	}

	/**
	 * @param NoCols
	 *            to set
	 */
	public void setColumns(int noCols) {
		this.mNoCols = noCols;
	}

	/**
	 * @return the mLeftMargin
	 */
	public int getLeftMargin() {
		return mLeftMargin;
	}

	/**
	 * @param LeftMargin
	 *            to set
	 */
	public void setLeftMargin(int leftMargin) {
		this.mLeftMargin = leftMargin;
	}

}
