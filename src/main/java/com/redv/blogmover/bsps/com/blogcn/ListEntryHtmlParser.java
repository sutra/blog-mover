/**
 * 
 */
package com.redv.blogmover.bsps.com.blogcn;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.transform.TransformerException;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.html.HTMLAnchorElement;
import org.w3c.dom.html.HTMLCollection;
import org.w3c.dom.html.HTMLTableCellElement;
import org.w3c.dom.html.HTMLTableElement;
import org.w3c.dom.html.HTMLTableRowElement;

import com.redv.blogmover.util.DomNodeUtils;

/**
 * @author shutrazh
 * 
 */
class ListEntryHtmlParser {
	private static final Log log = LogFactory.getLog(ListEntryHtmlParser.class);

	private static final String NO_ENTRY_PROMPT = "您好，您还未发表过日志";

	private SimpleDateFormat sdf;

	private Document document;

	private int totalEntries;

	private int totalPages;

	private int currentPage;

	private List<Date> publishedDates;

	private List<String> titles;

	private List<String> permalinks;

	private List<String> modifyLinks;

	/**
	 * 
	 */
	public ListEntryHtmlParser() {
		super();
		sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * @param document
	 *            the document to set
	 */
	public void setDocument(Document document) {
		this.document = document;
	}

	/**
	 * @return the currentPage
	 */
	public int getCurrentPage() {
		return currentPage;
	}

	/**
	 * @return the publishedDates
	 */
	public List<Date> getPublishedDates() {
		return publishedDates;
	}

	/**
	 * @return the titles
	 */
	public List<String> getTitles() {
		return titles;
	}

	/**
	 * @return the modifyLinks
	 */
	public List<String> getModifyLinks() {
		return modifyLinks;
	}

	/**
	 * @return the permalinks
	 */
	public List<String> getPermalinks() {
		return permalinks;
	}

	/**
	 * @return the totalEntries
	 */
	public int getTotalEntries() {
		return totalEntries;
	}

	/**
	 * @return the totalPages
	 */
	public int getTotalPages() {
		return totalPages;
	}

	public void parse() {
		log(document);
		this.publishedDates = new ArrayList<Date>();
		this.titles = new ArrayList<String>();
		this.permalinks = new ArrayList<String>();
		this.modifyLinks = new ArrayList<String>();

		if (hasEntries()) {
			parseFenye();
			parseEntries();
		}
	}

	/**
	 * Check if has entris in this blog.
	 * 
	 * @return
	 */
	private boolean hasEntries() {
		String documentXmlString;
		try {
			documentXmlString = DomNodeUtils.getXmlAsString(document);
		} catch (TransformerException e) {
			throw new RuntimeException(e);
		}
		if (StringUtils.contains(documentXmlString, NO_ENTRY_PROMPT)) {
			return false;
		}
		return true;
	}

	private void parseFenye() {
		Element formFenye = document.getElementById("Form2");
		NodeList children = formFenye.getChildNodes();
		Node table = children.item(1);
		children = table.getChildNodes();
		Node tr = children.item(1);
		if (tr instanceof HTMLTableRowElement) {
			Element trElement = (Element) tr;
			NodeList tds = trElement.getElementsByTagName("TD");
			Element td = (Element) tds.item(0);
			children = td.getChildNodes();
			Node node = children.item(3);
			String str = node.getFirstChild().getNodeValue();
			Pattern p = Pattern.compile("共([0-9]+)条记录 第([0-9]+)/([0-9]+) 页");
			Matcher m = p.matcher(str);
			if (m.find()) {
				this.totalEntries = Integer.parseInt(m.group(1));
				this.currentPage = Integer.parseInt(m.group(2));
				this.totalPages = Integer.parseInt(m.group(3));
			}
		}
	}

	private void parseEntries() {
		Element formFenye = document.getElementById("Form2");
		Node node = formFenye.getParentNode().getParentNode()
				.getPreviousSibling().getPreviousSibling();
		if (node instanceof HTMLTableElement) {
			HTMLTableElement table = (HTMLTableElement) node;
			HTMLCollection rows = table.getRows();
			for (int i = 2; i < rows.getLength() - 1; i++) {
				HTMLTableRowElement row = (HTMLTableRowElement) rows.item(i);
				HTMLCollection cells = row.getCells();

				// date: 2007-02-01 10:03:31
				HTMLTableCellElement cell = (HTMLTableCellElement) cells
						.item(1);
				String value = cell.getFirstChild().getNodeValue();
				try {
					this.publishedDates.add(sdf.parse(value));
				} catch (ParseException e) {
					throw new RuntimeException(e);
				}

				// permalink and title
				cell = (HTMLTableCellElement) cells.item(2);
				NodeList as = cell.getElementsByTagName("A");
				HTMLAnchorElement a = (HTMLAnchorElement) as.item(0);
				this.permalinks.add(a.getHref());
				this.titles.add(a.getFirstChild().getNodeValue());

				// Modify link.
				cell = (HTMLTableCellElement) cells.item(3);
				a = (HTMLAnchorElement) cell.getChildNodes().item(1)
						.getChildNodes().item(0);
				this.modifyLinks.add(a.getHref());
			}
		}
	}

	@SuppressWarnings("unused")
	private void log(Node node) {
		if (log.isDebugEnabled()) {
			try {
				log.debug(DomNodeUtils.getXmlAsString(node));
			} catch (TransformerException e) {
				log.debug(e);
			}
		}
	}
}
