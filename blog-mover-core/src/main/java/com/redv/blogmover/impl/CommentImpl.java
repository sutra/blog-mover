/**
 * 
 */
package com.redv.blogmover.impl;

import java.util.Date;

import com.redv.blogmover.Comment;

/**
 * @author shqzhou
 * 
 */
public class CommentImpl implements Comment {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4426503564573234847L;

	private Date publishedDate;

	private String name;

	private String email;

	private String url;

	private String title;

	private String comment;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.redv.blogremover.Comment#getPublishedDate()
	 */
	public Date getPublishedDate() {
		return publishedDate;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.redv.blogremover.Comment#setPublishedDate(java.util.Date)
	 */
	public void setPublishedDate(Date publishedDate) {
		this.publishedDate = publishedDate;
	}

	/*
	 * （非 Javadoc）
	 * 
	 * @see com.redv.blogmover.Comment#getTitle()
	 */
	public String getTitle() {
		return title;
	}

	/*
	 * （非 Javadoc）
	 * 
	 * @see com.redv.blogmover.Comment#setTitle(java.lang.String)
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.redv.blogremover.Comment#getComment()
	 */
	public String getComment() {
		return comment;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.redv.blogremover.Comment#setComment(java.lang.String)
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.redv.blogremover.Comment#getEmail()
	 */
	public String getEmail() {
		return email;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.redv.blogremover.Comment#setEmail(java.lang.String)
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.redv.blogremover.Comment#getName()
	 */
	public String getName() {
		return name;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.redv.blogremover.Comment#setName(java.lang.String)
	 */
	public void setName(String name) {
		this.name = name;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.redv.blogremover.Comment#getUrl()
	 */
	public String getUrl() {
		return url;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.redv.blogremover.Comment#setUrl(java.lang.String)
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	public int compareTo(Comment o) {
		Date publishedDate2 = o.getPublishedDate();
		return this.publishedDate.compareTo(publishedDate2);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		return obj.hashCode() == this.hashCode();
	}

	private int _hashCode = 0;

	private boolean _hashCodeCalc = false;

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		if (!_hashCodeCalc) {
			synchronized (this) {
				if (!_hashCodeCalc) {
					_hashCode = (publishedDate == null ? 0 : publishedDate
							.hashCode())
							^ (name == null ? 0 : name.hashCode())
							^ (url == null ? 0 : url.hashCode())
							^ (comment == null ? 0 : comment.hashCode());
					_hashCodeCalc = true;
				}
			}
		}
		return _hashCode;
	}
}
