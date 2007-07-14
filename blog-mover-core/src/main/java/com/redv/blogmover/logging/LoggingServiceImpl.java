/**
 * 
 */
package com.redv.blogmover.logging;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.redv.blogmover.WebLog;
import com.redv.blogmover.logging.dao.MovingLogDao;

/**
 * @author shutrazh
 * 
 */
public class LoggingServiceImpl implements LoggingService {
	private MovingLogDao movingLogDao;

	/**
	 * @param movingLogDao
	 *            the movingLogDao to set
	 */
	public void setMovingLogDao(MovingLogDao movingLogDao) {
		this.movingLogDao = movingLogDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.redv.blogmover.logging.LoggingService#log(java.util.List,
	 *      com.redv.blogmover.logging.BSP)
	 */
	public void log(List<WebLog> webLogs, BSP toBsp) {
		if (webLogs == null) {
			throw new IllegalArgumentException("webLogs can't be null.");
		}
		if (toBsp == null) {
			throw new IllegalArgumentException("toBsp can't be null.");
		}

		Moving moving = new Moving();
		moving.setDate(new Date());
		this.movingLogDao.insertMoving(moving);

		// Add all bsps to a set.
		Set<BSP> bsps = new HashSet<BSP>();

		bsps.add(toBsp);

		for (WebLog webLog : webLogs) {
			BSP bsp = webLog.getBsp();
			if (bsp != null && bsps.contains(bsp) == false) {
				bsps.add(bsp);
			}
		}

		// Insert the bsps in the set into database.
		for (BSP bsp : bsps) {
			if (this.movingLogDao.getBsp(bsp.getId()) == null) {
				this.movingLogDao.insertBsp(bsp);
			}
		}

		for (WebLog webLog : webLogs) {
			MovingEntry movingEntry = new MovingEntry();
			movingEntry.setBsp(webLog.getBsp());
			movingEntry.setPermalink(webLog.getUrl());
			movingEntry.setTitle(webLog.getTitle());
			this.movingLogDao.insertMovingEntry(movingEntry);

			MovingLog movingLog = new MovingLog();
			movingLog.setMoving(moving);
			movingLog.setFromEntry(movingEntry);
			movingLog.setToBsp(toBsp);
			this.movingLogDao.insertMovingLog(movingLog);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.redv.blogmover.logging.LoggingService#getBsps()
	 */
	public List<BSP> getBsps() {
		return this.movingLogDao.getBsps();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.redv.blogmover.logging.LoggingService#getMovedOutCount(java.lang.String)
	 */
	public long getMovedOutCount(String bspId) {
		return this.movingLogDao.getFromCount(bspId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.redv.blogmover.logging.LoggingService#getMovedOutCount(java.lang.String,
	 *      java.util.Date, java.util.Date)
	 */
	public long getMovedOutCount(String bspId, Date beginDate, Date endDate) {
		return this.movingLogDao.getFromCount(bspId, beginDate, endDate);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.redv.blogmover.logging.LoggingService#getMovedInCount(java.lang.String)
	 */
	public long getMovedInCount(String bspId) {
		return this.movingLogDao.getToCount(bspId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.redv.blogmover.logging.LoggingService#getMovedInCount(java.lang.String,
	 *      java.util.Date, java.util.Date)
	 */
	public long getMovedInCount(String bspId, Date beginDate, Date endDate) {
		return this.movingLogDao.getToCount(bspId, beginDate, endDate);
	}

}
