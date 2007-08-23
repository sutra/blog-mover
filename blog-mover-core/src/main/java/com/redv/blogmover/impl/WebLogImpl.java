/**
 *  Created on 2006-6-14 21:02:53
 */
package com.redv.blogmover.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.redv.blogmover.Attachment;
import com.redv.blogmover.Category;
import com.redv.blogmover.Comment;
import com.redv.blogmover.WebLog;
import com.redv.blogmover.logging.BSP;

/**
 * @author Joe
 * @version 1.0
 * @version 2.0
 * 
 */
public class WebLogImpl implements WebLog {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4086193699357367723L;

	private String url;

	private Date publishedDate;

	private String title;

	private String excerpt;

	private String body;

	private String[] tags;

	private Category[] categories;

	private List<Comment> comments;

	private List<Attachment> attachments;

	private BSP bsp;

	/**
	 * 
	 */
	public WebLogImpl() {
		super();
		this.publishedDate = new Date();
		this.comments = new ArrayList<Comment>(1);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.redv.blogmover.WebLog#getUrl()
	 */
	public String getUrl() {
		return url;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.redv.blogmover.WebLog#setUrl(java.lang.String)
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.redv.blogmover.WebLog#getPublishedDate()
	 */
	public Date getPublishedDate() {
		return publishedDate;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.redv.blogmover.WebLog#setPublishedDate(java.util.Date)
	 */
	public void setPublishedDate(Date publishedDate) {
		this.publishedDate = publishedDate;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.redv.blogmover.WebLog#getTitle()
	 */
	public String getTitle() {
		return title;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.redv.blogmover.WebLog#setTitle(java.lang.String)
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.redv.blogmover.WebLog#getExcerpt()
	 */
	public String getExcerpt() {
		return excerpt;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.redv.blogmover.WebLog#setExcerpt(java.lang.String)
	 */
	public void setExcerpt(String excerpt) {
		this.excerpt = excerpt;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.redv.blogmover.WebLog#getBody()
	 */
	public String getBody() {
		return body;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.redv.blogmover.WebLog#setBody(java.lang.String)
	 */
	public void setBody(String body) {
		this.body = body;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.redv.blogmover.WebLog#getTags()
	 */
	public String[] getTags() {
		return tags;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.redv.blogmover.WebLog#setTags(java.lang.String[])
	 */
	public void setTags(String[] tags) {
		this.tags = tags;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.redv.blogmover.WebLog#getComments()
	 */
	public List<Comment> getComments() {
		return comments;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.redv.blogmover.WebLog#setComments(java.util.List)
	 */
	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.redv.blogmover.WebLog#getAttachments()
	 */
	public List<Attachment> getAttachments() {
		return this.attachments;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.redv.blogmover.WebLog#setAttachments(java.util.List)
	 */
	public void setAttachments(List<Attachment> attachments) {
		this.attachments = attachments;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.redv.blogmover.WebLog#getBsp()
	 */
	public BSP getBsp() {
		return bsp;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.redv.blogmover.WebLog#setBsp(com.redv.blogmover.logging.BSP)
	 */
	public void setBsp(BSP bsp) {
		this.bsp = bsp;
	}

	public int compareTo(WebLog another) {
		Date publishedDate2 = another.getPublishedDate();

		if (this.publishedDate == null && publishedDate2 == null) {
			return 0;
		} else if (this.publishedDate == null) {
			return 1;
		} else if (publishedDate2 == null) {
			return -1;
		} else {
			return this.publishedDate.compareTo(publishedDate2);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object o) {
		if (o instanceof WebLog) {
			WebLog obj = (WebLog) o;
			if (this.getTitle() == null && obj.getTitle() == null) {
				if (this.getPublishedDate() == null
						&& obj.getPublishedDate() == null) {
					return true;
				} else {
					return false;
				}
			}
			return this.getTitle().equals(obj.getTitle())
					&& this.getPublishedDate().equals(obj.getPublishedDate());
		}
		return false;
	}

	private int _hashCode;

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
					_hashCode = (this.getPublishedDate() == null ? new Date()
							: this.getPublishedDate()).hashCode()
							^ this.getTitle().hashCode();
					_hashCodeCalc = true;
				}
			}
		}
		return _hashCode;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.redv.blogmover.WebLog#getCategories()
	 */
	public Category[] getCategories() {
		return categories;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.redv.blogmover.WebLog#setCategories(com.redv.blogmover.Category[])
	 */
	public void setCategories(Category[] categories) {
		this.categories = categories;
	}

}
