/**
 * 
 */
package com.redv.blogmover.bsps.com.blogcn;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.html.HTMLTextAreaElement;

import com.redv.blogmover.WebLog;
import com.redv.blogmover.impl.WebLogImpl;

/**
 * @author shutrazh
 * 
 */
class ModifyEntryHtmlParser {
	@SuppressWarnings("unused")
	private static final Log log = LogFactory
			.getLog(ModifyEntryHtmlParser.class);

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
		webLog.setTitle(document.getElementById("mytitle")
				.getAttribute("value"));
		webLog.setTags(document.getElementById("keyword").getAttribute("value")
				.split(","));
		HTMLTextAreaElement textarea = (HTMLTextAreaElement) document
				.getElementById("mycontent");
		webLog.setBody(textarea.getFirstChild().getNodeValue());
		webLog.setPublishedDate(parsePublishedDate());
	}

	private Date parsePublishedDate() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, getValue("Text3"));
		cal.set(Calendar.MONTH, getValue("Text4") - 1);
		cal.set(Calendar.DATE, getValue("Text5"));
		cal.set(Calendar.HOUR_OF_DAY, getValue("Text6"));
		cal.set(Calendar.MINUTE, getValue("Text7"));
		cal.set(Calendar.SECOND, getValue("Text8"));
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	private int getValue(String id) {
		Element element = document.getElementById(id);
		return Integer.parseInt(element.getAttribute("value"));
	}
}
