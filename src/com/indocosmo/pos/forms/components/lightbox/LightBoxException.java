package com.indocosmo.pos.forms.components.lightbox;

/**
 * Exception class for Light box effect jar.
 * 
 */
public class LightBoxException extends Exception {
	private static final long serialVersionUID = 3523762473012640617L;
	/**
	 * 
	 */
	public LightBoxException() {
	}
	/**
	 * @param message
	 */
	public LightBoxException(String message) {
		super(message);
	}
	/**
	 * @param throwable
	 */
	public LightBoxException(Throwable throwable) {
		super(throwable);
	}
	/**
	 * @param message
	 * @param throwable
	 */
	public LightBoxException(String message, Throwable throwable) {
		super(message, throwable);
	}
}
