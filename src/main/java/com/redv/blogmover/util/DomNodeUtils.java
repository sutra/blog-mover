/**
 *  Created on 2006-6-28 23:42:54
 */
package com.redv.blogmover.util;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

/**
 * @author Joe
 * @version 1.0
 * 
 */
public class DomNodeUtils {
	public static String getTextContent(Node node) {
		String ret = null;
		switch (node.getNodeType()) {
		case Node.ELEMENT_NODE:
		case Node.ATTRIBUTE_NODE:
		case Node.ENTITY_NODE:
		case Node.ENTITY_REFERENCE_NODE:
		case Node.DOCUMENT_FRAGMENT_NODE:
			Node child = node.getFirstChild();
			if (child != null) {
				ret = child.getNodeValue();
			}
			break;
		case Node.TEXT_NODE:
		case Node.CDATA_SECTION_NODE:
		case Node.COMMENT_NODE:
		case Node.PROCESSING_INSTRUCTION_NODE:
			ret = node.getNodeValue();
			break;
		case Node.DOCUMENT_NODE:
		case Node.DOCUMENT_TYPE_NODE:
		case Node.NOTATION_NODE:
			ret = null;
			break;
		}
		return ret;
	}

	public static String toString(Node node) {
		StringBuffer sb = new StringBuffer();
		if (node instanceof Text) {
			sb.append(node.getNodeValue());
		} else {
			sb.append("<").append(node.getNodeName());
			NamedNodeMap attrs = node.getAttributes();
			for (int j = 0; j < attrs.getLength(); j++) {
				sb.append(" ").append(attrs.item(j).getNodeName())
						.append("=\"").append(attrs.item(j).getNodeValue())
						.append("\"");
			}
			sb.append(">");
			if (node.hasChildNodes()) {
				NodeList children = node.getChildNodes();
				for (int i = 0; i < children.getLength(); i++) {
					sb.append(toString(children.item(i)));
				}
			} else {
				sb.append(node.getNodeValue());
			}
			sb.append("</").append(node.getNodeName()).append(">");
		}
		return sb.toString();
	}

	/**
	 * 获取某节点下的子节点（包括孙子……节点）的标签名为tagName的节点。
	 * 
	 * @param node
	 * @param tagName
	 * @return
	 */
	public static NodeList getElementsByTagName(Node node, String tagName) {
		// TODO
		return null;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

	}

}
