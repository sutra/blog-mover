/**
 *  Created on 2006-6-28 23:42:54
 */
package com.redv.blogmover.util;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Element;
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
	private static final Log log = LogFactory.getLog(DomNodeUtils.class);

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

	/**
	 * 
	 * @param node
	 * @return
	 * @deprecated Use {@link #getXmlAsString(Node)} instead.
	 */
	public static String toString(Node node) {
		StringBuffer sb = new StringBuffer();
		if (node instanceof Text) {
			if (node.getNodeValue() != null) {
				byte[] bytes = node.getNodeValue().getBytes();
				List<Byte> newBytes = new ArrayList<Byte>();
				for (int i = 0; i < bytes.length; i++) {
					if (bytes[i] == 63) {
						byte[] bs = "&nbsp;".getBytes();
						for (byte b : bs) {
							newBytes.add(b);
						}
					} else {
						newBytes.add(bytes[i]);
					}
				}
				byte[] valueBytes = new byte[newBytes.size()];
				for (int i = 0; i < newBytes.size(); i++) {
					valueBytes[i] = newBytes.get(i).byteValue();
				}
				sb.append(new String(valueBytes));
			}
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
					String str = toString(children.item(i));
					if (str != null)
						sb.append(str);
				}
			} else {
				sb.append(node.getNodeValue());
			}
			sb.append("</").append(node.getNodeName()).append(">");
		}
		return sb.toString();
	}

	private static Transformer getTransformer() throws TransformerException {
		TransformerFactory factory = TransformerFactory.newInstance();
		Transformer transformer = factory.newTransformer();
		transformer.setOutputProperty(javax.xml.transform.OutputKeys.INDENT,
				"yes");
		transformer.setOutputProperty(
				javax.xml.transform.OutputKeys.STANDALONE, "yes");
		return transformer;
	}

	/**
	 * @param node
	 * @return output of the node's xml string.
	 * @throws TransformerException
	 */
	public static String getXmlAsString(Node node) throws TransformerException {
		Transformer transformer = getTransformer();
		DOMSource source = new DOMSource(node);
		StringWriter xmlString = new StringWriter();
		StreamResult streamResult = new StreamResult(xmlString);
		transformer.transform(source, streamResult);
		return xmlString.toString();
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
	 * A <code>select</code> html element, parse it, and return the html form
	 * select. pair.
	 * 
	 * @param select
	 *            a <code>select</code> html element.
	 * @return the html form select.
	 */
	public static HtmlFormSelect getSelect(Element select) {
		HtmlFormSelect hfs = new HtmlFormSelect();
		try {
			log.debug(getXmlAsString(select));
		} catch (TransformerException e) {
			log.warn(e);
		}
		hfs.setName(select.getAttribute("name"));
		NodeList options = select.getElementsByTagName("option");
		List<String> values = new ArrayList<String>();
		for (int i = 0; i < options.getLength(); i++) {
			Element option = (Element) options.item(i);
			Node selectedNode = option.getAttributes().getNamedItem("selected");
			log.debug("is selectedNode null: " + (selectedNode == null));
			if (selectedNode != null) {
				values.add(option.getAttribute("value"));
			}
		}
		String[] valueStrings = new String[values.size()];
		values.toArray(valueStrings);
		hfs.setValues(valueStrings);
		return hfs;
	}

}
