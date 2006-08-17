/**
 *  Created on 2006-6-24 13:25:42
 */
package com.redv.blogmover.bsps.space;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.redv.blogmover.util.DomNodeUtils;

/**
 * @author Joe
 * @version 1.0
 * 
 */
class HtmlUtil implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final List<String> singleTags = new ArrayList<String>();

	static {
		singleTags.add("img");
		singleTags.add("br");
		singleTags.add("input");
		singleTags.add("hr");
	}

	/**
	 * 
	 */
	HtmlUtil() {
		super();
	}

	/**
	 * 将节点转成 html。
	 * 
	 * @param nodeList
	 * @return
	 */
	StringBuffer toHtml(NodeList nodeList) {
		StringBuffer ret = new StringBuffer();
		for (int i = 0; i < nodeList.getLength(); i++) {
			ret.append(toHtml(nodeList.item(i)));
		}
		return ret;
	}

	/**
	 * 将单个节点转成 html。
	 * 
	 * @param node
	 * @return
	 */
	private StringBuffer toHtml(Node node) {
		StringBuffer ret = new StringBuffer();
		if (node.getNodeType() == Node.TEXT_NODE) {
			ret.append(this.textNodeToHtml(node));
		} else {
			String nodeName = node.getNodeName().toLowerCase();
			if (singleTags.contains(nodeName)) {
				ret.append(this.singleNodeToHtml(node));
			} else {
				ret.append(this.generalNodeToHtml(node));
			}
		}
		return ret;
	}

	/**
	 * 常规（带内容的）标签。
	 * 
	 * @param node
	 * @return
	 */
	private StringBuffer generalNodeToHtml(Node node) {
		StringBuffer ret = new StringBuffer();
		String nodeName = node.getNodeName().toLowerCase();
		ret.append("<").append(nodeName);
		if (node.hasAttributes()) {
			NamedNodeMap attributes = node.getAttributes();
			for (int i = 0; i < attributes.getLength(); i++) {
				Node n = attributes.item(i);
				ret.append(" ");
				ret.append(n.getNodeName()).append("=").append("\"").append(
						DomNodeUtils.getTextContent(n)).append("\"");
			}
		}
		ret.append(">");

		if (node.hasChildNodes()) {
			NodeList children = node.getChildNodes();
			for (int i = 0; i < children.getLength(); i++) {
				Node child = children.item(i);
				ret.append(toHtml(child));
			}
		}
		ret.append("</").append(nodeName).append(">");
		return ret;
	}

	/**
	 * 直接的纯文本。
	 * 
	 * @param textNode
	 * @return
	 */
	private StringBuffer textNodeToHtml(Node textNode) {
		return new StringBuffer(escapeHtml(textNode.getNodeValue()));
	}

	String escapeHtml(String s) {
		return s.replace("<", "&lt;").replace(">", "&gt;").replace("\"",
				"&quot;");
	}

	/**
	 * 无内容标签。
	 * 
	 * @param imgNode
	 * @return
	 */
	StringBuffer singleNodeToHtml(Node imgNode) {
		StringBuffer ret = new StringBuffer();
		ret.append("<").append(imgNode.getNodeName().toLowerCase());
		if (imgNode.hasAttributes()) {
			NamedNodeMap attributes = imgNode.getAttributes();
			for (int i = 0; i < attributes.getLength(); i++) {
				Node n = attributes.item(i);
				ret.append(" ");
				ret.append(n.getNodeName()).append("=").append("\"").append(
						DomNodeUtils.getTextContent(n)).append("\"");
			}
		}
		ret.append("/>");
		// TODO 增加下载图片到本地
		return ret;
	}

}
