/**
 *  Created on 2006-6-18 12:42:25
 */
package com.redv.blogmover.bsps.space;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.redv.blogmover.BlogReader;
import com.redv.blogmover.BlogMoverException;
import com.redv.blogmover.Status;
import com.redv.blogmover.WebLog;
import com.redv.blogmover.impl.StatusImpl;
import com.redv.blogmover.logging.BSP;

/**
 * 微软提供的 Space 服务，匿名读取器。
 * <p>
 * 从起始页 http://[YOUR-SPACE-NAME].spaces.msn.com/blog/ 读取所有的日志。
 * </p>
 * 
 * @author Joe
 * @version 1.0
 * 
 */
public class AnonymousSpaceReader implements BlogReader {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final Log log = LogFactory
			.getLog(AnonymousSpaceReader.class);

	private static final AnonymousSpaceReaderUtil readerUtil = new AnonymousSpaceReaderUtil();

	private BSP bsp;

	private Status status;

	private String startUrl = "http://zhoushuqun.spaces.msn.com/blog/";

	private int currentCount;

	/**
	 * 空间名。
	 */
	private String spaceName;

	/**
	 * 
	 */
	public AnonymousSpaceReader() {
		super();
		this.bsp = new BSP();
		this.bsp.setId(this.getClass().getName());
		this.bsp.setName("Live Space");
		this.bsp.setServerURL("http://spaces.live.com");

		this.status = new StatusImpl();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.redv.blogmover.BlogReader#getBsp()
	 */
	public BSP getBsp() {
		return bsp;
	}

	/**
	 * 设置空间名。
	 * 
	 * @param spaceName
	 *            空间名。当你访问你的空间时你一般输入http://[YOUR-SPACE-NAME].spaces.msn.com，其中[YOUR-SPACE-NAME]就是空间名。
	 */
	public void setSpaceName(String spaceName) {
		log.debug("setSpaceName called. spaceName=" + spaceName);
		this.spaceName = spaceName;
		this.startUrl = "http://" + this.spaceName + ".spaces.msn.com/blog/";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.redv.blogremover.BlogReader#read(java.util.List)
	 */
	public List<WebLog> read() throws BlogMoverException {
		List<WebLog> webLogs = new ArrayList<WebLog>();
		this.currentCount = 0;
		String url = startUrl;
		log.debug("read() called. startUrl=" + url);
		do {
			Document doc = readerUtil.getDoc(url);
			// Check is "Space Not Available"?
			if (spaceNotAvailable(doc)) {
				throw new BlogMoverException(
						"Space Not Available. 你的 Space 不允许匿名访问。如果你要使用本 Reader，你必须打开你的 Space 的匿名访问权限。");
			}
			findEntries(doc, webLogs);
			url = AnonymousSpaceReaderUtil.findNextUrl(doc);
		} while (url != null);
		return webLogs;
	}

	/**
	 * 获取指定列表的实体列表。
	 * 
	 * @param doc
	 */
	private void findEntries(Document doc, List<WebLog> webLogs) {
		// 固定链接 URL 模式。
		Pattern entryUrlPattern;
		// http://zhoushuqun.spaces.msn.com/blog/cns!2B070A76FD6627CE!709.entry?_c11_blogpart_blogpart=blogview&_c=blogpart#permalink
		entryUrlPattern = Pattern
				.compile(startUrl
						+ "cns![0-9A-Z]*![0-9]*.entry\\?_c11_blogpart_blogpart=blogview&_c=blogpart#permalink");
		// Matcher m =
		// entryUrlPattern.matcher("http://zhoushuqun.spaces.msn.com/blog/cns!2B070A76FD6627CE!709.entry?_c11_blogpart_blogpart=blogview&_c=blogpart#permalink");
		// log.debug(m.matches());

		NodeList nl = doc.getElementsByTagName("a");
		for (int i = 0; i < nl.getLength(); i++) {
			Node n = nl.item(i);
			Node idNode = n.getAttributes().getNamedItem("href");
			if (idNode != null
					&& entryUrlPattern.matcher(idNode.getNodeValue()).matches()) {
				String entryUrl = idNode.getNodeValue();
				log.debug(entryUrl);
				WebLog webLog = readerUtil.readEntry(entryUrl);
				this.status.setCurrentCount(++currentCount);
				this.status.setCurrentWebLog(webLog);
				webLogs.add(webLog);
			}
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.redv.blogmover.BlogReader#getStatus()
	 */
	public Status getStatus() {
		return this.status;
	}

	private boolean spaceNotAvailable(Document doc) {
		NodeList tds = doc.getElementsByTagName("td");
		for (int i = 0; i < tds.getLength(); i++) {
			Node n = tds.item(i).getFirstChild();
			if (n != null) {
				String v = n.getNodeValue();
				if (log.isDebugEnabled()) {
					log.debug("nodeValue: " + v);
				}
				if (StringUtils.equals(v, "Space Not Available")) {
					return true;
				}
			}
		}
		return false;
	}
}
