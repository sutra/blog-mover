/**
 * 
 */
package com.redv.blogmover;

import java.util.List;

import com.redv.blogmover.logging.BSP;

/**
 * The reader to read web log entries from a blog engine.
 * 
 * @author Joe
 * @version 1.0
 * @version 2.0
 */
public interface BlogReader extends Statusable {
	/**
	 * 从BSP读取某个用户的所有日志。
	 * 
	 */
	List<WebLog> read() throws BlogMoverException;

	/**
	 * 
	 * @return Blog Service Provider.
	 * @since 2.0
	 */
	BSP getBsp();

}
