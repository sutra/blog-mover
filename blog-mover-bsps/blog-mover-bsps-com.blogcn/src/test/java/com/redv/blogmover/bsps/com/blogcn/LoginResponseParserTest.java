package com.redv.blogmover.bsps.com.blogcn;

import java.io.IOException;

import junit.framework.TestCase;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.redv.blogmover.bsps.com.blogcn.LoginResponseParser;
import com.redv.blogmover.util.HtmlFileToDocument;

/**
 * @author shutra
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
	 * {@link com.redv.blogmover.bsps.com.blogcn1.LoginResponseParser#parse()}.
	 * 
	 * @throws SAXException
	 * @throws IOException
	 */
	public void testParse() throws IOException, SAXException {
		testParse("login-success-response.html",
				LoginResponseParser.RESULT_LOGGED_IN);
	}

	public void testParsePasswordIncorrect() throws IOException, SAXException {
		testParse("login-fail-password-incorrect-response.html",
				LoginResponseParser.RESULT_PASSWORD_INCORRECT);
	}

	public void testParseUsernameNotExists() throws IOException, SAXException {
		testParse("login-fail-username-not-exists-response.html",
				LoginResponseParser.RESULT_USERNAME_NOT_EXISTS);
	}

	private void testParse(String htmlFile, int expectedResult)
			throws IOException, SAXException {
		Document document = new HtmlFileToDocument().getDocument(this
				.getClass().getResource(htmlFile), "gb2312");
		parser.setDocument(document);
		parser.parse();
		assertEquals(expectedResult, parser.getResult());
	}
}
