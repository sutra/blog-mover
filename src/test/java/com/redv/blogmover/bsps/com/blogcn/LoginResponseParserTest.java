/**
 * Created on 2007-1-31 下午11:49:50
 */
package com.redv.blogmover.bsps.com.blogcn;

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
 * @author shutra
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
	 * {@link com.redv.blogmover.bsps.com.blogcn.LoginResponseParser#parse()}.
	 * 
	 * @throws SAXException
	 * @throws IOException
	 */
	@Test
	public void testParse() throws IOException, SAXException {
		testParse("login-success-response.html",
				LoginResponseParser.RESULT_LOGGED_IN);
	}

	@Test
	public void testParsePasswordIncorrect() throws IOException, SAXException {
		testParse("login-fail-password-incorrect-response.html",
				LoginResponseParser.RESULT_PASSWORD_INCORRECT);
	}

	@Test
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
