/**
 * Created on 2006-12-28 下午11:37:26
 */
package com.redv.blogmover.logging;

import java.io.Serializable;

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

}
