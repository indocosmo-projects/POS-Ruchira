/**
 * 
 */
package com.indocosmo.pos.common.exception;

/**
 * @author jojesh
 *
 */
public class PosException extends Exception {

	/**
	 * 
	 */
	
	private String mMessage;
	/**
	 * 
	 */
	public PosException() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 */
	public PosException(String message) {
		super(message);
		mMessage=message;
	}

	/**
	 * @param cause
	 */
	public PosException(Throwable cause) {
		super(cause);
		mMessage=cause.getMessage();
	}

	/**
	 * @param message
	 * @param cause
	 */
	public PosException(String message, Throwable cause) {
		super(message, cause);
		if(message.equals(""))
			mMessage=cause.getMessage();
		else
			mMessage=message;
		
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public PosException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		if(message.equals(""))
			mMessage=cause.getMessage();
		else
			mMessage=message;
	}
	
	
	/* (non-Javadoc)
	 * @see java.lang.Throwable#getMessage()
	 */
	@Override
	public String getMessage() {
		return mMessage;
	}

}
