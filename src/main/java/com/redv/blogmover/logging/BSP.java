/**
 * Created on 2006-12-20 上午12:03:37
 */
package com.redv.blogmover.logging;

import java.io.Serializable;

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

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
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
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the serverURL
	 */
	public String getServerURL() {
		return serverURL;
	}

	/**
	 * @param serverURL
	 *            the serverURL to set
	 */
	public void setServerURL(String serverURL) {
		this.serverURL = serverURL;
	}

}
