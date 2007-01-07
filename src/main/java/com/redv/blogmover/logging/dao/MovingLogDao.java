/**
 * Created on 2006-12-24 下午04:40:19
 */
package com.redv.blogmover.logging.dao;

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
	BSP getBsp(String id);

	Moving getMoving(String id);

	MovingEntry getMovingEntry(String id);

	MovingLog getMovingLog(String id);

	String insertMoving(Moving moving);

	String insertBsp(BSP bsp);

	/**
	 * Read the moving from statistic.
	 * 
	 * @return
	 */
	Map<BSP, Long> getFromStatistic();

	/**
	 * Read the moving to statistic.
	 * 
	 * @return
	 */
	Map<BSP, Long> getToStatistic();
}
