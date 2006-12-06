/**
 * Created on 2006-12-6 下午09:54:21
 */
package com.redv.blogmover;

/**
 * 在 Reader/Writer 中，当需要登录的时候，用户名或者密码等凭据无法确认身份时抛出该异常。
 * 
 * @author shutra
 * 
 */
public class LoginFailedException extends BlogMoverException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3011960740386093227L;

	/**
	 * 
	 */
	public LoginFailedException() {
	}

	/**
	 * @param message
	 */
	public LoginFailedException(String message) {
		super(message);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public LoginFailedException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param cause
	 */
	public LoginFailedException(Throwable cause) {
		super(cause);
	}

}
