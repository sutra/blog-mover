/**
 * 
 */
package com.redv.blogmover.bsps.sohu;

import com.redv.blogmover.impl.WebLogImpl;

/**
 * @author shqzhou
 * 
 */
public class SohuWebLog extends WebLogImpl {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7970215070168154927L;

	private String dateString;

	private String modifyUrl;

	private String deleteUrl;

	private String keywords;

	/**
	 * @return Returns the dateString.
	 */
	public String getDateString() {
		return dateString;
	}

	/**
	 * @param dateString
	 *            The dateString to set.
	 */
	public void setDateString(String dateString) {
		this.dateString = dateString;
	}

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
	 * @return Returns the modifyUrl.
	 */
	public String getModifyUrl() {
		return modifyUrl;
	}

	/**
	 * @param modifyUrl
	 *            The modifyUrl to set.
	 */
	public void setModifyUrl(String modifyUrl) {
		this.modifyUrl = modifyUrl;
	}

	/**
	 * @return Returns the keywords.
	 */
	public String getKeywords() {
		return keywords;
	}

	/**
	 * @param keywords
	 *            The keywords to set.
	 */
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

}
