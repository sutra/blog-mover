/**
 * Created on 2007-4-21 下午07:55:50
 */
package com.redv.blogmover.bsps.com.live.spaces;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * Windows live space's list page parser.
 * <p>
 * Parse the entry list html page to find the web log entries' permalinks.
 * </p>
 * 
 * @author Sutra Zhou
 * 
 */
public class ListParser {
	private Document document;

	private String previousPageUrl;

	private String nextPageUrl;

	private List<String> permalinks;

	/**
	 * @param document
	 *            the document to set
	 */
	public void setDocument(Document document) {
		this.document = document;
	}

	/**
	 * @return the nextPageUrl
	 */
	public String getNextPageUrl() {
		return nextPageUrl;
	}

	/**
	 * @return the previousPageUrl
	 */
	public String getPreviousPageUrl() {
		return previousPageUrl;
	}

	/**
	 * @return the permalinks
	 */
	public List<String> getPermalinks() {
		return permalinks;
	}

	public void parse() {
		Element previousPageAnchor = document
				.getElementById("BlogViewPaging_Prev");
		if (!previousPageAnchor.getAttribute("style").equals("display:none")) {
			this.previousPageUrl = previousPageAnchor.getAttribute("href");
		}

		Element nextPageAnchor = document.getElementById("BlogViewPaging_Next");
		if (!nextPageAnchor.getAttribute("style").equals("display:none")) {
			this.nextPageUrl = nextPageAnchor.getAttribute("href");
		}

		this.permalinks = new ArrayList<String>();
		NodeList as = document.getElementsByTagName("a");
		for (int i = 0; i < as.getLength(); i++) {
			Element a = (Element) as.item(i);
			String id = a.getAttribute("id");
			if (id.startsWith("blogPermalink")) {
				this.permalinks.add(a.getAttribute("href"));
			}
		}
	}
}
