/**
 *  Created on 2006-6-24 13:29:12
 */
package com.redv.blogmover.bsps.space;

import java.io.IOException;
import java.io.Serializable;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.cyberneko.html.parsers.DOMParser;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.redv.blogmover.WebLog;
import com.redv.blogmover.impl.WebLogImpl;
import com.redv.blogmover.util.DomNodeUtils;

/**
 * @author Joe
 * @version 1.0
 * 
 */
class AnonymousSpaceReaderUtil implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final Log log = LogFactory
			.getLog(AnonymousSpaceReaderUtil.class);

	/**
	 * 
	 */
	AnonymousSpaceReaderUtil() {
		super();
	}

	/**
	 * 查找页面的下一页链接的 URL。
	 * 
	 * @param doc
	 * @return
	 */
	static String findNextUrl(Document doc) {
		NodeList nl = doc.getElementsByTagName("img");
		String url = null;
		for (int i = 0; i < nl.getLength(); i++) {
			Node n = nl.item(i);
			if (n.getAttributes().getNamedItem("src").getNodeValue().endsWith(
					"/Web/images/arrow_next.gif")) {
				url = n.getParentNode().getAttributes().getNamedItem("href")
						.getNodeValue();
			} else if (n.getAttributes().getNamedItem("src").getNodeValue()
					.endsWith("/Web/images/arrow_next_d.gif")) {
				url = null;
			}
		}
		log.debug(url);
		return url;
	}

	/**
	 * 读取指定 URL 的内容。
	 * 
	 * @param startUrl
	 * @return
	 */
	Document getDoc(String startUrl) {
		DOMParser parser = new DOMParser();
		try {
			parser.parse(startUrl);
		} catch (SAXException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		Document doc = parser.getDocument();
		return doc;
	}

	/**
	 * 从 URL 中得到 entry 的 ID。
	 * 
	 * @param entryUrl
	 * @return 类似 cns!23DC51B41A685911!160 的字符串。
	 */
	String findId(String entryUrl) {
		int i = entryUrl.indexOf("/blog/cns!") + 6;
		int j = entryUrl.indexOf(".entry", i);
		return entryUrl.substring(i, j);
	}

	/**
	 * 读取一篇 WebLog。
	 * 
	 * @param entryUrl
	 * @return
	 */
	WebLog readEntry(String entryUrl) {
		String id = findId(entryUrl);
		Document doc = getDoc(entryUrl);
		WebLog webLog = new WebLogImpl();
		webLog.setUrl(entryUrl);
		webLog.setTitle(DomNodeUtils.getTextContent(doc.getElementById("subj"
				+ id)));
		Node bodyNode = doc.getElementById("msg" + id);
		if (bodyNode.getParentNode().getNextSibling() != null) {
			Node attrNode = bodyNode.getParentNode().getNextSibling()
					.getFirstChild();
			String html = new HtmlUtil().toHtml(bodyNode.getChildNodes())
					+ "<hr />"
					+ new HtmlUtil().toHtml(attrNode.getChildNodes());
			webLog.setBody(html);
		} else {
			log.warn("TODO ...");
		}
		webLog.setPublishedDate(findPublishedDate(id, doc));
		log.debug("Title=" + webLog.getTitle() + ", PublishedDate="
				+ webLog.getPublishedDate() + " read.");
		return webLog;
	}

	/**
	 * 从 Document 中查找发布日期。
	 * 
	 * @param doc
	 * @return
	 */
	Date findPublishedDate(String entryId, Document doc) {
		Date date = null;
		Node node = doc.getElementById("LastMDate" + entryId);
		if (node == null) {
			date = findPublishedDate1(entryId, doc, node);
		} else {
			date = findPublishedDate2(entryId, doc, node);
		}
		return date;
	}

	/**
	 * <xmp>
	 * <td valign="top" nowrap>2006/6/18 3:34:50</td>
	 * </xmp>
	 * 
	 * @param doc
	 * @return
	 */
	private Date findPublishedDate1(String entryId, Document doc, Node node) {
		Date date = null;
		String dateString = findPublishedDateString1(entryId, doc, node);
		date = parseDate(dateString);
		return date;
	}

	/**
	 * <xmp>
	 * <td valign=top nowrap>15:42</td>
	 * </xmp>
	 * 
	 * @param entryId
	 * @param doc
	 * @param node
	 * @return
	 */
	private String findPublishedDateString1(String entryId, Document doc,
			Node node) {
		String ret = null;
		NodeList nl = doc.getElementsByTagName("td");
		for (int i = 0; i < nl.getLength(); i++) {
			Node n = nl.item(i);
			NamedNodeMap nnm = n.getAttributes();
			Node n1 = nnm.getNamedItem("valign");
			Node n2 = nnm.getNamedItem("nowrap");
			if (n1 != null && n2 != null && n1.getNodeValue().equals("top")
					&& n2.getNodeValue() == "") {
				// 6/18/2006 3:34:50 AM
				ret = DomNodeUtils.getTextContent(n);
			}
		}
		return ret;
	}

	/**
	 * <xmp><span class='bold' id='LastMDatecns!23DC51B41A685911!160'>4月14日</span></xmp>
	 * 
	 * @param doc
	 * @return
	 */
	private Date findPublishedDate2(String entryId, Document doc, Node node) {
		Date date = null;
		String dateString = DomNodeUtils.getTextContent(node);
		log.debug(dateString);
		String timeString = findPublishedDateString1(entryId, doc, node);
		log.debug(timeString);

		date = parseDate(dateString + " " + timeString);
		return date;
	}

	/**
	 * 将字符串形式的日期解析成 Date 对象。
	 * 
	 * @param dateString
	 * @return
	 */
	private Date parseDate(String dateString) {
		return new DateTimeParser().parse(dateString);
	}

}
