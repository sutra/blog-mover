/**
 *  Created on 2006-6-28 23:42:54
 */
package com.redv.blogmover.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.StringWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

import com.redv.blogmover.BlogSettings;
//import com.sun.image.codec.jpeg.JPEGCodec;
//import com.sun.image.codec.jpeg.JPEGImageEncoder;

/**
 * @author Joe
 * @version 1.0
 * 
 */
public class DomNodeUtils {
	private static final Log log = LogFactory.getLog(DomNodeUtils.class);

	/**
	 * Get text of the specified node recursively.
	 * 
	 * @param node
	 * @return
	 */
	public static String getTextContent(Node node) {
		return getTextContent(node, false);
	}

	/**
	 * Get text of the specified node recursively.
	 * 
	 * @param node
	 * @param comment
	 *            retrive comment as text
	 * @return
	 */
	public static String getTextContent(Node node, boolean comment) {
		StringBuilder ret = new StringBuilder();
		if (log.isDebugEnabled()) {
			log.debug("node type: " + node.getNodeType());
		}
		switch (node.getNodeType()) {
		case Node.ELEMENT_NODE: // 1
		case Node.ATTRIBUTE_NODE:// 2
		case Node.ENTITY_NODE: // 6
		case Node.ENTITY_REFERENCE_NODE: // 5
		case Node.DOCUMENT_NODE: // 9
		case Node.DOCUMENT_FRAGMENT_NODE: // 11
			NodeList childNodes = node.getChildNodes();
			for (int i = 0, l = childNodes.getLength(); i < l; i++) {
				ret.append(getTextContent(childNodes.item(i)));
			}
			break;
		case Node.TEXT_NODE: // 3
		case Node.CDATA_SECTION_NODE: // 4
		case Node.PROCESSING_INSTRUCTION_NODE: // 7
			ret.append(node.getNodeValue());
			break;
		case Node.COMMENT_NODE: // 8
			if (comment) {
				ret.append(node.getNodeValue());
			}
			break;
		case Node.DOCUMENT_TYPE_NODE: // 10
		case Node.NOTATION_NODE: // 12
			ret = null;
			break;
		}
		return ret != null ? ret.toString() : null;
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
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		transformer.setOutputProperty(OutputKeys.STANDALONE, "yes");
		transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
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

	public static String getHtmlAsString(Node node) {
		String s = "";
		try {
			s = getXmlAsString(node);
		} catch (TransformerException e) {
		}
		return s.toLowerCase().replaceAll(" xmlns=\"http://www.w3.org/1999/xhtml\"", "");
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

	public static List<Node> getElementsByCssClass(Document document, String tagName, String className) {
		List<Node> nodes = new ArrayList<Node>();
		NodeList nodeList = document.getElementsByTagName(tagName);
		for (int i = 0, l = nodeList.getLength(); i < l; i++) {
			Node node = nodeList.item(i);
			if (ArrayUtils.contains(getCssClasses(node), className)) {
				nodes.add(node);
			}
		}
		return nodes;
	}

	/**
	 * Get the attribute value of the node.
	 * 
	 * @param node
	 *            the node
	 * @param attributeName
	 *            the name of the attribute
	 * @return the value of the attribute
	 */
	public static String getAttributeValue(Node node, String attributeName) {
		Node attribute = node.getAttributes().getNamedItem(attributeName);
		if (attribute != null) {
			return attribute.getNodeValue();
		} else {
			return null;
		}
	}

	/**
	 * Get CSS class names.
	 * 
	 * @param node
	 *            the HTML node
	 * @return CSS class names
	 */
	public static String[] getCssClasses(Node node) {
		String classString = getAttributeValue(node, "class");
		if (classString != null) {
			return classString.split(",");
		} else {
			return ArrayUtils.EMPTY_STRING_ARRAY;
		}
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
		if (log.isDebugEnabled()) {
			try {
				log.debug(getXmlAsString(select));
			} catch (TransformerException e) {
				log.warn(e);
			}
		}
		hfs.setName(select.getAttribute("name"));
		NodeList options = select.getElementsByTagName("option");
		if (options.getLength() == 0) {// Hack it as html and xhtml are not
			// same in capitalization-sensitivity.
			options = select.getElementsByTagName("OPTION");
		}
		List<String> candidateValues = new ArrayList<String>(options
				.getLength());
		List<String> candidateLabels = new ArrayList<String>(options
				.getLength());
		List<String> values = new ArrayList<String>(options.getLength());
		List<String> labels = new ArrayList<String>(options.getLength());
		String value, label;
		for (int i = 0; i < options.getLength(); i++) {
			Element option = (Element) options.item(i);
			value = option.getAttribute("value");
			label = option.getFirstChild().getNodeValue();
			candidateValues.add(value);
			candidateLabels.add(label);

			Node selectedNode = option.getAttributes().getNamedItem("selected");
			log.debug("is selectedNode null: " + (selectedNode == null));
			if (selectedNode != null) {
				values.add(value);
				labels.add(label);
			}
		}

		// 如果没有任何被 selected，那么默认是选择的第一个。
		if (values.size() == 0 && options.getLength() != 0) {
			Element option = (Element) options.item(0);
			values.add(option.getAttribute("value"));
			labels.add(option.getFirstChild().getNodeValue());
		}

		String[] candidateValueStrings = new String[candidateValues.size()];
		candidateValues.toArray(candidateValueStrings);
		hfs.setCandidateValues(candidateValueStrings);
		String[] candidateLabelStrings = new String[candidateLabels.size()];
		candidateLabels.toArray(candidateLabelStrings);
		hfs.setCandidateLabels(candidateLabelStrings);

		String[] valueStrings = new String[values.size()];
		values.toArray(valueStrings);
		hfs.setValues(valueStrings);
		String[] labelStrings = new String[labels.size()];
		labels.toArray(labelStrings);
		hfs.setLabels(labelStrings);
		return hfs;
	}

	/**
	 * Output the node as xml to debug.
	 * 
	 * @param log
	 * @param node
	 */
	public static void debug(Log log, Node node) {
		debug(null, log, node);
	}

	/**
	 * Output the node as xml to debug.
	 * 
	 * @param title
	 * @param log
	 * @param node
	 */
	public static void debug(String title, Log log, Node node) {
		if (log.isDebugEnabled()) {
			try {
				String s = getXmlAsString(node);
				if (title != null) {
					log.debug(String.format("%1$s: %2$s", title, s));
				} else {
					log.debug(s);
				}
			} catch (TransformerException e) {
				log.debug("Error while get xml as string from a node.", e);
			}
		}
	}

	public static String TextExtractor(Node root, BlogSettings config) {
		// 若是文本节点的话，直接返回
		if (root.getNodeType() == Node.TEXT_NODE) {
			return root.getNodeValue().trim();
		}
		if (root.getNodeType() == Node.ELEMENT_NODE) {
			Element elmt = (Element) root;
			// 抛弃脚本
			if (elmt.getTagName().equals("SCRIPT"))
				return "";

			if (elmt.getTagName().equalsIgnoreCase("IMG")) {
				String imgSrc = elmt.getAttribute("src");
				
				if(config.getIsSaveImage()) {
					// 下载图片
					File file = saveImg(imgSrc, config.getImageSavePath());
					String fileName = file.getName();
					// 替换图片地址
					elmt.setAttribute("src", config.getImageUrlPrefix() + fileName);
				}
				
				return getHtmlAsString(root);
			}
			NodeList children = elmt.getChildNodes();
			StringBuilder text = new StringBuilder();
			for (int i = 0; i < children.getLength(); i++) {
				text.append(TextExtractor(children.item(i), config));
			}
			return text.toString();
		}
		// 对其它类型的节点，返回空值
		return "";
	}

	/**
	 * 下载URL图片
	 * 
	 * @param url
	 *            图片URL地址
	 * @param path
	 *            图片存放地址
	 * @return 返回新文件名字加设定后缀,如果读取失败则返回null
	 */
	public static File saveImg(String url, String paths) {
		File newName = null;

		URL imgUrl = null;
		BufferedImage img = null;
		FileOutputStream out = null;
		boolean isDownload = false;
		File testNewName = null;
		try {
			String newFileName = paths + UUID.randomUUID().toString() + ".jpg";
			imgUrl = new URL(url);
			img = ImageIO.read(imgUrl);
			out = new FileOutputStream(newFileName);
//			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
//			encoder.encode(img);
			ImageIO.write(img, "jpg", out);
			out.close();
			img.flush();
			log.info("读取图片成功!" + imgUrl.toString());
			testNewName = new File(newFileName);
			isDownload = true;
		} catch (Exception e) {
			log.info("读取图片失败!" + e.getMessage());
		}

		finally {
			if (null != null) {
				img.flush();
			}

			if (null != out) {
				try {
					out.close();
				} catch (Exception e) {
				}
			}
		}

		if (isDownload) {
			newName = testNewName;
		}
		return newName;
	}
}
