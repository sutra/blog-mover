/**
 * 
 */
package com.redv.blogremover;

import java.util.List;

/**
 * @author Joe
 * @version 1.0
 */
public interface BlogReader extends Statusable {
	/**
	 * 从BSP读取某个用户的所有日志。
	 * 
	 */
	List<WebLog> read() throws BlogRemoverException;

}
