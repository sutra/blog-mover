/**
 * Created on 2007-2-3 上午12:16:29
 */
package com.redv.blogmover.bsps.cn.com.blog;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.redv.blogmover.BlogMoverException;

/**
 * @author shutra
 * 
 */
class LoginResponseParser {

	@SuppressWarnings("unused")
	private static final Log log = LogFactory.getLog(LoginResponseParser.class);

	/**
	 * Check if the document tell us it is login OK.
	 * 
	 * @param document
	 * @return if is login OK, return true, otherwise false.
	 * @throws BlogMoverException
	 */
	public boolean checkLoginSuccess(Document document) {
		boolean ok = false;
		NodeList nodes = document.getElementsByTagName("title");
		for (int i = 0; i < nodes.getLength(); i++) {
			Element element = (Element) nodes.item(i);
			String title = element.getFirstChild().getNodeValue();
			if (title.indexOf("用户管理后台") != -1) {
				ok = true;
			}
		}
		return ok;
	}
}
