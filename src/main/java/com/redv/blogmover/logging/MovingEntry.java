/**
 * Created on 2006-12-28 下午11:37:26
 */
package com.redv.blogmover.logging;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.redv.blogmover.WebLog;

/**
 * @author shutra
 * 
 */
public class MovingEntry implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8735393062875086004L;

	private String id;

	private BSP bsp;

	private String permalink;

	private String title;

	public MovingEntry() {

	}

	public MovingEntry(WebLog webLog) {
		this.permalink = webLog.getUrl();
		this.title = webLog.getTitle();
		this.bsp = webLog.getBsp();
	}

	/**
	 * @return the bsp
	 */
	public BSP getBsp() {
		return bsp;
	}

	/**
	 * @param bsp
	 *            the bsp to set
	 */
	public void setBsp(BSP bsp) {
		this.bsp = bsp;
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
	 * @return the permalink
	 */
	public String getPermalink() {
		return permalink;
	}

	/**
	 * @param permalink
	 *            the permalink to set
	 */
	public void setPermalink(String permalink) {
		this.permalink = permalink;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title
	 *            the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(bsp).append(id).append(permalink)
				.append(title).toHashCode();
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
		final MovingEntry other = (MovingEntry) obj;
		return new EqualsBuilder().append(bsp, other.bsp).append(id, other.id)
				.append(permalink, other.permalink).append(title, other.title)
				.isEquals();
	}

}
