/**
 * Created on 2007-12-9 下午01:36:58
 */
package com.redv.blogmover.bsps.hexun.htmlparser;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.html.HTMLAnchorElement;
import org.w3c.dom.html.HTMLDivElement;
import org.w3c.dom.html.HTMLInputElement;

import com.redv.blogmover.bsps.hexun.HexunWebLog;

/**
 * @author sutra
 * 
 */
public class ListParser {
	private static final Log log = LogFactory.getLog(ListParser.class);

	private static final String LINE_CLASS_NAME = "table_bg3";

	private static final SimpleDateFormat DATE_PARSER = new SimpleDateFormat(
			"MM-dd HH:mm");

	private Document document;

	private List<HexunWebLog> entries;

	public ListParser(Document document) {
		this.document = document;
		entries = new ArrayList<HexunWebLog>(20);// 和讯的博客文章编辑页面每页默认最多20个日志。
		parse();
	}

	/**
	 * @return entries
	 */
	public List<HexunWebLog> getEntries() {
		return entries;
	}

	/**
	 * 
	 */
	private void parse() {
		NodeList divs = document.getElementsByTagName("div");
		for (int i = 0; i < divs.getLength(); i++) {
			Node divNode = divs.item(i);
			if (divNode instanceof HTMLDivElement) {
				HTMLDivElement div = (HTMLDivElement) divNode;
				if (LINE_CLASS_NAME.equals(div.getClassName())) {
					entries.add(parseLine(div));
				}
			}
		}
	}

	/**
	 * @param div
	 * @return
	 */
	private HexunWebLog parseLine(HTMLDivElement div) {
		HexunWebLog hwl = new HexunWebLog();
		HTMLDivElement t_3_1 = (HTMLDivElement) div.getFirstChild();
		NodeList childNodes = t_3_1.getChildNodes();

		HTMLInputElement checkbox = (HTMLInputElement) childNodes.item(0);
		hwl.setArticleId(checkbox.getValue());

		HTMLAnchorElement a = (HTMLAnchorElement) childNodes.item(1);
		hwl.setUrl(a.getHref());
		hwl.setTitle(a.getTextContent());

		HTMLDivElement t_4 = (HTMLDivElement) t_3_1.getNextSibling()
				.getNextSibling().getNextSibling();
		String dateString = t_4.getTextContent();
		log.debug("dateString: " + dateString);
		try {
			hwl.setPublishedDate(DATE_PARSER.parse(dateString));
		} catch (ParseException e) {
			log.warn("Parse date error", e);
		}

		HTMLDivElement t_5 = (HTMLDivElement) t_4.getNextSibling();

		a = (HTMLAnchorElement) t_5.getFirstChild();
		hwl.setEditUrl(a.getHref());

		return hwl;
	}
}
