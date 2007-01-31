/**
 * Created on 2007-1-31 下午11:47:43
 */
package com.redv.blogmover.bsps.com.blogcn;

import javax.xml.transform.TransformerException;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;

import com.redv.blogmover.util.DomNodeUtils;

/**
 * @author shutra
 * 
 */
public class LoginResponseParser {
	@SuppressWarnings("unused")
	private static final Log log = LogFactory.getLog(LoginResponseParser.class);

	public static final int RESULT_LOGGED_IN = 1;

	public static final int RESULT_USERNAME_NOT_EXISTS = 2;

	public static final int RESULT_PASSWORD_INCORRECT = 3;

	private Document document;

	private int result;

	/**
	 * @return the result
	 */
	public int getResult() {
		return result;
	}

	/**
	 * @param document
	 *            the document to set
	 */
	public void setDocument(Document document) {
		this.document = document;
	}

	public void parse() {
		this.result = RESULT_LOGGED_IN;
		String str;
		try {
			str = DomNodeUtils.getXmlAsString(document);
		} catch (TransformerException e) {
			throw new RuntimeException(e);
		}
		if (StringUtils.contains(str, "用户名不存在，请重新输入")) {
			this.result = RESULT_USERNAME_NOT_EXISTS;
		} else if (StringUtils.contains(str, "密码不正确，请重新输入")) {
			this.result = RESULT_PASSWORD_INCORRECT;
		}
	}
}
