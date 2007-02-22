/**
 * Created on 2007-1-27 下午03:22:01
 */
package com.redv.blogmover.bsps.com.blogchinese;

import java.io.IOException;

import junit.framework.TestCase;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.redv.blogmover.util.HtmlFileToDocument;

/**
 * @author shutra
 * 
 */
public class LoginResponseParserTest extends TestCase {
	@SuppressWarnings("unused")
	private static final Log log = LogFactory
			.getLog(LoginResponseParserTest.class);

	private LoginResponseParser parser;

	/**
	 * @throws java.lang.Exception
	 */
	public void setUp() throws Exception {
		parser = new LoginResponseParser();
	}

	/**
	 * @throws java.lang.Exception
	 */
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for
	 * {@link com.redv.blogmover.bsps.com.blogchinese.LoginResponseParser#checkLoginSuccess(org.w3c.dom.Document)}.
	 * 
	 * @throws SAXException
	 * @throws IOException
	 */
	public void testCheckLoginSuccess() throws IOException, SAXException {
		Document loginSuccessResponseDocument = new HtmlFileToDocument()
				.getDocument(this.getClass().getResource(
						"login-success-response.html"), "gb2312");
		boolean ret = parser.checkLoginSuccess(loginSuccessResponseDocument);
		assertTrue("true expected, but was false.", ret);
	}

	public void testCheckLoginFailed() throws IOException, SAXException {
		Document loginFailResponseDocument = new HtmlFileToDocument()
				.getDocument(this.getClass().getResource(
						"login-fail-response.html"), "gb2312");
		boolean ret = parser.checkLoginSuccess(loginFailResponseDocument);
		assertFalse("false expected, but was true", ret);
	}

}
