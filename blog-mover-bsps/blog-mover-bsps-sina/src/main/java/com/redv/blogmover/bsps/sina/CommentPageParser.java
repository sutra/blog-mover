/**
 * Created on 2007-09-23 00:26
 */
package com.redv.blogmover.bsps.sina;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.TransformerException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.redv.blogmover.BlogMoverRuntimeException;
import com.redv.blogmover.Comment;
import com.redv.blogmover.impl.CommentImpl;
import com.redv.blogmover.util.DomNodeUtils;

/**
 * The comment page parser for sina.
 * 
 * @author sutra
 */
public class CommentPageParser {
	private static final Log log = LogFactory.getLog(CommentPageParser.class);

	private static final SimpleDateFormat commentDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");

	private Document document;

	private List<Comment> comments;

	public CommentPageParser(Document document) {
		this.document = document;
		this.comments = new ArrayList<Comment>();

		parse();
	}

	/**
	 * @return comments
	 */
	public List<Comment> getComments() {
		return comments;
	}

	private void parse() {
		Element ul = this.document.getElementById("commentContainer");
		NodeList lis = ul.getChildNodes();
		for (int i = 0, j = lis.getLength(); i < j; i++) {
			if (lis.item(i).getNodeName().equalsIgnoreCase("li")) {
				parseLi(lis.item(i));
			}
		}
	}

	private void parseLi(Node li) {
		Comment comment = new CommentImpl();
		Node firstDiv = li.getFirstChild().getNextSibling();
		parseDiv1(comment, firstDiv);
		Node secondDiv = firstDiv.getNextSibling().getNextSibling();
		parseDiv2(comment, secondDiv);
		this.comments.add(comment);
	}

	private void parseDiv1(Comment comment, Node div) {
		Node commentTitle = div.getFirstChild().getNextSibling()
				.getNextSibling().getNextSibling().getNextSibling()
				.getNextSibling();
		String name = commentTitle.getFirstChild().getNodeValue();
		Node urlNode = commentTitle.getAttributes().getNamedItem("href");
		String url = null;
		if (urlNode != null) {
			url = urlNode.getNodeValue();
		}
		log.debug("name: " + name);
		log.debug("url: " + url);
		comment.setName(name);
		comment.setUrl(url);
	}

	private void parseDiv2(Comment comment, Node div) {
		Node commentTimeHolder = div.getFirstChild().getNextSibling()
				.getNextSibling().getNextSibling();
		Node timeNode = commentTimeHolder.getFirstChild().getNextSibling()
				.getFirstChild();
		String timeString = timeNode.getNodeValue();
		log.debug("timeString: " + timeString);
		try {
			comment.setPublishedDate(commentDateFormat.parse(timeString));
		} catch (ParseException e) {
			throw new BlogMoverRuntimeException(e);
		}
		Node commentContentNode = commentTimeHolder.getNextSibling()
				.getNextSibling();
		DomNodeUtils.debug(log, commentContentNode);
		try {
			String s = DomNodeUtils.getXmlAsString(commentContentNode);
			comment.setContent(s.substring(s.indexOf('>') + 1, s
					.lastIndexOf('<')));
		} catch (TransformerException e) {
			throw new BlogMoverRuntimeException(e);
		}
	}
}
