/**
 * Created on 2007-2-22 下午11:25:45
 */
package com.redv.blogmover.bsps.com.sohu.blog;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;

import com.redv.blogmover.WebLog;
import com.redv.blogmover.impl.WebLogImpl;

/**
 * @author <a href="mailto:zhoushuqun@gmail.com">Sutra</a>
 * 
 */
public class EditPageParser {
	@SuppressWarnings("unused")
	private static final Log log = LogFactory.getLog(EditPageParser.class);

	private Document document;

	private WebLog webLog;

	/**
	 * @param document
	 *            the document to set
	 */
	public void setDocument(Document document) {
		this.document = document;
	}

	/**
	 * @return the webLog
	 */
	public WebLog getWebLog() {
		return webLog;
	}

	public void parse() {
		webLog = new WebLogImpl();
		webLog.setTitle(document.getElementById("entrytitle").getAttribute(
				"value"));
		webLog.setBody(document.getElementById("entrycontent").getFirstChild()
				.getNodeValue());
		String[] tags = document.getElementById("keywords").getAttribute(
				"value").split(" ");
		if (StringUtils.isEmpty(tags[tags.length - 1])) {
			tags = (String[]) ArrayUtils.subarray(tags, 0, tags.length - 1);
		}
		webLog.setTags(tags);
		webLog.setExcerpt(document.getElementById("excerpt").getFirstChild()
				.getNodeValue());
	}
}
