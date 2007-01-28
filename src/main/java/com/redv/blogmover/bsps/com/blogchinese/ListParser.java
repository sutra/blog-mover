/**
 * Created on 2007-1-27 下午10:17:43
 */
package com.redv.blogmover.bsps.com.blogchinese;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.redv.blogmover.util.DomNodeUtils;

/**
 * @author shutra
 * 
 */
public class ListParser {
	private static final Log log = LogFactory.getLog(ListParser.class);

	private int totalCount;

	private int lastPageNumber;

	private List<String> webLogIds;

	public ListParser() {
		super();
	}

	/**
	 * Parse the document, find the last page number, and the web logs in this
	 * list.
	 * 
	 * @param document
	 */
	public void parse(Document document) {
		this.webLogIds = new ArrayList<String>(20);

		Element showPageDiv = document.getElementById("showpage");

		// The total count.
		String nodeValue = showPageDiv.getFirstChild().getNodeValue();
		log.debug(nodeValue);

		Pattern p = Pattern.compile("共([0-9]+)篇日志.*");
		Matcher m = p.matcher(nodeValue);
		if (m.find()) {
			this.totalCount = Integer.parseInt(m.group(1));
			log.debug(totalCount);
		}

		// The last page number.
		NodeList as = showPageDiv.getElementsByTagName("a");
		this.lastPageNumber = getLastPageNumber(as);

		// The modify urls.
		Element list = document.getElementById("list");
		NodeList uls = list.getElementsByTagName("ul");
		for (int i = 0; i < uls.getLength(); i++) {
			Element ul = (Element) uls.item(i);
			if (StringUtils.equals(ul.getAttribute("class"), "list_content")) {
				NodeList inputs = ul.getElementsByTagName("input");
				for (int j = 0; j < inputs.getLength(); j++) {
					Element input = (Element) inputs.item(j);
					String name = input.getAttribute("name");
					if (StringUtils.equals(name, "id")) {
						this.webLogIds.add(input.getAttribute("value"));
					}
				}
			}
		}
	}

	/**
	 * Find a node which text content is "尾页'" in a node list, parse it's href
	 * to find the page number.
	 * 
	 * @param as
	 * @return
	 */
	private int getLastPageNumber(NodeList as) {
		for (int i = 0; i < as.getLength(); i++) {
			log.debug(as.item(i).getTextContent());
			Node node = as.item(i);
			String textContent = DomNodeUtils.getTextContent(node);
			if (StringUtils.equals(textContent, "尾页")) {
				String href = node.getAttributes().getNamedItem("href")
						.getNodeValue();
				log.debug(href);
				return getLastPageNumber(href);
			}
		}
		return 0;
	}

	/**
	 * Parse the page number from this styled url
	 * <code>user_blogmanage.asp?t=0&page=2</code>.
	 * 
	 * @param href
	 * @return
	 */
	private int getLastPageNumber(String href) {
		if (href == null) {
			return 0;
		}
		int lastIndexOf = href.lastIndexOf("=");
		String pageNumberString = href
				.substring(lastIndexOf + 1, href.length());
		log.debug(pageNumberString);
		return Integer.parseInt(pageNumberString);
	}

	/**
	 * Get web log count of this blog. This is the total count, not just in this
	 * page.
	 * 
	 * @return
	 */
	public int getTotalCount() {
		return this.totalCount;
	}

	/**
	 * Get the last page number after parsed.
	 * 
	 * @return
	 */
	public int getLastPageNumber() {
		return lastPageNumber;
	}

	/**
	 * Get the web logs id in this list page.
	 * 
	 * @return
	 */
	public List<String> getWebLogIds() {
		return webLogIds;
	}
}
