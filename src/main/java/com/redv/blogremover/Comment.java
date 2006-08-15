package com.redv.blogremover;

import java.io.Serializable;
import java.util.Date;

public interface Comment extends Serializable, Comparable {

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
	 * @return Returns the comment.
	 */
	public abstract String getComment();

	/**
	 * @param comment
	 *            The comment to set.
	 */
	public abstract void setComment(String comment);

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