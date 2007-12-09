/**
 * Created on 2007-12-9 下午03:18:46
 */
package com.redv.blogmover.bsps.hexun.htmlparser;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.redv.blogmover.bsps.hexun.HexunWebLog;
import com.redv.blogmover.util.DomNodeUtils;

/**
 * @author sutra
 * 
 */
public class DetailParser {
	private static final Log log = LogFactory.getLog(DetailParser.class);

	private final HexunWebLog entry;

	private final Document document;

	public DetailParser(HexunWebLog entry, Document document) {
		this.entry = entry;
		this.document = document;
		parse();
	}

	/**
	 * 
	 */
	private void parse() {
		// title
		Element titleElement = document
				.getElementById("NewEditArticle1_TitleTextbox");
		entry.setTitle(titleElement.getAttribute("value"));
		// excerpt
		Element excerptElement = document
				.getElementById("NewEditArticle1_BriefTextbox");
		entry.setExcerpt(StringUtils.trim(excerptElement.getFirstChild()
				.getNodeValue()));
		// tags
		Element tagsElement = document
				.getElementById("NewEditArticle1_TagTextbox");
		entry.setTags(parseTags(tagsElement.getAttribute("value")));

		NodeList textareas = document.getElementsByTagName("textarea");
		for (int i = 0; i < textareas.getLength(); i++) {
			Element textarea = (Element) textareas.item(i);
			String name = textarea.getAttribute("name");
			if ("NewEditArticle1:ContentSpaw".equals(name)) {
				// body
				entry.setBody(StringUtils.trim(DomNodeUtils
						.getTextContent(textarea)));
				if (log.isDebugEnabled()) {
					log.debug(entry.getBody());
				}
			}
		}
	}

	String[] parseTags(String t) {
		List<String> tags = new ArrayList<String>();
		char[] chars = t.toCharArray();
		boolean quote = false;
		StringBuffer sb = new StringBuffer();
		for (char c : chars) {
			if (c == '"') {
				quote = !quote;
			} else if (c == ' ') {
				if (quote) {
					sb.append(c);
				} else {
					if (sb.toString().trim().length() > 0) {
						tags.add(StringUtils.trim(sb.toString()));
					}
					sb = new StringBuffer();
				}
			} else if (c == ',') {
				if (quote) {
					sb.append(c);
				} else {
					if (sb.toString().trim().length() > 0) {
						tags.add(StringUtils.trim(sb.toString()));
					}
					sb = new StringBuffer();
				}
			} else {
				sb.append(c);
			}
		}
		if (sb.toString().trim().length() > 0) {
			tags.add(StringUtils.trim(sb.toString()));
		}
		String[] ret = new String[tags.size()];
		tags.toArray(ret);
		return ret;
	}
}
