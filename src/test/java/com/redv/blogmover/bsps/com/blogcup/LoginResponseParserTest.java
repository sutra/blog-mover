/**
 * 
 */
package com.redv.blogmover.bsps.com.blogcup;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.redv.blogmover.util.HtmlFileToDocument;

/**
 * @author shutrazh
 * 
 */
public class LoginResponseParserTest {
	private LoginResponseParser parser;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		parser = new LoginResponseParser();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for
	 * {@link com.redv.blogmover.bsps.com.blogcup.LoginResponseParser#parse()}.
	 * 
	 * @throws SAXException
	 * @throws IOException
	 */
	@Test
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
	@Test
	public void testParseLoginFail() throws IOException, SAXException {
		Document document = new HtmlFileToDocument().getDocument(this
				.getClass().getResource("login-fail-response.html"), "UTF-8");
		parser.setDocument(document);
		parser.parse();
		assertFalse(parser.isLoggedIn());
	}

}
