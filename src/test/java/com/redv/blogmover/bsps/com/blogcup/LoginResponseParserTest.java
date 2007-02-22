/**
 * 
 */
package com.redv.blogmover.bsps.com.blogcup;

import java.io.IOException;

import junit.framework.TestCase;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.redv.blogmover.util.HtmlFileToDocument;

/**
 * @author shutrazh
 * 
 */
public class LoginResponseParserTest extends TestCase {
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
	 * {@link com.redv.blogmover.bsps.com.blogcup.LoginResponseParser#parse()}.
	 * 
	 * @throws SAXException
	 * @throws IOException
	 */
	public void testParseLoginSuccess() throws IOException, SAXException {
		Document document = new HtmlFileToDocument()
				.getDocument(this.getClass().getResource(
						"login-success-response.html"), "UTF-8");
		parser.setDocument(document);
		parser.parse();
		assertTrue(parser.isLoggedIn());
	}

	/**
	 * Test method for
	 * {@link com.redv.blogmover.bsps.com.blogcup.LoginResponseParser#parse()}.
	 * 
	 * @throws SAXException
	 * @throws IOException
	 */
	public void testParseLoginFail() throws IOException, SAXException {
		Document document = new HtmlFileToDocument().getDocument(this
				.getClass().getResource("login-fail-response.html"), "UTF-8");
		parser.setDocument(document);
		parser.parse();
		assertFalse(parser.isLoggedIn());
	}

}
