/**
 * 
 */
package com.indocosmo.pos.terminal.devices;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import com.indocosmo.pos.common.PosLog;

/**
 * @author jojesh
 * 
 */
public abstract class PosIODevice extends PosDeviceBase {

	private OutputStream mOutputStream;
	private InputStream mInputStream;
	private BufferedReader mInputBufferReader;

	public void close() throws Exception {

		if (mOutputStream != null){
			mOutputStream.close();
			mOutputStream = null;
		}

		if (mInputStream != null){
			mInputStream.close();
			mInputStream = null;
		}
	}

	/**
	 * @param text
	 * @throws Exception
	 */
	protected void write(byte text) throws Exception{ 

		write(new byte[]{text});
	}

	/**
	 * @param bytes
	 * @throws Exception
	 */
	protected void write(byte[] bytes) throws Exception {

		try {
			if (mOutputStream == null)
				throw new Exception("Device not opened");
			mOutputStream.write(bytes);
			mOutputStream.flush();
		} catch (Exception e) {
			PosLog.write(this, "write", e);
			throw e;
		}
	}


	/**
	 * @return
	 * @throws Exception
	 */
	protected String readLine() throws Exception{

		String line=null;

		if (mInputBufferReader.ready()) {

			line=mInputBufferReader.readLine();

		}

		PosLog.debug("PosIODevice:readLine value "+line);
		
		return line;
	}

	/**
	 * @return the OutputStream
	 */
	public OutputStream getOutputStream() {
		return mOutputStream;
	}

	/**
	 * @param the
	 *            OutputStream to set
	 */
	public void setOutputStream(OutputStream outputStream) {
		this.mOutputStream = outputStream;
	}

	/**
	 * @return the inputStream
	 */
	public InputStream getInputStream() {
		return mInputStream;
	}

	/**
	 * @param the
	 *            inputStream to set
	 */
	public void setInputStream(InputStream inputStream) {

		mInputBufferReader=new BufferedReader(new InputStreamReader(inputStream));
		this.mInputStream = inputStream;
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.pos.terminal.devices.IPosDeviceBase#shutdown()
	 */
	@Override
	public void shutdown() throws Exception {
		
		
		close();
		super.shutdown();
	}

	public abstract void open() throws Exception;

}
