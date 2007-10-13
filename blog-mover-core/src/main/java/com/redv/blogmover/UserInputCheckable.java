/**
 * Created on 2006-6-30 15:05:50
 */
package com.redv.blogmover;

/**
 * 用户输入可检查的。
 * 
 * @author Sutra
 * @version 3.0
 */
public interface UserInputCheckable {

	/**
	 * 检查用户输入的正确性。
	 * 
	 */
	void check() throws BlogMoverException;
}
