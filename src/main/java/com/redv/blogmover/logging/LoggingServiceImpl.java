/**
 * 
 */
package com.redv.blogmover.logging;

import java.util.List;

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
		this.movingLogDao.insertBsp(toBsp);

		Moving moving = new Moving();
		this.movingLogDao.insertMoving(moving);

		for (WebLog webLog : webLogs) {
			MovingEntry movingEntry = new MovingEntry();
			if (webLog.getBsp() != null) {
				this.movingLogDao.insertBsp(webLog.getBsp());
			}
			movingEntry.setBsp(webLog.getBsp());
			movingEntry.setPermalink(webLog.getUrl());
			movingEntry.setTitle(webLog.getTitle());
			this.movingLogDao.insertMovingEntry(movingEntry);

			MovingLog movingLog = new MovingLog();
			movingLog.setMoving(moving);
			movingLog.setFrom(movingEntry);
			movingLog.setToBsp(toBsp);
			this.movingLogDao.insertMovingLog(movingLog);
		}
	}

}
