/**
 *  Created on 2006-6-18 12:40:31
 */
package com.redv.blogmover;

/**
 * 当系统运行时发生用户异常时，抛出此异常。此异常应该在 UI 上体现给用户。
 * 
 * @author Joe
 * @version 1.0
 * 
 */
public class BlogMoverException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public BlogMoverException() {
		super();
	}

	/**
	 * @param message
	 */
	public BlogMoverException(String message) {
		super(message);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public BlogMoverException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param cause
	 */
	public BlogMoverException(Throwable cause) {
		super(cause);
	}

}
