/**
 * 
 */
package com.redv.blogmover.logging;

import java.util.List;

import com.redv.blogmover.WebLog;

/**
 * @author shutrazh
 * 
 */
public interface LoggingService {
	void log(List<WebLog> webLogs, BSP toBsp);
}
