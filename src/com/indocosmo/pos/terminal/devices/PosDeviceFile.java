/**
 * 
 */
package com.indocosmo.pos.terminal.devices;

import java.io.FileOutputStream;

/**
 * @author jojesh
 *
 */
public class PosDeviceFile extends PosPrintingDevice {

	private String mFileName;
	
	public PosDeviceFile(String fileName){
		mFileName=fileName;
	}
	/* (non-Javadoc)
	 * @see com.indocosmo.pos.terminal.devices.IPosDeviceBase#initialize()
	 */
	@Override
	public boolean initialize() throws Exception {
		// TODO Auto-generated method stub
		return true;
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.pos.terminal.devices.IPosDeviceBase#shutdown()
	 */
	@Override
	public void shutdown() throws Exception {
		close();
	}

	public void open() throws Exception {
		FileOutputStream os=new FileOutputStream(mFileName,false);
		setOutputStream(os);
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.pos.terminal.devices.PosOutputDevice#newLine()
	 */
	@Override
	public void newLine() throws Exception {
		write("\n");
	}


}
