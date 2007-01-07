/**
 *  Created on 2006-6-14 21:02:53
 */
package com.redv.blogmover;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.redv.blogmover.logging.BSP;

/**
 * The web log entry.
 * 
 * @author Joe
 * @version 1.0
 * @version 2.0
 * 
 */
public interface WebLog extends Serializable, Comparable {

	/**
	 * 获取固定链接。
	 * 
	 * @return
	 */
	String getUrl();

	/**
	 * 设置固定链接。
	 * 
	 * @param url
	 * @return
	 */
	void setUrl(String url);

	/**
	 * 获取发布日期。
	 * 
	 * @return
	 */
	Date getPublishedDate();

	/**
	 * 设置发布日期。
	 * 
	 * @param publishedDate
	 */
	void setPublishedDate(Date publishedDate);

	/**
	 * 获取标题。
	 * 
	 * @return
	 */
	String getTitle();

	/**
	 * 设置标题。
	 * 
	 * @param title
	 */
	void setTitle(String title);

	/**
	 * 获取摘要。
	 * 
	 * @return
	 */
	String getExcerpt();

	/**
	 * 设置摘要。
	 * 
	 * @param excerpt
	 */
	void setExcerpt(String excerpt);

	/**
	 * 获取正文。
	 * 
	 * @return
	 */
	String getBody();

	/**
	 * 设置正文。
	 * 
	 * @param body
	 */
	void setBody(String body);

	/**
	 * 获取评论。
	 * 
	 * @return
	 */
	List<Comment> getComments();

	/**
	 * 设置评论。
	 * 
	 * @param comments
	 */
	void setComments(List<Comment> comments);

	/**
	 * 获取附件。
	 * 
	 * @return
	 */
	List<Attachment> getAttachments();

	/**
	 * 设置附件。
	 * 
	 * @param attachments
	 */
	void setAttachments(List<Attachment> attachments);

	/**
	 * 获取标签。
	 * 
	 * @return
	 */
	String[] getTags();

	/**
	 * 设置标签。
	 * 
	 * @param tags
	 */
	void setTags(String[] tags);

	/**
	 * 获取 Blog Service Provider。
	 * 
	 * @return
	 * @since 2.0
	 */
	BSP getBsp();

	/**
	 * 设置 Blog Service Provider.
	 * 
	 * @param bsp
	 * @since 2.0
	 */
	void setBsp(BSP bsp);
}
