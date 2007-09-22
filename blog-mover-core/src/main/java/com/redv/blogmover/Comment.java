package com.redv.blogmover;

import java.io.Serializable;
import java.util.Date;

/**
 * The comment of a web log entry.
 * 
 * @author shutra
 * 
 */
public interface Comment extends Serializable, Comparable<Comment> {

	/**
	 * @return Returns the publishedDate.
	 */
	public Date getPublishedDate();

	/**
	 * @param publishedDate
	 *            The publishedDate to set.
	 */
	public void setPublishedDate(Date publishedDate);

	/**
	 * Get the title.
	 * 
	 * @return Returns the title.
	 */
	String getTitle();

	/**
	 * Set the title.
	 * 
	 * @param title
	 *            The title to set.
	 */
	void setTitle(String title);

	/**
	 * @return Returns the comment content.
	 */
	public abstract String getContent();

	/**
	 * @param comment
	 *            The comment content to set.
	 */
	public abstract void setContent(String content);

	/**
	 * @return Returns the email.
	 */
	public abstract String getEmail();

	/**
	 * @param email
	 *            The email to set.
	 */
	public abstract void setEmail(String email);

	/**
	 * @return Returns the name.
	 */
	public abstract String getName();

	/**
	 * @param name
	 *            The name to set.
	 */
	public abstract void setName(String name);

	/**
	 * @return Returns the url.
	 */
	public abstract String getUrl();

	/**
	 * @param url
	 *            The url to set.
	 */
	public abstract void setUrl(String url);

}