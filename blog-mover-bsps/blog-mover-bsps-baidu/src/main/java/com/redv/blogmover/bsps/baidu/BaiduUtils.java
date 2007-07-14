/**
 * Created on 2006-12-2 上午12:24:31
 */
package com.redv.blogmover.bsps.baidu;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.redv.blogmover.util.HttpDocument;

/**
 * @author shutra
 * 
 */
public class BaiduUtils {

	public static String findBlogHandle(HttpDocument httpDocument) {
		String ret = null;
		Document document = httpDocument.get("http://hi.baidu.com/");
		NodeList inputs = document.getElementsByTagName("input");
		for (int i = 0; i < inputs.getLength(); i++) {
			Node input = inputs.item(i);
			String name = null;
			// Catched the null pointer exception, as the sometime the element
			// is not exists.
			try {
				name = input.getAttributes().getNamedItem("name")
						.getNodeValue();
			} catch (NullPointerException e) {
				// Yes, just do nothing.
			}
			String value = null;
			try {
				value = input.getAttributes().getNamedItem("value")
						.getNodeValue();
			} catch (NullPointerException e) {
				// Yes, just do nothing.
			}
			if ("".equals(name) && "立即进入我的空间".equals(value)) {
				String onclick = input.getAttributes().getNamedItem("onclick")
						.getNodeValue();
				Pattern p = Pattern.compile("window.location.href='/([^/']+)'");
				Matcher m = p.matcher(onclick);
				if (m.matches()) {
					ret = m.group(1);
				}
			}
		}
		return ret;
	}
}
