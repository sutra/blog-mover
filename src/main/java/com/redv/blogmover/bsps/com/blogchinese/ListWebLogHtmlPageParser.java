/**
 * Created on 2007-1-27 下午10:17:43
 */
package com.redv.blogmover.bsps.com.blogchinese;

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
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.redv.blogmover.util.DomNodeUtils;

/**
 * Parse the document and find the web log list. The document is getting from
 * "http://www.blogchinese.com/user_blogmanage.asp".
 * 
 * @author shutra
 * 
 */
public class ListWebLogHtmlPageParser {
	private static final Log log = LogFactory
			.getLog(ListWebLogHtmlPageParser.class);

	private Document document;

	private int totalCount;

	private int currentPageNumber;

	private int lastPageNumber;

	private List<String> webLogIds;

	private List<String> urls;

	public ListWebLogHtmlPageParser() {
		super();
	}

	/**
	 * Parse the document, find the last page number, and the web logs in this
	 * list.
	 * 
	 * @param document
	 */
	public void parse() {
		if (log.isDebugEnabled()) {
			try {
				log.debug(DomNodeUtils.getXmlAsString(document));
			} catch (TransformerException e) {
				log.debug(e);
			}
		}
		this.webLogIds = new ArrayList<String>(20);
		this.urls = new ArrayList<String>(20);

		Element showPageDiv = document.getElementById("showpage");

		// The total count.
		String nodeValue = showPageDiv.getFirstChild().getNodeValue();
		log.debug(nodeValue);

		Pattern p = Pattern.compile("共([0-9]+)篇(日志|BLOG).*");
		Matcher m = p.matcher(nodeValue);
		if (m.find()) {
			this.totalCount = Integer.parseInt(m.group(1));
			log.debug(totalCount);
		}

		// The last page number.
		NodeList as = showPageDiv.getElementsByTagName("A");
		this.lastPageNumber = getLastPageNumber(as);

		if (this.lastPageNumber == 0) {
			this.lastPageNumber = currentPageNumber;
		}

		// The web logs id and url.
		List<Element> listContentUlNodes = this.findListContentUlNodes();

		for (Element ul : listContentUlNodes) {
			NodeList inputs = ul.getElementsByTagName("INPUT");
			for (int j = 0; j < inputs.getLength(); j++) {
				Element input = (Element) inputs.item(j);
				String name = input.getAttribute("name");
				if (StringUtils.equals(name, "id")) {
					this.webLogIds.add(input.getAttribute("value"));
				}
			}

			NodeList lis = ul.getElementsByTagName("LI");
			log.debug("lis.getLength(): " + lis.getLength());
			for (int j = 0; j < lis.getLength(); j++) {
				Element li = (Element) lis.item(j);
				if (log.isDebugEnabled()) {
					try {
						log.debug("li: " + DomNodeUtils.getXmlAsString(li));
					} catch (TransformerException e) {
						log.debug("exception: " + e);
					}
				}
				String styleClass = li.getAttribute("class");
				if (StringUtils.equals(styleClass, "t3")) {
					NodeList li_as = li.getElementsByTagName("A");
					if (li_as.getLength() == 1) {
						Element li_a = (Element) li_as.item(0);
						String href = li_a.getAttribute("href");
						this.urls.add(href);
					}
				}
			}
		}
	}

	/**
	 * Find a node which text content is "尾页" in a node list, parse it's href to
	 * find the page number.
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
	 * Find the nodes that is like this: <xmp>
	 * <ul class="list_content" onMouseOver="fSetBg(this)" onMouseOut="fReBg(this)">
	 * <li class="t1"><input name='id' type='checkbox' onClick="unselectall()"
	 * id="id" value='939713' /></li>
	 * <li class="t2">未分类</li>
	 * <li class="t3"> <a href=06091/260430/archives/2007/2007127212710.shtml
	 * target=_blank>a </a> </li>
	 * <li class="t4">blogmover</li>
	 * 
	 * <li class="t5" title="2007-1-27 21:20:00">2007-1-27 </li>
	 * <li class="t6">0/0</li>
	 * <li class="t7">发布</li>
	 * <li class="t9"> <a
	 * href='user_blogmanage.asp?action=updatelog&id=939713&t=0'>发布</a>&nbsp;<a
	 * href='user_post.asp?logid=939713&t=0'>修改</a>&nbsp;<a
	 * href='user_blogmanage.asp?action=del&id=939713&t=0' onClick='return
	 * confirm("确定要删除此日志吗？");'>删除</a>&nbsp; </li>
	 * </ul>
	 * </xmp>
	 * 
	 * @return
	 */
	private List<Element> findListContentUlNodes() {
		List<Element> ret = new ArrayList<Element>(20);
		Element list = document.getElementById("list");
		NodeList uls = list.getElementsByTagName("UL");
		for (int i = 0; i < uls.getLength(); i++) {
			Element ul = (Element) uls.item(i);
			if (StringUtils.equals(ul.getAttribute("class"), "list_content")) {
				ret.add(ul);
			}
		}

		return ret;
	}

	/**
	 * @param document
	 *            the document to set
	 */
	public void setDocument(Document document) {
		this.document = document;
	}

	/**
	 * @param currentPageNumber
	 *            the currentPageNumber to set
	 */
	public void setCurrentPageNumber(int currentPageNumber) {
		this.currentPageNumber = currentPageNumber;
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

	/**
	 * @return the urls
	 */
	public List<String> getUrls() {
		return urls;
	}

}
