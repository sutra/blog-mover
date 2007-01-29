/**
 * 
 */
package com.redv.blogmover.bsps.com.blogchinese;

import java.util.Calendar;
import java.util.Date;

import javax.xml.transform.TransformerException;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.redv.blogmover.WebLog;
import com.redv.blogmover.impl.WebLogImpl;
import com.redv.blogmover.util.DomNodeUtils;
import com.redv.blogmover.util.HtmlFormSelect;

/**
 * @author shutrazh
 * 
 */
public class ModifyWebLogHtmlPageParser {
	private static final Log log = LogFactory
			.getLog(ModifyWebLogHtmlPageParser.class);

	private WebLog webLog;

	private Document document;

	/**
	 * @return the webLog
	 */
	public WebLog getWebLog() {
		return webLog;
	}

	/**
	 * @param document
	 *            the document to set
	 */
	public void setDocument(Document document) {
		this.document = document;
	}

	public void parse() {
		webLog = new WebLogImpl();
		try {
			log.debug(DomNodeUtils.getXmlAsString(document));
		} catch (TransformerException e) {
			log.warn(e);
		}
		Element topic = document.getElementById("topic");
		try {
			log.debug(DomNodeUtils.getXmlAsString(topic));
		} catch (TransformerException e) {
			log.warn(e);
		}
		webLog.setTitle(topic.getAttribute("value"));
		webLog.setBody(document.getElementById("edit").getAttribute("value"));
		webLog.setPublishedDate(parsePublishedDate());
		webLog.setTags(parseTags());
	}

	/**
	 * Parse the published date from the document.
	 * 
	 * @return
	 */
	private Date parsePublishedDate() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, getValue("selecty"));
		cal.set(Calendar.MONTH, getValue("selectm") - 1);
		cal.set(Calendar.DATE, getValue("selectd"));
		cal.set(Calendar.HOUR, getValue("selecth"));
		cal.set(Calendar.MINUTE, getValue("selectmi"));
		cal.set(Calendar.SECOND, getValue("selects"));
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	/**
	 * Get the select year, month, date, hour, minute, second value.
	 * 
	 * @param id
	 * @return
	 */
	private int getValue(String id) {
		Element select = document.getElementById(id);
		HtmlFormSelect hfs = DomNodeUtils.getSelect(select);
		String[] values = hfs.getValues();
		log.debug("values length: " + values.length);
		int ret = 0;
		if (values.length == 1) {
			return Integer.parseInt(values[0]);
		}
		log.debug("getValue.ret: " + ret);
		return ret;
	}

	/**
	 * Parse the tags.
	 * 
	 * @return
	 */
	private String[] parseTags() {
		NodeList inputs = document.getElementById("list").getElementsByTagName(
				"input");
		for (int i = 0; i < inputs.getLength(); i++) {
			Element input = (Element) inputs.item(i);
			if (StringUtils.equals(input.getAttribute("name"), "logtags")) {
				String value = input.getAttribute("value");
				log.debug("the from input 'tag' value: " + value);
				String[] tags = value.split(" ");
				for (int j = 0; j < tags.length; j++) {
					tags[j] = StringUtils.trim(tags[j]);
				}
				return tags;
			}
		}
		return null;
	}
}
