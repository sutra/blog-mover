/**
 * Created on 2006-12-20 上午12:03:37
 */
package com.redv.blogmover.logging;

import java.io.Serializable;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * The model of Blog Service Provider.
 * 
 * @author shutra
 * 
 */
public class BSP implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2288389023380670067L;

	private String id;

	private String name;

	private String serverURL;

	private String description;

	private Set<MovingEntry> movingEntries;

	/**
	 * @return the description
	 * @deprecated
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 * @deprecated
	 */
	public void setDescription(String description) {
		this.description = description;
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
	 * @return the name
	 * @deprecated
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 * @deprecated
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the serverURL
	 * @deprecated
	 */
	public String getServerURL() {
		return serverURL;
	}

	/**
	 * @param serverURL
	 *            the serverURL to set
	 * @deprecated
	 */
	public void setServerURL(String serverURL) {
		this.serverURL = serverURL;
	}

	/**
	 * @return the movingEntries
	 */
	public Set<MovingEntry> getMovingEntries() {
		return movingEntries;
	}

	/**
	 * @param movingEntries
	 *            the movingEntries to set
	 */
	public void setMovingEntries(Set<MovingEntry> movingEntries) {
		this.movingEntries = movingEntries;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(id).toHashCode();
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
		final BSP other = (BSP) obj;
		return new EqualsBuilder().append(id, other.id).isEquals();
	}

}
