/**
 * 
 */
package com.redv.blogmover;


/**
 * Set reader's properties while reader has not setted yet.
 * 
 * @author sutra
 * 
 */
public class ReaderNotSettedException extends BlogMoverException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8380396217481651497L;

	/**
	 * 
	 */
	public ReaderNotSettedException() {
		super();
	}

	/**
	 * @param message
	 * @param cause
	 */
	public ReaderNotSettedException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 */
	public ReaderNotSettedException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public ReaderNotSettedException(Throwable cause) {
		super(cause);
	}

}
