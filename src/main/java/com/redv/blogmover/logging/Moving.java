/**
 * Created on 2006-12-25 上午12:22:29
 */
package com.redv.blogmover.logging;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

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

	private Set<MovingLog> movingLogs;

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
	public Set<MovingLog> getMovingLogs() {
		return movingLogs;
	}

	/**
	 * @param movingLogs
	 *            the movingLogs to set
	 */
	public void setMovingLogs(Set<MovingLog> movingLogs) {
		this.movingLogs = movingLogs;
		for (MovingLog movingLog : movingLogs) {
			movingLog.setMoving(this);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		int result = 1;
		result = new HashCodeBuilder().append(date).append(id).append(
				movingLogs).toHashCode();
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final Moving other = (Moving) obj;
		EqualsBuilder eb = new EqualsBuilder().append(date, other.date).append(
				id, other.id);
		return eb.isEquals();
	}

}
