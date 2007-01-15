/**
 * Created on 2006-6-30 13:42:21
 */
package com.redv.blogmover.impl;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.redv.blogmover.Attachment;
import com.redv.blogmover.BlogMoverException;
import com.redv.blogmover.BlogWriter;
import com.redv.blogmover.Comment;
import com.redv.blogmover.Status;
import com.redv.blogmover.WebLog;
import com.redv.blogmover.bsps.BSPIDUtils;
import com.redv.blogmover.logging.BSP;

/**
 * @author Joe
 * @version 1.0
 * @version 2.0
 * 
 */
public abstract class AbstractBlogWriter implements BlogWriter {
	protected Log log = LogFactory.getLog(this.getClass());

	protected BSP bsp;

	protected Status status;

	/**
	 * 
	 */
	public AbstractBlogWriter() {
		super();
		bsp = new BSP();
		bsp.setId(BSPIDUtils.getId(this.getClass()));
		this.status = new StatusImpl();
	}

	public BSP getBsp() {
		return bsp;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.redv.blogremover.BlogWriter#write(java.util.List)
	 */
	@SuppressWarnings("unchecked")
	public void write(List<WebLog> webLogs) throws BlogMoverException {
		this.status.setTotalCount(webLogs.size());
		// 对日志排序。
		Collections.sort(webLogs);

		Map<Attachment, String> attachments = new HashMap<Attachment, String>();

		int i = 0;
		begin();
		try {
			for (WebLog webLog : webLogs) {
				this.status.setCurrentWebLog(webLog);
				if (webLog.getAttachments() != null) {
					for (Attachment att : webLog.getAttachments()) {
						if (attachments.containsKey(att) == false) {
							String url = writeAttachment(att);
							attachments.put(att, url);
						}
					}
				}
				write(webLog, attachments);
				this.status.setCurrentCount(++i);
			}
		} finally {
			end();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.redv.blogremover.BlogWriter#getStatus()
	 */
	public Status getStatus() {
		return status;
	}

	/**
	 * Write a web log entry to the target BSP.
	 * 
	 * @param webLog
	 * @param attachments
	 * @throws BlogMoverException
	 */
	@SuppressWarnings("unchecked")
	private void write(WebLog webLog, Map<Attachment, String> attachments)
			throws BlogMoverException {
		log.debug("开始写入日志：" + webLog.getTitle());
		this.writeBlog(webLog, attachments);
		List<Comment> comments = webLog.getComments();
		if (comments != null) {
			// 对评论排序。
			Collections.sort(comments);
			if (comments != null) {
				for (Iterator<Comment> iter = webLog.getComments().iterator(); iter
						.hasNext();) {
					Comment comment = iter.next();
					this.writeComment(comment);
				}
			}
		}
	}

	/**
	 * 在开始写入前调用。
	 * 
	 * @throws BlogMoverException
	 */
	protected abstract void begin() throws BlogMoverException;

	/**
	 * 在所有的日志都写入结束后调用。
	 * 
	 * @throws BlogMoverException
	 */
	protected abstract void end() throws BlogMoverException;

	/**
	 * 写入单个日志。
	 * 
	 * @param webLog
	 * @param attachments
	 * @throws BlogMoverException
	 */
	protected abstract void writeBlog(WebLog webLog,
			Map<Attachment, String> attachments) throws BlogMoverException;

	/**
	 * 写入一个评论。
	 * 
	 * @param comment
	 * @throws BlogMoverException
	 */
	protected abstract void writeComment(Comment comment)
			throws BlogMoverException;

	/**
	 * 写入一个附件。
	 * 
	 * @param att
	 * @return 写入到新的位置后的 URL。
	 */
	protected abstract String writeAttachment(Attachment att);
}
