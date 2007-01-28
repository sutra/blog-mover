/**
 * Created on 2007-1-27 下午03:22:01
 */
package com.redv.blogmover.bsps.com.blogchinese;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.InputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.cyberneko.html.parsers.DOMParser;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import com.redv.blogmover.util.DomNodeUtils;

/**
 * @author shutra
 * 
 */
public class LoginResponseParserTest {
	private static final Log log = LogFactory
			.getLog(LoginResponseParserTest.class);

	private LoginResponseParser parser;

	private static Document loginSuccessResponseDocument;

	private static Document loginFailResponseDocument;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		InputStream inputStream = LoginResponseParserTest.class
				.getResourceAsStream("login-success-response.html");
		DOMParser parser = new DOMParser();
		InputSource inputSource = new InputSource();
		inputSource.setByteStream(inputStream);
		try {
			parser.parse(inputSource);
			loginSuccessResponseDocument = parser.getDocument();
		} finally {
			inputStream.close();
		}

		inputStream = LoginResponseParserTest.class
				.getResourceAsStream("login-fail-response.html");
		inputSource = new InputSource();
		inputSource.setByteStream(inputStream);
		try {
			parser.parse(inputSource);
			parser.parse(inputSource);
			loginFailResponseDocument = parser.getDocument();
		} finally {
			inputStream.close();
		}

		log.debug(DomNodeUtils.getXmlAsString(loginSuccessResponseDocument));
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
	 * {@link com.redv.blogmover.bsps.com.blogchinese.LoginResponseParser#checkLoginSuccess(org.w3c.dom.Document)}.
	 */
	@Test
	public void testCheckLoginSuccess() {
		boolean ret = parser.checkLoginSuccess(loginSuccessResponseDocument);
		assertTrue(ret);
	}

	@Test
	public void testCheckLoginFailed() {
		boolean ret = parser.checkLoginSuccess(loginFailResponseDocument);
		assertFalse(ret);
	}

}
