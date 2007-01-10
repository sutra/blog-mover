/**
 * Created on 2006-12-24 下午04:40:19
 */
package com.redv.blogmover.logging.dao;

import java.util.Date;
import java.util.Map;

import com.redv.blogmover.logging.BSP;
import com.redv.blogmover.logging.Moving;
import com.redv.blogmover.logging.MovingEntry;
import com.redv.blogmover.logging.MovingLog;

/**
 * @author shutra
 * 
 */
public interface MovingLogDao {
	/**
	 * Get the BSP by specified id.
	 * 
	 * @param id
	 * @return
	 */
	BSP getBsp(String id);

	/**
	 * Insert a BSP.
	 * 
	 * @param bsp
	 * @return
	 */
	String insertBsp(BSP bsp);

	/**
	 * Get the moving by specified id.
	 * 
	 * @param id
	 * @return
	 */
	Moving getMoving(String id);

	/**
	 * Insert a moving.
	 * 
	 * @param moving
	 * @return
	 */
	String insertMoving(Moving moving);

	/**
	 * Get the moving entry by specified id.
	 * 
	 * @param id
	 * @return
	 */
	MovingEntry getMovingEntry(String id);

	/**
	 * Insert a moving entry.
	 * 
	 * @param movingEntry
	 * @return
	 */
	String insertMovingEntry(MovingEntry movingEntry);

	/**
	 * Get the moving log by specified id.
	 * 
	 * @param id
	 * @return
	 */
	MovingLog getMovingLog(String id);

	/**
	 * Insert a moving log.
	 * 
	 * @param movingLog
	 * @return
	 */
	String insertMovingLog(MovingLog movingLog);

	long getFromCount(String id);

	long getFromCount(String id, Date beginDate, Date endDate);

	long getToCount(String id);

	long getToCount(String id, Date beginDate, Date endDate);

	/**
	 * Read the moving from statistic.
	 * 
	 * @return
	 * @deprecated
	 */
	Map<BSP, Long> getFromStatistic();

	/**
	 * Read the moving to statistic.
	 * 
	 * @return
	 * @deprecated
	 */
	Map<BSP, Long> getToStatistic();
}
