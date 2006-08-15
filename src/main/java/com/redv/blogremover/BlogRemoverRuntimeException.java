/**
 *  Created on 2006-7-12 22:25:57
 */
package com.redv.blogremover;

/**
 * @author Joe
 * @version 1.0
 * 
 */
public class BlogRemoverRuntimeException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7804497896683382788L;

	/**
	 * 
	 */
	public BlogRemoverRuntimeException() {
		super();
	}

	/**
	 * @param arg0
	 */
	public BlogRemoverRuntimeException(String arg0) {
		super(arg0);
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public BlogRemoverRuntimeException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	/**
	 * @param arg0
	 */
	public BlogRemoverRuntimeException(Throwable arg0) {
		super(arg0);
	}

}
