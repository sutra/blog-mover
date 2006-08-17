/**
 * 
 */
package com.redv.blogmover;

import java.util.List;

/**
 * @author Joe
 * @version 1.0
 */
public interface BlogWriter extends Statusable {
	/**
	 * 写入日志。
	 * 
	 * @param webLogs
	 */
	void write(List<WebLog> webLogs) throws BlogRemoverException;

}
