/**
 * Created on 2007-4-21 下午07:56:30
 */
package com.redv.blogmover.bsps.com.live.spaces;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.xml.transform.TransformerException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.redv.blogmover.BlogMoverRuntimeException;
import com.redv.blogmover.WebLog;
import com.redv.blogmover.impl.WebLogImpl;
import com.redv.blogmover.util.DomNodeUtils;

/**
 * Windows Live Spaces' web log entry parser.
 * <p>
 * Parse the html, to find a web log entry.
 * </p>
 * 
 * @author Sutra Zhou
 * 
 */
public class EntryParser {
	private final Log log = LogFactory.getLog(EntryParser.class);

	private final SimpleDateFormat publishedDateFormat = new SimpleDateFormat(
			"yyyy/M/dd h:mm:ss");

	private Document document;

	private WebLog webLog;

	private String id;

	/**
	 * @param document
	 *            the document to set
	 */
	public void setDocument(Document document) {
		this.document = document;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the webLog
	 */
	public WebLog getWebLog() {
		return webLog;
	}

	public void parse() {
		if (this.id == null) {
			throw new BlogMoverRuntimeException("id is null.");
		}
		log.debug("Parsing: " + this.id);
		webLog = new WebLogImpl();
		webLog.setTitle(document.getElementById("subj" + id).getFirstChild()
				.getNodeValue());
		Node bodyNode = document.getElementById("msg" + id);
		try {
			String s = DomNodeUtils.getXmlAsString(bodyNode);

			// 去除外层的 div 标签的包围。
			int start = s.indexOf('>');
			int end = s.lastIndexOf('<');
			s = s.substring(start + 1, end);

			webLog.setBody(s);
		} catch (TransformerException e) {
			throw new BlogMoverRuntimeException(e);
		}

		NodeList divs = document.getElementsByTagName("div");
		for (int i = 0; i < divs.getLength(); i++) {
			Element div = (Element) divs.item(i);
			if ("footerLinks".equals(div.getAttribute("class"))) {
				String s = div.getFirstChild().getNodeValue();
				log.debug("date string: " + s);
				try {
					webLog.setPublishedDate(publishedDateFormat.parse(s));
				} catch (ParseException e) {
					throw new BlogMoverRuntimeException(e);
				}
				break;
			}
		}
	}

	public static String parseId(String entryUrl) {
		int lastSlash = entryUrl.lastIndexOf('/');
		int lastDot = entryUrl.lastIndexOf('.');
		return entryUrl.substring(lastSlash + 1, lastDot);
	}
}
