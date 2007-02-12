/**
 * Created on 2007-2-13 上午12:01:57
 */
package com.redv.blogmover.bsps.com.sohu.blog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.transform.TransformerException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.redv.blogmover.BlogMoverRuntimeException;
import com.redv.blogmover.util.DomNodeUtils;

/**
 * @author shutra
 * 
 */
public class ListPageParser {
	private static final Log log = LogFactory.getLog(ListPageParser.class);

	private static final SimpleDateFormat sdf = new SimpleDateFormat(
			"yyyy-MM-dd");

	private Document document;

	private int totalPage;

	private List<Date> dates;

	private List<String> titles;

	private List<String> permalinks;

	private List<String> editUrls;

	/**
	 * @param document
	 *            the document to set
	 */
	public void setDocument(Document document) {
		this.document = document;
	}

	/**
	 * @return the dates
	 */
	public List<Date> getDates() {
		return dates;
	}

	/**
	 * @return the editUrls
	 */
	public List<String> getEditUrls() {
		return editUrls;
	}

	/**
	 * @return the permalinks
	 */
	public List<String> getPermalinks() {
		return permalinks;
	}

	/**
	 * @return the titles
	 */
	public List<String> getTitles() {
		return titles;
	}

	/**
	 * @return the totalPage
	 */
	public int getTotalPage() {
		return totalPage;
	}

	public void parse() {
		this.dates = new ArrayList<Date>();
		this.permalinks = new ArrayList<String>();
		this.titles = new ArrayList<String>();
		this.editUrls = new ArrayList<String>();

		Element entryList = document.getElementById("entryList");
		NodeList entryListChildNodes = entryList.getChildNodes();
		logNodeList(entryListChildNodes);

		Element itemDiv = (Element) entryListChildNodes.item(5);
		NodeList itemDivChildNodes = itemDiv.getChildNodes();
		logNodeList(itemDivChildNodes);

		Element itemContentDiv = (Element) itemDivChildNodes.item(3);
		Element itemInfo = (Element) itemDivChildNodes.item(7);
		parseItemInfo(itemInfo);

		NodeList itemContentDivChildNodes = itemContentDiv.getChildNodes();
		logNodeList(itemContentDivChildNodes);
		Element itemTable = (Element) itemContentDivChildNodes.item(3);
		parseItemTable(itemTable);
	}

	/**
	 * <xmp><div class="item-info">共2页&nbsp;上一页&nbsp;1&nbsp;<a
	 * href='?pg=2'>[2]</a>&nbsp;<a href='?pg=2'>下一页</a></div></xmp>
	 * 
	 * @param itemInfo
	 */
	private void parseItemInfo(Element itemInfo) {
		String totalPageSring = itemInfo.getFirstChild().getNodeValue();
		log.debug("totalPageString: " + totalPageSring);
		Pattern p = Pattern.compile("共([0-9]+)页");
		Matcher m = p.matcher(totalPageSring);
		if (m.find()) {
			this.totalPage = Integer.parseInt(m.group(1));
		}
	}

	private void parseItemTable(Element itemTable) {
		NodeList tbodies = itemTable.getElementsByTagName("TBODY");
		logNodeList(tbodies);
		Element tbody = (Element) tbodies.item(0);
		NodeList trs = tbody.getElementsByTagName("TR");
		for (int i = 0; i < trs.getLength(); i++) {
			parseItemTableTr((Element) trs.item(i));
		}
	}

	private void parseItemTableTr(Element tr) {
		NodeList tds = tr.getElementsByTagName("TD");
		Element td = (Element) tds.item(0);
		String dateString = td.getFirstChild().getNodeValue();
		log.debug("dateString: " + dateString);
		try {
			this.dates.add(sdf.parse(dateString.trim()));
		} catch (ParseException e) {
			throw new BlogMoverRuntimeException(e);
		}

		td = (Element) tds.item(1);
		Element a = (Element) td.getChildNodes().item(1);
		String permalink = a.getAttribute("href");
		log.debug("permalink: " + permalink);
		this.permalinks.add(permalink);
		String title = a.getFirstChild().getNodeValue();
		log.debug("title: " + title);
		this.titles.add(title);

		td = (Element) tds.item(2);
		a = (Element) td.getChildNodes().item(1);
		String editUrl = a.getAttribute("href");
		log.debug("editUrl: " + editUrl);
		this.editUrls.add(editUrl);
	}

	private void logNodeList(NodeList nodeList) {
		if (log.isDebugEnabled()) {
			log.debug("nodeList.getLength(): " + nodeList.getLength());
			for (int i = 0; i < nodeList.getLength(); i++) {
				log.debug("i = " + i);
				log.debug(nodeList.item(i).getClass());
				try {
					log.debug(DomNodeUtils.getXmlAsString(nodeList.item(i)));
				} catch (TransformerException e) {
					log.debug("", e);
				}
			}
		}
	}
}
