/**
 * Created on 2007-4-21 上午10:08:07
 */
package com.redv.blogmover.bsps.com.live.spaces;

import javax.xml.transform.TransformerException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.redv.blogmover.util.DomNodeUtils;

/**
 * Windows live spaces' home page parser.
 * <p>
 * Parse http://<YOUR SPACE NAME>.spaces.live.com/ to find the entries list
 * page's url.
 * </p>
 * 
 * @author Sutra Zhou
 * 
 */
public class HomepageParser {
	private final Log log = LogFactory.getLog(HomepageParser.class);

	private static final String FLAG = "查看更多的博客";

	private Document document;

	private String secondPage;

	/**
	 * @return the secondPage
	 */
	public String getSecondPage() {
		return secondPage;
	}

	/**
	 * @param document
	 *            the document to set
	 */
	public void setDocument(Document document) {
		this.document = document;
	}

	public void parse() {
		NodeList as = document.getElementsByTagName("a");
		for (int i = 0; i < as.getLength(); i++) {
			Element a = (Element) as.item(i);
			try {
				log.debug(DomNodeUtils.getXmlAsString(a));
			} catch (TransformerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			log.debug(a.hasChildNodes());
			if (a.hasChildNodes())
				try {
					log.debug(DomNodeUtils.getXmlAsString(a.getFirstChild()));
				} catch (TransformerException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			if (a.hasChildNodes()
					&& FLAG.equals(a.getFirstChild().getNodeValue())) {
				secondPage = a.getAttribute("href");
				log.debug("secondPage: " + secondPage);
				break;
			}
		}
	}

}
