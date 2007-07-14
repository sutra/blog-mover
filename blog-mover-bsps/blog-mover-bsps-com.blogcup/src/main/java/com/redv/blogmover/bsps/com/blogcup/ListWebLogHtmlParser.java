/**
 * 
 */
package com.redv.blogmover.bsps.com.blogcup;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.transform.TransformerException;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.redv.blogmover.util.DomNodeUtils;

/**
 * @author shutrazh
 * 
 */
class ListWebLogHtmlParser {
	private static final Log log = LogFactory
			.getLog(ListWebLogHtmlParser.class);

	private Document document;

	private int pageSize;

	private int totalCount;

	private int currentPage;

	private int totalPage;

	private List<String> ids;

	private List<String> links;

	/**
	 * @param document
	 *            the document to set
	 */
	public void setDocument(Document document) {
		this.document = document;
	}

	/**
	 * @return the ids
	 */
	public List<String> getIds() {
		return ids;
	}

	/**
	 * @return the links
	 */
	public List<String> getLinks() {
		return links;
	}

	/**
	 * @return the totalCount
	 */
	public int getTotalCount() {
		return totalCount;
	}

	/**
	 * @return the currentPage
	 */
	public int getCurrentPage() {
		return currentPage;
	}

	/**
	 * @return the totalPage
	 */
	public int getTotalPage() {
		return totalPage;
	}

	public void parse() {
		if (log.isDebugEnabled()) {
			try {
				log.debug(DomNodeUtils.getXmlAsString(document));
			} catch (TransformerException e) {
				log.debug(e);
			}
		}
		Element showpage = document.getElementById("showpage");
		String nodeValue = showpage.getFirstChild().getNodeValue();
		log.debug(nodeValue);
		Pattern p = Pattern
				.compile("共([0-9]+)篇BLOG[^0]*([0-9]+)/([0-9]+)[^0]*([0-9]+)篇BLOG[.]*");
		Matcher m = p.matcher(nodeValue);
		if (m.find()) {
			if (log.isDebugEnabled()) {
				log.debug("m.group(1): " + m.group(1));
				log.debug("m.group(2): " + m.group(2));
				log.debug("m.group(3): " + m.group(3));
				log.debug("m.group(3): " + m.group(4));
			}
			this.totalCount = Integer.parseInt(m.group(1));
			this.currentPage = Integer.parseInt(m.group(2));
			this.totalPage = Integer.parseInt(m.group(3));
			this.pageSize = Integer.parseInt(m.group(4));
		}

		this.ids = new ArrayList<String>(pageSize);
		this.links = new ArrayList<String>(pageSize);

		Element list = document.getElementById("list");

		NodeList inputs = list.getElementsByTagName("INPUT");
		log.debug("inputs.getLength(): " + inputs.getLength());
		for (int i = 0; i < inputs.getLength(); i++) {
			Element input = (Element) inputs.item(i);
			if (StringUtils.equals("id", input.getAttribute("name"))) {
				this.ids.add(input.getAttribute("value"));
			}
		}

		NodeList as = list.getElementsByTagName("A");
		log.debug("as.getLength(): " + as.getLength());
		for (int i = 0; i < as.getLength(); i++) {
			Element a = (Element) as.item(i);
			if (StringUtils.equals("在新窗口打开", a.getAttribute("title"))) {
				this.links.add(a.getAttribute("href"));
			}
		}
	}
}
