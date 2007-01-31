/**
 * 
 */
package com.redv.blogmover.bsps.com.blogcup;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import com.redv.blogmover.util.DomNodeUtils;

/**
 * @author shutrazh
 * 
 */
public class LoginResponseParser {
	private static final Log log = LogFactory.getLog(LoginResponseParser.class);

	private Document document;

	private boolean loggedIn;

	/**
	 * @param document
	 *            the document to set
	 */
	public void setDocument(Document document) {
		this.document = document;
	}

	/**
	 * @return the loggedIn
	 */
	public boolean isLoggedIn() {
		return loggedIn;
	}

	public void parse() {
		NodeList titles = document.getElementsByTagName("title");
		if (titles.getLength() == 0) {
			loggedIn = false;
		} else {
			String titleString = DomNodeUtils.getTextContent(titles.item(0));
			log.debug("titleString: " + titleString);
			int index = titleString.indexOf("的BlogCup管理站台");
			log.debug("index: " + index);
			if (index != -1) {
				loggedIn = true;
			} else {
				loggedIn = false;
			}
		}
	}
}
