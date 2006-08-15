/**
 *  Created on 2006-6-18 12:40:31
 */
package com.redv.blogremover;

/**
 * @author Joe
 * @version 1.0
 * 
 */
public class BlogRemoverException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public BlogRemoverException() {
		super();
	}

	/**
	 * @param arg0
	 */
	public BlogRemoverException(String arg0) {
		super(arg0);
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public BlogRemoverException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	/**
	 * @param arg0
	 */
	public BlogRemoverException(Throwable arg0) {
		super(arg0);
	}

}
