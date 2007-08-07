/**
 * 
 */
package com.redv.blogmover;

/**
 * Set writer's properties while writer has not setted yet.
 * 
 * @author sutra
 * 
 */
public class WriterNotSettedException extends BlogMoverException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7782528359379289463L;

	/**
	 * 
	 */
	public WriterNotSettedException() {
		super();
	}

	/**
	 * @param message
	 * @param cause
	 */
	public WriterNotSettedException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 */
	public WriterNotSettedException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public WriterNotSettedException(Throwable cause) {
		super(cause);
	}

}
