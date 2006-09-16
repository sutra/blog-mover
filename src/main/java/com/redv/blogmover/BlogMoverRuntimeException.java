/**
 *  Created on 2006-7-12 22:25:57
 */
package com.redv.blogmover;

/**
 * 系统运行时发生异常时抛出此异常。此异常只做系统日志，而不通过 UI 呈现给用户。
 * 
 * @author Joe
 * @version 1.0
 * 
 */
public class BlogMoverRuntimeException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7804497896683382788L;

	/**
	 * 
	 */
	public BlogMoverRuntimeException() {
		super();
	}

	/**
	 * @param arg0
	 */
	public BlogMoverRuntimeException(String arg0) {
		super(arg0);
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public BlogMoverRuntimeException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	/**
	 * @param arg0
	 */
	public BlogMoverRuntimeException(Throwable arg0) {
		super(arg0);
	}

}
