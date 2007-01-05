/**
 * Created on 2006-12-25 上午12:22:29
 */
package com.redv.blogmover.logging;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.redv.blogmover.WebLog;

/**
 * @author shutra
 * 
 */
public class Moving implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8178321320432480296L;

	private String id;

	private Date date;

	private List<MovingLog> movingLogs;

	public Moving() {
		this.date = new Date();
	}

	public Moving(List<MovingLog> movingLogs) {
		this();
		this.movingLogs = movingLogs;
	}

	public Moving(List<WebLog> fromWebLogs, List<WebLog> toWebLogs) {
		this();
		this.movingLogs = new ArrayList<MovingLog>(fromWebLogs.size());
		for (int i = 0; i < fromWebLogs.size(); i++) {
			MovingLog movingLog = new MovingLog();
			movingLog.setMoving(this);
			movingLog.setFrom(new MovingEntry(fromWebLogs.get(i)));
			movingLog.setTo(new MovingEntry(toWebLogs.get(i)));
			movingLogs.add(movingLog);
		}
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * @param date
	 *            the date to set
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	/**
	 * @return the movingLogs
	 */
	public List<MovingLog> getMovingLogs() {
		return movingLogs;
	}

	/**
	 * @param movingLogs
	 *            the movingLogs to set
	 */
	public void setMovingLogs(List<MovingLog> movingLogs) {
		this.movingLogs = movingLogs;
	}

}
