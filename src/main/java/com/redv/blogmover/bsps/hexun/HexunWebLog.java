/**
 *  Created on 2006-7-13 1:41:11
 */
package com.redv.blogmover.bsps.hexun;

import com.redv.blogmover.impl.WebLogImpl;

/**
 * @author Joe
 * @version 1.0
 * 
 */
public class HexunWebLog extends WebLogImpl {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6062821237644115558L;

	private String editUrl;

	private String deleteUrl;

	/**
	 * @return Returns the deleteUrl.
	 */
	public String getDeleteUrl() {
		return deleteUrl;
	}

	/**
	 * @param deleteUrl
	 *            The deleteUrl to set.
	 */
	public void setDeleteUrl(String deleteUrl) {
		this.deleteUrl = deleteUrl;
	}

	/**
	 * @return Returns the editUrl.
	 */
	public String getEditUrl() {
		return editUrl;
	}

	/**
	 * @param editUrl
	 *            The editUrl to set.
	 */
	public void setEditUrl(String editUrl) {
		this.editUrl = editUrl;
	}

}
