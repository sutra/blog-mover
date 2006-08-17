/**
 *  Created on 2006-6-14 21:02:53
 */
package com.redv.blogmover.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.redv.blogmover.Attachment;
import com.redv.blogmover.Comment;
import com.redv.blogmover.WebLog;

/**
 * @author Joe
 * @version 1.0
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

	private List<Comment> comments;

	private List<Attachment> attachments;

	public WebLogImpl() {
		super();
		this.publishedDate = new Date();
		this.comments = new ArrayList<Comment>(1);
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Date getPublishedDate() {
		return publishedDate;
	}

	public void setPublishedDate(Date publishedDate) {
		this.publishedDate = publishedDate;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getExcerpt() {
		return excerpt;
	}

	public void setExcerpt(String excerpt) {
		this.excerpt = excerpt;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String[] getTags() {
		return tags;
	}

	public void setTags(String[] tags) {
		this.tags = tags;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public List<Attachment> getAttachments() {
		return this.attachments;
	}

	public void setAttachments(List<Attachment> attachments) {
		this.attachments = attachments;
	}

	public int compareTo(Object o) {
		if (!(o instanceof WebLog))
			return 0;
		Date publishedDate2 = ((WebLog) o).getPublishedDate();
		return this.publishedDate.compareTo(publishedDate2);
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
					_hashCode = this.getPublishedDate().hashCode()
							^ this.getTitle().hashCode();
					_hashCodeCalc = true;
				}
			}
		}
		return _hashCode;
	}

}
