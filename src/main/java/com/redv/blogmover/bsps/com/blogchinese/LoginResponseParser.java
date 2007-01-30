/**
 * Created on 2007-1-27 下午02:57:41
 */
package com.redv.blogmover.bsps.com.blogchinese;

import javax.xml.transform.TransformerException;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.redv.blogmover.util.DomNodeUtils;

/**
 * Parse the document and judge it is the login success response page.
 * 
 * @author shutra
 * 
 */
public class LoginResponseParser {
	private static final Log log = LogFactory.getLog(LoginResponseParser.class);

	/**
	 * Check if the document tell us it is login OK.
	 * 
	 * @param document
	 * @return if is login OK, return true, otherwise false.
	 */
	public boolean checkLoginSuccess(Document document) {
		if (log.isDebugEnabled()) {
			try {
				log.debug("checkLoginSuccess(Document): "
						+ DomNodeUtils.getXmlAsString(document));
			} catch (TransformerException e) {
				log.debug(e);
			}
		}
		boolean ret = false;
		NodeList titles = document.getElementsByTagName("TITLE");
		if (titles.getLength() == 1) {
			Element title = (Element) titles.item(0);
			String titleString = DomNodeUtils.getTextContent(title);
			log.debug(titleString);
			ret = StringUtils.contains(titleString, "-用户管理后台");
		} else {
			ret = false;
		}
		return ret;
	}
}
