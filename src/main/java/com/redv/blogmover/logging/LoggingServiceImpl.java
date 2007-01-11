/**
 * 
 */
package com.redv.blogmover.logging;

import java.util.Date;
import java.util.List;

import org.springframework.dao.DataAccessException;

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

		try {
			this.movingLogDao.insertBsp(toBsp);
		} catch (DataAccessException ex) {

		}

		for (WebLog webLog : webLogs) {
			MovingEntry movingEntry = new MovingEntry();
			if (webLog.getBsp() != null) {
				try {
					this.movingLogDao.insertBsp(webLog.getBsp());
				} catch (DataAccessException ex) {

				}
			}
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
	 * @see com.redv.blogmover.logging.LoggingService#getFromCount(java.lang.String)
	 */
	public long getFromCount(String bspId) {
		return this.movingLogDao.getFromCount(bspId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.redv.blogmover.logging.LoggingService#getFromCount(java.lang.String,
	 *      java.util.Date, java.util.Date)
	 */
	public long getFromCount(String bspId, Date beginDate, Date endDate) {
		return this.movingLogDao.getFromCount(bspId, beginDate, endDate);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.redv.blogmover.logging.LoggingService#getToCount(java.lang.String)
	 */
	public long getToCount(String bspId) {
		return this.movingLogDao.getToCount(bspId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.redv.blogmover.logging.LoggingService#getToCount(java.lang.String,
	 *      java.util.Date, java.util.Date)
	 */
	public long getToCount(String bspId, Date beginDate, Date endDate) {
		return this.movingLogDao.getToCount(bspId, beginDate, endDate);
	}

}