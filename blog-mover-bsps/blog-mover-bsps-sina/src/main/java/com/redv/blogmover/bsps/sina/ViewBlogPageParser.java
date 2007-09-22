/**
 * Created on 2007-09-23 00:24
 */
package com.redv.blogmover.bsps.sina;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * @author sutra
 * 
 */
public class ViewBlogPageParser {
	private String commentsFirstPageUrl;

	public ViewBlogPageParser(Document document) {
		Element commentsAnchor = document.getElementById("comments");
		if (commentsAnchor != null) {
			commentsFirstPageUrl = commentsAnchor.getAttribute("href");
		}
	}

	/**
	 * @return commentsFirstPageUrl
	 */
	public String getCommentsFirstPageUrl() {
		return commentsFirstPageUrl;
	}

}
