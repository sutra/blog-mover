/**
 * 
 */
package com.redv.blogmover.logging;

import java.util.Date;
import java.util.List;

import com.redv.blogmover.WebLog;

/**
 * Logging Service.
 * <p>
 * A business layer for logging.
 * </p>
 * 
 * @author shutrazh
 * 
 */
public interface LoggingService {
	/**
	 * Add a log.
	 * 
	 * @param webLogs
	 * @param toBsp
	 */
	void log(List<WebLog> webLogs, BSP toBsp);

	/**
	 * Retrive all BSPs.
	 * 
	 * @return
	 */
	List<BSP> getBsps();

	/**
	 * Get a bsp's moving times that be moved out.
	 * 
	 * @param bspId
	 * @return
	 */
	long getMovedOutCount(String bspId);

	/**
	 * Get a bsp's moving times that be moved out between beginDate and endDate.
	 * 
	 * @param bspId
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	long getMovedOutCount(String bspId, Date beginDate, Date endDate);

	/**
	 * Get a bsp's moving times that be moved in.
	 * 
	 * @param bspId
	 * @return
	 */
	long getMovedInCount(String bspId);

	/**
	 * Get a bsp's moving times that be moved in between beginDate and endDate.
	 * 
	 * @param bspId
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	long getMovedInCount(String bspId, Date beginDate, Date endDate);
}
