/**
 * 
 */
package com.redv.blogmover.bsps.com.blogcup;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.redv.blogmover.WebLog;
import com.redv.blogmover.impl.WebLogImpl;
import com.redv.blogmover.util.DomNodeUtils;
import com.redv.blogmover.util.HtmlFormSelect;

/**
 * @author shutrazh
 * 
 */
public class ModifyWebLogHtmlParser {
	private static final Log log = LogFactory
			.getLog(ModifyWebLogHtmlParser.class);

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

		webLog.setTitle(document.getElementById("topic").getAttribute("value"));
		webLog.setBody(document.getElementById("edit").getAttribute("value"));
		webLog.setPublishedDate(parsePublishedDate());
		String[] tags = document.getElementById("itag").getAttribute("value")
				.split(",");
		webLog.setTags(tags);
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
		int date = getValue("selectd");
		log.debug("date: " + date);
		cal.set(Calendar.DATE, date);
		cal.set(Calendar.HOUR_OF_DAY, getValue("selecth"));
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

}
