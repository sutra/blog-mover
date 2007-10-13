/**
 * 
 */
package com.redv.blogmover;

import java.util.List;

import com.redv.blogmover.logging.BSP;

/**
 * The writer to write web log entries to blog engine.
 * 
 * @author Joe
 * @version 1.0
 * @version 2.0
 * @version 3.0
 */
public interface BlogWriter extends Statusable, UserInputCheckable {
	/**
	 * 写入日志。
	 * 
	 * @param webLogs
	 */
	void write(List<WebLog> webLogs) throws BlogMoverException;

	/**
	 * 
	 * @return
	 * @since 2.0
	 */
	BSP getBsp();

}
